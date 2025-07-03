/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.search;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.license.Feature;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdScheme;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnknownSchemeException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.query.BIQueryHandler;
import javax.baja.query.BQueryScheme;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BIProtected;
import javax.baja.space.BISpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Action;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.BVector;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.nre.util.NreForkJoinWorkerThreadFactory;
import com.tridium.sys.engine.NClockTicket;
import com.tridium.sys.license.LicenseUtil;

/**
 * The Niagara search service.
 *
 * @author Dan Heine, Scott Hoye on 2013-08-19
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The default search or query scheme.  The default scheme is assumed
 when the scheme is omitted from the search request.
 */
@NiagaraProperty(
  name = "defaultScheme",
  type = "String",
  defaultValue = "neql"
)
/*
 The list of scopes designated as suggested defaults.  These
 scopes will be marked as defaults in the list returned
 by getSearchScopes().  Each element in the list is expected
 to be either a BSearchScope or a BOrd.
 */
@NiagaraProperty(
  name = "defaultScopes",
  type = "BVector",
  defaultValue = "makeDefaultSearchScopes()"
)
/*
 This size determines the maximum number of concurrent
 (active) searches that are allowed at one time.  Active
 search tasks are those that are in a subscribed state.
 */
@NiagaraProperty(
  name = "maxConcurrentSearches",
  type = "int",
  defaultValue = "50",
  facets = @Facet(name = "BFacets.MIN", value = "1")
)
/*
 For each search request submitted, this size determines
 the maximum number of results that will be cached in memory
 for the duration of the search task.
 */
@NiagaraProperty(
  name = "maxResultsPerSearch",
  type = "int",
  defaultValue = "500",
  facets = @Facet(name = "BFacets.MIN", value = "1")
)
/*
 For each Search Task child created in the activeSearchContainer
 as the result of a search invocation, this time to live value
 determines how long the Search Task will linger in the station
 before it is automatically removed if it has not been used
 during this time.  If the Search Task is subscribed or accessed
 in any way, it will reset the expiration such that the Search
 Task will remain until it is no longer used and this time to live
 has expired since it was last in a subscription state or otherwise
 accessed.
 */
@NiagaraProperty(
  name = "searchTaskTimeToLive",
  type = "BRelTime",
  defaultValue = "BRelTime.makeMinutes(2)",
  facets = @Facet(name = "BFacets.MIN", value = "BRelTime.makeSeconds(1)")
)
/*
 A container for the active search tasks.  We need this container
 because the active search tasks will linger in memory as long as
 they are subscribed, so we don't want views on the SearchService
 (such as the property sheet) causing the active search tasks to
 remain subscribed even if a user isn't actively viewing the search
 results for a given search task.
 */
@NiagaraProperty(
  name = "activeSearchContainer",
  type = "BVector",
  defaultValue = "new BVector()",
  flags = Flags.HIDDEN | Flags.OPERATOR | Flags.TRANSIENT
)
/*
 Perform asynchronous search for data based on user specified search parameters.
 Search results are returned via an Ord that maps to a BSearchTask.
 */
@NiagaraAction(
  name = "search",
  parameterType = "BSearchParams",
  defaultValue = "new BSearchParams()",
  returnType = "BOrd",
  flags = Flags.HIDDEN | Flags.OPERATOR
)
/*
 Retrieve results from a search operation.
 */
@NiagaraAction(
  name = "retrieveResults",
  parameterType = "BResultsRequest",
  defaultValue = "BResultsRequest.DEFAULT",
  returnType = "BSearchResultSet",
  flags = Flags.HIDDEN | Flags.NO_AUDIT | Flags.OPERATOR
)
/*
 Retrieve the list of available search scopes.
 */
@NiagaraAction(
  name = "getSearchScopes",
  returnType = "BVector",
  flags = Flags.HIDDEN | Flags.NO_AUDIT | Flags.OPERATOR
)
/*
 Update the name and lexicon info for any blank values
 in the default scopes.
 */
