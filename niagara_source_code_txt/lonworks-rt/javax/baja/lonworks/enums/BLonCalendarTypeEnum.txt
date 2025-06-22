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
 * The BLonCalendarTypeEnum class provides enumeration for
 * SNVT_time_zone
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:22 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "calGreg", ordinal = 0),
    @Range(value = "calJul", ordinal = 1),
    @Range(value = "calMeu", ordinal = 2),
    @Range(value = "calNul", ordinal = -1)
  }
)
public final class BLonCalendarTypeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonCalendarTypeEnum(2054808687)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for calGreg. */
  public static final int CAL_GREG = 0;
  /** Ordinal value for calJul. */
  public static final int CAL_JUL = 1;
  /** Ordinal value for calMeu. */
  public static final int CAL_MEU = 2;
  /** Ordinal value for calNul. */
  public static final int CAL_NUL = -1;

  /** BLonCalendarTypeEnum constant for calGreg. */
  public static final BLonCalendarTypeEnum calGreg = new BLonCalendarTypeEnum(CAL_GREG);
  /** BLonCalendarTypeEnum constant for calJul. */
  public static final BLonCalendarTypeEnum calJul = new BLonCalendarTypeEnum(CAL_JUL);
  /** BLonCalendarTypeEnum constant for calMeu. */
  public static final BLonCalendarTypeEnum calMeu = new BLonCalendarTypeEnum(CAL_MEU);
  /** BLonCalendarTypeEnum constant for calNul. */
  public static final BLonCalendarTypeEnum calNul = new BLonCalendarTypeEnum(CAL_NUL);

  /** Factory method with ordinal. */
  public static BLonCalendarTypeEnum make(int ordinal)
  {
    return (BLonCalendarTypeEnum)calGreg.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonCalendarTypeEnum make(String tag)
  {
    return (BLonCalendarTypeEnum)calGreg.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonCalendarTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonCalendarTypeEnum DEFAULT = calGreg;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonCalendarTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
