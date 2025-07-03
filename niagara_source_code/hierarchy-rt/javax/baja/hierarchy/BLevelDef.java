/*
 * Copyright 2013, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;

import com.tridium.hierarchy.MakeElemUtil;
import com.tridium.util.CompUtil;

/**
 * BLevelDef implements BLevelDef to provide basic next and previous functionality.
 *
 * @author Andy Saunders
 * @creation 1 July 2013
 * @since Baja 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "queryContext",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
public abstract class BLevelDef
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BLevelDef(2221683063)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "queryContext"

  /**
   * Slot for the {@code queryContext} property.
   * @see #getQueryContext
   * @see #setQueryContext
   */
  public static final Property queryContext = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code queryContext} property.
   * @see #queryContext
   */
  public BFacets getQueryContext() { return (BFacets)get(queryContext); }

  /**
   * Set the {@code queryContext} property.
   * @see #queryContext
   */
  public void setQueryContext(BFacets v) { set(queryContext, v, null); }

  //endregion Property "queryContext"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLevelDef.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * LevelDefs don't have children.
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return false;
  }

  /**
   * Only allow one BHierarchy parents.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BHierarchy;
  }

  /**
   * Get the BLevelElement array for parent BLevelElement.
   * If this definitions results in no elements at this level
   * return an array of length zero.
   *
   * @param parentElem is the parent Level Elem in the Hierarchy
   * @param cx execution context; its user is not used for getting permissions that determine which
   *           elements to return
   */
  public abstract BLevelElem[] getElements(BLevelElem parentElem, Context cx);

  static void sortElems(BLevelElem[] elems, BLevelSort sort)
  {
    switch (sort.getOrdinal())
    {
    case BLevelSort.ASCENDING:
      SortUtil.sort(elems); break;
    case BLevelSort.DESCENDING:
      SortUtil.rsort(elems); break;
    }
  }

  /**
   * Return the parent levelDef of null if isRoot def.
   */
  public BLevelDef getPrevious()
  {
    BHierarchy root = getHierarchy();
    BLevelDef[] childLevelDefs = CompUtil.getDescendants(root, BLevelDef.class);
    for (int i = 0; i < childLevelDefs.length; ++i)
    {
      if (childLevelDefs[i] == this)
      {
        return i == 0 ? root : childLevelDefs[i - 1];
      }
    }

    throw new IllegalStateException("Could not find level def in the descendants of its hierarchy root");
  }

  /**
   * Return the next levelDef or null if isLeaf.
   */
  public BLevelDef getNext()
  {
    BHierarchy root = getHierarchy();
    BLevelDef[] childLevelDefs = CompUtil.getDescendants(root, BLevelDef.class);

    return getNextLevel(childLevelDefs);
  }

  private BLevelDef getNextLevel(BLevelDef[] childLevelDefs)
  {
    for (int i = 0; i < childLevelDefs.length; ++i)
    {
      if (childLevelDefs[i] == this)
      {
        if (i + 1 < childLevelDefs.length)
        {
          return childLevelDefs[i + 1];
        }
        return null;
      }
    }
    return null;
  }

  /**
   * Server side call.
   */
  protected BHierarchyService getHierarchyService()
  {
    return (BHierarchyService)Sys.getService(BHierarchyService.TYPE);
  }

  public BHierarchy getHierarchy()
  {
    BComplex parent = getParent();
    while (parent != null && !parent.getType().is(BHierarchy.TYPE))
    {
      parent = parent.getParent();
    }

    return parent == null ? null : (BHierarchy)parent;
  }

  /**
   * Combine the specified existing and {@link #queryContext} property facets along with an entity
   * ord (if the entity is specified; see {@link #ENTITY_ORD}), a hierarchy ord (using the specified
   * path name; see {@link #HIERARCHY_ORD}), a slot path from the hierarchy service to this level
   * definition (see {@link #LEVEL_DEF_PATH}), this level definition's predicate (see
   * {@link #CHILD_PREDICATE}), and a type specification of the specified entity (see
   * {@link #TYPE_SPEC}).
   *
   * @param entity used to resolve any queryContext facets to tag values, create the entity ord, and
   * determine the type spec
   * @param pathName prepended by a '/' and appended to a new or existing hierarchy ord
   * @param childPredicate this level definition's predicate
   * @param existing existing facets
   * @return set of facets from the combination of the specified existing ones, the contents of the
   * queryContext property, and ENTITY_ORD, HIERARCHY_ORD, TYPE_SPEC, LEVEL_DEF_PATH, and
   * CHILD_PREDICATE
   */
  protected BFacets getResolvedContextParams(Entity entity, String pathName, String childPredicate,
                                             BFacets existing)
  {
    return getResolvedContextParams(entity, pathName, childPredicate, existing, BFacets.DEFAULT);
  }

  /**
   * Combine the specified existing, {@link #queryContext} property, and specified new facets along
   * with an entity ord (if the entity is specified; see {@link #ENTITY_ORD}), a hierarchy ord
   * (using the specified path name; see {@link #HIERARCHY_ORD}), a slot path from the hierarchy
   * service to this level definition (see {@link #LEVEL_DEF_PATH}), this level definition's
   * predicate (see {@link #CHILD_PREDICATE}), and a type specification of the specified entity (see
   * {@link #TYPE_SPEC}).
   *
   * @param entity used to resolve any queryContext facets to tag values, create the entity ord, and
   * determine the type spec
   * @param pathName prepended by a '/' and appended to a new or existing hierarchy ord
   * @param childPredicate this level definition's predicate
   * @param existing existing facets
   * @param newParams new facets to include
   * @return set of facets from the combination of the specified existing ones, the contents of the
   * queryContext property, specified new ones, and ENTITY_ORD, HIERARCHY_ORD, TYPE_SPEC,
   * LEVEL_DEF_PATH, and CHILD_PREDICATE
   */
  protected BFacets getResolvedContextParams(Entity entity, String pathName, String childPredicate,
                                             BFacets existing, BFacets newParams)
  {
    return MakeElemUtil.getResolvedContextParams(this, entity, pathName, childPredicate,
      existing, newParams);
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  /**
   * Path from the Hierarchy Service to this Level Def
   */
  public static final String LEVEL_DEF_PATH = "levelDefPath";
  /**
   * NEQL predicate that defines this level (based on the previous levels)
   */
  public static final String CHILD_PREDICATE = "childPredicate";
  /**
   * Ord to the resulting Entity.
   */
  public static final String ENTITY_ORD = "entityOrd";
  /**
   * Ord to the resulting Entity.
   */
  public static final String HIERARCHY_ORD = "hierarchyOrd";
  /**
   * TypeSpec of the Entity - used in WebUI.
   */
  public static final String TYPE_SPEC = "typeSpec";
  /**
   * Tag Id for n:displayName
   */
  public static final String DISPLAY_NAME_TAG_NAME = SlotPath.escape("n:displayName");

  private static final BIcon icon = BIcon.make("module://hierarchy/rc/level.png");

  /**
   * Constant to be returned by getElements is something goes wrong.
   */
  protected static final BLevelElem[] EMPTY_LEVEL_ELEMS = new BLevelElem[0];

  protected boolean fatalFault;
}
