/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BStruct;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;
import javax.baja.util.BUuid;

import com.tridium.alarm.BIAlarmRecordDecorator;

/**
 * Representation of a time stamped alarm record.
 *
 *<p>
 * Alarm records are mutable records that are capable of changing
 * type as their state changes.  For more information on state changes,
 * refer to the package documentation.  Typical state changes include:
 *   <ul>
 *     <li>Alarm -&gt; Alarm Ack Request -&gt; Alarm Ack Notification</li>
 *   </ul>
 *
 * @author    Blake M Puhak
 * @author    Dan Giorgis
 * @creation  16 Feb 01
 * @version   $Revision: 84$ $Date: 10/6/10 3:15:16 PM EDT$javax.baja.alarm.BAlarmRecord(3666406805)1.0$ @
 */
@NiagaraType
/*
 The time that the alarm was generated.
 */
@NiagaraProperty(
  name = "timestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.SUMMARY,
  facets = @Facet(name = "BFacets.SHOW_SECONDS", value = "BBoolean.TRUE")
)
/*
 The unique universal identifier of the alarm.
 */
@NiagaraProperty(
  name = "uuid",
  type = "BUuid",
  defaultValue = "BUuid.make()"
)
/*
 The current state of the alarm source.
 */
@NiagaraProperty(
  name = "sourceState",
  type = "BSourceState",
  defaultValue = "BSourceState.offnormal"
)
/*
 The current acknowledged state of the alarm.
 */
@NiagaraProperty(
  name = "ackState",
  type = "BAckState",
  defaultValue = "BAckState.unacked"
)
/*
 If alarm is required to be routed back to its source.
 */
@NiagaraProperty(
  name = "ackRequired",
  type = "boolean",
  defaultValue = "true"
)
/*
 The path to the source of the alarm. Should use getNavOrd().
 */
@NiagaraProperty(
  name = "source",
  type = "BOrdList",
  defaultValue = "BOrdList.DEFAULT",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.make(\"width\", BInteger.make(512))")
)
/*
 The path to the alarm class of the alarm.  In BacSpeak, the Notification
 class of the alarm.  Essentially, the routing information.
 */
@NiagaraProperty(
  name = "alarmClass",
  type = "String",
  defaultValue = "defaultAlarmClass",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.make(\"width\", BInteger.make(256))")
)
/*
 The priority of the alarm (0=high, 255=low).
 */
