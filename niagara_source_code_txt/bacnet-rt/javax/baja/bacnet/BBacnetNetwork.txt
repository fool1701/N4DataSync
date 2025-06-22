/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetOctetString;
import javax.baja.bacnet.enums.BBacnetSegmentation;
import javax.baja.bacnet.export.BLocalBacnetDevice;
import javax.baja.bacnet.io.BBacnetComm;
import javax.baja.bacnet.point.BBacnetTuningPolicy;
import javax.baja.bacnet.point.BBacnetTuningPolicyMap;
import javax.baja.bacnet.util.BBacnetWorker;
import javax.baja.bacnet.util.BIBacnetPollable;
import javax.baja.driver.history.BHistoryNetworkExt;
import javax.baja.driver.loadable.BLoadableNetwork;
import javax.baja.driver.point.BTuningPolicyMap;
import javax.baja.driver.util.BAbstractPollService;
import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;
import javax.baja.util.IFuture;

import com.tridium.bacnet.datatypes.BChangeDeviceIdConfig;
import com.tridium.bacnet.datatypes.BDeviceDiscoveryConfig;
import com.tridium.bacnet.datatypes.BTimeSynchConfig;
import com.tridium.bacnet.datatypes.BWhoHasConfig;
import com.tridium.bacnet.job.BBacnetDiscoverDevicesJob;
import com.tridium.bacnet.job.BChangeDeviceIdJob;
import com.tridium.bacnet.job.BTimeSynchJob;
import com.tridium.bacnet.job.BWhoHasJob;
import com.tridium.bacnet.stack.BBacnetPoll;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.client.BBacnetClientLayer;
import com.tridium.bacnet.stack.network.BBacnetNetworkLayer;
import com.tridium.bacnet.stack.network.BNetworkPort;
import com.tridium.bacnet.stack.server.BBacnetServerLayer;
import com.tridium.bacnet.stack.server.cov.BBacnetCovWorker;
import com.tridium.sys.license.LicenseUtil;
import com.tridium.util.ComponentTreeCursor;

/**
 * BBacnetNetwork is the base container for Bacnet communications.
 * <p>
 * It contains the BACnet communications stack, a local device which
 * displays information about our BACnet device representation, and
 * manages the worker queues for requests and writes.
 * <p>
 * All BACnet devices must be contained under this network container.
 * There may be at most one instance of BBacnetNetwork per station.
 * <p>
 * The order of callbacks during the startup sequence is as follows:
 * <pre>
 * BacnetNetwork.started()
 * BacnetStack.started()
 * BacnetClientLayer.started()
 * BacnetClientLayer.descendantsStarted()
 * BacnetServerLayer.started()
 * BacnetServerLayer.descendantsStarted()
 * BacnetTransportLayer.started()
 * BacnetTransportLayer.descendantsStarted()
 * BacnetNetworkLayer.started()
 * NetworkPort{ipPort}.started()
 * BacnetIpLinkLayer.started()
 * BacnetIpLinkLayer.descendantsStarted()
 * NetworkPort{ipPort}.descendantsStarted()
 * [NetworkPort{Xxx}.started()]
 * [BacnetXxxLinkLayer.started()]
 * [BacnetXxxLinkLayer.descendantsStarted()]
 * [NetworkPort.descendantsStarted()]
 * BacnetNetworkLayer.descendantsStarted()
 * BacnetStack.descendantsStarted()
 * [BacnetDevice1.started()]
 * [BacnetPointDeviceExt1.started()]
 * [BacnetPointDeviceExt1.descendantsStarted()]
 * [... virtual alarms schedules trendLogs config ...]
 * [BacnetDevice1.descendantsStarted()]
 * [BacnetDevice2.started()]
 * [BacnetPointDeviceExt2.started()]
 * [BacnetPointDeviceExt2.descendantsStarted()]
 * [... virtual alarms schedules trendLogs config ...]
 * [BacnetDevice2.descendantsStarted()]
 * BacnetNetwork.descendantsStarted()
 * </pre>
 *
 * @author Craig Gemmill on 12 Jan 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 The rules used to determine the configuration of histories
 that are pushed in to this device.
 */
@NiagaraProperty(
  name = "historyPolicies",
  type = "BHistoryNetworkExt",
  defaultValue = "new BHistoryNetworkExt()"
)
/*
 the worker for managing asynchronous tasks like device
 and object discovery, pings, etc.
 */
@NiagaraProperty(
  name = "worker",
  type = "BBacnetWorker",
  defaultValue = "new BBacnetWorker()",
  flags = Flags.HIDDEN
)
/*
 the worker for managing writes.
 */
@NiagaraProperty(
  name = "writeWorker",
  type = "BBacnetWorker",
  defaultValue = "new BBacnetWorker()",
  flags = Flags.HIDDEN
)
/*
 the Bacnet comm stack.
 */
