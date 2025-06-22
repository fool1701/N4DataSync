/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;

import com.tridium.ui.*;

/**
 * BActionMenuItem a menu item which when 
 * selected invokes an action.
 *
 * @author    Brian Frank       
 * @creation  2 Dec 00
 * @version   $Revision: 11$ $Date: 5/9/05 3:40:25 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The programatic hook for invoking an action
 which always results in the actionPerformed
 topic being fired.
 */
@NiagaraAction(
  name = "invokeAction"
)
/*
 The pressed topic fires an BActionEvent
 whenever the menu item is invoked.
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
public class BActionMenuItem
  extends BMenuItem
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BActionMenuItem(2060846560)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "invokeAction"

  /**
   * Slot for the {@code invokeAction} action.
   * The programatic hook for invoking an action
   * which always results in the actionPerformed
   * topic being fired.
   * @see #invokeAction()
   */
  public static final Action invokeAction = newAction(0, null);

  /**
   * Invoke the {@code invokeAction} action.
   * The programatic hook for invoking an action
   * which always results in the actionPerformed
   * topic being fired.
   * @see #invokeAction
   */
  public void invokeAction() { invoke(invokeAction, null, null); }

  //endregion Action "invokeAction"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * The pressed topic fires an BActionEvent
   * whenever the menu item is invoked.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * The pressed topic fires an BActionEvent
   * whenever the menu item is invoked.
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BActionMenuItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a menu item with specified label 
   * text and accelerator.
   */
  public BActionMenuItem(String text, BAccelerator accelerator)
  {
    super(text, accelerator);
  }

  /**
   * Construct a menu item with specified label text.
   */
  public BActionMenuItem(String text)
  {
    super(text);
  }

  /**
   * Construct a menu item for the specified command
   * using the command's label, accelerator, and icon.
   * The actionPerform topic is automatically registered
   * in the command.
   */
  public BActionMenuItem(Command command)
  {
    super(command);
  }

  /**
   * No argument constructor.
   */
  public BActionMenuItem()
  {
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /** 
   * Convenience for <code>doInvokeAction(null)</code>.
   */
  public final void doInvokeAction()
  {            
    doInvokeAction(null);
  }
  
  /**
   * This is the implementation which all action
   * invokes are routed to.  It can be used as a
   * consistent override point for catching all
   * programatic and user driven invocations.
   */
  public void doInvokeAction(CommandEvent event)
  {
    fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, this));
    if (command != null)
      command.invoke(event);
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Process a menu item activation.
   */
  void doClick(BInputEvent event)
  {          
    UiEnv.get().closePopup(null);
    doInvokeAction(new CommandEvent(event));
  }

}
