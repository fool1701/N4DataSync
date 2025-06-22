/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BDegradeBehavior specifies how a binding should behave 
 * when the binding returns true for isDegrated().
 *
 * @author    Brian Frank
 * @creation  21 Feb 06
 * @version   $Revision: 1$ $Date: 2/21/06 2:46:36 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("disable"),
    @Range("hide")
  }
)
public final class BDegradeBehavior
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BDegradeBehavior(1924855682)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for disable. */
  public static final int DISABLE = 1;
  /** Ordinal value for hide. */
  public static final int HIDE = 2;

  /** BDegradeBehavior constant for none. */
  public static final BDegradeBehavior none = new BDegradeBehavior(NONE);
  /** BDegradeBehavior constant for disable. */
  public static final BDegradeBehavior disable = new BDegradeBehavior(DISABLE);
  /** BDegradeBehavior constant for hide. */
  public static final BDegradeBehavior hide = new BDegradeBehavior(HIDE);

  /** Factory method with ordinal. */
  public static BDegradeBehavior make(int ordinal)
  {
    return (BDegradeBehavior)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BDegradeBehavior make(String tag)
  {
    return (BDegradeBehavior)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BDegradeBehavior(int ordinal)
  {
    super(ordinal);
  }

  public static final BDegradeBehavior DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDegradeBehavior.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
}
