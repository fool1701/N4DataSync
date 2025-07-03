/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.search;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.baja.collection.BITable;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.query.BIQueryHandler;
import javax.baja.space.BISpaceNode;
import javax.baja.sys.BIObject;
import javax.baja.sys.BObject;
import javax.baja.sys.BStation;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.util.CloseableIterator;
import javax.baja.virtual.BVirtualComponent;

/**
 * Search provider for BQL search queries.
 *
 * @author Dan Heine
 * @author Scott Hoye
 * @creation 2013-11-06
 * @since Niagara 4.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "baja:ComponentSpace", "baja:Component", "bql:BqlScheme" }
  )
)
public class BBqlSearchProvider extends BObject
  implements BISearchProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BBqlSearchProvider(3470815998)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBqlSearchProvider.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BISearchProvider
////////////////////////////////////////////////////////////////

  /**
   * Search the specified scope with the given query ORD.
   * When called by a search task via the SearchService, this method
   * is called on an async executor, so you can do the work on the
   * calling thread.
   *
   * @param query The query ORD to resolve against the given scope
   * @param scope The scope to resolve the query ORD against
   * @param context The context associated with this search request.  Implementers
   *                should extract any user information from this context in order
   *                to filter results to only those permitted to the user.
   * @return A Stream of Entities for the search results. Callers need to remember to close() this
   * Stream when they are finished using it.
   */
  @Override
  public Stream<Entity> search(BOrd query, BIObject scope, Context context)
  {
    if (scope.getType().is(BIQueryHandler.TYPE) && scope.getType().is(BISpaceNode.TYPE))
    {
      BOrd scopeOrd = ((BISpaceNode)scope).getAbsoluteOrd();
      OrdTarget scopeTarget = scopeOrd.resolve(BSearchService.getService(), context);
      BIQueryHandler queryHandler = (BIQueryHandler)scope;
      // TODO: Is it ok to assume the remote bql case when null BQueryScheme argument passed to canHandle()?
      if (queryHandler.canHandle(scopeTarget, null))
      {
        OrdQuery[] queries = query.parse();
        CloseableIterator<Entity> results = queryHandler.query(scopeTarget, queries[queries.length - 1]);
        Spliterator<Entity> split = Spliterators.spliteratorUnknownSize(results, /*characteristics*/0);
        return StreamSupport.stream(split, /*parallel*/false).onClose(() ->
        {
          try
          {
            results.close();
          }
          catch(RuntimeException re)
          {
            throw re;
          }
          catch(Exception e)
          {
            throw new BajaRuntimeException(e);
          }
        });
      }
    }

    BStation station = Sys.getStation();
    if (scope == station.getComponentSpace())
      scope = station.getComponentSpace().getRootComponent();

    BITable<?> result = (BITable<?>)(query.resolve((BObject)scope, context).get());
    return result.cursor().stream(true)
                          .filter(obj -> (obj instanceof BISpaceNode) ||
                                         (obj instanceof Entity && obj instanceof BIObject &&
                                          ((Entity)obj).getOrdToEntity().isPresent()))
                          .map(obj -> convertToSearchResult(obj));
  }

  private static Entity convertToSearchResult(Object obj)
  {
    if (obj instanceof BVirtualComponent)
    {
      BVirtualComponent node = (BVirtualComponent)obj;
      return BSearchResult.make(node.getNavOrd().relativizeToSession(), node);
    }
    if (obj instanceof BISpaceNode)
    {
      BISpaceNode node = (BISpaceNode)obj;
      return BSearchResult.make(node.getOrdInSession(), node);
    }
    else if (obj instanceof Entity && obj instanceof BIObject &&
      ((Entity)obj).getOrdToEntity().isPresent())
    {
      return BSearchResult.make(((Entity)obj).getOrdToEntity().get(), (BIObject)obj);
    }

    return null;
  }
}
