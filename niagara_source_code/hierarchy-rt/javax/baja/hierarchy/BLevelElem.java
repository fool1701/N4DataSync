/*
 * Copyright 2013, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import static com.tridium.hierarchy.HierarchyUtil.getDefSort;
import static com.tridium.hierarchy.HierarchyUtil.getUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.data.BIDataValue;
import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BIIndirectNavNode;
import javax.baja.nav.BINavContainer;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Id;
import javax.baja.tag.Tag;
import javax.baja.tag.Tags;
import javax.baja.user.BUser;

import com.tridium.sys.schema.Fw;
import com.tridium.util.PxUtil;

/**
 * BLevelElem is the neql query result element.
 * It is used to capture the target component and Hierarchy navigation info.
 * <p>
 * Please note, any changes to this class should also be reflected in the BajaScript
 * version (hierarchy-ux/rc/bs/LevelElem.js).
 * </p>
 *
 * @author    Andy Saunders
 * @creation  1 July 2013
 * @since     Baja 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "elemName",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "elemIcon",
  type = "BIcon",
  defaultValue = "BIcon.DEFAULT"
)
/*
 For remote calls to getElements(), contextParams must always contain:
 BLevelDef.ENTITY_ORD
 BLevelDef.LEVEL_DEF_PATH
 BLevelDef.CHILD_PREDICATE
 */
