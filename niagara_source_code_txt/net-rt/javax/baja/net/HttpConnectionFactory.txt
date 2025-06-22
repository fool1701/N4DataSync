/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.net;

import java.net.MalformedURLException;
import java.net.URL;

import javax.baja.naming.BIpHost;

public class HttpConnectionFactory
{
  public static HttpConnection make(String url)
    throws MalformedURLException
  {
    
    URL parsedUrl = new URL(url);
    
    String protocol = parsedUrl.getProtocol().toLowerCase();
    String host = parsedUrl.getHost();
    int port = parsedUrl.getPort();
    if (port < 0)
      port = parsedUrl.getDefaultPort();
    String file = parsedUrl.getFile();
    BIpHost ipHost = new BIpHost(host);
      
    if (protocol.equalsIgnoreCase("https"))
      return new HttpsConnection(ipHost, port, file);
    else if (protocol.equalsIgnoreCase("http"))
      return new HttpConnection(ipHost, port, file);
    
    throw new MalformedURLException("unsupported protocol: " + protocol);
  }

}