/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.collection;

import javax.baja.sys.BIObject;
import javax.baja.sys.IterableCursor;

/**
 * TableCursor is a cursor for iterating through the rows in a table.
 *
 * @author <a href="mailto:jsublett@tridium.com">John Sublett</a>
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface TableCursor<T extends BIObject> extends IterableCursor<T>
{
  /**
   * Get the BITable backing this cursor (covariant return).
   */
  BITable<T> getTable();

  /**
   * Get the current table row (covariant return).
   */
  Row<T> row();

  /**
   * Get the cell value for the specified column at the current row.
   */
  default BIObject cell(Column column)
  {
    return row().cell(column);
  }
}
