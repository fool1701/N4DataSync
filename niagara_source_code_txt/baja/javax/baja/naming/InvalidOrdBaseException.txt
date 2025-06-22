/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.sys.*;

/**
 * InvalidOrdBaseException is thrown when the base for
 * a query is not valid for the query scheme.
 *
 * @author    John Sublett
 * @creation  16 Dec 2002
 * @version   $Revision: 1$ $Date: 12/18/02 1:06:22 PM EST$
 * @since     Baja 1.0
 */
public class InvalidOrdBaseException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message and cause.
   */
  public InvalidOrdBaseException(String msg, Throwable cause)
  {  
    super(msg, cause);
  }

  /**
   * Constructor with specified detailed message.
   */
  public InvalidOrdBaseException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with no message.
   */
  public InvalidOrdBaseException()
  {  
  }
       
}
