/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import javax.baja.naming.BHost;
import javax.baja.naming.BIpHost;
import javax.baja.nre.security.ClientTlsParameters;
import javax.baja.security.crypto.CertManagerFactory;
import javax.baja.security.crypto.ICryptoManager;
import javax.baja.status.BStatus;

import com.tridium.crypto.core.io.CryptoCoreClientSocketFactory;
import com.tridium.net.BProxyAuthenticationType;

/**
 * HttpsConnection allows client applications to submit requests to
 * an HTTP server.
 *
 * @author John Sublett on 20 Sep 2000
 * @since Niagara 3.0
 */
public class HttpsConnection
  extends HttpConnection
{
  /**********************************************
   * Constructor.
   ***********************************************/

  public HttpsConnection(BHost host, int port, String uri, SocketFactory socketFactory)
  {
    super(host, port, uri);
    initSocketFactory(socketFactory);
  }

  public HttpsConnection(BHost host, int port, String uri)
  {
    super(host, port, uri);
    createSocketFactory(null);
  }

  public HttpsConnection(BHost host, int port, String uri, String clientAlias)
    throws Exception
  {
    super(host, port, uri);
    createSocketFactory(clientAlias);
  }

  /**
   * Constructor.
   */
  private HttpsConnection(BHost host, int port)
  {
    super(host, port);
    initSocketFactory(null);
  }

  private void createSocketFactory(String clientAlias)
  {
    try
    {
      socketFactory = getDefaultSocketFactory(clientAlias);
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }

  private void initSocketFactory(SocketFactory socketFactory)
  {
    try
    {
      if (socketFactory == null)
      {
        socketFactory = getDefaultSocketFactory(null);
      }
    }
    catch (IOException ignored)
    {
    }

    this.socketFactory = socketFactory;
  }

  /**
   * Post the specified buffer to the server.
   *
   * @param host        The destination host.
   * @param port        The destination port.
   * @param uri         The uri for the posted resource.
   * @param contentType The mime type of the posted resource.
   * @param buf         The content buffer.
   */
  public static int post(
    BHost host, int port, String uri,
    String contentType, byte[] buf
  )
    throws IOException
  {
    HttpConnection conn = new HttpsConnection(host, port, uri);
    conn.setRequestMethod(Http.METHOD_POST);
    if (contentType != null)
    {
      conn.setRequestHeader("content-type", contentType);
    }
    //NCCB-20553: Support jetty 9.3.9 duplicate content-length enforcement
    while (conn.getRequestHeader("content-length") != null)
    {
      conn.removeRequestHeader("content-length");
    }
    conn.setRequestHeader("content-length", buf.length);

    int rc = conn.connect(buf.length, new ByteArrayInputStream(buf));
    try
    {
      conn.readContent();
    }
    catch (Exception ignored)
    {
    }
    return rc;
  }

  public synchronized int connect(long contentLength, InputStream stream)
    throws IOException
  {
    if (hostHeader == null)
    {
      //make sure the host header field can correctly handle both IPv4 and IPv6
      //numeric addresses--instead of adding logic to handle this here,
      //redirect to utility class.
      BInternetAddress addr = new BInternetAddress(
        host.getHostname(),
        (port != Http.DEFAULT_HTTPS_PORT) ? port : -1
      );

      hostHeader = addr.getAuthority();
    }

    proxy = BHttpProxyService.get(host);
    if (proxy != null)
    {
      s = createSocket(proxy, host.getHostname(), port, connectionTimeout);
    }
    else
    {
      s = createSocket(((BIpHost)host).getInetAddress(), port, connectionTimeout);
    }

    in = new BufferedInputStream(s.getInputStream());
    out = new BufferedOutputStream(s.getOutputStream());
    pw = new PrintWriter(out, false);

    if (timeout > 0)
    {
      s.setSoTimeout(timeout);
    }

    return newRequest(startUri, contentLength, stream);
  }

  /**********************************************
   * Open the connection.  If doAuthenticate is
   * true, authentication requests will result
   * in an automatic retry using the userName and
   * password.
   * @param timeout in milliseconds
   * @since Niagara 3.6
   ***********************************************/
  public synchronized int connect(int timeout)
    throws IOException
  {
    if (hostHeader == null)
    {
      //make sure the host header field can correctly handle both IPv4 and IPv6
      //numeric addresses--instead of adding logic to handle this here,
      //redirect to utility class.
      BInternetAddress addr = new BInternetAddress(
        host.getHostname(),
        (port != Http.DEFAULT_HTTP_PORT) ? port : -1
      );

      hostHeader = addr.getAuthority();
    }

    proxy = BHttpProxyService.get(host);
    if (proxy != null)
    {
      s = createSocket(proxy, host.getHostname(), port, timeout);
    }
    else
    {
      s = createSocket(((BIpHost)host).getInetAddress(), port, timeout);
    }

    in = new BufferedInputStream(s.getInputStream());
    out = new BufferedOutputStream(s.getOutputStream());
    pw = new PrintWriter(out, false);

    if (timeout > 0)
    {
      s.setSoTimeout(timeout);
    }

    return newRequest(startUri);
  }

  private Socket createSocket(InetAddress host, int port, int timeout)
    throws IOException
  {
    if (socketFactory instanceof CryptoCoreClientSocketFactory)
    {
      return ((CryptoCoreClientSocketFactory)socketFactory).createSocket(host, port, timeout);
    }
    else
    {
      return socketFactory.createSocket(host, port);
    }
  }

  private Socket createSocket(BHttpProxyService proxy, String host, int port, int timeout)
    throws IOException
  {
    s = proxy.openSocket(connectionTimeout);
    s.setSoTimeout(timeout);
    out = new BufferedOutputStream(s.getOutputStream());
    pw = new PrintWriter(out, false);

    sendConnect();
    in = s.getInputStream();

    int statusCode = readResponse(in);
    if (statusCode < 200 || statusCode >= 300)
    {
      String message = getStatusMessage();
      proxy.setStatus(BStatus.makeFault(proxy.getStatus(), true));
      proxy.setFaultCause(message);
      BHttpProxyService.log.severe(message + " (HTTP " + statusCode + ")");
      throw new IOException("Failed to establish proxy connection: " + statusCode + ": " + message);
    }

    return ((SSLSocketFactory)socketFactory).createSocket(s, host, port, true);
  }

  @Override
  protected void handleProxyAuth(long contentLength)
  {
    sendConnect();
  }

  private void sendConnect()
  {
    pw.print("CONNECT ");
    pw.print(hostHeader);
    if(hostHeader.indexOf(':') < 0)
    { // If the port is not in the hostHeader then add it here
      pw.print(':');
      pw.print(port);
    }
    pw.print(' ');
    pw.print(HTTP_VERSION);
    pw.print(Http.CRLF);

    if (!proxy.getAuthenticationScheme().equals(BProxyAuthenticationType.none))
    {
      String authorizationHeader = proxy.getAuthentication(proxyChallenge, "CONNECT", "/");
      if (authorizationHeader != null)
      {
        pw.print("Proxy-Authorization: ");
        pw.print(authorizationHeader);
        pw.print(Http.CRLF);
      }
    }

    // Write the user agent.
    String ua = (userAgent != null) ? userAgent : defUserAgent;
    if (ua != null)
    {
      pw.print("user-agent: ");
      pw.print(ua);
      pw.print(Http.CRLF);
    }

    pw.print(Http.CRLF);
    pw.flush();
  }

  /**********************************************
   * Initiate a new request on the same connection.
   ***********************************************/
  public synchronized int newRequest(String uri, long contentLength, InputStream stream)
    throws IOException
  {
    if (!allowMultipleRequests)
    {
      throw new IOException("This connection doesn't allow multiple requests.");
    }

    if (!isOpen())
    {
      startUri = uri;
      return connect(contentLength, stream);
    }
    else
    {
      writeRequest(uri, contentLength, stream);
      return readResponse(in);
    }
  }

  @Override
  protected void checkError()
    throws SSLException
  {
    boolean error;
    try
    {
      error = pw.checkError();
    }
    catch (Exception e)
    {
      error = true;
    }

    if (error)
    {
      SSLSession handshakeSession = ((SSLSocket)getSocket()).getHandshakeSession();
      if (handshakeSession != null)
      {
        Exception failureCause = (Exception)handshakeSession.getValue("tlsFailureCause");
        if (failureCause != null)
        {
          throw new SSLException("TLS connection failed", failureCause);
        }
      }
    }
  }

  public static SocketFactory getDefaultSocketFactory()
    throws IOException
  {
    return getDefaultSocketFactory(null);
  }

  public static SocketFactory getDefaultSocketFactory(String clientAlias)
    throws IOException
  {
    SocketFactory socketFactory;
    try
    {
      ICryptoManager service = CertManagerFactory.getInstance();
      socketFactory = service.getClientSocketFactory(ClientTlsParameters.DEFAULT);
    }
    catch (Exception e)
    {
      throw new IOException(e.getMessage());
    }

    return socketFactory;
  }

  private SocketFactory socketFactory = null;
}
