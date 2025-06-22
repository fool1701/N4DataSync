/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.query;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdScheme;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BISpace;
import javax.baja.space.BISpaceNode;
import javax.baja.space.BSpace;
import javax.baja.spy.Spy;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.BIEntity;
import javax.baja.tag.BIEntitySpace;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.util.BasicEntity;

import com.tridium.sys.Nre;
import com.tridium.sys.tag.BEntityObjectWrapper;

/**
 *  A BQueryScheme is an ord scheme whose body is a query syntax and when resolved, processes
 *  the query against a Niagara space.
 *
 * @author John Sublett
 * @creation 01/15/2014
 * @since Niagara 4.0
 */
@NiagaraType
public abstract class BQueryScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.query.BQueryScheme(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BQueryScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a BOrdScheme with the specified ID.
   */
  protected BQueryScheme(String id)
  {
    super(id);
  }

  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    // Set the namespace facet if not set and if the default
    // namespace can be determined from the installed TagDictionaryService
    if (base.getFacet(BFacets.NAMESPACE) == null)
    {
      BObject baseObj = base.get();
      if ((baseObj instanceof BISpaceNode) || (baseObj instanceof BISpace))
      {
        BISpace space = null;
        if (baseObj instanceof BISpace)
          space = baseObj.as(BSpace.class);
        else
          space = baseObj.as(BISpaceNode.class).getSpace();

        if (space instanceof BIEntitySpace)
        {
          TagDictionaryService service = ((BIEntitySpace)space).getTagDictionaryService();
          if (service != null)
          {
            String defaultNamespace = service.getDefaultNamespace();
            if (defaultNamespace != null)
              base = OrdTarget.makeWithFacets(base, BFacets.make(BFacets.NAMESPACE, defaultNamespace));
          }
        }
      }
    }

    // Look for a special facet on the base to decide if an alternate scope for the query should be used
    OrdTarget scope = base;
    BObject alternateScopeOrd = base.getFacet(ALTERNATE_SCOPE_FACET_KEY);
    if (alternateScopeOrd != null)
    {
      BOrd ord = BOrd.make(alternateScopeOrd.toString());
      // Check for an additional context facet to decide whether to create a new Entity to use as
      // the alternate scope, or to just resolve the ORD to determine the alternate scope.
      if (base.getFacet(MAKE_ENTITY_FOR_ALTERNATE_SCOPE_FACET_KEY) == BBoolean.TRUE)
      {
        BIEntity entity = BEntityObjectWrapper.makeEntityObject(new BasicEntity(ord));
        scope = new OrdTarget(base, (BObject)entity);
      }
      else
      {
        scope = ord.resolve(BLocalHost.INSTANCE, base);
      }
    }

    BIQueryHandler handler =
      doFindQueryHandler(base, scope, (BQueryScheme)BOrdScheme.lookup(query.getScheme()));
    if (handler == null)
      throw new UnresolvedException("No available query handler for " + scope.get());

    return new OrdTarget(base, new BQueryResult(query, scope, handler));
  }

  /**
   * Find a query handler for the specified scope and scheme.
   *
   * @param scope The collection of objects that the query will be evaluated against.
   * @param queryScheme The scheme for the query to be evaluated.
   * @return Returns a BIQueryHandler or null if no handler can be found.
   */
  public static BIQueryHandler findQueryHandler(OrdTarget scope, BQueryScheme queryScheme)
  {
    return doFindQueryHandler(scope, scope, queryScheme);
  }

  /**
   * Find a query handler for the specified base, scope and scheme.
   *
   * @param base The base to look for a registered query handler. Can be the same as the scope.
   * @param scope The collection of objects that the query will be evaluated against.
   * @param queryScheme The scheme for the query to be evaluated.
   * @return Returns a BIQueryHandler or null if no handler can be found.
   *
   * @since Niagara 4.6
   */
  private static BIQueryHandler doFindQueryHandler(OrdTarget base, OrdTarget scope, BQueryScheme queryScheme)
  {
    BIQueryHandler handler = null;

    // find a query provider for the base object
    // first check to see if the base object is a QueryHandler
    // that can handle this type of query
    BObject baseObj = base.get();
    if (baseObj instanceof BIQueryHandler)
    {
      if (((BIQueryHandler)baseObj).canHandle(scope, queryScheme))
        handler = (BIQueryHandler)baseObj;
    }

    // if the base is not already an appropriate query handler, try
    // to find a separate QueryHandler for the base type
    if (handler == null)
    {
      // Starting in Niagara 4.4, check to see if any of the registered priority
      // query handlers (such as the BFoxQueryHandler) can accept this query.  Check in priority order.
      // We could have just used the agent registry to lookup query handlers as done originally,
      // but since we may need to give precedence to the SystemDb in Niagara 4.6, the original agent
      // lookup code below was finding and returning the first matching agent in the list
      // (and the order of this list could change based on modules installed on the system, so it
      // was unreliable).
      Iterator<PriorityHandler> iterator = priorityHandlers.iterator();
      while(handler == null && iterator.hasNext())
      {
        BIQueryHandler priorityHandler = iterator.next().handler;
        if (priorityHandler.canHandle(scope, queryScheme))
        {
          handler = priorityHandler;
        }
      }

      if (handler == null)
      {
        // As a last resort, find the QueryHandler agents registered on the target
        AgentList targetAgents =
          baseObj.getAgents().filter(AgentFilter.is(BIQueryHandler.TYPE));

        // find the QueryHandler agents registered on the scheme
        AgentList schemeAgents =
          queryScheme.getAgents().filter(AgentFilter.is(BIQueryHandler.TYPE));

        // find the first common QueryHandler agent between the target and the scheme
        int taCount = targetAgents.size();
        for (int i = 0; i < taCount; i++)
        {
          AgentInfo agent = targetAgents.get(i);
          if (schemeAgents.indexOf(agent) != -1)
          {
            handler = (BIQueryHandler)agent.getInstance();
            break;
          }
        }
      }
    }

    return handler;
  }

  /**
   * Register the given BIQueryHandler instance to be considered during query ord resolution.
   *
   * @param handler The BIQueryHandler instance to register
   * @param priority The priority level to use for the given priority query handler. Priority query
   *                 handlers give precedence to the lowest priority level. At query resolution time,
   *                 the priority query handler with the lowest priority level that can handle the
   *                 query will win.
   * @throws IllegalStateException if the given BIQueryHandler is already registered (even at a
   * different priority level) OR if the given priority level is already in use by another registered
   * priority query handler.
   * @throws NullPointerException if the given BIQueryHandler argument is null
   *
   * @since Niagara 4.4
   */
  public static void registerPriorityQueryHandler(BIQueryHandler handler, int priority)
  {
    Objects.requireNonNull(handler, "Cannot register a null priority query handler");

    if (priorityHandlers.stream().anyMatch(pHandler -> {
      return priority == pHandler.priority || pHandler.handler.equals(handler); }))
    {
      throw new IllegalStateException("Cannot re-register a priority query handler or register one with a priority level already in use");
    }

    PriorityHandler priorityHandler = new PriorityHandler(handler, priority);
    priorityHandlers.add(priorityHandler);
  }

  /**
   * Unregister the given BIQueryHandler instance from the priority handler list
   *
   * @param handler The BIQueryHandler instance to unregister
   * @return true if the instance was in the list and successfully unregistered
   *
   * @since Niagara 4.4
   */
  public static boolean unregisterPriorityQueryHandler(BIQueryHandler handler)
  {
    return priorityHandlers.removeIf(pHandler -> { return pHandler.handler.equals(handler); });
  }

