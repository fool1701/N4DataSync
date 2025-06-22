/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.net;

import java.net.URL;

import javax.baja.naming.BIpHost;

/**
 * URLConnection allows client applications to submit requests to
 * an HTTP server using a java.net.URL.
 *
 * @author    John Sublett
 * @creation  20 Sep 2000
 * @version   $Revision: 2$ $Date: 3/28/05 9:49:34 AM EST$
 * @since     Niagara 3.0
 */
public class UrlConnection
  extends HttpConnection
{
  /**********************************************
  * Constructor.
  ***********************************************/
  public UrlConnection(URL url)
  {
    super(new BIpHost(url.getHost()), 
          (url.getPort() == -1) ? Http.DEFAULT_HTTP_PORT : url.getPort(),
          url.getFile());
    startUrl = url;
  }

  /************************************************
  * Get the URL for the first resource retrieved with
  * this connection.
  *************************************************/
  public URL getUrl()
  {
    return startUrl;
  }

  /**********************************************
  * Get the string key for hashing the specified url.
  ***********************************************/
  private String getHashString(URL url)
  {
    String host = url.getHost().toLowerCase();
    int    port = url.getPort();

    if (port == -1)
      port = Http.DEFAULT_HTTP_PORT;

    return host + ":" + port;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private URL          startUrl;
}