/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.rdb.*;
import com.tridium.rdb.jdbc.*;

/**
 * CreateSequence creates a sequence.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class CreateSequence implements DdlCommand
{
  public CreateSequence(String name)
  {
    this.name = name;
  }

  /**
   * Create the CREATE SEQUENCE statement.
   */
  public String getDdl(RdbmsContext context)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    return "CREATE SEQUENCE " + name;
  }

  public String getSequenceName() { return name; }

  private final String name;
}
