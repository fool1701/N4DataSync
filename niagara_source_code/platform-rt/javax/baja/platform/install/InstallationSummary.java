/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.install;

import javax.baja.file.*;
import javax.baja.nre.platform.RuntimeProfile;
import java.util.Set;

/**
 * Summarizes the changes that will be made when a software installation
 * takes place
 * 
 * @author    Matt Boon       
 * @creation  3 Apr 07
 * @version   $Revision: 3$ $Date: 10/9/07 4:38:03 PM EDT$
 * @since     Baja 1.0
 */
public interface InstallationSummary
{
  /**
   * Return true if the requestion software installation is valid
   */
  boolean canInstall();

  /**
   * Returns an array of module or dist files that will be installed
   * to the target host
   */
  BIFile[] getFilesToInstall();

  /**
   * Returns an array of module names that will be uninstalled from the
   * target host
   */
  String[] getModulesToUninstall();

  /**
   * Returns an array of dependencies that can't be met by PlatformParts 
   * already on the target host, or by installing additional software
   * available on the local host.   If the array size is nonzero, the
   * requested installation cannot take place.
   */
  PlatformDependency[] getUnmetDependencies();

  /**
   * Returns an array of module or dist files that cannot be installed
   * because they specify exclusions that apply to the installation
   * request.  If the array size is nonzero, the requested installation
   * cannot take place.
   */
  BIFile[] getExcludedFiles();

  Set<RuntimeProfile> getUpdatedEnabledRuntimeProfiles();

}

