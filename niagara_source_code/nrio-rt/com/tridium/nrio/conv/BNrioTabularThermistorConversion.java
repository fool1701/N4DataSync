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
import javax.baja.nre.util.Array;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.units.UnitException;

import com.tridium.util.EscUtil;

/**
 * @author    Bill Smith
 * @creation  11 Feb 05
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BNrioTabularThermistorConversion
  extends BProxyConversion
{
  private static final String DEFAULT_ENCODING = "Thermistor Type 3|0.0,165.0;610.0,110.0;1060.0,90.0;1690.0,75.0;2320.0,65.0;3250.0,55.0;4620.0,45.0;6240.0,37.0;8197.0,30.0;10000.0,25.0;12268.0,20.0;15136.0,15.0;18787.0,10.0;23462.0,5.0;29490.0,0.0;37316.0,-5.0;47549.0,-10.0;61030.0,-15.0;78930.0,-20.0;100000.0,-25.0;";
  public static final BNrioTabularThermistorConversion NULL = BNrioTabularThermistorConversion.make();
  public static final BNrioTabularThermistorConversion DEFAULT = BNrioTabularThermistorConversion.make(DEFAULT_ENCODING);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.conv.BNrioTabularThermistorConversion(2979906276)1.0$ @*/
/* Generated Fri Jul 30 14:58:04 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioTabularThermistorConversion.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  public static BNrioTabularThermistorConversion make()
  {
    return new BNrioTabularThermistorConversion();
  }

  public static BNrioTabularThermistorConversion make(String encoding)
  {
    try
    {
      return (BNrioTabularThermistorConversion) NULL.decodeFromString(encoding);
    }
    catch(Exception e)
    {
      return new BNrioTabularThermistorConversion();
    }
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BNrioTabularThermistorConversion()
  {
  }

////////////////////////////////////////////////////////////////
// Nrio
////////////////////////////////////////////////////////////////

  public Array<XYPoint> getPoints()
  {
    return points;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public void add(double x, double y)
  {
    XYPoint p = new XYPoint(x, y);
    points.add(p);
    refreshArrays();
  }

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  public void convertDeviceToProxy(BProxyExt ext, BStatusValue deviceValue, BStatusValue proxyValue)
    throws Exception
  {
    if (deviceValue instanceof BStatusNumeric)
    {
      BUnit proxyUnit = (BUnit)ext.getPointFacets().get(BFacets.UNITS, BUnit.NULL);
      BUnit deviceUnit = (BUnit)ext.getDeviceFacets().get(BFacets.UNITS, BUnit.NULL);

      boolean deviceOk = deviceUnit.isConvertible(OHMS_UNIT) || deviceUnit.isNull();
      boolean proxyOk = proxyUnit.isConvertible(CELSIUS_UNIT);

      if (ohmsArray == null)
      {
        deviceValue.copyFrom(proxyValue);
        throw new UnitException("Not convertible: invalid points table");
      }

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

  public void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception
  {
    if (proxyValue instanceof BStatusNumeric)
    {
      BUnit proxyUnit = (BUnit)ext.getPointFacets().get(BFacets.UNITS, BUnit.NULL);
      BUnit deviceUnit = (BUnit)ext.getDeviceFacets().get(BFacets.UNITS, BUnit.NULL);

      boolean deviceOk = deviceUnit.isConvertible(OHMS_UNIT) || deviceUnit.isNull();
      boolean proxyOk = proxyUnit.isConvertible(CELSIUS_UNIT);

      if (ohmsArray == null)
      {
        deviceValue.copyFrom(proxyValue);
        throw new UnitException("Not convertible: invalid points table");
      }

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
    return false;
  }

  public String toString(Context context)
  {
    return encodeToString();
  }

  public void regen()
  {
    points = points.sort();
    refreshArrays();
  }

  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }

  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  public String encodeToString()
  {
    StringBuilder sb = new StringBuilder();
    String descriptionx = EscUtil.slot.escape(description);
    sb.append(descriptionx).append("|");

    for (int i = 0; i < points.size(); i++)
    {
      XYPoint p = points.get(i);
      sb.append(p.x()).append(',').append(p.y()).append(';');
    }

    return sb.toString();
   }

  public BObject decodeFromString(String src)
    throws IOException
  {

    BNrioTabularThermistorConversion conv = new BNrioTabularThermistorConversion();

    int last = -1,sep;

    sep = src.indexOf('|');
    String descriptionx = src.substring(0, sep);
    conv.setDescription(EscUtil.slot.unescape(descriptionx));
    last = sep;

    sep = src.indexOf(';');
    while (true)
    {
      if (sep == -1) // it's the last pair
      {
        decodePoint(conv, src.substring(last + 1));
        break;
      }

      decodePoint(conv, src.substring(last + 1, sep));
      last = sep;
      sep = src.indexOf(';', last + 1);
    }

    conv.regen();
    return conv;
  }

  private void decodePoint(BNrioTabularThermistorConversion conv, String src)
  {
    int comma = src.indexOf(',');
    if (comma == -1) return;

    String xstr = src.substring(0, comma).trim();
    String ystr = src.substring(comma + 1).trim();

    if (xstr.length() > 0 && ystr.length() > 0)
    {
      try
      {
        double x = Double.parseDouble(xstr);
        double y = Double.parseDouble(ystr);
        conv.add(x, y);
      }
      catch(Exception e)
      {
      }
    }
  }

  private void refreshArrays()
  {
    if (points.size() >= 2)
    {
      ohmsArray = new double[points.size()];
      celsiusArray = new double[points.size()];

      for (int i = 0; i < points.size(); i++)
      {
        XYPoint pnt = points.get(i);
        ohmsArray[i] = pnt.x();
        celsiusArray[i] = pnt.y();
      }
    }
    else
    {
      ohmsArray = celsiusArray = null;
    }
  }


////////////////////////////////////////////////////////////////
// Inner Class XYPoint
////////////////////////////////////////////////////////////////

  public class XYPoint implements Comparable<Object>
  {
    public XYPoint(double x, double y)
    {
      this.x = x;
      this.y = y;
    }

    public double x()
    {
      return x;
    }

    public double y()
    {
      return y;
    }

    public int compareTo(Object o)
    {
      if (o instanceof XYPoint)
      {
        XYPoint p = (XYPoint) o;
        if (p.x > x) return -1;
        if (p.x < x) return 1;
        return 0;
      }

      throw new ClassCastException("object not of type XYPoint.class");
    }

    public boolean equals(Object o)
    {
      if (o instanceof XYPoint)
      {
        XYPoint p = (XYPoint) o;
        return (x == p.x && y == p.y);
      }

      return false;
    }

    public int hashCode()
    {
      return (String.valueOf(x) + "," + String.valueOf(y)).hashCode();
    }

    private double x;
    private double y;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static BUnit OHMS_UNIT = BUnit.getUnit("ohm");
  private static BUnit CELSIUS_UNIT = BUnit.getUnit("celsius");
  private Array<XYPoint> points = new Array<>(XYPoint.class);
  private double[] ohmsArray = null;
  private double[] celsiusArray = null;
  private String description = "";
}
