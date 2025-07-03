/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BFullPolicy defines the behavior of a history when the
 * capacity is limited, the history is full, and an attempt
 * is made to write new records.
 *
 * @author    John Sublett
 * @creation  20 Jun 2002
 * @version   $Revision: 4$ $Date: 3/31/04 11:50:44 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("stop"),
    @Range("roll")
  }
)
public final class BFullPolicy
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BFullPolicy(4148748620)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for stop. */
  public static final int STOP = 0;
  /** Ordinal value for roll. */
  public static final int ROLL = 1;

  /** BFullPolicy constant for stop. */
  public static final BFullPolicy stop = new BFullPolicy(STOP);
  /** BFullPolicy constant for roll. */
  public static final BFullPolicy roll = new BFullPolicy(ROLL);

  /** Factory method with ordinal. */
  public static BFullPolicy make(int ordinal)
  {
    return (BFullPolicy)stop.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BFullPolicy make(String tag)
  {
    return (BFullPolicy)stop.getRange().get(tag);
  }

  /** Private constructor. */
  private BFullPolicy(int ordinal)
  {
    super(ordinal);
  }

  public static final BFullPolicy DEFAULT = stop;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFullPolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
