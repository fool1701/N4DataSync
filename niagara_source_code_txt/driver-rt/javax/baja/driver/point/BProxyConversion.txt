/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BProxyConversion is the base class for BSimples which provide 
 * conversion support between the device value space and the proxy 
 * value space.
 *
 * @author    Brian Frank       
 * @creation  9 Feb 05
 * @version   $Revision: 1$ $Date: 2/9/05 4:50:28 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public abstract class BProxyConversion
  extends BSimple
{                                       

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  /**
   * Convert the deviceValue to the proxyValue.  
   * Throw an exception to mark proxyValue as fault.
   */
  public abstract void convertDeviceToProxy(BProxyExt ext, BStatusValue deviceValue, BStatusValue proxyValue)
    throws Exception;

  /**
   * Convert the proxyValue to the deviceValue.
   * Throw an exception to mark deviceValue as fault.
   */
  public abstract void convertProxyToDevice(BProxyExt ext, BStatusValue proxyValue, BStatusValue deviceValue)
    throws Exception;

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProxyConversion.class);
  
}
