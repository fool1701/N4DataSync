/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.energy;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.Lexicon;


/** Electrictial Demand limit object
 *   On each new minute, this object predicts a demand average of a sliding window
 *   interval (the length of which is user defined) by combining projected usage
 *   with historical samplings and averaging over the interval. The user controls
 *  the assumed position (in percentage) within the sliding window. The user may
 *  divide the day into three sections, each with its own demand limit. The projected
 *   demand is compared to the limit for the current time-of-day to decide whether
 *  "shedding" or "reloading" loads is appropriate in a fixed priority . The time,
 *  date and value of new demand limits are save for both this month and the previous
 *  month.
 *
 *  This object provides an output that can be linked to the Shed Control object which
 *  actually performs the equipment control.
 *
 *  A message is output with each calculation to provide an indication of the object's
 *  calculated result or recommendation.
 *
 *  Execution of this object can be enabled or disabled (default) either automatically
 *  or manually (via right click).
 *
 * 
 * @author    Andy Saunders
 * @creation  11 Jan 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraProperty(
  name = "predictionEnabled",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(true)",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "powerInput",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0.0)",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "message",
  type = "String",
  defaultValue = "new String(\"\")",
  flags = Flags.TRANSIENT,
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, true)")
)
@NiagaraProperty(
  name = "shedOut",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "billingStartDay",
  type = "int",
  defaultValue = "1"
)
@NiagaraProperty(
  name = "demandInterval",
  type = "int",
  defaultValue = "15"
)
@NiagaraProperty(
  name = "percentIntervalElapsed",
  type = "int",
  defaultValue = "75"
)
@NiagaraProperty(
  name = "rotateLevel",
  type = "int",
  defaultValue = "0"
)
@NiagaraProperty(
  name = "demandLimitingDeadband",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "demandLimitPeriod1",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "demandLimitPeriod2",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "demandLimitPeriod3",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "demandPeriod1Start",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
@NiagaraProperty(
  name = "demandPeriod2Start",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
@NiagaraProperty(
  name = "demandPeriod3Start",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
@NiagaraProperty(
  name = "powerShedLevel1",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel2",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel3",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel4",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel5",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel6",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel7",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel8",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel9",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel10",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel11",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel12",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel13",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel14",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel15",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel16",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel17",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel18",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel19",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel20",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel21",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel22",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel23",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel24",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel25",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel26",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel27",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel28",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel29",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel30",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel31",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "powerShedLevel32",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "thisMonthDemandPeriod1Peak",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "thisMonthDemandPeriod1PeakTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "thisMonthDemandPeriod2Peak",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "thisMonthDemandPeriod2PeakTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "thisMonthDemandPeriod3Peak",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "thisMonthDemandPeriod3PeakTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "previousMonthDemandPeriod1Peak",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "previousMonthDemandPeriod1PeakTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "previousMonthDemandPeriod2Peak",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "previousMonthDemandPeriod2PeakTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "previousMonthDemandPeriod3Peak",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "previousMonthDemandPeriod3PeakTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "projectedDemandAverage",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "maxShedLevel",
  type = "int",
  defaultValue = "32",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraAction(
  name = "calculate",
  flags = Flags.HIDDEN
)
public class BElectricalDemandLimit
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BElectricalDemandLimit(3608107362)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "predictionEnabled"

  /**
   * Slot for the {@code predictionEnabled} property.
   * @see #getPredictionEnabled
   * @see #setPredictionEnabled
   */
  public static final Property predictionEnabled = newProperty(Flags.SUMMARY, new BStatusBoolean(true), null);

  /**
   * Get the {@code predictionEnabled} property.
   * @see #predictionEnabled
   */
  public BStatusBoolean getPredictionEnabled() { return (BStatusBoolean)get(predictionEnabled); }

  /**
   * Set the {@code predictionEnabled} property.
   * @see #predictionEnabled
   */
  public void setPredictionEnabled(BStatusBoolean v) { set(predictionEnabled, v, null); }

  //endregion Property "predictionEnabled"

  //region Property "powerInput"

  /**
   * Slot for the {@code powerInput} property.
   * @see #getPowerInput
   * @see #setPowerInput
   */
  public static final Property powerInput = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(0.0), null);

  /**
   * Get the {@code powerInput} property.
   * @see #powerInput
   */
  public BStatusNumeric getPowerInput() { return (BStatusNumeric)get(powerInput); }

  /**
   * Set the {@code powerInput} property.
   * @see #powerInput
   */
  public void setPowerInput(BStatusNumeric v) { set(powerInput, v, null); }

  //endregion Property "powerInput"

  //region Property "message"

  /**
   * Slot for the {@code message} property.
   * @see #getMessage
   * @see #setMessage
   */
  public static final Property message = newProperty(Flags.TRANSIENT, new String(""), BFacets.make(BFacets.MULTI_LINE, true));

  /**
   * Get the {@code message} property.
   * @see #message
   */
  public String getMessage() { return getString(message); }

  /**
   * Set the {@code message} property.
   * @see #message
   */
  public void setMessage(String v) { setString(message, v, null); }

  //endregion Property "message"

  //region Property "shedOut"

  /**
   * Slot for the {@code shedOut} property.
   * @see #getShedOut
   * @see #setShedOut
   */
  public static final Property shedOut = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code shedOut} property.
   * @see #shedOut
   */
  public BStatusNumeric getShedOut() { return (BStatusNumeric)get(shedOut); }

  /**
   * Set the {@code shedOut} property.
   * @see #shedOut
   */
  public void setShedOut(BStatusNumeric v) { set(shedOut, v, null); }

  //endregion Property "shedOut"

  //region Property "billingStartDay"

  /**
   * Slot for the {@code billingStartDay} property.
   * @see #getBillingStartDay
   * @see #setBillingStartDay
   */
  public static final Property billingStartDay = newProperty(0, 1, null);

  /**
   * Get the {@code billingStartDay} property.
   * @see #billingStartDay
   */
  public int getBillingStartDay() { return getInt(billingStartDay); }

  /**
   * Set the {@code billingStartDay} property.
   * @see #billingStartDay
   */
  public void setBillingStartDay(int v) { setInt(billingStartDay, v, null); }

  //endregion Property "billingStartDay"

  //region Property "demandInterval"

  /**
   * Slot for the {@code demandInterval} property.
   * @see #getDemandInterval
   * @see #setDemandInterval
   */
  public static final Property demandInterval = newProperty(0, 15, null);

  /**
   * Get the {@code demandInterval} property.
   * @see #demandInterval
   */
  public int getDemandInterval() { return getInt(demandInterval); }

  /**
   * Set the {@code demandInterval} property.
   * @see #demandInterval
   */
  public void setDemandInterval(int v) { setInt(demandInterval, v, null); }

  //endregion Property "demandInterval"

  //region Property "percentIntervalElapsed"

  /**
   * Slot for the {@code percentIntervalElapsed} property.
   * @see #getPercentIntervalElapsed
   * @see #setPercentIntervalElapsed
   */
  public static final Property percentIntervalElapsed = newProperty(0, 75, null);

  /**
   * Get the {@code percentIntervalElapsed} property.
   * @see #percentIntervalElapsed
   */
  public int getPercentIntervalElapsed() { return getInt(percentIntervalElapsed); }

  /**
   * Set the {@code percentIntervalElapsed} property.
   * @see #percentIntervalElapsed
   */
  public void setPercentIntervalElapsed(int v) { setInt(percentIntervalElapsed, v, null); }

  //endregion Property "percentIntervalElapsed"

  //region Property "rotateLevel"

  /**
   * Slot for the {@code rotateLevel} property.
   * @see #getRotateLevel
   * @see #setRotateLevel
   */
  public static final Property rotateLevel = newProperty(0, 0, null);

  /**
   * Get the {@code rotateLevel} property.
   * @see #rotateLevel
   */
  public int getRotateLevel() { return getInt(rotateLevel); }

  /**
   * Set the {@code rotateLevel} property.
   * @see #rotateLevel
   */
  public void setRotateLevel(int v) { setInt(rotateLevel, v, null); }

  //endregion Property "rotateLevel"

  //region Property "demandLimitingDeadband"

  /**
   * Slot for the {@code demandLimitingDeadband} property.
   * @see #getDemandLimitingDeadband
   * @see #setDemandLimitingDeadband
   */
  public static final Property demandLimitingDeadband = newProperty(0, 0.0f, null);

  /**
   * Get the {@code demandLimitingDeadband} property.
   * @see #demandLimitingDeadband
   */
  public float getDemandLimitingDeadband() { return getFloat(demandLimitingDeadband); }

  /**
   * Set the {@code demandLimitingDeadband} property.
   * @see #demandLimitingDeadband
   */
  public void setDemandLimitingDeadband(float v) { setFloat(demandLimitingDeadband, v, null); }

  //endregion Property "demandLimitingDeadband"

  //region Property "demandLimitPeriod1"

  /**
   * Slot for the {@code demandLimitPeriod1} property.
   * @see #getDemandLimitPeriod1
   * @see #setDemandLimitPeriod1
   */
  public static final Property demandLimitPeriod1 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code demandLimitPeriod1} property.
   * @see #demandLimitPeriod1
   */
  public float getDemandLimitPeriod1() { return getFloat(demandLimitPeriod1); }

  /**
   * Set the {@code demandLimitPeriod1} property.
   * @see #demandLimitPeriod1
   */
  public void setDemandLimitPeriod1(float v) { setFloat(demandLimitPeriod1, v, null); }

  //endregion Property "demandLimitPeriod1"

  //region Property "demandLimitPeriod2"

  /**
   * Slot for the {@code demandLimitPeriod2} property.
   * @see #getDemandLimitPeriod2
   * @see #setDemandLimitPeriod2
   */
  public static final Property demandLimitPeriod2 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code demandLimitPeriod2} property.
   * @see #demandLimitPeriod2
   */
  public float getDemandLimitPeriod2() { return getFloat(demandLimitPeriod2); }

  /**
   * Set the {@code demandLimitPeriod2} property.
   * @see #demandLimitPeriod2
   */
  public void setDemandLimitPeriod2(float v) { setFloat(demandLimitPeriod2, v, null); }

  //endregion Property "demandLimitPeriod2"

  //region Property "demandLimitPeriod3"

  /**
   * Slot for the {@code demandLimitPeriod3} property.
   * @see #getDemandLimitPeriod3
   * @see #setDemandLimitPeriod3
   */
  public static final Property demandLimitPeriod3 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code demandLimitPeriod3} property.
   * @see #demandLimitPeriod3
   */
  public float getDemandLimitPeriod3() { return getFloat(demandLimitPeriod3); }

  /**
   * Set the {@code demandLimitPeriod3} property.
   * @see #demandLimitPeriod3
   */
  public void setDemandLimitPeriod3(float v) { setFloat(demandLimitPeriod3, v, null); }

  //endregion Property "demandLimitPeriod3"

  //region Property "demandPeriod1Start"

  /**
   * Slot for the {@code demandPeriod1Start} property.
   * @see #getDemandPeriod1Start
   * @see #setDemandPeriod1Start
   */
  public static final Property demandPeriod1Start = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code demandPeriod1Start} property.
   * @see #demandPeriod1Start
   */
  public BRelTime getDemandPeriod1Start() { return (BRelTime)get(demandPeriod1Start); }

  /**
   * Set the {@code demandPeriod1Start} property.
   * @see #demandPeriod1Start
   */
  public void setDemandPeriod1Start(BRelTime v) { set(demandPeriod1Start, v, null); }

  //endregion Property "demandPeriod1Start"

  //region Property "demandPeriod2Start"

  /**
   * Slot for the {@code demandPeriod2Start} property.
   * @see #getDemandPeriod2Start
   * @see #setDemandPeriod2Start
   */
  public static final Property demandPeriod2Start = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code demandPeriod2Start} property.
   * @see #demandPeriod2Start
   */
  public BRelTime getDemandPeriod2Start() { return (BRelTime)get(demandPeriod2Start); }

  /**
   * Set the {@code demandPeriod2Start} property.
   * @see #demandPeriod2Start
   */
  public void setDemandPeriod2Start(BRelTime v) { set(demandPeriod2Start, v, null); }

  //endregion Property "demandPeriod2Start"

  //region Property "demandPeriod3Start"

  /**
   * Slot for the {@code demandPeriod3Start} property.
   * @see #getDemandPeriod3Start
   * @see #setDemandPeriod3Start
   */
  public static final Property demandPeriod3Start = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code demandPeriod3Start} property.
   * @see #demandPeriod3Start
   */
  public BRelTime getDemandPeriod3Start() { return (BRelTime)get(demandPeriod3Start); }

  /**
   * Set the {@code demandPeriod3Start} property.
   * @see #demandPeriod3Start
   */
  public void setDemandPeriod3Start(BRelTime v) { set(demandPeriod3Start, v, null); }

  //endregion Property "demandPeriod3Start"

  //region Property "powerShedLevel1"

  /**
   * Slot for the {@code powerShedLevel1} property.
   * @see #getPowerShedLevel1
   * @see #setPowerShedLevel1
   */
  public static final Property powerShedLevel1 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel1} property.
   * @see #powerShedLevel1
   */
  public float getPowerShedLevel1() { return getFloat(powerShedLevel1); }

  /**
   * Set the {@code powerShedLevel1} property.
   * @see #powerShedLevel1
   */
  public void setPowerShedLevel1(float v) { setFloat(powerShedLevel1, v, null); }

  //endregion Property "powerShedLevel1"

  //region Property "powerShedLevel2"

  /**
   * Slot for the {@code powerShedLevel2} property.
   * @see #getPowerShedLevel2
   * @see #setPowerShedLevel2
   */
  public static final Property powerShedLevel2 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel2} property.
   * @see #powerShedLevel2
   */
  public float getPowerShedLevel2() { return getFloat(powerShedLevel2); }

  /**
   * Set the {@code powerShedLevel2} property.
   * @see #powerShedLevel2
   */
  public void setPowerShedLevel2(float v) { setFloat(powerShedLevel2, v, null); }

  //endregion Property "powerShedLevel2"

  //region Property "powerShedLevel3"

  /**
   * Slot for the {@code powerShedLevel3} property.
   * @see #getPowerShedLevel3
   * @see #setPowerShedLevel3
   */
  public static final Property powerShedLevel3 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel3} property.
   * @see #powerShedLevel3
   */
  public float getPowerShedLevel3() { return getFloat(powerShedLevel3); }

  /**
   * Set the {@code powerShedLevel3} property.
   * @see #powerShedLevel3
   */
  public void setPowerShedLevel3(float v) { setFloat(powerShedLevel3, v, null); }

  //endregion Property "powerShedLevel3"

  //region Property "powerShedLevel4"

  /**
   * Slot for the {@code powerShedLevel4} property.
   * @see #getPowerShedLevel4
   * @see #setPowerShedLevel4
   */
  public static final Property powerShedLevel4 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel4} property.
   * @see #powerShedLevel4
   */
  public float getPowerShedLevel4() { return getFloat(powerShedLevel4); }

  /**
   * Set the {@code powerShedLevel4} property.
   * @see #powerShedLevel4
   */
  public void setPowerShedLevel4(float v) { setFloat(powerShedLevel4, v, null); }

  //endregion Property "powerShedLevel4"

  //region Property "powerShedLevel5"

  /**
   * Slot for the {@code powerShedLevel5} property.
   * @see #getPowerShedLevel5
   * @see #setPowerShedLevel5
   */
  public static final Property powerShedLevel5 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel5} property.
   * @see #powerShedLevel5
   */
  public float getPowerShedLevel5() { return getFloat(powerShedLevel5); }

  /**
   * Set the {@code powerShedLevel5} property.
   * @see #powerShedLevel5
   */
  public void setPowerShedLevel5(float v) { setFloat(powerShedLevel5, v, null); }

  //endregion Property "powerShedLevel5"

  //region Property "powerShedLevel6"

  /**
   * Slot for the {@code powerShedLevel6} property.
   * @see #getPowerShedLevel6
   * @see #setPowerShedLevel6
   */
  public static final Property powerShedLevel6 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel6} property.
   * @see #powerShedLevel6
   */
  public float getPowerShedLevel6() { return getFloat(powerShedLevel6); }

  /**
   * Set the {@code powerShedLevel6} property.
   * @see #powerShedLevel6
   */
  public void setPowerShedLevel6(float v) { setFloat(powerShedLevel6, v, null); }

  //endregion Property "powerShedLevel6"

  //region Property "powerShedLevel7"

  /**
   * Slot for the {@code powerShedLevel7} property.
   * @see #getPowerShedLevel7
   * @see #setPowerShedLevel7
   */
  public static final Property powerShedLevel7 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel7} property.
   * @see #powerShedLevel7
   */
  public float getPowerShedLevel7() { return getFloat(powerShedLevel7); }

  /**
   * Set the {@code powerShedLevel7} property.
   * @see #powerShedLevel7
   */
  public void setPowerShedLevel7(float v) { setFloat(powerShedLevel7, v, null); }

  //endregion Property "powerShedLevel7"

  //region Property "powerShedLevel8"

  /**
   * Slot for the {@code powerShedLevel8} property.
   * @see #getPowerShedLevel8
   * @see #setPowerShedLevel8
   */
  public static final Property powerShedLevel8 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel8} property.
   * @see #powerShedLevel8
   */
  public float getPowerShedLevel8() { return getFloat(powerShedLevel8); }

  /**
   * Set the {@code powerShedLevel8} property.
   * @see #powerShedLevel8
   */
  public void setPowerShedLevel8(float v) { setFloat(powerShedLevel8, v, null); }

  //endregion Property "powerShedLevel8"

  //region Property "powerShedLevel9"

  /**
   * Slot for the {@code powerShedLevel9} property.
   * @see #getPowerShedLevel9
   * @see #setPowerShedLevel9
   */
  public static final Property powerShedLevel9 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel9} property.
   * @see #powerShedLevel9
   */
  public float getPowerShedLevel9() { return getFloat(powerShedLevel9); }

  /**
   * Set the {@code powerShedLevel9} property.
   * @see #powerShedLevel9
   */
  public void setPowerShedLevel9(float v) { setFloat(powerShedLevel9, v, null); }

  //endregion Property "powerShedLevel9"

  //region Property "powerShedLevel10"

  /**
   * Slot for the {@code powerShedLevel10} property.
   * @see #getPowerShedLevel10
   * @see #setPowerShedLevel10
   */
  public static final Property powerShedLevel10 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel10} property.
   * @see #powerShedLevel10
   */
  public float getPowerShedLevel10() { return getFloat(powerShedLevel10); }

  /**
   * Set the {@code powerShedLevel10} property.
   * @see #powerShedLevel10
   */
  public void setPowerShedLevel10(float v) { setFloat(powerShedLevel10, v, null); }

  //endregion Property "powerShedLevel10"

  //region Property "powerShedLevel11"

  /**
   * Slot for the {@code powerShedLevel11} property.
   * @see #getPowerShedLevel11
   * @see #setPowerShedLevel11
   */
  public static final Property powerShedLevel11 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel11} property.
   * @see #powerShedLevel11
   */
  public float getPowerShedLevel11() { return getFloat(powerShedLevel11); }

  /**
   * Set the {@code powerShedLevel11} property.
   * @see #powerShedLevel11
   */
  public void setPowerShedLevel11(float v) { setFloat(powerShedLevel11, v, null); }

  //endregion Property "powerShedLevel11"

  //region Property "powerShedLevel12"

  /**
   * Slot for the {@code powerShedLevel12} property.
   * @see #getPowerShedLevel12
   * @see #setPowerShedLevel12
   */
  public static final Property powerShedLevel12 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel12} property.
   * @see #powerShedLevel12
   */
  public float getPowerShedLevel12() { return getFloat(powerShedLevel12); }

  /**
   * Set the {@code powerShedLevel12} property.
   * @see #powerShedLevel12
   */
  public void setPowerShedLevel12(float v) { setFloat(powerShedLevel12, v, null); }

  //endregion Property "powerShedLevel12"

  //region Property "powerShedLevel13"

  /**
   * Slot for the {@code powerShedLevel13} property.
   * @see #getPowerShedLevel13
   * @see #setPowerShedLevel13
   */
  public static final Property powerShedLevel13 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel13} property.
   * @see #powerShedLevel13
   */
  public float getPowerShedLevel13() { return getFloat(powerShedLevel13); }

  /**
   * Set the {@code powerShedLevel13} property.
   * @see #powerShedLevel13
   */
  public void setPowerShedLevel13(float v) { setFloat(powerShedLevel13, v, null); }

  //endregion Property "powerShedLevel13"

  //region Property "powerShedLevel14"

  /**
   * Slot for the {@code powerShedLevel14} property.
   * @see #getPowerShedLevel14
   * @see #setPowerShedLevel14
   */
  public static final Property powerShedLevel14 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel14} property.
   * @see #powerShedLevel14
   */
  public float getPowerShedLevel14() { return getFloat(powerShedLevel14); }

  /**
   * Set the {@code powerShedLevel14} property.
   * @see #powerShedLevel14
   */
  public void setPowerShedLevel14(float v) { setFloat(powerShedLevel14, v, null); }

  //endregion Property "powerShedLevel14"

  //region Property "powerShedLevel15"

  /**
   * Slot for the {@code powerShedLevel15} property.
   * @see #getPowerShedLevel15
   * @see #setPowerShedLevel15
   */
  public static final Property powerShedLevel15 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel15} property.
   * @see #powerShedLevel15
   */
  public float getPowerShedLevel15() { return getFloat(powerShedLevel15); }

  /**
   * Set the {@code powerShedLevel15} property.
   * @see #powerShedLevel15
   */
  public void setPowerShedLevel15(float v) { setFloat(powerShedLevel15, v, null); }

  //endregion Property "powerShedLevel15"

  //region Property "powerShedLevel16"

  /**
   * Slot for the {@code powerShedLevel16} property.
   * @see #getPowerShedLevel16
   * @see #setPowerShedLevel16
   */
  public static final Property powerShedLevel16 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel16} property.
   * @see #powerShedLevel16
   */
  public float getPowerShedLevel16() { return getFloat(powerShedLevel16); }

  /**
   * Set the {@code powerShedLevel16} property.
   * @see #powerShedLevel16
   */
  public void setPowerShedLevel16(float v) { setFloat(powerShedLevel16, v, null); }

  //endregion Property "powerShedLevel16"

  //region Property "powerShedLevel17"

  /**
   * Slot for the {@code powerShedLevel17} property.
   * @see #getPowerShedLevel17
   * @see #setPowerShedLevel17
   */
  public static final Property powerShedLevel17 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel17} property.
   * @see #powerShedLevel17
   */
  public float getPowerShedLevel17() { return getFloat(powerShedLevel17); }

  /**
   * Set the {@code powerShedLevel17} property.
   * @see #powerShedLevel17
   */
  public void setPowerShedLevel17(float v) { setFloat(powerShedLevel17, v, null); }

  //endregion Property "powerShedLevel17"

  //region Property "powerShedLevel18"

  /**
   * Slot for the {@code powerShedLevel18} property.
   * @see #getPowerShedLevel18
   * @see #setPowerShedLevel18
   */
  public static final Property powerShedLevel18 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel18} property.
   * @see #powerShedLevel18
   */
  public float getPowerShedLevel18() { return getFloat(powerShedLevel18); }

  /**
   * Set the {@code powerShedLevel18} property.
   * @see #powerShedLevel18
   */
  public void setPowerShedLevel18(float v) { setFloat(powerShedLevel18, v, null); }

  //endregion Property "powerShedLevel18"

  //region Property "powerShedLevel19"

  /**
   * Slot for the {@code powerShedLevel19} property.
   * @see #getPowerShedLevel19
   * @see #setPowerShedLevel19
   */
  public static final Property powerShedLevel19 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel19} property.
   * @see #powerShedLevel19
   */
  public float getPowerShedLevel19() { return getFloat(powerShedLevel19); }

  /**
   * Set the {@code powerShedLevel19} property.
   * @see #powerShedLevel19
   */
  public void setPowerShedLevel19(float v) { setFloat(powerShedLevel19, v, null); }

  //endregion Property "powerShedLevel19"

  //region Property "powerShedLevel20"

  /**
   * Slot for the {@code powerShedLevel20} property.
   * @see #getPowerShedLevel20
   * @see #setPowerShedLevel20
   */
  public static final Property powerShedLevel20 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel20} property.
   * @see #powerShedLevel20
   */
  public float getPowerShedLevel20() { return getFloat(powerShedLevel20); }

  /**
   * Set the {@code powerShedLevel20} property.
   * @see #powerShedLevel20
   */
  public void setPowerShedLevel20(float v) { setFloat(powerShedLevel20, v, null); }

  //endregion Property "powerShedLevel20"

  //region Property "powerShedLevel21"

  /**
   * Slot for the {@code powerShedLevel21} property.
   * @see #getPowerShedLevel21
   * @see #setPowerShedLevel21
   */
  public static final Property powerShedLevel21 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel21} property.
   * @see #powerShedLevel21
   */
  public float getPowerShedLevel21() { return getFloat(powerShedLevel21); }

  /**
   * Set the {@code powerShedLevel21} property.
   * @see #powerShedLevel21
   */
  public void setPowerShedLevel21(float v) { setFloat(powerShedLevel21, v, null); }

  //endregion Property "powerShedLevel21"

  //region Property "powerShedLevel22"

  /**
   * Slot for the {@code powerShedLevel22} property.
   * @see #getPowerShedLevel22
   * @see #setPowerShedLevel22
   */
  public static final Property powerShedLevel22 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel22} property.
   * @see #powerShedLevel22
   */
  public float getPowerShedLevel22() { return getFloat(powerShedLevel22); }

  /**
   * Set the {@code powerShedLevel22} property.
   * @see #powerShedLevel22
   */
  public void setPowerShedLevel22(float v) { setFloat(powerShedLevel22, v, null); }

  //endregion Property "powerShedLevel22"

  //region Property "powerShedLevel23"

  /**
   * Slot for the {@code powerShedLevel23} property.
   * @see #getPowerShedLevel23
   * @see #setPowerShedLevel23
   */
  public static final Property powerShedLevel23 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel23} property.
   * @see #powerShedLevel23
   */
  public float getPowerShedLevel23() { return getFloat(powerShedLevel23); }

  /**
   * Set the {@code powerShedLevel23} property.
   * @see #powerShedLevel23
   */
  public void setPowerShedLevel23(float v) { setFloat(powerShedLevel23, v, null); }

  //endregion Property "powerShedLevel23"

  //region Property "powerShedLevel24"

  /**
   * Slot for the {@code powerShedLevel24} property.
   * @see #getPowerShedLevel24
   * @see #setPowerShedLevel24
   */
  public static final Property powerShedLevel24 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel24} property.
   * @see #powerShedLevel24
   */
  public float getPowerShedLevel24() { return getFloat(powerShedLevel24); }

  /**
   * Set the {@code powerShedLevel24} property.
   * @see #powerShedLevel24
   */
  public void setPowerShedLevel24(float v) { setFloat(powerShedLevel24, v, null); }

  //endregion Property "powerShedLevel24"

  //region Property "powerShedLevel25"

  /**
   * Slot for the {@code powerShedLevel25} property.
   * @see #getPowerShedLevel25
   * @see #setPowerShedLevel25
   */
  public static final Property powerShedLevel25 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel25} property.
   * @see #powerShedLevel25
   */
  public float getPowerShedLevel25() { return getFloat(powerShedLevel25); }

  /**
   * Set the {@code powerShedLevel25} property.
   * @see #powerShedLevel25
   */
  public void setPowerShedLevel25(float v) { setFloat(powerShedLevel25, v, null); }

  //endregion Property "powerShedLevel25"

  //region Property "powerShedLevel26"

  /**
   * Slot for the {@code powerShedLevel26} property.
   * @see #getPowerShedLevel26
   * @see #setPowerShedLevel26
   */
  public static final Property powerShedLevel26 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel26} property.
   * @see #powerShedLevel26
   */
  public float getPowerShedLevel26() { return getFloat(powerShedLevel26); }

  /**
   * Set the {@code powerShedLevel26} property.
   * @see #powerShedLevel26
   */
  public void setPowerShedLevel26(float v) { setFloat(powerShedLevel26, v, null); }

  //endregion Property "powerShedLevel26"

  //region Property "powerShedLevel27"

  /**
   * Slot for the {@code powerShedLevel27} property.
   * @see #getPowerShedLevel27
   * @see #setPowerShedLevel27
   */
  public static final Property powerShedLevel27 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel27} property.
   * @see #powerShedLevel27
   */
  public float getPowerShedLevel27() { return getFloat(powerShedLevel27); }

  /**
   * Set the {@code powerShedLevel27} property.
   * @see #powerShedLevel27
   */
  public void setPowerShedLevel27(float v) { setFloat(powerShedLevel27, v, null); }

  //endregion Property "powerShedLevel27"

  //region Property "powerShedLevel28"

  /**
   * Slot for the {@code powerShedLevel28} property.
   * @see #getPowerShedLevel28
   * @see #setPowerShedLevel28
   */
  public static final Property powerShedLevel28 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel28} property.
   * @see #powerShedLevel28
   */
  public float getPowerShedLevel28() { return getFloat(powerShedLevel28); }

  /**
   * Set the {@code powerShedLevel28} property.
   * @see #powerShedLevel28
   */
  public void setPowerShedLevel28(float v) { setFloat(powerShedLevel28, v, null); }

  //endregion Property "powerShedLevel28"

  //region Property "powerShedLevel29"

  /**
   * Slot for the {@code powerShedLevel29} property.
   * @see #getPowerShedLevel29
   * @see #setPowerShedLevel29
   */
  public static final Property powerShedLevel29 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel29} property.
   * @see #powerShedLevel29
   */
  public float getPowerShedLevel29() { return getFloat(powerShedLevel29); }

  /**
   * Set the {@code powerShedLevel29} property.
   * @see #powerShedLevel29
   */
  public void setPowerShedLevel29(float v) { setFloat(powerShedLevel29, v, null); }

  //endregion Property "powerShedLevel29"

  //region Property "powerShedLevel30"

  /**
   * Slot for the {@code powerShedLevel30} property.
   * @see #getPowerShedLevel30
   * @see #setPowerShedLevel30
   */
  public static final Property powerShedLevel30 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel30} property.
   * @see #powerShedLevel30
   */
  public float getPowerShedLevel30() { return getFloat(powerShedLevel30); }

  /**
   * Set the {@code powerShedLevel30} property.
   * @see #powerShedLevel30
   */
  public void setPowerShedLevel30(float v) { setFloat(powerShedLevel30, v, null); }

  //endregion Property "powerShedLevel30"

  //region Property "powerShedLevel31"

  /**
   * Slot for the {@code powerShedLevel31} property.
   * @see #getPowerShedLevel31
   * @see #setPowerShedLevel31
   */
  public static final Property powerShedLevel31 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel31} property.
   * @see #powerShedLevel31
   */
  public float getPowerShedLevel31() { return getFloat(powerShedLevel31); }

  /**
   * Set the {@code powerShedLevel31} property.
   * @see #powerShedLevel31
   */
  public void setPowerShedLevel31(float v) { setFloat(powerShedLevel31, v, null); }

  //endregion Property "powerShedLevel31"

  //region Property "powerShedLevel32"

  /**
   * Slot for the {@code powerShedLevel32} property.
   * @see #getPowerShedLevel32
   * @see #setPowerShedLevel32
   */
  public static final Property powerShedLevel32 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code powerShedLevel32} property.
   * @see #powerShedLevel32
   */
  public float getPowerShedLevel32() { return getFloat(powerShedLevel32); }

  /**
   * Set the {@code powerShedLevel32} property.
   * @see #powerShedLevel32
   */
  public void setPowerShedLevel32(float v) { setFloat(powerShedLevel32, v, null); }

  //endregion Property "powerShedLevel32"

  //region Property "thisMonthDemandPeriod1Peak"

  /**
   * Slot for the {@code thisMonthDemandPeriod1Peak} property.
   * @see #getThisMonthDemandPeriod1Peak
   * @see #setThisMonthDemandPeriod1Peak
   */
  public static final Property thisMonthDemandPeriod1Peak = newProperty(Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code thisMonthDemandPeriod1Peak} property.
   * @see #thisMonthDemandPeriod1Peak
   */
  public float getThisMonthDemandPeriod1Peak() { return getFloat(thisMonthDemandPeriod1Peak); }

  /**
   * Set the {@code thisMonthDemandPeriod1Peak} property.
   * @see #thisMonthDemandPeriod1Peak
   */
  public void setThisMonthDemandPeriod1Peak(float v) { setFloat(thisMonthDemandPeriod1Peak, v, null); }

  //endregion Property "thisMonthDemandPeriod1Peak"

  //region Property "thisMonthDemandPeriod1PeakTime"

  /**
   * Slot for the {@code thisMonthDemandPeriod1PeakTime} property.
   * @see #getThisMonthDemandPeriod1PeakTime
   * @see #setThisMonthDemandPeriod1PeakTime
   */
  public static final Property thisMonthDemandPeriod1PeakTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code thisMonthDemandPeriod1PeakTime} property.
   * @see #thisMonthDemandPeriod1PeakTime
   */
  public BAbsTime getThisMonthDemandPeriod1PeakTime() { return (BAbsTime)get(thisMonthDemandPeriod1PeakTime); }

  /**
   * Set the {@code thisMonthDemandPeriod1PeakTime} property.
   * @see #thisMonthDemandPeriod1PeakTime
   */
  public void setThisMonthDemandPeriod1PeakTime(BAbsTime v) { set(thisMonthDemandPeriod1PeakTime, v, null); }

  //endregion Property "thisMonthDemandPeriod1PeakTime"

  //region Property "thisMonthDemandPeriod2Peak"

  /**
   * Slot for the {@code thisMonthDemandPeriod2Peak} property.
   * @see #getThisMonthDemandPeriod2Peak
   * @see #setThisMonthDemandPeriod2Peak
   */
  public static final Property thisMonthDemandPeriod2Peak = newProperty(Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code thisMonthDemandPeriod2Peak} property.
   * @see #thisMonthDemandPeriod2Peak
   */
  public float getThisMonthDemandPeriod2Peak() { return getFloat(thisMonthDemandPeriod2Peak); }

  /**
   * Set the {@code thisMonthDemandPeriod2Peak} property.
   * @see #thisMonthDemandPeriod2Peak
   */
  public void setThisMonthDemandPeriod2Peak(float v) { setFloat(thisMonthDemandPeriod2Peak, v, null); }

  //endregion Property "thisMonthDemandPeriod2Peak"

  //region Property "thisMonthDemandPeriod2PeakTime"

  /**
   * Slot for the {@code thisMonthDemandPeriod2PeakTime} property.
   * @see #getThisMonthDemandPeriod2PeakTime
   * @see #setThisMonthDemandPeriod2PeakTime
   */
  public static final Property thisMonthDemandPeriod2PeakTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code thisMonthDemandPeriod2PeakTime} property.
   * @see #thisMonthDemandPeriod2PeakTime
   */
  public BAbsTime getThisMonthDemandPeriod2PeakTime() { return (BAbsTime)get(thisMonthDemandPeriod2PeakTime); }

  /**
   * Set the {@code thisMonthDemandPeriod2PeakTime} property.
   * @see #thisMonthDemandPeriod2PeakTime
   */
  public void setThisMonthDemandPeriod2PeakTime(BAbsTime v) { set(thisMonthDemandPeriod2PeakTime, v, null); }

  //endregion Property "thisMonthDemandPeriod2PeakTime"

  //region Property "thisMonthDemandPeriod3Peak"

  /**
   * Slot for the {@code thisMonthDemandPeriod3Peak} property.
   * @see #getThisMonthDemandPeriod3Peak
   * @see #setThisMonthDemandPeriod3Peak
   */
  public static final Property thisMonthDemandPeriod3Peak = newProperty(Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code thisMonthDemandPeriod3Peak} property.
   * @see #thisMonthDemandPeriod3Peak
   */
  public float getThisMonthDemandPeriod3Peak() { return getFloat(thisMonthDemandPeriod3Peak); }

  /**
   * Set the {@code thisMonthDemandPeriod3Peak} property.
   * @see #thisMonthDemandPeriod3Peak
   */
  public void setThisMonthDemandPeriod3Peak(float v) { setFloat(thisMonthDemandPeriod3Peak, v, null); }

  //endregion Property "thisMonthDemandPeriod3Peak"

  //region Property "thisMonthDemandPeriod3PeakTime"

  /**
   * Slot for the {@code thisMonthDemandPeriod3PeakTime} property.
   * @see #getThisMonthDemandPeriod3PeakTime
   * @see #setThisMonthDemandPeriod3PeakTime
   */
  public static final Property thisMonthDemandPeriod3PeakTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code thisMonthDemandPeriod3PeakTime} property.
   * @see #thisMonthDemandPeriod3PeakTime
   */
  public BAbsTime getThisMonthDemandPeriod3PeakTime() { return (BAbsTime)get(thisMonthDemandPeriod3PeakTime); }

  /**
   * Set the {@code thisMonthDemandPeriod3PeakTime} property.
   * @see #thisMonthDemandPeriod3PeakTime
   */
  public void setThisMonthDemandPeriod3PeakTime(BAbsTime v) { set(thisMonthDemandPeriod3PeakTime, v, null); }

  //endregion Property "thisMonthDemandPeriod3PeakTime"

  //region Property "previousMonthDemandPeriod1Peak"

  /**
   * Slot for the {@code previousMonthDemandPeriod1Peak} property.
   * @see #getPreviousMonthDemandPeriod1Peak
   * @see #setPreviousMonthDemandPeriod1Peak
   */
  public static final Property previousMonthDemandPeriod1Peak = newProperty(Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code previousMonthDemandPeriod1Peak} property.
   * @see #previousMonthDemandPeriod1Peak
   */
  public float getPreviousMonthDemandPeriod1Peak() { return getFloat(previousMonthDemandPeriod1Peak); }

  /**
   * Set the {@code previousMonthDemandPeriod1Peak} property.
   * @see #previousMonthDemandPeriod1Peak
   */
  public void setPreviousMonthDemandPeriod1Peak(float v) { setFloat(previousMonthDemandPeriod1Peak, v, null); }

  //endregion Property "previousMonthDemandPeriod1Peak"

  //region Property "previousMonthDemandPeriod1PeakTime"

  /**
   * Slot for the {@code previousMonthDemandPeriod1PeakTime} property.
   * @see #getPreviousMonthDemandPeriod1PeakTime
   * @see #setPreviousMonthDemandPeriod1PeakTime
   */
  public static final Property previousMonthDemandPeriod1PeakTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code previousMonthDemandPeriod1PeakTime} property.
   * @see #previousMonthDemandPeriod1PeakTime
   */
  public BAbsTime getPreviousMonthDemandPeriod1PeakTime() { return (BAbsTime)get(previousMonthDemandPeriod1PeakTime); }

  /**
   * Set the {@code previousMonthDemandPeriod1PeakTime} property.
   * @see #previousMonthDemandPeriod1PeakTime
   */
  public void setPreviousMonthDemandPeriod1PeakTime(BAbsTime v) { set(previousMonthDemandPeriod1PeakTime, v, null); }

  //endregion Property "previousMonthDemandPeriod1PeakTime"

  //region Property "previousMonthDemandPeriod2Peak"

  /**
   * Slot for the {@code previousMonthDemandPeriod2Peak} property.
   * @see #getPreviousMonthDemandPeriod2Peak
   * @see #setPreviousMonthDemandPeriod2Peak
   */
  public static final Property previousMonthDemandPeriod2Peak = newProperty(Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code previousMonthDemandPeriod2Peak} property.
   * @see #previousMonthDemandPeriod2Peak
   */
  public float getPreviousMonthDemandPeriod2Peak() { return getFloat(previousMonthDemandPeriod2Peak); }

  /**
   * Set the {@code previousMonthDemandPeriod2Peak} property.
   * @see #previousMonthDemandPeriod2Peak
   */
  public void setPreviousMonthDemandPeriod2Peak(float v) { setFloat(previousMonthDemandPeriod2Peak, v, null); }

  //endregion Property "previousMonthDemandPeriod2Peak"

  //region Property "previousMonthDemandPeriod2PeakTime"

  /**
   * Slot for the {@code previousMonthDemandPeriod2PeakTime} property.
   * @see #getPreviousMonthDemandPeriod2PeakTime
   * @see #setPreviousMonthDemandPeriod2PeakTime
   */
  public static final Property previousMonthDemandPeriod2PeakTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code previousMonthDemandPeriod2PeakTime} property.
   * @see #previousMonthDemandPeriod2PeakTime
   */
  public BAbsTime getPreviousMonthDemandPeriod2PeakTime() { return (BAbsTime)get(previousMonthDemandPeriod2PeakTime); }

  /**
   * Set the {@code previousMonthDemandPeriod2PeakTime} property.
   * @see #previousMonthDemandPeriod2PeakTime
   */
  public void setPreviousMonthDemandPeriod2PeakTime(BAbsTime v) { set(previousMonthDemandPeriod2PeakTime, v, null); }

  //endregion Property "previousMonthDemandPeriod2PeakTime"

  //region Property "previousMonthDemandPeriod3Peak"

  /**
   * Slot for the {@code previousMonthDemandPeriod3Peak} property.
   * @see #getPreviousMonthDemandPeriod3Peak
   * @see #setPreviousMonthDemandPeriod3Peak
   */
  public static final Property previousMonthDemandPeriod3Peak = newProperty(Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code previousMonthDemandPeriod3Peak} property.
   * @see #previousMonthDemandPeriod3Peak
   */
  public float getPreviousMonthDemandPeriod3Peak() { return getFloat(previousMonthDemandPeriod3Peak); }

  /**
   * Set the {@code previousMonthDemandPeriod3Peak} property.
   * @see #previousMonthDemandPeriod3Peak
   */
  public void setPreviousMonthDemandPeriod3Peak(float v) { setFloat(previousMonthDemandPeriod3Peak, v, null); }

  //endregion Property "previousMonthDemandPeriod3Peak"

  //region Property "previousMonthDemandPeriod3PeakTime"

  /**
   * Slot for the {@code previousMonthDemandPeriod3PeakTime} property.
   * @see #getPreviousMonthDemandPeriod3PeakTime
   * @see #setPreviousMonthDemandPeriod3PeakTime
   */
  public static final Property previousMonthDemandPeriod3PeakTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code previousMonthDemandPeriod3PeakTime} property.
   * @see #previousMonthDemandPeriod3PeakTime
   */
  public BAbsTime getPreviousMonthDemandPeriod3PeakTime() { return (BAbsTime)get(previousMonthDemandPeriod3PeakTime); }

  /**
   * Set the {@code previousMonthDemandPeriod3PeakTime} property.
   * @see #previousMonthDemandPeriod3PeakTime
   */
  public void setPreviousMonthDemandPeriod3PeakTime(BAbsTime v) { set(previousMonthDemandPeriod3PeakTime, v, null); }

  //endregion Property "previousMonthDemandPeriod3PeakTime"

  //region Property "projectedDemandAverage"

  /**
   * Slot for the {@code projectedDemandAverage} property.
   * @see #getProjectedDemandAverage
   * @see #setProjectedDemandAverage
   */
  public static final Property projectedDemandAverage = newProperty(Flags.TRANSIENT | Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code projectedDemandAverage} property.
   * @see #projectedDemandAverage
   */
  public float getProjectedDemandAverage() { return getFloat(projectedDemandAverage); }

  /**
   * Set the {@code projectedDemandAverage} property.
   * @see #projectedDemandAverage
   */
  public void setProjectedDemandAverage(float v) { setFloat(projectedDemandAverage, v, null); }

  //endregion Property "projectedDemandAverage"

  //region Property "maxShedLevel"

  /**
   * Slot for the {@code maxShedLevel} property.
   * @see #getMaxShedLevel
   * @see #setMaxShedLevel
   */
  public static final Property maxShedLevel = newProperty(Flags.TRANSIENT | Flags.READONLY, 32, null);

  /**
   * Get the {@code maxShedLevel} property.
   * @see #maxShedLevel
   */
  public int getMaxShedLevel() { return getInt(maxShedLevel); }

  /**
   * Set the {@code maxShedLevel} property.
   * @see #maxShedLevel
   */
  public void setMaxShedLevel(int v) { setInt(maxShedLevel, v, null); }

  //endregion Property "maxShedLevel"

  //region Action "calculate"

  /**
   * Slot for the {@code calculate} action.
   * @see #calculate()
   */
  public static final Action calculate = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code calculate} action.
   * @see #calculate
   */
  public void calculate() { invoke(calculate, null, null); }

  //endregion Action "calculate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BElectricalDemandLimit.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Initialization  /  Cleanup
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {

    if(ticket != null)
      ticket.cancel();
    ticket = Clock.schedule(this, BRelTime.makeMinutes(1), calculate, null);

    super.started();
	  if( !Sys.atSteadyState() )
		  return;


  }

  public void stopped()
    throws Exception
  {
    if(ticket != null)
      ticket.cancel();
    super.stopped();
  }


  public void changed(Property property, Context context) 
  {
  	super.changed(property, context);
    if( !Sys.atSteadyState() || !isRunning() )
	    return;
    if(property.equals(demandInterval))
    {
    	if(getDemandInterval() != 15 && getDemandInterval() != 30)
    		setDemandInterval(15);
    }
    doCalculate();
  }

  public BFacets getSlotFacets(Slot slot)
  {
    return super.getSlotFacets(slot);
  }

  public void doCalculate()
  {
    if(ticket != null)
      ticket.cancel();
    ticket = Clock.schedule(this, BRelTime.makeMinutes(1), calculate, null);
    
    BStatusNumeric powerValue = getPowerInput();
    BAbsTime now = null;
    try { now = (BAbsTime)get("clockIn"); } catch(Exception e){ now = null; }
    if(now == null)
    	now = Clock.time();
    
    //BAbsTime now = getTimeIn();
    //
    // Perform Demand Monitoring function
    //
    if( !powerValue.getStatus().isAlarm() )	// is demand input in alarm?
    {
      // What to do, if anything?  Value is outside limits, but input is probably reliable.
    }
    
    //
    // Try to be a little more accurate than just using minutely readings
    //
    if (!powerValue.getStatus().isValid())	// only use reliable input
    {
      mode = 2;
      numberOfReadings = 0;
      sumOfReadings = 0;
    }
    else
    {
    	if(mode == 2)
    		mode = 0;
      numberOfReadings = numberOfReadings + 1;
      sumOfReadings = sumOfReadings + powerValue.getValue();
    }
    
    //
    // Check for synch input
    //
    //if (syncInput.status.fault == true) and (syncInput.status.outOfService != true)
    //  syncPulseReceived = true
      // What else should be done?
    //endif
    
    //
    // Perform Demand Monitoring function
    //
    if (getPredictionEnabled().getValue() != true)	// process only if prediction is enabled
    {
      shedLevels = 0 ;
      mode = 1 ;
    }
    else if (now.getMinute() != lastMinute)	// evaluate shed/restore only once per minute
    {
      lastMinute = now.getMinute();
      if( mode != 2 )			// skip calculations if input is unreliable as determined above
      {
        mode = 0;
        //
        // Roll out oldest demand reading, insert newest
        //
        for(int i = 29; i >= 1; i-- )		// work from bottom to top of table
          demandMinute[i] = demandMinute[i-1];
        
        demandMinute[0] = (float)(sumOfReadings / numberOfReadings);
        //
        // Determine position within demand interval
        //
        if ((getPercentIntervalElapsed() > 0) && (getPercentIntervalElapsed() < 100))	// percentIntervalElapsed represents the percentage of demand period that is assumed to have elapsed 
          percentElapsed = getPercentIntervalElapsed();
        else
          percentElapsed = 75;		// default is 75% of interval has elapsed
        if ((getDemandInterval() == 15) || (getDemandInterval() == 30))	// interval must be 15 or 30 minutes
        {
          minutesElapsed = (getDemandInterval() * percentElapsed) / 100;
          minutesRemaining = getDemandInterval() - minutesElapsed;
        }
        else
        {
          minutesElapsed = (15 * percentElapsed) / 100;		// default interval length is 15 minutes
          minutesRemaining = 15 - minutesElapsed;
        }
        //
        // Calculate demand projection
        //

        projectedIntervalTotal = demandMinute[0] * minutesRemaining;
        for(int i = 1; i <= minutesElapsed; i++)
          projectedIntervalTotal = projectedIntervalTotal + demandMinute[i];
        demandAverage = (float)(projectedIntervalTotal / (minutesElapsed + minutesRemaining));	// can't use demandInterval because it may not be set
        setProjectedDemandAverage(demandAverage);
        //
        // Determine period of day (1,2 or 3) and select control limit
        //
        long nowMillis = now.getTimeOfDayMillis();
        periodOfDay = 1;
        if ( ( (nowMillis >= getDemandPeriod1Start().getMillis()) && 
               (nowMillis <  getDemandPeriod2Start().getMillis())    ) || 
             (getDemandPeriod2Start().getMillis() == 0l)                  )
        {
          periodOfDay = 1;
        }
        else if ( ( (nowMillis >= getDemandPeriod2Start().getMillis()) && 
                    (nowMillis <  getDemandPeriod3Start().getMillis())    ) || 
                  (getDemandPeriod3Start().getMillis() == 0l)                  )
        {
        	periodOfDay = 2;
        }
        else
        {
        	periodOfDay = 3;
        }
//        System.out.println(now + ": edl periodOfDay = " + periodOfDay);
        //if this is this billing day & it is 12 noon or after roll peaks from this to previous month;
        if (getBillingStartDay() == now.getDay() )
        {
//        	System.out.println("today is the billing start day");
        	if(  now.getHour() >= 12 && !rollOverComplete )
      	  {
//          	System.out.println("it's after 12 so rollover");
          	setPreviousMonthDemandPeriod1Peak(getThisMonthDemandPeriod1Peak());
	          setPreviousMonthDemandPeriod1PeakTime(getThisMonthDemandPeriod1PeakTime());
	          setThisMonthDemandPeriod1Peak(0.0f);
	          setThisMonthDemandPeriod1PeakTime(BAbsTime.NULL);
	          
	          setPreviousMonthDemandPeriod2Peak(getThisMonthDemandPeriod2Peak());
	          setPreviousMonthDemandPeriod2PeakTime(getThisMonthDemandPeriod2PeakTime());
	          setThisMonthDemandPeriod2Peak(0.0f);
	          setThisMonthDemandPeriod2PeakTime(BAbsTime.NULL);
	          
	          setPreviousMonthDemandPeriod3Peak(getThisMonthDemandPeriod3Peak());
	          setPreviousMonthDemandPeriod3PeakTime(getThisMonthDemandPeriod3PeakTime());
	          setThisMonthDemandPeriod3Peak(0.0f);
	          setThisMonthDemandPeriod3PeakTime(BAbsTime.NULL);
	          
	          rollOverComplete = true;
      	  }
        }
        else
        	rollOverComplete = false;
        
        switch(periodOfDay)
        {
        case 1:
          demandLimit = getDemandLimitPeriod1();
          if( demandAverage > getThisMonthDemandPeriod1Peak() )
          {
            setThisMonthDemandPeriod1Peak(demandAverage);
            setThisMonthDemandPeriod1PeakTime(now);
          }
        	break;
        case 2:
          demandLimit = getDemandLimitPeriod2();
          if( demandAverage > getThisMonthDemandPeriod2Peak() )
          {
            setThisMonthDemandPeriod2Peak(demandAverage);
            setThisMonthDemandPeriod2PeakTime(now);
          }
        	break;
        case 3:
          demandLimit = getDemandLimitPeriod3();
          if( demandAverage > getThisMonthDemandPeriod3Peak() )
          {
            setThisMonthDemandPeriod3Peak(demandAverage);
            setThisMonthDemandPeriod3PeakTime(now);
          }
        	break;
        }
        
        
    
        if (demandLimit > 0)	// check to make sure the control limit has been entered
        {
          //
          // Initialize local power table for potential shed or restore iteration
          //
          if (demandAverage > demandLimit || shedLevels > 0)
          {
            powerTable[0]  = 0; 		// zero always returned when shedding not active
            powerTable[1]  = getPowerShedLevel1 ();
            powerTable[2]  = getPowerShedLevel2 ();
            powerTable[3]  = getPowerShedLevel3 ();
            powerTable[4]  = getPowerShedLevel4 ();
            powerTable[5]  = getPowerShedLevel5 ();
            powerTable[6]  = getPowerShedLevel6 ();
            powerTable[7]  = getPowerShedLevel7 ();
            powerTable[8]  = getPowerShedLevel8 ();
            powerTable[9]  = getPowerShedLevel9 ();
            powerTable[10] = getPowerShedLevel10();
            powerTable[11] = getPowerShedLevel11();
            powerTable[12] = getPowerShedLevel12();
            powerTable[13] = getPowerShedLevel13();
            powerTable[14] = getPowerShedLevel14();
            powerTable[15] = getPowerShedLevel15();
            powerTable[16] = getPowerShedLevel16();
            powerTable[17] = getPowerShedLevel17();
            powerTable[18] = getPowerShedLevel18();
            powerTable[19] = getPowerShedLevel19();
            powerTable[20] = getPowerShedLevel20();
            powerTable[21] = getPowerShedLevel21();
            powerTable[22] = getPowerShedLevel22();
            powerTable[23] = getPowerShedLevel23();
            powerTable[24] = getPowerShedLevel24();
            powerTable[25] = getPowerShedLevel25();
            powerTable[26] = getPowerShedLevel26();
            powerTable[27] = getPowerShedLevel27();
            powerTable[28] = getPowerShedLevel28();
            powerTable[29] = getPowerShedLevel29();
            powerTable[30] = getPowerShedLevel30();
            powerTable[31] = getPowerShedLevel31();
            powerTable[32] = getPowerShedLevel32();
          }
          
          //
          // Shed or restore determination
          //
          if (demandAverage > demandLimit)
          {
            if (getRotateLevel() <= 0 || getRotateLevel() >= getMaxShedLevel())
              numberOfFixedLevels = getMaxShedLevel();
            else
              numberOfFixedLevels = getRotateLevel() - 1;
      
            //
            // Determine how many levels to shed
            //
            targetIntervalTotal = demandLimit * (minutesElapsed + minutesRemaining);
            powerChange = (projectedIntervalTotal - targetIntervalTotal) / minutesRemaining;
            while ((powerChange > 0) && (shedLevels < numberOfFixedLevels))
            {
              shedLevels = shedLevels + 1;
              powerChange = powerChange - powerTable[shedLevels];
            }
            if (shedLevels < numberOfFixedLevels)
              mode = 3;
            else
              mode = 4;
          }
          else if ( demandAverage <= demandLimit && shedLevels > 0 )
          {
            if (demandAverage < (demandLimit - getDemandLimitingDeadband()))
            {
              mode = 5;
              targetIntervalTotal = (demandLimit - getDemandLimitingDeadband()) * (minutesElapsed + minutesRemaining);
              powerChange = (targetIntervalTotal - projectedIntervalTotal) / minutesRemaining;
              while (shedLevels > 0)
              {
                powerChange = powerChange - powerTable[shedLevels];
                if( powerChange < 0 )
                  break;
                shedLevels = shedLevels - 1;
              }
              if (shedLevels == 0)
    	          mode = 7;
    	      }
            else
    	        mode = 6;
          }
        }
        else
          shedLevels = 0;
        
      }
      numberOfReadings = 0;
      sumOfReadings = 0;
    }

    //
    // Inform operator of what is happening
    //
    switch(mode)
    {
    case 1: setMessage(PREDICTION_NOT_ENABLED_MSG); break;
    case 2: setMessage(DEMAND_UNAVAILABLE_MSG); break;
    case 3: setMessage(SHEDDING_REQUIRED_MSG + ' ' + PROJECTED_DEMAND_MSG + ' ' + demandAverage); break;
    case 4: setMessage(MANUAL_INTERVENTION_MSG + ' ' + PROJECTED_DEMAND_MSG + ' ' + demandAverage); break;
    case 5: setMessage(RESTORATION_MSG + ' ' + PROJECTED_DEMAND_MSG + ' ' + demandAverage); break;
    case 6: setMessage(NOT_ENOUGH_POWER_MSG); break;
    case 7: setMessage(ALL_RESTORED_MSG + ' ' + PROJECTED_DEMAND_MSG + ' ' + demandAverage); break;
    default: setMessage(PROJECTED_DEMAND_MSG + ' ' + demandAverage);
    }
    
    //
    // Set level and priority
    //
    getShedOut().setValue(shedLevels);
    getShedOut().setStatus(getPowerInput().getStatus());
  }

