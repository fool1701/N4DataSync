/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.schedule.Chronometer;
import com.tridium.sys.metrics.IMetricResource;

/**
 * An schedule that fires events; there is no output.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 64$ $Date: 11/6/07 5:01:02 PM EST$
 */
@NiagaraType
@NiagaraProperty(
  name = "alwaysEffective",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN,
  override = true
)
@NiagaraProperty(
  name = "union",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN,
  override = true
)
@NiagaraProperty(
  name = "dates",
  type = "BCalendarSchedule",
  defaultValue = "new BCalendarSchedule()",
  flags = Flags.HIDDEN | Flags.OPERATOR
)
/*
 Times during the days selected by the calendar and matix
 for firing triggers.
 */
@NiagaraProperty(
  name = "times",
  type = "BDaySchedule",
  defaultValue = "new BDaySchedule()",
  flags = Flags.HIDDEN | Flags.OPERATOR
)
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.OPERATOR | Flags.SUMMARY | Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "lastTrigger",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "nextTrigger",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY
)
/*
 Limits how far to search for a next trigger event.
 */
@NiagaraProperty(
  name = "nextTriggerSearchLimit",
  type = "BRelTime",
  defaultValue = "Chronometer._90_DAYS",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "lastModified",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.OPERATOR | Flags.READONLY
)
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.OPERATOR | Flags.READONLY,
  override = true
)
@NiagaraAction(
  name = "execute",
  flags = Flags.ASYNC | Flags.HIDDEN
)
@NiagaraTopic(
  name = "trigger",
  flags = Flags.SUMMARY
)
@NiagaraTopic(
  name = "triggerMissed",
  eventType = "BAbsTime"
)
public class BTriggerSchedule
  extends BCompositeSchedule
  implements BIStatus, IMetricResource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BTriggerSchedule(1852329299)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alwaysEffective"

  /**
   * Slot for the {@code alwaysEffective} property.
   * @see #getAlwaysEffective
   * @see #setAlwaysEffective
   */
  public static final Property alwaysEffective = newProperty(Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN, false, null);

  //endregion Property "alwaysEffective"

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN, false, null);

  //endregion Property "union"

  //region Property "dates"

  /**
   * Slot for the {@code dates} property.
   * @see #getDates
   * @see #setDates
   */
  public static final Property dates = newProperty(Flags.HIDDEN | Flags.OPERATOR, new BCalendarSchedule(), null);

  /**
   * Get the {@code dates} property.
   * @see #dates
   */
  public BCalendarSchedule getDates() { return (BCalendarSchedule)get(dates); }

  /**
   * Set the {@code dates} property.
   * @see #dates
   */
  public void setDates(BCalendarSchedule v) { set(dates, v, null); }

  //endregion Property "dates"

  //region Property "times"

  /**
   * Slot for the {@code times} property.
   * Times during the days selected by the calendar and matix
   * for firing triggers.
   * @see #getTimes
   * @see #setTimes
   */
  public static final Property times = newProperty(Flags.HIDDEN | Flags.OPERATOR, new BDaySchedule(), null);

  /**
   * Get the {@code times} property.
   * Times during the days selected by the calendar and matix
   * for firing triggers.
   * @see #times
   */
  public BDaySchedule getTimes() { return (BDaySchedule)get(times); }

  /**
   * Set the {@code times} property.
   * Times during the days selected by the calendar and matix
   * for firing triggers.
   * @see #times
   */
  public void setTimes(BDaySchedule v) { set(times, v, null); }

  //endregion Property "times"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(Flags.OPERATOR | Flags.SUMMARY | Flags.USER_DEFINED_1, true, null);

  /**
   * Get the {@code enabled} property.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "lastTrigger"

  /**
   * Slot for the {@code lastTrigger} property.
   * @see #getLastTrigger
   * @see #setLastTrigger
   */
  public static final Property lastTrigger = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY, BAbsTime.NULL, null);

  /**
   * Get the {@code lastTrigger} property.
   * @see #lastTrigger
   */
  public BAbsTime getLastTrigger() { return (BAbsTime)get(lastTrigger); }

  /**
   * Set the {@code lastTrigger} property.
   * @see #lastTrigger
   */
  public void setLastTrigger(BAbsTime v) { set(lastTrigger, v, null); }

  //endregion Property "lastTrigger"

  //region Property "nextTrigger"

  /**
   * Slot for the {@code nextTrigger} property.
   * @see #getNextTrigger
   * @see #setNextTrigger
   */
  public static final Property nextTrigger = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY, BAbsTime.NULL, null);

  /**
   * Get the {@code nextTrigger} property.
   * @see #nextTrigger
   */
  public BAbsTime getNextTrigger() { return (BAbsTime)get(nextTrigger); }

  /**
   * Set the {@code nextTrigger} property.
   * @see #nextTrigger
   */
  public void setNextTrigger(BAbsTime v) { set(nextTrigger, v, null); }

  //endregion Property "nextTrigger"

  //region Property "nextTriggerSearchLimit"

  /**
   * Slot for the {@code nextTriggerSearchLimit} property.
   * Limits how far to search for a next trigger event.
   * @see #getNextTriggerSearchLimit
   * @see #setNextTriggerSearchLimit
   */
  public static final Property nextTriggerSearchLimit = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, Chronometer._90_DAYS, null);

  /**
   * Get the {@code nextTriggerSearchLimit} property.
   * Limits how far to search for a next trigger event.
   * @see #nextTriggerSearchLimit
   */
  public BRelTime getNextTriggerSearchLimit() { return (BRelTime)get(nextTriggerSearchLimit); }

  /**
   * Set the {@code nextTriggerSearchLimit} property.
   * Limits how far to search for a next trigger event.
   * @see #nextTriggerSearchLimit
   */
  public void setNextTriggerSearchLimit(BRelTime v) { set(nextTriggerSearchLimit, v, null); }

  //endregion Property "nextTriggerSearchLimit"

  //region Property "lastModified"

  /**
   * Slot for the {@code lastModified} property.
   * @see #getLastModified
   * @see #setLastModified
   */
  public static final Property lastModified = newProperty(Flags.OPERATOR | Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code lastModified} property.
   * @see #lastModified
   */
  public BAbsTime getLastModified() { return (BAbsTime)get(lastModified); }

  /**
   * Set the {@code lastModified} property.
   * @see #lastModified
   */
  public void setLastModified(BAbsTime v) { set(lastModified, v, null); }

  //endregion Property "lastModified"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.OPERATOR | Flags.READONLY, BStatus.ok, null);

  //endregion Property "status"

  //region Action "execute"

  /**
   * Slot for the {@code execute} action.
   * @see #execute()
   */
  public static final Action execute = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code execute} action.
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

  //endregion Action "execute"

  //region Topic "trigger"

  /**
   * Slot for the {@code trigger} topic.
   * @see #fireTrigger
   */
  public static final Topic trigger = newTopic(Flags.SUMMARY, null);

  /**
   * Fire an event for the {@code trigger} topic.
   * @see #trigger
   */
  public void fireTrigger(BValue event) { fire(trigger, event, null); }

  //endregion Topic "trigger"

  //region Topic "triggerMissed"

  /**
   * Slot for the {@code triggerMissed} topic.
   * @see #fireTriggerMissed
   */
  public static final Topic triggerMissed = newTopic(0, null);

  /**
   * Fire an event for the {@code triggerMissed} topic.
   * @see #triggerMissed
   */
  public void fireTriggerMissed(BAbsTime event) { fire(triggerMissed, event, null); }

  //endregion Topic "triggerMissed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTriggerSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BTriggerSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Convenience for programmatically adding a schedule that selects the
   * dates on which triggers are fired.
   */
  public void addDates(String name, BAbstractSchedule sch)
  {
    getDates().add(name,sch);
  }

  /**
   * Convenience for programmatically adding a time.
   * @param hour Hour of day, 0 - 23
   * @param min Minute of hour, 0 - 59
   */
  public boolean addTime(int hour, int min)
  {
    return getTimes().addTrigger(hour,min);
  }

  /**
   * Convenience for programmatically adding a time.
   * @param time in the form hh:mm [am|pm]
   * @since 4.2
   *
   */
  public boolean addTime(String time)
  {
    Matcher m = TIME_PATTERN.matcher(time);
    boolean matches = m.matches();
    if (!matches) {
      throw new IllegalArgumentException("Invalid argument (hh:mm [am|pm]): " + time);
    }
    final String hours = m.group("hours");
    final String mins = m.group("mins");
    final String ampm = m.group("ampm");
    int ihours = Integer.parseInt(hours);
    final int imins = Integer.parseInt(mins);
    // If am/pm is omitted, we'll assume 24 hour time
    if ("PM".equalsIgnoreCase(ampm)) {
      if (ihours < 12) {
        ihours += 12;
      }
    } else {
      // 12:00 AM is 00:00
      if (ihours == 12) {
        ihours = 0;
      }
    }
    return getTimes().addTrigger(ihours, imins);
  }

  public void clearTimes()
  {
    getTimes().clear();
  }

  @Override
  public void clockChanged(BRelTime shift)
    throws Exception
  {
    super.clockChanged(shift);
    execute();
  }

  /**
   * If the schedule transitions from ineffective into effective, the
   * trigger topic is fired.
   */
  public void doExecute()
  {
    if (ticket != null)
      ticket.cancel();
    BAbsTime time = Clock.time();
    if (!isMounted())
      return;
    if (!isRunning())
      return;
    if (isEffective(time))
    {
      if (getEnabled())
      {
        fireTrigger(null);
        setLastTrigger(time);
      }
    }
    else if (getNextTrigger() != BAbsTime.NULL)
    {
      if (time.getMillis() > (getNextTrigger().getMillis() + 1000))
      {
        if (getEnabled())
          fireTriggerMissed(getNextTrigger());
      }
    }
    BAbsTime next = nextTrigger(time,time.add(getNextTriggerSearchLimit()));
    if (next == null)
    {
      setNextTrigger(BAbsTime.NULL);
      next = Clock.time().add(BRelTime.DAY);
    }
    else
    {
      setNextTrigger(next);
    }
    ticket = Clock.schedule(this,next,execute,null);
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    if (getTimes().nextEvent(after) == null)
      return null;
    if (getDates().isEffective(after))
      return super.nextEvent(after);
    return getDates().nextEvent(after);
  }

  /**
   * The next time a trigger will be fired after the from time, up until
   * the to time.
   * @return Null if no trigger will occur in the give range.
   */
  public BAbsTime nextTrigger(BAbsTime from, BAbsTime to)
  {
    return next(true,from,to);
  }

  @Override
  public IFuture post(Action action, BValue arg, Context cx)
  {
    BControlSchedule.pool.enqueue(new Invocation(this,action,arg,cx));
    return null;
  }

  @Override
  public void started()
    throws Exception
  {
    super.started();
    if (Sys.isStationStarted())
      execute();
  }

  @Override
  public void stationStarted()
    throws Exception
  {
    super.stationStarted();
    execute();
  }

  @Override
  public void stopped()
    throws Exception
  {
    super.stopped();
    if (ticket != null)
      ticket.cancel();
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Increments the modification property.
   */
  @Override
  protected void modified()
  {
    setLastModified(Clock.time());
    execute();
    super.modified();
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////


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

  private Clock.Ticket ticket = null;
  private static final Pattern TIME_PATTERN = Pattern.compile("(?<hours>^\\d{1,2}):(?<mins>\\d{1,2})[ ]?(?<ampm>[a|p]m)?$", Pattern.CASE_INSENSITIVE);


  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BTriggerSchedule
