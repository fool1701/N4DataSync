/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import javax.baja.agent.BIAgent;
import javax.baja.hx.HxOp;
import javax.baja.hx.px.binding.BHxPxBinding;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.UnboundException;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.hx.util.HxUtils;
import com.tridium.kitpx.BIncrementSetPointBinding;

/**
 * @author Lee Adcock
 * @creation 29 Sept 09
 * @version $Revision$ $Date: 9/29/2009 9:51:02 AM$
 * @since Niagara 3.5
 */  
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:IncrementSetPointBinding"
  )
)
@NiagaraSingleton
public class BHxPxIncrementBinding
  extends BHxPxBinding
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxIncrementBinding(3691495288)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxIncrementBinding INSTANCE = new BHxPxIncrementBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxIncrementBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxIncrementBinding() {}

  public void handle(BInputEvent event, HxOp op)
      throws Exception
  {
    if (event instanceof BMouseEvent && event.getId() == BMouseEvent.MOUSE_PRESSED)
    {
      BMouseEvent mouseEvent = (BMouseEvent)event;
      if(mouseEvent.isButton1Down())
      {
        BIncrementSetPointBinding incrementBinding = (BIncrementSetPointBinding)op.get();
        try
        {
          incrementBinding.getTarget().getComponent().lease();
        } catch (UnboundException ue) {
          return;
        }
        incrementBinding.saveSetPoint(op.getBase());
        HxUtils.forceUpdate(op);
      }
    }
  }
  
}
