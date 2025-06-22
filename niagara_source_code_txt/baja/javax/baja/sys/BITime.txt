/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BITime represents a time of day which is independent 
 * of any date in the past or future.
 *
 * @author    Mike Jarmy
 * @creation  17 Mar 10
 * @version   $Revision: 1$ $Date: 3/18/10 12:46:19 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BITime
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BITime(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BITime.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * @return The hour: 0-23.
   */
  public int getHour();

  /**
   * @return The minute: 0-59.
   */
  public int getMinute();

  /**
   * @return The seconds: 0-59.
   */
  public int getSecond();

  /**
   * @return The millisecond: 0-999.
   */
  public int getMillisecond();
          
  /**
   * Milliseconds since the start of the day.
   */
  public long getTimeOfDayMillis();
}
