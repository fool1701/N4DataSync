/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import javax.baja.nre.util.ByteArrayUtil;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * NrioReceivedMessage is a wrapper class for a received byte array
 * Modbus Ascii message before it's contents have been interpreted.
 *
 * @author    Andy Saunders
 * @creation  11 Mar 04
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$
 * @since     Niagara 3.0 modbusAscii 1.0
 */
public class NrioReceivedMessage
  extends ReceivedMessage
{
////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////

 /**
  * Constructor with message data in byte array form.
  * Also specifies the tag.
  */
  public NrioReceivedMessage(byte[] data, int len, Object tag)
  {
    this.data = data;
    this.len = len;
    setTag(tag);
    setUnsolicitedListenerCode(Integer.valueOf(-1));
  }

////////////////////////////////////////////////////////////
// NrioReceivedMessage
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

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  private byte[] data;
  private int len;
}
