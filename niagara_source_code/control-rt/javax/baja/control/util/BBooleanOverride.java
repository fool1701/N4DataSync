/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBooleanOverride is the override argument for BooleanWritable.
 *
 * @author    Brian Frank
 * @creation  21 Jun 04
 * @version   $Revision: 1$ $Date: 6/22/04 8:35:23 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Value of the override.
 */
@NiagaraProperty(
  name = "value",
  type = "boolean",
  defaultValue = "false"
)
public class BBooleanOverride
  extends BOverride
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.util.BBooleanOverride(2977627359)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * Value of the override.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, false, null);

  /**
   * Get the {@code value} property.
   * Value of the override.
   * @see #value
   */
  public boolean getValue() { return getBoolean(value); }

  /**
   * Set the {@code value} property.
   * Value of the override.
   * @see #value
   */
  public void setValue(boolean v) { setBoolean(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanOverride.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BBooleanOverride(BRelTime duration, boolean value)
  {                                          
    setDuration(duration);
    setValue(value);
  }

  public BBooleanOverride(boolean value)
  {                                   
    setValue(value);
  }

  public BBooleanOverride()
  {
  }

}
