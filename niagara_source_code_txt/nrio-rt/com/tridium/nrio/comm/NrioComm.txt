/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.comm;

import java.io.BufferedReader;
import java.util.Vector;
import javax.baja.log.Log;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.serial.BISerialPort;
import javax.baja.serial.BISerialService;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Sys;
import com.tridium.basicdriver.comm.Comm;
import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;
import com.tridium.basicdriver.util.BasicException;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.platNrio.BNrioPlatformService;
import com.tridium.platNrio.NrioConst;


/**
 * The NrioComm class is used to synchronize access to a
 * Nrio network and handles the synchronization of communication.
 *
 * @author    Andy Saunders
 * @creation  05 June 2003
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:11 AM$
 * @since     Niagara 3.0 basicdriver 1.0
 */

public class NrioComm
  extends Comm
  implements NrioMessageConst
{

 /**
  * Constructor - initializes the SerialComm with a specified BNrioNetwork
  * Uses the GeM6CommReceiver + CommTransmitter + CommTransactionManager.
  */
  public NrioComm(BNrioNetwork accessNetwork)
  {
    super(accessNetwork, new NrioCommReceiver());
  }

  /**
   * Returns true if this Communication handler
   * has been started and is running, false if not.
   */
   public boolean isCommStarted()
   {
     return localCommStarted;
   }


  /**
   * Starts the serial transmit/receive drivers. Returns true if successfully started, false otherwise.
   * Opens the serial port and extracts the input and output streams,
   * and also starts the receive driver thread.
   */
  protected boolean started()
    throws Exception
  {
  	localCommStarted = false;
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    commInitError = null;
    try
    {
      BISerialService serialService = (BISerialService)Sys.getService(BISerialService.TYPE);
      serialService.checkPropertiesLoaded();

      port = serialService.openPort(network.getPortName(), network.getName());
    }
    catch(Exception e)
    {
    	commInitError = "Check for duplicate or invalid Port Name";
      getNetwork().getLog().error(commInitError, e);
      try {if(port != null) port.close();}catch(Exception e1){}
      port = null;
      throw e;
    }
    try
    {
      //  convert COMx to os port name
      String osName = port.getOsPortName();
      nrioService = (BNrioPlatformService)Sys.getService(BNrioPlatformService.TYPE);
      if(nrioService != null)
      {
	      handle = nrioService.open(network.getTrunk());
	      nrioService.setPortParams(handle, osName, network.getBaudRate().getOrdinal());
      }
//      else
//      {
//      	accessService = (BNrioPlatformService)Sys.getService(BNrioPlatformService.TYPE);
//      	if(accessService != null)
//      	{
//  	      handle = accessService.open(network.getTrunk());
//  	      accessService.setPortParams(handle, osName, network.getBaudRate().getOrdinal());
//      	}
//
//      }
      network.getLog().message("Opened " + network.getPortName());
    }
    catch(Exception e)
    {
    	commInitError = "Check for duplicate or invalid Trunk assignment.";
      getNetwork().getLog().error(commInitError, e);
      try {if(port != null) port.close();}catch(Exception e1){}
      try { if(nrioService != null) nrioService.close(handle);}catch(Exception e2){}
      port = null;
      nrioService = null;
      throw e;
    }
    localCommStarted = true;
    return true;
  }

  /**
   * Stops the serial transmit/receive drivers.
   * Closes the serial port and the input and output streams.
   */
  protected void stopped()
    throws Exception
  {
    getCommReceiver().setAlive(false);
    //if((getCommReceiver()!=null) && (rxThread!=null)) rxThread.interrupt();
    try{ if(nrioService != null) nrioService.close(handle);} catch(Exception e){}
    try{ if(port != null) port.close();} catch(Exception e){}
    nrioService = null;
    port = null;

    localCommStarted = false;
  }

  public void  resetCommPort()
  {
  	try
  	{
  	 ((BNrioNetwork)getNetwork()).stopUnsolicitedReceive();
  	 stopped();
  	 started();
  	 ((BNrioNetwork)getNetwork()).startUnsolicitedReceive();
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
    finally
    {
      String error = getCommInitError();
      if (error == null) getNetwork().configOk();
      else getNetwork().configFail(error);
    }
  }

  public Vector<byte[]> discover()
  {
  	if(nrioService != null)
  		return nrioService.discover(this.getHandle());
  	return null;
  }

  public void enablePolling(int address)
  {
  	if(nrioService != null)
  		nrioService.enablePolling(this.getHandle(), address);
  }

  public void disablePolling(int address)
  {
  	if(nrioService != null)
  		nrioService.disablePolling(this.getHandle(), address);
  }

  public void waitForStatusChange(int handle, byte[] reply)
  {
  	if(nrioService != null)
  		nrioService.waitForStatusChange(this.getHandle(), reply);
  }

  public byte[] sendRequest(byte[] request)
  {
    try
    {
      if (nrioService == null ) return NULL_BA;
      String thread = Thread.currentThread().getName();
      if(getLog().isTraceOn())
      {
        getLog().trace("<" + thread + "> request: " + (Clock.ticks()-replyTicks) + " ms " + ByteArrayUtil.toHexString(request));
      }
      byte[] response = new byte[NrioConst.MAX_MESSAGE_SIZE];
      long sendTicks = Clock.ticks();
      getNetwork().incrementSent();
      getNetwork().incrementReceived();
      nrioService.sendRequest(this.getHandle(), request, response);
      replyTicks = Clock.ticks();
      int respSize = (response[1] & 0x0ff) + 2;
      if(respSize > NrioConst.MAX_MESSAGE_SIZE)
        respSize = NrioConst.MAX_MESSAGE_SIZE;
      getLog().trace("response: " + (replyTicks-sendTicks) + " ms " + ByteArrayUtil.toHexString(response, 0, respSize));
      if(response[STATUS_OFFSET] != 0)
      {
        getLog().trace("<" + thread + ">***> request: " + ByteArrayUtil.toHexString(request));
        getLog().trace("<" + thread + ">***> response: " + ByteArrayUtil.toHexString(response, 0, respSize ));
        getNetwork().incrementTimeouts();
      }
      return response;
    }
    catch(Exception e)
    {
      getLog().error("sendRequest caught Exception: " + e);
      return NULL_BA;
    }
  }

  public BufferedReader readStats(int trunk)
  {
    if(nrioService != null) {
        return (nrioService.readStats(this.getHandle(), trunk));
    }
    return null;
  }

////////////////////////////////////////////////////////////
//  BasicDriver Api Overrides
////////////////////////////////////////////////////////////

  /**
   * Send a message using the message request/response service to
   * the communication medium.  Block the calling thread
   * until the response is obtained or the transaction times out.
   * Overridden here to enforce the inter message delay.
   *
   * @param msg a network request (in message form) to be
   *    sent to the output stream
   * @param responseTimeout the timeout to wait for a response for
   *    this request.
   * @param retryCount the number of retries to perform if the request
   *    fails (a timeout occurs).
   * @return Message the response received for the sent message
   *    if successful (or null if no response expected),
   *    otherwise an exception is thrown (i.e. timeout).
   */
  public Message transmit(Message msg, BRelTime responseTimeout, int retryCount)
    throws BasicException
  {
    if (msg == null)
      return null;

    // May not need this check - keep for now for legacy purposes
    if(!msg.getResponseExpected())
    {
      transmitNoResponse(msg);
      return null;
    }

    //performInterMessageDelay();

    return super.transmit(msg, responseTimeout, retryCount);
  }

  /**
   * Send a message to the transmit driver and do not expect or wait
   * for a response from the receive driver.
   * Overridden here to enforce the inter message delay.
   *
   * @param msg a message to be sent to the output stream
   */
  public void transmitNoResponse(Message msg)
    throws BasicException
  {
    if(msg == null)
      return;

    if(!isCommStarted()) throw new BasicException("Communication handler service not started.");

    //performInterMessageDelay();

    super.transmitNoResponse(msg);
  }

  /**
   * This is the access point for the receive driver to
   * pass its received unsolicited messages and/or
   * response messages up to the communications handler for
   * processing.  Overridden here to enforce the Inter Message
   * Delay.
   *
   * @param msg the response/unsolicited message received
   *    from the input stream.
   */
  public void receive(ReceivedMessage msg)
  {
    if (msg == null) return; // By default, don't do anything if received a null message.

    setReceivedMessageTicks(Clock.ticks());

    super.receive(msg);
  }

////////////////////////////////////////////////////////////
//  NrioComm methods
////////////////////////////////////////////////////////////

  /**
   * This method is called just before a message transmit in order to
   * checks to see when the last time a message was received.
   * It waits the necessary inter message delay time if needed before returning.
   */
/*
  protected void performInterMessageDelay()
  {
    long minDelay = ((BSerialNetwork)getNetwork()).getInterMessageDelay().getMillis();
    if (minDelay <= 0L) return; // short circuit

    long difference = Clock.ticks() - lastRecvMessageTicks;

    if (difference >= minDelay) return;

    // Otherwise sleep the appropriate time
    long sleepTime = Math.max((minDelay - difference), MIN_SLEEP_TIME);
    try { Thread.sleep(sleepTime); } catch (Exception e) { e.printStackTrace(); }
  }
*/

  /**
   * Should be called with the current time ticks whenever a message
   * is received.
   */
  protected void setReceivedMessageTicks(long ticks)
  {
    lastRecvMessageTicks = ticks;
  }

  public String getCommInitError() { return commInitError; }

/////////////////////////////////////////////////////
// NrioService convenience methods
//////////////////////////////////////////////////////

  /**
   * Returns the nrioPlatformService used by this NrioComm
   */
//  public BNrioPlatformService getNrioService()
//  {
//    return nrioService;
//  }

  public int getHandle()
  {
    return handle;
  }

  public Log getLog()
  {
  	return getNetwork().getLog();
  }

////////////////////////////////////////////////////////////
//  Attributes of NrioComm
////////////////////////////////////////////////////////////
  private static final long MIN_SLEEP_TIME = 10L;

  private boolean localCommStarted = false;
  private String commInitError = null;

  private long lastRecvMessageTicks = 0L;

  private int handle;
  protected BISerialPort port;

  BNrioPlatformService nrioService;
  private long    replyTicks = 0;

}
