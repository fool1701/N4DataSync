/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * CreateTable creates an table.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class CreateTable implements DdlCommand
{
  public CreateTable(
    String tableName,
    Column[] columns,
    Constraint[] constraints)
  {
    this.tableName   = tableName;
    this.columns     = columns;
    this.constraints = constraints;
  }

  /**
   * Create the CREATE TABLE statement.
   * <p>
   * If the table has a non-clustered primary key, and the session's 
   * underlying BRdbms does not support clustering,
   * then a warning will be logged, and the table's primary key.
   * will <i>not</i> be clustered.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    StringBuilder sql = new StringBuilder();
    sql.append("CREATE TABLE ").append(tableName).append(" (");

    // columns
    for (int i = 0; i < columns.length; i++)
    {
      if (i > 0) sql.append(", ");
      sql.append(columns[i].getDdl(dialect));
    }

    // constraints
    for (int i = 0; i < constraints.length; i++)
    {
      sql.append(", ");
      sql.append(constraints[i].getDdl(dialect, tableName));
    }

    // done
    sql.append(")");
    return sql.toString();
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public String getTableName() { return tableName; }
  public Column[] getColumns() { return columns; }
  public Constraint[] getConstraints() { return constraints; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  private final String tableName;
  private final Column[] columns;
  private final Constraint[] constraints;
}
