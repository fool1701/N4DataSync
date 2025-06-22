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
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BReversePolarityConversion is a singleton instance used to 
 * perform a logical not between the proxy value space and the 
 * device value space.
 *
 * @author    Brian Frank       
 * @creation  9 Feb 05
 * @version   $Revision: 2$ $Date: 7/22/08 10:37:26 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BReversePolarityConversion
  extends BProxyConversion
{                                       

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  /**
   * Convert proxy value to be logical not of device value.
   */
  public void convertDeviceToProxy(BProxyExt ext, BStatusValue deviceValue, BStatusValue proxyValue)
    throws Exception
  {
    if (deviceValue instanceof BStatusBoolean)
    {
      convert(ext, (BStatusBoolean)deviceValue, (BStatusBoolean)proxyValue);
    }
    else
    {
      proxyValue.copyFrom(deviceValue);
    }
  }

  /**
   * Convert device value to be logical not of proxy value.
   */
  public void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception
  {
    if (proxyValue instanceof BStatusBoolean)
    {
      convert(ext, (BStatusBoolean)proxyValue, (BStatusBoolean)deviceValue);
    }
    else
    {
      deviceValue.copyFrom(proxyValue);
    }
  }

  /**
   * Utility to convert device <-> proxy units.
   */
  private void convert(BProxyExt ext, BStatusBoolean from, BStatusBoolean to)
  {                      
    to.setValue(!from.getValue());
    to.setStatus(from.getStatus());
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
  public static final BReversePolarityConversion DEFAULT = new BReversePolarityConversion();
  private BReversePolarityConversion() {}

  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReversePolarityConversion.class);
  
}
