/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.TextUtil;
import javax.baja.timezone.BTimeZone;
import javax.baja.timezone.DstRule;

import com.tridium.util.TimeFormat;

/**
 * BAbsTime encapsulates an absolute point in time
 * relative to a given time zone.  Its a Baja simple type
 * that standardizes to UTC millis since 1 Jan 70 (the
 * Java Epoch).
 *
 * @author    Brian Frank
 * @creation  11 Feb 00
 * @version   $Revision: 86$ $Date: 9/21/10 3:59:55 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BAbsTime
  extends BSimple
  implements BIDate, BITime, BIComparable, BIDataValue
{
  
////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance which maps to current
   * time with the default time zone.
   */
  public static BAbsTime make()
  {
    return new BAbsTime(System.currentTimeMillis(), BTimeZone.getLocal());
  }

  /**
   * Construct an instance which maps to current
   * time with the default time zone.
   */
  public static BAbsTime now()
  {
    return new BAbsTime(System.currentTimeMillis(), BTimeZone.getLocal());
  }

  /**
   * Constructor with a millis since epoch using
   * the default time zone.
   */
  public static BAbsTime make(long millis)
  {
    if (millis == 0) return NULL;
    if (millis >= BAbsTime.END_OF_TIME.getMillis()) return BAbsTime.END_OF_TIME;
    return new BAbsTime(millis, BTimeZone.getLocal());
  }

  /**
   * Constructor with millis since epoch, and a
   * given time zone.
   */
  public static BAbsTime make(long millis, BTimeZone timeZone)
  {
    return new BAbsTime(millis, timeZone);
  }

  /**
   * Constructor defaulting hours, minutes, seconds and millis
   * to zero, and using default timezone.
   */
  public static BAbsTime make(int year, BMonth month, int day)
  {
    return make(year, month, day, 0, 0, 0, 0, BTimeZone.getLocal(), null);
  }


  /**
   * Constructor defaulting seconds and millis
   * to zero, and using default timezone.
   */
  public static BAbsTime make(int year, BMonth month, int day, int hour, int min)
  {
    return make(year, month, day, hour, min, 0, 0, BTimeZone.getLocal(), null);
  }

  /**
   * Constructor with all fields using default timezone.
   */
  public static BAbsTime make(int year, BMonth month, int day,
                              int hour, int min, int sec, int millis)
  {
    return make(year, month, day, hour, min, sec, millis, BTimeZone.getLocal(), null);
  }

  /**
   * Constructor for all fields.
   */
  public static BAbsTime make(int year, BMonth month, int day, int hour,
                              int min, int sec, int millis, BTimeZone timeZone)
  {
    return make(year, month, day, hour, min, sec, millis, timeZone, null);
  }
  
  /**
   * Constructor for all fields.   If the given context contains the {@code TIME_MODE_FACET} facet,
   * the time will be interpreted accordingly, otherwise it will be interpreted according to the 
   * rules for {@code TIME_MODE_DEFAULT}.
   * 
   * @see BAbsTime#TIME_MODE_FACET
   * 
   * @since Niagara 3.5
   */
  public static BAbsTime make(int year, BMonth month, int day, int hour,
                              int min, int sec, int millis, BTimeZone timeZone, Context cx)
  {
    Calendar calendar = makeCalendar(year, month, day, hour, min, sec, millis, timeZone, cx);
    
    BAbsTime result = new BAbsTime(calendar.getTimeInMillis(), timeZone);
   
    // Calculate the bits now since we already have a calendar instance that
    // has done the field computations. Otherwise millisToFields() will do a lot
    // of redundant work.
    
    // set year bits
    int x = calendar.get(Calendar.YEAR);
    result.bits0 |= ((x & 0xFFFF) << 16);

    // set millisecond bits
    x = calendar.get(Calendar.MILLISECOND);
    result.bits0 |= ((x & 0xFFFF) << 0);

    // set month bits
    x = calendar.get(Calendar.MONTH);
    result.bits1 |= ((x & 0x0F) << 25);

    // set day bits
    x = calendar.get(Calendar.DAY_OF_MONTH);
    result.bits1 |= ((x & 0x1F) << 20);

    // set hour bits
    x = calendar.get(Calendar.HOUR_OF_DAY);
    result.bits1 |= ((x & 0x1F) << 15);

    // set minute bits
    x = calendar.get(Calendar.MINUTE);
    result.bits1 |= ((x & 0x3F) << 9);

    // set seconds bits
    x = calendar.get(Calendar.SECOND);
    result.bits1 |= ((x & 0x3F) << 3);

    // set weekday
    x = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    result.bits1 |= ((x & 0x07) << 0);

    // set dst offset
    if (calendar.get(Calendar.DST_OFFSET) != 0)
      result.bits1 |= (0x01 << 29);    
    
    return result;
  }
  
  /**
   * Returns a java.util.Calendar object for the given date, wall time, and 
   * BTimeZone.  If the given context contains the {@code TIME_MODE_FACET} facet,
   * the time will be interpreted accordingly, otherwise it will be interpreted according to the 
   * rules for {@code TIME_MODE_DEFAULT}.
   * 
   * @see BAbsTime#TIME_MODE_FACET
   * 
   * @since Niagara 3.5
   */
  public static Calendar makeCalendar(int year, BMonth month, int day, int hour,
                                      int min, int sec, int millis, BTimeZone timeZone, Context cx)
  {
    int timeMode = (cx == null) ? TIME_MODE_DEFAULT : cx.getFacets().geti(TIME_MODE_FACET, TIME_MODE_DEFAULT);
    
    Calendar result = new GregorianCalendar(timeZone.getJavaTimeZone());
    boolean convertResultToWall = false;
    
    if (timeMode == TIME_MODE_UTC)
    {
      result.set(Calendar.ZONE_OFFSET, 0);
      result.set(Calendar.DST_OFFSET, 0);
      convertResultToWall = true;
    }
    else if (timeMode == TIME_MODE_DAYLIGHT)
    {
      result.set(Calendar.ZONE_OFFSET, timeZone.getUtcOffset());
      result.set(Calendar.DST_OFFSET, timeZone.getDaylightAdjustment());
      convertResultToWall = true;
    }
    else if (timeMode == TIME_MODE_STANDARD)
    {
      result.set(Calendar.ZONE_OFFSET, timeZone.getUtcOffset());
      result.set(Calendar.DST_OFFSET, 0);
      convertResultToWall = true;
    }
    else if ((timeMode == TIME_MODE_DEFAULT) ||
              (timeMode == TIME_MODE_WALL_STRICT) ||
              (timeMode == TIME_MODE_WALL_SCHD))
    {
      boolean checkEndTime = (timeMode == TIME_MODE_WALL_STRICT);
      DstRule startRule = timeZone.getDaylightStartRule();
      
      if (startRule != null)
      {
        BMonth startMonth = startRule.getMonth();   
        int startDay = DstRule.getUtcDayOfMonth(year, startRule);
        long startTimeStdMillis = Long.MIN_VALUE;  // start time in wall/std time-of-day millis, min value means uninitialized
        
        if (startRule.getTimeMode() == DstRule.UTC_TIME)
        {
          int daysInMonth = getDaysInMonth(year, startMonth);
          
          // convert start rule UTC time to wall/std time by adding UTC offset
          startTimeStdMillis = startRule.getTime().getTimeOfDayMillis() + timeZone.getUtcOffset(); 
          
          while (startTimeStdMillis >= BRelTime.MILLIS_IN_DAY)
          {
            // If DST starts in a later local day than the UTC day, we need to roll
            // startDay forward
            startTimeStdMillis -= BRelTime.MILLIS_IN_DAY;
            startDay++;  
            if (startDay > daysInMonth)
            {
              startMonth = startMonth.next();  // Assume we won't roll into next year - that's just silly
              startDay = 1;
              daysInMonth = getDaysInMonth(year, startMonth);
            }
          }
          
          while (startTimeStdMillis < 0)
          {
            // If DST starts in an earlier local day than the UTC day, we need to roll
            // startDay backward
            startTimeStdMillis += BRelTime.MILLIS_IN_DAY;
            startDay--;
            if (startDay < 1)
            {
              startMonth = startMonth.previous();  // Assume we won't roll into last year - that's just silly
              daysInMonth = getDaysInMonth(year, startMonth);
              startDay = daysInMonth;
            }
          }
        }
        
        if (month.equals(startMonth))
        {
          checkEndTime = false;
          if (day == startDay)
          {
            long timeOfDayMillis = BTime.make(hour, min, sec, millis).getTimeOfDayMillis();
            
            if (startTimeStdMillis == Long.MIN_VALUE) startTimeStdMillis = startRule.getTime().getTimeOfDayMillis();
            
            // The "lost time" range is the time that never shows on the wall clock because of
            // the DST adjustment
            
            long lostTimeEndMillis = startTimeStdMillis + timeZone.getDaylightAdjustment();
            
            if ((timeOfDayMillis >= startTimeStdMillis) && (timeOfDayMillis < lostTimeEndMillis)) 
            {
              // The given time is during the period of wall time that is skipped when standard time transitions
              // to daylight time
              
              // We do this extra processing because Sun's GregorianCalendar implementation doesn't work correctly
              // with TimeZone instances that aren't sun.util.calendar.ZoneInfo subclasses
              
              if (timeMode == TIME_MODE_WALL_STRICT)
              {
                throw new LocalizableRuntimeException("baja", "AbsTime.invalidWallTime", new Object[] {BTime.make(hour, min, sec, millis) });
              }
              
              BTime lostTimeEnd;
              if (timeMode == TIME_MODE_DEFAULT)
              {
                lostTimeEnd = BTime.make(BRelTime.make(timeOfDayMillis + timeZone.getDaylightAdjustment()));
              }
              else // TIME_MODE_WALL_SCHD
              {
                lostTimeEnd = BTime.make(BRelTime.make(lostTimeEndMillis));
              }
              hour = lostTimeEnd.getHour();
              min = lostTimeEnd.getMinute();
              sec = 0;
              millis = 0;
            }
          }
        }
      }
      
      if (checkEndTime)  // mode is TIME_MODE_WALL_STRICT and we didn't match the start time
      {
        DstRule endRule = timeZone.getDaylightEndRule();
        
        if (endRule != null)
        {
          BMonth endMonth = endRule.getMonth();   
          int endDay = DstRule.getUtcDayOfMonth(year, endRule);
          long endTimeStdMillis = Long.MIN_VALUE;  // end time in standard time-of-day millis, min value means uninitialized
          
          if (endRule.getTimeMode() == DstRule.UTC_TIME)
          {
            int daysInMonth = getDaysInMonth(year, endMonth);
            
            // convert end rule UTC time to standard time by adding UTC offset
            endTimeStdMillis = endRule.getTime().getTimeOfDayMillis() + timeZone.getUtcOffset(); 
            
            while (endTimeStdMillis >= BRelTime.MILLIS_IN_DAY)
            {
              // If DST ends in a later local day than the UTC day, we need to roll
              // endDay forward
              endTimeStdMillis -= BRelTime.MILLIS_IN_DAY;
              endDay++;  
              if (endDay > daysInMonth)
              {
                endMonth = endMonth.next();  // Assume we won't roll into next year - that's just silly
                endDay = 1;
                daysInMonth = getDaysInMonth(year, endMonth);
              }
            }
            
            while (endTimeStdMillis < 0)
            {
              // If DST ends in an earlier local day than the UTC day, we need to roll
              // endDay backward
              endTimeStdMillis += BRelTime.MILLIS_IN_DAY;
              endDay--;
              if (endDay < 1)
              {
                endMonth = endMonth.previous();  // Assume we won't roll into last year - that's just silly
                daysInMonth = getDaysInMonth(year, endMonth);
                endDay = daysInMonth;
              }
            }
          }
          else if (endRule.getTimeMode() == DstRule.WALL_TIME)
          {
            int daysInMonth = getDaysInMonth(year, endMonth);
            endTimeStdMillis = endRule.getTime().getTimeOfDayMillis() - timeZone.getDaylightAdjustment();
            
            while (endTimeStdMillis < 0)
            {
              // If DST ends in an earlier local day than the UTC day, we need to roll
              // endDay backward
              endTimeStdMillis += BRelTime.MILLIS_IN_DAY;
              endDay--;
              if (endDay < 1)
              {
                endMonth = endMonth.previous();  // Assume we won't roll into last year - that's just silly
                daysInMonth = getDaysInMonth(year, endMonth);
                endDay = daysInMonth;
              }
            }
          }
          
          if (month.equals(endMonth))
          {
            checkEndTime = false;
            if (day == endDay)
            {
              long timeOfDayMillis = BTime.make(hour, min, sec, millis).getTimeOfDayMillis();
              
              if (endTimeStdMillis == Long.MIN_VALUE) endTimeStdMillis = endRule.getTime().getTimeOfDayMillis();
              
              // The "ambiguous" range covers the times that appear on the clock twice that day, once as daylight
              // then again as standard
              
              long ambigTimeEndMillis = endTimeStdMillis + timeZone.getDaylightAdjustment();
              
              if ((timeOfDayMillis >= endTimeStdMillis) && (timeOfDayMillis < ambigTimeEndMillis)) 
              {
                throw new LocalizableRuntimeException("baja", "AbsTime.ambigWallTime", new Object[] {BTime.make(hour, min, sec, millis) });
              }
            }
          }
        }
      }
    }
    
    result.set(year, month.getOrdinal(), day, hour, min, sec);
    result.set(Calendar.MILLISECOND, millis);
    
    if (convertResultToWall)
    {
      long calMillis = result.getTimeInMillis();
      result = new GregorianCalendar(timeZone.getJavaTimeZone());
      result.setTimeInMillis(calMillis);
    }
    return result;
  }
  
  /**
   * The resulting instance represents the date and timezone of the first
   * argument and the time of day of the second.
   */
  public static BAbsTime make(BAbsTime date, BTime time)
  {
    return make(date.getYear(),
                date.getMonth(),
                date.getDay(),
                time.getHour(),
                time.getMinute(),
                time.getSecond(),
                time.getMillisecond(),
                date.getTimeZone());
  }

  /**
   * Construct a BAbsTime from a date, time, and timezone.
   */
  public static BAbsTime make(BDate date, BTime time, BTimeZone zone)
  {
    return make(
      date.getYear(),
      date.getMonth(),
      date.getDay(),
      time.getHour(),
      time.getMinute(),
      time.getSecond(),
      time.getMillisecond(),
      zone);
  }

  /**
   * Construct a BAbsTime for the same instant in time
   * but relative to another time zone.
   */
  public static BAbsTime make(BAbsTime absTime, BTimeZone timeZone)
  {
    if (absTime.timeZone == timeZone) return absTime;
    return make(absTime.millis, timeZone);
  }

  /**
   * Construct a BAbsTime for the specified time on the
   * specified day of the year.
   */
  public static BAbsTime makeDayOfYear(int year,
                                       int dayOfYear,
                                       int hour, int min, int sec, int ms,
                                       BTimeZone timeZone)
  {
    int daysInYear = getDaysInYear(year);
    if (dayOfYear > daysInYear)
      throw new IllegalArgumentException(dayOfYear + " > " + daysInYear);
    if (dayOfYear < 1)
      throw new IllegalArgumentException(dayOfYear + " < 1");

    // After this loop is completes, month will be the result month
    // and dayOfYear will be the day of the month.
    BMonth month = BMonth.january;
    int daysInMonth = getDaysInMonth(year, month);
    while(dayOfYear > daysInMonth)
    {
      dayOfYear -= daysInMonth;
      month = month.next();
      daysInMonth = getDaysInMonth(year, month);
    }

    return BAbsTime.make(year, month, dayOfYear, hour, min, sec, ms, timeZone);
  }

  /**
   * Factory from string encoding.
   */
  public static BAbsTime make(String s)
    throws IOException
  {
    return (BAbsTime)DEFAULT.decodeFromString(s);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BAbsTime(long millis, BTimeZone timeZone)
  {
    this.millis = millis;
    this.timeZone = timeZone;
  }

////////////////////////////////////////////////////////////////
// Get Functions
////////////////////////////////////////////////////////////////

  /**
   * @return millis since the epoch relative to UTC.  This
   *    result is independent of this BAbsTime's time zone.
   */
  public long getMillis()
  {
    return millis;
  }

  /**
   * @return The year as a four digit integer (ie 2001).
   */
  @Override
  public final int getYear()
  {
    if (bits0 == 0) millisToFields();
    return (bits0 >> 16) & 0xFFFF;
  }

  /**
   * @return the month as a BMonth.
   */
  @Override
  public final BMonth getMonth()
  {
    if (bits0 == 0) millisToFields();
    return BMonth.make( (bits1 >> 25) & 0x0F );
  }

  /**
   * @return The day: 1-31.
   */
  @Override
  public final int getDay()
  {
    if (bits0 == 0) millisToFields();
    return (bits1 >> 20) & 0x1F;
  }

  /**
   * @return The hour: 0-23.
   */
  @Override
  public final int getHour()
  {
    if (bits0 == 0) millisToFields();
    return (bits1 >> 15) & 0x1F;
  }

  /**
   * @return The minute: 0-59.
   */
  @Override
  public final int getMinute()
  {
    if (bits0 == 0) millisToFields();
    return (bits1 >> 9) & 0x3F;
  }

  /**
   * @return The seconds: 0-59.
   */
  @Override
  public final int getSecond()
  {
    if (bits0 == 0) millisToFields();
    return (bits1 >> 3) & 0x3F;
  }

  /**
   * @return The milliseconds: 0-999.
   */
  @Override
  public final int getMillisecond()
  {
    if (bits0 == 0) millisToFields();
    return bits0 & 0xFFFF;
  }

  /**
   * @return the weekday as a BWeekday enum.
   */
  @Override
  public final BWeekday getWeekday()
  {
    if (bits0 == 0) millisToFields();
    return BWeekday.make( bits1 & 0x07 );
  }

  /**
   * Get the day of the week for the specified date.
   */
  public static BWeekday getWeekday(int year, BMonth month, int day)
  {
    //This calculation does not involve time so we don't need to consider time of day, dst or anything, we
    //just want to know what day of the week something falls on in a particular year!
    Calendar cal = new GregorianCalendar(BTimeZone.getJavaUTCInstance());
    cal.set(year, month.getOrdinal(), day);
    
    int wd = cal.get(Calendar.DAY_OF_WEEK);
    if (wd == Calendar.SUNDAY) return BWeekday.sunday;
    if (wd == Calendar.MONDAY) return BWeekday.monday;
    if (wd == Calendar.TUESDAY) return BWeekday.tuesday;
    if (wd == Calendar.WEDNESDAY) return BWeekday.wednesday;
    if (wd == Calendar.THURSDAY) return BWeekday.thursday;
    if (wd == Calendar.FRIDAY) return BWeekday.friday;
    if (wd == Calendar.SATURDAY) return BWeekday.saturday;

    throw new BajaRuntimeException("Unrecognized weekday: " + wd);
  }

  /**
   * Get the number of milliseconds into the day
   * for this BAbsTime.  An example is that 1:00 AM
   * would return 3600000.
   */
  @Override
  public final long getTimeOfDayMillis()
  {
    return getHour()   * 60*60*1000L +
           getMinute() * 60*1000L +
           getSecond() * 1000L +
           getMillisecond();
  }

  /**
   * Get the day of the year for this BAbsTime.  An
   * example is that Feb. 1, 2000 would return 32.  The
   * method does account for leap years.
   */
  @Override
  public final int getDayOfYear()
  {
    int thisYear = getYear();
    BMonth thisMonth = getMonth();

    int dayOfYear = 0;
    for (BMonth m = BMonth.january; m != thisMonth; m = m.next())
      dayOfYear += getDaysInMonth(thisYear, m);
    dayOfYear += getDay();

    return dayOfYear;
  }

////////////////////////////////////////////////////////////////
// BTimeZone
////////////////////////////////////////////////////////////////

  /**
   * @return A date using the day, month, and year of this BAbsTime,
   * relative to this BAbsTime's time zone.
   */
  public BDate getDate()
  {
    return BDate.make(
      getYear(), 
      getMonth(), 
      getDay());
  }

  /**
   * @return A time using the hour, minute, second, and millisecond of this BAbsTime,
   * relative to this BAbsTime's time zone.
   */
  public BTime getTime()
  {
    return BTime.make(
      getHour(), 
      getMinute(), 
      getSecond(),
      getMillisecond());
  }

  /**
   * @return BTimeZone this BAbsTime's fields are relative to.
   */
  public BTimeZone getTimeZone()
  {
    return timeZone;
  }

  /**
   * @return the offset in millis from GMT taking daylight
   * savings time into account if appropriate.
   */
  public int getTimeZoneOffset()
  {
    //Return the active UTC offset of the zone at the specified date.
    return timeZone.getCurrentUtcOffset(millis);
  }

  /**
   * Does this time fall in daylight savings time
   * based on the current BTimeZone.
   */
  public boolean inDaylightTime()
  {
    if (bits0 == 0) millisToFields();
    return ((bits1 >> 29) & 0x01) != 0;
  }

  /**
   * Get a localized String for the time zone's long name.
   * Refer to BTimeZone.getDisplayName() for semantics.
   */
  public String getTimeZoneName(Context cx)
  {
    return timeZone.getDisplayName(this, cx);
  }

  /**
   * Get a localized String for the time zone's short name.
   * Refer to BTimeZone.getShotDisplayName() for semantics.
   */
  public String getTimeZoneShortName(Context cx)
  {
    return timeZone.getShortDisplayName(this, cx);
  }

  /**
   * Convert this instance to an equivalent instance in the
   * current VM's local time zone.
   */
  public BAbsTime toLocalTime()
  {
    if (timeZone.equals(BTimeZone.getLocal()))
      return this;
    else
      return BAbsTime.make(this, BTimeZone.getLocal());
  }

  /**
   * Convert this instance to an equivalent instance in UTC.
   */
  public BAbsTime toUtcTime()
  {
    if (timeZone.equals(BTimeZone.UTC))
      return this;
    else
      return BAbsTime.make(this, BTimeZone.UTC);
  }

  /**
   * <p>
   * Convert this instance to a timezoneless time at the same time of day.
   * This is useful when normalizing times in different timezones so that
   * corresponding values can be compared by time of day.
   * </p>
   * <p>
   * For example, 8:00AM in PST and 8:00AM in EST would normalize to the same
   * BAbsTime value.
   * </p>
   */
  public BAbsTime toNormalizedTime()
  {
    if (timeZone.equals(BTimeZone.NULL))
      return this;
    else
      return BAbsTime.make(this, BTimeZone.NULL);
  }

////////////////////////////////////////////////////////////////
// Algebra
////////////////////////////////////////////////////////////////

  /**
   * Add a relative time to this time and return
   * the new instant in time.
   */
  public BAbsTime add(BRelTime relTime)
  {
    return make(millis+relTime.getMillis(), timeZone);
  }

  /**
   * Subtract a relative time from this time and
   * return the new instant in time.
   */
  public BAbsTime subtract(BRelTime relTime)
  {
    return make(millis-relTime.getMillis(), timeZone);
  }

  /**
   * Compute the time difference between this time and the specified time.  If
   * t2 is after this time, the result will be positive.  If t2 is before
   * this time, the result will be negative.
   *
   * @param t2 The time to compare against.
   */
  public BRelTime delta(BAbsTime t2)
  {
    return BRelTime.make(t2.millis - millis);
  }

  /**
   * Create a new instance on the same date as this instance
   * but with a different time.
   */
  public BAbsTime timeOfDay(int hour, int min, int sec, int millis)
  {
    return make(getYear(), getMonth(), getDay(), hour, min, sec, millis, BTimeZone.getLocal());
  }

  /**
   * The same time on the next day.
   */
  public BAbsTime nextDay()
  {
    int year  = getYear();
    int month = getMonth().getOrdinal();
    int day   = getDay();
    
    if (day == getDaysInMonth(year,BMonth.make(month)))
    {
      day = 1;
      if (month == 11)
      {
        month = 0;
        year++;
      }
      else
      {
        month++;
      }
    }
    else
    {
      day++;
    }
    return make(year, BMonth.make(month), day,
                getHour(), getMinute(), getSecond(),
                getMillisecond(), getTimeZone());
  }

  /**
   * The same time on the previous day.
   */
  public BAbsTime prevDay()
  {
    int year  = getYear();
    int month = getMonth().getOrdinal();
    int day   = getDay();
    
    if (day == 1)
    {
      if (month == 0)
      {
        month = 11;
        year--;
      }
      else
      {
        month--;
      }
      day = getDaysInMonth(year,BMonth.make(month));
    }
    else
    {
      day--;
    }
    return make(year, BMonth.make(month), day,
                getHour(), getMinute(), getSecond(),
                getMillisecond(), getTimeZone());
  }

  /**
   * The same day and time in the next month.  If
   * this day is greater than the last day in the
   * next month, then cap the day to the next month's
   * last day.  If this time's day is the last day
   * in this month, then we automatically set the
   * month to the next month's last day.
   */
  public BAbsTime nextMonth()
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
      if (day == getDaysInMonth(year, BMonth.make(month)))
      {
        month++;
        day = getDaysInMonth(year, BMonth.make(month));
      }
      else
      {
        month++;
        if (day > getDaysInMonth(year, BMonth.make(month)))
          day = getDaysInMonth(year, BMonth.make(month));
      }
    }
    return make(year, BMonth.make(month), day,
                getHour(), getMinute(), getSecond(),
                getMillisecond(), getTimeZone());
  }

  /**
   * The same time and day in previous month. If
   * this day is greater than the last day in the
   * prev month, then cap the day to the prev month's
   * last day.  If this time's day is the last day
   * in this month, then we automatically set the
   * month to the prev month's last day.
   */
  public BAbsTime prevMonth()
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
      if (day == getDaysInMonth(year, BMonth.make(month)))
      {
        month--;
        day = getDaysInMonth(year, BMonth.make(month));
      }
      else
      {
        month--;
        if (day > getDaysInMonth(year, BMonth.make(month)))
          day = getDaysInMonth(year, BMonth.make(month));
      }
    }
    return make(year, BMonth.make(month), day,
                getHour(), getMinute(), getSecond(),
                getMillisecond(), getTimeZone());
  }

  /**
   * Get the same time and day in next year.  If today
   * is a leap day, then return next year Feb 28.
   */
  public BAbsTime nextYear()
  {
    int day = getDay();
    if (isLeapDay()) day = 28;
    return make(getYear()+1, getMonth(), day,
                getHour(), getMinute(), getSecond(),
                getMillisecond(), getTimeZone());
  }

  /**
   * Get the same time and day in prev year.  If today
   * is a leap day, then return prev year Feb 28.
   */
  public BAbsTime prevYear()
  {
    int day = getDay();
    if (isLeapDay()) day = 28;
    return make(getYear()-1, getMonth(), day,
                getHour(), getMinute(), getSecond(),
                getMillisecond(), getTimeZone());
  }

  /**
   * Get the next day of the specified weekday. If
   * today is the specified weekday, then return one
   * week from now.
   */
  public BAbsTime next(BWeekday weekday)
  {
    BAbsTime t = nextDay();
    while(t.getWeekday() != weekday)
      t = t.nextDay();
    return t;
  }

  /**
   * Get the prev day of the specified weekday. If
   * today is the specified weekday, then return one
   * week before now.
   */
  public BAbsTime prev(BWeekday weekday)
  {
    BAbsTime t = prevDay();
    while(t.getWeekday() != weekday)
      t = t.prevDay();
    return t;
  }

