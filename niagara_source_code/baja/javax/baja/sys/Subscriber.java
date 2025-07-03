/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.util.ObjectUtil;

import javax.baja.naming.BOrd;
import javax.baja.naming.BatchResolve;
import javax.baja.space.BComponentSpace;
import javax.baja.spy.SpyWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Subscriber is a listener for ComponentEvents on zero or more
 * BComponents.  Subscriber handles maintaining the list of components
 * which are subscribed. Subclasses decide how to process the events
 * by overriding the abstract {@code event()} method.
 *
 * @author    Brian Frank
 * @creation  26 Apr 02
 * @version   $Revision: 26$ $Date: 4/11/11 3:56:16 PM EDT$
 * @since     Baja 1.0
 */
public abstract class Subscriber
{
  /**
   * Return an instance of a subscriber with the specified event handler.
   *
   * @param eventHandler Invoked when the subscriber has a component event.
   * @return A subscriber.
   */
  public static final Subscriber make(Consumer<BComponentEvent> eventHandler)
  {
    Objects.requireNonNull(eventHandler);

    return new Subscriber()
    {
      @Override
      public void event(BComponentEvent event)
      {
        eventHandler.accept(event);
      }
    };
  }

  /**
   * Return an instance of a subscriber with the specified event,
   * subscriber and unsubscribed handlers.
   *
   * @param eventHandler Invoked when the subscriber has a component event.
   * @param subscribedHandler Invoked when a new component is added to the subscribers list.
   * @param unsubscribedHandler Invoked when a new component is removed from the subscribers list.
   * @return A subscriber.
   */
  public static final Subscriber make(Consumer<BComponentEvent> eventHandler,
                                      BiConsumer<BComponent, Context> subscribedHandler,
                                      BiConsumer<BComponent, Context> unsubscribedHandler)
  {
    Objects.requireNonNull(eventHandler);
    Objects.requireNonNull(subscribedHandler);
    Objects.requireNonNull(unsubscribedHandler);

    return new Subscriber()
    {
      @Override
      public void event(BComponentEvent event)
      {
        eventHandler.accept(event);
      }

      @Override
      protected void subscribed(BComponent c, Context cx)
      {
        subscribedHandler.accept(c, cx);
      }

      @Override
      protected void unsubscribed(BComponent c, Context cx)
      {
        unsubscribedHandler.accept(c, cx);
      }
    };
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return true if the specified component is in 
   * this instance's subscription list.
   */
  public final synchronized boolean isSubscribed(BComponent component)
  {
    return isSubscribed(component, this);
  }                            
  
  /**
   * Return true if all the specified components are in 
   * this instance's subscription list.
   */
  public final synchronized boolean isSubscribed(BComponent[] components)
  {
    for (BComponent comp : components)
    {
      if (!isSubscribed(comp, this))
      {
        return false;
      }
    }
    return true;
  }                              
  
  /**
   * Get the number of components currently subscribed.
   */
  public final int getSubscriptionCount()
  {
    int count = 0;
    for (BComponent comp : getKnownComponents())
    {
      if (isSubscribed(comp, this))
      {
        ++count;
      }
    }
    return count;
  }

  /**
   * Get the list of components this subscriber is 
   * currently subscribed to.
   */
  public final synchronized BComponent[] getSubscriptions()
  {
    BComponent[] knownComponents = getKnownComponents();
    List<BComponent> subscribedComponents = null;

    int size = knownComponents.length;
    for (int i = 0; i < size; i++)
    {
      BComponent comp = knownComponents[i];
      if (isSubscribed(comp, this))
      {
        if (subscribedComponents != null)
        {
          subscribedComponents.add(comp);
        }
      }
      else if (subscribedComponents == null)
      {
        // In the rare case where we had an orphaned component in the known components,
        // lazily initialize the subscribed list and back fill it with the known components
        // up to this point because all of the ones prior to this first orphaned one must
        // have been legit entries
        subscribedComponents = new ArrayList<>(size - 1);

        //noinspection ManualArrayToCollectionCopy
        for (int j = 0; j < i; j++)
        {
          subscribedComponents.add(knownComponents[j]);
        }
      }
    }

    if (subscribedComponents != null)
    {
      // We had at least one element removed from knownComponents, so return a new array
      // of just the legit subscribed components
      return subscribedComponents.toArray(EMPTY_COMPONENT_ARRAY);
    }
    else
    {
      // Everything in the knownComponents array checked out, so we can return it
      return knownComponents;
    }
  }

  /**
   * Convenience for {@code subscribe(c, 0, null)}
   */
  public final synchronized void subscribe(BComponent c)
  {
    subscribe(c, 0, null);
  }

  /**
   * Convenience for {@code subscribe(c, depth, null)}
   */
  public final synchronized void subscribe(BComponent c, int depth)
  { 
    subscribe(c, depth, null);
  }

  /**
   * Convenience for {@code subscribe(new BComponent[] {c}, depth, cx)}
   */
  public final synchronized void subscribe(BComponent c, int depth, Context cx)
  {                                                                            
    subscribe(new BComponent[] { c }, depth, cx);
  }

  /**
   * Subscribe to the list of specified components, and all their
   * ancestors up to depth.  A depth of zero indicated just
   * the component, one its children, two its grandchildren.
   */
  public final synchronized void subscribe(BComponent[] c, int depth, Context cx)
  { 
    // sanity checks          
    if (c.length == 0) return;
    
    try
    {
      // Starting in 3.7, we need to give the component a heads up about
      // a pending subscription so that on the proxy side, we won't miss
      // component events that could occur in the brief time between a
      // subscription request from the proxy side and the round trip
      // notification of a successful subscription response from the 
      // master side.
      for(int i=0; i<c.length; ++i)
      {
        // if not in map, then add it, and make slot map callback
        if (subscriptions.get(c[i]) == null)
          ((ComponentSlotMap)c[i].getSlotMap()).setPendingSubscribe(this);
      }
    
      // verify all components share the same space, 
     // otherwise hell could break loose
      // 12/19/06 - S.H. - I'm going to allow all hell to break loose...
      // OLD CODE    
      //BComponentSpace space = c[0].getComponentSpace();
      //for(int i=1; i<c.length; ++i)
      //  if (c[i].getComponentSpace() != space)
      //    throw new IllegalArgumentException("All components must be in same space");

      // Unleash hell...    
      // update all spaces for the subscribe
      updateSpaceSubscription(c, true, depth);

      // NOTE: subscriber callbacks should be made without holding
      // the lock to this guy; that requires recursing through the
      // tree to add into my map, and building up a list for callbacks
      // to make outside the synchronized block;  refer to stack dumps
      // from Les, re: stations link down, 18 aug 04     
    
      // recursively add to my table and make slot map and subscribed callbacks
      doSubscribe(c, depth, cx);
    }
    finally
    {
      // Be sure to clear any pending subscription states at this point
      for(int i=0; i<c.length; ++i)
      {
        ((ComponentSlotMap)c[i].getSlotMap()).clearPendingSubscribe(this);
      }
    }
  }                                
  
  /**
   * Resolve and subscribe to all the of specified ords.  
   * Return a matching array for the resolved components. 
   */
  public final BComponent[] subscribe(BComponentSpace space, BOrd[] ords, int depth, Context cx)
  {                             
    BatchResolve r = new BatchResolve(ords).resolve(space, cx);    
    BComponent[] components = new BComponent[ords.length];
    for(int i=0; i<components.length; ++i) components[i] = (BComponent)r.get(i);
    subscribe(components, depth, cx);
    return components;
  }
  
  private void doSubscribe(BComponent[] c, int depth, Context cx)
  {
    for(int i=0; i<c.length; ++i)
      doSubscribe(c[i], depth, cx);
  }
  
  private void doSubscribe(BComponent c, int depth, Context cx)
  {
    // if not in map, then add it, and make slot map callback
    if (!isSubscribed(c, this))
    {
      subscriptions.put(c, c);
      ((ComponentSlotMap)c.getSlotMap()).subscribe(this);
    }
    
    // make subscribed callback
    try { subscribed(c, cx); } catch(Throwable e) { e.printStackTrace(); }
    
    if (depth > 0)
    {
      c.loadSlots();
  
      for (BComponent kid : c.getChildComponents())
      {
        doSubscribe(kid, depth - 1, cx);
      }
    }
  }

  /**
   * @param comp component instance
   * @param subscriber subscriber instance
   * @return true if the subscriber has the component in its list of subscribed
   * components, and the component also has an active subscription using this
   * subscriber instance in its slot map
   */
  private static boolean isSubscribed(BComponent comp, Subscriber subscriber)
  {
    if (comp == null) { return false; }

    if (!isKnownToSubscriber(comp, subscriber)) { return false; }

    // check has to be both ways, because if component was unmounted while
    // subscribed, it would have forgotten its subscribers, but not vice
    // versa. see NCCB-50270.
    boolean componentIsSubscribed =
      (Boolean) comp.fw(Fw.IS_SUBSCRIBED, subscriber, null, null, null);

    // if the component has forgotten the subscriber, let the subscriber also
    // forget the component. save the extra fw call next time and some heap.
    if (!componentIsSubscribed)
    {
      subscriber.subscriptions.remove(comp);
    }

    return componentIsSubscribed;
  }

  /**
   * @param comp component instance
   * @param subscriber subscriber instance
   * @return true if the subscriber has the component in its list of subscribed
   * components - does not imply the reverse (the component could have forgotten
   * the subscriber already)
   */
  private static boolean isKnownToSubscriber(BComponent comp, Subscriber subscriber)
  {
    return subscriber.subscriptions.containsKey(comp);
  }

  /**
   * Only iterate known components using this method to avoid
   * ConcurrentModificationExceptions when forgetting unmounted components in
   * isSubscribed().
   *
   * @return array of components I think I am subscribed to - may include
   * unmounted components that have abandoned their subscriptions to me.
   */
  private BComponent[] getKnownComponents()
  {
    return subscriptions.keySet().toArray(EMPTY_COMPONENT_ARRAY);
  }
  
  /**
   * Convenience for {@code unsubscribe(c, null)}.
   */
  public final synchronized void unsubscribe(BComponent c)
  {                                   
    unsubscribe(c, null);
  }

  /**
   * Unsubscribe from the specified component.
   */
  public final synchronized void unsubscribe(BComponent c, Context cx)
  {                      
    unsubscribe(new BComponent[] { c }, cx);
  }
  
  /**
   * Unsubscribe from the list of specified components.
   *
   * @since     Niagara 3.2
   */
  public final synchronized void unsubscribe(BComponent[] c, Context cx)
  { 
    // sanity checks          
    if (c.length == 0) return; 
    
    // recursively remove from my table and make slot map, space, and unsubscribed callbacks
    doUnsubscribe(c, cx);
  }
  
  /**
   * Unsubscribe all the component's in the 
   * {@code getSubscription()} list.
   */
  public final synchronized void unsubscribeAll()
  {
    unsubscribe(getSubscriptions(), null);
  }
  
  private void doUnsubscribe(BComponent[] c, Context cx)
  {
    // First update the component slot map of the unsubscription
    // (this will ensure that the subscription count is updated).
    for(int i=0; i<c.length; ++i)
    {
      // component only needs to be known to the subscriber, not necessarily
      // vice versa. if the component has forgotten this subscriber, the
      // unsubscribe() call will be a noop.
      if (isKnownToSubscriber(c[i], this))
      {
        subscriptions.remove(c[i]);
        ((ComponentSlotMap)c[i].getSlotMap()).unsubscribe(this);
      }
    }
    
    // Next update all spaces for the unsubscribe
    updateSpaceSubscription(c, false, 0);
    
    // Finally provide a callback for subclasses
    for(int i=0; i<c.length; ++i)
    {
      try { unsubscribed(c[i], cx); } catch(Throwable e) { e.printStackTrace(); }
    }
  }
  
  private void updateSpaceSubscription(BComponent[] c, boolean subscribe, int depth)
  {
    // map components into buckets by space
    HashMap<BComponentSpace, List<BComponent>> bySpace = new HashMap<>();
    for(int i=0; i<c.length; ++i)
    {
      BComponent comp = c[i];
      
       // Don't perform the space unsubscribe call with components 
       // that are still in a subscribed state!
      if ((!subscribe) && comp.isSubscribed()) continue;
      
      BComponentSpace space = comp.getComponentSpace();
      List<BComponent> bucket = bySpace.get(space);
      if (bucket == null) 
        bySpace.put(space, bucket = new ArrayList<BComponent>());
      bucket.add(comp);
    }             
    
    // loop through the space buckets, and 
    // make the callback to the space (if the space is remote this 
    // results in a network call); note that we let the space figure 
    // out if me or other subscribers (like lease mgr) have already 
    // subscribed over the network
    Iterator<BComponentSpace> it = bySpace.keySet().iterator();
    while(it.hasNext())
    {
      BComponentSpace space = it.next();
      if (space != null) 
      {
        List<BComponent> bucket = (bySpace.get(space));
        BComponent[] bucketArray = bucket.toArray(new BComponent[bucket.size()]);
        if (subscribe)
        {
          space.getSubscribeCallbacks().subscribe(bucketArray, depth);
        }
        else
          space.getSubscribeCallbacks().unsubscribe(bucketArray);
      }
    } 
  }
 
  public final BComponentEventMask getMask()
  {
    return mask;
  }

  public final void setMask(BComponentEventMask mask)
  {
    this.mask = mask;
  }
 
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  
  
  /**
   * Callback when an event is received on subscribed component.
   */
  public abstract void event(BComponentEvent event);     
    
  /**
   * Callback when a new component is added to the subscribers list.
   */
  protected void subscribed(BComponent c, Context cx)
  {
  }
  
  /**
   * Callback when a component is removed from the subscribers list.
   */
  protected void unsubscribed(BComponent c, Context cx)
  {
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////
  
  /**
   * This method is called to unsubscribe any component which
   * is unmounted.  This allows the Subscriber to free the reference
   * and make the component available for garbage collection.  
   * Because this API is used with both mounted and unmounted 
   * components, this call is never invoked automatically.
   */
  public synchronized void gc()
  {                              
    int n = 0;
    BComponent[] c = getSubscriptions();
    for(int i=0; i<c.length; ++i)
    {
      if (!c[i].isMounted()) 
      {
        unsubscribe(c[i], null);
        n++;
      }
    }       
    //System.out.println("Subscriber.gc() freed=" + n + " thread=" + Thread.currentThread().getName());
  }  
  
  /**
   * Dump debug info.
   */
  public void spy(SpyWriter out)
  {                  
    out.startTable(true);
    ArrayList<BComponent> list = new ArrayList<>(Arrays.asList(getSubscriptions()));
    list.sort(Comparator.comparing(BComponent::toPathString));

    out.trTitle("Subscriptions (" + list.size() + " Components)", 1);
    for (BComponent bComponent : list)
    {
      out.tr().td()
        .a(String.valueOf(ObjectUtil.toSpyRelative(bComponent)), bComponent.toPathString())
        .endTd().endTr();
    }
    out.endTable();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private HashMap<BComponent, BComponent> subscriptions = new HashMap<>();
  private BComponentEventMask mask = BComponentEventMask.PROPERTY_EVENTS;
  private static final BComponent[] EMPTY_COMPONENT_ARRAY = new BComponent[0];
}
