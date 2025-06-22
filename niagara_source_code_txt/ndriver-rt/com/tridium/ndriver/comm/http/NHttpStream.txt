/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import java.io.InputStream;
import java.io.OutputStream;
import javax.baja.net.HttpConnection;
import com.tridium.ndriver.datatypes.BIpAddress;

/**
 * NHttpStream is NHttpResponse from calls that result in an http stream
 * connection. It provides callbacks to access the input and output stream
 * objects. Applications which receive NHttpStream object should call close()
 * when finished accessing the streams.
 *
 * @author Robert A Adams
 * @creation Nov 15, 2011
 */
public class NHttpStream
  extends NHttpResponse
  implements AutoCloseable
{
  /**
   * Construct response from info in HttpConnection
   */
  public NHttpStream(BIpAddress address, HttpConnection hcon)
    throws Exception
  {
    super(address, hcon);
    this.hcon = hcon;
  }

  @Override
  protected void processData(HttpConnection hcon)
    throws Exception
  {
    // For stream response just get the in/out streams. The calling
    // app will read data from stream.
    in = hcon.getInputStream();
    out = hcon.getOutputStream();
  }


  public InputStream getInputStream()
  {
    return in;
  }

  public OutputStream getOutStream()
  {
    return out;
  }

  @Override
  public synchronized void close()
  {
    hcon.close();
  }

  InputStream in;
  OutputStream out;
  HttpConnection hcon;
}
