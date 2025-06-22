/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BLonOverrideEnum class provides enumeration for SNVT_override
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:34 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "ovRetain", ordinal = 0),
    @Range(value = "ovSpecified", ordinal = 1),
    @Range(value = "ovDefault", ordinal = 2),
    @Range(value = "ovNul", ordinal = -1)
  }
)
public final class BLonOverrideEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonOverrideEnum(2367475354)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ovRetain. */
  public static final int OV_RETAIN = 0;
  /** Ordinal value for ovSpecified. */
  public static final int OV_SPECIFIED = 1;
  /** Ordinal value for ovDefault. */
  public static final int OV_DEFAULT = 2;
  /** Ordinal value for ovNul. */
  public static final int OV_NUL = -1;

  /** BLonOverrideEnum constant for ovRetain. */
  public static final BLonOverrideEnum ovRetain = new BLonOverrideEnum(OV_RETAIN);
  /** BLonOverrideEnum constant for ovSpecified. */
  public static final BLonOverrideEnum ovSpecified = new BLonOverrideEnum(OV_SPECIFIED);
  /** BLonOverrideEnum constant for ovDefault. */
  public static final BLonOverrideEnum ovDefault = new BLonOverrideEnum(OV_DEFAULT);
  /** BLonOverrideEnum constant for ovNul. */
  public static final BLonOverrideEnum ovNul = new BLonOverrideEnum(OV_NUL);

  /** Factory method with ordinal. */
  public static BLonOverrideEnum make(int ordinal)
  {
    return (BLonOverrideEnum)ovRetain.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonOverrideEnum make(String tag)
  {
    return (BLonOverrideEnum)ovRetain.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonOverrideEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonOverrideEnum DEFAULT = ovRetain;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonOverrideEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
