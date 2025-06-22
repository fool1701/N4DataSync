/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.treetable;

import javax.baja.collection.BITable;
import javax.baja.gx.BImage;
import javax.baja.sys.BIObject;
import javax.baja.sys.BObject;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.TableModel;

/**
 * A WrapperTableModel is a table model that wraps another table model.
 * It is commonly used to hide, reorder, or rename table columns.
 *
 * @author    John Sublett
 * @creation  30 Oct 2003
 * @version   $Revision: 3$ $Date: 6/28/11 8:35:58 AM EDT$
 * @since     Baja 1.0
 */
public abstract class WrapperTreeTableModel
  extends TreeTableModel
{
  protected WrapperTreeTableModel(TreeTableModel root)
  {
    this.root = root;
  }

  /**
   * Get the model that is wrapped by this model.
   */
  public TreeTableModel getRootModel()
  {
    return root;
  }

  /**
   * Set the table for this model.
   */
  public void setTable(BTable table)
  {
    super.setTable(table);
    root.setTable(table);
  }
  
  /**
   * Get the number of rows in the model.
   */
  public int getRowCount()
  {
    return root.getRowCount();
  }

  /**
   * Get the number of the columns in the model.
   */
  public int getColumnCount()
  {
    return root.getColumnCount();
  }
  
  /**
   * Get the column name for the specified column index.
   */
  public String getColumnName(int col)
  {
    return root.getColumnName(col);
  }

  /**
   * Get the grid value for the specified row and column.
   */
  public Object getValueAt(int row, int col)
  {
    return root.getValueAt(row, col);
  }
  
  /**
   * Get the horizontal alignment to use for the 
   * specified column index.
   */
  public BHalign getColumnAlignment(int col)
  {
    return root.getColumnAlignment(col);
  }

  /**
   * Get the subject value for the specified row.
   */
  public Object getSubject(int row)
  {
    return root.getSubject(row);
  }

  /**
   * Return the BImage to use for the specified row icon,
   * or null if icons are not supported.
   */
  public BImage getRowIcon(int row)
  {
    return root.getRowIcon(row);
  }                     

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the column name for the specified column index.
   */
  public String[] getColumnNames()
  {
    return root.getColumnNames();
  }
  
  /**
   * Get the specified row as an array of Objects.
   */
  public Object[] getRowValues(int row)
  {
    return root.getRowValues(row);
  }

  /**
   * Get the specified column as an array of Objects.
   */
  public Object[] getColumnValues(int col)
  {
    return root.getColumnValues(col);
  }                   

  /**
   * Get the number of root nodes in the table.
   */
  public int getRootCount()
  {
    return root.getRootCount();
  }

  /**
   * Get the specified root node.
   */
  public TreeTableNode getRoot(int index)
  {
    return root.getRoot(index);
  }
  
  /**
   * This method should be called when the table model
   * has been modified, and the table requires an update.
   * It automatically fires a tableModified event.
   */
  public void updateTable()
  {
    root.updateTable(false);
  }
  
  /**
   * This method should be called when the table model
   * has been modified, and the table requires an update.
   * It automatically fires a tableModified event.
   *
   * @param resizeColumns Indicates whether the update requires
   *   the columns to be resized.
   */
  public void updateTable(boolean resizeColumns)
  {
    root.updateTable(resizeColumns);
  }

////////////////////////////////////////////////////////////////
// Export
////////////////////////////////////////////////////////////////

   /**
   * Export this table model into a BITable.
   */
  public BITable<? extends BIObject> export()
  {
    // issue 7676 - needed this method to return the root's export(),
    // otherwise facet info contained in the root table (timezone info) gets lost
    return root.export();
  }
  
  /**
   * Export a cell as a BObject value.
   */
  public BObject export(int row, int col)
  {
    return root.export(row, col);
  }

////////////////////////////////////////////////////////////////
// Sorting
////////////////////////////////////////////////////////////////  

  /**
   * Return true if the specified column is sortable.
   * The default is to return false.
   */
  public boolean isColumnSortable(int col)
  {
    return root.isColumnSortable(col);
  }

  /**
   * When this method is called, the model should sort 
   * its rows based on the specified column index. The 
   * ascending parameter is true if the column should be 
   * sorted ascending (least to greatest), or false if 
   * a descending sort should be used.  The default 
   * implementation is to throw UnsupportedOperationException.
   */
  public void sortByColumn(int col, boolean ascending)
  {
    root.sortByColumn(col, ascending);
  }

  /**
   * Map a row index into a TreeTableNode instance.
   */
  public TreeTableNode rowToNode(int row)
  {
    return root.rowToNode(row);
  }

  /**
   * Map a TreeTableNode into a row index or -1 if 
   * not currently expanded as a row in the table.
   */
  public int nodeToRow(TreeTableNode node)
  {
    return root.nodeToRow(node);
  }

  /**
   * Return if the TreeTableNode is currently a 
   * visible row in the table.
   */
  public boolean isNodeVisibleRow(TreeTableNode node)
  {
    return root.isNodeVisibleRow(node);
  }

  /**
   * Return if the specified depth is expandable.  If true then 
   * the renderer will always leave space for an expansion button 
   * for nodes at the specified depth.  Default returns true
   * assuming that all depths of the table are expandable.
   */
  public boolean isDepthExpandable(int depth)
  {
    return root.isDepthExpandable(depth);
  }

  /**
   * Update the node to row mapping either becauses 
   * rows have been added or nodes have been collapsed
   * or expanded.
   */
  public void updateTreeTable(boolean resizeColumns)
  {
    root.updateTreeTable(resizeColumns);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  TreeTableModel root;
}