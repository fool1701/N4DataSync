/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util.worker;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;
import javax.baja.util.Queue;
import javax.baja.util.ThreadPoolWorker;

/**
 * BBacnetWorkerPool is a thin wrapper around
 * a ThreadPoolWorker, that can be added as a child
 * to any IWorkerPoolAware BComponent (e.g. BBacnetWorker).
 * <p>
 * The WorkerPool adds new work to the queues in a
 * round-robin fashion.
 *
 * @author Joseph Chandler
 * @version $Revision$ $Date$
 * @creation 26 Aug 2013
 * @since Niagara 3.8 Bacnet 1.0
 */
@NiagaraType
/*
 Max number of concurrent threads for working.
 */
@NiagaraProperty(
  name = "maxThreads",
  type = "int",
  defaultValue = "DEFAULT_WORKERS",
  facets = @Facet(name = "BFacets.MIN", value = "1")
)
public class BBacnetWorkerPool
  extends BComponent
  implements IWorkerPool
{
  private static final int DEFAULT_WORKERS = 2;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.util.worker.BBacnetWorkerPool(4000817463)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "maxThreads"

  /**
   * Slot for the {@code maxThreads} property.
   * Max number of concurrent threads for working.
   * @see #getMaxThreads
   * @see #setMaxThreads
   */
  public static final Property maxThreads = newProperty(0, DEFAULT_WORKERS, BFacets.make(BFacets.MIN, 1));

  /**
   * Get the {@code maxThreads} property.
   * Max number of concurrent threads for working.
   * @see #maxThreads
   */
  public int getMaxThreads() { return getInt(maxThreads); }

  /**
   * Set the {@code maxThreads} property.
   * Max number of concurrent threads for working.
   * @see #maxThreads
   */
  public void setMaxThreads(int v) { setInt(maxThreads, v, null); }

  //endregion Property "maxThreads"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetWorkerPool.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetWorkerPool()
  {

  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public boolean isParentLegal(BComponent parent)
  {
    return BBacnetWorkerPool.isLegalParent(parent);
  }

  public static boolean isLegalParent(BComponent parent)
  {
    return parent instanceof IWorkerPoolAware &&
      !alreadyHasWorkerPool(parent);
  }

  private static boolean alreadyHasWorkerPool(BComponent parent)
  {
    BComponent[] children = parent.getChildComponents();
    for (int i = 0; i < children.length; i++)
    {
      if (children[i] instanceof IWorkerPool)
        return true;
    }
    return false;
  }

  public void started()
    throws Exception
  {
    super.started();
    IWorkerPoolAware parent = (IWorkerPoolAware)getParent();

    queue = parent.getQueue();
    worker = new ThreadPoolWorker(queue);
    worker.setMaxThreads(getMaxThreads());
    worker.start(parent.getWorkerThreadName());
    parent.stopWorker();
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
    worker.stop();
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;

    if (p.equals(maxThreads))
    {
      updateMaxThreads();
    }
  }

  private void updateMaxThreads()
  {
    worker.setMaxThreads(getMaxThreads());
  }

////////////////////////////////////////////////////////////////
//Attributes
////////////////////////////////////////////////////////////////

  //private Object lock = new Object();
  private ThreadPoolWorker worker = null;
  private Queue queue = null;

  //private static final Log log = Log.getLog("bacnet.worker");

////////////////////////////////////////////////////////////////
// IBacnetWorker
////////////////////////////////////////////////////////////////

  /**
   * Post an action to be run asynchronously.
   */
  public IFuture post(Runnable r)
  {
    if (!isRunning())
      throw new NotRunningException();

    queue.enqueue(r);
    return null;
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("gears.png");

}
