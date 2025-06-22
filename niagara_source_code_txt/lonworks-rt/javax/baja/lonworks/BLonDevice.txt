/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import java.util.Vector;
import java.util.logging.*;

import javax.baja.agent.AgentList;
import javax.baja.control.BControlPoint;
import javax.baja.driver.BDevice;
import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.lonworks.datatypes.*;
import javax.baja.lonworks.enums.BLonNodeState;
import javax.baja.lonworks.enums.BLonObjectRequestEnum;
import javax.baja.lonworks.enums.BLonSnvtType;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.proxy.BLonPointDeviceExt;
import javax.baja.lonworks.proxy.BLonPointFolder;
import javax.baja.lonworks.proxy.BLonProxyExt;
import javax.baja.lonworks.util.LonFile;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.*;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.lonworks.Lon;
import com.tridium.lonworks.device.BDownloadJob;
import com.tridium.lonworks.device.BUploadJob;
import com.tridium.lonworks.device.DeviceFacets;
import com.tridium.lonworks.device.NvDev;
import com.tridium.lonworks.device.NvDev.SaveNv;
import com.tridium.lonworks.local.BPseudoNvContainer;
import com.tridium.lonworks.netmessages.NetMessages;
import com.tridium.lonworks.netmgmt.BChangeNvTypeAction;
import com.tridium.lonworks.util.Neuron;
import com.tridium.lonworks.util.NmUtil;

/**
 * BLonDevice is the base class to represent a physical lonworks device.
 * Adds support for polling, lonLinks and file access for support of
 * configuration properties.
 * <p>
 * @author    Robert Adams
 * @creation  06 Dec 00
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:42 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Contains data needed to represent devices' specific neuron chip.
 */
@NiagaraProperty(
  name = "deviceData",
  type = "BDeviceData",
  defaultValue = "new BDeviceData()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "points",
  type = "BLonPointDeviceExt",
  defaultValue = "new BLonPointDeviceExt()"
)
/*
 Linkable input for message tags
 */
@NiagaraProperty(
  name = "messageIn",
  type = "BMessageTag",
  defaultValue = "new BMessageTag()",
  flags = Flags.FAN_IN
)
/*
 Upload reads data from the physical device.
 */
@NiagaraAction(
  name = "upload",
  parameterType = "BUploadParameters",
  defaultValue = "new BUploadParameters()"
)
/*
 Download writes data to the physical device.
 */
@NiagaraAction(
  name = "download",
  parameterType = "BDownloadParameters",
  defaultValue = "new BDownloadParameters()"
)
/*
 reset device
 */
@NiagaraAction(
  name = "reset"
)
/*
 resolve all proxy links to target component
 */
@NiagaraAction(
  name = "renewProxies",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "initImport",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "closeImport",
  flags = Flags.HIDDEN
)
public class BLonDevice
  extends  BDevice
  implements BINvContainer,BILonLoadable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLonDevice(3498323775)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceData"

  /**
   * Slot for the {@code deviceData} property.
   * Contains data needed to represent devices' specific neuron chip.
   * @see #getDeviceData
   * @see #setDeviceData
   */
  public static final Property deviceData = newProperty(Flags.SUMMARY, new BDeviceData(), null);

  /**
   * Get the {@code deviceData} property.
   * Contains data needed to represent devices' specific neuron chip.
   * @see #deviceData
   */
  public BDeviceData getDeviceData() { return (BDeviceData)get(deviceData); }

  /**
   * Set the {@code deviceData} property.
   * Contains data needed to represent devices' specific neuron chip.
   * @see #deviceData
   */
  public void setDeviceData(BDeviceData v) { set(deviceData, v, null); }

  //endregion Property "deviceData"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BLonPointDeviceExt(), null);

  /**
   * Get the {@code points} property.
   * @see #points
   */
  public BLonPointDeviceExt getPoints() { return (BLonPointDeviceExt)get(points); }

  /**
   * Set the {@code points} property.
   * @see #points
   */
  public void setPoints(BLonPointDeviceExt v) { set(points, v, null); }

  //endregion Property "points"

  //region Property "messageIn"

  /**
   * Slot for the {@code messageIn} property.
   * Linkable input for message tags
   * @see #getMessageIn
   * @see #setMessageIn
   */
  public static final Property messageIn = newProperty(Flags.FAN_IN, new BMessageTag(), null);

  /**
   * Get the {@code messageIn} property.
   * Linkable input for message tags
   * @see #messageIn
   */
  public BMessageTag getMessageIn() { return (BMessageTag)get(messageIn); }

  /**
   * Set the {@code messageIn} property.
   * Linkable input for message tags
   * @see #messageIn
   */
  public void setMessageIn(BMessageTag v) { set(messageIn, v, null); }

  //endregion Property "messageIn"

  //region Action "upload"

  /**
   * Slot for the {@code upload} action.
   * Upload reads data from the physical device.
   * @see #upload(BUploadParameters parameter)
   */
  public static final Action upload = newAction(0, new BUploadParameters(), null);

  /**
   * Invoke the {@code upload} action.
   * Upload reads data from the physical device.
   * @see #upload
   */
  public void upload(BUploadParameters parameter) { invoke(upload, parameter, null); }

  //endregion Action "upload"

  //region Action "download"

  /**
   * Slot for the {@code download} action.
   * Download writes data to the physical device.
   * @see #download(BDownloadParameters parameter)
   */
  public static final Action download = newAction(0, new BDownloadParameters(), null);

  /**
   * Invoke the {@code download} action.
   * Download writes data to the physical device.
   * @see #download
   */
  public void download(BDownloadParameters parameter) { invoke(download, parameter, null); }

  //endregion Action "download"

  //region Action "reset"

  /**
   * Slot for the {@code reset} action.
   * reset device
   * @see #reset()
   */
  public static final Action reset = newAction(0, null);

  /**
   * Invoke the {@code reset} action.
   * reset device
   * @see #reset
   */
  public void reset() { invoke(reset, null, null); }

  //endregion Action "reset"

  //region Action "renewProxies"

  /**
   * Slot for the {@code renewProxies} action.
   * resolve all proxy links to target component
   * @see #renewProxies()
   */
  public static final Action renewProxies = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code renewProxies} action.
   * resolve all proxy links to target component
   * @see #renewProxies
   */
  public void renewProxies() { invoke(renewProxies, null, null); }

  //endregion Action "renewProxies"

  //region Action "initImport"

  /**
   * Slot for the {@code initImport} action.
   * @see #initImport()
   */
  public static final Action initImport = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code initImport} action.
   * @see #initImport
   */
  public void initImport() { invoke(initImport, null, null); }

  //endregion Action "initImport"

  //region Action "closeImport"

  /**
   * Slot for the {@code closeImport} action.
   * @see #closeImport()
   */
  public static final Action closeImport = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code closeImport} action.
   * @see #closeImport
   */
  public void closeImport() { invoke(closeImport, null, null); }

  //endregion Action "closeImport"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////
