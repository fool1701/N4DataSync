/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A {@link BITable} that supports random access to its cells.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
@NiagaraType
public interface BIRandomAccessTable<T extends BIObject> extends BITable<T>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.collection.BIRandomAccessTable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIRandomAccessTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the object at the given row.
   */
  Row<T> get(int row);

  /**
   * Get the number of rows in the table.
   */
  int size();

  /**
   * Sort the table by the given column. The default implementation will read this table into
   * a {@link BInMemoryTable} and then sort that.
   *
   * @param column the column to sort by
   * @param ascending if true, sort the table in ascending order by the given column
   * @return a BIRandomAccessTable that is sorted.
   */
  default BIRandomAccessTable<T> sort(Column column, boolean ascending)
  {
    return new BInMemoryTable<T>(this).sort(column, ascending);
  }

}
