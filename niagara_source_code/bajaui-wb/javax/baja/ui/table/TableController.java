/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.baja.collection.BITable;
import javax.baja.gx.RectGeom;
import javax.baja.sys.BIObject;
import javax.baja.sys.Clock;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BMenu;
import javax.baja.ui.BScrollBar;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.MouseCursor;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BMouseWheelEvent;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.util.UiLexicon;

/**
 * TableController is used to respond to user input on BTable.
 *
 * @author    Brian Frank
 * @creation  4 Aug 01
 * @version   $Revision: 44$ $Date: 6/28/11 9:45:51 AM EDT$
 * @since     Baja 1.0
 */
public class TableController
  extends BTable.TableSupport
{

////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////  

  /**
   * Return if the BTable is focus traversable.  
   * Default returns true.
   */
  public boolean isFocusTraversable()
  {                              
    return true;
  }

  /**
   * Callback when focusGained() on BTable.  The default
   * implementation is to select the first row if no rows
   * are currently selected.
   */
  public void focusGained(BFocusEvent event)
  {
    BTable table = getTable();
    TableSelection sel = table.getSelection();
    if (sel.getRow() == -1 && table.getModel().getRowCount() > 0) sel.select(0);
    getTable().repaint();
  }

  /**
   * Callback when focusLost() on BTable.  The default
   * implementation is to do nothing.
   */
  public void focusLost(BFocusEvent event)
  {
    getTable().repaint();
  }

////////////////////////////////////////////////////////////////
// Keyboard Input
////////////////////////////////////////////////////////////////

  /**
   * Callback when keyPressed() on BTable.  The default
   * implementation is to process the up, down, pageup
   * and pagedown keys.
   */
  public void keyPressed(BKeyEvent event) 
  {                 
    // Also, 21622 -- do not consume events with modifiers other than shift.
    if (event.getModifiersEx() != 0 && event.getModifiersEx()!= BInputEvent.SHIFT_DOWN_MASK) return;
    
    int key = event.getKeyCode();
    BTable table = getTable();
    TableSelection sel = table.getSelection();
    int anchor = sel.getAnchor();
    int lead = sel.getLead();
    int visible = table.getVisibleRowCount();
    int top = table.getVscrollBar().getPosition();
    int moveTo = -1;
    
    switch(key)
    {
      // Home --> goto first row
      case BKeyEvent.VK_HOME:
        moveTo = 0;
        searchText="";
        break;
      // End --> goto last row
      case BKeyEvent.VK_END:
        moveTo = table.getModel().getRowCount()-1;
        searchText="";
        break;
      case BKeyEvent.VK_UP:
        moveTo = Math.max(lead-1, 0);
        searchText="";
        break;
      case BKeyEvent.VK_DOWN:
        moveTo = Math.min(lead+1, table.getModel().getRowCount()-1);
        searchText="";
        break;
      case BKeyEvent.VK_PAGE_UP:
        if (lead > top && lead <= top+visible)
          moveTo = Math.max(top, 0);
        else
          moveTo = Math.max(lead-visible+1, 0);        
        searchText="";
        break;
      case BKeyEvent.VK_PAGE_DOWN:
        if (lead > top && lead < top+visible-1)
          moveTo = Math.min(top+visible-1, table.getModel().getRowCount()-1);
        else
          moveTo = Math.min(lead+visible-1, table.getModel().getRowCount()-1);
        searchText="";
        break;
    }  

    
    if (moveTo != -1)
    {
      event.consume();
      if (event.isShiftDown())
        sel.select(anchor, moveTo, true);
      else
        sel.select(moveTo, true);
      table.ensureRowIsVisible(moveTo);
    } 
    else if (getTextSearchColumn() != -1)
    {
      // try to map 0-9 or a-z to a list item
      char ch = event.getKeyChar();
      if ((BKeyEvent.VK_SPACE <= key && key <= BKeyEvent.VK_9) ||
          (BKeyEvent.VK_A <= key && key <= BKeyEvent.VK_Z))
      {
        if(Clock.ticks()-750<lastSearchTime)
          searchText = searchText + ch;
        else
          searchText = ""+ch;

        if(!search(searchText))
        {
          //searchText = "";
        }
        lastSearchTime = Clock.ticks();
        event.consume();
      }

    }
  }
  
  /**
   * Specify which column should be used for text searches initiated 
   * by user key presses.  Since text searches require iterating through
   * all table rows, care should be taken to to enable this for large tables.
   * @return The column index, or -1 if disabled (the default).
   */
  public int getTextSearchColumn()
  {
    return -1;
  }

  private boolean search(String jumpText)
  {
    TableModel model = table.getModel();
    TableSelection sel = getSelection();
    
    int index = -1;
    for(int i=0; i<model.getRowCount(); ++i)
    {
      String text = model.getValueAt(i, getTextSearchColumn()).toString();
      if (text != null && text.length() > 0 && 
          text.toLowerCase().startsWith(jumpText.toLowerCase()))
      {
        index = i;
        break;
      }
    }
    
    if (index >= 0)
    {
      sel.select(index, true);
      table.ensureRowIsVisible(index);
      return true;
    }
    return false;
  } 
  
  /**
   * Callback when keyReleased() on BTable.  The default
   * implementation is to process the Enter and Esc keys.
   */
  public void keyReleased(BKeyEvent event) 
  {
    if (event.getModifiersEx() != 0) return;
    if (event.getKeyCode() == BKeyEvent.VK_ENTER)
    {
      // have to do this on release, otherwise we will
      // close the popup and the release will be directed
      // to the text field
      handleEnter(event);
    }
    else if (event.getKeyCode() == BKeyEvent.VK_ESCAPE)
    {
      handleEscape(event);
    }
  }

  /**
   * Callback when keyTyped() on BTable.
   */
  public void keyTyped(BKeyEvent event) 
  {
  }
  
  /**
   * Default implementation is to fire actionPerformed.
   */
  protected void handleEnter(BKeyEvent event)
  {
    event.consume();
    getTable().fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getTable()));
  }

  /**
   * Default implementation is to fire cancelled.
   */
  protected void handleEscape(BKeyEvent event)
  {
    event.consume();
    getTable().fireCancelled(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getTable()));  
  }

