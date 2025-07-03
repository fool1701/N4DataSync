/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnUtil;

/**
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 24 Jun 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "presentValue",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_UNSIGNED)")
)
/*
 These facets are applied against the presentValue property.
 They are determined from the State_Text property (if present).
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
/*
 the number of states that the Present_Value may take on.
 */
@NiagaraProperty(
  name = "numberOfStates",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.DEFAULT",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.NUMBER_OF_STATES, ASN_UNSIGNED)")
)
abstract public class BBacnetMultistate
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetMultistate(851368486)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "presentValue"

  /**
   * Slot for the {@code presentValue} property.
   * @see #getPresentValue
   * @see #setPresentValue
   */
  public static final Property presentValue = newProperty(0, BDynamicEnum.make(0), makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_UNSIGNED));

  /**
   * Get the {@code presentValue} property.
   * @see #presentValue
   */
  public BEnum getPresentValue() { return (BEnum)get(presentValue); }

  /**
   * Set the {@code presentValue} property.
   * @see #presentValue
   */
  public void setPresentValue(BEnum v) { set(presentValue, v, null); }

  //endregion Property "presentValue"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the State_Text property (if present).
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.READONLY, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the State_Text property (if present).
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the presentValue property.
   * They are determined from the State_Text property (if present).
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

  //region Property "numberOfStates"

  /**
   * Slot for the {@code numberOfStates} property.
   * the number of states that the Present_Value may take on.
   * @see #getNumberOfStates
   * @see #setNumberOfStates
   */
  public static final Property numberOfStates = newProperty(0, BBacnetUnsigned.DEFAULT, makeFacets(BBacnetPropertyIdentifier.NUMBER_OF_STATES, ASN_UNSIGNED));

  /**
   * Get the {@code numberOfStates} property.
   * the number of states that the Present_Value may take on.
   * @see #numberOfStates
   */
  public BBacnetUnsigned getNumberOfStates() { return (BBacnetUnsigned)get(numberOfStates); }

  /**
   * Set the {@code numberOfStates} property.
   * the number of states that the Present_Value may take on.
   * @see #numberOfStates
   */
  public void setNumberOfStates(BBacnetUnsigned v) { set(numberOfStates, v, null); }

  //endregion Property "numberOfStates"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetMultistate.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $javax.baja.bacnet.config.BBacnetMultistate(1504781693)1.0$ @*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetMultistate()
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
    return super.getSlotFacets(slot);
  }

  public void setOutputFacets()
  {
    BBacnetArray stateText = (BBacnetArray)get(BBacnetPropertyIdentifier.stateText.getTag());
    if (stateText != null)
    {
      int[] ords = new int[stateText.getSize()];
      for (int i = 0; i < ords.length; i++) ords[i] = i + 1;
      String[] tags = new String[ords.length];
      for (int i = 0; i < tags.length; i++)
        tags[i] = SlotPath.escape(stateText.getElement(i + 1).toString());
      setFacets(BFacets.makeEnum(BEnumRange.make(ords, tags)));
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

  /**
   * Convert the property to an ASN.1-encoded byte array.
   * Subclasses with properties requiring specialized encoding
   * may need to override this method.
   *
   * @param d
   * @param p
   * @return encoded byte array
   */
  protected byte[] toEncodedValue(BacnetPropertyData d, Property p)
  {
    if (d.getPropertyId() == BBacnetPropertyIdentifier.PRESENT_VALUE)
    {
      return AsnUtil.toAsnUnsigned(((BEnum)get(p)).getOrdinal());
    }
    return AsnUtil.toAsn(d.getAsnType(), get(p));
  }


////////////////////////////////////////////////////////////////
//  Test Code
////////////////////////////////////////////////////////////////

}
