/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonBigInteger extends BLonPrimitive to
 * represent an s64 element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  28 Aug 13
 * @version   $Revision: 2$ $Date: 9/28/01 10:20:46 AM$
 * @since     Niagara 3.8
 */
@NiagaraType
@NoSlotomatic
public final class BLonBigInteger
  extends BLonPrimitive
  implements BINumeric
{
  public static final BLonBigInteger DEFAULT = new BLonBigInteger(BigInteger.ZERO, false);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonBigInteger(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonBigInteger.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method.
   */
  public static BLonBigInteger make(BigInteger value, boolean b)
  {
    return new BLonBigInteger(value, b);
  }

  /**
   * Create a BLonBigInteger with the given value.
   */
  private BLonBigInteger(BigInteger value, boolean b)
  {
    this.value = value;
    this.invalid = b;
 }

  /**
   * @return the BigInteger value.
   */
  public BigInteger getBigInteger()
  {
    return value;
  }
  
  /**
   * @return the BigInteger value as long.
   */
  public long getLong()
  {
    return value.longValue();
  }

  /**
   * @return the BigInteger value cast to a float.
   */
  public float getFloat()
  {
    return value.floatValue();
  }
  
  /**
   * BLonBigInteger hash code is (int)value. 
   */
  public int hashCode()
  {
    return value.intValue();
  }
  
  /**
   * BLonBigInteger equality is based on value equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLonBigInteger)
      return ((BLonBigInteger)obj).value == value;
    return false;
  }

  /**
   * Compares this object with the specified object for 
   * order. Returns a negative BigInteger, zero, or a positive 
   * BigInteger as this object is less than, equal to, or greater 
   * than the specified object.
   */
  public int compareTo(Object obj)
  {
    return value.compareTo((BigInteger)obj);
  }
        
  /**
   * To string.
   */
  public String toString(Context context)
  {
    return encodeToString();
  }
  
  /**
   * BLonBigInteger is serialized.  
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(value.toString());
    out.writeBoolean(invalid);
  }
  
  /**
   *  BLonBigInteger is unserialized.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(new BigInteger(in.readUTF()), in.readBoolean());
  }

  /**
   * Write the simple in text format.
   */
  public String encodeToString()
//    throws IOException
  {
    return encodeToString(value, invalid);
  }

  /**
   * Encode a BigInteger primitive value to its text format.
   */
  public static String encodeToString(BigInteger value, boolean invld)
  {
    return value.toString() + (invld ?  "/invalid" : "");
  }

  /**
   * Read the simple from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      int pos = s.indexOf("/");
      if(pos<0) return make( new BigInteger(s), false );
      return make( new BigInteger(s.substring(0, pos)), true );
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid BigInteger: " + s);
    }
  }

  /**
   * Parse the text format directly to a primitive BigInteger.
   */
  public static BigInteger bigIntegerFromString(String s)
    throws IOException
  {
    try
    {
      return new BigInteger(s);
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid BigInteger: " + s);
    }
  }

  private BigInteger  value;
  private boolean     invalid;

////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    BigInteger val = value;
    
    if( !invalid && e.getOffset() > 0.0F ) 
    {
      // apply offset
      val = val.add(BigInteger.valueOf((long)e.getOffset()));
    }
    
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.U64: out.writeUnsigned64(val);  break;
      default:
         throw new InvalidTypeException("Invalue id datatype for LonLong.");
    }
  }


  /**
   *  Translates from network bytes. Sets the
   *  value of the object to the state represented
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    BigInteger val;
    
    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
//      case BLonElementType.C8: val = in.readUnsigned8();  break;
//      case BLonElementType.S8: val = in.readSigned8();    break;
//      case BLonElementType.U8: val = in.readUnsigned8();  break;
//      case BLonElementType.S16: val = in.readSigned16();   break;
//      case BLonElementType.U16: val = in.readUnsigned16(); break;
//      case BLonElementType.S32: val = in.readSigned32();   break;
//      case BLonElementType.U32: val = in.readUnsigned32(); break;
//      case BLonElementType.S64: val = in.readSigned64();   break;
      case BLonElementType.U64: val = in.readUnsigned64();   break;
//      case BLonElementType.UB: val = in.readBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
//      case BLonElementType.SB: val = in.readSignedBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonLong.");
    }

    boolean invld = e.isInvalid(val.longValue());
    
    // remove any offset - don't change if invalid value
    if(!invld && e.getOffset() > 0.0F) 
    {
      val = val.subtract(BigInteger.valueOf((long)e.getOffset()));
    }
    
    // If the value didn't change return original
    if(value==val) return this;
    
    return make(val,invld);
  }
  
  /** Get the value of this element as a <code>float</code>. */
  public double getDataAsDouble()
  { 
    if(invalid) return Double.NaN;
    return value.doubleValue(); 
  }
  /** Create a {@code BLonBigInteger} from a <code>float</code>. */
  public BLonPrimitive makeFromDouble(double value, BLonElementQualifiers e) 
  { 
    if(Double.isNaN(value) && e!=null && e.hasInvalidValue()) 
      return make(BigInteger.valueOf(e.getInvalidValueL()), true);
    
    // There is no BigInteger constructor from double - must convert to string and then to bigInteger
    DecimalFormat df = new DecimalFormat("#0");
    FieldPosition pos = new FieldPosition( NumberFormat.INTEGER_FIELD);
    StringBuffer sb = df.format(value, new StringBuffer(), pos);
    String s = sb.toString();
    BigInteger bi =  new BigInteger(s);
    return make(bi,false); 
  } //return make(BigInteger.valueOf((long)value)); }

  /** Returns true. */
  public final boolean isNumeric() { return true; }
  
  /** Get the value of this element as a <code>boolean</code>.  
   * @return If the value=0.0 return false else true. */
  public boolean getDataAsBoolean() { return value.longValue()!=0; }
  
  /** Create a BLonBigInteger from a <code>boolean</code>. 
    * If true then set to 1 else set to 0. */
  public BLonPrimitive  makeFromBoolean(boolean v) {return make(v ? BigInteger.ONE : BigInteger.ZERO, false); }
  
  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString() { return value.toString(); }
  
  /** Create a {@code BLonBigInteger} from a <code>String</code>. If string
   *  is not a valid BigInteger then do not modify this element. */
  public BLonPrimitive  makeFromString(String stringValue) 
  {
    BigInteger i = BigInteger.ZERO;
    try { i = new BigInteger(stringValue); }
    catch(Throwable e){ return null; } 
    return make(i, false); 
  }
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the status element as a String.
  public final String toString(Context context)
  {
    return Integer.toString(value);
  }
   */
////////////////////////////////////////////////////////////////
// BINumeric,BIBoolean
////////////////////////////////////////////////////////////////
  /** Get the numeric as double value. */
  public double getNumeric() {  return getDataAsDouble(); }

  /** Facets not accessible - return BFacets.NULL. */
  public BFacets getNumericFacets()  {  return BFacets.NULL; }
  
  /** Get the boolean value. 
  public boolean getBoolean() { return getDataAsBoolean(); } 
  */
  /** Facets not accessible - return BFacets.NULL. 
  public BFacets getBooleanFacets() { return BFacets.NULL; } 
*/
}
