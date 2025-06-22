/*
 * Copyright 2002 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * @author Aaron Hansen
 * @creation Oct 2002
 * @version $Revision: 12$ $Date: 9/10/04 4:06:17 PM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "start",
  type = "BDateSchedule",
  defaultValue = "new BDateSchedule()"
)
@NiagaraProperty(
  name = "end",
  type = "BDateSchedule",
  defaultValue = "new BDateSchedule()"
)
public class BDateRangeSchedule
  extends BAbstractSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BDateRangeSchedule(1449979180)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "start"

  /**
   * Slot for the {@code start} property.
   * @see #getStart
   * @see #setStart
   */
  public static final Property start = newProperty(0, new BDateSchedule(), null);

  /**
   * Get the {@code start} property.
   * @see #start
   */
  public BDateSchedule getStart() { return (BDateSchedule)get(start); }

  /**
   * Set the {@code start} property.
   * @see #start
   */
  public void setStart(BDateSchedule v) { set(start, v, null); }

  //endregion Property "start"

  //region Property "end"

  /**
   * Slot for the {@code end} property.
   * @see #getEnd
   * @see #setEnd
   */
  public static final Property end = newProperty(0, new BDateSchedule(), null);

  /**
   * Get the {@code end} property.
   * @see #end
   */
  public BDateSchedule getEnd() { return (BDateSchedule)get(end); }

  /**
   * Set the {@code end} property.
   * @see #end
   */
  public void setEnd(BDateSchedule v) { set(end, v, null); }

  //endregion Property "end"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDateRangeSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BDateRangeSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public boolean isEffective(BAbsTime arg)
  {
    if (getAlwaysEffective()) return true;
    BDateSchedule str = getStart();
    BDateSchedule end = getEnd();
    int strTo = str.compareTo(arg);
    int endTo = end.compareTo(arg);
    int tmp = str.getYear();
    int argYear = arg.getYear();
    if ((tmp >= 0) && (tmp > argYear))
      return false;
    tmp = end.getYear();
    if ((tmp >= 0) && (tmp < argYear))
      return false;
    if (str.compareTo(end) <= 0)
      return (strTo <= 0) && (endTo >= 0);
    return (strTo <= 0) || (endTo >= 0);
  }

  @Override
  public BAbsTime nextEvent(BAbsTime t)
  {
    BAbsTime startNext = getStart().nextEvent(t);
    BAbsTime endNext = getEnd().nextEvent(t);
    if (startNext == null)
      return endNext;
    else if (endNext == null)
      return startNext;
    else if (startNext.isBefore(endNext))
      return startNext;
    else
      return endNext;
  }

  @Override
  public String toString(Context cx)
  {
    String s = getStart().criteriaString(cx);
    String e = getEnd().criteriaString(cx);
    Lexicon l = Lexicon.make(BAbstractSchedule.class);
    return l.get("type.dateRangeSchedule") + ": " + s + " - " + e;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
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

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BDateRangeSchedule
