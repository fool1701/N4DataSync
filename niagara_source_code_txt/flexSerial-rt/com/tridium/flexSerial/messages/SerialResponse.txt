/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;
import javax.baja.nre.util.*;
import javax.baja.util.*;

import com.tridium.basicdriver.message.*;

/**
 * SerialResponse is the super class for all serial response
 * messages.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  07 Nov 99
 * @author    Scott Hoye
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 09/04/02 12:47:14 PM$
 * @since     Niagara 3.0 flexSerial 1.0
 */
public class SerialResponse
  extends ReceivedMessage
  implements SerialMessageConst
{
////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public SerialResponse()
  {
  }

////////////////////////////////////////////////////////////
// SerialResponse
////////////////////////////////////////////////////////////
  /**
   * Form the response data based on the given received bytes.
   */
  public void readResponse(byte[] response, int len)
  {
    bytes = new byte[len];
    System.arraycopy(response, 0, bytes, 0, len);
    return;

  }


  /**
   * Returns true if the Modbus response
   * was an exception response, false otherwise.
   */
  public boolean isError()
  {
    return false;
  }

  public String toString()
  {
    return "message = " + message;
  }

  public String getMessage()
  {
    return message;
  }

  public byte[] getBytes()
  {
    return bytes;
  }


////////////////////////////////////////////////////////////
// Message
////////////////////////////////////////////////////////////

  /**
   * Return a debug string for this message.
   */
  public String toDebugString()
  {
      return ByteArrayUtil.toHexString(bytes);
    /*
    StringBuilder sb = new StringBuilder();
    sb.append("SerialResponse = " + TextUtil.getClassName(getClass()));
    sb.append("\n  Tag = " + getTag());
    sb.append("\n  message = " + message);
    return sb.toString();
    */
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  protected String  message;
  protected byte[] bytes;
}
