/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.license.Feature;
import javax.baja.lonworks.datatypes.BLonCommConfig;
import javax.baja.lonworks.datatypes.BNeuronId;
import javax.baja.lonworks.ext.BLonPollService;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.tuning.BLonTuningPolicyMap;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIService;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.CoalesceQueue;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;
import javax.baja.util.Queue;
import javax.baja.util.Worker;

import com.tridium.lonworks.BLonRouter;
import com.tridium.lonworks.NAddressManager;
import com.tridium.lonworks.NetMessageReceiver;
import com.tridium.lonworks.NvManager;
import com.tridium.lonworks.datatypes.BUtilCmdJob;
import com.tridium.lonworks.datatypes.BUtilitiesCommand;
import com.tridium.lonworks.device.BDownloadJob;
import com.tridium.lonworks.device.BUploadJob;
import com.tridium.lonworks.device.DynaDev;
import com.tridium.lonworks.device.NvDev;
import com.tridium.lonworks.loncomm.NAppBuffer;
import com.tridium.lonworks.loncomm.NLonComm;
import com.tridium.lonworks.netmgmt.BLonNetmgmt;
import com.tridium.lonworks.util.NmUtil;
import com.tridium.lonworks.util.TimedCoalesceQueue;
import com.tridium.sys.license.LicenseUtil;
import com.tridium.util.PxUtil;

/**
 * BLonNetwork is the root class for a lonworks network.
 *
 * @author Robert Adams on 06 Dec 00
 * @since Niagara 3.0
 */
@NiagaraType
/*
 lonComm provides lonworks communications api.
 */
@NiagaraProperty(
  name = "lonCommConfig",
  type = "BLonCommConfig",
  defaultValue = "new BLonCommConfig()"
)
/*
 Service to poll each LonDevice on a regular interval.
 */
