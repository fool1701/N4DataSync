/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import javax.baja.file.*;

/**
 * Interface for PlatformDaemon licensing functions
 * 
 * A PlatformLicenseManager instance can be obtained by calling
 * PlatformDaemon.getLicenseManager()
 * 
 * @author    Matt Boon
 * @creation  7 Dec 06
 * @version   $Revision: 1$ $Date: 12/8/06 9:39:10 AM EST$
 * @since     Baja 1.0
 */
public interface PlatformLicenseManager
{
  /**
   * Return the remote host's host ID 
   */
  String getHostId()
    throws Exception;

  /**
   * Return the files containing remote host's valid licenses
   */
  BIFile[] getLicenses()
    throws Exception;
  
  /**
   * Indicate if the licenses and certficates on this platform can be modified.
   */
  boolean getIsLicenseReadonly()
    throws Exception;

  /**
   * Install the given license files to the remote host.   
   * 
   * Files which are invalid, less current than licenses present on the host,
   * or are expired will be ignored.   Also, licenses present on the host
   * that are determined to be invalid or expired will be removed.
   * 
   * Station run state is not affected by this method, so to make
   * the changes effective, the caller may need to restart a running station.
   */
  void installLicenses(BIFile[] newLicenses, IPlatformOperationListener listener)
    throws Exception;
}