//  LonDevice api
////////////////////////////////////////////////////////////
  /** Does this device require authentication of network management messages. */
  public final boolean authenticate() { return getDeviceData().getAuthenticate(); }

  /**
   *  Is this the local lonDevice.  <p>
   *  @return Returns false. Override in BLocalLonDevice to return true.
   */
  public boolean isLocal()      { return false; }

  /** Indicates if programId may be changed during commissioning.
   *   @return Returns false. */
  public boolean programIdChanges() { return false; }

  /** Return the neuronId address for this device. */
  public final BNeuronId getNeuronIdAddress()
    { return getDeviceData().getNeuronId(); }

  /** Return the subnet node address for this device. */
  public final BSubnetNode getSubnetNodeAddress()
    { return getDeviceData().getSubnetNodeId(); }

  /** Is this devices nodeState configOnline? */
  public final boolean isConfigOnline()
    { return getDeviceData().getNodeState()==BLonNodeState.configOnline; }

  /** Is this devices nodeState configOnline? */
  public final boolean isConfigOffline()
    { return getDeviceData().getNodeState()==BLonNodeState.configOffline; }

  /** Is this devices nodeState in a configured state? */
  public final boolean isConfigured()
    { return isConfigOnline() || isConfigOffline(); }

  /**
   * Does this device use extended network management messages.
   */
  public boolean isExtended() { return getDeviceData().isExtended(); }


  public int getWorkingDomain() { return  getDeviceData().getWorkingDomain(); }

////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////
  /**
   * Get the Type of the parent network.
   */
  public Type getNetworkType() {return BLonNetwork.TYPE; }


  /**
   * Register device with network.
   * <p>To override use lonDeviceInit()
   */
  public void started()
    throws Exception
  {
    super.started();

    BLonNetwork net = lonNetwork();
    if(!net.isServiceRunning() || isFatalFault())
    {
      return;
    }

    if(lonComm()==null)
    {
      configFail("No LonComm stack available");
      log().warning("No LonComm stack available. Fix problems and restart station.");
      return;
    }

    // move device facets from device slot to deviceData slot
    DeviceFacets.moveDeviceFacets(this);

    lonDeviceInit();
    net.addressManager().registerLonDevice(this);
    lonDeviceStarted();
    changeableNvCheck();
  }

  public void descendantsStarted()
      throws Exception
  {
    super.descendantsStarted();

    // Make sure fault cause set correctly
    updateFaultCause();
  }
  
  /**
   * Do an initial ping().
   * To override use lonDeviceAtSteadyState()
   */
  public final void atSteadyState()
  {
    if(!lonNetwork().isServiceRunning() || isFatalFault()) return;

    // Do a ping at startup in case monitor is disabled.
    ping();

    lonDeviceAtSteadyState();
  }

  /** Unregister device with network. */
  public final void stopped()
    throws Exception
  {
    try
    {
      net = null;
      log = null;
      clearFiles();
      synchronized(nvSync)
      {
        nvList = null;
        staleNvList = true;
      }

      lonNetwork().addressManager().unregisterLonDevice(this);
      if(!lonNetwork().isServiceRunning() || isFatalFault()) return;

      lonDeviceStopped();
    }
    finally
    {
      super.stopped();
    }
  }

  /** Override for renamed(). */
  public void renamed(Property property, String oldName, Context context)
  {
    if(property.getType().is(BLonComponent.TYPE))
    {
      lonComponentRenamed(this, property.getName(), oldName, context);
    }
  }

  // Update any BLonProxyExt target
  static void lonComponentRenamed(BLonDevice dev, String newName, String oldName, Context context)
  {
    // fix any proxies with oldName as target
    BControlPoint[] cps = dev.getLonProxies();
    for(int i=0 ; i<cps.length ; i++)
    {
      BLonProxyExt lp = (BLonProxyExt)cps[i].getProxyExt();
      if(lp.getTargetComp().equals(oldName))
      {
        lp.setTargetComp(newName);
        lp.renew();
      }
    }
  }

  /** Update domain table. */
  public void updateDomainTable()
  {
    try
    {
      NmUtil.updateDomainTable(lonNetwork().getLonNetmgmt(), this);
    }
    catch(Throwable e)
    {
      log().log(Level.SEVERE,"Unable to update domain table in " + getDisplayName(null),e); 
    }
  }

  /** Update local node state. */
  public void updateNodeState()
  {
    try
    {
      BDeviceData dd = getDeviceData();
      BLonNodeState nState = dd.getNodeState();

      // Validate change before updating device.
      if( (nState == BLonNodeState.applicationless) &&
          (dd.getHosted() || isLocal()) )
      {
        try { set(BDeviceData.nodeState, NmUtil.getDeviceState(this), AddressManager.noDeviceChange); } catch(LonException e){}
        throw new BajaRuntimeException("Changing this device to applicationless state will render it inoperable.");
      }
      // Attempt to change state
      if(Lon.d()) NmUtil.setDeviceState(this, nState );
    }
    catch(Throwable e)
    {
      log().log(Level.SEVERE,"Unable to update nodeState in " + getDisplayName(null),e); 
    }
  }

  /** Override for changed(). */
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);

    if(!isRunning()) return;

    if(prop==status) statusChanged();

    if(prop.getType().is(BINetworkVariable.TYPE))
    {
      synchronized(nvSync) { staleNvList = true; }
    }
  }

  // When device status changed must notify the nvs tuning
  // to allow device up/enable/start triggered writes.
  private void statusChanged()
  {
    updateFaultCause();

    BINetworkVariable[] nvs = getNvList();
    for(int i=0 ; i<nvs.length ; i++)
    {
      if((nvs[i]==null) || !nvs[i].isNetworkVariable()) continue;
      ((BNetworkVariable)nvs[i]).getTuning().transition();
    }
  }

  protected void updateFaultCause()
  {
     // If in fault because network in fault borrow it's fault cause
    if(isFault() && (getFaultCause().length()==0) && getLonNetwork().isFault())
      setFaultCause("Network fault: " + getLonNetwork().getFaultCause());
    if(!isFault())
      setFaultCause("");
  }

  /**
   * If new nvi,nvo,nci force regeneration of nv list.
   * If lonLink call lonLinkAdded() on destination nv.
   */
  public void added(Property prop, Context context)
  {
    super.added(prop,context);

    if(prop.getType().is(BINetworkVariable.TYPE))
    {
      synchronized(nvSync) { staleNvList = true; }
    }
    else if(prop.getType().is(BLonLink.TYPE))
    {
      BLonLink lnk = (BLonLink)get(prop);
      if(isRunning() && !lnk.getMessageTag()) lnk.getDestinationNv().lonLinkAdded();
    }
  }

  /**
   * Override to add special handling for BLonlinks
   */
  public void checkRemove(Property prop, Context context)
  {
    snv = NvDev.checkRemove(this, prop, context);
    super.checkRemove(prop, context);
  }

  /**
   * Override to add special handling for BINetworkVariables and BLonlinks
   */
  public void removed(Property prop, BValue value, Context context)
  {
    super.removed(prop,value,context);

    if(prop.getType().is(BINetworkVariable.TYPE))
    {
     synchronized(nvSync) { staleNvList = true; }
    }
    NvDev.removed(this, snv, prop, value, context);
    snv = null;
  }

  // Container object for data need by NvDev to handle removed links
  SaveNv snv = null;

  /**
   * Called when a knob is activated.
   */
  public final void knobAdded(Knob knob, Context context)
  {
    NvDev.knobAdded(this, knob, context);
  }

  /**
   * Called when a knob is deactivated.
   */
  public final void knobRemoved(Knob knob, Context context)
  {
    NvDev.knobRemoved(this, knob, context);
  }

  /**
   * Convenience for <code>getNetwork().postAsync(r)</code>.
   */
  public IFuture postAsync(Runnable r)
  {
    return ((BLonNetwork)getNetwork()).postAsync(r);
  }

  /**
   * Post a ping Invocation.
   */
  protected IFuture postPing()
  {
    return postAsync(new Invocation(this, ping, null, null));
  }

  /**
   * Callback for processing upLoad.
   */
  public final void doUpload(BUploadParameters params, Context cx)
    throws Exception
  {
    checkState();
    checkUpload();
    new BUploadJob(this,params,cx).submit(cx);
  }

  /**
   * Callback for processing downLoad.
   */
  public final void doDownload(BDownloadParameters params, Context cx)
    throws Exception
  {
    checkState();
    checkDownload();
    new BDownloadJob(this,params,cx).submit(cx);
  }

  /** Throw BajaRuntimeException if device is down or disabled. */
  public void checkState()
  {
    if(isDown())
      throw new LocalizableRuntimeException("lonworks", "check.down");
    if(isDisabled())
      throw new LocalizableRuntimeException("lonworks", "check.disabled");
    if(isFault())
      throw new LocalizableRuntimeException("lonworks", "check.fault");
  }

  /**
   * Override to do initial setup before a BConfigParameter or BNetworkConfig write.
   * This callback will not be made if write is due to download operation.
   */
  public void beginConfigWrite() {}
  /**
   * Override to do cleanup after a BConfigParameter or BNetworkConfig write.
   * This callback will not be made if write is due to download operation.
   */
  public void endConfigWrite() {}


  /**
   * Override to show ChangeableNvManager view in devices with
   * ProgramId indicating device has changeable nvs.
   */
  public AgentList getAgents(Context cx)
  {
    AgentList alist = super.getAgents(cx);
    if(getAction("ChangeNvTypeAction")==null)
       alist.remove("lonworks:ChangeableNvManager");

    return NvDev.fixWireSheet(alist,cx);
  }

