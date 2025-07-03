/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.list;

import javax.baja.sys.Clock;
import javax.baja.ui.BMenu;
import javax.baja.ui.BScrollBar;
import javax.baja.ui.event.*;

/**
 * ListController is used to respond to user input on BList.
 *
 * @author    Brian Frank
 * @creation  9 Jul 02
 * @version   $Revision: 15$ $Date: 5/26/10 9:27:55 AM EDT$
 * @since     Baja 1.0
 */
public class ListController
  extends BList.ListSupport
{

////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////  

  /**
   * Callback when focusGained() on BList.  The default
   * implementation is to select the first item if no items
   * are currently selected.
   */
  public void focusGained(BFocusEvent event)
  {
    // if we have items, then make sure at least one is
    // selected and that it is visible
    BList list = getList();
    ListSelection sel = list.getSelection();
    if (sel.getItemCount() > 0)
    { 
      if (sel.getItem() == -1) sel.select(0);
      getList().ensureItemIsVisible(sel.getItem());
    }
  }

  /**
   * Callback when focusLost() on BList.  The default
   * implementation is to do nothing.
   */
  public void focusLost(BFocusEvent event)
  {
    getList().repaint();
  }

////////////////////////////////////////////////////////////////
// Keyboard Input
////////////////////////////////////////////////////////////////

  /**
   * Callback when keyPressed() on BList.  The default
   * implementation is to process the up, down, pageup
   * and pagedown keys.
   */
  public void keyPressed(BKeyEvent event) 
  {
    //issue 16527, short circuit on any key modifiers (ctrl, alt, shift)
    if (event.getModifiersEx() != 0 && event.getModifiersEx()!=BInputEvent.SHIFT_DOWN_MASK) return;
    
    int key = event.getKeyCode();
    BList list = getList();
    ListSelection sel = list.getSelection();
    int lead = sel.getLead();
    int visible = list.getVisibleRowCount();
    int top = list.getVscrollBar().getPosition();
    
    switch(key)
    {
      // Home --> goto first row
      case BKeyEvent.VK_HOME:
        event.consume();
        sel.select(0, true);
        list.ensureItemIsVisible(0);
        searchText="";
        break;
      // End --> goto last row
      case BKeyEvent.VK_END:
        event.consume();
        int item = list.getModel().getItemCount()-1;
        sel.select(item, true);
        list.ensureItemIsVisible(item);
        searchText="";
        break;
      case BKeyEvent.VK_UP:
        event.consume();
        lead = Math.max(lead-1, 0);
        sel.select(lead, true);
        list.ensureItemIsVisible(lead);
        searchText="";
        break;
      case BKeyEvent.VK_DOWN:
        event.consume();
        lead = Math.min(lead+1, list.getModel().getItemCount()-1);
        sel.select(lead, true);
        list.ensureItemIsVisible(lead);
        searchText="";
        break;
      case BKeyEvent.VK_PAGE_UP:
        event.consume();
        if (lead > top && lead <= top+visible)
          lead = Math.max(top, 0);
        else
          lead = Math.max(lead-visible+1, 0);        
        sel.select(lead, true);
        list.ensureItemIsVisible(lead);
        searchText="";
        break;
      case BKeyEvent.VK_PAGE_DOWN:
        event.consume();
        if (lead > top && lead < top+visible-1)
          lead = Math.min(top+visible-1, list.getModel().getItemCount()-1);
        else
          lead = Math.min(lead+visible-1, list.getModel().getItemCount()-1);
        sel.select(lead, true);
        list.ensureItemIsVisible(lead);
        searchText="";
        break;
      default:
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
   * Callback when keyReleased() on BList.  The default
   * implementation is to process the Enter and Esc keys.
   */
  public void keyReleased(BKeyEvent event) 
  {
    if (event.getModifiersEx() != 0) return;
    if (event.getKeyCode() == BKeyEvent.VK_ENTER  
        || event.getKeyCode() == BKeyEvent.VK_TAB)
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
   * Callback when keyTyped() on BList.
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
    getList().fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getList()));
  }

  /**
   * Default implementation is to fire cancelled.
   */
  protected void handleEscape(BKeyEvent event)
  {
    event.consume();
    getList().fireCancelled(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getList()));  
  }
  
  /**
   * When a key is pressed, try to auto select the item.
   */
  private boolean search(String searchText)
  {
    ListModel model = getModel();
    ListSelection sel = getSelection();
    ListRenderer renderer = getRenderer();
    ListRenderer.Item item = new ListRenderer.Item();
    
    int index = -1;
    for(int i=0; i<model.getItemCount(); ++i)
    {
      item.value = model.getItem(i);
      String text = renderer.getItemText(item);
      if (text != null && text.length() > 0 && 
          text.toLowerCase().startsWith(searchText.toLowerCase()))
      {
        index = i;
        break;
      }
    }
    
    if (index >= 0)
    {
      sel.select(index, true);
      list.ensureItemIsVisible(index);
      return true;
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Mouse Input
////////////////////////////////////////////////////////////////

  /**
   * Callback when mousePressed() on BList.
   */
  public void mousePressed(BMouseEvent event) 
  {
    if (overItem != -1) itemPressed(event, overItem);
    else if (overBackground) backgroundPressed(event);
    
    // request focus
    getList().requestFocus();
  }

  /**
   * Callback when mouseReleased() on BList.
   */
  public void mouseReleased(BMouseEvent event) 
  {
    if (overItem != -1) itemReleased(event, overItem);
    else if (overBackground) backgroundReleased(event);
    checkMouseOver(event);
  }

  /**
   * Callback when mouseEntered() on BList.
   */
  public void mouseEntered(BMouseEvent event) 
  {              
    // reset last mouse coordinates - see mouseMoved()
    lastx = -1;
    lasty = -1;
    
    // force checkMouseOver() to be called at least
    // once so that touch screen displays will
    // function correctly
    mouseMoved(event);
  }

  /**
   * Callback when mouseExited() on BList.
   */
  public void mouseExited(BMouseEvent event) 
  {
    fireExit(event);
  }

  /**
   * Callback when mouseMoved() on BList.
   */
  public void mouseMoved(BMouseEvent event) 
  {                                                            
    // don't process "fake" moves sometimes generated by 
    // the framework because we don't want to actually select 
    // something that just happened to open in a drop down directly 
    // under the mouse - make the user move the mouse first
    if (event.getX() == lastx && event.getY() == lasty)
    {
      return;
    } else if (lastx==-1 && lasty==-1)
    {
      lastx = event.getX();
      lasty = event.getY();
      return;
    }
    lastx = event.getX();
    lasty = event.getY();
    
    if (checkMouseOver(event)) return;
    if (overItem != -1) itemMoved(event, overItem);
    else if (overBackground) backgroundMoved(event);
  }

  /**
   * Callback when mouseDragged() on BList.
   */
  public void mouseDragged(BMouseEvent event) 
  {
    if (overItem != -1) itemDragged(event, overItem);
    else if (overBackground) backgroundDragged(event);
  }

  /**
   * Callback when mousePulsed() on BList.
   */
  public void mousePulsed(BMouseEvent event) 
  {
    if (overItem != -1) itemPulsed(event, overItem);
    else if (overBackground) backgroundPulsed(event);
  }

  /**
   * Callback when mouseWheel() on BList.
   */
  public void mouseWheel(BMouseWheelEvent event) 
  {
    BScrollBar sb = getList().getVscrollBar();
    sb.scrollByUnits(event.getPreciseWheelRotation());
  }
  
  /**
   * Figure out what the mouse is over.  If we moved 
   * over something new, then fire the appropiate exit 
   * and enter callbacks.  Return true if we changed
   * our mouse over.
   */
  private boolean checkMouseOver(BMouseEvent event)
  {
    BList list = getList();
    double mx = event.getX();
    double my = event.getY();
    int index = list.getItemIndexAt(mx, my);

    // out of bounds
    if (index == -1)
    {
      if (!overBackground)
      {
        fireExit(event);
        overBackground = true;
        backgroundEntered(event);
        return true;
      }
    }
        
    // over cell
    else
    {
      if (overItem != index) 
      {
        fireExit(event);     
        itemEntered(event, overItem = index);
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Fire the exit event to the current mouse over.
   */
  private void fireExit(BMouseEvent event)
  {
    if (overItem != -1)
    {
      itemExited(event, overItem);
      overItem = -1;
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
   * If the cursor is currently over a item, then
   * return the item index, otherwise return -1.
   */
  public int getMouseOverItem()
  {
    return overItem;
  }

  /**
   * Callback when the mouse enters a item cell.  
   * The default is to do nothing.
   */
  protected void itemEntered(BMouseEvent event, int index)
  {
  }

  /**
   * Callback when the mouse exits a item cell.  
   * The default is to do nothing.
   */
  protected void itemExited(BMouseEvent event, int index)
  {
  }

  /**
   * Callback when the mouse is pressed over a item.  
   * The default implementation of this method is to
   * select or deselect the specified item.
   */
  protected void itemPressed(BMouseEvent event, int index)
  {
    // a press over a currently selected item then we doesn't 
    // do anything until release (could be drag or popup)
    if (getSelection().isSelected(index))
    {
      checkSelectionOnRelease = true;
    }
    else
    {
      checkSelectionOnRelease = false;
      checkSelection(event, index);
    }
    
    if (event.isPopupTrigger()) 
    { 
      itemPopup(event, index); 
    }
    
    if (event.getClickCount() == 2)
    {
      checkSelectionOnRelease = false;
      itemDoubleClicked(event, index);
    }
  }

  /**
   * Callback when the mouse is released over a item.  
   * The default is to check selection.
   */
  protected void itemReleased(BMouseEvent event, int index)
  {
    if (checkSelectionOnRelease)
    {
      checkSelectionOnRelease = false;
      if (!event.isPopupTrigger()) checkSelection(event, index);
    }
    
    if (event.isPopupTrigger()) 
    { 
      itemPopup(event, index); 
    }
  }

  /**
   * Callback when the mouse is moved over a item.  
   * The default is to do nothing.
   */
  protected void itemMoved(BMouseEvent event, int index)
  {
  }

  /**
   * Callback when the mouse is dragged over a item cell.  
   * The default is to do nothing.
   */
  protected void itemDragged(BMouseEvent event, int index)
  {
  }

  /**
   * Callback when the mouse is pulsed over a item cell.  
   * The default is to do nothing.
   */
  protected void itemPulsed(BMouseEvent event, int index)
  {
  }
  
  /**
   * Call for double click over item.
   */
  protected void itemDoubleClicked(BMouseEvent event, int index)
  {
  }

////////////////////////////////////////////////////////////////
// Background
////////////////////////////////////////////////////////////////
  
  /**
   * Callback when the mouse enters background (below items).
   */
  protected void backgroundEntered(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse exits background (below items).
   */
  protected void backgroundExited(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse is pressed over background (below items).
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
   * Callback when the mouse is released over background (below items).
   */
  protected void backgroundReleased(BMouseEvent event)
  {
    if (event.isPopupTrigger()) 
      backgroundPopup(event); 
  }

  /**
   * Callback when the mouse is moved over background (below items).
   */
  protected void backgroundMoved(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse is dragged over background (below items).
   */
  protected void backgroundDragged(BMouseEvent event)
  {
  }

  /**
   * Callback when the mouse is pulsed over background (below items).  
   */
  protected void backgroundPulsed(BMouseEvent event)
  {
  }

////////////////////////////////////////////////////////////////
// Popup
////////////////////////////////////////////////////////////////

  /**
   * This callback is made when the user triggers a popup over an item.  
   * This method routes to <code>popup(event, index)</code> 
   */
  protected void itemPopup(BMouseEvent event, int index) 
  { 
    popup(event, index);
  }

  /**
   * This callback is made when the user triggers a popup 
   * in the background space where no items are visible.  
   * This method routes to <code>popup(event, -1)</code> 
   */
  protected void backgroundPopup(BMouseEvent event)
  {                          
    popup(event, -1);
  }

  /**
   * Callback when a mouse event triggers a popup menu.  The
   * index specifies the item index the user clicked over, or
   * -1 if the user clicked over the background.  This method
   * routes to makePopup() and opens the popup.
   */
  protected void popup(BMouseEvent event, int index)
  {                                               
    ListSubject subject = getSelection().getSubject(index);
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
  protected BMenu makePopup(ListSubject subject)
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  /**
   * Check if the selection should be changed.
   */
  protected void checkSelection(BMouseEvent event, int item)
  {
    BList list = getList();
    ListSelection sel = list.getSelection();
    
    if (list.getMultipleSelection())
    {      
      if (event.isShiftDown())
      {
        sel.select(sel.getAnchor(), item, !event.isControlDown());
      }
      else if (event.isControlDown())
      {
        if (sel.isSelected(item)) sel.deselect(item);
        else sel.select(item);
      }
      else
      {
        sel.select(item, true);
        list.fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getList()));
      }
    }
    else
    {
      sel.select(item, true);
      list.fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getList()));
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private String searchText = "";  // Current text search string
  private long lastSearchTime = 0; // Clock ticks when the user modified their search text

  private int overItem = -1;
  private boolean overBackground;
  private boolean checkSelectionOnRelease;
  private double lastx, lasty;
  
}
