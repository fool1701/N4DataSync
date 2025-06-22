/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

import com.tridium.sys.schema.Fw;
import com.tridium.util.*;

/**
 * BTime stores a time of day which is independent 
 * of any date in the past or future.
 *
 * @author    Brian Frank
 * @creation  21 Feb 01
 * @version   $Revision: 17$ $Date: 3/18/10 12:46:31 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BTime
  extends BSimple
  implements BITime, BIComparable
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance which maps to time of day
   * of the specified BAbsTime.
   */
  public static BTime make(BAbsTime absTime)
  {  
    return make(absTime.getHour(), absTime.getMinute(), 
                absTime.getSecond(), absTime.getMillisecond());
  }
  
  /**
   * Constructs an instance representing the number of milliseconds
   * from midnight of the last partial day of the reltime.
   */
  public static BTime make(BRelTime relTime)
  {  
    long ms = relTime.getMillis();
    ms = ms % BRelTime.MILLIS_IN_DAY;
    int hour = (int) (ms / BRelTime.MILLIS_IN_HOUR);
    ms = ms % BRelTime.MILLIS_IN_HOUR;
    int min = (int) (ms / BRelTime.MILLIS_IN_MINUTE);
    ms = ms % BRelTime.MILLIS_IN_MINUTE;
    int sec = (int) (ms / BRelTime.MILLIS_IN_SECOND);
    ms = ms % BRelTime.MILLIS_IN_SECOND;
    return make(hour, min, sec, (int)ms);
  }

  /**
   * Constructor for all fields defaulting millisecond to 0.
   */
  public static BTime make(int hour, int min, int sec)
  {  
    return make(hour, min, sec, 0);
  }
  
  /**
   * Constructor for all fields.
   */
  public static BTime make(int hour, int min, int sec, int ms)
  {  
    if (hour < 0 || hour > 23 ||
        min < 0 || min > 59 ||
        sec < 0 || sec > 59 ||
        ms < 0 || ms > 999)
      throw new IllegalArgumentException("Invalid field: " + hour + ":" + min + ":" + sec + "." + ms);

    if ((hour == 0) && (min == 0) && (sec == 0) && (ms == 0))
      return DEFAULT;
    
    return (BTime)(new BTime(hour, min, sec, ms).intern());
  }
  
  /**
   * Private constructor.
   */
  private BTime(int hour, int min, int sec, int ms)
  {  
    bits = (hour << 27) | (min << 21) | (sec << 15) | (ms);
  }  
    
////////////////////////////////////////////////////////////////
// Get Functions
////////////////////////////////////////////////////////////////

  /**
   * @return The hour: 0-23.
   */
  @Override
  public final int getHour()
  {
    return (bits >> 27) & 0x1F;
  }

  /**
   * @return The minute: 0-59.
   */
  @Override
  public final int getMinute()
  {
    return (bits >> 21) & 0x3F;
  }

  /**
   * @return The seconds: 0-59.
   */
  @Override
  public final int getSecond()
  {
    return (bits >> 15) & 0x3F;
  }

  /**
   * @return The millisecond: 0-999.
   */
  @Override
  public final int getMillisecond()
  {
    return bits & 0x7FFF;
  }
          
  /**
   * Milliseconds since the start of the day.
   */
  @Override
  public final long getTimeOfDayMillis()
  {
    long retern = getHour() * BRelTime.MILLIS_IN_HOUR;
    retern += getMinute() * BRelTime.MILLIS_IN_MINUTE;
    retern += getSecond() * BRelTime.MILLIS_IN_SECOND;
    retern += getMillisecond();
    return retern;
  }                        
  
  /**
   * Return a new time of day by adding the specified
   * duration.  If the result goes past midnight, then roll
   * into the next day.
   */
  public final BTime add(BRelTime duration)
  {
    return BTime.make(BRelTime.make(duration.getMillis() + getTimeOfDayMillis()));
  }

  /**
   * Get a formatted string for the time.  Use the following context 
   * facets to customize the format: SHOW_SECONDS, SHOW_MILLISECONDS, 
   * and SHOW_TIMEZONE.
   */
  @Override
  public String toString(Context context)
  {
    return TimeFormat.format(this, context);
  }

