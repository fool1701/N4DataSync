/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.virtual;

import javax.baja.category.BCategoryMask;
import javax.baja.naming.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.spy.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.sys.schema.*;

/**
 * BVirtualComponentSpace is a space which contains a slot tree of 
 * BVirtualComponents.
 *
 * @author    Scott Hoye
 * @creation  24 Oct 06
 * @version   $Revision: 12$ $Date: 6/16/08 2:15:24 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
@AuditableSpace
public class BVirtualComponentSpace
  extends BComponentSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.virtual.BVirtualComponentSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVirtualComponentSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Constructor.  Creates and sets a new 
   * VirtualCacheCallbacks instance, and assigns the given virtual gateway.
   */
  public BVirtualComponentSpace(String name, 
                                LexiconText lexText, 
                                BOrd ordInSession, 
                                BVirtualGateway gateway)
  {
    super(name, lexText, BOrd.make(gateway.getOrdInSession(), "virtual:")); 
    this.gateway = gateway;
    setVirtualCacheCallbacks(new VirtualCacheCallbacks(this));
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////  

  /**
   * Starts this virtual component space instance by starting the
   * root component and virtual cache callbacks instance.
   *
   * This method is called by the corresponding virtual gateway
   * when it is started.
   */
  public final void start()
  {
    synchronized(lock)
    {
      if (isRunning) return; // already started
      
      // First start() the current root component
      BComponent currentRoot = getRootComponent();
      
      // Throw an exception if the space can't be started due to null
      // root or null virtual cache callbacks
      if (currentRoot == null)
        throw new IllegalStateException("Cannot start virtual space due to null root component.");
      if (virtualCacheCallbacks == null) 
        throw new IllegalStateException("Cannot start virtual space due to null virtual cache callbacks.");
      
      // start() the root and virtual cache callbacks
      currentRoot.start();    
      virtualCacheCallbacks.start();
      
      isRunning = true;
      started(); // Give subclasses a chance to do something.
    }
  }
  
  /**
   * This hook for subclasses is called when this
   * virtual component space is started.
   */
  public void started() { }
  
  /**
   * Stops this virtual component space instance by stopping the
   * root component and virtual cache callbacks instance.
   *
   * This method is called by the corresponding virtual gateway
   * when it is stopped.
   */
  public final void stop()
  {
    synchronized(lock)
    {
      if (!isRunning) return; // already stopped
      
      // stop() the root and virtual cache callbacks
      
      // Stop the virtual cache thread first to avoid a potential deadlock condition
      virtualCacheCallbacks.stop();
      getRootComponent().stop();
      
      isRunning = false;
      stopped(); // Give subclasses a chance to do something.
    }
  }
  
  /**
   * This hook for subclasses is called when this
   * virtual component space is stopped.
   */
  public void stopped() { }
  
  /**
   * Returns true if this virtual component space is running,
   * false otherwise.
   */
  public final boolean isRunning()
  { 
    return isRunning;
  }


////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the virtual gateway instance assigned to this virtual space.
   */
  public final BVirtualGateway getVirtualGateway()
  {
    return gateway;
  }  
  
  /**
   * Get the virtual cache callbacks for this virtual space.
   */
  public final VirtualCacheCallbacks getVirtualCacheCallbacks()
  {
    return virtualCacheCallbacks;
  }  

  /**
   * Set the virtual cache callbacks for this virtual space.
   * If there was an old one
   */
  public final void setVirtualCacheCallbacks(VirtualCacheCallbacks virtualCacheCallbacks)
  {
    synchronized(lock)
    {
      // If already running, throw an exception
      if (isRunning)
        throw new IllegalStateException("Cannot set virtual cache callbacks because the virtual space is already running.");
      
      // set it
      this.virtualCacheCallbacks = virtualCacheCallbacks;
    }
  }
  
//////////////////////////////////////////////////////////////////
// Component Space Overrides
//////////////////////////////////////////////////////////////////  

  /**
   * Set the root BComponent of this space.  By default, this
   * method will also start the root if it is not already running.
   * It will also stop the old root component, if there was one.
   */
  @Override
  public final void setRootComponent(BComponent root)
  {
    synchronized(lock)
    {
      // If already running, throw an exception
      if (isRunning)
        throw new IllegalStateException("Cannot set root component because the virtual space is already running.");
      
      super.setRootComponent(root);
    }
  }
  
  /**
   * Return true if the entire component space is readonly.
   * Default implementation returns true.
   */
  @Override
  public boolean isSpaceReadonly()
  {
    return true;
  }                    

//////////////////////////////////////////////////////////////////
// Space Overrides
//////////////////////////////////////////////////////////////////    
  
  /**
   * If this space is mounted, then return its parent 
   * host, otherwise return null.
   */
  @Override
  public BHost getHost()
  {            
    return gateway.getSpace().getHost();
  }

  /**
   * If this space is mounted, then return its parent 
   * session, otherwise return null.
   */
  @Override
  public BISession getSession()
  {    
    return gateway.getSpace().getSession();
  }
  
  /**
   * Get an host absolute ord which identifies this
   * space.  If not mounted return null.
   */
  @Override
  public BOrd getAbsoluteOrd()
  {
    return BOrd.make(gateway.getAbsoluteOrd(), "virtual:");
  }

  /**
   * Get the ord of this space relative to its host.
   */
  @Override
  public BOrd getOrdInHost()
  {
    return BOrd.make(gateway.getOrdInHost(), "virtual:");
  }

//////////////////////////////////////////////////////////////////
// Nav Node Overrides
//////////////////////////////////////////////////////////////////  

  /**
   * Overridden to return the nav ord of the virtual space's root component.
   */
  @Override
  public final BOrd getNavOrd()
  {
    return gateway.getNavOrd();
  }
  
  /**
   * Overridden to return the virtual gateway as the nav parent.
   */
  @Override
  public final BINavNode getNavParent()
  {
    return gateway;
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Overridden to be sure to include virtual spy info.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps();
    out.trTitle("VirtualComponentSpace", 2);
    out.prop("isRunning", isRunning);
    out.endProps();
    
    // next show spy for virtual cache callbacks
    if(virtualCacheCallbacks != null)
      virtualCacheCallbacks.spy(out);
    
    // next show spy for this component space
    super.spy(out);
    
    // finally show spy for the virtual space's root component
    BComponent root = getRootComponent();
    if (root != null)
    {
      out.startProps();
      out.trTitle("VirtualComponentSpace Root Component", 2);
      out.endProps();
      root.spy(out);
    }
  }


////////////////////////////////////////////////////////////////
// Hidden Support API
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if ((x == Fw.ENSURE_LOADED) && (gateway != null))
    {
      if (a instanceof SlotPath[])
      {
        SlotPath[] paths = (SlotPath[])a;
        int len = paths.length;
        BOrd[] ords = new BOrd[len];
        BOrd rootNavOrd = getNavOrd();
        for (int i = 0; i < len; i++)
          ords[i] = BOrd.make(rootNavOrd, paths[i]).normalize();
        gateway.ensureVirtualsLoaded(ords);
      }
      else
        gateway.ensureVirtualsLoaded((BOrd[])a);
    }
    if (x == Fw.TOUCH)
      touch((BOrd[])a);
    return super.fw(x, a, b, c, d);
  } 
  
  void touch(BOrd[] ordsInSpace)
  {
    if (ordsInSpace == null) return;
    for (int i = 0; i < ordsInSpace.length; i++)
    {
      try
      {
        BObject obj = ordsInSpace[i].get(this);
        if (obj instanceof BVirtualComponent)
          ((BVirtualComponent)obj).updateTicks();
      }
      catch (Exception e)
      {
        e.printStackTrace(); 
      }
    }
  }

  /**
   * Return the virtual gateway's category mask.
   * @since Niagara 4.3
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    BVirtualGateway vg = getVirtualGateway();
    if(vg==null)
    {
      return super.getCategoryMask();
    }
    else
    {
      return vg.getCategoryMask();
    }
  }

  /**
   * Return the virtual gateway's category mask.
   * @since Niagara 4.3
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    BVirtualGateway vg = getVirtualGateway();
    if(vg==null)
    {
      return super.getAppliedCategoryMask();
    }
    else
    {
      return vg.getAppliedCategoryMask();
    }
  }

//////////////////////////////////////////////////////////////////
// Attributes
//////////////////////////////////////////////////////////////////  

  VirtualCacheCallbacks virtualCacheCallbacks = null;//new VirtualCacheCallbacks();
  final BVirtualGateway gateway;
  boolean isRunning = false;
  final Object lock = new Object();
  
}
