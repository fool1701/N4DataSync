/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BBacnetUnsigned represents an unsigned value in a Bacnet property.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 22 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NoSlotomatic
public final class BBacnetUnsigned
//  extends BNumber
  extends BSimple
  implements BIComparable
{
  /**
   * Constructor.
   *
   * @param value the unsigned value
   */
  public BBacnetUnsigned(long value)
  {
    if (value > MAX_UNSIGNED_VALUE)
      throw new IllegalArgumentException("" + value);
    this.value = value;
  }

  /**
   * Factory method.
   */
  public static BBacnetUnsigned make(long value)
  {
    switch ((int)value)
    {
      case 0:
        return ZERO;
      case 1:
        return ONE;
      case 2:
        return TWO;
    }
    return new BBacnetUnsigned(value);
  }


////////////////////////////////////////////////////////////////
//  BIComparable
////////////////////////////////////////////////////////////////

  /**
   * Compares this object with the specified object for
   * order. Returns a negative integer, zero, or a positive
   * integer as this object is less than, equal to, or greater
   * than the specified object.
   */
  public int compareTo(Object obj)
  {
    int a = (int)value;
    int b = ((BNumber)obj).getInt();
    if (a == b) return 0;
    if (a < b) return -1;
    else return 1;
  }


////////////////////////////////////////////////////////////////
//  BSimple
////////////////////////////////////////////////////////////////

  /**
   * BBacnetUnsigned equality is based on the value.
   */
  public boolean equals(Object obj)
  {
    if (obj == null) return false;
    if (obj instanceof BBacnetUnsigned)
      return ((BBacnetUnsigned)obj).value == value;
    return false;
  }

  /**
   * To String.
   */
  public String toString(Context context)
  {
    if (context != null)
    {
      BEnumRange r = (BEnumRange)context.getFacet(BFacets.RANGE);
      if (r != null)
      {
        return SlotPath.unescape(r.getTag((int)value));
      }
    }
    return String.valueOf(value);
  }

  /**
   * Hash code.
   * The hash code for a BBacnetUnsigned is its unique id.
   */
  public int hashCode()
  {
    return (int)value;
  }

  /**
   * BBacnetUnsigned is serialized using writeLong().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeLong(value);
  }

  /**
   * BBacnetUnsigned is unserialized using readLong().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readLong());
  }

  /**
   * Write the simple in text format.
   */
  public String encodeToString()
    throws IOException
  {
    return String.valueOf(value);
  }

  /**
   * Read the simple from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return make(Long.parseLong(s));
    }
    catch (Exception e)
    {
      throw new IOException("Invalid unsigned: " + s);
    }
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * @return the integer value.
   */
  public int getInt()
  {
    return (int)value;
  }

  /**
   * @return the unsigned value as a long.
   */
  public long getLong()
  {
    return value;
  }

  /**
   * @return the unsigned value as a long.
   */
  public long getUnsigned()
  {
    return value;
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final BBacnetUnsigned ZERO = new BBacnetUnsigned(0);
  private static final BBacnetUnsigned ONE = new BBacnetUnsigned(1);
  private static final BBacnetUnsigned TWO = new BBacnetUnsigned(2);

  public static final long MAX_UNSIGNED_VALUE = 4294967295L;
  public static final long MIN_UNSIGNED_VALUE = 0L;
  public static final long MAX_UNSIGNED16_VALUE = 65535;

  /**
   * The default unsigned is 0.
   */
  public static final BBacnetUnsigned DEFAULT = ZERO;
  public static final BBacnetUnsigned MAX_UNSIGNED = new BBacnetUnsigned(MAX_UNSIGNED_VALUE);

  public Type getType()
  {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BBacnetUnsigned.class);


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private long value;
}