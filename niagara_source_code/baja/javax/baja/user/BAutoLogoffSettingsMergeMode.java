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
 /**
 * Enum to determine user prototype autoLogoffSettings merge policy.
 * <ul>
 *   <li>preferShortest: autoLogoffSettings will be merged - the shortest (strictest) autoLogoffSetting will be picked</li>
 *   <li>useFirst: autoLogoffSettings will not be merged - the first value on the list will be picked</li>
 * </ul>
 *
 * @author Melanie Coggan on 2021-12-02
 * @since Niagara 4.12
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("preferShortest"),
    @Range("useFirst")
  }
)
public final class BAutoLogoffSettingsMergeMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BAutoLogoffSettingsMergeMode(3633123134)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for preferShortest. */
  public static final int PREFER_SHORTEST = 0;
  /** Ordinal value for useFirst. */
  public static final int USE_FIRST = 1;

  /** BAutoLogoffSettingsMergeMode constant for preferShortest. */
  public static final BAutoLogoffSettingsMergeMode preferShortest = new BAutoLogoffSettingsMergeMode(PREFER_SHORTEST);
  /** BAutoLogoffSettingsMergeMode constant for useFirst. */
  public static final BAutoLogoffSettingsMergeMode useFirst = new BAutoLogoffSettingsMergeMode(USE_FIRST);

  /** Factory method with ordinal. */
  public static BAutoLogoffSettingsMergeMode make(int ordinal)
  {
    return (BAutoLogoffSettingsMergeMode)preferShortest.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAutoLogoffSettingsMergeMode make(String tag)
  {
    return (BAutoLogoffSettingsMergeMode)preferShortest.getRange().get(tag);
  }

  /** Private constructor. */
  private BAutoLogoffSettingsMergeMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BAutoLogoffSettingsMergeMode DEFAULT = preferShortest;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAutoLogoffSettingsMergeMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns the preferred AutoLogoffSettings based on the merge type:
   * <ul>
   *   <li>preferShortest: the shortest AutoLogoffSettings will be returned. If there is a tie, using the default system Auto Logoff Period is preferred, if applicable.</li>
   *   <li>useFirst: currentAutoLogoffSettings will be returned</li>
   * </ul>
   * @param currentAutoLogoffSettings The current AutoLogoffSettings
   * @param proposedAutoLogoffSettings The AutoLogoffSettings we want to merge with
   * @return A new AutoLogoffSettings based on the merge type
   */
  public BAutoLogoffSettings getMergedValue(BAutoLogoffSettings currentAutoLogoffSettings, BAutoLogoffSettings proposedAutoLogoffSettings)
  {
    if (currentAutoLogoffSettings == null || proposedAutoLogoffSettings == null)
    {
      throw new IllegalArgumentException("currentAutoLogoffSettings and proposedAutoLogoffSettings cannot be null");
    }

    switch (getOrdinal())
    {
      case PREFER_SHORTEST:
        return (BAutoLogoffSettings) getShortestAutoLogoffSettings(currentAutoLogoffSettings, proposedAutoLogoffSettings).newCopy();
      case USE_FIRST:
      default:
        return (BAutoLogoffSettings) currentAutoLogoffSettings.newCopy();
    }
  }

  private BAutoLogoffSettings getShortestAutoLogoffSettings(BAutoLogoffSettings autoLogoffSettings1, BAutoLogoffSettings autoLogoffSettings2)
  {
    // If autoLogoffSettings1 is disabled, return the other - it is either disabled or shorter
    if (!autoLogoffSettings1.getAutoLogoffEnabled())
    {
      return autoLogoffSettings2;
    }

    // If autoLogoffSettings2 is disabled, return the other - it is either disabled or shorter
    if (!autoLogoffSettings2.getAutoLogoffEnabled())
    {
      return autoLogoffSettings1;
    }

    // If both are using the default auto logoff period, it doesn't matter which we choose
    if (autoLogoffSettings1.getUseDefaultAutoLogoffPeriod() && autoLogoffSettings2.getUseDefaultAutoLogoffPeriod())
    {
      return autoLogoffSettings1;
    }

    // If only autoLogoffSettings1 is using the default, we have to check if the default is shorter that autoLogoffSettings2's
    if (autoLogoffSettings1.getUseDefaultAutoLogoffPeriod())
    {
      BUserService userService = BUserService.getService();
      if (userService.getDefaultAutoLogoffPeriod().compareTo(autoLogoffSettings2.getAutoLogoffPeriod()) <= 0)
      {
        return autoLogoffSettings1;
      }
      else
      {
        return autoLogoffSettings2;
      }
    }

    // If only autoLogoffSettings2 is using the default, we have to check if the default is shorter that autoLogoffSettings1's
    if (autoLogoffSettings2.getUseDefaultAutoLogoffPeriod())
    {
      BUserService userService = BUserService.getService();
      if (userService.getDefaultAutoLogoffPeriod().compareTo(autoLogoffSettings1.getAutoLogoffPeriod()) <= 0)
      {
        return autoLogoffSettings2;
      }
      else
      {
        return autoLogoffSettings1;
      }
    }

    // Both have a custom auto logoff period - pick the shortest of the two
    if (autoLogoffSettings1.getAutoLogoffPeriod().compareTo(autoLogoffSettings2.getAutoLogoffPeriod()) <= 0)
    {
      return autoLogoffSettings1;
    }
    else
    {
      return autoLogoffSettings2;
    }
  }
}
