/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.spy.SpyWriter;
import com.tridium.driver.util.DrByteArrayUtil;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.LinkMessage;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.NLinkMessageFactory;
import com.tridium.ndriver.datatypes.BCommConfig;
import com.tridium.ndriver.datatypes.BIpAddress;
import com.tridium.ndriver.datatypes.BUdpCommConfig;
import com.tridium.ndriver.util.SpyUtil;

/**
 * LinkLayer implementation for UDP interface.  Send and receive messages using
 * DatagramSockets.
 *
 * @author Robert A Adams
 * @creation Oct 23, 2011
 */
public class UdpLinkLayer
  implements ILinkLayer
{
  public UdpLinkLayer(NComm comm, BUdpCommConfig comCfg)
  {
    this.comm = comm;
    this.udpComCfg = comCfg;
    this.lnkFac = comCfg.getLinkMessageFactory();
  }

  /**
   * Start linklayer
   */
  @Override
  public void start() throws Exception
  {

    log().fine("start UdpLinkLayer");

    done = false;

    udpSock = createSocket(udpComCfg);

    //  Start the Receive thread 
    receive = new LinkReceive();
    rcvThread = new Thread(receive, udpComCfg.getResourcePrefix() + ".LinkReceive");
    rcvThread.start();
  }

  private DatagramSocket createSocket(BUdpCommConfig cfg) throws Exception
  {
    ia = ipAddr(cfg);
    port = udpPort(cfg);

    // Cover all the options
    if (port > 0)
    {
      if (ia != null)
      {
        return new DatagramSocket(port, ia);
      }
      else
      {
        return new DatagramSocket(port);
      }
    }
    return new DatagramSocket();
  }

  /**
   * Stop linklayer
   */
  @Override
  public void stop()
  {
    // Stop serverSocket thread
    try
    {
      done = true;
      if (udpSock != null)
      {
        udpSock.close();
      }
    }
    catch (Throwable e)
    {
    }
  }

  /**
   * Called when comm config parameter changes.  Recreate ServerSocket if port
   * or ip changed.
   */
  @Override
  public synchronized void verifySettings(BCommConfig comCfg) throws Exception
  {
    BUdpCommConfig cfg = (BUdpCommConfig)comCfg;
    InetAddress cfgIa = ipAddr(cfg);

    // Make sure there is a DatagramSocket with correct port and local ip
    if (udpSock == null ||
      port != udpPort(cfg) ||
      (ia == null && cfgIa != null) ||
      (ia != null && !ia.equals(cfgIa)))
    {
      DatagramSocket s = udpSock;
      // create new socket with modified settings
      udpSock = createSocket(cfg);
      // close the original socket
      if (s != null)
      {
        s.close();
      }
    }
  }

  // Get my port from comm config
  private static int udpPort(BUdpCommConfig cfg)
  {
    return cfg.getAddress().getPort();
  }

  private static InetAddress ipAddr(BUdpCommConfig cfg)
  {
    BIpAddress myAdr = cfg.getAddress();
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
  public synchronized void sendMessage(LinkMessage msg)
    throws Exception
  {
    if (done)
    {
      return;
    }

    if (log().isLoggable(Level.FINE))
    {
      log().fine("send packet to " + msg.address + "\n\n" +
        DrByteArrayUtil.toString(msg.getByteArray(), msg.getLength()) + "\n");
    }

    BIpAddress ipAdr = (BIpAddress)msg.address;

    DatagramPacket pack = new DatagramPacket(msg.getByteArray(),     // idata
      msg.getLength(),        // ilength
      ipAdr.getInetAddress(), // iaddr
      ipAdr.getPort());       // iport

    udpSock.send(pack);
    stats.msgSent++;
  }


////////////////////////////////////////////////////////////////
// LinkServer
////////////////////////////////////////////////////////////////

  /**
   * Provide support for a serverSocket to receive incoming connections.
   */
  private class LinkReceive
    implements Runnable
  {
    @Override
    public void run()
    {
      while (!done)
      {
        try
        {
          LinkMessage lmsg = lnkFac.getLinkMessage();
          byte[] a = lmsg.getByteArray();
          DatagramPacket p = new DatagramPacket(a, a.length);
          udpSock.receive(p);

          // Set the length and source address on the link message
          lmsg.setLength(p.getLength());
          lmsg.address = new BIpAddress(p.getAddress(), p.getPort());

          if (log().isLoggable(Level.FINE))
          {
            log().fine("received packet from " + lmsg.address + " \n\n" + DrByteArrayUtil.toString(lmsg.getByteArray(), p.getLength()) + "\n");
          }

          // Pass the message to transport layer
          comm.receiveMessage(lmsg);
          stats.msgReceived++;
        }
        catch (Throwable e)
        {
          if (done)
          {
            return; // Shut down exception
          }
          log().severe("Exception caught in LinkReceiver. " + e);
          if (log().isLoggable(Level.FINE))
          {
            log().log(Level.FINE, "Caused by ", e);
          }
          stats.receiveError++;
          if (udpComCfg.getReconnectOnFailure())
          {
            try
            {
              if (udpSock != null)
              {
                udpSock.close();
              }
              udpSock = createSocket(udpComCfg);
            }
            catch (Exception e1)
            {
              log().severe("Could not reinitialize socket connection: " + e1.getMessage());
              if (log().isLoggable(Level.FINE))
              {
                log().log(Level.FINE, "Caused by ", e1);
              }
            }
          }
        }
      }
    }
    //int cnt=0;
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
    out.startProps("UdpLinkLayer");
    out.prop("done", done);
    out.prop("srvSock inet address", ((udpSock != null && udpSock.getInetAddress() != null) ? udpSock.getInetAddress().toString() : "n/a"));
    out.prop("srvSock port", ((udpSock != null) ? Integer.toString(udpSock.getLocalPort()) : "n/a"));
    out.endProps();

    SpyUtil.spy(out, "UdpLink Statistics", stats);
  }

  /**
   * Container class for statistics counters.
   */
  public static class Statistics
  {
    public long msgSent = 0;
    public long msgReceived = 0;
    public long receiveError = 0;
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
      log = Logger.getLogger(udpComCfg.getResourcePrefix() + ".Link");
    }
    return log;
  }

  private Logger log;

  private NComm comm;
  private BUdpCommConfig udpComCfg;
  private InetAddress ia;  // inet used to create socket
  private int port;        // port used to create socket
  private NLinkMessageFactory lnkFac;

  private DatagramSocket udpSock = null;
  private boolean done = true;
  private Statistics stats = new Statistics();

  LinkReceive receive = null;
  Thread rcvThread;
}
