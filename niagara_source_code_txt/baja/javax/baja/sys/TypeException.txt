/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * TypeException is thrown when an problem is
 * encountered loading or accessing a Type.
 *
 * @author    Brian Frank
 * @creation  23 Jul 01
 * @version   $Revision: 3$ $Date: 12/12/02 10:23:08 AM EST$
 * @since     Baja 1.0
 */
public class TypeException
  extends BajaRuntimeException
{

  /**
   * Construct a TypeException with the given message.
   */
  public TypeException(String msg)
  {
    super(msg);
  }

  /**
   * Construct with the given message and nested exception.
   */
  public TypeException(String msg, Throwable nested)
  {
    super(msg, nested);
  }
    
}
