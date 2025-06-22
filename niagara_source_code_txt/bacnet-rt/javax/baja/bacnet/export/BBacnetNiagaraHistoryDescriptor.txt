/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmService;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetDateTime;
import javax.baja.bacnet.datatypes.BBacnetLogRecord;
import javax.baja.bacnet.datatypes.BBacnetNull;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetRejectReason;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.control.BControlPoint;
import javax.baja.history.BCollectionInterval;
import javax.baja.history.BFullPolicy;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryRecord;
import javax.baja.history.BHistoryService;
import javax.baja.history.BIHistory;
import javax.baja.history.BTrendRecord;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.history.db.HistoryDatabaseConnection;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.datatypes.BTrendEvent;
import com.tridium.bacnet.history.BBacnetTrendLogAlarmSourceExt;
import com.tridium.bacnet.history.BBacnetTrendRecord;
import com.tridium.bacnet.history.BacnetTrendLogUtil;
import com.tridium.bacnet.services.BacnetConfirmedRequest;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.NChangeListError;

/**
 * BBacnetNiagaraHistoryDescriptor is the archive component which exposes
 * a Niagara history to Bacnet as a trend log.  It only supports 'By Time'
 * requests for the trend log data.
 *
 * @author Scott Hoye
 * @author Craig Gemmill on 4 Nov 03
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "history:IHistory"
  )
)
/*
 The unique identifier for this history within the
 entire system.
 */
@NiagaraProperty(
  name = "id",
  type = "BHistoryId",
  defaultValue = "BHistoryId.NULL",
  flags = Flags.SUMMARY
)
/*
 The history ord.  Maintained programmatically.
 */
