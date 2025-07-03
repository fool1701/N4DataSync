/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.user;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Enum to determine user prototype expiration merge policy.
 * <ul>
 *   <li>preferEarliest: expirations will be merged - the earliest expiration will be picked</li>
 *   <li>useFirst: expirations will not be merged - the first expiration on the list will be picked</li>
 * </ul>
 *
 * @author Melanie Coggan on 2021-12-02
 * @since Niagara 4.12
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("preferEarliest"),
    @Range("useFirst")
  }
)
public final class BExpirationMergeMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BExpirationMergeMode(3425954003)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for preferEarliest. */
  public static final int PREFER_EARLIEST = 0;
  /** Ordinal value for useFirst. */
  public static final int USE_FIRST = 1;

  /** BExpirationMergeMode constant for preferEarliest. */
  public static final BExpirationMergeMode preferEarliest = new BExpirationMergeMode(PREFER_EARLIEST);
  /** BExpirationMergeMode constant for useFirst. */
  public static final BExpirationMergeMode useFirst = new BExpirationMergeMode(USE_FIRST);

  /** Factory method with ordinal. */
  public static BExpirationMergeMode make(int ordinal)
  {
    return (BExpirationMergeMode)preferEarliest.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BExpirationMergeMode make(String tag)
  {
    return (BExpirationMergeMode)preferEarliest.getRange().get(tag);
  }

  /** Private constructor. */
  private BExpirationMergeMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BExpirationMergeMode DEFAULT = preferEarliest;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExpirationMergeMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns the preferred expiration based on the merge type:
   * <ul>
   *   <li>preferEarliest: picks the earliest of the two proposed expirations</li>
   *   <li>useFirst: currentExpiration will be returned</li>
   * </ul>
   * @param currentExpiration The current expiration value
   * @param proposedExpiration The expiration we want to merge with
   * @return A BAbsTime representing the preferred expiration time, based on the policy
   */
  public BAbsTime getMergedValue(BAbsTime currentExpiration, BAbsTime proposedExpiration)
  {
    if (currentExpiration == null || proposedExpiration == null)
    {
      throw new IllegalArgumentException("currentExpiration and proposedExpiration cannot be null");
    }

    switch(getOrdinal())
    {
      case PREFER_EARLIEST:
        return getEarliestAbsTime(currentExpiration, proposedExpiration);
      case USE_FIRST:
      default:
        return currentExpiration;
    }
  }

  private BAbsTime getEarliestAbsTime(BAbsTime absTime1, BAbsTime absTime2)
  {
    if (absTime1.isNull())
    {
      return absTime2;
    }

    if (absTime2.isNull())
    {
      return absTime1;
    }

    return absTime1.isBefore(absTime2) ? absTime1 : absTime2;
  }
}
