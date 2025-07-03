/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.control.trigger;

import javax.baja.sys.BAbsTime;

/**
 * A TriggerScheduler schedules the firing of a trigger according
 * to a trigger mode.
 *
 * @author    John Sublett
 * @creation  12 Feb 2004
 * @version   $Revision: 2$ $Date: 4/4/06 11:52:56 AM EDT$
 * @since     Baja 1.0
 */
public abstract class TriggerScheduler
{
  /**
   * Create a scheduler for the specified trigger.
   */
  public TriggerScheduler(BTimeTrigger trigger)
  {
    this.trigger = trigger;
  }

  /**
   * Get the trigger for this scheduler.
   */
  public final BTimeTrigger getTrigger()
  {
    return trigger;
  }

  /**
   * Start checking the time.  Typically, this method will schedule the
   * checkTime action for the TimeTrigger.
   * <p>
   * For example:
   * <code>
   * ticket = Clock.schedulePeriodically(getTrigger(), BRelTime.makeMinutes(5), BTimeTrigger.checkTime, null);
   * </code>
   * When the checkTime is invoked, the isTriggerTime() method will be called.
   * The TriggerTime should schedule the checkTime action to be invoked often enough
   * to catch all trigger times.
   */
  public abstract void start();

  /**
   * Stop checking the time.
   */
  public abstract void stop();
  
  /**
   * Does the specified time indicate that it is time to fire the trigger?
   */
  public abstract boolean isTriggerTime(BAbsTime time);

  /**
   * Get the next trigger time after the specified time.
   */
  public abstract BAbsTime getNextTriggerTime(BAbsTime after, BAbsTime previous);

  BAbsTime getScheduledTriggerTime()
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BTimeTrigger trigger;

}