/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmService;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.BAlarmTimestamps;
import javax.baja.alarm.ext.BFaultAlgorithm;
import javax.baja.alarm.ext.BLimitEnable;
import javax.baja.alarm.ext.BNotifyType;
import javax.baja.alarm.ext.BOffnormalAlgorithm;
import javax.baja.alarm.ext.fault.BEnumFaultAlgorithm;
import javax.baja.alarm.ext.fault.BOutOfRangeFaultAlgorithm;
import javax.baja.alarm.ext.offnormal.BBooleanChangeOfStateAlgorithm;
import javax.baja.alarm.ext.offnormal.BBooleanCommandFailureAlgorithm;
import javax.baja.alarm.ext.offnormal.BEnumChangeOfStateAlgorithm;
import javax.baja.alarm.ext.offnormal.BEnumCommandFailureAlgorithm;
import javax.baja.alarm.ext.offnormal.BFloatingLimitAlgorithm;
import javax.baja.alarm.ext.offnormal.BOutOfRangeAlgorithm;
import javax.baja.alarm.ext.offnormal.BStringChangeOfStateAlgorithm;
import javax.baja.alarm.ext.offnormal.BStringChangeOfStateFaultAlgorithm;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.alarm.BBacnetStatusAlgorithm;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetEventParameter;
import javax.baja.bacnet.datatypes.BBacnetListOf;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetPropertyStates;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetBinaryPv;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.OutOfRangeException;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.point.BBacnetProxyExt;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.control.BBooleanPoint;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BPointExtension;
import javax.baja.control.BStringPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BLink;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.util.BFormat;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyStates;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.history.BBacnetTrendLogAlarmSourceExt;
import com.tridium.bacnet.history.BBacnetTrendLogRemoteExt;
import com.tridium.bacnet.history.BIBacnetTrendLogExt;
import com.tridium.bacnet.services.error.NChangeListError;

/**
 * BBacnetEventEnrollmentDescriptor exposes a Niagara event to Bacnet.
 *
 * @author Sandipan Aich on 5/4/2017
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "eventEnrollmentOrd",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.EVENT_ENROLLMENT)",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "objectName",
  type = "String",
  defaultValue = "",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = "",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "typeOfEvent",
  type = "BBacnetEventType",
  defaultValue = "BBacnetEventType.none",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
@NiagaraProperty(
  name = "notifyTypeId",
  type = "BNotifyType",
  defaultValue = "BNotifyType.alarm",
  flags = Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY
)
@NiagaraProperty(
  name = "objectPropertyReference",
  type = "BBacnetDeviceObjectPropertyReference",
  defaultValue = "new BBacnetDeviceObjectPropertyReference()",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "notificationClassId",
  type = "int",
  defaultValue = "0",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 Reliability of the Event Enrollment object to perform its monitoring function as described in
 135-2012 12.12.21. Does not reflect the reliability of the monitored object or the result of the
 fault algorithm, if one is in use. Those other items are reflected in the BACnet Reliability
 property as read through a BACnet request.
 */
