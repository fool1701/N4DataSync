/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BStatusBoolean represents a two state binary value.
 *
 * @author    Dan Giorgis
 * @creation  15 Nov 00
 * @version   $Revision: 41$ $Date: 3/28/05 9:23:05 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The value of a BStatusBoolean is a
 simple boolean.
 */
@NiagaraProperty(
  name = "value",
  type = "boolean",
  defaultValue = "false"
)
public class BStatusBoolean
  extends BStatusValue
  implements BIBoolean
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.status.BStatusBoolean(2763676039)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The value of a BStatusBoolean is a
   * simple boolean.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, false, null);

  /**
   * Get the {@code value} property.
   * The value of a BStatusBoolean is a
   * simple boolean.
   * @see #value
   */
  public boolean getValue() { return getBoolean(value); }

  /**
   * Set the {@code value} property.
   * The value of a BStatusBoolean is a
   * simple boolean.
   * @see #value
   */
  public void setValue(boolean v) { setBoolean(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusBoolean.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with value and status.
   */
  public BStatusBoolean(boolean value, BStatus status) 
  { 
    super(status);
    setValue(value);
  }

  /**
   * Constructor with value.
   */
  public BStatusBoolean(boolean value) 
  { 
    setValue(value); 
  }

  /**
   * No argument constructor.
   */
  public BStatusBoolean() 
  { 
  }

////////////////////////////////////////////////////////////////
// BIBoolean
////////////////////////////////////////////////////////////////

  /**
   * Return <code>getValue()</code>.
   */
  public final boolean getBoolean()
  {
    return getValue();
  }

  /**
   * Return <code>BFacets.NULL</code>.
   */
  public final BFacets getBooleanFacets()
  {
    return BFacets.NULL;
  }

////////////////////////////////////////////////////////////////
// BIEnum
////////////////////////////////////////////////////////////////

  /**
   * Return an instance of BBoolean.
   */
  public final BEnum getEnum()
  {
    return BBoolean.make(getValue());
  }

  /**
   * Return enum facets for BBoolean.
   */
  public final BFacets getEnumFacets()
  {
    return BBoolean.make(getValue()).getEnumFacets();
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  

  /**
   * Get the value as a BValue.
   */
  public final BValue getValueValue()
  {
    return BBoolean.make(getValue());
  }

  /**
   * Get the "value" property.
   */
  public final Property getValueProperty()
  {
    return value;
  }
  
  /**
   * Format the boolean value using:
   * <code>BBoolean.toString(boolean, Context)</code>.
   */
  public String valueToString(Context context)
  {
    return BBoolean.toString(getValue(), context);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("statusBoolean.png");
      
}
