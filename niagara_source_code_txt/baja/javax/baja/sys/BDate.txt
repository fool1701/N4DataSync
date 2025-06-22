/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.timezone.BTimeZone;

/**
 * BDate represents a specific day, month, and year.
 *
 * @author    Mike Jarmy
 * @creation  08 Mar 10
 * @version   $Revision: 3$ $Date: 3/25/10 2:48:57 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BDate
  extends BSimple
  implements BIDate, BIComparable
{

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance which maps to the current day.
   */
  public static BDate make()
  {
    return make(BAbsTime.now());
  }

  /**
   * Construct an instance which maps to the current day.
   */
  public static BDate today()
  {
    return make(BAbsTime.now());
  }

  /**
   * Construct a date using the day, month, and year of the given BAbsTime.
   */
  public static BDate make(BAbsTime absTime)
  {
    return new BDate(
      absTime.getYear(),
      absTime.getMonth(),
      absTime.getDay());
  }

  /**
   * Construct a date using the day, month and year of the given BAbsTime, along with the
   * given timezone. This method calls {@code BAbsTime.make(absTime, zone)}, and
   * then uses the resulting new BAbsTime's day, month and year.
   */
  public static BDate make(BAbsTime absTime, BTimeZone zone)
  {
    BAbsTime at = BAbsTime.make(absTime, zone);

    return new BDate(
      at.getYear(),
      at.getMonth(),
      at.getDay());
  }

  /**
   * Construct a date using the day, month and year.
   */
  public static BDate make(int year, BMonth month, int day)
  {
    return new BDate(year, month, day);
  }

  /**
   * Construct a BDate for the specified day of the year.
   */
  public static BDate makeDayOfYear(int year, int dayOfYear)
  {
    int daysInYear = BAbsTime.getDaysInYear(year);
    if (dayOfYear > daysInYear)
      throw new IllegalArgumentException(dayOfYear + " > " + daysInYear);
    if (dayOfYear < 1)
      throw new IllegalArgumentException(dayOfYear + " < 1");

    // After this loop is completes, month will be the result month
    // and dayOfYear will be the day of the month.
    BMonth month = BMonth.january;
    int daysInMonth = BAbsTime.getDaysInMonth(year, month);
    while(dayOfYear > daysInMonth)
    {
      dayOfYear -= daysInMonth;
      month = month.next();
      daysInMonth = BAbsTime.getDaysInMonth(year, month);
    }

    return BDate.make(year, month, dayOfYear);
  }

  /**
   * Factory from string encoding.
   */
  public static BDate make(String s) throws IOException
  {
    return (BDate)DEFAULT.decodeFromString(s);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BDate(int year, BMonth month, int day)
  {
    if ((day < 1) || (day > BAbsTime.getDaysInMonth(year, month)))
      throw new IllegalArgumentException(
        "day '" + day + "' is invalid, " +
        "in year '" + year + "', month '" + month + "'.");

    this.year  = year;
    this.month = month;
    this.day   = day;
  }

////////////////////////////////////////////////////////////////
// Comparison
////////////////////////////////////////////////////////////////

  /**
   * Compare to another BDate.
   * @return a negative integer, zero, or a
   *    positive integer as this object is less
   *    than, equal to, or greater than the
   *    specified object.
   */
  @Override
  public int compareTo(Object obj)
  {
    BDate d = (BDate)obj;

    if (year != d.year)
      return year - d.year;

    if (month.getMonthOfYear() != d.month.getMonthOfYear())
      return month.getMonthOfYear() - d.month.getMonthOfYear();

    if (day != d.day)
      return day - d.day;

    return 0;
  }

  /**
   * @return true if the specified date is before this date.
   */
  public boolean isBefore(BDate x)
  {
    return compareTo(x) < 0;
  }

  /**
   * @return true if the specified date is after this date.
   */
  public boolean isAfter(BDate x)
  {
    return compareTo(x) > 0;
  }

  /**
   * BDate uses its encodeToString() value's hash code.
   */
  @Override
  public int hashCode()
  {
    try
    {
      if (hashCode == -1)
        hashCode = encodeToString().hashCode();
      return hashCode;
    }
    catch(Exception e)
    {
      Logger.getLogger("sys").log(Level.WARNING, "Could not create hashCode for '" + this + "'.", e);
      return System.identityHashCode(this);
    }
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof BDate)
    {
      BDate d = (BDate)obj;

      return
        (year == d.year) &&
        (month.getMonthOfYear() == d.month.getMonthOfYear()) &&
        (day == d.day);
    }
    return false;
  }

  /**
   * @return encodeToString()
   */
  @Override
  public String toString(Context context)
  {
    return encodeToString();
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BDate is serialized using writeInt()
   * for the day, month, and year.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(year);
    out.writeInt(month.getMonthOfYear());
    out.writeInt(day);
  }

  /**
   * BDate is unserialized using readInt()
   * for the day, month, and year.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return new BDate(
      in.readInt(),
      BMonth.make(in.readInt() - 1),
      in.readInt());
  }

  /**
   * Write the date in text format using the ISO 8601
   * standard format of "yyyy-mm-dd".
   */
  @Override
  public String encodeToString()
  {
    StringBuilder s = new StringBuilder(32);

    s.append(year).append('-');

    int mon = month.getOrdinal() + 1;
    if (mon < 10) s.append('0');
    s.append(mon).append( '-' );

    if (day < 10) s.append('0');
    s.append(day);

    return s.toString();
  }

  /**
   * Read the date from text format. This
   * text must strictly conform to the ISO 8601 standard
   * format of "yyyy-mm-dd".
   */
  @Override
  public BObject decodeFromString(String s)
  throws IOException
  {
    if (s.length() != 10)
      throw new BajaRuntimeException("Invalid date '" + s + "'");

    char[] c = s.toCharArray();
    int year =
      (c[0] - '0') * 1000 +
      (c[1] - '0') * 100 +
      (c[2] - '0') * 10 +
      (c[3] - '0') * 1;

    if (c[4] != '-') throw new BajaRuntimeException("Invalid date '" + s + "'");

    int mon =
      (c[5] - '0') * 10 +
      (c[6] - '0') * 1;

    if (c[7] != '-') throw new BajaRuntimeException("Invalid date '" + s + "'");

    int day =
      (c[8] - '0') * 10 +
      (c[9] - '0') * 1;

    return new BDate(year, BMonth.make(mon - 1), day);
  }

  /**
   * Test for the null value.
   */
  @Override
  public boolean isNull()
  {
    return equals(NULL);
  }

