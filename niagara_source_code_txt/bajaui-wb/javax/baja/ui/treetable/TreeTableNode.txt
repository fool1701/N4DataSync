/*                          
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.treetable;

import java.util.*;
import javax.baja.gx.*;
import javax.baja.ui.*;
import javax.baja.ui.table.*;

/**
 * TreeTableNode models a single node of a TreeTableModel.
 *
 * @author    Brian Frank 
 * @creation  7 Jan 04
 * @version   $Revision: 10$ $Date: 5/4/05 8:11:16 PM EDT$
 * @since     Baja 1.0
 */
public abstract class TreeTableNode
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a root node within the specified model.
   */
  public TreeTableNode(TreeTableModel model)
  {
    if (model == null) throw new NullPointerException("null model");
    this.model = model;
    this.parent = null;    
    this.depth = 0;
  }

  /**
   * Construct a child node under the parent node.
   */
  public TreeTableNode(TreeTableNode parent)
  {
    if (parent.model == null) throw new NullPointerException("null model");
    this.model  = parent.model;
    this.parent = parent;
    this.depth  = parent.depth + 1;
  }
 
////////////////////////////////////////////////////////////////
// Access
//////////////////////////////////////////////////////////////// 

  /**
   * Get the parent tree table.
   */
  public BTreeTable getTable()
  {
    return (BTreeTable)model.getTable();
  }
  
  /**
   * Get the parent model.
   */
  public TreeTableModel getModel()
  {
    return model;
  }

  /**
   * Convenience for <code>getTable().getShell()</code>.
   */
  public BWidgetShell getShell()
  {
    BTable table = model.getTable();
    if (table != null) return table.getShell();
    return null;
  }                               
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  

  /**
   * A group node is used only to group other nodes underneath
   * it.  Groups can only return data for column 0.   Groups are
   * renderered differently from other nodes.
   */
  public boolean isGroup()
  {
    return false;
  } 
  
  /**
   * Get the node's value for the specified column.
   */
  public abstract Object getValueAt(int col);
  
  /**
   * Get the subject to use when mapping current selection
   * to a Subject instance.  Default returns value at column 0,
   * but this method should usually be overridden.
   */
  public Object getSubject()
  {
    return getValueAt(0);
  }  
  
  /**
   * Return the icon for the node as a 16x16 image.
   */
  public BImage getIcon()
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Children
////////////////////////////////////////////////////////////////  

  /**
   * Get the parent of this node.  If this is a root node return null.
   */
  public TreeTableNode getParent()
  {
    return parent;
  }

  /**
   * Get the depth from the root of the node.
   */
  public int getDepth()
  {
    return depth;
  }        
        
  /**
   * Does this node have any children node?  Default
   * returns <code>getChildCount() != 0</code>.
   */
  public boolean hasChildren()
  {
    return getChildCount() != 0;
  }
  
  /**
   * Get the number of children nodes.  Default returns 0.
   */
  public int getChildCount()
  {
    return 0;
  }
    
  /**
   * Get the child node at the specified index.
   */
  public TreeTableNode getChild(int index)
  {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Return the index of the specified child node or -1.
   */
  public int getChildIndex(TreeTableNode child)
  {
    int count = getChildCount();
    for(int i=0; i<count; ++i)
      if (getChild(i) == child) return i;
    return -1;
  }
  
  /**
   * Is this node a descendant of the specified node?
   */  
  public boolean isDescendantOf(TreeTableNode ancestor)
  {
    TreeTableNode parent = getParent();
    while((parent != null) && (parent != ancestor))
      parent = parent.getParent();    
    return parent == ancestor;
  }

  /**
   * Get the path of this node starting from the root.
   */  
  public TreeTableNode[] getPathFromRoot()
  {
    // to list
    ArrayList<TreeTableNode> temp = new ArrayList<>(4);
    temp.add(this);
    TreeTableNode parent = getParent();
    while(parent != null)
    {
      temp.add(parent);
      parent = parent.getParent();
    }
    
    // reverse the list
    int len = temp.size();
    TreeTableNode[] path = new TreeTableNode[len];
    for(int i=0; i<len; i++)
      path[i] = temp.get(len-i-1);
    return path;
  }

///////////////////////////////////////////////////////////
// Expansion
///////////////////////////////////////////////////////////
  
  /**
   * Set the expanded state of this node.
   */
  public void setExpanded(boolean expanded)
  {                            
    if (this.expanded != expanded)
    {
      this.expanded = expanded;
      if (this.expanded)
        expanded();
      else
        collapsed();   
      
      boolean resize = expanded;
      getModel().updateTreeTable(resize);
    }
  }

  /**
   * Return the expanded state of this node.
   */  
  public final boolean isExpanded()
  {
    return expanded;
  }
  
  /**
   * Callback when the node is expanded.
   */
  public void expanded() 
  {        
  }
  
  /**
   * Callback when the node is collapsed.
   */
  public void collapsed() 
  {
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  TreeTableModel model;
  TreeTableNode parent; 
  boolean expanded;
  int index;
  int depth;
}