@NiagaraProperty(
  name = "pollService",
  type = "BLonPollService",
  defaultValue = "new BLonPollService()"
)
@NiagaraProperty(
  name = "lonNetmgmt",
  type = "BLonNetmgmt",
  defaultValue = "new BLonNetmgmt()"
)
@NiagaraProperty(
  name = "tuningPolicies",
  type = "BLonTuningPolicyMap",
  defaultValue = "new BLonTuningPolicyMap()"
)
@NiagaraProperty(
  name = "localLonDevice",
  type = "BLocalLonDevice",
  defaultValue = "new BLocalLonDevice()"
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
@NiagaraAction(
  name = "executeCommand",
  parameterType = "BUtilitiesCommand",
  defaultValue = "new BUtilitiesCommand()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "makeNvsNonCritical"
)
/*
 Fired when a londevice registers or unregisters.
 */
@NiagaraTopic(
  name = "deviceChange"
)
public class BLonNetwork
  extends BDeviceNetwork
  implements BIService
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLonNetwork(548393595)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "lonCommConfig"

  /**
   * Slot for the {@code lonCommConfig} property.
   * lonComm provides lonworks communications api.
   * @see #getLonCommConfig
   * @see #setLonCommConfig
   */
  public static final Property lonCommConfig = newProperty(0, new BLonCommConfig(), null);

  /**
   * Get the {@code lonCommConfig} property.
   * lonComm provides lonworks communications api.
   * @see #lonCommConfig
   */
  public BLonCommConfig getLonCommConfig() { return (BLonCommConfig)get(lonCommConfig); }

  /**
   * Set the {@code lonCommConfig} property.
   * lonComm provides lonworks communications api.
   * @see #lonCommConfig
   */
  public void setLonCommConfig(BLonCommConfig v) { set(lonCommConfig, v, null); }

  //endregion Property "lonCommConfig"

  //region Property "pollService"

  /**
   * Slot for the {@code pollService} property.
   * Service to poll each LonDevice on a regular interval.
   * @see #getPollService
   * @see #setPollService
   */
  public static final Property pollService = newProperty(0, new BLonPollService(), null);

  /**
   * Get the {@code pollService} property.
   * Service to poll each LonDevice on a regular interval.
   * @see #pollService
   */
  public BLonPollService getPollService() { return (BLonPollService)get(pollService); }

  /**
   * Set the {@code pollService} property.
   * Service to poll each LonDevice on a regular interval.
   * @see #pollService
   */
  public void setPollService(BLonPollService v) { set(pollService, v, null); }

  //endregion Property "pollService"

  //region Property "lonNetmgmt"

  /**
   * Slot for the {@code lonNetmgmt} property.
   * @see #getLonNetmgmt
   * @see #setLonNetmgmt
   */
  public static final Property lonNetmgmt = newProperty(0, new BLonNetmgmt(), null);

  /**
   * Get the {@code lonNetmgmt} property.
   * @see #lonNetmgmt
   */
  public BLonNetmgmt getLonNetmgmt() { return (BLonNetmgmt)get(lonNetmgmt); }

  /**
   * Set the {@code lonNetmgmt} property.
   * @see #lonNetmgmt
   */
  public void setLonNetmgmt(BLonNetmgmt v) { set(lonNetmgmt, v, null); }

  //endregion Property "lonNetmgmt"

  //region Property "tuningPolicies"

  /**
   * Slot for the {@code tuningPolicies} property.
   * @see #getTuningPolicies
   * @see #setTuningPolicies
   */
  public static final Property tuningPolicies = newProperty(0, new BLonTuningPolicyMap(), null);

  /**
   * Get the {@code tuningPolicies} property.
   * @see #tuningPolicies
   */
  public BLonTuningPolicyMap getTuningPolicies() { return (BLonTuningPolicyMap)get(tuningPolicies); }

  /**
   * Set the {@code tuningPolicies} property.
   * @see #tuningPolicies
   */
  public void setTuningPolicies(BLonTuningPolicyMap v) { set(tuningPolicies, v, null); }

  //endregion Property "tuningPolicies"

  //region Property "localLonDevice"

  /**
   * Slot for the {@code localLonDevice} property.
   * @see #getLocalLonDevice
   * @see #setLocalLonDevice
   */
  public static final Property localLonDevice = newProperty(0, new BLocalLonDevice(), null);

  /**
   * Get the {@code localLonDevice} property.
   * @see #localLonDevice
   */
  public BLocalLonDevice getLocalLonDevice() { return (BLocalLonDevice)get(localLonDevice); }

  /**
   * Set the {@code localLonDevice} property.
   * @see #localLonDevice
   */
  public void setLocalLonDevice(BLocalLonDevice v) { set(localLonDevice, v, null); }

  //endregion Property "localLonDevice"

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

  //region Action "executeCommand"

  /**
   * Slot for the {@code executeCommand} action.
   * @see #executeCommand(BUtilitiesCommand parameter)
   */
  public static final Action executeCommand = newAction(Flags.HIDDEN, new BUtilitiesCommand(), null);

  /**
   * Invoke the {@code executeCommand} action.
   * @see #executeCommand
   */
  public BOrd executeCommand(BUtilitiesCommand parameter) { return (BOrd)invoke(executeCommand, parameter, null); }

  //endregion Action "executeCommand"

  //region Action "makeNvsNonCritical"

  /**
   * Slot for the {@code makeNvsNonCritical} action.
   * @see #makeNvsNonCritical()
   */
  public static final Action makeNvsNonCritical = newAction(0, null);

  /**
   * Invoke the {@code makeNvsNonCritical} action.
   * @see #makeNvsNonCritical
   */
  public void makeNvsNonCritical() { invoke(makeNvsNonCritical, null, null); }

  //endregion Action "makeNvsNonCritical"

  //region Topic "deviceChange"

  /**
   * Slot for the {@code deviceChange} topic.
   * Fired when a londevice registers or unregisters.
   * @see #fireDeviceChange
   */
  public static final Topic deviceChange = newTopic(0, null);

  /**
   * Fire an event for the {@code deviceChange} topic.
   * Fired when a londevice registers or unregisters.
   * @see #deviceChange
   */
  public void fireDeviceChange(BValue event) { fire(deviceChange, event, null); }

  //endregion Topic "deviceChange"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Licensing
////////////////////////////////////////////////////////////////

  public final Feature getLicenseFeature()
  {
    return Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "lonworks");
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public final BOrd doExecuteCommand(BUtilitiesCommand cmd)
  {
    return new BUtilCmdJob(this, cmd).submit(null);
  }

  /**
   * Register this component under "BLonNetwork.class".
   */
  public final Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static Type[] serviceTypes = new Type[] { TYPE };

  /**
   * Replace wiresheet with lonworks version and push BLonDeviceManager
   * to top of agent list.  Then call modifyAgentList();
   */
  public final AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list = NvDev.fixWireSheet(list,cx);

    // Put BLonDeviceManager or subclass on top - must push all instances to top
    // as BWbProfile overrides are done after this call
    AgentInfo[] aia = list.list();
    for(int i=0 ; i<aia.length ; ++i)
    {
      if(aia[i].getAgentType().getTypeName().equals("LonDeviceManager"))
      {
        if(i!=0) list.toTop(i);
      }
    }
    return PxUtil.movePxViewsToTop(modifyAgentList(list, cx));
  }

  /**
   * Override point for subclasses to make modifications to
   * agentList.
   *
   * Added in 3.6.32
   *
   * @param list - current AgentList
   * @param cx
   * @return modified AgentList
   */
  protected AgentList modifyAgentList(AgentList list, Context cx)
  {
    return list;
  }

  /** LonNetwork cannot be parent of LonNetwork. */
  public final boolean isParentLegal(BComponent parent)
  {
    return !(parent instanceof BLonNetwork) ;
  }

  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if( (value instanceof BLocalLonDevice) /*&& !name.equals("localLonDevice")*/)
      throw new LocalizableRuntimeException("lonworks", "addLocalDeviceError");
  }

  /** Override */
  public void added(Property prop, Context context)
  {
    try
    {
      if(!isRunning()) return;

      if(prop.getType().is(BLonDevice.TYPE))
        netmgmt().deviceAdded((BLonDevice)get(prop));
      else if(prop.getType().is(BLonRouter.TYPE))
        netmgmt().routerAdded((BLonRouter)get(prop));
    }
    finally
    {
      super.added(prop,context);
    }
  }


  /**
   * The Type for all devices supported by BLonNetwork
   * is BLonDevice.
   */
  public final Type getDeviceType() { return BLonDevice.TYPE; }

  /**
   * The Type for DeviceFolders for this network is .
   */
  public final Type getDeviceFolderType() { return BLonDeviceFolder.TYPE; }



  /**
   * Callback for processing upLoad.
   */
  public final void doUpload(BUploadParameters p, Context cx)
    throws Exception
  {
    new BUploadJob(this,p,cx).submit(cx);
  }

  /**
   * Callback for processing downLoad.
   */
  public final void doDownload(BDownloadParameters p, Context cx)
    throws Exception
  {
    new BDownloadJob(this,p,cx).submit(cx);
  }

  /**
   * Post a ping Invocation.
   */
  protected IFuture postPing()
  {
    return postAsync(new Invocation(this, ping, null, null));
  }

  public void doPing()
    throws Exception
  {
    Object[] pa = NmUtil.getDecendantsByClass(this,BLonDevice.class);
    for(int i=0 ; i<pa.length ; i++)
      ((BLonDevice)pa[i]).doPing();
    pingOk();
  }

  /** 
   * Callback for makeNvsNonCritical action.
   * 
   * @since Niagara 3.8.38.1 , 3.7.107
   */
  public void doMakeNvsNonCritical()
  {
    BLonDevice[] devA = getLonDevices();

    for(int i=0 ; i<devA.length ; ++i)
    {
      BINetworkVariable[] nvs = devA[i].getNetworkVariables();
      for(int n=0 ; n<nvs.length ; ++n)
      {
        if (nvs[n] != null)
        {
          if (nvs[n].isNetworkVariable() || nvs[n].isLocalNv())
            DynaDev.setNonCritical((BLonData)nvs[n]);
        }
      }
    }
  }

