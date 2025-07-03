/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history.ext;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BTime;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BDaysOfWeekBits;
import javax.baja.util.BTimeRange;

/**
 * BBasicActivePeriod defines the active period
 * by day of week and time of day.
 *
 * @author    John Sublett
 * @creation  19 Nov 2004
 * @version   $Revision: 4$ $Date: 5/26/05 4:05:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "daysOfWeek",
  type = "BDaysOfWeekBits",
  defaultValue = "BDaysOfWeekBits.ALL"
)
@NiagaraProperty(
  name = "timeRange",
  type = "BTimeRange",
  defaultValue = "new BTimeRange()"
)
public final class BBasicActivePeriod
  extends BActivePeriod
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BBasicActivePeriod(1321464053)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "daysOfWeek"

  /**
   * Slot for the {@code daysOfWeek} property.
   * @see #getDaysOfWeek
   * @see #setDaysOfWeek
   */
  public static final Property daysOfWeek = newProperty(0, BDaysOfWeekBits.ALL, null);

  /**
   * Get the {@code daysOfWeek} property.
   * @see #daysOfWeek
   */
  public BDaysOfWeekBits getDaysOfWeek() { return (BDaysOfWeekBits)get(daysOfWeek); }

  /**
   * Set the {@code daysOfWeek} property.
   * @see #daysOfWeek
   */
  public void setDaysOfWeek(BDaysOfWeekBits v) { set(daysOfWeek, v, null); }

  //endregion Property "daysOfWeek"

  //region Property "timeRange"

  /**
   * Slot for the {@code timeRange} property.
   * @see #getTimeRange
   * @see #setTimeRange
   */
  public static final Property timeRange = newProperty(0, new BTimeRange(), null);

  /**
   * Get the {@code timeRange} property.
   * @see #timeRange
   */
  public BTimeRange getTimeRange() { return (BTimeRange)get(timeRange); }

  /**
   * Set the {@code timeRange} property.
   * @see #timeRange
   */
  public void setTimeRange(BTimeRange v) { set(timeRange, v, null); }

  //endregion Property "timeRange"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBasicActivePeriod.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BBasicActivePeriod()
  {
  }

  /**
   * Make an active period defined by the specified days of week and time range.
   *
   * @param daysOfWeek The days of week when the active state is true.
   * @param timeRange The time range defining the active period on the active days of week.
   */
  public BBasicActivePeriod(BDaysOfWeekBits daysOfWeek, BTimeRange timeRange)
  {
    setDaysOfWeek(daysOfWeek);
    setTimeRange(timeRange);
  }

  /**
   * Does the active period always return true for isActive() with its current configuration?
   */
  @Override
  public boolean isAlwaysActive()
  {
    return getDaysOfWeek().isEveryDay() && getTimeRange().isAllDay();
  }

  /**
   * Does the active period always return false for isActive() with its current configuration?
   */
  @Override
  public boolean isNeverActive()
  {
    return getDaysOfWeek().isEmpty();
  }

  /**
   * Get the start time of the active period that includes the specified time.
   *
   * @param time A time that is included in the active period.  That is, isActive(time)
   *   return true.
   * @return Returns the start time of the period that includes the specified time.  If the
   *   specified time is not in an active period, null is returned.
   */
  @Override
  public BAbsTime getActiveStart(BAbsTime time)
  {
    if (!isActive(time))
      return null;
    else if (isAlwaysActive())
      return BAbsTime.make(time, BTime.make(0,0,0,0));
    else
      return BAbsTime.make(time, getTimeRange().getStartTime());
  }

  /**
   * Get the end time of the active period that includes the specified time.
   *
   * @param time A time that is included in the active period.  That is, isActive(time)
   *   return true.
   * @return Returns the end time of the period that includes the specified time.  If the
   *   specified time is not in an active period, null is returned.
   */
  @Override
  public BAbsTime getActiveEnd(BAbsTime time)
  {
    if (!isActive(time))
      return null;
    else if (isAlwaysActive())
      return null;
    else
    {
      BTime endTime = getTimeRange().getEndTime();
      if (endTime.getTimeOfDayMillis() == 0)
        time = time.nextDay();
      return BAbsTime.make(time, endTime);
    }
  }

  /**
   * Get the next active time after the specified time.
   *
   * @param time This method will always return a value after the specified time.
   * @return Returns the first start time after the specified time or null
   *   if this period is always active.
   */
  @Override
  public BAbsTime getNextActive(BAbsTime time)
  {
    if (isAlwaysActive()) return null;
    if (isNeverActive()) return null;

    BDaysOfWeekBits daysOfWeek = getDaysOfWeek();
    BTimeRange timeRange = getTimeRange();

    if (getDaysOfWeek().includes(time))
    {
      if (BTime.make(time).isBefore(timeRange.getStartTime()))
        return BAbsTime.make(time, timeRange.getStartTime());
    }

    BAbsTime day = time.nextDay();
    while (!daysOfWeek.includes(day.getWeekday()))
      day = day.nextDay();

    return BAbsTime.make(day, timeRange.getStartTime());
  }

  @Override
  public BAbsTime getNextInactive(BAbsTime time)
  {
    if (isAlwaysActive()) return null;
    if (isNeverActive()) return null;

    BDaysOfWeekBits daysOfWeek = getDaysOfWeek();
    BTimeRange timeRange = getTimeRange();

    if (daysOfWeek.includes(time))
    {
      long endMillis = timeRange.getEndTime().getTimeOfDayMillis();
      if (endMillis == 0)
        return BAbsTime.make(time.nextDay(), timeRange.getEndTime());

      long timeMillis = time.getTimeOfDayMillis();
      if (timeMillis < endMillis)
        return BAbsTime.make(time, timeRange.getEndTime());
    }

    BAbsTime day = time.nextDay();
    while (!daysOfWeek.includes(day.getWeekday()))
      day = day.nextDay();

    BTime endTime = timeRange.getEndTime();
    if (endTime.getTimeOfDayMillis() == 0)
      day = day.nextDay();
    return BAbsTime.make(day, timeRange.getEndTime());
  }

  /**
   * Returns true if the conditions for history collection to
   * be enabled are met, false otherwise.  Determination is based
   * on the given timestamp.
   */
  @Override
  public boolean isActive(BAbsTime timestamp)
  {
    // Check day of week
    if (!getDaysOfWeek().includes(timestamp.getWeekday()))
      return false;

    // Check for the special exclusive case - pacman issue 6401
    if (getTimeRange().getStartTime().isAfter(getTimeRange().getEndTime()))
    {
      BTimeRange invalidRange = new BTimeRange(getTimeRange().getEndTime(), getTimeRange().getStartTime());
      if (invalidRange.includes(timestamp)) // This is the special exclusive case
        return false;
    }
    else if (!getTimeRange().includes(timestamp)) // Check time range for normal case (non-exclusive)
      return false;

    return true;
  }
}
