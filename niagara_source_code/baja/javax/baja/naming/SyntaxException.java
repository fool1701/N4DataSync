/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.sys.*;

/**
 * SyntaxException indicates an invalid ord or query syntax.
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 1$ $Date: 12/12/02 10:20:28 AM EST$
 * @since     Baja 1.0
 */
public class SyntaxException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified message and cause.
   */
  public SyntaxException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * Constructor with specified message.
   */
  public SyntaxException(String msg)
  {
    super(msg);
  }

  /**
   * Constructor with specified cause.
   */
  public SyntaxException(Throwable cause)
  {
    super(cause);
  }
  
}