////////////////////////////////////////////////////////////////
// BIDate
////////////////////////////////////////////////////////////////

  /**
   * @return The year as a four digit integer (ie 2001).
   */
  @Override
  public int getYear()
  {
    return year;
  }

  /**
   * @return the month as a BMonth.
   */
  @Override
  public BMonth getMonth()
  {
    return month;
  }

  /**
   * @return The day: 1-31.
   */
  @Override
  public int getDay()
  {
    return day;
  }

  /**
   * @return the weekday as a BWeekday enum.
   */
  @Override
  public BWeekday getWeekday()
  {
    return BAbsTime.getWeekday(year, month, day);
  }

  /**
   * Get the day of the year for this BDate.  An
   * example is that Feb. 1, 2000 would return 32.  The
   * method does account for leap years.
   */
  @Override
  public int getDayOfYear()
  {
    int dayOfYear = 0;
    for (BMonth m = BMonth.january; m != month; m = m.next())
      dayOfYear += BAbsTime.getDaysInMonth(year, m);
    dayOfYear += day;

    return dayOfYear;
  }

  /**
   * Return if today is Feb 29.
   */
  @Override
  public boolean isLeapDay()
  {
    return (month == BMonth.february) && (day == 29);
  }

////////////////////////////////////////////////////////////////
// Algebra
////////////////////////////////////////////////////////////////

  /**
   * Add a number of days to this day, and return the new day.
   */
  public BDate add(int days)
  {
    if (days == 0) return this;

    int d = getDayOfYear() + days;
    int y = year;

    // add
    if (days > 0)
    {
      while (d > BAbsTime.getDaysInYear(y))
      {
        d -= BAbsTime.getDaysInYear(y);
        y++;
      }
    }
    // subtract
    else
    {
      while (d < 1)
      {
        // yes, this is backwards from 'add'.
        // its correct though.
        y--;
        d += BAbsTime.getDaysInYear(y);
      }
    }

    return makeDayOfYear(y, d);
  }

  /**
   * Subtract a number of days from this day, and return the new day.
   */
  public BDate subtract(int days)
  {
    return add(-days);
  }

  /**
   * Compute the number of days between this day and the specified day.  If
   * d2 is after this day, the result will be positive.  If d2 is before
   * this day, the result will be negative.
   *
   * @param d2 The day to compare against.
   */
  public int delta(BDate d2)
  {
    int n = this.compareTo(d2);
    if (n == 0) return 0;

    BDate d1 = this;
    int sign = 1;
    if (n > 0)
    {
      d1 = d2;
      d2 = this;
      sign = -1;
    }

    int y1 = d1.getYear();
    int y2 = d2.getYear();

    int z1 = d1.getDayOfYear();
    int z2 = d2.getDayOfYear();

    if (y1 == y2)
      return sign * (z2 - z1);

    int gap = 0;
    for (int i = y1 + 1; i < y2; i++)
      gap += BAbsTime.getDaysInYear(i);

    return sign * (gap + z2 + (BAbsTime.getDaysInYear(y1) - z1));
  }

  /**
   * The next day.
   */
  public BDate nextDay()
  {
    return add(1);
  }

  /**
   * The previous day.
   */
  public BDate prevDay()
  {
    return add(-1);
  }

  /**
   * The same day in the next month.  If
   * this day is greater than the last day in the
   * next month, then cap the day to the next month's
   * last day.  If this dates's day is the last day
   * in this month, then we automatically set the
   * month to the next month's last day.
   */
  public BDate nextMonth()
  {
    int year  = getYear();
    int month = getMonth().getOrdinal();
    int day   = getDay();

    if (month == 11)
    {
      // no need to worry about day capping
      // because both Dec and Jan have 31 days
      month = 0;
      year++;
    }
    else
    {
      if (day == BAbsTime.getDaysInMonth(year, BMonth.make(month)))
      {
        month++;
        day = BAbsTime.getDaysInMonth(year, BMonth.make(month));
      }
      else
      {
        month++;
        if (day > BAbsTime.getDaysInMonth(year, BMonth.make(month)))
          day = BAbsTime.getDaysInMonth(year, BMonth.make(month));
      }
    }
    return make(year, BMonth.make(month), day);
  }

  /**
   * The same day in previous month. If
   * this day is greater than the last day in the
   * prev month, then cap the day to the prev month's
   * last day.  If this dates's day is the last day
   * in this month, then we automatically set the
   * month to the prev month's last day.
   */
  public BDate prevMonth()
  {
    int year  = getYear();
    int month = getMonth().getOrdinal();
    int day   = getDay();

    if (month == 0)
    {
      // no need to worry about day capping
      // because both Dec and Jan have 31 days
      month = 11;
      year--;
    }
    else
    {
      if (day == BAbsTime.getDaysInMonth(year, BMonth.make(month)))
      {
        month--;
        day = BAbsTime.getDaysInMonth(year, BMonth.make(month));
      }
      else
      {
        month--;
        if (day > BAbsTime.getDaysInMonth(year, BMonth.make(month)))
          day = BAbsTime.getDaysInMonth(year, BMonth.make(month));
      }
    }
    return make(year, BMonth.make(month), day);
  }

  /**
   * Get the day in next year.  If today
   * is a leap day, then return next year Feb 28.
   */
  public BDate nextYear()
  {
    int day = getDay();
    if (isLeapDay()) day = 28;
    return make(getYear()+1, getMonth(), day);
  }

  /**
   * Get the same day in prev year.  If today
   * is a leap day, then return prev year Feb 28.
   */
  public BDate prevYear()
  {
    int day = getDay();
    if (isLeapDay()) day = 28;
    return make(getYear()-1, getMonth(), day);
  }

  /**
   * Get the next day of the specified weekday. If
   * today is the specified weekday, then return one
   * week from now.
   */
  public BDate next(BWeekday weekday)
  {
    BDate t = nextDay();
    while(t.getWeekday() != weekday)
      t = t.nextDay();
    return t;
  }

  /**
   * Get the prev day of the specified weekday. If
   * today is the specified weekday, then return one
   * week before now.
   */
  public BDate prev(BWeekday weekday)
  {
    BDate t = prevDay();
    while(t.getWeekday() != weekday)
      t = t.prevDay();
    return t;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * The default BDate constant is Jan 1st, 1970.
   */
  public static final BDate DEFAULT = new BDate(1970, BMonth.january, 1);

  /**
   * The default BDate.
   */
  public static final BDate NULL = DEFAULT;

  private final int year;
  private final BMonth month;
  private final int day;

  private int hashCode = -1;

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDate.class);
}
