/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.enums.BNrioIoTypeEnum;

/**
 * BInputOutputModulePoints - The learn IO Module point definitions.
 * 
 *
 * @author    Andy Saunders
 * @creation  13 Jan 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "di1",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.digitalInput, 1)"
)
@NiagaraProperty(
  name = "di2",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.digitalInput, 2)"
)
@NiagaraProperty(
  name = "sdi1",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 1)"
)
@NiagaraProperty(
  name = "sdi2",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 2)"
)
@NiagaraProperty(
  name = "sdi3",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 3)"
)
@NiagaraProperty(
  name = "sdi4",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 4)"
)
@NiagaraProperty(
  name = "sdi5",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 5)"
)
@NiagaraProperty(
  name = "sdi6",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 6)"
)
@NiagaraProperty(
  name = "sdi7",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 7)"
)
@NiagaraProperty(
  name = "sdi8",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 8)"
)
@NiagaraProperty(
  name = "ro1",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 1)"
)
@NiagaraProperty(
  name = "ro2",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 2)"
)
@NiagaraProperty(
  name = "ro3",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 3)"
)
@NiagaraProperty(
  name = "ro4",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 4)"
)
@NiagaraProperty(
  name = "ro5",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 5)"
)
@NiagaraProperty(
  name = "ro6",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 6)"
)
@NiagaraProperty(
  name = "ro7",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 7)"
)
@NiagaraProperty(
  name = "ro8",
  type = "BNrioIOPointEntry",
  defaultValue = "new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 8)"
)
public class BInputOutputModulePoints
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BInputOutputModulePoints(2298114538)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "di1"

  /**
   * Slot for the {@code di1} property.
   * @see #getDi1
   * @see #setDi1
   */
  public static final Property di1 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.digitalInput, 1), null);

  /**
   * Get the {@code di1} property.
   * @see #di1
   */
  public BNrioIOPointEntry getDi1() { return (BNrioIOPointEntry)get(di1); }

  /**
   * Set the {@code di1} property.
   * @see #di1
   */
  public void setDi1(BNrioIOPointEntry v) { set(di1, v, null); }

  //endregion Property "di1"

  //region Property "di2"

  /**
   * Slot for the {@code di2} property.
   * @see #getDi2
   * @see #setDi2
   */
  public static final Property di2 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.digitalInput, 2), null);

  /**
   * Get the {@code di2} property.
   * @see #di2
   */
  public BNrioIOPointEntry getDi2() { return (BNrioIOPointEntry)get(di2); }

  /**
   * Set the {@code di2} property.
   * @see #di2
   */
  public void setDi2(BNrioIOPointEntry v) { set(di2, v, null); }

  //endregion Property "di2"

  //region Property "sdi1"

  /**
   * Slot for the {@code sdi1} property.
   * @see #getSdi1
   * @see #setSdi1
   */
  public static final Property sdi1 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 1), null);

  /**
   * Get the {@code sdi1} property.
   * @see #sdi1
   */
  public BNrioIOPointEntry getSdi1() { return (BNrioIOPointEntry)get(sdi1); }

  /**
   * Set the {@code sdi1} property.
   * @see #sdi1
   */
  public void setSdi1(BNrioIOPointEntry v) { set(sdi1, v, null); }

  //endregion Property "sdi1"

  //region Property "sdi2"

  /**
   * Slot for the {@code sdi2} property.
   * @see #getSdi2
   * @see #setSdi2
   */
  public static final Property sdi2 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 2), null);

  /**
   * Get the {@code sdi2} property.
   * @see #sdi2
   */
  public BNrioIOPointEntry getSdi2() { return (BNrioIOPointEntry)get(sdi2); }

  /**
   * Set the {@code sdi2} property.
   * @see #sdi2
   */
  public void setSdi2(BNrioIOPointEntry v) { set(sdi2, v, null); }

  //endregion Property "sdi2"

  //region Property "sdi3"

  /**
   * Slot for the {@code sdi3} property.
   * @see #getSdi3
   * @see #setSdi3
   */
  public static final Property sdi3 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 3), null);

  /**
   * Get the {@code sdi3} property.
   * @see #sdi3
   */
  public BNrioIOPointEntry getSdi3() { return (BNrioIOPointEntry)get(sdi3); }

  /**
   * Set the {@code sdi3} property.
   * @see #sdi3
   */
  public void setSdi3(BNrioIOPointEntry v) { set(sdi3, v, null); }

  //endregion Property "sdi3"

  //region Property "sdi4"

  /**
   * Slot for the {@code sdi4} property.
   * @see #getSdi4
   * @see #setSdi4
   */
  public static final Property sdi4 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 4), null);

  /**
   * Get the {@code sdi4} property.
   * @see #sdi4
   */
  public BNrioIOPointEntry getSdi4() { return (BNrioIOPointEntry)get(sdi4); }

  /**
   * Set the {@code sdi4} property.
   * @see #sdi4
   */
  public void setSdi4(BNrioIOPointEntry v) { set(sdi4, v, null); }

  //endregion Property "sdi4"

  //region Property "sdi5"

  /**
   * Slot for the {@code sdi5} property.
   * @see #getSdi5
   * @see #setSdi5
   */
  public static final Property sdi5 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 5), null);

  /**
   * Get the {@code sdi5} property.
   * @see #sdi5
   */
  public BNrioIOPointEntry getSdi5() { return (BNrioIOPointEntry)get(sdi5); }

  /**
   * Set the {@code sdi5} property.
   * @see #sdi5
   */
  public void setSdi5(BNrioIOPointEntry v) { set(sdi5, v, null); }

  //endregion Property "sdi5"

  //region Property "sdi6"

  /**
   * Slot for the {@code sdi6} property.
   * @see #getSdi6
   * @see #setSdi6
   */
  public static final Property sdi6 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 6), null);

  /**
   * Get the {@code sdi6} property.
   * @see #sdi6
   */
  public BNrioIOPointEntry getSdi6() { return (BNrioIOPointEntry)get(sdi6); }

  /**
   * Set the {@code sdi6} property.
   * @see #sdi6
   */
  public void setSdi6(BNrioIOPointEntry v) { set(sdi6, v, null); }

  //endregion Property "sdi6"

  //region Property "sdi7"

  /**
   * Slot for the {@code sdi7} property.
   * @see #getSdi7
   * @see #setSdi7
   */
  public static final Property sdi7 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 7), null);

  /**
   * Get the {@code sdi7} property.
   * @see #sdi7
   */
  public BNrioIOPointEntry getSdi7() { return (BNrioIOPointEntry)get(sdi7); }

  /**
   * Set the {@code sdi7} property.
   * @see #sdi7
   */
  public void setSdi7(BNrioIOPointEntry v) { set(sdi7, v, null); }

  //endregion Property "sdi7"

  //region Property "sdi8"

  /**
   * Slot for the {@code sdi8} property.
   * @see #getSdi8
   * @see #setSdi8
   */
  public static final Property sdi8 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.supervisedDigitalInput, 8), null);

  /**
   * Get the {@code sdi8} property.
   * @see #sdi8
   */
  public BNrioIOPointEntry getSdi8() { return (BNrioIOPointEntry)get(sdi8); }

  /**
   * Set the {@code sdi8} property.
   * @see #sdi8
   */
  public void setSdi8(BNrioIOPointEntry v) { set(sdi8, v, null); }

  //endregion Property "sdi8"

  //region Property "ro1"

  /**
   * Slot for the {@code ro1} property.
   * @see #getRo1
   * @see #setRo1
   */
  public static final Property ro1 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 1), null);

  /**
   * Get the {@code ro1} property.
   * @see #ro1
   */
  public BNrioIOPointEntry getRo1() { return (BNrioIOPointEntry)get(ro1); }

  /**
   * Set the {@code ro1} property.
   * @see #ro1
   */
  public void setRo1(BNrioIOPointEntry v) { set(ro1, v, null); }

  //endregion Property "ro1"

  //region Property "ro2"

  /**
   * Slot for the {@code ro2} property.
   * @see #getRo2
   * @see #setRo2
   */
  public static final Property ro2 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 2), null);

  /**
   * Get the {@code ro2} property.
   * @see #ro2
   */
  public BNrioIOPointEntry getRo2() { return (BNrioIOPointEntry)get(ro2); }

  /**
   * Set the {@code ro2} property.
   * @see #ro2
   */
  public void setRo2(BNrioIOPointEntry v) { set(ro2, v, null); }

  //endregion Property "ro2"

  //region Property "ro3"

  /**
   * Slot for the {@code ro3} property.
   * @see #getRo3
   * @see #setRo3
   */
  public static final Property ro3 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 3), null);

  /**
   * Get the {@code ro3} property.
   * @see #ro3
   */
  public BNrioIOPointEntry getRo3() { return (BNrioIOPointEntry)get(ro3); }

  /**
   * Set the {@code ro3} property.
   * @see #ro3
   */
  public void setRo3(BNrioIOPointEntry v) { set(ro3, v, null); }

  //endregion Property "ro3"

  //region Property "ro4"

  /**
   * Slot for the {@code ro4} property.
   * @see #getRo4
   * @see #setRo4
   */
  public static final Property ro4 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 4), null);

  /**
   * Get the {@code ro4} property.
   * @see #ro4
   */
  public BNrioIOPointEntry getRo4() { return (BNrioIOPointEntry)get(ro4); }

  /**
   * Set the {@code ro4} property.
   * @see #ro4
   */
  public void setRo4(BNrioIOPointEntry v) { set(ro4, v, null); }

  //endregion Property "ro4"

  //region Property "ro5"

  /**
   * Slot for the {@code ro5} property.
   * @see #getRo5
   * @see #setRo5
   */
  public static final Property ro5 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 5), null);

  /**
   * Get the {@code ro5} property.
   * @see #ro5
   */
  public BNrioIOPointEntry getRo5() { return (BNrioIOPointEntry)get(ro5); }

  /**
   * Set the {@code ro5} property.
   * @see #ro5
   */
  public void setRo5(BNrioIOPointEntry v) { set(ro5, v, null); }

  //endregion Property "ro5"

  //region Property "ro6"

  /**
   * Slot for the {@code ro6} property.
   * @see #getRo6
   * @see #setRo6
   */
  public static final Property ro6 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 6), null);

  /**
   * Get the {@code ro6} property.
   * @see #ro6
   */
  public BNrioIOPointEntry getRo6() { return (BNrioIOPointEntry)get(ro6); }

  /**
   * Set the {@code ro6} property.
   * @see #ro6
   */
  public void setRo6(BNrioIOPointEntry v) { set(ro6, v, null); }

  //endregion Property "ro6"

  //region Property "ro7"

  /**
   * Slot for the {@code ro7} property.
   * @see #getRo7
   * @see #setRo7
   */
  public static final Property ro7 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 7), null);

  /**
   * Get the {@code ro7} property.
   * @see #ro7
   */
  public BNrioIOPointEntry getRo7() { return (BNrioIOPointEntry)get(ro7); }

  /**
   * Set the {@code ro7} property.
   * @see #ro7
   */
  public void setRo7(BNrioIOPointEntry v) { set(ro7, v, null); }

  //endregion Property "ro7"

  //region Property "ro8"

  /**
   * Slot for the {@code ro8} property.
   * @see #getRo8
   * @see #setRo8
   */
  public static final Property ro8 = newProperty(0, new BNrioIOPointEntry(BNrioIoTypeEnum.relayOutput, 8), null);

  /**
   * Get the {@code ro8} property.
   * @see #ro8
   */
  public BNrioIOPointEntry getRo8() { return (BNrioIOPointEntry)get(ro8); }

  /**
   * Set the {@code ro8} property.
   * @see #ro8
   */
  public void setRo8(BNrioIOPointEntry v) { set(ro8, v, null); }

  //endregion Property "ro8"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInputOutputModulePoints.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BInputOutputModulePoints(){}
}
