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
  name = "fromIndex",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "beginIndex",
  type = "int",
  defaultValue = "-1",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "afterIndex",
  type = "int",
  defaultValue = "-1",
  flags = Flags.TRANSIENT
)
public class BStringIndexOf
  extends BComponent
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BStringIndexOf(1001797330)1.0$ @*/
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

  //region Property "fromIndex"

  /**
   * Slot for the {@code fromIndex} property.
   * @see #getFromIndex
   * @see #setFromIndex
   */
  public static final Property fromIndex = newProperty(Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code fromIndex} property.
   * @see #fromIndex
   */
  public int getFromIndex() { return getInt(fromIndex); }

  /**
   * Set the {@code fromIndex} property.
   * @see #fromIndex
   */
  public void setFromIndex(int v) { setInt(fromIndex, v, null); }

  //endregion Property "fromIndex"

  //region Property "beginIndex"

  /**
   * Slot for the {@code beginIndex} property.
   * @see #getBeginIndex
   * @see #setBeginIndex
   */
  public static final Property beginIndex = newProperty(Flags.TRANSIENT, -1, null);

  /**
   * Get the {@code beginIndex} property.
   * @see #beginIndex
   */
  public int getBeginIndex() { return getInt(beginIndex); }

  /**
   * Set the {@code beginIndex} property.
   * @see #beginIndex
   */
  public void setBeginIndex(int v) { setInt(beginIndex, v, null); }

  //endregion Property "beginIndex"

  //region Property "afterIndex"

  /**
   * Slot for the {@code afterIndex} property.
   * @see #getAfterIndex
   * @see #setAfterIndex
   */
  public static final Property afterIndex = newProperty(Flags.TRANSIENT, -1, null);

  /**
   * Get the {@code afterIndex} property.
   * @see #afterIndex
   */
  public int getAfterIndex() { return getInt(afterIndex); }

  /**
   * Set the {@code afterIndex} property.
   * @see #afterIndex
   */
  public void setAfterIndex(int v) { setInt(afterIndex, v, null); }

  //endregion Property "afterIndex"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringIndexOf.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStringIndexOf()
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

    if (p == inA || p == inB || p == fromIndex)
    {
      getOut().setValue( calculate() );
    }
  }

  public boolean calculate()
  {
    if(!getInA().getStatus().isValid() || !getInB().getStatus().isValid())
      return getOut().getValue();
    String inA = getInA().getValue();
    String inB = getInB().getValue();

    int index = inA.indexOf(inB, getFromIndex());
    setBeginIndex(index);
    if(index < 0)
      setAfterIndex(index);
    else
      setAfterIndex(index + inB.length());
    return index >=0;
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
