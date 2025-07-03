/*
 * Copyright (c) 2019 Tridium, Inc. All Rights Reserved.
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
import javax.baja.sys.BBoolean;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconModule;

import com.tridium.driver.util.UnitConversionUtil;

/**
 * BLinearWithUnitConversion expands on BlinearCoversion by adding unit conversion before, or after, the
 * linear conversion with a scale and offset.
 *
 * @author Robert Staley on 23-Aug-2019
 * @since Niagara 4.9
 */
@NiagaraType
@NoSlotomatic
public final class BLinearWithUnitConversion
  extends BProxyConversion
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a LinearConversion instance with the specified scale
   * and offset to use for the device to proxy linear equation.
   */
  public static BLinearWithUnitConversion make(double scale, double offset, boolean applyUnitsToDevice)
  {
    if (scale == 1 && offset == 0 && !applyUnitsToDevice) { return DEFAULT; }
    return (BLinearWithUnitConversion) (new BLinearWithUnitConversion(scale, offset, applyUnitsToDevice).intern());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BLinearWithUnitConversion(double scale, double offset, boolean applyUnitsTo)
  {
    this.scale = scale;
    this.offset = offset;
    this.applyUnitsToDevice = applyUnitsTo;
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

  /**
   * Get the boolean value controlling when the unit conversion is applied.
   *
   * true indicates units are converted on the device value.
   * false indicates units are converted on the proxy value.
   */
  public boolean getApplyUnitsToDevice()
  {
    return applyUnitsToDevice;
  }

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  /**
   * Convert using forward equation: {@code proxy = device*scale + offset}.
   * <p>
   * Unit conversions are preformed on either the device value or the proxy value, based
   * on the value returned from {@code getApplyUnitsToDevice()}.
   *
   * true indicates units are converted on the device value.
   * false indicates units are converted on the proxy value.
   */
  public void convertDeviceToProxy(BProxyExt ext, BStatusValue deviceValue, BStatusValue proxyValue)
    throws Exception
  {
    if (deviceValue instanceof BStatusNumeric)
    {
      if (getApplyUnitsToDevice())
      {
        UnitConversionUtil.convertUnits(ext, (BStatusNumeric) deviceValue, (BStatusNumeric) proxyValue, true);
        BLinearConversion.convert(ext, (BStatusNumeric) proxyValue, (BStatusNumeric) proxyValue, true, scale, offset);
      }
      else
      {
        BLinearConversion.convert(ext, (BStatusNumeric) deviceValue, (BStatusNumeric) proxyValue, true, scale, offset);
        UnitConversionUtil.convertUnits(ext, (BStatusNumeric) proxyValue, (BStatusNumeric) proxyValue, true);
      }
    }
    else
    {
      proxyValue.copyFrom(deviceValue);
    }
  }

  /**
   * Convert using reverse equation:  {@code device = (proxy-offset)/scale}.
   * <p>
   * Unit conversions are preformed on either the device value or the proxy value, based
   * on the value returned from {@code getApplyUnitsToDevice()}.
   *
   * true indicates units are converted on the device value.
   * false indicates units are converted on the proxy value.
   */
  public void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception
  {
    if (proxyValue instanceof BStatusNumeric)
    {
      if (getApplyUnitsToDevice())
      {
        BLinearConversion.convert(ext, (BStatusNumeric) proxyValue, (BStatusNumeric) deviceValue, false, scale, offset);
        UnitConversionUtil.convertUnits(ext, (BStatusNumeric) deviceValue, (BStatusNumeric) deviceValue, false);
      }
      else
      {
        UnitConversionUtil.convertUnits(ext, (BStatusNumeric) proxyValue, (BStatusNumeric) deviceValue, false);
        BLinearConversion.convert(ext, (BStatusNumeric) deviceValue, (BStatusNumeric) deviceValue, false, scale, offset);
      }
    }
    else
    {
      deviceValue.copyFrom(proxyValue);
    }
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BLinearWithUnitConversion uses its scale/offset/applyUnitsToDevice to compute the hash code.
   */
  @Override
  public int hashCode()
  {
    if (hashCode == -1)
    {
      long hash = 23L + Double.doubleToRawLongBits(offset);
      hash = (hash * 37L) + Double.doubleToRawLongBits(scale);
      hash = (hash * 37L) + Boolean.valueOf(applyUnitsToDevice).hashCode();
      hashCode = (int) (hash >>> 32) ^ (int) hash;
    }

    return hashCode;
  }

  /**
   * BLinearWithUnitConversion uses its scale/offset/applyUnitsToDevice to determine equality.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof BLinearWithUnitConversion)
    {
      BLinearWithUnitConversion x = (BLinearWithUnitConversion) obj;
      return scale == x.scale && offset == x.offset && applyUnitsToDevice == x.applyUnitsToDevice;
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

    // unit conversion applied to device, or proxy (the default is proxy)
    final String conversionText = LEXICON.get("LinearWithUnitConversion.unit.conversion", context);
    final String deviceText = LEXICON.get("LinearWithUnitConversion.unit.conversion.device", context);
    final String proxyText = LEXICON.get("LinearWithUnitConversion.unit.conversion.proxy", context);
    s.append(" ").append(conversionText).append(" ").append(applyUnitsToDevice ? deviceText : proxyText);

    return s.toString();
  }

  public void encode(DataOutput out)
    throws IOException
  {
    out.writeDouble(scale);
    out.writeDouble(offset);
    out.writeBoolean(applyUnitsToDevice);
  }

  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readDouble(), in.readDouble(), in.readBoolean());
  }

  public String encodeToString()
  {
    return BDouble.encode(scale) + ";" + BDouble.encode(offset) + ";" + BBoolean.encode(applyUnitsToDevice);
  }

  public BObject decodeFromString(String s)
    throws IOException
  {
    String[] parts = s.split(";");

    return make(BDouble.decode(parts[0]), BDouble.decode(parts[1]), BBoolean.decode(parts[2]));
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * This is default instance has scale of 1, offset of 0, and performs unit conversion after data conversion.
   */
  public static final BLinearWithUnitConversion DEFAULT = new BLinearWithUnitConversion(1, 0, false);

  public Type getType() { return TYPE; }

  public static final Type TYPE = Sys.loadType(BLinearWithUnitConversion.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private static final LexiconModule LEXICON = LexiconModule.make("driver");

  private double scale;
  private double offset;
  private boolean applyUnitsToDevice;

  private int hashCode = -1;
}
