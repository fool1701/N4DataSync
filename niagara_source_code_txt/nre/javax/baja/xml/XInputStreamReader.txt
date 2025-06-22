/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/*
 * This source code file is public domain
 * http://sourceforge.net/projects/uxparser
 */
package javax.baja.xml;

import java.io.*;
import java.util.zip.*;

import com.tridium.nre.util.InputStreamInfo;

/**
 * XInputStreamRead is used to read a XML byte stream into a stream
 * of unicode characters.  Mapping the byte stream into a charset
 * encoding is implemented according XML 1.0 Appendix F - Autodetection
 * of Character Encodings.  XInputStream also automatically handles
 * reading a PKZIP zipped XML.  Character encodings supported:
 * <ul>
 *  <li>UTF-16 big-endian with 0xFEFF byte order mark;</li>
 *  <li>UTF-16 little-endian with 0xFFFE byte order mark;</li>
 *  <li>UTF-8 with 0xEFBBBF byte order mark;</li>
 *  <li>PKZIP containing any other format with 0x504b0304 byte
 *      order mark (assumes text is first zip entry)</li>
 *  <li>Anything else assumes UTF-8;</li>
 * </ul>
 *
 * @author    Brian Frank on 19 Apr 05
 * @since     Baja 1.0
 */
public class XInputStreamReader
  extends Reader
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct writer for specified file.
   *
   * @param in Underlying inputstream
   * @throws IOException if opening the underlying input stream fails
   */
  public XInputStreamReader(InputStream in)
    throws IOException
  {
    if (in.markSupported())
    {
      this.in = in;
    }
    else
    {
      this.in = new BufferedInputStream(in);
    }
  }

////////////////////////////////////////////////////////////////
// Reader
////////////////////////////////////////////////////////////////

  /**
   * Get the character encoding being used.
   *
   * @return Character encoding
   * @throws IOException if the character encoding cannot be detected
   */
  public String getEncoding()
    throws IOException
  {
    if (!autoDetected) autoDetect();
    return inputStreamInfo.getEncodingTag();
  }

  /**
   * Return if the stream was zipped.
   *
   * @return True if the underlying stream was zipped
   * @throws IOException if detecting zipped encoding fails
   */
  public boolean isZipped()
    throws IOException
  {
    if (!autoDetected) autoDetect();
    return zipped;
  }

  /**
   * Read one character.
   *
   * @return single character
   * @throws IOException on read error
   */
  @Override
  public int read()
    throws IOException
  {
    if (!autoDetected) autoDetect();

    // do our own UTF decoding, since java.io.InputStreamReader
    // is deathly slow (tests indicate it can more than double
    // the time of the rest of the XML parsing combined)
    switch(encoding)
    {
      case UTF_8:
        // handle ASCII 99% case inline for performance
        int c = in.read();
        if (c < 0) return -1;
        if ((c & 0x80) == 0) return c;
        return readUtf8(c);
      case UTF_16_BE:
        return readUtf16be();
      case UTF_16_LE:
        return readUtf16le();
    }

    throw new IllegalStateException();
  }

  /**
   * Read a block of characters into the specified buffer.
   *
   * @param buf buffer to read into
   * @param off offset in buffer
   * @param len length of buffer
   * @return number of characters read
   * @throws IOException if read error occurs
   */
  @Override
  public int read(char[] buf, int off, int len)
    throws IOException
  {
    if (!autoDetected) autoDetect();
    int last = off+len;
    for(int i=0; i<len; ++i)
    {
      int c = read();
      if (c < 0) return i == 0 ? -1 : i;
      buf[off+i] = (char)c;
    }
    return len;
  }

  /**
   * Read a block of characters into the specified buffer.
   *
   * @param buf buffer to read into
   * @return number of characters read
   * @throws IOException if read error occurs
   */
  @Override
  public int read(char[] buf)
    throws IOException
  {
    return read(buf, 0, buf.length);
  }

  /**
   * Close the underlying input stream.
   *
   * @throws IOException if closing the underlying input stream fails
   */
  @Override
  public void close()
    throws IOException
  {
    in.close();
  }

////////////////////////////////////////////////////////////////
// Auto-detect
////////////////////////////////////////////////////////////////

  /**
   * Auto-detect the character encoding.
   */
  private void autoDetect()
    throws IOException
  {
    inputStreamInfo = new InputStreamInfo(in);
    if(inputStreamInfo.isZipped())
    {
      zipped = true;
      ZipInputStream unzip = new ZipInputStream(in);
      unzip.getNextEntry(); //Read the first zip entry to detect its encoding
      in = new BufferedInputStream(unzip);
      autoDetect();
      return;
    }

    // create the appropriate InputStreamReader with char encoding
    encoding = inputStreamInfo.getEncoding();
    autoDetected = true;
  }

////////////////////////////////////////////////////////////////
// UTF
////////////////////////////////////////////////////////////////

  private int readUtf8(int c0)
    throws IOException
  {
    // at this point 0xxx xxxx (ASCII) is already handled
    // since it is inlined into read() itself

    int c1, c2, c3;
    switch (c0 >> 4)
    {
      case 12:
      case 13:
        // 110x xxxx   10xx xxxx
        c1 = in.read();
        if ((c1 & 0xC0) != 0x80)
          throw new UTFDataFormatException(Integer.toHexString(c0));
        return ((c0 & 0x1F) << 6) | ((c1 & 0x3F) << 0);
      case 14:
        // 1110 xxxx  10xx xxxx  10xx xxxx
        c1 = in.read();
        c2 = in.read();
        if (((c1 & 0xC0) != 0x80) || ((c2 & 0xC0) != 0x80))
          throw new UTFDataFormatException();
        return ((c0  & 0x0F) << 12) | ((c1 & 0x3F) << 6)  | ((c2 & 0x3F) << 0);
      case 15:
        // 1111 0xxx  10xx xxxx  10xx xxxx  10xx xxxx
        /* I think this is valid, but Java doesn't seem to output
           characters this high - so cap things below this
        c1 = in.read();
        c2 = in.read();
        c3 = in.read();
        if (((c1 & 0xC0) != 0x80) || ((c2 & 0xC0) != 0x80) || ((c3 & 0xC0) != 0x80))
          throw new UTFDataFormatException();
        return ((c0  & 0x07) << 18) | ((c1 & 0x3F) << 12) | ((c2 & 0x3F) << 6)  | ((c3 & 0x3F) << 0);
        */
        throw new UTFDataFormatException(Integer.toHexString(c0));
      default:
        throw new UTFDataFormatException(Integer.toHexString(c0));
    }
  }

  private int readUtf16be()
    throws IOException
  {
    int c0 = in.read();
    int c1 = in.read();
    if (c0 < 0) return -1;
    return ((c0 & 0xFF) << 8) | ((c1 & 0xFF) << 0);
  }

  private int readUtf16le()
    throws IOException
  {
    int c0 = in.read();
    int c1 = in.read();
    if (c0 < 0) return -1;
    return ((c1 & 0xFF) << 8) | ((c0 & 0xFF) << 0);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final int UTF_8     = InputStreamInfo.UTF_8;
  static final int UTF_16_BE = InputStreamInfo.UTF_16_BE;
  static final int UTF_16_LE = InputStreamInfo.UTF_16_LE;
  private InputStreamInfo inputStreamInfo;

  private InputStream in;        // raw InputStream
  private boolean autoDetected;  // have we run autoDetect() yet
  private boolean zipped;        // was stream a zip file
  private int encoding = -1;     // encoding constant

}
