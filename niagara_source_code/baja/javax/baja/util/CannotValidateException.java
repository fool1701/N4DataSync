/**
 * Copyright 2009 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.sys.*;

/**
 * A CannotValidateException is thrown by a BIValidator or an IPropertyValidator
 * if validation fails.
 * 
 * @author Matthew Giannini
 * @creation Sep 24, 2009
 * @see BIValidator
 *
 */
public class CannotValidateException extends BajaRuntimeException
{
  public CannotValidateException()
  {
    super();
  }

  /**
   * Constructor with the specified message and nested
   * exception.  This message should be localized for the 
   * current Locale.
   */
  public CannotValidateException(String localizedMessage, Throwable cause)
  {
    super(localizedMessage, cause);
  }

  /**
   * Constructor with the specified message.  This
   * message should be localized for the current
   * Locale.
   */
  public CannotValidateException(String localizedMessage)
  {
    super(localizedMessage);
  }
  
  /**
   * Constructor with the no message and nested  exception.
   */
  public CannotValidateException(Throwable cause)
  {
    super(cause);
  }
}
