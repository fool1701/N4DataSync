/*
 * Copyright 2014, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import static com.tridium.hierarchy.HierarchyUtil.createQueryContext;
import static com.tridium.hierarchy.HierarchyUtil.getGroupingBase;
import static com.tridium.hierarchy.HierarchyUtil.getUser;
import static com.tridium.hierarchy.MakeElemUtil.makeEntityElem;
import static com.tridium.hierarchy.QueryUtil.resolveQueryOnScopes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.user.BUser;
import javax.baja.util.CloseableIterator;

/**
 * BQueryLevelDef defines a level in a hierarchy based on neql tags.
 *
 * @author Blake Puhak
 * @creation 4 Mar 2014
 * @since Niagara 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "query",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "includeGroupingQueries",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "sort",
  type = "BLevelSort",
  defaultValue = "BLevelSort.ascending"
)
public class BQueryLevelDef
  extends BLevelDef
  implements BIEntityLevelDef
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BQueryLevelDef(3123466865)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "query"

  /**
   * Slot for the {@code query} property.
   * @see #getQuery
   * @see #setQuery
   */
  public static final Property query = newProperty(0, "", null);

  /**
   * Get the {@code query} property.
   * @see #query
   */
  public String getQuery() { return getString(query); }

  /**
   * Set the {@code query} property.
   * @see #query
   */
  public void setQuery(String v) { setString(query, v, null); }

  //endregion Property "query"

  //region Property "includeGroupingQueries"

  /**
   * Slot for the {@code includeGroupingQueries} property.
   * @see #getIncludeGroupingQueries
   * @see #setIncludeGroupingQueries
   */
  public static final Property includeGroupingQueries = newProperty(0, true, null);

  /**
   * Get the {@code includeGroupingQueries} property.
   * @see #includeGroupingQueries
   */
  public boolean getIncludeGroupingQueries() { return getBoolean(includeGroupingQueries); }

  /**
   * Set the {@code includeGroupingQueries} property.
   * @see #includeGroupingQueries
   */
  public void setIncludeGroupingQueries(boolean v) { setBoolean(includeGroupingQueries, v, null); }

  //endregion Property "includeGroupingQueries"

  //region Property "sort"

  /**
   * Slot for the {@code sort} property.
   * @see #getSort
   * @see #setSort
   */
  public static final Property sort = newProperty(0, BLevelSort.ascending, null);

  /**
   * Get the {@code sort} property.
   * @see #sort
   */
  public BLevelSort getSort() { return (BLevelSort)get(sort); }

  /**
   * Set the {@code sort} property.
   * @see #sort
   */
  public void setSort(BLevelSort v) { set(sort, v, null); }

  //endregion Property "sort"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BQueryLevelDef.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @SuppressWarnings("try")
  @Override
  public BLevelElem[] getElements(BLevelElem parent, Context cx)
  {
    try
    {
      // form the neql query
      String groupingBase = getGroupingBase(this, getIncludeGroupingQueries(), parent);
      String neql = "neql: " + groupingBase + '(' + getQuery() + ')';
      BHierarchyService.log.fine(() -> "BQueryLevelDef(" + getName() + ").getElements: " + neql);

      // Include context parameters from the parent
      BUser user = getUser();
      Context queryContext = createQueryContext(user, cx, parent);
      BHierarchyService.log.fine(() -> "  context=" + queryContext.getFacets());

      List<BLevelElem> levelElems = new ArrayList<>();
      Map<String, BIcon> iconCache = new ConcurrentHashMap<>();

      try (CloseableIterator<Entity> queryResults = resolveQueryOnScopes(this, BOrd.make(neql),
        /*traverseBaseOrd*/ null, getHierarchyService().getHierarchyTimeout(), queryContext))
      {
        while (queryResults.hasNext())
        {
          levelElems.add(makeEntityElem(queryResults.next(), this, parent, iconCache, queryContext));
        }
      }

      BLevelElem[] elems = levelElems.toArray(new BLevelElem[levelElems.size()]);
      sortElems(elems, getSort());
      return elems;
    }
    catch (Exception e)
    {
      BHierarchyService.log.log(Level.SEVERE, e, () -> "Could not resolve elements for " + getName());
    }

    return BLevelDef.EMPTY_LEVEL_ELEMS;
  }

  @Override
  public String toString(Context cx)
  {
    return "Query Level Def: " + getQuery();
  }
}
