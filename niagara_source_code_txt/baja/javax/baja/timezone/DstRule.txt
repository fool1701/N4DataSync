/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.timezone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SimpleTimeZone;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.baja.sys.BAbsTime;
import javax.baja.sys.BMonth;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BTime;
import javax.baja.sys.BWeekday;

import com.tridium.sys.schema.Fw;

/**
 * DstRule is a rule for determining the start or end time for daylight
 * savings time in a timezone.
 *
 * @author    John Sublett
 * @creation  09 Mar 2004
 * @version   $Revision: 27$ $Date: 5/26/10 10:07:01 AM EDT$
 * @since     Baja 1.0
 */
public final class DstRule
{

  private DstRule()
  {
  }

////////////////////////////////////////////////////////////////
//Make from historical
////////////////////////////////////////////////////////////////

  /**
   * Framework support only, this method should not be used by developers.
   */
  public static Object fw(int cmd, Object a, Object b, Object c, Object d)
  {
    if (cmd == Fw.CREATE_RULE_FROM_JAVA_TIME_ZONE && a != null && b != null)
    {
      return makeFromJavaTimeZone((TimeZone)a, ((Boolean)b).booleanValue());
    }

    return null;
  }

////////////////////////////////////////////////////////////////
// Extract active DST rule from JavaTimeZone
///////////////////////////////////////////////////////////////

  private final static DstRule makeFromJavaTimeZone(TimeZone javaTimeZone, boolean start)
  {
    ZoneRules javaRules = null;
    ZoneOffsetTransition targetTransition = null;
    ZoneOffsetTransitionRule targetTransitionRule = null;
    LocalDateTime targetTransitionOccurs = null;

    try
    {
      //Obtain java.time.zone.ZoneRules object for the provided Java TimeZone object
      javaRules = ZoneRulesProvider.getRules(javaTimeZone.getID(), true);

      if (javaRules == null)
      {
        //This zone will never use DST, even historically
        return null;
      }
    }
    catch (ZoneRulesException zre)
    {
      //TZDB does not have a definition for this item, it could be a built in zone
      return null;
    }

    //Determine what the local year of the requested zone
    LocalDateTime nowInTarget = LocalDateTime.now(ZoneId.of(javaTimeZone.getID()));

    //Determine if any "exact" transitions occur this year, caching the result.
    //NOTE: Possible concurrent access concerns here should ok since the iteration
    //      below does not modify the content of the list (and its UnmodifiableList anyway)
    List<ZoneOffsetTransition> fixedTransitions = LRU_RULE_TRANSITIONS_CACHE.get(javaTimeZone.getID());
    if (fixedTransitions == null)
    {
      fixedTransitions = javaRules.getTransitions();
      LRU_RULE_TRANSITIONS_CACHE.put(javaTimeZone.getID(), fixedTransitions);
    }

    for (ZoneOffsetTransition currentTransition : fixedTransitions)
    {
      //Obtain the instant in which this transition occurs
      LocalDateTime beforeTransition = currentTransition.getDateTimeBefore();
      int transitionYear = beforeTransition.getYear();

      //Determine if there are any really strange transitions

      if (transitionYear == nowInTarget.getYear())
      {
        //This transition affects us

        if (start)
        {
          //This transition is the exact target iff it is a gap (start) transition
          if (currentTransition.isGap())
          {
            //Capture reference
            targetTransition = currentTransition;

            //All done
            break;
          }
          //else keep looking
        }
        //else making end rule
        else
        {
          //This transition is the exact target iff it is a overlap (end) transition
          if (currentTransition.isOverlap())
          {
            //Capture reference
            targetTransition = currentTransition;

            //All done
            break;
          }
          //else keep looking
        }
      }
      else if (transitionYear > nowInTarget.getYear())
      {
        //I've encountered a future "exact" transition date.
        //This means that in the current LocalDateTime, I will not
        //being using DST, and there will be no "future" rule that
        //applies to the current year (since we encountered a future
        //"exact" transition. I can safely say I don't respect DST right
        //now.
        return null;
      }
      //Else, the year is less that what I am looking for and I should continue
      //incrementing the iterator
    }

    if (targetTransition == null)
    {
      //I did not find an exact transition, lets see if the there
      //are any "future" rules that I can use to create a "non exact"
      //DST rule

      if (javaRules.getTransitionRules() == null ||
          javaRules.getTransitionRules().size() == 0)
      {
        //There are no "future" rules, the zone may have used
        //DST at some historical point, but not now or in the
        //future, return no rule
        return null;
      }

      //There are future rules, get a reference to the target definition
      if (start)
      {
        //I want the rule that defines a "gap"

        //Northern hemispheres will place the "gap" rule in the first
        //position, Southern hemispheres will place the "gap" rule in
        //the second position

        ZoneOffsetTransitionRule candidate = javaRules.getTransitionRules().get(0);
        if (candidate.getStandardOffset().equals(candidate.getOffsetBefore()))
        {
          //We are going from "Standard" time to "Daylight" time, this is a "gap" rule
          targetTransitionRule = candidate;
        }
        else
        {
          //We are going from "Daylight" time to "Standard" time, this is an "overlap rule, use the other one
          targetTransitionRule = javaRules.getTransitionRules().get(1);
        }
      }
      else
      {
        //I want the rule that defines an "overlap"

        //Northern hemispheres will place the "overlap" rule in the second
        //position, Southern hemispheres will place the "overlap" rule in
        //the first position

        ZoneOffsetTransitionRule candidate = javaRules.getTransitionRules().get(1);
        if (candidate.getStandardOffset().equals(candidate.getOffsetAfter()))
        {
          //We are going from "Daylight" time to "Standard" time, this is an "overlap" rule
          targetTransitionRule = candidate;
        }
        else
        {
          //We are going from "Standard" time to "Daylight" time, this is an "gap" rule, use the other one
          targetTransitionRule = javaRules.getTransitionRules().get(0);
        }
      }
    }

    //I don't think it's possible to execute this, but as a precaution, exit
    //with no rules if you did not find either a rule to interpret, or an
    //exact day to return
    if (targetTransition == null &&
        targetTransitionRule == null)
    {
      //No DST
      return null;
    }
    else if (targetTransition != null)
    {
      targetTransitionOccurs = targetTransition.getDateTimeBefore();
    }

    if (targetTransition != null)
    {
      //Make an exact rule based on a transition we found that affects us.
      //This is typical of zones that plan exact dates for DST years ahead
      //of time, but those occurances are different for each year, not
      //a "rule".
      //
      //Zones like:
      //
      //  "Africa/Casablanca", "Africa/Tripoli", "America/Asuncion", "America/Campo_Grande"
      //  "America/Cuiaba", "America/Havana", "America/Sao_Paulo", "Asia/Gaza"
      //  "Asia/Hebron", "Asia/Jerusalem", "Asia/Tehran", "Asia/Tel_Aviv"
      //  "Brazil/East", "Cuba", "Iran", "Israel"
      //  "Libya", "Africa/Casablanca", "Africa/Tripoli", "America/Asuncion"
      //  "America/Campo_Grande", "America/Cuiaba", "America/Havana", "America/Sao_Paulo"
      //  "Asia/Gaza", "Asia/Hebron", "Asia/Jerusalem", "Asia/Tehran"
      //  "Asia/Tel_Aviv", "Brazil/East", "Cuba", "Iran", "Israel", "Libya", "Africa/Casablanca"
      //
      //do this.
      //
      //Will these need to be refreshed every year?

      Month month = targetTransitionOccurs.getMonth();
      int day = targetTransitionOccurs.getDayOfMonth();
      BTime time = BTime.make(targetTransitionOccurs.getHour(),
                              targetTransitionOccurs.getMinute(),
                              targetTransitionOccurs.getSecond());

      //System.out.println("Made exact rule for zone \"" + javaTimeZone.getID() + "\": " + convertJavaMonthToNiagaraMonth(month) + " " + day + " " + nowInTarget.getYear() + " " + time);

      try
      {
        return DstRule.makeExact(time,                                  //Millis after midnight
                                 DstRule.WALL_TIME,                     //All transitions are local time
                                 convertJavaMonthToNiagaraMonth(month), //Month
                                 day);                                  //Day of month
      }
      catch (Exception e)
      {
        e.printStackTrace();
        return null;
      }
    }
    else if (targetTransitionRule != null)
    {
      try
      {
        //Exact date rule
        Month month = targetTransitionRule.getMonth();
        int day = targetTransitionRule.getDayOfMonthIndicator();
        DayOfWeek dayOfWeek = targetTransitionRule.getDayOfWeek();
        LocalTime localTime = targetTransitionRule.getLocalTime();
        TimeDefinition timeMode = targetTransitionRule.getTimeDefinition();
        boolean isMidnightEndOfDay = targetTransitionRule.isMidnightEndOfDay();
        BTime time = BTime.make(localTime.getHour(),
                                localTime.getMinute(),
                                localTime.getSecond());

        if (dayOfWeek == null)
        {
          //EXACT
          return DstRule.makeExact(time,                                           //Millis after midnight
                                   convertJavaTimeModeToNiagaraTimeMode(timeMode), //Convert time mode
                                   convertJavaMonthToNiagaraMonth(month),          //Month
                                   day);                                           //Day of month
        }
        else
        {
//          //Nth day, on or after or on or before, or on or after rule
//          if (day != 1 ||
//              day != 8 ||
//              day != 15 ||
//              day != 22 ||
//              day != 29 ||
//              day < 0 ||
//              isMidnightEndOfDay)
//          {
//            System.out.println("Looking at rule for zone \"" + javaTimeZone.getID() + "\": ");
//            System.out.println("  Month: " + month);
//            System.out.println("  Day: " + day);
//            System.out.println("  DayOfWeek: " + dayOfWeek);
//            System.out.println("  Local Time is: " + time);
//            System.out.println("  Time Mode is: " + timeMode);
//            System.out.println("  Is Midnight: " + isMidnightEndOfDay);
//          }

          if (day > 0)
          {
            //If the value is positive, then it represents a normal day-of-month,
            //and is the earliest possible date that the transition can be. The date
            //may refer to 29th February which should be treated as 1st March in non-leap years.

            return DstRule.makeOnOrAfter(time,
                                         convertJavaTimeModeToNiagaraTimeMode(timeMode),
                                         convertJavaMonthToNiagaraMonth(month),
                                         day,
                                         convertJavaWeekdayToNiagaraWeekday(dayOfWeek));

          }
          else if (day < 0)
          {
            //If the value is negative, then it represents the number of days back from the
            //end of the month where -1 is the last day of the month. In this case, the day
            //identified is the latest possible date that the transition can be.
            int daysInMonth = BAbsTime.getDaysInMonth(nowInTarget.getYear(), convertJavaMonthToNiagaraMonth(month));

            //Add negative value to determine on or before (adding 1 to day since -1 indicates last day)
            day = daysInMonth + (day + 1);

            return DstRule.makeOnOrAfter(time,
                                         convertJavaTimeModeToNiagaraTimeMode(timeMode),
                                         convertJavaMonthToNiagaraMonth(month),
                                         day,
                                         convertJavaWeekdayToNiagaraWeekday(dayOfWeek));
          }
          else
          {
            //If the rule defines a week where the transition might occur, then the day defines
            //either the start of the end of the transition week.
            //
            //NOTE: Examining the source of ZoneOffsetTransitionRule, there does not appear to
            //be any Nth weekday transitions. They are all automatically converted to "on or after"
            //definitions.
          }
        }
      }
      catch (Exception e)
      {
        //Error occurred while attempting to create this zone
        e.printStackTrace();
        return null;
      }

      //Should not get here
      return null;
    }

    //Should not get here
    return null;
  }

