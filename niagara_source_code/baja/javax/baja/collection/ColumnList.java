/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.collection;

/**
 * A list of columns for a {@link BITable}
 *
 * @author <a href="mailto:jsublett@tridium.com">John Sublett</a>
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface ColumnList
{
  /**
   * Get the number of columns in the list.
   */
  int size();

  /**
   * Get the column at the specified index.
   */
  Column get(int index);

  /**
   * Get the column with the specified name.
   *
   * @param name The name of the target column.
   *
   * @return Return the column having the specified name,
   * or null if no such column exists.
   */
  Column get(String name);

  /**
   * Get the index of the column with the specified name.
   *
   * @param name The name of the target column.
   *
   * @return Return the column having the specified name,
   * or -1 if no such column exists.
   */
  int indexOf(String name);

  /**
   * Get an array of the columns in the list.
   */
  Column[] list();
}
