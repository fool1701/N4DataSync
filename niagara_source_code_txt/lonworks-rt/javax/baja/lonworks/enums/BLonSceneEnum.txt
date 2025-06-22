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
 * The BLonSceneEnum class provides enumeration for SNVT_scene
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:37 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "scRecall", ordinal = 0),
    @Range(value = "scLearn", ordinal = 1),
    @Range(value = "scDisplay", ordinal = 2),
    @Range(value = "scGroupOff", ordinal = 3),
    @Range(value = "scGroupOn", ordinal = 4),
    @Range(value = "scStatusOff", ordinal = 5),
    @Range(value = "scStatusOn", ordinal = 6),
    @Range(value = "scStatusMixed", ordinal = 7),
    @Range(value = "scGroupStatus", ordinal = 8),
    @Range(value = "scFlick", ordinal = 9),
    @Range(value = "scTimeout", ordinal = 10),
    @Range(value = "scTimeoutFlick", ordinal = 11),
    @Range(value = "scDelayoff", ordinal = 12),
    @Range(value = "scDelayoffFlick", ordinal = 13),
    @Range(value = "scDelayon", ordinal = 14),
    @Range(value = "scEnableGroup", ordinal = 15),
    @Range(value = "scDisableGroup", ordinal = 16),
    @Range(value = "scCleanon", ordinal = 17),
    @Range(value = "scCleanoff", ordinal = 18),
    @Range(value = "scWink", ordinal = 19),
    @Range(value = "scReset", ordinal = 20),
    @Range(value = "scMode1", ordinal = 21),
    @Range(value = "scMode2", ordinal = 22),
    @Range(value = "scMode3", ordinal = 23),
    @Range(value = "scNul", ordinal = -1)
  }
)
public final class BLonSceneEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonSceneEnum(3488844461)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for scRecall. */
  public static final int SC_RECALL = 0;
  /** Ordinal value for scLearn. */
  public static final int SC_LEARN = 1;
  /** Ordinal value for scDisplay. */
  public static final int SC_DISPLAY = 2;
  /** Ordinal value for scGroupOff. */
  public static final int SC_GROUP_OFF = 3;
  /** Ordinal value for scGroupOn. */
  public static final int SC_GROUP_ON = 4;
  /** Ordinal value for scStatusOff. */
  public static final int SC_STATUS_OFF = 5;
  /** Ordinal value for scStatusOn. */
  public static final int SC_STATUS_ON = 6;
  /** Ordinal value for scStatusMixed. */
  public static final int SC_STATUS_MIXED = 7;
  /** Ordinal value for scGroupStatus. */
  public static final int SC_GROUP_STATUS = 8;
  /** Ordinal value for scFlick. */
  public static final int SC_FLICK = 9;
  /** Ordinal value for scTimeout. */
  public static final int SC_TIMEOUT = 10;
  /** Ordinal value for scTimeoutFlick. */
  public static final int SC_TIMEOUT_FLICK = 11;
  /** Ordinal value for scDelayoff. */
  public static final int SC_DELAYOFF = 12;
  /** Ordinal value for scDelayoffFlick. */
  public static final int SC_DELAYOFF_FLICK = 13;
  /** Ordinal value for scDelayon. */
  public static final int SC_DELAYON = 14;
  /** Ordinal value for scEnableGroup. */
  public static final int SC_ENABLE_GROUP = 15;
  /** Ordinal value for scDisableGroup. */
  public static final int SC_DISABLE_GROUP = 16;
  /** Ordinal value for scCleanon. */
  public static final int SC_CLEANON = 17;
  /** Ordinal value for scCleanoff. */
  public static final int SC_CLEANOFF = 18;
  /** Ordinal value for scWink. */
  public static final int SC_WINK = 19;
  /** Ordinal value for scReset. */
  public static final int SC_RESET = 20;
  /** Ordinal value for scMode1. */
  public static final int SC_MODE_1 = 21;
  /** Ordinal value for scMode2. */
  public static final int SC_MODE_2 = 22;
  /** Ordinal value for scMode3. */
  public static final int SC_MODE_3 = 23;
  /** Ordinal value for scNul. */
  public static final int SC_NUL = -1;

  /** BLonSceneEnum constant for scRecall. */
  public static final BLonSceneEnum scRecall = new BLonSceneEnum(SC_RECALL);
  /** BLonSceneEnum constant for scLearn. */
  public static final BLonSceneEnum scLearn = new BLonSceneEnum(SC_LEARN);
  /** BLonSceneEnum constant for scDisplay. */
  public static final BLonSceneEnum scDisplay = new BLonSceneEnum(SC_DISPLAY);
  /** BLonSceneEnum constant for scGroupOff. */
  public static final BLonSceneEnum scGroupOff = new BLonSceneEnum(SC_GROUP_OFF);
  /** BLonSceneEnum constant for scGroupOn. */
  public static final BLonSceneEnum scGroupOn = new BLonSceneEnum(SC_GROUP_ON);
  /** BLonSceneEnum constant for scStatusOff. */
  public static final BLonSceneEnum scStatusOff = new BLonSceneEnum(SC_STATUS_OFF);
  /** BLonSceneEnum constant for scStatusOn. */
  public static final BLonSceneEnum scStatusOn = new BLonSceneEnum(SC_STATUS_ON);
  /** BLonSceneEnum constant for scStatusMixed. */
  public static final BLonSceneEnum scStatusMixed = new BLonSceneEnum(SC_STATUS_MIXED);
  /** BLonSceneEnum constant for scGroupStatus. */
  public static final BLonSceneEnum scGroupStatus = new BLonSceneEnum(SC_GROUP_STATUS);
  /** BLonSceneEnum constant for scFlick. */
  public static final BLonSceneEnum scFlick = new BLonSceneEnum(SC_FLICK);
  /** BLonSceneEnum constant for scTimeout. */
  public static final BLonSceneEnum scTimeout = new BLonSceneEnum(SC_TIMEOUT);
  /** BLonSceneEnum constant for scTimeoutFlick. */
  public static final BLonSceneEnum scTimeoutFlick = new BLonSceneEnum(SC_TIMEOUT_FLICK);
  /** BLonSceneEnum constant for scDelayoff. */
  public static final BLonSceneEnum scDelayoff = new BLonSceneEnum(SC_DELAYOFF);
  /** BLonSceneEnum constant for scDelayoffFlick. */
  public static final BLonSceneEnum scDelayoffFlick = new BLonSceneEnum(SC_DELAYOFF_FLICK);
  /** BLonSceneEnum constant for scDelayon. */
  public static final BLonSceneEnum scDelayon = new BLonSceneEnum(SC_DELAYON);
  /** BLonSceneEnum constant for scEnableGroup. */
  public static final BLonSceneEnum scEnableGroup = new BLonSceneEnum(SC_ENABLE_GROUP);
  /** BLonSceneEnum constant for scDisableGroup. */
  public static final BLonSceneEnum scDisableGroup = new BLonSceneEnum(SC_DISABLE_GROUP);
  /** BLonSceneEnum constant for scCleanon. */
  public static final BLonSceneEnum scCleanon = new BLonSceneEnum(SC_CLEANON);
  /** BLonSceneEnum constant for scCleanoff. */
  public static final BLonSceneEnum scCleanoff = new BLonSceneEnum(SC_CLEANOFF);
  /** BLonSceneEnum constant for scWink. */
  public static final BLonSceneEnum scWink = new BLonSceneEnum(SC_WINK);
  /** BLonSceneEnum constant for scReset. */
  public static final BLonSceneEnum scReset = new BLonSceneEnum(SC_RESET);
  /** BLonSceneEnum constant for scMode1. */
  public static final BLonSceneEnum scMode1 = new BLonSceneEnum(SC_MODE_1);
  /** BLonSceneEnum constant for scMode2. */
  public static final BLonSceneEnum scMode2 = new BLonSceneEnum(SC_MODE_2);
  /** BLonSceneEnum constant for scMode3. */
  public static final BLonSceneEnum scMode3 = new BLonSceneEnum(SC_MODE_3);
  /** BLonSceneEnum constant for scNul. */
  public static final BLonSceneEnum scNul = new BLonSceneEnum(SC_NUL);

  /** Factory method with ordinal. */
  public static BLonSceneEnum make(int ordinal)
  {
    return (BLonSceneEnum)scRecall.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonSceneEnum make(String tag)
  {
    return (BLonSceneEnum)scRecall.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonSceneEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonSceneEnum DEFAULT = scRecall;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonSceneEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
