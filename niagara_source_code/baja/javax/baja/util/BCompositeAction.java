/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;

/**
 * BCompositeAction is an implementation of BAction
 * which maps to another action as the knob of a link.
 *
 * @author    Brian Frank
 * @creation  6 Aug 03
 * @version   $Revision: 3$ $Date: 9/21/04 10:20:35 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BCompositeAction
  extends BAction
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BCompositeAction(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCompositeAction.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * If this action is linked to another action 
   * via a knob, then return the mirror information.  
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
      
      Knob[] knobs = parent.getKnobs(getPropertyInParent());
      if (knobs.length == 0) return null;
      
      Knob knob = knobs[0];
      BOrd ord = knob.getTargetOrd();
      String slotName = knob.getTargetSlotName();
      BComponent c = (BComponent)ord.get(space);
      Slot slot = c.getSlot(slotName);
      
      if (slot == getPropertyInParent()) return null;
      
      if (!(slot instanceof Action)) return null;
      
      return new Mirror(knob, c, (Action)slot);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }
  
////////////////////////////////////////////////////////////////
// BAction
////////////////////////////////////////////////////////////////  

  /**
   * Return <code>getMirror().getParameterType()</code>.
   * If no linked action return null.
   */
  @Override
  public Type getParameterType()
  {
    Mirror mirror = getMirror();
    if (mirror != null) 
      return mirror.action.getParameterType();
    return null;
  }
  
  /**
   * Return <code>getMirror().getParameterDefault()</code>.
   * If no linked action return null.
   */
  @Override
  public BValue getParameterDefault()
  {  
    Mirror mirror = getMirror();
    if (mirror != null) 
    {
      mirror.component.lease();
      return mirror.component.getActionParameterDefault(mirror.action);
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
    {
      // we don't do an update here, but it is fairly
      // certain that we did one for getParameterDefault()
      // and we can save ourselves a network call
      /*mirror.component.update();*/
      return mirror.component.getSlotFacets(mirror.action);
    }
    return null;
  }

  /**
   * Return null.
   */
  @Override
  public BValue invoke(BComponent target, BValue arg)
  {
    return null;
  }

  /**
   * Return null.
   */
  @Override
  public Type getReturnType()
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Mirror
////////////////////////////////////////////////////////////////  

  /**
   * Class returned from <code>BCompositeAction.getMirror()</code>
   */
  public static class Mirror
  {
    public Mirror(Knob knob, BComponent component, Action action)
    {
      this.knob = knob;
      this.component = component;
      this.action = action;
    }
    
    public String toString()
    {
      return component.toPathString() + "." + action.getName();
    }
    
    public Knob knob;
    public BComponent component;
    public Action action;
  }
  
}
