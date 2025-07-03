/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BLoopAction is a BEnum containing values for direct (direct) 
 * and reverse action PID loop logic
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 10$ $Date: 03-Jun-04 1:11:25 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("direct"),
    @Range("reverse")
  }
)
public final class BLoopAction
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BLoopAction(457396846)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for direct. */
  public static final int DIRECT = 0;
  /** Ordinal value for reverse. */
  public static final int REVERSE = 1;

  /** BLoopAction constant for direct. */
  public static final BLoopAction direct = new BLoopAction(DIRECT);
  /** BLoopAction constant for reverse. */
  public static final BLoopAction reverse = new BLoopAction(REVERSE);

  /** Factory method with ordinal. */
  public static BLoopAction make(int ordinal)
  {
    return (BLoopAction)direct.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLoopAction make(String tag)
  {
    return (BLoopAction)direct.getRange().get(tag);
  }

  /** Private constructor. */
  private BLoopAction(int ordinal)
  {
    super(ordinal);
  }

  public static final BLoopAction DEFAULT = direct;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLoopAction.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



}
