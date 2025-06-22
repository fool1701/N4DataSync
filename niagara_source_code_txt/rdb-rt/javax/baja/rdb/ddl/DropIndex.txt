/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * DropIndex drops an index.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class DropIndex implements DdlCommand
{
  public DropIndex(String tableName, String indexName)
  {
    this.indexName = indexName;
    this.tableName = tableName;
  }

  /**
   * Create the ALTER TABLE DROP INDEX statement.
   *
   * @throws RuntimeException if the session's underlying BRdbms
   * does not support ALTER TABLE DROP INDEX.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    return dialect.getDropIndex(tableName, indexName);    
  }

  public String getIndexName() { return indexName; }
  public String getTableName() { return tableName; }

  private final String indexName;
  private final String tableName;
}
