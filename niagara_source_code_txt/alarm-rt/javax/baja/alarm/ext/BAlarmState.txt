/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import javax.baja.alarm.*;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BAlarmState is an BEnum that represents valid Baja alarm states
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 10$ $Date: 3/30/05 11:21:46 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("normal"),
    @Range("fault"),
    @Range("offnormal"),
    @Range("highLimit"),
    @Range("lowLimit")
  }
)
public final class BAlarmState
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BAlarmState(3446447957)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for normal. */
  public static final int NORMAL = 0;
  /** Ordinal value for fault. */
  public static final int FAULT = 1;
  /** Ordinal value for offnormal. */
  public static final int OFFNORMAL = 2;
  /** Ordinal value for highLimit. */
  public static final int HIGH_LIMIT = 3;
  /** Ordinal value for lowLimit. */
  public static final int LOW_LIMIT = 4;

  /** BAlarmState constant for normal. */
  public static final BAlarmState normal = new BAlarmState(NORMAL);
  /** BAlarmState constant for fault. */
  public static final BAlarmState fault = new BAlarmState(FAULT);
  /** BAlarmState constant for offnormal. */
  public static final BAlarmState offnormal = new BAlarmState(OFFNORMAL);
  /** BAlarmState constant for highLimit. */
  public static final BAlarmState highLimit = new BAlarmState(HIGH_LIMIT);
  /** BAlarmState constant for lowLimit. */
  public static final BAlarmState lowLimit = new BAlarmState(LOW_LIMIT);

  /** Factory method with ordinal. */
  public static BAlarmState make(int ordinal)
  {
    return (BAlarmState)normal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAlarmState make(String tag)
  {
    return (BAlarmState)normal.getRange().get(tag);
  }

  /** Private constructor. */
  private BAlarmState(int ordinal)
  {
    super(ordinal);
  }

  public static final BAlarmState DEFAULT = normal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /*********************************************
  *  Convenience method.  Returns true if and only
  *  if this instance is not equal to BAlarmState.normal
  **********************************************/
  public boolean isInAlarm()
  {
    return (this != normal);
  }

  /*********************************************
  *  Convenience method.  Returns true if and only
  *  if this instance is not equal to
  *  BAlarmState.normal or BAlarmState.fault
  **********************************************/
  public boolean isOffnormal()
  {
    return ((this != normal) && (this != fault));
  }

  /**********************************************
  *  Returns the alarm transition type associated
  * with a change to this state
  **********************************************/
  /*public BAlarmTransition getTransitionType()
  {
    if (this == normal) return BAlarmTransition.toNormal;
    if (this == fault) return BAlarmTransition.toFault;
    return BAlarmTransition.toOffnormal;
  }*/
}
