/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import javax.baja.sys.*;
import com.tridium.rdb.jdbc.*;

/**
 * RenameTable renames a table.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class RenameTable implements DdlCommand
{
  public RenameTable(String oldName, String newName)
  {
    this.oldName = oldName;
    this.newName = newName;
  }

  /**
   * Create the RENAME TABLE statement.
   *
   * @throws RuntimeException if the session's underlying BRdbms
   * does not support RENAME TABLE.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    if (!dialect.supportsRenameTable())
      throw new BajaRuntimeException(
        dialect.getClass().getName() + 
        " does not support RENAME TABLE");

    return "RENAME TABLE " + oldName + " TO " + newName;
  }

  public String getOldName() { return oldName; }
  public String getNewName() { return newName; }

  private final String oldName;
  private final String newName;
}
