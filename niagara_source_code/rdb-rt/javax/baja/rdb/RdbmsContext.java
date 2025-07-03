/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

/**
 * RdbmsContext models the differences
 * in behaviour between different subclasses
 * of BRdbms.
 * 
 * @author    Mike Jarmy
 * @creation  08 Feb 08
 * @version   $Revision: 3$ $Date: 1/28/10 1:57:34 PM EST$
 * @since     Baja 1.0
 */
public interface RdbmsContext
{
  /**
   * Does the database support ALTER TABLE DROP COLUMN ddl statements?
   */
  public boolean supportsDropColumn();

  /**
   * Does the database support RENAME TABLE ddl statements?
   */
  public boolean supportsRenameTable();

  /**
   * Does the database support clustered indexes?
   */
  public boolean supportsClusteredIndex();
}