  private static BWeekday convertJavaWeekdayToNiagaraWeekday(DayOfWeek dayOfWeek)
    throws Exception
  {
    switch (dayOfWeek)
    {
      case SUNDAY:
        return BWeekday.sunday;
      case MONDAY:
        return BWeekday.monday;
      case TUESDAY:
        return BWeekday.tuesday;
      case WEDNESDAY:
        return BWeekday.wednesday;
      case THURSDAY:
        return BWeekday.thursday;
      case FRIDAY:
        return BWeekday.friday;
      case SATURDAY:
        return BWeekday.saturday;
    }

    throw new Exception("Could not convert day of week");
  }

  private static int convertJavaTimeModeToNiagaraTimeMode(int mode)
    throws Exception
  {
    switch (mode)
    {
      case SimpleTimeZone.STANDARD_TIME:
        return DstRule.STANDARD_TIME;
      case SimpleTimeZone.UTC_TIME:
        return DstRule.UTC_TIME;
      case SimpleTimeZone.WALL_TIME:
        return DstRule.WALL_TIME;
    }

    throw new Exception("Could not convert mode");
  }

  private static int convertJavaTimeModeToNiagaraTimeMode(TimeDefinition mode)
    throws Exception
  {
    switch (mode)
    {
      case STANDARD:
        return DstRule.STANDARD_TIME;
      case UTC:
        return DstRule.UTC_TIME;
      case WALL:
        return DstRule.WALL_TIME;
    }

    throw new Exception("Could not convert mode");
  }

