/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver;

import java.util.logging.Logger;

import javax.baja.alarm.AlarmSupport;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmSourceInfo;
import javax.baja.driver.ping.BIPingable;
import javax.baja.driver.ping.BPingHealth;
import javax.baja.driver.ping.BPingMonitor;
import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;
import javax.baja.util.IFuture;

import com.tridium.sys.metrics.BISubLicenseable;
import com.tridium.sys.metrics.Metrics;
import com.tridium.sys.resource.ResourceReport;
import com.tridium.sys.schema.Fw;

/**
 * BDevice models a device plugged into the host computer,
 * either through a bus or network connection.
 *
 * @author    Brian Frank
 * @creation  17 Oct 01
 * @version   $Revision: 51$ $Date: 6/1/05 2:50:18 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Status of the device.  This property should
 never be set directly.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
/*
 Enabled is used to manually disable all communication with the device.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Provides a short message why the network is in fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Indicates communication health status.
 */
@NiagaraProperty(
  name = "health",
  type = "BPingHealth",
  defaultValue = "new BPingHealth()",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "alarmSourceInfo",
  type = "BAlarmSourceInfo",
  defaultValue = "initAlarmSourceInfo()"
)
/*
 Perform a ping test of the device.
 */
@NiagaraAction(
  name = "ping",
  flags = Flags.ASYNC
)
@NiagaraAction(
  name = "ackAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  returnType = "BBoolean",
  flags = Flags.HIDDEN
)
public abstract class BDevice
  extends BComponent
  implements BIStatus, BIPingable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.BDevice(2915480636)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Status of the device.  This property should
   * never be set directly.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * Status of the device.  This property should
   * never be set directly.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * Status of the device.  This property should
   * never be set directly.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * Enabled is used to manually disable all communication with the device.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * Enabled is used to manually disable all communication with the device.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * Enabled is used to manually disable all communication with the device.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a short message why the network is in fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a short message why the network is in fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a short message why the network is in fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "health"

  /**
   * Slot for the {@code health} property.
   * Indicates communication health status.
   * @see #getHealth
   * @see #setHealth
   */
  public static final Property health = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, new BPingHealth(), null);

  /**
   * Get the {@code health} property.
   * Indicates communication health status.
   * @see #health
   */
  public BPingHealth getHealth() { return (BPingHealth)get(health); }

  /**
   * Set the {@code health} property.
   * Indicates communication health status.
   * @see #health
   */
  public void setHealth(BPingHealth v) { set(health, v, null); }

  //endregion Property "health"

  //region Property "alarmSourceInfo"

  /**
   * Slot for the {@code alarmSourceInfo} property.
   * @see #getAlarmSourceInfo
   * @see #setAlarmSourceInfo
   */
  public static final Property alarmSourceInfo = newProperty(0, initAlarmSourceInfo(), null);

  /**
   * Get the {@code alarmSourceInfo} property.
   * @see #alarmSourceInfo
   */
  public BAlarmSourceInfo getAlarmSourceInfo() { return (BAlarmSourceInfo)get(alarmSourceInfo); }

  /**
   * Set the {@code alarmSourceInfo} property.
   * @see #alarmSourceInfo
   */
  public void setAlarmSourceInfo(BAlarmSourceInfo v) { set(alarmSourceInfo, v, null); }

  //endregion Property "alarmSourceInfo"

  //region Action "ping"

  /**
   * Slot for the {@code ping} action.
   * Perform a ping test of the device.
   * @see #ping()
   */
  public static final Action ping = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code ping} action.
   * Perform a ping test of the device.
   * @see #ping
   */
  public void ping() { invoke(ping, null, null); }

  //endregion Action "ping"

  //region Action "ackAlarm"

  /**
   * Slot for the {@code ackAlarm} action.
   * @see #ackAlarm(BAlarmRecord parameter)
   */
  public static final Action ackAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code ackAlarm} action.
   * @see #ackAlarm
   */
  public BBoolean ackAlarm(BAlarmRecord parameter) { return (BBoolean)invoke(ackAlarm, parameter, null); }

  //endregion Action "ackAlarm"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the Type of the parent network.
   */
  public abstract Type getNetworkType();

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the parent as a BDeviceNetwork.  Throw an exception
   * if this device is not running or has a fatal fault.
   */
  public final BDeviceNetwork getNetwork()
  {
    if (network != null) return network;
    if (!isRunning()) throw new NotRunningException();
    throw new IllegalStateException(getFaultCause());
  }


  /**
   * Get an array containing all the BDeviceExt
   * children of this device.
   */
  public final BDeviceExt[] getDeviceExts()
  {
    return getChildren(BDeviceExt.class);
  }

