/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.points;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.components.BUIPointEntry;
import com.tridium.nrio.enums.BNrioIoTypeEnum;

/**
 * BNrio16ModulePoints - The learn IO Module point definitions.
 * 
 *
 * @author    Andy Saunders
 * @creation  7 Oct 2016
 */

@NiagaraType
@NiagaraProperty(
  name = "ui1",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 1)"
)
@NiagaraProperty(
  name = "ui2",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 2)"
)
@NiagaraProperty(
  name = "ui3",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 3)"
)
@NiagaraProperty(
  name = "ui4",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 4)"
)
@NiagaraProperty(
  name = "ui5",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 5)"
)
@NiagaraProperty(
  name = "ui6",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 6)"
)
@NiagaraProperty(
  name = "ui7",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 7)"
)
@NiagaraProperty(
  name = "ui8",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 8)"
)
@NiagaraProperty(
  name = "ui9",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 9)"
)
@NiagaraProperty(
  name = "ui10",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 10)"
)
@NiagaraProperty(
  name = "ui11",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 11)"
)
@NiagaraProperty(
  name = "ui12",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 12)"
)
@NiagaraProperty(
  name = "ui13",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 13)"
)
@NiagaraProperty(
  name = "ui14",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 14)"
)
@NiagaraProperty(
  name = "ui15",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 15)"
)
@NiagaraProperty(
  name = "ui16",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.universalInput, 16)"
)
@NiagaraProperty(
  name = "ro1",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 1)"
)
@NiagaraProperty(
  name = "ro2",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 2)"
)
@NiagaraProperty(
  name = "ro3",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 3)"
)
@NiagaraProperty(
  name = "ro4",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 4)"
)
@NiagaraProperty(
  name = "ro5",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 5)"
)
@NiagaraProperty(
  name = "ro6",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 6)"
)
@NiagaraProperty(
  name = "ro7",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 7)"
)
@NiagaraProperty(
  name = "ro8",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 8)"
)
@NiagaraProperty(
  name = "ro9",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 9)"
)
@NiagaraProperty(
  name = "ro10",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 10)"
)
@NiagaraProperty(
  name = "ao1",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 1)"
)
@NiagaraProperty(
  name = "ao2",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 2)"
)
@NiagaraProperty(
  name = "ao3",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 3)"
)
@NiagaraProperty(
  name = "ao4",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 4)"
)
@NiagaraProperty(
  name = "ao5",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 5)"
)
@NiagaraProperty(
  name = "ao6",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 6)"
)
@NiagaraProperty(
  name = "ao7",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 7)"
)
@NiagaraProperty(
  name = "ao8",
  type = "BUIPointEntry",
  defaultValue = "new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 8)"
)



