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
 * BMouseOverEffect.
 *
 * @author    Andy Frank       
 * @creation  08 Nov 04
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("outline"),
    @Range("highlight")
  }
)
public final class BMouseOverEffect
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.enums.BMouseOverEffect(2988591947)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for outline. */
  public static final int OUTLINE = 1;
  /** Ordinal value for highlight. */
  public static final int HIGHLIGHT = 2;

  /** BMouseOverEffect constant for none. */
  public static final BMouseOverEffect none = new BMouseOverEffect(NONE);
  /** BMouseOverEffect constant for outline. */
  public static final BMouseOverEffect outline = new BMouseOverEffect(OUTLINE);
  /** BMouseOverEffect constant for highlight. */
  public static final BMouseOverEffect highlight = new BMouseOverEffect(HIGHLIGHT);

  /** Factory method with ordinal. */
  public static BMouseOverEffect make(int ordinal)
  {
    return (BMouseOverEffect)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BMouseOverEffect make(String tag)
  {
    return (BMouseOverEffect)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BMouseOverEffect(int ordinal)
  {
    super(ordinal);
  }

  public static final BMouseOverEffect DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMouseOverEffect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
}
