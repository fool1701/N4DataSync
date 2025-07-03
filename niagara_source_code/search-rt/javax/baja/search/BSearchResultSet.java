/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.search;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIProtected;
import javax.baja.space.BISpaceNode;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BVector;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.virtual.BVirtualComponent;

/**
 * Result set for searches.
 *
 * @author Dan Heine
 * @author Scott Hoye
 * @creation 2013-12-12
 * @since Niagara 4.0
 */
@NiagaraType
/*
 Starting index of the search results contained in this result set.
 This is useful when retrieving a subset of search results from a
 main set of search results.
 */
@NiagaraProperty(
  name = "startIndex",
  type = "int",
  defaultValue = "0",
  flags = Flags.OPERATOR,
  facets = @Facet(name = "BFacets.MIN", value = "0")
)
/*
 Maximum number of search results that are allowed in this result set.
 This limit can be used in conjunction with the startIndex property to
 define a subset of search results to retrieve from a main set of
 search results. A value of -1 means no limit.
 */
@NiagaraProperty(
  name = "maxResults",
  type = "int",
  defaultValue = "-1",
  flags = Flags.OPERATOR,
  facets = @Facet(name = "BFacets.MIN", value = "-1")
)
/*
 Number of search results contained in this result set (ie. the number of
 BSearchResult children under the results child component).  Add this number to
 startIndex when creating a request for the next block of results.
 */
@NiagaraProperty(
  name = "resultCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.OPERATOR
)
/*
 True if there are no more search results available from
 the search source.  If this result set instance was obtained
 as a subset of a main search result set instance, this
 property will only be set to true if this subset contains
 the last search result available from the main set.
 If this instance is the main result set itself, this property
 will be set to true when no more search results are asynchronously
 being retrieved, or when the max limit has been reached.
 */
@NiagaraProperty(
  name = "resultsComplete",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY | Flags.OPERATOR
)
/*
 If the number of search results attempted to be added to this result
 set exceeded the max size, then this property will be set to true
 indicating that the search results were limited to the maxResults size.
 */
@NiagaraProperty(
  name = "resultsExceedLimit",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY | Flags.OPERATOR
)
/*
 A vector that contains the set of search results as dynamic slots.
 */
