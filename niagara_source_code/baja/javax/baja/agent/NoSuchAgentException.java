/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import javax.baja.sys.*;

/**
 * NoSuchAgentException is thrown when expecting a specific
 * agent, but one is not found.
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 1$ $Date: 12/26/02 1:32:56 PM EST$
 * @since     Baja 1.0
 */
public class NoSuchAgentException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified message and cause.
   */
  public NoSuchAgentException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * Constructor with specified message.
   */
  public NoSuchAgentException(String msg)
  {
    super(msg);
  }

  /**
   * Constructor with specified cause.
   */
  public NoSuchAgentException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Default constructor.
   */
  public NoSuchAgentException()
  {
  }
  
}

