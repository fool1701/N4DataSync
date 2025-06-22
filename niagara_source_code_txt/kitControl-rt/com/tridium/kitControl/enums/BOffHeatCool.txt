/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BOffHeatCool is an BEnum that represents Off Heat Cool selection
 * values
 *
 * @author    Andy Saunders
 * @creation  27 Jan 05
 * @version   $Revision: 11$ $Date: 03-Jun-04 1:11:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("off"),
    @Range("heat"),
    @Range("cool")
  }
)
public final class BOffHeatCool
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BOffHeatCool(238933864)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for off. */
  public static final int OFF = 0;
  /** Ordinal value for heat. */
  public static final int HEAT = 1;
  /** Ordinal value for cool. */
  public static final int COOL = 2;

  /** BOffHeatCool constant for off. */
  public static final BOffHeatCool off = new BOffHeatCool(OFF);
  /** BOffHeatCool constant for heat. */
  public static final BOffHeatCool heat = new BOffHeatCool(HEAT);
  /** BOffHeatCool constant for cool. */
  public static final BOffHeatCool cool = new BOffHeatCool(COOL);

  /** Factory method with ordinal. */
  public static BOffHeatCool make(int ordinal)
  {
    return (BOffHeatCool)off.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BOffHeatCool make(String tag)
  {
    return (BOffHeatCool)off.getRange().get(tag);
  }

  /** Private constructor. */
  private BOffHeatCool(int ordinal)
  {
    super(ordinal);
  }

  public static final BOffHeatCool DEFAULT = off;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOffHeatCool.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /*********************************************
  *  Convenience method.  Returns true if and only
  *  if the current value of the enumeration
  *  is NOT equal to NO_FAULT_DETECTED.
  **********************************************/
  public final boolean isFault()
  {
    return (this != off);
  }
      
}