////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Return true if the device is down.  A device is down when
   * <code>pingFail()</code> has been called  more recently than
   * <code>pinkOk()</code>. A device is also automatically marked
   * down if the network is down.  Refer to either the network or
   * the device <code>health.lastFailCause</code> for the down
   * reason.
   */
  public final boolean isDown()
  {
    return getStatus().isDown();
  }

  /**
   * Return true if the device is disabled.  A device is disabled if
   * the user has manually set either the network's or this device's
   * enabled property to false.
   */
  public final boolean isDisabled()
  {
    return getStatus().isDisabled();
  }

  /**
   * Return true if the device is in fault.  A device is in fault if
   * either a fatal fault was detected or if <code>configFail()</code>
   * has been called more recently than <code>configOk()</code>.  A
   * device is also automatically marked in fault if the network is
   * in fault.  Refer to either the network's or the device's
   * <code>faultCause</code> property for the fault reason.
   */
  public final boolean isFault()
  {
    return getStatus().isFault();
  }

  /**
   * Recompute the device status and let child
   * extensions know if something has changed.
   */
  @Override
  public final void updateStatus()
  {
    // compute new status...
    int newStatus = getStatus().getBits();
    BStatus network = (this.network == null) ? BStatus.ok : this.network.getStatus();

    // ... disabled bit
    if (!getEnabled() || network.isDisabled())
      newStatus |= BStatus.DISABLED;
    else
      newStatus &= ~BStatus.DISABLED;

    // ... down bit
    if (getHealth().getDown()  || network.isDown())
      newStatus |= BStatus.DOWN;
    else
      newStatus &= ~BStatus.DOWN;

    // ... fault bit
    if (fatalFault || configFault || network.isFault())
      newStatus |= BStatus.FAULT;
    else
      newStatus &= ~BStatus.FAULT;

    // short circuit if nothing has changed since last time
    if (oldStatus == newStatus) return;
    setStatus(BStatus.make(newStatus));
    oldStatus = newStatus;

    // notify extensions
    BDeviceExt[] exts = getDeviceExts();
    for(int i=0; i<exts.length; ++i)
    {
      try { exts[i].updateStatus(); } catch(Throwable e) { e.printStackTrace(); }
    }
  }

////////////////////////////////////////////////////////////////
// Fault
////////////////////////////////////////////////////////////////

  /**
   * Return true if the device detected a fatal fault.
   * Fatal faults cannot be recovered until the device
   * is restarted.  Fatal faults trump config faults.
   * Fatal device faults include licensing failures and
   * invalid parentage.
   */
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  /**
   * Clear the configuration fault status.  The device may
   * remain in fault if the network is in fault or if the
   * device has fatal faults.
   */
  public final void configOk()
  {
    // clear config fault flag
    configFault = false;

    // fatal faults always trump
    if (fatalFault) return;

    // update props
    setFaultCause("");
    updateStatus();
  }

  /**
   * Set the device into configuration fault.  If the device
   * was previously not in fault, then this sets the entire
   * device into fault.
   */
  public final void configFail(String cause)
  {
    // set config fault flag
    configFault = true;

    // fatal faults always trump
    if (fatalFault) return;

    // update props
    setFaultCause(cause);
    updateStatus();
  }

  /**
   * Set the device into the fatal fault condition.  Unlike
   * configFail(), the fatal fault condition cannot be cleared
   * until station restart.
   */
  public final void configFatal(String cause)
  {
    fatalFault = true;
    setFaultCause(cause);
    updateStatus();
  }

  /**
   * This method checks for fatal faults built into the framework.
   */
  private void checkFatalFault(String badGroups)
  {
    // check capacity
    if (badGroups != null)
    {
      fatalFault = true;
      getLogger().severe("Exceeded device limit for " + badGroups);
      setFaultCause ("Exceeded device limit for " + badGroups);
      return;
    }

    BDeviceNetwork network = null;

    // short circuit if already in fatal fault
    if (fatalFault) return;

    // find network
    BComplex parent = getParent();
    while(parent != null)
    {
      if (parent instanceof BDeviceNetwork)
        { network = (BDeviceNetwork)parent; break; }
      parent = parent.getParent();
    }

    // check mounted in network
    if (network == null)
    {
      fatalFault = true;
      setFaultCause("Not under DeviceNetwork");
      return;
    }

    // check network type
    if (!network.getType().is(getNetworkType()))
    {
      fatalFault = true;
      setFaultCause("Parent DeviceNetwork " + network.getType() + " is not " + getNetworkType());
      return;
    }

    // check network's fatal fault
    if (network.isFatalFault())
    {
      fatalFault = true;
      setFaultCause("Network fault: " + network.getFaultCause());
      return;
    }

    if (getType().is(network.getDeviceType())) // 6/1/05 S. Hoye added this device type check
    {
      // check licensed device count
      Object licenseFault = network.fw(Fw.CHECK_LICENSE_LIMIT,
                                       BISubLicenseable.getLicenseKey(this, "device.limit"),
                                       this, null, null);
      if (licenseFault != null)
      {
        fatalFault = true;
        setFaultCause(licenseFault.toString());
        return;
      }
    }

    // no fatal faults
    this.network = network;
    setFaultCause("");
  }

