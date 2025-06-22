/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table;

import javax.baja.sys.*;
import javax.baja.collection.*;
import javax.baja.naming.*;
import javax.baja.gx.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;
import javax.baja.data.*;
import com.tridium.data.*;

/**
 * TableModel provides the class used to store the data
 * for the grid visualized by a BTable.
 *                                     
 * <pre>
 *  class Model extends TableModel
 *  {
 *    public int getRowCount() { return 100; }
 *    public int getColumnCount() { return 3; }
 *    public String getColumnName(int col) { return "Col " + col; }
 *    public Object getValueAt(int row, int col) { return "Value " + row + "," + col; }
 *  }
 * </pre>
 *
 * @author    John Sublett
 * @creation  21 Nov 00
 * @version   $Revision: 32$ $Date: 4/13/11 4:56:31 PM EDT$
 * @since     Baja 1.0
 */
public abstract class TableModel
  extends BTable.TableSupport
{

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the number of rows in the model.
   */
  public abstract int getRowCount();

  /**
   * Get the number of the columns in the model.
   */
  public abstract int getColumnCount();
  
  /**
   * Get the column name for the specified column index.
   */
  public abstract String getColumnName(int col);

  /**
   * Get the grid value for the specified row and column.
   */
  public abstract Object getValueAt(int row, int col);
  
  /**
   * Get the subject of the specified row.  This is 
   * used to map selection into a Subject instance.
   * The default maps to <code>getRowValues()</code>,
   * but should usually be overridden.
   */
  public Object getSubject(int row)
  {      
    return getRowValues(row);
  }
  
  /**
   * Get the horizontal alignment to use for the 
   * specified column index.
   */
  public BHalign getColumnAlignment(int col)
  {
    return BHalign.left;
  }

  /**
   * Return the BImage to use for the specified row icon,
   * or null if icons are not supported.
   */
  public BImage getRowIcon(int row)
  {
    return null;
  }                     

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the column name for the specified column index.
   */
  public String[] getColumnNames()
  {
    String[] names = new String[getColumnCount()];
    for(int c=0; c<names.length; ++c) 
      names[c] = getColumnName(c);
    return names;
  }
  
  /**
   * Get the specified row as an array of Objects.
   */
  public Object[] getRowValues(int row)
  {
    Object[] x = new Object[getColumnCount()];
    for(int c=0; c<x.length; ++c)
      x[c] = getValueAt(row, c);
    return x;
  }

  /**
   * Get the specified column as an array of Objects.
   */
  public Object[] getColumnValues(int col)
  {
    Object[] x = new Object[getRowCount()];
    for(int r=0; r<x.length; ++r)
      x[r] = getValueAt(r, col);
    return x;
  }                   
  
  /**
   * This method should be called when the table model
   * has been modified, and the table requires an update.
   * It automatically fires a tableModified event.
   */
  public void updateTable()
  {
    updateTable(false);
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
    BTable table = getTable();
    if (table != null) 
    {
      if (resizeColumns)
        getTable().sizeColumnsToFit();
      else
        getTable().relayout();
        
      getTable().fireTableModified(new BWidgetEvent(BWidgetEvent.MODIFIED, getTable()));
    }
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
    return false;
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
    throw new UnsupportedOperationException();
  }                  

////////////////////////////////////////////////////////////////
// Export
////////////////////////////////////////////////////////////////
  
  /**
   * Export this table model into a BITable.
   */
  public BITable<? extends BIObject> export()
  {                    
    int colCount = getColumnCount();
    int rowCount = getRowCount();
                                       
    // make data table
    BDataTable t = new BDataTable();
    
    // add columns
    for(int c=0; c<colCount; ++c)
    {                                  
      String displayName = getColumnName(c);
      String name = SlotPath.escape(displayName);
      Type type = BIDataValue.TYPE;
      t.addColumn(name, displayName, type, 0, BFacets.NULL);
    } 
    
    // add rows
    t.startRows();
    for(int r=0; r<rowCount; ++r)
    {            
      t.startRow();      
      for(int c=0; c<colCount; ++c)             
        t.set(export(r, c).toDataValue(), BFacets.NULL);
      t.endRow();      
    }
    t.endRows();
    
    return t;
  }  
  
  /**
   * Export a cell as a BObject value.
   */
  public BObject export(int row, int col)
  {                      
    Object cell = getValueAt(row, col);
    
    if (cell instanceof BObject)
      return (BObject)cell;
    
    return BString.make(String.valueOf(cell));
  }
  
}
