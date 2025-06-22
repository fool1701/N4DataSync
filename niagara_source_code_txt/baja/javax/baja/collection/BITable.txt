/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.collection;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BInterface;
import javax.baja.sys.Cursorable;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

// TODO: better documentation
/**
 * BITable is a collection members are accessible by
 * row and whose members' elements are accessible by column.
 *
 * @author <a href="mailto:jsublett@tridium.com">John Sublett</a>
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
@NiagaraType
public interface BITable<T extends BIObject> extends Cursorable<T>, BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.collection.BITable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BITable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get a table cursor for iterating the rows of the table (covariant return).
   */
  @Override
  TableCursor<T> cursor();

  /**
   * Get the list of columns in the table.
   */
  ColumnList getColumns();

  /**
   * Get the facets that apply to the entire table.
   *
   * @return Returns the table facets or BFacets.NULL if
   *   if no facets are defined for the table.
   */
  BFacets getTableFacets();
}
