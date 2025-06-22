/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.units.BUnit;
import javax.baja.units.BUnitConversion;

/**
 * The BDouble is the class interface for primitive double objects.
 *
 * @author Brian Frank 22 Mar 2004
 * @since Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BDouble
  extends BNumber
{

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Factory method.
   */
  public static BDouble make(double value)
  {
    if (value == 0) return DEFAULT;
    if (value == Double.NEGATIVE_INFINITY) return NEGATIVE_INFINITY;
    if (value == Double.POSITIVE_INFINITY) return POSITIVE_INFINITY;
    if (Double.isNaN(value)) return NaN;
    return new BDouble(value);
  }

  /**
   * Factory method.
   */
  public static BDouble make(String value)
  {
    return make(decode(value));
  }

  /**
   * Private constructor.
   */
  private BDouble(double value)
  {
    this.value = value;
  }

////////////////////////////////////////////////////////////////
// BNumber
////////////////////////////////////////////////////////////////

  /**
   * @return the double value cast to an integer.
   */
  @Override
  public int getInt()
  {
    return (int) value;
  }

  /**
   * @return the double value cast to a float.
   */
  @Override
  public float getFloat()
  {
    return (float) value;
  }

  /**
   * @return the double value cast to a long.
   */
  @Override
  public long getLong()
  {
    return (long) value;
  }

  /**
   * @return the double value.
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
   * BDouble's uses the algorthm for hashing BLong
   * with the result of Double.doubleToLongBits().
   */
  public int hashCode()
  {
    long hash = Double.doubleToLongBits(value);
    return (int) (hash ^ (hash >>> 32));
  }

  /**
   * BDouble equality is based on double value equality.
   * Unlike the standard == operator, two double values
   * of Double.NaN are considered equal.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BDouble)
    {
      double x = ((BDouble) obj).value;
      if (x == value) return true;
      if (Double.isNaN(x) && Double.isNaN(value)) return true;
    }
    return false;
  }

  /**
   * Do a double comparision, but unlike the
   * standard == operator, two double values of
   * Double.NaN are considered equal.
   */
  public static boolean equals(double a, double b)
  {
    if (a == b) return true;
    if (Double.isNaN(a) && Double.isNaN(b)) return true;
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
    double a = value;
    double b = ((BNumber) obj).getDouble();
    if (a == b) return 0;
    if (Double.isNaN(a) && Double.isNaN(b)) return 0;
    if (a < b) return -1;
    else return 1;
  }

  /**
   * Route to {@code BDouble.toString(double, Context)}.
   */
  @Override
  public String toString(Context context)
  {
    return toString(value, context);
  }

  /**
   * BDouble is serialized using writeDouble().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeDouble(value);
  }

  /**
   * BDouble is unserialized using readDouble().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readDouble());
  }

  /**
   * Route to {@code BDouble.encode(double)}.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    return encode(value);
  }

  /**
   * Route to {@code BDouble.decode(String)}.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return make(s);
    }
    catch (RuntimeException e)
    {
      throw new IOException("Invalid double: " + s + "\n" + e.toString());
    }
  }

////////////////////////////////////////////////////////////////
// Primitive Encoding
////////////////////////////////////////////////////////////////

  /**
   * Encode the primitive double into a string.
   * Special values are "+inf", "-inf", and "nan".
   */
  public static String encode(double d)
  {
    if (d == Double.POSITIVE_INFINITY) return "+inf";
    if (d == Double.NEGATIVE_INFINITY) return "-inf";
    if (Double.isNaN(d)) return "nan";

    return String.valueOf(d);
  }

  /**
   * Decode the primitive double from a string.
   * Special values are "+inf", "-inf", and "nan".
   */
  public static double decode(String s)
  {
    if (s.equals("+inf")) return Double.POSITIVE_INFINITY;
    if (s.equals("-inf")) return Double.NEGATIVE_INFINITY;
    if (s.equals("nan")) return Double.NaN;

    // START FIX: Issues 11393 and 14108: Due to a bug introduced with
    // scientific notation support, it is possible that some bog files
    // will have localized encodings of the double. The approach is to:
    // 1) Assume the BOG is correctly encoded and attempt to parse the double
    // 2) If that fails, try using the format to decode it (localized decode)
    //    This uses the current locale.
    // 3) If that fails, try all available locales to see if one works.
    // 4) If that fails, bail with an exception. It will be necessary
    //    to manually fix the BOG in this case.

    boolean loggable = LOG.isLoggable(Level.FINE);
    StringBuilder buf = null;

    Number n;
    // If we already have a lastWorkingLocale, try that one first
    if (lastWorkingLocale != null)
    {
      if (loggable)
      {
        LOG.fine("Trying lastWorkingLocale: " + lastWorkingLocale);
      }
      n = tryLocale(s, lastWorkingLocale, loggable);
      if (n != null) return n.doubleValue();

      if (lastWorkingLocale != Locale.getDefault())
      {
        if (loggable)
        {
          LOG.fine("Trying default: " + Locale.getDefault());
        }
        n = tryLocale(s, Locale.getDefault(), loggable);
        if (n != null) return n.doubleValue();
      }
    }

    // Before looping through all the locales, try parseDouble()
    try
    {
      if (loggable)
      {
        LOG.fine(String.format("Decoding '%s'", s));
        buf = new StringBuilder("  Decoding with Double.parseDouble\t");
      }
      // Double.parseDouble doesn't support locales.  But it is very fast, so we'll try it first
      double doubleValue = Double.parseDouble(s);
      if (loggable)
      {
        buf.append("  (Success!)");
      }
      return doubleValue;
    }
    catch (Exception e)
    {
      if (loggable)
      {
        buf.append("  (Failed)");
      }
    }
    finally
    {
      if (loggable)
      {
        LOG.fine(buf.toString());
      }
    }

    for (Locale locale : NumberFormat.getAvailableLocales())
    {
      n = tryLocale(s, locale, loggable);
      if (n != null) return n.doubleValue();
    }

    throw new NumberFormatException("Invalid character - unable to decode BDouble: " + s);
  }

  private static Number tryLocale(String s, Locale locale, boolean loggable)
  {
    ParsePosition pos = new ParsePosition(0);
    DecimalFormat localeFormat = (DecimalFormat) NumberFormat.getInstance(locale);
    localeFormat.setGroupingUsed(false);
    StringBuilder buf = null;
    if (loggable)
    {
      buf = new StringBuilder();
      buf.append(formatLocale(locale, localeFormat));
    }
    Number n = localeFormat.parse(s, pos);
    if (pos.getIndex() == s.length() && n != null)
    {
      if (loggable)
      {
        buf.append(" (Success!)");
        LOG.fine(buf.toString());
        LOG.fine("Changing lastWorkingLocale to " + locale);
      }
      lastWorkingLocale = locale;
      return n;
    }
    if (loggable)
    {
      buf.append(" (Failed)");
      LOG.fine(buf.toString());
    }
    return null;
  }

  private static String formatLocale(Locale locale, DecimalFormat localeFormat)
  {
    return String.format("  Decoding with locale: %5s %3s (%20s) %s (%20s)- %20s\t", locale, locale.getCountry(), locale.getDisplayCountry(), locale.getLanguage(), locale.getDisplayLanguage(), localeFormat.toLocalizedPattern());
  }


