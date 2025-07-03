/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import java.io.Writer;

import javax.baja.agent.BIAgent;
import javax.baja.gx.IGeom;
import javax.baja.hx.HxOp;
import javax.baja.hx.HxUtil;
import javax.baja.hx.PropertiesCollection;
import javax.baja.hx.px.BHxPxWidget;
import javax.baja.hx.px.MouseEventCommand;
import javax.baja.hx.px.binding.BHxPxBinding;
import javax.baja.io.HtmlWriter;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.LexiconModule;

import com.tridium.kitpx.BMouseOverBinding;

/**
 * BHxPxMouseOverBinding
 * <p>
 * This binding is the corresponding HxPx equivalent for BMouseOverBinding to permit supporting
 * animation effects (like highlighting) on mouse enter / mouse exit events for widgets.
 *
 * @author Danesh Kamal
 * @version $Revision$ $Date$
 * @creation June 17 2016
 * @since Niagara 4.3
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:MouseOverBinding"
  )
)
@NiagaraSingleton
public class BHxPxMouseOverBinding
  extends BHxPxBinding
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxMouseOverBinding(3711894980)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxMouseOverBinding INSTANCE = new BHxPxMouseOverBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxMouseOverBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  protected BHxPxMouseOverBinding() {}

  @Override
  public void write(HxOp op) throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();
    HxOp widgetOp = ((HxOp) op.getBase().getBase());
    BMouseOverBinding mouseOverBinding = (BMouseOverBinding) op.get();
    BHxPxWidget hxPxWidget = BHxPxWidget.makeFor(mouseOverBinding.getWidget());

    //register event handlers for 'mouseenter' and 'mouseleave'
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
          events.add("onmouseenter", "if(!hx.$('" + widgetOp.getPath() + "').$entered){ hx.$('" + widgetOp.getPath() + "').$entered=true;" + mouseHandler.getInvokeCode(BMouseEvent.MOUSE_ENTERED, widgetOp) + "}");
          events.add("onmouseleave", "hx.$('" + widgetOp.getPath() + "').$entered=false;" + mouseHandler.getInvokeCode(BMouseEvent.MOUSE_EXITED, widgetOp));
          events.write(widgetOp);
        }
        finally
        {
          HxUtil.finishOnloadWriter(writer, op);
        }
      }
    }
  }

  public String validateBinding(BBinding binding, Context cx)
  {
    BObject parentWidget = binding.getParent();
    if (parentWidget != null)
    {
      try
      {
        BHxPxWidget hxPxWidget = BHxPxWidget.makeFor(parentWidget, cx);
        if (hxPxWidget.getMouseEventHandler() == null)
          return LEX.getText("validateMedia.noMouseOverBinding", cx, parentWidget.getType().getDisplayName(cx));
      } catch (Exception e)
      {
        //ignore, widget may not be represented in hx
      }
    }
    return null;
  }

  public void handle(BInputEvent event, HxOp op)
    throws Exception
  {
    if (event.getId() == BMouseEvent.MOUSE_ENTERED || event.getId() == BMouseEvent.MOUSE_EXITED)
    {
      BMouseEvent mouseEvent = (BMouseEvent) event;
      BMouseOverBinding mouseOverBinding = (BMouseOverBinding) op.get();
      HxOp widgetOp = ((HxOp) op.getBase().getBase());
      BHxPxWidget hxPxWidget = BHxPxWidget.makeFor(mouseOverBinding.getWidget());
      IGeom geom = hxPxWidget.getGeom(mouseOverBinding.getWidget(), widgetOp);

      //dispatch event to MouseOverBinding and force widget update
      mouseOverBinding.mouseEvent(mouseEvent);
      hxPxWidget.update((int) geom.bounds().width(), (int) geom.bounds().height(), true, widgetOp);
    }
  }
  private static LexiconModule LEX = LexiconModule.make(BHxPxMouseOverBinding.class);
}
