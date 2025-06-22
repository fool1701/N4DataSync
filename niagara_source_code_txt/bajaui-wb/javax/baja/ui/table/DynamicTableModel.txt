/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table;

import java.util.*;
import javax.baja.ui.enums.*;
import javax.baja.sys.*;

/**
 * DynamicTableModel wraps another root TableModel to
 * allow columns to be toggled on and off.
 *
 * @author    Brian Frank
 * @creation  16 Oct 03
 * @version   $Revision: 14$ $Date: 7/27/10 9:54:25 AM EDT$
 * @since     Baja 1.0
 */
public class DynamicTableModel
  extends WrapperTableModel
{                                             
  /**
   * Construct with where only the specified columns
   * are visible by default.
   */
  public DynamicTableModel(TableModel root, int[] showColumns)
  {
    super(root);
    for(int i=0; i<showColumns.length; ++i)
      showCols.set(showColumns[i]);
    
    set(showable, 0, root.getColumnCount());
  }

  /**
   * Construct with every column visible by default.
   */
  public DynamicTableModel(TableModel root)
  {                       
    super(root);
    set(showCols, 0, 255);
    set(showable, 0, 255);
  }

////////////////////////////////////////////////////////////////
// Show Space
////////////////////////////////////////////////////////////////
  
  /**
   * Return the number of showing columns.
   */
  public int getColumnCount()
  {             
    if (showCount < 0) columnsModified();   
    return showCount;
  }
  
  /**
   * Return <code>root.getColumnName(toRootColumnIndex(col))</code>.
   */
  public String getColumnName(int col)
  {                                               
    return root.getColumnName(toRootColumnIndex(col));
  }

  /**
   * Return <code>root.getValueAt(row, toRootColumnIndex(col)</code>.
   */
  public Object getValueAt(int row, int col)
  {                                                 
    return root.getValueAt(row, toRootColumnIndex(col));
  }

  /**
   * Return <code>root.getSubject(row)</code>.
   */
  public Object getSubject(int row)
  {                                                 
    return root.getSubject(row);
  }
  
  /**
   * Return <code>root.getColumnAlignment(toRootColumnIndex(col))</code>.
   */
  public BHalign getColumnAlignment(int col)
  {                                                    
    return root.getColumnAlignment(toRootColumnIndex(col));
  }                        

  /**
   * Return <code>root.isColumnSortable(toRootColumnIndex(col))</code>.
   */
  public boolean isColumnSortable(int col)
  {
    return root.isColumnSortable(toRootColumnIndex(col));
  }

  /**
   * Call <code>root.sortByColumn(toRootColumnIndex(col), ascending)</code>.
   */
  public void sortByColumn(int col, boolean ascending)
  {
    root.sortByColumn(toRootColumnIndex(col), ascending);
  }                            

////////////////////////////////////////////////////////////////
// Mapping
////////////////////////////////////////////////////////////////
  
  /**
   * Get the root's number of columns.
   */                   
  public int getRootColumnCount()
  {                             
    return root.getColumnCount();
  }  

  /**
   * Get the root model's column name for the
   * specified root column index.
   */                   
  public String getRootColumnName(int rootCol)
  {                             
    return root.getColumnName(rootCol);
  }  
  
  /**
   * Map a column index from the my index space to 
   * the root's index space.
   */
  public int toRootColumnIndex(int showCol)
  {                                     
    if (showCol < 0) return showCol;
    if (fromRootMap == null) columnsModified();
    return toRootMap[showCol];
  }  

  /**
   * Map a column index from the the root's index space 
   * to my index space.
   */
  public int fromRootColumnIndex(int rootCol)
  {                                     
    if (rootCol < 0) return rootCol;
    if (fromRootMap == null) columnsModified();
    return fromRootMap[rootCol];
  }  

  /**
   * Return if the specified root column index 
   * is currently showing.
   */
  public boolean showColumn(int rootCol)
  {
    return showCols.get(rootCol);
  }                                                      

  /**
   * Set the specified root column index as showing 
   * or not showing.
   */
  public void setShowColumn(int rootCol, boolean show)
  {
    // Don't allow non-showable columns to be toggled.
    if (!showable.get(rootCol)) return;
    
    BTable table = getTable();
    if (showCols.get(rootCol) != show)
    { 
      // save away column which was sorted                            
      int sortCol = -1;      
      boolean sortAscending = true;
      if (table != null)
      {
        toRootColumnIndex(table.getSortColumn());  
        sortAscending = table.isSortAscending();
      }
      
      // build columns
      set(showCols, rootCol, show);
      columnsModified();
      if (table != null) getTable().sizeColumnsToFit();             
      
      // if there was originally a sort, then resort
      if (table != null)
      {
        table.sortByColumn(-1, sortAscending); 
        if (sortCol >= 0 && showColumn(sortCol))
        {
          int relSortCol = fromRootColumnIndex(sortCol);
          if (relSortCol >= 0 && root.isColumnSortable(relSortCol))
            table.sortByColumn(relSortCol, sortAscending);
        }
      }
    }
  }                                                      
  
  /**
   * Called when the root model's columns are updated
   * in order to recalculate and cache column information.
   * 
   * @since Niagara 3.6
   */
  public void columnsModified()
  {          
    int count = getRootColumnCount();
    int showCount = 0;
    int[] toRootMap = new int[count];             
    int[] fromRootMap = new int[count];             

    for(int i=0; i<count; ++i)
    {                       
      if (showColumn(i))
      {               
        toRootMap[showCount++] = i;
        fromRootMap[i] = showCount-1;        
      }                            
      else
      {
        fromRootMap[i] = -1;        
      }
    }  

    this.showCount   = showCount;
    this.toRootMap   = toRootMap;
    this.fromRootMap = fromRootMap;
  }

  /**
   * Set whether the specified column can be hidden
   * or shown.  If false, then the visibility of the
   * specified column cannot be modified.
   */
  public void setColumnShowable(int rootCol, boolean show)
  {
    set(showable, rootCol, show);
  }

  /**
   * Test whether hidden columns can be shown.  If false,
   * then the visibility of the specified column cannot
   * be modified.
   */
  public boolean isColumnShowable(int rootCol)
  {
    return showable.get(rootCol);
  }         

////////////////////////////////////////////////////////////////
// Export
////////////////////////////////////////////////////////////////

  /**
   * Export a cell as a BObject value.
   */
  public BObject export(int row, int col)
  {
    return root.export(row, toRootColumnIndex(col)); 
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////
  
  /**
   * J2ME implementation for BitSet.set(int, int)
   */
  static void set(BitSet bits, int fromIndex, int toIndex)
  {                               
    for(int i=fromIndex; i<toIndex; ++i)
      bits.set(i);
  }

  /**
   * J2ME implementation for BitSet.set(int, boolean)
   */
  static void set(BitSet bits, int index, boolean bit)
  {
    if (bit) bits.set(index);
    else bits.clear(index);
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  BitSet showCols = new BitSet(); 
  BitSet showable = new BitSet();
  int showCount = -1;
  int[] toRootMap;
  int[] fromRootMap;
}
