/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BStatusString defines a tuple representing 
 * an String value and status flags.
 *
 * @author    Brian Frank
 * @creation  30 Mar 01
 * @version   $Revision: 24$ $Date: 3/28/05 9:23:06 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The string value.
 */
@NiagaraProperty(
  name = "value",
  type = "String",
  defaultValue = ""
)
public class BStatusString
  extends BStatusValue
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.status.BStatusString(432317296)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The string value.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, "", null);

  /**
   * Get the {@code value} property.
   * The string value.
   * @see #value
   */
  public String getValue() { return getString(value); }

  /**
   * Set the {@code value} property.
   * The string value.
   * @see #value
   */
  public void setValue(String v) { setString(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusString.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with value and status.
   */
  public BStatusString(String value, BStatus status) 
  { 
    super(status);
    setValue(value);
  }

  /**
   * Constructor with value.
   */
  public BStatusString(String value) 
  { 
    setValue(value); 
  }

  /**
   * No argument constructor.
   */
  public BStatusString() 
  {
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  

  /**
   * Get the value as a BValue.
   */
  public final BValue getValueValue()
  {
    return BString.make(getValue());
  }

  /**
   * Get the "value" property.
   */
  public final Property getValueProperty()
  {
    return value;
  }

  /**
   * Return the value property.
   */
  public String valueToString(Context context)
  {
    return getValue();
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("statusString.png");

}