@NiagaraProperty(
  name = "bacnetComm",
  type = "BBacnetComm",
  defaultValue = "new BBacnetStack()"
)
/*
 the representation of this Niagara station as a Bacnet device.
 */
@NiagaraProperty(
  name = "localDevice",
  type = "BLocalBacnetDevice",
  defaultValue = "new BLocalBacnetDevice()"
)
/*
 the map of tuning policies governing reads and writes.
 */
@NiagaraProperty(
  name = "tuningPolicies",
  type = "BTuningPolicyMap",
  defaultValue = "new BBacnetTuningPolicyMap()"
)
/*
 the worker for sending COV notifications
 */
@NiagaraProperty(
  name = "covWorker",
  type = "BBacnetCovWorker",
  defaultValue = "new BBacnetCovWorker()",
  flags = Flags.HIDDEN
)
/*
 dispatch ping requests to the network worker
 */
@NiagaraProperty(
  name = "asyncPing",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "submitDeviceManagerJob",
  parameterType = "BValue",
  defaultValue = "new BDeviceDiscoveryConfig()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "lookupDeviceById",
  parameterType = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT",
  returnType = "BBacnetDevice",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "lookupDeviceByAddress",
  parameterType = "BBacnetAddress",
  defaultValue = "BBacnetAddress.DEFAULT",
  returnType = "BBacnetDevice",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "lookupDeviceOrdById",
  parameterType = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "lookupDeviceOrdByAddress",
  parameterType = "BBacnetAddress",
  defaultValue = "BBacnetAddress.DEFAULT",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
public class BBacnetNetwork
  extends BLoadableNetwork
  implements BacnetConst, BIService
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.BBacnetNetwork(2991319719)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "historyPolicies"

  /**
   * Slot for the {@code historyPolicies} property.
   * The rules used to determine the configuration of histories
   * that are pushed in to this device.
   * @see #getHistoryPolicies
   * @see #setHistoryPolicies
   */
  public static final Property historyPolicies = newProperty(0, new BHistoryNetworkExt(), null);

  /**
   * Get the {@code historyPolicies} property.
   * The rules used to determine the configuration of histories
   * that are pushed in to this device.
   * @see #historyPolicies
   */
  public BHistoryNetworkExt getHistoryPolicies() { return (BHistoryNetworkExt)get(historyPolicies); }

  /**
   * Set the {@code historyPolicies} property.
   * The rules used to determine the configuration of histories
   * that are pushed in to this device.
   * @see #historyPolicies
   */
  public void setHistoryPolicies(BHistoryNetworkExt v) { set(historyPolicies, v, null); }

  //endregion Property "historyPolicies"

  //region Property "worker"

  /**
   * Slot for the {@code worker} property.
   * the worker for managing asynchronous tasks like device
   * and object discovery, pings, etc.
   * @see #getWorker
   * @see #setWorker
   */
  public static final Property worker = newProperty(Flags.HIDDEN, new BBacnetWorker(), null);

  /**
   * Get the {@code worker} property.
   * the worker for managing asynchronous tasks like device
   * and object discovery, pings, etc.
   * @see #worker
   */
  public BBacnetWorker getWorker() { return (BBacnetWorker)get(worker); }

  /**
   * Set the {@code worker} property.
   * the worker for managing asynchronous tasks like device
   * and object discovery, pings, etc.
   * @see #worker
   */
  public void setWorker(BBacnetWorker v) { set(worker, v, null); }

  //endregion Property "worker"

  //region Property "writeWorker"

  /**
   * Slot for the {@code writeWorker} property.
   * the worker for managing writes.
   * @see #getWriteWorker
   * @see #setWriteWorker
   */
  public static final Property writeWorker = newProperty(Flags.HIDDEN, new BBacnetWorker(), null);

  /**
   * Get the {@code writeWorker} property.
   * the worker for managing writes.
   * @see #writeWorker
   */
  public BBacnetWorker getWriteWorker() { return (BBacnetWorker)get(writeWorker); }

  /**
   * Set the {@code writeWorker} property.
   * the worker for managing writes.
   * @see #writeWorker
   */
  public void setWriteWorker(BBacnetWorker v) { set(writeWorker, v, null); }

  //endregion Property "writeWorker"

  //region Property "bacnetComm"

  /**
   * Slot for the {@code bacnetComm} property.
   * the Bacnet comm stack.
   * @see #getBacnetComm
   * @see #setBacnetComm
   */
  public static final Property bacnetComm = newProperty(0, new BBacnetStack(), null);

  /**
   * Get the {@code bacnetComm} property.
   * the Bacnet comm stack.
   * @see #bacnetComm
   */
  public BBacnetComm getBacnetComm() { return (BBacnetComm)get(bacnetComm); }

  /**
   * Set the {@code bacnetComm} property.
   * the Bacnet comm stack.
   * @see #bacnetComm
   */
  public void setBacnetComm(BBacnetComm v) { set(bacnetComm, v, null); }

  //endregion Property "bacnetComm"

  //region Property "localDevice"

  /**
   * Slot for the {@code localDevice} property.
   * the representation of this Niagara station as a Bacnet device.
   * @see #getLocalDevice
   * @see #setLocalDevice
   */
  public static final Property localDevice = newProperty(0, new BLocalBacnetDevice(), null);

  /**
   * Get the {@code localDevice} property.
   * the representation of this Niagara station as a Bacnet device.
   * @see #localDevice
   */
  public BLocalBacnetDevice getLocalDevice() { return (BLocalBacnetDevice)get(localDevice); }

  /**
   * Set the {@code localDevice} property.
   * the representation of this Niagara station as a Bacnet device.
   * @see #localDevice
   */
  public void setLocalDevice(BLocalBacnetDevice v) { set(localDevice, v, null); }

  //endregion Property "localDevice"

  //region Property "tuningPolicies"

  /**
   * Slot for the {@code tuningPolicies} property.
   * the map of tuning policies governing reads and writes.
   * @see #getTuningPolicies
   * @see #setTuningPolicies
   */
  public static final Property tuningPolicies = newProperty(0, new BBacnetTuningPolicyMap(), null);

  /**
   * Get the {@code tuningPolicies} property.
   * the map of tuning policies governing reads and writes.
   * @see #tuningPolicies
   */
  public BTuningPolicyMap getTuningPolicies() { return (BTuningPolicyMap)get(tuningPolicies); }

  /**
   * Set the {@code tuningPolicies} property.
   * the map of tuning policies governing reads and writes.
   * @see #tuningPolicies
   */
  public void setTuningPolicies(BTuningPolicyMap v) { set(tuningPolicies, v, null); }

  //endregion Property "tuningPolicies"

  //region Property "covWorker"

  /**
   * Slot for the {@code covWorker} property.
   * the worker for sending COV notifications
   * @see #getCovWorker
   * @see #setCovWorker
   */
  public static final Property covWorker = newProperty(Flags.HIDDEN, new BBacnetCovWorker(), null);

  /**
   * Get the {@code covWorker} property.
   * the worker for sending COV notifications
   * @see #covWorker
   */
  public BBacnetCovWorker getCovWorker() { return (BBacnetCovWorker)get(covWorker); }

  /**
   * Set the {@code covWorker} property.
   * the worker for sending COV notifications
   * @see #covWorker
   */
  public void setCovWorker(BBacnetCovWorker v) { set(covWorker, v, null); }

  //endregion Property "covWorker"

  //region Property "asyncPing"

  /**
   * Slot for the {@code asyncPing} property.
   * dispatch ping requests to the network worker
   * @see #getAsyncPing
   * @see #setAsyncPing
   */
  public static final Property asyncPing = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code asyncPing} property.
   * dispatch ping requests to the network worker
   * @see #asyncPing
   */
  public boolean getAsyncPing() { return getBoolean(asyncPing); }

  /**
   * Set the {@code asyncPing} property.
   * dispatch ping requests to the network worker
   * @see #asyncPing
   */
  public void setAsyncPing(boolean v) { setBoolean(asyncPing, v, null); }

  //endregion Property "asyncPing"

  //region Action "submitDeviceManagerJob"

  /**
   * Slot for the {@code submitDeviceManagerJob} action.
   * @see #submitDeviceManagerJob(BValue parameter)
   */
  public static final Action submitDeviceManagerJob = newAction(Flags.HIDDEN, new BDeviceDiscoveryConfig(), null);

  /**
   * Invoke the {@code submitDeviceManagerJob} action.
   * @see #submitDeviceManagerJob
   */
  public BOrd submitDeviceManagerJob(BValue parameter) { return (BOrd)invoke(submitDeviceManagerJob, parameter, null); }

  //endregion Action "submitDeviceManagerJob"

  //region Action "lookupDeviceById"

  /**
   * Slot for the {@code lookupDeviceById} action.
   * @see #lookupDeviceById(BBacnetObjectIdentifier parameter)
   */
  public static final Action lookupDeviceById = newAction(Flags.HIDDEN, BBacnetObjectIdentifier.DEFAULT, null);

  /**
   * Invoke the {@code lookupDeviceById} action.
   * @see #lookupDeviceById
   */
  public BBacnetDevice lookupDeviceById(BBacnetObjectIdentifier parameter) { return (BBacnetDevice)invoke(lookupDeviceById, parameter, null); }

  //endregion Action "lookupDeviceById"

  //region Action "lookupDeviceByAddress"

  /**
   * Slot for the {@code lookupDeviceByAddress} action.
   * @see #lookupDeviceByAddress(BBacnetAddress parameter)
   */
  public static final Action lookupDeviceByAddress = newAction(Flags.HIDDEN, BBacnetAddress.DEFAULT, null);

  /**
   * Invoke the {@code lookupDeviceByAddress} action.
   * @see #lookupDeviceByAddress
   */
  public BBacnetDevice lookupDeviceByAddress(BBacnetAddress parameter) { return (BBacnetDevice)invoke(lookupDeviceByAddress, parameter, null); }

  //endregion Action "lookupDeviceByAddress"

  //region Action "lookupDeviceOrdById"

  /**
   * Slot for the {@code lookupDeviceOrdById} action.
   * @see #lookupDeviceOrdById(BBacnetObjectIdentifier parameter)
   */
  public static final Action lookupDeviceOrdById = newAction(Flags.HIDDEN, BBacnetObjectIdentifier.DEFAULT, null);

  /**
   * Invoke the {@code lookupDeviceOrdById} action.
   * @see #lookupDeviceOrdById
   */
  public BOrd lookupDeviceOrdById(BBacnetObjectIdentifier parameter) { return (BOrd)invoke(lookupDeviceOrdById, parameter, null); }

  //endregion Action "lookupDeviceOrdById"

  //region Action "lookupDeviceOrdByAddress"

  /**
   * Slot for the {@code lookupDeviceOrdByAddress} action.
   * @see #lookupDeviceOrdByAddress(BBacnetAddress parameter)
   */
  public static final Action lookupDeviceOrdByAddress = newAction(Flags.HIDDEN, BBacnetAddress.DEFAULT, null);

  /**
   * Invoke the {@code lookupDeviceOrdByAddress} action.
   * @see #lookupDeviceOrdByAddress
   */
  public BOrd lookupDeviceOrdByAddress(BBacnetAddress parameter) { return (BOrd)invoke(lookupDeviceOrdByAddress, parameter, null); }

  //endregion Action "lookupDeviceOrdByAddress"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BBacnetNetwork()
  {
  }


