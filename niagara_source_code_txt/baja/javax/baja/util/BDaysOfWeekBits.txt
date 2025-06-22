/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BDaysOfWeekBits is an bit string containing one bit
 * for each day of the week.  Uses include logging and
 * alarm routing applications to indicate what days
 * the logging should take place or when alarms should
 * be routed. 
 *
 * @author    Dan Giorgis
 * @creation  19 Feb 00
 * @version   $Revision: 15$ $Date: 1/25/08 4:04:07 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BDaysOfWeekBits
  extends BBitString
{

////////////////////////////////////////////////////////////////
// Factory methods
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance in which all days are 
   * set to the given value.
   */
  public static BDaysOfWeekBits make(boolean v)
  {                                            
    if (v)
      return ALL;
    else
      return EMPTY;
  }

  /**
   *  Construct an instance using the given bits
   */
  public static BDaysOfWeekBits make(int bits)
  {                           
    if (bits == ALL.bits) return ALL;
    if (bits == EMPTY.bits) return EMPTY;
    return (BDaysOfWeekBits)(new BDaysOfWeekBits(bits).intern());
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance using the given bits
   */
  private BDaysOfWeekBits(int bits)
  {
    this.bits = bits;
  }
    
////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Is specified day of week active.
   */
  public boolean includes(BAbsTime absTime)
  {
    return includes(absTime.getWeekday());  
  }
  
  /**
   * Get the bits.
   */
  public int getBits()
  {
    return bits;
  }
  
  /**
   * Is specified day of week active.
   */
  public boolean includes(BWeekday weekday)
  {
    return ((bits & (0x1 << weekday.getOrdinal())) != 0);
  }

////////////////////////////////////////////////////////////////
// BBitString
////////////////////////////////////////////////////////////////

  /**
   * Return if the bit specified by the given ordinal is set.
   */
  @Override
  public boolean getBit(int ordinal)
  {
    return (bits & ordinal) != 0;
  }

  /**
   * Return if the bit specified by the given tag is set.
   */
  @Override
  public boolean getBit(String tag)
  {
    return getBit(tagToOrdinal(tag));
  }

  /**
   * Get an array enumerating the list of all known
   * ordinal values of this bitstring instance.
   */
  @Override
  public int[] getOrdinals()
  {
    return support.getOrdinals();
  }
  
  /**
   * Is the specified ordinal value included in this
   * bitstring's range of valid ordinals.
   */
  @Override
  public boolean isOrdinal(int ordinal)
  {
    return support.isOrdinal(ordinal);
  }
  
  /**
   * Get the tag identifier for an ordinal value.
   */
  @Override
  public String getTag(int ordinal)
  {
    return support.getTag(ordinal);
  }

  /**
   * Get the user readable tag for an ordinal value.
   */
  @Override
  public String getDisplayTag(int ordinal, Context cx)
  {
    return support.getDisplayTag(ordinal, cx);
  }
  
  /**
   * Get the BBitString instance which maps to the 
   * specified set of ordinal values.
   */
  @Override
  public BBitString getInstance(int[] ordinals)
  {
    int mask = 0;
    for(int i=0; i<ordinals.length; ++i) mask |= ordinals[i];
    return make(mask);
  }
  
  /**
   * Return true if the specified tag is contained by the range.
   */
  @Override
  public boolean isTag(String tag)
  {
    return support.isTag(tag);
  }
  
  /**
   * Get the ordinal associated with the specified tag.
   */
  @Override
  public int tagToOrdinal(String tag)
  {
    return support.tagToOrdinal(tag);
  }
  
  /**
   * Return if true if no bits set.
   */
  @Override
  public boolean isEmpty()
  {
    return bits == 0;
  }

  /**
   * Are all bits set?
   */
  public boolean isEveryDay()
  {
    return bits == ALL.bits;
  }

  /**
   * The empty tag is "none".
   */
  @Override
  public String getEmptyTag()
  {
    return "none";
  }

////////////////////////////////////////////////////////////////
// Comparsion
////////////////////////////////////////////////////////////////
 
  /**
   * BDaysOfWeekBits uses its bits as the hash code.
   */
  public int hashCode()
  {
    return bits;
  }
  
  /**
   * Equality is based on bitmask equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BDaysOfWeekBits)
      return ((BDaysOfWeekBits)obj).bits == bits;
    return false;
  }


////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * BDaysOfWeekBits is serialized using writeInt
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(bits);
  }
  
  /**
   * BDaysOfWeekBits is unserialized using readInt()
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readInt() );
  }

  /**
   * Text format is the bit mask in hex.
   */
  @Override
  public String encodeToString()
  {
    return Integer.toHexString(bits);
  }

  /**
   * Read the bit mask as hex.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return make( Integer.parseInt(s, 16) );
    }
    catch(Exception e)
    {
      throw new IOException("Invalid bits: " + s);
    }
  }

////////////////////////////////////////////////////////////////
// Formatting
////////////////////////////////////////////////////////////////
        
  @Override
  public String toString(Context cx)
  {
    if (bits == 0) return "{}";
    StringBuilder s = new StringBuilder();

    s.append('{');

    s.append(
      Stream.of(
        BWeekday.sunday,
        BWeekday.monday,
        BWeekday.tuesday,
        BWeekday.wednesday,
        BWeekday.thursday,
        BWeekday.friday,
        BWeekday.saturday
      )
        .filter(day -> includes(day))
        .map(day -> day.getShortDisplayTag(cx))
        .collect(Collectors.joining(" "))
    );

    s.append('}');

    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  

  public static final int SUNDAY        = 0x0001;
  public static final int MONDAY        = 0x0002;
  public static final int TUESDAY       = 0x0004;
  public static final int WEDNESDAY     = 0x0008;
  public static final int THURSDAY      = 0x0010;
  public static final int FRIDAY        = 0x0020;
  public static final int SATURDAY      = 0x0040;

  public static final BDaysOfWeekBits sunday    = (BDaysOfWeekBits)(new BDaysOfWeekBits(SUNDAY).intern());  
  public static final BDaysOfWeekBits monday    = (BDaysOfWeekBits)(new BDaysOfWeekBits(MONDAY).intern());  
  public static final BDaysOfWeekBits tuesday   = (BDaysOfWeekBits)(new BDaysOfWeekBits(TUESDAY).intern());  
  public static final BDaysOfWeekBits wednesday = (BDaysOfWeekBits)(new BDaysOfWeekBits(WEDNESDAY).intern());  
  public static final BDaysOfWeekBits thursday  = (BDaysOfWeekBits)(new BDaysOfWeekBits(THURSDAY).intern());  
  public static final BDaysOfWeekBits friday    = (BDaysOfWeekBits)(new BDaysOfWeekBits(FRIDAY).intern());  
  public static final BDaysOfWeekBits saturday  = (BDaysOfWeekBits)(new BDaysOfWeekBits(SATURDAY).intern());  
  
  /** DEFAULT has all bits set */
  public static final BDaysOfWeekBits DEFAULT = new BDaysOfWeekBits(SUNDAY | MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY);
  /** EMPTY has all bits clear */
  public static final BDaysOfWeekBits EMPTY   = new BDaysOfWeekBits(0);
  /** ALL is DEFAULT */
  public static final BDaysOfWeekBits ALL     = DEFAULT;

  private static Support support = new Support(DEFAULT);
  static
  {
    support.add(SUNDAY,    "sunday");
    support.add(MONDAY,    "monday");
    support.add(TUESDAY,   "tuesday");
    support.add(WEDNESDAY, "wednesday");
    support.add(THURSDAY,  "thursday");
    support.add(FRIDAY,    "friday");
    support.add(SATURDAY,  "saturday");
  }

////////////////////////////////////////////////////////////////
// Type 
////////////////////////////////////////////////////////////////  

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDaysOfWeekBits.class);
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private int bits;

}