////////////////////////////////////////////////////////////
//  Actions
////////////////////////////////////////////////////////////

  /** Send reset command to device.  */
  public void doReset()
  {
    try
    {
      if(Lon.d()) NmUtil.resetNode(this);
    }
    catch(Throwable e)
    {
      log().log(Level.SEVERE,"Exception in LonDevice.reset()", e); 
    }
  }

  public void doRenewProxies()
  {
    BControlPoint[] cps = getLonProxies();

    for(int i=0 ; i<cps.length ; i++)
    {
      try
      {
        BLonProxyExt lp = (BLonProxyExt)cps[i].getProxyExt();
        // Attempt to resolve proxy - update point facets from device facets
        lp.renew(true);
      }
      // ignore fail, point will be set in fault - user must resolve problem
      catch(Throwable e){ System.out.println(e);e.printStackTrace();}
    }
  }

  public void doInitImport()
  {
    // Get all the links from/to this device that need to be evaluated after
    BINvContainer[] nvCntrs = getNvContainers();
    Array<BLink> to = new Array<>(BLink.class);
    for(int i = 0 ; i < nvCntrs.length ; i++)
    {
      BLink[] lnks = nvCntrs[i].asComponent().getLinks();
      for(int ndx = 0 ; ndx < lnks.length ; ndx++)
      {
        if( !(lnks[ndx].getType().is(BLonLink.TYPE)) ) continue;
        to.add(lnks[ndx]);
      }
      Knob[] knobs = nvCntrs[i].asComponent().getKnobs();
      for(int ndx = 0 ; ndx < knobs.length ; ndx++)
      {
        BLink lnk = knobs[ndx].getLink();
        if( !(lnk.getType().is(BLonLink.TYPE)) ) continue;
        to.add(lnk);
      }
    }
    toLnks = to.trim();
  }
  BLink[] toLnks = null;

  public void doCloseImport()
  {
    doRenewProxies();
    if(toLnks==null) return;

    // Deactivate and activate all the links to determine which are valid.
    // Delete links that can not be activated.
    for(int i=0 ; i<toLnks.length ; i++)
    {
      toLnks[i].deactivate();
      try
      {
        toLnks[i].activate();
      }
      catch(Throwable e)
      {
        if(toLnks[i].getParent()!=null)
          ((BComponent)toLnks[i].getParent()).remove(toLnks[i]);
      }
    }
    toLnks = null;
  }


////////////////////////////////////////////////////////////
//  Override points
////////////////////////////////////////////////////////////

  /**
   * Override point for subclasses to do initialization before lonDevice
   * is registered.
   * <p> Only called if LonNetwork has been started.
   */
  protected void lonDeviceInit() { }

  /**
   * Override point for subclasses to do startup functions after device
   * is registered. Overrides {@code started()->deviceStarted()}
   * <p> Only called if LonNetwork has been started.
   */
  protected void lonDeviceStarted() { }

  /**
   * Override point for subclasses to do startup functions after device
   *  station has reached steady state. Overrides atSteadyState();
   * <p> Only called if LonNetwork has been started.
   */
  protected void lonDeviceAtSteadyState() { }

  /**
   * Override point for subclasses to do cleanup functions before device
   * is unregistered as the results of call to deviceStopped().
   * Overrides {@code stopped()->deviceStopped()}
   * <p> Only called if LonNetwork has been started.
   */
  protected void lonDeviceStopped() { }


  /** Receive notification of change to device data. */
  public void deviceDataChanged(Property prop, Context context)
  {
    // If progamId changes check if changed from/to device with changeable nvs.
    if(prop.equals(BDeviceData.programId))
    {
      changeableNvCheck();
    }
    // When the subnet/node changes any locally bound nvs must be re-registered.
    if(prop.equals(BDeviceData.subnetNodeId))
    {
      updateLocallyBoundNvs();
    }

    // New device - force update of max message size
    if(prop == BDeviceData.neuronId) clearMaxMessageLength();

  }

  public void updateLocalState()
  {

  }

  /**
   * Override point for lon devices to perform any actions needed before
   * the device is commissioned or replaced.
   * <p>
   * Added in 3.6.32
   */
  public void beginCommission() {}
  /** Override point for lon devices to perform any actions needed after
   *  the device is commissioned or replaced. */
  public void postCommission() {}

  // If device has changeableNvs make sure it has BChangeNvTypeAction
  private void changeableNvCheck()
  {
    if(isLocal()) return;

    boolean hasChangeAction = (getAction("ChangeNvTypeAction")!=null);
    if(hasChangeableNvs())
    {
      if(hasChangeAction)  return;
      add("ChangeNvTypeAction", new BChangeNvTypeAction(), Flags.HIDDEN);
    }
    else
    {
      if(hasChangeAction) remove("ChangeNvTypeAction");
    }
  }


  private void updateLocallyBoundNvs()
  {
    BINetworkVariable[] nvs = getNetworkVariables();
    for(int i=0 ; i<nvs.length ; i++)
    {
      if(nvs[i] != null && nvs[i].isNetworkVariable())
        ((BNetworkVariable)nvs[i]).reregisterSelector();
    }
  }

  /** Check programId to determine if this device has changeable nvs.  */
  public boolean hasChangeableNvs()
  {
  	return getDeviceData().getProgramId().hasChangeableNvs();
  }

  /** Callback during bind process when nv taken to bound state. */
  public void bound(int nvIndex) {}

  /** Callback during bind process when nv taken to unbound state. */
  public void unbound(int nvIndex) {}

  /** Callback if device affected by bind after bind process is complete. */
  public void bindComplete() { }

