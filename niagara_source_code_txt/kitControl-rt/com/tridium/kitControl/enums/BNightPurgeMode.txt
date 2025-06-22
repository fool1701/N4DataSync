/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BNightPurgeMode is an BEnum that defines operation modes of
 * the BNightPurge object.
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
    @Range("noFreeCooling"),
    @Range("satisfied")
  }
)
public final class BNightPurgeMode
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BNightPurgeMode(3827286000)1.0$ @*/
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
  /** Ordinal value for satisfied. */
  public static final int SATISFIED = 5;

  /** BNightPurgeMode constant for disabled. */
  public static final BNightPurgeMode disabled = new BNightPurgeMode(DISABLED);
  /** BNightPurgeMode constant for inputError. */
  public static final BNightPurgeMode inputError = new BNightPurgeMode(INPUT_ERROR);
  /** BNightPurgeMode constant for lowTemperature. */
  public static final BNightPurgeMode lowTemperature = new BNightPurgeMode(LOW_TEMPERATURE);
  /** BNightPurgeMode constant for freeCooling. */
  public static final BNightPurgeMode freeCooling = new BNightPurgeMode(FREE_COOLING);
  /** BNightPurgeMode constant for noFreeCooling. */
  public static final BNightPurgeMode noFreeCooling = new BNightPurgeMode(NO_FREE_COOLING);
  /** BNightPurgeMode constant for satisfied. */
  public static final BNightPurgeMode satisfied = new BNightPurgeMode(SATISFIED);

  /** Factory method with ordinal. */
  public static BNightPurgeMode make(int ordinal)
  {
    return (BNightPurgeMode)disabled.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BNightPurgeMode make(String tag)
  {
    return (BNightPurgeMode)disabled.getRange().get(tag);
  }

  /** Private constructor. */
  private BNightPurgeMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BNightPurgeMode DEFAULT = disabled;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNightPurgeMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


      
}
