/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.search;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A request for search results from a search task.
 *
 * @author Dan Heine
 * @author Scott Hoye
 * @creation 2013-09-27
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The ORD to the search task from which to retrieve search results.
 */
@NiagaraProperty(
  name = "taskOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.OPERATOR
)
/*
 The index of the starting search result to retrieve from
 the search task. 0 is the starting index.
 */
@NiagaraProperty(
  name = "startIndex",
  type = "int",
  defaultValue = "0",
  flags = Flags.OPERATOR,
  facets = @Facet(name = "BFacets.MIN", value = "0")
)
/*
 The maximum number of search results to retrieve from
 the search task. This limit can be used in conjunction
 with the startIndex property to define a subset of
 search results to retrieve from a main set of
 search results on the search task. A value of -1 means no limit.
 */
@NiagaraProperty(
  name = "maxResults",
  type = "int",
  defaultValue = "-1",
  flags = Flags.OPERATOR,
  facets = @Facet(name = "BFacets.MIN", value = "-1")
)
public class BResultsRequest
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BResultsRequest(2117631211)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "taskOrd"

  /**
   * Slot for the {@code taskOrd} property.
   * The ORD to the search task from which to retrieve search results.
   * @see #getTaskOrd
   * @see #setTaskOrd
   */
  public static final Property taskOrd = newProperty(Flags.OPERATOR, BOrd.NULL, null);

  /**
   * Get the {@code taskOrd} property.
   * The ORD to the search task from which to retrieve search results.
   * @see #taskOrd
   */
  public BOrd getTaskOrd() { return (BOrd)get(taskOrd); }

  /**
   * Set the {@code taskOrd} property.
   * The ORD to the search task from which to retrieve search results.
   * @see #taskOrd
   */
  public void setTaskOrd(BOrd v) { set(taskOrd, v, null); }

  //endregion Property "taskOrd"

  //region Property "startIndex"

  /**
   * Slot for the {@code startIndex} property.
   * The index of the starting search result to retrieve from
   * the search task. 0 is the starting index.
   * @see #getStartIndex
   * @see #setStartIndex
   */
  public static final Property startIndex = newProperty(Flags.OPERATOR, 0, BFacets.make(BFacets.MIN, 0));

  /**
   * Get the {@code startIndex} property.
   * The index of the starting search result to retrieve from
   * the search task. 0 is the starting index.
   * @see #startIndex
   */
  public int getStartIndex() { return getInt(startIndex); }

  /**
   * Set the {@code startIndex} property.
   * The index of the starting search result to retrieve from
   * the search task. 0 is the starting index.
   * @see #startIndex
   */
  public void setStartIndex(int v) { setInt(startIndex, v, null); }

  //endregion Property "startIndex"

  //region Property "maxResults"

  /**
   * Slot for the {@code maxResults} property.
   * The maximum number of search results to retrieve from
   * the search task. This limit can be used in conjunction
   * with the startIndex property to define a subset of
   * search results to retrieve from a main set of
   * search results on the search task. A value of -1 means no limit.
   * @see #getMaxResults
   * @see #setMaxResults
   */
  public static final Property maxResults = newProperty(Flags.OPERATOR, -1, BFacets.make(BFacets.MIN, -1));

  /**
   * Get the {@code maxResults} property.
   * The maximum number of search results to retrieve from
   * the search task. This limit can be used in conjunction
   * with the startIndex property to define a subset of
   * search results to retrieve from a main set of
   * search results on the search task. A value of -1 means no limit.
   * @see #maxResults
   */
  public int getMaxResults() { return getInt(maxResults); }

  /**
   * Set the {@code maxResults} property.
   * The maximum number of search results to retrieve from
   * the search task. This limit can be used in conjunction
   * with the startIndex property to define a subset of
   * search results to retrieve from a main set of
   * search results on the search task. A value of -1 means no limit.
   * @see #maxResults
   */
  public void setMaxResults(int v) { setInt(maxResults, v, null); }

  //endregion Property "maxResults"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BResultsRequest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * For internal use only.  Call make(...) methods to create a new request.
   */
  public BResultsRequest()
  {
    this(BOrd.NULL, DEFAULT_START_INDEX, DEFAULT_MAX_RESULTS);
  }

  /**
   * Call make(...) methods to create a new request.
   *
   * @param taskOrd The ORD to the search task from which to retrieve search results
   * @param startIndex The index of the starting search result to retrieve from
   * the search task. 0 is the starting index.
   * @param maxResults The maximum number of search results to retrieve from
   * the search task starting from the startIndex. A value of -1 means to retrieve
   * all available search results starting from the startIndex.
   */
  private BResultsRequest(BOrd taskOrd, int startIndex, int maxResults)
  {
    setTaskOrd(taskOrd);
    setStartIndex(startIndex);
    setMaxResults(maxResults);
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Create a BResultsRequest instance which can be used to provide parameters
   * for retrieving search results from a search task.
   *
   * @param taskOrd The ORD to the search task from which to retrieve search results
   * @param startIndex The index of the starting search result to retrieve from
   * the search task. 0 is the starting index.
   * @param maxResults The maximum number of search results to retrieve from
   * the search task starting from the startIndex. A value of -1 means to retrieve
   * all available search results starting from the startIndex.
   * @return a BResultsRequest instance containing the giving parameters
   */
  public static BResultsRequest make(BOrd taskOrd, int startIndex, int maxResults)
  {
    return new BResultsRequest(taskOrd, startIndex, maxResults);
  }

  /**
   * Create a BResultsRequest instance which can be used to provide parameters
   * for retrieving search results from a search task.  It will be set up to
   * retrieve all available search results.
   *
   * @param taskOrd The ORD to the search task from which to retrieve search results
   * @return a BResultsRequest instance containing the giving parameters
   */
  public static BResultsRequest make(BOrd taskOrd)
  {
    return make(taskOrd, DEFAULT_START_INDEX, DEFAULT_MAX_RESULTS);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BResultsRequest DEFAULT = new BResultsRequest();

  private static final int DEFAULT_START_INDEX = 0; // 0 is the first search result
  private static final int DEFAULT_MAX_RESULTS = -1; // (maxResults < 0) means retrieve all results
}
