/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
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
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.kitpx.BMomentaryToggleBinding;

/**
 * BHxPxMomentaryToggleBinding behaves like workbench's BMomentaryToggleBinding
 * except this binding will toggle back to false when the user moves their mouse
 * outside of the button they are pressing.
 *
 * @author Jeremy Narron
 * @since Niagara 4.3
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:MomentaryToggleBinding"
  )
)
@NiagaraSingleton
public class BHxPxMomentaryToggleBinding
  extends BHxPxBinding
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxMomentaryToggleBinding(3334764120)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxMomentaryToggleBinding INSTANCE = new BHxPxMomentaryToggleBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxMomentaryToggleBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxMomentaryToggleBinding() {}

  public void write(HxOp op)
    throws Exception
  {
    HxOp widgetOp = ((HxOp) op.getBase().getBase());
    BMomentaryToggleBinding momentaryToggleBinding = (BMomentaryToggleBinding) op.get();
    BHxPxWidget hxPxWidget = BHxPxWidget.makeFor(momentaryToggleBinding.getWidget());
    MouseEventCommand mouseHandler = hxPxWidget.getMouseEventHandler();

    if (mouseHandler != null)
    {
      hxPxWidget.registerEvent(mouseHandler, "mouseEventHandler");

      if (hxPxWidget.isEventRegistered(hxPxWidget.mouseEventCommand))
      {
        Writer writer = HxUtil.startOnloadWriter(op);
        try
        {
          PropertiesCollection events = new PropertiesCollection.Events();

          //slightly delay mouseup to prevent the mouseup from being sent before the mousedown.
          events.add("onmouseup", mouseHandler.getInvokeCode(BMouseEvent.MOUSE_RELEASED, 100, widgetOp));
          events.add("ontouchcancel", mouseHandler.getInvokeCode(BMouseEvent.MOUSE_RELEASED, 100, widgetOp));
          events.add("ontouchend", mouseHandler.getInvokeCode(BMouseEvent.MOUSE_RELEASED, 100, widgetOp));

          //onmousedown is already in BHxHtmlPxView
          events.add("ontouchstart", mouseHandler.getInvokeCode(BMouseEvent.MOUSE_PRESSED, widgetOp));


          // the JavaScript if statement is to make sure the mouseleave event
          // only fires when the user is pressing the button that has this binding
          events.add("onmouseleave", "if (event.buttons) {" + mouseHandler.getInvokeCode(BMouseEvent.MOUSE_EXITED, 100, widgetOp) + "}");

          events.write(widgetOp);
        }
        finally
        {
          HxUtil.finishOnloadWriter(writer, op);
        }
      }
    }
  }

  public void handle(BInputEvent event, HxOp op)
      throws Exception
  {
    switch(event.getId())
    {
      case BMouseEvent.MOUSE_PRESSED:
        ((BMomentaryToggleBinding) op.get()).setValueOnTarget(true);
        break;
      case BMouseEvent.MOUSE_RELEASED:
      case BMouseEvent.MOUSE_EXITED:
        ((BMomentaryToggleBinding) op.get()).setValueOnTarget(false);
    }
  }
}