////////////////////////////////////////////////////////////////
// Mouse Input
////////////////////////////////////////////////////////////////

  /**
   * Callback when mousePressed() on BTable.
   */
  public void mousePressed(BMouseEvent event) 
  {
    if (overRow != -1) cellPressed(event, overRow, overColumn);
    else if (overHeader != -1) headerPressed(event, overHeader);
    else if (overResize != -1) resizeHotspotPressed(event, overResize);
    else if (overBackground) backgroundPressed(event);
    
    // request focus if over a valid cell
    if (overRow != -1) getTable().requestFocus();
  }

  /**
   * Callback when mouseReleased() on BTable.
   */
  public void mouseReleased(BMouseEvent event) 
  {
    if (overRow != -1) cellReleased(event, overRow, overColumn);
    else if (overHeader != -1) headerReleased(event, overHeader);
    else if (overResize != -1) resizeHotspotReleased(event, overResize);
    else if (overBackground) backgroundReleased(event);
    checkMouseOver(event);
  }

  /**
   * Callback when mouseEntered() on BTable.
   */
  public void mouseEntered(BMouseEvent event) 
  {
    checkMouseOver(event);
  }

  /**
   * Callback when mouseExited() on BTable.
   */
  public void mouseExited(BMouseEvent event) 
  {
    fireExit(event);
  }

  /**
   * Callback when mouseMoved() on BTable.
   */
  public void mouseMoved(BMouseEvent event) 
  {
    if (checkMouseOver(event)) return;
    if (overRow != -1) cellMoved(event, overRow, overColumn);
    else if (overHeader != -1) headerMoved(event, overHeader);
    else if (overResize != -1) resizeHotspotMoved(event, overResize);
    else if (overBackground) backgroundMoved(event);
  }

  /**
   * Callback when mouseDragged() on BTable.
   */
  public void mouseDragged(BMouseEvent event) 
  {
    if (overRow != -1) cellDragged(event, overRow, overColumn);
    else if (overHeader != -1) headerDragged(event, overHeader);
    else if (overResize != -1) resizeHotspotDragged(event, overResize);
    else if (overBackground) backgroundDragged(event);
  }

  /**
   * Callback when mousePulsed() on BTable.
   */
  public void mousePulsed(BMouseEvent event) 
  {
    if (overRow != -1) cellPulsed(event, overRow, overColumn);
    else if (overHeader != -1) headerPulsed(event, overHeader);
    else if (overResize != -1) resizeHotspotPulsed(event, overResize);
    else if (overBackground) backgroundPulsed(event);
  }

  /**
   * Callback when mouseWheel() on BTable.
   */
  public void mouseWheel(BMouseWheelEvent event) 
  {
    BScrollBar sb = getTable().getVscrollBar();
    sb.scrollByUnits(event.getPreciseWheelRotation());
  }

  /**
   * Callback when mouseHover() on BTable.
   */
  public void mouseHover(BMouseEvent event) 
  {
    if (overRow != -1) cellHover(event, overRow, overColumn);
    else if (overHeader != -1) headerHover(event, overHeader);    
    else if (overBackground) backgroundHover(event);
  }
  
  /**
   * Figure out what the mouse is over.  If we moved 
   * over something new, then fire the appropiate exit 
   * and enter callbacks.  Return true if we changed
   * our mouse over.
   */
  private boolean checkMouseOver(BMouseEvent event)
  {
    BTable table = getTable();
    double mx = event.getX();
    double my = event.getY();
    int row = table.getRowAt(my);
    int col = table.getColumnAt(mx);
    
    // out of bounds
    if (row == -1 || col == -1)
    {
      if (!overBackground)
      {
        fireExit(event);
        overBackground = true;
        backgroundEntered(event);
        return true;
      }
    }
    
    // over header
    else if (row == Integer.MAX_VALUE)
    {
      // we are over a hotspot
      int resize = getResize(table, col, mx);
      if (resize != -1)
      {
        if (overResize != resize)
        {
          fireExit(event);
          resizeHotspotEntered(event, overResize = resize);
          return true;
        }
        else
        {
          return false;
        }
      }
      
      // we are over a header
      if (overHeader != col)
      {
        fireExit(event);
        headerEntered(event, overHeader = col);
        return true;
      }
    }
    
    // over cell
    else
    {
      // if we are allowing extended resize
      if (getTable().getExtendedResize())
      {
        // we are over a hotspot
        int resize = getResize(table, col, mx);
        if (resize != -1)
        {
          if (overResize != resize)
          {
            fireExit(event);
            resizeHotspotEntered(event, overResize = resize);
            return true;
          }
          else
          {
            return false;
          }
        }
      }

      // over cell
      if (overRow != row || overColumn != col) 
      {
        fireExit(event);
        cellEntered(event, overRow = row, overColumn = col);
        return true;
      }
    }
    
    return false;
  }
  
  /**
  * Figure out whether the mouse is over a resize hotspot
  */
  private int getResize(BTable table, int col, double mx)
  {
    mx = table.translateXToTable(mx);
    int colCount = table.getModel().getColumnCount();

    int resize = -1;
    RectGeom rect = table.getHeaderBounds(col);
    double leftEdge = rect.x;
    double rightEdge = rect.x + rect.width;
    if (mx > rightEdge-4 && col != colCount-1) resize = col+1;
    else if (mx < leftEdge+4 && col != 0) resize = col;

    return resize;
  }

  /**
   * Fire the exit event to the current mouse over.
   */
  private void fireExit(BMouseEvent event)
  {
    if (overRow != -1)
    {
      cellExited(event, overRow, overColumn);
      overRow = overColumn = -1;
    }
    else if (overHeader != -1)
    {
      headerExited(event, overHeader);
      overHeader = -1;
    }
    else if (overResize != -1) 
    {
      resizeHotspotExited(event, overResize); 
      overResize = -1; 
    }
    else if (overBackground)
    {
      backgroundExited(event);
      overBackground = false;
    }
  }

