/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * The Raise Lower function enumeration
 * 
 * @author    Gareth Johnson
 * @creation  30 Nov 2006
 * @version   $Revision: 1$ $Date: 01/29/2007 12:20 AM$
 * @since     Niagara 3.4
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("offState"),
    @Range("staticState"),
    @Range("lowerState"),
    @Range("raiseState"),
    @Range("resetRaiseState"),
    @Range("resetLowerState")
  }
)
public final class BRaiseLowerFunction extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BRaiseLowerFunction(489668598)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for offState. */
  public static final int OFF_STATE = 0;
  /** Ordinal value for staticState. */
  public static final int STATIC_STATE = 1;
  /** Ordinal value for lowerState. */
  public static final int LOWER_STATE = 2;
  /** Ordinal value for raiseState. */
  public static final int RAISE_STATE = 3;
  /** Ordinal value for resetRaiseState. */
  public static final int RESET_RAISE_STATE = 4;
  /** Ordinal value for resetLowerState. */
  public static final int RESET_LOWER_STATE = 5;

  /** BRaiseLowerFunction constant for offState. */
  public static final BRaiseLowerFunction offState = new BRaiseLowerFunction(OFF_STATE);
  /** BRaiseLowerFunction constant for staticState. */
  public static final BRaiseLowerFunction staticState = new BRaiseLowerFunction(STATIC_STATE);
  /** BRaiseLowerFunction constant for lowerState. */
  public static final BRaiseLowerFunction lowerState = new BRaiseLowerFunction(LOWER_STATE);
  /** BRaiseLowerFunction constant for raiseState. */
  public static final BRaiseLowerFunction raiseState = new BRaiseLowerFunction(RAISE_STATE);
  /** BRaiseLowerFunction constant for resetRaiseState. */
  public static final BRaiseLowerFunction resetRaiseState = new BRaiseLowerFunction(RESET_RAISE_STATE);
  /** BRaiseLowerFunction constant for resetLowerState. */
  public static final BRaiseLowerFunction resetLowerState = new BRaiseLowerFunction(RESET_LOWER_STATE);

  /** Factory method with ordinal. */
  public static BRaiseLowerFunction make(int ordinal)
  {
    return (BRaiseLowerFunction)offState.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BRaiseLowerFunction make(String tag)
  {
    return (BRaiseLowerFunction)offState.getRange().get(tag);
  }

  /** Private constructor. */
  private BRaiseLowerFunction(int ordinal)
  {
    super(ordinal);
  }

  public static final BRaiseLowerFunction DEFAULT = offState;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRaiseLowerFunction.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
