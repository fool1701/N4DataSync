/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.ext;

import javax.baja.control.BControlPoint;
import javax.baja.control.BDiscretePoint;
import javax.baja.control.BPointExtension;
import javax.baja.control.enums.BCountTransition;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BDiscreteTotalizerExt is a standard point extension useful
 * for accumulating run time and change of state counts
 * on binary or enum values.
 *
 * @author    Dan Giorgis
 * @creation   1 Nov 00
 * @version   $Revision: 36$ $Date: 7/8/09 3:16:32 PM EDT$
 * @since     Baja 1.0
 */

//  FIXX - elapsed active time should track status input (feedback value)
//  not present value - how to implement in R3?

@NiagaraType
@NiagaraProperty(
  name = "changeOfStateCountTransition",
  type = "BCountTransition",
  defaultValue = "BCountTransition.both"
)
/*
 defines which input status flags will be propagated from
 input to output.
 */
@NiagaraProperty(
  name = "propagateFlags",
  type = "BStatus",
  defaultValue = "BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED )"
)
/*
 defines which input status flags will denote invalid input
 values that should not be included in the total
 */
@NiagaraProperty(
  name = "invalidValueFlags",
  type = "BStatus",
  defaultValue = "BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED)"
)
/*
 Shows a date/timestamp for the last change of state.
 */
@NiagaraProperty(
  name = "changeOfStateTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.make()",
  flags = Flags.READONLY
)
/*
 Shows the total number of changes of state that have occurred
 since the last reset of change of state count.
 */
