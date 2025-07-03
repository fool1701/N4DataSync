/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ping;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.baja.alarm.AlarmSupport;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BSourceState;
import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;

/**
 * The PingMonitor periodically calls ping on all the IPingables
 * to monitor network and device health.  PingMonitor provides
 * builtin support to generate alarms when pingables are down.
 *
 * @author    Brian Frank
 * @creation  14 Dec 01
 * @version   $Revision: 28$ $Date: 7/30/10 9:21:00 AM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
/*
 Enable the ping poll engine
 */
@NiagaraProperty(
  name = "pingEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 How often should the monitor attempt a ping if no
 other communication has taken place
 */
@NiagaraProperty(
  name = "pingFrequency",
  type = "BRelTime",
  defaultValue = "BRelTime.make(5L*60L*1000L)"
)
/*
 Should the an alarm be generated when status is
 changed to down.
 */
@NiagaraProperty(
  name = "alarmOnFailure",
  type = "boolean",
  defaultValue = "true"
)
/*
 This is the amount of time to wait after startup
 before we report ping failure alarms.  Pinging will
 still occur during this time, but alarms generated
 by a device going down will be suppressed.  It is
 useful to prevent nuisance alarming after a power failure.
 */
@NiagaraProperty(
  name = "startupAlarmDelay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(5L*60L*1000L)"
)
/*
 Specifies the maximum number of ping failures that must occur before the
 device is considered failing (down). The default is zero, which means that
 the first communication failure for the device will cause the device to be
 considered failing (down).
 @since Niagara 4.10
 */
@NiagaraProperty(
  name = "numRetriesUntilPingFail",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(null, 0, Integer.MAX_VALUE)")
)
/*
 Enable monitoring.
 */
@NiagaraAction(
  name = "enable"
)
/*
 Disable monitoring.
 */
@NiagaraAction(
  name = "disable"
)
public class BPingMonitor
  extends BComponent
  implements Runnable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ping.BPingMonitor(3145117250)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pingEnabled"

  /**
   * Slot for the {@code pingEnabled} property.
   * Enable the ping poll engine
   * @see #getPingEnabled
   * @see #setPingEnabled
   */
  public static final Property pingEnabled = newProperty(0, true, null);

  /**
   * Get the {@code pingEnabled} property.
   * Enable the ping poll engine
   * @see #pingEnabled
   */
  public boolean getPingEnabled() { return getBoolean(pingEnabled); }

  /**
   * Set the {@code pingEnabled} property.
   * Enable the ping poll engine
   * @see #pingEnabled
   */
  public void setPingEnabled(boolean v) { setBoolean(pingEnabled, v, null); }

  //endregion Property "pingEnabled"

  //region Property "pingFrequency"

  /**
   * Slot for the {@code pingFrequency} property.
   * How often should the monitor attempt a ping if no
   * other communication has taken place
   * @see #getPingFrequency
   * @see #setPingFrequency
   */
  public static final Property pingFrequency = newProperty(0, BRelTime.make(5L*60L*1000L), null);

  /**
   * Get the {@code pingFrequency} property.
   * How often should the monitor attempt a ping if no
   * other communication has taken place
   * @see #pingFrequency
   */
  public BRelTime getPingFrequency() { return (BRelTime)get(pingFrequency); }

  /**
   * Set the {@code pingFrequency} property.
   * How often should the monitor attempt a ping if no
   * other communication has taken place
   * @see #pingFrequency
   */
  public void setPingFrequency(BRelTime v) { set(pingFrequency, v, null); }

  //endregion Property "pingFrequency"

  //region Property "alarmOnFailure"

  /**
   * Slot for the {@code alarmOnFailure} property.
   * Should the an alarm be generated when status is
   * changed to down.
   * @see #getAlarmOnFailure
   * @see #setAlarmOnFailure
   */
  public static final Property alarmOnFailure = newProperty(0, true, null);

  /**
   * Get the {@code alarmOnFailure} property.
   * Should the an alarm be generated when status is
   * changed to down.
   * @see #alarmOnFailure
   */
  public boolean getAlarmOnFailure() { return getBoolean(alarmOnFailure); }

  /**
   * Set the {@code alarmOnFailure} property.
   * Should the an alarm be generated when status is
   * changed to down.
   * @see #alarmOnFailure
   */
  public void setAlarmOnFailure(boolean v) { setBoolean(alarmOnFailure, v, null); }

  //endregion Property "alarmOnFailure"

  //region Property "startupAlarmDelay"

  /**
   * Slot for the {@code startupAlarmDelay} property.
   * This is the amount of time to wait after startup
   * before we report ping failure alarms.  Pinging will
   * still occur during this time, but alarms generated
   * by a device going down will be suppressed.  It is
   * useful to prevent nuisance alarming after a power failure.
   * @see #getStartupAlarmDelay
   * @see #setStartupAlarmDelay
   */
  public static final Property startupAlarmDelay = newProperty(0, BRelTime.make(5L*60L*1000L), null);

  /**
   * Get the {@code startupAlarmDelay} property.
   * This is the amount of time to wait after startup
   * before we report ping failure alarms.  Pinging will
   * still occur during this time, but alarms generated
   * by a device going down will be suppressed.  It is
   * useful to prevent nuisance alarming after a power failure.
   * @see #startupAlarmDelay
   */
  public BRelTime getStartupAlarmDelay() { return (BRelTime)get(startupAlarmDelay); }

  /**
   * Set the {@code startupAlarmDelay} property.
   * This is the amount of time to wait after startup
   * before we report ping failure alarms.  Pinging will
   * still occur during this time, but alarms generated
   * by a device going down will be suppressed.  It is
   * useful to prevent nuisance alarming after a power failure.
   * @see #startupAlarmDelay
   */
  public void setStartupAlarmDelay(BRelTime v) { set(startupAlarmDelay, v, null); }

  //endregion Property "startupAlarmDelay"

  //region Property "numRetriesUntilPingFail"

  /**
   * Slot for the {@code numRetriesUntilPingFail} property.
   * Specifies the maximum number of ping failures that must occur before the
   * device is considered failing (down). The default is zero, which means that
   * the first communication failure for the device will cause the device to be
   * considered failing (down).
   * @since Niagara 4.10
   * @see #getNumRetriesUntilPingFail
   * @see #setNumRetriesUntilPingFail
   */
  public static final Property numRetriesUntilPingFail = newProperty(0, 0, BFacets.makeInt(null, 0, Integer.MAX_VALUE));

  /**
   * Get the {@code numRetriesUntilPingFail} property.
   * Specifies the maximum number of ping failures that must occur before the
   * device is considered failing (down). The default is zero, which means that
   * the first communication failure for the device will cause the device to be
   * considered failing (down).
   * @since Niagara 4.10
   * @see #numRetriesUntilPingFail
   */
  public int getNumRetriesUntilPingFail() { return getInt(numRetriesUntilPingFail); }

  /**
   * Set the {@code numRetriesUntilPingFail} property.
   * Specifies the maximum number of ping failures that must occur before the
   * device is considered failing (down). The default is zero, which means that
   * the first communication failure for the device will cause the device to be
   * considered failing (down).
   * @since Niagara 4.10
   * @see #numRetriesUntilPingFail
   */
  public void setNumRetriesUntilPingFail(int v) { setInt(numRetriesUntilPingFail, v, null); }

  //endregion Property "numRetriesUntilPingFail"

  //region Action "enable"

  /**
   * Slot for the {@code enable} action.
   * Enable monitoring.
   * @see #enable()
   */
  public static final Action enable = newAction(0, null);

  /**
   * Invoke the {@code enable} action.
   * Enable monitoring.
   * @see #enable
   */
  public void enable() { invoke(enable, null, null); }

  //endregion Action "enable"

  //region Action "disable"

  /**
   * Slot for the {@code disable} action.
   * Disable monitoring.
   * @see #disable()
   */
  public static final Action disable = newAction(0, null);

  /**
   * Invoke the {@code disable} action.
   * Disable monitoring.
   * @see #disable
   */
  public void disable() { invoke(disable, null, null); }

  //endregion Action "disable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPingMonitor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public BDeviceNetwork getNetwork()
  {
    return (BDeviceNetwork)getParent();
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {
    super.started();
    if (Sys.atSteadyState()) startThread();
  }

  public void atSteadyState()
    throws Exception
  {
    super.atSteadyState();
    startThread();
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
    stopThread();
  }

  void startThread()
  {
    isAlive = true;
    thread = new Thread(this, "Ping:" + getParent().getName());
    thread.start();
  }

  void stopThread()
  {
    isAlive = false;
    if (thread != null) thread.interrupt();
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning()) return;
  }

 /**
  * Get the pingables to monitor.  By default this is
  * the network and all its devices.
  */
  public BIPingable[] getPingables()
  {
    BDevice[] devices = getNetwork().getDevices();
    BIPingable[] result = new BIPingable[devices.length+1];
    result[0] = getNetwork();
    System.arraycopy(devices, 0, result, 1, devices.length);
    return result;
  }
  
////////////////////////////////////////////////////////////////
// Ping Poll
////////////////////////////////////////////////////////////////

  public void run()
  {
    // Record the startup time
    startupTicks = Clock.ticks();
    
    while(isAlive)
    {
      try
      {
        Thread.sleep(1000);
        if (!getPingEnabled()) continue;

        long freq = getPingFrequency().getMillis();
        long now = Clock.ticks();
        
        //Is it time to update the list of pingable devices?
        if(pingables == null || now > lastPingablesUpdate + freq)
        {
          lastPingablesUpdate = now;
          updatePingables();
        }

        //Check if the network or the devices need to be pinged
        for(int i=0; i<pingables.length; i++)
        {
          // short circuit if the monitor is disabled
          if (!getPingEnabled()) break;
          
          BIPingable p = pingables[i];
          if((p != null) && isPingEnabled(p))
          {
            if(!(p instanceof BDevice) || ((BDevice)p).isRunning())
            {
              checkPing(p, freq, now);
            }
          }
        }
      }
      catch(Throwable e)
      {
        if (isAlive) e.printStackTrace();
      }
    }
  }
  
  private static void checkPing(BIPingable p, long freq, long now) throws Exception
  {
    // if the elapsed time from last attempt has exceeded
    // the ping frequency, then it is time to ping again
    if (p != null && now > p.getHealth().lastAttemptTicks + freq)
    {
      p.getHealth().lastAttemptTicks = now;
      p.doPing();
    }
  }
  
  /**
   * This method is called to check if the specified pingable
   * should be pinged.  The default implementation returns
   * false if status is disabled or fault.
   */
  public boolean isPingEnabled(BIPingable pingable)
  {
    BStatus status = pingable.getStatus();
    if (status.isDisabled()) return false;
    if (status.isFault())    return false;
    return true;
  }

////////////////////////////////////////////////////////////////
// Ping
////////////////////////////////////////////////////////////////

  /**
   * Called from <code>BPingHealth.pingOk()</code>.
   * Sets BPingHealth.alarm=false.
   */
  public void pingOk(BIPingable p)
  {
    resetPingsFailed(p);
    // if was in alarm, send toNormal
    if (p.getHealth().getAlarm())
    {
      p.getHealth().setAlarm(false);
      alarmToNormal(p);
    }
  }

  /**
   * Called from <code>BPingHealth.pingFail()</code>.
   * Sets BPingHealth.alarm=true.
   */
  public void pingFail(BIPingable p)
  {
    int consecutivePingFails = incrementPingsFailed(p);
    if (getAlarmOnFailure())
    {
      // if already in alarm then skip
      if (p.getHealth().getAlarm())
      {
        return;
      }
      
      if (consecutivePingFails > getNumRetriesUntilPingFail())
      {
        // First check to see if the startup delay time
        // has elapsed.  If not, don't report the ping failure alarm.
        long delay = getStartupAlarmDelay().getMillis();
        long now = Clock.ticks();
        if (now <= startupTicks + delay)
        {
          return;
        }

        // send toOffnormal
        p.getHealth().setAlarm(true);
        alarmToOffnormal(p);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Alarming
////////////////////////////////////////////////////////////////

  /**
   * Generate the toOffnormal alarm.
   */
  void alarmToOffnormal(BIPingable p)
  {
    // notify alarm service
    boolean ackRequired = false;
    try
    {
      AlarmSupport support = getAlarmSupport(p);
      ackRequired = support.isAckRequired(BSourceState.offnormal);
      support.newOffnormalAlarm(p.getAlarmSourceInfo().makeAlarmData(BSourceState.offnormal));
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }

    // set the alarm bit and maybe the unacked bit
    int status = p.getStatus().getBits();
    status |= BStatus.ALARM;
    if (ackRequired) status |= BStatus.UNACKED_ALARM;
    p.setStatus(BStatus.make(status));
  }

  /**
   * Generate the toNormal alarm.
   */
  void alarmToNormal(BIPingable p)
  {
    // notify alarm service
    try
    {
      getAlarmSupport(p).toNormal(p.getAlarmSourceInfo().makeAlarmData(BSourceState.normal), null);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }

    // clear the alarm flag
    p.setStatus(BStatus.make(p.getStatus(), BStatus.ALARM, false));
  }

  /**
   * Update known pingable objects and reset the ping fail map to only retain currently pingable objects.
   */
  void updatePingables()
  {
    pingables = getPingables();
    pingFailsPerDeviceMap.keySet().retainAll(Arrays.asList(pingables));
  }

  /**
   * Handle the alarm ack.
   */
  BBoolean alarmAck(BIPingable p, BAlarmRecord ackRequest)
  {
    // notify alarm service
    try
    {
      getAlarmSupport(p).ackAlarm(ackRequest);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }

    // clear unacked alarm flag
    p.setStatus(BStatus.make(p.getStatus(), BStatus.UNACKED_ALARM, false));
    return BBoolean.TRUE;
  }

  /**
   * Get a cached AlarmSupport for the pingable.
   */
  AlarmSupport getAlarmSupport(BIPingable p)
  {
    AlarmSupport support = (AlarmSupport)((BObject)p).fw(Fw.GET_ALARM_SUPPORT);
    support.setAlarmClass(p.getAlarmSourceInfo().getAlarmClass());
    return support;
  }

//////////////////////////////////////////////////////////////////
//  Access / Convenience Methods
//////////////////////////////////////////////////////////////////
  /**
   * Called to increment the number of consecutive
   * ping/poll fails for this device.  Returns
   * the new number of consecutive ping/poll fails.
   * @since Niagara 4.10
   */
  private int incrementPingsFailed(BIPingable p)
  {
    return pingFailsPerDeviceMap
      .compute(p, (key, value) -> value == null ? 1 : value + 1);
  }

  /**
   * Called on a successful ping/poll for this
   * device to reset its the consecutive
   * ping/poll fail counter.
   * @since Niagara 4.10
   */
  private void resetPingsFailed(BIPingable p)
  {
    pingFailsPerDeviceMap.remove(p);
  }

////////////////////////////////////////////////////////////////
// Spy for BIPingable vs failed pings
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out) throws Exception
  {
    if (!pingFailsPerDeviceMap.isEmpty())
    {
      out.startTable(true);
      out.trTitle("Pingables currently failing", 3);
      out.w("<tr>").th("Pingable").th("Consecutive Ping Fails").th("Current Status").w("</tr>\n");
      for (Map.Entry<BIPingable, Integer> entry : pingFailsPerDeviceMap
        .entrySet())
      {
        BIPingable key = entry.getKey();
        if (key instanceof BComponent)
        {
          out.tr(((BComponent) key).toPathString(), entry.getValue(), key.getStatus());
        }
        else
        {
          out.tr(key, entry.getValue(), key.getStatus());
        }
      }
      out.endTable();
    }
    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /** Enable monitoring */
  public void doEnable() { setPingEnabled(true); }

  /** Disable monitoring */
  public void doDisable() { setPingEnabled(false); }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("monitor.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  boolean isAlive;
  
  private BIPingable[] pingables = null;
  private long lastPingablesUpdate = 0L;
  long startupTicks = 0L;
  Thread thread;
  private final Map<BIPingable, Integer> pingFailsPerDeviceMap = new ConcurrentHashMap<>();
}
