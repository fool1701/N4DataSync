/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;
 
import javax.baja.file.*;
import javax.baja.security.*;

import java.net.*;
import java.util.function.Supplier;

/**
 * Interface for PlatformDaemon station management functions
 *
 * A StationManager instance can be obtained by calling
 * PlatformDaemon.getStationManager()
 * 
 * @author    Matt Boon       
 * @creation  24 Feb 05
 * @version   $Revision: 7$ $Date: 12/14/05 10:16:01 AM EST$
 * @since     Baja 1.0
 */
public interface StationManager
{
  /**
   * Transfer a local station to the remote host.  If the station already
   * exists, then the existing station will be stopped, and any existing
   * files will be overwritten by the transfer
   * 
   * @param localSourceDirectory local directory which contains a
   * station's config.bog database, plus any additional station-related
   * files
   * @param targetName name to give the station on the remote host
   * @param listener can be used to cancel the request, or receive status during 
   * the operation
   * @param passPhraseSupplier provides the pass phrase for the local station's BOG
   *                           file.  Ignored for AX stations, required for Niagara 4
   *                           stations when the local config.bog is protected with
   *                           a passphrase that's different from the remote host's
   *                           system passphrase.
   *
   * @return interface to the updated or newly created station
   *
   * @since Niagara 4.1
   */
  RemoteStation createStation(BDirectory                 localSourceDirectory,
                              String                     targetName,
                              IPlatformOperationListener listener,
                              Supplier<char[]>           passPhraseSupplier)
    throws Exception;

  /**
   * Transfer a local station to the remote host.  If the station already
   * exists, then the existing station will be stopped, and any existing
   * files will be overwritten by the transfer.
   *
   * Note: If the local copy of the station config.bog file uses a passphrase
   * that's different from the target host's system passphrase, you need to
   * provide the passphrase value.  Use {@link #createStation(BDirectory, String, IPlatformOperationListener, Supplier)}
   * if this is the case.
   *
   * @param localSourceDirectory local directory which contains a
   * station's config.bog database, plus any additional station-related
   * files
   * @param targetName name to give the station on the remote host
   * @param listener can be used to cancel the request, or receive status during
   * the operation
   *
   * @return interface to the updated or newly created station
   */
  default RemoteStation createStation(BDirectory                 localSourceDirectory,
                                      String                     targetName,
                                      IPlatformOperationListener listener)
    throws Exception
  {
    return createStation(localSourceDirectory, targetName, listener, null);
  }


  /**
   * Return information about all of the stations on the given host
   */
  RemoteStation[] getAllStations()
    throws Exception;

  /**
   * Return information about the station with the given name, or
   * null if a station with that name does not exist.
   * 
   * @param stationName name of a station that's currently installed
   * on the remote host
   */
  RemoteStation getStation(String stationName)
    throws Exception;

  /**
   * Send a request to the platform daemon to stop all running stations,
   * and block until they have all stopped successfully.
   * 
   * @return array of stations which were running and are now stopped
   */
  RemoteStation[] stopAllStations(IPlatformOperationListener listener)
    throws Exception;

  /**
   * Request that the remote host be rebooted after gracefully shutting 
   * down all running stations
   */
  void rebootAsync()
    throws ConnectException,
           AuthenticationException;

  /**
   * Request that the remote host  be rebooted after gracefully shutting 
   * down all running stations, return after the reboot is complete and 
   * the platform daemon is running again
   */
  void rebootSync(IPlatformOperationListener listener)
    throws Exception;

}

