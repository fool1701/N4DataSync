/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * DropTable drops a table.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class DropTable implements DdlCommand
{
  public DropTable(String tableName)
  {
    this.tableName = tableName;
  }

  /**
   * Create the DROP TABLE statement.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    return "DROP TABLE " + tableName;
  }

  public String getTableName() { return tableName; }

  private final String tableName;
}
