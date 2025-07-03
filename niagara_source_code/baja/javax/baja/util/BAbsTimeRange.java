/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BAbsTimeRange is a range of absolute times defining a period of
 * time.  The bounds of the range are inclusive.
 *
 * @author    John Sublett
 * @creation  16 Feb 2001
 * @version   $Revision: 6$ $Date: 2/6/03 11:21:41 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The start time of the range, inclusive.
 */
@NiagaraProperty(
  name = "startTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.make(0)"
)
/*
 The end time of the range, inclusive.
 */
@NiagaraProperty(
  name = "endTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.END_OF_TIME"
)
public class BAbsTimeRange
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BAbsTimeRange(3931745603)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "startTime"

  /**
   * Slot for the {@code startTime} property.
   * The start time of the range, inclusive.
   * @see #getStartTime
   * @see #setStartTime
   */
  public static final Property startTime = newProperty(0, BAbsTime.make(0), null);

  /**
   * Get the {@code startTime} property.
   * The start time of the range, inclusive.
   * @see #startTime
   */
  public BAbsTime getStartTime() { return (BAbsTime)get(startTime); }

  /**
   * Set the {@code startTime} property.
   * The start time of the range, inclusive.
   * @see #startTime
   */
  public void setStartTime(BAbsTime v) { set(startTime, v, null); }

  //endregion Property "startTime"

  //region Property "endTime"

  /**
   * Slot for the {@code endTime} property.
   * The end time of the range, inclusive.
   * @see #getEndTime
   * @see #setEndTime
   */
  public static final Property endTime = newProperty(0, BAbsTime.END_OF_TIME, null);

  /**
   * Get the {@code endTime} property.
   * The end time of the range, inclusive.
   * @see #endTime
   */
  public BAbsTime getEndTime() { return (BAbsTime)get(endTime); }

  /**
   * Set the {@code endTime} property.
   * The end time of the range, inclusive.
   * @see #endTime
   */
  public void setEndTime(BAbsTime v) { set(endTime, v, null); }

  //endregion Property "endTime"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbsTimeRange.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor.
   */
  public BAbsTimeRange()
  {
  }

  /**
   * Constructor with initial time range.
   *
   * @param startTime The beginning of the range.  The range
   *   includes the startTime.  If null, then startTime is
   *   set to the earliest possible time that can be represented
   *   by BAbsTime.
   *
   * @param endTime the end of the range.  The range includes
   *   the endTime.  If null, then endTime is set to the
   *   latest possible time that can be represented by BAbsTime.
   */
  public BAbsTimeRange(BAbsTime startTime, BAbsTime endTime)
  {
    if (startTime != null) setStartTime(startTime);
    if (endTime != null) setEndTime(endTime);
  }

  /**
   * Tests whether or not the specified time is within this range.
   * The range is inclusive of the endpoints.
   *
   * @param time A time to test for containment in the range.
   *
   * @return Returns true if the range contains the time, false otherwise.
   */
  public boolean includes(BAbsTime time)
  {
    return !time.isBefore(getStartTime()) && !time.isAfter(getEndTime());
  }
  
  /**
   * Get a string representation of the time range.
   */  
  @Override
  public String toString(Context ctx)
  {
    return getStartTime().toString(ctx) + " - " + getEndTime().toString(ctx);
  }
} 
