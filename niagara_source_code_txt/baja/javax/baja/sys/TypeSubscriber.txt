/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.baja.space.BComponentSpace;
import javax.baja.spy.SpyWriter;

/**
 * @author    Lee Adcock
 * @creation  08 March 11
 * @version   $Revision: 4$ $Date: 4/11/11 3:56:16 PM EDT$
 * @since     Niagara 3.7
 */
public abstract class TypeSubscriber
{   

  protected TypeSubscriber(BComponentSpace space)
  {
    this.space = space;
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return true if the specified type is in 
   * this instance's subscription list.
   */
  public final synchronized boolean isSubscribed(Type t)
  {
    return subscriptions.contains(t);
  }                            
  
  /**
   * Return true if all the specified types are in 
   * this instance's subscription list.
   */
  public final synchronized boolean isSubscribed(Type[] t)
  {                                                          
    for(int i=0; i<t.length; ++i)
      if (!isSubscribed(t[i])) return false;
    return true;
  }                              
  
  /**
   * Get the number of types currently subscribed.
   */
  public final int getSubscriptionCount()
  {                                                   
    return subscriptions.size();
  }

  /**
   * Get an array of Types that this subscriber is 
   * currently subscribed to.
   */
  public final synchronized Type[] getSubscriptions()
  {  
    // Create a copy of the underlying array
    return new ArrayList<Type>(subscriptions).toArray(new Type[subscriptions.size()]);
  }

  /**
   * Convenience for {@code subsubscribe(new Type[] {t}, cx)}
   */
  public final synchronized void subscribe(Type t, Context cx)
  {                                                                            
    if(!t.is(BComponent.TYPE))
      throw new InvalidParameterException(t+" is not a BComponent type");
    subscribe(new Type[] { t }, cx);
  }

  /**
   * Subscribe to the list of specified types, and all their
   * ancestors up to depth.  A depth of zero indicated just
   * the component, one its children, two its grandchildren.
   */
  public final synchronized void subscribe(Type[] t, Context cx)
  { 
    // sanity checks          
    if (t.length == 0) return;
    for(int i=0; i<t.length; i++)
      if(!t[i].is(BComponent.TYPE))
        throw new InvalidParameterException(t[i]+" is not a BComponent type");

    updateSpaceSubscription(t, true);

    for(int i=0; i<t.length; ++i)
    {
      if(subscriptions.contains(t[i]))
        return;
      subscriptions.add(t[i]);
      // provide a callback for subclasses
      try { subscribed(t[i], cx); } catch(Throwable e) { e.printStackTrace(); }
    }  
  }                                
             
  /**
   * Convenience for {@code unsubscribe(c, null)}.
   */
  public final synchronized void unsubscribe(Type t)
  {                                   
    unsubscribe(t, null);
  }

  /**
   * Unsubscribe from the specified types.
   */
  public final synchronized void unsubscribe(Type t, Context cx)
  {                      
    unsubscribe(new Type[] { t }, cx);
  }
  
  /**
   * Unsubscribe from the list of specified types.
   */
  public final synchronized void unsubscribe(Type[] t, Context cx)
  { 
    // sanity checks          
    if (t.length == 0) return; 
    
    // Next update all spaces for the unsubscribe
    updateSpaceSubscription(t, false);
    
    for(int i=0; i<t.length; ++i)
    {
      subscriptions.remove(t[i]);
      // provide a callback for subclasses
      try { unsubscribed(t[i], cx); } catch(Throwable e) { e.printStackTrace(); }
    }
  }
  
  /**
   * Unsubscribe all the types in the 
   * {@code getSubscription()} list.
   */
  public final synchronized void unsubscribeAll()
  {
    unsubscribe(getSubscriptions(), null);
  }

  private void updateSpaceSubscription(Type[] t, boolean subscribe)
  {
    if (space != null) 
    {
      if (subscribe)
        space.subscribe(t, this);
      else
        space.unsubscribe(t, this);
    }
  }
  
  public final BComponentEventMask getMask()
  {
    return mask;
  }

  public final synchronized void setMask(BComponentEventMask mask)
  {
    this.mask = mask;
    if(space!=null && !subscriptions.isEmpty())
      space.updateSubscription(getSubscriptions(), this);
  }
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  
  
  /**
   * Callback when an event is received on subscribed type.
   */
  public abstract void event(BComponentEvent event);     
  
  /**
   * Callback when a new type is added to subscribers list.
   */
  protected void subscribed(Type t, Context cx)
  {
  }
  
  /**
   * Callback when a type is removed from subscribers list.
   */
  protected void unsubscribed(Type t, Context cx)
  {
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////
  
  /**
   * Dump debug info.
   */
  public void spy(SpyWriter out)
  {                  
    out.startTable(true);          
    Type[] t = getSubscriptions();
    out.trTitle("Subscriptions (" + t.length + " Types)", 1);
    for(int i=0; i<t.length; ++i)
      out.tr(t[i].getTypeSpec().toString());
    out.endTable();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private List<Type> subscriptions = new ArrayList<>();
  private BComponentSpace space;
  private BComponentEventMask mask = BComponentEventMask.SELF_EVENTS;
 
}