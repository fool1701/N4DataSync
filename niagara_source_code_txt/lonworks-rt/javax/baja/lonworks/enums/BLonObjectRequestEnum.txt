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
 * The BLonObjectRequestEnum class provides enumeration for
 * SNVT_obj_request
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:33 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "rqNormal", ordinal = 0),
    @Range(value = "rqDisabled", ordinal = 1),
    @Range(value = "rqUpdateStatus", ordinal = 2),
    @Range(value = "rqSelfTest", ordinal = 3),
    @Range(value = "rqUpdateAlarm", ordinal = 4),
    @Range(value = "rqReportMask", ordinal = 5),
    @Range(value = "rqOverride", ordinal = 6),
    @Range(value = "rqEnable", ordinal = 7),
    @Range(value = "rqRmvOverride", ordinal = 8),
    @Range(value = "rqClearStatus", ordinal = 9),
    @Range(value = "rqClearAlarm", ordinal = 10),
    @Range(value = "rqAlarmNotifyEnabled", ordinal = 11),
    @Range(value = "rqAlarmNotifyDisabled", ordinal = 12),
    @Range(value = "rqManualCtrl", ordinal = 13),
    @Range(value = "rqRemoteCtrl", ordinal = 14),
    @Range(value = "rqProgram", ordinal = 15),
    @Range(value = "rqNul", ordinal = -1)
  }
)
public final class BLonObjectRequestEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonObjectRequestEnum(3343796300)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for rqNormal. */
  public static final int RQ_NORMAL = 0;
  /** Ordinal value for rqDisabled. */
  public static final int RQ_DISABLED = 1;
  /** Ordinal value for rqUpdateStatus. */
  public static final int RQ_UPDATE_STATUS = 2;
  /** Ordinal value for rqSelfTest. */
  public static final int RQ_SELF_TEST = 3;
  /** Ordinal value for rqUpdateAlarm. */
  public static final int RQ_UPDATE_ALARM = 4;
  /** Ordinal value for rqReportMask. */
  public static final int RQ_REPORT_MASK = 5;
  /** Ordinal value for rqOverride. */
  public static final int RQ_OVERRIDE = 6;
  /** Ordinal value for rqEnable. */
  public static final int RQ_ENABLE = 7;
  /** Ordinal value for rqRmvOverride. */
  public static final int RQ_RMV_OVERRIDE = 8;
  /** Ordinal value for rqClearStatus. */
  public static final int RQ_CLEAR_STATUS = 9;
  /** Ordinal value for rqClearAlarm. */
  public static final int RQ_CLEAR_ALARM = 10;
  /** Ordinal value for rqAlarmNotifyEnabled. */
  public static final int RQ_ALARM_NOTIFY_ENABLED = 11;
  /** Ordinal value for rqAlarmNotifyDisabled. */
  public static final int RQ_ALARM_NOTIFY_DISABLED = 12;
  /** Ordinal value for rqManualCtrl. */
  public static final int RQ_MANUAL_CTRL = 13;
  /** Ordinal value for rqRemoteCtrl. */
  public static final int RQ_REMOTE_CTRL = 14;
  /** Ordinal value for rqProgram. */
  public static final int RQ_PROGRAM = 15;
  /** Ordinal value for rqNul. */
  public static final int RQ_NUL = -1;

  /** BLonObjectRequestEnum constant for rqNormal. */
  public static final BLonObjectRequestEnum rqNormal = new BLonObjectRequestEnum(RQ_NORMAL);
  /** BLonObjectRequestEnum constant for rqDisabled. */
  public static final BLonObjectRequestEnum rqDisabled = new BLonObjectRequestEnum(RQ_DISABLED);
  /** BLonObjectRequestEnum constant for rqUpdateStatus. */
  public static final BLonObjectRequestEnum rqUpdateStatus = new BLonObjectRequestEnum(RQ_UPDATE_STATUS);
  /** BLonObjectRequestEnum constant for rqSelfTest. */
  public static final BLonObjectRequestEnum rqSelfTest = new BLonObjectRequestEnum(RQ_SELF_TEST);
  /** BLonObjectRequestEnum constant for rqUpdateAlarm. */
  public static final BLonObjectRequestEnum rqUpdateAlarm = new BLonObjectRequestEnum(RQ_UPDATE_ALARM);
  /** BLonObjectRequestEnum constant for rqReportMask. */
  public static final BLonObjectRequestEnum rqReportMask = new BLonObjectRequestEnum(RQ_REPORT_MASK);
  /** BLonObjectRequestEnum constant for rqOverride. */
  public static final BLonObjectRequestEnum rqOverride = new BLonObjectRequestEnum(RQ_OVERRIDE);
  /** BLonObjectRequestEnum constant for rqEnable. */
  public static final BLonObjectRequestEnum rqEnable = new BLonObjectRequestEnum(RQ_ENABLE);
  /** BLonObjectRequestEnum constant for rqRmvOverride. */
  public static final BLonObjectRequestEnum rqRmvOverride = new BLonObjectRequestEnum(RQ_RMV_OVERRIDE);
  /** BLonObjectRequestEnum constant for rqClearStatus. */
  public static final BLonObjectRequestEnum rqClearStatus = new BLonObjectRequestEnum(RQ_CLEAR_STATUS);
  /** BLonObjectRequestEnum constant for rqClearAlarm. */
  public static final BLonObjectRequestEnum rqClearAlarm = new BLonObjectRequestEnum(RQ_CLEAR_ALARM);
  /** BLonObjectRequestEnum constant for rqAlarmNotifyEnabled. */
  public static final BLonObjectRequestEnum rqAlarmNotifyEnabled = new BLonObjectRequestEnum(RQ_ALARM_NOTIFY_ENABLED);
  /** BLonObjectRequestEnum constant for rqAlarmNotifyDisabled. */
  public static final BLonObjectRequestEnum rqAlarmNotifyDisabled = new BLonObjectRequestEnum(RQ_ALARM_NOTIFY_DISABLED);
  /** BLonObjectRequestEnum constant for rqManualCtrl. */
  public static final BLonObjectRequestEnum rqManualCtrl = new BLonObjectRequestEnum(RQ_MANUAL_CTRL);
  /** BLonObjectRequestEnum constant for rqRemoteCtrl. */
  public static final BLonObjectRequestEnum rqRemoteCtrl = new BLonObjectRequestEnum(RQ_REMOTE_CTRL);
  /** BLonObjectRequestEnum constant for rqProgram. */
  public static final BLonObjectRequestEnum rqProgram = new BLonObjectRequestEnum(RQ_PROGRAM);
  /** BLonObjectRequestEnum constant for rqNul. */
  public static final BLonObjectRequestEnum rqNul = new BLonObjectRequestEnum(RQ_NUL);

  /** Factory method with ordinal. */
  public static BLonObjectRequestEnum make(int ordinal)
  {
    return (BLonObjectRequestEnum)rqNormal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonObjectRequestEnum make(String tag)
  {
    return (BLonObjectRequestEnum)rqNormal.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonObjectRequestEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonObjectRequestEnum DEFAULT = rqNormal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonObjectRequestEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
