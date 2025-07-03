/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.report;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.control.trigger.BTimeTrigger;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

/**
 * BReportSource is responsible for generating BReports for consumption
 * by BReportRecipients.  Reports are generated when the {@code generate}
 * action is invoked. ReportSource includes a built-in schedule for
 * automatically invoking {@code generate} on a scheduled basis.
 * Subclasses should override {@code handleGenerate()} to customize:
 *
 * <pre>{@code
 *   public class BMyReportSource
 *     extends BReportSource
 *   {
 *     public BReport handleGenerate()
 *       throws Exception
 *     {
 *       ...
 *       return new BReport(...);
 *     }
 *   }
 * }</pre>
 *
 * @author    Andy Frank
 * @creation  16 Oct 06
 * @version   $Revision: 6$ $Date: 11/28/07 3:39:18 PM EST$
 * @since     Niagara 3.2
 */
@NiagaraType
/*
 The schedule used to trigger this report to be generated.
 */
@NiagaraProperty(
  name = "schedule",
  type = "BTimeTrigger",
  defaultValue = "new BTimeTrigger()"
)
/*
 Generate this report and fire the out topic.
 */
@NiagaraAction(
  name = "generate",
  flags = Flags.ASYNC
)
/*
 Fired when generate() is invoked.
 */
@NiagaraTopic(
  name = "out",
  eventType = "BReport",
  flags = Flags.READONLY | Flags.SUMMARY
)
public abstract class BReportSource
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.report.BReportSource(3209655370)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "schedule"

  /**
   * Slot for the {@code schedule} property.
   * The schedule used to trigger this report to be generated.
   * @see #getSchedule
   * @see #setSchedule
   */
  public static final Property schedule = newProperty(0, new BTimeTrigger(), null);

  /**
   * Get the {@code schedule} property.
   * The schedule used to trigger this report to be generated.
   * @see #schedule
   */
  public BTimeTrigger getSchedule() { return (BTimeTrigger)get(schedule); }

  /**
   * Set the {@code schedule} property.
   * The schedule used to trigger this report to be generated.
   * @see #schedule
   */
  public void setSchedule(BTimeTrigger v) { set(schedule, v, null); }

  //endregion Property "schedule"

  //region Action "generate"

  /**
   * Slot for the {@code generate} action.
   * Generate this report and fire the out topic.
   * @see #generate()
   */
  public static final Action generate = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code generate} action.
   * Generate this report and fire the out topic.
   * @see #generate
   */
  public void generate() { invoke(generate, null, null); }

  //endregion Action "generate"

  //region Topic "out"

  /**
   * Slot for the {@code out} topic.
   * Fired when generate() is invoked.
   * @see #fireOut
   */
  public static final Topic out = newTopic(Flags.READONLY | Flags.SUMMARY, null);

  /**
   * Fire an event for the {@code out} topic.
   * Fired when generate() is invoked.
   * @see #out
   */
  public void fireOut(BReport event) { fire(out, event, null); }

  //endregion Topic "out"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReportSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  @Override
  public void started()
  {
    linkTo(getSchedule(), BTimeTrigger.fireTrigger, generate);
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Generate a new Report.
   */
  public abstract BReport handleGenerate() throws Exception;

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public final void doGenerate()
  {
    try
    {
      BReport event = handleGenerate();
      fireOut(event);
    }
    catch (Exception e)
    {
      log.log(Level.SEVERE, "Generate failed", e);
      throw new BajaRuntimeException(e);
    }
  }

  @Override
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action == generate)
    {
      BReportService s = (BReportService)Sys.getService(BReportService.TYPE);
      s.enqueue(new Invocation(this, action, arg, cx));
      return null;
    }
    else
    {
      return super.post(action, arg, cx);
    }
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("export.png");

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("report");

}