////////////////////////////////////////////////////////////
//  MessageTag Utilities
////////////////////////////////////////////////////////////
  /**
   * Find the message tag with the specified index.
   *  <p>
   * @param mtIndex The unque index of the message tag within device.
   * @return The message tag with the specified index
   *          or null if not found.
   */
  public final BMessageTag getMessageTag(int mtIndex)
  {
    // index -1 is the input message tag on all lonDevices
    if(mtIndex==-1) return getMessageIn();

    SlotCursor<Property> c = getProperties();
    while(c.next(BMessageTag.class))
    {
      BMessageTag mt = (BMessageTag)c.get();
      if(mt.getIndex()==mtIndex) return mt;
    }
    return null;
  }

////////////////////////////////////////////////////////////
//  BINvContainer Implementation
////////////////////////////////////////////////////////////

  public BLonDevice getLonDevice() { return this; }

  public BLonNetwork getLonNetwork() { return lonNetwork(); }

  public boolean isLonObject() { return false; }

////////////////////////////////////////////////////////////
//  BINetworkVariable Utilities
////////////////////////////////////////////////////////////

  /** Get an array of BINvContainers in this device. */
  public BINvContainer[] getNvContainers()
  {
  	Array<BINvContainer> a = new Array<>(BINvContainer.class);
  	a.add(this);
    doGetNvContainers(this,a);
    return a.trim();
  }

  private void doGetNvContainers(BComponent comp, Array<BINvContainer> a)
  {
    SlotCursor<Property> sc = comp.getProperties();
    while(sc.nextComponent())
    {
      BComponent c = (BComponent)sc.get();
      if(c.getType().is(BINvContainer.TYPE) && !c.getType().is(BPseudoNvContainer.TYPE))
        a.add((BINvContainer)c);
      else if(c.getType().is(BLonObjectFolder.TYPE))
        doGetNvContainers(c,a);
    }
  }

  /** Get array of BLonObjects in this device. */
  public BLonObject[] getLonObjects()
  {
  	Array<BLonObject> a = new Array<>(BLonObject.class);
    doGetLonObjects(this, a);
    return a.trim();
  }

  private void doGetLonObjects(BComponent comp, Array<BLonObject> a)
  {
    SlotCursor<Property> sc = comp.getProperties();
    while(sc.nextComponent())
    {
      BComponent c = (BComponent)sc.get();
      if(c.getType().is(BLonObject.TYPE))
        a.add((BLonObject)c);
      else if(c.getType().is(BLonObjectFolder.TYPE))
        doGetLonObjects(c,a);
    }
  }

  public BLonObject getLonObject(int objectId)
  {
    return doGetLonObject(this,objectId);
  }

  private BLonObject doGetLonObject(BComponent comp, int id)
  {
    SlotCursor<Property> sc = comp.getProperties();
    while(sc.nextComponent())
    {
      BComponent c = (BComponent)sc.get();
      if(c.getType().is(BLonObject.TYPE))
      {
        BLonObject lo = (BLonObject)c;
        if( lo.getObjectId()==id )
          return lo;
      }
      else if(c.getType().is(BLonObjectFolder.TYPE))
        return doGetLonObject(c,id);
    }
    return null;
  }

  /**
   * Find the network variable with the specified nvIndex.
   *  <p>
   * @param nvIndex The unque index of the nv within device.
   * @return The network variable with the specified nvIndex
   *          or null if not found.
   */
  public final BNetworkVariable getNetworkVariable(int nvIndex)
  {
    if(nvIndex<0) return null;
    BINetworkVariable[] nvs = getNvList();
    if( (nvIndex >= nvs.length) ||
        (nvs[nvIndex] == null) ||
        !nvs[nvIndex].isNetworkVariable()) return null;

    return (BNetworkVariable)nvs[nvIndex];
  }

  /**
   * Find the network config with the specified nvIndex.
   *  <p>
   * @param nvIndex The unque index of the nci within device.
   * @return The network config with the specified nvIndex
   *          or null if not found.
   */
  public final BNetworkConfig getNetworkConfig(int nvIndex)
  {
    if(nvIndex<0) return null;
    BINetworkVariable[] nvs = getNvList();
    if( (nvIndex >= nvs.length) ||
        (nvs[nvIndex] == null) ||
        !nvs[nvIndex].isNetworkConfig()) return null;

    return (BNetworkConfig)nvs[nvIndex];
  }

  /**
   * Find the property for a particular network variable that is a specific member
   * of a specific object within the device.
   *  <p>
   * @param objectIndex The unque index of the object nv is member of.
   * @param memberIndex The memberIndex within the object per the object definition.
   * @return The property of the network variable with the specified objectIndex
   *         and memberIndex or null if not found.
   */
  public final Property findLonObjectNvProperty(int objectIndex, int memberIndex, int snvtType)
  {
    BINetworkVariable[] nvs = getNvList();
    for(int i=0 ; i<nvs.length ; i++)
    {
      if((nvs[i]==null) || !nvs[i].isNetworkVariable()) continue;
      BNetworkVariable nv = (BNetworkVariable)nvs[i];
      if( (nv.getNvProps().getObjectIndex() == objectIndex) &&
          (nv.getNvProps().getMemberIndex() == memberIndex) )
      {
        // Verify type
        if(snvtType>0 && nv.getSnvtType()!=snvtType)
          throw new BajaRuntimeException("Invalid nvtype for object/member " + objectIndex + "|" + memberIndex + " snvtType=" + nv.getSnvtType() + ". Expected " + snvtType );

        log().fine("found nv for " + objectIndex + "  " + memberIndex + " - " + nv.getDisplayName(null));
        return getProperty(nv.getName());
      }
    }

    // index not found
    return null;
  }

  /**
   * Find a network variable of the specified snvt type.
   *  <p>
   * @param snvtType The snvt type id number to find.
   * @return The first lowest nv index of the specified type.
   */
  public final BINetworkVariable findSnvtType(int snvtType)
  {
    BINetworkVariable[] nvs = getNvList();
    for(int i=0 ; i<nvs.length ; i++)
    {
      if((nvs[i]==null)/* || !nvs[i].isNetworkVariable()*/) continue;
     // BINetworkVariable nv = (BINetworkVariable)nvs[i];

      if( nvs[i].getSnvtType() == snvtType )
      {
        return nvs[i];
      }
    }

    // index not found
    return null;
  }

  /**
   * Get array of NetworkVariables that are descendants of this
   * device. The nvs are indexed by nvIndex.  There will be null
   * entries for any given nv that is not represented in the
   * database.
   */
  public final BINetworkVariable[] getNetworkVariables()
  {
    return getNvList();
  }

  /** Get new array of all BNetworkConfig objects in this device. */
  public final BNetworkConfig[] getNetworkConfigs()
  {
    return (BNetworkConfig[])NmUtil.getDecendantsByClass(this, BNetworkConfig.class);
  }

  /** Get new array of all BConfigParameter objects in this device. */
  public final BConfigParameter[] getConfigParameters()
  {
    return (BConfigParameter[])NmUtil.getDecendantsByClass(this, BConfigParameter.class);
  }

  /**
   * Force refresh of internal nv list.
   */
  public final void refreshNvList()
  {
    synchronized(nvSync)
    {
      staleNvList=true;
    }
  }

  private BINetworkVariable[] getNvList()
  {
    synchronized(nvSync)
    {
      if(staleNvList)
      {
        Vector<BINetworkVariable> v = new Vector<>(100); //getDeviceData().get NvCount());

        // Check for max index as we go.
        int maxNvIndex = -1;
        BINvContainer[] nvcs = getNvContainers();
        for(int i=0 ; i<nvcs.length ; i++)
        {
          SlotCursor<Property> c = ((BComponent)nvcs[i]).getProperties();
          while(c.next(BINetworkVariable.class))
          {
            BINetworkVariable nv = (BINetworkVariable)c.get();
            int nvIndex = nv.getNvIndex();
            if(nvIndex>maxNvIndex) maxNvIndex = nvIndex;
            v.add(nv);
          }
        }

        nvList = new BINetworkVariable[maxNvIndex+1];
        for(int i=0 ; i<nvList.length ; i++) nvList[i] = null;

        for(int i=0 ; i<v.size() ; i++)
        {
          BINetworkVariable nv = v.elementAt(i);
          if(nv.getNvIndex() < 0) continue;
          nvList[nv.getNvIndex()] = nv;
        }
        staleNvList = false;
      }
      return nvList;
    }
  }

  Object nvSync = new Object();
  BINetworkVariable[] nvList;
  protected boolean staleNvList = true;

  public final BAliasConfigData getAlias(BNetworkVariable nv, int initialIndex)
  {
    BAliasTable tab = getDeviceData().getAliasTable();
    if(tab.getAliasCount()==0) return null;
    int nvIndex = nv.getNvIndex();
    for(int i=initialIndex ; i<tab.getAliasCount() ; i++)
    {
      BAliasConfigData aDat = tab.getAliasEntry(i);
      if(aDat.getPrimary()==nvIndex) return aDat;
    }
    return null;
  }

  public BControlPoint[] getLonProxies()
  {
    return  getLonProxies(null);
  }

  /**
   * Get an array of proxies which target specified
   * BLonComponent. If null then get all proxies in this device.
   */
  public BControlPoint[] getLonProxies(BLonComponent lc)
  {
    Array<BControlPoint> a = new Array<>(BControlPoint.class);
    getLonProxies(this, a, lc);
    return a.trim();
  }

  private void getLonProxies(BComponent comp, Array<BControlPoint> a, BLonComponent lc)
  {
    SlotCursor<Property> sc = comp.getProperties();
    while(sc.nextComponent())
    {
      BComponent c = (BComponent)sc.get();
      if( c.getType().is(BLonPointFolder.TYPE) ||
          c.getType().is(BLonPointDeviceExt.TYPE) )
      {
        getLonProxies(c,a,lc);
      }
      else if(c.getType().is(BControlPoint.TYPE))
      {
        BComponent ext = ((BControlPoint)c).getProxyExt();

        // Ignore control points that are not lon proxies
        if(!(ext instanceof BLonProxyExt)) continue;

        // If lonComponent specified make sure this is one of its proxies
        if( lc==null ||
            ((BLonProxyExt)ext).getTargetComp().equals(lc.getName()) )
        {
          a.add((BControlPoint)c);
        }
      }
    }
  }
