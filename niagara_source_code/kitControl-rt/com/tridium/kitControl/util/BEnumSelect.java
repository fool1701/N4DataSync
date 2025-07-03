/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BEnumSelect uses a numeric to switch between multiple numerics.
 *
 * @author    Andy Saunders
 * @creation  April 05, 2004
 * @version   $Revision: 19$ $Date: 3/30/2004 3:43:05 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum()",
  override = true
)
/*
 The output of a BNumericPoint is a BStatusEnum
 */
@NiagaraProperty(
  name = "out",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "inA",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inB",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inC",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inD",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inE",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inF",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inG",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inH",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inI",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inJ",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
public class BEnumSelect
  extends BMuxSwitch
  implements BIStatus, BIEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BEnumSelect(3042994527)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeEnum(), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * The output of a BNumericPoint is a BStatusEnum
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code out} property.
   * The output of a BNumericPoint is a BStatusEnum
   * @see #out
   */
  public BStatusEnum getOut() { return (BStatusEnum)get(out); }

  /**
   * Set the {@code out} property.
   * The output of a BNumericPoint is a BStatusEnum
   * @see #out
   */
  public void setOut(BStatusEnum v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "inA"

  /**
   * Slot for the {@code inA} property.
   * @see #getInA
   * @see #setInA
   */
  public static final Property inA = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inA} property.
   * @see #inA
   */
  public BStatusEnum getInA() { return (BStatusEnum)get(inA); }

  /**
   * Set the {@code inA} property.
   * @see #inA
   */
  public void setInA(BStatusEnum v) { set(inA, v, null); }

  //endregion Property "inA"

  //region Property "inB"

  /**
   * Slot for the {@code inB} property.
   * @see #getInB
   * @see #setInB
   */
  public static final Property inB = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inB} property.
   * @see #inB
   */
  public BStatusEnum getInB() { return (BStatusEnum)get(inB); }

  /**
   * Set the {@code inB} property.
   * @see #inB
   */
  public void setInB(BStatusEnum v) { set(inB, v, null); }

  //endregion Property "inB"

  //region Property "inC"

  /**
   * Slot for the {@code inC} property.
   * @see #getInC
   * @see #setInC
   */
  public static final Property inC = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inC} property.
   * @see #inC
   */
  public BStatusEnum getInC() { return (BStatusEnum)get(inC); }

  /**
   * Set the {@code inC} property.
   * @see #inC
   */
  public void setInC(BStatusEnum v) { set(inC, v, null); }

  //endregion Property "inC"

  //region Property "inD"

  /**
   * Slot for the {@code inD} property.
   * @see #getInD
   * @see #setInD
   */
  public static final Property inD = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inD} property.
   * @see #inD
   */
  public BStatusEnum getInD() { return (BStatusEnum)get(inD); }

  /**
   * Set the {@code inD} property.
   * @see #inD
   */
  public void setInD(BStatusEnum v) { set(inD, v, null); }

  //endregion Property "inD"

  //region Property "inE"

  /**
   * Slot for the {@code inE} property.
   * @see #getInE
   * @see #setInE
   */
  public static final Property inE = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inE} property.
   * @see #inE
   */
  public BStatusEnum getInE() { return (BStatusEnum)get(inE); }

  /**
   * Set the {@code inE} property.
   * @see #inE
   */
  public void setInE(BStatusEnum v) { set(inE, v, null); }

  //endregion Property "inE"

  //region Property "inF"

  /**
   * Slot for the {@code inF} property.
   * @see #getInF
   * @see #setInF
   */
  public static final Property inF = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inF} property.
   * @see #inF
   */
  public BStatusEnum getInF() { return (BStatusEnum)get(inF); }

  /**
   * Set the {@code inF} property.
   * @see #inF
   */
  public void setInF(BStatusEnum v) { set(inF, v, null); }

  //endregion Property "inF"

  //region Property "inG"

  /**
   * Slot for the {@code inG} property.
   * @see #getInG
   * @see #setInG
   */
  public static final Property inG = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inG} property.
   * @see #inG
   */
  public BStatusEnum getInG() { return (BStatusEnum)get(inG); }

  /**
   * Set the {@code inG} property.
   * @see #inG
   */
  public void setInG(BStatusEnum v) { set(inG, v, null); }

  //endregion Property "inG"

  //region Property "inH"

  /**
   * Slot for the {@code inH} property.
   * @see #getInH
   * @see #setInH
   */
  public static final Property inH = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inH} property.
   * @see #inH
   */
  public BStatusEnum getInH() { return (BStatusEnum)get(inH); }

  /**
   * Set the {@code inH} property.
   * @see #inH
   */
  public void setInH(BStatusEnum v) { set(inH, v, null); }

  //endregion Property "inH"

  //region Property "inI"

  /**
   * Slot for the {@code inI} property.
   * @see #getInI
   * @see #setInI
   */
  public static final Property inI = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inI} property.
   * @see #inI
   */
  public BStatusEnum getInI() { return (BStatusEnum)get(inI); }

  /**
   * Set the {@code inI} property.
   * @see #inI
   */
  public void setInI(BStatusEnum v) { set(inI, v, null); }

  //endregion Property "inI"

  //region Property "inJ"

  /**
   * Slot for the {@code inJ} property.
   * @see #getInJ
   * @see #setInJ
   */
  public static final Property inJ = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inJ} property.
   * @see #inJ
   */
  public BStatusEnum getInJ() { return (BStatusEnum)get(inJ); }

  /**
   * Set the {@code inJ} property.
   * @see #inJ
   */
  public void setInJ(BStatusEnum v) { set(inJ, v, null); }

  //endregion Property "inJ"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumSelect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Interfaces
////////////////////////////////////////////////////////////////

  public BStatusValue getNullInstance()
  {
    return new BStatusEnum();
  }

  public final BStatusValue getInStatusValue(int select)
  {
    switch(select)
    {
    case 1: return getInA();
    case 2: return getInB();
    case 3: return getInC();
    case 4: return getInD();
    case 5: return getInE();
    case 6: return getInF();
    case 7: return getInG();
    case 8: return getInH();
    case 9: return getInI();
    case 10: return getInJ();
    }
    return getNullInstance();
  }
  
  public void setOutput(BStatusValue value)
  {
    setOut((BStatusEnum)value );
  }
  
  public BStatus getOutStatus()
  {
    return getOut().getStatus();
  }
  
  public void setOutStatus(BStatus status)
  {
    getOut().setStatus(status);
  }
  
  public String toString(Context cx) { return getOut().toString(cx); }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }
  
////////////////////////////////////////////////////////////////
// BIBoolean interface
////////////////////////////////////////////////////////////////

  /**
   * Return the vaule as a enum.
   */
  public final BEnum getEnum() { return getOut().getEnum(); }

  /**
   * Return getFacets().
   */
  public final BFacets getEnumFacets() { return getFacets(); }

  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");


}
