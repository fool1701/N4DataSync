/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import static com.tridium.hierarchy.HierarchyUtil.createQueryContext;
import static com.tridium.hierarchy.HierarchyUtil.getExcludeEmptyGroupsQuery;
import static com.tridium.hierarchy.HierarchyUtil.getGroupingBase;
import static com.tridium.hierarchy.HierarchyUtil.getUser;
import static com.tridium.hierarchy.MakeElemUtil.getGroupIcon;
import static com.tridium.hierarchy.MakeElemUtil.getGroupName;
import static com.tridium.hierarchy.MakeElemUtil.makeGroupElem;
import static com.tridium.hierarchy.QueryUtil.resolveQueryOnScopes;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.user.BUser;
import javax.baja.util.CloseableIterator;

/**
 * A GroupLevelDef creates groups based on distinct tag values.
 *
 * @author    Blake Puhak
 * @creation  4 Mar 2014
 * @since     Niagara 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "groupBy",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "includeEmptyGroups",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "sort",
  type = "BLevelSort",
  defaultValue = "BLevelSort.ascending"
)
@NiagaraProperty(
  name = "tags",
  type = "BHierarchyTags",
  defaultValue = "new BHierarchyTags()"
)
public class BGroupLevelDef
  extends BLevelDef
  implements BIGroupingLevelDef
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BGroupLevelDef(1173550398)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "groupBy"

  /**
   * Slot for the {@code groupBy} property.
   * @see #getGroupBy
   * @see #setGroupBy
   */
  public static final Property groupBy = newProperty(0, "", null);

  /**
   * Get the {@code groupBy} property.
   * @see #groupBy
   */
  public String getGroupBy() { return getString(groupBy); }

  /**
   * Set the {@code groupBy} property.
   * @see #groupBy
   */
  public void setGroupBy(String v) { setString(groupBy, v, null); }

  //endregion Property "groupBy"

  //region Property "includeEmptyGroups"

  /**
   * Slot for the {@code includeEmptyGroups} property.
   * @see #getIncludeEmptyGroups
   * @see #setIncludeEmptyGroups
   */
  public static final Property includeEmptyGroups = newProperty(0, false, null);

  /**
   * Get the {@code includeEmptyGroups} property.
   * @see #includeEmptyGroups
   */
  public boolean getIncludeEmptyGroups() { return getBoolean(includeEmptyGroups); }

  /**
   * Set the {@code includeEmptyGroups} property.
   * @see #includeEmptyGroups
   */
  public void setIncludeEmptyGroups(boolean v) { setBoolean(includeEmptyGroups, v, null); }

  //endregion Property "includeEmptyGroups"

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

  //region Property "tags"

  /**
   * Slot for the {@code tags} property.
   * @see #getTags
   * @see #setTags
   */
  public static final Property tags = newProperty(0, new BHierarchyTags(), null);

  /**
   * Get the {@code tags} property.
   * @see #tags
   */
  public BHierarchyTags getTags() { return (BHierarchyTags)get(tags); }

  /**
   * Set the {@code tags} property.
   * @see #tags
   */
  public void setTags(BHierarchyTags v) { set(tags, v, null); }

  //endregion Property "tags"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BGroupLevelDef.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final Logger LOGGER = Logger.getLogger("hierarchy");

  @SuppressWarnings("try")
  @Override
  public BLevelElem[] getElements(BLevelElem parent, Context cx)
  {
    try
    {
      // form the neql query
      // neql:${groupingBase}(${groupBy})${excludeEmptyGroupsQuery}
      String groupingBase = getGroupingBase(this, true, parent);
      String groupByStr = getGroupBy().trim();
      String neql = "neql:" + groupingBase + '(' + groupByStr + ')';

      if (!getIncludeEmptyGroups())
      {
        Optional<String> excludeEmptiesQuery = getExcludeEmptyGroupsQuery(this);
        if (excludeEmptiesQuery.isPresent())
        {
          neql += excludeEmptiesQuery.get();
        }
        else
        {
          LOGGER.warning(() -> "Could not resolve elements for " + getName() +
            " because a subsequent level def is not valid.");
          return EMPTY_LEVEL_ELEMS;
        }
      }

      if (LOGGER.isLoggable(Level.FINE))
      {
        LOGGER.fine("BGroupLevelDef(" + getName() + ").getElements: " + neql);
      }
      BOrd neqlQuery = BOrd.make(neql);
      
      // Distinct tag is added to the context to support system DB queries
      // see OrientGeneratingVisitor#visitSelect
      BUser user = getUser();
      Context queryContext = createQueryContext(user, cx, parent,
        new SimpleEntry<String, BIDataValue>("distinctTag", BString.make(groupByStr)));

      // groupName is the key
      LinkedHashMap<String, BLevelElem> groupElems = new LinkedHashMap<>();

      try (CloseableIterator<Entity> queryResults = resolveQueryOnScopes(this, neqlQuery,
        /*traverseBaseOrd*/ null, getHierarchyService().getHierarchyTimeout(), queryContext))
      {
        BIcon groupIcon = getGroupIcon(this);

        // parse query results looking for distinct groups
        Id groupByTagId = Id.newId(groupByStr);

        while (queryResults.hasNext())
        {
          Entity entity = queryResults.next();

          Optional<BIDataValue> groupTagValue = entity.tags().get(groupByTagId);
          if (groupTagValue.isPresent())
          {
            String groupName = getGroupName(groupTagValue.get(), queryContext);
            groupElems.computeIfAbsent(groupName, k -> makeGroupElem(
              /* levelDef */ this,
              parent,
              groupingBase,
              groupName,
              groupTagValue.get(),
              groupIcon));
          }
        }
      }

      BLevelElem[] elems = groupElems.values().toArray(new BLevelElem[groupElems.size()]);
      sortElems(elems, getSort());
      return elems;
    }
    catch (Exception e)
    {
      LOGGER.log(Level.SEVERE, e, () -> "Could not resolve elements for " + getName());
      return EMPTY_LEVEL_ELEMS;
    }
  }

  @Override
  public String toString(Context cx)
  {
    return "Group Level Def groupBy:  " + getGroupBy();
  }
}
