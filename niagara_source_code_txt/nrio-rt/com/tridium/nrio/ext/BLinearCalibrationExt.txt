/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ext;

import javax.baja.control.BControlPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BPointExtension;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;

/**
 * The BNrioScaleOffsetExt is a standard point extension
 * that takes the value of a numeric point and applies
 * the linear function: newVal = oldVal * scale + offset
 *
 * @author    Bill Smith
 * @creation  1 Apr 2003
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 A multiplier against the old value
 */
@NiagaraProperty(
  name = "scale",
  type = "float",
  defaultValue = "1.0f",
  facets = @Facet("BFacets.make(BFacets.PRECISION, BInteger.make(5))")
)
/*
 Added to the value after the scale
 has been applied.
 */
@NiagaraProperty(
  name = "offset",
  type = "float",
  defaultValue = "0.0f",
  facets = @Facet("BFacets.make(BFacets.PRECISION, BInteger.make(5))")
)
@NiagaraProperty(
  name = "units",
  type = "BUnit",
  defaultValue = "BUnit.DEFAULT"
)
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
public class BLinearCalibrationExt
  extends BPointExtension
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ext.BLinearCalibrationExt(2081887017)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "scale"

  /**
   * Slot for the {@code scale} property.
   * A multiplier against the old value
   * @see #getScale
   * @see #setScale
   */
  public static final Property scale = newProperty(0, 1.0f, BFacets.make(BFacets.PRECISION, BInteger.make(5)));

  /**
   * Get the {@code scale} property.
   * A multiplier against the old value
   * @see #scale
   */
  public float getScale() { return getFloat(scale); }

  /**
   * Set the {@code scale} property.
   * A multiplier against the old value
   * @see #scale
   */
  public void setScale(float v) { setFloat(scale, v, null); }

  //endregion Property "scale"

  //region Property "offset"

  /**
   * Slot for the {@code offset} property.
   * Added to the value after the scale
   * has been applied.
   * @see #getOffset
   * @see #setOffset
   */
  public static final Property offset = newProperty(0, 0.0f, BFacets.make(BFacets.PRECISION, BInteger.make(5)));

  /**
   * Get the {@code offset} property.
   * Added to the value after the scale
   * has been applied.
   * @see #offset
   */
  public float getOffset() { return getFloat(offset); }

  /**
   * Set the {@code offset} property.
   * Added to the value after the scale
   * has been applied.
   * @see #offset
   */
  public void setOffset(float v) { setFloat(offset, v, null); }

  //endregion Property "offset"

  //region Property "units"

  /**
   * Slot for the {@code units} property.
   * @see #getUnits
   * @see #setUnits
   */
  public static final Property units = newProperty(0, BUnit.DEFAULT, null);

  /**
   * Get the {@code units} property.
   * @see #units
   */
  public BUnit getUnits() { return (BUnit)get(units); }

  /**
   * Set the {@code units} property.
   * @see #units
   */
  public void setUnits(BUnit v) { set(units, v, null); }

  //endregion Property "units"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLinearCalibrationExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  public BLinearCalibrationExt()
  {
  }

  public BLinearCalibrationExt(BUnit units)
  {
    setUnits(units);
  }

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * Forces the parent to be a numeric point.
   */

  public boolean isParentLegal(BComponent parent)
  {
    if (parent instanceof BNumericPoint)
      return true;
    else
      return false;
  }


////////////////////////////////////////////////////////////////
//  Initialization
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
// Update Methods
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context cx)
  {
  	super.changed(p, cx);
  	if(!isRunning())
  		return;
  	if(p.equals(scale) || p.equals(offset) || p.equals(units))
  	{
  		BControlPoint parent = getParentPoint();
  		if(parent != null) getParentPoint().execute();
  	}
  }

  /**
   * Called when either me or my parent control
   * point is updated.
   */

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusNumeric out = (BStatusNumeric)o;

    BUnit proxyUnit = (BUnit) getPointFacets().get(BFacets.UNITS, BUnit.NULL);

    if (proxyUnit.isConvertible(getUnits()))
    {
      double value = out.getValue();

      double calval = proxyUnit.convertTo(getUnits(), value);
      calval = getFloat(scale) * calval + getFloat(offset);
      value = getUnits().convertTo(proxyUnit, calval);

      o.setValueValue(BDouble.make(value));
      setFaultCause("");
    }
    else if (proxyUnit.isNull() || getUnits().isNull())
    {
      double value = out.getValue();
      value = getFloat(scale) * value + getFloat(offset);
      o.setValueValue(BDouble.make(value));
      setFaultCause("");
    }
    else
    {
      o.setStatusFault(true);
      setFaultCause("Units between point and extension are not convertible.");
    }
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
