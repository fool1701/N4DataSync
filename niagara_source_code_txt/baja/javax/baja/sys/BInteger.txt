/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;
import java.text.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.units.*;

/**
 * The BInteger is the wrapper class for Java primitive
 * int objects.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 36$ $Date: 1/4/10 6:27:04 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BInteger
  extends BNumber
{

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Factory method.
   */
  public static BInteger make(int value)
  {
    switch(value)
    {
      case 0: return ZERO;
      case 1: return ONE;
      case 2: return TWO;
      case 3: return THREE;
      case 4: return FOUR;
      case 5: return FIVE;
      case Integer.MIN_VALUE: return MIN;
      case Integer.MAX_VALUE: return MAX;
    }
    return new BInteger(value);
  }

  /**
   * Factory method.
   */
  public static BInteger make(String value)
  {
    return make(decode(value));
  }

  /**
   * Private constructor.
   */
  private BInteger(int value)
  {
    this.value = value;
  }

////////////////////////////////////////////////////////////////
// BNumber
////////////////////////////////////////////////////////////////

  /**
   * @return the integer value.
   */
  @Override
  public int getInt()
  {
    return value;
  }

  /**
   * @return the integer value as a long.
   */
  @Override
  public long getLong()
  {
    return value;
  }

  /**
   * @return the integer value cast to a float.
   */
  @Override
  public float getFloat()
  {
    return value;
  }

  /**
   * @return the integer value cast to a double.
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
   * BInteger hash code is it integer value.
   */
  public int hashCode()
  {
    return value;
  }

  /**
   * BInteger equality is based on integer value equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BInteger)
      return ((BInteger)obj).value == value;
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
    int a = value;
    int b = ((BNumber)obj).getInt();
    if (a == b) return 0;
    if (a < b) return -1;
    else return 1;
  }

  /**
   * Route to {@code BInteger.toString(long, Context)}.
   */
  @Override
  public String toString(Context context)
  {
    return toString(value, context);
  }

  /**
   * BInteger is serialized using writeInt().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(value);
  }

  /**
   *  BInteger is unserialized using readInt().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readInt() );
  }

  /**
   * Route to {@code BInteger.encode(int)}.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    return encode(value);
  }

  /**
   * Route to {@code BInteger.decode(String)}.
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
      throw new IOException("Invalid integer: " + s);
    }
  }

////////////////////////////////////////////////////////////////
// Primitive Encoding
////////////////////////////////////////////////////////////////

  /**
   * Parse the text format directly to a primitive int.  The
   * special string "min" maps to {@code Integer.MIN_VALUE}
   * and "max" to {@code Integer.MAX_VALUE}.
   */
  public static int decode(String s)
  {
    if (s.equals("min")) return Integer.MIN_VALUE;
    if (s.equals("max")) return Integer.MAX_VALUE;
    return Integer.parseInt(s);
  }

  /**
   * Encode the primitive int into a string.  If the int is
   * {@code Integer.MIN_VALUE} then return "min" or if
   * {@code Integer.MAX_VALUE} return "max".
   */
  public static String encode(int i)
  {
    if (i == Integer.MIN_VALUE) return "min";
    if (i == Integer.MAX_VALUE) return "max";
    return String.valueOf(i);
  }

////////////////////////////////////////////////////////////////
// Format
////////////////////////////////////////////////////////////////

  /**
   * Format the int value as a String.
   */
  public static String toString(int value, Context cx)
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
        value = (int)(units.convertTo(desired, ((double)value)));
        units = desired;
      }
    }

    String s = "";

    if (showUnits && (units != null) && units.getIsPrefix()) s += units.getSymbol() + ' ';

    if (value == Integer.MIN_VALUE) s += "min";
    else if (value == Integer.MAX_VALUE) s += "max";
    // Check a few special radix cases
    else if (radix == 2) s += Integer.toBinaryString(value);
    else if (radix == 8) s += Integer.toOctalString(value);
    else if (radix == 16) s += Integer.toHexString(value);
    else if (radix != 10) s += Integer.toString(value, radix);
    else if (showSeparators) {
      s += BDouble.addForcedSign(forceSign, (double) value);
      s += BDouble.getFormatter(0, true, false, cx).format(value);
    }
    else {
      s += BDouble.addForcedSign(forceSign, (double) value);
      s += Integer.toString(value);
    }

    if (showUnits && (units != null) && !units.getIsPrefix()) s += ' ' + units.getSymbol();
    return s;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final BInteger ZERO  = new BInteger(0);
  private static final BInteger ONE   = new BInteger(1);
  private static final BInteger TWO   = new BInteger(2);
  private static final BInteger THREE = new BInteger(3);
  private static final BInteger FOUR  = new BInteger(4);
  private static final BInteger FIVE  = new BInteger(5);

  /** Constant for Integer.MIN_VALUE */
  public static final BInteger MIN = new BInteger(Integer.MIN_VALUE);

  /** Constant for Integer.MAX_VALUE */
  public static final BInteger MAX = new BInteger(Integer.MAX_VALUE);

  /** The default integer constant is 0. */
  public static final BInteger DEFAULT = ZERO;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInteger.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int value;

}
