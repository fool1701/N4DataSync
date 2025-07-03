/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * ExpiredTicketException indicates an attempt to access
 * the properties of a ticket which has already expired.
 *
 * @author    Brian Frank
 * @creation  16 Nov 01
 * @version   $Revision: 1$ $Date: 11/25/01 2:19:26 PM EST$
 * @since     Baja 1.0
 */
public class ExpiredTicketException
  extends BajaRuntimeException
{

  /**
   * Construct a ExpiredTicketException with the given message.
   */
  public ExpiredTicketException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a ExpiredTicketException.
   */
  public ExpiredTicketException()
  {
  }
    
}
