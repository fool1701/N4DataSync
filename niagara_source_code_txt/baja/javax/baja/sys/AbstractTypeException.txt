/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The AbstractTypeException is thrown on operations
 * which require a concrete class.
 *
 * @author    Brian Frank
 * @creation  11 Dec 02
 * @version   $Revision: 1$ $Date: 12/11/02 9:30:29 AM EST$
 * @since     Baja 1.0 
 */
public class AbstractTypeException
  extends BajaRuntimeException
{

  /**
   * Constructor with message.
   */
  public AbstractTypeException(String msg)
  {  
    super(msg);
  }
           
}
