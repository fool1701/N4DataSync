/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.lonworks.enums.BLonSnvtType;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 *   This class file ???.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision: 4$ $Date: 10/12/01 9:43:22 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 NV index within device
 */
@NiagaraProperty(
  name = "nvIndex",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 SNVT index if this nv is a standard network variable type or
 0 if proprietary data type
 */
@NiagaraProperty(
  name = "snvtType",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 Index of LonMark object this nv is member of. -1 if n/a
 */
@NiagaraProperty(
  name = "objectIndex",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY
)
/*
 Member index within LonMark object. -1 for n/a
 */
@NiagaraProperty(
  name = "memberIndex",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY
)
/*
 User command to enable or disable polling of specific nv
 */
@NiagaraProperty(
  name = "pollEnable",
  type = "boolean",
  defaultValue = "true"
)
/*
 Type of nv which uses polling mechanism if bound.
 */
@NiagaraProperty(
  name = "polled",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 Allow netMgmt to modify authenticate flag in configData.
 */
@NiagaraProperty(
  name = "authConf",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.READONLY
)
/*
 Allow netMgmt to modify serviceType in configData.
 */
@NiagaraProperty(
  name = "serviceConf",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.READONLY
)
/*
 Allow netMgmt to modify priority flag in configData.
 */
@NiagaraProperty(
  name = "priorityConf",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 Must take device offline before modifying nv data.
 */
@NiagaraProperty(
  name = "modifyOffline",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY | Flags.HIDDEN
)
/*
 This is a synchronized nv.
 */
@NiagaraProperty(
  name = "sync",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 The nvType can be changed in the device at runtime.
 */
@NiagaraProperty(
  name = "changeableType",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 The nv is bound to the local device. This flag is
 used to register for nv updates and to control polling.
 */
@NiagaraProperty(
  name = "boundToLocal",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
public class BNvProps
  extends BStruct
{  
  /**
   * No arg constructor
   */
  public BNvProps() {}

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BNvProps(2797917251)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "nvIndex"

  /**
   * Slot for the {@code nvIndex} property.
   * NV index within device
   * @see #getNvIndex
   * @see #setNvIndex
   */
  public static final Property nvIndex = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code nvIndex} property.
   * NV index within device
   * @see #nvIndex
   */
  public int getNvIndex() { return getInt(nvIndex); }

  /**
   * Set the {@code nvIndex} property.
   * NV index within device
   * @see #nvIndex
   */
  public void setNvIndex(int v) { setInt(nvIndex, v, null); }

  //endregion Property "nvIndex"

  //region Property "snvtType"

  /**
   * Slot for the {@code snvtType} property.
   * SNVT index if this nv is a standard network variable type or
   * 0 if proprietary data type
   * @see #getSnvtType
   * @see #setSnvtType
   */
  public static final Property snvtType = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code snvtType} property.
   * SNVT index if this nv is a standard network variable type or
   * 0 if proprietary data type
   * @see #snvtType
   */
  public int getSnvtType() { return getInt(snvtType); }

  /**
   * Set the {@code snvtType} property.
   * SNVT index if this nv is a standard network variable type or
   * 0 if proprietary data type
   * @see #snvtType
   */
  public void setSnvtType(int v) { setInt(snvtType, v, null); }

  //endregion Property "snvtType"

  //region Property "objectIndex"

  /**
   * Slot for the {@code objectIndex} property.
   * Index of LonMark object this nv is member of. -1 if n/a
   * @see #getObjectIndex
   * @see #setObjectIndex
   */
  public static final Property objectIndex = newProperty(Flags.READONLY, -1, null);

  /**
   * Get the {@code objectIndex} property.
   * Index of LonMark object this nv is member of. -1 if n/a
   * @see #objectIndex
   */
  public int getObjectIndex() { return getInt(objectIndex); }

  /**
   * Set the {@code objectIndex} property.
   * Index of LonMark object this nv is member of. -1 if n/a
   * @see #objectIndex
   */
  public void setObjectIndex(int v) { setInt(objectIndex, v, null); }

  //endregion Property "objectIndex"

  //region Property "memberIndex"

  /**
   * Slot for the {@code memberIndex} property.
   * Member index within LonMark object. -1 for n/a
   * @see #getMemberIndex
   * @see #setMemberIndex
   */
  public static final Property memberIndex = newProperty(Flags.READONLY, -1, null);

  /**
   * Get the {@code memberIndex} property.
   * Member index within LonMark object. -1 for n/a
   * @see #memberIndex
   */
  public int getMemberIndex() { return getInt(memberIndex); }

  /**
   * Set the {@code memberIndex} property.
   * Member index within LonMark object. -1 for n/a
   * @see #memberIndex
   */
  public void setMemberIndex(int v) { setInt(memberIndex, v, null); }

  //endregion Property "memberIndex"

  //region Property "pollEnable"

  /**
   * Slot for the {@code pollEnable} property.
   * User command to enable or disable polling of specific nv
   * @see #getPollEnable
   * @see #setPollEnable
   */
  public static final Property pollEnable = newProperty(0, true, null);

  /**
   * Get the {@code pollEnable} property.
   * User command to enable or disable polling of specific nv
   * @see #pollEnable
   */
  public boolean getPollEnable() { return getBoolean(pollEnable); }

  /**
   * Set the {@code pollEnable} property.
   * User command to enable or disable polling of specific nv
   * @see #pollEnable
   */
  public void setPollEnable(boolean v) { setBoolean(pollEnable, v, null); }

  //endregion Property "pollEnable"

  //region Property "polled"

  /**
   * Slot for the {@code polled} property.
   * Type of nv which uses polling mechanism if bound.
   * @see #getPolled
   * @see #setPolled
   */
  public static final Property polled = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code polled} property.
   * Type of nv which uses polling mechanism if bound.
   * @see #polled
   */
  public boolean getPolled() { return getBoolean(polled); }

  /**
   * Set the {@code polled} property.
   * Type of nv which uses polling mechanism if bound.
   * @see #polled
   */
  public void setPolled(boolean v) { setBoolean(polled, v, null); }

  //endregion Property "polled"

  //region Property "authConf"

  /**
   * Slot for the {@code authConf} property.
   * Allow netMgmt to modify authenticate flag in configData.
   * @see #getAuthConf
   * @see #setAuthConf
   */
  public static final Property authConf = newProperty(Flags.READONLY, true, null);

  /**
   * Get the {@code authConf} property.
   * Allow netMgmt to modify authenticate flag in configData.
   * @see #authConf
   */
  public boolean getAuthConf() { return getBoolean(authConf); }

  /**
   * Set the {@code authConf} property.
   * Allow netMgmt to modify authenticate flag in configData.
   * @see #authConf
   */
  public void setAuthConf(boolean v) { setBoolean(authConf, v, null); }

  //endregion Property "authConf"

  //region Property "serviceConf"

  /**
   * Slot for the {@code serviceConf} property.
   * Allow netMgmt to modify serviceType in configData.
   * @see #getServiceConf
   * @see #setServiceConf
   */
  public static final Property serviceConf = newProperty(Flags.READONLY, true, null);

  /**
   * Get the {@code serviceConf} property.
   * Allow netMgmt to modify serviceType in configData.
   * @see #serviceConf
   */
  public boolean getServiceConf() { return getBoolean(serviceConf); }

  /**
   * Set the {@code serviceConf} property.
   * Allow netMgmt to modify serviceType in configData.
   * @see #serviceConf
   */
  public void setServiceConf(boolean v) { setBoolean(serviceConf, v, null); }

  //endregion Property "serviceConf"

  //region Property "priorityConf"

  /**
   * Slot for the {@code priorityConf} property.
   * Allow netMgmt to modify priority flag in configData.
   * @see #getPriorityConf
   * @see #setPriorityConf
   */
  public static final Property priorityConf = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code priorityConf} property.
   * Allow netMgmt to modify priority flag in configData.
   * @see #priorityConf
   */
  public boolean getPriorityConf() { return getBoolean(priorityConf); }

  /**
   * Set the {@code priorityConf} property.
   * Allow netMgmt to modify priority flag in configData.
   * @see #priorityConf
   */
  public void setPriorityConf(boolean v) { setBoolean(priorityConf, v, null); }

  //endregion Property "priorityConf"

  //region Property "modifyOffline"

  /**
   * Slot for the {@code modifyOffline} property.
   * Must take device offline before modifying nv data.
   * @see #getModifyOffline
   * @see #setModifyOffline
   */
  public static final Property modifyOffline = newProperty(Flags.READONLY | Flags.HIDDEN, false, null);

  /**
   * Get the {@code modifyOffline} property.
   * Must take device offline before modifying nv data.
   * @see #modifyOffline
   */
  public boolean getModifyOffline() { return getBoolean(modifyOffline); }

  /**
   * Set the {@code modifyOffline} property.
   * Must take device offline before modifying nv data.
   * @see #modifyOffline
   */
  public void setModifyOffline(boolean v) { setBoolean(modifyOffline, v, null); }

  //endregion Property "modifyOffline"

  //region Property "sync"

  /**
   * Slot for the {@code sync} property.
   * This is a synchronized nv.
   * @see #getSync
   * @see #setSync
   */
  public static final Property sync = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code sync} property.
   * This is a synchronized nv.
   * @see #sync
   */
  public boolean getSync() { return getBoolean(sync); }

  /**
   * Set the {@code sync} property.
   * This is a synchronized nv.
   * @see #sync
   */
  public void setSync(boolean v) { setBoolean(sync, v, null); }

  //endregion Property "sync"

  //region Property "changeableType"

  /**
   * Slot for the {@code changeableType} property.
   * The nvType can be changed in the device at runtime.
   * @see #getChangeableType
   * @see #setChangeableType
   */
  public static final Property changeableType = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code changeableType} property.
   * The nvType can be changed in the device at runtime.
   * @see #changeableType
   */
  public boolean getChangeableType() { return getBoolean(changeableType); }

  /**
   * Set the {@code changeableType} property.
   * The nvType can be changed in the device at runtime.
   * @see #changeableType
   */
  public void setChangeableType(boolean v) { setBoolean(changeableType, v, null); }

  //endregion Property "changeableType"

  //region Property "boundToLocal"

  /**
   * Slot for the {@code boundToLocal} property.
   * The nv is bound to the local device. This flag is
   * used to register for nv updates and to control polling.
   * @see #getBoundToLocal
   * @see #setBoundToLocal
   */
  public static final Property boundToLocal = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, false, null);

  /**
   * Get the {@code boundToLocal} property.
   * The nv is bound to the local device. This flag is
   * used to register for nv updates and to control polling.
   * @see #boundToLocal
   */
  public boolean getBoundToLocal() { return getBoolean(boundToLocal); }

  /**
   * Set the {@code boundToLocal} property.
   * The nv is bound to the local device. This flag is
   * used to register for nv updates and to control polling.
   * @see #boundToLocal
   */
  public void setBoundToLocal(boolean v) { setBoolean(boundToLocal, v, null); }

  //endregion Property "boundToLocal"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNvProps.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Set nv to unbound state.
   */
  public void setUnbound()
  {
    setBoundToLocal(false);
  }

  public String toString(Context c)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("nv:").append(getNvIndex());  
    if(getSnvtType()>0)sb.append(",snvt:").append(getSnvtType());  
    if(getObjectIndex() != -1)
    {
      sb.append(",obj:").append(getObjectIndex());  
      sb.append(".").append(getMemberIndex());  
    }  
    if(getPollEnable())sb.append(",pollEnable");  
    if(getPolled())sb.append(",polled");  
    boolean dot = false;
    sb.append(",conf:");
    if(getAuthConf())     { sb.append("auth"); dot=true; }
    if(getServiceConf())  { if(dot)sb.append("."); sb.append("srv"); dot=true; }  
    if(getPriorityConf()) { if(dot)sb.append("."); sb.append("pri"); }  
    if(getModifyOffline()) sb.append(",modOffline"); 
    if(getSync()) sb.append(",sync");  
    if(getChangeableType()) sb.append(",chngTyp");  
//    sb.append(",").append(getSelfDoc());  
    return sb.toString();
  }
  
  /**
   * Get the snvt type of network variable as BLonSnvtType enum.
   * If not a valid snvt type then return BLonSnvtType.SnvtXxx. 
   */
  public BLonSnvtType getSnvtTypeEnum()
  {
    int typ = getSnvtType();
    if(typ>0 && typ<BLonSnvtType.LAST_SNVT_ID)
      return BLonSnvtType.make(typ);
    
    return BLonSnvtType.SnvtXxx;
  }
  
  
}
