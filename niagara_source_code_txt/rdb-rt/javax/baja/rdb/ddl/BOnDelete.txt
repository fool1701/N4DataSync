/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.ddl;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BOnDelete enumerates the options for 
 * a foreign key's "reference on delete" action
 *
 * @author    Mike Jarmy
 * @creation  20 Jun 07
 * @version   $Revision$Date: 6/22/2007 11:17:32 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("noAction"),
    @Range("cascade")
  }
)
public final class BOnDelete
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.ddl.BOnDelete(4004094636)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for noAction. */
  public static final int NO_ACTION = 0;
  /** Ordinal value for cascade. */
  public static final int CASCADE = 1;

  /** BOnDelete constant for noAction. */
  public static final BOnDelete noAction = new BOnDelete(NO_ACTION);
  /** BOnDelete constant for cascade. */
  public static final BOnDelete cascade = new BOnDelete(CASCADE);

  /** Factory method with ordinal. */
  public static BOnDelete make(int ordinal)
  {
    return (BOnDelete)noAction.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BOnDelete make(String tag)
  {
    return (BOnDelete)noAction.getRange().get(tag);
  }

  /** Private constructor. */
  private BOnDelete(int ordinal)
  {
    super(ordinal);
  }

  public static final BOnDelete DEFAULT = noAction;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOnDelete.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
