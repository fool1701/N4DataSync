/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * DropSequence drops a sequence.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class DropSequence implements DdlCommand
{
  public DropSequence(String name)
  {
    this.name = name;
  }

  /**
   * Create the DROP SEQUENCE statement.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    return "DROP SEQUENCE " + name;
  }

  public String getSequenceName() { return name; }

  private final String name;
}
