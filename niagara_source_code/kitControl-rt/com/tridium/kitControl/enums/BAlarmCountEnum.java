/**
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * The BAlarmCountEnum class provides enumeration for schedule overriding. 
 *
 * @author    JJ Frankovich        
 * @creation  31 May 07
 * @version   $Revision: $ $Date: $  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("unackedAlarmCount"),
    @Range("openAlarmCount"),
    @Range("inAlarmCount"),
    @Range("totalAlarmCount"),
    @Range("anyCount")
  }
)
public final class BAlarmCountEnum
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BAlarmCountEnum(1148221138)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unackedAlarmCount. */
  public static final int UNACKED_ALARM_COUNT = 0;
  /** Ordinal value for openAlarmCount. */
  public static final int OPEN_ALARM_COUNT = 1;
  /** Ordinal value for inAlarmCount. */
  public static final int IN_ALARM_COUNT = 2;
  /** Ordinal value for totalAlarmCount. */
  public static final int TOTAL_ALARM_COUNT = 3;
  /** Ordinal value for anyCount. */
  public static final int ANY_COUNT = 4;

  /** BAlarmCountEnum constant for unackedAlarmCount. */
  public static final BAlarmCountEnum unackedAlarmCount = new BAlarmCountEnum(UNACKED_ALARM_COUNT);
  /** BAlarmCountEnum constant for openAlarmCount. */
  public static final BAlarmCountEnum openAlarmCount = new BAlarmCountEnum(OPEN_ALARM_COUNT);
  /** BAlarmCountEnum constant for inAlarmCount. */
  public static final BAlarmCountEnum inAlarmCount = new BAlarmCountEnum(IN_ALARM_COUNT);
  /** BAlarmCountEnum constant for totalAlarmCount. */
  public static final BAlarmCountEnum totalAlarmCount = new BAlarmCountEnum(TOTAL_ALARM_COUNT);
  /** BAlarmCountEnum constant for anyCount. */
  public static final BAlarmCountEnum anyCount = new BAlarmCountEnum(ANY_COUNT);

  /** Factory method with ordinal. */
  public static BAlarmCountEnum make(int ordinal)
  {
    return (BAlarmCountEnum)unackedAlarmCount.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAlarmCountEnum make(String tag)
  {
    return (BAlarmCountEnum)unackedAlarmCount.getRange().get(tag);
  }

  /** Private constructor. */
  private BAlarmCountEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BAlarmCountEnum DEFAULT = unackedAlarmCount;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmCountEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
