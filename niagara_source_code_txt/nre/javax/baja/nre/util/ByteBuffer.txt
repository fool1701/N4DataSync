/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UTFDataFormatException;


/**
 * ByteBuffer is a dynamically growable byte array which
 * has implicit support for the DataOuput and DataInput
 * interfaces.
 *
 * @author    Brian Frank
 * @creation  25 Jan 01
 * @version   $Revision: 17$ $Date: 3/25/08 11:19:10 AM EDT$
 * @since     Baja 1.0
 */
public class ByteBuffer
  implements DataOutput, DataInput
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a ByteBuffer of length <code>len</code> using 
   * the specified byte array for the internal buffer.
   */
  public ByteBuffer(byte[] buf, int len)
  {
    length = len;
    buffer = buf;
  }

  /**
   * Create a ByteBuffer of length <code>buf.length</code>
   * using the specified byte array for the internal buffer.
   */
  public ByteBuffer(byte[] buf)
  {
    length = buf.length;
    buffer = buf;
  }

  /**
   * Create a buffer with the specified inital capacity.
   */
  public ByteBuffer(int initialCapacity)
  {
    buffer = new byte[initialCapacity];
  }

  /**
   * Create a buffer with an inital capacity of 64 bytes.
   */
  public ByteBuffer()
  {
    this(64);
  }

////////////////////////////////////////////////////////////////
// Buffer
////////////////////////////////////////////////////////////////

  /**
   * Get the length of the buffer.
   */
  public int getLength() 
  {
    return length;
  }

  /**
   * Set the length and pos back to 0.
   */
  public void reset() 
  {
    this.pos = 0;
    this.length = 0;
  }
  
  /**
   * Move the current read position index.
   */
  public void seek(int pos)
  {
    this.pos = pos;
  }

  /**
   * Get the current read position index.
   */
  public int getPosition()
  {
    return pos;
  }

  /**
   * Get a direct reference to the internal buffer array.
   */
  public byte[] getBytes() 
  {
    return buffer;
  }
  
  /**
   * Get a copy of the byte array sized to actual length.
   */
  public byte[] toByteArray()
  {
    byte[] copy = new byte[length];
    System.arraycopy(buffer, 0, copy, 0, length);
    return copy;
  }
  
  /**
   * Get the big endian flag for byte ordering.
   */
  public boolean isBigEndian()
  {
    return bigEndian;
  }

  /**
   * Set the big endian flag for byte ordering.
   */
  public void setBigEndian(boolean bigEndian)
  {
    this.bigEndian = bigEndian;
  }

////////////////////////////////////////////////////////////////
// Streams
////////////////////////////////////////////////////////////////  

  /**
   * Get a new InputStream for reading the internal buffer.
   */
  public InputStream getInputStream()
  {
    return new InputStream()
    {
      public final int available() throws IOException { return ByteBuffer.this.available(); }
      
      public final int read() throws IOException
      {
        //NCCB-5942: InputStream read() must return -1 if no more data; however, 
        //since the same read() is being used as the implementation for 
        //both InputStream and DataInput (which must throw EOF on no more data), trap the
        //EOF in the InputStream specific implementation and return -1
        try
        {
          return ByteBuffer.this.read();
        }
        catch (EOFException eof)
        {
          return -1;
        }
      }
      
      public final int read(byte[] buf, int off, int len) throws IOException { return ByteBuffer.this.read(buf, off, len); }
    };
  }

  /**
   * Get a new OutputStream for the writing to the internal buffer.
   */
  public OutputStream getOutputStream()
  {
    return new OutputStream()
    {
      public final void write(int v) { ByteBuffer.this.write(v); }
      public final void write(byte[] buf, int off, int len) { ByteBuffer.this.write(buf, off, len); }
    };
  }
  
  /**
   * Read the specified number of bytes from the input
   * stream into the internal buffer.
   *
   * @return the number of bytes actually read.
   */
  public int readFrom(InputStream in, int len)
    throws IOException
  {
    insureCapacityToAdd(len);
    int n = 0;
    while(n < len)
    {
      int count = in.read(buffer, pos+n, len-n);
      if (count < 0) break;
      n += count;
    }
    length += len;
    return n;
  }

  /**
   * Read the specified number of bytes from the input
   * stream into the internal buffer.  If the len
   * passed is less than zero, then this method routes
   * to readToEnd().
   *
   * @throws EOFException if the end of the stream
   *    is reached before <code>len</code> bytes
   *    are read.
   */
  public void readFullyFrom(InputStream in, int len)
    throws IOException
  {
    if (len < 0) { readToEnd(in); return; }
    insureCapacityToAdd(len);
    int n = 0;
    while(n < len)
    {
      int count = in.read(buffer, pos+n, len-n);
      if (count < 0)
        throw new EOFException();
      n += count;
    }
    length += len;
  }

  /**
   * Read from the specified InputStream into the internal
   * buffer.  The stream is read until it returns -1 indicating
   * the end of the input stream.
   */
  public void readToEnd(InputStream in)
    throws IOException
  {
    int n;
    byte[] buf = new byte[1024];
    while((n = in.read(buf, 0, 1024)) >= 0)
      write(buf, 0, n);
  }
    
  /**
   * Write the internal buffer in its entirity to the 
   * specified output stream.
   */
  public void writeTo(OutputStream out)
    throws IOException
  {
    out.write(buffer, 0, length);
  }

  /**
   * Write <code>len</code> bytes of the internal buffer 
   * starting at <code>offset</code> to the specified output 
   * stream.
   */
  public void writeTo(OutputStream out, int offset, int len)
    throws IOException
  {
    if (len > length) throw new IOException("len > internal buffer");
    out.write(buffer, offset, len);
  }
  
  /**
   * Write the entire internal buffer to the specified output.
   */
  public void writeTo(DataOutput out)
    throws IOException
  {
    out.write(buffer, 0, length);
  }

  /**
   * Write <code>len</code> bytes of the internal buffer 
   * starting at <code>offset</code> to the specified output.
   */
  public void writeTo(DataOutput out, int offset, int len)
    throws IOException
  {
    if (len > length) throw new IOException("len > internal buffer");
    out.write(buffer, offset, len);
  }

