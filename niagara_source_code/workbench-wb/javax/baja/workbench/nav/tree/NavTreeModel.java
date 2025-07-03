/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.tree;

import java.util.HashMap;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nav.BNavFileNode;
import javax.baja.nav.NavEvent;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.ui.tree.TreeModel;
import com.tridium.workbench.shell.WbMain;

/**
 * NavTreeModel is the TreeModel for a BNavTree.  The model works 
 * in tandem with NavTreeNode to provide support for mapping
 * BINavNodes to NavTreeNodes using a NavMap.  This allows fast 
 * lookups from NavEvents to NavTreeNodes for synchronization.
 *
 * @author    Brian Frank       
 * @creation  7 Mar 02
 * @version   $Revision: 15$ $Date: 11/15/06 1:10:30 PM EST$
 * @since     Baja 1.0
 */
public abstract class NavTreeModel
  extends TreeModel
{   

////////////////////////////////////////////////////////////////
// Map
////////////////////////////////////////////////////////////////
 
  /**
   * Given an ord, map it to a NavTreeNode if we have
   * expanded to that node.  Ords are mapped via the
   * ord returned from <code>BINavNode.getNavOrd()</code>.
   * Return null if no mapping.
   */
  public NavTreeNode lookup(BOrd ord)
  {
    return ordMap.get(ord);
  }

  /**
   * Add the tree node to the node map.  This happens
   * automatically in the NavTreeNode constructor.
   */
  public void add(NavTreeNode node)
  {
    // add to ord map
    if (node.navOrd != null)
      ordMap.put(node.navOrd, node);
      
    // add any children
    NavTreeNode[] kids = node.children;
    if (kids != null)
    {
      for(int i=0; i<kids.length; ++i)
        if (kids[i] != null) add(kids[i]);
    }
  }

  /**
   * Remove the tree node from the node map.  This 
   * happens automatically in NavTreeNode.removeChild().
   */
  public void remove(NavTreeNode node)
  {                    
    // remove from ordMap
    if (ordMap.get(node.navOrd) == node)
      ordMap.remove(node.navOrd);

    // remove any children
    NavTreeNode[] kids = node.children;
    if (kids != null)
    {
      for(int i=0; i<kids.length; ++i)
        if (kids[i] != null) remove(kids[i]);
    }
  }       
  
  /**
   * Remap the specified node in the ord lookup table.
   */
  void remap(NavTreeNode node, BOrd oldOrd, BOrd newOrd)
  {
    ordMap.remove(oldOrd);
    ordMap.put(newOrd, node);
  }
    
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  /**
   * Callback for NavEvents.  The BNavTree automatically
   * registers and unregisters for these events.  This
   * calls routes to added and removed.
   */
  public void navEvent(NavEvent event)
  {                        
    switch(event.getId())
    {
      case NavEvent.ADDED:     added(event); break;
      case NavEvent.REMOVED:   removed(event); break;
      case NavEvent.RENAMED:   renamed(event); break;
      case NavEvent.REORDERED: reordered(event); break;
      case NavEvent.REPLACED:  replaced(event); break;
    }
  }
  
  /**
   * Called from navEvent() when id is NavEvent.ADDED.
   * The default implementations attempts to lookup the
   * parent node.  If it has been mapped to a tree node
   * then we create a new node using makeNavTreeNode()
   * and add it using parent.addChild(child).
   */
  protected void added(NavEvent event)
  {
    NavTreeNode parent = eventToNode(event);
    if (parent != null)
    {
      BINavNode childNav = parent.navNode.getNavChild(event.getNewChildName());
      if (childNav != null)
      {
        NavTreeNode child = makeNavTreeNode(parent, childNav);
        if (child != null)
          parent.addChild(child);
      }
    }
  }

  /**
   * Called from navEvent() when id is NavEvent.REMOVED.
   * The default implementations attempts to lookup the
   * child node.  If it has been mapped to a tree node 
   * then we call parent.removeChild(child).
   */
  protected void removed(NavEvent event)
  {
    NavTreeNode parent = eventToNode(event);
    if (parent != null)
    {
      NavTreeNode child = parent.getChild(event.getOldChildName(), false);
      if (child != null)
        parent.removeChild(child);
    }
  }

  /**
   * Called from navEvent() when id is NavEvent.RENAMED.  The
   * default implementation calls refresh on the parent node.
   */
  protected void renamed(NavEvent event)
  {
    NavTreeNode parent = eventToNode(event);
    if (parent != null) parent.refresh(false);
  }

  /**
   * Called from navEvent() when id is NavEvent.REORDERED.  The
   * default implementation calls refresh on the parent node.
   */
  protected void reordered(NavEvent event)
  {
    NavTreeNode parent = eventToNode(event);
    if (parent != null) parent.refresh(false);
  }

  /**
   * Called from navEvent() when id is NavEvent.REPLACED.
   * Attempt to map child to a tree node.  If it has been
   * mapped, then remove from parent node, and then refresh 
   * the parent.
   */
  protected void replaced(NavEvent event)
  { 
    NavTreeNode parent = eventToNode(event);
    if (parent != null && parent.children != null) 
    { 
      // get old child
      NavTreeNode oldChild = parent.getChild(event.getOldChildName(), false);
      
      // get new child
      BINavNode newNav = parent.navNode.getNavChild(event.getOldChildName());
      NavTreeNode newChild = null;
      if (newNav != null) 
        newChild = makeNavTreeNode(parent, newNav);
      
      // remove, replace, or add
      if (oldChild != null)
      {                                     
        // if the event has the close facet set, then close
        // the old child so that we don't attempt to re-expand
        if (event.getFacets().getb("close", false))
          oldChild.setExpanded(false);
      
        if (newChild == null)
          parent.removeChild(oldChild);
        else
          parent.replaceChild(oldChild, newChild);
      }
      else
      {
        if (newChild != null)
          parent.addChild(newChild);
      }
    }
  }
  
  /**
   * Lookup the parent node by ord for the specified event.  
   * Ignore events for NavFileNode because otherwise I will add
   * and remove children for events that match the node's ord.
   */
  NavTreeNode eventToNode(NavEvent event)
  {
    NavTreeNode node = lookup(event.getParentOrd());
    if (node == null) return null;
    if (node.getNavNode() instanceof BNavFileNode) return null;
    return node;
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////  

  /**
   * This is the factory method called when new NavTreeNodes 
   * are required either thru expansion or eventing.
   */
  public NavTreeNode makeNavTreeNode(NavTreeNode parent, BINavNode navNode)
  {                                            
    // in kiosk mode we need to hide localhost's children
    if (parent != null && parent.navNode == BLocalHost.INSTANCE)
    {     
      BOrd ord = navNode.getNavOrd();
      if (!WbMain.isKioskAccessible(ord))
        return null;
    }
    
    if (navNode instanceof BComponent)
    {
      BComponent c = (BComponent)navNode;     
      BComplex cparent = c.getParent();
      Property cprop = c.getPropertyInParent();
      if (cparent != null && cprop != null && Flags.isHidden(cparent, cprop))
        return null;
    }
    
    return new NavTreeNode(this, parent, navNode);
  }                  
  
////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////  

  void dumpOrdMap()
  {
    System.out.println("===== NavTreeModel.ordMap =====");
    BOrd[] ords = ordMap.keySet().toArray(new BOrd[ordMap.size()]);
    SortUtil.sort(ords);
    for(int i=0; i<ords.length; ++i)                       
    {
      NavTreeNode node = lookup(ords[i]);
      System.out.println(" " + ords[i] + " -> " + node.navName + " @  " + node.navOrd);
    }
    System.out.println("===============================");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  
  HashMap<BOrd, NavTreeNode> ordMap = new HashMap<>();  // maps ords to NavTreeNodes
  
}
