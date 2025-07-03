/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.sys.*;
import javax.baja.naming.*;
import javax.baja.virtual.*;

/**
 * LoadCallbacks provides a BComponentSpace the hooks to
 * lazy load components into its space as needed.
 *
 * @author    Brian Frank       
 * @creation  23 Oct 01
 * @version   $Revision: 8$ $Date: 12/20/06 4:50:40 PM EST$
 * @since     Niagara 3.0
 */
public class LoadCallbacks
{ 

////////////////////////////////////////////////////////////////
// Lazy Load
////////////////////////////////////////////////////////////////
 
  /**
   * Return true if this broker is supporting lazy load.  The
   * loadProperties() is only used if true is returned.  The
   * default implementation returns false.
   */
  public boolean isLazyLoad()
  {
    return false;
  }

  /**
   * This is a hook for lazy loading all the dynamic slots 
   * on the specified component.  It is invoked the first time 
   * the BComplex.loadSlots() is called.  If the space is supporting 
   * lazy load, then all the dynamic properties should be added 
   * at this time.  This method is guaranteed to be called only 
   * once per component instance.
   */
  public void loadSlots(BComponent c)
  {
    // Check for virtual case, and make call to virtual gateway if necessary
    BComponentSpace space = c.getComponentSpace();
    if ((space instanceof BVirtualComponentSpace) &&
        (c instanceof BVirtualComponent))
    {
      BVirtualGateway vGate = ((BVirtualComponentSpace)space).getVirtualGateway();
      if (vGate != null)
        vGate.loadVirtualSlots((BVirtualComponent)c);
    }
  }

  /**
   * This is a hook for lazy loading a single dynamic slot 
   * on the specified component (by slot name).  It is invoked during
   * slot path resolution.  If the space is supporting lazy load of
   * individual slots (ie. virtual spaces), then the dynamic property 
   * specified by the given slot name should be added at this time.
   *
   * Returns the Slot loaded, or null if individual lazy loading is
   * not supported. If null is returned, try using loadSlots() instead.
   *
   * @since     Niagara 3.2
   */
  public Slot loadSlot(BComponent c, String slotName)
  {
    BComponentSpace space = c.getComponentSpace();
    if ((space instanceof BVirtualComponentSpace) &&
        (c instanceof BVirtualComponent))
    {
      BVirtualGateway vGate = ((BVirtualComponentSpace)space).getVirtualGateway();
      if (vGate != null)
      {
        return vGate.loadVirtualSlot((BVirtualComponent)c, VirtualPath.toVirtualPathName(slotName));
      }
    }
    return null; // loadSlots() should be used for the null case.
  }
  
  /**
   * This is a trap for BValue.newCopy().  If the space
   * doesn't have its own cloning mechanism, then return null.
   * Otherwise the space should return a deep clone of the
   * specified values.
   * <p>
   * Default implementation returns null.
   */
  public BValue[] newCopy(BValue[] values, CopyHints hints)
  {
    return null;
  }
  
  
}
