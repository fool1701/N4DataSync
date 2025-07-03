/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.alarm.AlarmSupport;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmSourceInfo;
import javax.baja.driver.ping.BIPingable;
import javax.baja.driver.ping.BPingHealth;
import javax.baja.driver.ping.BPingMonitor;
import javax.baja.license.BILicensed;
import javax.baja.license.Feature;
import javax.baja.log.Log;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;
import javax.baja.util.IFuture;

import com.tridium.sys.metrics.BISubLicenseable;
import com.tridium.sys.metrics.Metrics;
import com.tridium.sys.resource.ResourceReport;
import com.tridium.sys.schema.Fw;
import com.tridium.util.PxUtil;

/**
 * BDeviceNetwork is the abstract base class for drivers
 * which model a network of BDevices.
 *
 * @author    Brian Frank
 * @creation  17 Oct 01
 * @version   $Revision: 67$ $Date: 5/19/10 5:06:21 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Status of the entire network.  This property
 should never be set directly.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Used to set the entire network out of service and
 disable all communication with child devices.
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
 Manages the ping status.
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
 The ping monitor periodically pings each device
 to monitor communication health.
 */
@NiagaraProperty(
  name = "monitor",
  type = "BPingMonitor",
  defaultValue = "new BPingMonitor()"
)
/*
 Perform a ping test of the network.
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
public abstract class BDeviceNetwork
  extends BComponent
  implements BIDeviceFolder, BIStatus, BIPingable, BILicensed
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.BDeviceNetwork(966621284)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Status of the entire network.  This property
   * should never be set directly.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * Status of the entire network.  This property
   * should never be set directly.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * Status of the entire network.  This property
   * should never be set directly.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * Used to set the entire network out of service and
   * disable all communication with child devices.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * Used to set the entire network out of service and
   * disable all communication with child devices.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * Used to set the entire network out of service and
   * disable all communication with child devices.
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
   * Manages the ping status.
   * @see #getHealth
   * @see #setHealth
   */
  public static final Property health = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, new BPingHealth(), null);

  /**
   * Get the {@code health} property.
   * Manages the ping status.
   * @see #health
   */
  public BPingHealth getHealth() { return (BPingHealth)get(health); }

  /**
   * Set the {@code health} property.
   * Manages the ping status.
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

  //region Property "monitor"

  /**
   * Slot for the {@code monitor} property.
   * The ping monitor periodically pings each device
   * to monitor communication health.
   * @see #getMonitor
   * @see #setMonitor
   */
  public static final Property monitor = newProperty(0, new BPingMonitor(), null);

  /**
   * Get the {@code monitor} property.
   * The ping monitor periodically pings each device
   * to monitor communication health.
   * @see #monitor
   */
  public BPingMonitor getMonitor() { return (BPingMonitor)get(monitor); }

  /**
   * Set the {@code monitor} property.
   * The ping monitor periodically pings each device
   * to monitor communication health.
   * @see #monitor
   */
  public void setMonitor(BPingMonitor v) { set(monitor, v, null); }

  //endregion Property "monitor"

  //region Action "ping"

  /**
   * Slot for the {@code ping} action.
   * Perform a ping test of the network.
   * @see #ping()
   */
  public static final Action ping = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code ping} action.
   * Perform a ping test of the network.
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
  public static final Type TYPE = Sys.loadType(BDeviceNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the Type which all devices for this
   * network must extend.
   */
  
  public abstract Type getDeviceType();

  /**
   * Get the Type for DeviceFolders for this network.
   */
  public abstract Type getDeviceFolderType();

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return this.
   */
  public final BDeviceNetwork getNetwork()
  {
    return this;
  }

  /**
   * Get an array containing all the BNetworkExt
   * children of this device.
   */
  public final BNetworkExt[] getNetworkExts()
  {
    return getChildren(BNetworkExt.class);
  }

  /**
   * Get an array containing all the BDevice
   * descendants of this network component.
   */
  public BDevice[] getDevices()
  {
    List<BDevice> list = getBDeviceList();
    return list.toArray(new BDevice[list.size()]);
  }
  
  /**
   * Get a List containing all the BDevice
   * descendants of this network component.
   *
   * @return List of BDevice descendants.
   */
  public List<BDevice> getBDeviceList()
  {
    List<BDevice> list = new ArrayList<BDevice>();
    getDevices(this, getDeviceType(), list);
    return list;
  }

  private void getDevices(BComponent comp, Type deviceType, List<BDevice> list)
  {
    // use cursor so we don't taking out any locks
    SlotCursor<Property> cursor = comp.loadSlots().getProperties();
    while(cursor.nextComponent())
    {
      BComponent kid = cursor.get().asComponent();
      if (kid.getType().is(deviceType))
        list.add((BDevice) kid);
      else
        getDevices(kid, deviceType, list);
    }
  }

  /**
   * Return true if all the descendant components are started.
   */
  public final boolean isDescendantsStarted()
  {
    return descendantsStarted;
  }

////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Return true if the entire network is down.  A network
   * is down when <code>pingFail()</code> has been called
   * more recently than <code>pinkOk()</code>.  Refer to
   * <code>health.lastFailCause</code> for the down reason.
   */
  public final boolean isDown()
  {
    return getStatus().isDown();
  }

  /**
   * Return true if the entire network is disabled.  A
   * network is disabled if the user has manually set
   * the enabled property to false.
   */
  public final boolean isDisabled()
  {
    // 3/16/05 S. Hoye added a check for the 'Enabled' property to
    // handle the case where the status hasn't been updated yet during startup.
    return !getEnabled() || getStatus().isDisabled();
  }

  /**
   * Return true if the entire network is in fault.  A network
   * is in fault if either a fatal fault was detected or if
   * <code>configFail()</code> has been called more recently
   * than <code>configOk()</code>.  Refer to <code>faultCause</code>
   * for the fault reason.
   */
  public final boolean isFault()
  {
    return getStatus().isFault();
  }

  /**
   * Recompute the network status and let child
   * devices know if something has changed.
   */
  public final void updateStatus()
  {
    // compute new status...
    int newStatus = getStatus().getBits();

    // ...disabled bit
    if (!getEnabled())
      newStatus |= BStatus.DISABLED;
    else
      newStatus &= ~BStatus.DISABLED;

    // ...down bit
    if (getHealth().getDown())
      newStatus |= BStatus.DOWN;
    else
      newStatus &= ~BStatus.DOWN;

    // .. fault bit
    if (fatalFault | configFault)
      newStatus |= BStatus.FAULT;
    else
      newStatus &= ~BStatus.FAULT;

    // short circuit if nothing has changed
    if (newStatus == oldStatus) return;
    setStatus(BStatus.make(newStatus));
    oldStatus = newStatus;

    // notify extensions
    BNetworkExt[] exts = getNetworkExts();
    for(int i=0; i<exts.length; ++i)
    {
      try { exts[i].updateStatus(); } catch(Throwable e) { e.printStackTrace(); }
    }

    // notify devices
    BDevice[] devices = getDevices();
    for(int i=0; i<devices.length; ++i)
    {
      try { devices[i].updateStatus(); } catch(Throwable e) { e.printStackTrace(); }
    }
  }

////////////////////////////////////////////////////////////////
// Fault
////////////////////////////////////////////////////////////////

  /**
   * Return true if the network detected a fatal fault.
   * Fatal faults cannot be recovered until the network
   * is restarted.  Fatal faults trump config faults.
   * Fatal network faults include licensing failures.
   */
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  /**
   * Clear the configuration fault status.  If there are no fatal
   * faults then clear the entire network's fault status, otherwise
   * the network remains in fault.
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
   * Set the network into configuration fault.  If the network was
   * previously not in fault, then this sets the entire network
   * into fault.
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
   * Set the network into the fatal fault condition.  Unlike
   * configFail(), the fatal fault condition cannot be cleared
   * until station restart.
   */
  public final void configFatal(String cause)
  {
    fatalFault = true;
    setFaultCause(cause);
    updateStatus();
  }

////////////////////////////////////////////////////////////////
// Licensing
////////////////////////////////////////////////////////////////

  /**
   * If this driver is to be licensed using the standard licensing
   * mechanism then override this method to return the Feature or
   * return null for no license checks.  Convention is that the
   * vendor and feature name matches the declaring module.
   */
  public Feature getLicenseFeature()
  {
    return null;
  }

  /**
   * Implement the license check.
   */
  private void checkLicense(String badGroups)
  {
    try
    {
      // check capacity
      if (badGroups != null)
      {
        fatalFault = true;
        getLogger().severe("Exceeded network limit for " + badGroups);
        setFaultCause ("Exceeded network limit for " + badGroups);
        return;
      }

      // get the license Feature
      Feature feature = getLicenseFeature();
      if (feature == null) return;

      // do basic check to see if driver even works
      feature.check();

      // use the feature to map to a global limit pool
      // so that you can't by-pass limits by using multiple
      // network instances in the same station
      String globalKey = feature.getVendorName() + ":" + feature.getFeatureName();
      globalKey = TextUtil.toLowerCase(globalKey);
      limits = globalLimits.get(globalKey);
      if (limits != null) return;

      // this is the first pass for this specific feature,
      // so we need to create a new HashMap for the global pool
      limits = new HashMap<>();
      globalLimits.put(globalKey, limits);

      // map all the *.limit properties to LicenseLimit itels
      String[] keys = feature.list();
      for(int i=0; i<keys.length; ++i)
      {
        // process all *.limit properties
        String key = keys[i];
        if (!key.endsWith(".limit")) continue;

        // parse limit
        String val = feature.get(key);
        int limit = Integer.MAX_VALUE;
        if (val != null && !TextUtil.toLowerCase(val).equals("none"))
          limit = Integer.parseInt(val);

        // store in a hashtable
        LicenseLimit lic = new LicenseLimit();
        lic.key   = key;
        lic.used  = 0.0;
        lic.limit = limit;
        limits.put(key, lic);
      }
    }
    catch(Exception e)
    {
      fatalFault = true;
      getLogger().log(Level.SEVERE, "Unlicensed: " + toPathString(), e);
      setFaultCause("Unlicensed: " + e);
    }
  }

  /**
   * Get the count for the current key
   */
  private double getLicenseCount(String key)
  {
    if (limits == null) return 0;
    synchronized(limits)
    {
      LicenseLimit lic = limits.get(key);
      // if not specified, consider it used as of 0
      if (lic == null)
        return 0.0;

      return lic.used;
    }
  }

  /**
   * Increment the limit counter and return null if the specified
   * limit is ok, otherwise return the fault cause message.
   */
  private String checkLicenseLimit(String key, Object obj)
  {
    if (limits == null) return null;
    synchronized(limits)
    {
      LicenseLimit lic = limits.get(key);

      // if not specified, consider it a limit of 0
      if (lic == null) return "Unlicensed: " + key;

      // increment used and check if we have exceeded capacity
      BObject bObj = obj instanceof BObject ? (BObject)obj : null;
      lic.used += BISubLicenseable.getLicenseLimitIncrement(bObj);
      if (lic.used - lic.limit > EPSILON)
        return "Exceeded " + lic.key + " of " + lic.limit;

      // everything is a-okay
      return null;
    }
  }

  static class LicenseLimit
  {
    String key;
    int limit;
    double used;
  }

////////////////////////////////////////////////////////////////
// IPingable
////////////////////////////////////////////////////////////////

  /**
   * Callback to post an asynchronous ping request.
   */
  protected IFuture postPing()
  {
    try { doPing(); } catch(Exception e) { e.printStackTrace(); }
    return null;
  }

  /**
   * Implementation of ping.
   */
  
  public void doPing()
    throws Exception
  {
    pingOk();
  }

  /**
   * Called when <code>doPing()</code> is successful.  May also
   * be called when any successful communication occurs.  This
   * method is routed to <code>getHealth().pingOk()</code>.
   */
 
  public void pingOk()
  {
    getHealth().pingOk();
  }

  /**
   * Called when <code>doPing()</code> fails.  This method is
   * routed to <code>getHealth().pingFail()</code>.
   */
 
  public void pingFail(String cause)
  {
    getHealth().pingFail(cause);
  }

  /**
   * Called on alarm acknowledge.  This method is routed
   * to <code>getHealth().doAckAlarm(ackRequest)</code>
   */
 
  public BBoolean doAckAlarm(BAlarmRecord ackRequest)
  {
    return getHealth().doAckAlarm(ackRequest);
  }

  /**
   * Callback to post an async action.  Subclasses should call super.
   */
 
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(ping)) return postPing();
    return super.post(action, arg, cx);
  }

