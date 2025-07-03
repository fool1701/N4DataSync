/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import static com.tridium.hierarchy.BHierarchyCacheStatus.cached;
import static com.tridium.hierarchy.BHierarchyCacheStatus.notCached;
import static com.tridium.hierarchy.BHierarchyCacheStatus.notCachedOnStarted;
import static com.tridium.hierarchy.HierarchyUtil.canCurrentUserViewHierarchy;
import static com.tridium.hierarchy.MakeElemUtil.makeHierarchyElem;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.hierarchy.BHierarchyCacheStatus;
import com.tridium.hierarchy.BHierarchyCachingJob;
import com.tridium.hierarchy.HierarchyCacheBuilder;
import com.tridium.hierarchy.QueryUtil;
import com.tridium.sys.schema.Fw;

/**
 * BHierarchy defines a navigational hierarchy.
 *
 * @author    Blake Puhak
 * @creation  4 Mar 2014
 * @since     Niagara 4.0
 */
@NiagaraType
/*
 Status of the hierarchy. This property should never be set directly.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
/*
 Provides a short message regarding why the hierarchy is in fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 The list of scopes to build this hierarchy against.
 */
@NiagaraProperty(
  name = "scope",
  type = "BHierarchyScopeContainer",
  defaultValue = "new BHierarchyScopeContainer()"
)
/*
 Tags on the hierarchy itself.
 */
@NiagaraProperty(
  name = "tags",
  type = "BHierarchyTags",
  defaultValue = "new BHierarchyTags()"
)
/*
 State of the cache of the hierarchy.
 @since Niagara 4.4
 */
