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
 * The BLonLearnModeEnum class provides enumeration for SNVT_preset
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:31 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "lnRecall", ordinal = 0),
    @Range(value = "lnLearnCurrent", ordinal = 1),
    @Range(value = "lnLearnValue", ordinal = 2),
    @Range(value = "lnReportValue", ordinal = 3),
    @Range(value = "lnNul", ordinal = -1)
  }
)
public final class BLonLearnModeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonLearnModeEnum(3479622720)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for lnRecall. */
  public static final int LN_RECALL = 0;
  /** Ordinal value for lnLearnCurrent. */
  public static final int LN_LEARN_CURRENT = 1;
  /** Ordinal value for lnLearnValue. */
  public static final int LN_LEARN_VALUE = 2;
  /** Ordinal value for lnReportValue. */
  public static final int LN_REPORT_VALUE = 3;
  /** Ordinal value for lnNul. */
  public static final int LN_NUL = -1;

  /** BLonLearnModeEnum constant for lnRecall. */
  public static final BLonLearnModeEnum lnRecall = new BLonLearnModeEnum(LN_RECALL);
  /** BLonLearnModeEnum constant for lnLearnCurrent. */
  public static final BLonLearnModeEnum lnLearnCurrent = new BLonLearnModeEnum(LN_LEARN_CURRENT);
  /** BLonLearnModeEnum constant for lnLearnValue. */
  public static final BLonLearnModeEnum lnLearnValue = new BLonLearnModeEnum(LN_LEARN_VALUE);
  /** BLonLearnModeEnum constant for lnReportValue. */
  public static final BLonLearnModeEnum lnReportValue = new BLonLearnModeEnum(LN_REPORT_VALUE);
  /** BLonLearnModeEnum constant for lnNul. */
  public static final BLonLearnModeEnum lnNul = new BLonLearnModeEnum(LN_NUL);

  /** Factory method with ordinal. */
  public static BLonLearnModeEnum make(int ordinal)
  {
    return (BLonLearnModeEnum)lnRecall.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonLearnModeEnum make(String tag)
  {
    return (BLonLearnModeEnum)lnRecall.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonLearnModeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonLearnModeEnum DEFAULT = lnRecall;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonLearnModeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
