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
 * The BLonEvapEnum class provides enumeration for SNVT_evap_state
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:27 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "evapNoCooling", ordinal = 0),
    @Range(value = "evapCooling", ordinal = 1),
    @Range(value = "evapEmergCooling", ordinal = 2),
    @Range(value = "evapNul", ordinal = -1)
  }
)
public final class BLonEvapEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonEvapEnum(1943282054)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for evapNoCooling. */
  public static final int EVAP_NO_COOLING = 0;
  /** Ordinal value for evapCooling. */
  public static final int EVAP_COOLING = 1;
  /** Ordinal value for evapEmergCooling. */
  public static final int EVAP_EMERG_COOLING = 2;
  /** Ordinal value for evapNul. */
  public static final int EVAP_NUL = -1;

  /** BLonEvapEnum constant for evapNoCooling. */
  public static final BLonEvapEnum evapNoCooling = new BLonEvapEnum(EVAP_NO_COOLING);
  /** BLonEvapEnum constant for evapCooling. */
  public static final BLonEvapEnum evapCooling = new BLonEvapEnum(EVAP_COOLING);
  /** BLonEvapEnum constant for evapEmergCooling. */
  public static final BLonEvapEnum evapEmergCooling = new BLonEvapEnum(EVAP_EMERG_COOLING);
  /** BLonEvapEnum constant for evapNul. */
  public static final BLonEvapEnum evapNul = new BLonEvapEnum(EVAP_NUL);

  /** Factory method with ordinal. */
  public static BLonEvapEnum make(int ordinal)
  {
    return (BLonEvapEnum)evapNoCooling.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonEvapEnum make(String tag)
  {
    return (BLonEvapEnum)evapNoCooling.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonEvapEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonEvapEnum DEFAULT = evapNoCooling;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonEvapEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
