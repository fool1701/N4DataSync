/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.registry;

import javax.baja.util.*;

/**
 * DependencyInfo provides information on a module dependency.
 *
 * @author    Brian Frank
 * @creation  11 Feb 03
 * @version   $Revision: 4$ $Date: 3/28/05 9:23:02 AM EST$
 * @since     Baja 1.0
 */
public interface DependencyInfo
{
  /**
   * Get the dependent module name.
   */
  public String getModulePartName();

  /**
   * Get the dependent ModuleInfo, or null if not installed.
   */
  public ModuleInfo getModuleInfo();

  /**
   * Get the dependent Baja version number or null.
   */
  public Version getBajaVersion();
  
  /**
   * Get the dependent vendor name or null.
   */
  public String getVendor();

  /**
   * Get the dependent vendor version number or null.
   */
  public Version getVendorVersion();
  
}
