/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;

/**
 * BDynamicEnumTagInfo is a {@link BSimpleTagInfo} that contains an EnumRange property used for the
 * {@link BDynamicEnum} DefValue property. This {@link BEnumRange} will be included with direct and
 * implied {@link BDynamicEnum} tags.
 *
 * @author Sabana Bandopadhyay on 29-Sep-2021
 * @since Niagara 4.12
 */
@NiagaraType
/*
 When this {@link BEnumRange} property is changed, the {@link #defValue} value will be updated to
 include it.
 */
@NiagaraProperty(
  name = "enumRange",
  type = "BEnumRange",
  defaultValue = "BEnumRange.NULL"
)
@NiagaraProperty(
  name = "defValue",
  type = "BValue",
  defaultValue = "BDynamicEnum.DEFAULT",
  override = true
)
public class BDynamicEnumTagInfo extends BSimpleTagInfo
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BDynamicEnumTagInfo(1940702875)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enumRange"

  /**
   * Slot for the {@code enumRange} property.
   * When this {@link BEnumRange} property is changed, the {@link #defValue} value will be updated to
   * include it.
   * @see #getEnumRange
   * @see #setEnumRange
   */
  public static final Property enumRange = newProperty(0, BEnumRange.NULL, null);

  /**
   * Get the {@code enumRange} property.
   * When this {@link BEnumRange} property is changed, the {@link #defValue} value will be updated to
   * include it.
   * @see #enumRange
   */
  public BEnumRange getEnumRange() { return (BEnumRange)get(enumRange); }

  /**
   * Set the {@code enumRange} property.
   * When this {@link BEnumRange} property is changed, the {@link #defValue} value will be updated to
   * include it.
   * @see #enumRange
   */
  public void setEnumRange(BEnumRange v) { set(enumRange, v, null); }

  //endregion Property "enumRange"

  //region Property "defValue"

  /**
   * Slot for the {@code defValue} property.
   * @see #getDefValue
   * @see #setDefValue
   */
  public static final Property defValue = newProperty(0, BDynamicEnum.DEFAULT, null);

  //endregion Property "defValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDynamicEnumTagInfo.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor
   */
  public BDynamicEnumTagInfo()
  {
  }

  /**
   * Constructor that initializes the enumRange property.
   *
   * @param range initial null value
   */
  public BDynamicEnumTagInfo(BEnumRange range)
  {
    setEnumRange(range);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.CHANGED)
    {
      fwChanged((Property)a, (Context)b);
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwChanged(Property p, Context cx)
  {
    if (p.equals(enumRange))
    {
      BValue defValue = getDefValue();
      if (defValue instanceof BDynamicEnum)
      {
        setDefValue(BDynamicEnum.make(((BDynamicEnum) defValue).getOrdinal(), getEnumRange()));
      }
    }
  } 
}
