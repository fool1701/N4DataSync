/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ping;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BIcon;
import javax.baja.sys.BStruct;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BPingHealth manages the ping status of a BIPingable.  It should
 * be a child property of BIPingable.
 *
 * @author    Brian Frank
 * @creation  6 May 02
 * @version   $Revision: 9$ $Date: 1/11/11 5:06:12 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Indicates the state of the communication health.
 If the last result was ok then the pingable is
 up, otherwise it is down.
 */
@NiagaraProperty(
  name = "down",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 Indicates the alarm state of the communication health.
 If alarming is enabled and the last result was fail then the pingable
 is in alarm.
 */
@NiagaraProperty(
  name = "alarm",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 Last time the device was successfully pinged
 and we knew it was up and communicating.
 */
@NiagaraProperty(
  name = "lastOkTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT",
  flags = Flags.READONLY | Flags.NON_CRITICAL
)
/*
 Last time we attempted communication with the
 device and failed.
 */
@NiagaraProperty(
  name = "lastFailTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "lastFailCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
public class BPingHealth
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ping.BPingHealth(937782119)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "down"

  /**
   * Slot for the {@code down} property.
   * Indicates the state of the communication health.
   * If the last result was ok then the pingable is
   * up, otherwise it is down.
   * @see #getDown
   * @see #setDown
   */
  public static final Property down = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code down} property.
   * Indicates the state of the communication health.
   * If the last result was ok then the pingable is
   * up, otherwise it is down.
   * @see #down
   */
  public boolean getDown() { return getBoolean(down); }

  /**
   * Set the {@code down} property.
   * Indicates the state of the communication health.
   * If the last result was ok then the pingable is
   * up, otherwise it is down.
   * @see #down
   */
  public void setDown(boolean v) { setBoolean(down, v, null); }

  //endregion Property "down"

  //region Property "alarm"

  /**
   * Slot for the {@code alarm} property.
   * Indicates the alarm state of the communication health.
   * If alarming is enabled and the last result was fail then the pingable
   * is in alarm.
   * @see #getAlarm
   * @see #setAlarm
   */
  public static final Property alarm = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code alarm} property.
   * Indicates the alarm state of the communication health.
   * If alarming is enabled and the last result was fail then the pingable
   * is in alarm.
   * @see #alarm
   */
  public boolean getAlarm() { return getBoolean(alarm); }

  /**
   * Set the {@code alarm} property.
   * Indicates the alarm state of the communication health.
   * If alarming is enabled and the last result was fail then the pingable
   * is in alarm.
   * @see #alarm
   */
  public void setAlarm(boolean v) { setBoolean(alarm, v, null); }

  //endregion Property "alarm"

  //region Property "lastOkTime"

  /**
   * Slot for the {@code lastOkTime} property.
   * Last time the device was successfully pinged
   * and we knew it was up and communicating.
   * @see #getLastOkTime
   * @see #setLastOkTime
   */
  public static final Property lastOkTime = newProperty(Flags.READONLY | Flags.NON_CRITICAL, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code lastOkTime} property.
   * Last time the device was successfully pinged
   * and we knew it was up and communicating.
   * @see #lastOkTime
   */
  public BAbsTime getLastOkTime() { return (BAbsTime)get(lastOkTime); }

  /**
   * Set the {@code lastOkTime} property.
   * Last time the device was successfully pinged
   * and we knew it was up and communicating.
   * @see #lastOkTime
   */
  public void setLastOkTime(BAbsTime v) { set(lastOkTime, v, null); }

  //endregion Property "lastOkTime"

  //region Property "lastFailTime"

  /**
   * Slot for the {@code lastFailTime} property.
   * Last time we attempted communication with the
   * device and failed.
   * @see #getLastFailTime
   * @see #setLastFailTime
   */
  public static final Property lastFailTime = newProperty(Flags.READONLY, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code lastFailTime} property.
   * Last time we attempted communication with the
   * device and failed.
   * @see #lastFailTime
   */
  public BAbsTime getLastFailTime() { return (BAbsTime)get(lastFailTime); }

  /**
   * Set the {@code lastFailTime} property.
   * Last time we attempted communication with the
   * device and failed.
   * @see #lastFailTime
   */
  public void setLastFailTime(BAbsTime v) { set(lastFailTime, v, null); }

  //endregion Property "lastFailTime"

  //region Property "lastFailCause"

  /**
   * Slot for the {@code lastFailCause} property.
   * @see #getLastFailCause
   * @see #setLastFailCause
   */
  public static final Property lastFailCause = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code lastFailCause} property.
   * @see #lastFailCause
   */
  public String getLastFailCause() { return getString(lastFailCause); }

  /**
   * Set the {@code lastFailCause} property.
   * @see #lastFailCause
   */
  public void setLastFailCause(String v) { setString(lastFailCause, v, null); }

  //endregion Property "lastFailCause"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPingHealth.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
 
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the parent as a <code>BIPingable</code>.
   */
  public final BIPingable getParentPingable()
  {
    return (BIPingable)getParent();
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  /**
   * Return if the last failure time was more recent 
   * than then the last success time.
   */
  public boolean inFailure()
  {
    return getLastFailTime().getMillis() >= 
           getLastOkTime().getMillis();
  }

  /**
   * Called when successful communication is made.  This method
   * updates the lastOkTime and clears the down flag.
   */
  public void pingOk()
  {                         
    lastAttemptTicks = Clock.ticks();
    
    long now = System.currentTimeMillis();
    long truncated = now - (now % 60000L);
    BAbsTime t = (truncated <= (getLastFailTime().getMillis()))?BAbsTime.make(now):BAbsTime.make(truncated);
    setLastOkTime(t);
    BIPingable parent = getParentPingable();
    if (getDown())
    { 
      setDown(false); 
      parent.updateStatus();
    }
    parent.getMonitor().pingOk(parent);
  }

  /**
   * Called when communication with the pingable fails.  This method 
   * updates the lastFail properties and sets the down flag.
   */
  public void pingFail(String cause)
  {
    lastAttemptTicks = Clock.ticks();
    
    long now = System.currentTimeMillis();
    long truncated = now - (now % 60000L);
    BAbsTime t = (truncated <= (getLastOkTime().getMillis()))?BAbsTime.make(now):BAbsTime.make(truncated);
    setLastFailTime(t);
    setLastFailCause(cause == null ? "" : cause);
    BIPingable parent = getParentPingable();
    if (!getDown())
    { 
      setDown(true);
      parent.updateStatus();
    }
    
    try
    {
      parent.getMonitor().pingFail(parent);
    }
    catch(Exception e)
    { 
      // don't propagate exception if we can't access network
      // such as in a fatal fault condition
    }
  }
  
  /**
   * Called on alarm acknowledge.  Clears the unack alarm bit.
   */
  public BBoolean doAckAlarm(BAlarmRecord ackRequest)
  {                     
    BIPingable parent = getParentPingable();
    return parent.getMonitor().alarmAck(parent, ackRequest);
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////  
  
  /**
   * To string.
   */
  public String toString(Context cx)
  {
    if (inFailure())
      return "Fail [" + getLastFailTime().toString(cx) + "] " + getLastFailCause();
    else
      return "Ok [" + getLastOkTime().toString(cx) + "]";
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("monitor.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  long lastAttemptTicks = 0;
}
