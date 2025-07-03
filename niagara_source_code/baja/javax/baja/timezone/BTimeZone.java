/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.timezone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;

/**
 * BTimeZone is the definition of a time zone. The time zone
 * id must uniquely identify the time zone in the time zone
 * database.
 *
 * @author    John Sublett
 * @creation  19 Feb 2004
 * @version   $Revision: 58$ $Date: 3/23/11 1:09:30 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BTimeZone
  extends BSimple
  implements BIDataValue
{
  /**
   * Private default constructor.
   */
  private BTimeZone()
  {
  }

  /**
   * Get the time zone with the specified Olson id
   */
  public static BTimeZone getTimeZone(final String id)
  {
    //Retrieve the Time Zone from the internal java database
    final TimeZone javaTimeZone = TimeZone.getTimeZone(id);
    
    //If the Zone ID requested matches what we asked for, TimeZone is supported
    if (javaTimeZone != null && javaTimeZone.getID().equalsIgnoreCase(id))
    {
      //Create the BTimeZone object from the Java TimeZone (and tzsupport)
      return BTimeZone.makeFromJavaTimeZone(javaTimeZone);           
    }
      
    //if you didn't find it in either location, print an appropriate error message
    logger.fine("Could not find definition for time zone: " + id);
    throw new TimeZoneException("Unknown time zone: " + id);     
  }

  /**
   * Make a time zone with the specified offset from UTC. If an attempt is made
   * to create a time zone with an id that is already defined by the internal
   * database, the existing zone is returned and the rules defined by the parameters
   * to this method are ignored. If no zone in the internal database matches
   * the provided id, the behavior of this function is identical to makeIgnoringCache().
   * 
   * To create an instance without being affected by the internal database, 
   * use makeIgnoringCache().
   *
   * @param id The unique identifier for the time zone.
   * @param utcOffset The offset from UTC in milliseconds.
   */
  public static BTimeZone make(final String id, final int utcOffset)
  {
    return make(id, id, id, id, id, utcOffset, 0, null, null);
  }

  /**
   * Make a time zone with the specified offset from UTC and daylight saving
   * time adjustment and rules. If an attempt is made to create a time zone with 
   * an id that is already defined by the internal database, the existing zone
   * is returned and the rules defined by the parameters to this method are 
   * ignored. If no zone in the internal database matches the provided id,
   * the behavior of this function is identical to makeIgnoringCache().
   * 
   * To create an instance without being affected by the internal database, 
   * use makeIgnoringCache().
   *
   * @param id The unique identifier for the time zone.
   * @param utcOffset The offset from UTC in milliseconds.
   * @param daylightAdj The difference between standard time and daylight saving
   *   time in milliseconds.
   * @param startRule The rule that determines the start of daylight saving time.
   * @param endRule The rule that determines the end of daylight saving time.
   */
  public static BTimeZone make(final String   id,
                               final int      utcOffset,
                               final int      daylightAdj,
                               final DstRule  startRule,
                               final DstRule  endRule)
  {
    return make(id, id, id, id, id, utcOffset, daylightAdj, startRule, endRule);
  }

  /**
   * Make a time zone with the specified offset from UTC and daylight saving
   * time adjustment, name, and rules. If an attempt is made to create a time zone with 
   * an id that is already defined by the internal database, the existing zone
   * is returned and the rules defined by the parameters to this method are 
   * ignored. If no zone in the internal database matches the provided id,
   * the behavior of this function is identical to makeIgnoringCache().
   *
   * @param id The unique identifier for the time zone.
   * @param stdName The display name of this time zone to use during standard time.
   * @param stdShort The short display name of this Time Zone to use during standard time.
   * @param dstName The display name of this time zone to use during daylight time.
   * @param dstShort The short display name of this Time Zone to use during daylight time.
   * @param utcOffset The offset from UTC in milliseconds.
   * @param daylightAdj The difference between standard time and daylight saving time in milliseconds.
   * @param startRule The rule that determines the start of daylight saving time.
   * @param endRule The rule that determines the end of daylight saving time.
   */
  public static BTimeZone make(final String   id,
                               final String   stdName,
                               final String   stdShort,
                               final String   dstName,
                               final String   dstShort,
                               final int      utcOffset,
                               final int      daylightAdj,
                               final DstRule  startRule,
                               final DstRule  endRule)
  {
    //check the database to see if we are aware of a time zone like that...
    try
    {
      final BTimeZone previouslyExisting = BTimeZone.getTimeZone(id);

      //if the time zone we wanted to make is already known to us,
      //it overrides anything someone else thinks...
      if (previouslyExisting != null && id.equals(previouslyExisting.id))
      {
        return previouslyExisting;
      }
    }
    //if we don't find it, thats ok too...
    catch(TimeZoneException e)
    {}

    return makeIgnoringCache(id, stdName, stdShort, dstName, dstShort, utcOffset, daylightAdj, startRule, endRule);
  }
  
  /**
   * Make a time zone with the specified offset from UTC, ignoring any
   * previously existing definition.
   *
   * @param id The unique identifier for the time zone.
   * @param utcOffset The offset from UTC in milliseconds.
   */
  public static BTimeZone makeIgnoringCache(final String id, final int utcOffset)
  {
    return makeIgnoringCache(id, id, id, id, id, utcOffset, 0, null, null);
  }  

  /**
   * Make a time zone with the specified offset from UTC, ignoring any
   * previously existing definition. The time zone will demonstrate 
   * daylight saving time behavior based on the provided parameters.
   *
   * @param id The unique identifier for the Time Zone.
   * @param stdName The display name of this Time Zone to use during standard time.
   * @param stdShort The short display name of this Time Zone to use during standard time.
   * @param dstName The display name of this Time Zone to use during daylight time.
   * @param dstShort The short display name of this Time Zone to use during daylight time.
   * @param utcOffset The offset from UTC in milliseconds.
   * @param daylightAdj The difference between standard time and daylight saving
   *   time in milliseconds.
   * @param startRule The rule that determines the start of daylight saving time.
   * @param endRule The rule that determines the end of daylight saving time.
   */
  public static BTimeZone makeIgnoringCache(final String   id,
                                            final String   stdName,
                                            final String   stdShort,
                                            final String   dstName,
                                            final String   dstShort,
                                            final int      utcOffset,
                                            final int      daylightAdj,
                                            final DstRule  startRule,
                                            final DstRule  endRule)
  {
    BTimeZone tz = new BTimeZone();

    tz.id = id;
    tz.fixedUtcOffset = utcOffset;
    tz.fixedStdName = stdName == null ? id : stdName;
    tz.fixedStdShort = stdShort == null ? tz.fixedStdName : stdShort;
    tz.fixedDstName = dstName == null ? id : dstName;
    tz.fixedDstShort = dstShort == null ? tz.fixedDstName : dstShort;
    tz.fixedDstDelta = daylightAdj;
    tz.fixedStartRule = startRule;
    tz.fixedEndRule = endRule;

    return tz;
  }

  /**
   * Make a BTimeZone from a Java TimeZone object
   */  
  private static BTimeZone makeFromJavaTimeZone(final TimeZone RHS)
  {
    //Create the object
    BTimeZone tz = new BTimeZone();
  
    //Cache the zone being provided
    tz.jzone = RHS;
    
    //Indicate where this item originated from
    tz.isJavaTimeZone = true;
    
    //Convert Java TimeZone Rule to DstRule
    tz.jzoneStartRule = (DstRule)DstRule.fw(Fw.CREATE_RULE_FROM_JAVA_TIME_ZONE, tz.jzone, Boolean.TRUE, null, null);
    tz.jzoneEndRule = (DstRule)DstRule.fw(Fw.CREATE_RULE_FROM_JAVA_TIME_ZONE, tz.jzone, Boolean.FALSE, null, null);    
    
    //Build some "BTimeZone" member values from java Time Zone
    tz.id = RHS.getID();
    
    return tz; 
  }
  
  /**
   * Is this a null instance?
   */
  @Override
  public boolean isNull()
  {
    return equals(NULL);
  }

  /**
   * Get the unique time zone identifier.
   */
  public String getId()
  {
    return id;
  }

  /**
   * Get the display name for the specified time and context.
   */
  public String getDisplayName(final BAbsTime time, final Context cx)
  {
      return getDisplayName(time.inDaylightTime(), cx);
    }

  /**
   * Get the display name for the specified daylight time state.
   */
  public String getDisplayName(final boolean daylightTime, final Context cx)
  {
    if (isJavaTimeZone)
    {
      return jzone.getDisplayName(daylightTime, TimeZone.LONG);
    }
    else
    {
      return (daylightTime) ? fixedDstName : fixedStdName;
    }
  }

  /**
   * Get the abbreviated display name for the specified time and context.
   */
  public String getShortDisplayName(final BAbsTime time, final Context cx)
  {
    return getShortDisplayName(time.inDaylightTime(), cx);
  }

  /**
   * Get the abbreviated display name for the specified daylight time state.
   */
  public String getShortDisplayName(final boolean daylightTime, final Context cx)
  {
    if (isJavaTimeZone)
    {
      return jzone.getDisplayName(daylightTime, TimeZone.SHORT);
    }
    else
    {
      return (daylightTime) ? fixedDstShort : fixedStdShort;
    }
  }

  /**
   * Get the offset from UTC during standard time in milliseconds.
   * This value is not automatically adjusted for DST. The behavior
   * of this function is comparable to the TimeZone getRawOffset()
   * method.
   *
   * @return The offset in milliseconds between a standard local
   *   time in this time zone and the same time in UTC.
   */
  public int getUtcOffset()
  {
    if (isJavaTimeZone)
    {
      return jzone.getRawOffset();
    }
    else
    {
      return fixedUtcOffset;
    }
  }

  /**
   * Get the daylight saving time adjustment during daylight time in milliseconds.
   * The behavior of this method is comparable to the TimeZone getDSTSavings()
   * method.
   *
   * @return The daylight adjustment in milliseconds to apply to this
   *   zone's UTC offset to calculate local time during DST
   */
  public int getDaylightAdjustment()
  {
    if (isJavaTimeZone)
    {
      return jzone.getDSTSavings();
    }
    else
    {
      return fixedDstDelta;
    }
  }

  /**
   * Get the offset of this zone from UTC at the provided date in milliseconds since the Epoch. 
   * If the zone uses DST, and DST is in effect at the specified date, the return value will 
   * be is adjusted with the amount of daylight saving in milliseconds. The behavior of this 
   * method is comparable to the java.util.TimeZone getUtcOffset(long millis) method.
   *
   * @param millis The requested date in milliseconds since the UTC Epoch
   * @return The UTC offset in milliseconds that was in effect at the provided date,
   *   adjusted for DST when appropriate.
   *   
   * @since Niagara 4.0   
   */
  public int getCurrentUtcOffset(final long millis)
  {
    if (isJavaTimeZone)
    {
      return jzone.getOffset(millis);
    }
    else
    {
      return getJavaTimeZone().getOffset(millis);
    }
  }

  /**
   * Get the rule that determines the start of daylight saving time.
   */
  public DstRule getDaylightStartRule()
  {
    return (isJavaTimeZone) ? jzoneStartRule : fixedStartRule;
  }

  /**
   * Get the rule that determines the start of daylight saving time.
   */
  public DstRule getDaylightEndRule()
  {
    return (isJavaTimeZone) ? jzoneEndRule : fixedEndRule;
  }

  /**
   * Return a java.util.TimeZone object that behaves the same way as this object.
   *
   * @since Niagara 3.5
   */
  public TimeZone getJavaTimeZone()
  {
    return (TimeZone)tzSupport();
  }

  /**
   * Framework use only, not to used by developers
   */
  public Object tzSupport()
  {
    //Create a java TimeZone object from this BTimeZone
    //If this zone was created from an Olson zone, this trivial
    if (jzone == null)
    {
      //if there's no start rule, and its not historical, make a simple time zone..
      if (fixedDstDelta == 0)
      {
        jzone = new SimpleTimeZone(fixedUtcOffset, id);
      }
      else
      {
        final int rawOffset = fixedUtcOffset;
        final int dstAdjustment = fixedDstDelta;

        final DstRule startRuleWall = fixedStartRule.asWallTimeRule(DstRule.START, this);
        if (startRuleWall == null)
        {
          // can't express the rule as wall time, so we can't make a
          // java.util.SimpleTimeZone for this zone using the J2ME
          // API
        }
        else
        {
          //Obtain parameters for creating a start rule
          final int startMonth = startRuleWall.getMonth().getOrdinal();
          final int startTime = (int)startRuleWall.getTime().getTimeOfDayMillis();

          final int startDay;
          int startDayOfWeek;

          if (startRuleWall.getDayMode() == DstRule.EXACT)
          {
            // day and month are given explicitly
            startDay = startRuleWall.getDay();
            startDayOfWeek = 0;
          }
          else if (startRuleWall.getDayMode() == DstRule.WEEKDAY)
          {
            startDayOfWeek = startRuleWall.getWeekday().getOrdinal() + 1;
            if (startRuleWall.getWeek() == DstRule.LAST)
            {
              // last weekday
              startDay = -1;
            }
            else
            {
              // SimpleTimeZone doesn't support nth weekday, so
              // we must express it as weekday on-or-after a date
              startDay = (startRuleWall.getWeek() * 7) + 1;
              startDayOfWeek *= -1;
            }
          }
          else if (startRuleWall.getDayMode() == DstRule.ON_OR_BEFORE)
          {
            startDay = -1 * startRuleWall.getDay();
            startDayOfWeek = -1 * (startRuleWall.getWeekday().getOrdinal() + 1);
          }
          else // ON_OR_AFTER
          {
            startDay = startRuleWall.getDay();
            startDayOfWeek = -1 * (startRuleWall.getWeekday().getOrdinal() + 1);
          }

          //Obtain parameters for creating an end rule
          final DstRule endRuleWall = fixedEndRule.asWallTimeRule(DstRule.END, this);
          if (endRuleWall == null)
          {
            // can't express the rule as wall time, so we can't make a
            // java.util.SimpleTimeZone for this zone using the J2ME
            // API
          }
          else
          {
            final int endMonth = endRuleWall.getMonth().getOrdinal();
            final int endTime = (int)endRuleWall.getTime().getTimeOfDayMillis();

            final int endDay;
            int endDayOfWeek;

            if (endRuleWall.getDayMode() == DstRule.EXACT)
            {
              // day and month are given explicitly
              endDay = endRuleWall.getDay();
              endDayOfWeek = 0;
            }
            else if (endRuleWall.getDayMode() == DstRule.WEEKDAY)
            {
              endDayOfWeek = endRuleWall.getWeekday().getOrdinal() + 1;
              if (endRuleWall.getWeek() == DstRule.LAST)
              {
                // last weekday
                endDay = -1;
              }
              else
              {
                // SimpleTimeZone doesn't support nth weekday, so
                // we must express it as weekday on-or-after a date
                endDay = (endRuleWall.getWeek() * 7) + 1;
                endDayOfWeek *= -1;
              }
            }
            else if (endRuleWall.getDayMode() == DstRule.ON_OR_BEFORE)
            {
              endDay = -1 * endRuleWall.getDay();
              endDayOfWeek = -1 * (endRuleWall.getWeekday().getOrdinal() + 1);
            }
            else // ON_OR_AFTER
            {
              endDay = endRuleWall.getDay();
              endDayOfWeek = -1 * (endRuleWall.getWeekday().getOrdinal() + 1);
            }

            //Create the zone from the parameters obtained
            try
            {
              jzone = new SimpleTimeZone(rawOffset, id,
                                         startMonth, startDay, startDayOfWeek, startTime,
                                         endMonth, endDay, endDayOfWeek, endTime, dstAdjustment);
            }
            catch (IllegalArgumentException iae)
            {
              System.out.println("rawOffset="+rawOffset);
              System.out.println("id="+id);
              System.out.println("startMonth="+startMonth);
              System.out.println("startDay="+startDay);
              System.out.println("startDayOfWeek="+startDayOfWeek);
              System.out.println("startTime="+startTime);
              System.out.println("endMonth="+endMonth);
              System.out.println("endDay="+endDay);
              System.out.println("endDayOfWeek="+endDayOfWeek);
              System.out.println("endTime="+endTime);
              System.out.println("dstSavings="+dstAdjustment);
              iae.printStackTrace();
            }
          }
        }
      }
      
      //Java failed to create the class, emulate it using the support object
      if (jzone == null)
      {
        jzone = new Support();
      }      
    }

    return jzone;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue()
  {
    return this;
  }

  /**
   * BTimeZone uses its encodeToString value's hash code.
   *
   * @since Niagara 3.4
   */
  @Override
  public int hashCode()
  {
    try
    {
      if (hashCode == -1)
      {
        hashCode = encodeToString().hashCode();
      }
      return hashCode;
    }
    catch(Exception e)
    {
      return System.identityHashCode(this);
    }
  }

  /**
   * Compare this time zone to the specified time zone to check
   * for equality.
   *
   * @return Returns true if the specified object is a BTimeZone
   *   with the same id, UTC offset, and daylight saving time rules
   *   as this time zone.
   */
  @Override
  public boolean equals(final Object o)
  {
    try
    {
      if (!(o instanceof BTimeZone)) return false; // issue 11846
      final BTimeZone tz = (BTimeZone)o;
      
      long thisAdj,
           RHSAdj,
           thisUtc,
           RHSUtc;
      DstRule thisStart,
              thisEnd,
              RHSStart,
              RHSEnd;

      if (isJavaTimeZone)
      {
        thisUtc = jzone.getRawOffset();
        thisAdj = jzone.getDSTSavings();      
        thisStart = jzoneStartRule;
        thisEnd = jzoneEndRule;        
      }
      else
      {
        thisAdj = fixedDstDelta;
        thisUtc = fixedUtcOffset;
        thisStart = fixedStartRule;
        thisEnd = fixedEndRule;
      }

      if (tz.isJavaTimeZone)
      {
        RHSUtc = tz.jzone.getRawOffset();
        RHSAdj = tz.jzone.getDSTSavings();
        RHSStart = tz.jzoneStartRule;
        RHSEnd = tz.jzoneEndRule;
      }
      else
      {
        RHSAdj = tz.fixedDstDelta;
        RHSUtc = tz.fixedUtcOffset;
        RHSStart = tz.fixedStartRule;
        RHSEnd = tz.fixedEndRule;
      }

      return tz.id.equals(id) &&
             (RHSUtc  == thisUtc) &&
             (RHSAdj == thisAdj) &&
              DstRule.equals(RHSStart, thisStart) &&
              DstRule.equals(RHSEnd, thisEnd);
    }
    catch(ClassCastException e)
    {
      return false;
    }
  }

  /**
   * Compare this time zone to the specified time zone to check
   * for equivalence.  Two time zones are equivalent if they
   * have the same UTC offset and daylight saving time rules
   * even if they do not have matching IDs.
   *
   * @return Returns true if the specified object is a BTimeZone
   *   with the UTC offset, and daylight saving time rules
   *   as this time zone.
   */
  public boolean isEquivalent(final BTimeZone tz)
  {
    //Else, determine the older way
    int thisAdj,
        thisUtc,
        RHSAdj,
        RHSUtc;
    DstRule thisStart,
            thisEnd,
            RHSStart,
            RHSEnd;

    if (isJavaTimeZone)
    {
      thisUtc = jzone.getRawOffset();
      thisAdj = jzone.getDSTSavings();      
      thisStart = jzoneStartRule;
      thisEnd = jzoneEndRule;        
    }
    else
    {
      thisAdj = fixedDstDelta;
      thisUtc = fixedUtcOffset;
      thisStart = fixedStartRule;
      thisEnd = fixedEndRule;
    }

    if (tz.isJavaTimeZone)
    {
      RHSUtc = tz.jzone.getRawOffset();
      RHSAdj = tz.jzone.getDSTSavings();
      RHSStart = tz.jzoneStartRule;
      RHSEnd = tz.jzoneEndRule;
    }
    else
    {
      RHSAdj = tz.fixedDstDelta;
      RHSUtc = tz.fixedUtcOffset;
      RHSStart = tz.fixedStartRule;
      RHSEnd = tz.fixedEndRule;
    }

    return (RHSUtc == thisUtc) &&
           (RHSAdj == thisAdj) &&
           DstRule.isEquivalent(RHSStart, thisStart, this, DstRule.START) &&
           DstRule.isEquivalent(RHSEnd, thisEnd, this, DstRule.END);
  }

  /**
   * Encode this instance to the specified output.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    int thisAdj = 0,
        thisUtc = 0;
    DstRule thisStart = null,
            thisEnd = null;

    out.writeUTF(id);

    if (isJavaTimeZone)
    {
      thisUtc = jzone.getRawOffset();
      thisAdj = jzone.getDSTSavings();      
      thisStart = jzoneStartRule;
      thisEnd = jzoneEndRule;       
    }
    else
    {
      thisUtc = fixedUtcOffset;
      thisAdj = fixedDstDelta;
      thisStart = fixedStartRule;
      thisEnd = fixedEndRule;
    }

    out.writeInt(thisUtc);
    out.writeInt(thisAdj);

    out.writeBoolean(thisStart != null);
    if (thisStart != null) thisStart.encode(out);

    out.writeBoolean(thisEnd != null);
    if (thisEnd != null) thisEnd.encode(out);
  }

  /**
   * Decode a BTimeZone instance from the specified input.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    final String id = in.readUTF();
    final int utcOffset = in.readInt();
    final int dstDelta = in.readInt();

    final DstRule startRule = (in.readBoolean()) ? DstRule.decode(in) : null;
    final DstRule endRule = (in.readBoolean()) ? DstRule.decode(in) : null;

    return make(id, id, id, id, id, utcOffset, dstDelta, startRule, endRule);
  }

  /**
   * Encode this instance to a string representation that can
   * be decoded with decodeFromString(String).  This method
   * uses the ';' character as the separator between elements
   * so no subelements (specifically BDstRule) can use the '|'
   * character in their encoding.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    if (stringEncoding == null)
    {
      StringBuilder s = new StringBuilder(128);

      int thisAdj = 0,
          thisUtc = 0;
      DstRule thisStart = null,
              thisEnd = null;

      if (isJavaTimeZone)
      {
        //The "encoding" of a Java time zone will always be the "current" values
        thisUtc = jzone.getRawOffset();
        thisAdj = jzone.getDSTSavings();      
        thisStart = jzoneStartRule;
        thisEnd = jzoneEndRule;   
      }
      else
      {
        thisUtc = fixedUtcOffset;
        thisAdj = fixedDstDelta;
        thisStart = fixedStartRule;
        thisEnd = fixedEndRule;
      }

      s.append(id);
      s.append(';');
      s.append(thisUtc);
      s.append(';');
      s.append(thisAdj);
      s.append(';');
      s.append((thisStart != null) ? thisStart.encodeToString() : "null");
      s.append(';');
      s.append((thisEnd != null) ? thisEnd.encodeToString() : "null");

      stringEncoding = s.toString();
    }
    return stringEncoding;
  }

  /**
   * Decode a string representation of a BTimeZone.
   */
  @Override
  public BObject decodeFromString(final String s)
    throws IOException
  {
    StringTokenizer t = new StringTokenizer(s, ";");

    final String id = t.nextToken();
    if (!t.hasMoreTokens())
    {
      return BTimeZone.getTimeZone(id);
    }

    final int utcOffset = Integer.parseInt(t.nextToken());
    final int dstDelta = Integer.parseInt(t.nextToken());

    String rule = t.nextToken();
    final DstRule startRule = (!rule.equals("null")) ? DstRule.decodeFromString(rule) : null;

    rule = t.nextToken();
    final DstRule endRule = (!rule.equals("null")) ? DstRule.decodeFromString(rule) : null;

    return make(id, id, id, id, id, utcOffset, dstDelta, startRule, endRule);
  }

  @Override
  public String toString(final Context cx)
  {
    int thisAdj,
        thisUtc;

    if (isJavaTimeZone)
    {
      thisUtc = jzone.getRawOffset();
      thisAdj = jzone.getDSTSavings();  
    }
    else
    {
      thisUtc = fixedUtcOffset;
      thisAdj = fixedDstDelta;
    }

    final long utcOffsetHours = thisUtc / BRelTime.MILLIS_IN_HOUR;

    long buffer = thisUtc % BRelTime.MILLIS_IN_HOUR;

    final long utcOffsetMinutes = Math.abs(buffer / BRelTime.MILLIS_IN_MINUTE);

    StringBuilder result = new StringBuilder(getId());
    result.append(" (");
    if (thisUtc >= 0)
    {
      result.append("+");
    }

    result.append(String.valueOf(utcOffsetHours));

    if (utcOffsetMinutes != 0)
    {
      result.append(":");
      if (utcOffsetMinutes < 10)
      {
        result.append("0");
      }
      result.append(String.valueOf(utcOffsetMinutes));
    }

    if (thisAdj != 0)
    {
      result.append("/");
      if ((thisUtc + thisAdj) >= 0)
      {
        result.append("+");
      }

      final long daylightOffsetHours = (thisUtc + thisAdj) / BRelTime.MILLIS_IN_HOUR;

      buffer = (thisUtc + thisAdj) % BRelTime.MILLIS_IN_HOUR;

      final long daylightOffsetMinutes = Math.abs(buffer / BRelTime.MILLIS_IN_MINUTE);

      result.append(String.valueOf(daylightOffsetHours));

      if (daylightOffsetMinutes != 0)
      {
        result.append(":");
        if (daylightOffsetMinutes < 10)
        {
          result.append("0");
        }
        result.append(String.valueOf(daylightOffsetMinutes));
      }
    }

    result.append(")");
    return result.toString();
  }

  /**
   * Will this time zone demonstrate backed by java time zone?
   */
  public boolean isJavaTimeZone()
  {
    return isJavaTimeZone;
  }  

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final BTimeZone UTC = BTimeZone.makeIgnoringCache("UTC", 0);
  public static final BTimeZone GMT = BTimeZone.makeIgnoringCache("GMT", 0);
  public static final BTimeZone NULL = BTimeZone.makeIgnoringCache("NULL", 0);
  public static final BTimeZone DEFAULT = UTC;

  private static TimeZone JAVA_UTC = null;
  private static BTimeZone localTimeZone;
  private static final Logger logger = Logger.getLogger("timezone");

  static
  {
    //Initialize the database
    TimeZoneDatabase.get();
    
    final String loadProp = AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("timezonedb.load", "true"));

    if (loadProp.equals("true"))
    {
      //Default to the system definition
      TimeZone defaultZone = TimeZone.getDefault();

      //Determine if the ID is supported by the Olson database
      boolean olsonSupport = false;
      for (String id : TimeZone.getAvailableIDs())
      {
        //break if zone is backed by olson
        if (defaultZone.getID().equals(id))
        {
          olsonSupport = true;
          break;
        }
      }

      if (!olsonSupport)
      {
        //The zone is a most likely a custom GMT Hours : Minutes offset,
        //We would very much prefer if we could find a zone in the Olson
        //db that behaves as this zone. If we can't then use the custom
        //zone, although it is known to cause problems.
        //
        //Start with UTC. Lots of zones behave like UTC, but culturally
        //its best to return a generic definition.
        if (defaultZone.hasSameRules(TimeZone.getTimeZone("UTC")))
        {
          defaultZone = TimeZone.getTimeZone("UTC");
        }
        else
        {
          TimeZone olsonEquivalent = null;
          for (String currentId : TimeZone.getAvailableIDs())
          {
            if (defaultZone.hasSameRules(TimeZone.getTimeZone(currentId)))
            {
              //Found a zone in the Olson db that behaves like the
              //custom zone, use that instead
              olsonEquivalent = TimeZone.getTimeZone(currentId);
              break;
            }
          }

          //If we found an olson equivalent, print a warning that we
          //are mapping the custom zone to it for the sake of compatibility
          //with other niagara environments.
          if (olsonEquivalent != null)
          {
            logger.warning("JVM Default Time Zone \"" + defaultZone + "\" not supported by TZDB, using TZDB equivalent  \"" + olsonEquivalent + "\"");
            defaultZone = olsonEquivalent;
          }
          else
          {
            //Could not find an Olson equivalent, forced to use the custom ID, this may cause problem,
            //but this should be extraordinarily rare.
            logger.warning("JVM Default Time Zone \"" + defaultZone + "\" not supported by TZDB");
          }
        }
      }

      //Populate the localTimeZone value
      try
      {
        //attempt to get a definition for the local time zone
        localTimeZone = BTimeZone.getTimeZone(defaultZone.getID());
      }
      catch(TimeZoneException e)
      {
        e.printStackTrace();

        logger.warning("Error occurred obtaining JVM Time Zone \"" + defaultZone.getID() + "\", approximating value");
        localTimeZone = BTimeZone.make(TimeZone.getDefault().getID(), TimeZone.getDefault().getOffset(System.currentTimeMillis()));
      }
    }
    else
    {
      logger.warning("timezonedb.load=false, Using UTC for local timezone.");
      localTimeZone = BTimeZone.makeIgnoringCache("UTC", 0);
    }
  }

  /**
   * Lazy load an instance of the Java SimpleTimeZone representing UTC time
   */
  public static TimeZone getJavaUTCInstance()
  {
    if (JAVA_UTC == null)
    {
      JAVA_UTC = TimeZone.getTimeZone("UTC");
    }
    
    return JAVA_UTC;
  }

  /**
   * Get the time zone that this VM is running in.
   */
  public static BTimeZone getLocal()
  {
    return localTimeZone;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  //Common field for both historical and simple time zones
  private String id;
  private String stringEncoding;
  
  //Internal java time zone that represents this BTimeZone in a Calendar object
  private TimeZone jzone;
  
  //DstRule representation of a Java TimeZone rule (NOTE: Not guaranteed to be historical!)
  private DstRule jzoneStartRule;
  private DstRule jzoneEndRule;
  
  //Is this BTimeZone driven directly from a Java TimeZone (N4+)
  private boolean isJavaTimeZone;
  
  //Is this BTimeZone manually created?
  private String  fixedStdName;
  private String  fixedStdShort;
  private String  fixedDstName;
  private String  fixedDstShort;
  private int     fixedUtcOffset;
  private int     fixedDstDelta;
  private DstRule fixedStartRule;
  private DstRule fixedEndRule;

  //Utility
  private int hashCode = -1;

  /**
   * A Comparator implementation that sorts BTimeZones by standard UTC offset.
   * If 2 BTimeZones have the same standard UTC offset, an alphabetical sort
   * is used to resolve the collision.
   *
   * @since Niagara 4.0
   */  
  public static final Comparator<BTimeZone> OFFSET_COMPARATOR =
    (t1, t2) ->
    {
      // sort by offset, then ID
      if (t1.getUtcOffset() == t2.getUtcOffset())
      {
        return t1.getId().compareTo(t2.getId());
      }
      else if (t1.getUtcOffset() < t2.getUtcOffset())
      {
        return -1;
      }
      else
      {
        return 1;
      }
    };

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final Type TYPE = Sys.loadType(BTimeZone.class);
  
  @Override
  public Type getType() { return TYPE; }

////////////////////////////////////////////////////////////////
// Inner classes follow
////////////////////////////////////////////////////////////////

  //Create an object from a custom BTimeZone definition that
  //can be used as a Java TimeZone in Calendar objects
  private class Support
    extends TimeZone
  {
    @Override
    public String getDisplayName(final boolean daylight, final int style, final Locale locale)
    {
      if (style == SHORT)
      {
        return BTimeZone.this.getShortDisplayName(daylight, null);
      }
      else
      {
        return BTimeZone.this.getDisplayName(daylight, null);
      }
    }

    @Override
    public String getID()
    {
      return BTimeZone.this.id;
    }

    @Override
    public int getRawOffset()
    {
      return BTimeZone.this.getUtcOffset();
    }

    @Override
    public boolean useDaylightTime()
    {
      return (BTimeZone.this.getDaylightAdjustment() != 0);
    }

    @Override
    public int getDSTSavings()
    {
      return BTimeZone.this.getDaylightAdjustment();
    }

    @Override
    public int getOffset(final long date)
    {
      int thisUtc = BTimeZone.this.fixedUtcOffset,
          thisAdj = BTimeZone.this.fixedDstDelta;

      if (thisAdj == 0) return thisUtc;

      if (inDaylightTime(new Date(date)))
      {
        return thisUtc + thisAdj;
      }
      else
      {
        return thisUtc;
      }
    }

    @Override
    public int getOffset(final int era,
                         final int year,
                         final int month,
                         final int day,
                         final int dayOfWeek,
                         final int milliseconds)
    {
      Calendar cal = new GregorianCalendar(getJavaUTCInstance());
      cal.clear();
      cal.set(GregorianCalendar.ERA, era);
      cal.set(year, month, day);

      int thisUtc = BTimeZone.this.fixedUtcOffset,
          thisAdj = BTimeZone.this.fixedDstDelta;
      long targetTime = cal.getTimeInMillis() + milliseconds - BTimeZone.this.fixedUtcOffset;

      if (thisAdj == 0) return thisUtc;

      if (inDaylightTime(new Date(targetTime)))
      {
        return thisUtc + thisAdj;
      }
      else
      {
        return thisUtc;
      }
    }

    @Override
    public boolean hasSameRules(final TimeZone other)
    {
      if (other instanceof Support)
      {
        ((Support)other).getBTimeZone().isEquivalent(getBTimeZone());
      }
      return false;
    }

    @Override
    public boolean inDaylightTime(final Date date)
    {
      // Use Calendar w/UTC to figure out the date's year
      // We assume that DST doesn't start/end within utcOffset of the
      ///new year, which would make the utc<-->wall years different

      if (BTimeZone.this.fixedStartRule == null ||
          BTimeZone.this.fixedEndRule == null) return false;

      Calendar cal = new GregorianCalendar(getJavaUTCInstance());
      cal.clear();
      cal.setTime(date);

      final DstRule startRule = BTimeZone.this.getDaylightStartRule();
      if (startRule == null)
      {
        return false;
      }
        
      final BAbsTime daylightStart = startRule.getTime(cal.get(Calendar.YEAR), BTimeZone.this, DstRule.START);
      if (daylightStart.isNull())
      {
        //dont bother getting the end, you know you're not in daylight!
        return false;
      }

      final DstRule endRule = BTimeZone.this.getDaylightEndRule();
      if (endRule == null)
      {
        return false;
      }        
        
      final BAbsTime daylightEnd = endRule.getTime(cal.get(Calendar.YEAR), BTimeZone.this, DstRule.END);
      if (daylightEnd.isNull())
      {
        //this dosn't really make sense but...return false so we don't npe
        return false;
      }

      if (daylightStart.getMillis() < daylightEnd.getMillis())
      {
        //if we are in a region where daylight time start comes before end in a year (northern hemisphere behavior)
        return ((date.getTime() >= daylightStart.getMillis()) &&
                (date.getTime() < daylightEnd.getMillis()));
      }
      else
      {
        //else, we must be in a region when daylight wraps around the new year.
        return ((date.getTime() >= daylightStart.getMillis()) ||
                (date.getTime() < daylightEnd.getMillis()));
      }
    }

    @Override
    public void setID(final String ID)
    {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setRawOffset(final int offsetMillis)
    {
      throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
      try
      {
        return BTimeZone.this.encodeToString();
      }
      catch(IOException e)
      {
        return null;
      }
    }
    
    private BTimeZone getBTimeZone()
    {
      return BTimeZone.this;
    }
  }
}