////////////////////////////////////////////////////////////////
//  BIService
////////////////////////////////////////////////////////////////

  /**
   * Return the service types.
   */
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }

  /**
   * Service started.
   */
  @Override
  public final void serviceStarted()
  {
    bacnetService = this;

    //Force the cache to invalidate and refresh any time a BACnet Network
    //'service' is started (added) or stopped (removed). This leverages
    //that only one BACnet Network is ever allowed to exist on the Station.
    //If that principle changes, a more complex cache lifecycle would be required.
    BACNET_NETWORK = null;
    BACNET_LOCAL_DEVICE = null;

    bacnet();
    localDevice();
  }

  /**
   * Service stopped.
   */
  @Override
  public final void serviceStopped()
  {
    bacnetService = null;

    BACNET_NETWORK = null;
    BACNET_LOCAL_DEVICE = null;
  }

////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one BLocalBacnetDevice is allowed.
   *
   * @return true unless the child is a second instance of BLocalBacnetDevice.
   */
  public boolean isChildLegal(BComponent child)
  {
    return !(child instanceof BLocalBacnetDevice);
  }

  /**
   * Network started.
   */
  public void started()
    throws Exception
  {
    try
    {
      super.started();
      if (Sys.getService(TYPE) != this)
      {
        configFail("Duplicate BacnetNetwork");
        throw new IllegalStateException("Only one BacnetNetwork allowed per station!");
      }
      setUploadOnStart();
      setAndGetWriteOnFacetChange();
      setAndGetShouldSupportFaults();
      setAndGetPrivateTransferResultBlockFlag();
    }
    catch (ServiceNotFoundException e)
    {
      log.severe("BACnet Network not registered as a service!");
    }
  }

  public void changed(Property property, Context context)
  {
    super.changed(property, context);
    if (property.isDynamic())
    {
      if (property.getName().equalsIgnoreCase(UPLOAD_ON_START))
      {
        uploadOnStart = setUploadOnStart();
      }
    }
  }

  /**
   * When all of the layers have been started,
   * announce ourselves on the Bacnet internetwork.
   */
  public void descendantsStarted()
    throws Exception
  {
    super.descendantsStarted();

    // Inform the network layer that we are ready to listen to packets.
    ((BBacnetStack)getBacnetComm()).getNetwork().networkReady();

    // Announce ourselves on the Bacnet network.
    ((BBacnetStack)getBacnetComm()).getServer().iAm();

    // Let the devices know the network is ready.
    networkReady = true;
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetDevice.class))
      ((BBacnetDevice)c.get()).networkReady();
  }

  /**
   * Wait until all BACnet points and devices have had a chance to complete
   * their traffic before shutting down the comm stack.
   */
  public void descendantsStopped()
    throws Exception
  {
    super.descendantsStopped();
    ((BBacnetStack)getBacnetComm()).stopStack();
  }


