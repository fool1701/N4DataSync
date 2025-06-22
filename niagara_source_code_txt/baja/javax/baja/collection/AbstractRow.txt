/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;

/**
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public abstract class AbstractRow<T extends BIObject> implements Row<T>
{
  private final BITable<T> table;
  private final T rowObject;

  protected AbstractRow(BITable<T> table, T rowObject)
  {
    this.table = table;
    this.rowObject = rowObject;
  }

  @Override
  public BITable<T> getTable()
  {
    return table;
  }

  @Override
  public T rowObject()
  {
    return rowObject;
  }

  @Override
  public abstract BIObject cell(Column column);

  @Override
  public int getCellFlags(Column column)
  {
    return 0;
  }

  @Override
  public BFacets getCellFacets(Column column)
  {
    return BFacets.NULL;
  }

  @Override
  public abstract Row<T> safeCopy();

}
