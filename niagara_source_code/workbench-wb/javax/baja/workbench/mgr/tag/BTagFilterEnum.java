/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr.tag;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * @author    Andrew Saunders
 * @creation 3/22/14
 * @since Niagara 4.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("showAll"),
    @Range("validOnly"),
    @Range("bestOnly")
  }
)
public final class BTagFilterEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.tag.BTagFilterEnum(1383457279)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for showAll. */
  public static final int SHOW_ALL = 0;
  /** Ordinal value for validOnly. */
  public static final int VALID_ONLY = 1;
  /** Ordinal value for bestOnly. */
  public static final int BEST_ONLY = 2;

  /** BTagFilterEnum constant for showAll. */
  public static final BTagFilterEnum showAll = new BTagFilterEnum(SHOW_ALL);
  /** BTagFilterEnum constant for validOnly. */
  public static final BTagFilterEnum validOnly = new BTagFilterEnum(VALID_ONLY);
  /** BTagFilterEnum constant for bestOnly. */
  public static final BTagFilterEnum bestOnly = new BTagFilterEnum(BEST_ONLY);

  /** Factory method with ordinal. */
  public static BTagFilterEnum make(int ordinal)
  {
    return (BTagFilterEnum)showAll.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BTagFilterEnum make(String tag)
  {
    return (BTagFilterEnum)showAll.getRange().get(tag);
  }

  /** Private constructor. */
  private BTagFilterEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BTagFilterEnum DEFAULT = showAll;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagFilterEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