////////////////////////////////////////////////////////////////
// PriorityHandler
////////////////////////////////////////////////////////////////

  private static class PriorityHandler
    implements Comparable<PriorityHandler>
  {
    public PriorityHandler(BIQueryHandler handler, int priority)
    {
      this.handler = handler;
      this.priority = priority;
    }

    @Override
    public int compareTo(PriorityHandler o)
    {
      return Integer.compare(priority, o.priority);
    }

    BIQueryHandler handler;
    int priority;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * For framework use only
   *
   * @since Niagara 4.4
   */
  public static void postInit()
  {
    if (Nre.spySysManagers.find("queryHandlers") == null)
    {
      Nre.spySysManagers.add("queryHandlers", new Page());
    }
  }

  private static class Page
    extends Spy
  {
    @Override
    public void write(SpyWriter out)
      throws Exception
    {
      if (priorityHandlers.isEmpty())
      {
        out.write("No priority query handlers registered");
        return;
      }

      out.startTable(true);
      out.w("<tr>");
      out.thTitle("Priority Level");
      out.thTitle("Registered Priority Query Handler");
      out.thTitle("Type");
      out.w("</tr>");

      priorityHandlers.forEach(pHandler -> {
          BIQueryHandler handler = pHandler.handler;
          String objString = handler.toString();
          if (handler instanceof BComponent)
          {
            BComponent comp = (BComponent)handler;
            if (comp.getSlotPath() != null)
              objString = comp.toDisplayPathString(null);
          }
          out.w("<tr>");
          out.td(pHandler.priority);
          out.w("<td align='left' nowrap='true'>").safe(objString).w("</td>");
          out.w("<td align='left' nowrap='true'>").safe(handler.getType().toString()).w("</td>");
          out.w("</tr>");
        });

      out.endTable();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * This facet key name can be optionally checked when resolving a query to enforce a limit
   * on the number of results.  Implementations of BIQueryHandler may or may not check for this
   * limit in the Context facets, so callers are not guaranteed to get back a limited set of
   * results.  Instead, this can be used to request a limit on the number of results, and if the
   * backend implementation of the BIQueryHandler supports limiting the results returned, it will
   * do so if a Context facet is set with this facet key and a positive BInteger value indicating
   * the maximum requested results to return for the processed query. A zero (or negative) BInteger
   * value paired with this facet key also means that no limit is requested.
   *
   * @since Niagara 4.7
   */
  public static final String QUERY_LIMIT_FACET_KEY = "queryLimit";

  /**
   * This facet key name will be checked on the base OrdTarget when resolving a
   * query to see if an alternate query scope should be used instead of the base when
   * resolving the query. If the {@link #MAKE_ENTITY_FOR_ALTERNATE_SCOPE_FACET_KEY} facet is also
   * set to a value of {@link BBoolean#TRUE}, then a new Entity is created with an entity ORD
   * matching that specified by this facet's value to be used as the alternate scope of the query.
   * If the {@link #MAKE_ENTITY_FOR_ALTERNATE_SCOPE_FACET_KEY} facet is not present or not set to a
   * value of {@link BBoolean#TRUE}, then this alternate query scope will be resolved just prior to
   * processing the query.
   *
   * @since Niagara 4.6
   */
  public static final String ALTERNATE_SCOPE_FACET_KEY = "alternateQueryScope";

  /**
   * This facet key will be checked on the base OrdTarget when resolving a query to decide whether
   * to create a new Entity object to use for the scope of the query to be resolved.  This facet key
   * is only checked for a {@link BBoolean#TRUE} value when the {@link #ALTERNATE_SCOPE_FACET_KEY}
   * facet is also specified on the base OrdTarget.  When these conditions are present, a new
   * Entity is created with an entity ORD matching that specified in the
   * {@link #ALTERNATE_SCOPE_FACET_KEY} facet value.  This alternate Entity is then used as the
   * scope of the query.  This is useful to support lightweight Entity queries without requiring
   * the scope to be resolved in the station space first.
   *
   * @since Niagara 4.6
   */
  public static final String MAKE_ENTITY_FOR_ALTERNATE_SCOPE_FACET_KEY = "makeEntityForAlternateScope";

  /**
   * The sorted set of priority query handlers
   *
   * @since Niagara 4.4
   */
  private static final SortedSet<PriorityHandler> priorityHandlers = Collections.synchronizedSortedSet(new TreeSet<>());

}
