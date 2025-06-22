/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.tree;

import java.util.ArrayList;
import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nav.BNavFileNode;
import javax.baja.nav.BNavFileSpace;
import javax.baja.space.BComponentSpace;
import javax.baja.space.BISpaceNode;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.tree.TreeNode;
import javax.baja.virtual.BVirtualGateway;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.workbench.nav.NavMonitor;

/**
 * NavTreeNode is a TreeNode used to visualize a BINavNode.
 *
 * @author    Brian Frank
 * @creation  14 Jan 03
 * @version   $Revision: 38$ $Date: 7/15/11 4:30:28 PM EDT$
 * @since     Baja 1.0
 */
public class NavTreeNode
  extends TreeNode
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a node as a child of the specified parent.
   * Nodes should be created using NavTreeModel as a factory.
   */
  public NavTreeNode(NavTreeNode parent, BINavNode navNode)
  {
    this((NavTreeModel)parent.getModel(), parent, navNode);
  }

  /**
   * Construct a NavTreeNode.  This method automatically
   * registers the node with the model using the specified
   * BINavNode key.  Nodes should be created using NavTreeModel
   * as a factory.
   */
  public NavTreeNode(NavTreeModel model, NavTreeNode parent, BINavNode navNode)
  {
    super(model, parent);
    this.navNode = navNode;
    this.navName = navNode.getNavName();
    this.navOrd  = navNode.getNavOrd();
    model.add(this);  // must do this after setting navNode
  }

////////////////////////////////////////////////////////////////
// TreeNode
////////////////////////////////////////////////////////////////

  /**
   * Get the target BINavNode.
   */
  public Object getSubject()
  {
    return navNode;
  }

  /**
   * Get the target BINavNode.
   */
  public BINavNode getNavNode()
  {
    return navNode;
  }

  /**
   * Return <code>navNode.getNavDisplayName()</code>
   */
  public String getText()
  {
    return navNode.getNavDisplayName(null);
  }

  /**
   * Return image for <code>navNode.getNavIcon()</code>
   */
  public BImage getIcon()
  {
    BIcon icon = navNode.getNavIcon();
    if (icon != this.icon)
    {
      this.icon = icon;
      this.iconImage = BImage.make(icon);
      this.iconImage.sync();
    }

    if (iconImage == null)
      iconImage = defaultIcon;

    if (navNode instanceof BISpaceNode &&
       ((BISpaceNode)navNode).isPendingMove())
      return iconImage.getDisabledImage();

    return iconImage;
  }

  /**
   * Return <code>navNode.hasNavChildren()</code>
   */
  public boolean hasChildren()
  {
    return navNode.hasNavChildren();
  }

  /**
   * Convenience for <code>getChildren().length</code>
   */
  public int getChildCount()
  {
    return getChildren().length;
  }

  /**
   * Convenience for <code>getChildren()[index]</code>
   */
  public TreeNode getChild(int index)
  {
    return getChildren()[index];
  }

