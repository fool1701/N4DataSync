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
 * The BLonInterpEnum class provides enumeration for SNVT_trans_table
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:31 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "ipLinear", ordinal = 0),
    @Range(value = "ipCubicSpline", ordinal = 1),
    @Range(value = "ipReserved", ordinal = 2),
    @Range(value = "ipNul", ordinal = -1)
  }
)
public final class BLonInterpEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonInterpEnum(1008722496)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ipLinear. */
  public static final int IP_LINEAR = 0;
  /** Ordinal value for ipCubicSpline. */
  public static final int IP_CUBIC_SPLINE = 1;
  /** Ordinal value for ipReserved. */
  public static final int IP_RESERVED = 2;
  /** Ordinal value for ipNul. */
  public static final int IP_NUL = -1;

  /** BLonInterpEnum constant for ipLinear. */
  public static final BLonInterpEnum ipLinear = new BLonInterpEnum(IP_LINEAR);
  /** BLonInterpEnum constant for ipCubicSpline. */
  public static final BLonInterpEnum ipCubicSpline = new BLonInterpEnum(IP_CUBIC_SPLINE);
  /** BLonInterpEnum constant for ipReserved. */
  public static final BLonInterpEnum ipReserved = new BLonInterpEnum(IP_RESERVED);
  /** BLonInterpEnum constant for ipNul. */
  public static final BLonInterpEnum ipNul = new BLonInterpEnum(IP_NUL);

  /** Factory method with ordinal. */
  public static BLonInterpEnum make(int ordinal)
  {
    return (BLonInterpEnum)ipLinear.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonInterpEnum make(String tag)
  {
    return (BLonInterpEnum)ipLinear.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonInterpEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonInterpEnum DEFAULT = ipLinear;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonInterpEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
