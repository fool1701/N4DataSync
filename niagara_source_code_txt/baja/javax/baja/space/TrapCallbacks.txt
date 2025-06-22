/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.category.BCategoryMask;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;

/**
 * TrapCallbacks provides a BComponentSpace the hooks to
 * trap all changes to its BComponents.  To commit a change
 * and by pass trap callbacks use Context.commit.
 * 
 *
 * @author    Brian Frank       
 * creation  23 Oct 01
 * @version   $Revision: 4$ $Date: 2/12/05 4:51:10 PM EST$
 * @since     Baja 1.0
 */
public class TrapCallbacks
{ 

  /**
   * Return true if changes should be trapped, or false
   * to short circuit the entire process.  If true is returned, 
   * then the subclass must override add(), remove(), rename(), 
   * reorder(), setFlags(), and invoke().  Default implementation 
   * of this method is to return false.
   */
  public boolean isTrapEnabled()
  {
    return false;
  }
  
  /**
   * This callback is a performance hook for to indicate to 
   * if the set() callback should be invoked.  If true is
   * returned, then set() must be overridden and isTrapEnabled()
   * must return true.  Default is to return false.
   */
  public boolean trapSets()
  {
    return false;
  }
  
  /**
   * The set callback indicates that a slot under the component's
   * property tree has been modified through a call to BComplex.set().
   * Changes are only reported to BSimples or BStructs, but not
   * bubbled up further than the first BComponent ancestor.  
   *
   * @return true if you the property set should be 
   *    automtically be committed.
   */
  public boolean set(BComponent c, Property[] propertyPath, BValue value, Context context)
  {
    throw new UnsupportedOperationException();
  }
  
  /**
   * The add callback is a trap for BComponent.add().
   */
  public Property add(BComponent c, String name, BValue value, int flags, BFacets facets, Context context)
  {
    throw new UnsupportedOperationException();
  }
  
  /**
   * The remove callback is a trap for BComponent.remove().
   */
  public void remove(BComponent c, Property prop, Context context)
  {
    throw new UnsupportedOperationException();
  }
  
  /**
   * The rename callback is a trap for BComponent.rename().
   */
  public void rename(BComponent c, Property prop, String newName, Context context)
  {
    throw new UnsupportedOperationException();
  }
  
  /**
   * The reorder callback is a trap for BComponent.reorder().
   */
  public void reorder(BComponent c, Property[] order, Context context)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * The setFlags callback is a trap for BComponent.setFlags().
   */
  public void setFlags(BComponent c, Slot slot, int flags, Context context)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * The setFacets callback is a trap for BComponent.setFacets().
   */
  public void setFacets(BComponent c, Slot slot, BFacets facets, Context context)
  {
    throw new UnsupportedOperationException();
  }  

  /**
   * The setCategoryMask callback is a trap for BComponent.setCategoryMask().
   */
  public void setCategoryMask(BComponent c, BCategoryMask mask, Context context)
  {
    throw new UnsupportedOperationException();
  }                  

  /**
   * The invoke callback is a trap for BComponent.invoke().
   */
  public BValue invoke(BComponent c, Action action, BValue arg, Context context)
  {
    throw new UnsupportedOperationException();
  }
  
}
