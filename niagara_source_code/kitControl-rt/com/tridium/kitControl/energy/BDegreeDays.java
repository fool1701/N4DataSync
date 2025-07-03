/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.energy;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;


/** Degree Day Calculation object
 *
 * 
 * @author    Andy Saunders
 * @creation  11 Jan 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"fahrenheit\"), 1)"
)
@NiagaraProperty(
  name = "baseTemperature",
  type = "float",
  defaultValue = "65.0f"
)
@NiagaraProperty(
  name = "tempIn",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "minTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "maxTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "meanTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "clgDegDays",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "clgDegDaysTotal",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "htgDegDays",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "htgDegDaysTotal",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraAction(
  name = "resetTotals"
)
@NiagaraAction(
  name = "calculate",
  flags = Flags.HIDDEN
)
public class BDegreeDays
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BDegreeDays(1189541047)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("fahrenheit"), 1), null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "baseTemperature"

  /**
   * Slot for the {@code baseTemperature} property.
   * @see #getBaseTemperature
   * @see #setBaseTemperature
   */
  public static final Property baseTemperature = newProperty(0, 65.0f, null);

  /**
   * Get the {@code baseTemperature} property.
   * @see #baseTemperature
   */
  public float getBaseTemperature() { return getFloat(baseTemperature); }

  /**
   * Set the {@code baseTemperature} property.
   * @see #baseTemperature
   */
  public void setBaseTemperature(float v) { setFloat(baseTemperature, v, null); }

  //endregion Property "baseTemperature"

  //region Property "tempIn"

  /**
   * Slot for the {@code tempIn} property.
   * @see #getTempIn
   * @see #setTempIn
   */
  public static final Property tempIn = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code tempIn} property.
   * @see #tempIn
   */
  public BStatusNumeric getTempIn() { return (BStatusNumeric)get(tempIn); }

  /**
   * Set the {@code tempIn} property.
   * @see #tempIn
   */
  public void setTempIn(BStatusNumeric v) { set(tempIn, v, null); }

  //endregion Property "tempIn"

  //region Property "minTemp"

  /**
   * Slot for the {@code minTemp} property.
   * @see #getMinTemp
   * @see #setMinTemp
   */
  public static final Property minTemp = newProperty(Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code minTemp} property.
   * @see #minTemp
   */
  public BStatusNumeric getMinTemp() { return (BStatusNumeric)get(minTemp); }

  /**
   * Set the {@code minTemp} property.
   * @see #minTemp
   */
  public void setMinTemp(BStatusNumeric v) { set(minTemp, v, null); }

  //endregion Property "minTemp"

  //region Property "maxTemp"

  /**
   * Slot for the {@code maxTemp} property.
   * @see #getMaxTemp
   * @see #setMaxTemp
   */
  public static final Property maxTemp = newProperty(Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code maxTemp} property.
   * @see #maxTemp
   */
  public BStatusNumeric getMaxTemp() { return (BStatusNumeric)get(maxTemp); }

  /**
   * Set the {@code maxTemp} property.
   * @see #maxTemp
   */
  public void setMaxTemp(BStatusNumeric v) { set(maxTemp, v, null); }

  //endregion Property "maxTemp"

  //region Property "meanTemp"

  /**
   * Slot for the {@code meanTemp} property.
   * @see #getMeanTemp
   * @see #setMeanTemp
   */
  public static final Property meanTemp = newProperty(Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code meanTemp} property.
   * @see #meanTemp
   */
  public BStatusNumeric getMeanTemp() { return (BStatusNumeric)get(meanTemp); }

  /**
   * Set the {@code meanTemp} property.
   * @see #meanTemp
   */
  public void setMeanTemp(BStatusNumeric v) { set(meanTemp, v, null); }

  //endregion Property "meanTemp"

  //region Property "clgDegDays"

  /**
   * Slot for the {@code clgDegDays} property.
   * @see #getClgDegDays
   * @see #setClgDegDays
   */
  public static final Property clgDegDays = newProperty(Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code clgDegDays} property.
   * @see #clgDegDays
   */
  public BStatusNumeric getClgDegDays() { return (BStatusNumeric)get(clgDegDays); }

  /**
   * Set the {@code clgDegDays} property.
   * @see #clgDegDays
   */
  public void setClgDegDays(BStatusNumeric v) { set(clgDegDays, v, null); }

  //endregion Property "clgDegDays"

  //region Property "clgDegDaysTotal"

  /**
   * Slot for the {@code clgDegDaysTotal} property.
   * @see #getClgDegDaysTotal
   * @see #setClgDegDaysTotal
   */
  public static final Property clgDegDaysTotal = newProperty(Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code clgDegDaysTotal} property.
   * @see #clgDegDaysTotal
   */
  public BStatusNumeric getClgDegDaysTotal() { return (BStatusNumeric)get(clgDegDaysTotal); }

  /**
   * Set the {@code clgDegDaysTotal} property.
   * @see #clgDegDaysTotal
   */
  public void setClgDegDaysTotal(BStatusNumeric v) { set(clgDegDaysTotal, v, null); }

  //endregion Property "clgDegDaysTotal"

  //region Property "htgDegDays"

  /**
   * Slot for the {@code htgDegDays} property.
   * @see #getHtgDegDays
   * @see #setHtgDegDays
   */
  public static final Property htgDegDays = newProperty(Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code htgDegDays} property.
   * @see #htgDegDays
   */
  public BStatusNumeric getHtgDegDays() { return (BStatusNumeric)get(htgDegDays); }

  /**
   * Set the {@code htgDegDays} property.
   * @see #htgDegDays
   */
  public void setHtgDegDays(BStatusNumeric v) { set(htgDegDays, v, null); }

  //endregion Property "htgDegDays"

  //region Property "htgDegDaysTotal"

  /**
   * Slot for the {@code htgDegDaysTotal} property.
   * @see #getHtgDegDaysTotal
   * @see #setHtgDegDaysTotal
   */
  public static final Property htgDegDaysTotal = newProperty(Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code htgDegDaysTotal} property.
   * @see #htgDegDaysTotal
   */
  public BStatusNumeric getHtgDegDaysTotal() { return (BStatusNumeric)get(htgDegDaysTotal); }

  /**
   * Set the {@code htgDegDaysTotal} property.
   * @see #htgDegDaysTotal
   */
  public void setHtgDegDaysTotal(BStatusNumeric v) { set(htgDegDaysTotal, v, null); }

  //endregion Property "htgDegDaysTotal"

  //region Action "resetTotals"

  /**
   * Slot for the {@code resetTotals} action.
   * @see #resetTotals()
   */
  public static final Action resetTotals = newAction(0, null);

  /**
   * Invoke the {@code resetTotals} action.
   * @see #resetTotals
   */
  public void resetTotals() { invoke(resetTotals, null, null); }

  //endregion Action "resetTotals"

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
  public static final Type TYPE = Sys.loadType(BDegreeDays.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Initialization  /  Cleanup
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {
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
    if( property.equals(tempIn))
    {
      doCalculate();
    }
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if( slot.equals(tempIn) ||
        slot.equals(meanTemp) ||
        slot.equals(maxTemp)  ||
        slot.equals(minTemp)  ||
        slot.equals(baseTemperature) )
      return getFacets();
    return super.getSlotFacets(slot);
  }

  public void doResetTotals()
  {
    getHtgDegDaysTotal().setValue(0.0);
    getClgDegDaysTotal().setValue(0.0);
  }
  public void doCalculate()
  {
    if(ticket != null)
      ticket.cancel();
    ticket = Clock.schedule(this, BRelTime.makeMinutes(1), calculate, null);
    currentDay = Clock.time().getDayOfYear();
    //currentDay = getTime().getDayOfYear();
    BStatusNumeric currentTemp = getTempIn();
    if(!currentTemp.getStatus().isValid())
    {
      return;
    }
    if ( firstTime == true )	//Initialization
    {
      firstTime = false;
      previousDay = currentDay;
      setMinTemp( (BStatusNumeric)currentTemp.newCopy() );
      setMaxTemp( (BStatusNumeric)currentTemp.newCopy() );
      return;
    }
    if ( currentTemp.getValue() < getMinTemp().getValue() )	//Trap min temp
      setMinTemp((BStatusNumeric)currentTemp.newCopy());
    
    if ( currentTemp.getValue() > getMaxTemp().getValue() )	//Trap max temp
      setMaxTemp((BStatusNumeric)currentTemp.newCopy());
    
    if ( currentDay != previousDay )	//Calculate degree days once per day
    {
      previousDay = currentDay;
      getMeanTemp().setValue( (getMinTemp().getValue() + getMaxTemp().getValue())  / 2.0 );
      tempDiff = getMeanTemp().getValue() - getBaseTemperature();
      if ( tempDiff > 0.0 )  //Cooling Degree Days
      {
        getClgDegDays().setValue( Math.abs(tempDiff));
        getHtgDegDays().setValue( 0.0 );
      }

      else
      {
        getHtgDegDays().setValue( Math.abs(tempDiff));
        getClgDegDays().setValue( 0.0 );
      }
      getHtgDegDaysTotal().setValue(getHtgDegDaysTotal().getValue() + getHtgDegDays().getValue());
      getClgDegDaysTotal().setValue(getClgDegDaysTotal().getValue() + getClgDegDays().getValue());
      setMinTemp( (BStatusNumeric)currentTemp.newCopy() );
      setMaxTemp( (BStatusNumeric)currentTemp.newCopy() );
    }
  }

////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////
  boolean firstTime = true;
  double tempDiff;
  int currentDay;
  int previousDay;

  Clock.Ticket ticket = null;



}
