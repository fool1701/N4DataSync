/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import java.util.Base64;
import javax.baja.security.BUsernameAndPassword;
import com.tridium.ndriver.datatypes.BIpAddress;

/**
 * NHttpRequest is the base http request message.
 *
 * @author Robert A Adams
 * @creation Nov 15, 2011
 */
public class NHttpRequest
  extends NHttpMessage
{
  /**
   * Constructor
   */
  public NHttpRequest(BIpAddress address)
  {
    super(address);
    this.method = "";
    this.uri = "";
  }

  /**
   * Constructor
   */
  public NHttpRequest(BIpAddress address, String method, String uri)
  {
    super(address);
    this.method = method;
    this.uri = uri;
  }

  /**
   * Get the method Sting.
   */
  public String getMethod()
  {
    return method;
  }

  /**
   * Get the URI String.
   */
  public String getUri()
  {
    return uri;
  }

  /**
   * Set the username password
   */
  public void setUsernamePassword(BUsernameAndPassword up)
  {
    usPass = up;
  }

  /**
   * Get the username password
   */
  public BUsernameAndPassword getUsernamePassword()
  {
    return usPass;
  }


  /**
   * Return true to close connection after response received.
   */
  public boolean closeConn()
  {
    return close;
  }


  /**
   * Add basic access authentication per rfc 2617.  This adds an "Authorization"
   * header with base64 encoded userName and password.
   */
  public void addBasicAuthorization(String userName, String password)
  {
    // Combines the user name and password into one ASCII string
    String credentials = userName + ":" + password;

    // Perform Base64 encryption of the credentials.
    String authorization = Base64.getEncoder().encodeToString(credentials.getBytes());

    addHeader("Authorization", "Basic " + authorization);
  }

  /**
   * Check if the specified HTTP response code is a valid response for this
   * message.  HttpComm will return an error response for any response code
   * other than SC_OK(200) which does not pass this test.  Override to direct
   * HttpComm to change default test which passes codes 201-206.
   * <p>
   *
   * @param rc is a HTTP response code (find constant defs in
   *           javax.baja.net.HTTP) see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
   *           for code descriptions
   * @return default returns true for (rc>=201 && rc<=206) for all others return
   * false
   */
  public boolean isValidResponse(int rc)
  {
    if (rc >= 201 && rc <= 206)
    {
      return true;
    }
    return false;
  }

  @Override
  public String toTraceString()
  {
    return method + " " + uri + "\n" + super.toTraceString();
  }

  /**
   * Set message data.
   *
   * @since 3.8.38.1
   */
  public void setData(String data)
  {
    buf = data.getBytes();
  }

  boolean close = true;
  String uri;
  String method;
  BUsernameAndPassword usPass = null;
}
