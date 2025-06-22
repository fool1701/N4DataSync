/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BLonHvacEnum class provides enumeration for telling the
 * status of hvac units
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:30 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "hvacAuto", ordinal = 0),
    @Range(value = "hvacHeat", ordinal = 1),
    @Range(value = "hvacMrngWrmup", ordinal = 2),
    @Range(value = "hvacCool", ordinal = 3),
    @Range(value = "hvacNightPurge", ordinal = 4),
    @Range(value = "hvacPreCool", ordinal = 5),
    @Range(value = "hvacOff", ordinal = 6),
    @Range(value = "hvacTest", ordinal = 7),
    @Range(value = "hvacEmergHeat", ordinal = 8),
    @Range(value = "hvacFanOnly", ordinal = 9),
    @Range(value = "hvacFreeCool", ordinal = 10),
    @Range(value = "hvacIce", ordinal = 11),
    @Range(value = "hvacMaxHeat", ordinal = 12),
    @Range(value = "hvacEconomy", ordinal = 13),
    @Range(value = "hvacDehumid", ordinal = 14),
    @Range(value = "hvacCalibrate", ordinal = 15),
    @Range(value = "hvacEmergCool", ordinal = 16),
    @Range(value = "hvacEmergSteam", ordinal = 17),
    @Range(value = "hvacNul", ordinal = -1)
  },
  defaultValue = "hvacNul"
)
public final class BLonHvacEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonHvacEnum(336154991)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for hvacAuto. */
  public static final int HVAC_AUTO = 0;
  /** Ordinal value for hvacHeat. */
  public static final int HVAC_HEAT = 1;
  /** Ordinal value for hvacMrngWrmup. */
  public static final int HVAC_MRNG_WRMUP = 2;
  /** Ordinal value for hvacCool. */
  public static final int HVAC_COOL = 3;
  /** Ordinal value for hvacNightPurge. */
  public static final int HVAC_NIGHT_PURGE = 4;
  /** Ordinal value for hvacPreCool. */
  public static final int HVAC_PRE_COOL = 5;
  /** Ordinal value for hvacOff. */
  public static final int HVAC_OFF = 6;
  /** Ordinal value for hvacTest. */
  public static final int HVAC_TEST = 7;
  /** Ordinal value for hvacEmergHeat. */
  public static final int HVAC_EMERG_HEAT = 8;
  /** Ordinal value for hvacFanOnly. */
  public static final int HVAC_FAN_ONLY = 9;
  /** Ordinal value for hvacFreeCool. */
  public static final int HVAC_FREE_COOL = 10;
  /** Ordinal value for hvacIce. */
  public static final int HVAC_ICE = 11;
  /** Ordinal value for hvacMaxHeat. */
  public static final int HVAC_MAX_HEAT = 12;
  /** Ordinal value for hvacEconomy. */
  public static final int HVAC_ECONOMY = 13;
  /** Ordinal value for hvacDehumid. */
  public static final int HVAC_DEHUMID = 14;
  /** Ordinal value for hvacCalibrate. */
  public static final int HVAC_CALIBRATE = 15;
  /** Ordinal value for hvacEmergCool. */
  public static final int HVAC_EMERG_COOL = 16;
  /** Ordinal value for hvacEmergSteam. */
  public static final int HVAC_EMERG_STEAM = 17;
  /** Ordinal value for hvacNul. */
  public static final int HVAC_NUL = -1;

  /** BLonHvacEnum constant for hvacAuto. */
  public static final BLonHvacEnum hvacAuto = new BLonHvacEnum(HVAC_AUTO);
  /** BLonHvacEnum constant for hvacHeat. */
  public static final BLonHvacEnum hvacHeat = new BLonHvacEnum(HVAC_HEAT);
  /** BLonHvacEnum constant for hvacMrngWrmup. */
  public static final BLonHvacEnum hvacMrngWrmup = new BLonHvacEnum(HVAC_MRNG_WRMUP);
  /** BLonHvacEnum constant for hvacCool. */
  public static final BLonHvacEnum hvacCool = new BLonHvacEnum(HVAC_COOL);
  /** BLonHvacEnum constant for hvacNightPurge. */
  public static final BLonHvacEnum hvacNightPurge = new BLonHvacEnum(HVAC_NIGHT_PURGE);
  /** BLonHvacEnum constant for hvacPreCool. */
  public static final BLonHvacEnum hvacPreCool = new BLonHvacEnum(HVAC_PRE_COOL);
  /** BLonHvacEnum constant for hvacOff. */
  public static final BLonHvacEnum hvacOff = new BLonHvacEnum(HVAC_OFF);
  /** BLonHvacEnum constant for hvacTest. */
  public static final BLonHvacEnum hvacTest = new BLonHvacEnum(HVAC_TEST);
  /** BLonHvacEnum constant for hvacEmergHeat. */
  public static final BLonHvacEnum hvacEmergHeat = new BLonHvacEnum(HVAC_EMERG_HEAT);
  /** BLonHvacEnum constant for hvacFanOnly. */
  public static final BLonHvacEnum hvacFanOnly = new BLonHvacEnum(HVAC_FAN_ONLY);
  /** BLonHvacEnum constant for hvacFreeCool. */
  public static final BLonHvacEnum hvacFreeCool = new BLonHvacEnum(HVAC_FREE_COOL);
  /** BLonHvacEnum constant for hvacIce. */
  public static final BLonHvacEnum hvacIce = new BLonHvacEnum(HVAC_ICE);
  /** BLonHvacEnum constant for hvacMaxHeat. */
  public static final BLonHvacEnum hvacMaxHeat = new BLonHvacEnum(HVAC_MAX_HEAT);
  /** BLonHvacEnum constant for hvacEconomy. */
  public static final BLonHvacEnum hvacEconomy = new BLonHvacEnum(HVAC_ECONOMY);
  /** BLonHvacEnum constant for hvacDehumid. */
  public static final BLonHvacEnum hvacDehumid = new BLonHvacEnum(HVAC_DEHUMID);
  /** BLonHvacEnum constant for hvacCalibrate. */
  public static final BLonHvacEnum hvacCalibrate = new BLonHvacEnum(HVAC_CALIBRATE);
  /** BLonHvacEnum constant for hvacEmergCool. */
  public static final BLonHvacEnum hvacEmergCool = new BLonHvacEnum(HVAC_EMERG_COOL);
  /** BLonHvacEnum constant for hvacEmergSteam. */
  public static final BLonHvacEnum hvacEmergSteam = new BLonHvacEnum(HVAC_EMERG_STEAM);
  /** BLonHvacEnum constant for hvacNul. */
  public static final BLonHvacEnum hvacNul = new BLonHvacEnum(HVAC_NUL);

  /** Factory method with ordinal. */
  public static BLonHvacEnum make(int ordinal)
  {
    return (BLonHvacEnum)hvacAuto.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonHvacEnum make(String tag)
  {
    return (BLonHvacEnum)hvacAuto.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonHvacEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonHvacEnum DEFAULT = hvacNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonHvacEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
