/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.bacnet.BacnetException;

/**
 * ErrorExceptions are thrown when an error is encountered during
 * a Bacnet transaction.
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 11/29/01 1:24:00 PM$
 * @creation 26 Nov 01
 * @since Niagara 3 Bacnet 1.0
 */

public class ErrorException
  extends BacnetException
{
  /**
   * Constructor.
   *
   * @param errorType the Bacnet ErrorType associated with this error.
   */
  public ErrorException(ErrorType errorType)
  {
    super(errorType.toString());
    this.errorType = errorType;
  }

  /**
   * Constructor for compound errors such as WritePropertyMultipleError.
   *
   * @param errorType       the Bacnet ErrorType associated with this error.
   * @param errorParameters the additional error parameters.
   */
  public ErrorException(ErrorType errorType,
                        Object[] errorParameters)
  {
    super(errorType.toString());
    this.errorType = errorType;
    this.errorParameters = errorParameters;
  }

  /**
   * Returns the ErrorType for this ErrorException.
   *
   * @return the Bacnet ErrorType associated with this exception.
   */
  public ErrorType getErrorType()
  {
    return errorType;
  }

  public Object[] getErrorParameters()
  {
    if (errorParameters == null) return null;
    Object[] ret = new Object[errorParameters.length];
    System.arraycopy(errorParameters, 0, ret, 0, ret.length);
    return ret;
  }

  /**
   * To String.
   */
  public String toString()
  {
    return errorType.toString();
  }

  private ErrorType errorType;
  private Object[] errorParameters = null;
}
