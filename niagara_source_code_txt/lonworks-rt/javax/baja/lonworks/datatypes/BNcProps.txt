/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.lonworks.enums.BLonConfigScope;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 *   This class file ???.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  12 Jan 01
 * @version   $Revision: 2$ $Date: 9/18/01 9:50:13 AM$
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
 SNVT index if this nci maps to a standard network variable type or
 0 if proprietary data type
 */
@NiagaraProperty(
  name = "snvtType",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 SCPT/UCPT type
 */
@NiagaraProperty(
  name = "configIndex",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 false if parameter is Scpt, true if Ucpt
 */
@NiagaraProperty(
  name = "mfgDefined",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 indicates conditions for modifying this config property
 */
@NiagaraProperty(
  name = "modifyFlag",
  type = "BModifyFlags",
  defaultValue = "BModifyFlags.DEFAULT",
  flags = Flags.READONLY
)
/*
 Indicates scope of config parameter 0 node, 1 object, 2 nv
 */
@NiagaraProperty(
  name = "scope",
  type = "BLonConfigScope",
  defaultValue = "BLonConfigScope.node",
  flags = Flags.READONLY
)
/*
 list of objects/nvs controled by config parameter
 */
@NiagaraProperty(
  name = "select",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
public class BNcProps
  extends BStruct
{  
  /**
   * No arg constructor
   */
  public BNcProps()
  {
  }

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BNcProps(2189532722)1.0$ @*/
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
   * SNVT index if this nci maps to a standard network variable type or
   * 0 if proprietary data type
   * @see #getSnvtType
   * @see #setSnvtType
   */
  public static final Property snvtType = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code snvtType} property.
   * SNVT index if this nci maps to a standard network variable type or
   * 0 if proprietary data type
   * @see #snvtType
   */
  public int getSnvtType() { return getInt(snvtType); }

  /**
   * Set the {@code snvtType} property.
   * SNVT index if this nci maps to a standard network variable type or
   * 0 if proprietary data type
   * @see #snvtType
   */
  public void setSnvtType(int v) { setInt(snvtType, v, null); }

  //endregion Property "snvtType"

  //region Property "configIndex"

  /**
   * Slot for the {@code configIndex} property.
   * SCPT/UCPT type
   * @see #getConfigIndex
   * @see #setConfigIndex
   */
  public static final Property configIndex = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code configIndex} property.
   * SCPT/UCPT type
   * @see #configIndex
   */
  public int getConfigIndex() { return getInt(configIndex); }

  /**
   * Set the {@code configIndex} property.
   * SCPT/UCPT type
   * @see #configIndex
   */
  public void setConfigIndex(int v) { setInt(configIndex, v, null); }

  //endregion Property "configIndex"

  //region Property "mfgDefined"

  /**
   * Slot for the {@code mfgDefined} property.
   * false if parameter is Scpt, true if Ucpt
   * @see #getMfgDefined
   * @see #setMfgDefined
   */
  public static final Property mfgDefined = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code mfgDefined} property.
   * false if parameter is Scpt, true if Ucpt
   * @see #mfgDefined
   */
  public boolean getMfgDefined() { return getBoolean(mfgDefined); }

  /**
   * Set the {@code mfgDefined} property.
   * false if parameter is Scpt, true if Ucpt
   * @see #mfgDefined
   */
  public void setMfgDefined(boolean v) { setBoolean(mfgDefined, v, null); }

  //endregion Property "mfgDefined"

  //region Property "modifyFlag"

  /**
   * Slot for the {@code modifyFlag} property.
   * indicates conditions for modifying this config property
   * @see #getModifyFlag
   * @see #setModifyFlag
   */
  public static final Property modifyFlag = newProperty(Flags.READONLY, BModifyFlags.DEFAULT, null);

  /**
   * Get the {@code modifyFlag} property.
   * indicates conditions for modifying this config property
   * @see #modifyFlag
   */
  public BModifyFlags getModifyFlag() { return (BModifyFlags)get(modifyFlag); }

  /**
   * Set the {@code modifyFlag} property.
   * indicates conditions for modifying this config property
   * @see #modifyFlag
   */
  public void setModifyFlag(BModifyFlags v) { set(modifyFlag, v, null); }

  //endregion Property "modifyFlag"

  //region Property "scope"

  /**
   * Slot for the {@code scope} property.
   * Indicates scope of config parameter 0 node, 1 object, 2 nv
   * @see #getScope
   * @see #setScope
   */
  public static final Property scope = newProperty(Flags.READONLY, BLonConfigScope.node, null);

  /**
   * Get the {@code scope} property.
   * Indicates scope of config parameter 0 node, 1 object, 2 nv
   * @see #scope
   */
  public BLonConfigScope getScope() { return (BLonConfigScope)get(scope); }

  /**
   * Set the {@code scope} property.
   * Indicates scope of config parameter 0 node, 1 object, 2 nv
   * @see #scope
   */
  public void setScope(BLonConfigScope v) { set(scope, v, null); }

  //endregion Property "scope"

  //region Property "select"

  /**
   * Slot for the {@code select} property.
   * list of objects/nvs controled by config parameter
   * @see #getSelect
   * @see #setSelect
   */
  public static final Property select = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code select} property.
   * list of objects/nvs controled by config parameter
   * @see #select
   */
  public String getSelect() { return getString(select); }

  /**
   * Set the {@code select} property.
   * list of objects/nvs controled by config parameter
   * @see #select
   */
  public void setSelect(String v) { setString(select, v, null); }

  //endregion Property "select"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNcProps.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Set nci to unbound state.
   */
  public void setUnbound()
  {
  }
  
  public String toString(Context c)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("nv:").append(getNvIndex());  
    if(getSnvtType()>0)sb.append(",snvt:").append(getSnvtType());  
    if(getConfigIndex() != 0)
    {
      sb.append(",cfgNdx:").append(getConfigIndex());  
    }  
    if(getMfgDefined())sb.append(",mfgDefn");  
    sb.append(",mod:").append(getModifyFlag());
    sb.append(",scope:").append(getScope());
    if(getScope()!=BLonConfigScope.node) sb.append(".").append(getSelect());
    return sb.toString();
  }

}
