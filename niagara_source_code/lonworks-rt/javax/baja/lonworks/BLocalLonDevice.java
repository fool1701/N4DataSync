/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import static javax.baja.lonworks.BINetworkVariable.MAX_NV_INDEX;

import java.util.logging.Level;

import javax.baja.agent.AgentList;
import javax.baja.lonworks.datatypes.*;
import javax.baja.lonworks.enums.BLonNodeState;
import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.lonworks.enums.BLonSnvtType;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.Lon;
import com.tridium.lonworks.local.BLocalNci;
import com.tridium.lonworks.local.BLocalNv;
import com.tridium.lonworks.local.LocalDev;
import com.tridium.lonworks.local.SnvtInfo;
import com.tridium.lonworks.loncomm.NLonComm;
import com.tridium.lonworks.netmessages.QueryDomainResponse;
import com.tridium.lonworks.netmessages.UnprocessedNV;
import com.tridium.lonworks.netmessages.WriteMemRequest;
import com.tridium.lonworks.netmgmt.BLonNetmgmt;
import com.tridium.lonworks.util.Neuron;
import com.tridium.lonworks.util.NmUtil;
import com.tridium.lonworks.util.xif.LocalToXif;

/**
 * BLocalLonDevice represents the local interface to the lonworks
 * fieldbus. 
 *
 * @author    Robert Adams
 * @creation  06 Dec 00
 * @version   $Revision: 3$ $Date: 10/18/01 2:56:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 If true the local interface will be managed externally.
 */
@NiagaraProperty(
  name = "externalConfig",
  type = "boolean",
  defaultValue = "false"
)
/*
 Self documentation string.
 */
@NiagaraProperty(
  name = "selfDoc",
  type = "String",
  defaultValue = "&3.0@0;Niagara Server Node"
)
/*
 Send service pin message from local interface
 */
@NiagaraAction(
  name = "servicePin"
)
/*
 Import nvis, nvos and ncis from lonXml file.
 */
@NiagaraAction(
  name = "importXml",
  parameterType = "BLocalImportXmlParameter",
  defaultValue = "new BLocalImportXmlParameter()",
  flags = Flags.HIDDEN
)
/*
 Create an xif file representation of the local device.
 */
@NiagaraAction(
  name = "extractXif",
  parameterType = "BLocalExtractXifParameter",
  defaultValue = "new BLocalExtractXifParameter()",
  returnType = "BString",
  flags = Flags.HIDDEN
)
/*
 Wink command received by station
 */
