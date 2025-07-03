/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import javax.baja.sys.BajaRuntimeException;

/**
 * Base exception class for history.
 *
 * @author    John Sublett
 * @creation  12 Jan 01
 * @version   $Revision: 7$ $Date: 3/18/03 12:03:50 PM EST$
 * @since     Baja 1.0
 */
public class HistoryException
  extends BajaRuntimeException
{
  /**
   * Constructor with specified message and nested exception.
   */
  public HistoryException(String msg, Throwable e)
  {
    super(msg, e);
  }

  /**
   * Constructor with specified nested exception.
   */
  public HistoryException(Throwable e)
  {
    super(e);
  }

  /**
   * Construct a HistoryException with the given message.
   */
  public HistoryException(String msg)
  {
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public HistoryException()
  {
  }

}

