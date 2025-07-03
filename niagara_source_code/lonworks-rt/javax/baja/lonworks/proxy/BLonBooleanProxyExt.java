/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.proxy;

import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonBooleanProxyExt is the proxy extension for 
 * <code>BBooleanPoint</code>. It will link a single
 * boolean point to a lonworks <code>BLonPrimitive</code>.
 * The appropriate conversions will be performed.
 *
 * @author    Robert Adams
 * @creation  19 Dec 01
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:42 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "readValue",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
@NiagaraProperty(
  name = "writeValue",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
public final class BLonBooleanProxyExt
  extends BLonProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.proxy.BLonBooleanProxyExt(1823119248)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "readValue"

  /**
   * Slot for the {@code readValue} property.
   * @see #getReadValue
   * @see #setReadValue
   */
  public static final Property readValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusBoolean(), null);

  //endregion Property "readValue"

  //region Property "writeValue"

  /**
   * Slot for the {@code writeValue} property.
   * @see #getWriteValue
   * @see #setWriteValue
   */
  public static final Property writeValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusBoolean(), null);

  //endregion Property "writeValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonBooleanProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  /**
   * Get data point value to as a BStatusBoolean.
   */
  public BStatusValue getStatusValue(BLonPrimitive newValue)
  {
    return new BStatusBoolean(newValue.getDataAsBoolean());
  }
  
  /**
   * Convert a BStatusBoolean to a BLonPrimitive.
   */
  public BLonPrimitive getPrimitiveValue(BStatusValue value)
  {
    BLonData dataPoint = getDataPoint();    
    boolean val = ((BStatusBoolean)value).getValue();
    return ((BLonPrimitive)dataPoint.get(targetProp)).makeFromBoolean(val);
  }  
  
}
