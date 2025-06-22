/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import javax.baja.sys.*;

/**
 * UnsupportedFormatException is thrown when attempting
 * to access a TranfserData with a format that is not
 * supported.
 *
 * @author    Brian Frank
 * @creation  13 Jan 03
 * @version   $Revision: 1$ $Date: 1/13/03 11:00:06 AM EST$
 * @since     Baja 1.0
 */
public class UnsupportedFormatException
  extends BajaRuntimeException
{
  /**
   * Constructor with specified message and cause.
   */
  public UnsupportedFormatException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * Constructor with specified message.
   */
  public UnsupportedFormatException(String msg)
  {
    super(msg);
  }

  /**
   * Constructor with specified cause.
   */
  public UnsupportedFormatException(Throwable cause)
  {
    super(cause);
  }
  
}

