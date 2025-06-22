/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.stream.Stream;

import javax.baja.job.BJob;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdScheme;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.neql.BNamespaceScheme;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.query.BQueryResult;
import javax.baja.query.BQueryScheme;
import javax.baja.security.BPermissions;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BVector;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.user.BUser;

/**
 * An individual search task.  It collects search results in memory on the local VM
 * where it was executed (ie. the station/server side only).  To kick off a search
 * task from a client VM, use the SearchService's search action.  Then to retrieve
 * results from a client VM, use the SearchService's retrieveResults action.
 *
 * @author Dan Heine
 * @author Scott Hoye
 * @creation 2013-08-19
 * @since Niagara 4.0
 */
@NiagaraType
/*
 String that defines the search query.  It is the String form of a query ORD.
 */
@NiagaraProperty(
  name = "query",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.OPERATOR
)
/*
 Total number of results available in the entire result set so far.
 This running total is not considered final until the task
 successfully completes.
 */
@NiagaraProperty(
  name = "resultCount",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.OPERATOR
)
/*
 If the total number of results exceeds the max size as defined
 by the BSearchService's maxResultsPerSearch property, then this
 property will be set to true indicating that the search results
 were capped.
 */
@NiagaraProperty(
  name = "resultsExceedLimit",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.OPERATOR
)
/*
 This hidden action is used to expire this search task which
 will force it to cancel and dispose itself.
 */
