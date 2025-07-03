/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Created by E491819 on 7/10/2017.
 */
@NiagaraType
@NiagaraProperty(
  name = "startTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT"
)
@NiagaraProperty(
  name = "endTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT"
)
@NiagaraProperty(
  name = "format",
  type = "String",
  defaultValue = "D MMM YY HH:mm"
)
@NiagaraProperty(
  name = "current",
  type = "long",
  defaultValue = "0"
)
public class BAbsStartEndTime extends BStruct {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.bajax.analytics.data.BAbsStartEndTime(3564925485)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "startTime"

  /**
   * Slot for the {@code startTime} property.
   * @see #getStartTime
   * @see #setStartTime
   */
  public static final Property startTime = newProperty(0, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code startTime} property.
   * @see #startTime
   */
  public BAbsTime getStartTime() { return (BAbsTime)get(startTime); }

  /**
   * Set the {@code startTime} property.
   * @see #startTime
   */
  public void setStartTime(BAbsTime v) { set(startTime, v, null); }

  //endregion Property "startTime"

  //region Property "endTime"

  /**
   * Slot for the {@code endTime} property.
   * @see #getEndTime
   * @see #setEndTime
   */
  public static final Property endTime = newProperty(0, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code endTime} property.
   * @see #endTime
   */
  public BAbsTime getEndTime() { return (BAbsTime)get(endTime); }

  /**
   * Set the {@code endTime} property.
   * @see #endTime
   */
  public void setEndTime(BAbsTime v) { set(endTime, v, null); }

  //endregion Property "endTime"

  //region Property "format"

  /**
   * Slot for the {@code format} property.
   * @see #getFormat
   * @see #setFormat
   */
  public static final Property format = newProperty(0, "D MMM YY HH:mm", null);

  /**
   * Get the {@code format} property.
   * @see #format
   */
  public String getFormat() { return getString(format); }

  /**
   * Set the {@code format} property.
   * @see #format
   */
  public void setFormat(String v) { setString(format, v, null); }

  //endregion Property "format"

  //region Property "current"

  /**
   * Slot for the {@code current} property.
   * @see #getCurrent
   * @see #setCurrent
   */
  public static final Property current = newProperty(0, 0, null);

  /**
   * Get the {@code current} property.
   * @see #current
   */
  public long getCurrent() { return getLong(current); }

  /**
   * Set the {@code current} property.
   * @see #current
   */
  public void setCurrent(long v) { setLong(current, v, null); }

  //endregion Property "current"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbsStartEndTime.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public static final String DEFAULT_TIME_FORMAT = "D MMM YY HH:mm";
}