@NiagaraProperty(
  name = "results",
  type = "BVector",
  defaultValue = "new BVector()",
  flags = Flags.READONLY | Flags.OPERATOR
)
public class BSearchResultSet extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BSearchResultSet(3434412620)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "startIndex"

  /**
   * Slot for the {@code startIndex} property.
   * Starting index of the search results contained in this result set.
   * This is useful when retrieving a subset of search results from a
   * main set of search results.
   * @see #getStartIndex
   * @see #setStartIndex
   */
  public static final Property startIndex = newProperty(Flags.OPERATOR, 0, BFacets.make(BFacets.MIN, 0));

  /**
   * Get the {@code startIndex} property.
   * Starting index of the search results contained in this result set.
   * This is useful when retrieving a subset of search results from a
   * main set of search results.
   * @see #startIndex
   */
  public int getStartIndex() { return getInt(startIndex); }

  /**
   * Set the {@code startIndex} property.
   * Starting index of the search results contained in this result set.
   * This is useful when retrieving a subset of search results from a
   * main set of search results.
   * @see #startIndex
   */
  public void setStartIndex(int v) { setInt(startIndex, v, null); }

  //endregion Property "startIndex"

  //region Property "maxResults"

  /**
   * Slot for the {@code maxResults} property.
   * Maximum number of search results that are allowed in this result set.
   * This limit can be used in conjunction with the startIndex property to
   * define a subset of search results to retrieve from a main set of
   * search results. A value of -1 means no limit.
   * @see #getMaxResults
   * @see #setMaxResults
   */
  public static final Property maxResults = newProperty(Flags.OPERATOR, -1, BFacets.make(BFacets.MIN, -1));

  /**
   * Get the {@code maxResults} property.
   * Maximum number of search results that are allowed in this result set.
   * This limit can be used in conjunction with the startIndex property to
   * define a subset of search results to retrieve from a main set of
   * search results. A value of -1 means no limit.
   * @see #maxResults
   */
  public int getMaxResults() { return getInt(maxResults); }

  /**
   * Set the {@code maxResults} property.
   * Maximum number of search results that are allowed in this result set.
   * This limit can be used in conjunction with the startIndex property to
   * define a subset of search results to retrieve from a main set of
   * search results. A value of -1 means no limit.
   * @see #maxResults
   */
  public void setMaxResults(int v) { setInt(maxResults, v, null); }

  //endregion Property "maxResults"

  //region Property "resultCount"

  /**
   * Slot for the {@code resultCount} property.
   * Number of search results contained in this result set (ie. the number of
   * BSearchResult children under the results child component).  Add this number to
   * startIndex when creating a request for the next block of results.
   * @see #getResultCount
   * @see #setResultCount
   */
  public static final Property resultCount = newProperty(Flags.READONLY | Flags.OPERATOR, 0, null);

  /**
   * Get the {@code resultCount} property.
   * Number of search results contained in this result set (ie. the number of
   * BSearchResult children under the results child component).  Add this number to
   * startIndex when creating a request for the next block of results.
   * @see #resultCount
   */
  public int getResultCount() { return getInt(resultCount); }

  /**
   * Set the {@code resultCount} property.
   * Number of search results contained in this result set (ie. the number of
   * BSearchResult children under the results child component).  Add this number to
   * startIndex when creating a request for the next block of results.
   * @see #resultCount
   */
  public void setResultCount(int v) { setInt(resultCount, v, null); }

  //endregion Property "resultCount"

  //region Property "resultsComplete"

  /**
   * Slot for the {@code resultsComplete} property.
   * True if there are no more search results available from
   * the search source.  If this result set instance was obtained
   * as a subset of a main search result set instance, this
   * property will only be set to true if this subset contains
   * the last search result available from the main set.
   * If this instance is the main result set itself, this property
   * will be set to true when no more search results are asynchronously
   * being retrieved, or when the max limit has been reached.
   * @see #getResultsComplete
   * @see #setResultsComplete
   */
  public static final Property resultsComplete = newProperty(Flags.READONLY | Flags.OPERATOR, false, null);

  /**
   * Get the {@code resultsComplete} property.
   * True if there are no more search results available from
   * the search source.  If this result set instance was obtained
   * as a subset of a main search result set instance, this
   * property will only be set to true if this subset contains
   * the last search result available from the main set.
   * If this instance is the main result set itself, this property
   * will be set to true when no more search results are asynchronously
   * being retrieved, or when the max limit has been reached.
   * @see #resultsComplete
   */
  public boolean getResultsComplete() { return getBoolean(resultsComplete); }

  /**
   * Set the {@code resultsComplete} property.
   * True if there are no more search results available from
   * the search source.  If this result set instance was obtained
   * as a subset of a main search result set instance, this
   * property will only be set to true if this subset contains
   * the last search result available from the main set.
   * If this instance is the main result set itself, this property
   * will be set to true when no more search results are asynchronously
   * being retrieved, or when the max limit has been reached.
   * @see #resultsComplete
   */
  public void setResultsComplete(boolean v) { setBoolean(resultsComplete, v, null); }

  //endregion Property "resultsComplete"

  //region Property "resultsExceedLimit"

  /**
   * Slot for the {@code resultsExceedLimit} property.
   * If the number of search results attempted to be added to this result
   * set exceeded the max size, then this property will be set to true
   * indicating that the search results were limited to the maxResults size.
   * @see #getResultsExceedLimit
   * @see #setResultsExceedLimit
   */
  public static final Property resultsExceedLimit = newProperty(Flags.READONLY | Flags.OPERATOR, false, null);

  /**
   * Get the {@code resultsExceedLimit} property.
   * If the number of search results attempted to be added to this result
   * set exceeded the max size, then this property will be set to true
   * indicating that the search results were limited to the maxResults size.
   * @see #resultsExceedLimit
   */
  public boolean getResultsExceedLimit() { return getBoolean(resultsExceedLimit); }

  /**
   * Set the {@code resultsExceedLimit} property.
   * If the number of search results attempted to be added to this result
   * set exceeded the max size, then this property will be set to true
   * indicating that the search results were limited to the maxResults size.
   * @see #resultsExceedLimit
   */
  public void setResultsExceedLimit(boolean v) { setBoolean(resultsExceedLimit, v, null); }

  //endregion Property "resultsExceedLimit"

  //region Property "results"

  /**
   * Slot for the {@code results} property.
   * A vector that contains the set of search results as dynamic slots.
   * @see #getResults
   * @see #setResults
   */
  public static final Property results = newProperty(Flags.READONLY | Flags.OPERATOR, new BVector(), null);

  /**
   * Get the {@code results} property.
   * A vector that contains the set of search results as dynamic slots.
   * @see #results
   */
  public BVector getResults() { return (BVector)get(results); }

  /**
   * Set the {@code results} property.
   * A vector that contains the set of search results as dynamic slots.
   * @see #results
   */
  public void setResults(BVector v) { set(results, v, null); }

  //endregion Property "results"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSearchResultSet.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BSearchResultSet()
  {
  }

  /**
   * Constructor that initializes startIndex and maxResults
   *
   * @param startIndex the first search result index
   * @param maxResults max number of results allowed in this set
   */
  protected BSearchResultSet(int startIndex, int maxResults)
  {
    setStartIndex(startIndex);
    setMaxResults(maxResults);
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get a subset of the total results from the passed-in list.
   *
   * @param set A SearchResultSet instance from which to copy results into the new (returned)
   *            instance starting at startIndex and copying up to maxResults search results.
   * @param startIndex the first search result index
   * @param maxResults max number of results to add to this set from the given set
   * @param cx The context to use to check permissions on results added to the new set.
   * @return The subset of search results as a new BSearchResultSet instance.
   */
  public static final BSearchResultSet make(BSearchResultSet set, int startIndex, int maxResults, Context cx)
  {
    BSearchResultSet results = new BSearchResultSet(startIndex, maxResults);
    int lastIndex = results.copyResults(set, cx);
    int origCount = set.getResultCount();
    results.setResultsComplete(set.getResultsComplete() && (lastIndex == origCount));
    results.setResultsExceedLimit((lastIndex < origCount) ||
      ((lastIndex == origCount) && set.getResultsExceedLimit()));
    return results;
  }

////////////////////////////////////////////////////////////////
// Operations
////////////////////////////////////////////////////////////////

  /**
   * Returns a Stream of Entities that corresponds to the Search Results
   * that have been added to this result set.
   *
   * @return a Stream of Entities
   */
  public Stream<Entity> streamResults()
  {
    return Arrays.stream(getResults().getDynamicPropertiesArray())
      .map(prop -> (Entity)get(prop));
  }

  /**
   * Given a Stream of Entities, add them as Search Results to this set up until
   * the maxResults limit has been reached.
   *
   * @param stream Stream of Entities to add to this SearchResultSet as BSearchResults
   */
  public void addResults(Stream<Entity> stream)
  {
    addResults(stream, null);
  }

  /**
   * Given a Stream of Entities, add them as Search Results to this set up until
   * the maxResults limit has been reached.
   *
   * @param stream Stream of Entities to add to this SearchResultSet as BSearchResults
   * @param cx The Context which can be used for filtering the results to add by performing
   *           additional permission checks if it is known that the query itself didn't
   *           already perform permission checking. When null, no additional permission
   *           checks will occur on the results to add.
   *
   * @since Niagara 4.3
   */
  public void addResults(Stream<Entity> stream, Context cx)
  {
    synchronized(lock)
    {
      try
      {
        stream.forEach(e -> {
          addResult(e, cx);
        });
      }
      catch(IllegalStateException ise)
      {
        setResultsExceedLimit(true);
      }

      setResultCount(getResults().getPropertyCount());
    }
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  /**
   * Add the given Entity to this set of search results by converting it to
   * a BSearchResult.
   *
   * @param entity Entity to add to this SearchResultSet as a BSearchResult
   * @return the BSearchResult that was added to the set for the given Entity
   * @throws IllegalStateException if this result set has already reached its
   * max size limit and can no longer accept any more results to be added.
   */
  private BSearchResult addResult(Entity entity, Context cx)
    throws IllegalStateException
  {
    BComponent items = getResults();
    int itemCount = items.getPropertyCount();
    int maxItems = getMaxResults();
    if ((maxItems >= 0) && (itemCount >= maxItems))
      throw new IllegalStateException("Cannot add result to SearchResultSet: Max Size Reached");

    // TODO: Add IHasEpoch interface
    BSimple epoch = /*entity instanceof IHasEpoch ? ((IHasEpoch)entity).getEpoch() :*/ BAbsTime.now();
    BSearchResult result = null;
    if (entity instanceof BSearchResult)
    {
      result = (BSearchResult)entity;
      if (result.getPropertyInParent() != null)
        result = copyTaskResult(result);
    }
    else
    {
      BOrd ord = entity.getOrdToEntity().orElse(BOrd.DEFAULT).relativizeToSession();
      if (entity instanceof BVirtualComponent)
      {
        // TODO: For now, I'm just checking permissions for the virtual case due to how
        // we handle remote queries.  In the future when we have the system database working,
        // we may need to revisit this permission check (if the system database queries don't
        // handle the permission checks for us) unless the system database only returns virtual
        // components as the Entities from a query result (but I expect it could also return
        // local components as the results).
        BVirtualComponent virtual = (BVirtualComponent)entity;
        if (!virtual.getPermissions(cx).hasOperatorRead())
          return result; // skip it if not enough permissions to see it

        ord = virtual.getNavOrd().relativizeToSession();
      }
      else if (entity instanceof BISpaceNode)
        ord = ((BISpaceNode)entity).getOrdInSession();

      if (entity instanceof BIObject)
      {
        result = BSearchResult.make(ord, (BIObject)entity, epoch);
      }
      else
      {
        result = BSearchResult.make(ord, null, epoch);
        result.applyTags(entity, cx);
      }
    }

    if (result != null)
    {
      items.add("result0?", result, Flags.TRANSIENT | Flags.OPERATOR); // LITOK
    }

    return result;
  }

  /**
   * Return a copy of the task result that can be added to the result set.
   * This override copies the object reference if it exists in order to
   * avoid a redundant resolve of the ord.
   *
   * @param taskResult result object from task results
   * @return object to add to task result set
   */
  private BSearchResult copyTaskResult(BSearchResult taskResult)
  {
    BSearchResult newObj = (BSearchResult)taskResult.newCopy();
    if (taskResult.hasObject())
    {
      newObj.setObject(taskResult.getObject());
    }
    return newObj;
  }

  private int copyResults(BSearchResultSet set, Context cx)
  {
    BComponent myItems = getResults(); // It is assumed that there are no properties on myItems!
    BComponent sourceItems = set.getResults();

    boolean checkPermissions = (cx != null);
    int index = getStartIndex();
    int maxItems = getMaxResults();
    int propertyCount = sourceItems.getPropertyCount();
    int endIndex = propertyCount;
    if ((maxItems >= 0) && (maxItems != Integer.MAX_VALUE))
    {
      endIndex = index + maxItems;
      if (endIndex > propertyCount)
        endIndex = propertyCount;
    }

    int newIndex = 0;
    StringBuilder sb = new StringBuilder("result");
    while (index < endIndex)
    {
      try
      {
        String name = sb.append(index).toString();
        sb.setLength(6); // reset StringBuilder to "result"
        String newName = sb.append(newIndex).toString();
        BSearchResult result = (BSearchResult)sourceItems.get(name);

        BIObject obj = null;
        try
        {
          obj = result.getObject();
        }
        catch(Exception ignore) { }

        if (obj != null)
        {
          if (checkPermissions && (obj instanceof BIProtected))
          {
            if (((BIProtected)obj).getPermissions(cx).hasOperatorRead())
            {
              myItems.add(newName, copyTaskResult(result), Flags.TRANSIENT | Flags.OPERATOR);
              newIndex++;
            }
            else if (endIndex < propertyCount)
              endIndex++; // Since we're skipping one, we have room to expand the end index
          }
          else
          {
            myItems.add(newName, copyTaskResult(result), Flags.TRANSIENT | Flags.OPERATOR);
            newIndex++;
          }
        }
        else
        { // If we can't resolve the SearchResult's object, then just use the BSearchResult as is.
          // This could happen if the search is against the System Database and the result is an
          // uncached Niagara virtual for a BNiagaraStation that is currently {down}. Since
          // permissions are checked in the query itself, we shouldn't need to worry about that part
          // here.
          myItems.add(newName, result.newCopy(), Flags.TRANSIENT | Flags.OPERATOR);
          newIndex++;
        }
      }
      catch(Exception e)
      {
        BSearchService.logger.fine("Could not copy search result. " + e);
      }
      index++;
      sb.setLength(6); // reset StringBuilder to "result"
    }

    setResultCount(myItems.getPropertyCount());
    return endIndex;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Object lock = new Object();

}
