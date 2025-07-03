/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * An in memory table stores all rows of the table in memory as a list.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
@NiagaraType
public class BInMemoryTable<T extends BIObject>
  extends BObject
  implements BIRandomAccessTable<T>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.collection.BInMemoryTable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInMemoryTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private final BITable<T> table;
  private final List<Row<T>> rows;

  public BInMemoryTable(BITable<T> table)
  {
    this.table = table;
    rows = new ArrayList<>(16);
    try (TableCursor<T> cursor = table.cursor())
    {
      while (cursor.next())
      {
        rows.add(cursor.row().safeCopy());
      }
    }
  }

  private BInMemoryTable(BITable<T> table, List<Row<T>> sorted)
  {
    this.table = table;
    rows = sorted;
  }

  @Override
  public int size()
  {
    return rows.size();
  }

  @Override
  public Row<T> get(int row)
  {
    return rows.get(row);
  }

  @Override
  public TableCursor<T> cursor()
  {
    // No need for special cursor
    return table.cursor();
  }

  @Override
  public ColumnList getColumns()
  {
    return table.getColumns();
  }

  @Override
  public BFacets getTableFacets()
  {
    return table.getTableFacets();
  }

  @Override
  public BIRandomAccessTable<T> sort(final Column col, final boolean ascending)
  {
    ArrayList<Row<T>> sorted = new ArrayList<>(rows);
    Collections.sort(sorted, new Comparator<Row<T>>()
    {
      @SuppressWarnings("unchecked")
      @Override
      public int compare(Row<T> r1, Row<T> r2)
      {
        BIObject o1 = r1.cell(col);
        BIObject o2 = r2.cell(col);
        final int mult = ascending ?  1 : -1;
        try
        {
          if (o1 instanceof Comparable)
          {
            return mult * ((Comparable<BIObject>)o1).compareTo(o2);
          }
        }
        catch(Exception ignored)
        {
        }
        return mult * o1.toString(r1.getCellFacets(col)).compareTo(o2.toString(r2.getCellFacets(col)));
      }
    });
    return new BInMemoryTable<T>(table, sorted);
  }
}
