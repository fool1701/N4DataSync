/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BCountTransition is a BEnum specifying changeOfState edge to count.
 *
 * @author    Andy Saunders
 * @creation  22 Nov 2004
 * @version   $Revision: 2$ $Date: 3/23/05 11:37:10 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("toActive"),
    @Range("toInactive"),
    @Range("both")
  }
)
public final class BCountTransition
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.enums.BCountTransition(486585103)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for toActive. */
  public static final int TO_ACTIVE = 0;
  /** Ordinal value for toInactive. */
  public static final int TO_INACTIVE = 1;
  /** Ordinal value for both. */
  public static final int BOTH = 2;

  /** BCountTransition constant for toActive. */
  public static final BCountTransition toActive = new BCountTransition(TO_ACTIVE);
  /** BCountTransition constant for toInactive. */
  public static final BCountTransition toInactive = new BCountTransition(TO_INACTIVE);
  /** BCountTransition constant for both. */
  public static final BCountTransition both = new BCountTransition(BOTH);

  /** Factory method with ordinal. */
  public static BCountTransition make(int ordinal)
  {
    return (BCountTransition)toActive.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BCountTransition make(String tag)
  {
    return (BCountTransition)toActive.getRange().get(tag);
  }

  /** Private constructor. */
  private BCountTransition(int ordinal)
  {
    super(ordinal);
  }

  public static final BCountTransition DEFAULT = toActive;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCountTransition.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
