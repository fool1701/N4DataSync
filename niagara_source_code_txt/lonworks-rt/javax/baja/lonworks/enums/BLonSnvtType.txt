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
 * The BLonSnvtType class provides an enumeration for 
 * all standard SNVT types
 *
 * @author    Robert Adams
 * @creation  14 Jan 01
 * @version   $Revision: 1$ $Date: 6/13/01 12:26:08 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("SnvtXxx"),
    @Range("SnvtAmp"),
    @Range("SnvtAmpMil"),
    @Range("SnvtAngle"),
    @Range("SnvtAngleVel"),
    @Range("SnvtBtuKilo"),
    @Range("SnvtBtuMega"),
    @Range("SnvtCharAscii"),
    @Range("SnvtCount"),
    @Range("SnvtCountInc"),
    @Range("SnvtDateCal"),
    @Range("SnvtDateDay"),
    @Range("SnvtDateTime"),
    @Range("SnvtElecKwh"),
    @Range("SnvtElecWhr"),
    @Range("SnvtFlow"),
    @Range("SnvtFlowMil"),
    @Range("SnvtLength"),
    @Range("SnvtLengthKilo"),
    @Range("SnvtLengthMicr"),
    @Range("SnvtLengthMil"),
    @Range("SnvtLevCont"),
    @Range("SnvtLevDisc"),
    @Range("SnvtMass"),
    @Range("SnvtMassKilo"),
    @Range("SnvtMassMega"),
    @Range("SnvtMassMil"),
    @Range("SnvtPower"),
    @Range("SnvtPowerKilo"),
    @Range("SnvtPpm"),
    @Range("SnvtPress"),
    @Range("SnvtRes"),
    @Range("SnvtResKilo"),
    @Range("SnvtSoundDb"),
    @Range("SnvtSpeed"),
    @Range("SnvtSpeedMil"),
    @Range("SnvtStrAsc"),
    @Range("SnvtStrInt"),
    @Range("SnvtTelcom"),
    @Range("SnvtTemp"),
    @Range("SnvtTimePassed"),
    @Range("SnvtVol"),
    @Range("SnvtVolKilo"),
    @Range("SnvtVolMil"),
    @Range("SnvtVolt"),
    @Range("SnvtVoltDbmv"),
    @Range("SnvtVoltKilo"),
    @Range("SnvtVoltMil"),
    @Range("SnvtAmpF"),
    @Range("SnvtAngleF"),
    @Range("SnvtAngleVelF"),
    @Range("SnvtCountF"),
    @Range("SnvtCountIncF"),
    @Range("SnvtFlowF"),
    @Range("SnvtLengthF"),
    @Range("SnvtLevContF"),
    @Range("SnvtMassF"),
    @Range("SnvtPowerF"),
    @Range("SnvtPpmF"),
    @Range("SnvtPressF"),
    @Range("SnvtResF"),
    @Range("SnvtSoundDbF"),
    @Range("SnvtSpeedF"),
    @Range("SnvtTempF"),
    @Range("SnvtTimeF"),
    @Range("SnvtVolF"),
    @Range("SnvtVoltF"),
    @Range("SnvtBtuF"),
    @Range("SnvtElecWhrF"),
    @Range("SnvtConfigSrc"),
    @Range("SnvtColor"),
    @Range("SnvtGrammage"),
    @Range("SnvtGrammageF"),
    @Range("SnvtFileReq"),
    @Range("SnvtFileStatus"),
    @Range("SnvtFreqF"),
    @Range("SnvtFreqHz"),
    @Range("SnvtFreqKilohz"),
    @Range("SnvtFreqMilhz"),
    @Range("SnvtLux"),
    @Range("SnvtIso7811"),
    @Range("SnvtLevPercent"),
    @Range("SnvtMultiplier"),
    @Range("SnvtState"),
    @Range("SnvtTimeStamp"),
    @Range("SnvtZerospan"),
    @Range("SnvtMagcard"),
    @Range("SnvtElapsedTm"),
    @Range("SnvtAlarm"),
    @Range("SnvtCurrency"),
    @Range("SnvtFilePos"),
    @Range("SnvtMuldiv"),
    @Range("SnvtObjRequest"),
    @Range("SnvtObjStatus"),
    @Range("SnvtPreset"),
    @Range("SnvtSwitch"),
    @Range("SnvtTransTable"),
    @Range("SnvtOverride"),
    @Range("SnvtPwrFact"),
    @Range("SnvtPwrFactF"),
    @Range("SnvtDensity"),
    @Range("SnvtDensityF"),
    @Range("SnvtRpm"),
    @Range("SnvtHvacEmerg"),
    @Range("SnvtAngleDeg"),
    @Range("SnvtTempP"),
    @Range("SnvtTempSetpt"),
    @Range("SnvtTimeSec"),
    @Range("SnvtHvacMode"),
    @Range("SnvtOccupancy"),
    @Range("SnvtArea"),
    @Range("SnvtHvacOverid"),
    @Range("SnvtHvacStatus"),
    @Range("SnvtPressP"),
    @Range("SnvtAddress"),
    @Range("SnvtScene"),
    @Range("SnvtSceneCfg"),
    @Range("SnvtSetting"),
    @Range("SnvtEvapState"),
    @Range("SnvtThermMode"),
    @Range("SnvtDefrMode"),
    @Range("SnvtDefrTerm"),
    @Range("SnvtDefrState"),
    @Range("SnvtTimeMin"),
    @Range("SnvtTimeHour"),
    @Range("SnvtPh"),
    @Range("SnvtPhF"),
    @Range("SnvtChlrStatus"),
    @Range("SnvtTodEvent"),
    @Range("SnvtSmoObscur"),
    @Range("SnvtFireTest"),
    @Range("SnvtTempRor"),
    @Range("SnvtFireInit"),
    @Range("SnvtFireIndcte"),
    @Range("SnvtTimeZone"),
    @Range("SnvtEarthPos"),
    @Range("SnvtRegVal"),
    @Range("SnvtRegValTs"),
    @Range("SnvtVoltAc"),
    @Range("SnvtAmpAc"),
    @Range(value = "SnvtTurbidity", ordinal = 143),
    @Range(value = "SnvtTurbidityF", ordinal = 144),
    @Range(value = "SnvtHvacType", ordinal = 145),
    @Range(value = "SnvtElecKwhL", ordinal = 146),
    @Range(value = "SnvtTempDiffP", ordinal = 147),
    @Range(value = "SnvtCtrlReq", ordinal = 148),
    @Range(value = "SnvtCtrlResp", ordinal = 149),
    @Range(value = "SnvtPtz", ordinal = 150),
    @Range(value = "SnvtPrivacyzone", ordinal = 151),
    @Range(value = "SnvtPosCtrl", ordinal = 152),
    @Range(value = "SnvtEnthalpy", ordinal = 153),
    @Range(value = "SnvtGfciStatus", ordinal = 154),
    @Range(value = "SnvtMotorState", ordinal = 155),
    @Range(value = "SnvtPumpsetMn", ordinal = 156),
    @Range(value = "SnvtExControl", ordinal = 157),
    @Range(value = "SnvtPumpsetSn", ordinal = 158),
    @Range(value = "SnvtPumpSensor", ordinal = 159),
    @Range(value = "SnvtAbsHumid", ordinal = 160),
    @Range(value = "SnvtFlowP", ordinal = 161),
    @Range(value = "SnvtDevCMode", ordinal = 162),
    @Range(value = "SnvtValveMode", ordinal = 163),
    @Range(value = "SnvtAlarm2", ordinal = 164),
    @Range(value = "SnvtState64", ordinal = 165),
    @Range(value = "SnvtNvType", ordinal = 166),
    @Range(value = "SnvtEntOpmode", ordinal = 168),
    @Range(value = "SnvtEntState", ordinal = 169),
    @Range(value = "SnvtEntStatus", ordinal = 170),
    @Range(value = "SnvtFlowDir", ordinal = 171),
    @Range(value = "SnvtHvacSatsts", ordinal = 172),
    @Range(value = "SnvtDevStatus", ordinal = 173),
    @Range(value = "SnvtDevFault", ordinal = 174),
    @Range(value = "SnvtDevMaint", ordinal = 175),
    @Range(value = "SnvtDateEvent", ordinal = 176),
    @Range(value = "SnvtSchedVal", ordinal = 177),
    @Range(value = "SnvtSecState", ordinal = 178),
    @Range(value = "SnvtSecStatus", ordinal = 179),
    @Range(value = "SnvtSblndState", ordinal = 180),
    @Range(value = "SnvtRacCtrl", ordinal = 181),
    @Range(value = "SnvtRacReq", ordinal = 182),
    @Range(value = "SnvtCount32", ordinal = 183),
    @Range(value = "SnvtClothesWC", ordinal = 184),
    @Range(value = "SnvtClothesWM", ordinal = 185),
    @Range(value = "SnvtClothesWS", ordinal = 186),
    @Range(value = "SnvtClothesWA", ordinal = 187),
    @Range(value = "SnvtMultiplierS", ordinal = 188),
    @Range(value = "SnvtSwitch2", ordinal = 189),
    @Range(value = "SnvtColor2", ordinal = 190),
    @Range(value = "SnvtLogStatus", ordinal = 191),
    @Range(value = "SnvtTimeStampP", ordinal = 192),
    @Range(value = "SnvtLogFxRequest", ordinal = 193),
    @Range(value = "SnvtLogFxStatus", ordinal = 194),
    @Range(value = "SnvtLogRequest", ordinal = 195),
    @Range(value = "SnvtEnthalpyD", ordinal = 196),
    @Range(value = "SnvtAmpAcMil", ordinal = 197),
    @Range(value = "SnvtTimeHourP", ordinal = 198),
    @Range(value = "SnvtLampStatus", ordinal = 199),
    @Range(value = "SnvtEnvironment", ordinal = 200),
    @Range(value = "SnvtGeoLoc", ordinal = 201),
    @Range(value = "SnvtProgramStatus", ordinal = 202),
    @Range(value = "SnvtLoadOffsets", ordinal = 203),
    @Range(value = "SnvtWm2P", ordinal = 204),
    @Range(value = "SnvtSafe1", ordinal = 205),
    @Range(value = "SnvtSafe2", ordinal = 206),
    @Range(value = "SnvtSafe4", ordinal = 207),
    @Range(value = "SnvtSafe8", ordinal = 208),
    @Range(value = "SnvtTimeVal2", ordinal = 209),
    @Range(value = "SnvtTimeOffset", ordinal = 210),
    @Range(value = "SnvtSchedExc", ordinal = 211),
    @Range(value = "SnvtSchedStatus", ordinal = 212),
    @Range(value = "SnvtMassFlow", ordinal = 213),
    @Range(value = "SnvtMassFlowF", ordinal = 214)
  }
)
//Use NoSlotomatic since legacy SnvtTypes SNVT_COUNT32, SNVT_WM_2_P newly autogenerated ordinals don't match
//existing ordinal names. Need to maintain the previous names.
@NoSlotomatic
public final class BLonSnvtType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonSnvtType(1743505305)1.0$ @*/
/* Generated Tue Aug 10 12:49:29 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  /** Ordinal value for SnvtXxx. */
  public static final int SNVT_XXX = 0;
  /** Ordinal value for SnvtAmp. */
  public static final int SNVT_AMP = 1;
  /** Ordinal value for SnvtAmpMil. */
  public static final int SNVT_AMP_MIL = 2;
  /** Ordinal value for SnvtAngle. */
  public static final int SNVT_ANGLE = 3;
  /** Ordinal value for SnvtAngleVel. */
  public static final int SNVT_ANGLE_VEL = 4;
  /** Ordinal value for SnvtBtuKilo. */
  public static final int SNVT_BTU_KILO = 5;
  /** Ordinal value for SnvtBtuMega. */
  public static final int SNVT_BTU_MEGA = 6;
  /** Ordinal value for SnvtCharAscii. */
  public static final int SNVT_CHAR_ASCII = 7;
  /** Ordinal value for SnvtCount. */
  public static final int SNVT_COUNT = 8;
  /** Ordinal value for SnvtCountInc. */
  public static final int SNVT_COUNT_INC = 9;
  /** Ordinal value for SnvtDateCal. */
  public static final int SNVT_DATE_CAL = 10;
  /** Ordinal value for SnvtDateDay. */
  public static final int SNVT_DATE_DAY = 11;
  /** Ordinal value for SnvtDateTime. */
  public static final int SNVT_DATE_TIME = 12;
  /** Ordinal value for SnvtElecKwh. */
  public static final int SNVT_ELEC_KWH = 13;
  /** Ordinal value for SnvtElecWhr. */
  public static final int SNVT_ELEC_WHR = 14;
  /** Ordinal value for SnvtFlow. */
  public static final int SNVT_FLOW = 15;
  /** Ordinal value for SnvtFlowMil. */
  public static final int SNVT_FLOW_MIL = 16;
  /** Ordinal value for SnvtLength. */
  public static final int SNVT_LENGTH = 17;
  /** Ordinal value for SnvtLengthKilo. */
  public static final int SNVT_LENGTH_KILO = 18;
  /** Ordinal value for SnvtLengthMicr. */
  public static final int SNVT_LENGTH_MICR = 19;
  /** Ordinal value for SnvtLengthMil. */
  public static final int SNVT_LENGTH_MIL = 20;
  /** Ordinal value for SnvtLevCont. */
  public static final int SNVT_LEV_CONT = 21;
  /** Ordinal value for SnvtLevDisc. */
  public static final int SNVT_LEV_DISC = 22;
  /** Ordinal value for SnvtMass. */
  public static final int SNVT_MASS = 23;
  /** Ordinal value for SnvtMassKilo. */
  public static final int SNVT_MASS_KILO = 24;
  /** Ordinal value for SnvtMassMega. */
  public static final int SNVT_MASS_MEGA = 25;
  /** Ordinal value for SnvtMassMil. */
  public static final int SNVT_MASS_MIL = 26;
  /** Ordinal value for SnvtPower. */
  public static final int SNVT_POWER = 27;
  /** Ordinal value for SnvtPowerKilo. */
  public static final int SNVT_POWER_KILO = 28;
  /** Ordinal value for SnvtPpm. */
  public static final int SNVT_PPM = 29;
  /** Ordinal value for SnvtPress. */
  public static final int SNVT_PRESS = 30;
  /** Ordinal value for SnvtRes. */
  public static final int SNVT_RES = 31;
  /** Ordinal value for SnvtResKilo. */
  public static final int SNVT_RES_KILO = 32;
  /** Ordinal value for SnvtSoundDb. */
  public static final int SNVT_SOUND_DB = 33;
  /** Ordinal value for SnvtSpeed. */
  public static final int SNVT_SPEED = 34;
  /** Ordinal value for SnvtSpeedMil. */
  public static final int SNVT_SPEED_MIL = 35;
  /** Ordinal value for SnvtStrAsc. */
  public static final int SNVT_STR_ASC = 36;
  /** Ordinal value for SnvtStrInt. */
  public static final int SNVT_STR_INT = 37;
  /** Ordinal value for SnvtTelcom. */
  public static final int SNVT_TELCOM = 38;
  /** Ordinal value for SnvtTemp. */
  public static final int SNVT_TEMP = 39;
  /** Ordinal value for SnvtTimePassed. */
  public static final int SNVT_TIME_PASSED = 40;
  /** Ordinal value for SnvtVol. */
  public static final int SNVT_VOL = 41;
  /** Ordinal value for SnvtVolKilo. */
  public static final int SNVT_VOL_KILO = 42;
  /** Ordinal value for SnvtVolMil. */
  public static final int SNVT_VOL_MIL = 43;
  /** Ordinal value for SnvtVolt. */
  public static final int SNVT_VOLT = 44;
  /** Ordinal value for SnvtVoltDbmv. */
  public static final int SNVT_VOLT_DBMV = 45;
  /** Ordinal value for SnvtVoltKilo. */
  public static final int SNVT_VOLT_KILO = 46;
  /** Ordinal value for SnvtVoltMil. */
  public static final int SNVT_VOLT_MIL = 47;
  /** Ordinal value for SnvtAmpF. */
  public static final int SNVT_AMP_F = 48;
  /** Ordinal value for SnvtAngleF. */
  public static final int SNVT_ANGLE_F = 49;
  /** Ordinal value for SnvtAngleVelF. */
  public static final int SNVT_ANGLE_VEL_F = 50;
  /** Ordinal value for SnvtCountF. */
  public static final int SNVT_COUNT_F = 51;
  /** Ordinal value for SnvtCountIncF. */
  public static final int SNVT_COUNT_INC_F = 52;
  /** Ordinal value for SnvtFlowF. */
  public static final int SNVT_FLOW_F = 53;
  /** Ordinal value for SnvtLengthF. */
  public static final int SNVT_LENGTH_F = 54;
  /** Ordinal value for SnvtLevContF. */
  public static final int SNVT_LEV_CONT_F = 55;
  /** Ordinal value for SnvtMassF. */
  public static final int SNVT_MASS_F = 56;
  /** Ordinal value for SnvtPowerF. */
  public static final int SNVT_POWER_F = 57;
  /** Ordinal value for SnvtPpmF. */
  public static final int SNVT_PPM_F = 58;
  /** Ordinal value for SnvtPressF. */
  public static final int SNVT_PRESS_F = 59;
  /** Ordinal value for SnvtResF. */
  public static final int SNVT_RES_F = 60;
  /** Ordinal value for SnvtSoundDbF. */
  public static final int SNVT_SOUND_DB_F = 61;
  /** Ordinal value for SnvtSpeedF. */
  public static final int SNVT_SPEED_F = 62;
  /** Ordinal value for SnvtTempF. */
  public static final int SNVT_TEMP_F = 63;
  /** Ordinal value for SnvtTimeF. */
  public static final int SNVT_TIME_F = 64;
  /** Ordinal value for SnvtVolF. */
  public static final int SNVT_VOL_F = 65;
  /** Ordinal value for SnvtVoltF. */
  public static final int SNVT_VOLT_F = 66;
  /** Ordinal value for SnvtBtuF. */
  public static final int SNVT_BTU_F = 67;
  /** Ordinal value for SnvtElecWhrF. */
  public static final int SNVT_ELEC_WHR_F = 68;
  /** Ordinal value for SnvtConfigSrc. */
  public static final int SNVT_CONFIG_SRC = 69;
  /** Ordinal value for SnvtColor. */
  public static final int SNVT_COLOR = 70;
  /** Ordinal value for SnvtGrammage. */
  public static final int SNVT_GRAMMAGE = 71;
  /** Ordinal value for SnvtGrammageF. */
  public static final int SNVT_GRAMMAGE_F = 72;
  /** Ordinal value for SnvtFileReq. */
  public static final int SNVT_FILE_REQ = 73;
  /** Ordinal value for SnvtFileStatus. */
  public static final int SNVT_FILE_STATUS = 74;
  /** Ordinal value for SnvtFreqF. */
  public static final int SNVT_FREQ_F = 75;
  /** Ordinal value for SnvtFreqHz. */
  public static final int SNVT_FREQ_HZ = 76;
  /** Ordinal value for SnvtFreqKilohz. */
  public static final int SNVT_FREQ_KILOHZ = 77;
  /** Ordinal value for SnvtFreqMilhz. */
  public static final int SNVT_FREQ_MILHZ = 78;
  /** Ordinal value for SnvtLux. */
  public static final int SNVT_LUX = 79;
  /** Ordinal value for SnvtIso7811. */
  public static final int SNVT_ISO_7811 = 80;
  /** Ordinal value for SnvtLevPercent. */
  public static final int SNVT_LEV_PERCENT = 81;
  /** Ordinal value for SnvtMultiplier. */
  public static final int SNVT_MULTIPLIER = 82;
  /** Ordinal value for SnvtState. */
  public static final int SNVT_STATE = 83;
  /** Ordinal value for SnvtTimeStamp. */
  public static final int SNVT_TIME_STAMP = 84;
  /** Ordinal value for SnvtZerospan. */
  public static final int SNVT_ZEROSPAN = 85;
  /** Ordinal value for SnvtMagcard. */
  public static final int SNVT_MAGCARD = 86;
  /** Ordinal value for SnvtElapsedTm. */
  public static final int SNVT_ELAPSED_TM = 87;
  /** Ordinal value for SnvtAlarm. */
  public static final int SNVT_ALARM = 88;
  /** Ordinal value for SnvtCurrency. */
  public static final int SNVT_CURRENCY = 89;
  /** Ordinal value for SnvtFilePos. */
  public static final int SNVT_FILE_POS = 90;
  /** Ordinal value for SnvtMuldiv. */
  public static final int SNVT_MULDIV = 91;
  /** Ordinal value for SnvtObjRequest. */
  public static final int SNVT_OBJ_REQUEST = 92;
  /** Ordinal value for SnvtObjStatus. */
  public static final int SNVT_OBJ_STATUS = 93;
  /** Ordinal value for SnvtPreset. */
  public static final int SNVT_PRESET = 94;
  /** Ordinal value for SnvtSwitch. */
  public static final int SNVT_SWITCH = 95;
  /** Ordinal value for SnvtTransTable. */
  public static final int SNVT_TRANS_TABLE = 96;
  /** Ordinal value for SnvtOverride. */
  public static final int SNVT_OVERRIDE = 97;
  /** Ordinal value for SnvtPwrFact. */
  public static final int SNVT_PWR_FACT = 98;
  /** Ordinal value for SnvtPwrFactF. */
  public static final int SNVT_PWR_FACT_F = 99;
  /** Ordinal value for SnvtDensity. */
  public static final int SNVT_DENSITY = 100;
  /** Ordinal value for SnvtDensityF. */
  public static final int SNVT_DENSITY_F = 101;
  /** Ordinal value for SnvtRpm. */
  public static final int SNVT_RPM = 102;
  /** Ordinal value for SnvtHvacEmerg. */
  public static final int SNVT_HVAC_EMERG = 103;
  /** Ordinal value for SnvtAngleDeg. */
  public static final int SNVT_ANGLE_DEG = 104;
  /** Ordinal value for SnvtTempP. */
  public static final int SNVT_TEMP_P = 105;
  /** Ordinal value for SnvtTempSetpt. */
  public static final int SNVT_TEMP_SETPT = 106;
  /** Ordinal value for SnvtTimeSec. */
  public static final int SNVT_TIME_SEC = 107;
  /** Ordinal value for SnvtHvacMode. */
  public static final int SNVT_HVAC_MODE = 108;
  /** Ordinal value for SnvtOccupancy. */
  public static final int SNVT_OCCUPANCY = 109;
  /** Ordinal value for SnvtArea. */
  public static final int SNVT_AREA = 110;
  /** Ordinal value for SnvtHvacOverid. */
  public static final int SNVT_HVAC_OVERID = 111;
  /** Ordinal value for SnvtHvacStatus. */
  public static final int SNVT_HVAC_STATUS = 112;
  /** Ordinal value for SnvtPressP. */
  public static final int SNVT_PRESS_P = 113;
  /** Ordinal value for SnvtAddress. */
  public static final int SNVT_ADDRESS = 114;
  /** Ordinal value for SnvtScene. */
  public static final int SNVT_SCENE = 115;
  /** Ordinal value for SnvtSceneCfg. */
  public static final int SNVT_SCENE_CFG = 116;
  /** Ordinal value for SnvtSetting. */
  public static final int SNVT_SETTING = 117;
  /** Ordinal value for SnvtEvapState. */
  public static final int SNVT_EVAP_STATE = 118;
  /** Ordinal value for SnvtThermMode. */
  public static final int SNVT_THERM_MODE = 119;
  /** Ordinal value for SnvtDefrMode. */
  public static final int SNVT_DEFR_MODE = 120;
  /** Ordinal value for SnvtDefrTerm. */
  public static final int SNVT_DEFR_TERM = 121;
  /** Ordinal value for SnvtDefrState. */
  public static final int SNVT_DEFR_STATE = 122;
  /** Ordinal value for SnvtTimeMin. */
  public static final int SNVT_TIME_MIN = 123;
  /** Ordinal value for SnvtTimeHour. */
  public static final int SNVT_TIME_HOUR = 124;
  /** Ordinal value for SnvtPh. */
  public static final int SNVT_PH = 125;
  /** Ordinal value for SnvtPhF. */
  public static final int SNVT_PH_F = 126;
  /** Ordinal value for SnvtChlrStatus. */
  public static final int SNVT_CHLR_STATUS = 127;
  /** Ordinal value for SnvtTodEvent. */
  public static final int SNVT_TOD_EVENT = 128;
  /** Ordinal value for SnvtSmoObscur. */
  public static final int SNVT_SMO_OBSCUR = 129;
  /** Ordinal value for SnvtFireTest. */
  public static final int SNVT_FIRE_TEST = 130;
  /** Ordinal value for SnvtTempRor. */
  public static final int SNVT_TEMP_ROR = 131;
  /** Ordinal value for SnvtFireInit. */
  public static final int SNVT_FIRE_INIT = 132;
  /** Ordinal value for SnvtFireIndcte. */
  public static final int SNVT_FIRE_INDCTE = 133;
  /** Ordinal value for SnvtTimeZone. */
  public static final int SNVT_TIME_ZONE = 134;
  /** Ordinal value for SnvtEarthPos. */
  public static final int SNVT_EARTH_POS = 135;
  /** Ordinal value for SnvtRegVal. */
  public static final int SNVT_REG_VAL = 136;
  /** Ordinal value for SnvtRegValTs. */
  public static final int SNVT_REG_VAL_TS = 137;
  /** Ordinal value for SnvtVoltAc. */
  public static final int SNVT_VOLT_AC = 138;
  /** Ordinal value for SnvtAmpAc. */
  public static final int SNVT_AMP_AC = 139;
  /** Ordinal value for SnvtTurbidity. */
  public static final int SNVT_TURBIDITY = 143;
  /** Ordinal value for SnvtTurbidityF. */
  public static final int SNVT_TURBIDITY_F = 144;
  /** Ordinal value for SnvtHvacType. */
  public static final int SNVT_HVAC_TYPE = 145;
  /** Ordinal value for SnvtElecKwhL. */
  public static final int SNVT_ELEC_KWH_L = 146;
  /** Ordinal value for SnvtTempDiffP. */
  public static final int SNVT_TEMP_DIFF_P = 147;
  /** Ordinal value for SnvtCtrlReq. */
  public static final int SNVT_CTRL_REQ = 148;
  /** Ordinal value for SnvtCtrlResp. */
  public static final int SNVT_CTRL_RESP = 149;
  /** Ordinal value for SnvtPtz. */
  public static final int SNVT_PTZ = 150;
  /** Ordinal value for SnvtPrivacyzone. */
  public static final int SNVT_PRIVACYZONE = 151;
  /** Ordinal value for SnvtPosCtrl. */
  public static final int SNVT_POS_CTRL = 152;
  /** Ordinal value for SnvtEnthalpy. */
  public static final int SNVT_ENTHALPY = 153;
  /** Ordinal value for SnvtGfciStatus. */
  public static final int SNVT_GFCI_STATUS = 154;
  /** Ordinal value for SnvtMotorState. */
  public static final int SNVT_MOTOR_STATE = 155;
  /** Ordinal value for SnvtPumpsetMn. */
  public static final int SNVT_PUMPSET_MN = 156;
  /** Ordinal value for SnvtExControl. */
  public static final int SNVT_EX_CONTROL = 157;
  /** Ordinal value for SnvtPumpsetSn. */
  public static final int SNVT_PUMPSET_SN = 158;
  /** Ordinal value for SnvtPumpSensor. */
  public static final int SNVT_PUMP_SENSOR = 159;
  /** Ordinal value for SnvtAbsHumid. */
  public static final int SNVT_ABS_HUMID = 160;
  /** Ordinal value for SnvtFlowP. */
  public static final int SNVT_FLOW_P = 161;
  /** Ordinal value for SnvtDevCMode. */
  public static final int SNVT_DEV_CMODE = 162;
  /** Ordinal value for SnvtValveMode. */
  public static final int SNVT_VALVE_MODE = 163;
  /** Ordinal value for SnvtAlarm2. */
  public static final int SNVT_ALARM_2 = 164;
  /** Ordinal value for SnvtState64. */
  public static final int SNVT_STATE_64 = 165;
  /** Ordinal value for SnvtNvType. */
  public static final int SNVT_NV_TYPE = 166;
  /** Ordinal value for SnvtEntOpmode. */
  public static final int SNVT_ENT_OPMODE = 168;
  /** Ordinal value for SnvtEntState. */
  public static final int SNVT_ENT_STATE = 169;
  /** Ordinal value for SnvtEntStatus. */
  public static final int SNVT_ENT_STATUS = 170;
  /** Ordinal value for SnvtFlowDir. */
  public static final int SNVT_FLOW_DIR = 171;
  /** Ordinal value for SnvtHvacSatsts. */
  public static final int SNVT_HVAC_SATSTS = 172;
  /** Ordinal value for SnvtDevStatus. */
  public static final int SNVT_DEV_STATUS = 173;
  /** Ordinal value for SnvtDevFault. */
  public static final int SNVT_DEV_FAULT = 174;
  /** Ordinal value for SnvtDevMaint. */
  public static final int SNVT_DEV_MAINT = 175;
  /** Ordinal value for SnvtDateEvent. */
  public static final int SNVT_DATE_EVENT = 176;
  /** Ordinal value for SnvtSchedVal. */
  public static final int SNVT_SCHED_VAL = 177;
  /** Ordinal value for SnvtSecState. */
  public static final int SNVT_SEC_STATE = 178;
  /** Ordinal value for SnvtSecStatus. */
  public static final int SNVT_SEC_STATUS = 179;
  /** Ordinal value for SnvtSblndState. */
  public static final int SNVT_SBLND_STATE = 180;
  /** Ordinal value for SnvtRacCtrl. */
  public static final int SNVT_RAC_CTRL = 181;
  /** Ordinal value for SnvtRacReq. */
  public static final int SNVT_RAC_REQ = 182;
  /** Ordinal value for SnvtCount32. */
  public static final int SNVT_COUNT32 = 183;
  /** Ordinal value for SnvtClothesWC. */
  public static final int SNVT_CLOTHES_WC = 184;
  /** Ordinal value for SnvtClothesWM. */
  public static final int SNVT_CLOTHES_WM = 185;
  /** Ordinal value for SnvtClothesWS. */
  public static final int SNVT_CLOTHES_WS = 186;
  /** Ordinal value for SnvtClothesWA. */
  public static final int SNVT_CLOTHES_WA = 187;
  /** Ordinal value for SnvtMultiplierS. */
  public static final int SNVT_MULTIPLIER_S = 188;
  /** Ordinal value for SnvtSwitch2. */
  public static final int SNVT_SWITCH_2 = 189;
  /** Ordinal value for SnvtColor2. */
  public static final int SNVT_COLOR_2 = 190;
  /** Ordinal value for SnvtLogStatus. */
  public static final int SNVT_LOG_STATUS = 191;
  /** Ordinal value for SnvtTimeStampP. */
  public static final int SNVT_TIME_STAMP_P = 192;
  /** Ordinal value for SnvtLogFxRequest. */
  public static final int SNVT_LOG_FX_REQUEST = 193;
  /** Ordinal value for SnvtLogFxStatus. */
  public static final int SNVT_LOG_FX_STATUS = 194;
  /** Ordinal value for SnvtLogRequest. */
  public static final int SNVT_LOG_REQUEST = 195;
  /** Ordinal value for SnvtEnthalpyD. */
  public static final int SNVT_ENTHALPY_D = 196;
  /** Ordinal value for SnvtAmpAcMil. */
  public static final int SNVT_AMP_AC_MIL = 197;
  /** Ordinal value for SnvtTimeHourP. */
  public static final int SNVT_TIME_HOUR_P = 198;
  /** Ordinal value for SnvtLampStatus. */
  public static final int SNVT_LAMP_STATUS = 199;
  /** Ordinal value for SnvtEnvironment. */
  public static final int SNVT_ENVIRONMENT = 200;
  /** Ordinal value for SnvtGeoLoc. */
  public static final int SNVT_GEO_LOC = 201;
  /** Ordinal value for SnvtProgramStatus. */
  public static final int SNVT_PROGRAM_STATUS = 202;
  /** Ordinal value for SnvtLoadOffsets. */
  public static final int SNVT_LOAD_OFFSETS = 203;
  /** Ordinal value for SnvtWm2P. */
  public static final int SNVT_WM_2_P = 204;
  /** Ordinal value for SnvtSafe1. */
  public static final int SNVT_SAFE_1 = 205;
  /** Ordinal value for SnvtSafe2. */
  public static final int SNVT_SAFE_2 = 206;
  /** Ordinal value for SnvtSafe4. */
  public static final int SNVT_SAFE_4 = 207;
  /** Ordinal value for SnvtSafe8. */
  public static final int SNVT_SAFE_8 = 208;
  /** Ordinal value for SnvtTimeVal2. */
  public static final int SNVT_TIME_VAL_2 = 209;
  /** Ordinal value for SnvtTimeOffset. */
  public static final int SNVT_TIME_OFFSET = 210;
  /** Ordinal value for SnvtSchedExc. */
  public static final int SNVT_SCHED_EXC = 211;
  /** Ordinal value for SnvtSchedStatus. */
  public static final int SNVT_SCHED_STATUS = 212;
  /** Ordinal value for SnvtMassFlow. */
  public static final int SNVT_MASS_FLOW = 213;
  /** Ordinal value for SnvtMassFlowF. */
  public static final int SNVT_MASS_FLOW_F = 214;

  /** BLonSnvtType constant for SnvtXxx. */
  public static final BLonSnvtType SnvtXxx = new BLonSnvtType(SNVT_XXX);
  /** BLonSnvtType constant for SnvtAmp. */
  public static final BLonSnvtType SnvtAmp = new BLonSnvtType(SNVT_AMP);
  /** BLonSnvtType constant for SnvtAmpMil. */
  public static final BLonSnvtType SnvtAmpMil = new BLonSnvtType(SNVT_AMP_MIL);
  /** BLonSnvtType constant for SnvtAngle. */
  public static final BLonSnvtType SnvtAngle = new BLonSnvtType(SNVT_ANGLE);
  /** BLonSnvtType constant for SnvtAngleVel. */
  public static final BLonSnvtType SnvtAngleVel = new BLonSnvtType(SNVT_ANGLE_VEL);
  /** BLonSnvtType constant for SnvtBtuKilo. */
  public static final BLonSnvtType SnvtBtuKilo = new BLonSnvtType(SNVT_BTU_KILO);
  /** BLonSnvtType constant for SnvtBtuMega. */
  public static final BLonSnvtType SnvtBtuMega = new BLonSnvtType(SNVT_BTU_MEGA);
  /** BLonSnvtType constant for SnvtCharAscii. */
  public static final BLonSnvtType SnvtCharAscii = new BLonSnvtType(SNVT_CHAR_ASCII);
  /** BLonSnvtType constant for SnvtCount. */
  public static final BLonSnvtType SnvtCount = new BLonSnvtType(SNVT_COUNT);
  /** BLonSnvtType constant for SnvtCountInc. */
  public static final BLonSnvtType SnvtCountInc = new BLonSnvtType(SNVT_COUNT_INC);
  /** BLonSnvtType constant for SnvtDateCal. */
  public static final BLonSnvtType SnvtDateCal = new BLonSnvtType(SNVT_DATE_CAL);
  /** BLonSnvtType constant for SnvtDateDay. */
  public static final BLonSnvtType SnvtDateDay = new BLonSnvtType(SNVT_DATE_DAY);
  /** BLonSnvtType constant for SnvtDateTime. */
  public static final BLonSnvtType SnvtDateTime = new BLonSnvtType(SNVT_DATE_TIME);
  /** BLonSnvtType constant for SnvtElecKwh. */
  public static final BLonSnvtType SnvtElecKwh = new BLonSnvtType(SNVT_ELEC_KWH);
  /** BLonSnvtType constant for SnvtElecWhr. */
  public static final BLonSnvtType SnvtElecWhr = new BLonSnvtType(SNVT_ELEC_WHR);
  /** BLonSnvtType constant for SnvtFlow. */
  public static final BLonSnvtType SnvtFlow = new BLonSnvtType(SNVT_FLOW);
  /** BLonSnvtType constant for SnvtFlowMil. */
  public static final BLonSnvtType SnvtFlowMil = new BLonSnvtType(SNVT_FLOW_MIL);
  /** BLonSnvtType constant for SnvtLength. */
  public static final BLonSnvtType SnvtLength = new BLonSnvtType(SNVT_LENGTH);
  /** BLonSnvtType constant for SnvtLengthKilo. */
  public static final BLonSnvtType SnvtLengthKilo = new BLonSnvtType(SNVT_LENGTH_KILO);
  /** BLonSnvtType constant for SnvtLengthMicr. */
  public static final BLonSnvtType SnvtLengthMicr = new BLonSnvtType(SNVT_LENGTH_MICR);
  /** BLonSnvtType constant for SnvtLengthMil. */
  public static final BLonSnvtType SnvtLengthMil = new BLonSnvtType(SNVT_LENGTH_MIL);
  /** BLonSnvtType constant for SnvtLevCont. */
  public static final BLonSnvtType SnvtLevCont = new BLonSnvtType(SNVT_LEV_CONT);
  /** BLonSnvtType constant for SnvtLevDisc. */
  public static final BLonSnvtType SnvtLevDisc = new BLonSnvtType(SNVT_LEV_DISC);
  /** BLonSnvtType constant for SnvtMass. */
  public static final BLonSnvtType SnvtMass = new BLonSnvtType(SNVT_MASS);
  /** BLonSnvtType constant for SnvtMassKilo. */
  public static final BLonSnvtType SnvtMassKilo = new BLonSnvtType(SNVT_MASS_KILO);
  /** BLonSnvtType constant for SnvtMassMega. */
  public static final BLonSnvtType SnvtMassMega = new BLonSnvtType(SNVT_MASS_MEGA);
  /** BLonSnvtType constant for SnvtMassMil. */
  public static final BLonSnvtType SnvtMassMil = new BLonSnvtType(SNVT_MASS_MIL);
  /** BLonSnvtType constant for SnvtPower. */
  public static final BLonSnvtType SnvtPower = new BLonSnvtType(SNVT_POWER);
  /** BLonSnvtType constant for SnvtPowerKilo. */
  public static final BLonSnvtType SnvtPowerKilo = new BLonSnvtType(SNVT_POWER_KILO);
  /** BLonSnvtType constant for SnvtPpm. */
  public static final BLonSnvtType SnvtPpm = new BLonSnvtType(SNVT_PPM);
  /** BLonSnvtType constant for SnvtPress. */
  public static final BLonSnvtType SnvtPress = new BLonSnvtType(SNVT_PRESS);
  /** BLonSnvtType constant for SnvtRes. */
  public static final BLonSnvtType SnvtRes = new BLonSnvtType(SNVT_RES);
  /** BLonSnvtType constant for SnvtResKilo. */
  public static final BLonSnvtType SnvtResKilo = new BLonSnvtType(SNVT_RES_KILO);
  /** BLonSnvtType constant for SnvtSoundDb. */
  public static final BLonSnvtType SnvtSoundDb = new BLonSnvtType(SNVT_SOUND_DB);
  /** BLonSnvtType constant for SnvtSpeed. */
  public static final BLonSnvtType SnvtSpeed = new BLonSnvtType(SNVT_SPEED);
  /** BLonSnvtType constant for SnvtSpeedMil. */
  public static final BLonSnvtType SnvtSpeedMil = new BLonSnvtType(SNVT_SPEED_MIL);
  /** BLonSnvtType constant for SnvtStrAsc. */
  public static final BLonSnvtType SnvtStrAsc = new BLonSnvtType(SNVT_STR_ASC);
  /** BLonSnvtType constant for SnvtStrInt. */
  public static final BLonSnvtType SnvtStrInt = new BLonSnvtType(SNVT_STR_INT);
  /** BLonSnvtType constant for SnvtTelcom. */
  public static final BLonSnvtType SnvtTelcom = new BLonSnvtType(SNVT_TELCOM);
  /** BLonSnvtType constant for SnvtTemp. */
  public static final BLonSnvtType SnvtTemp = new BLonSnvtType(SNVT_TEMP);
  /** BLonSnvtType constant for SnvtTimePassed. */
  public static final BLonSnvtType SnvtTimePassed = new BLonSnvtType(SNVT_TIME_PASSED);
  /** BLonSnvtType constant for SnvtVol. */
  public static final BLonSnvtType SnvtVol = new BLonSnvtType(SNVT_VOL);
  /** BLonSnvtType constant for SnvtVolKilo. */
  public static final BLonSnvtType SnvtVolKilo = new BLonSnvtType(SNVT_VOL_KILO);
  /** BLonSnvtType constant for SnvtVolMil. */
  public static final BLonSnvtType SnvtVolMil = new BLonSnvtType(SNVT_VOL_MIL);
  /** BLonSnvtType constant for SnvtVolt. */
  public static final BLonSnvtType SnvtVolt = new BLonSnvtType(SNVT_VOLT);
  /** BLonSnvtType constant for SnvtVoltDbmv. */
  public static final BLonSnvtType SnvtVoltDbmv = new BLonSnvtType(SNVT_VOLT_DBMV);
  /** BLonSnvtType constant for SnvtVoltKilo. */
  public static final BLonSnvtType SnvtVoltKilo = new BLonSnvtType(SNVT_VOLT_KILO);
  /** BLonSnvtType constant for SnvtVoltMil. */
  public static final BLonSnvtType SnvtVoltMil = new BLonSnvtType(SNVT_VOLT_MIL);
  /** BLonSnvtType constant for SnvtAmpF. */
  public static final BLonSnvtType SnvtAmpF = new BLonSnvtType(SNVT_AMP_F);
  /** BLonSnvtType constant for SnvtAngleF. */
  public static final BLonSnvtType SnvtAngleF = new BLonSnvtType(SNVT_ANGLE_F);
  /** BLonSnvtType constant for SnvtAngleVelF. */
  public static final BLonSnvtType SnvtAngleVelF = new BLonSnvtType(SNVT_ANGLE_VEL_F);
  /** BLonSnvtType constant for SnvtCountF. */
  public static final BLonSnvtType SnvtCountF = new BLonSnvtType(SNVT_COUNT_F);
  /** BLonSnvtType constant for SnvtCountIncF. */
  public static final BLonSnvtType SnvtCountIncF = new BLonSnvtType(SNVT_COUNT_INC_F);
  /** BLonSnvtType constant for SnvtFlowF. */
  public static final BLonSnvtType SnvtFlowF = new BLonSnvtType(SNVT_FLOW_F);
  /** BLonSnvtType constant for SnvtLengthF. */
  public static final BLonSnvtType SnvtLengthF = new BLonSnvtType(SNVT_LENGTH_F);
  /** BLonSnvtType constant for SnvtLevContF. */
  public static final BLonSnvtType SnvtLevContF = new BLonSnvtType(SNVT_LEV_CONT_F);
  /** BLonSnvtType constant for SnvtMassF. */
  public static final BLonSnvtType SnvtMassF = new BLonSnvtType(SNVT_MASS_F);
  /** BLonSnvtType constant for SnvtPowerF. */
  public static final BLonSnvtType SnvtPowerF = new BLonSnvtType(SNVT_POWER_F);
  /** BLonSnvtType constant for SnvtPpmF. */
  public static final BLonSnvtType SnvtPpmF = new BLonSnvtType(SNVT_PPM_F);
  /** BLonSnvtType constant for SnvtPressF. */
  public static final BLonSnvtType SnvtPressF = new BLonSnvtType(SNVT_PRESS_F);
  /** BLonSnvtType constant for SnvtResF. */
  public static final BLonSnvtType SnvtResF = new BLonSnvtType(SNVT_RES_F);
  /** BLonSnvtType constant for SnvtSoundDbF. */
  public static final BLonSnvtType SnvtSoundDbF = new BLonSnvtType(SNVT_SOUND_DB_F);
  /** BLonSnvtType constant for SnvtSpeedF. */
  public static final BLonSnvtType SnvtSpeedF = new BLonSnvtType(SNVT_SPEED_F);
  /** BLonSnvtType constant for SnvtTempF. */
  public static final BLonSnvtType SnvtTempF = new BLonSnvtType(SNVT_TEMP_F);
  /** BLonSnvtType constant for SnvtTimeF. */
  public static final BLonSnvtType SnvtTimeF = new BLonSnvtType(SNVT_TIME_F);
  /** BLonSnvtType constant for SnvtVolF. */
  public static final BLonSnvtType SnvtVolF = new BLonSnvtType(SNVT_VOL_F);
  /** BLonSnvtType constant for SnvtVoltF. */
  public static final BLonSnvtType SnvtVoltF = new BLonSnvtType(SNVT_VOLT_F);
  /** BLonSnvtType constant for SnvtBtuF. */
  public static final BLonSnvtType SnvtBtuF = new BLonSnvtType(SNVT_BTU_F);
  /** BLonSnvtType constant for SnvtElecWhrF. */
  public static final BLonSnvtType SnvtElecWhrF = new BLonSnvtType(SNVT_ELEC_WHR_F);
  /** BLonSnvtType constant for SnvtConfigSrc. */
  public static final BLonSnvtType SnvtConfigSrc = new BLonSnvtType(SNVT_CONFIG_SRC);
  /** BLonSnvtType constant for SnvtColor. */
  public static final BLonSnvtType SnvtColor = new BLonSnvtType(SNVT_COLOR);
  /** BLonSnvtType constant for SnvtGrammage. */
  public static final BLonSnvtType SnvtGrammage = new BLonSnvtType(SNVT_GRAMMAGE);
  /** BLonSnvtType constant for SnvtGrammageF. */
  public static final BLonSnvtType SnvtGrammageF = new BLonSnvtType(SNVT_GRAMMAGE_F);
  /** BLonSnvtType constant for SnvtFileReq. */
  public static final BLonSnvtType SnvtFileReq = new BLonSnvtType(SNVT_FILE_REQ);
  /** BLonSnvtType constant for SnvtFileStatus. */
  public static final BLonSnvtType SnvtFileStatus = new BLonSnvtType(SNVT_FILE_STATUS);
  /** BLonSnvtType constant for SnvtFreqF. */
  public static final BLonSnvtType SnvtFreqF = new BLonSnvtType(SNVT_FREQ_F);
  /** BLonSnvtType constant for SnvtFreqHz. */
  public static final BLonSnvtType SnvtFreqHz = new BLonSnvtType(SNVT_FREQ_HZ);
  /** BLonSnvtType constant for SnvtFreqKilohz. */
  public static final BLonSnvtType SnvtFreqKilohz = new BLonSnvtType(SNVT_FREQ_KILOHZ);
  /** BLonSnvtType constant for SnvtFreqMilhz. */
  public static final BLonSnvtType SnvtFreqMilhz = new BLonSnvtType(SNVT_FREQ_MILHZ);
  /** BLonSnvtType constant for SnvtLux. */
  public static final BLonSnvtType SnvtLux = new BLonSnvtType(SNVT_LUX);
  /** BLonSnvtType constant for SnvtIso7811. */
  public static final BLonSnvtType SnvtIso7811 = new BLonSnvtType(SNVT_ISO_7811);
  /** BLonSnvtType constant for SnvtLevPercent. */
  public static final BLonSnvtType SnvtLevPercent = new BLonSnvtType(SNVT_LEV_PERCENT);
  /** BLonSnvtType constant for SnvtMultiplier. */
  public static final BLonSnvtType SnvtMultiplier = new BLonSnvtType(SNVT_MULTIPLIER);
  /** BLonSnvtType constant for SnvtState. */
  public static final BLonSnvtType SnvtState = new BLonSnvtType(SNVT_STATE);
  /** BLonSnvtType constant for SnvtTimeStamp. */
  public static final BLonSnvtType SnvtTimeStamp = new BLonSnvtType(SNVT_TIME_STAMP);
  /** BLonSnvtType constant for SnvtZerospan. */
  public static final BLonSnvtType SnvtZerospan = new BLonSnvtType(SNVT_ZEROSPAN);
  /** BLonSnvtType constant for SnvtMagcard. */
  public static final BLonSnvtType SnvtMagcard = new BLonSnvtType(SNVT_MAGCARD);
  /** BLonSnvtType constant for SnvtElapsedTm. */
  public static final BLonSnvtType SnvtElapsedTm = new BLonSnvtType(SNVT_ELAPSED_TM);
  /** BLonSnvtType constant for SnvtAlarm. */
  public static final BLonSnvtType SnvtAlarm = new BLonSnvtType(SNVT_ALARM);
  /** BLonSnvtType constant for SnvtCurrency. */
  public static final BLonSnvtType SnvtCurrency = new BLonSnvtType(SNVT_CURRENCY);
  /** BLonSnvtType constant for SnvtFilePos. */
  public static final BLonSnvtType SnvtFilePos = new BLonSnvtType(SNVT_FILE_POS);
  /** BLonSnvtType constant for SnvtMuldiv. */
  public static final BLonSnvtType SnvtMuldiv = new BLonSnvtType(SNVT_MULDIV);
  /** BLonSnvtType constant for SnvtObjRequest. */
  public static final BLonSnvtType SnvtObjRequest = new BLonSnvtType(SNVT_OBJ_REQUEST);
  /** BLonSnvtType constant for SnvtObjStatus. */
  public static final BLonSnvtType SnvtObjStatus = new BLonSnvtType(SNVT_OBJ_STATUS);
  /** BLonSnvtType constant for SnvtPreset. */
  public static final BLonSnvtType SnvtPreset = new BLonSnvtType(SNVT_PRESET);
  /** BLonSnvtType constant for SnvtSwitch. */
  public static final BLonSnvtType SnvtSwitch = new BLonSnvtType(SNVT_SWITCH);
  /** BLonSnvtType constant for SnvtTransTable. */
  public static final BLonSnvtType SnvtTransTable = new BLonSnvtType(SNVT_TRANS_TABLE);
  /** BLonSnvtType constant for SnvtOverride. */
  public static final BLonSnvtType SnvtOverride = new BLonSnvtType(SNVT_OVERRIDE);
  /** BLonSnvtType constant for SnvtPwrFact. */
  public static final BLonSnvtType SnvtPwrFact = new BLonSnvtType(SNVT_PWR_FACT);
  /** BLonSnvtType constant for SnvtPwrFactF. */
  public static final BLonSnvtType SnvtPwrFactF = new BLonSnvtType(SNVT_PWR_FACT_F);
  /** BLonSnvtType constant for SnvtDensity. */
  public static final BLonSnvtType SnvtDensity = new BLonSnvtType(SNVT_DENSITY);
  /** BLonSnvtType constant for SnvtDensityF. */
  public static final BLonSnvtType SnvtDensityF = new BLonSnvtType(SNVT_DENSITY_F);
  /** BLonSnvtType constant for SnvtRpm. */
  public static final BLonSnvtType SnvtRpm = new BLonSnvtType(SNVT_RPM);
  /** BLonSnvtType constant for SnvtHvacEmerg. */
  public static final BLonSnvtType SnvtHvacEmerg = new BLonSnvtType(SNVT_HVAC_EMERG);
  /** BLonSnvtType constant for SnvtAngleDeg. */
  public static final BLonSnvtType SnvtAngleDeg = new BLonSnvtType(SNVT_ANGLE_DEG);
  /** BLonSnvtType constant for SnvtTempP. */
  public static final BLonSnvtType SnvtTempP = new BLonSnvtType(SNVT_TEMP_P);
  /** BLonSnvtType constant for SnvtTempSetpt. */
  public static final BLonSnvtType SnvtTempSetpt = new BLonSnvtType(SNVT_TEMP_SETPT);
  /** BLonSnvtType constant for SnvtTimeSec. */
  public static final BLonSnvtType SnvtTimeSec = new BLonSnvtType(SNVT_TIME_SEC);
  /** BLonSnvtType constant for SnvtHvacMode. */
  public static final BLonSnvtType SnvtHvacMode = new BLonSnvtType(SNVT_HVAC_MODE);
  /** BLonSnvtType constant for SnvtOccupancy. */
  public static final BLonSnvtType SnvtOccupancy = new BLonSnvtType(SNVT_OCCUPANCY);
  /** BLonSnvtType constant for SnvtArea. */
  public static final BLonSnvtType SnvtArea = new BLonSnvtType(SNVT_AREA);
  /** BLonSnvtType constant for SnvtHvacOverid. */
  public static final BLonSnvtType SnvtHvacOverid = new BLonSnvtType(SNVT_HVAC_OVERID);
  /** BLonSnvtType constant for SnvtHvacStatus. */
  public static final BLonSnvtType SnvtHvacStatus = new BLonSnvtType(SNVT_HVAC_STATUS);
  /** BLonSnvtType constant for SnvtPressP. */
  public static final BLonSnvtType SnvtPressP = new BLonSnvtType(SNVT_PRESS_P);
  /** BLonSnvtType constant for SnvtAddress. */
  public static final BLonSnvtType SnvtAddress = new BLonSnvtType(SNVT_ADDRESS);
  /** BLonSnvtType constant for SnvtScene. */
  public static final BLonSnvtType SnvtScene = new BLonSnvtType(SNVT_SCENE);
  /** BLonSnvtType constant for SnvtSceneCfg. */
  public static final BLonSnvtType SnvtSceneCfg = new BLonSnvtType(SNVT_SCENE_CFG);
  /** BLonSnvtType constant for SnvtSetting. */
  public static final BLonSnvtType SnvtSetting = new BLonSnvtType(SNVT_SETTING);
  /** BLonSnvtType constant for SnvtEvapState. */
  public static final BLonSnvtType SnvtEvapState = new BLonSnvtType(SNVT_EVAP_STATE);
  /** BLonSnvtType constant for SnvtThermMode. */
  public static final BLonSnvtType SnvtThermMode = new BLonSnvtType(SNVT_THERM_MODE);
  /** BLonSnvtType constant for SnvtDefrMode. */
  public static final BLonSnvtType SnvtDefrMode = new BLonSnvtType(SNVT_DEFR_MODE);
  /** BLonSnvtType constant for SnvtDefrTerm. */
  public static final BLonSnvtType SnvtDefrTerm = new BLonSnvtType(SNVT_DEFR_TERM);
  /** BLonSnvtType constant for SnvtDefrState. */
  public static final BLonSnvtType SnvtDefrState = new BLonSnvtType(SNVT_DEFR_STATE);
  /** BLonSnvtType constant for SnvtTimeMin. */
  public static final BLonSnvtType SnvtTimeMin = new BLonSnvtType(SNVT_TIME_MIN);
  /** BLonSnvtType constant for SnvtTimeHour. */
  public static final BLonSnvtType SnvtTimeHour = new BLonSnvtType(SNVT_TIME_HOUR);
  /** BLonSnvtType constant for SnvtPh. */
  public static final BLonSnvtType SnvtPh = new BLonSnvtType(SNVT_PH);
  /** BLonSnvtType constant for SnvtPhF. */
  public static final BLonSnvtType SnvtPhF = new BLonSnvtType(SNVT_PH_F);
  /** BLonSnvtType constant for SnvtChlrStatus. */
  public static final BLonSnvtType SnvtChlrStatus = new BLonSnvtType(SNVT_CHLR_STATUS);
  /** BLonSnvtType constant for SnvtTodEvent. */
  public static final BLonSnvtType SnvtTodEvent = new BLonSnvtType(SNVT_TOD_EVENT);
  /** BLonSnvtType constant for SnvtSmoObscur. */
  public static final BLonSnvtType SnvtSmoObscur = new BLonSnvtType(SNVT_SMO_OBSCUR);
  /** BLonSnvtType constant for SnvtFireTest. */
  public static final BLonSnvtType SnvtFireTest = new BLonSnvtType(SNVT_FIRE_TEST);
  /** BLonSnvtType constant for SnvtTempRor. */
  public static final BLonSnvtType SnvtTempRor = new BLonSnvtType(SNVT_TEMP_ROR);
  /** BLonSnvtType constant for SnvtFireInit. */
  public static final BLonSnvtType SnvtFireInit = new BLonSnvtType(SNVT_FIRE_INIT);
  /** BLonSnvtType constant for SnvtFireIndcte. */
  public static final BLonSnvtType SnvtFireIndcte = new BLonSnvtType(SNVT_FIRE_INDCTE);
  /** BLonSnvtType constant for SnvtTimeZone. */
  public static final BLonSnvtType SnvtTimeZone = new BLonSnvtType(SNVT_TIME_ZONE);
  /** BLonSnvtType constant for SnvtEarthPos. */
  public static final BLonSnvtType SnvtEarthPos = new BLonSnvtType(SNVT_EARTH_POS);
  /** BLonSnvtType constant for SnvtRegVal. */
  public static final BLonSnvtType SnvtRegVal = new BLonSnvtType(SNVT_REG_VAL);
  /** BLonSnvtType constant for SnvtRegValTs. */
  public static final BLonSnvtType SnvtRegValTs = new BLonSnvtType(SNVT_REG_VAL_TS);
  /** BLonSnvtType constant for SnvtVoltAc. */
  public static final BLonSnvtType SnvtVoltAc = new BLonSnvtType(SNVT_VOLT_AC);
  /** BLonSnvtType constant for SnvtAmpAc. */
  public static final BLonSnvtType SnvtAmpAc = new BLonSnvtType(SNVT_AMP_AC);
  /** BLonSnvtType constant for SnvtTurbidity. */
  public static final BLonSnvtType SnvtTurbidity = new BLonSnvtType(SNVT_TURBIDITY);
  /** BLonSnvtType constant for SnvtTurbidityF. */
  public static final BLonSnvtType SnvtTurbidityF = new BLonSnvtType(SNVT_TURBIDITY_F);
  /** BLonSnvtType constant for SnvtHvacType. */
  public static final BLonSnvtType SnvtHvacType = new BLonSnvtType(SNVT_HVAC_TYPE);
  /** BLonSnvtType constant for SnvtElecKwhL. */
  public static final BLonSnvtType SnvtElecKwhL = new BLonSnvtType(SNVT_ELEC_KWH_L);
  /** BLonSnvtType constant for SnvtTempDiffP. */
  public static final BLonSnvtType SnvtTempDiffP = new BLonSnvtType(SNVT_TEMP_DIFF_P);
  /** BLonSnvtType constant for SnvtCtrlReq. */
  public static final BLonSnvtType SnvtCtrlReq = new BLonSnvtType(SNVT_CTRL_REQ);
  /** BLonSnvtType constant for SnvtCtrlResp. */
  public static final BLonSnvtType SnvtCtrlResp = new BLonSnvtType(SNVT_CTRL_RESP);
  /** BLonSnvtType constant for SnvtPtz. */
  public static final BLonSnvtType SnvtPtz = new BLonSnvtType(SNVT_PTZ);
  /** BLonSnvtType constant for SnvtPrivacyzone. */
  public static final BLonSnvtType SnvtPrivacyzone = new BLonSnvtType(SNVT_PRIVACYZONE);
  /** BLonSnvtType constant for SnvtPosCtrl. */
  public static final BLonSnvtType SnvtPosCtrl = new BLonSnvtType(SNVT_POS_CTRL);
  /** BLonSnvtType constant for SnvtEnthalpy. */
  public static final BLonSnvtType SnvtEnthalpy = new BLonSnvtType(SNVT_ENTHALPY);
  /** BLonSnvtType constant for SnvtGfciStatus. */
  public static final BLonSnvtType SnvtGfciStatus = new BLonSnvtType(SNVT_GFCI_STATUS);
  /** BLonSnvtType constant for SnvtMotorState. */
  public static final BLonSnvtType SnvtMotorState = new BLonSnvtType(SNVT_MOTOR_STATE);
  /** BLonSnvtType constant for SnvtPumpsetMn. */
  public static final BLonSnvtType SnvtPumpsetMn = new BLonSnvtType(SNVT_PUMPSET_MN);
  /** BLonSnvtType constant for SnvtExControl. */
  public static final BLonSnvtType SnvtExControl = new BLonSnvtType(SNVT_EX_CONTROL);
  /** BLonSnvtType constant for SnvtPumpsetSn. */
  public static final BLonSnvtType SnvtPumpsetSn = new BLonSnvtType(SNVT_PUMPSET_SN);
  /** BLonSnvtType constant for SnvtPumpSensor. */
  public static final BLonSnvtType SnvtPumpSensor = new BLonSnvtType(SNVT_PUMP_SENSOR);
  /** BLonSnvtType constant for SnvtAbsHumid. */
  public static final BLonSnvtType SnvtAbsHumid = new BLonSnvtType(SNVT_ABS_HUMID);
  /** BLonSnvtType constant for SnvtFlowP. */
  public static final BLonSnvtType SnvtFlowP = new BLonSnvtType(SNVT_FLOW_P);
  /** BLonSnvtType constant for SnvtDevCMode. */
  public static final BLonSnvtType SnvtDevCMode = new BLonSnvtType(SNVT_DEV_CMODE);
  /** BLonSnvtType constant for SnvtValveMode. */
  public static final BLonSnvtType SnvtValveMode = new BLonSnvtType(SNVT_VALVE_MODE);
  /** BLonSnvtType constant for SnvtAlarm2. */
  public static final BLonSnvtType SnvtAlarm2 = new BLonSnvtType(SNVT_ALARM_2);
  /** BLonSnvtType constant for SnvtState64. */
  public static final BLonSnvtType SnvtState64 = new BLonSnvtType(SNVT_STATE_64);
  /** BLonSnvtType constant for SnvtNvType. */
  public static final BLonSnvtType SnvtNvType = new BLonSnvtType(SNVT_NV_TYPE);
  /** BLonSnvtType constant for SnvtEntOpmode. */
  public static final BLonSnvtType SnvtEntOpmode = new BLonSnvtType(SNVT_ENT_OPMODE);
  /** BLonSnvtType constant for SnvtEntState. */
  public static final BLonSnvtType SnvtEntState = new BLonSnvtType(SNVT_ENT_STATE);
  /** BLonSnvtType constant for SnvtEntStatus. */
  public static final BLonSnvtType SnvtEntStatus = new BLonSnvtType(SNVT_ENT_STATUS);
  /** BLonSnvtType constant for SnvtFlowDir. */
  public static final BLonSnvtType SnvtFlowDir = new BLonSnvtType(SNVT_FLOW_DIR);
  /** BLonSnvtType constant for SnvtHvacSatsts. */
  public static final BLonSnvtType SnvtHvacSatsts = new BLonSnvtType(SNVT_HVAC_SATSTS);
  /** BLonSnvtType constant for SnvtDevStatus. */
  public static final BLonSnvtType SnvtDevStatus = new BLonSnvtType(SNVT_DEV_STATUS);
  /** BLonSnvtType constant for SnvtDevFault. */
  public static final BLonSnvtType SnvtDevFault = new BLonSnvtType(SNVT_DEV_FAULT);
  /** BLonSnvtType constant for SnvtDevMaint. */
  public static final BLonSnvtType SnvtDevMaint = new BLonSnvtType(SNVT_DEV_MAINT);
  /** BLonSnvtType constant for SnvtDateEvent. */
  public static final BLonSnvtType SnvtDateEvent = new BLonSnvtType(SNVT_DATE_EVENT);
  /** BLonSnvtType constant for SnvtSchedVal. */
  public static final BLonSnvtType SnvtSchedVal = new BLonSnvtType(SNVT_SCHED_VAL);
  /** BLonSnvtType constant for SnvtSecState. */
  public static final BLonSnvtType SnvtSecState = new BLonSnvtType(SNVT_SEC_STATE);
  /** BLonSnvtType constant for SnvtSecStatus. */
  public static final BLonSnvtType SnvtSecStatus = new BLonSnvtType(SNVT_SEC_STATUS);
  /** BLonSnvtType constant for SnvtSblndState. */
  public static final BLonSnvtType SnvtSblndState = new BLonSnvtType(SNVT_SBLND_STATE);
  /** BLonSnvtType constant for SnvtRacCtrl. */
  public static final BLonSnvtType SnvtRacCtrl = new BLonSnvtType(SNVT_RAC_CTRL);
  /** BLonSnvtType constant for SnvtRacReq. */
  public static final BLonSnvtType SnvtRacReq = new BLonSnvtType(SNVT_RAC_REQ);
  /** BLonSnvtType constant for SnvtCount32. */
  public static final BLonSnvtType SnvtCount32 = new BLonSnvtType(SNVT_COUNT32);
  /** BLonSnvtType constant for SnvtClothesWC. */
  public static final BLonSnvtType SnvtClothesWC = new BLonSnvtType(SNVT_CLOTHES_WC);
  /** BLonSnvtType constant for SnvtClothesWM. */
  public static final BLonSnvtType SnvtClothesWM = new BLonSnvtType(SNVT_CLOTHES_WM);
  /** BLonSnvtType constant for SnvtClothesWS. */
  public static final BLonSnvtType SnvtClothesWS = new BLonSnvtType(SNVT_CLOTHES_WS);
  /** BLonSnvtType constant for SnvtClothesWA. */
  public static final BLonSnvtType SnvtClothesWA = new BLonSnvtType(SNVT_CLOTHES_WA);
  /** BLonSnvtType constant for SnvtMultiplierS. */
  public static final BLonSnvtType SnvtMultiplierS = new BLonSnvtType(SNVT_MULTIPLIER_S);
  /** BLonSnvtType constant for SnvtSwitch2. */
  public static final BLonSnvtType SnvtSwitch2 = new BLonSnvtType(SNVT_SWITCH_2);
  /** BLonSnvtType constant for SnvtColor2. */
  public static final BLonSnvtType SnvtColor2 = new BLonSnvtType(SNVT_COLOR_2);
  /** BLonSnvtType constant for SnvtLogStatus. */
  public static final BLonSnvtType SnvtLogStatus = new BLonSnvtType(SNVT_LOG_STATUS);
  /** BLonSnvtType constant for SnvtTimeStampP. */
  public static final BLonSnvtType SnvtTimeStampP = new BLonSnvtType(SNVT_TIME_STAMP_P);
  /** BLonSnvtType constant for SnvtLogFxRequest. */
  public static final BLonSnvtType SnvtLogFxRequest = new BLonSnvtType(SNVT_LOG_FX_REQUEST);
  /** BLonSnvtType constant for SnvtLogFxStatus. */
  public static final BLonSnvtType SnvtLogFxStatus = new BLonSnvtType(SNVT_LOG_FX_STATUS);
  /** BLonSnvtType constant for SnvtLogRequest. */
  public static final BLonSnvtType SnvtLogRequest = new BLonSnvtType(SNVT_LOG_REQUEST);
  /** BLonSnvtType constant for SnvtEnthalpyD. */
  public static final BLonSnvtType SnvtEnthalpyD = new BLonSnvtType(SNVT_ENTHALPY_D);
  /** BLonSnvtType constant for SnvtAmpAcMil. */
  public static final BLonSnvtType SnvtAmpAcMil = new BLonSnvtType(SNVT_AMP_AC_MIL);
  /** BLonSnvtType constant for SnvtTimeHourP. */
  public static final BLonSnvtType SnvtTimeHourP = new BLonSnvtType(SNVT_TIME_HOUR_P);
  /** BLonSnvtType constant for SnvtLampStatus. */
  public static final BLonSnvtType SnvtLampStatus = new BLonSnvtType(SNVT_LAMP_STATUS);
  /** BLonSnvtType constant for SnvtEnvironment. */
  public static final BLonSnvtType SnvtEnvironment = new BLonSnvtType(SNVT_ENVIRONMENT);
  /** BLonSnvtType constant for SnvtGeoLoc. */
  public static final BLonSnvtType SnvtGeoLoc = new BLonSnvtType(SNVT_GEO_LOC);
  /** BLonSnvtType constant for SnvtProgramStatus. */
  public static final BLonSnvtType SnvtProgramStatus = new BLonSnvtType(SNVT_PROGRAM_STATUS);
  /** BLonSnvtType constant for SnvtLoadOffsets. */
  public static final BLonSnvtType SnvtLoadOffsets = new BLonSnvtType(SNVT_LOAD_OFFSETS);
  /** BLonSnvtType constant for SnvtWm2P. */
  public static final BLonSnvtType SnvtWm2P = new BLonSnvtType(SNVT_WM_2_P);
  /** BLonSnvtType constant for SnvtSafe1. */
  public static final BLonSnvtType SnvtSafe1 = new BLonSnvtType(SNVT_SAFE_1);
  /** BLonSnvtType constant for SnvtSafe2. */
  public static final BLonSnvtType SnvtSafe2 = new BLonSnvtType(SNVT_SAFE_2);
  /** BLonSnvtType constant for SnvtSafe4. */
  public static final BLonSnvtType SnvtSafe4 = new BLonSnvtType(SNVT_SAFE_4);
  /** BLonSnvtType constant for SnvtSafe8. */
  public static final BLonSnvtType SnvtSafe8 = new BLonSnvtType(SNVT_SAFE_8);
  /** BLonSnvtType constant for SnvtTimeVal2. */
  public static final BLonSnvtType SnvtTimeVal2 = new BLonSnvtType(SNVT_TIME_VAL_2);
  /** BLonSnvtType constant for SnvtTimeOffset. */
  public static final BLonSnvtType SnvtTimeOffset = new BLonSnvtType(SNVT_TIME_OFFSET);
  /** BLonSnvtType constant for SnvtSchedExc. */
  public static final BLonSnvtType SnvtSchedExc = new BLonSnvtType(SNVT_SCHED_EXC);
  /** BLonSnvtType constant for SnvtSchedStatus. */
  public static final BLonSnvtType SnvtSchedStatus = new BLonSnvtType(SNVT_SCHED_STATUS);
  /** BLonSnvtType constant for SnvtMassFlow. */
  public static final BLonSnvtType SnvtMassFlow = new BLonSnvtType(SNVT_MASS_FLOW);
  /** BLonSnvtType constant for SnvtMassFlowF. */
  public static final BLonSnvtType SnvtMassFlowF = new BLonSnvtType(SNVT_MASS_FLOW_F);

  /** Factory method with ordinal. */
  public static BLonSnvtType make(int ordinal)
  {
    return (BLonSnvtType)SnvtXxx.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonSnvtType make(String tag)
  {
    return (BLonSnvtType)SnvtXxx.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonSnvtType(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonSnvtType DEFAULT = SnvtXxx;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonSnvtType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static final int LAST_SNVT_ID  = 214 ;

//  // Create a list of snvt types for this class file
//  // to run >>nre lonworks:javax.baja.lonworks.enums.BLonSnvtType
//  public static void main(String[] args)
//  {  
//      XLonInterfaceFile std = XUtil.getStandard();
//      Vector types = std.types;
//      String[] a = new String[255];
//int[] la = new int [255];      
//      int maxNv = 0;
//      int maxLen = 0;
//      for(int i=0 ; i<types.size() ; i++)
//      {
//        XTypeDef t = ((XTypeDef)types.elementAt(i));
//        if(!t.isCpType())
//        { 
//          int ndx = t.getTypeIndex();
//          if(ndx < 0) continue;
//          a[ndx] = t.getName();
//          if(ndx>maxNv) maxNv = ndx;
//          int len = NameUtil.toConstantName(a[ndx]).length();
//          if( len > maxLen) maxLen = len;
//try { la[ndx] = t.getLonData(std).getByteLength(); } catch(Exception e) {}          
//        }
//      }
//      
//      String pad = "                                          ";
//      System.out.println("  public static final int SNVT_XXX          = 0  ;");
//      for(int i=1; i<=maxNv ; i++)
//      {           
//        if(a[i]==null) continue;
//        String conName = NameUtil.toConstantName(a[i]);
//        int padLen = maxLen - conName.length();
//        System.out.println("  public static final int SNVT_" + 
//               conName + pad.substring(0,padLen) + " = " + i + "  ;"); 
//      }
//
//      System.out.println("\n\n");
//      System.out.println("  public static final int LAST_SNVT_ID  = " + maxNv + " ;\n");
//      System.out.println("  public static final BLonSnvtType SnvtXxx         = new BLonSnvtType(SNVT_XXX         );\n");
//      
//      for(int i=1; i<=maxNv ; i++)
//      {
//        if(a[i]==null) continue;
//        String conName = NameUtil.toConstantName(a[i]);
//        int padLen = maxLen - a[i].length();
//        System.out.println("  public static final BLonSnvtType Snvt" 
//                     + NameUtil.toJavaName(a[i],true) + pad.substring(0,padLen) +
//                      " = new BLonSnvtType(SNVT_" + conName + pad.substring(0,maxLen-conName.length()) + 
//  ");  " + la[i]);
//      }
//    
//  }
}