////////////////////////////////////////////////////////////////
// Startup
////////////////////////////////////////////////////////////////

  /** Implementaion of BIService.serviceStarted() */
  public final void serviceStarted()
  {
    if(firstPass)
    {
      lonComm = new NLonComm(this);
      netMessageReceiver = new NetMessageReceiver(this);
      nvManager = new NvManager(this);
      addressManager = new NAddressManager(this);

      // Make network the point for serializing commands.
      workQueue = new Worker(new Queue(1000) ) ;
      coalesceQueue = new Worker(new CoalesceQueue(5000) ) ;
      proxyQueue = new Worker(new Queue(1000) ) ;
      timedQueue = new Worker(new TimedCoalesceQueue(1000) ) ;

      firstPass = false;
    }

    // If outOfService don't start anything
    if(isDisabled()) return;
    disabledAtStart = false;

    try
    {
      lonComm.start();
      workQueue.start(getLogName() + ".Async");
      setWorkQueuePriority(); // increment async priority
      coalesceQueue.start(getLogName() + ".AsyncEvent");
      proxyQueue.start(getLogName() + ".Proxy");
      timedQueue.start(getLogName() + ".Delay");
      log().info("Service started on " + getLonCommConfig().getDeviceName());
      setStatus(BStatus.make(getStatus(), BStatus.DOWN, false));
      serviceRunning = true;
      configOk();
    }
    catch(Throwable e)
    {
      log().log(Level.SEVERE,"Error initializing LonNetwork " + getDisplayName(null),e); 
      configFail(e.getMessage());
    }
  }
  private boolean firstPass = true;

  // This flag needed to indicate that the network was disabled at startup - need to
  // know this to force start of devices when network subsequently enabled. Pacman 20922
  private boolean disabledAtStart = true;

  /** Implementaion of BIService.serviceStopped()*/
  public final void serviceStopped()
  {
    serviceRunning = false;
    setStatus(BStatus.make(getStatus(), BStatus.DOWN, true));
    workQueue.stop();
    coalesceQueue.stop();
    proxyQueue.stop();
    timedQueue.stop();
    lonComm.stop();
    log().info("Service stopped on " + getLonCommConfig().getDeviceName());
  }

  /** Is the service running. */
  public boolean isServiceRunning() { return serviceRunning; }
  private boolean serviceRunning = false;

  /**
   * Call serviceStarted() if not already running, not disable, and not in fault.
   */
  public void started()
    throws Exception
  {
    try
    {
      if (!isServiceRunning() && !isDisabled() && !getStatus().isFault())
        serviceStarted();
    }
    finally
    {
      super.started();
    }
  }

  public void changed(Property prop, Context context)
  {
    super.changed(prop,context);

      if(!isRunning()) return;

      if(prop==enabled)
      {
        boolean restartDevs = false;

        // Start or stop service based on new setting.
        if(!isDisabled() && !isServiceRunning())
        {
          if(disabledAtStart) restartDevs = true;
          serviceStarted();
        }

        if(isDisabled() && isServiceRunning())
          serviceStopped();

        if(restartDevs)
        {
          BLonDevice[] devs = (BLonDevice[])NmUtil.getDecendantsByClass(this, BLonDevice.class);
          for(int i=0 ; i<devs.length ; ++i) try { devs[i].started(); } catch(Exception e) {}
        }
      }

      if(prop==lonCommConfig)
      {
        // When lonComm config changes if network is not running and not out of service
        // then assume setting is attempt to fix problem and attempt start
        if(!isServiceRunning() && !isDisabled())
        {
          serviceStarted();

          // If that worked - need to call started on londevices
          if(isServiceRunning())
          {
            BLonDevice[] devs = (BLonDevice[])NmUtil.getDecendantsByClass(this, BLonDevice.class);
            for(int i=0 ; i<devs.length ; ++i)
              try{ devs[i].started(); } catch (Exception e){}
          }
        }
        // If already running let lonComm check for changes
        // that matter to it.
        else if(isServiceRunning())
        {
          try
          {
            lonComm.verifySettings();
            configOk();
          }
          catch(Throwable e)
          {
            log().log(Level.SEVERE,"Error initializing LonNetwork " + getDisplayName(null),e); 
            configFail(e.getMessage());
          }
        }
      }
  }

  /**
   * Override superclass to add localLonDevice to nav tree.
   */
  public BINavNode[] getNavChildren()
  {
    BINavNode[] kids = super.getNavChildren();
    Array<BINavNode> acc = new Array<>(BINavNode.class);
    acc.add(getLocalLonDevice());
    for(int i=0; i<kids.length; ++i)
      acc.add(kids[i]);
    return acc.trim();
  }

  public String getLogName()
  {
    return getLonCommConfig().getDeviceName().toLowerCase();
  }

