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
 * BStringOverride is the override argument for StringWritable.
 *
 * @author    Brian Frank
 * @creation  21 Jun 04
 * @version   $Revision: 1$ $Date: 6/22/04 8:35:24 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Value of the override.
 */
@NiagaraProperty(
  name = "value",
  type = "String",
  defaultValue = ""
)
public class BStringOverride
  extends BOverride
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.util.BStringOverride(3554428164)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * Value of the override.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, "", null);

  /**
   * Get the {@code value} property.
   * Value of the override.
   * @see #value
   */
  public String getValue() { return getString(value); }

  /**
   * Set the {@code value} property.
   * Value of the override.
   * @see #value
   */
  public void setValue(String v) { setString(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringOverride.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStringOverride(BRelTime duration, String value)
  {                                         
    setDuration(duration);
    setValue(value);
  }
  
  public BStringOverride(String value)
  {                                   
    setValue(value);
  }

  public BStringOverride()
  {
  }

}