@NiagaraAction(
  name = "expire",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.NO_AUDIT | Flags.OPERATOR
)
public final class BSearchTask
  extends BJob
  implements Runnable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BSearchTask(782067882)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "query"

  /**
   * Slot for the {@code query} property.
   * String that defines the search query.  It is the String form of a query ORD.
   * @see #getQuery
   * @see #setQuery
   */
  public static final Property query = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.OPERATOR, "", null);

  /**
   * Get the {@code query} property.
   * String that defines the search query.  It is the String form of a query ORD.
   * @see #query
   */
  public String getQuery() { return getString(query); }

  /**
   * Set the {@code query} property.
   * String that defines the search query.  It is the String form of a query ORD.
   * @see #query
   */
  public void setQuery(String v) { setString(query, v, null); }

  //endregion Property "query"

  //region Property "resultCount"

  /**
   * Slot for the {@code resultCount} property.
   * Total number of results available in the entire result set so far.
   * This running total is not considered final until the task
   * successfully completes.
   * @see #getResultCount
   * @see #setResultCount
   */
  public static final Property resultCount = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.OPERATOR, 0, null);

  /**
   * Get the {@code resultCount} property.
   * Total number of results available in the entire result set so far.
   * This running total is not considered final until the task
   * successfully completes.
   * @see #resultCount
   */
  public long getResultCount() { return getLong(resultCount); }

  /**
   * Set the {@code resultCount} property.
   * Total number of results available in the entire result set so far.
   * This running total is not considered final until the task
   * successfully completes.
   * @see #resultCount
   */
  public void setResultCount(long v) { setLong(resultCount, v, null); }

  //endregion Property "resultCount"

  //region Property "resultsExceedLimit"

  /**
   * Slot for the {@code resultsExceedLimit} property.
   * If the total number of results exceeds the max size as defined
   * by the BSearchService's maxResultsPerSearch property, then this
   * property will be set to true indicating that the search results
   * were capped.
   * @see #getResultsExceedLimit
   * @see #setResultsExceedLimit
   */
  public static final Property resultsExceedLimit = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.OPERATOR, false, null);

  /**
   * Get the {@code resultsExceedLimit} property.
   * If the total number of results exceeds the max size as defined
   * by the BSearchService's maxResultsPerSearch property, then this
   * property will be set to true indicating that the search results
   * were capped.
   * @see #resultsExceedLimit
   */
  public boolean getResultsExceedLimit() { return getBoolean(resultsExceedLimit); }

  /**
   * Set the {@code resultsExceedLimit} property.
   * If the total number of results exceeds the max size as defined
   * by the BSearchService's maxResultsPerSearch property, then this
   * property will be set to true indicating that the search results
   * were capped.
   * @see #resultsExceedLimit
   */
  public void setResultsExceedLimit(boolean v) { setBoolean(resultsExceedLimit, v, null); }

  //endregion Property "resultsExceedLimit"

  //region Action "expire"

  /**
   * Slot for the {@code expire} action.
   * This hidden action is used to expire this search task which
   * will force it to cancel and dispose itself.
   * @see #expire()
   */
  public static final Action expire = newAction(Flags.HIDDEN | Flags.READONLY | Flags.NO_AUDIT | Flags.OPERATOR, null);

  /**
   * Invoke the {@code expire} action.
   * This hidden action is used to expire this search task which
   * will force it to cancel and dispose itself.
   * @see #expire
   */
  public void expire() { invoke(expire, null, null); }

  //endregion Action "expire"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSearchTask.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * For framework use only.  Do not use this constructor.
   */
  public BSearchTask()
  {
  }

  /**
   * Creates a BSearchTask instance using the parameters to
   * initialize the search query arguments.  A BajaRuntimeException
   * will be thrown if any of the parameters are invalid
   * for performing a search.
   *
   * @param searchParams
   * @param cx
   * @throws BajaRuntimeException if any of the parameters are
   * invalid for performing a search
   */
  public BSearchTask(BSearchParams searchParams, Context cx)
  {
    setQuery(searchParams.getQuery());

    BOrdScheme ordScheme = null;
    try
    {
      OrdQuery[] queries = BOrd.make(searchParams.getQuery()).parse();

      Optional<BOrdScheme> scheme = BOrdScheme.find(queries[queries.length - 1].getScheme());
      if (scheme.isPresent())
        ordScheme = scheme.get();
    }
    catch(Exception ex)
    {
    }

    if (ordScheme == null)
    {
      String defaultScheme = BSearchService.getService().getDefaultScheme();
      if (defaultScheme.length() > 0)
      {
        // See if the text parses using the default scheme.  If it
        // does, prepend the scheme.
        try
        {
          String newQuery = defaultScheme + ":" + searchParams.getQuery();
          BOrd.make(newQuery).parse();

          // testOrd parses, so use the default scheme
          searchParams.setQuery(newQuery);
          ordScheme = BOrdScheme.lookup(defaultScheme);
        }
        catch (Exception ex)
        {
          // testOrd doesn't parse using default scheme
        }
      }
    }

    if (ordScheme == null)
      throw new LocalizableRuntimeException("search", "search.error.noQueryScheme");

    // Examine the scopes to validate them for user access.
    filterScopes(ordScheme, searchParams.getScopeVector(), cx);

    if (searchParams.getScopeVector().getPropertyCount() == 0)
      throw new LocalizableRuntimeException("search", "search.error.noScopesForQueryScheme");

    // Prevent ORD injections by ensuring that there is only one ORD query, or if more than one,
    // the first one can only be the "namespace" ORD scheme.
    OrdQuery[] queries = BOrd.make(searchParams.getQuery()).parse();
    if ((queries.length > 2) ||
      ((queries.length > 1) && (!queries[0].getScheme().equals(BNamespaceScheme.SCHEME_ID))))
      throw new LocalizableRuntimeException("search", "search.error.invalidQueryOrd");

    this.searchParams = searchParams;
    searchContext = cx;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return the results for this search task.  Results are only available when
   * the search task has been run (or is running).  Results are also only available
   * in the context of the station VM.
   *
   * @return a BSearchResultSet instance containing the cached results.
   */
  public BSearchResultSet getResults()
  {
    checkExpiration(/*resetExpiration*/true);
    return searchResults;
  }

  /**
   * Called when the expire action is invoked.
   */
  public void doExpire(Context cx)
  {
    try
    {
      if ((expirationTicket != null) && (!expirationTicket.isExpired()))
      {
        expirationTicket.cancel();
        expirationTicket = null;
      }

      // First try to expire the task the graceful way, but failover
      // to force it to be removed.
      if (getJobState().isRunning())
        doCancel(cx);
      doDispose(cx);
    }
    catch(Exception e)
    {
      BSearchService.logger.fine("Could not dispose search task gracefully. Forcing removal." + e);
      if(getParent()!=null)
        ((BComponent)getParent()).remove(getPropertyInParent(), cx);
    }
  }

////////////////////////////////////////////////////////////////
// Runnable
////////////////////////////////////////////////////////////////

  @Override
  public void run()
  {
    try
    {
      doRun(searchContext);
    }
    catch(Exception e)
    { // Already handled in doRun
    }
  }

////////////////////////////////////////////////////////////////
// Job
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>BSearchService.getService().submitSearchTask(this, cx)</code>.
   */
  @Override
  public BOrd submit(Context cx)
  {
    return BSearchService.getService().submitSearchTask(this, cx);
  }

  /**
   * This is the callback to begin the task.  All work should be done
   * on a background thread - never block the callers thread.  During
   * the task execution, subclasses should periodically update progress
   * via the <code>progress()</code> or <code>heartbeat()</code> method.
   * Diagnostics information may be dumped via the log() method.  Once
   * the run finishes, the subclass must invoke one of the completion
   * methods (success, canceled, failed, or complete).
   */
  @Override
  public void doRun(Context cx)
    throws Exception
  {
    try
    {
      BSearchService searchService = BSearchService.getService();
      int maxResultsPerSearch = searchService.getMaxResultsPerSearch();
      this.searchResults.setMaxResults(maxResultsPerSearch);

      if (maxResultsPerSearch > 0)
      {
        if (maxResultsPerSearch < Integer.MAX_VALUE)
        { // Make the requested limit one more than the maxResultsPerSearch property value obtained
          // from the SearchService so that we can know when to add the '+' indicator on the display
          // for the number of search results (e.g. "500+ Results" versus "500 Records")
          maxResultsPerSearch++;
        }
        this.searchContext = new BasicContext(searchContext,
          BFacets.make(BQueryScheme.QUERY_LIMIT_FACET_KEY, maxResultsPerSearch));
      }

      BOrd query = searchParams.getQueryOrd();
      if (query == null)
      {
        failed(new LocalizableRuntimeException("search", "search.error.noQueryScheme"));
        return;
      }

      BIObject[] scopes = searchParams.resolveScopes();
      int scopeCount = scopes.length;
      if (scopeCount < 1)
      {
        failed(new BajaRuntimeException("No scopes found for the search submitted"));
        return;
      }

      // Handle list of scopes passed in by the search request
      CompletableFuture<?>[] futures = new CompletableFuture<?>[scopeCount];
      Executor executor = searchService.getExecutor();
      for (int i = 0; i < scopeCount; i++)
      {
        BIObject scope = scopes[i];
        futures[i] = CompletableFuture.runAsync(() -> {
          retrieveQueryResults(query, scope, searchContext);
        }, executor);
      }

      allTasksFuture = CompletableFuture.allOf(futures);
      allTasksFuture.handle((ok, ex) -> {
        searchResults.setResultsComplete(true);
        if ((ex == null) || (ex instanceof CancellationException))
        {
          if (isAlive())
            success();
        }
        else
        {
          failed(ex);
          if (BSearchService.logger.isLoggable(Level.WARNING))
            BSearchService.logger.log(Level.WARNING, toPathString() + " search failed", ex);
        }
        checkExpiration(/*resetExpiration*/false);
        return null;
      });

    }
    catch(Exception ex)
    {
      failed(ex);
      if (BSearchService.logger.isLoggable(Level.SEVERE))
        BSearchService.logger.log(Level.SEVERE, toPathString() + " search could not be processed", ex);
    }
  }

  /**
   * This callback is invoked when the user manually cancels a
   * running task.  It is up to the subclass to terminate the task on
   * the background thread.  Typically the state should be set to
   * canceling while waiting for the background thread to terminate.
   */
  @Override
  public void doCancel(Context cx)
    throws Exception
  {
    canceled();
    if ((allTasksFuture != null) && (!allTasksFuture.isDone()))
      allTasksFuture.cancel(true);
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  @Override
  public void stopped()
    throws Exception
  {
    if (expirationTicket != null)
    {
      expirationTicket.cancel();
      expirationTicket = null;
    }

    // If the Task is running, clean it up
    if (getJobState().isRunning())
      doCancel(null);

    super.stopped();
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  @Override
  public void subscribed()
  {
    checkExpiration(/*resetExpiration*/true);
  }

  /**
   * Callback when the component exits the subscribed state.
   */
  @Override
  public void unsubscribed()
  {
    checkExpiration(/*resetExpiration*/false);
  }

  @Override
  public BPermissions getPermissions(Context cx)
  {
    // If the user is not the one that invoked this search task
    // (and the user is not a super user), then restrict access to this search task
    if (cx != null)
    {
      BUser user = cx.getUser();
      BUser searchUser = (searchContext != null)?searchContext.getUser():null;
      if (user != null && user != searchUser && !user.getPermissions().isSuperUser())
        return BPermissions.none;
    }

    return super.getPermissions(cx);
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  void retrieveQueryResults(BOrd query, BIObject scope, Context context)
  {
    Stream<Entity> stream = null;

    // This "lightweightSystemDbQueryResults" facet is currently used by BOrientSystemDb for better
    // search efficiency
    Context cx = new BasicContext(context, lightweightQueryResultsFacets);
    try
    {
      OrdQuery[] queries = query.parse();
      BOrdScheme ordScheme = BOrdScheme.lookup(queries[queries.length - 1].getScheme());
      if (ordScheme instanceof BQueryScheme)
      {
        BQueryResult queryResult = (BQueryResult)query.resolve((BObject)scope, cx).get();
        stream = queryResult.stream();
      }
      else
      {
        BISearchProvider provider = BSearchService.findSearchProvider((BObject)scope, ordScheme);
        stream = provider.search(query, scope, cx);
      }
      searchResults.addResults(stream, cx);
    }
    finally
    {
      if (stream != null)
        stream.close();

      // indicate that this subtask is complete and update the progress and result count
      boolean resultsExceedLimit = searchResults.getResultsExceedLimit();
      if (resultsExceedLimit)
        setResultsExceedLimit(true);
      setResultCount(searchResults.getResultCount());
      heartbeat();

      if (resultsExceedLimit)
      { // If we've reached the search result limit, we can cancel any other outstanding queries
        // since we don't have room for them anyways
        if ((allTasksFuture != null) && (!allTasksFuture.isDone()))
          allTasksFuture.cancel(true);
      }
    }
  }

  /**
   * Check the search scopes requested by the user in the specified context.
   * If the list is not empty, filter it based on read access to the specified scopes (and
   * the scopes must have a registered BIQueryHandler that is also registered on the given
   * queryScheme).
   *
   * @param requestScopes The list of BOrds submitted as search scopes
   *                      through a user request.
   * @param cx The context containing the user for the request.
   */
  static void filterScopes(BOrdScheme ordScheme, BVector requestScopes, Context cx)
  {
    if (requestScopes.getPropertyCount() == 0)
      return;

    // if not empty, remove the ones that the user cannot access
    ArrayList<Property> toRemove = null;
    SlotCursor<Property> c = requestScopes.getProperties();
    while (c.next(BOrd.class))
    {
      BOrd scopeOrd = (BOrd)c.get();
      OrdTarget scope = scopeOrd.resolve(BSearchService.getService(), cx);
      if (!scope.canRead() || !BSearchService.validSchemeForScope(ordScheme, scope))
      {
        if (toRemove == null)
          toRemove = new ArrayList<>();
        toRemove.add(c.property());
      }
    }

    if (toRemove != null)
    {
      Iterator<Property> i = toRemove.iterator();
      while (i.hasNext())
      {
        requestScopes.remove(i.next());
      }
    }
  }

  void checkExpiration(boolean resetExpiration)
  {
    if (!isRunning()) return; // Only valid when mounted station side

    synchronized(lock)
    {
      boolean subscribed = isSubscribed();
      if ((expirationTicket != null) && (subscribed || resetExpiration))
      {
        expirationTicket.cancel();
        expirationTicket = null;
      }

      if ((expirationTicket == null) && !subscribed)
      {
        expirationTicket = Clock.schedule(this,
                                          BSearchService.getService().getSearchTaskTimeToLive(),
                                          expire,
                                          null);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("magnifyingGlass.png");

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private static final BFacets lightweightQueryResultsFacets =
    BFacets.make("lightweightSystemDbQueryResults", true);

  private BSearchResultSet searchResults = new BSearchResultSet();
  private CompletableFuture<Void> allTasksFuture;
  BSearchParams searchParams;
  Context searchContext;
  Clock.Ticket expirationTicket;
  private final Object lock = new Object();

}
