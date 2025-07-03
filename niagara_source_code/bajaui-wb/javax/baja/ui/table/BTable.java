/*                          
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.table;

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
 * BTable displays a grid of rows and columns.  
 *
 * <p>
 * Features included in BTable:
 * <ul>
 *   <li>Auto-resize column button</li>
 *   <li>Automatic support for column sorting</li>
 *   <li>Pluggable data model</li>
 *   <li>Pluggable user input processing</li>
 *   <li>Pluggable header rendering</li>
 *   <li>Pluggable cell rendering</li>
 *   <li>Automatic scrolling support</li>
 * </ul>
 *
 * <p>
 * BTable is composed of several pluggable support classes:
 * <ul>
 * <li><b>Model:</b> The table model stores the grid of
 *   data for the table.  The model is responsible for
 *   implementing methods the table uses to know the grid
 *   size, the Objects used to render the grid cells, and
 *   provide sort support.  There is required one to one 
 *   mapping between BTable and TableModel.
 * </li>
 * <li><b>Controller:</b> The TableController is responsible
 *   for processing all user input on the table in the form
 *   of focus, keyboard, and mouse events.  All input events
 *   are routed to the installed controller for processing.
 *   The default controller keeps track of what table element
 *   (header, cell, or resize hotspot) the mouse is over and
 *   provides convenient callbacks for subclasses.  The default
 *   controller also provides all the selection management.  
 *   There is required one to one mapping between BTable and 
 *   TableController.
 * </li>
 * <li><b>Selection:</b> Every table has a TableSelection
 *   instance which is used to store the current selection
 *   and provide access to modify the selection.  The default
 *   implementation provides row selection support and is
 *   designed to work in tandem with the default controller.
 *   There is required one to one mapping between BTable and 
 *   TableSelection.
 * </li>
 * <li><b>Cell Renderer:</b> The table's installed cell renderer
 *   is used to paint each cell.  The paintCell() callback
 *   contains all the cell information needed to visualize the
 *   cell.  The cell renderer is also used in determining the
 *   preferred column size.  There is required one to one mapping 
 *   between BTable and TableCellRenderer.
 * </li>
 * <li><b>Header Renderer:</b> The table's installed header renderer
 *   is used to paint each column header.  The paintHeader() callback
 *   contains all the header information needed to visualize the
 *   header.  The header renderer is also used in determining the
 *   preferred column size.  There is required one to one mapping 
 *   between BTable and TableHeaderRenderer.
 * </li>
 * </ul>
 *
 * @author    John Sublett
 * @author    Brian Frank (rewrite 3 Aug 01)
 * @creation  22 Nov 00
 * @version   $Revision: 124$ $Date: 6/28/11 8:45:13 AM EDT$
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
/*
 Indicates whether or not the header is visible.
 */
