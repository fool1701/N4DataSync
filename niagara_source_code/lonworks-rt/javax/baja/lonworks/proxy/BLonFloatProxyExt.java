/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.proxy;

import javax.baja.control.BControlPoint;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonElementQualifiers;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.lonworks.londata.LonFacetsUtil;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;
import javax.baja.units.BUnit;

/**
 * BLonFloatProxyExt is the proxy extension for
 * <code>BNumericPoint</code>. It will link a single
 * float point to a lonworks <code>BLonPrimitive</code>.
 * The appropriate conversions will be performed.
 * <p>
 * BLonFloatProxyExt also handles converting the
 * min and max facets in the <code>BNumericPoint</code>
 * when device and control point engineering units
 * are different.
 *
 * @author    Robert Adams
 * @creation  19 Dec 01
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:42 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "readValue",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
@NiagaraProperty(
  name = "writeValue",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
public final class BLonFloatProxyExt
  extends BLonProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.proxy.BLonFloatProxyExt(2024563189)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "readValue"

  /**
   * Slot for the {@code readValue} property.
   * @see #getReadValue
   * @see #setReadValue
   */
  public static final Property readValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusNumeric(), null);

  //endregion Property "readValue"

  //region Property "writeValue"

  /**
   * Slot for the {@code writeValue} property.
   * @see #getWriteValue
   * @see #setWriteValue
   */
  public static final Property writeValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusNumeric(), null);

  //endregion Property "writeValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFloatProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  protected void extStarted()
  {
    super.extStarted();
    checkFacets();
  }
  
  /**
   * Callback when the point facets change. If the eng units
   * changed make sure the min and max values are adjusted
   * as needed.
   */
  public void pointFacetsChanged()
  {
    super.pointFacetsChanged();
    
    checkFacets();
    
    BControlPoint cp =  (BControlPoint)getParent();
    BFacets pntFcts = cp.getFacets();
    BUnit pntUnit = (BUnit)pntFcts.get(BFacets.UNITS);
    // If no units on point then forget it
    if(pntUnit==null) return;

    BFacets devFcts = getDeviceFacets();
    BUnit devUnit = (BUnit)devFcts.get(BFacets.UNITS);
    // If no units on device element forget if 
    if(devUnit==null) return;

    // Must be same dimension
    if(!pntUnit.isConvertible(devUnit)) return;
    
    // min and max must not exceed the converted dev values
    double devMin;
    BNumber curDevMin = (BNumber)devFcts.get(BFacets.MIN, BFloat.NEGATIVE_INFINITY);
    if(curDevMin!=BFloat.NEGATIVE_INFINITY)
    {
      devMin = devUnit.convertTo(pntUnit, curDevMin.getNumeric());
    }
    else
    {
      devMin = Double.NEGATIVE_INFINITY;
    }

    double devMax;
    BNumber curDevMax = (BNumber)devFcts.get(BFacets.MAX, BFloat.POSITIVE_INFINITY);

    if(curDevMax!=BFloat.POSITIVE_INFINITY)
    {
      devMax = (float)devUnit.convertTo(pntUnit, curDevMax.getNumeric());
    }
    else
    {
      devMax= Double.POSITIVE_INFINITY;
    }

    double newMin = ((BNumber)pntFcts.get(BFacets.MIN, BFloat.NEGATIVE_INFINITY)).getDouble();
    double newMax = ((BNumber)pntFcts.get(BFacets.MAX, BFloat.POSITIVE_INFINITY)).getDouble();
    double min = Math.max(newMin,devMin);
    double max = Math.min(newMax,devMax);
    if( min!=newMin  || max!=newMax )
    {
      BInteger p = (BInteger)devFcts.get(BFacets.PRECISION,null);
      int prec = (p==null) ? 1 : p.getInt();
      BFacets f = BFacets.makeNumeric(pntUnit,prec,min,max);
      cp.setFacets(f);
    }
  }

  /**
   * Get data point value to as a BStatusNumeric.
   */
  public BStatusValue getStatusValue(BLonPrimitive newValue)
  {
    double num = newValue.getDataAsDouble();
    BStatusNumeric sNum = new BStatusNumeric(num);
    if(Double.isNaN(num) && nanIsNull) sNum.setStatusNull(true);
    else if(nullSpecified && num==nullVal) sNum.setStatusNull(true);
    return sNum;
  }

  /**
   * Convert a BStatusNumeric to a BLonPrimitive.
   */
  public BLonPrimitive getPrimitiveValue(BStatusValue value)
  {
    BLonData dataPoint = getDataPoint();    
    double val = ((BStatusNumeric)value).getValue();
    BLonElementQualifiers e = LonFacetsUtil.getQualifiers(targetProp.getFacets());
    return ((BLonPrimitive)dataPoint.get(targetProp)).makeFromDouble(val,e);
  }
  
  // Set local nanIsNull flag per nanIsNull point facet
  private void checkFacets()
  {
    BControlPoint cp =  (BControlPoint)getParent();
    BFacets pntFcts = cp.getFacets();
    nanIsNull = pntFcts.getb("nanIsNull",false);
    double d = pntFcts.getd("isNull",Double.NaN);
    if(Double.isNaN(d))
    {
      nullSpecified = false;
    }
    else
    {
      nullSpecified = true;
      nullVal = d;
    }
  }
  private boolean nanIsNull = false;
  private boolean nullSpecified = false;
  private double  nullVal;
}
