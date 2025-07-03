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
 * The BLonCamFuncEnum class provides enumeration for SNVT_pos_ctrl
 *
 * @author    Robert Adams
 * @creation  9 Nov 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "cmfRel", ordinal = 0),
    @Range(value = "cmfTour", ordinal = 1),
    @Range(value = "cmfAbs", ordinal = 2),
    @Range(value = "cmfNul", ordinal = -1)
  },
  defaultValue = "cmfNul"
)
public final class BLonCamFuncEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonCamFuncEnum(2058643208)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for cmfRel. */
  public static final int CMF_REL = 0;
  /** Ordinal value for cmfTour. */
  public static final int CMF_TOUR = 1;
  /** Ordinal value for cmfAbs. */
  public static final int CMF_ABS = 2;
  /** Ordinal value for cmfNul. */
  public static final int CMF_NUL = -1;

  /** BLonCamFuncEnum constant for cmfRel. */
  public static final BLonCamFuncEnum cmfRel = new BLonCamFuncEnum(CMF_REL);
  /** BLonCamFuncEnum constant for cmfTour. */
  public static final BLonCamFuncEnum cmfTour = new BLonCamFuncEnum(CMF_TOUR);
  /** BLonCamFuncEnum constant for cmfAbs. */
  public static final BLonCamFuncEnum cmfAbs = new BLonCamFuncEnum(CMF_ABS);
  /** BLonCamFuncEnum constant for cmfNul. */
  public static final BLonCamFuncEnum cmfNul = new BLonCamFuncEnum(CMF_NUL);

  /** Factory method with ordinal. */
  public static BLonCamFuncEnum make(int ordinal)
  {
    return (BLonCamFuncEnum)cmfRel.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonCamFuncEnum make(String tag)
  {
    return (BLonCamFuncEnum)cmfRel.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonCamFuncEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonCamFuncEnum DEFAULT = cmfNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonCamFuncEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
