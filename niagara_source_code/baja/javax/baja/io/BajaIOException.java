/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.io;

/**
 * BajaIOException is the root exception for checked
 * IOExceptions in the Baja architecture.
 *
 * @author    Brian Frank
 * @creation  27 Feb 01
 * @version   $Revision: 2$ $Date: 6/13/01 9:34:08 AM EDT$
 * @since     Baja 1.0 
 */
public class BajaIOException
  extends java.io.IOException
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with specified message and nested exception.
   */
  public BajaIOException(String msg, Throwable cause)
  {  
    super(msg);
    this.cause = cause;
  }

  /**
   * Constructor with specified nested exception.
   */
  public BajaIOException(Throwable cause)
  {  
    super();
    this.cause = cause;
  }

  /**
   * Constructor with specified message.
   */
  public BajaIOException(String msg)
  {  
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public BajaIOException()
  {  
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the nested exception for this BajaIOException
   * or return null if no nested exception is provided.
   */
  @Override
  public Throwable getCause()
  {
    return cause;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private Throwable cause;
  
}
