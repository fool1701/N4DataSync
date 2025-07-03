/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The NotRunningException indicates an attempt
 * to perform an operation which only makes sense
 * in a running graph.
 *
 * @author    Brian Frank
 * @creation  17 Mar 00
 * @version   $Revision: 2$ $Date: 2/28/01 9:45:11 AM EST$
 * @since     Baja 1.0 
 */
public class NotRunningException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified message.
   */
  public NotRunningException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with no message.
   */
  public NotRunningException()
  {  
    super();
  }
  
}
