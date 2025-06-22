/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.tree;

import javax.baja.ui.event.*;

/**
 * TreeModel is the data model for the BTree.
 *
 * @author    John Sublett
 * @creation  04 Dec 2000
 * @version   $Revision: 9$ $Date: 3/28/05 10:32:41 AM EST$
 * @since     Niagara 1.0
 */
public abstract class TreeModel
  extends BTree.TreeSupport
{
  /**
   * Get the number of roots in the tree.  Roots are displayed as
   * the top level nodes in the tree.  The parent of a root node
   * must be null.
   */
  public abstract int getRootCount();
  
  /**
   * Get the root at the specified index.
   */
  public abstract TreeNode getRoot(int index);

  /**
   * Get the index of the specified root.
   */  
  public int getRootIndex(TreeNode root)
  {
    int rootCount = getRootCount();
    for (int i = 0; i < rootCount; i++)
      if (getRoot(i) == root)
        return i;
    
    return -1;
  }

  /**
   * This method should be called when the tree model
   * has been modified, and the tree requires an update.
   * It automatically fires the treeModified event.
   */
  public void updateTree()
  {
    BTree tree = getTree();
    if (tree != null) 
    {
      tree.relayout();
      tree.fireTreeModified(new BWidgetEvent(BWidgetEvent.MODIFIED, tree));
    }
  }

///////////////////////////////////////////////////////////
// Default
///////////////////////////////////////////////////////////

  public static class DefaultTreeModel
    extends TreeModel
  {
    public int getRootCount() { return 0; }
    public TreeNode getRoot(int index) { return null; }
  }

///////////////////////////////////////////////////////////
// Test
///////////////////////////////////////////////////////////
//
//  public static class TestTreeModel
//    extends TreeModel
//  {
//    public TestTreeModel(int rootCount, int childCount, int depth)
//    {
//      roots = new TreeNode[rootCount];
//      for (int i = 0; i < rootCount; i++)
//      {
//        roots[i] = new TreeNode.TestTreeNode(this, "root " + i, childCount, depth - 1);
//      }
//    }
//    
//    public int getRootCount()
//    {
//      return roots.length;
//    }
//    
//    public TreeNode getRoot(int index)
//    {
//      return roots[index];
//    }
//    
//    private TreeNode[] roots;
//  }
}