////////////////////////////////////////////////////////////////
// IPingable
////////////////////////////////////////////////////////////////

  /**
   * Callback to post an asynchronous ping request.
   */
  protected abstract IFuture postPing();

  /**
   * Implementation of ping.
   */
  @Override
  public abstract void doPing()
    throws Exception;

  /**
   * Get the network's monitor.
   */
  @Override
  public BPingMonitor getMonitor()
  {
    return getNetwork().getMonitor();
  }

  /**
   * Called when <code>doPing()</code> is successful.  May also
   * be called when any successful communication occurs.  This
   * method is routed to <code>getHealth().pingOk()</code>.
   */
  @Override
  public void pingOk()
  {
    getHealth().pingOk();
  }

  /**
   * Called when <code>doPing()</code> fails.  This method is
   * routed to <code>getHealth().pingFail()</code>.
   */
  @Override
  public void pingFail(String cause)
  {
    getHealth().pingFail(cause);
  }

  /**
   * Called on alarm acknowledge.  This method is routed
   * to <code>getHealth().doAckAlarm(ackRequest)</code>
   */
  @Override
  public BBoolean doAckAlarm(BAlarmRecord ackRequest)
  {
    return getHealth().doAckAlarm(ackRequest);
  }

  /**
   * Callback to post an async action.  Subclasses should call super.
   */
  @Override
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(ping))
    {
      if (getStatus().isDisabled())
      {
        return null;
      }
      return postPing();
    }
    return super.post(action, arg, cx);
  }

////////////////////////////////////////////////////////////////
// Framework Implementation
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED:              fwStarted(); break;
      case Fw.DESCENDANTS_STARTED:  fwDescendantsStarted(); break;
      case Fw.DESCENDANTS_STOPPED:  fwDescendantsStopped(); break;
      case Fw.CHANGED:              fwChanged((Property)a); break;
      case Fw.RR:                   ((ResourceReport)a).add("device", 5000); break;
      case Fw.GET_ALARM_SUPPORT:    return alarmSupport;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    checkFatalFault(Metrics.incrementDevice(this));
  }

  private void fwDescendantsStopped()
  {
    network = null;
  }

  private void fwDescendantsStarted()
  {
    updateStatus();
  }

  private void fwChanged(Property prop)
  {
    if (!isRunning()) return;

    if (prop == enabled)
      updateStatus();
  }

////////////////////////////////////////////////////////////////
// Logging
////////////////////////////////////////////////////////////////

  /**
   * Get the log named "{networkLog}.{deviceName}".
   *
   * @deprecated As of Niagara 4.0, replaced by {@link #getLogger()}
   */
  @Deprecated
  public Log getLog()
  {
    return Log.getLog(getLogger().getName());
  }

  /**
   * Get the logger name "{networkLog}.{deviceName}".
   *
   * @return the Logger named {networkLog}.{deviceName}
   */
  public Logger getLogger()
  {
    if (log == null)
    {
      try
      {
        log = Logger.getLogger(getNetwork().getLogger().getName() + "." + getName());
      }
      catch (Exception e)
      {
        log = Logger.getLogger(getType().getTypeName());
      }
    }
    return log;
  }

  static BAlarmSourceInfo initAlarmSourceInfo()
  {
    BAlarmSourceInfo asi = new BAlarmSourceInfo();
    asi.setSourceName(BFormat.make("%parent.parent.displayName% %parent.displayName%"));
    asi.setToOffnormalText(BFormat.make("%lexicon(driver:pingFail)%"));
    asi.setToNormalText(BFormat.make("%lexicon(driver:pingSuccess)%"));
    return asi;
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  protected static final BIcon icon = BIcon.std("device.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Logger log;
  private int oldStatus = 0;
  private BDeviceNetwork network;
  private boolean fatalFault;
  private boolean configFault;
  private final AlarmSupport alarmSupport = new AlarmSupport(this, "");
}
