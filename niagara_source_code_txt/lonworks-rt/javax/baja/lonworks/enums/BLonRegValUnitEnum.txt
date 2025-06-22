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
 * The BLonRegValUnitEnum class provides enumeration for the
 * SNVT_reg_val and SNVT_reg_val_ts
 *
 * @author    Robert Adams
 * @creation  19 April 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:36 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "rvuNone", ordinal = 0),
    @Range(value = "rvuW", ordinal = 1),
    @Range(value = "rvuKw", ordinal = 2),
    @Range(value = "rvuMw", ordinal = 3),
    @Range(value = "rvuGw", ordinal = 4),
    @Range(value = "rvuVar", ordinal = 5),
    @Range(value = "rvuKvar", ordinal = 6),
    @Range(value = "rvuMvar", ordinal = 7),
    @Range(value = "rvuGvar", ordinal = 8),
    @Range(value = "rvuWh", ordinal = 9),
    @Range(value = "rvuKwh", ordinal = 10),
    @Range(value = "rvuMwh", ordinal = 11),
    @Range(value = "rvuGwn", ordinal = 12),
    @Range(value = "rvuVarh", ordinal = 13),
    @Range(value = "rvuKvarh", ordinal = 14),
    @Range(value = "rvuMvarh", ordinal = 15),
    @Range(value = "rvuGvarh", ordinal = 16),
    @Range(value = "rvuV", ordinal = 17),
    @Range(value = "rvuA", ordinal = 18),
    @Range(value = "rvuCosf", ordinal = 19),
    @Range(value = "rvuM3", ordinal = 20),
    @Range(value = "rvuL", ordinal = 21),
    @Range(value = "rvuMl", ordinal = 22),
    @Range(value = "rvuUsgal", ordinal = 23),
    @Range(value = "rvuGj", ordinal = 24),
    @Range(value = "rvuMj", ordinal = 25),
    @Range(value = "rvuMcal", ordinal = 26),
    @Range(value = "rvuKcal", ordinal = 27),
    @Range(value = "rvuMbtu", ordinal = 28),
    @Range(value = "rvuKbtu", ordinal = 29),
    @Range(value = "rvuMjh", ordinal = 30),
    @Range(value = "rvuMls", ordinal = 31),
    @Range(value = "rvuLs", ordinal = 32),
    @Range(value = "rvuM3s", ordinal = 33),
    @Range(value = "rvuC", ordinal = 34),
    @Range(value = "rvuLh", ordinal = 35),
    @Range(value = "rvuVa", ordinal = 36),
    @Range(value = "rvuKva", ordinal = 37),
    @Range(value = "rvuMva", ordinal = 38),
    @Range(value = "rvuGva", ordinal = 39),
    @Range(value = "rvuVah", ordinal = 40),
    @Range(value = "rvuKvah", ordinal = 41),
    @Range(value = "rvuMvah", ordinal = 42),
    @Range(value = "rvuGvah", ordinal = 43),
    @Range(value = "rvuNul", ordinal = -1)
  }
)
public final class BLonRegValUnitEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonRegValUnitEnum(588573188)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for rvuNone. */
  public static final int RVU_NONE = 0;
  /** Ordinal value for rvuW. */
  public static final int RVU_W = 1;
  /** Ordinal value for rvuKw. */
  public static final int RVU_KW = 2;
  /** Ordinal value for rvuMw. */
  public static final int RVU_MW = 3;
  /** Ordinal value for rvuGw. */
  public static final int RVU_GW = 4;
  /** Ordinal value for rvuVar. */
  public static final int RVU_VAR = 5;
  /** Ordinal value for rvuKvar. */
  public static final int RVU_KVAR = 6;
  /** Ordinal value for rvuMvar. */
  public static final int RVU_MVAR = 7;
  /** Ordinal value for rvuGvar. */
  public static final int RVU_GVAR = 8;
  /** Ordinal value for rvuWh. */
  public static final int RVU_WH = 9;
  /** Ordinal value for rvuKwh. */
  public static final int RVU_KWH = 10;
  /** Ordinal value for rvuMwh. */
  public static final int RVU_MWH = 11;
  /** Ordinal value for rvuGwn. */
  public static final int RVU_GWN = 12;
  /** Ordinal value for rvuVarh. */
  public static final int RVU_VARH = 13;
  /** Ordinal value for rvuKvarh. */
  public static final int RVU_KVARH = 14;
  /** Ordinal value for rvuMvarh. */
  public static final int RVU_MVARH = 15;
  /** Ordinal value for rvuGvarh. */
  public static final int RVU_GVARH = 16;
  /** Ordinal value for rvuV. */
  public static final int RVU_V = 17;
  /** Ordinal value for rvuA. */
  public static final int RVU_A = 18;
  /** Ordinal value for rvuCosf. */
  public static final int RVU_COSF = 19;
  /** Ordinal value for rvuM3. */
  public static final int RVU_M3 = 20;
  /** Ordinal value for rvuL. */
  public static final int RVU_L = 21;
  /** Ordinal value for rvuMl. */
  public static final int RVU_ML = 22;
  /** Ordinal value for rvuUsgal. */
  public static final int RVU_USGAL = 23;
  /** Ordinal value for rvuGj. */
  public static final int RVU_GJ = 24;
  /** Ordinal value for rvuMj. */
  public static final int RVU_MJ = 25;
  /** Ordinal value for rvuMcal. */
  public static final int RVU_MCAL = 26;
  /** Ordinal value for rvuKcal. */
  public static final int RVU_KCAL = 27;
  /** Ordinal value for rvuMbtu. */
  public static final int RVU_MBTU = 28;
  /** Ordinal value for rvuKbtu. */
  public static final int RVU_KBTU = 29;
  /** Ordinal value for rvuMjh. */
  public static final int RVU_MJH = 30;
  /** Ordinal value for rvuMls. */
  public static final int RVU_MLS = 31;
  /** Ordinal value for rvuLs. */
  public static final int RVU_LS = 32;
  /** Ordinal value for rvuM3s. */
  public static final int RVU_M3S = 33;
  /** Ordinal value for rvuC. */
  public static final int RVU_C = 34;
  /** Ordinal value for rvuLh. */
  public static final int RVU_LH = 35;
  /** Ordinal value for rvuVa. */
  public static final int RVU_VA = 36;
  /** Ordinal value for rvuKva. */
  public static final int RVU_KVA = 37;
  /** Ordinal value for rvuMva. */
  public static final int RVU_MVA = 38;
  /** Ordinal value for rvuGva. */
  public static final int RVU_GVA = 39;
  /** Ordinal value for rvuVah. */
  public static final int RVU_VAH = 40;
  /** Ordinal value for rvuKvah. */
  public static final int RVU_KVAH = 41;
  /** Ordinal value for rvuMvah. */
  public static final int RVU_MVAH = 42;
  /** Ordinal value for rvuGvah. */
  public static final int RVU_GVAH = 43;
  /** Ordinal value for rvuNul. */
  public static final int RVU_NUL = -1;

  /** BLonRegValUnitEnum constant for rvuNone. */
  public static final BLonRegValUnitEnum rvuNone = new BLonRegValUnitEnum(RVU_NONE);
  /** BLonRegValUnitEnum constant for rvuW. */
  public static final BLonRegValUnitEnum rvuW = new BLonRegValUnitEnum(RVU_W);
  /** BLonRegValUnitEnum constant for rvuKw. */
  public static final BLonRegValUnitEnum rvuKw = new BLonRegValUnitEnum(RVU_KW);
  /** BLonRegValUnitEnum constant for rvuMw. */
  public static final BLonRegValUnitEnum rvuMw = new BLonRegValUnitEnum(RVU_MW);
  /** BLonRegValUnitEnum constant for rvuGw. */
  public static final BLonRegValUnitEnum rvuGw = new BLonRegValUnitEnum(RVU_GW);
  /** BLonRegValUnitEnum constant for rvuVar. */
  public static final BLonRegValUnitEnum rvuVar = new BLonRegValUnitEnum(RVU_VAR);
  /** BLonRegValUnitEnum constant for rvuKvar. */
  public static final BLonRegValUnitEnum rvuKvar = new BLonRegValUnitEnum(RVU_KVAR);
  /** BLonRegValUnitEnum constant for rvuMvar. */
  public static final BLonRegValUnitEnum rvuMvar = new BLonRegValUnitEnum(RVU_MVAR);
  /** BLonRegValUnitEnum constant for rvuGvar. */
  public static final BLonRegValUnitEnum rvuGvar = new BLonRegValUnitEnum(RVU_GVAR);
  /** BLonRegValUnitEnum constant for rvuWh. */
  public static final BLonRegValUnitEnum rvuWh = new BLonRegValUnitEnum(RVU_WH);
  /** BLonRegValUnitEnum constant for rvuKwh. */
  public static final BLonRegValUnitEnum rvuKwh = new BLonRegValUnitEnum(RVU_KWH);
  /** BLonRegValUnitEnum constant for rvuMwh. */
  public static final BLonRegValUnitEnum rvuMwh = new BLonRegValUnitEnum(RVU_MWH);
  /** BLonRegValUnitEnum constant for rvuGwn. */
  public static final BLonRegValUnitEnum rvuGwn = new BLonRegValUnitEnum(RVU_GWN);
  /** BLonRegValUnitEnum constant for rvuVarh. */
  public static final BLonRegValUnitEnum rvuVarh = new BLonRegValUnitEnum(RVU_VARH);
  /** BLonRegValUnitEnum constant for rvuKvarh. */
  public static final BLonRegValUnitEnum rvuKvarh = new BLonRegValUnitEnum(RVU_KVARH);
  /** BLonRegValUnitEnum constant for rvuMvarh. */
  public static final BLonRegValUnitEnum rvuMvarh = new BLonRegValUnitEnum(RVU_MVARH);
  /** BLonRegValUnitEnum constant for rvuGvarh. */
  public static final BLonRegValUnitEnum rvuGvarh = new BLonRegValUnitEnum(RVU_GVARH);
  /** BLonRegValUnitEnum constant for rvuV. */
  public static final BLonRegValUnitEnum rvuV = new BLonRegValUnitEnum(RVU_V);
  /** BLonRegValUnitEnum constant for rvuA. */
  public static final BLonRegValUnitEnum rvuA = new BLonRegValUnitEnum(RVU_A);
  /** BLonRegValUnitEnum constant for rvuCosf. */
  public static final BLonRegValUnitEnum rvuCosf = new BLonRegValUnitEnum(RVU_COSF);
  /** BLonRegValUnitEnum constant for rvuM3. */
  public static final BLonRegValUnitEnum rvuM3 = new BLonRegValUnitEnum(RVU_M3);
  /** BLonRegValUnitEnum constant for rvuL. */
  public static final BLonRegValUnitEnum rvuL = new BLonRegValUnitEnum(RVU_L);
  /** BLonRegValUnitEnum constant for rvuMl. */
  public static final BLonRegValUnitEnum rvuMl = new BLonRegValUnitEnum(RVU_ML);
  /** BLonRegValUnitEnum constant for rvuUsgal. */
  public static final BLonRegValUnitEnum rvuUsgal = new BLonRegValUnitEnum(RVU_USGAL);
  /** BLonRegValUnitEnum constant for rvuGj. */
  public static final BLonRegValUnitEnum rvuGj = new BLonRegValUnitEnum(RVU_GJ);
  /** BLonRegValUnitEnum constant for rvuMj. */
  public static final BLonRegValUnitEnum rvuMj = new BLonRegValUnitEnum(RVU_MJ);
  /** BLonRegValUnitEnum constant for rvuMcal. */
  public static final BLonRegValUnitEnum rvuMcal = new BLonRegValUnitEnum(RVU_MCAL);
  /** BLonRegValUnitEnum constant for rvuKcal. */
  public static final BLonRegValUnitEnum rvuKcal = new BLonRegValUnitEnum(RVU_KCAL);
  /** BLonRegValUnitEnum constant for rvuMbtu. */
  public static final BLonRegValUnitEnum rvuMbtu = new BLonRegValUnitEnum(RVU_MBTU);
  /** BLonRegValUnitEnum constant for rvuKbtu. */
  public static final BLonRegValUnitEnum rvuKbtu = new BLonRegValUnitEnum(RVU_KBTU);
  /** BLonRegValUnitEnum constant for rvuMjh. */
  public static final BLonRegValUnitEnum rvuMjh = new BLonRegValUnitEnum(RVU_MJH);
  /** BLonRegValUnitEnum constant for rvuMls. */
  public static final BLonRegValUnitEnum rvuMls = new BLonRegValUnitEnum(RVU_MLS);
  /** BLonRegValUnitEnum constant for rvuLs. */
  public static final BLonRegValUnitEnum rvuLs = new BLonRegValUnitEnum(RVU_LS);
  /** BLonRegValUnitEnum constant for rvuM3s. */
  public static final BLonRegValUnitEnum rvuM3s = new BLonRegValUnitEnum(RVU_M3S);
  /** BLonRegValUnitEnum constant for rvuC. */
  public static final BLonRegValUnitEnum rvuC = new BLonRegValUnitEnum(RVU_C);
  /** BLonRegValUnitEnum constant for rvuLh. */
  public static final BLonRegValUnitEnum rvuLh = new BLonRegValUnitEnum(RVU_LH);
  /** BLonRegValUnitEnum constant for rvuVa. */
  public static final BLonRegValUnitEnum rvuVa = new BLonRegValUnitEnum(RVU_VA);
  /** BLonRegValUnitEnum constant for rvuKva. */
  public static final BLonRegValUnitEnum rvuKva = new BLonRegValUnitEnum(RVU_KVA);
  /** BLonRegValUnitEnum constant for rvuMva. */
  public static final BLonRegValUnitEnum rvuMva = new BLonRegValUnitEnum(RVU_MVA);
  /** BLonRegValUnitEnum constant for rvuGva. */
  public static final BLonRegValUnitEnum rvuGva = new BLonRegValUnitEnum(RVU_GVA);
  /** BLonRegValUnitEnum constant for rvuVah. */
  public static final BLonRegValUnitEnum rvuVah = new BLonRegValUnitEnum(RVU_VAH);
  /** BLonRegValUnitEnum constant for rvuKvah. */
  public static final BLonRegValUnitEnum rvuKvah = new BLonRegValUnitEnum(RVU_KVAH);
  /** BLonRegValUnitEnum constant for rvuMvah. */
  public static final BLonRegValUnitEnum rvuMvah = new BLonRegValUnitEnum(RVU_MVAH);
  /** BLonRegValUnitEnum constant for rvuGvah. */
  public static final BLonRegValUnitEnum rvuGvah = new BLonRegValUnitEnum(RVU_GVAH);
  /** BLonRegValUnitEnum constant for rvuNul. */
  public static final BLonRegValUnitEnum rvuNul = new BLonRegValUnitEnum(RVU_NUL);

  /** Factory method with ordinal. */
  public static BLonRegValUnitEnum make(int ordinal)
  {
    return (BLonRegValUnitEnum)rvuNone.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonRegValUnitEnum make(String tag)
  {
    return (BLonRegValUnitEnum)rvuNone.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonRegValUnitEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonRegValUnitEnum DEFAULT = rvuNone;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonRegValUnitEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