////////////////////////////////////////////////////////////////
// Comparsion
////////////////////////////////////////////////////////////////

  /**
   * Compare to another BTime.
   * @return a negative integer, zero, or a 
   *    positive integer as this object is less 
   *    than, equal to, or greater than the 
   *    specified object.  
   */
  @Override
  public int compareTo(Object obj)
  {
    if(!(obj instanceof BTime))
      return 0;
    long mine = bits & 0xFFFFFFFFFL;
    long his  = ((BTime)obj).bits & 0xFFFFFFFFFL;
    if (mine < his) return -1;
    else if (mine == his) return 0;
    else return 1;
  }
  
  /**
   * @return true if the specified time is before this time. 
   */
  public boolean isBefore(BTime x) 
  { 
    return compareTo(x) < 0; 
  }

  /**
   * @return true if the specified time is after this time. 
   */
  public boolean isAfter(BTime x) 
  { 
    return compareTo(x) > 0; 
  }
  
  /**
   * BTime hash code.
   */
  public int hashCode()
  {
   return bits;
  }

  /**
   * Equals.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BTime)
      return ((BTime)obj).bits == bits;
    return false;
  }
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * BTime is serialized using: 1 byte hour, 1 byte
   * min, 1 byte sec, 2 bytes milliseconds.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeByte(getHour());
    out.writeByte(getMinute());
    out.writeByte(getSecond());
    out.writeShort(getMillisecond());
  }
  
  /**
   * BTime is unserialized using: 1 byte hour, 1 byte
   * min, 1 byte sec, 2 bytes milliseconds.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readUnsignedByte(), 
                in.readUnsignedByte(), 
                in.readUnsignedByte(),
                in.readUnsignedShort());
  }

  /**
   * Write the simple in text format using the ISO 8601 
   * standard format of "hh:mm:ss.mmm".
   */
  @Override
  public String encodeToString()
  {
    StringBuilder s = new StringBuilder(32);
    
    int hour = getHour();
    if (hour < 10) s.append('0');
    s.append( hour ).append( ':' );

    int min = getMinute();
    if (min < 10) s.append('0');
    s.append( min ).append( ':' );
    
    int sec = getSecond();
    if (sec < 10) s.append('0');
    s.append( sec ).append( '.' );
    
    int millis = getMillisecond();
    if (millis < 10) s.append('0');
    if (millis < 100) s.append('0');
    s.append( millis );
        
    return s.toString();
  }

  /**
   * Read the simple from text format conforming to 
   * the ISO 8601 standard format of "hh:mm:ss.mmm".
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    char[] c = s.toCharArray();
    try
    {
      int i = 0;
                    
      int hour = (c[i++] - '0') * 10 +
                 (c[i++] - '0') * 1;

      if (c[i++] != ':') throw new Exception();                 
                 
      int min = (c[i++] - '0') * 10 +
                (c[i++] - '0') * 1;
                
      if (c[i++] != ':') throw new Exception();                 
      
      int sec = (c[i++] - '0') * 10 +
                (c[i++] - '0') * 1;

      if (c[i++] != '.') throw new Exception();                 
      
      int ms = (c[i++] - '0') * 100 +
               (c[i++] - '0') * 10 +
               (c[i++] - '0') * 1;
                      
      return make(hour, min, sec, ms);
    }
    catch(Exception e)
    {
      throw new IOException("Invalid BTime: " + s);
    }
  }
    
////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.SKIP_INTERN)
    { // Don't intern if milliseconds are greater than zero
      if (getMillisecond() != 0)
        return Boolean.TRUE;

      return null;
    }

    return super.fw(x, a, b, c, d);
  } 
  
////////////////////////////////////////////////////////////////
// Schema
////////////////////////////////////////////////////////////////

  /**
   * Get default BTime constant is midnight.
   */
  public static final BTime DEFAULT = new BTime(0, 0, 0, 0);

  public static final BTime MIDNIGHT = DEFAULT;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTime.class);
            
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /**
   * Bits:
   *  ------------------------------------------------
   *  Field    Num Bits   Range    Loc
   *  ------------------------------------------------
   *  Hour         5       0-23    27-31   
   *  Minutes      6       0-59    21-26
   *  Seconds      6       0-59    15-20   
   *  Seconds      15      0-999   0-14   
   * ------------------------------------------------
   */
  private int bits;

}

