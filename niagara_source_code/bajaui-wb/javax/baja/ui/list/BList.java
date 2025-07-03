/*                          
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.list;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;
import javax.baja.ui.transfer.*;

import com.tridium.ui.theme.*;

/**
 * BList is used to visualize a list of items.
 *
 * <p>
 * BList is composed of several pluggable support classes:
 * <ul>
 * <li><b>Model:</b> The list model stores the items.  There is 
 * required one to one  mapping between BList and ListModel.
 * </li>
 * <li><b>Controller:</b> The ListController is responsible
 *   for processing all user input on the list in the form
 *   of focus, keyboard, and mouse events.  All input events
 *   are routed to the installed controller for processing.
 *   There is required one to one mapping between BList and 
 *   ListController.
 * </li>
 * <li><b>Selection:</b> Every list has a ListSelection
 *   instance which is used to store the current selection
 *   and provide access to modify the selection.  There is 
 *   required one to one mapping between BList and  ListSelection.
 * </li>
 * <li><b>Renderer:</b> The list's installed ListRenderer
 *   is used to paint each item.  There is required one to one 
 *   mapping between BList and ListRenderer.
 * </li>
 * </ul>
 *
 * @author    Brian Frank
 * @creation  9 Jul 02
 * @version   $Revision: 55$ $Date: 6/29/11 12:15:44 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 If true then multiple rows may be selected, or if
 false then only row at a time may be selected.
 */
@NiagaraProperty(
  name = "multipleSelection",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "vscrollBar",
  type = "BScrollBar",
  defaultValue = "new BScrollBar(BOrientation.vertical)",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN
)
@NiagaraAction(
  name = "vscroll",
  parameterType = "BScrollEvent",
  defaultValue = "new BScrollEvent()"
)
/*
 Event fired when the user presses the Enter key
 or the selects a row with the mouse.
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
/*
 Fired when the user presses the Esc key.
 */
@NiagaraTopic(
  name = "cancelled",
  eventType = "BWidgetEvent"
)
/*
 Fired when the contents are modified.
 */
@NiagaraTopic(
  name = "listModified",
  eventType = "BWidgetEvent"
)
/*
 Fired when the list selection is modified.
 */
@NiagaraTopic(
  name = "selectionModified",
  eventType = "BWidgetEvent"
)
public class BList
  extends javax.baja.ui.transfer.BTransferWidget
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.list.BList(3097087234)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "multipleSelection"

  /**
   * Slot for the {@code multipleSelection} property.
   * If true then multiple rows may be selected, or if
   * false then only row at a time may be selected.
   * @see #getMultipleSelection
   * @see #setMultipleSelection
   */
  public static final Property multipleSelection = newProperty(0, true, null);

  /**
   * Get the {@code multipleSelection} property.
   * If true then multiple rows may be selected, or if
   * false then only row at a time may be selected.
   * @see #multipleSelection
   */
  public boolean getMultipleSelection() { return getBoolean(multipleSelection); }

  /**
   * Set the {@code multipleSelection} property.
   * If true then multiple rows may be selected, or if
   * false then only row at a time may be selected.
   * @see #multipleSelection
   */
  public void setMultipleSelection(boolean v) { setBoolean(multipleSelection, v, null); }

  //endregion Property "multipleSelection"

  //region Property "vscrollBar"

  /**
   * Slot for the {@code vscrollBar} property.
   * @see #getVscrollBar
   * @see #setVscrollBar
   */
  public static final Property vscrollBar = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN, new BScrollBar(BOrientation.vertical), null);

  /**
   * Get the {@code vscrollBar} property.
   * @see #vscrollBar
   */
  public BScrollBar getVscrollBar() { return (BScrollBar)get(vscrollBar); }

  /**
   * Set the {@code vscrollBar} property.
   * @see #vscrollBar
   */
  public void setVscrollBar(BScrollBar v) { set(vscrollBar, v, null); }

  //endregion Property "vscrollBar"

  //region Action "vscroll"

  /**
   * Slot for the {@code vscroll} action.
   * @see #vscroll(BScrollEvent parameter)
   */
  public static final Action vscroll = newAction(0, new BScrollEvent(), null);

  /**
   * Invoke the {@code vscroll} action.
   * @see #vscroll
   */
  public void vscroll(BScrollEvent parameter) { invoke(vscroll, parameter, null); }

  //endregion Action "vscroll"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * Event fired when the user presses the Enter key
   * or the selects a row with the mouse.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * Event fired when the user presses the Enter key
   * or the selects a row with the mouse.
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Topic "cancelled"

  /**
   * Slot for the {@code cancelled} topic.
   * Fired when the user presses the Esc key.
   * @see #fireCancelled
   */
  public static final Topic cancelled = newTopic(0, null);

  /**
   * Fire an event for the {@code cancelled} topic.
   * Fired when the user presses the Esc key.
   * @see #cancelled
   */
  public void fireCancelled(BWidgetEvent event) { fire(cancelled, event, null); }

  //endregion Topic "cancelled"

  //region Topic "listModified"

  /**
   * Slot for the {@code listModified} topic.
   * Fired when the contents are modified.
   * @see #fireListModified
   */
  public static final Topic listModified = newTopic(0, null);

  /**
   * Fire an event for the {@code listModified} topic.
   * Fired when the contents are modified.
   * @see #listModified
   */
  public void fireListModified(BWidgetEvent event) { fire(listModified, event, null); }

  //endregion Topic "listModified"

  //region Topic "selectionModified"

  /**
   * Slot for the {@code selectionModified} topic.
   * Fired when the list selection is modified.
   * @see #fireSelectionModified
   */
  public static final Topic selectionModified = newTopic(0, null);

  /**
   * Fire an event for the {@code selectionModified} topic.
   * Fired when the list selection is modified.
   * @see #selectionModified
   */
  public void fireSelectionModified(BWidgetEvent event) { fire(selectionModified, event, null); }

  //endregion Topic "selectionModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////  

  /**
   * Constructor with an empty DefaultListModel.
   */
  public BList()
  {
    this(new DefaultListModel(), new ListController());
  }

  /**
   * Constructor with specified model.
   */
  public BList(ListModel model)
  {
    this(model, new ListController());
  }

  /**
   * Constructor with specified controller.
   */
  public BList(ListController controller)
  {
    this(new DefaultListModel(), controller);
  }

  /**
   * Constructor with model and controller.
   */
  public BList(ListModel model, ListController controller)
  {
    setModel(model);
    setController(controller);
    setSelection(new ListSelection());
    setRenderer(new ListRenderer());

    linkTo(getVscrollBar(), BScrollBar.positionChanged, BList.vscroll);
  }
  
  public void started()
  {
    linkTo(getVscrollBar(), BScrollBar.positionChanged, BList.vscroll);
  }    

