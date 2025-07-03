/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonByteArray extends <code>BLonPrimitive</code> to
 * represent a byte array in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  8 June 01
 * @version   $Revision: 9$ $Date: 9/28/01 10:20:40 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonByteArray
  extends BLonPrimitive
{
  /**
   * The default BLonByteArray constant is zero length array.
   */
  public static final BLonByteArray DEFAULT = new BLonByteArray(1);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonByteArray(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonByteArray.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Get a BLonByteArray with the specified byte array.
   */
  public static BLonByteArray make(int size)
  {
    return new BLonByteArray(size);
  }
  
  /**
   * Get a BLonByteArray with the given length.
   */
  public static BLonByteArray make(byte[] v)
  {
//    if(compare(v)) return this;
    return new BLonByteArray(v);
  }

  private BLonByteArray(byte[] v)
  {
    this.value = new byte[v.length];
    System.arraycopy(v, 0, this.value, 0, value.length);
  }
  
  private BLonByteArray(int len)
  {
    this.value = new byte[len];
  }
  
  /**
   * Create a BLonByteArray with the given value. The length
   * will be no longer than len.
  public BLonByteArray(byte[] v, int len)
  {
    this.value = new byte[len];
    int lenToCopy = (v.length >= len) ? len : v.length;
    System.arraycopy(v, 0, this.value, 0, lenToCopy);
  }
   */

  
  /**
   * Test if the obj is equal in value to this BLonByteArray.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BLonByteArray))
      return false;
    
    BLonByteArray comp = (BLonByteArray)obj;
    
    return compare(comp.value);
  }  
  
  private boolean compare(byte[] a)
  {  
    if (a.length != value.length) return false;

    for (int i = 0; i < a.length; i++)
    {
      if (a[i] != value[i]) return false;
    }

    return true;
  }
  
  /**
   * @return String for byte array value.
   */
  public String toString(Context context)
  {
    int len = value.length;
    if ( len == 0 ) return "";
    
    StringBuilder sb = new StringBuilder();
    for ( int i=0 ; i < len ; i++)
    {
      // Add delimiter before all bytes except first.
      if(i>0) sb.append(DELIMITER);
      // Supply leading zero if requested.
      sb.append(Integer.toString(value[i] & 0x00ff,RADIX));
    }
    return sb.toString();
  }
  
  /**
   * BLonByteArray is serialized.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    int len = value.length;
    out.writeInt(len);
    if(len>0)out.write(value);
  }
  
  /**
   *  BLonByteArray is unserialized.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    int len = in.readInt();
    byte[] a = new byte[len] ;
    if(len>0)in.readFully(a);
    return new BLonByteArray(a);
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
  {
    return toString(null);
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
  {
    StringTokenizer st = new StringTokenizer(s, DELIMITER); 
    int tokCnt = st.countTokens();
    byte[] b = new byte[tokCnt]; 

    if(tokCnt>0)
    {
      for (int i = 0 ; i < tokCnt ; i++)
      {
        b[i] = (byte)Integer.parseInt(st.nextToken(),RADIX);
      }
    }
    
    // If the value didn't change return original
    if(compare(b)) return this;
    
    return new BLonByteArray(b);
  }

  private static final String DELIMITER = " ";
  private static final int    RADIX     = 16;

  private byte[] value;
  
  public byte[] getBytes() { return value; }
  
////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    // convert to appropriate datatype in stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.NA: out.writeByteArray(value,e.getSize());  break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonString."); 
    }   
  }
  

  /**
   *  Translates from network bytes. Sets the 
   *  value of the object to the state represented 
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    BLonByteArray val;
    
    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.NA: val = new BLonByteArray(in.readByteArray(e.getSize()));  break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonString."); 
    }
  
    return val;
  }
  
  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString()
  {
    return encodeToString();
  }
    
  /** 
   * Create a BLonByteArray from a <code>String</code>.
   * If stringValue can not be decode to a <code>BLonByteArray</code> 
   * then do not modify this element this element.  
   */
  public BLonPrimitive makeFromString(String stringValue)
  {
    return (BLonPrimitive)decodeFromString(stringValue);
  }


}
