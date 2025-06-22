/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;
 
import javax.baja.naming.BHost;
import javax.baja.naming.BOrd;
import javax.baja.security.BUsernameAndPassword;

import com.tridium.platform.daemon.NiagaraPlatformDaemon;

/**
 * Provides a simple interface to a local or remote platform daemon, which
 * can be used for station control and file transfer
 * 
 * @author    Matt Boon       
 * @creation  04 Feb 05
 * @version   $Revision: 15$ $Date: 7/15/10 3:17:38 PM EDT$
 * @since     Baja 1.0
 */
public abstract class PlatformDaemon
{
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Factory
   * 
   * @param hostAddr host name or IP address where the platform daemon is running
   * @param port HTTP port on which the platform daemon is listening, default is 3011
   * @param username user name to use for authentication to the platform daemon
   * @param password password to use for authentication to the platform daemon
   */
  public static PlatformDaemon make(String hostAddr,
                                    int    port,
                                    String username,
                                    String password)
    throws Exception
  {
    return make((BHost)BOrd.make("ip:" + hostAddr).get(),
      port,
      port != 3011,
      new BUsernameAndPassword(username, password));
  }

  /**
   * Factory
   *
   * @param hostAddr host name or IP address where the platform daemon is running
   * @param port HTTP port on which the platform daemon is listening, default is 3011
   * @param secure if true, make a secure connection to the platform daemon
   * @param username user name to use for authentication to the platform daemon
   * @param password password to use for authentication to the platform daemon
   *
   * @since Niagara 4.0
   */
  public static PlatformDaemon make(String hostAddr,
                                    int    port,
                                    boolean secure,
                                    String username,
                                    String password)
    throws Exception
  {
    return make((BHost)BOrd.make("ip:" + hostAddr).get(),
      port,
      secure,
      new BUsernameAndPassword(username, password));
  }

  /**
   * Factory
   * 
   * @param host host where the platform daemon is running
   * @param port HTTP port on which the platform daemon is listening, default is 3011
   * @param credentials admin credentials for the platform daemon
   */
  public static PlatformDaemon make(BHost                host,
                                    int                  port,
                                    BUsernameAndPassword credentials)
    throws Exception
  {
    return make(host, port, port != 3011, credentials);
  }

  /**
   * Factory
   *
   * @param host host where the platform daemon is running
   * @param port HTTP port on which the platform daemon is listening,
   * default is 3011
   * @param secure if true, make a secure connection to the platform daemon
   * @param credentials admin credentials for the platform daemon
   *
   * @since Niagara 4.0
   */
  public static PlatformDaemon make(BHost                host,
                                    int                  port,
                                    boolean              secure,
                                    BUsernameAndPassword credentials)
    throws Exception
  {
    return NiagaraPlatformDaemon.make(host, port, secure, credentials);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Returns an object that can be used for station control
   */
  public abstract StationManager getStationManager();

  /**
   * Returns an object that can be used for file access and transfer
   */
  public abstract FileManager getFileManager();

  /**
   * Returns an object that can be used to install software to the
   * remote host
   */
  public abstract InstallManager getInstallManager();

  /**
   * Returns an object that can be used to manage the authentication
   * and access control settings for the remote host
   */
  public abstract DaemonSecurityManager getSecurityManager();

  /**
   * Return a BackupManager object that can make a backup from the remote
   * host while no station is running.   
   */
  public abstract BackupManager getOfflineBackupManager()
    throws Exception;

  /**
   * Return a PlatformLicenseManager object that can manage licenses on
   * the remote host.
   */
  public abstract PlatformLicenseManager getLicenseManager()
    throws Exception;

  public abstract void close()
    throws Exception;

  /**
   * Returns true if the remote host is running Niagara 4 software, or returns false
   * if it is running Niagara AX.
   *
   * @since Niagara 4.1
   */
  public abstract boolean isNiagara4Platform()
    throws Exception;
}
