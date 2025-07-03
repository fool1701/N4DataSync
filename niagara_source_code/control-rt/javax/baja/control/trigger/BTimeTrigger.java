/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.control.trigger;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BTimeTrigger is a component that fires a topic at configured times.
 *
 * @author    John Sublett
 * @creation  28 Mar 2003
 * @version   $Revision: 11$ $Date: 4/20/09 5:27:05 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "triggerMode",
  type = "BTriggerMode",
  defaultValue = "BDailyTriggerMode.DEFAULT"
)
/*
 The time of the last trigger or null is no trigger
 has ever occurred.
 */
@NiagaraProperty(
  name = "lastTrigger",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 The next time that a trigger will be fired.
 */
@NiagaraProperty(
  name = "nextTrigger",
  type = "BAbsTime",
  defaultValue = "BAbsTime.END_OF_TIME",
  flags = Flags.READONLY | Flags.NON_CRITICAL
)
/*
 Fire the trigger.
 */
@NiagaraAction(
  name = "fireTrigger"
)
/*
 See if it's time to fire the trigger.
 */
@NiagaraAction(
  name = "checkTime",
  flags = Flags.HIDDEN
)
public class BTimeTrigger
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.trigger.BTimeTrigger(4138249433)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "triggerMode"

  /**
   * Slot for the {@code triggerMode} property.
   * @see #getTriggerMode
   * @see #setTriggerMode
   */
  public static final Property triggerMode = newProperty(0, BDailyTriggerMode.DEFAULT, null);

  /**
   * Get the {@code triggerMode} property.
   * @see #triggerMode
   */
  public BTriggerMode getTriggerMode() { return (BTriggerMode)get(triggerMode); }

  /**
   * Set the {@code triggerMode} property.
   * @see #triggerMode
   */
  public void setTriggerMode(BTriggerMode v) { set(triggerMode, v, null); }

  //endregion Property "triggerMode"

  //region Property "lastTrigger"

  /**
   * Slot for the {@code lastTrigger} property.
   * The time of the last trigger or null is no trigger
   * has ever occurred.
   * @see #getLastTrigger
   * @see #setLastTrigger
   */
  public static final Property lastTrigger = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code lastTrigger} property.
   * The time of the last trigger or null is no trigger
   * has ever occurred.
   * @see #lastTrigger
   */
  public BAbsTime getLastTrigger() { return (BAbsTime)get(lastTrigger); }

  /**
   * Set the {@code lastTrigger} property.
   * The time of the last trigger or null is no trigger
   * has ever occurred.
   * @see #lastTrigger
   */
  public void setLastTrigger(BAbsTime v) { set(lastTrigger, v, null); }

  //endregion Property "lastTrigger"

  //region Property "nextTrigger"

  /**
   * Slot for the {@code nextTrigger} property.
   * The next time that a trigger will be fired.
   * @see #getNextTrigger
   * @see #setNextTrigger
   */
  public static final Property nextTrigger = newProperty(Flags.READONLY | Flags.NON_CRITICAL, BAbsTime.END_OF_TIME, null);

  /**
   * Get the {@code nextTrigger} property.
   * The next time that a trigger will be fired.
   * @see #nextTrigger
   */
  public BAbsTime getNextTrigger() { return (BAbsTime)get(nextTrigger); }

  /**
   * Set the {@code nextTrigger} property.
   * The next time that a trigger will be fired.
   * @see #nextTrigger
   */
  public void setNextTrigger(BAbsTime v) { set(nextTrigger, v, null); }

  //endregion Property "nextTrigger"

  //region Action "fireTrigger"

  /**
   * Slot for the {@code fireTrigger} action.
   * Fire the trigger.
   * @see #fireTrigger()
   */
  public static final Action fireTrigger = newAction(0, null);

  /**
   * Invoke the {@code fireTrigger} action.
   * Fire the trigger.
   * @see #fireTrigger
   */
  public void fireTrigger() { invoke(fireTrigger, null, null); }

  //endregion Action "fireTrigger"

  //region Action "checkTime"

  /**
   * Slot for the {@code checkTime} action.
   * See if it's time to fire the trigger.
   * @see #checkTime()
   */
  public static final Action checkTime = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code checkTime} action.
   * See if it's time to fire the trigger.
   * @see #checkTime
   */
  public void checkTime() { invoke(checkTime, null, null); }

  //endregion Action "checkTime"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeTrigger.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BTimeTrigger()
  {
  }
  
  public BTimeTrigger(BTriggerMode mode)
  {
    setTriggerMode(mode);
  }

  /**
   * Init after steady state has been reached.
   */
  public void atSteadyState()
  {
    if (isRunning()) init();
  }

  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    if (Sys.atSteadyState()) init();
  }

  /**
   * Cleanup on stop.
   */
  public void stopped()
  {
    if (scheduler != null) scheduler.stop();
    scheduler = null;
  }

  /**
   * Start checking the time.
   */
  private void init()
  {
    if (scheduler != null) scheduler.stop();
    scheduler = getTriggerMode().makeScheduler(this);
    checkTime();
    scheduler.start();
    BAbsTime scheduled = scheduler.getScheduledTriggerTime();
    if (scheduled == null)
      setNextTrigger(scheduler.getNextTriggerTime(Clock.time(), getLastTrigger()));
    else
      setNextTrigger(scheduled);
  }

  /**
   * Fire the trigger and update the last trigger time.
   * This implementation includes required behavior so
   * subclasses must call super.doFireTrigger().
   */
  public void doFireTrigger()
  {
    BAbsTime now = Clock.time();
    setLastTrigger(now);
  }

  /**
   * See if now is a trigger time.  If so, fire the trigger and
   * update the next time.
   */
  public void doCheckTime()
  {
    BAbsTime now = Clock.time();
    if (scheduler.isTriggerTime(now))
    {
      fireTrigger();
    }
    
    BAbsTime scheduled = scheduler.getScheduledTriggerTime();
    if (scheduled == null)
      setNextTrigger(scheduler.getNextTriggerTime(now, getLastTrigger()));
    else
      setNextTrigger(scheduled);
  }

  /**
   * Reinit on change of triggerTime.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == triggerMode)
    {
      init();
    }
  }

  public void clockChanged(BRelTime shift)
  {
    init();
  }

  public String toString(Context cx)
  {
    return getTriggerMode().toString(cx);
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/trigger.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private TriggerScheduler scheduler;
  
}
