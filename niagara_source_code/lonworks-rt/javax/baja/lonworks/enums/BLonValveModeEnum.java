/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BLonValveModeEnum represents Lonworks standard enumeration ValveModeT.
 *
 * @author    Robert Adams
 * @creation  12 Jan 01
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:32 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "valveNormal", ordinal = 0),
    @Range(value = "valveCooling", ordinal = 1),
    @Range(value = "valveHeating", ordinal = 2),
    @Range(value = "valveEmergency", ordinal = 3),
    @Range(value = "valveStrokeAdp", ordinal = 4),
    @Range(value = "valveStrokeSyn", ordinal = 5),
    @Range(value = "valveError", ordinal = 6),
    @Range(value = "valveOverridden", ordinal = 7),
    @Range(value = "valveNul", ordinal = -1)
  }
)
public final class BLonValveModeEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonValveModeEnum(2363569437)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for valveNormal. */
  public static final int VALVE_NORMAL = 0;
  /** Ordinal value for valveCooling. */
  public static final int VALVE_COOLING = 1;
  /** Ordinal value for valveHeating. */
  public static final int VALVE_HEATING = 2;
  /** Ordinal value for valveEmergency. */
  public static final int VALVE_EMERGENCY = 3;
  /** Ordinal value for valveStrokeAdp. */
  public static final int VALVE_STROKE_ADP = 4;
  /** Ordinal value for valveStrokeSyn. */
  public static final int VALVE_STROKE_SYN = 5;
  /** Ordinal value for valveError. */
  public static final int VALVE_ERROR = 6;
  /** Ordinal value for valveOverridden. */
  public static final int VALVE_OVERRIDDEN = 7;
  /** Ordinal value for valveNul. */
  public static final int VALVE_NUL = -1;

  /** BLonValveModeEnum constant for valveNormal. */
  public static final BLonValveModeEnum valveNormal = new BLonValveModeEnum(VALVE_NORMAL);
  /** BLonValveModeEnum constant for valveCooling. */
  public static final BLonValveModeEnum valveCooling = new BLonValveModeEnum(VALVE_COOLING);
  /** BLonValveModeEnum constant for valveHeating. */
  public static final BLonValveModeEnum valveHeating = new BLonValveModeEnum(VALVE_HEATING);
  /** BLonValveModeEnum constant for valveEmergency. */
  public static final BLonValveModeEnum valveEmergency = new BLonValveModeEnum(VALVE_EMERGENCY);
  /** BLonValveModeEnum constant for valveStrokeAdp. */
  public static final BLonValveModeEnum valveStrokeAdp = new BLonValveModeEnum(VALVE_STROKE_ADP);
  /** BLonValveModeEnum constant for valveStrokeSyn. */
  public static final BLonValveModeEnum valveStrokeSyn = new BLonValveModeEnum(VALVE_STROKE_SYN);
  /** BLonValveModeEnum constant for valveError. */
  public static final BLonValveModeEnum valveError = new BLonValveModeEnum(VALVE_ERROR);
  /** BLonValveModeEnum constant for valveOverridden. */
  public static final BLonValveModeEnum valveOverridden = new BLonValveModeEnum(VALVE_OVERRIDDEN);
  /** BLonValveModeEnum constant for valveNul. */
  public static final BLonValveModeEnum valveNul = new BLonValveModeEnum(VALVE_NUL);

  /** Factory method with ordinal. */
  public static BLonValveModeEnum make(int ordinal)
  {
    return (BLonValveModeEnum)valveNormal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonValveModeEnum make(String tag)
  {
    return (BLonValveModeEnum)valveNormal.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonValveModeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonValveModeEnum DEFAULT = valveNormal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonValveModeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
