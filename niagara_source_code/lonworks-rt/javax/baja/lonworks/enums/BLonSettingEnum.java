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
 * The BLonSettingEnum class provides enumeration for 	SNVT_setting
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:38 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "setOff", ordinal = 0),
    @Range(value = "setOn", ordinal = 1),
    @Range(value = "setDown", ordinal = 2),
    @Range(value = "setUp", ordinal = 3),
    @Range(value = "setStop", ordinal = 4),
    @Range(value = "setState", ordinal = 5),
    @Range(value = "setNul", ordinal = -1)
  }
)
public final class BLonSettingEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonSettingEnum(2601084428)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for setOff. */
  public static final int SET_OFF = 0;
  /** Ordinal value for setOn. */
  public static final int SET_ON = 1;
  /** Ordinal value for setDown. */
  public static final int SET_DOWN = 2;
  /** Ordinal value for setUp. */
  public static final int SET_UP = 3;
  /** Ordinal value for setStop. */
  public static final int SET_STOP = 4;
  /** Ordinal value for setState. */
  public static final int SET_STATE = 5;
  /** Ordinal value for setNul. */
  public static final int SET_NUL = -1;

  /** BLonSettingEnum constant for setOff. */
  public static final BLonSettingEnum setOff = new BLonSettingEnum(SET_OFF);
  /** BLonSettingEnum constant for setOn. */
  public static final BLonSettingEnum setOn = new BLonSettingEnum(SET_ON);
  /** BLonSettingEnum constant for setDown. */
  public static final BLonSettingEnum setDown = new BLonSettingEnum(SET_DOWN);
  /** BLonSettingEnum constant for setUp. */
  public static final BLonSettingEnum setUp = new BLonSettingEnum(SET_UP);
  /** BLonSettingEnum constant for setStop. */
  public static final BLonSettingEnum setStop = new BLonSettingEnum(SET_STOP);
  /** BLonSettingEnum constant for setState. */
  public static final BLonSettingEnum setState = new BLonSettingEnum(SET_STATE);
  /** BLonSettingEnum constant for setNul. */
  public static final BLonSettingEnum setNul = new BLonSettingEnum(SET_NUL);

  /** Factory method with ordinal. */
  public static BLonSettingEnum make(int ordinal)
  {
    return (BLonSettingEnum)setOff.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonSettingEnum make(String tag)
  {
    return (BLonSettingEnum)setOff.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonSettingEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonSettingEnum DEFAULT = setOff;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonSettingEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
