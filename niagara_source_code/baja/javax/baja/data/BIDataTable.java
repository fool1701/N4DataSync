/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.data;

import javax.baja.collection.BITable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A BIDataTable is a {@link BITable} with {@link BIDataValue} values for all cell values.
 *
 * @author <a href="mailto:jsublett@tridium.com">John Sublett</a>
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
@NiagaraType
public interface BIDataTable<Row extends BIObject> extends BITable<Row>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.data.BIDataTable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIDataTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
