/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import javax.baja.sys.*;
import javax.baja.category.*;
import javax.baja.naming.*;

/**
 * NavEvent indicates a change in the navigation tree.  
 * Events are fired on the BNavRoot.INSTANCE.
 *
 * @author    Brian Frank
 * @creation  22 Jan 03
 * @version   $Revision: 10$ $Date: 5/29/05 10:42:07 PM EDT$
 * @since     Baja 1.0
 */
public class NavEvent
{ 

////////////////////////////////////////////////////////////////
// Ids
////////////////////////////////////////////////////////////////

  /**
   * Indicates that a node has been added to the tree.
   * <ul>
   * <li>getParentOrd(): returns parent of child added</li>
   * <li>getNewChildName(): returns name of new child</li>
   * </ul>
   */
  public static final int ADDED = 1;

  /**
   * Indicates that a node has been removed from the tree.
   * <ul>
   * <li>getParentOrd(): returns parent of child removed</li>
   * <li>getOldChildName(): returns name in parent of object removed</li>
   * </ul>
   */
  public static final int REMOVED = 2;

  /**
   * Indicates that a node has been renamed under its parent.
   * <ul>
   * <li>getParentOrd(): returns parent of child renamed</li>
   * <li>getOldChildName(): returns original name of child in parent</li>
   * <li>getNewChildName(): returns new name of object renamed</li>
   * </ul>
   */
  public static final int RENAMED = 3;

  /**
   * Indicates that a node's children have been reordered.
   * <ul>
   * <li>getParentOrd(): returns parent object whose children are reordered</li>
   * <li>getNewOrder(): return list of child names in new order</li>
   * </ul>
   */
  public static final int REORDERED = 4;

  /**
   * Indicates that a child under a parent has been replaced with a new instance.
   * <ul>
   * <li>getParentOrd(): returns parent of child renamed</li>
   * <li>getOldChildName(): returns name of child in parent</li>
   * </ul>
   */
  public static final int REPLACED = 5;

  /**
   * Indicates that a BICategorizable child under a parent has had 
   * it's category mask modified.
   * <ul>
   * <li>getParentOrd(): returns parent of child renamed</li>
   * <li>getOldChildName(): encoding of old mask</li>
   * <li>getNewChildName(): encoding of new mask</li>
   * </ul>
   */
  public static final int RECATEGORIZED = 6;
  
  /**
   * Private String array for the id names.
   */
  private static String[] ID_STRINGS = 
  {
    "-",
    "added",
    "removed",
    "renamed",
    "reordered",
    "replaced", 
    "recategorized",
  };

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make the ADDED event.
   */
  public static NavEvent makeAdded(BOrd parentOrd, String newChildName, Context cx)
  {
    NavEvent event = new NavEvent(ADDED);
    event.parentOrd    = parentOrd;
    event.newChildName = newChildName;
    event.context      = cx;
    return event;
  }

  /**
   * Make the ADDED event.
   */
  public static NavEvent makeAdded(BINavNode parent, String newChildName, Context cx)
  {
    NavEvent event = makeAdded(parent.getNavOrd(), newChildName, cx);
    event.parent       = parent;
    return event;
  }

  /**
   * Make the REMOVED event.
   */
  public static NavEvent makeRemoved(BOrd parentOrd, String oldChildName, Context cx)
  {
    NavEvent event = new NavEvent(REMOVED);
    event.parentOrd    = parentOrd;
    event.oldChildName = oldChildName;
    event.context      = cx;
    return event;
  }

  /**
   * Make the REMOVED event.
   */
  public static NavEvent makeRemoved(BINavNode parent, String oldChildName, Context cx)
  {
    NavEvent event = makeRemoved(parent.getNavOrd(), oldChildName, cx);
    event.parent = parent;
    return event;
  }

  /**
   * Make the RENAMED event.
   */
  public static NavEvent makeRenamed(BOrd parentOrd, String oldChildName, String newChildName, Context cx)
  {
    NavEvent event = new NavEvent(RENAMED);
    event.parentOrd    = parentOrd;
    event.oldChildName = oldChildName;
    event.newChildName = newChildName;
    event.context      = cx;
    return event;
  }

  /**
   * Make the RENAMED event.
   */
  public static NavEvent makeRenamed(BINavNode parent, String oldChildName, String newChildName, Context cx)
  {
    NavEvent event = makeRenamed(parent.getNavOrd(), oldChildName, newChildName, cx);
    event.parent = parent;
    return event;
  }

  /**
   * Make the REORDERED event.
   */
  public static NavEvent makeReordered(BOrd parentOrd, String[] newOrder, Context cx)
  {
    NavEvent event = new NavEvent(REORDERED);
    event.parentOrd = parentOrd;
    event.newOrder  = newOrder;
    event.context   = cx;
    return event;
  }

  /**
   * Make the REORDERED event.
   */
  public static NavEvent makeReordered(BINavNode parent, String[] newOrder, Context cx)
  {
    NavEvent event = makeReordered(parent.getNavOrd(), newOrder, cx);
    event.parent = parent;
    return event;
  }

  /**
   * Make the REPLACED event.
   */
  public static NavEvent makeReplaced(BOrd parentOrd, String oldChildName, Context cx)
  {
    NavEvent event = new NavEvent(REPLACED);
    event.parentOrd = parentOrd;
    event.oldChildName = oldChildName;
    event.context      = cx;
    return event;
  }

