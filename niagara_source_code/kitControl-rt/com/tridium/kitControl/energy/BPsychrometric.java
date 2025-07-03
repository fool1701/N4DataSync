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

/** BPsychrometric Component
 *
 * 
 * @author    Andy Saunders
 * @creation  20 April 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraProperty(
  name = "unitSelect",
  type = "BEnglishMetric",
  defaultValue = "BEnglishMetric.english"
)
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
  name = "inTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inHumidity",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "outDewPoint",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outEnthalpy",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT,
  facets = @Facet("BFacets.makeNumeric(UnitDatabase.getUnit(\"btu per pound\"), 2)")
)
@NiagaraProperty(
  name = "outSatPress",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT,
  facets = @Facet("BFacets.makeNumeric(UnitDatabase.getUnit(\"pounds per square inch\"), 3)")
)
@NiagaraProperty(
  name = "outVaporPress",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT,
  facets = @Facet("BFacets.makeNumeric(UnitDatabase.getUnit(\"pounds per square inch\"), 3)")
)
@NiagaraProperty(
  name = "outWetBulbTemp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraAction(
  name = "calculate",
  flags = Flags.HIDDEN
)
public class BPsychrometric
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BPsychrometric(3691286769)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "unitSelect"

  /**
   * Slot for the {@code unitSelect} property.
   * @see #getUnitSelect
   * @see #setUnitSelect
   */
  public static final Property unitSelect = newProperty(0, BEnglishMetric.english, null);

  /**
   * Get the {@code unitSelect} property.
   * @see #unitSelect
   */
  public BEnglishMetric getUnitSelect() { return (BEnglishMetric)get(unitSelect); }

  /**
   * Set the {@code unitSelect} property.
   * @see #unitSelect
   */
  public void setUnitSelect(BEnglishMetric v) { set(unitSelect, v, null); }

  //endregion Property "unitSelect"

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

  //region Property "inTemp"

  /**
   * Slot for the {@code inTemp} property.
   * @see #getInTemp
   * @see #setInTemp
   */
  public static final Property inTemp = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inTemp} property.
   * @see #inTemp
   */
  public BStatusNumeric getInTemp() { return (BStatusNumeric)get(inTemp); }

  /**
   * Set the {@code inTemp} property.
   * @see #inTemp
   */
  public void setInTemp(BStatusNumeric v) { set(inTemp, v, null); }

  //endregion Property "inTemp"

  //region Property "inHumidity"

  /**
   * Slot for the {@code inHumidity} property.
   * @see #getInHumidity
   * @see #setInHumidity
   */
  public static final Property inHumidity = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inHumidity} property.
   * @see #inHumidity
   */
  public BStatusNumeric getInHumidity() { return (BStatusNumeric)get(inHumidity); }

  /**
   * Set the {@code inHumidity} property.
   * @see #inHumidity
   */
  public void setInHumidity(BStatusNumeric v) { set(inHumidity, v, null); }

  //endregion Property "inHumidity"

  //region Property "outDewPoint"

  /**
   * Slot for the {@code outDewPoint} property.
   * @see #getOutDewPoint
   * @see #setOutDewPoint
   */
  public static final Property outDewPoint = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code outDewPoint} property.
   * @see #outDewPoint
   */
  public BStatusNumeric getOutDewPoint() { return (BStatusNumeric)get(outDewPoint); }

  /**
   * Set the {@code outDewPoint} property.
   * @see #outDewPoint
   */
  public void setOutDewPoint(BStatusNumeric v) { set(outDewPoint, v, null); }

  //endregion Property "outDewPoint"

  //region Property "outEnthalpy"

  /**
   * Slot for the {@code outEnthalpy} property.
   * @see #getOutEnthalpy
   * @see #setOutEnthalpy
   */
  public static final Property outEnthalpy = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), BFacets.makeNumeric(UnitDatabase.getUnit("btu per pound"), 2));

  /**
   * Get the {@code outEnthalpy} property.
   * @see #outEnthalpy
   */
  public BStatusNumeric getOutEnthalpy() { return (BStatusNumeric)get(outEnthalpy); }

  /**
   * Set the {@code outEnthalpy} property.
   * @see #outEnthalpy
   */
  public void setOutEnthalpy(BStatusNumeric v) { set(outEnthalpy, v, null); }

  //endregion Property "outEnthalpy"

  //region Property "outSatPress"

  /**
   * Slot for the {@code outSatPress} property.
   * @see #getOutSatPress
   * @see #setOutSatPress
   */
  public static final Property outSatPress = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), BFacets.makeNumeric(UnitDatabase.getUnit("pounds per square inch"), 3));

  /**
   * Get the {@code outSatPress} property.
   * @see #outSatPress
   */
  public BStatusNumeric getOutSatPress() { return (BStatusNumeric)get(outSatPress); }

  /**
   * Set the {@code outSatPress} property.
   * @see #outSatPress
   */
  public void setOutSatPress(BStatusNumeric v) { set(outSatPress, v, null); }

  //endregion Property "outSatPress"

  //region Property "outVaporPress"

  /**
   * Slot for the {@code outVaporPress} property.
   * @see #getOutVaporPress
   * @see #setOutVaporPress
   */
  public static final Property outVaporPress = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), BFacets.makeNumeric(UnitDatabase.getUnit("pounds per square inch"), 3));

  /**
   * Get the {@code outVaporPress} property.
   * @see #outVaporPress
   */
  public BStatusNumeric getOutVaporPress() { return (BStatusNumeric)get(outVaporPress); }

  /**
   * Set the {@code outVaporPress} property.
   * @see #outVaporPress
   */
  public void setOutVaporPress(BStatusNumeric v) { set(outVaporPress, v, null); }

  //endregion Property "outVaporPress"

  //region Property "outWetBulbTemp"

  /**
   * Slot for the {@code outWetBulbTemp} property.
   * @see #getOutWetBulbTemp
   * @see #setOutWetBulbTemp
   */
  public static final Property outWetBulbTemp = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code outWetBulbTemp} property.
   * @see #outWetBulbTemp
   */
  public BStatusNumeric getOutWetBulbTemp() { return (BStatusNumeric)get(outWetBulbTemp); }

  /**
   * Set the {@code outWetBulbTemp} property.
   * @see #outWetBulbTemp
   */
  public void setOutWetBulbTemp(BStatusNumeric v) { set(outWetBulbTemp, v, null); }

  //endregion Property "outWetBulbTemp"

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
  public static final Type TYPE = Sys.loadType(BPsychrometric.class);

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
    if( property.getName().startsWith("in") ||
        property.equals(unitSelect)            )
     doCalculate();
    if( property.equals(unitSelect))
    {
      isEnglish = getUnitSelect().equals(BEnglishMetric.english);
      int precision = getTemperatureFacets().geti(BFacets.PRECISION, 1);
      if(isEnglish)
        setTemperatureFacets(BFacets.makeNumeric(UnitDatabase.getUnit("fahrenheit"), precision));
      else
        setTemperatureFacets(BFacets.makeNumeric(UnitDatabase.getUnit("celsius"), precision));
    }

  }

  public BFacets getSlotFacets(Slot slot)
  {
    isEnglish = getUnitSelect().equals(BEnglishMetric.english);
    if(slot.equals(inTemp) ||
       slot.equals(outDewPoint) ||
       slot.equals(outWetBulbTemp)  )
    {
      return getTemperatureFacets();
    }
    
    else if(slot.equals(inHumidity) )
      return getHumidityFacets();
    else if(slot.equals(outEnthalpy))
    {
      if(isEnglish)
        return enthalpyEnglishFacets;
      else
        return enthalpyMetricFacets;
    }
    else if(slot.equals(outSatPress) ||
            slot.equals(outVaporPress)  )
    {
      if(isEnglish)
        return pressureEnglishFacets;
      else
        return pressureMetricFacets;
    }
    return super.getSlotFacets(slot);
  }

  public void atSteadyState()
  {
    doCalculate();
  }

  public void doCalculate()
  {
    isEnglish = getUnitSelect().equals(BEnglishMetric.english);
    float temp = getInTempEnglish();
    float RH   = (float)getInHumidity().getValue();
    BStatusNumeric dewPointValue    = new BStatusNumeric();
    BStatusNumeric enthalpyValue    = new BStatusNumeric();
    BStatusNumeric vaporPressValue  = new BStatusNumeric();
    BStatusNumeric wetBulbTempValue = new BStatusNumeric();
    BStatusNumeric satPressValue    = new BStatusNumeric();
    boolean tempAndRhValid = isTempValid() && isRHValid();
    if(tempAndRhValid)
    {
      dewPointValue   .setValue((double)Psychrometric.dewpointTemperature( temp, RH ));
      enthalpyValue   .setValue((double)Psychrometric.enthalpy( temp, RH ));
      vaporPressValue .setValue((double)Psychrometric.vaporPressure( temp, RH ));
      wetBulbTempValue.setValue((double)Psychrometric.wetbulbTemperature( temp, RH ));
    }
    else
    {
      dewPointValue   .setValue(Double.NaN);
      enthalpyValue   .setValue(Double.NaN);
      vaporPressValue .setValue(Double.NaN);
      wetBulbTempValue.setValue(Double.NaN);
    }
    dewPointValue   .setStatus(BStatus.makeFault(getOutDewPoint()   .getStatus(), !tempAndRhValid));
    enthalpyValue   .setStatus(BStatus.makeFault(getOutEnthalpy()   .getStatus(), !tempAndRhValid));
    vaporPressValue .setStatus(BStatus.makeFault(getOutVaporPress() .getStatus(), !tempAndRhValid));
    wetBulbTempValue.setStatus(BStatus.makeFault(getOutWetBulbTemp().getStatus(), !tempAndRhValid));


    // calculate saturation pressure from temperature
    if(isTempValid())
    {
      satPressValue.setValue((double)Psychrometric.saturationPressure( temp ));
    }
    else
    {
      satPressValue.setValue(Double.NaN);
    }
    satPressValue.setStatus(BStatus.makeFault(getOutSatPress().getStatus(), !isTempValid()));

    if(!isEnglish)  // then convert to metric
    {
      dewPointValue   .setValue( convertTemp    (dewPointValue   .getValue()) );
      enthalpyValue   .setValue( convertEnthalpy(enthalpyValue   .getValue()) );
      vaporPressValue .setValue( convertPress   (vaporPressValue .getValue()) );
      wetBulbTempValue.setValue( convertTemp    (wetBulbTempValue.getValue()) );
      satPressValue   .setValue( convertPress   (satPressValue   .getValue()) );

    }

    setOutDewPoint   (dewPointValue   );
    setOutEnthalpy   (enthalpyValue   );
    setOutVaporPress (vaporPressValue );
    setOutWetBulbTemp(wetBulbTempValue);
    setOutSatPress   (satPressValue   );
    //
  }


	private boolean isTempValid()
  {
    return getInTemp().getStatus().isValid() && Psychrometric.isTempValid(getInTempEnglish());
  }
  
	private boolean isRHValid()
  {
    return getInHumidity().getStatus().isValid() && Psychrometric.isRHValid((float)getInHumidity().getValue());
  }
  
  private float getInTempEnglish()
  {
    float inTempValue = (float)getInTemp().getValue();
    if(isEnglish)
      return inTempValue;
    return 1.8f * inTempValue +32;
  }

  private double convertTemp(double value)
  {
    if(Double.isNaN(value))
      return value;
    return (value - 32.0) * fiveNineths;
  }

  // 1 BTU/lb = 2.32600 kilojoules per kilogram
  // kJ/kg = (btu/lb - 7.68.152) * 2.326;
  private double convertEnthalpy(double value)
  {
    if(Double.isNaN(value))
      return value;
    return (value - 7.68152) * 2.32600;
  }

  // 1 psi = 0.00689475729 kPa 
  private double convertPress(double value)
  {
    if(Double.isNaN(value))
      return value;
    return value * 6.89475729 ;
  }

  private static double fiveNineths = 5.0/9.0;
  private static BFacets pressureEnglishFacets = BFacets.makeNumeric(UnitDatabase.getUnit("pounds per square inch"), 3);
  private static BFacets pressureMetricFacets  = BFacets.makeNumeric(UnitDatabase.getUnit("kilopascal")            , 3);
  private static BFacets enthalpyEnglishFacets = BFacets.makeNumeric(UnitDatabase.getUnit("btu per pound"      ), 3);
  private static BFacets enthalpyMetricFacets  = BFacets.makeNumeric(UnitDatabase.getUnit("kilojoule per kilogram"), 3);
////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////

  Clock.Ticket ticket = null;
  private boolean isEnglish = true;

}
