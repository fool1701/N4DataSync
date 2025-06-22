/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import static javax.baja.bacnet.enums.BBacnetErrorClass.property;
import static javax.baja.bacnet.enums.BBacnetErrorCode.valueOutOfRange;
import static javax.baja.bacnet.enums.BBacnetLoggingType.cov;
import static javax.baja.bacnet.enums.BBacnetLoggingType.polled;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetClientCov;
import javax.baja.bacnet.datatypes.BBacnetDate;
import javax.baja.bacnet.datatypes.BBacnetDateTime;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetTime;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetLoggingType;
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
import javax.baja.bacnet.point.BBacnetTuningPolicy;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.history.BCapacity;
import javax.baja.history.BFullPolicy;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryService;
import javax.baja.history.BIHistory;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.history.db.HistoryDatabaseConnection;
import javax.baja.history.ext.BCovHistoryExt;
import javax.baja.history.ext.BHistoryExt;
import javax.baja.history.ext.BIntervalHistoryExt;
import javax.baja.history.ext.BNumericCovHistoryExt;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.ObjectTypeList;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.datatypes.BTrendEvent;
import com.tridium.bacnet.history.BBacnetActivePeriod;
import com.tridium.bacnet.history.BBacnetNumericTrendLogExt;
import com.tridium.bacnet.history.BBacnetNumericTrendLogRemoteExt;
import com.tridium.bacnet.history.BBacnetTrendLogAlarmSourceExt;
import com.tridium.bacnet.history.BBacnetTrendLogRemoteExt;
import com.tridium.bacnet.history.BIBacnetTrendLogExt;
import com.tridium.bacnet.history.BacnetTrendLogUtil;
import com.tridium.bacnet.services.BacnetConfirmedRequest;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.NChangeListError;

/**
 * BBacnetTrendLogDescriptor exports a Bacnet trend log extension to Bacnet.
 *
 * @author Craig Gemmill on 12 Aug 03
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bacnet:IBacnetTrendLogExt"
  )
)
/*
 the ord to the exposed Trend Log Ext.
 */
@NiagaraProperty(
  name = "logOrd",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  facets = @Facet(name = "BFacets.TARGET_TYPE", value = "\"baja:Component\"")
)
/*
 objectId is the identifier by which this history is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG)",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 the ord to the history.
 */
@NiagaraProperty(
  name = "historyOrd",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY
)
/*
 the name by which this object is known to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectName",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "logDeviceObjectPropertyReference",
  type = "BBacnetDeviceObjectPropertyReference",
  defaultValue = "new BBacnetDeviceObjectPropertyReference()",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "covResubscriptionInterval",
  type = "int",
  defaultValue = "5"
)
@NiagaraProperty(
  name = "reliability",
  type = "BBacnetReliability",
  defaultValue = "BBacnetReliability.configurationError",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "clientCovIncrement",
  type = "BBacnetClientCov",
  defaultValue = "new BBacnetClientCov()"
)
public class BBacnetTrendLogDescriptor
  extends BBacnetEventSource
  implements BacnetPropertyListProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetTrendLogDescriptor(191066474)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "logOrd"

  /**
   * Slot for the {@code logOrd} property.
   * the ord to the exposed Trend Log Ext.
   * @see #getLogOrd
   * @see #setLogOrd
   */
  public static final Property logOrd = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BOrd.DEFAULT, BFacets.make(BFacets.TARGET_TYPE, "baja:Component"));

  /**
   * Get the {@code logOrd} property.
   * the ord to the exposed Trend Log Ext.
   * @see #logOrd
   */
  public BOrd getLogOrd() { return (BOrd)get(logOrd); }

  /**
   * Set the {@code logOrd} property.
   * the ord to the exposed Trend Log Ext.
   * @see #logOrd
   */
  public void setLogOrd(BOrd v) { set(logOrd, v, null); }

  //endregion Property "logOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this history is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG), null);

  /**
   * Get the {@code objectId} property.
   * objectId is the identifier by which this history is known
   * to the Bacnet world.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * objectId is the identifier by which this history is known
   * to the Bacnet world.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "historyOrd"

  /**
   * Slot for the {@code historyOrd} property.
   * the ord to the history.
   * @see #getHistoryOrd
   * @see #setHistoryOrd
   */
  public static final Property historyOrd = newProperty(Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY, BOrd.DEFAULT, null);

  /**
   * Get the {@code historyOrd} property.
   * the ord to the history.
   * @see #historyOrd
   */
  public BOrd getHistoryOrd() { return (BOrd)get(historyOrd); }

  /**
   * Set the {@code historyOrd} property.
   * the ord to the history.
   * @see #historyOrd
   */
  public void setHistoryOrd(BOrd v) { set(historyOrd, v, null); }

  //endregion Property "historyOrd"

  //region Property "objectName"

  /**
   * Slot for the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #getObjectName
   * @see #setObjectName
   */
  public static final Property objectName = newProperty(0, "", null);

  /**
   * Get the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #objectName
   */
  public String getObjectName() { return getString(objectName); }

  /**
   * Set the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
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
  public static final Property description = newProperty(0, "", null);

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

  //region Property "logDeviceObjectPropertyReference"

  /**
   * Slot for the {@code logDeviceObjectPropertyReference} property.
   * @see #getLogDeviceObjectPropertyReference
   * @see #setLogDeviceObjectPropertyReference
   */
  public static final Property logDeviceObjectPropertyReference = newProperty(Flags.READONLY | Flags.HIDDEN, new BBacnetDeviceObjectPropertyReference(), null);

  /**
   * Get the {@code logDeviceObjectPropertyReference} property.
   * @see #logDeviceObjectPropertyReference
   */
  public BBacnetDeviceObjectPropertyReference getLogDeviceObjectPropertyReference() { return (BBacnetDeviceObjectPropertyReference)get(logDeviceObjectPropertyReference); }

  /**
   * Set the {@code logDeviceObjectPropertyReference} property.
   * @see #logDeviceObjectPropertyReference
   */
  public void setLogDeviceObjectPropertyReference(BBacnetDeviceObjectPropertyReference v) { set(logDeviceObjectPropertyReference, v, null); }

  //endregion Property "logDeviceObjectPropertyReference"

  //region Property "covResubscriptionInterval"

  /**
   * Slot for the {@code covResubscriptionInterval} property.
   * @see #getCovResubscriptionInterval
   * @see #setCovResubscriptionInterval
   */
  public static final Property covResubscriptionInterval = newProperty(0, 5, null);

  /**
   * Get the {@code covResubscriptionInterval} property.
   * @see #covResubscriptionInterval
   */
  public int getCovResubscriptionInterval() { return getInt(covResubscriptionInterval); }

  /**
   * Set the {@code covResubscriptionInterval} property.
   * @see #covResubscriptionInterval
   */
  public void setCovResubscriptionInterval(int v) { setInt(covResubscriptionInterval, v, null); }

  //endregion Property "covResubscriptionInterval"

  //region Property "reliability"

  /**
   * Slot for the {@code reliability} property.
   * @see #getReliability
   * @see #setReliability
   */
  public static final Property reliability = newProperty(Flags.READONLY | Flags.HIDDEN, BBacnetReliability.configurationError, null);

  /**
   * Get the {@code reliability} property.
   * @see #reliability
   */
  public BBacnetReliability getReliability() { return (BBacnetReliability)get(reliability); }

  /**
   * Set the {@code reliability} property.
   * @see #reliability
   */
  public void setReliability(BBacnetReliability v) { set(reliability, v, null); }

  //endregion Property "reliability"

  //region Property "clientCovIncrement"

  /**
   * Slot for the {@code clientCovIncrement} property.
   * @see #getClientCovIncrement
   * @see #setClientCovIncrement
   */
  public static final Property clientCovIncrement = newProperty(0, new BBacnetClientCov(), null);

  /**
   * Get the {@code clientCovIncrement} property.
   * @see #clientCovIncrement
   */
  public BBacnetClientCov getClientCovIncrement() { return (BBacnetClientCov)get(clientCovIncrement); }

  /**
   * Set the {@code clientCovIncrement} property.
   * @see #clientCovIncrement
   */
  public void setClientCovIncrement(BBacnetClientCov v) { set(clientCovIncrement, v, null); }

  //endregion Property "clientCovIncrement"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTrendLogDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  public BBacnetTrendLogDescriptor()
  {
  }

  /**
   * Started.
   * Initialize the log name subscriber and check the export configuration.
   */
  @Override
  public final void started()
    throws Exception
  {
    super.started();

    // Export the history and initialize the local copies.
    oldId = getObjectId();
    oldName = getObjectName();
    logSubscriber = new BacnetTrendLogSubscriber(this, getLog());
    checkConfiguration();

    // Increment the Device object's Database_Revision for created objects.
    if (Sys.isStationStarted())
    {
      BBacnetNetwork.localDevice().incrementDatabaseRevision();
    }
  }

  /**
   * Stopped.
   * Clean up the log name subscriber and null references.
   */
  @Override
  public final void stopped()
    throws Exception
  {
    super.stopped();

    // unexport
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    local.unexport(oldId, oldName, this);

    // Clear the local copies.
    logSubscriber.unsubscribeAll();
    optionalProps = null;
    logSubscriber = null;
    tlog = null;
    oldId = null;
    oldName = null;

    // Increment the Device object's Database_Revision for deleted objects.
    if (local.isRunning())
    {
      local.incrementDatabaseRevision();
    }
  }

  /**
   * Changed.
   * If the objectId changes, make sure the new ID is not already in use.
   * If it is, reset it to the current value.
   */
  @Override
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
    {
      return;
    }

    if (p.equals(objectId))
    {
      BBacnetNetwork.localDevice().unexport(oldId, oldName, this);
      checkConfiguration();
      oldId = getObjectId();

      try
      {
        ((BComponent)getParent()).rename(getPropertyInParent(), getObjectId().toString(nameContext));
      }
      catch (DuplicateSlotException ignored)
      {}

      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(objectName))
    {
      BBacnetNetwork.localDevice().unexport(oldId, oldName, this);
      checkConfiguration();
      oldName = getObjectName();
      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(logOrd))
    {
      checkConfiguration();
      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
  }

  /**
   * Get slot facets.
   *
   * @param s target slot.
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s.equals(objectId))
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.TREND_LOG);
    }

    return super.getSlotFacets(s);
  }

////////////////////////////////////////////////////////////////
// BIBacnetExportObject
////////////////////////////////////////////////////////////////

  /**
   * Get the exported object.
   *
   * @return the actual exported object by resolving the object ord.
   */
  @Override
  public final BObject getObject()
  {
    return (BObject)getLog();
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getLogOrd();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(logOrd, objectOrd, cx);
  }

  /**
   * Check the configuration of this object.
   */
  @Override
  public void checkConfiguration()
  {
    // quit if fatal fault
    if (isFatalFault())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      return;
    }

    // Clear the name subscriber.
    logSubscriber.unsubscribeAll();
    boolean configOk = true;

    // Check the configuration.
    if (getLog() == null && !isDynamicallyCreated())
    {
      setFaultCause("Cannot find exported history");
      configOk = false;
    }
    else
    {
      logSubscriber.config = tlog.getHistoryConfig();
      logSubscriber.subscribe(tlog.getHistoryConfig());

      if (!(isDynamicallyCreated()))
      {
        logSubscriber.subscribe((BComponent)((BComplex)tlog).getParent());
        BBacnetDeviceObjectPropertyReference logDOPRef = getLogDOPRef();
        if (logDOPRef != NULL_DOPR)
        {
          setLogDeviceObjectPropertyReference(logDOPRef);
        }
        else
        {
          logger.severe("Cannot write log device object property in static trend log extension on unexported control object");
        }
      }

      if (objectName.isEquivalentToDefaultValue(get(objectName)))
      {
        setObjectName(tlog.getHistoryConfig().getId().getHistoryName());
      }

      setHistoryOrd(BOrd.make("history:" + tlog.getHistoryConfig().getId().toString()));
    }

    // Check for valid object id.
    if (!getObjectId().isValid())
    {
      setFaultCause("Invalid Object ID");
      configOk = false;
    }

    if (configOk)
    {
      // Try to export - duplicate id & names will be checked in here.
      String err = BBacnetNetwork.localDevice().export(this);
      if (err != null)
      {
        duplicate = true;
        setFaultCause(err);
        configOk = false;
      }
      else
      {
        duplicate = false;
      }
    }

    // Set the exported flag.
    if (configOk)
    {
      setFaultCause("");
    }

    setStatus(BStatus.makeFault(getStatus(), !configOk));
  }

