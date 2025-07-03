/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.time;

import static javax.bajax.analytics.AnalyticConstants.INTERVAL_DAY;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_FIFTEEN_MINUTES;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_FIFTEEN_SECONDS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_FIVE_MINUTES;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_FIVE_SECONDS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_FOUR_HOURS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_HOUR;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_MINUTE;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_MONTH;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_NONE;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_QUARTER;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_SECOND;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_SIX_HOURS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_TEN_MINUTES;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_TEN_SECONDS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_THIRTY_MINUTES;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_THIRTY_SECONDS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_THREE_HOURS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_TWELVE_HOURS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_TWENTY_MINUTES;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_TWO_HOURS;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_WEEK;
import static javax.bajax.analytics.AnalyticConstants.INTERVAL_YEAR;

import java.time.Instant;
import java.time.ZoneId;
import java.time.zone.ZoneOffsetTransition;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridiumx.analytics.time.TimeBinding;
import com.tridiumx.analytics.util.Utils;

/**
 * Niagara enum implementation of the Interval interface.
 *
 * @author Aaron Hansen
 * @see Interval
 * @since NA 2.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "none", ordinal = INTERVAL_NONE),
    @Range(value = "second", ordinal = INTERVAL_SECOND),
    @Range(value = "fiveSeconds", ordinal = INTERVAL_FIVE_SECONDS),
    @Range(value = "tenSeconds", ordinal = INTERVAL_TEN_SECONDS),
    @Range(value = "fifteenSeconds", ordinal = INTERVAL_FIFTEEN_SECONDS),
    @Range(value = "thirtySeconds", ordinal = INTERVAL_THIRTY_SECONDS),
    @Range(value = "minute", ordinal = INTERVAL_MINUTE),
    @Range(value = "fiveMinutes", ordinal = INTERVAL_FIVE_MINUTES),
    @Range(value = "tenMinutes", ordinal = INTERVAL_TEN_MINUTES),
    @Range(value = "fifteenMinutes", ordinal = INTERVAL_FIFTEEN_MINUTES),
    @Range(value = "twentyMinutes", ordinal = INTERVAL_TWENTY_MINUTES),
    @Range(value = "thirtyMinutes", ordinal = INTERVAL_THIRTY_MINUTES),
    @Range(value = "hour", ordinal = INTERVAL_HOUR),
    @Range(value = "twoHours", ordinal = INTERVAL_TWO_HOURS),
    @Range(value = "threeHours", ordinal = INTERVAL_THREE_HOURS),
    @Range(value = "fourHours", ordinal = INTERVAL_FOUR_HOURS),
    @Range(value = "sixHours", ordinal = INTERVAL_SIX_HOURS),
    @Range(value = "twelveHours", ordinal = INTERVAL_TWELVE_HOURS),
    @Range(value = "day", ordinal = INTERVAL_DAY),
    @Range(value = "week", ordinal = INTERVAL_WEEK),
    @Range(value = "month", ordinal = INTERVAL_MONTH),
    @Range(value = "quarter", ordinal = INTERVAL_QUARTER),
    @Range(value = "year", ordinal = INTERVAL_YEAR)
  }
)
public final class BInterval
  extends BFrozenEnum
  implements Interval
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.bajax.analytics.time.BInterval(1295284219)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = INTERVAL_NONE;
  /** Ordinal value for second. */
  public static final int SECOND = INTERVAL_SECOND;
  /** Ordinal value for fiveSeconds. */
  public static final int FIVE_SECONDS = INTERVAL_FIVE_SECONDS;
  /** Ordinal value for tenSeconds. */
  public static final int TEN_SECONDS = INTERVAL_TEN_SECONDS;
  /** Ordinal value for fifteenSeconds. */
  public static final int FIFTEEN_SECONDS = INTERVAL_FIFTEEN_SECONDS;
  /** Ordinal value for thirtySeconds. */
  public static final int THIRTY_SECONDS = INTERVAL_THIRTY_SECONDS;
  /** Ordinal value for minute. */
  public static final int MINUTE = INTERVAL_MINUTE;
  /** Ordinal value for fiveMinutes. */
  public static final int FIVE_MINUTES = INTERVAL_FIVE_MINUTES;
  /** Ordinal value for tenMinutes. */
  public static final int TEN_MINUTES = INTERVAL_TEN_MINUTES;
  /** Ordinal value for fifteenMinutes. */
  public static final int FIFTEEN_MINUTES = INTERVAL_FIFTEEN_MINUTES;
  /** Ordinal value for twentyMinutes. */
  public static final int TWENTY_MINUTES = INTERVAL_TWENTY_MINUTES;
  /** Ordinal value for thirtyMinutes. */
  public static final int THIRTY_MINUTES = INTERVAL_THIRTY_MINUTES;
  /** Ordinal value for hour. */
  public static final int HOUR = INTERVAL_HOUR;
  /** Ordinal value for twoHours. */
  public static final int TWO_HOURS = INTERVAL_TWO_HOURS;
  /** Ordinal value for threeHours. */
  public static final int THREE_HOURS = INTERVAL_THREE_HOURS;
  /** Ordinal value for fourHours. */
  public static final int FOUR_HOURS = INTERVAL_FOUR_HOURS;
  /** Ordinal value for sixHours. */
  public static final int SIX_HOURS = INTERVAL_SIX_HOURS;
  /** Ordinal value for twelveHours. */
  public static final int TWELVE_HOURS = INTERVAL_TWELVE_HOURS;
  /** Ordinal value for day. */
  public static final int DAY = INTERVAL_DAY;
  /** Ordinal value for week. */
  public static final int WEEK = INTERVAL_WEEK;
  /** Ordinal value for month. */
  public static final int MONTH = INTERVAL_MONTH;
  /** Ordinal value for quarter. */
  public static final int QUARTER = INTERVAL_QUARTER;
  /** Ordinal value for year. */
  public static final int YEAR = INTERVAL_YEAR;

  /** BInterval constant for none. */
  public static final BInterval none = new BInterval(NONE);
  /** BInterval constant for second. */
  public static final BInterval second = new BInterval(SECOND);
  /** BInterval constant for fiveSeconds. */
  public static final BInterval fiveSeconds = new BInterval(FIVE_SECONDS);
  /** BInterval constant for tenSeconds. */
  public static final BInterval tenSeconds = new BInterval(TEN_SECONDS);
  /** BInterval constant for fifteenSeconds. */
  public static final BInterval fifteenSeconds = new BInterval(FIFTEEN_SECONDS);
  /** BInterval constant for thirtySeconds. */
  public static final BInterval thirtySeconds = new BInterval(THIRTY_SECONDS);
  /** BInterval constant for minute. */
  public static final BInterval minute = new BInterval(MINUTE);
  /** BInterval constant for fiveMinutes. */
  public static final BInterval fiveMinutes = new BInterval(FIVE_MINUTES);
  /** BInterval constant for tenMinutes. */
  public static final BInterval tenMinutes = new BInterval(TEN_MINUTES);
  /** BInterval constant for fifteenMinutes. */
  public static final BInterval fifteenMinutes = new BInterval(FIFTEEN_MINUTES);
  /** BInterval constant for twentyMinutes. */
  public static final BInterval twentyMinutes = new BInterval(TWENTY_MINUTES);
  /** BInterval constant for thirtyMinutes. */
  public static final BInterval thirtyMinutes = new BInterval(THIRTY_MINUTES);
  /** BInterval constant for hour. */
  public static final BInterval hour = new BInterval(HOUR);
  /** BInterval constant for twoHours. */
  public static final BInterval twoHours = new BInterval(TWO_HOURS);
  /** BInterval constant for threeHours. */
  public static final BInterval threeHours = new BInterval(THREE_HOURS);
  /** BInterval constant for fourHours. */
  public static final BInterval fourHours = new BInterval(FOUR_HOURS);
  /** BInterval constant for sixHours. */
  public static final BInterval sixHours = new BInterval(SIX_HOURS);
  /** BInterval constant for twelveHours. */
  public static final BInterval twelveHours = new BInterval(TWELVE_HOURS);
  /** BInterval constant for day. */
  public static final BInterval day = new BInterval(DAY);
  /** BInterval constant for week. */
  public static final BInterval week = new BInterval(WEEK);
  /** BInterval constant for month. */
  public static final BInterval month = new BInterval(MONTH);
  /** BInterval constant for quarter. */
  public static final BInterval quarter = new BInterval(QUARTER);
  /** BInterval constant for year. */
  public static final BInterval year = new BInterval(YEAR);

  /** Factory method with ordinal. */
  public static BInterval make(int ordinal)
  {
    return (BInterval)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BInterval make(String tag)
  {
    return (BInterval)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BInterval(int ordinal)
  {
    super(ordinal);
  }

  public static final BInterval DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInterval.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * Returns the number of intervals for the given time range, or -1
   * if indeterminate.
   */
  public int count(long start, long end)
  {
    if ((start < 0) || (end < 0))
      return -1;
    long delta = end - start;
    int type = getOrdinal();
    switch (type)
    {
      case INTERVAL_NONE:
        return (int)delta;
      case INTERVAL_SECOND:
      case INTERVAL_FIVE_SECONDS:
      case INTERVAL_TEN_SECONDS:
      case INTERVAL_FIFTEEN_SECONDS:
      case INTERVAL_THIRTY_SECONDS:
      case INTERVAL_MINUTE:
      case INTERVAL_FIVE_MINUTES:
      case INTERVAL_TEN_MINUTES:
      case INTERVAL_FIFTEEN_MINUTES:
      case INTERVAL_TWENTY_MINUTES:
      case INTERVAL_THIRTY_MINUTES:
      case INTERVAL_HOUR:
      case INTERVAL_TWO_HOURS:
      case INTERVAL_THREE_HOURS:
      case INTERVAL_FOUR_HOURS:
      case INTERVAL_SIX_HOURS:
      case INTERVAL_TWELVE_HOURS:
      case INTERVAL_DAY:
      case INTERVAL_WEEK:
        return (int)(delta / millis());
    }
    int ret = 0;
    if (start < end)
    {
      while (start < end)
      {
        ret++;
        start = next(start);
      }
    }
    else
    {
      while (end < start)
      {
        ret++;
        end = next(end);
      }
    }
    return ret;
  }

  /**
   * The approximate number of ms in the interval.
   */
  public long millis()
  {
    switch (getOrdinal())
    {
      case INTERVAL_NONE:
        return 1;
      case INTERVAL_SECOND:
        return MILLIS_SECOND;
      case INTERVAL_FIVE_SECONDS:
        return MILLIS_FIVE_SECONDS;
      case INTERVAL_TEN_SECONDS:
        return MILLIS_TEN_SECONDS;
      case INTERVAL_FIFTEEN_SECONDS:
        return MILLIS_FIFTEEN_SECONDS;
      case INTERVAL_THIRTY_SECONDS:
        return MILLIS_THIRTY_SECONDS;
      case INTERVAL_MINUTE:
        return MILLIS_MINUTE;
      case INTERVAL_FIVE_MINUTES:
        return MILLIS_FIVE_MINUTES;
      case INTERVAL_TEN_MINUTES:
        return MILLIS_TEN_MINUTES;
      case INTERVAL_FIFTEEN_MINUTES:
        return MILLIS_FIFTEEN_MINUTES;
      case INTERVAL_TWENTY_MINUTES:
        return MILLIS_TWENTY_MINUTES;
      case INTERVAL_THIRTY_MINUTES:
        return MILLIS_THIRTY_MINUTES;
      case INTERVAL_HOUR:
        return MILLIS_HOUR;
      case INTERVAL_TWO_HOURS:
        return MILLIS_TWO_HOURS;
      case INTERVAL_THREE_HOURS:
        return MILLIS_THREE_HOURS;
      case INTERVAL_FOUR_HOURS:
        return MILLIS_FOUR_HOURS;
      case INTERVAL_SIX_HOURS:
        return MILLIS_SIX_HOURS;
      case INTERVAL_TWELVE_HOURS:
        return MILLIS_TWELVE_HOURS;
      case INTERVAL_DAY:
        return MILLIS_DAY;
      case INTERVAL_WEEK:
        return MILLIS_WEEK;
      case INTERVAL_MONTH:
        return MILLIS_MONTH;
      case INTERVAL_QUARTER:
        return MILLIS_QUARTER;
      case INTERVAL_YEAR:
        return MILLIS_YEAR;
    }
    throw new IllegalStateException(
      Utils.lex("unknownInterval") + ": " + getOrdinal());
  }

  /**
   * Returns the next interval for the previously aligned timestamp.
   */
  private ZoneId zoneId = ZoneId.of(TimeBinding.getTimeZone().getID());


  private int adjustH(long ts, int hours)
  {
    Instant ins = Instant.ofEpochMilli(ts);
    ZoneOffsetTransition zoneOffsetTransition = zoneId.getRules().nextTransition(ins);
    if (zoneId.getRules().nextTransition(ins) != null)
    {
      long nextChange = zoneOffsetTransition.toEpochSecond();
      boolean isDST = zoneId.getRules().isDaylightSavings(ins);
      long calcNextInterval = (ts + (hours * MILLIS_HOUR));
      if (!isDST && (calcNextInterval / 1000) >= nextChange)
      {
        calcNextInterval = calcNextInterval - MILLIS_HOUR;
        //Adjust the hours to be added to align it to next interval.
        if (((calcNextInterval) / 1000) >= nextChange)
        {
          return hours - 1;
        }
        //Skip one interval, if it aligned to the next interval.
        return (2 * hours) - 1;
      }
      else if (isDST && (calcNextInterval / 1000) >= nextChange)
      {
        return hours + 1;
      }
    }
    return hours;
  }

  public long next(long timestamp)
  {
    long ts = timestamp;

    switch (getOrdinal())
    {
      case INTERVAL_NONE:
        return ts + 1;
      case INTERVAL_SECOND:
        return ts + MILLIS_SECOND;
      case INTERVAL_FIVE_SECONDS:
        return ts + MILLIS_FIVE_SECONDS;
      case INTERVAL_TEN_SECONDS:
        return ts + MILLIS_TEN_SECONDS;
      case INTERVAL_FIFTEEN_SECONDS:
        return ts + MILLIS_FIFTEEN_SECONDS;
      case INTERVAL_THIRTY_SECONDS:
        return ts + MILLIS_THIRTY_SECONDS;
      case INTERVAL_MINUTE:
        return ts + MILLIS_MINUTE;
      case INTERVAL_FIVE_MINUTES:
        return ts + MILLIS_FIVE_MINUTES;
      case INTERVAL_TEN_MINUTES:
        return ts + MILLIS_TEN_MINUTES;
      case INTERVAL_FIFTEEN_MINUTES:
        return ts + MILLIS_FIFTEEN_MINUTES;
      case INTERVAL_TWENTY_MINUTES:
        return ts + MILLIS_TWENTY_MINUTES;
      case INTERVAL_THIRTY_MINUTES:
        return ts + MILLIS_THIRTY_MINUTES;
      case INTERVAL_HOUR:
        return TimeBinding.addHours(1, ts);
      case INTERVAL_TWO_HOURS:
        return TimeBinding.addHours(adjustH(ts, 2), ts);
      case INTERVAL_THREE_HOURS:
        return TimeBinding.addHours(adjustH(ts, 3), ts);
      case INTERVAL_FOUR_HOURS:
        return TimeBinding.addHours(adjustH(ts, 4), ts);
      case INTERVAL_SIX_HOURS:
        return TimeBinding.addHours(adjustH(ts, 6), ts);
      case INTERVAL_TWELVE_HOURS:
        return TimeBinding.addHours(adjustH(ts, 12), ts);
      case INTERVAL_DAY:
        return TimeBinding.addDays(1, ts);
      case INTERVAL_WEEK:
        return TimeBinding.addWeeks(1, ts);
      case INTERVAL_MONTH:
        return TimeBinding.addMonths(1, ts);
      case INTERVAL_QUARTER:
        return TimeBinding.addMonths(3, ts);
      case INTERVAL_YEAR:
        return TimeBinding.addYears(1, ts);
    }
    throw new IllegalStateException(
      Utils.lex("unknownInterval") + ": " + getOrdinal());
  }

  /**
   * Get the previous time stamp based on the intervals
   *
   * @param timestamp
   * @return
   */
  public long previous(long timestamp)
  {
    long ts = timestamp;

    switch (getOrdinal())
    {
      case INTERVAL_NONE:
        return ts - 1;
      case INTERVAL_SECOND:
        return ts - MILLIS_SECOND;
      case INTERVAL_FIVE_SECONDS:
        return ts - MILLIS_FIVE_SECONDS;
      case INTERVAL_TEN_SECONDS:
        return ts - MILLIS_TEN_SECONDS;
      case INTERVAL_FIFTEEN_SECONDS:
        return ts - MILLIS_FIFTEEN_SECONDS;
      case INTERVAL_THIRTY_SECONDS:
        return ts - MILLIS_THIRTY_SECONDS;
      case INTERVAL_MINUTE:
        return ts - MILLIS_MINUTE;
      case INTERVAL_FIVE_MINUTES:
        return ts - MILLIS_FIVE_MINUTES;
      case INTERVAL_TEN_MINUTES:
        return ts - MILLIS_TEN_MINUTES;
      case INTERVAL_FIFTEEN_MINUTES:
        return ts - MILLIS_FIFTEEN_MINUTES;
      case INTERVAL_TWENTY_MINUTES:
        return ts - MILLIS_TWENTY_MINUTES;
      case INTERVAL_THIRTY_MINUTES:
        return ts - MILLIS_THIRTY_MINUTES;
      case INTERVAL_HOUR:
        return TimeBinding.addHours(-1, ts);
      case INTERVAL_TWO_HOURS:
        return TimeBinding.addHours(-2, ts);
      case INTERVAL_THREE_HOURS:
        return TimeBinding.addHours(-3, ts);
      case INTERVAL_FOUR_HOURS:
        return TimeBinding.addHours(-4, ts);
      case INTERVAL_SIX_HOURS:
        return TimeBinding.addHours(-6, ts);
      case INTERVAL_TWELVE_HOURS:
        return TimeBinding.addHours(-12, ts);
      case INTERVAL_DAY:
        return TimeBinding.addDays(-1, ts);
      case INTERVAL_WEEK:
        return TimeBinding.addWeeks(-1, ts);
      case INTERVAL_MONTH:
        return TimeBinding.addMonths(-1, ts);
      case INTERVAL_QUARTER:
        return TimeBinding.addMonths(-3, ts);
      case INTERVAL_YEAR:
        return TimeBinding.addYears(-1, ts);
      default:
        throw new IllegalStateException(
          Utils.lex("unknownInterval") + ": " + getOrdinal());
    }

  }


}
