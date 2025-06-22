/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.wiresheet;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BMenu;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.util.BTitlePane;
import javax.baja.workbench.view.BWbComponentView;

import com.tridium.wiresheet.BWireSheetPane;

/**
 * BWireSheet.
 *
 * @author    Brian Frank on 10 Jan 04
 * @version   $Revision: 10$ $Date: 8/25/10 12:04:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Component",
    requiredPermissions = "W"
  )
)
public class BWireSheet
  extends BWbComponentView
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.wiresheet.BWireSheet(4133010682)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWireSheet.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor.
   */
  public BWireSheet()
  {
    autoRegisterForComponentEvents = false;
    
    // We need to store a local copy of this in case
    // a subclass modifies the content widget.
    BWireSheetPane pane = new BWireSheetPane();
    setContent(new BTitlePane("Wire Sheet", pane));
    setTransferWidget(pane.getCanvas());
  }

  public BWireSheetPane getWireSheetPane()
  {
    BWidget content = getContent();
  
    if (content instanceof BTitlePane)
    {
      return (BWireSheetPane) ((BTitlePane) content).getContent();
    }
  
    // This should never happen.
    throw new IllegalStateException("Cannot find wiresheet pane!");
  }
////////////////////////////////////////////////////////////////
// Menu/Toolbar Merging
////////////////////////////////////////////////////////////////

  public BMenu[] getViewMenus()
  {
    return getWireSheetPane().commands.getViewMenus();
  }
  
  public BToolBar getViewToolBar()
  {
    return getWireSheetPane().commands.getViewToolBar();
  }              

  
////////////////////////////////////////////////////////////////
// Popups
////////////////////////////////////////////////////////////////
  
  /**
   * Make a popup menu for when the mouse is clicked on 
   * the background.
   */
  public BMenu makeBackgroundPopup(BMenu defaultMenu)
  {                                                 
    return defaultMenu;
  }

  /**
   * Make a popup menu for when the mouse is clicked on 
   * the specified component.
   */
  public BMenu makeComponentPopup(BMenu defaultMenu, BComponent component)
  {                                                 
    return defaultMenu;
  }
  
////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////
  
  /**
   * Get the currently selected components.
   */
  public BComponent[] getSelectedComponents()
  {
    return getWireSheetPane().selection.getComponents();
  }
  
  /**
   * Set the selected components.
   */
  public void setSelectedComponents(BComponent[] components)
  {
    getWireSheetPane().selection.setComponents(components);
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  public void deactivated() 
  {
    getWireSheetPane().deactivated();
  }

  public void doLoadValue(BObject value, Context cx)
  {
    getWireSheetPane().doLoadValue(value, cx);
  }
    
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////  

  public void handleComponentEvent(BComponentEvent event)
  {
    // Issue 16744: Wiresheet Component Events not handled in a thread safe manner
    // The simplest solution to make the Wiresheet more thread safe is just to forward these events
    // to the event dispatch thread. Therefore, everything for the wiresheet is handled in the same thread.
    // Furthermore, since this is the Wiresheet, most ComponentEvents will be handled by the view, therefore
    // we'll just try posting everything we receive.
    BWidget.invokeLater(() -> getWireSheetPane().controller.handleComponentEvent(event));
  }
}
