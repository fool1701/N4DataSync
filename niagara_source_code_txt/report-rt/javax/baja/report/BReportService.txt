/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.report;

import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.Queue;

import com.tridium.util.PxUtil;
/**
 * BReportService.
 *
 * @author    Andy Frank
 * @creation  16 Oct 06
 * @version   $Revision: 4$ $Date: 11/28/07 3:39:17 PM EST$
 * @since     Niagara 3.2
 */
@NiagaraType
public class BReportService
  extends BComponent
  implements BIService, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.report.BReportService(2979906276)1.0$ @*/
/* Generated Fri Nov 19 16:00:09 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReportService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BIService
////////////////////////////////////////////////////////////////

  /**
   * Register this component under "report:ReportService".
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static Type[] serviceTypes = { TYPE };

  /**
   * Service start.
   */
  @Override
  public void serviceStarted()
    throws Exception
  {
    queue = new Queue();
    manager = new QueueManager();
    manager.start();
  }

  /**
   * Service stop.
   */
  @Override
  public void serviceStopped()
    throws Exception
  {
    if (manager != null) manager.interrupt();
    if (queue != null) queue.clear();
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   * Only Super Users are allowed to add an instance of this type to the station.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkContextForSuperUser(this, cx);
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Queue
////////////////////////////////////////////////////////////////

  /**
   * Enqueue a job to be run on the QueueManager thread.
   */
  public void enqueue(Runnable r)
  {
    queue.enqueue(r);
  }

  /**
   * QueueManager is used to queue Runnable work units
   * to run asynchronously off the main engine thread.
   */
  class QueueManager extends Thread
  {
    QueueManager()
    {
      super("ReportService:QueueManager");
    }

    @Override
    public void run()
    {
      while (true)
      {
        try { queue.todo(-1).run();  }
        catch (InterruptedException e) { break; }
        catch (Throwable e) { e.printStackTrace(); }
      }
    }
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the agent list.
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.toTop("wiresheet:WebWiresheet");
    list.toTop("wiresheet:WireSheet");
    return PxUtil.movePxViewsToTop(list);
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://report/icons/reportService.png");

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("report");

  QueueManager manager;  // queue manager thread
  Queue queue;           // queue of Runnable objects

}
