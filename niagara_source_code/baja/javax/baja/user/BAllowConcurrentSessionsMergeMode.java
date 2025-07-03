/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.user;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Enum to determine user prototype allowConcurrentSession merge policy.
 * <ul>
 *   <li>preferFalse: allowConcurrentSessions will be merged - if any value is false, false will be picked</li>
 *   <li>useFirst: allowConcurrentSessions will not be merged - the first value on the list will be picked</li>
 * </ul>
 *
 * @author Melanie Coggan on 2021-12-02
 * @since Niagara 4.12
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("preferFalse"),
    @Range("useFirst")
  }
)
public final class BAllowConcurrentSessionsMergeMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BAllowConcurrentSessionsMergeMode(1251587201)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for preferFalse. */
  public static final int PREFER_FALSE = 0;
  /** Ordinal value for useFirst. */
  public static final int USE_FIRST = 1;

  /** BAllowConcurrentSessionsMergeMode constant for preferFalse. */
  public static final BAllowConcurrentSessionsMergeMode preferFalse = new BAllowConcurrentSessionsMergeMode(PREFER_FALSE);
  /** BAllowConcurrentSessionsMergeMode constant for useFirst. */
  public static final BAllowConcurrentSessionsMergeMode useFirst = new BAllowConcurrentSessionsMergeMode(USE_FIRST);

  /** Factory method with ordinal. */
  public static BAllowConcurrentSessionsMergeMode make(int ordinal)
  {
    return (BAllowConcurrentSessionsMergeMode)preferFalse.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAllowConcurrentSessionsMergeMode make(String tag)
  {
    return (BAllowConcurrentSessionsMergeMode)preferFalse.getRange().get(tag);
  }

  /** Private constructor. */
  private BAllowConcurrentSessionsMergeMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BAllowConcurrentSessionsMergeMode DEFAULT = preferFalse;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAllowConcurrentSessionsMergeMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns the preferred 'Allow Concurrent Sessions' based on the merge type:
   * <ul>
   *   <li>preferFalse: if either value is false, false will be returned</li>
   *   <li>useFirst: currentAllowConcurrentSessions will be returned</li>
   * </ul>
   * @param currentAllowConcurrentSessions The current 'Allow Concurrent Settings' value
   * @param proposedAllowConcurrentSessions The 'Allow Concurrent Settings' we want to merge with
   * @return A new value for 'Allow Concurrent Sessions' based on the merge type
   */
  public boolean getMergedValue(boolean currentAllowConcurrentSessions, boolean proposedAllowConcurrentSessions)
  {
    switch(getOrdinal())
    {
      case PREFER_FALSE:
        return currentAllowConcurrentSessions && proposedAllowConcurrentSessions;
      case USE_FIRST:
      default:
        return currentAllowConcurrentSessions;
    }
  }
}