////////////////////////////////////////////////////////////////
// DataOutput
////////////////////////////////////////////////////////////////  

  /**
   * Write the specified byte to the internal buffer.
   */
  public void write(int b)
  {
    if (length+1 > buffer.length)
    {
      byte[] temp = new byte[Math.max(length<<2, 256)];
      System.arraycopy(buffer, 0, temp, 0, length);
      buffer = temp;
    }
    buffer[length++] = (byte)b;
  }

  /**
   * Write the specified buffer to the internal buffer.
   */
  public void write(byte[] buf)
  {
    write(buf, 0, buf.length);
  }

  /**
   * Writes <code>len</code> bytes from the specified byte array 
   * starting at <code>offset</code> to the internal buffer. 
   */
  public void write(byte[] buf, int offset, int len)
  {
    insureCapacityToAdd(len);
    System.arraycopy(buf, offset, this.buffer, this.length, len);
    length += len;
  }
  
  /**
   * Insure the internal buffer has enough capacity
   * to add the specified number of bytes.
   */
  private final void insureCapacityToAdd(int toAddLength)
  {
    int needed = length+toAddLength;
    if (needed > buffer.length)
    {
      byte[] temp = new byte[Math.max(needed, 256)];
      System.arraycopy(buffer, 0, temp, 0, length);
      buffer = temp;
    }
  }

  /**
   * Write a boolean value to the internal buffer.
   */
  public void writeBoolean(boolean v) 
  {
    write(v ? 1 : 0);            
  }

  /**
   * Write a 8 bit value to the internal buffer.
   */
  public void writeByte(int v) 
  {
    write(v);
  }

  /**
   * Write a 16 bit value to the internal buffer.
   */
  public void writeShort(int v) 
  {
    if (bigEndian)
    {
      write((v >>> 8) & 0xFF);
      write((v >>> 0) & 0xFF);
    }
    else
    {
      write((v >>> 0) & 0xFF);
      write((v >>> 8) & 0xFF);
    }
  }
  
  /**
   * Write a unicode char to the internal buffer.
   */
  public void writeChar(int v) 
  {
    if (bigEndian)
    {
      write((v >>> 8) & 0xFF);
      write((v >>> 0) & 0xFF);
    }
    else
    {
      write((v >>> 0) & 0xFF);
      write((v >>> 8) & 0xFF);
    }
  }

  /**
   * Write a 32 bit value to the internal buffer.
   */
  public void writeInt(int v) 
  {
    if (bigEndian)
    {
      write((v >>> 24) & 0xFF);
      write((v >>> 16) & 0xFF);
      write((v >>>  8) & 0xFF);
      write((v >>>  0) & 0xFF);
    }
    else
    {
      write((v >>>  0) & 0xFF);
      write((v >>>  8) & 0xFF);
      write((v >>> 16) & 0xFF);
      write((v >>> 24) & 0xFF);
    }
  }

  /**
   * Write a 64 bit value to the internal buffer.
   */
  public void writeLong(long v) 
  {
    if (bigEndian)
    {
      write((int)(v >>> 56) & 0xFF);
      write((int)(v >>> 48) & 0xFF);
      write((int)(v >>> 40) & 0xFF);
      write((int)(v >>> 32) & 0xFF);
      write((int)(v >>> 24) & 0xFF);
      write((int)(v >>> 16) & 0xFF);
      write((int)(v >>>  8) & 0xFF);
      write((int)(v >>>  0) & 0xFF);
    }
    else
    {
      write((int)(v >>>  0) & 0xFF);
      write((int)(v >>>  8) & 0xFF);
      write((int)(v >>> 16) & 0xFF);
      write((int)(v >>> 24) & 0xFF);
      write((int)(v >>> 32) & 0xFF);
      write((int)(v >>> 40) & 0xFF);
      write((int)(v >>> 48) & 0xFF);
      write((int)(v >>> 56) & 0xFF);
    }
  }

  /**
   * Write a 32 bit float to the internal buffer.
   */
  public void writeFloat(float v) 
  {
    writeInt(Float.floatToIntBits(v));
  }

  /**
   * Write a 64 bit double to the internal buffer.
   */
  public void writeDouble(double v) 
  {
    writeLong(Double.doubleToLongBits(v));
  }

  /**
   * Write the specified String's bytes to the internal buffer.
   */
  public void writeBytes(String s) 
  {
    int strlen = s.length();
    for(int i=0; i<strlen; ++i)
      write((byte)s.charAt(i));
  }

  /**
   * Write the specified String's characters to the internal buffer.
   */
  public void writeChars(String s) 
  {
    int strlen = s.length();
    for(int i=0; i<strlen; ++i)
    {
      int v = s.charAt(i);
      write((v >>> 8) & 0xFF);
      write((v >>> 0) & 0xFF);
    }
  }

  /**
   * Write the specified String's to the internal buffer
   * using modified UTF-8 format. 
   */
  public void writeUTF(String s) 
    throws UTFDataFormatException
  {
    int strlen = s.length();
    int utflen = 0;
    char[] chars = new char[strlen];

    s.getChars(0, strlen, chars, 0);
    for(int i=0; i<strlen; ++i)
    {
      int c = chars[i];
      if ((c >= 0x0001) && (c <= 0x007F))
        utflen++;
      else if (c > 0x07FF)
        utflen += 3;
      else
        utflen += 2;
    }

    if (utflen > 65535)
      throw new UTFDataFormatException();

    insureCapacityToAdd(utflen + 2);
    byte[] buf = this.buffer;  // use stack vars for performance
    int count = this.length;
    this.length += utflen + 2;
    
    buf[count++] = (byte) ((utflen >>> 8) & 0xFF);
    buf[count++] = (byte) ((utflen >>> 0) & 0xFF);
    for(int i= 0; i<strlen; ++i)
    {
      int c = chars[i];
      if ((c >= 0x0001) && (c <= 0x007F))
      {
        buf[count++] = (byte) c;
      }
      else if (c > 0x07FF)
      {
        buf[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
        buf[count++] = (byte) (0x80 | ((c >>  6) & 0x3F));
        buf[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
      }
      else
      {
        buf[count++] = (byte) (0xC0 | ((c >>  6) & 0x1F));
        buf[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
      }
    }
  }

  /**
   * Utility which returns the number of bytes required to encode the 
   * given string with the modified UTF-8 format used by DataOutput.writeUTF().
   */
  public static int utfEncodedSize(String string)
  {
    int result = 2; // start with two bytes for length
    int len = string.length();
    for (int i = 0; i < len; i++)
    {
      result += utfEncodedSize(string.charAt(i));
    }
    return result;
  }

  /**
   * Utility which returns the number of bytes required to encode the 
   * given character as part of a string with the modified UTF-8 format 
   * used by DataOutput.writeUTF().
   */
  public static int utfEncodedSize(char c)
  {
    if ((c >= 0x0001) && (c <= 0x007F))
    {
      return 1;
    }
    else if (c > 0x07FF)
    {
      return 3;
    }
    else
    {
      return 2;
    }
  }
  
////////////////////////////////////////////////////////////////
// DataInput
////////////////////////////////////////////////////////////////  
  
  /**
   * Return the number of bytes available based on the
   * current read position and the buffer length.
   */
  public int available()
  {
    return length - pos;
  }

  /**
   * Peek at the next byte to read without
   * actually changing the read position.
   */
  public int peek()
    throws IOException
  {
    if (pos >= length) throw new EOFException();
    return buffer[pos] & 0xFF;        
  }

  /**
   * Read the next byte from the internal buffer.
   *
   * @throws EOFException if there are no more 
   *    bytes available in the buffer.
   */
  public int read()
    throws IOException
  {
    if (pos >= length) throw new EOFException();
    return buffer[pos++] & 0xFF;        
  }

  /**
   * Read <code>buf.length</code> bytes from the internal
   * buffer into the specified byte array.
   */
  public int read(byte[] buf) 
    throws IOException
  {
    return read(buf, 0, buf.length);
  }

  /**
   * Read <code>len</code> bytes from the internal buffer 
   * into the specified byte array at <code>offset</code>.
   */
  public int read(byte[] buf, int offset, int len) 
    throws IOException
  {
    if (buf == null)
    {
      throw new NullPointerException();
    }

    if (offset < 0 || len < 0 || len > buf.length - offset)
    {
      throw new IndexOutOfBoundsException();
    }

    if (len == 0)
    {
      return 0;
    }

    //NCCB-5942: Per docs.oracle.com/javase/7/docs/api/java/io/InputStream.html, 
    //InputStream read(byte[] buf, int offset, int len) must return -1 if no 
    //more data is being read. The previous implementation would not return -1 
    //on any condition.    
    if (pos >= length) return -1;
    
    int actual = Math.min(len, length-pos);
    System.arraycopy(buffer, pos, buf, offset, actual);
    pos += actual;
    return actual;
  }

  /**
   * Read <code>buf.length</code> bytes from the internal
   * buffer into the specified byte array.
   */
  public void readFully(byte buf[]) 
    throws IOException
  {
    readFully(buf, 0, buf.length);
  }

  /**
   * Read <code>len</code> bytes from the internal buffer 
   * into the specified byte array at <code>offset</code>.
   */
  public void readFully(byte buf[], int offset, int len) 
    throws IOException
  {
    //NCCB-5942: Per http://docs.oracle.com/javase/7/docs/api/java/io/DataInput.html#readFully%28byte[],%20int,%20int%29
    //DataInput readFully() must throw EOF if not all bytes are read, not truncate to what is actually available
    
    //If actual read does not equal what was requested, throw EOFException
    if (read(buf, offset, len) != len)
    {
      throw new EOFException();
    }
  }

  /**
   * Skip <code>n</code> bytes in the internal buffer.
   */
  public int skipBytes(int n) 
    throws IOException
  {
    pos += n;
    return n;
  }

  /**
   * Read a boolean from the internal buffer.
   */
  public boolean readBoolean() 
    throws IOException
  {
    return read() != 0;
  }

  /**
   * Read a 8-bit signed byte value value from the internal buffer.
   */
  public byte readByte() 
    throws IOException
  {
    return (byte)read();
  }

  /**
   * Read a 8-bit unsigned byte value from the internal buffer.
   */
  public int readUnsignedByte() 
    throws IOException
  {
    return read();
  }

  /**
   * Read a 16-bit signed byte value from the internal buffer.
   */
  public short readShort() 
    throws IOException
  {
    if (bigEndian)
      return (short)((read() << 8) + read());
    else
      return (short)(read() + (read() << 8));
  }

  /**
   * Read a 16-bit unsigned byte value from the internal buffer.
   */
  public int readUnsignedShort() 
    throws IOException
  {
    if (bigEndian)
      return (read() << 8) + read();
    else
      return read() + (read() << 8);
  }

  /**
   * Read a unicode char from the internal buffer.
   */
  public char readChar() 
    throws IOException
  {
    if (bigEndian)
      return (char)((read() << 8) + read());
    else
      return (char)(read() + (read() << 8));
  }

  /**
   * Read a 32-bit integer value from the internal buffer.
   */
  public int readInt() 
    throws IOException
  {
    if (bigEndian)
      return (read() << 24) + (read() << 16) + 
             (read() << 8) + (read() << 0);
    else
      return (read() << 0) + (read() << 8) + 
             (read() << 16) + (read() << 24);
  }

  /**
   * Read a 64-bit long value from the internal buffer.
   */
  public long readLong() 
    throws IOException
  {
    if (bigEndian)
      return (((long)readInt()) << 32) + (readInt() & 0xFFFFFFFFL);
    else
      return ((readInt() & 0xFFFFFFFFL + ((long)readInt()) << 32));
  }

  /**
   * Read a 32-bit float value from the internal buffer.
   */
  public float readFloat() 
    throws IOException
  {
    return Float.intBitsToFloat(readInt());
  }

  /**
   * Read a 64-bit double value from the internal buffer.
   */
  public double readDouble() 
    throws IOException
  {
    return Double.longBitsToDouble(readLong());
  }

  /**
   * Always throw an UnsupportedOperationException.
   */
  public String readLine() 
    throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Read a UTF encoded string from the internal buffer.
   */
  public String readUTF() 
    throws IOException
  {
    int utflen = readUnsignedShort();
    StringBuilder str = new StringBuilder(utflen);
    
    int count = 0;
    while (count < utflen)
    {
      int c = read();      
      int c2, c3;
      switch (c >> 4)
      {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
          /* 0xxxxxxx*/
          count++;
          str.append((char)c);
          break;
        case 12:
        case 13:
          /* 110x xxxx   10xx xxxx*/
          count += 2;
          if (count > utflen)
            throw new UTFDataFormatException();
          c2 = read();
          if ((c2 & 0xC0) != 0x80)
            throw new UTFDataFormatException();
          str.append((char)(((c & 0x1F) << 6) | (c2 & 0x3F)));
          break;
        case 14:
          /* 1110 xxxx  10xx xxxx  10xx xxxx */
          count += 3;
          if (count > utflen)
            throw new UTFDataFormatException();
          c2 = read();
          c3 = read();
          if (((c2 & 0xC0) != 0x80) || ((c3 & 0xC0) != 0x80))
            throw new UTFDataFormatException();
          str.append((char)(((c  & 0x0F) << 12) |
                            ((c2 & 0x3F) << 6)  |
                            ((c3 & 0x3F) << 0)));
          break;
        default:
          /* 10xx xxxx,  1111 xxxx */
          throw new UTFDataFormatException();     
      }
    }
    
    return str.toString();
  }  

////////////////////////////////////////////////////////////////
// StartsWith
////////////////////////////////////////////////////////////////  

  /**
   * Return true if the current byte buffer starts
   * with the specified byte.
   */
  public boolean startsWith(int b)
  {
    if (length < 1) return false;
    return buffer[0] == (byte)(b & 0xFF);
  }

  /**
   * Return true if the current byte buffer 
   * starts with the specified byte array.
   */
  public boolean startsWith(byte[] b)
  {
    if (length < b.length) return false;
    byte[] buf = buffer;
    for(int i=0; i<b.length; ++i)
      if (buf[i] != b[i]) return false;
    return true;
  }

////////////////////////////////////////////////////////////////
// EndsWith
////////////////////////////////////////////////////////////////  
  
  /**
   * Return true if the current byte buffer ends
   * with the specified byte.
   */
  public boolean endsWith(int b)
  {
    if (length < 1) return false;
    return buffer[buffer.length-1] == (byte)(b & 0xFF);
  }

  /**
   * Return true if the current byte buffer 
   * ends with the specified byte array.
   */
  public boolean endsWith(byte[] b)
  {
    if (length < b.length) return false;
    byte[] buf = buffer;
    int index = length - b.length;
    for(int i=0; i<b.length; ++i)
      if (buf[index++] != b[i]) return false;
    return true;
  }

////////////////////////////////////////////////////////////////
// IndexOf
////////////////////////////////////////////////////////////////

  /**
   * Get first the index of the specified byte or 
   * return -1  if the byte is not found.
   */
  public int indexOf(int b)
  {
    return indexOf(b, 0);
  }

  /**
   * Get first the index of the specified byte or 
   * return -1  if the byte is not found.
   *
   * @param b  byte to search for.
   * @param fromIndex  the index to start the search from. 
   *   There is no restriction on the value of fromIndex. If 
   *   it is greater than the length of this string, it has the 
   *   same effect as if it were equal to the length. If it is 
   *   negative, it has the same effect as if it 0.
   */
  public int indexOf(int b, int fromIndex)
  {
    if (length < 0) return -1;
    if (fromIndex < 0) fromIndex = 0;
    for(int i=fromIndex; i<length; ++i)
      if (buffer[i] == b) return i;
    return -1;
  }
  
  /**
   * Get first the index of the specified byte array 
   * pattern or return -1 if the pattern is not found.
   */
  public int indexOf(byte[] b)
  {
    return indexOf(b, 0);
  }

  /**
   * Get first the index of the specified byte array 
   * pattern or return -1 if the pattern is not found.
   *
   * @param b  byte array containing pattern to search for.
   * @param fromIndex  the index to start the search from. 
   *   There is no restriction on the value of fromIndex. If 
   *   it is greater than the length of this string, it has the 
   *   same effect as if it were equal to the length. If it is 
   *   negative, it has the same effect as if it were 0.
   */
  public int indexOf(byte[] b, int fromIndex)
  {
    byte b0 = b[0];
    if (length < 0) return -1;
    if (fromIndex < 0) fromIndex = 0;
    int len = length - b.length + 1;
    for(int i=fromIndex; i<len; ++i)
    {
      if (buffer[i] == b0)
      {
        boolean match = true;
        for(int j=1; j<b.length; ++j)
          if (b[j] != buffer[i+j])
            { match = false; break; }
        if (match) return i;
      }
    }
    return -1;
  }

////////////////////////////////////////////////////////////////
// LastIndexOf
////////////////////////////////////////////////////////////////

  /**
   * Get last the index of the specified byte or 
   * return -1 if the byte is not found.
   */
  public int lastIndexOf(int b)
  {
    return lastIndexOf(b, length-1);
  }

  /**
   * Get last the index of the specified byte or 
   * return -1 if the byte is not found.
   *
   * @param b  byte array containing pattern to search for.
   * @param fromIndex  the index to start the search from. 
   *   There is no restriction on the value of fromIndex. If 
   *   it is greater than the length of this string, it has the 
   *   same effect as if it were equal to the length. If it is 
   *   negative, it has the same effect as if it were -1: -1 is 
   *   returned.
   */
  public int lastIndexOf(int b, int fromIndex)
  {
    if (length < 0) return -1;
    if (fromIndex < 0) return -1;
    if (fromIndex >= length) fromIndex = length-1;
    for(int i=fromIndex; i>=0; --i)
      if (buffer[i] == b) return i;
    return -1;
  }

  /**
   * Get last the index of the specified byte array 
   * pattern or return -1 if the pattern is not found.
   *
   */
  public int lastIndexOf(byte[] b)
  {
    return lastIndexOf(b, length-1);
  }

  /**
   * Get last the index of the specified byte array 
   * pattern or return -1 if the pattern is not found.
   *
   * @param b  byte array containing pattern to search for.
   * @param fromIndex  the index to start the search from. 
   *   There is no restriction on the value of fromIndex. If 
   *   it is greater than the length of this string, it has the 
   *   same effect as if it were equal to the length. If it is 
   *   negative, it has the same effect as if it were -1: -1 is 
   *   returned.
   */
  public int lastIndexOf(byte[] b, int fromIndex)
  {
    byte b0 = b[0];
    if (length < 0) return -1;
    if (fromIndex < 0) return -1;
    if (fromIndex >= length) fromIndex = length-1;
    int start = Math.min(fromIndex, length-b.length+1);
    for(int i=start; i>=0; --i)
    {
      if (buffer[i] == b0)
      {
        boolean match = true;
        for(int j=1; j<b.length; ++j)
          if (b[j] != buffer[i+j])
            { match = false; break; }
        if (match) return i;
      }
    }
    return -1;
  }
  
  public void setLength(int newLen)
  {
    this.length = newLen;
  }
  public void setBuffer(byte[] newBuf)
  {
    this.buffer = newBuf;
  }  

////////////////////////////////////////////////////////////////
// Debugging
////////////////////////////////////////////////////////////////  

  /**
   * Dump the byte buffer to standard output.
   */
  public void dump()
  {
    ByteArrayUtil.hexDump(buffer, 0, length);
  }
  
  /**
   * Dump the byte buffer to a String.
   */
  public String dumpToString()
  {
    StringWriter sout = new StringWriter();
    PrintWriter out = new PrintWriter(sout);
    ByteArrayUtil.hexDump(out, buffer, 0, length);
    out.flush();
    return sout.toString();    
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /** Actual length of valid data in buffer. */
  protected int length;
  
  /** Byte array buffer. */
  protected byte[] buffer;
  
  /** Pos is used to store the next read index */
  protected int pos;

  /** Flag which determines whether to use 
      big or little endian byte ordering */
  protected boolean bigEndian = true;
  
}