@NiagaraProperty(
  name = "contextParams",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  flags = Flags.HIDDEN | Flags.READONLY
)
@NiagaraProperty(
  name = "elemTags",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  flags = Flags.HIDDEN | Flags.READONLY
)
public class BLevelElem extends BComponent
  implements Comparable<Object>, BINavContainer, BIIndirectNavNode
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BLevelElem(1156413994)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "elemName"

  /**
   * Slot for the {@code elemName} property.
   * @see #getElemName
   * @see #setElemName
   */
  public static final Property elemName = newProperty(0, "", null);

  /**
   * Get the {@code elemName} property.
   * @see #elemName
   */
  public String getElemName() { return getString(elemName); }

  /**
   * Set the {@code elemName} property.
   * @see #elemName
   */
  public void setElemName(String v) { setString(elemName, v, null); }

  //endregion Property "elemName"

  //region Property "elemIcon"

  /**
   * Slot for the {@code elemIcon} property.
   * @see #getElemIcon
   * @see #setElemIcon
   */
  public static final Property elemIcon = newProperty(0, BIcon.DEFAULT, null);

  /**
   * Get the {@code elemIcon} property.
   * @see #elemIcon
   */
  public BIcon getElemIcon() { return (BIcon)get(elemIcon); }

  /**
   * Set the {@code elemIcon} property.
   * @see #elemIcon
   */
  public void setElemIcon(BIcon v) { set(elemIcon, v, null); }

  //endregion Property "elemIcon"

  //region Property "contextParams"

  /**
   * Slot for the {@code contextParams} property.
   * For remote calls to getElements(), contextParams must always contain:
   * BLevelDef.ENTITY_ORD
   * BLevelDef.LEVEL_DEF_PATH
   * BLevelDef.CHILD_PREDICATE
   * @see #getContextParams
   * @see #setContextParams
   */
  public static final Property contextParams = newProperty(Flags.HIDDEN | Flags.READONLY, BFacets.DEFAULT, null);

  /**
   * Get the {@code contextParams} property.
   * For remote calls to getElements(), contextParams must always contain:
   * BLevelDef.ENTITY_ORD
   * BLevelDef.LEVEL_DEF_PATH
   * BLevelDef.CHILD_PREDICATE
   * @see #contextParams
   */
  public BFacets getContextParams() { return (BFacets)get(contextParams); }

  /**
   * Set the {@code contextParams} property.
   * For remote calls to getElements(), contextParams must always contain:
   * BLevelDef.ENTITY_ORD
   * BLevelDef.LEVEL_DEF_PATH
   * BLevelDef.CHILD_PREDICATE
   * @see #contextParams
   */
  public void setContextParams(BFacets v) { set(contextParams, v, null); }

  //endregion Property "contextParams"

  //region Property "elemTags"

  /**
   * Slot for the {@code elemTags} property.
   * @see #getElemTags
   * @see #setElemTags
   */
  public static final Property elemTags = newProperty(Flags.HIDDEN | Flags.READONLY, BFacets.DEFAULT, null);

  /**
   * Get the {@code elemTags} property.
   * @see #elemTags
   */
  public BFacets getElemTags() { return (BFacets)get(elemTags); }

  /**
   * Set the {@code elemTags} property.
   * @see #elemTags
   */
  public void setElemTags(BFacets v) { set(elemTags, v, null); }

  //endregion Property "elemTags"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLevelElem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLevelElem() {}

  public BLevelElem(BLevelDef levelDef, BLevelElem parent, String name, BIcon icon, BFacets contextParams)
  {
    this(levelDef, parent, name, icon, contextParams, BFacets.DEFAULT);
  }

  public BLevelElem(BLevelDef levelDef, BLevelElem parent, String name, BIcon icon, BFacets contextParams, BFacets elemTags)
  {
    this.levelDef = levelDef;
    this.parent = parent;
    if (name != null)
    {
      setElemName(name);
    }
    if (icon != null)
    {
      setElemIcon(icon);
    }
    if (contextParams != null)
    {
      setContextParams(contextParams);
    }
    if (elemTags != null)
    {
      setElemTags(elemTags);
    }
  }

  /**
   * Server side only.
   */
  protected BLevelDef getLevelDef()
  {
    return levelDef;
  }

  public void setHierarchySpace(BHierarchySpace space)
  {
    this.space = space;
  }

  public BHierarchySpace getHierarchySpace()
  {
    return space;
  }

  @Override
  public BINavNode[] getNavChildren()
  {
    if (navChildCache == null)
    {
      navChildCache = BHierarchyService.getHierarchyElems(this, space, null);
    }
    return navChildCache;
  }

  @Override
  public String getNavName()
  {
    return SlotPath.escape(getElemName());
  }

  @Override
  public String getNavDisplayName(Context cx)
  {
    String displayName = getElemTags().gets(BLevelDef.DISPLAY_NAME_TAG_NAME, null);
    if (displayName == null)
    {
      displayName = getElemName();
    }
    return displayName;
  }

  @Override
  public BINavNode getNavChild(String navName)
  {
    if (navChildCache == null)
    {
      getNavChildren();
    }

    if (navChildCache != null)
    {
      for (BINavNode navChild : navChildCache)
      {
        if (navChild.getNavName().equals(navName))
        {
          return navChild;
        }
      }
    }
    return null;
  }

  @Override
  public BINavNode resolveNavChild(String navName)
  {
    BINavNode child = getNavChild(navName);
    if (child != null)
    {
      return child;
    }
    throw new UnresolvedException(navName);
  }

  @Override
  public boolean hasNavChildren()
  {
    return true;
  }

  @Override
  public String toString(Context cx)
  {
    try
    {
      return "levelDef: " + levelDef.getName() +
        ", parent: " + (parent == null ? "null" : parent.getElemName()) +
        ", elemName: " + getElemName();
    }
    catch(Exception ignored)
    {
      return super.toString(cx);
    }
  }

  @Override
  public BIcon getNavIcon()
  {
    return getElemIcon();
  }

  @Override
  public BHost getHost()
  {
    return space != null ? space.getHost() : null;
  }

  @Override
  public BISession getSession()
  {
    return space != null ? space.getSession() : null;
  }

  /**
   * @return the {@code hierarchyOrd}
   */
  @Override
  public BOrd getNavOrd()
  {
    if (space == null)
    {
      return (BOrd)getContextParams().get(BLevelDef.HIERARCHY_ORD);
    }

    return BOrd.make(space.getSession().getNavOrd(), (BOrd)getContextParams().get(BLevelDef.HIERARCHY_ORD));
  }

  @Override
  public BOrd getTargetOrd()
  {
    BComponent target = getTargetComponent();
    return target != null ? target.getNavOrd() : null;
  }

  /**
   * @return the neql predicate used to create this LevelElem.
   */
  public String getChildPredicate()
  {
    return getContextParams().gets(BLevelDef.CHILD_PREDICATE, "");
  }

  /**
   * @return the path in the HierarchyService to the BLevelDef that created this BLevelElem.
   */
  public String getLevelDefPath()
  {
    return getContextParams().gets(BLevelDef.LEVEL_DEF_PATH, "");
  }

  /**
   * @return return the entity ord if this BLevelElem points to an Entity, otherwise returns BOrd.NULL.
   */
  public BOrd getEntityOrd()
  {
    return (BOrd)getContextParams().get(BLevelDef.ENTITY_ORD, BOrd.NULL);
  }

  /**
   * @return the hierarchy ord that resolves to this BLevelElem.
   */
  public BOrd getHierarchyOrd()
  {
    return (BOrd)getContextParams().get(BLevelDef.HIERARCHY_ORD, BOrd.NULL);
  }

  /**
   * @return the targetComponent if the {@code getEntityOrd()} resolves to a BComponent, otherwise returns null.
   */
  public BComponent getTargetComponent()
  {
    return doGetTargetComponent(/*lease*/true);
  }

  BComponent doGetTargetComponent(boolean lease)
  {
    if (getEntityOrd().isNull())
    {
      return null;
    }

    String entityOrdStr = getEntityOrd().toString();
    BOrd eOrd = BOrd.make(entityOrdStr.startsWith("station:|") ?
      entityOrdStr : "station:|" + entityOrdStr);

    try
    {
      return eOrd.resolve().get().asComponent();
    }
    catch(Exception ignored) { }

    if (getHierarchySpace() != null)
    {
      String absOrdStr = getHierarchySpace().getAbsoluteOrd().toString();
      String stationAbsOrdStr = TextUtil.replace(absOrdStr, "hierarchy:", "station:");
      BOrd targetOrd = BOrd.make(stationAbsOrdStr + '|' + getEntityOrd());
      try
      {
        BComponent target = targetOrd.resolve().get().asComponent();

        // TODO: Is this lease ever necessary? It is a performance killer. If not ever necessary,
        // consolidate this method back to just getTargetComponent()
        // From NHyperlinkInfo#resolve: need component's up to date so that we can figure out
        // default views etc
        if (lease)
        {
          target.lease();
        }

        return target;
      }
      catch(Exception ignore) { }
    }

    return null;
  }

  /**
   * Comparison based on NavDisplayName.
   */
  @Override
  public int compareTo(Object other)
  {
    if (other instanceof BLevelElem)
    {
      BLevelElem otherElem = (BLevelElem)other;
      int displayNameOrder = getNavDisplayName(null).compareTo(otherElem.getNavDisplayName(null));
      if (displayNameOrder == 0)
      {
        // If display names are the same, compare the nav ords, which are hierarchy ords and will be unique.
        return getNavOrd().compareTo(otherElem.getNavOrd());
      }

      return displayNameOrder;
    }
    return toString().compareTo(other.toString());
  }

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  /**
   * Get the list of agents for this BObject.  The
   * default implementation of this method returns
   * {@code Registry.getAgents()}
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    AgentInfo[] agentInfos = agents.list();
    for (AgentInfo agentInfo : agentInfos)
    {
      String agentId = agentInfo.getAgentId();
      if ("workbench:WbComponentView".equals(agentId) || "pxEditor:PxEditor".equals(agentId))
      {
        agents.remove(agentInfo);
      }
    }
    agents.add("workbench:NavContainerView");
    return PxUtil.movePxViewsToTop(agents);
  }

  @Override
  public String getNavDescription(Context cx)
  {
    BComponent target = doGetTargetComponent(/*lease*/false);
    if(target != null)
    {
      return target.toString(cx);
    }
    return getType().toString();
  }

  /**
   * Return {@code getParent()}.
   */
  @Override
  public BINavNode getNavParent()
  {
    if (parent != null)
    {
      return parent;
    }
    return getHierarchySpace();
  }

