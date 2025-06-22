/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import javax.baja.hx.HxOp;
import javax.baja.hx.PropertiesCollection;
import javax.baja.hx.px.binding.BHxPxBinding;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;

/**
 * @author JJ Frankovich
 * @since Niagara 4.12
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:ButtonGroupBinding"
  )
)
@NiagaraSingleton
public class BHxPxButtonGroupBinding
  extends BHxPxBinding
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxButtonGroupBinding(518452830)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxButtonGroupBinding INSTANCE = new BHxPxButtonGroupBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxButtonGroupBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxButtonGroupBinding() {}

  @Override
  public void update(int width, int height, boolean forceUpdate, HxOp op)
    throws Exception
  {
    super.update(width, height, forceUpdate, op);

    BBinding binding = (BBinding)op.get();

    HxOp baseOp = (HxOp)op.getBase().getBase();
    PropertiesCollection properties = new PropertiesCollection.Properties();

    properties.append("className", "ux-ButtonGroupBinding");
    if(!binding.getWidget().getEnabled())
    {
      properties.append("className", "bajaux-disabled");
    }

    properties.write(baseOp);
  }
}
