/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import javax.baja.net.HttpConnection;
import javax.baja.nre.util.TextUtil;
import com.tridium.ndriver.datatypes.BIpAddress;

/**
 * NHttpErrorResponse encapsulates an http error response message.
 *
 * @author Robert Adams
 * @creation June 6, 2012
 */
public class NHttpErrorResponse
  extends NHttpResponse
{
  /**
   * Construct response from info in HttpConnection. Sets {@code statusCode} to -1
   */
  public NHttpErrorResponse(BIpAddress address, HttpConnection hcon)
    throws Exception
  {
    super(address, hcon);
  }

  public NHttpErrorResponse(BIpAddress address, HttpConnection hcon, int statusCode)
    throws Exception
  {
    super(address, hcon);
    this.statusCode = statusCode;
  }

  /**
   * Attempt to extract an error message from response data.
   */
  public String getErrorMessage()
  {
    if (buf == null)
    {
      return "";
    }

    char[] cbuf = new char[buf.length];
    for (int i = 0; i < cbuf.length; ++i)
    {
      cbuf[i] = (char)buf[i];
    }
    String s = String.valueOf(cbuf);

    String contentType = getValue("Content-Type");

    // If html return body
    if (contentType != null && contentType.equalsIgnoreCase("text/html"))
    {
      String ls = s.toLowerCase();
      if (ls.indexOf("<body>") > 0)
      {
        String bod = s.substring(ls.indexOf("<body>") + 6, ls.indexOf("</body>"));
        return TextUtil.stripMarkup(bod).trim();
      }
    }

    return s;
  }

  public int getStatusCode()
  {
    return statusCode;
  }

  private int statusCode = -1;
}
