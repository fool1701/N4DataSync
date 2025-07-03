/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A BFrozenEnum that contains the following types:
 *  unversalInput, analogOutput, booleanOutput
 *
 * @author    Bill Smith
 * @creation  19 Jun 2002
 * @version   $Revision: 1$ $Date: 3/21/2003 1:29:18 PM$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "count", ordinal = 0),
    @Range(value = "rate", ordinal = 1)
  }
)
public final class BNrio16CounterSelectEnum extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BNrio16CounterSelectEnum(54403364)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for count. */
  public static final int COUNT = 0;
  /** Ordinal value for rate. */
  public static final int RATE = 1;

  /** BNrio16CounterSelectEnum constant for count. */
  public static final BNrio16CounterSelectEnum count = new BNrio16CounterSelectEnum(COUNT);
  /** BNrio16CounterSelectEnum constant for rate. */
  public static final BNrio16CounterSelectEnum rate = new BNrio16CounterSelectEnum(RATE);

  /** Factory method with ordinal. */
  public static BNrio16CounterSelectEnum make(int ordinal)
  {
    return (BNrio16CounterSelectEnum)count.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BNrio16CounterSelectEnum make(String tag)
  {
    return (BNrio16CounterSelectEnum)count.getRange().get(tag);
  }

  /** Private constructor. */
  private BNrio16CounterSelectEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BNrio16CounterSelectEnum DEFAULT = count;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio16CounterSelectEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
