
/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.lang.reflect.Array;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.agent.BAbstractPxView;
import javax.baja.category.BCategoryMask;
import javax.baja.category.BICategorizable;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.Registry;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.security.PermissionException;
import javax.baja.space.BComponentSpace;
import javax.baja.space.BISpaceNode;
import javax.baja.space.BSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.BIEntity;
import javax.baja.tag.DataPolicy;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.tag.Tag;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.Tags;
import javax.baja.tag.util.ImpliedRelations;
import javax.baja.tag.util.ImpliedTags;
import javax.baja.tag.util.SmartRelationSet;
import javax.baja.tag.util.SmartTagSet;
import javax.baja.util.BConverter;
import javax.baja.util.BFolder;
import javax.baja.util.BFormat;
import javax.baja.util.BNameMap;
import javax.baja.util.BServiceContainer;
import javax.baja.util.IFuture;

import com.tridium.sys.Nre;
import com.tridium.sys.engine.EngineManager;
import com.tridium.sys.engine.EngineUtil;
import com.tridium.sys.engine.LeaseManager;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.schema.NAction;
import com.tridium.sys.schema.NTopic;
import com.tridium.sys.tag.ComponentRelations;
import com.tridium.sys.tag.ComponentTags;
import com.tridium.util.PxUtil;

/**
 * BComponent is the required base class for all
 * Baja component classes.
 *
 * @author Brian Frank
 *         creation  8 Aug 00
 * @version $Revision: 227$ $Date: 7/15/11 11:42:43 AM EDT$
 * @since Baja 1.0
 */
@NiagaraType
public class BComponent
  extends BComplex
  implements BISpaceNode, BIProtected, BICategorizable, BIPropertyContainer, BIEntity
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BComponent(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComponent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Public no arg constructor.
   */
  public BComponent()
  {
  }

////////////////////////////////////////////////////////////////
// Framework access
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.GET_REMOTE_VERSION:
      {
        BISession session = getSession();
        if (session instanceof BObject)
          return ((BObject)session).fw(x, a, b, c, d);
        return null;
      }
      case Fw.IS_SUBSCRIBED:
      {
        return ((ComponentSlotMap) slotMap).isSubscribed((Subscriber) a);
      }
    }
    return super.fw(x, a, b, c, d);
  }

