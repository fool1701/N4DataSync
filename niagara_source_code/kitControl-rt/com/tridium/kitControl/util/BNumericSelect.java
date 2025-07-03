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
 * BNumericSelect uses a numeric to switch between multiple numerics.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 19$ $Date: 3/30/2004 3:43:05 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric()",
  override = true
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "inA",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inB",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inC",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inD",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inE",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inF",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inG",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inH",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inI",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inJ",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
public class BNumericSelect
  extends BMuxSwitch
  implements BIStatus, BINumeric
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BNumericSelect(2101193997)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeNumeric(), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code out} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "inA"

  /**
   * Slot for the {@code inA} property.
   * @see #getInA
   * @see #setInA
   */
  public static final Property inA = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inA} property.
   * @see #inA
   */
  public BStatusNumeric getInA() { return (BStatusNumeric)get(inA); }

  /**
   * Set the {@code inA} property.
   * @see #inA
   */
  public void setInA(BStatusNumeric v) { set(inA, v, null); }

  //endregion Property "inA"

  //region Property "inB"

  /**
   * Slot for the {@code inB} property.
   * @see #getInB
   * @see #setInB
   */
  public static final Property inB = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inB} property.
   * @see #inB
   */
  public BStatusNumeric getInB() { return (BStatusNumeric)get(inB); }

  /**
   * Set the {@code inB} property.
   * @see #inB
   */
  public void setInB(BStatusNumeric v) { set(inB, v, null); }

  //endregion Property "inB"

  //region Property "inC"

  /**
   * Slot for the {@code inC} property.
   * @see #getInC
   * @see #setInC
   */
  public static final Property inC = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inC} property.
   * @see #inC
   */
  public BStatusNumeric getInC() { return (BStatusNumeric)get(inC); }

  /**
   * Set the {@code inC} property.
   * @see #inC
   */
  public void setInC(BStatusNumeric v) { set(inC, v, null); }

  //endregion Property "inC"

  //region Property "inD"

  /**
   * Slot for the {@code inD} property.
   * @see #getInD
   * @see #setInD
   */
  public static final Property inD = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inD} property.
   * @see #inD
   */
  public BStatusNumeric getInD() { return (BStatusNumeric)get(inD); }

  /**
   * Set the {@code inD} property.
   * @see #inD
   */
  public void setInD(BStatusNumeric v) { set(inD, v, null); }

  //endregion Property "inD"

  //region Property "inE"

  /**
   * Slot for the {@code inE} property.
   * @see #getInE
   * @see #setInE
   */
  public static final Property inE = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inE} property.
   * @see #inE
   */
  public BStatusNumeric getInE() { return (BStatusNumeric)get(inE); }

  /**
   * Set the {@code inE} property.
   * @see #inE
   */
  public void setInE(BStatusNumeric v) { set(inE, v, null); }

  //endregion Property "inE"

  //region Property "inF"

  /**
   * Slot for the {@code inF} property.
   * @see #getInF
   * @see #setInF
   */
  public static final Property inF = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inF} property.
   * @see #inF
   */
  public BStatusNumeric getInF() { return (BStatusNumeric)get(inF); }

  /**
   * Set the {@code inF} property.
   * @see #inF
   */
  public void setInF(BStatusNumeric v) { set(inF, v, null); }

  //endregion Property "inF"

  //region Property "inG"

  /**
   * Slot for the {@code inG} property.
   * @see #getInG
   * @see #setInG
   */
  public static final Property inG = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inG} property.
   * @see #inG
   */
  public BStatusNumeric getInG() { return (BStatusNumeric)get(inG); }

  /**
   * Set the {@code inG} property.
   * @see #inG
   */
  public void setInG(BStatusNumeric v) { set(inG, v, null); }

  //endregion Property "inG"

  //region Property "inH"

  /**
   * Slot for the {@code inH} property.
   * @see #getInH
   * @see #setInH
   */
  public static final Property inH = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inH} property.
   * @see #inH
   */
  public BStatusNumeric getInH() { return (BStatusNumeric)get(inH); }

  /**
   * Set the {@code inH} property.
   * @see #inH
   */
  public void setInH(BStatusNumeric v) { set(inH, v, null); }

  //endregion Property "inH"

  //region Property "inI"

  /**
   * Slot for the {@code inI} property.
   * @see #getInI
   * @see #setInI
   */
  public static final Property inI = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inI} property.
   * @see #inI
   */
  public BStatusNumeric getInI() { return (BStatusNumeric)get(inI); }

  /**
   * Set the {@code inI} property.
   * @see #inI
   */
  public void setInI(BStatusNumeric v) { set(inI, v, null); }

  //endregion Property "inI"

  //region Property "inJ"

  /**
   * Slot for the {@code inJ} property.
   * @see #getInJ
   * @see #setInJ
   */
  public static final Property inJ = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inJ} property.
   * @see #inJ
   */
  public BStatusNumeric getInJ() { return (BStatusNumeric)get(inJ); }

  /**
   * Set the {@code inJ} property.
   * @see #inJ
   */
  public void setInJ(BStatusNumeric v) { set(inJ, v, null); }

  //endregion Property "inJ"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericSelect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Interfaces
////////////////////////////////////////////////////////////////

  /**
   * Get the control output value.
   */
  public BStatusValue getNullInstance()
  {
    return new BStatusNumeric();
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
    setOut((BStatusNumeric)value );
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
// BINumeric interface
////////////////////////////////////////////////////////////////

  public double getNumeric() { return getOut().getValue(); }

  public final BFacets getNumericFacets() { return getOut().getStatus().getFacets(); }


  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");


}
