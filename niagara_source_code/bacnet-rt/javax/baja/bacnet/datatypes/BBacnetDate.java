/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BIComparable;
import javax.baja.sys.BMonth;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BWeekday;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconModule;

/**
 * BBacnetDate represents a date value in a Bacnet property.
 *
 * @author Craig Gemmill
 * @version $Revision: 6$ $Date: 11/8/01 9:04:51 AM$
 * @creation 24 Sep 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBacnetDate
  extends BSimple
  implements BIComparable
{
  /**
   * Private constructor.  BacnetDate is defined as follows:
   * first octet:   year - 1900   0-254,255
   * second octet:  month         1-12,255 1=Jan
   * third octet:   dayOfMonth    1-31,255
   * fourth octet:  dayOfWeek     1-7,255 1=Monday
   * Invalid values are converted to UNSPECIFIED.
   */
  private BBacnetDate(int year, int month, int dayOfMonth, int dayOfWeek)
  {
    if (year < 0) year = UNSPECIFIED;
    if (year >= 1900) year -= 1900;
    if ((month < 1) || (month > 14)) month = UNSPECIFIED; //13=ODD_MONTHS, 14=EVEN_MONTHS
    if ((dayOfMonth < 1) || (dayOfMonth > 34)) dayOfMonth = UNSPECIFIED;  //32=LAST_DAY_OF_MONTH
    if ((dayOfWeek < 1) || (dayOfWeek > 7)) dayOfWeek = UNSPECIFIED;
    this.year = (byte)year;
    this.month = (byte)month;
    this.dayOfMonth = (byte)dayOfMonth;
    this.dayOfWeek = (byte)dayOfWeek;
    getHashCode();
  }

  /**
   * Factory method for all unspecified.
   */
  public static BBacnetDate make()
  {
    return new BBacnetDate(UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED);
  }

  /**
   * Factory method.
   *
   * @param year       Year minus 1900, or 255 for unspecified
   * @param month      1(Jan) - 12(Dec), or 255 for unspecified
   * @param dayOfMonth 1-31, or 255 for unspecified
   * @param dayOfWeek  1(Mon) - 7(Sun), or 255 for unspecified
   */
  public static BBacnetDate make(int year, int month, int dayOfMonth, int dayOfWeek)
  {
    return new BBacnetDate(year, month, dayOfMonth, dayOfWeek);
  }

  /**
   * Factory method.
   * dayOfWeek is calculated from year/month/day as long as none are unspecified or special
   *
   * @param year       Year minus 1900, or 255 for unspecified
   * @param month      1(Jan) - 12(Dec), or 255 for unspecified
   * @param dayOfMonth 1-31, or 255 for unspecified
   */
  public static BBacnetDate make(int year, int month, int dayOfMonth)
  {
    if (year == UNSPECIFIED || month < 1 || month > 12 || dayOfMonth < 1 || dayOfMonth > 31)
    {
      return make(year, month, dayOfMonth, UNSPECIFIED);
    }

    BAbsTime newDate = BAbsTime.make(year + 1900, BMonth.make(month - 1), dayOfMonth);
    return make(newDate);
  }

  /**
   * Factory method from BAbsTime.
   *
   * @param bt BAbsTime.
   */
  public static BBacnetDate make(BAbsTime bt)
  {
    int y = bt.getYear() - 1900;              // Bacnet year is year minus 1900
    int m = bt.getMonth().getOrdinal() + 1;   // Bacnet months begin with Jan=1
    int d = bt.getDay();
    int w = bt.getWeekday().getOrdinal();     // Bacnet weekdays are 1=Mon...7=Sun
    if (w == BacnetConst.NIAGARA_SUNDAY)
      w = BacnetConst.BAC_SUNDAY;
    return new BBacnetDate(y, m, d, w);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * <code>BBacnetDate</code> equality is based on all values being equal.
   * <p>
   * <B>NOTE</B>: UNSPECIFIED values match ONLY other UNSPECIFIED values.
   * This is <B>not</B> the same as Bacnet equivalence.
   * For Bacnet date equivalence, use the dateEquals() method.
   *
   * @param obj the comparison object
   * @return true if the object is a <code>BBacnetDate</code> with all values equal to this one.
   * @see BBacnetDate#dateEquals(Object)
   */
  public boolean equals(Object obj)
  {
    if (obj == null) return false;
    if (obj instanceof BBacnetDate)
    {
      BBacnetDate d = (BBacnetDate)obj;
      return ((year == d.year) && (month == d.month)
        && (dayOfMonth == d.dayOfMonth) && (dayOfWeek == d.dayOfWeek));
    }
    return false;
  }

  /**
   * BBacnetDate hashcode is a concatenation of all fields.
   *
   * @return a hash code computed by concatenating all fields.
   */
  public int hashCode()
  {
    return hashCode;
  }

  /**
   * To String.
   */
  public String toString(Context context)
  {
    return toString(context, true);
  }

  public String toString(Context context, boolean lexiconize)
  {
    try
    {
      StringBuilder sb = new StringBuilder();
      if (year == UNSPECIFIED)
        sb.append("****-");
      else
        sb.append((year & 0xFF) + 1900).append('-');

      if (month == UNSPECIFIED)
        sb.append("**-");
      else if (month == EVEN_MONTHS)
        sb.append("EV-");
      else if (month == ODD_MONTHS)
        sb.append("OD-");
      else
        sb.append((month < 10) ? "0" + month : String.valueOf(month)).append('-');

      if (dayOfMonth == UNSPECIFIED)
        sb.append("**-");
      else if (dayOfMonth == LAST_DAY_OF_MONTH)
        sb.append("LD-");
      else if (dayOfMonth == ODD_DAYS_OF_MONTH)
        sb.append("OD-");
      else if (dayOfMonth == EVEN_DAYS_OF_MONTH)
        sb.append("ED-");
      else
        sb.append((dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth)).append('-');

      if (dayOfWeek == UNSPECIFIED)
        sb.append("***");
      else if (lexiconize)
        sb.append(lex.getText(WEEKDAYS_LEXKEYS[dayOfWeek - 1], context));
      else
        sb.append(WEEKDAYS[dayOfWeek - 1]);

      return sb.toString();
    }
    catch (RuntimeException e)
    {
      logger.log(Level.SEVERE, "BBacnetDate toString() error: year="+year+" month="+month+" dayOfMonth="+dayOfMonth+" dayOfWeek="+dayOfWeek, e);
      throw e;
    }
  }

  /**
   * BBacnetDate is serialized using calls to writeByte().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeByte(year);
    out.writeByte(month);
    out.writeByte(dayOfMonth);
    out.writeByte(dayOfWeek);
  }

  /**
   * BBacnetDate is unserialized using calls to readByte().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    byte y = in.readByte();
    byte m = in.readByte();
    byte d = in.readByte();
    byte w = in.readByte();
    return new BBacnetDate(y, m, d, w);
  }

  /**
   * Write the primitive in text format.
   */
  public String encodeToString()
    throws IOException
  {
    return toString(null, false);
  }

  /**
   * Read the primitive from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      StringTokenizer st = new StringTokenizer(s, "-");
      int y = UNSPECIFIED;
      int m = UNSPECIFIED;
      int d = UNSPECIFIED;
      int w = UNSPECIFIED;
      String ys = st.nextToken();
      y = (ys.indexOf("*") < 0) ? (Integer.parseInt(ys) - 1900) : UNSPECIFIED;
      if (st.hasMoreTokens())
      {
        String ms = st.nextToken();
        if (ms.indexOf("*") != -1) m = UNSPECIFIED;
        else if (ms.indexOf("EV") != -1) m = EVEN_MONTHS;
        else if (ms.indexOf("OD") != -1) m = ODD_MONTHS;
        else m = Integer.parseInt(ms);
        if (st.hasMoreTokens())
        {
          String ds = st.nextToken();
          if (ds.indexOf("*") != -1) d = UNSPECIFIED;
          else if (ds.indexOf("LD") != -1) d = LAST_DAY_OF_MONTH;
          else if (ds.indexOf("OD") != -1) d = ODD_DAYS_OF_MONTH;
          else if (ds.indexOf("ED") != -1) d = EVEN_DAYS_OF_MONTH;
          else d = Integer.parseInt(ds);
          if (st.hasMoreTokens())
          {
            String ws = st.nextToken();
            w = (ws.indexOf("*") < 0) ? weekday(ws) : UNSPECIFIED;
          }
        }
      }
      return new BBacnetDate(y, m, d, w);
    }
    catch (Exception e)
    {
      throw new IOException("Error decoding BBacnetDate:" + s + "; exc=" + e.toString());
    }
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Get the year.
   *
   * @return the actual year represented by this BBacnetDate,
   * or -1 if unspecified.
   */
  public int getYear()
  {
    if (isYearUnspecified()) return (int)UNSPECIFIED;
//    return (int)year + 1900;
    return (year & 0xFF) + 1900;
  }

  /**
   * Get the uncorrected year value.
   *
   * @return the actual value of the year byte.
   */
  public int getRawYear()
  {
//    return (int)year;
    return year & 0xFF;
  }

  /**
   * Get the month.
   *
   * @return the actual month represented by this BBacnetDate:
   * 1=January, ..., 12=December
   * or -1 if unspecified,
   * or 13 if ODD MONTHS,
   * or 14 if EVEN MONTHS
   */
  public int getMonth()
  {
    return (int)month;
  }

  /**
   * Get the month as a BMonth.
   *
   * @throws IllegalStateException if month is unspecified.
   * @return the month, as a BMonth.
   */
  public BMonth getBMonth()
  {
    if (isMonthUnspecified()) throw new IllegalStateException("Month is unspecified!");
    if (isEvenMonths()) throw new IllegalStateException("Month is EVEN MONTHS");
    if (isOddMonths()) throw new IllegalStateException("Month is ODD MONTHS");
    return BMonth.make(month - 1);
  }

  /**
   * Get the dayOfMonth.
   *
   * @return the actual day of the month represented by this BBacnetDate,
   * or -1 if unspecified.
   * or 32 if last day of month
   * or 33 if odd days of month
   * or 34 if even days of month
   */
  public int getDayOfMonth()
  {
    return (int)dayOfMonth;
  }

  /**
   * Get the dayOfWeek.
   *
   * @return the actual day of the week represented by this BBacnetDate,
   * or -1 if unspecified.
   */
  public int getDayOfWeek()
  {
    return (int)dayOfWeek;
  }

  /**
   * Get the day of the week as a BWeekday.
   *
   * @throws IllegalStateException if dayOfWeek is unspecified.
   * @return the dayOfWeek, as a BWeekday.
   */
  public BWeekday getBWeekday()
  {
    if (isDayOfWeekUnspecified()) throw new IllegalStateException("DayOfWeek is unspecified!");
    if (dayOfWeek == BacnetConst.BAC_SUNDAY) return BWeekday.make(BacnetConst.NIAGARA_SUNDAY);
    return BWeekday.make(dayOfWeek);
  }

  /**
   * Is the year unspecified?
   *
   * @return true if the year is unspecified.
   */
  public boolean isYearUnspecified()
  {
    return (year == UNSPECIFIED);
  }

  /**
   * Is the month unspecified?
   *
   * @return true if the month is unspecified.
   */
  public boolean isMonthUnspecified()
  {
    return (month == UNSPECIFIED);
  }

  /**
   * Is the month unspecified?
   *
   * @return true if the month is unspecified.
   */
  public boolean isOddMonths()
  {
    return (month == ODD_MONTHS);
  }

  /**
   * Is the month unspecified?
   *
   * @return true if the month is unspecified.
   */
  public boolean isEvenMonths()
  {
    return (month == EVEN_MONTHS);
  }

  /**
   * Is the dayOfMonth unspecified?
   *
   * @return true if the dayOfMonth is unspecified.
   */
  public boolean isDayOfMonthUnspecified()
  {
    return (dayOfMonth == UNSPECIFIED);
  }

  /**
   * Is the dayOfMonth unspecified?
   *
   * @return true if the dayOfMonth is unspecified.
   */
  public boolean isLastDayOfMonth()
  {
    return (dayOfMonth == LAST_DAY_OF_MONTH);
  }

  /**
   * Is the oddDaysOfMonth unspecified?
   *
   * @return true if oddDaysOfMonth is specified.
   */
  public boolean isOddDaysOfMonth()
  {
    return (dayOfMonth == ODD_DAYS_OF_MONTH);
  }

  /**
   * Is the evenDaysOfMonth unspecified?
   *
   * @return true if evenDaysOfMonth is specified.
   */
  public boolean isEvenDaysOfMonth()
  {
    return (dayOfMonth == EVEN_DAYS_OF_MONTH);
  }

  /**
   * Is the dayOfWeek unspecified?
   *
   * @return true if the dayOfWeek is unspecified.
   */
  public boolean isDayOfWeekUnspecified()
  {
    return (dayOfWeek == UNSPECIFIED);
  }

  /**
   * Is any field unspecified?
   *
   * @return true if any field is unspecified.
   */
  public boolean isAnyUnspecified()
  {
    return ((year == UNSPECIFIED) || (month == UNSPECIFIED)
      || (dayOfMonth == UNSPECIFIED) || (dayOfWeek == UNSPECIFIED));
  }

////////////////////////////////////////////////////////////////
// Comparison
////////////////////////////////////////////////////////////////

  /**
   * BBacnetDate equivalence is based on all values being equal,
   * or unspecified.
   * <B>NOTE</B>: This is the method to determine date equivalence according
   * to BACnet, <B>not</B> the equals() method, which requires UNSPECIFIED values
   * to match <B>only</B> with UNSPECIFIED values.
   *
   * @param obj the comparison object.
   * @see BBacnetDate#equals(Object)
   */
  public boolean dateEquals(Object obj)
  {
    return compareTo(obj) == 0;
  }

  /**
   * Compare to another BBacnetDate.
   *
   * @param obj the comparison object.
   * @return a negative integer, zero, or a
   * positive integer as this object is less
   * than, equal to, or greater than the
   * specified object.
   */
  public int compareTo(Object obj)
  {
    if (obj == null) throw new ClassCastException();
    BBacnetDate other = (BBacnetDate)obj;
    if (!other.isYearUnspecified() && !isYearUnspecified())
    {
      int y1 = (int)year & 0xFF;
      int y2 = (int)other.year & 0xFF;
      if (y1 < y2) return -1;
      if (y1 > y2) return 1;
    }
    if (!other.isMonthUnspecified() && !isMonthUnspecified() &&
      !other.isOddMonths() && !isOddMonths() &&
      !other.isEvenMonths() && !isEvenMonths())
    {
      if (month < other.month) return -1;
      if (month > other.month) return 1;
    }
    if (!other.isDayOfMonthUnspecified() && !isDayOfMonthUnspecified() &&
      !other.isLastDayOfMonth() && !isLastDayOfMonth() &&
      !other.isEvenDaysOfMonth() && !isEvenDaysOfMonth() &&
      !other.isOddDaysOfMonth() && !isOddDaysOfMonth())
    {
      if (dayOfMonth < other.dayOfMonth) return -1;
      if (dayOfMonth > other.dayOfMonth) return 1;
    }
    if (!other.isDayOfWeekUnspecified() && !isDayOfWeekUnspecified())
    {
      if (dayOfWeek < other.dayOfWeek) return -1;
      if (dayOfWeek > other.dayOfWeek) return 1;
    }
    return 0;
  }

  /**
   * @return true if the specified date is before this date.
   */
  public boolean isBefore(Object x)
  {
    return compareTo(x) < 0;
  }

  /**
   * @return true if the specified date is after this date.
   */
  public boolean isAfter(Object x)
  {
    return compareTo(x) > 0;
  }

  /**
   * @return true if the specified date is not before this date.
   */
  public boolean isNotBefore(Object x)
  {
    return compareTo(x) >= 0;
  }

  /**
   * @return true if the specified date is not after this date.
   */
  public boolean isNotAfter(Object x)
  {
    return compareTo(x) <= 0;
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Create a BAbsTime from this BBacnetDate.
   * Use values from the given BAbsTime for unspecified
   * fields and the time.
   */
  public BAbsTime makeBAbsTime(BAbsTime date)
  {
    int y = (isYearUnspecified() ? date.getYear() : getYear());
    BMonth m = ((isMonthUnspecified() || isOddMonths() || isEvenMonths()) ? date.getMonth() : getBMonth());
    int d = ((isDayOfMonthUnspecified() || isLastDayOfMonth()) ? date.getDay() : getDayOfMonth());
    return BAbsTime.make(y, m, d,
      date.getHour(), date.getMinute(),
      date.getSecond(), date.getMillisecond(),
      date.getTimeZone());
  }

  /**
   * Read the time values from the
   * given String and return a new BBacnetDate.
   *
   * @param s the input string.
   * @return a BBacnetDate read from the string.
   */
  public static BBacnetDate fromString(String s)
  {
    try
    {
      return (BBacnetDate)DEFAULT.decodeFromString(s);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "BBacnetDate.fromString('" + s + "'): error parsing string!!", e);
      return DEFAULT;
    }
  }

  private int weekday(String wkdayStr)
  {
    // First try unlexiconized names - this is the correct way.
    for (int i = 0; i < WEEKDAYS.length; i++)
    { if (wkdayStr.equals(WEEKDAYS[i])) { return (i + 1); } }

    // Try to handle pre-100.6 names, which were lexiconized (incorrect).
    for (int i = 0; i < WEEKDAYS_LEXKEYS.length; i++)
    { if (wkdayStr.equals(lex.getText(WEEKDAYS_LEXKEYS[i], null))) { return (i + 1); } }

    // Bad name, or it was lexiconized with a different lexicon
    // (now you see why lexiconizing things in the bog is bad!)
    throw new IllegalArgumentException("Invalid weekday:" + wkdayStr);
  }

  private void getHashCode()
  {
    hashCode = (year << 24) | (month << 16) | (dayOfMonth << 8) | dayOfWeek;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final byte UNSPECIFIED = -1;

  public static final byte ODD_MONTHS = 13;
  public static final byte EVEN_MONTHS = 14;

  public static final byte BAJA_ODD_MONTHS = 12;
  public static final byte BAJA_EVEN_MONTHS = 13;
  public static final byte LAST_DAY_OF_MONTH = 32;
  public static final byte ODD_DAYS_OF_MONTH = 33;
  public static final byte EVEN_DAYS_OF_MONTH = 34;
  public static final byte BAJA_ALWAYS_EFFECTIVE = -1;
  public static final byte BAJA_LAST_7_DAYS_OF_MONTH = 33;
  public static final byte BAJA_ODD_DAYS_OF_MONTH = 34;
  public static final byte BAJA_EVEN_DAYS_OF_MONTH = 35;

  private static LexiconModule lex = LexiconModule.make("baja");

  // Used in presentation/display
  private static final String[] WEEKDAYS_LEXKEYS =
    {
      "monday.short",
      "tuesday.short",
      "wednesday.short",
      "thursday.short",
      "friday.short",
      "saturday.short",
      "sunday.short"
    };

  // Used in serialization/deserialization only.
  private static final String[] WEEKDAYS =
    {
      "Mon",
      "Tue",
      "Wed",
      "Thu",
      "Fri",
      "Sat",
      "Sun"
    };

  /**
   * The length of the string returned by toFacetString().
   */
  static final int TEXT_LENGTH = 14;

  /**
   * The default date is all unspecified.
   */
  public static final BBacnetDate DEFAULT = new BBacnetDate(UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDate.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final byte year;
  private final byte month;
  private final byte dayOfMonth;
  private final byte dayOfWeek;
  private int hashCode;

  private static final Logger logger = Logger.getLogger("bacnet.datatypes");
}