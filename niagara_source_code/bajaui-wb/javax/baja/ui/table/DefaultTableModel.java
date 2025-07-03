/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table;

import java.util.List;

import javax.baja.gx.BImage;
import javax.baja.nre.util.SortUtil;

/**
 * DefaultTableModel provides a simple implementation  
 * of TableModel with methods to manipulate the model.
 *
 * @author    Brian Frank
 * @creation  3 Aug 00
 * @version   $Revision: 16$ $Date: 5/4/05 8:10:19 PM EDT$
 * @since     Baja 1.0
 */
public class DefaultTableModel
  extends TableModel
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct an empty table model with zero 
   * columns and zero rows.
   */
  public DefaultTableModel()
  {
    this.columnNames = new String[0];
    this.rows = new Row[16];
  }

  /**
   * Construct an list with the specified column names.
   */
  public DefaultTableModel(String[] columnNames)
  {
    this.columnNames = columnNames;
    this.rows = new Row[16];
  }

////////////////////////////////////////////////////////////////
// TableModel
////////////////////////////////////////////////////////////////  

  /**
   * Get the number of rows in the model.
   */
  public synchronized int getRowCount() 
  { 
    return count; 
  }
  
  /**
   * Get the number of the columns in the model.
   */
  public synchronized int getColumnCount() 
  { 
    return columnNames.length; 
  }
  
  /**
   * Get the column name for the specified column index.
   */
  public synchronized String getColumnName(int index) 
  { 
    return columnNames[index]; 
  }

  /**
   * Get the number of the columns in the model.
   */
  public synchronized BImage getRowIcon(int row) 
  { 
    return rows[row].icon; 
  }
    
  /**
   * Get the grid value for the specified row and column.
   */
  public synchronized Object getValueAt(int row, int col) 
  { 
    return rows[row].columns[col];
  }

  /**
   * Return <code>getRowValues(row)</code>.
   */
  public synchronized Object getSubject(int row) 
  { 
    return getRowValues(row);
  }

////////////////////////////////////////////////////////////////
// Sorting
////////////////////////////////////////////////////////////////
  
  /**
   * All columns are sortable in the default table model.
   */
  public boolean isColumnSortable(int col)
  {
    return true;
  }
  
  /**
   * Sort the specified column.  If the column contains
   * Objects which implement the Comparable interface then
   * that is how the sort is performed.  Otherwise the sort
   * is performed on the result of the toString() method
   * of the Object value.
   */
  public synchronized void sortByColumn(int col, boolean ascending)
  {
    Object[] keys = getColumnValues(col);
    Row[] temp = new Row[keys.length];
    System.arraycopy(rows, 0, temp, 0, keys.length);
    SortUtil.sort(keys, temp, ascending);     
    rows = temp;
  }
  
////////////////////////////////////////////////////////////////
// Manipulation
////////////////////////////////////////////////////////////////  

  /**
   * Set the value at the specified row and column.
   */
  public synchronized void set(int row, int col, Object value)
  {
    rows[row].columns[col] = value;
    updateTable();
  }

  /**
   * Add a new row to the table.  The specified array
   * should contain the values for each column.
   */
  public void addRow(Object[] rowValues)
  {
    addRow(new Row(null, rowValues));
  }
  
  /**
   * Add a new row to the table.  The specified array
   * should contain the values for each column.
   */
  public void addRow(BImage icon, Object[] rowValues)
  {
    addRow(new Row(icon, rowValues));
  }

  /**
   * Add a new row to the table.  The specified List
   * should contain the values for each column.
   */
  public void addRow(List<Object> rowData)
  {
    addRow(new Row(null, rowData.toArray()));
  }

  /**
   * Add a new row to the table.  The specified List
   * should contain the values for each column.
   */
  public void addRow(BImage icon, List<Object> rowData)
  {
    addRow(new Row(icon, rowData.toArray()));
  }
  
  /**
   * Implementation of addRow().
   */
  private synchronized void addRow(Row row)
  {
    if (row.columns.length != columnNames.length)
      throw new IllegalArgumentException("invalid number of columns");
      
    if (count >= rows.length)
    {
      Row[] temp = new Row[count*2+1]; // +1 in case count==0
      System.arraycopy(rows, 0, temp, 0, count);
      rows = temp;
    }
    
    rows[count++] = row;
    updateTable();
  }
  
  /**
   * Remove the specified row index from the model.
   */
  public synchronized void removeRow(int row)
  {
    System.arraycopy(rows, row+1, rows, row, count-row-1);
    rows[count-1] = null;
    count--;
    updateTable();
  }
  
  /**
   * Remove all rows.
   */ 
  public synchronized void removeAllRows()
  {
    count = 0;
    updateTable();
  }

////////////////////////////////////////////////////////////////
// Row
////////////////////////////////////////////////////////////////  

  static class Row
  {
    Row(BImage i, Object[] c) { icon = i; columns = c; }
    BImage icon;
    Object[] columns;
  }
    
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private String[] columnNames;
  private int count;
  private Row[] rows;
  
}