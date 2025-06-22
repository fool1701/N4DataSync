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
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonLong extends BLonPrimitive to
 * represent an s64 element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  28 Aug 13
 * @version   $Revision: 2$ $Date: 9/28/01 10:20:46 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonLong
  extends BLonPrimitive
  implements BINumeric
{
  public static final BLonLong DEFAULT = new BLonLong(0,false);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonLong(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonLong.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method.
   * @param b 
   */
  public static BLonLong make(long value, boolean b)
  {
    return new BLonLong(value, b);
  }

  /**
   * Create a BLonLong with the given value.
   * @param b 
   */
  private BLonLong(long value, boolean b)
  {
    this.value = value;
    this.invalid = b;
  }

  /**
   * @return the long value.
   */
  public long getLong()
  {
    return value;
  }

  /**
   * @return the long value cast to a float.
   */
  public float getFloat()
  {
    return (float)value;
  }
  
  /**
   * BLonLong hash code is (int)value. 
   */
  public int hashCode()
  {
    return (int)value;
  }
  
  /**
   * BLonLong equality is based on value equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLonLong)
      return ((BLonLong)obj).value == value;
    return false;
  }

  /**
   * Compares this object with the specified object for 
   * order. Returns a negative long, zero, or a positive 
   * long as this object is less than, equal to, or greater 
   * than the specified object.
   */
  public int compareTo(Object obj)
  {
    long a = value;
    long b = ((BNumber)obj).getLong();
    if (a == b) return 0;
    if (a < b) return -1;
    else return 1;
  }
        
  /**
   * To string.
   */
  public String toString(Context context)
  {
    return encodeToString();
  }
  
  /**
   * BLonLong is serialized using writeInt().  
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeLong(value);
    out.writeBoolean(invalid);
  }
  
  /**
   *  BLonLong is unserialized using readInt().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readLong(), in.readBoolean() );
  }

  /**
   * Write the simple in text format.
   */
  public String encodeToString()
 //   throws IOException
  {
    return encodeToString(value, invalid);
  }

  /**
   * Encode a long primitive value to its text format.
   */
  public static String encodeToString(long val, boolean invld)
  {
    return Long.toString(val) + (invld ?  "/invalid" : "");
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
      if(pos<0) return make( Long.parseLong(s), false );
      return make( Long.parseLong(s.substring(0, pos)), true );
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid long: " + s);
    }
  }

  /**
   * Parse the text format directly to a primitive long.
  public static long longFromString(String s)
    throws IOException
  {
    try
    {
      return Long.parseLong(s);
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid long: " + s);
    }
  }
   */
  
  private long  value;
  private boolean invalid=false;

////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    long  val = value;
    
    // Don't apply offset if invalid value
    if( !(e.hasInvalidValue() && value == e.getInvalidValueL()) && e.getOffset() > 0.0F ) 
    {
      // apply offset
      val += e.getOffset();
      // scale
      // val /= e.getResolution();  // if scale used BLonDouble
    }

    switch(e.getElemtype().getOrdinal())
    {
//      case BLonElementType.C8: out.writeUnsigned8((int)val);  break;
//      case BLonElementType.S8: out.writeSigned8((int)val);    break;
//      case BLonElementType.U8: out.writeUnsigned8((int)val);  break;
//      case BLonElementType.S16: out.writeSigned16((int)val);   break;
//      case BLonElementType.U16: out.writeUnsigned16((int)val); break;
      case BLonElementType.S32: out.writeSigned32((int)val);   break;
      case BLonElementType.U32: out.writeUnsigned32(val);      break;
      case BLonElementType.S64: out.writeSigned64(val);        break;
//      case BLonElementType.U64: out.writeUnsigned64(new BigInteger(Long.toString(val)));  break;
//      case BLonElementType.UB: out.writeBit((int)val,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
//      case BLonElementType.SB: out.writeSignedBit((int)val,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
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
    long val;
    
    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
//      case BLonElementType.C8: val = in.readUnsigned8();  break;
//      case BLonElementType.S8: val = in.readSigned8();    break;
//      case BLonElementType.U8: val = in.readUnsigned8();  break;
//      case BLonElementType.S16: val = in.readSigned16();   break;
//      case BLonElementType.U16: val = in.readUnsigned16(); break;
      case BLonElementType.S32: val = in.readSigned32();   break;
      case BLonElementType.U32: val = in.readUnsigned32(); break;
      case BLonElementType.S64: val = in.readSigned64();   break;
//      case BLonElementType.U64: val = in.readUnsigned64().longValue();   break;
//      case BLonElementType.UB: val = in.readBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
//      case BLonElementType.SB: val = in.readSignedBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonLong.");
    }
    
    boolean invld = e.isInvalid(val);

    // remove any offset - don't change if invalid value
    if(!invld && e.getOffset() > 0.0F) 
    {
      val -= e.getOffset();
    }
    
    // If the value didn't change return original
    if(value==val) return this;
    
    return make(val,invld);
  }
  
  /** Get the value of this element as a <code>float</code>. */
  public double getDataAsDouble()
  { 
    if(invalid) return Double.NaN;
    return (double)value; 
  }

  /** Create a {@code BLonLong} from a <code>float</code>. */
  public BLonPrimitive  makeFromDouble(double value, BLonElementQualifiers e) 
  { 
    if(Double.isNaN(value) && e!=null && e.hasInvalidValue()) 
      return make(e.getInvalidValueL(), true);
    
    return make((long)value, false);
  }

  /** Returns true. */
  public final boolean isNumeric() { return true; }
  
  /** Get the value of this element as a <code>boolean</code>.  
   * @return If the value=0.0 return false else true. */
  public boolean getDataAsBoolean() { return value!=0; }
  
  /** Create a BLonLong from a <code>boolean</code>. 
    * If true then set to 1 else set to 0. */
  public BLonPrimitive  makeFromBoolean(boolean v) {return make(v ? 1 : 0, false); }
  
  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString() { return Long.toString(value); }
  
  /** Create a {@code BLonLong} from a <code>String</code>. If string
   *  is not a valid long then do not modify this element. */
  public BLonPrimitive  makeFromString(String stringValue) 
  {
    long i = 0;
    try { i = Long.parseLong(stringValue); }
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
