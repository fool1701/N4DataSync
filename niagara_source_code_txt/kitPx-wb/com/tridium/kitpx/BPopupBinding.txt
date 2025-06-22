/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitpx;

import java.util.logging.Level;

import javax.baja.gx.BPoint;
import javax.baja.gx.BSize;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.MouseCursor;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.BFormat;
import javax.baja.util.Lexicon;
import javax.baja.util.LexiconModule;
import javax.baja.workbench.BWbShell;

import com.tridium.ui.UiEnv;
import com.tridium.util.FormatUtil;
import com.tridium.workbench.shell.BNiagaraWbDialog;

/**
 * The pop up binding
 * 
 * This binding gets used for popping up new views in a window
 * 
 * @author    Gareth Johnson
 * @creation  11 May 2007
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:Widget"
  )
)
/*
 The title of the pop up Window.
 This can be localized by using a BFormat text like '%lexicon(kitPx:popupBinding.title)%'.
 */
@NiagaraProperty(
  name = "title",
  type = "String",
  defaultValue = "Pop up"
)
/*
 The position of the pop up window
 */
@NiagaraProperty(
  name = "position",
  type = "BPoint",
  defaultValue = "BPoint.make(100, 100)"
)
/*
 The size of the pop up window
 */
@NiagaraProperty(
  name = "size",
  type = "BSize",
  defaultValue = "BSize.make(800, 600)"
)
/*
 Is the pop up modal
 */
@NiagaraProperty(
  name = "modal",
  type = "boolean",
  defaultValue = "false"
)
/*
 Fired whenever there's an event on the associated Widget
 */
@NiagaraAction(
  name = "mouseEvent",
  parameterType = "BMouseEvent",
  defaultValue = "new BMouseEvent()"
)
public final class BPopupBinding
  extends BBinding
{



//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BPopupBinding(3599552445)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "title"

  /**
   * Slot for the {@code title} property.
   * The title of the pop up Window.
   * This can be localized by using a BFormat text like '%lexicon(kitPx:popupBinding.title)%'.
   * @see #getTitle
   * @see #setTitle
   */
  public static final Property title = newProperty(0, "Pop up", null);

  /**
   * Get the {@code title} property.
   * The title of the pop up Window.
   * This can be localized by using a BFormat text like '%lexicon(kitPx:popupBinding.title)%'.
   * @see #title
   */
  public String getTitle() { return getString(title); }

  /**
   * Set the {@code title} property.
   * The title of the pop up Window.
   * This can be localized by using a BFormat text like '%lexicon(kitPx:popupBinding.title)%'.
   * @see #title
   */
  public void setTitle(String v) { setString(title, v, null); }

  //endregion Property "title"

  //region Property "position"

  /**
   * Slot for the {@code position} property.
   * The position of the pop up window
   * @see #getPosition
   * @see #setPosition
   */
  public static final Property position = newProperty(0, BPoint.make(100, 100), null);

  /**
   * Get the {@code position} property.
   * The position of the pop up window
   * @see #position
   */
  public BPoint getPosition() { return (BPoint)get(position); }

  /**
   * Set the {@code position} property.
   * The position of the pop up window
   * @see #position
   */
  public void setPosition(BPoint v) { set(position, v, null); }

  //endregion Property "position"

  //region Property "size"

  /**
   * Slot for the {@code size} property.
   * The size of the pop up window
   * @see #getSize
   * @see #setSize
   */
  public static final Property size = newProperty(0, BSize.make(800, 600), null);

  /**
   * Get the {@code size} property.
   * The size of the pop up window
   * @see #size
   */
  public BSize getSize() { return (BSize)get(size); }

  /**
   * Set the {@code size} property.
   * The size of the pop up window
   * @see #size
   */
  public void setSize(BSize v) { set(size, v, null); }

  //endregion Property "size"

  //region Property "modal"

  /**
   * Slot for the {@code modal} property.
   * Is the pop up modal
   * @see #getModal
   * @see #setModal
   */
  public static final Property modal = newProperty(0, false, null);

  /**
   * Get the {@code modal} property.
   * Is the pop up modal
   * @see #modal
   */
  public boolean getModal() { return getBoolean(modal); }

  /**
   * Set the {@code modal} property.
   * Is the pop up modal
   * @see #modal
   */
  public void setModal(boolean v) { setBoolean(modal, v, null); }

  //endregion Property "modal"

  //region Action "mouseEvent"

  /**
   * Slot for the {@code mouseEvent} action.
   * Fired whenever there's an event on the associated Widget
   * @see #mouseEvent(BMouseEvent parameter)
   */
  public static final Action mouseEvent = newAction(0, new BMouseEvent(), null);

  /**
   * Invoke the {@code mouseEvent} action.
   * Fired whenever there's an event on the associated Widget
   * @see #mouseEvent
   */
  public void mouseEvent(BMouseEvent parameter) { invoke(mouseEvent, parameter, null); }

  //endregion Action "mouseEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPopupBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
        
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  public void started()
  {
    super.started();
    
    // Issue 15791. Receive mouse events from Widget regardless of whether other 
    // bindings have consumed them
    if (getWidget() != null)
      linkTo(getWidget(), BWidget.mouseEvent, mouseEvent);
  }
  
////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////

  public void doMouseEvent(BMouseEvent event)
  {
    if (!getWidget().isEnabled())
      return;
        
    switch(event.getId())
    {  
      case BMouseEvent.MOUSE_ENTERED:  entered(event); break;
      case BMouseEvent.MOUSE_EXITED:   exited(event); break;
      case BMouseEvent.MOUSE_RELEASED: released(event); break;
    } 
  }
  
  private void entered(BMouseEvent event)
  {       
    BWidgetShell shell = getShell();
    isOver = true;
    if (shell != null)
    {
      shell.showStatus(toShowStatus());
      if (!getOrd().isNull() && UiEnv.get().hasMouse())    
        restoreCursor = getWidget().setMouseCursor(MouseCursor.hand);
      else
        restoreCursor = null;
    }
  }
  
  private void exited(BMouseEvent event)
  {
    BWidgetShell shell = getShell();
    isOver = false;
    
    if (shell != null)
      shell.showStatus(""); 
      
    if (restoreCursor != null)
      getWidget().setMouseCursor(restoreCursor);
  }    
  
  private void released(BMouseEvent event)
  {  
    if (isOver && !getOrd().isNull() && event.isButton1Down())
      popup();
  }
  
////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  String toShowStatus()
  {
    BOrd ord = getOrd();
    if (ord.isNull()) return "";
    return lex.getText("popupBinding.summary", ord);
  }
  
////////////////////////////////////////////////////////////////
// Pop up methods
////////////////////////////////////////////////////////////////  
  
  /**
   * Pop up the page
   */
  private void popup()
  {
    try
    {           
      BWbShell shell = BWbShell.getWbShell(getWidget());
      
      BOrd o = BOrd.make(shell.getActiveOrd(), getOrd()).normalize();
      String titleFormat = FormatUtil.formatForStringProperty(getTitle(), getTarget());
      BNiagaraWbDialog dlg = new BNiagaraWbDialog(BPopupProfile.TYPE, shell, o, titleFormat, getPosition(), getSize(), getModal());
      dlg.open();
    }
    catch(Exception e)
    {
      BBinding.LOGGER.log(Level.SEVERE, "Could not resolve pop up binding ORD", e);
    }
  } 
  
////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////
  
  private MouseCursor restoreCursor; 
  private boolean isOver = false; 
  private static final Lexicon lex = Lexicon.make(BPopupBinding.class);
}
