/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import javax.baja.file.*;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.util.*;

import com.tridium.platform.daemon.*;

import java.util.Set;

/**
 * Configuration options for software installations performed using
 * PlatformDaemon
 * 
 * @author    Matt Boon
 * @creation  4 May 05
 * @version   $Revision: 7$ $Date: 6/1/07 11:56:30 AM EDT$
 * @since     Baja 1.0
 */
public abstract class InstallOperation
{
  /**
   * Private constructor.  Caller should use the factory.
   */
  protected InstallOperation()
  {
  }

  /**
   * Factory
   */
  public static InstallOperation make()
  {
    return new PlatformInstallOperation();
  }

  /**
   * If set to true, any TCP/IP settings changes that may be included
   * with backup distribution files will be ignored when the operation
   * is executed.
   */
  public abstract void setIgnoreTcpIpChanges(boolean value);

  /**
   * If set to true, any platform daemon authentication changes that may 
   * be included with distribution files will be ignored when the operation
   * is executed.
   */
  public abstract void setIgnoreAuthChanges(boolean value);

  /**
   * Add a module to be installed with the operation.   
   * 
   * @param moduleName name of the module to install (does not include the
   * .jar extension)
   * @param version version of the module to install, or Version.NULL for
   * latest available version
   */
  public abstract void installModule(String moduleName, Version version);

  /**
   * Add a distribution to be installed with the operation.   
   * 
   * @param distFile distribution file to install
   */
  public abstract void installDistribution(BIFile distFile)
    throws Exception;

  /**
   * Add a distribution to be installed with the operation.   
   * 
   * @param distName name of the distribution to install (does not
   * include the .dist extension)
   * @param version version of the distribution to install, or 
   * Version.NULL for latest available version
   */
  public abstract void installDistribution(String distName, Version version);

  /**
   * Uninstall a module
   */
  public abstract void uninstallModule(String moduleName);

  /**
   * Update the set of enabled runtime profiles on the remote Niagara 4 host.  Modules will be installed or uninstalled
   * accordingly.  For example, if a platform currently has only {@link RuntimeProfile#rt} enabled and has the "alarm-rt"
   * module installed, enabling {@link RuntimeProfile#ux} and {@link RuntimeProfile#wb} will cause "alarm-ux" and "alarm-wb"
   * to be installed.
   *
   * If either {@link RuntimeProfile#ux} or {@link RuntimeProfile#wb} is in the set without the other, the other
   * profile will be added automatically.  Any missing profiles required for the platform ({@link RuntimeProfile#rt})
   * will be added automatically as well.
   *
   * If any runtime profiles in the set are unsupported by the remote host's platform, an exception will be thrown
   * when the operation is attempted. To avoid this exception, use {@link InstallManager#getUnsupportedRuntimeProfiles()}
   * to validate the values.
   *
   * @param enabledRuntimeProfiles Set of runtime profiles that should be enabled after the operation is performed.
   *                               If null, profiles should be unchanged.
   *
   * @since Niagara 4.1
   */
  public abstract void updateEnabledRuntimeProfiles(Set<RuntimeProfile> enabledRuntimeProfiles);
}
