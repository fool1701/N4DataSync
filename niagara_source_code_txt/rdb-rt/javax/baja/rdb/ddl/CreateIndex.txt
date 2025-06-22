/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import java.util.logging.Logger;

import javax.baja.rdb.RdbmsContext;

import com.tridium.rdb.jdbc.RdbmsDialect;

/**
 * CreateIndex creates a database index.
 *
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class CreateIndex implements DdlCommand
{
  /**
   * Convenience for creating an unclustered index
   */
  public CreateIndex(
    String   indexName,
    String   tableName,
    boolean  isUnique,
    Column[] columns)
  {
    this(indexName, tableName, isUnique, BClustered.unspecified, columns);
  }

  /**
   * Create an index.
   */
  public CreateIndex(
    String     indexName,
    String     tableName,
    boolean    isUnique,
    BClustered clustered,
    Column[] columns)
  {
    this.indexName = indexName;
    this.tableName = tableName;
    this.isUnique  = isUnique;
    this.clustered = clustered;
    this.columns   = columns;
  }

  /**
   * Convenience for creating an index
   * whose columns are all sorted in ascending order.
   */
  public CreateIndex(
    String   indexName,
    String   tableName,
    boolean  isUnique,
    String[] columnNames)
  {
    this(indexName, tableName, isUnique, makeColumns(columnNames));
  }

  /**
   * Convenience for creating an index
   * whose columns are all sorted in ascending order.
   */
  public CreateIndex(
    String   indexName,
    String   tableName,
    boolean  isUnique,
    BClustered clustered,
    String[] columnNames)
  {
    this(indexName, tableName, isUnique, clustered, makeColumns(columnNames));
  }

  private static Column[] makeColumns(String[] columnNames)
  {
    Column[] columns = new Column[columnNames.length];
    for (int i = 0; i < columns.length; i++)
      columns[i] = new Column(columnNames[i]);
    return columns;
  }

  /**
   * Create the CREATE INDEX statement.
   * <p>
   * If the index is clustered, and the session's
   * underlying BRdbms does not support clustering,
   * then a warning will be logged, and the index
   * will <i>not</i> be clustered.
   */
  @Override
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    // make index
    StringBuilder sql = new StringBuilder();
    sql.append("CREATE ");

    // unique
    if (isUnique) sql.append("UNIQUE ");

    // clustered
    sql.append(clusteredDdl(dialect, tableName, clustered));

    // names
    sql.append("INDEX ").append(indexName);
    sql.append(" ON ").append(tableName);

    // columns
    sql.append("(");
    for (int i = 0; i < columns.length; i++)
    {
      if (i > 0) sql.append(", ");
      sql.append(columns[i].getName());
      if (columns[i].isDescending())
        sql.append(" DESC");
    }
    sql.append(")");

    return sql.toString();
  }

  /**
   * clusteredDdl
   */
  static String clusteredDdl(RdbmsDialect dialect, String tableName, BClustered clustered)
  {
    // clustering was specified, but the database
    // doesn't support clustering
    if (!clustered.equals(BClustered.unspecified) &&
      !dialect.supportsClusteredIndex())
    {
      Logger.getLogger("rdb.jdbc").warning(
        dialect.getClass().getName() +
        " does not support clustered indexes. " +
        "Table " + tableName + " will not have clustering specified.");
      return "";
    }

    switch(clustered.getOrdinal())
    {
      case BClustered.UNSPECIFIED: return "";
      case BClustered.CLUSTERED: return "CLUSTERED ";
      case BClustered.NON_CLUSTERED: return "NONCLUSTERED ";
      default: throw new IllegalStateException();
    }
  }

  /**
   * A column in a database index.
   */
  public static class Column
  {
    /**
     * Convenience for <code>Column(name, false)</code>.
     */
    public Column(String name)
    {
      this(name, false);
    }

    public Column(String name, boolean descending)
    {
      this.name       = name;
      this.descending = descending;
    }

    public String  getName()      { return name;       }
    public boolean isDescending() { return descending; }

    private final String  name;
    private final boolean descending;
  }

  public String  getIndexName() { return indexName; }
  public String  getTableName() { return tableName; }
  public boolean isUnique()     { return isUnique;  }
  public BClustered  isClustered()  { return clustered; }
  public Column[] getColumns()   { return columns;   }

  private final String indexName;
  private final String tableName;
  private final boolean isUnique;
  private final BClustered clustered;
  private final Column[] columns;
}
