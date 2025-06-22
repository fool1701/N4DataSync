/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistorySummary is a struct that contains the summary info
 * for a history.
 *
 * @author    John Sublett
 * @creation  03 Apr 2003
 * @version   $Revision: 1$ $Date: 10/13/03 11:15:59 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The id of the history with this state.
 */
@NiagaraProperty(
  name = "id",
  type = "BHistoryId",
  defaultValue = "BHistoryId.NULL"
)
/*
 The number of records in the history.
 */
@NiagaraProperty(
  name = "recordCount",
  type = "int",
  defaultValue = "0"
)
/*
 The timestamp of the first record in the history.
 */
@NiagaraProperty(
  name = "firstTimestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL"
)
/*
 The timestamp of the last record in the history.
 */
@NiagaraProperty(
  name = "lastTimestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL"
)
public class BHistorySummary
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistorySummary(3143067501)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * The id of the history with this state.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(0, BHistoryId.NULL, null);

  /**
   * Get the {@code id} property.
   * The id of the history with this state.
   * @see #id
   */
  public BHistoryId getId() { return (BHistoryId)get(id); }

  /**
   * Set the {@code id} property.
   * The id of the history with this state.
   * @see #id
   */
  public void setId(BHistoryId v) { set(id, v, null); }

  //endregion Property "id"

  //region Property "recordCount"

  /**
   * Slot for the {@code recordCount} property.
   * The number of records in the history.
   * @see #getRecordCount
   * @see #setRecordCount
   */
  public static final Property recordCount = newProperty(0, 0, null);

  /**
   * Get the {@code recordCount} property.
   * The number of records in the history.
   * @see #recordCount
   */
  public int getRecordCount() { return getInt(recordCount); }

  /**
   * Set the {@code recordCount} property.
   * The number of records in the history.
   * @see #recordCount
   */
  public void setRecordCount(int v) { setInt(recordCount, v, null); }

  //endregion Property "recordCount"

  //region Property "firstTimestamp"

  /**
   * Slot for the {@code firstTimestamp} property.
   * The timestamp of the first record in the history.
   * @see #getFirstTimestamp
   * @see #setFirstTimestamp
   */
  public static final Property firstTimestamp = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code firstTimestamp} property.
   * The timestamp of the first record in the history.
   * @see #firstTimestamp
   */
  public BAbsTime getFirstTimestamp() { return (BAbsTime)get(firstTimestamp); }

  /**
   * Set the {@code firstTimestamp} property.
   * The timestamp of the first record in the history.
   * @see #firstTimestamp
   */
  public void setFirstTimestamp(BAbsTime v) { set(firstTimestamp, v, null); }

  //endregion Property "firstTimestamp"

  //region Property "lastTimestamp"

  /**
   * Slot for the {@code lastTimestamp} property.
   * The timestamp of the last record in the history.
   * @see #getLastTimestamp
   * @see #setLastTimestamp
   */
  public static final Property lastTimestamp = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code lastTimestamp} property.
   * The timestamp of the last record in the history.
   * @see #lastTimestamp
   */
  public BAbsTime getLastTimestamp() { return (BAbsTime)get(lastTimestamp); }

  /**
   * Set the {@code lastTimestamp} property.
   * The timestamp of the last record in the history.
   * @see #lastTimestamp
   */
  public void setLastTimestamp(BAbsTime v) { set(lastTimestamp, v, null); }

  //endregion Property "lastTimestamp"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistorySummary.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
