/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.driver.util.UnitConversionUtil;

/**
 * BDefaultProxyConversion is the standard conversion type used
 * by ProxyExts.  For StatusNumerics it provides a default
 * conversion based on <code>parentPoint.facets.units</code> and
 * <code>deviceValueFacets.units</code>
 *
 * @author Brian Frank
 * @version $Revision: 2$ $Date: 7/22/08 10:37:26 AM EDT$
 * @creation 9 Feb 05
 * @since Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BDefaultProxyConversion
  extends BProxyConversion
{

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  /**
   * Copy the deviceValue to the proxyValue.  The default
   * implementation performs a unit conversion if using
   * StatusNumeric and <code>parentPoint.facets.units</code>
   * and <code>deviceValueFacets.units</code> are non-null and
   * different.
   */
  public void convertDeviceToProxy(BProxyExt ext, BStatusValue deviceValue, BStatusValue proxyValue)
    throws Exception
  {
    if (deviceValue instanceof BStatusNumeric)
    {
      UnitConversionUtil.convertUnits(ext, (BStatusNumeric) deviceValue, (BStatusNumeric) proxyValue, true);
    }
    else
    {
      proxyValue.copyFrom(deviceValue);
    }
  }

  /**
   * Copy the proxyValue to the deviceValue.   The default
   * implementation performs a unit conversion if using
   * StatusNumeric and <code>parentPoint.facets.units</code>
   * and <code>deviceValueFacets.units</code> are non-null and
   * different.
   */
  public void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception
  {
    if (proxyValue instanceof BStatusNumeric)
    {
      UnitConversionUtil.convertUnits(ext, (BStatusNumeric) proxyValue, (BStatusNumeric) deviceValue, false);
    }
    else
    {
      deviceValue.copyFrom(proxyValue);
    }
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  public boolean equals(Object obj)
  {
    return this == obj; // singleton
  }

  /**
   * The hashcode is always the same since this is a singleton.
   *
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    return System.identityHashCode(this);
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

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * This is the singleton instance.
   */
  public static final BDefaultProxyConversion DEFAULT = new BDefaultProxyConversion();

  private BDefaultProxyConversion() {}

  public Type getType() { return TYPE; }

  public static final Type TYPE = Sys.loadType(BDefaultProxyConversion.class);

}
