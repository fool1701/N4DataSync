/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BMarker;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BSimpleTagInfo is a {@code BComponent} implementation of {@code TagInfo} that
 * contains a default value.
 *
 * @author John Sublett
 * @creation 2/13/14
 * @since Niagara 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "defValue",
  type = "BValue",
  defaultValue = "BMarker.MARKER"
)
public class BSimpleTagInfo
  extends BTagInfo
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BSimpleTagInfo(4238238071)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defValue"

  /**
   * Slot for the {@code defValue} property.
   * @see #getDefValue
   * @see #setDefValue
   */
  public static final Property defValue = newProperty(0, BMarker.MARKER, null);

  /**
   * Get the {@code defValue} property.
   * @see #defValue
   */
  public BValue getDefValue() { return get(defValue); }

  /**
   * Set the {@code defValue} property.
   * @see #defValue
   */
  public void setDefValue(BValue v) { set(defValue, v, null); }

  //endregion Property "defValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSimpleTagInfo.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor
   */
  public BSimpleTagInfo()
  {
  }

  /**
   * Constructor that initializes the {@link #defValue} property.
   *
   * @param defaultValue initial default value
   */
  public BSimpleTagInfo(BIDataValue defaultValue)
  {
    setDefValue((BValue)defaultValue);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Set the {@link #defValue} property value.
   *
   * @param value new default value
   */
  public void setDefaultValue(BIDataValue value)
  {
    setDefValue((BValue)value);
  }

////////////////////////////////////////////////////////////////
// TagInfo
////////////////////////////////////////////////////////////////

  /**
   * Get the default value of the tag.  This also restricts the data type for the tag value.
   * See {@link #getTagType()}.
   *
   * @return default value of the tag
   */
  @Override
  public BIDataValue getDefaultValue()
  {
    BValue def = getDefValue();
    if (def instanceof BIDataValue)
      return (BIDataValue)def;
    else
      throw new IllegalStateException("defValue is not a valid data value.");
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Returns the type name of the default value.
   *
   * @param cx execution context
   * @return {@code String} representation of the type of the default value
   */
  @Override
  public String toString(Context cx)
  {
    return getDefaultValue().getType().getTypeName();
  }
}
