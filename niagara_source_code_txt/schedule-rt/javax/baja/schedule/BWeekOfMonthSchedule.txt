/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.naming.BOrd;
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

import com.tridium.fox.util.FoxRpcUtil;
import com.tridium.schedule.ScheduleUtil;
import com.tridium.sys.schema.Fw;

/**
 * Effective depending upon the week of month, 1 - 6 where 6 represents the
 * last 7 days of any month.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 14$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "set",
  type = "BEnumSet",
  defaultValue = "BEnumSet.DEFAULT",
  flags = Flags.USER_DEFINED_1,
  facets = @Facet(name = "BFacets.RANGE", value = "BEnumRange.make(ScheduleUtil.weeksOfMonth)"),
  override = true
)
public class BWeekOfMonthSchedule
  extends BEnumSetSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BWeekOfMonthSchedule(3239501791)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "set"

  /**
   * Slot for the {@code set} property.
   * @see #getSet
   * @see #setSet
   */
  public static final Property set = newProperty(Flags.USER_DEFINED_1, BEnumSet.DEFAULT, BFacets.make(BFacets.RANGE, BEnumRange.make(ScheduleUtil.weeksOfMonth)));

  //endregion Property "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWeekOfMonthSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BWeekOfMonthSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Converts a date (day of month) into a week of month.
   */
  public static final int dayToWeek(int dayOfMonth)
  {
    while (dayOfMonth % 7 != 0)
      dayOfMonth++;
    return dayOfMonth / 7;
  }

  /**
   * Converts a date (day of month) into a week of month.
   */
  public final int dayToCalWeek(BAbsTime at)
  {
    // get the first day of the month
    BAbsTime first = BAbsTime.make(at.getYear(), at.getMonth(), 1);
    int firstWeekday = first.getWeekday().getOrdinal();


    if (firstDayOfWeek == null)
    {
      firstDayOfWeek = findFirstDayOfWeek();
    }



    int firstDayOfWeek = this.firstDayOfWeek.getOrdinal();

    if (firstDayOfWeek > firstWeekday)
    {
      firstWeekday += 7;
    }

    // modify the day of month as if the month started on sunday,
    // this will allow us to get the week
    int dayOfMonth = at.getDay() + firstWeekday - firstDayOfWeek;

    // get the value of the last day of this week, then / by 7
    if (dayOfMonth % 7 != 0)
    {
      dayOfMonth += (7 - (dayOfMonth % 7));
    }
    return dayOfMonth / 7;
  }

  private BWeekday findFirstDayOfWeek()
  {
    BAbstractSchedule root = getRootSchedule();
    BOrd refBase = (BOrd)root.get("refBase");

    // if refBase is not null, root is not mounted but refBase is, do a RPC on getFirstDayOfWeek
    // to get the station's first day of week
    if (refBase != null)
    {
      BAbstractSchedule schedule = (BAbstractSchedule)refBase.resolve().get();
      return FoxRpcUtil.<BWeekday>doSilentRpc(schedule, "getFirstDayOfWeek").orElse(BWeekday.DEFAULT);
    }
    // root should be mounted, make the RPC on it.
    else if (root.isMounted())
    {
      return FoxRpcUtil.<BWeekday>doSilentRpc(root, "getFirstDayOfWeek").orElse(BWeekday.DEFAULT);
    }
    // this shoudn't happen, but just in case, call getFirstDayOfWeek directly
    else
    {
      return root.getFirstDayOfWeek(null, null);
    }
  }

  /**  12 */
  @Override
  public int getMax()
  {
    return 12;
  }

  /**  1 */
  @Override
  public int getMin()
  {
    return 1;
  }

  @Override
  public boolean isAlwaysEffective()
  {
    if (super.isAlwaysEffective())
      return true;
    BEnumSet set = getSet();

    // Here are some rules abour week to cal week equality
    // week 1 always contains all days in cal week 1
    // last 7 days always contains all days in cal week 6
    // last 7 days always contains all weeks in week 5
    // week 5 always contains all days in cal week 6
    // combintation of week x and x+1 always contains all days in cal week x+1
    // this handles all cases that we need to provide a definitive value for alwaysEffective

    // 1-5 = week 1-5, 6 = last 7 days, 7-12 = cal week 1-6
    if (!set.contains(1) && !set.contains(7))
      return false;
    if (! (set.contains(1) && set.contains(2)) && !set.contains(8))
      return false;
    if (! (set.contains(2) && set.contains(3)) && !set.contains(9))
      return false;
    if (! (set.contains(3) && set.contains(4)) && !set.contains(10))
      return false;
    if (! (set.contains(4) && (set.contains(5) || set.contains(6))) && !set.contains(11))
      return false;
    if (!set.contains(5) && !set.contains(6) && !set.contains(12))
      return false;
    return true;
  }

  @Override
  public boolean isEffective(BAbsTime at)
  {
    int wk = dayToWeek(at.getDay());
    int calWk = dayToCalWeek(at);
    if (isEffective(wk))
      return true;
    if (isEffective(calWk + 6))
      return true;
    if (contains(6))
      return at.getDay() > BAbsTime.getDaysInMonth(at.getYear(),at.getMonth()) - 7;
    return false;
  }

  @Override
  public BAbsTime nextEvent(BAbsTime from)
  {
    if (isAlwaysEffective() || isNeverEffective())
      return null;
    boolean effective = isEffective(from);
    from = BAbsTime.make(
        from.getYear(),
        from.getMonth(),
        from.getDay(),
        0,0,0,0,
        from.getTimeZone());
    while (isEffective(from) == effective)
      from = from.nextDay();
    return from;
  }

  BWeekday firstDayOfWeek = null;


}//BWeekOfMonthSchedule