////////////////////////////////////////////////////////////////
// Entity
////////////////////////////////////////////////////////////////

  public TagDictionaryService getTagDictionaryService()
  {
    BComponentSpace space = getComponentSpace();
    if (space == null)
      return null;
    else
    {
      TagDictionaryService tdService = space.getTagDictionaryService();
      if (tdService != null)
      {
        BStatus bStatus = BStatus.ok;
        try
        {
          bStatus = (BStatus)((BComponent)tdService).get("status");
        }
        catch (Exception ignored)
        {
        }
        if (bStatus.isValid())
          return tdService;

      }

      //        if(space instanceof BBogSpace)
      // check to see if off-line bog file
      BComponent root = space.getRootComponent();
//      // virtual component space must be running station
//      if(space instanceof BVirtualComponentSpace)
//      {
//        root = Sys.getStation();
//      }
//      else
//      {
//        root = space.getRootComponent();
//      }
      BServiceContainer[] services = root.getChildren(BServiceContainer.class);
      if (services == null || services.length == 0)
        return null;
      // assume index 0
      TagDictionaryService[] tagServices = services[0].getChildren(TagDictionaryService.class);
      if (tagServices == null || tagServices.length == 0)
        return null;
      if (tagServices[0] instanceof BComponent)
      {
        BStatus sValue = BStatus.ok;
        try
        {
          sValue = (BStatus)((BComponent)tagServices[0]).get("status");
        }
        catch (Exception ignored)
        {
        }
        if (!sValue.isValid())
          return null;
      }
      space.setTagDictionaryService(tagServices[0]);
      return tagServices[0];
    }
  }

  @Override
  public Tags tags()
  {
    TagDictionaryService service = getTagDictionaryService();
    if (service != null)
    {
      return new SmartTagSet(new ImpliedTags(service, this), new ComponentTags(this));
    }
    else
      return new ComponentTags(this);
  }

  @Override
  public Relations relations()
  {
    TagDictionaryService service = getTagDictionaryService();
    if (service != null)
    {
      return new SmartRelationSet(new ImpliedRelations(service, this), new ComponentRelations(this));
    }
    else
    {
      return new ComponentRelations(this);
    }
  }

  @Override
  public Optional<BOrd> getOrdToEntity()
  {
    return Optional.ofNullable(getAbsoluteOrd());
  }

  ////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /**
   * Get the handle which uniquely identifies this
   * component within its component space.
   */
  public Object getHandle()
  {
    return slotMap.getHandle();
  }

  /**
   * Get the slot path of this component within its space.
   * If not mounted within a space return null.
   */
  public SlotPath getSlotPath()
  {
    return slotMap.getSlotPath();
  }

  /**
   * Recursively clear the handle of this component
   * and all it descendant components.  Sometimes this
   * is necessary if adding a component tree into a
   * new ComponentSpace.
   *
   * @throws IllegalStateException if this method is
   *                               called and isMounted() returns true.
   */
  public void clearHandles()
  {
    if (isMounted())
    {
      throw new IllegalStateException("Component is mounted");
    }

    ((ComponentSlotMap)slotMap).setHandle(null);
    BComponent[] kids = getChildComponents();
    for (BComponent kid : kids)
    {
      kid.clearHandles();
    }
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Is this component running.
   */
  public final boolean isRunning()
  {
    return slotMap.isRunning();
  }

  /**
   * Start this component, and all its descendant
   * components.  The start sequence is:
   * <ol>
   * <li>Resolve all components links</li>
   * <li>The started() method is called this component</li>
   * <li>The component's children components are recursively
   * started</li>
   * <li>The descendantsStarted() method is called on this
   * component</li>
   * <li>The execution engine works its magic
   * through link and timer execution.</li>
   * </ol>
   * Once a component is running, it may be stopped using
   * the stop() method.  Calling the start() method has no
   * effect if the component is currently running.
   */
  public final void start()
  {
    slotMap.start();
  }

  /**
   * Stop the component graph from running.
   * The stop sequence for a component graph is:
   * <ol>
   * <li>Stop the execution engine from
   * executing links and timers</li>
   * <li>The stopped() method is called on this component</li>
   * <li>The component's children components are recursively
   * stopped</li>
   * <li>The descendantsStopped() method is called on this
   * component</li>
   * </ol>
   * Calling this method on a component which is not currently
   * running has no effect.
   */
  public final void stop()
  {
    slotMap.stop();
  }

  /**
   * The started() method is called when a component's
   * running state moves to true.  Components are started
   * top-down, children after their parent.
   */
  public void started()
    throws Exception
  {
  }

  /**
   * This method is called after started() has been called
   * on this component and all its descendants.
   */
  public void descendantsStarted()
    throws Exception
  {
  }

  /**
   * The stopped() method is called when a component is
   * running state is set to false.  Components are stopped
   * top-down, children after their parent.
   */
  public void stopped()
    throws Exception
  {
  }

  /**
   * This method is called after stopped() has been called
   * on this component and all its descendants.
   */
  public void descendantsStopped()
    throws Exception
  {
  }

  /**
   * This callback is invoked during station bootstrap after
   * all components in the station have been started.
   */
  public void stationStarted()
    throws Exception
  {
  }

  /**
   * This callback is invoked during station bootstrap after
   * the steady state timeout has expired.
   */
  public void atSteadyState()
    throws Exception
  {
  }

  /**
   * This callback is invoked when the system clock is modified.
   * The shift parameter specifies the positive or negative change
   * in the clock's value.
   */
  public void clockChanged(BRelTime shift)
    throws Exception
  {
  }

////////////////////////////////////////////////////////////////
// Subscription
////////////////////////////////////////////////////////////////

  /**
   * Return if this component is in the subscribed state.  A
   * component is defined as subscribed:
   * <pre>{@code
   *  isPermanentlySubscribed() ||
   *  getSubscribers().length > 0 ||
   *  (getKnobs().length > 0 && isRunning())
   * }</pre>
   */
  public final boolean isSubscribed()
  {
    return slotMap.isSubscribed();
  }

  /**
   * Get the list of subscribers on this component.  Each
   * subscriber for this component receives callbacks on
   * component state changes.
   */
  public final Subscriber[] getSubscribers()
  {
    return slotMap.getSubscribers();
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public void subscribed()
  {
  }

  /**
   * Callback when the component exits the subscribed state.
   */
  public void unsubscribed()
  {
  }

  /**
   * Return if the permanent subscription flag is set.  If
   * true then the component is in the subscribed state regardless
   * of other conditions like knob and subscriber count.
   */
  public final boolean isPermanentlySubscribed()
  {
    return slotMap.isPermanentlySubscribed();
  }

  /**
   * This method allows a subclass to set the permanent subscription
   * flag.  If permanent subscription is true then this component is
   * locked in the subscription state regardless of knob count or
   * subscriber count.  If it is false, then normal subscription rules
   * apply.  Since there is only one permanent subscribe flag, it is
   * impertative that only the subclass itself determine how the flag gets
   * set and cleared.  This method should only be used for components
   * running in a station VM (it is not intended for use in a workbench VM).
   */
  protected final void setPermanentlySubscribed(boolean subscribed)
  {
    slotMap.setPermanentlySubscribed(subscribed);
  }

  /**
   * If this component is currently leased, then return
   * the expiration time in ticks.  If not currently leased
   * then return -1.
   */
  public final long getLeaseExpiration()
  {
    return Nre.getLeaseManager().getLeaseExpiration(this);
  }

  /**
   * Convenience for {@code lease(0, getComponentSpace().getDefaultLeaseTime())}.
   */
  @Override
  public final void lease()
  {
    lease(0, getDefaultLeaseTime());
  }

  /**
   * Convenience for {@code lease(depth, getComponentSpace().getDefaultLeaseTime())}.
   */
  public final void lease(int depth)
  {
    lease(depth, getDefaultLeaseTime());
  }

  /**
   * Lease this component for the specified number of milliseconds
   * so that it expires at {@code Clock.ticks() + millis}.
   * Leasing is a stateless form of subscription which automatically
   * performs a subscribe and then an unsubscribe when the lease
   * expires.  If the component is already under lease, then the
   * new lease expiration is the max of {@code getLeaseExpiration()}
   * or {@code Clock.ticks() + millis}.  If depth is greater than
   * zero then the lease includes descendants (one is children, two is
   * children and grandchildren, etc).
   */
  @Override
  public final void lease(int depth, long millis)
  {
    Nre.getLeaseManager().lease(this, depth, millis);
  }

  /**
   * Convenience for {@code lease(components, depth, components[0].getComponentSpace().getDefaultLeaseTime())}.
   */
  public static void lease(BComponent[] components, int depth)
  {
    long leaseTime = 60000L;
    if ((components != null) && (components.length > 0))
    {
      BComponent first = components[0];
      if (first != null)
      {
        BComponentSpace space = first.getComponentSpace();
        if (space != null)
          leaseTime = space.getDefaultLeaseTime();
      }
    }
    lease(components, depth, leaseTime);
  }

  /**
   * This method is used to lease a batch of components at once.
   * It provides the same semantics as calling lease on each
   * component in the array, but potentially has much higher
   * performance.  All the components passed in the specified
   * array must exist inside the same component space.
   */
  public static void lease(BComponent[] components, int depth, long millis)
  {
    Nre.getLeaseManager().lease(components, depth, millis);
  }

  /**
   * Computes the default lease time (in milliseconds) for this component
   * by checking the default lease time specified by the component space in
   * which this component is mounted. The default lease time is 60000
   * (or 60 seconds).
   *
   * @since Niagara 3.7
   */
  private long getDefaultLeaseTime()
  {
    BComponentSpace space = getComponentSpace();
    if (space != null) return space.getDefaultLeaseTime();
    return 60000L;
  }

////////////////////////////////////////////////////////////////
// BISpaceNode
////////////////////////////////////////////////////////////////

  /**
   * Return {@code getComponentSpace()}.
   */
  @Override
  public final BSpace getSpace()
  {
    return getComponentSpace();
  }

  /**
   * If this component is in a space, then get the BComponentSpace
   * instance.  Return null for components which are not mounted
   * in a space.
   */
  public final BComponentSpace getComponentSpace()
  {
    // the null check is necessary in case this method
    // is called during the constructor before the slot
    // map is initialized
    return (slotMap == null) ? null : slotMap.getSpace();
  }

  /**
   * Return true if this component is readonly and shouldn't be
   * modified.  The default implementation of this returns
   * space.isSpaceReadonly().  This readonly state is independent
   * of any security permissions which might apply.
   */
  public boolean isComponentReadonly()
  {
    BComponentSpace space = getComponentSpace();
    if (space == null) return false;
    return space.isSpaceReadonly();
  }

  /**
   * Return ord relative to host using componentSpace.ordInSession.
   */
  @Override
  public BOrd getOrdInSession()
  {
    BComponentSpace cs = getComponentSpace();
    if (cs == null) return null;
    BOrd base = cs.getOrdInSession();
    if (base == null) return null;
    return BOrd.make(base, getOrdInSpace());
  }

  /**
   * Return getHandleOrd().
   */
  @Override
  public BOrd getOrdInSpace()
  {
    return getHandleOrd();
  }

  /**
   * Return "h:" + getHandle(), or null if not mounted.
   */
  public BOrd getHandleOrd()
  {
    Object handle = getHandle();
    if (handle == null) return null;
    return BOrd.make("h:" + handle);
  }

  /**
   * Return "slot:" + getSlotPath(), or null if not mounted.
   */
  public BOrd getSlotPathOrd()
  {
    SlotPath path = getSlotPath();
    if (path == null) return null;
    return BOrd.make(path);
  }

  /**
   * If this BComponent is currently contained by the current Mark
   * and the mark is set to pending move, then this method returns
   * true.  In user interfaces this flag should be used to render
   * the component grayed out to illustrate that a cut operation
   * has been performed on the component, but that a paste operation
   * is needed to complete the move.
   */
  @Override
  public final boolean isPendingMove()
  {
    return slotMap.isPendingMove();
  }

  /**
   * Set the pending move flag.
   */
  @Override
  public void setPendingMove(boolean pendingMove)
  {
    slotMap.setPendingMove(pendingMove);
  }

  /**
   * Return a string of this component's programmatic slot path.
   * If there is no slot path return a non-null string.
   */
  public String toPathString()
  {
    SlotPath path = getSlotPath();
    if (path == null) return "?" + getType() + "?";
    return path.getBody();
  }

  /**
   * Return a string of this component's display slot path.
   * Typically this is the unescaped version of toPathString().
   * If there is no slot path return a non-null string.
   */
  public String toDisplayPathString(Context cx)
  {
    SlotPath path = getSlotPath();
    if (path == null) return "?" + getType() + "?";
    return path.toDisplayString();
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Return {@code getName()}.
   */
  @Override
  public String getNavName()
  {
    String name = getName();
    if (name != null) return name;

    BComponentSpace space = getComponentSpace();
    if (space != null && space.getRootComponent() == this)
      return space.getNavName();

    return null;
  }

  /**
   * Return {@code getDisplayName(cx)}.
   */
  @Override
  public String getNavDisplayName(Context cx)
  {
    return getDisplayName(cx);
  }

  /**
   * Return {@code getParent()}.
   */
  @Override
  public BINavNode getNavParent()
  {
    BComponent parent = (BComponent)getParent();
    if (parent != null) return parent;
    return getComponentSpace();
  }

  /**
   * Return {@code true}.
   */
  @Override
  public boolean hasNavChildren()
  {
    return true;
  }

  /**
   * Return child slot if it is a BINavNode.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    BObject child = get(navName);
    if (child instanceof BINavNode)
      return (BINavNode)child;
    return null;
  }

  /**
   * Return getNavChild() or throw UnresolvedException if null.
   */
  @Override
  public BINavNode resolveNavChild(String navName)
  {
    BINavNode child = getNavChild(navName);
    if (child != null) return child;
    throw new UnresolvedException(navName);
  }

  /**
   * Return all the non-hidden child components..
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    loadSlots();
    BComponent[] temp = new BComponent[getSlotCount()];
    SlotCursor<Property> c = getProperties();
    int count = 0;
    while (c.nextComponent())
    {
      BComponent kid = (BComponent)c.get();
      if (Flags.isHidden(this, c.property())) continue;
      if (!kid.isNavChild()) continue;
      temp[count++] = kid;
    }

    BComponent[] result = new BComponent[count];
    System.arraycopy(temp, 0, result, 0, count);
    return result;
  }

  /**
   * Override to return false to prevent this component being
   * included in the getNavChildren list of its parent.
   */
  public boolean isNavChild()
  {
    return true;
  }

  /**
   * Get a short description.
   */
  @Override
  public String getNavDescription(Context cx)
  {
    return getType().toString();
  }

  /**
   * Return {@code space.navOrd + slotPathOrd}.
   */
  @Override
  public BOrd getNavOrd()
  {
    BComponentSpace space = getComponentSpace();
    if (space == null) return null;
    BOrd spaceOrd = space.getAbsoluteOrd();
    if (spaceOrd == null) return null;
    SlotPath path = getSlotPath();
    if (path == null) return null;
    return BOrd.make(spaceOrd, path);
  }

  /**
   * Return {@code getIcon}.
   */
  @Override
  public BIcon getNavIcon()
  {
    return getIcon();
  }

////////////////////////////////////////////////////////////////
// Add
////////////////////////////////////////////////////////////////

  /**
   * Add a new slot to the component.  The new slot is
   * always a dynamic (un-frozen) Property.  If the
   * value is an subclass of BAction the new slot is
   * also an Action slot.  If the value is a subclass of
   * BTopic the new slot is also a Topic.  All other
   * types are simple Property slots.
   *
   * @param name    the unique name to use as the String key for the slot.
   *                The name must meet the "name" production in the SlotPath BNF
   *                grammar. Informally this means that the name must start with
   *                an ascii letter and contain only ascii letters, ascii
   *                digits, or '_'.  Escape sequences can be specified using the
   *                '$' char.  Use SlotPath.escape() to escape illegal
   *                characters.
   *                If null is passed, then a unique name will automatically be
   *                generated.
   *                If the name ends with the '?' character, a unique name will
   *                automatically be generated by appending numbers to the
   *                specified name. Note that when using the '?' character, the
   *                specified name must be unescaped: e.g.,
   *                {@code "hello world?"} not {@code "hello$20world?"}.
   * @param value   BValue value of the new property.
   * @param flags   Mask of the properties slots using constants
   *                defined in Flags.
   * @param facets  Facets provide additional meta-data about
   *                the property. Maybe null or BFacets.NULL if no facets
   *                are required.
   * @param context Used to provide additional contextual info.
   * @return the property the slot was added with, or
   * null if trapped by a transaction or remote call.
   * @throws DuplicateSlotException   if a slot already
   *                                  exists with specified name.
   * @throws IllegalNameException     if the name contains
   *                                  illegal characters.  Use SlotPath.escape() to
   *                                  escape invalid characters.
   * @throws AlreadyParentedException if value
   *                                  is already contained by another object.
   *                                  If this is the case you should copy the
   *                                  value first.
   * @throws IllegalChildException    if the child type is
   *                                  not allowed as a property on this component.
   * @throws IllegalParentException   if this component type
   *                                  is not a legal parent type for the child.
   * @throws NullPointerException     if value is
   *                                  null.  Properties may never be set to null.
   * @throws PermissionException      if the context user does not
   *                                  have adminWrite permission on this component.
   */
  @Override
  public final Property add(String name, BValue value, int flags, BFacets facets, Context context)
  {
    return slotMap.add(name, flags, value, value.getSlotMap(), facets, context, null);
  }

  /**
   * Convenience method for add with BFacets.NULL.
   */
  public final Property add(String name, BValue value, int flags, Context context)
  {
    return slotMap.add(name, flags, value, value.getSlotMap(), null, context, null);
  }

  /**
   * Convenience method for add with BFacets.NULL and null context.
   */
  public final Property add(String name, BValue value, int flags)
  {
    return slotMap.add(name, flags, value, value.getSlotMap(), null, null, null);
  }

  /**
   * Convenience method for add with a default flags value 0 and
   * BFacets.NULL.
   */
  public final Property add(String name, BValue value, Context context)
  {
    return slotMap.add(name, 0, value, value.getSlotMap(), null, context, null);
  }

  /**
   * Convenience method for add with a default flags of 0,
   * BFacets.NULL, and null context.
   */
  public final Property add(String name, BValue value)
  {
    return slotMap.add(name, 0, value, value.getSlotMap(), null, null, null);
  }

////////////////////////////////////////////////////////////////
// Remove
////////////////////////////////////////////////////////////////

  /**
   * Remove the dynamic slot by the specified name.
   *
   * @throws FrozenSlotException if specified slot is frozen.
   * @throws PermissionException if the context user does not
   *                             have adminWrite permission on this component.
   */
  @Override
  public final void remove(String name, Context context)
  {
    Property property = getProperty(name);
    if (property == null)
      throw new NoSuchSlotException(name);
    remove(property, context);
  }

  /**
   * Remove the specified slot.
   *
   * @throws FrozenSlotException if specified slot is frozen.
   * @throws PermissionException if the context user does not
   *                             have adminWrite permission on this component.
   */
  @Override
  public final void remove(Property slot, Context context)
  {
    slotMap.remove(slot, context);
  }

  /**
   * Convenience method for remove with null context.
   */
  public final void remove(String name)
  {
    remove(name, null);
  }

  /**
   * Convenience method for remove with null context.
   */
  public final void remove(Property slot)
  {
    slotMap.remove(slot, null);
  }

  /**
   * Convenience method for {@code remove(child.getPropertyInParent())}.
   */
  public final void remove(BComplex child)
  {
    if (child.getParent() != this)
      throw new IllegalArgumentException("Not my child");
    slotMap.remove(child.getPropertyInParent(), null);
  }

  /**
   * Remove all dynamic properties.
   */
  @Override
  public final void removeAll(Context context)
  {
    Property[] props = slotMap.getPropertiesArray();
    for (Property prop : props)
    {
      if (!prop.isFrozen())
      {
        remove(prop, context);
      }
    }
  }

  /**
   * Remove all dynamic properties with null context.
   */
  public final void removeAll()
  {
    removeAll(null);
  }

////////////////////////////////////////////////////////////////
// Rename
////////////////////////////////////////////////////////////////

  /**
   * Rename the specified slot.
   *
   * @param slot    Property to rename.
   * @param newName New String name for the property.
   *                The name must meet the "name" production in the
   *                SlotPath BNF grammar.  Informally this means that
   *                the name must start with an ascii letter, and
   *                contain only ascii letters, ascii digits, or '_'.
   *                Escape sequences can be specified using the '$' char.
   *                Use SlotPath.escape() to escape illegal characters.
   * @param context Used to provide additional contextual info.
   * @throws IllegalNameException   if the name contains
   *                                illegal characters.  Use SlotPath.escape() to
   *                                escape invalid characters.
   * @throws FrozenSlotException    if specified slot is frozen.
   * @throws DuplicateSlotException if a slot already
   *                                exists with specified name.
   * @throws PermissionException    if the context user does not
   *                                have adminWrite permission on this component.
   */
  @Override
  public final void rename(Property slot, String newName, Context context)
  {
    slotMap.rename(slot, newName, context);
  }

  /**
   * Convenience method for rename with null context.
   */
  public final void rename(Property slot, String newName)
  {
    slotMap.rename(slot, newName, null);
  }

////////////////////////////////////////////////////////////////
// Display Name
////////////////////////////////////////////////////////////////

  /**
   * Get the display name format for the specified slot.  This returns
   * the BFormat stored in the displayNames map for the specified slot
   * or null if the slot does not have a display name defined.
   */
  public BFormat getDisplayNameFormat(Property slot)
  {
    BNameMap nameMap = (BNameMap)get("displayNames");
    if (nameMap == null)
    {
      return null;
    }
    else
    {
      return nameMap.get(slot.getName());
    }
  }

  /**
   * Set the display name for the specified slot.
   *
   * @since Niagara 3.6
   */
  public final void setDisplayName(Property slot, BFormat newName, Context context)
  {
    BNameMap nameMap = (BNameMap)get("displayNames");
    HashMap<String, BFormat> newMap = new HashMap<>();

    // extract names into a temporary hash map
    if (nameMap != null)
    {
      String[] keys = nameMap.list();
      for (String key : keys)
      {
        newMap.put(key, nameMap.get(key));
      }
    }

    // if the new name is null, or blank, then remove the display
    // name mapping for the slot
    if ((newName == null) || (newName.getFormat().isEmpty()))
      newMap.remove(slot.getName());
    else
      newMap.put(slot.getName(), newName);

    // if there are no more mapped display names, then remove the
    // displayNames slot if necessary
    if (newMap.isEmpty())
    {
      if (nameMap != null)
        remove("displayNames", context);
    }
    // otherwise replace or add the displayNames map
    else
    {
      boolean toAdd = nameMap == null;
      nameMap = BNameMap.make(newMap);
      if (toAdd)
        add("displayNames", nameMap, Flags.HIDDEN, context);
      else
        set("displayNames", nameMap, context);
    }
  }

////////////////////////////////////////////////////////////////
// Reorder
////////////////////////////////////////////////////////////////

  /**
   * Reorder the component's dynamic properties.
   *
   * @throws FrozenSlotException            if any of the specified
   *                                        properties are frozen.
   * @throws ArrayIndexOutOfBoundsException if the array length
   *                                        does not match number of dynamic properties.
   * @throws PermissionException            if the context user does not
   *                                        have adminWrite permission on this component.
   */
  @Override
  public final void reorder(Property[] dynamicProperties, Context context)
  {
    slotMap.reorder(dynamicProperties, context);
  }

  /**
   * Reorder the component's dynamic properties with null context.
   *
   * @throws FrozenSlotException            if any of the specified
   *                                        properties are frozen.
   * @throws ArrayIndexOutOfBoundsException if the array length
   *                                        does not match number of dynamic properties.
   */
  public final void reorder(Property[] dynamicProperties)
  {
    slotMap.reorder(dynamicProperties, null);
  }

  /**
   * Reorder the specified dynamic property to the
   * first dynamic slot position.
   *
   * @throws FrozenSlotException if the specified
   *                             property is frozen.
   * @throws PermissionException if the context user does not
   *                             have adminWrite permission on this component.
   */
  @Override
  public final void reorderToTop(Property dynamicProperty, Context context)
  {
    slotMap.reorderToTop(dynamicProperty, context);
  }

  /**
   * Reorder the specified dynamic property to the
   * first dynamic slot position using a null context.
   *
   * @throws FrozenSlotException if the specified
   *                             property is frozen.
   */
  public final void reorderToTop(Property dynamicProperty)
  {
    slotMap.reorderToTop(dynamicProperty, null);
  }

  /**
   * Reorder the specified dynamic property to the
   * last dynamic slot position.
   *
   * @throws FrozenSlotException if the specified
   *                             property is frozen.
   * @throws PermissionException if the context user does not
   *                             have adminWrite permission on this component.
   */
  @Override
  public final void reorderToBottom(Property dynamicProperty, Context context)
  {
    slotMap.reorderToBottom(dynamicProperty, context);
  }

  /**
   * Reorder the specified dynamic property to the
   * last dynamic slot position using a null context.
   *
   * @throws FrozenSlotException if the specified
   *                             property is frozen.
   */
  public final void reorderToBottom(Property dynamicProperty)
  {
    slotMap.reorderToBottom(dynamicProperty, null);
  }

////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////

  /**
   * This method allows subclasses to create the
   * default value to use when prompting the user for
   * the action's argument.  By default it calls
   * {@code Action.getParameterDefault()}.
   */
  public BValue getActionParameterDefault(Action action)
  {
    return action.getParameterDefault();
  }

  /**
   * Invoke the specified action.  If the action is synchronous
   * then the "do" implementation is invoked on the callers
   * thread.  If the action is asynchronous then multiple
   * invocations are coalesced and actually executed at some
   * point in the near future.
   *
   * @param action   Action to invoke
   * @param argument the argument to pass to the action
   *                 or null if no argument is required.  The specified
   *                 argument must be the same type as the action's
   *                 parameter or the invoke will fail.  Passing an
   *                 argument to an action with no parameter is ok, and
   *                 the specified argument will be ignored.
   * @return action return value or null if the action has
   * a void return value or the action is asynchronous.
   * @throws ActionInvokeException any invocation on a synchronous
   *                               action that raises an exception will raise the exception to
   *                               the caller wrapped by an ActionInvokeException.  Actions which
   *                               occur asynchronously or in the background will log any
   *                               raised exceptions.
   * @throws PermissionException   if the context user does not
   *                               have adminWrite permission on this component.
   */
  public final BValue invoke(Action action, BValue argument, Context context)
    throws ActionInvokeException
  {
    return slotMap.invoke(action, argument, context);
  }

  /**
   * Convenience method for invoke with a null context.
   */
  public final BValue invoke(Action action, BValue argument)
    throws ActionInvokeException
  {
    return slotMap.invoke(action, argument, null);
  }

  /**
   * This is a callback when an async action is invoked.  It gives
   * subclasses a chance to manage the invocation using their own
   * queues and threading models.  The default implementation schedules
   * the action using the engine manager thread.  The action should
   * invoked using the doInvoke() method on another thread and
   * immediately return control to the calling thread.  See the
   * utility classes Invocation, Queue, and Worker.
   *
   * @return always return null
   */
  public IFuture post(Action action, BValue argument, Context cx)
  {
    Nre.getEngineManager().enqueueAction(this, action, argument);
    return null;
  }

  /**
   * This method forces the do method of an async action
   * to be called.  Calling this method should be the result
   * of a post callback.
   */
  public void doInvoke(Action action, BValue argument, Context cx)
    throws ActionInvokeException
  {
    EngineUtil.doInvoke(this, action, argument, cx);
  }

////////////////////////////////////////////////////////////////
// Fire
////////////////////////////////////////////////////////////////

  /**
   * Fire an event on the specified topic.  Any
   * actions linked to this topic will get invoked
   * by the execution engine.
   */
  public final void fire(Topic topic, BValue event, Context context)
  {
    slotMap.fire(topic, event, context);
  }

  /**
   * Fire an event on the specified topic with a null
   * context.  Any actions linked to this topic will
   * get invoked by the execution engine.
   */
  public final void fire(Topic topic, BValue event)
  {
    slotMap.fire(topic, event, null);
  }

////////////////////////////////////////////////////////////////
// Invariant Callbacks
////////////////////////////////////////////////////////////////

  /**
   * This method is called during the first phase of {@code add()}
   * before the add is committed.  If a subclass wishes to cancel the
   * add, then a {@code LocalizableRuntimeException} should be thrown.
   * This method is called before {@code isParentLegal} and
   * {@code isChildLegal()}.  However, unlike the parent and
   * child checks, this method is called when adding any type
   * of BValue, not just BComponents.
   */
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
  }

  /**
   * This method is called during the first phase of {@code remove()}
   * before the remove is committed.  If a subclass wishes to cancel
   * the remove, then a {@code LocalizableRuntimeException} should
   * be thrown.
   */
  public void checkRemove(Property property, Context context)
  {
  }

  /**
   * This method is called during the first phase of {@code rename()}
   * before the rename is committed.  If a subclass wishes to cancel
   * the rename, then a {@code IllegalNameException} should
   * be thrown.
   */
  public void checkRename(Property property, String newName, Context context)
  {
  }

  /**
   * This method is called during the first phase of the reorder
   * methods before the reorder is committed.  If a subclass wishes to
   * cancel the reorder, then a {@code LocalizableRuntimeException}
   * should be thrown.
   */
  public void checkReorder(Property[] properties, Context context)
  {
  }

  /**
   * This method is called during the first phase of {@code setFlags()}
   * before the set flags is committed.  If a subclass wishes to cancel
   * the set flags, then a {@code LocalizableRuntimeException} should
   * be thrown.
   */
  public void checkSetFlags(Slot slot, int flags, Context context)
  {
  }

  /**
   * This method is called during the first phase of {@code setFacets()}
   * before the set facets is committed.  If a subclass wishes to cancel
   * the set facets, then a {@code LocalizableRuntimeException} should
   * be thrown.
   */
  public void checkSetFacets(Slot slot, BFacets facets, Context context)
  {
  }

  /**
   * Is the specified parent a legal parent for this component.
   * Default is to return true for any parent.  Note that this
   * check is disabled if adding to a BUnrestrictedFolder.  This
   * method is called during the {@code add()} method.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return true;
  }

  /**
   * Is the specified child a legal child component for this
   * component.  There are no restrictions placed on BSimple
   * and BStruct children, only BComponent children.  Default
   * is to return true for any child. This method is called
   * during the {@code add()} method.
   */
  public boolean isChildLegal(BComponent child)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Post Callbacks
////////////////////////////////////////////////////////////////

  /**
   * Callback when a property (or possibly a descendant of
   * that property) is modified on this component via
   * one of the {@code set} methods.
   */
  public void changed(Property property, Context context)
  {
  }

  /**
   * Called when a new property is added to this component
   * via one of the {@code add} methods.
   */
  public void added(Property property, Context context)
  {
  }

  /**
   * Called when an existing property is removed from this
   * component via one of the {@code remove} methods.
   */
  public void removed(Property property, BValue oldValue, Context context)
  {
  }

  /**
   * Called when an existing property is renamed via one
   * of the {@code rename} methods.
   */
  public void renamed(Property property, String oldName, Context context)
  {
  }

  /**
   * Called when the properties are reordered via one of
   * the {@code reorder} methods.
   */
  public void reordered(Context context)
  {
  }

  /**
   * Called when a slot's flags are modified via one of
   * the {@code setFlags} methods.
   */
  public void flagsChanged(Slot slot, Context context)
  {
  }

  /**
   * Called when a slot's facets are modified via one of
   * the {@code setFacets} methods.
   */
  public void facetsChanged(Slot slot, Context context)
  {
  }

  /**
   * Callback when the component's category mask is changed.
   */
  public void recategorized(Context context)
  {
  }

  /**
   * Called when a BValue is being parented as a direct child
   * of this component either because of a set or an add.
   */
  public void childParented(Property property, BValue newChild, Context context)
  {
  }

  /**
   * The childUnparented callback is invoked when a BValue
   * is being removed as a direct child of this component
   * either because of a set or a remove.
   */
  public void childUnparented(Property property, BValue oldChild, Context context)
  {
  }

  /**
   * Called when a knob is activated.
   */
  public void knobAdded(Knob knob, Context context)
  {
  }

  /**
   * Called when a knob is deactivated.
   */
  public void knobRemoved(Knob knob, Context context)
  {
  }

  /**
   * Called when a relationKnob is added.
   */
  public void relationKnobAdded(RelationKnob knob, Context context)
  {
  }

  /**
   * Called when a relationKnob is removed.
   */
  public void relationKnobRemoved(RelationKnob knob, Context context)
  {
  }

  /**
   * Callback when a group of properties is modified on this component
   * in batch via the batch {@code set} method.  This callback will
   * occur after all of the individual {@code changed()} callbacks
   * have occurred.
   *
   * @param properties an array of the properties that just
   *                   had a batch change of value.
   * @param context    the Context associated with the batch change.
   * @since Niagara 4.0
   */
  public void batchChanged(Property[] properties, Context context)
  {
  }

////////////////////////////////////////////////////////////////
// Children
////////////////////////////////////////////////////////////////

  /**
   * Get all an array of the children properties
   * which are instances of BComponent.
   */
  public synchronized BComponent[] getChildComponents()
  {
    BComponent[] temp = new BComponent[getSlotCount()];
    SlotCursor<Property> c = getProperties();
    int count = 0;
    while (c.nextComponent())
    {
      temp[count++] = (BComponent)c.get();
    }

    BComponent[] result = new BComponent[count];
    System.arraycopy(temp, 0, result, 0, count);
    return result;
  }

  /**
   * This is a convenience method to get an array of all
   * this object's children which are an instance of the
   * specified class.
   *
   * @return an array of with a component type of the
   * specified class which contains all the child
   * property values which are an instance of the
   * specified class.
   */
  @SuppressWarnings("unchecked")  // BValue is cast to T
  public synchronized <T> T[] getChildren(Class<T> cls)
  {
    List<T> result = new ArrayList<>();
    SlotCursor<Property> c = getProperties();
    while (c.next(cls))
    {
      result.add((T)c.get());
    }
    return result.toArray((T[])Array.newInstance(cls, result.size()));
  }

  /**
   * Get the MixIn child property value.  MixIns are always
   * stored with a property name equal to the type spec string
   * where the colon is replaced with an underbar.  Return
   * null if the specified MixIn type has not been added to
   * this component.
   */
  public BValue getMixIn(Type type)
  {
    return get(type.toString().replace(':', '_'));
  }

////////////////////////////////////////////////////////////////
// Links
////////////////////////////////////////////////////////////////

  /**
   * Check if the specified link if valid, and return
   * an appropriate LinkCheck instance.  This method is
   * does standard checking, then routes to doCheckLink().
   */
  public final LinkCheck checkLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    LinkCheck check = LinkCheck.make(source, sourceSlot, this, targetSlot, cx);
    if (check.isValid())
      check = doCheckLink(source, sourceSlot, targetSlot, cx);
    return check;
  }

  /**
   * This an override point to specify additional link checking
   * between the specified source and my target slot.  If the
   * link is valid then return LinkCheck.makeValid().  Or if
   * the link would result in an error condition return then
   * return a LinkCheck with the appropriate reason.  The default
   * implementation returns LinkCheck.makeValid().
   */
  protected LinkCheck doCheckLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    return LinkCheck.makeValid();
  }


  /**
   * Create an instance of BLink to use for a link to the specified
   * source component.  This method is used by Baja tools when users
   * create links via the "bajaui:javax.baja.ui.commands.LinkCommand".
   * The default implementation returns a standard BLink instance with
   * enable set to true.  When setting the source ord of the BLink you
   * should use {@code source.getHandleOrd()}.
   */
  public BLink makeLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    BOrd srcOrd = source.getHandleOrd();

    Type sourceType = null;
    if (sourceSlot.isAction())
      sourceType = sourceSlot.asAction().getParameterType();
    else if (sourceSlot.isTopic())
      sourceType = sourceSlot.asTopic().getEventType();
    else if (sourceSlot.isProperty())
      sourceType = sourceSlot.asProperty().getType();

    Type targetType = null;
    if (targetSlot.isAction())
      targetType = targetSlot.asAction().getParameterType();
    else if (targetSlot.isTopic())
      targetType = targetSlot.asTopic().getEventType();
    else if (targetSlot.isProperty())
      targetType = targetSlot.asProperty().getType();

    // Use a conversion link if required
    if (sourceType != null && targetType != null && !sourceType.is(targetType))
    {
      Registry registry = Sys.getRegistry();
      TypeInfo[] adapters = registry.getAdapters(sourceType.getTypeInfo(), targetType.getTypeInfo());
      for (int i = adapters.length - 1; i >= 0; i--)
      {
        if (registry.isAgent(adapters[i], BConversionLink.TYPE.getTypeInfo()))
        {
          return new BConversionLink(srcOrd, sourceSlot.getName(), targetSlot.getName(), true, (BConverter)adapters[i].getInstance());
        }
      }
    }

    // By default use a standard link
    return new BLink(srcOrd, sourceSlot.getName(), targetSlot.getName(), true);
  }

  /**
   * Get all the children on this component which are BLinks which by definition have this component
   * as the target.
   *
   * <p>Method used to be synchronized but synchronization is now handled within the method. This
   * method should not be called within a block synchronized on this component.
   */
  public BLink[] getLinks()
  {
    // This method used to be synchronized but that was causing deadlocks. If the client sync thread
    // was synchronizing with the server (syncFromMaster), loadSlots would be blocked waiting for
    // that lock. If a sync operation within syncFromMaster then needed a lock on this component, a
    // deadlock would occur.
    loadSlots();

    synchronized (this)
    {
      BLink[] temp = new BLink[getSlotCount()];
      int count = 0;

      SlotCursor<Property> c = slotMap.getProperties();
      while (c.nextObject())
      {
        BObject child = c.get();
        if (child instanceof BLink)
          temp[count++] = (BLink) child;
      }

      BLink[] result = new BLink[count];
      System.arraycopy(temp, 0, result, 0, count);
      return result;
    }
  }

  /**
   * Get all the children BLinks on this component which are linked to the specified target slot.
   *
   * <p>Method used to be synchronized but synchronization is now handled within the method. This
   * method should not be called within a block synchronized on this component.
   */
  public BLink[] getLinks(Slot slot)
  {
    // This method used to be synchronized but that was causing deadlocks. If the client sync thread
    // was synchronizing with the server (syncFromMaster), loadSlots would be blocked waiting for
    // that lock. If a sync operation within syncFromMaster then needed a lock on this component, a
    // deadlock would occur.
    loadSlots();

    synchronized (this)
    {
      String name = slot.getName();
      BLink[] temp = new BLink[getSlotCount()];
      int count = 0;

      SlotCursor<Property> c = slotMap.getProperties();
      while (c.nextObject())
      {
        BObject child = c.get();
        if (child instanceof BLink)
        {
          BLink link = (BLink) child;
          if (link.getTargetSlotName().equals(name))
            temp[count++] = (BLink) child;
        }
      }

      BLink[] result = new BLink[count];
      System.arraycopy(temp, 0, result, 0, count);
      return result;
    }
  }

  /**
   * Get the number of knobs currently registered on
   * this component.
   */
  public synchronized int getKnobCount()
  {
    return slotMap.getKnobCount();
  }

  /**
   * Get the link knobs currently registered on this
   * component.  Knobs indicate this component's usage
   * as a link source.
   */
  public synchronized Knob[] getKnobs()
  {
    return slotMap.getKnobs();
  }

  /**
   * Get all the Knobs for the specified slot.  These
   * knobs indicate the links which are sourced on
   * this component and the specified slot.
   */
  public synchronized Knob[] getKnobs(Slot slot)
  {
    return slotMap.getKnobs(slot);
  }

  /**
   * Return true if the specified slot is currently already
   * the target slot of an existing link.
   *
   * <p>Method used to be synchronized. This method calls {@link #getLinks(Slot)}, which also used
   * to be synchronized, and synchronization is now handled within that method. This method should
   * not be called within a block synchronized on this component.
   */
  public boolean isLinkTarget(Slot slot)
  {
    return getLinks(slot).length > 0;
  }

  /**
   * Create a direct link to the specified source, add it
   * a dynamic transient property, and activate it.  The name
   * is autogenerated.
   */
  public BLink linkTo(BComponent source, Slot sourceSlot, Slot targetSlot)
  {
    return linkTo(null, source, sourceSlot, targetSlot);
  }

  /**
   * Create a direct link to the specified source, add it
   * a dynamic transient property, and activate it.
   */
  public BLink linkTo(String propertyName, BComponent source, Slot sourceSlot, Slot targetSlot)
  {
    BLink link = new BLink(source, sourceSlot, targetSlot);
    add(propertyName, link, Flags.TRANSIENT);
    link.activate();
    return link;
  }

