/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.ndriver.util;

import java.io.InputStream;
import javax.baja.sys.BajaRuntimeException;

/**
 * TByteArrayInputStream is a wrapper class for byte array that allows peeking.
 * implements java.io.InputStream.
 *
 * @author Robert A Adams
 * @creation Jun 16, 2011
 */
public class TByteArrayInputStream
  extends InputStream
{
  /**
   * Don't use this constructor
   */
  public TByteArrayInputStream()
  {
    throw new BajaRuntimeException("Must supply byte array");
  }

  /**
   * Creates a new byte array input stream that reads data from the specified
   * byte array
   */
  public TByteArrayInputStream(byte[] buf, int pos, int cnt)
  {
    this.buf = buf;
    this.pos = pos;
    this.count = cnt;
  }

  /**
   * Creates a new byte array input stream that reads data from the specified
   * byte array
   */
  public TByteArrayInputStream(byte[] buf)
  {
    this.buf = buf;
    count = buf.length;
  }

  /**
   * Returns number of bytes available to read.
   */
  @Override
  public synchronized int available()
  {
    return count - pos;
  }

  /**
   * Set mark to current position
   */
  public void mark()
  {
    mark = pos;
  }

  /**
   * Set position of mark to specified value.
   */
  @Override
  public void mark(int markpos)
  {
    if (markpos >= count)
    {
      return;
    }
    mark = markpos;
  }

  /**
   * returns true
   */
  @Override
  public boolean markSupported()
  {
    return true;
  }

  /**
   * Reads the next byte of data from this input stream. The value byte is
   * returned as an int in the range 0 to 255. If no byte is available because
   * the end of the stream has been reached, the value -1 is returned.
   */
  @Override
  public synchronized int read()
  {
    if (pos >= count)
    {
      throw new BajaRuntimeException("end of buf encountered");
    }
    return buf[pos++];
  }

  /**
   * Return the next byte in the stream without incrementing position marker.
   */
  public synchronized int peek()
  {
    if (pos >= count)
    {
      throw new BajaRuntimeException("end of buf encountered");
    }
    return buf[pos];
  }

  /**
   * Resets position in buf to current mark.
   */
  @Override
  public synchronized void reset()
  {
    pos = mark;
  }

  /**
   * Get the current position of the next read.
   */
  public synchronized int getPosition()
  {
    return pos;
  }

  /**
   * Get a copy of the current buffer
   */
  public byte[] copyBuffer()
  {
    byte[] a = new byte[buf.length];
    System.arraycopy(buf, 0, a, 0, buf.length);
    return a;
  }

  public String toString()
  {
    return new String(buf, 0, count);
  }

  /**
   * Return the last byte in the stream without changing position marker.
   */
  public int peekFinal()
  {
    return buf[count - 1];
  }

  byte[] buf;
  int count;
  int mark = 0;
  int pos = 0;
}
