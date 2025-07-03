/*
 * Copyright 2020 Tridium, Inc.  All rights reserved.
 *
 */

package javax.baja.util;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A generic enum that can be used for monitoring the execution state of a
 * (typically long-running) operation.
 *
 * @author Ashutosh Chaturvrdi on 7/14/2020
 * @since Niagara 4.11
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "idle", ordinal = 0),
    @Range(value = "pending", ordinal = 1),
    @Range(value = "inProgress", ordinal = 2)
  }
)
public final class BExecutionState
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BExecutionState(270817838)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for idle. */
  public static final int IDLE = 0;
  /** Ordinal value for pending. */
  public static final int PENDING = 1;
  /** Ordinal value for inProgress. */
  public static final int IN_PROGRESS = 2;

  /** BExecutionState constant for idle. */
  public static final BExecutionState idle = new BExecutionState(IDLE);
  /** BExecutionState constant for pending. */
  public static final BExecutionState pending = new BExecutionState(PENDING);
  /** BExecutionState constant for inProgress. */
  public static final BExecutionState inProgress = new BExecutionState(IN_PROGRESS);

  /** Factory method with ordinal. */
  public static BExecutionState make(int ordinal)
  {
    return (BExecutionState)idle.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BExecutionState make(String tag)
  {
    return (BExecutionState)idle.getRange().get(tag);
  }

  /** Private constructor. */
  private BExecutionState(int ordinal)
  {
    super(ordinal);
  }

  public static final BExecutionState DEFAULT = idle;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExecutionState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
