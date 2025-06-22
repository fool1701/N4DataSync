/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

/**
 * A HistoryNotFoundException is thrown when a history cannot
 * be found in the Baja history database.
 *
 * @author    John Sublett
 * @creation  01 Nov 2002
 * @version   $Revision: 3$ $Date: 11/18/04 3:14:35 PM EST$
 * @since     Baja 1.0
 */
public class HistoryNotFoundException
  extends HistoryException
{
  /**
   * Constructor.
   */
  public HistoryNotFoundException()
  {
  }

  /**
   * Constructor.
   *
   * @param id The id of the history that could not be found.
   */
  public HistoryNotFoundException(BHistoryId id)
  {
    super("History not found." + ((id == null) ? "" : " (" +  id.toString() + ")"));
    this.id = id;
  }

  /**
   * Constructor.
   *
   * @param id The id of the history that could not be found.
   */
  public HistoryNotFoundException(BHistoryId id, String msg)
  {
    super(msg);
    this.id = id;
  }

  /**
   * Get the id of the history that could not be found.
   */
  public BHistoryId getHistoryId()
  {
    return id;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BHistoryId id;


}