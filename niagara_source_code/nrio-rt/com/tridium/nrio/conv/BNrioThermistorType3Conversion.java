/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.conv;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.driver.point.BProxyConversion;
import javax.baja.driver.point.BProxyExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.units.UnitException;

/**
 * @author    Bill Smith
 * @creation  9 Feb 05
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BNrioThermistorType3Conversion
  extends BProxyConversion
{
  public static final BNrioThermistorType3Conversion DEFAULT = new BNrioThermistorType3Conversion();

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.conv.BNrioThermistorType3Conversion(2979906276)1.0$ @*/
/* Generated Fri Jul 30 14:58:04 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioThermistorType3Conversion.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  /**
   * Copy the deviceValue to the proxyValue.
   */
  public void convertDeviceToProxy(BProxyExt ext, BStatusValue deviceValue, BStatusValue proxyValue)
    throws Exception
  {
    if (deviceValue instanceof BStatusNumeric)
    {
      BUnit proxyUnit = (BUnit)ext.getPointFacets().get(BFacets.UNITS, BUnit.NULL);
      BUnit deviceUnit = (BUnit)ext.getDeviceFacets().get(BFacets.UNITS, BUnit.NULL);

      boolean deviceOk = deviceUnit.isConvertible(OHMS_UNIT) || deviceUnit.isNull();
      boolean proxyOk = proxyUnit.isConvertible(CELSIUS_UNIT);

      if (deviceOk && proxyOk)
      {
        double ohms = deviceUnit.convertTo(OHMS_UNIT, ((BStatusNumeric)deviceValue).getValue());
        double celsius = convertTo(ohms, ohmsArray, celsiusArray);
        proxyValue.setStatus(deviceValue.getStatus());
        ((BStatusNumeric)proxyValue).setValue(CELSIUS_UNIT.convertTo(proxyUnit, celsius));
        return;
      }

      proxyValue.copyFrom(deviceValue);
      throw new UnitException("Not convertible: " + deviceUnit + " -> " + proxyUnit);
    }

    proxyValue.copyFrom(deviceValue);
    throw new UnitException("Not convertible: invalid point type");    
  }

  /**
   * Copy the proxyValue to the deviceValue.
   */
  public void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception
  {
    if (proxyValue instanceof BStatusNumeric)
    {
      BUnit proxyUnit = (BUnit)ext.getPointFacets().get(BFacets.UNITS, BUnit.NULL);
      BUnit deviceUnit = (BUnit)ext.getDeviceFacets().get(BFacets.UNITS, BUnit.NULL);

      boolean deviceOk = deviceUnit.isConvertible(OHMS_UNIT) || deviceUnit.isNull();
      boolean proxyOk = proxyUnit.isConvertible(CELSIUS_UNIT);

      if (deviceOk && proxyOk)
      {
        double celsius = proxyUnit.convertTo(CELSIUS_UNIT, ((BStatusNumeric)proxyValue).getValue());
        double ohms = convertTo(celsius, celsiusArray, ohmsArray);
        deviceValue.setStatus(proxyValue.getStatus());
        ((BStatusNumeric)deviceValue).setValue(OHMS_UNIT.convertTo(deviceUnit, ohms));
        return;
      }

      deviceValue.copyFrom(proxyValue);
      throw new UnitException("Not convertible: " + proxyUnit + " -> " + deviceUnit);
    }

    deviceValue.copyFrom(proxyValue);
    throw new UnitException("Not convertible: invalid point type");    
  }

  private double convertTo(double src, double[] srcArray, double[] destArray)
  {
    int band = 0, count;
    double p1, p2, v1, v2;

    count = srcArray.length;
      
    // Check boundry conditions
    if (src < srcArray[0]){
      return destArray[0];
    }

    if (src > srcArray[count - 1]){
      return destArray[count - 1];
    }

    if (src == srcArray[count - 1]){
      return destArray[count - 1];
    }

    // Find the band
    for (int i = 0; i < count - 1; i++){
      if ((src >= srcArray[i]) && (src < srcArray[i+1])){
        band = i;
        break;
      }
    }
    
    p1 = srcArray[band];
    p2 = srcArray[band + 1];
    v1 = destArray[band];
    v2 = destArray[band + 1];

    return v1 - ((p1 - src) * (v1 - v2) / (p1 - p2));
  }  

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  public boolean equals(Object obj)
  {                    
    return this == obj; // singleton
  }

  public String toString(Context context)
  {
    return TYPE.getDisplayName(context);
  }
  
  public void encode(DataOutput out)
    throws IOException
  {
  }
  
  public BObject decode(DataInput in)
    throws IOException
  {          
    return DEFAULT;
  }

  public String encodeToString()
  {
    return "";
  }
  
  public BObject decodeFromString(String s)
    throws IOException
  {
    return DEFAULT;
  }  

  /**
   * This is the singleton instance.
   */
  private BNrioThermistorType3Conversion() {}

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static BUnit OHMS_UNIT = BUnit.getUnit("ohm");
  private static BUnit CELSIUS_UNIT = BUnit.getUnit("celsius");

  private static double[] ohmsArray = new double[]{0.0f, 610f, 1060f, 1690f, 2320f, 3250f, 4620f, 
                                               6240f, 8197f, 10000f, 12268f, 15136f, 18787f, 23462f, 
                                               29490f, 37316f, 47549f, 61030f, 78930f, 100000f}; 
  private static double[] celsiusArray = new double[]{165f, 110f, 90f, 75f, 65f, 55f, 45f, 37f, 30f, 25f, 20f, 
                                               15f, 10f, 5f, 0.0f, -5f, -10f, -15f, -20f, -25f};
  
}
