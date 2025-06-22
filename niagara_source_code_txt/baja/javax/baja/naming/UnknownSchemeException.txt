/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.sys.*;

/**
 * UnknownSchemeException is thrown when an unregistered
 * scheme is encountered in parsing a BOrd.
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 1$ $Date: 12/12/02 10:20:28 AM EST$
 * @since     Baja 1.0
 */
public class UnknownSchemeException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified message and cause.
   */
  public UnknownSchemeException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * Constructor with specified message.
   */
  public UnknownSchemeException(String msg)
  {
    super(msg);
  }

  /**
   * Constructor with specified cause.
   */
  public UnknownSchemeException(Throwable cause)
  {
    super(cause);
  }
  
}

