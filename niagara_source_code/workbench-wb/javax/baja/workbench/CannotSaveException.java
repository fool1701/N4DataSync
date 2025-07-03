/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import javax.baja.sys.BajaException;

/**
 * The CannotSaveException is thrown during a saveValue() 
 * operation on BWbEditor.
 *
 * @author    Brian Frank
 * @creation  21 Apr 01
 * @version   $Revision: 2$ $Date: 7/10/03 12:07:07 PM EDT$
 * @since     Baja 1.0 
 */
public class CannotSaveException
  extends BajaException
{
  /**
   * Constructor with the specified message.  This
   * message should be localized for the current
   * Locale.
   */
  public CannotSaveException(String localizedMessage)
  {  
    super(localizedMessage);
  }

  /**
   * Constructor with the specified message.  This
   * message should be localized for the current
   * Locale.
   * @param localizedMessage The localized message.
   * @param suppressStackTrace True if the stack trace should be suppressed.
   * @since Niagara 4.3
   */
  public CannotSaveException(String localizedMessage, boolean suppressStackTrace)
  {
    super(localizedMessage);
    this.suppressStackTrace = suppressStackTrace;
  }

  /**
   * Constructor with the specified message.  This
   * message should be localized for the current
   * Locale.
   * @param localizedMessage The localized message.
   * @param suppressStackTrace True if the stack trace should be suppressed.
   * @param cause The cause of this exception.
   * @since Niagara 4.3
   */
  public CannotSaveException(String localizedMessage, boolean suppressStackTrace, Throwable cause)
  {
    super(localizedMessage, cause);
    this.suppressStackTrace = suppressStackTrace;
  }

  /**
   * Constructor with the specified message and nested
   * exception.  This message should be localized for the 
   * current Locale.
   */
  public CannotSaveException(String localizedMessage, Throwable cause)
  {  
    super(localizedMessage, cause);
  }

  /**
   * Constructor with the no message and nested  exception.
   */
  public CannotSaveException(Throwable cause)
  {  
    super(cause);
  }

  /**
   * Default constructor.
   */
  public CannotSaveException() {}
  
  /**
   * Return true if this exception should be silently handled.
   */
  public boolean isSilent() { return silent; }
  
  /**
   * Set to true if this exception should be silently handled.
   */
  public void setSilent(boolean silent) { this.silent = silent; }

  /**
   * Returns true if the stack trace for this error should be suppressed.
   * @return true if the stack trace for this error should be suppressed.
   * @since Niagara 4.3
   */
  public boolean suppressStackTrace()
  {
    return suppressStackTrace;
  }

  /**
   * Sets whether the stack trace for this exception should be displayed to
   * the user or not.
   * @param suppressStackTrace If set to the true, the stack trace associated
   *                           with this exception will not be displayed.
   * @since Niagara 4.3
   */
  public void setSuppressStackTrace(boolean suppressStackTrace)
  {
    this.suppressStackTrace = suppressStackTrace;
  }

  private boolean silent = false;
  private boolean suppressStackTrace = false;
}