@NiagaraProperty(
  name = "historyOrd",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT",
  flags = Flags.HIDDEN | Flags.READONLY
)
/*
 objectId is the identifier by which this trend log is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG)",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 the name by which this object is known to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectName",
  type = "String",
  defaultValue = "",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "firstTimestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "firstSeqNum",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "lastTimestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "lastSeqNum",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.HIDDEN
)
public class BBacnetNiagaraHistoryDescriptor
  extends BBacnetEventSource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetNiagaraHistoryDescriptor(3730834836)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * The unique identifier for this history within the
   * entire system.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(Flags.SUMMARY, BHistoryId.NULL, null);

  /**
   * Get the {@code id} property.
   * The unique identifier for this history within the
   * entire system.
   * @see #id
   */
  public BHistoryId getId() { return (BHistoryId)get(id); }

  /**
   * Set the {@code id} property.
   * The unique identifier for this history within the
   * entire system.
   * @see #id
   */
  public void setId(BHistoryId v) { set(id, v, null); }

  //endregion Property "id"

  //region Property "historyOrd"

  /**
   * Slot for the {@code historyOrd} property.
   * The history ord.  Maintained programmatically.
   * @see #getHistoryOrd
   * @see #setHistoryOrd
   */
  public static final Property historyOrd = newProperty(Flags.HIDDEN | Flags.READONLY, BOrd.DEFAULT, null);

  /**
   * Get the {@code historyOrd} property.
   * The history ord.  Maintained programmatically.
   * @see #historyOrd
   */
  public BOrd getHistoryOrd() { return (BOrd)get(historyOrd); }

  /**
   * Set the {@code historyOrd} property.
   * The history ord.  Maintained programmatically.
   * @see #historyOrd
   */
  public void setHistoryOrd(BOrd v) { set(historyOrd, v, null); }

  //endregion Property "historyOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this trend log is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG), null);

  /**
   * Get the {@code objectId} property.
   * objectId is the identifier by which this trend log is known
   * to the Bacnet world.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * objectId is the identifier by which this trend log is known
   * to the Bacnet world.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "objectName"

  /**
   * Slot for the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #getObjectName
   * @see #setObjectName
   */
  public static final Property objectName = newProperty(Flags.DEFAULT_ON_CLONE, "", null);

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

  //region Property "firstTimestamp"

  /**
   * Slot for the {@code firstTimestamp} property.
   * @see #getFirstTimestamp
   * @see #setFirstTimestamp
   */
  public static final Property firstTimestamp = newProperty(Flags.READONLY | Flags.HIDDEN, BAbsTime.NULL, null);

  /**
   * Get the {@code firstTimestamp} property.
   * @see #firstTimestamp
   */
  public BAbsTime getFirstTimestamp() { return (BAbsTime)get(firstTimestamp); }

  /**
   * Set the {@code firstTimestamp} property.
   * @see #firstTimestamp
   */
  public void setFirstTimestamp(BAbsTime v) { set(firstTimestamp, v, null); }

  //endregion Property "firstTimestamp"

  //region Property "firstSeqNum"

  /**
   * Slot for the {@code firstSeqNum} property.
   * @see #getFirstSeqNum
   * @see #setFirstSeqNum
   */
  public static final Property firstSeqNum = newProperty(Flags.READONLY | Flags.HIDDEN, 0, null);

  /**
   * Get the {@code firstSeqNum} property.
   * @see #firstSeqNum
   */
  public long getFirstSeqNum() { return getLong(firstSeqNum); }

  /**
   * Set the {@code firstSeqNum} property.
   * @see #firstSeqNum
   */
  public void setFirstSeqNum(long v) { setLong(firstSeqNum, v, null); }

  //endregion Property "firstSeqNum"

  //region Property "lastTimestamp"

  /**
   * Slot for the {@code lastTimestamp} property.
   * @see #getLastTimestamp
   * @see #setLastTimestamp
   */
  public static final Property lastTimestamp = newProperty(Flags.READONLY | Flags.HIDDEN, BAbsTime.NULL, null);

  /**
   * Get the {@code lastTimestamp} property.
   * @see #lastTimestamp
   */
  public BAbsTime getLastTimestamp() { return (BAbsTime)get(lastTimestamp); }

  /**
   * Set the {@code lastTimestamp} property.
   * @see #lastTimestamp
   */
  public void setLastTimestamp(BAbsTime v) { set(lastTimestamp, v, null); }

  //endregion Property "lastTimestamp"

  //region Property "lastSeqNum"

  /**
   * Slot for the {@code lastSeqNum} property.
   * @see #getLastSeqNum
   * @see #setLastSeqNum
   */
  public static final Property lastSeqNum = newProperty(Flags.READONLY | Flags.HIDDEN, 0, null);

  /**
   * Get the {@code lastSeqNum} property.
   * @see #lastSeqNum
   */
  public long getLastSeqNum() { return getLong(lastSeqNum); }

  /**
   * Set the {@code lastSeqNum} property.
   * @see #lastSeqNum
   */
  public void setLastSeqNum(long v) { setLong(lastSeqNum, v, null); }

  //endregion Property "lastSeqNum"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetNiagaraHistoryDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty default constructor
   */
  public BBacnetNiagaraHistoryDescriptor()
  {
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Register with the Bacnet service when this component is started.
   */
  @Override
  public final void started()
    throws Exception
  {
    super.started();

    if (getFirstTimestamp().equals(BAbsTime.NULL))
    {
      initialize();
    }

    // Export the history and initialize the local copies.
    oldId = getObjectId();
    oldName = getObjectName();
    checkConfiguration();

    // Increment the Device object's Database_Revision for created objects.
    if (Sys.isStationStarted())
    {
      BBacnetNetwork.localDevice().incrementDatabaseRevision();
    }
  }

  /**
   * Unregister with the Bacnet service when this component is stopped.
   */
  @Override
  public final void stopped()
    throws Exception
  {
    super.stopped();

    // unexport
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    local.unexport(oldId, oldName, this);

    try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
    {
      // Clear the name subscriber.
      local.unsubscribe(this, getHistory(conn));
    }

    // Clear the local copies.
    optionalProps = null;
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
   */
  @Override
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    // Handle historyOrd changes separately - we need to update the id even
    // if we're not running!
    if (p.equals(historyOrd))
    {
      setId(BHistoryId.make(getHistoryOrd().parse()[0].getBody()));
      if (isRunning())
      {
        checkConfiguration();
      }
      return;
    }

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
    else if (p.equals(id))
    {
      setHistoryOrd(BOrd.make("history:" + getId()));

      // When history ID changes, the first and last timestamps and seqNums must be restarted based
      // on the new history and not "re-init'ed" based on the values derived from the old history.
      initialize();
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
   * @param s
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
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
    return (BObject)getHistory();
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getHistoryOrd();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(historyOrd, objectOrd, cx);
  }

  /**
   * Check the configuration of this object.
   */
  @Override
  public void checkConfiguration()
  {
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    // quit if fatal fault
    if (isFatalFault())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      return;
    }

    try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
    {
      // Clear the name subscriber.
      local.unsubscribe(this, getHistory(conn));

      // Check the configuration.
      boolean configOk = true;
      if (getHistory(conn) == null)
      {
        setFaultCause("Cannot find exported history");
        configOk = false;
      }
      else
      {
        local.subscribe(this, getHistory(conn));
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
  }

////////////////////////////////////////////////////////////////
// BBacnetEventSource
// 2004-12-22 CPG
// The BBacnetNiagaraHistoryDescriptor cannot currently be an alarm
// source, because we get no notification that the history has
// added a record.  However, in the event that this becomes
// possible in the future, I am keeping this part of the API,
// because it would be difficult to change this later.
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

  @Override
  public BControlPoint getPoint()
  {
    return null;
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
      BAbsTime normalTime = almExt.getToOffnormalTimes().getNormalTime();
      if (normalTime.isBefore(almExt.getToFaultTimes().getNormalTime()))
      {
        normalTime = almExt.getToFaultTimes().getNormalTime();
      }
      return new BBacnetTimeStamp[] { new BBacnetTimeStamp(almExt.getToOffnormalTimes().getAlarmTime()),
                                      new BBacnetTimeStamp(almExt.getToFaultTimes().getAlarmTime()),
                                      new BBacnetTimeStamp(normalTime) };
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
  public final BBacnetNotificationClassDescriptor getNotificationClass()
  {
    BBacnetTrendLogAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    try
    {
      BAlarmService as = (BAlarmService)Sys.getService(BAlarmService.TYPE);
      BAlarmClass ac = as.lookupAlarmClass(almExt.getAlarmClass());
      SlotCursor<Property> c = ac.getProperties();
      if (c.next(BBacnetNotificationClassDescriptor.class))
      {
        return (BBacnetNotificationClassDescriptor) c.get();
      }
    }
    catch (ServiceNotFoundException e)
    {
      log.log(Level.SEVERE, "getNotificationClass on " + this + ": Unable to find alarm service", e);
    }

    return null;
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
// Support methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  @Override
  public String toString(Context c)
  {
    return getObjectName() + " [" + getObjectId() + "]";
  }

  /**
   * Get the BBacnetTrendLogAlarmSourceExtension that gives this point
   * alarming capability.
   */
  final BBacnetTrendLogAlarmSourceExt getAlarmExt()
  {
    SlotCursor<Property> c = getProperties();
    if (c.next(BBacnetTrendLogAlarmSourceExt.class))
    {
      return (BBacnetTrendLogAlarmSourceExt)c.get();
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine(getObjectId() + ": found no associated BacnetTrendLogAlarmSourceExt");
    }
    return null;
  }

  /**
   * Used to initialize the first and last timestamps and corresponding
   * sequence numbers.  Necessary because Bacnet requires sequence numbers to
   * be assigned to log entries, while Niagara does not.  Thus sequence numbers
   * must be assigned at runtime based on the initial size of the exposed history.
   */
  private void initialize()
  {
    try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
    {
      BIHistory history = getHistory(conn);
      if (history == null)
      {
        resetTimestamps();
        return;
      }

      int dataSize = conn.getRecordCount(history);
      if (dataSize < 1)
      {
        resetTimestamps();
        return;
      }

      // Found some initial entries in the history
      setFirstTimestamp(conn.getFirstTimestamp(history));
      setFirstSeqNum(1L);
      setLastTimestamp(conn.getLastTimestamp(history));
      setLastSeqNum(dataSize);
    }
  }

  private void resetTimestamps()
  {
    setFirstTimestamp(BAbsTime.NULL);
    setFirstSeqNum(0);
    setLastTimestamp(BAbsTime.NULL);
    setLastSeqNum(0);
  }

  /**
   * Returns the history or null if not initialized. Creates (and closes)
   * a new History Database Connection.
   */
  public final BIHistory getHistory()
  {
    if (!isRunning())
    {
      return null;
    }
    try
    {
      try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
      {
        return conn.getHistory(getId());
      }
    }
    catch (Exception e)
    {
      logException(Level.SEVERE, getObjectId() + ": Exception occurred in getHistory", e);
      return null;
    }
  }

  /**
   * Returns the history or null if not initialized.
   *
   * @since Niagara 4.0
   */
  public final BIHistory getHistory(HistoryDatabaseConnection conn)
  {
    if (!isRunning())
    {
      return null;
    }
    try
    {
      return conn.getHistory(getId());
    }
    catch (Exception e)
    {
      logException(Level.SEVERE, getObjectId() + ": Exception occurred in getHistory for history ID " + getId(), e);
      return null;
    }
  }

  /**
   * This method will return a HistoryDatabaseConnection that excludes archive
   * history data for any subsequent history queries that use the connection.
   * This ensures that any history queries for BACnet use cases (e.g. exposing
   * histories to BACnet) only consider local history data.
   */
  private static HistoryDatabaseConnection getHistoryDbConnection(Context cx)
  {
    BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
    BHistoryDatabase db = service.getDatabase();
    return db.getDbConnection(true, cx);
  }

  /**
   * Checks to see if the first and last timestamps of the history have changed,
   * and if so, adjust properties accordingly to keep up with the
   * sequence number tracking.
   */
  private void reinitTimestamps(HistoryDatabaseConnection conn, BIHistory history)
  {
    BAbsTime newFirstTimestamp = conn.getFirstTimestamp(history);
    if (newFirstTimestamp.isNull())
    {
      // History records have been cleared. Do not clear the lastSeqNum- that will continue to
      // increment.
      setFirstTimestamp(BAbsTime.NULL);
      setFirstSeqNum(0);
      setLastTimestamp(BAbsTime.NULL);
      return;
    }

    BAbsTime oldFirstTimestamp = getFirstTimestamp();
    BAbsTime newLastTimestamp = conn.getLastTimestamp(history);
    BAbsTime oldLastTimestamp = getLastTimestamp();

    // First adjust the first stored timestamp and sequence number, if necessary
    if (!newFirstTimestamp.equals(oldFirstTimestamp))
    {
      if (newFirstTimestamp.isAfter(oldLastTimestamp))
      {
        // The history has wrapped around and an unknown number of records have been added between
        // the old lastTimestamp and the new firstTimestamp. There are only records between the new
        // firstTimestamp and new lastTimestamp. Therefore, we cannot reliably determine the first
        // sequence value.
        //
        // For history capacity of 6:
        // 1 oldFirstTimeStamp, oldFirstSeqNum
        // 2
        // 3
        // 4
        // 5
        // 6 oldLastTimestamp, oldLastSeqNum
        // 7 newFirstTimestamp, newFirstSeqNum
        // 8
        // 9
        // 10
        // 11
        // 12 newLastTimestamp, newLastSeqNum
        setFirstTimestamp(newFirstTimestamp);
        setFirstSeqNum(getSequenceNumber(getLastSeqNum() + 1L));
      }
      else
      {
        // Not enough records have been added to the history to wrap around so the firstSeqNum can
        // be updated by counting the number of records between the new firstTimestamp and the old
        // lastTimestamp and subtracting that from the old lastSeqNum.
        //
        // newFirstTimestamp is before oldLastTimestamp:
        // 1 oldFirstTimeStamp, oldFirstSeqNum
        // 2
        // 3 newFirstTimestamp, newFirstSeqNum = oldLastSeqNum - count [newFirstTimestamp..oldLastTimestamp)
        // 4
        // 5
        // 6 oldLastTimestamp, oldLastSeqNum
        // 7
        // 8 newLastTimestamp, newLastSeqNum
        //
        // newFirstTimestamp is equal to oldLastTimestamp:
        // 1 oldFirstTimeStamp, oldFirstSeqNum
        // 2
        // 3
        // 4
        // 5
        // 6 oldLastTimestamp, oldLastSeqNum, newFirstTimestamp, newFirstSeqNum = oldLastSeqNum - 0
        // 7 newFirstTimestamp, newFirstSeqNum
        // 8
        // 9
        // 10
        // 11 newLastTimestamp, newLastSeqNum
        try (Cursor<BHistoryRecord> data = conn.timeQuery(history, newFirstTimestamp, oldLastTimestamp).cursor())
        {
          long count = countRecords(data, oldLastTimestamp);
          setFirstTimestamp(newFirstTimestamp);
          setFirstSeqNum(getSequenceNumber(getLastSeqNum() - count));
        }
      }

      if (newFirstTimestamp.isBefore(oldFirstTimestamp))
      {
        // We do not support altering histories through Niagara and reporting correct values to
        // BACnet.
        log.log(Level.WARNING, "History has been altered causing the timestamp of the oldest" +
          " record to be earlier than the timestamp of the previous oldest record;" +
          " BACnet Object ID: " + getObjectId() + ", history ID: " + history.getId());
      }
    }

    // Next adjust the last stored timestamp and sequence number, if necessary
    if (!newLastTimestamp.equals(oldLastTimestamp))
    {
      if (newLastTimestamp.isAfter(oldLastTimestamp))
      {
        // NewLastTimestamp always be after oldLastTimestamp.
        try (Cursor<BHistoryRecord> data = conn.timeQuery(history, oldLastTimestamp, newLastTimestamp).cursor())
        {
          long count = countRecords(data, oldLastTimestamp);
          setLastTimestamp(newLastTimestamp);
          setLastSeqNum(getSequenceNumber(getLastSeqNum() + count));
        }
      }
      else if (newLastTimestamp.isBefore(oldLastTimestamp))
      {
        // Can happen if history records have been deleted.  We do not support deleting records
        // through Niagara and still reporting correct values to BACnet.
        log.log(Level.WARNING, "History has been altered causing the timestamp of the newest" +
          " record to be earlier than the timestamp of the previous newest record;" +
          " BACnet Object ID: " + getObjectId() + ", history ID: " + history.getId());
      }
    }
  }

  private static long countRecords(Cursor<BHistoryRecord> data, BAbsTime excludeTimestamp)
  {
    long count = 0L;
    while (data.next())
    {
      if (!data.get().getTimestamp().equals(excludeTimestamp))
      {
        count++;
      }
    }

    return count;
  }

  /**
   * Convenience method to set the next sequence number, obeying
   * Bacnet max value constraints.
   */
  private static long getSequenceNumber(long newSeqNum)
  {
    if (newSeqNum > BacnetTrendLogUtil.MAX_SEQ_NUM)
    {
      newSeqNum -= BacnetTrendLogUtil.MAX_SEQ_NUM;
    }
    return newSeqNum;
  }

  /**
   * Read history records from the data history
   * and return all of them as Bacnet trend records.
   */
  private ReadLogResult readRangeAll(int maxSize)
  {
    long itemCount = 0L;
    long firstFoundSequenceNumber = 0L;
    boolean includesFirstItem = false; // set to true if the response includes the first entry
    boolean moreItems = false; // set to true if the number of entries requested is more than the response can handle.

    synchronized (asnOut)
    {
      asnOut.reset();
      try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
      {
        BIHistory dataHistory = getHistory(conn);
        reinitTimestamps(conn, dataHistory);
        try (Cursor<BHistoryRecord> data = conn.scan(dataHistory)) // Get all of the data records
        {
          BTrendRecord r = null;
          long loopCount = getFirstSeqNum();

          boolean isNext = data.next();
          while (isNext)
          {
            r = (BTrendRecord)data.get();

            if (getLogDatumType(r) >= 0) // Don't include any invalid String entries
            {
              AsnOutputStream temp = new AsnOutputStream();

              try
              {
                asnOut.writeTo(temp);
              }
              catch (Exception e)
              {
                log.log(Level.WARNING, "Error exporting all trend records, Bacnet Object ID " + getObjectId(), e);
                temp = asnOut;
              }

              BBacnetLogRecord.writeLogRecord(r.getTimestamp(),
                                              (BSimple)r.get(r.getValueProperty()),
                                              getLogDatumType(r),
                                              r.getStatus(),
                                              getLogEvent(r).getLong(),
                                              asnOut);

              if ((maxSize > 0) && (asnOut.size() > maxSize))
              {
                asnOut = temp;
                moreItems = true;
                break;
              }

              if (loopCount == getFirstSeqNum())
              {
                includesFirstItem = true;
              }

              if (itemCount == 0L)
              {
                firstFoundSequenceNumber = loopCount;
              }

              itemCount++;
            }

            loopCount++;
            isNext = data.next();
          }

          return new ReadLogResult(itemCount, firstFoundSequenceNumber, asnOut.toByteArray(), includesFirstItem, !isNext, moreItems);
        }
      }
    }
  }

  /**
   * Read history records from the data history
   * and return Bacnet trend records specified by the requested reference position.
   */
  private ReadLogResult readRangeByPosition(long refIndex, int count, int maxSize)
  {
    try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
    {
      BIHistory dataHistory = getHistory(conn);
      reinitTimestamps(conn, dataHistory);
      BAbsTime firstTimestamp = conn.getFirstTimestamp(dataHistory);
      BAbsTime lastTimestamp = conn.getLastTimestamp(dataHistory);
      BAbsTime recTimestamp = null;
      long itemCount = 0L;
      boolean includesFirstItem = false;  // set to true if the response includes the first entry
      boolean includesLastItem = false;  // set to true if the response includes the last entry
      boolean moreItems = false;  // set to true if the number of entries requested is more than the response can handle.
      BTrendRecord r = null;

      synchronized (asnOut)
      {
        if (count >= 0)
        {
          asnOut.reset();
          long position = 1L;
          // Get all of the data records
          try (Cursor<BHistoryRecord> data = conn.scan(dataHistory))
          {
            while (data.next() && (itemCount < count))
            {
              if (position >= refIndex)  // skip until we get to the refIndex position
              {
                r = (BTrendRecord)data.get();
                recTimestamp = r.getTimestamp();
                int logDatumType = getLogDatumType(r);
                if (logDatumType >= 0)
                {
                  AsnOutputStream temp = new AsnOutputStream();
                  try
                  {
                    asnOut.writeTo(temp);
                  }
                  catch (Exception e)
                  {
                    log.log(Level.WARNING, "Error caching trend records during read by position, Bacnet Object ID " + getObjectId(), e);
                    temp = asnOut;
                  }

                  BBacnetLogRecord.writeLogRecord(recTimestamp,
                                                  (BSimple)r.get(r.getValueProperty()),
                                                  logDatumType,
                                                  r.getStatus(),
                                                  getLogEvent(r).getLong(),
                                                  asnOut);

                  if ((maxSize > 0) && (asnOut.size() > maxSize))
                  {
                    asnOut = temp;
                    moreItems = true;
                    break;
                  }

                  if (recTimestamp.equals(firstTimestamp))
                  {
                    includesFirstItem = true;
                  }
                  if (recTimestamp.equals(lastTimestamp))
                  {
                    includesLastItem = true;
                  }
                  itemCount++;
                }
              }
              position++;
            }
          }
        }
        else
        {
          count = -count;
          ArrayList<BTrendRecord> records = new ArrayList<>();
          // Get all of the data records
          try (Cursor<BHistoryRecord> data = conn.scan(dataHistory))
          {
            while (data.next())
            {
              r = (BTrendRecord) data.get();
              if (getLogDatumType(r) >= 0)
              {
                records.add(r);
              }
            }
          }

          int numRecords = records.size();
          int startIndex = 0;
          if (numRecords > count)
          {
            startIndex = numRecords - count;
            numRecords = count;
          }

          asnOut.reset();
          for (int i = startIndex; i < numRecords; i++)
          {
            r = records.get(i);
            recTimestamp = r.getTimestamp();
            AsnOutputStream temp = new AsnOutputStream();
            try
            {
              asnOut.writeTo(temp);
            }
            catch (Exception e)
            {
              log.log(Level.WARNING, "Error caching trend records during read by position, Bacnet Object ID " + getObjectId(), e);
              temp = asnOut;
            }

            BBacnetLogRecord.writeLogRecord(recTimestamp,
                                            (BSimple)r.get(r.getValueProperty()),
                                            getLogDatumType(r),
                                            r.getStatus(),
                                            getLogEvent(r).getLong(),
                                            asnOut);
            if ((maxSize > 0) && (asnOut.size() > maxSize))
            {
              asnOut = temp;
              moreItems = true;
              break;
            }

            if (recTimestamp.equals(firstTimestamp))
            {
              includesFirstItem = true;
            }
            if (recTimestamp.equals(lastTimestamp))
            {
              includesLastItem = true;
            }
            itemCount++;
          }
        }
      }

      return new ReadLogResult(itemCount, NOT_USED, asnOut.toByteArray(), includesFirstItem, includesLastItem, moreItems);
    }
  }

  /**
   * Read history records from the data history
   * and return Bacnet trend records specified by the requested reference time.
   */
  private ReadLogResult readRangeByTime(BBacnetDateTime refTime, int count, int maxSize)
  {
    BAbsTime referenceTime = refTime.toBAbsTime();
    try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
    {
      BIHistory dataHistory = getHistory(conn);
      reinitTimestamps(conn, dataHistory);
      BAbsTime firstTimestamp = conn.getFirstTimestamp(dataHistory);
      BAbsTime lastTimestamp = conn.getLastTimestamp(dataHistory);
      long itemCount = 0L;
      long firstFoundSequenceNumber = 0L;
      boolean includesFirstItem = false;  // set to true if the response includes the first entry
      boolean includesLastItem = false;  // set to true if the response includes the last entry
      boolean moreItems = false;  // set to true if the number of entries requested is more than the response can handle.
      synchronized (asnOut)
      {
        if (count >= 0)
        {
          asnOut.reset();
          long loopCount = 0L;
          try (Cursor<BHistoryRecord> data = conn.timeQuery(dataHistory, referenceTime, null).cursor())
          { // Get all of the data records after referenceTime
            BTrendRecord r = null;
            while (data.next())
            {
              if (count > itemCount)
              {
                r = (BTrendRecord)data.get();
                BBacnetDateTime recTime = new BBacnetDateTime(r.getTimestamp());
                if ((getLogDatumType(r) >= 0) && // Don't include any invalid String entries
                  (!(recTime.toBAbsTime().equals(referenceTime)))) // Don't include an entry with the same reference time
                {
                  AsnOutputStream temp = new AsnOutputStream();

                  try
                  {
                    asnOut.writeTo(temp);
                  }
                  catch (Exception e)
                  {
                    log.log(Level.WARNING, "Error caching trend records during read by time, Bacnet Object ID " + getObjectId(), e);
                    temp = asnOut;
                  }

                  BBacnetLogRecord.writeLogRecord(r.getTimestamp(),
                                                  (BSimple)r.get(r.getValueProperty()),
                                                  getLogDatumType(r),
                                                  r.getStatus(),
                                                  getLogEvent(r).getLong(),
                                                  asnOut);

                  if ((maxSize > 0) && (asnOut.size() > maxSize))
                  {
                    asnOut = temp;
                    moreItems = true;
                    break;
                  }

                  if (r.getTimestamp().equals(firstTimestamp))
                  {
                    includesFirstItem = true;
                  }

                  if (r.getTimestamp().equals(lastTimestamp))
                  {
                    includesLastItem = true;
                  }

                  if (itemCount == 0L)
                  {
                    firstFoundSequenceNumber = loopCount + 1L;
                  }

                  itemCount++;
                }
              }
              loopCount++;
            }
          }
          firstFoundSequenceNumber = getLastSeqNum() - loopCount + firstFoundSequenceNumber;
        }
        else
        {
          ArrayList<Object> records = new ArrayList<>();
          int adjustedCount = count * -1; // get absolute value of count
          try (Cursor<BHistoryRecord> data = conn.timeQuery(dataHistory, null, referenceTime).cursor())  // Get all of the data records before referenceTime
          {
            // create a ArrayList of all of the data entries up to referenceTime.
            // then only include the last 'count' ones.
            // don't forget to delete the ArrayList when finished!
            long loopCount = getFirstSeqNum();
            while (data.next())
            {
              BBacnetDateTime recTime = new BBacnetDateTime(data.get().getTimestamp());
              if ((getLogDatumType((BTrendRecord) data.get()) >= 0) && // Don't include any invalid String entries
                  (!(recTime.toBAbsTime().equals(referenceTime)))) // Don't include an entry with the same reference time
              {
                records.add(data.get());
                records.add(BLong.make(loopCount));
              }
              loopCount++;
            }
          }

          int numRecords = records.size() / 2;
          int startIndex = 0;
          if (numRecords > adjustedCount)
          {
            startIndex = numRecords - adjustedCount;
            numRecords = adjustedCount;
          }

          asnOut.reset();
          BTrendRecord r = null;

          for (int i = startIndex; i < numRecords; i++)
          {
            r = (BTrendRecord)records.get(i * 2);

            AsnOutputStream temp = new AsnOutputStream();

            try
            {
              asnOut.writeTo(temp);
            }
            catch (Exception e)
            {
              log.log(Level.WARNING, "Error exporting trend records by time, Bacnet Object ID " + getObjectId(), e);
              temp = asnOut;
            }

            BBacnetLogRecord.writeLogRecord(r.getTimestamp(),
                                            (BSimple)r.get(r.getValueProperty()),
                                            getLogDatumType(r),
                                            r.getStatus(),
                                            getLogEvent(r).getLong(),
                                            asnOut);

            if ((maxSize > 0) && (asnOut.size() > maxSize))
            {
              asnOut = temp;
              moreItems = true;
              break;
            }

            if (r.getTimestamp().equals(firstTimestamp))
            {
              includesFirstItem = true;
            }

            if (r.getTimestamp().equals(lastTimestamp))
            {
              includesLastItem = true;
            }

            if (itemCount == 0L)
            {
              firstFoundSequenceNumber = ((BLong) records.get((i * 2) + 1)).getLong();
            }

            itemCount++;
          }
          records.clear();
        }
      }

      return new ReadLogResult(itemCount, firstFoundSequenceNumber, asnOut.toByteArray(), includesFirstItem, includesLastItem, moreItems);
    }
  }

  private static int getLogDatumType(BTrendRecord record)
  {
    if (record instanceof BBacnetTrendRecord)
    {
      return ((BBacnetTrendRecord) record).getLogDatumType();
    }

    // Use the type of the value to determine the LogDatumType!
    BValue recordType = record.get(record.getValueProperty());
    if (recordType instanceof BBoolean)
    {
      return BBacnetLogRecord.BOOLEAN_VALUE_TAG;
    }
    else if (recordType instanceof BDouble)
    {
      return BBacnetLogRecord.REAL_VALUE_TAG;
    }
    else if (recordType instanceof BEnum)
    {
      return BBacnetLogRecord.ENUM_VALUE_TAG;
    }
    else if (recordType instanceof BBacnetUnsigned)
    {
      return BBacnetLogRecord.UNSIGNED_VALUE_TAG;
    }
    else if (recordType instanceof BFloat)
    {
      return BBacnetLogRecord.REAL_VALUE_TAG;
    }
    else if (recordType instanceof BInteger)
    {
      return BBacnetLogRecord.SIGNED_VALUE_TAG;
    }
    else if (recordType instanceof BBacnetBitString)
    {
      return BBacnetLogRecord.BITSTRING_VALUE_TAG;
    }
    else if (recordType instanceof BBacnetNull)
    {
      return BBacnetLogRecord.NULL_VALUE_TAG;
    }
    else if (recordType instanceof BTrendEvent)
    {
      BTrendEvent evt = (BTrendEvent)recordType;
      if (evt.isLogStatus())
      {
        return BBacnetLogRecord.LOG_STATUS_TAG;
      }
      if (evt.isFailure())
      {
        return BBacnetLogRecord.FAILURE_TAG;
      }
      if (evt.isTimeChange())
      {
        return BBacnetLogRecord.TIME_CHANGE_TAG;
      }
    }
    else if (recordType instanceof BString)
    {
      return BBacnetLogRecord.ANY_VALUE_TAG;
    }

    return -1; // Invalid type!
  }

  private static BTrendEvent getLogEvent(BTrendRecord record)
  {
    // If it's a Bacnet trend record, look for a log event
    if (record instanceof BBacnetTrendRecord)
    {
      return ((BBacnetTrendRecord) record).getLogEvent();
    }

    return BTrendEvent.DEFAULT;
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
          results.add(readProperty(refs[i].getPropertyId(),
                                   refs[i].getPropertyArrayIndex()));
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
    int pId = rangeReference.getPropertyId();
    if (pId == BBacnetPropertyIdentifier.LOG_BUFFER)
    {
      int maxDataSize = -1;
      if (rangeReference instanceof BacnetConfirmedRequest)
      {
        maxDataSize = ((BacnetConfirmedRequest) rangeReference).getMaxDataLength();
      }
      int count = rangeReference.getCount();
      switch (rangeReference.getRangeType())
      {
        case RangeReference.BY_SEQUENCE_NUMBER:
          // By Sequence Number is not supported for exported Niagara histories, as the stored
          // history does not have sequence numbers on its records.
          logger.warning("BY_SEQUENCE_NUMBER is not supported for NiagaraHistoryDescriptor, transaction rejected");
          throw new RejectException(BBacnetRejectReason.PARAMETER_OUT_OF_RANGE);

        case RangeReference.BY_POSITION:
          long refIndex = rangeReference.getReferenceIndex();
          try
          {
            // Read the history records, using the appropriate method.
            ReadLogResult rlr = readRangeByPosition(refIndex, count, maxDataSize);

            return new ReadRangeAck(getObjectId(),
                                    pId,
                                    NOT_USED,
                                    rlr.getResultFlags(),
                                    rlr.itemCount,
                                    (rlr.itemCount > 0) ? rlr.firstSequenceNumber : NOT_USED,
                                    rlr.itemData);
          }
          catch (Exception e)
          {
            logException(Level.SEVERE, getObjectId() + ": could not readRange by position", e);
            return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.OTHER);
          }

        case RangeReference.BY_TIME:
          // FIXX Should I also include the deprecated version of By_time?  May also be able to support by position
          BBacnetDateTime refTime = rangeReference.getReferenceTime();
          try
          {
            // Read the history records, using the appropriate method.
            ReadLogResult rlr = readRangeByTime(refTime, count, maxDataSize);

            return new ReadRangeAck(getObjectId(),
                                    pId,
                                    NOT_USED,
                                    rlr.getResultFlags(),
                                    rlr.itemCount,
                                    (rlr.itemCount > 0) ? rlr.firstSequenceNumber : NOT_USED,
                                    rlr.itemData);
          }
          catch (Exception e)
          {
            logException(Level.SEVERE, getObjectId() + ": could not readRange by time", e);
            return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.OTHER);
          }

        case NOT_USED:
          try
          {
            // Read the history records, using the appropriate method.
            ReadLogResult rlr = readRangeAll(maxDataSize);

            return new ReadRangeAck(getObjectId(),
                                    pId,
                                    NOT_USED,
                                    rlr.getResultFlags(),
                                    rlr.itemCount,
                                    (rlr.itemCount > 0) ? rlr.firstSequenceNumber : NOT_USED,
                                    rlr.itemData);
          }
          catch (Exception e)
          {
            logException(Level.SEVERE, getObjectId() + ": could not readRange all records", e);
            return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.OTHER);
          }

        default:
          log.info(getObjectId() + ": unsupported ReadRange Range Type: " + rangeReference.getRangeType());
          return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.OTHER);
      }
    }

    // Handle all other properties here.
    for (int i = 0; i < REQUIRED_PROPS.length; i++)
    {
      if (pId == REQUIRED_PROPS[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    int[] props = getOptionalProps();
    for (int i = 0; i < props.length; i++)
    {
      if (pId == props[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
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
    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.LOG_BUFFER)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
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
                                      new NErrorType(BBacnetErrorClass.SERVICES,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }
      int[] props = getOptionalProps();
      for (int i = 0; i < props.length; i++)
      {
        if (propertyId == props[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }

      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.UNKNOWN_PROPERTY),
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
    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.LOG_BUFFER)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
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
                                      new NErrorType(BBacnetErrorClass.SERVICES,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }
      int[] props = getOptionalProps();
      for (int i = 0; i < props.length; i++)
      {
        if (propertyId == props[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }

      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.UNKNOWN_PROPERTY),
                                  0);
    }
  }

////////////////////////////////////////////////////////////////
// Bacnet Support
////////////////////////////////////////////////////////////////

  /**
   * Is the property referenced by this propertyId an array property?
   *
   * @return true if it is an array property, false if not or if the
   * propertyId does not refer to a property in this object.
   */
  boolean isArray(int propId)
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
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
        BBacnetErrorCode.INVALID_ARRAY_INDEX));
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

      // Begin Scott additions
      case BBacnetPropertyIdentifier.ENABLE:
        log.info("ReadProperty for enable is not accessible for NiagaraHistoryDescriptor");
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.READ_ACCESS_DENIED));

      case BBacnetPropertyIdentifier.STOP_WHEN_FULL:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getHistory().getConfig().getFullPolicy().equals(BFullPolicy.stop)));

      case BBacnetPropertyIdentifier.BUFFER_SIZE:
        long maxRecords = BBacnetUnsigned.MAX_UNSIGNED_VALUE;
        BIHistory hist = getHistory();
        if (hist.getConfig().getCapacity().isByRecordCount())
        {
          maxRecords = hist.getConfig().getCapacity().getMaxRecords();
        }
        else if (hist.getConfig().getCapacity().isByStorageSize())
        {
          maxRecords = hist.getConfig().getCapacity().getMaxStorage();
        }
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(maxRecords));

      case BBacnetPropertyIdentifier.LOG_BUFFER:
        log.info("ReadProperty for logBuffer is not accessible except via ReadRange for NiagaraHistoryDescriptor");
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.READ_ACCESS_DENIED));

      case BBacnetPropertyIdentifier.RECORD_COUNT:
        try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(conn.getRecordCount(getHistory(conn))));
        }

      case BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT:
        return readTotalRecordCount();
      case BBacnetPropertyIdentifier.EVENT_STATE:
        return readEventState();
      default:
        return readOptionalProperty(pId, ndx);
    }
  }

  private PropertyValue readTotalRecordCount()
  {
    try (HistoryDatabaseConnection conn = getHistoryDbConnection(null))
    {
      BIHistory history = getHistory(conn);
      reinitTimestamps(conn, history);
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT,
        NOT_USED,
        AsnUtil.toAsnUnsigned(getLastSeqNum()));
    }
  }

  private PropertyValue readEventState()
  {
    if (!getEventDetectionEnable())
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_STATE,
        NOT_USED,
        AsnUtil.toAsnEnumerated(BBacnetEventState.NORMAL));
    }

    BBacnetTrendLogAlarmSourceExt alarmExt = getAlarmExt();
    if (alarmExt == null)
    {
      // Object does not support event reporting, set to Normal.
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_STATE,
        NOT_USED,
        AsnUtil.toAsnEnumerated(BBacnetEventState.NORMAL));
    }

    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.EVENT_STATE,
      NOT_USED,
      AsnUtil.toAsnEnumerated(BBacnetEventState.fromBAlarmState(alarmExt.getAlarmState())));
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
    // Check for array index on non-array property.
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
      return new NErrorType(BBacnetErrorClass.PROPERTY,
        BBacnetErrorCode.INVALID_ARRAY_INDEX);
    }

    try
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.OBJECT_NAME:
          return BacUtil.setObjectName(this, objectName, val);

        case BBacnetPropertyIdentifier.ENABLE:
        case BBacnetPropertyIdentifier.RECORD_COUNT:
        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        case BBacnetPropertyIdentifier.OBJECT_TYPE:
        case BBacnetPropertyIdentifier.STOP_WHEN_FULL:
        case BBacnetPropertyIdentifier.BUFFER_SIZE:
        case BBacnetPropertyIdentifier.LOG_BUFFER:
        case BBacnetPropertyIdentifier.TOTAL_RECORD_COUNT:
        case BBacnetPropertyIdentifier.EVENT_STATE:
          if (logger.isLoggable(Level.FINE))
          {
            logger.fine(getObjectId() + ": attempted to write read-only property " + BBacnetPropertyIdentifier.tag(pId));
          }
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.DESCRIPTION:
          setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        default:
          return writeOptionalProperty(pId, ndx, val, pri);
      }
    }
    catch (AsnException e)
    {
      logException(Level.INFO, getObjectId() + ": AsnException writing property " + BBacnetPropertyIdentifier.tag(pId), e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      logException(Level.INFO, getObjectId() + ": PermissionException writing property " + BBacnetPropertyIdentifier.tag(pId), e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
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
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAlarmEnable())));

        case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getEventDetectionEnable()));

        case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
          return readAckedTransitions(almExt.getAckedTransitions());
        case BBacnetPropertyIdentifier.NOTIFY_TYPE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(almExt.getNotifyType()));

        case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
          return readEventTimeStamps(almExt.getToOffnormalTimes().getAlarmTime(), almExt.getToFaultTimes().getAlarmTime(),
                                     almExt.getToNormalTimes().getAlarmTime(), ndx);

        case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
          return readEventMessageTexts(ndx);

        case BBacnetPropertyIdentifier.NOTIFICATION_THRESHOLD:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getNotificationThreshold()));

        case BBacnetPropertyIdentifier.RECORDS_SINCE_NOTIFICATION:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getRecordsSinceNotification()));

        case BBacnetPropertyIdentifier.LAST_NOTIFY_RECORD:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getLastNotifyRecord()));

        default:
          // Fall through to the next round of checks.
          break;
      }
    }

    if (pId == BBacnetPropertyIdentifier.LOG_INTERVAL)
    {
      BCollectionInterval collInt = getHistory().getConfig().getInterval();
      long logInt = (collInt.isIrregular() ? 0L : collInt.getInterval().getMillis() / 10);
      return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(logInt));
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
        NOT_USED,
        AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(eventTrans)));
    }
    else
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.ACKED_TRANSITIONS,
        NOT_USED,
        AsnUtil.toAsnBitString(ACKED_TRANS_DEFAULT));
    }
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
          case BBacnetPropertyIdentifier.NOTIFY_TYPE:
            set(BBacnetTrendLogAlarmSourceExt.notifyType,
                BBacnetNotifyType.make(AsnUtil.fromAsnEnumerated(val)),
                BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
            int ncinst = AsnUtil.fromAsnUnsignedInt(val);
            if (ncinst > BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            BBacnetObjectIdentifier ncid = BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS, ncinst);
            BBacnetNotificationClassDescriptor nc = (BBacnetNotificationClassDescriptor)BBacnetNetwork.localDevice().lookupBacnetObject(ncid);
            if (nc == null)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            BAlarmClass ac = nc.getAlarmClass();
            almExt.setString(BBacnetTrendLogAlarmSourceExt.alarmClass,
                             ac.getName(),
                             BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.EVENT_ENABLE:
          case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
          case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
          case BBacnetPropertyIdentifier.NOTIFICATION_THRESHOLD:
          case BBacnetPropertyIdentifier.RECORDS_SINCE_NOTIFICATION:
          case BBacnetPropertyIdentifier.LAST_NOTIFY_RECORD:
            if (logger.isLoggable(Level.FINE))
            {
              logger.fine(getObjectId() + ": attempted to write read-only property " + BBacnetPropertyIdentifier.tag(pId));
            }
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
          {
            setBoolean(eventDetectionEnable, AsnUtil.fromAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());
            return null;
          }
        }
      }

      if (pId == BBacnetPropertyIdentifier.LOG_INTERVAL)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.fine(getObjectId() + ": attempted to write read-only property " + BBacnetPropertyIdentifier.tag(pId));
        }
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }
    }
    catch (AsnException e)
    {
      logException(Level.INFO, getObjectId() + ": AsnException writing property " + BBacnetPropertyIdentifier.tag(pId), e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      logException(Level.INFO, getObjectId() + ": PermissionException writing property " + BBacnetPropertyIdentifier.tag(pId), e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
    catch (IllegalArgumentException e)
    {
      logException(Level.INFO, getObjectId() + ": IllegalArgumentException writing property " + BBacnetPropertyIdentifier.tag(pId), e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }

    return new NErrorType(BBacnetErrorClass.PROPERTY,
                          BBacnetErrorCode.UNKNOWN_PROPERTY);
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
      v.add(BBacnetPropertyIdentifier.eventDetectionEnable);
    }
    optionalProps = new int[v.size()];
    for (int i = 0; i < optionalProps.length; i++)
    {
      optionalProps[i] = ((BEnum) v.get(i)).getOrdinal();
    }
    return optionalProps;
  }

  private static void logException(Level level, String message, Exception e)
  {
    if (logger.isLoggable(Level.FINE))
    {
      logger.log(level, message + "; exception: " + e.getLocalizedMessage(), e);
    }
    else if (logger.isLoggable(level))
    {
      logger.log(level, message + "; exception: " + e.getLocalizedMessage());
    }
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
    out.trTitle("BacnetNiagaraHistoryDescriptor", 2);
    out.prop("history", getHistory());
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

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int[] optionalProps;
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static AsnOutputStream asnOut = new AsnOutputStream();

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
      BBacnetPropertyIdentifier.EVENT_STATE
    };

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, getOptionalProps());
  }

  static Logger log = Logger.getLogger("bacnet.server");