////////////////////////////////////////////////////////////////
// Format
////////////////////////////////////////////////////////////////

  /**
   * Format the double value using the specified Context.
   * If there is a BFacets.PRECISION facet then that determines
   * how many digits are displayed after the decimal place.
   * The default is to display two digits after the decimal.
   * If there is a BFacets.UNITS then the the unit symbol is
   * appended.  If BFacets.RADIX is specified other than 10,
   * then the value is assumed to an int and displayed in
   * specified radix. Special values: "+inf", "-inf", "nan".
   */

  public static String toString(double value, Context context)
  {
    int prec = 2;
    BUnit units = null;
    int radix = 10;
    int convert = com.tridium.sys.Nre.unitConversion;
    boolean showUnits = true;
    boolean showSeparators = false;
    boolean forceSign = false;

    if (context != null)
    {
      BNumber precFacet = (BNumber) context.getFacet(BFacets.PRECISION);
      if (precFacet != null) prec = precFacet.getInt();

      units = (BUnit) context.getFacet(BFacets.UNITS);
      if (units != null && units.isNull()) units = null;

      BNumber radixFacet = (BNumber) context.getFacet(BFacets.RADIX);
      if (radixFacet != null) radix = radixFacet.getInt();

      convert = context.getFacets().geti(BFacets.UNIT_CONVERSION, convert);
      showUnits = context.getFacets().getb(BFacets.SHOW_UNITS, showUnits);
      showSeparators = context.getFacets().getb(BFacets.SHOW_SEPARATORS, showSeparators);
      forceSign = context.getFacets().getb(BFacets.FORCE_SIGN, forceSign);
    }

    if (convert != 0 && units != null)
    {
      BUnitConversion c = BUnitConversion.make(convert);
      BUnit desired = c.getDesiredUnit(units);
      if (desired != units)
      {
        value = units.convertTo(desired, value);
        units = desired;
      }
    }

    String s = "";

    if (showUnits && (units != null) && units.getIsPrefix())
      s += units.getSymbol(context) + ' ';

    if (value == Double.POSITIVE_INFINITY) s += "+inf";
    else if (value == Double.NEGATIVE_INFINITY) s += "-inf";
    else if (Double.isNaN(value)) s += "nan";
    else if (radix != 10)
    {
      //large negative doubles show a negative sign instead of missing bits
      if ((long) (Math.abs(value)) >= MAX_LONG_PREC)
      {
        BigInteger number = BigDecimal.valueOf(value).toBigInteger();
        s += number.toString(radix);
      }
      else
      {
        //negative Longs get same special treatment as negative Integers
        if ((long) (Math.abs(value)) >= MAX_INT_PREC)
        {
          if (radix == 2) s += Long.toBinaryString((int) value);
          else if (radix == 8) s += Long.toOctalString((int) value);
          else if (radix == 16) s += Long.toHexString((int) value);
          else s += Long.toString((long) value, radix);
        }
        else
        {
          // Check a few special radix cases (preserve Pacman 15576)
          if (radix == 2) s += Integer.toBinaryString((int) value);
          else if (radix == 8) s += Integer.toOctalString((int) value);
          else if (radix == 16) s += Integer.toHexString((int) value);
          else s += Integer.toString((int) value, radix);
        }
      }
    }
    else if (Math.IEEEremainder(value, 1) == 0)
    {
      s += addForcedSign(forceSign, value);

      boolean useScientificNotation = (long) (Math.abs(value)) >= MAX_LONG_PREC;
      s += getFormatter(prec, showSeparators, useScientificNotation, context).format(value);
    }
    else
    {
      s += addForcedSign(forceSign, value);
      s += getFormatter(prec, showSeparators, false, context).format(value);
    }

    if (showUnits && (units != null) && !units.getIsPrefix())
      s += ' ' + units.getSymbol(context);
    return s;
  }

  static String addForcedSign(boolean forceSign, double value)
  {
    return (forceSign && (value > 0.0d)) ? "+" : "";
  }

  /**
   * Get the formatter for the specified precision, showSeparators value, useScientificNotation value (like 1e10) and Locale (based on the Context's getLanguageTag.
   */
  static DecimalFormat getFormatter(int precision, boolean showSeparators, boolean useScientificNotation, Context cx)
  {
    String userLang = Context.getLanguageTag(cx);
    String key = "" + precision + showSeparators + useScientificNotation + userLang;
    DecimalFormat formatter = formatters.get(key);
    if (formatter == null)
    {
      StringBuilder pattern = new StringBuilder(16);

      if (useScientificNotation)
      {
        pattern.append("0.###############E0");
      }
      else
      {
        if (showSeparators)
          pattern.append("#,##0");
        else
          pattern.append("#0");

        if (precision > 0)
        {
          pattern.append('.');
          for (int i = 0; i < precision; ++i) pattern.append('0');
        }
      }

      if (!userLang.equals("")) // Handle default user language
      {
        formatter = new DecimalFormat(pattern.toString(), DecimalFormatSymbols.getInstance(Locale.forLanguageTag(userLang)));
      }
      else
      {
        formatter = new DecimalFormat(pattern.toString());
      }

      formatters.put(key, formatter);
    }
    return formatter;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * BDouble equal to Float.POSITIVE_INFINITY.
   */
  public static final BDouble POSITIVE_INFINITY = new BDouble(Double.POSITIVE_INFINITY);

  /**
   * BDouble equal to Float.NEGATIVE_INFINITY.
   */
  public static final BDouble NEGATIVE_INFINITY = new BDouble(Double.NEGATIVE_INFINITY);

  /**
   * BDouble equal to Float.NaN.
   */
  public static final BDouble NaN = new BDouble(Double.NaN);

  /**
   * The default double constant is 0.0.
   */
  public static final BDouble DEFAULT = new BDouble(0f);

  @Override
  public Type getType()
  {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BDouble.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Map<String, DecimalFormat> formatters = new HashMap<>();
  private static Locale lastWorkingLocale = null;

  private double value;

  // this is the largest whole number that can be represented by a double
  // without losing any precision.  If you add one to a double that contains
  // this value, you will get this value back.
  private static final long MAX_LONG_PREC = 9007199254740992L;
  private static final long MAX_INT_PREC = 2147483647L;

  protected static Logger LOG = Logger.getLogger("sys");

}
