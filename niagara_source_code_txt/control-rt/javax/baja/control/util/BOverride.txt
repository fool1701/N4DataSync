/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BStruct;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BOverride is the argument used on the writable point override actions.
 *
 * @author    Brian Frank
 * @creation  21 Jun 04
 * @version   $Revision: 4$ $Date: 7/27/10 12:19:15 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Duration of time for the override or 0 for no expiration.
 */
@NiagaraProperty(
  name = "duration",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
/*
 Max override duration. Please note, this is a FW only
 Property and replaces the previous maxOverrideDuration field
 */
@NiagaraProperty(
  name = "maxOverrideDuration",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  flags = Flags.HIDDEN | Flags.TRANSIENT
)
public class BOverride
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.util.BOverride(4124583936)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "duration"

  /**
   * Slot for the {@code duration} property.
   * Duration of time for the override or 0 for no expiration.
   * @see #getDuration
   * @see #setDuration
   */
  public static final Property duration = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code duration} property.
   * Duration of time for the override or 0 for no expiration.
   * @see #duration
   */
  public BRelTime getDuration() { return (BRelTime)get(duration); }

  /**
   * Set the {@code duration} property.
   * Duration of time for the override or 0 for no expiration.
   * @see #duration
   */
  public void setDuration(BRelTime v) { set(duration, v, null); }

  //endregion Property "duration"

  //region Property "maxOverrideDuration"

  /**
   * Slot for the {@code maxOverrideDuration} property.
   * Max override duration. Please note, this is a FW only
   * Property and replaces the previous maxOverrideDuration field
   * @see #getMaxOverrideDuration
   * @see #setMaxOverrideDuration
   */
  public static final Property maxOverrideDuration = newProperty(Flags.HIDDEN | Flags.TRANSIENT, BRelTime.DEFAULT, null);

  /**
   * Get the {@code maxOverrideDuration} property.
   * Max override duration. Please note, this is a FW only
   * Property and replaces the previous maxOverrideDuration field
   * @see #maxOverrideDuration
   */
  public BRelTime getMaxOverrideDuration() { return (BRelTime)get(maxOverrideDuration); }

  /**
   * Set the {@code maxOverrideDuration} property.
   * Max override duration. Please note, this is a FW only
   * Property and replaces the previous maxOverrideDuration field
   * @see #maxOverrideDuration
   */
  public void setMaxOverrideDuration(BRelTime v) { set(maxOverrideDuration, v, null); }

  //endregion Property "maxOverrideDuration"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOverride.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {            
    // get value (or null if no associated value)
    BValue val = get("value");
    String valStr = null;
    if (val != null)
      valStr = val.toString(cx);
    
    // get duration string
    BRelTime dur = getDuration();      
    String durStr;                 
    if (dur.getMillis() == 0)
      durStr = "permanent";
    else
      durStr = dur.toString(cx);
    
    if (valStr == null)
      return durStr;  
    else
      return valStr + " : " + durStr;
  }         
  
  String valueToString(Context cx)
  {      
    return null;
  } 
}
