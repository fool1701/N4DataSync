/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Clock;
import com.tridium.ndriver.datatypes.BCommConfig;
import com.tridium.ndriver.util.SpyUtil;

/**
 * NComm provides access to the communication stack.  It handles sending and
 * receiving messages.
 *
 * @author Robert A Adams
 * @creation Oct 21, 2011
 */
public class NComm
  implements IComm
{
  ////////////////////////////////////////////////////////////
  //Constructor
  ////////////////////////////////////////////////////////////
  public NComm(BCommConfig comCfg, ICommListener defaultListener)
  {
    this.comCfg = comCfg;
    this.ntimer = new NCommTimer(comCfg.getMaxOutstandingTransactions(), comCfg.getMaxTransactionWait(), comCfg.getResourcePrefix());
    this.fragMgr = new FragmentManager(this, ntimer);
    ntimer.setFragmentManager(fragMgr);
    this.linkLayer = comCfg.makeLinkLayer(this);
    this.defListener = defaultListener;
    this.lnkFac = comCfg.getLinkMessageFactory();
    this.msgFac = comCfg.getMessageFactory();
  }

  ///////////////////////////////////////////////////////////
  //API
  ////////////////////////////////////////////////////////////

  /**
   * Called from BCommConfig when network is started.
   */
  @Override
  public void start()
    throws Exception
  {
    if (serviceStarted)
    {
      return; // Don't allow duplicate start
    }

    ntimer.start();
    receive.start(comCfg.getResourcePrefix() + ".Receiver");
    incoming.start(comCfg.getResourcePrefix() + ".Incoming");
    log().fine("start NComm");

    try
    {
      linkLayer.start();
      lnkEx = null;
    }
    catch (Exception e)
    {
      lnkEx = e;
    }

    serviceStarted = true;// set start flag

    // Pass link exception up to commConfig
    if (lnkEx != null)
    {
      throw lnkEx;
    }
  }

  /**
   * Called from BCommConfig when network is stopped.
   */
  @Override
  public void stop()
  {
    serviceStarted = false;
    linkLayer.stop();
    ntimer.stop();
    receive.stop();
    incoming.stop();
    listeners.clear();
  }

  /**
   * Called from BCommConfig when a property changes.
   */
  @Override
  public void verifySettings(BCommConfig comCfg)
    throws Exception
  {
    try
    {
      if (!serviceStarted)
      {
        return;
      }
      linkLayer.verifySettings(comCfg);
      lnkEx = null;
    }
    catch (Exception e)
    {
      lnkEx = e;
      throw e;
    }
  }

  /**
   * Access the linkLayer
   */
  public ILinkLayer getLinkLayer()
  {
    return linkLayer;
  }

  /**
   * Clear all statistics counts. Called when user invokes resetStats action on
   * commConfig object.
   */
  @Override
  public void resetStats()
  {
    stats = new Statistics();
    if (linkLayer != null)
    {
      linkLayer.resetStats();
    }
  }

  ///////////////////////////////////////////////////////////
  // Send API
  ////////////////////////////////////////////////////////////

  /**
   * Send a request message and receive a response.
   *
   * @throws throws exception for message timeout or comm failure
   */
  public NMessage sendRequest(NMessage msg)
    throws Exception
  {
    Exception te = null;
    NMessage response = null;

    // Make sure comm is usable
    verify();

    // Output trace info
    if (log().isLoggable(Level.FINE))
    {
      String adrSt = (msg.getAddress() != null) ? (" to " + msg.getAddress()) : "";
      log.fine("sendRequest" + adrSt + " :\n" + msg.toTraceString() + "\n");
    }
    stats.sendRequest++;

    int retries = msg.getRetryCount();
    boolean retry;

    do
    {
      retry = false;

      // Get transaction - this will start transaction timer
      NCommTimer.Transaction trns = ntimer.getTransaction(msg);
      if (trns == null)
      {
        return null; //  shutting down
      }
      try
      {
        synchronized (trns)
        {
          // Send to link - fragment if needed
          sendFragments(msg);

          // Wait on this trns  NCCB-27783 wait in loop to deal with "spurious wakeups"
          while (!trns.isDone())
          {
            trns.wait();
          }
          trns.setComplete(true);
        }
        // Return the response msg
        response = trns.getResponseMessage();
        te = trns.getException();

      }
      catch (Exception e)
      {
        trns.setComplete(true);
        te = e;
      }
      finally
      {
        ntimer.freeTransaction(trns);
      }

      // If failed because of timeout may try again
      if (retries > 0 && te instanceof NCommTimeoutException)
      {
        log.fine("retry");
        retry = true;
        retries--;
        stats.timeoutRetry++;
      }
      if (!retry && te != null)
      {
        stats.sendFail++;
        throw te;
      }
    }
    while (retry);

    // Allow request to modify or create new response type
    return msg.modifyResponse(response);
  }

  /**
   * Send a message.
   *
   * @throws throws exception for comm failure
   */
  public void sendMessage(NMessage msg)
    throws Exception
  {
    // Make sure comm is usable
    verify();

    // Ouput trace info
    if (log().isLoggable(Level.FINE))
    {
      String adrSt = (msg.getAddress() != null) ? (" to " + msg.getAddress()) : "";
      log.fine("sendMessage" + adrSt + " :\n" + msg.toTraceString() + "\n");
    }
    stats.sendMessage++;

    // Send to link - fragment if needed
    sendFragments(msg);
  }

  private void sendFragments(NMessage msg) throws Exception
  {
    boolean more = true;
    // signal start of fragmentaion
    msg.initFragmentation();

    while (more)
    {
      more = sendToLink(msg);
    }
  }

  private boolean sendToLink(NMessage msg)
    throws Exception
  {
    LinkMessage lmsg = lnkFac.getLinkMessage();
    // Build linkMsg - returns flag to indicate more fragments
    boolean more = lmsg.setMessage(msg);
    // send it
    linkLayer.sendMessage(lmsg);
    return more;
  }

  ///////////////////////////////////////////////////////////
  // Receive API
  ////////////////////////////////////////////////////////////

  /**
   * Access point for linklayer to transmit received message.
   */
  public void receiveMessage(LinkMessage lmsg)
  {
    receive.enqueue(lmsg);
  }

  private void doReceiveMessage(LinkMessage lmsg)
  {
    try
    {
      // create appropriate type
      NMessage msg = msgFac.makeMessage(lmsg);
      if (msg == null)
      {
        return;
      }
      // process message fragments
      if (fragMgr != null && msg.isFragmentable())
      {
        IFragmentable frag = (IFragmentable)msg;
        // If fragment ack supplied send to link
        NMessage fragAck = frag.getFragmentAck();
        if (fragAck != null)
        {
          sendToLink(fragAck);
        }
        // merge fragment
        msg = fragMgr.mergeFragments(frag);
        // null indicates this is not final fragment - return
        if (msg == null)
        {
          return;
        }
      }

      // Output trace info
      if (log().isLoggable(Level.FINE))
      {
        String adrSt = (msg.getAddress() != null) ? (" from " + msg.getAddress()) : "";
        log().fine("receiveMessage" + adrSt + " :\n" + msg.toTraceString() + "\n");
      }

      // If protocol supports it return low level ack
      NMessage ack = msg.getAck();
      if (ack != null)
      {
        sendToLink(ack);
      }

      if (msg.isResponse())
      {
        stats.receiveResponse++;
        processResponse(msg);
      }
      else
      {
        stats.receiveIncoming++;
        processIncoming(msg);
      }
    }
    catch (Throwable e)
    {
      log().log(Level.SEVERE, "Exception in NComm.receiveMessage()", e);
      stats.receiveException++;
      return;
    }
    finally
    {
      lnkFac.releaseLinkMessage(lmsg);
    }
  }

  /* Process various response messages types. */
  private void processResponse(NMessage msg)
  {
    NCommTimer.Transaction trns = ntimer.getTransactionMatch(msg.getTag());
    if (trns == null)
    {
      log().severe("Receive response with no matching transaction from " + msg.getAddress() + " :\n" + msg.toTraceString() + "\n");
      return;
    }

    synchronized (trns)
    {
      try
      {
        if (trns.getComplete() || trns.getTimeout())
        {
          log().warning("Received response message with no matching transaction." + msg);
          return;
        }
        switch (trns.getRequestMessage().validateResponse(msg))
        {
          case NMessage.DELAY_RESPONSE:
            //pass to transaction manager 
            ntimer.resetTimer(trns);
            return;
          case NMessage.FAILED_RESPONSE:
            trns.setException(new NCommException("Failed response"));
            break;
          case NMessage.SUCCESS_RESPONSE:
            trns.setResponseMessage(msg);
            break;
          case NMessage.ACK_SUCCESS_RESPONSE:
            // Complete transaction with no response
            break;
        }
      }
      catch (Exception ex)
      {
        // validateResponse can throw its own more specific exception
        trns.setException(ex);
      }

      // At this point have success failure or exception - end transaction
      trns.receivedResponse();
    }
  }

  /**
   * Process incoming messages on separate thread
   */
  private void processIncoming(NMessage msg)
  {
    incoming.enqueue(msg);
  }

  private void doProcessIncoming(NMessage msg)
  {
    boolean exclusive = false;

    // Check for listeners for this message
    synchronized (listeners)
    {
      for (int i = 0; i < listeners.size(); ++i)
      {
        ListenerData ld = listeners.elementAt(i);
        if (ld.fltr.accept(msg))
        {
          ld.listner.receiveMessage(msg);
          if (ld.exclusive)
          {
            exclusive = true;
          }
        }
      }
    }

    // If no exclusive listener for this message pass to default listener
    if (!exclusive && defListener != null)
    {
      defListener.receiveMessage(msg);
    }
    if (!exclusive && defListener == null)
    {
      stats.noListener++;
    }
  }


  // LinkedProcess used to receive messages from linklayer on separate thread.
  // This is needed so that thread reading from comm port will not carry any of 
  // the message processing.
  LinkedProcessor receive = new LinkedProcessor()
  {
    @Override
    public void process(Object obj)
    {
      doReceiveMessage((LinkMessage)obj);
    }
  };

  // LinkedProcess used to handle incoming NMessages on a separate thread.
  // This is needed in cases where an incoming message triggers actions that cause additional
  // request messages to be sent and the processing is left on the comm threads.  Any response 
  // or ack could not be processed resulting in a possible deadlock.
  LinkedProcessor incoming = new LinkedProcessor()
  {
    @Override
    public void process(Object obj)
    {
      doProcessIncoming((NMessage)obj);
    }
  };

  ////////////////////////////////////////////////////////////////
  // Listeners
  ////////////////////////////////////////////////////////////////

  /**
   * Set the default listener.
   */
  @Override
  public void setDefaultListener(ICommListener listener)
  {
    defListener = listener;
  }

  /**
   * Register a listener for incoming messages. Specify a filter to select
   * messages for this listener. Set exclusive to true if message should not
   * also be sent to the default listener.
   */
  public void registerCommListener(ICommListener listener, ICommFilter filter, boolean exclusive)
  {
    synchronized (listeners)
    {
      if (findListener(listener, filter) < 0)
      {
        ListenerData ld = new ListenerData();
        ld.listner = listener;
        ld.fltr = filter;
        ld.exclusive = exclusive;
        listeners.add(ld);
      }
    }
  }

  /**
   * Unregister a listener filter pair.  If filter is null all instances of
   * listeners will be unregistered.
   */
  public void unregisterCommListener(ICommListener listener, ICommFilter filter)
  {
    synchronized (listeners)
    {
      int ndx = findListener(listener, filter);
      while (ndx >= 0)
      {
        listeners.remove(ndx);
        ndx = findListener(listener, filter);
      }
    }
  }

  /*
   * Find the specified listener/filter pair. If filter is null return
   * any match only the listener.
   */
  private int findListener(ICommListener listner, ICommFilter filter)
  {
    for (int i = 0; i < listeners.size(); ++i)
    {
      ListenerData ld = listeners.elementAt(i);
      if (ld.listner == listner &&
        (filter == null || ld.fltr == filter))
      {
        return i;
      }
    }
    return -1;
  }

  /*
   * Container class for listener/filter pairs.
   */
  private static class ListenerData
  {
    ICommListener listner;
    ICommFilter fltr;
    boolean exclusive;
  }

  Vector<ListenerData> listeners = new Vector<>();

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
    out.startProps("NComm");
    out.prop("serviceStarted", serviceStarted);
    if (defListener != null)
    {
      out.prop("defListener", defListener.getClass().getName());
    }
    if (lnkEx != null)
    {
      out.prop("link fault", lnkEx.getMessage());
      out.endProps();
      return;
    }
    out.endProps();

    SpyUtil.spy(out, "NCom Statistics", stats);
    ntimer.spy(out);
    linkLayer.spy(out);
    fragMgr.spy(out);
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

    public long sendRequest = 0;
    public long sendMessage = 0;
    public long sendFail = 0;
    public long timeoutRetry = 0;
    public long receiveResponse = 0;
    public long receiveIncoming = 0;
    public long receiveException = 0;
    public long noListener = 0;
  }
  ////////////////////////////////////////////////////////////////
  //Log
  ////////////////////////////////////////////////////////////////

  final Logger log()
  {
    if (log == null)
    {
      log = Logger.getLogger(comCfg.getResourcePrefix() + ".Comm");
    }
    return log;
  }

  private Logger log;

  ///////////////////////////////////////////////////////////
  // Utilities
  ////////////////////////////////////////////////////////////

  private void verify()
  {
    if (!serviceStarted)
    {
      throw new BajaRuntimeException("service not started.");
    }
    if (lnkEx != null)
    {
      throw new BajaRuntimeException("link fault:", lnkEx);
    }
  }

  ///////////////////////////////////////////////////////////
  // Local variables
  ////////////////////////////////////////////////////////////
  private BCommConfig comCfg;
  ILinkLayer linkLayer;
  private ICommListener defListener;
  private NCommTimer ntimer;
  private NLinkMessageFactory lnkFac;
  private IMessageFactory msgFac;
  private FragmentManager fragMgr;

  private boolean serviceStarted = false;
  private Exception lnkEx = null;
  private Statistics stats = new Statistics();
}
