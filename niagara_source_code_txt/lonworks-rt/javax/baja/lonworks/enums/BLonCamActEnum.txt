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
 * The BLonCamActEnum class provides enumeration for SNVT_pos_ctrl
 *
 * @author    Robert Adams
 * @creation  9 Nov 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "cmaSave", ordinal = 0),
    @Range(value = "cmaCall", ordinal = 1),
    @Range(value = "cmaRead", ordinal = 2),
    @Range(value = "cmaNul", ordinal = -1)
  },
  defaultValue = "cmaNul"
)
public final class BLonCamActEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonCamActEnum(165177453)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for cmaSave. */
  public static final int CMA_SAVE = 0;
  /** Ordinal value for cmaCall. */
  public static final int CMA_CALL = 1;
  /** Ordinal value for cmaRead. */
  public static final int CMA_READ = 2;
  /** Ordinal value for cmaNul. */
  public static final int CMA_NUL = -1;

  /** BLonCamActEnum constant for cmaSave. */
  public static final BLonCamActEnum cmaSave = new BLonCamActEnum(CMA_SAVE);
  /** BLonCamActEnum constant for cmaCall. */
  public static final BLonCamActEnum cmaCall = new BLonCamActEnum(CMA_CALL);
  /** BLonCamActEnum constant for cmaRead. */
  public static final BLonCamActEnum cmaRead = new BLonCamActEnum(CMA_READ);
  /** BLonCamActEnum constant for cmaNul. */
  public static final BLonCamActEnum cmaNul = new BLonCamActEnum(CMA_NUL);

  /** Factory method with ordinal. */
  public static BLonCamActEnum make(int ordinal)
  {
    return (BLonCamActEnum)cmaSave.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonCamActEnum make(String tag)
  {
    return (BLonCamActEnum)cmaSave.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonCamActEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonCamActEnum DEFAULT = cmaNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonCamActEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
