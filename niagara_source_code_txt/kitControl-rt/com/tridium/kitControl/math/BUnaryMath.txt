/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BUnaryMath is the superclass of all Baja math objects take
 * operate on a single input value. 
 *
 * If the input has null status, the output will be Double.NaN
 * and null status.  If the input is invalid but not null, the 
 * output will Double.NaN and have the same status as the input.
 *
 * @author    Dan Giorgis
 * @creation  8 Nov 00
 * @version   $Revision: 27$ $Date: 3/30/2004 3:42:52 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Input A
 */
@NiagaraProperty(
  name = "inA",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)",
  flags = Flags.SUMMARY
)
public abstract class BUnaryMath
  extends BMath
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BUnaryMath(4074619843)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inA"

  /**
   * Slot for the {@code inA} property.
   * Input A
   * @see #getInA
   * @see #setInA
   */
  public static final Property inA = newProperty(Flags.SUMMARY, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code inA} property.
   * Input A
   * @see #inA
   */
  public BStatusNumeric getInA() { return (BStatusNumeric)get(inA); }

  /**
   * Set the {@code inA} property.
   * Input A
   * @see #inA
   */
  public void setInA(BStatusNumeric v) { set(inA, v, null); }

  //endregion Property "inA"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUnaryMath.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusNumeric inA = getInA();
    BStatus sa = inA.getStatus();                     
    BStatusNumeric out = (BStatusNumeric)o;

    //  Update output
    if (sa.isNull())
    {
      out.setValue(Double.NaN);
      out.setStatus(BStatus.nullStatus);
    }    
    else
    {
      out.setValue(calculate(inA.getValue()));   
      out.setStatus(propagate(sa));
    }
  }  

  abstract protected double calculate(double a);
}
