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
 * The BLonFireTestEnum class provides enumeration for SNVT_fire_test
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:30 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "ftNormal", ordinal = 0),
    @Range(value = "ftReset", ordinal = 1),
    @Range(value = "ftTest", ordinal = 2),
    @Range(value = "ftNotest", ordinal = 3),
    @Range(value = "ftNul", ordinal = -1)
  }
)
public final class BLonFireTestEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonFireTestEnum(3467246982)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ftNormal. */
  public static final int FT_NORMAL = 0;
  /** Ordinal value for ftReset. */
  public static final int FT_RESET = 1;
  /** Ordinal value for ftTest. */
  public static final int FT_TEST = 2;
  /** Ordinal value for ftNotest. */
  public static final int FT_NOTEST = 3;
  /** Ordinal value for ftNul. */
  public static final int FT_NUL = -1;

  /** BLonFireTestEnum constant for ftNormal. */
  public static final BLonFireTestEnum ftNormal = new BLonFireTestEnum(FT_NORMAL);
  /** BLonFireTestEnum constant for ftReset. */
  public static final BLonFireTestEnum ftReset = new BLonFireTestEnum(FT_RESET);
  /** BLonFireTestEnum constant for ftTest. */
  public static final BLonFireTestEnum ftTest = new BLonFireTestEnum(FT_TEST);
  /** BLonFireTestEnum constant for ftNotest. */
  public static final BLonFireTestEnum ftNotest = new BLonFireTestEnum(FT_NOTEST);
  /** BLonFireTestEnum constant for ftNul. */
  public static final BLonFireTestEnum ftNul = new BLonFireTestEnum(FT_NUL);

  /** Factory method with ordinal. */
  public static BLonFireTestEnum make(int ordinal)
  {
    return (BLonFireTestEnum)ftNormal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonFireTestEnum make(String tag)
  {
    return (BLonFireTestEnum)ftNormal.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonFireTestEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonFireTestEnum DEFAULT = ftNormal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFireTestEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