@NiagaraTopic(
  name = "wink"
)
public class BLocalLonDevice
  extends BLonDevice
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLocalLonDevice(3568991044)1.0$ @*/
/* Generated Mon Nov 21 08:50:24 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "externalConfig"

  /**
   * Slot for the {@code externalConfig} property.
   * If true the local interface will be managed externally.
   * @see #getExternalConfig
   * @see #setExternalConfig
   */
  public static final Property externalConfig = newProperty(0, false, null);

  /**
   * Get the {@code externalConfig} property.
   * If true the local interface will be managed externally.
   * @see #externalConfig
   */
  public boolean getExternalConfig() { return getBoolean(externalConfig); }

  /**
   * Set the {@code externalConfig} property.
   * If true the local interface will be managed externally.
   * @see #externalConfig
   */
  public void setExternalConfig(boolean v) { setBoolean(externalConfig, v, null); }

  //endregion Property "externalConfig"

  //region Property "selfDoc"

  /**
   * Slot for the {@code selfDoc} property.
   * Self documentation string.
   * @see #getSelfDoc
   * @see #setSelfDoc
   */
  public static final Property selfDoc = newProperty(0, "&3.0@0;Niagara Server Node", null);

  /**
   * Get the {@code selfDoc} property.
   * Self documentation string.
   * @see #selfDoc
   */
  public String getSelfDoc() { return getString(selfDoc); }

  /**
   * Set the {@code selfDoc} property.
   * Self documentation string.
   * @see #selfDoc
   */
  public void setSelfDoc(String v) { setString(selfDoc, v, null); }

  //endregion Property "selfDoc"

  //region Action "servicePin"

  /**
   * Slot for the {@code servicePin} action.
   * Send service pin message from local interface
   * @see #servicePin()
   */
  public static final Action servicePin = newAction(0, null);

  /**
   * Invoke the {@code servicePin} action.
   * Send service pin message from local interface
   * @see #servicePin
   */
  public void servicePin() { invoke(servicePin, null, null); }

  //endregion Action "servicePin"

  //region Action "importXml"

  /**
   * Slot for the {@code importXml} action.
   * Import nvis, nvos and ncis from lonXml file.
   * @see #importXml(BLocalImportXmlParameter parameter)
   */
  public static final Action importXml = newAction(Flags.HIDDEN, new BLocalImportXmlParameter(), null);

  /**
   * Invoke the {@code importXml} action.
   * Import nvis, nvos and ncis from lonXml file.
   * @see #importXml
   */
  public void importXml(BLocalImportXmlParameter parameter) { invoke(importXml, parameter, null); }

  //endregion Action "importXml"

  //region Action "extractXif"

  /**
   * Slot for the {@code extractXif} action.
   * Create an xif file representation of the local device.
   * @see #extractXif(BLocalExtractXifParameter parameter)
   */
  public static final Action extractXif = newAction(Flags.HIDDEN, new BLocalExtractXifParameter(), null);

  /**
   * Invoke the {@code extractXif} action.
   * Create an xif file representation of the local device.
   * @see #extractXif
   */
  public BString extractXif(BLocalExtractXifParameter parameter) { return (BString)invoke(extractXif, parameter, null); }

  //endregion Action "extractXif"

  //region Topic "wink"

  /**
   * Slot for the {@code wink} topic.
   * Wink command received by station
   * @see #fireWink
   */
  public static final Topic wink = newTopic(0, null);

  /**
   * Fire an event for the {@code wink} topic.
   * Wink command received by station
   * @see #wink
   */
  public void fireWink(BValue event) { fire(wink, event, null); }

  //endregion Topic "wink"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalLonDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////
//  LonDevice api
////////////////////////////////////////////////////////////
  
  /** Receive nvUpdate.  If selector matches localNv/Nci then receive
   * update and return true. Otherwise return false. */
  public final boolean receiveNvUpdate(int sel, UnprocessedNV msg)
  {
    BINetworkVariable[] nvs = getNetworkVariables();
    for(int i=0 ; i<nvs.length ; i++)
    {
      BINetworkVariable nv = nvs[i];
      if(nv!=null && nv.getNvConfigData().getSelector()==sel)
      {
        if(msg.getDirection().equals(BLonNvDirection.output))
        {
          // This is a poll - send response
          if(nv.getNvConfigData().isOutput()) sendNvPollResponse(msg, nv);
        }
        else
        {
          if(nv.getNvConfigData().isInput()) nv.receiveUpdate(msg.getData());
        }
        return true;
      }  
    }
    return false;
  }
  
  private void sendNvPollResponse(UnprocessedNV msg, BINetworkVariable nv)
  {
    try
    {
      BNvConfigData configData = nv.getNvConfigData();
      UnprocessedNV  rsp = new UnprocessedNV( configData.getDirection(),
                                              configData.getSelector(),
                                              nv.getData().toNetBytes());
      lonComm().sendResponse(msg,rsp);
    }
    catch (LonException e)
    {
      lonNetwork().log().log(Level.SEVERE,"Unable to create or send nvPoll response.", e);
    }
  }

