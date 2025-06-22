/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * AddColumn alters a database table by adding a column.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class AddColumn implements DdlCommand
{
  public AddColumn(String tableName, Column column)
  {
    this.tableName = tableName;
    this.column = column;
  }

  /**
   * Create the ALTER TABLE ADD COLUMN statement.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    return
      "ALTER TABLE " + tableName + " " + 
      "ADD " + column.getDdl(dialect);
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public String getTableName() { return tableName; }
  public Column getColumn() { return column; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  private final String tableName;
  private final Column column;
}
