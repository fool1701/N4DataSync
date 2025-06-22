/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.schedule.*;

/**
 * Effective depending upon the month of year, 0 - 11 and 12-13 where twelve
 * is every other month starting with Jan and 13 is every other month
 * starting with Feb.
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
  facets = @Facet(name = "BFacets.RANGE", value = "BEnumRange.make(ScheduleUtil.months)"),
  override = true
)
public class BMonthSchedule
  extends BEnumSetSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BMonthSchedule(315531769)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "set"

  /**
   * Slot for the {@code set} property.
   * @see #getSet
   * @see #setSet
   */
  public static final Property set = newProperty(Flags.USER_DEFINED_1, BEnumSet.DEFAULT, BFacets.make(BFacets.RANGE, BEnumRange.make(ScheduleUtil.months)));

  //endregion Property "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMonthSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BMonthSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /** 13 */
  @Override
  public int getMax()
  {
    return 13;
  }

  /** 0 */
  @Override
  public int getMin()
  {
    return 0;
  }

  @Override
  public boolean isAlwaysEffective()
  {
    if (super.isAlwaysEffective())
      return true;
    for (int i = 0; i <= 11; i++)
    {
      if (!contains(i))
        return false;
    }
    return true;
  }

  @Override
  public boolean isEffective(BAbsTime at)
  {
    int mo = at.getMonth().getOrdinal();
    if (isEffective(mo))
      return true;
    if ((mo % 2) == 0) //Jan,Mar,May,Jul,Sep,Nov
      return contains(12);
    return contains(13); //Feb,Apr,Jun,Aug,Oct,Dec
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    if (isAlwaysEffective() || isNeverEffective())
      return null;
    int mo = after.getMonth().getOrdinal();
    if (++mo <= 11)
      return BAbsTime.make(
          after.getYear(),
          BMonth.make(mo),
          1,
          0,0,0,0,
          after.getTimeZone());
    return BAbsTime.make(
        after.getYear() + 1,
        BMonth.january,
        1,
        0,0,0,0,
        after.getTimeZone());
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

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


}//BMonthSchedule
