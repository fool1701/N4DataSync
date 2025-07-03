/*
 * Copyright 2009, Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BScrollBarPolicy;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.theme.Theme;

/**
 * Field Editor that has rows of Widgets that can be added/removed
 *
 * @author    gjohnson
 * @creation  12 Aug 2009
 * @version   1
 * @since     Niagara 3.5
 */
@NiagaraType
/*
 Called when a new Row is to be added
 */
@NiagaraAction(
  name = "addRow",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
/*
 Called when a new row of Widgets to be removed
 */
@NiagaraAction(
  name = "removeRow",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
public abstract class BMultiRowFE
    extends BWbFieldEditor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BMultiRowFE(576025145)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "addRow"

  /**
   * Slot for the {@code addRow} action.
   * Called when a new Row is to be added
   * @see #addRow(BWidgetEvent parameter)
   */
  public static final Action addRow = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code addRow} action.
   * Called when a new Row is to be added
   * @see #addRow
   */
  public void addRow(BWidgetEvent parameter) { invoke(addRow, parameter, null); }

  //endregion Action "addRow"

  //region Action "removeRow"

  /**
   * Slot for the {@code removeRow} action.
   * Called when a new row of Widgets to be removed
   * @see #removeRow(BWidgetEvent parameter)
   */
  public static final Action removeRow = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code removeRow} action.
   * Called when a new row of Widgets to be removed
   * @see #removeRow
   */
  public void removeRow(BWidgetEvent parameter) { invoke(removeRow, parameter, null); }

  //endregion Action "removeRow"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMultiRowFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
   
////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  public BMultiRowFE()
  {            
    gridPane.setColumnCount(2);

    // Holds add button and rows
    BGridPane basePane = new BGridPane(2);        
    basePane.setColumnAlign(BHalign.left);
    basePane.setRowAlign(BValign.top);

    addCompButton.setButtonStyle(BButtonStyle.toolBar);
    linkTo(addCompButton, BButton.actionPerformed, addRow);

    BBorderPane addButtonPane = new BBorderPane(addCompButton, 0, 5, 0, 0);
    basePane.add(null, addButtonPane);
    basePane.add(null, gridPane);

    // Hold everything in a constrained scroll pane
    BConstrainedPane constPane = new BConstrainedPane();
    constPane.setMaxHeight(300);

    BScrollPane scrollPane = new BScrollPane(new BBorderPane(basePane, 3, Theme.scrollBar().getFixedWidth() + 3, 3, 3));
    scrollPane.setHpolicy(BScrollBarPolicy.never);
    scrollPane.setViewportBackground(Theme.widget().getControlBackground());
    scrollPane.setBorderPolicy(BScrollBarPolicy.asNeeded);

    constPane.setContent(scrollPane);  
    setContent(constPane);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    addCompButton.setEnabled(!readonly);
  } 

  protected void doLoadValue(BObject val, Context cx) throws Exception
  {    
    // Remove everything from the grid pane to start afresh
    gridPane.removeAll();

    Object[] values = makeRowValues(val, cx);

    // At least add one row if none are available
    if (values.length == 0)
      addRowWidget(null);
    else
    {
      for (int i = 0; i < values.length; ++i)
        addRowWidget(values[i]);
    }

    relayout();
  }
  
  protected BObject doSaveValue(BObject val, Context cx) throws CannotSaveException, Exception
  {
    Property[] props = gridPane.getDynamicPropertiesArray();

    Array<BWidget> rowArray = new Array<>(BWidget.class);

    // Search for Row Widgets through remove button and save their values away
    for (int i = 0; i < props.length; ++i)
    {
      if (props[i].getName().indexOf("remove") > -1)
      {
        // BFacets pickle of remove button parent Widget contains references to other field editors
        BWidget row = (BWidget)gridPane.getSlotFacets(props[i]).getPickle();
        rowArray.add(row);
      }
    }

    return makeSaveValue(rowArray.trim(), val, cx);
  }
  
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  public void started() throws Exception
  {
    if (getCurrentValue() != null)
      doLoadValue(getCurrentValue(), getCurrentContext());
  }
  
