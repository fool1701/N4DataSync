/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BStopRun is an BEnum that represents valid Baja StopRun
 * values
 *
 * @author    Danny Wahlquist
 * @creation  25 Oct 04
 * @version   $Revision: 11$ $Date: 03-Jun-04 1:11:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("stop"),
    @Range("run")
  }
)
public final class BStopRun
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BStopRun(2843918212)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for stop. */
  public static final int STOP = 0;
  /** Ordinal value for run. */
  public static final int RUN = 1;

  /** BStopRun constant for stop. */
  public static final BStopRun stop = new BStopRun(STOP);
  /** BStopRun constant for run. */
  public static final BStopRun run = new BStopRun(RUN);

  /** Factory method with ordinal. */
  public static BStopRun make(int ordinal)
  {
    return (BStopRun)stop.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BStopRun make(String tag)
  {
    return (BStopRun)stop.getRange().get(tag);
  }

  /** Private constructor. */
  private BStopRun(int ordinal)
  {
    super(ordinal);
  }

  public static final BStopRun DEFAULT = stop;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStopRun.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /*********************************************
  *  Convenience method.  Returns true if and only
  *  if the current value of the enumeration
  *  is NOT equal to NO_FAULT_DETECTED.
  **********************************************/
  public final boolean isFault()
  {
    return (this != stop);
  }

}
