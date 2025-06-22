/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.tree;

import java.util.*;
import javax.baja.ui.event.*;

/**
 * A TreeSelection manages selection for the BTree.  
 *
 * @author    John Sublett
 * @author    Mike Jarmy
 * @creation  07 Dec 2001
 * @version   $Revision: 8$ $Date: 6/11/07 12:41:33 PM EDT$
 * @since     Baja 1.0
 */
public class TreeSelection
  extends BTree.TreeSupport
{
///////////////////////////////////////////////////////////
// selection
///////////////////////////////////////////////////////////
  
  /**
   * Add the specified node to the list of selected nodes.
   */  
  public void select(TreeNode node)
  {
    add(node);
    updateTree();
  }

  /**
   * Deselect all selected nodes.
   */
  public void deselectAll()
  {
    clear();
    updateTree();
  }

  /**
   * Remove the specified node from the list of selected nodes.
   */  
  public void deselect(TreeNode node)
  {
    remove(node);
    updateTree();
  }  
  
  /**
   * Get the selected node.  If the tree allows multiple
   * selection and more than one node is selected, the result is one
   * of the selected nodes, but which selection is returned is undefined.
   */  
  public TreeNode getNode()
  {
    if (nodes.size() == 0) return null;
    else return nodes.get(0);
  }

  /**
   * Get the list of selected nodes.  If no nodes are selected, 
   * an empty array is returned.
   */
  public TreeNode[] getNodes()
  {
    return nodes.toArray(new TreeNode[nodes.size()]);
  }

  /**
   * Convenience for <code>getSubject(null)</code>.
   */
  public final TreeSubject getSubject()
  {
    return getSubject(null);                                                
  }
  
  /**
   * Get the current selection as a Subject by mapping selected 
   * TreeNodes to objects via <code>TreeNode.getSubject()</code>.
   */
  public TreeSubject getSubject(TreeNode activeNode)
  {                                     
    return new TreeSubject(getTree(), getNodes(), activeNode);
  }  

  /**
   * Call this method to update the tree whenever the 
   * selection is modified.  It automatically fires
   * the selectionModified event.
   */
  public void updateTree()
  {
    if (supressUpdate)
    {
      updatePending = true;
    }
    else
    {
      BTree tree = getTree();
      if (tree != null) 
      {
        tree.repaint();
        getTree().fireSelectionModified(new BWidgetEvent(BWidgetEvent.MODIFIED, getTree()));
      }
    }
  }

  void supressUpdates(boolean value)
  {
    if (value)
    {
      supressUpdate = true;
      updatePending = false;
    }
    else
    {
      supressUpdate = false; 
      if (updatePending) updateTree();
      updatePending = false;
    }
  }

  /**
   * Handle an expansion of the specified node.
   */
  public void expanded(TreeNode node)
  {
  }  
  
  /**
   * Handle the collapse of the specified node, by unselecting
   * any selected children
   */  
  public void collapsed(TreeNode node)
  {
    int n = 0;
    boolean changed = false;
    
    while (n < nodes.size())
    {
      TreeNode selNode = nodes.get(n);
      
      if (selNode.isDescendantOf(node))
      {
        remove(selNode);        
        changed = true;
      }
      else
      {
        n++;                
      }
    }
    
    if (changed) updateTree();
  }
  
///////////////////////////////////////////////////////////
// private
///////////////////////////////////////////////////////////

  /**
   * Add the specified node to the list of selected nodes.
   */  
  private void add(TreeNode node)
  {
    if (!getTree().getMultipleSelection()) clear();      
    node.setSelected(true);
    nodes.add(node);
  }

  /**
   * Deselect all selected nodes.
   */
  private void clear()
  {
    for (int i = 0; i < nodes.size(); i++)
    {
      nodes.get(i).setSelected(false);
    }
    nodes.clear();
  }

  /**
   * Remove the specified node from the list of selected nodes.
   */  
  private void remove(TreeNode node)
  {
    node.setSelected(false);
    nodes.remove(node);        
  }  
  
///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private boolean supressUpdate = false; 
  private boolean updatePending = false;
  
  private List<TreeNode> nodes = new ArrayList<>();
}

