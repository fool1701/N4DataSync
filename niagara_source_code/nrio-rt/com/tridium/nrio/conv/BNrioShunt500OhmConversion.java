/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.conv;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.driver.point.BDefaultProxyConversion;
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
import javax.baja.util.BTypeSpec;

import com.tridium.nrio.NrioException;
import com.tridium.nrio.points.BNrioVoltageInputProxyExt;

/**
 * The B500OhmShuntConversion is a standard conversion
 * that corrects for the voltage input when used
 * with a 500 Ohm shunt.
 *
 * @author    Bill Smith
 * @creation  23 May 2008
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BNrioShunt500OhmConversion
  extends BProxyConversion
{
  public static final BNrioShunt500OhmConversion DEFAULT = BNrioShunt500OhmConversion.make();

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.conv.BNrioShunt500OhmConversion(2979906276)1.0$ @*/
/* Generated Fri Jul 30 14:58:04 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioShunt500OhmConversion.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// factory
////////////////////////////////////////////////////////////////

  public static BNrioShunt500OhmConversion make()
  {
    return new BNrioShunt500OhmConversion();
  }

  public static BNrioShunt500OhmConversion make(BProxyConversion subConv)
  {
    return new BNrioShunt500OhmConversion(subConv);
  }

////////////////////////////////////////////////////////////////
// constructor
////////////////////////////////////////////////////////////////

  private BNrioShunt500OhmConversion()
  {
    super(); 
    this.subConv = BDefaultProxyConversion.DEFAULT;
  }

  private BNrioShunt500OhmConversion(BProxyConversion subConv)
  {
    super(); 
    this.subConv = subConv;
  }

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
      //BUnit proxyUnit = (BUnit)ext.getPointFacets().get(BFacets.UNITS, BUnit.NULL);
      BUnit deviceUnit = (BUnit)ext.getDeviceFacets().get(BFacets.UNITS, BUnit.NULL);

      if (!ext.getType().is(BNrioVoltageInputProxyExt.TYPE))
      {
        throw new NrioException("500 Ohm shunt conversion can only be used with nrio voltage input");
      }

      if (deviceUnit.isConvertible(VOLTS_UNIT))
      {        
        double deviceVal = ((BStatusNumeric)deviceValue).getValue();
        double proxyVal = convertTo(deviceVal, src, dest);
        proxyValue.setStatus(deviceValue.getStatus());
        ((BStatusNumeric)proxyValue).setValue(proxyVal);
        subConv.convertDeviceToProxy(ext, proxyValue, proxyValue);
        return;
      }

      proxyValue.copyFrom(deviceValue);
      throw new UnitException("Not convertible: " + deviceUnit);
    }

    proxyValue.copyFrom(deviceValue);
    throw new NrioException("Not convertible: invalid point type");    
  }


  /**
   * Copy the proxyValue to the deviceValue.
   */
  public void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception
  {
//    System.out.println("*** convertProxyToDevice");
//    if (proxyValue instanceof BStatusNumeric)
//    {
//      BUnit proxyUnit = (BUnit)ext.getPointFacets().get(BFacets.UNITS, BUnit.NULL);
//      BUnit deviceUnit = (BUnit)ext.getDeviceFacets().get(BFacets.UNITS, BUnit.NULL);
//
//      //boolean deviceOk = deviceUnit.isConvertible(VOLTS_UNIT) || deviceUnit.isNull();
//      boolean proxyOk = proxyUnit.isConvertible(VOLTS_UNIT);
//
//      if (proxyOk)
//      {
//        double volts = proxyUnit.convertTo(VOLTS_UNIT, ((BStatusNumeric)proxyValue).getValue());
//        double ma = convertTo(volts, dest, src);
//        deviceValue.setStatus(proxyValue.getStatus());
//        ((BStatusNumeric)deviceValue).setValue(ma);
//        subConv.convertProxyToDevice(ext, proxyValue, deviceValue);
//        return;
//      }
//
//      deviceValue.copyFrom(proxyValue);
//      throw new UnitException("Not convertible: " + proxyUnit + " -> " + deviceUnit);
//    }

    deviceValue.copyFrom(proxyValue);
    throw new NrioException("Not convertible: invalid point type");    
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

  /**
   * B500OhmConversion uses it's subConversion to calculate it's hashcode
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    return subConv.hashCode();
  }
  
  public boolean equals(Object obj)
  {                    
    if (obj instanceof BNrioShunt500OhmConversion)
    {                        
      BNrioShunt500OhmConversion x = (BNrioShunt500OhmConversion) obj;
      if (x.getSubConversion().getType() == subConv.getType())
        return hashCode() == x.hashCode();
    }                                              
    return false;
  }

  public String toString(Context context)
  {
    return TYPE.getDisplayName(context);
  }
  
  public void encode(DataOutput out)
    throws IOException
  {
    subConv.getType().getTypeSpec().encode(out);
    subConv.encode(out);
  }
  
  public BObject decode(DataInput in)
    throws IOException
  {  
    BTypeSpec typeSpec = (BTypeSpec) BTypeSpec.DEFAULT.decode(in);        
    return BNrioShunt500OhmConversion.make((BProxyConversion)((BProxyConversion)typeSpec.getInstance()).decode(in));
  }

  public String encodeToString()
    throws IOException
  {
    StringBuilder buf = new StringBuilder();
    buf.append(subConv.getType().getTypeSpec().encodeToString());
    buf.append(";");
    buf.append(subConv.encodeToString());
    return buf.toString();
  }
  
  public BObject decodeFromString(String s)
    throws IOException
  {
    int semi = s.indexOf(";");
    BTypeSpec typeSpec = (BTypeSpec) BTypeSpec.DEFAULT.decodeFromString(s.substring(0, semi));        
    return BNrioShunt500OhmConversion.make((BProxyConversion)((BProxyConversion)typeSpec.getInstance()).decodeFromString(s.substring(semi+1)));
  }  
  
  public BProxyConversion getSubConversion()
  {
    return subConv; 
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static BUnit VOLTS_UNIT = BUnit.getUnit("volt");
  private static BUnit MA_UNIT = BUnit.getUnit("milliampere");
  private BProxyConversion subConv; 


  private static double[] src = new double[]{0.0f, 3.912f, 9.315f}; 
  private static double[] dest = new double[]{0f, 3.912f, 10.0};

}
