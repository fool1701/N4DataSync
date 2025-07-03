/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.enums.BBacnetBinaryPv;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision: 7$ $Date: 12/10/01 9:26:03 AM$
 * @creation 31 Jan 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 presentValue represents the object's current value.
 */
@NiagaraProperty(
  name = "presentValue",
  type = "BBacnetBinaryPv",
  defaultValue = "BBacnetBinaryPv.inactive",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_ENUMERATED)")
)
/*
 These facets are applied against the presentValue property.
 They are determined from the Active_Text and Inactive_Text
 properties (if present).
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  flags = Flags.HIDDEN | Flags.READONLY
)
/*
 statusFlags represents flags indicating the alarm and fault status of
 the Bacnet object.
 */
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
abstract public class BBacnetBinary
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetBinary(1319186145)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "presentValue"

  /**
   * Slot for the {@code presentValue} property.
   * presentValue represents the object's current value.
   * @see #getPresentValue
   * @see #setPresentValue
   */
  public static final Property presentValue = newProperty(0, BBacnetBinaryPv.inactive, makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_ENUMERATED));

  /**
   * Get the {@code presentValue} property.
   * presentValue represents the object's current value.
   * @see #presentValue
   */
  public BBacnetBinaryPv getPresentValue() { return (BBacnetBinaryPv)get(presentValue); }

  /**
   * Set the {@code presentValue} property.
   * presentValue represents the object's current value.
   * @see #presentValue
   */
  public void setPresentValue(BBacnetBinaryPv v) { set(presentValue, v, null); }

  //endregion Property "presentValue"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the Active_Text and Inactive_Text
   * properties (if present).
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.HIDDEN | Flags.READONLY, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the Active_Text and Inactive_Text
   * properties (if present).
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the Active_Text and Inactive_Text
   * properties (if present).
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "statusFlags"

  /**
   * Slot for the {@code statusFlags} property.
   * statusFlags represents flags indicating the alarm and fault status of
   * the Bacnet object.
   * @see #getStatusFlags
   * @see #setStatusFlags
   */
  public static final Property statusFlags = newProperty(Flags.READONLY, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetStatusFlags")), makeFacets(BBacnetPropertyIdentifier.STATUS_FLAGS, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_STATUS_FLAGS_MAP));

  /**
   * Get the {@code statusFlags} property.
   * statusFlags represents flags indicating the alarm and fault status of
   * the Bacnet object.
   * @see #statusFlags
   */
  public BBacnetBitString getStatusFlags() { return (BBacnetBitString)get(statusFlags); }

  /**
   * Set the {@code statusFlags} property.
   * statusFlags represents flags indicating the alarm and fault status of
   * the Bacnet object.
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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBinary.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetBinary()
  {
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context)).append(" = " + getPresentValue().toString(context));
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
    if (slot == presentValue) return getFacets();
    if (slot.getName().equals("alarmValue")) return getFacets();
    if (slot.getName().equals("feedbackValue")) return getFacets();
    if (slot.getName().equals("relinquishDefault")) return getFacets();
    return super.getSlotFacets(slot);
  }

  public void setOutputFacets()
  {
    BString activeText = (BString)get("activeText");
    BString inactiveText = (BString)get("inactiveText");
    BFacets f = BFacets.makeBoolean(
      ((activeText != null) ? activeText : (BString.make("true"))),
      ((inactiveText != null) ? inactiveText : (BString.make("false"))));
    setFacets(f);
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
//  Test Code
////////////////////////////////////////////////////////////////

}
