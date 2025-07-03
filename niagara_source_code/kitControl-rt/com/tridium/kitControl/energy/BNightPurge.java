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
import com.tridium.kitControl.hvac.*;

/** Night Purge Component
 *
 *   This component uses the two sets of temperature and humidity inputs
 *   to find the air supply with the least amount of heat when the
 *   purgeEnabled input is 'true'.  
 *   The freeCooling output will be set to false
 *     if outside >= inside 
 *   or set to  true 
 *     if outside <= inside - (abs)thresholdSpan 
 *     and insideTemp >= nightSetpoint.
 *
 *   For inside/outside comparisons, the user can select either temperature
 *   or enthalpy comparisons.
 *   There is also a low temperature check to protect against freezing.
 *
 * 
 * @author    Andy Saunders
 * @creation  20 April 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraProperty(
  name = "temperatureFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"fahrenheit\"), 1)"
)
@NiagaraProperty(
  name = "humidityFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"percent relative humidity\"), 1)"
)
@NiagaraProperty(
  name = "purgeEnabled",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "outsideTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "outsideHumidity",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "insideTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "insideHumidity",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "lowTemperatureLimit",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "nightSetpoint",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "outsideEnthalpy",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet("BFacets.makeNumeric(UnitDatabase.getUnit(\"btu per pound\"), 2)")
)
@NiagaraProperty(
  name = "insideEnthalpy",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet("BFacets.makeNumeric(UnitDatabase.getUnit(\"btu per pound\"), 2)")
)
@NiagaraProperty(
  name = "freeCooling",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "currentMode",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum(BNightPurgeMode.disabled)",
  flags = Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "setpointDeadband",
  type = "float",
  defaultValue = "1.0f"
)
@NiagaraProperty(
  name = "thresholdSpan",
  type = "float",
  defaultValue = "1.0f"
)
@NiagaraProperty(
  name = "useEnthalpy",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "freeCoolingCommand",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "useNullOutput",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraAction(
  name = "calculate",
  flags = Flags.HIDDEN
)
public class BNightPurge
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BNightPurge(3396181121)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "temperatureFacets"

  /**
   * Slot for the {@code temperatureFacets} property.
   * @see #getTemperatureFacets
   * @see #setTemperatureFacets
   */
  public static final Property temperatureFacets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("fahrenheit"), 1), null);

  /**
   * Get the {@code temperatureFacets} property.
   * @see #temperatureFacets
   */
  public BFacets getTemperatureFacets() { return (BFacets)get(temperatureFacets); }

  /**
   * Set the {@code temperatureFacets} property.
   * @see #temperatureFacets
   */
  public void setTemperatureFacets(BFacets v) { set(temperatureFacets, v, null); }

  //endregion Property "temperatureFacets"

  //region Property "humidityFacets"

  /**
   * Slot for the {@code humidityFacets} property.
   * @see #getHumidityFacets
   * @see #setHumidityFacets
   */
  public static final Property humidityFacets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("percent relative humidity"), 1), null);

  /**
   * Get the {@code humidityFacets} property.
   * @see #humidityFacets
   */
  public BFacets getHumidityFacets() { return (BFacets)get(humidityFacets); }

  /**
   * Set the {@code humidityFacets} property.
   * @see #humidityFacets
   */
  public void setHumidityFacets(BFacets v) { set(humidityFacets, v, null); }

  //endregion Property "humidityFacets"

  //region Property "purgeEnabled"

  /**
   * Slot for the {@code purgeEnabled} property.
   * @see #getPurgeEnabled
   * @see #setPurgeEnabled
   */
  public static final Property purgeEnabled = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code purgeEnabled} property.
   * @see #purgeEnabled
   */
  public BStatusBoolean getPurgeEnabled() { return (BStatusBoolean)get(purgeEnabled); }

  /**
   * Set the {@code purgeEnabled} property.
   * @see #purgeEnabled
   */
  public void setPurgeEnabled(BStatusBoolean v) { set(purgeEnabled, v, null); }

  //endregion Property "purgeEnabled"

  //region Property "outsideTemp"

  /**
   * Slot for the {@code outsideTemp} property.
   * @see #getOutsideTemp
   * @see #setOutsideTemp
   */
  public static final Property outsideTemp = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

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

  //region Property "outsideHumidity"

  /**
   * Slot for the {@code outsideHumidity} property.
   * @see #getOutsideHumidity
   * @see #setOutsideHumidity
   */
  public static final Property outsideHumidity = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code outsideHumidity} property.
   * @see #outsideHumidity
   */
  public BStatusNumeric getOutsideHumidity() { return (BStatusNumeric)get(outsideHumidity); }

  /**
   * Set the {@code outsideHumidity} property.
   * @see #outsideHumidity
   */
  public void setOutsideHumidity(BStatusNumeric v) { set(outsideHumidity, v, null); }

  //endregion Property "outsideHumidity"

  //region Property "insideTemp"

  /**
   * Slot for the {@code insideTemp} property.
   * @see #getInsideTemp
   * @see #setInsideTemp
   */
  public static final Property insideTemp = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code insideTemp} property.
   * @see #insideTemp
   */
  public BStatusNumeric getInsideTemp() { return (BStatusNumeric)get(insideTemp); }

  /**
   * Set the {@code insideTemp} property.
   * @see #insideTemp
   */
  public void setInsideTemp(BStatusNumeric v) { set(insideTemp, v, null); }

  //endregion Property "insideTemp"

  //region Property "insideHumidity"

  /**
   * Slot for the {@code insideHumidity} property.
   * @see #getInsideHumidity
   * @see #setInsideHumidity
   */
  public static final Property insideHumidity = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code insideHumidity} property.
   * @see #insideHumidity
   */
  public BStatusNumeric getInsideHumidity() { return (BStatusNumeric)get(insideHumidity); }

  /**
   * Set the {@code insideHumidity} property.
   * @see #insideHumidity
   */
  public void setInsideHumidity(BStatusNumeric v) { set(insideHumidity, v, null); }

  //endregion Property "insideHumidity"

  //region Property "lowTemperatureLimit"

  /**
   * Slot for the {@code lowTemperatureLimit} property.
   * @see #getLowTemperatureLimit
   * @see #setLowTemperatureLimit
   */
  public static final Property lowTemperatureLimit = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code lowTemperatureLimit} property.
   * @see #lowTemperatureLimit
   */
  public BStatusNumeric getLowTemperatureLimit() { return (BStatusNumeric)get(lowTemperatureLimit); }

  /**
   * Set the {@code lowTemperatureLimit} property.
   * @see #lowTemperatureLimit
   */
  public void setLowTemperatureLimit(BStatusNumeric v) { set(lowTemperatureLimit, v, null); }

  //endregion Property "lowTemperatureLimit"

  //region Property "nightSetpoint"

  /**
   * Slot for the {@code nightSetpoint} property.
   * @see #getNightSetpoint
   * @see #setNightSetpoint
   */
  public static final Property nightSetpoint = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code nightSetpoint} property.
   * @see #nightSetpoint
   */
  public BStatusNumeric getNightSetpoint() { return (BStatusNumeric)get(nightSetpoint); }

  /**
   * Set the {@code nightSetpoint} property.
   * @see #nightSetpoint
   */
  public void setNightSetpoint(BStatusNumeric v) { set(nightSetpoint, v, null); }

  //endregion Property "nightSetpoint"

  //region Property "outsideEnthalpy"

  /**
   * Slot for the {@code outsideEnthalpy} property.
   * @see #getOutsideEnthalpy
   * @see #setOutsideEnthalpy
   */
  public static final Property outsideEnthalpy = newProperty(Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY, new BStatusNumeric(), BFacets.makeNumeric(UnitDatabase.getUnit("btu per pound"), 2));

  /**
   * Get the {@code outsideEnthalpy} property.
   * @see #outsideEnthalpy
   */
  public BStatusNumeric getOutsideEnthalpy() { return (BStatusNumeric)get(outsideEnthalpy); }

  /**
   * Set the {@code outsideEnthalpy} property.
   * @see #outsideEnthalpy
   */
  public void setOutsideEnthalpy(BStatusNumeric v) { set(outsideEnthalpy, v, null); }

  //endregion Property "outsideEnthalpy"

  //region Property "insideEnthalpy"

  /**
   * Slot for the {@code insideEnthalpy} property.
   * @see #getInsideEnthalpy
   * @see #setInsideEnthalpy
   */
  public static final Property insideEnthalpy = newProperty(Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY, new BStatusNumeric(), BFacets.makeNumeric(UnitDatabase.getUnit("btu per pound"), 2));

  /**
   * Get the {@code insideEnthalpy} property.
   * @see #insideEnthalpy
   */
  public BStatusNumeric getInsideEnthalpy() { return (BStatusNumeric)get(insideEnthalpy); }

  /**
   * Set the {@code insideEnthalpy} property.
   * @see #insideEnthalpy
   */
  public void setInsideEnthalpy(BStatusNumeric v) { set(insideEnthalpy, v, null); }

  //endregion Property "insideEnthalpy"

  //region Property "freeCooling"

  /**
   * Slot for the {@code freeCooling} property.
   * @see #getFreeCooling
   * @see #setFreeCooling
   */
  public static final Property freeCooling = newProperty(Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY, new BStatusBoolean(), null);

  /**
   * Get the {@code freeCooling} property.
   * @see #freeCooling
   */
  public BStatusBoolean getFreeCooling() { return (BStatusBoolean)get(freeCooling); }

  /**
   * Set the {@code freeCooling} property.
   * @see #freeCooling
   */
  public void setFreeCooling(BStatusBoolean v) { set(freeCooling, v, null); }

  //endregion Property "freeCooling"

  //region Property "currentMode"

  /**
   * Slot for the {@code currentMode} property.
   * @see #getCurrentMode
   * @see #setCurrentMode
   */
  public static final Property currentMode = newProperty(Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY, new BStatusEnum(BNightPurgeMode.disabled), null);

  /**
   * Get the {@code currentMode} property.
   * @see #currentMode
   */
  public BStatusEnum getCurrentMode() { return (BStatusEnum)get(currentMode); }

  /**
   * Set the {@code currentMode} property.
   * @see #currentMode
   */
  public void setCurrentMode(BStatusEnum v) { set(currentMode, v, null); }

  //endregion Property "currentMode"

  //region Property "setpointDeadband"

  /**
   * Slot for the {@code setpointDeadband} property.
   * @see #getSetpointDeadband
   * @see #setSetpointDeadband
   */
  public static final Property setpointDeadband = newProperty(0, 1.0f, null);

  /**
   * Get the {@code setpointDeadband} property.
   * @see #setpointDeadband
   */
  public float getSetpointDeadband() { return getFloat(setpointDeadband); }

  /**
   * Set the {@code setpointDeadband} property.
   * @see #setpointDeadband
   */
  public void setSetpointDeadband(float v) { setFloat(setpointDeadband, v, null); }

  //endregion Property "setpointDeadband"

  //region Property "thresholdSpan"

  /**
   * Slot for the {@code thresholdSpan} property.
   * @see #getThresholdSpan
   * @see #setThresholdSpan
   */
  public static final Property thresholdSpan = newProperty(0, 1.0f, null);

  /**
   * Get the {@code thresholdSpan} property.
   * @see #thresholdSpan
   */
  public float getThresholdSpan() { return getFloat(thresholdSpan); }

  /**
   * Set the {@code thresholdSpan} property.
   * @see #thresholdSpan
   */
  public void setThresholdSpan(float v) { setFloat(thresholdSpan, v, null); }

  //endregion Property "thresholdSpan"

  //region Property "useEnthalpy"

  /**
   * Slot for the {@code useEnthalpy} property.
   * @see #getUseEnthalpy
   * @see #setUseEnthalpy
   */
  public static final Property useEnthalpy = newProperty(0, true, null);

  /**
   * Get the {@code useEnthalpy} property.
   * @see #useEnthalpy
   */
  public boolean getUseEnthalpy() { return getBoolean(useEnthalpy); }

  /**
   * Set the {@code useEnthalpy} property.
   * @see #useEnthalpy
   */
  public void setUseEnthalpy(boolean v) { setBoolean(useEnthalpy, v, null); }

  //endregion Property "useEnthalpy"

  //region Property "freeCoolingCommand"

  /**
   * Slot for the {@code freeCoolingCommand} property.
   * @see #getFreeCoolingCommand
   * @see #setFreeCoolingCommand
   */
  public static final Property freeCoolingCommand = newProperty(0, true, null);

  /**
   * Get the {@code freeCoolingCommand} property.
   * @see #freeCoolingCommand
   */
  public boolean getFreeCoolingCommand() { return getBoolean(freeCoolingCommand); }

  /**
   * Set the {@code freeCoolingCommand} property.
   * @see #freeCoolingCommand
   */
  public void setFreeCoolingCommand(boolean v) { setBoolean(freeCoolingCommand, v, null); }

  //endregion Property "freeCoolingCommand"

  //region Property "useNullOutput"

  /**
   * Slot for the {@code useNullOutput} property.
   * @see #getUseNullOutput
   * @see #setUseNullOutput
   */
  public static final Property useNullOutput = newProperty(0, true, null);

  /**
   * Get the {@code useNullOutput} property.
   * @see #useNullOutput
   */
  public boolean getUseNullOutput() { return getBoolean(useNullOutput); }

  /**
   * Set the {@code useNullOutput} property.
   * @see #useNullOutput
   */
  public void setUseNullOutput(boolean v) { setBoolean(useNullOutput, v, null); }

  //endregion Property "useNullOutput"

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
  public static final Type TYPE = Sys.loadType(BNightPurge.class);

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
    if( property.equals(purgeEnabled)    ||
        property.equals(nightSetpoint)   ||
        property.equals(lowTemperatureLimit)   ||
        property.equals(outsideTemp)     ||
        property.equals(outsideHumidity) ||
        property.equals(insideTemp)      ||
        property.equals(insideHumidity)  ||
        property.equals(thresholdSpan)   ||
        property.equals(freeCoolingCommand) ||
        property.equals(useEnthalpy)           )
      doCalculate();

  }

  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.equals(insideTemp)  ||
       slot.equals(outsideTemp) ||
       slot.equals(nightSetpoint) ||
       slot.equals(setpointDeadband) ||
       slot.equals(lowTemperatureLimit)  )
      return getTemperatureFacets();
    else if(slot.equals(insideHumidity) ||
            slot.equals(outsideHumidity)   )
      return getHumidityFacets();
    return super.getSlotFacets(slot);
  }

  public void atSteadyState()
  {
    doCalculate();
  }

  public void doCalculate()
  {
    double purgeTempLimit = getNightSetpoint().getValue();
    if( !getPurgeEnabled().getValue() )
    {
      setOutput(false);
      mode = DISABLED;
    }
    else if( !getOutsideTemp().getStatus().isValid()     || 
        !getOutsideHumidity().getStatus().isValid() || 
        !getInsideTemp().getStatus().isValid()      ||
        !getInsideHumidity().getStatus().isValid()     )
    {
      mode = INPUT_ERROR;
    }
    else
    {
      // all inputs are good
      // no need to do anything if outside temp is < lowTemperature limit
      if( getOutsideTemp().getValue() < getLowTemperatureLimit().getValue() )
      {
        mode = LOW_TEMPERATURE;
      }
      else
      {
        if(mode == SATISFIED)
          purgeTempLimit = getNightSetpoint().getValue() + (double)getSetpointDeadband();
        // is night purge needed
        if( getInsideTemp().getValue() < purgeTempLimit)
        {
          mode = SATISFIED;
        }
        else
        {
          // calculate enthalpies if necessary
          if( getUseEnthalpy() == true )
          {
            getOutsideEnthalpy().setValue( (double)Psychrometric.enthalpy((float)getOutsideTemp().getValue(), (float)getOutsideHumidity().getValue()) );
            getInsideEnthalpy().setValue( (double)Psychrometric.enthalpy((float)getInsideTemp().getValue(), (float)getInsideHumidity().getValue()) ) ;

            // format enthalpy strings
            boolean outEntError =  getOutsideEnthalpy().getValue() <= 0.0;
            getOutsideEnthalpy().setStatus( BStatus.makeFault(BStatus.make(0), outEntError ) );


            boolean inEntError = getInsideEnthalpy().getValue() <= 0.0;
            getInsideEnthalpy().setStatus( BStatus.makeFault(BStatus.make(0), inEntError ) );

            if (outEntError || inEntError) 
            {
              mode = INPUT_ERROR;
            }
            else
            {
              // compare enthalpies
              if( getOutsideEnthalpy().getValue() >= getInsideEnthalpy().getValue() )
              {
                mode = ENTHALPY_NO_FREE_COOLING;
              }
              else if( getOutsideEnthalpy().getValue() <= ( getInsideEnthalpy().getValue() - Math.abs((double)getThresholdSpan()) ) )
              {
                mode = ENTHALPY_FREE_COOLING;
              }
            }
          }
          else	// compare temperature only
          {
            getOutsideEnthalpy().setValue(0.0);
            getInsideEnthalpy().setValue( 0.0);
            getOutsideEnthalpy().setStatus( BStatus.make(0, BFacets.make("unused", true) ) );
            getInsideEnthalpy().setStatus( BStatus.make(0, BFacets.make("unused", true) ) );
            // compare temperatures
            if( getOutsideTemp().getValue() >= getInsideTemp().getValue() )
            {
              mode = TEMP_NO_FREE_COOLING;
            }
            else if( getOutsideTemp().getValue() <= ( getInsideTemp().getValue() - Math.abs((double)getThresholdSpan()) ) )
            {
              mode = TEMP_FREE_COOLING;
            }
          }
        }
      }
    }
    switch (mode) 
    {
    case DISABLED                :setOutput(false); getCurrentMode().setValue(BNightPurgeMode.disabled); break;
    case INPUT_ERROR             :setOutput(false); getCurrentMode().setValue(BNightPurgeMode.inputError); break;
    case LOW_TEMPERATURE         :setOutput(false); getCurrentMode().setValue(BNightPurgeMode.lowTemperature); break;
    case ENTHALPY_NO_FREE_COOLING:
    case TEMP_NO_FREE_COOLING    :setOutput(false); getCurrentMode().setValue(BNightPurgeMode.noFreeCooling); break;
    case ENTHALPY_FREE_COOLING   :
    case TEMP_FREE_COOLING       :setOutput(true) ; getCurrentMode().setValue(BNightPurgeMode.freeCooling); break;
    case SATISFIED               :setOutput(false); getCurrentMode().setValue(BNightPurgeMode.satisfied); break;
    default:

    }
  }

  private void setOutput(boolean value)
  {
    if(value)
      setFreeCooling( new BStatusBoolean(getFreeCoolingCommand(), BStatus.ok ) );
    else
    {
      if(getUseNullOutput())
        setFreeCooling(new BStatusBoolean(!getFreeCoolingCommand(), BStatus.nullStatus));
      else
        setFreeCooling(new BStatusBoolean(!getFreeCoolingCommand(), BStatus.ok));
    }
  }

////////////////////////////////////////////////////////////////
// constants
////////////////////////////////////////////////////////////////
  private static final int DISABLED                 = 0;
  private static final int INPUT_ERROR              = 1;
  private static final int LOW_TEMPERATURE          = 2;
  private static final int ENTHALPY_NO_FREE_COOLING = 3;
  private static final int ENTHALPY_FREE_COOLING    = 4;
  private static final int TEMP_NO_FREE_COOLING     = 5;
  private static final int TEMP_FREE_COOLING        = 6;
  private static final int SATISFIED                = 7;



////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////
  
  int mode = 0;
  Clock.Ticket ticket = null;

}