////////////////////////////////////////////////////////////////
// Inner Class: ReadLogResult
////////////////////////////////////////////////////////////////

  /**
   * ReadLogResult encapsulates all of the results of reading
   * history data required to complete a Bacnet ReadRange-ACK.
   */
  static class ReadLogResult
    implements RangeData
  {
    ReadLogResult(long ic, long fsn, byte[] id, boolean inclFirst, boolean inclLast, boolean more)
    {
      itemCount = ic;
      firstSequenceNumber = fsn;
      itemData = id;
      includeFirst = inclFirst;
      includeLast = inclLast;
      moreItems = more;
    }

    @Override
    public BBacnetBitString getResultFlags()
    {
      return BBacnetBitString.make(new boolean[] { includeFirst, includeLast, moreItems });
    }

    @Override
    public boolean includesFirstItem()
    {
      return includeFirst;
    }

    @Override
    public boolean includesLastItem()
    {
      return includeLast;
    }

    @Override
    public boolean isMoreItems()
    {
      return moreItems;
    }

    @Override
    public long getItemCount()
    {
      return itemCount;
    }

    @Override
    public long getFirstSequenceNumber()
    {
      return firstSequenceNumber;
    }

    @Override
    public byte[] getItemData()
    {
      return itemData;
    }

    @Override
    public ErrorType getError()
    {
      return null;
    }

    @Override
    public int getErrorClass()
    {
      return -1;
    }

    @Override
    public int getErrorCode()
    {
      return -1;
    }

    @Override
    public boolean isError()
    {
      return false;
    }

    // PropertyReference stub
    @Override
    public int getPropertyId()
    {
      return BBacnetPropertyIdentifier.LOG_BUFFER;
    }

    @Override
    public int getPropertyArrayIndex()
    {
      return -1;
    }

    @Override
    public void writeAsn(AsnOutput out)
    {
    }

    @Override
    public void readAsn(AsnInput in)
    {
    }

    long itemCount;
    long firstSequenceNumber;
    byte[] itemData;
    boolean includeFirst;
    boolean includeLast;
    boolean moreItems;
  }
}
