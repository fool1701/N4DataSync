/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BStatusEnum is the normalized class for enumerated values.
 *
 * @author    Brian Frank
 * @creation  13 Dec 01
 * @version   $Revision: 23$ $Date: 10/19/10 4:52:14 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The value of a BStatusEnum is stored in an BDynamicEnum.
 */
@NiagaraProperty(
  name = "value",
  type = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT"
)
public class BStatusEnum
  extends BStatusValue
  implements BIEnum
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.status.BStatusEnum(1402322450)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The value of a BStatusEnum is stored in an BDynamicEnum.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, BDynamicEnum.DEFAULT, null);

  /**
   * Get the {@code value} property.
   * The value of a BStatusEnum is stored in an BDynamicEnum.
   * @see #value
   */
  public BDynamicEnum getValue() { return (BDynamicEnum)get(value); }

  /**
   * Set the {@code value} property.
   * The value of a BStatusEnum is stored in an BDynamicEnum.
   * @see #value
   */
  public void setValue(BDynamicEnum v) { set(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with value and status.
   */
  public BStatusEnum(BEnum value, BStatus status) 
  { 
    super(status);
    setValue(value);
  }

  /**
   * Constructor with value.
   */
  public BStatusEnum(BEnum value) 
  { 
    setValue(value); 
  }

  /**
   * No argument constructor.
   */
  public BStatusEnum() 
  { 
  }

////////////////////////////////////////////////////////////////
// BIEnum
////////////////////////////////////////////////////////////////

  /**
   * Return <code>getValue()</code>.
   */
  public BEnum getEnum()
  {
    return getValue();
  }

  public final BFacets getEnumFacets()
  {
    return BFacets.make(BFacets.RANGE, getEnum().getRange());
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  
  
  /**
   * Set the value property using any enum.
   */
  public void setValue(BEnum x)
  {                                        
    setValue(BDynamicEnum.make(x));      
  }  

  /**
   * Get the value as a BValue.
   */
  public final BValue getValueValue()
  {
    return getValue();
  }

  /**
   * Get the "value" property.
   */
  public final Property getValueProperty()
  {
    return value;
  }

  /**
   * Format the value using <code>getValue().toString(context)</code>.
   */
  public String valueToString(Context context)
  {
    return getValue().toString(context);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("statusEnum.png");
      
}