////////////////////////////////////////////////////////////
//  Device Status Support
////////////////////////////////////////////////////////////
  /**
   * Implementation of the Pingable interface.<p>Determine if
   * the device can communicate and if so mark device up and
   * if not mark the device down.
   */
  public void doPing()
  {
    if( !isRunning() || !getEnabled() || (!lonNetwork().isServiceRunning()) || isFatalFault() ) return;
    pingImpl();
  }

  private void pingImpl()
  {
    try
    {
      if(getNeuronIdAddress().isZero()) throw new RuntimeException("Neuron id is zero");
      if(getDeviceData().getNodeState()==BLonNodeState.unknown) throw new RuntimeException("Unknown state - Device requires commissioning");

      if(Lon.d()) NmUtil.getDeviceState(this);
      pingOk();
      setStatus(BStatus.make(getStatus(), BStatus.DOWN, false));
    }
    catch(NotRunningException e)
    {
      //Ignore this exception
    }
    catch(Throwable e)
    {
      pingFail(e.getMessage());
      setStatus(BStatus.make(getStatus(), BStatus.DOWN, true));
    }
  }

////////////////////////////////////////////////////////////
//  Poll Support
////////////////////////////////////////////////////////////

  public boolean isReadyForNvUpdates()
  {
    // status isValid if not disabled,fault,down,stale,null
    return Sys.atSteadyState() && getStatus().isValid() && isConfigOnline() && getEnabled();
  }


////////////////////////////////////////////////////////////////
// Link callbacks
////////////////////////////////////////////////////////////////

  /** Override point for link validation between londevices. */
  protected LinkCheck doCheckLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    return NvDev.doNvCheckLink(source, sourceSlot, this, targetSlot, cx);
  }

  public void linkUpdate()
  {
    if(linkUpdateDone) return;

    // Insure non-component properties are up-to-date
    getComponentSpace().update(this,1);
    getComponentSpace().update(getDeviceData(),2);

    linkUpdateDone = true;

    BINvContainer[] ca = getNvContainers();
    // first containers is this
    for(int i=1 ; i<ca.length ; ++i) ca[i].linkUpdate();
  }
  boolean linkUpdateDone = false;

  /**
   * Create an instance of BLink to use for a link to the specified
   * source component.  This method is used by Baja tools when users
   * create links via the "bajaui:javax.baja.ui.commands.LinkCommand".
   */
  public final BLink makeLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    if(!NvDev.requiresLonLink(targetSlot))
      return super.makeLink(source,sourceSlot,targetSlot, cx);

    return NvDev.makeLonLink(source, sourceSlot, this, targetSlot, cx);
  }


