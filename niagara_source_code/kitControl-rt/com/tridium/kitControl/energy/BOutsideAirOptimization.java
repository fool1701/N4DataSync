/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.energy;

import java.io.*;
import java.text.*;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

import com.tridium.kitControl.enums.*;
import com.tridium.kitControl.hvac.*;

 /*
 * Outside Air Optimization
 *
 *   This object uses the two sets of temperature and humidity inputs
 *   to find the air supply with the least amount of heat.  The freeCooling
 *   output will be set to false if outside &gt;= inside
 *   and set to true if outside &lt;= inside - (abs)thresholdSpan.
 *   The user can select temperature or enthalpy comparisons.
 *   There is also a low temperature check to protect against freezing.
 * 
 * @author    Andy Saunders
 * @creation  24 Mar 2005
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
  name = "outsideEnthalpy",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outsideEnthalpyString",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\")",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "insideEnthalpy",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "insideEnthalpyString",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\")",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "freeCooling",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "currentMode",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum(BOutsideAirOptimizationMode.noFreeCooling)",
  flags = Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY
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
  name = "calculate"
)
public class BOutsideAirOptimization
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BOutsideAirOptimization(2203651733)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "outsideEnthalpy"

  /**
   * Slot for the {@code outsideEnthalpy} property.
   * @see #getOutsideEnthalpy
   * @see #setOutsideEnthalpy
   */
  public static final Property outsideEnthalpy = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), null);

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

  //region Property "outsideEnthalpyString"

  /**
   * Slot for the {@code outsideEnthalpyString} property.
   * @see #getOutsideEnthalpyString
   * @see #setOutsideEnthalpyString
   */
  public static final Property outsideEnthalpyString = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusString(""), null);

  /**
   * Get the {@code outsideEnthalpyString} property.
   * @see #outsideEnthalpyString
   */
  public BStatusString getOutsideEnthalpyString() { return (BStatusString)get(outsideEnthalpyString); }

  /**
   * Set the {@code outsideEnthalpyString} property.
   * @see #outsideEnthalpyString
   */
  public void setOutsideEnthalpyString(BStatusString v) { set(outsideEnthalpyString, v, null); }

  //endregion Property "outsideEnthalpyString"

  //region Property "insideEnthalpy"

  /**
   * Slot for the {@code insideEnthalpy} property.
   * @see #getInsideEnthalpy
   * @see #setInsideEnthalpy
   */
  public static final Property insideEnthalpy = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), null);

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

  //region Property "insideEnthalpyString"

  /**
   * Slot for the {@code insideEnthalpyString} property.
   * @see #getInsideEnthalpyString
   * @see #setInsideEnthalpyString
   */
  public static final Property insideEnthalpyString = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusString(""), null);

  /**
   * Get the {@code insideEnthalpyString} property.
   * @see #insideEnthalpyString
   */
  public BStatusString getInsideEnthalpyString() { return (BStatusString)get(insideEnthalpyString); }

  /**
   * Set the {@code insideEnthalpyString} property.
   * @see #insideEnthalpyString
   */
  public void setInsideEnthalpyString(BStatusString v) { set(insideEnthalpyString, v, null); }

  //endregion Property "insideEnthalpyString"

  //region Property "freeCooling"

  /**
   * Slot for the {@code freeCooling} property.
   * @see #getFreeCooling
   * @see #setFreeCooling
   */
  public static final Property freeCooling = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusBoolean(), null);

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
  public static final Property currentMode = newProperty(Flags.SUMMARY | Flags.TRANSIENT | Flags.READONLY, new BStatusEnum(BOutsideAirOptimizationMode.noFreeCooling), null);

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
  public static final Action calculate = newAction(0, null);

  /**
   * Invoke the {@code calculate} action.
   * @see #calculate
   */
  public void calculate() { invoke(calculate, null, null); }

  //endregion Action "calculate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutsideAirOptimization.class);

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
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
  }

  public void changed(Property property, Context context) 
  {
  	super.changed(property, context);
    if( !Sys.atSteadyState() || !isRunning() )
	    return;
    if( property.equals(outsideTemp)     ||
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
    if( !getOutsideTemp().getStatus().isValid()     || 
        !getOutsideHumidity().getStatus().isValid() || 
        !getInsideTemp().getStatus().isValid()      ||
        !getInsideHumidity().getStatus().isValid()     )
    {
      getCurrentMode().setValue( BOutsideAirOptimizationMode.inputError );
      return;
    }

    // all inputs are good
    if( getOutsideTemp().getValue() < getLowTemperatureLimit().getValue() )
    {
      setOutput(false);
      getCurrentMode().setValue(BOutsideAirOptimizationMode.lowTemperature) ;
      lowTemp = true;
      return;
    }

    // calculate enthalpies if necessary
    if( getUseEnthalpy() == true )
    {
      getOutsideEnthalpy().setValue( (double)Psychrometric.enthalpy((float)getOutsideTemp().getValue(), (float)getOutsideHumidity().getValue()) );
      getInsideEnthalpy().setValue( (double)Psychrometric.enthalpy((float)getInsideTemp().getValue(), (float)getInsideHumidity().getValue()) ) ;

      // format enthalpy strings
      if( getOutsideEnthalpy().getValue() > 0.0 )
        getOutsideEnthalpyString().setValue(  (int)getOutsideEnthalpy().getValue() + engUnits ) ;
      else
        getOutsideEnthalpyString().setValue( OUT_OF_RANGE );

      if( getInsideEnthalpy().getValue() > 0.0 )
        getInsideEnthalpyString().setValue( (int)getInsideEnthalpy().getValue() + engUnits ) ;
      else
        getInsideEnthalpyString().setValue( OUT_OF_RANGE );

      // compare enthalpies
      if( getOutsideEnthalpy().getValue() >= getInsideEnthalpy().getValue() )
      {
        setOutput(false);
        getCurrentMode().setValue( BOutsideAirOptimizationMode.noFreeCooling );
      }
      else if( getOutsideEnthalpy().getValue() <= (getInsideEnthalpy().getValue() - Math.abs((double)getThresholdSpan())) )
      {
        setOutput(true);
        getCurrentMode().setValue( BOutsideAirOptimizationMode.freeCooling );
      }
    }
    else	// compare temperature only
    {
      getOutsideEnthalpy().setValue(0.0);
      getInsideEnthalpy().setValue( 0.0);
      getOutsideEnthalpyString().setValue( UNUSED );
      getInsideEnthalpyString().setValue( UNUSED );
      // compare temperatures
      if( getOutsideTemp().getValue() >= getInsideTemp().getValue() )
      {
        setOutput(false);
        getCurrentMode().setValue( BOutsideAirOptimizationMode.noFreeCooling );
      }
      else if( getOutsideTemp().getValue() <= (getInsideTemp().getValue() - Math.abs((double)getThresholdSpan())) )
      {
        setOutput(true);
        getCurrentMode().setValue( BOutsideAirOptimizationMode.freeCooling );
      }
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

  private BStatus getNoFreeCoolingStatus()
  {
    if(getUseNullOutput())
      return BStatus.nullStatus;
    return BStatus.ok;
  }

////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////

  static String engUnits = " BTU/lb"                         ;
  static String OUT_OF_RANGE =           "Input out of range";
  static String UNUSED =                 "Unused"            ;

  boolean lowTemp = false;



}
