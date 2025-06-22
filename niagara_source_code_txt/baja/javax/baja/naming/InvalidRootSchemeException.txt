/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.sys.*;

/**
 * InvalidRootSchemeException is thrown when a non-root scheme
 * is used as the first scheme in an ord.
 *
 * @author    John Sublett
 * @creation  16 Dec 2002
 * @version   $Revision: 1$ $Date: 12/18/02 1:06:23 PM EST$
 * @since     Baja 1.0
 */
public class InvalidRootSchemeException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message and cause.
   */
  public InvalidRootSchemeException(String msg, Throwable cause)
  {  
    super(msg, cause);
  }

  /**
   * Constructor with specified detailed message.
   */
  public InvalidRootSchemeException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with no message.
   */
  public InvalidRootSchemeException()
  {  
  }
       
}
