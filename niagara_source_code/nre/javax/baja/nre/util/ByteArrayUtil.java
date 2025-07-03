/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.io.PrintWriter;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import com.tridium.nre.security.SecretBytes;


/**
 * Byte array utilities.
 *
 * @author    Brian Frank
 * @creation  5 Feb 01
 * @version   $Revision: 10$ $Date: 5/13/11 5:06:53 PM EDT$
 * @since     Baja 1.0
 */
public class ByteArrayUtil
{

////////////////////////////////////////////////////////////////
// Buffer Utils
////////////////////////////////////////////////////////////////

  /**
   * Create a copy of the specified byte array.
   */
  public static byte[] clone(byte[] a)
  {
    byte[] clone = new byte[a.length];
    System.arraycopy(a, 0, clone, 0, a.length);
    return clone;
  }

  /**
   * Copy the contents of the source byte array
   * into the target byte array.
   *
   * @throws ArrayIndexOutOfBoundsException if the target
   *   array is not as big or bigger than the
   *   source array.
   */
  public static void copy(byte[] source, byte[] target)
  {
    System.arraycopy(source, 0, target, 0, source.length);
  }

  /**
   * Return true if the two specified byte arrays
   * have equal lengths and contents.
   */
  public static boolean equals(byte[] a1, byte[] a2)
  {
    int len = a1.length;
    if(len != a2.length) return false;
    for(int i=0 ; i<len; ++i)
      if( a1[i] != a2[i] ) return false;
    return true;
  }

  public static void memset(byte [] buf, byte value)
  {
    buf[0] = value;
    for (int i = 1; i < buf.length; i += i)
    {
      System.arraycopy( buf, 0, buf, i, ((buf.length - i) < i) ? (buf.length - i) : i);
    }
  }

////////////////////////////////////////////////////////////////
// Read
////////////////////////////////////////////////////////////////

  /**
   * Read an unsigned byte from the byte
   * array at the given offset.
   */
  public static int readUnsignedByte(byte[] buf, int offset)
  {
    return buf[offset] & 0xFF;
  }

  /**
   * Read an signed short from the byte
   * array at the given offset using network
   * byte ordering.
   */
  public static short readShort(byte[] buf, int offset)
  {
    return (short)readUnsignedShort(buf, offset);
  }

  /**
   * Read an unsigned short from the byte
   * array at the given offset using network byte
   * ordering.
   */
  public static int readUnsignedShort(byte[] buf, int offset)
  {
    int b0 = readUnsignedByte(buf, offset+0);
    int b1 = readUnsignedByte(buf, offset+1);
    return ((b0 << 8) + (b1 << 0));
  }

  /**
   * Read a 4 byte integer from the byte
   * array at the given offset using network
   * byte ordering.
   */
  public static int readInt(byte[] buf, int offset)
  {
    int b0 = readUnsignedByte(buf, offset+0);
    int b1 = readUnsignedByte(buf, offset+1);
    int b2 = readUnsignedByte(buf, offset+2);
    int b3 = readUnsignedByte(buf, offset+3);
    return ((b0 << 24) + (b1 << 16) + (b2 << 8) + (b3 << 0));
  }

  /**
   * Read a 8 byte long from the byte
   * array at the given offset using network
   * byte ordering.
   */
  public static long readLong(byte[] buf, int offset)
  {
    return (((long)readInt(buf, offset)) << 32) +
           (readInt(buf, offset+4) & 0xFFFFFFFFL);
  }

////////////////////////////////////////////////////////////////
// Write
////////////////////////////////////////////////////////////////

  /**
   * Write the specified byte into the byte array at
   * the give offset.
   * @return offset + 1
   */
  public static int writeByte(byte[] buf, int offset, int value)
  {
    buf[offset] = (byte)(value & 0xFF);
    return offset+1;
  }

  /**
   * Write the 2 bytes at specified offset with
   * the given short value.
   * @return offset + 2
   */
  public static int writeShort(byte[] buf, int offset, int value)
  {
    buf[offset+0] = (byte)((value >>> 8) & 0xFF);
    buf[offset+1] = (byte)((value >>> 0) & 0xFF);
    return offset + 2;
  }

  /**
   * Write the 4 bytes at the specified offset with
   * the given integer value.
   * @return offset + 4
   */
  public static int writeInt(byte[] buf, int offset, int value)
  {
    buf[offset+0] = (byte)((value >>> 24) & 0xFF);
    buf[offset+1] = (byte)((value >>> 16) & 0xFF);
    buf[offset+2] = (byte)((value >>> 8)  & 0xFF);
    buf[offset+3] = (byte)((value >>> 0)  & 0xFF);
    return offset + 4;
  }

