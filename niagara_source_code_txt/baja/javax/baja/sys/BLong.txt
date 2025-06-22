/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;
import java.text.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.units.*;

/**
 * The BLong stores a Java long primitive.
 *
 * @author    Brian Frank
 * @creation  25 Apr 03
 * @version   $Revision: 12$ $Date: 1/4/10 6:27:04 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BLong
  extends BNumber
{

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Factory method.
   */
  public static BLong make(long value)
  {
    if (value == 0) return ZERO;
    if (value == Long.MIN_VALUE) return MIN;
    if (value == Long.MAX_VALUE) return MAX;
    return new BLong(value);
  }

  /**
   * Factory method.
   */
  public static BLong make(String value)
  {
    return make(decode(value));
  }

  /**
   * Private constructor.
   */
  private BLong(long value)
  {
    this.value = value;
  }

////////////////////////////////////////////////////////////////
// BNumber
////////////////////////////////////////////////////////////////

  /**
   * @return the long value.
   */
  @Override
  public long getLong()
  {
    return value;
  }

  /**
   * @return the long cast to a int.
   */
  @Override
  public int getInt()
  {
    return (int)value;
  }

  /**
   * @return the long cast to a float.
   */
  @Override
  public float getFloat()
  {
    return value;
  }

  /**
   * @return the long cast to a double.
   */
  @Override
  public double getDouble()
  {
    return value;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BLong hash code is {@code (int)(value^(value()&gt;&gt;&gt;32))}.
   */
  public int hashCode()
  {
   return (int)(value ^ (value >>> 32));
  }

  /**
   * BLong equality is based on long value equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLong)
      return ((BLong)obj).value == value;
    return false;
  }

  /**
   * Compares this object with the specified object for
   * order. Returns a negative integer, zero, or a positive
   * integer as this object is less than, equal to, or greater
   * than the specified object.
   */
  @Override
  public int compareTo(Object obj)
  {
    long a = value;
    long b = ((BNumber)obj).getLong();
    if (a == b) return 0;
    if (a < b) return -1;
    else return 1;
  }

  /**
   * Route to {@code BLong.toString(long, Context)}.
   */
  @Override
  public String toString(Context context)
  {
    return toString(value, context);
  }

  /**
   * BLong is serialized using writeLong().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeLong(value);
  }

  /**
   *  BLong is unserialized using readLong().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readLong() );
  }

  /**
   * Route to {@code BLong.encode(long)}.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    return encode(value);
  }

  /**
   * Route to {@code BLong.decode(String)}.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return make(s);
    }
    catch(RuntimeException e)
    {
      throw new IOException("Invalid long: " + s);
    }
  }

////////////////////////////////////////////////////////////////
// Primitive Encoding
////////////////////////////////////////////////////////////////

  /**
   * Parse the text format directly to a primitive long.  The
   * special string "min" maps to {@code Long.MIN_VALUE}
   * and "max" to {@code Long.MAX_VALUE}.
   */
  public static long decode(String s)
  {
    if (s.equals("min")) return Long.MIN_VALUE;
    if (s.equals("max")) return Long.MAX_VALUE;
    return Long.parseLong(s);
  }

  /**
   * Encode the primitive long into a string.  If the long is
   * {@code Long.MIN_VALUE} then return "min" or if
   * {@code Long.MAX_VALUE} return "max".
   */
  public static String encode(long x)
  {
    if (x == Long.MIN_VALUE) return "min";
    if (x == Long.MAX_VALUE) return "max";
    return String.valueOf(x);
  }

////////////////////////////////////////////////////////////////
// Format
////////////////////////////////////////////////////////////////

  /**
   * Format the long value as a String.
   */
  public static String toString(long value, Context cx)
  {
    int radix = 10;
    BUnit units = null;
    int convert = com.tridium.sys.Nre.unitConversion;
    boolean showUnits = true;
    boolean showSeparators = false;
    boolean forceSign = false;

    if (cx != null)
    {
      BNumber radixFacet = (BNumber)cx.getFacet(BFacets.RADIX);
      if (radixFacet != null)
        radix = radixFacet.getInt();

      units = (BUnit)cx.getFacet(BFacets.UNITS);
      if (units != null && units.isNull()) units = null;

      convert = cx.getFacets().geti(BFacets.UNIT_CONVERSION, convert);
      showUnits = cx.getFacets().getb(BFacets.SHOW_UNITS, showUnits);
      showSeparators = cx.getFacets().getb(BFacets.SHOW_SEPARATORS, showSeparators);
      forceSign = cx.getFacets().getb(BFacets.FORCE_SIGN, forceSign);
    }

    if (convert != 0 && units != null)
    {
      BUnitConversion c = BUnitConversion.make(convert);
      BUnit desired = c.getDesiredUnit(units);
      if (desired != units)
      {
        value = (long)(units.convertTo(desired, ((double)value)));
        units = desired;
      }
    }

    String s = "";

    if (showUnits && (units != null) && units.getIsPrefix()) s += units.getSymbol() + ' ';

    if (value == Long.MIN_VALUE) s += "min";
    else if (value == Long.MAX_VALUE) s += "max";
      // Check a few special radix cases
    else if (radix == 2) s += Long.toBinaryString(value);
    else if (radix == 8) s += Long.toOctalString(value);
    else if (radix == 16) s += Long.toHexString(value);
    else if (radix != 10) s += Long.toString(value, radix);
    else if (showSeparators) {
      s += BDouble.addForcedSign(forceSign, (double) value);
      s += BDouble.getFormatter(0, true, false, cx).format(value);
    }
    else {
      s += BDouble.addForcedSign(forceSign, (double) value);
      s += Long.toString(value);
    }

    if (showUnits && (units != null) && !units.getIsPrefix()) s += ' ' + units.getSymbol();
    return s;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final BLong ZERO  = new BLong(0);

  /** Constant for Long.MIN_VALUE */
  public static final BLong MIN = new BLong(Long.MIN_VALUE);

  /** Constant for Long.MAX_VALUE */
  public static final BLong MAX = new BLong(Long.MAX_VALUE);

  /** The default constant is 0. */
  public static final BLong DEFAULT = ZERO;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLong.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private long value;

}
