/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.list;

import javax.baja.gx.*;
import javax.baja.ui.event.*;

/**
 * ListModel provides the class used to store the data 
 * items of a BList.
 *
 * @author    Brian Frank
 * @creation  9 Jul 02
 * @version   $Revision: 8$ $Date: 3/28/05 10:32:26 AM EST$
 * @since     Baja 1.0
 */
public abstract class ListModel
  extends BList.ListSupport
{

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the number of items in the model.
   */
  public abstract int getItemCount();

  /**
   * Get the index of the specified item or return -1 if
   * the item is not in the list.
   */
  public int indexOfItem(Object value)
  {
    int count = getItemCount();
    for(int i=0; i<count; ++i)
      if (getItem(i).equals(value))
        return i;
    return -1;
  }
  
  /**
   * Get the item value for the specified index using equals().
   */
  public abstract Object getItem(int index);
  
  /**
   * Return the BImage to use for the specified item,
   * or null if icons are not supported.
   */
  public BImage getItemIcon(int index)
  {
    return null;
  }

  /**
   * Convenience for <code>addItem(null, value)</code>.
   */
  public void addItem(Object value)
  {
    addItem(null, value);
  }

  /**
   * Add the specified item to the end of the list.
   * Default throws UnsupportedOperationException.
   */
  public void addItem(BImage icon, Object value)
  { 
    throw new UnsupportedOperationException(); 
  }

  /**
   * Convenience for <code>insertItem(index, null, value)</code>.
   */
  public void insertItem(int index, Object value)
  {
    insertItem(index, null, value);
  }
  
  /**
   * Insert the specified item at the given index into 
   * the list.  Default throws UnsupportedOperationException.
   */
  public void insertItem(int index, BImage icon, Object value)
  { 
    throw new UnsupportedOperationException(); 
  }

  /**
   * Convenience for <code>setItem(index, null, value)</code>.
   */
  public void setItem(int index, Object value)
  {
    setItem(index, null, value);
  }
  
  /**
   * Set the specified item at the given index into 
   * the list.  Default throws UnsupportedOperationException.
   */
  public void setItem(int index, BImage icon, Object value)
  { 
    throw new UnsupportedOperationException(); 
  }
  
  /**
   * Remove the specified item value.  Equality is checked 
   * using equals().  Default throws UnsupportedOperationException.
   */
  public void removeItem(Object value)
  { 
    throw new UnsupportedOperationException(); 
  }
  
  /**
   * Remove the value at the specified item index.
   * Default throws UnsupportedOperationException.
   */
  public void removeItem(int index)
  { 
    throw new UnsupportedOperationException(); 
  }
  
  /**
   * Remove all the items from the list.
   * Default throws UnsupportedOperationException.
   */
  public void removeAllItems()
  {
    throw new UnsupportedOperationException(); 
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  
  
  /**
   * This method should be called when the list model
   * has been modified, and the list requires an update.
   * It automatically fires a listModified event.
   */
  public void updateList()
  {
    BList list = getList();
    if (list != null) 
    {
      getList().relayout();
      getList().fireListModified(new BWidgetEvent(BWidgetEvent.MODIFIED, getList()));
    }
  }
  
}
