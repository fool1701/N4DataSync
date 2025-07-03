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
 * The BLonStateEnum class provides enumeration for the state
 * attribute of SNVT_switch.
 *
 * @author    Sean Morton
 * @creation  20 July 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:38 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "stOff", ordinal = 0),
    @Range(value = "stOn", ordinal = 1),
    @Range(value = "stNul", ordinal = -1)
  }
)
public final class BLonStateEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonStateEnum(3423961423)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for stOff. */
  public static final int ST_OFF = 0;
  /** Ordinal value for stOn. */
  public static final int ST_ON = 1;
  /** Ordinal value for stNul. */
  public static final int ST_NUL = -1;

  /** BLonStateEnum constant for stOff. */
  public static final BLonStateEnum stOff = new BLonStateEnum(ST_OFF);
  /** BLonStateEnum constant for stOn. */
  public static final BLonStateEnum stOn = new BLonStateEnum(ST_ON);
  /** BLonStateEnum constant for stNul. */
  public static final BLonStateEnum stNul = new BLonStateEnum(ST_NUL);

  /** Factory method with ordinal. */
  public static BLonStateEnum make(int ordinal)
  {
    return (BLonStateEnum)stOff.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonStateEnum make(String tag)
  {
    return (BLonStateEnum)stOff.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonStateEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonStateEnum DEFAULT = stOff;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonStateEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
