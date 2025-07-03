/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * BajaRuntimeException is the root exception for 
 * unchecked exceptions in the Baja architecture.
 *
 * @author    Brian Frank
 * @creation  27 Feb 01
 * @version   $Revision: 2$ $Date: 6/13/01 9:34:24 AM EDT$
 * @since     Baja 1.0 
 */
public class BajaRuntimeException
  extends RuntimeException
  implements Localizable
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with specified message and nested exception.
   */
  public BajaRuntimeException(String msg, Throwable cause)
  {  
    super(msg);
    this.cause = cause;
  }

  /**
   * Constructor with specified nested exception.
   */
  public BajaRuntimeException(Throwable cause)
  {  
    this.cause = cause;
  }

  /**
   * Constructor with specified message.
   */
  public BajaRuntimeException(String msg)
  {  
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public BajaRuntimeException()
  {  
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the nested exception for this BajaRuntimeException
   * or return null if no cause exception is provided.
   */
  @Override
  public Throwable getCause()
  {
    return cause;
  }

  /**
   * Implementation of the {@code Localizable} interface. If this exception
   * wraps another {@code Localizable} exception, then return the localized
   * message of that wrapped exception. Otherwise, let the JVM attempt to
   * do its own localization by returning {@code getLocalizedMessage()}.
   *
   * @param context context containing language/locale info
   * @return a localized string
   */
  @Override
  public String toString(Context context)
  {
    Throwable cause = getCause();
    if (cause instanceof Localizable && cause != this)
    {
      return ((Localizable) cause).toString(context);
    }
    return getLocalizedMessage();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private Throwable cause;
}
