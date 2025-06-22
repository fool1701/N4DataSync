/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * ServiceNotFoundException indicates an attempt to lookup
 * a service which is not registered.
 *
 * @author    Brian Frank
 * @creation  25 Jul 00
 * @version   $Revision: 2$ $Date: 9/4/03 3:00:15 PM EDT$
 * @since     Baja 1.0
 */
public class ServiceNotFoundException
  extends BajaRuntimeException
{

  /**
   * Construct a ServiceNotFoundException with the 
   * given message and cause.
   */
  public ServiceNotFoundException(String msg, Throwable cause)
  {
    super(msg, cause);    
  }

  /**
   * Construct a ServiceNotFoundException with the given message.
   */
  public ServiceNotFoundException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a ServiceNotFoundException.
   */
  public ServiceNotFoundException()
  {
  }
      
}
