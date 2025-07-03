/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.list;

import javax.baja.ui.*;

/**
 * ListSubject is a Subject with additional List specific data.
 *
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 1$ $Date: 5/4/05 8:05:33 PM EDT$
 * @since     Baja 1.0
 */
public class ListSubject
  extends Subject
{                       

///////////////////////////////////////////////////////////
// Constructors
///////////////////////////////////////////////////////////

  /**
   * Convenience for <code>this(list, items, -1)</code>
   */
  public ListSubject(BList list, int[] items)
  {                                         
    this(list, items, -1);
  }
  
  /**
   * Construct with specified list of item indices and active item index.
   */
  public ListSubject(BList list, int[] items, int activeItem)
  {                                  
    super(map(list, items), map(list, activeItem));
    this.list = list;
    this.items = items;     
    this.activeItem = activeItem;
  }                           
  
  static Object[] map(BList list, int[] items)
  {              
    Object[] x = new Object[items.length];
    for(int i=0; i<x.length; ++i) x[i] = map(list, items[i]);
    return x;
  }

  static Object map(BList list, int item)
  {
    if (item < 0) return null;
    return list.getModel().getItem(item);
  }
  
///////////////////////////////////////////////////////////
// Access
///////////////////////////////////////////////////////////

  /**
   * Get the list associated with this subject.
   */
  public BList getList()
  {
    return list;
  }  

  /**
   * Get all the selected item indices.
   */
  public int[] getItems()
  {
    return items.clone();
  }                
  
  /**
   * Get the selected item index at the specified index. 
   */
  public int getItem(int index)
  {
    return items[index];
  }              
  
  /**
   * Get the active item index or -1
   */                             
  public int getActiveItem()
  {
    return activeItem;
  }
  
///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  
  private BList list;
  private int[] items;
  private int activeItem;
  
}

