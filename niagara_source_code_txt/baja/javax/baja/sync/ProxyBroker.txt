/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.io.ValueDocEncoder;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
import javax.baja.nav.NavListener;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BComponentEventMask;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Subscriber;

/**
 * ProxyBroker is used to buffer up changes made to a component
 * space to forward to a BProxyComponentSpace for synchronization.
 * The space a ProxyBroker is registered on is called the master space.
 *
 * @author    Brian Frank on 21 Jan 03
 * @version   $Revision: 9$ $Date: 2/6/07 3:59:07 PM EST$
 * @since     Baja 1.0
 */
public class ProxyBroker 
  extends Subscriber
  implements NavListener  
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public ProxyBroker(BComponentSpace masterSpace)
  {
    this.masterSpace = masterSpace;
    this.buffer = new ProxySyncBuffer(masterSpace);
    this.setMask(PROXY_EVENT_MASK);
  }

  public ProxyBroker(BComponentSpace masterSpace, IProxyBrokerPlugin plugin)
  {
    this(masterSpace);
    this.plugin = plugin;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the master space this broker is registered on.
   */
  @SuppressWarnings("unused")
  public BComponentSpace getMasterSpace()
  {
    return masterSpace;
  }

  /**
   * Start this broker buffering up changes.
   */
  public void start()
  {
    BNavRoot.INSTANCE.addNavListener(this);
  }

  /**
   * Cleanup this broker for disposal.
   */
  public void stop()
  {
    BNavRoot.INSTANCE.removeNavListener(this);
    unsubscribeAll();
  }

  /**
   * This method takes the current buffer, detaches it
   * from this broker so that it buffers no more changes,
   * and installs a fresh buffer.
   */
  public SyncBuffer detachBuffer()
  {
    synchronized(bufferLock)
    {
      SyncBuffer detached = buffer;
      buffer = new ProxySyncBuffer(masterSpace);
      return detached;
    }
  }

  /**
   * Buffer up a LoadOp
   */
  public void loadOp(BComponent component, int depth)
  {
    LoadOp op = new LoadOp(component, depth);
    synchronized(bufferLock)
    {
      buffer.add(op);
    }
    if (plugin != null) plugin.newSyncOp(op);
  }

  /**
   * Buffer up a LoadOp and specify whether partial loading
   * is enabled.
   * This method should only be used
   * with caution, as it is intended for supporting the
   * virtual component scenario.  It basically allows for
   * partial loading, which is ONLY used in the case of
   * virtuals.
   *
   * @since     Niagara 3.2
   */
  public void loadOp(BComponent component, int depth, boolean partialLoad)
  {
    LoadOp op = new LoadOp(component, depth, partialLoad);
    synchronized(bufferLock)
    {
      buffer.add(op);
    }
    if (plugin != null) plugin.newSyncOp(op);
  }

  /**
   * Perform a subscription and buffer up a LoadOp.
   */
  public void subscribeOp(BComponent component, int depth)
  {
    subscribe(component, depth);  // do this outside bufferLock
    LoadOp op = new LoadOp(component, depth);
    synchronized(bufferLock)
    {
      buffer.add(op);
    }
    if (plugin != null) plugin.newSyncOp(op);
  }

  /**
   * Perform a subscription and buffer up a LoadOp also specifying
   * the partialLoad constraint.
   * This method should only be used
   * with caution, as it is intended for supporting the
   * virtual component scenario.  It basically allows for
   * partial loading, which is ONLY used in the case of
   * virtuals.
   *
   * @since     Niagara 3.2
   */
  public void subscribeOp(BComponent component, int depth, boolean partialLoad)
  {
    subscribe(component, depth);  // do this outside bufferLock
    LoadOp op = new LoadOp(component, depth, partialLoad);
    synchronized(bufferLock)
    {
      buffer.add(op);
    }
    if (plugin != null) plugin.newSyncOp(op);
  }

////////////////////////////////////////////////////////////////
// NavListener
////////////////////////////////////////////////////////////////

  /**
   * If the event is for a component within the
   * master space, then add it to the sync buffer.
   */
  @Override
  public void navEvent(NavEvent event)
  {
    // if not component bail
    if (!(event.getParent() instanceof BComponent))
      return;

    // if not for my space bail
    BComponent component = (BComponent)event.getParent();
    if (component.getComponentSpace() != masterSpace)
      return;

    SyncOp op = null;

    switch(event.getId())
    {
      case NavEvent.ADDED:
        Property prop = component.getProperty(event.getNewChildName());
        BComponent child = (BComponent)component.get(prop);
        op = new AddOp(component, prop.getName(), child, prop.getDefaultFlags(), prop.getFacets(), NAV_EVENT_COMPONENT_DEPTH);
        break;
      case NavEvent.REMOVED:
        op = new RemoveOp(component, event.getOldChildName());
        break;
      case NavEvent.RENAMED:
        op = new RenameOp(component, event.getOldChildName(), event.getNewChildName());
        break;
      case NavEvent.REORDERED:
        op = new ReorderOp(component, event.getNewOrder());
        break;
      case NavEvent.REPLACED:
        // This event was previously ignored, but it needs to add a SetOp to the buffer
        String childName = event.getOldChildName();
        op = new SetOp(component, childName, null, NAV_EVENT_COMPONENT_DEPTH);
        break;
      case NavEvent.RECATEGORIZED:
        op = new SetCategoryMaskOp(component, event.getNewChildName());
        break;
    }

    if (op != null)
    {
      if (plugin == null || plugin.isValidSyncOp(op))
      {
        synchronized (bufferLock)
        {
          buffer.add(op);
        }
      }

      if (plugin != null) plugin.newSyncOp(op);
    }
  }

////////////////////////////////////////////////////////////////
// Subscriber
////////////////////////////////////////////////////////////////

  /**
   * Add the operation to the sync buffer.
   */
  @Override
  public void event(BComponentEvent event)
  {
    BComponent c = event.getSourceComponent();
    if (c.getComponentSpace() != masterSpace)
      throw new IllegalStateException();

    SyncOp op = null;

    switch(event.getId())
    {
      case BComponentEvent.PROPERTY_CHANGED:
        op = new SetOp(c, event.getSlotName(), null);
        break;
      case BComponentEvent.PROPERTY_ADDED:
        op = new AddOp(c, event.getSlotName(), event.getValue(), event.getSlotFlags(), event.getSlot().getFacets());
        break;
      case BComponentEvent.PROPERTY_REMOVED:
        op = new RemoveOp(c, event.getSlotName());
        break;
      case BComponentEvent.PROPERTY_RENAMED:
        op = new RenameOp(c, event.getValue().toString(), event.getSlotName());
        break;
      case BComponentEvent.PROPERTIES_REORDERED:
        // handled with nav event
        break;
      case BComponentEvent.TOPIC_FIRED:
        op = new FireTopicOp(c, event.getSlotName(), event.getValue());
        break;
      case BComponentEvent.FLAGS_CHANGED:
        op = new SetFlagsOp(c, event.getSlotName(), event.getSlotFlags());
        break;
      case BComponentEvent.FACETS_CHANGED:
        op = new SetFacetsOp(c, event.getSlotName(), (BFacets)event.getValue());
        break;
      case BComponentEvent.RECATEGORIZED:
        // handled with nav event
        break;
      case BComponentEvent.KNOB_ADDED:
        op = new AddKnobOp(c, event.getKnob());
        break;
      case BComponentEvent.KNOB_REMOVED:
        op = new RemoveKnobOp(c, event.getKnob());
        break;
      case BComponentEvent.RELATION_KNOB_ADDED:
        op = new AddRelationKnobOp(c, event.getRelationKnob());
        break;
      case BComponentEvent.RELATION_KNOB_REMOVED:
        op = new RemoveRelationKnobOp(c, event.getRelationKnob());
        break;
    }

    if (op != null)
    {
      if (plugin == null || plugin.isValidSyncOp(op))
      {
        synchronized (bufferLock)
        {
          buffer.add(op);
        }
      }

      if (plugin != null) plugin.newSyncOp(op);
    }
  }

////////////////////////////////////////////////////////////////
// ProxySyncBuffer
////////////////////////////////////////////////////////////////

  class ProxySyncBuffer extends SyncBuffer
  {
    ProxySyncBuffer(BComponentSpace space)
    {
      super(space, /*coalesce*/true);
    }
  }

  static class BufferLock {}

////////////////////////////////////////////////////////////////
// Proxy Broker Plug-in
////////////////////////////////////////////////////////////////

  @SuppressWarnings("unused")
  public interface IProxyBrokerPlugin
  {
    default boolean isValidSyncOp(SyncOp op) { return true; }

    void newSyncOp(SyncOp op);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BComponentEventMask PROXY_EVENT_MASK   = BComponentEventMask.make(
    (0x01 << BComponentEvent.PROPERTY_CHANGED) |
    (0x01 << BComponentEvent.PROPERTY_ADDED) |
    (0x01 << BComponentEvent.PROPERTY_REMOVED) |
    (0x01 << BComponentEvent.PROPERTY_RENAMED) |
    (0x01 << BComponentEvent.PROPERTIES_REORDERED) |
    (0x01 << BComponentEvent.TOPIC_FIRED) |
    (0x01 << BComponentEvent.FLAGS_CHANGED) |
    (0x01 << BComponentEvent.FACETS_CHANGED) |
    (0x01 << BComponentEvent.KNOB_ADDED) |
    (0x01 << BComponentEvent.KNOB_REMOVED) |
    (0x01 << BComponentEvent.RELATION_KNOB_ADDED) |
    (0x01 << BComponentEvent.RELATION_KNOB_REMOVED) |
    (0x01 << BComponentEvent.RECATEGORIZED)
    );

  private BComponentSpace masterSpace;
  private final Object bufferLock = new BufferLock();
  private ProxySyncBuffer buffer;

  private IProxyBrokerPlugin plugin;

  /**
   * In Niagara 4.8, ProxyBroker continues to send the entire Component for the Add and Replace Nav Events by default.
   * By adding "niagara.proxyBroker.deepNavEvents=false" as a system property, ProxyBroker will use a depth of zero
   * for serializing the Components those events and this can improve performance of Nav Events
   * sent via ProxyBroker.
   *
   * In Niagara 4.9+, ProxyBroker will no longer send the entire Component for the Add and Replace Nav Events by default.
   * By adding "niagara.proxyBroker.deepNavEvents=true" as a system property, ProxyBroker will send deep Nav events.
   *
   * @see javax.baja.io.ValueDocEncoder#encode
   * @since Niagara 4.8
   */
  private static final boolean DEEP_NAV_EVENT_DEPTH =
    AccessController.doPrivileged((PrivilegedAction<Boolean>)
      () -> Boolean.getBoolean("niagara.proxyBroker.deepNavEvents"));

  private static final int NAV_EVENT_COMPONENT_DEPTH = DEEP_NAV_EVENT_DEPTH ? Integer.MAX_VALUE : 0;

  static final Logger LOG = Logger.getLogger("sys.sync");
  static
  {
    if (LOG.isLoggable(Level.FINE))
    {
      LOG.fine("ProxyBroker is currently set to " + (DEEP_NAV_EVENT_DEPTH ? "" : "not ") + "send deep nav events.");
    }
  }
}
