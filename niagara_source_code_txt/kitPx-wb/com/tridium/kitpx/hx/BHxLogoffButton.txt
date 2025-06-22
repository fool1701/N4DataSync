/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import java.io.IOException;

import javax.baja.hx.HxOp;
import javax.baja.hx.HxUtil;
import javax.baja.io.HtmlWriter;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

/**
 * BHxLogoffButton.
 *
 * @author    Andy Frank
 * @creation  14 Feb 05
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:LogoffButton",
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public class BHxLogoffButton
  extends BHxWbCommandButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxLogoffButton(2125556314)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxLogoffButton INSTANCE = new BHxLogoffButton();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxLogoffButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxLogoffButton() {}

  public void handle(BInputEvent event, HxOp op)
    throws IOException
  {
    if (event instanceof BMouseEvent && event.getId() == BMouseEvent.MOUSE_PRESSED)
    {
      BMouseEvent mouseEvent = (BMouseEvent)event;
      if(mouseEvent.isButton1Down())
      {
        //Stick the csrf token in if there is a valid session
        String csrfTokenQs = HxUtil.getCsrfTokenQueryString();

        HtmlWriter out = op.getHtmlWriter();
        out.append("hx.hyperlink").append("('");
        out.append("/logout");
        out.append(csrfTokenQs);
        out.append("');");
      }
    }
  }   
  
}
