/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

/**
 * InvalidHistoryIdException is thrown when an attempt is made
 * to open a history without a valid history id.
 *
 * @author    John Sublett
 * @creation  06 Mar 2003
 * @version   $Revision: 1$ $Date: 3/18/03 12:08:13 PM EST$
 * @since     Baja 1.0
 */
public class InvalidHistoryIdException
  extends HistoryException
{
  /**
   * Constructor
   */
  public InvalidHistoryIdException(BHistoryId id)
  {
    super(id + " is not a valid history id.");
    this.id = id;
  }

  /**
   * Get the invalid id.
   */
  public BHistoryId getId()
  {
    return id;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BHistoryId id;
}