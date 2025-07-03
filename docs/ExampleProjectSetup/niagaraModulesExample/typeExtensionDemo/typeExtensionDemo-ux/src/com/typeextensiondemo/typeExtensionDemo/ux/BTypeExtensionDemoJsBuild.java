/*
 * Copyright 2020 Tridium, Inc. All Rights Reserved.
 */

package com.typeextensiondemo.typeExtensionDemo.ux;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.js.BJsBuild;

@NiagaraType
@NiagaraSingleton
public class BTypeExtensionDemoJsBuild
  extends BJsBuild
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.typeextensiondemo.typeExtensionDemo.ux.BTypeExtensionDemoJsBuild(2747097003)1.0$ @*/
/* Generated Thu Dec 01 20:57:47 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BTypeExtensionDemoJsBuild INSTANCE = new BTypeExtensionDemoJsBuild();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTypeExtensionDemoJsBuild.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BTypeExtensionDemoJsBuild()
  {
    super(
      "typeExtensionDemo",
      BOrd.make("module://typeExtensionDemo/rc/typeExtensionDemo.built.min.js")
    );
  }
}