////////////////////////////////////////////////////////////////
// Relations
////////////////////////////////////////////////////////////////

  /**
   * Create an instance of BRelation to use for a relation to the specified
   * endpoint component.  This method is used by Baja tools when users
   * create relations via the "bajaui:javax.baja.ui.commands.RelateCommand".
   * The default implementation returns a standard BRelation instance with
   * enable set to true.  When setting the endpoint ord of the BRelation you
   * should use {@code source.getHandleOrd()}.
   */
  public BRelation makeRelation(Id id, BComponent endpoint, Context cx)
  {
    BOrd srcOrd = endpoint.getHandleOrd();

    // By default use a standard link
    return new BRelation(id, srcOrd);
  }

  /**
   * Get all the children on this component which are BRelation which by definition have this
   * component as the target.
   *
   * <p>Method used to be synchronized but synchronization is now handled within the method. This
   * method should not be called within a block synchronized on this component.
   */
  public BRelation[] getComponentRelations()
  {
    // This method used to be synchronized but that was causing deadlocks. If the client sync thread
    // was synchronizing with the server (syncFromMaster), loadSlots would be blocked waiting for
    // that lock. If a sync operation within syncFromMaster then needed a lock on this component, a
    // deadlock would occur.
    loadSlots();

    synchronized (this)
    {
      BRelation[] temp = new BRelation[getSlotCount()];
      int count = 0;

      SlotCursor<Property> c = slotMap.getProperties();
      while (c.nextObject())
      {
        BObject child = c.get();
        if (child instanceof BRelation)
        {
          temp[count++] = (BRelation) child;
        }
      }

      BRelation[] result = new BRelation[count];
      System.arraycopy(temp, 0, result, 0, count);
      return result;
    }
  }

  /**
   * Get the number of knobs currently registered on
   * this component.
   */
  public synchronized int getRelationKnobCount()
  {
    try
    {
      return slotMap.getRelationKnobCount();
    }
    catch (Exception e)
    {
      return 0;
    }
  }

  /**
   * Get the link knobs currently registered on this
   * component.  Knobs indicate this component's usage
   * as a link source.
   */
  public synchronized RelationKnob[] getRelationKnobs()
  {
    return slotMap.getRelationKnobs();
  }

  /**
   * Get the link knobs currently registered on this
   * component.  Knobs indicate this component's usage
   * as a link source.
   */
  public synchronized RelationKnob getRelationKnob(Id id)
  {
    return slotMap.getRelationKnob(id.getQName());
  }

  /**
   * Return true if the specified slot is currently already the target slot of an existing link.
   *
   * <p>Method used to be synchronized. This method calls {@link #getComponentRelations()}, which
   * also used to be synchronized, and synchronization is now handled within that method. This
   * method should not be called within a block synchronized on this component.
   */
  public boolean isRelationTarget()
  {
    return getComponentRelations().length > 0;
  }

  /**
   * Create a direct relation to the specified endpoint, add it
   * a dynamic transient property, and activate it.  The name
   * is autogenerated.
   */
  public BRelation relateTo(Id id, BComponent endpoint)
  {
    return relateTo(null, id, endpoint);
  }

  /**
   * Create a direct link to the specified source, add it
   * a dynamic transient property, and activate it.
   */
  public BRelation relateTo(String propertyName, Id id, BComponent endpoint)
  {
    BRelation relation = new BRelation(id, endpoint);
    add(propertyName, relation, Flags.TRANSIENT);
    relation.activateRelation();
    return relation;
  }


