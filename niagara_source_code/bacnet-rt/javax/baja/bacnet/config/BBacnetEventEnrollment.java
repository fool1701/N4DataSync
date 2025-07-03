/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.*;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.*;

import com.tridium.bacnet.BacUtil;

/**
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 28 Jul 2006
 * @since Niagara 3.2
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.EVENT_ENROLLMENT)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.EVENT_ENROLLMENT, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
/*
 eventState indicates if this object has an active event state.
 */
@NiagaraProperty(
  name = "eventType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetEventType.CHANGE_OF_STATE, BEnumRange.make(BBacnetEventType.TYPE))",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.EVENT_TYPE, ASN_ENUMERATED)")
)
/*
 eventState indicates if this object has an active event state.
 */
@NiagaraProperty(
  name = "notifyType",
  type = "BBacnetNotifyType",
  defaultValue = "BBacnetNotifyType.event",
  facets = {
    @Facet("makeFacets(BBacnetPropertyIdentifier.NOTIFY_TYPE, ASN_ENUMERATED)"),
    @Facet("BFacets.make(BacUtil.makeBacnetNotifyTypeFacets())")
  }
)
/*
 determines the algorithm used to monitor the referenced object
 and provides the parameter values needed for this algorithm.
 */
@NiagaraProperty(
  name = "eventParameters",
  type = "BBacnetEventParameter",
  defaultValue = "new BBacnetEventParameter()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.EVENT_PARAMETERS, ASN_CONSTRUCTED_DATA)")
)
@NiagaraProperty(
  name = "objectPropertyReference",
  type = "BBacnetDeviceObjectPropertyReference",
  defaultValue = "new BBacnetDeviceObjectPropertyReference()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE, ASN_CONSTRUCTED_DATA)")
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
 conveys flags that whether notifications are enabled for the various
 transitions.
 */
@NiagaraProperty(
  name = "eventEnable",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetEventTransitionBits\"))",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.EVENT_ENABLE, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_MAP)")
)
/*
 indicates whether the last transition of each type has been acknowledged.
 */
@NiagaraProperty(
  name = "ackedTransitions",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetEventTransitionBits\"))",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.ACKED_TRANSITIONS, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_MAP)")
)
/*
 references a Notification Class in the same device that specifies the
 handling, reporting, and acknowledgment characteristics for this object.
 */
@NiagaraProperty(
  name = "notificationClass",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.NOTIFICATION_CLASS, ASN_UNSIGNED, new String[] { BFacets.MIN, BFacets.MAX }, new BInteger[] { BInteger.make(0), BInteger.make(BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER) } )")
)
/*
 contains the times of the last event notifications for each transition type.
 */
