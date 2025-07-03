/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.license;

import javax.baja.sys.*;

/**
 * LicenseException indicates a licensing problem.
 *
 * @author    Brian Frank
 * @creation  3 Nov 01
 * @version   $Revision: 1$ $Date: 11/25/01 2:12:37 PM EST$
 * @since     Baja 1.0
 */
public class LicenseException
  extends BajaRuntimeException
{

  /**
   * Construct a LicensedException with the given message.
   */
  public LicenseException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a LicensedException.
   */
  public LicenseException()
  {
  }
      
}
