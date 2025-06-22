/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BTime;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Effective everyday between an inclusive BTime start and exclusive BTime
 * finish.  Usually output is assigned the schedules of this type.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 24$ $Date: 2/1/10 12:54:57 PM EST$
 */
@NiagaraType
@NiagaraProperty(
  name = "start",
  type = "BTime",
  defaultValue = "BTime.make(0,0,0)",
  flags = Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "finish",
  type = "BTime",
  defaultValue = "BTime.make(0,0,0)",
  flags = Flags.USER_DEFINED_1
)
public class BTimeSchedule
  extends BAbstractSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BTimeSchedule(3779914546)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "start"

  /**
   * Slot for the {@code start} property.
   * @see #getStart
   * @see #setStart
   */
  public static final Property start = newProperty(Flags.USER_DEFINED_1, BTime.make(0,0,0), null);

  /**
   * Get the {@code start} property.
   * @see #start
   */
  public BTime getStart() { return (BTime)get(start); }

  /**
   * Set the {@code start} property.
   * @see #start
   */
  public void setStart(BTime v) { set(start, v, null); }

  //endregion Property "start"

  //region Property "finish"

  /**
   * Slot for the {@code finish} property.
   * @see #getFinish
   * @see #setFinish
   */
  public static final Property finish = newProperty(Flags.USER_DEFINED_1, BTime.make(0,0,0), null);

  /**
   * Get the {@code finish} property.
   * @see #finish
   */
  public BTime getFinish() { return (BTime)get(finish); }

  /**
   * Set the {@code finish} property.
   * @see #finish
   */
  public void setFinish(BTime v) { set(finish, v, null); }

  //endregion Property "finish"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BTimeSchedule() {}

  public BTimeSchedule(BTime start, BTime finish)
  {
    setStart(start);
    setFinish(finish);
  }

  public BTimeSchedule(BTime start, BTime finish, BStatusValue output)
  {
    setStart(start);
    setFinish(finish);
    setEffectiveValue(output);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public boolean isEffective(BAbsTime at)
  {
    if (getAlwaysEffective())
      return true;
    return isEffective(at.getTimeOfDayMillis());
  }

  public boolean isEffective(BTime at)
  {
    if (getAlwaysEffective())
      return true;
    return isEffective(at.getTimeOfDayMillis());
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    if (getAlwaysEffective())
      return null;
    BTime st = getStart();
    BTime fn = getFinish();
    long stMs = st.getTimeOfDayMillis();
    long fnMs = fn.getTimeOfDayMillis();
    long adjusted = finMillis(fnMs);// if finish is 0, it is end of day
    if (stMs >= adjusted)
      return null;
    BAbsTime date = after;
    BTime time = st;
    long inMs = after.getTimeOfDayMillis();
    if (inMs >= adjusted)
    {
      date = date.nextDay();
    }
    else if (inMs >= stMs)
    {
      if (fnMs == 0)
        date = date.nextDay();
      time = fn;
    }

    BAbsTime returnValue = BAbsTime.make(date.getYear(),
                         date.getMonth(),
                         date.getDay(),
                         time.getHour(),
                         time.getMinute(),
                         time.getSecond(),
                         time.getMillisecond(),
                         after.getTimeZone(),
                         BFacets.make(BAbsTime.TIME_MODE_FACET, BAbsTime.TIME_MODE_WALL_SCHD));

    //BAbsTime.TIME_MODE_WALL_SCHD is 3.5's fix for DST BUG FIX
    return returnValue;

  }

  @Override
  public String toString(Context cx)
  {
    //BTriggerSchedule as a parent
    if(getEffectiveValue() == null)
      return lex.getText("timeSchedule.noEffective.toString", new Object[] { getStart().toString(cx), getFinish().toString(cx)});
    else //all other schedules
      return lex.getText("timeSchedule.toString", new Object[] { getStart().toString(cx), getFinish().toString(cx), getEffectiveValue().valueToString(cx)});
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  private final boolean isEffective(long ms)
  {
    long finishMillis = finMillis(getFinish().getTimeOfDayMillis());
    return ((getStart().getTimeOfDayMillis() <= ms) && (ms < finishMillis));
  }

  private final long finMillis(long ms)
  {
    if (ms == 0)
      return BRelTime.MILLIS_IN_DAY;
    return ms;
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

}//BTimeSchedule
