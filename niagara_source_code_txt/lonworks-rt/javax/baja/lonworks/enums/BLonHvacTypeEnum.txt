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
 * The BLonHvacTypeEnum class This file defines the
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
    @Range(value = "hvtGeneric", ordinal = 0),
    @Range(value = "hvtFanCoil", ordinal = 1),
    @Range(value = "hvtVav", ordinal = 2),
    @Range(value = "hvtHeatPump", ordinal = 3),
    @Range(value = "hvtRooftop", ordinal = 4),
    @Range(value = "hvtUnitVent", ordinal = 5),
    @Range(value = "hvtChillCeil", ordinal = 6),
    @Range(value = "hvtRadiator", ordinal = 7),
    @Range(value = "hvtAhu", ordinal = 8),
    @Range(value = "hvtSelfCont", ordinal = 9),
    @Range(value = "hvtNul", ordinal = -1)
  }
)
public final class BLonHvacTypeEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonHvacTypeEnum(2887273868)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for hvtGeneric. */
  public static final int HVT_GENERIC = 0;
  /** Ordinal value for hvtFanCoil. */
  public static final int HVT_FAN_COIL = 1;
  /** Ordinal value for hvtVav. */
  public static final int HVT_VAV = 2;
  /** Ordinal value for hvtHeatPump. */
  public static final int HVT_HEAT_PUMP = 3;
  /** Ordinal value for hvtRooftop. */
  public static final int HVT_ROOFTOP = 4;
  /** Ordinal value for hvtUnitVent. */
  public static final int HVT_UNIT_VENT = 5;
  /** Ordinal value for hvtChillCeil. */
  public static final int HVT_CHILL_CEIL = 6;
  /** Ordinal value for hvtRadiator. */
  public static final int HVT_RADIATOR = 7;
  /** Ordinal value for hvtAhu. */
  public static final int HVT_AHU = 8;
  /** Ordinal value for hvtSelfCont. */
  public static final int HVT_SELF_CONT = 9;
  /** Ordinal value for hvtNul. */
  public static final int HVT_NUL = -1;

  /** BLonHvacTypeEnum constant for hvtGeneric. */
  public static final BLonHvacTypeEnum hvtGeneric = new BLonHvacTypeEnum(HVT_GENERIC);
  /** BLonHvacTypeEnum constant for hvtFanCoil. */
  public static final BLonHvacTypeEnum hvtFanCoil = new BLonHvacTypeEnum(HVT_FAN_COIL);
  /** BLonHvacTypeEnum constant for hvtVav. */
  public static final BLonHvacTypeEnum hvtVav = new BLonHvacTypeEnum(HVT_VAV);
  /** BLonHvacTypeEnum constant for hvtHeatPump. */
  public static final BLonHvacTypeEnum hvtHeatPump = new BLonHvacTypeEnum(HVT_HEAT_PUMP);
  /** BLonHvacTypeEnum constant for hvtRooftop. */
  public static final BLonHvacTypeEnum hvtRooftop = new BLonHvacTypeEnum(HVT_ROOFTOP);
  /** BLonHvacTypeEnum constant for hvtUnitVent. */
  public static final BLonHvacTypeEnum hvtUnitVent = new BLonHvacTypeEnum(HVT_UNIT_VENT);
  /** BLonHvacTypeEnum constant for hvtChillCeil. */
  public static final BLonHvacTypeEnum hvtChillCeil = new BLonHvacTypeEnum(HVT_CHILL_CEIL);
  /** BLonHvacTypeEnum constant for hvtRadiator. */
  public static final BLonHvacTypeEnum hvtRadiator = new BLonHvacTypeEnum(HVT_RADIATOR);
  /** BLonHvacTypeEnum constant for hvtAhu. */
  public static final BLonHvacTypeEnum hvtAhu = new BLonHvacTypeEnum(HVT_AHU);
  /** BLonHvacTypeEnum constant for hvtSelfCont. */
  public static final BLonHvacTypeEnum hvtSelfCont = new BLonHvacTypeEnum(HVT_SELF_CONT);
  /** BLonHvacTypeEnum constant for hvtNul. */
  public static final BLonHvacTypeEnum hvtNul = new BLonHvacTypeEnum(HVT_NUL);

  /** Factory method with ordinal. */
  public static BLonHvacTypeEnum make(int ordinal)
  {
    return (BLonHvacTypeEnum)hvtGeneric.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonHvacTypeEnum make(String tag)
  {
    return (BLonHvacTypeEnum)hvtGeneric.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonHvacTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonHvacTypeEnum DEFAULT = hvtGeneric;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonHvacTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
