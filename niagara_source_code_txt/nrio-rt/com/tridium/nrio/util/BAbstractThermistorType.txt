/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
public abstract class BAbstractThermistorType extends BSingleton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.util.BAbstractThermistorType(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractThermistorType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  protected BAbstractThermistorType()
  {
  }
  
  public abstract float convertValue(float ohms);
  
}
