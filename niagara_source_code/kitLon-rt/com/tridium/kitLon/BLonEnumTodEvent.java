/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitLon;

import javax.baja.lonworks.enums.BLonOccupancyEnum;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;

/**
 * BLonEnumTodEvent - takes input from a Niagara EnumSchedule to update a SnvtTodEvent.
 *
 * @author Robert Adams
 * @creation 3/23/2015
 * @since Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "currentState",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum(BLonOccupancyEnum.DEFAULT)",
  override = true
)
@NiagaraProperty(
  name = "nextState",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum(BLonOccupancyEnum.DEFAULT)",
  override = true
)
public class BLonEnumTodEvent
  extends BLonTodEvent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitLon.BLonEnumTodEvent(2521426864)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "currentState"

  /**
   * Slot for the {@code currentState} property.
   * @see #getCurrentState
   * @see #setCurrentState
   */
  public static final Property currentState = newProperty(0, new BStatusEnum(BLonOccupancyEnum.DEFAULT), null);

  //endregion Property "currentState"

  //region Property "nextState"

  /**
   * Slot for the {@code nextState} property.
   * @see #getNextState
   * @see #setNextState
   */
  public static final Property nextState = newProperty(0, new BStatusEnum(BLonOccupancyEnum.DEFAULT), null);

  //endregion Property "nextState"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonEnumTodEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  // Called from BLonTodEvent when setting values in SnvtTodEvent
  BLonOccupancyEnum makeLonOccEnum(BStatusValue v)
  {
    BStatusEnum en = (BStatusEnum)v;
    return BLonOccupancyEnum.make(en.getValue().getOrdinal());
  }


}