////////////////////////////////////////////////////////////////
// ListSupport
////////////////////////////////////////////////////////////////  

  /**
   * Get the table model currently installed.
   */
  public ListModel getModel()
  {
    return model;
  }

  /**
   * Install the specified model this BList visualizes.
   */
  public void setModel(ListModel model)
  {
    installSupport(this.model, model);
    this.model = model;
  }

  /**
   * Get the controller currently installed.
   */
  public ListController getController()
  {
    return controller;
  }

  /**
   * Install the specified controller.
   */
  public void setController(ListController controller)
  {
    installSupport(this.controller, controller);
    this.controller = controller;
  }

  /**
   * Get the current list selection.
   */
  public ListSelection getSelection()
  {
    return selection;
  }

  /**
   * Install the specified selection model.
   */
  public void setSelection(ListSelection selection)
  {
    installSupport(this.selection, selection);
    this.selection = selection;
  }

  /**
   * Get the ListRenderer currently installed.
   */
  public ListRenderer getRenderer()
  {
    return renderer;
  }

  /**
   * Install the specified ListRenderer.
   */
  public void setRenderer(ListRenderer renderer)
  {
    installSupport(this.renderer, renderer);
    this.renderer = renderer;
    this.itemHeight = renderer.getItemHeight();
  }
  
  /**
   * Check that the specified support is not null and 
   * not installed on another table.
   */
  private void installSupport(ListSupport old, ListSupport support)
  {
    if (support == null) throw new NullPointerException();
    if (old == support) return;
    if (support.list != null) throw new IllegalArgumentException("Already installed on another list");
    if (old != null) old.list = null;
    support.list = this;
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Relayout and repaint when properties change.
   */
  public void changed(Property prop, Context context)
  {
    relayout();
  }

  /**
   * Compute the preferred size of the list.
   */
  public void computePreferredSize()
  {  
    // our running totals
    double w = 0, h = 0;
    
    // compute max width
    int count = model.getItemCount();
    for(int i=0; i<count; ++i)
    {
      item.index = i;
      item.icon = model.getItemIcon(i);
      item.value = model.getItem(i);
      w = Math.max(w, renderer.getPreferredItemWidth(item));
    }
      
    // add up height
    h += model.getItemCount() * itemHeight;
    
    // all computed!
    w = Math.max(w, 10);
    h = Math.max(h, 10);
    setPreferredSize(w + 2, h + 2); //shadow/highlight/outline
  }

  /**
   * Layout the list.
   */
  public void doLayout(BWidget[] kids)
  {
    // cache some useful attributes
    double w = getWidth();
    double h = getHeight();
    int count = model.getItemCount();
    double scrollWidth = Theme.scrollBar().getFixedWidth();
    int visibleRows = getVisibleRowCount();
    
    // layout the scroll bar
    BScrollBar sb = getVscrollBar();
    if (visibleRows < count)
    {
      sb.setVisible(true);
      sb.setMin(0);
      sb.setMax(count);
      sb.setExtent(visibleRows);
      sb.setBlockIncrement(Math.max(3, visibleRows-1));
      //shadow/highlight/outline
      sb.setBounds(w-scrollWidth - 1, 1, scrollWidth, h - 2);
    }
    else
    {
      sb.setVisible(false);
      sb.setBounds(0,0,0,0);
      sb.setPosition(0);
      sb.setMin(0);
      sb.setMax(visibleRows);
      sb.setExtent(visibleRows);
    }
  }
  
////////////////////////////////////////////////////////////////
// Positioning
////////////////////////////////////////////////////////////////

  /**
   * Get the number of visible rows for the list.  
   */
  public int getVisibleRowCount()
  {
    return (int)(getHeight() / itemHeight);
  }

  /**
   * Translate a coordinate into a item index or return -1
   * if the coordinate does not map to an item's cell.
   */
  public int getItemIndexAt(double x, double y)
  {
    if (x < 0 || x > getWidth()-getVscrollBar().getWidth())
      return -1;
     
    int index = (int)(y/itemHeight) + getVscrollBar().getPosition();
    if (index < model.getItemCount()) return index;
    return -1;
  }
  
  /**
   * Get the item bounds of the specified item index.
   */
  public RectGeom getItemBounds(int index)
  {
    double x = 0;
    double y = index*itemHeight;
    double w = getWidth()-getVscrollBar().getWidth();
    double h = itemHeight;
    return new RectGeom(x, y, w, h);
  }
      
  /**
   * Ensure the item index is scrolled so that it is visible.
   */
  public void ensureItemIsVisible(int index)
  { 
    // get visible count, if none visible that means we have zero 
    // height, so it is best to not go mucking with the scroll bar 
    // position; now if the parent if a dropdown, then it is 
    // highly likely height=0, so for that case assume the max
    // dropdown height of 308
    int visible = getVisibleRowCount();
    if (visible <= 0) 
    {
      if (getParent() instanceof BDropDown)
        visible = (int)(308.0 / itemHeight);
      else
        return;
    }
    
    // compute start, end, and total item count
    int start = getVscrollBar().getPosition();
    int end = start + visible;
    int itemCount = model.getItemCount();

    // make sure scroll bar has max set correctly, otherwise we 
    // could clip the setPosition we are getting ready to perform
    BScrollBar sb = getVscrollBar();      
    sb.setMax(Math.max(visible, itemCount));
    
    if (index < start)
    {                     
      sb.setPosition(index);
    }
    else if (index >= end)
    {
      int pos = index - visible + 1;                                                       
      if (pos >= itemCount) pos = itemCount-1;
      if (pos < 0) pos = 0;
      sb.setPosition(pos);
    }
  }

  /**
   * Implementation for the scroll action.
   */  
  public void doVscroll(BScrollEvent event)
  {
    repaint();
  }

////////////////////////////////////////////////////////////////
// Model Access
////////////////////////////////////////////////////////////////  

  /**
   * Convenience for <code>getModel().getItemCount()</code>.
   */
  public int getItemCount()
  {
    return model.getItemCount();
  }
  
  /**
   * Get the item at the specified index.
   */
  public Object getItem(int index)
  {
    return model.getItem(index);
  }

  /**
   * Convenience for <code>getModel().indexOfItem(value)</code>.
   */
  public int indexOfItem(Object value)
  {
    return model.indexOfItem(value);
  }

  /**
   * Convenience for <code>getModel().addItem(value)</code>.
   */
  public void addItem(Object value)
  {
    model.addItem(null, value);
  }

  /**
   * Convenience for <code>getModel().addItem(icon, value)</code>.
   */
  public void addItem(BImage icon, Object value)
  {
    model.addItem(icon, value);
  }

  /**
   * Convenience for <code>getModel().insertItem(index, value)</code>.
   */
  public void insertItem(int index, Object value)
  {
    model.insertItem(index, value);
  }
  
  /**
   * Convenience for <code>getModel().insertItem(index, icon, value)</code>.
   */
  public void insertItem(int index, BImage icon, Object value)
  {
    model.insertItem(index, icon, value);
  }

  /**
   * Convenience for <code>getModel().setItem(index, value)</code>.
   */
  public void setItem(int index, Object value)
  {
    model.setItem(index, value);
  }
  
  /**
   * Convenience for <code>getModel().setItem(index, icon, value)</code>.
   */
  public void setItem(int index, BImage icon, Object value)
  {
    model.setItem(index, icon, value);
  }
  
  /**
   * Convenience for <code>getModel().removeItem(value)</code>.
   */
  public void removeItem(Object value)
  {
    model.removeItem(value);
  }
  
  /**
   * Convenience for <code>getModel().removeItem(index)</code>.
   */
  public void removeItem(int index)
  {
    model.removeItem(index);
  }
  
  /**
   * Convenience for <code>getModel().removeAllItems()</code>.
   */
  public void removeAllItems()
  {
    model.removeAllItems();
  }
  
  /**
   * Remove all currently selected items from the list.
   */
  public void removeSelectedItems()
  {
    int[] selections = getSelection().getItems();
    getSelection().deselectAll();
    for (int i = selections.length - 1; i >= 0; i--)
      removeItem(selections[i]);
  }

////////////////////////////////////////////////////////////////
// Selection Access
////////////////////////////////////////////////////////////////

  /**
   * Is the specified index selected.
   */
  public boolean isSelected(int index)
  {
    return getSelection().isSelected(index);
  }

  /**
   * Is the specified item selected.
   */
  public boolean isSelected(Object item)
  {
    return getSelection().isSelected(indexOfItem(item));
  }

  /**
   * Get the currently selected item index 
   * or -1 if nothing selected.
   */
  public int getSelectedIndex()
  {
    return getSelection().getItem();
  }

  /**
   * Get the currently selected item indices, or
   * an empty array is nothing selected.
   */
  public int[] getSelectedIndices()
  {
    return getSelection().getItems();
  }
  
  /**
   * Get the currently selected item value or return
   * null if there is no selection.
   */
  public Object getSelectedItem()
  {
    int index = getSelectedIndex();
    if (index < 0 || index >= getModel().getItemCount()) return null;
    return getModel().getItem(index);
  }

  /**
   * Get the currently selected items, or an 
   * empty array is nothing is selected.
   */
  public Object[] getSelectedItems()
  {
    int[] indices = getSelectedIndices();
    Object[] result = new Object[indices.length];
    for(int i=0; i<result.length; ++i)
    result[i] = getModel().getItem(indices[i]);
    return result;
  }

  /**
   * Set the selected index.
   */
  public void setSelectedIndex(int index)
  {
    getSelection().deselectAll();
    getSelection().select(index);
  }
  
  /**
   * Select the specified item, or if null clear
   * the current selection.  Equality is checked
   * using the "==" operator, not equals().
   */
  public void setSelectedItem(Object value)
  {
    getSelection().deselectAll();
    int index = indexOfItem(value);
    if (index != -1)
      setSelectedIndex(index);
  }
  
////////////////////////////////////////////////////////////////
// Paint
//////////////////////////////////////////////////////////////// 
  /**
   * Paint the list.
   */
  public void paint(Graphics g)
  {   
    // cache some useful attributes
    IGeom origClip = g.getClip();
    BScrollBar scrollBar = getVscrollBar();
    int count = model.getItemCount();
    int visibleRows = getVisibleRowCount();
    double w = getWidth(), h = getHeight();
    double ih = itemHeight;
    double iw = w - scrollBar.getWidth();
    
    // clear my background
    Theme.dropDown().paintBackground(g, this);
    
    // paint cells
    int start = scrollBar.getPosition();
    int end = Math.min(start + visibleRows + 1, count);
    double y = 2; //shadow/highlight/outline
    for(int i=start; i<end; ++i)
    {
      g.push();
      try 
      { 
        paintItem(g, i, y, iw);
      }
      finally 
      { 
        g.pop(); 
      }
      y += ih;
    }

    // paint outline
    Theme.dropDown().paintBorder(g, this);

    // paint my children
    paintChildren(g);
  }
  
  /**
   * Paint the specified cell.
   */
  private void paintItem(Graphics g, int index, double y, double itemWidth)
  {
    BImage icon = null;
    Object value = "?";
    try
    {
      icon = model.getItemIcon(index);
      value = model.getItem(index);
    }
    catch(Exception e)
    {
      System.out.println("ERROR:  BList.paintItem: " + index);
      System.out.println("  " + e);
    }
    
    ListRenderer.Item item = this.item;
    item.icon    = icon;
    item.index   = index;
    item.value   = value;
    item.width   = itemWidth;
    item.height  = itemHeight;
    item.selected = selection.isSelected(index);
    
    double x = 1; //shadow/highlight/outline
    double wClip = Math.min(item.width, getWidth()-x-x);
    double hClip = Math.min(item.height, getHeight()-y-2);
    g.clip(x, y, wClip, hClip);
    g.translate(x, y);
    renderer.paintItem(g, item);
    g.translate(-x, -y);
  }
  
  //for now, let's have lists use their parent's styling instead of having
  //one style for all lists
  //scratch that, it's a giant pain. all lists share a style now
  public String getStyleSelector() { 
    //return this.getParentWidget().getNSSSelector();
    return "list";
  }

  
////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////  

  /**
   * BList will accept focus.
   */
  public boolean isFocusTraversable()
  {
    return true;
  }

  /**
   * Route to ListController.
   */
  public void focusGained(BFocusEvent event)
  {
    controller.focusGained(event);
  }

  /**
   * Route to ListController.
   */
  public void focusLost(BFocusEvent event)
  {
    controller.focusLost(event);
  }

////////////////////////////////////////////////////////////////
// Keyboard Input
////////////////////////////////////////////////////////////////

  /**
   * Route to ListController.
   */
  public void keyPressed(BKeyEvent event)
  {
    controller.keyPressed(event);
  }

  /**
   * Route to ListController.
   */
  public void keyReleased(BKeyEvent event)
  {
    controller.keyReleased(event);
  }

  /**
   * Route to ListController.
   */
  public void keyTyped(BKeyEvent event)
  {
    controller.keyTyped(event);
  }

///////////////////////////////////////////////////////////
// Mouse Input
///////////////////////////////////////////////////////////

  /**
   * Route to ListController.
   */
  public void mousePressed(BMouseEvent event)
  {
    controller.mousePressed(event);
  }

  /**
   * Route to ListController.
   */
  public void mouseReleased(BMouseEvent event)
  {
    controller.mouseReleased(event);
  }

  /**
   * Route to ListController.
   */  
  public void mouseEntered(BMouseEvent event)
  {
    controller.mouseEntered(event);
  }

  /**
   * Route to ListController.
   */  
  public void mouseExited(BMouseEvent event)
  {
    controller.mouseExited(event);
  }

  /**
   * Route to ListController.
   */
  public void mouseMoved(BMouseEvent event)
  {
    controller.mouseMoved(event);
  }

  /**
   * Route to ListController.
   */
  public void mouseDragged(BMouseEvent event)
  {
    controller.mouseDragged(event);
  }

  /**
   * Route to ListController.
   */  
  public void mousePulsed(BMouseEvent event)
  {
    controller.mousePulsed(event);
  }

  /**
   * Route to ListController.
   */  
  public void mouseWheel(BMouseWheelEvent event)
  {
    controller.mouseWheel(event);
  }

////////////////////////////////////////////////////////////////
// Transfer
////////////////////////////////////////////////////////////////

  /** Throw UnsupportedOperationException */
  public TransferEnvelope getTransferData()
    throws Exception 
  { 
    throw new UnsupportedOperationException();
  }

  /** Throw UnsupportedOperationException */
  public CommandArtifact insertTransferData(TransferContext cx)
    throws Exception
  { 
    throw new UnsupportedOperationException();
  }

  /** Throw UnsupportedOperationException */
  public CommandArtifact removeTransferData(TransferContext cx)
    throws Exception
  { 
    throw new UnsupportedOperationException();
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/list.png");
  
////////////////////////////////////////////////////////////////
// ListSupport
////////////////////////////////////////////////////////////////  

  /**
   * Abstract base class for support classes.
   */
  public static abstract class ListSupport
  {
    /**
     * Get the list the support instance is installed on.
     */
    public final BList getList()
    {
      return list;
    }

    public ListModel getModel() { return list.model; }
    public ListController getController() { return list.controller; }
    public ListRenderer getRenderer() { return list.renderer; }
    public ListSelection getSelection() { return list.selection; }
    public BWidgetShell getShell() { return list.getShell(); }
    
    BList list;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  BScrollBar scrollBar;
  ListModel model;
  ListController controller;
  ListRenderer renderer;
  ListSelection selection;
  double itemHeight;
  ListRenderer.Item item = new ListRenderer.Item();
  
}
