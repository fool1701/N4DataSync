/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BStatusNumeric defines a tuple representing a numeric value 
 * with a 64-bit double.
 *
 * @author    Dan Giorgis
 * @creation  15 Nov 00
 * @version   $Revision: 39$ $Date: 3/28/05 9:23:05 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The value of a BStatusNumeric is a 64-bit primitive double.
 */
@NiagaraProperty(
  name = "value",
  type = "double",
  defaultValue = "0.0"
)
public class BStatusNumeric
  extends BStatusValue
  implements BINumeric
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.status.BStatusNumeric(64966405)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The value of a BStatusNumeric is a 64-bit primitive double.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, 0.0, null);

  /**
   * Get the {@code value} property.
   * The value of a BStatusNumeric is a 64-bit primitive double.
   * @see #value
   */
  public double getValue() { return getDouble(value); }

  /**
   * Set the {@code value} property.
   * The value of a BStatusNumeric is a 64-bit primitive double.
   * @see #value
   */
  public void setValue(double v) { setDouble(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusNumeric.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with value and status.
   */
  public BStatusNumeric(double value, BStatus status) 
  { 
    super(status);
    setValue(value);
  }

  /**
   * Constructor with value.
   */
  public BStatusNumeric(double value) 
  { 
    setValue(value); 
  }

  /**
   * No argument constructor.
   */
  public BStatusNumeric() 
  {
  }

////////////////////////////////////////////////////////////////
// BINumeric
////////////////////////////////////////////////////////////////

  /**
   * Return <code>getValue()</code>.
   */
  public final double getNumeric()
  {
    return getValue();
  }

  /**
   * Return <code>BFacets.NULL</code>.
   */
  public final BFacets getNumericFacets()
  {
    return BFacets.NULL;
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  

  /**
   * Get the value as a BValue.
   */
  public final BValue getValueValue()
  {
    return BDouble.make(getValue());
  }

  /**
   * Get the "value" property.
   */
  public final Property getValueProperty()
  {
    return value;
  }
  
  /**
   * Format the double value using:
   * <code>BDouble.toString(getValue(), Context)</code>.
   */
  public String valueToString(Context context)
  {
    return BDouble.toString(getValue(), context);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("statusNumeric.png");
  
}