@NiagaraAction(
  name = "updateDefaultScopeInfo",
  flags = Flags.ASYNC
)
public final class BSearchService
  extends BAbstractService
{
  // Must be placed here due to class loading issues since it's referenced in other static methods
  private static final BOrd SYS_ORD = BOrd.make("sys:");

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BSearchService(4186685741)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultScheme"

  /**
   * Slot for the {@code defaultScheme} property.
   * The default search or query scheme.  The default scheme is assumed
   * when the scheme is omitted from the search request.
   * @see #getDefaultScheme
   * @see #setDefaultScheme
   */
  public static final Property defaultScheme = newProperty(0, "neql", null);

  /**
   * Get the {@code defaultScheme} property.
   * The default search or query scheme.  The default scheme is assumed
   * when the scheme is omitted from the search request.
   * @see #defaultScheme
   */
  public String getDefaultScheme() { return getString(defaultScheme); }

  /**
   * Set the {@code defaultScheme} property.
   * The default search or query scheme.  The default scheme is assumed
   * when the scheme is omitted from the search request.
   * @see #defaultScheme
   */
  public void setDefaultScheme(String v) { setString(defaultScheme, v, null); }

  //endregion Property "defaultScheme"

  //region Property "defaultScopes"

  /**
   * Slot for the {@code defaultScopes} property.
   * The list of scopes designated as suggested defaults.  These
   * scopes will be marked as defaults in the list returned
   * by getSearchScopes().  Each element in the list is expected
   * to be either a BSearchScope or a BOrd.
   * @see #getDefaultScopes
   * @see #setDefaultScopes
   */
  public static final Property defaultScopes = newProperty(0, makeDefaultSearchScopes(), null);

  /**
   * Get the {@code defaultScopes} property.
   * The list of scopes designated as suggested defaults.  These
   * scopes will be marked as defaults in the list returned
   * by getSearchScopes().  Each element in the list is expected
   * to be either a BSearchScope or a BOrd.
   * @see #defaultScopes
   */
  public BVector getDefaultScopes() { return (BVector)get(defaultScopes); }

  /**
   * Set the {@code defaultScopes} property.
   * The list of scopes designated as suggested defaults.  These
   * scopes will be marked as defaults in the list returned
   * by getSearchScopes().  Each element in the list is expected
   * to be either a BSearchScope or a BOrd.
   * @see #defaultScopes
   */
  public void setDefaultScopes(BVector v) { set(defaultScopes, v, null); }

  //endregion Property "defaultScopes"

  //region Property "maxConcurrentSearches"

  /**
   * Slot for the {@code maxConcurrentSearches} property.
   * This size determines the maximum number of concurrent
   * (active) searches that are allowed at one time.  Active
   * search tasks are those that are in a subscribed state.
   * @see #getMaxConcurrentSearches
   * @see #setMaxConcurrentSearches
   */
  public static final Property maxConcurrentSearches = newProperty(0, 50, BFacets.make(BFacets.MIN, 1));

  /**
   * Get the {@code maxConcurrentSearches} property.
   * This size determines the maximum number of concurrent
   * (active) searches that are allowed at one time.  Active
   * search tasks are those that are in a subscribed state.
   * @see #maxConcurrentSearches
   */
  public int getMaxConcurrentSearches() { return getInt(maxConcurrentSearches); }

  /**
   * Set the {@code maxConcurrentSearches} property.
   * This size determines the maximum number of concurrent
   * (active) searches that are allowed at one time.  Active
   * search tasks are those that are in a subscribed state.
   * @see #maxConcurrentSearches
   */
  public void setMaxConcurrentSearches(int v) { setInt(maxConcurrentSearches, v, null); }

  //endregion Property "maxConcurrentSearches"

  //region Property "maxResultsPerSearch"

  /**
   * Slot for the {@code maxResultsPerSearch} property.
   * For each search request submitted, this size determines
   * the maximum number of results that will be cached in memory
   * for the duration of the search task.
   * @see #getMaxResultsPerSearch
   * @see #setMaxResultsPerSearch
   */
  public static final Property maxResultsPerSearch = newProperty(0, 500, BFacets.make(BFacets.MIN, 1));

  /**
   * Get the {@code maxResultsPerSearch} property.
   * For each search request submitted, this size determines
   * the maximum number of results that will be cached in memory
   * for the duration of the search task.
   * @see #maxResultsPerSearch
   */
  public int getMaxResultsPerSearch() { return getInt(maxResultsPerSearch); }

  /**
   * Set the {@code maxResultsPerSearch} property.
   * For each search request submitted, this size determines
   * the maximum number of results that will be cached in memory
   * for the duration of the search task.
   * @see #maxResultsPerSearch
   */
  public void setMaxResultsPerSearch(int v) { setInt(maxResultsPerSearch, v, null); }

  //endregion Property "maxResultsPerSearch"

  //region Property "searchTaskTimeToLive"

  /**
   * Slot for the {@code searchTaskTimeToLive} property.
   * For each Search Task child created in the activeSearchContainer
   * as the result of a search invocation, this time to live value
   * determines how long the Search Task will linger in the station
   * before it is automatically removed if it has not been used
   * during this time.  If the Search Task is subscribed or accessed
   * in any way, it will reset the expiration such that the Search
   * Task will remain until it is no longer used and this time to live
   * has expired since it was last in a subscription state or otherwise
   * accessed.
   * @see #getSearchTaskTimeToLive
   * @see #setSearchTaskTimeToLive
   */
  public static final Property searchTaskTimeToLive = newProperty(0, BRelTime.makeMinutes(2), BFacets.make(BFacets.MIN, BRelTime.makeSeconds(1)));

  /**
   * Get the {@code searchTaskTimeToLive} property.
   * For each Search Task child created in the activeSearchContainer
   * as the result of a search invocation, this time to live value
   * determines how long the Search Task will linger in the station
   * before it is automatically removed if it has not been used
   * during this time.  If the Search Task is subscribed or accessed
   * in any way, it will reset the expiration such that the Search
   * Task will remain until it is no longer used and this time to live
   * has expired since it was last in a subscription state or otherwise
   * accessed.
   * @see #searchTaskTimeToLive
   */
  public BRelTime getSearchTaskTimeToLive() { return (BRelTime)get(searchTaskTimeToLive); }

  /**
   * Set the {@code searchTaskTimeToLive} property.
   * For each Search Task child created in the activeSearchContainer
   * as the result of a search invocation, this time to live value
   * determines how long the Search Task will linger in the station
   * before it is automatically removed if it has not been used
   * during this time.  If the Search Task is subscribed or accessed
   * in any way, it will reset the expiration such that the Search
   * Task will remain until it is no longer used and this time to live
   * has expired since it was last in a subscription state or otherwise
   * accessed.
   * @see #searchTaskTimeToLive
   */
  public void setSearchTaskTimeToLive(BRelTime v) { set(searchTaskTimeToLive, v, null); }

  //endregion Property "searchTaskTimeToLive"

  //region Property "activeSearchContainer"

  /**
   * Slot for the {@code activeSearchContainer} property.
   * A container for the active search tasks.  We need this container
   * because the active search tasks will linger in memory as long as
   * they are subscribed, so we don't want views on the SearchService
   * (such as the property sheet) causing the active search tasks to
   * remain subscribed even if a user isn't actively viewing the search
   * results for a given search task.
   * @see #getActiveSearchContainer
   * @see #setActiveSearchContainer
   */
  public static final Property activeSearchContainer = newProperty(Flags.HIDDEN | Flags.OPERATOR | Flags.TRANSIENT, new BVector(), null);

  /**
   * Get the {@code activeSearchContainer} property.
   * A container for the active search tasks.  We need this container
   * because the active search tasks will linger in memory as long as
   * they are subscribed, so we don't want views on the SearchService
   * (such as the property sheet) causing the active search tasks to
   * remain subscribed even if a user isn't actively viewing the search
   * results for a given search task.
   * @see #activeSearchContainer
   */
  public BVector getActiveSearchContainer() { return (BVector)get(activeSearchContainer); }

  /**
   * Set the {@code activeSearchContainer} property.
   * A container for the active search tasks.  We need this container
   * because the active search tasks will linger in memory as long as
   * they are subscribed, so we don't want views on the SearchService
   * (such as the property sheet) causing the active search tasks to
   * remain subscribed even if a user isn't actively viewing the search
   * results for a given search task.
   * @see #activeSearchContainer
   */
  public void setActiveSearchContainer(BVector v) { set(activeSearchContainer, v, null); }

  //endregion Property "activeSearchContainer"

  //region Action "search"

  /**
   * Slot for the {@code search} action.
   * Perform asynchronous search for data based on user specified search parameters.
   * Search results are returned via an Ord that maps to a BSearchTask.
   * @see #search(BSearchParams parameter)
   */
  public static final Action search = newAction(Flags.HIDDEN | Flags.OPERATOR, new BSearchParams(), null);

  /**
   * Invoke the {@code search} action.
   * Perform asynchronous search for data based on user specified search parameters.
   * Search results are returned via an Ord that maps to a BSearchTask.
   * @see #search
   */
  public BOrd search(BSearchParams parameter) { return (BOrd)invoke(search, parameter, null); }

  //endregion Action "search"

  //region Action "retrieveResults"

  /**
   * Slot for the {@code retrieveResults} action.
   * Retrieve results from a search operation.
   * @see #retrieveResults(BResultsRequest parameter)
   */
  public static final Action retrieveResults = newAction(Flags.HIDDEN | Flags.NO_AUDIT | Flags.OPERATOR, BResultsRequest.DEFAULT, null);

  /**
   * Invoke the {@code retrieveResults} action.
   * Retrieve results from a search operation.
   * @see #retrieveResults
   */
  public BSearchResultSet retrieveResults(BResultsRequest parameter) { return (BSearchResultSet)invoke(retrieveResults, parameter, null); }

  //endregion Action "retrieveResults"

  //region Action "getSearchScopes"

  /**
   * Slot for the {@code getSearchScopes} action.
   * Retrieve the list of available search scopes.
   * @see #getSearchScopes()
   */
  public static final Action getSearchScopes = newAction(Flags.HIDDEN | Flags.NO_AUDIT | Flags.OPERATOR, null);

  /**
   * Invoke the {@code getSearchScopes} action.
   * Retrieve the list of available search scopes.
   * @see #getSearchScopes
   */
  public BVector getSearchScopes() { return (BVector)invoke(getSearchScopes, null, null); }

  //endregion Action "getSearchScopes"

  //region Action "updateDefaultScopeInfo"

  /**
   * Slot for the {@code updateDefaultScopeInfo} action.
   * Update the name and lexicon info for any blank values
   * in the default scopes.
   * @see #updateDefaultScopeInfo()
   */
  public static final Action updateDefaultScopeInfo = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code updateDefaultScopeInfo} action.
   * Update the name and lexicon info for any blank values
   * in the default scopes.
   * @see #updateDefaultScopeInfo
   */
  public void updateDefaultScopeInfo() { invoke(updateDefaultScopeInfo, null, null); }

  //endregion Action "updateDefaultScopeInfo"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSearchService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BSearchService()
  {
  }