////////////////////////////////////////////////////////////////
// Categories
////////////////////////////////////////////////////////////////

  /**
   * Get the category mask to actually use for this component.
   * If {@code getCategoryMask()} is null, then return
   * the inherited category mask.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return slotMap.getAppliedCategoryMask();
  }

  /**
   * Get the raw category mask for this component.  If this
   * method returns {@code BCategoryMask.NULL}, then the
   * component is inheriting categories from it's parent and
   * {@code getAppliedCategoryMask()} will return the
   * actual category mask to use.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    return slotMap.getCategoryMask();
  }

  /**
   * Set the raw category mask for this component.  Set the
   * mask to {@code BCategoryMask.NULL} to inherit categories
   * from the component's parent.
   */
  public void setCategoryMask(BCategoryMask mask, Context cx)
  {
    slotMap.setCategoryMask(mask, cx);
  }

////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////

  /**
   * Get the set of permissions available based on the
   * specified context.  If the context is non-null and
   * has a non-null user then this method must be return
   * {@code cx.getUser().getPermissionsFor(this)}.
   * If the context is null then typically this method
   * should return {@code BPermissions.all}.  If this
   * happens to be a proxy object within a remote session,
   * then this method should return a cached instance
   * of BPermissions based on the credentials used to
   * establish the session.  Under no circumstances should
   * this method return null or make a network call.
   */
  @Override
  public BPermissions getPermissions(Context cx)
  {
    // use cached permissions if we got them
    BPermissions permissions = slotMap.getCachedPermissions();

    // otherwise check user permissions
    if (permissions == null)
    {
      if (cx != null && cx.getUser() != null)
        permissions = cx.getUser().getPermissionsFor(this);
      else
        permissions = BPermissions.all;
    }

    return permissions;
  }

  /**
   * If the target is to a slot within this component
   * then use the read permission based on the slot's
   * operator flag.  If the target is the component itself
   * return if operator read is enabled.
   */
  @Override
  public boolean canRead(OrdTarget cx)
  {
    if (cx.getComponent() != this) throw new IllegalStateException();
    Slot slot = cx.getSlotInComponent();
    BPermissions permissions = cx.getPermissionsForTarget();
    if (slot == null)
      return permissions.has(BPermissions.OPERATOR_READ);
    else if (Flags.isOperator(this, slot))
      return permissions.has(BPermissions.OPERATOR_READ);
    else
      return permissions.has(BPermissions.ADMIN_READ);
  }

  /**
   * If the target is to a slot within this component
   * then use the write permission based on the slot's
   * readonly *and* operator flags.  If the target is the
   * component itself return if operator write is enabled.
   */
  @Override
  public boolean canWrite(OrdTarget cx)
  {
    if (cx.getComponent() != this) throw new IllegalStateException();
    Slot slot = cx.getSlotInComponent();
    BPermissions permissions = cx.getPermissionsForTarget();
    if (slot == null)
      return permissions.has(BPermissions.OPERATOR_WRITE);
    else if (Flags.isReadonly(this, slot))
      return false;
    else if (Flags.isOperator(this, slot))
      return permissions.has(BPermissions.OPERATOR_WRITE);
    else
      return permissions.has(BPermissions.ADMIN_WRITE);
  }

  /**
   * If the target is to a slot within this component
   * then use the invoke permission based on the slot's
   * operator flag.  If the target is the component itself
   * return if operator invoke is enabled.
   */
  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    if (cx.getComponent() != this) throw new IllegalStateException();
    Slot slot = cx.getSlotInComponent();
    BPermissions permissions = cx.getPermissionsForTarget();
    if (slot == null)
      return permissions.has(BPermissions.OPERATOR_INVOKE);
    else if (Flags.isOperator(this, slot))
      return permissions.has(BPermissions.OPERATOR_INVOKE);
    else
      return permissions.has(BPermissions.ADMIN_INVOKE);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);

    // Put property sheet on top of the wiresheet.
    {
      int prop = list.indexOf("workbench:PropertySheet");
      int ws = list.indexOf("wiresheet:WireSheet");

      if (prop >= 0)
      {
        if (ws >= 0)
        {
          if (this instanceof BFolder)
          {
            // If we have a BFolder then move the wiresheet to the top.
            if (prop < ws)
            {
              list.swap(prop, ws);
              prop = ws;
            }
          }
          else
          {
            if (ws < prop)
            {
              list.swap(prop, ws);
              prop = ws;
            }
          }
        }

        if (moveMultiSheet)
        {
          // If allowed, make sure the MultiSheet is above the AX Property Sheet.
          int ms = list.indexOf("webEditors:MultiSheet");
          if (ms >= 0)
          {
            list.remove(ms);
            list.add(prop, "webEditors:MultiSheet");
          }
        }
      }
    }



    if (hideWebWiresheet)
    {
      list.remove("wiresheet:WebWiresheet");
    }
    else
    {
      int ws = list.indexOf("wiresheet:WireSheet");
      list.remove("wiresheeet:WebWiresheet");
      list.add(ws + 1, "wiresheet:WebWiresheet");
    }

    list.toBottom("wbutil:CategorySheet");
    list.toBottom("workbench:SlotSheet");
    list.toBottom("workbench:LinkSheet");
    list.toBottom("workbench:RelationSheet");
    list.toBottom("pxEditor:PxEditor");

    // find all agents whose type extends from
    // BAbstractPxView and move them to the top
    for (int i = 0; i < list.size(); i++)
    {
      AgentInfo agentInfo = list.get(i);
      if (agentInfo.getAgentType().is(BAbstractPxView.TYPE))
        list.toTop(agentInfo);
    }

    // add any property values which implement AgentInfo
    Property[] props = getPropertiesArray();
    for (int i = props.length - 1; i >= 0; --i)
    {
      if (props[i].getTypeAccess() != Slot.BOBJECT_TYPE) continue;
      BValue value = get(props[i]);
      if (value instanceof AgentInfo)
        list.add((AgentInfo)value);
    }

    // explode each PxViews into PxEditor, HTML, PDF, etc
    PxUtil.explode(list);

    return list;
  }

  private static boolean moveMultiSheet =
    AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.moveMultiSheet", "false")).equals("true");

  private static boolean hideWebWiresheet =
    AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.hideWebWiresheet", "false")).equals("true");

  /**
   * Get the icon.  The default implement checks if
   * there is a BIcon property called "icon".  If
   * not then return a default icon.
   */
  @Override
  public BIcon getIcon()
  {
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon;
  }

  private static final BIcon icon = BIcon.std("object.png");

