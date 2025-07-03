/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BLonMotorStateEnum represents Lonworks standard enumeration MotorStateT.
 *
 * @author    Robert Adams
 * @creation  12 Jan 01
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:32 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "motorStopped", ordinal = 0),
    @Range(value = "motorStarting", ordinal = 1),
    @Range(value = "motorAccelerating", ordinal = 2),
    @Range(value = "motorAtStandby", ordinal = 3),
    @Range(value = "motorAtNormal", ordinal = 4),
    @Range(value = "motorAtReference", ordinal = 5),
    @Range(value = "motorDecelerating", ordinal = 6),
    @Range(value = "motorStopping", ordinal = 7),
    @Range(value = "motorNul", ordinal = -1)
  }
)
public final class BLonMotorStateEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonMotorStateEnum(3456273085)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for motorStopped. */
  public static final int MOTOR_STOPPED = 0;
  /** Ordinal value for motorStarting. */
  public static final int MOTOR_STARTING = 1;
  /** Ordinal value for motorAccelerating. */
  public static final int MOTOR_ACCELERATING = 2;
  /** Ordinal value for motorAtStandby. */
  public static final int MOTOR_AT_STANDBY = 3;
  /** Ordinal value for motorAtNormal. */
  public static final int MOTOR_AT_NORMAL = 4;
  /** Ordinal value for motorAtReference. */
  public static final int MOTOR_AT_REFERENCE = 5;
  /** Ordinal value for motorDecelerating. */
  public static final int MOTOR_DECELERATING = 6;
  /** Ordinal value for motorStopping. */
  public static final int MOTOR_STOPPING = 7;
  /** Ordinal value for motorNul. */
  public static final int MOTOR_NUL = -1;

  /** BLonMotorStateEnum constant for motorStopped. */
  public static final BLonMotorStateEnum motorStopped = new BLonMotorStateEnum(MOTOR_STOPPED);
  /** BLonMotorStateEnum constant for motorStarting. */
  public static final BLonMotorStateEnum motorStarting = new BLonMotorStateEnum(MOTOR_STARTING);
  /** BLonMotorStateEnum constant for motorAccelerating. */
  public static final BLonMotorStateEnum motorAccelerating = new BLonMotorStateEnum(MOTOR_ACCELERATING);
  /** BLonMotorStateEnum constant for motorAtStandby. */
  public static final BLonMotorStateEnum motorAtStandby = new BLonMotorStateEnum(MOTOR_AT_STANDBY);
  /** BLonMotorStateEnum constant for motorAtNormal. */
  public static final BLonMotorStateEnum motorAtNormal = new BLonMotorStateEnum(MOTOR_AT_NORMAL);
  /** BLonMotorStateEnum constant for motorAtReference. */
  public static final BLonMotorStateEnum motorAtReference = new BLonMotorStateEnum(MOTOR_AT_REFERENCE);
  /** BLonMotorStateEnum constant for motorDecelerating. */
  public static final BLonMotorStateEnum motorDecelerating = new BLonMotorStateEnum(MOTOR_DECELERATING);
  /** BLonMotorStateEnum constant for motorStopping. */
  public static final BLonMotorStateEnum motorStopping = new BLonMotorStateEnum(MOTOR_STOPPING);
  /** BLonMotorStateEnum constant for motorNul. */
  public static final BLonMotorStateEnum motorNul = new BLonMotorStateEnum(MOTOR_NUL);

  /** Factory method with ordinal. */
  public static BLonMotorStateEnum make(int ordinal)
  {
    return (BLonMotorStateEnum)motorStopped.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonMotorStateEnum make(String tag)
  {
    return (BLonMotorStateEnum)motorStopped.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonMotorStateEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonMotorStateEnum DEFAULT = motorStopped;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonMotorStateEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
