/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The TypedOutputStream is an extension of the standard ByteArrayOutputStream
 * for use in building messages.  It contains utility methods to convert various
 * types and pass them to the data stream.
 *
 * @author Robert Adams
 * @creation 30 Jan 2013
 * @since Niagara 3.7.104
 */
public class TypedOutputStream
  extends java.io.ByteArrayOutputStream
{
  /**
   * Construct a TypedOutputStream with no initial capacity
   */
  public TypedOutputStream()
  {
    super();
  }

  /**
   * Construct a TypedOutputStream with the specified initial capacity.
   *
   * @param size initial capacity in bytes
   */
  public TypedOutputStream(int size)
  {
    super(size);
  }

  /**
   * Construct a TypedOutputStream which wraps the specified byte array
   *
   * @param b supplied byte array
   */
  public TypedOutputStream(byte[] b)
  {
    this.buf = b;
    this.count = 0;
  }


  /**
   * Writes the entire array to the output stream.
   */
  public void writeByteArray(byte[] byteArray)
  {
    write(byteArray, 0, byteArray.length);
  }

  /**
   * Writes the specifiec number of bytes to the output stream from the given
   * array. Pad with 0's to fill out count.
   */
  public void writeByteArray(byte[] byteArray, int count)
  {
    int copyCnt = (byteArray.length > count) ? count : byteArray.length;

    write(byteArray, 0, copyCnt);
    for (; copyCnt < count; copyCnt++)
    {
      write(0);
    }
  }

  /**
   * Writes the specified number of characters to the output stream from the
   * given String. Pad with 0's to fill out count.
   */
  public void writeCharArray(String str, int count)
  {
    char[] a = str.toCharArray();
    for (int i = 0; i < count; i++)
    {
      if (i < a.length)
      {
        write(a[i]);
      }
      else
      {
        write(0);
      }
    }
  }

  /**
   * Writes a null terminated string to the output stream.
   */
  public void writeString(String str)
  {
    char[] a = str.toCharArray();
    for (int i = 0; i < a.length; i++)
    {
      write(a[i]);
    }
    write(0);
  }

  /**
   * Write boolean value to a bit field in the output stream. If true write 1
   * else write 0. <p>
   *
   * @see com.tridium.ndriver.io.TypedOutputStream#writeBit
   */
  public void writeBooleanBit(boolean val, int byteOffset, int bitOffset, int bitCount)
  {
    writeBit((val ? 1 : 0), byteOffset, bitOffset, bitCount);
  }

  /**
   * Write an unsigned value to a bit field. Multiple calls to writeBit with the
   * same byteOffset is supported.  <p> Bitfield can not span more than 32 bits
   * (bitCount+bitOffset<=32).
   *
   * @param byteOffset Offset from first byte in output stream or bitFieldMark
   *                   (if multi byte bit field, specify offset of lsb)
   * @param bitOffset  Number of positions (0-31) to shift value. O if value in
   *                   lsb.
   * @param bitCount   The number of bits in bit field
   */
  public void writeBit(int val, int byteOffset, int bitOffset, int bitCount)
  {
    if (bitCount + bitOffset > 32)
    {
      throw new RuntimeException("bitfield exceeds maximum of 32. " + bitCount + bitOffset);
    }

    int affectedByteCnt = ((bitCount + bitOffset - 1) / 8) + 1;

    int finalPos = bitFieldMark + byteOffset;  // index in buf of least significant byte
    int startPos = finalPos - affectedByteCnt + 1; // index in buf of most significant byte
    if (finalPos >= buf.length)
    {
      throw new RuntimeException("OutOfRange byteOffset in readBit. " + byteOffset + ":" + count);
    }
    if (startPos < bitFieldMark)
    {
      throw new RuntimeException("Invalid byteOffset/bitcount - would read beyond bbeginningof bit fields.");
    }

    // Add enough bytes to contain field - count is finalPos+1
    while (count <= finalPos)
    {
      write(0);
    }

    // Get Mask an apply to value
    int mask = 1;
    while (--bitCount > 0)
    {
      mask = (mask << 1) | 0x0001;
    }
    val = val & mask;

    // apply bitOffset to value
    val = val << bitOffset;

    // write to all affect bytes buf
    int pos = startPos;
    for (int i = 0; i < affectedByteCnt; ++i)
    {
      buf[pos++] |= ((val >> (affectedByteCnt - i - 1) * 8) & 0x0ff);
    }
  }

  /**
   * Write a signed value to a bit field.  <p>
   *
   * @see com.tridium.ndriver.io.TypedOutputStream#writeBit
   */
  public void writeSignedBit(int val, int byteOffset, int bitOffset, int bitCount)
  {
    writeBit(val, byteOffset, bitOffset, bitCount);
  }

  /**
   * Write a value to the upper or lower nibble at the specified byteOffset.<p>
   *
   * @param byteOffset Offset from first byte in output stream
   * @param upper      flag to indicate upper nibble (offset 4) - if false will
   *                   read lower nibble (offset 0)
   * @since 3.8.38.1, 3.7.202, 3.6.503
   */
  public void writeNibble(int val, int byteOffset, boolean upper)
  {
    writeBit(val, byteOffset, (upper ? 4 : 0), 4);
  }

  /**
   * Write a 8 bit boolean value to the output stream. If true write 1 else
   * write 0.
   */
  public void writeBoolean(boolean b)
  {
    write(b ? 1 : 0);
  }

  /**
   * Write a 16 bit signed value to the output stream. Valid values (-32768 to
   * 32767)
   */
  public void writeSigned16(int l)
  {
    int highByte = (l >> 8) & 0xFF;
    int lowByte = l & 0xFF;

    write(highByte);
    write(lowByte);
  }

  /**
   * Write a 16 bit unsigned value to the output stream Valid values (0 to
   * 65535)
   */
  public void writeUnsigned16(int l)
  {
    int highByte = (l >> 8) & 0xFF;
    int lowByte = l & 0xFF;

    write(highByte);
    write(lowByte);
  }


  /**
   * Write a 8 bit signed value to the output stream Valid values (-128 to 127)
   */
  public void writeSigned8(int i)
  {
    write(i & 0xFF);
  }

  /**
   * Write a 8 bit unsigned value to the output stream Valid values (0 to 255)
   */
  public void writeUnsigned8(int i)
  {
    write(i & 0xFF);
  }

  /**
   * Writes a 32-bit value to the output stream.
   */
  public void writeSigned32(int i)
  {
    write((i & 0xFF000000) >> 24);
    write((i & 0x00FF0000) >> 16);
    write((i & 0x0000FF00) >> 8);
    write(i & 0x000000FF);
  }

  /**
   * Writes a 64-bit value to the output stream.
   *
   * @since 4.2.35
   */
  public void writeSigned64(long lval)
  {
    write((int)((lval & 0xFF00000000000000L) >> 56));
    write((int)((lval & 0x00FF000000000000L) >> 48));
    write((int)((lval & 0x0000FF0000000000L) >> 40));
    write((int)((lval & 0x000000FF00000000L) >> 32));
    write((int)((lval & 0x00000000FF000000L) >> 24));
    write((int)((lval & 0x0000000000FF0000L) >> 16));
    write((int)((lval & 0x000000000000FF00L) >> 8));
    write((int)(lval & 0x00000000000000FFL));
  }

  /**
   * Writes an unsigned 32-bit value to the output stream.
   */
  public void writeUnsigned32(long i)
  {
    write((int)(i & 0x0FF000000) >> 24);
    write((int)(i & 0x00FF0000) >> 16);
    write((int)(i & 0x0000FF00) >> 8);
    write((int)(i & 0x000000FF));
  }

  /**
   * Writes a 32-bit value to the output stream, high-order byte first.
   */
  public void writeFloat(float value)
  {
    int bitView = Float.floatToIntBits(value);

    write((bitView & 0xFF000000) >> 24);
    write((bitView & 0x00FF0000) >> 16);
    write((bitView & 0x0000FF00) >> 8);
    write(bitView & 0x000000FF);
  }

  /**
   * Writes a 64-bit value to the output stream, high-order byte first.
   */
  public void writeDouble(double value)
  {
    long bitView = Double.doubleToLongBits(value);

    writeSigned64(bitView);
  }

  /**
   * Writes a float value as an unsigned8.  If value is NaN then write invalid,
   * otherwise multiple by scale and write results as an int.
   *
   * @since 3.7.105
   */
  public void writeUnsigned8Float(float value, int invalid, int scale)
  {
    int tmp = (int)(Float.isNaN(value) ? invalid : value * scale);
    writeUnsigned8(tmp);
  }

  /**
   * Writes a float value as an unsigned16.  If value is NaN then write invalid,
   * otherwise multiple by scale and write results as an int.
   *
   * @since 3.7.105
   */
  public void writeUnsigned16Float(float value, int invalid, int scale)
  {
    int tmp = (int)(Float.isNaN(value) ? invalid : value * scale);
    writeUnsigned16(tmp);
  }

  /**
   * Writes a float value as an signed8.  If value is NaN then write invalid,
   * otherwise multiple by scale and write results as an int.
   *
   * @since 3.7.105
   */
  public void writeSigned8Float(float value, int invalid, int scale)
  {
    int tmp = (int)(Float.isNaN(value) ? invalid : value * scale);
    writeSigned8(tmp);
  }

  /**
   * Writes a float value as an signed16.  If value is NaN then write invalid,
   * otherwise multiple by scale and write results as an int.
   *
   * @since 3.7.105
   */
  public void writeSigned16Float(float value, int invalid, int scale)
  {
    int tmp = (int)(Float.isNaN(value) ? invalid : value * scale);
    writeSigned16(tmp);
  }

  /**
   * For internal use.<p> Used to mark bit field count at entry in nested
   * structs to allow correct handling of byteOffsets.
   */
  public int setBitFieldMark()
  {
    int orig = bitFieldMark;
    bitFieldMark = count;
    return orig;
  }

  /**
   * For internal use.<p> Used to reset bit field count at exit from structs to
   * allow correct handling of byteOffsets.
   */
  public void resetBitFieldMark(int orig)
  {
    bitFieldMark = orig;
  }

  int bitFieldMark = 0;

  /**
   * Write bytes to ensure next byte writes to specified position. If position
   * is less than current byte count it will have no effect.
   */
  public void setPosition(int position)
  {
    while (count < position)
    {
      write(0);
    }
  }

  /**
   * Transfer the contents of this TypedOutputStream to the specified
   * OutputStream.
   *
   * @since 3.8.38.1, 3.7.202, 3.6.503
   */
  public void toOutputStream(OutputStream out)
    throws IOException
  {
    out.write(buf, 0, count);
  }
}