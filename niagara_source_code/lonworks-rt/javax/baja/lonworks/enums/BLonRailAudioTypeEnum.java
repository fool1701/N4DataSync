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
 * The BLonRailAudioTypeEnum class provides enumeration for SNVT_rac_ctrl and
 *  SNVT_rac_req
 *
 * @author    Robert Adams
 * @creation  9 Nov 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "ratIcReq", ordinal = 0),
    @Range(value = "ratIcJoin", ordinal = 1),
    @Range(value = "ratIcQuit", ordinal = 2),
    @Range(value = "ratIcEnd", ordinal = 3),
    @Range(value = "ratHwRadioReq", ordinal = 4),
    @Range(value = "ratHwRadioEnd", ordinal = 5),
    @Range(value = "ratHwPaReq", ordinal = 6),
    @Range(value = "ratHwPaEnd", ordinal = 7),
    @Range(value = "ratSwPaReq", ordinal = 8),
    @Range(value = "ratSwPaEnd", ordinal = 9),
    @Range(value = "ratSwPaOrReq", ordinal = 10),
    @Range(value = "ratSwPaOrEnd", ordinal = 11),
    @Range(value = "ratPauReq", ordinal = 12),
    @Range(value = "ratPauAccept", ordinal = 13),
    @Range(value = "ratPauCall", ordinal = 14),
    @Range(value = "ratPauEnd", ordinal = 15),
    @Range(value = "ratEntertReq", ordinal = 16),
    @Range(value = "ratEntertEnd", ordinal = 17),
    @Range(value = "ratNul", ordinal = -1)
  },
  defaultValue = "ratNul"
)
public final class BLonRailAudioTypeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonRailAudioTypeEnum(1394470572)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ratIcReq. */
  public static final int RAT_IC_REQ = 0;
  /** Ordinal value for ratIcJoin. */
  public static final int RAT_IC_JOIN = 1;
  /** Ordinal value for ratIcQuit. */
  public static final int RAT_IC_QUIT = 2;
  /** Ordinal value for ratIcEnd. */
  public static final int RAT_IC_END = 3;
  /** Ordinal value for ratHwRadioReq. */
  public static final int RAT_HW_RADIO_REQ = 4;
  /** Ordinal value for ratHwRadioEnd. */
  public static final int RAT_HW_RADIO_END = 5;
  /** Ordinal value for ratHwPaReq. */
  public static final int RAT_HW_PA_REQ = 6;
  /** Ordinal value for ratHwPaEnd. */
  public static final int RAT_HW_PA_END = 7;
  /** Ordinal value for ratSwPaReq. */
  public static final int RAT_SW_PA_REQ = 8;
  /** Ordinal value for ratSwPaEnd. */
  public static final int RAT_SW_PA_END = 9;
  /** Ordinal value for ratSwPaOrReq. */
  public static final int RAT_SW_PA_OR_REQ = 10;
  /** Ordinal value for ratSwPaOrEnd. */
  public static final int RAT_SW_PA_OR_END = 11;
  /** Ordinal value for ratPauReq. */
  public static final int RAT_PAU_REQ = 12;
  /** Ordinal value for ratPauAccept. */
  public static final int RAT_PAU_ACCEPT = 13;
  /** Ordinal value for ratPauCall. */
  public static final int RAT_PAU_CALL = 14;
  /** Ordinal value for ratPauEnd. */
  public static final int RAT_PAU_END = 15;
  /** Ordinal value for ratEntertReq. */
  public static final int RAT_ENTERT_REQ = 16;
  /** Ordinal value for ratEntertEnd. */
  public static final int RAT_ENTERT_END = 17;
  /** Ordinal value for ratNul. */
  public static final int RAT_NUL = -1;

  /** BLonRailAudioTypeEnum constant for ratIcReq. */
  public static final BLonRailAudioTypeEnum ratIcReq = new BLonRailAudioTypeEnum(RAT_IC_REQ);
  /** BLonRailAudioTypeEnum constant for ratIcJoin. */
  public static final BLonRailAudioTypeEnum ratIcJoin = new BLonRailAudioTypeEnum(RAT_IC_JOIN);
  /** BLonRailAudioTypeEnum constant for ratIcQuit. */
  public static final BLonRailAudioTypeEnum ratIcQuit = new BLonRailAudioTypeEnum(RAT_IC_QUIT);
  /** BLonRailAudioTypeEnum constant for ratIcEnd. */
  public static final BLonRailAudioTypeEnum ratIcEnd = new BLonRailAudioTypeEnum(RAT_IC_END);
  /** BLonRailAudioTypeEnum constant for ratHwRadioReq. */
  public static final BLonRailAudioTypeEnum ratHwRadioReq = new BLonRailAudioTypeEnum(RAT_HW_RADIO_REQ);
  /** BLonRailAudioTypeEnum constant for ratHwRadioEnd. */
  public static final BLonRailAudioTypeEnum ratHwRadioEnd = new BLonRailAudioTypeEnum(RAT_HW_RADIO_END);
  /** BLonRailAudioTypeEnum constant for ratHwPaReq. */
  public static final BLonRailAudioTypeEnum ratHwPaReq = new BLonRailAudioTypeEnum(RAT_HW_PA_REQ);
  /** BLonRailAudioTypeEnum constant for ratHwPaEnd. */
  public static final BLonRailAudioTypeEnum ratHwPaEnd = new BLonRailAudioTypeEnum(RAT_HW_PA_END);
  /** BLonRailAudioTypeEnum constant for ratSwPaReq. */
  public static final BLonRailAudioTypeEnum ratSwPaReq = new BLonRailAudioTypeEnum(RAT_SW_PA_REQ);
  /** BLonRailAudioTypeEnum constant for ratSwPaEnd. */
  public static final BLonRailAudioTypeEnum ratSwPaEnd = new BLonRailAudioTypeEnum(RAT_SW_PA_END);
  /** BLonRailAudioTypeEnum constant for ratSwPaOrReq. */
  public static final BLonRailAudioTypeEnum ratSwPaOrReq = new BLonRailAudioTypeEnum(RAT_SW_PA_OR_REQ);
  /** BLonRailAudioTypeEnum constant for ratSwPaOrEnd. */
  public static final BLonRailAudioTypeEnum ratSwPaOrEnd = new BLonRailAudioTypeEnum(RAT_SW_PA_OR_END);
  /** BLonRailAudioTypeEnum constant for ratPauReq. */
  public static final BLonRailAudioTypeEnum ratPauReq = new BLonRailAudioTypeEnum(RAT_PAU_REQ);
  /** BLonRailAudioTypeEnum constant for ratPauAccept. */
  public static final BLonRailAudioTypeEnum ratPauAccept = new BLonRailAudioTypeEnum(RAT_PAU_ACCEPT);
  /** BLonRailAudioTypeEnum constant for ratPauCall. */
  public static final BLonRailAudioTypeEnum ratPauCall = new BLonRailAudioTypeEnum(RAT_PAU_CALL);
  /** BLonRailAudioTypeEnum constant for ratPauEnd. */
  public static final BLonRailAudioTypeEnum ratPauEnd = new BLonRailAudioTypeEnum(RAT_PAU_END);
  /** BLonRailAudioTypeEnum constant for ratEntertReq. */
  public static final BLonRailAudioTypeEnum ratEntertReq = new BLonRailAudioTypeEnum(RAT_ENTERT_REQ);
  /** BLonRailAudioTypeEnum constant for ratEntertEnd. */
  public static final BLonRailAudioTypeEnum ratEntertEnd = new BLonRailAudioTypeEnum(RAT_ENTERT_END);
  /** BLonRailAudioTypeEnum constant for ratNul. */
  public static final BLonRailAudioTypeEnum ratNul = new BLonRailAudioTypeEnum(RAT_NUL);

  /** Factory method with ordinal. */
  public static BLonRailAudioTypeEnum make(int ordinal)
  {
    return (BLonRailAudioTypeEnum)ratIcReq.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonRailAudioTypeEnum make(String tag)
  {
    return (BLonRailAudioTypeEnum)ratIcReq.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonRailAudioTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonRailAudioTypeEnum DEFAULT = ratNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonRailAudioTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
