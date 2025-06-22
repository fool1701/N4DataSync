
/**
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.virtual;

import javax.baja.agent.*;
import javax.baja.category.*;
import javax.baja.naming.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.spy.*;
import javax.baja.sys.*;
import javax.baja.tag.TagDictionaryService;

import com.tridium.sys.schema.*;

/**
 * Captures information about a virtual component. 
 * Virtual Components are subject to automatic removal when no longer in use 
 * (ie. when unsubscribed and no active children), as managed by the the virtual 
 * component space's virtual cache callbacks.  The lastActiveTicks property
 * contains the tick count of the last time this virtual component moved from
 * an active to inactive state (ie. moved to an unsubscribed state).
 *
 * @author    Scott Hoye
 * @creation  14 Jul 06
 * @version   $Revision: 20$ $Date: 7/29/10 5:17:35 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
public class BVirtualComponent
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.virtual.BVirtualComponent(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVirtualComponent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

//////////////////////////////////////////////////////////////////
// Virtual Component
//////////////////////////////////////////////////////////////////
  
  /**
   * This is an override hook for subclasses if they wish to
   * disable the default behavior of auto removal for virtual components.
   * Auto removal means that virtual components will automatically
   * remove themselves when they are no longer in use (ie. unsubscribed and
   * have no subscribed children, or virtual children that don't perform auto
   * removal) and the virtual cache lifetime (defined by the parent virtual 
   * component space) has expired since the last unsubscription.
   *
   * The default value is to return true for this method (enables auto
   * removal).
   */
  public boolean performAutoRemoval()
  {
    return true;
  }

  /**
   * Get the last active ticks count.
   */
  public final long getLastActiveTicks() 
  { 
    return lastActiveTicks;
  }
  
  /**
   * Set the last active ticks count.
   */
  public final void setLastActiveTicks(long ticks) 
  { 
    lastActiveTicks = ticks;
  }
  
  /**
   * Returns the parent virtual gateway instance, or null if it can't be found.
   */
  public final BVirtualGateway getVirtualGateway()
  { 
    BComponentSpace space = getComponentSpace();
    if (space == null) return null;
    
    // The nav parent of the virtual space should always
    // be the virtual gateway.
    BINavNode navParent = space.getNavParent();
    if (navParent instanceof BVirtualGateway)
      return (BVirtualGateway)navParent;
    
    return null; // Could not find
  }
  
  /**
   * Return a "normalized" virtual ord for this virtual
   * component which can be used for identification in the
   * category service.  A "normalized" virtual ord is one
   * that uniquely identifies the object/point in the actual
   * device which is represented by this virtual component.  
   * In many cases, the default Ord returned by this method, 
   * getNavOrd(), will be sufficient.  However, in cases where 
   * you can have multiple virtual Ords that resolve to the
   * same object/point in the device, the result of this method 
   * should be the same "normalized" Ord for both virtual component 
   * instances.  This can happen if you allow facets to be included
   * in a virtual Ord to tweak the virtual representation of an 
   * object/point.  For such cases, you would want to strip out the
   * facet information in the "normalized" Ord returned by this method,
   * while keeping the necessary parentage virtual path information 
   * in tact (which also must be "normalized").
   * 
   * @since Niagara 3.4
   */
  public BOrd getCategorizableOrd()
  {
    BOrd navOrd = getNavOrd();
    if (navOrd != null)
      return navOrd.relativizeToSession();
    return null;
  }
  

////////////////////////////////////////////////////////////////
// Component Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Overridden to enforce that only virtual components and
   * BVectors can be children of virtual components.
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return ((child instanceof BVirtualComponent) || 
            (child instanceof BVector));
  }
  
  /**
   * Overridden to enforce that only virtual components can be
   * parents of virtual components.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BVirtualComponent);
  }
  
  /**
   * Overridden to incorporate the virtual address into the
   * nav ord.
   */
  @Override
  public final BOrd getNavOrd()
  {
    BComponentSpace space = getComponentSpace();
    if (space == null) return null;  
    // If the virtual gateway can't be found, this indicates
    // that this virtual component doesn't live in a virtual space, so
    // we don't want to risk calling getNavOrd() on a non-virtual component
    // space because this could result in a continuous loop if this
    // virtual component is the root.
    BOrd spaceOrd = (getVirtualGateway() == null)?space.getAbsoluteOrd():space.getNavOrd();
    if (spaceOrd == null) return null;
    SlotPath path = getSlotPath();   
    if (path == null) return null;   
    VirtualPath vPath = VirtualPath.convertFromSlotPath(path);
    return BOrd.make(spaceOrd, vPath).normalize();
  }

  /**
   * Overridden to use the virtual gateway to retrieve the
   * local TagDictionaryService.
   *
   * since Niagara 4.2
   */
  @Override
  public TagDictionaryService getTagDictionaryService()
  {
    BVirtualGateway vGateway = getVirtualGateway();
    if (vGateway != null)
      return vGateway.getTagDictionaryService();

    return super.getTagDictionaryService();
  }

