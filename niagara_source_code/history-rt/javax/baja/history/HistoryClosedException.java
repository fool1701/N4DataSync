/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

/**
 * HistoryClosedException is thrown when a history is closed at a time
 * when it is expected to be open.
 *
 * @author    John Sublett
 * @creation  18 Jul 2002
 * @version   $Revision: 1$ $Date: 7/26/02 3:52:23 PM EDT$
 * @since     Baja 1.0
 */
public class HistoryClosedException
  extends HistoryException
{
  /**
   * Construct a HistoryException with the given message.
   */
  public HistoryClosedException(String msg)
  {
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public HistoryClosedException()
  {
  }
}