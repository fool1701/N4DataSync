/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import java.util.Arrays;
import java.util.Base64;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * The BBlob is the wrapper class for Java
 * raw byte array objects.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 22$ $Date: 1/6/04 1:57:06 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBlob
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a new BBlob with given byte array.
   * The original byte array is copied into a safe
   * private array.
   */
  public static BBlob make(byte[] v)
  {
    return make(v, 0, v.length);
  }
   
  /**
   * Construct a new BBlob with given byte array.
   * The original byte array is copied into a safe
   * private array.
   */
  public static BBlob make(byte[] v, int start, int len)
  {           
    if (len == 0) return DEFAULT;
    
    byte[] value = new byte[len];
    System.arraycopy(v, start, value, 0, len);
    return new BBlob(value);
  }

  /**
   * Private constructor with safe byte array.
   */
  private BBlob(byte[] v)
  {
    value = v;
  }
  
////////////////////////////////////////////////////////////////
// Access
//////////////////////////////////////////////////////////////// 

  /**
   * Get the length of the byte array.
   */
  public int length()
  {
    return value.length;
  }
  
  /**
   * Get the byte at the specified index.
   */
  public byte byteAt(int index)
  {
    return value[index];
  }
  
  /**
   * Copy the value into the given buffer.  The specified
   * buffer must have a length equal to or greater than
   * this BBlob's length.
   */
  public void copy(byte[] buf)
  {
    System.arraycopy(value, 0, buf, 0, value.length);    
  }
  
  /**
   * Copy the value of this byte array into the given
   * buffer byte array.
   *
   * @param start the start index of this byte array
   * @param len the number of bytes to copy
   * @param buf byte array to copy into
   * @param bufStart start index of {@code buf} to
   *    begin copying into
   */
  public void copyBytes(int start, byte[] buf, int bufStart, int len)
  {
    System.arraycopy(value, start, buf, bufStart, len);
  }
    
  /**
   * @return a copy of the byte array value.
   */
  public byte[] copyBytes()
  {
    byte[] copy = new byte[value.length];
    System.arraycopy(value, 0, copy, 0, copy.length);
    return copy;
  }

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /**
   * Hashcode for BBlob is based on the algorithm
   * used by the JDK for java.lang.String and 
   * java.rmi.MarshalledObject.
   */
  public int hashCode()
  {
    if (hashCode == 0)
    {
      int h = 31;
      for (byte b : value)
      {
        h = 31 * h + b;
      }
      hashCode = h;
    }
    return hashCode;
  }
  
  /**
   * All the bytes must be equal for BBlob equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BBlob)
    {
      byte[] me  = value;
      byte[] him = ((BBlob)obj).value;
      
      if (me.length != him.length)
        return false;
        
      for(int i=0; i<me.length; ++i)
        if (me[i] != him[i])
          return false;
          
      return true;
    }
    return false;
  }

  /**
   * Compares the provided byte array to the contents of
   * the bblob object.
   *
   * @since Niagara 4.4
   * @param bytes to compare to blob content
   * @return true if they are the same, otherwise, false
   */
  public boolean bytesEqual(byte[] bytes)
  {
    return Arrays.equals(value, bytes);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * BBlob is serialized as:
   *  - 4 byte magic number 0xB10B 
   *  - 4 byte length
   *  - byte array value
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(0xB10B);
    out.writeInt(value.length);
    out.write(value, 0, value.length);
  }
  
  /**
   * BBlob is unserialized as 4 byte magic
   * number (0xB10BxBBBB), 4 byte length, and
   * byte array value.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    int magic = in.readInt();
    if (magic != 0xB10B)
      throw new IOException("Invalid BBlob magic number 0x" + Integer.toHexString(magic));
      
    int len = in.readInt();
    if (len == 0) return DEFAULT;
    try
    {
      byte[] buf = new byte[len];
      in.readFully(buf, 0, len);
      return new BBlob(buf);
    }
    catch(OutOfMemoryError e)
    {
      throw new IOException("Invalid byte array length: " + len); 
    }
  }

  /**
   * Write the simple in text format using
   * Base 64 encoding.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    return Base64.getEncoder().encodeToString(value);
  }

  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    return new BBlob(Base64.getDecoder().decode(s));
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final BBlob DEFAULT = new BBlob(new byte[0]);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBlob.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  byte[] value;
  int hashCode;
  
}
