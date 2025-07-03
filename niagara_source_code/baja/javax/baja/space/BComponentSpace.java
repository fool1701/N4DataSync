/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.category.BCategoryMask;
import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.dataRecovery.BIDataRecoverySource;
import javax.baja.dataRecovery.IDataRecoveryRecord;
import javax.baja.naming.BOrd;
import javax.baja.naming.BatchResolve;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.registry.Registry;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.security.PasswordEncodingContext;
import javax.baja.spy.SpyWriter;
import javax.baja.sync.Transaction;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.BIObject;
import javax.baja.sys.BIPropertyContainer;
import javax.baja.sys.BIPropertySpace;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Flags;
import javax.baja.sys.IterableCursor;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeSubscriber;
import javax.baja.tag.BIEntitySpace;
import javax.baja.tag.TagDictionaryService;
import javax.baja.util.LexiconText;

import com.tridium.dataRecovery.BDataRecoveryComponentRecorder;
import com.tridium.sys.Nre;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.station.Station;
import com.tridium.sys.transfer.DeleteOp;
import com.tridium.sys.transfer.IntraCompSpaceMove;
import com.tridium.util.ArrayUtil;
import com.tridium.util.ObjectUtil;

/**
 * BComponentSpace is a space which contains a slot tree of
 * BComponents.
 *
 * @author Brian Frank
 */

