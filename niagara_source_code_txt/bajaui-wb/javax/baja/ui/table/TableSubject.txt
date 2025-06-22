/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.table;

import javax.baja.ui.*;

/**
 * TableSubject is a Subject with additional Table specific data.
 *
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 1$ $Date: 5/4/05 7:57:45 PM EDT$
 * @since     Baja 1.0
 */
public class TableSubject
  extends Subject
{                       

///////////////////////////////////////////////////////////
// Constructors
///////////////////////////////////////////////////////////

  /**
   * Convenience for <code>this(table, rows, -1)</code>
   */
  public TableSubject(BTable table, int[] rows)
  {                                         
    this(table, rows, -1);
  }
  
  /**
   * Construct with specified list of row indices and active row index.
   */
  public TableSubject(BTable table, int[] rows, int activeRow)
  {                                  
    super(map(table, rows), map(table, activeRow));
    this.table = table;
    this.rows = rows;     
    this.activeRow = activeRow;
  }                           
  
  static Object[] map(BTable table, int[] rows)
  {              
    Object[] list = new Object[rows.length];
    for(int i=0; i<list.length; ++i) list[i] = map(table, rows[i]);
    return list;
  }

  static Object map(BTable table, int row)
  {
    if (row < 0) return null;
    return table.getModel().getSubject(row);
  }
  
///////////////////////////////////////////////////////////
// Access
///////////////////////////////////////////////////////////

  /**
   * Get the table associated with this subject.
   */
  public BTable getTable()
  {
    return table;
  }  

  /**
   * Get all the selected row indices.
   */
  public int[] getRows()
  {
    return rows.clone();
  }                
  
  /**
   * Get the selected row index at the specified index. 
   */
  public int getRow(int index)
  {
    return rows[index];
  }              
  
  /**
   * Get the active row index or -1
   */                             
  public int getActiveRow()
  {
    return activeRow;
  }
  
///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  
  private BTable table;
  private int[] rows;
  private int activeRow;
  
}

