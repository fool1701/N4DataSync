/**
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BDoDefaultValueSelect class provides enumeration to select the defalue value for DOs
 *
 * @author    Andy Saunders
 * @creation  27 Oct 2016
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("off"),
    @Range("on"),
    @Range("hold")
  }
)

public final class BDoDefaultValueSelect
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BDoDefaultValueSelect(3519738254)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for off. */
  public static final int OFF = 0;
  /** Ordinal value for on. */
  public static final int ON = 1;
  /** Ordinal value for hold. */
  public static final int HOLD = 2;

  /** BDoDefaultValueSelect constant for off. */
  public static final BDoDefaultValueSelect off = new BDoDefaultValueSelect(OFF);
  /** BDoDefaultValueSelect constant for on. */
  public static final BDoDefaultValueSelect on = new BDoDefaultValueSelect(ON);
  /** BDoDefaultValueSelect constant for hold. */
  public static final BDoDefaultValueSelect hold = new BDoDefaultValueSelect(HOLD);

  /** Factory method with ordinal. */
  public static BDoDefaultValueSelect make(int ordinal)
  {
    return (BDoDefaultValueSelect)off.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BDoDefaultValueSelect make(String tag)
  {
    return (BDoDefaultValueSelect)off.getRange().get(tag);
  }

  /** Private constructor. */
  private BDoDefaultValueSelect(int ordinal)
  {
    super(ordinal);
  }

  public static final BDoDefaultValueSelect DEFAULT = off;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDoDefaultValueSelect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
