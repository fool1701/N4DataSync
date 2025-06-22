/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.tree;

import javax.baja.ui.*;

/**
 * TreeSubject is a Subject with additional Tree specific data.
 *
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 1$ $Date: 5/4/05 7:57:45 PM EDT$
 * @since     Baja 1.0
 */
public class TreeSubject
  extends Subject
{                       

///////////////////////////////////////////////////////////
// Constructors
///////////////////////////////////////////////////////////

  /**
   * Convenience for <code>this(tree, nodes, null)</code>
   */
  public TreeSubject(BTree tree, TreeNode[] nodes)
  {                                         
    this(tree, nodes, null);               
  }
  
  /**
   * Construct with specified list of tree nodes and active tree node.
   */
  public TreeSubject(BTree tree, TreeNode[] nodes, TreeNode activeNode)
  {                                  
    super(map(nodes), map(activeNode));
    this.tree = tree;
    this.nodes = nodes;     
    this.activeNode = activeNode;
  }                           
  
  static Object[] map(TreeNode[] nodes)
  {              
    Object[] list = new Object[nodes.length];
    for(int i=0; i<list.length; ++i) list[i] = map(nodes[i]);
    return list;
  }

  static Object map(TreeNode node)
  {
    if (node == null) return null;
    return node.getSubject();
  }
  
///////////////////////////////////////////////////////////
// Access
///////////////////////////////////////////////////////////
  
  /**
   * Get the tree associated with this subject.
   */
  public BTree getTree()
  {
    return tree;
  }  

  /**
   * Get all the selected nodes.
   */
  public TreeNode[] getNodes()
  {
    return nodes.clone();
  }                
  
  /**
   * Get the selected TreeNode at the specified index. 
   */
  public TreeNode getNode(int index)
  {
    return nodes[index];
  }              
  
  /**
   * Get the active TreeNode or null.
   */
  public TreeNode getActiveNode()
  {
    return activeNode;
  }
  
///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  
  private BTree tree;
  private TreeNode[] nodes;
  private TreeNode activeNode;
  
}

