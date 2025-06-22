/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.sys.*;

/**
 * NullOrdException is thrown when attempting a BOrd
 * operation on the null ord.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 1$ $Date: 12/12/02 10:20:28 AM EST$
 * @since     Baja 1.0 
 */
public class NullOrdException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message and cause.
   */
  public NullOrdException(String msg, Throwable cause)
  {  
    super(msg, cause);
  }

  /**
   * Constructor with specified detailed message.
   */
  public NullOrdException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with no message.
   */
  public NullOrdException()
  {  
  }
       
}
