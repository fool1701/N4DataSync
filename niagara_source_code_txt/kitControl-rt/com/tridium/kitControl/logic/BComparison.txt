/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.logic;

import java.io.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;


/**
 * BComparison is an abstract superclass for mathematical 
 * operations such as A > B, A >= B, etc.
 *
 * @author    Dan Giorgis
 * @creation  3 Nov 00
 * @version   $Revision: 29$ $Date: 3/30/2004 3:40:36 PM$
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
/*
 Input B
 */
@NiagaraProperty(
  name = "inB",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)",
  flags = Flags.SUMMARY
)
public abstract class BComparison
  extends BLogic
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BComparison(4225619954)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "inB"

  /**
   * Slot for the {@code inB} property.
   * Input B
   * @see #getInB
   * @see #setInB
   */
  public static final Property inB = newProperty(Flags.SUMMARY, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code inB} property.
   * Input B
   * @see #inB
   */
  public BStatusNumeric getInB() { return (BStatusNumeric)get(inB); }

  /**
   * Set the {@code inB} property.
   * Input B
   * @see #inB
   */
  public void setInB(BStatusNumeric v) { set(inB, v, null); }

  //endregion Property "inB"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComparison.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusNumeric inA = getInA();
    BStatusNumeric inB = getInB();
    BStatusBoolean out = (BStatusBoolean)o;

    BStatus sa = inA.getStatus();
    BStatus sb = inB.getStatus();

    //  If either input is null, force the output
    //  to null
    if (sa.isNull() || sb.isNull())
    { 
      out.setValue(false);
      out.setStatus(BStatus.nullStatus);            
    }
    else
    {
      out.setStatus(propagate(BStatus.make(sa.getBits() | sb.getBits())));
  
      //  If either input is invalid, force the output
      //  to false
      if (!sa.isValid() || !sb.isValid())        
        out.setValue(false);
      else
        out.setValue(calculate(inA.getValue(), inB.getValue()));         
      if(getNullOnInactive() && !(out.getValue()) )
        out.setStatusNull(true);
    }    
  }

  abstract protected boolean calculate(double valueA, 
                                       double valueB);

}
