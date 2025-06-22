/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.time;

/**
 * Interface for managing a computer's time zone and locale
 * 
 * <br>
 * A TimeManager can be obtained by calling <code>LocalPlatform.getTimeManager()</code>
 * <br>
 * Each new TimeManager will load the current SystemPlatformService properties.
 * 
 * @author    Frank Smith       
 * @creation  16 Jan 05
 * @version   $Revision: 2$ $Date: 3/20/08 11:45:55 AM EDT$
 * @since     Baja 1.0
 */
public interface TimeManager
{

////////////////////////////////////////////////////////////////
// Readonly
////////////////////////////////////////////////////////////////
  
  /**
   * Return true if the time properties (system time, time zone) for this platform are
   * readonly. You should not attempt to modify readonly values where possible.
   * <br>
   * @since Niagara 4.10
   */
  boolean isSystemTimeReadonly();
  
////////////////////////////////////////////////////////////////
// Time zone
////////////////////////////////////////////////////////////////

  /**
   * Return true if the time zone for the given id is supported by the operating system
   * and is compatible with the level of support the OS has for daylight-saving time
   * beginning and ending rules.
   * <br>
   * <b>Note:</b> Setting the time zone to one that is not supported by the OS will result
   * in the OS time zone being changed to UTC.
   *
   * @param tzId is the time zone identifier
   */
  boolean isTimeZoneSupported(String tzId);
    
  /**
   * Set the Niagara Environment's time zone to the one specified and return true if the
   * given time zone is supported by the operating system <b>and</b> is compatible with
   * the level of support the OS has for daylight-saving time beginning and ending rules.
   *
   * @param tzId is the time zone identifier
   *
   */
  boolean setTimeZone(String tzId);
  
  /**
   * Set the Niagara Environment's time zone to the one specified and return true if the
   * given time zone is supported by the operating system <b>and</b> is compatible with
   * the level of support the OS has for daylight-saving time beginning and ending rules.
   * <br>
   * If the time zone is not supported and allowUTC is true, set the OS time zone to
   * UTC and return true, otherwise return false.
   *
   * @param tzId is the time zone identifier
   * @param allowUTC indicates whether to set the time zone to UTC if the specified id is
   * not supported
   */
  boolean setTimeZone(String tzId, boolean allowUTC);

////////////////////////////////////////////////////////////////
// Locale
////////////////////////////////////////////////////////////////
  
  /**
   * Returns the locale string for the Niagara Environment
   */
  String getLocale();
    
  /**
   * Set the locale used by the Niagara Environment to the one specified
   * <br>
   * Return true if the change is successful; false if not
   *
   * @param newLocale is the new locale
   */
  boolean setLocale(String newLocale);
  
////////////////////////////////////////////////////////////////
// Save the platform properties
////////////////////////////////////////////////////////////////

  /**
   * Save the platform time properties
   */
  void saveProperties();

}