////////////////////////////////////////////////////////////////
// Mouse Overs
////////////////////////////////////////////////////////////////  
  
  /**
   * If the cursor is currently over a column header 
   * return the column index, otherwise return -1.
   */
  public int getMouseOverHeader()
  {
    return overHeader;
  }

  /**
   * If the cursor is currently over a table column, then
   * return the column index, otherwise return -1.  Note
   * that if the cursor is over a header, this method
   * will return -1.
   */
  public int getMouseOverColumn()
  {
    return overColumn;
  }

  /**
   * If the cursor is currently over a table row, then
   * return row index, otherwise return -1.
   */
  public int getMouseOverRow()
  {
    return overRow;
  }

  /**
   * If the cursor is currently over a column resize hotspot
   * return return the column index of the column to the right
   * of the hotspot.  If the cursor is not currently over a 
   * resize hotspot then return -1.
   */
  public int getMouseOverResizeColumn()
  {
    return overResize;
  }

////////////////////////////////////////////////////////////////
// Header
////////////////////////////////////////////////////////////////
  
  /**
   * Callback when the mouse enters a column header.  
   * The default is to do nothing.
   */
  protected void headerEntered(BMouseEvent event, int column)
  {
  }

  /**
   * Callback when the mouse exits a column header.  
   * The default is to do nothing.
   */
  protected void headerExited(BMouseEvent event, int column)
  {
  }

  /**
   * Callback when the mouse is pressed over a column header.  
   * The default implementation is to sort the column if
   * the model supports sorting on the specified column.
   */
  protected void headerPressed(BMouseEvent event, int column)
  {
    if (event.isButton1Down())
    {    
      BTable table = getTable();
      if (table.getModel().isColumnSortable(column))
      {
        boolean ascending = table.isSortAscending();
        if (table.getSortColumn() == column)
          table.sortByColumn(column, !ascending);
        else
          table.sortByColumn(column, ascending);
      }
    }
    
    if (event.isPopupTrigger()) 
    { 
      headerPopup(event, column);
    }
  }

  /**
   * Callback when the mouse is released over a column header.  
   * The default is to do nothing.
   */
  protected void headerReleased(BMouseEvent event, int column)
  {
    if (event.isPopupTrigger()) 
    { 
      headerPopup(event, column); 
    }
  }

  /**
   * Callback when the mouse is moved over a column header.  
   * The default is to do nothing.
   */
  protected void headerMoved(BMouseEvent event, int column)
  {
  }

  /**
   * Callback when the mouse is dragged over a column header.  
   * The default is to do nothing.
   */
  protected void headerDragged(BMouseEvent event, int column)
  {
  }

  /**
   * Callback when the mouse is pulsed over a column header.  
   * The default is to do nothing.
   */
  protected void headerPulsed(BMouseEvent event, int column)
  {
  }

  /**
   * Call for popup trigger over header.
   */
  protected void headerPopup(BMouseEvent event, int column)
  {
    TableModel model = getModel();
    
    if (model instanceof DynamicTableModel)
    {
      DynamicTableModel dynamicModel = (DynamicTableModel)model;
      column = dynamicModel.toRootColumnIndex(column);
      if (dynamicModel.isColumnShowable(column))
      {
        BMenu menu = new BMenu();
        menu.add(null, new HideColumnCommand(dynamicModel, column));
        menu.open(getTable(), event.getX(), event.getY());
      }
    }
  }

  /**
   * Callback when the mouse is hovering over a column header.  
   * The default is to do nothing.
   */
  protected void headerHover(BMouseEvent event, int column)
  {
  }

