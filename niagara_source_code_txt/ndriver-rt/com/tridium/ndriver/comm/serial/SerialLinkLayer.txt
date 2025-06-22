/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.serial;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.serial.BISerialPort;
import javax.baja.serial.BISerialService;
import javax.baja.serial.PortDeniedException;
import javax.baja.serial.PortNotFoundException;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BComponent;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import com.tridium.driver.util.DrByteArrayUtil;
import com.tridium.ndriver.comm.DebugStream;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.LinkMessage;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.NCommException;
import com.tridium.ndriver.comm.NLinkMessageFactory;
import com.tridium.ndriver.datatypes.BCommConfig;
import com.tridium.ndriver.datatypes.BSerialCommConfig;
import com.tridium.ndriver.util.SpyUtil;

/**
 * LinkLayer implementation for Serial interface.  Send and receive messages
 * using a serial comm port.
 * <p>
 * SerialLinkLayer also implements apis to allow SerialTunnelShare to
 * synchronize access to the comm port.
 *
 * @author Robert A Adams
 * @creation Oct 23, 2011
 */
public class SerialLinkLayer
  implements ILinkLayer
{
  public SerialLinkLayer(NComm comm, BSerialCommConfig serCfg)
  {
    this.comm = comm;
    this.serCfg = serCfg;
    this.lnkFac = serCfg.getLinkMessageFactory();
    debIn = new DebugStream(lnkFac.getLinkMaxLength());
  }

  /**
   * Start linklayer - called from NComm
   */
  @Override
  public void start() throws Exception
  {
    log().fine("start SerialLinkLayer");

    // Port is unavailable until thread started and port successfully initialized.
    state = UNAVAILABLE;

    //  Start the Receive thread 
    receive = new LinkReceive();
    rcvThread = new Thread(receive, serCfg.getResourcePrefix() + ".LinkReceive");
    rcvThread.start();

    // Initialize com port
    open();
  }

  /**
   * Stop linklayer - called from NComm
   */
  @Override
  public void stop()
  {
    try
    {
      // Cleanup
      state = DONE;

      // Release any threads waiting for access to port
      synchronized (receive)
      {
        receive.notifyAll();
      }

      // Release com port resources
      closePort();
    }
    catch (Throwable e)
    {
    }
  }

  /**
   * Called when comm config parameter changes.
   */
  @Override
  public synchronized void verifySettings(BCommConfig comCfg) throws Exception
  {
    serCfg = (BSerialCommConfig)comCfg;

    // If not port, is new port name or inFault - recreate port
    if (port == null ||
      !portName.equals(serCfg.getPortName()))
    {
      stop();
      start();
    }
    // If any parameter changed then setCommParams
    else if (!port.getFlowControlMode().equals(serCfg.getFlowControlMode()) ||
      !port.getBaudRate().equals(serCfg.getBaudRate()) ||
      !port.getDataBits().equals(serCfg.getDataBits()) ||
      !port.getStopBits().equals(serCfg.getStopBits()) ||
      !port.getParity().equals(serCfg.getParity()))
    {
      setCommParams();
    }
  }

  private void open()
    throws Exception
  {
    BISerialService platSvc = (BISerialService)Sys.getService(BISerialService.TYPE);
    ((BComponent)platSvc).lease();  // create a subscription to force the platform service to lazy-init

    // Open port
    try
    {
      portName = serCfg.getPortName(); // Save this so we can check for modified portName
      if (portName.equals(BSerialCommConfig.noPort))
      {
        throw new BajaRuntimeException("comm port not initialized");
      }
      port = platSvc.openPort(portName, serCfg.getResourcePrefix());
    }
    catch (PortNotFoundException notPort)
    {
      String errMsg = "'" + serCfg.getPortName() + "' not a valid comm port.";
      reportFault(errMsg, notPort);
    }
    catch (PortDeniedException deniedPort)
    {
      String errMsg = "Denied opening comm port '" + serCfg.getPortName() + "'";
      reportFault(errMsg, deniedPort);
    }
    catch (Exception e)
    {
      String errMsg = "Exception opening comm port '" + serCfg.getPortName() + "'";
      reportFault(errMsg, e);
    }

    // Set comm parameters
    setCommParams();

    // Notify receive that it can start processing
    synchronized (receive)
    {
      state = RECEIVING;
      receive.notify();
    }
  }

  private void setCommParams()
    throws Exception
  {
    try
    {
      // set comm parameters
      port.setSerialPortParams(serCfg.getBaudRate(),
        serCfg.getDataBits(),
        serCfg.getStopBits(),
        serCfg.getParity());
      // set flow control
      port.setFlowControlMode(serCfg.getFlowControlMode());
    }
    catch (UnsupportedOperationException unsupported)
    {
      String errMsg = "Unsupported comm parameter for " + serCfg.getPortName(); //id.serCfg.getCurrentOwner();      
      reportFault(errMsg, unsupported);
    }
    catch (Exception e)
    {
      String errMsg = "Exception setting comm parameters for " + serCfg.getPortName(); //id.serCfg.getCurrentOwner();      
      reportFault(errMsg, e);
    }

    try
    {
      // set receive timeout
      int tmo = serCfg.getReceiveTimeout();
      if (tmo == 0)
      {
        port.disableReceiveTimeout();
      }
      else
      {
        port.enableReceiveTimeout(tmo);
      }
    }
    catch (UnsupportedOperationException e)
    {
      String errMsg = "Can't set receiveTimeout on " + serCfg.getPortName(); //id.serCfg.getCurrentOwner();      
      reportFault(errMsg, e);
    }
  }

  // Error setting up comm port - log error and close port
  private void reportFault(String errMsg, Exception ex)
    throws Exception
  {
    log().log(Level.SEVERE, errMsg, ex);
    state = UNAVAILABLE;
    closePort();
    throw ex;
  }

  private void closePort()
  {
    // Kill recv thread
    if (rcvThread != null)
    {
      rcvThread.interrupt();
    }
    rcvThread = null;

    if (port == null)
    {
      return;
    }
    try
    {
      port.close();
    }
    catch (Throwable e)
    {
    }
    port = null;
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
    if (state == TUNNEL_WAIT || state == TUNNELING)
    {
      throw new NCommException("Link layer blocked for tunneling.");
    }
    if (state != RECEIVING)
    {
      throw new NCommException("Link layer in fault state.");
    }

    if (log().isLoggable(Level.FINE))
    {
      log().fine("send:" + DrByteArrayUtil.toString(msg.getByteArray(), msg.getLength()));
    }

    // give current rcvMsg access to outgoint msg - needed by some protocols to constrain incoming msg framing
    receive.initRcvMsg(msg);

    // Write message to output stream
    port.getOutputStream().write(msg.getByteArray(), 0, msg.getLength());
    stats.msgSent++;
  }


////////////////////////////////////////////////////////////////
// LinkReceive
////////////////////////////////////////////////////////////////

  /**
   * Receive messages from serial port input stream
   */
  private class LinkReceive
    implements Runnable
  {
    @Override
    public void run()
    {
      while (state != DONE)
      {
        try
        {
          synchronized (this)
          {
            // If waiting to tunnel release access to port
            if (state == TUNNEL_WAIT)
            {
              state = TUNNELING;
              this.notify();
            }
            // Wait for port to be available
            while (state != RECEIVING && state != DONE)
            {
              wait();
            }
          }
          // Make sure we did not finish while waiting
          if (state == DONE)
          {
            break;
          }

          boolean trace = log().isLoggable(Level.FINE);
          LinkMessage lmsg = lnkFac.getLinkMessage();
          setCurrRcvMsg(lmsg);  // Store rcv link message for possible initRcvMsg() call

          // Read message from input port
          InputStream in = port.getInputStream();
          if (trace)
          {
            in = debIn.reset(in);  // if tracing use debug wrapper stream
          }
          boolean complete = lmsg.receive(in);
          setCurrRcvMsg(null);  // null rcv link message to block any initRcvMsg() call after msg completed

          // Dump any debug accumulated by receive - dump bytes even if complete message not rcvd
          if (trace && debIn.hasDebug())
          {
            log().fine("rcvd:" + (complete ? "" : "frag:") + debIn.debugString());
          }

          if (complete)
          {
            // If complete message rcvd send to comm
            comm.receiveMessage(lmsg);
            stats.msgReceived++;
          }
          else
          {
            // Not good msg - just release the message
            lnkFac.releaseLinkMessage(lmsg);
          }
        }
        catch (Throwable e)
        {
          if (state == DONE)
          {
            return; // Shut down exception - don't report anything
          }
          log().log(Level.SEVERE, "Exception caught in LinkReceiver", e);
          stats.receiveError++;
        }
      }
    }

    /* Set the current active rcv mesage. */
    synchronized void setCurrRcvMsg(LinkMessage lmsg)
    {
      currRcvMsg = lmsg;
    }

    /* Pass initReceive to current rcv msg if any */
    synchronized void initRcvMsg(LinkMessage lmsg)
    {
      if (currRcvMsg != null)
      {
        currRcvMsg.initReceive(lmsg);
      }
    }

    private LinkMessage currRcvMsg = null;
  }

  DebugStream debIn;

////////////////////////////////////////////////////////////////
// Tunnel support
////////////////////////////////////////////////////////////////

  /**
   * Callback used by {@code BSerialTunnelShare} to lock access to comm
   * port for tunneling.
   */
  public BISerialPort tunnelLock()
  {
    synchronized (receive)
    {
      // Can't initiate tunnel unless in receiving state
      if (state != RECEIVING)
      {
        return null;
      }

      // enter wait state
      state = TUNNEL_WAIT;
      // Wait for LinkRecieve to release port
      while (state != TUNNELING && state != DONE)
      {
        try
        {
          receive.wait();
        }
        catch (InterruptedException e)
        {
        }
      }
      stats.tunnelSessions++;
      if (state == DONE)
      {
        return null;
      }
    }
    return port;
  }

  /**
   * Callback used by {@code BSerialTunnelShare} to release lock on comm
   * port to allow general message processing.
   */
  public void releaseLock()
  {
    synchronized (receive)
    {
      if (state != DONE)
      {
        state = RECEIVING;
        receive.notify();
      }
    }
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
    out.startProps("SerialLinkLayer");
    out.prop("state", stateToString());
    out.endProps();
    SpyUtil.spy(out, "SerialLink Statistics", stats);
  }

  private String stateToString()
  {
    switch (state)
    {
      case UNAVAILABLE:
        return "UNAVAILABLE";
      case RECEIVING:
        return "RECEIVING";
      case TUNNEL_WAIT:
        return "TUNNEL_WAIT";
      case TUNNELING:
        return "TUNNELING";
      case DONE:
        return "DONE";
    }
    return "Unknown state " + Integer.toString(state);
  }

  /**
   * Container class for statistics counters.
   */
  public static class Statistics
  {
    public long msgSent = 0;
    public long msgReceived = 0;
    public long receiveError = 0;
    public long tunnelSessions = 0;
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
      // If no cfg (??) return temporary log
      if (serCfg == null)
      {
        return Logger.getLogger(".SerialLink");
      }
      log = Logger.getLogger(serCfg.getResourcePrefix() + ".Link");
    }
    return log;
  }

  private Logger log;

  private NComm comm;
  private BSerialCommConfig serCfg;
  private NLinkMessageFactory lnkFac;
  private BISerialPort port = null;
  private String portName = "";
  private Statistics stats = new Statistics();

  private volatile int state = UNAVAILABLE;
  private static final int UNAVAILABLE = 1;
  private static final int RECEIVING = 2;
  private static final int TUNNEL_WAIT = 3;
  private static final int TUNNELING = 4;
  private static final int DONE = 5;

  LinkReceive receive = null;
  Thread rcvThread;
}
