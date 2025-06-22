/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The InvalidEnumException is used to indicate an
 * attempt to access an enum with an invalid name
 * or ordinal value.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 4$ $Date: 3/30/04 3:37:21 PM EST$
 * @since     Baja 1.0 
 */
public class InvalidEnumException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message.
   */
  public InvalidEnumException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with specified ordinal.
   */
  public InvalidEnumException(int ordinal)
  {  
    super(String.valueOf(ordinal));
  }
       
}