public class BNrio34ModulePoints
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrio34ModulePoints(3368863824)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ui1"

  /**
   * Slot for the {@code ui1} property.
   * @see #getUi1
   * @see #setUi1
   */
  public static final Property ui1 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 1), null);

  /**
   * Get the {@code ui1} property.
   * @see #ui1
   */
  public BUIPointEntry getUi1() { return (BUIPointEntry)get(ui1); }

  /**
   * Set the {@code ui1} property.
   * @see #ui1
   */
  public void setUi1(BUIPointEntry v) { set(ui1, v, null); }

  //endregion Property "ui1"

  //region Property "ui2"

  /**
   * Slot for the {@code ui2} property.
   * @see #getUi2
   * @see #setUi2
   */
  public static final Property ui2 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 2), null);

  /**
   * Get the {@code ui2} property.
   * @see #ui2
   */
  public BUIPointEntry getUi2() { return (BUIPointEntry)get(ui2); }

  /**
   * Set the {@code ui2} property.
   * @see #ui2
   */
  public void setUi2(BUIPointEntry v) { set(ui2, v, null); }

  //endregion Property "ui2"

  //region Property "ui3"

  /**
   * Slot for the {@code ui3} property.
   * @see #getUi3
   * @see #setUi3
   */
  public static final Property ui3 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 3), null);

  /**
   * Get the {@code ui3} property.
   * @see #ui3
   */
  public BUIPointEntry getUi3() { return (BUIPointEntry)get(ui3); }

  /**
   * Set the {@code ui3} property.
   * @see #ui3
   */
  public void setUi3(BUIPointEntry v) { set(ui3, v, null); }

  //endregion Property "ui3"

  //region Property "ui4"

  /**
   * Slot for the {@code ui4} property.
   * @see #getUi4
   * @see #setUi4
   */
  public static final Property ui4 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 4), null);

  /**
   * Get the {@code ui4} property.
   * @see #ui4
   */
  public BUIPointEntry getUi4() { return (BUIPointEntry)get(ui4); }

  /**
   * Set the {@code ui4} property.
   * @see #ui4
   */
  public void setUi4(BUIPointEntry v) { set(ui4, v, null); }

  //endregion Property "ui4"

  //region Property "ui5"

  /**
   * Slot for the {@code ui5} property.
   * @see #getUi5
   * @see #setUi5
   */
  public static final Property ui5 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 5), null);

  /**
   * Get the {@code ui5} property.
   * @see #ui5
   */
  public BUIPointEntry getUi5() { return (BUIPointEntry)get(ui5); }

  /**
   * Set the {@code ui5} property.
   * @see #ui5
   */
  public void setUi5(BUIPointEntry v) { set(ui5, v, null); }

  //endregion Property "ui5"

  //region Property "ui6"

  /**
   * Slot for the {@code ui6} property.
   * @see #getUi6
   * @see #setUi6
   */
  public static final Property ui6 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 6), null);

  /**
   * Get the {@code ui6} property.
   * @see #ui6
   */
  public BUIPointEntry getUi6() { return (BUIPointEntry)get(ui6); }

  /**
   * Set the {@code ui6} property.
   * @see #ui6
   */
  public void setUi6(BUIPointEntry v) { set(ui6, v, null); }

  //endregion Property "ui6"

  //region Property "ui7"

  /**
   * Slot for the {@code ui7} property.
   * @see #getUi7
   * @see #setUi7
   */
  public static final Property ui7 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 7), null);

  /**
   * Get the {@code ui7} property.
   * @see #ui7
   */
  public BUIPointEntry getUi7() { return (BUIPointEntry)get(ui7); }

  /**
   * Set the {@code ui7} property.
   * @see #ui7
   */
  public void setUi7(BUIPointEntry v) { set(ui7, v, null); }

  //endregion Property "ui7"

  //region Property "ui8"

  /**
   * Slot for the {@code ui8} property.
   * @see #getUi8
   * @see #setUi8
   */
  public static final Property ui8 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 8), null);

  /**
   * Get the {@code ui8} property.
   * @see #ui8
   */
  public BUIPointEntry getUi8() { return (BUIPointEntry)get(ui8); }

  /**
   * Set the {@code ui8} property.
   * @see #ui8
   */
  public void setUi8(BUIPointEntry v) { set(ui8, v, null); }

  //endregion Property "ui8"

  //region Property "ui9"

  /**
   * Slot for the {@code ui9} property.
   * @see #getUi9
   * @see #setUi9
   */
  public static final Property ui9 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 9), null);

  /**
   * Get the {@code ui9} property.
   * @see #ui9
   */
  public BUIPointEntry getUi9() { return (BUIPointEntry)get(ui9); }

  /**
   * Set the {@code ui9} property.
   * @see #ui9
   */
  public void setUi9(BUIPointEntry v) { set(ui9, v, null); }

  //endregion Property "ui9"

  //region Property "ui10"

  /**
   * Slot for the {@code ui10} property.
   * @see #getUi10
   * @see #setUi10
   */
  public static final Property ui10 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 10), null);

  /**
   * Get the {@code ui10} property.
   * @see #ui10
   */
  public BUIPointEntry getUi10() { return (BUIPointEntry)get(ui10); }

  /**
   * Set the {@code ui10} property.
   * @see #ui10
   */
  public void setUi10(BUIPointEntry v) { set(ui10, v, null); }

  //endregion Property "ui10"

  //region Property "ui11"

  /**
   * Slot for the {@code ui11} property.
   * @see #getUi11
   * @see #setUi11
   */
  public static final Property ui11 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 11), null);

  /**
   * Get the {@code ui11} property.
   * @see #ui11
   */
  public BUIPointEntry getUi11() { return (BUIPointEntry)get(ui11); }

  /**
   * Set the {@code ui11} property.
   * @see #ui11
   */
  public void setUi11(BUIPointEntry v) { set(ui11, v, null); }

  //endregion Property "ui11"

  //region Property "ui12"

  /**
   * Slot for the {@code ui12} property.
   * @see #getUi12
   * @see #setUi12
   */
  public static final Property ui12 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 12), null);

  /**
   * Get the {@code ui12} property.
   * @see #ui12
   */
  public BUIPointEntry getUi12() { return (BUIPointEntry)get(ui12); }

  /**
   * Set the {@code ui12} property.
   * @see #ui12
   */
  public void setUi12(BUIPointEntry v) { set(ui12, v, null); }

  //endregion Property "ui12"

  //region Property "ui13"

  /**
   * Slot for the {@code ui13} property.
   * @see #getUi13
   * @see #setUi13
   */
  public static final Property ui13 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 13), null);

  /**
   * Get the {@code ui13} property.
   * @see #ui13
   */
  public BUIPointEntry getUi13() { return (BUIPointEntry)get(ui13); }

  /**
   * Set the {@code ui13} property.
   * @see #ui13
   */
  public void setUi13(BUIPointEntry v) { set(ui13, v, null); }

  //endregion Property "ui13"

  //region Property "ui14"

  /**
   * Slot for the {@code ui14} property.
   * @see #getUi14
   * @see #setUi14
   */
  public static final Property ui14 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 14), null);

  /**
   * Get the {@code ui14} property.
   * @see #ui14
   */
  public BUIPointEntry getUi14() { return (BUIPointEntry)get(ui14); }

  /**
   * Set the {@code ui14} property.
   * @see #ui14
   */
  public void setUi14(BUIPointEntry v) { set(ui14, v, null); }

  //endregion Property "ui14"

  //region Property "ui15"

  /**
   * Slot for the {@code ui15} property.
   * @see #getUi15
   * @see #setUi15
   */
  public static final Property ui15 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 15), null);

  /**
   * Get the {@code ui15} property.
   * @see #ui15
   */
  public BUIPointEntry getUi15() { return (BUIPointEntry)get(ui15); }

  /**
   * Set the {@code ui15} property.
   * @see #ui15
   */
  public void setUi15(BUIPointEntry v) { set(ui15, v, null); }

  //endregion Property "ui15"

  //region Property "ui16"

  /**
   * Slot for the {@code ui16} property.
   * @see #getUi16
   * @see #setUi16
   */
  public static final Property ui16 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.universalInput, 16), null);

  /**
   * Get the {@code ui16} property.
   * @see #ui16
   */
  public BUIPointEntry getUi16() { return (BUIPointEntry)get(ui16); }

  /**
   * Set the {@code ui16} property.
   * @see #ui16
   */
  public void setUi16(BUIPointEntry v) { set(ui16, v, null); }

  //endregion Property "ui16"

  //region Property "ro1"

  /**
   * Slot for the {@code ro1} property.
   * @see #getRo1
   * @see #setRo1
   */
  public static final Property ro1 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 1), null);

  /**
   * Get the {@code ro1} property.
   * @see #ro1
   */
  public BUIPointEntry getRo1() { return (BUIPointEntry)get(ro1); }

  /**
   * Set the {@code ro1} property.
   * @see #ro1
   */
  public void setRo1(BUIPointEntry v) { set(ro1, v, null); }

  //endregion Property "ro1"

  //region Property "ro2"

  /**
   * Slot for the {@code ro2} property.
   * @see #getRo2
   * @see #setRo2
   */
  public static final Property ro2 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 2), null);

  /**
   * Get the {@code ro2} property.
   * @see #ro2
   */
  public BUIPointEntry getRo2() { return (BUIPointEntry)get(ro2); }

  /**
   * Set the {@code ro2} property.
   * @see #ro2
   */
  public void setRo2(BUIPointEntry v) { set(ro2, v, null); }

  //endregion Property "ro2"

  //region Property "ro3"

  /**
   * Slot for the {@code ro3} property.
   * @see #getRo3
   * @see #setRo3
   */
  public static final Property ro3 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 3), null);

  /**
   * Get the {@code ro3} property.
   * @see #ro3
   */
  public BUIPointEntry getRo3() { return (BUIPointEntry)get(ro3); }

  /**
   * Set the {@code ro3} property.
   * @see #ro3
   */
  public void setRo3(BUIPointEntry v) { set(ro3, v, null); }

  //endregion Property "ro3"

  //region Property "ro4"

  /**
   * Slot for the {@code ro4} property.
   * @see #getRo4
   * @see #setRo4
   */
  public static final Property ro4 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 4), null);

  /**
   * Get the {@code ro4} property.
   * @see #ro4
   */
  public BUIPointEntry getRo4() { return (BUIPointEntry)get(ro4); }

  /**
   * Set the {@code ro4} property.
   * @see #ro4
   */
  public void setRo4(BUIPointEntry v) { set(ro4, v, null); }

  //endregion Property "ro4"

  //region Property "ro5"

  /**
   * Slot for the {@code ro5} property.
   * @see #getRo5
   * @see #setRo5
   */
  public static final Property ro5 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 5), null);

  /**
   * Get the {@code ro5} property.
   * @see #ro5
   */
  public BUIPointEntry getRo5() { return (BUIPointEntry)get(ro5); }

  /**
   * Set the {@code ro5} property.
   * @see #ro5
   */
  public void setRo5(BUIPointEntry v) { set(ro5, v, null); }

  //endregion Property "ro5"

  //region Property "ro6"

  /**
   * Slot for the {@code ro6} property.
   * @see #getRo6
   * @see #setRo6
   */
  public static final Property ro6 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 6), null);

  /**
   * Get the {@code ro6} property.
   * @see #ro6
   */
  public BUIPointEntry getRo6() { return (BUIPointEntry)get(ro6); }

  /**
   * Set the {@code ro6} property.
   * @see #ro6
   */
  public void setRo6(BUIPointEntry v) { set(ro6, v, null); }

  //endregion Property "ro6"

  //region Property "ro7"

  /**
   * Slot for the {@code ro7} property.
   * @see #getRo7
   * @see #setRo7
   */
  public static final Property ro7 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 7), null);

  /**
   * Get the {@code ro7} property.
   * @see #ro7
   */
  public BUIPointEntry getRo7() { return (BUIPointEntry)get(ro7); }

  /**
   * Set the {@code ro7} property.
   * @see #ro7
   */
  public void setRo7(BUIPointEntry v) { set(ro7, v, null); }

  //endregion Property "ro7"

  //region Property "ro8"

  /**
   * Slot for the {@code ro8} property.
   * @see #getRo8
   * @see #setRo8
   */
  public static final Property ro8 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 8), null);

  /**
   * Get the {@code ro8} property.
   * @see #ro8
   */
  public BUIPointEntry getRo8() { return (BUIPointEntry)get(ro8); }

  /**
   * Set the {@code ro8} property.
   * @see #ro8
   */
  public void setRo8(BUIPointEntry v) { set(ro8, v, null); }

  //endregion Property "ro8"

  //region Property "ro9"

  /**
   * Slot for the {@code ro9} property.
   * @see #getRo9
   * @see #setRo9
   */
  public static final Property ro9 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 9), null);

  /**
   * Get the {@code ro9} property.
   * @see #ro9
   */
  public BUIPointEntry getRo9() { return (BUIPointEntry)get(ro9); }

  /**
   * Set the {@code ro9} property.
   * @see #ro9
   */
  public void setRo9(BUIPointEntry v) { set(ro9, v, null); }

  //endregion Property "ro9"

  //region Property "ro10"

  /**
   * Slot for the {@code ro10} property.
   * @see #getRo10
   * @see #setRo10
   */
  public static final Property ro10 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.relayOutput, 10), null);

  /**
   * Get the {@code ro10} property.
   * @see #ro10
   */
  public BUIPointEntry getRo10() { return (BUIPointEntry)get(ro10); }

  /**
   * Set the {@code ro10} property.
   * @see #ro10
   */
  public void setRo10(BUIPointEntry v) { set(ro10, v, null); }

  //endregion Property "ro10"

  //region Property "ao1"

  /**
   * Slot for the {@code ao1} property.
   * @see #getAo1
   * @see #setAo1
   */
  public static final Property ao1 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 1), null);

  /**
   * Get the {@code ao1} property.
   * @see #ao1
   */
  public BUIPointEntry getAo1() { return (BUIPointEntry)get(ao1); }

  /**
   * Set the {@code ao1} property.
   * @see #ao1
   */
  public void setAo1(BUIPointEntry v) { set(ao1, v, null); }

  //endregion Property "ao1"

  //region Property "ao2"

  /**
   * Slot for the {@code ao2} property.
   * @see #getAo2
   * @see #setAo2
   */
  public static final Property ao2 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 2), null);

  /**
   * Get the {@code ao2} property.
   * @see #ao2
   */
  public BUIPointEntry getAo2() { return (BUIPointEntry)get(ao2); }

  /**
   * Set the {@code ao2} property.
   * @see #ao2
   */
  public void setAo2(BUIPointEntry v) { set(ao2, v, null); }

  //endregion Property "ao2"

  //region Property "ao3"

  /**
   * Slot for the {@code ao3} property.
   * @see #getAo3
   * @see #setAo3
   */
  public static final Property ao3 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 3), null);

  /**
   * Get the {@code ao3} property.
   * @see #ao3
   */
  public BUIPointEntry getAo3() { return (BUIPointEntry)get(ao3); }

  /**
   * Set the {@code ao3} property.
   * @see #ao3
   */
  public void setAo3(BUIPointEntry v) { set(ao3, v, null); }

  //endregion Property "ao3"

  //region Property "ao4"

  /**
   * Slot for the {@code ao4} property.
   * @see #getAo4
   * @see #setAo4
   */
  public static final Property ao4 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 4), null);

  /**
   * Get the {@code ao4} property.
   * @see #ao4
   */
  public BUIPointEntry getAo4() { return (BUIPointEntry)get(ao4); }

  /**
   * Set the {@code ao4} property.
   * @see #ao4
   */
  public void setAo4(BUIPointEntry v) { set(ao4, v, null); }

  //endregion Property "ao4"

  //region Property "ao5"

  /**
   * Slot for the {@code ao5} property.
   * @see #getAo5
   * @see #setAo5
   */
  public static final Property ao5 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 5), null);

  /**
   * Get the {@code ao5} property.
   * @see #ao5
   */
  public BUIPointEntry getAo5() { return (BUIPointEntry)get(ao5); }

  /**
   * Set the {@code ao5} property.
   * @see #ao5
   */
  public void setAo5(BUIPointEntry v) { set(ao5, v, null); }

  //endregion Property "ao5"

  //region Property "ao6"

  /**
   * Slot for the {@code ao6} property.
   * @see #getAo6
   * @see #setAo6
   */
  public static final Property ao6 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 6), null);

  /**
   * Get the {@code ao6} property.
   * @see #ao6
   */
  public BUIPointEntry getAo6() { return (BUIPointEntry)get(ao6); }

  /**
   * Set the {@code ao6} property.
   * @see #ao6
   */
  public void setAo6(BUIPointEntry v) { set(ao6, v, null); }

  //endregion Property "ao6"

  //region Property "ao7"

  /**
   * Slot for the {@code ao7} property.
   * @see #getAo7
   * @see #setAo7
   */
  public static final Property ao7 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 7), null);

  /**
   * Get the {@code ao7} property.
   * @see #ao7
   */
  public BUIPointEntry getAo7() { return (BUIPointEntry)get(ao7); }

  /**
   * Set the {@code ao7} property.
   * @see #ao7
   */
  public void setAo7(BUIPointEntry v) { set(ao7, v, null); }

  //endregion Property "ao7"

  //region Property "ao8"

  /**
   * Slot for the {@code ao8} property.
   * @see #getAo8
   * @see #setAo8
   */
  public static final Property ao8 = newProperty(0, new BUIPointEntry(BNrioIoTypeEnum.analogOutput, 8), null);

  /**
   * Get the {@code ao8} property.
   * @see #ao8
   */
  public BUIPointEntry getAo8() { return (BUIPointEntry)get(ao8); }

  /**
   * Set the {@code ao8} property.
   * @see #ao8
   */
  public void setAo8(BUIPointEntry v) { set(ao8, v, null); }

  //endregion Property "ao8"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio34ModulePoints.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNrio34ModulePoints(){}
}
