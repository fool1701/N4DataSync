/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.enums.BSdiEnum;

/**
 * BSdiValueConfig - This is a structure to configure SCI AI values for
 * Open, Closed, Cut, Short enumerations.
 *
 * @author    Andy Saunders
 * @creation  Nov 17, 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 This is the unit number of the discovered access device 4096 *5/6
 */
@NiagaraProperty(
  name = "cutValue",
  type = "int",
  defaultValue = "3412"
)
/*
 This is the unit number of the discovered access device 4096 /2
 */
@NiagaraProperty(
  name = "openValue",
  type = "int",
  defaultValue = "2047"
)
/*
 This is the unit number of the discovered access device 4096 /2
 */
@NiagaraProperty(
  name = "closedValue",
  type = "int",
  defaultValue = "682"
)
public class BSdiValueConfig
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BSdiValueConfig(3003168986)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "cutValue"

  /**
   * Slot for the {@code cutValue} property.
   * This is the unit number of the discovered access device 4096 *5/6
   * @see #getCutValue
   * @see #setCutValue
   */
  public static final Property cutValue = newProperty(0, 3412, null);

  /**
   * Get the {@code cutValue} property.
   * This is the unit number of the discovered access device 4096 *5/6
   * @see #cutValue
   */
  public int getCutValue() { return getInt(cutValue); }

  /**
   * Set the {@code cutValue} property.
   * This is the unit number of the discovered access device 4096 *5/6
   * @see #cutValue
   */
  public void setCutValue(int v) { setInt(cutValue, v, null); }

  //endregion Property "cutValue"

  //region Property "openValue"

  /**
   * Slot for the {@code openValue} property.
   * This is the unit number of the discovered access device 4096 /2
   * @see #getOpenValue
   * @see #setOpenValue
   */
  public static final Property openValue = newProperty(0, 2047, null);

  /**
   * Get the {@code openValue} property.
   * This is the unit number of the discovered access device 4096 /2
   * @see #openValue
   */
  public int getOpenValue() { return getInt(openValue); }

  /**
   * Set the {@code openValue} property.
   * This is the unit number of the discovered access device 4096 /2
   * @see #openValue
   */
  public void setOpenValue(int v) { setInt(openValue, v, null); }

  //endregion Property "openValue"

  //region Property "closedValue"

  /**
   * Slot for the {@code closedValue} property.
   * This is the unit number of the discovered access device 4096 /2
   * @see #getClosedValue
   * @see #setClosedValue
   */
  public static final Property closedValue = newProperty(0, 682, null);

  /**
   * Get the {@code closedValue} property.
   * This is the unit number of the discovered access device 4096 /2
   * @see #closedValue
   */
  public int getClosedValue() { return getInt(closedValue); }

  /**
   * Set the {@code closedValue} property.
   * This is the unit number of the discovered access device 4096 /2
   * @see #closedValue
   */
  public void setClosedValue(int v) { setInt(closedValue, v, null); }

  //endregion Property "closedValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSdiValueConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BSdiValueConfig(int cut, int open, int closed)
  {
    setCutValue(cut);
    setOpenValue(open);
    setClosedValue(closed);
  }
  public BSdiValueConfig(){}

  public BSdiEnum getEnumValue(int value)
  {
    if(value > getCutValue()) return BSdiEnum.cut;
    if(value > getOpenValue()) return BSdiEnum.open;
    if(value > getClosedValue()) return BSdiEnum.closed;
    return BSdiEnum.shorted;
  }
}