///////////////////////////////////////////////////////////
// Taggable
///////////////////////////////////////////////////////////

  @Override
  public Tags tags()
  {
    BComponent target = getTargetComponent();
    return target != null ? target.tags() : new LevelElemTags(getElemTags());
  }

  @Override
  protected void spyTags(SpyWriter out)
  {
    out.trTitle("Tags", 2);
    Tags tags = tags();
    for(Tag tag : tags)
    {
      out.prop(tag.getId(), tag.getValue());
    }
  }

///////////////////////////////////////////////////////////
// BINavContainer - no op implementation to support
//   NavContainer viewing, but not editing
///////////////////////////////////////////////////////////

  /**
   * Not implemented  to support NavContainer viewing, but not editing
   */
  @Override
  public void addNavChild(BINavNode child)
  {
  }

  /**
   * Not implemented  to support NavContainer viewing, but not editing
   */
  @Override
  public void removeNavChild(BINavNode child)
  {
  }

  /**
   * Not implemented  to support NavContainer viewing, but not editing
   */
  @Override
  public void reorderNavChildren(BINavNode[] children)
  {
  }

////////////////////////////////////////////////////////////////
// Framework access
////////////////////////////////////////////////////////////////

  /**
   * @since Niagara 4.4
   */
  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
    case Fw.LEVELELEM_IS_CACHED:
      return isCachedOnServer;

    case Fw.LEVELELEM_ADD_CACHED_CHILD:
      // a: BLevelElem child

      // A lock on the cache is not necessary because the cached root obtained from a BHierarchy is
      // not available until all elements have been added to the cache.

      // Adding a null child is used on the hierarchy's root element to indicate that it is part of a
      // cached hierarchy even if it does not have any children.
      if (a == null)
      {
        isCachedOnServer = true;
        return null;
      }

      BLevelElem child = (BLevelElem)a;

      if (serverCacheChildren == null)
      {
        serverCacheChildren = new ArrayList<>();
      }

      serverCacheChildren.add(child);
      child.isCachedOnServer = true;

      // get the sort property of the level def; all level def types have one except BHierarchy
      BLevelDef childDef = child.levelDef;
      BLevelSort sort = getDefSort(childDef);

      switch (sort.getOrdinal())
      {
      case BLevelSort.ASCENDING:
        serverCacheChildren.sort(Comparator.naturalOrder());
        break;
      case BLevelSort.DESCENDING:
        serverCacheChildren.sort(Comparator.reverseOrder());
        break;
      default:
        if (childDef instanceof BListLevelDef)
        {
          noneSortListDef((BListLevelDef)childDef);
        }
      }

      return null;

    case Fw.LEVELELEM_GET_CACHED_CHILDREN:
      // A lock on the cache is not necessary because the cached root obtained from a BHierarchy is
      // not available until all elements have been added to the cache.
      if (!isCachedOnServer || serverCacheChildren == null)
      {
        return BLevelDef.EMPTY_LEVEL_ELEMS;
      }

      BUser user = getUser();

      // Return everything to a super user
      // TODO NCCB-30147 Should return an empty array for a null user once background threads can
      // provide a current authenticated user.
      if (user == null || user.getPermissions().isSuperUser())
      {
        return serverCacheChildren.toArray(EMPTY_LEVEL_ELEM_ARR);
      }

      // Otherwise, return items for which the user has permissions
      return serverCacheChildren.stream()
        .filter(elem -> elem.getPermissions(user).hasOperatorRead())
        .toArray(BLevelElem[]::new);

    case Fw.LEVELELEM_GET_LEVELDEF:
      return levelDef;
    }

    return super.fw(x, a, b, c, d);
  }

  /**
   * Keep the named group defs in the order they appear under the list level def and not the order
   * in which they are added.  This matches the way they would appear in normal navigation.
   *
   * @param listDef list level def whose named group def order should be matched
   */
  private void noneSortListDef(BListLevelDef listDef)
  {
    // Only a single child so no need to sort anything
    if (serverCacheChildren.size() <= 1)
    {
      return;
    }

    // Loop through all named group defs in the list def and create a new cache list that matches
    // the named group def order.
    ArrayList<BLevelElem> newOrder = new ArrayList<>(serverCacheChildren.size());
    for (BNamedGroupDef namedGroupDef : listDef.getChildren(BNamedGroupDef.class))
    {
      String namedGroupName = namedGroupDef.getName();

      for (int i = 0; i < serverCacheChildren.size(); ++i)
      {
        BLevelElem child = serverCacheChildren.get(i);
        if (child.getElemName().equals(namedGroupName))
        {
          newOrder.add(child);
          serverCacheChildren.remove(i);
          break;
        }
      }
    }

    if (!serverCacheChildren.isEmpty())
    {
      throw new IllegalStateException("At least one child in the server cache not found in the named group def of the list def.");
    }

    serverCacheChildren = newOrder;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private BHierarchySpace space;
  private BLevelDef levelDef;
  protected BLevelElem parent;
  private BINavNode[] navChildCache;

  private boolean isCachedOnServer;
  private ArrayList<BLevelElem> serverCacheChildren;

  private static final BLevelElem[] EMPTY_LEVEL_ELEM_ARR = new BLevelElem[0];

///////////////////////////////////////////////////////////
// Facet Tags
///////////////////////////////////////////////////////////

  private static class LevelElemTags
    implements Tags
  {
    public LevelElemTags(BFacets tags)
    {
      this.tags = tags;
    }

    @Override
    public boolean isEmpty()
    {
      return tags.size() <= 0;
    }

    @Override
    public boolean contains(Id id)
    {
      return tags.get(Id.idToFacetKey(id)) != null;
    }

    @Override
    public boolean isMulti(Id id)
    {
      return false;
    }

    @Override
    public boolean set(Tag tag)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean set(Id id, BIDataValue value)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean setMulti(Id id, Collection<? extends BIDataValue> values)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean addMulti(Id id, Collection<? extends BIDataValue> values)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean addMulti(Tag tag)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean addMulti(Id id, BIDataValue value)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean merge(Collection<Tag> tags)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean remove(Id id, BIDataValue value)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean removeAll(Id id)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    @Override
    public boolean remove(Tag tag)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    /**
     * Get the subset of tags in the collection that satisfy the given predicate.
     * <p/>
     * For example:
     * <pre>
     *   // Find all marker tags
     *   //
     *   Collection&lt;Tag> markers = myEntity.tags().filter(t -> t.getValue() instanceof BMarker);
     * </pre>
     *
     * @param condition the condition to test for each tag
     * @return a {@code Collection<Tag>} containing all tags that satisfy the condition
     */
    @Override
    public Collection<Tag> filter(Predicate<Tag> condition)
    {
      return getAll().stream()
        .filter(condition)
        .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get all tags in the collection.
     *
     * @return a {@code Collection<Tag>} containing all tags in the collection
     */
    @Override
    public Collection<Tag> getAll()
    {
      Map<Id, Tag> map = new HashMap<>();

      // add or overwrite all of the facets as tags
      if (!tags.isEmpty())
      {
        for (String key : tags.list())
        {
          Id tagId = Id.facetKeyToId(key);
          map.put(tagId, new Tag(tagId, (BIDataValue)tags.get(key)));
        }
      }

      return map.values();
    }

    /**
     * Get the value of the tag with the given id if it exists.
     * <p/>
     * If the tag is multi-value the returned value is non-deterministic.
     *
     * @param id the id to search for
     * @return a Optional containing the value of the tag with the given id if it exists
     */
    @Override
    public Optional<BIDataValue> get(Id id)
    {
      if (!tags.isEmpty())
      {
        BIDataValue value = (BIDataValue)tags.get(Id.idToFacetKey(id));
        if (value != null)
        {
          return Optional.of(value);
        }
      }
      return Optional.empty();
    }

    @Override
    public Collection<BIDataValue> getValues(Id id)
    {
      throw new UnsupportedOperationException("Not implemented.");
    }

    private final BFacets tags;
  }
}