@NiagaraProperty(
  name = "reliability",
  type = "BBacnetReliability",
  defaultValue = "BBacnetReliability.configurationError",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
@NiagaraProperty(
  name = "eventParameter",
  type = "BBacnetEventParameter",
  defaultValue = "new BBacnetEventParameter()",
  flags = Flags.READONLY | Flags.HIDDEN
)
public class BBacnetEventEnrollmentDescriptor
  extends BBacnetEventSource
  implements BacnetPropertyListProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetEventEnrollmentDescriptor(2188437266)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "eventEnrollmentOrd"

  /**
   * Slot for the {@code eventEnrollmentOrd} property.
   * @see #getEventEnrollmentOrd
   * @see #setEventEnrollmentOrd
   */
  public static final Property eventEnrollmentOrd = newProperty(Flags.DEFAULT_ON_CLONE, BOrd.DEFAULT, null);

  /**
   * Get the {@code eventEnrollmentOrd} property.
   * @see #eventEnrollmentOrd
   */
  public BOrd getEventEnrollmentOrd() { return (BOrd)get(eventEnrollmentOrd); }

  /**
   * Set the {@code eventEnrollmentOrd} property.
   * @see #eventEnrollmentOrd
   */
  public void setEventEnrollmentOrd(BOrd v) { set(eventEnrollmentOrd, v, null); }

  //endregion Property "eventEnrollmentOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.EVENT_ENROLLMENT), null);

  /**
   * Get the {@code objectId} property.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "objectName"

  /**
   * Slot for the {@code objectName} property.
   * @see #getObjectName
   * @see #setObjectName
   */
  public static final Property objectName = newProperty(Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code objectName} property.
   * @see #objectName
   */
  public String getObjectName() { return getString(objectName); }

  /**
   * Set the {@code objectName} property.
   * @see #objectName
   */
  public void setObjectName(String v) { setString(objectName, v, null); }

  //endregion Property "objectName"

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Property "typeOfEvent"

  /**
   * Slot for the {@code typeOfEvent} property.
   * @see #getTypeOfEvent
   * @see #setTypeOfEvent
   */
  public static final Property typeOfEvent = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, BBacnetEventType.none, null);

  /**
   * Get the {@code typeOfEvent} property.
   * @see #typeOfEvent
   */
  public BBacnetEventType getTypeOfEvent() { return (BBacnetEventType)get(typeOfEvent); }

  /**
   * Set the {@code typeOfEvent} property.
   * @see #typeOfEvent
   */
  public void setTypeOfEvent(BBacnetEventType v) { set(typeOfEvent, v, null); }

  //endregion Property "typeOfEvent"

  //region Property "notifyTypeId"

  /**
   * Slot for the {@code notifyTypeId} property.
   * @see #getNotifyTypeId
   * @see #setNotifyTypeId
   */
  public static final Property notifyTypeId = newProperty(Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY, BNotifyType.alarm, null);

  /**
   * Get the {@code notifyTypeId} property.
   * @see #notifyTypeId
   */
  public BNotifyType getNotifyTypeId() { return (BNotifyType)get(notifyTypeId); }

  /**
   * Set the {@code notifyTypeId} property.
   * @see #notifyTypeId
   */
  public void setNotifyTypeId(BNotifyType v) { set(notifyTypeId, v, null); }

  //endregion Property "notifyTypeId"

  //region Property "objectPropertyReference"

  /**
   * Slot for the {@code objectPropertyReference} property.
   * @see #getObjectPropertyReference
   * @see #setObjectPropertyReference
   */
  public static final Property objectPropertyReference = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.HIDDEN, new BBacnetDeviceObjectPropertyReference(), null);

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

  //region Property "notificationClassId"

  /**
   * Slot for the {@code notificationClassId} property.
   * @see #getNotificationClassId
   * @see #setNotificationClassId
   */
  public static final Property notificationClassId = newProperty(Flags.DEFAULT_ON_CLONE, 0, null);

  /**
   * Get the {@code notificationClassId} property.
   * @see #notificationClassId
   */
  public int getNotificationClassId() { return getInt(notificationClassId); }

  /**
   * Set the {@code notificationClassId} property.
   * @see #notificationClassId
   */
  public void setNotificationClassId(int v) { setInt(notificationClassId, v, null); }

  //endregion Property "notificationClassId"

  //region Property "reliability"

  /**
   * Slot for the {@code reliability} property.
   * Reliability of the Event Enrollment object to perform its monitoring function as described in
   * 135-2012 12.12.21. Does not reflect the reliability of the monitored object or the result of the
   * fault algorithm, if one is in use. Those other items are reflected in the BACnet Reliability
   * property as read through a BACnet request.
   * @see #getReliability
   * @see #setReliability
   */
  public static final Property reliability = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, BBacnetReliability.configurationError, null);

  /**
   * Get the {@code reliability} property.
   * Reliability of the Event Enrollment object to perform its monitoring function as described in
   * 135-2012 12.12.21. Does not reflect the reliability of the monitored object or the result of the
   * fault algorithm, if one is in use. Those other items are reflected in the BACnet Reliability
   * property as read through a BACnet request.
   * @see #reliability
   */
  public BBacnetReliability getReliability() { return (BBacnetReliability)get(reliability); }

  /**
   * Set the {@code reliability} property.
   * Reliability of the Event Enrollment object to perform its monitoring function as described in
   * 135-2012 12.12.21. Does not reflect the reliability of the monitored object or the result of the
   * fault algorithm, if one is in use. Those other items are reflected in the BACnet Reliability
   * property as read through a BACnet request.
   * @see #reliability
   */
  public void setReliability(BBacnetReliability v) { set(reliability, v, null); }

  //endregion Property "reliability"

  //region Property "eventParameter"

  /**
   * Slot for the {@code eventParameter} property.
   * @see #getEventParameter
   * @see #setEventParameter
   */
  public static final Property eventParameter = newProperty(Flags.READONLY | Flags.HIDDEN, new BBacnetEventParameter(), null);

  /**
   * Get the {@code eventParameter} property.
   * @see #eventParameter
   */
  public BBacnetEventParameter getEventParameter() { return (BBacnetEventParameter)get(eventParameter); }

  /**
   * Set the {@code eventParameter} property.
   * @see #eventParameter
   */
  public void setEventParameter(BBacnetEventParameter v) { set(eventParameter, v, null); }

  //endregion Property "eventParameter"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEventEnrollmentDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  //region BComponent

  @Override
  public void started()
    throws Exception
  {
    super.started();

    // Export the point and initialize the local copies.
    oldId = getObjectId();
    oldName = getObjectName();

    if (Sys.isStationStarted())
    {
      initialize();
      // Increment the Device object's Database_Revision for created object.
      BBacnetNetwork.localDevice().incrementDatabaseRevision();
    }
  }

  @Override
  public void stationStarted() throws Exception
  {
    super.stationStarted();

    // Must wait for stationStarted to ensure that any pointDescriptors referenced by this
    // eventEnrollmentDescriptor are exported first.
    initialize();
  }

  private void initialize()
  {
    BBacnetNetwork.localDevice().export(this);

    BPointExtension pointExt = (BPointExtension) getObject();
    updateEventParameters(pointExt);
    // Initialize the eventEnable, notificationClass, and eventMessageTextsConfig fields based on
    // the associated point ext, if it can be resolved.
    getEventEnable(pointExt);
    getNotificationClass(pointExt);
    updateEventMessageTextsConfig(pointExt);
    updateEventAlgorithmInhibitInfo(pointExt);
  }

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning())
    {
      return;
    }

    if (p.equals(objectId))
    {
      BLocalBacnetDevice local = BBacnetNetwork.localDevice();
      local.unexport(oldId, oldName, this);
      checkConfiguration();
      oldId = getObjectId();

      try
      {
        ((BComponent)getParent()).rename(getPropertyInParent(), getObjectId().toString(nameContext));
      }
      catch (DuplicateSlotException e)
      {
        // ignore this
      }

      if (configOk)
      {
        local.incrementDatabaseRevision();
      }
    }
    else if (p.equals(objectName))
    {
      BLocalBacnetDevice local = BBacnetNetwork.localDevice();
      local.unexport(oldId, oldName, this);
      checkConfiguration();
      oldName = getObjectName();
      if (configOk)
      {
        local.incrementDatabaseRevision();
      }
    }
    else if (p.equals(eventEnrollmentOrd))
    {
      pointExt = null;
      BLocalBacnetDevice local = BBacnetNetwork.localDevice();
      local.exportByOrd(this);
      if (configOk)
      {
        local.incrementDatabaseRevision();
      }
    }
  }

  @Override
  public void stopped()
    throws Exception
  {
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    local.unexport(oldId, oldName, this);
    oldId = null;
    oldName = null;
    pointExt = null;

    // Increment the Device object's Database_Revision for deleted object.
    if (local.isRunning())
    {
      local.incrementDatabaseRevision();
    }

    super.stopped();
  }

  @Override
  public String toString(Context context)
  {
    return getObjectName() + " [" + getObjectId() + ']';
  }

  //endregion

  //region BIBacnetExportObject

  /**
   * Get the {@link BPointExtension} that {@link #eventEnrollmentOrd} points to. The extension's
   * parent should be the same as pointed to by {@link #objectPropertyReference}. Return null if the
   * eventEnrollmentOrd cannot be resolved to a BPointExtension, the extension has no parent, or the
   * extension's parent is not consistent with the objectPropertyReference.
   */
  @Override
  public BObject getObject()
  {
    BPointExtension pointExt = this.pointExt;
    if (pointExt == null)
    {
      pointExt = resolvePointExt();
      if (pointExt == null)
      {
        // Could not resolve the eventEnrollmentOrd
        return null;
      }
    }

    // Check that the extension has a parent
    BComponent target = (BComponent) pointExt.getParent();
    if (target == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": associated PointExt (" + pointExt.getSlotPath() + ") has no parent");
      }
      resetDescriptor();
      return null;
    }

    // Resolved extension is consistent with the objectPropertyReference.
    this.pointExt = pointExt;
    return pointExt;
  }

  /**
   * Resolve and return the point extension pointed to by the eventEnrollmentOrd. Returns null if
   * the ord is not resolvable or resolves to something that is not instanceof BPointExtension.
   */
  private BPointExtension resolvePointExt()
  {
    BOrd objectOrd = getEventEnrollmentOrd();
    if (objectOrd.isNull())
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": eventEnrollmentOrd is null");
      }
      resetDescriptor();
      return null;
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": resolving eventEnrollmentOrd: " + objectOrd);
    }

    BObject resolved;
    try
    {
      resolved = objectOrd.get(this);
    }
    catch (Exception e)
    {
      logException(
        Level.WARNING,
        new StringBuilder(getObjectId().toString())
          .append(": could not resolve eventEnrollmentOrd: ")
          .append(objectOrd),
        e);
      resetDescriptor();
      return null;
    }

    if (resolved instanceof BAlarmSourceExt || resolved instanceof BBacnetTrendLogAlarmSourceExt)
    {
      return (BPointExtension) resolved;
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": eventEnrollmentOrd resolved to type " + resolved.getType() +
        " and not instanceof alarm:AlarmSourceExt or bacnet:BacnetTrendLogAlarmSourceExt");
    }
    resetDescriptor();
    return null;
  }

  /**
   * Get {@link #eventEnrollmentOrd}.
   */
  @Override
  public BOrd getObjectOrd()
  {
    return getEventEnrollmentOrd();
  }

  /**
   * Set {@link #eventEnrollmentOrd}.
   */
  @Override
  public void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(eventEnrollmentOrd, objectOrd, cx);
  }

  @Override
  public void checkConfiguration()
  {
    // quit if fatal fault
    if (isFatalFault())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      configOk = false;
      return;
    }

    if (!getObjectId().isValid())
    {
      setStatusFaulted("Invalid Object ID");
      return;
    }

    String err = BBacnetNetwork.localDevice().export(this);
    if (err != null)
    {
      duplicate = true;
      setStatusFaulted(err);
      return;
    }

    duplicate = false;
    configOk = true;
    setStatus(BStatus.ok);
    setFaultCause("");
  }

  private void setStatusFaulted(String faultCause)
  {
    setStatus(BStatus.makeFault(getStatus(), true));
    setFaultCause(faultCause);
    configOk = false;
  }

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, OPTIONAL_PROPS);
  }

  @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
  public int[] getOptionalProps()
  {
    return OPTIONAL_PROPS;
  }

  @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
  public int[] getRequiredProps()
  {
    return REQUIRED_PROPS;
  }

  @Override
  public PropertyValue readProperty(PropertyReference propertyReference)
    throws RejectException
  {
    return readProperty(propertyReference.getPropertyId(), propertyReference.getPropertyArrayIndex());
  }

  @Override
  public PropertyValue[] readPropertyMultiple(PropertyReference[] propertyReferences) throws RejectException
  {
    ArrayList<PropertyValue> results = new ArrayList<>(propertyReferences.length);
    for (PropertyReference ref : propertyReferences)
    {
      switch (ref.getPropertyId())
      {
        case BBacnetPropertyIdentifier.ALL:
          for (int prop : REQUIRED_PROPS)
          {
            results.add(readProperty(prop, NOT_USED));
          }
          for (int prop : OPTIONAL_PROPS)
          {
            results.add(readProperty(prop, NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.OPTIONAL:
          for (int prop : OPTIONAL_PROPS)
          {
            results.add(readProperty(prop, NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.REQUIRED:
          for (int prop : REQUIRED_PROPS)
          {
            results.add(readProperty(prop, NOT_USED));
          }
          break;

        default:
          results.add(readProperty(ref.getPropertyId(), ref.getPropertyArrayIndex()));
          break;
      }
    }

    return results.toArray(EMPTY_PROP_VALUE_ARRAY);
  }

  @Override
  public RangeData readRange(RangeReference rangeReference) throws RejectException
  {
    return null;
  }

  @Override
  public ErrorType writeProperty(PropertyValue propertyValue) throws BacnetException
  {
    return writeProperty(
      propertyValue.getPropertyId(),
      propertyValue.getPropertyArrayIndex(),
      propertyValue.getPropertyValue(),
      propertyValue.getPriority());
  }

  @Override
  public ChangeListError addListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    return handleChangeList(propertyValue.getPropertyId(), BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT);
  }

  @Override
  public ChangeListError removeListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    return handleChangeList(propertyValue.getPropertyId(), BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT);
  }

  private static ChangeListError handleChangeList(int propertyId, int serviceChoice)
  {
    for (int prop : REQUIRED_PROPS)
    {
      if (propertyId == prop)
      {
        return new NChangeListError(
          serviceChoice,
          new NErrorType(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
          0);
      }
    }

    for (int prop : OPTIONAL_PROPS)
    {
      if (propertyId == prop)
      {
        return new NChangeListError(
          serviceChoice,
          new NErrorType(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
          0);
      }
    }

    return new NChangeListError(
      serviceChoice,
      new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY),
      0);
  }

  //endregion

  //region BBacnetEventSource

  /**
   * Get the event state associated with the alarm source extension
   */
  @Override
  public BBacnetEventState getEventState()
  {
    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt instanceof BAlarmSourceExt)
    {
      return BBacnetEventState.make(((BAlarmSourceExt) pointExt).getAlarmState());
    }

    if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      return BBacnetEventState.make(((BBacnetTrendLogAlarmSourceExt) pointExt).getAlarmState());
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
        ") is not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt; returning null for event state");
    }

    return null;
  }

  // TODO Remove and replace with getEventState()?  Or, keep and ensure it will work with null returns?
  private BBacnetEventState getEventState(BPointExtension pointExt)
  {
    if (pointExt instanceof BAlarmSourceExt)
    {
      return BBacnetEventState.make(((BAlarmSourceExt) pointExt).getAlarmState());
    }

    if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      return BBacnetEventState.make(((BBacnetTrendLogAlarmSourceExt) pointExt).getAlarmState());
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
        ") is not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt; returning BacnetEventState.normal");
    }
    return BBacnetEventState.normal;
  }

  /**
   * Get the parent point of the BPointExtension returned by {@link #getObject()} if that extension
   * is instanceof BAlarmSourceExt. Otherwise, return null if getObject() returns null or the
   * extension is not instanceof BAlarmSourceExt.
   */
  @Override
  public BControlPoint getPoint()
  {
    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt instanceof BAlarmSourceExt)
    {
      return pointExt.getParentPoint();
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
        ") is not an AlarmSourceExt; returning null for getPoint");
    }
    return null;
  }

  /**
   * This method returns three flags that separately indicate the acknowledgment state for
   * TO_OFFNORMAL, TO_FAULT, and TO_NORMAL events
   */
  @Override
  public BBacnetBitString getAckedTransitions()
  {
    BAlarmTransitionBits ackedTransitions = getAckedTransitions((BPointExtension) getObject());
    if (ackedTransitions == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
          ") is not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt; returning default ackedTransitions value");
      }
      return null;
    }

    return BacnetBitStringUtil.getBacnetEventTransitionBits(ackedTransitions);
  }

  private static BAlarmTransitionBits getAckedTransitions(BPointExtension pointExt)
  {
    if (pointExt instanceof BAlarmSourceExt)
    {
      return ((BAlarmSourceExt) pointExt).getAckedTransitions();
    }

    if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      return ((BBacnetTrendLogAlarmSourceExt) pointExt).getAckedTransitions();
    }

    return null;
  }

  @Override
  public BBacnetTimeStamp[] getEventTimeStamps()
  {
    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt instanceof BAlarmSourceExt)
    {
      BAlarmSourceExt alarmExt = (BAlarmSourceExt) pointExt;
      return new BBacnetTimeStamp[] {
        new BBacnetTimeStamp(alarmExt.getLastOffnormalTime()),
        new BBacnetTimeStamp(alarmExt.getLastFaultTime()),
        new BBacnetTimeStamp(alarmExt.getLastToNormalTime())
      };
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      BBacnetTrendLogAlarmSourceExt trendAlarmExt = (BBacnetTrendLogAlarmSourceExt) pointExt;
      BAlarmTimestamps toOffnormalTimes = trendAlarmExt.getToOffnormalTimes();
      BAlarmTimestamps toFaultTimes = trendAlarmExt.getToFaultTimes();

      BAbsTime normalTime = toOffnormalTimes.getNormalTime();
      if (normalTime.isBefore(toFaultTimes.getNormalTime()))
      {
        normalTime = toFaultTimes.getNormalTime();
      }

      return new BBacnetTimeStamp[] {
        new BBacnetTimeStamp(toOffnormalTimes.getAlarmTime()),
        new BBacnetTimeStamp(toFaultTimes.getAlarmTime()),
        new BBacnetTimeStamp(normalTime)
      };
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
          ") is not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt; returning default eventTimeStamps");
      }

      // TODO Ensure this is compatible with all calls to this method.
      return null;
    }
  }

  @Override
  public BBacnetNotifyType getNotifyType()
  {
    return BBacnetNotifyType.make(getNotifyTypeId());
  }

  @Override
  public BBacnetBitString getEventEnable()
  {
    BPointExtension pointExt = (BPointExtension) getObject();
    BAlarmTransitionBits alarmEnable = getEventEnable(pointExt);
    return alarmEnable != null ? BacnetBitStringUtil.getBacnetEventTransitionBits(alarmEnable) : null;
  }

  private BAlarmTransitionBits getEventEnable(BPointExtension pointExt)
  {
    BAlarmTransitionBits alarmEnable = null;
    if (pointExt instanceof BAlarmSourceExt)
    {
      alarmEnable = ((BAlarmSourceExt) pointExt).getAlarmEnable();
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      alarmEnable = ((BBacnetTrendLogAlarmSourceExt) pointExt).getAlarmEnable();
    }

    if (alarmEnable != null)
    {
      eventEnable = alarmEnable;
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
          ") is not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt");
      }
    }

    return alarmEnable;
  }

  @Override
  public BEnum getEventType()
  {
    updateEventParameters((BPointExtension) getObject());
    return getTypeOfEvent();
  }

  @Override
  public void statusChanged()
  {
    setBacnetStatusFlags(getStatusFlags());
  }

  /**
   *  This method represents four Boolean flags that indicate the general "health" of an object.
   *  These properties are :- <br>
   *  <ul>
   *    <li> IN_ALARM : Logical FALSE (0) if the Event_State property has a value of NORMAL, otherwise logical TRUE (1) </li>
   *    <li> FAULT : Logical TRUE (1) if the Reliability property is present and does not have a value of NO_FAULT_DETECTED, otherwise logical FALSE (0) </li>
   *    <li> OVERRIDDEN : Logical FALSE (0) </li>
   *    <li> OUT_OF_SERVICE : Logical FALSE (0) </li>
   *  </ul>
   * @return status Flags' BitString
   */
  public BBacnetBitString getStatusFlags()
  {
    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
          ") is not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt; returning default status flags");
      }
      return STATUS_FLAGS_DEFAULT;
    }

    // TODO What about when event state returns null?
    return BBacnetBitString.make(new boolean[] {
      /* inAlarm */ !BBacnetEventState.isNormal(getEventState(pointExt)),
      /* inFault */ !readReliability().equals(BBacnetReliability.noFaultDetected),
      /* isOverridden */ false,
      /* isOutOfService */ false });
  }

  @Override
  public boolean isValidAlarmExt(BIAlarmSource ext)
  {
    BPointExtension pointExt = (BPointExtension) getObject();
    // TODO Check the offnormal algorithm of the BAlarmSourceExt
    return pointExt instanceof BAlarmSourceExt || pointExt instanceof BBacnetTrendLogAlarmSourceExt;
  }

  @Override
  public boolean isEventInitiationEnabled()
  {
    return true;
  }

  @Override
  public int[] getEventPriorities()
  {
    BBacnetNotificationClassDescriptor nc = getNotificationClass();
    return nc != null ? nc.getEventPriorities() : null;
  }

  @Override
  public BBacnetNotificationClassDescriptor getNotificationClass()
  {
    return getNotificationClass((BPointExtension) getObject());
  }

  private BBacnetNotificationClassDescriptor getNotificationClass(BPointExtension pointExt)
  {
    String alarmClassName = getAlarmClassName(pointExt);
    if (alarmClassName == null)
    {
      // No associated point extension so no alarm class to correlate
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": cannot retrieve the notification class descriptor-" +
          " associated PointExt (" + (pointExt != null ? pointExt.getSlotPath() : null) +
          ") is not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt");
      }
      return null;
    }

    if (alarmClassName.isEmpty())
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": pointExt's alarmClassName is an empty string;" +
          " setting notification class ID to unconfigured instance number");
      }
      setNotificationClassId(BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER);
      setReliability(BBacnetReliability.configurationError);
      return null;
    }

    BBacnetNotificationClassDescriptor descriptor = findNotificationClass(alarmClassName);
    if (descriptor == null)
    {
      setReliability(BBacnetReliability.configurationError);
      return null;
    }

    setNotificationClassId(descriptor.getObjectId().getInstanceNumber());
    return descriptor;
  }

  private BBacnetNotificationClassDescriptor findNotificationClass(String alarmClassName)
  {
    BAlarmService alarmService;
    try
    {
      alarmService = (BAlarmService) Sys.getService(BAlarmService.TYPE);
    }
    catch (ServiceNotFoundException e)
    {
      logException(
        Level.WARNING,
        new StringBuilder(getObjectId().toString())
          .append(": getNotificationClass: could not find the alarm service"),
        e);
      return null;
    }

    BAlarmClass alarmClass = alarmService.lookupAlarmClass(alarmClassName);
    if (alarmClass == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": getNotificationClass:" +
          " could not find alarm class " + alarmClassName + " in the alarm service");
      }
      return null;
    }

    BIBacnetExportObject descriptor = findDescriptor(alarmClass.getHandleOrd());
    if (descriptor instanceof BBacnetNotificationClassDescriptor)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": getNotificationClass:" +
          " found new notification class descriptor for alarm class " + alarmClassName);
      }
      return (BBacnetNotificationClassDescriptor) descriptor;
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": getNotificationClass:" +
          " could not find a notification class descriptor for alarmClass \"" + alarmClass.getSlotPath() + '\"');
      }
      return null;
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  @Deprecated
  protected void updateAlarmInhibit()
  {
  }

  //endregion

  //region Read Property

  /**
   * Get the value of a property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing either the encoded value or the error.
   */
  protected PropertyValue readProperty(int pId, int ndx)
  {
    if (ndx >= 0 && !isArray(pId))
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY));
    }

    if (ndx < NOT_USED)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }

    switch (pId)
    {
      case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnObjectId(getObjectId()));
      case BBacnetPropertyIdentifier.OBJECT_NAME:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getObjectName()));
      case BBacnetPropertyIdentifier.OBJECT_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetObjectType.EVENT_ENROLLMENT));
      case BBacnetPropertyIdentifier.EVENT_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getEventType()));
      case BBacnetPropertyIdentifier.NOTIFY_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BNotifyType.ALARM));
      case BBacnetPropertyIdentifier.EVENT_PARAMETERS:
        return readEventParameters();
      case BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE:
        return readObjectPropertyReference();
      case BBacnetPropertyIdentifier.EVENT_STATE:
        return readEventState();
      case BBacnetPropertyIdentifier.EVENT_ENABLE:
        return readEventEnable();
      case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
        return readAckedTransitions();
      case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
        getNotificationClass();
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(getNotificationClassId()));
      case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
        return readEventTimeStamps(ndx);
      case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getEventDetectionEnable()));
      case BBacnetPropertyIdentifier.STATUS_FLAGS:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBitString(getStatusFlags()));
      case BBacnetPropertyIdentifier.RELIABILITY:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(readReliability()));
      case BBacnetPropertyIdentifier.PROPERTY_LIST:
        return readPropertyList(ndx);
      default:
        return readOptionalProperty(pId, ndx);
    }
  }

  private static String getAlarmClassName(BPointExtension pointExt)
  {
    if (pointExt instanceof BAlarmSourceExt)
    {
      return ((BAlarmSourceExt) pointExt).getAlarmClass();
    }

    if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      return ((BBacnetTrendLogAlarmSourceExt) pointExt).getAlarmClass();
    }

    return null;
  }

  private PropertyValue readEventParameters()
  {
    try
    {
      BBacnetEventParameter params = readEventParameters((BPointExtension) getObject());
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_PARAMETERS,
        AsnUtil.toAsn(BacnetConst.ASN_ANY, params));
    }
    catch (EventEnrollmentException e)
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_PARAMETERS, e.errorType);
    }
  }

  private PropertyValue readObjectPropertyReference()
  {
    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": target alarm ext not configured; falling back to cached objectPropertyReference value");
      }
      return makeObjPropRefPropValue(getObjectPropertyReference());
    }

    BComponent target = (BComponent) pointExt.getParent();
    if (target == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": target alarm ext has no parent");
      }
      return makeObjPropRefError();
    }

    return getTargetObjPropRef(target);
  }

  private PropertyValue getTargetObjPropRef(BComponent target)
  {
    if (target instanceof BControlPoint)
    {
      return getPointPropRef((BControlPoint) target);
    }
    else if (target instanceof BIBacnetTrendLogExt)
    {
      return getTrendPropRef((BIBacnetTrendLogExt) target);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": target alarm ext has no parent");
      }
      return makeObjPropRefError();
    }
  }

  private PropertyValue getPointPropRef(BControlPoint point)
  {
    BAbstractProxyExt proxyExt = point.getProxyExt();
    if (proxyExt instanceof BBacnetProxyExt)
    {
      return makeObjPropRefPropValue(makeRemoteDeviceObjPropRef((BBacnetProxyExt) proxyExt));
    }
    else
    {
      // Must be a local object
      BIBacnetExportObject descriptor = findDescriptor(point.getHandleOrd());
      if (descriptor == null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": alarm ext's target's descriptor not found in local device;" +
            " target slot path: " + point.getSlotPath());
        }
        return makeObjPropRefError();
      }

      return makeObjPropRefPropValue(makeLocalDeviceObjPropRef(descriptor.getObjectId()));
    }
  }

  private BIBacnetExportObject findDescriptor(BOrd ord)
  {
    BBacnetObjectIdentifier objectId = BBacnetNetwork.localDevice().lookupBacnetObjectId(ord);
    if (objectId == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": could not find object ID for ord \"" + ord + '\"');
      }
      return null;
    }

    BIBacnetExportObject descriptor = BBacnetNetwork.localDevice().lookupBacnetObject(objectId);
    if (descriptor == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": found object ID \"" + objectId + "\" for ord \"" + ord +
          "\" but could not find descriptor");
      }
      return null;
    }

    return descriptor;
  }

  private PropertyValue getTrendPropRef(BIBacnetTrendLogExt trendLogExt)
  {
    if (trendLogExt instanceof BBacnetTrendLogRemoteExt)
    {
      BBacnetTrendLogRemoteExt trendLogRemoteExt = (BBacnetTrendLogRemoteExt) trendLogExt;
      BBacnetDeviceObjectPropertyReference deviceObjPropRef = new BBacnetDeviceObjectPropertyReference(
        trendLogRemoteExt.getObjectId(),
        trendLogRemoteExt.getPropertyId(),
        trendLogRemoteExt.getArrayIndex(),
        trendLogRemoteExt.getDevice().getObjectId());
      return makeObjPropRefPropValue(deviceObjPropRef);
    }
    else
    {
      BIBacnetExportObject descriptor = findDescriptor(((BComponent) trendLogExt).getHandleOrd());
      if (descriptor == null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": alarm ext's target's descriptor not found in local device;" +
            " target slot path: " + ((BComponent) trendLogExt).getSlotPath());
        }
        return makeObjPropRefError();
      }

      return makeObjPropRefPropValue(makeLocalDeviceObjPropRef(descriptor.getObjectId()));
    }
  }

  private static PropertyValue makeObjPropRefPropValue(BBacnetDeviceObjectPropertyReference objPropRef)
  {
    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE,
      BacnetConst.NOT_USED,
      AsnUtil.toAsn(BacnetConst.ASN_ANY, objPropRef));
  }

  private static PropertyValue makeObjPropRefError()
  {
    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE,
      new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER));
  }

  private PropertyValue readEventState()
  {
    BBacnetEventState eventState = null;
    if (getEventDetectionEnable())
    {
      eventState = getEventState();
    }

    if (eventState == null)
    {
      eventState = BBacnetEventState.normal;
    }

    return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_STATE, NOT_USED, AsnUtil.toAsnEnumerated(eventState));
  }

  private PropertyValue readEventEnable()
  {
    BBacnetBitString eventEnable = getEventEnable();
    if (eventEnable == null)
    {
      eventEnable = BacnetBitStringUtil.getBacnetEventTransitionBits(this.eventEnable);
    }

    return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_ENABLE, NOT_USED, AsnUtil.toAsnBitString(eventEnable));
  }

  private PropertyValue readAckedTransitions()
  {
    if (!getEventDetectionEnable())
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.ACKED_TRANSITIONS, NOT_USED, AsnUtil.toAsnBitString(ACKED_TRANS_DEFAULT));
    }

    BAlarmTransitionBits ackedTrans = getAckedTransitions((BPointExtension) getObject());
    if (ackedTrans == null)
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.ACKED_TRANSITIONS, NOT_USED, AsnUtil.toAsnBitString(ACKED_TRANS_DEFAULT));
    }

    BAlarmTransitionBits eventTrans = readEventTransition(ackedTrans);
    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.ACKED_TRANSITIONS,
      NOT_USED,
      AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(eventTrans)));
  }

  /**
   * Read the value of an optional property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing either the encoded value or the error.
   */
  protected PropertyValue readOptionalProperty(int pId, int ndx)
  {
    switch (pId)
    {
      case BBacnetPropertyIdentifier.DESCRIPTION:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getDescription()));
      case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
        return readEventMessageTexts(ndx);
      case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
        return readEventMessageTextsConfig(ndx);
      case BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT:
        return readEventAlgorithmInhibit();
      case BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT_REF:
        return readEventAlgorithmInhibitRef();
    }

    return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY));
  }

  private PropertyValue readEventMessageTextsConfig(int ndx)
  {
    updateEventMessageTextsConfig((BPointExtension) getObject());
    return readEventMessageTextsConfig(
      toOffnormalText,
      toFaultText,
      toNormalText,
      ndx);
  }

  private void updateEventMessageTextsConfig(BPointExtension pointExt)
  {
    if (pointExt instanceof BAlarmSourceExt)
    {
      BAlarmSourceExt alarmExt = (BAlarmSourceExt) pointExt;
      toOffnormalText = alarmExt.getToOffnormalText().getFormat();
      toFaultText = alarmExt.getToFaultText().getFormat();
      toNormalText = alarmExt.getToNormalText().getFormat();
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      BBacnetTrendLogAlarmSourceExt trendAlarmExt = (BBacnetTrendLogAlarmSourceExt) pointExt;
      toOffnormalText = trendAlarmExt.getToOffnormalText().getFormat();
      toFaultText = trendAlarmExt.getToFaultText().getFormat();
      toNormalText = trendAlarmExt.getToNormalText().getFormat();
    }
  }

  private void updateEventParameters(BPointExtension pointExt)
  {
    try
    {
      readEventParameters(pointExt);
    }
    catch (EventEnrollmentException e)
    {
      logger.log(Level.FINE, getObjectId() + ": exception while updating event parameters", e);
    }
  }

  private BBacnetEventParameter readEventParameters(BPointExtension pointExt)
    throws EventEnrollmentException
  {
    if (pointExt == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": target alarm ext not configured; falling back to cached eventParameters value");
      }
      return getEventParameter();
    }

    // Extension exists- base the event parameters on the alarm extension's properties
    BBacnetEventParameter eventParam;

    if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      BBacnetTrendLogAlarmSourceExt trendAlarmExt = (BBacnetTrendLogAlarmSourceExt) pointExt;
      eventParam = BBacnetEventParameter.makeBufferReady(
        trendAlarmExt.getNotificationThreshold(),
        trendAlarmExt.getLastNotifyRecord());
    }
    else if (pointExt instanceof BAlarmSourceExt)
    {
      BAlarmSourceExt alarmExt = (BAlarmSourceExt) pointExt;
      BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
      if (offnormalAlgorithm instanceof BBooleanChangeOfStateAlgorithm)
      {
        eventParam = BBacnetEventParameter.makeChangeOfState(
          alarmExt.getTimeDelay(),
          getListOfValues((BBooleanChangeOfStateAlgorithm) offnormalAlgorithm));
      }
      else if (offnormalAlgorithm instanceof BEnumChangeOfStateAlgorithm)
      {
        eventParam = BBacnetEventParameter.makeChangeOfState(
          alarmExt.getTimeDelay(),
          getListOfValues((BEnumChangeOfStateAlgorithm) offnormalAlgorithm));
      }
      else if (offnormalAlgorithm instanceof BStringChangeOfStateAlgorithm)
      {
        eventParam = BBacnetEventParameter.makeChangeOfCharacterString(
          alarmExt.getTimeDelay(),
          getListOfValues((BStringChangeOfStateAlgorithm) offnormalAlgorithm));
      }
      else if (offnormalAlgorithm instanceof BBooleanCommandFailureAlgorithm)
      {
        eventParam = BBacnetEventParameter.makeCommandFailure(
          alarmExt.getTimeDelay(),
          getLinkedPropertyReference(
            offnormalAlgorithm,
            BBooleanCommandFailureAlgorithm.feedbackValue,
            BBacnetBinaryPointDescriptor.TYPE));
      }
      else if (offnormalAlgorithm instanceof BEnumCommandFailureAlgorithm)
      {
        eventParam = BBacnetEventParameter.makeCommandFailure(
          alarmExt.getTimeDelay(),
          getLinkedPropertyReference(
            offnormalAlgorithm,
            BEnumCommandFailureAlgorithm.feedbackValue,
            BBacnetMultiStatePointDescriptor.TYPE));
      }
      else if (offnormalAlgorithm instanceof BFloatingLimitAlgorithm)
      {
        BFloatingLimitAlgorithm floatingLimitAlgorithm = (BFloatingLimitAlgorithm) offnormalAlgorithm;
        eventParam = BBacnetEventParameter.makeFloatingLimit(
          alarmExt.getTimeDelay(),
          getLinkedPropertyReference(
            offnormalAlgorithm,
            BFloatingLimitAlgorithm.setpoint,
            BBacnetAnalogPointDescriptor.TYPE),
          (float) floatingLimitAlgorithm.getLowDiffLimit(),
          (float) floatingLimitAlgorithm.getHighDiffLimit(),
          (float) floatingLimitAlgorithm.getDeadband());
      }
      else if (offnormalAlgorithm instanceof BOutOfRangeAlgorithm)
      {
        BOutOfRangeAlgorithm outOfRangeAlgorithm = (BOutOfRangeAlgorithm) offnormalAlgorithm;
        eventParam = BBacnetEventParameter.makeOutOfRange(
          alarmExt.getTimeDelay(),
          (float) outOfRangeAlgorithm.getLowLimit(),
          (float) outOfRangeAlgorithm.getHighLimit(),
          (float) outOfRangeAlgorithm.getDeadband());
      }
      else
      {
        logger.warning(getObjectId() + ": could not construct EventParameters for BAlarmExt offnormalAlgorithm of type " +
          offnormalAlgorithm.getType());
        throw new EventEnrollmentException(
          "alarmExt offnormal algorithm type " + offnormalAlgorithm.getType() + " not supported",
          new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER));
      }
    }
    else
    {
      logger.warning(getObjectId() + ": could not construct EventParameters for BPointExtension of type " +
        pointExt.getType());
      throw new EventEnrollmentException(
        "pointExt type " + pointExt.getType() + " not supported",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER));
    }

    setEventParameter(eventParam);
    setTypeOfEvent(BBacnetEventType.make(eventParam.getChoice()));
    return eventParam;
  }

  private static BBacnetListOf getListOfValues(BBooleanChangeOfStateAlgorithm offnormalAlgorithm)
  {
    // TODO How to differentiate between BOOLEAN and BACnetBinaryPv?
    BBacnetPropertyStates element = BBacnetPropertyStates.makeBinaryPv(offnormalAlgorithm.getAlarmValue());

    BBacnetListOf listOfValues = new BBacnetListOf(BBacnetPropertyStates.TYPE);
    listOfValues.addListElement(element, null);
    return listOfValues;
  }

  private static BBacnetListOf getListOfValues(BEnumChangeOfStateAlgorithm offnormalAlgorithm)
  {
    BBacnetListOf listOfValues = new BBacnetListOf(BBacnetPropertyStates.TYPE);

    BEnumRange alarmValues = offnormalAlgorithm.getAlarmValues();
    for (int ordinal : alarmValues.getOrdinals())
    {
      listOfValues.addListElement(BBacnetPropertyStates.makeUnsigned(ordinal), null);
    }

    return listOfValues;
  }

  private static BBacnetListOf getListOfValues(BStringChangeOfStateAlgorithm offnormalAlgorithm)
  {
    BBacnetListOf listOfValues = new BBacnetListOf(BString.TYPE);
    listOfValues.addListElement(offnormalAlgorithm.get(BStringChangeOfStateAlgorithm.expression), null);
    return listOfValues;
  }

  private PropertyValue readEventTimeStamps(int ndx)
  {
    BPointExtension pointExt = (BPointExtension) getObject();

    BAbsTime lastOffnormalTime = BAbsTime.DEFAULT;
    BAbsTime lastFaultTime = BAbsTime.DEFAULT;
    BAbsTime lastToNormalTime = BAbsTime.DEFAULT;
    if (pointExt instanceof BAlarmSourceExt)
    {
      BAlarmSourceExt alarmExt = (BAlarmSourceExt) pointExt;
      lastOffnormalTime = alarmExt.getLastOffnormalTime();
      lastFaultTime = alarmExt.getLastFaultTime();
      lastToNormalTime = alarmExt.getLastToNormalTime();
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      BBacnetTrendLogAlarmSourceExt trendAlarmExt = (BBacnetTrendLogAlarmSourceExt) pointExt;
      BAlarmTimestamps toOffnormalTimes = trendAlarmExt.getToOffnormalTimes();
      BAlarmTimestamps toFaultTimes = trendAlarmExt.getToFaultTimes();

      lastOffnormalTime = toOffnormalTimes.getAlarmTime();
      lastFaultTime = toFaultTimes.getAlarmTime();

      lastToNormalTime = toOffnormalTimes.getNormalTime();
      if (lastToNormalTime.isBefore(toFaultTimes.getNormalTime()))
      {
        lastToNormalTime = toFaultTimes.getNormalTime();
      }
    }

    return readEventTimeStamps(lastOffnormalTime, lastFaultTime, lastToNormalTime, ndx);
  }

  private BBacnetReliability readReliability()
  {
    BBacnetReliability eventEnrollmentReliability = getReliability();
    if (!eventEnrollmentReliability.equals(BBacnetReliability.noFaultDetected))
    {
      return eventEnrollmentReliability;
    }

    BPointExtension target = (BPointExtension) getObject();
    BComplex parent = target != null ? target.getParent() : null;
    if (parent == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": reliability set to configurationError because target could not be resolved");
      }
      setReliability(BBacnetReliability.configurationError);
      return BBacnetReliability.configurationError;
    }

    if (!(parent instanceof BIStatus))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": resolved target is type " + parent.getType()
          + " and not instanceof BIStatus; returning noFaultDetected as reliability value");
      }
      return BBacnetReliability.noFaultDetected;
    }

    BStatus parentStatus = ((BIStatus) parent).getStatus();
    if (parentStatus.isNull())
    {
      return BBacnetReliability.unreliableOther;
    }

    if (parentStatus.isDown() || parentStatus.isStale())
    {
      return BBacnetReliability.communicationFailure;
    }

    if (parentStatus.isFault())
    {
      return BBacnetReliability.monitoredObjectFault;
    }

    // TODO Include fault algorithm?

    return BBacnetReliability.noFaultDetected;
  }

  private PropertyValue readEventAlgorithmInhibit()
  {
    updateEventAlgorithmInhibitInfo((BPointExtension) getObject());
    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT,
      BacnetConst.NOT_USED,
      AsnUtil.toAsnBoolean(eventAlgorithmInhibit));
  }

  private PropertyValue readEventAlgorithmInhibitRef()
  {
    updateEventAlgorithmInhibitInfo((BPointExtension) getObject());
    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT_REF,
      BacnetConst.NOT_USED,
      AsnUtil.toAsn(BacnetConst.ASN_ANY, eventAlgorithmInhibitRef));
  }

  private void updateEventAlgorithmInhibitInfo(BPointExtension pointExt)
  {
    if (pointExt instanceof BAlarmSourceExt)
    {
      eventAlgorithmInhibit = ((BAlarmSourceExt) pointExt).getAlarmInhibit().getBoolean();
      updateAlarmInhibitRef(pointExt.getLinks(BAlarmSourceExt.alarmInhibit));
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      eventAlgorithmInhibit = ((BBacnetTrendLogAlarmSourceExt) pointExt).getAlarmInhibit().getBoolean();
      updateAlarmInhibitRef(pointExt.getLinks(BBacnetTrendLogAlarmSourceExt.alarmInhibit));
    }
  }

  private void updateAlarmInhibitRef(BLink[] links)
  {
    for (BLink link : links)
    {
      if (!link.isActive() || !link.isEnabled())
      {
        continue;
      }

      BComponent source = link.getSourceComponent();
      if (source instanceof BBooleanPoint)
      {
        // TODO Check that descriptor object type is valid binary type
        BIBacnetExportObject descriptor = findDescriptor(source.getHandleOrd());
        if (descriptor != null)
        {
          BBacnetObjectPropertyReference newValue = new BBacnetObjectPropertyReference(descriptor.getObjectId());
          if (logger.isLoggable(Level.FINE) && eventAlgorithmInhibitRef.getObjectId().getInstanceNumber() == BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
          {
            logger.fine(getObjectId() + ": updating unconfigured eventAlgorithmInhibitRef because" +
              " there is a valid link to alarmInhibitRef; new value: " + newValue);
          }
          eventAlgorithmInhibitRef = newValue;
          return;
        }
      }
    }

    if (logger.isLoggable(Level.FINE) && eventAlgorithmInhibitRef.getObjectId().getInstanceNumber() != BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
    {
      logger.fine(getObjectId() + ": setting eventAlgorithmInhibitRef as unconfigured because there" +
        " are no valid links to alarmInhibitRef; old value: " + eventAlgorithmInhibitRef);
    }
    eventAlgorithmInhibitRef = OBJECT_PROP_REF_DEFAULT;
  }

  //endregion

  //region Write Property

  @SuppressWarnings("unused")
  protected ErrorType writeProperty(int pId, int ndx, byte[] val, int pri)
  {
    if (ndx >= 0)
    {
      if (!isArray(pId))
      {
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }
    else if (ndx < NOT_USED)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX);
    }

    try
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        case BBacnetPropertyIdentifier.OBJECT_TYPE:
        case BBacnetPropertyIdentifier.EVENT_TYPE:
        case BBacnetPropertyIdentifier.EVENT_STATE:
        case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
        case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
        case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
        case BBacnetPropertyIdentifier.STATUS_FLAGS:
        case BBacnetPropertyIdentifier.RELIABILITY:
        case BBacnetPropertyIdentifier.PROPERTY_LIST:
          if (logger.isLoggable(Level.FINE))
          {
            logger.fine(getObjectId() + ": attempted to write read-only property " + BBacnetPropertyIdentifier.tag(pId));
          }
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.OBJECT_NAME:
          return BacUtil.setObjectName(this, objectName, val);
        case BBacnetPropertyIdentifier.DESCRIPTION:
          setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.NOTIFY_TYPE:
          return writeNotifyType(val);
        case BBacnetPropertyIdentifier.EVENT_PARAMETERS:
          return writeEventParameters(val);
        case BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE:
          return writeObjectPropertyReference(val);
        case BBacnetPropertyIdentifier.EVENT_ENABLE:
          return writeEventEnable(val);
        case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
          return writeNotificationClass(val);
        case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
          setBoolean(BBacnetEventSource.eventDetectionEnable, AsnUtil.fromAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());
          return null;
        case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
          return writeMessageTextsConfig(ndx, val);
        case BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT:
          return writeEventAlgorithmInhibit(val);
        case BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT_REF:
          return writeEventAlgorithmInhibitRef(val);
        default:
          if (logger.isLoggable(Level.FINE))
          {
            logger.fine(getObjectId() + ": unknown property: " + BBacnetPropertyIdentifier.tag(pId));
          }
          // Property not found
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY);
      }
    }
    catch (OutOfRangeException e)
    {
      logException(
        Level.INFO,
        new StringBuilder(getObjectId().toString())
          .append(": OutOfRangeException writing property ")
          .append(BBacnetPropertyIdentifier.tag(pId)),
        e);
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }
    catch (AsnException e)
    {
      logException(
        Level.INFO,
        new StringBuilder(getObjectId().toString())
          .append(": AsnException writing property ")
          .append(BBacnetPropertyIdentifier.tag(pId)),
        e);
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      logException(
        Level.INFO,
        new StringBuilder(getObjectId().toString())
          .append(": PermissionException writing property ")
          .append(BBacnetPropertyIdentifier.tag(pId)),
        e);
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

  private ErrorType writeNotifyType(byte[] val) throws AsnException
  {
    BBacnetNotifyType notifyType = BBacnetNotifyType.make(AsnUtil.fromAsnEnumerated(val));
    if (notifyType.getOrdinal() == BBacnetNotifyType.ALARM)
    {
      return null;
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": the notify type must be 'Alarm': attempted value: " + notifyType);
    }
    return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
  }

  private ErrorType writeEventParameters(byte[] val) throws AsnException
  {
    BBacnetEventParameter eventParam = new BBacnetEventParameter();
    AsnUtil.fromAsn(BacnetConst.ASN_ANY, val, eventParam);

    Context bacnetContext = BLocalBacnetDevice.getBacnetContext();
    bacnetContext.getUser().checkWrite(this, eventParameter);
    bacnetContext.getUser().checkWrite(this, typeOfEvent);
    checkEventType(eventParam.getChoice());

    ErrorType error = writeEventParameters(eventParam);
    if (error != null)
    {
      return error;
    }

    set(eventParameter, eventParam, bacnetContext);
    set(typeOfEvent, BBacnetEventType.make(eventParam.getChoice()), bacnetContext);
    return null;
  }

  private void checkEventType(int eventType)
    throws OutOfRangeException
  {
    // These event types are not supported at all. Other types may not be supported based on the
    // referenced object- that is checked in configureExt.
    switch (eventType)
    {
      case BBacnetEventType.CHANGE_OF_STATE:
      case BBacnetEventType.COMMAND_FAILURE:
      case BBacnetEventType.FLOATING_LIMIT:
      case BBacnetEventType.OUT_OF_RANGE:
      case BBacnetEventType.DOUBLE_OUT_OF_RANGE:
      case BBacnetEventType.SIGNED_OUT_OF_RANGE:
      case BBacnetEventType.UNSIGNED_OUT_OF_RANGE:
      case BBacnetEventType.BUFFER_READY:
      case BBacnetEventType.CHANGE_OF_CHARACTERSTRING:
      case BBacnetEventType.NONE:
        return;

      case BBacnetEventType.CHANGE_OF_BITSTRING:
      case BBacnetEventType.CHANGE_OF_VALUE:
      case BBacnetEventType.COMPLEX_EVENT_TYPE:
      case BBacnetEventType.BUFFER_READY_DEPRECATED:
      case BBacnetEventType.CHANGE_OF_LIFE_SAFETY:
      case BBacnetEventType.EXTENDED:
      case BBacnetEventType.UNSIGNED_RANGE:
      case BBacnetEventType.RESERVED:
      case BBacnetEventType.ACCESS_EVENT:
      case BBacnetEventType.CHANGE_OF_STATUS_FLAGS:
      case BBacnetEventType.CHANGE_OF_RELIABILITY:
      default:
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": event type " + BBacnetEventType.tag(eventType) + " is not supported");
        }
        throw new OutOfRangeException("event type " + BBacnetEventType.tag(eventType) + " is not supported");
    }
  }

  private ErrorType writeEventParameters(BBacnetEventParameter eventParam)
  {
    // getObject will validate that the eventEnrollmentOrd and objectPropertyReference properties
    // are consistent. If not, ext will be null and recreated if there is a target. Keep getObject
    // ahead of getTarget.
    BPointExtension pointExt = (BPointExtension) getObject();

    BComponent target;
    if (pointExt != null)
    {
      // getTarget is called within getObject to verify pointExt's parent matches the target of the
      // objectPropertyReference.
      target = (BComponent) pointExt.getParent();

      // eventEnable, notificationClass, eventMessageTextsConfig, eventAlgorithmInhibit, and
      // eventAlgorithmInhibitRef are outside eventParameters; get their latest values off the
      // pointExt so that they'll be transferred to the updated ext.
      getEventEnable(pointExt);
      getNotificationClass(pointExt);
      updateEventMessageTextsConfig(pointExt);
      updateEventAlgorithmInhibitInfo(pointExt);
    }
    else
    {
      // The pointExt has never been configured or was invalid; a new one is being created.
      target = resolveTarget(getObjectPropertyReference());
      if (target == null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": eventParameters BACnet property (" + eventParam +
            ") was written but a target could not be resolved based on the objectPropertyReference: " +
            getObjectPropertyReference());
        }
        return null;
      }
    }

    ErrorType error = configureExt(eventParam, pointExt, target);
    if (error != null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": error configuring alarm ext while writing EventParameters property: " + eventParam);
      }
    }
    return error;
  }

  private ErrorType writeObjectPropertyReference(byte[] val) throws AsnException
  {
    BBacnetDeviceObjectPropertyReference objPropRef = new BBacnetDeviceObjectPropertyReference();
    AsnUtil.fromAsn(BacnetConst.ASN_ANY, val, objPropRef);

    BComponent newTarget = resolveTarget(objPropRef);
    if (newTarget == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": could not find a target when writing BACnet objectPropertyReference property: " + objPropRef);
      }
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }

    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt != null)
    {
      BComponent oldTarget = (BComponent) pointExt.getParent();
      if (oldTarget == newTarget)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": BACnet write of Object_Property_Reference points to existing extension's parent: " + objPropRef);
        }
        return null;
      }

      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": BACnet changing Object_Property_Reference to " + objPropRef);
      }

      updateEventParameters(pointExt);

      // eventEnable, notificationClass, eventMessageTextsConfig, eventAlgorithmInhibit, and
      // eventAlgorithmInhibitRef are outside eventParameters; get their latest values off the
      // pointExt so that they'll be transferred to the new ext.
      getEventEnable(pointExt);
      getNotificationClass(pointExt);
      updateEventMessageTextsConfig(pointExt);
      updateEventAlgorithmInhibitInfo(pointExt);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": BACnet writing Object_Property_Reference when extension is not yet configured: " + objPropRef);
      }
    }

    // The target point is changing so any current alarm ext will need to be re-created.
    resetDescriptor();

    ErrorType error = configureExt(getEventParameter(), null, newTarget);
    if (error != null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": error configuring alarm ext when writing BACnet" +
          " objectPropertyReference property: " + objPropRef);
      }
    }
    else
    {
      setObjectPropertyReference(objPropRef);
    }

    return error;
  }

  private ErrorType writeNotificationClass(byte[] val) throws AsnException
  {
    int instanceNum = AsnUtil.fromAsnUnsignedInt(val);
    Context context = BLocalBacnetDevice.getBacnetContext();
    ErrorType error = configureAlarmClass((BPointExtension) getObject(), instanceNum, context);
    if (error == null)
    {
      setInt(notificationClassId, instanceNum, context);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": error in writeNotificationClass");
      }
    }
    return error;
  }

  private static BBacnetNotificationClassDescriptor lookupNotificationClass(int instanceNum)
  {
    BBacnetObjectIdentifier id = BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS, instanceNum);
    return (BBacnetNotificationClassDescriptor) BBacnetNetwork.localDevice().lookupBacnetObject(id);
  }

  private ErrorType writeEventEnable(byte[] val) throws AsnException
  {
    BBacnetBitString eventEnableBits = AsnUtil.fromAsnBitString(val);
    BAlarmTransitionBits alarmEnable = BacnetBitStringUtil.getBAlarmTransitionBits(eventEnableBits);
    if (alarmEnable == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": could not write the eventEnable property because" +
          " the alarm transition bits could not be retrieved for value " + eventEnableBits);
      }
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }

    eventEnable = alarmEnable;

    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt instanceof BAlarmSourceExt)
    {
      pointExt.set(BAlarmSourceExt.alarmEnable, alarmEnable, BLocalBacnetDevice.getBacnetContext());
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      pointExt.set(BBacnetTrendLogAlarmSourceExt.alarmEnable, alarmEnable, BLocalBacnetDevice.getBacnetContext());
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": could not write the eventEnable property because the"
          + " associated point ext is not set or not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt");
      }
    }

    return null;
  }

  private ErrorType writeMessageTextsConfig(int ndx, byte[] val)
    throws AsnException
  {
    if (ndx < NOT_USED || ndx > MESSAGE_TEXTS_COUNT)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX);
    }

    if (ndx == 0)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }

    switch (ndx)
    {
      case NOT_USED:
        BBacnetArray textsConfig = new BBacnetArray(BString.TYPE, 3);
        AsnUtil.fromAsn(BacnetConst.ASN_ANY, val, textsConfig);
        toOffnormalText = textsConfig.getElement(1).toString(null);
        toFaultText = textsConfig.getElement(2).toString(null);
        toNormalText = textsConfig.getElement(3).toString(null);
        break;

      case 1:
        toOffnormalText = AsnUtil.fromAsnCharacterString(val);
        break;

      case 2:
        toFaultText = AsnUtil.fromAsnCharacterString(val);
        break;

      case 3:
        toNormalText = AsnUtil.fromAsnCharacterString(val);
        break;
    }

    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt instanceof BAlarmSourceExt)
    {
      BAlarmSourceExt alarmExt = (BAlarmSourceExt) pointExt;
      Context context = BLocalBacnetDevice.getBacnetContext();
      switch (ndx)
      {
        case NOT_USED:
          alarmExt.set(
            BAlarmSourceExt.toOffnormalText,
            BFormat.make(toOffnormalText),
            context);
          alarmExt.set(
            BAlarmSourceExt.toFaultText,
            BFormat.make(toFaultText),
            context);
          alarmExt.set(
            BAlarmSourceExt.toNormalText,
            BFormat.make(toNormalText),
            context);
          resetOutOfRangeTexts(alarmExt);
          break;

        case 1:
          alarmExt.set(
            BAlarmSourceExt.toOffnormalText,
            BFormat.make(toOffnormalText),
            context);
          resetOutOfRangeTexts(alarmExt);
          break;

        case 2:
          alarmExt.set(
            BAlarmSourceExt.toFaultText,
            BFormat.make(toFaultText),
            context);
          break;

        case 3:
          alarmExt.set(
            BAlarmSourceExt.toNormalText,
            BFormat.make(toNormalText),
            context);
          break;
      }
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      BBacnetTrendLogAlarmSourceExt trendAlarmExt = (BBacnetTrendLogAlarmSourceExt) pointExt;
      switch (ndx)
      {
        case NOT_USED:
          if (!toOffnormalText.isEmpty() || !toFaultText.isEmpty())
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }

          // BBacnetTrendLogAlarmSourceExt does not have properties for toOffnormal and toFault texts
          trendAlarmExt.set(
            BBacnetTrendLogAlarmSourceExt.toNormalText,
            BFormat.make(toNormalText),
            BLocalBacnetDevice.getBacnetContext());
          break;

        case 1:
          if (!toOffnormalText.isEmpty())
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }
          break;

        case 2:
          if (!toFaultText.isEmpty())
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }
          break;

        case 3:
          trendAlarmExt.set(
            BBacnetTrendLogAlarmSourceExt.toNormalText,
            BFormat.make(toNormalText),
            BLocalBacnetDevice.getBacnetContext());
          break;
      }
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": could not write the eventMessageTextsConfig property because"
          + " the associated point ext is not set or not an AlarmSourceExt or BacnetTrendLogAlarmSourceExt");
      }
    }

    return null;
  }

  private ErrorType writeEventAlgorithmInhibit(byte[] val)
    throws AsnException
  {
    boolean newValue = AsnUtil.fromAsnBoolean(val);

    if (!getEventDetectionEnable())
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": could not write the alarmInhibit property because event detection is disabled");
      }
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }

    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt != null)
    {
      BLink[] alarmInhibitLinks = getAlarmInhibitLinks(pointExt);
      if (alarmInhibitLinks.length > 0)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": could not write the eventAlgorithmInhibit property because alarmInhibit is linked");
        }
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }

      if (pointExt instanceof BAlarmSourceExt)
      {
        pointExt.set(BAlarmSourceExt.alarmInhibit, new BStatusBoolean(newValue), BLocalBacnetDevice.getBacnetContext());
      }
      else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
      {
        pointExt.set(BBacnetTrendLogAlarmSourceExt.alarmInhibit, new BStatusBoolean(newValue), BLocalBacnetDevice.getBacnetContext());
      }
    }
    else
    {
      if (eventAlgorithmInhibitRef.getObjectId().getInstanceNumber() != BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": could not write the eventAlgorithmInhibit property because" +
            " eventAlgorithmInhibitRef (" + eventAlgorithmInhibitRef + ") is configured");
        }
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }
    }

    eventAlgorithmInhibit = newValue;
    return null;
  }

  private static BLink[] getAlarmInhibitLinks(BPointExtension pointExt)
  {
    if (pointExt instanceof BAlarmSourceExt)
    {
      return pointExt.getLinks(BAlarmSourceExt.alarmInhibit);
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      return pointExt.getLinks(BBacnetTrendLogAlarmSourceExt.alarmInhibit);
    }
    else
    {
      return EMPTY_LINKS_ARRAY;
    }
  }

  private ErrorType writeEventAlgorithmInhibitRef(byte[] val)
    throws AsnException
  {
    BBacnetObjectPropertyReference newObjPropRef = new BBacnetObjectPropertyReference();
    AsnUtil.fromAsn(val, newObjPropRef);

    Context context = BLocalBacnetDevice.getBacnetContext();

    if (newObjPropRef.getObjectId().getInstanceNumber() == BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
    {
      eventAlgorithmInhibitRef = newObjPropRef;
      removeAlarmInhibitLinks((BPointExtension) getObject(), context);
      return null;
    }

    BBooleanPoint sourcePoint;
    try
    {
      sourcePoint = findEventAlgorithmInhibitSourcePoint(newObjPropRef);
    }
    catch (EventEnrollmentException e)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": error adding eventAlgorithmInhibitRef link; message: " + e.getMessage());
      }
      return e.errorType;
    }

    checkLinkPermissions(sourcePoint, "out", context);

    BPointExtension pointExt = (BPointExtension) getObject();
    if (pointExt instanceof BAlarmSourceExt)
    {
      replaceLinks(pointExt, BAlarmSourceExt.alarmInhibit, sourcePoint, context);
    }
    else if (pointExt instanceof BBacnetTrendLogAlarmSourceExt)
    {
      replaceLinks(pointExt, BBacnetTrendLogAlarmSourceExt.alarmInhibit, sourcePoint, context);
    }

    eventAlgorithmInhibitRef = newObjPropRef;
    return null;
  }

  private void removeAlarmInhibitLinks(BPointExtension pointExt, Context context)
  {
    if (pointExt != null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": removing links to alarmInhibit because" +
          " eventAlgorithmInhibitRef is set to the unconfigured instance number " + BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER);
      }

      for (BLink link : getAlarmInhibitLinks(pointExt))
      {
        pointExt.remove(link.getPropertyInParent(), context);
      }
    }
  }

  private static BBooleanPoint findEventAlgorithmInhibitSourcePoint(BBacnetObjectPropertyReference objPropRef)
    throws EventEnrollmentException
  {
    int newObjectType = objPropRef.getObjectId().getObjectType();
    if (!(newObjectType == BBacnetObjectType.BINARY_INPUT ||
          newObjectType == BBacnetObjectType.BINARY_OUTPUT ||
          newObjectType == BBacnetObjectType.BINARY_VALUE))
    {
      throw new EventEnrollmentException(
        "EventAlgorithmInhibitRef is to non-binary object type " + BBacnetObjectType.tag(newObjectType),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    BComponent sourcePoint;
    try
    {
      sourcePoint = BacnetDescriptorUtil.findLocalPoint(objPropRef);
    }
    catch (Exception e)
    {
      logException(
        Level.SEVERE,
        new StringBuilder(objPropRef.getObjectId().toString())
          .append(": could not resolve the point for objectPropertyReference ")
          .append(objPropRef),
        e
      );
      throw new EventEnrollmentException(
        "Could not resolve point for eventAlgorithmInhibitRef " + objPropRef,
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    if (!(sourcePoint instanceof BBooleanPoint))
    {
      throw new EventEnrollmentException(
        "eventAlgorithmRef (" + objPropRef + ") point is type " +
          sourcePoint.getType() + " but should be instanceof BooleanPoint",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    return (BBooleanPoint) sourcePoint;
  }

  //endregion

  //region ConfigureExt

  private ErrorType configureExt(BBacnetEventParameter eventParam, BPointExtension pointExt, BComponent target)
  {
    try
    {
      int eventType = eventParam.getChoice();
      switch (eventType)
      {
        case BBacnetEventType.CHANGE_OF_STATE:
          configureChangeOfStateExt(eventParam, pointExt, target);
          break;
        case BBacnetEventType.COMMAND_FAILURE:
          configureCommandFailureExt(eventParam, pointExt, target);
          break;
        case BBacnetEventType.FLOATING_LIMIT:
          configureFloatingLimitExt(eventParam, pointExt, target);
          break;
        case BBacnetEventType.OUT_OF_RANGE:
        case BBacnetEventType.DOUBLE_OUT_OF_RANGE:
        case BBacnetEventType.SIGNED_OUT_OF_RANGE:
        case BBacnetEventType.UNSIGNED_OUT_OF_RANGE:
          configureOutOfRangeExt(eventParam, pointExt, target);
          break;
        case BBacnetEventType.BUFFER_READY:
          configureTrendAlarmExt(eventParam, pointExt, target);
          break;
        case BBacnetEventType.CHANGE_OF_CHARACTERSTRING:
          configureStringChangeOfStateExt(eventParam, pointExt, target);
          break;
        case BBacnetEventType.NONE:
          configureNoneExt();
          break;

        case BBacnetEventType.CHANGE_OF_BITSTRING:
        case BBacnetEventType.CHANGE_OF_VALUE:
        case BBacnetEventType.COMPLEX_EVENT_TYPE:
        case BBacnetEventType.BUFFER_READY_DEPRECATED:
        case BBacnetEventType.CHANGE_OF_LIFE_SAFETY:
        case BBacnetEventType.EXTENDED:
        case BBacnetEventType.UNSIGNED_RANGE:
        case BBacnetEventType.RESERVED:
        case BBacnetEventType.ACCESS_EVENT:
        case BBacnetEventType.CHANGE_OF_STATUS_FLAGS:
        case BBacnetEventType.CHANGE_OF_RELIABILITY:
        default:
          throw new EventEnrollmentException(
            "event type " + BBacnetEventType.tag(eventType) + " is not supported",
            new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OPTIONAL_FUNCTIONALITY_NOT_SUPPORTED));
      }

      return null;
    }
    catch (EventEnrollmentException e)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": error configuring alarm ext; message: " + e.getMessage());
      }
      resetDescriptor();
      return e.errorType;
    }
    catch (PermissionException e)
    {
      logException(
        Level.INFO,
        new StringBuilder(getObjectId().toString()).append(": permission exception configuring alarm ext"),
        e);
      resetDescriptor();
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
    catch (Exception e)
    {
      logException(
        Level.INFO,
        new StringBuilder(getObjectId().toString()).append(": unexpected error configuring alarm ext"),
        e);
      resetDescriptor();
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER);
    }
  }

  private void configureAlarmExt(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
  {
    Context context = BLocalBacnetDevice.getBacnetContext();
    configureTimeDelays(eventParam, alarmExt, context);
    alarmExt.set(BAlarmSourceExt.alarmEnable, eventEnable, context);
    configureAlarmClass(alarmExt, getNotificationClassId(), context);
    alarmExt.set(BAlarmSourceExt.toOffnormalText, BFormat.make(toOffnormalText), context);
    alarmExt.set(BAlarmSourceExt.toFaultText, BFormat.make(toFaultText), context);
    alarmExt.set(BAlarmSourceExt.toNormalText, BFormat.make(toNormalText), context);
    configureAlarmInhibit(alarmExt, context);
  }

  private static void configureTimeDelays(BBacnetEventParameter eventParam, BAlarmSourceExt ext, Context context)
  {
    BRelTime timeDelay = BRelTime.makeSeconds(((BBacnetUnsigned) eventParam.get(BBacnetEventParameter.TIME_DELAY_SLOT_NAME)).getInt());
    ext.set(BAlarmSourceExt.timeDelay, timeDelay, context);
    ext.set(BAlarmSourceExt.timeDelayToNormal, timeDelay, context);
  }

  private ErrorType configureAlarmClass(BPointExtension ext, int instanceNum, Context context)
  {
    if (instanceNum < 0 || instanceNum > BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": configureAlarmClass:" +
          " notification class instance number " + instanceNum + " exceeds the maximum allowable instance number value");
      }
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }

    String alarmClassName;
    if (instanceNum == BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
    {
      alarmClassName = "";
    }
    else
    {
      BBacnetNotificationClassDescriptor descriptor = lookupNotificationClass(instanceNum);
      if (descriptor == null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": configureAlarmClass:" +
            " cannot find descriptor for notification class instance number " + instanceNum);
        }
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
      }

      BAlarmClass alarmClass = descriptor.getAlarmClass();
      if (alarmClass == null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": configureAlarmClass:" +
            " descriptor for notification class instance number " + instanceNum + " could not resolve its alarm class");
        }
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER);
      }

      alarmClassName = alarmClass.getName();
    }

    if (ext instanceof BAlarmSourceExt)
    {
      ext.setString(BAlarmSourceExt.alarmClass, alarmClassName, context);
    }
    else if (ext instanceof BBacnetTrendLogAlarmSourceExt)
    {
      ext.setString(BBacnetTrendLogAlarmSourceExt.alarmClass, alarmClassName, context);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": configureAlarmClass:" +
          " no associated point ext on which to update the alarm class based on" +
          " notification-class instance number " + instanceNum);
      }
    }

    return null;
  }

  private void configureAlarmInhibit(BPointExtension ext, Context context)
  {
    boolean isLinked = addAlarmInhibitLink(ext, context);
    if (!isLinked)
    {
      if (ext instanceof BAlarmSourceExt)
      {
        ((BAlarmSourceExt) ext).setAlarmInhibit(new BStatusBoolean(eventAlgorithmInhibit));
      }
      else if (ext instanceof BBacnetTrendLogAlarmSourceExt)
      {
        ((BBacnetTrendLogAlarmSourceExt) ext).setAlarmInhibit(new BStatusBoolean(eventAlgorithmInhibit));
      }
    }
  }

  private boolean addAlarmInhibitLink(BPointExtension ext, Context context)
  {
    if (eventAlgorithmInhibitRef.getObjectId().getInstanceNumber() == BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
    {
      removeAlarmInhibitLinks(ext, context);
      return false;
    }

    BBooleanPoint sourcePoint;
    try
    {
      sourcePoint = findEventAlgorithmInhibitSourcePoint(eventAlgorithmInhibitRef);
      checkLinkPermissions(sourcePoint, "out", context);

      if (ext instanceof BAlarmSourceExt)
      {
        replaceLinks(ext, BAlarmSourceExt.alarmInhibit, sourcePoint, context);
        return true;
      }

      if (ext instanceof BBacnetTrendLogAlarmSourceExt)
      {
        replaceLinks(ext, BBacnetTrendLogAlarmSourceExt.alarmInhibit, sourcePoint, context);
        return true;
      }

      if (logger.isLoggable(Level.WARNING))
      {
        logger.log(Level.WARNING, getObjectId() + ": when adding alarm inhibit link, type not supported: " + ext.getType());
      }
      return false;
    }
    catch (Exception e)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.log(Level.FINE, getObjectId() + ": error adding eventAlgorithmInhibitRef link", e);
      }
      return false;
    }
  }

  private void configureGeneralFaultAlgorithm(BBacnetEventParameter eventParam, BAlarmSourceExt ext)
  {
    BFaultAlgorithm faultAlgorithm = ext.getFaultAlgorithm();
    if (!(ext.getFaultAlgorithm() instanceof BFaultAlgorithm))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing fault algorithm of type " + faultAlgorithm.getType() +
          " with FaultAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      ext.set(BAlarmSourceExt.faultAlgorithm, new BFaultAlgorithm(), BLocalBacnetDevice.getBacnetContext());
    }
  }

  private BAlarmSourceExt updateToAlarmExt(BPointExtension pointExt)
  {
    if (!(pointExt instanceof BAlarmSourceExt))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing point extension of type " +
          (pointExt != null ? pointExt.getType() : null) + " with BAlarmExt");
      }
      // TODO Has the non-AlarmSourceExt already been removed?
      return new BAlarmSourceExt();
    }

    return (BAlarmSourceExt) pointExt;
  }

  private BBacnetTrendLogAlarmSourceExt updateToTrendAlarmExt(BPointExtension pointExt)
  {
    if (!(pointExt instanceof BBacnetTrendLogAlarmSourceExt))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing point extension of type " + (pointExt != null ? pointExt.getType() : null) +
          " with BBacnetTrendLogAlarmSourceExt");
      }
      return new BBacnetTrendLogAlarmSourceExt();
    }

    return (BBacnetTrendLogAlarmSourceExt) pointExt;
  }

  private static void addExtIfMissing(BPointExtension pointExt, BComponent target)
  {
    if (pointExt.getParent() == null)
    {
      // Not added to the target yet
      target.add("EventEnrollmentAlarmExt?", pointExt, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private void configureNoneExt()
  {
    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": resetting the descriptor because event type is none");
    }
    resetDescriptor();
  }

  //region ChangeOfState

  private void configureChangeOfStateExt(
    BBacnetEventParameter eventParam,
    BPointExtension pointExt,
    BComponent target)
      throws EventEnrollmentException
  {
    // TODO check that the property type matches the event type?
    // int objType = getRemoteObjectType();

    if (target instanceof BBooleanPoint || target instanceof BEnumPoint)
    {
      BAlarmSourceExt alarmExt = updateToAlarmExt(pointExt);
      configureAlarmExt(eventParam, alarmExt);

      if (target instanceof BBooleanPoint)
      {
        configureBooleanChangeOfStateOffnormal(eventParam, alarmExt);
      }
      else
      {
        configureEnumChangeOfStateOffnormal(eventParam, alarmExt, (BEnumPoint) target);
      }

      configureGeneralFaultAlgorithm(eventParam, alarmExt);
      addExtIfMissing(alarmExt, target);
      updateDescriptor(alarmExt);
    }
    else
    {
      throw new EventEnrollmentException(
        "referenced object is of type " + target.getType() +
          " and not instanceof BooleanPoint or EnumPoint, which is required for change-of-state extensions; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }
  }

  private void configureBooleanChangeOfStateOffnormal(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
    throws EventEnrollmentException
  {
    BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BBooleanChangeOfStateAlgorithm)
    {
      configureBooleanChangeOfStateOffnormal(eventParam, (BBooleanChangeOfStateAlgorithm) offnormalAlgorithm);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with BooleanChangeOfStateAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BBooleanChangeOfStateAlgorithm changeOfStateAlgorithm = new BBooleanChangeOfStateAlgorithm();
      configureBooleanChangeOfStateOffnormal(eventParam, changeOfStateAlgorithm);
      alarmExt.set(BAlarmSourceExt.offnormalAlgorithm, changeOfStateAlgorithm, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private static void configureBooleanChangeOfStateOffnormal(
    BBacnetEventParameter eventParam,
    BBooleanChangeOfStateAlgorithm algorithm)
      throws EventEnrollmentException
  {
    BBacnetListOf listOfValues = (BBacnetListOf) eventParam.get(BBacnetEventParameter.LIST_OF_VALUES_SLOT_NAME);
    BBacnetPropertyStates[] propStates = listOfValues.getChildren(BBacnetPropertyStates.class);
    if (propStates.length < 1)
    {
      throw new EventEnrollmentException(
        "boolean change-of-state alarm extensions require at least 1 alarm value; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    // TODO Check that the BacnetPropertyState type is the same as the property value type
    boolean alarmValue;
    switch (propStates[0].getChoice())
    {
      case NBacnetPropertyStates.BOOLEAN_VALUE_TAG:
        //noinspection OverlyStrongTypeCast
        alarmValue = ((BBoolean) propStates[0].get(BBacnetPropertyStates.BOOLEAN_VALUE_SLOT_NAME)).getBoolean();
        break;
      case NBacnetPropertyStates.BINARY_VALUE_TAG:
        //noinspection OverlyStrongTypeCast
        alarmValue = ((BBacnetBinaryPv) propStates[0].get(BBacnetPropertyStates.BINARY_VALUE_SLOT_NAME)).isActive();
        break;
      default:
        throw new EventEnrollmentException(
          "boolean change-of-state alarm extensions require a BOOLEAN (0) or BACnetBinaryPv (1) value; found: " +
            propStates[0].getChoice() +
            ", event type: " + BBacnetEventType.tag(eventParam.getChoice()),
          new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    algorithm.setBoolean(BBooleanChangeOfStateAlgorithm.alarmValue, alarmValue, BLocalBacnetDevice.getBacnetContext());
  }

  private void configureEnumChangeOfStateOffnormal(
    BBacnetEventParameter eventParam,
    BAlarmSourceExt alarmExt,
    BEnumPoint point)
      throws EventEnrollmentException
  {
    BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BEnumChangeOfStateAlgorithm)
    {
      configureEnumChangeOfStateOffnormal(eventParam, (BEnumChangeOfStateAlgorithm) offnormalAlgorithm, point);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with EnumChangeOfStateAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BEnumChangeOfStateAlgorithm changeOfStateAlgorithm = new BEnumChangeOfStateAlgorithm();
      configureEnumChangeOfStateOffnormal(eventParam, changeOfStateAlgorithm, point);
      alarmExt.set(BAlarmSourceExt.offnormalAlgorithm, changeOfStateAlgorithm, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private static void configureEnumChangeOfStateOffnormal(
    BBacnetEventParameter eventParam,
    BEnumChangeOfStateAlgorithm algorithm,
    BEnumPoint point)
      throws EventEnrollmentException
  {
    BBacnetListOf listOfValues = (BBacnetListOf) eventParam.get(BBacnetEventParameter.LIST_OF_VALUES_SLOT_NAME);
    BBacnetPropertyStates[] propStates = listOfValues.getChildren(BBacnetPropertyStates.class);

    BEnumRange range = (BEnumRange) point.getFacets().get(BFacets.RANGE);

    int[] ordinals = new int[propStates.length];
    String[] tags = new String[propStates.length];
    for (int i = 0; i < propStates.length; i++)
    {
      if (propStates[i].getChoice() != NBacnetPropertyStates.UNSIGNED_VALUE_TAG)
      {
        throw new EventEnrollmentException(
          "enum change-of-state alarm extensions require an UNSIGNED (11) value; found: " +
            propStates[i].getChoice() +
            ", event type: " + BBacnetEventType.tag(eventParam.getChoice()),
          new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
      }

      BBacnetUnsigned child = (BBacnetUnsigned) propStates[i].get(BBacnetPropertyStates.UNSIGNED_VALUE_SLOT_NAME);
      ordinals[i] = child.getInt();
      tags[i] = range != null ? SlotPath.escape(range.getTag(ordinals[i])) : SlotPath.escape(String.valueOf(ordinals[i]));
    }

    algorithm.set(BEnumChangeOfStateAlgorithm.alarmValues, BEnumRange.make(ordinals, tags), BLocalBacnetDevice.getBacnetContext());
  }

  //endregion

  //region CommandFailure

  private void configureCommandFailureExt(
    BBacnetEventParameter eventParam,
    BPointExtension pointExt,
    BComponent target)
      throws EventEnrollmentException
  {
    // TODO check that the property type matches the event type?

    if (target instanceof BBooleanPoint || target instanceof BEnumPoint)
    {
      BAlarmSourceExt alarmExt = updateToAlarmExt(pointExt);
      configureAlarmExt(eventParam, alarmExt);

      if (target instanceof BBooleanPoint)
      {
        configureBooleanCommandFailureOffnormal(eventParam, alarmExt);
        configureGeneralFaultAlgorithm(eventParam, alarmExt);
      }
      else
      {
        configureEnumCommandFailureOffnormal(eventParam, alarmExt);
        configureEnumCommandFailureFault(eventParam, alarmExt);
      }

      addExtIfMissing(alarmExt, target);
      updateDescriptor(alarmExt);
    }
    else
    {
      throw new EventEnrollmentException(
        "referenced object is of type " + target.getType() +
          " and not instanceof BooleanPoint or EnumPoint, which is required for command failure extensions; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }
  }

  private void configureBooleanCommandFailureOffnormal(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
    throws EventEnrollmentException
  {
    BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BBooleanCommandFailureAlgorithm)
    {
      configureBooleanCommandFailureOffnormal(eventParam, (BBooleanCommandFailureAlgorithm) offnormalAlgorithm);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with BooleanCommandFailureAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BBooleanCommandFailureAlgorithm commandFailureAlgorithm = new BBooleanCommandFailureAlgorithm();
      configureBooleanCommandFailureOffnormal(eventParam, commandFailureAlgorithm);
      alarmExt.set(BAlarmSourceExt.offnormalAlgorithm, commandFailureAlgorithm, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private void configureBooleanCommandFailureOffnormal(
    BBacnetEventParameter eventParam,
    BBooleanCommandFailureAlgorithm algorithm)
      throws EventEnrollmentException
  {
    BComponent feedbackPoint = getCommandFailureFeedbackPoint(eventParam);
    if (!(feedbackPoint instanceof BBooleanPoint))
    {
      throw new EventEnrollmentException(
        "feedback point for boolean command failure is type " + feedbackPoint.getType() +
          " but should be instanceof BooleanPoint",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    Context context = BLocalBacnetDevice.getBacnetContext();
    checkLinkPermissions(feedbackPoint, "out", context);
    replaceLinks(algorithm, BBooleanCommandFailureAlgorithm.feedbackValue, feedbackPoint, context);
  }

  private static void replaceLinks(BComponent target, Property targetSlot, BComponent source, Context context)
  {
    // Set this once a link is found with the correct sourceOrd and sourceSlotName. Any other links
    // with the same sourceOrd or sourceSlotName should be removed so only a single one remains.
    BLink existingLink = null;

    for (BLink link : target.getLinks(targetSlot))
    {
      if (!link.getSourceOrd().equals(source.getHandleOrd()) ||
          !link.getSourceSlotName().equals("out") ||
          existingLink != null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine("Before adding a link to feedback point, cleared link to source ord "
            + link.getSourceOrd() + " and source slot " + link.getSourceSlotName());
        }
        target.remove(link.getPropertyInParent(), context);
      }
      else
      {
        existingLink = link;
      }
    }

    if (existingLink == null)
    {
      target.add(
        null,
        new BLink(
          source.getHandleOrd(),
          /* sourceSlot */ "out",
          targetSlot.getName(),
          /* enabled */ true),
        context);
    }
  }

  private BBacnetDeviceObjectPropertyReference getLinkedPropertyReference(
    BComponent component,
    Property targetSlot,
    Type targetDescType)
  {
    BLink[] targetSlotLinks = component.getLinks(targetSlot);
    for (BLink link : targetSlotLinks)
    {
      if (!link.getSourceSlotName().equals("out") || !link.isActive() || !link.isEnabled())
      {
        continue;
      }

      BComponent source = link.getSourceComponent();
      if (source instanceof BControlPoint)
      {
        BAbstractProxyExt proxyExt = ((BControlPoint) source).getProxyExt();
        if (proxyExt instanceof BBacnetProxyExt)
        {
          // Must be a point on a remote device.
          return makeRemoteDeviceObjPropRef((BBacnetProxyExt) proxyExt);
        }
      }

      // TODO support links to remote schedules?

      // Must be a local object
      BIBacnetExportObject descriptor = findDescriptor(source.getHandleOrd());
      if (descriptor != null && descriptor.getType().is(targetDescType))
      {
        return makeLocalDeviceObjPropRef(descriptor.getObjectId());
      }
    }

    return makeUnconfiguredDeviceObjPropRef();
  }

  private static BBacnetDeviceObjectPropertyReference makeUnconfiguredDeviceObjPropRef()
  {
    return new BBacnetDeviceObjectPropertyReference(
      /* objectId */ BBacnetObjectIdentifier.make(BBacnetObjectType.ANALOG_INPUT, BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER),
      /* propertyId */ BBacnetPropertyIdentifier.PRESENT_VALUE,
      /* propertyArrayIndex */ NOT_USED,
      /* deviceId */ BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE, BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER));
  }

  private static BBacnetDeviceObjectPropertyReference makeLocalDeviceObjPropRef(BBacnetObjectIdentifier objectId)
  {
    return new BBacnetDeviceObjectPropertyReference(
      objectId,
      /* propertyId */ BBacnetPropertyIdentifier.PRESENT_VALUE,
      /* propertyArrayIndex */ NOT_USED,
      BBacnetNetwork.localDevice().getObjectId());
  }

  private static BBacnetDeviceObjectPropertyReference makeRemoteDeviceObjPropRef(BBacnetProxyExt proxyExt)
  {
    return new BBacnetDeviceObjectPropertyReference(
      proxyExt.getObjectId(),
      proxyExt.getPropertyId().getOrdinal(),
      proxyExt.getPropertyArrayIndex(),
      proxyExt.device().getObjectId());
  }

  private static void checkLinkPermissions(BComponent source, String sourceSlotName, Context context)
  {
    BUser user = context != null ? context.getUser() : null;
    if (user == null)
    {
      return;
    }

    user.checkWrite(source, source.getSlot(sourceSlotName));
  }

  private void configureEnumCommandFailureOffnormal(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
    throws EventEnrollmentException
  {
    BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BEnumCommandFailureAlgorithm)
    {
      configureEnumCommandFailureOffnormal(eventParam, (BEnumCommandFailureAlgorithm) offnormalAlgorithm);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with EnumCommandFailureAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BEnumCommandFailureAlgorithm commandFailureAlgorithm = new BEnumCommandFailureAlgorithm();
      configureEnumCommandFailureOffnormal(eventParam, commandFailureAlgorithm);
      alarmExt.set(BAlarmSourceExt.offnormalAlgorithm, commandFailureAlgorithm, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private void configureEnumCommandFailureOffnormal(
    BBacnetEventParameter eventParam,
    BEnumCommandFailureAlgorithm algorithm)
      throws EventEnrollmentException
  {
    BComponent feedbackPoint = getCommandFailureFeedbackPoint(eventParam);
    if (!(feedbackPoint instanceof BEnumPoint))
    {
      throw new EventEnrollmentException(
        "feedback point for enum command failure is type " + feedbackPoint.getType() +
          " but should be instanceof EnumPoint",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    Context context = BLocalBacnetDevice.getBacnetContext();
    checkLinkPermissions(feedbackPoint, "out", context);
    replaceLinks(algorithm, BEnumCommandFailureAlgorithm.feedbackValue, feedbackPoint, context);
  }

  private void configureEnumCommandFailureFault(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
  {
    BFaultAlgorithm faultAlgorithm = alarmExt.getFaultAlgorithm();
    if (!(alarmExt.getFaultAlgorithm() instanceof BEnumFaultAlgorithm))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing fault algorithm of type " +
          faultAlgorithm.getType() + " with EnumFaultAlgorithm for event type " +
          BBacnetEventType.tag(eventParam.getChoice()));
      }
      alarmExt.set(BAlarmSourceExt.faultAlgorithm, new BEnumFaultAlgorithm(), BLocalBacnetDevice.getBacnetContext());
    }
  }

  private BComponent getCommandFailureFeedbackPoint(BBacnetEventParameter eventParam)
    throws EventEnrollmentException
  {
    BValue feedbackRef = eventParam.get(BBacnetEventParameter.FEEDBACK_PROPERTY_REFERENCE_SLOT_NAME);
    if (!(feedbackRef instanceof BBacnetDeviceObjectPropertyReference))
    {
      throw new EventEnrollmentException(
        "feedback reference for command failure is type " +
          (feedbackRef != null ? feedbackRef.getType() : null) +
          " but should be instanceof BacnetDeviceObjectPropertyReference",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    try
    {
      return BacnetDescriptorUtil.findLocalOrRemotePoint((BBacnetDeviceObjectPropertyReference) feedbackRef);
    }
    catch (Exception e)
    {
      logException(
        Level.FINE,
        new StringBuilder(getObjectId().toString())
          .append(": error finding point for command failure feedback ref"),
        e);
      throw new EventEnrollmentException(
        "error finding point for command failure feedback ref",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }
  }

  //endregion

  //region FloatingLimit

  private void configureFloatingLimitExt(
    BBacnetEventParameter eventParam,
    BPointExtension pointExt,
    BComponent target)
      throws EventEnrollmentException
  {
    // TODO should we check that the object property reference points to a property type that matches the event type?
    //  For example, DOUBLE_OUT_OF_RANGE is placed on a NumericPoint tied to a Double parameter?
    //  int objType = getRemoteObjectType();
    if (!(target instanceof BNumericPoint))
    {
      throw new EventEnrollmentException(
        "referenced object is of type " + target.getType() +
          " and not instanceof NumericPoint, which is required for Floating Limit Algorithm extensions; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    BAlarmSourceExt alarmExt = updateToAlarmExt(pointExt);
    configureAlarmExt(eventParam, alarmExt);
    configureFloatingLimitOffnormal(eventParam, alarmExt);
    configureGeneralFaultAlgorithm(eventParam, alarmExt);
    addExtIfMissing(alarmExt, target);
    updateDescriptor(alarmExt);
  }

  private void configureFloatingLimitOffnormal(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
    throws EventEnrollmentException
  {
    BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BFloatingLimitAlgorithm)
    {
      configureFloatingLimitOffnormal(eventParam, (BFloatingLimitAlgorithm) offnormalAlgorithm);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with BFloatingLimitAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BFloatingLimitAlgorithm floatingLimitAlgorithm = new BFloatingLimitAlgorithm();
      configureFloatingLimitOffnormal(eventParam, floatingLimitAlgorithm);
      alarmExt.set(BAlarmSourceExt.offnormalAlgorithm, floatingLimitAlgorithm, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private void configureFloatingLimitOffnormal(BBacnetEventParameter eventParam, BFloatingLimitAlgorithm algorithm)
    throws EventEnrollmentException
  {
    BComponent setpoint = getFloatingLimitSetpoint(eventParam);
    if (!(setpoint instanceof BNumericPoint))
    {
      throw new EventEnrollmentException(
        "feedback point for floating limit is type " + setpoint.getType() +
          " but should be instanceof BNumericPoint",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    Context context = BLocalBacnetDevice.getBacnetContext();
    checkLinkPermissions(setpoint, "out", context);
    replaceLinks(algorithm, BFloatingLimitAlgorithm.setpoint, setpoint, context);

    algorithm.setDouble(BFloatingLimitAlgorithm.lowDiffLimit, ((BNumber) eventParam.get(BBacnetEventParameter.LOW_DIFF_LIMIT_SLOT_NAME)).getDouble(), context);
    algorithm.setDouble(BFloatingLimitAlgorithm.highDiffLimit, ((BNumber) eventParam.get(BBacnetEventParameter.HIGH_DIFF_LIMIT_SLOT_NAME)).getDouble(), context);
    algorithm.setDouble(BFloatingLimitAlgorithm.deadband, ((BNumber) eventParam.get(BBacnetEventParameter.DEADBAND_SLOT_NAME)).getDouble(), context);

    BLimitEnable limitEnable = algorithm.getLimitEnable();
    limitEnable.setBoolean(BLimitEnable.highLimitEnable, true, context);
    limitEnable.setBoolean(BLimitEnable.lowLimitEnable, true, context);
  }

  private BComponent getFloatingLimitSetpoint(BBacnetEventParameter eventParam)
    throws EventEnrollmentException
  {
    BValue setpointRef = eventParam.get(BBacnetEventParameter.SETPOINT_REFERENCE_SLOT_NAME);
    if (!(setpointRef instanceof BBacnetDeviceObjectPropertyReference))
    {
      throw new EventEnrollmentException(
        "setpoint reference for Floating Limit is type " +
          (setpointRef != null ? setpointRef.getType() : null) +
          " but should be instanceof BacnetDeviceObjectPropertyReference",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    try
    {
      return BacnetDescriptorUtil.findLocalOrRemotePoint((BBacnetDeviceObjectPropertyReference) setpointRef);
    }
    catch (Exception e)
    {
      logException(
        Level.WARNING,
        new StringBuilder(getObjectId().toString()).append(": error finding point for floating limit setpoint ref"),
        e);
      throw new EventEnrollmentException(
        "error finding point for floating limit setpoint ref",
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }
  }

  //endregion

  //region OutOfRange

  private void configureOutOfRangeExt(
    BBacnetEventParameter eventParam,
    BPointExtension pointExt,
    BComponent target)
      throws EventEnrollmentException
  {
    // TODO should we check that the object property reference points to a property type that matches the event type?
    //  For example, DOUBLE_OUT_OF_RANGE is placed on a NumericPoint tied to a Double parameter?
    //  int objType = getRemoteObjectType();
    if (!(target instanceof BNumericPoint))
    {
      throw new EventEnrollmentException(
        "referenced object is of type " + target.getType() +
          " and not instanceof NumericPoint, which is required for out-of-range extensions; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    BAlarmSourceExt alarmExt = updateToAlarmExt(pointExt);
    configureAlarmExt(eventParam, alarmExt);
    configureOutOfRangeOffnormal(eventParam, alarmExt);
    configureOutOfRangeFault(eventParam, alarmExt);
    addExtIfMissing(alarmExt, target);
    updateDescriptor(alarmExt);
  }

  private void configureOutOfRangeOffnormal(BBacnetEventParameter eventParam, BAlarmSourceExt ext)
  {
    BOffnormalAlgorithm offnormalAlgorithm = ext.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BOutOfRangeAlgorithm)
    {
      configureOutOfRangeOffnormal(eventParam, (BOutOfRangeAlgorithm) offnormalAlgorithm);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with OutOfRangeAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BOutOfRangeAlgorithm outOfRangeAlgorithm = new BOutOfRangeAlgorithm();
      configureOutOfRangeOffnormal(eventParam, outOfRangeAlgorithm);
      ext.set(BAlarmSourceExt.offnormalAlgorithm, outOfRangeAlgorithm, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private static void configureOutOfRangeOffnormal(BBacnetEventParameter eventParam, BOutOfRangeAlgorithm algorithm)
  {
    Context context = BLocalBacnetDevice.getBacnetContext();
    algorithm.setDouble(BOutOfRangeAlgorithm.lowLimit, ((BNumber) eventParam.get(BBacnetEventParameter.LOW_LIMIT_SLOT_NAME)).getDouble(), context);
    algorithm.setDouble(BOutOfRangeAlgorithm.highLimit, ((BNumber) eventParam.get(BBacnetEventParameter.HIGH_LIMIT_SLOT_NAME)).getDouble(), context);
    algorithm.setDouble(BOutOfRangeAlgorithm.deadband, ((BNumber) eventParam.get(BBacnetEventParameter.DEADBAND_SLOT_NAME)).getDouble(), context);

    BLimitEnable limitEnable = algorithm.getLimitEnable();
    limitEnable.setBoolean(BLimitEnable.highLimitEnable, true, context);
    limitEnable.setBoolean(BLimitEnable.lowLimitEnable, true, context);
  }

  private void configureOutOfRangeFault(BBacnetEventParameter eventParam, BAlarmSourceExt ext)
  {
    BFaultAlgorithm faultAlgorithm = ext.getFaultAlgorithm();
    if (!(ext.getFaultAlgorithm() instanceof BOutOfRangeFaultAlgorithm))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing fault algorithm of type " + faultAlgorithm.getType() +
          " with OutOfRangeFaultAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      ext.set(BAlarmSourceExt.faultAlgorithm, new BOutOfRangeFaultAlgorithm(), BLocalBacnetDevice.getBacnetContext());
    }
  }

  //endregion

  //region BufferReady

  private void configureTrendAlarmExt(BBacnetEventParameter eventParam, BPointExtension pointExt, BComponent target)
    throws EventEnrollmentException
  {
    // TODO check that the property type matches the event type? TREND_LOG
    // int objType = getRemoteObjectType();
    if (!(target instanceof BIBacnetTrendLogExt))
    {
      throw new EventEnrollmentException(
        "target is of type " + target.getType() +
          " and not instanceof BIBacnetTrendLogExt, which is required for trend log alarm source extensions; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    BBacnetTrendLogAlarmSourceExt trendAlarmExt = updateToTrendAlarmExt(pointExt);
    addExtIfMissing(trendAlarmExt, target);

    Context context = BLocalBacnetDevice.getBacnetContext();
    trendAlarmExt.set(BAlarmSourceExt.alarmEnable, eventEnable, context);
    configureAlarmClass(trendAlarmExt, getNotificationClassId(), context);
    trendAlarmExt.set(BBacnetTrendLogAlarmSourceExt.toNormalText, BFormat.make(toNormalText), context);
    trendAlarmExt.updateParameters(
      getLongParameter(eventParam, BBacnetEventParameter.NOTIFICATION_THRESHOLD_SLOT_NAME),
      getLongParameter(eventParam, BBacnetEventParameter.PREVIOUS_NOTIFICATION_COUNT_SLOT_NAME),
      context);
    configureAlarmInhibit(trendAlarmExt, context);
    updateDescriptor(trendAlarmExt);
  }

  private static long getLongParameter(BBacnetEventParameter eventParam, String slotName)
  {
    return ((BBacnetUnsigned) eventParam.get(slotName)).getLong();
  }

  //endregion

  //region StringChangeOfState

  private void configureStringChangeOfStateExt(
    BBacnetEventParameter eventParam,
    BPointExtension pointExt,
    BComponent target)
      throws EventEnrollmentException
  {
    // TODO check that the property type matches the event type?
    // int objType = getRemoteObjectType();
    if (!(target instanceof BStringPoint))
    {
      throw new EventEnrollmentException(
        "referenced object is of type " + target.getType() +
          " and not instanceof StringPoint, which is required for String change-of-state extensions; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    BAlarmSourceExt alarmExt = updateToAlarmExt(pointExt);
    configureAlarmExt(eventParam, alarmExt);
    configureStringChangeOfStateOffnormal(eventParam, alarmExt);
    configureStringChangeOfStateFault(eventParam, alarmExt);
    addExtIfMissing(alarmExt, target);
    updateDescriptor(alarmExt);
  }

  private void configureStringChangeOfStateOffnormal(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
    throws EventEnrollmentException
  {
    BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BStringChangeOfStateAlgorithm)
    {
      configureStringChangeOfStateOffnormal(eventParam, (BStringChangeOfStateAlgorithm) offnormalAlgorithm);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with StringChangeOfStateAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BStringChangeOfStateAlgorithm stringChangeOfStateAlgorithm = new BStringChangeOfStateAlgorithm();
      configureStringChangeOfStateOffnormal(eventParam, stringChangeOfStateAlgorithm);
      alarmExt.set(BAlarmSourceExt.offnormalAlgorithm, stringChangeOfStateAlgorithm, BLocalBacnetDevice.getBacnetContext());
    }
  }

  private static void configureStringChangeOfStateOffnormal(
    BBacnetEventParameter eventParam,
    BStringChangeOfStateAlgorithm algorithm)
      throws EventEnrollmentException
  {
    BBacnetListOf listOfValues = (BBacnetListOf) eventParam.get(BBacnetEventParameter.LIST_OF_ALARM_VALUES_SLOT_NAME);
    BString[] alarmValues = listOfValues.getChildren(BString.class);

    // TODO Handle multiple alarm values

    if (alarmValues.length < 1)
    {
      throw new EventEnrollmentException(
        "String change-of-state alarm extensions require at least 1 alarm value; event type: " +
          BBacnetEventType.tag(eventParam.getChoice()),
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE));
    }

    String alarmValue = alarmValues[0].getString();
    Context context = BLocalBacnetDevice.getBacnetContext();
    algorithm.setString(BStringChangeOfStateAlgorithm.expression, alarmValue, context);
    algorithm.setBoolean(BStringChangeOfStateAlgorithm.normalOnMatch, false, context);
  }

  private void configureStringChangeOfStateFault(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
  {
    BFaultAlgorithm faultAlgorithm = alarmExt.getFaultAlgorithm();
    if (!(alarmExt.getFaultAlgorithm() instanceof BStringChangeOfStateFaultAlgorithm))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing fault algorithm of type " + faultAlgorithm.getType() +
          " with StringChangeOfStateFaultAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      alarmExt.set(BAlarmSourceExt.faultAlgorithm, new BStringChangeOfStateFaultAlgorithm(), BLocalBacnetDevice.getBacnetContext());
    }
  }

  //endregion

  //region ChangeOfStatusFlags

  // Not used. Implementation was in the previous version and has been retained but not enabled
  // until it can be thoroughly tested.
  @SuppressWarnings("unused")
  private void configureChangeOfStatusFlagsExt(
    BBacnetEventParameter eventParam,
    BAlarmSourceExt alarmExt,
    BComponent target)
  {
    // TODO check that the property type matches the event type?
    // int objType = getRemoteObjectType();

    alarmExt = alarmExt == null ? new BAlarmSourceExt() : alarmExt;
    configureAlarmExt(eventParam, alarmExt);
    configureChangeOfStatusFlagsOffnormal(eventParam, alarmExt);
    configureGeneralFaultAlgorithm(eventParam, alarmExt);
    addExtIfMissing(alarmExt, target);
    updateDescriptor(alarmExt);
  }

  private void configureChangeOfStatusFlagsOffnormal(BBacnetEventParameter eventParam, BAlarmSourceExt alarmExt)
  {
    BOffnormalAlgorithm offnormalAlgorithm = alarmExt.getOffnormalAlgorithm();
    if (offnormalAlgorithm instanceof BBacnetStatusAlgorithm)
    {
      configureChangeOfStatusFlagsOffnormal(eventParam, (BBacnetStatusAlgorithm) offnormalAlgorithm);
    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": replacing offnormal algorithm of type " + offnormalAlgorithm.getType() +
          " with BBacnetStatusAlgorithm for event type " + BBacnetEventType.tag(eventParam.getChoice()));
      }
      BBacnetStatusAlgorithm changeOfStatusFlagsAlgorithm = new BBacnetStatusAlgorithm();
      configureChangeOfStatusFlagsOffnormal(eventParam, changeOfStatusFlagsAlgorithm);
      alarmExt.set(
        BAlarmSourceExt.offnormalAlgorithm,
        changeOfStatusFlagsAlgorithm,
        BLocalBacnetDevice.getBacnetContext());
    }
  }

  private static void configureChangeOfStatusFlagsOffnormal(BBacnetEventParameter eventParam, BBacnetStatusAlgorithm algorithm)
  {
    algorithm.set(
      BBacnetStatusAlgorithm.alarmValues,
      eventParam.get(BBacnetEventParameter.STATUS_FLAGS_SLOT_NAME),
      BLocalBacnetDevice.getBacnetContext());
  }

  //endregion

  //endregion

  //region Spy

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    out.startProps();
    out.trTitle("BacnetEventEnrollmentDescriptor", 2);
    out.prop("pointExt", pointExt);
    out.prop("oldId", oldId);
    out.prop("oldName", oldName);
    out.prop("configOk", configOk);
    out.prop("duplicate", duplicate);
    out.prop("typeOfEvent", getTypeOfEvent());
    out.prop("notificationClass", getNotificationClass());
    out.endProps();
  }

  //endregion

  //region Utility

  /**
   * Is the property referenced by this propertyId an array property?
   */
  private static boolean isArray(int propId)
  {
    for (int arrayPropId : ARRAY_PROPS)
    {
      if (propId == arrayPropId)
      {
        return true;
      }
    }

    return false;
  }

  private static final int[] ARRAY_PROPS = {
    BBacnetPropertyIdentifier.EVENT_TIME_STAMPS,
    BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS,
    BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG,
    BBacnetPropertyIdentifier.PROPERTY_LIST,
  };

  private BComponent resolveTarget(BBacnetDeviceObjectPropertyReference objPropRef)
  {
    if (!BacnetDescriptorUtil.isValidDeviceObjectPropertyReference(objPropRef))
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine(getObjectId() + ": objectPropertyReference (" + objPropRef + ") is not valid");
      }
      return null;
    }

    try
    {
      BComponent target = BacnetDescriptorUtil.findLocalOrRemotePoint(objPropRef);
      if (target == null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": did not resolve objectPropertyReference " + objPropRef);
        }
      }

      return target;
    }
    catch (Exception e)
    {
      logException(
        Level.SEVERE,
        new StringBuilder(getObjectId().toString())
          .append(": could not resolve target for objectPropertyReference ")
          .append(objPropRef),
        e);
      return null;
    }
  }

  private void updateDescriptor(BPointExtension pointExt)
  {
    this.pointExt = pointExt;

    Context context = BLocalBacnetDevice.getBacnetContext();
    set(eventEnrollmentOrd, pointExt.getHandleOrd(), context);

    if (getNotificationClass(pointExt) == null)
    {
      // Notification class ID could not be used to set the alarm class on the point ext
      set(reliability, BBacnetReliability.configurationError, context);
    }
    else
    {
      set(reliability, BBacnetReliability.noFaultDetected, context);
    }
  }

  private void resetDescriptor()
  {
    BPointExtension pointExt = this.pointExt;
    if (pointExt != null)
    {
      BComplex parent = pointExt.getParent();
      if (parent instanceof BComponent)
      {
        ((BComponent) parent).remove(pointExt);
      }
    }

    this.pointExt = null;

    Context context = BLocalBacnetDevice.getBacnetContext();
    set(eventEnrollmentOrd, BOrd.NULL, context);
    set(reliability, BBacnetReliability.configurationError, context);
  }

  private static void logException(Level level, StringBuilder message, Exception e)
  {
    if (logger.isLoggable(Level.FINE))
    {
      logger.log(Level.FINE, message.append("; exception: ").append(e.getLocalizedMessage()).toString(), e);
    }
    else if (logger.isLoggable(level))
    {
      logger.log(level, message.append("; exception: ").append(e.getLocalizedMessage()).toString());
    }
  }

  //endregion

  //region Fields

  private static final Logger logger = Logger.getLogger("bacnet.export.object.eventEnrollment");

  private static final int[] REQUIRED_PROPS = {
    BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
    BBacnetPropertyIdentifier.OBJECT_NAME,
    BBacnetPropertyIdentifier.OBJECT_TYPE,
    BBacnetPropertyIdentifier.EVENT_TYPE,
    BBacnetPropertyIdentifier.NOTIFY_TYPE,
    BBacnetPropertyIdentifier.EVENT_PARAMETERS,
    BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE,
    BBacnetPropertyIdentifier.EVENT_STATE,
    BBacnetPropertyIdentifier.EVENT_ENABLE,
    BBacnetPropertyIdentifier.ACKED_TRANSITIONS,
    BBacnetPropertyIdentifier.NOTIFICATION_CLASS,
    BBacnetPropertyIdentifier.EVENT_TIME_STAMPS,
    BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE,
    BBacnetPropertyIdentifier.STATUS_FLAGS,
    BBacnetPropertyIdentifier.RELIABILITY
  };

  private static final int[] OPTIONAL_PROPS = {
    BBacnetPropertyIdentifier.DESCRIPTION,
    BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS,
    BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG,
    BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT_REF,
    BBacnetPropertyIdentifier.EVENT_ALGORITHM_INHIBIT
  };

  private static final PropertyValue[] EMPTY_PROP_VALUE_ARRAY = new PropertyValue[0];

  private static final BBacnetBitString STATUS_FLAGS_DEFAULT = BBacnetBitString.make(new boolean[] {
    /* inAlarm */ false,
    /* inFault */ true, // reliability is CONFIGURATION_ERROR and not NO_FAULT_DETECTED
    /* isOverridden */ false,
    /* isOutOfService */ false });

  private static final BLink[] EMPTY_LINKS_ARRAY = {};

  // The pointExt is usually instance BAlarmSourceExt except for BufferReady event types, which are
  // BBacnetTrendLogAlarmSourceExt.
  private BPointExtension pointExt;

  // Holds any Event_Enable BACnet property writes until the point ext can be configured.
  private BAlarmTransitionBits eventEnable = BAlarmTransitionBits.DEFAULT;

  // Holds the text values until the point ext can be configured.
  private String toOffnormalText = "";
  private String toFaultText = "";
  private String toNormalText = "";

  // Holds the alarm inhibit info until the point ext can be configured.
  private boolean eventAlgorithmInhibit;
  private static final BBacnetObjectPropertyReference OBJECT_PROP_REF_DEFAULT = new BBacnetObjectPropertyReference(
    BBacnetObjectIdentifier.make(BBacnetObjectType.ANALOG_INPUT, BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER));
  private BBacnetObjectPropertyReference eventAlgorithmInhibitRef = OBJECT_PROP_REF_DEFAULT;

  private BBacnetObjectIdentifier oldId;
  private String oldName;
  private boolean duplicate;
  private boolean configOk;

  //endregion

  private static class EventEnrollmentException extends Exception
  {
    public EventEnrollmentException(String message, ErrorType errorType)
    {
      super(message);
      this.errorType = errorType;
    }

    final ErrorType errorType;
  }
}
