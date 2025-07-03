/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * AlterColumn alters a database table by altering a column.
 * 
 * @author    JJ Frankovich
 * @creation  01 Jun 09
 * @version   $Revision$ $Date$
 * @since     Baja 3.4
 */
public class AlterColumn implements DdlCommand
{
  public AlterColumn(String tableName, Column column)
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
      dialect.getAlterColumn() + " " + 
      column.getDdl(dialect, true);
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