////////////////////////////////////////////////////////////////
// Framework Implementation
////////////////////////////////////////////////////////////////

 
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED:              fwStarted(); break;
      case Fw.DESCENDANTS_STARTED:  fwDescendantsStarted(); break;
      case Fw.CHANGED:              fwChanged((Property)a); break;
      case Fw.CHECK_LICENSE_LIMIT:  return checkLicenseLimit((String)a, b);
      case Fw.GET_LICENSE_COUNT:    // Starting in Niagara 4.10, the used license counters
                                    // are doubles instead of ints.  Since Fw.GET_LICENSE_COUNT
                                    // is also used by BAbstractService, and the original
                                    // definition for it returned an int, we'll just round
                                    // UP to the nearest int value for the result here. If
                                    // precision of the double count was just slightly above a whole
                                    // number, we'll use the EPSILON to get us back under
                                    // the whole number so the round up works the way we want.
                                    // As of Niagara 4.10, this operation does not seem to be
                                    // called by any framework code.
                                    return (int)Math.ceil(getLicenseCount((String)a) - EPSILON);
      case Fw.RR:                   ((ResourceReport)a).add("network", 100000); break;
      case Fw.GET_ALARM_SUPPORT:    return alarmSupport;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    checkLicense(Metrics.incrementNetwork(this));
    updateStatus();     // do update status to incorporate enabled bit           
  }

  private void fwDescendantsStarted()
  {
    updateStatus();
    descendantsStarted = true;
  }

  private void fwChanged(Property prop)
  {
    if (!isRunning()) return;

    if (prop.equals(enabled))
      updateStatus();
  }