@NiagaraProperty(
  name = "cacheStatus",
  type = "BHierarchyCacheStatus",
  defaultValue = "notCached",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Date/time when the current cache was created.
 @since Niagara 4.4
 */
@NiagaraProperty(
  name = "cacheCreationTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Cache this hierarchy once the station is started (see stationStarted()).
 @since Niagara 4.4
 */
@NiagaraProperty(
  name = "cacheOnStationStarted",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 Creates or replaces the cache for this hierarchy.
 @since Niagara 4.4
 */
@NiagaraAction(
  name = "createCache",
  returnType = "BOrd"
)
/*
 Clears the cache for this hierarchy.
 @since Niagara 4.4
 */
@NiagaraAction(
  name = "clearCache"
)
public final class BHierarchy
  extends BLevelDef
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BHierarchy(2770839415)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Status of the hierarchy. This property should never be set directly.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * Status of the hierarchy. This property should never be set directly.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * Status of the hierarchy. This property should never be set directly.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a short message regarding why the hierarchy is in fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a short message regarding why the hierarchy is in fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a short message regarding why the hierarchy is in fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "scope"

  /**
   * Slot for the {@code scope} property.
   * The list of scopes to build this hierarchy against.
   * @see #getScope
   * @see #setScope
   */
  public static final Property scope = newProperty(0, new BHierarchyScopeContainer(), null);

  /**
   * Get the {@code scope} property.
   * The list of scopes to build this hierarchy against.
   * @see #scope
   */
  public BHierarchyScopeContainer getScope() { return (BHierarchyScopeContainer)get(scope); }

  /**
   * Set the {@code scope} property.
   * The list of scopes to build this hierarchy against.
   * @see #scope
   */
  public void setScope(BHierarchyScopeContainer v) { set(scope, v, null); }

  //endregion Property "scope"

  //region Property "tags"

  /**
   * Slot for the {@code tags} property.
   * Tags on the hierarchy itself.
   * @see #getTags
   * @see #setTags
   */
  public static final Property tags = newProperty(0, new BHierarchyTags(), null);

  /**
   * Get the {@code tags} property.
   * Tags on the hierarchy itself.
   * @see #tags
   */
  public BHierarchyTags getTags() { return (BHierarchyTags)get(tags); }

  /**
   * Set the {@code tags} property.
   * Tags on the hierarchy itself.
   * @see #tags
   */
  public void setTags(BHierarchyTags v) { set(tags, v, null); }

  //endregion Property "tags"

  //region Property "cacheStatus"

  /**
   * Slot for the {@code cacheStatus} property.
   * State of the cache of the hierarchy.
   * @since Niagara 4.4
   * @see #getCacheStatus
   * @see #setCacheStatus
   */
  public static final Property cacheStatus = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, notCached, null);

  /**
   * Get the {@code cacheStatus} property.
   * State of the cache of the hierarchy.
   * @since Niagara 4.4
   * @see #cacheStatus
   */
  public BHierarchyCacheStatus getCacheStatus() { return (BHierarchyCacheStatus)get(cacheStatus); }

  /**
   * Set the {@code cacheStatus} property.
   * State of the cache of the hierarchy.
   * @since Niagara 4.4
   * @see #cacheStatus
   */
  public void setCacheStatus(BHierarchyCacheStatus v) { set(cacheStatus, v, null); }

  //endregion Property "cacheStatus"

  //region Property "cacheCreationTime"

  /**
   * Slot for the {@code cacheCreationTime} property.
   * Date/time when the current cache was created.
   * @since Niagara 4.4
   * @see #getCacheCreationTime
   * @see #setCacheCreationTime
   */
  public static final Property cacheCreationTime = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAbsTime.NULL, null);

  /**
   * Get the {@code cacheCreationTime} property.
   * Date/time when the current cache was created.
   * @since Niagara 4.4
   * @see #cacheCreationTime
   */
  public BAbsTime getCacheCreationTime() { return (BAbsTime)get(cacheCreationTime); }

  /**
   * Set the {@code cacheCreationTime} property.
   * Date/time when the current cache was created.
   * @since Niagara 4.4
   * @see #cacheCreationTime
   */
  public void setCacheCreationTime(BAbsTime v) { set(cacheCreationTime, v, null); }

  //endregion Property "cacheCreationTime"

  //region Property "cacheOnStationStarted"

  /**
   * Slot for the {@code cacheOnStationStarted} property.
   * Cache this hierarchy once the station is started (see stationStarted()).
   * @since Niagara 4.4
   * @see #getCacheOnStationStarted
   * @see #setCacheOnStationStarted
   */
  public static final Property cacheOnStationStarted = newProperty(Flags.DEFAULT_ON_CLONE, false, null);

  /**
   * Get the {@code cacheOnStationStarted} property.
   * Cache this hierarchy once the station is started (see stationStarted()).
   * @since Niagara 4.4
   * @see #cacheOnStationStarted
   */
  public boolean getCacheOnStationStarted() { return getBoolean(cacheOnStationStarted); }

  /**
   * Set the {@code cacheOnStationStarted} property.
   * Cache this hierarchy once the station is started (see stationStarted()).
   * @since Niagara 4.4
   * @see #cacheOnStationStarted
   */
  public void setCacheOnStationStarted(boolean v) { setBoolean(cacheOnStationStarted, v, null); }

  //endregion Property "cacheOnStationStarted"

  //region Action "createCache"

  /**
   * Slot for the {@code createCache} action.
   * Creates or replaces the cache for this hierarchy.
   * @since Niagara 4.4
   * @see #createCache()
   */
  public static final Action createCache = newAction(0, null);

  /**
   * Invoke the {@code createCache} action.
   * Creates or replaces the cache for this hierarchy.
   * @since Niagara 4.4
   * @see #createCache
   */
  public BOrd createCache() { return (BOrd)invoke(createCache, null, null); }

  //endregion Action "createCache"

  //region Action "clearCache"

  /**
   * Slot for the {@code clearCache} action.
   * Clears the cache for this hierarchy.
   * @since Niagara 4.4
   * @see #clearCache()
   */
  public static final Action clearCache = newAction(0, null);

  /**
   * Invoke the {@code clearCache} action.
   * Clears the cache for this hierarchy.
   * @since Niagara 4.4
   * @see #clearCache
   */
  public void clearCache() { invoke(clearCache, null, null); }

  //endregion Action "clearCache"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHierarchy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Called when a component's running state moves to true. Components are started top-down:
   * children after their parent.
   *
   * <p>For a hierarchy component, the license requirements are checked.</p>
   */
  @Override
  public void started()
  {
    try
    {
      BHierarchyService service = (BHierarchyService)Sys.getService(BHierarchyService.TYPE);
      String hierarchyLimitFault = (String)service.fw(Fw.CHECK_LICENSE_LIMIT, "hierarchy.limit", null, null, null);
      if (hierarchyLimitFault != null)
      {
        fatalFault = true;
        setFaultCause(hierarchyLimitFault);
        updateStatus(service);
      }
    }
    catch (ServiceNotFoundException ex)
    {
      // Do nothing if no service
    }
  }

  /**
   * Cache the hierarchy once the station is started if {@link #cacheOnStationStarted} is set and
   * the system property named "niagara.hierarchy.caching.disableOnStationStarted" is not set.
   */
  @Override
  public void stationStarted() throws Exception
  {
    super.stationStarted();

    if (getCacheOnStationStarted())
    {
      if (isCachingDisabledBySystemProp())
      {
        BHierarchyService.log.warning(() -> "Hierarchy " + getName() +
          " was not cached on station started because the system property " +
          DISABLE_CACHING_SYSPROP + " is set to true.");
        setCacheStatus(notCachedOnStarted);
      }
      else if (isCachingDisabledBySystemDbScope())
      {
        BHierarchyService.log.warning(() -> "Hierarchy " + getName() +
          " was not cached on station started because at least one of its scopes includes the system database.");
        setCacheStatus(notCachedOnStarted);
      }
      else if (DISABLE_CACHING_ON_STATION_STARTED)
      {
        BHierarchyService.log.warning(() -> "Hierarchy " + getName() +
          " was not cached on station started because the system property " +
          DISABLE_CACHING_ON_STATION_STARTED_SYSPROP + " is set to true.");
        setCacheStatus(notCachedOnStarted);
      }
      else
      {
        doCreateCache(null);
      }
    }
  }

  /**
   * Recompute the {@link #status} property.
   *
   * @param service hierarchy service to which this hierarchy belongs
   */
  public void updateStatus(BHierarchyService service)
  {
    // compute new status...
    int newStatus = getStatus().getBits();
    // ... fault bit
    if (fatalFault || service.isFault())
    {
      newStatus |= BStatus.FAULT;
    }
    else
    {
      newStatus &= ~BStatus.FAULT;
    }

    // short circuit if nothing has changed since last time
    if (oldStatus == newStatus)
    {
      return;
    }

    setStatus(BStatus.make(newStatus));
    oldStatus = newStatus;
  }

  /**
   * Tests that the hierarchy is neither disabled nor in fault.
   *
   * @return {@code true} if neither disabled nor in fault; {@code false} otherwise
   */
  public boolean isOperational()
  {
    BStatus status = getStatus();
    return !status.isDisabled() && !status.isFault();
  }

  /**
   * Tests whether the specified parent is a legal parent for this component. This method is called
   * during an {@code add()} method.
   *
   * <p>For a hierarchy, the only legal parent is a hierarchy service.</p>
   *
   * @param parent component to which this component is being added
   * @return {@code true} if the parent is an instance of {@link BHierarchyService}; {@code false}
   * otherwise
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BHierarchyService;
  }

  /**
   * Tests that the specified child is a legal child component for this component. This method is
   * called during an {@code add()} method.
   *
   * <p>For a hierarchy, legal children are either level definitions are a hierarchy scope
   * container.</p>
   *
   * @param child component being added to this component
   * @return {@code true} if the child is an instance of {@link BLevelDef} or
   * {@link BHierarchyScopeContainer} ; {@code false}
   * otherwise
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return child instanceof BLevelDef || child instanceof BHierarchyScopeContainer;
  }

  /**
   * Return an array that contains only a root level element for the hierarchy.
   *
   * @param parentElem not used
   * @param context execution context; its user is not used for getting permissions that determine
   * which elements to return
   * @return array with a single level element representing the root of the hierarchy
   */
  @Override
  public BLevelElem[] getElements(BLevelElem parentElem, Context context)
  {
    if (!canViewHierarchy())
    {
      return BLevelDef.EMPTY_LEVEL_ELEMS;
    }

    synchronized (cachedRootLock)
    {
      return new BLevelElem[] { cachedRoot != null ? cachedRoot : makeHierarchyElem(this, context) };
    }
  }

  /**
   * @return true if the hierarchy is operational and the user can view this hierarchy.
   * @since Niagara 4.4
   */
  boolean canViewHierarchy()
  {
    return isOperational() && canCurrentUserViewHierarchy(this);
  }

  /**
   * Returns the hierarchy that this hierarchy belongs to: itself.
   *
   * @return this hierarchy
   */
  @Override
  public BHierarchy getHierarchy()
  {
    return this;
  }

  /**
   * Return {@code null} because the hierarchy is the root of the hierarchy tree.
   *
   * @return {@code null}
   */
  @Override
  public BLevelDef getPrevious()
  {
    return null;
  }

  /**
   * Return the a child level definition or null if this level definition is a leaf. In the case of
   * the hierarchy component, return the first child level definition.
   *
   * @return the first level definition child, if any exist; {@code null} otherwise
   */
  @Override
  public BLevelDef getNext()
  {
    BLevelDef[] children = getChildren(BLevelDef.class);
    if (children.length > 0)
    {
      return children[0];
    }
    return null;
  }

  /**
   * Rebuilds the level def cache (server-side only) once all of the hierarchy's properties have
   * been started.
   * @since Niagara 4.6
   */
  @Override
  public void descendantsStarted()
  {
    rebuildLevelDefCache();
  }

  /**
   * Rebuilds the level def cache (server-side only) if a BLevelDef property is added.
   * @since Niagara 4.6
   */
  @Override
  public void added(Property property, Context context)
  {
    if (isRunning() && property.getType().is(BLevelDef.TYPE))
    {
      rebuildLevelDefCache();
    }
  }

  /**
   * Rebuilds the level def cache (server-side only) if a BLevelDef property is changed via a set
   * method.
   * @since Niagara 4.6
   */
  @Override
  public void changed(Property property, Context context)
  {
    if (isRunning() && property.getType().is(BLevelDef.TYPE))
    {
      rebuildLevelDefCache();
    }
  }

  /**
   * Rebuilds the level def cache (server-side only) if a BLevelDef property has been removed.
   * @since Niagara 4.6
   */
  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    if (isRunning() && property.getType().is(BLevelDef.TYPE))
    {
      rebuildLevelDefCache();
    }
  }

  /**
   * Rebuilds the level def cache (server-side only) when properties have been reordered.  It is
   * difficult to detect that only the order of BLevelDefs have been affected so the cache is
   * rebuilt every time.
   * @since Niagara 4.6
   */
  @Override
  public void reordered(Context context)
  {
    if (isRunning())
    {
      rebuildLevelDefCache();
    }
  }

  private void rebuildLevelDefCache()
  {
    // Only rebuild the levelDef cache if the hierarchy is not cached. Otherwise, there will be a
    // mismatch between the structure of the cached hierarchy and the level def cache used to
    // short-circuit when the last level def is a query or relation def.
    synchronized (cachedRootLock)
    {
      if (cachedRoot == null)
      {
        levelDefCache.set(Collections.unmodifiableList(Arrays.asList(getChildren(BLevelDef.class))));
      }
    }
  }

  /**
   * Returns an unmodifiable cached list of the BLevelDefs in this hierarchy.  Caching avoids
   * rebuilding an array with a call to getChildren every time the BLevelDefs are required.  This
   * cache is rebuilt when BLevelDefs are added or removed and when properties of this hierarchy are
   * reordered.  Cache is populated on the server-side only.
   */
  public List<BLevelDef> getLevelDefCache()
  {
    return levelDefCache.get();
  }

  /**
   * Get the icon for this component.
   * @return component's icon
   */
  @Override
  public BIcon getIcon()
  {
    return icon;
  }
  private static final BIcon icon = BIcon.make("module://icons/x16/r2/tree.png");

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Cache an un-cached hierarchy or replace an existing cache.
   * @since Niagara 4.4
   */
  public BOrd doCreateCache(Context context)
  {
    if (!isRunning() || !isOperational())
    {
      return BOrd.NULL;
    }

    doClearCache();

    // Check if caching is disabled by a system property.
    if (isCachingDisabledBySystemProp())
    {
      throw new LocalizableRuntimeException("hierarchy", DISABLE_CACHING_SYSPROP_MSG_LEX_KEY, new Object[] { DISABLE_CACHING_SYSPROP });
    }

    if (isCachingDisabledBySystemDbScope())
    {
      throw new LocalizableRuntimeException("hierarchy", DISABLE_CACHING_SYSDB_MSG_LEX_KEY, new Object[] { getName() });
    }

    BHierarchyService hierarchyService = (BHierarchyService)Sys.getService(BHierarchyService.TYPE);
    HierarchyCacheBuilder builder = new HierarchyCacheBuilder(this, hierarchyService);
    return new BHierarchyCachingJob(this, builder).submit(context);
  }

  private static boolean isCachingDisabledBySystemProp()
  {
    return AccessController.doPrivileged((PrivilegedAction<Boolean>) () ->
      Boolean.getBoolean(DISABLE_CACHING_SYSPROP));
  }

  private boolean isCachingDisabledBySystemDbScope()
  {
    TypeInfo systemDbTypeInfo = QueryUtil.getSystemDbType();
    if (systemDbTypeInfo == null)
    {
      return false;
    }

    BHierarchyScope[] hierarchyScopes = getScope().getHierarchyScopes();
    for (BHierarchyScope hierarchyScope : hierarchyScopes)
    {
      try
      {
        if (hierarchyScope.getScopeOrd().get().getType().is(systemDbTypeInfo))
        {
          return true;
        }
      }
      catch (Exception e)
      {
        // skip the exception to continue checking other hierarchy scope.
        BHierarchyService.log.log(Level.FINE, e, () -> "Exception while checking the type of a " +
          "hierarchy scope ord (" + hierarchyScope.getScopeOrd() +
          ") for hierarchy " + getName() + '.');
      }
    }

    return false;
  }

  /**
   * Remove an existing cache of this hierarchy.
   * @since Niagara 4.4
   */
  public void doClearCache()
  {
    synchronized (cachedRootLock)
    {
      cachedRoot = null;
      cachedElemCount = 0;
      cacheSizeEstimate = 0;
      setCacheStatus(notCached);
      setCacheCreationTime(BAbsTime.NULL);
	  
	    // The level def cache has been fixed to match the cached hierarchy. Refresh it now to the
      // current set of level defs if they were changed while the hierarchy was cached.
      rebuildLevelDefCache();
    }
  }

