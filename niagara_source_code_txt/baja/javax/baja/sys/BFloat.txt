/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * The BFloat is the class interface for primitive float objects.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 40$ $Date: 10/25/04 10:04:16 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BFloat
  extends BNumber
{ 

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Factory method.
   */
  public static BFloat make(float value)
  {
    if (value == 0) return DEFAULT;
    if (value == Float.NEGATIVE_INFINITY) return NEGATIVE_INFINITY;
    if (value == Float.POSITIVE_INFINITY) return POSITIVE_INFINITY;
    if (Float.isNaN(value)) return NaN;
    return new BFloat(value);
  }

  /**
   * Factory method.
   */
  public static BFloat make(String value)
  {
    return make(decode(value));
  }

  /**
   * Private constructor.
   */
  private BFloat(float value) 
  {
    this.value = value;
  }

////////////////////////////////////////////////////////////////
// BNumber
////////////////////////////////////////////////////////////////  

  /**
   * @return the float value cast to an integer.
   */
  @Override
  public int getInt()
  {
    return (int)value;
  }

  /**
   * @return the float value cast to a long.
   */
  @Override
  public long getLong()
  {
    return (long)value;
  }

  /**
   * @return the float value.
   */
  @Override
  public float getFloat()
  {
    return value;
  }

  /**
   * @return the float value cast to a double.
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
   * BFloat's hash code is the floating value's
   * integer represention given by the
   * Float.floatToIntBits method.
   */
  public int hashCode()
  {
    return Float.floatToIntBits(value);
  }
  
  /**
   * BFloat equality is based on float value equality.
   * Unlike the standard == operator, two float values
   * of Float.NaN are considered equal.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BFloat)
    {
      float x = ((BFloat)obj).value;
      if (x == value) return true;
      if (Float.isNaN(x) && Float.isNaN(value)) return true;
    }
    return false;
  }
  
  /**
   * Do a float comparision, but unlike the 
   * standard == operator, two float values of
   * Float.NaN are considered equal.
   */
  public static boolean equals(float a, float b)
  {
    if (a == b) return true;
    if (Float.isNaN(a) && Float.isNaN(b)) return true;
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
    float a = value;
    float b = ((BNumber)obj).getFloat();
    if (a == b) return 0;
    if (Float.isNaN(a) && Float.isNaN(b)) return 0;
    if (a < b) return -1;
    else return 1;
  }
  
  /**
   * Route to {@code BFloat.toString(float, Context)}.
   */
  @Override
  public String toString(Context context)
  {
    return toString(value, context);
  }
  
  /**
   * BFloat is serialized using writeFloat().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeFloat(value);
  }
  
  /**
   *  BFloat is unserialized using readFloat().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readFloat() );
  }

  /**
   * Route to {@code BFloat.encode(float)}.
   */               
  @Override
  public String encodeToString()
    throws IOException
  {
    return encode(value);
  }

  /**
   * Route to {@code BFloat.decode(String)}.
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
      throw new IOException("Invalid float: " + s);
    }
  }

////////////////////////////////////////////////////////////////
// Primitive Encoding
////////////////////////////////////////////////////////////////  

  /**
   * Encode the primitive float into a string.  
   * Special values are "+inf", "-inf", and "nan".
   */
  public static String encode(float f)
  {
    if (f == Float.POSITIVE_INFINITY) return "+inf";
    if (f == Float.NEGATIVE_INFINITY) return "-inf";
    if (Float.isNaN(f)) return "nan";
    return String.valueOf(f);
  }

  /**
   * Decode the primitive float from a string.  
   * Special values are "+inf", "-inf", and "nan".
   */
  public static float decode(String s)
  {
    if (s.equals("+inf")) return Float.POSITIVE_INFINITY;
    if (s.equals("-inf")) return Float.NEGATIVE_INFINITY;
    if (s.equals("nan"))  return Float.NaN;
    return Float.parseFloat(s);
  }

////////////////////////////////////////////////////////////////
// Format
////////////////////////////////////////////////////////////////  

  /**
   * Format the float value using the specified Context.
   * If there is a BFacets.PRECISION facet then that determines
   * how many digits are displayed after the decimal place.
   * The default is to display two digits after the decimal.
   * If there is a BFacets.UNITS then the the unit symbol is
   * appended.  Special values: "+inf", "-inf", "nan".
   */
  public static String toString(float value, Context context)
  {                  
    return BDouble.toString(value, context);
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  
  
  /** BFloat equal to Float.POSITIVE_INFINITY. */
  public static final BFloat POSITIVE_INFINITY = new BFloat(Float.POSITIVE_INFINITY);

  /** BFloat equal to Float.NEGATIVE_INFINITY. */
  public static final BFloat NEGATIVE_INFINITY = new BFloat(Float.NEGATIVE_INFINITY);

  /** BFloat equal to Float.NaN. */
  public static final BFloat NaN = new BFloat(Float.NaN);

  /** The default float constant is 0.0. */
  public static final BFloat DEFAULT = new BFloat(0f);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFloat.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
        
  private float value;
  
}