  /**
   * Write the 8 bytes at the specified offset with
   * the given long value.
   * @return offset + 8
   */
  public static int writeLong(byte[] buf, int offset, long value)
  {
    buf[offset+0] = (byte)((value >>> 56) & 0xFF);
    buf[offset+1] = (byte)((value >>> 48) & 0xFF);
    buf[offset+2] = (byte)((value >>> 40) & 0xFF);
    buf[offset+3] = (byte)((value >>> 32) & 0xFF);
    buf[offset+4] = (byte)((value >>> 24) & 0xFF);
    buf[offset+5] = (byte)((value >>> 16) & 0xFF);
    buf[offset+6] = (byte)((value >>> 8)  & 0xFF);
    buf[offset+7] = (byte)((value >>> 0)  & 0xFF);
    return offset + 8;
  }

////////////////////////////////////////////////////////////////
// Hex String
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
// Hex String
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>toHexString(b, 0, b.length)</code>.
   */
  public static String toHexString(byte[] b)
  {                          
    if (b == null) return "null";
    return toHexString(b, 0, b.length, "");
  }

  public static String toHexString(byte[] b, String delimiter)
  {                          
    if (b == null) return "null";
    return toHexString(b, 0, b.length, delimiter);
  }
  
  /**
   * Return the byte array as a hex string.
   */
  public static String toHexString(byte[] b, int off, int len)
  {
    if (b == null) return "null";
    return toHexString(b, off, len, "");
  }

  public static String toHexString(byte[] b, int off, int len, String delimiter)
  {
    boolean first = true;
    
    if (b == null) return "null";
    if (b.length == 0) return "";

    if (off < 0 || len < 0 || off >= b.length || off + len > b.length)
      throw new IllegalArgumentException();

    if (len == 0) return "";

    StringBuilder s = new StringBuilder();
    for (int i=off; i<off+len; ++i)
    {
      if (!first)
        s.append(delimiter);
      s.append(TextUtil.byteToHexString(b[i] & 0xFF));      
      first = false;
    }
    return s.toString();
  }

  /**
   * Convert a hex string into the equivalent byte array.
   */
  public static byte[] hexStringToBytes(String hex)
  {
    byte[] b = new byte[hex.length()/2];

    for( int i = 0; i < b.length; i++ )
    {
      b[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2),16);
    }
    
    /*for (int i=0, j=0; i<b.length; i++, j+=2)
    {
      int high = TextUtil.hexCharToInt(hex.charAt(j));
      int low  = TextUtil.hexCharToInt(hex.charAt(j+1));
      b[i] = (byte)((high << 4) | low);
    }*/
    
    return b;
  }

////////////////////////////////////////////////////////////////
// Hex Dumps
////////////////////////////////////////////////////////////////

  /**
   * Dump a byte array to standard out.
   */
  public static void hexDump(byte[] b)
  {
    PrintWriter out = new PrintWriter(System.out);
    hexDump("", out, b, 0, b.length);
    out.flush();
  }
  
  public static void hexDump(String linePrefix, byte[] b)
  {
    PrintWriter out = new PrintWriter(System.out);
    hexDump(linePrefix, out, b, 0, b.length);
    out.flush();    
  }

  /**
   * Dump a slice of a byte array to standard out.
   */
  public static void hexDump(byte[] b, int offset, int length)
  {
    PrintWriter out = new PrintWriter(System.out);
    hexDump("", out, b, offset, length);
    out.flush();
  }

  public static void hexDump(String linePrefix, byte[] b, int offset, int length)
  {
    PrintWriter out = new PrintWriter(System.out);
    hexDump(linePrefix, out, b, offset, length);
    out.flush();
  }
  
  /**
   * Dump a byte array to the given print writer.
   */
  public static void hexDump(PrintWriter out, byte[] b, int offset, int length)
  {
    hexDump("", out, b, offset, length);
  }
  
  public static void hexDump(String linePrefix, PrintWriter out, byte[] b, int offset, int length)
  {
    int rowLen = 0;
    byte[] row = new byte[16];

    for(int i=0; i<length; i += rowLen)
    {
      // get the row
      rowLen = Math.min(16, length-i);
      System.arraycopy(b, offset+i, row, 0, rowLen);

      // print line prefix
      out.print(linePrefix);
      
      // print buffer offset
      String off = Integer.toHexString(i+offset);
      out.print( TextUtil.padLeft(off, 3) );
      out.print(':');

      // print in hex
      for(int j=0; j<16; ++j)
      {
        if (j % 4 == 0) out.print(' ');
        if (j >= rowLen) out.print("  ");
        else out.print( TextUtil.byteToHexString(row[j] & 0xFF) );
      }
      out.print("  ");

      // print in ascii
      for(int j=0; j<rowLen; ++j)
        out.print( TextUtil.byteToChar(row[j] & 0xFF, '.') );
      out.println();
    }
  }   
}
