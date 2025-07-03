/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.io;

import javax.baja.sys.BajaRuntimeException;
import java.math.BigInteger;

/**
 *  The LonOutputStream is an extension of the standard
 *  ByteArrayOutputStream for use in building LonMessage 
 *  messages.  It contains utility methods to convert
 *  various types and pass them to the data stream.
 *  
 *
 * @author    Robert Adams
 * @creation  5 Jan 01
 * @version   $Revision: 5$ $Date: 9/18/01 9:49:38 AM$
 * @since     Niagara 3.0
 */
public class LonOutputStream 
  extends java.io.ByteArrayOutputStream  
{
  /**
   *  Construct a LonOutputStream with no initial 
   *  capacity
   */
  public LonOutputStream()
  {
    super();
  }

  /**
   *  Construct a LonOutputStream with the specified
   *  initial capacity.
   *  @param size  initial capacity in bytes
   */
  public LonOutputStream(int size)
  {
    super(size);
  }

  /**
   *  Writes the entire array to the output stream.    
   */
  public void writeByteArray(byte[] byteArray)
  {
    write(byteArray, 0, byteArray.length);
  }

  /**
   *  Writes the specifiec number of bytes to the output stream
   *  from the given array. Pad with 0's to fill out count.    
   */
  public void writeByteArray(byte[] byteArray, int count)
  {
    int copyCnt = (byteArray.length > count) ? count : byteArray.length ;

    write(byteArray, 0, copyCnt);
    for ( ; copyCnt < count ; copyCnt++)
    {
      write(0);
    }
  }

  /**
   *  Writes the specified number of characters to the output 
   *  stream from the given String. Pad with 0's to fill out count.    
   */
  public void writeCharArray(String str, int count)
  {
    char[] a = str.toCharArray();
    for ( int i = 0 ; i < count ; i++)
    {
      if(i<a.length)
        write(a[i]);
      else
        write(0);
    }
  }
  
  /**
   *  Writes a null terminated string to the output stream.
   */
  public void writeString(String str)
  {
    char[] a = str.toCharArray();
    for ( int i=0 ; i < a.length ; i++) write(a[i]);
    write(0);
  }

  /**
   * Write boolean value to a bit field in the output stream. 
   * If true write 1 else write 0. 
   * @see javax.baja.lonworks.io.LonOutputStream#writeBit
   */
  public void writeBooleanBit(boolean val, int byteOffset, int bitOffset, int bitCount)
  {
    writeBit((val ? 1 : 0), byteOffset, bitOffset, bitCount);
  }

  /**
   * Write an unsigned value to a bit field. Multiple calls to 
   * writeBit with the same byteOffset is supported.  <p>
   * Does not support bit fields which overlap multiple bytes.<p>
   *
   * @param byteOffset Offset from first byte in output stream
   * @param bitOffset  Number of positions (0-7) to shift value. O if value in lsb.
   * @param bitCount  The number of bits in bit field
   */
  public void writeBit(int val, int byteOffset, int bitOffset, int bitCount)
  {
    int bytOffset = bitFieldMark + byteOffset;

    while(count <= bytOffset)
    {
      write(0);
    }

    int mask = 1;
    while(--bitCount > 0) mask = (mask << 1) | 0x0001;
    
    // Sanity check
    if(bytOffset<0 || bytOffset >= buf.length)
    {
      System.out.println("*********\nwriteBit byteOffset = " + bytOffset + "  len = " + buf.length);
      Thread.dumpStack();
      return;
    }
    
    buf[bytOffset] |= ((val & mask) << bitOffset);
  }
   
  /**
   * Write a signed value to a bit field. Multiple calls to 
   * writeBit with the same byteOffset is supported.  <p>
   * Does not support bit fields which overlap multiple bytes.<p>
   *
   * @param byteOffset Offset from first byte in output stream
   * @param bitOffset  Number of positions (0-7) to shift value. O if value in lsb.
   * @param bitCount  The number of bits in bit field
   */
  public void writeSignedBit(int val, int byteOffset, int bitOffset, int bitCount)
  {
    writeBit(val, byteOffset, bitOffset, bitCount);
  }
    
  /**
   * Write a 8 bit boolean value to the output stream. 
   * If true write 1 else write 0.
   */
  public void writeBoolean(boolean b)
  {
    write(b ? 1 : 0);
  }

  /**
   * Write a 16 bit signed value to the output stream.
   * Valid values (-32768 to 32767)
   */
  public void writeSigned16(int l)
  {
    int highByte = (l >> 8) & 0xFF;
    int lowByte = l & 0xFF;

    write(highByte);
    write(lowByte);
  }

  /**
   * Write a 16 bit unsigned value to the output stream
   * Valid values (0 to 65535)
   */
  public void writeUnsigned16(int l)
  {
    int highByte = (l >> 8) & 0xFF;
    int lowByte = l & 0xFF;

    write(highByte);
    write(lowByte);
  }


  /**
   * Write a 8 bit signed value to the output stream
   * Valid values (-128 to 127)
   */
  public void writeSigned8(int i)
  {
    write(i & 0xFF);
  }

  /**
   * Write a 8 bit unsigned value to the output stream
   * Valid values (0 to 255)
   */
  public void writeUnsigned8(int i)
  {
    write(i & 0xFF);    
  }

  /**
   *  Writes a 32-bit value to the output stream.
   */
  public void writeSigned32(int i)
  {
    write((i & 0xFF000000) >> 24);
    write((i & 0x00FF0000) >> 16);
    write((i & 0x0000FF00) >> 8);
    write(i & 0x000000FF);
  }

  /**
   *  Writes an unsigned 32-bit value to the output stream.
   */
  public void writeUnsigned32(long i)
  {
    write((int)(i & 0x0FF000000) >> 24);
    write((int)(i & 0x00FF0000) >> 16);
    write((int)(i & 0x0000FF00) >> 8);
    write((int)(i & 0x000000FF));
  }

  /**
   *  Writes a 64-bit value to the output stream.
   */
  public void writeSigned64(long i)
  {
    write((int)((i & 0xFF00000000000000L) >> 56));
    write((int)((i & 0x00FF000000000000L) >> 48));
    write((int)((i & 0x0000FF0000000000L) >> 40));
    write((int)((i & 0x000000FF00000000L) >> 32));
    write((int)((i & 0x00000000FF000000L) >> 24));
    write((int)((i & 0x0000000000FF0000L) >> 16));
    write((int)((i & 0x000000000000FF00L) >>  8));
    write((int)(i & 0x00000000000000FFL) );
  }

  /**
   *  Writes a long val as 64-bit unsigned value to the output stream.
   */
  public void writeUnsigned64(BigInteger val)
  {
    byte[] a = val.toByteArray();
    int len = a.length;
    int msb = 0;
    
    // Skip any leading zeros in excess of 64bits
    while(a[msb]==0 && len-msb>8) ++msb;
    
    if(len-msb>8) throw new BajaRuntimeException("LonOutputStream.writeUnsigned64() error:" + val + " greater than 64 bits.");
    
    // Pad msbytes as needed
    for(int i=0 ; i<(8-len+msb) ; ++i)  write(0);
    // msByte is a[0]
    for(int i=msb ; i<len ; ++i) write(a[i]);
  }


  /**
   *  Writes a 32-bit value to the output stream,
   *  high-order byte first.
   */
  public void writeFloat(float value)
  {
    int bitView = Float.floatToIntBits( value);

    write((bitView & 0xFF000000) >> 24);
    write((bitView & 0x00FF0000) >> 16);
    write((bitView & 0x0000FF00) >> 8);
    write(bitView & 0x000000FF);
  }
  
  /**
   *  Writes a 32-bit value to the output stream,
   *  high-order byte first.
   */
  public void writeDouble(double value)
  {
    long bitView = Double.doubleToLongBits(value);

    write((int)((bitView & 0xFF00000000000000L) >> 56));
    write((int)((bitView & 0x00FF000000000000L) >> 48));
    write((int)((bitView & 0x0000FF0000000000L) >> 40));
    write((int)((bitView & 0x000000FF00000000L) >> 32));
    write((int)((bitView & 0x00000000FF000000L) >> 24));
    write((int)((bitView & 0x0000000000FF0000L) >> 16));
    write((int)((bitView & 0x000000000000FF00L) >> 8));
    write((int)(bitView & 0x00000000000000FFL));
  }
  
  /**
   * For internal use.<p>
   * Used to mark bit field  count at entry in nested LonData structs
   * to allow correct handling of byteOffsets. 
   */
  public int setBitFieldMark()
  {
    int orig = bitFieldMark;
    bitFieldMark = count;
    return orig;
  }
  /**
   * For internal use.<p>
   * Used to reset bit field count at exit from nested LonData structs
   * to allow correct handling of byteOffsets. 
   */
  public void  resetBitFieldMark(int orig)
  {
    bitFieldMark = orig;
  }
  int bitFieldMark = 0;
  
  /**
   * Write bytes to ensure next byte writes to position.
   */
  public void setPosition(int position)
  {
    while(count < position)
    {
      write(0);
    }
  }


}