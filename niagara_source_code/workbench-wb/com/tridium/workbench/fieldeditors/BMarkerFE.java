/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.BMarker;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Marker"
  )
)
public class BMarkerFE extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BMarkerFE(2358760056)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMarkerFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  static final BImage icon = BImage.make(BIcon.std("tag.png"));

  public BMarkerFE()
  {
    setContent(new BLabel(icon, BMarker.DEFAULT.toString(getCurrentContext())));
  }


  @Override
  protected BObject doSaveValue(BObject value, Context cx) throws Exception
  {
    return BMarker.DEFAULT;
  }
}