////////////////////////////////////////////////////////////////
// Service
////////////////////////////////////////////////////////////////

  @Override
  public Type[] getServiceTypes()
  {
    return new Type[] { TYPE };
  }

  @Override
  public final void serviceStarted()
  {
    if (isOperational())
    {
      // Default number of threads is twice the number of CPUs in order to handle the blocking I/O
      // paradigm in Niagara.  If tasks used only asynchronous, non-blocking I/O,
      // the number of threads could be reduced back to the number of CPUs to reduce stack
      // requirements.
      int threadsPerCPU = Integer.parseInt(AccessController.doPrivileged((PrivilegedAction<String>) () ->
        System.getProperty("niagara.search.threadsPerCPU", String.valueOf(2))));
      int defaultThreads = Runtime.getRuntime().availableProcessors() * threadsPerCPU;

      // Allow total override in system.properties for some installations if needed
      int threads = Integer.parseInt(AccessController.doPrivileged((PrivilegedAction<String>) () ->
        System.getProperty("niagara.search.threads", String.valueOf(defaultThreads))));

      // Create the ForkJoinPool
      executor = new ForkJoinPool(threads,
                                  NreForkJoinWorkerThreadFactory.DEFAULT_INSTANCE,
                                  new UncaughtSearchExceptionHandler(),
                                  /* asyncMode = */ true);

      try
      {
        // If any of the default scopes need property updates due to missing fields, do it now
        SlotCursor<Property> c = getDefaultScopes().getProperties();
        Exception resolveError = null;
        while (c.next(BSearchScope.class))
        {
          BSearchScope scope = (BSearchScope)c.get();
          if (scope.getScopeName().isEmpty() &&
              (scope.getScopeLexiconModule().isEmpty() ||
               scope.getScopeLexiconKey().isEmpty()))
          {
            BOrd scopeOrd = scope.getScopeOrd();
            BObject scopeObj;
            try
            {
              scopeObj = scopeOrd.resolve(this, null).get();
            }
            catch(Exception ex)
            {
              // Suppress nuisance warnings when trying to resolve the "sys:"
              // scope if the System Database or its modules are not installed
              // on the station
              if (resolveError == null &&
                  !((ex instanceof UnresolvedException || ex instanceof UnknownSchemeException) &&
                    SYS_ORD.equals(scopeOrd)))
              {
                resolveError = ex;
              }
              continue;
            }

            if (scopeObj instanceof BISpace)
            {
              BISpace space = (BISpace)scopeObj;
              scope.setScopeName(space.getDisplayName(null));
              scope.setScopeLexiconModule(space.getLexiconText().module.getModuleName());
              scope.setScopeLexiconKey(space.getLexiconText().key);
            }
            else if (scopeObj.isComponent())
            {
              BComponent comp = (BComponent)scopeObj;
              scope.setScopeName(comp.getDisplayName(null));
            }
          }
        }

        if (resolveError != null)
        { // If we encountered any ORD resolution errors while checking the scopes, report it after
          // walking through all of them
          throw resolveError;
        }
      }
      catch(Exception e)
      {
        String warningMsg =
          "Could not update missing default search scope properties. Users will need to manually update them under the SearchService";
        if (logger.isLoggable(Level.FINE))
        {
          // Add the exception to the warning log message only when FINE level
          // is enabled, otherwise it's too much detail
          logger.log(Level.WARNING, warningMsg, e);
        }
        else
        {
          logger.log(Level.WARNING, warningMsg);
        }
      }
    }
  }

  @Override
  public final void serviceStopped()
  {
    if (executor == null)
    {
      return;
    }

    // Shut down the pool
    executor.shutdownNow();
  }

  /**
   * This method is called when moving from disabled
   * state into the enabled state.
   */
  @Override
  protected final void enabled()
  {
    serviceStarted();
  }

  /**
   * This method is called when moving from enabled
   * state into the disabled state.
   */
  @Override
  protected final void disabled()
  {
    serviceStopped();
  }

  /**
   * Return the BSearchService object when running in the station.
   *
   * @return search service
   * @throws ServiceNotFoundException if no SearchService is
   *    in the local station or if this method is not called from
   *    the context of the station VM.
   */
  public static BSearchService getService()
  {
    return (BSearchService)Sys.getService(TYPE);
  }

