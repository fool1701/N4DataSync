/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.conversion;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

import com.tridium.kitControl.enums.BNullValueOverrideSelect;

/**
 * BStatusNumericToDouble is a component that converts a float to a statusNumeric.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.UNITS, BUnit.NULL, BFacets.PRECISION, BInteger.make(1) )"
)
@NiagaraProperty(
  name = "out",
  type = "double",
  defaultValue = "0.0d",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "onNullInValue",
  type = "BNullValueOverrideSelect",
  defaultValue = "BNullValueOverrideSelect.useInValue"
)
public class BStatusNumericToDouble
  extends BStatusValueToValue
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.conversion.BStatusNumericToDouble(2010256819)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.make(BFacets.UNITS, BUnit.NULL, BFacets.PRECISION, BInteger.make(1) ), null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, 0.0d, null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public double getOut() { return getDouble(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(double v) { setDouble(out, v, null); }

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

  //region Property "onNullInValue"

  /**
   * Slot for the {@code onNullInValue} property.
   * @see #getOnNullInValue
   * @see #setOnNullInValue
   */
  public static final Property onNullInValue = newProperty(0, BNullValueOverrideSelect.useInValue, null);

  /**
   * Get the {@code onNullInValue} property.
   * @see #onNullInValue
   */
  public BNullValueOverrideSelect getOnNullInValue() { return (BNullValueOverrideSelect)get(onNullInValue); }

  /**
   * Set the {@code onNullInValue} property.
   * @see #onNullInValue
   */
  public void setOnNullInValue(BNullValueOverrideSelect v) { set(onNullInValue, v, null); }

  //endregion Property "onNullInValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusNumericToDouble.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStatusNumericToDouble()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    execute();
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if( p.equals(in) )
    {
      execute();
    }
    else super.changed(p, cx);
  }

  public void execute()
  {
    setOut( ((BDouble)calculate(getIn())).getDouble());
  }

  public Type getOutType()
  {
    return BDouble.TYPE;
  }
  

  public String toString(Context cx)
  {
    return BDouble.make(getOut()).toString(cx);
  }

  /**
   * Apply the "facets" property to the "out" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == out) return getFacets();
    return super.getSlotFacets(slot);
  }

  public BFacets getOutFacets()
  {
    return getFacets();
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
}
