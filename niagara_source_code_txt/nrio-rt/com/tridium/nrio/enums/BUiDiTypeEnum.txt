/**
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BUiDiTypeEnum class provides enumeration of Universal Digital Input types
 *
 * @author    Andy Saunders
 * @creation  20 June 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$
 * @since     Niagara 3.0 andi 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("di_Normal"),
    @Range("di_HighSpeed")
  }
)
public final class BUiDiTypeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BUiDiTypeEnum(3902061505)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for di_Normal. */
  public static final int DI_NORMAL = 0;
  /** Ordinal value for di_HighSpeed. */
  public static final int DI_HIGH_SPEED = 1;

  /** BUiDiTypeEnum constant for di_Normal. */
  public static final BUiDiTypeEnum di_Normal = new BUiDiTypeEnum(DI_NORMAL);
  /** BUiDiTypeEnum constant for di_HighSpeed. */
  public static final BUiDiTypeEnum di_HighSpeed = new BUiDiTypeEnum(DI_HIGH_SPEED);

  /** Factory method with ordinal. */
  public static BUiDiTypeEnum make(int ordinal)
  {
    return (BUiDiTypeEnum)di_Normal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BUiDiTypeEnum make(String tag)
  {
    return (BUiDiTypeEnum)di_Normal.getRange().get(tag);
  }

  /** Private constructor. */
  private BUiDiTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BUiDiTypeEnum DEFAULT = di_Normal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUiDiTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
