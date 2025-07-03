/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.proxy;

import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.data.BIDataValue;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonEnum;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;

/**
 * BLonEnumProxyExt is the proxy extension for 
 * <code>BEnumPoint</code>. It will link a single
 * enum point to a lonworks <code>BLonPrimitive</code>.
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
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
@NiagaraProperty(
  name = "writeValue",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
public final class BLonEnumProxyExt
  extends BLonProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.proxy.BLonEnumProxyExt(2942147947)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "readValue"

  /**
   * Slot for the {@code readValue} property.
   * @see #getReadValue
   * @see #setReadValue
   */
  public static final Property readValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusEnum(), null);

  //endregion Property "readValue"

  //region Property "writeValue"

  /**
   * Slot for the {@code writeValue} property.
   * @see #getWriteValue
   * @see #setWriteValue
   */
  public static final Property writeValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusEnum(), null);

  //endregion Property "writeValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonEnumProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  protected void extStarted()
  {
    checkFacets();
    updateFacets();

    // Set the read value from enum created from range so correct
    // enum available for later read conversions
    BEnumRange range = (BEnumRange)getDeviceFacets().getFacet(BFacets.RANGE);
    if(range==null || range.getOrdinals().length==0) 
    {
      getDevice().getLogger().warning("Invalid range for enum proxy in " + getDevice().getDisplayName(null) + ":" + getDisplayName(null));
      return;
    }
    BDynamicEnum en =  BDynamicEnum.make(range.getOrdinals()[0], range);
    BLonPrimitive tgt = getTarget();
    BEnum v = tgt.getDataAsEnum(en);
    
    setReadValue(new BStatusEnum(v));
  }
  
  // This is called from control point when it's facets change
  public void pointFacetsChanged()
  {
    updateFacets();
    super.pointFacetsChanged();
    checkFacets();
  }
  
  // Make sure the deviceFacets range matches the parents
  private void updateFacets()
  {
    BFacets f = ((BControlPoint)getParent()).getFacets();
    BFacets DeviceFacets = getDeviceFacets();
    BIDataValue idv = (BIDataValue)f.get(BFacets.RANGE);
    if(idv==null) return; // The control point may not be an enum
    setDeviceFacets( BFacets.make(DeviceFacets, BFacets.RANGE, idv) );// BFacets.makeEnum(BEnumRange range));
  }

  /**
   * Set the Control Point and ProxyExt facets from the device facets
   * which may have changed. This is called after reimport of lon xml.
   */
  protected void deviceFacetsChanged()
  {
    // Get range of tgt BLonEnum
    BLonPrimitive tgt = getTarget();
    if(tgt==null || !tgt.getType().is(BLonEnum.TYPE)) return;
    BEnumRange rng = ((BLonEnum)tgt).getEnum().getRange();
    
    // Update range - update cp facets prop which forces update of deviceFacets 
    BFacets cpFacets = ((BControlPoint)getParent()).getFacets();
    BFacets newFacets = BFacets.make(cpFacets, BFacets.RANGE, rng);
    ((BControlPoint)getParent()).setFacets(newFacets);
  }
  
  /**
   * Get data point value to as a BStatusEnum.
   */
  public BStatusValue getStatusValue(BLonPrimitive newValue)
  {
    BStatusEnum msElem = (BStatusEnum)((BEnumPoint)getParent()).getOutStatusValue();

    BEnum v = newValue.getDataAsEnum(msElem.getValue());
    BStatusEnum sEnum =  new BStatusEnum(v);
    if(nullSpecified && v.getOrdinal()==nullVal) sEnum.setStatusNull(true);
    return sEnum;
  }
  
  /**
   * Convert a BStatusEnum to a BLonPrimitive.
   */
  public BLonPrimitive getPrimitiveValue(BStatusValue value)
  {
    BLonData dataPoint = getDataPoint();    
    BDynamicEnum val = ((BStatusEnum)value).getValue();
    return ((BLonPrimitive)dataPoint.get(targetProp)).makeFromEnum(val);
  }

  // Set local nullVal flag per point facet
  private void checkFacets()
  {
    BControlPoint cp =  (BControlPoint)getParent();
    BFacets pntFcts = cp.getFacets();
    int n = pntFcts.geti("isNull",Integer.MAX_VALUE);
    if(n==Integer.MAX_VALUE)
    {
      nullSpecified = false;
    }
    else
    {
      nullSpecified = true;
      nullVal = n;
    }
  }
  private boolean nullSpecified = false;
  private int  nullVal;
  
}
