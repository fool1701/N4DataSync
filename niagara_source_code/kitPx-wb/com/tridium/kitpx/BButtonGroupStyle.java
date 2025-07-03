/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BButtonGroupStyle.
 *
 * @author    Andy Frank
 * @creation  15 May 07
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "radio", ordinal = 0),
    @Range(value = "normal", ordinal = 1)
  },
  defaultValue = "normal"
)
public final class BButtonGroupStyle
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BButtonGroupStyle(2190764913)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for radio. */
  public static final int RADIO = 0;
  /** Ordinal value for normal. */
  public static final int NORMAL = 1;

  /** BButtonGroupStyle constant for radio. */
  public static final BButtonGroupStyle radio = new BButtonGroupStyle(RADIO);
  /** BButtonGroupStyle constant for normal. */
  public static final BButtonGroupStyle normal = new BButtonGroupStyle(NORMAL);

  /** Factory method with ordinal. */
  public static BButtonGroupStyle make(int ordinal)
  {
    return (BButtonGroupStyle)radio.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BButtonGroupStyle make(String tag)
  {
    return (BButtonGroupStyle)radio.getRange().get(tag);
  }

  /** Private constructor. */
  private BButtonGroupStyle(int ordinal)
  {
    super(ordinal);
  }

  public static final BButtonGroupStyle DEFAULT = normal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BButtonGroupStyle.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
