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
 * The BLonOccupancyEnum class provides enumeration for LonOccupancy 
 * Snvt per SNVT Master List.
 *
 * @author    Robert Adams
 * @creation  29 May 01
 * @version   $Revision: 2$ $Date: 8/9/01 2:23:04 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "occupied", ordinal = 0),
    @Range(value = "unoccupied", ordinal = 1),
    @Range(value = "bypass", ordinal = 2),
    @Range(value = "standby", ordinal = 3),
    @Range(value = "occNull", ordinal = -1)
  },
  defaultValue = "occNull"
)
public final class BLonOccupancyEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonOccupancyEnum(665089954)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for occupied. */
  public static final int OCCUPIED = 0;
  /** Ordinal value for unoccupied. */
  public static final int UNOCCUPIED = 1;
  /** Ordinal value for bypass. */
  public static final int BYPASS = 2;
  /** Ordinal value for standby. */
  public static final int STANDBY = 3;
  /** Ordinal value for occNull. */
  public static final int OCC_NULL = -1;

  /** BLonOccupancyEnum constant for occupied. */
  public static final BLonOccupancyEnum occupied = new BLonOccupancyEnum(OCCUPIED);
  /** BLonOccupancyEnum constant for unoccupied. */
  public static final BLonOccupancyEnum unoccupied = new BLonOccupancyEnum(UNOCCUPIED);
  /** BLonOccupancyEnum constant for bypass. */
  public static final BLonOccupancyEnum bypass = new BLonOccupancyEnum(BYPASS);
  /** BLonOccupancyEnum constant for standby. */
  public static final BLonOccupancyEnum standby = new BLonOccupancyEnum(STANDBY);
  /** BLonOccupancyEnum constant for occNull. */
  public static final BLonOccupancyEnum occNull = new BLonOccupancyEnum(OCC_NULL);

  /** Factory method with ordinal. */
  public static BLonOccupancyEnum make(int ordinal)
  {
    return (BLonOccupancyEnum)occupied.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonOccupancyEnum make(String tag)
  {
    return (BLonOccupancyEnum)occupied.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonOccupancyEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonOccupancyEnum DEFAULT = occNull;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonOccupancyEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