@NiagaraProperty(
  name = "headerVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 Indicates whether or not the options button is visible.
 */
@NiagaraProperty(
  name = "optionsButtonVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 Indicates whether or not the horizontal lines of the grid
 are visible.
 */
@NiagaraProperty(
  name = "hgridVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 Indicates whether or not the horizontal lines of the grid
 are visible.
 */
@NiagaraProperty(
  name = "vgridVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 Indicates whether or not the user can resize columns anywhere
 on the table, instead of just over the header.
 */
@NiagaraProperty(
  name = "extendedResize",
  type = "boolean",
  defaultValue = "false"
)
/*
 Indicates whether or not the horizontal scroll bar is visible.
 */
@NiagaraProperty(
  name = "hscrollBarVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 Indicates whether or not the vertical scroll bar is visible.
 */
@NiagaraProperty(
  name = "vscrollBarVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 Brush used to paint the grid lines if visible.  The theme
 default is used if set to BBrush.NULL.
 */
@NiagaraProperty(
  name = "gridBrush",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
@NiagaraProperty(
  name = "colorRows",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "evenRowColor",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
@NiagaraProperty(
  name = "oddRowColor",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
@NiagaraProperty(
  name = "hscrollBar",
  type = "BScrollBar",
  defaultValue = "new BScrollBar(BOrientation.horizontal)",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN
)
@NiagaraProperty(
  name = "vscrollBar",
  type = "BScrollBar",
  defaultValue = "new BScrollBar(BOrientation.vertical)",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN
)
@NiagaraProperty(
  name = "optionsButton",
  type = "BButton",
  defaultValue = "new BButton()",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraAction(
  name = "vscroll",
  parameterType = "BScrollEvent",
  defaultValue = "new BScrollEvent()"
)
@NiagaraAction(
  name = "hscroll",
  parameterType = "BScrollEvent",
  defaultValue = "new BScrollEvent()"
)
/*
 Event fired when the user presses the Enter key
 or selects a row with the mouse.
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
 Fired when the table contents are modified.
 */
@NiagaraTopic(
  name = "tableModified",
  eventType = "BWidgetEvent"
)
/*
 Fired when the table selection is modified.
 */
@NiagaraTopic(
  name = "selectionModified",
  eventType = "BWidgetEvent"
)
public class BTable
  extends javax.baja.ui.transfer.BTransferWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.table.BTable(3065794103)1.0$ @*/
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

  //region Property "headerVisible"

  /**
   * Slot for the {@code headerVisible} property.
   * Indicates whether or not the header is visible.
   * @see #getHeaderVisible
   * @see #setHeaderVisible
   */
  public static final Property headerVisible = newProperty(0, true, null);

  /**
   * Get the {@code headerVisible} property.
   * Indicates whether or not the header is visible.
   * @see #headerVisible
   */
  public boolean getHeaderVisible() { return getBoolean(headerVisible); }

  /**
   * Set the {@code headerVisible} property.
   * Indicates whether or not the header is visible.
   * @see #headerVisible
   */
  public void setHeaderVisible(boolean v) { setBoolean(headerVisible, v, null); }

  //endregion Property "headerVisible"

  //region Property "optionsButtonVisible"

  /**
   * Slot for the {@code optionsButtonVisible} property.
   * Indicates whether or not the options button is visible.
   * @see #getOptionsButtonVisible
   * @see #setOptionsButtonVisible
   */
  public static final Property optionsButtonVisible = newProperty(0, true, null);

  /**
   * Get the {@code optionsButtonVisible} property.
   * Indicates whether or not the options button is visible.
   * @see #optionsButtonVisible
   */
  public boolean getOptionsButtonVisible() { return getBoolean(optionsButtonVisible); }

  /**
   * Set the {@code optionsButtonVisible} property.
   * Indicates whether or not the options button is visible.
   * @see #optionsButtonVisible
   */
  public void setOptionsButtonVisible(boolean v) { setBoolean(optionsButtonVisible, v, null); }

  //endregion Property "optionsButtonVisible"

  //region Property "hgridVisible"

  /**
   * Slot for the {@code hgridVisible} property.
   * Indicates whether or not the horizontal lines of the grid
   * are visible.
   * @see #getHgridVisible
   * @see #setHgridVisible
   */
  public static final Property hgridVisible = newProperty(0, true, null);

  /**
   * Get the {@code hgridVisible} property.
   * Indicates whether or not the horizontal lines of the grid
   * are visible.
   * @see #hgridVisible
   */
  public boolean getHgridVisible() { return getBoolean(hgridVisible); }

  /**
   * Set the {@code hgridVisible} property.
   * Indicates whether or not the horizontal lines of the grid
   * are visible.
   * @see #hgridVisible
   */
  public void setHgridVisible(boolean v) { setBoolean(hgridVisible, v, null); }

  //endregion Property "hgridVisible"

  //region Property "vgridVisible"

  /**
   * Slot for the {@code vgridVisible} property.
   * Indicates whether or not the horizontal lines of the grid
   * are visible.
   * @see #getVgridVisible
   * @see #setVgridVisible
   */
  public static final Property vgridVisible = newProperty(0, true, null);

  /**
   * Get the {@code vgridVisible} property.
   * Indicates whether or not the horizontal lines of the grid
   * are visible.
   * @see #vgridVisible
   */
  public boolean getVgridVisible() { return getBoolean(vgridVisible); }

  /**
   * Set the {@code vgridVisible} property.
   * Indicates whether or not the horizontal lines of the grid
   * are visible.
   * @see #vgridVisible
   */
  public void setVgridVisible(boolean v) { setBoolean(vgridVisible, v, null); }

  //endregion Property "vgridVisible"

  //region Property "extendedResize"

  /**
   * Slot for the {@code extendedResize} property.
   * Indicates whether or not the user can resize columns anywhere
   * on the table, instead of just over the header.
   * @see #getExtendedResize
   * @see #setExtendedResize
   */
  public static final Property extendedResize = newProperty(0, false, null);

  /**
   * Get the {@code extendedResize} property.
   * Indicates whether or not the user can resize columns anywhere
   * on the table, instead of just over the header.
   * @see #extendedResize
   */
  public boolean getExtendedResize() { return getBoolean(extendedResize); }

  /**
   * Set the {@code extendedResize} property.
   * Indicates whether or not the user can resize columns anywhere
   * on the table, instead of just over the header.
   * @see #extendedResize
   */
  public void setExtendedResize(boolean v) { setBoolean(extendedResize, v, null); }

  //endregion Property "extendedResize"

  //region Property "hscrollBarVisible"

  /**
   * Slot for the {@code hscrollBarVisible} property.
   * Indicates whether or not the horizontal scroll bar is visible.
   * @see #getHscrollBarVisible
   * @see #setHscrollBarVisible
   */
  public static final Property hscrollBarVisible = newProperty(0, true, null);

  /**
   * Get the {@code hscrollBarVisible} property.
   * Indicates whether or not the horizontal scroll bar is visible.
   * @see #hscrollBarVisible
   */
  public boolean getHscrollBarVisible() { return getBoolean(hscrollBarVisible); }

  /**
   * Set the {@code hscrollBarVisible} property.
   * Indicates whether or not the horizontal scroll bar is visible.
   * @see #hscrollBarVisible
   */
  public void setHscrollBarVisible(boolean v) { setBoolean(hscrollBarVisible, v, null); }

  //endregion Property "hscrollBarVisible"

  //region Property "vscrollBarVisible"

  /**
   * Slot for the {@code vscrollBarVisible} property.
   * Indicates whether or not the vertical scroll bar is visible.
   * @see #getVscrollBarVisible
   * @see #setVscrollBarVisible
   */
  public static final Property vscrollBarVisible = newProperty(0, true, null);

  /**
   * Get the {@code vscrollBarVisible} property.
   * Indicates whether or not the vertical scroll bar is visible.
   * @see #vscrollBarVisible
   */
  public boolean getVscrollBarVisible() { return getBoolean(vscrollBarVisible); }

  /**
   * Set the {@code vscrollBarVisible} property.
   * Indicates whether or not the vertical scroll bar is visible.
   * @see #vscrollBarVisible
   */
  public void setVscrollBarVisible(boolean v) { setBoolean(vscrollBarVisible, v, null); }

  //endregion Property "vscrollBarVisible"

  //region Property "gridBrush"

  /**
   * Slot for the {@code gridBrush} property.
   * Brush used to paint the grid lines if visible.  The theme
   * default is used if set to BBrush.NULL.
   * @see #getGridBrush
   * @see #setGridBrush
   */
  public static final Property gridBrush = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code gridBrush} property.
   * Brush used to paint the grid lines if visible.  The theme
   * default is used if set to BBrush.NULL.
   * @see #gridBrush
   */
  public BBrush getGridBrush() { return (BBrush)get(gridBrush); }

  /**
   * Set the {@code gridBrush} property.
   * Brush used to paint the grid lines if visible.  The theme
   * default is used if set to BBrush.NULL.
   * @see #gridBrush
   */
  public void setGridBrush(BBrush v) { set(gridBrush, v, null); }

  //endregion Property "gridBrush"

  //region Property "colorRows"

  /**
   * Slot for the {@code colorRows} property.
   * @see #getColorRows
   * @see #setColorRows
   */
  public static final Property colorRows = newProperty(0, true, null);

  /**
   * Get the {@code colorRows} property.
   * @see #colorRows
   */
  public boolean getColorRows() { return getBoolean(colorRows); }

  /**
   * Set the {@code colorRows} property.
   * @see #colorRows
   */
  public void setColorRows(boolean v) { setBoolean(colorRows, v, null); }

  //endregion Property "colorRows"

  //region Property "evenRowColor"

  /**
   * Slot for the {@code evenRowColor} property.
   * @see #getEvenRowColor
   * @see #setEvenRowColor
   */
  public static final Property evenRowColor = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code evenRowColor} property.
   * @see #evenRowColor
   */
  public BBrush getEvenRowColor() { return (BBrush)get(evenRowColor); }

  /**
   * Set the {@code evenRowColor} property.
   * @see #evenRowColor
   */
  public void setEvenRowColor(BBrush v) { set(evenRowColor, v, null); }

  //endregion Property "evenRowColor"

  //region Property "oddRowColor"

  /**
   * Slot for the {@code oddRowColor} property.
   * @see #getOddRowColor
   * @see #setOddRowColor
   */
  public static final Property oddRowColor = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code oddRowColor} property.
   * @see #oddRowColor
   */
  public BBrush getOddRowColor() { return (BBrush)get(oddRowColor); }

  /**
   * Set the {@code oddRowColor} property.
   * @see #oddRowColor
   */
  public void setOddRowColor(BBrush v) { set(oddRowColor, v, null); }

  //endregion Property "oddRowColor"

  //region Property "hscrollBar"

  /**
   * Slot for the {@code hscrollBar} property.
   * @see #getHscrollBar
   * @see #setHscrollBar
   */
  public static final Property hscrollBar = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN, new BScrollBar(BOrientation.horizontal), null);

  /**
   * Get the {@code hscrollBar} property.
   * @see #hscrollBar
   */
  public BScrollBar getHscrollBar() { return (BScrollBar)get(hscrollBar); }

  /**
   * Set the {@code hscrollBar} property.
   * @see #hscrollBar
   */
  public void setHscrollBar(BScrollBar v) { set(hscrollBar, v, null); }

  //endregion Property "hscrollBar"

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

  //region Property "optionsButton"

  /**
   * Slot for the {@code optionsButton} property.
   * @see #getOptionsButton
   * @see #setOptionsButton
   */
  public static final Property optionsButton = newProperty(Flags.READONLY | Flags.TRANSIENT, new BButton(), null);

  /**
   * Get the {@code optionsButton} property.
   * @see #optionsButton
   */
  public BButton getOptionsButton() { return (BButton)get(optionsButton); }

  /**
   * Set the {@code optionsButton} property.
   * @see #optionsButton
   */
  public void setOptionsButton(BButton v) { set(optionsButton, v, null); }

  //endregion Property "optionsButton"

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

  //region Action "hscroll"

  /**
   * Slot for the {@code hscroll} action.
   * @see #hscroll(BScrollEvent parameter)
   */
  public static final Action hscroll = newAction(0, new BScrollEvent(), null);

  /**
   * Invoke the {@code hscroll} action.
   * @see #hscroll
   */
  public void hscroll(BScrollEvent parameter) { invoke(hscroll, parameter, null); }

  //endregion Action "hscroll"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * Event fired when the user presses the Enter key
   * or selects a row with the mouse.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * Event fired when the user presses the Enter key
   * or selects a row with the mouse.
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

  //region Topic "tableModified"

  /**
   * Slot for the {@code tableModified} topic.
   * Fired when the table contents are modified.
   * @see #fireTableModified
   */
  public static final Topic tableModified = newTopic(0, null);

  /**
   * Fire an event for the {@code tableModified} topic.
   * Fired when the table contents are modified.
   * @see #tableModified
   */
  public void fireTableModified(BWidgetEvent event) { fire(tableModified, event, null); }

  //endregion Topic "tableModified"

  //region Topic "selectionModified"

  /**
   * Slot for the {@code selectionModified} topic.
   * Fired when the table selection is modified.
   * @see #fireSelectionModified
   */
  public static final Topic selectionModified = newTopic(0, null);

  /**
   * Fire an event for the {@code selectionModified} topic.
   * Fired when the table selection is modified.
   * @see #selectionModified
   */
  public void fireSelectionModified(BWidgetEvent event) { fire(selectionModified, event, null); }

  //endregion Topic "selectionModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////  

  /**
   * Constructor with an empty DefaultTableModel.
   */
  public BTable()
  {
    this(new DefaultTableModel(), new TableController(), new TableSelection());
  }

  /**
   * Constructor with specified model.
   */
  public BTable(TableModel model)
  {
    this(model, new TableController(), new TableSelection(), new TableHeaderRenderer(), new TableCellRenderer());
  }

  /**
   * Constructor with model and controller.
   */
  public BTable(TableModel model, TableController controller)
  {
    this(model, controller, new TableSelection(), new TableHeaderRenderer(), new TableCellRenderer());
  }

  public BTable(TableModel model, TableController controller, TableSelection selection)
  {
    this(model, controller, selection, new TableHeaderRenderer(), new TableCellRenderer());
  }
  
  public BTable(TableModel model, TableController controller, TableSelection selection,
                TableHeaderRenderer headerRenderer, TableCellRenderer cellRenderer)
  {
    setModel(model);
    setController(controller);
    setSelection(selection);
    setHeaderRenderer(headerRenderer);
    setCellRenderer(cellRenderer);
    initUIComponents();
  }
  
  protected void initUIComponents()
  {
    vsb = getVscrollBar();
    hsb = getHscrollBar();
    optButton = getOptionsButton();
    optButton.setStyleId("tableOptionsButton");
    optButton.setFocusTraversable(false);
    optButton.setCommand(new OptionsCommand(this), false, true);
    
    linkTo(vsb, BScrollBar.positionChanged, BTable.vscroll);
    linkTo(hsb, BScrollBar.positionChanged, BTable.hscroll);
  }
  
////////////////////////////////////////////////////////////////
// TableSupport
////////////////////////////////////////////////////////////////  

  /**
   * Get the table model currently installed.
   */
  public TableModel getModel()
  {
    return model;
  }

  /**
   * Install the specified model this BTable visualizes.
   */
  public void setModel(TableModel model)
  {
    installSupport(this.model, model);
    this.model = model;
    fireTableModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
  }

  /**
   * Get the table controller currently installed.
   */
  public TableController getController()
  {
    return controller;
  }

  /**
   * Install the specified controller.
   */
  public void setController(TableController controller)
  {
    installSupport(this.controller, controller);
    this.controller = controller;
  }

  /**
   * Get the current table selection.
   */
  public TableSelection getSelection()
  {
    return selection;
  }

  /**
   * Install the specified selection model.
   */
  public void setSelection(TableSelection selection)
  {
    installSupport(this.selection, selection);
    this.selection = selection;
  }

  /**
   * Get the TableHeaderRenderer currently installed.
   */
  public TableHeaderRenderer getHeaderRenderer()
  {
    return headerRenderer;
  }

  /**
   * Install the specified TableHeaderRenderer.
   */
  public void setHeaderRenderer(TableHeaderRenderer renderer)
  {
    installSupport(this.headerRenderer, renderer);
    this.headerRenderer = renderer;
    this.headerHeight = getHeaderVisible() ? renderer.getHeaderHeight() : 1;
  }

  /**
   * Get the TableCellRenderer currently installed.
   */
  public TableCellRenderer getCellRenderer()
  {
    return cellRenderer;
  }

  /**
   * Install the specified TableCellRenderer.
   */
  public void setCellRenderer(TableCellRenderer renderer)
  {
    installSupport(this.cellRenderer, renderer);
    this.cellRenderer = renderer;
    this.cellHeight = renderer.getCellHeight();
  }
  
  /**
   * Check that the specified support is not null and 
   * not installed on another table.
   */
  private void installSupport(TableSupport old, TableSupport support)
  {
    if (support == null) throw new NullPointerException();
    if (old == support) return;
    if (support.table != null) throw new IllegalArgumentException("Already installed on another table");
    if (old != null) old.setTable(null);
    support.setTable(this);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Relayout and repaint table when properties change.
   */
  public void changed(Property prop, Context context)
  {
    relayout();
  }

  /**
   * Compute the preferred size of the table.
   */
  public void computePreferredSize()
  {  
    // our running totals
    int w = 0, h = 0;
    
    // compute ideal size of columns
    int colCount = model.getColumnCount();
    if (needsColumnLayout || colCount != columnPositions.length) 
      layoutColumns();

    // add up width of all the columns
    w += preferredWidth;
      
    // add up height
    if (getHeaderVisible()) h += headerRenderer.getHeaderHeight();
    h += model.getRowCount() * cellHeight;
    
    // all computed!
    w = Math.max(w, 10);
    h = Math.max(h, 10);
    setPreferredSize(w, h);
  }

  /**
   * Layout the table.
   */
  public void doLayout(BWidget[] kids)
  {
    // cache some useful attributes
    double w = getWidth();
    double h = getHeight();
    int colCount = model.getColumnCount();
    double scrollWidth = Theme.scrollBar().getFixedWidth();
    this.headerHeight = getHeaderVisible() ? headerRenderer.getHeaderHeight() : 1;
    
    layoutVertScrollBar();
    
    // layout the resize button
    if (getOptionsButtonVisible() && getHeaderVisible())
    {
      optButton.setBounds(w-scrollWidth, 0, scrollWidth, headerHeight);
    }
    else
      optButton.setVisible(false);
    
    // if the column widths need to be relaid out, then go at it
    if (needsColumnLayout || colCount != columnPositions.length) 
      layoutColumns();    

    layoutHorizScrollBar();
  }
  
  /**
   * Layout the vertical scrollbar.
   */
  private void layoutVertScrollBar()
  {
    // cache some useful attributes
    double w = getWidth();
    double h = getHeight();
    int rowCount = model.getRowCount();
    int colCount = model.getColumnCount();
    double scrollWidth = Theme.scrollBar().getFixedWidth();
    int visibleRows = getVisibleRowCount();
    double dh = hsb.isVisible() ? scrollWidth-1 : 0;

    // layout the vertical scroll bar
    if ((visibleRows < rowCount) && getVscrollBarVisible())
    {
      vsb.setVisible(true);
      vsb.setMin(0);
      vsb.setMax(rowCount);
      vsb.setExtent(visibleRows);
      vsb.setBlockIncrement(Math.max(3, visibleRows-1));      
      double y = (getHeaderVisible()) ? headerHeight-1 : headerHeight;
      vsb.setBounds(w-scrollWidth, y, scrollWidth, h-y-dh);
    }
    else
    {
      vsb.setVisible(false);
      vsb.setBounds(0,0,0,0);
      vsb.setPosition(0);
      vsb.setMin(0);
      vsb.setMax(visibleRows);
      vsb.setExtent(visibleRows);
    }
  }

  /**
   * Layout the horizontal scrollbar.
   */
  private void layoutHorizScrollBar()
  {
    double w = getWidth();
    double h = getHeight();
    int rowCount = model.getRowCount();
    double scrollWidth = Theme.scrollBar().getFixedWidth();
    double total = 0;
    for (int i=0; i<columnWidths.length; i++) 
      total += columnWidths[i];
    
    boolean v = hsb.isVisible();
    if ((total > w) && getHscrollBarVisible())
    {
      hsb.setVisible(true);
      hsb.setMin(0);
      hsb.setMax((int)total);
      hsb.setExtent((int)(w-scrollWidth));
      double dw = (getVisibleRowCount() < rowCount) ? scrollWidth-1 : 0;
      hsb.setBounds(0,h-scrollWidth,w-dw,scrollWidth);
      if (!v) layoutVertScrollBar();
    }
    else 
    {
      hsb.setVisible(false);
      if (v) layoutVertScrollBar();
    }
  }
  
  /**
   * Layout the columns as best as possible.  This
   * fills in the columnPositions and preferredWidth
   * attribute with the preferred max width for every 
   * column based on header and all the rows.
   */
  private void layoutColumns()
  {
    // cache from frequently used attributes
    TableHeaderRenderer headerRenderer = this.headerRenderer;
    TableCellRenderer cellRenderer = this.cellRenderer;
    TableModel model = this.model;
    int rowCount = model.getRowCount();
    int colCount = model.getColumnCount();
    double[] widths = new double[colCount];

    // compute header columns
    if (getHeaderVisible())
      for(int c=0; c<colCount; ++c)
      {
        String name = model.getColumnName(c);
        if (name == null) name = "";
        header.column = c;
        header.name = name;
        widths[c] = headerRenderer.getPreferredHeaderWidth(header);
      }

    // column columns from all the rows
    for(int r=0; r<rowCount; ++r)
      for(int c=0; c<colCount; ++c)
      {
        cell.row = r;
        cell.column = c;
        try
        {
          cell.value = model.getValueAt(r, c);
          double colWidth = cellRenderer.getPreferredCellWidth(cell);
          widths[c] = Math.max(widths[c], colWidth);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    // compute total and column x positions
    double total = 0; if (colCount > 0) total = widths[0];
    double[] positions = new double[colCount];
    for(int c=1; c<colCount; ++c)
    {
      total += widths[c];
      positions[c] = positions[c-1] + widths[c-1];          
    }
    
    // update attributes
    this.columnPositions = positions;
    this.preferredWidth = total;
    this.needsColumnLayout = false;
    this.columnWidths = widths;
  }

  /**
   * Resize all columns to automatically fit the longest row.
   */
  public void sizeColumnsToFit()
  {
    needsColumnLayout = true;
    relayout();
  }

////////////////////////////////////////////////////////////////
// Positioning
////////////////////////////////////////////////////////////////

  /**
   * Translate this screen x coordinate relative to
   * the table's origin.
   */
  double translateXToTable(double x)
  {
    if (hsb.isVisible()) return x + hsb.getPosition();
    else return x;
  }

  /**
   * Given a x coordinate in the BTable's coordinate 
   * space, return the column index.  If x falls outside
   * the BTable's bounds then return -1.
   */
  public int getColumnAt(double x)
  {
    //if (x < 0 || x > getWidth()-vsb.getWidth())
    //  return -1;
    
    x = translateXToTable(x);
    double[] pos = columnPositions;
    for(int c=1; c<pos.length; ++c)
      if (x < pos[c]) return c-1;
    
    return pos.length-1;
  }
  
  /**
   * Given a y coordinate in the BTable's coordinate space, 
   * return the row index.  If the y coordinate falls within 
   * the header row return Integer.MAX_VALUE, or it is falls 
   * outside of a valid row then return -1.
   */
  public int getRowAt(double y)
  {
    if (y < 0 || y > getHeight()) return -1;
    if (y < headerHeight) return Integer.MAX_VALUE;
    int row = (int)((y - headerHeight)/cellHeight) + vsb.getPosition();
    if (row < model.getRowCount()) return row;
    return -1;
  }

  /**
   * Get the width of the specified header column.
   */
  public double getHeaderWidth(int col)
  {
    double[] w = this.columnWidths;
    double[] pos = this.columnPositions;    
    boolean gap = getOptionsButtonVisible();
    double vsw = Theme.scrollBar().getFixedWidth();
    return (col < pos.length-1) ?
      pos[col+1] - pos[col] :
      hsb.isVisible() ?
        hsb.getMax() - pos[col] + (gap ? 0 : vsw-1) :
        getWidth() - (gap ? vsw : 1) - pos[col];
  }

  /**
   * Get the width of the specified column cell.
   */
  public double getCellWidth(int col)
  {
    double[] w = this.columnWidths;
    double[] pos = this.columnPositions;
    boolean gap = vsb.isVisible();
    double vsw = Theme.scrollBar().getFixedWidth();
    return (col < pos.length-1) ?
      pos[col+1] - pos[col] :
      hsb.isVisible() ?
        hsb.getMax() - pos[col] + (gap ? 0 : vsw) :
        getWidth() - (gap ? vsw : 0) - pos[col];              
  }
  
  /**
   * Get the header bounds of the specified column.
   */
  public RectGeom getHeaderBounds(int col)
  {
    double x = columnPositions[col];
    double w = getHeaderWidth(col);
    return new RectGeom(x, 0, w, headerHeight);
  }

  /**
   * Get the cell bounds of the specified row and column.
   */
  public RectGeom getCellBounds(int row, int col)
  {
    double x = columnPositions[col];
    double y = headerHeight + row*cellHeight;
    double w = getCellWidth(col);
    return new RectGeom(x, y, w, cellHeight);
  }
    
  /**
   * Set the specified column's position to the given
   * x coordinate.  If the x coordinate would obscure
   * the column to the left then it is clipped to a
   * minimum.  All the columns to the right are shifted
   * accordingly.
   */
  public void setColumnPosition(int col, double x)
  {
    double[] pos = columnPositions;
    double[] width = columnWidths;
    double min = 0; if (col > 0) min = pos[col-1]+6;
    //int max = getWidth() - optButton.getWidth() - 2;
    
    if (hsb.isVisible()) x += hsb.getPosition();
    if (x < min) x = min;
    //if (x > max) x = max;
    double delta = x - pos[col];

    if (delta != 0)
    {
      for(int c=col; c<pos.length; ++c) 
      {
        pos[c] += delta;
        //width[c-1] += delta;
      }
      width[col] += delta;
      layoutHorizScrollBar();
      repaint();
    }
  }

  /**
   * Get the number of visible rows for the table.
   */
  public int getVisibleRowCount()
  {
    double x = getHeight() - headerHeight;
    if (hsb.isVisible()) x -= Theme.scrollBar().getFixedWidth();
    return (int)(x / cellHeight);
  }
  
  /**
   * Ensure the table is scrolled so that the 
   * specified row is visible.
   */
  public void ensureRowIsVisible(int row)
  {
    int visible = getVisibleRowCount();
    int start = vsb.getPosition();
    int end = start + visible;
    int rowCount = model.getRowCount();

    // make sure scroll bar has max set correctly, otherwise we 
    // could clip the setPosition we are getting ready to perform
    vsb.setMax(Math.max(visible, rowCount));
    
    if (row < start)
    {
      vsb.setPosition(row);
    }
    else if (row >= end)
    {
      int pos = row - visible + 1;
      if (pos >= rowCount) pos = rowCount-1;
      if (pos < 0) pos = 0;
      vsb.setPosition(pos);
    }
  }

  /**
   * Implementation for the verticalScroll action.
   */  
  public void doVscroll(BScrollEvent event)
  {
    repaint();
  }

  /**
   * Implementation for the horizontalScroll action.
   */
  public void doHscroll(BScrollEvent event)
  {
    repaint();
  }

////////////////////////////////////////////////////////////////
// Sorting
////////////////////////////////////////////////////////////////
  
  /**
   * Get the column being used for the current sort,
   * or -1 if no sorting is currently active.
   */
  public int getSortColumn()
  {
    return sortColumn;
  }
  
  /**
   * Return true if the current sort is ascending 
   * or false if it is descending.
   */
  public boolean isSortAscending()
  {
    return sortAscending;
  }
  
  /**
   * Setup the table's column sort on the 
   * specified column index.
   */
  public void sortByColumn(int col, boolean ascending)
  {
    this.sortColumn = col;
    this.sortAscending = ascending;
    if (col >= 0) getModel().sortByColumn(col, ascending);
    repaint();
  }

////////////////////////////////////////////////////////////////
// Paint
//////////////////////////////////////////////////////////////// 

  /**
   * Paint the table.
   */
  public void paint(Graphics g)
  {   
    // clear my background
    paintBackground(g);

    if (hsb.isVisible()) g.translate(-hsb.getPosition(), 0);    

    // cache some useful attributes
    IGeom origClip = g.getClip();
    int rowCount = model.getRowCount();
    int visibleRows = getVisibleRowCount();
    double hh = headerHeight;
    double ch = cellHeight;
    double w = getWidth(), h = getHeight();
    double[] pos = columnPositions;
    int colCount = pos.length;

    // paint header
    if (getHeaderVisible())
    {
      for(int c=0; c<colCount; ++c) 
      {
        g.push();
        try
        {
          paintHeader(g, c);
        }
        finally
        {
          g.pop();
        }
      }
    }
    
    // paint cells
    int start = vsb.getPosition();
    int end = Math.min(start + visibleRows+1, rowCount);
    double y = headerHeight;
    for(int r=start; r<end; ++r)
    {
      for(int c=0; c<colCount; ++c) 
      {
        g.push();
        try
        {
          paintCell(g, r, c, y);
        }
        finally
        {
          g.pop();
        }
      }
      y += cellHeight;
    }
    
    // paint grid
    double tx = (hsb.isVisible()) ? hsb.getPosition() : 0;
    BBrush grid = getGridBrush();
    if (!grid.isNull()) g.setBrush(grid);    
    else g.setBrush(Theme.table().getGridBrush());

    //paint header underline
    g.strokeLine(tx, hh, tx + w, hh);

    if (paintHorizontalGrid())
      for(int r=1; r<=end-start; ++r)
        g.strokeLine(tx+1, r*ch+hh-1, tx+w-2, r*ch+hh-1);
    if (paintVerticalGrid())
      for(int c=1; c<colCount; ++c)
        g.strokeLine(pos[c], hh, pos[c], y-1);
      
    if (hsb.isVisible()) g.translate(hsb.getPosition(), 0);

    // paint table border
    g.setBrush(Theme.table().getControlForeground(this));
    g.strokeRect(0, 0, w-1, h-1);

    // paint my children
    paintChildren(g);

    // Paint corner b/w scrollbars if necessary
    if (hsb.isVisible() && vsb.isVisible())
    {
      g.setBrush(Theme.widget().getControlBackground());
      g.fillRect(
        vsb.getX()+1, 
        hsb.getY()+1, 
        vsb.getWidth()-2, 
        Theme.scrollBar().getFixedWidth()-2);
    }
  }

  protected void paintBackground(Graphics g) {
    Theme.table().paintBackground(g, this);
  }
  /**
   * Paint column header cell.
   */
  private void paintHeader(Graphics g, int c)
  {
    TableHeaderRenderer.Header header = this.header;
    double x = columnPositions[c];
    double y = 0;
    
    header.column = c;
    header.name   = model.getColumnName(c);
    header.height = headerHeight;
    header.width  = getHeaderWidth(c);
                   
    double wClip = Math.min(header.width, translateXToTable(getWidth())-x);
    double hClip = Math.min(header.height, getHeight());
    if (wClip < 0) wClip = 0;
    g.clip(x, 0, wClip, hClip);
    g.translate(x, 0);
    headerRenderer.paintHeader(g, header);    
  }

  /**
   * Paint the specified cell.
   */
  private void paintCell(Graphics g, int r, int c, double y)
  {
    TableCellRenderer.Cell cell = this.cell;
    double x = columnPositions[c]+1;
    Object value = "?";
    try
    {
      value = model.getValueAt(r, c);
    }
    catch(Exception e)
    {
      System.out.println("ERROR:  BTable.paintCell: " + r + "," + c);
      System.out.println("  " + e);
    }
    
    cell.row      = r;
    cell.column   = c;
    cell.value    = value;
    cell.width    = getCellWidth(c);
    cell.height   = cellHeight;
    cell.selected = selection.isSelected(r, c);
    
    double wClip = translateXToTable(getWidth())-x;
    double hClip = getHeight()-y;
    if (wClip < 0) wClip = 0;
    
    g.clip(x, y, wClip, hClip);
    g.translate(x, y);
    g.clip(cellRenderer.getClip(cell));
    cellRenderer.paintCell(g, cell);    
  }
  
  /**
   * Return if the horizontal grid should be painted.  The default 
   * returns the state of the horizontalGridVisible property.  This
   * a hook allows subclasses to perform their own grid painting.
   */
  protected boolean paintHorizontalGrid()
  {
    return getHgridVisible();
  }

  /**
   * Return if the vertical grid should be painted.  The default 
   * returns the state of the verticalGridVisible property.  This
   * a hook allows subclasses to perform their own grid painting.
   */
  protected boolean paintVerticalGrid()
  {
    return getVgridVisible();
  }
  
  public String getStyleSelector() { return "table"; }
    
////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////  

  /**
   * Route to TableController.
   */
  public boolean isFocusTraversable()
  {
    return controller.isFocusTraversable();
  }

  /**
   * Route to TableController.
   */
  public void focusGained(BFocusEvent event)
  {
    controller.focusGained(event);
  }

  /**
   * Route to TableController.
   */
  public void focusLost(BFocusEvent event)
  {
    controller.focusLost(event);
  }

////////////////////////////////////////////////////////////////
// Keyboard Input
////////////////////////////////////////////////////////////////

  /**
   * Route to TableController.
   */
  public void keyPressed(BKeyEvent event)
  {
    controller.keyPressed(event);
  }

  /**
   * Route to TableController.
   */
  public void keyReleased(BKeyEvent event)
  {
    controller.keyReleased(event);
  }

  /**
   * Route to TableController.
   */
  public void keyTyped(BKeyEvent event)
  {
    controller.keyTyped(event);
  }

///////////////////////////////////////////////////////////
// Mouse Input
///////////////////////////////////////////////////////////

  /**
   * Route to TableController.
   */
  public void mousePressed(BMouseEvent event)
  {
    controller.mousePressed(event);
  }

  /**
   * Route to TableController.
   */
  public void mouseReleased(BMouseEvent event)
  {
    controller.mouseReleased(event);
  }

  /**
   * Route to TableController.
   */  
  public void mouseEntered(BMouseEvent event)
  {
    controller.mouseEntered(event);
  }

  /**
   * Route to TableController.
   */  
  public void mouseExited(BMouseEvent event)
  {
    controller.mouseExited(event);
  }

  /**
   * Route to TableController.
   */
  public void mouseMoved(BMouseEvent event)
  {
    resetHover();
    controller.mouseMoved(event);
  }

  /**
   * Route to TableController.
   */
  public void mouseDragged(BMouseEvent event)
  {
    controller.mouseDragged(event);
  }

  /**
   * Route to TableController.
   */  
  public void mousePulsed(BMouseEvent event)
  {
    controller.mousePulsed(event);
  }

  /**
   * Route to TableController.
   */  
  public void mouseWheel(BMouseWheelEvent event)
  {
    controller.mouseWheel(event);
  }

  /**
   * Route to TableController.
   */  
  public void mouseHover(BMouseEvent event)
  {
    controller.mouseHover(event);
  }

///////////////////////////////////////////////////////////
// Options
///////////////////////////////////////////////////////////

  class OptionsCommand extends Command
  {     
    OptionsCommand(BTable owner)
    {
      super(owner, "Options");
      this.icon = BImage.make("module://bajaui/com/tridium/ui/images/tableColumns.png");
    }      
    
    public CommandArtifact doInvoke()
    {             
      getController().doOptions();
      return null;
    }
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
  private static final BIcon icon = BIcon.std("widgets/table.png");
  
////////////////////////////////////////////////////////////////
// TableSupport
////////////////////////////////////////////////////////////////  

  /**
   * Abstract base class for support classes.
   */
  public static abstract class TableSupport
  {
    /**
     * Get the table the support instance is installed on.
     */
    public final BTable getTable()
    {
      return table;
    }

    public TableModel getModel() { return table.model; }
    public TableController getController() { return table.controller; }
    public TableHeaderRenderer getHeaderRenderer() { return table.headerRenderer; }
    public TableCellRenderer getCellRenderer() { return table.cellRenderer; }
    public TableSelection getSelection() { return table.selection; }
    public BWidgetShell getShell() { return table.getShell(); }
    public void setTable(BTable table) { this.table = table; }
    
    BTable table;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private BScrollBar vsb;
  private BScrollBar hsb;
  private BButton optButton;

  private TableModel model;
  private TableController controller;
  private TableHeaderRenderer headerRenderer;
  private TableCellRenderer cellRenderer;
  private TableSelection selection;
  private double[] columnPositions = new double[0];
  private double[] columnWidths = new double[0];
  private double preferredWidth;
  private double cellHeight;
  private double headerHeight;
  private boolean needsColumnLayout = true;
  private TableHeaderRenderer.Header header = new TableHeaderRenderer.Header();
  private TableCellRenderer.Cell cell = new TableCellRenderer.Cell();
  private int sortColumn = -1;
  private boolean sortAscending = true;  
}
