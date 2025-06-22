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

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BIComparable;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BTime;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBacnetTime represents a date value in a Bacnet property.
 *
 * @author Craig Gemmill
 * @version $Revision: 6$ $Date: 11/8/01 9:04:51 AM$
 * @creation 24 Sep 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBacnetTime
  extends BSimple
  implements BIComparable
{
  /**
   * Private constructor.
   */
  private BBacnetTime(int hour, int minute, int second, int hundredth)
  {
    this.hour = (byte)hour;
    this.minute = (byte)minute;
    this.second = (byte)second;
    this.hundredth = (byte)hundredth;
    getHashCode();
  }

  /**
   * Factory method for all unspecified.
   */
  public static BBacnetTime make()
  {
    return new BBacnetTime(UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED);
  }

  /**
   * Factory method.
   * Note that BACnet uses unsigned bytes, so 255 for BACnet corresponds
   * to -1 for Java's signed byte data type.  This factory method will
   * accept either 255 or -1 for 'unspecified'.
   *
   * @param hour      0-23, or 255/-1 for unspecified
   * @param minute    0-59, or 255/-1 for unspecified
   * @param second    0-59, or 255/-1 for unspecified
   * @param hundredth 0-99, or 255/-1 for unspecified
   */
  public static BBacnetTime make(int hour, int minute, int second, int hundredth)
  {
    if (((hour < -1) || (hour > 23)) && (hour != 255))
      throw new IllegalArgumentException("BBacnetTime: invalid hour:" + hour);
    if (((minute < -1) || (minute > 59)) && (minute != 255))
      throw new IllegalArgumentException("BBacnetTime: invalid minute:" + minute);
    if (((second < -1) || (second > 59)) && (second != 255))
      throw new IllegalArgumentException("BBacnetTime: invalid second:" + second);
    if (((hundredth < -1) || (hundredth > 99)) && (hundredth != 255))
      throw new IllegalArgumentException("BBacnetTime: invalid hundredth:" + hundredth);
    return new BBacnetTime(hour, minute, second, hundredth);
  }

  /**
   * Factory method from BAbsTime.
   *
   * @param bt BAbsTime.
   */
  public static BBacnetTime make(BAbsTime bt)
  {
    int h = bt.getHour();
    int n = bt.getMinute();
    int s = bt.getSecond();
    int u = bt.getMillisecond() / 10;         // Bacnet uses hundredths
    return new BBacnetTime(h, n, s, u);
  }

  /**
   * Factory method from BAbsTime.
   *
   * @param bt BAbsTime.
   */
  public static BBacnetTime make(BTime bt)
  {
    int h = bt.getHour();
    int n = bt.getMinute();
    int s = bt.getSecond();
    int u = bt.getMillisecond() / 10;         // Bacnet uses hundredths
    return new BBacnetTime(h, n, s, u);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BBacnetTime equality is based on all values being equal.
   * NOTE: UNSPECIFIED values match ONLY other UNSPECIFIED values!
   * This is NOT the same as Bacnet equivalence!  For Bacnet time equivalence,
   * use the timeEquals() method.
   *
   * @param obj the comparison object
   * @return true if the object is a <code>BBacnetTime</code> with all values equal to this one.
   * @see timeEquals
   */
  public boolean equals(Object obj)
  {
    if (obj == null) return false;
    if (obj instanceof BBacnetTime)
    {
      BBacnetTime d = (BBacnetTime)obj;
      return ((hour == d.hour) && (minute == d.minute)
        && (second == d.second) && (hundredth == d.hundredth));
    }
    return false;
  }

  /**
   * BBacnetTime hashcode is a concatenation of all fields.
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
    StringBuilder sb = new StringBuilder();
    if (hour == UNSPECIFIED)
      sb.append("**:");
    else
      sb.append((hour < 10) ? "0" + hour : String.valueOf(hour)).append(':');

    if (minute == UNSPECIFIED)
      sb.append("**:");
    else
      sb.append((minute < 10) ? "0" + minute : String.valueOf(minute)).append(':');

    if (second == UNSPECIFIED)
      sb.append("**.");
    else
      sb.append((second < 10) ? "0" + second : String.valueOf(second)).append('.');

    if (hundredth == UNSPECIFIED)
      sb.append("**");
    else
      sb.append((hundredth < 10) ? "0" + hundredth : String.valueOf(hundredth));

    return sb.toString();
  }

  /**
   * BBacnetTime is serialized using calls to writeByte().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeByte(hour);
    out.writeByte(minute);
    out.writeByte(second);
    out.writeByte(hundredth);
  }

  /**
   * BBacnetTime is unserialized using calls to readByte().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    byte h = in.readByte();
    byte m = in.readByte();
    byte s = in.readByte();
    byte u = in.readByte();
    return new BBacnetTime(h, m, s, u);
  }

  /**
   * Write the primitive in text format.
   */
  public String encodeToString()
    throws IOException
  {
    return toString(null);
  }

  /**
   * Read the primitive from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      StringTokenizer st = new StringTokenizer(s, ":.");
      int h = UNSPECIFIED;
      int m = UNSPECIFIED;
      int e = UNSPECIFIED;
      int u = UNSPECIFIED;
      String hs = st.nextToken();
      h = (hs.indexOf("*") < 0) ? Integer.parseInt(hs) : UNSPECIFIED;
      if (st.hasMoreTokens())
      {
        String ms = st.nextToken();
        m = (ms.indexOf("*") < 0) ? Integer.parseInt(ms) : UNSPECIFIED;
        if (st.hasMoreTokens())
        {
          String es = st.nextToken();
          e = (es.indexOf("*") < 0) ? Integer.parseInt(es) : UNSPECIFIED;
          if (st.hasMoreTokens())
          {
            String us = st.nextToken();
            u = (us.indexOf("*") < 0) ? Integer.parseInt(us) : UNSPECIFIED;
          }
        }
      }
      return new BBacnetTime(h, m, e, u);
    }
    catch (Exception e)
    {
      throw new IOException("Error decoding BBacnetTime:" + s);
    }
  }

////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Get the hour.
   *
   * @return the hour represented by this BBacnetTime,
   * or -1 if unspecified.
   */
  public int getHour()
  {
    return (int)hour;
  }

  /**
   * Get the minute.
   *
   * @return the minute represented by this BBacnetTime,
   * or -1 if unspecified.
   */
  public int getMinute()
  {
    return (int)minute;
  }

  /**
   * Get the second.
   *
   * @return the second represented by this BBacnetTime,
   * or -1 if unspecified.
   */
  public int getSecond()
  {
    return (int)second;
  }

  /**
   * Get the hundredth.
   *
   * @return the hundredth represented by this BBacnetTime,
   * or -1 if unspecified.
   */
  public int getHundredth()
  {
    return (int)hundredth;
  }

  /**
   * Is the hour unspecified?
   *
   * @return true if the hour is unspecified.
   */
  public boolean isHourUnspecified()
  {
    return (hour == UNSPECIFIED);
  }

  /**
   * Is the minute unspecified?
   *
   * @return true if the minute is unspecified.
   */
  public boolean isMinuteUnspecified()
  {
    return (minute == UNSPECIFIED);
  }

  /**
   * Is the second unspecified?
   *
   * @return true if the second is unspecified.
   */
  public boolean isSecondUnspecified()
  {
    return (second == UNSPECIFIED);
  }

  /**
   * Is the hundredth unspecified?
   *
   * @return true if the hundredth is unspecified.
   */
  public boolean isHundredthUnspecified()
  {
    return (hundredth == UNSPECIFIED);
  }

  /**
   * Is any field unspecified?
   *
   * @return true if any field is unspecified.
   */
  public boolean isAnyUnspecified()
  {
    return ((hour == UNSPECIFIED) || (minute == UNSPECIFIED)
      || (second == UNSPECIFIED) || (hundredth == UNSPECIFIED));
  }

////////////////////////////////////////////////////////////////
// Comparsion
////////////////////////////////////////////////////////////////

  /**
   * BBacnetTime equivalence is based on all values being equal,
   * or unspecified.
   * <B>NOTE</B>: This is the method to determine time equivalence according
   * to BACnet, <B>not</B> the equals() method, which requires UNSPECIFIED values
   * to match <B>only</B> with UNSPECIFIED values.
   *
   * @param obj the comparison object.
   * @see equals
   */
  public boolean timeEquals(Object obj)
  {
    return compareTo(obj) == 0;
  }

  /**
   * Compare to another BBacnetTime.
   *
   * @return a negative integer, zero, or a
   * positive integer as this object is less
   * than, equal to, or greater than the
   * specified object.
   */
  public int compareTo(Object obj)
  {
    if (obj == null) throw new ClassCastException();
    if (((BObject)obj).getType() == BBacnetTime.TYPE)
    {
      BBacnetTime other = (BBacnetTime)obj;
      if (!other.isHourUnspecified() && !isHourUnspecified())
      {
        if (hour < other.hour) return -1;
        if (hour > other.hour) return 1;
      }
      if (!other.isMinuteUnspecified() && !isMinuteUnspecified())
      {
        if (minute < other.minute) return -1;
        if (minute > other.minute) return 1;
      }
      if (!other.isSecondUnspecified() && !isSecondUnspecified())
      {
        if (second < other.second) return -1;
        if (second > other.second) return 1;
      }
      if (!other.isHundredthUnspecified() && !isHundredthUnspecified())
      {
        if (hundredth < other.hundredth) return -1;
        if (hundredth > other.hundredth) return 1;
      }
      return 0;
    }
    else if (((BObject)obj).getType() == BTime.TYPE)
    {
      BTime other = (BTime)obj;
      if (!isHourUnspecified())
      {
        if (hour < other.getHour()) return -1;
        if (hour > other.getHour()) return 1;
      }
      if (!isMinuteUnspecified())
      {
        if (minute < other.getMinute()) return -1;
        if (minute > other.getMinute()) return 1;
      }
      if (!isSecondUnspecified())
      {
        if (second < other.getSecond()) return -1;
        if (second > other.getSecond()) return 1;
      }
      if (!isHundredthUnspecified())
      {
        if (hundredth < (other.getMillisecond() / 10)) return -1;
        if (hundredth > (other.getMillisecond() / 10)) return 1;
      }
      return 0;
    }
    throw new IllegalArgumentException(obj.toString());
  }

  /**
   * @return true if the specified time is before this time.
   */
  public boolean isBefore(Object x)
  {
    return compareTo(x) < 0;
  }

  /**
   * @return true if the specified time is after this time.
   */
  public boolean isAfter(Object x)
  {
    return compareTo(x) > 0;
  }

  /**
   * @return true if the specified time is not before this time.
   */
  public boolean isNotBefore(Object x)
  {
    return compareTo(x) >= 0;
  }

  /**
   * @return true if the specified time is not after this time.
   */
  public boolean isNotAfter(Object x)
  {
    return compareTo(x) <= 0;
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Get the equivalent BTime.
   *
   * @return a BTime representing this BBacnetTime's value.
   */
  public BTime toBTime()
  {
    return getBTime(this, true);
  }

  /**
   * Read the time values from the
   * given String and return a new BBacnetTime.
   *
   * @param s the input string.
   * @return a BBacnetTime read from the string.
   */
  public static BBacnetTime fromString(String s)
  {
    try
    {
      return (BBacnetTime)DEFAULT.decodeFromString(s);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "BBacnetTime.fromString('" + s + "'): error parsing string!!", e);
      return DEFAULT;
    }
  }

  /**
   * Get the equivalent BTime.
   *
   * @param t the BBacnetTime.
   * @return a BTime representing the same time as the BBacnetTime.
   */
  public static BTime getBTime(BBacnetTime t)
  {
    return getBTime(t, true);
  }

  /**
   * Get a BTime object from a BBacnetTime.
   *
   * @param t    the BBacnetTime.
   * @param zero if true, UNSPECIFIED values will be coded as 0;
   *             if false, UNSPECIFIED values will be coded as the max value.
   * @return a BTime representing the same time as the BBacnetTime.
   */
  public static BTime getBTime(BBacnetTime t, boolean zero)
  {
    return BTime.make(t.isHourUnspecified() ? (zero ? 0 : 23) : t.getHour(),
      t.isMinuteUnspecified() ? (zero ? 0 : 59) : t.getMinute(),
      t.isSecondUnspecified() ? (zero ? 0 : 59) : t.getSecond(),
      t.isHundredthUnspecified() ? (zero ? 0 : 999) : (t.getHundredth() * 10));
  }

  private void getHashCode()
  {
    hashCode = (hour << 24) | (minute << 16) | (second << 8) | hundredth;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final byte UNSPECIFIED = -1;

  /**
   * The length of the string returned by toFacetString().
   */
  public static final int TEXT_LENGTH = 11;

  public static final BBacnetTime DEFAULT = new BBacnetTime(UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTime.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final byte hour;
  private final byte minute;
  private final byte second;
  private final byte hundredth;
  private int hashCode;

  private static final Logger logger = Logger.getLogger("bacnet.datatypes");
}