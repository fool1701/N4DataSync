/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitpx.hx;

import javax.baja.hx.px.BHxPxWidget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Hx Format Pane
 * 
 * @author    Gareth Johnson
 * @creation  20 Aug 2007
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 * 
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:FormatPane",
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public final class BHxPxFormatPane 
  extends BHxPxWidget  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxFormatPane(2381384473)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxFormatPane INSTANCE = new BHxPxFormatPane();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxFormatPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BHxPxFormatPane() {}
}
