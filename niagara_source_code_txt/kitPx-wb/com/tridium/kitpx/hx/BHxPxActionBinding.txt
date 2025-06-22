/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import java.io.Writer;

import javax.baja.agent.BIAgent;
import javax.baja.hx.HxOp;
import javax.baja.hx.HxUtil;
import javax.baja.hx.PropertiesCollection;
import javax.baja.hx.px.BHxPxWidget;
import javax.baja.hx.px.MouseEventCommand;
import javax.baja.hx.px.binding.BHxPxBinding;
import javax.baja.hx.px.binding.BHxPxValueBinding;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BValue;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.kitpx.BActionBinding;

/**
 * @author Lee Adcock on 29 Sept 09
 * @since Niagara 3.5
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:ActionBinding"
  )
)
@NiagaraSingleton
public class BHxPxActionBinding
  extends BHxPxBinding
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxActionBinding(3422441309)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxActionBinding INSTANCE = new BHxPxActionBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxActionBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxActionBinding()
  {
    registerEvent(invokeActionCommand = new ActionBindingInvokeActionCommand());
  }

  @Override
  public void write(HxOp op)
    throws Exception
  {
    HxOp widgetOp = ((HxOp) op.getBase().getBase());
    BActionBinding actionBinding = (BActionBinding) op.get();
    BHxPxWidget hxPxWidget = BHxPxWidget.makeFor(actionBinding.getWidget());

    if(actionBinding.getWidgetEvent().equals("started"))
    {
      Writer writer = HxUtil.startOnloadWriter(op);
      try
      {
        BHxPxValueBinding.handleAction(invokeActionCommand, op);
      }
      finally
      {
        HxUtil.finishOnloadWriter(writer, op);
      }
    }

    MouseEventCommand mouseHandler = hxPxWidget.getMouseEventHandler();

    if (mouseHandler != null && actionBinding.getWidgetEvent().equals("mouseEvent"))
    {
      hxPxWidget.registerEvent(mouseHandler, "mouseEventHandler");

      if (hxPxWidget.isEventRegistered(hxPxWidget.mouseEventCommand))
      {
        Writer writer = HxUtil.startOnloadWriter(op);
        try
        {
          PropertiesCollection events = new PropertiesCollection.Events();
          events.add("onmouseenter", mouseHandler.getInvokeCode(BMouseEvent.MOUSE_ENTERED, widgetOp));
          events.write(widgetOp);
        }
        finally
        {
          HxUtil.finishOnloadWriter(writer, op);
        }
      }
    }
  }

  @Override
  public void handle(BInputEvent event, HxOp op)
    throws Exception
  {
    boolean button1Down = event.getId() == BMouseEvent.MOUSE_PRESSED && ((BMouseEvent) event).isButton1Down();
    BActionBinding actionBinding = (BActionBinding) op.get();
    String widgetEvent = actionBinding.getWidgetEvent();

    boolean fireFromMouseEvent = widgetEvent.equals("mouseEvent") &&
      ((event.getId() == BMouseEvent.MOUSE_ENTERED) || button1Down);

    boolean fireFromActionPerformed = widgetEvent.equals("actionPerformed") && button1Down;

    //Hx currently does not support focus events yet. this has been previously working with clicking,
    //so we'll keep it that way for now.
    boolean fireFromFocusEvent = widgetEvent.equals("focusEvent") && button1Down;

    if (fireFromMouseEvent || fireFromActionPerformed || fireFromFocusEvent)
    {
      BHxPxValueBinding.handleAction(invokeActionCommand, op);
    }
  }

  @Override
  public boolean process(HxOp op) throws Exception
  {
    if (BHxPxValueBinding.processAction(invokeActionCommand, op))
      return true;

    return super.process(op);
  }

  private static class ActionBindingInvokeActionCommand
    extends BHxPxValueBinding.InvokeActionCommand
  {

    @Override
    public String getActionName(HxOp op)
    {
      try
      {
        BActionBinding actionBinding = (BActionBinding) op.get();
        if(!actionBinding.isBound())
        {
          return null;
        }
        OrdTarget target = actionBinding.getTarget();
        Slot slot = target.getSlotInComponent();
        if(slot != null)
          return slot.getName();
      }
      catch(Exception ignore)  {}
      return null;
    }

    @Override
    public BValue getDefaultActionArgument(HxOp op)
    {
      BActionBinding actionBinding = (BActionBinding) op.get();
      if (!actionBinding.getActionArg().equals(BActionBinding.actionArg.getDefaultValue()))
        return actionBinding.getActionArg();
      else
        return null;
    }
  }

  private BHxPxValueBinding.InvokeActionCommand invokeActionCommand;
}
