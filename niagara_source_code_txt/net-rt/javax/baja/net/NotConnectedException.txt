/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.net;

/**
 * NotConnectedException indicates an attempt to use
 * a BConnection which doesn't have a valid connection
 * established.
 *
 * @author    Brian Frank
 * @creation  28 Jun 01
 * @version   $Revision: 1$ $Date: 12/16/02 7:35:02 AM EST$
 * @since     Baja 1.0 
 */
public class NotConnectedException
  extends javax.baja.io.BajaIOException
{

  /**
   * Constructor with specified message.
   */
  public NotConnectedException(String msg)
  {  
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public NotConnectedException()
  {  
  }
  
}
