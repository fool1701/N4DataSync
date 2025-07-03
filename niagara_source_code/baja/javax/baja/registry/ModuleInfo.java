/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.registry;

import javax.baja.util.*;
import javax.baja.nre.platform.RuntimeProfile;

/**
 * ModuleInfo provides summary information about an installed module.
 *
 * @author    Brian Frank
 * @creation  5 Dec 02
 * @version   $Revision: 8$ $Date: 7/6/11 2:26:37 PM EDT$
 * @since     Baja 1.0
 */
public interface ModuleInfo
{

  RuntimeProfile getRuntimeProfile();

  String getModuleName();

  String getModulePartName();
    
  /**
   * Get the Baja specification version that this
   * module implements.  If it does not implement
   * any Baja specification currently published
   * then return Version.ZERO.
   */
  Version getBajaVersion();

  /**
   * Get the vendor name for the module.
   */
  String getVendor();

  /**
   * Get the vendor specific version of this module.
   */
  Version getVendorVersion();
  
  /**
   * Get a short description of the module.
   */
  String getDescription();
  
  /**
   * Get the list of module dependencies or an empty 
   * array if none.
   */
  DependencyInfo[] getDependencies();
  
  /**
   * Get the types available in this module.
   */
  TypeInfo[] getTypes();
  
  /**
   * Get whether this module was loaded from a non
   * persisted source.
   */
  boolean isTransient();
 
}
