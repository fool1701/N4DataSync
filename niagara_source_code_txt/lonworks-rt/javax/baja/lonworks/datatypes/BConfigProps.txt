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
 * @creation  8 Nov 00
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:26 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Scpt/Ucpt integer identifier
 */
@NiagaraProperty(
  name = "configIndex",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 offset in config parameter value file
 */
@NiagaraProperty(
  name = "offset",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 length of config parameter value
 */
@NiagaraProperty(
  name = "length",
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
public class BConfigProps
  extends BStruct
{  
  /**
   * No arg constructor
   */
  public BConfigProps()
  {
  }

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BConfigProps(3215743564)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "configIndex"

  /**
   * Slot for the {@code configIndex} property.
   * Scpt/Ucpt integer identifier
   * @see #getConfigIndex
   * @see #setConfigIndex
   */
  public static final Property configIndex = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code configIndex} property.
   * Scpt/Ucpt integer identifier
   * @see #configIndex
   */
  public int getConfigIndex() { return getInt(configIndex); }

  /**
   * Set the {@code configIndex} property.
   * Scpt/Ucpt integer identifier
   * @see #configIndex
   */
  public void setConfigIndex(int v) { setInt(configIndex, v, null); }

  //endregion Property "configIndex"

  //region Property "offset"

  /**
   * Slot for the {@code offset} property.
   * offset in config parameter value file
   * @see #getOffset
   * @see #setOffset
   */
  public static final Property offset = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code offset} property.
   * offset in config parameter value file
   * @see #offset
   */
  public int getOffset() { return getInt(offset); }

  /**
   * Set the {@code offset} property.
   * offset in config parameter value file
   * @see #offset
   */
  public void setOffset(int v) { setInt(offset, v, null); }

  //endregion Property "offset"

  //region Property "length"

  /**
   * Slot for the {@code length} property.
   * length of config parameter value
   * @see #getLength
   * @see #setLength
   */
  public static final Property length = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code length} property.
   * length of config parameter value
   * @see #length
   */
  public int getLength() { return getInt(length); }

  /**
   * Set the {@code length} property.
   * length of config parameter value
   * @see #length
   */
  public void setLength(int v) { setInt(length, v, null); }

  //endregion Property "length"

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
  public static final Type TYPE = Sys.loadType(BConfigProps.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public String toString(Context c)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(!getMfgDefined() ? "scpt:" : "ucpt:");
    sb.append(getConfigIndex());  
   
    sb.append(",").append(getScope());
    if(getScope()!=BLonConfigScope.node) sb.append(".").append(getSelect());
    
    sb.append(",").append(getModifyFlag());
   
    return sb.toString();
  }
  
}
