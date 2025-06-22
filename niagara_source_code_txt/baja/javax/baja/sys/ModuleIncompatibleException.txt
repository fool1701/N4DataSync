/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * ModuleIncompatibleException indicates an incompatible
 * vendor name or version number.
 *
 * @author    Brian Frank
 * @creation  25 Jul 00
 * @version   $Revision: 1$ $Date: 2/6/02 1:03:20 PM EST$
 * @since     Baja 1.0
 */
public class ModuleIncompatibleException
  extends ModuleException
{

  /**
   * Construct a ModuleIncompatibleException with the given message.
   */
  public ModuleIncompatibleException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a ModuleIncompatibleException.
   */
  public ModuleIncompatibleException()
  {
  }
      
}
