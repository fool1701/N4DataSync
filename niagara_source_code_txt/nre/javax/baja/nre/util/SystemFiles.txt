/*
 * Copyright 2022 Tridium Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.io.File;

import com.tridium.nre.util.NiagaraFiles;

/**
 * Provides locations of Niagara system files and directories of the local Niagara instance.
 *
 * Beginning with Niagara 4.13, locations of some files that were traditionally stored in Niagara
 * Home may be elsewhere for implementations that keep Niagara Home read-only. By using the methods
 * of this class in lieu of hardcoded paths to local files, solutions will work with these
 * implementations.
 *
 * For example, to access the local modules directory, do not use a hardcoded solution like:
 * {@code
 *   // DO NOT DO THIS!
 *   File modules = new File(SystemFiles.getNiagaraHomeDirectory(), "modules");
 * }
 *
 * Rather, use the utility function of this class:
 * {@code
 *   File modules = SystemFiles.getModulesDirectory();
 * }
 *
 * @author M Swainston on 4/13/2022.
 * @since Niagara 4.12
 */
public final class SystemFiles
{
  /**
   * Provides the location of the local platform.bog file.
   *
   * @return the location of the local platform.bog file
   */
  public static File getPlatformBog()
  {
    return NiagaraFiles.getPlatformBogPath();
  }

  /**
   * Provides the location of the local system.properties file.
   *
   * @return the location of the local system.properties file
   */
  public static File getSystemProperties()
  {
    return NiagaraFiles.getSystemPropertiesPath();
  }

  /**
   * Provides the location of the local license.properties file.
   *
   * @return the location of the local license.properties file
   */
  public static File getLicenseProperties()
  {
    return NiagaraFiles.getLicensePropertiesPath();
  }

  /**
   * Provides the location of the local Niagara Home directory.
   *
   * Note, do not use this method to construct the location of system subdirectories, such as the
   * {@code modules} directory, which have their own utility methods in this class
   * (see {@link #getModulesDirectory()}).
   *
   * @return the location of the local Niagara Home directory
   */
  public static File getNiagaraHomeDirectory()
  {
    return NiagaraFiles.getNiagaraHome();
  }

  /**
   * Determines if the home directory is read-only.
   */
  public static boolean isNiagaraHomeReadOnly()
  {
    return NiagaraFiles.isNiagaraHomeReadonly();
  }

  /**
   * Provides the location of local Niagara modules.
   *
   * @return the location of local Niagara modules
   */
  public static File getModulesDirectory()
  {
    return NiagaraFiles.getModulesPath();
  }

  /**
   * Provides the location of local Niagara certificates.
   *
   * @return the location of local Niagara certificates
   */
  public static File getPerpetualCertificatesDirectory()
  {
    return NiagaraFiles.getPerpetualCertificatesPath();
  }

  /**
   * Provides the location of local Niagara licenses.
   *
   * @return the location of local Niagara licenses
   */
  public static File getPerpetualLicensesDirectory()
  {
    return NiagaraFiles.getPerpetualLicensePath();
  }

  /**
   * Provides the location of local Niagara subscription certificates.
   *
   * @return the location of local Niagara subscription certificates
   * @since 4.13
   */
  public static File getSubscriptionCertificatesDirectory()
  {
    return NiagaraFiles.getSubscriptionCertificatesPath();
  }

  /**
   * Provides the location of local Niagara subscription licenses.
   *
   * @return the location of local Niagara subscription licenses
   * @since 4.13
   */
  public static File getSubscriptionLicensesDirectory()
  {
    return NiagaraFiles.getSubscriptionLicensePath();
  }

  private SystemFiles()
  {
  }
}
