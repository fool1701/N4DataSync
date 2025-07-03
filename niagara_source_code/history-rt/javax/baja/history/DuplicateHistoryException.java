/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

/**
 * DuplicateHistoryException is thrown when an attempt
 * is made to create a history with an id that already exists.
 *
 * @author    John Sublett
 * @creation  18 Jul 2002
 * @version   $Revision: 1$ $Date: 4/4/03 5:21:09 PM EST$
 * @since     Baja 1.0
 */
public class DuplicateHistoryException
  extends HistoryException
{
  /**
   * Construct an instance with the specified history id.
   */
  public DuplicateHistoryException(BHistoryId id)
  {
    super(id.toString());
  }

  /**
   * Construct an instance with the given message.
   */
  public DuplicateHistoryException(String msg)
  {
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public DuplicateHistoryException()
  {
  }
}