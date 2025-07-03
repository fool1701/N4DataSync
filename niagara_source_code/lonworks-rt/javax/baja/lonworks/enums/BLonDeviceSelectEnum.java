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
 * The BLonDeviceSelectEnum class provides enumeration for SNVT_dev_status,
 * SNVT_dev_fault, SNVT_dev_maint
 *
 * @author    Robert Adams
 * @creation  9 Nov 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "dvPumpCtrl", ordinal = 0),
    @Range(value = "dvValvePos", ordinal = 1),
    @Range(value = "dvNul", ordinal = -1)
  },
  defaultValue = "dvNul"
)
public final class BLonDeviceSelectEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonDeviceSelectEnum(3394962057)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for dvPumpCtrl. */
  public static final int DV_PUMP_CTRL = 0;
  /** Ordinal value for dvValvePos. */
  public static final int DV_VALVE_POS = 1;
  /** Ordinal value for dvNul. */
  public static final int DV_NUL = -1;

  /** BLonDeviceSelectEnum constant for dvPumpCtrl. */
  public static final BLonDeviceSelectEnum dvPumpCtrl = new BLonDeviceSelectEnum(DV_PUMP_CTRL);
  /** BLonDeviceSelectEnum constant for dvValvePos. */
  public static final BLonDeviceSelectEnum dvValvePos = new BLonDeviceSelectEnum(DV_VALVE_POS);
  /** BLonDeviceSelectEnum constant for dvNul. */
  public static final BLonDeviceSelectEnum dvNul = new BLonDeviceSelectEnum(DV_NUL);

  /** Factory method with ordinal. */
  public static BLonDeviceSelectEnum make(int ordinal)
  {
    return (BLonDeviceSelectEnum)dvPumpCtrl.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonDeviceSelectEnum make(String tag)
  {
    return (BLonDeviceSelectEnum)dvPumpCtrl.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonDeviceSelectEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonDeviceSelectEnum DEFAULT = dvNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDeviceSelectEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
