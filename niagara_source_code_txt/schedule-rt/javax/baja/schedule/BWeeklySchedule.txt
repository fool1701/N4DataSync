/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BWeekday;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconModule;

/**
 * A week schedule with special events and an effective period.
 * @author Aaron Hansen
 * @creation Oct 2001
 * @version $Revision: 19$ $Date: 3/4/11 9:30:26 AM EST$
 */
@NiagaraType
@NiagaraProperty(
  name = "effective",
  type = "BDateRangeSchedule",
  defaultValue = "new BDateRangeSchedule()",
  flags = Flags.HIDDEN | Flags.OPERATOR
)
@NiagaraProperty(
  name = "schedule",
  type = "BCompositeSchedule",
  defaultValue = "makeSchedule()",
  flags = Flags.HIDDEN | Flags.OPERATOR
)
@NiagaraProperty(
  name = "outSource",
  type = "String",
  defaultValue = "",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT
)
public abstract class BWeeklySchedule
  extends BControlSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BWeeklySchedule(2208949070)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "effective"

  /**
   * Slot for the {@code effective} property.
   * @see #getEffective
   * @see #setEffective
   */
  public static final Property effective = newProperty(Flags.HIDDEN | Flags.OPERATOR, new BDateRangeSchedule(), null);

  /**
   * Get the {@code effective} property.
   * @see #effective
   */
  public BDateRangeSchedule getEffective() { return (BDateRangeSchedule)get(effective); }

  /**
   * Set the {@code effective} property.
   * @see #effective
   */
  public void setEffective(BDateRangeSchedule v) { set(effective, v, null); }

  //endregion Property "effective"

  //region Property "schedule"

  /**
   * Slot for the {@code schedule} property.
   * @see #getSchedule
   * @see #setSchedule
   */
  public static final Property schedule = newProperty(Flags.HIDDEN | Flags.OPERATOR, makeSchedule(), null);

  /**
   * Get the {@code schedule} property.
   * @see #schedule
   */
  public BCompositeSchedule getSchedule() { return (BCompositeSchedule)get(schedule); }

  /**
   * Set the {@code schedule} property.
   * @see #schedule
   */
  public void setSchedule(BCompositeSchedule v) { set(schedule, v, null); }

  //endregion Property "schedule"

  //region Property "outSource"

  /**
   * Slot for the {@code outSource} property.
   * @see #getOutSource
   * @see #setOutSource
   */
  public static final Property outSource = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT, "", null);

  /**
   * Get the {@code outSource} property.
   * @see #outSource
   */
  public String getOutSource() { return getString(outSource); }

  /**
   * Set the {@code outSource} property.
   * @see #outSource
   */
  public void setOutSource(String v) { setString(outSource, v, null); }

  //endregion Property "outSource"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWeeklySchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BWeeklySchedule() { }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  public void addSpecialEvent(String name, BDailySchedule specialEvent)
  {
    addSpecialEvent(name, specialEvent, null);
  }

  public void addSpecialEvent(String name, BDailySchedule specialEvent, Context cx)
  {
    getSpecialEvents().add(name, specialEvent, cx);
  }


  /**
   * Set the effectivePeriod and overridableWeely slots to new
   * instances, doesn't clear the old ones.
   */
  public void clear()
  {
    setEffective(new BDateRangeSchedule());
    setSchedule(makeSchedule());
  }

  /**
   * Removes expired special events.
   */
  @Override
  public void doCleanup()
  {
    BCompositeSchedule spec = getSpecialEvents();
    BAbstractSchedule[] ary = spec.getSchedules();
    BDailySchedule md;
    for (int i = ary.length; --i >= 0; )
    {
      // Issue 18858: casting exceptions thrown on clean up of weekly schedule
      if (ary[i] instanceof BDailySchedule)
      {
        md = (BDailySchedule) ary[i];
        if (isExpired(md.getDays()))
        {
          log.info(
            toPathString() + " removing expired special event " + md.getName());
          spec.remove(md);
        }
      }
    }
  }

  public BDaySchedule get(BWeekday day)
  {
    return getWeek().get(day);
  }

  /**
   * @param outputSource a schedule to summarize
   * @return a string summary of the input schedule
   * @deprecated as of Niagara 4.11, use the version that takes a context for
   * string formatting.
   */
  @Deprecated
  public String getSummary(BAbstractSchedule outputSource)
  {
    return getSummary(outputSource, null);
  }

  /**
   * @param outputSource a schedule to summarize
   * @param cx user context
   * @return a string summary of the input schedule
   * @since Niagara 4.11
   */
  public String getSummary(BAbstractSchedule outputSource, Context cx)
  {
    String ret;

    if ((outputSource == this) || (outputSource == null))
    {
      ret = SCHEDULE_LEX.getText("summary.defaultOutput", cx);
    }
    else
    {
      BComplex c = outputSource.getParent().getParent().getParent();
      String sourceSlotName = SlotPath.unescape(outputSource.getParent().getParent().getName());
      String sourceDisplay;

      if (c instanceof BWeekSchedule)
      {
        ret = SCHEDULE_LEX.getText("summary.weeklySchedule", cx);
        String weekday = sourceSlotName;
        sourceDisplay = BAJA_LEX.get(weekday, cx, weekday);
      }
      else
      {
        ret = SCHEDULE_LEX.getText("summary.specialEvent", cx);
        sourceDisplay = sourceSlotName;
      }

      ret += ": " + sourceDisplay;
    }

    return ret;
  }

  /**
   * The special event container.
   */
  public final BCompositeSchedule getSpecialEvents()
  {
    return (BCompositeSchedule) getSchedule().get("specialEvents");
  }

  /**
   * The actual special events.
   */
  public BDailySchedule[] getSpecialEventsChildren()
  {
    // Issue 18858: casting exceptions thrown on clean up of weekly schedule
    BAbstractSchedule[] kids = getSpecialEvents().getSchedules();
    Array<BDailySchedule> events = new Array<>(BDailySchedule.class, kids.length);
    for (int i = 0; i < kids.length; ++i)
    {
      if (kids[i] instanceof BDailySchedule)
        events.add((BDailySchedule)kids[i]);
    }
    return events.trim();
  }

  public final BWeekSchedule getWeek()
  {
    return (BWeekSchedule) getSchedule().get("week");
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    BAbsTime sch = getSchedule().nextEvent(after);
    if (sch == null)
      return null;
    BAbsTime eff = getEffective().nextEvent(after);
    if (eff == null)
      return sch;
    if (sch.compareTo(eff) < 0)
      return sch;
    return eff;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  protected void currentOutputSource(BAbstractSchedule sch)
  {
    if (sch == last)
      return;
    last = sch;
    setOutSource(getSummary(sch, null));
  }

  @Override
  protected void currentOutputSourceIsInput()
  {
    last = null;
    setOutSource(SCHEDULE_LEX.getText("summary.input", null));
  }

  static BCompositeSchedule makeSchedule()
  {
    BCompositeSchedule retern = new BCompositeSchedule();
    retern.add("specialEvents", new BCompositeSchedule());
    retern.add("week", new BWeekSchedule());
    return retern;
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

  private BAbstractSchedule last;

  private static final LexiconModule SCHEDULE_LEX = LexiconModule.make(BWeeklySchedule.class);
  private static final LexiconModule BAJA_LEX = LexiconModule.make("baja");

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BWeeklySchedule
