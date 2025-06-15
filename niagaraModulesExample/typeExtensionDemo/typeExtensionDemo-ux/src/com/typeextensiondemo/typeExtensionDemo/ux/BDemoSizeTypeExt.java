/*
 * Copyright 2020 Tridium, Inc. All Rights Reserved.
 */

package com.typeextensiondemo.typeExtensionDemo.ux;

import javax.baja.bajascript.BBajaScriptTypeExt;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.BIOffline;
import javax.baja.web.js.JsInfo;

@NiagaraType(
  agent = @AgentOn(
    types = "typeExtensionDemo:DemoSize"
  )
)
@NiagaraSingleton
public final class BDemoSizeTypeExt
  extends BBajaScriptTypeExt
  implements BIOffline
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.typeextensiondemo.typeExtensionDemo.ux.BDemoSizeTypeExt(2304774524)1.0$ @*/
/* Generated Thu Dec 01 20:57:47 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BDemoSizeTypeExt INSTANCE = new BDemoSizeTypeExt();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDemoSizeTypeExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BDemoSizeTypeExt()
  {
  }

  @Override
  public JsInfo getTypeExtJs(Context cx)
  {
    return JS_INFO;
  }

  private static final JsInfo JS_INFO = JsInfo.make(
    BOrd.make("module://typeExtensionDemo/rc/baja/DemoSize.js"),
    BTypeExtensionDemoJsBuild.TYPE
  );
}
