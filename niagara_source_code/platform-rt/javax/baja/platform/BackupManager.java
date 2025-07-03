/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import javax.baja.file.*;

/**
 * Interface for performing online or offline backups.
 * 
 * Instances of BackupManager can be obtained by calling 
 * getOfflineBackupManager() on the PlatformDaemon object for 
 * a host with no running stations
 * 
 * @author    Matt Boon       
 * @creation  13 Dec 05
 * @version   $Revision: 2$ $Date: 6/1/07 11:56:30 AM EDT$
 * @since     Baja 1.0
 */
public interface BackupManager
{
  /**
   * Write a backup of the connected host to the given file.  
   * 
   * The host can be restored to the state it was in when the file 
   * was successfully written using the InstallManager.install() and
   * InstallOperation.installDistribution() methods.
   * 
   * An exception will be thrown if any stations are running on the 
   * remote host.
   * 
   * @param backupFile file to which the backup will be written.   It should
   * have the ".dist" extension.
   * @param listener can be used to cancel the request, or receive status while 
   * the backup is in progress
   */
  void backup(BIFile backupFile, IPlatformOperationListener listener)
    throws Exception;
}