////////////////////////////////////////////////////////////////
//  BDeviceNetwork
////////////////////////////////////////////////////////////////

  /**
   * The base device type for BACnet devices is BBacnetDevice.
   */
  public Type getDeviceType()
  {
    return BBacnetDevice.TYPE;
  }

  /**
   * Get the Type for DeviceFolders for this network.
   */
  public Type getDeviceFolderType()
  {
    return BBacnetDeviceFolder.TYPE;
  }

  /**
   * Filter out frozen slots which tend to be support objects
   * and not useful to display in the navigation tree.
   */
  public BINavNode[] getNavChildren()
  {
    BINavNode[] kids = super.getNavChildren();
    Array<BINavNode> acc = new Array<>(BINavNode.class);
    acc.add(getLocalDevice());
    acc.add(getBacnetComm());
    acc.add(getMonitor());
    acc.add(getTuningPolicies());
    for (int i = 0; i < kids.length; ++i)
      acc.add(kids[i]);
    return acc.trim();
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
  public final Feature getLicenseFeature()
  {
    return Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "bacnet");
  }

  /**
   * Override point for BacnetAwsNetwork, used to check
   * before invoking AWS-specific behavior.
   * Is this a BacnetAwsNetwork?
   *
   * @return false
   */
  public boolean isAws()
  {
    return false;
  }

