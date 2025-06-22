/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.util;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
@NiagaraSingleton
public class BThermistorNoOpType extends BAbstractThermistorType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.util.BThermistorNoOpType(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BThermistorNoOpType INSTANCE = new BThermistorNoOpType();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BThermistorNoOpType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BThermistorNoOpType()
  {
  }
  
  public float convertValue(float resistance)
  {
    return resistance;
  }  
}
