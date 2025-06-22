/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BReliability is an BEnum that represents valid Baja reliability
 * values
 *
 * FIXX - should this be an Enum -- will others want to extend it???
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 11$ $Date: 03-Jun-04 1:11:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("noFaultDetected"),
    @Range("noSensor"),
    @Range("overRange"),
    @Range("underRange"),
    @Range("openLoop"),
    @Range("shortedLoop"),
    @Range("noOutputValue"),
    @Range("unreliableOther"),
    @Range("processError")
  }
)
public final class BReliability
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BReliability(3665931438)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for noFaultDetected. */
  public static final int NO_FAULT_DETECTED = 0;
  /** Ordinal value for noSensor. */
  public static final int NO_SENSOR = 1;
  /** Ordinal value for overRange. */
  public static final int OVER_RANGE = 2;
  /** Ordinal value for underRange. */
  public static final int UNDER_RANGE = 3;
  /** Ordinal value for openLoop. */
  public static final int OPEN_LOOP = 4;
  /** Ordinal value for shortedLoop. */
  public static final int SHORTED_LOOP = 5;
  /** Ordinal value for noOutputValue. */
  public static final int NO_OUTPUT_VALUE = 6;
  /** Ordinal value for unreliableOther. */
  public static final int UNRELIABLE_OTHER = 7;
  /** Ordinal value for processError. */
  public static final int PROCESS_ERROR = 8;

  /** BReliability constant for noFaultDetected. */
  public static final BReliability noFaultDetected = new BReliability(NO_FAULT_DETECTED);
  /** BReliability constant for noSensor. */
  public static final BReliability noSensor = new BReliability(NO_SENSOR);
  /** BReliability constant for overRange. */
  public static final BReliability overRange = new BReliability(OVER_RANGE);
  /** BReliability constant for underRange. */
  public static final BReliability underRange = new BReliability(UNDER_RANGE);
  /** BReliability constant for openLoop. */
  public static final BReliability openLoop = new BReliability(OPEN_LOOP);
  /** BReliability constant for shortedLoop. */
  public static final BReliability shortedLoop = new BReliability(SHORTED_LOOP);
  /** BReliability constant for noOutputValue. */
  public static final BReliability noOutputValue = new BReliability(NO_OUTPUT_VALUE);
  /** BReliability constant for unreliableOther. */
  public static final BReliability unreliableOther = new BReliability(UNRELIABLE_OTHER);
  /** BReliability constant for processError. */
  public static final BReliability processError = new BReliability(PROCESS_ERROR);

  /** Factory method with ordinal. */
  public static BReliability make(int ordinal)
  {
    return (BReliability)noFaultDetected.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BReliability make(String tag)
  {
    return (BReliability)noFaultDetected.getRange().get(tag);
  }

  /** Private constructor. */
  private BReliability(int ordinal)
  {
    super(ordinal);
  }

  public static final BReliability DEFAULT = noFaultDetected;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReliability.class);

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
    return (this != noFaultDetected);
  }
      
}
