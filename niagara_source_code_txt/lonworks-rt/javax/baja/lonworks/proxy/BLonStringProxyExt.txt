/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.proxy;

import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonStringProxyExt is the proxy extension for 
 * <code>BStringPoint</code>. It will link a single
 * boolean point to a lonworks <code>BLonPrimitive</code>.
 * The appropriate conversions will be performed.
 *
 * @author    Robert Adams
 * @creation  19 Dec 01
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:42 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public final class BLonStringProxyExt
  extends BLonProxyExt
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.proxy.BLonStringProxyExt(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonStringProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get data point value to as a BStatusString.
   */
  public BStatusValue getStatusValue(BLonPrimitive newValue)
  {
    return new BStatusString(newValue.getDataAsString());
  }
  
  
  /**
   * Convert a BStatusString to a BLonString.
   */
  public BLonPrimitive getPrimitiveValue(BStatusValue value)
  {
    BLonData dataPoint = getDataPoint();    
    String val = ((BStatusString)value).getValue();
    return ((BLonPrimitive)dataPoint.get(targetProp)).makeFromString(val);
  }
 
}
