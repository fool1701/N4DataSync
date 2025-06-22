/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import java.io.IOException;
import java.io.InputStream;
import com.tridium.driver.util.DrByteArrayUtil;

/**
 * DebugStream is a wrapper class for inputStream to allow debug trace of input
 * bytes. This is a utility class for linkLayer implementations.
 *
 * @author Robert A Adams
 * @creation Mar 14, 2011
 */
public class DebugStream
  extends InputStream
{
  public DebugStream(int maxLen)
  {
    if (maxLen < MINIMUM_BUFFER_SIZE)
    {
      maxLen = MINIMUM_BUFFER_SIZE;
    }
    dat = new byte[maxLen];
  }

  @Override
  public int read()
    throws IOException
  {
    int n = in.read();
    if (n >= 0 && cnt < dat.length)
    {
      dat[cnt++] = (byte)n;
    }
    return n;
  }

  public InputStream reset(InputStream in)
  {
    this.in = in;
    cnt = 0;
    return this;
  }

  public boolean hasDebug()
  {
    return cnt > 0;
  }

  public String debugString()
  {
    return DrByteArrayUtil.toString(dat, cnt);
  }

  byte[] dat;
  int cnt = 0;
  InputStream in;
  
  private static final int MINIMUM_BUFFER_SIZE = 1024;
}
