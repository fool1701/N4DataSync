/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetPropertyValue;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.ErrorException;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.status.BStatus;
import javax.baja.sys.*;
import javax.baja.util.BFormat;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.stack.BBacnetStack;
/**
 * @author    Craig Gemmill
 * @creation  30 Jan 01
 * @version   $Revision: 7$ $Date: 12/10/01 9:26:02 AM$
 * @since     Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.TREND_LOG, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "logEnable",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.ENABLE, ASN_BOOLEAN)")
)
@NiagaraProperty(
  name = "stopWhenFull",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.STOP_WHEN_FULL, ASN_BOOLEAN)")
)
@NiagaraProperty(
  name = "bufferSize",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(60)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.BUFFER_SIZE, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "recordCount",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.RECORD_COUNT, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "totalRecordCount",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT, ASN_UNSIGNED)")
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
 references a Notification Class in the same device that specifies the
 handling, reporting, and acknowledgment characteristics for this object.
 */
@NiagaraProperty(
  name = "notificationClass",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.NOTIFICATION_CLASS, ASN_UNSIGNED, new String[] { BFacets.MIN, BFacets.MAX }, new BInteger[] { BInteger.make(1), BInteger.make(BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER) } )")
)
@NiagaraProperty(
  name = "objectPropertyReference",
  type = "BBacnetDeviceObjectPropertyReference",
  defaultValue = "new BBacnetDeviceObjectPropertyReference()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.LOG_DEVICE_OBJECT_PROPERTY, ASN_CONSTRUCTED_DATA)")
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
public class BBacnetTrendLog extends BBacnetCreatableObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetTrendLog(652498188)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.TREND_LOG, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "logEnable"

  /**
   * Slot for the {@code logEnable} property.
   * @see #getLogEnable
   * @see #setLogEnable
   */
  public static final Property logEnable = newProperty(0, false, makeFacets(BBacnetPropertyIdentifier.ENABLE, ASN_BOOLEAN));

  /**
   * Get the {@code logEnable} property.
   * @see #logEnable
   */
  public boolean getLogEnable() { return getBoolean(logEnable); }

  /**
   * Set the {@code logEnable} property.
   * @see #logEnable
   */
  public void setLogEnable(boolean v) { setBoolean(logEnable, v, null); }

  //endregion Property "logEnable"

  //region Property "stopWhenFull"

  /**
   * Slot for the {@code stopWhenFull} property.
   * @see #getStopWhenFull
   * @see #setStopWhenFull
   */
  public static final Property stopWhenFull = newProperty(0, false, makeFacets(BBacnetPropertyIdentifier.STOP_WHEN_FULL, ASN_BOOLEAN));

  /**
   * Get the {@code stopWhenFull} property.
   * @see #stopWhenFull
   */
  public boolean getStopWhenFull() { return getBoolean(stopWhenFull); }

  /**
   * Set the {@code stopWhenFull} property.
   * @see #stopWhenFull
   */
  public void setStopWhenFull(boolean v) { setBoolean(stopWhenFull, v, null); }

  //endregion Property "stopWhenFull"

  //region Property "bufferSize"

  /**
   * Slot for the {@code bufferSize} property.
   * @see #getBufferSize
   * @see #setBufferSize
   */
  public static final Property bufferSize = newProperty(0, BBacnetUnsigned.make(60), makeFacets(BBacnetPropertyIdentifier.BUFFER_SIZE, ASN_UNSIGNED));

  /**
   * Get the {@code bufferSize} property.
   * @see #bufferSize
   */
  public BBacnetUnsigned getBufferSize() { return (BBacnetUnsigned)get(bufferSize); }

  /**
   * Set the {@code bufferSize} property.
   * @see #bufferSize
   */
  public void setBufferSize(BBacnetUnsigned v) { set(bufferSize, v, null); }

  //endregion Property "bufferSize"

  //region Property "recordCount"

  /**
   * Slot for the {@code recordCount} property.
   * @see #getRecordCount
   * @see #setRecordCount
   */
  public static final Property recordCount = newProperty(0, BBacnetUnsigned.make(0), makeFacets(BBacnetPropertyIdentifier.RECORD_COUNT, ASN_UNSIGNED));

  /**
   * Get the {@code recordCount} property.
   * @see #recordCount
   */
  public BBacnetUnsigned getRecordCount() { return (BBacnetUnsigned)get(recordCount); }

  /**
   * Set the {@code recordCount} property.
   * @see #recordCount
   */
  public void setRecordCount(BBacnetUnsigned v) { set(recordCount, v, null); }

  //endregion Property "recordCount"

  //region Property "totalRecordCount"

  /**
   * Slot for the {@code totalRecordCount} property.
   * @see #getTotalRecordCount
   * @see #setTotalRecordCount
   */
  public static final Property totalRecordCount = newProperty(0, BBacnetUnsigned.make(0), makeFacets(BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT, ASN_UNSIGNED));

  /**
   * Get the {@code totalRecordCount} property.
   * @see #totalRecordCount
   */
  public BBacnetUnsigned getTotalRecordCount() { return (BBacnetUnsigned)get(totalRecordCount); }

  /**
   * Set the {@code totalRecordCount} property.
   * @see #totalRecordCount
   */
  public void setTotalRecordCount(BBacnetUnsigned v) { set(totalRecordCount, v, null); }

  //endregion Property "totalRecordCount"

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

  //region Property "notificationClass"

  /**
   * Slot for the {@code notificationClass} property.
   * references a Notification Class in the same device that specifies the
   * handling, reporting, and acknowledgment characteristics for this object.
   * @see #getNotificationClass
   * @see #setNotificationClass
   */
  public static final Property notificationClass = newProperty(0, BBacnetUnsigned.make(BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER), makeFacets(BBacnetPropertyIdentifier.NOTIFICATION_CLASS, ASN_UNSIGNED, new String[] { BFacets.MIN, BFacets.MAX }, new BInteger[] { BInteger.make(1), BInteger.make(BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER) } ));

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

  //region Property "objectPropertyReference"

  /**
   * Slot for the {@code objectPropertyReference} property.
   * @see #getObjectPropertyReference
   * @see #setObjectPropertyReference
   */
  public static final Property objectPropertyReference = newProperty(0, new BBacnetDeviceObjectPropertyReference(), makeFacets(BBacnetPropertyIdentifier.LOG_DEVICE_OBJECT_PROPERTY, ASN_CONSTRUCTED_DATA));

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTrendLog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final Lexicon lex = Lexicon.make("bacnet");

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetTrendLog() {}


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Should this property ID be polled?
   * Override point for objects to filter properties for polling, e.g.,
   * Object_List in Device object, or Log_Buffer in Trend Log.
   */
  protected boolean shouldPoll(int propertyId)
  {
    switch (propertyId)
    {
      case BBacnetPropertyIdentifier.LOG_BUFFER:
        return false;
    }
    return true;
  }

  /**
   * This code was added to fix a UI issue raised by BTL.
   * The issue is articulated in the Jira entry NCCB-29907.
   */
  @Override
  public String getDisplayName(Slot slot, Context cx)
  {
    if(slot == logEnable)
      return lex.getText("bacnet.trendlog.log.enable.display.name");
    if(slot == totalRecordCount)
      return lex.getText("bacnet.trendlog.log.buffer.display.name");
     return super.getDisplayName(slot, cx);
  }
////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  @Override
  protected void addObjectInitialValues(Array<PropertyValue> listOfInitialValues)
  {
    addNotifyType(getNotifyType(), listOfInitialValues);
    addLogDeviceObjectPropertyReference(getObjectPropertyReference(), listOfInitialValues);
    addNotificationClass(getNotificationClass(), listOfInitialValues);
    listOfInitialValues.add(new BBacnetPropertyValue(BBacnetPropertyIdentifier.BUFFER_SIZE, getBufferSize()));
  }
}
