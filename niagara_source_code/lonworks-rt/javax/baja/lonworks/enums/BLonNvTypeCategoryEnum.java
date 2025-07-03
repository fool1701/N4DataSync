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
 * The BLonNvTypeCategoryEnum class This file defines the
 * enumeration to be used with SNVT_hvac_type.
 *
 * @author    Robert Adams
 * @creation  10 April 02
 * @version   $Revision: 1$ $Date: 12/11/00 8:13:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "nvtCatInitial", ordinal = 0),
    @Range(value = "nvtCatSignedChar", ordinal = 1),
    @Range(value = "nvtCatUnsignedChar", ordinal = 2),
    @Range(value = "nvtCatSignedShort", ordinal = 3),
    @Range(value = "nvtCatUnsignedShort", ordinal = 4),
    @Range(value = "nvtCatSignedLong", ordinal = 5),
    @Range(value = "nvtCatUnsignedLong", ordinal = 6),
    @Range(value = "nvtCatEnum", ordinal = 7),
    @Range(value = "nvtCatArray", ordinal = 8),
    @Range(value = "nvtCatStruct", ordinal = 9),
    @Range(value = "nvtCatUnion", ordinal = 10),
    @Range(value = "nvtCatBitfield", ordinal = 11),
    @Range(value = "nvtCatFloat", ordinal = 12),
    @Range(value = "nvtCatSignedQuad", ordinal = 13),
    @Range(value = "nvtCatReference", ordinal = 14),
    @Range(value = "nvtCatNul", ordinal = -1)
  }
)
public final class BLonNvTypeCategoryEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonNvTypeCategoryEnum(628037117)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for nvtCatInitial. */
  public static final int NVT_CAT_INITIAL = 0;
  /** Ordinal value for nvtCatSignedChar. */
  public static final int NVT_CAT_SIGNED_CHAR = 1;
  /** Ordinal value for nvtCatUnsignedChar. */
  public static final int NVT_CAT_UNSIGNED_CHAR = 2;
  /** Ordinal value for nvtCatSignedShort. */
  public static final int NVT_CAT_SIGNED_SHORT = 3;
  /** Ordinal value for nvtCatUnsignedShort. */
  public static final int NVT_CAT_UNSIGNED_SHORT = 4;
  /** Ordinal value for nvtCatSignedLong. */
  public static final int NVT_CAT_SIGNED_LONG = 5;
  /** Ordinal value for nvtCatUnsignedLong. */
  public static final int NVT_CAT_UNSIGNED_LONG = 6;
  /** Ordinal value for nvtCatEnum. */
  public static final int NVT_CAT_ENUM = 7;
  /** Ordinal value for nvtCatArray. */
  public static final int NVT_CAT_ARRAY = 8;
  /** Ordinal value for nvtCatStruct. */
  public static final int NVT_CAT_STRUCT = 9;
  /** Ordinal value for nvtCatUnion. */
  public static final int NVT_CAT_UNION = 10;
  /** Ordinal value for nvtCatBitfield. */
  public static final int NVT_CAT_BITFIELD = 11;
  /** Ordinal value for nvtCatFloat. */
  public static final int NVT_CAT_FLOAT = 12;
  /** Ordinal value for nvtCatSignedQuad. */
  public static final int NVT_CAT_SIGNED_QUAD = 13;
  /** Ordinal value for nvtCatReference. */
  public static final int NVT_CAT_REFERENCE = 14;
  /** Ordinal value for nvtCatNul. */
  public static final int NVT_CAT_NUL = -1;

  /** BLonNvTypeCategoryEnum constant for nvtCatInitial. */
  public static final BLonNvTypeCategoryEnum nvtCatInitial = new BLonNvTypeCategoryEnum(NVT_CAT_INITIAL);
  /** BLonNvTypeCategoryEnum constant for nvtCatSignedChar. */
  public static final BLonNvTypeCategoryEnum nvtCatSignedChar = new BLonNvTypeCategoryEnum(NVT_CAT_SIGNED_CHAR);
  /** BLonNvTypeCategoryEnum constant for nvtCatUnsignedChar. */
  public static final BLonNvTypeCategoryEnum nvtCatUnsignedChar = new BLonNvTypeCategoryEnum(NVT_CAT_UNSIGNED_CHAR);
  /** BLonNvTypeCategoryEnum constant for nvtCatSignedShort. */
  public static final BLonNvTypeCategoryEnum nvtCatSignedShort = new BLonNvTypeCategoryEnum(NVT_CAT_SIGNED_SHORT);
  /** BLonNvTypeCategoryEnum constant for nvtCatUnsignedShort. */
  public static final BLonNvTypeCategoryEnum nvtCatUnsignedShort = new BLonNvTypeCategoryEnum(NVT_CAT_UNSIGNED_SHORT);
  /** BLonNvTypeCategoryEnum constant for nvtCatSignedLong. */
  public static final BLonNvTypeCategoryEnum nvtCatSignedLong = new BLonNvTypeCategoryEnum(NVT_CAT_SIGNED_LONG);
  /** BLonNvTypeCategoryEnum constant for nvtCatUnsignedLong. */
  public static final BLonNvTypeCategoryEnum nvtCatUnsignedLong = new BLonNvTypeCategoryEnum(NVT_CAT_UNSIGNED_LONG);
  /** BLonNvTypeCategoryEnum constant for nvtCatEnum. */
  public static final BLonNvTypeCategoryEnum nvtCatEnum = new BLonNvTypeCategoryEnum(NVT_CAT_ENUM);
  /** BLonNvTypeCategoryEnum constant for nvtCatArray. */
  public static final BLonNvTypeCategoryEnum nvtCatArray = new BLonNvTypeCategoryEnum(NVT_CAT_ARRAY);
  /** BLonNvTypeCategoryEnum constant for nvtCatStruct. */
  public static final BLonNvTypeCategoryEnum nvtCatStruct = new BLonNvTypeCategoryEnum(NVT_CAT_STRUCT);
  /** BLonNvTypeCategoryEnum constant for nvtCatUnion. */
  public static final BLonNvTypeCategoryEnum nvtCatUnion = new BLonNvTypeCategoryEnum(NVT_CAT_UNION);
  /** BLonNvTypeCategoryEnum constant for nvtCatBitfield. */
  public static final BLonNvTypeCategoryEnum nvtCatBitfield = new BLonNvTypeCategoryEnum(NVT_CAT_BITFIELD);
  /** BLonNvTypeCategoryEnum constant for nvtCatFloat. */
  public static final BLonNvTypeCategoryEnum nvtCatFloat = new BLonNvTypeCategoryEnum(NVT_CAT_FLOAT);
  /** BLonNvTypeCategoryEnum constant for nvtCatSignedQuad. */
  public static final BLonNvTypeCategoryEnum nvtCatSignedQuad = new BLonNvTypeCategoryEnum(NVT_CAT_SIGNED_QUAD);
  /** BLonNvTypeCategoryEnum constant for nvtCatReference. */
  public static final BLonNvTypeCategoryEnum nvtCatReference = new BLonNvTypeCategoryEnum(NVT_CAT_REFERENCE);
  /** BLonNvTypeCategoryEnum constant for nvtCatNul. */
  public static final BLonNvTypeCategoryEnum nvtCatNul = new BLonNvTypeCategoryEnum(NVT_CAT_NUL);

  /** Factory method with ordinal. */
  public static BLonNvTypeCategoryEnum make(int ordinal)
  {
    return (BLonNvTypeCategoryEnum)nvtCatInitial.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonNvTypeCategoryEnum make(String tag)
  {
    return (BLonNvTypeCategoryEnum)nvtCatInitial.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonNvTypeCategoryEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonNvTypeCategoryEnum DEFAULT = nvtCatInitial;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonNvTypeCategoryEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
