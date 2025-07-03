/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

/**
 * Checked exception for queue full.
 *
 * @author    Brian Frank
 * @creation  31 May 00
 * @version   $Revision: 3$ $Date: 2/7/04 10:04:41 AM EST$
 * @since     Baja 1.0 
 */
public class QueueFullException
  extends javax.baja.sys.BajaRuntimeException
{

  /**
   * Construct a new QueueFullException with
   * the specified message.
   */
  public QueueFullException(String msg) 
  { 
    super(msg); 
  }
  
  /**
   * Construct a new QueueFullException.
   */
  public QueueFullException() 
  {
  }
  
}