////////////////////////////////////////////////////////////////
// Licensing
////////////////////////////////////////////////////////////////

  /**
   * Return a "search" license feature
   */
  @Override
  public final Feature getLicenseFeature()
  {
    Feature feature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "search");
    allowLocalSearch = feature.getb("local", false);
    allowSystemSearch = feature.getb("system", false);
    if (!allowLocalSearch && !allowSystemSearch)
      configFatal("Unlicensed for both local and system searches. No searching is allowed.");
    return feature;
  }

///////////////////////////////////////////////////////////
// Component
///////////////////////////////////////////////////////////

  @Override
  public void changed(Property property, Context context)
  {
    if (isRunning())
    {
      if (property.equals(searchTaskTimeToLive))
      { // Need to update all child SearchTasks of the new expiration time
        SlotCursor<Property> c = getActiveSearchContainer().getProperties();
        while (c.next(BSearchTask.class))
          ((BSearchTask)c.get()).checkExpiration(/*resetExpiration*/true);
      }
      else if (property.equals(maxConcurrentSearches))
      {
        try { checkConcurrentSearchLimit(/*reduceByOne*/false); }
        catch(Exception ignore) {} // Suppress any exceptions enforcing the limit
      }
    }

    super.changed(property, context);
  }

  @Override
  public IFuture post(Action action, BValue argument, Context cx)
  {
    if (action == updateDefaultScopeInfo)
      new Thread(new Invocation(this, action, argument, cx)).start();
    return null;
  }

