/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import java.io.IOException;
import java.util.logging.Level;

import javax.baja.alarm.BAckState;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BSourceState;
import javax.baja.data.BIDataValue;
import javax.baja.data.DataUtil;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.query.BOrdering;
import javax.baja.query.util.Columns;
import javax.baja.query.util.Exprs;
import javax.baja.query.util.Predicates;
import javax.baja.rdb.ddl.BOnDelete;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BDate;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BUuid;

import com.tridium.orion.BOrionObject;
import com.tridium.orion.BRef;
import com.tridium.orion.OrionCursor;
import com.tridium.orion.OrionSession;
import com.tridium.orion.OrionType;
import com.tridium.orion.annotations.NiagaraOrionType;
import com.tridium.orion.annotations.OrionProperty;
import com.tridium.orion.sql.BSqlExtent;
import com.tridium.orion.sql.BSqlField;
import com.tridium.orion.sql.BSqlJoin;
import com.tridium.orion.sql.BSqlQuery;
import com.tridium.orion.sql.PropertyValue;

/**
 * The representation of an alarm record within the orion database.
 *
 * @author Lee Adcock
 * @creation March 18, 2009
 */
@NiagaraType
@NiagaraOrionType
@NiagaraProperty(
  name = "id",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY | Flags.SUMMARY,
  facets = {
    @Facet("ID_KEY"),
    @Facet(name = "DESCENDING", value = "true")
  }
)
@NiagaraProperty(
  name = "timestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "datestamp",
  type = "BDate",
  defaultValue = "BDate.NULL",
  flags = Flags.SUMMARY,
  facets = @Facet(name = "INDEXED", value = "true")
)
/*
 The hash of the universal identifier of the alarm.
 */
