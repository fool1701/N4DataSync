/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * A BDaySchedule for each weekday.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 11$ $Date: 9/10/04 4:06:19 PM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "sunday",
  type = "BDailySchedule",
  defaultValue = "make(BWeekday.sunday)"
)
@NiagaraProperty(
  name = "monday",
  type = "BDailySchedule",
  defaultValue = "make(BWeekday.monday)"
)
@NiagaraProperty(
  name = "tuesday",
  type = "BDailySchedule",
  defaultValue = "make(BWeekday.tuesday)"
)
@NiagaraProperty(
  name = "wednesday",
  type = "BDailySchedule",
  defaultValue = "make(BWeekday.wednesday)"
)
@NiagaraProperty(
  name = "thursday",
  type = "BDailySchedule",
  defaultValue = "make(BWeekday.thursday)"
)
@NiagaraProperty(
  name = "friday",
  type = "BDailySchedule",
  defaultValue = "make(BWeekday.friday)"
)
@NiagaraProperty(
  name = "saturday",
  type = "BDailySchedule",
  defaultValue = "make(BWeekday.saturday)"
)
public class BWeekSchedule
  extends BCompositeSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BWeekSchedule(375553868)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "sunday"

  /**
   * Slot for the {@code sunday} property.
   * @see #getSunday
   * @see #setSunday
   */
  public static final Property sunday = newProperty(0, make(BWeekday.sunday), null);

  /**
   * Get the {@code sunday} property.
   * @see #sunday
   */
  public BDailySchedule getSunday() { return (BDailySchedule)get(sunday); }

  /**
   * Set the {@code sunday} property.
   * @see #sunday
   */
  public void setSunday(BDailySchedule v) { set(sunday, v, null); }

  //endregion Property "sunday"

  //region Property "monday"

  /**
   * Slot for the {@code monday} property.
   * @see #getMonday
   * @see #setMonday
   */
  public static final Property monday = newProperty(0, make(BWeekday.monday), null);

  /**
   * Get the {@code monday} property.
   * @see #monday
   */
  public BDailySchedule getMonday() { return (BDailySchedule)get(monday); }

  /**
   * Set the {@code monday} property.
   * @see #monday
   */
  public void setMonday(BDailySchedule v) { set(monday, v, null); }

  //endregion Property "monday"

  //region Property "tuesday"

  /**
   * Slot for the {@code tuesday} property.
   * @see #getTuesday
   * @see #setTuesday
   */
  public static final Property tuesday = newProperty(0, make(BWeekday.tuesday), null);

  /**
   * Get the {@code tuesday} property.
   * @see #tuesday
   */
  public BDailySchedule getTuesday() { return (BDailySchedule)get(tuesday); }

  /**
   * Set the {@code tuesday} property.
   * @see #tuesday
   */
  public void setTuesday(BDailySchedule v) { set(tuesday, v, null); }

  //endregion Property "tuesday"

  //region Property "wednesday"

  /**
   * Slot for the {@code wednesday} property.
   * @see #getWednesday
   * @see #setWednesday
   */
  public static final Property wednesday = newProperty(0, make(BWeekday.wednesday), null);

  /**
   * Get the {@code wednesday} property.
   * @see #wednesday
   */
  public BDailySchedule getWednesday() { return (BDailySchedule)get(wednesday); }

  /**
   * Set the {@code wednesday} property.
   * @see #wednesday
   */
  public void setWednesday(BDailySchedule v) { set(wednesday, v, null); }

  //endregion Property "wednesday"

  //region Property "thursday"

  /**
   * Slot for the {@code thursday} property.
   * @see #getThursday
   * @see #setThursday
   */
  public static final Property thursday = newProperty(0, make(BWeekday.thursday), null);

  /**
   * Get the {@code thursday} property.
   * @see #thursday
   */
  public BDailySchedule getThursday() { return (BDailySchedule)get(thursday); }

  /**
   * Set the {@code thursday} property.
   * @see #thursday
   */
  public void setThursday(BDailySchedule v) { set(thursday, v, null); }

  //endregion Property "thursday"

  //region Property "friday"

  /**
   * Slot for the {@code friday} property.
   * @see #getFriday
   * @see #setFriday
   */
  public static final Property friday = newProperty(0, make(BWeekday.friday), null);

  /**
   * Get the {@code friday} property.
   * @see #friday
   */
  public BDailySchedule getFriday() { return (BDailySchedule)get(friday); }

  /**
   * Set the {@code friday} property.
   * @see #friday
   */
  public void setFriday(BDailySchedule v) { set(friday, v, null); }

  //endregion Property "friday"

  //region Property "saturday"

  /**
   * Slot for the {@code saturday} property.
   * @see #getSaturday
   * @see #setSaturday
   */
  public static final Property saturday = newProperty(0, make(BWeekday.saturday), null);

  /**
   * Get the {@code saturday} property.
   * @see #saturday
   */
  public BDailySchedule getSaturday() { return (BDailySchedule)get(saturday); }

  /**
   * Set the {@code saturday} property.
   * @see #saturday
   */
  public void setSaturday(BDailySchedule v) { set(saturday, v, null); }

  //endregion Property "saturday"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWeekSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BWeekSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Days of week starting with BWeekday.getFirstDayOfWeek()
   */
  public static BWeekday[] daysInOrder(Context cx)
  {
    BWeekday[] ret = new BWeekday[7];
    int i = BWeekday.getFirstDayOfWeek(cx).getOrdinal();
    for (int j = 0; j < 7; j++)
    {
      ret[j] = BWeekday.make(i);
      if (++i == 7)
        i = 0;
    }
    return ret;
  }

  public BDaySchedule get(BWeekday day)
  {
    return ((BDailySchedule)get(day.getTag())).getDay();
  }


  /**
   * Schedules who order matchs daysInOrder()
   */
  public BDailySchedule[] schedulesInOrder(Context cx)
  {
    BDailySchedule[] ret = new BDailySchedule[7];
    int i = BWeekday.getFirstDayOfWeek(cx).getOrdinal();
    for (int j = 0; j < 7; j++)
    {
      ret[j] = schedule(i);
      if (++i == 7)
        i = 0;
    }
    return ret;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  private static BDailySchedule make(BWeekday day)
  {
    BDailySchedule s = new BDailySchedule();
    s.setDays(new BWeekdaySchedule().add(day));
    return s;
  }

  private final BDailySchedule schedule(int weekday)
  {
    switch (weekday)
    {
      case BWeekday.MONDAY: return getMonday();
      case BWeekday.TUESDAY: return getTuesday();
      case BWeekday.WEDNESDAY: return getWednesday();
      case BWeekday.THURSDAY: return getThursday();
      case BWeekday.FRIDAY: return getFriday();
      case BWeekday.SATURDAY: return getSaturday();
    }
    return getSunday();
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


}//BWeekSchedule
