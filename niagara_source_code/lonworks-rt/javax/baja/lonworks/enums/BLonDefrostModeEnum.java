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
 * The BLonDefrostModeEnum class provides enumeration for SNVT_defr_mode
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:25 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "dfmModeAmbient", ordinal = 0),
    @Range(value = "dfmModeForced", ordinal = 1),
    @Range(value = "dfmModeSync", ordinal = 2),
    @Range(value = "dfmNul", ordinal = -1)
  }
)
public final class BLonDefrostModeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonDefrostModeEnum(3862996463)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for dfmModeAmbient. */
  public static final int DFM_MODE_AMBIENT = 0;
  /** Ordinal value for dfmModeForced. */
  public static final int DFM_MODE_FORCED = 1;
  /** Ordinal value for dfmModeSync. */
  public static final int DFM_MODE_SYNC = 2;
  /** Ordinal value for dfmNul. */
  public static final int DFM_NUL = -1;

  /** BLonDefrostModeEnum constant for dfmModeAmbient. */
  public static final BLonDefrostModeEnum dfmModeAmbient = new BLonDefrostModeEnum(DFM_MODE_AMBIENT);
  /** BLonDefrostModeEnum constant for dfmModeForced. */
  public static final BLonDefrostModeEnum dfmModeForced = new BLonDefrostModeEnum(DFM_MODE_FORCED);
  /** BLonDefrostModeEnum constant for dfmModeSync. */
  public static final BLonDefrostModeEnum dfmModeSync = new BLonDefrostModeEnum(DFM_MODE_SYNC);
  /** BLonDefrostModeEnum constant for dfmNul. */
  public static final BLonDefrostModeEnum dfmNul = new BLonDefrostModeEnum(DFM_NUL);

  /** Factory method with ordinal. */
  public static BLonDefrostModeEnum make(int ordinal)
  {
    return (BLonDefrostModeEnum)dfmModeAmbient.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonDefrostModeEnum make(String tag)
  {
    return (BLonDefrostModeEnum)dfmModeAmbient.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonDefrostModeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonDefrostModeEnum DEFAULT = dfmModeAmbient;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDefrostModeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
