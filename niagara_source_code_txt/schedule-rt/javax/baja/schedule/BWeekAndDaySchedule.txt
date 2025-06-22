/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BMonth;
import javax.baja.sys.BWeekday;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * @author Aaron Hansen
 * @version $Revision: 15$ $Date: 1/9/08 10:47:58 AM EST$
 * @creation Oct 2002
 */
@NiagaraType
@NiagaraProperty(
  name = "union",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1 | Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "months",
  type = "BMonthSchedule",
  defaultValue = "new BMonthSchedule().initEffectiveWhenEmpty(true).initSingleSelection(true)"
)
@NiagaraProperty(
  name = "weeksOfMonth",
  type = "BWeekOfMonthSchedule",
  defaultValue = "new BWeekOfMonthSchedule().initEffectiveWhenEmpty(true).initSingleSelection(true)"
)
@NiagaraProperty(
  name = "weekdays",
  type = "BWeekdaySchedule",
  defaultValue = "new BWeekdaySchedule().initEffectiveWhenEmpty(true).initSingleSelection(true)"
)
public class BWeekAndDaySchedule
    extends BCompositeSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BWeekAndDaySchedule(3784996003)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.USER_DEFINED_1 | Flags.READONLY, false, null);

  //endregion Property "union"

  //region Property "months"

  /**
   * Slot for the {@code months} property.
   * @see #getMonths
   * @see #setMonths
   */
  public static final Property months = newProperty(0, new BMonthSchedule().initEffectiveWhenEmpty(true).initSingleSelection(true), null);

  /**
   * Get the {@code months} property.
   * @see #months
   */
  public BMonthSchedule getMonths() { return (BMonthSchedule)get(months); }

  /**
   * Set the {@code months} property.
   * @see #months
   */
  public void setMonths(BMonthSchedule v) { set(months, v, null); }

  //endregion Property "months"

  //region Property "weeksOfMonth"

  /**
   * Slot for the {@code weeksOfMonth} property.
   * @see #getWeeksOfMonth
   * @see #setWeeksOfMonth
   */
  public static final Property weeksOfMonth = newProperty(0, new BWeekOfMonthSchedule().initEffectiveWhenEmpty(true).initSingleSelection(true), null);

  /**
   * Get the {@code weeksOfMonth} property.
   * @see #weeksOfMonth
   */
  public BWeekOfMonthSchedule getWeeksOfMonth() { return (BWeekOfMonthSchedule)get(weeksOfMonth); }

  /**
   * Set the {@code weeksOfMonth} property.
   * @see #weeksOfMonth
   */
  public void setWeeksOfMonth(BWeekOfMonthSchedule v) { set(weeksOfMonth, v, null); }

  //endregion Property "weeksOfMonth"

  //region Property "weekdays"

  /**
   * Slot for the {@code weekdays} property.
   * @see #getWeekdays
   * @see #setWeekdays
   */
  public static final Property weekdays = newProperty(0, new BWeekdaySchedule().initEffectiveWhenEmpty(true).initSingleSelection(true), null);

  /**
   * Get the {@code weekdays} property.
   * @see #weekdays
   */
  public BWeekdaySchedule getWeekdays() { return (BWeekdaySchedule)get(weekdays); }

  /**
   * Set the {@code weekdays} property.
   * @see #weekdays
   */
  public void setWeekdays(BWeekdaySchedule v) { set(weekdays, v, null); }

  //endregion Property "weekdays"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWeekAndDaySchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BWeekAndDaySchedule()
  {
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * -1 if always effective, otherwise the month ordinal.
   */
  public int getMonth()
  {
    return getField(getMonths());
  }

  public int getWeek()
  {
    return getField(getWeeksOfMonth());
  }

  public int getWeekday()
  {
    return getField(getWeekdays());
  }

  public void setMonth(int val)
  {
    getMonths().clear();
    if (val == -1)
      getMonths().setAlwaysEffective(true);
    else
    {
      getWeekdays().setAlwaysEffective(false);
      getMonths().add(val);
    }
  }

  public void setWeek(int val)
  {
    getWeeksOfMonth().clear();
    if (val == -1)
      getWeeksOfMonth().setAlwaysEffective(true);
    else
    {
      getWeekdays().setAlwaysEffective(false);
      getWeeksOfMonth().add(val);
    }
  }

  public void setWeekday(int val)
  {
    getWeekdays().clear();
    if (val == -1)
      getWeekdays().setAlwaysEffective(true);
    else
    {
      getWeekdays().setAlwaysEffective(false);
      getWeekdays().add(val);
    }
  }

  @Override
  public String toString(Context cx)
  {
    Lexicon l = Lexicon.make(BAbstractSchedule.class);
    boolean criteria = false;
    StringBuilder buf = new StringBuilder(l.get("type.weekAndDaySchedule"));
    buf.append(": ");
    //weekday
    int i = getWeekday();
    if (i >= 0)
    {
      BWeekday w = BWeekday.make(i);
      buf.append(w.getShortDisplayTag(cx));
      criteria = true;
    }
    //week
    i = getWeek();
    if (i >= 0)
    {
      if (criteria)
        buf.append(' ');
      if (i == 6)
        buf.append(l.get("week.last7days"));
      else if (i < 6)
        buf.append(l.get("week.week")).append(' ').append(i);
      else
        buf.append(l.get("week.calendarWeek")).append(' ').append(i - 6);
      criteria = true;
    }
    else if (criteria)
    {
      buf.append(' ').append(l.get("week.everyWeek"));
    }
    //month
    i = getMonth();
    if (i >= 0)
    {
      if (criteria)
        buf.append(' ');
      if (i == 12)
      {
        buf.append(l.get("month.jan_mar_may_jul_sep_nov"));
      }
      else if (i == 13)
      {
        buf.append(l.get("month.feb_apr_jun_aug_oct_dec"));
      }
      else
      {
        BMonth m = BMonth.make(i);
        buf.append(m.getShortDisplayTag(cx));
      }
      criteria = true;
    }
    else if (criteria)
    {
      buf.append(' ').append(l.get("month.everyMonth"));
    }
    if (!criteria)
      buf.append("*");
    return buf.toString();
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected int getField(BEnumSetSchedule sch)
  {
    if (getAlwaysEffective())
      return -1;
    if (sch.isAlwaysEffective())
      return -1;
    return sch.first();
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


}//BWeekAndDaySchedule
