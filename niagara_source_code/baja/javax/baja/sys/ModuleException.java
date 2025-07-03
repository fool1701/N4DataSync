/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * ModuleException is thrown when an problem is
 * encountered loading or accessing a module.
 *
 * @author    Brian Frank
 * @creation  25 Jul 00
 * @version   $Revision: 4$ $Date: 12/12/02 10:23:03 AM EST$
 * @since     Baja 1.0
 */
public class ModuleException
  extends BajaRuntimeException
{

  /**
   * Construct with the given message and nested exception.
   */
  public ModuleException(String msg, Throwable nested)
  {
    super(msg, nested);
  }

  /**
   * Construct a ModuleException with the given message.
   */
  public ModuleException(String msg)
  {
    super(msg);
  }

  /**
   * Construct a ModuleException.
   */
  public ModuleException()
  {
  }
    
}