////////////////////////////////////////////////////////////////
// Security overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Starting in Niagara 3.4, the default behavior of this method 
   * will be to check the category service's ord to category mask
   * map for the category mask to use for this virtual component.
   * (It uses the getCategorizableOrd() as the lookup key).
   * If it doesn't exist there, then it will default to use the 
   * applied category mask of the virtual gateway.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {                         
    BOrd ord = getCategorizableOrd();
    if (ord != null && !ord.isNull())
    {
      BCategoryService service = null;
      if (isRunning())
        service = BCategoryService.getService();
      else
      {
        service = (BCategoryService)BOrd.make(getVirtualGateway().getAbsoluteOrd(), "service:baja:CategoryService").get();
        service.lease(1);
      }
      BCategoryMask mask = service.getOrdMap().getAppliedCategoryMask(ord.relativizeToSession());
      if (mask != null) return mask;
    }
    
    BVirtualGateway gateway = getVirtualGateway();
    if (gateway != null) return gateway.getAppliedCategoryMask();
    return super.getAppliedCategoryMask();
  }
  
  /**
   * Starting in Niagara 3.4, the default behavior of this method 
   * will be to check the category service's ord to category mask
   * map for the category mask to use for this virtual component.
   * (It uses the getCategorizableOrd() as the lookup key).
   * If it doesn't exist there, then it will return BCategoryMask.NULL.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {            
    BOrd ord = getCategorizableOrd();
    if (ord != null && !ord.isNull())
    {
      BCategoryService service = null;
      if (isRunning() || (getVirtualGateway() == null))
        service = BCategoryService.getService();
      else
      {
        service = (BCategoryService)BOrd.make(getVirtualGateway().getAbsoluteOrd(), "service:baja:CategoryService").get();
        service.lease(1);
      }
      BCategoryMask mask = service.getOrdMap().getCategoryMask(ord.relativizeToSession());
      if (mask != null) return mask;
    }
    
    return BCategoryMask.NULL;
  }


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Override the agent list to remove the pxEditor, since
   * px views are not allowed on virtuals.  Also
   * removes the WireSheet and LinkSheet views since linking 
   * is not allowed for virtual components.
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.remove("pxEditor:PxEditor");
    list.remove("wiresheet:WireSheet");
    list.remove("wiresheet:WebWiresheet");
    list.remove("workbench:LinkSheet");
    list.remove("tagdictionary:TagManager");
    return list;
  }

  /**
   * Get the icon.  The default implement checks if 
   * there is a BIcon property called "icon".  If 
   * not then return a default virtual component icon.
   */
  @Override
  public BIcon getIcon()
  { 
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon;
  }
  private static final BIcon icon = BIcon.make(BIcon.std("object.png"), BIcon.std("badges/ghost.png"));


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
    out.trTitle("VirtualComponent", 2);
    out.prop("performAutoRemoval", performAutoRemoval());
    out.prop("lastActiveTicks", getLastActiveTicks()+" ("+BAbsTime.make(getLastActiveTicks()).encodeToString()+")");
    out.prop("categorizableOrd", getCategorizableOrd());
    out.endProps();
    super.spy(out);
  }
  

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {                               
    switch(x)
    {
      case Fw.STARTED:             updateTicks(); break;
      case Fw.STOPPED:             updateTicks(); break;
      case Fw.UNSUBSCRIBED:        updateTicks(); updateSpace(Fw.VIRTUAL_UNSUBSCRIBED); break;
      case Fw.SUBSCRIBED:          updateSpace(Fw.VIRTUAL_SUBSCRIBED); break;
    }
    return super.fw(x, a, b, c, d);
  }

  /**
   * Set the last active ticks count.
   */
  void updateTicks()
  {
    setLastActiveTicks(Clock.ticks());
  }

  /**
   * Propogates the given fwConstant to the space's fw()
   * callback.
   *
   * @since Niagara 4.1
   */
  private void updateSpace(int fwConstant)
  {
    BComponentSpace space = getComponentSpace();
    if (space != null)
    {
      space.fw(fwConstant, this, null, null, null);
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  long lastActiveTicks = Long.MAX_VALUE; // The tick count specifying the last time 
                                           // this virtual component moved from an active
                                           // to inactive state
                                           // (ie. moved to an unsubscribed state).

}
