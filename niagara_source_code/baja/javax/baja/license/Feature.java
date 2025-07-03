/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.license;

/**
 * Feature encapsulates a feature specification for the 
 * licensing framework.  A feature is uniquely identified
 * by a vendor and feature name.  It contains an expiration
 * and a set of optional properties.
 *
 * @author    Brian Frank
 * @creation  3 Nov 01
 * @version   $Revision: 3$ $Date: 3/28/05 9:22:59 AM EST$
 * @since     Baja 1.0
 */
public interface Feature
{

  /**
   * Get the vendor name of the feature.
   */
  public String getVendorName();
  
  /**
   * Get the feature name of the feature.
   */
  public String getFeatureName();

  /**
   * Return if this feature is actively licensed.
   */
  public boolean isExpired();

  /**
   * If this Feature returns true for <code>isExpired()</code>
   * then throw <code>FeatureNotLicensedException</code>.
   */
  public void check()
    throws FeatureNotLicensedException;

  /**
   * Get the expiration time for this feature in millis since 
   * the epoch.  If the feature has no expiration date, then
   * return Long.MAX_VALUE.
   */
  public long getExpiration();

  /**
   * Get the list of property keys of this feature.
   */
  public String[] list();
  
  /**
   * Get a feature property, or return null if not found
   * in the license database.
   */
  public String get(String key);

  /**
   * Get a license property, or return def if not found
   * in the license database.
   */
  public String get(String key, String def);

  /**
   * Get a feature property as a boolean or return def is 
   * not found.  The boolean must be true or false (case 
   * insensitive) or an exception is thrown.
   */
  public boolean getb(String key, boolean def);

  /**
   * Get a feature property as an int or return def 
   * is not found.  If the value is not a properly
   * formatted integer than an exception is thrown.
   */
  public int geti(String key, int def);
  
}