////////////////////////////////////////////////////////////////
// Comparison
////////////////////////////////////////////////////////////////

  /**
   * Compare to another BAbsTime.
   * @return a negative integer, zero, or a
   *    positive integer as this object is less
   *    than, equal to, or greater than the
   *    specified object.
   */
  @Override
  public int compareTo(Object obj)
  {
    BAbsTime t = (BAbsTime)obj;
    if (this.millis < t.millis) return -1;
    else if (this.millis == t.millis) return 0;
    else return 1;
  }

  /**
   * @return true if the specified time is before this time.
   */
  public boolean isBefore(BAbsTime x)
  {
    return compareTo(x) < 0;
  }

  /**
   * @return true if the specified time is after this time.
   */
  public boolean isAfter(BAbsTime x)
  {
    return compareTo(x) > 0;
  }

  /**
   * BAbsTime hash code is based on the
   * the absolute time in millis.
   */
  public int hashCode()
  {
   return (int)(millis ^ (millis >> 32));
  }

  /**
   * Equality is based only on absolute time, not
   * the relative time zone.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BAbsTime)
    {
      return ((BAbsTime)obj).millis == millis;
    }      
    return false;
  }

  /**
   * Is the date of the specified instance equal to the date of this instance?
   */
  public boolean dateEquals(BAbsTime other)
  {
    return (other.getYear() == getYear()) &&
           (other.getMonth() == getMonth()) &&
           (other.getDay() == getDay());
  }

  /**
   * Is the time of the specified instance equal to the date of this instance?
   */
  public boolean timeEquals(BAbsTime other)
  {
    return other.getTimeOfDayMillis() == getTimeOfDayMillis();
  }

