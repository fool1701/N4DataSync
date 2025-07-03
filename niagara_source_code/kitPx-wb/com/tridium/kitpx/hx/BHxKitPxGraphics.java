/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.hx.px.BHxPxGraphics;

@NiagaraType(
  agent = @AgentOn(
    types = { "kitPx:AnalogMeter", "kitPx:Bargraph" },
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public class BHxKitPxGraphics
  extends BHxPxGraphics
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxKitPxGraphics(1572032157)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxKitPxGraphics INSTANCE = new BHxKitPxGraphics();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxKitPxGraphics.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxKitPxGraphics() {}

}