////////////////////////////////////////////////////////////
//  File support
////////////////////////////////////////////////////////////
  // ** For internal use only. Open any files needed for downloading device. */
  final void initDownload(boolean allowRandomAccess)
  {
    try
    {
      if(fileState==FILE_STATE_INIT) createFiles();
      if(fileState==FILE_STATE_NO_FILES) return;

      if(readWritefile != null && !readWritefile.isOpen())
      {
        if(!hasReadOnly && hasReadOnlyCp())
        {
          // If the device has constant cps and no readOnly file then must ensure that
          // constants are not overwritten - allow random access or read before writing
          if(readWritefile.supportsRandomAccess())
            readWritefile.open(LonFile.READ_WRITE_CONFIG_FILE,LonFile.CREATE_FILE,true);
          else
            readWritefile.open(LonFile.READ_WRITE_CONFIG_FILE,LonFile.ACCESS_FILE,false);
        }
        else
          readWritefile.open(LonFile.READ_WRITE_CONFIG_FILE,LonFile.CREATE_FILE,allowRandomAccess);
      }

      if(readOnlyfile != null && !readOnlyfile.isOpen())
        readOnlyfile.open(LonFile.READ_ONLY_CONFIG_FILE,LonFile.CREATE_FILE,allowRandomAccess);
    }
    catch(LonException e)
    {
      close();
      // Force recreation of file on next try - problem could be file objects
      clearFiles();
      throw new BajaRuntimeException("Unable to open files for download.",e);
    }

    fileState=FILE_STATE_DOWNLOAD;
  }

  private boolean hasReadOnlyCp()
  {
    BINvContainer[] nvcs = getNvContainers();
    for(int i=0 ; i<nvcs.length ; i++)
    {
      SlotCursor<Property> c = ((BComponent)nvcs[i]).getProperties();
      while(c.next(BConfigParameter.class))
      {
        BConfigParameter cp = (BConfigParameter)c.get();
        if(!cp.isWriteable()) return true;
      }
    }
    return false;
  }

  /** For internal use only. Close any open files and reset device if required. */
  final void cleanupDownload()
  {
    close();
    fileState=FILE_STATE_IDLE;
  }

  /** Is a download in progress (i.e. has beginDownload() been called and not endDownload()).*/
  public final boolean isDownLoadInProgress() { return downloading; }
  /** Is a upload in progress (i.e. has beginUpload() been called and not endUpload()).*/
  public final boolean isUpLoadInProgress() { return uploading; }

  /** Override point to customize upload process.  This will be called before the upload job is initiated.
  If a subclass wishes to cancel the upload, then a LocalizableRuntimeException should be thrown.*/
  public void checkUpload(){}
  /** Override point to customize upload process.  This will be called at the beginning of an upload operation. */ //after initUpload()
  public void beginUpload(){uploading=true; initUpload(false);}
  /** Override point to customize upload process.  This will be called after all device components have been uploaded. */
  public void endUpload(){cleanupUpload(); uploading=false; }

  /** Override point to customize changeNvType job.  This will be called before the changeNvType job is initiated.
   * If a subclass wishes to cancel the changeNvType job, then a LocalizableRuntimeException should be thrown.*/
  public void checkChangeNvType() {}
  /** Callback after changeNvType job is complete */
  public void changeNvTypeComplete() {}

  /** Override point to customize download process.  This will be called before the download job is initiated.
  If a subclass wishes to cancel the download, then a LocalizableRuntimeException should be thrown.*/
  public void checkDownload(){}
  /** Override point to customize download process.  This will be called at the beginning of a download operation . */ //after initDownload()
  public void beginDownload(){downloading = true; initDownload(false); }
  /** Override point to customize download process.  This will be called after all device components have been downloaded. */
  public void endDownload(){cleanupDownload(); downloading = false;}

  // ** For internal use only. Open any files needed for uploading device. */
  final void initUpload(boolean allowRandomAccess)
  {
    try
    {
      if(fileState==FILE_STATE_INIT) createFiles();
      if(fileState==FILE_STATE_NO_FILES) return;

      if(readWritefile != null && !readWritefile.isOpen())
        readWritefile.open(LonFile.READ_WRITE_CONFIG_FILE,LonFile.ACCESS_FILE,allowRandomAccess);

      if(readOnlyfile  != null && !readOnlyfile.isOpen())
        readOnlyfile .open(LonFile.READ_ONLY_CONFIG_FILE,LonFile.ACCESS_FILE,allowRandomAccess);
    }
    catch(LonException e)
    {
      close();
      // Force recreation of file on next try - problem could be file objects
      clearFiles();
      throw new BajaRuntimeException("Unable to open files for upload.",e);
    }

    fileState=FILE_STATE_UPLOAD;
  }

  /** For internal use only. Close any open files. */
  final void cleanupUpload()
  {
    close();
    fileState=FILE_STATE_IDLE;
  }



  /**
   *  Convience call for getLonFileOpen(int fileNum, false, true)
   *
   *  @param fileNum The file index within the device of the desired file.
   *  @return A file object for access to the specified file if it exists,
   *          otherwise return null.
   */
  public final LonFile getLonFileOpen(int fileNum)
  {
    return getLonFileOpen(fileNum, false, true);
  }


  /**
   *  Get a LonFile for access to the file with the specified fileNum.  Create
   *  and open files as needed.
   *
   *  @param fileNum The file index within the device of the desired file.
   *  @param create If true the file data will be zeroed - if false initial file contents will be read from device.
   *  @param random If true the allow random access (if supported by device) - if false entire contents of file will be written when file closed.
   *  @return A file object for access to the specified file if it exists,
   *          otherwise return null.
   */
  public final LonFile getLonFileOpen(int fileNum, boolean create, boolean random)
  {
    try
    {
      if(fileState==FILE_STATE_INIT) createFiles();
      if(fileState==FILE_STATE_NO_FILES) return null;

      // Always return a copy. Making a copy will reuse fileDirectory.
      //  Also, we don't know what else caller will do with this file
      LonFile f = lonFile.copy();
      if(fileNum<0) return f;
      if(f!=null && !f.isOpen()) f.open(fileNum, create, random);
      return f;
    }
    catch(LonException e)
    {
      e.printStackTrace();
      log().log(Level.SEVERE,"error accessing file " + fileNum,e); 
      return null;
    }
  }

  // Protected (since 4.0.22) call used for cp read/writes
  protected LonFile getReadWriteFile()
  {
    try
    {
      if(fileState==FILE_STATE_INIT) createFiles();
      if(fileState==FILE_STATE_NO_FILES) return null;

      LonFile f = readWritefile;
      if(f!=null && !f.isOpen())  f.open(LonFile.READ_WRITE_CONFIG_FILE, false, true);
      return f;
    }
    catch(LonException e)
    {
      e.printStackTrace();
      log().log(Level.SEVERE,"error accessing file readOnly config file",e); 
      return null;
    }
  }

  // Protected (since 4.0.22) call used for cp read/writes
  protected LonFile getReadOnlyFile()
  {
    try
    {
      if(fileState==FILE_STATE_INIT) createFiles();
      if(fileState==FILE_STATE_NO_FILES) return null;

      LonFile f    = readOnlyfile;
      int     fnum = LonFile.READ_ONLY_CONFIG_FILE;
      if(!hasReadOnly)
      {
        f=readWritefile;
        fnum=LonFile.READ_WRITE_CONFIG_FILE;
      }

      if(f!=null && !f.isOpen())  f.open(fnum, false, true);
      return f;
    }
    catch(LonException e)
    {
      e.printStackTrace();
      log().log(Level.SEVERE,"error accessing file readOnly config file",e); 
      return null;
    }
  }

  private int fileState = FILE_STATE_INIT;
  private boolean hasReadOnly = false;
  private LonFile lonFile = null;
  private LonFile readWritefile = null;
  private LonFile readOnlyfile = null;

  private void createFiles()
    throws LonException
  {
    // Check for node object
    if(!getDeviceData().getHasNodeObject())
    {
      fileState=FILE_STATE_NO_FILES;
      return;
    }

    lonFile = LonFile.createFile(this);
    if(lonFile==null)
    {
      fileState=FILE_STATE_NO_FILES;
      return;
    }

    // Check for template file before creating cp access files
    if(lonFile.findFileNum(2)==0)
    {
      readWritefile = lonFile;

      // If this device has a second value file then it is a readOnly file.
      // Create readOnly file by copying readWriteFile to keep from having to
      // read the template file again.
      hasReadOnly = lonFile.findFileNum(LonFile.CONFIG_PARAM_VALUE_FILE,LonFile.READ_WRITE_CONFIG_FILE) > 0;
      if(hasReadOnly)   readOnlyfile = lonFile.copy();
    }
    fileState=FILE_STATE_IDLE;
  }

  private void close()
  {
    if(lonFile != null) try {lonFile.close(); } catch(LonException e) { }
    if(readWritefile != null) try {readWritefile.close(); } catch(LonException e) { }
    if(readOnlyfile  != null) try {readOnlyfile .close(); } catch(LonException e) { }
  }

  /** Force recreation of file objects on next access. */
  public final void clearFiles()
  {
    fileState = FILE_STATE_INIT;
    lonFile = null;
    readWritefile = null;
    readOnlyfile = null;
  }

  private final static int FILE_STATE_INIT     = 0;
  private final static int FILE_STATE_NO_FILES = 1;
  private final static int FILE_STATE_DOWNLOAD = 2;
  private final static int FILE_STATE_UPLOAD   = 3;
  private final static int FILE_STATE_IDLE     = 4;

