/*
  Copyright 2012 Tridium Inc.- All Rights Reserved.
 */
package javax.baja.hierarchy;

import static com.tridium.hierarchy.HierarchyUtil.findElem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.license.Feature;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.role.BIRole;
import javax.baja.role.BRoleService;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.BVector;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;

import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.hierarchy.BHierarchyCacheStatus;
import com.tridium.hierarchy.QueryUtil;
import com.tridium.hierarchy.fox.BFoxHierarchyChannel;
import com.tridium.hierarchy.fox.BFoxHierarchySpace;
import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.schema.Fw;

/**
 * Manage hierarchy definitions.
 *
 * @author Blake Puhak on 4 Mar 2014
 * @since Niagara 4.0
 */
@NiagaraType
/*
 Specifies the maximum time to wait for a hierarchy level resolution to complete. The default
 value is 45 seconds.
 @since Niagara 4.3
 */
@NiagaraProperty(
  name = "hierarchyTimeout",
  type = "BRelTime",
  defaultValue = "BRelTime.make(45000)",
  facets = @Facet(name = "BFacets.MIN", value = "BRelTime.make(1)")
)
public class BHierarchyService extends BAbstractService implements BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BHierarchyService(2590436965)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "hierarchyTimeout"

  /**
   * Slot for the {@code hierarchyTimeout} property.
   * Specifies the maximum time to wait for a hierarchy level resolution to complete. The default
   * value is 45 seconds.
   * @since Niagara 4.3
   * @see #getHierarchyTimeout
   * @see #setHierarchyTimeout
   */
  public static final Property hierarchyTimeout = newProperty(0, BRelTime.make(45000), BFacets.make(BFacets.MIN, BRelTime.make(1)));

  /**
   * Get the {@code hierarchyTimeout} property.
   * Specifies the maximum time to wait for a hierarchy level resolution to complete. The default
   * value is 45 seconds.
   * @since Niagara 4.3
   * @see #hierarchyTimeout
   */
  public BRelTime getHierarchyTimeout() { return (BRelTime)get(hierarchyTimeout); }

  /**
   * Set the {@code hierarchyTimeout} property.
   * Specifies the maximum time to wait for a hierarchy level resolution to complete. The default
   * value is 45 seconds.
   * @since Niagara 4.3
   * @see #hierarchyTimeout
   */
  public void setHierarchyTimeout(BRelTime v) { set(hierarchyTimeout, v, null); }

  //endregion Property "hierarchyTimeout"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHierarchyService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /*
   * (non-Javadoc)
   *
   * @see javax.baja.sys.BIService#getServiceTypes()
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static final Type[] serviceTypes = { TYPE };

  /*
   * (non-Javadoc)
   *
   * @see javax.baja.sys.BIService#serviceStarted()
   */
  @Override
  public void serviceStarted()
  {
    if (isOperational())
    {
      // create and add the hierarchy space
      hSpace = new BHierarchySpace(this);
      BLocalHost.INSTANCE.addNavChild(hSpace);

      // add a hierarchy fox channel
      try
      {
        BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
        if (registry.get("hierarchy") == null)
        {
          registry.add("hierarchy", new BFoxHierarchyChannel());
        }
      }
      catch (Exception e)
      {
        log.severe(() -> "Unable to add BFoxHierarchyChannel");
      }

      QueryUtil.init();

      getComponentSpace().enableMixIn(BRoleHierarchies.TYPE);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see javax.baja.sys.BIService#serviceStopped()
   */
  @Override
  public void serviceStopped()
  {
    getComponentSpace().disableMixIn(BRoleHierarchies.TYPE);

    QueryUtil.cleanup();

    // remove the hierarchy channel
    try
    {
      BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
      if (registry.get("hierarchy") != null)
      {
        registry.remove("hierarchy");
      }
    }
    catch (Exception e)
    {
      log.severe(() -> "Unable to remove BFoxHierarchyChannel");
    }

    // remove the hierarchy space
    try
    {
      BLocalHost.INSTANCE.removeNavChild(hSpace);
    }
    catch(Exception e)
    {
      log.severe(() -> "Unable to remove BHierarchySpace");
    }

    hSpace = null;
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

  @Override
  public final Feature getLicenseFeature()
  {
    Feature feature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "hierarchy");
    allowLocalHierarchy = feature.getb("local", false);
    allowSystemHierarchy = feature.getb("system", false);
    if (!allowLocalHierarchy && !allowSystemHierarchy)
    {
      configFatal("Unlicensed for all scopes. No hierarchies are allowed.");
    }
    return feature;
  }

  public static BHierarchyScope[] getLicensedScopes(BHierarchyScope[] scopesArray)
  {
    ArrayList<BHierarchyScope> scopesList = new ArrayList<>();
    doGetLicensedScopes(scopesArray, scopesList);
    return scopesList.toArray(EMPTY_HIERARCHY_SCOPES_ARR);
  }

  private static void doGetLicensedScopes(BHierarchyScope[] scopesArray, List<BHierarchyScope> scopesList)
  {
    for (BHierarchyScope scope : scopesArray)
    {
      if (!allowLocalHierarchy  && isLocalScope(scope) || // Unlicensed for hierarchy against local scope
          !allowSystemHierarchy && isSystemScope(scope))  // Unlicensed for hierarchy against system scope
      {
        log.warning(scope + ": \"" + scope.getScopeOrd() + "\" is not a licensed scope.");
      }
      else
      {
        scopesList.add(scope);
      }
    }
  }

  static boolean isLocalScope(BHierarchyScope scope)
  {
    // TODO: Once we have the system db added, we need to fix this method and the system scope; check below
    return true;
  }

  static boolean isSystemScope(BHierarchyScope scope)
  {
    // TODO: Once we have the system db added, we need to fix this method and the local scope; check above
    return false;
  }

  /**
   * One or more instances of a BHierarchy are allowed as children.
   */
  @Override
  public boolean isChildLegal(BComponent c)
  {
    return c instanceof BHierarchy;
  }

  @Override
  public void renamed(Property property, String oldName, Context context)
  {
    super.renamed(property, oldName, context);

    if (isOperational())
    {
      try
      {
        BRoleService roleSvc = (BRoleService)Sys.getService(BRoleService.TYPE);
        BIRole[] roles = roleSvc.getChildren(BIRole.class);
        for (BIRole role : roles)
        {
          BRoleHierarchies hierarchies = (BRoleHierarchies)role.asObject().asComponent()
            .getMixIn(BRoleHierarchies.TYPE);
          if (hierarchies != null)
          {
            hierarchies.renameHierarchy(oldName, property.getName());
          }
        }
      }
      catch (ServiceNotFoundException ignored)
      {
      }
    }
  }

  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    super.removed(property, oldValue, context);

    if (isOperational())
    {
      try
      {
        BRoleService roleSvc = (BRoleService)Sys.getService(BRoleService.TYPE);
        BIRole[] roles = roleSvc.getChildren(BIRole.class);
        for (BIRole role : roles)
        {
          BRoleHierarchies hierarchies = (BRoleHierarchies)role.asObject().asComponent()
            .getMixIn(BRoleHierarchies.TYPE);
          if (hierarchies != null)
          {
            hierarchies.removeHierarchy(property.getName());
          }
        }
      }
      catch (ServiceNotFoundException ignored)
      {
      }
    }
  }

  public BLevelDef[] getHierarchyDefs()
  {
    return getChildren(BHierarchy.class);
  }

  /**
   * Return the child level elements of the specified parent.  Can be called on both the client and
   * server sides, given a base object that exists in the current VM's station space.
   * <p>
   * On the server side, {@link #getHierarchyRootElems(Context)} is equivalent to calling this
   * method when parent is null.  When the parent is not null, it is close to
   * {@link #getChildElems(String, BFacets, BFacets, Context)} but the parent passed in will be the
   * parent of the returned children.  No children will be returned for a parent in a hierarchy that
   * the user cannot view.
   *
   * @param parent level element on which to find children
   * @param base starting point for resolving the hierarchy service and space
   * @param cx execution context; its user is not used for getting permissions that determine which
   *           elements to return
   * @return parent's child elements or, if parent is null, elements for each hierarchy in the space
   */
  public static BLevelElem[] getHierarchyElems(BLevelElem parent, BObject base, Context cx)
  {
    // If called on the proxy side to retrieve the hierarchies
    BHierarchySpace space = getHierarchySpace(base);
    if (space instanceof BFoxHierarchySpace)
    {
      try
      {
        BLevelElem[] elems;
        if (parent == null)
        {
          elems = ((BFoxHierarchySpace)space).channel().getRootLevelElems();
        }
        else
        {
          elems = ((BFoxHierarchySpace)space).channel().getLevelElems(parent.getContextParams(), parent.getElemTags());
        }

        // set parent and space
        for (BLevelElem elem : elems)
        {
          elem.parent = parent;
          elem.setHierarchySpace(space);
        }

        return elems;
      }
      catch (Exception e)
      {
        log.log(Level.SEVERE, e, () -> "Failed to retrieve hierarchy elems over the fox channel");
        return BLevelDef.EMPTY_LEVEL_ELEMS;
      }
    }

    // Only server-side calls will make it to this point; client-side calls are handled above.
    try
    {
      BHierarchyService service = (BHierarchyService)Sys.getService(TYPE);
      if (parent == null)
      {
        return service.getHierarchyRootElems(cx);
      }
      else
      {
        if (service.isHierarchyViewable(parent))
        {
          return service.getChildElems(parent, cx);
        }
      }
    }
    catch (Exception e)
    {
      log.log(Level.SEVERE, e, () -> "Cannot get Hierarchy Elements");
    }

    return BLevelDef.EMPTY_LEVEL_ELEMS;
  }

  /**
   * @param parent elem whose hierarchy needs to be tested
   * @return true if user can view the hierarchy to which the parent belongs
   * @since Niagara 4.4
   */
  private boolean isHierarchyViewable(BLevelElem parent)
  {
    // server side only
    if (!isRunning() || !isOperational())
    {
      log.fine(() -> "isHierarchyViewable: service running? " + isRunning() + "; operational? " + isOperational());
      return false;
    }

    Object parentDef = parent.fw(Fw.LEVELELEM_GET_LEVELDEF);
    if (!(parentDef instanceof BLevelDef))
    {
      log.fine("isHierarchyViewable: cannot retrieve level def");
      return false;
    }

    BHierarchy hierarchyDef = ((BLevelDef)parentDef).getHierarchy();
    if (hierarchyDef == null)
    {
      log.fine("isHierarchyViewable: hierarchy def is null");
      return false;
    }

    return hierarchyDef.canViewHierarchy();
  }

  /**
   * Returns hierarchy root level elements.  These elements will have cached children if the
   * hierarchy is cached.  This method works on the server-side only.
   *
   * @param context execution context; its user is not used for getting permissions that determine
   *                which elements to return
   * @return level elements that represent the root of each hierarchy in the service
   */
  public BLevelElem[] getHierarchyRootElems(Context context)
  {
    // server side only
    if (!isRunning() || !isOperational())
    {
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    // return a single element for each HierarchyDef in the HierarchyService
    BHierarchy[] hierarchyDefs = (BHierarchy[])getHierarchyDefs();
    List<BLevelElem> rootElems = new ArrayList<>(hierarchyDefs.length);
    boolean isFox = context != null && context.getFacet("foxSessionId") != null;
    for (BHierarchy hierarchyDef : hierarchyDefs)
    {
      // getChildElems of a BHierarchy level def returns a single BLevelElem that represents
      // the root of the hierarchy
      BLevelElem[] elems = hierarchyDef.getElements(null, context);
      if (elems.length > 0)
      {
        BLevelElem rootElem = elems[0];
        if (isFox)
        {
          // Need to add a dynamic levelDefTypes property so fox clients can short-circuit hierarchy
          // ord evaluation when the last ord name corresponds to a query or relation level def.
          if (hierarchyDef.getCacheStatus().equals(BHierarchyCacheStatus.cached))
          {
            // Do not add the dynamic property to the cached hierarchy element because then it
            // would go out to other non-fox clients as well.
            rootElem = (BLevelElem) rootElem.newCopy(true);
          }

          addLevelDefTypes(rootElem, hierarchyDef);
        }

        rootElems.add(rootElem);
      }
    }

    return rootElems.toArray(EMPTY_LEVEL_ELEM_ARR);
  }

  /**
   * Add a hidden dynamic slot with the type specs of level defs in this hierarchy.  This is used to
   * short-circuit hierarchy resolution in BHierarchyScheme#resolve. Only need this slot when
   * supplying root elems for the fox hierarchy space.
   */
  private static void addLevelDefTypes(BLevelElem root, BHierarchy hierarchyDef)
  {
    BVector levelDefTypes = new BVector();
    for (BLevelDef levelDef : hierarchyDef.getLevelDefCache())
    {
      levelDefTypes.add(null, levelDef.getType().getTypeSpec(), Flags.READONLY);
    }
    root.add(LEVEL_DEF_TYPES_PROP_NAME, levelDefTypes, Flags.READONLY | Flags.HIDDEN);
  }

  /**
   * Retrieve child elements at a point within the hierarchy.  This method works on the
   * server-side only.
   *
   * @param defPath path to the level def for the element for which to retrieve children
   * @param contextParams context parameters that further specify which element to return
   * @param tags tags to apply to the returned element
   * @param context execution context; its user is not used for getting permissions that determine
   *                which elements to return
   * @return child elements for an element within the hierarchy
   *
   * @since Niagara 4.4
   */
  public BLevelElem[] getChildElems(String defPath, BFacets contextParams, BFacets tags, Context context)
  {
    // server side only
    if (!isRunning() || !isOperational())
    {
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    // The first item in the def path is the name of the hierarchy.
    String[] defNames = new SlotPath(defPath).getNames();
    if (defNames.length < 1)
    {
      log.warning(() -> "Def path is empty when trying to retrieve a parent elem");
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    // Use that name to get the corresponding hierarchy def and then get the hierarchy root elem.
    String hierarchyName = defNames[0];
    BHierarchy hierarchyDef = null;
    for (BLevelDef rootDef : getHierarchyDefs())
    {
      if (rootDef.getName().equals(hierarchyName))
      {
        hierarchyDef = (BHierarchy)rootDef;
        break;
      }
    }

    if (hierarchyDef == null)
    {
      log.fine(() -> "Hierarchy def " + hierarchyName + " not found when trying to retrieve child elems");
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    // If the hierarchy is cached, gets the cached root.  Even if not cached, however, this method
    // checks whether the hierarchy is operational and if the user can view it.
    BLevelElem[] hierarchyRoots = hierarchyDef.getElements(null, context);
    if (hierarchyRoots.length < 1)
    {
      log.fine(() -> "Hierarchy root elem could not be retrieved for " + hierarchyName + " when trying to retrieve child elems");
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    if ((Boolean)hierarchyRoots[0].fw(Fw.LEVELELEM_IS_CACHED))
    {
      // Hierarchy is cached.  Use the hierarchy ord to navigate through the cached children to find
      // the parent.
      BLevelElem parent = getCachedParent(hierarchyRoots[0], contextParams);
      return getChildElems(parent, context);
    }
    else
    {
      // Create a dummy parent just to find the child elements.
      BOrd levelDefOrd = BOrd.make("station:|" + getSlotPathOrd() + defPath);
      BLevelDef levelDef = (BLevelDef)levelDefOrd.resolve(this).get();
      BLevelElem parent =  new BLevelElem(levelDef, /* parent */ null, /* name */ "parent",
        /* icon */ BIcon.DEFAULT, contextParams, tags);
      return getChildElems(parent, context);
    }
  }

  /**
   * @param hierarchyRoot cached root hierarchy element
   * @param contextParams context parameters from which the hierarchy ord is obtained
   * @return a level element from a cached hierarchy
   *
   * @since Niagara 4.4
   */
  private static BLevelElem getCachedParent(BLevelElem hierarchyRoot, BFacets contextParams)
  {
    // Pull the hierarchy ord out of the context parameters for walking the cached elements.
    BObject facetValue = contextParams.get(BLevelDef.HIERARCHY_ORD);
    if (!(facetValue instanceof BOrd))
    {
      log.fine("Hierarchy ord missing from context parameters when looking for cached parent");
      return null;
    }

    OrdQuery[] queries = ((BOrd)facetValue).parse();
    if (queries.length < 1 || !"hierarchy".equals(queries[0].getScheme()))
    {
      log.fine(() -> "Missing hierarchy scheme in first ord query when looking for cached parent; " + facetValue);
      return null;
    }

    // Starting at the supplied root elem, walk through the cached children looking for a match to
    // each name in the hierarchy ord query.  Return null if one is missing or the last child if all
    // are found.
    BLevelElem parent = hierarchyRoot;
    String[] hierarchyNames = new HierarchyQuery(queries[0].getBody()).getNames();
    for (int i = 1; i < hierarchyNames.length; ++i)
    {
      String hierarchyName = hierarchyNames[i];
      BLevelElem[] children = (BLevelElem[])parent.fw(Fw.LEVELELEM_GET_CACHED_CHILDREN);
      parent = findElem(hierarchyName, children);
      if (parent == null)
      {
        log.fine(() -> "Could not find " + hierarchyName + " when looking for cached parent; " + facetValue);
        return null;
      }
    }

    return parent;
  }

  /**
   * If the parent has cached children, these are returned.  Otherwise, a level def is used.  If the
   * parent's level def is a {@link BRelationLevelDef} and {@link
   * BRelationLevelDef#getRepeatRelation()} is true, this level def will be used first.  If it does
   * not result in any child elements, the next level def in the hierarchy will be used.  The cached
   * root of a hierarchy can be obtained with {@link #getHierarchyRootElems(Context)}. This method
   * works on the server-side only.
   * <p />
   * This method is package-internal.  There are no checks on whether the user can view the
   * hierarchy that the parent was retrieved from.  Those checks are made in {@link
   * #getHierarchyRootElems(Context)} and an element retrieved from those methods would be passed to
   * this method.  To obtain children with checks on whether the hierarchy can be viewed, use {@link
   * #getHierarchyElems(BLevelElem, BObject, Context)} or {@link #getChildElems(String, BFacets,
   * BFacets, Context)}.
   *
   * @param parent element for which children are being retrieved
   * @param context execution context; its user is not used for getting permissions that determine
   *                which elements to return
   * @return child elements for the specified parent
   *
   * @since Niagara 4.4
   */
  BLevelElem[] getChildElems(BLevelElem parent, Context context)
  {
    // Server side only
    if (parent == null)
    {
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    // If the parent is cached, return its cached children even if empty.
    if ((Boolean)parent.fw(Fw.LEVELELEM_IS_CACHED))
    {
      return (BLevelElem[])parent.fw(Fw.LEVELELEM_GET_CACHED_CHILDREN);
    }

    // If the parent is not part of a cached hierarchy, use the level def to obtain child elems.
    BLevelDef levelDef = parent.getLevelDef();
    if (levelDef == null)
    {
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    // Check for relation level def repeat relations
    BLevelElem[] elems = BLevelDef.EMPTY_LEVEL_ELEMS;
    if (levelDef instanceof BRelationLevelDef && ((BRelationLevelDef)levelDef).getRepeatRelation())
    {
      elems = levelDef.getElements(parent, context);
    }

    if (elems.length > 0)
    {
      return elems;
    }

    // If no elements are returned from the repeat relations of a relation level def, look at
    // the next level def.
    BLevelDef nextDef = levelDef.getNext();
    if (nextDef != null)
    {
      elems = nextDef.getElements(parent, context);
    }

    return elems;
  }

  /**
   * Resolve the given HierarchyQuery to a BLevelElem in a hierarchy tree.
   * <p>
   * Note: This is different that resolving a hierarchy ord as this method as it will always return
   * the BLevelElem rather than the target component.
   * <p>
   * This method was intended to run station-side only and that is being enforced as of Niagara 4.4.
   *
   * @param query HierarchyQuery to resolve
   * @return BLevelElem in hierarchy tree that the given query resolves to
   * @throws UnresolvedException if the query does not resolve to a LevelElem
   * @since Niagara 4.1
   */
  public BLevelElem resolveHierarchyLevelElem(HierarchyQuery query)
    throws UnresolvedException
  {
    // Server side only
    if (!isRunning())
    {
      throw new UnsupportedOperationException("resolveHierarchyLevelElem may only be called from the station");
    }

    BHierarchyService.log.fine(() -> "resolveHierarchyLevelElem: query=" + query);

    // Resolve the BHierarchy instance...
    String[] hierarchyNames = query.getNames();
    BLevelElem parent = null;
    Context context = null;

    for (String hierarchyName : hierarchyNames)
    {
      BHierarchyService.log.fine(() -> "  " + hierarchyName);

      /**
       * Previously, this method called getHierarchyElems. For the reason this was changed, see the
       * comments in {@link BHierarchyScheme#resolve}.
       */
      BLevelElem[] children;
      if (parent == null)
      {
        children = getHierarchyRootElems(context);
      }
      else
      {
        children = getChildElems(parent, context);
      }

      parent = findElem(hierarchyName, children);
      if (parent == null)
      {
        throw new UnresolvedException("Cannot resolve hierarchy elem " + hierarchyName);
      }

      context = parent.getContextParams();
    }

    if (parent != null)
    {
      return parent;
    }

    throw new UnresolvedException("Cannot resolve hierarchy elem " + query);
  }

  public static BHierarchySpace getHierarchySpace(BObject base)
  {
    // If called on the proxy side to retrieve the hierarchy space
    BHierarchySpace space = null;
    try
    {
      space = (BHierarchySpace)(base instanceof BHierarchySpace ?
        base : BOrd.make("hierarchy:").get(base));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return space;
  }

  public static BHierarchyService getHierarchyService(BObject base)
  {
    // If called on the proxy side to retrieve the hierarchy space
    BHierarchyService service = null;
    try
    {
      BOrd ord = BOrd.make("service:hierarchy:HierarchyService");
      service = (BHierarchyService)ord.get(base);
    }
    catch (Exception e)
    {
      BHierarchyService.log.log(Level.SEVERE, e, () -> "Could not get the hierarchy service");
    }
    return service;
  }

  public BHierarchySpace getHierarchySpace()
  {
    return hSpace;
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("navOnly/hierarchyService.png");

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps("HierarchyService");
    out.prop("Licensed for local hierarchy", allowLocalHierarchy);
    out.prop("Licensed for system hierarchy", allowSystemHierarchy);
    out.endProps();

    QueryUtil.spyExecutor(out);

    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("hierarchy");

  // levelDefTypes dynamic property name; set on a hierarchy level elem
  public static final String LEVEL_DEF_TYPES_PROP_NAME = "levelDefTypes";

  private static final BHierarchyScope[] EMPTY_HIERARCHY_SCOPES_ARR = new BHierarchyScope[0];
  private static final BLevelElem[] EMPTY_LEVEL_ELEM_ARR = new BLevelElem[0];

  private BHierarchySpace hSpace;
  private static boolean allowLocalHierarchy;
  private static boolean allowSystemHierarchy;
}
