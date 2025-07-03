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
 * BStringTest is a component that converts a float to a statusNumeric.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
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
  name = "testSelect",
  type = "BDynamicEnum",
  defaultValue = "BDynamicEnum.make(0, testRange)"
)
public class BStringTest
  extends BComponent
{
  private static String[] testTags = {"aEqualsB", "aEqualsBIgnoreCase", "aStartsWithB", "aEndsWithB", "aContainsB"};
  private static BEnumRange testRange  = BEnumRange.make(testTags);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BStringTest(4244317292)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusBoolean getOut() { return (BStatusBoolean)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusBoolean v) { set(out, v, null); }

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

  //region Property "testSelect"

  /**
   * Slot for the {@code testSelect} property.
   * @see #getTestSelect
   * @see #setTestSelect
   */
  public static final Property testSelect = newProperty(0, BDynamicEnum.make(0, testRange), null);

  /**
   * Get the {@code testSelect} property.
   * @see #testSelect
   */
  public BDynamicEnum getTestSelect() { return (BDynamicEnum)get(testSelect); }

  /**
   * Set the {@code testSelect} property.
   * @see #testSelect
   */
  public void setTestSelect(BDynamicEnum v) { set(testSelect, v, null); }

  //endregion Property "testSelect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStringTest()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    getOut().setValue( calculate() );
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == inA || p == inB || p == testSelect )
    {
      getOut().setValue( calculate() );
    }
  }

  public boolean calculate()
  {
    if(!getInA().getStatus().isValid() || !getInB().getStatus().isValid())
      return getOut().getValue();
    String inAv = getInA().getValue().trim();
    String inBv = getInB().getValue();
    switch(getTestSelect().getOrdinal())
    {
    case 0:
      return inAv.equals(inBv);
    case 1:
      return inAv.equalsIgnoreCase(inBv);
    case 2:
      return inAv.startsWith(inBv);
    case 3:
      return inAv.endsWith(inBv);
    case 4:
      return inAv.indexOf(inBv) >= 0;
    }
    return false;
  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }


  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  
}
