/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javax.baja.sys.BajaRuntimeException;
import com.tridium.ndriver.util.LinkedQueue;

/**
 * LinkMessage is a LinkLayer message class that provides mechanisms to stream
 * data to and from a byte array representation.
 * <p>
 * It is intended that LinkMessage instances are reusable to minimize object
 * creation and cleanup.  It wraps a maxMessageSize byte array with input and
 * output streams for efficient processing from/to NMessage representation and
 * low level driver input/output.
 *
 * @author Robert A Adams
 * @creation Oct 24, 2011
 */
public class LinkMessage
  implements LinkedQueue.ILinkable
{
  /**
   * LinkMessage message constructor creates a maxLength byte array which is
   * then wrapped with both and input and output stream. <p> LinkMessages are
   * pooled for later use and may be used for incoming or outgoing messages.
   * Subclasses should override this default constructor and call super().
   */
  public LinkMessage(int maxLength)
  {
    this.buffer = new byte[maxLength];
    outputStream = new MyOutputStream(buffer);
    inputStream = new MyInputStream(buffer, 0);
  }


////////////////////////////////////////////////////////////
// Api
////////////////////////////////////////////////////////////

  /**
   * Get byte data from inputStream. Subclasses must override this method to
   * code beginning and ending checks for reading message from an inputStream.
   * return true if complete message received
   */
  public boolean receive(InputStream in)
    throws Exception
  {
    throw new BajaRuntimeException("receive not implemented");
  }

  /**
   * Translate data from app message to link message representation
   */
  public boolean setMessage(NMessage msg)
    throws Exception
  {
    address = msg.getAddress();
    return msg.toOutputStream(outputStream);
  }

  /**
   * Access the underlying byte array
   */
  public byte[] getByteArray()
  {
    return buffer;
  }

  public InputStream getInputStream()
  {
    return inputStream;
  }

  public OutputStream getOutputStream()
  {
    return outputStream;
  }

  /**
   * Get the size in the output stream
   */
  public int getLength()
  {
    return outputStream.size();
  }

  /**
   * Set the size of the input stream
   */
  public void setLength(int len)
  {
    inputStream.init(len);
  }

  public Object address;

  /**
   * This callback is made on incoming linkmessages to provide a mechanism to
   * constrain the receive message framing with info from the transmit message.
   * This call is made by streaming linklayers on the current receive link
   * message immediately before the request message is written to the outgoing
   * stream.  The msgToSend data can be accessed with getByteArray() and
   * getLength().
   *
   * @param msgToSend - the outgoing link message
   * @since 3.8.38.1
   */
  public void initReceive(LinkMessage msgToSend)
  {

  }

  /**
   * Override point for subclasses to initialize any state variables.  This is
   * called when a linkMessage is retrieved from the linkMessageFactory.
   *
   * @since 3.8.38.1
   */
  protected void doInit()
  {

  }

  void init()
  {
    Arrays.fill(buffer, (byte)0);
    outputStream.init();
    inputStream.init(0);
    doInit();
  }

  protected byte[] buffer;
  private MyOutputStream outputStream;
  private MyInputStream inputStream;

  boolean freeBuf = false;

////////////////////////////////////////////////////////////
// Stream augmentation
////////////////////////////////////////////////////////////
  static class MyOutputStream
    extends ByteArrayOutputStream
  {
    MyOutputStream(byte[] b)
    {
      this.buf = b;
      init();
    }

    private void init()
    {
      this.count = 0;
    }
  }

  // Wrapper class for ByteArrayInputStream to allow
  // manipulation of pos and count.
  static class MyInputStream
    extends ByteArrayInputStream
  {
    MyInputStream(byte[] b, int msgLen)
    {
      super(b);
      init(msgLen);
    }

    private void init(int msgLen)
    {
      pos = 0;
      count = msgLen;
    }
  }

////////////////////////////////////////////////////////////
//LinkedQueue.ILinkable implementation
////////////////////////////////////////////////////////////

  @Override
  public final LinkedQueue.ILinkable getNext()
  {
    return nextLinkMsg;
  }

  @Override
  public final void setNext(LinkedQueue.ILinkable nxt)
  {
    nextLinkMsg = (LinkMessage)nxt;
  }

  private LinkMessage nextLinkMsg = null;

}
