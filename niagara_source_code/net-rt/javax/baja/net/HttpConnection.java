/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.net.ssl.SSLException;

import javax.baja.file.BajaFileUtil;
import javax.baja.naming.BHost;
import javax.baja.status.BStatus;

import com.tridium.net.BProxyAuthenticationType;
import com.tridium.net.ChunkedOutputStream;
import com.tridium.net.HttpDateFormat;
import com.tridium.net.HttpInputStream;
import com.tridium.sys.Nre;
import com.tridium.sys.module.ModuleManager;

/**
 * HttpConnection allows client applications to submit requests to
 * an HTTP server.
 *
 * @author John Sublett on 20 Sep 2000
 * @since Niagara 3.0
 */
public class HttpConnection
{
  /**********************************************
   * Constructor.
   ***********************************************/
  public HttpConnection(BHost host, int port, String uri)
  {
    this(host, port);
    this.startUri = uri;
  }

  /**
   * Constructor.
   */
  public HttpConnection(BHost host, int port)
  {
    this.host = host;
    this.port = port;
    this.requestHeaderFields = new Vector<>(5);
    this.responseHeaderFields = new HashMap<>(5);
    timeout = 0;
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
    HttpConnection conn = new HttpConnection(host, port, uri);
    conn.setRequestMethod(Http.METHOD_POST);
    if (contentType != null)
    {
      conn.setRequestHeader("content-type", contentType);
    }

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

  /**
   * Set the default user agent to use for all connections.
   */
  public static void setDefaultUserAgent(String defUserAgent)
  {
    HttpConnection.defUserAgent = defUserAgent;
  }

  /**
   * Set the user agent to use for this connection.
   */
  public void setUserAgent(String userAgent)
  {
    this.userAgent = userAgent;
  }

  /************************************************
   * Set the SO_TIMEOUT option on the connection.  If
   * this is set to 0 or never set, there is no timeout.
   *************************************************/
  public void setTimeout(int timeout)
    throws SocketException
  {
    this.timeout = timeout;
    if (s != null)
    {
      s.setSoTimeout(timeout);
    }
  }

  /************************************************
   * Get the current SO_TIMEOUT option on the connection.
   *************************************************/
  public int getTimeout()
  {
    return timeout;
  }

  /************************************************
   * Set the connection timeout.  This timer will be use for
   * socket connection unless the connect(int timeout) api is used.
   * Default value is 0.
   * @throws IllegalArgumentException - If value passed is &lt; 0
   * @since 4.3.42
   *************************************************/
  public void setConnectionTimeout(int conTimeout)
  {
    if (conTimeout < 0)
    {
      throw new IllegalArgumentException("Value must be >=0");
    }
    connectionTimeout = conTimeout;
  }

  /************************************************
   * Get the connectionTimeout for this connection.
   * @since 4.3.42
   *************************************************/
  public int getConnectionTimeout()
  {
    return connectionTimeout;
  }

  /************************************************
   * Get the URI for the first resource retrieved with
   * this connection.
   *************************************************/
  public String getUri()
  {
    return startUri;
  }

  /**
   * Get the hostname for this connection
   */
  public String getRemoteHost()
  {
    return host.getHostname();
  }

  /************************************************
   * Get the request method.
   *************************************************/
  public String getRequestMethod()
  {
    return requestMethod;
  }

  /**********************************************
   * Set the request method for this connection.
   * The value is a string indicating the HTTP request
   * type (e.g. GET, POST, HEAD, etc.). Constants for
   * common values are defined on the class.
   ***********************************************/
  public void setRequestMethod(String method)
  {
    requestMethod = method;
  }

  /**********************************************
   * Set the specified  request header field to the
   * specified value.
   ***********************************************/
  public void setRequestHeader(String name, String value)
  {
    checkHeaderReset();
    requestHeaderFields.addElement(new NameValue(name, value));
  }

  /**********************************************
   * Set the specified  request header field to the
   * specified value.
   ***********************************************/
  public void setRequestHeader(String name, int value)
  {
    checkHeaderReset();
    requestHeaderFields.addElement(new NameValue(name, value));
  }

  /**********************************************
   * Set the specified  request header field to the
   * specified value.
   ***********************************************/
  public void setRequestHeader(String name, long value)
  {
    checkHeaderReset();
    requestHeaderFields.addElement(new NameValue(name, value));
  }

  /**********************************************
   * Set the specified  request header field to the
   * specified value.
   ***********************************************/
  public void setRequestHeader(String name, boolean value)
  {
    checkHeaderReset();
    requestHeaderFields.addElement(new NameValue(name, value));
  }

  /**********************************************
   * Check whether or not the header should be reset.
   ***********************************************/
  void checkHeaderReset()
  {
    if (requestComplete)
    {
      requestHeaderFields.removeAllElements();
      requestComplete = false;
    }
  }

  /**********************************************
   * Remove the request header field with the
   * specified name.
   ***********************************************/
  public void removeRequestHeader(String name)
  {
    for (int i = 0; i < requestHeaderFields.size(); i++)
    {
      NameValue nv = requestHeaderFields.elementAt(i);
      if (nv.name.equalsIgnoreCase(name))
      {
        requestHeaderFields.removeElementAt(i);
        return;
      }
    }
  }

  /************************************************
   * @deprecated
   *************************************************/
  @Deprecated
  public void setFollowRedirects(boolean follow)
  {
    // does nothing
  }

  /************************************************
   * @deprecated
   *************************************************/
  @Deprecated
  public boolean isRedirected()
  {
    // does nothing
    return false;
  }

  /**********************************************
   * Open the connection.  If doAuthenticate is
   * true, authentication requests will result
   * in an automatic retry using the userName and
   * password.
   ***********************************************/
  public synchronized int connect()
    throws IOException
  {
    return connect(0, null);
  }

  protected void createSocket(int timeout)
    throws IOException
  {
    // Is a proxy defined for connections to this
    // host? If yes, use it instead of connecting
    // directly to the destination host
    proxy = BHttpProxyService.get(host);
    if (proxy != null)
    {
      s = proxy.openSocket(connectionTimeout);
    }
    else
    {
      s = host.openSocket(port, connectionTimeout);
    }

    in = new BufferedInputStream(s.getInputStream());
    out = new BufferedOutputStream(s.getOutputStream());
    pw = new PrintWriter(out, false);

    if (timeout > 0)
    {
      s.setSoTimeout(timeout);
    }
  }

  /**
   * Send a new request over a new connection, with the specified request
   * body.  If doAuthenticate is true, authentication requests will result
   * in an automatic retry using the userName and password.
   *
   * @since Niagara 3.6
   */
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
        (port != Http.DEFAULT_HTTP_PORT) ? port : -1
      );

