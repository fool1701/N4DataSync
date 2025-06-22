/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BWebLogFilePolicy
 *
 * @author    Lee Adcock
 * @creation  16 Aug 07
 * @version   $Revision: 1$ $Date: 8/16/07 4:07:06 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("hourly"),
    @Range("daily"),
    @Range("weekly"),
    @Range("monthly"),
    @Range("limitedSize")
  }
)
public final class BWebLogFilePolicy
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BWebLogFilePolicy(3106250803)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for hourly. */
  public static final int HOURLY = 0;
  /** Ordinal value for daily. */
  public static final int DAILY = 1;
  /** Ordinal value for weekly. */
  public static final int WEEKLY = 2;
  /** Ordinal value for monthly. */
  public static final int MONTHLY = 3;
  /** Ordinal value for limitedSize. */
  public static final int LIMITED_SIZE = 4;

  /** BWebLogFilePolicy constant for hourly. */
  public static final BWebLogFilePolicy hourly = new BWebLogFilePolicy(HOURLY);
  /** BWebLogFilePolicy constant for daily. */
  public static final BWebLogFilePolicy daily = new BWebLogFilePolicy(DAILY);
  /** BWebLogFilePolicy constant for weekly. */
  public static final BWebLogFilePolicy weekly = new BWebLogFilePolicy(WEEKLY);
  /** BWebLogFilePolicy constant for monthly. */
  public static final BWebLogFilePolicy monthly = new BWebLogFilePolicy(MONTHLY);
  /** BWebLogFilePolicy constant for limitedSize. */
  public static final BWebLogFilePolicy limitedSize = new BWebLogFilePolicy(LIMITED_SIZE);

  /** Factory method with ordinal. */
  public static BWebLogFilePolicy make(int ordinal)
  {
    return (BWebLogFilePolicy)hourly.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BWebLogFilePolicy make(String tag)
  {
    return (BWebLogFilePolicy)hourly.getRange().get(tag);
  }

  /** Private constructor. */
  private BWebLogFilePolicy(int ordinal)
  {
    super(ordinal);
  }

  public static final BWebLogFilePolicy DEFAULT = hourly;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebLogFilePolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