////////////////////////////////////////////////////////////
//  LonDevice overrides
////////////////////////////////////////////////////////////

  /**
   * Returns true to indicates that programId may be changed 
   * during commissioning. 
   */
  public final boolean programIdChanges() { return true; }

  /**
   *  Is this the local lonDivice. 
   *  <p>
   *  @return Always return true.
   */ 
  public final boolean isLocal() { return true; }

  /** Use ping to check for changes to device data if externalConfig is set true */
  public void doPing()
  {
    if( !isRunning() || !getEnabled() || (!lonNetwork().isServiceRunning()) ) return;

    if(getExternalConfig())
    {
      try
      {
        // NOTE: certain network management messages to the local device put the neuron
        // in a state which will fail incoming request messages for up to 400mSec.  Add a delay
        // sufficient to allow recovery between queries of neuron tables.
        BDeviceData dd = getDeviceData();
        dd.set(BDeviceData.nodeState, NmUtil.getDeviceState(this), AddressManager.noDeviceChange);
        // Update address table
        for(int i=0 ; i<dd.getAddressCount() ; i++)
        {
          dd.setAddressEntry(i, NmUtil.getBAddressTableEntry(this,i), AddressManager.noDeviceChange);
          NmUtil.wait(600);
        }
        // Update channelId
        dd.setInt(BDeviceData.channelId, Neuron.getChannelId(this), AddressManager.noDeviceChange);
        NmUtil.wait(600);

        // Determine working domain
        QueryDomainResponse qd0 = NmUtil.queryDomain(this, 0); NmUtil.wait(600);
        QueryDomainResponse qd1 = NmUtil.queryDomain(this, 1);
        QueryDomainResponse wrkg = qd0;

        int wrkgDmn = 0;
        if( qd1.inUse() && (!qd0.inUse() ||
                           (qd0.getLen()==0 && qd1.getLen()>=1 )) )
        {
          wrkgDmn = 1;
          wrkg = qd1;
        }  
                            
        // Update domain data
        dd.set(BDeviceData.subnetNodeId, BSubnetNode.make(wrkg.getSubnet(),wrkg.getNodeId()), AddressManager.noDeviceChange);
        dd.setInt(BDeviceData.workingDomain, wrkgDmn, AddressManager.noDeviceChange);

        BLonNetmgmt netmgmt = lonNetwork().netmgmt();
        netmgmt.set(BLonNetmgmt.domainId, BDomainId.make(wrkg.getLen(),wrkg.getDomainId()),BLonNetwork.lonNoWrite);
        netmgmt.set(BLonNetmgmt.authenticationKey, BAuthenticationKey.make(wrkg.getKey()),BLonNetwork.lonNoWrite);
      }
      catch(LonException e)
      {
        lonNetwork().log().log(Level.SEVERE,"Unable to initialize local lon interface.", e); 
      }
    }

    pingOk();
  }
  
  /**
   * Override to remove NvManager and NcManager.
   */
  public AgentList getAgents(Context cx)
  {
    AgentList alist = super.getAgents(cx);

    alist.remove("lonworks:NcManager");
    alist.remove("lonworks:NvManager");

    return alist;
  }


