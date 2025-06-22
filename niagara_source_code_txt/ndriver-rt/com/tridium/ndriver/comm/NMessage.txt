/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.baja.sys.BajaRuntimeException;
import com.tridium.ndriver.datatypes.BAddress;
import com.tridium.ndriver.util.LinkedQueue;

/**
 * NMessage is super class for all messages.
 *
 * @author Robert A Adams
 * @creation May 24, 2011
 */
public class NMessage
  implements LinkedQueue.ILinkable
{
  public NMessage() {}

  public NMessage(BAddress address)
  {
    this.address = address;
  }

  /**
   * Write message fields to the specified output stream. Outgoing message types
   * must override this method.<p>
   *
   * @return true if fragmented message and more fragments
   * @throws Exception
   */
  public boolean toOutputStream(OutputStream out)
    throws Exception
  {
    throw new BajaRuntimeException("toOutputStream not implemented");
  }

  /**
   * Fill message fields from the specified input stream. Incoming message types
   * may override this method.<p>
   *
   * @throws Exception
   */
  public void fromInputStream(InputStream in)
    throws Exception
  {
    throw new BajaRuntimeException("fromInputStream not implemented");
  }

  /**
   * Override to return protocol specific message tag to match requests and
   * response messages. <p> Default will return nullTag to indicate protocol
   * does not have a tag. The nullTag is a static object so that request and
   * response messages that don't override this method will always match.<p>
   *
   * @return protocol specific message tag
   */
  public Object getTag()
  {
    return nullTag;
  }

  public static final Object nullTag = new Object();

  /**
   * Override to return true for response message types. <p>
   *
   * @return true if this is a response message type
   */
  public boolean isResponse()
  {
    return false;
  }

  /**
   * Override point to validate response message. This is called on request
   * messages when an incoming messages has been match by tag as the response
   * message.  The return indicates if the transaction can be completed and in
   * what state (success/failure).
   * <p>
   *
   * @return one of: FAILED_RESPONSE - response message is error
   * SUCCESS_RESPONSE - response message is success ACK_SUCCESS_RESPONSE - ack
   * response to acknowledged messages DELAY_RESPONSE - delayed response (could
   * be delay or ack to be followed by response) default returns
   * SUCCESS_RESPONSE
   * @throws Exception
   */
  public int validateResponse(NMessage msg)
    throws Exception
  {
    return SUCCESS_RESPONSE;
  }

  public static final int FAILED_RESPONSE = 1;
  public static final int SUCCESS_RESPONSE = 2;
  public static final int ACK_SUCCESS_RESPONSE = 3;
  public static final int DELAY_RESPONSE = 4;

  /**
   * Override point to allow a request message to perform modifications on an
   * associate response message. This api is called by <code>NComm.sendRequest()</code>
   * after a successful response message has been matched to the request.  The
   * returned NMessage is the response returned by <code>NComm.sendRequest()</code>.<p>
   * This api allows for either the creation of a new response type or the
   * modification of the original response message.<p>
   *
   * @param resp the original response message created by <code>IMessageFactory</code>
   *             for incoming response.
   * @return new or modified response message. Default implementation returns
   * original response.
   */
  public NMessage modifyResponse(NMessage resp)
  {
    return resp;
  }

  public String toTraceString()
  {
    return "toTraceString not implemented in " + getClass().getName();
  }

  public String getString()
  {
    ByteArrayOutputStream os = new ByteArrayOutputStream(1500);
    try
    {
      toOutputStream(os);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return os.toString();
  }


  public String toString()
  {
    return "toString() not implemented in " + getClass().getName();
  }

  /**
   * Get response timeout in milliSeconds
   */
  public int getResponseTimeOut()
  {
    return respTmo;
  }

  /**
   * Get max retry count
   */
  public int getRetryCount()
  {
    return retryCnt;
  }

  /**
   * Get source or destination address
   */
  public BAddress getAddress()
  {
    return address;
  }

  // NCCB-26351 - added api that allows config of padding - original time was 1000ms.

  /**
   * Additional response time padding to allow for system response time delay.
   * This time is added to the value returned in getResponseTimeOut() in
   * NCommTimer. Default returns 1000ms
   *
   * @return Time in milliseconds to add to getResponseTimeOut()
   */
  public int getRespTimePadding()
  {
    return 1000;
  }

  /**
   * Set response timeout in milliseconds. If time is less than 0 it will be set
   * to 0.
   */
  public void setResponseTimeOut(int time)
  {
    respTmo = time > 0 ? time : 0;
  }

  /**
   * Set max retry count - 0 for no retries
   */
  public void setRetryCount(int retryCount)
  {
    retryCnt = retryCount;
  }

  /**
   * Set source or destination address
   */
  public void setAddress(BAddress address)
  {
    this.address = address;
  }

  private BAddress address;

  /**
   * Developers must override to return true in subclasses that implement
   * <code>IFragmentable</code>. This default implementation returns false.
   */
  public boolean isFragmentable()
  {
    return false;
  }

  /**
   * Callback from NComm to indicate start of fragmentation. The next call of
   * toOutputStream() expects to receive first fragment. If an outgoing message
   * will be sent in fragments this callback should be overridden to reset any
   * attributes needed to track state of fragmentation.
   */
  public void initFragmentation()
  {
  }

  private int respTmo = 0;
  private int retryCnt = 0;

  /**
   * Callback from NComm after receiving incoming message. Override point for
   * protocols that support a low level ack message to acknowledge receipt of
   * message.  This callback is made after application message type is created
   * from link layer message so integrity of message should be verify but before
   * application has opportunity to process actual message.  Developer may
   * decide to handle acknowledgement at application layer.<p> Default returns
   * null to indicate no ack to be sent.
   */
  public NMessage getAck()
  {
    return null;
  }

////////////////////////////////////////////////////////////
//LinkedQueue.ILinkable implementation
////////////////////////////////////////////////////////////

  @Override
  public final LinkedQueue.ILinkable getNext()
  {
    return nextMsg;
  }

  @Override
  public final void setNext(LinkedQueue.ILinkable nxt)
  {
    nextMsg = (NMessage)nxt;
  }

  private NMessage nextMsg = null;
}
