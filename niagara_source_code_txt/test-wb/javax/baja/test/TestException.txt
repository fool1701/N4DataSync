/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

/**
 * TestException is thrown when a verify fails.
 *
 * @author    Brian Frank
 * @creation  3 Oct 06
 * @version   $Revision: 1$ $Date: 10/4/06 3:13:44 PM EDT$
 * @since     Niagara 3.2
 */
public class TestException
  extends javax.baja.sys.BajaRuntimeException
{
  /**
   * Constructor with specified message and nested exception.
   */
  public TestException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * Constructor with specified nested exception.
   */
  public TestException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Constructor with specified message.
   */
  public TestException(String msg)
  {
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public TestException()
  {
  }

}
