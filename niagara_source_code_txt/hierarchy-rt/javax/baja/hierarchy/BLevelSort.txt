/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "none", ordinal = 0),
    @Range(value = "ascending", ordinal = 1),
    @Range(value = "descending", ordinal = 2)
  }
)
public final class BLevelSort
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BLevelSort(2012815866)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for ascending. */
  public static final int ASCENDING = 1;
  /** Ordinal value for descending. */
  public static final int DESCENDING = 2;

  /** BLevelSort constant for none. */
  public static final BLevelSort none = new BLevelSort(NONE);
  /** BLevelSort constant for ascending. */
  public static final BLevelSort ascending = new BLevelSort(ASCENDING);
  /** BLevelSort constant for descending. */
  public static final BLevelSort descending = new BLevelSort(DESCENDING);

  /** Factory method with ordinal. */
  public static BLevelSort make(int ordinal)
  {
    return (BLevelSort)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLevelSort make(String tag)
  {
    return (BLevelSort)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BLevelSort(int ordinal)
  {
    super(ordinal);
  }

  public static final BLevelSort DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLevelSort.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
