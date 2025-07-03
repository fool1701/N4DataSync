/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.sys.BajaRuntimeException;

/**
 * The InvalidTypeException is thrown when an attempt
 * is made to convert a lon data element with in invalid
 * LonElementQualifier.
 *
 * @author    Robert Adams
 * @creation  5 May 01
 * @version   $Revision: 1$ $Date: 6/13/01 12:26:09 PM$
 * @since     Niagara 3.0
 */
public class InvalidTypeException
  extends BajaRuntimeException
{
  /**
   * Constructor with specified message.
   */
  public InvalidTypeException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with no message.
   */
  public InvalidTypeException()
  {  
    super();
  }
  
}