@NiagaraProperty(
  name = "changeOfStateCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 Shows a date/timestamp for when the change of state count was last cleared.
 */
@NiagaraProperty(
  name = "timeOfStateCountReset",
  type = "BAbsTime",
  defaultValue = "BAbsTime.make()",
  flags = Flags.READONLY
)
/*
 Shows the accumulated runtime (elapsed active time).
 */
@NiagaraProperty(
  name = "elapsedActiveTime",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  flags = Flags.READONLY
)
/*
 Shows the accumulated runtime as a numeric.
 */
@NiagaraProperty(
  name = "elapsedActiveTimeNumeric",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 Shows a date/timestamp for when the accumulated runtime (elapsed active time) was last cleared.
 */
@NiagaraProperty(
  name = "timeOfActiveTimeReset",
  type = "BAbsTime",
  defaultValue = "BAbsTime.make()",
  flags = Flags.READONLY
)
/*
 Shows the minimum update time for Elapsed Active Time and Elapsed Active Time Numeric properties.
 */
@NiagaraProperty(
  name = "eaTimeUpdateInterval",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(10)",
  flags = Flags.SUMMARY
)
/*
 timerExpired
 */
@NiagaraAction(
  name = "timerExpired",
  flags = Flags.HIDDEN
)
/*
 This sets the changeOfStateCount property value to zero (0), clearing any change of state count.
 */
@NiagaraAction(
  name = "resetChangeOfStateCount",
  flags = Flags.CONFIRM_REQUIRED
)
/*
 This sets the elapsedActiveTime property value to zero, clearing any accumulated runtime.
 */
@NiagaraAction(
  name = "resetElapsedActiveTime",
  flags = Flags.CONFIRM_REQUIRED
)
public class BDiscreteTotalizerExt
  extends BPointExtension
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.ext.BDiscreteTotalizerExt(957455442)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "changeOfStateCountTransition"

  /**
   * Slot for the {@code changeOfStateCountTransition} property.
   * @see #getChangeOfStateCountTransition
   * @see #setChangeOfStateCountTransition
   */
  public static final Property changeOfStateCountTransition = newProperty(0, BCountTransition.both, null);

  /**
   * Get the {@code changeOfStateCountTransition} property.
   * @see #changeOfStateCountTransition
   */
  public BCountTransition getChangeOfStateCountTransition() { return (BCountTransition)get(changeOfStateCountTransition); }

  /**
   * Set the {@code changeOfStateCountTransition} property.
   * @see #changeOfStateCountTransition
   */
  public void setChangeOfStateCountTransition(BCountTransition v) { set(changeOfStateCountTransition, v, null); }

  //endregion Property "changeOfStateCountTransition"

  //region Property "propagateFlags"

  /**
   * Slot for the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #getPropagateFlags
   * @see #setPropagateFlags
   */
  public static final Property propagateFlags = newProperty(0, BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED ), null);

  /**
   * Get the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public BStatus getPropagateFlags() { return (BStatus)get(propagateFlags); }

  /**
   * Set the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public void setPropagateFlags(BStatus v) { set(propagateFlags, v, null); }

  //endregion Property "propagateFlags"

  //region Property "invalidValueFlags"

  /**
   * Slot for the {@code invalidValueFlags} property.
   * defines which input status flags will denote invalid input
   * values that should not be included in the total
   * @see #getInvalidValueFlags
   * @see #setInvalidValueFlags
   */
  public static final Property invalidValueFlags = newProperty(0, BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED), null);

  /**
   * Get the {@code invalidValueFlags} property.
   * defines which input status flags will denote invalid input
   * values that should not be included in the total
   * @see #invalidValueFlags
   */
  public BStatus getInvalidValueFlags() { return (BStatus)get(invalidValueFlags); }

  /**
   * Set the {@code invalidValueFlags} property.
   * defines which input status flags will denote invalid input
   * values that should not be included in the total
   * @see #invalidValueFlags
   */
  public void setInvalidValueFlags(BStatus v) { set(invalidValueFlags, v, null); }

  //endregion Property "invalidValueFlags"

  //region Property "changeOfStateTime"

  /**
   * Slot for the {@code changeOfStateTime} property.
   * Shows a date/timestamp for the last change of state.
   * @see #getChangeOfStateTime
   * @see #setChangeOfStateTime
   */
  public static final Property changeOfStateTime = newProperty(Flags.READONLY, BAbsTime.make(), null);

  /**
   * Get the {@code changeOfStateTime} property.
   * Shows a date/timestamp for the last change of state.
   * @see #changeOfStateTime
   */
  public BAbsTime getChangeOfStateTime() { return (BAbsTime)get(changeOfStateTime); }

  /**
   * Set the {@code changeOfStateTime} property.
   * Shows a date/timestamp for the last change of state.
   * @see #changeOfStateTime
   */
  public void setChangeOfStateTime(BAbsTime v) { set(changeOfStateTime, v, null); }

  //endregion Property "changeOfStateTime"

  //region Property "changeOfStateCount"

  /**
   * Slot for the {@code changeOfStateCount} property.
   * Shows the total number of changes of state that have occurred
   * since the last reset of change of state count.
   * @see #getChangeOfStateCount
   * @see #setChangeOfStateCount
   */
  public static final Property changeOfStateCount = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code changeOfStateCount} property.
   * Shows the total number of changes of state that have occurred
   * since the last reset of change of state count.
   * @see #changeOfStateCount
   */
  public int getChangeOfStateCount() { return getInt(changeOfStateCount); }

  /**
   * Set the {@code changeOfStateCount} property.
   * Shows the total number of changes of state that have occurred
   * since the last reset of change of state count.
   * @see #changeOfStateCount
   */
  public void setChangeOfStateCount(int v) { setInt(changeOfStateCount, v, null); }

  //endregion Property "changeOfStateCount"

  //region Property "timeOfStateCountReset"

  /**
   * Slot for the {@code timeOfStateCountReset} property.
   * Shows a date/timestamp for when the change of state count was last cleared.
   * @see #getTimeOfStateCountReset
   * @see #setTimeOfStateCountReset
   */
  public static final Property timeOfStateCountReset = newProperty(Flags.READONLY, BAbsTime.make(), null);

  /**
   * Get the {@code timeOfStateCountReset} property.
   * Shows a date/timestamp for when the change of state count was last cleared.
   * @see #timeOfStateCountReset
   */
  public BAbsTime getTimeOfStateCountReset() { return (BAbsTime)get(timeOfStateCountReset); }

  /**
   * Set the {@code timeOfStateCountReset} property.
   * Shows a date/timestamp for when the change of state count was last cleared.
   * @see #timeOfStateCountReset
   */
  public void setTimeOfStateCountReset(BAbsTime v) { set(timeOfStateCountReset, v, null); }

  //endregion Property "timeOfStateCountReset"

  //region Property "elapsedActiveTime"

  /**
   * Slot for the {@code elapsedActiveTime} property.
   * Shows the accumulated runtime (elapsed active time).
   * @see #getElapsedActiveTime
   * @see #setElapsedActiveTime
   */
  public static final Property elapsedActiveTime = newProperty(Flags.READONLY, BRelTime.DEFAULT, null);

  /**
   * Get the {@code elapsedActiveTime} property.
   * Shows the accumulated runtime (elapsed active time).
   * @see #elapsedActiveTime
   */
  public BRelTime getElapsedActiveTime() { return (BRelTime)get(elapsedActiveTime); }

  /**
   * Set the {@code elapsedActiveTime} property.
   * Shows the accumulated runtime (elapsed active time).
   * @see #elapsedActiveTime
   */
  public void setElapsedActiveTime(BRelTime v) { set(elapsedActiveTime, v, null); }

  //endregion Property "elapsedActiveTime"

  //region Property "elapsedActiveTimeNumeric"

  /**
   * Slot for the {@code elapsedActiveTimeNumeric} property.
   * Shows the accumulated runtime as a numeric.
   * @see #getElapsedActiveTimeNumeric
   * @see #setElapsedActiveTimeNumeric
   */
  public static final Property elapsedActiveTimeNumeric = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code elapsedActiveTimeNumeric} property.
   * Shows the accumulated runtime as a numeric.
   * @see #elapsedActiveTimeNumeric
   */
  public BStatusNumeric getElapsedActiveTimeNumeric() { return (BStatusNumeric)get(elapsedActiveTimeNumeric); }

  /**
   * Set the {@code elapsedActiveTimeNumeric} property.
   * Shows the accumulated runtime as a numeric.
   * @see #elapsedActiveTimeNumeric
   */
  public void setElapsedActiveTimeNumeric(BStatusNumeric v) { set(elapsedActiveTimeNumeric, v, null); }

  //endregion Property "elapsedActiveTimeNumeric"

  //region Property "timeOfActiveTimeReset"

  /**
   * Slot for the {@code timeOfActiveTimeReset} property.
   * Shows a date/timestamp for when the accumulated runtime (elapsed active time) was last cleared.
   * @see #getTimeOfActiveTimeReset
   * @see #setTimeOfActiveTimeReset
   */
  public static final Property timeOfActiveTimeReset = newProperty(Flags.READONLY, BAbsTime.make(), null);

  /**
   * Get the {@code timeOfActiveTimeReset} property.
   * Shows a date/timestamp for when the accumulated runtime (elapsed active time) was last cleared.
   * @see #timeOfActiveTimeReset
   */
  public BAbsTime getTimeOfActiveTimeReset() { return (BAbsTime)get(timeOfActiveTimeReset); }

  /**
   * Set the {@code timeOfActiveTimeReset} property.
   * Shows a date/timestamp for when the accumulated runtime (elapsed active time) was last cleared.
   * @see #timeOfActiveTimeReset
   */
  public void setTimeOfActiveTimeReset(BAbsTime v) { set(timeOfActiveTimeReset, v, null); }

  //endregion Property "timeOfActiveTimeReset"

  //region Property "eaTimeUpdateInterval"

  /**
   * Slot for the {@code eaTimeUpdateInterval} property.
   * Shows the minimum update time for Elapsed Active Time and Elapsed Active Time Numeric properties.
   * @see #getEaTimeUpdateInterval
   * @see #setEaTimeUpdateInterval
   */
  public static final Property eaTimeUpdateInterval = newProperty(Flags.SUMMARY, BRelTime.makeSeconds(10), null);

  /**
   * Get the {@code eaTimeUpdateInterval} property.
   * Shows the minimum update time for Elapsed Active Time and Elapsed Active Time Numeric properties.
   * @see #eaTimeUpdateInterval
   */
  public BRelTime getEaTimeUpdateInterval() { return (BRelTime)get(eaTimeUpdateInterval); }

  /**
   * Set the {@code eaTimeUpdateInterval} property.
   * Shows the minimum update time for Elapsed Active Time and Elapsed Active Time Numeric properties.
   * @see #eaTimeUpdateInterval
   */
  public void setEaTimeUpdateInterval(BRelTime v) { set(eaTimeUpdateInterval, v, null); }

  //endregion Property "eaTimeUpdateInterval"

  //region Action "timerExpired"

  /**
   * Slot for the {@code timerExpired} action.
   * timerExpired
   * @see #timerExpired()
   */
  public static final Action timerExpired = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code timerExpired} action.
   * timerExpired
   * @see #timerExpired
   */
  public void timerExpired() { invoke(timerExpired, null, null); }

  //endregion Action "timerExpired"

  //region Action "resetChangeOfStateCount"

  /**
   * Slot for the {@code resetChangeOfStateCount} action.
   * This sets the changeOfStateCount property value to zero (0), clearing any change of state count.
   * @see #resetChangeOfStateCount()
   */
  public static final Action resetChangeOfStateCount = newAction(Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code resetChangeOfStateCount} action.
   * This sets the changeOfStateCount property value to zero (0), clearing any change of state count.
   * @see #resetChangeOfStateCount
   */
  public void resetChangeOfStateCount() { invoke(resetChangeOfStateCount, null, null); }

  //endregion Action "resetChangeOfStateCount"

  //region Action "resetElapsedActiveTime"

  /**
   * Slot for the {@code resetElapsedActiveTime} action.
   * This sets the elapsedActiveTime property value to zero, clearing any accumulated runtime.
   * @see #resetElapsedActiveTime()
   */
  public static final Action resetElapsedActiveTime = newAction(Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code resetElapsedActiveTime} action.
   * This sets the elapsedActiveTime property value to zero, clearing any accumulated runtime.
   * @see #resetElapsedActiveTime
   */
  public void resetElapsedActiveTime() { invoke(resetElapsedActiveTime, null, null); }

  //endregion Action "resetElapsedActiveTime"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDiscreteTotalizerExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void started()
  {
    synchronized (updateLock)
    {
      elapsedTimeMillis = getElapsedActiveTime().getMillis();
      cosCount = getChangeOfStateCount();
    }

    BControlPoint point = getParentPoint();
    if (point != null)
    {
      update(point.getOutStatusValue());
    }
  }

  @Override
  public void stopped()
  {
    synchronized (updateLock)
    {
      if (ticket != null)
      {
        ticket.cancel();
        ticket = null;
      }
    }
  }

