/*
 * Copyright 2002 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
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
 * @creation Oct 2002
 * @version $Revision: 25$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "union",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1,
  override = true
)
/*
 Any positive number, or -1 for any year.
 */
@NiagaraProperty(
  name = "yearSchedule",
  type = "BYearSchedule",
  defaultValue = "new BYearSchedule().initAlwaysEffective(true)",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "monthSchedule",
  type = "BMonthSchedule",
  defaultValue = "new BMonthSchedule().initSingleSelection(true)",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "daySchedule",
  type = "BDayOfMonthSchedule",
  defaultValue = "new BDayOfMonthSchedule().initSingleSelection(true)",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "weekdaySchedule",
  type = "BWeekdaySchedule",
  defaultValue = "new BWeekdaySchedule().initSingleSelection(true)",
  flags = Flags.READONLY
)
public class BDateSchedule
  extends BCompositeSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BDateSchedule(116466625)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.USER_DEFINED_1, false, null);

  //endregion Property "union"

  //region Property "yearSchedule"

  /**
   * Slot for the {@code yearSchedule} property.
   * Any positive number, or -1 for any year.
   * @see #getYearSchedule
   * @see #setYearSchedule
   */
  public static final Property yearSchedule = newProperty(Flags.READONLY, new BYearSchedule().initAlwaysEffective(true), null);

  /**
   * Get the {@code yearSchedule} property.
   * Any positive number, or -1 for any year.
   * @see #yearSchedule
   */
  public BYearSchedule getYearSchedule() { return (BYearSchedule)get(yearSchedule); }

  /**
   * Set the {@code yearSchedule} property.
   * Any positive number, or -1 for any year.
   * @see #yearSchedule
   */
  public void setYearSchedule(BYearSchedule v) { set(yearSchedule, v, null); }

  //endregion Property "yearSchedule"

  //region Property "monthSchedule"

  /**
   * Slot for the {@code monthSchedule} property.
   * @see #getMonthSchedule
   * @see #setMonthSchedule
   */
  public static final Property monthSchedule = newProperty(Flags.READONLY, new BMonthSchedule().initSingleSelection(true), null);

  /**
   * Get the {@code monthSchedule} property.
   * @see #monthSchedule
   */
  public BMonthSchedule getMonthSchedule() { return (BMonthSchedule)get(monthSchedule); }

  /**
   * Set the {@code monthSchedule} property.
   * @see #monthSchedule
   */
  public void setMonthSchedule(BMonthSchedule v) { set(monthSchedule, v, null); }

  //endregion Property "monthSchedule"

  //region Property "daySchedule"

  /**
   * Slot for the {@code daySchedule} property.
   * @see #getDaySchedule
   * @see #setDaySchedule
   */
  public static final Property daySchedule = newProperty(Flags.READONLY, new BDayOfMonthSchedule().initSingleSelection(true), null);

  /**
   * Get the {@code daySchedule} property.
   * @see #daySchedule
   */
  public BDayOfMonthSchedule getDaySchedule() { return (BDayOfMonthSchedule)get(daySchedule); }

  /**
   * Set the {@code daySchedule} property.
   * @see #daySchedule
   */
  public void setDaySchedule(BDayOfMonthSchedule v) { set(daySchedule, v, null); }

  //endregion Property "daySchedule"

  //region Property "weekdaySchedule"

  /**
   * Slot for the {@code weekdaySchedule} property.
   * @see #getWeekdaySchedule
   * @see #setWeekdaySchedule
   */
  public static final Property weekdaySchedule = newProperty(Flags.READONLY, new BWeekdaySchedule().initSingleSelection(true), null);

  /**
   * Get the {@code weekdaySchedule} property.
   * @see #weekdaySchedule
   */
  public BWeekdaySchedule getWeekdaySchedule() { return (BWeekdaySchedule)get(weekdaySchedule); }

  /**
   * Set the {@code weekdaySchedule} property.
   * @see #weekdaySchedule
   */
  public void setWeekdaySchedule(BWeekdaySchedule v) { set(weekdaySchedule, v, null); }

  //endregion Property "weekdaySchedule"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDateSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BDateSchedule() {}

  public BDateSchedule(int day, BMonth mo, int year)
  {
    setDay(day);
    setMonth(mo);
    setYear(year);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  public int compareTo(BAbsTime arg)
  {
    int thisTmp = getYear();
    int argTmp = arg.getYear();
    if (thisTmp >= 0)
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    thisTmp = getMonth();
    argTmp = arg.getMonth().getOrdinal();
    if (thisTmp >= 0)
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    thisTmp = getDay();
    argTmp = arg.getDay();
    if (thisTmp > 0)
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    thisTmp = getWeekday();
    argTmp = arg.getWeekday().getOrdinal();
    if (thisTmp > 0)
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    return 0;
  }

  public int compareTo(BDateSchedule arg)
  {
    int thisTmp = getYear();
    int argTmp = arg.getYear();
    if (thisTmp >= 0)
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    thisTmp = getMonth();
    argTmp = arg.getMonth();
    if ((thisTmp >= 0) && (argTmp >= 0))
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    thisTmp = getDay();
    argTmp = arg.getDay();
    if ((thisTmp > 0) && (argTmp > 0))
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    thisTmp = getWeekday();
    argTmp = arg.getWeekday();
    if (thisTmp > 0)
    {
      if (thisTmp < argTmp)
        return -1;
      if (thisTmp > argTmp)
        return 1;
    }
    return 0;
  }

  public int getDay()
  {
    if (getDaySchedule().isAlwaysEffective())
      return -1;
    return getDaySchedule().first();
  }

  public int getMonth()
  {
    if (getMonthSchedule().isAlwaysEffective())
      return -1;
    return getMonthSchedule().first();
  }

  public int getWeekday()
  {
    if (getWeekdaySchedule().isAlwaysEffective())
      return -1;
    return getWeekdaySchedule().first();
  }

  public int getYear()
  {
    if (getYearSchedule().getAlwaysEffective())
      return -1;
    return getYearSchedule().getYear();
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    int year = getYear();
    //optimization
    if (year > -1)
    {
      int ay = after.getYear();
      if (ay > year)
      {
        return null;
      }
      else if (ay == year)
      {
        int month = getMonth();
        if (month >= 0)
        {
          if (after.getMonth().getOrdinal() > month)
            return null;
        }
      }
    }
    return super.nextEvent(after);
  }

  /**
   *
   */
  public void setDay(int dy)
  {
    setDay(dy, null);
  }

  public void setMonth(int mo)
  {
    setMonth(mo, null);
  }

  public void setMonth(BMonth mo)
  {
    setMonth(mo, null);
  }

  public void setWeekday(int wd)
  {
    setWeekday(wd, null);
  }

  public void setYear(int yr)
  {
    setYear(yr, null);
  }



  public void setDay(int dy, Context cx)
  {
    if (dy < 0)
      getDaySchedule().clear();
    else
      getDaySchedule().clear().add(dy, cx);
  }

  public void setMonth(int mo, Context cx)
  {
    if (mo < 0)
      getMonthSchedule().clear();
    else
      getMonthSchedule().clear().add(mo, cx);
  }

  public void setMonth(BMonth mo, Context cx)
  {
    if (mo == null)
      getMonthSchedule().clear();
    else
      getMonthSchedule().clear().add(mo, cx);
  }

  public void setWeekday(int wd, Context cx)
  {
    if (wd < 0)
      getWeekdaySchedule().clear();
    else
      getWeekdaySchedule().clear().add(wd, cx);
  }

  public void setYear(int yr, Context cx)
  {
    getYearSchedule().initYear(yr, cx).setAlwaysEffective(yr < 0);
  }

  @Override
  public String toString(Context cx)
  {
    Lexicon l = Lexicon.make(BAbstractSchedule.class);
    return l.get("type.dateSchedule") + ": " + criteriaString(cx);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected String criteriaString(Context cx)
  {
    boolean criteria = false;
    StringBuilder buf = new StringBuilder();
    //weekday
    int i = getWeekday();
    if (i >= 0)
    {
      BWeekday w = BWeekday.make(i);
      buf.append(w.getShortDisplayTag(cx));
      criteria = true;
    }
    //dom
    i = getDay();
    if (i >= 0)
    {
      if (criteria)
        buf.append(' ');
      buf.append(i);
      criteria = true;
    }
    //moy
    i = getMonth();
    if (i >= 0)
    {
      if (criteria)
        buf.append(' ');
      if (i == 12)
      {
        Lexicon l = Lexicon.make(BDateSchedule.class);
        buf.append(l.get("month.jan_mar_may_jul_sep_nov"));
      }
      else if (i == 13)
      {
        Lexicon l = Lexicon.make(BDateSchedule.class);
        buf.append(l.get("month.feb_apr_jun_aug_oct_dec"));
      }
      else
      {
        BMonth m = BMonth.make(i);
        buf.append(m.getShortDisplayTag(cx));
      }
      criteria = true;
    }
    //year
    i = getYear();
    if (i >= 0)
    {
      if (criteria)
        buf.append(' ');
      buf.append(i);
      criteria = true;
    }
    if (criteria)
      return buf.toString();
    return "*";
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
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


}//BDateSchedule
