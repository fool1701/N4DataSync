/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BMonth;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;

import com.tridium.util.TimeFormat;

/**
 * BAbsTimeFE allows viewing and editing of a BAbsTime.
 *
 * @author    Brian Frank
 * @creation  18 Jul 01
 * @version   $Revision: 22$ $Date: 12/8/09 4:16:33 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:AbsTime"
  )
)
public class BAbsTimeFE
  extends BMultiFieldFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BAbsTimeFE(2419597384)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbsTimeFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BAbsTimeFE()
  {
  }
  
  public BAbsTimeFE(BAbsTime time)
  {
    this();
    loadValue(time);
  }

////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
  {
    BAbsTime abs = (BAbsTime)value;

    this.timeZone = abs.getTimeZone();

    if (abs.isNull() && isReadonly())
    {
      fields = new Field[] { new LabelField("null") };
      return;
    }

    ArrayList<Field> v  = new ArrayList<>();
    int[] pattern = TimeFormat.pattern(cx);

    boolean showMillis = false;
    if (cx != null)
    {
      BBoolean b = (BBoolean)cx.getFacet(BFacets.SHOW_MILLISECONDS);
      if (b != null) showMillis = b.getBoolean();
      b = (BBoolean)cx.getFacet(BFacets.SHOW_DATE);
      if (b == null || b.getBoolean()) addDatePicker();

      BTimeZone tz = (BTimeZone)cx.getFacet(BFacets.TIME_ZONE);
      if (tz != null)
      {
        this.timeZone = tz;
        abs = BAbsTime.make(abs, tz);
      }
    }

    for (int i=0; i<pattern.length; i++)
    {
      switch (pattern[i])
      {
        case TimeFormat.YEAR_2: // fall through?
        case TimeFormat.YEAR_4:
          year = new YearField();
          year.set(abs.getYear());
          v.add(year);
          break;

        case TimeFormat.MON_1:  // fall through
        case TimeFormat.MON_2:
          month = new MonthField();
          month.set(abs.getMonth().getOrdinal());
          v.add(month);
          break;

        case TimeFormat.MON_TAG:
          month = new MonthTagField();
          month.set(abs.getMonth().getOrdinal());
          v.add(month);
          break;

        case TimeFormat.DAY_1:  // fall through
        case TimeFormat.DAY_2:
          day = new DayField();
          day.set(abs.getDay());
          v.add(day);
          break;

        case TimeFormat.HOUR_12_1:  // fall through
        case TimeFormat.HOUR_12_2:
          TwelveHourField hour12 = new TwelveHourField();
          int h = abs.getHour();
          if (h >= 12) h -= 12;
          hour12.set(h);
          v.add(hour12);
          break;

        case TimeFormat.HOUR_24_1:  // fall through
        case TimeFormat.HOUR_24_2:
          HourField hour = new HourField();
          hour.set(abs.getHour());
          v.add(hour);
          break;

        case TimeFormat.MIN:
          MinuteField min = new MinuteField();
          min.set(abs.getMinute());
          v.add(min);
          break;

        case TimeFormat.AM_PM:
          AmPmField m = new AmPmField();
          m.set((abs.getHour() < 12) ? 0 : 1);
          v.add(m);
          break;

        case TimeFormat.SEC:
          SecondField sec = new SecondField();
          sec.set(abs.getSecond());
          v.add(sec);
          if (showMillis)
          {
            MillisecondField millis = new MillisecondField();
            millis.set(abs.getMillisecond());
            v.add(new LabelField("."));
            v.add(millis);
          }
          break;

        case TimeFormat.ZONE_TAG:
          String tz = abs.getTimeZoneShortName(cx);
          if (tz == null) tz = "???";
          v.add(new LabelField(tz));
          break;

        case TimeFormat.WEEK_1:
        case TimeFormat.WEEK_2:
          break;
          
        default:
          v.add(new LabelField("" + (char)pattern[i]));
          break;
      }
    }

    fields = v.toArray(new Field[v.size()]);
  }

  protected BObject doSaveValue(BObject value, Context cx)
  {
    Date targetDate = new Date((value instanceof BAbsTime) ? ((BAbsTime)value).getMillis() : 0);
    
    //issue 13463, since we don't know the target timezone era yet (we have to get these
    //from the fields later on) we need to use a fully fledged historical timezone, with
    //dst support, in order to properly render the resulting time.
    Calendar c = new GregorianCalendar((TimeZone)timeZone.tzSupport());

    // seed with original (Issue 10870)
    c.setTime(targetDate);

    // Issue 11592, if milliseconds and seconds aren't specified
    // then we always want to assume the top of the minute as the value.
    // Since date is seeded with original value, and if seconds/milliseconds
    // are hidden via facet. saving 12:00 results in 12:00.[whatever seconds].[whatever milliseconds] 
    int targetMilliseconds = -1,
        targetSeconds = -1;    
    
    boolean twelveHour = false;
    boolean addPM = false, addAM = false;
    
    for (int i=0; i<fields.length; i++)
    {
      Field f = fields[i];
      if (f instanceof TwelveHourField) twelveHour = true;

      if (f instanceof YearField)   c.set(Calendar.YEAR, f.value);
      if (f instanceof MonthField)  c.set(Calendar.MONTH, f.value);
      if (f instanceof DayField)    c.set(Calendar.DATE, f.value);
      
      // Issue 12019 - Need to correctly handle 12AM and 12PM
      if (f instanceof HourField || f instanceof TwelveHourField)
      {
        int calendarHourOfDay = f.value;
        if (addPM && twelveHour)
        {
          if (calendarHourOfDay<12) // If hour is 1-11 then add 12 to it
            calendarHourOfDay += 12;
        }
        else if (addAM && twelveHour) 
        {
          if (calendarHourOfDay==12) // If hour is 12 AM then...
            calendarHourOfDay = 0;   // Hour of day is 0 (Issue 12019)
        }
        c.set(Calendar.HOUR_OF_DAY, calendarHourOfDay);
      }
      if (f instanceof MinuteField) c.set(Calendar.MINUTE, f.value);
      
      //Issue 11592, handle seconds and milliseconds being hidden,
      //which is a common occurence in FE. If they were not 
      //specified in the op, don't allow them to retain original seeded values.
      if (f instanceof SecondField)      targetSeconds = f.value;
      if (f instanceof MillisecondField) targetMilliseconds = f.value;

      // Issue 12019 - Need to correctly handle 12AM and 12PM
      if (f instanceof AmPmField && twelveHour)
      {
        // The following int value was assigned to the Calendar c in a previous
        // loop iteration
        int calendarHourOfDay = c.get(Calendar.HOUR_OF_DAY);
        
        if (f.value == 1) //  If PM
        {
          if (calendarHourOfDay < 12) // If hour is 1-11 then add 12 to it
           c.set(Calendar.HOUR_OF_DAY, calendarHourOfDay + 12);
        }
        else // If AM (f.value must be == 0 because 0 = AM and 1 = PM and
        {    // there are no more choices)
          
          if (calendarHourOfDay==12) // If hour is 12 AM then...
            c.set(Calendar.HOUR_OF_DAY, 0); // Hour of day is 0 (Issue 12019)
        }          
      }
      else if (f instanceof AmPmField && !twelveHour)
      {  
        if (f.value == 1)
          addPM = true; // In case the AmPmField is before the hour field (Issue 10352)
        else            // In case the AmPmField is before the hour field, we
          addAM = true; //  need to make 12AM have hour of 0 (Issue 12019)
      }
    }
    
    // Issue 11592, set seconds/millis to 0 if they werent specified
    c.set(Calendar.SECOND, targetSeconds == -1 ? 0 : targetSeconds);
    c.set(Calendar.MILLISECOND, targetMilliseconds == -1 ? 0 : targetMilliseconds);

    return BAbsTime.make(c.getTime().getTime(), timeZone);
  }

  protected void fieldModified(Field field)
  {
    if (((field == month) || (field == year)) && month != null && year != null)
    {
      BMonth mon = BMonth.make(month.value);
      int daysInMonth = BAbsTime.getDaysInMonth(year.value, mon);
      day.setMax(daysInMonth);
    }

    super.fieldModified(field);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  YearField year;
  MonthField month;
  DayField day;
  BTimeZone timeZone;
}
