/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

/**
 * A ConfigurationMismatchException is thrown when the properties
 * of a BHistory do not match the properties for that history
 * that are stored in the database.
 *
 * @author    John Sublett
 * @creation  05 Nov 2002
 * @version   $Revision: 2$ $Date: 4/4/03 5:28:08 PM EST$
 * @since     Baja 1.0
 */
public class ConfigurationMismatchException
  extends HistoryException
{
  /**
   * A mismatch between the two specified histories.
   *
   * @param db The version of the history that is stored
   *   in the database.
   * @param other The version that is different from the
   *   history that is stored in the database.
   */
  public ConfigurationMismatchException(BHistoryId id,
                                        BHistoryConfig db,
                                        BHistoryConfig other)
  {
    this.id = id;
    this.db = (BHistoryConfig)db.newCopy(true);
    this.other = (BHistoryConfig)other.newCopy(true);
  }

  /**
   * Get the history configuration that is stored
   * in the database.
   */
  public BHistoryConfig getDatabaseConfig()
  {
    return db;
  }

  /**
   * Get the history configuration that is different from the
   * database version.
   */
  public BHistoryConfig getOther()
  {
    return other;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BHistoryId     id;
  private BHistoryConfig db;
  private BHistoryConfig other;
}