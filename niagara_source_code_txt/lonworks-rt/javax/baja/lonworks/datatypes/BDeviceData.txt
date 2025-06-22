/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.lonworks.AddressManager;
import javax.baja.lonworks.BLonDevice;
import javax.baja.lonworks.LonException;
import javax.baja.lonworks.enums.BLonNodeState;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.BLonRouter;
import com.tridium.lonworks.NAddressManager;
import com.tridium.lonworks.device.DeviceFacets;
import com.tridium.lonworks.util.NmUtil;

/**
 *   This class file contains the data needed to represent a specific
 * neuron chip including state information and configurable data in the
 * neuron tables as described in the Appendix A: Neuron Chip Data
 * Structures in the Neuron Chip Data Book.
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:36 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 unique 6 byte identifier of Neuron Chip
 */
@NiagaraProperty(
  name = "neuronId",
  type = "BNeuronId",
  defaultValue = "BNeuronId.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 8 byte program identifier supplied by device manufacturer
 */
@NiagaraProperty(
  name = "programId",
  type = "BProgramId",
  defaultValue = "BProgramId.DEFAULT",
  flags = Flags.SUMMARY
)
/*
 the current configured state of the lon device
 */
@NiagaraProperty(
  name = "nodeState",
  type = "BLonNodeState",
  defaultValue = "BLonNodeState.unknown",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 subnet node address of this lon device
 */
@NiagaraProperty(
  name = "subnetNodeId",
  type = "BSubnetNode",
  defaultValue = "BSubnetNode.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
/*
 optional location string supplied by user
 */
@NiagaraProperty(
  name = "location",
  type = "String",
  defaultValue = ""
)
/*
 flag indicating that authentication is enabled in device
 */
@NiagaraProperty(
  name = "authenticate",
  type = "boolean",
  defaultValue = "false"
)
/*
 channel identifier assigned to this device
 */
@NiagaraProperty(
  name = "channelId",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 the domain table index containing the working domain
 */
@NiagaraProperty(
  name = "workingDomain",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 flag indicating if the device uses new binding constraints.
 */
@NiagaraProperty(
  name = "bindingII",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 flag indicating if the device is a hosted node
 */
@NiagaraProperty(
  name = "hosted",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 flag indicating if the devices' domain table has two entries
 */
@NiagaraProperty(
  name = "twoDomains",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 the number of message tags used by the device
 */
@NiagaraProperty(
  name = "msgTagCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 the number of entries in this devices' address table
 */
@NiagaraProperty(
  name = "addressCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 the devices' address table
 */
@NiagaraProperty(
  name = "addressTable",
  type = "BAddressTable",
  defaultValue = "new BAddressTable()",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
/*
 the devices' address table
 */
@NiagaraProperty(
  name = "extAddressTable",
  type = "BExtAddressTable",
  defaultValue = "new BExtAddressTable()",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "prioritySlot",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 the number of network variable aliases supported on the device
 */
@NiagaraProperty(
  name = "aliasTable",
  type = "BAliasTable",
  defaultValue = "new BAliasTable()",
  flags = Flags.READONLY
)
/*
 selfdocumentation string
 */
@NiagaraProperty(
  name = "selfDoc",
  type = "String",
  defaultValue = ""
)
/*
 flag indicating if devices has a node object
 */
@NiagaraProperty(
  name = "hasNodeObject",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "freezeChannelPriorities",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "lastHash",
  type = "int",
  defaultValue = "-1",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT
)
/*
 Facets used to configure network management operations.
 delayToReset - mSec delay inserted before reset during commissioning
 delayToHardOffline - mSec delay inserted before setting to hardOffline during commissioning
 minNvUpdateInterMsgDelay - minimum delay between nv updates to device
 disableSetOfflineInBind - do not set device offline when modifying address table during a bind operation
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
public class BDeviceData
  extends BComponent
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BDeviceData(3810850883)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "neuronId"

  /**
   * Slot for the {@code neuronId} property.
   * unique 6 byte identifier of Neuron Chip
   * @see #getNeuronId
   * @see #setNeuronId
   */
  public static final Property neuronId = newProperty(Flags.DEFAULT_ON_CLONE, BNeuronId.DEFAULT, null);

  /**
   * Get the {@code neuronId} property.
   * unique 6 byte identifier of Neuron Chip
   * @see #neuronId
   */
  public BNeuronId getNeuronId() { return (BNeuronId)get(neuronId); }

  /**
   * Set the {@code neuronId} property.
   * unique 6 byte identifier of Neuron Chip
   * @see #neuronId
   */
  public void setNeuronId(BNeuronId v) { set(neuronId, v, null); }

  //endregion Property "neuronId"

  //region Property "programId"

  /**
   * Slot for the {@code programId} property.
   * 8 byte program identifier supplied by device manufacturer
   * @see #getProgramId
   * @see #setProgramId
   */
  public static final Property programId = newProperty(Flags.SUMMARY, BProgramId.DEFAULT, null);

  /**
   * Get the {@code programId} property.
   * 8 byte program identifier supplied by device manufacturer
   * @see #programId
   */
  public BProgramId getProgramId() { return (BProgramId)get(programId); }

  /**
   * Set the {@code programId} property.
   * 8 byte program identifier supplied by device manufacturer
   * @see #programId
   */
  public void setProgramId(BProgramId v) { set(programId, v, null); }

  //endregion Property "programId"

  //region Property "nodeState"

  /**
   * Slot for the {@code nodeState} property.
   * the current configured state of the lon device
   * @see #getNodeState
   * @see #setNodeState
   */
  public static final Property nodeState = newProperty(Flags.DEFAULT_ON_CLONE, BLonNodeState.unknown, null);

  /**
   * Get the {@code nodeState} property.
   * the current configured state of the lon device
   * @see #nodeState
   */
  public BLonNodeState getNodeState() { return (BLonNodeState)get(nodeState); }

  /**
   * Set the {@code nodeState} property.
   * the current configured state of the lon device
   * @see #nodeState
   */
  public void setNodeState(BLonNodeState v) { set(nodeState, v, null); }

  //endregion Property "nodeState"

  //region Property "subnetNodeId"

  /**
   * Slot for the {@code subnetNodeId} property.
   * subnet node address of this lon device
   * @see #getSubnetNodeId
   * @see #setSubnetNodeId
   */
  public static final Property subnetNodeId = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, BSubnetNode.DEFAULT, null);

  /**
   * Get the {@code subnetNodeId} property.
   * subnet node address of this lon device
   * @see #subnetNodeId
   */
  public BSubnetNode getSubnetNodeId() { return (BSubnetNode)get(subnetNodeId); }

  /**
   * Set the {@code subnetNodeId} property.
   * subnet node address of this lon device
   * @see #subnetNodeId
   */
  public void setSubnetNodeId(BSubnetNode v) { set(subnetNodeId, v, null); }

  //endregion Property "subnetNodeId"

  //region Property "location"

  /**
   * Slot for the {@code location} property.
   * optional location string supplied by user
   * @see #getLocation
   * @see #setLocation
   */
  public static final Property location = newProperty(0, "", null);

  /**
   * Get the {@code location} property.
   * optional location string supplied by user
   * @see #location
   */
  public String getLocation() { return getString(location); }

  /**
   * Set the {@code location} property.
   * optional location string supplied by user
   * @see #location
   */
  public void setLocation(String v) { setString(location, v, null); }

  //endregion Property "location"

  //region Property "authenticate"

  /**
   * Slot for the {@code authenticate} property.
   * flag indicating that authentication is enabled in device
   * @see #getAuthenticate
   * @see #setAuthenticate
   */
  public static final Property authenticate = newProperty(0, false, null);

  /**
   * Get the {@code authenticate} property.
   * flag indicating that authentication is enabled in device
   * @see #authenticate
   */
  public boolean getAuthenticate() { return getBoolean(authenticate); }

  /**
   * Set the {@code authenticate} property.
   * flag indicating that authentication is enabled in device
   * @see #authenticate
   */
  public void setAuthenticate(boolean v) { setBoolean(authenticate, v, null); }

  //endregion Property "authenticate"

  //region Property "channelId"

  /**
   * Slot for the {@code channelId} property.
   * channel identifier assigned to this device
   * @see #getChannelId
   * @see #setChannelId
   */
  public static final Property channelId = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code channelId} property.
   * channel identifier assigned to this device
   * @see #channelId
   */
  public int getChannelId() { return getInt(channelId); }

  /**
   * Set the {@code channelId} property.
   * channel identifier assigned to this device
   * @see #channelId
   */
  public void setChannelId(int v) { setInt(channelId, v, null); }

  //endregion Property "channelId"

  //region Property "workingDomain"

  /**
   * Slot for the {@code workingDomain} property.
   * the domain table index containing the working domain
   * @see #getWorkingDomain
   * @see #setWorkingDomain
   */
  public static final Property workingDomain = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code workingDomain} property.
   * the domain table index containing the working domain
   * @see #workingDomain
   */
  public int getWorkingDomain() { return getInt(workingDomain); }

  /**
   * Set the {@code workingDomain} property.
   * the domain table index containing the working domain
   * @see #workingDomain
   */
  public void setWorkingDomain(int v) { setInt(workingDomain, v, null); }

  //endregion Property "workingDomain"

  //region Property "bindingII"

  /**
   * Slot for the {@code bindingII} property.
   * flag indicating if the device uses new binding constraints.
   * @see #getBindingII
   * @see #setBindingII
   */
  public static final Property bindingII = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code bindingII} property.
   * flag indicating if the device uses new binding constraints.
   * @see #bindingII
   */
  public boolean getBindingII() { return getBoolean(bindingII); }

  /**
   * Set the {@code bindingII} property.
   * flag indicating if the device uses new binding constraints.
   * @see #bindingII
   */
  public void setBindingII(boolean v) { setBoolean(bindingII, v, null); }

  //endregion Property "bindingII"

  //region Property "hosted"

  /**
   * Slot for the {@code hosted} property.
   * flag indicating if the device is a hosted node
   * @see #getHosted
   * @see #setHosted
   */
  public static final Property hosted = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code hosted} property.
   * flag indicating if the device is a hosted node
   * @see #hosted
   */
  public boolean getHosted() { return getBoolean(hosted); }

  /**
   * Set the {@code hosted} property.
   * flag indicating if the device is a hosted node
   * @see #hosted
   */
  public void setHosted(boolean v) { setBoolean(hosted, v, null); }

  //endregion Property "hosted"

  //region Property "twoDomains"

  /**
   * Slot for the {@code twoDomains} property.
   * flag indicating if the devices' domain table has two entries
   * @see #getTwoDomains
   * @see #setTwoDomains
   */
  public static final Property twoDomains = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code twoDomains} property.
   * flag indicating if the devices' domain table has two entries
   * @see #twoDomains
   */
  public boolean getTwoDomains() { return getBoolean(twoDomains); }

  /**
   * Set the {@code twoDomains} property.
   * flag indicating if the devices' domain table has two entries
   * @see #twoDomains
   */
  public void setTwoDomains(boolean v) { setBoolean(twoDomains, v, null); }

  //endregion Property "twoDomains"

  //region Property "msgTagCount"

  /**
   * Slot for the {@code msgTagCount} property.
   * the number of message tags used by the device
   * @see #getMsgTagCount
   * @see #setMsgTagCount
   */
  public static final Property msgTagCount = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code msgTagCount} property.
   * the number of message tags used by the device
   * @see #msgTagCount
   */
  public int getMsgTagCount() { return getInt(msgTagCount); }

  /**
   * Set the {@code msgTagCount} property.
   * the number of message tags used by the device
   * @see #msgTagCount
   */
  public void setMsgTagCount(int v) { setInt(msgTagCount, v, null); }

  //endregion Property "msgTagCount"

  //region Property "addressCount"

  /**
   * Slot for the {@code addressCount} property.
   * the number of entries in this devices' address table
   * @see #getAddressCount
   * @see #setAddressCount
   */
  public static final Property addressCount = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code addressCount} property.
   * the number of entries in this devices' address table
   * @see #addressCount
   */
  public int getAddressCount() { return getInt(addressCount); }

  /**
   * Set the {@code addressCount} property.
   * the number of entries in this devices' address table
   * @see #addressCount
   */
  public void setAddressCount(int v) { setInt(addressCount, v, null); }

  //endregion Property "addressCount"

  //region Property "addressTable"

  /**
   * Slot for the {@code addressTable} property.
   * the devices' address table
   * @see #getAddressTable
   * @see #setAddressTable
   */
  public static final Property addressTable = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, new BAddressTable(), null);

  /**
   * Get the {@code addressTable} property.
   * the devices' address table
   * @see #addressTable
   */
  public BAddressTable getAddressTable() { return (BAddressTable)get(addressTable); }

  /**
   * Set the {@code addressTable} property.
   * the devices' address table
   * @see #addressTable
   */
  public void setAddressTable(BAddressTable v) { set(addressTable, v, null); }

  //endregion Property "addressTable"

  //region Property "extAddressTable"

  /**
   * Slot for the {@code extAddressTable} property.
   * the devices' address table
   * @see #getExtAddressTable
   * @see #setExtAddressTable
   */
  public static final Property extAddressTable = newProperty(Flags.HIDDEN, new BExtAddressTable(), null);

  /**
   * Get the {@code extAddressTable} property.
   * the devices' address table
   * @see #extAddressTable
   */
  public BExtAddressTable getExtAddressTable() { return (BExtAddressTable)get(extAddressTable); }

  /**
   * Set the {@code extAddressTable} property.
   * the devices' address table
   * @see #extAddressTable
   */
  public void setExtAddressTable(BExtAddressTable v) { set(extAddressTable, v, null); }

  //endregion Property "extAddressTable"

  //region Property "prioritySlot"

  /**
   * Slot for the {@code prioritySlot} property.
   * @see #getPrioritySlot
   * @see #setPrioritySlot
   */
  public static final Property prioritySlot = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code prioritySlot} property.
   * @see #prioritySlot
   */
  public int getPrioritySlot() { return getInt(prioritySlot); }

  /**
   * Set the {@code prioritySlot} property.
   * @see #prioritySlot
   */
  public void setPrioritySlot(int v) { setInt(prioritySlot, v, null); }

  //endregion Property "prioritySlot"

  //region Property "aliasTable"

  /**
   * Slot for the {@code aliasTable} property.
   * the number of network variable aliases supported on the device
   * @see #getAliasTable
   * @see #setAliasTable
   */
  public static final Property aliasTable = newProperty(Flags.READONLY, new BAliasTable(), null);

  /**
   * Get the {@code aliasTable} property.
   * the number of network variable aliases supported on the device
   * @see #aliasTable
   */
  public BAliasTable getAliasTable() { return (BAliasTable)get(aliasTable); }

  /**
   * Set the {@code aliasTable} property.
   * the number of network variable aliases supported on the device
   * @see #aliasTable
   */
  public void setAliasTable(BAliasTable v) { set(aliasTable, v, null); }

  //endregion Property "aliasTable"

  //region Property "selfDoc"

  /**
   * Slot for the {@code selfDoc} property.
   * selfdocumentation string
   * @see #getSelfDoc
   * @see #setSelfDoc
   */
  public static final Property selfDoc = newProperty(0, "", null);

  /**
   * Get the {@code selfDoc} property.
   * selfdocumentation string
   * @see #selfDoc
   */
  public String getSelfDoc() { return getString(selfDoc); }

  /**
   * Set the {@code selfDoc} property.
   * selfdocumentation string
   * @see #selfDoc
   */
  public void setSelfDoc(String v) { setString(selfDoc, v, null); }

  //endregion Property "selfDoc"

  //region Property "hasNodeObject"

  /**
   * Slot for the {@code hasNodeObject} property.
   * flag indicating if devices has a node object
   * @see #getHasNodeObject
   * @see #setHasNodeObject
   */
  public static final Property hasNodeObject = newProperty(0, false, null);

  /**
   * Get the {@code hasNodeObject} property.
   * flag indicating if devices has a node object
   * @see #hasNodeObject
   */
  public boolean getHasNodeObject() { return getBoolean(hasNodeObject); }

  /**
   * Set the {@code hasNodeObject} property.
   * flag indicating if devices has a node object
   * @see #hasNodeObject
   */
  public void setHasNodeObject(boolean v) { setBoolean(hasNodeObject, v, null); }

  //endregion Property "hasNodeObject"

  //region Property "freezeChannelPriorities"

  /**
   * Slot for the {@code freezeChannelPriorities} property.
   * @see #getFreezeChannelPriorities
   * @see #setFreezeChannelPriorities
   */
  public static final Property freezeChannelPriorities = newProperty(0, false, null);

  /**
   * Get the {@code freezeChannelPriorities} property.
   * @see #freezeChannelPriorities
   */
  public boolean getFreezeChannelPriorities() { return getBoolean(freezeChannelPriorities); }

  /**
   * Set the {@code freezeChannelPriorities} property.
   * @see #freezeChannelPriorities
   */
  public void setFreezeChannelPriorities(boolean v) { setBoolean(freezeChannelPriorities, v, null); }

  //endregion Property "freezeChannelPriorities"

  //region Property "lastHash"

  /**
   * Slot for the {@code lastHash} property.
   * @see #getLastHash
   * @see #setLastHash
   */
  public static final Property lastHash = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT, -1, null);

  /**
   * Get the {@code lastHash} property.
   * @see #lastHash
   */
  public int getLastHash() { return getInt(lastHash); }

  /**
   * Set the {@code lastHash} property.
   * @see #lastHash
   */
  public void setLastHash(int v) { setInt(lastHash, v, null); }

  //endregion Property "lastHash"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * Facets used to configure network management operations.
   * delayToReset - mSec delay inserted before reset during commissioning
   * delayToHardOffline - mSec delay inserted before setting to hardOffline during commissioning
   * minNvUpdateInterMsgDelay - minimum delay between nv updates to device
   * disableSetOfflineInBind - do not set device offline when modifying address table during a bind operation
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * Facets used to configure network management operations.
   * delayToReset - mSec delay inserted before reset during commissioning
   * delayToHardOffline - mSec delay inserted before setting to hardOffline during commissioning
   * minNvUpdateInterMsgDelay - minimum delay between nv updates to device
   * disableSetOfflineInBind - do not set device offline when modifying address table during a bind operation
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * Facets used to configure network management operations.
   * delayToReset - mSec delay inserted before reset during commissioning
   * delayToHardOffline - mSec delay inserted before setting to hardOffline during commissioning
   * minNvUpdateInterMsgDelay - minimum delay between nv updates to device
   * disableSetOfflineInBind - do not set device offline when modifying address table during a bind operation
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDeviceData.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public BDeviceData() {}
  
  public BDeviceData(BProgramId pid)
  {
  	setProgramId(pid);
  }
  
  public BDeviceData(BProgramId pid,
                     boolean    bindingII, 
                     boolean    hosted,
                     boolean    twoDomains,
                     int        msgTagCount,
                     int        addressCount,
                     int        aliasCount,  
                     boolean    hasNodeObject)
  {
  	setProgramId(pid);
  	setBindingII(bindingII);
  	setHosted(hosted);
  	setTwoDomains(twoDomains);
  	setMsgTagCount(msgTagCount);
  	setAddressCount(addressCount);
  	if(aliasCount>0) setAliasTable(new BAliasTable(aliasCount));
  	setHasNodeObject(hasNodeObject);
  }

  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    if(!isRunning() || context==importChanges) return;
    
    // If facets change force recreate of pickle
    if(prop==facets) pickle = null;
    
    // Give parent device opportunity to respond.
    BObject p = getParent();
    if(p instanceof BLonDevice)
    {
      ((BLonDevice)p).deviceDataChanged(prop,context);
    }

    if( (prop == neuronId) ||
        (prop == programId) ||
        (prop == nodeState) ||
        (prop == subnetNodeId) ||
        (prop == channelId) )
    {
      NmUtil.getLonNetwork(this).addressManager().deviceDataChanged(this, context);
    }

    if( (context==AddressManager.noDeviceChange) ||
        (context==NAddressManager.localChange)  )
    {
      return;
    }
    
    if(getNeuronId().isZero()) return;
    
    Runnable req = null;
    if(prop==nodeState) 
    {
      req = new Runnable()
          {
            public void run() { updateNodeState(); }
          };
    }
    else if(prop == subnetNodeId ||
            prop == workingDomain) 
    {
      req = new Runnable()
          {
            public void run() { updateSubnetNodeId(); }
          };
    }  
    
    if(req!=null) NmUtil.getLonNetwork(this).postAsync(req);
  }
  
  private void updateSubnetNodeId()
  {  
    BComplex o = getParent();
    if(o instanceof BLonDevice)
    {
      BLonDevice dev = (BLonDevice)o;
      dev.updateDomainTable();
    }
  }

  private void updateNodeState()
  {
    boolean err = false;
    BComplex o = getParent();
    BLonNodeState nState = getNodeState();
    LonException cause = null;

    if(o instanceof BLonDevice)
    {
      BLonDevice dev = (BLonDevice)o;
      dev.updateNodeState();
    }
    else if(o instanceof BLonRouter)
    {
      // Keep near and far side the same
      BLonRouter rtr = (BLonRouter)o;
      try
      {
        NmUtil.setDeviceState(rtr, nState, true );
        NmUtil.setDeviceState(rtr, nState, false );
      }
      catch(LonException e)
      {
        err = true;
        cause = e;
      }

      rtr.getNearDeviceData().set(nodeState,nState,AddressManager.noDeviceChange);
      rtr.getFarDeviceData().set(nodeState,nState,AddressManager.noDeviceChange);
    }

    if(err) throw new BajaRuntimeException("Unable to update state change in " + o.getDisplayName(null), cause);
  }
  
  // Need to store DeviceFacets here. This is created from the BFacets in deviceFacets slot and was
  // previously stored on the BFacets pickle.  However the pickle was not getting saved to the facets
  // causing DeviceFacets to be recreate each time used (i.e. every nvupdate)
  private Object pickle = null;
  
  /** For internal use only */
  public void setPickle(Object o) 
  {
    if(o instanceof DeviceFacets) pickle=o;
  }
  /** For internal use only */
  public Object getPickle() { return pickle; }
  
  /** 
   * Does this device use extended device tables and implement extended network
   *  management messages. 
   */
  public boolean isExtended() { return false; }
  
  public void clearAddressTable() { getAddressTable().clearTable(); }
  
  public BIAddressEntry getAddressEntry(int index)                   { return getAddressTable().getAddressEntry(index); }
  public void setAddressEntry(int index, BIAddressEntry e)           { getAddressTable().setAddressEntry(index,e); }
  public void setAddressEntry(int index, BIAddressEntry e, Context c){ getAddressTable().setAddressEntry(index,e,c); }       
  
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("deviceData.png");

  public static final Context importChanges = new BasicContext();

}
