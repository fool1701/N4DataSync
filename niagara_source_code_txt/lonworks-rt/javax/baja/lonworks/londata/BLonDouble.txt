 /*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

 import java.io.DataInput;
 import java.io.DataOutput;
 import java.io.IOException;
 import java.math.BigInteger;

 import javax.baja.lonworks.enums.BLonElementType;
 import javax.baja.lonworks.io.LonInputStream;
 import javax.baja.lonworks.io.LonOutputStream;
 import javax.baja.nre.annotations.NiagaraType;
 import javax.baja.nre.annotations.NoSlotomatic;
 import javax.baja.sys.BEnum;
 import javax.baja.sys.BEnumRange;
 import javax.baja.sys.BFacets;
 import javax.baja.sys.BINumeric;
 import javax.baja.sys.BObject;
 import javax.baja.sys.Context;
 import javax.baja.sys.Sys;
 import javax.baja.sys.Type;
 import javax.baja.units.BUnit;

/**
 * BLonDouble extends BLonPrimitive to
 * represent a float64 and unsigned32 element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  13 April 07
 * @version   $Revision: 9$ $Date: 9/28/01 10:20:44 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonDouble
  extends BLonPrimitive
  implements BINumeric
{
  /** The default double constant is 0.0. */
  public static final BLonDouble DEFAULT = new BLonDouble(0f);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonDouble(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDouble.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Factory method.
   */
  public static BLonDouble make(double value)
  {
    if (value == 0) return DEFAULT;
    if (value == Double.NEGATIVE_INFINITY) return NEGATIVE_INFINITY;
    if (value == Double.POSITIVE_INFINITY) return POSITIVE_INFINITY;
    if (Double.isNaN(value)) return NaN;
    return new BLonDouble(value);
  }

  /**
   * Create a BLonDouble with the given value.
   */
  private BLonDouble(double value) 
  {
    this.value = value;
  }

  /**
   * @return the double value.
   */
  public double getDouble()
  {
    return value;
  }

  /**
   * @return the double value cast to an integer.
   */
  public int getInt()
  {
    return (int)value;
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
   * appended.  Special values: "+inf", "-inf", "nan".
   */
  public static String toString(double value, Context context)
  {    
   // int prec = 2;
    BUnit units = null;
    if (context != null)
    {
    //  BNumber precFacet = (BNumber)context.getFacet(BFacets.PRECISION);
    //  if (precFacet != null) prec = precFacet.getInt();
      
      units = (BUnit)context.getFacet(BFacets.UNITS);      
      if (units != null && units.isNull()) units = null;
    }
    
    String s;
    if (value == Double.POSITIVE_INFINITY) s = "+inf";
    else if (value == Double.NEGATIVE_INFINITY) s = "-inf";
    else if (Double.isNaN(value)) s = "nan";
    else /*s = getFormatter(prec).format(value);*/ s=Double.toString(value);
    
    if (units != null) s += ' ' + units.getSymbol();
    return s;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * BLonDouble's hash code is the double value's
   * integer represention given by the
   * Double.doubleToIntBits method.
   */
  public int hashCode()
  {
    return (int)(Double.doubleToLongBits(value) & 0x0ffffffff);
  }
  
  /**
   * BLonDouble equality is based on double value equality.
   * Unlike the standard == operator, two double values
   * of Double.NaN are considered equal.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLonDouble)
    {
      double x = ((BLonDouble)obj).value;
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
  public int compareTo(Object obj)
  {
    double a = value;
    double b = ((BLonDouble)obj).value;
    if(equals(a,b)) return 0;
    if (a < b) return -1;
    return 1;
  }
  
  /**
   * Route to <code>BLonDouble.toString(double, Context)</code>.
   */
  public String toString(Context context)
  {
    return toString(value, context);
  }
  
  /**
   * BLonDouble is serialized using writeDouble().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeDouble(value);
  }
  
  /**
   *  BLonDouble is unserialized using readDouble().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    double f = in.readDouble();
    if(equals(value,f)) return this;
    
    return new BLonDouble(f);
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
   * Write the primitive double in String format.
   */
  public static String encodeToString(double value)
    throws IOException
  {
    if (value == Double.POSITIVE_INFINITY) return "+inf";
    if (value == Double.NEGATIVE_INFINITY) return "-inf";
    if (Double.isNaN(value)) return "nan";
    return String.valueOf(value); // Cast to double to work around QNX vm bug
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
      return new BLonDouble(Double.parseDouble(s));
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid double: " + s);
    }
  }

  /**
   * Parse the String format directly to a primitive double.
   */
  public static double doubleFromString(String s)
    throws IOException
  {
    try
    {
      if (s.equalsIgnoreCase("+inf")) return Double.POSITIVE_INFINITY;
      if (s.equalsIgnoreCase("-inf")) return Double.NEGATIVE_INFINITY;
      if (s.equalsIgnoreCase("nan"))  return Double.NaN;
      return Double.parseDouble(s);
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid double: " + s);
    }
  }

  /**
   * Get the BTypeSpec for this type's BPlugin.
  public BTypeSpec getPluginTypeSpec()
  {
    return pluginType;
  }
  static final BTypeSpec pluginType = new BTypeSpec("lonworks", "1.0", "LonDoublePlugin");
   */

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  
  
  /** BLonDouble equal to Double.POSITIVE_INFINITY. */
  public static final BLonDouble POSITIVE_INFINITY = new BLonDouble(Double.POSITIVE_INFINITY);

  /** BLonDouble equal to Double.NEGATIVE_INFINITY. */
  public static final BLonDouble NEGATIVE_INFINITY = new BLonDouble(Double.NEGATIVE_INFINITY);

  /** BLonDouble equal to Double.NaN. */
  public static final BLonDouble NaN = new BLonDouble(Double.NaN);

  private double value;

////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    // Get value
    double val = value;
    boolean invalid = false;

    if(e.hasInvalidValue() && Double.isNaN(val) ) 
    {
      val = e.getInvalidValue();
      invalid = true;
    }
    else
    {
      // apply offset
      if(e.getOffset()!=0.0f) val += e.getOffset();
      // scale
      if(e.getResolution()!= 1.0F) val /= e.getResolution();
    }
    
    // convert to appropriate datatype in output stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.C8:  out.writeUnsigned8 ((int)val);   break;
      case BLonElementType.S8:  out.writeSigned8   ((int)val);   break;
      case BLonElementType.U8:  out.writeUnsigned8 ((int)val);   break;
      case BLonElementType.S16: out.writeSigned16  ((int)val);   break;
      case BLonElementType.U16: out.writeUnsigned16((int)val);   break;
      case BLonElementType.S32: out.writeSigned32  ((int)val);   break;
      case BLonElementType.U32:
        if(invalid)
          out.writeUnsigned32(e.getInvalidValueL()); 
        else
          out.writeUnsigned32 ((long)val); 
        break;
      case BLonElementType.UB:  out.writeBit ((int)val,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
      case BLonElementType.SB:  out.writeSignedBit ((int)val,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
      case BLonElementType.F32: out.writeFloat     ((float)val);    break;
      case BLonElementType.F64: out.writeDouble    (val);    break;
      case BLonElementType.S64: 
        if(invalid)
          out.writeSigned64 (e.getInvalidValueL()); 
        else  
          out.writeSigned64 ((long)val);
        break;
      case BLonElementType.U64: 
        if(invalid)
          out.writeUnsigned64 (new BigInteger(Long.toString(e.getInvalidValueL())));
        else
          out.writeUnsigned64 (new BigInteger(Long.toString((long)val)));
        break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonDouble " + e.getElemtype());
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
    long lval;

    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.C8:  val = in.readUnsigned8();    break;
      case BLonElementType.S8:  val = in.readSigned8();      break;
      case BLonElementType.U8:  val = in.readUnsigned8();    break;
      case BLonElementType.S16: val = in.readSigned16();     break;
      case BLonElementType.U16: val = in.readUnsigned16();   break;
      case BLonElementType.S32: val = in.readSigned32();     break;
      case BLonElementType.U32: 
        lval = in.readUnsigned32();   
        if(e.isInvalid(lval)) return make(Double.NaN);
        val = (double)lval;
        break;
      case BLonElementType.UB:  val = in.readBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
      case BLonElementType.SB:  val = in.readSignedBit(e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
      case BLonElementType.F32: val = in.readFloat();        break;
      case BLonElementType.F64: val = in.readDouble();       break;
      case BLonElementType.S64: 
        lval = in.readSigned64();     
        if(e.isInvalid(lval))  return make(Double.NaN);
        val = (double)lval;
        break;
      case BLonElementType.U64: val = in.readUnsigned64().doubleValue();   break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonDouble.");
    }
     
    // check for invalid value
    if(e.isInvalid(val)) return make(Double.NaN);
      
    // scale
    if(e.getResolution()!= 1.0F) val *= e.getResolution();

    // remove offset
    if(e.getOffset()!=0.0f) val -= e.getOffset();

    // If the value didn't change return original
    if(val == value) return this;
    
    return make(val);
  }

  /** Get the value of this element as a <code>double</code>. */
  public double getDataAsDouble() { return value; }
  /** Create a BLonDouble from a <code>double</code>. */
  public BLonPrimitive  makeFromDouble(double v, BLonElementQualifiers e) { return make(v); }
 
  /** Returns true. */
  public final boolean isNumeric() { return true; }

  /** Get the value of this element as a <code>boolean</code>.  
   * @return If the value=0.0 return false else true. */
  public boolean getDataAsBoolean() { return value>0.0; }
  /** Create a BLonDouble from a <code>boolean</code>. 
    * If true then set to 1.0 else set to 0.0. */
  public BLonPrimitive  makeFromBoolean(boolean v) {return make((v ? 1.0F : 0.0F)); }

  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString() { return Double.toString(value); }
  /** Create a BLonDouble from a <code>String</code>. If string
   *  is not a valid double then set to Double.NaN */
  public BLonPrimitive  makeFromString(String v) 
  {
    double f = Double.NaN;
    try { f = Double.valueOf(v).doubleValue(); } catch(Throwable e){}
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
    return new BLonDouble(v.getOrdinal());
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the status element as a String.
  public final String toString(Context context)
  {
    return Double.toString(value);
  }
   */

  public int getIntValue() { return (int)value; }

////////////////////////////////////////////////////////////////
// BINumeric,BIBoolean
////////////////////////////////////////////////////////////////
  /** Get the numeric as double value.  */
  public double getNumeric() { return value; }

  /** Facets not accessible - return BFacets.NULL.  */
  public BFacets getNumericFacets() { return BFacets.NULL; }

  /** Get the boolean value.
  public boolean getBoolean() { return getDataAsBoolean(); } 
   */
  /** Facets not accessible - return BFacets.NULL.  
  public BFacets getBooleanFacets() { return BFacets.NULL; } 
*/
}
