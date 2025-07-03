/*
 * Copyright (c) 2018 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import static javax.baja.hierarchy.BHierarchyService.LEVEL_DEF_TYPES_PROP_NAME;

import static com.tridium.hierarchy.HierarchyUtil.findElem;
import static com.tridium.hierarchy.HierarchyUtil.getUser;

import java.util.List;

import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.space.BSpaceScheme;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BVector;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.util.BTypeSpec;

import com.tridium.hierarchy.fox.BFoxHierarchySpace;

/**
 * BHierarchyScheme is the ord scheme for accessing hierarchies.
 *
 * @author    Andrew Saunders
 * @creation  19 Aug 2013
 * @version   $Revision: 18$ $Date: 8/18/09 4:28:34 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "hierarchy"
)
@NiagaraSingleton
public class BHierarchyScheme extends BSpaceScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BHierarchyScheme(2129272053)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHierarchyScheme INSTANCE = new BHierarchyScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHierarchyScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with scheme id.
   */
  protected BHierarchyScheme()
  {
    super("hierarchy");
  }

  /**
   * BHierarchyScheme is used to access histories in a BHierarchySpace.
   */
  @Override
  public Type getSpaceType()
  {
    return BHierarchySpace.TYPE;
  }

  /**
   * This method gives scheme the chance to return a custom subclass of OrdQuery with a scheme
   * specific API.  The default implementation returns an instance of BasicQuery.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new HierarchyQuery(getId(), queryBody);
  }

  /**
   * This is the subclass hook for resolve after the default implementation has mapped the ord to an
   * instanceof BHierarchySpace.
   *
   * @param base base ord target; its user is not used for getting permissions that determine which
   *             elements to return
   * @param query hierarchy query
   * @param space hierarchy space
   * @return if the resolved level element has a target component, return an ord target to it;
   * otherwise, return an ord target to the level element
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query, BSpace space)
  {
    BHierarchyService.log.fine(() -> "resolve: base=" + base + " query=" + query + " space=" + space.getSession().getNavOrd());

    BHierarchySpace hierarchySpace = (BHierarchySpace)space;

    HierarchyQuery hierarchyQuery = (HierarchyQuery)query;
    String[] hierarchyNames = hierarchyQuery.getNames();

    BFacets queryFacet = BFacets.make(HIERARCHY_QUERY_FACET, query.toString());

    if (hierarchyNames.length <= 0)
    {
      // Query body is empty; no need to do anything further
      return OrdTarget.makeWithFacets(base, hierarchySpace, queryFacet);
    }

    BLevelElem[] children;
    BHierarchyService service = null;

    // First, check if the user can view this hierarchy
    if (hierarchySpace instanceof BFoxHierarchySpace)
    {
      // This method will pull from the workbench nav child cache or ask the server if the cache is
      // empty.  If the hierarchy is in the nav child cache, permission checks were previous applied
      // in order to fill the cache.
      children = (BLevelElem[])hierarchySpace.getNavChildren();
    }
    else
    {
      // getHierarchyRootElems will only return level elems for those hierarchies that the user has
      // permission to view.
      service = (BHierarchyService)Sys.getService(BHierarchyService.TYPE);
      children = service.getHierarchyRootElems(null);
    }

    // If the hierarchy is not found, the user does not have permission to view it.
    BLevelElem parent = findElem(hierarchyNames[0], children);
    if (parent == null)
    {
      throw new UnresolvedException("Cannot resolve hierarchy elem " + hierarchyNames[0]);
    }

    // If the ord is only asking for the hierarchy level elem, return that.
    if (hierarchyNames.length == 1)
    {
      return OrdTarget.makeWithFacets(base, parent, queryFacet);
    }

    // Short circuit if the last name corresponds to a query or relation level def.
    // The hierarchy names will include the BHierarchy so length - 2 is used instead of length - 1.
    // For example, if a hierarchy has 3 level defs, then a hierarchy ord where the last name
    // corresponds to the 3rd level def would have 4 names: hierarchy:hierarchyName/def1/def2/def3
    String lastName = null;
    if (hierarchySpace instanceof BFoxHierarchySpace)
    {
      BVector levelDefTypes = (BVector)parent.get(LEVEL_DEF_TYPES_PROP_NAME);
      if (levelDefTypes != null)
      {
        Property[] levelDefTypeProps = levelDefTypes.getDynamicPropertiesArray();
        if ((hierarchyNames.length - 2) < levelDefTypeProps.length)
        {
          BTypeSpec levelDefTypeSpec = (BTypeSpec)levelDefTypes.get(levelDefTypeProps[hierarchyNames.length - 2]);
          if (levelDefTypeSpec.getTypeInfo().is(BIEntityLevelDef.TYPE))
          {
            lastName = hierarchyNames[hierarchyNames.length - 1];
          }
        }
      }
      else
      {
        // The old, fragile way of short-circuiting.  Retained for compatibility with older stations
        // that will not include the levelDefTypes property on the hierarchy root level elem.
        if (hierarchyNames[hierarchyNames.length - 1].startsWith("station$3a$7c"))
        {
          lastName = hierarchyNames[hierarchyNames.length - 1];
        }
      }
    }
    else
    {
      List<BLevelDef> levelDefs = parent.getLevelDef().getHierarchy().getLevelDefCache();
      if ((hierarchyNames.length - 2) < levelDefs.size() &&
          levelDefs.get(hierarchyNames.length - 2) instanceof BIEntityLevelDef)
      {
        lastName = hierarchyNames[hierarchyNames.length - 1];
      }
    }

    if (lastName != null)
    {
      // BOrd.resolve returns an OrdTarget but with the ord of the last elem instead of the
      // hierarchy ord in base.  Therefore, a new OrdTarget is created with the original hierarchy
      // ord and the resolved component.
      if (hierarchySpace instanceof BFoxHierarchySpace)
      {
        // The user will be null in the proxy space, so just resolve the ord and let the fox scheme
        // enforce permissions.
        BObject targetComp = BOrd.make(SlotPath.unescape(lastName)).resolve(base.get()).get();
        return OrdTarget.makeWithFacets(base, targetComp, queryFacet);
      }
      else
      {
        // TODO NCCB-30147 Should throw an UnresolvedException for a null user once background
        // threads can provide a current authenticated user.
        BUser user = getUser();

        OrdTarget target = BOrd.make(SlotPath.unescape(lastName)).resolve(base.get(), user);
        if (target.canRead())
        {
          return OrdTarget.makeWithFacets(base, target.get(), queryFacet);
        }

        throw new UnresolvedException("Cannot resolve hierarchy elem");
      }
    }

    Context context = parent.getContextParams();
    OrdTarget target = OrdTarget.makeWithFacets(base, parent, queryFacet);

    // Walk through the remaining names (the first was processed above)
    for (int i = 1; i < hierarchyNames.length; ++i)
    {
      String hierarchyName = hierarchyNames[i];
      BHierarchyService.log.fine(() -> "  " + hierarchyName);
      if (hierarchySpace instanceof BFoxHierarchySpace)
      {
        // For better performance using client side caching, we can just retrieve
        // the nav children which should be cached on the client side after the first load.
        children = (BLevelElem[])parent.getNavChildren();
      }
      else
      {
        // Instead of calling BHierarchyService.getHierarchyElems on each level of the hierarchy,
        // get the root and then child elements from each parent.  If the hierarchy is cached, the
        // root and each subsequent parent will contain the cached children.  If not cached, the
        // normal element resolution using the level def will occur.
        //
        // If the hierarchy finishes being cached in the middle of this resolution, the cached
        // elements will not be picked up once the root is picked up.  If the cached hierarchy is
        // cleared during resolution, that will not be reflected and results will still be returned
        // from the cached hierarchy.
        //
        // If BHierarchyService.getHierarchyElems was still used, the state of the cache would be
        // reflected in results almost immediately.  However, because getHierarchyElems could be
        // called with a parent in the middle of the hierarchy, that method must check whether the
        // hierarchy is cached each time.  Calling these methods directly on the service avoids
        // these duplicate checks.
        children = service.getChildElems(parent, context);
      }

      parent = findElem(hierarchyName, children);
      if (parent == null)
      {
        throw new UnresolvedException("Cannot resolve hierarchy elem " + hierarchyName);
      }

      context = parent.getContextParams();
      BComponent targetComp = parent.doGetTargetComponent(/*lease*/false);
      target = OrdTarget.makeWithFacets(base, targetComp != null ? targetComp : parent, queryFacet);
    }

    return target;
  }

  public static final String HIERARCHY_QUERY_FACET = "hierarchyQuery";
}
