/*
 * Copyright 2016, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import static com.tridium.hierarchy.HierarchyUtil.RELATION_ID_SPLIT;
import static com.tridium.hierarchy.HierarchyUtil.createQueryContext;
import static com.tridium.hierarchy.HierarchyUtil.getTraverseBaseOrd;
import static com.tridium.hierarchy.HierarchyUtil.getTraverseNeqlPredicate;
import static com.tridium.hierarchy.HierarchyUtil.getUser;
import static com.tridium.hierarchy.MakeElemUtil.makeEntityElem;
import static com.tridium.hierarchy.QueryUtil.resolveQueryOnScopes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.user.BUser;
import javax.baja.util.CloseableIterator;

/**
 * Defines a level in a hierarchy based on entities that can be traversed by specified relations
 * from entity level elements in the previous hierarchy level.  An entity will only be shown once
 * even if it can be traversed to by more than one of the specified relations.
 *
 * @author Blake Puhak
 * @creation 24 Mar 2014
 * @since Niagara 4.0
 */
@NiagaraType
/*
 IDs of inbound relations to traverse.
 @since Niagara 4.3
 */
@NiagaraProperty(
  name = "inboundRelationIds",
  type = "String",
  defaultValue = ""
)
/*
 IDs of outbound relations to traverse.
 @since Niagara 4.3
 */
@NiagaraProperty(
  name = "outboundRelationIds",
  type = "String",
  defaultValue = ""
)
/*
 NEQL query that must be satisfied by each each entity to which the relations are traversed in
 order for that entity to be included.
 */
@NiagaraProperty(
  name = "filterExpression",
  type = "String",
  defaultValue = ""
)
/*
 If true, as long as the relations exist to be traversed, this level def will be repeatedly
 executed to produce additional child level elems.  Otherwise, if false, the relations will only
 be traversed once.
 */
@NiagaraProperty(
  name = "repeatRelation",
  type = "boolean",
  defaultValue = "false"
)
/*
 The number of times this level def will be repeated, if possible, when the hierarchy is cached.
 This limit does not affect expansion of a non-cached hierarchy.  This property is only relevant
 when the repeatRelation property is true.
 @since Niagara 4.4
 */
@NiagaraProperty(
  name = "cachingRepeatLimit",
  type = "int",
  defaultValue = "5",
  facets = @Facet(name = "BFacets.MIN", value = "1")
)
/*
 The order in which level elems will be displayed.  Default is ascending.
 */
@NiagaraProperty(
  name = "sort",
  type = "BLevelSort",
  defaultValue = "BLevelSort.ascending"
)
/*
 @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
 */
@NiagaraProperty(
  name = "relationId",
  type = "String",
  defaultValue = "",
  flags = Flags.HIDDEN | Flags.READONLY,
  deprecated = true
)
/*
 @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
 */
