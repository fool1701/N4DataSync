/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history.ext;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BStruct;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * An active period defines an active state by some time based criteria.
 *
 * @author    John Sublett
 * @creation  19 Nov 2004
 * @version   $Revision: 3$ $Date: 12/1/04 10:50:32 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BActivePeriod
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BActivePeriod(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BActivePeriod.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Does the active period always return true for isActive() with its current configuration?
   */
  public abstract boolean isAlwaysActive();

  /**
   * Does the active period always return false for isActive() with its current configuration?
   */
  public abstract boolean isNeverActive();

  /**
   * Returns true if the conditions for history collection to
   * be enabled are met, false otherwise.  Determination is based
   * on the given timestamp.
   */
  public abstract boolean isActive(BAbsTime timestamp);

  /**
   * Get the start time of the active period that includes the specified time.
   *
   * @param time A time that is included in the active period.  That is, isActive(time)
   *   return true.
   * @return Returns the start time of the period that includes the specified time.  If the
   *   specified time is not in an active period, null is returned.
   */
  public abstract BAbsTime getActiveStart(BAbsTime time);

  /**
   * Get the end time of the active period that includes the specified time.
   *
   * @param time A time that is included in the active period.  That is, isActive(time)
   *   return true.
   * @return Returns the end time of the period that includes the specified time.  If the
   *   specified time is not in an active period, null is returned.
   */
  public abstract BAbsTime getActiveEnd(BAbsTime time);

  /**
   * Get the start of the next active period
   */
  public abstract BAbsTime getNextActive(BAbsTime time);

  /**
   * Get the end of the next active period.  If the specified
   * time is in an active period, the end of the period is returned.
   * If the specified time is not in an effective period, the
   * end of the next active period is returned.
   */
  public abstract BAbsTime getNextInactive(BAbsTime time);

}
