/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * Schedule combining a day schedule with another for determining when
 * to apply the day.
 * @author Aaron Hansen
 * @creation Aug 2002
 * @version $Revision: 20$ $Date: 7/17/09 10:08:35 AM EDT$
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
  name = "day",
  type = "BDaySchedule",
  defaultValue = "new BDaySchedule()",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "days",
  type = "BAbstractSchedule",
  defaultValue = "new BDateSchedule()"
)
public class BDailySchedule
  extends BCompositeSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BDailySchedule(3057823583)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.USER_DEFINED_1 | Flags.READONLY, false, null);

  //endregion Property "union"

  //region Property "day"

  /**
   * Slot for the {@code day} property.
   * @see #getDay
   * @see #setDay
   */
  public static final Property day = newProperty(Flags.READONLY, new BDaySchedule(), null);

  /**
   * Get the {@code day} property.
   * @see #day
   */
  public BDaySchedule getDay() { return (BDaySchedule)get(day); }

  /**
   * Set the {@code day} property.
   * @see #day
   */
  public void setDay(BDaySchedule v) { set(day, v, null); }

  //endregion Property "day"

  //region Property "days"

  /**
   * Slot for the {@code days} property.
   * @see #getDays
   * @see #setDays
   */
  public static final Property days = newProperty(0, new BDateSchedule(), null);

  /**
   * Get the {@code days} property.
   * @see #days
   */
  public BAbstractSchedule getDays() { return (BAbstractSchedule)get(days); }

  /**
   * Set the {@code days} property.
   * @see #days
   */
  public void setDays(BAbstractSchedule v) { set(days, v, null); }

  //endregion Property "days"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDailySchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BDailySchedule() {}

  public BDailySchedule(BAbstractSchedule sch)
  {
    setDays(sch);
  }

  public BDailySchedule(BAbstractSchedule sch,
                        BTime start,
                        BTime finish,
                        BStatusValue value)
  {
    setDays(sch);
    add(start,finish,value);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  public boolean add(BTime start, BTime finish, BStatusValue value)
  {
    return add(start,finish,value, null);
  }

  public boolean add(BTime start, BTime finish, BStatusValue value, Context cx)
  {
    return getDay().add(start,finish,value, cx);
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    if (getDay().nextEvent(after) == null)
      return null;
    if (getDays().isEffective(after))
      return super.nextEvent(after);
    return getDays().nextEvent(after);
  }

  @Override
  public String toString(Context cx)
  {
    return getDays().toString(cx);
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


}//BDailySchedule
