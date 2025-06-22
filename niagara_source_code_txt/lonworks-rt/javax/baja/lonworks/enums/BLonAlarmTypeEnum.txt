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
 * The BLonAlarmTypeEnum class provides enumeration for the alarm
 * condition of a SNVT_alarm.
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:22 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "alNoCondition", ordinal = 0),
    @Range(value = "alAlmCondition", ordinal = 1),
    @Range(value = "alTotSvcAlm1", ordinal = 2),
    @Range(value = "alTotSvcAlm2", ordinal = 3),
    @Range(value = "alTotSvcAlm3", ordinal = 4),
    @Range(value = "alLowLmtClr1", ordinal = 5),
    @Range(value = "alLowLmtClr2", ordinal = 6),
    @Range(value = "alHighLmtClr1", ordinal = 7),
    @Range(value = "alHighLmtClr2", ordinal = 8),
    @Range(value = "alLowLmtAlm1", ordinal = 9),
    @Range(value = "alLowLmtAlm2", ordinal = 10),
    @Range(value = "alHighLmtAlm1", ordinal = 11),
    @Range(value = "alHighLmtAlm2", ordinal = 12),
    @Range(value = "alFirAlm", ordinal = 13),
    @Range(value = "alFirPreAlm", ordinal = 14),
    @Range(value = "alFirTrbl", ordinal = 15),
    @Range(value = "alFirSupv", ordinal = 16),
    @Range(value = "alFirTestAlm", ordinal = 17),
    @Range(value = "alFirTestPreAlm", ordinal = 18),
    @Range(value = "alFirEnvcompMax", ordinal = 19),
    @Range(value = "alFirMonitorCond", ordinal = 20),
    @Range(value = "alFirMaintAlert", ordinal = 21),
    @Range(value = "alNul", ordinal = -1)
  }
)
public final class BLonAlarmTypeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonAlarmTypeEnum(2082452230)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for alNoCondition. */
  public static final int AL_NO_CONDITION = 0;
  /** Ordinal value for alAlmCondition. */
  public static final int AL_ALM_CONDITION = 1;
  /** Ordinal value for alTotSvcAlm1. */
  public static final int AL_TOT_SVC_ALM_1 = 2;
  /** Ordinal value for alTotSvcAlm2. */
  public static final int AL_TOT_SVC_ALM_2 = 3;
  /** Ordinal value for alTotSvcAlm3. */
  public static final int AL_TOT_SVC_ALM_3 = 4;
  /** Ordinal value for alLowLmtClr1. */
  public static final int AL_LOW_LMT_CLR_1 = 5;
  /** Ordinal value for alLowLmtClr2. */
  public static final int AL_LOW_LMT_CLR_2 = 6;
  /** Ordinal value for alHighLmtClr1. */
  public static final int AL_HIGH_LMT_CLR_1 = 7;
  /** Ordinal value for alHighLmtClr2. */
  public static final int AL_HIGH_LMT_CLR_2 = 8;
  /** Ordinal value for alLowLmtAlm1. */
  public static final int AL_LOW_LMT_ALM_1 = 9;
  /** Ordinal value for alLowLmtAlm2. */
  public static final int AL_LOW_LMT_ALM_2 = 10;
  /** Ordinal value for alHighLmtAlm1. */
  public static final int AL_HIGH_LMT_ALM_1 = 11;
  /** Ordinal value for alHighLmtAlm2. */
  public static final int AL_HIGH_LMT_ALM_2 = 12;
  /** Ordinal value for alFirAlm. */
  public static final int AL_FIR_ALM = 13;
  /** Ordinal value for alFirPreAlm. */
  public static final int AL_FIR_PRE_ALM = 14;
  /** Ordinal value for alFirTrbl. */
  public static final int AL_FIR_TRBL = 15;
  /** Ordinal value for alFirSupv. */
  public static final int AL_FIR_SUPV = 16;
  /** Ordinal value for alFirTestAlm. */
  public static final int AL_FIR_TEST_ALM = 17;
  /** Ordinal value for alFirTestPreAlm. */
  public static final int AL_FIR_TEST_PRE_ALM = 18;
  /** Ordinal value for alFirEnvcompMax. */
  public static final int AL_FIR_ENVCOMP_MAX = 19;
  /** Ordinal value for alFirMonitorCond. */
  public static final int AL_FIR_MONITOR_COND = 20;
  /** Ordinal value for alFirMaintAlert. */
  public static final int AL_FIR_MAINT_ALERT = 21;
  /** Ordinal value for alNul. */
  public static final int AL_NUL = -1;

  /** BLonAlarmTypeEnum constant for alNoCondition. */
  public static final BLonAlarmTypeEnum alNoCondition = new BLonAlarmTypeEnum(AL_NO_CONDITION);
  /** BLonAlarmTypeEnum constant for alAlmCondition. */
  public static final BLonAlarmTypeEnum alAlmCondition = new BLonAlarmTypeEnum(AL_ALM_CONDITION);
  /** BLonAlarmTypeEnum constant for alTotSvcAlm1. */
  public static final BLonAlarmTypeEnum alTotSvcAlm1 = new BLonAlarmTypeEnum(AL_TOT_SVC_ALM_1);
  /** BLonAlarmTypeEnum constant for alTotSvcAlm2. */
  public static final BLonAlarmTypeEnum alTotSvcAlm2 = new BLonAlarmTypeEnum(AL_TOT_SVC_ALM_2);
  /** BLonAlarmTypeEnum constant for alTotSvcAlm3. */
  public static final BLonAlarmTypeEnum alTotSvcAlm3 = new BLonAlarmTypeEnum(AL_TOT_SVC_ALM_3);
  /** BLonAlarmTypeEnum constant for alLowLmtClr1. */
  public static final BLonAlarmTypeEnum alLowLmtClr1 = new BLonAlarmTypeEnum(AL_LOW_LMT_CLR_1);
  /** BLonAlarmTypeEnum constant for alLowLmtClr2. */
  public static final BLonAlarmTypeEnum alLowLmtClr2 = new BLonAlarmTypeEnum(AL_LOW_LMT_CLR_2);
  /** BLonAlarmTypeEnum constant for alHighLmtClr1. */
  public static final BLonAlarmTypeEnum alHighLmtClr1 = new BLonAlarmTypeEnum(AL_HIGH_LMT_CLR_1);
  /** BLonAlarmTypeEnum constant for alHighLmtClr2. */
  public static final BLonAlarmTypeEnum alHighLmtClr2 = new BLonAlarmTypeEnum(AL_HIGH_LMT_CLR_2);
  /** BLonAlarmTypeEnum constant for alLowLmtAlm1. */
  public static final BLonAlarmTypeEnum alLowLmtAlm1 = new BLonAlarmTypeEnum(AL_LOW_LMT_ALM_1);
  /** BLonAlarmTypeEnum constant for alLowLmtAlm2. */
  public static final BLonAlarmTypeEnum alLowLmtAlm2 = new BLonAlarmTypeEnum(AL_LOW_LMT_ALM_2);
  /** BLonAlarmTypeEnum constant for alHighLmtAlm1. */
  public static final BLonAlarmTypeEnum alHighLmtAlm1 = new BLonAlarmTypeEnum(AL_HIGH_LMT_ALM_1);
  /** BLonAlarmTypeEnum constant for alHighLmtAlm2. */
  public static final BLonAlarmTypeEnum alHighLmtAlm2 = new BLonAlarmTypeEnum(AL_HIGH_LMT_ALM_2);
  /** BLonAlarmTypeEnum constant for alFirAlm. */
  public static final BLonAlarmTypeEnum alFirAlm = new BLonAlarmTypeEnum(AL_FIR_ALM);
  /** BLonAlarmTypeEnum constant for alFirPreAlm. */
  public static final BLonAlarmTypeEnum alFirPreAlm = new BLonAlarmTypeEnum(AL_FIR_PRE_ALM);
  /** BLonAlarmTypeEnum constant for alFirTrbl. */
  public static final BLonAlarmTypeEnum alFirTrbl = new BLonAlarmTypeEnum(AL_FIR_TRBL);
  /** BLonAlarmTypeEnum constant for alFirSupv. */
  public static final BLonAlarmTypeEnum alFirSupv = new BLonAlarmTypeEnum(AL_FIR_SUPV);
  /** BLonAlarmTypeEnum constant for alFirTestAlm. */
  public static final BLonAlarmTypeEnum alFirTestAlm = new BLonAlarmTypeEnum(AL_FIR_TEST_ALM);
  /** BLonAlarmTypeEnum constant for alFirTestPreAlm. */
  public static final BLonAlarmTypeEnum alFirTestPreAlm = new BLonAlarmTypeEnum(AL_FIR_TEST_PRE_ALM);
  /** BLonAlarmTypeEnum constant for alFirEnvcompMax. */
  public static final BLonAlarmTypeEnum alFirEnvcompMax = new BLonAlarmTypeEnum(AL_FIR_ENVCOMP_MAX);
  /** BLonAlarmTypeEnum constant for alFirMonitorCond. */
  public static final BLonAlarmTypeEnum alFirMonitorCond = new BLonAlarmTypeEnum(AL_FIR_MONITOR_COND);
  /** BLonAlarmTypeEnum constant for alFirMaintAlert. */
  public static final BLonAlarmTypeEnum alFirMaintAlert = new BLonAlarmTypeEnum(AL_FIR_MAINT_ALERT);
  /** BLonAlarmTypeEnum constant for alNul. */
  public static final BLonAlarmTypeEnum alNul = new BLonAlarmTypeEnum(AL_NUL);

  /** Factory method with ordinal. */
  public static BLonAlarmTypeEnum make(int ordinal)
  {
    return (BLonAlarmTypeEnum)alNoCondition.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonAlarmTypeEnum make(String tag)
  {
    return (BLonAlarmTypeEnum)alNoCondition.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonAlarmTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonAlarmTypeEnum DEFAULT = alNoCondition;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonAlarmTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
