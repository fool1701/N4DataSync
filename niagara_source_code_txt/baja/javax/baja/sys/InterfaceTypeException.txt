/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The InterfaceTypeException is thrown on operations
 * which require a concrete class.
 *
 * @author    Brian Frank
 * @creation  11 Dec 02
 * @version   $Revision: 1$ $Date: 12/12/02 2:51:53 PM EST$
 * @since     Baja 1.0 
 */
public class InterfaceTypeException
  extends BajaRuntimeException
{

  /**
   * Constructor with message.
   */
  public InterfaceTypeException(String msg)
  {  
    super(msg);
  }
           
}
