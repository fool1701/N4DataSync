/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.commands.InvokeActionCommand;
import javax.baja.ui.event.BMouseEvent;

/**
 * BActionBinding invokes an action on the binding target component
 * when an event is fired by the parent widget.  The ord of an ActionBinding
 * must resolve down to a specific action within a BComponent.
 *
 * @author    Brian Frank on 31 Aug 04
 * @since     Niagara 3.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:Widget"
  )
)
/*
 Slot name of widget action or topic to trigger action.
 */
@NiagaraProperty(
  name = "widgetEvent",
  type = "String",
  defaultValue = ""
)
/*
 Argument to pass to action.
 */
@NiagaraProperty(
  name = "actionArg",
  type = "BValue",
  defaultValue = "BString.DEFAULT"
)
public class BActionBinding
  extends BBinding
{                          
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BActionBinding(3736301152)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "widgetEvent"

  /**
   * Slot for the {@code widgetEvent} property.
   * Slot name of widget action or topic to trigger action.
   * @see #getWidgetEvent
   * @see #setWidgetEvent
   */
  public static final Property widgetEvent = newProperty(0, "", null);

  /**
   * Get the {@code widgetEvent} property.
   * Slot name of widget action or topic to trigger action.
   * @see #widgetEvent
   */
  public String getWidgetEvent() { return getString(widgetEvent); }

  /**
   * Set the {@code widgetEvent} property.
   * Slot name of widget action or topic to trigger action.
   * @see #widgetEvent
   */
  public void setWidgetEvent(String v) { setString(widgetEvent, v, null); }

  //endregion Property "widgetEvent"

  //region Property "actionArg"

  /**
   * Slot for the {@code actionArg} property.
   * Argument to pass to action.
   * @see #getActionArg
   * @see #setActionArg
   */
  public static final Property actionArg = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code actionArg} property.
   * Argument to pass to action.
   * @see #actionArg
   */
  public BValue getActionArg() { return get(actionArg); }

  /**
   * Set the {@code actionArg} property.
   * Argument to pass to action.
   * @see #actionArg
   */
  public void setActionArg(BValue v) { set(actionArg, v, null); }

  //endregion Property "actionArg"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BActionBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public boolean invokedOnWidget(Action action, BValue value, Context context) 
  {
    if (action.getName().equals(getWidgetEvent()))
    {
      invokeActionOnTarget();
      return true;
    }     
    return false;
  }

  @Override
  public boolean firedOnWidget(Topic topic, BValue event, Context context)
  {
    if (topic.getName().equals(getWidgetEvent()))
    {
      if(event instanceof BMouseEvent)
      {
        BMouseEvent mouseEvent = (BMouseEvent) event;
        //dialog popup causes exit, so skip mouse exits to prevent double dialogs
        if(mouseEvent.getId() == BMouseEvent.MOUSE_EXITED)
          return false;
      }
      invokeActionOnTarget();
      return true;
    }     
    return false;
  }

  @Override
  public boolean isDegraded()
  {                              
    return !isBound() || !getTarget().canInvoke();
  }

  @Override
  public void targetChanged()
  {            
    if (!firedStarted && isBound() && getWidgetEvent().equals("started"))
    {                
      firedStarted = true;
      invokeActionOnTarget();
    }                 
    super.targetChanged();
  }
  
  public void invokeActionOnTarget()
  {             
    BOrd ord = getOrd();
    if (ord.isNull())
    {
      return;
    }

    // check if bound
    if (!isBound())
    {
      LOGGER.severe("ActionBinding not bound " + ord);
      return;
    }

    // get bound component
    OrdTarget target = getTarget();
    BComponent comp = target.getComponent();
    if (comp == null)
    {
      LOGGER.severe("ActionBinding not bound to component " + ord);
      return;
    }

    // get action
    Action action = null;
    if (target.getSlotInComponent() instanceof Action)
    {
      action = (Action) target.getSlotInComponent();
    }
    if (action == null)
    {
      LOGGER.severe("ActionBinding not bound to action " + ord);
      return;
    }

    // check if we have an argument to use
    BValue actionArg = null;
    BValue arg = getActionArg();
    Type paramType = action.getParameterType();
    if (!arg.equals(BString.DEFAULT) && paramType != null)
    {
      // if configured arg type, just use it
      if (arg.getType().is(paramType))
      {
        actionArg = arg;
      }

      // if arg is string and param is simple, decode it
      else if (arg instanceof BString && paramType.is(BSimple.TYPE))
      {
        try
        {
          BSimple simpleParam = (BSimple) paramType.getInstance();
          actionArg = (BValue) simpleParam.decodeFromString(arg.toString());
        }
        catch (Exception e)
        {
          LOGGER.severe("ActionBinding parsing arg \"" + arg + "\" as \"" + paramType + "\"");
        }
      }

      else
      {
        LOGGER.severe("ActionBinding arg type mismatch " + arg.getType() + " != " + paramType);
      }
    }

    //don't run more than 1 action at a time as multiple dialogs can be confusing when
    //ActionPerformed is mouseEvent
    if (runningAction)
    {
      return;
    }

    synchronized(actionMonitor)
    {
      if (runningAction)
      {
        return;
      }

      try
      {
        runningAction = true;
        new InvokeActionCommand(getWidget(), comp, action, actionArg).invoke();
      }
      finally
      {
        runningAction = false;
      }
    }
  }

  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////


  boolean firedStarted = false;

  /**
   * use the runningAction and actionMonitor to help prevent more than one dialog action showing at once
   */
  private volatile boolean runningAction = false;
  private final Object actionMonitor = new Object();
  
}
