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
 * The BLonPriorityLevelEnum class provides enumeration for
 * the level of priority of a SNVT_alarm
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:34 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "prLevel0", ordinal = 0),
    @Range(value = "prLevel1", ordinal = 1),
    @Range(value = "prLevel2", ordinal = 2),
    @Range(value = "prLevel3", ordinal = 3),
    @Range(value = "pr1", ordinal = 4),
    @Range(value = "pr2", ordinal = 5),
    @Range(value = "pr3", ordinal = 6),
    @Range(value = "pr4", ordinal = 7),
    @Range(value = "pr6", ordinal = 8),
    @Range(value = "pr8", ordinal = 9),
    @Range(value = "pr10", ordinal = 10),
    @Range(value = "pr16", ordinal = 11),
    @Range(value = "prNul", ordinal = -1)
  }
)
public final class BLonPriorityLevelEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonPriorityLevelEnum(2055006211)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for prLevel0. */
  public static final int PR_LEVEL_0 = 0;
  /** Ordinal value for prLevel1. */
  public static final int PR_LEVEL_1 = 1;
  /** Ordinal value for prLevel2. */
  public static final int PR_LEVEL_2 = 2;
  /** Ordinal value for prLevel3. */
  public static final int PR_LEVEL_3 = 3;
  /** Ordinal value for pr1. */
  public static final int PR_1 = 4;
  /** Ordinal value for pr2. */
  public static final int PR_2 = 5;
  /** Ordinal value for pr3. */
  public static final int PR_3 = 6;
  /** Ordinal value for pr4. */
  public static final int PR_4 = 7;
  /** Ordinal value for pr6. */
  public static final int PR_6 = 8;
  /** Ordinal value for pr8. */
  public static final int PR_8 = 9;
  /** Ordinal value for pr10. */
  public static final int PR_10 = 10;
  /** Ordinal value for pr16. */
  public static final int PR_16 = 11;
  /** Ordinal value for prNul. */
  public static final int PR_NUL = -1;

  /** BLonPriorityLevelEnum constant for prLevel0. */
  public static final BLonPriorityLevelEnum prLevel0 = new BLonPriorityLevelEnum(PR_LEVEL_0);
  /** BLonPriorityLevelEnum constant for prLevel1. */
  public static final BLonPriorityLevelEnum prLevel1 = new BLonPriorityLevelEnum(PR_LEVEL_1);
  /** BLonPriorityLevelEnum constant for prLevel2. */
  public static final BLonPriorityLevelEnum prLevel2 = new BLonPriorityLevelEnum(PR_LEVEL_2);
  /** BLonPriorityLevelEnum constant for prLevel3. */
  public static final BLonPriorityLevelEnum prLevel3 = new BLonPriorityLevelEnum(PR_LEVEL_3);
  /** BLonPriorityLevelEnum constant for pr1. */
  public static final BLonPriorityLevelEnum pr1 = new BLonPriorityLevelEnum(PR_1);
  /** BLonPriorityLevelEnum constant for pr2. */
  public static final BLonPriorityLevelEnum pr2 = new BLonPriorityLevelEnum(PR_2);
  /** BLonPriorityLevelEnum constant for pr3. */
  public static final BLonPriorityLevelEnum pr3 = new BLonPriorityLevelEnum(PR_3);
  /** BLonPriorityLevelEnum constant for pr4. */
  public static final BLonPriorityLevelEnum pr4 = new BLonPriorityLevelEnum(PR_4);
  /** BLonPriorityLevelEnum constant for pr6. */
  public static final BLonPriorityLevelEnum pr6 = new BLonPriorityLevelEnum(PR_6);
  /** BLonPriorityLevelEnum constant for pr8. */
  public static final BLonPriorityLevelEnum pr8 = new BLonPriorityLevelEnum(PR_8);
  /** BLonPriorityLevelEnum constant for pr10. */
  public static final BLonPriorityLevelEnum pr10 = new BLonPriorityLevelEnum(PR_10);
  /** BLonPriorityLevelEnum constant for pr16. */
  public static final BLonPriorityLevelEnum pr16 = new BLonPriorityLevelEnum(PR_16);
  /** BLonPriorityLevelEnum constant for prNul. */
  public static final BLonPriorityLevelEnum prNul = new BLonPriorityLevelEnum(PR_NUL);

  /** Factory method with ordinal. */
  public static BLonPriorityLevelEnum make(int ordinal)
  {
    return (BLonPriorityLevelEnum)prLevel0.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonPriorityLevelEnum make(String tag)
  {
    return (BLonPriorityLevelEnum)prLevel0.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonPriorityLevelEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonPriorityLevelEnum DEFAULT = prLevel0;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonPriorityLevelEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
