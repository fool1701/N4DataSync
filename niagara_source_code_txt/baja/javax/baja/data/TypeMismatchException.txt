/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.data;

import javax.baja.sys.*;

/**
 * TypeMismatchException is thrown when one type is encountered when
 * a different type is expected.
 *
 * @author    John Sublett
 * @creation  19 Feb 2003
 * @version   $Revision: 1$ $Date: 2/27/03 9:18:33 AM EST$
 * @since     Baja 1.0
 */
public class TypeMismatchException
  extends BajaRuntimeException
{
  public TypeMismatchException(Type expected, Type actual)
  {
    super("Expected " + expected + " instead of " + actual + ".");
    this.expected = expected;
    this.actual = actual;
  }

  /**
   * Get the type that was expected.
   */
  public Type getExpectedType()
  {
    return expected;
  }
  
  /**
   * Get the type that was actually encountered.
   */
  public Type getActualType()
  {
    return actual;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Type expected;
  private Type actual;

}