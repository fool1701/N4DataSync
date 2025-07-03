/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.sys.*;

/**
 * UnboundException is thrown when attempting to access 
 * the target of an unbound BBinding.
 *
 * @author    Brian Frank
 * @creation  17 May 04
 * @version   $Revision: 2$ $Date: 12/22/05 10:22:56 AM EST$
 * @since     Baja 1.0
 */
public class UnboundException
  extends BajaRuntimeException
{
  /**
   * Construct with message and root cause.
   */
  public UnboundException(String msg, Throwable cause)
  {                        
    super(msg, cause);
  }

  /**
   * Construct with message.
   */
  public UnboundException(String msg)
  {                        
    super(msg);
  }

  /**
   * No arg constructor.
   */
  public UnboundException()
  {                        
    super();
  }
  
}