@NiagaraType
@AuditableSpace
public class BComponentSpace
    extends BSpace
    implements BIProtected, BIPropertySpace,
    BIDataRecoverySource, BIEntitySpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.space.BComponentSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComponentSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BComponentSpace(String name, LexiconText lexText, BOrd ordInSession)
  {
    super(name, lexText);
    this.ordInSession = ordInSession;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the root BComponent of this space.
   */
  public BComponent getRootComponent()
  {
    return root;
  }

  /**
   * Set the root BComponent of this space.
   */
  public synchronized void setRootComponent(BComponent root)
  {
    this.root = root;
    map.clear();
    if (root != null && root.getComponentSpace() == null)
      ((ComponentSlotMap)root.fw(Fw.SLOT_MAP)).mount(this, null, null);
  }

  /**
   * Return true if the entire component space is readonly.
   */
  public boolean isSpaceReadonly()
  {
    return false;
  }

  /**
   * Get the number of components in this space.
   */
  public int getComponentCount()
  {
    return map.size();
  }

  /**
   * Get an array copy of all the components contained by this space.
   */
  public BComponent[] getAllComponents()
  {
    return map.values().toArray(new BComponent[map.size()]);
  }

  /**
   * Defines the default lease time (in milliseconds) for components managed by this
   * component space.  Allows subclasses to override the default lease
   * time which is 60000 (or 60 seconds).
   *
   * @since Niagara 3.7
   */
  public long getDefaultLeaseTime()
  {
    return 60000L;
  }

////////////////////////////////////////////////////////////////
// Type Subscription
////////////////////////////////////////////////////////////////

  /**
   * Subscribe the provided TypeSubscriber to each of the specified
   * types.
   */
  public void subscribe(Type[] t, TypeSubscriber s)
  {
    // TODO Give component space implementations the appropriate
    // call back?
    // getSubscribeCallbacks().subscribe(t);

    for (Type type : t)
    {
      // See if we already have an array of subscribers for this type
      List<List<TypeSubscriber>> eventSubscribers;
      synchronized (typeSubscriptionMap)
      {
        typeSubscriptionMap.putIfAbsent(type, new ArrayList<>(Collections.nCopies(COMPONENT_EVENT_COUNT, null)));
        eventSubscribers = typeSubscriptionMap.get(type);
      }
      synchronized (eventSubscribers)
      {
        // If our subscriber isn't in the list, add it for each event
        // it subscribes to
        int bits = s.getMask().getBits();
        for (int j = 0; j < COMPONENT_EVENT_COUNT; j++)
        {
          if (((bits >> j) & 0x1) != 0)
          {
            if (eventSubscribers.get(j) == null)
              eventSubscribers.set(j, new ArrayList<>(2));
            if (!eventSubscribers.get(j).contains(s))
            {
              eventSubscribers.get(j).add(s);
            }
          }
        }
      }
    }
  }

  /**
   * Unsubscribe the provided TypeSubscriber from each of the specified
   * types.
   */
  public void unsubscribe(Type[] t, TypeSubscriber s)
  {
    // TODO Give component space implementations the appropriate
    // call back?
    // getSubscribeCallbacks().unsubscribe(t);

    for (Type type : t)
    {
      List<List<TypeSubscriber>> eventSubscribers = typeSubscriptionMap.get(type);
      if (eventSubscribers != null)
      {
        synchronized (eventSubscribers)
        {
          for (int j = 0; j < eventSubscribers.size(); j++)
          {
            if (eventSubscribers.get(j) != null)
            {
              eventSubscribers.get(j).remove(s);
              if (eventSubscribers.get(j).isEmpty())
                eventSubscribers.set(j, null);
            }
          }
        }
      }
    }
  }

  /**
   * Update the provided TypeSubscriber's subscription to each of
   * the specified types.
   */
  public void updateSubscription(Type[] t, TypeSubscriber s)
  {
    // TODO Give component space implementations the appropriate
    // call back?
    // getSubscribeCallbacks().updateSubscription(t);

    for (Type type : t)
    {
      // See if we already have an array of subscribers for this type
      List<List<TypeSubscriber>> eventSubscribers;
      synchronized (typeSubscriptionMap)
      {
        typeSubscriptionMap.putIfAbsent(type, new ArrayList<>(Collections.nCopies(COMPONENT_EVENT_COUNT, null)));
        eventSubscribers = typeSubscriptionMap.get(type);
      }
      synchronized (eventSubscribers)
      {
        int bits = s.getMask().getBits();
        for (int j = 0; j < eventSubscribers.size(); j++)
        {
          if (((bits >> j) & 0x1) != 0)
          {
            // Make sure we are subscribed
            if (eventSubscribers.get(j) == null)
              eventSubscribers.set(j, new ArrayList<>(2));
            if (!eventSubscribers.get(j).contains(s))
            {
              eventSubscribers.get(j).add(s);
            }
          }
          else
          {
            // Make sure we are unsubscribed
            if (eventSubscribers.get(j) != null)
            {
              eventSubscribers.get(j).remove(s);
              if (eventSubscribers.get(j).isEmpty())
                eventSubscribers.set(j, null);
            }
          }
        }
      }
    }
  }

  /**
   * Is the specified type currently subscribed to by a
   * TypeSubscriber?
   */
  public boolean isSubscribed(Type t)
  {
    if (t == null)
      return false;
    //TODO how can we make this use .is?
    if (typeSubscriptionMap.containsKey(t))
    {
      for (List<TypeSubscriber> eventSubscriber : typeSubscriptionMap.get(t))
        if (eventSubscriber != null && !eventSubscriber.isEmpty())
          return true;
    }
    return isSubscribed(t.getSuperType());
  }

  /**
   * Is the specified type currently subscribed to by a
   * TypeSubscriber for the specified component event id
   */
  public boolean isSubscribed(Type t, int componentEventId)
  {
    if (t == null)
      return false;

    if (componentEventId == -1)
    {
      // Invalid component event id, fail fast
      return false;
    }

    if (!typeSubscriptionMap.containsKey(t))
    {
      // no one subscribed to this type
      return isSubscribed(t.getSuperType(), componentEventId);
    }

    List<List<TypeSubscriber>> eventSubscribers = typeSubscriptionMap.get(t);
    // no one subscribed to this component event id
    return !(eventSubscribers.get(componentEventId) == null ||
        eventSubscribers.get(componentEventId).isEmpty()) ||
        isSubscribed(t.getSuperType(), componentEventId);
// there is one or more subscribers
  }

  /**
   * Provide the component space with an event that should be
   * sent to the subscribed TypeSubscribers.
   */
  public void event(BComponentEvent event)
  {
    event(event, event.getSourceComponent().getType());
  }

  private void event(BComponentEvent event, Type type)
  {
    if (type == null || type.getModule().getModuleName().equals("event"))
      return;
    List<List<TypeSubscriber>> eventSubscribers = typeSubscriptionMap.get(type);
    if (eventSubscribers != null)
    {
      synchronized (eventSubscribers)
      {
        if (eventSubscribers.get(event.getId()) != null && !eventSubscribers.get(event.getId()).isEmpty())
        {
          for (int i = 0; i < eventSubscribers.get(event.getId()).size(); i++)
          {
            eventSubscribers.get(event.getId()).get(i).event(event);
          }
        }
      }
    }
    event(event, type.getSuperType());
  }

////////////////////////////////////////////////////////////////
// BSpace
////////////////////////////////////////////////////////////////

  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * Get the load callbacks for this space.
   */
  public LoadCallbacks getLoadCallbacks()
  {
    return loadCallbacks;
  }

  /**
   * Set the load callbacks for this space.
   */
  public void setLoadCallbacks(LoadCallbacks loadCallbacks)
  {
    this.loadCallbacks = loadCallbacks;
  }

  /**
   * Get the trap callbacks for this space.
   */
  public TrapCallbacks getTrapCallbacks()
  {
    return trapCallbacks;
  }

  /**
   * Set the trap callbacks for this space.
   */
  public void setTrapCallbacks(TrapCallbacks trapCallbacks)
  {
    this.trapCallbacks = trapCallbacks;
  }

  /**
   * Get the trap callbacks for this space.
   */
  public SubscribeCallbacks getSubscribeCallbacks()
  {
    return subscribeCallbacks;
  }

  /**
   * Set the trap callbacks for this space.
   */
  public void setSubscribeCallbacks(SubscribeCallbacks subscribeCallbacks)
  {
    this.subscribeCallbacks = subscribeCallbacks;
  }


////////////////////////////////////////////////////////////////
// Misc
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>getSubscribeCallbacks().update(c, depth)</code>.
   */
  public final void update(BComponent c, int depth)
  {
    getSubscribeCallbacks().update(c, depth);
  }

  /**
   * Convenience for <code>newTransaction(null)</code>.
   */
  public final Transaction newTransaction()
  {
    return newTransaction(null);
  }

  /**
   * Get a transaction to use for this space.  This transaction
   * may be used as the context for BComponent modifications
   * to buffer up the changes.  When ready to apply the changes
   * use the <code>Transaction.commit()</code>.
   */
  public Transaction newTransaction(Context cx)
  {
    return new Transaction(this, getEncodingContext(cx))
    {
    };
  }

  /**
   * @since Niagara 4.0
   */
  public Context getEncodingContext(Context base)
  {
    // TODO: what's the correct default for:
    //  - BOrionSpace
    //  - BFoxOrionSpace (maybe BOrionSpace takes care of it)
    return PasswordEncodingContext.updateForNone(base);
  }

  /**
   * This callback is invoked when any modification is made on
   * the specified component.  This method is a convenient hook
   * to maintain a dirty flag.
   * <p>
   * Default implementation does nothing.
   */
  public void modified(BComponent c, Context context)
  {
  }

  /**
   * This is a callback for BComponent.childParented().
   * <p>
   * Default implementation does nothing.
   */
  public void childParented(BComponent c, Property property, BValue newChild, Context context)
  {
  }

  /**
   * This is a callback for BComponent.childUnparented().
   * <p>
   * Default implementation does nothing.
   */
  public void childUnparented(BComponent c, Property property, BValue oldChild, Context context)
  {
  }

  /**
   * This method indicates if callbacks which are normally
   * fired directly on the component itself should be
   * suppressed or fired as normal.
   * <p>
   * Default is to return true.
   */
  public boolean fireDirectCallbacks()
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Child Management
////////////////////////////////////////////////////////////////

  /**
   * Return true if this a proxy component space.  Proxy
   * component spaces never generate handles themselves.
   * Proxy spaces should subclass BProxyComponentSpace.
   */
  public boolean isProxyComponentSpace()
  {
    return false;
  }

  /**
   * If this is a proxy component space, then this performs
   * a sync with the master space.  Otherwise this is a no op.
   */
  public void sync()
      throws Exception
  {
  }

  /**
   * Convenience for <code>findByHandle(handle, true)</code>.
   */
  public final BComponent findByHandle(Object handle)
  {
    return findByHandle(handle, true);
  }

  /**
   * Get a BComponent within this space by handle,
   * or return null if not found.
   */
  public BComponent findByHandle(Object handle, boolean autoLoad)
  {
    return doFindByHandle(handle, autoLoad);
  }

  /**
   * Allows proxy component space subclasses to override the behavior
   * of findByHandle.
   *
   * @since Niagara 3.7
   */
  protected BComponent doFindByHandle(Object handle, boolean autoLoad)
  {
    return map.get(handle);
  }

  /**
   * Get a BComponent within this space by handle,
   * or throw UnresolvedException.
   */
  public final BComponent resolveByHandle(Object handle)
  {
    BComponent c = findByHandle(handle);
    if (c != null) return c;
    throw new UnresolvedException(String.valueOf(handle));
  }

  /**
   * Map a handle to a slot path.  Return null if handle
   * does not identify a valid component in this space.
   */
  public SlotPath handleToSlotPath(Object handle)
  {
    BComponent c = map.get(handle);
    if (c == null) return null;
    return c.getSlotPath();
  }

  /**
   * Map an array of handles to an array of SlotPaths.
   * Any handle in the array which does not identify a
   * valid component results in null.
   */
  public SlotPath[] handlesToSlotPaths(Object[] handles)
  {
    SlotPath[] paths = new SlotPath[handles.length];
    for (int i = 0; i < handles.length; ++i)
      paths[i] = handleToSlotPath(handles[i]);
    return paths;
  }

  /**
   * Get an Iterator for all components in the space. This includes
   * the root of the componenet tree.
   *
   * @return Returns an Iterator of all components in the space.
   * The order of the returned components is unpredictable and
   * unrelated to the component tree structure.
   * @since Niagara 4.0
   */
  public Iterator<BComponent> iterateAllComponents()
  {
    return map.values().iterator();
  }

////////////////////////////////////////////////////////////////
// MixIn
////////////////////////////////////////////////////////////////

  /**
   * Get the list of MixIn types which are currently enabled
   * on this component space.  This may be modified using the
   * <code>enableMixIn()</code> and <code>disableMixIn()</code>
   * methods.
   */
  public Type[] getEnabledMixIns()
  {
    return mixIns.clone();
  }

  /**
   * Enable the specified MixIn type.  This call will automatically
   * add an instance of the MixIn for all the components in this
   * space which the MixIn is an agent on.
   */
  public synchronized void enableMixIn(Type mixInType)
  {
    // cannot call on a proxy space
    if (isProxyComponentSpace())
      throw new IllegalStateException("Cannot call on proxy space");

    // verify type implements baja:IMixIn
    if (!mixInType.is(BIMixIn.TYPE))
      throw new IllegalArgumentException(mixInType.toString() + " does not implement baja:IMixIn");

    // short circuit if already added
    for (Type mixIn : mixIns)
    {
      if (mixIn == mixInType) return;
    }

    // add to array
    mixIns = ArrayUtil.addOne(mixIns, mixInType);

    // update all my components
    updateMixIns();

    // if this is the station space, then broadcase for remote sync
    if (Sys.getStation() == root)
      Station.broadcastStationMixIns();
  }

  /**
   * Disable the specified MixIn type in the component space.  This
   * call prevents the MixIn from being added to new components, but
   * does not effect any existing components currently containing
   * the MixIn.
   */
  public synchronized void disableMixIn(Type mixInType)
  {
    // cannot call on a proxy space
    if (isProxyComponentSpace())
      throw new IllegalStateException("Cannot call on proxy space");

    // find it
    int index = -1;
    for (int i = 0; i < mixIns.length; ++i)
      if (mixIns[i] == mixInType)
      {
        index = i;
        break;
      }
    if (index < 0) return;

    // remove it from array
    mixIns = ArrayUtil.removeOne(mixIns, index);

    // if this is the station space, then broadcase for remote sync
    if (Sys.getStation() == root)
      Station.broadcastStationMixIns();
  }

  /**
   * Resan all the components currently mapped into
   * this space, and check if MixIns need to be added.
   */
  private synchronized void updateMixIns()
  {
    // don't process if in hold mode (used for station bootstrap)
    if (holdMixInUpdates) return;

    long t1 = Clock.ticks();

    // cannot call on a proxy space
    if (isProxyComponentSpace())
      throw new IllegalStateException("Cannot call on proxy space");

    // walk the map using an offline copy (since new MixIns
    // that get added will be getting inserted into the map)
    BComponent[] comps = new BComponent[map.size()];
    map.values().toArray(comps);
    for (BComponent comp : comps)
      updateMixIns(comp);

    long t2 = Clock.ticks();
    mixInLog.info("Updated [" + (t2 - t1) + "ms]");
  }

  /**
   * Resan the specified component and check if MixIns
   * need to be added.
   */
  private synchronized void updateMixIns(BComponent c)
  {
    // don't process if in hold mode (used for station bootstrap)
    if (holdMixInUpdates) return;

    // cache variables
    TypeInfo cType = c.getType().getTypeInfo();
    Registry reg = Sys.getRegistry();

    // check each mix in
    for (Type mixIn : mixIns)
    {
      if (reg.isAgent(mixIn.getTypeInfo(), cType))
        addMixInIfNeeded(c, mixIn);
    }
  }

  /**
   * If the specified component doesn't have an instance of the
   * given MixIn, then automatically add a new instance.
   */
  private void addMixInIfNeeded(BComponent c, Type mixInType)
  {
    try
    {
      // short circuit if the component already has the mix in
      if (c.getMixIn(mixInType) != null) return;

      // trace
      if (mixInLog.isLoggable(Level.FINE))
        mixInLog.fine("Add " + mixInType + " to " + c.toPathString());

      // create a new instance of the mix in
      BValue value = (BValue)mixInType.getInstance();

      // add to the component as a dynamic slot
      c.add(mixInType.toString().replace(':', '_'), value);
    }
    catch (Exception e)
    {
      mixInLog.log(Level.SEVERE, "Cannot add mix in " + mixInType + " to " + c.toPathString(), e);
    }
  }

  /**
   * This is a private fw() call to hold and release mix in
   * updates.  We always perform a scan on release if MixIns
   * have been registered.
   */
  private synchronized void setHoldMixInUpdates(boolean hold)
  {
    holdMixInUpdates = hold;
    if (!hold && mixIns.length > 0) updateMixIns();
  }

////////////////////////////////////////////////////////////////
// ICategorizble
////////////////////////////////////////////////////////////////

  /**
   * Return the root's category mask.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    BCategoryMask mask = root.getCategoryMask();
    if (mask == null || mask.isNull()) return BCategoryMask.make("1");
    return mask;
  }

  /**
   * Return the root's category mask.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return getCategoryMask();
  }

////////////////////////////////////////////////////////////////
// IProtected
////////////////////////////////////////////////////////////////

  @Override
  public BPermissions getPermissions(Context cx)
  {
    if (cx != null && cx.getUser() != null)
      return cx.getUser().getPermissionsFor(this);
    else
      return BPermissions.all;
  }

  @Override
  public boolean canRead(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorRead();
  }

  @Override
  public boolean canWrite(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorWrite();
  }

  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorInvoke();
  }


////////////////////////////////////////////////////////////////
// IPropertySpace
////////////////////////////////////////////////////////////////

  /**
   * Find the objects with a property with the specified name.
   *
   * @param objectType   The common type of the objects to return in the result.
   *                     If null, the result is not filtered by object type.
   * @param baseOrd      The base ord from which to start
   *                     searching (only objects that are descendants of this base
   *                     will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param cx           The context for the search.
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, Context cx)
  {
    return findObjects(objectType, baseOrd, propertyName, (Type)null, cx);
  }

  /**
   * Find the objects with a property with the specified name and type.
   *
   * @param objectType   The common type of the objects to return in the result.
   *                     If null, the result is not filtered by object type.
   * @param baseOrd      The base ord from which to start
   *                     searching (only objects that are descendants of this base
   *                     will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param propertyType The type of the property to search for.  If null,
   *                     any types will be included that have the given propertyName.
   * @param cx           The context for the search.
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, Type propertyType, Context cx)
  {
    return new ContainerCursor(findContainers(objectType, baseOrd, cx), propertyName, propertyType, cx);
  }

  /**
   * Find the objects with the specified property name and value.
   *
   * @param objectType    The common type of the objects to return in the result.
   *                      If null, the result is not filtered by object type.
   * @param baseOrd       The base ord from which to start
   *                      searching (only objects that are descendants of this base
   *                      will be included in the result).  If null, the search will include the entire space.
   * @param propertyName  The name of the property to search for.
   * @param propertyValue The value of the property to search for.
   * @param cx            The context for the search.
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, BValue propertyValue, Context cx)
  {
    return new ContainerCursor(findContainers(objectType, baseOrd, cx), propertyName, propertyValue, cx);
  }

  /**
   * Find the distinct values for the specified property name within the space.
   *
   * @param objectType   The common type of the objects to return in the result.
   *                     If null, the result is not filtered by object type.
   * @param baseOrd      The base ord from which to start
   *                     searching (only objects that are descendants of this base
   *                     will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param cx           The context for the search.
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findDistinctValues(Type objectType, BOrd baseOrd, String propertyName, Context cx)
  {
    return findDistinctValues(objectType, baseOrd, propertyName, null, cx);
  }

  /**
   * Find the distinct values for the specified property name and type within the space.
   *
   * @param objectType   The common type of the objects to return in the result.
   *                     If null, the result is not filtered by object type.
   * @param baseOrd      The base ord from which to start
   *                     searching (only objects that are descendants of this base
   *                     will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param propertyType The type of the property to search for.  If null,
   *                     any types will be included that have the given propertyName.
   * @param cx           The context for the search.
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findDistinctValues(Type objectType, BOrd baseOrd, String propertyName, Type propertyType, Context cx)
  {
    return new DistinctPropertyCursor<>(findContainers(objectType, baseOrd, cx), propertyName, propertyType, cx);
  }

  /**
   * Index the specified property.  Indexed properties should be more efficient to search.
   * Implementation of indexing is optional.
   *
   * @param propertyName The name of the property to index.
   * @param cx           The context for the operation.
   * @return Returns true if the requested index was created, false otherwise.
   * @since Niagara 3.5
   */
  @Override
  public boolean addIndex(String propertyName, Context cx)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Remove the index for the specified property.
   *
   * @param propertyName The name of the property that should no longer be indexed.
   * @param cx           The context for the operation.
   * @since Niagara 3.5
   */
  @Override
  public void removeIndex(String propertyName, Context cx)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Find all the BIPropertyContainers of the given object type within the given
   * base Ord.
   *
   * @since Niagara 3.5
   */
  private Cursor<BObject> findContainers(Type objectType, BOrd baseOrd, Context cx)
  {
    // If the type is not provided, default to searching all BComponents in
    // the space.
    Type t = (objectType != null) ? objectType : BComponent.TYPE;
    boolean useNavOrd = isProxyComponentSpace() && t.is(BComponent.TYPE);
    StringBuilder sb = new StringBuilder();
    sb.append("bql:select ");
    if (useNavOrd) sb.append("navOrd");
    else sb.append("*");
    sb.append(" from ").append(t.toString());

    BOrd query;
    if ((baseOrd == null) || (baseOrd.isNull()))
      query = BOrd.make(getNavOrd(), sb.toString());
    else
      query = BOrd.make(baseOrd, sb.toString());

    @SuppressWarnings("unchecked")
    BITable<BObject> table = (BITable<BObject>)query.resolve(this).get();
    Column col = table.getColumns().get(0);
    TableCursor<BObject> c = table.cursor();

    if (!useNavOrd) return c;

    Array<BOrd> arr = new Array<>(BOrd.class);
    while (c.next())
    {
      arr.add(BOrd.make(c.cell(col).toString(null)).relativizeToSession());
    }

    BOrd[] ords = arr.trim();

    // for the proxy space case, preemptively resolve all the ords.
    BatchResolve targets = (new BatchResolve(ords)).resolve(this);
    BComponent[] comps = targets.getTargetComponents();

    // Batch lease so child slot values are up to date
    BComponent.lease(comps, 0);

    return new ArrayCursor(comps, cx);
  }


////////////////////////////////////////////////////////////////
// ArrayCursor
////////////////////////////////////////////////////////////////

  /**
   * Return a Cursor that simply wraps an array of BObjects.
   *
   * @since Niagara 3.5
   */
  static class ArrayCursor implements IterableCursor<BObject>
  {
    ArrayCursor(BObject[] a, Context cx)
    {
      this.a = a;
      this.cx = cx;
      current = -1;
    }

    @Override
    public void close()
    {
      current = a.length;
    }

    /**
     * Get the current element.
     */
    @Override
    public BObject get()
    {
      return a[current];
    }

    /**
     * Get the context for this cursor.
     */
    @Override
    public Context getContext()
    {
      return cx;
    }

    /**
     * Advance to the next object.
     */
    @Override
    public boolean next()
    {
      if (current < a.length)
        current++;
      return current != a.length;
    }

    /**
     * Advance to the next object that is an instance of the specified class.
     */
    public boolean next(Class<?> cls)
    {
      while (next())
      {
        BObject o = get();
        if (cls.isInstance(o))
          return true;
      }

      return false;
    }

    /**
     * Advance to the next object that is an instance of BComponent.
     */
    public boolean nextComponent()
    {
      while (next())
      {
        if (get() instanceof BComponent)
          return true;
      }

      return false;
    }

    private final BObject[] a;
    private final Context cx;
    private int current;
  }


////////////////////////////////////////////////////////////////
// ContainerCursor
////////////////////////////////////////////////////////////////

  /**
   * A cursor of BIPropertyContainers that have the given property name and type.
   *
   * @since Niagara 3.5
   */
  static class ContainerCursor implements IterableCursor<BIPropertyContainer>
  {
    ContainerCursor(Cursor<? extends BIObject> iterator, String propertyName, Type propertyType, Context cx)
    {
      this.iterator = iterator;
      this.propertyName = propertyName;
      this.propertyType = propertyType;
      this.cx = cx;
    }

    ContainerCursor(Cursor<? extends BIObject> iterator, String propertyName, BValue propertyValue, Context cx)
    {
      this(iterator, propertyName, propertyValue.getType(), cx);
      this.propertyValue = propertyValue;
    }

    @Override
    public void close()
    {
      iterator.close();
    }

    /**
     * If there is a context associated with the Cursor, then
     * return the Context instance.  Otherwise return null.
     */
    @Override
    public Context getContext()
    {
      return cx;
    }

    /**
     * The cursor is initially placed before the first slot.
     * Calling next() advances the cursor to the next slot, and
     * returns true if it is positioned on a valid slot, or
     * false if the cursor has reached the end of the iteration.
     */
    @Override
    public boolean next()
    {
      return next(null);
    }

    /**
     * Like next(), but the cursor advances to the next object
     * that is a BComponent.
     */
    public boolean nextComponent()
    {
      return next(BComponent.class);
    }

    /**
     * Like next(), but the cursor advances to the next object
     * that is an instance of the specified class.
     */
    public boolean next(Class<?> cls)
    {
      while (iterator.next())
      {
        BIObject comp = iterator.get();
        if ((comp instanceof BIPropertyContainer) &&
            ((cls == null) || (cls.isInstance(comp))))
        {

          BValue val = ((BIPropertyContainer)comp).get(propertyName);
          if (val == null) continue;

          if (((propertyType == null) || (val.getType().is(propertyType))) &&
              ((propertyValue == null) || (val.equals(propertyValue))))
          {
            current = (BIPropertyContainer)comp;
            return true;
          }
        }
      }

      return false;
    }

    /**
     * Get the object at the current cursor position.
     */
    @Override
    public BIPropertyContainer get()
    {
      return current;
    }

    Cursor<? extends BIObject> iterator;
    String propertyName;
    Type propertyType = null;
    BValue propertyValue = null;
    Context cx;
    BIPropertyContainer current = null;
  }


////////////////////////////////////////////////////////////////
// DistinctPropertyCursor
////////////////////////////////////////////////////////////////

  /**
   * A cursor of distinct values with the given property name.
   *
   * @since Niagara 3.5
   */
  static class DistinctPropertyCursor<T extends BObject> implements IterableCursor<T>
  {
    DistinctPropertyCursor(Cursor<? extends BIObject> iterator, String propertyName, Type propertyType, Context cx)
    {
      this.iterator = iterator;
      this.propertyName = propertyName;
      this.propertyType = propertyType;
      this.cx = cx;
    }

    @Override
    public void close()
    {
      iterator.close();
    }

    /**
     * If there is a context associated with the Cursor, then
     * return the Context instance.  Otherwise return null.
     */
    @Override
    public Context getContext()
    {
      return cx;
    }

    /**
     * The cursor is initially placed before the first slot.
     * Calling next() advances the cursor to the next slot, and
     * returns true if it is positioned on a valid slot, or
     * false if the cursor has reached the end of the iteration.
     */
    @Override
    public boolean next()
    {
      return next(null);
    }

    /**
     * Like next(), but the cursor advances to the next object
     * that is a BComponent.
     */
    public boolean nextComponent()
    {
      return next(BComponent.class);
    }

    /**
     * Like next(), but the cursor advances to the next object
     * that is an instance of the specified class.
     */
    public boolean next(Class<?> cls)
    {
      while (iterator.next())
      {
        BIObject comp = iterator.get();
        if (comp instanceof BIPropertyContainer)
        {
          BValue val = ((BIPropertyContainer)comp).get(propertyName);
          if (val == null) continue;

          if (((cls == null) || (cls.isInstance(val))) &&
              ((propertyType == null) || (val.getType().is(propertyType))) &&
              (!propertyValues.contains(val)))
          {
            propertyValues.add(val);
            @SuppressWarnings("unchecked")
            T temp = (T)val;
            current = temp;
            return true;
          }
        }
      }

      return false;
    }

    /**
     * Get the object at the current cursor position.
     */
    @Override
    public T get()
    {
      return current;
    }

    Cursor<? extends BIObject> iterator;
    String propertyName;
    Type propertyType = null;
    Array<BValue> propertyValues = new Array<>(BValue.class);
    Context cx;
    T current = null;
  }


////////////////////////////////////////////////////////////////
// Hidden Support API
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.MOUNT:
        mount(a);
        return null;
      case Fw.UNMOUNT:
        unmount(a);
        return null;
      case Fw.GENERATE_HANDLES:
        return generateHandles((Integer)a);
      case Fw.GENERATE_UNIQUE_NAME:
        return generateUniqueName((String)a, (ObjectUtil.NameContainer)b);
      case Fw.ENSURE_LOADED:
        return null;
      case Fw.HOLD_MIX_IN_UPDATES:
        setHoldMixInUpdates((Boolean)a);
        return null;
      case Fw.MAKE_DELETE_OP:
        return DeleteOp.make((Mark)a, (Context)b);
      case Fw.DELETE_PROPS:
        return ((DeleteOp)a).doDelete();
      case Fw.UNDELETE_PROPS:
        return ((DeleteOp)a).doUndelete();
      case Fw.MAKE_INTRA_MOVE:
        return new IntraCompSpaceMove();
      case Fw.INIT_DATA_RECOVERY_RESTORE:
        dataRecoveryRestorer = (BDataRecoveryComponentRecorder)a;
        if (dataRecoveryRestorer != null)
        {
          nextHandleOnCriticalStart = nextHandle;
          reassignedHandles = new ArrayList<>();
        }
        else
          reassignedHandles = null;
        return null;
      case Fw.GET_COMPONENT:
        return map.get(a);
    }
    return super.fw(x, a, b, c, d);
  }

  protected synchronized void mount(Object support)
  {
    // mounting is driven thru ComponentSlotMap, and that
    // code calls here to finish the operation

    ComponentSlotMap c = (ComponentSlotMap)support;
    BComponent comp = (BComponent)c.getInstance();

    // recompute next handle in case the specified
    // graph contains handles
    nextHandle = solveForNextHandle(comp, nextHandle);

    if (c.getSpace() != null)
      throw new IllegalStateException("Component already mounted: " + c.getInstance());

    // the handle may have already been set if we
    // deserializing an exsiting component graph
    Object handle = c.getHandle();
    if (handle == null || handle.toString().startsWith(ComponentSlotMap.SWIZZLE_PREFIX))
      handle = generateHandle();
    c.setHandle(handle);

    // stash in lookup map
    BComponent dup = map.get(handle);
    if (dup != null)
    {
      // Special case: If we're in the middle of restoring data recovery, it is possible that frozen/transient
      // components in config.bog (ie. NullProxyExts) or dynamic PlatformService components could have taken a
      // handle that is used in the data recovery restore.  In such cases, I want to reassign the handle for
      // the frozen/transient component or PlatformService component because the data recovery restore must be
      // able to keep its handles (in case it has any links, etc.).
      boolean reassignHandle = false;
      try
      {
        if (dataRecoveryRestorer != null)
        {
          long h = Long.parseLong((String)handle, 16);
          if ((h < nextHandleOnCriticalStart) || (reassignedHandles.contains(handle)))
          {
            // We want to reassign the handle if it's a NullProxyExt,
            // or it is a duplicate component set() event,
            // or if it is contained within a transient ancestor
            reassignHandle = (dup.getType().getTypeInfo() == nullProxyExt);

            if (!reassignHandle)
            {
              BComplex parent = dup.getParent();
              Property prop = dup.getPropertyInParent();
              Property newProp = c.getPropertyInParent();

              // Check for a component property set() that is a duplicate event (ie. the event
              // occurred during a save, so it was kept in data recovery but actually did make
              // it in the saved bog)
              if ((parent == c.getParent()) &&
                  ((prop != null) && (newProp != null) && (prop.equals(newProp))))
                reassignHandle = true;
              else
              { // Check for transient ancestor
                while ((parent != null) && (prop != null))
                {
                  int pflags = parent.getFlags(prop);
                  reassignHandle = ((pflags & Flags.TRANSIENT) != 0);
                  if (reassignHandle) break;

                  prop = parent.getPropertyInParent();
                  parent = parent.getParent();
                }
              }
            }

            if (reassignHandle)
            {
              // Reassign the handle here and then assign the original handle to the data recovery event's component (comp).
              Object newHandle = generateHandle();

              // store new handle on the duplicate component
              ((ComponentSlotMap)dup.fw(Fw.SLOT_MAP)).setHandle(newHandle);

              // update the map
              map.put(newHandle, dup);

              // keep a record of reassigned handles in case they are seen again in
              // subsequent data recovery events
              reassignedHandles.remove(handle);
              reassignedHandles.add(newHandle);

              try
              { // Log a record of the handle reassignment
                if (BDataRecoveryComponentRecorder.LOG.isLoggable(Level.FINE))
                {
                  BDataRecoveryComponentRecorder.LOG.fine("Reassigned handle for " + dup.toPathString() + ", old=" +
                      handle + ", new=" + newHandle + " due to conflict with data recovery restore for component " + comp.toPathString());
                }
              }
              catch (Throwable ignored)
              {
              }
            }
          }
        }
      }
      catch (Throwable t)
      {
        t.printStackTrace();
        throw new IllegalStateException("Duplicate handle: " + handle + " " + dup.toDebugString() + " ; " + comp.toDebugString());
      }

      /*dumpMap();*/
      if (!reassignHandle)
        throw new IllegalStateException("Duplicate handle: " + handle + " " + dup.toDebugString() + " ; " + comp.toDebugString());
    }
    map.put(handle, comp);

    // check for MixIns
    updateMixIns(comp);
  }

  protected synchronized void unmount(Object support)
  {
    // unmounting is driven thru ComponentSlotMap, and that
    // code calls here to finish the operation

    ComponentSlotMap c = (ComponentSlotMap)support;

    if (c.getSpace() != this)
      throw new IllegalStateException("Component not mounted: " + c);

    // remove from lookup map
    map.remove(c.getHandle());

    // leave the handle set so that move operations
    // work as expected (brian 30 jan 04)
    // c.setHandle(null);
  }

  /**
   * Generate a list of unique handle.
   */
  synchronized Object[] generateHandles(int count)
  {
    Object[] handles = new Object[count];
    for (int i = 0; i < count; ++i)
      handles[i] = generateHandle();
    return handles;
  }

  /**
   * Generate a new unique handle.
   */
  synchronized Object generateHandle()
  {
    if (isProxyComponentSpace()) throw new IllegalStateException("generateHandle in proxy space");
    return Long.toHexString(nextHandle++);
  }

  /**
   * Generate a name based on the defName that is guarenteed
   * to be unique within the container.
   */
  String generateUniqueName(String defName, ObjectUtil.NameContainer container)
  {
    return ObjectUtil.generateUniqueSlotName(defName, container);
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  @Override
  public boolean hasNavChildren()
  {
    return root != null;
  }

  @Override
  public BINavNode getNavChild(String name)
  {
    if (root == null) return null;
    return root.getNavChild(name);
  }

  @Override
  public BINavNode[] getNavChildren()
  {
    if (root == null) return new BINavNode[0];
    return root.getNavChildren();
  }

  @Override
  public BIcon getNavIcon()
  {
    if (root == null) return getIcon();
    return root.getNavIcon();
  }

  @Override
  public BOrd getNavOrd()
  {
    if (root == null) return super.getNavOrd();
    return root.getNavOrd();
  }

///////////////////////////////////////////////////////////
// BIEntitySpace
///////////////////////////////////////////////////////////

  @Override
  public TagDictionaryService getTagDictionaryService()
  {
    return tagDictionaryService;
  }

  @Override
  public void setTagDictionaryService(TagDictionaryService service)
  {
    tagDictionaryService = service;
  }

  @Override
  public void removeTagDictionaryService(TagDictionaryService service)
  {
    if (tagDictionaryService == service)
      tagDictionaryService = null;
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Recursively scan the component tree to figure
   * out what the next handle should be.
   */
  private static long solveForNextHandle(BComponent comp, long next)
  {
    Object handle = comp.getHandle();
    if (handle != null && !handle.toString().startsWith(ComponentSlotMap.SWIZZLE_PREFIX))
    {

      try
      {
        long h = Long.parseLong(handle.toString(), 16);
        if (h >= next) next = h + 1;
      }
      catch (Exception ignored)
      {
      }
    }

    @SuppressWarnings("rawtypes")
    SlotCursor c = comp.getProperties();
    while (c.nextComponent())
      next = Math.max(next, solveForNextHandle(c.get().asComponent(), next));

    return next;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Dump slots and information common to all BComplex's.
   */
  @Override
  public void spy(SpyWriter out)
      throws Exception
  {
    out.startProps();
    out.trTitle("ComponentSpace", 2);
    out.prop("map.size", map.size());
    out.prop("nextHandle", Long.toHexString(nextHandle));
    out.prop("categoryMask", getCategoryMask());
    out.prop("defaultLeaseTime", getDefaultLeaseTime());

    out.trTitle("Enabled MixIns [" + mixIns.length + "]", 2);
    out.w("<tr>").th("MixIn").th("On").w("</tr>\n");
    for (Type mixIn : mixIns)
      out.tr(mixIn, mixInOn(mixIn));
    out.endProps();

    super.spy(out);
  }

  String mixInOn(Type t)
  {
    try
    {
      StringBuilder s = new StringBuilder("{");
      TypeInfo[] on = t.getTypeInfo().getAgentInfo().getAgentOn();
      for (int i = 0; i < on.length; ++i)
      {
        if (i > 0) s.append(", ");
        s.append(on[i]);
      }
      s.append("}");
      return s.toString();
    }
    catch (Exception e)
    {
      return e.toString();
    }
  }

  void dumpMap()
  {
    System.out.println("ComponentSpace.map");
    for (Entry<Object, BComponent> entry : map.entrySet())
    {
      Object value = entry.getValue();
      System.out.println("  " + entry.getKey() + " = " + value);
    }
  }

////////////////////////////////////////////////////////////////
// BIDataRecoverySource
////////////////////////////////////////////////////////////////

  /**
   * Called by the DataRecoveryService when a record should be restored.
   *
   * @since Niagara 3.6
   */
  @Override
  public boolean dataRecoveryRestore(IDataRecoveryRecord rec)
      throws Exception
  {
    // Route the call to the DataRecoveryComponentRecorder, if one exists for
    // this space
    return dataRecoveryRestorer != null && dataRecoveryRestorer.restore(this, rec);
  }

  /**
   * Callback to indicate that no further records will be restored to this source.
   *
   * @since Niagara 3.6
   */
  @Override
  public void dataRecoveryRestoreComplete()
  {
  }

  /**
   * Decode the data recovery provided to you by the Iterator and place it
   * into the SpyWriter for runtime analysis for the DataRecoverySource.
   *
   * @since Niagara 3.6
   */
  @Override
  public void dataRecoverySpy(SpyWriter out, Iterator<IDataRecoveryRecord> recoveryData)
      throws Exception
  {
    // Route the call to the dataRecoveryComponentRecorder, if one exists for
    // this space
    BDataRecoveryComponentRecorder dataRecoveryRecorder = Nre.getServiceManager().getDataRecoveryComponentRecorder(this);
    if (dataRecoveryRecorder != null)
      dataRecoveryRecorder.dataRecoverySpy(this, out, recoveryData);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Logger mixInLog = Logger.getLogger("sys.mixin");
  static TypeInfo nullProxyExt = null;

  static
  {
    try
    {
      nullProxyExt = Sys.getRegistry().getType("control:NullProxyExt");
    }
    catch (Exception ignored)
    {
    }
  }

  Map<Object, BComponent> map = new ConcurrentHashMap<>();
  long nextHandle = 1;

  LoadCallbacks loadCallbacks = new LoadCallbacks();
  TrapCallbacks trapCallbacks = new TrapCallbacks();
  SubscribeCallbacks subscribeCallbacks = new SubscribeCallbacks();
  BComponent root;
  BOrd ordInSession;
  Type[] mixIns = new Type[0];
  boolean holdMixInUpdates;
  private BDataRecoveryComponentRecorder dataRecoveryRestorer = null;
  long nextHandleOnCriticalStart = 1;
  List<Object> reassignedHandles = null;
  private TagDictionaryService tagDictionaryService;

  // This is a HashMap keyed by Type where the value
  // is an Array of TypeSubscriber instances.
  final Map<Type, List<List<TypeSubscriber>>> typeSubscriptionMap = new HashMap<>();

  // matches the number of possible component event types
  private static final int COMPONENT_EVENT_COUNT = 25;

}
