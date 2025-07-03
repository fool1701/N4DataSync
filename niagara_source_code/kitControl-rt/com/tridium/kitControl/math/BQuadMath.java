/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BQuadMath is the superclass of all Baja math objects that
 * operate on up to four input values.
 *
 * @author    Dan Giorgis
 * @creation  8 Nov 00
 * @version   $Revision: 28$ $Date: 3/30/2004 3:42:25 PM$
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
/*
 Input C
 */
@NiagaraProperty(
  name = "inC",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)"
)
/*
 Input D
 */
@NiagaraProperty(
  name = "inD",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)"
)
public abstract class BQuadMath
  extends BMath
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BQuadMath(3006250524)1.0$ @*/
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

  //region Property "inC"

  /**
   * Slot for the {@code inC} property.
   * Input C
   * @see #getInC
   * @see #setInC
   */
  public static final Property inC = newProperty(0, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code inC} property.
   * Input C
   * @see #inC
   */
  public BStatusNumeric getInC() { return (BStatusNumeric)get(inC); }

  /**
   * Set the {@code inC} property.
   * Input C
   * @see #inC
   */
  public void setInC(BStatusNumeric v) { set(inC, v, null); }

  //endregion Property "inC"

  //region Property "inD"

  /**
   * Slot for the {@code inD} property.
   * Input D
   * @see #getInD
   * @see #setInD
   */
  public static final Property inD = newProperty(0, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code inD} property.
   * Input D
   * @see #inD
   */
  public BStatusNumeric getInD() { return (BStatusNumeric)get(inD); }

  /**
   * Set the {@code inD} property.
   * Input D
   * @see #inD
   */
  public void setInD(BStatusNumeric v) { set(inD, v, null); }

  //endregion Property "inD"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BQuadMath.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusNumeric inA = getInA();
    BStatusNumeric inB = getInB();
    BStatusNumeric inC = getInC();
    BStatusNumeric inD = getInD();

    BStatus aStatus = inA.getStatus();
    BStatus bStatus = inB.getStatus();
    BStatus cStatus = inC.getStatus();
    BStatus dStatus = inD.getStatus();

    int a = 0; int b = 0; int c = 0; int d = 0;
    a = aStatus.getBits();
    b = bStatus.getBits();
    c = cStatus.getBits();
    d = dStatus.getBits();

    //  Check for less than the minimum number
    //  of non-null inputs.  If null bit
    //  is set, ignore
    int nonNullCount = 0;
    boolean forceNull = false;


    if (aStatus.isValid())
      {  nonNullCount++; }

    if (bStatus.isValid())
      {  nonNullCount++; }

    if (cStatus.isValid())
      {  nonNullCount++; }

    if (dStatus.isValid())
      {  nonNullCount++; }

    if (nonNullCount < minInputs())
      forceNull = true;

    BStatusNumeric out = (BStatusNumeric)o;
    if (forceNull)
    {
      out.setValue(Double.NaN);
      out.setStatus(BStatus.nullStatus);
    }
    else
    {
      out.setValue(calculate(inA,inB,inC,inD));
      out.setStatus(propagate(BStatus.make(a | b | c | d)));
    }
  }


  /**
   * Calculation method.  Only valid elements should be used
   * in the calculation.  If there are not enough valid elements,
   * Double.NaN should be returned
   */
  abstract protected double calculate(BStatusNumeric a,
                                      BStatusNumeric b,
                                      BStatusNumeric c,
                                      BStatusNumeric d);


  /**
   * Return the minimum number of valid inputs needed to
   * generate a valid output
   */
  abstract public int minInputs();

}
