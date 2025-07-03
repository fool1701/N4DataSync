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
 * The BLonDefrostStateEnum class provides enumeration for SNVT_defr_state
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "dfsStandby", ordinal = 0),
    @Range(value = "dfsPumpdown", ordinal = 1),
    @Range(value = "dfsDefrost", ordinal = 2),
    @Range(value = "dfsDraindown", ordinal = 3),
    @Range(value = "dfsInjectDly", ordinal = 4),
    @Range(value = "dfsNul", ordinal = -1)
  }
)
public final class BLonDefrostStateEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonDefrostStateEnum(3592406167)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for dfsStandby. */
  public static final int DFS_STANDBY = 0;
  /** Ordinal value for dfsPumpdown. */
  public static final int DFS_PUMPDOWN = 1;
  /** Ordinal value for dfsDefrost. */
  public static final int DFS_DEFROST = 2;
  /** Ordinal value for dfsDraindown. */
  public static final int DFS_DRAINDOWN = 3;
  /** Ordinal value for dfsInjectDly. */
  public static final int DFS_INJECT_DLY = 4;
  /** Ordinal value for dfsNul. */
  public static final int DFS_NUL = -1;

  /** BLonDefrostStateEnum constant for dfsStandby. */
  public static final BLonDefrostStateEnum dfsStandby = new BLonDefrostStateEnum(DFS_STANDBY);
  /** BLonDefrostStateEnum constant for dfsPumpdown. */
  public static final BLonDefrostStateEnum dfsPumpdown = new BLonDefrostStateEnum(DFS_PUMPDOWN);
  /** BLonDefrostStateEnum constant for dfsDefrost. */
  public static final BLonDefrostStateEnum dfsDefrost = new BLonDefrostStateEnum(DFS_DEFROST);
  /** BLonDefrostStateEnum constant for dfsDraindown. */
  public static final BLonDefrostStateEnum dfsDraindown = new BLonDefrostStateEnum(DFS_DRAINDOWN);
  /** BLonDefrostStateEnum constant for dfsInjectDly. */
  public static final BLonDefrostStateEnum dfsInjectDly = new BLonDefrostStateEnum(DFS_INJECT_DLY);
  /** BLonDefrostStateEnum constant for dfsNul. */
  public static final BLonDefrostStateEnum dfsNul = new BLonDefrostStateEnum(DFS_NUL);

  /** Factory method with ordinal. */
  public static BLonDefrostStateEnum make(int ordinal)
  {
    return (BLonDefrostStateEnum)dfsStandby.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonDefrostStateEnum make(String tag)
  {
    return (BLonDefrostStateEnum)dfsStandby.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonDefrostStateEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonDefrostStateEnum DEFAULT = dfsStandby;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDefrostStateEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
