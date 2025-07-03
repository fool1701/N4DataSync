/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BStringConcat is a component that converts a float to a statusNumeric.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "inA",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inB",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inC",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inD",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
public class BStringConcat
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BStringConcat(811106875)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusString getOut() { return (BStatusString)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusString v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "inA"

  /**
   * Slot for the {@code inA} property.
   * @see #getInA
   * @see #setInA
   */
  public static final Property inA = newProperty(Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code inA} property.
   * @see #inA
   */
  public BStatusString getInA() { return (BStatusString)get(inA); }

  /**
   * Set the {@code inA} property.
   * @see #inA
   */
  public void setInA(BStatusString v) { set(inA, v, null); }

  //endregion Property "inA"

  //region Property "inB"

  /**
   * Slot for the {@code inB} property.
   * @see #getInB
   * @see #setInB
   */
  public static final Property inB = newProperty(Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code inB} property.
   * @see #inB
   */
  public BStatusString getInB() { return (BStatusString)get(inB); }

  /**
   * Set the {@code inB} property.
   * @see #inB
   */
  public void setInB(BStatusString v) { set(inB, v, null); }

  //endregion Property "inB"

  //region Property "inC"

  /**
   * Slot for the {@code inC} property.
   * @see #getInC
   * @see #setInC
   */
  public static final Property inC = newProperty(Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code inC} property.
   * @see #inC
   */
  public BStatusString getInC() { return (BStatusString)get(inC); }

  /**
   * Set the {@code inC} property.
   * @see #inC
   */
  public void setInC(BStatusString v) { set(inC, v, null); }

  //endregion Property "inC"

  //region Property "inD"

  /**
   * Slot for the {@code inD} property.
   * @see #getInD
   * @see #setInD
   */
  public static final Property inD = newProperty(Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code inD} property.
   * @see #inD
   */
  public BStatusString getInD() { return (BStatusString)get(inD); }

  /**
   * Set the {@code inD} property.
   * @see #inD
   */
  public void setInD(BStatusString v) { set(inD, v, null); }

  //endregion Property "inD"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringConcat.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStringConcat()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    calculate();
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == inA || p == inB || p == inC || p == inD)
    {
      calculate();
    }
  }

  void calculate()
  {
    String working = "";
    if(getInA().getStatus().isValid())
      working = getInA().getValue();
    if(getInB().getStatus().isValid())
      working = working + getInB().getValue();
    if(getInC().getStatus().isValid())
      working = working + getInC().getValue();
    if(getInD().getStatus().isValid())
      working = working + getInD().getValue();

    getOut().setValue( working );
  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }
  

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  
}
