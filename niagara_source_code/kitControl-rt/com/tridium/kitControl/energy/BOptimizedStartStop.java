/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.energy;

import java.text.DecimalFormat;

import javax.baja.log.Log;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/** Optimized Start Stop object
 *
 *   This object uses a space temperature input and area characteristics to 
 *   calculate a leadtime in advance of a scheduled event that allows an 
 *   early start to achieve a specified temperature range by occupancy time 
 *   or an early stop operation without sacrificing the temperature range by 
 *   unoccupancy time.  The actual area response is analyzed and weighted 
 *   adjustments are made to the calculation parameters accordingly. 
 *
 *   Calculation is performed at 15 seconds after top of minute.
 * 
 *   Only one optimized start sequence is performed per day.  Multiple stop 
 *   operations may be allowed by adjusting the "earliestStopTime" property. 
 * 
 * @author    Andy Saunders
 * @creation  18 Mar 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraProperty(
  name = "heatCoolMode",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.TRANSIENT | Flags.SUMMARY,
  facets = @Facet("BFacets.makeBoolean(\"coolMode\", \"heatMode\")")
)
@NiagaraProperty(
  name = "parameterResetTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "startEnable",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "stopEnable",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "scheduleStatus",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "nextEventTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "nextEventValue",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "outsideTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "spaceTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "startTimeCommand",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "stopTimeCommand",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "message",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\")",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "upperComfortLimit",
  type = "float",
  defaultValue = "77.0f"
)
@NiagaraProperty(
  name = "lowerComfortLimit",
  type = "float",
  defaultValue = "68.0f"
)
@NiagaraProperty(
  name = "dynamicParameterAdjust",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "oldParameterMultiplier",
  type = "int",
  defaultValue = "2"
)
@NiagaraProperty(
  name = "earliestStartTime",
  type = "BTime",
  defaultValue = "BTime.make(0, 0, 10)"
)
@NiagaraProperty(
  name = "earliestStopTime",
  type = "BTime",
  defaultValue = "BTime.make(16, 0, 0)"
)
@NiagaraProperty(
  name = "drifttimePerDegreeCoolingUserDefined",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "drifttimePerDegreeHeatingUserDefined",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "runtimePerDegreeCoolingUserDefined",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "runtimePerDegreeHeatingUserDefined",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "drifttimePerDegreeCooling",
  type = "float",
  defaultValue = "10.0f"
)
@NiagaraProperty(
  name = "drifttimePerDegreeHeating",
  type = "float",
  defaultValue = "10.0f"
)
@NiagaraProperty(
  name = "runtimePerDegreeCooling",
  type = "float",
  defaultValue = "10.0f"
)
@NiagaraProperty(
  name = "runtimePerDegreeHeating",
  type = "float",
  defaultValue = "10.0f"
)
@NiagaraProperty(
  name = "lastStartTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "lastStopTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outsideTempAtBeginning",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "spaceTempAtBeginning",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "calculatedCommandTime",
  type = "BTime",
  defaultValue = "BTime.DEFAULT",
  flags = Flags.TRANSIENT | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "programMode",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.DEFAULT_ON_CLONE
)
@NiagaraAction(
  name = "startTimeTrigger",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "stopTimeTrigger",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "calculate",
  flags = Flags.HIDDEN
)
public class BOptimizedStartStop
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BOptimizedStartStop(2873081201)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "heatCoolMode"

  /**
   * Slot for the {@code heatCoolMode} property.
   * @see #getHeatCoolMode
   * @see #setHeatCoolMode
   */
  public static final Property heatCoolMode = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(false), BFacets.makeBoolean("coolMode", "heatMode"));

  /**
   * Get the {@code heatCoolMode} property.
   * @see #heatCoolMode
   */
  public BStatusBoolean getHeatCoolMode() { return (BStatusBoolean)get(heatCoolMode); }

  /**
   * Set the {@code heatCoolMode} property.
   * @see #heatCoolMode
   */
  public void setHeatCoolMode(BStatusBoolean v) { set(heatCoolMode, v, null); }

  //endregion Property "heatCoolMode"

  //region Property "parameterResetTime"

  /**
   * Slot for the {@code parameterResetTime} property.
   * @see #getParameterResetTime
   * @see #setParameterResetTime
   */
  public static final Property parameterResetTime = newProperty(Flags.TRANSIENT | Flags.SUMMARY, BAbsTime.NULL, null);

  /**
   * Get the {@code parameterResetTime} property.
   * @see #parameterResetTime
   */
  public BAbsTime getParameterResetTime() { return (BAbsTime)get(parameterResetTime); }

  /**
   * Set the {@code parameterResetTime} property.
   * @see #parameterResetTime
   */
  public void setParameterResetTime(BAbsTime v) { set(parameterResetTime, v, null); }

  //endregion Property "parameterResetTime"

  //region Property "startEnable"

  /**
   * Slot for the {@code startEnable} property.
   * @see #getStartEnable
   * @see #setStartEnable
   */
  public static final Property startEnable = newProperty(Flags.SUMMARY, new BStatusBoolean(false), null);

  /**
   * Get the {@code startEnable} property.
   * @see #startEnable
   */
  public BStatusBoolean getStartEnable() { return (BStatusBoolean)get(startEnable); }

  /**
   * Set the {@code startEnable} property.
   * @see #startEnable
   */
  public void setStartEnable(BStatusBoolean v) { set(startEnable, v, null); }

  //endregion Property "startEnable"

  //region Property "stopEnable"

  /**
   * Slot for the {@code stopEnable} property.
   * @see #getStopEnable
   * @see #setStopEnable
   */
  public static final Property stopEnable = newProperty(Flags.SUMMARY, new BStatusBoolean(false), null);

  /**
   * Get the {@code stopEnable} property.
   * @see #stopEnable
   */
  public BStatusBoolean getStopEnable() { return (BStatusBoolean)get(stopEnable); }

  /**
   * Set the {@code stopEnable} property.
   * @see #stopEnable
   */
  public void setStopEnable(BStatusBoolean v) { set(stopEnable, v, null); }

  //endregion Property "stopEnable"

  //region Property "scheduleStatus"

  /**
   * Slot for the {@code scheduleStatus} property.
   * @see #getScheduleStatus
   * @see #setScheduleStatus
   */
  public static final Property scheduleStatus = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(false), null);

  /**
   * Get the {@code scheduleStatus} property.
   * @see #scheduleStatus
   */
  public BStatusBoolean getScheduleStatus() { return (BStatusBoolean)get(scheduleStatus); }

  /**
   * Set the {@code scheduleStatus} property.
   * @see #scheduleStatus
   */
  public void setScheduleStatus(BStatusBoolean v) { set(scheduleStatus, v, null); }

  //endregion Property "scheduleStatus"

  //region Property "nextEventTime"

  /**
   * Slot for the {@code nextEventTime} property.
   * @see #getNextEventTime
   * @see #setNextEventTime
   */
  public static final Property nextEventTime = newProperty(Flags.TRANSIENT | Flags.SUMMARY, BAbsTime.NULL, null);

  /**
   * Get the {@code nextEventTime} property.
   * @see #nextEventTime
   */
  public BAbsTime getNextEventTime() { return (BAbsTime)get(nextEventTime); }

  /**
   * Set the {@code nextEventTime} property.
   * @see #nextEventTime
   */
  public void setNextEventTime(BAbsTime v) { set(nextEventTime, v, null); }

  //endregion Property "nextEventTime"

  //region Property "nextEventValue"

  /**
   * Slot for the {@code nextEventValue} property.
   * @see #getNextEventValue
   * @see #setNextEventValue
   */
  public static final Property nextEventValue = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(false), null);

  /**
   * Get the {@code nextEventValue} property.
   * @see #nextEventValue
   */
  public BStatusBoolean getNextEventValue() { return (BStatusBoolean)get(nextEventValue); }

  /**
   * Set the {@code nextEventValue} property.
   * @see #nextEventValue
   */
  public void setNextEventValue(BStatusBoolean v) { set(nextEventValue, v, null); }

  //endregion Property "nextEventValue"

  //region Property "outsideTemp"

  /**
   * Slot for the {@code outsideTemp} property.
   * @see #getOutsideTemp
   * @see #setOutsideTemp
   */
  public static final Property outsideTemp = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code outsideTemp} property.
   * @see #outsideTemp
   */
  public BStatusNumeric getOutsideTemp() { return (BStatusNumeric)get(outsideTemp); }

  /**
   * Set the {@code outsideTemp} property.
   * @see #outsideTemp
   */
  public void setOutsideTemp(BStatusNumeric v) { set(outsideTemp, v, null); }

  //endregion Property "outsideTemp"

  //region Property "spaceTemp"

  /**
   * Slot for the {@code spaceTemp} property.
   * @see #getSpaceTemp
   * @see #setSpaceTemp
   */
  public static final Property spaceTemp = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code spaceTemp} property.
   * @see #spaceTemp
   */
  public BStatusNumeric getSpaceTemp() { return (BStatusNumeric)get(spaceTemp); }

  /**
   * Set the {@code spaceTemp} property.
   * @see #spaceTemp
   */
  public void setSpaceTemp(BStatusNumeric v) { set(spaceTemp, v, null); }

  //endregion Property "spaceTemp"

  //region Property "startTimeCommand"

  /**
   * Slot for the {@code startTimeCommand} property.
   * @see #getStartTimeCommand
   * @see #setStartTimeCommand
   */
  public static final Property startTimeCommand = newProperty(Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code startTimeCommand} property.
   * @see #startTimeCommand
   */
  public BStatusBoolean getStartTimeCommand() { return (BStatusBoolean)get(startTimeCommand); }

  /**
   * Set the {@code startTimeCommand} property.
   * @see #startTimeCommand
   */
  public void setStartTimeCommand(BStatusBoolean v) { set(startTimeCommand, v, null); }

  //endregion Property "startTimeCommand"

  //region Property "stopTimeCommand"

  /**
   * Slot for the {@code stopTimeCommand} property.
   * @see #getStopTimeCommand
   * @see #setStopTimeCommand
   */
  public static final Property stopTimeCommand = newProperty(Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code stopTimeCommand} property.
   * @see #stopTimeCommand
   */
  public BStatusBoolean getStopTimeCommand() { return (BStatusBoolean)get(stopTimeCommand); }

  /**
   * Set the {@code stopTimeCommand} property.
   * @see #stopTimeCommand
   */
  public void setStopTimeCommand(BStatusBoolean v) { set(stopTimeCommand, v, null); }

  //endregion Property "stopTimeCommand"

  //region Property "message"

  /**
   * Slot for the {@code message} property.
   * @see #getMessage
   * @see #setMessage
   */
  public static final Property message = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusString(""), null);

  /**
   * Get the {@code message} property.
   * @see #message
   */
  public BStatusString getMessage() { return (BStatusString)get(message); }

  /**
   * Set the {@code message} property.
   * @see #message
   */
  public void setMessage(BStatusString v) { set(message, v, null); }

  //endregion Property "message"

  //region Property "upperComfortLimit"

  /**
   * Slot for the {@code upperComfortLimit} property.
   * @see #getUpperComfortLimit
   * @see #setUpperComfortLimit
   */
  public static final Property upperComfortLimit = newProperty(0, 77.0f, null);

  /**
   * Get the {@code upperComfortLimit} property.
   * @see #upperComfortLimit
   */
  public float getUpperComfortLimit() { return getFloat(upperComfortLimit); }

  /**
   * Set the {@code upperComfortLimit} property.
   * @see #upperComfortLimit
   */
  public void setUpperComfortLimit(float v) { setFloat(upperComfortLimit, v, null); }

  //endregion Property "upperComfortLimit"

  //region Property "lowerComfortLimit"

  /**
   * Slot for the {@code lowerComfortLimit} property.
   * @see #getLowerComfortLimit
   * @see #setLowerComfortLimit
   */
  public static final Property lowerComfortLimit = newProperty(0, 68.0f, null);

  /**
   * Get the {@code lowerComfortLimit} property.
   * @see #lowerComfortLimit
   */
  public float getLowerComfortLimit() { return getFloat(lowerComfortLimit); }

  /**
   * Set the {@code lowerComfortLimit} property.
   * @see #lowerComfortLimit
   */
  public void setLowerComfortLimit(float v) { setFloat(lowerComfortLimit, v, null); }

  //endregion Property "lowerComfortLimit"

  //region Property "dynamicParameterAdjust"

  /**
   * Slot for the {@code dynamicParameterAdjust} property.
   * @see #getDynamicParameterAdjust
   * @see #setDynamicParameterAdjust
   */
  public static final Property dynamicParameterAdjust = newProperty(0, true, null);

  /**
   * Get the {@code dynamicParameterAdjust} property.
   * @see #dynamicParameterAdjust
   */
  public boolean getDynamicParameterAdjust() { return getBoolean(dynamicParameterAdjust); }

  /**
   * Set the {@code dynamicParameterAdjust} property.
   * @see #dynamicParameterAdjust
   */
  public void setDynamicParameterAdjust(boolean v) { setBoolean(dynamicParameterAdjust, v, null); }

  //endregion Property "dynamicParameterAdjust"

  //region Property "oldParameterMultiplier"

  /**
   * Slot for the {@code oldParameterMultiplier} property.
   * @see #getOldParameterMultiplier
   * @see #setOldParameterMultiplier
   */
  public static final Property oldParameterMultiplier = newProperty(0, 2, null);

  /**
   * Get the {@code oldParameterMultiplier} property.
   * @see #oldParameterMultiplier
   */
  public int getOldParameterMultiplier() { return getInt(oldParameterMultiplier); }

  /**
   * Set the {@code oldParameterMultiplier} property.
   * @see #oldParameterMultiplier
   */
  public void setOldParameterMultiplier(int v) { setInt(oldParameterMultiplier, v, null); }

  //endregion Property "oldParameterMultiplier"

  //region Property "earliestStartTime"

  /**
   * Slot for the {@code earliestStartTime} property.
   * @see #getEarliestStartTime
   * @see #setEarliestStartTime
   */
  public static final Property earliestStartTime = newProperty(0, BTime.make(0, 0, 10), null);

  /**
   * Get the {@code earliestStartTime} property.
   * @see #earliestStartTime
   */
  public BTime getEarliestStartTime() { return (BTime)get(earliestStartTime); }

  /**
   * Set the {@code earliestStartTime} property.
   * @see #earliestStartTime
   */
  public void setEarliestStartTime(BTime v) { set(earliestStartTime, v, null); }

  //endregion Property "earliestStartTime"

  //region Property "earliestStopTime"

  /**
   * Slot for the {@code earliestStopTime} property.
   * @see #getEarliestStopTime
   * @see #setEarliestStopTime
   */
  public static final Property earliestStopTime = newProperty(0, BTime.make(16, 0, 0), null);

  /**
   * Get the {@code earliestStopTime} property.
   * @see #earliestStopTime
   */
  public BTime getEarliestStopTime() { return (BTime)get(earliestStopTime); }

  /**
   * Set the {@code earliestStopTime} property.
   * @see #earliestStopTime
   */
  public void setEarliestStopTime(BTime v) { set(earliestStopTime, v, null); }

  //endregion Property "earliestStopTime"

  //region Property "drifttimePerDegreeCoolingUserDefined"

  /**
   * Slot for the {@code drifttimePerDegreeCoolingUserDefined} property.
   * @see #getDrifttimePerDegreeCoolingUserDefined
   * @see #setDrifttimePerDegreeCoolingUserDefined
   */
  public static final Property drifttimePerDegreeCoolingUserDefined = newProperty(0, 0.0f, null);

  /**
   * Get the {@code drifttimePerDegreeCoolingUserDefined} property.
   * @see #drifttimePerDegreeCoolingUserDefined
   */
  public float getDrifttimePerDegreeCoolingUserDefined() { return getFloat(drifttimePerDegreeCoolingUserDefined); }

  /**
   * Set the {@code drifttimePerDegreeCoolingUserDefined} property.
   * @see #drifttimePerDegreeCoolingUserDefined
   */
  public void setDrifttimePerDegreeCoolingUserDefined(float v) { setFloat(drifttimePerDegreeCoolingUserDefined, v, null); }

  //endregion Property "drifttimePerDegreeCoolingUserDefined"

  //region Property "drifttimePerDegreeHeatingUserDefined"

  /**
   * Slot for the {@code drifttimePerDegreeHeatingUserDefined} property.
   * @see #getDrifttimePerDegreeHeatingUserDefined
   * @see #setDrifttimePerDegreeHeatingUserDefined
   */
  public static final Property drifttimePerDegreeHeatingUserDefined = newProperty(0, 0.0f, null);

  /**
   * Get the {@code drifttimePerDegreeHeatingUserDefined} property.
   * @see #drifttimePerDegreeHeatingUserDefined
   */
  public float getDrifttimePerDegreeHeatingUserDefined() { return getFloat(drifttimePerDegreeHeatingUserDefined); }

  /**
   * Set the {@code drifttimePerDegreeHeatingUserDefined} property.
   * @see #drifttimePerDegreeHeatingUserDefined
   */
  public void setDrifttimePerDegreeHeatingUserDefined(float v) { setFloat(drifttimePerDegreeHeatingUserDefined, v, null); }

  //endregion Property "drifttimePerDegreeHeatingUserDefined"

  //region Property "runtimePerDegreeCoolingUserDefined"

  /**
   * Slot for the {@code runtimePerDegreeCoolingUserDefined} property.
   * @see #getRuntimePerDegreeCoolingUserDefined
   * @see #setRuntimePerDegreeCoolingUserDefined
   */
  public static final Property runtimePerDegreeCoolingUserDefined = newProperty(0, 0.0f, null);

  /**
   * Get the {@code runtimePerDegreeCoolingUserDefined} property.
   * @see #runtimePerDegreeCoolingUserDefined
   */
  public float getRuntimePerDegreeCoolingUserDefined() { return getFloat(runtimePerDegreeCoolingUserDefined); }

  /**
   * Set the {@code runtimePerDegreeCoolingUserDefined} property.
   * @see #runtimePerDegreeCoolingUserDefined
   */
  public void setRuntimePerDegreeCoolingUserDefined(float v) { setFloat(runtimePerDegreeCoolingUserDefined, v, null); }

  //endregion Property "runtimePerDegreeCoolingUserDefined"

  //region Property "runtimePerDegreeHeatingUserDefined"

  /**
   * Slot for the {@code runtimePerDegreeHeatingUserDefined} property.
   * @see #getRuntimePerDegreeHeatingUserDefined
   * @see #setRuntimePerDegreeHeatingUserDefined
   */
  public static final Property runtimePerDegreeHeatingUserDefined = newProperty(0, 0.0f, null);

  /**
   * Get the {@code runtimePerDegreeHeatingUserDefined} property.
   * @see #runtimePerDegreeHeatingUserDefined
   */
  public float getRuntimePerDegreeHeatingUserDefined() { return getFloat(runtimePerDegreeHeatingUserDefined); }

  /**
   * Set the {@code runtimePerDegreeHeatingUserDefined} property.
   * @see #runtimePerDegreeHeatingUserDefined
   */
  public void setRuntimePerDegreeHeatingUserDefined(float v) { setFloat(runtimePerDegreeHeatingUserDefined, v, null); }

  //endregion Property "runtimePerDegreeHeatingUserDefined"

  //region Property "drifttimePerDegreeCooling"

  /**
   * Slot for the {@code drifttimePerDegreeCooling} property.
   * @see #getDrifttimePerDegreeCooling
   * @see #setDrifttimePerDegreeCooling
   */
  public static final Property drifttimePerDegreeCooling = newProperty(0, 10.0f, null);

  /**
   * Get the {@code drifttimePerDegreeCooling} property.
   * @see #drifttimePerDegreeCooling
   */
  public float getDrifttimePerDegreeCooling() { return getFloat(drifttimePerDegreeCooling); }

  /**
   * Set the {@code drifttimePerDegreeCooling} property.
   * @see #drifttimePerDegreeCooling
   */
  public void setDrifttimePerDegreeCooling(float v) { setFloat(drifttimePerDegreeCooling, v, null); }

  //endregion Property "drifttimePerDegreeCooling"

  //region Property "drifttimePerDegreeHeating"

  /**
   * Slot for the {@code drifttimePerDegreeHeating} property.
   * @see #getDrifttimePerDegreeHeating
   * @see #setDrifttimePerDegreeHeating
   */
  public static final Property drifttimePerDegreeHeating = newProperty(0, 10.0f, null);

  /**
   * Get the {@code drifttimePerDegreeHeating} property.
   * @see #drifttimePerDegreeHeating
   */
  public float getDrifttimePerDegreeHeating() { return getFloat(drifttimePerDegreeHeating); }

  /**
   * Set the {@code drifttimePerDegreeHeating} property.
   * @see #drifttimePerDegreeHeating
   */
  public void setDrifttimePerDegreeHeating(float v) { setFloat(drifttimePerDegreeHeating, v, null); }

  //endregion Property "drifttimePerDegreeHeating"

  //region Property "runtimePerDegreeCooling"

  /**
   * Slot for the {@code runtimePerDegreeCooling} property.
   * @see #getRuntimePerDegreeCooling
   * @see #setRuntimePerDegreeCooling
   */
  public static final Property runtimePerDegreeCooling = newProperty(0, 10.0f, null);

  /**
   * Get the {@code runtimePerDegreeCooling} property.
   * @see #runtimePerDegreeCooling
   */
  public float getRuntimePerDegreeCooling() { return getFloat(runtimePerDegreeCooling); }

  /**
   * Set the {@code runtimePerDegreeCooling} property.
   * @see #runtimePerDegreeCooling
   */
  public void setRuntimePerDegreeCooling(float v) { setFloat(runtimePerDegreeCooling, v, null); }

  //endregion Property "runtimePerDegreeCooling"

  //region Property "runtimePerDegreeHeating"

  /**
   * Slot for the {@code runtimePerDegreeHeating} property.
   * @see #getRuntimePerDegreeHeating
   * @see #setRuntimePerDegreeHeating
   */
  public static final Property runtimePerDegreeHeating = newProperty(0, 10.0f, null);

  /**
   * Get the {@code runtimePerDegreeHeating} property.
   * @see #runtimePerDegreeHeating
   */
  public float getRuntimePerDegreeHeating() { return getFloat(runtimePerDegreeHeating); }

  /**
   * Set the {@code runtimePerDegreeHeating} property.
   * @see #runtimePerDegreeHeating
   */
  public void setRuntimePerDegreeHeating(float v) { setFloat(runtimePerDegreeHeating, v, null); }

  //endregion Property "runtimePerDegreeHeating"

  //region Property "lastStartTime"

  /**
   * Slot for the {@code lastStartTime} property.
   * @see #getLastStartTime
   * @see #setLastStartTime
   */
  public static final Property lastStartTime = newProperty(Flags.TRANSIENT, BAbsTime.NULL, null);

  /**
   * Get the {@code lastStartTime} property.
   * @see #lastStartTime
   */
  public BAbsTime getLastStartTime() { return (BAbsTime)get(lastStartTime); }

  /**
   * Set the {@code lastStartTime} property.
   * @see #lastStartTime
   */
  public void setLastStartTime(BAbsTime v) { set(lastStartTime, v, null); }

  //endregion Property "lastStartTime"

  //region Property "lastStopTime"

  /**
   * Slot for the {@code lastStopTime} property.
   * @see #getLastStopTime
   * @see #setLastStopTime
   */
  public static final Property lastStopTime = newProperty(Flags.TRANSIENT, BAbsTime.NULL, null);

  /**
   * Get the {@code lastStopTime} property.
   * @see #lastStopTime
   */
  public BAbsTime getLastStopTime() { return (BAbsTime)get(lastStopTime); }

  /**
   * Set the {@code lastStopTime} property.
   * @see #lastStopTime
   */
  public void setLastStopTime(BAbsTime v) { set(lastStopTime, v, null); }

  //endregion Property "lastStopTime"

  //region Property "outsideTempAtBeginning"

  /**
   * Slot for the {@code outsideTempAtBeginning} property.
   * @see #getOutsideTempAtBeginning
   * @see #setOutsideTempAtBeginning
   */
  public static final Property outsideTempAtBeginning = newProperty(Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code outsideTempAtBeginning} property.
   * @see #outsideTempAtBeginning
   */
  public BStatusNumeric getOutsideTempAtBeginning() { return (BStatusNumeric)get(outsideTempAtBeginning); }

  /**
   * Set the {@code outsideTempAtBeginning} property.
   * @see #outsideTempAtBeginning
   */
  public void setOutsideTempAtBeginning(BStatusNumeric v) { set(outsideTempAtBeginning, v, null); }

  //endregion Property "outsideTempAtBeginning"

  //region Property "spaceTempAtBeginning"

  /**
   * Slot for the {@code spaceTempAtBeginning} property.
   * @see #getSpaceTempAtBeginning
   * @see #setSpaceTempAtBeginning
   */
  public static final Property spaceTempAtBeginning = newProperty(Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code spaceTempAtBeginning} property.
   * @see #spaceTempAtBeginning
   */
  public BStatusNumeric getSpaceTempAtBeginning() { return (BStatusNumeric)get(spaceTempAtBeginning); }

  /**
   * Set the {@code spaceTempAtBeginning} property.
   * @see #spaceTempAtBeginning
   */
  public void setSpaceTempAtBeginning(BStatusNumeric v) { set(spaceTempAtBeginning, v, null); }

  //endregion Property "spaceTempAtBeginning"

  //region Property "calculatedCommandTime"

  /**
   * Slot for the {@code calculatedCommandTime} property.
   * @see #getCalculatedCommandTime
   * @see #setCalculatedCommandTime
   */
  public static final Property calculatedCommandTime = newProperty(Flags.TRANSIENT | Flags.DEFAULT_ON_CLONE, BTime.DEFAULT, null);

  /**
   * Get the {@code calculatedCommandTime} property.
   * @see #calculatedCommandTime
   */
  public BTime getCalculatedCommandTime() { return (BTime)get(calculatedCommandTime); }

  /**
   * Set the {@code calculatedCommandTime} property.
   * @see #calculatedCommandTime
   */
  public void setCalculatedCommandTime(BTime v) { set(calculatedCommandTime, v, null); }

  //endregion Property "calculatedCommandTime"

  //region Property "programMode"

  /**
   * Slot for the {@code programMode} property.
   * @see #getProgramMode
   * @see #setProgramMode
   */
  public static final Property programMode = newProperty(Flags.TRANSIENT | Flags.DEFAULT_ON_CLONE, 0, null);

  /**
   * Get the {@code programMode} property.
   * @see #programMode
   */
  public int getProgramMode() { return getInt(programMode); }

  /**
   * Set the {@code programMode} property.
   * @see #programMode
   */
  public void setProgramMode(int v) { setInt(programMode, v, null); }

  //endregion Property "programMode"

  //region Action "startTimeTrigger"

  /**
   * Slot for the {@code startTimeTrigger} action.
   * @see #startTimeTrigger()
   */
  public static final Action startTimeTrigger = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code startTimeTrigger} action.
   * @see #startTimeTrigger
   */
  public void startTimeTrigger() { invoke(startTimeTrigger, null, null); }

  //endregion Action "startTimeTrigger"

  //region Action "stopTimeTrigger"

  /**
   * Slot for the {@code stopTimeTrigger} action.
   * @see #stopTimeTrigger()
   */
  public static final Action stopTimeTrigger = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code stopTimeTrigger} action.
   * @see #stopTimeTrigger
   */
  public void stopTimeTrigger() { invoke(stopTimeTrigger, null, null); }

  //endregion Action "stopTimeTrigger"

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
  public static final Type TYPE = Sys.loadType(BOptimizedStartStop.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Initialization  /  Cleanup
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {

    initClockTicket();
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

  public void clockChanged(BRelTime value)
  {
    initClockTicket();
  }

  private void initClockTicket()
  {
    if(ticket != null)
      ticket.cancel();
    BAbsTime tom = Clock.nextTopOfMinute().add(BRelTime.makeSeconds(15));
    ticket = Clock.schedulePeriodically(this, tom, BRelTime.makeMinutes(1), calculate, null);
  }

  public void changed(Property property, Context context) 
  {
   super.changed(property, context);
    if( !Sys.atSteadyState() || !isRunning() )
     return;
    boolean parameterReset = false;
    if(property.equals(drifttimePerDegreeCoolingUserDefined))
    {
      setDrifttimePerDegreeCooling( getDrifttimePerDegreeCoolingUserDefined() );
      parameterReset = true;
    }
    else if(property.equals(drifttimePerDegreeHeatingUserDefined))
    {
      setDrifttimePerDegreeHeating( getDrifttimePerDegreeHeatingUserDefined () );
      parameterReset = true;
    }
    else if( property.equals( runtimePerDegreeCoolingUserDefined ))
    {  
      setRuntimePerDegreeCooling( getRuntimePerDegreeCoolingUserDefined() ); 
      parameterReset = true;
    }
    else if( property.equals( runtimePerDegreeHeatingUserDefined ))
    {
      setRuntimePerDegreeHeating( getRuntimePerDegreeHeatingUserDefined() );
      parameterReset = true;
    }
    if( parameterReset )
      setParameterResetTime(Clock.time());
  }

  public BFacets getSlotFacets(Slot slot)
  {
    return super.getSlotFacets(slot);
  }

  public void doStopTimeTrigger() {}

  public void doStartTimeTrigger() {}

  private String formatNumeric(double value, String pattern)
  {
    DecimalFormat format = new DecimalFormat( pattern );
    return format.format(value);
  }

  public void doCalculate()
  {    
    now = Clock.time();

    // not sure this does anything.  Noone looks at lastMinute???
    //if now.minute != lastMinute
    //  lastMinute = now.minute
    
    // 
    // Reset the flag that allows only one start command per day 
    // 
    if ( (getStartEnable().getValue() == DISABLED) || (now.getTimeOfDayMillis() < TIME_00_01) )
      startDone = false; 
    
    performStartStopAnalysis();

    performStartStopCalculation();
    
    performStartStopControl();

    updateControlOutput();
    
    // 
    // Save the mode determined during this execution for use by analysis section 
    // 
    lastProgramMode = getProgramMode();
  }

  private void performStartStopCalculation()
  {

    // 
    // Perform optimum start or stop time calculation 
    // If next scheduled event is for today 
    //        If next event is a "start" (active) 
    //          If optimized start is enabled AND schedule status is not already active 
    //       If an optimized start has NOT already occurred today AND space temperature input is reliable (no fault, not out of service, not down) 
    //  Set programmode to "Start Calculation" 
    //         If space temperature is above or below target range (heating/cooling mode flag is not used for optimized start)  
    //    Calculate minutes of lead time (absolute value ((space temp - comfort limit) * (heating/cooling factor))) 
    //  Else 
    //    Set lead time to zero because temperature is already within desired range 
    //  Endif
    //       Else (optimized start has occurred OR space temperature input is unreliable
    //  Set lead time to zero 
    //       Endif 
    //     Else 
    //       Set mode to "No Calculation" 
    //     Endif 
    //        Else (continue here if next event is a "stop") 
    //          If optimized stop is enabled AND schedule status is not already inactive 
    //       If an optimized stop is NOT in progress AND space temperature input is reliable (no fault, not out of service, not down) 
    //  Set mode to "Stop Calculation" 
    //         If space temperature is within target range 
    //    Save heating/cooling mode flag for use in analyzing effect 
    //    If mode is heating 
    //      Use lower comfort limit as target because space temp should drift lower when equipment stops 
    //      Calculate minutes of lead time (|(space temp - comfort limit) * (heating/cooling factor)|) 
    //    Else (mode is cooling) 
    //      Use upper comfort limit as target because space temp should drift higher when equipment stops 
    //      Calculate minutes of lead time (|(space temp - comfort limit) * (heating/cooling factor)|) 
    //    Endif 
    //  Else (space temperature is outside comfort range) 
    //    Set lead time to zero because temperature is already outside desired range 
    //  Endif
    //       Else (optimized stop in progress OR space temperature input is unreliable
    //  Set lead time to zero 
    //       Endif 
    //     Else (optimized stop is disabled OR schedule status is already inactive) 
    //       Set mode to "No Calculation" 
    //     Endif 
    //   Endif 
    // Else (next event is not for today) 
    //   Set mode to "No Calculation" 
    // Endif 
    //

    if (getNextEventTime().getDayOfYear() != now.getDayOfYear()) 
    {
      setProgramMode( NO_CALCULATION );
      return;
    }
    if (getNextEventValue().getValue() == ACTIVE)
      performStartCalculation();
    else
      performStopCalculation();
  }

  private void performStartCalculation()
  {
    if ( (getStartEnable().getValue() != ENABLED) || (getScheduleStatus().getValue() == ACTIVE) )
    {
      setProgramMode( NO_CALCULATION );
      return;
    }
    if  ( (startDone == false) && (getSpaceTemp().getStatus().isValid()) ) 
    {
      if(getProgramMode() != START_IN_PROCESS)
      {
        setProgramMode( START_CALCULATION ); 
        if (getSpaceTemp().getValue() > (double)getUpperComfortLimit() ) 
          leadTime = 1 + (int)((getSpaceTemp().getValue() - (double)getUpperComfortLimit()) * (double)getRuntimePerDegreeCooling() ); 
        else if ( getSpaceTemp().getValue() < (double)getLowerComfortLimit() ) 
          leadTime = 1 + (int)(((double)getLowerComfortLimit() - getSpaceTemp().getValue()) * (double)getRuntimePerDegreeHeating() ); 
        else 
          leadTime = 0 ;
      }
    }
    else
      leadTime = 0;
    
    ossLog.trace(this.getParent().getName() + "." + this.getName() + "::oss start lead time = " + leadTime);    
  }
     
  private void performStopCalculation()
  {
    if ( (getStopEnable().getValue() != ENABLED) || (getScheduleStatus().getValue() == INACTIVE) ) 
    {
      setProgramMode( NO_CALCULATION );
      return;
    }
    if ( !getSpaceTemp().getStatus().isValid() || 
         getProgramMode() == STOP_IN_PROCESS       )
    {
      leadTime = 0;
      return;
    }

    setProgramMode( STOP_CALCULATION );
    if ( getSpaceTemp().getValue() > (double)getLowerComfortLimit() )
    {
      controlModeAtBeginning = getHeatCoolMode().getValue(); 
      if ( getHeatCoolMode().getValue() == HEATING )
      {
        leadTime = (int)((getSpaceTemp().getValue() - (double)getLowerComfortLimit()) * (double)getDrifttimePerDegreeHeating() ); 
      }
      else 
        leadTime = (int)(((double)getUpperComfortLimit() - getSpaceTemp().getValue()) * (double)getDrifttimePerDegreeCooling() ); 
    }
    ossLog.trace(this.getName() + "::oss stop lead time = " + leadTime);
  }

  private void performStartStopControl()
  {
    // 
    // Program mode has been determined and perhaps leadtime calculated - Determine whether is it time to issue command 
    // If mode is one of actively calculating a lead time 
    //   Calculate optimized command time by subtracting lead time from next event time 
    //   If mode is "Stop Calculation" 
    //     Use "Earliest Stop Time" as command time if it is LATER than the calculated command time 
    //   Endif 
    //   If command time is prior or equal to the current time OR command time is after next event time 
    //     If this is an optimized start operation 
    //       Set the flag to indicate that a start has been done today 
    //       Set mode to "Start in Progress" 
    //       Save parameters at start time for later analysis 
    //       Fire a trigger that could initiate auxilliary sequences
    //       Output message with time and space temp 
    //     Else (optimized stop operation) 
    //       Set mode to "Stop in Progress" 
    //       Save parameters at stop time for later analysis 
    //       Fire a trigger that could initiate auxilliary sequences
    //       Output message with time and space temp 
    //     Endif 
    //   Endif 
    // Else (not actively calculating a lead time) 
    //   Set optimized command time to next scheduled event time 
    // Endif 
    //  

    if ( (getProgramMode() != START_CALCULATION) && (getProgramMode() != STOP_CALCULATION) ) 
    {
      setCalculatedCommandTime(BTime.make(getNextEventTime()));
      return;
    }
    long calcCmdTime = getNextEventTime().getTimeOfDayMillis() - (leadTime * TIME_00_01) ;
    if(calcCmdTime < getEarliestStartTime().getTimeOfDayMillis())
    	calcCmdTime = getEarliestStartTime().getTimeOfDayMillis();
    setCalculatedCommandTime( BTime.make( BRelTime.make(calcCmdTime ) ) );

    if (getProgramMode() == STOP_CALCULATION && getCalculatedCommandTime().isBefore( getEarliestStopTime() ) )  
      setCalculatedCommandTime( getEarliestStopTime() ); 
    BTime currentTime = BTime.make(Clock.time());
    if ( currentTime.isAfter(getCalculatedCommandTime()) || currentTime.isAfter( BTime.make(getNextEventTime()) ) )
    {
      if ( getProgramMode() == START_CALCULATION )
      {
        startDone = true ;
        setProgramMode( START_IN_PROCESS );
        setLastStartTime( Clock.time()) ;
        getSpaceTempAtBeginning().setValue( getSpaceTemp().getValue() ); 
        getOutsideTempAtBeginning().setValue( getOutsideTemp().getValue() );
        startTimeTrigger(); 
        getMessage().setValue( "Optimized start for " + getNextEventTime() + " schedule time.  Space temp is " + formatNumeric(getSpaceTemp().getValue(), "#0.0") + "." );
      }
      else 
      {
        setProgramMode( STOP_IN_PROCESS );
        setLastStopTime( Clock.time())   ;
        getSpaceTempAtBeginning().setValue( getSpaceTemp().getValue() ); 
        getOutsideTempAtBeginning().setValue( getOutsideTemp().getValue() );
        controlModeAtBeginning = getHeatCoolMode().getValue() ;
        stopTimeTrigger() ;
        getMessage().setValue( "Optimized stop for " + getNextEventTime() + " schedule time.  Space temp is " + formatNumeric(getSpaceTemp().getValue(), "#0.0") + "." ); 
      } 
    }    
  }

  private void updateControlOutput()
  {
    // 
    // Commands are finally output here 
    // If program mode is "Start in Progress" 
    //   Set optimized start output to start 
    //   Set optimized stop output to auto 
    // Elseif program mode is "Stop in Progress" 
    //   Set optimized stop output to stop 
    //   Set optimized start output to auto 
    // Else (for all other program modes) 
    //   Set both optimized outputs to auto 
    // Endif 
    // 
    if ( getProgramMode() == START_IN_PROCESS )
    {
      getStartTimeCommand().setValue(START); 
      getStartTimeCommand().setStatusNull(false); 
      getStopTimeCommand().setValue(STOP); 
      getStopTimeCommand().setStatusNull(true); 
    }
    else if ( getProgramMode() == STOP_IN_PROCESS )
    {
      getStopTimeCommand().setValue(STOP); 
      getStopTimeCommand().setStatusNull(false); 
      getStartTimeCommand().setValue(STOP); 
      getStartTimeCommand().setStatusNull(true); 
    }
    else 
    {
      getStopTimeCommand().setValue(STOP); 
      getStopTimeCommand().setStatusNull(true); 
      getStartTimeCommand().setValue(STOP); 
      getStartTimeCommand().setStatusNull(true); 
      analysisComplete = false ;
    } 
  }

  private void performStartStopAnalysis()
  {
    // 
    // Perform analysis on effectiveness of optimum start or stop 
    // If control parameter allows parameter adjustment AND analysis has not already been done 
    //   If space temperature input is reliable (no fault, not out of service, not down) 
    //     If optimum start or stop is in progress 
    //       Determine mode under which command was issued 
    //       Make sure space temperature is moving in direction of the target (running longer would not help if it is not!) 
    //       Check space temperature against target value and schedule status 
    //       If space temperature has reached its target OR if the schedule command is the same as the optimum command 
    //         Calculate temperature change during optimized period
    //         Calculate length of optimized period 
    //         Calculate the observed minutes/degreeChange by dividing optimized period length by space temperature change 
    //         Adjust the proper start/stop heating/cooling calculation parameter 
    //         Set the analysis complete flag
    //  Output message with analysis time and space temp 
    //       Endif 
    //     Endif
    //   Else (if temperature input is not reliable
    //     Set length of optimized period to zero
    //        Endif 
    //      Endif  
    // 
    if ( !getDynamicParameterAdjust() || analysisComplete || !getSpaceTemp().getStatus().isValid() )
      return;
    if(lastProgramMode == START_IN_PROCESS)
      handleStartAnalysis(); 
    else if (lastProgramMode == STOP_IN_PROCESS)
      handleStopAnalysis();
  }

  private void handleStartAnalysis()
  {
    if( isCoolingAnalysis() )
    {
      if ( getSpaceTemp().getValue() < getSpaceTempAtBeginning().getValue() )
      {
        if ( (getSpaceTemp().getValue() <= (double)getUpperComfortLimit()) || (getScheduleStatus().getValue() == ACTIVE) )
        {
          spaceTempChange = (float)( getSpaceTempAtBeginning().getValue() - getSpaceTemp().getValue() );
          optimizedRuntimeMinutes = getLastStartTime().delta(now).getMinutes();
          observedMinutesPerDegree = (float)optimizedRuntimeMinutes / spaceTempChange; 
          setRuntimePerDegreeCooling( (( getRuntimePerDegreeCooling() * (float)getOldParameterMultiplier() ) + observedMinutesPerDegree) / (float)(getOldParameterMultiplier() + 1) ); 
          analysisComplete = true;
          getMessage().setValue( "Optimized start analysis done at " + now + ".  Space temp is " + formatNumeric(getSpaceTemp().getValue(), "#0.0") + "." );
        } 
      } 
    }
    else
    {
      if ( getSpaceTemp().getValue() > getSpaceTempAtBeginning().getValue() ) 
      {
        if ( (getSpaceTemp().getValue() >= (double)getLowerComfortLimit()) || (getScheduleStatus().getValue() == ACTIVE) ) 
        {
          spaceTempChange = (float)( getSpaceTemp().getValue() - getSpaceTempAtBeginning().getValue() ); 
          optimizedRuntimeMinutes = getLastStartTime().delta(now).getMinutes();   
          observedMinutesPerDegree = (float)optimizedRuntimeMinutes / spaceTempChange ;
          setRuntimePerDegreeHeating( (( getRuntimePerDegreeHeating() * (float)getOldParameterMultiplier() ) + observedMinutesPerDegree) / (float)(getOldParameterMultiplier() + 1) );
          analysisComplete = true ;
          getMessage().setValue( "Optimized start analysis done at " + now + ".  Space temp is " + formatNumeric(getSpaceTemp().getValue(), "#0.0") + "." );
        } 
      } 
    } 
  }

  private boolean isCoolingAnalysis()
  {
    return getSpaceTempAtBeginning().getValue() > (double)getUpperComfortLimit();
  }

  private void handleStopAnalysis()
  {
    if ( controlModeAtBeginning == HEATING )
    {
      if ( getSpaceTemp().getValue() < getSpaceTempAtBeginning().getValue() ) 
      {
        if ( (getSpaceTemp().getValue() <= (double)getLowerComfortLimit()) || (getScheduleStatus().getValue() == INACTIVE) )
        {
          spaceTempChange = (float)getSpaceTempAtBeginning().getValue() - (float)getSpaceTemp().getValue();
          optimizedRuntimeMinutes = getLastStopTime().delta(now).getMinutes();
          observedMinutesPerDegree = (float)optimizedRuntimeMinutes / spaceTempChange ; 
          setDrifttimePerDegreeHeating( (( getDrifttimePerDegreeHeating() * (float)getOldParameterMultiplier() ) + observedMinutesPerDegree) / (float)(getOldParameterMultiplier() + 1) );
          getMessage().setValue( "Optimized stop analysis done at " + now + ".  Space temp is " + formatNumeric(getSpaceTemp().getValue(), "#0.0") + "." );
          analysisComplete = true ;
        } 
      } 
    }
    else
    {
      if ( getSpaceTemp().getValue() > getSpaceTempAtBeginning().getValue() )
      {
        if ( (getSpaceTemp().getValue() >= (double)getUpperComfortLimit()) || (getScheduleStatus().getValue() == INACTIVE) ) 
        {
          spaceTempChange = (float)getSpaceTemp().getValue() - (float)getSpaceTempAtBeginning().getValue(); 
          optimizedRuntimeMinutes = getLastStopTime().delta(now).getMinutes();
          observedMinutesPerDegree = (float)optimizedRuntimeMinutes / spaceTempChange ;
          setDrifttimePerDegreeCooling( (( getDrifttimePerDegreeCooling() * (float)getOldParameterMultiplier() ) + observedMinutesPerDegree) / (float)(getOldParameterMultiplier() + 1) );
          getMessage().setValue( "Optimized stop analysis done at " + now + ".  Space temp is " + formatNumeric(getSpaceTemp().getValue(), "#0.0") + "." );
          analysisComplete = true; 
        } 
      } 
    } 
  }

////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////

  boolean controlModeAtBeginning;  // identifies whether heating or cooling mode was in effect when stop command was issued 
  boolean startDone;             // flag to limit number of optimum starts to 1 
                               // set false at midnight or when optimized start is disabled 
                               // set true when first start command is issued 
  boolean analysisComplete = false;        // flag to indicate that parameter adjustment has been completed  
  float observedMinutesPerDegree= 0.0f;  // calculated from length of optimized period divided by change in space temperature 
  float spaceTempChange = 0.0f;         // change in space temperature during optimized period  
  int leadTime = 0;                  // length of period start or stop should be issued prior to scheduled event time 
  int optimizedRuntimeMinutes= 0;    // length of optimized period until target space temperature is reached 
  int lastProgramMode = 0;             // program mode determined during LAST execution 
  BAbsTime now = BAbsTime.NULL;                 // temporary storage for current time
  BAbsTime lastResetTime = BAbsTime.NULL;        // last time runtime/drifttime parameters were reset to operator entered values  
  int lastMinute = 0;                // limits analysis and time calculations to one per minute
  
  // 
  // EQUATES 
  //
  private static boolean ACTIVE         = true         ;
  private static boolean INACTIVE        = false        ;
  private static boolean START         = true         ;
  private static boolean STOP           = false        ;
  private static boolean DISABLED        = false       ;
  private static boolean ENABLED       = true         ;
  private static boolean COOLING       = true         ;
  private static boolean HEATING       = false        ;
  private static long TIME_00_01       = 60l*1000l;
  private static int NO_CALCULATION    = 0           ; // used with local variable "mode" 
  private static int START_CALCULATION  = 1          ; // used with local variable "mode" 
  private static int START_IN_PROCESS  = 2          ; // used with local variable "mode" 
  private static int STOP_CALCULATION  = 3          ; // used with local variable "mode" 
  private static int STOP_IN_PROCESS    = 4          ; // used with local variable "mode"

  Clock.Ticket ticket = null;
  public static final Log ossLog = Log.getLog("kitControl.oss"); 

}
