/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonDaysOfWeekEnum;
import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.enums.BLonIntervalOfMonthEnum;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file represents SCPT_time_period.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  10 Nov 06
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:43 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "units",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonIntervalOfMonthEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "valueMinutesInterval",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 1.0F, null)")
)
@NiagaraProperty(
  name = "valueDateOfMonth",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 1.0F, 31.0F, 1.0F, null)")
)
@NiagaraProperty(
  name = "valueHourOfDay",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 0.0F, 23.0F, 1.0F, null)")
)
@NiagaraProperty(
  name = "valueDayOfWeek",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonDaysOfWeekEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "valueHoursInterval",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 1.0F, null)")
)
public class BLonCpTimePeriod
  extends BLonData
{  
  /*
 <CpTimePeriod type="XCpTypeDef">
  <init v="00 00"/>
  <elem n="units                " qual="e8" enumDef="IntervalOfMonthT"/>
  <elem n="valueMinutesInterval " qual="u8 byt=1 min=1.0"/>
  <elem n="valueDateOfMonth     " qual="u8 byt=1 min=1.0 max=31.0"/>
  <elem n="valueHourOfDay       " qual="u8 byt=1 max=23.0"/>
  <elem n="valueDayOfWeek       " qual="e8" enumDef="DaysOfWeekT"/>
  <elem n="valueHoursInterval   " qual="u8 byt=1 min=1.0"/>
  <typeScope v="0,291"/>
 </CpTimePeriod>
 */

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonCpTimePeriod(1599794477)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "units"

  /**
   * Slot for the {@code units} property.
   * @see #getUnits
   * @see #setUnits
   */
  public static final Property units = newProperty(0, BLonEnum.make(BLonIntervalOfMonthEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code units} property.
   * @see #units
   */
  public BLonEnum getUnits() { return (BLonEnum)get(units); }

  /**
   * Set the {@code units} property.
   * @see #units
   */
  public void setUnits(BLonEnum v) { set(units, v, null); }

  //endregion Property "units"

  //region Property "valueMinutesInterval"

  /**
   * Slot for the {@code valueMinutesInterval} property.
   * @see #getValueMinutesInterval
   * @see #setValueMinutesInterval
   */
  public static final Property valueMinutesInterval = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 1.0F, null));

  /**
   * Get the {@code valueMinutesInterval} property.
   * @see #valueMinutesInterval
   */
  public BLonFloat getValueMinutesInterval() { return (BLonFloat)get(valueMinutesInterval); }

  /**
   * Set the {@code valueMinutesInterval} property.
   * @see #valueMinutesInterval
   */
  public void setValueMinutesInterval(BLonFloat v) { set(valueMinutesInterval, v, null); }

  //endregion Property "valueMinutesInterval"

  //region Property "valueDateOfMonth"

  /**
   * Slot for the {@code valueDateOfMonth} property.
   * @see #getValueDateOfMonth
   * @see #setValueDateOfMonth
   */
  public static final Property valueDateOfMonth = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 1.0F, 31.0F, 1.0F, null));

  /**
   * Get the {@code valueDateOfMonth} property.
   * @see #valueDateOfMonth
   */
  public BLonFloat getValueDateOfMonth() { return (BLonFloat)get(valueDateOfMonth); }

  /**
   * Set the {@code valueDateOfMonth} property.
   * @see #valueDateOfMonth
   */
  public void setValueDateOfMonth(BLonFloat v) { set(valueDateOfMonth, v, null); }

  //endregion Property "valueDateOfMonth"

  //region Property "valueHourOfDay"

  /**
   * Slot for the {@code valueHourOfDay} property.
   * @see #getValueHourOfDay
   * @see #setValueHourOfDay
   */
  public static final Property valueHourOfDay = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 0.0F, 23.0F, 1.0F, null));

  /**
   * Get the {@code valueHourOfDay} property.
   * @see #valueHourOfDay
   */
  public BLonFloat getValueHourOfDay() { return (BLonFloat)get(valueHourOfDay); }

  /**
   * Set the {@code valueHourOfDay} property.
   * @see #valueHourOfDay
   */
  public void setValueHourOfDay(BLonFloat v) { set(valueHourOfDay, v, null); }

  //endregion Property "valueHourOfDay"

  //region Property "valueDayOfWeek"

  /**
   * Slot for the {@code valueDayOfWeek} property.
   * @see #getValueDayOfWeek
   * @see #setValueDayOfWeek
   */
  public static final Property valueDayOfWeek = newProperty(0, BLonEnum.make(BLonDaysOfWeekEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code valueDayOfWeek} property.
   * @see #valueDayOfWeek
   */
  public BLonEnum getValueDayOfWeek() { return (BLonEnum)get(valueDayOfWeek); }

  /**
   * Set the {@code valueDayOfWeek} property.
   * @see #valueDayOfWeek
   */
  public void setValueDayOfWeek(BLonEnum v) { set(valueDayOfWeek, v, null); }

  //endregion Property "valueDayOfWeek"

  //region Property "valueHoursInterval"

  /**
   * Slot for the {@code valueHoursInterval} property.
   * @see #getValueHoursInterval
   * @see #setValueHoursInterval
   */
  public static final Property valueHoursInterval = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 1.0F, null));

  /**
   * Get the {@code valueHoursInterval} property.
   * @see #valueHoursInterval
   */
  public BLonFloat getValueHoursInterval() { return (BLonFloat)get(valueHoursInterval); }

  /**
   * Set the {@code valueHoursInterval} property.
   * @see #valueHoursInterval
   */
  public void setValueHoursInterval(BLonFloat v) { set(valueHoursInterval, v, null); }

  //endregion Property "valueHoursInterval"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonCpTimePeriod.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(units, out);

    switch(getUnits().getEnum().getOrdinal()) 
    {
      case BLonIntervalOfMonthEnum.IOM_MINUTE  : primitiveToOutputStream(valueMinutesInterval , out); break;
      case BLonIntervalOfMonthEnum.IOM_HOUR    : primitiveToOutputStream(valueDateOfMonth     , out); break;
      case BLonIntervalOfMonthEnum.IOM_DAY     : primitiveToOutputStream(valueHourOfDay       , out); break;
      case BLonIntervalOfMonthEnum.IOM_WEEK    : primitiveToOutputStream(valueDayOfWeek       , out); break;
      case BLonIntervalOfMonthEnum.IOM_MONTH   : primitiveToOutputStream(valueHoursInterval   , out); break;
      default : out.writeUnsigned8(0);
    }  
  }
  
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(units, in);
    
    switch(getUnits().getEnum().getOrdinal()) 
    {
      case BLonIntervalOfMonthEnum.IOM_MINUTE  : primitiveFromInputStream(valueMinutesInterval , in); break; 
      case BLonIntervalOfMonthEnum.IOM_HOUR    : primitiveFromInputStream(valueDateOfMonth     , in); break; 
      case BLonIntervalOfMonthEnum.IOM_DAY     : primitiveFromInputStream(valueHourOfDay       , in); break; 
      case BLonIntervalOfMonthEnum.IOM_WEEK    : primitiveFromInputStream(valueDayOfWeek       , in); break; 
      case BLonIntervalOfMonthEnum.IOM_MONTH   : primitiveFromInputStream(valueHoursInterval   , in); break; 
    }
  }  


}      
