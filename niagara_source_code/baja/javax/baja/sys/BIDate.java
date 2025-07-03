/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIDate represents a specific day, month, and year.
 *
 * @author    Mike Jarmy
 * @creation  08 Mar 10
 * @version   $Revision: 2$ $Date: 3/25/10 2:48:57 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIDate
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIDate(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIDate.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Get Functions
////////////////////////////////////////////////////////////////

  /**
   * @return The year as a four digit integer (ie 2001).
   */
  public int getYear();

  /**
   * @return the month as a BMonth.
   */
  public BMonth getMonth();

  /**
   * @return The day: 1-31.
   */
  public int getDay();

  /**
   * @return the weekday as a BWeekday enum.
   */
  public BWeekday getWeekday();

  /**
   * Get the day of the year for this BIDate.  An
   * example is that Feb. 1, 2000 would return 32.  The
   * method does account for leap years.
   */
  public int getDayOfYear();

  /**
   * Return if today is Feb 29.
   */
  public boolean isLeapDay();
}
