/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonElementType class provides enumeration for the element type
 * as specified in <code>BLonElementQualifiers</code>. It indicates
 * the type of data for a particular data element when encoded in 
 * network byte format.
 *
 * @author    Robert Adams
 * @creation  14 Dec 00
 * @version   $Revision: 5$ $Date: 9/18/01 9:49:39 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
@NiagaraEnum(
  range = {
    @Range("c8"),
    @Range("s8"),
    @Range("u8"),
    @Range("s16"),
    @Range("u16"),
    @Range("s32"),
    @Range("b8"),
    @Range("e8"),
    @Range("f32"),
    @Range("eb"),
    @Range("esb"),
    @Range("bb"),
    @Range("ub"),
    @Range("sb"),
    @Range("st"),
    @Range("na"),
    @Range("u32"),
    @Range("f64"),
    @Range("s64"),
    @Range("u64")
  }
)
public final class BLonElementType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonElementType(3570962494)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  /** Ordinal value for c8. */
  public static final int C8 = 0;
  /** Ordinal value for s8. */
  public static final int S8 = 1;
  /** Ordinal value for u8. */
  public static final int U8 = 2;
  /** Ordinal value for s16. */
  public static final int S16 = 3;
  /** Ordinal value for u16. */
  public static final int U16 = 4;
  /** Ordinal value for s32. */
  public static final int S32 = 5;
  /** Ordinal value for b8. */
  public static final int B8 = 6;
  /** Ordinal value for e8. */
  public static final int E8 = 7;
  /** Ordinal value for f32. */
  public static final int F32 = 8;
  /** Ordinal value for eb. */
  public static final int EB = 9;
  /** Ordinal value for esb. */
  public static final int ESB = 10;
  /** Ordinal value for bb. */
  public static final int BB = 11;
  /** Ordinal value for ub. */
  public static final int UB = 12;
  /** Ordinal value for sb. */
  public static final int SB = 13;
  /** Ordinal value for st. */
  public static final int ST = 14;
  /** Ordinal value for na. */
  public static final int NA = 15;
  /** Ordinal value for u32. */
  public static final int U32 = 16;
  /** Ordinal value for f64. */
  public static final int F64 = 17;
  /** Ordinal value for s64. */
  public static final int S64 = 18;
  /** Ordinal value for u64. */
  public static final int U64 = 19;

  /** BLonElementType constant for c8. */
  public static final BLonElementType c8 = new BLonElementType(C8);
  /** BLonElementType constant for s8. */
  public static final BLonElementType s8 = new BLonElementType(S8);
  /** BLonElementType constant for u8. */
  public static final BLonElementType u8 = new BLonElementType(U8);
  /** BLonElementType constant for s16. */
  public static final BLonElementType s16 = new BLonElementType(S16);
  /** BLonElementType constant for u16. */
  public static final BLonElementType u16 = new BLonElementType(U16);
  /** BLonElementType constant for s32. */
  public static final BLonElementType s32 = new BLonElementType(S32);
  /** BLonElementType constant for b8. */
  public static final BLonElementType b8 = new BLonElementType(B8);
  /** BLonElementType constant for e8. */
  public static final BLonElementType e8 = new BLonElementType(E8);
  /** BLonElementType constant for f32. */
  public static final BLonElementType f32 = new BLonElementType(F32);
  /** BLonElementType constant for eb. */
  public static final BLonElementType eb = new BLonElementType(EB);
  /** BLonElementType constant for esb. */
  public static final BLonElementType esb = new BLonElementType(ESB);
  /** BLonElementType constant for bb. */
  public static final BLonElementType bb = new BLonElementType(BB);
  /** BLonElementType constant for ub. */
  public static final BLonElementType ub = new BLonElementType(UB);
  /** BLonElementType constant for sb. */
  public static final BLonElementType sb = new BLonElementType(SB);
  /** BLonElementType constant for st. */
  public static final BLonElementType st = new BLonElementType(ST);
  /** BLonElementType constant for na. */
  public static final BLonElementType na = new BLonElementType(NA);
  /** BLonElementType constant for u32. */
  public static final BLonElementType u32 = new BLonElementType(U32);
  /** BLonElementType constant for f64. */
  public static final BLonElementType f64 = new BLonElementType(F64);
  /** BLonElementType constant for s64. */
  public static final BLonElementType s64 = new BLonElementType(S64);
  /** BLonElementType constant for u64. */
  public static final BLonElementType u64 = new BLonElementType(U64);

  /** Factory method with ordinal. */
  public static BLonElementType make(int ordinal)
  {
    return (BLonElementType)c8.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonElementType make(String tag)
  {
    return (BLonElementType)c8.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonElementType(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonElementType DEFAULT = c8;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonElementType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
