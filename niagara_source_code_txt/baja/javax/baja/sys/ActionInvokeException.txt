/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The ActionInvokeException is an unchecked exception that
 * wraps an exception raised during an action invocation.
 *
 * @author    Brian Frank
 * @creation  26 Jan 01
 * @version   $Revision: 3$ $Date: 2/28/01 9:44:52 AM EST$
 * @since     Baja 1.0 
 */
public class ActionInvokeException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified message and target exception.
   */
  public ActionInvokeException(String gripe, Throwable ex)
  {  
    super(gripe, ex);
  }

  /**
   * Constructor with specified message.
   */
  public ActionInvokeException(Throwable ex)
  {  
    super(ex);
  }
  
  private Throwable ex;
  
}