////////////////////////////////////////////////////////////////
// Child Management
////////////////////////////////////////////////////////////////

  /**
   * Get a child by name or return null.  If we haven't
   * tried to load the children yet, then the autoLoad
   * flag indicates if that should be attempted first.
   * Return null if no matching child node.
   */
  public NavTreeNode getChild(String navName, boolean autoLoad)
  {
    if (children == null && !autoLoad) return null;
    NavTreeNode[] children = getChildren();
    for(int i=0; i<children.length; ++i)
      if (navName.equals(children[i].navName))
        return children[i];
    return null;
  }

  /**
   * Get a child nav node where eqaulity is determined using
   * the BINavNode.equals() method.  If we haven't tried to
   * load the children yet, then the autoLoad flag indicates
   * if that should be attempted first.  Return null if no
   * matching child node.
   */
  public NavTreeNode getChild(BINavNode navNode, boolean autoLoad)
  {
    if (children == null && !autoLoad) return null;
    NavTreeNode[] children = getChildren();
    for(int i=0; i<children.length; ++i)
      if (navNode.equals(children[i].navNode))
        return children[i];
    return null;
  }

  /**
   * If the children have not been build yet then call
   * buildChildren() and cache the result.  Otherwise
   * return the cached children node.
   */
  public NavTreeNode[] getChildren()
  {
    // 1/5/07 - If the buildChildren flag has been set, force a full load
    if (buildChildren || (children == null))
    {
      buildChildren = false; // always reset this flag on full load
      BWidgetShell shell = getShell();
      if (shell != null) shell.enterBusy();
      try
      {
        children = buildChildren();
      }
      catch(Throwable e)
      {
        children = new NavTreeNode[0];
        e.printStackTrace();
        BDialog.error(getTree(), BDialog.TITLE_ERROR, "Cannot expand tree", e);
      }
      finally
      {
        if (shell != null) shell.exitBusy();
      }
    }
    return children;
  }

  /**
   * Build the list of children nodes using the navNode's
   * getNavChildren() method.  Each of the tree nodes is
   * created using NavTreeModel.makeNavTreeNode().
   */
  protected NavTreeNode[] buildChildren()
  {
    NavTreeModel model = (NavTreeModel)getModel();
    BINavNode[] navKids = navNode.getNavChildren();
    ArrayList<NavTreeNode> acc = new ArrayList<>(navKids.length);
    for(int i=0; i<navKids.length; ++i)
    {
      NavTreeNode node = model.makeNavTreeNode(this, navKids[i]);
      if (node != null) acc.add(node);
    }
    return acc.toArray(new NavTreeNode[acc.size()]);
  }

  /**
   * Add the child node and update the tree.
   */
  public void addChild(NavTreeNode child)
  {
    if (children == null) return;
    if (child == null) throw new NullPointerException();

    NavTreeNode[] temp = new NavTreeNode[children.length+1];
    System.arraycopy(children, 0, temp, 0, children.length);
    temp[children.length] = child;
    children = temp;
    getModel().updateTree();
  }

  /**
   * Remove the specified child and update the tree.
   */
  public void removeChild(NavTreeNode child)
  {
    ((NavTreeModel)getModel()).remove(child);

    if (children == null || children.length == 0) return;
    if (child == null) throw new NullPointerException();

    // 1/5/07 begin changes
    // Special handling for virtuals (or any component types that can reset
    // isBrokerPropsLoaded() at runtime).
    // Since virtuals go back to a partial loaded state (indicated by a remove),
    // this check sets a performFullLoad flag if the isBrokerPropsLoaded() flag is false
    // for the component.  This ensures that the next time this nav tree node is
    // expanded, a full load will occur.
    if(getNavNode() instanceof BComponent)
    {
      BComponent comp = ((BComponent)getNavNode());
      if (comp instanceof BVirtualGateway)
      { // Special check for the virtual gateway case to route the call to its
        // virtual space's root
        try { comp = ((BVirtualGateway)comp).getVirtualSpace().getRootComponent(); }
        catch(Exception e) {}
      }
      if (!((ComponentSlotMap)comp.fw(Fw.SLOT_MAP)).isBrokerPropsLoaded())
      { // Indicates the component is not loaded (or partially loaded),
        // so we mark a flag to indicate a full load should occur on the next
        // expand.
        performFullLoad = true;
      }
    }
    // 1/5/07 end changes
//    else if (getNavNode() instanceof BFoxGatewaySpace)
//    {
//      BFoxGatewaySpace f = (BFoxGatewaySpace)getNavNode();
//      performFullLoad |= !((ComponentSlotMap)f.getRootComponent().fw(Fw.SLOT_MAP)).isBrokerPropsLoaded();
//    }

    NavTreeNode[] kids = children;
    NavTreeNode[] temp = new NavTreeNode[kids.length-1];
    boolean found = false;

    for(int i=0, n=0; i<kids.length; ++i)
    {
      boolean match = kids[i] == child;

      // not in kids list, so just bail
      if (!match && n >= temp.length)
        return;

      if (!found && match) found = true;
      else temp[n++] = kids[i];
    }

    if (found)
    {
      children = temp;

      // 1/5/07
      // If the child list is null or empty after the removal, then
      // reset the expanded state back to false.  This is appropriate
      // for all cases, not just the virtual case.
      if (children == null || children.length == 0)
        getTree().setExpanded(this, false);

      getModel().updateTree();
    }
  }

  /**
   * Replace the specified child and update the tree.
   */
  public void replaceChild(NavTreeNode oldChild, NavTreeNode newChild)
  {
    // remove old child from model
    ((NavTreeModel)getModel()).remove(oldChild);

    // add new child to model
    ((NavTreeModel)getModel()).add(newChild);

    // replace in my children array
    boolean match = false;
    for(int i=0, n=0; i<children.length; ++i)
    {
      if (children[i] == oldChild)
      {
        match = true;
        children[i] = newChild;
      }
    }
    if (!match) throw new IllegalStateException();

    // if the old child was expanded, then expand the
    // new child (however this will only map the expansion
    // one level depth, because we have no idea if the
    // new child's descendents are the same as the old child)
    if (oldChild.isExpanded())
      newChild.setExpanded(true);

    // update the tree
    getModel().updateTree();
  }


///////////////////////////////////////////////////////////
// Expansion callback overrides
///////////////////////////////////////////////////////////

  /**
   * Callback when the node is expanded.
   *
   * @since     Niagara 3.2
   */
  public void expanded()
  {
    // 1/5/07
    // This method is overridden here to check for the special virtual case.
    // Since virtuals can be partially loaded (or revert back to a partial
    // loaded state at runtime), this method sets the appropriate flags so
    // that a subsequent expand will cause a full load.
    if (performFullLoad)
    {
      performFullLoad = false; // reset
      buildChildren = true; // triggers the children to be reloaded
    }

    // Wake the NavMonitor thread to process immediately
    // on an expand (because we want to be sure any new visible
    // nodes get "touched")
    NavMonitor.touch();
  }

