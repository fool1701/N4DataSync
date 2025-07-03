/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.sys.*;

/**
 * The UnresolvedException is thrown when attempting 
 * to resolve the source object of a BOrd.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 1$ $Date: 12/12/02 10:20:28 AM EST$
 * @since     Baja 1.0 
 */
public class UnresolvedException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message and cause.
   */
  public UnresolvedException(String msg, Throwable cause)
  {  
    super(msg, cause);
  }

  /**
   * Constructor with specified detailed message.
   */
  public UnresolvedException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with no message.
   */
  public UnresolvedException()
  {  
  }
       
}