////////////////////////////////////////////////////////////////
// Cell
////////////////////////////////////////////////////////////////
  
  /**
   * Callback when the mouse enters a cell.  
   * The default is to do nothing.
   */
  protected void cellEntered(BMouseEvent event, int row, int column)
  {
  }

  /**
   * Callback when the mouse exits a cell.  
   * The default is to do nothing.
   */
  protected void cellExited(BMouseEvent event, int row, int column)
  {
  }

  /**
   * Callback when the mouse is pressed over a cell.  
   * The default implementation of this method is to
   * select or deselect the specified row.
   */
  protected void cellPressed(BMouseEvent event, int row, int column)
  {
    // a press over a currently selected row then we doesn't 
    // do anything until release (could be drag or popup)
    if (getSelection().isSelected(row))
    {
      checkSelectionOnRelease = true;
    }
    else
    {
      checkSelectionOnRelease = false;
      checkSelection(event, row);
    }
    
    if (event.isPopupTrigger()) 
    { 
      cellPopup(event, row, column); 
    }
    
    if (event.getClickCount() == 2)
    {
      checkSelectionOnRelease = false;
      cellDoubleClicked(event, row, column);
    }
  }

  /**
   * Callback when the mouse is released over a cell.  
   * The default is to check selection.
   */
  protected void cellReleased(BMouseEvent event, int row, int column)
  {
    if (checkSelectionOnRelease)
    {
      checkSelectionOnRelease = false;
      if (!event.isPopupTrigger()) checkSelection(event, row);
    }
    
    if (event.isPopupTrigger()) 
    { 
      cellPopup(event, row, column); 
    }
  }

  /**
   * Callback when the mouse is moved over a cell.  
   * The default is to do nothing.
   */
  protected void cellMoved(BMouseEvent event, int row, int column)
  {
  }

  /**
   * Callback when the mouse is dragged over a cell.  
   * The default is to do nothing.
   */
  protected void cellDragged(BMouseEvent event, int row, int column)
  {
  }

  /**
   * Callback when the mouse is pulsed over a cell.  
   * The default is to do nothing.
   */
  protected void cellPulsed(BMouseEvent event, int row, int column)
  {
  }  

  /**
   * Call for double click over cell.
   */
  protected void cellDoubleClicked(BMouseEvent event, int row, int column)
  {
  }

  /**
   * Callback when the mouse is hovering over a cell.
   * The default is to do nothing.
   */
  protected void cellHover(BMouseEvent event, int row, int column)
  {
  }