////////////////////////////////////////////////////////////
//  Implementation
////////////////////////////////////////////////////////////
  public void started()
    throws Exception
  {
    super.started();
    verifyNvIndices();
    verifyLonMark();
    verifyNetmgmtEnable();
  }
    
  public void forceStart()
  {
    lonDeviceInit();
  }  
  
  /** Make sure the local Neuron settings match DeviceData. */
  protected void lonDeviceInit()
  {
    // NCCB-18049 - If s/n not initialized set to default.
    // Do before registered with address manager.
    if(getSubnetNodeAddress().equals(BSubnetNode.DEFAULT))
    {
      getDeviceData().setSubnetNodeId(BSubnetNode.make(1,127));
    }

    // Do on workQueue thread
    runAsyncUpdate();
  } 
  
  private void runAsyncUpdate()
  {   
    Runnable req = new Runnable()
      {
        public void run() { asyncUpdate(); }
      };
 
    lonNetwork().postAsync(req);
  }
  
  private void asyncUpdate()
  { 
    try
    {
      // Do this because first message will timeout after read of LonNetwork on embedded platform
      try{ NmUtil.queryStatus(this,0); } 
      catch(LonException e) { try{ NmUtil.queryStatus(this,0); } catch(LonException e1) {} }
      
      BDeviceData dd = getDeviceData();
      BNeuronId nid = Neuron.getNeuronId(this);
      
      // If the neuronId does not match
      if(!dd.getNeuronId().equals(nid))
      {
        // Get these from local interface
        dd.set(BDeviceData.neuronId, nid, AddressManager.noDeviceChange); 
       // dd.set(BDeviceData.programId, Neuron.getProgramId(this), AddressManager.noDeviceChange);
        dd.setBoolean(BDeviceData.twoDomains, Neuron.isTwoDomains(lonComm(), BLocal.local, false, false), AddressManager.noDeviceChange);

        // Force these to default 
        dd.setInt(BDeviceData.channelId, 1, AddressManager.noDeviceChange);
        dd.set(BDeviceData.subnetNodeId, BSubnetNode.make(1,127), AddressManager.noDeviceChange);
        dd.setBoolean(BDeviceData.hosted, true, AddressManager.noDeviceChange);
        dd.set(BDeviceData.programId, BProgramId.TRIDIUM_PID, AddressManager.noDeviceChange);
        dd.set(BDeviceData.nodeState, BLonNodeState.configOnline, AddressManager.noDeviceChange);

      }
      // Must always do this check to deal with installed Titan lon expansions that only had 1 address entry
      dd.setInt(BDeviceData.addressCount, Neuron.getAddressCount(lonComm(),BLocal.local,false,false), AddressManager.noDeviceChange);
      // If address table has more than 15 entries must use extended device data. This is true for Titan expansion boards.
      if(dd.getAddressCount()>15 && !(dd instanceof BExtDeviceData))
      {
        BExtDeviceData edd = BExtDeviceData.make(dd);
        edd.setExtended(false);
        setDeviceData(edd);
      }


      // Verify addresses and state
      BLonNetmgmt nm = lonNetwork().netmgmt();
      
      NmUtil.updateDomainTable(this, nm.getDomainId(), nm.getAuthenticationKey(), false);
      NmUtil.updateAddressTable(this);
      Neuron.updateChannelId(this);
      NmUtil.setDeviceState(this, getDeviceData().getNodeState());
      writeProgramId(getDeviceData().getProgramId());
      
      // ping to make sure local device gets set to up state quickly
      ping();

    }
    catch(LonException e)
    {
      lonNetwork().log().log(Level.SEVERE,"Error initializing local lon interface.", e); 
      e.printStackTrace();
    }

    lonNetwork().netMessageReceiver().okToReceive();
    // This is needed to clear Neuron transaction manager after all lonComm components working NCCB-8244
    if(!Lon.disableResetOnStart() ) doReset();  // check flag to disable this reset

  }
  
  /**
   *  Receive notification of change to device data. Update local interface for
   * subnetNode address, domainId, authenticationKey,  changes. 
   * <p>If context is AddressManager.noDeviceChange then don't update interface.
   */
  public void deviceDataChanged(Property prop, Context context)
  {
    if(prop == BDeviceData.workingDomain) ((NLonComm)getLonNetwork().lonComm()).updateWorkingDomain();

    
    if(context==AddressManager.noDeviceChange) return;
    
    // ignore prop == BDeviceData.nodeState -  changes handled in BDeviceData
    if( (prop == BDeviceData.authenticate) ||
        (prop == BDeviceData.addressTable) ||
        (prop == BDeviceData.subnetNodeId) ||
        (prop == BDeviceData.programId) ||
        (prop == BDeviceData.workingDomain) ||
        (prop == BDeviceData.channelId) )
    {
      runAsyncUpdate();
    }  
  }
  
  /** Override for changed(). */
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    
    if(!isRunning()) return;
    
    if(prop==externalConfig) verifyNetmgmtEnable();
  }  
  
  private void verifyNetmgmtEnable()
  {
    // Disable netmgmt if local set for external configuration
    lonNetwork().netmgmt().setEnabled(!getExternalConfig());
  }  

  /** Override for changed(). 
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);

    if(prop==commType)
    {
      try
      { 
        byte[] a = Neuron.readMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                             BLocal.local, 0x9, 1, false, false);
        System.out.println("update commType to " + getCommType());  
        a[0] = (byte)((a[0] & 0x1F) + (getCommType()<<5));
     
        Neuron.writeMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                           BLocal.local, 0x9, NetMessages.CNFG_CS_RECALC, a, false, false);
      }
      catch(LonException e)
      {
          System.out.println(e);
      } 
    }
    if(prop==commPinDir)
    {
      try
      { 
        byte[] a = Neuron.readMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                              BLocal.local, 0x9, 1, false, false);
        System.out.println("update commType to " + getCommType());  
        a[0] = (byte)((a[0] & 0x070) + (getCommPinDir() & 0x1F));
        
        Neuron.writeMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                              BLocal.local, 0x9, NetMessages.CNFG_CS_RECALC, a, false, false);
      } 
      catch(LonException e)
      {
        System.out.println(e);
      } 
    }
    if(prop==preambleLen)
    {
      try
      { 
        System.out.println("update preambleLen to " + getPreambleLen());  
        byte[] a = new byte[0];
        a[0]= (byte)getPreambleLen();
        
        Neuron.writeMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                              BLocal.local, 0x0a, NetMessages.CNFG_CS_RECALC, a, false, false);
      }
      catch(LonException e)
      {
        System.out.println(e);
      } 
    }
    if(prop==packetCycle)
    {
      try
      { 
        System.out.println("update preambleLen to " + getPacketCycle());  
        byte[] a = new byte[0];
        a[0]= (byte)getPacketCycle();
        
        Neuron.writeMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                              BLocal.local, 0x0b, NetMessages.CNFG_CS_RECALC, a, false, false);
      }
      catch(LonException e)
      {
        System.out.println(e);
      } 
    }
    if(prop==betaControl)
    {
      try
      { 
        System.out.println("update preambleLen to " + getBetaControl());  
        byte[] a = new byte[0];
        a[0]= (byte)getBetaControl();
        
        Neuron.writeMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                              BLocal.local, 0x0c, NetMessages.CNFG_CS_RECALC, a, false, false);
      }
      catch(LonException e)
      {
        System.out.println(e);
      } 
    }
    if(prop==xmitInter)
    {
      try
      { 
        System.out.println("update preambleLen to " + getXmitInter());  
        byte[] a = new byte[0];
        a[0]= (byte)getXmitInter();
        
        Neuron.writeMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                              BLocal.local, 0x0d, NetMessages.CNFG_CS_RECALC, a, false, false);
      }
      catch(LonException e)
      {
        System.out.println(e);
      } 
    }
    if(prop==recvInter)
    {
      try
      { 
        System.out.println("update preambleLen to " + getRecvInter());  
        byte[] a = new byte[0];
        a[0]= (byte)getRecvInter();
        
        Neuron.writeMemory(lonComm(), NetMessages.CONFIG_RELATIVE,
                              BLocal.local, 0x0e, NetMessages.CNFG_CS_RECALC, a, false, false);
      }
      catch(LonException e)
      {
        System.out.println(e);
      } 
    }
  }
*/  
  /**
   * Implement servicePin action.
   */
  public final void doServicePin()
  {
    try
    {
      NmUtil.sendServicePin(this);
    }
    catch(LonException e)
    {
      throw new BajaRuntimeException("Unable to do servicePin." + e);
    }
  }
  
  private void writeProgramId(BProgramId pid)
    throws LonException
  {
    byte[] a = pid.getByteArray();
    
    // Attempt to write new program id to neuron
    WriteMemRequest writeReq = new WriteMemRequest(1, //NetMessages.READ_ONLY_RELATIVE,
                                       0x0D,
                                       a.length, 3,//  *NetMessages.NO_ACTION*/
                                       a);
    lonComm().sendUnacknowledged(BLocal.local, writeReq);
  }  
  
 // protected static final BProgramId tridiumPid  = BProgramId.make(9, BLonMfgId.tridium, 0x0103,0x8000,0x3);
    
  