////////////////////////////////////////////////////////////////////////////////
// API
////////////////////////////////////////////////////////////////////////////////

  /**
   * Search a scope using the passed-in executor (or the default executor
   * if the passed-in executor is null) and context.
   *
   * @param searchParams search parameters
   * @param executor executor to use for search execution
   * @param cx Context to use for the search
   * @return a running search task
   */
  public BSearchTask executeSearch(BSearchParams searchParams, Executor executor, Context cx)
  {
    if (!isOperational())
      throw new NotRunningException("The SearchService is not operational");

    if (executor == null)
      executor = getExecutor();

    BSearchTask searchTask = new BSearchTask(searchParams, cx);
    executor.execute(searchTask);
    return searchTask;
  }

  /**
   * Search a scope using the passed-in executor, or the default executor
   * if the passed-in executor is null.
   *
   * @param searchParams search parameters
   * @param executor  executor to use for search execution
   * @return a running search task
   */
  public BSearchTask executeSearch(BSearchParams searchParams, Executor executor)
  {
    return executeSearch(searchParams, executor, null);
  }

  /**
   * Search a scope using the default executor.
   *
   * @param searchParams search parameters
   * @return a running search task
   */
  public BSearchTask executeSearch(BSearchParams searchParams)
  {
    return executeSearch(searchParams, null, null);
  }

  /**
   * Kick off a search task using the given search parameters
   * and return the BOrd to the BSearchTask created.
   *
   * @param searchParams search parameters
   * @param cx The context associated with the search request
   * @return absolute Ord
   */
  public BOrd doSearch(BSearchParams searchParams, Context cx)
  {
    if (!isOperational())
      throw new NotRunningException("The SearchService is not operational");

    BSearchTask searchTask = new BSearchTask(searchParams, cx);
    BOrd slotPathOrd = submitSearchTask(searchTask, cx);
    return BOrd.make(getSpace().getAbsoluteOrd(), slotPathOrd);
  }

  /**
   * Retrieve results from a search task.
   *
   * @param request The parameters to use for retrieving search results
   * @param cx The context associated with the request
   * @return
   */
  public BSearchResultSet doRetrieveResults(BResultsRequest request, Context cx)
  {
    if (!isOperational())
      throw new NotRunningException("The SearchService is not operational");

    BSearchTask task = (BSearchTask)request.getTaskOrd().get(null, cx);

    if (task != null)
    {
      return BSearchResultSet.make(task.getResults(), request.getStartIndex(),
        request.getMaxResults(), cx);
    }

    throw new IllegalArgumentException("Unknown task type");
  }

  /**
   * Fill in any blank information that can be determined
   * for the default scopes.
   */
  public void doUpdateDefaultScopeInfo()
  {
    if (!isOperational())
      throw new NotRunningException("The SearchService is not operational");

    SlotCursor<Property> c = getDefaultScopes().getProperties();
    while (c.next(BSearchScope.class))
    {
      BSearchScope scope = (BSearchScope)c.get();
      BOrd scopeOrd = scope.getScopeOrd();
      try
      {
        BObject scopeObj = scopeOrd.resolve(this, null).get();
        if (scopeObj instanceof BISpace)
        {
          BISpace space = (BISpace)scopeObj;
          if (scope.getScopeName().length() == 0)
            scope.setScopeName(space.getDisplayName(null));
          if (scope.getScopeLexiconModule().length() == 0)
            scope.setScopeLexiconModule(space.getLexiconText().module.getModuleName());
          if (scope.getScopeLexiconKey().length() == 0)
            scope.setScopeLexiconKey(space.getLexiconText().key);
        }
        else if (scopeObj.isComponent())
        {
          BComponent comp = (BComponent)scopeObj;
          if (scope.getScopeName().length() == 0)
            scope.setScopeName(comp.getDisplayName(null));
        }
      }
      catch (Exception ignore)
      {
      }
    }
  }

  /**
   * Action: getSearchScopes
   *
   * Get the default search scopes as a vector of BSearchScope.
   * The vector is constructed by including each element of the
   * defaultScopes property that is readable in the current context.
   *
   * The action is used by search clients to provide a list of
   * scopes for users to choose from.
   *
   * @param cx The context that the action is executing in.
   * @return Returns a BVector of BSearchScope objects.
   */
  public BVector doGetSearchScopes(Context cx)
  {
    if (!isOperational())
      throw new NotRunningException("The SearchService is not operational");

    // fail with no user
    if ((cx == null) || (cx.getUser() == null))
      return new BVector();

    // build a list of BSearchScope
    ArrayList<BSearchScope> temp = new ArrayList<>();

    // first add the defaults
    BVector defScopes = getDefaultScopes();
    SlotCursor<Property> c = defScopes.getProperties();
    while (c.next())
    {
      BObject obj = c.get();
      if (obj instanceof BSearchScope)
      {
        BSearchScope defScope = (BSearchScope)obj;
        BOrd scopeOrd = defScope.getScopeOrd();
        OrdTarget scopeTarget;
        try
        {
          scopeTarget = scopeOrd.resolve(this, cx);
        }
        catch(Exception ex)
        {
          continue;
        }

        if (scopeTarget.canRead() && validSchemeForScope(null, scopeTarget))
        {
          BObject scopeObj = scopeTarget.get();
          // update scope info if it is blank
          if (scopeObj instanceof BISpace)
          {
            BISpace scopeSpace = (BISpace)scopeObj;
            if (defScope.getScopeName().length() == 0)
              defScope.setScopeName(scopeSpace.getLexiconText().getText(null));
            if (defScope.getScopeLexiconModule().length() == 0)
              defScope.setScopeLexiconModule(scopeSpace.getLexiconText().module.getModuleName());
            if (defScope.getScopeLexiconKey().length() == 0)
              defScope.setScopeLexiconKey(scopeSpace.getLexiconText().key);
          }

          defScope = (BSearchScope)obj.asComplex().newCopy(true);
          if (scopeObj instanceof BISpace)
            defScope.setScopeName(scopeObj.as(BISpace.class).getLexiconText().getText(cx));
          temp.add(defScope);
        }
      }
      else if (obj instanceof BOrd)
      {
        BOrd scopeOrd = (BOrd)obj;
        OrdTarget scopeTarget = scopeOrd.resolve(this, cx);
        if (scopeTarget.canRead() && validSchemeForScope(null, scopeTarget))
        {
          BObject scopeObj = scopeTarget.get();
          String scopeName;
          if (scopeObj instanceof BComplex)
            scopeName = scopeObj.asComplex().getDisplayName(cx);
          else
            scopeName = scopeObj.toString(cx);

          String lexModule = "";
          String lexKey = "";
          if (scopeObj instanceof BISpace)
          {
            BISpace space = (BISpace)scopeObj;
            lexModule = space.getLexiconText().module.getModuleName();
            lexKey = space.getLexiconText().key;
          }

          BSearchScope scope =
            new BSearchScope(scopeName, lexModule, lexKey, scopeOrd, true);
          temp.add(scope);
        }
      }
    }

    BUser user = cx.getUser();

    // now add all of the readable spaces
    Iterator<BISpace> spaces = BLocalHost.INSTANCE.getSpaces();
    while (spaces.hasNext())
    {
      BISpace space = spaces.next();
      if (!(space instanceof BIProtected))
        continue;

      if (!user.getPermissionsFor(space.as(BIProtected.class)).hasOperatorRead())
        continue;

      if (!validSchemeForScope(null, space.getAbsoluteOrd().resolve()))
        continue;

      // make sure this space is not already a default
      boolean found = false;
      for (BSearchScope def : temp)
      {
        if (def.getScopeOrd().equals(space.getOrdInSession()))
        {
          found = true;
          break;
        }
      }

      if (!found)
      {
        BSearchScope spaceScope =
          new BSearchScope(space.getDisplayName(cx),
            space.getLexiconText().module.getModuleName(),
            space.getLexiconText().key,
            space.getOrdInSession(),
            false);
        temp.add(spaceScope);
      }
    }

    // copy into a BVector and we're done
    BVector result = new BVector();
    Iterator<BSearchScope> i = temp.iterator();
    while (i.hasNext())
      result.add("scope" + result.getPropertyCount(), i.next());
    return result;
  }

