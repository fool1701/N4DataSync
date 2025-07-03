/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.conversion;

import java.text.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BStatusNumericToStatusString is a component that converts a StatusNumeric to a StatusString.
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
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "integerDigits",
  type = "int",
  defaultValue = "6"
)
@NiagaraProperty(
  name = "decimalDigits",
  type = "int",
  defaultValue = "6"
)
public class BStatusNumericToStatusString
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.conversion.BStatusNumericToStatusString(655578930)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusString(), null);

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

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "integerDigits"

  /**
   * Slot for the {@code integerDigits} property.
   * @see #getIntegerDigits
   * @see #setIntegerDigits
   */
  public static final Property integerDigits = newProperty(0, 6, null);

  /**
   * Get the {@code integerDigits} property.
   * @see #integerDigits
   */
  public int getIntegerDigits() { return getInt(integerDigits); }

  /**
   * Set the {@code integerDigits} property.
   * @see #integerDigits
   */
  public void setIntegerDigits(int v) { setInt(integerDigits, v, null); }

  //endregion Property "integerDigits"

  //region Property "decimalDigits"

  /**
   * Slot for the {@code decimalDigits} property.
   * @see #getDecimalDigits
   * @see #setDecimalDigits
   */
  public static final Property decimalDigits = newProperty(0, 6, null);

  /**
   * Get the {@code decimalDigits} property.
   * @see #decimalDigits
   */
  public int getDecimalDigits() { return getInt(decimalDigits); }

  /**
   * Set the {@code decimalDigits} property.
   * @see #decimalDigits
   */
  public void setDecimalDigits(int v) { setInt(decimalDigits, v, null); }

  //endregion Property "decimalDigits"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusNumericToStatusString.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStatusNumericToStatusString()
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

    if (p == in || p == decimalDigits || p == integerDigits)
    {
      calculate();
    }
  }

  void calculate()
  {
    double inValue = getIn().getValue();
    workingValue.setStatus(getIn().getStatus());
    int precision = getDecimalDigits();
    int integer   = getIntegerDigits();
    NumberFormat format = NumberFormat.getNumberInstance();
    format.setMaximumFractionDigits(precision);
    format.setMinimumFractionDigits(precision);
    format.setMaximumIntegerDigits(integer);
    format.setMinimumIntegerDigits(integer);
    format.setGroupingUsed(false);
    String value = format.format(inValue);
    try
    {
      workingValue.setValue(value);
    }
    catch(Exception e)
    {
      workingValue.setStatusNull(true);
      workingValue.setStatusFault(true);
    }
    setOut(workingValue);
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
  BStatusString workingValue = new BStatusString();
  
}
