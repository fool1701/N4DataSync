/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.license;

/**
 * FeatureLicenseExpiredException indicates an attempt to use
 * a feature which has expired.
 *
 * @author    Brian Frank
 * @creation  3 Nov 01
 * @version   $Revision: 2$ $Date: 3/28/05 9:22:59 AM EST$
 * @since     Baja 1.0
 */
public class FeatureLicenseExpiredException
  extends FeatureNotLicensedException
{

  /**
   * Construct a FeatureLicenseExpiredException with the given message.
   */
  public FeatureLicenseExpiredException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a FeatureLicenseExpiredException.
   */
  public FeatureLicenseExpiredException()
  {
  }
      
}
