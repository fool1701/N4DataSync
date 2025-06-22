/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.license;

/**
 * LicenseManager is used to access the Baja licensing framework.
 * The licensing framework is based on the notion of digitally signed 
 * set of key/value pairs stored in properties file.  The license 
 * manager is accessed via <code>Sys.getLicenseManager()</code>.
 *
 * @author    Brian Frank
 * @creation  3 Nov 01
 * @version   $Revision: 5$ $Date: 3/28/05 9:22:59 AM EST$
 * @since     Baja 1.0
 */
public interface LicenseManager
{

  /**
   * Get the specified feature by vendor and feature name.  Note 
   * this method does not automatically call Feature.check().
   *
   * @throws LicenseDatabaseException if the license database 
   *   is not installed or the feature is not found.
   */
  public Feature getFeature(String vendor, String feature)
    throws FeatureNotLicensedException, LicenseDatabaseException;

  /**
   * Verify that the specified feature exists and is not expired.
   *
   * @throws LicenseDatabaseException if the license database 
   *   is not installed or invalid.
   * @throws FeatureNotLicensedException if the feature is not
   *   licensed or has expired.
   */
  public Feature checkFeature(String vendor, String feature)
    throws FeatureNotLicensedException, LicenseDatabaseException;

  /**
   * Get the list of features which are licensed for this 
   * particular machine.  Each of these feature may or may 
   * not have an expiration date.
   *
   * @throws LicenseDatabaseException if the license database 
   *   is not installed or is invalid.
   */
  public Feature[] getFeatures()
    throws LicenseDatabaseException;

    
}