@NiagaraProperty(
  name = "inbound",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.HIDDEN | Flags.READONLY,
  deprecated = true
)
public class BRelationLevelDef
  extends BLevelDef
  implements BIEntityLevelDef
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BRelationLevelDef(4010037970)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inboundRelationIds"

  /**
   * Slot for the {@code inboundRelationIds} property.
   * IDs of inbound relations to traverse.
   * @since Niagara 4.3
   * @see #getInboundRelationIds
   * @see #setInboundRelationIds
   */
  public static final Property inboundRelationIds = newProperty(0, "", null);

  /**
   * Get the {@code inboundRelationIds} property.
   * IDs of inbound relations to traverse.
   * @since Niagara 4.3
   * @see #inboundRelationIds
   */
  public String getInboundRelationIds() { return getString(inboundRelationIds); }

  /**
   * Set the {@code inboundRelationIds} property.
   * IDs of inbound relations to traverse.
   * @since Niagara 4.3
   * @see #inboundRelationIds
   */
  public void setInboundRelationIds(String v) { setString(inboundRelationIds, v, null); }

  //endregion Property "inboundRelationIds"

  //region Property "outboundRelationIds"

  /**
   * Slot for the {@code outboundRelationIds} property.
   * IDs of outbound relations to traverse.
   * @since Niagara 4.3
   * @see #getOutboundRelationIds
   * @see #setOutboundRelationIds
   */
  public static final Property outboundRelationIds = newProperty(0, "", null);

  /**
   * Get the {@code outboundRelationIds} property.
   * IDs of outbound relations to traverse.
   * @since Niagara 4.3
   * @see #outboundRelationIds
   */
  public String getOutboundRelationIds() { return getString(outboundRelationIds); }

  /**
   * Set the {@code outboundRelationIds} property.
   * IDs of outbound relations to traverse.
   * @since Niagara 4.3
   * @see #outboundRelationIds
   */
  public void setOutboundRelationIds(String v) { setString(outboundRelationIds, v, null); }

  //endregion Property "outboundRelationIds"

  //region Property "filterExpression"

  /**
   * Slot for the {@code filterExpression} property.
   * NEQL query that must be satisfied by each each entity to which the relations are traversed in
   * order for that entity to be included.
   * @see #getFilterExpression
   * @see #setFilterExpression
   */
  public static final Property filterExpression = newProperty(0, "", null);

  /**
   * Get the {@code filterExpression} property.
   * NEQL query that must be satisfied by each each entity to which the relations are traversed in
   * order for that entity to be included.
   * @see #filterExpression
   */
  public String getFilterExpression() { return getString(filterExpression); }

  /**
   * Set the {@code filterExpression} property.
   * NEQL query that must be satisfied by each each entity to which the relations are traversed in
   * order for that entity to be included.
   * @see #filterExpression
   */
  public void setFilterExpression(String v) { setString(filterExpression, v, null); }

  //endregion Property "filterExpression"

  //region Property "repeatRelation"

  /**
   * Slot for the {@code repeatRelation} property.
   * If true, as long as the relations exist to be traversed, this level def will be repeatedly
   * executed to produce additional child level elems.  Otherwise, if false, the relations will only
   * be traversed once.
   * @see #getRepeatRelation
   * @see #setRepeatRelation
   */
  public static final Property repeatRelation = newProperty(0, false, null);

  /**
   * Get the {@code repeatRelation} property.
   * If true, as long as the relations exist to be traversed, this level def will be repeatedly
   * executed to produce additional child level elems.  Otherwise, if false, the relations will only
   * be traversed once.
   * @see #repeatRelation
   */
  public boolean getRepeatRelation() { return getBoolean(repeatRelation); }

  /**
   * Set the {@code repeatRelation} property.
   * If true, as long as the relations exist to be traversed, this level def will be repeatedly
   * executed to produce additional child level elems.  Otherwise, if false, the relations will only
   * be traversed once.
   * @see #repeatRelation
   */
  public void setRepeatRelation(boolean v) { setBoolean(repeatRelation, v, null); }

  //endregion Property "repeatRelation"

  //region Property "cachingRepeatLimit"

  /**
   * Slot for the {@code cachingRepeatLimit} property.
   * The number of times this level def will be repeated, if possible, when the hierarchy is cached.
   * This limit does not affect expansion of a non-cached hierarchy.  This property is only relevant
   * when the repeatRelation property is true.
   * @since Niagara 4.4
   * @see #getCachingRepeatLimit
   * @see #setCachingRepeatLimit
   */
  public static final Property cachingRepeatLimit = newProperty(0, 5, BFacets.make(BFacets.MIN, 1));

  /**
   * Get the {@code cachingRepeatLimit} property.
   * The number of times this level def will be repeated, if possible, when the hierarchy is cached.
   * This limit does not affect expansion of a non-cached hierarchy.  This property is only relevant
   * when the repeatRelation property is true.
   * @since Niagara 4.4
   * @see #cachingRepeatLimit
   */
  public int getCachingRepeatLimit() { return getInt(cachingRepeatLimit); }

  /**
   * Set the {@code cachingRepeatLimit} property.
   * The number of times this level def will be repeated, if possible, when the hierarchy is cached.
   * This limit does not affect expansion of a non-cached hierarchy.  This property is only relevant
   * when the repeatRelation property is true.
   * @since Niagara 4.4
   * @see #cachingRepeatLimit
   */
  public void setCachingRepeatLimit(int v) { setInt(cachingRepeatLimit, v, null); }

  //endregion Property "cachingRepeatLimit"

  //region Property "sort"

  /**
   * Slot for the {@code sort} property.
   * The order in which level elems will be displayed.  Default is ascending.
   * @see #getSort
   * @see #setSort
   */
  public static final Property sort = newProperty(0, BLevelSort.ascending, null);

  /**
   * Get the {@code sort} property.
   * The order in which level elems will be displayed.  Default is ascending.
   * @see #sort
   */
  public BLevelSort getSort() { return (BLevelSort)get(sort); }

  /**
   * Set the {@code sort} property.
   * The order in which level elems will be displayed.  Default is ascending.
   * @see #sort
   */
  public void setSort(BLevelSort v) { set(sort, v, null); }

  //endregion Property "sort"

  //region Property "relationId"

  /**
   * Slot for the {@code relationId} property.
   * @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
   * @see #getRelationId
   * @see #setRelationId
   */
  @Deprecated
  public static final Property relationId = newProperty(Flags.HIDDEN | Flags.READONLY, "", null);

  /**
   * Get the {@code relationId} property.
   * @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
   * @see #relationId
   */
  @Deprecated
  public String getRelationId() { return getString(relationId); }

  /**
   * Set the {@code relationId} property.
   * @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
   * @see #relationId
   */
  @Deprecated
  public void setRelationId(String v) { setString(relationId, v, null); }

  //endregion Property "relationId"

  //region Property "inbound"

  /**
   * Slot for the {@code inbound} property.
   * @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
   * @see #getInbound
   * @see #setInbound
   */
  @Deprecated
  public static final Property inbound = newProperty(Flags.HIDDEN | Flags.READONLY, true, null);

  /**
   * Get the {@code inbound} property.
   * @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
   * @see #inbound
   */
  @Deprecated
  public boolean getInbound() { return getBoolean(inbound); }

  /**
   * Set the {@code inbound} property.
   * @deprecated since Niagara 4.3; use inboundRelationIds and outboundRelationIds instead
   * @see #inbound
   */
  @Deprecated
  public void setInbound(boolean v) { setBoolean(inbound, v, null); }

  //endregion Property "inbound"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRelationLevelDef.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * The started() method is called when a component's running state moves to true.  Components are
   * started top-down, children after their parent.
   *
   * <p>Here, the relation level def is also migrated, if necessary, from using the
   * {@link #relationId} and {@link #inbound} properties to the {@link #outboundRelationIds} and
   * {@link #inboundRelationIds} properties.</p>
   */
  @SuppressWarnings("deprecation")
  @Override
  public void started()
    throws Exception
  {
    super.started();

    // Log a warning message if RelationId is not empty
    if (!getRelationId().isEmpty())
    {
      BHierarchyService.log.warning(() -> "Old relation level id exists and the value is: " + getRelationId());
    }

    // If the outbound and inbound relation id's are empty and relation id value exists, this value
    // is copied over to one of the outbound or inbound relation id's on the basis of the boolean
    // inbound flag.
    // These values get persisted into the bog file on save station or stop station action.
    if (getOutboundRelationIds().isEmpty() && getInboundRelationIds().isEmpty() && !getRelationId().isEmpty())
    {
      BHierarchyService.log.fine(() -> "Mapping old config.bog to use new relation level definition format. Deprecated::relationId :" + getRelationId());
      // check inbound property and set inbound or outbound relation id
      if (getInbound())
        setInboundRelationIds(getRelationId());
      else
        setOutboundRelationIds(getRelationId());
    }

    // reset the relationId and inbound
    setRelationId("");
    setInbound(false);
  }

  /**
   * Enforce the minimum value of the {@link #cachingRepeatLimit} property on the server side.
   *
   * @param property property being changed
   * @param context execution context
   */
  @Override
  public void changed(Property property, Context context)
  {
    super.changed(property, context);

    if (!isRunning())
      return;

    if (property == cachingRepeatLimit)
    {
      // Coerce the cachingRepeatLimit to the minimum
      int minLimit = cachingRepeatLimit.getFacets().geti(BFacets.MIN, 1);
      int value = getInt(property);
      if (value < minLimit)
      {
        setInt(property, minLimit);
        BHierarchyService.log.fine(() -> "Coerced cachingRepeatLimit value of " + value + " to minimum value of " + minLimit);
      }
    }
  }

  /**
   * Returns level elements for entities found by traversing the relations specified in the
   * {@link #outboundRelationIds} and {@link #inboundRelationIds} properties.
   *
   * @param parent parent level element used as the base for traversing relations
   * @param cx execution context; its user is not used for getting permissions that determine which
   *           elements to return
   * @return elements as a result of traversing the specified relations
   */
  @SuppressWarnings("try")
  @Override
  public BLevelElem[] getElements(BLevelElem parent, Context cx)
  {
    try
    {
      BOrd traverseBaseOrd = getTraverseBaseOrd(parent);

      String outboundRelationsIds = getOutboundRelationIds().trim();
      String inboundRelationIds = getInboundRelationIds().trim();
      String filterExpression = getFilterExpression().trim();

      // Include context parameters from the parent
      BUser user = getUser();
      Context queryContext = createQueryContext(user, cx, parent);

      if (BHierarchyService.log.isLoggable(Level.FINE))
      {
        if (!outboundRelationsIds.isEmpty())
          BHierarchyService.log.fine("BRelationLevelDef(" + getName() + ").getElements: " + traverseBaseOrd + " -> " + outboundRelationsIds);
        if (!inboundRelationIds.isEmpty())
          BHierarchyService.log.fine("BRelationLevelDef(" + getName() + ").getElements: " + traverseBaseOrd + " <- " + inboundRelationIds);
        if (!filterExpression.isEmpty())
          BHierarchyService.log.fine("  filter=" + filterExpression);

        BHierarchyService.log.fine("  context=" + queryContext.getFacets());
      }

      BRelTime queryTimeout = getHierarchyService().getHierarchyTimeout();
      Map<BOrd, BLevelElem> levelElems = new LinkedHashMap<>();
      Map<String, BIcon> iconCache = new ConcurrentHashMap<>();

      // traverse each outboundRelationId
      for (String relationId : RELATION_ID_SPLIT.split(outboundRelationsIds))
      {
        Optional<String> neql = getTraverseNeqlPredicate(relationId, false, filterExpression);
        if (neql.isPresent())
        {
          try (CloseableIterator<Entity> entities = resolveQueryOnScopes(this, BOrd.make(neql.get()), traverseBaseOrd, queryTimeout, queryContext))
          {
            while (entities.hasNext())
            {
              addToElements(entities.next(), levelElems, parent, iconCache, queryContext);
            }
          }
        }
      }

      // traverse each inboundRelationId
      for (String relationId : RELATION_ID_SPLIT.split(inboundRelationIds))
      {
        Optional<String> neql = getTraverseNeqlPredicate(relationId, true, filterExpression);
        if (neql.isPresent())
        {
          try (CloseableIterator<Entity> entities = resolveQueryOnScopes(this, BOrd.make(neql.get()), traverseBaseOrd, queryTimeout, queryContext))
          {
            while (entities.hasNext())
            {
              addToElements(entities.next(), levelElems, parent, iconCache, queryContext);
            }
          }
        }
      }

      BLevelElem[] elems = levelElems.values().toArray(new BLevelElem[levelElems.size()]);
      sortElems(elems, getSort());
      return elems;
    }
    catch (Exception e)
    {
      BHierarchyService.log.log(Level.SEVERE, e, () -> "Could not resolve elements for " + getName());
    }

    return EMPTY_LEVEL_ELEMS;
  }

  /**
   * Adds all unique entities as level elements to the supplied map.
   * @param entity entity for which, if it is unique, a level element will be added
   * @param levelElems map of entity ord to level elements
   * @param parent parent level element to which an entity level element will be appended
   * @param iconCache cache of icons based on entity type
   * @param context used to retrieve the entity's display name
   */
  private void addToElements(Entity entity, Map<BOrd, BLevelElem> levelElems, BLevelElem parent,
    Map<String, BIcon> iconCache, Context context)
  {
    Optional<BOrd> ord = entity.getOrdToEntity();

    // skip entities that do not have an ord or have an ord we already have
    if (!ord.isPresent() || levelElems.containsKey(ord.get()))
    {
      return;
    }

    levelElems.put(ord.get(), makeEntityElem(entity, this, parent, iconCache, context));
  }

  @Override
  public String toString(Context cx)
  {
    String result = "Relation Level Def: ";
    String outboundIds = getOutboundRelationIds().trim();
    String inboundIds = getInboundRelationIds().trim();

    // Only show the filter expression if there are any outbound or inbound relations IDs
    String filter = getFilterExpression().trim();

    if (!outboundIds.isEmpty())
    {
      // Outbound IDs go first
      result += "out: " + outboundIds +
        (inboundIds.isEmpty() ? "" : "; in: " + inboundIds) +
        (filter.isEmpty()     ? "" : "; filter: " + filter);
    }
    else if (!inboundIds.isEmpty())
    {
      result += "in: " + inboundIds +
        (filter.isEmpty()     ? "" : "; filter: " + filter);
    }

    return result;
  }
}