////////////////////////////////////////////////////////////////
// BBacnetEventSource
////////////////////////////////////////////////////////////////

  /**
   * Is the given alarm source ext a valid extension for
   * exporting BACnet alarm properties?  This determines if the
   * given alarm source extension follows the appropriate algorithm
   * defined for the intrinsic alarming of a particular object
   * type as required by the BACnet specification.<p>
   * BACnet BinaryOutput points use a CommandFailure alarm algorithm.
   *
   * @param ext
   * @return true if valid, otherwise false.
   */
  @Override
  public boolean isValidAlarmExt(BIAlarmSource ext)
  {
    return ext instanceof BBacnetTrendLogAlarmSourceExt;
  }

  @SuppressWarnings("deprecation")
  @Override
  @Deprecated
  protected void updateAlarmInhibit()
  {
  }

  /**
   * Is this object currently configured to support event initiation?
   * This will return false if the exported object does not have an
   * appropriate alarm extension configured to allow Bacnet event initiation.
   *
   * @return true if this object can initiate Bacnet events.
   */
  @Override
  public final boolean isEventInitiationEnabled()
  {
    return getNotificationClass() != null;
  }

  /**
   * Get the current Event_State of the object.
   * If the exported object also has an alarm extension, this
   * returns the current event state as translated from the
   * alarm extension's alarm state.  Otherwise, it returns null.
   *
   * @return the object's event state if configured for alarming, or null.
   */
  @Override
  public final BEnum getEventState()
  {
    BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      return BBacnetEventState.make(almExt.getAlarmState());
    }
  }

  /**
   * Get the current Acknowledged_Transitions property of the object.
   * If the exported object also has an alarm extension, this
   * returns the current acked transitions as translated from the
   * alarm extension's alarm transitions.  Otherwise, it returns null.
   *
   * @return the object's acknowledged transitions if configured for alarming, or null.
   */
  @Override
  public final BBacnetBitString getAckedTransitions()
  {
    BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      return BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAckedTransitions());
    }
  }

  /**
   * Get the event time stamps.
   *
   * @return the event time stamps, or null if event initiation is not enabled.
   */
  @Override
  public final BBacnetTimeStamp[] getEventTimeStamps()
  {
    BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      BBacnetTimeStamp[] ets = new BBacnetTimeStamp[3];
      BAbsTime t = almExt.getToOffnormalTimes().getAlarmTime();
      if (BAbsTime.DEFAULT.equals(t))
      {
        ets[0] = new BBacnetTimeStamp(new BBacnetDateTime());
      }
      else
      {
        ets[0] = new BBacnetTimeStamp(t);
      }
      t = almExt.getToFaultTimes().getAlarmTime();
      if (BAbsTime.DEFAULT.equals(t))
      {
        ets[1] = new BBacnetTimeStamp(new BBacnetDateTime());
      }
      else
      {
        ets[1] = new BBacnetTimeStamp(t);
      }
      t = almExt.getToNormalTimes().getAlarmTime();
      if (BAbsTime.DEFAULT.equals(t))
      {
        ets[2] = new BBacnetTimeStamp(new BBacnetDateTime());
      }
      else
      {
        ets[2] = new BBacnetTimeStamp(t);
      }

      return ets;
    }
  }

  /**
   * Get the notify type.
   *
   * @return the notify type, or null if event initiation is not enabled.
   */
  @Override
  public final BBacnetNotifyType getNotifyType()
  {
    BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      return almExt.getNotifyType();
    }
  }

  /**
   * Get the event enable bits.
   *
   * @return the event enable bits, or null if event initiation is not enabled.
   */
  @Override
  public final BBacnetBitString getEventEnable()
  {
    BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      return BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAlarmEnable());
    }
  }

  /**
   * Get the event priorities.
   *
   * @return the event priorities, or null if event initiation is not enabled.
   */
  @Override
  public final int[] getEventPriorities()
  {
    BBacnetNotificationClassDescriptor nc = getNotificationClass();
    if (nc == null)
    {
      return null;
    }
    else
    {
      return nc.getEventPriorities();
    }
  }

  /**
   * Get the Notification Class object for this event source.
   *
   * @return the <code>BacnetNotificationClassDescriptor</code> for this object.
   */
  @Override
  public BBacnetNotificationClassDescriptor getNotificationClass()
  {
    return BacnetTrendLogUtil.getNotificationClass(tlog);
  }

  /**
   * Get the BACnetEventType reported by this object.
   */
  @Override
  public BEnum getEventType()
  {
    return BBacnetEventType.bufferReady;
  }

