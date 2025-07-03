/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BEnum class provides enumeration for SCPT_time_event
 *
 * @author    Robert Adams
 * @creation  14 Jan 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:28 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "endOfList", ordinal = 0),
    @Range(value = "scene", ordinal = 1),
    @Range(value = "mode", ordinal = 2)
  },
  defaultValue = "endOfList"
)
public final class BLonEventModeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonEventModeEnum(1029345323)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for endOfList. */
  public static final int END_OF_LIST = 0;
  /** Ordinal value for scene. */
  public static final int SCENE = 1;
  /** Ordinal value for mode. */
  public static final int MODE = 2;

  /** BLonEventModeEnum constant for endOfList. */
  public static final BLonEventModeEnum endOfList = new BLonEventModeEnum(END_OF_LIST);
  /** BLonEventModeEnum constant for scene. */
  public static final BLonEventModeEnum scene = new BLonEventModeEnum(SCENE);
  /** BLonEventModeEnum constant for mode. */
  public static final BLonEventModeEnum mode = new BLonEventModeEnum(MODE);

  /** Factory method with ordinal. */
  public static BLonEventModeEnum make(int ordinal)
  {
    return (BLonEventModeEnum)endOfList.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonEventModeEnum make(String tag)
  {
    return (BLonEventModeEnum)endOfList.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonEventModeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonEventModeEnum DEFAULT = endOfList;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonEventModeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