////////////////////////////////////////////////////////////////
// Framework access
////////////////////////////////////////////////////////////////

  /**
   * @since Niagara 4.4
   */
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
    case Fw.HIERARCHY_SET_CACHED_ROOT:
      // Need to ensure that setting the levelDefCache does not get overwritten by the
      // rebuildLevelDefCache method if that method calls cachedRoot.get before the set below and
      // that method is interrupted while the steps below take place.  Then, that method's
      // levelDefCache set would overwrite the set below.
      synchronized (cachedRootLock)
      {
        // a: BLevelElem hierarchy root
        cachedRoot = (BLevelElem) a;
        // b: cached elem count
        cachedElemCount = (int) b;
        // c: cache size estimate [bytes]
        cacheSizeEstimate = (long) c;
        // d: array of cached level defs
        levelDefCache.set(Collections.unmodifiableList(Arrays.asList((BLevelDef[]) d)));

        setCacheStatus(cached);
        setCacheCreationTime(BAbsTime.now());
      }

      return null;
    }

    return super.fw(x, a, b, c, d);
  }

///////////////////////////////////////////////////////////
// Spy
///////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out) throws Exception
  {
    if (getCacheStatus().equals(BHierarchyCacheStatus.cached))
    {
      out.startProps();
      out.trTitle("Cache Info", 2);
      out.prop("cached element count", cachedElemCount);
      out.prop("estimated cache size [MB]", cacheSizeEstimate / 1000000.0);
      out.endProps();
    }

    super.spy(out);
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final AtomicReference<List<BLevelDef>> levelDefCache = new AtomicReference<>(Collections.emptyList());

  private int oldStatus;

  private final Object cachedRootLock = new Object();
  private BLevelElem cachedRoot;
  private int cachedElemCount;
  private long cacheSizeEstimate;

  // Checks system property to determine if cacheOnStationStarted should be ignored
  private static final String DISABLE_CACHING_ON_STATION_STARTED_SYSPROP = "niagara.hierarchy.caching.disableOnStationStarted";
  private static final boolean DISABLE_CACHING_ON_STATION_STARTED =
    AccessController.doPrivileged((PrivilegedAction<Boolean>) () ->
      Boolean.getBoolean(DISABLE_CACHING_ON_STATION_STARTED_SYSPROP));

  private static final String DISABLE_CACHING_SYSPROP = "niagara.hierarchy.caching.disabled";
  private static final String DISABLE_CACHING_SYSDB_MSG_LEX_KEY = "hierarchy.caching.disabled.systemdb.message";
  private static final String DISABLE_CACHING_SYSPROP_MSG_LEX_KEY = "hierarchy.caching.disabled.message";
}
