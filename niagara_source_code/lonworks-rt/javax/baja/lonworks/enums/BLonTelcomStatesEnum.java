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
 * The BLonTelcomStatesEnum class provides enumeration for SNVT_telcom
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:39 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "telNotinuse", ordinal = 0),
    @Range(value = "telOffhook", ordinal = 1),
    @Range(value = "telDialing", ordinal = 2),
    @Range(value = "telDialcomp", ordinal = 3),
    @Range(value = "telRingback", ordinal = 4),
    @Range(value = "telIncoming", ordinal = 5),
    @Range(value = "telRinging", ordinal = 6),
    @Range(value = "telAnswered", ordinal = 7),
    @Range(value = "telTalking", ordinal = 8),
    @Range(value = "telHangingup", ordinal = 9),
    @Range(value = "telHungupx", ordinal = 10),
    @Range(value = "telHold", ordinal = 11),
    @Range(value = "telUnhold", ordinal = 12),
    @Range(value = "telRelease", ordinal = 13),
    @Range(value = "telFulldup", ordinal = 14),
    @Range(value = "telBlocked", ordinal = 15),
    @Range(value = "telCwait", ordinal = 16),
    @Range(value = "telDestbusy", ordinal = 17),
    @Range(value = "telNetbusy", ordinal = 18),
    @Range(value = "telError", ordinal = 19),
    @Range(value = "telNul", ordinal = -1)
  }
)
public final class BLonTelcomStatesEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonTelcomStatesEnum(966503924)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for telNotinuse. */
  public static final int TEL_NOTINUSE = 0;
  /** Ordinal value for telOffhook. */
  public static final int TEL_OFFHOOK = 1;
  /** Ordinal value for telDialing. */
  public static final int TEL_DIALING = 2;
  /** Ordinal value for telDialcomp. */
  public static final int TEL_DIALCOMP = 3;
  /** Ordinal value for telRingback. */
  public static final int TEL_RINGBACK = 4;
  /** Ordinal value for telIncoming. */
  public static final int TEL_INCOMING = 5;
  /** Ordinal value for telRinging. */
  public static final int TEL_RINGING = 6;
  /** Ordinal value for telAnswered. */
  public static final int TEL_ANSWERED = 7;
  /** Ordinal value for telTalking. */
  public static final int TEL_TALKING = 8;
  /** Ordinal value for telHangingup. */
  public static final int TEL_HANGINGUP = 9;
  /** Ordinal value for telHungupx. */
  public static final int TEL_HUNGUPX = 10;
  /** Ordinal value for telHold. */
  public static final int TEL_HOLD = 11;
  /** Ordinal value for telUnhold. */
  public static final int TEL_UNHOLD = 12;
  /** Ordinal value for telRelease. */
  public static final int TEL_RELEASE = 13;
  /** Ordinal value for telFulldup. */
  public static final int TEL_FULLDUP = 14;
  /** Ordinal value for telBlocked. */
  public static final int TEL_BLOCKED = 15;
  /** Ordinal value for telCwait. */
  public static final int TEL_CWAIT = 16;
  /** Ordinal value for telDestbusy. */
  public static final int TEL_DESTBUSY = 17;
  /** Ordinal value for telNetbusy. */
  public static final int TEL_NETBUSY = 18;
  /** Ordinal value for telError. */
  public static final int TEL_ERROR = 19;
  /** Ordinal value for telNul. */
  public static final int TEL_NUL = -1;

  /** BLonTelcomStatesEnum constant for telNotinuse. */
  public static final BLonTelcomStatesEnum telNotinuse = new BLonTelcomStatesEnum(TEL_NOTINUSE);
  /** BLonTelcomStatesEnum constant for telOffhook. */
  public static final BLonTelcomStatesEnum telOffhook = new BLonTelcomStatesEnum(TEL_OFFHOOK);
  /** BLonTelcomStatesEnum constant for telDialing. */
  public static final BLonTelcomStatesEnum telDialing = new BLonTelcomStatesEnum(TEL_DIALING);
  /** BLonTelcomStatesEnum constant for telDialcomp. */
  public static final BLonTelcomStatesEnum telDialcomp = new BLonTelcomStatesEnum(TEL_DIALCOMP);
  /** BLonTelcomStatesEnum constant for telRingback. */
  public static final BLonTelcomStatesEnum telRingback = new BLonTelcomStatesEnum(TEL_RINGBACK);
  /** BLonTelcomStatesEnum constant for telIncoming. */
  public static final BLonTelcomStatesEnum telIncoming = new BLonTelcomStatesEnum(TEL_INCOMING);
  /** BLonTelcomStatesEnum constant for telRinging. */
  public static final BLonTelcomStatesEnum telRinging = new BLonTelcomStatesEnum(TEL_RINGING);
  /** BLonTelcomStatesEnum constant for telAnswered. */
  public static final BLonTelcomStatesEnum telAnswered = new BLonTelcomStatesEnum(TEL_ANSWERED);
  /** BLonTelcomStatesEnum constant for telTalking. */
  public static final BLonTelcomStatesEnum telTalking = new BLonTelcomStatesEnum(TEL_TALKING);
  /** BLonTelcomStatesEnum constant for telHangingup. */
  public static final BLonTelcomStatesEnum telHangingup = new BLonTelcomStatesEnum(TEL_HANGINGUP);
  /** BLonTelcomStatesEnum constant for telHungupx. */
  public static final BLonTelcomStatesEnum telHungupx = new BLonTelcomStatesEnum(TEL_HUNGUPX);
  /** BLonTelcomStatesEnum constant for telHold. */
  public static final BLonTelcomStatesEnum telHold = new BLonTelcomStatesEnum(TEL_HOLD);
  /** BLonTelcomStatesEnum constant for telUnhold. */
  public static final BLonTelcomStatesEnum telUnhold = new BLonTelcomStatesEnum(TEL_UNHOLD);
  /** BLonTelcomStatesEnum constant for telRelease. */
  public static final BLonTelcomStatesEnum telRelease = new BLonTelcomStatesEnum(TEL_RELEASE);
  /** BLonTelcomStatesEnum constant for telFulldup. */
  public static final BLonTelcomStatesEnum telFulldup = new BLonTelcomStatesEnum(TEL_FULLDUP);
  /** BLonTelcomStatesEnum constant for telBlocked. */
  public static final BLonTelcomStatesEnum telBlocked = new BLonTelcomStatesEnum(TEL_BLOCKED);
  /** BLonTelcomStatesEnum constant for telCwait. */
  public static final BLonTelcomStatesEnum telCwait = new BLonTelcomStatesEnum(TEL_CWAIT);
  /** BLonTelcomStatesEnum constant for telDestbusy. */
  public static final BLonTelcomStatesEnum telDestbusy = new BLonTelcomStatesEnum(TEL_DESTBUSY);
  /** BLonTelcomStatesEnum constant for telNetbusy. */
  public static final BLonTelcomStatesEnum telNetbusy = new BLonTelcomStatesEnum(TEL_NETBUSY);
  /** BLonTelcomStatesEnum constant for telError. */
  public static final BLonTelcomStatesEnum telError = new BLonTelcomStatesEnum(TEL_ERROR);
  /** BLonTelcomStatesEnum constant for telNul. */
  public static final BLonTelcomStatesEnum telNul = new BLonTelcomStatesEnum(TEL_NUL);

  /** Factory method with ordinal. */
  public static BLonTelcomStatesEnum make(int ordinal)
  {
    return (BLonTelcomStatesEnum)telNotinuse.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonTelcomStatesEnum make(String tag)
  {
    return (BLonTelcomStatesEnum)telNotinuse.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonTelcomStatesEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonTelcomStatesEnum DEFAULT = telNotinuse;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonTelcomStatesEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
