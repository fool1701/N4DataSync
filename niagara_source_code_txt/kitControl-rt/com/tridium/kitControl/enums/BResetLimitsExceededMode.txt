/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This enum is used to specify the mode to use when either of the inputLowLimit
 * or inputHighLimit values for BReset is exceeded.
 *
 * When useExceededLimit is selected and inA exceeds the inputLowLimit or
 * inputHighLimit the exceeded limit is assigned to the out value.
 * The out status is propagated in the same as when the limits are not exceeded.
 * This is the default mode.
 *
 * When setStatusToNull is selected and inA exceeds the inputLowLimit or
 * inputHighLimit the exceeded limit is assigned to the out value.
 * The out status is then set to null.
 *
 * @author Robert Staley on 09 Sep 2021
 * @since Niagara 4.12
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("useExceededLimit"),
    @Range("setStatusToNull")
  },
  defaultValue = "useExceededLimit"
)
public final class BResetLimitsExceededMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BResetLimitsExceededMode(2311344826)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for useExceededLimit. */
  public static final int USE_EXCEEDED_LIMIT = 0;
  /** Ordinal value for setStatusToNull. */
  public static final int SET_STATUS_TO_NULL = 1;

  /** BResetLimitsExceededMode constant for useExceededLimit. */
  public static final BResetLimitsExceededMode useExceededLimit = new BResetLimitsExceededMode(USE_EXCEEDED_LIMIT);
  /** BResetLimitsExceededMode constant for setStatusToNull. */
  public static final BResetLimitsExceededMode setStatusToNull = new BResetLimitsExceededMode(SET_STATUS_TO_NULL);

  /** Factory method with ordinal. */
  public static BResetLimitsExceededMode make(int ordinal)
  {
    return (BResetLimitsExceededMode)useExceededLimit.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BResetLimitsExceededMode make(String tag)
  {
    return (BResetLimitsExceededMode)useExceededLimit.getRange().get(tag);
  }

  /** Private constructor. */
  private BResetLimitsExceededMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BResetLimitsExceededMode DEFAULT = useExceededLimit;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BResetLimitsExceededMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