////////////////////////////////////////////////////////////////
// Async support
////////////////////////////////////////////////////////////////


  /**
   * Post a task which needs to be synchronized with
   * other tasks on the same network.
   */
  public final IFuture postAsync(Runnable t)
  {
    if(!isServiceRunning()) return null;

    if( (workQueue==null) || !workQueue.isRunning())
      throw new NotRunningException();

    ((Queue)workQueue.getTodo()).enqueue(t);

    return null;
  }

  /**
   *  Set the workQueue priority one above normal to give it the
   *  advantage over the poll and ping threads for access to lonComm.
   */
  private void setWorkQueuePriority()
  {
    // Execute on the worker thread to bump its priority
    Runnable t = new  Runnable()
    {
      public void run()
      {
        Thread.currentThread().setPriority(Thread.NORM_PRIORITY+1);
      }
    };
    ((Queue)workQueue.getTodo()).enqueue(t);
  }

  /**
   * Post a task which needs to be synchronized with
   * other tasks on the same network. If a duplicate
   * post is made to the coalesceQueue queue before the
   * original post is executed the later post is ignored.
   * Duplicate post are detemined by calling the equals()
   * method on the posted object.
   */
  public final IFuture postWrite(Runnable t)
  {
    if(!isServiceRunning()) return null;

    if( (coalesceQueue==null) || !coalesceQueue.isRunning())
      throw new NotRunningException();

    ((Queue)coalesceQueue.getTodo()).enqueue(t);

    return null;
  }

  /** FOR INTERNAL USE */
  public final Worker getProxyQueue() { return proxyQueue;  }
  /** FOR INTERNAL USE */
  public final Worker getTimedQueue() { return timedQueue;  }

  protected Worker workQueue = null;
  protected Worker coalesceQueue = null;
  protected Worker proxyQueue = null;
  protected Worker timedQueue = null;