@NiagaraProperty(
  name = "uuidHash",
  type = "int",
  defaultValue = "0",
  facets = @Facet(name = "INDEXED", value = "true")
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
 Is the alarm open.
 */
@NiagaraProperty(
  name = "isOpen",
  type = "boolean",
  defaultValue = "false"
)
/*
 The current state of the alarm source.
 */
@NiagaraProperty(
  name = "sourceState",
  type = "BSourceState",
  defaultValue = "BSourceState.offnormal",
  facets = @Facet(name = "INDEXED", value = "true")
)
/*
 The current acknowledged state of the alarm.
 */
@NiagaraProperty(
  name = "ackState",
  type = "BAckState",
  defaultValue = "BAckState.unacked",
  facets = @Facet(name = "INDEXED", value = "true")
)
/*
 If alarm is required to be routed back to its source.
 */
@NiagaraProperty(
  name = "ackRequired",
  type = "boolean",
  defaultValue = "true"
)
@OrionProperty(
  name = "alarmClass",
  refType = "alarmOrion:OrionAlarmClass",
  flags = Flags.SUMMARY,
  facets = {
    @Facet(name = "ON_DELETE", value = "BOnDelete.CASCADE"),
    @Facet(name = "AUTO_RESOLVE", value = "true")
  }
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
  defaultValue = "BAbsTime.NULL"
)
/*
 The time at which the alarm is acked.  Note:
 that interpretation of this property's value depends upon
 the state of the alarm.
 */
@NiagaraProperty(
  name = "ackTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL"
)
/*
 The name of the user who acknowledged the alarm.
 */
@NiagaraProperty(
  name = "userAccount",
  type = "String",
  defaultValue = "Unknown User",
  facets = @Facet(name = "WIDTH", value = "32")
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
 The time at which the alarm was last updated. Updates occour
 at creation, acknowlegement, and changes to alarmData such as notes.
 */
@NiagaraProperty(
  name = "lastUpdate",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL"
)
public class BOrionAlarmRecord
  extends BOrionObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmRecord(3370765182)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(Flags.READONLY | Flags.SUMMARY, -1, BFacets.make(ID_KEY, BFacets.make(DESCENDING, true)));

  /**
   * Get the {@code id} property.
   * @see #id
   */
  public int getId() { return getInt(id); }

  /**
   * Set the {@code id} property.
   * @see #id
   */
  public void setId(int v) { setInt(id, v, null); }

  //endregion Property "id"

  //region Property "timestamp"

  /**
   * Slot for the {@code timestamp} property.
   * @see #getTimestamp
   * @see #setTimestamp
   */
  public static final Property timestamp = newProperty(Flags.SUMMARY, BAbsTime.NULL, null);

  /**
   * Get the {@code timestamp} property.
   * @see #timestamp
   */
  public BAbsTime getTimestamp() { return (BAbsTime)get(timestamp); }

  /**
   * Set the {@code timestamp} property.
   * @see #timestamp
   */
  public void setTimestamp(BAbsTime v) { set(timestamp, v, null); }

  //endregion Property "timestamp"

  //region Property "datestamp"

  /**
   * Slot for the {@code datestamp} property.
   * @see #getDatestamp
   * @see #setDatestamp
   */
  public static final Property datestamp = newProperty(Flags.SUMMARY, BDate.NULL, BFacets.make(INDEXED, true));

  /**
   * Get the {@code datestamp} property.
   * @see #datestamp
   */
  public BDate getDatestamp() { return (BDate)get(datestamp); }

  /**
   * Set the {@code datestamp} property.
   * @see #datestamp
   */
  public void setDatestamp(BDate v) { set(datestamp, v, null); }

  //endregion Property "datestamp"

  //region Property "uuidHash"

  /**
   * Slot for the {@code uuidHash} property.
   * The hash of the universal identifier of the alarm.
   * @see #getUuidHash
   * @see #setUuidHash
   */
  public static final Property uuidHash = newProperty(0, 0, BFacets.make(INDEXED, true));

  /**
   * Get the {@code uuidHash} property.
   * The hash of the universal identifier of the alarm.
   * @see #uuidHash
   */
  public int getUuidHash() { return getInt(uuidHash); }

  /**
   * Set the {@code uuidHash} property.
   * The hash of the universal identifier of the alarm.
   * @see #uuidHash
   */
  public void setUuidHash(int v) { setInt(uuidHash, v, null); }

  //endregion Property "uuidHash"

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

  //region Property "isOpen"

  /**
   * Slot for the {@code isOpen} property.
   * Is the alarm open.
   * @see #getIsOpen
   * @see #setIsOpen
   */
  public static final Property isOpen = newProperty(0, false, null);

  /**
   * Get the {@code isOpen} property.
   * Is the alarm open.
   * @see #isOpen
   */
  public boolean getIsOpen() { return getBoolean(isOpen); }

  /**
   * Set the {@code isOpen} property.
   * Is the alarm open.
   * @see #isOpen
   */
  public void setIsOpen(boolean v) { setBoolean(isOpen, v, null); }

  //endregion Property "isOpen"

  //region Property "sourceState"

  /**
   * Slot for the {@code sourceState} property.
   * The current state of the alarm source.
   * @see #getSourceState
   * @see #setSourceState
   */
  public static final Property sourceState = newProperty(0, BSourceState.offnormal, BFacets.make(INDEXED, true));

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
  public static final Property ackState = newProperty(0, BAckState.unacked, BFacets.make(INDEXED, true));

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

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(Flags.SUMMARY, BRef.make("alarmOrion:OrionAlarmClass"), BFacets.make(BFacets.make(ON_DELETE, BOnDelete.CASCADE), BFacets.make(AUTO_RESOLVE, true)));

  /**
   * Get the {@code alarmClass} property.
   * @see #alarmClass
   */
  public BRef getAlarmClass() { return (BRef)get(alarmClass); }

  /**
   * Set the {@code alarmClass} property.
   * @see #alarmClass
   */
  public void setAlarmClass(BRef v) { set(alarmClass, v, null); }

  //endregion Property "alarmClass"

  /**
   * Resolve the {@code alarmClass} property.
   * @see #alarmClass
   */
  public BOrionAlarmClass resolveAlarmClass(OrionSession session)
  {
    return (BOrionAlarmClass)getAlarmClass().getTarget(session);
  }

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
  public static final Property normalTime = newProperty(0, BAbsTime.NULL, null);

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
   * The time at which the alarm is acked.  Note:
   * that interpretation of this property's value depends upon
   * the state of the alarm.
   * @see #getAckTime
   * @see #setAckTime
   */
  public static final Property ackTime = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code ackTime} property.
   * The time at which the alarm is acked.  Note:
   * that interpretation of this property's value depends upon
   * the state of the alarm.
   * @see #ackTime
   */
  public BAbsTime getAckTime() { return (BAbsTime)get(ackTime); }

  /**
   * Set the {@code ackTime} property.
   * The time at which the alarm is acked.  Note:
   * that interpretation of this property's value depends upon
   * the state of the alarm.
   * @see #ackTime
   */
  public void setAckTime(BAbsTime v) { set(ackTime, v, null); }

  //endregion Property "ackTime"

  //region Property "userAccount"

  /**
   * Slot for the {@code userAccount} property.
   * The name of the user who acknowledged the alarm.
   * @see #getUserAccount
   * @see #setUserAccount
   */
  public static final Property userAccount = newProperty(0, "Unknown User", BFacets.make(WIDTH, 32));

  /**
   * Get the {@code userAccount} property.
   * The name of the user who acknowledged the alarm.
   * @see #userAccount
   */
  public String getUserAccount() { return getString(userAccount); }

  /**
   * Set the {@code userAccount} property.
   * The name of the user who acknowledged the alarm.
   * @see #userAccount
   */
  public void setUserAccount(String v) { setString(userAccount, v, null); }

  //endregion Property "userAccount"

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
   * The time at which the alarm was last updated. Updates occour
   * at creation, acknowlegement, and changes to alarmData such as notes.
   * @see #getLastUpdate
   * @see #setLastUpdate
   */
  public static final Property lastUpdate = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code lastUpdate} property.
   * The time at which the alarm was last updated. Updates occour
   * at creation, acknowlegement, and changes to alarmData such as notes.
   * @see #lastUpdate
   */
  public BAbsTime getLastUpdate() { return (BAbsTime)get(lastUpdate); }

  /**
   * Set the {@code lastUpdate} property.
   * The time at which the alarm was last updated. Updates occour
   * at creation, acknowlegement, and changes to alarmData such as notes.
   * @see #lastUpdate
   */
  public void setLastUpdate(BAbsTime v) { set(lastUpdate, v, null); }

  //endregion Property "lastUpdate"

  //region Type

  @Override
  public Type getType() { return getTypeFromSpace(TYPE); }
  public static final Type TYPE = Sys.loadType(BOrionAlarmRecord.class);

  //endregion Type
  public static final OrionType ORION_TYPE = (OrionType)TYPE;

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Create an BOrionAlarm object representation of a BAlarmRecord.  This does not
   * store the object within the database, or set the alarm source or alarm data values.
   */
  public static BOrionAlarmRecord make(BAlarmRecord alarmRecord, OrionSession session)
  {
    BOrionAlarmClass alarmClass = BOrionAlarmClass.get(alarmRecord.getAlarmClass(), session);

    BOrionAlarmRecord alarm = new BOrionAlarmRecord();
    alarm.setTimestamp(alarmRecord.getTimestamp());
    alarm.setUuid(alarmRecord.getUuid());
    alarm.setSourceState(alarmRecord.getSourceState());
    alarm.setAckState(alarmRecord.getAckState());
    alarm.setAckRequired(alarmRecord.getAckRequired());
    alarm.setPriority(alarmRecord.getPriority());
    alarm.setNormalTime(alarmRecord.getNormalTime());
    alarm.setAckTime(alarmRecord.getAckTime());
    alarm.setUserAccount(alarmRecord.getUser());
    alarm.setAlarmTransition(alarmRecord.getAlarmTransition());
    alarm.setLastUpdate(alarmRecord.getLastUpdate());
    alarm.setAlarmClass(BRef.make(alarmClass));
    return alarm;
  }

  /**
   * Get an alarm record from the database based on a unique uuid.
   */
  public static BOrionAlarmRecord get(BUuid uuid, OrionSession session)
  {
    return (BOrionAlarmRecord)session.read(ORION_TYPE, new PropertyValue(BOrionAlarmRecord.uuid, uuid));
  }

  /**
   * Get the BAlarmRecord object that was represented in the
   * database by this BOrionAlarm object.
   */
  public BAlarmRecord getAlarmRecord(OrionSession session)
  {
    BAlarmRecord alarmRecord = new BAlarmRecord(BUuid.DEFAULT);

    alarmRecord.setTimestamp(getTimestamp());
    alarmRecord.setUuid(getUuid());
    alarmRecord.setSourceState(getSourceState());
    alarmRecord.setAckState(getAckState());
    alarmRecord.setAckRequired(getAckRequired());
    alarmRecord.setAlarmClass(getAlarmClass(session));
    alarmRecord.setPriority(getPriority());
    alarmRecord.setNormalTime(getNormalTime());
    alarmRecord.setAckTime(getAckTime());
    alarmRecord.setUser(getUserAccount());
    alarmRecord.setAlarmTransition(getAlarmTransition());
    alarmRecord.setLastUpdate(getLastUpdate());
    alarmRecord.setSource(getSource(session));
    alarmRecord.setAlarmData(getAlarmData(session));
    return alarmRecord;
  }

  /**
   * Get the alarm's class
   */
  public String getAlarmClass(OrionSession session)
  {
    return resolveAlarmClass(session).getAlarmClass();
  }

  /**
   * Get the alarm's source
   */
  public BOrdList getSource(OrionSession session)
  {
    BSqlExtent alarmSourceExt = new BSqlExtent(BOrionAlarmSource.ORION_TYPE);
    BSqlExtent alarmSourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE);

    // Query for all sources for this alarm sorted by the correct ordering
    BSqlQuery query = BSqlQuery.make(BOrionAlarmSourceOrder.ORION_TYPE);
    query.join(new BSqlJoin(
      new BSqlField(alarmSourceOrderExt, BOrionAlarmSourceOrder.alarmSource),
      new BSqlField(alarmSourceExt, BOrionAlarmSource.id)));
    query.where(Exprs.builder(Predicates.eq(BOrionAlarmSourceOrder.alarm, BRef.make(this))));
    query.orderBy(BOrdering.make(Columns.orderBy(Exprs.field(BOrionAlarmSourceOrder.sourceOrder)).asc()));

    OrionCursor sourceCursor = session.select(query);

    try
    {
      // Populate a BOrdList with the query's results.
      BOrdList ordList = BOrdList.make(new BOrd[] {});
      while(sourceCursor.next())
      {
        BOrionAlarmSourceOrder alarmSource = ((BOrionAlarmSourceOrder)sourceCursor.get());
        ordList = BOrdList.add(ordList, alarmSource.resolveAlarmSource(session).getSource());
      }
      return ordList;
    } catch (Exception e) {
      throw new BajaRuntimeException("Unable to load alarm source ("+this.getUuid()+").", e);
    } finally {
      if(sourceCursor!=null)
        sourceCursor.close();
    }
  }

  /**
   * Get the alarm's facet data
   */
  public BFacets getAlarmData(OrionSession session)
  {
    BSqlQuery query = BSqlQuery.make(BOrionAlarmFacetValue.ORION_TYPE);
    query.where(Exprs.builder(Predicates.eq(BOrionAlarmFacetValue.alarm, BRef.make(this))));

    OrionCursor facetCursor = session.select(query);
    try
    {
      Array<String> facetKeys = new Array<>(String.class);
      Array<BIDataValue> facetData = new Array<>(BIDataValue.class);
      while(facetCursor.next())
      {
        try
        {
          BOrionAlarmFacetValue data = (BOrionAlarmFacetValue)facetCursor.get();
          String key = data.resolveFacetName(session).getFacetName();
          BIDataValue dataValue = (BIDataValue)DataUtil.unmarshal(data.getValue());
          
          facetKeys.add(key);
          facetData.add(dataValue);          
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
      
      BFacets facets = BFacets.make(facetKeys.trim(), facetData.trim());
      return facets;
    } catch (Exception e) {
      BOrionAlarmDatabase.log.log(Level.SEVERE, "Unable to retrieve alarm facets from database.", e);
      return BFacets.NULL;
    } finally {
      facetCursor.close();
    }
  }

  /**
   * Retrieve the value of a specific alarm facet. This is most efficient if you need only
   * a single facet value, but if you plan on accessing multiple facets it is best to use
   * the getAlarmData method to retrieve them all instead.
   */
  public BIDataValue getAlarmData(String facetName, OrionSession session)
  {
    BSqlQuery query = BSqlQuery.make(BOrionAlarmFacetValue.ORION_TYPE);

    // Setup table aliases
    BSqlExtent facetNameExt = new BSqlExtent(BOrionAlarmFacetName.ORION_TYPE).alias("facetName");
    BSqlExtent facetValueExt = new BSqlExtent(BOrionAlarmFacetValue.ORION_TYPE).alias("facetValue");

    // Join BOrionAlarmFacetName and BOrionAlarmFacetValue
    query.join(new BSqlJoin(
        new BSqlField(facetNameExt, BOrionAlarmFacetName.id),
        new BSqlField(facetValueExt, BOrionAlarmFacetValue.facetName)
        ));

    query.where(
        Exprs.builder(Predicates.eq(BOrionAlarmFacetName.facetName, BString.make(facetName))).and(
            Predicates.eq(BOrionAlarmFacetValue.alarm, BInteger.make(getId())))
        );

    OrionCursor cursor = session.select(query);
    if(cursor.next())
    {
      try
      {
        BOrionAlarmFacetValue data = (BOrionAlarmFacetValue)cursor.get();
        return (BIDataValue)DataUtil.unmarshal(data.getValue());
      } catch (IOException ioe) {
        throw new BajaRuntimeException("Unable to unmarshal data value");
      } finally {
        cursor.close();
      }
    } else
      return null;
  }

////////////////////////////////////////////////////////////////
//Alarm State
////////////////////////////////////////////////////////////////

  /**
   * Mirrors the behavior of BAlarmRecord.isAlarm().
   * @return true if the record type is alarm.
   */
  public boolean isAlarm()
  {
    return getSourceState() != BSourceState.normal && getAckState() != BAckState.acked;
  }

  /**
   * Mirrors the behavior of BAlarmRecord.isAcknowledged().
   * @return true if the ackState is acked.
   */
  public boolean isAcknowledged()
  {
    return getAckState() == BAckState.acked;
  }

  /**
   * Mirrors the behavior of BAlarmRecord.isAckPending().
   * @return true if the ackState is ackPending.
   */
  public boolean isAckPending()
  {
    return getAckState() == BAckState.ackPending;
  }

  /**
   * Mirrors the behavior of BAlarmRecord.isNormal().
   * @return true if the sourceState is normal.
   */
  public boolean isNormal()
  {
    return getSourceState() == BSourceState.normal;
  }

  /**
   * Mirrors the behavior of BAlarmRecord.isOpen().
   * @return true if the record is open
   */
  public boolean isOpen()
  {
    //alerts are always open until acked - regarless of the ackRequired flag
    if ((getSourceState() == BSourceState.alert) && (getAckState() != BAckState.acked))
      return true;

    return ((getAckState() != BAckState.acked && getAckRequired() == true) ||
        (getSourceState() != BSourceState.normal && getSourceState() != BSourceState.alert));
  }

  @Override
  public void changed(Property p, Context cx)
  {
    if(p.equals(BOrionAlarmRecord.timestamp))
      setDatestamp(BDate.make(getTimestamp()));

    if(p.equals(BOrionAlarmRecord.uuid))
      setUuidHash(getUuid().hashCode());

    super.changed(p, cx);
  }

  @Override
  public boolean beforeInsert(OrionSession session)
  {
    this.setIsOpen(this.isOpen());
    this.setUuidHash(getUuid().hashCode());
    return super.beforeInsert(session);
  }

  @Override
  public boolean beforeUpdate(OrionSession session)
  {
    this.setIsOpen(this.isOpen());
    this.setUuidHash(getUuid().hashCode());
    return super.beforeUpdate(session);
  }
}
