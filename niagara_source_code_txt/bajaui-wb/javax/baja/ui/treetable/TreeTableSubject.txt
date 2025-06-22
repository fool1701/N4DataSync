/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.treetable;

import javax.baja.ui.table.*;

/**
 * TreeTableSubject is a Subject with additional TreeTable specific data.
 *
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 2$ $Date: 6/11/07 12:41:33 PM EDT$
 * @since     Baja 1.0
 */
public class TreeTableSubject
  extends TableSubject
{                       

///////////////////////////////////////////////////////////
// Constructors
///////////////////////////////////////////////////////////

  /**
   * Convenience for <code>this(table, rows, -1)</code>
   */
  public TreeTableSubject(BTreeTable table, int[] rows)
  {                                         
    this(table, rows, -1);
  }
  
  /**
   * Construct with specified list of row indices and active row index.
   */
  public TreeTableSubject(BTreeTable table, int[] rows, int activeRow)
  {                                  
    super(table, rows, activeRow);                                      
    
    // map rows to node
    TreeTableModel model = table.getTreeTableModel();        
    this.nodes = new TreeTableNode[rows.length];
    for(int i=0; i<nodes.length; ++i) 
      nodes[i] = model.rowToNode(rows[i]);
    
    // map activeRow to node
    this.activeNode = model.rowToNode(activeRow);  
  }                           
  
///////////////////////////////////////////////////////////
// Access
///////////////////////////////////////////////////////////

  /**
   * Get the tree table associated with this subject.
   */
  public BTreeTable getTreeTable()
  {
    return (BTreeTable)getTable();
  }  

  /**
   * Get all the selected nodes.
   */
  public TreeTableNode[] getNodes()
  {              
    return this.nodes.clone();
  }                
  
  /**
   * Get the selected TreeTableNode at the specified index. 
   */
  public TreeTableNode getNode(int index)
  {
    return nodes[index];
  }              
  
  /**
   * Get the active TreeTableNode or null.
   */
  public TreeTableNode getActiveNode()
  {                                   
    return activeNode;
  }
  
///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  
  private TreeTableNode[] nodes;
  private TreeTableNode activeNode;
  
}

