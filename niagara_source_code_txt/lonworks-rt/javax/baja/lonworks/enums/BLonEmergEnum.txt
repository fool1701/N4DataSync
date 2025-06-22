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
 * The BLonEmergEnum class provides enumeration for SNVT_hvac_emerg
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:27 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "emergNormal", ordinal = 0),
    @Range(value = "emergPressurize", ordinal = 1),
    @Range(value = "emergDepressurize", ordinal = 2),
    @Range(value = "emergPurge", ordinal = 3),
    @Range(value = "emergShutdown", ordinal = 4),
    @Range(value = "emergFire", ordinal = 5),
    @Range(value = "emergNul", ordinal = -1)
  }
)
public final class BLonEmergEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonEmergEnum(1334407709)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for emergNormal. */
  public static final int EMERG_NORMAL = 0;
  /** Ordinal value for emergPressurize. */
  public static final int EMERG_PRESSURIZE = 1;
  /** Ordinal value for emergDepressurize. */
  public static final int EMERG_DEPRESSURIZE = 2;
  /** Ordinal value for emergPurge. */
  public static final int EMERG_PURGE = 3;
  /** Ordinal value for emergShutdown. */
  public static final int EMERG_SHUTDOWN = 4;
  /** Ordinal value for emergFire. */
  public static final int EMERG_FIRE = 5;
  /** Ordinal value for emergNul. */
  public static final int EMERG_NUL = -1;

  /** BLonEmergEnum constant for emergNormal. */
  public static final BLonEmergEnum emergNormal = new BLonEmergEnum(EMERG_NORMAL);
  /** BLonEmergEnum constant for emergPressurize. */
  public static final BLonEmergEnum emergPressurize = new BLonEmergEnum(EMERG_PRESSURIZE);
  /** BLonEmergEnum constant for emergDepressurize. */
  public static final BLonEmergEnum emergDepressurize = new BLonEmergEnum(EMERG_DEPRESSURIZE);
  /** BLonEmergEnum constant for emergPurge. */
  public static final BLonEmergEnum emergPurge = new BLonEmergEnum(EMERG_PURGE);
  /** BLonEmergEnum constant for emergShutdown. */
  public static final BLonEmergEnum emergShutdown = new BLonEmergEnum(EMERG_SHUTDOWN);
  /** BLonEmergEnum constant for emergFire. */
  public static final BLonEmergEnum emergFire = new BLonEmergEnum(EMERG_FIRE);
  /** BLonEmergEnum constant for emergNul. */
  public static final BLonEmergEnum emergNul = new BLonEmergEnum(EMERG_NUL);

  /** Factory method with ordinal. */
  public static BLonEmergEnum make(int ordinal)
  {
    return (BLonEmergEnum)emergNormal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonEmergEnum make(String tag)
  {
    return (BLonEmergEnum)emergNormal.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonEmergEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonEmergEnum DEFAULT = emergNormal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonEmergEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