  /**
   * Make the REPLACED event.
   */
  public static NavEvent makeReplaced(BINavNode parent, String oldChildName, Context cx)
  {
    NavEvent event = makeReplaced(parent.getNavOrd(), oldChildName, cx);
    event.parent = parent;
    return event;
  }

  /**
   * Make the RECATEGORIZED event.
   */
  public static NavEvent makeRecategorized(BOrd parentOrd, String oldMask, String newMask, Context cx)
  {
    NavEvent event = new NavEvent(RECATEGORIZED);
    event.parentOrd    = parentOrd;
    event.oldChildName = oldMask;
    event.newChildName = newMask;
    event.context      = cx;
    return event;
  }

  /**
   * Make the RECATEGORIZED event.
   */
  public static NavEvent makeRecategorized(BOrd parentOrd, BCategoryMask oldMask, BCategoryMask newMask, Context cx)
  {                             
    return makeRecategorized(parentOrd, oldMask.encodeToString(), newMask.encodeToString(), cx);
  }

  /**
   * Make the RECATEGORIZED event.
   */
  public static NavEvent makeRecategorized(BINavNode parent, BCategoryMask oldMask, BCategoryMask newMask, Context cx)
  {
    NavEvent event = makeRecategorized(parent.getNavOrd(), oldMask, newMask, cx);
    event.parent = parent;
    return event;
  }

  /**
   * Get a new NavEvent with the same parameters, except 
   * change the parentOrd to the value specified.
   */
  public static NavEvent make(NavEvent orig, BOrd parentOrd)
  {
    NavEvent event = new NavEvent(orig.id);
    event.parentOrd    = parentOrd;
    event.oldChildName = orig.oldChildName;
    event.newChildName = orig.newChildName;
    event.newOrder     = orig.newOrder;
    event.context      = orig.context;
    return event;
  }

  /**
   * Get a new NavEvent with the same parameters, except
   * change the parentOrd to the value specified and replace the
   * context with the passed-in context.
   */
  public static NavEvent make(NavEvent orig, BOrd parentOrd, Context cx)
  {
    NavEvent event = new NavEvent(orig.id);
    event.parentOrd    = parentOrd;
    event.oldChildName = orig.oldChildName;
    event.newChildName = orig.newChildName;
    event.newOrder     = orig.newOrder;
    event.context      = cx;
    return event;
  }


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private NavEvent(int id)
  {
    this.id = id;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the event id constant.
   */
  public int getId() { return id; }
  
  /**
   * See event id constant for details.
   */
  public BOrd getParentOrd() { return parentOrd; }

  /**
   * Get the parent node.  This value may be null, in
   * which case use <code>getParentOrd()</code>.
   */
  public BINavNode getParent() { return parent; }
  
  /**
   * See event id constant for details.
   */
  public String getOldChildName() { return oldChildName; }

  /**
   * See event id constant for details.
   */
  public String getNewChildName() { return newChildName; }  
  
  /**
   * Get the old child ord.
   */
  public BOrd getOldChildOrd()
  {                        
    // NOTE: this is not guaranteed to work for all ords
    if (oldChildOrd == null)
      oldChildOrd = toChildOrd(oldChildName);
    return oldChildOrd;
  }  

  /**
   * Get the new child ord.
   */
  public BOrd getNewChildOrd()
  {                        
    // NOTE: this is not guaranteed to work for all ords
    if (newChildOrd == null)
      newChildOrd = toChildOrd(newChildName);
    return newChildOrd;
  }  
  
  /**
   * See event id constant for details.
   */
  public String[] getNewOrder() { return newOrder; }   

  /**
   * Get the context which may provide additional meta-data
   * about the navigation event.
   */
  public Context getContext() { return context; }
  
  /**
   * If context is non-null return the context's facets, 
   * otherwise return BFacets.NULL.
   */
  public BFacets getFacets() 
  { 
    if (context != null) return BFacets.make(context.getFacets());
    return BFacets.NULL; 
  }
    
  /**
   * To string.
   */
  public String toString()
  {
    StringBuilder s = new StringBuilder("NavEvent[");
    s.append(ID_STRINGS[id]).append(" p=").append(parentOrd);
    if (oldChildName != null) s.append("  old=").append(oldChildName);
    if (newChildName != null) s.append("  new=").append(newChildName);
    if (newOrder != null) s.append("  order.len=").append(newOrder.length);
    s.append("]");
    return s.toString();
  }                  
  
  /**
   * Attempt to construct a child ord from the parent ord and
   * a child name.  This should probably be computed by the
   * guy calling the factory method to begin with, but this is
   * a pretty good hack for covering all the key use cases.
   */
  private BOrd toChildOrd(String childName)
  {            
    if (childName == null) return null;
    
    String parentOrdStr = parentOrd.toString();
    OrdQuery[] q = parentOrd.parse();
    OrdQuery last = q[q.length-1];
    if (last instanceof Path)
    {
      Path path = (Path)last;
      if (path.depth() == 0)
        return BOrd.make(parentOrdStr + childName);
      else
        return BOrd.make(parentOrdStr + "/" + childName);
    }
    else
    {
      return BOrd.make(parentOrdStr + "|" + childName + ":");
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int id;
  private BINavNode parent;
  private BOrd parentOrd;
  private String oldChildName;
  private String newChildName;
  private BOrd oldChildOrd;
  private BOrd newChildOrd;
  private String[] newOrder;       
  private Context context;
    
}