////////////////////////////////////////////////////////////////
//  Workers
////////////////////////////////////////////////////////////////

  /**
   * Post an async action to the thread pool worker.
   */
  public IFuture postAsync(Runnable runnable)
  {
    return getWorker().post(runnable);
  }

  /**
   * Post an async action to the thread pool worker.
   */
  public IFuture postWrite(Runnable runnable)
  {
    return getWriteWorker().post(runnable);
  }


////////////////////////////////////////////////////////////////
//  Actions
////////////////////////////////////////////////////////////////

  /**
   * Submit a device manager job.
   */
  public BOrd doSubmitDeviceManagerJob(BValue arg, Context cx)
  {
    if (isFatalFault()) return null;
    Type t = arg.getType();
    if (t.is(BWhoHasConfig.TYPE))
      return new BWhoHasJob(this, (BWhoHasConfig)arg).submit(cx);
    if (t.is(BDeviceDiscoveryConfig.TYPE))
      return new BBacnetDiscoverDevicesJob(this, (BDeviceDiscoveryConfig)arg).submit(cx);
    if (t.is(BTimeSynchConfig.TYPE))
      return new BTimeSynchJob(this, (BTimeSynchConfig)arg).submit(cx);
    if (t.is(BChangeDeviceIdConfig.TYPE))
      return new BChangeDeviceIdJob(this, (BChangeDeviceIdConfig)arg).submit(cx);
    return BOrd.DEFAULT;
  }

  /**
   * Look up a device by its objectId.
   *
   * @param objectId
   * @return the <code>BBacnetDevice</code> registered with this objectId.
   */
  public BBacnetDevice doLookupDeviceById(BBacnetObjectIdentifier objectId)
  {
    // Sanity checks.
    if (objectId == null) return null;
    if (objectId.getInstanceNumber() < 0) return null;

    synchronized (this)
    {
      BOrd ord = ordByObjectId.get(objectId);
      if (ord == null) return null;
      try
      {
        BBacnetDevice dev = (BBacnetDevice)ord.get(this);
        return dev;
      }
      catch (UnresolvedException e)
      {
        ordByObjectId.remove(objectId);
        return null;
      }
    }
  }

  /**
   * Look up a device by its address.
   *
   * @param address
   * @return the <code>BBacnetDevice</code> registered with this address.
   */
  public BBacnetDevice doLookupDeviceByAddress(BBacnetAddress address)
  {
    synchronized (this)
    {
      BOrd ord = doLookupDeviceOrdByAddress(address);
      if (ord != null)
      {
        try
        {
          BBacnetDevice dev = (BBacnetDevice)ord.get(this);
          return dev;
        }
        catch (UnresolvedException e)
        {
          removeAddress(address);
        }
      }
    }
    return null;
  }

  /**
   * Look up the ord for a device by its objectId.
   *
   * @param objectId
   * @return a <code>BOrd</code> to the <code>BBacnetDevice</code> registered with this objectId.
   */
  public BOrd doLookupDeviceOrdById(BBacnetObjectIdentifier objectId)
  {
    // Sanity checks.
    if (objectId == null) return null;
    if (objectId.getInstanceNumber() < 0) return null;

    synchronized (this)
    {
      BOrd ord = ordByObjectId.get(objectId);
      return ord;
    }
  }

  /**
   * Look up the ord for a device by its address.
   *
   * @param address
   * @return a <code>BOrd</code> for the <code>BBacnetDevice</code> registered with this address.
   */
  public BOrd doLookupDeviceOrdByAddress(BBacnetAddress address)
  {
    // Sanity checks.
    if (address == null) return null;
    if (address.equals(BBacnetAddress.DEFAULT)) return null;

    BOrd ord = null;
    synchronized (this)
    {
      int networkNumber = address.getNetworkNumber();
      Map<BBacnetOctetString, BOrd> network = ordByAddress.get(networkNumber);
      if (network != null)
      {
        ord = network.get(address.getMacAddress());
      }
    }
    return ord;
  }


