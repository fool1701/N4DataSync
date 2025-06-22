/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.tree;

import javax.baja.nav.BINavNode;
import javax.baja.ui.tree.TreeNode;

/**
 * DefaultNavTreeModel is designed to browse a single arbitrary 
 * INavNode root.  The roots of the tree are the nav children 
 * of the root node.  The root node itself is hidden.
 *
 * @author    Brian Frank       
 * @creation  7 Mar 02
 * @version   $Revision: 3$ $Date: 3/28/05 1:41:01 PM EST$
 * @since     Baja 1.0
 */
public class DefaultNavTreeModel
  extends NavTreeModel
{   

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor a DefaultNavTreeModel for the specified root.
   */
  public DefaultNavTreeModel(BINavNode rootNavNode)
  {
    this.root = new NavTreeNode(this, null, rootNavNode);
  }  

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the root BINavNode.
   */
  public BINavNode getRootNavNode()
  {
    return root.navNode;
  }

  /**
   * Set the root node to be visible/hidden.
   */
  public void setRootVisible(boolean b) { rootVisible = b; }

  /**
   * Return true if the root node is visible, false if hidden.
   */
  public boolean getRootVisible() { return rootVisible; } 

////////////////////////////////////////////////////////////////
// TreeModel
////////////////////////////////////////////////////////////////  

  /**
   * Get the number of root nodes. 
   */
  public int getRootCount()
  {
    return (rootVisible) ? 1 : root.getChildCount();
  }
  
  /**
   * Get the specified root.
   */
  public TreeNode getRoot(int index)
  {
    return (rootVisible) ? root : root.getChild(index);
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private NavTreeNode root;
  private boolean rootVisible = false;
}
