 /*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.units.BUnit;

/**
 * BLonFloat extends BLonPrimitive to
 * represent a float or integer element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  5 Jan 01
 * @version   $Revision: 9$ $Date: 9/28/01 10:20:44 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonFloat
  extends BLonPrimitive
  implements BINumeric
{
  /** The default float constant is 0.0. */
  public static final BLonFloat DEFAULT = new BLonFloat(0f);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonFloat(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFloat.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Factory method.
   */
  public static BLonFloat make(float value)
  {
    if (value == 0) return DEFAULT;
    if (value == Float.NEGATIVE_INFINITY) return NEGATIVE_INFINITY;
    if (value == Float.POSITIVE_INFINITY) return POSITIVE_INFINITY;
    if (Float.isNaN(value)) return NaN;
    return new BLonFloat(value);
  }

  /**
   * Create a BLonFloat with the given value.
   */
  private BLonFloat(float value) 
  {
    this.value = value;
  }

  /**
   * @return the float value.
   */
  public float getFloat()
  {
    return value;
  }

  /**
   * @return the float value cast to an integer.
   */
  public int getInt()
  {
    return (int)value;
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
  //  int prec = 2;
    BUnit units = null;
    if (context != null)
    {
     // BNumber precFacet = (BNumber)context.getFacet(BFacets.PRECISION);
     // if (precFacet != null) prec = precFacet.getInt();
      
      units = (BUnit)context.getFacet(BFacets.UNITS);      
      if (units != null && units.isNull()) units = null;
    }
    
    String s;
    if (value == Float.POSITIVE_INFINITY) s = "+inf";
    else if (value == Float.NEGATIVE_INFINITY) s = "-inf";
    else if (Float.isNaN(value)) s = "nan";
    else /*s = getFormatter(prec).format(value);*/ s=Float.toString(value);
    
    if (units != null) s += ' ' + units.getSymbol();
    return s;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * BLonFloat's hash code is the floating value's
   * integer represention given by the
   * Float.floatToIntBits method.
   */
  public int hashCode()
  {
    return Float.floatToIntBits(value);
  }
  
  /**
   * BLonFloat equality is based on float value equality.
   * Unlike the standard == operator, two float values
   * of Float.NaN are considered equal.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLonFloat)
    {
      float x = ((BLonFloat)obj).value;
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
  public int compareTo(Object obj)
  {
    float a = value;
    float b = ((BLonFloat)obj).value;
    if(equals(a,b)) return 0;
    if (a < b) return -1;
    return 1;
  }
  
  /**
   * Route to <code>BLonFloat.toString(float, Context)</code>.
   */
  public String toString(Context context)
  {
    return toString(value, context);
  }
  
  /**
   * BLonFloat is serialized using writeFloat().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeFloat(value);
  }
  
  /**
   *  BLonFloat is unserialized using readFloat().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    float f = in.readFloat();
    if(equals(value,f)) return this;
    
    return new BLonFloat(f);
  }

  /**
   * Write the primitive in String format.  Special
   * values:  "+inf", "-inf", "nan".
   */
  public String encodeToString()
    throws IOException
  {
    return encodeToString(value);
  }

  /**
   * Write the primitive float in String format.
   */
  public static String encodeToString(float value)
    throws IOException
  {
    if (value == Float.POSITIVE_INFINITY) return "+inf";
    if (value == Float.NEGATIVE_INFINITY) return "-inf";
    if (Float.isNaN(value)) return "nan";
    return String.valueOf((double)value); // Cast to double to work around QNX vm bug
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {    
    try
    {
      if (s.equals("+inf")) return POSITIVE_INFINITY;
      if (s.equals("-inf")) return NEGATIVE_INFINITY;
      if (s.equals("nan"))  return NaN;
      return new BLonFloat((float)Double.parseDouble(s));
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid float: " + s);
    }
  }

  /**
   * Parse the String format directly to a primitive float.
   */
  public static float floatFromString(String s)
    throws IOException
  {
    try
    {
      if (s.equalsIgnoreCase("+inf")) return Float.POSITIVE_INFINITY;
      if (s.equalsIgnoreCase("-inf")) return Float.NEGATIVE_INFINITY;
      if (s.equalsIgnoreCase("nan"))  return Float.NaN;
      return (float)Double.parseDouble(s);
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid float: " + s);
    }
  }

  /**
   * Get the BTypeSpec for this type's BPlugin.
  public BTypeSpec getPluginTypeSpec()
  {
    return pluginType;
  }
  static final BTypeSpec pluginType = new BTypeSpec("lonworks", "1.0", "LonFloatPlugin");
   */

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  
  
  /** BLonFloat equal to Float.POSITIVE_INFINITY. */
  public static final BLonFloat POSITIVE_INFINITY = new BLonFloat(Float.POSITIVE_INFINITY);

  /** BLonFloat equal to Float.NEGATIVE_INFINITY. */
  public static final BLonFloat NEGATIVE_INFINITY = new BLonFloat(Float.NEGATIVE_INFINITY);

  /** BLonFloat equal to Float.NaN. */
  public static final BLonFloat NaN = new BLonFloat(Float.NaN);

  private float value;

////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    // Get value
    float val = value;

    if(e.hasInvalidValue() && Float.isNaN(val) ) 
    {
      val = e.getInvalidValue();
    }
    else
    {
      // apply offset
      if(e.getOffset()!=0.0f) val += e.getOffset();
      
      // scale
      if(e.getResolution()!= 1.0F) val /= e.getResolution();
    }
    
    // convert to appropriate datatype in output stream
    int ival = (int)val;
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.C8: out.writeUnsigned8 (ival);  break;
      case BLonElementType.S8: out.writeSigned8   (ival);    break;
      case BLonElementType.U8: out.writeUnsigned8 (ival);  break;
      case BLonElementType.S16: out.writeSigned16  (ival);   break;
      case BLonElementType.U16: out.writeUnsigned16(ival); break;
      case BLonElementType.S32: out.writeSigned32    (ival);  break;
      case BLonElementType.UB: out.writeBit (ival,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
      case BLonElementType.SB: out.writeSignedBit (ival,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
      case BLonElementType.F32: out.writeFloat     (val); break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonFloat " + e.getElemtype());
    }
  }


  /**
   *  Translates from network bytes. Sets the
   *  value of the object to the state represented
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    double val;

    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.C8: val = in.readUnsigned8();  break;
      case BLonElementType.S8: val = in.readSigned8();    break;
      case BLonElementType.U8: val = in.readUnsigned8();  break;
      case BLonElementType.S16: val = in.readSigned16();   break;
      case BLonElementType.U16: val = in.readUnsigned16(); break;
      case BLonElementType.S32: val = in.readSigned32();       break;
      case BLonElementType.UB: val = in.readBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
      case BLonElementType.SB: val = in.readSignedBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
      case BLonElementType.F32: val = in.readFloat();        break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonFloat.");
    }
     
    // check for invalid value
    if(e.hasInvalidValue() && (val == e.getInvalidValueL())) 
      return make(Float.NaN);
      
    // scale
    if(e.getResolution()!= 1.0F) val *= e.getResolution();

    // remove offset
    if(e.getOffset()!=0.0f) val -= e.getOffset();

    // If the value didn't change return original
    if(val == value) return this;
    
    return make((float)val);
  }

  /** Get the value of this element as a <code>float</code>. */
  public double getDataAsDouble() { return value; }
  /** Create a BLonFloat from a <code>float</code>. */
  public BLonPrimitive  makeFromDouble(double v, BLonElementQualifiers e) { return make((float)v); }
 
  /** Returns true. */
  public final boolean isNumeric() { return true; }

  /** Get the value of this element as a <code>boolean</code>.  
   * @return If the value=0.0 return false else true. */
  public boolean getDataAsBoolean() { return value>0.0; }
  /** Create a BLonFloat from a <code>boolean</code>. 
    * If true then set to 1.0 else set to 0.0. */
  public BLonPrimitive  makeFromBoolean(boolean v) {return make((v ? 1.0F : 0.0F)); }

  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString() { return String.valueOf((double)value); }
  /** Create a BLonFloat from a <code>String</code>. If string
   *  is not a valid float then set to Float.NaN */
  public BLonPrimitive  makeFromString(String v) 
  {
    float f = Float.NaN;
    try { f = Float.valueOf(v).floatValue(); } catch(Throwable e){}
    return make(f); 
  }

  public BEnum getDataAsEnum(BEnum en)
  {
    try
    {    
      BEnumRange enRng = en.getRange();
      return enRng.get((int)value);
    }
    catch(Throwable e){}
    return null;
  }

  public BLonPrimitive makeFromEnum(BEnum v)
  {
    return new BLonFloat(v.getOrdinal());
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the status element as a String.
  public final String toString(Context context)
  {
    return Float.toString(value);
  }
   */

  public int getIntValue() { return (int)value; }

////////////////////////////////////////////////////////////////
// BINumeric,BIBoolean
////////////////////////////////////////////////////////////////
  /** Get the numeric as double value.  */
  public double getNumeric() { return getDataAsDouble(); }

  /** Facets not accessible - return BFacets.NULL.  */
  public BFacets getNumericFacets() { return BFacets.NULL; }

  /** Get the boolean value.
  public boolean getBoolean() { return getDataAsBoolean(); } 
   */
  /** Facets not accessible - return BFacets.NULL.  
  public BFacets getBooleanFacets() { return BFacets.NULL; } 
*/

////////////////////////////////////////////////////////////////
// Test
////////////////////////////////////////////////////////////////
  // to run >>nre lonworks:javax.baja.lonworks.londata.BLonFloat
//  public static void main(String[] args)
//  {
//    test("5.0259750912E10");
//    test("5.0259750912E11");
//    test("5.0259750912E12");
//    test("5.0259750912E13");
//    test("5.0259750912E14");
//    test("5.0259750912E15");
//    test("5.0259750912E16");
//    test("5.0259750912E17");
//  }  
//  private static void test(String s)
//  {
//    try
//    {
//      BLonFloat lf = (BLonFloat)BLonFloat.DEFAULT.decodeFromString(s);
//      System.out.println(lf.encodeToString() + "   \t" + Double.parseDouble(s) + "   \t" + (float)Double.parseDouble(s));
//    }
//    catch(Throwable e)
//    {
//      e.printStackTrace();
//    }
//
//  }

}
