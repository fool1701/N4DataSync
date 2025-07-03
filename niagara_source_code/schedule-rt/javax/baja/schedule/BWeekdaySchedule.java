/*
 * Copyright 2001 Tridium, Inc.  All Rights Reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BWeekday;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BEnumSet;

import com.tridium.schedule.ScheduleUtil;

/**
 * Effective depending upon the day of week, 0 - 6.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 16$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "set",
  type = "BEnumSet",
  defaultValue = "BEnumSet.DEFAULT",
  flags = Flags.USER_DEFINED_1,
  facets = @Facet(name = "BFacets.RANGE", value = "BEnumRange.make(ScheduleUtil.weekdays)"),
  override = true
)
public class BWeekdaySchedule extends BEnumSetSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BWeekdaySchedule(2583587381)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "set"

  /**
   * Slot for the {@code set} property.
   * @see #getSet
   * @see #setSet
   */
  public static final Property set = newProperty(Flags.USER_DEFINED_1, BEnumSet.DEFAULT, BFacets.make(BFacets.RANGE, BEnumRange.make(ScheduleUtil.weekdays)));

  //endregion Property "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWeekdaySchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BWeekdaySchedule() {}

  public BWeekdaySchedule(BWeekday day)
  {
    add(day);
  }

  @Override
  public boolean isAlwaysEffective()
  {
    if (super.isAlwaysEffective())
    {
      return true;
    }

    BEnumSet set = getSet();
    if (!set.contains(0))
    {
      return false;
    }
    if (!set.contains(1))
    {
      return false;
    }
    if (!set.contains(2))
    {
      return false;
    }
    if (!set.contains(3))
    {
      return false;
    }
    if (!set.contains(4))
    {
      return false;
    }
    if (!set.contains(5))
    {
      return false;
    }
    if (!set.contains(6))
    {
      return false;
    }
    return true;
  }

  @Override
  public boolean isEffective(BAbsTime at)
  {
    return isEffective(at.getWeekday().getOrdinal());
  }

  /** 6 */
  @Override
  public int getMax()
  {
    return 6;
  }

  /** 0 */
  @Override
  public int getMin()
  {
    return 0;
  }

  @Override
  public BAbsTime nextEvent(BAbsTime from)
  {
    if (isAlwaysEffective() || isNeverEffective())
    {
      return null;
    }

    from = BAbsTime.make(
        from.getYear(),
        from.getMonth(),
        from.getDay(),
        0,0,0,0,
        from.getTimeZone());

    boolean effective = isEffective(from);

    BWeekday wd = from.getWeekday();
    int dayOfYear = from.getDayOfYear();
    int year = from.getYear();

    while (contains(wd.getOrdinal()) == effective)
    {
      ++dayOfYear;
      wd = wd.next();
    }

    while (dayOfYear > BAbsTime.getDaysInYear(year))
    {
      //we rolled to the next year
      dayOfYear -= BAbsTime.getDaysInYear(year);
      ++year;
    }

    return BAbsTime.makeDayOfYear(year, dayOfYear, 0, 0, 0, 0, from.getTimeZone());
  }
}
