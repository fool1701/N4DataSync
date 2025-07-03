/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import java.io.ByteArrayInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.naming.BIpHost;
import javax.baja.net.Http;
import javax.baja.net.HttpConnection;
import javax.baja.net.HttpsConnection;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Clock;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import com.tridium.ndriver.comm.IComm;
import com.tridium.ndriver.comm.ICommListener;
import com.tridium.ndriver.comm.NCommException;
import com.tridium.ndriver.datatypes.BCommConfig;
import com.tridium.ndriver.datatypes.BHttpCommConfig;
import com.tridium.ndriver.datatypes.BIpAddress;
import com.tridium.ndriver.util.SpyUtil;

/**
 * HttpComm provides comm access to specified devices using the http protocol.
 *
 * @author Robert A Adams
 * @creation Nov 14, 2011
 */
public class HttpComm
  implements IComm
{
  public HttpComm(BHttpCommConfig comCfg, ICommListener defaultListener)
  {
    this.comCfg = comCfg;
  }

  /**
   * Called from BCommConfig when network is started.
   */
  @Override
  public void start() throws Exception
  {
    log().fine("start HttpComm");

    done = false;
  }

  /**
   * Called from BCommConfig when network is stopped.
   */
  @Override
  public void stop()
  {
    done = true;

    // Stop linkSessions and clear hash 
    Enumeration<LinkSession> en = sessions.elements();
    while (en.hasMoreElements())
    {
      LinkSession t = en.nextElement();
      synchronized (t)
      {
        t.close();
        t.notify();
      }
    }
    sessions.clear();
  }

  /**
   * Called from BCommConfig when a property changes.
   */
  @Override
  public void verifySettings(BCommConfig comCfg) throws Exception
  {
  }

  /**
   * Set the default listener.
   */
  @Override
  public void setDefaultListener(ICommListener listener)
  {
  }


////////////////////////////////////////////////////////////////
// Link Api
////////////////////////////////////////////////////////////////

  /**
   * Send and http request and blocks until response received.
   *
   * @return NHttpResponse
   * @throws throw any comm exception
   */
  public NHttpResponse sendRequest(NHttpRequest msg)
    throws Exception
  {
    if (done)
    {
      return null;
    }

    // Get LinkSession for this address
    String key = msg.getAddress().toString();
    LinkSession ls = sessions.get(key);
    if (ls == null)
    {
      ls = new LinkSession();
      log().fine("new LinkSession for " + key);
      sessions.put(key, ls);
    }

    int retry = msg.getRetryCount();
    while (true)
    {
      try
      {
        if (log().isLoggable(Level.FINE))
        {
          log().fine("sendRequest to " + key + "\n" + msg.toTraceString());
        }
        NHttpResponse resp = (NHttpResponse)ls.doSend(msg, false);
        if (log().isLoggable(Level.FINE))
        {
          log().fine("response from " + key + "\n" + resp.toTraceString());
        }
        stats.msgSent++;
        return resp;
      }
      catch (Exception e)
      {
        if (retry-- <= 0)
        {
          throw e;
        }
      }
    }

  }

  /**
   * Send an http request that will open a stream connection. Blocks until
   * stream opened. Applications which receive NHttpStream object should call
   * close() when finished accessing stream.
   *
   * @return an NHttpStream which allows access to the connections input and
   * output streams.
   * @throws throw any comm exception
   */
  public NHttpStream openStream(NHttpRequest msg)
    throws Exception
  {
    if (done)
    {
      return null;
    }

    // Use link session to create stream but don't hash as each stream
    // needs separate HttpConnection
    LinkSession ls = new LinkSession();
    NHttpStream strm = (NHttpStream)ls.doSend(msg, true);
    stats.openStreamReq++;
    if (log().isLoggable(Level.FINE))
    {
      log().fine("openStream to " + msg.getAddress() + "\n" + msg.toTraceString());
    }
    return strm;

  }

////////////////////////////////////////////////////////////////
// LinkSession
////////////////////////////////////////////////////////////////

  /**
   * Provide support for a single HTTP connection.
   */
  private class LinkSession
  {
    LinkSession() {}

    // Send a message and make sure response can be received
    NHttpMessage doSend(NHttpRequest msg, boolean streamReq)
      throws Exception
    {
      try
      {
        BIpAddress ipAdr = (BIpAddress)msg.getAddress();

        boolean send = true;
        boolean firstPass = true;
        while (send) // allow resend to deal with authentication
        {
          // Must have HttpConnection 
          if (httpConn == null)
          {
            if (comCfg.getUseTls())
            {
              SSLSocketFactory defaultFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
              httpConn = new HttpsConnection(new BIpHost(ipAdr.getIpAddress()), ipAdr.getPort(), null, defaultFactory);
            }
            else
            {
              httpConn = new HttpConnection(new BIpHost(ipAdr.getIpAddress()), ipAdr.getPort());
            }
          }

          // If available add authorization entry to request
          /* boolean authenticated = */
          auth.addAuthorization(msg, log);
          // Set timeout per message
          httpConn.setTimeout(msg.getResponseTimeOut());
          httpConn.setConnectionTimeout(comCfg.getConnectionTimeout());

          // Init httpConn to send request
          httpConn.setRequestMethod(msg.getMethod());

          Map<String, List<String>> headers = msg.getHeaderMap();
          for (Map.Entry<String, List<String>> entry : headers.entrySet())
          {
            String name = entry.getKey();
            for (String value : entry.getValue())
            {
              httpConn.setRequestHeader(name, value);
            }
          }

          String uri = msg.getUri();
          byte[] buf = msg.getData();
          int rc;

          // newRequest will connect socket, send the request, read the response headers
          if (buf == null)
          {
            rc = httpConn.newRequest(uri);
          }
          else
          {
            rc = httpConn.newRequest(msg.getUri(), buf.length, new ByteArrayInputStream(buf));
          }

          // Check status for error response
          if (rc == Http.SC_OK || msg.isValidResponse(rc))
          {
            send = false; // we are good - no need to retry
          }
          else
          {
            // create error response
            NHttpErrorResponse rsp = new NHttpErrorResponse(ipAdr, httpConn, rc);
            stats.errorResponses++;
            // For UNAUTHORIZED errors attempt to extract challenge info and resend.
            if (rc != Http.SC_UNAUTHORIZED || !firstPass || !auth.receiveChallenge(msg, rsp, log))
            {
              throw new NCommException("Error " + rc + ":" + rsp.getErrorMessage());
            }
            firstPass = false;
            httpConn.close();
          }
        }

        // Create a response and get contents
        NHttpMessage resp;
        if (streamReq)
        {
          resp = new NHttpStream(ipAdr, httpConn);
          stats.streamsOpened++;
        }
        else
        {
          resp = new NHttpResponse(ipAdr, httpConn);
          stats.msgReceived++;
        }

        if (!streamReq)
        {
          if (httpConn.shouldClose() || msg.closeConn())
          {
            httpConn.close();
          }
          if (httpConn.shouldClose())
          {
            httpConn = null;
          }
        }
        return resp;

      }
      catch (Exception e)
      {
        close();
        httpConn = null;
        throw e;
      }
      finally
      {
      }
    }

    // Close the socket
    void close()
    {
      if (httpConn != null)
      {
        httpConn.close();
      }
    }

    HttpConnection httpConn = null;
  }

////////////////////////////////////////////////////////////////
//Spy
////////////////////////////////////////////////////////////////

  /**
   * Provide some spy debug
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps("HttpComm");
    out.prop("done", done);
    out.prop("number of linkSessions", sessions.size());
    out.endProps();

    auth.spy(out);

    SpyUtil.spy(out, "HttpComm Statistics", stats);

  }

  /**
   * Container class for statistics counters.
   */
  public static class Statistics
  {
    long startTime = Clock.millis();

    public String getStartTime()
    {
      return BAbsTime.make(startTime).toLocalTime().toString(null);
    }

    public long msgSent = 0;
    public long openStreamReq = 0;
    public long streamsOpened = 0;
    public long msgReceived = 0;
    public long errorResponses = 0;
  }

  /**
   * Called when user invokes resetStats action on commConfig object.
   */
  @Override
  public void resetStats()
  {
    stats = new Statistics();
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////
  public final Logger log()
  {
    if (log == null)
    {
      log = Logger.getLogger(comCfg.getResourcePrefix() + ".HttpComm");
    }
    return log;
  }

  private Logger log;

  private BHttpCommConfig comCfg;

  private boolean done = true;
  private Statistics stats = new Statistics();

  // Sessions hash
  Hashtable<String, LinkSession> sessions = new Hashtable<>(30);
  AuthUtil auth = new AuthUtil();
  
  Thread rcvThread;
}
