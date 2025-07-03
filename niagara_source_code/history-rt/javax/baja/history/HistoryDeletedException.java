/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

/**
 * A HistoryDeletedException is thrown when an attempt is made to
 * access a history that has been deleted.
 *
 * @author    John Sublett
 * @creation  8 Aug 2002
 * @version   $Revision: 4$ $Date: 11/18/04 3:14:35 PM EST$
 * @since     Baja 1.0
 */
public class HistoryDeletedException
  extends HistoryNotFoundException
{
  /**
   * Default constructor.
   */
  public HistoryDeletedException()
  {
    this(null);
  }

  /**
   * Constructor with the history that was deleted.
   */
  public HistoryDeletedException(BHistoryId id)
  {
    super(id, "The target history has been deleted." + ((id == null) ? "" : " (" + id.toString() + ")"));
  }
}