////////////////////////////////////////////////////////////////
// Object Disable/Enable support
////////////////////////////////////////////////////////////////

  /**
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public boolean disableObjectForCpWrite(BConfigProps configProps)  { throw new UnsupportedOperationException("Deprecated"); }

  /**
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public void enableObject(BConfigProps configProps) {  throw new UnsupportedOperationException("Deprecated"); }

  /**
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public final void resetDevice(){  throw new UnsupportedOperationException("Deprecated : use doReset()"); }

  /**
   * Check if the specified object is disabled. If the device
   * is not config online all objects are disable return true.  If
   * unable to access status throw RuntimeException.
   */
  public boolean isObjectDisabled(int objNdx)
  {
    if(!isConfigOnline()) return true;

    Property reqProp = findLonObjectNvProperty(0,1,BLonSnvtType.SNVT_OBJ_REQUEST);
    int reqNdx = ((BNetworkVariable)get(reqProp)).getNvIndex();
    if(reqNdx<0) throw new BajaRuntimeException("Can not find SNVT_OBJ_REQUEST in " + getDisplayName(null));
    BNetworkVariable reqNv = getNetworkVariable(reqNdx);

    Property statNdx = findLonObjectNvProperty(0,2,BLonSnvtType.SNVT_OBJ_STATUS);
    if(statNdx==null) throw new BajaRuntimeException("Can not find nvoStatus.");
    BNetworkVariable statNv = (BNetworkVariable)get(statNdx);

    try
    {
   //   sendObjectRequest(BLonObjectRequestEnum.rqNul, 0, reqNv);
   //   NmUtil.wait(50);
      sendObjectRequest(BLonObjectRequestEnum.rqUpdateStatus, objNdx, reqNv);
      NmUtil.wait(50);
      BNetworkVariable objStat = getObjectStatus(objNdx, statNv);
      return objStat.getData().getLonBoolean("disabled");
    }
    catch(LonException e)
    {
      throw new BajaRuntimeException("Unable to set object " + objNdx + " status in " + getDisplayName(null),e);
    }

  }

  /**
   * Set the enable/disable state of the specified object.  Return true
   * if operation successfull.
   */
  public boolean enableObject(int objNdx, boolean en)
  {
    if(objNdx<0) return false;

    Property reqProp = findLonObjectNvProperty(0,1,BLonSnvtType.SNVT_OBJ_REQUEST);
    int reqNdx = ((BNetworkVariable)get(reqProp)).getNvIndex();
    if(reqNdx<0) return false;
    BNetworkVariable reqNv = getNetworkVariable(reqNdx);

    Property statNdx = findLonObjectNvProperty(0,2,BLonSnvtType.SNVT_OBJ_STATUS);
    if(statNdx==null) return false;
    BNetworkVariable statNv = (BNetworkVariable)get(statNdx);

    for(int attempts=3; attempts>0 ; attempts--)
    {
      try
      {
        BLonObjectRequestEnum req = en ? BLonObjectRequestEnum.rqEnable : BLonObjectRequestEnum.rqDisabled;
        sendObjectRequest(req, objNdx, reqNv);
        NmUtil.wait(50);
        BNetworkVariable objStat = getObjectStatus(objNdx, statNv);
        if(objStat.getData().getLonBoolean("invalidRequest")) return false;
        if(objStat.getData().getLonBoolean("disabled")==en) return true;
      }
      catch(Exception e)
      {
        System.out.println("enableObject() " +  e);
        e.printStackTrace();
      }
    }
    return false;
  }

  private void sendObjectRequest(BLonObjectRequestEnum req, int objNdx, BNetworkVariable nv)
    throws LonException
  {
    BLonData ld = nv.getData();
    ld.setLonInt("objectId",objNdx,BLonNetwork.lonNoWrite);
    ld.setLonEnum("objectRequest",req.getTag(),BLonNetwork.lonNoWrite);
    nv.doForceWrite();
  }

  // Get the status of the specified objNdx.
  private BNetworkVariable getObjectStatus(int objNdx, BNetworkVariable statNv)
    throws LonException
  {
    for(int i=0 ; i<4 ; i++)
    {
      if(!statNv.getNvProps().getBoundToLocal()) statNv.doForceRead();
      int objId = statNv.getData().getLonInt("objectId");
      if(objId==objNdx)  return statNv;

      // delay and try again
      NmUtil.wait(100);
    }
    throw new LonException("Can not read status nv");
  }

  /**
   * Disable object in specified select string. Return true if objects
   * have been disabled. Set objDis flag false if it was aready disabled or a
   * device is not configOnline.
   */
  void disableObjectsForWrite(int[] sels, boolean[] objDis)
  {
    // If download in progress objects were disabled - don't change, set flags to false
    if(isDownLoadInProgress() || !isConfigOnline())
    {
      for(int i=0 ; i<sels.length ; i++) objDis[i]=false;
      return;
    }

    for(int i=0 ; i<sels.length ; i++)
    {
      // If object already disable - set flag false so it will not be enabled after write
      if(isObjectDisabled(sels[i]))
      {
        objDis[i] = false;
      }
      else
      {
        try { objDis[i] = enableObject(sels[i],false); }
        catch(Throwable e) { objDis[i] = false; System.out.println(e); }
      }
    }

  }

  /**
   * Enable object associated with BConfigParameter with specified
   * BConfigProps.
   */
  void enableObjectsAfterWrite(int[] sels, boolean[] objDis)
  {
    for(int i=0 ; i<sels.length ; i++)
    {
      if(objDis[i])
      {
        try { enableObject(sels[i],true); }
        catch(Throwable e) {System.out.println(e); }
      }
    }
  }


