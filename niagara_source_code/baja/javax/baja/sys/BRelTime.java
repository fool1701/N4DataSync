/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.util.LexiconModule;

import com.tridium.sys.schema.Fw;

/**
 * BRelTime is a BSimple type for managing
 * a relative amount of time.  The standard storage
 * mechanism is a 64 bit long containing the number
 * of milliseconds.
 *
 * @author    Brian Frank
 * @creation  19 Feb 00
 * @version   $Revision: 37$ $Date: 6/27/11 3:51:41 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BRelTime
  extends BSimple
  implements BIDataValue, BIComparable
{

  /**
   * Factory which takes a number of milliseconds.
   *
   * @param ms milliseconds for BRelTime
   */
  public static BRelTime make(long ms)
  {
    if (ms == 0)
    {
      return DEFAULT;
    }
    return (BRelTime)(new BRelTime(ms).intern());
  }

  /**
   * Factory from string encoding.
   */
  public static BRelTime make(String s)
    throws IOException
  {
    return (BRelTime)DEFAULT.decodeFromString(s);
  }

  /**
   * Make a BRelTime for the specified number of days.
   * @since Niagara 3.7
   */
  public static BRelTime makeDays(int days)
  {
    return BRelTime.make(BRelTime.MILLIS_IN_DAY * days);
  }

  /**
   * Make a BRelTime for the specified number of hours.
   */
  public static BRelTime makeHours(int hours)
  {
    return BRelTime.make(BRelTime.MILLIS_IN_HOUR * hours);
  }

  /**
   * Make a BRelTime for the specified number of minutes.
   */
  public static BRelTime makeMinutes(int minutes)
  {
    return BRelTime.make(BRelTime.MILLIS_IN_MINUTE * minutes);
  }

  /**
   * Make a BRelTime for the specified number of seconds.
   */
  public static BRelTime makeSeconds(int seconds)
  {
    return BRelTime.make(BRelTime.MILLIS_IN_SECOND * seconds);
  }

  /**
   * Make a BRelTime for the specified interval
   * @param days
   * @param hours
   * @param minutes
   * @param seconds
   * @return
   * @since 4.2
   */
  public static BRelTime make(int days, int hours, int minutes, int seconds) {
    return BRelTime.make(BRelTime.MILLIS_IN_DAY*days + BRelTime.MILLIS_IN_HOUR*hours + BRelTime.MILLIS_IN_MINUTE*minutes + BRelTime.MILLIS_IN_SECOND*seconds);
  }

  /**
   * Private constructor.
   */
  private BRelTime(long ms)
  {
    this.ms = ms;
  }

  /**
   *  @return this duration as a number of milliseconds.
   */
  public long getMillis()
  {
    return ms;
  }

  /**
   * @return this duration as a number of seconds.
   *    Truncate to the nearest second.
   */
  public int getSeconds()
  {
    return (int) (ms / MILLIS_IN_SECOND);
  }

  /**
   * @return this duration as a number of minutes.
   *    Truncate to the nearest minute.
   */
  public int getMinutes()
  {
    return (int) (ms / MILLIS_IN_MINUTE);
  }

  /**
   * @return this duration as a number of hours.
   *   Truncate to the nearest hour.
   */
  public int getHours()
  {
    return (int) (ms / MILLIS_IN_HOUR);
  }

  /**
   * @return this duration as a number of days.
   *    Truncate to the nearest day.
   */
  public int getDays()
  {
    return (int) (ms / MILLIS_IN_DAY);
  }


  /**
   *  @return this duration as a number of milliseconds.
   */
  public long getMillisPart()
  {
    return ms % MILLIS_IN_SECOND;
  }

  /**
   * @return the seconds part of this duration
   *    Truncate to the nearest second.
   */
  public int getSecondsPart()
  {
    return (int) (((ms - getMillisPart()) % MILLIS_IN_MINUTE) / MILLIS_IN_SECOND);
  }

  /**
   * @return the minutes part of this duration
   *    Truncate to the nearest minute.
   */
  public int getMinutesPart()
  {
    return (int) (((ms - (MILLIS_IN_SECOND*getSecondsPart())) % MILLIS_IN_HOUR) / MILLIS_IN_MINUTE);
  }

  /**
   * @return the hours part of this duration
   *   Truncate to the nearest hour.
   */
  public int getHoursPart()
  {
    return (int) (((ms - (MILLIS_IN_MINUTE*getMinutesPart())) % MILLIS_IN_DAY) / MILLIS_IN_HOUR);
  }

  /**
   * @return the days part of this duration
   *   Truncate to the nearest hour.
   */
  public int getDaysPart()
  {
    return (int) ((ms - (MILLIS_IN_HOUR*getHoursPart())) / MILLIS_IN_DAY);
  }

  /**
   * Get the absolute value of this duration.
   */
  public BRelTime abs()
  {
    if (ms >= 0L)
    {
      return this;
    }
    else
    {
      return BRelTime.make(Math.abs(ms));
    }
  }

  /**
   * BRelTime has a hash based on its milliseconds.
   *
   * @return integer hash code
   */
  public int hashCode()
  {
   return (int)(ms ^ (ms >> 32));
  }

  /**
   * Equality is based on milliseconds.
   *
   * @return true if obj is a BRelTime with
   *    an equal number of milliseconds.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BRelTime)
    {
      return ((BRelTime)obj).ms == ms;
    }
    return false;
  }

  /**
   * Compares this object with the specified object for order.
   *
   * @return Returns a negative integer, zero, or a positive
   * integer as this object is less than, equal to, or greater
   * than the specified object.
   */
  @Override
  public int compareTo(Object o)
  {
    BRelTime t = (BRelTime)o;
    long delta = getMillis() - t.getMillis();
    if (delta == 0)
    {
      return 0;
    }
    if (delta < 0)
    {
      return -1;
    }
    else
    {
      return 1;
    }
  }

  /**
   * get a java.time.Duration from the BRelTime duration
   * @return java.time.Duration
   * @since Niagara 4.9
   */
  public Duration toDuration()
  {
    return Duration.ofMillis(this.ms);
  }

  /**
   * Convenience for {@code toString(ms, null)}.
   */
  public static String toString(long ms)
  {
    return toString(ms, null);
  }

  /**
   * Get string to represent a duration of time displayed in
   * days, hours, minutes, seconds, and milliseconds.
   */
  public static String toString(long ms, Context cx)
  {
    List<String> fields = new ArrayList<>();
    Context numberCx = toNumberLocalizationContext(cx);
    String prefix = "";

    boolean showMillis = true;
    if (cx != null)
    {
      BBoolean x = (BBoolean)cx.getFacet(BFacets.SHOW_MILLISECONDS);
      if (x != null) { showMillis = x.getBoolean(); }
    }

    boolean showSeconds = true;
    if (cx != null)
    {
      BBoolean x = (BBoolean)cx.getFacet(BFacets.SHOW_SECONDS);
      if (x != null) { showSeconds = x.getBoolean(); }
    }

    boolean showMinutes = true;
    if (cx != null)
    {
      BBoolean x = (BBoolean)cx.getFacet(SHOW_MINUTES);
      if (x != null) { showMinutes = x.getBoolean(); }
    }

    boolean showHours = true;
    if (cx != null)
    {
      BBoolean x = (BBoolean)cx.getFacet(SHOW_HOURS);
      if (x != null) { showHours = x.getBoolean(); }
    }

    boolean showDays = true;
    if (cx != null)
    {
      BBoolean x = (BBoolean)cx.getFacet(SHOW_DAYS);
      if (x != null) { showDays = x.getBoolean(); }
    }

    if (ms < 0)
    {
      prefix = "-";
      ms = -ms;
    }

    // anything less than a second
    if (ms < 1000)
    {
      if (showSeconds)
      {
        if (showMillis)
        {
          return prefix + getText("ms", cx, ms);
        }
        else
        {
          return prefix + getText("seconds", cx, 0);
        }
      }
      else if (showMinutes)
      {
        return prefix + getText("minutes", cx, 0);
      }
      else if (showHours)
      {
        return prefix + getText("hours", cx, 0);
      }
      else if (showDays)
      {
        return prefix + getText("days", cx, 0);
      }
    }

    if (ms >= MILLIS_IN_DAY && showDays)
    {
      long days = ms/MILLIS_IN_DAY;
      ms = ms % MILLIS_IN_DAY;
      fields.add(getText(days == 1 ? "day" : "days", cx, BLong.make(days).toString(numberCx)));
    }

    if (ms >= MILLIS_IN_HOUR && showHours)
    {
      long hours = ms/MILLIS_IN_HOUR;
      ms = ms % MILLIS_IN_HOUR;
      fields.add(getText(hours == 1 ? "hour" : "hours", cx, BLong.make(hours).toString(numberCx)));
    }

    if (ms >= MILLIS_IN_MINUTE && showMinutes)
    {
      long mins = ms/MILLIS_IN_MINUTE;
      ms = ms % MILLIS_IN_MINUTE;
      fields.add(getText(mins == 1 ? "minute" : "minutes", cx, BLong.make(mins).toString(numberCx)));
    }

    if (ms > 0 && showSeconds)
    {
      long secs = ms/MILLIS_IN_SECOND;

      long millisPart = ms % MILLIS_IN_SECOND;
      String suffix = (secs != 1 || millisPart != 0) ? "seconds" : "second";

      if (showMillis && millisPart > 0)
      {
        Context triplePrecision = new BasicContext(numberCx, BFacets.make(BFacets.PRECISION, 3));
        fields.add(getText(suffix, cx, BDouble.make(((double) ms) / MILLIS_IN_SECOND).toString(triplePrecision)));
      }
      else
      {
        fields.add(getText(suffix, cx, BLong.make(secs).toString(numberCx)));
      }
    }

    return prefix + String.join(LEX.get("relTime.separator", cx, ",") + ' ', fields);
  }

  /**
   * To string.
   */
  @Override
  public String toString(Context context)
  {
    return toString(ms, context);
  }

  /**
   * Returns a friendly string indicating the time interval from the present
   * moment. For example: "right now", "a few seconds ago", "3 months from now",
   * "an hour ago".
   *
   * @param cx context used for localization
   * @return a human-readable string indicating the time interval, relative to
   * the present moment
   * @since Niagara 4.8
   */
  public String toFriendlyString(Context cx)
  {
    long duration = Math.abs(getMillis());
    String durationText;

    if (duration == 0)
    {
      return LEX.get("relTime.friendly.rightNow", cx);
    }

    if (duration < MILLIS_IN_MINUTE)
    {
      durationText = getFriendlyText("aFewSeconds", cx);
    }
    else if (duration < MILLIS_IN_MINUTE * 2)
    {
      durationText = getFriendlyText("minute", cx);
    }
    else if (duration < MILLIS_IN_HOUR)
    {
      durationText = getFriendlyText("minutes", cx, Math.abs(getMinutes()));
    }
    else if (duration < MILLIS_IN_HOUR * 2)
    {
      durationText = getFriendlyText("hour", cx);
    }
    else if (duration < MILLIS_IN_DAY)
    {
      durationText = getFriendlyText("hours", cx, Math.abs(getHours()));
    }
    else if (duration < MILLIS_IN_DAY * 2)
    {
      durationText = getFriendlyText("day", cx);
    }
    else if (duration < MILLIS_IN_MONTH)
    {
      durationText = getFriendlyText("days", cx, Math.abs(getDays()));
    }
    else if (duration < MILLIS_IN_MONTH * 2)
    {
      durationText = getFriendlyText("month", cx);
    }
    else if (duration < MILLIS_IN_YEAR)
    {
      durationText = getFriendlyText("months", cx, Math.abs(getDays()) / DAYS_IN_MONTH);
    }
    else if (duration < MILLIS_IN_YEAR * 2)
    {
      durationText = getFriendlyText("year", cx);
    }
    else
    {
      durationText = getFriendlyText("years", cx, Math.abs(getDays()) / DAYS_IN_YEAR);
    }

    return getFriendlyText(getMillis() < 0 ? "inThePast" : "inTheFuture", cx, durationText);
  }

  private static String getText(String suffix, Context cx, Object... args)
  {
    return LEX.getText("relTime." + suffix, cx, args);
  }

  private static String getFriendlyText(String suffix, Context cx, Object... args)
  {
    return LEX.getText("relTime.friendly." + suffix, cx, args);
  }

  /**
   * @param cx context passed to toString that may include values that would
   *           have unintended consequences when formatting numbers
   * @return a context safe to localize numbers, or null
   */
  private static Context toNumberLocalizationContext(Context cx)
  {
    if (cx == null) { return null; }

    Context languageCx = new BasicContext(null, cx.getLanguage());
    BObject showSeparators = cx.getFacet(BFacets.SHOW_SEPARATORS);
    if (showSeparators instanceof BBoolean)
    {
      return new BasicContext(languageCx,
        BFacets.make(BFacets.SHOW_SEPARATORS, (BBoolean) showSeparators));
    }
    else
    {
      return languageCx;
    }
  }

  /**
   * BRelTime is serialized using encodeLong().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeLong(ms);
  }

  /**
   *  BRelTime is unserialized using decodeLong().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readLong() );
  }

  /**
   * Write the simple in text format as
   * the number of milliseconds.
   */
  @Override
  public String encodeToString()
  {
    return String.valueOf(ms);
  }

  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    if (s.equals("0"))
    {
      return DEFAULT;
    }
    return new BRelTime ( Long.valueOf(s).longValue() ).intern();
  }

  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue() { return this; }