////////////////////////////////////////////////////////////////
//Slot Factory
////////////////////////////////////////////////////////////////

  protected static Action newAction(int flags, BValue parameterDefault, BFacets facets)
  {
    if (facets == null) facets = BFacets.NULL;
    return new NAction(flags, parameterDefault, facets);
  }

  protected static Action newAction(int flags, BValue parameterDefault)
  {
    return newAction(flags, parameterDefault, null);
  }

  protected static Action newAction(int flags, BFacets facets)
  {
    return newAction(flags, null, facets);
  }

  protected static Action newAction(int flags)
  {
    return newAction(flags, null, null);
  }

  protected static Topic newTopic(int flags, BFacets facets)
  {
    if (facets == null) facets = BFacets.NULL;
    return new NTopic(flags, facets);
  }

  protected static Topic newTopic(int flags)
  {
    return newTopic(flags, null);
  }

  @Override
  protected void spyRelations(SpyWriter out)
  {
    BComponent comp = this;
    TagDictionaryService service = getTagDictionaryService();
    ComponentRelations directRelations = new ComponentRelations(this);
    ImpliedRelations impliedRelations;
    if (service != null)
    {
      impliedRelations = new ImpliedRelations(service, this);
      boolean first = true;
      for (Relation relation : impliedRelations)
      {
        if (first)
        {
          out.trTitle("Relations Implied", 2);
          first = false;
        }
        String dir = IN;
        if (relation.isOutbound())
          dir = OUT;
        dir = dir + ((BISpaceNode)relation.getEndpoint()).getNavOrd();
        out.prop(relation.getId(), dir);
      }
      first = true;
      for (Relation relation : directRelations)
      {
        String dir;
        // only print outbound relations
        // relation knobs are printed in BComplex's spyKnobs()
        if (relation.isOutbound())
        {
          if (first)
          {
            out.trTitle("Relations Direct", 2);
            first = false;
          }
          dir = OUT;
          out.prop(relation.getId(), dir + ((BISpaceNode)relation.getEndpoint()).getNavOrd());
        }
      }
      // check for direct tags with data policies
      ComponentTags directTags = new ComponentTags(this);
      List<DataPolicy> dataPolicies = new ArrayList<>();
      for (Tag directTag : directTags)
      {
        Optional<DataPolicy> dataPolicyForTag = service.getDataPolicyForTag(directTag.getId());
        if (dataPolicyForTag.isPresent())
          dataPolicies.add(dataPolicyForTag.get());
      }
      // get the data policy via the n:tagGroup relation
      Collection<DataPolicy> tgPolicies = DataPolicy.getDataPolicy(this);
      for (DataPolicy tgPolicy : tgPolicies)
      {
        dataPolicies.add(tgPolicy);

      }
      if (!dataPolicies.isEmpty())
      {
        out.trTitle("DataPolicy", 2);
        //noinspection Convert2streamapi
        for (DataPolicy dataPolicy : dataPolicies)
        {
          if (dataPolicy instanceof BComponent)
          {
            BComponent dpComp = (BComponent)dataPolicy;
            out.prop(dpComp.getName(), dpComp.getSlotPath());
          }
        }
      }

    }


  }

  @Override
  protected void spyTags(SpyWriter out)
  {
    BComponent comp = this;
    TagDictionaryService service = getTagDictionaryService();
    ComponentTags directTags = new ComponentTags(this);
    ImpliedTags impliedTags = null;
    if (service != null)
    {
      boolean first = true;
      for (Tag tag : service.getImpliedTags(this))
      {
        if (first)
        {
          out.trTitle("Tags Implied", 2);
          first = false;
        }
        out.prop(tag.getId(), tag.getValue());
      }
      first = true;
      for (Tag tag : directTags)
      {
        if (first)
        {
          out.trTitle("Tags Direct", 2);
          first = false;
        }
        out.prop(tag.getId(), tag.getValue());
      }
    }
  }

}
