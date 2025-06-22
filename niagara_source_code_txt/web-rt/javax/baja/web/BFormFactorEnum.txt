/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A frozen enumeration for the different form factors.
 *
 * @see BIFormFactor
 * @see BIFormFactorMax
 * @see BIFormFactorCompact
 * @see BIFormFactorMini
 *
 * @author Gareth Johnson on 07/07/2014
 * @since Niagara 4.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("max"),
    @Range("compact"),
    @Range("mini")
  }
)
public final class BFormFactorEnum
    extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BFormFactorEnum(1643989996)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for max. */
  public static final int MAX = 0;
  /** Ordinal value for compact. */
  public static final int COMPACT = 1;
  /** Ordinal value for mini. */
  public static final int MINI = 2;

  /** BFormFactorEnum constant for max. */
  public static final BFormFactorEnum max = new BFormFactorEnum(MAX);
  /** BFormFactorEnum constant for compact. */
  public static final BFormFactorEnum compact = new BFormFactorEnum(COMPACT);
  /** BFormFactorEnum constant for mini. */
  public static final BFormFactorEnum mini = new BFormFactorEnum(MINI);

  /** Factory method with ordinal. */
  public static BFormFactorEnum make(int ordinal)
  {
    return (BFormFactorEnum)max.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BFormFactorEnum make(String tag)
  {
    return (BFormFactorEnum)max.getRange().get(tag);
  }

  /** Private constructor. */
  private BFormFactorEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BFormFactorEnum DEFAULT = max;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFormFactorEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static final String key = "formFactor";
}
