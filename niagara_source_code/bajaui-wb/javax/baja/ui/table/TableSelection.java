/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table;

import java.util.*;
import javax.baja.ui.event.*;

/**
 * TableSelection is responsible for storing the currently
 * selected cells.  The default implementation supports
 * single or muliple row selection.  
 *
 * @author    Brian Frank
 * @creation  4 Aug 01
 * @version   $Revision: 13$ $Date: 6/11/07 12:41:33 PM EDT$
 * @since     Baja 1.0
 */
public class TableSelection
  extends BTable.TableSupport
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Is the specified cell selected?  This method routes 
   * to <code>isSelected(int row)</code> by default.  This
   * is the override point to implement more sophisticated
   * selection models on a per cell basis.
   */
  public boolean isSelected(int row, int col)
  {
    return isSelected(row);
  }

  /**
   * Is the specified row selected?
   */
  public boolean isSelected(int row)
  {
    return bits.get(row);
  }

  /**
   * Select all the items.
   */                     
  public void selectAll()
  {                                     
    int count = getModel().getRowCount();
    if (count > 0) select(0, count-1);
  }                         
  
  /**
   * Convenience for <code>select(row, false)</code>.
   */
  public void select(int row)
  {                                    
    select(row, false);
  }

  /**
   * Select the specified row.  If the clear flag is set
   * to true then deselect all other rows first.
   */
  public void select(int row, boolean clear)
  {
    if (clear || row < 0) bits = new BitSet(bits.size());
    
    if (row >= 0)
    {
      bits.set(row);
      anchor = lead = row;
    } 
    
    updateTable();
  }

  /**
   * Convenience for <code>select(anchor, lead, false)</code>.
   */
  public void select(int anchor, int lead)
  {
    select(anchor, lead, false);
  }

  /**
   * Add the rows between start and end inclusively
   * to the current selection.  The first row
   * becomes the new anchor, and the second new
   * lead.  If the clear flag is set to true then deselect 
   * all other rows first.
   */
  public void select(int anchor, int lead, boolean clear)
  {                     
    if (clear) bits = new BitSet(bits.size());
    
    this.anchor = anchor;
    this.lead = lead;
    
    int start = anchor;
    int end = lead;
    if (end < start) 
      { int temp = start; start = end; end = temp; }
      
    for(int r=start; r<=end; ++r) bits.set(r);
    updateTable();
  }
  
  /**
   * Select the specified rows.  The first one is 
   * used as the selection anchor.
   */
  public void select(int[] rows)
  {
    bits = new BitSet(bits.size());
    if (rows.length == 0) return;
    anchor = rows[0];
    lead = rows[rows.length-1];
    for(int i=0; i<rows.length; ++i)
      bits.set(rows[i]);
    updateTable();
  }
  
  /**
   * Deselect the specified row, and set both the
   * anchor and lead to the new row.
   */
  public void deselect(int row)
  {
    bits.clear(row);
    anchor = lead = row;
    updateTable();
  }

  /**
   * Deselect all the rows.
   */
  public void deselectAll()
  {
    bits = new BitSet(bits.size());
    updateTable();
  }

  /**
   * Return true if row count is zero.
   */
  public boolean isEmpty()
  {
    return getRowCount() == 0; // NOTE: performance could be better
  }

  /**
   * Get the number of rows that are currently selected.
   */
  public int getRowCount()
  {
    int rowCount = getTable().getModel().getRowCount();
    int n = 0;
    for(int r=0; r<rowCount; ++r)
      if (bits.get(r)) n++;
    return n;
  }

  /**
   * Get the index of the first selected row, or 
   * return -1 if no rows are currently selected.
   */
  public int getRow()
  {
    for(int r=0; r<bits.size(); ++r)
      if (bits.get(r)) return r;
    return -1;
  }

  /**
   * Get indices of the selected rows, or return an
   * empty array if no rows currently selected.
   */
  public int[] getRows()
  {
    int rowCount = getTable().getModel().getRowCount();
    int[] temp = new int[rowCount];
    int n = 0;
    
    for(int r=0; r<rowCount; ++r)
      if (bits.get(r)) temp[n++] = r;
    
    int[] result = new int[n];
    System.arraycopy(temp, 0, result, 0, n);
    return result;
  }
  
  /**
   * Get the selection anchor index, or -1 if no
   * anchor is active.  The anchor selection is 
   * the usually the first row index selected or 
   * unselected.
   */
  public int getAnchor()
  {
    return anchor;
  }
  
  /**
   * Get the selection lead row index, or -1 if no 
   * lead is active.  The lead selection is 
   * the last row index selected or unselected.
   */
  public int getLead()
  {
    return lead;
  }         

  /**
   * Convenience for <code>getSubject(lead)</code>.
   */
  public final TableSubject getSubject()
  {
    return getSubject(lead);                                                
  }
  
  /**
   * Get the current selection as a Subject by mapping selected 
   * row indices to objects via <code>TableModel.getSubject()</code>.
   */
  public TableSubject getSubject(int activeRow)
  {                   
    return new TableSubject(getTable(), getRows(), activeRow);                  
  }  
    
  /**
   * Call this method to update the table whenever the 
   * selection is modified.  It automatically fires
   * the selectionModified event.
   */
  public void updateTable()
  {
    BTable table = getTable();
    if (table != null) 
    {
      table.repaint();
      getTable().fireSelectionModified(new BWidgetEvent(BWidgetEvent.MODIFIED, getTable()));
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BitSet bits = new BitSet();
  private int lead = -1;
  private int anchor = -1;

}
