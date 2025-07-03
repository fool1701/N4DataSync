/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;

/**
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface Row<T extends BIObject>
{
  /**
   * Get the table this row belongs to.
   */
  BITable<T> getTable();

  /**
   * Get the object representing the current row.
   */
  T rowObject();

  BIObject cell(Column column);

  int getCellFlags(Column column);

  BFacets getCellFacets(Column column);

  /**
   * Get a safe copy of the row.
   * This method is useful if you need to maintain references to rows returned by a cursor
   * between invocations to the {@link javax.baja.collection.TableCursor#next()}} method.
   * <p>
   * You cannot safely assume that you get a new {@link javax.baja.collection.Row} for every iteration of the table cursor
   * because the backing {@link javax.baja.collection.BITable} may be re-using the same
   * Row object and re-filling it on each iteration through the cursor. Therefore this code is
   * considered unsafe:
   *
   *   <pre>
   *   // UNSAFE: DO NOT DO THIS
   *   TableCursor cursor = table.cursor();
   *   cursor.next();
   *   Row r1 = cursor.get();
   *   cursor.next();
   *   Row r2 = cursor.get();
   *
   *   // COULD FAIL!
   *   //
   *   assert r1 != r2;
   *   </pre>
   * <p>
   * Instead do this:
   *
   *   <pre>
   *   // OK: The safe way to save rows
   *   TableCursor cursor = table.cursor();
   *   cursor.next();
   *   Row r1 = cursor.get().safeCopy();
   *   cursor.next();
   *   Row r2 = cursor.get().safeCopy();
   *
   *   // Will not fail
   *   //
   *   assert r1 != r2;
   *   </pre>
   *
   * @return a safe copy of the row.
   */
  Row<T> safeCopy();
}
