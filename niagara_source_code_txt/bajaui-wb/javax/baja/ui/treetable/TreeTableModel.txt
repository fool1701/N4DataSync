/*                          
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.treetable;

import java.util.*;
import javax.baja.gx.*;
import javax.baja.ui.table.*;

/**
 * TreeTableModel is the TableModel used by BTreeTable
 *
 * @author    Brian Frank 
 * @creation  7 Jan 04
 * @version   $Revision: 10$ $Date: 5/4/05 8:11:16 PM EDT$
 * @since     Baja 1.0
 */
public abstract class TreeTableModel
  extends TableModel
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the table as a BTreeTable.
   */
  public final BTreeTable getTreeTable()
  {
    return (BTreeTable)getTable();
  }
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the number of root nodes in the table.
   */
  public abstract int getRootCount();

  /**
   * Get the specified root node.
   */
  public abstract TreeTableNode getRoot(int index);

////////////////////////////////////////////////////////////////
// Mapping
////////////////////////////////////////////////////////////////  
  
  /**
   * Map a row index into a TreeTableNode instance.
   */
  public TreeTableNode rowToNode(int row)
  {                             
    if (row < 0) return null;
    return rows[row];
  }
  
  /**
   * Map a TreeTableNode into a row index or -1 if 
   * not currently expanded as a row in the table.
   */
  public int nodeToRow(TreeTableNode node)
  {            
    if (!isNodeVisibleRow(node)) return -1;
    return node.index;
  }     
  
  /**
   * Return if the TreeTableNode is currently a 
   * visible row in the table.
   */
  public boolean isNodeVisibleRow(TreeTableNode node)
  {     
    TreeTableNode p = node.getParent();
    while (p != null) 
    {
      if (!p.isExpanded()) return false;
      p = p.getParent();
    }
    return true;
  }                       
  
  /**
   * Return if the specified depth is expandable.  If true then 
   * the renderer will always leave space for an expansion button 
   * for nodes at the specified depth.  Default returns true
   * assuming that all depths of the table are expandable.
   */
  public boolean isDepthExpandable(int depth)
  {                                                        
    return true;
  }                   

  /**
   * Update the node to row mapping either becauses 
   * rows have been added or nodes have been collapsed
   * or expanded.
   */
  public void updateTreeTable(boolean resizeColumns)
  {
    ArrayList<TreeTableNode> temp = new ArrayList<>(rows.length + 10);
    
    for(int i=0; i<getRootCount(); ++i)
      updateTreeTable(temp, getRoot(i));
    
    rows = temp.toArray(new TreeTableNode[temp.size()]);
    updateTable(resizeColumns);
  }   
  
  /**
   * Recursive implemention
   */
  void updateTreeTable(ArrayList<TreeTableNode> temp, TreeTableNode node)
  {
    temp.add(node);
    node.index = temp.size() - 1;
    if (node.isExpanded())
    {
      for(int i=0; i<node.getChildCount(); ++i)
        updateTreeTable(temp, node.getChild(i));
    }
  }
  
  /**
   * access to whether or not the TreeTable Model has been initalized
   */ 
  public boolean needsInitialization()
  {
    return needInit;
  }

////////////////////////////////////////////////////////////////
// TableModel
////////////////////////////////////////////////////////////////    
  
  /**
   * Computed from roots and current expansion state.
   */
  public int getRowCount()
  {                  
    if (needInit) { needInit = false; updateTreeTable(true); }
    return rows.length;
  }

  /**
   * Call <code>rowToNode(row).getValueAt(col)</code>
   */
  public Object getValueAt(int row, int col)
  {                    
    TreeTableNode node = rowToNode(row);
    if (node.isGroup() && col > 0) return "";
    return node.getValueAt(col);
  }                 
  
  /**
   * Call <code>rowToNode(row).getSubject()</code>
   */
  public Object getSubject(int row)
  {  
    return rowToNode(row).getSubject();
  }  
  
  /**
   * Call <code>rowToNode(row).getIcon()</code>
   */
  public BImage getRowIcon(int row)
  {
    return rowToNode(row).getIcon();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  boolean needInit = true;
  TreeTableNode[] rows = new TreeTableNode[0];
}

