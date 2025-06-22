/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BStatusToBrushMode
 *
 * @author    Brian Frank
 * @creation  1 Dec 00
 * @version   $Revision$ $Date: 23-Mar-05 11:29:07 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("background"),
    @Range("foreground")
  }
)
public final class BStatusToBrushMode
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.enums.BStatusToBrushMode(614878426)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for background. */
  public static final int BACKGROUND = 0;
  /** Ordinal value for foreground. */
  public static final int FOREGROUND = 1;

  /** BStatusToBrushMode constant for background. */
  public static final BStatusToBrushMode background = new BStatusToBrushMode(BACKGROUND);
  /** BStatusToBrushMode constant for foreground. */
  public static final BStatusToBrushMode foreground = new BStatusToBrushMode(FOREGROUND);

  /** Factory method with ordinal. */
  public static BStatusToBrushMode make(int ordinal)
  {
    return (BStatusToBrushMode)background.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BStatusToBrushMode make(String tag)
  {
    return (BStatusToBrushMode)background.getRange().get(tag);
  }

  /** Private constructor. */
  private BStatusToBrushMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BStatusToBrushMode DEFAULT = background;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusToBrushMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
