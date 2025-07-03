/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.*;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.units.BUnit;

/**
 * @author    Craig Gemmill
 * @creation  26 Jul 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.1
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.LOOP)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.LOOP, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
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
  name = "outputUnits",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetEngineeringUnits.NO_UNITS, BEnumRange.make(BBacnetEngineeringUnits.TYPE))",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OUTPUT_UNITS, ASN_ENUMERATED)")
)
@NiagaraProperty(
  name = "manipulatedVariableReference",
  type = "BBacnetObjectPropertyReference",
  defaultValue = "new BBacnetObjectPropertyReference()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.MANIPULATED_VARIABLE_REFERENCE, ASN_CONSTRUCTED_DATA)")
)
@NiagaraProperty(
  name = "controlledVariableReference",
  type = "BBacnetObjectPropertyReference",
  defaultValue = "new BBacnetObjectPropertyReference()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_REFERENCE, ASN_CONSTRUCTED_DATA)")
)
@NiagaraProperty(
  name = "controlledVariableValue",
  type = "float",
  defaultValue = "0",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_VALUE, ASN_REAL)")
)
@NiagaraProperty(
  name = "controlledVariableUnits",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetEngineeringUnits.NO_UNITS, BEnumRange.make(BBacnetEngineeringUnits.TYPE))",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_UNITS, ASN_ENUMERATED)")
)
@NiagaraProperty(
  name = "setpointReference",
  type = "BBacnetSetpointReference",
  defaultValue = "new BBacnetSetpointReference()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.SETPOINT_REFERENCE, ASN_CONSTRUCTED_DATA)")
)
@NiagaraProperty(
  name = "setpoint",
  type = "float",
  defaultValue = "0",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.SETPOINT, ASN_REAL)")
)
@NiagaraProperty(
  name = "action",
  type = "BBacnetAction",
  defaultValue = "BBacnetAction.direct",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.ACTION, ASN_ENUMERATED)")
)
@NiagaraProperty(
  name = "priorityForWriting",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(16)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING, ASN_UNSIGNED)")
)
public class BBacnetLoop
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetLoop(2672481609)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.LOOP), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.LOOP, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

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

  //region Property "outputUnits"

  /**
   * Slot for the {@code outputUnits} property.
   * @see #getOutputUnits
   * @see #setOutputUnits
   */
  public static final Property outputUnits = newProperty(0, BDynamicEnum.make(BBacnetEngineeringUnits.NO_UNITS, BEnumRange.make(BBacnetEngineeringUnits.TYPE)), makeFacets(BBacnetPropertyIdentifier.OUTPUT_UNITS, ASN_ENUMERATED));

  /**
   * Get the {@code outputUnits} property.
   * @see #outputUnits
   */
  public BEnum getOutputUnits() { return (BEnum)get(outputUnits); }

  /**
   * Set the {@code outputUnits} property.
   * @see #outputUnits
   */
  public void setOutputUnits(BEnum v) { set(outputUnits, v, null); }

  //endregion Property "outputUnits"

  //region Property "manipulatedVariableReference"

  /**
   * Slot for the {@code manipulatedVariableReference} property.
   * @see #getManipulatedVariableReference
   * @see #setManipulatedVariableReference
   */
  public static final Property manipulatedVariableReference = newProperty(0, new BBacnetObjectPropertyReference(), makeFacets(BBacnetPropertyIdentifier.MANIPULATED_VARIABLE_REFERENCE, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code manipulatedVariableReference} property.
   * @see #manipulatedVariableReference
   */
  public BBacnetObjectPropertyReference getManipulatedVariableReference() { return (BBacnetObjectPropertyReference)get(manipulatedVariableReference); }

  /**
   * Set the {@code manipulatedVariableReference} property.
   * @see #manipulatedVariableReference
   */
  public void setManipulatedVariableReference(BBacnetObjectPropertyReference v) { set(manipulatedVariableReference, v, null); }

  //endregion Property "manipulatedVariableReference"

  //region Property "controlledVariableReference"

  /**
   * Slot for the {@code controlledVariableReference} property.
   * @see #getControlledVariableReference
   * @see #setControlledVariableReference
   */
  public static final Property controlledVariableReference = newProperty(0, new BBacnetObjectPropertyReference(), makeFacets(BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_REFERENCE, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code controlledVariableReference} property.
   * @see #controlledVariableReference
   */
  public BBacnetObjectPropertyReference getControlledVariableReference() { return (BBacnetObjectPropertyReference)get(controlledVariableReference); }

  /**
   * Set the {@code controlledVariableReference} property.
   * @see #controlledVariableReference
   */
  public void setControlledVariableReference(BBacnetObjectPropertyReference v) { set(controlledVariableReference, v, null); }

  //endregion Property "controlledVariableReference"

  //region Property "controlledVariableValue"

  /**
   * Slot for the {@code controlledVariableValue} property.
   * @see #getControlledVariableValue
   * @see #setControlledVariableValue
   */
  public static final Property controlledVariableValue = newProperty(0, 0, makeFacets(BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_VALUE, ASN_REAL));

  /**
   * Get the {@code controlledVariableValue} property.
   * @see #controlledVariableValue
   */
  public float getControlledVariableValue() { return getFloat(controlledVariableValue); }

  /**
   * Set the {@code controlledVariableValue} property.
   * @see #controlledVariableValue
   */
  public void setControlledVariableValue(float v) { setFloat(controlledVariableValue, v, null); }

  //endregion Property "controlledVariableValue"

  //region Property "controlledVariableUnits"

  /**
   * Slot for the {@code controlledVariableUnits} property.
   * @see #getControlledVariableUnits
   * @see #setControlledVariableUnits
   */
  public static final Property controlledVariableUnits = newProperty(0, BDynamicEnum.make(BBacnetEngineeringUnits.NO_UNITS, BEnumRange.make(BBacnetEngineeringUnits.TYPE)), makeFacets(BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_UNITS, ASN_ENUMERATED));

  /**
   * Get the {@code controlledVariableUnits} property.
   * @see #controlledVariableUnits
   */
  public BEnum getControlledVariableUnits() { return (BEnum)get(controlledVariableUnits); }

  /**
   * Set the {@code controlledVariableUnits} property.
   * @see #controlledVariableUnits
   */
  public void setControlledVariableUnits(BEnum v) { set(controlledVariableUnits, v, null); }

  //endregion Property "controlledVariableUnits"

  //region Property "setpointReference"

  /**
   * Slot for the {@code setpointReference} property.
   * @see #getSetpointReference
   * @see #setSetpointReference
   */
  public static final Property setpointReference = newProperty(0, new BBacnetSetpointReference(), makeFacets(BBacnetPropertyIdentifier.SETPOINT_REFERENCE, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code setpointReference} property.
   * @see #setpointReference
   */
  public BBacnetSetpointReference getSetpointReference() { return (BBacnetSetpointReference)get(setpointReference); }

  /**
   * Set the {@code setpointReference} property.
   * @see #setpointReference
   */
  public void setSetpointReference(BBacnetSetpointReference v) { set(setpointReference, v, null); }

  //endregion Property "setpointReference"

  //region Property "setpoint"

  /**
   * Slot for the {@code setpoint} property.
   * @see #getSetpoint
   * @see #setSetpoint
   */
  public static final Property setpoint = newProperty(0, 0, makeFacets(BBacnetPropertyIdentifier.SETPOINT, ASN_REAL));

  /**
   * Get the {@code setpoint} property.
   * @see #setpoint
   */
  public float getSetpoint() { return getFloat(setpoint); }

  /**
   * Set the {@code setpoint} property.
   * @see #setpoint
   */
  public void setSetpoint(float v) { setFloat(setpoint, v, null); }

  //endregion Property "setpoint"

  //region Property "action"

  /**
   * Slot for the {@code action} property.
   * @see #getAction
   * @see #setAction
   */
  public static final Property action = newProperty(0, BBacnetAction.direct, makeFacets(BBacnetPropertyIdentifier.ACTION, ASN_ENUMERATED));

  /**
   * Get the {@code action} property.
   * @see #action
   */
  public BBacnetAction getAction() { return (BBacnetAction)get(action); }

  /**
   * Set the {@code action} property.
   * @see #action
   */
  public void setAction(BBacnetAction v) { set(action, v, null); }

  //endregion Property "action"

  //region Property "priorityForWriting"

  /**
   * Slot for the {@code priorityForWriting} property.
   * @see #getPriorityForWriting
   * @see #setPriorityForWriting
   */
  public static final Property priorityForWriting = newProperty(0, BBacnetUnsigned.make(16), makeFacets(BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING, ASN_UNSIGNED));

  /**
   * Get the {@code priorityForWriting} property.
   * @see #priorityForWriting
   */
  public BBacnetUnsigned getPriorityForWriting() { return (BBacnetUnsigned)get(priorityForWriting); }

  /**
   * Set the {@code priorityForWriting} property.
   * @see #priorityForWriting
   */
  public void setPriorityForWriting(BBacnetUnsigned v) { set(priorityForWriting, v, null); }

  //endregion Property "priorityForWriting"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLoop.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetLoop() {}


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context))
      .append(nameContext.equals(context)?'_':':')
      .append(getPresentValue());
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * Started.
   */
  public void started()
    throws Exception
  {
    super.started();
    proportionalFacets = getFacetsFromUnits(PROPORTIONAL_CONSTANT_UNITS, 2);
    integralFacets = getFacetsFromUnits(INTEGRAL_CONSTANT_UNITS, 2);
    derivativeFacets = getFacetsFromUnits(DERIVATIVE_CONSTANT_UNITS, 2);
  }

  /**
   * Stopped.
   */
  public void stopped()
    throws Exception
  {
    super.stopped();
    proportionalFacets = null;
    integralFacets = null;
    derivativeFacets = null;
  }

  /**
   * Property changed.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p,cx);
    if (!isRunning()) return;
    if (p.getName().equals(PROPORTIONAL_CONSTANT_UNITS))
      proportionalFacets = getFacetsFromUnits(PROPORTIONAL_CONSTANT_UNITS, 2);
    else if (p.getName().equals(INTEGRAL_CONSTANT_UNITS))
      integralFacets = getFacetsFromUnits(INTEGRAL_CONSTANT_UNITS, 2);
    else if (p.getName().equals(DERIVATIVE_CONSTANT_UNITS))
      derivativeFacets = getFacetsFromUnits(DERIVATIVE_CONSTANT_UNITS, 2);
  }

  /**
   * Property added.
   */
  public void added(Property p, Context cx)
  {
    super.added(p,cx);
    if (!isRunning()) return;
    if (p.getName().equals(PROPORTIONAL_CONSTANT_UNITS))
      proportionalFacets = getFacetsFromUnits(PROPORTIONAL_CONSTANT_UNITS, 2);
    else if (p.getName().equals(INTEGRAL_CONSTANT_UNITS))
      integralFacets = getFacetsFromUnits(INTEGRAL_CONSTANT_UNITS, 2);
    else if (p.getName().equals(DERIVATIVE_CONSTANT_UNITS))
      derivativeFacets = getFacetsFromUnits(DERIVATIVE_CONSTANT_UNITS, 2);
  }

  /**
   * Property removed.
   */
  public void removed(Property p, BValue oldValue, Context cx)
  {
    super.removed(p,oldValue,cx);
    if (!isRunning()) return;
    if (p.getName().equals(PROPORTIONAL_CONSTANT_UNITS))
      proportionalFacets = null;
    else if (p.getName().equals(INTEGRAL_CONSTANT_UNITS))
      integralFacets = null;
    else if (p.getName().equals(DERIVATIVE_CONSTANT_UNITS))
      derivativeFacets = null;
  }

  /**
   * Get the slot facets.
   */
  public BFacets getSlotFacets(Slot s)
  {
    if (s.equals(presentValue)) return getFacets();
//    if (s.equals(controlledVariable)) return
    if (s.getName().equals(PROPORTIONAL_CONSTANT))
      return proportionalFacets != null ? proportionalFacets : BFacets.DEFAULT;
    if (s.getName().equals(INTEGRAL_CONSTANT))
      return integralFacets != null ? integralFacets : BFacets.DEFAULT;
    if (s.getName().equals(DERIVATIVE_CONSTANT))
      return derivativeFacets != null ? derivativeFacets : BFacets.DEFAULT;
    return super.getSlotFacets(s);
  }

  public void setOutputFacets()
  {
    BUnit u = null;
    try
    {
      u = BBacnetEngineeringUnits.make(getOutputUnits().getOrdinal()).getNiagaraUnits();
    }
    catch (InvalidEnumException e)
    {
      log.warning("Can't make BUnits from BacnetEngineeringUnits:"+getOutputUnits());
    }

    // Check for min & max constraints.
    BFloat minPV = (BFloat)get("minimumOutput");
    BFloat maxPV = (BFloat)get("maximumOutput");

    // If no constraints, just use +/-Inf.
    if (minPV == null) minPV = BFloat.make(Float.NEGATIVE_INFINITY);
    if (maxPV == null) maxPV = BFloat.make(Float.POSITIVE_INFINITY);

    // Adjust min & max if they are +/-Float.MAX_VALUE, for display purposes.
    if (minPV.getFloat() == -Float.MAX_VALUE) minPV = BFloat.make(Float.NEGATIVE_INFINITY);
    if (maxPV.getFloat() == Float.MAX_VALUE) maxPV = BFloat.make(Float.POSITIVE_INFINITY);

    // Build & set facets.
    BFacets f = BFacets.makeNumeric(u, minPV, maxPV);
    setFacets(f);
  }


////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

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
//  Utility
////////////////////////////////////////////////////////////////

  private BFacets getFacetsFromUnits(String units, int precision)
  {
    BEnum u = (BEnum)get(units);
    if (u != null)
      return BFacets.makeNumeric(BBacnetEngineeringUnits.getNiagaraUnits(u.getOrdinal()), precision);
    return null;
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  private static final String PROPORTIONAL_CONSTANT = "proportionalConstant";
  private static final String INTEGRAL_CONSTANT = "integralConstant";
  private static final String DERIVATIVE_CONSTANT = "derivativeConstant";

  private static final String PROPORTIONAL_CONSTANT_UNITS = "proportionalConstantUnits";
  private static final String INTEGRAL_CONSTANT_UNITS = "integralConstantUnits";
  private static final String DERIVATIVE_CONSTANT_UNITS = "derivativeConstantUnits";


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private BFacets proportionalFacets;
  private BFacets integralFacets;
  private BFacets derivativeFacets;
}
