/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.license;

/**
 * FeatureNotLicensedException indicates an attempt to use
 * a feature which not currently licensed for this machine.
 *
 * @author    Brian Frank
 * @creation  3 Nov 01
 * @version   $Revision: 2$ $Date: 3/28/05 9:22:59 AM EST$
 * @since     Baja 1.0
 */
public class FeatureNotLicensedException
  extends LicenseException
{

  /**
   * Construct a FeatureNotLicensedException with the given message.
   */
  public FeatureNotLicensedException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a FeatureNotLicensedException.
   */
  public FeatureNotLicensedException()
  {
  }
      
}
