/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.bql.table;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.naming.SlotPath;
import javax.baja.status.BStatus;
import javax.baja.ui.table.DynamicTableModel;
import javax.baja.ui.table.TableCellRenderer;

/**
 * BqlTableCellRenderer is a renderer for the table cells in a BqlTable.
 * It includes special handling for icon support and status coloring.
 * 
 * @author    John Sublett
 * @creation  07 Dec 2004
 * @version   $Revision: 1$ $Date: 12/9/04 5:05:45 PM EST$
 * @since     Baja 1.0
 */
public class BqlTableCellRenderer
  extends TableCellRenderer
{
  public BqlTableCellRenderer()
  {
  }

  /**
   * Get the text to display in the table cell.  This handles the
   * UNESCAPE flag if necessary.
   */
  public String getCellText(TableCellRenderer.Cell cell)
  {
    String text = super.getCellText(cell);
    int rootIndex = ((DynamicTableModel)getModel()).toRootColumnIndex(cell.column);
    BqlTableColumn bqlCol = ((BBqlTable)getTable()).getBqlModel().getBqlColumn(rootIndex);
    if ((bqlCol.getFlags() & BqlTableColumn.UNESCAPE) != 0)
      return SlotPath.unescape(text);
    else
      return text;
  }

  /**
   * Get the foreground color for the specified cell.
   */
  public BBrush getForeground(TableCellRenderer.Cell cell)
  {
    BqlTableModel model = ((BBqlTable)getTable()).getBqlModel();
    BqlTableColumn.Status statusCol = model.getStatusColumn();
    if (statusCol == null)
      return super.getForeground(cell);
    else
    {
      BStatus status = (BStatus)statusCol.getValueAt(cell.row);
      return ((BColor)status.getForegroundColor(BColor.black)).toBrush();
    }
  }

  /**
   * Get the background color for the specified cell.
   */
  public BBrush getBackground(TableCellRenderer.Cell cell)
  {
    BqlTableModel model = ((BBqlTable)getTable()).getBqlModel();
    BqlTableColumn.Status statusCol = model.getStatusColumn();
    if (statusCol == null)
      return super.getBackground(cell);
    else
    {
      BStatus status = (BStatus)statusCol.getValueAt(cell.row);
      return ((BColor)status.getBackgroundColor(BColor.white)).toBrush();
    }
  }
  
}