////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////

  boolean syncPulseReceived;		// flag for indicating beginning of next fixed interval window
  
  double powerChange;			// energy that needs to be shed or can be restored this minute
  double demandLimit;			// contains demand limit for control comparison
  float[] demandMinute = new float[30];			// readings for last thirty minutes
                              // demandMinute[0] is most recent, demandMinute[29] is oldest
  double numberOfReadings;			// number of power readings taken during the last minute
  float[] powerTable = new float[33];	// copy of the power level properties that is used for shed/restore iteration
  double projectedIntervalTotal;	      // sum of elapsed minutes' power readings + (this * minutesRemaining)
  double sumOfReadings;			          // sum of power readings taken during the last minute
  double targetIntervalTotal;		      // result of (demandLImit * demandInterval) or (demandLimit-demandDeadband * demandInterval)
  float demandAverage;		      // projected demand average working variable
  
  int i;					                    // loop counter
  int shedLevels;					            // calculated shed level
  int numberOfFixedLevels;			// number of fixed priority levels (set to maxShedLevels or getRotateLevel()-1)
  int lastMinute;				// controls processing frequency
  int minutesElapsed;			// minutes of demand interval elapsed based on percent
  int minutesRemaining;			// minutes of demand interval remaining
  int mode;				// message control
  int percentElapsed;			// working percentage penetration into interval (default is 75%)
  int periodOfDay;				// index into period of day (1-3)

  boolean rollOverComplete = false;
  //
  // Messages
  //

  protected static String PROJECTED_DEMAND_MSG       = Lexicon.make("kitControl").getText("electricalDemandLimit.projectedDemand");
  protected static String PREDICTION_NOT_ENABLED_MSG = Lexicon.make("kitControl").getText("electricalDemandLimit.predictionNotEnabled");
  protected static String DEMAND_UNAVAILABLE_MSG     = Lexicon.make("kitControl").getText("electricalDemandLimit.demandInputUnavailable");
  protected static String SHEDDING_REQUIRED_MSG      = Lexicon.make("kitControl").getText("electricalDemandLimit.sheddingRequired");
  protected static String MANUAL_INTERVENTION_MSG    = Lexicon.make("kitControl").getText("electricalDemandLimit.manualInterventionRequired");
  protected static String RESTORATION_MSG            = Lexicon.make("kitControl").getText("electricalDemandLimit.restorationInProgress");
  protected static String NOT_ENOUGH_POWER_MSG       = Lexicon.make("kitControl").getText("electricalDemandLimit.notEnoughPowerForRestoration");
  protected static String ALL_RESTORED_MSG           = Lexicon.make("kitControl").getText("electricalDemandLimit.allRestored");

  Clock.Ticket ticket = null;

  static long NOON_MILLIS = 1000 * 60 * 60 * 12;

}
