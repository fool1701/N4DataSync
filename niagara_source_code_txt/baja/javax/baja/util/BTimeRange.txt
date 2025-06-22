/* 
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * A time range within any given day, with inclusive endpoints.
 * @author Aaron Hansen
 * @creation April 2001
 * @version $Revision: 9$ $Date: 8/17/05 9:46:04 AM EDT$
 */
@NiagaraType
/*
 Inclusive
 */
@NiagaraProperty(
  name = "startTime",
  type = "BTime",
  defaultValue = "BTime.make(0,0,0,0)"
)
/*
 Inclusive
 */
@NiagaraProperty(
  name = "endTime",
  type = "BTime",
  defaultValue = "BTime.make(0,0,0,0)"
)
public class BTimeRange 
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BTimeRange(1573113112)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "startTime"

  /**
   * Slot for the {@code startTime} property.
   * Inclusive
   * @see #getStartTime
   * @see #setStartTime
   */
  public static final Property startTime = newProperty(0, BTime.make(0,0,0,0), null);

  /**
   * Get the {@code startTime} property.
   * Inclusive
   * @see #startTime
   */
  public BTime getStartTime() { return (BTime)get(startTime); }

  /**
   * Set the {@code startTime} property.
   * Inclusive
   * @see #startTime
   */
  public void setStartTime(BTime v) { set(startTime, v, null); }

  //endregion Property "startTime"

  //region Property "endTime"

  /**
   * Slot for the {@code endTime} property.
   * Inclusive
   * @see #getEndTime
   * @see #setEndTime
   */
  public static final Property endTime = newProperty(0, BTime.make(0,0,0,0), null);

  /**
   * Get the {@code endTime} property.
   * Inclusive
   * @see #endTime
   */
  public BTime getEndTime() { return (BTime)get(endTime); }

  /**
   * Set the {@code endTime} property.
   * Inclusive
   * @see #endTime
   */
  public void setEndTime(BTime v) { set(endTime, v, null); }

  //endregion Property "endTime"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeRange.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BTimeRange() {}

  public BTimeRange(BTime start, BTime end) 
  {
    setStartTime(start);
    setEndTime(end);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Tests whether or not the specified time is within this range.
   * The range is inclusive of the endpoints.
   */
  public boolean includes(BAbsTime time)
  {
    return test(time.getTimeOfDayMillis());
  }

  /**
   * Tests whether or not the specified time is within this range.
   * The range is inclusive of the endpoints.
   */
  public boolean includes(BTime time)
  {
    return test(time.getTimeOfDayMillis());
  }

  /**
   * Does this time range include the entire day?
   */
  public boolean isAllDay()
  {
    return getStartTime().equals(getEndTime());
  }

  /**
   * Get a string representation of the time range.
   */  
  @Override
  public String toString(Context ctx)
  {
    return getStartTime().toString(ctx) + " - " + getEndTime().toString(ctx);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  private boolean test(long time)
  {
    long start = getStartTime().getTimeOfDayMillis();
    long end = getEndTime().getTimeOfDayMillis();
    if (end == 0)
      end = BRelTime.MILLIS_IN_DAY;
    if (start < end)
      return (time >= start) && (time <= end);
    else //start >= end
      return (time >= start) || (time <= end);
  }


}//BTimeRange
