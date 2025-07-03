/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import javax.baja.sys.*;
import com.tridium.rdb.jdbc.*;

/**
 * DropColumn alters a database table by dropping a column.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class DropColumn implements DdlCommand
{
  public DropColumn(String tableName, String columnName)
  {
    this.tableName = tableName;
    this.columnName = columnName;
  }

  /**
   * Create the ALTER TABLE DROP COLUMN statement.
   *
   * @throws RuntimeException if the session's underlying BRdbms
   * does not support ALTER TABLE DROP COLUMN.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    if (!dialect.supportsDropColumn())
      throw new BajaRuntimeException(
        dialect.getClass().getName() + 
        " does not support ALTER TABLE DROP COLUMN");

    return
      "ALTER TABLE " + tableName + " " + 
      "DROP COLUMN " + columnName;
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public String getTableName() { return tableName; }
  public String getColumnName() { return columnName; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  private final String tableName;
  private final String columnName;
}
