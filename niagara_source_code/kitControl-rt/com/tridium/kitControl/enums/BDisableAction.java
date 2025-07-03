/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BDisableAction is a BEnum containing values for the action that
 * should be taken when the loop is disabled. 
 * and reverse action PID loop logic
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 7$ $Date: 03-Jun-04 1:11:20 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("maxValue"),
    @Range("minValue"),
    @Range("hold"),
    @Range("zero")
  }
)
public final class BDisableAction
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BDisableAction(1175135246)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for maxValue. */
  public static final int MAX_VALUE = 0;
  /** Ordinal value for minValue. */
  public static final int MIN_VALUE = 1;
  /** Ordinal value for hold. */
  public static final int HOLD = 2;
  /** Ordinal value for zero. */
  public static final int ZERO = 3;

  /** BDisableAction constant for maxValue. */
  public static final BDisableAction maxValue = new BDisableAction(MAX_VALUE);
  /** BDisableAction constant for minValue. */
  public static final BDisableAction minValue = new BDisableAction(MIN_VALUE);
  /** BDisableAction constant for hold. */
  public static final BDisableAction hold = new BDisableAction(HOLD);
  /** BDisableAction constant for zero. */
  public static final BDisableAction zero = new BDisableAction(ZERO);

  /** Factory method with ordinal. */
  public static BDisableAction make(int ordinal)
  {
    return (BDisableAction)maxValue.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BDisableAction make(String tag)
  {
    return (BDisableAction)maxValue.getRange().get(tag);
  }

  /** Private constructor. */
  private BDisableAction(int ordinal)
  {
    super(ordinal);
  }

  public static final BDisableAction DEFAULT = maxValue;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDisableAction.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