////////////////////////////////////////////////////////////////
// Leap Years
////////////////////////////////////////////////////////////////

  /**
   * Return if today is Feb 29.
   */
  @Override
  public boolean isLeapDay()
  {
    return (getMonth() == BMonth.february) && (getDay() == 29);
  }

  /**
   * Return if the specified year (as a four digit
   * number) is a leap year.
   */
  public static boolean isLeapYear(int year)
  {
    if (year >= 1582)
    {
      // Gregorian
      return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
    }
    else
    {
      // Julian
      return (year % 4 == 0);
    }
  }
  
  private static final int[] daysInMonth =
  { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
  
  /**
   * Given a year and month, return the number of days
   * in that month taking into consideration leap years.
   */
  public static int getDaysInMonth(int year, BMonth month)
  {
    if (month == BMonth.february)
      return isLeapYear(year) ? 29 : 28;
    else
      return daysInMonth[month.getOrdinal()];
  }

  /**
   * Given a year, return the number of days in that
   * year taking into consideration leap years.
   */
  public static int getDaysInYear(int year)
  {
    return isLeapYear(year) ? 366 : 365;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BAbsTime is serialized using writeLong()
   * of milliseconds since UTC epoch.
   * @throws IOException
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeLong(millis);
  }

  /**
   * BAbsTime is deserialized from an 8 byte long created
   * by encode() or from 6 bytes created by  encode48.
   * @throws IOException
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    long timeInMillis = 0;
    int firstByte = in.readUnsignedByte();
    if(firstByte==0)
    {
      // This is original encoding - first two bytes always zero
      in.readByte();
      timeInMillis = timeInMillis | ((long)(in.readByte() & 0xff )) << (40);
    }
    else
    {
      // This is 48 bit encoding - most significant nibble has 1 added (see Note)
      timeInMillis = timeInMillis | ((long)((firstByte - 0x10) & 0xff )) << 40;
    }

    for (int i = 4 ; i >=0   ; --i)
      timeInMillis = timeInMillis | ((long)(in.readByte() & 0xff )) << (8 * i);

    return make(timeInMillis);
  }

  // Note: 48bit encoding has been added to compress db records containing absTime (NCCB-8645).  To provide a means
  // to detect the new encoding on the wire, encode48 will take advantage of the fact that the maximum time,
  // end-of-time, has 0xE in the most significant nibble of millis.  Valid values for the most significant nibble for
  // 48bit millis are 0-e, by adding 1 to this nibble during encoding the valid encoded values are 1-f.
  // The only valid value of the most significant nibble in the original encoding is 0. It is therefore possible for
  // decode() to detect which method was used to encode the value.

  /**
   * Serialize BAbsTime as 6 byte representaion of milliseconds since UTC epoch.
   * @throws IOException
   */
 public void encode48(DataOutput out) throws IOException
  {
    // Add 1 to most significant nibble (see Note).  Write most significant bytes first.
    out.write((byte)(((millis >> 40) + 0x10) & 0xff));
    for (int i = 4; i >=0; --i)
      out.write((byte)((millis >> (8 * i)) & 0xff));
  }

  /**
   * Deserialize BAbsTime using decode() which can reverse encoding created by encode48() or encode().
   * @throws IOException
   */
  public BObject decode48(DataInput input) throws IOException
  {
    return decode(input);
  }

  /**
   * Deserialize BAbsTime by reading a full 64-bit long value that reverses the encoding created by
   * encode() only. It does not try to detect if the encoding was created by encode48(). This is
   * required when the number of millis is a negative value and it is impossible to distinguish a
   * 64-bit and 48-bit encoded value.
   * @since Niagara 4.10u4
   * @since Niagara 4.12
   */
  public BObject decode64(DataInput in) throws IOException
  {
    return make(in.readLong());
  }

  /**
   * Write the simple in text format using the ISO 8601
   * standard format of "yyyy-mm-ddThh:mm:ss.mmm[+/-]hh:mm".
   */
  @Override
  public String encodeToString()
  {
    StringBuilder s = new StringBuilder(32);

    if(getYear()>9999)
      throw new IllegalStateException("Year must be < 9999.");
    
    s.append(TextUtil.padZeros(String.valueOf(getYear()), 4)).append('-');
    
    int month = getMonth().getOrdinal() + 1;
    s.append(TextUtil.padZeros(String.valueOf(month), 2)).append('-');

    int day = getDay();
    s.append(TextUtil.padZeros(String.valueOf(day), 2)).append('T');

    int hour = getHour();
    s.append(TextUtil.padZeros(String.valueOf(hour), 2)).append(':');

    int min = getMinute();
    s.append(TextUtil.padZeros(String.valueOf(min), 2)).append(':');

    int sec = getSecond();
    s.append(TextUtil.padZeros(String.valueOf(sec), 2)).append('.');

    int millis = getMillisecond();
    s.append(TextUtil.padZeros(String.valueOf(millis), 3));
        
    int offset = getTimeZoneOffset();
    if (offset == 0)
    {
      s.append('Z');
    }
    else
    {
      int hrOff = Math.abs(offset / (1000*60*60));
      int minOff = Math.abs((offset % (1000*60*60)) / (1000*60));

      if (offset < 0) s.append('-');
      else s.append('+');

      s.append(TextUtil.padZeros(String.valueOf(hrOff), 2)).append(':');
      s.append(TextUtil.padZeros(String.valueOf(minOff), 2));
    }
    
    return s.toString();
  }

  /**
   * Read the simple from text format normalizing
   * to the BAbsTime's current time zone.  This
   * text must strictly conform to the ISO 8601 standard
   * format of "yyyy-mm-ddThh:mm:ss.mmm[+/-]hh:mm".
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    char[] c = s.toCharArray();
    try
    {
      int i = 0;

      int year = (c[i++] - '0') * 1000 +
                 (c[i++] - '0') * 100 +
                 (c[i++] - '0') * 10 +
                 (c[i++] - '0') * 1;

      if (c[i++] != '-') throw new Exception();

      int mon = (c[i++] - '0') * 10 +
                (c[i++] - '0') * 1;

      if (c[i++] != '-') throw new Exception();

      int day = (c[i++] - '0') * 10 +
                (c[i++] - '0') * 1;

      if (c[i++] != 'T') throw new Exception();

      int hour = (c[i++] - '0') * 10 +
                 (c[i++] - '0') * 1;

      if (c[i++] != ':') throw new Exception();

      int min = (c[i++] - '0') * 10 +
                (c[i++] - '0') * 1;

      if (c[i++] != ':') throw new Exception();

      int sec = (c[i++] - '0') * 10 +
                (c[i++] - '0') * 1;

      int ms = 0;
      if (c[i] == '.')
      {
        i++;
        ms = (c[i++] - '0') * 100;
        if ('0' <= c[i] && c[i] <= '9') ms += (c[i++] - '0') * 10;
        if ('0' <= c[i] && c[i] <= '9') ms += (c[i++] - '0') * 1;

        // skip any additional fractional digits
        while(i < c.length && '0' <= c[i]  && c[i] <= '9') i++;
      }

      // timezone offset sign
      int tzOff = 0;
      char sign = c[i++];
      if (sign != 'Z')
      {
        if (sign != '+' && sign != '-')
          throw new Exception();

        // timezone hours
        int hrOff = c[i++] - '0';
        if (i < c.length && c[i] != ':')
          hrOff = hrOff*10 + c[i++] - '0';

        // timezone minutes
        int minOff = 0;
        if (i < c.length)
        {
          if (c[i++] != ':') throw new Exception();
          minOff = 10* (c[i++] - '0') + c[i++] - '0';
        }

        tzOff = hrOff*(60*60*1000) + minOff*(60*1000);
        if (sign == '-') tzOff *= -1;
      }

      Calendar cal = new GregorianCalendar(new SimpleTimeZone(tzOff, "Offset"));
      cal.set(year, mon-1, day, hour, min, sec);
      cal.set(Calendar.MILLISECOND, ms);
      
      return make(cal.getTime().getTime());
    }
    catch(Exception e)
    {
      throw new IOException("Invalid BAbsTime: " + s);
    }
  }

  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue() { return this; }

  /**
   * Test for the null value.
   */
  @Override
  public boolean isNull()
  {
    return millis == 0;
  }

////////////////////////////////////////////////////////////////
// Formatting
////////////////////////////////////////////////////////////////

  /**
   * Get just the date as a string.
   */
  public String toDateString(Context context)
  {
    if (millis == 0) return "null";
    return TimeFormat.formatDate(this, context);
  }

  /**
   * Get just the time as a string.
   */
  public String toTimeString(Context context)
  {
    if (millis == 0) return "null";
    return TimeFormat.formatTime(this, context);
  }

  /**
   * Get a formatted string for the time.  Use the following context
   * facets to customize the format: SHOW_TIME, SHOW_DATE, SHOW_SECONDS,
   * SHOW_MILLISECONDS, SHOW_TIME_ZONE, and TIME_ZONE.
   */
  @Override
  public String toString(Context context)
  {
    if (millis == 0) return "null";
    return TimeFormat.format(this, context);
  }

////////////////////////////////////////////////////////////////
// Millis To Fields
////////////////////////////////////////////////////////////////

  /**
   * Map millis and timeZone to its component fields.
   *
   * Bits0:
   *  ------------------------------------------------
   *  Field    Num Bits  Range    Loc
   *  ------------------------------------------------
   *  Year       16      short    16-31
   *  Millis     16      short    0-15
   *
   * Bits1:
   *  ------------------------------------------------
   *  Field    Num Bits  Range    Loc
   *  ------------------------------------------------
   *  Daylight    1       0-1     29-29
   *  Month       4       1-12    25-28
   *  Day         5       1-31    20-24
   *  Hour        5       0-23    15-19
   *  Minutes     6       0-59    9-14
   *  Seconds     6       0-59    3-8
   *  Weekday     3       0-6     0-2
   * ------------------------------------------------
   */
  private void millisToFields()
  {
    // init a calendar with timeZone and millis
    TimeZone utilTz = (TimeZone)timeZone.tzSupport();
    if (utilTz == null)
    {
      throw new LocalizableRuntimeException("baja",
                                            "AbsTime.unsupportedTimeZone",
                                            new Object[] { timeZone.getId() });
    }
    
    //intializing a calendar with a historical timezone is much more expensive
    //then initializing a calendar with a simple timezone; however, how do
    //we get historical performance with a SimpleTimeZone? We can actually
    //take advantage of the fact that we already know this BAbsTime's millis
    //and retrieve the proper offset, even with respect to dst. We then can 
    //create a SimpleTimeZone (with an arbitrary name) and assign it this offset
    //to correctly resolve the Epoch millis to fields. As precedence, we already
    //employ such a method in the decodeFromString method of BAbsTime
    
    //retrieve the offset that is in effect for the millis that this BAbsTime is assigned 
    int offset = utilTz.getOffset(millis);
    
    //create a new calendar based on the simple timezone driven by the previous offset
    Calendar calendar = new GregorianCalendar(new SimpleTimeZone(offset, "Offset"));
    
    //set the calendar to the time that we are looking for.
    calendar.setTimeInMillis(millis);

    int zeroBitsToSet = 0;
    int oneBitsToSet = 0;

    // set year bits
    int x = calendar.get(Calendar.YEAR);
    zeroBitsToSet |= ((x & 0xFFFF) << 16);

    // set millisecond bits
    x = calendar.get(Calendar.MILLISECOND);
    zeroBitsToSet |= ((x & 0xFFFF) << 0);

    // set month bits
    x = calendar.get(Calendar.MONTH);
    oneBitsToSet |= ((x & 0x0F) << 25);

    // set day bits
    x = calendar.get(Calendar.DAY_OF_MONTH);
    oneBitsToSet |= ((x & 0x1F) << 20);

    // set hour bits
    x = calendar.get(Calendar.HOUR_OF_DAY);
    oneBitsToSet |= ((x & 0x1F) << 15);

    // set minute bits
    x = calendar.get(Calendar.MINUTE);
    oneBitsToSet |= ((x & 0x3F) << 9);

    // set seconds bits
    x = calendar.get(Calendar.SECOND);
    oneBitsToSet |= ((x & 0x3F) << 3);

    // set weekday
    x = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    oneBitsToSet |= ((x & 0x07) << 0);

    //utilTz can determine very quickly if we are in dst or not (much faster than 2 babstimes can..)
    if (utilTz.inDaylightTime(calendar.getTime()))
      oneBitsToSet |= (0x01 << 29);

    bits1 = oneBitsToSet;
    bits0 = zeroBitsToSet;
  }

////////////////////////////////////////////////////////////////
// Schema
////////////////////////////////////////////////////////////////

  /**
   * Get default BAbsTime constant is 0 millis from epoch.
   */
  public static final BAbsTime DEFAULT = new BAbsTime(0, BTimeZone.getLocal());
  
  /**
   * The default BAbsTime.
   */
  public static final BAbsTime NULL = DEFAULT;
  
  /**
   * Name of facet used by {@code make(int, BMonth, int, int, int, int, int, BTimeZone, Context)} and
   * {@code makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context)}
   * to specify how
   * the time fields are specified.   Supported facet values are {@code TIME_MODE_DEFAULT},
   * <code>TIME_MODE_WALL_STRICT</code>, <code>TIME_MODE_STANDARD</code>, {@code TIME_MODE_DAYLIGHT}, {@code TIME_MODE_UTC}.
   *
   *  @see BAbsTime#makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context) getCalendar
   *  @see BAbsTime#make(int, BMonth, int, int, int, int, int, BTimeZone, Context) make
   *  @see BAbsTime#TIME_MODE_DEFAULT 
   *  @see BAbsTime#TIME_MODE_WALL_STRICT 
   *  @see BAbsTime#TIME_MODE_STANDARD 
   *  @see BAbsTime#TIME_MODE_DAYLIGHT 
   *  @see BAbsTime#TIME_MODE_UTC
   *   
   *  @since Niagara 3.5
   */
  public static final String TIME_MODE_FACET = "timeMode";
  
  /**
   * Value for {@code TIME_MODE_FACET} facet that indicates that {@code make(int, BMonth, int, int, int, int, int, BTimeZone, Context)} and
   * {@code makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context)} should
   * interpret time fields as wall time with the default handling for ambiguous or invalid daylight boundary
   * times.  Times that occur during the period of wall time that is skipped as daylight time starts will
   * be interpreted as standard time (therefore advancing the clock from the given hour).  The wall times that repeat as
   * daylight time ends (times occur first as daylight, then again as standard) are assumed to be standard time.
   *    
   * @see BAbsTime#makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context) getCalendar
   * @see BAbsTime#make(int, BMonth, int, int, int, int, int, BTimeZone, Context) make
   * @see BAbsTime#TIME_MODE_FACET
   *   
   * @since Niagara 3.5
   */
  public static final int TIME_MODE_DEFAULT = 0;
  
  /**
   * Value for {@code TIME_MODE_FACET} facet that indicates that {@code make(int, BMonth, int, int, int, int, int, BTimeZone, Context)} and
   * {@code makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context)} should
   * interpret time fields as wall time, and that an exception should be thrown if the time is ambiguous (the range of wall time that repeats
   * when daylight time ends), or invalid (the range of wall times that is skipped when daylight time starts)
   *    
   * @see BAbsTime#makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context) getCalendar
   * @see BAbsTime#make(int, BMonth, int, int, int, int, int, BTimeZone, Context) make
   * @see BAbsTime#TIME_MODE_FACET
   *   
   * @since Niagara 3.5
   */
  public static final int TIME_MODE_WALL_STRICT = 1;
  
  /**
   * Value for {@code TIME_MODE_FACET} facet that indicates that {@code make(int, BMonth, int, int, int, int, int, BTimeZone, Context)} and
   * {@code makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context)} should
   * interpret time fields as standard time.   Even if the time would occur during daylight time according to the time zone
   * rules, the time fields are interpreted as not using the daylight offset.
   *    
   * @see BAbsTime#makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context) getCalendar
   * @see BAbsTime#make(int, BMonth, int, int, int, int, int, BTimeZone, Context) make
   * @see BAbsTime#TIME_MODE_FACET
   *   
   * @since Niagara 3.5
   */
  public static final int TIME_MODE_STANDARD = 2;
  
  /**
   * Value for {@code TIME_MODE_FACET} facet that indicates that {@code make(int, BMonth, int, int, int, int, int, BTimeZone, Context)} and
   * {@code makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context)} should
   * interpret time fields as daylight time.   Even if the time would occur outside of daylight time according to the time zone
   * rules, the time fields are interpreted as using the daylight offset.
   *    
   * @see BAbsTime#makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context) getCalendar
   * @see BAbsTime#make(int, BMonth, int, int, int, int, int, BTimeZone, Context) make
   * @see BAbsTime#TIME_MODE_FACET
   *   
   * @since Niagara 3.5
   */
  public static final int TIME_MODE_DAYLIGHT = 3;
  
  /**
   * Value for {@code TIME_MODE_FACET} facet that indicates that {@code make(int, BMonth, int, int, int, int, int, BTimeZone, Context)} and
   * {@code getCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context)} should
   * interpret time fields as UTC time.
   *    
   * @see BAbsTime#makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context) getCalendar
   * @see BAbsTime#make(int, BMonth, int, int, int, int, int, BTimeZone, Context) make
   * @see BAbsTime#TIME_MODE_FACET
   *   
   * @since Niagara 3.5
   */
  public static final int TIME_MODE_UTC = 4;

  /**
   * Value for {@code TIME_MODE_FACET} facet that indicates that {@code make(int, BMonth, int, int, int, int, int, BTimeZone, Context)} and
   * {@code makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context)} should
   * interpret time fields as wall time with the special handling for ambiguous or invalid daylight boundary
   * times.  Times that occur during the period of wall time that is skipped as daylight time starts will
   * be advanced to the end of the end of the skipped period.  The wall times that repeat as
   * daylight time ends (times occur first as daylight, then again as standard) are assumed to be standard time.
   *    
   * @see BAbsTime#makeCalendar(int, BMonth, int, int, int, int, int, BTimeZone, Context) getCalendar
   * @see BAbsTime#make(int, BMonth, int, int, int, int, int, BTimeZone, Context) make
   * @see BAbsTime#TIME_MODE_FACET
   *   
   * @since Niagara 3.5
   */
  public static final int TIME_MODE_WALL_SCHD = 5;
  
  /**
   * The end of time.  This is a good value to use as "never".
   */
  public static final BAbsTime END_OF_TIME = BAbsTime.make(9999, BMonth.december, 31, 23, 59, 59, 999, BTimeZone.UTC);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbsTime.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private long millis;          // millis since epoch
  private BTimeZone timeZone;    // relative for fields
  private int bits0;            // year, millis
  private int bits1;            // mon, day, hour, min, sec, weekday, daylight
}