@NiagaraProperty(
  name = "eventTimeStamps",
  type = "BBacnetArray",
  defaultValue = "new BBacnetArray(BBacnetTimeStamp.TYPE, 3)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.EVENT_TIME_STAMPS, ASN_BACNET_ARRAY)")
)
public class BBacnetEventEnrollment extends BBacnetCreatableObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetEventEnrollment(1487487462)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.EVENT_ENROLLMENT), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.EVENT_ENROLLMENT, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "eventType"

  /**
   * Slot for the {@code eventType} property.
   * eventState indicates if this object has an active event state.
   * @see #getEventType
   * @see #setEventType
   */
  public static final Property eventType = newProperty(0, BDynamicEnum.make(BBacnetEventType.CHANGE_OF_STATE, BEnumRange.make(BBacnetEventType.TYPE)), makeFacets(BBacnetPropertyIdentifier.EVENT_TYPE, ASN_ENUMERATED));

  /**
   * Get the {@code eventType} property.
   * eventState indicates if this object has an active event state.
   * @see #eventType
   */
  public BEnum getEventType() { return (BEnum)get(eventType); }

  /**
   * Set the {@code eventType} property.
   * eventState indicates if this object has an active event state.
   * @see #eventType
   */
  public void setEventType(BEnum v) { set(eventType, v, null); }

  //endregion Property "eventType"

  //region Property "notifyType"

  /**
   * Slot for the {@code notifyType} property.
   * eventState indicates if this object has an active event state.
   * @see #getNotifyType
   * @see #setNotifyType
   */
  public static final Property notifyType = newProperty(0, BBacnetNotifyType.event, BFacets.make(makeFacets(BBacnetPropertyIdentifier.NOTIFY_TYPE, ASN_ENUMERATED), BFacets.make(BacUtil.makeBacnetNotifyTypeFacets())));

  /**
   * Get the {@code notifyType} property.
   * eventState indicates if this object has an active event state.
   * @see #notifyType
   */
  public BBacnetNotifyType getNotifyType() { return (BBacnetNotifyType)get(notifyType); }

  /**
   * Set the {@code notifyType} property.
   * eventState indicates if this object has an active event state.
   * @see #notifyType
   */
  public void setNotifyType(BBacnetNotifyType v) { set(notifyType, v, null); }

  //endregion Property "notifyType"

  //region Property "eventParameters"

  /**
   * Slot for the {@code eventParameters} property.
   * determines the algorithm used to monitor the referenced object
   * and provides the parameter values needed for this algorithm.
   * @see #getEventParameters
   * @see #setEventParameters
   */
  public static final Property eventParameters = newProperty(0, new BBacnetEventParameter(), makeFacets(BBacnetPropertyIdentifier.EVENT_PARAMETERS, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code eventParameters} property.
   * determines the algorithm used to monitor the referenced object
   * and provides the parameter values needed for this algorithm.
   * @see #eventParameters
   */
  public BBacnetEventParameter getEventParameters() { return (BBacnetEventParameter)get(eventParameters); }

  /**
   * Set the {@code eventParameters} property.
   * determines the algorithm used to monitor the referenced object
   * and provides the parameter values needed for this algorithm.
   * @see #eventParameters
   */
  public void setEventParameters(BBacnetEventParameter v) { set(eventParameters, v, null); }

  //endregion Property "eventParameters"

  //region Property "objectPropertyReference"

  /**
   * Slot for the {@code objectPropertyReference} property.
   * @see #getObjectPropertyReference
   * @see #setObjectPropertyReference
   */
  public static final Property objectPropertyReference = newProperty(0, new BBacnetDeviceObjectPropertyReference(), makeFacets(BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code objectPropertyReference} property.
   * @see #objectPropertyReference
   */
  public BBacnetDeviceObjectPropertyReference getObjectPropertyReference() { return (BBacnetDeviceObjectPropertyReference)get(objectPropertyReference); }

  /**
   * Set the {@code objectPropertyReference} property.
   * @see #objectPropertyReference
   */
  public void setObjectPropertyReference(BBacnetDeviceObjectPropertyReference v) { set(objectPropertyReference, v, null); }

  //endregion Property "objectPropertyReference"

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

  //region Property "eventEnable"

  /**
   * Slot for the {@code eventEnable} property.
   * conveys flags that whether notifications are enabled for the various
   * transitions.
   * @see #getEventEnable
   * @see #setEventEnable
   */
  public static final Property eventEnable = newProperty(0, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetEventTransitionBits")), makeFacets(BBacnetPropertyIdentifier.EVENT_ENABLE, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_MAP));

  /**
   * Get the {@code eventEnable} property.
   * conveys flags that whether notifications are enabled for the various
   * transitions.
   * @see #eventEnable
   */
  public BBacnetBitString getEventEnable() { return (BBacnetBitString)get(eventEnable); }

  /**
   * Set the {@code eventEnable} property.
   * conveys flags that whether notifications are enabled for the various
   * transitions.
   * @see #eventEnable
   */
  public void setEventEnable(BBacnetBitString v) { set(eventEnable, v, null); }

  //endregion Property "eventEnable"

  //region Property "ackedTransitions"

  /**
   * Slot for the {@code ackedTransitions} property.
   * indicates whether the last transition of each type has been acknowledged.
   * @see #getAckedTransitions
   * @see #setAckedTransitions
   */
  public static final Property ackedTransitions = newProperty(0, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetEventTransitionBits")), makeFacets(BBacnetPropertyIdentifier.ACKED_TRANSITIONS, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_MAP));

  /**
   * Get the {@code ackedTransitions} property.
   * indicates whether the last transition of each type has been acknowledged.
   * @see #ackedTransitions
   */
  public BBacnetBitString getAckedTransitions() { return (BBacnetBitString)get(ackedTransitions); }

  /**
   * Set the {@code ackedTransitions} property.
   * indicates whether the last transition of each type has been acknowledged.
   * @see #ackedTransitions
   */
  public void setAckedTransitions(BBacnetBitString v) { set(ackedTransitions, v, null); }

  //endregion Property "ackedTransitions"

  //region Property "notificationClass"

  /**
   * Slot for the {@code notificationClass} property.
   * references a Notification Class in the same device that specifies the
   * handling, reporting, and acknowledgment characteristics for this object.
   * @see #getNotificationClass
   * @see #setNotificationClass
   */
  public static final Property notificationClass = newProperty(0, BBacnetUnsigned.make(BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER), makeFacets(BBacnetPropertyIdentifier.NOTIFICATION_CLASS, ASN_UNSIGNED, new String[] { BFacets.MIN, BFacets.MAX }, new BInteger[] { BInteger.make(0), BInteger.make(BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER) } ));

  /**
   * Get the {@code notificationClass} property.
   * references a Notification Class in the same device that specifies the
   * handling, reporting, and acknowledgment characteristics for this object.
   * @see #notificationClass
   */
  public BBacnetUnsigned getNotificationClass() { return (BBacnetUnsigned)get(notificationClass); }

  /**
   * Set the {@code notificationClass} property.
   * references a Notification Class in the same device that specifies the
   * handling, reporting, and acknowledgment characteristics for this object.
   * @see #notificationClass
   */
  public void setNotificationClass(BBacnetUnsigned v) { set(notificationClass, v, null); }

  //endregion Property "notificationClass"

  //region Property "eventTimeStamps"

  /**
   * Slot for the {@code eventTimeStamps} property.
   * contains the times of the last event notifications for each transition type.
   * @see #getEventTimeStamps
   * @see #setEventTimeStamps
   */
  public static final Property eventTimeStamps = newProperty(0, new BBacnetArray(BBacnetTimeStamp.TYPE, 3), makeFacets(BBacnetPropertyIdentifier.EVENT_TIME_STAMPS, ASN_BACNET_ARRAY));

  /**
   * Get the {@code eventTimeStamps} property.
   * contains the times of the last event notifications for each transition type.
   * @see #eventTimeStamps
   */
  public BBacnetArray getEventTimeStamps() { return (BBacnetArray)get(eventTimeStamps); }

  /**
   * Set the {@code eventTimeStamps} property.
   * contains the times of the last event notifications for each transition type.
   * @see #eventTimeStamps
   */
  public void setEventTimeStamps(BBacnetArray v) { set(eventTimeStamps, v, null); }

  //endregion Property "eventTimeStamps"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEventEnrollment.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetEventEnrollment()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context)).append(' ')
      .append(getEventType())
      .append(" monitoring ")
      .append(getObjectPropertyReference().toString(context));
    return sb.toString();
  }

  @Override
  protected void addObjectInitialValues(Array<PropertyValue> listOfInitialValues)
  {
    addNotifyType(getNotifyType(), listOfInitialValues);
    addObjectPropertyReference(getObjectPropertyReference(), listOfInitialValues);
    addEventEnable(getEventEnable(), listOfInitialValues);
    addEventParameter(getEventParameters(), listOfInitialValues);
    addNotificationClass(getNotificationClass(), listOfInitialValues);
  }

  ////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

}
