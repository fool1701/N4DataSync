/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

/**
 * A DatabaseClosedException is thrown when an operation
 * is attempted on a history database that is not open.
 *
 * @author    John Sublett
 * @creation  5 Nov 2002
 * @version   $Revision: 1$ $Date: 11/11/02 2:32:26 PM EST$
 * @since     Baja 1.0
 */
public class DatabaseClosedException
  extends HistoryException
{
  /**
   * Constructor with specified message.
   */
  public DatabaseClosedException(String msg)
  {
    super(msg);
  }

  /**
   * Default constructor.
   */
  public DatabaseClosedException()
  {
  }
}