      hostHeader = addr.getAuthority();
    }

    createSocket(timeout);

    return newRequest(startUri, contentLength, stream);
  }

  /**
   * Send a new request over a new connection.  If doAuthenticate is true,
   * authentication requests will result in an automatic retry using
   * the userName and password.
   */
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

    createSocket(timeout);

    return newRequest(startUri);
  }

  /**
   * Send a new request, reusing an existing connection if available.
   */
  public synchronized int newRequest(String uri)
    throws IOException
  {
    return newRequest(uri, 0, null);
  }

  /**
   * Send a new request, reusing an existing connection if available.
   */
  public synchronized int newRequest(String uri, byte[] post)
    throws IOException
  {
    if (post == null || post.length == 0)
    {
      return newRequest(uri);
    }
    return newRequest(uri, post.length, new ByteArrayInputStream(post));
  }

  /**
   * Send a new request, reusing an existing connection if available.
   *
   * @since Niagara 3.6
   */
  public synchronized int newRequest(String uri, long contentLength, InputStream stream)
    throws IOException
  {
    if (!allowMultipleRequests)
    {
      throw new IOException("This connection doesn't allow multiple requests.");
    }

    requestContent = null;
    if (stream != null)
    {
      if (stream.markSupported())
      {
        requestContent = stream;
      }
      else
      {
        requestContent = new BufferedInputStream(stream);
      }
      // mark the stream so we can reset it if we need to re-send the request
      // with proxy authentication
      requestContent.mark(MARK_LIMIT);
    }

    if (!isOpen())
    {
      startUri = uri;
      return connect(contentLength, requestContent);
    }
    else
    {
      writeRequest(uri, contentLength, requestContent);
      return readResponse(in);
    }
  }

  /**
   * Initiate a POST request with the specified body content.
   *
   * @since Niagara 3.6
   */
  public synchronized int post(String uri, String contentType, long contentLength, InputStream stream)
    throws IOException
  {
    setRequestMethod(Http.METHOD_POST);
    setRequestHeader("content-type", contentType);
    removeRequestHeader("expect");

    return newRequest(uri, contentLength, stream);
  }

  /**
   * Initiate a POST request with the specified body content.
   */
  public synchronized int post(String uri, String contentType, byte[] buf)
    throws IOException
  {
    setRequestMethod(Http.METHOD_POST);
    setRequestHeader("content-type", contentType);
    removeRequestHeader("expect");

    return newRequest(uri, buf.length, new ByteArrayInputStream(buf));
  }

  /**
   * Initiate a PUT request with the specified body content.
   *
   * @since Niagara 3.6
   */
  public synchronized int put(String uri, String contentType, byte[] buf)
    throws IOException
  {
    setRequestMethod(Http.METHOD_PUT);
    setRequestHeader("content-type", contentType);
    removeRequestHeader("expect");

    return newRequest(uri, buf.length, new ByteArrayInputStream(buf));
  }

  /**
   * Initiate a PUT request with the specified body content.
   *
   * @since Niagara 3.6
   */
  public synchronized int put(String uri, String contentType, long contentLength, InputStream contents)
    throws IOException
  {
    setRequestMethod(Http.METHOD_PUT);
    setRequestHeader("content-type", contentType);
    removeRequestHeader("expect");

    return newRequest(uri, contentLength, contents);
  }

  /**
   * Read the POST response, and return the response status code.
   * <p>
   * The status code returned from the original
   * POST should be used instead. POST requests are no
   * longer a two round trip transaction.
   */
  public int postComplete()
    throws IOException
  {
    if (!requestMethod.equals(Http.METHOD_POST))
    {
      throw new IllegalStateException("Request was not a post.");
    }

    out.flush();
    responseHeaderFields.clear();
    return readResponse(in);
  }

  /**
   * Read the PUT response, and return the response status code.
   *
   * @deprecated The status code returned from the original
   * PUT should be used instead.  PUT requests are no
   * longer a two round trip transaction.
   */
  @SuppressWarnings("DeprecatedIsStillUsed")
  @Deprecated
  public int putComplete()
    throws IOException
  {
    if (!requestMethod.equals(Http.METHOD_PUT))
    {
      throw new IllegalStateException("Request was not a put.");
    }

    out.flush();
    responseHeaderFields.clear();
    return readResponse(in);
  }

  /**********************************************
   * Write the request to the output.
   ***********************************************/
  void writeRequest(String uri, long contentLength, InputStream stream)
    throws IOException
  {
    currentUri = uri;
    writeRequest(contentLength, stream);
  }

  void writeRequest(long contentLength, InputStream stream)
    throws IOException
  {
    if (stream != null)
    {
      //NCCB-20553: Support jetty 9.3.9 duplicate content-length enforcement
      while (getRequestHeader("Content-Length") != null)
      {
        contentLength = Long.parseLong(getRequestHeader("Content-Length"));
        removeRequestHeader("Content-Length");
      }
      setRequestHeader("Content-Length", contentLength);
    }

    pw.print(requestMethod);
    pw.print(' ');

    if (proxy != null)
    {
      pw.print("http://" + hostHeader + currentUri);
    }
    else
    {
      pw.print(currentUri);
    }

    pw.print(' ');
    pw.print(HTTP_VERSION);
    pw.print(Http.CRLF);

    if (proxy != null && !proxy.getAuthenticationScheme().equals(BProxyAuthenticationType.none))
    {
      String authorizationHeader = proxy.getAuthentication(proxyChallenge, requestMethod, currentUri);
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

    // Write the host header
    pw.print("host: ");
    pw.print(hostHeader);
    pw.print(Http.CRLF);

    //Write the rest of headers
    for (int i = 0; i < requestHeaderFields.size(); i++)
    {
      NameValue nv = requestHeaderFields.elementAt(i);

      pw.print(nv.name);
      pw.print(": ");
      pw.print(nv.value);
      pw.print(Http.CRLF);
    }

    pw.print(Http.CRLF);

    pw.flush();

    //HTTP spec does not actually disallow including a body in a GET request,
    //and it's necessary to open a websocket. -LAB20121003
    //if(stream!=null && (getRequestMethod()==Http.METHOD_POST || getRequestMethod()==Http.METHOD_PUT))
    if (stream != null)
    {
      BajaFileUtil.pipe(stream, out);
      out.flush();
    }

    checkError();

    requestComplete = true;
    responseHeaderFields.clear();
  }

  protected int readResponse(InputStream in)
    throws IOException
  {
    return readResponse(in, true);
  }

  /**********************************************
   * Read the response from the input. Make sure
   * that we correctly handle unexpected EOF, i.e.
   * propagate the exception.
   ***********************************************/
  protected int readResponse(InputStream in, boolean retry)
    throws IOException
  {
    statusLine = new StatusLine(in);

    StringBuilder responseBuilder = new StringBuilder(30);

    while (true)
    {
      String name;
      String value;

      responseBuilder.setLength(0);

      int ch = in.read();

      // Detect termination sequence CRLF indicating an empty message
      if (ch == Http.CR)
      {
        // Ignore assumed LF
        //noinspection UnusedAssignment
        ch = in.read();
        break;
      }
      // PacMan 24054 - also tolerate LF only terminations
      if (ch == Http.LF)
      {
        break;
      }

      if (ch == -1)
      {
        close();
        throw new EOFException("End of input while reading response.");
      }

      while ((ch != -1) && (ch != ':'))
      {
        responseBuilder.append((char)ch);
        ch = in.read();
      }

      if (ch == -1)
      {
        close();
        throw new EOFException("End of input while reading response.");
      }

      name = responseBuilder.toString();

      ch = in.read();
      while (ch == ' ')
      {
        ch = in.read();
      }

      if (ch == -1)
      {
        close();
        throw new EOFException("End of input while reading response.");
      }

      responseBuilder.setLength(0);
      // read until eof(error) or CRLF(good) or LF(tolerable)
      while ((ch != -1) && (ch != Http.CR) && (ch != Http.LF))
      {
        responseBuilder.append((char)ch);
        ch = in.read();
      }

      if (ch == -1)
      {
        close();
        throw new EOFException("End of input while reading response.");
      }
      else if (ch == Http.CR)
      {
        // Ignore assumed LF
        //noinspection UnusedAssignment
        ch = in.read();
      }

      value = responseBuilder.toString();

      List<String> values = responseHeaderFields.computeIfAbsent(name.toLowerCase(), key -> new ArrayList<>());
      values.add(value);
    }

    String connHeader = getResponseHeader("Connection");
    if ((connHeader != null) && connHeader.equalsIgnoreCase("close"))
    {
      if (statusLine.getStatusCode() != Http.SC_MOVED_TEMPORARILY)
      {
        allowMultipleRequests = false;
      }
    }

    if (proxy != null)
    {
      int statusCode = statusLine.getStatusCode();
      if (statusCode == 407 && retry)
      {
        proxyChallenge = getResponseHeader("Proxy-Authenticate");

        if (proxyChallenge != null && !proxyChallenge.isEmpty()
          && proxy.getAuthenticationScheme().getOrdinal() == BProxyAuthenticationType.DIGEST)
        {
          BHttpProxyService.log.fine("Received 407 from proxy, retrying with authentication.");
          readContent();

          // Special handling for Expect: 100 Continue. Recreate the socket so the
          // server doesn't think the new request is a continuation of the last one.
          if ("100-Continue".equalsIgnoreCase(getRequestHeader("Expect")))
          {
            s.close();
            createSocket(timeout);
            in = this.in;
          }

          if (requestContent != null)
          {
            requestContent.reset();
          }
          long contentLength = 0;
          String contentLengthString = getRequestHeader("content-length");
          if (contentLengthString != null)
          {
            contentLength = Long.parseLong(contentLengthString);
          }
          handleProxyAuth(contentLength);
          return readResponse(in, false);
        }
      }
      if (statusCode == 407 || statusCode == 502 || statusCode == 504)
      {
        proxy.setStatus(BStatus.makeFault(proxy.getStatus(), true));
        proxy.setFaultCause(statusLine.getMessage());
        BHttpProxyService.log.severe(statusLine.getMessage() + " (HTTP " + statusLine.getStatusCode() + ")");
      }
      else
      {
        proxy.setStatus(BStatus.makeFault(proxy.getStatus(), false));
        proxy.setFaultCause("");
      }
    }

    return statusLine.getStatusCode();
  }

  protected void handleProxyAuth(long contentLength)
    throws IOException
  {
    writeRequest(contentLength, requestContent);
  }

  /**********************************************
   * Get the version of the HTTP response.
   ***********************************************/
  public String getResponseVersion()
  {
    return statusLine.getVersion();
  }

  /**********************************************
   * Get the HTTP response status code.
   ***********************************************/
  public int getStatusCode()
  {
    return statusLine.getStatusCode();
  }

  /**********************************************
   * Get the HTTP response status message.
   ***********************************************/
  public String getStatusMessage()
  {
    return statusLine.getMessage();
  }

  /**********************************************
   * Get the content type.
   ***********************************************/
  public String getContentType()
  {
    return getResponseHeader("Content-Type");
  }

  /**********************************************
   * Get the content length.
   ***********************************************/
  public int getContentLength()
  {
    return getResponseHeaderInt("Content-Length");
  }

  /**
   * Read the content of the current response from the input.
   */
  public byte[] readContent()
    throws IOException
  {
    InputStream in = getInputStream(); // Issue 14641 : HttpConnection.readContent() locks up for Transfer-Encoding: chunked

    byte[] result;
    int size = getContentLength();

    if (size != -1)
    {
      result = new byte[size];
      DataInputStream dataIn = new DataInputStream(in);
      dataIn.readFully(result);
      dataIn.close();
    }
    else
    {
      byte[] temp = new byte[1024];
      ByteArrayOutputStream bOut = new ByteArrayOutputStream(1024);
      int thisRead;
      while ((thisRead = in.read(temp, 0, 1024)) != -1)
      {
        bOut.write(temp, 0, thisRead);
      }
      result = bOut.toByteArray();
    }

    return result;
  }

  /************************************************
   * Get the value of the specified request header.
   *************************************************/
  public String getRequestHeader(String name)
  {
    if (requestHeaderFields == null)
    {
      return null;
    }

    for (int i = 0; i < requestHeaderFields.size(); i++)
    {
      NameValue nv = requestHeaderFields.elementAt(i);
      if (nv.name.equalsIgnoreCase(name))
      {
        return nv.value;
      }
    }

    return null;
  }

  /**********************************************
   * Get the specified header field value.
   ***********************************************/
  public String getResponseHeader(String name)
  {
    List<String> values = responseHeaderFields.get(name.toLowerCase());
    if (values != null && !values.isEmpty())
    {
      return values.get(values.size() - 1);
    }
    return null;
  }

  /**
   * Get a list of all response headers matching the given name, ignoring case.
   *
   * @param name header name
   * @return list of header values
   * @since Niagara 4.10
   */
  public List<String> getResponseHeaders(String name)
  {
    return responseHeaderFields.get(name.toLowerCase());
  }

  /**********************************************
   * Get a list of the response header fields.
   ***********************************************/
  public Enumeration<String> getResponseHeaderNames()
  {
    return Collections.enumeration(responseHeaderFields.keySet());
  }

  /**********************************************
   * Get the specified header field value as an int.
   ***********************************************/
  public int getResponseHeaderInt(String name)
  {
    String val = getResponseHeader(name);
    if (val != null)
    {
      try
      {
        return Integer.parseInt(val);
      }
      catch (Exception e)
      {
        return -1;
      }
    }
    else
    {
      return -1;
    }
  }

  /**********************************************
   * Get the specified header field value as a date.
   ***********************************************/
  public long getResponseHeaderDate(String name)
  {
    String val = getResponseHeader(name);
    if (val != null)
    {
      try
      {
        return HttpDateFormat.parse(val);
      }
      catch (Exception e)
      {
        return -1;
      }
    }
    else
    {
      return -1;
    }
  }

  /**********************************************
   * Get an input stream for reading from this connection.
   ***********************************************/
  public InputStream getInputStream()
    throws IOException
  {
    if (!isOpen())
    {
      throw new IOException("Connection is not open");
    }

    int contentLength = getResponseHeaderInt("Content-Length");
    String transferEncoding = getResponseHeader("Transfer-Encoding");

    //Handle chunked encoding
    if ((transferEncoding != null) &&
      transferEncoding.equalsIgnoreCase(Http.TRANSFER_CHUNKED))
    {
      return new HttpInputStream(in, true);
    }

    if (getRequestMethod().equalsIgnoreCase(Http.METHOD_HEAD))
    {
      //NCCB-7952: Do not anticipate any content on HEAD method,
      //The content-length and content-type may be there, but
      //it is only for meta purposes
      contentLength = 0;
    }

    return new HttpInputStream(in, contentLength);
  }

  /**********************************************
   * Get an output stream for writing to this connection.
   ***********************************************/
  public OutputStream getOutputStream()
    throws IOException
  {
    if (!isOpen())
    {
      throw new IOException("Connection is not open");
    }

    String transferEncoding = getRequestHeader("Transfer-Encoding");
    if ((transferEncoding != null) &&
      transferEncoding.equalsIgnoreCase(Http.TRANSFER_CHUNKED))
    {
      transferChunked = true;
      return new ChunkedOutputStream(out, 2048);
    }
    else
    {
      transferChunked = false;
      return out;
    }
  }

  /**********************************************
   * Is the connection chunked?
   ***********************************************/
  public boolean isTransferChunked()
  {
    return transferChunked;
  }

  /**********************************************
   * Is the connection open?
   ***********************************************/
  public boolean isOpen()
  {
    return s != null;
  }

  /**********************************************
   * Should this connection be closed after a single
   * request?
   ***********************************************/
  public boolean shouldClose()
  {
    return !allowMultipleRequests;
  }

  /**********************************************
   * Close the connection.
   ***********************************************/
  public void close()
  {
    if (s != null)
    {
      try
      {
        pw.flush();
      }
      catch (Exception ignored)
      {
      }
      try
      {
        in.close();
      }
      catch (Exception ignored)
      {
      }
      try
      {
        out.flush();
      }
      catch (Exception ignored)
      {
      }
      try
      {
        out.close();
      }
      catch (Exception ignored)
      {
      }
      try
      {
        s.close();
      }
      catch (Exception ignored)
      {
      }

      pw = null;
      in = null;
      out = null;
      s = null;

      // PacMan 22479  - change made for 3.8.1
      // reset following to allow reuse of HttpConnection instance
      allowMultipleRequests = true;
      transferChunked = false;
      requestHeaderFields.clear();
    }
  }

  /**********************************************
   * Dump the response header.
   ***********************************************/
  public String dumpResponseHeader()
  {
    StringBuilder responseHeader = new StringBuilder(100);
    responseHeader.append(statusLine).append('\n');

    for (Map.Entry<String, List<String>> entry : responseHeaderFields.entrySet())
    {
      responseHeader.append("   ").append(entry.getKey()).append(":\n");
      for (String value : entry.getValue())
      {
        responseHeader.append(value).append('\n');
      }
    }

    return responseHeader.toString();
  }

///////////////////////////////////////////////////////////
// Status checks
///////////////////////////////////////////////////////////

  /**********************************************
   * Make sure the returned status code is was OK (200).
   ***********************************************/
  public void checkOk()
    throws HttpException
  {
    checkStatus(Http.SC_OK);
  }

  /**********************************************
   * Make sure the returned status code is what
   * was expected.  If the actual status is
   * unexpected, the connection is closed and
   * an HttpException is thrown.
   ***********************************************/
  public void checkStatus(int expectedStatus)
    throws HttpException
  {
    int statusCode = getStatusCode();
    if (statusCode != expectedStatus)
    {
      close();
      throw new HttpException(expectedStatus, statusCode);
    }
  }

  /**
   * This method is called after the connection has completed (successfully or unsuccessfully), and gives
   * a chance to throw an exception if anything went wrong
   *
   * @throws SSLException if the TLS connection fails (HTTPS sub-classes only).
   * @since Niagara 4.11
   */
  protected void checkError()
    throws SSLException
  {
    // No-op: regular HTTP connections will throw an appropriate error when used so
    // there's no need for an additional error check
  }

///////////////////////////////////////////////////////////
// Inner classes
///////////////////////////////////////////////////////////

  /**********************************************
   * The StatusLine handles parsing of the HTTP
   * response status line.
   ***********************************************/
  protected class StatusLine
  {
    /**********************************************
     * Constructor.
     ***********************************************/
    public StatusLine(InputStream in)
      throws IOException
    {
      this.in = in;
      readVersion();
      readStatusCode();
      readMessage();
    }

    /**********************************************
     * Read the HTTP version.
     ***********************************************/
    protected void readVersion()
      throws IOException
    {
      StringBuilder versionBuilder = new StringBuilder(8);
      int ch = in.read();

      while ((ch != -1) && (ch != ' '))
      {
        versionBuilder.append((char)ch);
        ch = in.read();
      }

      if (ch == -1)
      {
        close();
        throw new EOFException("End of input while reading version.");
      }

      version = versionBuilder.toString();
    }

    /**********************************************
     * Get the HTTP version of the response.
     ***********************************************/
    public String getVersion()
    {
      return version;
    }

    /**********************************************
     * Read the status code.
     ***********************************************/
    protected void readStatusCode()
      throws IOException
    {
      StringBuilder statusBuilder = new StringBuilder(3);
      int ch = in.read();

      while ((ch != -1) && (ch != ' '))
      {
        statusBuilder.append((char)ch);
        ch = in.read();
      }

      if (ch == -1)
      {
        close();
        throw new EOFException("End of input while reading status code.");
      }

      statusCode = Integer.parseInt(statusBuilder.toString());
    }

    /**********************************************
     * Get the status code.
     ***********************************************/
    public int getStatusCode()
    {
      return statusCode;
    }

    /**********************************************
     * Read the reason message.
     ***********************************************/
    protected void readMessage()
      throws IOException
    {
      StringBuilder messageBuilder = new StringBuilder(25);
      int ch = in.read();

      while ((ch != -1) && (ch != Http.CR))
      {
        messageBuilder.append((char)ch);
        ch = in.read();
      }

      if (ch == -1)
      {
        close();
        throw new EOFException("End of input while reading reason phrase.");
      }

      //ignore following LF
      //noinspection ResultOfMethodCallIgnored
      in.read();

      message = messageBuilder.toString();
    }

    /**********************************************
     * Get the response message.
     ***********************************************/
    public String getMessage()
    {
      return message;
    }

    /**********************************************
     * Get this status line as a string.
     ***********************************************/
    @Override
    public String toString()
    {
      return version + " " + statusCode + " " + message;
    }

    ///////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////

    InputStream in;

    String version;
    int statusCode;
    String message;
  }

  /**********************************************
   * Encapsulation of a name/value pair.
   ***********************************************/
  static class NameValue
  {
    public NameValue(String name, String value)
    {
      this.name = name;
      this.value = value;
    }

    public NameValue(String name, int value)
    {
      this.name = name;
      this.value = Integer.toString(value);
    }

    public NameValue(String name, long value)
    {
      this.name = name;
      this.value = String.valueOf(value);
    }

    public NameValue(String name, boolean value)
    {
      this.name = name;
      this.value = String.valueOf(value);
    }

    public String name;
    public String value;
  }

  protected StatusLine getStatusLine()
  {
    return statusLine;
  }

  protected void setStatusLine(StatusLine sl)
  {
    statusLine = sl;
  }

  public Socket getSocket()
  {
    return s;
  }

  /**
   * @return a BufferedInputStream that wraps the Socket's InputStream
   * @since Niagara 4.0
   */
  protected InputStream getSocketInputStream()
  {
    return in;
  }

  /**
   * @return a BufferedOutputStream that wraps the Socket's OutputStream
   * @since Niagara 4.0
   */
  protected OutputStream getSocketOutputStream()
  {
    return out;
  }

  /**
   * @return a PrinterWriter that wraps the SocketOutputStream.  Note that this
   * PrintWriter does not auto-flush.
   * @since Niagara 4.0
   */
  protected PrintWriter getPrintWriter()
  {
    return pw;
  }

  /**
   * @return the authority string of the BInternetAddress created from
   * the hostname and port
   * @since Niagara 4.0
   */
  public String getHostAuthority()
  {
    return hostHeader;
  }

///////////////////////////////////////////////////////////
// Constants
///////////////////////////////////////////////////////////

  public static final String HTTP_VERSION = "HTTP/1.1";
  private static final int MARK_LIMIT = AccessController.doPrivileged(
    (PrivilegedAction<Integer>)() -> Integer.getInteger("net.http.markLimit", 8192));

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  String startUri;
  String currentUri;
  String requestMethod = Http.METHOD_GET;
  String hostHeader;
  StatusLine statusLine;
  String proxyChallenge;
  boolean allowMultipleRequests = true;
  boolean requestComplete = true;
  boolean transferChunked = false;

  String userAgent;
  static String defUserAgent = "Niagara";

  static
  {
    try
    {
      defUserAgent += "/" + AccessController.doPrivileged((PrivilegedAction<ModuleManager>)Nre::getModuleManager).getModuleForClass(HttpConnection.class).getVendorVersion();
    }
    catch (Throwable e)
    {
      System.out.println("SEVERE: Failed to determine default user-agent (" + e + ")");
    }
  }

  Socket s;
  BufferedOutputStream out;
  PrintWriter pw;
  InputStream in;
  InputStream requestContent;
  int timeout;
  int connectionTimeout = 0;
  int port;
  BHost host;
  BHttpProxyService proxy;

  Vector<NameValue> requestHeaderFields;
  private final Map<String, List<String>> responseHeaderFields;
}