////////////////////////////////////////////////////////////////
// Refresh
////////////////////////////////////////////////////////////////

  /**
   * If the current thread is in the middle of refreshing the given
   * nav node, return true.  Otherwise, return false.  Also returns
   * false always if the supplied nav node is null.
   *
   * @since Niagara 3.5
   */
  public static boolean performingRefresh(BINavNode navNode)
  {
    if (navNode == null) return false;

    return (navNode == currentNodeInRefresh.get());
  }

  private static ThreadLocal<BINavNode> currentNodeInRefresh = new ThreadLocal<>();


  /**
   * Refresh forces this tree node to attempt to rebuild its
   * children without unnecessary changes to the expansion state.
   */
  public void refresh()
  {
    refresh(true);
  }

  void refresh(boolean update)
  {
    try
    {
      currentNodeInRefresh.set(navNode);

      // if component call update
      if (update && navNode instanceof BComponent)
      {
        BComponent comp = (BComponent)navNode;

        if (comp instanceof BVirtualGateway)
        { // Special check for the virtual gateway case to route the call to its
          // virtual space's root
          try { comp = ((BVirtualGateway)comp).getVirtualSpace().getRootComponent(); }
          catch(Exception e) {}
        }

        BComponentSpace space = comp.getComponentSpace();
        if (space != null) space.update(comp, 0);
      }

      // if nav file node, reload
      if (update && (navNode instanceof BNavFileSpace || navNode instanceof BNavFileNode))
      {
        BINavNode p = navNode;
        while(true)
        {
          if (p == null) break;
          if (p instanceof BFoxSession)
          {
            BFoxSession session = (BFoxSession)p;
            session.loadNavFileSpace();
            break;
          }
          p = p.getNavParent();
        }
      }

      // if never loaded, then bail
      if (children == null) return;

      NavTreeModel model = (NavTreeModel)getModel();

      // rebuild the nav children
      BINavNode[] navKids = navNode.getNavChildren();
      NavTreeNode[] existing = children.clone();
      ArrayList<NavTreeNode> acc = new ArrayList<>();
      for(int i=0; i<navKids.length; ++i)
      {
        BINavNode navKid = navKids[i];
        NavTreeNode treeKid = null;

        // attempt to get existing child, and if
        // found null in existing array
        for(int j=0; j<existing.length; ++j)
        {
          if (existing[j] != null && navKid.equals(existing[j].navNode))
          {
            treeKid = existing[j];
            treeKid.remap();
            existing[j] = null;
            break;
          }
        }


        // if we didn't find an existing child, then attempt
        // to create new tree node for it, although model can
        // reject by returning null
        if (treeKid == null)
          treeKid = model.makeNavTreeNode(this, navKid);

        // put into new array
        if (treeKid != null) acc.add(treeKid);
      }

      // any existing children not used need to be disposed of
      for(int i=0; i<existing.length; ++i)
        if (existing[i] != null)
          model.remove(existing[i]);

      // all done
      children = acc.toArray(new NavTreeNode[acc.size()]);
      getModel().updateTree();
    }
    finally
    {
      currentNodeInRefresh.set(null);
    }
  }

  /**
   * This method is used to refresh the sesh' when a user
   * interacts with the nav tree, in order to prevent being
   * automatically logged out.
   */
  public void resetSession()
  {
    BINavNode current = (BINavNode) this.getSubject();
    while (current != null)
    {
      if (current instanceof BFoxSession)
      {
        ((BFoxSession) current).userActivity();
        break;
      }
      current = current.getNavParent();
    }
  }

  /**
   * Check if navName or navOrd has changed, and if so
   * then recursively remap this node by updating the
   * navName, navOrd and the model's lookup table.
   */
  void remap()
  {
    String name = navNode.getNavName();
    BOrd ord  = navNode.getNavOrd();
    if (!name.equals(navName) || !ord.equals(navOrd))
    {
      NavTreeModel model = (NavTreeModel)getModel();
      model.remap(this, navOrd, ord);
      navName = name;
      navOrd = ord;
      if (children != null)
      {
        for(int i=0; i<children.length; ++i)
          children[i].remap();
      }
    }
  }



////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static BImage defaultIcon = BImage.make("module://icons/x16/object.png");

  final BINavNode navNode;
  String navName;
  BOrd navOrd;
  protected NavTreeNode[] children;
  BIcon icon;
  BImage iconImage;
  boolean isDragOver;

  boolean performFullLoad = false; // when true, an expand will set the buildChildren flag, so that a full load occurs
  boolean buildChildren = false; // when true, getChildren() will perform a full load
}

