/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.util;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BPollFrequency is used to configure how fast a 
 * component is polled by a BPollManager.
 *
 * @author    Brian Frank
 * @creation  21 Jan 02
 * @version   $Revision: 5$ $Date: 6/3/04 1:11:49 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("fast"),
    @Range("normal"),
    @Range("slow")
  }
)
public final class BPollFrequency
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BPollFrequency(2487947019)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for fast. */
  public static final int FAST = 0;
  /** Ordinal value for normal. */
  public static final int NORMAL = 1;
  /** Ordinal value for slow. */
  public static final int SLOW = 2;

  /** BPollFrequency constant for fast. */
  public static final BPollFrequency fast = new BPollFrequency(FAST);
  /** BPollFrequency constant for normal. */
  public static final BPollFrequency normal = new BPollFrequency(NORMAL);
  /** BPollFrequency constant for slow. */
  public static final BPollFrequency slow = new BPollFrequency(SLOW);

  /** Factory method with ordinal. */
  public static BPollFrequency make(int ordinal)
  {
    return (BPollFrequency)fast.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BPollFrequency make(String tag)
  {
    return (BPollFrequency)fast.getRange().get(tag);
  }

  /** Private constructor. */
  private BPollFrequency(int ordinal)
  {
    super(ordinal);
  }

  public static final BPollFrequency DEFAULT = fast;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPollFrequency.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
