/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import javax.baja.nre.util.ByteArrayUtil;

import com.tridium.basicdriver.message.*;

/**
 * SerialReceivedMessage is a wrapper class for a received byte array
 * Serial message before it's contents have been interpreted.
 *
 * @author    Scott Hoye
 * @creation  11 Mar 04
 * @version   $Revision: 1$ $Date: 03/11/04 12:47:14 PM$
 * @since     Niagara 3.0 flexSerial 1.0
 */
public class SerialReceivedMessage
  extends ReceivedMessage
{
////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////

 /**
  * Constructor with message data in byte array form.
  * Also specifies the tag.
  */
  public SerialReceivedMessage(byte[] data, int len, Object tag)
  {
    this.data = data;
    this.len = len;
    setTag(tag);
    setUnsolicitedListenerCode(Integer.valueOf(-1));
  }

////////////////////////////////////////////////////////////
// SerialReceivedMessage
////////////////////////////////////////////////////////////

  /**
   * Returns the message data in byte array form.
   */
  public byte[] getBytes()
  {
    return data;
  }

  /**
   * Sets the message data in byte array form.
   */
  public void setBytes(byte[] data)
  {
    this.data = data;
  }

  /**
   * Returns the message length.
   */
  public int getLength()
  {
    return len;
  }

  /**
   * Sets the message length.
   */
  public void setLength(int len)
  {
    this.len = len;
  }

  /**
   * Returns the bytes in Hex String form
   */
  public String toDebugString()
  {
    return ByteArrayUtil.toHexString(data, 0, len);
  }

  public String getStringMessage()
  {
    return new String(data, 0,len);
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  private byte[] data;
  private int len;
}