////////////////////////////////////////////////////////////////
// Device management
////////////////////////////////////////////////////////////////

  public synchronized void registerDevice(BBacnetDevice device)
  {
    BOrd ordInSession = device.getOrdInSession();
    ordByObjectId.put(device.getObjectId(), ordInSession);

    BBacnetAddress address = device.getAddress();
    int networkNumber = address.getNetworkNumber();
    Map<BBacnetOctetString, BOrd> network = ordByAddress.get(networkNumber);
    if (network == null)
    {
      network = new HashMap<>();
      ordByAddress.put(networkNumber, network);
    }
    network.put(address.getMacAddress(), ordInSession);
  }

  public synchronized void unregisterDevice(BBacnetDevice device)
  {
    removeFromMaps(device.getOrdInSession());
  }

  public synchronized void updateDevice(BBacnetDevice device)
  {
    unregisterDevice(device);
    registerDevice(device);
  }

  /**
   * Look up a device by its objectId.
   *
   * @param objectId
   * @return the <code>BBacnetDevice</code> registered with this objectId.
   * @see #doLookupDeviceById(BBacnetObjectIdentifier)
   * @deprecated as of 3.5.  Use the <code>lookupDeviceByObjectId</code> action
   * or its implementation, <code>doLookupDeviceByObjectId(objectId)</code>.
   */
  @Deprecated
  public BBacnetDevice lookupDevice(BBacnetObjectIdentifier objectId)
  {
    return doLookupDeviceById(objectId);
  }

  /**
   * Look up a device by its address.
   *
   * @param address
   * @return the <code>BBacnetDevice</code> registered with this address.
   * @see #doLookupDeviceByAddress(BBacnetAddress)
   * @deprecated as of 3.5.  Use the <code>lookupDeviceByAddress</code> action
   * or its implementation, <code>doLookupDeviceByAddress(address)</code>.
   */
  @Deprecated
  public BBacnetDevice lookupDevice(BBacnetAddress address)
  {
    return doLookupDeviceByAddress(address);
  }

  /**
   * Update the device information.
   * The objectId is used to look up the <code>BBacnetDevice</code>, and
   * its parameters are then updated with the new values.
   * <p>
   * This is generally done as the result of receiving an I-Am message
   * from the device.
   *
   * @param objectId
   * @param address
   * @param maxAPDULengthAccepted
   * @param segmentationSupported
   * @param vendorId
   */
  public void updateDeviceInfo(BBacnetObjectIdentifier objectId,
                               BBacnetAddress address,
                               int maxAPDULengthAccepted,
                               BBacnetSegmentation segmentationSupported,
                               int vendorId)
  {
    if (log.isLoggable(Level.FINE)) log.fine("Updating device data for {" + objectId + "}");
    BBacnetDevice device = doLookupDeviceById(objectId);
    if (device != null)
    {
      if (log.isLoggable(Level.FINE))
        log.fine("Updating device data for " + device.getName() + " {" + objectId + "}");
      device.updateDeviceInfo(objectId,
        address,
        maxAPDULengthAccepted,
        segmentationSupported,
        vendorId,
        network().getPortByNetwork(address.getNetworkNumber()));
    }
  }

  /**
   * Get an array containing all the BBacnetDevice
   * children of this network.
   *
   * @return an array of <code>BBacnetDevice</code>s.
   */
  public BBacnetDevice[] getDeviceList()
  {
    Array<BBacnetDevice> ret = new Array<>(BBacnetDevice.class);
    ComponentTreeCursor c = new ComponentTreeCursor(this, null);
    while (c.next(BBacnetDevice.class))
    {
      ret.add((BBacnetDevice)c.get());
    }
    return ret.trim();
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get our object identifier.
   */
  public BBacnetObjectIdentifier getObjectId()
  {
    return getLocalDevice().getObjectId();
  }

  /**
   * Get the Bacnet Service.
   */
  public static BBacnetNetwork bacnet()
  {
    //NOTE: We are not concerned about thread safety here, or about singleton lifecycle,
    //      just that eventually the network request does not cause the Sys.getService()
    //      lookups to occur repeatedly in a Niagara Station where this value is well established.
    if (BACNET_NETWORK == null)
    {
      BBacnetNetwork tempBacnetNetwork = null;

      try
      {
        if (Sys.getStation() == null)
        {
          // BacnetWbService tool uses this method for retrieving the network.
          tempBacnetNetwork = bacnetService;
        }
        else
        {
          // Regular station access uses this method.
          tempBacnetNetwork = (BBacnetNetwork)(Sys.getService(TYPE));
        }
      }
      catch (ServiceNotFoundException e)
      {
        log.log(Level.SEVERE, "Unable to locate Bacnet Service!", e);
      }

      //NOTE: Acknowledge the inspection that this is not a thread safe mechanism for initializing
      //      this value. The risk here is that the value is not synchronized between 2 threads and
      //      that we might initialize the value twice by entering the above 'if' on 2 separate threads,
      //      which is wasteful (however, it is a lot better than initializing it every time!).
      //      We recognize that this risk exists yet we will do nothing to correct it since we want access
      //      to this value to remain un-hindered by volatile behavior or synchronization that might
      //      affect performance.
      //noinspection NonThreadSafeLazyInitialization
      BACNET_NETWORK = tempBacnetNetwork;
    }

    return BACNET_NETWORK;
  }

  /**
   * Get the local Bacnet device.
   */
  public static BLocalBacnetDevice localDevice()
  {
    //NOTE: We are not concerned about thread safety here, or about singleton lifecycle,
    //      just that eventually the local device request does not cause the Sys.getService()
    //      lookups to occur repeatedly in a Niagara Station where this value is well established.
    if (BACNET_LOCAL_DEVICE == null)
    {
      BLocalBacnetDevice tempLocalDevice = null;

      try
      {
        if (Sys.getStation() == null)
        {
          // BacnetWbService tool uses this method for retrieving the network.
          if (bacnetService != null)
          {
            tempLocalDevice = bacnetService.getLocalDevice();
          }
          else
          {
            throw new ServiceNotFoundException("BacnetNetwork service not initialized!");
          }
        }
        else
        {
          // Regular station access uses this method.
          tempLocalDevice = ((BBacnetNetwork)(Sys.getService(TYPE))).getLocalDevice();
        }
      }
      catch (ServiceNotFoundException e)
      {
        log.log(Level.SEVERE, "Unable to locate Bacnet Service!", e);
      }

      //NOTE: Acknowledge the inspection that this is not a thread safe mechanism for initializing
      //      this value. The risk here is that the value is not synchronized between 2 threads and
      //      that we might initialize the value twice by entering the above 'if' on 2 separate threads,
      //      which is wasteful (however, it is a lot better than initializing it every time!).
      //      We recognize that this risk exists yet we will do nothing to correct it since we want access
      //      to this value to remain un-hindered by volatile behavior or synchronization that might
      //      affect performance.
      //noinspection NonThreadSafeLazyInitialization
      BACNET_LOCAL_DEVICE = tempLocalDevice;
    }

    return BACNET_LOCAL_DEVICE;
  }

  public boolean isNetworkReady()
  {
    return networkReady;
  }

  public BAbstractPollService getPollService(BIBacnetPollable pollable)
  {
    return poll(pollable.device().getAddress().getNetworkNumber());
  }

  public void tuningChanged(BBacnetTuningPolicy policy, Context cx)
  {
    BBacnetDevice[] devices = getDeviceList();
    for (int i = 0; i < devices.length; i++)
      devices[i].tuningChanged(policy, cx);
  }


  public BBoolean uploadOnStart()
  {
    if (uploadOnStart == null)
      uploadOnStart = setUploadOnStart();

    return uploadOnStart;
  }

  private BBoolean setUploadOnStart()
  {
    BValue value = get(UPLOAD_ON_START);
    BBoolean uploadOnStart = BBoolean.TRUE;
    if (value == null)
    {
      add(UPLOAD_ON_START, uploadOnStart);
    }
    else if (value instanceof BBoolean)
    {
      uploadOnStart = (BBoolean)value;
    }
    return uploadOnStart;
  }
  public BBoolean setAndGetWriteOnFacetChange()
  {
    BValue value = get(WRITE_ON_FACET_CHANGE);
    BBoolean writeOnFacetChange = BBoolean.TRUE;
    if(value == null)
    {
      add(WRITE_ON_FACET_CHANGE, writeOnFacetChange, Flags.HIDDEN);
    }else if(value instanceof BBoolean)
    {
      writeOnFacetChange = (BBoolean) value;
    }
    return writeOnFacetChange;
  }

  public boolean setAndGetShouldSupportFaults()
  {
    BValue value = get(SHOULD_SUPPORT_FAULTS_MULTI_STATE);
    BBoolean toSupportFaults = BBoolean.TRUE;
    if(value == null)
    {
      add(SHOULD_SUPPORT_FAULTS_MULTI_STATE, toSupportFaults , Flags.HIDDEN);
    }
    else if(value instanceof BBoolean)
    {
      toSupportFaults = (BBoolean) value;
    }
    return toSupportFaults.getBoolean();
  }

  public boolean setAndGetPrivateTransferResultBlockFlag()
  {
    BValue value = get(PRIVATE_TRANSFER_RESULT_BLOCK);
    BBoolean privateTransferResultBlockFlag = BBoolean.TRUE;
    if(value == null)
    {
      add(PRIVATE_TRANSFER_RESULT_BLOCK, privateTransferResultBlockFlag, Flags.HIDDEN);
    }
    else if(value instanceof BBoolean)
    {
      privateTransferResultBlockFlag = (BBoolean) value;
    }
    return privateTransferResultBlockFlag.getBoolean();
  }

////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////

  BBacnetClientLayer client()
  {
    return ((BBacnetStack)getBacnetComm()).getClient();
  }

  BBacnetServerLayer server()
  {
    return ((BBacnetStack)getBacnetComm()).getServer();
  }

  BBacnetNetworkLayer network()
  {
    return ((BBacnetStack)getBacnetComm()).getNetwork();
  }

  final BBacnetPoll poll(int networkNumber)
  {
//    BNetworkPort port = network().getPortByNetwork(networkNumber);
    BNetworkPort port = network().getPortByDNET(networkNumber);
    // FIXX!! Safety valve - make a guess that we'll use IP
    if (port == null) port = network().getIpPort();

//    log.message("BacnetNetwork.getPollService:No port found for DNET "+networkNumber+"; defaulting to base B/IP port (dnet "+port.getNetworkNumber());
    return port.getPollService();
  }

  private void removeAddress(BBacnetAddress address)
  {
    if (address == null) return;
    if (address.equals(BBacnetAddress.DEFAULT)) return;

    synchronized (this)
    {
      int networkNumber = address.getNetworkNumber();
      Map<BBacnetOctetString, BOrd> network = ordByAddress.get(networkNumber);
      if (network != null)
      {
        network.remove(address.getMacAddress());
        if (network.isEmpty())
        {
          ordByAddress.remove(networkNumber);
        }
      }
    }
  }

  private synchronized void removeFromMaps(BOrd ord)
  {
    ordByObjectId.values().remove(ord);

    Iterator<Map.Entry<Integer, Map<BBacnetOctetString, BOrd>>> entries = ordByAddress.entrySet().iterator();
    while (entries.hasNext())
    {
      Map<BBacnetOctetString, BOrd> network = entries.next().getValue();
      network.values().remove(ord);
      if (network.isEmpty())
      {
        entries.remove();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the agent list.
   */
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.remove("driver:DeviceManager");
    agents.toBottom("bacnetEDE:EdeBacnetDeviceManager");
    return agents;
  }


////////////////////////////////////////////////////////////////
//  Spy
////////////////////////////////////////////////////////////////

  private int hashtableSize(Hashtable<BBacnetObjectIdentifier, BOrd> t)
  {
    int vsize = 0;
    int ksize = 0;
    Enumeration<BOrd> ee = t.elements();
    while (ee.hasMoreElements())
    {
      vsize++;
      ee.nextElement();
    }
    Enumeration<BBacnetObjectIdentifier> ek = t.keys();
    while (ek.hasMoreElements())
    {
      ksize++;
      ek.nextElement();
    }
    if (ksize != vsize)
    {
      log.warning("HASHTABLE SIZE MISMATCH: ksize=" + ksize + "; vsize=" + vsize);
    }
    return vsize;
  }

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetNetwork", 2);

    out.prop("networkReady", networkReady);
    out.prop("bacnetService", bacnetService);
    out.prop("bacnet()", bacnet());
    if (isRunning())
    {
      BComponent c = Sys.getService(TYPE);
      out.prop("service", c);
    }
    out.prop("this", this);

    synchronized (this)
    {
      out.trTitle("ordByObjectId reported size:" + ordByObjectId.size()
        + "; actual size:" + hashtableSize(ordByObjectId), 2);
      Enumeration<BBacnetObjectIdentifier> e = ordByObjectId.keys();
      if (ordByObjectId.size() < 1000)
      {
        while (e.hasMoreElements())
        {
          BBacnetObjectIdentifier k = e.nextElement();
          out.prop("  " + k, ordByObjectId.get(k));
        }
      }
      out.trTitle("ordByAddress network size:" + ordByAddress.size(), 2);
    } // synch
    out.endProps();
  }

  public String toString(Context cx)
  {
    return super.toString(cx) + getHandleOrd();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static Type[] serviceTypes = new Type[] { TYPE };
  public static final String UPLOAD_ON_START = "uploadOnStart";
  public static final String WRITE_ON_FACET_CHANGE = "writeOnFacetChange";
  public static final String SHOULD_SUPPORT_FAULTS_MULTI_STATE = "shouldSupportFaultForMultiState";
  public static final String PRIVATE_TRANSFER_RESULT_BLOCK = "privateTransferResultBlockFlag";

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private boolean networkReady = false;
  private BBoolean uploadOnStart = null;

  //Workbench reference
  private static BBacnetNetwork bacnetService = null;

  //Retain references to commonly used objects to avoid service lookup
  private static BBacnetNetwork BACNET_NETWORK = null;
  private static BLocalBacnetDevice BACNET_LOCAL_DEVICE = null;

  protected static final Logger log = Logger.getLogger("bacnet");

  private Hashtable<BBacnetObjectIdentifier, BOrd> ordByObjectId = new Hashtable<>();
  //Map of integer network numbers to a map of (octet strings to BOrd)
  private Map<Integer, Map<BBacnetOctetString, BOrd>> ordByAddress = new HashMap<>();

////////////////////////////////////////////////////////////////
// Static Initialization
////////////////////////////////////////////////////////////////

  static
  {
    // Make sure the unit database is loaded
    UnitDatabase.getDefault();
  }
}
