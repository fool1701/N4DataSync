/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.bql;

import javax.baja.collection.BITable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIObject;
import javax.baja.sys.BInterface;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIRelational is an interface for an object that manages relational data.
 *
 * @author    John Sublett
 */
@NiagaraType
public interface BIRelational<T extends BIObject>
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bql.BIRelational(2979906276)1.0$ @*/
/* Generated Wed Jan 26 11:12:09 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIRelational.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the relation with the specified identifier.
   *
   * @param id A string identifier for the relation.  The format
   *   of the string is implementation specific.
   *
   * @param cx The Context associated with this request.
   *           This parameter was added starting in Niagara 4.0.
   *
   * @return Returns the relation identified by id or null if the relation
   *   cannot be found.
   */
  BITable<T> getRelation(String id, Context cx);
}