@NiagaraProperty(
  name = "priority",
  type = "int",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
/*
 The time at which the alarm goes back to normal state
 */
@NiagaraProperty(
  name = "normalTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  facets = @Facet(name = "BFacets.SHOW_SECONDS", value = "BBoolean.TRUE")
)
/*
 The time at which the alarm is acked.  Note: that interpretation of this
 property's value depends upon the state of the alarm.
 */
@NiagaraProperty(
  name = "ackTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  facets = @Facet(name = "BFacets.SHOW_SECONDS", value = "BBoolean.TRUE")
)
/*
 The name of the user who acknowledged the alarm.
 */
@NiagaraProperty(
  name = "user",
  type = "String",
  defaultValue = "Unknown User"
)
/*
 Containing dynamic alarm data, in key-value pairs.
 */
@NiagaraProperty(
  name = "alarmData",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  facets = @Facet("BFacets.make(\"width\", BInteger.make(1024))")
)
/*
 The initial source state that caused the alarm to be generated.
 */
@NiagaraProperty(
  name = "alarmTransition",
  type = "BSourceState",
  defaultValue = "BSourceState.offnormal"
)
/*
 The time at which the alarm was last updated. Updates occur at creation,
 acknowledgement, and changes to alarmData such as notes.
 */
@NiagaraProperty(
  name = "lastUpdate",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  facets = @Facet(name = "BFacets.SHOW_SECONDS", value = "BBoolean.TRUE")
)
public final class BAlarmRecord
   extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmRecord(1325579962)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "timestamp"

  /**
   * Slot for the {@code timestamp} property.
   * The time that the alarm was generated.
   * @see #getTimestamp
   * @see #setTimestamp
   */
  public static final Property timestamp = newProperty(Flags.SUMMARY, BAbsTime.NULL, BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE));

  /**
   * Get the {@code timestamp} property.
   * The time that the alarm was generated.
   * @see #timestamp
   */
  public BAbsTime getTimestamp() { return (BAbsTime)get(timestamp); }

  /**
   * Set the {@code timestamp} property.
   * The time that the alarm was generated.
   * @see #timestamp
   */
  public void setTimestamp(BAbsTime v) { set(timestamp, v, null); }

  //endregion Property "timestamp"

  //region Property "uuid"

  /**
   * Slot for the {@code uuid} property.
   * The unique universal identifier of the alarm.
   * @see #getUuid
   * @see #setUuid
   */
  public static final Property uuid = newProperty(0, BUuid.make(), null);

  /**
   * Get the {@code uuid} property.
   * The unique universal identifier of the alarm.
   * @see #uuid
   */
  public BUuid getUuid() { return (BUuid)get(uuid); }

  /**
   * Set the {@code uuid} property.
   * The unique universal identifier of the alarm.
   * @see #uuid
   */
  public void setUuid(BUuid v) { set(uuid, v, null); }

  //endregion Property "uuid"

  //region Property "sourceState"

  /**
   * Slot for the {@code sourceState} property.
   * The current state of the alarm source.
   * @see #getSourceState
   * @see #setSourceState
   */
  public static final Property sourceState = newProperty(0, BSourceState.offnormal, null);

  /**
   * Get the {@code sourceState} property.
   * The current state of the alarm source.
   * @see #sourceState
   */
  public BSourceState getSourceState() { return (BSourceState)get(sourceState); }

  /**
   * Set the {@code sourceState} property.
   * The current state of the alarm source.
   * @see #sourceState
   */
  public void setSourceState(BSourceState v) { set(sourceState, v, null); }

  //endregion Property "sourceState"

  //region Property "ackState"

  /**
   * Slot for the {@code ackState} property.
   * The current acknowledged state of the alarm.
   * @see #getAckState
   * @see #setAckState
   */
  public static final Property ackState = newProperty(0, BAckState.unacked, null);

  /**
   * Get the {@code ackState} property.
   * The current acknowledged state of the alarm.
   * @see #ackState
   */
  public BAckState getAckState() { return (BAckState)get(ackState); }

  /**
   * Set the {@code ackState} property.
   * The current acknowledged state of the alarm.
   * @see #ackState
   */
  public void setAckState(BAckState v) { set(ackState, v, null); }

  //endregion Property "ackState"

  //region Property "ackRequired"

  /**
   * Slot for the {@code ackRequired} property.
   * If alarm is required to be routed back to its source.
   * @see #getAckRequired
   * @see #setAckRequired
   */
  public static final Property ackRequired = newProperty(0, true, null);

  /**
   * Get the {@code ackRequired} property.
   * If alarm is required to be routed back to its source.
   * @see #ackRequired
   */
  public boolean getAckRequired() { return getBoolean(ackRequired); }

  /**
   * Set the {@code ackRequired} property.
   * If alarm is required to be routed back to its source.
   * @see #ackRequired
   */
  public void setAckRequired(boolean v) { setBoolean(ackRequired, v, null); }

  //endregion Property "ackRequired"

  //region Property "source"

  /**
   * Slot for the {@code source} property.
   * The path to the source of the alarm. Should use getNavOrd().
   * @see #getSource
   * @see #setSource
   */
  public static final Property source = newProperty(Flags.SUMMARY, BOrdList.DEFAULT, BFacets.make("width", BInteger.make(512)));

  /**
   * Get the {@code source} property.
   * The path to the source of the alarm. Should use getNavOrd().
   * @see #source
   */
  public BOrdList getSource() { return (BOrdList)get(source); }

  /**
   * Set the {@code source} property.
   * The path to the source of the alarm. Should use getNavOrd().
   * @see #source
   */
  public void setSource(BOrdList v) { set(source, v, null); }

  //endregion Property "source"

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * The path to the alarm class of the alarm.  In BacSpeak, the Notification
   * class of the alarm.  Essentially, the routing information.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(Flags.SUMMARY, "defaultAlarmClass", BFacets.make("width", BInteger.make(256)));

  /**
   * Get the {@code alarmClass} property.
   * The path to the alarm class of the alarm.  In BacSpeak, the Notification
   * class of the alarm.  Essentially, the routing information.
   * @see #alarmClass
   */
  public String getAlarmClass() { return getString(alarmClass); }

  /**
   * Set the {@code alarmClass} property.
   * The path to the alarm class of the alarm.  In BacSpeak, the Notification
   * class of the alarm.  Essentially, the routing information.
   * @see #alarmClass
   */
  public void setAlarmClass(String v) { setString(alarmClass, v, null); }

  //endregion Property "alarmClass"

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * The priority of the alarm (0=high, 255=low).
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code priority} property.
   * The priority of the alarm (0=high, 255=low).
   * @see #priority
   */
  public int getPriority() { return getInt(priority); }

  /**
   * Set the {@code priority} property.
   * The priority of the alarm (0=high, 255=low).
   * @see #priority
   */
  public void setPriority(int v) { setInt(priority, v, null); }

  //endregion Property "priority"

  //region Property "normalTime"

  /**
   * Slot for the {@code normalTime} property.
   * The time at which the alarm goes back to normal state
   * @see #getNormalTime
   * @see #setNormalTime
   */
  public static final Property normalTime = newProperty(0, BAbsTime.NULL, BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE));

  /**
   * Get the {@code normalTime} property.
   * The time at which the alarm goes back to normal state
   * @see #normalTime
   */
  public BAbsTime getNormalTime() { return (BAbsTime)get(normalTime); }

  /**
   * Set the {@code normalTime} property.
   * The time at which the alarm goes back to normal state
   * @see #normalTime
   */
  public void setNormalTime(BAbsTime v) { set(normalTime, v, null); }

  //endregion Property "normalTime"

  //region Property "ackTime"

  /**
   * Slot for the {@code ackTime} property.
   * The time at which the alarm is acked.  Note: that interpretation of this
   * property's value depends upon the state of the alarm.
   * @see #getAckTime
   * @see #setAckTime
   */
  public static final Property ackTime = newProperty(0, BAbsTime.NULL, BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE));

  /**
   * Get the {@code ackTime} property.
   * The time at which the alarm is acked.  Note: that interpretation of this
   * property's value depends upon the state of the alarm.
   * @see #ackTime
   */
  public BAbsTime getAckTime() { return (BAbsTime)get(ackTime); }

  /**
   * Set the {@code ackTime} property.
   * The time at which the alarm is acked.  Note: that interpretation of this
   * property's value depends upon the state of the alarm.
   * @see #ackTime
   */
  public void setAckTime(BAbsTime v) { set(ackTime, v, null); }

  //endregion Property "ackTime"

  //region Property "user"

  /**
   * Slot for the {@code user} property.
   * The name of the user who acknowledged the alarm.
   * @see #getUser
   * @see #setUser
   */
  public static final Property user = newProperty(0, "Unknown User", null);

  /**
   * Get the {@code user} property.
   * The name of the user who acknowledged the alarm.
   * @see #user
   */
  public String getUser() { return getString(user); }

  /**
   * Set the {@code user} property.
   * The name of the user who acknowledged the alarm.
   * @see #user
   */
  public void setUser(String v) { setString(user, v, null); }

  //endregion Property "user"

  //region Property "alarmData"

  /**
   * Slot for the {@code alarmData} property.
   * Containing dynamic alarm data, in key-value pairs.
   * @see #getAlarmData
   * @see #setAlarmData
   */
  public static final Property alarmData = newProperty(0, BFacets.DEFAULT, BFacets.make("width", BInteger.make(1024)));

  /**
   * Get the {@code alarmData} property.
   * Containing dynamic alarm data, in key-value pairs.
   * @see #alarmData
   */
  public BFacets getAlarmData() { return (BFacets)get(alarmData); }

  /**
   * Set the {@code alarmData} property.
   * Containing dynamic alarm data, in key-value pairs.
   * @see #alarmData
   */
  public void setAlarmData(BFacets v) { set(alarmData, v, null); }

  //endregion Property "alarmData"

  //region Property "alarmTransition"

  /**
   * Slot for the {@code alarmTransition} property.
   * The initial source state that caused the alarm to be generated.
   * @see #getAlarmTransition
   * @see #setAlarmTransition
   */
  public static final Property alarmTransition = newProperty(0, BSourceState.offnormal, null);

  /**
   * Get the {@code alarmTransition} property.
   * The initial source state that caused the alarm to be generated.
   * @see #alarmTransition
   */
  public BSourceState getAlarmTransition() { return (BSourceState)get(alarmTransition); }

  /**
   * Set the {@code alarmTransition} property.
   * The initial source state that caused the alarm to be generated.
   * @see #alarmTransition
   */
  public void setAlarmTransition(BSourceState v) { set(alarmTransition, v, null); }

  //endregion Property "alarmTransition"

  //region Property "lastUpdate"

  /**
   * Slot for the {@code lastUpdate} property.
   * The time at which the alarm was last updated. Updates occur at creation,
   * acknowledgement, and changes to alarmData such as notes.
   * @see #getLastUpdate
   * @see #setLastUpdate
   */
  public static final Property lastUpdate = newProperty(0, BAbsTime.NULL, BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE));

  /**
   * Get the {@code lastUpdate} property.
   * The time at which the alarm was last updated. Updates occur at creation,
   * acknowledgement, and changes to alarmData such as notes.
   * @see #lastUpdate
   */
  public BAbsTime getLastUpdate() { return (BAbsTime)get(lastUpdate); }

  /**
   * Set the {@code lastUpdate} property.
   * The time at which the alarm was last updated. Updates occur at creation,
   * acknowledgement, and changes to alarmData such as notes.
   * @see #lastUpdate
   */
  public void setLastUpdate(BAbsTime v) { set(lastUpdate, v, null); }

  //endregion Property "lastUpdate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmRecord.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  /**
   * Create a default instance of an alarm record without creating
   * a new BUuid, to avoid consuming entropy. If a new BUuid is
   * required, use new BAlarmRecord(BUuid.make()).
   */
  public BAlarmRecord()
  {
    this(BUuid.DEFAULT);
  }

  /**
   * Creates a new default instance of an alarm record
   * @param uuid The uuid for this alarm record.
   */
  public BAlarmRecord(BUuid uuid)
  {
    this(BOrd.make(""),
         "defaultAlarmClass",
         BFacets.DEFAULT,
         uuid
    );
  }

  public BAlarmRecord(BComponent comp, String alarmClass, BFacets alarmData)
  {
    this(comp, alarmClass, alarmData, BUuid.DEFAULT);
  }

  public BAlarmRecord(BComponent comp, String alarmClass, BFacets alarmData, BUuid uuid)
  {
    this(BOrd.make(comp.getSlotPath()), alarmClass, alarmData, uuid);
  }

  /**
   * Create a new instance of an alarm record.
   *
   * @param source The string identifier of the alarm source.
   * @param alarmClass The class of the alarm used for notification/routing
   *   of the alarm.
   * @param alarmData The dynamic alarm data.
   */
  public BAlarmRecord(BOrd source, String alarmClass, BFacets alarmData)
  {
    this(source, alarmClass, alarmData, BUuid.DEFAULT);
  }

  /**
   * Create a new instance of an alarm record.
   *
   * @param source The string identifier of the alarm source.
   * @param alarmClass The class of the alarm used for notification/routing
   *   of the alarm.
   * @param alarmData The dynamic alarm data.
   * @param uuid The uuid for this alarm record.
   */
  public BAlarmRecord(BOrd source, String alarmClass, BFacets alarmData, BUuid uuid)
  {
    setSource(BOrdList.make(source));
    setAlarmClass(alarmClass);
    setAlarmData(alarmData);

    BAbsTime time = Clock.time();
    setUuid(uuid);
    setTimestamp(time);
    setLastUpdate(time);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Due to the text fields, alarm records do not have a fixed size.
   */
  public boolean isFixedSize()
  {
    return false;
  }

  /**
   * Get the size of this record.  This is a potentially
   * expensive operation.  The default behavior is to
   * serialize the record to a buffer and return the
   * resulting number of bytes.
   *
   * For non-fixed length records, return -1.
   *
   * @return Returns the size of the record in bytes or
   *   -1 if the size cannot be determined.
   * @since Niagara 4.0
   */
  public int getRecordSize()
  {
    return -1;
  }
  
  /**
   * Add an alarm acknowledgement notification to the alarm.
   */
  public void ackAlarm()
  {
    ackAlarm(getUser());
  }

  /**
   * Add an alarm acknowledgement notification to the alarm.
   *
   * @param user The user.
   */
  public void ackAlarm(String user)
  {
    BAbsTime time = Clock.time();
    if(getAckRequired())
    {
      setAckState(BAckState.ackPending);
    }
    else
    {
      setAckState(BAckState.acked);
    }
    setUser(user);
    setAckTime(time);
    setLastUpdate(time);
  }

  /**
   * Is the alarm record type an alarm?
   *
   * @return true if the record type is alarm.
   */
  public boolean isAlarm()
  {
    return getSourceState() != BSourceState.normal && getAckState() != BAckState.acked;
  }

  /**
   * @return true if the ackState is acked.
   */
  public boolean isAcknowledged()
  {
    return getAckState() == BAckState.acked;
  }
  
  /**
   * @return true if the ackState is ackPending.
   */
  public boolean isAckPending()
  {
    return getAckState() == BAckState.ackPending;
  }
  
  /**
   * @return true if the sourceState is normal.
   */
  public boolean isNormal()
  {
    return getSourceState() == BSourceState.normal;
  }

  /**
   * @return true if the record is not acked and not normal.
   */
  public boolean isOpen()
  {
    boolean acked = (getAckState() == BAckState.acked);
    
    //alerts are always open until acked - regarless of the ackRequired flag
    if ((getSourceState() == BSourceState.alert) && !acked)
    {
      return true;
    }
    
    //otherwise 
    return (!acked && getAckRequired())  ||
           (getSourceState() != BSourceState.normal  && getSourceState() != BSourceState.alert);
  }

  /**
   * And a name value pair to the alarmData.
   */
  public void addAlarmFacet(String key, BIDataValue value)
  {
    setAlarmData(BFacets.make(getAlarmData(), key, value));
  }
  
  /**
   * Get a value from the alarmData.
   */
  public BObject getAlarmFacet(String key)
  {
    return getAlarmData().get(key);
  }
  
  /**
   * If the alarm is a point alarm, this will look for an
   * ALARM_VALUE, OFFNORMAL_VALUE, or FAULT_VALUE in that order.  If nothing 
   * is found, returns null.
   * @return Null if nothing is found.
   */
  public BObject getAlarmValue()
  {
    BObject ret = getAlarmFacet(ALARM_VALUE);
    if (ret != null)
    {
      return ret;
    }
    ret = getAlarmFacet(OFFNORMAL_VALUE);
    if (ret != null)
    {
      return ret;
    }
    return getAlarmFacet(FAULT_VALUE);
  }

  /**
   * Remove a name value pair from the alarmData.
   */
  public void removeAlarmFacet(String key)
  {
    setAlarmData(BFacets.makeRemove(getAlarmData(), key));
  }
  
  @Override
  public String toString(Context ctxt)
  {
    StringBuilder sbuf = new StringBuilder();

    sbuf.append("timestamp = ");
    sbuf.append(getTimestamp()).append(", ");
    sbuf.append("sourceState = ");
    sbuf.append(getSourceState()).append(", ");
    sbuf.append("ackState = ");
    sbuf.append(getAckState()).append(", ");
    sbuf.append("ackRequired = ");
    sbuf.append(getAckRequired()).append(", ");
    sbuf.append("source = ");
    sbuf.append(getSource()).append(", ");
    sbuf.append("priority = ");
    sbuf.append(getPriority()).append(", ");
    sbuf.append("alarmClass = ");
    sbuf.append(getAlarmClass()); //.append(", ");
    
    return sbuf.toString();
  }

  /**
   * Station-Side only call for resolving BFormats.
   * Return getAlarmClass() if called from client.
   */
  public String getAlarmClassDisplayName(Context cx)
  {
    if (Sys.getStation() == null)
    {
      return getAlarmClass();
    }
    BAlarmService alarmService = (BAlarmService)Sys.getService(BAlarmService.TYPE);
    return alarmService.getAlarmClassDisplayName(BString.make(getAlarmClass()), cx).toString();
  }
  
  /**
   * @since Niagara 4.0
   */
  public BAlarmSchema getSchema()
  {
    BAlarmSchema schema = new BAlarmSchema();
    SlotCursor<Property> props = getProperties();
    while (props.next())
    {
      Property prop = props.property();
      schema.addColumn(prop.getName(), prop.getType().getTypeSpec());
    }
    
    schema.getColumnCount(); // go ahead and freeze it
    return schema;
  }
  
  /**
   * Get the serial version id for this record.  This
   * uniquely identifies the set of properties and the
   * format in which they are serialized.
   */
  public int getSerialVersionId()
  {
    return SERIAL_VERSION_ID;
  }

  /**
   * Write this record to the output.
   *
   * NOTE: Since Niagara 4.10 this method also acts as a convenience
   * (and backwards compatible) method for writing the record with no
   * special context (null) via the write(DataOutput out, Context context) method.
   */
  public void write(DataOutput out)
    throws IOException
  {
    write(out, null);
  }
  
  /**
   * Write this record to the output with the provided context.
   *
   * @since Niagara 4.10
   */
  public void write(DataOutput out, Context context)
    throws IOException
  {
    // IMPORTANT NOTE: Any change to this method with the null context
    // requires that the serial version id be incremented. Changes made
    // that are isolated to the Data Recovery Context are not required
    // to be backwards compatible as the "version" of the encoding will
    // never be different between when the record is written and read back.

    // NCCB-44647 NOTE: Further incremental improvements to the Data Recovery Service encoding
    // should come from generic improvements to the encoding process to avoid further
    // complicating this method. For instance, encoding of enum values like sourceState,
    // ackState and alarmTransition could be reduced from int (4 bytes) to short (2 bytes)
    // where the first byte indicates a < 127 value. A similar improvement could be made
    // for the alarm priority where it is likely to be less than integer value (max 255 is common).
    // Note that modifications to the generic encoding context (i.e. null context) would
    // require modifications to the serial version as noted above. However, even with
    // those reduced encodings, per record encoding reduction would only be < 10 bytes.
    //
    // Consider that the major space savings for Data Recovery alarm events from this point
    // forward (having eliminated duplicate UUID and optimized AbsTime encoding) will come
    // from the de-duplication of the repeated alarmClass, user UTF-8 string and/or referring to the
    // BOrdList source as BOrd handles rather than the full encoded UTF-8 string. For example,
    // replacing:
    //
    //     local:|station:|slot:/Folder/NumericWritable/OutOfRangeAlarmExt
    //
    // with its HandleOrd (i.e. something like "h:123") would save 50+ bytes in each alarm record
    // sent to the Data Recovery Service. Given that alarms should be sufficiently rare in a well
    // functioning system, and the risk associated with making such changes, I have elected not
    // to make further optimizations of the record at this time.
    //
    // Another place where the encoding could be improved is the alarmData BFacets "TimeZone" encoding.
    // The present encoding uses 160 bytes for "America/New_York", where as all of the DST transition
    // information is encoded. This strikes me as wasteful for "well known" Olson zones where the
    // ID should be enough to retrieve this information when decoded.

    // Give any registered decorators a chance to undecorate the alarm before writing
    // to serialized form
    BAlarmRecord undecoratedAlarmRec = BIAlarmRecordDecorator.undecorateAllOnWriteStart(this, context);
    try
    {
      undecoratedAlarmRec.encodeAbsTime(out, context, getTimestamp());
      undecoratedAlarmRec.getUuid().encode(out);
      undecoratedAlarmRec.getSourceState().encode(out);
      undecoratedAlarmRec.getAckState().encode(out);
      out.writeBoolean(undecoratedAlarmRec.getAckRequired());
      undecoratedAlarmRec.getSource().encode(out);
      out.writeUTF(undecoratedAlarmRec.getAlarmClass());
      out.writeInt(undecoratedAlarmRec.getPriority());
      undecoratedAlarmRec.encodeAbsTime(out, context, undecoratedAlarmRec.getNormalTime());
      undecoratedAlarmRec.encodeAbsTime(out, context, undecoratedAlarmRec.getAckTime());
      out.writeUTF(undecoratedAlarmRec.getUser());
      undecoratedAlarmRec.getAlarmData().encode(out);
      undecoratedAlarmRec.getAlarmTransition().encode(out);
      undecoratedAlarmRec.encodeAbsTime(out, context, undecoratedAlarmRec.getLastUpdate());
    }
    finally
    {
      // Give any registered decorators a chance to redecorate the alarm record after writing
      // to serialized form
      BIAlarmRecordDecorator.redecorateAllOnWriteEnd(undecoratedAlarmRec, context);
    }
  }

  /**
   * Read this record from the input.
   *
   * NOTE: Since Niagara 4.10 this method also acts as a convenience
   * (and backwards compatible) method for reading the record with no
   * special context (null) via the read(DataOutput out, Context context) method.
   *
   * Subclasses must override this method to read all of the record fields except
   * for the first timestamp.
   */
  public void read(DataInput in)
    throws IOException
  {
    read(in, null);
  }

  /**
   * Read this record from the input with the provided context.
   *
   * Subclasses must override this method to read all of the record fields except
   * for the first timestamp.
   *
   * @since Niagara 4.10
   */
  public void read(DataInput in, Context context)
    throws IOException
  {
    // IMPORTANT NOTE: Any change to this method with the null context
    // requires that the serial version id be incremented. Changes made
    // that are isolated to the Data Recovery Context are not required
    // to be backwards compatible as the "version" of the encoding will
    // never be different between when the record is written and read back.

    // Give any registered decorators a chance to decorate the alarm before reading from serialized form
    BIAlarmRecordDecorator.decorateAllOnReadStart(this, context);
    try
    {
      setTimestamp(decodeAbsTime(in, context));
      setUuid((BUuid) BUuid.DEFAULT.decode(in));
      setSourceState((BSourceState) BSourceState.normal.decode(in));
      setAckState((BAckState) BAckState.acked.decode(in));
      setAckRequired(in.readBoolean());
      setSource((BOrdList) BOrdList.DEFAULT.decode(in));
      setAlarmClass(in.readUTF());
      setPriority(in.readInt());
      setNormalTime(decodeAbsTime(in, context));
      setAckTime(decodeAbsTime(in, context));
      setUser(in.readUTF());
      setAlarmData((BFacets) BFacets.DEFAULT.decode(in));
      setAlarmTransition((BSourceState) BSourceState.normal.decode(in));
      setLastUpdate(decodeAbsTime(in, context));
    }
    finally
    {
      // Give any registered decorators a chance to decorate the alarm after reading from serialized form
      BIAlarmRecordDecorator.decorateAllOnReadEnd(this, context);
    }
  }

  private void encodeAbsTime(DataOutput out, Context context, BAbsTime absTime)
    throws IOException
  {
    if (context == DATA_RECOVERY_CX)
    {
      //Use optimized BAbsTime encoding when writing to the Data Recovery Service
      absTime.encode48(out);
    }
    else
    {
      absTime.encode(out);
    }
  }

  private BAbsTime decodeAbsTime(DataInput in, Context context)
    throws IOException
  {
    BAbsTime absTime = null;
    if (context == DATA_RECOVERY_CX)
    {
      //Use optimized BAbsTime decoding when reading from the Data Recovery Service
      absTime = (BAbsTime)BAbsTime.DEFAULT.decode48(in);
    }
    else
    {
      // AbsTimes are always encoded in the alarm DB with the full 64-bit long. An AbsTime before
      // the Unix epoch will have a negative number of millis. If one of these is written to the
      // alarm DB, it will appear to be a 48-bit encoded value when the record is decoded.
      // Therefore, all AbsTimes for alarm DB records are decoded as full 64-bit longs.
      absTime = (BAbsTime)BAbsTime.DEFAULT.decode64(in);
    }
    return absTime;
  }

  public String toSummaryString()
  {
    StringBuilder s = new StringBuilder(128);
    s.append(getTimestamp().toString(TIMESTAMP_FACETS));
    s.append(": ");
    s.append('(').append(getAckState()).append(':').append(getSourceState()).append(") ");
    s.append(getUuid());

    return s.toString();
  }

  /**
   * Returns an array of common user defined alarm data keys for an alarm record.  
   *
   * Note that this array contains only the public static final String fields in BAlarmRecord
   *
   * @return String array containing user defined fields for an alarm record
   * @since Niagara 4.4
   */
  public static String[] getAlarmDataFields()
  {
    return new String[]{
      ALARM_VALUE      ,
      CONTROLLED_VALUE ,
      COUNT            ,
      DEADBAND         ,
      ERROR_LIMIT      ,
      FAULT_VALUE      ,
      FEEDBACK_NUMERIC ,
      FEEDBACK_VALUE   ,
      FROM_STATE       ,
      HIGH_LIMIT       ,
      HYPERLINK_ORD    ,
      ICON             ,
      INSTRUCTIONS     ,
      LOW_LIMIT        ,
      MSG_TEXT         ,
      NEW_VALUE        ,
      NOTES            ,
      NOTIFY_TYPE      ,
      NUMERIC_VALUE    ,
      OFFNORMAL_VALUE  ,
      PRESENT_VALUE    ,
      SETPT_VALUE      ,
      SOUND_FILE       ,
      SOURCE_NAME      ,
      STATUS           ,
      TIME_ZONE        ,
      TO_STATE         ,
      SETPT_NUMERIC    ,
      HIGH_DIFF_LIMIT  ,
      LOW_DIFF_LIMIT
    };
    
  }

  /**
   * Return true if the alarm data field can be processed as
   * a {@link javax.baja.util.BFormat}.
   *
   * @see #getAlarmDataFields()
   * @since Niagara 4.6
   *
   * @param field The field name.
   * @return true if the field should be processed as a format.
   */
  public final boolean isAlarmDataFieldFormat(String field)
  {
    return MSG_TEXT.equals(field) || INSTRUCTIONS.equals(field);
  }

  /**
   * Returns a formatted string from the alarm data facets for the specified key.
   * <p>
   * An empty string is returned if the key can't be found in the alarm data.
   * </p>
   *
   * @see #getAlarmDataFields()
   * @see #isAlarmDataFieldFormat(String)
   * @since Niagara 4.6
   *
   * @param field The alarm data field name.
   * @param cx The calling context. Can be null.
   * @return A string that may have been formatted or an empty string.
   */
  public final String getFormattedAlarmDataValue(String field, Context cx)
  {
    String value = Objects.toString(getAlarmData().getFacet(field), "");
    if (value.isEmpty())
    {
      return value;
    }

    return isAlarmDataFieldFormat(field) ? BFormat.make(value).format(this, cx)
      : value;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // Allow the read/write methods to recognize when they are being asked
  // to write/read data to the Data Recovery Store. This is intended to
  // enable encoding / decoding optimizations that might not otherwise be
  // possible when serializing the data with a different sink / source.
  public static final Context DATA_RECOVERY_CX = new BasicContext()
  {
    @Override
    public boolean equals(Object obj) { return this == obj; }
    @Override
    public int hashCode() { return toString().hashCode(); }
    @Override
    public String toString() { return "Context.dataRecovery"; }
  };

  /**
   * This context is used when a BAlarmRecord is doing a {@link #read(DataInput, Context)}
   * or {@link #write(DataOutput, Context)} from/to an alarm store for persistence, such as a
   * {@link BAlarmDatabase}.
   *
   * @since Niagara 4.13
   */
  public static final Context ALARM_STORE_CX = new BasicContext()
  {
    @Override
    public boolean equals(Object obj) { return this == obj; }
    @Override
    public int hashCode() { return toString().hashCode(); }
    @Override
    public String toString() { return "Context.alarmStore"; }
  };

  private static final int SERIAL_VERSION_ID = 0;

  public static final BFacets TIMESTAMP_FACETS =
    BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE, BFacets.SHOW_MILLISECONDS, BBoolean.TRUE);

  /** User defined message text key */
  public static final String MSG_TEXT         = "msgText";
  public static final String FROM_STATE       = "fromState";
  public static final String TO_STATE         = "toState";
  public static final String NOTIFY_TYPE      = "notifyType";
  public static final String STATUS           = "status";
  public static final String NEW_VALUE        = "newValue";
  public static final String SETPT_VALUE      = "setptValue";
  public static final String SETPT_NUMERIC    = "setpointNumeric";
  public static final String ERROR_LIMIT      = "errorLimit";
  public static final String DEADBAND         = "deadband";
  public static final String COUNT            = "Count";
  public static final String HIGH_LIMIT       = "highLimit";
  public static final String LOW_LIMIT        = "lowLimit";
  public static final String HIGH_DIFF_LIMIT  = "highDiffLimit";
  public static final String LOW_DIFF_LIMIT   = "lowDiffLimit";
  public static final String ALARM_VALUE      = "alarmValue";
  public static final String OFFNORMAL_VALUE  = "offnormalValue";
  public static final String FAULT_VALUE      = "faultValue";
  public static final String PRESENT_VALUE    = "presentValue";
  public static final String NUMERIC_VALUE    = "numericValue";
  public static final String FEEDBACK_VALUE   = "feedbackValue";
  public static final String FEEDBACK_NUMERIC = "feedbackNumeric";
  public static final String CONTROLLED_VALUE = "controlledValue";
  public static final String HYPERLINK_ORD    = "hyperlinkOrd";
  public static final String SOUND_FILE       = "soundFile";
  public static final String ICON             = "icon";
  public static final String SOURCE_NAME      = "sourceName";
  public static final String NOTES            = "notes";
  public static final String INSTRUCTIONS     = "instructions";
  public static final String TIME_ZONE        = "TimeZone";
  public static final String TIME_DELAY       = "timeDelay";
  public static final String TIME_DELAY_TO_NORMAL = "timeDelayToNormal";
}
