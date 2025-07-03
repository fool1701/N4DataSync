/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.sys.*;

/**
 * SubscribeCallbacks provides a BComponentSpace the hooks 
 * to trap the subscription of its BComponents.
 * 
 *
 * @author    Brian Frank       
 * @creation  23 Oct 01
 * @version   $Revision: 6$ $Date: 3/17/11 10:36:01 AM EDT$
 * @since     Baja 1.0
 */
public class SubscribeCallbacks
{                                

  /**
   * This callback is invoked when a BComponent enters the 
   * subscribed state.  It is an indication that the component 
   * is actively being used and the space should attempt to 
   * maintain synchronization until unsubscribed.  This is the 
   * hook to implement demand based COV registration and polling.
   * If depth is non-zero, then the space should subscribe
   * to the components, plus all their descendents up to the 
   * specified depth.  A depth of one indicates child components, 
   * a depth of two grandchildren, and so on.
   * <p>
   * Since this method may be requesting a subscription of
   * several components at one time, it is possible that
   * the components within the subscription tree may already
   * be subscribed to.  It is the space's responsibility to
   * handle this case.
   * <p>
   * Default implementation does nothing.
   */
  public void subscribe(BComponent[] c, int depth)
  { 
  }

  /**
   * This callback is invoked when components enters the unsubscribed 
   * state.  It is an indication that the component is no longer 
   * actively being used and that demand based registration or polling 
   * may now be cleaned up.
   * <p>
   * Since this method may be requesting a unsubscription of
   * several components at one time, it is possible that
   * the components within the unsubscription tree may already
   * be unsubscribed.  It is the space's responsibility to
   * handle this case.
   * <p>
   * Default implementation does nothing.
   */
  public void unsubscribe(BComponent[] c)
  { 
  }
  
  /**
   * This callback is invoked when a component's update()
   * method is called.  It is used to indicate an up-to-date
   * snapshot of the component is needed, but not an ongoing
   * subscription.
   * <p>
   * Default implementation does nothing.
   */
  public void update(BComponent c, int depth)
  { 
  }              
}