////////////////////////////////////////////////////////////////
// Resize Column
////////////////////////////////////////////////////////////////  

  /**
   * Callback when mouse is pressed over resize hotspot.
   * The default is to do nothing.
   */
  protected void resizeHotspotPressed(BMouseEvent event, int column)
  {
  }

  /**
   * Callback when mouse is released over resize hotspot.
   * The default is to do nothing.
   */
  protected void resizeHotspotReleased(BMouseEvent event, int column)
  {
  }

  /**
   * This callback is invoked when the mouse enters a 
   * potential column resize hotspot.  The specified column
   * index is the index of the column to the right of the
   * hotspot.  So a column index of 3 would indicate a resize
   * between columns 2 and 3.
   * <p>
   * The default implementation is to change the cursor
   * to the east/west resize cursor.
   */
  protected void resizeHotspotEntered(BMouseEvent event, int column)
  {
    getTable().setMouseCursor(MouseCursor.eResize);
  }

  /**
   * This callback is invoked when the mouse exits a 
   * potential column resize hotspot.  The specified column
   * index is the index of the column to the right of the
   * border.  So a column index of 3 would indicate a resize
   * between columns 2 and 3.
   * <p>
   * The default implementation is to change the cursor back
   * to the default cursor.
   */
  protected void resizeHotspotExited(BMouseEvent event, int column)
  {
    getTable().setMouseCursor(MouseCursor.normal);
  }

  /**
   * Callback when mouse is moved over resize hotspot.
   * The default is to do nothing.
   */
  protected void resizeHotspotMoved(BMouseEvent event, int column)
  {
  }

  /**
   * Callback when mouse is dragged over resize hotspot.
   * The default is to update the table with a call
   * to setColumnPosition().
   */
  protected void resizeHotspotDragged(BMouseEvent event, int column)
  {
    getTable().setColumnPosition(column, event.getX());
  }

  /**
   * Callback when mouse is pulsed over resize hotspot.
   * The default is to do nothing.
   */
  protected void resizeHotspotPulsed(BMouseEvent event, int column)
  {
  }
  
