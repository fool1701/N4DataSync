/*
 * Copyright 2002 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BInteger;
import javax.baja.sys.BMonth;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Effective all years (always effective) or just a single year.
 * @author Aaron Hansen
 * @creation Oct 2002
 * @version $Revision: 10$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "year",
  type = "int",
  defaultValue = "2000",
  flags = Flags.USER_DEFINED_1
)
public class BYearSchedule
  extends BAbstractSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BYearSchedule(1412245295)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "year"

  /**
   * Slot for the {@code year} property.
   * @see #getYear
   * @see #setYear
   */
  public static final Property year = newProperty(Flags.USER_DEFINED_1, 2000, null);

  /**
   * Get the {@code year} property.
   * @see #year
   */
  public int getYear() { return getInt(year); }

  /**
   * Set the {@code year} property.
   * @see #year
   */
  public void setYear(int v) { setInt(year, v, null); }

  //endregion Property "year"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BYearSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BYearSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  public BYearSchedule initYear(int yr)
  {
    initYear(yr, null);
    return this;
  }

  public BYearSchedule initYear(int yr, Context cx)
  {
    set(year, BInteger.make(yr), cx);
    return this;
  }

  @Override
  public boolean isEffective(BAbsTime at)
  {
    if (getAlwaysEffective())
      return true;
    return getYear() == at.getYear();
  }

  @Override
  public BAbsTime nextEvent(BAbsTime at)
  {
    if (getAlwaysEffective())
      return null;
    int parmYear = at.getYear();
    int myYear = getYear();
    if (parmYear > myYear)
      return null;
    if (parmYear < myYear)
      return BAbsTime.make(myYear,BMonth.january,1,0,0,0,0,at.getTimeZone());
    return BAbsTime.make(++myYear,BMonth.january,1,0,0,0,0,at.getTimeZone());
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


}//BYearSchedule