////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////

  public final BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("deviceLocal.png");

////////////////////////////////////////////////////////////
//  Local Nv support
////////////////////////////////////////////////////////////

  public boolean isChildLegal(BComponent child)
  {
    // Nv/Nci must be localNv/localNci
    if( child.getType().is(BINetworkVariable.TYPE) && 
        !((BINetworkVariable)child).isLocalNv() && !((BINetworkVariable)child).isLocalNci())
    {
      return false;
    }      
    return super.isChildLegal(child); 
  }

  public void added(Property prop, Context context)
  {
    super.added(prop,context);
    
    if(isRunning() && !noInfoChange.equals(context))
    {
      if(prop.getType().is(BINetworkVariable.TYPE)) verifyNvIndices();
      updateSnvtInfo();
    }  
  }

  public void removed(Property prop, BValue value, Context context)
  {
    super.removed(prop,value,context);
    
    if(isRunning() && !noInfoChange.equals(context))
    {
      if(prop.getType().is(BINetworkVariable.TYPE)) verifyNvIndices();
      updateSnvtInfo();
    }  
  }  
  
  // Add standard required lonmark nvs if no nvs in device
  private void verifyLonMark()
  {
    BINetworkVariable[] a = getNetworkVariables();
    if(a.length>0) return;
    
    BLocalNv nv =  new BLocalNv(0,BLonSnvtType.SNVT_OBJ_REQUEST,0,1,0,BLonNvDirection.input, "@0|1");
    add("nviRequest",nv,Flags.FAN_IN);
 
    nv =  new BLocalNv(1,BLonSnvtType.SNVT_OBJ_STATUS,0,2,0,BLonNvDirection.output, "@0|1");
    add("nvoStatus",nv,0);
  }
  
  // Remove any gaps in nv indices.
  private void verifyNvIndices()
  {
    BINetworkVariable[] a = getNetworkVariables();
    boolean stale = false;
    for(int i=0,n=0;i < a.length; i++) 
    {
      if(a[i]==null)
      {
        // If there are empty slots then need to refresh nv list
        stale=true;
        continue;
      }  
      
      if(a[i].getNvIndex()!=n)
      {
        if(a[i].isLocalNv())
          ((BLocalNv)a[i]).getNvProps().setInt(BNvProps.nvIndex,n,noInfoChange);
        else if(a[i].isLocalNci())
          ((BLocalNci)a[i]).getNcProps().setInt(BNcProps.nvIndex,n,noInfoChange);
          
        stale=true;
        
        // Make sure unbound selector continues to match nvIndex.
        // ?? Should changing the nvIndices force the nvs to an unbound state if bound???
        BNvConfigData cd = a[i].getNvConfigData();
        if(!cd.isBoundNv()) cd.setInt(BNvConfigData.selector,BNvConfigData.UNBOUND_NV_BASE_SELECTOR - n,noInfoChange);
      }  
      n++;
    } 
    if(stale) refreshNvList();
  }
  
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context) 
  {
    super.checkAdd(name, value, flags, facets, context);
    if(!isRunning()) return;
    
    if(!value.getType().is(BINetworkVariable.TYPE)) return;
    BINetworkVariable nv = (BINetworkVariable)value;

    //
    // Initialize NvIndex to unique value
    //
   
    // Use selector to detect uninitialized nvs
    if(nv.getNvConfigData().getSelector() >= 0) return;
    
    // Get array of nvs - indexed by nvIndex. If null entry found use that
    // index - otherwise use next available.
    BINetworkVariable[] a = getNetworkVariables();
    int nvIndex = 0;
    for(; nvIndex < a.length ; nvIndex++) 
      if(a[nvIndex]==null) break;

    // Limit nvIndex to 0xFFF - NCCB-38281
    if(nvIndex>MAX_NV_INDEX)
      throw new LocalizableRuntimeException("lonworks", "check.add.maxNvIndex");
    
    // PacMan 13957
    // Put this nv in empty slot or add to end.  This is needed for copy of multiple nvs
    // because the copy operation calls check add on each instance before adding any previous
    // ones.  This causes all nvs in a batch to resolved to the same next available nvIndex.
    if(nvIndex>=a.length)
    {
      BINetworkVariable[] newA = new BINetworkVariable[nvIndex+1];
      System.arraycopy(a, 0, newA, 0, a.length);
      a = nvList = newA;
    }
    a[nvIndex]=nv;
    
    nv.setNvIndex(nvIndex);
    nv.getNvConfigData().setUnbound(nvIndex);
        
  }

  protected void lonDeviceAtSteadyState() { updateSnvtInfo(); }
 
  public void renamed(Property prop, String oldName, Context context)
  {
    super.renamed(prop, oldName, context);
    if(!isRunning() || context==noInfoChange) return;
    if(prop.getType().is(BINetworkVariable.TYPE)) updateSnvtInfo();
  }  
  
  /**
   * For internal use.  Callback for LocalNvi/Nvo/Nci to indicated need to update
   * local device self documentation.
   */
  public void nvChanged(BINetworkVariable inv)
  {
    updateSnvtInfo();
  }
  
  
  /**
   * Call when property is changed which requires regeneration of snvt info.
   */
  private void updateSnvtInfo()
  {
   //System.out.println("BLocalLonDevice.updateSnvtInfo ");

    synchronized (syncSnvtInfo)
    {
      snvtInfo = null;
    }

//    // Generate new snvtinfo on the workQueue thread
//    Runnable req = new Runnable()
//        {
//          public void run()
//          {
//            synchronized (syncSnvtInfo)
//            {
//              snvtInfo = null;
//              getSnvtInfo();
//            }
//          }
//        };
//
//    lonNetwork().postAsync(req);
  }

  /** Access the SnvtInfo for this LocalLonDevice.  Create new instance if needed. */
  public final SnvtInfo getSnvtInfo()
  {
    synchronized (syncSnvtInfo)
    {
      if (snvtInfo == null) snvtInfo = new SnvtInfo(this);
      return snvtInfo;
    }
  }
  
  public void doImportXml(BLocalImportXmlParameter p)
  {
    synchronized (syncSnvtInfo)
    {
      LocalDev.importXLon(this, p);
      snvtInfo = null;
      verifyNvIndices();
      refreshNvList();
    }
  }
  
  public BString doExtractXif(BLocalExtractXifParameter p)
  {
    String rtn = "error";
    try
    { 
      rtn = LocalToXif.extractXif(this, p);
    }
    catch (Exception e)
    {
      rtn = e.toString();
    }
    return BString.make(rtn);
  }
   
  private SnvtInfo snvtInfo = null;
  private Object   syncSnvtInfo = new Object();
  
  private static BFacets noChange = BFacets.make("noChange",true);

  public static final Context noInfoChange = new BasicContext(noChange)
                    {
                      public boolean equals(Object obj) 
                      { 
                        return (obj!=null) && 
                               (obj instanceof Context) && 
                               ((Context)obj).getFacets().getb("noChange",false); 
                      }
                      @Override
                      public int hashCode() { return super.hashCode(); }

                    };
 
  

}
