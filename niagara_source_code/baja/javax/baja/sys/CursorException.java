/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * CursorException is used to indicate invalid usage of a Cursor.
 *
 * @author    Brian Frank
 * @creation  2 May 02
 * @version   $Revision: 1$ $Date: 5/2/02 10:49:36 AM EDT$
 * @since     Baja 1.0 
 */
public class CursorException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified message and root cause.
   */
  public CursorException(String msg, Throwable cause)
  {  
    super(msg, cause);
  }

  /**
   * Constructor with specified message.
   */
  public CursorException(String msg)
  {  
    super(msg);
  }
       
}
