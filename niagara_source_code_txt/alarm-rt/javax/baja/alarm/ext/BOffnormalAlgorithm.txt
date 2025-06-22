/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import java.util.Map;
import java.util.logging.Logger;

import javax.baja.control.BControlPoint;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BOffnormalAlgorithm is a super-class for all algorithms
 * that check for off normal (not fault) conditions.
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 33$ $Date: 6/17/10 2:27:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BOffnormalAlgorithm
  extends BAlarmAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BOffnormalAlgorithm(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:02 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOffnormalAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BOffnormalAlgorithm's parent must be a BAlarmSourceExt
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    if (parent instanceof BAlarmSourceExt)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
//  Offnormal transition checking
////////////////////////////////////////////////////////////////

  /**
   * Check for a normal / offnormal alarm transition.  Return
   * new alarm state or null if no change.
   */
  public final BAlarmState checkAlarmState(BStatusValue out, long toAlarmTimeDelay, long toNormalDelay)
  {
    return checkAlarms(out, toAlarmTimeDelay, toNormalDelay);
  }

  /**
   * Check for a normal / offnormal alarm transition.  Return
   * new alarm state or null if no change.
   * @param toAlarmTimeDelay Minimum time period that an alarm condition must exist before the object alarms.
   * @deprecated since Niagara 3.5.  Use checkAlarmState(BStatusValue, long, long) instead.
   */
  @Deprecated
  public final BAlarmState checkAlarmState(BStatusValue out, long toAlarmTimeDelay)
  {
    return checkAlarms(out, toAlarmTimeDelay, toAlarmTimeDelay);
  }

////////////////////////////////////////////////////////////////
//  Override Points
////////////////////////////////////////////////////////////////

  /**
   * Default implementation.  Always returns null indicating no change in state.
   * @param toAlarmTimeDelay Minimum time period that an alarm condition must exist before the object alarms.
   * @deprecated since Niagara 3.5.  Use checkAlarms(BStatusValue, long, long) instead.
   */
  @Deprecated
  public BAlarmState checkAlarms(BStatusValue out, long toAlarmTimeDelay)
  {
    return null;
  }

  /**
   * Default implementation.  Always returns null indicating no change in state.
   * @param toAlarmTimeDelay Minimum time period that an alarm condition must exist before the object alarms.
   * @param toNormalTimeDelay Minimum time period that a normal condition must exist before the object returns to normal.
   */
  public BAlarmState checkAlarms(BStatusValue out, long toAlarmTimeDelay, long toNormalTimeDelay)
  {
    return checkAlarms(out, toAlarmTimeDelay);
  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Facets.
   *
   * @param out The relevant control point status value
   * @param map The map.
   */
  @SuppressWarnings("rawtypes")
  public void writeAlarmData(BStatusValue out, Map map)
  {
  }

////////////////////////////////////////////////////////////////
//  Time Delay Utility Methods
////////////////////////////////////////////////////////////////

  /**********************************************
  *  Start a timer to handle alarm validation.
  **********************************************/
  protected void startTimer(long timeDelay)
  {
    endTime = Clock.ticks() + timeDelay;

    BControlPoint cp = getParentPoint();
    if (cp != null && isRunning())
    {
      ticket = Clock.schedule(cp, BRelTime.make(timeDelay), BControlPoint.execute, null);
    }
  }

  /**********************************************
  *  Cancels all timers associated with this
  *  alarm support object
  **********************************************/
  protected void cancelTimer()
  {
    endTime = -1;
    if (ticket != null)
    {
      ticket.cancel();
    }
  }

  /**********************************************
  *  Timer status function
  **********************************************/
  protected boolean isTimerExpired()
  {
    if (endTime == -1)
    {
      throw new IllegalStateException();
    }

    long now = Clock.ticks();
    if (now >= endTime)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  long endTime;
  Clock.Ticket ticket;
  protected static Logger log = Logger.getLogger("control");
}
