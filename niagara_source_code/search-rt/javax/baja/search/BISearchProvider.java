/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.search;

import java.util.stream.Stream;

import javax.baja.agent.BIAgent;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;

/**
 * A search provider can be used as an alternative to a query scheme to
 * allow for searching spaces in Niagara.  A search provider must be
 * registered as an agent on any BOrdSchemes that it supports AND it
 * must also register as an agent on any scopes (ie. spaces) that it
 * supports searching.
 *
 * @author Dan Heine
 * @author Scott Hoye
 * @creation 2013-08-15
 * @since Niagara 4.0
 */
@NiagaraType
public interface BISearchProvider extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BISearchProvider(2979906276)1.0$ @*/
/* Generated Tue Jan 25 16:35:52 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BISearchProvider.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// API
////////////////////////////////////////////////////////////////

  /**
   * Search the specified scope with the given query ORD.
   * When called by a search task via the SearchService, this method
   * is called on an async executor, so you can do the work on the
   * calling thread.
   *
   * @param queryOrd The query ORD to resolve against the given scope
   * @param scope The scope to resolve the query ORD against
   * @param context The context associated with this search request.  Implementers
   *                should extract any user information from this context in order
   *                to filter results to only those permitted to the user.
   */
  Stream<Entity> search(BOrd queryOrd, BIObject scope, Context context);

}
