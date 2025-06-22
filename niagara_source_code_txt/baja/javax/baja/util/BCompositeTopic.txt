/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;

/**
 * BCompositeTopic is an implementation of BTopic
 * which maps to an action or topic through a link.
 *
 * @author    Brian Frank
 * @creation  6 Aug 03
 * @version   $Revision: 2$ $Date: 8/8/03 12:09:14 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BCompositeTopic
  extends BTopic
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BCompositeTopic(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCompositeTopic.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * If this topic is linked to another topic or action
   * via a link, then return the mirror information.  
   * If not linked return null.
   */
  public Mirror getMirror()
  {
    try
    {
      BComponent parent = getParentComponent();
      if (parent == null) return null;
      
      BComponentSpace space = parent.getComponentSpace();
      if (space == null) return null;
      
      BLink[] links = parent.getLinks(getPropertyInParent());
      if (links.length == 0) return null;
      
      BLink link = links[0];
      BOrd ord = link.getSourceOrd();
      String slotName = link.getSourceSlotName();
      BComponent c = (BComponent)ord.get(space);
      Slot slot = c.getSlot(slotName);
      
      if (slot == getPropertyInParent())
        return null;
      
      if (slot instanceof Topic || slot instanceof Action)
        return new Mirror(link, c, slot);
          
      return null;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }
  
////////////////////////////////////////////////////////////////
// BTopic
////////////////////////////////////////////////////////////////  

  /**
   * If the mirror is an action return its parameter type.  
   * If the mirror is a topic return its event type.
   * If not linked return null.
   */
  @Override
  public Type getEventType()
  {
    Mirror mirror = getMirror();
    if (mirror != null) 
    {
      Slot slot = mirror.slot;
      if (slot.isAction())
        return slot.asAction().getParameterType();
      else
        return slot.asTopic().getEventType();
    }
    return null;
  }
  
  /**
   * Get the facets for the action or return BFacets.NULL.
   */
  @Override
  public BFacets getFacets()
  {
    Mirror mirror = getMirror();
    if (mirror != null) 
      return mirror.component.getSlotFacets(mirror.slot);
    return null;
  }

////////////////////////////////////////////////////////////////
// Mirror
////////////////////////////////////////////////////////////////  

  /**
   * Class returned from <code>BCompositeTopic.getMirror()</code>
   */
  public static class Mirror
  {
    public Mirror(BLink link, BComponent component, Slot slot)
    {
      this.link = link;
      this.component = component;
      this.slot = slot;
    }
    
    public String toString()
    {
      return component.toPathString() + "." + slot.getName();
    }
    
    public BLink link;
    public BComponent component;
    public Slot slot;
  }
  
}
