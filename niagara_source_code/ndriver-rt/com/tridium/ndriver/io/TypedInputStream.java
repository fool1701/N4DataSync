/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.io;

import javax.baja.sys.BajaRuntimeException;

/**
 * The TypedInputStream is an extension of the standard ByteArrayInputStream for
 * use in parsing messages.  It contains utility methods to extract various
 * types from the data stream.
 *
 * @author Robert Adams
 * @creation 30 Jan 2013
 * @since Niagara 3.7.104
 */
public class TypedInputStream
  extends java.io.ByteArrayInputStream
{
  /**
   * Construct an input stream that reads data from the given byte array.
   */
  public TypedInputStream(byte[] buf)
  {
    super(buf);
  }

  /**
   * Construct an input stream that reads data from the given byte array.
   */
  public TypedInputStream(byte[] buf, int offset, int length)
  {
    super(buf, offset, length);
  }
  
  /**
   * Reads a 8 bit boolean value from the input stream.
   *
   * @return If bit field=0 return false else return true.
   */
  public boolean readBoolean()
  {
    if (read() == 0)
    {
      return false;
    }
    return true;
  }

  /**
   * Reads a 32 bit value from the input stream.
   */
  public int readSigned32()
  {
    int byte3 = read() & 0xFF;
    int byte2 = read() & 0xFF;
    int byte1 = read() & 0xFF;
    int byte0 = read() & 0xFF;

    return ((byte3 << 24) | (byte2 << 16) | (byte1 << 8) | byte0);
  }

  /**
   * Reads a 64 bit value from the input stream.
   *
   * @since 4.2.35
   */
  public long readSigned64()
  {
    long v = read();
    for (int i = 0; i < 7; i++)
    {
      v = (v << 8) | read();
    }
    return v;
  }
  
  /**
   * Reads a 32 bit unsigned value from the input stream.
   */
  public long readUnsigned32()
  {
    long byte3 = read() & 0xFF;
    long byte2 = read() & 0xFF;
    long byte1 = read() & 0xFF;
    long byte0 = read() & 0xFF;

    return ((byte3 << 24) | (byte2 << 16) | (byte1 << 8) | byte0) & 0x0FFFFFFFF;
  }

  /**
   * Read an 16 bit unsigned value from the input stream.
   *
   * @return int (0 to 65535)
   */
  public int readUnsigned16()
  {
    //  Converts an array of bytes to an integer
    int highByte = read() & 0xFF;
    int lowByte = read() & 0xFF;

    return (highByte << 8) | lowByte;
  }

  /**
   * Read a 16 bit signed value from the input stream
   *
   * @return int (-32768 to 32767)
   */
  public int readSigned16()
  {
    int highByte = read();
    int lowByte = read() & 0xFF;
    boolean signBit = false;
    int temp = 0;

    if ((highByte & 0x80) != 0)
    {
      signBit = true;
    }

    temp = highByte << 8 | lowByte;

    if (signBit)
    {
      return temp | 0xFFFF0000;
    }
    else
    {
      return temp;
    }
  }

  /**
   * Read an 8 bit signed value from the input stream
   *
   * @return int (-128 to 127)
   */
  public int readSigned8()
  {
    int highByte = read();

    if ((highByte & 0x80) != 0)
    {
      return highByte | 0xFFFFFF00;
    }
    else
    {
      return highByte;
    }
  }

  /**
   * Read an 8 bit unsigned value from the input stream
   *
   * @return int (0 to 255)
   */
  public int readUnsigned8()
  {
    return (read() & 0x00FF);
  }


  /**
   * Read an unsigned value from a bit field. The stream pos will be set to
   * byteOffset to allow multiple readBit calls to access bit fields in the same
   * byte. <p> Bitfield can not span more than 32 bits (bitCount+bitOffset<=32).
   *
   * @param byteOffset Offset from first byte in output stream (if multi byte
   *                   bit field, specify offset of lsb)
   * @param bitOffset  Number of positions (0-31) to shift value. O if value in
   *                   lsb.
   * @param bitCount   The number of bits in bit field
   */
  public int readBit(int byteOffset, int bitOffset, int bitCount)
  {
    if ((byteOffset >= count) || (byteOffset < 0))
    {
      throw new RuntimeException("OutOfRange byteOffset in readBit. " + byteOffset + ":" + count);
    }
    if (bitCount + bitOffset > 32)
    {
      throw new RuntimeException("bitCount exceeds maximum of 32. " + bitCount);
    }

    int extraByteCnt = (bitCount + bitOffset - 1) / 8;
    if (byteOffset - extraByteCnt < 0)
    {
      throw new RuntimeException("Invalid byteOffset/bitcount - would read beyond beginning of bit fields.");
    }

    // Reposition stream per byteOffset - there may be multiple reads to
    // the same byte.
    pos = bitFieldMark + byteOffset - extraByteCnt;
    int b = read();
    for (int i = 0; i < extraByteCnt; ++i)
    {
      b = (b << 8) | read();
    }
    b = b >> bitOffset;
    int mask = 1;
    while (--bitCount > 0)
    {
      mask = (mask << 1) | 0x0001;
    }
    return b & mask;
  }

  /**
   * Read an signed value from a bit field.<p>
   *
   * @see com.tridium.ndriver.io.TypedInputStream#readBit
   */
  public int readSignedBit(int byteOffset, int bitOffset, int bitCount)
  {
    int n = readBit(byteOffset, bitOffset, bitCount);

    // Check for positive numbers
    int signBit = 1 << (bitCount - 1);
    if ((n & signBit) == 0)
    {
      return n;
    }

    // Convert negative number
    int mask = 0;
    for (int i = 1; i < bitCount; i++)
    {
      mask = (mask << 1) + 1;
    }
    return (n & mask) - signBit;
  }

  /**
   * Read the upper or lower nibble at the specified byteOffset as an unsigned
   * int.
   *
   * @param byteOffset Offset from first byte in output stream
   * @param upper      flag to indicate upper nibble (offset 4) - if false will
   *                   read lower nibble (offset 0)
   * @since 3.8.38.1, 3.7.202, 3.6.503
   */
  public int readNibble(int byteOffset, boolean upper)
  {
    return readBit(byteOffset, (upper ? 4 : 0), 4);
  }

  /**
   * Read the upper or lower nibble at the specified byteOffset as an signed
   * int.
   *
   * @param byteOffset Offset from first byte in output stream
   * @param upper      flag to indicate upper nibble (offset 4) - if false will
   *                   read lower nibble (offset 0)
   * @since 3.8.38.1, 3.7.202, 3.6.503
   */
  public int readSignedNibble(int byteOffset, boolean upper)
  {
    return readSignedBit(byteOffset, (upper ? 4 : 0), 4);
  }

  /**
   * Read the bit field and return the result as a boolean.
   *
   * @return If bit field=0 return false else return true.
   * @see com.tridium.ndriver.io.TypedInputStream#readBit
   */
  public boolean readBooleanBit(int byteOffset, int bitOffset, int bitCount)
  {
    int val = readBit(byteOffset, bitOffset, bitCount);
    return val > 0;
  }

  /**
   * Reads in a null terminated string from the input stream. Discard null
   * termination '\0'. Next read begins at byte following null termination.
   */
  public String readString()
  {
    StringBuilder sb = new StringBuilder();
    char c8;

    while ((c8 = (char)read()) != '\0')
    {
      sb.append(c8);
    }

    return sb.toString();
  }

  /**
   * Reads no more than the specified number of chars from the input stream to
   * create a string. If len is greater then the number of available chars then
   * it is reduced to that number.<p>
   *
   * @param len the maximum number of chars to read from stream..
   */
  public String readCharArray(int len)
  {
    if (len > available())
    {
      len = available();
    }

    StringBuilder sb = new StringBuilder();
    char c8;
    boolean terminated = false;
    // Read len characters - append chars until null termination found.
    for (int i = 0; i < len; i++)
    {
      c8 = (char)read();
      if (c8 == '\0')
      {
        terminated = true;
      }
      if (!terminated)
      {
        sb.append(c8);
      }
    }

    return sb.toString();
  }

  /**
   * Reads no more than the specified number of bytes from the input stream. If
   * len is greater then the number of available bytes then it is reduced to
   * that number.<p>
   *
   * @param len the maximum number of bytes to read from stream.
   */
  public byte[] readByteArray(int len)
  {
    if (len > available())
    {
      len = available();   //throw new IOException("len exceeds available bytes"); 
    }

    byte[] a = new byte[len];

    for (int i = 0; i < len; i++)
    {
      a[i] = (byte)read();
    }

    return a;
  }

  /**
   * Reads the remaining bytes in the stream.
   */
  public byte[] readByteArray()
  {
    return readByteArray(available());
  }

  /**
   * Read a 32 bit float value from the input stream.
   */
  public float readFloat()
  {
    return Float.intBitsToFloat(readSigned32());
  }

  /**
   * Read a 64 bit double value from the input stream.
   */
  public double readDouble()
  {
    long v = read();
    for (int i = 0; i < 7; i++)
    {
      v = (v << 8) | read();
    }
    return Double.longBitsToDouble(v);
  }

  /**
   * Read an unsigned8 field, divide by scale and return as a float. If the read
   * value == invalid then return Float.NaN
   *
   * @since 3.7.105
   */
  public float readUnsigned8Float(int invalid, int scale)
  {
    int tmp = readUnsigned8();
    return (tmp != invalid) ? tmp / scale : Float.NaN;
  }

  /**
   * Read an unsigned16 field, divide by scale and return as a float. If the
   * read value == invalid then return Float.NaN
   *
   * @since 3.7.105
   */
  public float readUnsigned16Float(int invalid, int scale)
  {
    int tmp = readUnsigned16();
    return (tmp != invalid) ? tmp / scale : Float.NaN;
  }

  /**
   * Read an signed8 field, divide by scale and return as a float. If the read
   * value == invalid then return Float.NaN
   *
   * @since 3.7.105
   */
  public float readSigned8Float(int invalid, int scale)
  {
    int tmp = readSigned8();
    return (tmp != invalid) ? tmp / scale : Float.NaN;
  }

  /**
   * Read an signed16 field, divide by scale and return as a float. If the read
   * value == invalid then return Float.NaN
   *
   * @since 3.7.105
   */
  public float readSigned16Float(int invalid, int scale)
  {
    int tmp = readSigned16();
    return (tmp != invalid) ? tmp / scale : Float.NaN;
  }

  /**
   * Reposition stream at beginning of message.
   */
  public void reset()
  {
    pos = 0;
  }

  /**
   * Reposition stream at specified offset.
   */
  public void reset(int position)
  {
    pos = position;
  }

  /**
   * Set the value of bit field mark to current position and return previous bit
   * field mark.
   */
  public int setBitFieldMark()
  {
    int orig = bitFieldMark;
    bitFieldMark = pos;
    return orig;
  }

  /**
   * Reset bit field mark.
   */
  public void resetBitFieldMark(int orig)
  {
    bitFieldMark = orig;
  }

  int bitFieldMark = 0;

  public int position()
  {
    return pos;
  }

  /**
   * Check for available byte and throw exception if none available.
   */
  @Override
  public int read()
  {
    if (pos >= count)
    {
      throw new BajaRuntimeException("end of buf encountered");
    }
    return super.read();
  }
}