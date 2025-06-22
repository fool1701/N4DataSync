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
 * The BLonDaysOfWeekEnum class provides enumeration for SNVT_date_day
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:25 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "daySun", ordinal = 0),
    @Range(value = "dayMon", ordinal = 1),
    @Range(value = "dayTue", ordinal = 2),
    @Range(value = "dayWed", ordinal = 3),
    @Range(value = "dayThu", ordinal = 4),
    @Range(value = "dayFri", ordinal = 5),
    @Range(value = "daySat", ordinal = 6),
    @Range(value = "dayNul", ordinal = -1)
  },
  defaultValue = "dayNul"
)
public final class BLonDaysOfWeekEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonDaysOfWeekEnum(1716190180)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for daySun. */
  public static final int DAY_SUN = 0;
  /** Ordinal value for dayMon. */
  public static final int DAY_MON = 1;
  /** Ordinal value for dayTue. */
  public static final int DAY_TUE = 2;
  /** Ordinal value for dayWed. */
  public static final int DAY_WED = 3;
  /** Ordinal value for dayThu. */
  public static final int DAY_THU = 4;
  /** Ordinal value for dayFri. */
  public static final int DAY_FRI = 5;
  /** Ordinal value for daySat. */
  public static final int DAY_SAT = 6;
  /** Ordinal value for dayNul. */
  public static final int DAY_NUL = -1;

  /** BLonDaysOfWeekEnum constant for daySun. */
  public static final BLonDaysOfWeekEnum daySun = new BLonDaysOfWeekEnum(DAY_SUN);
  /** BLonDaysOfWeekEnum constant for dayMon. */
  public static final BLonDaysOfWeekEnum dayMon = new BLonDaysOfWeekEnum(DAY_MON);
  /** BLonDaysOfWeekEnum constant for dayTue. */
  public static final BLonDaysOfWeekEnum dayTue = new BLonDaysOfWeekEnum(DAY_TUE);
  /** BLonDaysOfWeekEnum constant for dayWed. */
  public static final BLonDaysOfWeekEnum dayWed = new BLonDaysOfWeekEnum(DAY_WED);
  /** BLonDaysOfWeekEnum constant for dayThu. */
  public static final BLonDaysOfWeekEnum dayThu = new BLonDaysOfWeekEnum(DAY_THU);
  /** BLonDaysOfWeekEnum constant for dayFri. */
  public static final BLonDaysOfWeekEnum dayFri = new BLonDaysOfWeekEnum(DAY_FRI);
  /** BLonDaysOfWeekEnum constant for daySat. */
  public static final BLonDaysOfWeekEnum daySat = new BLonDaysOfWeekEnum(DAY_SAT);
  /** BLonDaysOfWeekEnum constant for dayNul. */
  public static final BLonDaysOfWeekEnum dayNul = new BLonDaysOfWeekEnum(DAY_NUL);

  /** Factory method with ordinal. */
  public static BLonDaysOfWeekEnum make(int ordinal)
  {
    return (BLonDaysOfWeekEnum)daySun.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonDaysOfWeekEnum make(String tag)
  {
    return (BLonDaysOfWeekEnum)daySun.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonDaysOfWeekEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonDaysOfWeekEnum DEFAULT = dayNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDaysOfWeekEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
