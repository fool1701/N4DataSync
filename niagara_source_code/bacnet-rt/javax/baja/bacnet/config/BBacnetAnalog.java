/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import java.util.logging.Level;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.enums.BBacnetEngineeringUnits;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.units.BUnit;

/**
 * @author    Craig Gemmill
 * @creation  30 Jan 01
 * @version   $Revision: 7$ $Date: 12/10/01 9:26:02 AM$
 * @since     Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "presentValue",
  type = "float",
  defaultValue = "0",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_REAL)")
)
/*
 These facets are applied against the presentValue property.
 They are determined from the Min_Pres_Value, Max_Pres_Value, and Units
 properties (if present).
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "statusFlags",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetStatusFlags\"))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.STATUS_FLAGS, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_STATUS_FLAGS_MAP)")
)
/*
 eventState indicates if this object has an active event state.
 */
@NiagaraProperty(
  name = "eventState",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetEventState.NORMAL, BEnumRange.make(BBacnetEventState.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.EVENT_STATE, ASN_ENUMERATED)")
)
/*
 is the physical point represented by this object out of service?
 if TRUE, then this point's Present_Value does NOT reflect the actual state
 of the point.
 */
@NiagaraProperty(
  name = "outOfService",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OUT_OF_SERVICE, ASN_BOOLEAN)")
)
@NiagaraProperty(
  name = "units",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetEngineeringUnits.NO_UNITS, BEnumRange.make(BBacnetEngineeringUnits.TYPE))",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.UNITS, ASN_ENUMERATED)")
)
abstract public class BBacnetAnalog
  extends BBacnetObject
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetAnalog(211285416)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "presentValue"

  /**
   * Slot for the {@code presentValue} property.
   * @see #getPresentValue
   * @see #setPresentValue
   */
  public static final Property presentValue = newProperty(0, 0, makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_REAL));

  /**
   * Get the {@code presentValue} property.
   * @see #presentValue
   */
  public float getPresentValue() { return getFloat(presentValue); }

  /**
   * Set the {@code presentValue} property.
   * @see #presentValue
   */
  public void setPresentValue(float v) { setFloat(presentValue, v, null); }

  //endregion Property "presentValue"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the Min_Pres_Value, Max_Pres_Value, and Units
   * properties (if present).
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.READONLY, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the Min_Pres_Value, Max_Pres_Value, and Units
   * properties (if present).
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the Min_Pres_Value, Max_Pres_Value, and Units
   * properties (if present).
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "statusFlags"

  /**
   * Slot for the {@code statusFlags} property.
   * @see #getStatusFlags
   * @see #setStatusFlags
   */
  public static final Property statusFlags = newProperty(Flags.READONLY, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetStatusFlags")), makeFacets(BBacnetPropertyIdentifier.STATUS_FLAGS, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_STATUS_FLAGS_MAP));

  /**
   * Get the {@code statusFlags} property.
   * @see #statusFlags
   */
  public BBacnetBitString getStatusFlags() { return (BBacnetBitString)get(statusFlags); }

  /**
   * Set the {@code statusFlags} property.
   * @see #statusFlags
   */
  public void setStatusFlags(BBacnetBitString v) { set(statusFlags, v, null); }

  //endregion Property "statusFlags"

  //region Property "eventState"

  /**
   * Slot for the {@code eventState} property.
   * eventState indicates if this object has an active event state.
   * @see #getEventState
   * @see #setEventState
   */
  public static final Property eventState = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetEventState.NORMAL, BEnumRange.make(BBacnetEventState.TYPE)), makeFacets(BBacnetPropertyIdentifier.EVENT_STATE, ASN_ENUMERATED));

  /**
   * Get the {@code eventState} property.
   * eventState indicates if this object has an active event state.
   * @see #eventState
   */
  public BEnum getEventState() { return (BEnum)get(eventState); }

  /**
   * Set the {@code eventState} property.
   * eventState indicates if this object has an active event state.
   * @see #eventState
   */
  public void setEventState(BEnum v) { set(eventState, v, null); }

  //endregion Property "eventState"

  //region Property "outOfService"

  /**
   * Slot for the {@code outOfService} property.
   * is the physical point represented by this object out of service?
   * if TRUE, then this point's Present_Value does NOT reflect the actual state
   * of the point.
   * @see #getOutOfService
   * @see #setOutOfService
   */
  public static final Property outOfService = newProperty(0, false, makeFacets(BBacnetPropertyIdentifier.OUT_OF_SERVICE, ASN_BOOLEAN));

  /**
   * Get the {@code outOfService} property.
   * is the physical point represented by this object out of service?
   * if TRUE, then this point's Present_Value does NOT reflect the actual state
   * of the point.
   * @see #outOfService
   */
  public boolean getOutOfService() { return getBoolean(outOfService); }

  /**
   * Set the {@code outOfService} property.
   * is the physical point represented by this object out of service?
   * if TRUE, then this point's Present_Value does NOT reflect the actual state
   * of the point.
   * @see #outOfService
   */
  public void setOutOfService(boolean v) { setBoolean(outOfService, v, null); }

  //endregion Property "outOfService"

  //region Property "units"

  /**
   * Slot for the {@code units} property.
   * @see #getUnits
   * @see #setUnits
   */
  public static final Property units = newProperty(0, BDynamicEnum.make(BBacnetEngineeringUnits.NO_UNITS, BEnumRange.make(BBacnetEngineeringUnits.TYPE)), makeFacets(BBacnetPropertyIdentifier.UNITS, ASN_ENUMERATED));

  /**
   * Get the {@code units} property.
   * @see #units
   */
  public BEnum getUnits() { return (BEnum)get(units); }

  /**
   * Set the {@code units} property.
   * @see #units
   */
  public void setUnits(BEnum v) { set(units, v, null); }

  //endregion Property "units"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAnalog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetAnalog() {}

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context)).append(" = "+getPresentValue());
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Property changed.
  public void changed(Property p, Context cx)
  {
    super.changed(p,cx);
    if (!isRunning()) return;
    if (p == facets) return;
  }
   */

  /**
   * Apply the "facets" property to the "presentValue" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.equals(presentValue)) return getFacets();
    if (slot.getName().equals("highLimit")) return getFacets();
    if (slot.getName().equals("lowLimit")) return getFacets();
//    if (slot.getName().equals("minPresValue")) return getFacets();
//    if (slot.getName().equals("maxPresValue")) return getFacets();
    if (slot.getName().equals("deadband")) return getFacets();
    if (slot.getName().equals("resolution")) return getFacets();
    if (slot.getName().equals("covIncrement")) return getFacets();
    return super.getSlotFacets(slot);
  }

  public void setOutputFacets()
  {
    try
    {
      BUnit u = null;
      try
      {
        u = BBacnetEngineeringUnits.getNiagaraUnits(getUnits().getOrdinal());
      }
      catch (Exception e) { log.info(this+":Can't make BUnits from BacnetEngineeringUnits:"+getUnits()); }
  
      // Use resolution property to set precision.
      BFloat res = (BFloat)get("resolution");
      BInteger precision = BInteger.make(2);
      if (res != null && res.getFloat() > 0)
        precision = BInteger.make((int)Math.ceil(-(Math.log(res.getFloat())/LN_10)));
  
      // Check for min & max constraints.
      BFloat minPV = (BFloat)get("minPresValue");
      BFloat maxPV = (BFloat)get("maxPresValue");
  
      // If no constraints, just use +/-Inf.
      if (minPV == null) minPV = BFloat.make(Float.NEGATIVE_INFINITY);
      if (maxPV == null) maxPV = BFloat.make(Float.POSITIVE_INFINITY);
  
      // Adjust min & max if they are +/-Float.MAX_VALUE, for display purposes.
      if (minPV.getFloat() == -Float.MAX_VALUE) minPV = BFloat.make(Float.NEGATIVE_INFINITY);
      if (maxPV.getFloat() == Float.MAX_VALUE) maxPV = BFloat.make(Float.POSITIVE_INFINITY);
  
      // Build & set facets.
      BFacets f = BFacets.makeNumeric(u, precision, minPV, maxPV);
      setFacets(f);
    }
    catch (Exception e)
    {
      log.log(Level.INFO,this+":Exception in setOutputFacets()",e);
    }
  }

  /**
   * Subclasses that have a present value property should
   * override this method and return this property.  The
   * default returns null.
   */
  public Property getPresentValueProperty()
  {
    return presentValue;
  }

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  private static final double LN_10 = Math.log(10.0);

}
