/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point.conv;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.driver.point.BProxyConversion;
import javax.baja.driver.point.BProxyExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLinearConversion converts between device value space and
 * proxy value space using a linear conversion with a scale
 * and offset.
 *
 * @author Brian Frank
 * @version $Revision: 2$ $Date: 1/25/08 4:04:10 PM EST$
 * @creation 9 Feb 05
 * @since Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BLinearConversion
  extends BProxyConversion
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a LinearConversion instance with the specified scale
   * and offset to use for the device to proxy linear equation.
   */
  public static BLinearConversion make(double scale, double offset)
  {
    if (scale == 1 && offset == 0) { return DEFAULT; }
    return (BLinearConversion) (new BLinearConversion(scale, offset).intern());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BLinearConversion(double scale, double offset)
  {
    this.scale = scale;
    this.offset = offset;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the scale value for the device to proxy linear equation.
   */
  public double getScale()
  {
    return scale;
  }

  /**
   * Get the offset value for the device to proxy linear equation.
   */
  public double getOffset()
  {
    return offset;
  }

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  /**
   * Convert using forward equation: <code>proxy = device*scale + offset</code>.
   */
  public void convertDeviceToProxy(BProxyExt ext, BStatusValue deviceValue, BStatusValue proxyValue)
    throws Exception
  {
    if (deviceValue instanceof BStatusNumeric)
    {
      convert(ext, (BStatusNumeric) deviceValue, (BStatusNumeric) proxyValue, true, scale, offset);
    }
    else
    {
      proxyValue.copyFrom(deviceValue);
    }
  }

  /**
   * Convert using reverse equation: <code>device = (proxy-offset)/scale</code>.
   */
  public void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception
  {
    if (proxyValue instanceof BStatusNumeric)
    {
      convert(ext, (BStatusNumeric) proxyValue, (BStatusNumeric) deviceValue, false, scale, offset);
    }
    else
    {
      deviceValue.copyFrom(proxyValue);
    }
  }

  /**
   * Utility to convert device <-> proxy units.
   * This method is also used by BLinearWithUnitConversion.
   */
  static void convert(BProxyExt ext, BStatusNumeric from, BStatusNumeric to, boolean deviceToProxy, double scale, double offset)
  {
    double fromValue = from.getValue();
    double toValue;

    if (deviceToProxy)
    { toValue = fromValue * scale + offset; }
    else
    { toValue = (fromValue - offset) / scale; }

    to.setStatus(from.getStatus());
    to.setValue(toValue);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * BLinearConversion uses its scale/offset to compute the hash code.
   *
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    if (hashCode == -1)
    {
      long hash = 23L + Double.doubleToRawLongBits(offset);
      hash = (hash * 37L) + Double.doubleToRawLongBits(scale);
      hashCode = (int) (hash >>> 32) ^ (int) hash;
    }
    return hashCode;
  }

  public boolean equals(Object obj)
  {
    if (obj instanceof BLinearConversion)
    {
      BLinearConversion x = (BLinearConversion) obj;
      return scale == x.scale && offset == x.offset;
    }
    return false;
  }

  public String toString(Context context)
  {
    StringBuilder s = new StringBuilder();

    // type
    s.append(TYPE.getDisplayName(context));

    // scale
    if (scale != 1) { s.append(" *").append(BDouble.toString(scale, context)); }

    // offset
    if (offset < 0) { s.append(' ').append(BDouble.toString(offset, context)); }
    else if (offset > 0) { s.append(" +").append(BDouble.toString(offset, context)); }

    return s.toString();
  }

  public void encode(DataOutput out)
    throws IOException
  {
    out.writeDouble(scale);
    out.writeDouble(offset);
  }

  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readDouble(), in.readDouble());
  }

  public String encodeToString()
  {
    return BDouble.encode(scale) + ";" + BDouble.encode(offset);
  }

  public BObject decodeFromString(String s)
    throws IOException
  {
    int semi = s.indexOf(';');
    return make(BDouble.decode(s.substring(0, semi)),
      BDouble.decode(s.substring(semi + 1)));
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * This is default instance has scale of 1 and offset of 0.
   */
  public static final BLinearConversion DEFAULT = new BLinearConversion(1, 0);

  public Type getType() { return TYPE; }

  public static final Type TYPE = Sys.loadType(BLinearConversion.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private double scale;
  private double offset;
  private int hashCode = -1;
}
