/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.net;

/**
 * HttpException wraps a HTTP status code.
 *
 * @author    Brian Frank
 * @creation  28 Jun 01
 * @version   $Revision: 1$ $Date: 1/29/03 1:46:16 PM EST$
 * @since     Baja 1.0 
 */
public class HttpException
  extends javax.baja.io.BajaIOException
{

  /**
   * Constructor with expected and actual status code.
   */
  public HttpException(int expectedStatusCode, int statusCode)
  {  
    super(statusCode + ": " + Http.getReasonPhrase(statusCode));
    this.expectedStatusCode = expectedStatusCode;
    this.statusCode = statusCode;
  }

  /**
   * Constructor with actual status code.
   */
  public HttpException(int statusCode)
  {  
    this(-1, statusCode);
  }

  /**
   * Get the expected status or -1 if unknown.
   */
  public int getExpectedStatusCode()
  {
    return expectedStatusCode;
  }

  /**
   * Get the status code.
   */
  public int getStatusCode()
  {
    return statusCode;
  }
  
  private int expectedStatusCode;
  private int statusCode;
  
}
