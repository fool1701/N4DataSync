/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.logic;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BQuad is the superclass of all Baja logic objects that
 * operate on up to four input values. 
 *
 * @author    Dan Giorgis
 * @creation  8 Nov 00
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:18 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Input A
 */
@NiagaraProperty(
  name = "inA",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.SUMMARY
)
/*
 Input B
 */
@NiagaraProperty(
  name = "inB",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.SUMMARY
)
/*
 Input C
 */
@NiagaraProperty(
  name = "inC",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)"
)
/*
 Input D
 */
@NiagaraProperty(
  name = "inD",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)"
)
public abstract class BQuadLogic
  extends BLogic
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BQuadLogic(2247175529)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inA"

  /**
   * Slot for the {@code inA} property.
   * Input A
   * @see #getInA
   * @see #setInA
   */
  public static final Property inA = newProperty(Flags.SUMMARY, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code inA} property.
   * Input A
   * @see #inA
   */
  public BStatusBoolean getInA() { return (BStatusBoolean)get(inA); }

  /**
   * Set the {@code inA} property.
   * Input A
   * @see #inA
   */
  public void setInA(BStatusBoolean v) { set(inA, v, null); }

  //endregion Property "inA"

  //region Property "inB"

  /**
   * Slot for the {@code inB} property.
   * Input B
   * @see #getInB
   * @see #setInB
   */
  public static final Property inB = newProperty(Flags.SUMMARY, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code inB} property.
   * Input B
   * @see #inB
   */
  public BStatusBoolean getInB() { return (BStatusBoolean)get(inB); }

  /**
   * Set the {@code inB} property.
   * Input B
   * @see #inB
   */
  public void setInB(BStatusBoolean v) { set(inB, v, null); }

  //endregion Property "inB"

  //region Property "inC"

  /**
   * Slot for the {@code inC} property.
   * Input C
   * @see #getInC
   * @see #setInC
   */
  public static final Property inC = newProperty(0, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code inC} property.
   * Input C
   * @see #inC
   */
  public BStatusBoolean getInC() { return (BStatusBoolean)get(inC); }

  /**
   * Set the {@code inC} property.
   * Input C
   * @see #inC
   */
  public void setInC(BStatusBoolean v) { set(inC, v, null); }

  //endregion Property "inC"

  //region Property "inD"

  /**
   * Slot for the {@code inD} property.
   * Input D
   * @see #getInD
   * @see #setInD
   */
  public static final Property inD = newProperty(0, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code inD} property.
   * Input D
   * @see #inD
   */
  public BStatusBoolean getInD() { return (BStatusBoolean)get(inD); }

  /**
   * Set the {@code inD} property.
   * Input D
   * @see #inD
   */
  public void setInD(BStatusBoolean v) { set(inD, v, null); }

  //endregion Property "inD"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BQuadLogic.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusBoolean inA = getInA();
    BStatusBoolean inB = getInB();
    BStatusBoolean inC = getInC();
    BStatusBoolean inD = getInD();

    BStatus aStatus = inA.getStatus();
    BStatus bStatus = inB.getStatus();
    BStatus cStatus = inC.getStatus();
    BStatus dStatus = inD.getStatus();

    //  Check for less than the minimum number
    //  of non-null inputs.  If null bit 
    //  is set, ignore 
    int nonNullCount = 0;
    boolean forceNull = false;

    int a = 0; int b = 0; int c = 0; int d = 0;

    if (!aStatus.isNull())
      { a = aStatus.getBits(); nonNullCount++; }

    if (!bStatus.isNull())
      { b = bStatus.getBits(); nonNullCount++; }

    if (!cStatus.isNull())
      { c = cStatus.getBits(); nonNullCount++; }

    if (!dStatus.isNull())
      { d = dStatus.getBits(); nonNullCount++; }

    if (nonNullCount < minInputs())
      forceNull = true; 

    BStatusBoolean out = (BStatusBoolean)o;
    if (forceNull)
    {
      out.setValue(false);
      out.setStatus(BStatus.nullStatus);
    }
    else
    {
      out.setValue(calculate(inA,inB,inC,inD));   
      out.setStatus(propagate(BStatus.make(a | b | c | d)));        
      if(getNullOnInactive() && !(out.getValue()) )
        out.setStatusNull(true);
    }
  }


  /**
   *  Calculation method.  Only valid elements should be used
   * in the calculation.  If there are not enough valid elements,
   * false should be returned
   */
  abstract protected boolean calculate(BStatusBoolean a, 
                                       BStatusBoolean b, 
                                       BStatusBoolean c, 
                                       BStatusBoolean d);

  
  /**
   * Return the minimum number of valid inputs needed to
   * generate a valid output
   */
  abstract public int minInputs();

}
