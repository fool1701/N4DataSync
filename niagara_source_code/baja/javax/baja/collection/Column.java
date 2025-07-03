/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.collection;

import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import javax.baja.sys.Type;

/**
 * Column is a handle to a column in a BITable.  It
 * also provides access to the column meta data.
 *
 * @author <a href="mailto:jsublett@tridium.com">John Sublett</a>
 */
public interface Column
{
  Column[] NO_COLUMNS = new Column[0];

  /**
   * Get the name of the column.
   */
  String getName();

  /**
   * Get the display name for this column.
   */
  String getDisplayName(Context cx);

  /**
   * Get the type of the column values.
   */
  Type getType();

  /**
   * Get the flags for the column.
   */
  int getFlags();

  /**
   * Get the facets for the column.
   *
   * @return Returns the facets or BFacets.NULL if no
   *   facets are defined for the column.
   */
  BFacets getFacets();
}
