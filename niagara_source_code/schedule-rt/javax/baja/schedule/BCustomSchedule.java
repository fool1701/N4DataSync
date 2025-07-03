/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * @author Aaron Hansen
 * @creation Aug 2002
 * @version $Revision: 18$ $Date: 7/17/09 10:08:13 AM EDT$
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
  name = "daysOfMonth",
  type = "BDayOfMonthSchedule",
  defaultValue = "new BDayOfMonthSchedule()"
)
@NiagaraProperty(
  name = "months",
  type = "BMonthSchedule",
  defaultValue = "new BMonthSchedule()"
)
@NiagaraProperty(
  name = "weekdays",
  type = "BWeekdaySchedule",
  defaultValue = "new BWeekdaySchedule()"
)
@NiagaraProperty(
  name = "weeksOfMonth",
  type = "BWeekOfMonthSchedule",
  defaultValue = "new BWeekOfMonthSchedule()"
)
@NiagaraProperty(
  name = "year",
  type = "BYearSchedule",
  defaultValue = "new BYearSchedule().initAlwaysEffective(true)"
)
public class BCustomSchedule
  extends BCompositeSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BCustomSchedule(2067161316)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.USER_DEFINED_1 | Flags.READONLY, false, null);

  //endregion Property "union"

  //region Property "daysOfMonth"

  /**
   * Slot for the {@code daysOfMonth} property.
   * @see #getDaysOfMonth
   * @see #setDaysOfMonth
   */
  public static final Property daysOfMonth = newProperty(0, new BDayOfMonthSchedule(), null);

  /**
   * Get the {@code daysOfMonth} property.
   * @see #daysOfMonth
   */
  public BDayOfMonthSchedule getDaysOfMonth() { return (BDayOfMonthSchedule)get(daysOfMonth); }

  /**
   * Set the {@code daysOfMonth} property.
   * @see #daysOfMonth
   */
  public void setDaysOfMonth(BDayOfMonthSchedule v) { set(daysOfMonth, v, null); }

  //endregion Property "daysOfMonth"

  //region Property "months"

  /**
   * Slot for the {@code months} property.
   * @see #getMonths
   * @see #setMonths
   */
  public static final Property months = newProperty(0, new BMonthSchedule(), null);

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

  //region Property "weekdays"

  /**
   * Slot for the {@code weekdays} property.
   * @see #getWeekdays
   * @see #setWeekdays
   */
  public static final Property weekdays = newProperty(0, new BWeekdaySchedule(), null);

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

  //region Property "weeksOfMonth"

  /**
   * Slot for the {@code weeksOfMonth} property.
   * @see #getWeeksOfMonth
   * @see #setWeeksOfMonth
   */
  public static final Property weeksOfMonth = newProperty(0, new BWeekOfMonthSchedule(), null);

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

  //region Property "year"

  /**
   * Slot for the {@code year} property.
   * @see #getYear
   * @see #setYear
   */
  public static final Property year = newProperty(0, new BYearSchedule().initAlwaysEffective(true), null);

  /**
   * Get the {@code year} property.
   * @see #year
   */
  public BYearSchedule getYear() { return (BYearSchedule)get(year); }

  /**
   * Set the {@code year} property.
   * @see #year
   */
  public void setYear(BYearSchedule v) { set(year, v, null); }

  //endregion Property "year"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCustomSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BCustomSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////



  @Override
  public String toString(Context cx)
  {
    Lexicon l = Lexicon.make(BAbstractSchedule.class);
    BAbsTime now  = BAbsTime.now();
    BAbsTime time = next(true, BAbsTime.now(), now.nextYear() );


    String info = "";
    if(time != null)
    {
      info=l.getText("custom.nextEvent", new Object[] {time.toDateString(cx)});
    }
    return l.get("type.customSchedule")  + info;
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


}//BCustomSchedule
