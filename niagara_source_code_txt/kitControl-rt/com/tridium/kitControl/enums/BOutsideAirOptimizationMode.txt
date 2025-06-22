/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BOutsideAirOptimizationMode is an BEnum that defines operation modes of
 * the BOutsideAirOptimization object.
 *
 * @author    Andy Saunders
 * @creation  21 April 04
 * @version   $Revision: 11$ $Date: 03-Jun-04 1:11:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("disabled"),
    @Range("inputError"),
    @Range("lowTemperature"),
    @Range("freeCooling"),
    @Range("noFreeCooling")
  }
)
public final class BOutsideAirOptimizationMode
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BOutsideAirOptimizationMode(3909347142)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for disabled. */
  public static final int DISABLED = 0;
  /** Ordinal value for inputError. */
  public static final int INPUT_ERROR = 1;
  /** Ordinal value for lowTemperature. */
  public static final int LOW_TEMPERATURE = 2;
  /** Ordinal value for freeCooling. */
  public static final int FREE_COOLING = 3;
  /** Ordinal value for noFreeCooling. */
  public static final int NO_FREE_COOLING = 4;

  /** BOutsideAirOptimizationMode constant for disabled. */
  public static final BOutsideAirOptimizationMode disabled = new BOutsideAirOptimizationMode(DISABLED);
  /** BOutsideAirOptimizationMode constant for inputError. */
  public static final BOutsideAirOptimizationMode inputError = new BOutsideAirOptimizationMode(INPUT_ERROR);
  /** BOutsideAirOptimizationMode constant for lowTemperature. */
  public static final BOutsideAirOptimizationMode lowTemperature = new BOutsideAirOptimizationMode(LOW_TEMPERATURE);
  /** BOutsideAirOptimizationMode constant for freeCooling. */
  public static final BOutsideAirOptimizationMode freeCooling = new BOutsideAirOptimizationMode(FREE_COOLING);
  /** BOutsideAirOptimizationMode constant for noFreeCooling. */
  public static final BOutsideAirOptimizationMode noFreeCooling = new BOutsideAirOptimizationMode(NO_FREE_COOLING);

  /** Factory method with ordinal. */
  public static BOutsideAirOptimizationMode make(int ordinal)
  {
    return (BOutsideAirOptimizationMode)disabled.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BOutsideAirOptimizationMode make(String tag)
  {
    return (BOutsideAirOptimizationMode)disabled.getRange().get(tag);
  }

  /** Private constructor. */
  private BOutsideAirOptimizationMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BOutsideAirOptimizationMode DEFAULT = disabled;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutsideAirOptimizationMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


      
}
