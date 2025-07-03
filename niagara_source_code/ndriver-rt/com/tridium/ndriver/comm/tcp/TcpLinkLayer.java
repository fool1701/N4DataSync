/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.nre.util.IntHashMap;
import javax.baja.spy.SpyWriter;

import com.tridium.driver.util.DrByteArrayUtil;
import com.tridium.ndriver.comm.DebugStream;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.LinkMessage;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.NCommException;
import com.tridium.ndriver.comm.NLinkMessageFactory;
import com.tridium.ndriver.datatypes.BCommConfig;
import com.tridium.ndriver.datatypes.BIpAddress;
import com.tridium.ndriver.datatypes.BTcpCommConfig;
import com.tridium.ndriver.util.SpyUtil;

/**
 * LinkLayer implementation for TCP interface.  Send and receive messages using
 * tcp sockets.
 *
 * @author Robert A Adams
 * @creation Oct 23, 2011
 */
public class TcpLinkLayer
  implements ILinkLayer
{
  public TcpLinkLayer(NComm comm, BTcpCommConfig comCfg)
  {
    this.comm = comm;
    this.comCfg = comCfg;
    this.lnkFac = comCfg.getLinkMessageFactory();
  }

  /**
   * Start linklayer
   */
  @Override
  public void start() throws Exception
  {
    log().fine("start TcpLinkLayer");
    done = false;

    if (!comCfg.getServerEnabled())
    {
      if (log().isLoggable(Level.FINE))
      {
        log().fine("comm config server disabled, skipping server startup");
      }
    }
    else if (myPort() == 0)
    {
      log().info("comm config port set to 0, skipping server startup");
    }
    else
    {
      startServer();
    }
  }

  /**
   * Stop linklayer
   */
  @Override
  public void stop()
  {
    done = true;
    stopServer();

    // Stop linkSessions and clear hash
    for (LinkSession t : sessions.values())
    {
      synchronized (t)
      {
        t.listening = false;
        t.close();
        t.notify();
      }
    }
    sessions.clear();
  }

  private void startServer() throws IOException
  {
    // If ip address specified then use for serverSocket
    InetAddress ia = myIp();
    srvSock = ia == null ? new ServerSocket(myPort())
      : new ServerSocket(myPort(), 5, ia);
    serverRunning = true;

    //  Start the Receive thread
    receive = new LinkServer();
    rcvThread = new Thread(receive, comCfg.getResourcePrefix() + ".LinkServer");
    rcvThread.start();
  }

  private void stopServer()
  {
    serverRunning = false;
    // Stop serverSocket thread
    try
    {
      if (srvSock != null)
      {
        srvSock.close();
      }
    }
    catch (Throwable e)
    {
    }

    // Stop and remove server sessions
    Iterator<Map.Entry<String, LinkSession>> iterator = sessions.entrySet().iterator();
    while (iterator.hasNext())
    {
      Map.Entry<String, LinkSession> entry = iterator.next();
      LinkSession t = entry.getValue();
      if (t.server)
      {
        synchronized (t)
        {
          t.listening = false;
          t.close();
          t.notify();
        }
        iterator.remove();
      }
    }
  }

  /**
   * Called when comm config parameter changes.  Recreate ServerSocket if port
   * changed.
   */
  @Override
  public void verifySettings(BCommConfig comCfg) throws Exception
  {
    boolean enabled = ((BTcpCommConfig)comCfg).getServerEnabled() && myPort() != 0;
    if (enabled && !serverRunning)
    {
      startServer();
    }
    else if (!enabled && serverRunning)
    {
      stopServer();
    }
    // Make sure there is a Socket with correct port
    else if (enabled && (srvSock == null || srvSock.getLocalPort() != myPort()))
    {
      ServerSocket s = srvSock;
      srvSock = new ServerSocket(myPort());
      s.close();
    }

  }

  // Get my port from comm config
  private int myPort()
  {
    return comCfg.getAddress().getPort();
  }

  private InetAddress myIp()
  {
    BIpAddress myAdr = comCfg.getAddress();
    if (myAdr.getIpAddress().equalsIgnoreCase("local"))
    {
      return null;
    }
    return myAdr.getInetAddress();
  }

////////////////////////////////////////////////////////////////
// Link Api
////////////////////////////////////////////////////////////////

  /**
   * Entry point for NComm to send message to link layer
   */
  @Override
  public void sendMessage(LinkMessage msg)
    throws Exception
  {
    if (done)
    {
      return;
    }
    BIpAddress addr = (BIpAddress)msg.address;

    LinkSession ls = getLinkSession(addr);
    log().fine("sendMessage to " + msg.address);
    ls.doSend(msg);
    stats.msgSent++;
  }

  private synchronized LinkSession getLinkSession(BIpAddress address)
    throws Exception
  {
    // Try finding session by sessionId first - this will use session created
    // by incoming messages.
    LinkSession ls = (LinkSession)sessionsById.get(address.getSessionId());

    if (ls == null)
    {
      // Get LinkSession for this address
      String key = address.toString();
      ls = sessions.get(key);
      if (ls == null)
      {
        ls = new LinkSession(address);
        log().fine("created LinkSession for " + address);
        sessions.put(key, ls);
      }
    }

    return ls;
  }

  /**
   * Create a session for the specified address and return a unique
   * sessionId.<p> Messages can be sent to this session by setting the sessionId
   * on BIpAddress of outgoing messages.
   * <p>Call {@code closeSession(int sessionId)} when the
   * session is no longer needed.
   */
  public int createSession(BIpAddress tcpAdr)
    throws Exception
  {
    int sessionId = getNextSessionId();
    LinkSession ls = new LinkSession(tcpAdr, sessionId);
    sessionsById.put(sessionId, ls);
    log().fine("created LinkSession " + sessionId + " for " + tcpAdr);
    return sessionId;
  }

  /**
   * Close a session created by a call to {@code createSession()} and
   * cleanup associated resources.
   */
  public void closeSession(int sessionId)
    throws Exception
  {
    LinkSession ls = (LinkSession)sessionsById.remove(sessionId);
    if (ls == null)
    {
      return;
    }

    if (log().isLoggable(Level.FINE))
    {
      log().fine("close LinkSession " + sessionId + " for " + ls.addr);
    }
    
    synchronized (ls)
    {
      ls.listening = false;
      ls.close();
      ls.notify();
    }
  }

////////////////////////////////////////////////////////////////
// LinkSession
////////////////////////////////////////////////////////////////

  /**
   * Provide support for a single TCP connection.
   */
  private class LinkSession
    implements Runnable
  {
    LinkSession(BIpAddress tcpAdr)
      throws Exception
    {
      this.addr = (BIpAddress)tcpAdr.newCopy(true);
      server = false;
      timeOut = comCfg.getSendSocketTO() * 1000;
    }

    // create linkSession for specific sessionId
    LinkSession(BIpAddress tcpAdr, int sessionId)
      throws Exception
    {
      this.addr = (BIpAddress)tcpAdr.newCopy(true);
      addr.setSessionId(sessionId); // set sessionId on address so it is available to event listener
      server = false;
      timeOut = 0;
    }

    // linkSession for incoming socket
    LinkSession(Socket s)
      throws Exception
    {
      this.sock = s;
      this.addr = new BIpAddress(sock.getInetAddress(), sock.getPort());
      // Assign a sessionId and store in this sessions address object
      addr.setSessionId(getNextSessionId());

      sessions.put(addr.toString(), this);
      server = true;
      timeOut = comCfg.getServerSocketTO() * 1000;
    }

    @Override
    public void run()
    {
      synchronized (this)
      {
        notifyAll();
      }
      listening = true;

      // Set timeout
      try
      {
        sock.setSoTimeout(timeOut);
      }
      catch (SocketException e)
      {
        log().log(Level.SEVERE, "Unable to set socket timeout " + addr, e);
      }

      // Receive messages
      while (listening)
      {
        boolean trace = log().isLoggable(Level.FINE);
        try
        {
          // Create linkMessage and use its buffer in packet to receive data
          LinkMessage tmsg = lnkFac.getLinkMessage();
          tmsg.address = addr;
          InputStream in = sock.getInputStream();
          if (trace)
          {
            in = debIn.reset(in);  // if tracing use debug wrapper stream
          }
          boolean complete = tmsg.receive(in);
          // Dump any debug accumulated by receive - dump bytes even if complete message not rcvd
          if (trace && debIn.hasDebug())
          {
            log().fine("rcvd:" + (complete ? "" : "frag:") + debIn.debugString());
          }

          if (complete)
          {
            comm.receiveMessage(tmsg);
            stats.msgReceived++;
            sent = false;
          }
          else
          {
            // Incomplete messages are due to remote port closing
            lnkFac.releaseLinkMessage(tmsg);
            if (listening && trace)
            {
              log().fine("Remote socket closed for " + addr);
            }
            terminate();
          }
        }
        catch (SocketTimeoutException e)
        {
          if (!sent)
          {
            if (listening)
            {
              if (trace)
              {
                log().log(Level.FINE, "Timeout LinkSession for " + addr, e);
              }
              stats.receiveError++;
            }
            terminate();
          }
        }
        catch (Throwable e)
        {
          if (listening)
          {
            String msg = "Exception caught in LinkReceiver for " + addr + "\n";
            if (trace)
            {
              log().log(Level.SEVERE, msg, e);
            }
            else 
            {
              log.log(Level.SEVERE, msg);
            }
            stats.receiveError++;
          }
          terminate();
        }
      }
    }

    // Make sure that there is a socket and thread to receive incoming messages.
    private synchronized void listen()
      throws Exception
    {
      if (sock == null)
      {
        sock = new Socket();
      }

      if (!sock.isConnected())
      {
        try
        {
          if (log().isLoggable(Level.FINE))
          {
            log().fine("connect socket " + addr);
          }
          InetSocketAddress insa = new InetSocketAddress(addr.getInetAddress(), addr.getPort());
          sock.connect(insa, 1000);
        }
        catch (Exception e)
        {
          sock = null;
          throw new NCommException("Can not connect to " + addr, e);
        }
      }
      if (!listening)
      {
        if (log().isLoggable(Level.FINE))
        {
          log().fine("start rcvThread " + addr);
        }
        Thread rcvThread = new Thread(this, comCfg.getResourcePrefix() + ".LinkSession");
        rcvThread.start();
        try
        {
          wait(1000);
        }
        catch (Throwable e)
        {
        }
      }
    }

    // Send a message and make sure response can be received
    synchronized void doSend(LinkMessage msg)
      throws Exception
    {
      try
      {
        listen();

        if (log().isLoggable(Level.FINE))
        {
          log().fine("send:" + DrByteArrayUtil.toString(msg.getByteArray(), msg.getLength()));
        }

        sock.getOutputStream().write(msg.getByteArray(), 0, msg.getLength());
        sent = true;
      }
      finally
      {
        lnkFac.releaseLinkMessage(msg);
      }
    }

    // Close the socket
    void close()
    {
      listening = false;
      try
      {
        if (sock != null)
        {
          sock.close();
        }
      }
      catch (Throwable e)
      {
        String msg = "close: can't close socket: " + e;
        if (log().isLoggable(Level.FINE))
        {
          log().log(Level.WARNING, msg, e);
        }
        else
        {
          log().log(Level.WARNING, msg);
        }
      }
    }

    void terminate()
    {
      log().fine("terminate " + addr);
      listening = false;
      try
      {
        if (sock != null)
        {
          sock.close();
        }
      }
      catch (Throwable e)
      {
        String msg = "terminate: can't close socket: " + e;
        if (log().isLoggable(Level.FINE))
        {
          log().log(Level.WARNING, msg, e);
        }
        else
        {
          log().log(Level.WARNING, msg);
        }
      }
      sessions.remove(addr.toString());
      sessionsById.remove(addr.getSessionId());

      // for event listners
      socketTerminated(addr, server);
    }

    Socket sock;
    BIpAddress addr;
    boolean listening = false;
    boolean server;
    boolean sent = false;
    int timeOut = 0;
    DebugStream debIn = new DebugStream(lnkFac.getLinkMaxLength());
  }

  private synchronized int getNextSessionId()
  {
    int n = nextSessionId;
    if (++nextSessionId < 0)
    {
      nextSessionId = 1;
    }
    return n;
  }

  private static int nextSessionId = 1;


////////////////////////////////////////////////////////////////
// LinkServer
////////////////////////////////////////////////////////////////

  /**
   * Provide support for a serverSocket to receive incoming connections.
   */
  private class LinkServer
    implements Runnable
  {
    @Override
    public void run()
    {
      while (serverRunning)
      {
        try
        {
          Socket s = srvSock.accept();
          LinkSession ls = new LinkSession(s);
          Thread t = new Thread(ls, comCfg.getResourcePrefix() + ".In.LinkSession" + cnt++);
          t.start();
          stats.acceptedConnections++;
        }
        catch (Throwable e)
        {
          if (!serverRunning)
          {
            return; // Shut down exception
          }
          String msg = "Exception caught in LinkReceiver. " + e;
          if (log().isLoggable(Level.FINE))
          {
            log().log(Level.SEVERE, msg, e);
          }
          else
          {
            log().log(Level.SEVERE, msg);
          }
        }
      }
    }

    int cnt = 0;
  }

////////////////////////////////////////////////////////////////
// EventListener support
////////////////////////////////////////////////////////////////

  /**
   * Register an {@code ITcpEventListener} to receive event callbacks.
   */
  public void registerTcpEvenListener(ITcpEventListener listener)
  {
    if (!evlisteners.contains(listener))
    {
      evlisteners.add(listener);
    }
  }

  /**
   * Unregister an {@code ITcpEventListener}.
   */
  public void unregisterTcpEvenListener(ITcpEventListener listener)
  {
    evlisteners.remove(listener);
  }

  private void socketTerminated(BIpAddress addr, boolean server)
  {
    for (int i = 0; i < evlisteners.size(); ++i)
    {
      evlisteners.elementAt(i).socketTerminated(addr, server);
    }
  }

  Vector<ITcpEventListener> evlisteners = new Vector<>();

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
    out.startProps("TcpLinkLayer");
    out.prop("done", done);
    out.prop("srvSock inet address", ((srvSock != null) ? srvSock.getInetAddress().toString() : "n/a"));
    out.prop("srvSock port", ((srvSock != null) ? Integer.toString(srvSock.getLocalPort()) : "n/a"));
    out.prop("number of linkSessions", sessions.size());
    out.endProps();

    out.startTable(true);
    out.trTitle("sessions", 8);
    out.w("<tr>").th("id").th("server").th("ip").th("port").th("myPort").th("bound").th("listening").th("soTimeout").w("</tr>\n");
    for (LinkSession c : sessions.values())
    {
      spySession(out, c);
    }
    out.endTable();

    out.startTable(true);
    out.trTitle("sessionsById", 8);
    out.w("<tr>").th("id").th("server").th("ip").th("port").th("myPort").th("bound").th("listening").th("soTimeout").w("</tr>\n");
    LinkSession[] a = (LinkSession[])sessionsById.toArray(new LinkSession[sessionsById.size()]);
    for (int i = 0; i < a.length; ++i)
    {
      spySession(out, a[i]);
    }
    out.endTable();

    SpyUtil.spy(out, "TcpLink Statistics", stats);
  }

  private void spySession(SpyWriter out, LinkSession c)
  {
    String sto = "n/a";
    try
    {
      sto = Integer.toString(c.sock.getSoTimeout());
    }
    catch (Throwable e)
    {
    }

    out.w("<tr>");
    out.td(Integer.toString(c.addr.getSessionId()));
    out.td(Boolean.toString(c.server));
    out.td(c.addr.getIpAddress());
    out.td(((c.sock != null) ? Integer.toString(c.sock.getPort()) : "n/a"));
    out.td(((c.sock != null) ? Integer.toString(c.sock.getLocalPort()) : "n/a"));
    out.td(((c.sock != null) ? Boolean.toString(c.sock.isBound()) : "n/a"));
    out.td(Boolean.toString(c.listening));
    out.td(sto);
    out.w("</tr>\n");
  }

  /**
   * Container class for statistics counters.
   */
  public static class Statistics
  {
    public long msgSent = 0;
    public long msgReceived = 0;
    public long receiveError = 0;
    public long acceptedConnections = 0;
  }

  /**
   * Clear all statistics counts. Called when user invokes resetStats action on
   * commConfig object.
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
      log = Logger.getLogger(comCfg.getResourcePrefix() + ".Link");
    }
    return log;
  }

  private Logger log;

  private NComm comm;
  private BTcpCommConfig comCfg;
  private NLinkMessageFactory lnkFac;

  private ServerSocket srvSock = null;
  private boolean done = true;
  private boolean serverRunning = false;
  private Statistics stats = new Statistics();

  // Sessions hash
  //Hashtable<String, LinkSession> sessions = new Hashtable<>(30);
  Map<String, LinkSession> sessions = new ConcurrentHashMap<>(30);
  IntHashMap sessionsById = new IntHashMap(30);

  LinkServer receive = null;
  Thread rcvThread;
}
