/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import javax.baja.naming.*;
import javax.baja.file.*;
import javax.baja.security.*;

import java.net.*;

/**
 * Represents the state of a single station running on a remote host,
 * and provides a simple control interface for it.
 * 
 * RemoteStation instances can be found using StationManager objects.
 * 
 * @author    Matt Boon       
 * @creation  04 Feb 05
 * @version   $Revision: 15$ $Date: 12/14/05 10:16:01 AM EST$
 * @since     Baja 1.0
 */
public interface RemoteStation
{
  /**
   * Life cycle status of the station
   */
  BStationStatus getStatus();

  /**
   * Name of the directory under !stations where the station is installed
   */
  String getName();

  /**
   * ORD that can be followed to open the station in workbench
   */
  BOrd getFoxOrd();

  /**
   * Poll the platform daemon and update the properties of this 
   * object accordingly
   */
  void poll()
    throws Exception;

  /**
   * Return true if the station can be started.  
   * 
   * If false is returned, the host must be rebooted before the station can
   * be started
   */
  boolean canStart()
    throws Exception;

  /**
   * Start the remote station and wait until it its startup sequence completes
   * successfully
   * 
   * @param listener receives status updates and allows the caller to cancel the
   * wait for startup to finish.   Cancelling does not interrupt the station startup,
   * that must be done explicitly with a stop or kill method.
   */
  void start(IPlatformOperationListener listener)
    throws Exception;

  /**
   * Start the remote station 
   */
  void startAsync()
    throws Exception;

  /**
   * Request that the running remote station save its state 
   */
  void saveAsync()
    throws ConnectException,
           AuthenticationException;

  /**
   * Request that the running remote station save its state and block until 
   * the save has completed or failed, or is canceled 
   * 
   * @param listener receives status updates and allows the caller to cancel the
   * wait for the save to finish.  Cancelling does not affect the station's save 
   * operation. 
   */
  void save(IPlatformOperationListener listener)
    throws ConnectException,
           AuthenticationException;

  /**
   * Request that the running remote station stop gracefully 
   */
  void stopAsync()
    throws ConnectException,
           AuthenticationException;

  /**
   * Request that the running remote station stop gracefully and block 
   * until the station is idle or the operation is canceled
   * 
   * @param listener receives status updates and allows the caller to cancel the
   * wait for the station to be fully stopped.   Cancelling affects only the wait, 
   * and will not cause the shutdown to abort.
   */
  void stop(IPlatformOperationListener listener)
    throws ConnectException,
           AuthenticationException;

  /**
   * Return true if the station can be restarted.   If false, once the station
   * is stopped the host must be rebooted before it can be started again.
   */
  boolean canRestart()
    throws Exception;

  /**
   * Request that the running remote station restart itself
   */
  void restartAsync()
    throws Exception;

  /**
   * Request that the running remote station be terminated immediately
   */
  void killAsync()
    throws ConnectException,
           AuthenticationException;

  /**
   * Stop the the remote station then delete it from the host
   * 
   * @param listener receives status updates and allows the caller to cancel the
   * operation.   Cancelling stops the wait for the station shutdown, but will
   * not cause the shutdown operation to abort.   Once the station is shut down, 
   * the file deletion cannot be interrupted.
   */
  void delete(IPlatformOperationListener listener)
    throws ConnectException,
           AuthenticationException;

  /**
   * Stop the the remote station then rename it
   * 
   * @param newName new name for the remote station
   * @param listener receives status updates and allows the caller to cancel the
   * operation.   Cancelling stops the wait for the station shutdown, but will
   * not cause the shutdown operation to abort.   Once the station is shut down, 
   * the file rename cannot be interrupted.
   */
  void rename(String                     newName,
              IPlatformOperationListener listener)
    throws Exception;

  /**
   * Make a local copy of the station
   * 
   * @param listener receives status updates for the operation.   listener.isCanceled()
   * is not checked, so has no effect
   */
  void makeLocalCopy(BDirectory                 localStationDir,
                     IPlatformOperationListener listener)
    throws Exception;


  /**
   * Set / Update the certificate alias for this station.
   *
   * @param alias The new certificate alias for the station
   */
  void updateStationCertAlias(String alias) throws ConnectException;

  /**
   * Update TLS version for this station.
   *
   * @param tlsVersion The new TLS version for the station
   */
  void updateStationTlsVersion(String tlsVersion) throws ConnectException;
}
