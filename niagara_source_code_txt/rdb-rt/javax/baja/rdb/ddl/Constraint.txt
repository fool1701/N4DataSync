/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.ddl;

import javax.baja.log.*;
import javax.baja.rdb.*;

import com.tridium.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * A Constraint in a database table.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class Constraint
{
  /**
   * Make a UNIQUE constraint.
   */
  public static Constraint makeUnique(String name, String[] columns)
  {
    return new Constraint(name, columns, UNIQUE);
  }

  /**
   * Make a UNIQUE constraint.
   */
  public static Constraint makeUnique(String name, String[] columns, BClustered clustered)
  {
    return new Constraint(name, columns, UNIQUE, clustered);
  }

  /**
   * Make a PRIMARY KEY constraint.
   */
  public static Constraint makePrimaryKey(String name, String[] columns)
  {
    return new Constraint(name, columns, PRIMARY_KEY);
  }

  /**
   * Make a PRIMARY KEY constraint.
   */
  public static Constraint makePrimaryKey(String name, String[] columns, BClustered clustered)
  {
    return new Constraint(name, columns, PRIMARY_KEY, clustered);
  }

  /**
   * Make a FOREIGN KEY constraint.
   */
  public static Constraint makeForeignKey(
    String    name,
    String[]  columns,
    String    refTable,
    String[]  refColumns,
    BOnDelete refOnDelete)
  {
    return new Constraint(name, columns, refTable, refColumns, refOnDelete);
  }

  /**
   * Convenience for <code>makeUnique(name, new String[] { column })</code>.
   */
  public static Constraint makeUnique(String name, String column)
  {
    return new Constraint(name, new String[] { column }, UNIQUE);
  }

  /**
   * Convenience for <code>makePrimaryKey(name, new String[] { column })</code>.
   */
  public static Constraint makePrimaryKey(String name, String column)
  {
    return new Constraint(name, new String[] { column }, PRIMARY_KEY);
  }

  /**
   * Convenience for
   * <pre>
   *   makeForeignKey(
   *       name, 
   *       new String[] { column }, 
   *       refTable, 
   *       new String[] { refColumn }, 
   *       refOnDelete);
   * </pre>
   */
  public static Constraint makeForeignKey(
    String    name,
    String    column,
    String    refTable,
    String    refColumn,
    BOnDelete refOnDelete)
  {
    return new Constraint(
      name, 
      new String[] { column }, 
      refTable, 
      new String[] { refColumn }, 
      refOnDelete);
  }

  /**
   * constructor
   */
  private Constraint(String name, String[] columns, int type)
  {
    this.name    = name;
    this.columns = columns;
    this.type    = type;

    this.clustered = BClustered.unspecified;

    this.refTable    = null;
    this.refColumns  = null;
    this.refOnDelete = null;
  }

  /**
   * constructor
   */
  private Constraint(String name, String[] columns, int type, BClustered clustered)
  {
    this.name    = name;
    this.columns = columns;
    this.type    = type;

    this.clustered = clustered;

    this.refTable    = null;
    this.refColumns  = null;
    this.refOnDelete = null;
  }

  /**
   * constructor
   */
  private Constraint(
    String    name,
    String[]  columns,
    String    refTable,
    String[]  refColumns,
    BOnDelete refOnDelete)
  {
    this.name    = name;
    this.columns = columns;
    this.type    = FOREIGN_KEY;

    this.clustered = BClustered.unspecified;

    this.refTable    = refTable;
    this.refColumns  = refColumns;
    this.refOnDelete = refOnDelete;
  }

////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////

  /**
   * asDdl
   */
  public String getDdl(RdbmsContext context, String tableName)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    StringBuilder sql = new StringBuilder();

    sql.append("CONSTRAINT ").append(getName()).append(" ");

    // type
    switch (type)
    {
      case UNIQUE:      sql.append("UNIQUE ");      break;
      case PRIMARY_KEY: sql.append("PRIMARY KEY "); break;
      case FOREIGN_KEY: sql.append("FOREIGN KEY "); break;
      default: throw new IllegalStateException();
    }

    // clustered
    sql.append(CreateIndex.clusteredDdl(dialect, tableName, clustered));

    // columns
    sql.append("(");
    for (int i = 0; i < columns.length; i++)
    {
      if (i > 0) sql.append(", ");
      sql.append(columns[i]);
    }
    sql.append(")");

    // foreign key
    if (type == FOREIGN_KEY)
    {
      sql.append(" REFERENCES ").append(refTable).append(" ");
      sql.append("(");
      for (int i = 0; i < refColumns.length; i++)
      {
        if (i > 0) sql.append(", ");
        sql.append(refColumns[i]);
      }
      sql.append(")");
      sql.append(" ON DELETE ");
      sql.append(dialect.getOnDelete(refOnDelete.getOrdinal()));
    }

    // done
    return sql.toString();
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public String   getName()    { return name;    }
  public String[] getColumns() { return columns; }

  /** @return one of UNIQUE, PRIMARY_KEY, or FOREIGN_KEY */
  public int getConstraintType() { return type; }

  public BClustered isClustered() 
  {
    return clustered;
  }

  /**
   * @throws UnsupportedOperationException if this Constraint is not a FOREIGN_KEY.
   */
  public String getRefTable() 
  {
    if (type != FOREIGN_KEY) throw new UnsupportedOperationException();

    return refTable;
  }

  /**
   * @throws UnsupportedOperationException if this Constraint is not a FOREIGN_KEY.
   */
  public String[] getRefColumns() 
  {
    if (type != FOREIGN_KEY) throw new UnsupportedOperationException();

    return refColumns;
  }

  /**
   * @throws UnsupportedOperationException if this Constraint is not a FOREIGN_KEY.
   */
  public BOnDelete getRefOnDelete() 
  {
    if (type != FOREIGN_KEY) throw new UnsupportedOperationException();

    return refOnDelete;
  }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  public static final int UNIQUE      = 0;
  public static final int PRIMARY_KEY = 1;
  public static final int FOREIGN_KEY = 2;

  private final String   name;
  private final String[] columns;
  private final int      type;

  private final BClustered clustered;

  private final String    refTable;
  private final String[]  refColumns;
  private final BOnDelete refOnDelete;
}