  private static BMonth convertJavaMonthToNiagaraMonth(Month month)
    throws Exception
  {
    switch(month)
    {
      case JANUARY: return BMonth.january;
      case FEBRUARY: return BMonth.february;
      case MARCH: return BMonth.march;
      case APRIL: return BMonth.april;
      case MAY: return BMonth.may;
      case JUNE: return BMonth.june;
      case JULY: return BMonth.july;
      case AUGUST: return BMonth.august;
      case SEPTEMBER: return BMonth.september;
      case OCTOBER: return BMonth.october;
      case NOVEMBER: return BMonth.november;
      case DECEMBER: return BMonth.december;
    }

    throw new Exception("Could not convert month");
  }

////////////////////////////////////////////////////////////////
// Exact day
///////////////////////////////////////////////////////////////

  /**
   * Create a rule for an exact day of the month.
   *
   * @param timeOfDay The time of day of the boundary.  This is
   *   expressed in wall time.
   * @param month The month of the boundary.
   * @param day The day of the boundary.
   */
  public static DstRule makeExact(BTime timeOfDay, BMonth month, int day)
  {
    return makeExact(timeOfDay, WALL_TIME, month, day);
  }

  /**
   * Create a rule for an exact day of the month.
   *
   * @param timeOfDay The time of day of the boundary.
   * @param timeMode Indicates how the time is specified
   *   (WALL_TIME, STANDARD_TIME, or UTC_TIME).
   * @param month The month of the boundary.
   * @param day The day of the boundary.
   */
  public static DstRule makeExact(BTime timeOfDay, int timeMode, BMonth month, int day)
  {
    DstRule rule = new DstRule();

    rule.timeOfDay = timeOfDay;
    rule.timeMode = timeMode;
    rule.month = month;
    rule.day = day;
    rule.dayMode = EXACT;
    rule.weekday = null;
    rule.week = UNDEFINED;

    return rule;
  }

///////////////////////////////////////////////////////////////
// Weekday
////////////////////////////////////////////////////////////////

  /**
   * Create a rule for a specific weekday within a week in the month.
   *
   * @param timeOfDay The time of day of the boundary.  This is
   *   expressed in wall time.
   * @param week The week within the month
   *  (FIRST, SECOND, THIRD, FOURTH, FIFTH, or LAST).
   * @param weekday The weekday of the boundary.
   * @param month The month of the boundary.
   */
  public static DstRule makeWeekday(BTime    timeOfDay,
                                    int      week,
                                    BWeekday weekday,
                                    BMonth   month)
  {
    return makeWeekday(timeOfDay, WALL_TIME, week, weekday, month);
  }

  /**
   * Create a rule for a specific weekday within a week in the month.
   *
   * @param timeOfDay The time of day of the boundary.
   * @param timeMode Indicates how the time is specified
   *   (WALL_TIME, STANDARD_TIME, or UTC_TIME).
   * @param week The week within the month
   *  (FIRST, SECOND, THIRD, FOURTH, FIFTH, or LAST).
   * @param weekday The weekday of the boundary.
   * @param month The month of the boundary.
   */
  public static DstRule makeWeekday(BTime    timeOfDay,
                                    int      timeMode,
                                    int      week,
                                    BWeekday weekday,
                                    BMonth   month)
  {
    if ((timeMode != WALL_TIME) &&
        (timeMode != STANDARD_TIME) &&
        (timeMode != UTC_TIME))
      throw new IllegalArgumentException("Invalid time mode: " + timeMode);

    DstRule rule = new DstRule();

    rule.timeOfDay = timeOfDay;
    rule.timeMode = timeMode;
    rule.month = month;
    rule.weekday = weekday;
    rule.week = week;

    rule.day = 0;
    rule.dayMode = WEEKDAY;

    return rule;
  }

  /**
   * Create a rule for a specific weekday on or after the specified day
   * of the month.
   */
  public static DstRule makeOnOrAfter(BTime    timeOfDay,
                                      BMonth   month,
                                      int      day,
                                      BWeekday weekday)
  {
    return makeOnOrAfter(timeOfDay, WALL_TIME, month, day, weekday);
  }

  /**
   * Create a rule for a specific weekday on or before the specified day
   * of the month.
   */
  public static DstRule makeOnOrAfter(BTime    timeOfDay,
                                      int      timeMode,
                                      BMonth   month,
                                      int      day,
                                      BWeekday weekday)
  {
    if ((timeMode != WALL_TIME) &&
        (timeMode != STANDARD_TIME) &&
        (timeMode != UTC_TIME))
      throw new IllegalArgumentException("Invalid time mode: " + timeMode);

    DstRule rule = new DstRule();

    rule.timeOfDay = timeOfDay;
    rule.timeMode = timeMode;
    rule.month = month;
    rule.day = day;
    rule.dayMode = ON_OR_AFTER;
    rule.weekday = weekday;
    rule.week = UNDEFINED;

    return rule;
  }

  public static DstRule makeOnOrBefore(BTime    timeOfDay,
                                       BMonth   month,
                                       int      day,
                                       BWeekday weekday)
  {
    return makeOnOrBefore(timeOfDay, WALL_TIME, month, day, weekday);
  }

