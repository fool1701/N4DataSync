/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import java.util.Enumeration;
import java.util.List;
import javax.baja.net.HttpConnection;
import com.tridium.ndriver.datatypes.BIpAddress;

/**
 * NHttpResponse is http response message.
 *
 * @author Robert Adams
 * @creation Nov 15, 2011
 */
public class NHttpResponse
  extends NHttpMessage
{
  /**
   * Construct response from info in HttpConnection
   */
  public NHttpResponse(BIpAddress address, HttpConnection hcon)
    throws Exception
  {
    super(address);

    Enumeration<String> en = hcon.getResponseHeaderNames();
    while (en.hasMoreElements())
    {
      String name = en.nextElement();
      List<String> values = hcon.getResponseHeaders(name);
      for (String value : values)
      {
        addHeader(name, value, false);
      }
    }
    responseVersion = hcon.getResponseVersion();

    // Use callback to allow customized handling of response data. 
    // If not setting up a stream the response content will be available
    // immediately.
    processData(hcon);
  }

  /**
   * Override point for subclasses to provide custom parsing.
   */
  protected void processData(HttpConnection hcon)
    throws Exception
  {
    buf = hcon.readContent();
  }

  String responseVersion = "";
}