////////////////////////////////////////////////////////////////
// Bacnet Request Execution
////////////////////////////////////////////////////////////////

  /**
   * Get the value of a property.
   *
   * @param ref the PropertyReference containing id and index.
   * @return a PropertyValue containing either the encoded value or the error.
   */
  @Override
  public final PropertyValue readProperty(PropertyReference ref)
    throws RejectException
  {
    getLog();
    return readProperty(ref.getPropertyId(), ref.getPropertyArrayIndex());
  }

  /**
   * Read the value of multiple Bacnet properties.
   *
   * @param refs the list of property references.
   * @return an array of PropertyValues.
   */
  @Override
  public final PropertyValue[] readPropertyMultiple(PropertyReference[] refs)
    throws RejectException
  {
    getLog();
    PropertyValue[] readResults = new PropertyValue[0];
    ArrayList<PropertyValue> results = new ArrayList<>(refs.length);
    for (int i = 0; i < refs.length; i++)
    {
      int[] props;
      switch (refs[i].getPropertyId())
      {
        case BBacnetPropertyIdentifier.ALL:
          props = REQUIRED_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          props = getOptionalProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.OPTIONAL:
          props = getOptionalProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.REQUIRED:
          props = REQUIRED_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        default:
          results.add(readProperty(refs[i].getPropertyId(), refs[i].getPropertyArrayIndex()));
          break;
      }
    }

    return results.toArray(readResults);
  }

  /**
   * Read the specified range of values of a compound property.
   *
   * @param rangeReference the range reference describing the requested range.
   * @return a byte array containing the encoded range.
   */
  @Override
  public final RangeData readRange(RangeReference rangeReference)
    throws RejectException
  {
    getLog();
    if (tlog == null)
    {
      return new ReadRangeAck(BBacnetErrorClass.OBJECT, BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }

    if (rangeReference.getPropertyArrayIndex() >= 0)
    {
      if (!isArray(rangeReference.getPropertyId()))
      {
        return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }

    // This is where I need to loop through both of my logs (data and event)
    // and return the proper log entries based on sequence number
    int pId = rangeReference.getPropertyId();
    if (pId == BBacnetPropertyIdentifier.LOG_BUFFER)
    {
      Integer pointAsnType = null;
      BAbstractProxyExt pxExt = getPoint().getProxyExt();
      if (pxExt instanceof BBacnetProxyExt)
      {
        BBacnetProxyExt bacPxExt = (BBacnetProxyExt)pxExt;
        pointAsnType = Integer.valueOf(AsnUtil.getAsnType(bacPxExt.getDataType()));
      }
      else
      {
        BBacnetDeviceObjectPropertyReference dopr = getLogDOPRef();
        if (dopr != null && dopr != NULL_DOPR)
        {
          BBacnetObjectIdentifier oid = null;
          if ((oid = dopr.getObjectId()) != null)
          {
            int objectType = oid.getObjectType();
            int propId = dopr.getPropertyId();
            PropertyInfo info = ObjectTypeList.getInstance().getPropertyInfo(objectType, propId);
            pointAsnType = Integer.valueOf(info.getAsnType());
          }
        }
      }

      // Determine maximum space available for record data.  This is the maxDataLength
      // from the request, minus the other necessary parts of the APDU.
      //    3     // CxAck-PDU header + invID + RdRngAck
      //    + 5   // objectId
      //    + 2   // propertyId (could be >2 for other properties?)
      //    + 3   // resultFlags
      //    + 2   // itemCount (could be >2 for large packets?)
      //    + 2   // itemData o/c tags
      int maxDataSize = -1;
      if (rangeReference instanceof BacnetConfirmedRequest)
      {
        maxDataSize = ((BacnetConfirmedRequest)rangeReference).getMaxDataLength();
        maxDataSize -= 17;
      }

      switch (rangeReference.getRangeType())
      {
        case RangeReference.BY_POSITION:
          long referenceNum = rangeReference.getReferenceIndex();
          int count = rangeReference.getCount();
          try
          {
            // Read the history records, using the appropriate method.
            RangeData rlr = BacnetTrendLogUtil.readRangeByPosition(tlog, referenceNum, count, maxDataSize, pointAsnType);

            return new ReadRangeAck(getObjectId(),
                                    pId,
                                    NOT_USED,
                                    rlr.getResultFlags(),
                                    rlr.getItemCount(),
                                    NOT_USED,
                                    rlr.getItemData());
          }
          catch (Exception e)
          {
            return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER);
          }

        case RangeReference.BY_TIME:
          // Adjust maxDataSize to account for the firstSequenceNumber parameter.
          maxDataSize -= 5;

          BBacnetDateTime refTime = rangeReference.getReferenceTime();
          count = rangeReference.getCount();
          try
          {
            // Read the history records, using the appropriate method.
            RangeData rlr = BacnetTrendLogUtil.readRangeByTime(tlog, refTime, count, maxDataSize, pointAsnType);

            return new ReadRangeAck(getObjectId(),
                                    pId,
                                    NOT_USED,
                                    rlr.getResultFlags(),
                                    rlr.getItemCount(),
                                    (rlr.getItemCount() > 0) ? rlr.getFirstSequenceNumber() : NOT_USED,
                                    rlr.getItemData());
          }
          catch (Exception e)
          {
            return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER);
          }

        case RangeReference.BY_SEQUENCE_NUMBER:
          // Adjust maxDataSize to account for the firstSequenceNumber parameter.
          maxDataSize -= 5;

          long startSeqNum = rangeReference.getReferenceIndex();
          count = rangeReference.getCount();
          try
          {
            // Read the history records, using the appropriate method.
            RangeData rlr = BacnetTrendLogUtil.readRangeBySequence(tlog, startSeqNum, count, maxDataSize, pointAsnType);

            return new ReadRangeAck(getObjectId(),
                                    pId,
                                    NOT_USED,
                                    rlr.getResultFlags(),
                                    rlr.getItemCount(),
                                    (rlr.getItemCount() > 0) ? rlr.getFirstSequenceNumber() : NOT_USED,
                                    rlr.getItemData());
          }
          catch (Exception e)
          {
            return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER);
          }

        case NOT_USED:
          try
          {
            // Read the history records, using the appropriate method.
            RangeData rlr = BacnetTrendLogUtil.readRangeAll(tlog, maxDataSize, pointAsnType);

            return new ReadRangeAck(getObjectId(),
                                    pId,
                                    NOT_USED,
                                    rlr.getResultFlags(),
                                    rlr.getItemCount(),
                                    (rlr.getItemCount() > 0) ? rlr.getFirstSequenceNumber() : NOT_USED,
                                    rlr.getItemData());
          }
          catch (Exception e)
          {
            return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER);
          }

        default:
          logger.warning("Unsupported ReadRange Range Type: " + rangeReference.getRangeType());
          return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER);
      }
    }

    // Handle all other properties here.
    for (int i = 0; i < REQUIRED_PROPS.length; i++)
    {
      if (pId == REQUIRED_PROPS[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    int[] props = getOptionalProps();
    for (int i = 0; i < props.length; i++)
    {
      if (pId == props[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY);
  }

  /**
   * Set the value of a property.
   *
   * @param val the PropertyValue containing the write information.
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  @Override
  public final ErrorType writeProperty(PropertyValue val)
    throws BacnetException
  {
    getLog();
    return writeProperty(val.getPropertyId(),
                         val.getPropertyArrayIndex(),
                         val.getPropertyValue(),
                         val.getPriority());
  }

  /**
   * Add list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to add any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError addListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getLog();
    if (tlog == null && !isDynamicallyCreated())
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT, BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.LOG_BUFFER)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
    }
    else
    {
      int propertyId = propertyValue.getPropertyId();
      for (int i = 0; i < REQUIRED_PROPS.length; i++)
      {
        if (propertyId == REQUIRED_PROPS[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }

      int[] props = getOptionalProps();
      for (int i = 0; i < props.length; i++)
      {
        if (propertyId == props[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }

      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY),
                                  0);
    }
  }

  /**
   * Remove list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to remove any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError removeListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getLog();
    if (tlog == null)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT, BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.LOG_BUFFER)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
    }
    else
    {
      int propertyId = propertyValue.getPropertyId();
      for (int i = 0; i < REQUIRED_PROPS.length; i++)
      {
        if (propertyId == REQUIRED_PROPS[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }

      int[] props = getOptionalProps();
      for (int i = 0; i < props.length; i++)
      {
        if (propertyId == props[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES, BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }

      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY),
                                  0);
    }
  }

////////////////////////////////////////////////////////////////
// Bacnet Support
////////////////////////////////////////////////////////////////

  /**
   * Is the property referenced by this propertyId an array property?
   *
   * @param propertyId
   * @return true if it is an array property, false if not or if the
   * propertyId does not refer to a property in this object.
   */
  boolean isArray(int propertyId)
  {
    for (int arrayPropId : ARRAY_PROPS)
    {
      if (propertyId == arrayPropId)
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
    findLog();
    if (tlog == null)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.OBJECT,
                                                              BBacnetErrorCode.TARGET_NOT_CONFIGURED));
    }

    // Check for array index on non-array property.
    if (ndx >= 0)
    {
      if (!isArray(pId))
      {
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY));
      }
    }
    else if (ndx < NOT_USED)
    {
      return new NReadPropertyResult(
        pId,
        ndx,
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }

    switch (pId)
    {
      case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnObjectId(getObjectId()));

      case BBacnetPropertyIdentifier.OBJECT_NAME:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getObjectName()));

      case BBacnetPropertyIdentifier.OBJECT_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getObjectId().getObjectType()));

      case BBacnetPropertyIdentifier.DESCRIPTION:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getDescription()));

      case BBacnetPropertyIdentifier.PROPERTY_LIST:
        return readPropertyList(ndx);

      case BBacnetPropertyIdentifier.ENABLE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(tlog.getEnabled()));

      case BBacnetPropertyIdentifier.STOP_WHEN_FULL:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(tlog.getHistoryConfig().getFullPolicy().equals(BFullPolicy.stop)));

      case BBacnetPropertyIdentifier.BUFFER_SIZE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(BacnetTrendLogUtil.getMaxRecords(tlog)));

      case BBacnetPropertyIdentifier.LOG_BUFFER:
        // logBuffer is not accessible except through the ReadRange service.
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.READ_ACCESS_DENIED));

      case BBacnetPropertyIdentifier.RECORD_COUNT:
        long recCount = 0L;
        try (HistoryDatabaseConnection conn = getHistoryDbConnection(BLocalBacnetDevice.getBacnetContext()))
        {
          BIHistory hist = getHistory(conn);
          if (getHistory(conn) != null)
          {
            recCount = conn.getRecordCount(hist);
          }
        }
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(recCount));

      case BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(tlog.getTotalRecordCount()));

      case BBacnetPropertyIdentifier.EVENT_STATE:
        return readEventState();
      case BBacnetPropertyIdentifier.LOGGING_TYPE:
        return readLoggingType();
      case BBacnetPropertyIdentifier.STATUS_FLAGS:
        return new NReadPropertyResult(pId, ndx, AsnUtil.statusToAsnStatusFlags(BStatus.ok));

      default:
        return readOptionalProperty(pId, ndx);
    }
  }

  private PropertyValue readEventState()
  {
    if (!getEventDetectionEnable())
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_STATE, NOT_USED, AsnUtil.toAsnEnumerated(BBacnetEventState.NORMAL));
    }

    BBacnetTrendLogAlarmSourceExt alarmExt = getAlarmExt();
    if (alarmExt == null)
    {
      // Object does not support event reporting, set to Normal.
      return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_STATE, NOT_USED, AsnUtil.toAsnEnumerated(BBacnetEventState.NORMAL));
    }

    if (tlog instanceof BCovHistoryExt)
    {
      BControlPoint point = getPoint();
      BAbstractProxyExt pxExt = point.getProxyExt();
      if (pxExt instanceof BBacnetProxyExt)
      {
        BBacnetProxyExt bac = (BBacnetProxyExt)pxExt;
        if (bac.useCov() && !bac.isCOV())
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_STATE, NOT_USED, AsnUtil.toAsnEnumerated(BBacnetEventState.FAULT));
        }
        else
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_STATE, NOT_USED, AsnUtil.toAsnEnumerated(BBacnetEventState.NORMAL));
        }
      }
    }

    return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_STATE, NOT_USED, AsnUtil.toAsnEnumerated(BBacnetEventState.fromBAlarmState(alarmExt.getAlarmState())));
  }

  private PropertyValue readLoggingType()
  {
    // BHistoryExt (abstract)
    //   BCovHistoryExt (abstract)
    //     BBooleanCovHistoryExt
    //       1 BBacnetBooleanCovTrendLogExt
    //     BEnumCovHistoryExt
    //       1 BBacnetEnumCovTrendLogExt
    //     BNumericCovHistoryExt
    //       1 BBacnetNumericCovTrendLogExt
    //     BStringCovHistoryExt
    //       1 BBacnetStringCovTrendLogExt
    //   BIntervalHistoryExt (abstract, has interval property, interval must be greater than zero)
    //     BBooleanIntervalHistoryExt
    //       1 BBacnetBooleanIntervalTrendLogExt
    //         1,2 BBacnetBooleanTrendLogExt (interval may be zero)
    //     BEnumIntervalHistoryExt
    //       1 BBacnetEnumIntervalTrendLogExt
    //         1,2 BBacnetEnumTrendLogExt (interval may be zero)
    //     BNumericIntervalHistoryExt
    //       1 BBacnetNumericIntervalTrendLogExt
    //         1 BBacnetBitStringTrendLogExt
    //         1,2 BBacnetNumericTrendLogExt (interval may be zero)
    //     BStringIntervalHistoryExt
    //       1 BBacnetStringIntervalTrendLogExt
    //         1,2 BBacnetStringTrendLogExt (interval may be zero)
    //     1,2 BBacnetTrendLogRemoteExt (abstract, interval may be zero)
    //       1,2 BBacnetBooleanTrendLogRemoteExt
    //       1,2 BBacnetEnumTrendLogRemoteExt
    //       1,2 BBacnetNumericTrendLogRemoteExt
    //       1,2 BBacnetBitStringTrendLogRemoteExt
    //       1,2 BBacnetStringTrendLogRemoteExt
    //
    // 1 BIBacnetTrendLogExt
    // 2 isGenericTrendLogExtension
    if (BacnetDescriptorUtil.isGenericTrendLogExtension(tlog))
    {
      return ((BIntervalHistoryExt) getLog()).getInterval().getMillis() == 0 ?
        makeLoggingTypeResult(cov) :
        makeLoggingTypeResult(polled);
    }
    else if (tlog instanceof BCovHistoryExt)
    {
      return makeLoggingTypeResult(cov);
    }
    else if (tlog instanceof BIntervalHistoryExt)
    {
      return makeLoggingTypeResult(polled);
    }
    else
    {
      logger.warning(this + ": trend log ext type is not supported: " + tlog.getClass());
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.LOGGING_TYPE,
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OTHER));
    }
  }

  private static PropertyValue makeLoggingTypeResult(BBacnetLoggingType type)
  {
    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.LOGGING_TYPE,
      AsnUtil.toAsnEnumerated(type));
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
    BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
          BBacnetNotificationClassDescriptor nc = getNotificationClass();
          if (nc == null)
          {
            return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                    BBacnetErrorCode.UNKNOWN_PROPERTY));
          }
          else
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(nc.getNotificationClass()));
          }

        case BBacnetPropertyIdentifier.EVENT_ENABLE:
          return new NReadPropertyResult(
            pId,
            ndx,
            AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAlarmEnable())));
        case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getEventDetectionEnable()));

        case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
          return readAckedTransitions(almExt.getAckedTransitions());
        case BBacnetPropertyIdentifier.NOTIFY_TYPE:
          return new NReadPropertyResult(
            pId,
            ndx,
            AsnUtil.toAsnEnumerated(almExt.getNotifyType()));
        case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
          return readEventTimeStamps(
            almExt.getToOffnormalTimes().getAlarmTime(),
            almExt.getToFaultTimes().getAlarmTime(),
            almExt.getToNormalTimes().getAlarmTime(),
            ndx);
        case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
          return readEventMessageTexts(ndx);

        case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
          return readEventMessageTextsConfig(
            almExt.getToOffnormalText().getFormat(),
            almExt.getToFaultText().getFormat(),
            almExt.getToNormalText().getFormat(),
            ndx);

        case BBacnetPropertyIdentifier.NOTIFICATION_THRESHOLD:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getNotificationThreshold()));

        case BBacnetPropertyIdentifier.RECORDS_SINCE_NOTIFICATION:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getRecordsSinceNotification()));

        case BBacnetPropertyIdentifier.LAST_NOTIFY_RECORD:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getLastNotifyRecord()));

        default:
          break;  // Fall through to the next round of checks.
      }
    }

    SlotCursor<Property> c;
    BControlPoint point = getPoint();
    switch (pId)
    {
      // optional properties for logging bacnet-exposed objects
      case BBacnetPropertyIdentifier.TRIGGER:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(tlog.getTrigger()));

      case BBacnetPropertyIdentifier.START_TIME:
        if (tlog.getActivePeriod() instanceof BBacnetActivePeriod)
        {
          BBacnetDateTime startTime = ((BBacnetActivePeriod)(tlog.getActivePeriod())).getStartTime();
          if (startTime != null)
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsn(startTime));
          }
        }
        break;

      case BBacnetPropertyIdentifier.STOP_TIME:
        if (tlog.getActivePeriod() instanceof BBacnetActivePeriod)
        {
          BBacnetDateTime stopTime = ((BBacnetActivePeriod)(tlog.getActivePeriod())).getStopTime();
          if (stopTime != null)
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsn(stopTime));
          }
        }
        break;

      case BBacnetPropertyIdentifier.LOG_DEVICE_OBJECT_PROPERTY:
        BBacnetDeviceObjectPropertyReference dopRef = getLogDeviceObjectPropertyReference();
        if (dopRef == NULL_DOPR)
        {
          return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                  BBacnetErrorCode.VALUE_NOT_INITIALIZED));
        }
        else
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsn(dopRef));
        }

      case BBacnetPropertyIdentifier.LOG_INTERVAL:
        long interval = 0;
        if (tlog instanceof BIntervalHistoryExt)
        {
          interval = ((BIntervalHistoryExt) getLog()).getInterval().getMillis() / 10;
        }
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(interval));

      case BBacnetPropertyIdentifier.CLIENT_COV_INCREMENT:
        if (tlog instanceof BCovHistoryExt)
        {
          // If the logged point is a BACnet proxy point, then the
          // COV increment is determined by the server, not by us,
          // so return NULL.
          c = point.getProperties();
          if (c.next(BBacnetProxyExt.class))
          {
            BBacnetProxyExt ext = (BBacnetProxyExt)c.get();
            if (ext.isCOV())
            {
              return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnNull());
            }
          }

          // If this is a local point, then return the COV increment
          // determined by our history ext.
          if (tlog instanceof BNumericCovHistoryExt)
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BNumericCovHistoryExt)tlog).getChangeTolerance()));
          }
          else
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnNull());
          }

          //Below couple of else if are added to return COV_increement for newly added generic trend log numeric exts defined for AMEV adherence
        }
        else
        {
          if (getClientCovIncrement().getIncrement().getStatus() != BStatus.nullStatus)
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(getClientCovIncrement().getIncrement().getNumeric()));
          }
          else
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnNull());
          }
        }

      case BBacnetPropertyIdentifier.COV_RESUBSCRIPTION_INTERVAL:

        if (getLog() != null && tlog instanceof BCovHistoryExt)
        {
          c = point.getProperties();
          if (c.next(BBacnetProxyExt.class))
          {
            BBacnetProxyExt ext = (BBacnetProxyExt)c.get();
            BBacnetDevice device = ext.device();
            if (device != null)
            {
              if (ext.isCOV())
              {
                // Return the COV lifetime, divided by our fixed safety factor of 2
                // and multiplied by 60 to convert minutes to seconds.
                return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(ext.getCovSubscriptionLifetime() * 30L));
              }
            }
          }
        }
        else
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(getCovResubscriptionInterval()));
        }
        break;

    }

    return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                            BBacnetErrorCode.UNKNOWN_PROPERTY));
  }

  private NReadPropertyResult readAckedTransitions(BAlarmTransitionBits ackedTrans)
  {
    if (getEventDetectionEnable())
    {
      BAlarmTransitionBits eventTrans = readEventTransition(ackedTrans);
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.ACKED_TRANSITIONS,
        BacnetConst.NOT_USED,
        AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(eventTrans)));
    }
    else
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.ACKED_TRANSITIONS,
        BacnetConst.NOT_USED,
        AsnUtil.toAsnBitString(ACKED_TRANS_DEFAULT));
    }
  }

  /**
   * Set the value of a property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @param pri the priority level (only used for commandable properties).
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    synchronized (asnIn)
    {
      getLog();
      if (ndx >= 0)
      {
        if (!isArray(pId))
        {
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
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
          case BBacnetPropertyIdentifier.OBJECT_NAME:
            return BacUtil.setObjectName(this, objectName, val);

          case BBacnetPropertyIdentifier.STOP_WHEN_FULL:
            if (AsnUtil.fromAsnBoolean(val))
            {
              tlog.getHistoryConfig().setFullPolicy(BFullPolicy.stop);
            }
            else
            {
              tlog.getHistoryConfig().setFullPolicy(BFullPolicy.roll);
            }
            return null;

          case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
          case BBacnetPropertyIdentifier.OBJECT_TYPE:
          case BBacnetPropertyIdentifier.LOG_BUFFER:
          case BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT:
          case BBacnetPropertyIdentifier.EVENT_STATE:
          case BBacnetPropertyIdentifier.LOGGING_TYPE:
          case BBacnetPropertyIdentifier.STATUS_FLAGS:
          case BBacnetPropertyIdentifier.PROPERTY_LIST:
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.LOG_DEVICE_OBJECT_PROPERTY:
            asnIn.setBuffer(val);

            BBacnetDeviceObjectPropertyReference dopr = new BBacnetDeviceObjectPropertyReference();
            dopr.readAsn(asnIn);
            BBacnetDeviceObjectPropertyReference currDopr = getLogDeviceObjectPropertyReference();

            if (BacnetDescriptorUtil.isEqual(dopr, currDopr))
            {
              return null;
            }

            BComponent point = getTargetPoint(new NBacnetPropertyValue(pId, dopr.getPropertyArrayIndex(), val));
            if (point == null)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OPTIONAL_FUNCTIONALITY_NOT_SUPPORTED);
            }

            BIBacnetTrendLogExt trendLogExt = getLog();
            boolean enabled = ((BHistoryExt)trendLogExt).getEnabled();

            ((BHistoryExt) trendLogExt).setEnabled(false);
            if (getLogOrd().isNull() &&
                (point instanceof BControlPoint) &&
                BacnetDescriptorUtil.areTrendLogAndPointCompatible((BControlPoint)point, trendLogExt, dopr) &&
                BacnetDescriptorUtil.isLocalDeviceID(dopr.getDeviceId().getInstanceNumber()))
            {
              String trendLogName = "TrendLog_" + getObjectId().getInstanceNumber();
              point.add(trendLogName, (BIntervalHistoryExt)trendLogExt);
              setLogOrd(((BIntervalHistoryExt) trendLogExt).getHandleOrd());
              BacnetDescriptorUtil.removeHistory(this, false);
            }
            else
            {
              PropertyValue[] pvs = BacnetDescriptorUtil.getValuesWrittenToTrendExtension(this);
              BacnetDescriptorUtil.removeHistory(this, true);
              tlog = null;
              try
              {
                tlog = BacnetDescriptorUtil.copy(this, dopr, pvs);
              }
              catch (BacnetException e)
              {
                return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.OPTIONAL_FUNCTIONALITY_NOT_SUPPORTED);
              }
            }

            setLogDeviceObjectPropertyReference(dopr);

            if (!(tlog instanceof BNumericCovHistoryExt) &&
                !(tlog instanceof BBacnetNumericTrendLogRemoteExt) &&
                !(tlog instanceof BBacnetNumericTrendLogExt))
            {
              setClientCovIncrement(new BBacnetClientCov());
            }
            BBacnetNetwork.localDevice().exportByOrd(this);
            ((BHistoryExt)getLog()).setEnabled(enabled );

            return null;

          case BBacnetPropertyIdentifier.DESCRIPTION:
            setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.ENABLE:
            // When the trendLog is dynamically created with no point reference then
            // a temporary point is added until an actual point is added. While creating
            // temporary point, Enabled is set to false and made readable if it is dynamic created trendlog
            // without actual point.
            if (Flags.isReadonly((BComplex)tlog, BHistoryExt.enabled))
            {
              return new NErrorType(BBacnetErrorClass.OBJECT, BBacnetErrorCode.TARGET_NOT_CONFIGURED);
            }

            // Check if stopped due to buffer full.
            long recCount = 0L;
            try (HistoryDatabaseConnection conn = getHistoryDbConnection(BLocalBacnetDevice.getBacnetContext()))
            {
              BIHistory hist = getHistory(conn);
              if (getHistory(conn) != null)
              {
                recCount = conn.getRecordCount(hist);
              }
            }

            long bufSize = BacnetTrendLogUtil.getMaxRecords(tlog);

            if (tlog.getHistoryConfig().getFullPolicy().equals(BFullPolicy.stop) &&
                (recCount >= bufSize))
            {
              return new NErrorType(BBacnetErrorClass.OBJECT, BBacnetErrorCode.LOG_BUFFER_FULL);
            }

            ((BHistoryExt)getLog()).setBoolean(BHistoryExt.enabled, AsnUtil.fromOnlyAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());

            if (getLogOrd() == null || getLogOrd().isNull())
            {
              setReliability(BBacnetReliability.configurationError);
            }

            return null;

          case BBacnetPropertyIdentifier.BUFFER_SIZE:
            return writeBufferSize(val);

          case BBacnetPropertyIdentifier.RECORD_COUNT:
            long recordCount = AsnUtil.fromAsnUnsignedInteger(val);
            if (recordCount == 0)
            {
              try
              {
                try (HistoryDatabaseConnection conn = getHistoryDbConnection(BLocalBacnetDevice.getBacnetContext()))
                {
                  // Short circuit for histories that haven't yet been started.
                  if (conn.getHistory(tlog.getHistoryConfig().getId()) == null)
                  {
                    return null;
                  }

                  conn.clearAllRecords(tlog.getHistoryConfig().getId());

                  // Append a message to the event log indicating the purged buffer
                  BacnetTrendLogUtil.writeEvent(tlog,
                                                BAbsTime.now(),
                                                BStatus.DEFAULT,
                                                BacnetTrendLogUtil.incrementSequenceNumber(tlog.getTotalRecordCount()),
                                                tlog.getEnabled() ? BTrendEvent.LOG_STATUS_ENABLED_BUFFER_PURGED :
                                                                    BTrendEvent.LOG_STATUS_DISABLED_BUFFER_PURGED);
                }
              }
              catch (PermissionException e)
              {
                logger.warning("PermissionException clearing history " + this + ": " + e);
                return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
              }
              catch (Exception e)
              {
                logger.log(Level.WARNING, "Error clearing history " + this, e);
                return new NErrorType(BBacnetErrorClass.DEVICE, BBacnetErrorCode.OPERATIONAL_PROBLEM);
              }
              return null;
            }
            else
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }

          default:
            return writeOptionalProperty(pId, ndx, val, pri);
        }
      }
      catch (AsnException e)
      {
        logger.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_DATA_TYPE);
      }
      catch (PermissionException e)
      {
        logger.warning("PermissionException writing property " + pId + " in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }
    }
  }

  private ErrorType writeBufferSize(byte[] value)
    throws AsnException
  {
    long maxRecords = AsnUtil.fromAsnUnsignedInteger(value);
    if (maxRecords == BBacnetUnsigned.MAX_UNSIGNED_VALUE)
    {
      // The value 2^32-1 is a symbol to indicate that the buffer size is unknown, rather than to
      // support that number of records.
      tlog.getHistoryConfig().set(
        BHistoryConfig.capacity,
        BCapacity.UNLIMITED,
        BLocalBacnetDevice.getBacnetContext());
    }
    else if (maxRecords > Integer.MAX_VALUE)
    {
      // BCapacity supports maximum of 2^31-1 records, thus limited to that value.
      return new NErrorType(property, valueOutOfRange);
    }
    else
    {
      // Changes communicated via BACnet applies to only record count, no support for storage size.
      tlog.getHistoryConfig().set(
        BHistoryConfig.capacity,
        BCapacity.makeByRecordCount((int) maxRecords),
        BLocalBacnetDevice.getBacnetContext());
    }

    return null;
  }

  /**
   * Set the value of an optional property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @param pri the priority level (only used for commandable properties).
   * @throws BacnetException
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeOptionalProperty(int pId,
                                            int ndx,
                                            byte[] val,
                                            int pri)
    throws BacnetException
  {
    try
    {
      BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
      if (almExt != null)
      {
        switch (pId)
        {
          case BBacnetPropertyIdentifier.EVENT_ENABLE:
            almExt.set(BBacnetTrendLogAlarmSourceExt.alarmEnable,
                       BacnetBitStringUtil.getBAlarmTransitionBits(AsnUtil.fromAsnBitString(val)),
                       BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.NOTIFY_TYPE:
            almExt.set(BBacnetTrendLogAlarmSourceExt.notifyType,
                       BBacnetNotifyType.make(AsnUtil.fromAsnEnumerated(val)),
                       BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
            long ncinst = AsnUtil.fromAsnUnsignedInteger(val);
            if (ncinst > BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }

            BBacnetObjectIdentifier ncid = BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS, (int)ncinst);
            BBacnetNotificationClassDescriptor nc = (BBacnetNotificationClassDescriptor)
            BBacnetNetwork.localDevice().lookupBacnetObject(ncid);

            if (nc == null)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }

            BAlarmClass ac = nc.getAlarmClass();
            almExt.setString(BBacnetTrendLogAlarmSourceExt.alarmClass,
                             ac.getName(),
                             BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.NOTIFICATION_THRESHOLD:
            almExt.setNotificationThreshold(AsnUtil.fromAsnUnsignedInteger(val));
            return null;

          case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
          case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
          case BBacnetPropertyIdentifier.RECORDS_SINCE_NOTIFICATION:
          case BBacnetPropertyIdentifier.LAST_NOTIFY_RECORD:
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
            return writeMessageTextsConfig(ndx, val, almExt);

          case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
            setBoolean(eventDetectionEnable, AsnUtil.fromAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());
            return null;

          default:
            break;  // Fall through to the next round of checks.
        }
      }
      switch (pId)
      {
        // optional properties for logging bacnet-exposed objects
        case BBacnetPropertyIdentifier.TRIGGER:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.START_TIME:
          if (getLog().getActivePeriod() instanceof BBacnetActivePeriod)
          {
            BBacnetActivePeriod activePeriodSta = (BBacnetActivePeriod)getLog().getActivePeriod();
            BBacnetDateTime startTime = (BBacnetDateTime)activePeriodSta.getStartTime().newCopy();
            AsnUtil.fromAsn(val, startTime);
            checkForSpecialValues(startTime);
            activePeriodSta.set(BBacnetActivePeriod.startTime, startTime, BLocalBacnetDevice.getBacnetContext());
            return null;
          }
          break;

        case BBacnetPropertyIdentifier.STOP_TIME:
          if (getLog().getActivePeriod() instanceof BBacnetActivePeriod)
          {
            BBacnetActivePeriod activePeriodSto = (BBacnetActivePeriod)tlog.getActivePeriod();
            BBacnetDateTime stopTime = (BBacnetDateTime)activePeriodSto.getStopTime().newCopy();
            AsnUtil.fromAsn(val, stopTime);
            checkForSpecialValues(stopTime);
            activePeriodSto.set(BBacnetActivePeriod.stopTime, stopTime, BLocalBacnetDevice.getBacnetContext());
            return null;
          }
          break;

        case BBacnetPropertyIdentifier.LOG_INTERVAL:
          long interval = AsnUtil.fromAsnUnsignedInteger(val) * 10;
          BObject o = (BObject)getLog();

          if (isDynamicallyCreated() || BacnetDescriptorUtil.isGenericTrendLogExtension(tlog))
          {
            ((BIntervalHistoryExt) o).setInterval(BRelTime.make(interval));
          }
          else
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }

          return null;

        case BBacnetPropertyIdentifier.CLIENT_COV_INCREMENT:
          if (getPoint() == null)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INCONSISTENT_SELECTION_CRITERION);
          }

          asnIn.setBuffer(val);

          BBacnetClientCov bacnetClientCov = new BBacnetClientCov();
          bacnetClientCov.readAsn(asnIn);
          BStatusNumeric covIncrement = bacnetClientCov.getIncrement();

          double covRealIncrement = 0.0d;
          if (!covIncrement.getStatus().equals(BStatus.nullStatus))
          {
            covRealIncrement = covIncrement.getNumeric();
          }

          if ((tlog instanceof BNumericCovHistoryExt))
          {
            ((BNumericCovHistoryExt)tlog).setChangeTolerance(covRealIncrement);
          }
          else if (tlog instanceof BBacnetNumericTrendLogRemoteExt)
          {
            ((BBacnetNumericTrendLogRemoteExt)tlog).setChangeTolerance(covRealIncrement);
          }
          else if (tlog instanceof BBacnetNumericTrendLogExt)
          {
            ((BBacnetNumericTrendLogExt)tlog).setChangeTolerance(covRealIncrement);
          }
          else
          {
            if (!covIncrement.getStatus().equals(BStatus.nullStatus))
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INCONSISTENT_SELECTION_CRITERION);
            }
          }
          setClientCovIncrement(bacnetClientCov);
          return null;

        case BBacnetPropertyIdentifier.COV_RESUBSCRIPTION_INTERVAL:

          int lifeTime = AsnUtil.fromAsnUnsignedInt(val);

          if (BacnetDescriptorUtil.isGenericTrendLogExtension(getLog()))
          {
            return writeCovResubscriptionIntervalToGenericTrengLog(tlog, lifeTime);
          }

          if (tlog instanceof BCovHistoryExt)
          {
            // Get parent control point - cast is ok b/c of our semantics.
            BControlPoint point = getPoint();
            SlotCursor<Property> c = point.getProperties();
            if (c.next(BBacnetProxyExt.class))
            {
              BBacnetProxyExt ext = (BBacnetProxyExt)c.get();
              if (ext != null)
              {
                if (ext.isCOV()) // FIXX: should this be useCov()?
                {
                  BBacnetTuningPolicy bacnetTuningPolicy = (BBacnetTuningPolicy)ext.getTuningPolicy();
                  bacnetTuningPolicy.setCovSubscriptionLifetime(lifeTime / 30);
                  return null;
                }
              }
            }
          }
          break;
      }
    }
    catch (OutOfRangeException e)
    {
      logger.warning("OutOfRangeException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }
    catch (AsnException e)
    {
      logger.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      logger.warning("PermissionException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }

    return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY);
  }

  private static ErrorType writeMessageTextsConfig(int ndx, byte[] val, BBacnetTrendLogAlarmSourceExt alarmExt)
    throws AsnException
  {
    if (ndx < NOT_USED || ndx > MESSAGE_TEXTS_COUNT)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX);
    }

    switch (ndx)
    {
      case 0:
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

      case NOT_USED:
        BBacnetArray textsConfig = new BBacnetArray(BString.TYPE, 3);
        AsnUtil.fromAsn(BacnetConst.ASN_ANY, val, textsConfig);
        String toOffnormalText = textsConfig.getElement(1).toString(null);
        String toFaultText = textsConfig.getElement(2).toString(null);
        String toNormalText = textsConfig.getElement(3).toString(null);

        if (!toOffnormalText.isEmpty() || !toFaultText.isEmpty())
        {
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
        }

        // BBacnetTrendLogAlarmSourceExt does not have properties for toOffnormal and toFault texts
        alarmExt.set(
          BBacnetTrendLogAlarmSourceExt.toNormalText,
          BFormat.make(toNormalText),
          BLocalBacnetDevice.getBacnetContext());
        break;

      case 1:
      case 2:
        if (!AsnUtil.fromAsnCharacterString(val).isEmpty())
        {
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
        }
        break;

      case 3:
        alarmExt.set(
          BBacnetTrendLogAlarmSourceExt.toNormalText,
          BFormat.make(AsnUtil.fromAsnCharacterString(val)),
          BLocalBacnetDevice.getBacnetContext());
        break;
    }

    return null;
  }

  /**
   * Get all the optional properties for this object.
   * Calculated only if needed, and recalculated if
   * properties are added or removed.
   *
   * @return the list as an array of BDiscretes.
   */
  private int[] getOptionalProps()
  {
    ArrayList<BBacnetPropertyIdentifier> v = new ArrayList<>();
    v.add(BBacnetPropertyIdentifier.description);
    v.add(BBacnetPropertyIdentifier.logInterval);
    v.add(BBacnetPropertyIdentifier.trigger);

    if (BacnetDescriptorUtil.isGenericTrendLogExtension(tlog))
    {
      v.add(BBacnetPropertyIdentifier.covResubscriptionInterval);
      v.add(BBacnetPropertyIdentifier.clientCovIncrement);
      v.add(BBacnetPropertyIdentifier.logDeviceObjectProperty);
      v.add(BBacnetPropertyIdentifier.notificationThreshold);
      v.add(BBacnetPropertyIdentifier.recordsSinceNotification);
      v.add(BBacnetPropertyIdentifier.lastNotifyRecord);
      v.add(BBacnetPropertyIdentifier.notificationClass);
      v.add(BBacnetPropertyIdentifier.eventEnable);
      v.add(BBacnetPropertyIdentifier.ackedTransitions);
      v.add(BBacnetPropertyIdentifier.notifyType);
      v.add(BBacnetPropertyIdentifier.eventTimeStamps);
      v.add(BBacnetPropertyIdentifier.eventMessageTexts);
      v.add(BBacnetPropertyIdentifier.eventMessageTextsConfig);
      v.add(BBacnetPropertyIdentifier.eventDetectionEnable);
      v.add(BBacnetPropertyIdentifier.startTime);
      v.add(BBacnetPropertyIdentifier.stopTime);
    }
    else
    {
      BControlPoint point = getPoint();
      if (point != null)
      {
        BOrd pointOrd = point.getHandleOrd();
        BAbstractProxyExt pxExt = point.getProxyExt();
        if (tlog instanceof BCovHistoryExt)
        {
          if (pxExt instanceof BBacnetProxyExt)
          {
            if (((BBacnetProxyExt) pxExt).isCOV())
            {
              v.add(BBacnetPropertyIdentifier.covResubscriptionInterval);
            }
          }
          v.add(BBacnetPropertyIdentifier.clientCovIncrement);
        }

        // Determine if we need Log_DeviceObjectProperty.
        BBacnetObjectIdentifier logObjId = BBacnetNetwork.localDevice().lookupBacnetObjectId(pointOrd);
        if (logObjId != null)
        {
          BIBacnetExportObject logObject = BBacnetNetwork.localDevice().lookupBacnetObject(logObjId);
          if (logObject != null)
          {
            v.add(BBacnetPropertyIdentifier.logDeviceObjectProperty);
          }
        }

        if (tlog != null)
        {
          if (tlog.getActivePeriod() instanceof BBacnetActivePeriod)
          {
            v.add(BBacnetPropertyIdentifier.startTime);
            v.add(BBacnetPropertyIdentifier.stopTime);
          }
        }

        BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
        if (almExt != null)
        {
          v.add(BBacnetPropertyIdentifier.notificationThreshold);
          v.add(BBacnetPropertyIdentifier.recordsSinceNotification);
          v.add(BBacnetPropertyIdentifier.lastNotifyRecord);
          v.add(BBacnetPropertyIdentifier.notificationClass);
          v.add(BBacnetPropertyIdentifier.eventEnable);
          v.add(BBacnetPropertyIdentifier.ackedTransitions);
          v.add(BBacnetPropertyIdentifier.notifyType);
          v.add(BBacnetPropertyIdentifier.eventTimeStamps);
          v.add(BBacnetPropertyIdentifier.eventMessageTexts);
          v.add(BBacnetPropertyIdentifier.eventMessageTextsConfig);
          v.add(BBacnetPropertyIdentifier.eventDetectionEnable);
        }
      }
    }

    optionalProps = new int[v.size()];
    for (int i = 0; i < optionalProps.length; i++)
    {
      optionalProps[i] = ((BEnum) v.get(i)).getOrdinal();
    }

    return optionalProps;
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  @Override
  public String toString(Context c)
  {
    return getObjectName() + " [" + getObjectId() + "]";
  }

  final BIBacnetTrendLogExt getLog(boolean forceful)
  {
    if (forceful)
    {
      tlog = null;
    }

    return getLog();
  }
  /**
   * Find the exposed control point.
   */
  final BIBacnetTrendLogExt getLog()
  {
    if (tlog == null)
    {
      return findLog();
    }

    if (tlog == null && isDynamicallyCreated())
    {
      tlog = new BBacnetNumericTrendLogExt();
    }

    return tlog;
  }

  public BBacnetTrendLogAlarmSourceExt getAlarmExt()
  {
    return BacnetTrendLogUtil.getAlarmExt(tlog);
  }

  @Override
  public BControlPoint getPoint()
  {
    return (tlog != null) ? (BControlPoint)((BHistoryExt)tlog).getParent() : null;
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  private static void checkForSpecialValues(BBacnetDateTime dateTime)
    throws OutOfRangeException
  {
    BBacnetDate date = dateTime.getDate();
    BBacnetTime time = dateTime.getTime();

    if (allUnspecified(date) && allUnspecified(time))
    {
      return;
    }

      if (date.getYear() == BBacnetDate.UNSPECIFIED ||
          monthHasSpecialValue(date.getMonth()) ||
          dayHasSpecialValue(date.getDayOfMonth()) ||
          date.getDayOfWeek() == BBacnetDate.UNSPECIFIED)
    {
      throw new OutOfRangeException("Date contains Special Values.");
    }

    if (time.isHourUnspecified() ||
        time.isMinuteUnspecified() ||
        time.isSecondUnspecified() ||
        time.isHundredthUnspecified())
    {
      throw new OutOfRangeException("Time contains Special Values.");
    }
  }

  private static boolean allUnspecified(BBacnetTime time)
  {
    if (time.isHourUnspecified() &&
        time.isMinuteUnspecified() &&
        time.isSecondUnspecified() &&
        time.isHundredthUnspecified())
    {
      return true;
    }

    return false;
  }

  private static boolean allUnspecified(BBacnetDate date)
  {
    if (date.isYearUnspecified() &&
        date.isMonthUnspecified() &&
        date.isDayOfMonthUnspecified() &&
        date.isDayOfWeekUnspecified())
    {
      return true;
    }

    return false;
  }

  private static boolean monthHasSpecialValue(int month)
  {
    return month == BBacnetDate.UNSPECIFIED ||
      month == BBacnetDate.BAJA_ODD_MONTHS + 1 ||  // BAJA months are 0 indexed. While reading from Niagara,
      month == BBacnetDate.BAJA_EVEN_MONTHS + 1;   // the month is increased by 1. Hence doing the same while writing
  }

  private static boolean dayHasSpecialValue(int day)
  {
    return day == BBacnetDate.UNSPECIFIED ||
      day == BBacnetDate.LAST_DAY_OF_MONTH ||
      day == BBacnetDate.BAJA_LAST_7_DAYS_OF_MONTH ||
      day == BBacnetDate.BAJA_ODD_DAYS_OF_MONTH ||
      day == BBacnetDate.BAJA_EVEN_DAYS_OF_MONTH;
  }

  private BIBacnetTrendLogExt findLog()
  {
    try
    {
      if (!logOrd.isEquivalentToDefaultValue(getLogOrd()) ||
          (isDynamicallyCreated() && getLogOrd()!=null && !getLogOrd().isNull()))
      {
        BObject o = getLogOrd().get(this);
        if (o instanceof BIBacnetTrendLogExt)
        {
          tlog = (BIBacnetTrendLogExt) o;
        }
        else
        {
          tlog = null;
        }
      }
    }
    catch (Exception e)
    {
      logger.warning("Unable to resolve log ord for " + this + " " + getLogOrd() + ": " + e);
      tlog = null;
    }

    if ((tlog == null) && isRunning())
    {
      setFaultCause("Cannot find exported history");
      setStatus(BStatus.makeFault(getStatus(), true));
    }

    if (isDynamicallyCreated() && tlog == null)
    {
      tlog = new BBacnetNumericTrendLogExt();
      BBacnetTrendLogAlarmSourceExt ext = new BBacnetTrendLogAlarmSourceExt();
      ext.setAlarmEnable(BAlarmTransitionBits.EMPTY);
      ((BComponent) tlog).add("BBacnetTrendLogAlarmSourceExt", ext);
    }

    return tlog;
  }

  /**
   * Get the history backing the trend log ext being exported.
   * This method will return the history even if the historyExt is
   * disabled, to allow compliance with the BACnet specification for
   * things like ReadProperty(Record_Count).
   *
   * @return the backing BIHistory.
   */
  private BIHistory getHistory(HistoryDatabaseConnection conn)
  {
    BIBacnetTrendLogExt tlog = getLog();
    BIHistory history = tlog.getHistory();

    if (history == null)
    {
      // lookup the service and db
      BHistoryConfig config = tlog.getHistoryConfig();
      BHistoryId id = config.getId();
      if (conn.exists(id))
      {
        history = conn.getHistory(id);
      }
    }

    return history;
  }

  /**
   * Get a connection to the Niagara History Database.
   *
   * @since Niagara 4.0
   */
  private static HistoryDatabaseConnection getHistoryDbConnection(Context cx)
  {
    BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
    BHistoryDatabase db = service.getDatabase();
    return db.getDbConnection(null);
  }

  private BBacnetDeviceObjectPropertyReference getLogDOPRef()
  {
    BBacnetDeviceObjectPropertyReference dopRef = NULL_DOPR;

    if (tlog == null)
    {
      findLog();
    }

    BControlPoint controlPoint = getPoint();
    if (controlPoint == null)
    {
      return dopRef;
    }

    BOrd pointOrd = controlPoint.getHandleOrd();
    BBacnetObjectIdentifier logObjId = BBacnetNetwork.localDevice().lookupBacnetObjectId(pointOrd);
    if (logObjId != null)
    {
      dopRef = new BBacnetDeviceObjectPropertyReference(logObjId);
    }
    else
    {
      BAbstractProxyExt pxExt = controlPoint.getProxyExt();
      if (pxExt instanceof BBacnetProxyExt)
      {
        BBacnetProxyExt bacPxExt = (BBacnetProxyExt)pxExt;
        dopRef = new BBacnetDeviceObjectPropertyReference(bacPxExt.getObjectId(),
                                                          bacPxExt.getPropertyId().getOrdinal(),
                                                          bacPxExt.getPropertyArrayIndex(),
                                                          bacPxExt.device().getObjectId());
      }
    }

    return dopRef;
  }

////////////////////////////////////////////////////////////////
// Inner class: BacnetTrendLogSubscriber
////////////////////////////////////////////////////////////////

  /**
   * BacnetTrendLogSubscriber handles updating the local device's export
   * table when the trend log's History Name property is changed.
   */
  class BacnetTrendLogSubscriber
    extends Subscriber
  {
    public BacnetTrendLogSubscriber(BBacnetTrendLogDescriptor obj, BIBacnetTrendLogExt log)
    {
      this.obj = obj;
      if (log != null)
      {
        this.config = log.getHistoryConfig();
      }
    }

    @Override
    public void event(BComponentEvent event)
    {
      try
      {
        switch (event.getId())
        {
          case BComponentEvent.PROPERTY_CHANGED:
            if (BHistoryConfig.historyName.equals(event.getSlot().asProperty()))
            {
              obj.checkConfiguration();
            }
            break;

          case BComponentEvent.PROPERTY_REMOVED:
            BObject object = obj.getObject();
            if (object instanceof BComplex)
            {
              if (!isDynamicallyCreated() && ((BComplex)object).getPropertyInParent() == null)
              {
                ((BComponent) obj.getParent()).remove(obj.getPropertyInParent());
              }
            }
            break;
        }
      }
      catch (Exception e)
      {
        logger.warning("Exception occurred handling event " + obj.getObjectId() + ": " + e);
      }
    }

    private final BBacnetTrendLogDescriptor obj;
    BHistoryConfig config;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetTrendLogDescriptor", 2);
    out.prop("tlog", tlog);
    out.prop("logSubscriber", logSubscriber);
    out.prop("oldId", oldId);
    out.prop("oldName", oldName);
    out.prop("duplicate", duplicate);
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make(BIcon.std("history.png"), BIcon.std("badges/export.png"));

  private int[] optionalProps;

  private BacnetTrendLogSubscriber logSubscriber;
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final AsnInputStream   asnIn  = new AsnInputStream();
  private static final AsnOutputStream asnOut = new AsnOutputStream();
  private static final BBacnetDeviceObjectPropertyReference NULL_DOPR = new BBacnetDeviceObjectPropertyReference();

  private static final int[] REQUIRED_PROPS = new int[]
  {
    BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
    BBacnetPropertyIdentifier.OBJECT_NAME,
    BBacnetPropertyIdentifier.OBJECT_TYPE,
    BBacnetPropertyIdentifier.ENABLE,
    BBacnetPropertyIdentifier.STOP_WHEN_FULL,
    BBacnetPropertyIdentifier.BUFFER_SIZE,
    BBacnetPropertyIdentifier.LOG_BUFFER,
    BBacnetPropertyIdentifier.RECORD_COUNT,
    BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT,
    BBacnetPropertyIdentifier.EVENT_STATE,
    BBacnetPropertyIdentifier.LOGGING_TYPE,
    BBacnetPropertyIdentifier.STATUS_FLAGS,
  };

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, getOptionalProps());
  }

  private BIBacnetTrendLogExt tlog;
  private BComponent targetPoint;
  private static final Logger logger = Logger.getLogger("bacnet.export.object.trendlog");

  @Override
  public boolean isDynamicallyCreated()
  {
    return getDynamicallyCreated();
  }

  private BComponent getTargetPoint(PropertyValue pv)
  {
    try
    {
      if (pv == null)
      {
        return targetPoint;
      }

      targetPoint = BacnetDescriptorUtil.parseLogDeviceObjectProperty(pv, getLogDeviceObjectPropertyReference());
    }
    catch (Exception e)
    {
      logger.severe("Could not find the target point: " + e);
    }
    return targetPoint;
  }

  private ErrorType writeCovResubscriptionIntervalToGenericTrengLog(BIBacnetTrendLogExt tlog, int lifeTime)
  {
    if (lifeTime > COV_LIFETIME_LIMIT)
    {
      return new NErrorType(BBacnetErrorClass.SERVICES, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }

    if (tlog != null)
    {
      if (tlog instanceof BBacnetTrendLogRemoteExt)
      {
        ((BBacnetTrendLogRemoteExt) tlog).setCovResubscriptionInterval(lifeTime);
      }
      setCovResubscriptionInterval(lifeTime);
    }

    return null;
  }

  private int readCovResubscriptionIntervalToGenericTrengLog(BIBacnetTrendLogExt tlog, int lifeTime)
  {
    if (tlog != null)
    {
      if (tlog instanceof BBacnetTrendLogRemoteExt)
      {
        setCovResubscriptionInterval(((BBacnetTrendLogRemoteExt) tlog).getCovResubscriptionInterval());
      }
    }

    return getCovResubscriptionInterval();
  }
}
