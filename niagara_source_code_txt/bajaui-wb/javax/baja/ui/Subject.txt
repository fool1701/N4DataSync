/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.*;
import javax.baja.sys.*;

/**
 * Subject wraps up the current selection of a widget.
 *
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 2$ $Date: 1/26/06 3:16:04 PM EST$
 * @since     Baja 1.0
 */
public class Subject
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>this(list, null)</code>.
   */
  public Subject(Object[] list)     
  {
    this(list, null);
  }
  
  /**
   * Construct with the specified list of selected 
   * objects and active object.
   */
  public Subject(Object[] list, Object active)
  {         
    if (list == null) throw new NullPointerException();                                 
    this.list = list;
    this.active = active;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get all the selected objects.
   */
  public Object[] get()
  {
    return list.clone();
  }                

  /**
   * Get all the selected objects into the specified array 
   * which must have a length equal to size().
   */
  public Object[] get(Object[] array)
  {                                 
    if (array.length != list.length)
      throw new IllegalArgumentException("array.length != size()");
    System.arraycopy(list, 0, array, 0, list.length);
    return array;
  }                
  
  /**
   * Get the selected object at the specified index. 
   */
  public Object get(int index)
  {
    return list[index];
  }

  /**
   * Get the number of selected objects.
   */
  public int size()                     
  {
    return list.length;
  }                                           
  
  /**
   * Return is size is zero.
   */
  public boolean isEmpty()
  {
    return list.length == 0;
  }  
  
  /**
   * Get the active object.  For key eventing, the active object
   * is usually the object with focus.  For mouse eventing, the
   * active object is typically the object the mouse cursor is
   * over.  It is not uncommon for the active object to be null
   * in conjunction with a non-empty selection. 
   */
  public Object getActive()
  {
    return active;
  }                    
  
  /**
   * If this Subject contains BComponents, perform a  batch 
   * lease on them to force them in-sync.
   */
  public void lease()
  {                                                                        
    ArrayList<BComponent> c = new ArrayList<>();
    for (int i=0; i<list.length; ++i)
      if (list[i] instanceof BComponent)
        c.add((BComponent)list[i]);
        
    if (c.size() > 0)
    {    
      BComponent.lease(c.toArray(new BComponent[c.size()]), 0);
    }
  }  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Object[] list;
  private Object active;
  
}
