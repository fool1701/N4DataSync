/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.data;

import javax.baja.sys.*;

/**
 * DataTypeException is thrown when a non-DataType type is
 * used where a DataType is expected.
 *
 * @author    John Sublett
 * @creation  23 Feb 2003
 * @version   $Revision: 1$ $Date: 2/27/03 9:18:32 AM EST$
 * @since     Baja 1.0
 */
public class DataTypeException
  extends BajaRuntimeException
{
  public DataTypeException(Type badType)
  {
    super(badType.getTypeSpec().toString() + " is not a valid DataType.");
    this.badType = badType;
  }

  /**
   * Get the type that was not a DataType
   */
  public Type getInvalidType()
  {
    return badType;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Type badType;
}