/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.time;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BMonth;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridiumx.analytics.time.Duration;
import com.tridiumx.analytics.time.TimeBinding;
import com.tridiumx.analytics.util.Strings;
import com.tridiumx.analytics.util.Utils;

/**
 * Represents a period of time.  Stored as a string, the possible values are 
 * fixed enums, or a script starting with current, next, previous or from.
 * <p>
 * The fixed enums are: today, yesterday, thisWeek, lastWeek, thisMonth, 
 * lastMonth, thisYear, lastYear, weekToDate, monthToDate, and yearToDate.
 * <p>
 * The scripts starting with current, next or previous all work in a 
 * similar way.  There are three parts, the script name, a number and a unit.
 * The units are seconds, minutes, hours, days, weeks, months and years.
 * The number preceeding the unit presents the number of units.  The current
 * script includes the current unit in the time range.  The previous
 * script excludes the current unit from the time range.  So the current 5
 * months would end with the current month, while the previous 5 months
 * would end with the prior month.  Today could be expressed as the
 * current1day while yesterday could be the previous1day.  A next script
 * starts from the current unit.
 * <p>
 * A from script follow the pattern: <br>
 * from{unit|Now}[duration]To{Now|Unit|duration}[duration]
 * <p>
 * The start of the time range aligns to the unit or "Now".  A duration,
 * can tne be applied to that.  To determine the end time, what follows "To"
 * can be "Now", or it could just be duration to add to the start, or it 
 * could be one of the time units to align to.  If the end starts with "Now"
 * or a unit, it can be followed by a duration to apply to the unit.
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
@NiagaraType
@NoSlotomatic
public final class BAnalyticTimeRange
  extends BSimple
  implements TimeRange
{
  private static final int TYPE_NULL        = 0;
  private static final int TYPE_DATE_RANGE  = 1;

  public static final int ALIGN_SECOND      = 10;
  public static final int ALIGN_MINUTE      = 20;
  public static final int ALIGN_HOUR        = 30;
  public static final int ALIGN_DAY         = 40;
  public static final int ALIGN_WEEK        = 50;
  public static final int ALIGN_MONTH       = 60;
  public static final int ALIGN_YEAR        = 70;
  public static final int ALIGN_QUARTER     = 80;

  public static final BAnalyticTimeRange NULL = new BAnalyticTimeRange(
      "null",TYPE_NULL);
  public static final BAnalyticTimeRange all = new BAnalyticTimeRange(
      "all",TYPE_NULL);
  public static final BAnalyticTimeRange DEFAULT = all;

  public static final BAnalyticTimeRange today = new BAnalyticTimeRange("today",
      ALIGN_DAY,null,new Duration(false,0,0,1));
  public static final BAnalyticTimeRange yesterday = new BAnalyticTimeRange("yesterday", ALIGN_DAY,new Duration(true,0,0,1),new Duration(false,0,0,1));
  public static final BAnalyticTimeRange thisWeek = new BAnalyticTimeRange("thisWeek",
      ALIGN_WEEK,null,new Duration(false,0,0,7));
  public static final BAnalyticTimeRange lastWeek = new BAnalyticTimeRange("lastWeek",
      ALIGN_WEEK,new Duration(true,0,0,7),new Duration(false,0,0,7));
  public static final BAnalyticTimeRange thisMonth = new BAnalyticTimeRange("thisMonth",
      ALIGN_MONTH,null,new Duration(false,0,1,0));
  public static final BAnalyticTimeRange lastMonth = new BAnalyticTimeRange("lastMonth",
      ALIGN_MONTH,new Duration(true,0,1,0),new Duration(false,0,1,0));
  public static final BAnalyticTimeRange thisYear = new BAnalyticTimeRange("thisYear",
      ALIGN_YEAR,null,new Duration(false,1,0,0));
  public static final BAnalyticTimeRange lastYear = new BAnalyticTimeRange("lastYear",
      ALIGN_YEAR,new Duration(true,1,0,0),new Duration(false,1,0,0));
  public static final BAnalyticTimeRange weekToDate = new BAnalyticTimeRange(
      "weekToDate",ALIGN_WEEK,null,null);
  public static final BAnalyticTimeRange monthToDate = new BAnalyticTimeRange(
      "monthToDate",ALIGN_MONTH,null,null);
  public static final BAnalyticTimeRange yearToDate = new BAnalyticTimeRange(
      "yearToDate",ALIGN_YEAR,null,null);




//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.bajax.analytics.time.BAnalyticTimeRange(2979906276)1.0$ @*/
/* Generated Sat Nov 13 10:09:19 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAnalyticTimeRange.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  private BAnalyticTimeRange() 
  {
    this.value = "null";
    this.units = TYPE_NULL;
  }

  private BAnalyticTimeRange(String value, int units)
  {
    this.value = value;
    this.units = units;
  }

  private BAnalyticTimeRange(
      String value,
      int units,
      Duration startOffset,
      Duration endOffset)
  {
    this.value = value;
    this.units = units;
    this.startOffset = startOffset;
    this.endOffset = endOffset;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Compares the toString representations of this object and the argument.
   */
  public final int compareTo(Object obj)
  {
    if (obj instanceof BObject)
      return value.compareTo(((BObject)obj).toString(null));
    return value.compareTo(obj.toString());
  }

  public BObject decode(java.io.DataInput in)
    throws IOException
  {
    return make(in.readUTF());
  }

  public BObject decodeFromString(String s)
  {
    BObject obj = null;
    try {
      if(Strings.isDecodingRequired(s)) {
        obj = make(URLDecoder.decode(s, "UTF-8"));
      } else {
        obj = make(s);
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return obj;
  }

  public void encode(java.io.DataOutput out)
    throws IOException
  {
    out.writeUTF(value);
  }

  public String encodeToString()
  {
    return value;
  }

  /**
   * Compares the string value.
   * @param obj Should be another time range.
   * @return True if both time ranges have the same string encoding.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BAnalyticTimeRange)
    {
      BAnalyticTimeRange str = (BAnalyticTimeRange) obj;
      return value.equals(str.value);
    }
    return false;
  }

  /**
   * Returns the hashcode of the string value.
   */
  public int hashCode()
  {
    return value.hashCode();
  }

  /**
   * @return True if this == NULL
   */
  public boolean isNull()
  {
    if (this == NULL)
      return true;
    return false;
  }

  /**
   * The first excluded time after the time range ends, based on the
   * current real time.
   * @return -1 if there is no end time.
   */
  public long getEnd()
  {
    return getEnd(System.currentTimeMillis());
  }

  /**
   * The first excluded time after the time range ends, based on the
   * provided time.
   * @return -1 if there is no end time.
   */
  public long getEnd(long from)
  {
    if (units == TYPE_NULL) return -1;
    if (units == TYPE_DATE_RANGE)
    {
      try
      {
        return TimeBinding.make(
            value.substring(value.indexOf(';')+1)).getTimeInMillis();
      }
      catch (Exception x)
      {
        throw new IllegalArgumentException(Utils.lex("invalidTime") 
            + ": " + value.substring(value.indexOf(';')+1));
      }
    }
    if (endOffset == null)
      return from;
    return endOffset.addTo(getStart(from));
  }

  /**
   * The inclusive start time, based on the current real time.
   */
  public long getStart()
  {
    return getStart(System.currentTimeMillis());
  }

  /**
   * The start of the time range for the given start time.
   * @param from The end time.
   */
  public long getStart(long from)
  {
    if (units == TYPE_NULL) return nullStart.getMillis();
    if (units == TYPE_DATE_RANGE)
    {
      try
      {
        return TimeBinding.make(
            value.substring(0,value.indexOf(';'))).getTimeInMillis();
      }
      catch (Exception x)
      {
        throw new IllegalArgumentException(Utils.lex("invalidTime") 
            + ": " + value.substring(0,value.indexOf(';')));
      }
    }
    long start = align(from,units);
    if (startOffset != null)
      start = startOffset.addTo(start);
    return start;
  }

  public long alignStart(long from, long interval)
  {
     if(interval == BInterval.MILLIS_YEAR){
        from = align(from, ALIGN_YEAR);
     }else if(interval == BInterval.MILLIS_QUARTER){
       from = align(from,ALIGN_QUARTER);
     }else if(interval == BInterval.MILLIS_MONTH){
       from = align(from, ALIGN_MONTH);
     }else if(interval == BInterval.MILLIS_WEEK){
       from = align(from, ALIGN_WEEK);
     }else{
       long f1 = align(from, ALIGN_YEAR);
       from = f1 + (((from -f1) / interval) * interval);
     }

    return from;
  }

  /**
   * Returns a fixed time range.
   * @param start Must not be null and must be before the end.
   * @param end Must not be null and must be after the start.
   * @return A fixed time range.
   */
  public static BAnalyticTimeRange make(BAbsTime start, BAbsTime end)
  {
    return new BAnalyticTimeRange(
        start.encodeToString()+";"+end.encodeToString(),TYPE_DATE_RANGE);
  }

  /**
   * Decodes the string according to the specification in the class docs.
   */
  public static BAnalyticTimeRange make(String s)
  {
    if (s == null) return NULL;
    if (s.length() == 0) return NULL;
    switch (s.charAt(0))
    {
      case 't': //this,today
      case 'y': //yearToDate
      case 'm': //monthToDate
      case 'w': //weekToDate
      case 'l': //last
      case 'a': //all
        return parseConstant(s);
      case 'c':  //current
        return parseCurrent(s);
      case 'p':  //previous
        return parsePrevious(s);
      case 'n':  //next
        if (s.startsWith("null")) return NULL;
        return parseNext(s);
      case 'f':  //from
        return parseFrom(s);
    }
    if (s.indexOf(';') > 0) 
      return new BAnalyticTimeRange(s,TYPE_DATE_RANGE);
    if (s.startsWith("NULL")) return NULL;
    throw new IllegalArgumentException(Utils.lex("invalidTime") + ": " + s);
  }

  /**
   * Only public because used by the Workbench FE.
   */
  public static String[] parseParts(String s)
  {
    if (s == null) return new String[] {"null"};
    if (s.length() == 0) return new String[] {"null"};
    switch (s.charAt(0))
    {
      case 't': //this
      case 'y': //yearToDate
      case 'm': //monthToDate
      case 'w': //weekToDate
      case 'l': //last
      case 'a': //all
        return new String[] {s};
      case 'c':  //current
      case 'p':  //previous
      case 'n':  //next
        if (s.equals("null")) return new String[] {s};
        return parsePcnParts(s);
      case 'f':  //from
        return parseFromParts(s);
    }
    if (s.indexOf(';') > 0) 
      return new String[] {s};
    throw new IllegalArgumentException(Utils.lex("invalidTime") + ": " + s);
  }

  /**
   * Returns the value as encode to string.
   */
  public String toString(Context cx)
  {
    return value;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Align to the start of the given unit.
   */
  private long align(long base, int unit)
  {
    switch (unit)
    {
      case ALIGN_SECOND :
        return TimeBinding.alignSecond(base);
      case ALIGN_MINUTE :
        return TimeBinding.alignMinute(base);
      case ALIGN_HOUR :
        return TimeBinding.alignHour(base);
      case ALIGN_DAY :
        return TimeBinding.alignDay(base);
      case ALIGN_WEEK :
        return TimeBinding.alignWeek(base);

      case ALIGN_MONTH :
        return TimeBinding.alignMonth(base);
      case ALIGN_YEAR :
        return TimeBinding.alignYear(base);
      case ALIGN_QUARTER:
        return TimeBinding.alignQuarter(base);
    }
    throw new IllegalStateException(Utils.lex("invalidTime")+": "+value);
  }

  /**
   * Decodes a contant time range such a yesterday or lastYear.
   */
  private static BAnalyticTimeRange parseConstant(String str)
  {
    switch (str.charAt(0))
    {
      case 'y': //yearToDate
        if (str.equals("yesterday")) return yesterday;
        if (str.equals("yearToDate")) return yearToDate;
        break;
      case 'm': //monthToDate
        if (str.equals("monthToDate")) return monthToDate;
        break;
      case 'w': //weekToDate
        if (str.equals("weekToDate")) return weekToDate;
        break;
      case 't': //this
        if (str.equals("today")) return today;
        if (str.equals("thisWeek")) return thisWeek;
        if (str.equals("thisMonth")) return thisMonth;
        if (str.equals("thisYear")) return thisYear;
        break;
      case 'l': //last
        if (str.equals("lastWeek")) return lastWeek;
        if (str.equals("lastMonth")) return lastMonth;
        if (str.equals("lastYear")) return lastYear;
        break;
      case 'a':
        if (str.equals("all")) return all;
        break;
    }
    throw new IllegalStateException(Utils.lex("invalidTime")+": "+str);
  }

  /**
   * Parses a currentNunits time range.
   */
  private static BAnalyticTimeRange parseCurrent(String s)
  {
    int i = 7;//"current".length();
    int j = i;
    int len = s.length();
    while (j < len)
    {
      if (Character.isDigit(s.charAt(j)))
      {
        j++;
      }
      else
      {
        break;
      }
    }
    int num = Integer.parseInt(s.substring(i,j));
    int units = toUnit(s,s.charAt(j),s.charAt(j+1));
    Duration sdur = new Duration();
    sdur.negative = true;
    Duration edur = new Duration();
    switch (units)
    {
      case ALIGN_SECOND : //fromSecond-PTnumSToPTnumS
        sdur.seconds = num-1;
        edur.seconds = num;
        break;
      case ALIGN_MINUTE : //fromMinute-PTnumMToPTnumM
        sdur.minutes = num-1;
        edur.minutes = num;
        break;
      case ALIGN_HOUR   : //fromHour-PTnumMToPTnumM
        sdur.hours = num-1;
        edur.hours = num;
        break;
      case ALIGN_DAY    : //fromDay-PnumDToPnumD
        sdur.days = num-1;
        edur.days = num;
        break;
      case ALIGN_WEEK   : //fromWeek-Pnum*7DToPnum*7D
        sdur.days = (num-1) * 7;
        edur.days = num * 7;
        break;
      case ALIGN_MONTH  : //fromMonth-PnumMToPnumM
        sdur.months = num-1;
        edur.months = num;
        break;
      case ALIGN_YEAR   : //fromYear-PnumYToPnumY
        sdur.years = num-1;
        edur.years = num;
        break;
    }
    if (num == 1) sdur = null;
    return new BAnalyticTimeRange(s, units, sdur, edur);
  }

  /**
   * Parses a from-to time range.
   */
  private static BAnalyticTimeRange parseFrom(String str)
  {
    int units = toUnit(str,str.charAt(4),str.charAt(5));
    Duration startOff = null;
    Duration endOff = null;
    int to = str.indexOf("To");
    if (to < 0) 
      throw new IllegalStateException(Utils.lex("invalidTime")+": "+str);
    int i = str.indexOf("P",6);
    if ((i > 0) && (i < to))
    {
      char ch = str.charAt(i - 1);
      if ((ch == '+') || (ch == '-')) i--;
      startOff = Duration.make(str.substring(i,to));
    }
    if (str.indexOf("Now",to) < 0)
      endOff = Duration.make(str.substring(to+2));
    return new BAnalyticTimeRange(str,units,startOff,endOff);
  }

  /**
   * Extracts the parts of a 'from' time range.
   */
  private static String[] parseFromParts(String str)
  {
    ArrayList<String> list = new ArrayList<>();
    if (str.startsWith("from")) list.add("from");
    else 
      throw new IllegalStateException(Utils.lex("invalidTime")+": "+str);
    int to = str.indexOf("To");
    if (to < 0)
      throw new IllegalStateException(Utils.lex("invalidTime")+": "+str);
    int i = str.indexOf("P",6);
    if ((i > 0) && (i < to))
    {
      char ch = str.charAt(i - 1);
      if ((ch == '+') || (ch == '-'))
        i--;
      list.add(str.substring(4,i)); //alignment
      list.add(str.substring(i,to)); //start offset
    }
    else
    {
      list.add(str.substring(4,to)); //alignment
    }
    list.add("To");
    if (str.indexOf("Now",to) < 0)
      list.add(str.substring(to+2));
    else
      list.add("Now");
    String[] ret = new String[list.size()];
    list.toArray(ret);
    return ret;
  }

  /**
   * Parses a nextNunits time range.
   */
  private static BAnalyticTimeRange parseNext(String s)
  {
    int i = 4;//"next".length();
    int j = i;
    int len = s.length();
    while (j < len)
    {
      if (Character.isDigit(s.charAt(j)))
      {
        j++;
      }
      else
      {
        break;
      }
    }
    int num = Integer.parseInt(s.substring(i,j));
    int units = toUnit(s,s.charAt(j),s.charAt(j+1));
    Duration sdur = new Duration();
    Duration edur = new Duration();
    switch (units)
    {
      case ALIGN_SECOND : //fromSecond-PTnumSToPTnumS
        sdur.seconds = 1;
        edur.seconds = num;
        break;
      case ALIGN_MINUTE : //fromMinute-PTnumMToPTnumM
        sdur.minutes = 1;
        edur.minutes = num;
        break;
      case ALIGN_HOUR   : //fromHour-PTnumMToPTnumM
        sdur.hours = 1;
        edur.hours = num;
        break;
      case ALIGN_DAY    : //fromDay-PnumDToPnumD
        sdur.days = 1;
        edur.days = num;
        break;
      case ALIGN_WEEK   : //fromWeek-Pnum*7DToPnum*7D
        sdur.days = 7;
        edur.days = num * 7;
        break;
      case ALIGN_MONTH  : //fromMonth-PnumMToPnumM
        sdur.months = 1;
        edur.months = num;
        break;
      case ALIGN_YEAR   : //fromYear-PnumYToPnumY
        sdur.years = 1;
        edur.years = num;
        break;
    }
    return new BAnalyticTimeRange(s, units, sdur, edur);
  }

  /**
   * Parse the parts out of a previous/current/next time range.
   */
  private static String[] parsePcnParts(String s)
  {
    String[] ret = new String[3];
    if (s.startsWith("previous")) ret[0] = "previous";
    else if (s.startsWith("current")) ret[0] = "current";
    else if (s.startsWith("next")) ret[0] = "next";
    else throw new IllegalStateException(Utils.lex("invalidTime")+": "+s);
    int i = ret[0].length();
    int j = i;
    int len = s.length();
    while (j < len)
    {
      if (Character.isDigit(s.charAt(j)))
      {
        j++;
      }
      else
      {
        break;
      }
    }
    ret[1] = s.substring(i,j);
    ret[2] = s.substring(j);
    return ret;
  }

  /**
   * Parses a previous time range.
   */
  private static BAnalyticTimeRange parsePrevious(String s)
  {
    int i = 8;//"previous".length();
    int j = i;
    int len = s.length();
    while (j < len)
    {
      if (Character.isDigit(s.charAt(j)))
      {
        j++;
      }
      else
      {
        break;
      }
    }
    int num = Integer.parseInt(s.substring(i,j));
    int units = toUnit(s,s.charAt(j),s.charAt(j+1));
    Duration sdur = new Duration();
    sdur.negative = true;
    Duration edur = new Duration();
    switch (units)
    {
      case ALIGN_SECOND : //fromSecond-PTnumSToPTnumS
        sdur.seconds = num;
        edur.seconds = num;
        break;
      case ALIGN_MINUTE : //fromMinute-PTnumMToPTnumM
        sdur.minutes = num;
        edur.minutes = num;
        break;
      case ALIGN_HOUR   : //fromHour-PTnumMToPTnumM
        sdur.hours = num;
        edur.hours = num;
        break;
      case ALIGN_DAY    : //fromDay-PnumDToPnumD
        sdur.days = num;
        edur.days = num;
        break;
      case ALIGN_WEEK   : //fromWeek-Pnum*7DToPnum*7D
        sdur.days = num * 7;
        edur.days = num * 7;
        break;
      case ALIGN_MONTH  : //fromMonth-PnumMToPnumM
        sdur.months = num;
        edur.months = num;
        break;
      case ALIGN_YEAR   : //fromYear-PnumYToPnumY
        sdur.years = num;
        edur.years = num;
        break;
    }
    return new BAnalyticTimeRange(s, units, sdur, edur);
  }

  /**
   * Decode a time unit and returns one of the constants.
   */
  private static int toUnit(String tr, char c1, char c2)
  {
    switch (c1)
    {
      case 'S' :
      case 's' : return ALIGN_SECOND;
      case 'M' :
      case 'm' :
        if (c2 == 'i') return ALIGN_MINUTE;
        return ALIGN_MONTH;
      case 'H' :
      case 'h' : return ALIGN_HOUR;
      case 'D' :
      case 'd' : return ALIGN_DAY;
      case 'W' :
      case 'w' : return ALIGN_WEEK;
      case 'Y' :
      case 'y' : return ALIGN_YEAR;
    }
    throw new IllegalStateException(Utils.lex("invalidTime")+": "+tr);
  }


  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /* This will be a problem if anyone has data older than the year 2000. */
  private static BAbsTime nullStart = BAbsTime.make(2000, BMonth.january, 1);

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  private String value;
  private int units;
  private Duration startOffset = null;
  private Duration endOffset = null;


  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//ComplexTimeRange
