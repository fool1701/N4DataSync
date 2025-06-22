/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.category.BCategoryMask;
import javax.baja.space.TrapCallbacks;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;

/**
 * TrapToSyncBuffer provides an implementation of
 * BComponentSpace TrapCallbacks which traps everything
 * to a SyncBuffer.
 * 
 *
 * @author    Brian Frank       
 * creation  23 Oct 01
 * @version   $Revision: 4$ $Date: 2/12/05 4:51:11 PM EST$
 * @since     Baja 1.0
 */
public abstract class TrapToSyncBuffer
  extends TrapCallbacks
{            

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Get the SyncBuffer to use for mapping trap 
   * callbacks into SyncOps.
   */
  public abstract SyncBuffer getBuffer();

////////////////////////////////////////////////////////////////
// TrapCallbacks
////////////////////////////////////////////////////////////////

  /**
   * Return true.
   */
  @Override
  public boolean isTrapEnabled()
  {
    return true;
  }
  
  /**
   * Return true.
   */
  @Override
  public boolean trapSets()
  {
    return true;
  }
  
  /**
   * Add new SetOp to buffer.
   */
  @Override
  public boolean set(BComponent c, Property[] propertyPath, BValue value, Context context)
  {                        
    getBuffer().set(c, propertyPath, value, context);
    return false;
  }
  
  /**
   * Add new AddOp to buffer.
   */
  @Override
  public Property add(BComponent c, String name, BValue value, int flags, BFacets facets, Context context)
  {
    getBuffer().add(c, name, value, flags, facets, context);
    return null;
  }
  
  /**
   * Add new RemoveOp to buffer.
   */
  @Override
  public void remove(BComponent c, Property prop, Context context)
  {
    getBuffer().remove(c, prop, context);
  }
  
  /**
   * Add new RenameOp to buffer.
   */
  @Override
  public void rename(BComponent c, Property prop, String newName, Context context)
  {
    getBuffer().rename(c, prop, newName, context);
  }
  
  /**
   * Add new ReorderOp to buffer.
   */
  @Override
  public void reorder(BComponent c, Property[] order, Context context)
  {
    getBuffer().reorder(c, order, context);
  }

  /**
   * Add new SetFlagsOp to buffer.
   */
  @Override
  public void setFlags(BComponent c, Slot slot, int flags, Context context)
  {            
    getBuffer().setFlags(c, slot, flags, context);
  }

  /**
   * Add new SetFacetOp to buffer.
   */
  @Override
  public void setFacets(BComponent c, Slot slot, BFacets facets, Context context)
  {            
    getBuffer().setFacets(c, slot, facets, context);
  }

  /**
   * Add new SetCategoryMaskOp to buffer.
   */
  @Override
  public void setCategoryMask(BComponent c, BCategoryMask mask, Context context)
  {            
    getBuffer().setCategoryMask(c, mask, context);
  }

  /**
   * Commit back to component.
   */
  @Override
  public BValue invoke(BComponent c, Action action, BValue arg, Context context)
  {               
    return c.invoke(action, arg, Context.commit);
  }                    
  
}