////////////////////////////////////////////////////////////
//  Convenience
////////////////////////////////////////////////////////////

  public int getMaxMessageLengthOut()
  {
    if(maxOutMessageSize == 0) updateMaxMessageLength();

    return maxOutMessageSize;
  }
  public int getMaxMessageLengthIn()
  {
    if(maxInMessageSize == 0) updateMaxMessageLength();

    return maxInMessageSize;
  }

  /* Clear the cached values for max in/out messages sizes.  These will
   *  force a new update on next memory read/write operation. */
  private final void clearMaxMessageLength()
  {
    maxOutMessageSize = 0;
    maxInMessageSize  = 0;
  }

  public final void updateMaxMessageLength()
  {
    try
    {
      if(!Lon.d()) return; // disable comm for debug
      byte[] a = Neuron.readMemory(lonComm(), NetMessages.READ_ONLY_RELATIVE,
                        NmUtil.getSendAddress(this), 0x15, 5, authenticate(), false, 8 );
      boolean explicit = ((a[0]>>5) & 0x01) > 0;

      int appBufOutSize = Neuron.getBufferSize((a[3] & 0x0f0)>>4);
      int appBufInSize  = Neuron.getBufferSize((a[3] & 0x0f));
      int netBufOutSize = Neuron.getBufferSize((a[4] & 0x0f0)>>4);
      int netBufInSize  = Neuron.getBufferSize((a[4] & 0x0f));

      // empirical test with EC-12b indicated 20 bytes overhead in net buffs 16 in app buffs
      // According to Neuron C Programing guide app overhead is 16 with explicited addressing 5 without
      // and worse case net overhead is 20 + 6
      int appOverhead = (explicit) ? 16 : 5;
      maxInMessageSize = Math.min(appBufInSize-appOverhead,netBufInSize-26);
      maxOutMessageSize = Math.min(appBufOutSize-appOverhead,netBufOutSize-26);

      // Message size is also limited by the max message length in the local device.
      if(!isLocal())
      {
        BLonDevice local = lonNetwork().getLocalLonDevice();
        maxInMessageSize = Math.min(maxInMessageSize,local.getMaxMessageLengthOut());
        maxOutMessageSize = Math.min(maxOutMessageSize,local.getMaxMessageLengthIn());
      }

/*
System.out.println(getName() + " updateMaxMessageLength");
System.out.println("appBufOutSize " + appBufOutSize );
System.out.println("appBufInSize  " + appBufInSize  );
System.out.println("netBufOutSize " + netBufOutSize );
System.out.println("netBufInSize  " + netBufInSize  );
System.out.println("maxInMessageSize " + maxInMessageSize );
System.out.println("maxOutMessageSize " + maxOutMessageSize );
*/
      // Don't let these be <0
      if(maxInMessageSize<0) maxInMessageSize=8;
      if(maxOutMessageSize <0) maxOutMessageSize =8;
    }
    catch(Exception e)
    {
      maxInMessageSize=8;
      maxOutMessageSize=8;
      log().log(Level.SEVERE,"error reading buffer size ",e); 
    }
  }

  private int maxInMessageSize = 0;
  private int maxOutMessageSize = 0;

////////////////////////////////////////////////////////////
//  Diagnostics
////////////////////////////////////////////////////////////
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    out.trTitle("LonDevice",1);
    out.startProps("Files");
    out.prop("Has node object", (getDeviceData().getHasNodeObject()));
    switch(fileState)
    {
      case FILE_STATE_INIT     : out.prop("File state","FILE_STATE_INIT    "); break;
      case FILE_STATE_NO_FILES : out.prop("File state","FILE_STATE_NO_FILES"); break;
      case FILE_STATE_DOWNLOAD : out.prop("File state","FILE_STATE_DOWNLOAD"); break;
      case FILE_STATE_UPLOAD   : out.prop("File state","FILE_STATE_UPLOAD  "); break;
      case FILE_STATE_IDLE     : out.prop("File state","FILE_STATE_IDLE    "); break;
      default  :   out.prop("File state","unknown"); break;
    }
    out.prop("Has readWrite file", (readWritefile != null));
    out.prop("Has readOnly file", (readOnlyfile != null));
    out.prop("maxMessageSizeIn", getMaxMessageLengthIn());
    out.prop("maxMessageSizeOut", getMaxMessageLengthOut());
    out.prop("dataPntMismatchCount", dataPntMismatchCount);
    out.prop("downloading", downloading);
    out.prop("uploading", uploading);
    out.endProps();

    // Add spy data for DeviceFacets if present
    DeviceFacets.spy(this,out);
  }
  public int dataPntMismatchCount = 0;
////////////////////////////////////////////////////////////
//  Convenience
////////////////////////////////////////////////////////////
  /** Convenience method to access the loncomm object. */
  public final  LonComm lonComm() { return lonNetwork().lonComm(); }

  /** Convenience method to access the network log object. */
  public Logger log()
  {
    if(log==null) log = lonNetwork().log();
    return log;
  }
  protected Logger log = null;

  /** Convenience method to access the network object. */
  public final BLonNetwork lonNetwork()
  {
    if(net==null)
    {
      BComplex p = getParent();
      while(p!=null &&  !(p instanceof BLonNetwork )) p = p.getParent();

      if(p!=null)net = (BLonNetwork)p;
    }
    return net;
  }
  private BLonNetwork net = null;
  protected boolean downloading = false;
  protected boolean uploading = false;


}
