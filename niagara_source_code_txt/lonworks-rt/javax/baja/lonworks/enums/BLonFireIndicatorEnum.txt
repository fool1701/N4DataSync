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
 * The BLonFireIndicatorEnum class provides enumeration for SNVT_fire_indcte
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:29 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "fnUndefined", ordinal = 0),
    @Range(value = "fnStrobeU", ordinal = 1),
    @Range(value = "fnStrobeS", ordinal = 2),
    @Range(value = "fnHorn", ordinal = 3),
    @Range(value = "fnChime", ordinal = 4),
    @Range(value = "fnBell", ordinal = 5),
    @Range(value = "fnSounder", ordinal = 6),
    @Range(value = "fnSpeaker", ordinal = 7),
    @Range(value = "fnUniversal", ordinal = 8),
    @Range(value = "fnNul", ordinal = -1)
  }
)
public final class BLonFireIndicatorEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonFireIndicatorEnum(1519632603)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for fnUndefined. */
  public static final int FN_UNDEFINED = 0;
  /** Ordinal value for fnStrobeU. */
  public static final int FN_STROBE_U = 1;
  /** Ordinal value for fnStrobeS. */
  public static final int FN_STROBE_S = 2;
  /** Ordinal value for fnHorn. */
  public static final int FN_HORN = 3;
  /** Ordinal value for fnChime. */
  public static final int FN_CHIME = 4;
  /** Ordinal value for fnBell. */
  public static final int FN_BELL = 5;
  /** Ordinal value for fnSounder. */
  public static final int FN_SOUNDER = 6;
  /** Ordinal value for fnSpeaker. */
  public static final int FN_SPEAKER = 7;
  /** Ordinal value for fnUniversal. */
  public static final int FN_UNIVERSAL = 8;
  /** Ordinal value for fnNul. */
  public static final int FN_NUL = -1;

  /** BLonFireIndicatorEnum constant for fnUndefined. */
  public static final BLonFireIndicatorEnum fnUndefined = new BLonFireIndicatorEnum(FN_UNDEFINED);
  /** BLonFireIndicatorEnum constant for fnStrobeU. */
  public static final BLonFireIndicatorEnum fnStrobeU = new BLonFireIndicatorEnum(FN_STROBE_U);
  /** BLonFireIndicatorEnum constant for fnStrobeS. */
  public static final BLonFireIndicatorEnum fnStrobeS = new BLonFireIndicatorEnum(FN_STROBE_S);
  /** BLonFireIndicatorEnum constant for fnHorn. */
  public static final BLonFireIndicatorEnum fnHorn = new BLonFireIndicatorEnum(FN_HORN);
  /** BLonFireIndicatorEnum constant for fnChime. */
  public static final BLonFireIndicatorEnum fnChime = new BLonFireIndicatorEnum(FN_CHIME);
  /** BLonFireIndicatorEnum constant for fnBell. */
  public static final BLonFireIndicatorEnum fnBell = new BLonFireIndicatorEnum(FN_BELL);
  /** BLonFireIndicatorEnum constant for fnSounder. */
  public static final BLonFireIndicatorEnum fnSounder = new BLonFireIndicatorEnum(FN_SOUNDER);
  /** BLonFireIndicatorEnum constant for fnSpeaker. */
  public static final BLonFireIndicatorEnum fnSpeaker = new BLonFireIndicatorEnum(FN_SPEAKER);
  /** BLonFireIndicatorEnum constant for fnUniversal. */
  public static final BLonFireIndicatorEnum fnUniversal = new BLonFireIndicatorEnum(FN_UNIVERSAL);
  /** BLonFireIndicatorEnum constant for fnNul. */
  public static final BLonFireIndicatorEnum fnNul = new BLonFireIndicatorEnum(FN_NUL);

  /** Factory method with ordinal. */
  public static BLonFireIndicatorEnum make(int ordinal)
  {
    return (BLonFireIndicatorEnum)fnUndefined.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonFireIndicatorEnum make(String tag)
  {
    return (BLonFireIndicatorEnum)fnUndefined.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonFireIndicatorEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonFireIndicatorEnum DEFAULT = fnUndefined;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFireIndicatorEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
