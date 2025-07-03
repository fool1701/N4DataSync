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
 * The BLonControlRespEnum class provides enumeration for SNVT_ctrl_resp
 *
 * @author    Robert Adams
 * @creation  9 Nov 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "ctrlrNo", ordinal = 0),
    @Range(value = "ctrlrPend", ordinal = 1),
    @Range(value = "ctrlrRel", ordinal = 2),
    @Range(value = "ctrlrQuery", ordinal = 3),
    @Range(value = "ctrlrRes", ordinal = 4),
    @Range(value = "ctrlrErr", ordinal = 5),
    @Range(value = "ctrlrNul", ordinal = -1)
  },
  defaultValue = "ctrlrNul"
)
public final class BLonControlRespEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonControlRespEnum(708000396)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ctrlrNo. */
  public static final int CTRLR_NO = 0;
  /** Ordinal value for ctrlrPend. */
  public static final int CTRLR_PEND = 1;
  /** Ordinal value for ctrlrRel. */
  public static final int CTRLR_REL = 2;
  /** Ordinal value for ctrlrQuery. */
  public static final int CTRLR_QUERY = 3;
  /** Ordinal value for ctrlrRes. */
  public static final int CTRLR_RES = 4;
  /** Ordinal value for ctrlrErr. */
  public static final int CTRLR_ERR = 5;
  /** Ordinal value for ctrlrNul. */
  public static final int CTRLR_NUL = -1;

  /** BLonControlRespEnum constant for ctrlrNo. */
  public static final BLonControlRespEnum ctrlrNo = new BLonControlRespEnum(CTRLR_NO);
  /** BLonControlRespEnum constant for ctrlrPend. */
  public static final BLonControlRespEnum ctrlrPend = new BLonControlRespEnum(CTRLR_PEND);
  /** BLonControlRespEnum constant for ctrlrRel. */
  public static final BLonControlRespEnum ctrlrRel = new BLonControlRespEnum(CTRLR_REL);
  /** BLonControlRespEnum constant for ctrlrQuery. */
  public static final BLonControlRespEnum ctrlrQuery = new BLonControlRespEnum(CTRLR_QUERY);
  /** BLonControlRespEnum constant for ctrlrRes. */
  public static final BLonControlRespEnum ctrlrRes = new BLonControlRespEnum(CTRLR_RES);
  /** BLonControlRespEnum constant for ctrlrErr. */
  public static final BLonControlRespEnum ctrlrErr = new BLonControlRespEnum(CTRLR_ERR);
  /** BLonControlRespEnum constant for ctrlrNul. */
  public static final BLonControlRespEnum ctrlrNul = new BLonControlRespEnum(CTRLR_NUL);

  /** Factory method with ordinal. */
  public static BLonControlRespEnum make(int ordinal)
  {
    return (BLonControlRespEnum)ctrlrNo.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonControlRespEnum make(String tag)
  {
    return (BLonControlRespEnum)ctrlrNo.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonControlRespEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonControlRespEnum DEFAULT = ctrlrNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonControlRespEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