////////////////////////////////////////////////////////////////
// PointExtension
////////////////////////////////////////////////////////////////
  
  @Override
  public boolean requiresPointSubscription()
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Parent checking
////////////////////////////////////////////////////////////////  

  /**
   * Parent must be a DiscretePoint. 
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {                
    if (!super.isParentLegal(parent))
    {
      return false;
    }
    return parent instanceof BDiscretePoint;
  }

  /**
   * Any sibling is legal for an alarm extension.
   */
  @Override
  protected boolean isSiblingLegal(BComponent sibling)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Update Methods
////////////////////////////////////////////////////////////////

  /**
   * Callback for timer expired.
   */
  public void doTimerExpired()
  {
    BControlPoint point = getParentPoint();
    if (point != null)
    {
      update(point.getOutStatusValue());
    }
  }

  /** 
   * Called when either me or my parent control 
   * point is updated.
   */ 
  @Override
  public void onExecute(BStatusValue out, Context cx)
  {
    update(out);
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////
  
  public void doResetChangeOfStateCount()
  {
    synchronized (updateLock)
    {
      cosCount = 0;
    }

    setChangeOfStateCount(cosCount);
    setTimeOfStateCountReset(Clock.time());
  }

  public void doResetElapsedActiveTime()
  {
    synchronized (updateLock)
    {
      lastActiveTime = 0;
      elapsedTimeMillis = 0;
    }

    setElapsedActiveTime(BRelTime.make(elapsedTimeMillis));
    getElapsedActiveTimeNumeric().setValue(elapsedTimeMillis);
    setTimeOfActiveTimeReset(Clock.time());

    BControlPoint point = getParentPoint();
    if (point != null)
    {
      update(point.getOutStatusValue());
    }

    synchronized (updateLock)
    {
      forceUpdate = true;
    }
  }

////////////////////////////////////////////////////////////////
//  Internal Utility Methods
////////////////////////////////////////////////////////////////  

  /**
   * Sets changeOfStateCount, changeOfStateTime, elapsedActiveTime, and elapsedActiveTimeNumeric.
   * A lock is required on this method to prevent another call to it from using a previous value of
   * changeOfStateCount (there may be other side-effects of concurrent processing as well).
   * Previously, a lock other than "this" was used but that would cause a deadlock when trying to
   * set property values if the component was already locked, such as when it was being added to a
   * point.
   *
   * There was also an issue where onExecute would run before started was called and that
   * would reset a persisted elapsedActiveTime value. The started issue could be solved with an
   * AtomicBoolean tracking whether started has completed but this did not prevent deadlocks. By
   * locking on "this", onExecute will not be able to call update until started is finished and its
   * lock on "this" is released.
   *
   * On a subsequent refactor, the locking was changed (yet) again to avoid using a lock on "this"
   * and delay any property sets until the new lock (updateLock) is relinquished. This change was
   * needed to address additional deadlocks (see NCCB-56060).
   */
  private void update(BStatusValue out)
  {
    // If the changeOfStateCount is updated within the lock, this cosTime variable will also be set-
    // that will cause the setChangeOfStateCount and setChangeOfStateTime methods to be called once
    // outside the lock.
    BAbsTime cosTime = null;

    boolean setElapsedAfterLock = false;

    synchronized (updateLock)
    {
      // Short-circuits if called from onExecute after the component has been stopped. This
      // isRunning check should occur inside the updateLock so the update here completes before
      // anything in the stopped callback.
      if (!isRunning())
      {
        return;
      }

      boolean isValueValid = (out.getStatus().getBits() & getInvalidValueFlags().getBits()) == 0;

      // Are we currently active?
      boolean isActive = ((BIEnum)out).getEnum().isActive();

      // Check for change of state
      boolean countIt = false;
      if (isValueValid)
      {
        switch (getChangeOfStateCountTransition().getOrdinal())
        {
          case BCountTransition.TO_ACTIVE:
            countIt = !lastActive && isActive;
            break;
          case BCountTransition.TO_INACTIVE:
            countIt = lastActive && !isActive;
            break;
          default:
            countIt = lastActive != isActive;
        }
        // lastActive is only read and written within the updateLock
        lastActive = isActive;
      }

      if (countIt)
      {
        // cosCount is only read and written within the updateLock
        cosCount += 1;
        cosTime = Clock.time();
      }

      // update elapsed active time
      long now = Clock.ticks();

      //  Add to the accumulated active time calculation
      // lastActiveTime and elapsedTimeMillis are only read and written within the updateLock
      boolean isElapsedTimeUpdated = false;
      if (lastActiveTime != 0)
      {
        elapsedTimeMillis += now - lastActiveTime;
        isElapsedTimeUpdated = true;
      }

      if (isActive && isValueValid)
      {
        lastActiveTime = now;
        // ticket is only read and written within the updateLock
        if (ticket == null || ticket.isExpired())
        {
          ticket = Clock.schedulePeriodically(this, BRelTime.make(1000), timerExpired, null);
        }
      }
      else
      {
        lastActiveTime = 0;
        if (ticket != null && !ticket.isExpired())
        {
          ticket.cancel();
        }
        ticket = null;
      }

      // Wait for minimum update time and ignore this only when reset happens
      // forceUpdate and lastEATUpdateTime are only read and written within the updateLock
      if (isElapsedTimeUpdated && (forceUpdate || now - lastEATUpdateTime > getEaTimeUpdateInterval().getMillis()))
      {
        setElapsedAfterLock = true;
        lastEATUpdateTime = now;
        forceUpdate = false;
      }
    }

    if (cosTime != null)
    {
      setChangeOfStateCount(cosCount);
      setChangeOfStateTime(cosTime);
    }

    if (setElapsedAfterLock)
    {
      setElapsedActiveTime(BRelTime.make(elapsedTimeMillis));
      getElapsedActiveTimeNumeric().setValue(elapsedTimeMillis);
      BControlPoint parentPoint = getParentPoint();
      if (parentPoint != null)
      {
        parentPoint.execute();
      }
    }

    getElapsedActiveTimeNumeric().setStatus(BStatus.make(out.getStatus().getBits() & getPropagateFlags().getBits()));
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final Object updateLock = new Object();

  private Clock.Ticket ticket;
  private boolean lastActive;
  
  private boolean forceUpdate;

  private long lastEATUpdateTime; // Last Elapsed Active Time update Time
  
  private long lastActiveTime;
  private long elapsedTimeMillis;

  private int cosCount;
}
