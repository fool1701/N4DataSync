/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.registry;

import javax.baja.sys.*;

/**
 * RegistryException indicates a registry problem.
 *
 * @author    Brian Frank
 * @creation  5 Dec 02
 * @version   $Revision: 1$ $Date: 12/12/02 10:20:15 AM EST$
 * @since     Baja 1.0
 */
public class RegistryException
  extends BajaRuntimeException
{

  /**
   * Construct a RegistryException with the given message and cause.
   */
  public RegistryException(String msg, Throwable cause)
  {
    super(msg, cause);    
  }

  /**
   * Construct a RegistryException with the given message.
   */
  public RegistryException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a RegistryException.
   */
  public RegistryException()
  {
  }
      
}
