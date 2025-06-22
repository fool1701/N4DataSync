/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDouble;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIncrementSetPointBinding is used to increment or decrement 
 * a numeric value when the widgetEvent is fired.
 *
 * @author    Brian Frank       
 * @creation  3 Nov 04
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:Button"
  )
)
/*
 Value to increment current value; or a negative
 number for a decrement.
 */
@NiagaraProperty(
  name = "increment",
  type = "double",
  defaultValue = "0"
)
@NiagaraProperty(
  name = "widgetProperty",
  type = "String",
  defaultValue = "",
  flags = Flags.HIDDEN,
  override = true
)
public class BIncrementSetPointBinding
  extends BSetPointBinding
{
         
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BIncrementSetPointBinding(1026545506)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "increment"

  /**
   * Slot for the {@code increment} property.
   * Value to increment current value; or a negative
   * number for a decrement.
   * @see #getIncrement
   * @see #setIncrement
   */
  public static final Property increment = newProperty(0, 0, null);

  /**
   * Get the {@code increment} property.
   * Value to increment current value; or a negative
   * number for a decrement.
   * @see #increment
   */
  public double getIncrement() { return getDouble(increment); }

  /**
   * Set the {@code increment} property.
   * Value to increment current value; or a negative
   * number for a decrement.
   * @see #increment
   */
  public void setIncrement(double v) { setDouble(increment, v, null); }

  //endregion Property "increment"

  //region Property "widgetProperty"

  /**
   * Slot for the {@code widgetProperty} property.
   * @see #getWidgetProperty
   * @see #setWidgetProperty
   */
  public static final Property widgetProperty = newProperty(Flags.HIDDEN, "", null);

  //endregion Property "widgetProperty"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIncrementSetPointBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// SetPointBinding
////////////////////////////////////////////////////////////////
  
  BValue saveWidgetProperty()
  {                      
    if (!isBound()) return null;
    BObject value = get();
    if (!(value instanceof BINumeric)) return null;
    double current = ((BINumeric)value).getNumeric();
    return BDouble.make(current + getIncrement());
  }
            

}
