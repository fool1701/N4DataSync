/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;


/**
 * BAlarmTimestamps 
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 9$ $Date: 4/4/11 4:11:40 PM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "alarmTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "ackTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "normalTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "count",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
public class BAlarmTimestamps
  extends BStruct
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BAlarmTimestamps(3111030466)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmTime"

  /**
   * Slot for the {@code alarmTime} property.
   * @see #getAlarmTime
   * @see #setAlarmTime
   */
  public static final Property alarmTime = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAbsTime.NULL, null);

  /**
   * Get the {@code alarmTime} property.
   * @see #alarmTime
   */
  public BAbsTime getAlarmTime() { return (BAbsTime)get(alarmTime); }

  /**
   * Set the {@code alarmTime} property.
   * @see #alarmTime
   */
  public void setAlarmTime(BAbsTime v) { set(alarmTime, v, null); }

  //endregion Property "alarmTime"

  //region Property "ackTime"

  /**
   * Slot for the {@code ackTime} property.
   * @see #getAckTime
   * @see #setAckTime
   */
  public static final Property ackTime = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAbsTime.NULL, null);

  /**
   * Get the {@code ackTime} property.
   * @see #ackTime
   */
  public BAbsTime getAckTime() { return (BAbsTime)get(ackTime); }

  /**
   * Set the {@code ackTime} property.
   * @see #ackTime
   */
  public void setAckTime(BAbsTime v) { set(ackTime, v, null); }

  //endregion Property "ackTime"

  //region Property "normalTime"

  /**
   * Slot for the {@code normalTime} property.
   * @see #getNormalTime
   * @see #setNormalTime
   */
  public static final Property normalTime = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAbsTime.NULL, null);

  /**
   * Get the {@code normalTime} property.
   * @see #normalTime
   */
  public BAbsTime getNormalTime() { return (BAbsTime)get(normalTime); }

  /**
   * Set the {@code normalTime} property.
   * @see #normalTime
   */
  public void setNormalTime(BAbsTime v) { set(normalTime, v, null); }

  //endregion Property "normalTime"

  //region Property "count"

  /**
   * Slot for the {@code count} property.
   * @see #getCount
   * @see #setCount
   */
  public static final Property count = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, 0, null);

  /**
   * Get the {@code count} property.
   * @see #count
   */
  public int getCount() { return getInt(count); }

  /**
   * Set the {@code count} property.
   * @see #count
   */
  public void setCount(int v) { setInt(count, v, null); }

  //endregion Property "count"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmTimestamps.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
