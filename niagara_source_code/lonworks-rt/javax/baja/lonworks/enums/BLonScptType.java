/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
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
 * The BLonScptType class provides an enumeration for 
 * all standard SCPT types
 *
 * @author    Robert Adams
 * @creation  31 Oct 03
 * @version   $Revision: 1$ $Date: 6/13/01 12:26:08 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("ScptXxx"),
    @Range("ScptActFbDly"),
    @Range("ScptAlrmClrT1"),
    @Range("ScptAlrmClrT2"),
    @Range("ScptAlrmIhbT"),
    @Range("ScptAlrmSetT1"),
    @Range("ScptAlrmSetT2"),
    @Range("ScptDefOutput"),
    @Range("ScptDriveT"),
    @Range("ScptHighLimit1"),
    @Range("ScptHighLimit2"),
    @Range("ScptHystHigh1"),
    @Range("ScptHystHigh2"),
    @Range("ScptHystLow1"),
    @Range("ScptHystLow2"),
    @Range("ScptInFbDly"),
    @Range("ScptInvrtOut"),
    @Range("ScptLocation"),
    @Range("ScptLowLimit1"),
    @Range("ScptLowLimit2"),
    @Range("ScptMaxRnge"),
    @Range("ScptMaxRcvT"),
    @Range("ScptMaxSndT"),
    @Range("ScptMinRnge"),
    @Range("ScptMinSndT"),
    @Range("ScptNwrkCnfg"),
    @Range("ScptOffset"),
    @Range("ScptSndDelta"),
    @Range("ScptTrnsTblX"),
    @Range("ScptTrnsTblY"),
    @Range("ScptOffDely"),
    @Range("ScptGain"),
    @Range("ScptOvrBehave"),
    @Range("ScptOvrValue"),
    @Range("ScptBypassTime"),
    @Range("ScptManOvrTime"),
    @Range("ScptHumSetpt"),
    @Range("ScptMaxFlowHeat"),
    @Range("ScptFireInitType"),
    @Range("ScptSmokeNomSens"),
    @Range("ScptSmokeDayAlrmLim"),
    @Range("ScptActuatorType"),
    @Range("ScptLimitCO2"),
    @Range("ScptMinDeltaAngl"),
    @Range("ScptDirection"),
    @Range("ScptDriveTime"),
    @Range("ScptDuctArea"),
    @Range("ScptMinDeltaFlow"),
    @Range("ScptMaxRcvTime"),
    @Range("ScptMaxSendTime"),
    @Range("ScptMaxSetpoint"),
    @Range("ScptMaxFlow"),
    @Range("ScptMinSendTime"),
    @Range("ScptMinSetpoint"),
    @Range("ScptMinFlow"),
    @Range("ScptMinFlowHeat"),
    @Range("ScptMinFlowStby"),
    @Range("ScptNomAirFlow"),
    @Range("ScptNomAngle"),
    @Range("ScptNumValves"),
    @Range("ScptSetPnts"),
    @Range("ScptOemType"),
    @Range("ScptMinDeltaRH"),
    @Range("ScptMinDeltaCO2"),
    @Range("ScptMinDeltaTemp"),
    @Range("ScptSensConstTmp"),
    @Range("ScptGainVAV"),
    @Range("ScptSensConstVAV"),
    @Range("ScptOffsetCO2"),
    @Range("ScptOffsetRH"),
    @Range("ScptOffsetTemp"),
    @Range("ScptDefltBehave"),
    @Range("ScptPwrUpDelay"),
    @Range("ScptPwrUpState"),
    @Range("ScptHvacMode"),
    @Range("ScptCoolSetpt"),
    @Range("ScptCoolLowerSP"),
    @Range("ScptCoolUpperSP"),
    @Range("ScptHeatSetpt"),
    @Range("ScptHeatLowerSP"),
    @Range("ScptHeatUpperSP"),
    @Range("ScptLimitChlrCap"),
    @Range("ScptLuxSetpoint"),
    @Range("ScptStep"),
    @Range("ScptOnOffHysteresis"),
    @Range("ScptClOffDelay"),
    @Range("ScptClOnDelay"),
    @Range("ScptPowerupState"),
    @Range("ScptMinDeltaLevel"),
    @Range("ScptReflection"),
    @Range("ScptFieldCalib"),
    @Range("ScptHoldTime"),
    @Range("ScptStepValue"),
    @Range("ScptMaxOut"),
    @Range("ScptSceneNmbr"),
    @Range("ScptFadeTime"),
    @Range("ScptDelayTime"),
    @Range("ScptMasterSlave"),
    @Range("ScptUpdateRate"),
    @Range("ScptSummerTime"),
    @Range("ScptWinterTime"),
    @Range("ScptManualAllowed"),
    @Range("ScptDefWeekMask"),
    @Range("ScptDayDateIndex"),
    @Range("ScptTimeEvent"),
    @Range("ScptModeHrtBt"),
    @Range("ScptDefrostMode"),
    @Range("ScptMaxDefrstTime"),
    @Range("ScptDrainDelay"),
    @Range("ScptInjDelay"),
    @Range("ScptMaxDefrstTemp"),
    @Range("ScptStrtupDelay"),
    @Range("ScptTermTimeTemp"),
    @Range("ScptPumpDownDelay"),
    @Range("ScptSuperHtRefInit"),
    @Range("ScptStrtupOpen"),
    @Range("ScptSuperHtRefMin"),
    @Range("ScptRefrigGlide"),
    @Range("ScptSuperHtRefMax"),
    @Range("ScptRefrigType"),
    @Range("ScptThermMode"),
    @Range("ScptDayNightCntrl"),
    @Range("ScptDiffNight"),
    @Range("ScptHighLimTemp"),
    @Range("ScptHighLimDly"),
    @Range("ScptCutOutValue"),
    @Range("ScptAirTemp1Day"),
    @Range("ScptSmokeNightAlrmLim"),
    @Range("ScptLowLimTemp"),
    @Range("ScptLowLimDly"),
    @Range("ScptDiffValue"),
    @Range("ScptAirTemp1Night"),
    @Range("ScptAirTemp1Alrm"),
    @Range("ScptHighLimDefrDly"),
    @Range("ScptDeltaNight"),
    @Range("ScptRunHrInit"),
    @Range("ScptRunHrAlarm"),
    @Range("ScptEnergyCntInit"),
    @Range("ScptSmokeDayPreAlrmLim"),
    @Range("ScptDebounce"),
    @Range("ScptSmokeNightPreAlrmLim"),
    @Range("ScptZoneNum"),
    @Range("ScptThermAlrmROR"),
    @Range("ScptVisOutput"),
    @Range("ScptAudOutput"),
    @Range("ScptFlashFreq"),
    @Range("ScptInstallDate"),
    @Range("ScptMaintDate"),
    @Range("ScptManfDate"),
    @Range("ScptFireTxt1"),
    @Range("ScptFireTxt2"),
    @Range("ScptFireTxt3"),
    @Range("ScptThermThreshold"),
    @Range("ScptFireIndicate"),
    @Range("ScptTimeZone"),
    @Range("ScptPrimeVal"),
    @Range("ScptSecondVal"),
    @Range("ScptSceneOffset"),
    @Range("ScptNomRPM"),
    @Range("ScptNomFreq"),
    @Range("ScptRampUpTm"),
    @Range("ScptRampDownTm"),
    @Range("ScptDefScale"),
    @Range("ScptRegName"),
    @Range("ScptBaseValue"),
    @Range("ScptDevMajVer"),
    @Range("ScptDevMinVer"),
    @Range("ScptObjMajVer"),
    @Range("ScptObjMinVer"),
    @Range("ScptHvacType"),
    @Range("ScptTimeout"),
    @Range("ScptControlPriority"),
    @Range("ScptDeviceGroupID"),
    @Range("ScptMaxPrivacyZones"),
    @Range("ScptMaxCameraPrepositions"),
    @Range("ScptDefaultPanTiltZoomSpeeds"),
    @Range("ScptDefaultAutoPanSpeed"),
    @Range("ScptAutoAnswer"),
    @Range("ScptDialString"),
    @Range("ScptSerialNumber"),
    @Range("ScptNormalRotationalSpeed"),
    @Range("ScptStandbyRotationalSpeed"),
    @Range("ScptPartNumber"),
    @Range("ScptDischargeAirCoolingSetpoint"),
    @Range("ScptDischargeAirHeatingSetpoint"),
    @Range("ScptMaxSupplyFanCapacity"),
    @Range("ScptMinSupplyFanCapacity"),
    @Range("ScptMaxReturnExhaustFanCapacity"),
    @Range("ScptMinReturnExhaustFanCapacity"),
    @Range("ScptDuctStaticPressureSetpoint"),
    @Range("ScptMaxDuctStaticPressureSetpoint"),
    @Range("ScptMinDuctStaticPressureSetpoint"),
    @Range("ScptDuctStaticPressureLimit"),
    @Range("ScptBuildingStaticPressureSetpoint"),
    @Range("ScptReturnFanStaticPressureSetpoint"),
    @Range("ScptFanDifferentialSetpoint"),
    @Range("ScptMixedAirLowLimitSetpoint"),
    @Range("ScptMixedAirTempSetpoint"),
    @Range("ScptMinOutdoorAirFlowSetpoint"),
    @Range("ScptOutdoorAirTempSetpoint"),
    @Range("ScptOutdoorAirEnthalpySetpoint"),
    @Range("ScptDiffTempSetpoint"),
    @Range("ScptExhaustEnablePosition"),
    @Range("ScptSpaceHumSetpoint"),
    @Range("ScptDischargeAirDewpointSetpoint"),
    @Range("ScptMaxDischargeAirCoolingSetpoint"),
    @Range("ScptMinDischargeAirCoolingSetpoint"),
    @Range("ScptMaxDischargeAirHeatingSetpoint"),
    @Range("ScptMinDischargeAirHeatingSetpoint"),
    @Range("ScptCoolingLockout"),
    @Range("ScptHeatingLockout"),
    @Range("ScptCoolingResetEnable"),
    @Range("ScptHeatingResetEnable"),
    @Range("ScptSetpoint"),
    @Range("ScptTemperatureHysteresis"),
    @Range("ScptControlTemperatureWeighting"),
    @Range("ScptPwmPeriod"),
    @Range("ScptDefrostInternalSchedule"),
    @Range("ScptDefrostStart"),
    @Range("ScptDefrostCycles"),
    @Range("ScptMinDefrostTime"),
    @Range("ScptMaxDefrostTime"),
    @Range("ScptDefrostFanDelay"),
    @Range("ScptDefrostRecoveryTime"),
    @Range("ScptDefrostHold"),
    @Range("ScptDefrostDetect"),
    @Range("ScptScheduleInternal"),
    @Range("ScptTempOffset"),
    @Range("ScptAudibleLevel"),
    @Range("ScptScrollSpeed"),
    @Range("ScptBrightness"),
    @Range("ScptOrientation"),
    @Range("ScptInstalledLevel"),
    @Range("ScptPumpCharacteristic"),
    @Range("ScptMinPressureSetpoint"),
    @Range("ScptMaxPressureSetpoint"),
    @Range("ScptMinFlowSetpoint"),
    @Range("ScptMaxFlowSetpoint"),
    @Range("ScptDeviceControlMode"),
    @Range("ScptMinRemotePressureSetpoint"),
    @Range("ScptMaxRemotePressureSetpoint"),
    @Range("ScptMinRemoteFlowSetpoint"),
    @Range("ScptMaxRemoteFlowSetpoint"),
    @Range("ScptMinRemoteTempSetpoint"),
    @Range("ScptMaxRemoteTempSetpoint"),
    @Range("ScptControlSignal"),
    @Range("ScptNightPurgePosition"),
    @Range("ScptFreeCoolPosition"),
    @Range("ScptValveFlowCharacteristic"),
    @Range("ScptValveOperatingMode"),
    @Range("ScptEmergencyPosition"),
    @Range("ScptBlockProtectionTime"),
    @Range("ScptMinStroke"),
    @Range("ScptMaxStroke"),
    @Range("ScptNvType"),
    @Range("ScptMaxNVLength"),
    @Range("ScptNvDynamicAssignment"),
    @Range("ScptSafExtCnfg"),
    @Range("ScptEmergCnfg"),
    @Range("ScptSluiceCnfg"),
    @Range("ScptFanOperation"),
    @Range("ScptMinFlowUnit"),
    @Range("ScptMaxFlowUnit"),
    @Range("ScptMinFlowHeatStby"),
    @Range("ScptMinFlowUnitStby"),
    @Range("ScptOffsetFlow"),
    @Range("ScptAreaDuctHeat"),
    @Range("ScptNomAirFlowHeat"),
    @Range("ScptGainVAVHeat"),
    @Range("ScptNumDampers"),
    @Range("ScptMinFlowUnitHeat"),
    @Range("ScptSaturationDelay"),
    @Range("ScptEffectivePeriod"),
    @Range("ScptScheduleDates"),
    @Range("ScptSchedule"),
    @Range("ScptScheduleTimeValue"),
    @Range("ScptValueDefinition"),
    @Range("ScptValueName"),
    @Range("ScptWeeklySchedule"),
    @Range("ScptScheduleName"),
    @Range("ScptValveStroke"),
    @Range("ScptValveNominalSize"),
    @Range("ScptValveKvs"),
    @Range("ScptValveType"),
    @Range("ScptActuatorCharacteristic"),
    @Range("ScptTrnsTblX2"),
    @Range("ScptTrnsTblY2"),
    @Range("ScptCombFlowCharacteristic"),
    @Range("ScptTrnsTblX3"),
    @Range("ScptTrnsTblY3"),
    @Range("ScptRunTimeAlarm"),
    @Range("ScptTimePeriod"),
    @Range("ScptPulseValue"),
    @Range("ScptNumDigits"),
    @Range("ScptIdentity"),
    @Range("ScptDefaultState"),
    @Range("ScptNvPriority"),
    @Range("ScptDefaultSetting"),
    @Range("ScptLowLimit1Enable"),
    @Range("ScptLowLimit2Enable"),
    @Range("ScptClockCalibration"),
    @Range("ScptNeuronId"),
    @Range("ScptHighLimit1Enable"),
    @Range("ScptHighLimit2Enable"),
    @Range("ScptAhamApplianceModel"),
    @Range("ScptDefInput"),
    @Range("ScptName1"),
    @Range("ScptScene"),
    @Range("ScptSceneTiming"),
    @Range("ScptName2"),
    @Range("ScptName3"),
    @Range("ScptButtonPressAction"),
    @Range("ScptButtonColor"),
    @Range("ScptButtonRepeatInterval"),
    @Range("ScptButtonHoldAction"),
    @Range("ScptPwrSendOnDelta"),
    @Range("ScptSceneName"),
    @Range("ScptMaxPower"),
    @Range("ScptIfaceDesc"),
    @Range("ScptMonInterval"),
    @Range("ScptLinkPowerDetectEnable"),
    @Range("ScptScanTime"),
    @Range("ScptDevListDesc"),
    @Range("ScptDevListEntry"),
    @Range("ScptLogCapacity"),
    @Range("ScptLogNotificationThreshold"),
    @Range("ScptLogSize"),
    @Range("ScptLogType"),
    @Range("ScptFanInEnable"),
    @Range("ScptLogTimestampEnable"),
    @Range("ScptLogHighLimit"),
    @Range("ScptLogLowLimit"),
    @Range("ScptMaxFanIn"),
    @Range("ScptLogMinDeltaTime"),
    @Range("ScptLogMinDeltaValue"),
    @Range("ScptPollRate"),
    @Range("ScptSourceAddress"),
    @Range("ScptLogRecord"),
    @Range("ScptLogFileHeader"),
    @Range("ScptLogAlarmThreshold"),
    @Range("ScptLogRequest"),
    @Range("ScptLogResponse"),
    @Range("ScptLightingGroupEnable"),
    @Range("ScptSceneColor"),
    @Range("ScptBkupSchedule"),
    @Range("ScptOLCLimits"),
    @Range("ScptLampPower"),
    @Range("ScptDeviceOutSelection"),
    @Range("ScptEnableStatusMsg"),
    @Range("ScptMaxLevelVolt"),
    @Range("ScptGeoLocation"),
    @Range("ScptProgName"),
    @Range("ScptProgRevision"),
    @Range("ScptProgSelect"),
    @Range("ScptProgSourceLocation"),
    @Range("ScptProgFileIndexes"),
    @Range("ScptProgCmdHistory"),
    @Range("ScptProgStateHistory"),
    @Range("ScptNsdsFbIndex"),
    @Range("ScptCurrentSenseEnable"),
    @Range("ScptMeasurementInterval"),
    @Range("ScptLightingGroupMembership"),
    @Range("ScptLoadControlOffset"),
    @Range("ScptProgErrorHistory"),
    @Range("ScptNvUsage"),
    @Range("ScptScheduleSunday"),
    @Range("ScptScheduleMonday"),
    @Range("ScptScheduleTuesday"),
    @Range("ScptScheduleWednesday"),
    @Range("ScptScheduleThursday"),
    @Range("ScptScheduleFriday"),
    @Range("ScptScheduleSaturday"),
    @Range("ScptOccupancyBehavior"),
    @Range("ScptTimeSource"),
    @Range("ScptScheduleException"),
    @Range("ScptScheduleHoliday"),
    @Range("ScptRandomizationInterval"),
    @Range("ScptSunriseTime"),
    @Range("ScptSunsetTime"),
    @Range("ScptSchedulerOptions"),
    @Range("ScptOccupancyThresholds")
  }
)
//Use NoSlotomatic since legacy ScptTypes SCPT_ALRM_CLR_T_1, SCPT_ALRM_CLR_T_2, etc. newly autogenerated ordinals don't match
//existing ordinal names. Need to maintain the previous names.
@NoSlotomatic
public final class BLonScptType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonScptType(540953435)1.0$ @*/
/* Generated Tue Aug 10 11:59:28 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  /** Ordinal value for ScptXxx. */
  public static final int SCPT_XXX = 0;
  /** Ordinal value for ScptActFbDly. */
  public static final int SCPT_ACT_FB_DLY = 1;
  /** Ordinal value for ScptAlrmClrT1. */
  public static final int SCPT_ALRM_CLR_T_1 = 2;
  /** Ordinal value for ScptAlrmClrT2. */
  public static final int SCPT_ALRM_CLR_T_2 = 3;
  /** Ordinal value for ScptAlrmIhbT. */
  public static final int SCPT_ALRM_IHB_T = 4;
  /** Ordinal value for ScptAlrmSetT1. */
  public static final int SCPT_ALRM_SET_T_1 = 5;
  /** Ordinal value for ScptAlrmSetT2. */
  public static final int SCPT_ALRM_SET_T_2 = 6;
  /** Ordinal value for ScptDefOutput. */
  public static final int SCPT_DEF_OUTPUT = 7;
  /** Ordinal value for ScptDriveT. */
  public static final int SCPT_DRIVE_T = 8;
  /** Ordinal value for ScptHighLimit1. */
  public static final int SCPT_HIGH_LIMIT_1 = 9;
  /** Ordinal value for ScptHighLimit2. */
  public static final int SCPT_HIGH_LIMIT_2 = 10;
  /** Ordinal value for ScptHystHigh1. */
  public static final int SCPT_HYST_HIGH_1 = 11;
  /** Ordinal value for ScptHystHigh2. */
  public static final int SCPT_HYST_HIGH_2 = 12;
  /** Ordinal value for ScptHystLow1. */
  public static final int SCPT_HYST_LOW_1 = 13;
  /** Ordinal value for ScptHystLow2. */
  public static final int SCPT_HYST_LOW_2 = 14;
  /** Ordinal value for ScptInFbDly. */
  public static final int SCPT_IN_FB_DLY = 15;
  /** Ordinal value for ScptInvrtOut. */
  public static final int SCPT_INVRT_OUT = 16;
  /** Ordinal value for ScptLocation. */
  public static final int SCPT_LOCATION = 17;
  /** Ordinal value for ScptLowLimit1. */
  public static final int SCPT_LOW_LIMIT_1 = 18;
  /** Ordinal value for ScptLowLimit2. */
  public static final int SCPT_LOW_LIMIT_2 = 19;
  /** Ordinal value for ScptMaxRnge. */
  public static final int SCPT_MAX_RNGE = 20;
  /** Ordinal value for ScptMaxRcvT. */
  public static final int SCPT_MAX_RCV_T = 21;
  /** Ordinal value for ScptMaxSndT. */
  public static final int SCPT_MAX_SND_T = 22;
  /** Ordinal value for ScptMinRnge. */
  public static final int SCPT_MIN_RNGE = 23;
  /** Ordinal value for ScptMinSndT. */
  public static final int SCPT_MIN_SND_T = 24;
  /** Ordinal value for ScptNwrkCnfg. */
  public static final int SCPT_NWRK_CNFG = 25;
  /** Ordinal value for ScptOffset. */
  public static final int SCPT_OFFSET = 26;
  /** Ordinal value for ScptSndDelta. */
  public static final int SCPT_SND_DELTA = 27;
  /** Ordinal value for ScptTrnsTblX. */
  public static final int SCPT_TRNS_TBL_X = 28;
  /** Ordinal value for ScptTrnsTblY. */
  public static final int SCPT_TRNS_TBL_Y = 29;
  /** Ordinal value for ScptOffDely. */
  public static final int SCPT_OFF_DELY = 30;
  /** Ordinal value for ScptGain. */
  public static final int SCPT_GAIN = 31;
  /** Ordinal value for ScptOvrBehave. */
  public static final int SCPT_OVR_BEHAVE = 32;
  /** Ordinal value for ScptOvrValue. */
  public static final int SCPT_OVR_VALUE = 33;
  /** Ordinal value for ScptBypassTime. */
  public static final int SCPT_BYPASS_TIME = 34;
  /** Ordinal value for ScptManOvrTime. */
  public static final int SCPT_MAN_OVR_TIME = 35;
  /** Ordinal value for ScptHumSetpt. */
  public static final int SCPT_HUM_SETPT = 36;
  /** Ordinal value for ScptMaxFlowHeat. */
  public static final int SCPT_MAX_FLOW_HEAT = 37;
  /** Ordinal value for ScptFireInitType. */
  public static final int SCPT_FIRE_INIT_TYPE = 38;
  /** Ordinal value for ScptSmokeNomSens. */
  public static final int SCPT_SMOKE_NOM_SENS = 39;
  /** Ordinal value for ScptSmokeDayAlrmLim. */
  public static final int SCPT_SMOKE_DAY_ALRM_LIM = 40;
  /** Ordinal value for ScptActuatorType. */
  public static final int SCPT_ACTUATOR_TYPE = 41;
  /** Ordinal value for ScptLimitCO2. */
  public static final int SCPT_LIMIT_CO_2 = 42;
  /** Ordinal value for ScptMinDeltaAngl. */
  public static final int SCPT_MIN_DELTA_ANGL = 43;
  /** Ordinal value for ScptDirection. */
  public static final int SCPT_DIRECTION = 44;
  /** Ordinal value for ScptDriveTime. */
  public static final int SCPT_DRIVE_TIME = 45;
  /** Ordinal value for ScptDuctArea. */
  public static final int SCPT_DUCT_AREA = 46;
  /** Ordinal value for ScptMinDeltaFlow. */
  public static final int SCPT_MIN_DELTA_FLOW = 47;
  /** Ordinal value for ScptMaxRcvTime. */
  public static final int SCPT_MAX_RCV_TIME = 48;
  /** Ordinal value for ScptMaxSendTime. */
  public static final int SCPT_MAX_SEND_TIME = 49;
  /** Ordinal value for ScptMaxSetpoint. */
  public static final int SCPT_MAX_SETPOINT = 50;
  /** Ordinal value for ScptMaxFlow. */
  public static final int SCPT_MAX_FLOW = 51;
  /** Ordinal value for ScptMinSendTime. */
  public static final int SCPT_MIN_SEND_TIME = 52;
  /** Ordinal value for ScptMinSetpoint. */
  public static final int SCPT_MIN_SETPOINT = 53;
  /** Ordinal value for ScptMinFlow. */
  public static final int SCPT_MIN_FLOW = 54;
  /** Ordinal value for ScptMinFlowHeat. */
  public static final int SCPT_MIN_FLOW_HEAT = 55;
  /** Ordinal value for ScptMinFlowStby. */
  public static final int SCPT_MIN_FLOW_STBY = 56;
  /** Ordinal value for ScptNomAirFlow. */
  public static final int SCPT_NOM_AIR_FLOW = 57;
  /** Ordinal value for ScptNomAngle. */
  public static final int SCPT_NOM_ANGLE = 58;
  /** Ordinal value for ScptNumValves. */
  public static final int SCPT_NUM_VALVES = 59;
  /** Ordinal value for ScptSetPnts. */
  public static final int SCPT_SET_PNTS = 60;
  /** Ordinal value for ScptOemType. */
  public static final int SCPT_OEM_TYPE = 61;
  /** Ordinal value for ScptMinDeltaRH. */
  public static final int SCPT_MIN_DELTA_RH = 62;
  /** Ordinal value for ScptMinDeltaCO2. */
  public static final int SCPT_MIN_DELTA_CO_2 = 63;
  /** Ordinal value for ScptMinDeltaTemp. */
  public static final int SCPT_MIN_DELTA_TEMP = 64;
  /** Ordinal value for ScptSensConstTmp. */
  public static final int SCPT_SENS_CONST_TMP = 65;
  /** Ordinal value for ScptGainVAV. */
  public static final int SCPT_GAIN_VAV = 66;
  /** Ordinal value for ScptSensConstVAV. */
  public static final int SCPT_SENS_CONST_VAV = 67;
  /** Ordinal value for ScptOffsetCO2. */
  public static final int SCPT_OFFSET_CO_2 = 68;
  /** Ordinal value for ScptOffsetRH. */
  public static final int SCPT_OFFSET_RH = 69;
  /** Ordinal value for ScptOffsetTemp. */
  public static final int SCPT_OFFSET_TEMP = 70;
  /** Ordinal value for ScptDefltBehave. */
  public static final int SCPT_DEFLT_BEHAVE = 71;
  /** Ordinal value for ScptPwrUpDelay. */
  public static final int SCPT_PWR_UP_DELAY = 72;
  /** Ordinal value for ScptPwrUpState. */
  public static final int SCPT_PWR_UP_STATE = 73;
  /** Ordinal value for ScptHvacMode. */
  public static final int SCPT_HVAC_MODE = 74;
  /** Ordinal value for ScptCoolSetpt. */
  public static final int SCPT_COOL_SETPT = 75;
  /** Ordinal value for ScptCoolLowerSP. */
  public static final int SCPT_COOL_LOWER_SP = 76;
  /** Ordinal value for ScptCoolUpperSP. */
  public static final int SCPT_COOL_UPPER_SP = 77;
  /** Ordinal value for ScptHeatSetpt. */
  public static final int SCPT_HEAT_SETPT = 78;
  /** Ordinal value for ScptHeatLowerSP. */
  public static final int SCPT_HEAT_LOWER_SP = 79;
  /** Ordinal value for ScptHeatUpperSP. */
  public static final int SCPT_HEAT_UPPER_SP = 80;
  /** Ordinal value for ScptLimitChlrCap. */
  public static final int SCPT_LIMIT_CHLR_CAP = 81;
  /** Ordinal value for ScptLuxSetpoint. */
  public static final int SCPT_LUX_SETPOINT = 82;
  /** Ordinal value for ScptStep. */
  public static final int SCPT_STEP = 83;
  /** Ordinal value for ScptOnOffHysteresis. */
  public static final int SCPT_ON_OFF_HYSTERESIS = 84;
  /** Ordinal value for ScptClOffDelay. */
  public static final int SCPT_CL_OFF_DELAY = 85;
  /** Ordinal value for ScptClOnDelay. */
  public static final int SCPT_CL_ON_DELAY = 86;
  /** Ordinal value for ScptPowerupState. */
  public static final int SCPT_POWERUP_STATE = 87;
  /** Ordinal value for ScptMinDeltaLevel. */
  public static final int SCPT_MIN_DELTA_LEVEL = 88;
  /** Ordinal value for ScptReflection. */
  public static final int SCPT_REFLECTION = 89;
  /** Ordinal value for ScptFieldCalib. */
  public static final int SCPT_FIELD_CALIB = 90;
  /** Ordinal value for ScptHoldTime. */
  public static final int SCPT_HOLD_TIME = 91;
  /** Ordinal value for ScptStepValue. */
  public static final int SCPT_STEP_VALUE = 92;
  /** Ordinal value for ScptMaxOut. */
  public static final int SCPT_MAX_OUT = 93;
  /** Ordinal value for ScptSceneNmbr. */
  public static final int SCPT_SCENE_NMBR = 94;
  /** Ordinal value for ScptFadeTime. */
  public static final int SCPT_FADE_TIME = 95;
  /** Ordinal value for ScptDelayTime. */
  public static final int SCPT_DELAY_TIME = 96;
  /** Ordinal value for ScptMasterSlave. */
  public static final int SCPT_MASTER_SLAVE = 97;
  /** Ordinal value for ScptUpdateRate. */
  public static final int SCPT_UPDATE_RATE = 98;
  /** Ordinal value for ScptSummerTime. */
  public static final int SCPT_SUMMER_TIME = 99;
  /** Ordinal value for ScptWinterTime. */
  public static final int SCPT_WINTER_TIME = 100;
  /** Ordinal value for ScptManualAllowed. */
  public static final int SCPT_MANUAL_ALLOWED = 101;
  /** Ordinal value for ScptDefWeekMask. */
  public static final int SCPT_DEF_WEEK_MASK = 102;
  /** Ordinal value for ScptDayDateIndex. */
  public static final int SCPT_DAY_DATE_INDEX = 103;
  /** Ordinal value for ScptTimeEvent. */
  public static final int SCPT_TIME_EVENT = 104;
  /** Ordinal value for ScptModeHrtBt. */
  public static final int SCPT_MODE_HRT_BT = 105;
  /** Ordinal value for ScptDefrostMode. */
  public static final int SCPT_DEFROST_MODE = 106;
  /** Ordinal value for ScptMaxDefrstTime. */
  public static final int SCPT_MAX_DEFRST_TIME = 107;
  /** Ordinal value for ScptDrainDelay. */
  public static final int SCPT_DRAIN_DELAY = 108;
  /** Ordinal value for ScptInjDelay. */
  public static final int SCPT_INJ_DELAY = 109;
  /** Ordinal value for ScptMaxDefrstTemp. */
  public static final int SCPT_MAX_DEFRST_TEMP = 110;
  /** Ordinal value for ScptStrtupDelay. */
  public static final int SCPT_STRTUP_DELAY = 111;
  /** Ordinal value for ScptTermTimeTemp. */
  public static final int SCPT_TERM_TIME_TEMP = 112;
  /** Ordinal value for ScptPumpDownDelay. */
  public static final int SCPT_PUMP_DOWN_DELAY = 113;
  /** Ordinal value for ScptSuperHtRefInit. */
  public static final int SCPT_SUPER_HT_REF_INIT = 114;
  /** Ordinal value for ScptStrtupOpen. */
  public static final int SCPT_STRTUP_OPEN = 115;
  /** Ordinal value for ScptSuperHtRefMin. */
  public static final int SCPT_SUPER_HT_REF_MIN = 116;
  /** Ordinal value for ScptRefrigGlide. */
  public static final int SCPT_REFRIG_GLIDE = 117;
  /** Ordinal value for ScptSuperHtRefMax. */
  public static final int SCPT_SUPER_HT_REF_MAX = 118;
  /** Ordinal value for ScptRefrigType. */
  public static final int SCPT_REFRIG_TYPE = 119;
  /** Ordinal value for ScptThermMode. */
  public static final int SCPT_THERM_MODE = 120;
  /** Ordinal value for ScptDayNightCntrl. */
  public static final int SCPT_DAY_NIGHT_CNTRL = 121;
  /** Ordinal value for ScptDiffNight. */
  public static final int SCPT_DIFF_NIGHT = 122;
  /** Ordinal value for ScptHighLimTemp. */
  public static final int SCPT_HIGH_LIM_TEMP = 123;
  /** Ordinal value for ScptHighLimDly. */
  public static final int SCPT_HIGH_LIM_DLY = 124;
  /** Ordinal value for ScptCutOutValue. */
  public static final int SCPT_CUT_OUT_VALUE = 125;
  /** Ordinal value for ScptAirTemp1Day. */
  public static final int SCPT_AIR_TEMP_1_DAY = 126;
  /** Ordinal value for ScptSmokeNightAlrmLim. */
  public static final int SCPT_SMOKE_NIGHT_ALRM_LIM = 127;
  /** Ordinal value for ScptLowLimTemp. */
  public static final int SCPT_LOW_LIM_TEMP = 128;
  /** Ordinal value for ScptLowLimDly. */
  public static final int SCPT_LOW_LIM_DLY = 129;
  /** Ordinal value for ScptDiffValue. */
  public static final int SCPT_DIFF_VALUE = 130;
  /** Ordinal value for ScptAirTemp1Night. */
  public static final int SCPT_AIR_TEMP_1_NIGHT = 131;
  /** Ordinal value for ScptAirTemp1Alrm. */
  public static final int SCPT_AIR_TEMP_1_ALRM = 132;
  /** Ordinal value for ScptHighLimDefrDly. */
  public static final int SCPT_HIGH_LIM_DEFR_DLY = 133;
  /** Ordinal value for ScptDeltaNight. */
  public static final int SCPT_DELTA_NIGHT = 134;
  /** Ordinal value for ScptRunHrInit. */
  public static final int SCPT_RUN_HR_INIT = 135;
  /** Ordinal value for ScptRunHrAlarm. */
  public static final int SCPT_RUN_HR_ALARM = 136;
  /** Ordinal value for ScptEnergyCntInit. */
  public static final int SCPT_ENERGY_CNT_INIT = 137;
  /** Ordinal value for ScptSmokeDayPreAlrmLim. */
  public static final int SCPT_SMOKE_DAY_PRE_ALRM_LIM = 138;
  /** Ordinal value for ScptDebounce. */
  public static final int SCPT_DEBOUNCE = 139;
  /** Ordinal value for ScptSmokeNightPreAlrmLim. */
  public static final int SCPT_SMOKE_NIGHT_PRE_ALRM_LIM = 140;
  /** Ordinal value for ScptZoneNum. */
  public static final int SCPT_ZONE_NUM = 141;
  /** Ordinal value for ScptThermAlrmROR. */
  public static final int SCPT_THERM_ALRM_ROR = 142;
  /** Ordinal value for ScptVisOutput. */
  public static final int SCPT_VIS_OUTPUT = 143;
  /** Ordinal value for ScptAudOutput. */
  public static final int SCPT_AUD_OUTPUT = 144;
  /** Ordinal value for ScptFlashFreq. */
  public static final int SCPT_FLASH_FREQ = 145;
  /** Ordinal value for ScptInstallDate. */
  public static final int SCPT_INSTALL_DATE = 146;
  /** Ordinal value for ScptMaintDate. */
  public static final int SCPT_MAINT_DATE = 147;
  /** Ordinal value for ScptManfDate. */
  public static final int SCPT_MANF_DATE = 148;
  /** Ordinal value for ScptFireTxt1. */
  public static final int SCPT_FIRE_TXT_1 = 149;
  /** Ordinal value for ScptFireTxt2. */
  public static final int SCPT_FIRE_TXT_2 = 150;
  /** Ordinal value for ScptFireTxt3. */
  public static final int SCPT_FIRE_TXT_3 = 151;
  /** Ordinal value for ScptThermThreshold. */
  public static final int SCPT_THERM_THRESHOLD = 152;
  /** Ordinal value for ScptFireIndicate. */
  public static final int SCPT_FIRE_INDICATE = 153;
  /** Ordinal value for ScptTimeZone. */
  public static final int SCPT_TIME_ZONE = 154;
  /** Ordinal value for ScptPrimeVal. */
  public static final int SCPT_PRIME_VAL = 155;
  /** Ordinal value for ScptSecondVal. */
  public static final int SCPT_SECOND_VAL = 156;
  /** Ordinal value for ScptSceneOffset. */
  public static final int SCPT_SCENE_OFFSET = 157;
  /** Ordinal value for ScptNomRPM. */
  public static final int SCPT_NOM_RPM = 158;
  /** Ordinal value for ScptNomFreq. */
  public static final int SCPT_NOM_FREQ = 159;
  /** Ordinal value for ScptRampUpTm. */
  public static final int SCPT_RAMP_UP_TM = 160;
  /** Ordinal value for ScptRampDownTm. */
  public static final int SCPT_RAMP_DOWN_TM = 161;
  /** Ordinal value for ScptDefScale. */
  public static final int SCPT_DEF_SCALE = 162;
  /** Ordinal value for ScptRegName. */
  public static final int SCPT_REG_NAME = 163;
  /** Ordinal value for ScptBaseValue. */
  public static final int SCPT_BASE_VALUE = 164;
  /** Ordinal value for ScptDevMajVer. */
  public static final int SCPT_DEV_MAJ_VER = 165;
  /** Ordinal value for ScptDevMinVer. */
  public static final int SCPT_DEV_MIN_VER = 166;
  /** Ordinal value for ScptObjMajVer. */
  public static final int SCPT_OBJ_MAJ_VER = 167;
  /** Ordinal value for ScptObjMinVer. */
  public static final int SCPT_OBJ_MIN_VER = 168;
  /** Ordinal value for ScptHvacType. */
  public static final int SCPT_HVAC_TYPE = 169;
  /** Ordinal value for ScptTimeout. */
  public static final int SCPT_TIMEOUT = 170;
  /** Ordinal value for ScptControlPriority. */
  public static final int SCPT_CONTROL_PRIORITY = 171;
  /** Ordinal value for ScptDeviceGroupID. */
  public static final int SCPT_DEVICE_GROUP_ID = 172;
  /** Ordinal value for ScptMaxPrivacyZones. */
  public static final int SCPT_MAX_PRIVACY_ZONES = 173;
  /** Ordinal value for ScptMaxCameraPrepositions. */
  public static final int SCPT_MAX_CAMERA_PREPOSITIONS = 174;
  /** Ordinal value for ScptDefaultPanTiltZoomSpeeds. */
  public static final int SCPT_DEFAULT_PAN_TILT_ZOOM_SPEEDS = 175;
  /** Ordinal value for ScptDefaultAutoPanSpeed. */
  public static final int SCPT_DEFAULT_AUTO_PAN_SPEED = 176;
  /** Ordinal value for ScptAutoAnswer. */
  public static final int SCPT_AUTO_ANSWER = 177;
  /** Ordinal value for ScptDialString. */
  public static final int SCPT_DIAL_STRING = 178;
  /** Ordinal value for ScptSerialNumber. */
  public static final int SCPT_SERIAL_NUMBER = 179;
  /** Ordinal value for ScptNormalRotationalSpeed. */
  public static final int SCPT_NORMAL_ROTATIONAL_SPEED = 180;
  /** Ordinal value for ScptStandbyRotationalSpeed. */
  public static final int SCPT_STANDBY_ROTATIONAL_SPEED = 181;
  /** Ordinal value for ScptPartNumber. */
  public static final int SCPT_PART_NUMBER = 182;
  /** Ordinal value for ScptDischargeAirCoolingSetpoint. */
  public static final int SCPT_DISCHARGE_AIR_COOLING_SETPOINT = 183;
  /** Ordinal value for ScptDischargeAirHeatingSetpoint. */
  public static final int SCPT_DISCHARGE_AIR_HEATING_SETPOINT = 184;
  /** Ordinal value for ScptMaxSupplyFanCapacity. */
  public static final int SCPT_MAX_SUPPLY_FAN_CAPACITY = 185;
  /** Ordinal value for ScptMinSupplyFanCapacity. */
  public static final int SCPT_MIN_SUPPLY_FAN_CAPACITY = 186;
  /** Ordinal value for ScptMaxReturnExhaustFanCapacity. */
  public static final int SCPT_MAX_RETURN_EXHAUST_FAN_CAPACITY = 187;
  /** Ordinal value for ScptMinReturnExhaustFanCapacity. */
  public static final int SCPT_MIN_RETURN_EXHAUST_FAN_CAPACITY = 188;
  /** Ordinal value for ScptDuctStaticPressureSetpoint. */
  public static final int SCPT_DUCT_STATIC_PRESSURE_SETPOINT = 189;
  /** Ordinal value for ScptMaxDuctStaticPressureSetpoint. */
  public static final int SCPT_MAX_DUCT_STATIC_PRESSURE_SETPOINT = 190;
  /** Ordinal value for ScptMinDuctStaticPressureSetpoint. */
  public static final int SCPT_MIN_DUCT_STATIC_PRESSURE_SETPOINT = 191;
  /** Ordinal value for ScptDuctStaticPressureLimit. */
  public static final int SCPT_DUCT_STATIC_PRESSURE_LIMIT = 192;
  /** Ordinal value for ScptBuildingStaticPressureSetpoint. */
  public static final int SCPT_BUILDING_STATIC_PRESSURE_SETPOINT = 193;
  /** Ordinal value for ScptReturnFanStaticPressureSetpoint. */
  public static final int SCPT_RETURN_FAN_STATIC_PRESSURE_SETPOINT = 194;
  /** Ordinal value for ScptFanDifferentialSetpoint. */
  public static final int SCPT_FAN_DIFFERENTIAL_SETPOINT = 195;
  /** Ordinal value for ScptMixedAirLowLimitSetpoint. */
  public static final int SCPT_MIXED_AIR_LOW_LIMIT_SETPOINT = 196;
  /** Ordinal value for ScptMixedAirTempSetpoint. */
  public static final int SCPT_MIXED_AIR_TEMP_SETPOINT = 197;
  /** Ordinal value for ScptMinOutdoorAirFlowSetpoint. */
  public static final int SCPT_MIN_OUTDOOR_AIR_FLOW_SETPOINT = 198;
  /** Ordinal value for ScptOutdoorAirTempSetpoint. */
  public static final int SCPT_OUTDOOR_AIR_TEMP_SETPOINT = 199;
  /** Ordinal value for ScptOutdoorAirEnthalpySetpoint. */
  public static final int SCPT_OUTDOOR_AIR_ENTHALPY_SETPOINT = 200;
  /** Ordinal value for ScptDiffTempSetpoint. */
  public static final int SCPT_DIFF_TEMP_SETPOINT = 201;
  /** Ordinal value for ScptExhaustEnablePosition. */
  public static final int SCPT_EXHAUST_ENABLE_POSITION = 202;
  /** Ordinal value for ScptSpaceHumSetpoint. */
  public static final int SCPT_SPACE_HUM_SETPOINT = 203;
  /** Ordinal value for ScptDischargeAirDewpointSetpoint. */
  public static final int SCPT_DISCHARGE_AIR_DEWPOINT_SETPOINT = 204;
  /** Ordinal value for ScptMaxDischargeAirCoolingSetpoint. */
  public static final int SCPT_MAX_DISCHARGE_AIR_COOLING_SETPOINT = 205;
  /** Ordinal value for ScptMinDischargeAirCoolingSetpoint. */
  public static final int SCPT_MIN_DISCHARGE_AIR_COOLING_SETPOINT = 206;
  /** Ordinal value for ScptMaxDischargeAirHeatingSetpoint. */
  public static final int SCPT_MAX_DISCHARGE_AIR_HEATING_SETPOINT = 207;
  /** Ordinal value for ScptMinDischargeAirHeatingSetpoint. */
  public static final int SCPT_MIN_DISCHARGE_AIR_HEATING_SETPOINT = 208;
  /** Ordinal value for ScptCoolingLockout. */
  public static final int SCPT_COOLING_LOCKOUT = 209;
  /** Ordinal value for ScptHeatingLockout. */
  public static final int SCPT_HEATING_LOCKOUT = 210;
  /** Ordinal value for ScptCoolingResetEnable. */
  public static final int SCPT_COOLING_RESET_ENABLE = 211;
  /** Ordinal value for ScptHeatingResetEnable. */
  public static final int SCPT_HEATING_RESET_ENABLE = 212;
  /** Ordinal value for ScptSetpoint. */
  public static final int SCPT_SETPOINT = 213;
  /** Ordinal value for ScptTemperatureHysteresis. */
  public static final int SCPT_TEMPERATURE_HYSTERESIS = 214;
  /** Ordinal value for ScptControlTemperatureWeighting. */
  public static final int SCPT_CONTROL_TEMPERATURE_WEIGHTING = 215;
  /** Ordinal value for ScptPwmPeriod. */
  public static final int SCPT_PWM_PERIOD = 216;
  /** Ordinal value for ScptDefrostInternalSchedule. */
  public static final int SCPT_DEFROST_INTERNAL_SCHEDULE = 217;
  /** Ordinal value for ScptDefrostStart. */
  public static final int SCPT_DEFROST_START = 218;
  /** Ordinal value for ScptDefrostCycles. */
  public static final int SCPT_DEFROST_CYCLES = 219;
  /** Ordinal value for ScptMinDefrostTime. */
  public static final int SCPT_MIN_DEFROST_TIME = 220;
  /** Ordinal value for ScptMaxDefrostTime. */
  public static final int SCPT_MAX_DEFROST_TIME = 221;
  /** Ordinal value for ScptDefrostFanDelay. */
  public static final int SCPT_DEFROST_FAN_DELAY = 222;
  /** Ordinal value for ScptDefrostRecoveryTime. */
  public static final int SCPT_DEFROST_RECOVERY_TIME = 223;
  /** Ordinal value for ScptDefrostHold. */
  public static final int SCPT_DEFROST_HOLD = 224;
  /** Ordinal value for ScptDefrostDetect. */
  public static final int SCPT_DEFROST_DETECT = 225;
  /** Ordinal value for ScptScheduleInternal. */
  public static final int SCPT_SCHEDULE_INTERNAL = 226;
  /** Ordinal value for ScptTempOffset. */
  public static final int SCPT_TEMP_OFFSET = 227;
  /** Ordinal value for ScptAudibleLevel. */
  public static final int SCPT_AUDIBLE_LEVEL = 228;
  /** Ordinal value for ScptScrollSpeed. */
  public static final int SCPT_SCROLL_SPEED = 229;
  /** Ordinal value for ScptBrightness. */
  public static final int SCPT_BRIGHTNESS = 230;
  /** Ordinal value for ScptOrientation. */
  public static final int SCPT_ORIENTATION = 231;
  /** Ordinal value for ScptInstalledLevel. */
  public static final int SCPT_INSTALLED_LEVEL = 232;
  /** Ordinal value for ScptPumpCharacteristic. */
  public static final int SCPT_PUMP_CHARACTERISTIC = 233;
  /** Ordinal value for ScptMinPressureSetpoint. */
  public static final int SCPT_MIN_PRESSURE_SETPOINT = 234;
  /** Ordinal value for ScptMaxPressureSetpoint. */
  public static final int SCPT_MAX_PRESSURE_SETPOINT = 235;
  /** Ordinal value for ScptMinFlowSetpoint. */
  public static final int SCPT_MIN_FLOW_SETPOINT = 236;
  /** Ordinal value for ScptMaxFlowSetpoint. */
  public static final int SCPT_MAX_FLOW_SETPOINT = 237;
  /** Ordinal value for ScptDeviceControlMode. */
  public static final int SCPT_DEVICE_CONTROL_MODE = 238;
  /** Ordinal value for ScptMinRemotePressureSetpoint. */
  public static final int SCPT_MIN_REMOTE_PRESSURE_SETPOINT = 239;
  /** Ordinal value for ScptMaxRemotePressureSetpoint. */
  public static final int SCPT_MAX_REMOTE_PRESSURE_SETPOINT = 240;
  /** Ordinal value for ScptMinRemoteFlowSetpoint. */
  public static final int SCPT_MIN_REMOTE_FLOW_SETPOINT = 241;
  /** Ordinal value for ScptMaxRemoteFlowSetpoint. */
  public static final int SCPT_MAX_REMOTE_FLOW_SETPOINT = 242;
  /** Ordinal value for ScptMinRemoteTempSetpoint. */
  public static final int SCPT_MIN_REMOTE_TEMP_SETPOINT = 243;
  /** Ordinal value for ScptMaxRemoteTempSetpoint. */
  public static final int SCPT_MAX_REMOTE_TEMP_SETPOINT = 244;
  /** Ordinal value for ScptControlSignal. */
  public static final int SCPT_CONTROL_SIGNAL = 245;
  /** Ordinal value for ScptNightPurgePosition. */
  public static final int SCPT_NIGHT_PURGE_POSITION = 246;
  /** Ordinal value for ScptFreeCoolPosition. */
  public static final int SCPT_FREE_COOL_POSITION = 247;
  /** Ordinal value for ScptValveFlowCharacteristic. */
  public static final int SCPT_VALVE_FLOW_CHARACTERISTIC = 248;
  /** Ordinal value for ScptValveOperatingMode. */
  public static final int SCPT_VALVE_OPERATING_MODE = 249;
  /** Ordinal value for ScptEmergencyPosition. */
  public static final int SCPT_EMERGENCY_POSITION = 250;
  /** Ordinal value for ScptBlockProtectionTime. */
  public static final int SCPT_BLOCK_PROTECTION_TIME = 251;
  /** Ordinal value for ScptMinStroke. */
  public static final int SCPT_MIN_STROKE = 252;
  /** Ordinal value for ScptMaxStroke. */
  public static final int SCPT_MAX_STROKE = 253;
  /** Ordinal value for ScptNvType. */
  public static final int SCPT_NV_TYPE = 254;
  /** Ordinal value for ScptMaxNVLength. */
  public static final int SCPT_MAX_NV_LENGTH = 255;
  /** Ordinal value for ScptNvDynamicAssignment. */
  public static final int SCPT_NV_DYNAMIC_ASSIGNMENT = 256;
  /** Ordinal value for ScptSafExtCnfg. */
  public static final int SCPT_SAF_EXT_CNFG = 257;
  /** Ordinal value for ScptEmergCnfg. */
  public static final int SCPT_EMERG_CNFG = 258;
  /** Ordinal value for ScptSluiceCnfg. */
  public static final int SCPT_SLUICE_CNFG = 259;
  /** Ordinal value for ScptFanOperation. */
  public static final int SCPT_FAN_OPERATION = 260;
  /** Ordinal value for ScptMinFlowUnit. */
  public static final int SCPT_MIN_FLOW_UNIT = 261;
  /** Ordinal value for ScptMaxFlowUnit. */
  public static final int SCPT_MAX_FLOW_UNIT = 262;
  /** Ordinal value for ScptMinFlowHeatStby. */
  public static final int SCPT_MIN_FLOW_HEAT_STBY = 263;
  /** Ordinal value for ScptMinFlowUnitStby. */
  public static final int SCPT_MIN_FLOW_UNIT_STBY = 264;
  /** Ordinal value for ScptOffsetFlow. */
  public static final int SCPT_OFFSET_FLOW = 265;
  /** Ordinal value for ScptAreaDuctHeat. */
  public static final int SCPT_AREA_DUCT_HEAT = 266;
  /** Ordinal value for ScptNomAirFlowHeat. */
  public static final int SCPT_NOM_AIR_FLOW_HEAT = 267;
  /** Ordinal value for ScptGainVAVHeat. */
  public static final int SCPT_GAIN_VAVHEAT = 268;
  /** Ordinal value for ScptNumDampers. */
  public static final int SCPT_NUM_DAMPERS = 269;
  /** Ordinal value for ScptMinFlowUnitHeat. */
  public static final int SCPT_MIN_FLOW_UNIT_HEAT = 270;
  /** Ordinal value for ScptSaturationDelay. */
  public static final int SCPT_SATURATION_DELAY = 271;
  /** Ordinal value for ScptEffectivePeriod. */
  public static final int SCPT_EFFECTIVE_PERIOD = 272;
  /** Ordinal value for ScptScheduleDates. */
  public static final int SCPT_SCHEDULE_DATES = 273;
  /** Ordinal value for ScptSchedule. */
  public static final int SCPT_SCHEDULE = 274;
  /** Ordinal value for ScptScheduleTimeValue. */
  public static final int SCPT_SCHEDULE_TIME_VALUE = 275;
  /** Ordinal value for ScptValueDefinition. */
  public static final int SCPT_VALUE_DEFINITION = 276;
  /** Ordinal value for ScptValueName. */
  public static final int SCPT_VALUE_NAME = 277;
  /** Ordinal value for ScptWeeklySchedule. */
  public static final int SCPT_WEEKLY_SCHEDULE = 278;
  /** Ordinal value for ScptScheduleName. */
  public static final int SCPT_SCHEDULE_NAME = 279;
  /** Ordinal value for ScptValveStroke. */
  public static final int SCPT_VALVE_STROKE = 280;
  /** Ordinal value for ScptValveNominalSize. */
  public static final int SCPT_VALVE_NOMINAL_SIZE = 281;
  /** Ordinal value for ScptValveKvs. */
  public static final int SCPT_VALVE_KVS = 282;
  /** Ordinal value for ScptValveType. */
  public static final int SCPT_VALVE_TYPE = 283;
  /** Ordinal value for ScptActuatorCharacteristic. */
  public static final int SCPT_ACTUATOR_CHARACTERISTIC = 284;
  /** Ordinal value for ScptTrnsTblX2. */
  public static final int SCPT_TRNS_TBL_X2 = 285;
  /** Ordinal value for ScptTrnsTblY2. */
  public static final int SCPT_TRNS_TBL_Y2 = 286;
  /** Ordinal value for ScptCombFlowCharacteristic. */
  public static final int SCPT_COMB_FLOW_CHARACTERISTIC = 287;
  /** Ordinal value for ScptTrnsTblX3. */
  public static final int SCPT_TRNS_TBL_X3 = 288;
  /** Ordinal value for ScptTrnsTblY3. */
  public static final int SCPT_TRNS_TBL_Y3 = 289;
  /** Ordinal value for ScptRunTimeAlarm. */
  public static final int SCPT_RUN_TIME_ALARM = 290;
  /** Ordinal value for ScptTimePeriod. */
  public static final int SCPT_TIME_PERIOD = 291;
  /** Ordinal value for ScptPulseValue. */
  public static final int SCPT_PULSE_VALUE = 292;
  /** Ordinal value for ScptNumDigits. */
  public static final int SCPT_NUM_DIGITS = 293;
  /** Ordinal value for ScptIdentity. */
  public static final int SCPT_IDENTITY = 294;
  /** Ordinal value for ScptDefaultState. */
  public static final int SCPT_DEFAULT_STATE = 295;
  /** Ordinal value for ScptNvPriority. */
  public static final int SCPT_NV_PRIORITY = 296;
  /** Ordinal value for ScptDefaultSetting. */
  public static final int SCPT_DEFAULT_SETTING = 297;
  /** Ordinal value for ScptLowLimit1Enable. */
  public static final int SCPT_LOW_LIMIT1_ENABLE = 298;
  /** Ordinal value for ScptLowLimit2Enable. */
  public static final int SCPT_LOW_LIMIT2_ENABLE = 299;
  /** Ordinal value for ScptClockCalibration. */
  public static final int SCPT_CLOCK_CALIBRATION = 300;
  /** Ordinal value for ScptNeuronId. */
  public static final int SCPT_NEURON_ID = 301;
  /** Ordinal value for ScptHighLimit1Enable. */
  public static final int SCPT_HIGH_LIMIT1_ENABLE = 302;
  /** Ordinal value for ScptHighLimit2Enable. */
  public static final int SCPT_HIGH_LIMIT2_ENABLE = 303;
  /** Ordinal value for ScptAhamApplianceModel. */
  public static final int SCPT_AHAM_APPLIANCE_MODEL = 304;
  /** Ordinal value for ScptDefInput. */
  public static final int SCPT_DEF_INPUT = 305;
  /** Ordinal value for ScptName1. */
  public static final int SCPT_NAME1 = 306;
  /** Ordinal value for ScptScene. */
  public static final int SCPT_SCENE = 307;
  /** Ordinal value for ScptSceneTiming. */
  public static final int SCPT_SCENE_TIMING = 308;
  /** Ordinal value for ScptName2. */
  public static final int SCPT_NAME2 = 309;
  /** Ordinal value for ScptName3. */
  public static final int SCPT_NAME3 = 310;
  /** Ordinal value for ScptButtonPressAction. */
  public static final int SCPT_BUTTON_PRESS_ACTION = 311;
  /** Ordinal value for ScptButtonColor. */
  public static final int SCPT_BUTTON_COLOR = 312;
  /** Ordinal value for ScptButtonRepeatInterval. */
  public static final int SCPT_BUTTON_REPEAT_INTERVAL = 313;
  /** Ordinal value for ScptButtonHoldAction. */
  public static final int SCPT_BUTTON_HOLD_ACTION = 314;
  /** Ordinal value for ScptPwrSendOnDelta. */
  public static final int SCPT_PWR_SEND_ON_DELTA = 315;
  /** Ordinal value for ScptSceneName. */
  public static final int SCPT_SCENE_NAME = 316;
  /** Ordinal value for ScptMaxPower. */
  public static final int SCPT_MAX_POWER = 317;
  /** Ordinal value for ScptIfaceDesc. */
  public static final int SCPT_IFACE_DESC = 318;
  /** Ordinal value for ScptMonInterval. */
  public static final int SCPT_MON_INTERVAL = 319;
  /** Ordinal value for ScptLinkPowerDetectEnable. */
  public static final int SCPT_LINK_POWER_DETECT_ENABLE = 320;
  /** Ordinal value for ScptScanTime. */
  public static final int SCPT_SCAN_TIME = 321;
  /** Ordinal value for ScptDevListDesc. */
  public static final int SCPT_DEV_LIST_DESC = 322;
  /** Ordinal value for ScptDevListEntry. */
  public static final int SCPT_DEV_LIST_ENTRY = 323;
  /** Ordinal value for ScptLogCapacity. */
  public static final int SCPT_LOG_CAPACITY = 324;
  /** Ordinal value for ScptLogNotificationThreshold. */
  public static final int SCPT_LOG_NOTIFICATION_THRESHOLD = 325;
  /** Ordinal value for ScptLogSize. */
  public static final int SCPT_LOG_SIZE = 326;
  /** Ordinal value for ScptLogType. */
  public static final int SCPT_LOG_TYPE = 327;
  /** Ordinal value for ScptFanInEnable. */
  public static final int SCPT_FAN_IN_ENABLE = 328;
  /** Ordinal value for ScptLogTimestampEnable. */
  public static final int SCPT_LOG_TIMESTAMP_ENABLE = 329;
  /** Ordinal value for ScptLogHighLimit. */
  public static final int SCPT_LOG_HIGH_LIMIT = 330;
  /** Ordinal value for ScptLogLowLimit. */
  public static final int SCPT_LOG_LOW_LIMIT = 331;
  /** Ordinal value for ScptMaxFanIn. */
  public static final int SCPT_MAX_FAN_IN = 332;
  /** Ordinal value for ScptLogMinDeltaTime. */
  public static final int SCPT_LOG_MIN_DELTA_TIME = 333;
  /** Ordinal value for ScptLogMinDeltaValue. */
  public static final int SCPT_LOG_MIN_DELTA_VALUE = 334;
  /** Ordinal value for ScptPollRate. */
  public static final int SCPT_POLL_RATE = 335;
  /** Ordinal value for ScptSourceAddress. */
  public static final int SCPT_SOURCE_ADDRESS = 336;
  /** Ordinal value for ScptLogRecord. */
  public static final int SCPT_LOG_RECORD = 337;
  /** Ordinal value for ScptLogFileHeader. */
  public static final int SCPT_LOG_FILE_HEADER = 338;
  /** Ordinal value for ScptLogAlarmThreshold. */
  public static final int SCPT_LOG_ALARM_THRESHOLD = 339;
  /** Ordinal value for ScptLogRequest. */
  public static final int SCPT_LOG_REQUEST = 340;
  /** Ordinal value for ScptLogResponse. */
  public static final int SCPT_LOG_RESPONSE = 341;
  /** Ordinal value for ScptLightingGroupEnable. */
  public static final int SCPT_LIGHTING_GROUP_ENABLE = 342;
  /** Ordinal value for ScptSceneColor. */
  public static final int SCPT_SCENE_COLOR = 343;
  /** Ordinal value for ScptBkupSchedule. */
  public static final int SCPT_BKUP_SCHEDULE = 344;
  /** Ordinal value for ScptOLCLimits. */
  public static final int SCPT_OLCLIMITS = 345;
  /** Ordinal value for ScptLampPower. */
  public static final int SCPT_LAMP_POWER = 346;
  /** Ordinal value for ScptDeviceOutSelection. */
  public static final int SCPT_DEVICE_OUT_SELECTION = 347;
  /** Ordinal value for ScptEnableStatusMsg. */
  public static final int SCPT_ENABLE_STATUS_MSG = 348;
  /** Ordinal value for ScptMaxLevelVolt. */
  public static final int SCPT_MAX_LEVEL_VOLT = 349;
  /** Ordinal value for ScptGeoLocation. */
  public static final int SCPT_GEO_LOCATION = 350;
  /** Ordinal value for ScptProgName. */
  public static final int SCPT_PROG_NAME = 351;
  /** Ordinal value for ScptProgRevision. */
  public static final int SCPT_PROG_REVISION = 352;
  /** Ordinal value for ScptProgSelect. */
  public static final int SCPT_PROG_SELECT = 353;
  /** Ordinal value for ScptProgSourceLocation. */
  public static final int SCPT_PROG_SOURCE_LOCATION = 354;
  /** Ordinal value for ScptProgFileIndexes. */
  public static final int SCPT_PROG_FILE_INDEXES = 355;
  /** Ordinal value for ScptProgCmdHistory. */
  public static final int SCPT_PROG_CMD_HISTORY = 356;
  /** Ordinal value for ScptProgStateHistory. */
  public static final int SCPT_PROG_STATE_HISTORY = 357;
  /** Ordinal value for ScptNsdsFbIndex. */
  public static final int SCPT_NSDS_FB_INDEX = 358;
  /** Ordinal value for ScptCurrentSenseEnable. */
  public static final int SCPT_CURRENT_SENSE_ENABLE = 359;
  /** Ordinal value for ScptMeasurementInterval. */
  public static final int SCPT_MEASUREMENT_INTERVAL = 360;
  /** Ordinal value for ScptLightingGroupMembership. */
  public static final int SCPT_LIGHTING_GROUP_MEMBERSHIP = 361;
  /** Ordinal value for ScptLoadControlOffset. */
  public static final int SCPT_LOAD_CONTROL_OFFSET = 362;
  /** Ordinal value for ScptProgErrorHistory. */
  public static final int SCPT_PROG_ERROR_HISTORY = 363;
  /** Ordinal value for ScptNvUsage. */
  public static final int SCPT_NV_USAGE = 364;
  /** Ordinal value for ScptScheduleSunday. */
  public static final int SCPT_SCHEDULE_SUNDAY = 365;
  /** Ordinal value for ScptScheduleMonday. */
  public static final int SCPT_SCHEDULE_MONDAY = 366;
  /** Ordinal value for ScptScheduleTuesday. */
  public static final int SCPT_SCHEDULE_TUESDAY = 367;
  /** Ordinal value for ScptScheduleWednesday. */
  public static final int SCPT_SCHEDULE_WEDNESDAY = 368;
  /** Ordinal value for ScptScheduleThursday. */
  public static final int SCPT_SCHEDULE_THURSDAY = 369;
  /** Ordinal value for ScptScheduleFriday. */
  public static final int SCPT_SCHEDULE_FRIDAY = 370;
  /** Ordinal value for ScptScheduleSaturday. */
  public static final int SCPT_SCHEDULE_SATURDAY = 371;
  /** Ordinal value for ScptOccupancyBehavior. */
  public static final int SCPT_OCCUPANCY_BEHAVIOR = 372;
  /** Ordinal value for ScptTimeSource. */
  public static final int SCPT_TIME_SOURCE = 373;
  /** Ordinal value for ScptScheduleException. */
  public static final int SCPT_SCHEDULE_EXCEPTION = 374;
  /** Ordinal value for ScptScheduleHoliday. */
  public static final int SCPT_SCHEDULE_HOLIDAY = 375;
  /** Ordinal value for ScptRandomizationInterval. */
  public static final int SCPT_RANDOMIZATION_INTERVAL = 376;
  /** Ordinal value for ScptSunriseTime. */
  public static final int SCPT_SUNRISE_TIME = 377;
  /** Ordinal value for ScptSunsetTime. */
  public static final int SCPT_SUNSET_TIME = 378;
  /** Ordinal value for ScptSchedulerOptions. */
  public static final int SCPT_SCHEDULER_OPTIONS = 379;
  /** Ordinal value for ScptOccupancyThresholds. */
  public static final int SCPT_OCCUPANCY_THRESHOLDS = 380;

  /** BLonScptType constant for ScptXxx. */
  public static final BLonScptType ScptXxx = new BLonScptType(SCPT_XXX);
  /** BLonScptType constant for ScptActFbDly. */
  public static final BLonScptType ScptActFbDly = new BLonScptType(SCPT_ACT_FB_DLY);
  /** BLonScptType constant for ScptAlrmClrT1. */
  public static final BLonScptType ScptAlrmClrT1 = new BLonScptType(SCPT_ALRM_CLR_T_1);
  /** BLonScptType constant for ScptAlrmClrT2. */
  public static final BLonScptType ScptAlrmClrT2 = new BLonScptType(SCPT_ALRM_CLR_T_2);
  /** BLonScptType constant for ScptAlrmIhbT. */
  public static final BLonScptType ScptAlrmIhbT = new BLonScptType(SCPT_ALRM_IHB_T);
  /** BLonScptType constant for ScptAlrmSetT1. */
  public static final BLonScptType ScptAlrmSetT1 = new BLonScptType(SCPT_ALRM_SET_T_1);
  /** BLonScptType constant for ScptAlrmSetT2. */
  public static final BLonScptType ScptAlrmSetT2 = new BLonScptType(SCPT_ALRM_SET_T_2);
  /** BLonScptType constant for ScptDefOutput. */
  public static final BLonScptType ScptDefOutput = new BLonScptType(SCPT_DEF_OUTPUT);
  /** BLonScptType constant for ScptDriveT. */
  public static final BLonScptType ScptDriveT = new BLonScptType(SCPT_DRIVE_T);
  /** BLonScptType constant for ScptHighLimit1. */
  public static final BLonScptType ScptHighLimit1 = new BLonScptType(SCPT_HIGH_LIMIT_1);
  /** BLonScptType constant for ScptHighLimit2. */
  public static final BLonScptType ScptHighLimit2 = new BLonScptType(SCPT_HIGH_LIMIT_2);
  /** BLonScptType constant for ScptHystHigh1. */
  public static final BLonScptType ScptHystHigh1 = new BLonScptType(SCPT_HYST_HIGH_1);
  /** BLonScptType constant for ScptHystHigh2. */
  public static final BLonScptType ScptHystHigh2 = new BLonScptType(SCPT_HYST_HIGH_2);
  /** BLonScptType constant for ScptHystLow1. */
  public static final BLonScptType ScptHystLow1 = new BLonScptType(SCPT_HYST_LOW_1);
  /** BLonScptType constant for ScptHystLow2. */
  public static final BLonScptType ScptHystLow2 = new BLonScptType(SCPT_HYST_LOW_2);
  /** BLonScptType constant for ScptInFbDly. */
  public static final BLonScptType ScptInFbDly = new BLonScptType(SCPT_IN_FB_DLY);
  /** BLonScptType constant for ScptInvrtOut. */
  public static final BLonScptType ScptInvrtOut = new BLonScptType(SCPT_INVRT_OUT);
  /** BLonScptType constant for ScptLocation. */
  public static final BLonScptType ScptLocation = new BLonScptType(SCPT_LOCATION);
  /** BLonScptType constant for ScptLowLimit1. */
  public static final BLonScptType ScptLowLimit1 = new BLonScptType(SCPT_LOW_LIMIT_1);
  /** BLonScptType constant for ScptLowLimit2. */
  public static final BLonScptType ScptLowLimit2 = new BLonScptType(SCPT_LOW_LIMIT_2);
  /** BLonScptType constant for ScptMaxRnge. */
  public static final BLonScptType ScptMaxRnge = new BLonScptType(SCPT_MAX_RNGE);
  /** BLonScptType constant for ScptMaxRcvT. */
  public static final BLonScptType ScptMaxRcvT = new BLonScptType(SCPT_MAX_RCV_T);
  /** BLonScptType constant for ScptMaxSndT. */
  public static final BLonScptType ScptMaxSndT = new BLonScptType(SCPT_MAX_SND_T);
  /** BLonScptType constant for ScptMinRnge. */
  public static final BLonScptType ScptMinRnge = new BLonScptType(SCPT_MIN_RNGE);
  /** BLonScptType constant for ScptMinSndT. */
  public static final BLonScptType ScptMinSndT = new BLonScptType(SCPT_MIN_SND_T);
  /** BLonScptType constant for ScptNwrkCnfg. */
  public static final BLonScptType ScptNwrkCnfg = new BLonScptType(SCPT_NWRK_CNFG);
  /** BLonScptType constant for ScptOffset. */
  public static final BLonScptType ScptOffset = new BLonScptType(SCPT_OFFSET);
  /** BLonScptType constant for ScptSndDelta. */
  public static final BLonScptType ScptSndDelta = new BLonScptType(SCPT_SND_DELTA);
  /** BLonScptType constant for ScptTrnsTblX. */
  public static final BLonScptType ScptTrnsTblX = new BLonScptType(SCPT_TRNS_TBL_X);
  /** BLonScptType constant for ScptTrnsTblY. */
  public static final BLonScptType ScptTrnsTblY = new BLonScptType(SCPT_TRNS_TBL_Y);
  /** BLonScptType constant for ScptOffDely. */
  public static final BLonScptType ScptOffDely = new BLonScptType(SCPT_OFF_DELY);
  /** BLonScptType constant for ScptGain. */
  public static final BLonScptType ScptGain = new BLonScptType(SCPT_GAIN);
  /** BLonScptType constant for ScptOvrBehave. */
  public static final BLonScptType ScptOvrBehave = new BLonScptType(SCPT_OVR_BEHAVE);
  /** BLonScptType constant for ScptOvrValue. */
  public static final BLonScptType ScptOvrValue = new BLonScptType(SCPT_OVR_VALUE);
  /** BLonScptType constant for ScptBypassTime. */
  public static final BLonScptType ScptBypassTime = new BLonScptType(SCPT_BYPASS_TIME);
  /** BLonScptType constant for ScptManOvrTime. */
  public static final BLonScptType ScptManOvrTime = new BLonScptType(SCPT_MAN_OVR_TIME);
  /** BLonScptType constant for ScptHumSetpt. */
  public static final BLonScptType ScptHumSetpt = new BLonScptType(SCPT_HUM_SETPT);
  /** BLonScptType constant for ScptMaxFlowHeat. */
  public static final BLonScptType ScptMaxFlowHeat = new BLonScptType(SCPT_MAX_FLOW_HEAT);
  /** BLonScptType constant for ScptFireInitType. */
  public static final BLonScptType ScptFireInitType = new BLonScptType(SCPT_FIRE_INIT_TYPE);
  /** BLonScptType constant for ScptSmokeNomSens. */
  public static final BLonScptType ScptSmokeNomSens = new BLonScptType(SCPT_SMOKE_NOM_SENS);
  /** BLonScptType constant for ScptSmokeDayAlrmLim. */
  public static final BLonScptType ScptSmokeDayAlrmLim = new BLonScptType(SCPT_SMOKE_DAY_ALRM_LIM);
  /** BLonScptType constant for ScptActuatorType. */
  public static final BLonScptType ScptActuatorType = new BLonScptType(SCPT_ACTUATOR_TYPE);
  /** BLonScptType constant for ScptLimitCO2. */
  public static final BLonScptType ScptLimitCO2 = new BLonScptType(SCPT_LIMIT_CO_2);
  /** BLonScptType constant for ScptMinDeltaAngl. */
  public static final BLonScptType ScptMinDeltaAngl = new BLonScptType(SCPT_MIN_DELTA_ANGL);
  /** BLonScptType constant for ScptDirection. */
  public static final BLonScptType ScptDirection = new BLonScptType(SCPT_DIRECTION);
  /** BLonScptType constant for ScptDriveTime. */
  public static final BLonScptType ScptDriveTime = new BLonScptType(SCPT_DRIVE_TIME);
  /** BLonScptType constant for ScptDuctArea. */
  public static final BLonScptType ScptDuctArea = new BLonScptType(SCPT_DUCT_AREA);
  /** BLonScptType constant for ScptMinDeltaFlow. */
  public static final BLonScptType ScptMinDeltaFlow = new BLonScptType(SCPT_MIN_DELTA_FLOW);
  /** BLonScptType constant for ScptMaxRcvTime. */
  public static final BLonScptType ScptMaxRcvTime = new BLonScptType(SCPT_MAX_RCV_TIME);
  /** BLonScptType constant for ScptMaxSendTime. */
  public static final BLonScptType ScptMaxSendTime = new BLonScptType(SCPT_MAX_SEND_TIME);
  /** BLonScptType constant for ScptMaxSetpoint. */
  public static final BLonScptType ScptMaxSetpoint = new BLonScptType(SCPT_MAX_SETPOINT);
  /** BLonScptType constant for ScptMaxFlow. */
  public static final BLonScptType ScptMaxFlow = new BLonScptType(SCPT_MAX_FLOW);
  /** BLonScptType constant for ScptMinSendTime. */
  public static final BLonScptType ScptMinSendTime = new BLonScptType(SCPT_MIN_SEND_TIME);
  /** BLonScptType constant for ScptMinSetpoint. */
  public static final BLonScptType ScptMinSetpoint = new BLonScptType(SCPT_MIN_SETPOINT);
  /** BLonScptType constant for ScptMinFlow. */
  public static final BLonScptType ScptMinFlow = new BLonScptType(SCPT_MIN_FLOW);
  /** BLonScptType constant for ScptMinFlowHeat. */
  public static final BLonScptType ScptMinFlowHeat = new BLonScptType(SCPT_MIN_FLOW_HEAT);
  /** BLonScptType constant for ScptMinFlowStby. */
  public static final BLonScptType ScptMinFlowStby = new BLonScptType(SCPT_MIN_FLOW_STBY);
  /** BLonScptType constant for ScptNomAirFlow. */
  public static final BLonScptType ScptNomAirFlow = new BLonScptType(SCPT_NOM_AIR_FLOW);
  /** BLonScptType constant for ScptNomAngle. */
  public static final BLonScptType ScptNomAngle = new BLonScptType(SCPT_NOM_ANGLE);
  /** BLonScptType constant for ScptNumValves. */
  public static final BLonScptType ScptNumValves = new BLonScptType(SCPT_NUM_VALVES);
  /** BLonScptType constant for ScptSetPnts. */
  public static final BLonScptType ScptSetPnts = new BLonScptType(SCPT_SET_PNTS);
  /** BLonScptType constant for ScptOemType. */
  public static final BLonScptType ScptOemType = new BLonScptType(SCPT_OEM_TYPE);
  /** BLonScptType constant for ScptMinDeltaRH. */
  public static final BLonScptType ScptMinDeltaRH = new BLonScptType(SCPT_MIN_DELTA_RH);
  /** BLonScptType constant for ScptMinDeltaCO2. */
  public static final BLonScptType ScptMinDeltaCO2 = new BLonScptType(SCPT_MIN_DELTA_CO_2);
  /** BLonScptType constant for ScptMinDeltaTemp. */
  public static final BLonScptType ScptMinDeltaTemp = new BLonScptType(SCPT_MIN_DELTA_TEMP);
  /** BLonScptType constant for ScptSensConstTmp. */
  public static final BLonScptType ScptSensConstTmp = new BLonScptType(SCPT_SENS_CONST_TMP);
  /** BLonScptType constant for ScptGainVAV. */
  public static final BLonScptType ScptGainVAV = new BLonScptType(SCPT_GAIN_VAV);
  /** BLonScptType constant for ScptSensConstVAV. */
  public static final BLonScptType ScptSensConstVAV = new BLonScptType(SCPT_SENS_CONST_VAV);
  /** BLonScptType constant for ScptOffsetCO2. */
  public static final BLonScptType ScptOffsetCO2 = new BLonScptType(SCPT_OFFSET_CO_2);
  /** BLonScptType constant for ScptOffsetRH. */
  public static final BLonScptType ScptOffsetRH = new BLonScptType(SCPT_OFFSET_RH);
  /** BLonScptType constant for ScptOffsetTemp. */
  public static final BLonScptType ScptOffsetTemp = new BLonScptType(SCPT_OFFSET_TEMP);
  /** BLonScptType constant for ScptDefltBehave. */
  public static final BLonScptType ScptDefltBehave = new BLonScptType(SCPT_DEFLT_BEHAVE);
  /** BLonScptType constant for ScptPwrUpDelay. */
  public static final BLonScptType ScptPwrUpDelay = new BLonScptType(SCPT_PWR_UP_DELAY);
  /** BLonScptType constant for ScptPwrUpState. */
  public static final BLonScptType ScptPwrUpState = new BLonScptType(SCPT_PWR_UP_STATE);
  /** BLonScptType constant for ScptHvacMode. */
  public static final BLonScptType ScptHvacMode = new BLonScptType(SCPT_HVAC_MODE);
  /** BLonScptType constant for ScptCoolSetpt. */
  public static final BLonScptType ScptCoolSetpt = new BLonScptType(SCPT_COOL_SETPT);
  /** BLonScptType constant for ScptCoolLowerSP. */
  public static final BLonScptType ScptCoolLowerSP = new BLonScptType(SCPT_COOL_LOWER_SP);
  /** BLonScptType constant for ScptCoolUpperSP. */
  public static final BLonScptType ScptCoolUpperSP = new BLonScptType(SCPT_COOL_UPPER_SP);
  /** BLonScptType constant for ScptHeatSetpt. */
  public static final BLonScptType ScptHeatSetpt = new BLonScptType(SCPT_HEAT_SETPT);
  /** BLonScptType constant for ScptHeatLowerSP. */
  public static final BLonScptType ScptHeatLowerSP = new BLonScptType(SCPT_HEAT_LOWER_SP);
  /** BLonScptType constant for ScptHeatUpperSP. */
  public static final BLonScptType ScptHeatUpperSP = new BLonScptType(SCPT_HEAT_UPPER_SP);
  /** BLonScptType constant for ScptLimitChlrCap. */
  public static final BLonScptType ScptLimitChlrCap = new BLonScptType(SCPT_LIMIT_CHLR_CAP);
  /** BLonScptType constant for ScptLuxSetpoint. */
  public static final BLonScptType ScptLuxSetpoint = new BLonScptType(SCPT_LUX_SETPOINT);
  /** BLonScptType constant for ScptStep. */
  public static final BLonScptType ScptStep = new BLonScptType(SCPT_STEP);
  /** BLonScptType constant for ScptOnOffHysteresis. */
  public static final BLonScptType ScptOnOffHysteresis = new BLonScptType(SCPT_ON_OFF_HYSTERESIS);
  /** BLonScptType constant for ScptClOffDelay. */
  public static final BLonScptType ScptClOffDelay = new BLonScptType(SCPT_CL_OFF_DELAY);
  /** BLonScptType constant for ScptClOnDelay. */
  public static final BLonScptType ScptClOnDelay = new BLonScptType(SCPT_CL_ON_DELAY);
  /** BLonScptType constant for ScptPowerupState. */
  public static final BLonScptType ScptPowerupState = new BLonScptType(SCPT_POWERUP_STATE);
  /** BLonScptType constant for ScptMinDeltaLevel. */
  public static final BLonScptType ScptMinDeltaLevel = new BLonScptType(SCPT_MIN_DELTA_LEVEL);
  /** BLonScptType constant for ScptReflection. */
  public static final BLonScptType ScptReflection = new BLonScptType(SCPT_REFLECTION);
  /** BLonScptType constant for ScptFieldCalib. */
  public static final BLonScptType ScptFieldCalib = new BLonScptType(SCPT_FIELD_CALIB);
  /** BLonScptType constant for ScptHoldTime. */
  public static final BLonScptType ScptHoldTime = new BLonScptType(SCPT_HOLD_TIME);
  /** BLonScptType constant for ScptStepValue. */
  public static final BLonScptType ScptStepValue = new BLonScptType(SCPT_STEP_VALUE);
  /** BLonScptType constant for ScptMaxOut. */
  public static final BLonScptType ScptMaxOut = new BLonScptType(SCPT_MAX_OUT);
  /** BLonScptType constant for ScptSceneNmbr. */
  public static final BLonScptType ScptSceneNmbr = new BLonScptType(SCPT_SCENE_NMBR);
  /** BLonScptType constant for ScptFadeTime. */
  public static final BLonScptType ScptFadeTime = new BLonScptType(SCPT_FADE_TIME);
  /** BLonScptType constant for ScptDelayTime. */
  public static final BLonScptType ScptDelayTime = new BLonScptType(SCPT_DELAY_TIME);
  /** BLonScptType constant for ScptMasterSlave. */
  public static final BLonScptType ScptMasterSlave = new BLonScptType(SCPT_MASTER_SLAVE);
  /** BLonScptType constant for ScptUpdateRate. */
  public static final BLonScptType ScptUpdateRate = new BLonScptType(SCPT_UPDATE_RATE);
  /** BLonScptType constant for ScptSummerTime. */
  public static final BLonScptType ScptSummerTime = new BLonScptType(SCPT_SUMMER_TIME);
  /** BLonScptType constant for ScptWinterTime. */
  public static final BLonScptType ScptWinterTime = new BLonScptType(SCPT_WINTER_TIME);
  /** BLonScptType constant for ScptManualAllowed. */
  public static final BLonScptType ScptManualAllowed = new BLonScptType(SCPT_MANUAL_ALLOWED);
  /** BLonScptType constant for ScptDefWeekMask. */
  public static final BLonScptType ScptDefWeekMask = new BLonScptType(SCPT_DEF_WEEK_MASK);
  /** BLonScptType constant for ScptDayDateIndex. */
  public static final BLonScptType ScptDayDateIndex = new BLonScptType(SCPT_DAY_DATE_INDEX);
  /** BLonScptType constant for ScptTimeEvent. */
  public static final BLonScptType ScptTimeEvent = new BLonScptType(SCPT_TIME_EVENT);
  /** BLonScptType constant for ScptModeHrtBt. */
  public static final BLonScptType ScptModeHrtBt = new BLonScptType(SCPT_MODE_HRT_BT);
  /** BLonScptType constant for ScptDefrostMode. */
  public static final BLonScptType ScptDefrostMode = new BLonScptType(SCPT_DEFROST_MODE);
  /** BLonScptType constant for ScptMaxDefrstTime. */
  public static final BLonScptType ScptMaxDefrstTime = new BLonScptType(SCPT_MAX_DEFRST_TIME);
  /** BLonScptType constant for ScptDrainDelay. */
  public static final BLonScptType ScptDrainDelay = new BLonScptType(SCPT_DRAIN_DELAY);
  /** BLonScptType constant for ScptInjDelay. */
  public static final BLonScptType ScptInjDelay = new BLonScptType(SCPT_INJ_DELAY);
  /** BLonScptType constant for ScptMaxDefrstTemp. */
  public static final BLonScptType ScptMaxDefrstTemp = new BLonScptType(SCPT_MAX_DEFRST_TEMP);
  /** BLonScptType constant for ScptStrtupDelay. */
  public static final BLonScptType ScptStrtupDelay = new BLonScptType(SCPT_STRTUP_DELAY);
  /** BLonScptType constant for ScptTermTimeTemp. */
  public static final BLonScptType ScptTermTimeTemp = new BLonScptType(SCPT_TERM_TIME_TEMP);
  /** BLonScptType constant for ScptPumpDownDelay. */
  public static final BLonScptType ScptPumpDownDelay = new BLonScptType(SCPT_PUMP_DOWN_DELAY);
  /** BLonScptType constant for ScptSuperHtRefInit. */
  public static final BLonScptType ScptSuperHtRefInit = new BLonScptType(SCPT_SUPER_HT_REF_INIT);
  /** BLonScptType constant for ScptStrtupOpen. */
  public static final BLonScptType ScptStrtupOpen = new BLonScptType(SCPT_STRTUP_OPEN);
  /** BLonScptType constant for ScptSuperHtRefMin. */
  public static final BLonScptType ScptSuperHtRefMin = new BLonScptType(SCPT_SUPER_HT_REF_MIN);
  /** BLonScptType constant for ScptRefrigGlide. */
  public static final BLonScptType ScptRefrigGlide = new BLonScptType(SCPT_REFRIG_GLIDE);
  /** BLonScptType constant for ScptSuperHtRefMax. */
  public static final BLonScptType ScptSuperHtRefMax = new BLonScptType(SCPT_SUPER_HT_REF_MAX);
  /** BLonScptType constant for ScptRefrigType. */
  public static final BLonScptType ScptRefrigType = new BLonScptType(SCPT_REFRIG_TYPE);
  /** BLonScptType constant for ScptThermMode. */
  public static final BLonScptType ScptThermMode = new BLonScptType(SCPT_THERM_MODE);
  /** BLonScptType constant for ScptDayNightCntrl. */
  public static final BLonScptType ScptDayNightCntrl = new BLonScptType(SCPT_DAY_NIGHT_CNTRL);
  /** BLonScptType constant for ScptDiffNight. */
  public static final BLonScptType ScptDiffNight = new BLonScptType(SCPT_DIFF_NIGHT);
  /** BLonScptType constant for ScptHighLimTemp. */
  public static final BLonScptType ScptHighLimTemp = new BLonScptType(SCPT_HIGH_LIM_TEMP);
  /** BLonScptType constant for ScptHighLimDly. */
  public static final BLonScptType ScptHighLimDly = new BLonScptType(SCPT_HIGH_LIM_DLY);
  /** BLonScptType constant for ScptCutOutValue. */
  public static final BLonScptType ScptCutOutValue = new BLonScptType(SCPT_CUT_OUT_VALUE);
  /** BLonScptType constant for ScptAirTemp1Day. */
  public static final BLonScptType ScptAirTemp1Day = new BLonScptType(SCPT_AIR_TEMP_1_DAY);
  /** BLonScptType constant for ScptSmokeNightAlrmLim. */
  public static final BLonScptType ScptSmokeNightAlrmLim = new BLonScptType(SCPT_SMOKE_NIGHT_ALRM_LIM);
  /** BLonScptType constant for ScptLowLimTemp. */
  public static final BLonScptType ScptLowLimTemp = new BLonScptType(SCPT_LOW_LIM_TEMP);
  /** BLonScptType constant for ScptLowLimDly. */
  public static final BLonScptType ScptLowLimDly = new BLonScptType(SCPT_LOW_LIM_DLY);
  /** BLonScptType constant for ScptDiffValue. */
  public static final BLonScptType ScptDiffValue = new BLonScptType(SCPT_DIFF_VALUE);
  /** BLonScptType constant for ScptAirTemp1Night. */
  public static final BLonScptType ScptAirTemp1Night = new BLonScptType(SCPT_AIR_TEMP_1_NIGHT);
  /** BLonScptType constant for ScptAirTemp1Alrm. */
  public static final BLonScptType ScptAirTemp1Alrm = new BLonScptType(SCPT_AIR_TEMP_1_ALRM);
  /** BLonScptType constant for ScptHighLimDefrDly. */
  public static final BLonScptType ScptHighLimDefrDly = new BLonScptType(SCPT_HIGH_LIM_DEFR_DLY);
  /** BLonScptType constant for ScptDeltaNight. */
  public static final BLonScptType ScptDeltaNight = new BLonScptType(SCPT_DELTA_NIGHT);
  /** BLonScptType constant for ScptRunHrInit. */
  public static final BLonScptType ScptRunHrInit = new BLonScptType(SCPT_RUN_HR_INIT);
  /** BLonScptType constant for ScptRunHrAlarm. */
  public static final BLonScptType ScptRunHrAlarm = new BLonScptType(SCPT_RUN_HR_ALARM);
  /** BLonScptType constant for ScptEnergyCntInit. */
  public static final BLonScptType ScptEnergyCntInit = new BLonScptType(SCPT_ENERGY_CNT_INIT);
  /** BLonScptType constant for ScptSmokeDayPreAlrmLim. */
  public static final BLonScptType ScptSmokeDayPreAlrmLim = new BLonScptType(SCPT_SMOKE_DAY_PRE_ALRM_LIM);
  /** BLonScptType constant for ScptDebounce. */
  public static final BLonScptType ScptDebounce = new BLonScptType(SCPT_DEBOUNCE);
  /** BLonScptType constant for ScptSmokeNightPreAlrmLim. */
  public static final BLonScptType ScptSmokeNightPreAlrmLim = new BLonScptType(SCPT_SMOKE_NIGHT_PRE_ALRM_LIM);
  /** BLonScptType constant for ScptZoneNum. */
  public static final BLonScptType ScptZoneNum = new BLonScptType(SCPT_ZONE_NUM);
  /** BLonScptType constant for ScptThermAlrmROR. */
  public static final BLonScptType ScptThermAlrmROR = new BLonScptType(SCPT_THERM_ALRM_ROR);
  /** BLonScptType constant for ScptVisOutput. */
  public static final BLonScptType ScptVisOutput = new BLonScptType(SCPT_VIS_OUTPUT);
  /** BLonScptType constant for ScptAudOutput. */
  public static final BLonScptType ScptAudOutput = new BLonScptType(SCPT_AUD_OUTPUT);
  /** BLonScptType constant for ScptFlashFreq. */
  public static final BLonScptType ScptFlashFreq = new BLonScptType(SCPT_FLASH_FREQ);
  /** BLonScptType constant for ScptInstallDate. */
  public static final BLonScptType ScptInstallDate = new BLonScptType(SCPT_INSTALL_DATE);
  /** BLonScptType constant for ScptMaintDate. */
  public static final BLonScptType ScptMaintDate = new BLonScptType(SCPT_MAINT_DATE);
  /** BLonScptType constant for ScptManfDate. */
  public static final BLonScptType ScptManfDate = new BLonScptType(SCPT_MANF_DATE);
  /** BLonScptType constant for ScptFireTxt1. */
  public static final BLonScptType ScptFireTxt1 = new BLonScptType(SCPT_FIRE_TXT_1);
  /** BLonScptType constant for ScptFireTxt2. */
  public static final BLonScptType ScptFireTxt2 = new BLonScptType(SCPT_FIRE_TXT_2);
  /** BLonScptType constant for ScptFireTxt3. */
  public static final BLonScptType ScptFireTxt3 = new BLonScptType(SCPT_FIRE_TXT_3);
  /** BLonScptType constant for ScptThermThreshold. */
  public static final BLonScptType ScptThermThreshold = new BLonScptType(SCPT_THERM_THRESHOLD);
  /** BLonScptType constant for ScptFireIndicate. */
  public static final BLonScptType ScptFireIndicate = new BLonScptType(SCPT_FIRE_INDICATE);
  /** BLonScptType constant for ScptTimeZone. */
  public static final BLonScptType ScptTimeZone = new BLonScptType(SCPT_TIME_ZONE);
  /** BLonScptType constant for ScptPrimeVal. */
  public static final BLonScptType ScptPrimeVal = new BLonScptType(SCPT_PRIME_VAL);
  /** BLonScptType constant for ScptSecondVal. */
  public static final BLonScptType ScptSecondVal = new BLonScptType(SCPT_SECOND_VAL);
  /** BLonScptType constant for ScptSceneOffset. */
  public static final BLonScptType ScptSceneOffset = new BLonScptType(SCPT_SCENE_OFFSET);
  /** BLonScptType constant for ScptNomRPM. */
  public static final BLonScptType ScptNomRPM = new BLonScptType(SCPT_NOM_RPM);
  /** BLonScptType constant for ScptNomFreq. */
  public static final BLonScptType ScptNomFreq = new BLonScptType(SCPT_NOM_FREQ);
  /** BLonScptType constant for ScptRampUpTm. */
  public static final BLonScptType ScptRampUpTm = new BLonScptType(SCPT_RAMP_UP_TM);
  /** BLonScptType constant for ScptRampDownTm. */
  public static final BLonScptType ScptRampDownTm = new BLonScptType(SCPT_RAMP_DOWN_TM);
  /** BLonScptType constant for ScptDefScale. */
  public static final BLonScptType ScptDefScale = new BLonScptType(SCPT_DEF_SCALE);
  /** BLonScptType constant for ScptRegName. */
  public static final BLonScptType ScptRegName = new BLonScptType(SCPT_REG_NAME);
  /** BLonScptType constant for ScptBaseValue. */
  public static final BLonScptType ScptBaseValue = new BLonScptType(SCPT_BASE_VALUE);
  /** BLonScptType constant for ScptDevMajVer. */
  public static final BLonScptType ScptDevMajVer = new BLonScptType(SCPT_DEV_MAJ_VER);
  /** BLonScptType constant for ScptDevMinVer. */
  public static final BLonScptType ScptDevMinVer = new BLonScptType(SCPT_DEV_MIN_VER);
  /** BLonScptType constant for ScptObjMajVer. */
  public static final BLonScptType ScptObjMajVer = new BLonScptType(SCPT_OBJ_MAJ_VER);
  /** BLonScptType constant for ScptObjMinVer. */
  public static final BLonScptType ScptObjMinVer = new BLonScptType(SCPT_OBJ_MIN_VER);
  /** BLonScptType constant for ScptHvacType. */
  public static final BLonScptType ScptHvacType = new BLonScptType(SCPT_HVAC_TYPE);
  /** BLonScptType constant for ScptTimeout. */
  public static final BLonScptType ScptTimeout = new BLonScptType(SCPT_TIMEOUT);
  /** BLonScptType constant for ScptControlPriority. */
  public static final BLonScptType ScptControlPriority = new BLonScptType(SCPT_CONTROL_PRIORITY);
  /** BLonScptType constant for ScptDeviceGroupID. */
  public static final BLonScptType ScptDeviceGroupID = new BLonScptType(SCPT_DEVICE_GROUP_ID);
  /** BLonScptType constant for ScptMaxPrivacyZones. */
  public static final BLonScptType ScptMaxPrivacyZones = new BLonScptType(SCPT_MAX_PRIVACY_ZONES);
  /** BLonScptType constant for ScptMaxCameraPrepositions. */
  public static final BLonScptType ScptMaxCameraPrepositions = new BLonScptType(SCPT_MAX_CAMERA_PREPOSITIONS);
  /** BLonScptType constant for ScptDefaultPanTiltZoomSpeeds. */
  public static final BLonScptType ScptDefaultPanTiltZoomSpeeds = new BLonScptType(SCPT_DEFAULT_PAN_TILT_ZOOM_SPEEDS);
  /** BLonScptType constant for ScptDefaultAutoPanSpeed. */
  public static final BLonScptType ScptDefaultAutoPanSpeed = new BLonScptType(SCPT_DEFAULT_AUTO_PAN_SPEED);
  /** BLonScptType constant for ScptAutoAnswer. */
  public static final BLonScptType ScptAutoAnswer = new BLonScptType(SCPT_AUTO_ANSWER);
  /** BLonScptType constant for ScptDialString. */
  public static final BLonScptType ScptDialString = new BLonScptType(SCPT_DIAL_STRING);
  /** BLonScptType constant for ScptSerialNumber. */
  public static final BLonScptType ScptSerialNumber = new BLonScptType(SCPT_SERIAL_NUMBER);
  /** BLonScptType constant for ScptNormalRotationalSpeed. */
  public static final BLonScptType ScptNormalRotationalSpeed = new BLonScptType(SCPT_NORMAL_ROTATIONAL_SPEED);
  /** BLonScptType constant for ScptStandbyRotationalSpeed. */
  public static final BLonScptType ScptStandbyRotationalSpeed = new BLonScptType(SCPT_STANDBY_ROTATIONAL_SPEED);
  /** BLonScptType constant for ScptPartNumber. */
  public static final BLonScptType ScptPartNumber = new BLonScptType(SCPT_PART_NUMBER);
  /** BLonScptType constant for ScptDischargeAirCoolingSetpoint. */
  public static final BLonScptType ScptDischargeAirCoolingSetpoint = new BLonScptType(SCPT_DISCHARGE_AIR_COOLING_SETPOINT);
  /** BLonScptType constant for ScptDischargeAirHeatingSetpoint. */
  public static final BLonScptType ScptDischargeAirHeatingSetpoint = new BLonScptType(SCPT_DISCHARGE_AIR_HEATING_SETPOINT);
  /** BLonScptType constant for ScptMaxSupplyFanCapacity. */
  public static final BLonScptType ScptMaxSupplyFanCapacity = new BLonScptType(SCPT_MAX_SUPPLY_FAN_CAPACITY);
  /** BLonScptType constant for ScptMinSupplyFanCapacity. */
  public static final BLonScptType ScptMinSupplyFanCapacity = new BLonScptType(SCPT_MIN_SUPPLY_FAN_CAPACITY);
  /** BLonScptType constant for ScptMaxReturnExhaustFanCapacity. */
  public static final BLonScptType ScptMaxReturnExhaustFanCapacity = new BLonScptType(SCPT_MAX_RETURN_EXHAUST_FAN_CAPACITY);
  /** BLonScptType constant for ScptMinReturnExhaustFanCapacity. */
  public static final BLonScptType ScptMinReturnExhaustFanCapacity = new BLonScptType(SCPT_MIN_RETURN_EXHAUST_FAN_CAPACITY);
  /** BLonScptType constant for ScptDuctStaticPressureSetpoint. */
  public static final BLonScptType ScptDuctStaticPressureSetpoint = new BLonScptType(SCPT_DUCT_STATIC_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptMaxDuctStaticPressureSetpoint. */
  public static final BLonScptType ScptMaxDuctStaticPressureSetpoint = new BLonScptType(SCPT_MAX_DUCT_STATIC_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptMinDuctStaticPressureSetpoint. */
  public static final BLonScptType ScptMinDuctStaticPressureSetpoint = new BLonScptType(SCPT_MIN_DUCT_STATIC_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptDuctStaticPressureLimit. */
  public static final BLonScptType ScptDuctStaticPressureLimit = new BLonScptType(SCPT_DUCT_STATIC_PRESSURE_LIMIT);
  /** BLonScptType constant for ScptBuildingStaticPressureSetpoint. */
  public static final BLonScptType ScptBuildingStaticPressureSetpoint = new BLonScptType(SCPT_BUILDING_STATIC_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptReturnFanStaticPressureSetpoint. */
  public static final BLonScptType ScptReturnFanStaticPressureSetpoint = new BLonScptType(SCPT_RETURN_FAN_STATIC_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptFanDifferentialSetpoint. */
  public static final BLonScptType ScptFanDifferentialSetpoint = new BLonScptType(SCPT_FAN_DIFFERENTIAL_SETPOINT);
  /** BLonScptType constant for ScptMixedAirLowLimitSetpoint. */
  public static final BLonScptType ScptMixedAirLowLimitSetpoint = new BLonScptType(SCPT_MIXED_AIR_LOW_LIMIT_SETPOINT);
  /** BLonScptType constant for ScptMixedAirTempSetpoint. */
  public static final BLonScptType ScptMixedAirTempSetpoint = new BLonScptType(SCPT_MIXED_AIR_TEMP_SETPOINT);
  /** BLonScptType constant for ScptMinOutdoorAirFlowSetpoint. */
  public static final BLonScptType ScptMinOutdoorAirFlowSetpoint = new BLonScptType(SCPT_MIN_OUTDOOR_AIR_FLOW_SETPOINT);
  /** BLonScptType constant for ScptOutdoorAirTempSetpoint. */
  public static final BLonScptType ScptOutdoorAirTempSetpoint = new BLonScptType(SCPT_OUTDOOR_AIR_TEMP_SETPOINT);
  /** BLonScptType constant for ScptOutdoorAirEnthalpySetpoint. */
  public static final BLonScptType ScptOutdoorAirEnthalpySetpoint = new BLonScptType(SCPT_OUTDOOR_AIR_ENTHALPY_SETPOINT);
  /** BLonScptType constant for ScptDiffTempSetpoint. */
  public static final BLonScptType ScptDiffTempSetpoint = new BLonScptType(SCPT_DIFF_TEMP_SETPOINT);
  /** BLonScptType constant for ScptExhaustEnablePosition. */
  public static final BLonScptType ScptExhaustEnablePosition = new BLonScptType(SCPT_EXHAUST_ENABLE_POSITION);
  /** BLonScptType constant for ScptSpaceHumSetpoint. */
  public static final BLonScptType ScptSpaceHumSetpoint = new BLonScptType(SCPT_SPACE_HUM_SETPOINT);
  /** BLonScptType constant for ScptDischargeAirDewpointSetpoint. */
  public static final BLonScptType ScptDischargeAirDewpointSetpoint = new BLonScptType(SCPT_DISCHARGE_AIR_DEWPOINT_SETPOINT);
  /** BLonScptType constant for ScptMaxDischargeAirCoolingSetpoint. */
  public static final BLonScptType ScptMaxDischargeAirCoolingSetpoint = new BLonScptType(SCPT_MAX_DISCHARGE_AIR_COOLING_SETPOINT);
  /** BLonScptType constant for ScptMinDischargeAirCoolingSetpoint. */
  public static final BLonScptType ScptMinDischargeAirCoolingSetpoint = new BLonScptType(SCPT_MIN_DISCHARGE_AIR_COOLING_SETPOINT);
  /** BLonScptType constant for ScptMaxDischargeAirHeatingSetpoint. */
  public static final BLonScptType ScptMaxDischargeAirHeatingSetpoint = new BLonScptType(SCPT_MAX_DISCHARGE_AIR_HEATING_SETPOINT);
  /** BLonScptType constant for ScptMinDischargeAirHeatingSetpoint. */
  public static final BLonScptType ScptMinDischargeAirHeatingSetpoint = new BLonScptType(SCPT_MIN_DISCHARGE_AIR_HEATING_SETPOINT);
  /** BLonScptType constant for ScptCoolingLockout. */
  public static final BLonScptType ScptCoolingLockout = new BLonScptType(SCPT_COOLING_LOCKOUT);
  /** BLonScptType constant for ScptHeatingLockout. */
  public static final BLonScptType ScptHeatingLockout = new BLonScptType(SCPT_HEATING_LOCKOUT);
  /** BLonScptType constant for ScptCoolingResetEnable. */
  public static final BLonScptType ScptCoolingResetEnable = new BLonScptType(SCPT_COOLING_RESET_ENABLE);
  /** BLonScptType constant for ScptHeatingResetEnable. */
  public static final BLonScptType ScptHeatingResetEnable = new BLonScptType(SCPT_HEATING_RESET_ENABLE);
  /** BLonScptType constant for ScptSetpoint. */
  public static final BLonScptType ScptSetpoint = new BLonScptType(SCPT_SETPOINT);
  /** BLonScptType constant for ScptTemperatureHysteresis. */
  public static final BLonScptType ScptTemperatureHysteresis = new BLonScptType(SCPT_TEMPERATURE_HYSTERESIS);
  /** BLonScptType constant for ScptControlTemperatureWeighting. */
  public static final BLonScptType ScptControlTemperatureWeighting = new BLonScptType(SCPT_CONTROL_TEMPERATURE_WEIGHTING);
  /** BLonScptType constant for ScptPwmPeriod. */
  public static final BLonScptType ScptPwmPeriod = new BLonScptType(SCPT_PWM_PERIOD);
  /** BLonScptType constant for ScptDefrostInternalSchedule. */
  public static final BLonScptType ScptDefrostInternalSchedule = new BLonScptType(SCPT_DEFROST_INTERNAL_SCHEDULE);
  /** BLonScptType constant for ScptDefrostStart. */
  public static final BLonScptType ScptDefrostStart = new BLonScptType(SCPT_DEFROST_START);
  /** BLonScptType constant for ScptDefrostCycles. */
  public static final BLonScptType ScptDefrostCycles = new BLonScptType(SCPT_DEFROST_CYCLES);
  /** BLonScptType constant for ScptMinDefrostTime. */
  public static final BLonScptType ScptMinDefrostTime = new BLonScptType(SCPT_MIN_DEFROST_TIME);
  /** BLonScptType constant for ScptMaxDefrostTime. */
  public static final BLonScptType ScptMaxDefrostTime = new BLonScptType(SCPT_MAX_DEFROST_TIME);
  /** BLonScptType constant for ScptDefrostFanDelay. */
  public static final BLonScptType ScptDefrostFanDelay = new BLonScptType(SCPT_DEFROST_FAN_DELAY);
  /** BLonScptType constant for ScptDefrostRecoveryTime. */
  public static final BLonScptType ScptDefrostRecoveryTime = new BLonScptType(SCPT_DEFROST_RECOVERY_TIME);
  /** BLonScptType constant for ScptDefrostHold. */
  public static final BLonScptType ScptDefrostHold = new BLonScptType(SCPT_DEFROST_HOLD);
  /** BLonScptType constant for ScptDefrostDetect. */
  public static final BLonScptType ScptDefrostDetect = new BLonScptType(SCPT_DEFROST_DETECT);
  /** BLonScptType constant for ScptScheduleInternal. */
  public static final BLonScptType ScptScheduleInternal = new BLonScptType(SCPT_SCHEDULE_INTERNAL);
  /** BLonScptType constant for ScptTempOffset. */
  public static final BLonScptType ScptTempOffset = new BLonScptType(SCPT_TEMP_OFFSET);
  /** BLonScptType constant for ScptAudibleLevel. */
  public static final BLonScptType ScptAudibleLevel = new BLonScptType(SCPT_AUDIBLE_LEVEL);
  /** BLonScptType constant for ScptScrollSpeed. */
  public static final BLonScptType ScptScrollSpeed = new BLonScptType(SCPT_SCROLL_SPEED);
  /** BLonScptType constant for ScptBrightness. */
  public static final BLonScptType ScptBrightness = new BLonScptType(SCPT_BRIGHTNESS);
  /** BLonScptType constant for ScptOrientation. */
  public static final BLonScptType ScptOrientation = new BLonScptType(SCPT_ORIENTATION);
  /** BLonScptType constant for ScptInstalledLevel. */
  public static final BLonScptType ScptInstalledLevel = new BLonScptType(SCPT_INSTALLED_LEVEL);
  /** BLonScptType constant for ScptPumpCharacteristic. */
  public static final BLonScptType ScptPumpCharacteristic = new BLonScptType(SCPT_PUMP_CHARACTERISTIC);
  /** BLonScptType constant for ScptMinPressureSetpoint. */
  public static final BLonScptType ScptMinPressureSetpoint = new BLonScptType(SCPT_MIN_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptMaxPressureSetpoint. */
  public static final BLonScptType ScptMaxPressureSetpoint = new BLonScptType(SCPT_MAX_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptMinFlowSetpoint. */
  public static final BLonScptType ScptMinFlowSetpoint = new BLonScptType(SCPT_MIN_FLOW_SETPOINT);
  /** BLonScptType constant for ScptMaxFlowSetpoint. */
  public static final BLonScptType ScptMaxFlowSetpoint = new BLonScptType(SCPT_MAX_FLOW_SETPOINT);
  /** BLonScptType constant for ScptDeviceControlMode. */
  public static final BLonScptType ScptDeviceControlMode = new BLonScptType(SCPT_DEVICE_CONTROL_MODE);
  /** BLonScptType constant for ScptMinRemotePressureSetpoint. */
  public static final BLonScptType ScptMinRemotePressureSetpoint = new BLonScptType(SCPT_MIN_REMOTE_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptMaxRemotePressureSetpoint. */
  public static final BLonScptType ScptMaxRemotePressureSetpoint = new BLonScptType(SCPT_MAX_REMOTE_PRESSURE_SETPOINT);
  /** BLonScptType constant for ScptMinRemoteFlowSetpoint. */
  public static final BLonScptType ScptMinRemoteFlowSetpoint = new BLonScptType(SCPT_MIN_REMOTE_FLOW_SETPOINT);
  /** BLonScptType constant for ScptMaxRemoteFlowSetpoint. */
  public static final BLonScptType ScptMaxRemoteFlowSetpoint = new BLonScptType(SCPT_MAX_REMOTE_FLOW_SETPOINT);
  /** BLonScptType constant for ScptMinRemoteTempSetpoint. */
  public static final BLonScptType ScptMinRemoteTempSetpoint = new BLonScptType(SCPT_MIN_REMOTE_TEMP_SETPOINT);
  /** BLonScptType constant for ScptMaxRemoteTempSetpoint. */
  public static final BLonScptType ScptMaxRemoteTempSetpoint = new BLonScptType(SCPT_MAX_REMOTE_TEMP_SETPOINT);
  /** BLonScptType constant for ScptControlSignal. */
  public static final BLonScptType ScptControlSignal = new BLonScptType(SCPT_CONTROL_SIGNAL);
  /** BLonScptType constant for ScptNightPurgePosition. */
  public static final BLonScptType ScptNightPurgePosition = new BLonScptType(SCPT_NIGHT_PURGE_POSITION);
  /** BLonScptType constant for ScptFreeCoolPosition. */
  public static final BLonScptType ScptFreeCoolPosition = new BLonScptType(SCPT_FREE_COOL_POSITION);
  /** BLonScptType constant for ScptValveFlowCharacteristic. */
  public static final BLonScptType ScptValveFlowCharacteristic = new BLonScptType(SCPT_VALVE_FLOW_CHARACTERISTIC);
  /** BLonScptType constant for ScptValveOperatingMode. */
  public static final BLonScptType ScptValveOperatingMode = new BLonScptType(SCPT_VALVE_OPERATING_MODE);
  /** BLonScptType constant for ScptEmergencyPosition. */
  public static final BLonScptType ScptEmergencyPosition = new BLonScptType(SCPT_EMERGENCY_POSITION);
  /** BLonScptType constant for ScptBlockProtectionTime. */
  public static final BLonScptType ScptBlockProtectionTime = new BLonScptType(SCPT_BLOCK_PROTECTION_TIME);
  /** BLonScptType constant for ScptMinStroke. */
  public static final BLonScptType ScptMinStroke = new BLonScptType(SCPT_MIN_STROKE);
  /** BLonScptType constant for ScptMaxStroke. */
  public static final BLonScptType ScptMaxStroke = new BLonScptType(SCPT_MAX_STROKE);
  /** BLonScptType constant for ScptNvType. */
  public static final BLonScptType ScptNvType = new BLonScptType(SCPT_NV_TYPE);
  /** BLonScptType constant for ScptMaxNVLength. */
  public static final BLonScptType ScptMaxNVLength = new BLonScptType(SCPT_MAX_NV_LENGTH);
  /** BLonScptType constant for ScptNvDynamicAssignment. */
  public static final BLonScptType ScptNvDynamicAssignment = new BLonScptType(SCPT_NV_DYNAMIC_ASSIGNMENT);
  /** BLonScptType constant for ScptSafExtCnfg. */
  public static final BLonScptType ScptSafExtCnfg = new BLonScptType(SCPT_SAF_EXT_CNFG);
  /** BLonScptType constant for ScptEmergCnfg. */
  public static final BLonScptType ScptEmergCnfg = new BLonScptType(SCPT_EMERG_CNFG);
  /** BLonScptType constant for ScptSluiceCnfg. */
  public static final BLonScptType ScptSluiceCnfg = new BLonScptType(SCPT_SLUICE_CNFG);
  /** BLonScptType constant for ScptFanOperation. */
  public static final BLonScptType ScptFanOperation = new BLonScptType(SCPT_FAN_OPERATION);
  /** BLonScptType constant for ScptMinFlowUnit. */
  public static final BLonScptType ScptMinFlowUnit = new BLonScptType(SCPT_MIN_FLOW_UNIT);
  /** BLonScptType constant for ScptMaxFlowUnit. */
  public static final BLonScptType ScptMaxFlowUnit = new BLonScptType(SCPT_MAX_FLOW_UNIT);
  /** BLonScptType constant for ScptMinFlowHeatStby. */
  public static final BLonScptType ScptMinFlowHeatStby = new BLonScptType(SCPT_MIN_FLOW_HEAT_STBY);
  /** BLonScptType constant for ScptMinFlowUnitStby. */
  public static final BLonScptType ScptMinFlowUnitStby = new BLonScptType(SCPT_MIN_FLOW_UNIT_STBY);
  /** BLonScptType constant for ScptOffsetFlow. */
  public static final BLonScptType ScptOffsetFlow = new BLonScptType(SCPT_OFFSET_FLOW);
  /** BLonScptType constant for ScptAreaDuctHeat. */
  public static final BLonScptType ScptAreaDuctHeat = new BLonScptType(SCPT_AREA_DUCT_HEAT);
  /** BLonScptType constant for ScptNomAirFlowHeat. */
  public static final BLonScptType ScptNomAirFlowHeat = new BLonScptType(SCPT_NOM_AIR_FLOW_HEAT);
  /** BLonScptType constant for ScptGainVAVHeat. */
  public static final BLonScptType ScptGainVAVHeat = new BLonScptType(SCPT_GAIN_VAVHEAT);
  /** BLonScptType constant for ScptNumDampers. */
  public static final BLonScptType ScptNumDampers = new BLonScptType(SCPT_NUM_DAMPERS);
  /** BLonScptType constant for ScptMinFlowUnitHeat. */
  public static final BLonScptType ScptMinFlowUnitHeat = new BLonScptType(SCPT_MIN_FLOW_UNIT_HEAT);
  /** BLonScptType constant for ScptSaturationDelay. */
  public static final BLonScptType ScptSaturationDelay = new BLonScptType(SCPT_SATURATION_DELAY);
  /** BLonScptType constant for ScptEffectivePeriod. */
  public static final BLonScptType ScptEffectivePeriod = new BLonScptType(SCPT_EFFECTIVE_PERIOD);
  /** BLonScptType constant for ScptScheduleDates. */
  public static final BLonScptType ScptScheduleDates = new BLonScptType(SCPT_SCHEDULE_DATES);
  /** BLonScptType constant for ScptSchedule. */
  public static final BLonScptType ScptSchedule = new BLonScptType(SCPT_SCHEDULE);
  /** BLonScptType constant for ScptScheduleTimeValue. */
  public static final BLonScptType ScptScheduleTimeValue = new BLonScptType(SCPT_SCHEDULE_TIME_VALUE);
  /** BLonScptType constant for ScptValueDefinition. */
  public static final BLonScptType ScptValueDefinition = new BLonScptType(SCPT_VALUE_DEFINITION);
  /** BLonScptType constant for ScptValueName. */
  public static final BLonScptType ScptValueName = new BLonScptType(SCPT_VALUE_NAME);
  /** BLonScptType constant for ScptWeeklySchedule. */
  public static final BLonScptType ScptWeeklySchedule = new BLonScptType(SCPT_WEEKLY_SCHEDULE);
  /** BLonScptType constant for ScptScheduleName. */
  public static final BLonScptType ScptScheduleName = new BLonScptType(SCPT_SCHEDULE_NAME);
  /** BLonScptType constant for ScptValveStroke. */
  public static final BLonScptType ScptValveStroke = new BLonScptType(SCPT_VALVE_STROKE);
  /** BLonScptType constant for ScptValveNominalSize. */
  public static final BLonScptType ScptValveNominalSize = new BLonScptType(SCPT_VALVE_NOMINAL_SIZE);
  /** BLonScptType constant for ScptValveKvs. */
  public static final BLonScptType ScptValveKvs = new BLonScptType(SCPT_VALVE_KVS);
  /** BLonScptType constant for ScptValveType. */
  public static final BLonScptType ScptValveType = new BLonScptType(SCPT_VALVE_TYPE);
  /** BLonScptType constant for ScptActuatorCharacteristic. */
  public static final BLonScptType ScptActuatorCharacteristic = new BLonScptType(SCPT_ACTUATOR_CHARACTERISTIC);
  /** BLonScptType constant for ScptTrnsTblX2. */
  public static final BLonScptType ScptTrnsTblX2 = new BLonScptType(SCPT_TRNS_TBL_X2);
  /** BLonScptType constant for ScptTrnsTblY2. */
  public static final BLonScptType ScptTrnsTblY2 = new BLonScptType(SCPT_TRNS_TBL_Y2);
  /** BLonScptType constant for ScptCombFlowCharacteristic. */
  public static final BLonScptType ScptCombFlowCharacteristic = new BLonScptType(SCPT_COMB_FLOW_CHARACTERISTIC);
  /** BLonScptType constant for ScptTrnsTblX3. */
  public static final BLonScptType ScptTrnsTblX3 = new BLonScptType(SCPT_TRNS_TBL_X3);
  /** BLonScptType constant for ScptTrnsTblY3. */
  public static final BLonScptType ScptTrnsTblY3 = new BLonScptType(SCPT_TRNS_TBL_Y3);
  /** BLonScptType constant for ScptRunTimeAlarm. */
  public static final BLonScptType ScptRunTimeAlarm = new BLonScptType(SCPT_RUN_TIME_ALARM);
  /** BLonScptType constant for ScptTimePeriod. */
  public static final BLonScptType ScptTimePeriod = new BLonScptType(SCPT_TIME_PERIOD);
  /** BLonScptType constant for ScptPulseValue. */
  public static final BLonScptType ScptPulseValue = new BLonScptType(SCPT_PULSE_VALUE);
  /** BLonScptType constant for ScptNumDigits. */
  public static final BLonScptType ScptNumDigits = new BLonScptType(SCPT_NUM_DIGITS);
  /** BLonScptType constant for ScptIdentity. */
  public static final BLonScptType ScptIdentity = new BLonScptType(SCPT_IDENTITY);
  /** BLonScptType constant for ScptDefaultState. */
  public static final BLonScptType ScptDefaultState = new BLonScptType(SCPT_DEFAULT_STATE);
  /** BLonScptType constant for ScptNvPriority. */
  public static final BLonScptType ScptNvPriority = new BLonScptType(SCPT_NV_PRIORITY);
  /** BLonScptType constant for ScptDefaultSetting. */
  public static final BLonScptType ScptDefaultSetting = new BLonScptType(SCPT_DEFAULT_SETTING);
  /** BLonScptType constant for ScptLowLimit1Enable. */
  public static final BLonScptType ScptLowLimit1Enable = new BLonScptType(SCPT_LOW_LIMIT1_ENABLE);
  /** BLonScptType constant for ScptLowLimit2Enable. */
  public static final BLonScptType ScptLowLimit2Enable = new BLonScptType(SCPT_LOW_LIMIT2_ENABLE);
  /** BLonScptType constant for ScptClockCalibration. */
  public static final BLonScptType ScptClockCalibration = new BLonScptType(SCPT_CLOCK_CALIBRATION);
  /** BLonScptType constant for ScptNeuronId. */
  public static final BLonScptType ScptNeuronId = new BLonScptType(SCPT_NEURON_ID);
  /** BLonScptType constant for ScptHighLimit1Enable. */
  public static final BLonScptType ScptHighLimit1Enable = new BLonScptType(SCPT_HIGH_LIMIT1_ENABLE);
  /** BLonScptType constant for ScptHighLimit2Enable. */
  public static final BLonScptType ScptHighLimit2Enable = new BLonScptType(SCPT_HIGH_LIMIT2_ENABLE);
  /** BLonScptType constant for ScptAhamApplianceModel. */
  public static final BLonScptType ScptAhamApplianceModel = new BLonScptType(SCPT_AHAM_APPLIANCE_MODEL);
  /** BLonScptType constant for ScptDefInput. */
  public static final BLonScptType ScptDefInput = new BLonScptType(SCPT_DEF_INPUT);
  /** BLonScptType constant for ScptName1. */
  public static final BLonScptType ScptName1 = new BLonScptType(SCPT_NAME1);
  /** BLonScptType constant for ScptScene. */
  public static final BLonScptType ScptScene = new BLonScptType(SCPT_SCENE);
  /** BLonScptType constant for ScptSceneTiming. */
  public static final BLonScptType ScptSceneTiming = new BLonScptType(SCPT_SCENE_TIMING);
  /** BLonScptType constant for ScptName2. */
  public static final BLonScptType ScptName2 = new BLonScptType(SCPT_NAME2);
  /** BLonScptType constant for ScptName3. */
  public static final BLonScptType ScptName3 = new BLonScptType(SCPT_NAME3);
  /** BLonScptType constant for ScptButtonPressAction. */
  public static final BLonScptType ScptButtonPressAction = new BLonScptType(SCPT_BUTTON_PRESS_ACTION);
  /** BLonScptType constant for ScptButtonColor. */
  public static final BLonScptType ScptButtonColor = new BLonScptType(SCPT_BUTTON_COLOR);
  /** BLonScptType constant for ScptButtonRepeatInterval. */
  public static final BLonScptType ScptButtonRepeatInterval = new BLonScptType(SCPT_BUTTON_REPEAT_INTERVAL);
  /** BLonScptType constant for ScptButtonHoldAction. */
  public static final BLonScptType ScptButtonHoldAction = new BLonScptType(SCPT_BUTTON_HOLD_ACTION);
  /** BLonScptType constant for ScptPwrSendOnDelta. */
  public static final BLonScptType ScptPwrSendOnDelta = new BLonScptType(SCPT_PWR_SEND_ON_DELTA);
  /** BLonScptType constant for ScptSceneName. */
  public static final BLonScptType ScptSceneName = new BLonScptType(SCPT_SCENE_NAME);
  /** BLonScptType constant for ScptMaxPower. */
  public static final BLonScptType ScptMaxPower = new BLonScptType(SCPT_MAX_POWER);
  /** BLonScptType constant for ScptIfaceDesc. */
  public static final BLonScptType ScptIfaceDesc = new BLonScptType(SCPT_IFACE_DESC);
  /** BLonScptType constant for ScptMonInterval. */
  public static final BLonScptType ScptMonInterval = new BLonScptType(SCPT_MON_INTERVAL);
  /** BLonScptType constant for ScptLinkPowerDetectEnable. */
  public static final BLonScptType ScptLinkPowerDetectEnable = new BLonScptType(SCPT_LINK_POWER_DETECT_ENABLE);
  /** BLonScptType constant for ScptScanTime. */
  public static final BLonScptType ScptScanTime = new BLonScptType(SCPT_SCAN_TIME);
  /** BLonScptType constant for ScptDevListDesc. */
  public static final BLonScptType ScptDevListDesc = new BLonScptType(SCPT_DEV_LIST_DESC);
  /** BLonScptType constant for ScptDevListEntry. */
  public static final BLonScptType ScptDevListEntry = new BLonScptType(SCPT_DEV_LIST_ENTRY);
  /** BLonScptType constant for ScptLogCapacity. */
  public static final BLonScptType ScptLogCapacity = new BLonScptType(SCPT_LOG_CAPACITY);
  /** BLonScptType constant for ScptLogNotificationThreshold. */
  public static final BLonScptType ScptLogNotificationThreshold = new BLonScptType(SCPT_LOG_NOTIFICATION_THRESHOLD);
  /** BLonScptType constant for ScptLogSize. */
  public static final BLonScptType ScptLogSize = new BLonScptType(SCPT_LOG_SIZE);
  /** BLonScptType constant for ScptLogType. */
  public static final BLonScptType ScptLogType = new BLonScptType(SCPT_LOG_TYPE);
  /** BLonScptType constant for ScptFanInEnable. */
  public static final BLonScptType ScptFanInEnable = new BLonScptType(SCPT_FAN_IN_ENABLE);
  /** BLonScptType constant for ScptLogTimestampEnable. */
  public static final BLonScptType ScptLogTimestampEnable = new BLonScptType(SCPT_LOG_TIMESTAMP_ENABLE);
  /** BLonScptType constant for ScptLogHighLimit. */
  public static final BLonScptType ScptLogHighLimit = new BLonScptType(SCPT_LOG_HIGH_LIMIT);
  /** BLonScptType constant for ScptLogLowLimit. */
  public static final BLonScptType ScptLogLowLimit = new BLonScptType(SCPT_LOG_LOW_LIMIT);
  /** BLonScptType constant for ScptMaxFanIn. */
  public static final BLonScptType ScptMaxFanIn = new BLonScptType(SCPT_MAX_FAN_IN);
  /** BLonScptType constant for ScptLogMinDeltaTime. */
  public static final BLonScptType ScptLogMinDeltaTime = new BLonScptType(SCPT_LOG_MIN_DELTA_TIME);
  /** BLonScptType constant for ScptLogMinDeltaValue. */
  public static final BLonScptType ScptLogMinDeltaValue = new BLonScptType(SCPT_LOG_MIN_DELTA_VALUE);
  /** BLonScptType constant for ScptPollRate. */
  public static final BLonScptType ScptPollRate = new BLonScptType(SCPT_POLL_RATE);
  /** BLonScptType constant for ScptSourceAddress. */
  public static final BLonScptType ScptSourceAddress = new BLonScptType(SCPT_SOURCE_ADDRESS);
  /** BLonScptType constant for ScptLogRecord. */
  public static final BLonScptType ScptLogRecord = new BLonScptType(SCPT_LOG_RECORD);
  /** BLonScptType constant for ScptLogFileHeader. */
  public static final BLonScptType ScptLogFileHeader = new BLonScptType(SCPT_LOG_FILE_HEADER);
  /** BLonScptType constant for ScptLogAlarmThreshold. */
  public static final BLonScptType ScptLogAlarmThreshold = new BLonScptType(SCPT_LOG_ALARM_THRESHOLD);
  /** BLonScptType constant for ScptLogRequest. */
  public static final BLonScptType ScptLogRequest = new BLonScptType(SCPT_LOG_REQUEST);
  /** BLonScptType constant for ScptLogResponse. */
  public static final BLonScptType ScptLogResponse = new BLonScptType(SCPT_LOG_RESPONSE);
  /** BLonScptType constant for ScptLightingGroupEnable. */
  public static final BLonScptType ScptLightingGroupEnable = new BLonScptType(SCPT_LIGHTING_GROUP_ENABLE);
  /** BLonScptType constant for ScptSceneColor. */
  public static final BLonScptType ScptSceneColor = new BLonScptType(SCPT_SCENE_COLOR);
  /** BLonScptType constant for ScptBkupSchedule. */
  public static final BLonScptType ScptBkupSchedule = new BLonScptType(SCPT_BKUP_SCHEDULE);
  /** BLonScptType constant for ScptOLCLimits. */
  public static final BLonScptType ScptOLCLimits = new BLonScptType(SCPT_OLCLIMITS);
  /** BLonScptType constant for ScptLampPower. */
  public static final BLonScptType ScptLampPower = new BLonScptType(SCPT_LAMP_POWER);
  /** BLonScptType constant for ScptDeviceOutSelection. */
  public static final BLonScptType ScptDeviceOutSelection = new BLonScptType(SCPT_DEVICE_OUT_SELECTION);
  /** BLonScptType constant for ScptEnableStatusMsg. */
  public static final BLonScptType ScptEnableStatusMsg = new BLonScptType(SCPT_ENABLE_STATUS_MSG);
  /** BLonScptType constant for ScptMaxLevelVolt. */
  public static final BLonScptType ScptMaxLevelVolt = new BLonScptType(SCPT_MAX_LEVEL_VOLT);
  /** BLonScptType constant for ScptGeoLocation. */
  public static final BLonScptType ScptGeoLocation = new BLonScptType(SCPT_GEO_LOCATION);
  /** BLonScptType constant for ScptProgName. */
  public static final BLonScptType ScptProgName = new BLonScptType(SCPT_PROG_NAME);
  /** BLonScptType constant for ScptProgRevision. */
  public static final BLonScptType ScptProgRevision = new BLonScptType(SCPT_PROG_REVISION);
  /** BLonScptType constant for ScptProgSelect. */
  public static final BLonScptType ScptProgSelect = new BLonScptType(SCPT_PROG_SELECT);
  /** BLonScptType constant for ScptProgSourceLocation. */
  public static final BLonScptType ScptProgSourceLocation = new BLonScptType(SCPT_PROG_SOURCE_LOCATION);
  /** BLonScptType constant for ScptProgFileIndexes. */
  public static final BLonScptType ScptProgFileIndexes = new BLonScptType(SCPT_PROG_FILE_INDEXES);
  /** BLonScptType constant for ScptProgCmdHistory. */
  public static final BLonScptType ScptProgCmdHistory = new BLonScptType(SCPT_PROG_CMD_HISTORY);
  /** BLonScptType constant for ScptProgStateHistory. */
  public static final BLonScptType ScptProgStateHistory = new BLonScptType(SCPT_PROG_STATE_HISTORY);
  /** BLonScptType constant for ScptNsdsFbIndex. */
  public static final BLonScptType ScptNsdsFbIndex = new BLonScptType(SCPT_NSDS_FB_INDEX);
  /** BLonScptType constant for ScptCurrentSenseEnable. */
  public static final BLonScptType ScptCurrentSenseEnable = new BLonScptType(SCPT_CURRENT_SENSE_ENABLE);
  /** BLonScptType constant for ScptMeasurementInterval. */
  public static final BLonScptType ScptMeasurementInterval = new BLonScptType(SCPT_MEASUREMENT_INTERVAL);
  /** BLonScptType constant for ScptLightingGroupMembership. */
  public static final BLonScptType ScptLightingGroupMembership = new BLonScptType(SCPT_LIGHTING_GROUP_MEMBERSHIP);
  /** BLonScptType constant for ScptLoadControlOffset. */
  public static final BLonScptType ScptLoadControlOffset = new BLonScptType(SCPT_LOAD_CONTROL_OFFSET);
  /** BLonScptType constant for ScptProgErrorHistory. */
  public static final BLonScptType ScptProgErrorHistory = new BLonScptType(SCPT_PROG_ERROR_HISTORY);
  /** BLonScptType constant for ScptNvUsage. */
  public static final BLonScptType ScptNvUsage = new BLonScptType(SCPT_NV_USAGE);
  /** BLonScptType constant for ScptScheduleSunday. */
  public static final BLonScptType ScptScheduleSunday = new BLonScptType(SCPT_SCHEDULE_SUNDAY);
  /** BLonScptType constant for ScptScheduleMonday. */
  public static final BLonScptType ScptScheduleMonday = new BLonScptType(SCPT_SCHEDULE_MONDAY);
  /** BLonScptType constant for ScptScheduleTuesday. */
  public static final BLonScptType ScptScheduleTuesday = new BLonScptType(SCPT_SCHEDULE_TUESDAY);
  /** BLonScptType constant for ScptScheduleWednesday. */
  public static final BLonScptType ScptScheduleWednesday = new BLonScptType(SCPT_SCHEDULE_WEDNESDAY);
  /** BLonScptType constant for ScptScheduleThursday. */
  public static final BLonScptType ScptScheduleThursday = new BLonScptType(SCPT_SCHEDULE_THURSDAY);
  /** BLonScptType constant for ScptScheduleFriday. */
  public static final BLonScptType ScptScheduleFriday = new BLonScptType(SCPT_SCHEDULE_FRIDAY);
  /** BLonScptType constant for ScptScheduleSaturday. */
  public static final BLonScptType ScptScheduleSaturday = new BLonScptType(SCPT_SCHEDULE_SATURDAY);
  /** BLonScptType constant for ScptOccupancyBehavior. */
  public static final BLonScptType ScptOccupancyBehavior = new BLonScptType(SCPT_OCCUPANCY_BEHAVIOR);
  /** BLonScptType constant for ScptTimeSource. */
  public static final BLonScptType ScptTimeSource = new BLonScptType(SCPT_TIME_SOURCE);
  /** BLonScptType constant for ScptScheduleException. */
  public static final BLonScptType ScptScheduleException = new BLonScptType(SCPT_SCHEDULE_EXCEPTION);
  /** BLonScptType constant for ScptScheduleHoliday. */
  public static final BLonScptType ScptScheduleHoliday = new BLonScptType(SCPT_SCHEDULE_HOLIDAY);
  /** BLonScptType constant for ScptRandomizationInterval. */
  public static final BLonScptType ScptRandomizationInterval = new BLonScptType(SCPT_RANDOMIZATION_INTERVAL);
  /** BLonScptType constant for ScptSunriseTime. */
  public static final BLonScptType ScptSunriseTime = new BLonScptType(SCPT_SUNRISE_TIME);
  /** BLonScptType constant for ScptSunsetTime. */
  public static final BLonScptType ScptSunsetTime = new BLonScptType(SCPT_SUNSET_TIME);
  /** BLonScptType constant for ScptSchedulerOptions. */
  public static final BLonScptType ScptSchedulerOptions = new BLonScptType(SCPT_SCHEDULER_OPTIONS);
  /** BLonScptType constant for ScptOccupancyThresholds. */
  public static final BLonScptType ScptOccupancyThresholds = new BLonScptType(SCPT_OCCUPANCY_THRESHOLDS);

  /** Factory method with ordinal. */
  public static BLonScptType make(int ordinal)
  {
    return (BLonScptType)ScptXxx.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonScptType make(String tag)
  {
    return (BLonScptType)ScptXxx.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonScptType(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonScptType DEFAULT = ScptXxx;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonScptType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static final int LAST_SCPT_ID  = 380 ;

//  // Create a list of SCPT types for this class file
//  // to run >>nre lonworks:javax.baja.lonworks.enums.BLonScptType
//  public static void main(String[] args)
//  {
//      XLonInterfaceFile std = XUtil.getStandard();
//      Vector types = std.types;
//      String[] a = new String[types.size()];
//      String[] c = new String[types.size()];
//int[] la = new int [types.size()];      
//      int maxNv = 0;
//      int maxLen = 0;
//      int maxCLen = 0;
//      for(int i=0 ; i<types.size() ; i++)
//      {
//        XTypeDef t = ((XTypeDef)types.elementAt(i));
//        if(t.isCpType())
//        { 
//          int ndx = t.getTypeIndex();
//          if(ndx < 0) continue;
//          String name = t.getName().substring(2);
//          a[ndx] = NameUtil.toJavaName(name,true);
//          c[ndx] = NameUtil.toConstantName(name);
//          if(ndx>maxNv) maxNv = ndx;
//          
//          if(a[ndx].length() > maxLen) maxLen = a[ndx].length();
//          if(c[ndx].length() > maxCLen) maxCLen = c[ndx].length();
//try { la[ndx] = t.getLonData(std).getByteLength(); } catch(Exception e) { System.out.println("error:" + a[ndx]);}          
//        }
//      }
//      
//      String pad = "                                                        ";
//      System.out.println("  public static final int SCPT_XXX          = 0  ;");
//      for(int i=1; i<=maxNv ; i++)
//      {
//        String conName = c[i];
//        System.out.println("  public static final int SCPT_" + 
//               conName + pad.substring(0,maxCLen - conName.length()) + " = " + i + "  ;"); 
//      }
//
//      System.out.println("\n\n");
//      System.out.println("  public static final int LAST_SCPT_ID  = " + maxNv + " ;\n");
//      System.out.println("  public static final BLonScptType SCPTXxx         = new BLonScptType(SCPT_XXX         );\n");
//      
//      for(int i=1; i<=maxNv ; i++)
//      {
//        String conName = c[i];
//        System.out.println("  public static final BLonScptType Scpt" 
//                     + a[i] + pad.substring(0,maxLen - a[i].length()) +
//                      " = new BLonScptType(SCPT_" + conName + pad.substring(0,maxCLen-conName.length()) + ");");
//      }
//    
//      System.out.println("\n\n/* matches name format in SCPT Master list\n");
//      for(int i=1; i<=maxNv ; i++)
//      {
//        String conName = c[i];
//        System.out.println("  public static final BLonScptType SCPT_" 
//                     + TextUtil.toLowerCase(conName) + pad.substring(0,maxCLen - conName.length()) +
//                      " = new BLonScptType(SCPT_" + conName + pad.substring(0,maxCLen - conName.length()) + 
// ");  " + la[i]);
//      }
//      System.out.println("* //");
//  }
}
