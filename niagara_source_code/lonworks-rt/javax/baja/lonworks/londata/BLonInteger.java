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

/**
 * BLonInteger extends BLonPrimitive to
 * represent an integer element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  5 Jan 01
 * @version   $Revision: 2$ $Date: 9/28/01 10:20:46 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonInteger
  extends BLonPrimitive
  implements BINumeric
{
  public static final BLonInteger DEFAULT = new BLonInteger(0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonInteger(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonInteger.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method.
   */
  public static BLonInteger make(int value)
  {
    return new BLonInteger(value);
  }

  /**
   * Create a BLonInteger with the given value.
   */
  private BLonInteger(int value)
  {
    this.value = value;
  }

  /**
   * @return the integer value.
   */
  public int getInt()
  {
    return value;
  }

  /**
   * @return the integer value cast to a float.
   */
  public float getFloat()
  {
    return (float)value;
  }
  
  /**
   * BLonInteger hash code is it integer value. 
   */
  public int hashCode()
  {
    return value;
  }
  
  /**
   * BLonInteger equality is based on integer value equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLonInteger)
      return ((BLonInteger)obj).value == value;
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
    int a = value;
    int b = ((BNumber)obj).getInt();
    if (a == b) return 0;
    if (a < b) return -1;
    else return 1;
  }
        
  /**
   * To string.
   */
  public String toString(Context context)
  {
    return String.valueOf(value);
  }
  
  /**
   * BLonInteger is serialized using writeInt().  
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(value);
  }
  
  /**
   *  BLonInteger is unserialized using readInt().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readInt() );
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
   * Encode a int primitive value to its text format.
   */
  public static String encodeToString(int value)
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
      return make( Integer.parseInt(s) );
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid integer: " + s);
    }
  }

  /**
   * Parse the text format directly to a primitive int.
   */
  public static int intFromString(String s)
    throws IOException
  {
    try
    {
      return Integer.parseInt(s);
    }
    catch(Throwable e)
    {
      throw new IOException("Invalid integer: " + s);
    }
  }
  
  /**
   * Get the BTypeSpec for this type's BPlugin.
  public BTypeSpec getPluginTypeSpec()
  {
    return pluginType;
  }
  static final BTypeSpec pluginType = BTypeSpec.make("lonworks", "LonIntegerPlugin");
   */

  private int  value;

////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.C8: out.writeUnsigned8(value);  break;
      case BLonElementType.S8: out.writeSigned8(value);    break;
      case BLonElementType.U8: out.writeUnsigned8(value);  break;
      case BLonElementType.S16: out.writeSigned16(value);   break;
      case BLonElementType.U16: out.writeUnsigned16(value); break;
      case BLonElementType.S32: out.writeSigned32(value);  break;
      case BLonElementType.UB: out.writeBit(value,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
      case BLonElementType.SB: out.writeSignedBit(value,e.getByteOffset(), e.getBitOffset(), e.getSize());   break;
      default:
         throw new InvalidTypeException("Invalueid datatype for LonInteger.");
    }
  }


  /**
   *  Translates from network bytes. Sets the
   *  value of the object to the state represented
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    int val;
    
    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.C8: val = in.readUnsigned8();  break;
      case BLonElementType.S8: val = in.readSigned8();    break;
      case BLonElementType.U8: val = in.readUnsigned8();  break;
      case BLonElementType.S16: val = in.readSigned16();   break;
      case BLonElementType.U16: val = in.readUnsigned16(); break;
      case BLonElementType.S32: val = in.readSigned32();   break;
      case BLonElementType.UB: val = in.readBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
      case BLonElementType.SB: val = in.readSignedBit(e.getByteOffset(), e.getBitOffset(), e.getSize());         break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonInteger.");
    }

    // If the value didn't change return original
    if(value==val) return this;
    
    return make(val);
  }
  
  /** Get the value of this element as a <code>float</code>. */
  public double getDataAsDouble() { return (double)value; }
  /** Create a {@code BLonInteger} from a <code>float</code>. */
  public BLonPrimitive  makeFromDouble(double value, BLonElementQualifiers e) { return make((int)value); }

  /** Returns true. */
  public final boolean isNumeric() { return true; }
  
  /** Get the value of this element as a <code>boolean</code>.  
   * @return If the value=0.0 return false else true. */
  public boolean getDataAsBoolean() { return value!=0; }
  
  /** Create a BLonInteger from a <code>boolean</code>. 
    * If true then set to 1 else set to 0. */
  public BLonPrimitive  makeFromBoolean(boolean v) {return make(v ? 1 : 0); }
  
  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString() { return Integer.toString(value); }
  /** Create a {@code BLonInteger} from a <code>String</code>. If string
   *  is not a valid integer then do not modify this element. */
  public BLonPrimitive  makeFromString(String stringValue) 
  {
    int i = 0;
    try { i = Integer.valueOf(stringValue).intValue(); }
    catch(Throwable e){ return null; } 
    return make(i); 
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
