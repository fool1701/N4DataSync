/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import java.io.IOException;

import javax.baja.hx.HxOp;
import javax.baja.io.HtmlWriter;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

/**
 * BHxBackButton.
 *
 * @author    JJ Frankovich
 * @creation  09 Jul 09
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:BackButton",
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public class BHxBackButton
  extends BHxWbCommandButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxBackButton(3683872898)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxBackButton INSTANCE = new BHxBackButton();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxBackButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxBackButton() {}

  public void handle(BInputEvent event, HxOp op)
    throws IOException
  {
    if (event instanceof BMouseEvent && event.getId() == BMouseEvent.MOUSE_PRESSED)
    {
      BMouseEvent mouseEvent = (BMouseEvent)event;
      if(mouseEvent.isButton1Down())
      {
        HtmlWriter out = op.getHtmlWriter();
        out.w("history.go(-1);");
      }
    }
  }  
}
