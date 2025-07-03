/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.license;

/**
 * LicenseDatabaseException indicates that the machine
 * has an invalid license database.  This could because
 * no licensing has been installed or that the installed
 * license is corrupt or invalid.
 *
 * @author    Brian Frank
 * @creation  3 Nov 01
 * @version   $Revision: 2$ $Date: 3/28/05 9:22:59 AM EST$
 * @since     Baja 1.0
 */
public class LicenseDatabaseException
  extends LicenseException
{

  /**
   * Construct a LicenseDatabaseException with the given message.
   */
  public LicenseDatabaseException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a LicenseDatabaseException.
   */
  public LicenseDatabaseException()
  {
  }
      
}
