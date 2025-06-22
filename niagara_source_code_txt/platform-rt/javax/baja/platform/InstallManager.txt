/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import java.util.Set;
import javax.baja.file.BIFile;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.platform.install.InstallationSummary;
import javax.baja.platform.install.PlatformPart;

/**
 * Interface for PlatformDaemon software installation functions
 * 
 * An InstallManager instance can be obtained by calling
 * PlatformDaemon.getInstallManager()
 * 
 * @author    Matt Boon
 * @creation  4 May 05
 * @version   $Revision: 7$ $Date: 6/1/07 11:56:30 AM EDT$
 * @since     Baja 1.0
 */
public interface InstallManager
{
  /**
   * Verify an install operation, then perform the install.
   */
  void install(InstallOperation operation, IPlatformOperationListener listener)
    throws Exception;

  /**
   * Verify an install operation that requires a passphrase, then perform the install.
   */
  void install(InstallOperation pOperation, IPlatformOperationListener iListener, char[] password)
    throws Exception;

  /**
   * Check an InstallOperation for validity.   If the returned summary has
   * no unmet dependencies, then it can be successfully installed.
   */
  InstallationSummary checkInstall(InstallOperation operation, IPlatformOperationListener listener)
    throws Exception;

  /**
   * Check if a Dist requires a password for install
   */
  boolean checkDistPasswordRequired(InstallOperation operation)
    throws Exception;

  /**
   * Enumerates all of the PlatformParts on the target host against which
   * software dependencies may be defined.
   */
  PlatformPart[] getPlatformParts(IPlatformOperationListener listener)
    throws Exception;

  /**
   * Imports a module or distribution file into the local computer's
   * software database.
   */
  void registerInstallableFile(BIFile file, IPlatformOperationListener listener)
    throws Exception;

  /**
   * If the remote host is running Niagara 4, returns a Set containing the runtime profiles currently
   * enabled, otherwise throws UnsupportedOperationException.
   *
   * @since Niagara 4.1
   */
  Set<RuntimeProfile> getEnabledRuntimeProfiles();

  /**
   * If the remote host is running Niagara 4, returns a Set containing the runtime profiles that are
   * unsupported by that host's platform, otherwise throws UnsupportedOperationException.
   *
   * @since Niagara 4.1
   */
  Set<RuntimeProfile> getUnsupportedRuntimeProfiles();
}
