/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import javax.baja.sys.BIObject;
import javax.baja.sys.Cursor;

/**
 * Utilities for working with a {@link BITable}. All methods will attempt to use the most efficient
 * interface for obtaining the result. However, users of these methods should be aware that the fallback
 * implementation of most methods will yield extremely poor performance. For example, unless a
 * table implements {@link BIRandomAccessTable}, then any row-based indexing will require iterating
 * the cursor from the beginning.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public final class Tables
{
  private Tables()
  {
  }

  /**
   * Return true if the table has at least one row.
   * <p>
   * If the table does not implement
   * {@link BIRandomAccessTable}, then a cursor is used to determine if the table has rows or not.
   * Depending on the table implementation, some cursors are only usable once, so use this utility
   * with care.
   */
  public static boolean hasRows(BITable<?> table)
  {
    if (table instanceof BIRandomAccessTable)
    {
      return ((BIRandomAccessTable<?>)table).size() > 0;
    }
    else
    {
      try(Cursor<?> c = table.cursor())
      {
        return c.next();
      }
    }
  }

  /**
   * Convert the given table to a {@link BIRandomAccessTable} so that it can be queried by index.
   * This will typically result in the entire table being read into memory.
   */
  public static <T extends BIObject> BIRandomAccessTable<T> slurp(BITable<T> table)
  {
    if (table instanceof BIRandomAccessTable)
    {
      return (BIRandomAccessTable<T>)table;
    }
    else
    {
      return new BInMemoryTable<T>(table);
    }
  }

  /**
   * Get the cell value at the given row for the given column.
   * <p>
   * In the best case this implementation takes <i>O(1)</i> time. In the worst case it takes
   * <i>O(n)</i> time, where n is the number of rows in the table.
   */
  public static BIObject get(BITable<?> table, int row, Column col)
  {
    if (table instanceof BIRandomAccessTable)
    {
      return table.as(BIRandomAccessTable.class).get(row).cell(col);
    }
    else
    {
      try (TableCursor<?> cursor = table.cursor())
      {
        do
        {
          if (!cursor.next())
          {
            throw new IndexOutOfBoundsException();
          }
          --row;
        } while (row >= 0);
        return cursor.cell(col);
      }
    }
  }
}
