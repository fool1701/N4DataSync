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
 * The BLonDefrostTermEnum class provides enumeration for SNVT_defr_term
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "dfrTermTemp", ordinal = 0),
    @Range(value = "dfrTermTime", ordinal = 1),
    @Range(value = "dfrTermFirst", ordinal = 2),
    @Range(value = "dfrTermLast", ordinal = 3),
    @Range(value = "dfrNul", ordinal = -1)
  }
)
public final class BLonDefrostTermEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonDefrostTermEnum(4169635653)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for dfrTermTemp. */
  public static final int DFR_TERM_TEMP = 0;
  /** Ordinal value for dfrTermTime. */
  public static final int DFR_TERM_TIME = 1;
  /** Ordinal value for dfrTermFirst. */
  public static final int DFR_TERM_FIRST = 2;
  /** Ordinal value for dfrTermLast. */
  public static final int DFR_TERM_LAST = 3;
  /** Ordinal value for dfrNul. */
  public static final int DFR_NUL = -1;

  /** BLonDefrostTermEnum constant for dfrTermTemp. */
  public static final BLonDefrostTermEnum dfrTermTemp = new BLonDefrostTermEnum(DFR_TERM_TEMP);
  /** BLonDefrostTermEnum constant for dfrTermTime. */
  public static final BLonDefrostTermEnum dfrTermTime = new BLonDefrostTermEnum(DFR_TERM_TIME);
  /** BLonDefrostTermEnum constant for dfrTermFirst. */
  public static final BLonDefrostTermEnum dfrTermFirst = new BLonDefrostTermEnum(DFR_TERM_FIRST);
  /** BLonDefrostTermEnum constant for dfrTermLast. */
  public static final BLonDefrostTermEnum dfrTermLast = new BLonDefrostTermEnum(DFR_TERM_LAST);
  /** BLonDefrostTermEnum constant for dfrNul. */
  public static final BLonDefrostTermEnum dfrNul = new BLonDefrostTermEnum(DFR_NUL);

  /** Factory method with ordinal. */
  public static BLonDefrostTermEnum make(int ordinal)
  {
    return (BLonDefrostTermEnum)dfrTermTemp.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonDefrostTermEnum make(String tag)
  {
    return (BLonDefrostTermEnum)dfrTermTemp.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonDefrostTermEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonDefrostTermEnum DEFAULT = dfrTermTemp;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDefrostTermEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
