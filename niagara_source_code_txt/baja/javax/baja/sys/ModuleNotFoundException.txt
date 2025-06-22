/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * ModuleNotFoundException indicates an attempt to load
 * a module failed because a compatible module cannot
 * be found.
 *
 * @author    Brian Frank
 * @creation  25 Jul 00
 * @version   $Revision: 4$ $Date: 10/26/04 9:45:48 AM EDT$
 * @since     Baja 1.0
 */
public class ModuleNotFoundException
  extends ModuleException
{

  /**
   * Construct a ModuleNotFoundException for the given moduleName.
   */
  public ModuleNotFoundException(String moduleName, Throwable cause)
  {
    super(moduleName, cause);    
  }

  /**
   * Construct a ModuleNotFoundException with the given moduleName.
   */
  public ModuleNotFoundException(String moduleName)
  {
    super(moduleName);    
  }
  
  /**
   * Get the module name of the module not found.
   */
  public String getModuleName()
  {
    return getMessage();
  }    
}
