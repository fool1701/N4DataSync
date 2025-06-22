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
 * The BLonRailAudioSensorTypeEnum class provides enumeration for SNVT_rac_ctrl and
 *  SNVT_rac_req
 *
 * @author    Robert Adams
 * @creation  9 Nov 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "rastCuType1", ordinal = 0),
    @Range(value = "rastCuType2", ordinal = 1),
    @Range(value = "rastCuType3", ordinal = 2),
    @Range(value = "rastCuType4", ordinal = 3),
    @Range(value = "rastLsLine1", ordinal = 4),
    @Range(value = "rastLsLine2", ordinal = 5),
    @Range(value = "rastLsLine3", ordinal = 6),
    @Range(value = "rastLsLine4", ordinal = 7),
    @Range(value = "rastLsLine5", ordinal = 8),
    @Range(value = "rastLsLine6", ordinal = 9),
    @Range(value = "rastLsLine7", ordinal = 10),
    @Range(value = "rastLsLine8", ordinal = 11),
    @Range(value = "rastPau", ordinal = 12),
    @Range(value = "rastCfaType1", ordinal = 13),
    @Range(value = "rastCfaType2", ordinal = 14),
    @Range(value = "rastCfaType3", ordinal = 15),
    @Range(value = "rastCfaType4", ordinal = 16),
    @Range(value = "rastDva", ordinal = 17),
    @Range(value = "rastEtType1", ordinal = 18),
    @Range(value = "rastEtType2", ordinal = 19),
    @Range(value = "rastUserdefType1", ordinal = 20),
    @Range(value = "rastUserdefType2", ordinal = 21),
    @Range(value = "rastUserdefType3", ordinal = 22),
    @Range(value = "rastUserdefType4", ordinal = 23),
    @Range(value = "rastNul", ordinal = -1)
  },
  defaultValue = "rastNul"
)
public final class BLonRailAudioSensorTypeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonRailAudioSensorTypeEnum(286120911)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for rastCuType1. */
  public static final int RAST_CU_TYPE_1 = 0;
  /** Ordinal value for rastCuType2. */
  public static final int RAST_CU_TYPE_2 = 1;
  /** Ordinal value for rastCuType3. */
  public static final int RAST_CU_TYPE_3 = 2;
  /** Ordinal value for rastCuType4. */
  public static final int RAST_CU_TYPE_4 = 3;
  /** Ordinal value for rastLsLine1. */
  public static final int RAST_LS_LINE_1 = 4;
  /** Ordinal value for rastLsLine2. */
  public static final int RAST_LS_LINE_2 = 5;
  /** Ordinal value for rastLsLine3. */
  public static final int RAST_LS_LINE_3 = 6;
  /** Ordinal value for rastLsLine4. */
  public static final int RAST_LS_LINE_4 = 7;
  /** Ordinal value for rastLsLine5. */
  public static final int RAST_LS_LINE_5 = 8;
  /** Ordinal value for rastLsLine6. */
  public static final int RAST_LS_LINE_6 = 9;
  /** Ordinal value for rastLsLine7. */
  public static final int RAST_LS_LINE_7 = 10;
  /** Ordinal value for rastLsLine8. */
  public static final int RAST_LS_LINE_8 = 11;
  /** Ordinal value for rastPau. */
  public static final int RAST_PAU = 12;
  /** Ordinal value for rastCfaType1. */
  public static final int RAST_CFA_TYPE_1 = 13;
  /** Ordinal value for rastCfaType2. */
  public static final int RAST_CFA_TYPE_2 = 14;
  /** Ordinal value for rastCfaType3. */
  public static final int RAST_CFA_TYPE_3 = 15;
  /** Ordinal value for rastCfaType4. */
  public static final int RAST_CFA_TYPE_4 = 16;
  /** Ordinal value for rastDva. */
  public static final int RAST_DVA = 17;
  /** Ordinal value for rastEtType1. */
  public static final int RAST_ET_TYPE_1 = 18;
  /** Ordinal value for rastEtType2. */
  public static final int RAST_ET_TYPE_2 = 19;
  /** Ordinal value for rastUserdefType1. */
  public static final int RAST_USERDEF_TYPE_1 = 20;
  /** Ordinal value for rastUserdefType2. */
  public static final int RAST_USERDEF_TYPE_2 = 21;
  /** Ordinal value for rastUserdefType3. */
  public static final int RAST_USERDEF_TYPE_3 = 22;
  /** Ordinal value for rastUserdefType4. */
  public static final int RAST_USERDEF_TYPE_4 = 23;
  /** Ordinal value for rastNul. */
  public static final int RAST_NUL = -1;

  /** BLonRailAudioSensorTypeEnum constant for rastCuType1. */
  public static final BLonRailAudioSensorTypeEnum rastCuType1 = new BLonRailAudioSensorTypeEnum(RAST_CU_TYPE_1);
  /** BLonRailAudioSensorTypeEnum constant for rastCuType2. */
  public static final BLonRailAudioSensorTypeEnum rastCuType2 = new BLonRailAudioSensorTypeEnum(RAST_CU_TYPE_2);
  /** BLonRailAudioSensorTypeEnum constant for rastCuType3. */
  public static final BLonRailAudioSensorTypeEnum rastCuType3 = new BLonRailAudioSensorTypeEnum(RAST_CU_TYPE_3);
  /** BLonRailAudioSensorTypeEnum constant for rastCuType4. */
  public static final BLonRailAudioSensorTypeEnum rastCuType4 = new BLonRailAudioSensorTypeEnum(RAST_CU_TYPE_4);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine1. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine1 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_1);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine2. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine2 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_2);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine3. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine3 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_3);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine4. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine4 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_4);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine5. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine5 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_5);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine6. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine6 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_6);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine7. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine7 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_7);
  /** BLonRailAudioSensorTypeEnum constant for rastLsLine8. */
  public static final BLonRailAudioSensorTypeEnum rastLsLine8 = new BLonRailAudioSensorTypeEnum(RAST_LS_LINE_8);
  /** BLonRailAudioSensorTypeEnum constant for rastPau. */
  public static final BLonRailAudioSensorTypeEnum rastPau = new BLonRailAudioSensorTypeEnum(RAST_PAU);
  /** BLonRailAudioSensorTypeEnum constant for rastCfaType1. */
  public static final BLonRailAudioSensorTypeEnum rastCfaType1 = new BLonRailAudioSensorTypeEnum(RAST_CFA_TYPE_1);
  /** BLonRailAudioSensorTypeEnum constant for rastCfaType2. */
  public static final BLonRailAudioSensorTypeEnum rastCfaType2 = new BLonRailAudioSensorTypeEnum(RAST_CFA_TYPE_2);
  /** BLonRailAudioSensorTypeEnum constant for rastCfaType3. */
  public static final BLonRailAudioSensorTypeEnum rastCfaType3 = new BLonRailAudioSensorTypeEnum(RAST_CFA_TYPE_3);
  /** BLonRailAudioSensorTypeEnum constant for rastCfaType4. */
  public static final BLonRailAudioSensorTypeEnum rastCfaType4 = new BLonRailAudioSensorTypeEnum(RAST_CFA_TYPE_4);
  /** BLonRailAudioSensorTypeEnum constant for rastDva. */
  public static final BLonRailAudioSensorTypeEnum rastDva = new BLonRailAudioSensorTypeEnum(RAST_DVA);
  /** BLonRailAudioSensorTypeEnum constant for rastEtType1. */
  public static final BLonRailAudioSensorTypeEnum rastEtType1 = new BLonRailAudioSensorTypeEnum(RAST_ET_TYPE_1);
  /** BLonRailAudioSensorTypeEnum constant for rastEtType2. */
  public static final BLonRailAudioSensorTypeEnum rastEtType2 = new BLonRailAudioSensorTypeEnum(RAST_ET_TYPE_2);
  /** BLonRailAudioSensorTypeEnum constant for rastUserdefType1. */
  public static final BLonRailAudioSensorTypeEnum rastUserdefType1 = new BLonRailAudioSensorTypeEnum(RAST_USERDEF_TYPE_1);
  /** BLonRailAudioSensorTypeEnum constant for rastUserdefType2. */
  public static final BLonRailAudioSensorTypeEnum rastUserdefType2 = new BLonRailAudioSensorTypeEnum(RAST_USERDEF_TYPE_2);
  /** BLonRailAudioSensorTypeEnum constant for rastUserdefType3. */
  public static final BLonRailAudioSensorTypeEnum rastUserdefType3 = new BLonRailAudioSensorTypeEnum(RAST_USERDEF_TYPE_3);
  /** BLonRailAudioSensorTypeEnum constant for rastUserdefType4. */
  public static final BLonRailAudioSensorTypeEnum rastUserdefType4 = new BLonRailAudioSensorTypeEnum(RAST_USERDEF_TYPE_4);
  /** BLonRailAudioSensorTypeEnum constant for rastNul. */
  public static final BLonRailAudioSensorTypeEnum rastNul = new BLonRailAudioSensorTypeEnum(RAST_NUL);

  /** Factory method with ordinal. */
  public static BLonRailAudioSensorTypeEnum make(int ordinal)
  {
    return (BLonRailAudioSensorTypeEnum)rastCuType1.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonRailAudioSensorTypeEnum make(String tag)
  {
    return (BLonRailAudioSensorTypeEnum)rastCuType1.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonRailAudioSensorTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonRailAudioSensorTypeEnum DEFAULT = rastNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonRailAudioSensorTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
