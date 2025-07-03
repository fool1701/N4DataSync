/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * DropConstraint alters a database table by dropping a constraint.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class DropConstraint implements DdlCommand
{
  public DropConstraint(String tableName, String constraintName)
  {
    this.tableName = tableName;
    this.constraintName = constraintName;
    this.constraint = null; //deprecated way (no mysql support)
  }
  
  public DropConstraint(String tableName, Constraint constraint)
  {
    this.tableName = tableName;
    this.constraintName = null; //deprecated way
    this.constraint = constraint;
  }


  /**
   * Create the ALTER TABLE DROP CONSTRAINT statement.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;
    if(constraint == null)
    {
      return
        "ALTER TABLE " + tableName + " " + 
        "DROP CONSTRAINT " + constraintName;
    }
    else
    {
      return dialect.getDropConstraint(tableName, constraint);
    }
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public String getTableName() { return tableName; }
  public String getConstraintName() { return constraintName; }
  public Constraint getConstraint() { return constraint; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  private final String tableName;
  private final String constraintName;
  private final Constraint constraint;
}
