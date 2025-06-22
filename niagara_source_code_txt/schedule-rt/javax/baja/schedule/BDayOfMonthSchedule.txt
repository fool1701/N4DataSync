/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BMonth;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BEnumSet;

import com.tridium.schedule.ScheduleUtil;

/**
 * Effective depending upon the day of month, 1 - 33 where 32 is the
 * last day of any month and 33 is the last seven days of any month.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 13$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "set",
  type = "BEnumSet",
  defaultValue = "BEnumSet.DEFAULT",
  flags = Flags.USER_DEFINED_1,
  facets = @Facet(name = "BFacets.RANGE", value = "BEnumRange.make(ScheduleUtil.daysOfMonth)"),
  override = true
)
public class BDayOfMonthSchedule
  extends BEnumSetSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BDayOfMonthSchedule(126418314)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "set"

  /**
   * Slot for the {@code set} property.
   * @see #getSet
   * @see #setSet
   */
  public static final Property set = newProperty(Flags.USER_DEFINED_1, BEnumSet.DEFAULT, BFacets.make(BFacets.RANGE, BEnumRange.make(ScheduleUtil.daysOfMonth)));

  //endregion Property "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDayOfMonthSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BDayOfMonthSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /** 35 */
  @Override
  public int getMax()
  {
    // 32 = last day of the month
    // 33 = last 7 days of the month
    // 34 = odd days of the month
    // 35 = even days of the month
    return 35;
  }

  /** 1 */
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
    for (int i = 1; i <= 31; i++)
    {
      if (!contains(i))
        return false;
    }
    return true;
  }

  @Override
  public boolean isEffective(BAbsTime at)
  {
    if (isAlwaysEffective())
      return true;
    if (isNeverEffective())
      return false;
    return isEffective(at.getDay(),
                       BAbsTime.getDaysInMonth(at.getYear(),at.getMonth()));
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    if (isAlwaysEffective() || isNeverEffective())
      return null;
    boolean effective = isEffective(after); //we're looking for the opposite
    int cur = after.getDay();
    int daysInMonth = BAbsTime.getDaysInMonth(after.getYear(),after.getMonth());
    //search after 'after' to end of month
    while (++cur <= daysInMonth)
    {
      if (isEffective(cur,daysInMonth) != effective)
        return BAbsTime.make(after.getYear(),
                             after.getMonth(),
                             cur,
                             0, 0, 0, 0,
                             after.getTimeZone());
    }
    //roll month, and possibly year; and return first day of next month.
    int year = after.getYear();
    BMonth month = after.getMonth();
    if (month == BMonth.december)
    {
      year++;
      month = BMonth.january;
    }
    else
    {
      month = BMonth.make(month.getOrdinal() + 1);
    }
    return BAbsTime.make(year,month,1,0,0,0,0,after.getTimeZone());
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  private final boolean isEffective(int dy, int daysInMonth)
  {
    if (isEffective(dy))
      return true;
    if ((dy == daysInMonth) && contains(32))
      return true;

    // Last 7 days of the month
    if (contains(33))
      return dy > (daysInMonth - 7);

    // Odd days of the month
    if (contains(34))
      return (dy & 1) != 0;

    // Even days of the month
    if (contains(35))
      return (dy % 2) == 0;
    return false;
  }


  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BDayOfMonthSchedule