  public static DstRule makeOnOrBefore(BTime    timeOfDay,
                                       int      timeMode,
                                       BMonth   month,
                                       int      day,
                                       BWeekday weekday)
  {
    if ((timeMode != WALL_TIME) &&
        (timeMode != STANDARD_TIME) &&
        (timeMode != UTC_TIME))
      throw new IllegalArgumentException("Invalid time mode: " + timeMode);

    DstRule rule = new DstRule();

    rule.timeOfDay = timeOfDay;
    rule.timeMode = timeMode;
    rule.month = month;
    rule.day = day;
    rule.dayMode = ON_OR_BEFORE;
    rule.weekday = weekday;
    rule.week = UNDEFINED;

    return rule;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public BTime getTime()
  {
    return timeOfDay;
  }

  public int getTimeMode()
  {
    return timeMode;
  }

  public int getWeek()
  {
    return week;
  }

  public BWeekday getWeekday()
  {
    return weekday;
  }

  public BMonth getMonth()
  {
    return month;
  }

  public int getDay()
  {
    return day;
  }

  public int getDayMode()
  {
    return dayMode;
  }

////////////////////////////////////////////////////////////////
// Evaluation
////////////////////////////////////////////////////////////////

  /**
   * Retrieve the day (Day of Month 1, 2, 3) on which the provided
   * daylight saving rule "day mode" expression would evaluate to in the
   * UTC time zone. Not guaranteed to be the same day as if this
   * rule was evaluated in the local time zone, or if the "time mode"
   * expression associated with this rule caused the day of the month
   * to change when evaluated.
   *
   * For more accurate results, use the getTime() function
   *
   * @param year The target year.
   * @param rule The rule to evaluate.
   * @return Returns The day of the month for this rule in the specified year.
   * @since Niagara 4.0
   */
  public static int getUtcDayOfMonth(int year, DstRule rule)
  {
    if (rule.getDayMode() == DstRule.WEEKDAY)
    {
      Calendar dateCal = new GregorianCalendar(BTimeZone.getJavaUTCInstance());
      dateCal.set(Calendar.YEAR, year);
      dateCal.set(Calendar.MONTH, rule.getMonth().getOrdinal());
      if (rule.getWeek() == DstRule.LAST || rule.getWeek() == DstRule.FIFTH)
      {
        //roll backwards from the last day in the month..

        dateCal.set(Calendar.DATE, BAbsTime.getDaysInMonth(year, rule.getMonth()));
        dateCal.setLenient(false);

        while (dateCal.get(Calendar.DAY_OF_WEEK) != rule.getWeekday().getOrdinal() + 1)
        {
          dateCal.add(Calendar.DATE, -1);
        }
      }
      else
      {
        //roll forward from the first, second, third...

        int d;
        if (rule.getWeek() == DstRule.FIRST) d = 1;
        else if (rule.getWeek() == DstRule.SECOND) d = 8;
        else if (rule.getWeek() == DstRule.THIRD) d = 15;
        else if (rule.getWeek() == DstRule.FOURTH) d = 22;
        else
          throw new IllegalArgumentException("Invalid week: " + rule.getWeek());

        dateCal.set(Calendar.DATE, d);
        dateCal.setLenient(false);

        while (dateCal.get(Calendar.DAY_OF_WEEK) != rule.getWeekday().getOrdinal() + 1)
        {
          dateCal.add(Calendar.DATE, 1);
        }
      }
      return dateCal.get(Calendar.DATE);
    }
    else if (rule.getDayMode() == DstRule.EXACT)
    {
      return rule.getDay();
    }
    else if ((rule.getDayMode() == DstRule.ON_OR_AFTER) ||
              (rule.getDayMode() == DstRule.ON_OR_BEFORE))
    {
      Calendar dateCal = new GregorianCalendar(BTimeZone.getJavaUTCInstance());
      dateCal.set(year, rule.getMonth().getOrdinal(), rule.getDay());
      dateCal.setLenient(false);
      while (dateCal.get(Calendar.DAY_OF_WEEK) != rule.getWeekday().getOrdinal() + 1)
      {
        if (rule.getDayMode() == DstRule.ON_OR_BEFORE)
          dateCal.add(Calendar.DATE, -1);
        else
          dateCal.add(Calendar.DATE, 1);
      }
      return dateCal.get(Calendar.DATE);
    }
    else
    {
      throw new IllegalArgumentException("");
    }
  }

  /**
   * Get the time specified by this rule in the specified year.
   * If this rule doesn't specify a time, BAbsTime.NULL is returned.
   *
   * @param year The target year.
   * @param timeZone The time zone of the result.
   * @return Returns the time for this rule in the specified year.
   */
  public final synchronized BAbsTime getTime(int year, BTimeZone timeZone, int boundary)
  {
    if ((boundary != START) && (boundary != END))
      throw new IllegalArgumentException("Invalid boundary: " + boundary);

    if ((cacheYear == year) && timeZone.equals(cacheTimeZone) && (cacheBoundary == boundary))
      return cachedTime;

    cacheYear = year;
    cacheTimeZone = timeZone;
    cacheBoundary = boundary;
    cachedTime = calculateTime(year, timeZone, boundary);

    return cachedTime;
  }

  /**
   * Calculate the time specified by this rule in the specified year.
   * If this rule doesn't specify a time, BAbsTime.NULL is returned.
   *
   * @param year The target year.
   * @param timeZone The time zone of the result.
   * @return Returns the time for this rule in the specified year.
   */
  private BAbsTime calculateTime(final int year, final BTimeZone timeZone, int boundary)
  {
    Calendar newCal = new GregorianCalendar(BTimeZone.getJavaUTCInstance());
    newCal.clear();
    newCal.set(Calendar.YEAR, year);
    newCal.set(Calendar.MONTH, month.getOrdinal());

    //
    // Weekday within a specific week of the month
    //
    if (dayMode == WEEKDAY)
    {
      if (week == LAST || week == FIFTH)
      {
        //roll backwards from the last day in the month..

        newCal.set(Calendar.DATE, BAbsTime.getDaysInMonth(year, month));
        newCal.setLenient(false);

        while (newCal.get(Calendar.DAY_OF_WEEK) != weekday.getOrdinal() + 1)
        {
          newCal.add(Calendar.DATE, -1);
        }
      }
      else
      {
        //roll forward from the first, second, third...

        int d;
        if (week == FIRST) d = 1;
        else if (week == SECOND) d = 8;
        else if (week == THIRD) d = 15;
        else if (week == FOURTH) d = 22;
        else
          throw new IllegalArgumentException("Invalid week: " + week);

        newCal.set(Calendar.DATE, d);
        newCal.setLenient(false);

        while (newCal.get(Calendar.DAY_OF_WEEK) != weekday.getOrdinal() + 1)
        {
          newCal.add(Calendar.DATE, 1);
        }
      }
    }
    //
    // Exact month and day.
    //
    else if (dayMode == EXACT)
    {
      newCal.set(Calendar.DATE, day);
    }
    //
    // on or before a specific day
    // on or after a specific day
    //
    else if ((dayMode == ON_OR_AFTER) || (dayMode == ON_OR_BEFORE))
    {
      newCal.set(Calendar.DATE, day);
      newCal.setLenient(false);

      //until we get the day we are looking for, keep incrementing the day...
      while (newCal.get(Calendar.DAY_OF_WEEK) != (weekday.getOrdinal() + 1))
      {
        if (dayMode == ON_OR_AFTER)
          newCal.add(Calendar.DATE, 1);
        else
          newCal.add(Calendar.DATE, -1);
      }
    }
    else
      throw new IllegalStateException("Cannot compute dst rule time.");

    newCal.set(Calendar.HOUR_OF_DAY, timeOfDay.getHour());
    newCal.set(Calendar.MINUTE, timeOfDay.getMinute());
    newCal.set(Calendar.SECOND, timeOfDay.getSecond());
    newCal.set(Calendar.MILLISECOND, timeOfDay.getSecond());

    // Adjust for the timeMode.
    if (timeMode == UTC_TIME)
      return BAbsTime.make(newCal.getTimeInMillis(), timeZone);
    else if (timeMode == STANDARD_TIME)
      return BAbsTime.make(newCal.getTimeInMillis() - timeZone.getUtcOffset(), timeZone);
    else if (timeMode == WALL_TIME)
    {
      long millis = newCal.getTimeInMillis() - timeZone.getUtcOffset();
      if (boundary == END)
        millis -= timeZone.getDaylightAdjustment();

      return BAbsTime.make(millis, timeZone);
    }
    else
      throw new IllegalArgumentException("Invalid time mode: " + timeMode);
  }

  /**
   * Convert the given rule to a wall-time based rule, or return null
   * if conversion is impossible.
   */
  public static DstRule getWallTimeRule(DstRule rule, int boundary, BTimeZone tz)
  {
    return (rule == null) ? null : rule.asWallTimeRule(boundary, tz);
  }

  private TimeZone getJavaTimeZone(long utcOffsetMillis)
  {
    if (utcOffsetMillis == 0)
      return BTimeZone.getJavaUTCInstance();

    StringBuilder id = new StringBuilder("GMT");

    if (utcOffsetMillis > 0) id.append('+');

    // if UTC offset is fractional the ID will be goofy and unknown
    // by TimeZone, but that's ok - we'll just construct a SimpleTimeZone
    id.append(String.valueOf(utcOffsetMillis / BRelTime.MILLIS_IN_HOUR));
    id.append(":00");

    TimeZone result = TimeZone.getTimeZone(id.toString());

    if (result == null)
      return new SimpleTimeZone((int)utcOffsetMillis, id.toString());

    return result;
  }

  /**
   * Convert this rule to a wall-time based rule, or return null
   * if conversion is impossible.
   */
  public DstRule asWallTimeRule(int boundary, BTimeZone tz)
  {
    //if its already stored as wall time, return it
    if (getTimeMode() == WALL_TIME)
    {
      return this;
    }
    //of if its standard time for a start rule, its already wall time
    else if ((getTimeMode() == STANDARD_TIME) &&
             (boundary == START))
    {
      // standard is the same as wall for the start rule
      DstRule result = new DstRule();
      result.timeOfDay = timeOfDay;
      result.timeMode  = WALL_TIME;
      result.month     = month;
      result.day       = day;
      result.dayMode   = dayMode;
      result.weekday   = weekday;
      result.week      = week;
      return result;
    }

    //however, it is standard time for an end rule, or utc time, then we need to do some work.
    Calendar cal;

    if (getTimeMode() == UTC_TIME)
    {
      cal = new GregorianCalendar(BTimeZone.getJavaUTCInstance());
    }
    else
    {
      cal = new GregorianCalendar(getJavaTimeZone(tz.getUtcOffset()));
    }
    int year = cal.get(Calendar.YEAR);
    cal.clear();

    //WARNING, the following will malfunction during leap years
    //we need to know what year to set the calendar to, to
    //accommodate leap years when rolling.
    //
    //HOWEVER, the bug will only occur for the following conditions:
    //
    //1) The current year is not a leap year, the rule provided is in effect for a leap year.
    //   The rule occurs on March 1st or February 28th.
    //   The rule will roll backward, or forward, respectively, when converted to wall time.
    //
    //2) The current year is a leap year, the rule provided is not in effect for a leap year.
    //   The rule occurs on March 1st or February 28th.
    //   The rule will roll backward, or forward, respectively, when converted to wall time.

    cal.set(Calendar.YEAR, year);  // <---------BAD BAD BAD!
    cal.set(Calendar.MONTH, getMonth().getOrdinal());
    cal.setLenient(false);

    if (getDayMode() == WEEKDAY)
    {
      switch(getWeek())
      {
        case FIRST:
          cal.set(Calendar.DATE, 1);
          while (cal.get(Calendar.DAY_OF_WEEK) != (getWeekday().getOrdinal() + 1))
          {
            cal.add(Calendar.DATE, 1);
          }
          break;
        case SECOND:
          cal.set(Calendar.DATE, 8);
          while (cal.get(Calendar.DAY_OF_WEEK) != (getWeekday().getOrdinal() + 1))
          {
            cal.add(Calendar.DATE, 1);
          }
          break;
        case THIRD:
          cal.set(Calendar.DATE, 15);
          while (cal.get(Calendar.DAY_OF_WEEK) != (getWeekday().getOrdinal() + 1))
          {
            cal.add(Calendar.DATE, 1);
          }
          break;
        case FOURTH:
          cal.set(Calendar.DATE, 22);
          while (cal.get(Calendar.DAY_OF_WEEK) != (getWeekday().getOrdinal() + 1))
          {
            cal.add(Calendar.DATE, 1);
          }
          break;
        case FIFTH:
        case LAST:
          cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

          //until we get the day we are looking for, keep decrementing the day...
          while (cal.get(Calendar.DAY_OF_WEEK) != (getWeekday().getOrdinal() + 1))
          {
            cal.add(Calendar.DATE, -1);
          }
          break;
      }
    }
    else
    {
      switch(getDayMode())
      {
        case DstRule.EXACT:
          cal.set(Calendar.DATE, getDay());
          break;
        case DstRule.ON_OR_AFTER:
          cal.set(Calendar.DATE, getDay());
          //until we get the day we are looking for, keep incrementing the day...
          while (cal.get(Calendar.DAY_OF_WEEK) != (getWeekday().getOrdinal() + 1))
          {
            cal.add(Calendar.DATE, 1);
          }
          break;
        case DstRule.ON_OR_BEFORE:
          cal.set(Calendar.DATE, getDay());
          //until we get the day we are looking for, keep decrementing the day...
          while (cal.get(Calendar.DAY_OF_WEEK) != (getWeekday().getOrdinal() + 1))
          {
            cal.add(Calendar.DATE, -1);
          }
          break;
      }
    }

    cal.set(Calendar.HOUR_OF_DAY, getTime().getHour());
    cal.set(Calendar.MINUTE, getTime().getMinute());
    cal.set(Calendar.SECOND, getTime().getSecond());
    cal.set(Calendar.MILLISECOND, getTime().getMillisecond());

    //  make the old info because we know have an exact date for this rule
    int oldDay = cal.get(Calendar.DAY_OF_MONTH);
    int oldWeek = 0;

    if (1 <= oldDay && oldDay < 8) oldWeek = FIRST;
    else if (8 <= oldDay && oldDay < 15) oldWeek = SECOND;
    else if (15 <= oldDay && oldDay < 22) oldWeek = THIRD;
    else if (22 <= oldDay && oldDay < 29)
    {
      //This is where it gets tricky. A day between this date could very well
      //occur on the last AND fourth week, but sometimes not. Let's seed the value with what we know
      //already if it has a week defined, because we NEED to pick on of the
      //two, but who do we favor?

      if (getWeek() != UNDEFINED)
      {
        //prefer how the rule was originally expressed
        if (getWeek() == LAST) oldWeek = LAST;
        else if (getWeek() == FOURTH) oldWeek = FOURTH;
      }
      else
      {
        //weekday was not defined so we're going to have guess, but we
        //want to give favor to last because it is sooooo much more common in this case
        //that they meant last....

        if (cal.getActualMaximum(Calendar.DAY_OF_MONTH) <= 28) oldWeek = LAST;
        else if ((oldDay + 7) > cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        {
          //favor the last
          oldWeek = LAST;
        }
        //only choose 4th if it is the only option
        else oldWeek = FOURTH;
      }
    }
    //after this date HAS to be the last....
    else if (29 <= oldDay && oldDay < 32) oldWeek = LAST;

    BMonth oldMonth = BMonth.make(cal.get(Calendar.MONTH));

    //figure out what the wall millis are so we can reset the calendar
    long wallMillis = 0;

    //remember, we can already assume that this is an END boundary...
    if (getTimeMode() == STANDARD_TIME)
    {
      wallMillis = cal.getTime().getTime() + tz.getDaylightAdjustment();
    }
    //its utc time....
    else
    {
      wallMillis = cal.getTime().getTime() + tz.getUtcOffset();
      if (boundary == END)
      {
        wallMillis += tz.getDaylightAdjustment();
      }
    }

    //set the calendar to the new date...
    cal.setTimeInMillis(wallMillis);

    //make the new info...
    int newDay = cal.get(Calendar.DAY_OF_MONTH);
    BWeekday newWd = BWeekday.make(cal.get(Calendar.DAY_OF_WEEK) - 1);
    int newWeek = 0;

    if (1 <= newDay && newDay < 8) newWeek = FIRST;
    else if (8 <= newDay && newDay < 15) newWeek = SECOND;
    else if (15 <= newDay && newDay < 22) newWeek = THIRD;
    else if (22 <= newDay && newDay < 29)
    {
      //we're going to have guess, but we want to give favor to last
      //because it is sooooo much more common in this case
      //that they meant last....

      if (cal.getActualMaximum(Calendar.DAY_OF_MONTH) <= 28) newWeek = LAST;
      else if ((newDay + 7) > cal.getActualMaximum(Calendar.DAY_OF_MONTH))
      {
        //favor the last
        newWeek = LAST;
      }
      //only choose 4th if it is the only option
      else newWeek = FOURTH;
    }
    else if (29 <= newDay && newDay < 32) newWeek = LAST;

    BMonth newMonth = BMonth.make(cal.get(Calendar.MONTH));

    //if our new day does not equal the one we now have, do some sanity checks...
    if (oldDay != newDay)
    {
      if (!oldMonth.equals(newMonth))
      {
        //ok, we actually rolled over to a new month, we will not support this rule!
        return null;
      }
      //we shouldn't have any problems rolling an exact day...
      if (getDayMode() == EXACT)
      {

      }
      //if the rule is on or after, can't roll a day that isn't already specific
      else if (getDayMode() == ON_OR_AFTER)
      {
        return null;
      }
      //if the rule is on or before can't roll a day that isn't already specific
      else if (getDayMode() == ON_OR_BEFORE)
      {
        return null;
      }
      else
      {
        //if the rule is weekday..
        if (newWeek != oldWeek)
        {
          //we can't support moving a week
          return null;
        }
        else
        {
          //the week is the same, but the problem is we can't know for sure
          //that this rule will be interpretted the same for different years.
          //
          //For example, if we take the change last Sunday 1:00 AM UTC to wall time in a zone with a -3 offset,
          //then that rule will roll over to the previous day on wall time conversion to become
          //the last Saturday 10:00 PM. This will be an illegal operation in 2007, because the
          //weeks would have changed, but if the conversion is done in the year 2008, then it is perfectly
          //legit. If we then apply this rule to 2007, then it appears that dst starts on march 31st instead
          //of march 24th.

          //therefore, we can't support a day that rolls when expressed in weekday moves
          return null;
        }
      }
    }

    if (getDayMode() == WEEKDAY)
    {
      return makeWeekday(BTime.make(cal.get(Calendar.HOUR_OF_DAY),
                                    cal.get(Calendar.MINUTE),
                                    cal.get(Calendar.SECOND),
                                    cal.get(Calendar.MILLISECOND)),
                                    WALL_TIME,
                                    newWeek,
                                    newWd,
                                    newMonth);
    }
    else if (getDayMode() == EXACT)
    {
      return makeExact(BTime.make(cal.get(Calendar.HOUR_OF_DAY),
                                  cal.get(Calendar.MINUTE),
                                  cal.get(Calendar.SECOND),
                                  cal.get(Calendar.MILLISECOND)),
                                  WALL_TIME,
                                  newMonth,
                                  newDay);
    }
    else if (getDayMode() == ON_OR_BEFORE)
    {
      //if the day was on_or_before, remake the original rule with an updated time
      //because we can already assume that the rule did not roll
      return makeOnOrBefore(BTime.make(cal.get(Calendar.HOUR_OF_DAY),
                                       cal.get(Calendar.MINUTE),
                                       cal.get(Calendar.SECOND),
                                       cal.get(Calendar.MILLISECOND)),
                                       WALL_TIME,
                                       month,
                                       day,
                                       weekday);
    }
    else // ON_OR_AFTER
    {
      //if the day on_or_after, remake the original rule with an updated time
      //because we can already assume that the rule did not really change
      return makeOnOrAfter(BTime.make(cal.get(Calendar.HOUR_OF_DAY),
                                      cal.get(Calendar.MINUTE),
                                      cal.get(Calendar.SECOND),
                                      cal.get(Calendar.MILLISECOND)),
                                      WALL_TIME,
                                      month,
                                      day,
                                      weekday);
    }
  }

  /**
   * If the given ON_OR_AFTER, ON_OR_BEFORE rule can be cleanly expressed as
   * WEEKDAY, then convert it for broader platform support
   */
  public static DstRule asWeekdayRule(DstRule rule) throws TimeZoneException
  {
    if (rule == null) return rule;
    else if (rule.getDayMode() == DstRule.WEEKDAY) return rule;
    else if (rule.getDayMode() == DstRule.EXACT) throw new TimeZoneException("Can't convert rule from exact to nth weekday");
    //else the rule must be on_or_after, on_or_before...
    else
    {
      int day = rule.getDay();
      int week = 0;

      if (rule.getDayMode() == DstRule.ON_OR_AFTER)
      {
        switch (day)
        {
          case 1:
            week = DstRule.FIRST;
            break;
          case 8:
            week = DstRule.SECOND;
            break;
          case 15:
            week = DstRule.THIRD;
            break;
          case 22:
            week = DstRule.FOURTH;
            break;
          case 29:
            week = DstRule.LAST;
            break;
          default: throw new TimeZoneException("Day = " + day + ". Can not accurately convert from ON_OR_AFTER to NTH day");
        }
      }

      if (rule.getDayMode() == DstRule.ON_OR_BEFORE)
      {
        switch (day)
        {
          case 7:
            week = DstRule.FIRST;
            break;
          case 14:
            week = DstRule.SECOND;
            break;
          case 21:
            week = DstRule.THIRD;
            break;
          case 28:
            week = DstRule.FOURTH;
            break;
          default: throw new TimeZoneException("Day = " + day + ". Can not accurately convert from ON_OR_BEFORE to NTH day");
        }
      }

      return DstRule.makeWeekday(rule.getTime(), rule.getTimeMode(), week, rule.getWeekday(), rule.getMonth());
    }
  }

////////////////////////////////////////////////////////////////
// Equals
////////////////////////////////////////////////////////////////

  /**
   * Compare two rules for equality.
   *
   * @return Returns true if the rules have the exact same
   *   parameters.  Otherwise, returns false.
   */
  public static boolean equals(final DstRule r1, final DstRule r2)
  {
    // if r1 and r2 are both null, return true
    // if r1 == null and r2 is not null, return false
    if (r1 == null) return (r2 == null);

    //  at this point we KNOW that r1 != null, so if r1 == r2, the must refer to the same rule
    if (r1 == r2) return true;

    // at this point we KNOW that r1 != null, so if r2 == null, return false
    if (r2 == null) return false;

    // neither are null
    return ((r1.timeOfDay.equals(r2.timeOfDay)) &&
            (r1.timeMode == r2.timeMode)        &&
            (r1.month.equals(r2.month))         &&
            (r1.day      == r2.day)             &&
            (r1.dayMode  == r2.dayMode)         &&
            (r1.weekday  == r2.weekday)         &&
            (r1.week     == r2.week)               );
  }

  /**
   * Compare two rules for equivalence.
   *
   * @return Returns true if the rules will behave exactly
   * the same way.  Otherwise, returns false.
   */
  public static boolean isEquivalent(final DstRule   r1,
                                     final DstRule   r2,
                                     final BTimeZone tz,
                                     final int       boundary)
  {
    // if r1 and r2 are both null, return true
    // if r1 == null and r2 is not null, return false
    if (r1 == null) return (r2 == null);

    //  at this point we KNOW that r1 != null, so if r1 == r2, the must refer to the same rule
    if (r1 == r2) return true;

    //  but if r2 is null, and we know that r1 isn't, they can't be equivalent
    if (r2 == null) return false;

    //  neither are null

    //if either rule is ON_OR_AFTER or ON_OR_BEFORE,
    //convert it nth weekday if you can for increased support.

    DstRule w1 = null,
            w2 = null;

    try
    {
      w1 = asWeekdayRule(r1);
    }
    catch (TimeZoneException e)
    {
      w1 = r1;
    }

    try
    {
      w2 = asWeekdayRule(r2);
    }
    catch (TimeZoneException e)
    {
      w2 = r2;
    }

    // the fifth week is always the last one
    int week1 = w1.week,
        week2 = w2.week;

    if (week1 == FIFTH) week1 = LAST;
    if (week2 == FIFTH) week2 = LAST;

    if ((w1.month.equals(w2.month)) &&
        (w1.day     == w2.day)      &&
        (w1.dayMode == w2.dayMode)  &&
        (w1.weekday == w2.weekday)  &&
        (week1      == week2)           )
    {
      if ((w1.timeMode == w2.timeMode) && w1.timeOfDay.equals(w2.timeOfDay))
      {
        return true;
      }

      w1 = w1.asWallTimeRule(boundary, tz);
      w2 = w2.asWallTimeRule(boundary, tz);

      return ((w1 != null) &&
              (w2 != null) &&
              (w1.timeOfDay.equals(w2.timeOfDay)));
    }

    return false;
  }

  /**
   * Compare this instance to the specified object for equality.
   *
   * @return Returns true if o is a DstRule with the exact same
   *   parameters.  Otherwise, returns false.
   */
  public boolean equals(Object o)
  {
    return (o instanceof DstRule) && equals(this, (DstRule)o);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(timeOfDay, timeMode, month, day, dayMode, weekday, week);
  }

  /**
   * Compare this instance to the specified object for equivalence.
   *
   * @return Returns true if o is a DstRule with the exact same
   *   parameters.  Otherwise, returns false.
   */
  public boolean isEquivalent(DstRule o, BTimeZone tz, int boundary)
  {
    return isEquivalent(this, o, tz, boundary);
  }

////////////////////////////////////////////////////////////////
// Codec
////////////////////////////////////////////////////////////////

  /**
   * Encode this instance to the specified output.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    timeOfDay.encode(out);
    out.writeInt(timeMode);
    month.encode(out);
    out.writeInt(day);
    out.writeInt(dayMode);
    out.writeBoolean(weekday != null);
    if (weekday != null) weekday.encode(out);
    out.writeInt(week);
  }

  /**
   * Decode an instance from ths specified input.
   */
  public static DstRule decode(DataInput in)
    throws IOException
  {
    DstRule rule = new DstRule();

    rule.timeOfDay = (BTime)BTime.DEFAULT.decode(in);
    rule.timeMode = in.readInt();
    rule.month = (BMonth)BMonth.january.decode(in);
    rule.day = in.readInt();
    rule.dayMode = in.readInt();
    if (in.readBoolean())
      rule.weekday = (BWeekday)BWeekday.sunday.decode(in);
    rule.week = in.readInt();

    return rule;
  }

  /**
   * Encode this instance to a string.
   */
  public String encodeToString()
    throws IOException
  {
    StringBuilder s = new StringBuilder(128);

    s.append(timeOfDay.encodeToString());
    s.append(',');
    s.append(encodeTimeMode(timeMode));
    s.append(',');
    s.append(month.encodeToString());
    s.append(',');
    s.append(day);
    s.append(',');
    s.append(encodeDayMode(dayMode));
    s.append(',');
    s.append((weekday == null) ? "null" : weekday.encodeToString());
    s.append(',');
    s.append(encodeWeek(week));

    return s.toString();
  }

  /**
   * Decode an instance from the specified string.
   */
  public static DstRule decodeFromString(String s)
    throws IOException
  {

    StringTokenizer tokens = new StringTokenizer(s, ",");

    DstRule rule = new DstRule();

    rule.timeOfDay = (BTime)BTime.DEFAULT.decodeFromString(tokens.nextToken());
    rule.timeMode = decodeTimeMode(tokens.nextToken());
    rule.month = (BMonth)BMonth.january.decodeFromString(tokens.nextToken());
    rule.day = Integer.parseInt(tokens.nextToken());
    rule.dayMode = decodeDayMode(tokens.nextToken());
    String weekday = tokens.nextToken();
    if (!weekday.equals("null"))
      rule.weekday = (BWeekday)BWeekday.sunday.decodeFromString(weekday);
    rule.week = decodeWeek(tokens.nextToken());

    return rule;
  }

  /**
   * Encode the day mode to a string.
   */
  public static String encodeDayMode(int dayMode)
  {
    switch (dayMode)
    {
      case WEEKDAY     : return "undefined";
      case EXACT       : return "exact";
      case ON_OR_BEFORE: return "on or before";
      case ON_OR_AFTER : return "on or after";

      default: return "invalid: " + dayMode;
    }
  }

  /**
   * Decode the day mode from a string.
   */
  public static int decodeDayMode(String txt)
  {
    if (txt.equals("undefined")) return WEEKDAY;
    if (txt.equals("exact")) return EXACT;
    if (txt.equals("on or before")) return ON_OR_BEFORE;
    if (txt.equals("on or after")) return ON_OR_AFTER;

    return WEEKDAY;
  }

  /**
   * Encode the week to a string.
   */
  public static String encodeWeek(int week)
  {
    switch (week)
    {
      case FIRST       : return "first";
      case SECOND      : return "second";
      case THIRD       : return "third";
      case FOURTH      : return "fourth";
      case FIFTH       : return "fifth";
      case LAST        : return "last";
      case UNDEFINED   : return "undefined";

      default:
        throw new IllegalArgumentException("Invalid week specification: " + week);
    }
  }

  /**
   * Decode the week from a string.
   */
  public static int decodeWeek(String txt)
  {
    if (txt.equals("first")) return FIRST;
    if (txt.equals("second")) return SECOND;
    if (txt.equals("third")) return THIRD;
    if (txt.equals("fourth")) return FOURTH;
    if (txt.equals("fifth")) return FIFTH;
    if (txt.equals("last")) return LAST;
    if (txt.equals("undefined")) return UNDEFINED;

    throw new IllegalArgumentException("Invalid week specification: " + txt);
  }

  /**
   * Encode the day mode to a string.
   */
  public static String encodeTimeMode(int timeMode)
  {
    switch (timeMode)
    {
      case WALL_TIME    : return "wall";
      case STANDARD_TIME: return "standard";
      case UTC_TIME     : return "utc";

      default: return "invalid: " + timeMode;
    }
  }

  /**
   * Decode the time mode from a string.
   */
  public static int decodeTimeMode(String txt)
  {
    if (txt.equals("wall")) return WALL_TIME;
    if (txt.equals("standard")) return STANDARD_TIME;
    if (txt.equals("utc")) return UTC_TIME;

    return UNDEFINED;
  }

////////////////////////////////////////////////////////////////
// General Constants
////////////////////////////////////////////////////////////////

  /*
   * IMPORTANT: constant definitions must be the same as those
   * defined in the platform module's <time/DstRule.h> header
   */
  public static final int UNDEFINED    = -1;

////////////////////////////////////////////////////////////////
// Time Mode Constants
////////////////////////////////////////////////////////////////

  /** Indicates that the time of day is expressed in terms of the
      wall time.  That means that daylight savings time may be
      in effect.  For example, if start time is 2:00AM in wall time,
      then the start time is 2:00AM standard time.  If the end time
      is 2:00AM in wall time, then the end time is 2:00AM daylight
      savings time. */
  public static final short WALL_TIME     = 0;

  /** Indicates that the time of day is expressed in terms of standard
      time only, regardless of whether daylight savings time is in effect. */
  public static final short STANDARD_TIME = 1;

  /** Indicates that the time of day is expressed in terms of UTC time. */
  public static final short UTC_TIME      = 2;

////////////////////////////////////////////////////////////////
// Day Mode Constants
////////////////////////////////////////////////////////////////

  public static final int WEEKDAY      = UNDEFINED;


  public static final short EXACT        = 0;
  public static final short ON_OR_AFTER  = 1;
  public static final short ON_OR_BEFORE = 2;

  /** The first week. (1-7) */
  public static final short FIRST  = 0;   //--> 0
  /** The first week. (8-14) */
  public static final short SECOND = 1;   //--> 1
  /** The first week. (15-21) */
  public static final short THIRD  = 2;   //--> 2
  /** The first week. (22-28) */
  public static final short FOURTH = 3;   //--> 3
  /** The fifth week. (29-31) */
  public static final short FIFTH  = 4;   //--> 4
  /** The last week. (last 7 days of the month) */
  public static final short LAST   = 5;   //--> 5

////////////////////////////////////////////////////////////////
// Boundary Constants
////////////////////////////////////////////////////////////////

  public static final int START = 0;
  public static final int END = 1;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int cacheYear = -1;
  private int cacheBoundary = -1;
  private BTimeZone cacheTimeZone;
  private BAbsTime cachedTime;

  private BTime timeOfDay;
  private int timeMode;
  private BMonth month;
  private int day;
  private int dayMode;
  private BWeekday weekday;
  private int week;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  //NCCB-48541: Employ a LRU replacement cache to enhance the performance of ZoneRules
  //            transitions retrieval. Prevents the re-construction of zone rules for the same ID.
  private static final Map<String, List<ZoneOffsetTransition>> LRU_RULE_TRANSITIONS_CACHE =
          Collections.synchronizedMap(new LinkedHashMap<String, List<ZoneOffsetTransition>>(16, 0.75f, true)
                                      {
                                        @Override
                                        protected boolean removeEldestEntry(Map.Entry<String, List<ZoneOffsetTransition>> eldest)
                                        {
                                          return size() > MAX_RULES_CACHE;
                                        }
                                      });

  private static final int MAX_RULES_CACHE = 10;
}
