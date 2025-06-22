/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * AddConstraint alters a database table by adding a constraint.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class AddConstraint implements DdlCommand
{
  public AddConstraint(String tableName, Constraint constraint)
  {
    this.tableName = tableName;
    this.constraint = constraint;
  }

  /**
   * Create the ALTER TABLE ADD CONSTRAINT statement.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    return
      "ALTER TABLE " + tableName + " " + 
      "ADD " + constraint.getDdl(dialect, tableName);
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public String getTableName() { return tableName; }
  public Constraint getConstraint() { return constraint; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  private final String tableName;
  private final Constraint constraint;
}
