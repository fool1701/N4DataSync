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
import javax.baja.units.*;

import com.tridium.kitControl.enums.*;

 /* Sliding Window Demand Calculation
 *
 * KW = ( KWHperPulse * PulsesPerScan * K * 3600.0 ) + ( (1-Scantime* K) * KW );
 * where
 *   K = 0.00751  for  5 Minute Interval
 *   K = 0.002535 for 15 Minute Interval
 *   K = 0.001278 for 30 Minute Interval
 *
 * @author    Andy Saunders
 * @creation  16 May 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "consumptionFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"kilowatt hour\"), 1)"
)
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "demandFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"kilowatt\"), 1)"
)
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "kwhPerPulseFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"kilowatt hour\"), 3)"
)
/*
 Pulse count input - continous pulses
 */
@NiagaraProperty(
  name = "currentPulseCount",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT
)
/*
 Reset Time.  Time of last reset
 */
@NiagaraProperty(
  name = "timeOfReset",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 5 minute Demand KW value
 */
@NiagaraProperty(
  name = "demand5",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 15 minute Demand KW value
 */
@NiagaraProperty(
  name = "demand15",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 30 minute Demand KW value
 */
@NiagaraProperty(
  name = "demand30",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 Running KWH value since last reset
 */
@NiagaraProperty(
  name = "kwh",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 Running KWH value since last hourly reset
 */
@NiagaraProperty(
  name = "kwhHourly",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 KWH value for last hour
 */
@NiagaraProperty(
  name = "kwhLastHour",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 Running KWH value since last daily reset
 */
@NiagaraProperty(
  name = "kwhDaily",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 KWH value for last day
 */
@NiagaraProperty(
  name = "kwhLastDay",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
/*
 KWH value per pulse
 */
@NiagaraProperty(
  name = "kwhPerPulse",
  type = "double",
  defaultValue = "1.0"
)
/*
 Flag to enable recurring automatic reset
 */
@NiagaraProperty(
  name = "enableReset",
  type = "boolean",
  defaultValue = "true"
)
/*
 Day of month for recurring automatic reset
 */
@NiagaraProperty(
  name = "resetDayOfMonth",
  type = "int",
  defaultValue = "1",
  facets = @Facet("BFacets.makeInt(1, 31)")
)
/*
 Day of week for recurring automatic reset
 */
@NiagaraProperty(
  name = "resetDayOfWeek",
  type = "BWeekday",
  defaultValue = "BWeekday.sunday"
)
/*
 Time of day for recurring automatic reset
 */
@NiagaraProperty(
  name = "resetTime",
  type = "BTime",
  defaultValue = "BTime.make(0,0,0)"
)
/*
 maximum pulse count from device
 */
@NiagaraProperty(
  name = "meterRollover",
  type = "long",
  defaultValue = "65535L"
)
@NiagaraAction(
  name = "calculate",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "hourUpdate",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "dayUpdate",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "resetMonthly"
)
@NiagaraAction(
  name = "resetData"
)
public class BSlidingWindowDemandCalc
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BSlidingWindowDemandCalc(1058760127)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "consumptionFacets"

  /**
   * Slot for the {@code consumptionFacets} property.
   * These facets are applied against the out property.
   * @see #getConsumptionFacets
   * @see #setConsumptionFacets
   */
  public static final Property consumptionFacets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("kilowatt hour"), 1), null);

  /**
   * Get the {@code consumptionFacets} property.
   * These facets are applied against the out property.
   * @see #consumptionFacets
   */
  public BFacets getConsumptionFacets() { return (BFacets)get(consumptionFacets); }

  /**
   * Set the {@code consumptionFacets} property.
   * These facets are applied against the out property.
   * @see #consumptionFacets
   */
  public void setConsumptionFacets(BFacets v) { set(consumptionFacets, v, null); }

  //endregion Property "consumptionFacets"

  //region Property "demandFacets"

  /**
   * Slot for the {@code demandFacets} property.
   * These facets are applied against the out property.
   * @see #getDemandFacets
   * @see #setDemandFacets
   */
  public static final Property demandFacets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("kilowatt"), 1), null);

  /**
   * Get the {@code demandFacets} property.
   * These facets are applied against the out property.
   * @see #demandFacets
   */
  public BFacets getDemandFacets() { return (BFacets)get(demandFacets); }

  /**
   * Set the {@code demandFacets} property.
   * These facets are applied against the out property.
   * @see #demandFacets
   */
  public void setDemandFacets(BFacets v) { set(demandFacets, v, null); }

  //endregion Property "demandFacets"

  //region Property "kwhPerPulseFacets"

  /**
   * Slot for the {@code kwhPerPulseFacets} property.
   * These facets are applied against the out property.
   * @see #getKwhPerPulseFacets
   * @see #setKwhPerPulseFacets
   */
  public static final Property kwhPerPulseFacets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("kilowatt hour"), 3), null);

  /**
   * Get the {@code kwhPerPulseFacets} property.
   * These facets are applied against the out property.
   * @see #kwhPerPulseFacets
   */
  public BFacets getKwhPerPulseFacets() { return (BFacets)get(kwhPerPulseFacets); }

  /**
   * Set the {@code kwhPerPulseFacets} property.
   * These facets are applied against the out property.
   * @see #kwhPerPulseFacets
   */
  public void setKwhPerPulseFacets(BFacets v) { set(kwhPerPulseFacets, v, null); }

  //endregion Property "kwhPerPulseFacets"

  //region Property "currentPulseCount"

  /**
   * Slot for the {@code currentPulseCount} property.
   * Pulse count input - continous pulses
   * @see #getCurrentPulseCount
   * @see #setCurrentPulseCount
   */
  public static final Property currentPulseCount = newProperty(Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code currentPulseCount} property.
   * Pulse count input - continous pulses
   * @see #currentPulseCount
   */
  public BStatusNumeric getCurrentPulseCount() { return (BStatusNumeric)get(currentPulseCount); }

  /**
   * Set the {@code currentPulseCount} property.
   * Pulse count input - continous pulses
   * @see #currentPulseCount
   */
  public void setCurrentPulseCount(BStatusNumeric v) { set(currentPulseCount, v, null); }

  //endregion Property "currentPulseCount"

  //region Property "timeOfReset"

  /**
   * Slot for the {@code timeOfReset} property.
   * Reset Time.  Time of last reset
   * @see #getTimeOfReset
   * @see #setTimeOfReset
   */
  public static final Property timeOfReset = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code timeOfReset} property.
   * Reset Time.  Time of last reset
   * @see #timeOfReset
   */
  public BAbsTime getTimeOfReset() { return (BAbsTime)get(timeOfReset); }

  /**
   * Set the {@code timeOfReset} property.
   * Reset Time.  Time of last reset
   * @see #timeOfReset
   */
  public void setTimeOfReset(BAbsTime v) { set(timeOfReset, v, null); }

  //endregion Property "timeOfReset"

  //region Property "demand5"

  /**
   * Slot for the {@code demand5} property.
   * 5 minute Demand KW value
   * @see #getDemand5
   * @see #setDemand5
   */
  public static final Property demand5 = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code demand5} property.
   * 5 minute Demand KW value
   * @see #demand5
   */
  public BStatusNumeric getDemand5() { return (BStatusNumeric)get(demand5); }

  /**
   * Set the {@code demand5} property.
   * 5 minute Demand KW value
   * @see #demand5
   */
  public void setDemand5(BStatusNumeric v) { set(demand5, v, null); }

  //endregion Property "demand5"

  //region Property "demand15"

  /**
   * Slot for the {@code demand15} property.
   * 15 minute Demand KW value
   * @see #getDemand15
   * @see #setDemand15
   */
  public static final Property demand15 = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code demand15} property.
   * 15 minute Demand KW value
   * @see #demand15
   */
  public BStatusNumeric getDemand15() { return (BStatusNumeric)get(demand15); }

  /**
   * Set the {@code demand15} property.
   * 15 minute Demand KW value
   * @see #demand15
   */
  public void setDemand15(BStatusNumeric v) { set(demand15, v, null); }

  //endregion Property "demand15"

  //region Property "demand30"

  /**
   * Slot for the {@code demand30} property.
   * 30 minute Demand KW value
   * @see #getDemand30
   * @see #setDemand30
   */
  public static final Property demand30 = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code demand30} property.
   * 30 minute Demand KW value
   * @see #demand30
   */
  public BStatusNumeric getDemand30() { return (BStatusNumeric)get(demand30); }

  /**
   * Set the {@code demand30} property.
   * 30 minute Demand KW value
   * @see #demand30
   */
  public void setDemand30(BStatusNumeric v) { set(demand30, v, null); }

  //endregion Property "demand30"

  //region Property "kwh"

  /**
   * Slot for the {@code kwh} property.
   * Running KWH value since last reset
   * @see #getKwh
   * @see #setKwh
   */
  public static final Property kwh = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code kwh} property.
   * Running KWH value since last reset
   * @see #kwh
   */
  public BStatusNumeric getKwh() { return (BStatusNumeric)get(kwh); }

  /**
   * Set the {@code kwh} property.
   * Running KWH value since last reset
   * @see #kwh
   */
  public void setKwh(BStatusNumeric v) { set(kwh, v, null); }

  //endregion Property "kwh"

  //region Property "kwhHourly"

  /**
   * Slot for the {@code kwhHourly} property.
   * Running KWH value since last hourly reset
   * @see #getKwhHourly
   * @see #setKwhHourly
   */
  public static final Property kwhHourly = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code kwhHourly} property.
   * Running KWH value since last hourly reset
   * @see #kwhHourly
   */
  public BStatusNumeric getKwhHourly() { return (BStatusNumeric)get(kwhHourly); }

  /**
   * Set the {@code kwhHourly} property.
   * Running KWH value since last hourly reset
   * @see #kwhHourly
   */
  public void setKwhHourly(BStatusNumeric v) { set(kwhHourly, v, null); }

  //endregion Property "kwhHourly"

  //region Property "kwhLastHour"

  /**
   * Slot for the {@code kwhLastHour} property.
   * KWH value for last hour
   * @see #getKwhLastHour
   * @see #setKwhLastHour
   */
  public static final Property kwhLastHour = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code kwhLastHour} property.
   * KWH value for last hour
   * @see #kwhLastHour
   */
  public BStatusNumeric getKwhLastHour() { return (BStatusNumeric)get(kwhLastHour); }

  /**
   * Set the {@code kwhLastHour} property.
   * KWH value for last hour
   * @see #kwhLastHour
   */
  public void setKwhLastHour(BStatusNumeric v) { set(kwhLastHour, v, null); }

  //endregion Property "kwhLastHour"

  //region Property "kwhDaily"

  /**
   * Slot for the {@code kwhDaily} property.
   * Running KWH value since last daily reset
   * @see #getKwhDaily
   * @see #setKwhDaily
   */
  public static final Property kwhDaily = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code kwhDaily} property.
   * Running KWH value since last daily reset
   * @see #kwhDaily
   */
  public BStatusNumeric getKwhDaily() { return (BStatusNumeric)get(kwhDaily); }

  /**
   * Set the {@code kwhDaily} property.
   * Running KWH value since last daily reset
   * @see #kwhDaily
   */
  public void setKwhDaily(BStatusNumeric v) { set(kwhDaily, v, null); }

  //endregion Property "kwhDaily"

  //region Property "kwhLastDay"

  /**
   * Slot for the {@code kwhLastDay} property.
   * KWH value for last day
   * @see #getKwhLastDay
   * @see #setKwhLastDay
   */
  public static final Property kwhLastDay = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code kwhLastDay} property.
   * KWH value for last day
   * @see #kwhLastDay
   */
  public BStatusNumeric getKwhLastDay() { return (BStatusNumeric)get(kwhLastDay); }

  /**
   * Set the {@code kwhLastDay} property.
   * KWH value for last day
   * @see #kwhLastDay
   */
  public void setKwhLastDay(BStatusNumeric v) { set(kwhLastDay, v, null); }

  //endregion Property "kwhLastDay"

  //region Property "kwhPerPulse"

  /**
   * Slot for the {@code kwhPerPulse} property.
   * KWH value per pulse
   * @see #getKwhPerPulse
   * @see #setKwhPerPulse
   */
  public static final Property kwhPerPulse = newProperty(0, 1.0, null);

  /**
   * Get the {@code kwhPerPulse} property.
   * KWH value per pulse
   * @see #kwhPerPulse
   */
  public double getKwhPerPulse() { return getDouble(kwhPerPulse); }

  /**
   * Set the {@code kwhPerPulse} property.
   * KWH value per pulse
   * @see #kwhPerPulse
   */
  public void setKwhPerPulse(double v) { setDouble(kwhPerPulse, v, null); }

  //endregion Property "kwhPerPulse"

  //region Property "enableReset"

  /**
   * Slot for the {@code enableReset} property.
   * Flag to enable recurring automatic reset
   * @see #getEnableReset
   * @see #setEnableReset
   */
  public static final Property enableReset = newProperty(0, true, null);

  /**
   * Get the {@code enableReset} property.
   * Flag to enable recurring automatic reset
   * @see #enableReset
   */
  public boolean getEnableReset() { return getBoolean(enableReset); }

  /**
   * Set the {@code enableReset} property.
   * Flag to enable recurring automatic reset
   * @see #enableReset
   */
  public void setEnableReset(boolean v) { setBoolean(enableReset, v, null); }

  //endregion Property "enableReset"

  //region Property "resetDayOfMonth"

  /**
   * Slot for the {@code resetDayOfMonth} property.
   * Day of month for recurring automatic reset
   * @see #getResetDayOfMonth
   * @see #setResetDayOfMonth
   */
  public static final Property resetDayOfMonth = newProperty(0, 1, BFacets.makeInt(1, 31));

  /**
   * Get the {@code resetDayOfMonth} property.
   * Day of month for recurring automatic reset
   * @see #resetDayOfMonth
   */
  public int getResetDayOfMonth() { return getInt(resetDayOfMonth); }

  /**
   * Set the {@code resetDayOfMonth} property.
   * Day of month for recurring automatic reset
   * @see #resetDayOfMonth
   */
  public void setResetDayOfMonth(int v) { setInt(resetDayOfMonth, v, null); }

  //endregion Property "resetDayOfMonth"

  //region Property "resetDayOfWeek"

  /**
   * Slot for the {@code resetDayOfWeek} property.
   * Day of week for recurring automatic reset
   * @see #getResetDayOfWeek
   * @see #setResetDayOfWeek
   */
  public static final Property resetDayOfWeek = newProperty(0, BWeekday.sunday, null);

  /**
   * Get the {@code resetDayOfWeek} property.
   * Day of week for recurring automatic reset
   * @see #resetDayOfWeek
   */
  public BWeekday getResetDayOfWeek() { return (BWeekday)get(resetDayOfWeek); }

  /**
   * Set the {@code resetDayOfWeek} property.
   * Day of week for recurring automatic reset
   * @see #resetDayOfWeek
   */
  public void setResetDayOfWeek(BWeekday v) { set(resetDayOfWeek, v, null); }

  //endregion Property "resetDayOfWeek"

  //region Property "resetTime"

  /**
   * Slot for the {@code resetTime} property.
   * Time of day for recurring automatic reset
   * @see #getResetTime
   * @see #setResetTime
   */
  public static final Property resetTime = newProperty(0, BTime.make(0,0,0), null);

  /**
   * Get the {@code resetTime} property.
   * Time of day for recurring automatic reset
   * @see #resetTime
   */
  public BTime getResetTime() { return (BTime)get(resetTime); }

  /**
   * Set the {@code resetTime} property.
   * Time of day for recurring automatic reset
   * @see #resetTime
   */
  public void setResetTime(BTime v) { set(resetTime, v, null); }

  //endregion Property "resetTime"

  //region Property "meterRollover"

  /**
   * Slot for the {@code meterRollover} property.
   * maximum pulse count from device
   * @see #getMeterRollover
   * @see #setMeterRollover
   */
  public static final Property meterRollover = newProperty(0, 65535L, null);

  /**
   * Get the {@code meterRollover} property.
   * maximum pulse count from device
   * @see #meterRollover
   */
  public long getMeterRollover() { return getLong(meterRollover); }

  /**
   * Set the {@code meterRollover} property.
   * maximum pulse count from device
   * @see #meterRollover
   */
  public void setMeterRollover(long v) { setLong(meterRollover, v, null); }

  //endregion Property "meterRollover"

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

  //region Action "hourUpdate"

  /**
   * Slot for the {@code hourUpdate} action.
   * @see #hourUpdate()
   */
  public static final Action hourUpdate = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code hourUpdate} action.
   * @see #hourUpdate
   */
  public void hourUpdate() { invoke(hourUpdate, null, null); }

  //endregion Action "hourUpdate"

  //region Action "dayUpdate"

  /**
   * Slot for the {@code dayUpdate} action.
   * @see #dayUpdate()
   */
  public static final Action dayUpdate = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code dayUpdate} action.
   * @see #dayUpdate
   */
  public void dayUpdate() { invoke(dayUpdate, null, null); }

  //endregion Action "dayUpdate"

  //region Action "resetMonthly"

  /**
   * Slot for the {@code resetMonthly} action.
   * @see #resetMonthly()
   */
  public static final Action resetMonthly = newAction(0, null);

  /**
   * Invoke the {@code resetMonthly} action.
   * @see #resetMonthly
   */
  public void resetMonthly() { invoke(resetMonthly, null, null); }

  //endregion Action "resetMonthly"

  //region Action "resetData"

  /**
   * Slot for the {@code resetData} action.
   * @see #resetData()
   */
  public static final Action resetData = newAction(0, null);

  /**
   * Invoke the {@code resetData} action.
   * @see #resetData
   */
  public void resetData() { invoke(resetData, null, null); }

  //endregion Action "resetData"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSlidingWindowDemandCalc.class);

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
    ticket = Clock.schedulePeriodically(this, BRelTime.makeMinutes(1), calculate, null);
    super.started();
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
//    if( !Sys.atSteadyState() || !isRunning() )
//	    return;
    
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if( slot.equals(kwh)         ||
        slot.equals(kwhDaily)    ||
        slot.equals(kwhHourly)   ||
        slot.equals(kwhLastDay)  ||
        slot.equals(kwhLastHour)    )
      return getConsumptionFacets();
    else if( slot.equals(demand5)  ||
             slot.equals(demand15) ||
             slot.equals(demand30)    )
      return getDemandFacets();
    else if(slot.equals(kwhPerPulse))
    {
      return getKwhPerPulseFacets();
    }
    return super.getSlotFacets(slot);
  }

  public void doHourUpdate() {}
  
  public void doDayUpdate(){}
  
  public void doResetData()
  {
    // return if calculate has never run.
    if(now == null)
      return;
    getKwh().setValue( 0.0);
    getKwhHourly().setValue(0.0);
    getKwhLastHour().setValue( 0.0 );
    getKwhDaily().setValue( 0.0 );
    getKwhLastDay().setValue(0.0);
    getDemand5().setValue(0.0);
    getDemand15().setValue(0.0);
    getDemand30().setValue(0.0);
    previousTime = now;
    lastHour = now.getHour();
    lastDay = now.getDay();
    resetFlag = true;
    setTimeOfReset( Clock.time() );
  }

  public void setDataFaultStatus(boolean fault)
  {
    getKwh().setStatusFault(fault);
    getKwhHourly().setStatusFault(fault);
    getKwhLastHour().setStatusFault(fault);
    getKwhDaily().setStatusFault(fault);
    getKwhLastDay().setStatusFault(fault);
    getDemand5().setStatusFault(fault);
    getDemand15().setStatusFault(fault);
    getDemand30().setStatusFault(fault);
  }

  public void doResetMonthly()
  {
    // return if calculate has never run.
    if(now == null)
      return;
    getKwh().setValue( 0.0);
    getKwhHourly().setValue(0.0);
    getKwhLastHour().setValue( 0.0 );
    getKwhDaily().setValue( 0.0 );
    getKwhLastDay().setValue(0.0);
    previousTime = now;
    lastHour = now.getHour();
    lastDay = now.getDay();
    resetFlag = true;
    setTimeOfReset( Clock.time() );
  }

  public void doPulseCalculate()
  {
    // do nothing unless pulse count actually changes
    if ( getCurrentPulseCount().getValue() == lastPulseCount )
      return;
    // don't process if current input is invalid.
    if ( ! getCurrentPulseCount().getStatus().isValid()  || Double.isNaN(getCurrentPulseCount().getValue()) )
      return;
    long thisCountChangeTicks = Clock.millis();
    countChangePeriod = thisCountChangeTicks - lastCountChangeTicks;
    if( (countChangePeriod) < FIFTY_SECONDS )
    {
      isSlowRate = false;
      lastCountChangeTicks = thisCountChangeTicks;
      doCalculate();
    }
    else
    {
      isSlowRate = true;
      lastCountChangeTicks = thisCountChangeTicks;
    }
      
  }
  
  public void doCalculate()
  {
//   if(ticket != null)
//      ticket.cancel();
//    ticket = Clock.schedule(this, BRelTime.makeMinutes(1), calculate, null);
    now = Clock.time();
    
    if ( firstTime == true )	//Initialize values when run for the first time
    {
      lastPulseCount = getCurrentPulseCount().getValue();
      // don't process if current input is invalid.
      if ( ! getCurrentPulseCount().getStatus().isValid()  || Double.isNaN(getCurrentPulseCount().getValue()) )
      {
        setDataFaultStatus(true);
        return;
      }
      setDataFaultStatus(false);
      lastCountChangeTicks = now.getMillis();
      previousTime = now;
      setTimeOfReset(now);
      lastHour = now.getHour();
      lastDay = now.getDay();
      firstTime = false;
      return;
    }
    
    if ( reset == false )		//Reset logic for recurring automatic reset
    {
      int today = now.getDay();
      int resetDayOfMonth = getResetDayOfMonth();
      if ( (today >= resetDayOfMonth) && (resetDayOfMonth < resetDayOfMonth + 7) )
      {
        if ( now.getWeekday().equals(getResetDayOfWeek() ) )
        {
          if ( now.getTimeOfDayMillis() >= getResetTime().getTimeOfDayMillis() )
          {
            reset = true;
            setTimeOfReset( Clock.time() );
          }
        }
      }
    }
    
    if ( reset )
    {
      if ( !( now.getWeekday().equals(getResetDayOfWeek()) ) )
        reset = false;
    }
      
    if ( ( getEnableReset() == true ) && ( reset == true ) && ( resetFlag == false ) )	//Reset values when recurring automatic reset occurs
    {
      getKwh().setValue( 0.0);
      getKwhHourly().setValue(0.0);
      getKwhLastHour().setValue( 0.0 );
      getKwhDaily().setValue( 0.0 );
      getKwhLastDay().setValue(0.0);
      previousTime = now;
      lastHour = now.getHour();
      lastDay = now.getDay();
      resetFlag = true;
    }
    
    if ( reset == false )
      resetFlag = false;
    
    diffSec= ((double)previousTime.delta(now).getMillis())/1000.0;
    //System.out.println("   diffSec = " + diffSec);
    previousTime=now;
    maxPulseDiff = (int)(diffSec * 10.0);  //Ensures that no more than 10 pulses/second are considered valid
    
    double currentCount =  getCurrentPulseCount().getValue();
    // don't process if current input is invalid.
    if ( ! getCurrentPulseCount().getStatus().isValid()  || Double.isNaN(getCurrentPulseCount().getValue()) )
    {
      setDataFaultStatus(true);
      return;
    }
    setDataFaultStatus(false);
    //System.out.println(" currentCount   = " + currentCount);
    //System.out.println(" lastPulseCount = " + lastPulseCount);
    if ( currentCount >= lastPulseCount )
      pulseCountDiff = currentCount - lastPulseCount;
    else
      pulseCountDiff = (double)getMeterRollover() + 1.0  + currentCount - lastPulseCount;  
    
    // To insure pulseCountDiff is not an invalid number due to power outage, etc. of the pulseCounter
    //System.out.println(" pulseCountDiff =  " + pulseCountDiff);
    //System.out.println(" maxPulseDiff   =  " + maxPulseDiff);
    if ( pulseCountDiff > maxPulseDiff  || pulseCountDiff < 0 )
    {
      lastPulseCount = currentCount;
      
      return;
    }
    
    if(pulseCountDiff > 0 && isSlowRate)
      diffSec = ((double)countChangePeriod)/1000.0;
    //System.out.println("  actual diffSec = " + diffSec);
    // Running KWH
    kwhNew = pulseCountDiff * getKwhPerPulse();
    getKwh().setValue( getKwh().getValue() + kwhNew );
    lastPulseCount = currentCount;
    
    // Hourly KWH
    if ( lastHour != now.getHour() )
    {
      lastHour = now.getHour();
      getKwhLastHour().setValue( getKwhHourly().getValue() ); 
      hourUpdate();		//Trigger for hourly Log
      getKwhHourly().setValue( kwhNew );
    }
    else
      getKwhHourly().setValue( getKwhHourly().getValue() + kwhNew );
    
    // Daily KWH
    if ( lastDay != now.getDay() )
    {
      lastDay = now.getDay();
      getKwhLastDay().setValue( getKwhDaily().getValue() );
      dayUpdate();		//Trigger for daily Log
      getKwhDaily().setValue(kwhNew);
    }
    else
      getKwhDaily().setValue( getKwhDaily().getValue()+ kwhNew );
    
    
    double x;
    // 5 Minute Demand Calculation
    x =  ( 1.0 - ( diffSec * 0.00751 ) ) * getDemand5().getValue();
    getDemand5().setValue( ( pulseCountDiff * getKwhPerPulse() * 0.00751 * 3600.0 ) + x );
    
    // 15 Minute Demand Calculation
    x =  ( 1.0 - ( diffSec * 0.002535 ) ) * getDemand15().getValue();
    getDemand15().setValue( ( pulseCountDiff * getKwhPerPulse() * 0.002535 * 3600.0 ) + x );
    
    // 30 Minute Demand Calculation
    x =  ( 1.0 - ( diffSec * 0.001278 ) ) * getDemand30().getValue();
    getDemand30().setValue( ( pulseCountDiff * getKwhPerPulse() * 0.001278 * 3600.0 ) + x );
    
    
    
  }
////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////

  Clock.Ticket ticket = null;

  private static int FIFTY_SECONDS = 50000;
  
  BAbsTime now;
  BAbsTime previousTime;
  boolean reset = false;           //Reset Flag.  If true resets all power values
  boolean resetFlag = false;
  double diffSec;
  double kwhNew;
  int lastHour;
  int lastDay;
  boolean firstTime = true;
  int maxPulseDiff;
  double lastPulseCount;
  double pulseCountDiff;
  long lastCountChangeTicks = 0;
  long countChangePeriod = 0;
  boolean isSlowRate = false;

}