////////////////////////////////////////////////////////////////
// Action handlers
////////////////////////////////////////////////////////////////

  /**
   * Add button Action handler
   * 
   * @param event the Widget Event for the Action Handler
   */
  public void doAddRow(BWidgetEvent event)
  {
    addRowWidget(null);

    relayout();
    setModified();

    // Hack to make Manager view dialog resize itself automatically
    BDialog d = findDialog(this);
    if (d != null)
    {
      d.relayout();
      d.setScreenSizeToPreferredSize();
    }
  }

  private BDialog findDialog(BComplex w)
  {
    if (w == null)
      return null;
    else if (w instanceof BDialog)
      return (BDialog)w;
    else
      return findDialog(w.getParent());
  }

  /**
   * Remove button Action handler
   * 
   * @param event the Widget event
   * @param cx the Context for the Action call
   */
  public void doRemoveRow(BWidgetEvent event, Context cx)
  {    
    BWidget w1 = (BWidget)event.getWidget().getParent();
    Property p = w1.getPropertyInParent();

    BWidget row = (BWidget)w1.getSlotFacets(p).getPickle();

    // Remove remove button
    gridPane.remove(event.getWidget().getParent());

    // Remove component editor
    gridPane.remove(row);

    // Add a blank row if the grid pane has no children
    if (gridPane.getChildren(BWidget.class).length == 0)
      addRowWidget(null);

    relayout();
    setModified();
  }
  
////////////////////////////////////////////////////////////////
// Methods to Implement
////////////////////////////////////////////////////////////////

  /**
   * Return the row values that represent each row from the value passed in from doLoadValue()
   * 
   * @param val the value passed in from doLoadValue
   * @param cx the current Field Editor Context
   * @return the values that represents each row
   */
  protected abstract Object[] makeRowValues(BObject val, Context cx) throws Exception;
  
  /**
   * Return the value to be saved based upon the row Widgets
   * 
   * @param rowWidgets the Widgets for each row
   * @param val the value passed in originally from doSaveValue
   * @param cx the current Field Editor Context
   * @return the object to be returned from doSaveValue and saved
   */
  protected abstract BObject makeSaveValue(BWidget[] rowWidgets, BObject val, Context cx) throws CannotSaveException, Exception;
  
  /**
   * Add a row Widget to the parent Widget based upon the row value.
   * Then return the newly created Widget. Any method implementation must follow
   * these guidelines... 
   * <ul>
   *   <li>Even if the rowVal is null, a row Widget must be added</li>
   *   <li>The new row Widget must be added to the parentWidget</li>
   *   <li>The new row Widget must be returned</li>
   *   <li>Note that this call is made after the readonly state has been determined
   *       (ie. the doSetReadonly() callback has already occurred).  Thus you 
   *       should check for this explicitly (using isReadonly()) and set the new
   *       Widget's readonly state appropriately (using setReadonly())</li>
   * </ul>
   * @param parentWidget the parent Widget of the new row Widget.
   * @param rowVal the row value that determines the Widget to be added (could be null)
   * @param cx the current Field Editor Context
   * @return the newly created row Widget that has been added to the parent Widget
   */
  protected abstract BWidget addRowWidget(BWidget parentWidget, Object rowVal, Context cx);
    
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////  
  
  /**
   * Add a row Widget for the given row value
   * 
   * @param val the row value (can be null)
   */
  private void addRowWidget(Object val)
  {
    BWidget row = addRowWidget(gridPane, val, getCurrentContext());

    BButton removeButton = new BButton(removeImg);
    removeButton.setButtonStyle(BButtonStyle.toolBar);
    linkTo(removeButton, BButton.actionPerformed, removeRow);

    // Add remove button Widgets to grid pane. Set reference to pickle so other widgets on row can be accessed easily
    gridPane.add("remove?", new BBorderPane(removeButton, 0, 0, 0, 5), 0, BFacets.makePickle(BFacets.DEFAULT, row), null);

    removeButton.setEnabled(!isReadonly());
  }
  
  /**
   * Find the Widget widget with the given name
   * 
   * @param wid the top level Widget to search from
   * @param name the name of the Widget to search for
   * @return the found Widget (could be null)
   */
  protected static final BWidget findWidget(BWidget wid, String name)
  {
    if (wid == null)
      return null;
    
    String wName = wid.getName();
    
    if (wName == null)
      return null;
    
    if (wName.equals(name))
      return wid;
    
    BWidget[] kids = wid.getChildWidgets();
    
    for (int i = 0; i < kids.length; ++i)
    {
      BWidget w = findWidget(kids[i], name);
      if (w != null)
        return w;
    }
    
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BButton addCompButton = new BButton(addImg);
  private BGridPane gridPane = new BGridPane();

  private static final BImage addImg    = BImage.make(BIcon.std("add.png"));
  private static final BImage removeImg = BImage.make(BIcon.std("close.png")); 
}
