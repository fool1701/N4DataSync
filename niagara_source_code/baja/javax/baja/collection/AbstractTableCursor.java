/*
 * Copyright 2013, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.collection;

import javax.baja.sys.BIObject;

/**
 * A general implementation of a {@link TableCursor}.  Subclasses only need to provide an implementation
 * of {@link #row()}.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public abstract class AbstractTableCursor<T extends BIObject>
  extends AbstractCursor<T>
  implements TableCursor<T>
{
  private final BITable<T> table;

  protected AbstractTableCursor(BITable<T> table)
  {
    this.table = table;
  }

  /**
   * Should only be overridden to add a covariant return method.
   *
   * @return this cursor's table
   */
  @Override
  public BITable<T> getTable()
  {
    return table;
  }

  /**
   * Get the row object from the current row.
   *
   * @return row object
   */
  @Override
  protected final T doGet()
  {
    return row().rowObject();
  }
}