////////////////////////////////////////////////////////////////
//Framework Support
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.SKIP_INTERN)
    { // Don't intern if the milliseconds part is not zero
      // and its not just a milliseconds BRelTime
      if ((Math.abs(ms) > MILLIS_IN_SECOND) && (getMillisPart() != 0))
      {
        return Boolean.TRUE;
      }

      return null;
    }

    return super.fw(x, a, b, c, d);
  }

  /**
   * Get default BRelTime constant is 0 milliseconds.
   */
  public static final BRelTime DEFAULT = new BRelTime(0);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRelTime.class);

  public static final long MILLIS_IN_SECOND = 1000L;
  public static final long MILLIS_IN_MINUTE = 1000L * 60L;
  public static final long MILLIS_IN_HOUR   = 1000L * 60L * 60L;
  public static final long MILLIS_IN_DAY    = 1000L * 60L * 60L * 24L;
  public static final long MILLIS_IN_WEEK   = 1000L * 60L * 60L * 24L * 7L;

  private static final long DAYS_IN_MONTH = 30;
  private static final long DAYS_IN_YEAR = 365;
  private static final long MILLIS_IN_MONTH = MILLIS_IN_DAY * DAYS_IN_MONTH;
  private static final long MILLIS_IN_YEAR = MILLIS_IN_DAY * DAYS_IN_YEAR;

  public static final BRelTime SECOND = BRelTime.make(MILLIS_IN_SECOND);
  public static final BRelTime MINUTE = BRelTime.make(MILLIS_IN_MINUTE);
  public static final BRelTime HOUR   = BRelTime.make(MILLIS_IN_HOUR);
  public static final BRelTime DAY    = BRelTime.make(MILLIS_IN_DAY);
  public static final BRelTime WEEK   = BRelTime.make(MILLIS_IN_WEEK);

  // Facet names
  public static final String SHOW_DAYS = "showDays";
  public static final String SHOW_HOURS = "showHours";
  public static final String SHOW_MINUTES = "showMinutes";

  private static final LexiconModule LEX = LexiconModule.make(TYPE);

  private final long ms;
}
