/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.component.table;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.status.BIStatus;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.ui.table.TableCellRenderer;

/**
 * ComponentTableCellRenderer is the specialization of 
 * TableCellRenderer for the BComponentTable class.
 *
 * @author    Brian Frank
 * @creation  21 Mar 02
 * @version   $Revision: 9$ $Date: 3/28/05 1:40:57 PM EST$
 * @since     Baja 1.0
 */
public class ComponentTableCellRenderer
  extends TableCellRenderer
{  

  public BBrush getForeground(Cell cell)
  {
    BBrush fg = super.getForeground(cell);
    try
    {
      BComponent component = getComponentTable().getComponentModel().getComponentAt(cell.row);
      if (component.isPendingMove()) return cutFg;
      if (component instanceof BIStatus)
      {
        BColor c = (BColor)((BIStatus)component).getStatus().getForegroundColor(null);
        if (c != null) return c.toBrush();
      }
    }
    catch(IndexOutOfBoundsException e)
    {
      // this happens since we aren't synchronized on model
    }
    return fg;
  }
    
  public BBrush getBackground(Cell cell)
  {
    BBrush bg = super.getBackground(cell);
    try
    {
      BComponent component = getComponentTable().getComponentModel().getComponentAt(cell.row);
      if (component instanceof BIStatus)
      {
        BColor c = (BColor)((BIStatus)component).getStatus().getBackgroundColor(null);
        if (c != null) return c.toBrush();
      }
    }
    catch(IndexOutOfBoundsException e)
    {
      // this happens since we aren't synchronized on model
    }
    return bg;
  }                    

  public String getCellText(Cell cell)
  {                  
    if (cell.value instanceof BObject)
      return ((BObject)cell.value).toString( ((BComponentTable)getTable()).context );
    else
      return String.valueOf(cell.value);
  }
  
  protected BComponentTable getComponentTable()
  {                         
    return (BComponentTable)getTable();
  }

  static final BBrush cutFg = BColor.make(100,100,100).toBrush();      
  
}