////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////

  /**
   * Get the log for the network, the name of this log
   * is used for naming many of the sublogs such as
   * <code>BDevice.getLog()</code>.
   *
   * @deprecated As of Niagara 4.0, replaced by {@link #getLogger()}
   */
  @Deprecated
  public Log getLog()
  {
    return Log.getLog(getLogger().getName());
  }

  /**
   * Get the logger for the network, the name of this log
   * is used for naming many of the sublogs such as
   * <code>BDevice.getLog()</code>.
   */
  public Logger getLogger()
  {
    if (log == null)
      log = Logger.getLogger(getType().getModule().getModuleName());
    return log;
  }

 
  public void spy(SpyWriter out)
    throws Exception
  {
    if (limits != null)
    {
      out.startTable(true);
      out.trTitle("License Limits", 3);
      out.w("<tr>").th("Feature").th("Used").th("Limit").w("</tr>\n");
      Iterator<LicenseLimit> it = limits.values().iterator();
      while(it.hasNext())
      {
        LicenseLimit lic = it.next();
        String limit = lic.limit == Integer.MAX_VALUE ? "none" : ""+lic.limit;
        out.tr(lic.key, new DecimalFormat("#0.#").format(lic.used), limit);
      }
      out.endTable();
    }

    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Filter out frozen slots which tend to be support objects
   * and not useful to display in the navigation tree.
   */
 
  public BINavNode[] getNavChildren()
  {
    BINavNode[] kids = super.getNavChildren();
    Array<BINavNode> acc = new Array<>(BINavNode.class);
    for(int i=0; i<kids.length; ++i)
    {
      BComponent kid = (BComponent)kids[i];
      if (kid.getPropertyInParent().isFrozen()) continue;
      acc.add(kid);
    }
    return acc.trim();
  }

  protected static BAlarmSourceInfo initAlarmSourceInfo()
  {
    BAlarmSourceInfo asi = new BAlarmSourceInfo();
    asi.setSourceName(BFormat.make("%parent.displayName%"));
    asi.setToOffnormalText(BFormat.make("%lexicon(driver:pingFail)%"));
    asi.setToNormalText(BFormat.make("%lexicon(driver:pingSuccess)%"));
    return asi;
  }

  /**
   * Get the agent list.  Add DeviceManager if one
   * not already registered.
   */
 
  public AgentList getAgents(Context cx)
  {
    TypeInfo deviceManager = Sys.getRegistry().getType("driver:DeviceManager");
    AgentList agents = super.getAgents(cx);

    for(int i=0; i<agents.size(); ++i)
      if (agents.get(i).getAgentType().is(deviceManager))
        return agents;

    agents.add(deviceManager.getAgentInfo());
    return PxUtil.movePxViewsToTop(agents);
  }

  /**
   * Get the icon.
   */
 
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/deviceNetwork.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static HashMap<String, HashMap<String, LicenseLimit>> globalLimits = new HashMap<>();
  private static final double EPSILON = 0.00001d;

  private Logger log;
  private int oldStatus = 0;
  private boolean fatalFault;
  private boolean configFault;
  private boolean descendantsStarted;
  private HashMap<String, LicenseLimit> limits;
  private final AlarmSupport alarmSupport  = new AlarmSupport(this, "");
}
