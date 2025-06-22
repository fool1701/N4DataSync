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
 * The BLonIntervalOfMonthEnum class provides enumeration for SCPT_time_period.
 *
 * @author    Robert Adams
 * @creation  9 Nov 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "iomMinute", ordinal = 0),
    @Range(value = "iomHour", ordinal = 1),
    @Range(value = "iomDay", ordinal = 2),
    @Range(value = "iomWeek", ordinal = 3),
    @Range(value = "iomMonth", ordinal = 4),
    @Range(value = "iomNul", ordinal = -1)
  },
  defaultValue = "iomNul"
)
public final class BLonIntervalOfMonthEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonIntervalOfMonthEnum(4212245799)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for iomMinute. */
  public static final int IOM_MINUTE = 0;
  /** Ordinal value for iomHour. */
  public static final int IOM_HOUR = 1;
  /** Ordinal value for iomDay. */
  public static final int IOM_DAY = 2;
  /** Ordinal value for iomWeek. */
  public static final int IOM_WEEK = 3;
  /** Ordinal value for iomMonth. */
  public static final int IOM_MONTH = 4;
  /** Ordinal value for iomNul. */
  public static final int IOM_NUL = -1;

  /** BLonIntervalOfMonthEnum constant for iomMinute. */
  public static final BLonIntervalOfMonthEnum iomMinute = new BLonIntervalOfMonthEnum(IOM_MINUTE);
  /** BLonIntervalOfMonthEnum constant for iomHour. */
  public static final BLonIntervalOfMonthEnum iomHour = new BLonIntervalOfMonthEnum(IOM_HOUR);
  /** BLonIntervalOfMonthEnum constant for iomDay. */
  public static final BLonIntervalOfMonthEnum iomDay = new BLonIntervalOfMonthEnum(IOM_DAY);
  /** BLonIntervalOfMonthEnum constant for iomWeek. */
  public static final BLonIntervalOfMonthEnum iomWeek = new BLonIntervalOfMonthEnum(IOM_WEEK);
  /** BLonIntervalOfMonthEnum constant for iomMonth. */
  public static final BLonIntervalOfMonthEnum iomMonth = new BLonIntervalOfMonthEnum(IOM_MONTH);
  /** BLonIntervalOfMonthEnum constant for iomNul. */
  public static final BLonIntervalOfMonthEnum iomNul = new BLonIntervalOfMonthEnum(IOM_NUL);

  /** Factory method with ordinal. */
  public static BLonIntervalOfMonthEnum make(int ordinal)
  {
    return (BLonIntervalOfMonthEnum)iomMinute.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonIntervalOfMonthEnum make(String tag)
  {
    return (BLonIntervalOfMonthEnum)iomMinute.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonIntervalOfMonthEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonIntervalOfMonthEnum DEFAULT = iomNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonIntervalOfMonthEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