////////////////////////////////////////////////////////////
//  Utilities
////////////////////////////////////////////////////////////

  /** Utility to provide access to <code>LonComm</code> stack. */
  public final LonComm lonComm() { return lonComm; }

  public final NetMessageReceiver netMessageReceiver() { return netMessageReceiver; }
  public final NvManager          nvManager()          { return nvManager; }
  public final AddressManager     addressManager()     { return addressManager; }
  public final BLonNetmgmt        netmgmt()            { return getLonNetmgmt(); } //NmUtil.getNetmgmt(this);  }

  /** Get an array of routers that are descendants of this BLonNetwork. */
  public final BLonRouter[] getLonRouters()
  {
    return NmUtil.getLonRouters(this);
  }


  /** Get an array of devices that are descendants of this BLonNetwork. */
  public final BLonDevice[] getLonDevices()
  {
    return NmUtil.getLonDevices(this);
  }

  /** Find the LonDevice with the specified NeuronId. */
  public final BLonDevice findDevice(BNeuronId nid)
  {
    BLonDevice[] devs = getLonDevices();
    for(int i=0 ; i<devs.length ; i++)
      if(devs[i].getNeuronIdAddress().equals(nid))
        return devs[i];
    return null;
  }

  /** Find the LonRouter with the specified NeuronId. */
  public final BLonRouter findRouter(BNeuronId nid)
  {
    BLonRouter[] rtrs = getLonRouters();
    for(int i=0 ; i<rtrs.length ; i++)
      if(rtrs[i].getNeuronIdAddress().equals(nid))
        return rtrs[i];
    return null;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps("BLonNetwork");
    out.prop("serviceRunning", serviceRunning);
    out.prop("dataPntMismatchCount", dataPntMismatchCount);
    out.endProps();

    if(workQueue!=null)
    {
      out.trTitle("Async Queue",1);
      workQueue.spy(out);
    }
    if(coalesceQueue!=null)
    {
      out.trTitle("Coalescing Write Queue",1);
      coalesceQueue.spy(out);
    }
    if(proxyQueue!=null)
    {
      out.trTitle("Proxy Queue",1);
      proxyQueue.spy(out);
    }
    if(timedQueue!=null)
    {
      out.trTitle("Delay Queue",1);
      timedQueue.spy(out);
      ((TimedCoalesceQueue)timedQueue.getTodo()).spy(out);
    }
    if(nvManager!=null)
    {
      out.trTitle("NvManager",1);
      nvManager.spy(out);
    }
    if(addressManager!=null)
    {
      out.trTitle("Address Manager",1);
      ((NAddressManager)addressManager).spy(out);
    }

    NAppBuffer.spy(out);

    if(lonComm!=null) lonComm.spy(out);
  }
  public int dataPntMismatchCount = 0;

////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////
  public final Logger log()
  {
    if(log==null) log = Logger.getLogger(getLogName());
    return log;
  }

  private Logger log = null;

  private NetMessageReceiver netMessageReceiver = null;
  private NLonComm lonComm = null;
  private NvManager nvManager = null;
  private AddressManager addressManager = null;

////////////////////////////////////////////////////////////
// Static
////////////////////////////////////////////////////////////

  private static BFacets noWrite = BFacets.make("noWrite",true);
  private static BFacets noPropagate = BFacets.make("noPropagate",true);

  /**
   *  Context used when setting LonData elements to prevent write to physical device.
   */
  public static final Context lonNoWrite = new BasicContext(noWrite)
                    {
                      public boolean equals(Object obj) { return (obj!=null) && (obj instanceof Context) && ((Context)obj).getFacets().getb("noWrite",false); }

                      @Override
                      public int hashCode() { return super.hashCode(); }
  };
  /**
   *  Context used when setting LonData elements to prevent propagation of lon links.
   */
  public static final Context lonNoPropagate = new BasicContext(noPropagate)
                    {
                      public boolean equals(Object obj) { return (obj!=null) && (obj instanceof Context) && ((Context)obj).getFacets().getb("noPropagate",false); }
                      @Override
                      public int hashCode() { return super.hashCode(); }
                    };
  /**
   *  Context used when setting LonData elements to prevent propagation of lon links or write to physical device.
   */
  public static final Context lonNoPropagateNoWrite = new BasicContext(BFacets.make(noWrite, noPropagate));

}
