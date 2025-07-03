/**
 * Copyright 2006
 */
package com.tridium.kitControl.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 *  BNumericToBitsDemux is a component which can break out
 *  BStatusNumeric value into individual bits (up to 32) and 
 *  individual bytes (up to 4).
 *  
 *  Behavior with negative input is not defined
 *
 *  A BStatusNumeric should be linked to the 
 *  "inNumeric" property of this point.  The Status portion is 
 *  propagated to all of the BStatusBoolean outputs
 *
 * @author    Clif Turman
 * @creation  05 Oct 2007
 * @version   $Revision: 1$ $Date: 9/12/2006 9:03:52 AM$
 * @since     Niagara 3.3
 */
@NiagaraType
/*
 A BStatusNumeric type is linked to this input.
 The numeric portion of the input is mapped to 32 bits which
 are presented on the bitn outputs.  In addition, the input
 is split into 4 bytes, which are mapped to the byten outputs.
 The status portion of the input is propagated to every output
 */
@NiagaraProperty(
  name = "inNumeric",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "bit0",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit1",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit2",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit3",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit4",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit5",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit6",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit7",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit8",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit9",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit10",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit11",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit12",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit13",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit14",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit15",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit16",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit17",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit18",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit19",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit20",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit21",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit22",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit23",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit24",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit25",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit26",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit27",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit28",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit29",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit30",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "bit31",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "byte0",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "byte1",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "byte2",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "byte3",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
public class BNumericToBitsDemux
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BNumericToBitsDemux(2995858113)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inNumeric"

  /**
   * Slot for the {@code inNumeric} property.
   * A BStatusNumeric type is linked to this input.
   * The numeric portion of the input is mapped to 32 bits which
   * are presented on the bitn outputs.  In addition, the input
   * is split into 4 bytes, which are mapped to the byten outputs.
   * The status portion of the input is propagated to every output
   * @see #getInNumeric
   * @see #setInNumeric
   */
  public static final Property inNumeric = newProperty(Flags.OPERATOR | Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code inNumeric} property.
   * A BStatusNumeric type is linked to this input.
   * The numeric portion of the input is mapped to 32 bits which
   * are presented on the bitn outputs.  In addition, the input
   * is split into 4 bytes, which are mapped to the byten outputs.
   * The status portion of the input is propagated to every output
   * @see #inNumeric
   */
  public BStatusNumeric getInNumeric() { return (BStatusNumeric)get(inNumeric); }

  /**
   * Set the {@code inNumeric} property.
   * A BStatusNumeric type is linked to this input.
   * The numeric portion of the input is mapped to 32 bits which
   * are presented on the bitn outputs.  In addition, the input
   * is split into 4 bytes, which are mapped to the byten outputs.
   * The status portion of the input is propagated to every output
   * @see #inNumeric
   */
  public void setInNumeric(BStatusNumeric v) { set(inNumeric, v, null); }

  //endregion Property "inNumeric"

  //region Property "bit0"

  /**
   * Slot for the {@code bit0} property.
   * @see #getBit0
   * @see #setBit0
   */
  public static final Property bit0 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit0} property.
   * @see #bit0
   */
  public BStatusBoolean getBit0() { return (BStatusBoolean)get(bit0); }

  /**
   * Set the {@code bit0} property.
   * @see #bit0
   */
  public void setBit0(BStatusBoolean v) { set(bit0, v, null); }

  //endregion Property "bit0"

  //region Property "bit1"

  /**
   * Slot for the {@code bit1} property.
   * @see #getBit1
   * @see #setBit1
   */
  public static final Property bit1 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit1} property.
   * @see #bit1
   */
  public BStatusBoolean getBit1() { return (BStatusBoolean)get(bit1); }

  /**
   * Set the {@code bit1} property.
   * @see #bit1
   */
  public void setBit1(BStatusBoolean v) { set(bit1, v, null); }

  //endregion Property "bit1"

  //region Property "bit2"

  /**
   * Slot for the {@code bit2} property.
   * @see #getBit2
   * @see #setBit2
   */
  public static final Property bit2 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit2} property.
   * @see #bit2
   */
  public BStatusBoolean getBit2() { return (BStatusBoolean)get(bit2); }

  /**
   * Set the {@code bit2} property.
   * @see #bit2
   */
  public void setBit2(BStatusBoolean v) { set(bit2, v, null); }

  //endregion Property "bit2"

  //region Property "bit3"

  /**
   * Slot for the {@code bit3} property.
   * @see #getBit3
   * @see #setBit3
   */
  public static final Property bit3 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit3} property.
   * @see #bit3
   */
  public BStatusBoolean getBit3() { return (BStatusBoolean)get(bit3); }

  /**
   * Set the {@code bit3} property.
   * @see #bit3
   */
  public void setBit3(BStatusBoolean v) { set(bit3, v, null); }

  //endregion Property "bit3"

  //region Property "bit4"

  /**
   * Slot for the {@code bit4} property.
   * @see #getBit4
   * @see #setBit4
   */
  public static final Property bit4 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit4} property.
   * @see #bit4
   */
  public BStatusBoolean getBit4() { return (BStatusBoolean)get(bit4); }

  /**
   * Set the {@code bit4} property.
   * @see #bit4
   */
  public void setBit4(BStatusBoolean v) { set(bit4, v, null); }

  //endregion Property "bit4"

  //region Property "bit5"

  /**
   * Slot for the {@code bit5} property.
   * @see #getBit5
   * @see #setBit5
   */
  public static final Property bit5 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit5} property.
   * @see #bit5
   */
  public BStatusBoolean getBit5() { return (BStatusBoolean)get(bit5); }

  /**
   * Set the {@code bit5} property.
   * @see #bit5
   */
  public void setBit5(BStatusBoolean v) { set(bit5, v, null); }

  //endregion Property "bit5"

  //region Property "bit6"

  /**
   * Slot for the {@code bit6} property.
   * @see #getBit6
   * @see #setBit6
   */
  public static final Property bit6 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit6} property.
   * @see #bit6
   */
  public BStatusBoolean getBit6() { return (BStatusBoolean)get(bit6); }

  /**
   * Set the {@code bit6} property.
   * @see #bit6
   */
  public void setBit6(BStatusBoolean v) { set(bit6, v, null); }

  //endregion Property "bit6"

  //region Property "bit7"

  /**
   * Slot for the {@code bit7} property.
   * @see #getBit7
   * @see #setBit7
   */
  public static final Property bit7 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit7} property.
   * @see #bit7
   */
  public BStatusBoolean getBit7() { return (BStatusBoolean)get(bit7); }

  /**
   * Set the {@code bit7} property.
   * @see #bit7
   */
  public void setBit7(BStatusBoolean v) { set(bit7, v, null); }

  //endregion Property "bit7"

  //region Property "bit8"

  /**
   * Slot for the {@code bit8} property.
   * @see #getBit8
   * @see #setBit8
   */
  public static final Property bit8 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit8} property.
   * @see #bit8
   */
  public BStatusBoolean getBit8() { return (BStatusBoolean)get(bit8); }

  /**
   * Set the {@code bit8} property.
   * @see #bit8
   */
  public void setBit8(BStatusBoolean v) { set(bit8, v, null); }

  //endregion Property "bit8"

  //region Property "bit9"

  /**
   * Slot for the {@code bit9} property.
   * @see #getBit9
   * @see #setBit9
   */
  public static final Property bit9 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit9} property.
   * @see #bit9
   */
  public BStatusBoolean getBit9() { return (BStatusBoolean)get(bit9); }

  /**
   * Set the {@code bit9} property.
   * @see #bit9
   */
  public void setBit9(BStatusBoolean v) { set(bit9, v, null); }

  //endregion Property "bit9"

  //region Property "bit10"

  /**
   * Slot for the {@code bit10} property.
   * @see #getBit10
   * @see #setBit10
   */
  public static final Property bit10 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit10} property.
   * @see #bit10
   */
  public BStatusBoolean getBit10() { return (BStatusBoolean)get(bit10); }

  /**
   * Set the {@code bit10} property.
   * @see #bit10
   */
  public void setBit10(BStatusBoolean v) { set(bit10, v, null); }

  //endregion Property "bit10"

  //region Property "bit11"

  /**
   * Slot for the {@code bit11} property.
   * @see #getBit11
   * @see #setBit11
   */
  public static final Property bit11 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit11} property.
   * @see #bit11
   */
  public BStatusBoolean getBit11() { return (BStatusBoolean)get(bit11); }

  /**
   * Set the {@code bit11} property.
   * @see #bit11
   */
  public void setBit11(BStatusBoolean v) { set(bit11, v, null); }

  //endregion Property "bit11"

  //region Property "bit12"

  /**
   * Slot for the {@code bit12} property.
   * @see #getBit12
   * @see #setBit12
   */
  public static final Property bit12 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit12} property.
   * @see #bit12
   */
  public BStatusBoolean getBit12() { return (BStatusBoolean)get(bit12); }

  /**
   * Set the {@code bit12} property.
   * @see #bit12
   */
  public void setBit12(BStatusBoolean v) { set(bit12, v, null); }

  //endregion Property "bit12"

  //region Property "bit13"

  /**
   * Slot for the {@code bit13} property.
   * @see #getBit13
   * @see #setBit13
   */
  public static final Property bit13 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit13} property.
   * @see #bit13
   */
  public BStatusBoolean getBit13() { return (BStatusBoolean)get(bit13); }

  /**
   * Set the {@code bit13} property.
   * @see #bit13
   */
  public void setBit13(BStatusBoolean v) { set(bit13, v, null); }

  //endregion Property "bit13"

  //region Property "bit14"

  /**
   * Slot for the {@code bit14} property.
   * @see #getBit14
   * @see #setBit14
   */
  public static final Property bit14 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit14} property.
   * @see #bit14
   */
  public BStatusBoolean getBit14() { return (BStatusBoolean)get(bit14); }

  /**
   * Set the {@code bit14} property.
   * @see #bit14
   */
  public void setBit14(BStatusBoolean v) { set(bit14, v, null); }

  //endregion Property "bit14"

  //region Property "bit15"

  /**
   * Slot for the {@code bit15} property.
   * @see #getBit15
   * @see #setBit15
   */
  public static final Property bit15 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit15} property.
   * @see #bit15
   */
  public BStatusBoolean getBit15() { return (BStatusBoolean)get(bit15); }

  /**
   * Set the {@code bit15} property.
   * @see #bit15
   */
  public void setBit15(BStatusBoolean v) { set(bit15, v, null); }

  //endregion Property "bit15"

  //region Property "bit16"

  /**
   * Slot for the {@code bit16} property.
   * @see #getBit16
   * @see #setBit16
   */
  public static final Property bit16 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit16} property.
   * @see #bit16
   */
  public BStatusBoolean getBit16() { return (BStatusBoolean)get(bit16); }

  /**
   * Set the {@code bit16} property.
   * @see #bit16
   */
  public void setBit16(BStatusBoolean v) { set(bit16, v, null); }

  //endregion Property "bit16"

  //region Property "bit17"

  /**
   * Slot for the {@code bit17} property.
   * @see #getBit17
   * @see #setBit17
   */
  public static final Property bit17 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit17} property.
   * @see #bit17
   */
  public BStatusBoolean getBit17() { return (BStatusBoolean)get(bit17); }

  /**
   * Set the {@code bit17} property.
   * @see #bit17
   */
  public void setBit17(BStatusBoolean v) { set(bit17, v, null); }

  //endregion Property "bit17"

  //region Property "bit18"

  /**
   * Slot for the {@code bit18} property.
   * @see #getBit18
   * @see #setBit18
   */
  public static final Property bit18 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit18} property.
   * @see #bit18
   */
  public BStatusBoolean getBit18() { return (BStatusBoolean)get(bit18); }

  /**
   * Set the {@code bit18} property.
   * @see #bit18
   */
  public void setBit18(BStatusBoolean v) { set(bit18, v, null); }

  //endregion Property "bit18"

  //region Property "bit19"

  /**
   * Slot for the {@code bit19} property.
   * @see #getBit19
   * @see #setBit19
   */
  public static final Property bit19 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit19} property.
   * @see #bit19
   */
  public BStatusBoolean getBit19() { return (BStatusBoolean)get(bit19); }

  /**
   * Set the {@code bit19} property.
   * @see #bit19
   */
  public void setBit19(BStatusBoolean v) { set(bit19, v, null); }

  //endregion Property "bit19"

  //region Property "bit20"

  /**
   * Slot for the {@code bit20} property.
   * @see #getBit20
   * @see #setBit20
   */
  public static final Property bit20 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit20} property.
   * @see #bit20
   */
  public BStatusBoolean getBit20() { return (BStatusBoolean)get(bit20); }

  /**
   * Set the {@code bit20} property.
   * @see #bit20
   */
  public void setBit20(BStatusBoolean v) { set(bit20, v, null); }

  //endregion Property "bit20"

  //region Property "bit21"

  /**
   * Slot for the {@code bit21} property.
   * @see #getBit21
   * @see #setBit21
   */
  public static final Property bit21 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit21} property.
   * @see #bit21
   */
  public BStatusBoolean getBit21() { return (BStatusBoolean)get(bit21); }

  /**
   * Set the {@code bit21} property.
   * @see #bit21
   */
  public void setBit21(BStatusBoolean v) { set(bit21, v, null); }

  //endregion Property "bit21"

  //region Property "bit22"

  /**
   * Slot for the {@code bit22} property.
   * @see #getBit22
   * @see #setBit22
   */
  public static final Property bit22 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit22} property.
   * @see #bit22
   */
  public BStatusBoolean getBit22() { return (BStatusBoolean)get(bit22); }

  /**
   * Set the {@code bit22} property.
   * @see #bit22
   */
  public void setBit22(BStatusBoolean v) { set(bit22, v, null); }

  //endregion Property "bit22"

  //region Property "bit23"

  /**
   * Slot for the {@code bit23} property.
   * @see #getBit23
   * @see #setBit23
   */
  public static final Property bit23 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit23} property.
   * @see #bit23
   */
  public BStatusBoolean getBit23() { return (BStatusBoolean)get(bit23); }

  /**
   * Set the {@code bit23} property.
   * @see #bit23
   */
  public void setBit23(BStatusBoolean v) { set(bit23, v, null); }

  //endregion Property "bit23"

  //region Property "bit24"

  /**
   * Slot for the {@code bit24} property.
   * @see #getBit24
   * @see #setBit24
   */
  public static final Property bit24 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit24} property.
   * @see #bit24
   */
  public BStatusBoolean getBit24() { return (BStatusBoolean)get(bit24); }

  /**
   * Set the {@code bit24} property.
   * @see #bit24
   */
  public void setBit24(BStatusBoolean v) { set(bit24, v, null); }

  //endregion Property "bit24"

  //region Property "bit25"

  /**
   * Slot for the {@code bit25} property.
   * @see #getBit25
   * @see #setBit25
   */
  public static final Property bit25 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit25} property.
   * @see #bit25
   */
  public BStatusBoolean getBit25() { return (BStatusBoolean)get(bit25); }

  /**
   * Set the {@code bit25} property.
   * @see #bit25
   */
  public void setBit25(BStatusBoolean v) { set(bit25, v, null); }

  //endregion Property "bit25"

  //region Property "bit26"

  /**
   * Slot for the {@code bit26} property.
   * @see #getBit26
   * @see #setBit26
   */
  public static final Property bit26 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit26} property.
   * @see #bit26
   */
  public BStatusBoolean getBit26() { return (BStatusBoolean)get(bit26); }

  /**
   * Set the {@code bit26} property.
   * @see #bit26
   */
  public void setBit26(BStatusBoolean v) { set(bit26, v, null); }

  //endregion Property "bit26"

  //region Property "bit27"

  /**
   * Slot for the {@code bit27} property.
   * @see #getBit27
   * @see #setBit27
   */
  public static final Property bit27 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit27} property.
   * @see #bit27
   */
  public BStatusBoolean getBit27() { return (BStatusBoolean)get(bit27); }

  /**
   * Set the {@code bit27} property.
   * @see #bit27
   */
  public void setBit27(BStatusBoolean v) { set(bit27, v, null); }

  //endregion Property "bit27"

  //region Property "bit28"

  /**
   * Slot for the {@code bit28} property.
   * @see #getBit28
   * @see #setBit28
   */
  public static final Property bit28 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit28} property.
   * @see #bit28
   */
  public BStatusBoolean getBit28() { return (BStatusBoolean)get(bit28); }

  /**
   * Set the {@code bit28} property.
   * @see #bit28
   */
  public void setBit28(BStatusBoolean v) { set(bit28, v, null); }

  //endregion Property "bit28"

  //region Property "bit29"

  /**
   * Slot for the {@code bit29} property.
   * @see #getBit29
   * @see #setBit29
   */
  public static final Property bit29 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit29} property.
   * @see #bit29
   */
  public BStatusBoolean getBit29() { return (BStatusBoolean)get(bit29); }

  /**
   * Set the {@code bit29} property.
   * @see #bit29
   */
  public void setBit29(BStatusBoolean v) { set(bit29, v, null); }

  //endregion Property "bit29"

  //region Property "bit30"

  /**
   * Slot for the {@code bit30} property.
   * @see #getBit30
   * @see #setBit30
   */
  public static final Property bit30 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit30} property.
   * @see #bit30
   */
  public BStatusBoolean getBit30() { return (BStatusBoolean)get(bit30); }

  /**
   * Set the {@code bit30} property.
   * @see #bit30
   */
  public void setBit30(BStatusBoolean v) { set(bit30, v, null); }

  //endregion Property "bit30"

  //region Property "bit31"

  /**
   * Slot for the {@code bit31} property.
   * @see #getBit31
   * @see #setBit31
   */
  public static final Property bit31 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code bit31} property.
   * @see #bit31
   */
  public BStatusBoolean getBit31() { return (BStatusBoolean)get(bit31); }

  /**
   * Set the {@code bit31} property.
   * @see #bit31
   */
  public void setBit31(BStatusBoolean v) { set(bit31, v, null); }

  //endregion Property "bit31"

  //region Property "byte0"

  /**
   * Slot for the {@code byte0} property.
   * @see #getByte0
   * @see #setByte0
   */
  public static final Property byte0 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code byte0} property.
   * @see #byte0
   */
  public BStatusNumeric getByte0() { return (BStatusNumeric)get(byte0); }

  /**
   * Set the {@code byte0} property.
   * @see #byte0
   */
  public void setByte0(BStatusNumeric v) { set(byte0, v, null); }

  //endregion Property "byte0"

  //region Property "byte1"

  /**
   * Slot for the {@code byte1} property.
   * @see #getByte1
   * @see #setByte1
   */
  public static final Property byte1 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code byte1} property.
   * @see #byte1
   */
  public BStatusNumeric getByte1() { return (BStatusNumeric)get(byte1); }

  /**
   * Set the {@code byte1} property.
   * @see #byte1
   */
  public void setByte1(BStatusNumeric v) { set(byte1, v, null); }

  //endregion Property "byte1"

  //region Property "byte2"

  /**
   * Slot for the {@code byte2} property.
   * @see #getByte2
   * @see #setByte2
   */
  public static final Property byte2 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code byte2} property.
   * @see #byte2
   */
  public BStatusNumeric getByte2() { return (BStatusNumeric)get(byte2); }

  /**
   * Set the {@code byte2} property.
   * @see #byte2
   */
  public void setByte2(BStatusNumeric v) { set(byte2, v, null); }

  //endregion Property "byte2"

  //region Property "byte3"

  /**
   * Slot for the {@code byte3} property.
   * @see #getByte3
   * @see #setByte3
   */
  public static final Property byte3 = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code byte3} property.
   * @see #byte3
   */
  public BStatusNumeric getByte3() { return (BStatusNumeric)get(byte3); }

  /**
   * Set the {@code byte3} property.
   * @see #byte3
   */
  public void setByte3(BStatusNumeric v) { set(byte3, v, null); }

  //endregion Property "byte3"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericToBitsDemux.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      if( property.getName().startsWith("in") )
      {
        calculate();
      }
    }
  }

  /**
   * Default implementation is to do nothing.
   */
  public void calculate()
  {
    checkLinks();
    BStatus status = getInNumeric().getStatus();

    long number = (long)getInNumeric().getNumeric();
    
    //range checks added
    //NOTE: -1           --> 0xFFFFFFFF
    //      -2147483648  --> 0x80000000
    if(number > 0xFFFFFFFFL) number = 0xFFFFFFFFL;
    if(number < Integer.MIN_VALUE) number = Integer.MIN_VALUE;  //-2147483648
    
    setBit0 ( new BStatusBoolean(((number & 0x00000001L) == 0x00000001L),status));
    setBit1 ( new BStatusBoolean(((number & 0x00000002L) == 0x00000002L),status));
    setBit2 ( new BStatusBoolean(((number & 0x00000004L) == 0x00000004L),status));
    setBit3 ( new BStatusBoolean(((number & 0x00000008L) == 0x00000008L),status));
    setBit4 ( new BStatusBoolean(((number & 0x00000010L) == 0x00000010L),status));
    setBit5 ( new BStatusBoolean(((number & 0x00000020L) == 0x00000020L),status));
    setBit6 ( new BStatusBoolean(((number & 0x00000040L) == 0x00000040L),status));
    setBit7 ( new BStatusBoolean(((number & 0x00000080L) == 0x00000080L),status));
    setBit8 ( new BStatusBoolean(((number & 0x00000100L) == 0x00000100L),status));
    setBit9 ( new BStatusBoolean(((number & 0x00000200L) == 0x00000200L),status));
    setBit10( new BStatusBoolean(((number & 0x00000400L) == 0x00000400L),status));
    setBit11( new BStatusBoolean(((number & 0x00000800L) == 0x00000800L),status));
    setBit12( new BStatusBoolean(((number & 0x00001000L) == 0x00001000L),status));
    setBit13( new BStatusBoolean(((number & 0x00002000L) == 0x00002000L),status));
    setBit14( new BStatusBoolean(((number & 0x00004000L) == 0x00004000L),status));
    setBit15( new BStatusBoolean(((number & 0x00008000L) == 0x00008000L),status));
    setBit16( new BStatusBoolean(((number & 0x00010000L) == 0x00010000L),status));
    setBit17( new BStatusBoolean(((number & 0x00020000L) == 0x00020000L),status));
    setBit18( new BStatusBoolean(((number & 0x00040000L) == 0x00040000L),status));
    setBit19( new BStatusBoolean(((number & 0x00080000L) == 0x00080000L),status));
    setBit20( new BStatusBoolean(((number & 0x00100000L) == 0x00100000L),status));
    setBit21( new BStatusBoolean(((number & 0x00200000L) == 0x00200000L),status));
    setBit22( new BStatusBoolean(((number & 0x00400000L) == 0x00400000L),status));
    setBit23( new BStatusBoolean(((number & 0x00800000L) == 0x00800000L),status));
    setBit24( new BStatusBoolean(((number & 0x01000000L) == 0x01000000L),status));
    setBit25( new BStatusBoolean(((number & 0x02000000L) == 0x02000000L),status));
    setBit26( new BStatusBoolean(((number & 0x04000000L) == 0x04000000L),status));
    setBit27( new BStatusBoolean(((number & 0x08000000L) == 0x08000000L),status));
    setBit28( new BStatusBoolean(((number & 0x10000000L) == 0x10000000L),status));
    setBit29( new BStatusBoolean(((number & 0x20000000L) == 0x20000000L),status));
    setBit30( new BStatusBoolean(((number & 0x40000000L) == 0x40000000L),status));
    setBit31( new BStatusBoolean(((number & 0x80000000L) == 0x80000000L),status));
    setByte0(new BStatusNumeric((number & 0x000000FFL), status));
    setByte1(new BStatusNumeric(((number & 0x0000FF00L) >>> 8), status));
    setByte2(new BStatusNumeric(((number & 0x00FF0000L) >>> 16), status));
    setByte3(new BStatusNumeric(((number & 0xFF000000L) >>> 24), status));
  }

  void checkLinks()
  {
    BLink[] links = getLinks(getSlot("inNumeric"));
    if(links.length == 0)
      setInNumeric(newNumeric);
  }



////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

  private BStatusNumeric newNumeric = new BStatusNumeric(0f);

}