////////////////////////////////////////////////////////////////
// Background
////////////////////////////////////////////////////////////////
  
  /**
   * Callback when the mouse enters background (below rows).
   */
  protected void backgroundEntered(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse exits background (below rows).
   */
  protected void backgroundExited(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse is pressed over background (below rows).
   */
  protected void backgroundPressed(BMouseEvent event)
  {
    if (event.isPopupTrigger()) 
      backgroundPopup(event); 
    // if not a right click then deselect all
    else if (!event.isButton3Down()) 
      getSelection().deselectAll();    
  }

  /**
   * Callback when the mouse is released over background (below rows).
   */
  protected void backgroundReleased(BMouseEvent event)
  {
    if (event.isPopupTrigger()) 
      backgroundPopup(event); 
  }

  /**
   * Callback when the mouse is moved over background (below rows).
   */
  protected void backgroundMoved(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse is dragged over background (below rows).
   */
  protected void backgroundDragged(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse is pulsed over background (below rows).  
   */
  protected void backgroundPulsed(BMouseEvent event)
  {
  }
  
  /**
   * Callback when the mouse is hovering over background (below rows).
   * The default is to do nothing.
   */
  protected void backgroundHover(BMouseEvent event)
  {
  }
  
////////////////////////////////////////////////////////////////
// Popup
////////////////////////////////////////////////////////////////
  
  /**
   * This callback is made when the user triggers a popup over a cell.  
   * This method routes to <code>popup(event, row, column)</code> 
   */
  protected void cellPopup(BMouseEvent event, int row, int column) 
  { 
    popup(event, row, column);
  }

  /**
   * This callback is made when the user triggers a popup 
   * in the background space where no cells are visible.  
   * This method routes to <code>popup(event, -1, -1)</code> 
   */
  protected void backgroundPopup(BMouseEvent event)
  {                          
    popup(event, -1, -1);
  }
  
  /**
   * Callback when a mouse event triggers a popup menu.  The
   * row and col specifies the cell the user clicked over, or
   * -1 if the user clicked over the background.  This method
   * routes to makePopup() and opens the popup.
   */
  protected void popup(BMouseEvent event, int row, int col)
  {                                               
    TableSubject subject = getSelection().getSubject(row);
    BMenu menu = makePopup(subject);
    if (menu != null) 
    {
      menu.removeConsecutiveSeparators();
      menu.open(event);
    }
  }                                                  
  
  /**
   * Make a popup menu for the specified subject 
   * selection or return null to display no popup menu.
   */
  protected BMenu makePopup(TableSubject subject)
  {
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Options
////////////////////////////////////////////////////////////////
  
  /**
   * Callback for when the option button is invoked.
   * The default implement builds a drop down with the
   * a command to auto resize the columns.  If the
   * model is an instance of DynamicTableModel then
   * provide a toggle for each of the columns.  The
   * default implementation routes to makeOptionsMenu()
   * to allow customization.
   */
  public void doOptions()
  {                          
    BMenu menu = makeOptionsMenu();
    if (menu != null)
    {
      BTable table = getTable();
      menu.computePreferredSize();               
      BButton b = table.getOptionsButton();
      double x = b.getX() + b.getWidth();
      double y = b.getY() + b.getHeight();
      if (x <= 0) x = 0;
      if (y <= 0) y = 0;
      x -= menu.getPreferredWidth();
      menu.open(table, x, y);
    }
  }
  
  /**
   * Build the options menu which displays with the 
   * options button is displayed.
   */      
  protected BMenu makeOptionsMenu()
  {
    BTable table = getTable();
    TableModel model = getModel();
    
    BMenu menu = new BMenu();
    
    menu.add("resizeColumns", new ResizeColumnsCommand(table));
    /*
    menu.add("print",         new PrintCommand(table));
    */
    menu.add("export",        new ExportCommand(table));
    
    if (model instanceof DynamicTableModel)
    {                                       
      DynamicTableModel dynamicModel = (DynamicTableModel)model;
      menu.add("showSep", new BSeparator());
      int count = 0;
      for(int i=0; i<dynamicModel.getRootColumnCount(); ++i)
      {
        if (dynamicModel.isColumnShowable(i))
        {
          menu.add(null, new ShowColumnCommand(dynamicModel, i));
          count++;
        }
      }
      if (count == 0) menu.remove("showSep");
    }
    return menu;
  }                       
  
  public class ResizeColumnsCommand extends Command
  {     
    public ResizeColumnsCommand(BTable owner)
    {                                
      super(owner, UiLexicon.bajaui().module, "commands.table.resizeColumns");
    }                                
    
    public CommandArtifact doInvoke()
    {                     
      getTable().sizeColumnsToFit();
      return null;
    }
  }

  class PrintCommand extends Command
  {     
    PrintCommand(BTable owner)
    {                                
      super(owner, UiLexicon.bajaui().module, "commands.print");
      accelerator = null;
    }                                
    
    public CommandArtifact doInvoke() throws Exception
    {                   
      return exportDialog((BTable)getOwner(), true);  
    }
  }

  public class ExportCommand extends Command
  {     
    public ExportCommand(BTable owner)
    {                                
      super(owner, UiLexicon.bajaui().module, "commands.export");
      accelerator = null;
    }                                
    
    public CommandArtifact doInvoke() throws Exception
    {                     
      return exportDialog((BTable)getOwner(), true);  
    }
  }              
  
  static CommandArtifact exportDialog(BTable table, boolean asPrint)
    throws Exception
  {                                        
    try
    {
      BITable<? extends BIObject> itable = table.getModel().export();
      Type type = Sys.getType("workbench:ExportDialog");
      Class<?> cls = type.getTypeClass();
      Method method = cls.getMethod("invoke", new Class<?>[] { BWidget.class, BITable.class, boolean.class });
      return (CommandArtifact)method.invoke(null, new Object[] { table, itable, Boolean.valueOf(asPrint) });
    }
    catch(InvocationTargetException e)
    {
      throw (Exception)e.getTargetException();
    }
  }

  class HideColumnCommand extends Command
  {
    HideColumnCommand(DynamicTableModel model, int column)
    {                                
      super(model.getTable(), UiLexicon.bajaui().module, "commands.table.hideColumn");
      this.model = model;
      this.column = column;             
    }                                
    
    public CommandArtifact doInvoke()
    {
      model.setShowColumn(column, false);
      return null;
    }            
    
    DynamicTableModel model;
    int column;
  }

  class ShowColumnCommand extends ToggleCommand
  {     
    ShowColumnCommand(DynamicTableModel model, int column)
    {                                
      super(model.getTable(), model.getRootColumnName(column));
      this.model = model;
      this.column = column;             
      setSelected(model.showColumn(column));
    }                                
    
    public void setSelected(boolean sel)
    {                                  
      super.setSelected(sel);
      model.setShowColumn(column, sel);
    }            
    
    DynamicTableModel model;
    int column;
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  /**
   * Check if the selection should be changed.
   */
  protected void checkSelection(BMouseEvent event, int row)
  {
    BTable table = getTable();
    TableSelection sel = table.getSelection();
    
    if (table.getMultipleSelection())
    {      
      if (event.isShiftDown() && (sel.getAnchor() >= 0))
      {
        sel.select(sel.getAnchor(), row, !event.isControlDown());
      }
      else if (event.isControlDown())
      {
        if (sel.isSelected(row)) sel.deselect(row);
        else sel.select(row);
      }
      else
      {
        sel.select(row, true);
        getTable().fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getTable()));
      }
    }
    else
    {
      sel.select(row, true);
      getTable().fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getTable()));
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private int overResize = -1;
  private int overHeader = -1;
  private int overRow    = -1;
  private int overColumn = -1;
  private boolean overBackground;
  private boolean checkSelectionOnRelease;
  
  private String searchText = "";  // Current text search string
  private long lastSearchTime = 0; // Clock ticks when the user modified their search text 
}
