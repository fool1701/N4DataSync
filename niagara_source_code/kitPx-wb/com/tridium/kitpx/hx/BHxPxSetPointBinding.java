/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import javax.baja.agent.BIAgent;
import javax.baja.hx.HxOp;
import javax.baja.hx.PropertiesCollection;
import javax.baja.hx.px.BHxPxWidget;
import javax.baja.hx.px.binding.BHxPxBinding;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.hx.util.HxUtils;
import com.tridium.kitpx.BGenericFieldEditor;
import com.tridium.kitpx.BSetPointBinding;

/**
 * @author Lee Adcock
 * @creation 29 Sept 09
 * @version $Revision$ $Date: 9/29/2009 9:51:02 AM$
 * @since Niagara 3.5
 */  
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:SetPointBinding"
  )
)
@NiagaraSingleton
public class BHxPxSetPointBinding
  extends BHxPxBinding
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxSetPointBinding(2598270589)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxSetPointBinding INSTANCE = new BHxPxSetPointBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxSetPointBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxSetPointBinding() {}

  public void save(BObject value, HxOp op)
    throws Exception
  {
    BSetPointBinding setPointBinding = (BSetPointBinding)op.get();
    if (!setPointBinding.isBound()) 
      return;
    
    setPointBinding.saveSetPoint((BValue)value,op.getBase());  
  }  
  
  public void handle(BInputEvent event, HxOp op)
      throws Exception
  {
    if (event instanceof BMouseEvent && event.getId() == BMouseEvent.MOUSE_PRESSED)
    {
      BMouseEvent mouseEvent = (BMouseEvent)event;
      if(mouseEvent.isButton1Down())
      {
        BSetPointBinding setPointBinding = (BSetPointBinding)op.get();
        if (!setPointBinding.isBound()) 
          return;
        
        BWidget widget = setPointBinding.getWidget();
        BHxPxWidget hxPxWidget = BHxPxWidget.makeFor(widget);
        HxOp widgetOp = ((HxOp)op.getBase().getBase());
        hxPxWidget.save(widgetOp);
        setPointBinding.saveSetPoint(op.getBase());
      }
    }
  }

  /**
   * @see BHxPxBinding#update
   * Overriden only to ensure binding degrade behavior for BGenericFieldEditors which are different than other BWidgets.
   */
  public void update(int width, int height, boolean forceUpdate, HxOp op) throws Exception
  {
    BBinding binding = (BBinding)op.get();
    HxOp baseOp = ((HxOp)op.getBase().getBase());

    if(binding.getWidget() instanceof BGenericFieldEditor)
    {

      if (!binding.isBound() || !binding.getWidget().getVisible())
      {
        PropertiesCollection style = new PropertiesCollection.Styles();
        style.add("visibility", "hidden");
        style.write(baseOp);
      }
      else
      {
        PropertiesCollection style = new PropertiesCollection.Styles();
        style.add("visibility", "inherit");
        style.write(baseOp);
      }
    }
    else{
      super.update(width, height, forceUpdate, op);
    }
  }
}