////////////////////////////////////////////////////////////////
// Thread pool
////////////////////////////////////////////////////////////////

  /**
   * Get the default search executor.
   */
  public Executor getExecutor()
  {
    return executor;
  }

  /**
   * Handle ForkJoinPool uncaught exceptions
   */
  static class UncaughtSearchExceptionHandler implements Thread.UncaughtExceptionHandler
  {

    /**
     * Uncaught exception handler for thread pool
     *
     * @param t thread
     * @param e Throwable
     */
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
      if (logger.isLoggable(Level.SEVERE))
      {
        logger.log(Level.SEVERE, "Uncaught exception from thread " + t +"\n" + e);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  /**
   * The implementation of submitting a search task, do not use directly.
   */
  BOrd submitSearchTask(BSearchTask task, Context cx)
  {
    checkConcurrentSearchLimit(/*reduceByOne*/true);

    // Only add the search task with the operator flag if the user in the context
    // doesn't have admin read permission on the SearchService.  This prevents
    // operator users from seeing search tasks submitted by other admin users.
    int flags = Flags.TRANSIENT;
    if (!getPermissions(cx).hasAdminRead())
      flags |= Flags.OPERATOR;

    // Use a null context so that it doesn't create an audit log entry.
    getActiveSearchContainer().add(task.getType().getTypeName() + '?', task, flags, null);
    task.doSubmit(cx);
    return task.getSlotPathOrd();
  }

  private void checkConcurrentSearchLimit(boolean reduceByOne)
  { // Enforce concurrent search limit
    int maxSearches = getMaxConcurrentSearches();
    if (maxSearches < 1)
      maxSearches = 1;
    if (reduceByOne) // Indicates we need to make room for a new search that will be added soon
      maxSearches--;
    BSearchTask[] tasks = getActiveSearchContainer().getChildren(BSearchTask.class);
    while (maxSearches < tasks.length)
    {
      // Find the index of the search task that will expire next, and remove it first
      int index = -1;
      long millisToExpiration = Long.MAX_VALUE;
      int len = tasks.length;
      for (int i = 0; i < len; i++)
      {
        // Don't bother with subscribed searches, we can't touch them because they are in use
        if (tasks[i].isSubscribed()) continue;

        if (tasks[i].expirationTicket instanceof NClockTicket)
        {
          long millisLeft = ((NClockTicket)(tasks[i].expirationTicket)).millisLeft();
          if (millisLeft < millisToExpiration)
          {
            index = i;
            millisToExpiration = millisLeft;
          }
        }
        else if (index == -1)
          index = i;
      }

      if (index == -1)
      { // Couldn't find any old searches eligible for removal, so throw an exception
        throw new LocalizableRuntimeException("search", "search.error.tooManySearches");
      }
      else
      { // Found an old task that we can force to expire
        tasks[index].doExpire(null);
      }

      tasks = getActiveSearchContainer().getChildren(BSearchTask.class);

      // This shouldn't ever happen, but just in case we couldn't remove an old
      // search task, check to see if the number of tasks hasn't changed and throw
      // an exception so we don't get in an infinite loop scenario.
      if (len == tasks.length)
        throw new LocalizableRuntimeException("search", "search.error.tooManySearches");
    }
  }

  static boolean validSchemeForScope(BOrdScheme ordScheme, OrdTarget scope)
  {
    if (!allowLocalSearch && isLocalScope(scope))
      return false; // Unlicensed for search against local scope

    if (!allowSystemSearch && isSystemScope(scope))
      return false; // Unlicensed for search against system scope

    if (ordScheme instanceof BQueryScheme)
      return BQueryScheme.findQueryHandler(scope, (BQueryScheme)ordScheme) != null;
    else if (ordScheme != null)
      return findSearchProvider(scope.get(), ordScheme) != null;

    // Lookup all available query schemes and find the first matching query handler for
    // the given scope
    TypeInfo[] querySchemeTypes = Sys.getRegistry().getConcreteTypes(BQueryScheme.TYPE.getTypeInfo());
    for (TypeInfo querySchemeType : querySchemeTypes)
    {
      BIQueryHandler queryHandler = BQueryScheme.findQueryHandler(scope, (BQueryScheme)querySchemeType.getInstance());
      if (queryHandler != null)
        return true;
    }

    TypeInfo[] ordSchemeTypes = Sys.getRegistry().getConcreteTypes(BOrdScheme.TYPE.getTypeInfo());
    for (TypeInfo ordSchemeType : ordSchemeTypes)
    {
      if (ordSchemeType.is(BQueryScheme.TYPE))
      {
        BIQueryHandler queryHandler = BQueryScheme.findQueryHandler(scope, (BQueryScheme)ordSchemeType.getInstance());
        if (queryHandler != null)
          return true;
      }
      else
      {
        BISearchProvider provider = findSearchProvider(scope.get(), (BOrdScheme)ordSchemeType.getInstance());
        if (provider != null)
          return true;
      }
    }

    return false;
  }

  /**
   * Find a search provider for the specified scope and scheme, or null if one can't be found.
   *
   * @param scope The collection of objects that the query will be evaluated against.
   * @param ordScheme The scheme for the query to be evaluated.
   * @return Returns a BISearchProvider or null if no provider can be found.
   */
  static BISearchProvider findSearchProvider(BObject scope, BOrdScheme ordScheme)
  {
    BISearchProvider handler = null;

    // find the BISearchProviders registered on the target
    AgentList targetAgents =
      scope.getAgents().filter(AgentFilter.is(BISearchProvider.TYPE));

    // find the BISearchProviders registered on the scheme
    AgentList schemeAgents =
      ordScheme.getAgents().filter(AgentFilter.is(BISearchProvider.TYPE));

    // find the first common BISearchProvider between the target and the scheme
    int taCount = targetAgents.size();
    for (int i = 0; i < taCount; i++)
    {
      AgentInfo agent = targetAgents.get(i);
      if (schemeAgents.indexOf(agent) != -1)
      {
        handler = (BISearchProvider)agent.getInstance();
        break;
      }
    }

    return handler;
  }

  static BVector makeDefaultSearchScopes()
  {
    BVector defaults = new BVector();
    defaults.add("scope?", new BSearchScope("", "", "", BOrd.make("station:"), true));
    defaults.add("scope?", new BSearchScope("", "", "", SYS_ORD, true));
    return defaults;
  }

  static boolean isLocalScope(OrdTarget scope)
  {
    // TODO: Once we have the system db added, we need to fix this method and the system scope
    // check below
    return true;
  }

  static boolean isSystemScope(OrdTarget scope)
  {
    // TODO: Once we have the system db added, we need to fix this method and the local scope
    // check above
    return false;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps("SearchService");
    out.prop("Licensed for local searches", allowLocalSearch);
    out.prop("Licensed for system searches", allowSystemSearch);
    out.endProps();

    if (executor != null)
    {
      out.w("<p>");
      out.startTable(true);
      out.trTitle("Thread Pool", 7);
      out.w("<tr>").th("Current Pool Size").th("Max Pool Size").th("Active").th("Running")
        .th("Submitted").th("Queued").th("Steals")
        .w("</tr>").nl();
      out.tr(executor.getPoolSize(),
        executor.getParallelism(),
        executor.getActiveThreadCount(),
        executor.getRunningThreadCount(),
        executor.getQueuedSubmissionCount(),
        executor.getQueuedTaskCount(),
        executor.getStealCount());
      out.endTable();
    }

    BSearchTask[] tasks = getActiveSearchContainer().getChildren(BSearchTask.class);
    out.startTable(true);
    out.trTitle("SearchTasks", 11);
    out.w("<tr>").th("Name").th("State").th("Progress")
      .th("Start").th("Heartbeat").th("End").th("User").th("ResultCount").th("ResultsExceedLimit")
      .th("ExpirationTicket").th("SearchParams").w("</tr>").nl();
    for (BSearchTask j : tasks)
    {
      out.w("<tr>").td(j.getName()).td(j.getJobState()).td(String.valueOf(j.getProgress()))
        .td(j.getStartTime()).td(j.getHeartbeatTime()).td(j.getEndTime());

      if (j.searchContext != null)
        out.td(j.searchContext.getUser());
      else
        out.td("null context");

      out.td(j.getResultCount()).td(j.getResultsExceedLimit()).td(j.expirationTicket)
        .td(j.searchParams).w("</tr>");
    }

    out.endTable();

    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/searchService.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Logger logger = Logger.getLogger("search");
  protected ForkJoinPool executor;

  private static boolean allowLocalSearch;
  private static boolean allowSystemSearch;

}
