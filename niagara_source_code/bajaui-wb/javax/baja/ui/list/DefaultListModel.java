/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.list;

import java.util.*;
import javax.baja.gx.*;

/**
 * DefaultListModel is a ready to use implementation of ListModel
 * that supports both icons and arbitrary Object items.
 *
 * @author    Brian Frank
 * @creation  9 Jul 02
 * @version   $Revision: 8$ $Date: 3/28/05 10:32:26 AM EST$
 * @since     Baja 1.0
 */
public class DefaultListModel
  extends ListModel
{

////////////////////////////////////////////////////////////////
// ListModel
////////////////////////////////////////////////////////////////

  public int getItemCount()
  {
    return items.size();
  }

  public int indexOfItem(Object value)
  {
    for(int i=0; i<items.size(); ++i)
      if (items.get(i).value.equals(value))
        return i;
    return -1;
  }
  
  public Object getItem(int index)
  {
    return items.get(index).value;
  }
  
  public BImage getItemIcon(int index)
  {
    return items.get(index).icon;
  }

  public void addItem(BImage icon, Object value)
  {
    items.add(new Item(icon, value));
    updateList();
  }
  
  public void insertItem(int index, BImage icon, Object value)
  {
    items.add(index, new Item(icon, value));
    updateList();
  }

  public void setItem(int index, BImage icon, Object value)
  {
    items.set(index, new Item(icon, value));
    updateList();
  }
  
  public void removeItem(Object value)
  {
    for(int i=0; i<items.size(); ++i)
      if (items.get(i).value.equals(value))
        { items.remove(i); updateList(); return; }
  }
  
  public void removeItem(int index)
  {
    items.remove(index);
    updateList();
  }
  
  /**
   * Remove all the values from the list.
   */
  public void removeAllItems()
  {
    items.clear();
    updateList();
  }  

////////////////////////////////////////////////////////////////
// Item
////////////////////////////////////////////////////////////////  

  public static class Item
  {
    public Item(BImage icon, Object value) 
    { 
      this.icon = icon; 
      this.value = value; 
    }
    
    public Item(Object value) 
    { 
      this.value = value; 
    }
    
    public BImage icon;
    public Object value;    
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** This list contains Item instances which wrap the
      item icon and value */
  protected ArrayList<Item> items = new ArrayList<>();
  
}
