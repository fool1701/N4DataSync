/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.types;

import javax.baja.log.Log;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.points.BNrioCounterInputProxyExt;

@NiagaraType
@NiagaraProperty(
  name = "scale",
  type = "float",
  defaultValue = "1.0f",
  facets = @Facet("BFacets.make(BFacets.PRECISION, BInteger.make(5))")
)
public abstract class BAbstractRateType extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.types.BAbstractRateType(319915983)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "scale"

  /**
   * Slot for the {@code scale} property.
   * @see #getScale
   * @see #setScale
   */
  public static final Property scale = newProperty(0, 1.0f, BFacets.make(BFacets.PRECISION, BInteger.make(5)));

  /**
   * Get the {@code scale} property.
   * @see #scale
   */
  public float getScale() { return getFloat(scale); }

  /**
   * Set the {@code scale} property.
   * @see #scale
   */
  public void setScale(float v) { setFloat(scale, v, null); }

  //endregion Property "scale"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractRateType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public boolean isParentLegal(BComponent parent)
  {
    if (super.isParentLegal(parent))
      if (parent instanceof BNrioCounterInputProxyExt)
        return true;

    return false;
  }

  public BNrioCounterInputProxyExt getCounterProxy()
  {
    return (BNrioCounterInputProxyExt) getParent(); 
  }

  public void initType()
  {
  }
  
  public void cleanupType()
  {
  }

  public abstract void resetRate();
  
  public abstract BStatusNumeric calculateRate(long count);
  
  protected static final Log log = Log.getLog("gpIo");  
}
