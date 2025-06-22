/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BStatusEffect.
 *
 * @author    Brian Frank
 * @creation  15 Nov 04
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("color"),
    @Range("colorAndBlink")
  }
)
public final class BStatusEffect
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.enums.BStatusEffect(102146476)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for color. */
  public static final int COLOR = 1;
  /** Ordinal value for colorAndBlink. */
  public static final int COLOR_AND_BLINK = 2;

  /** BStatusEffect constant for none. */
  public static final BStatusEffect none = new BStatusEffect(NONE);
  /** BStatusEffect constant for color. */
  public static final BStatusEffect color = new BStatusEffect(COLOR);
  /** BStatusEffect constant for colorAndBlink. */
  public static final BStatusEffect colorAndBlink = new BStatusEffect(COLOR_AND_BLINK);

  /** Factory method with ordinal. */
  public static BStatusEffect make(int ordinal)
  {
    return (BStatusEffect)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BStatusEffect make(String tag)
  {
    return (BStatusEffect)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BStatusEffect(int ordinal)
  {
    super(ordinal);
  }

  public static final BStatusEffect DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusEffect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
}
