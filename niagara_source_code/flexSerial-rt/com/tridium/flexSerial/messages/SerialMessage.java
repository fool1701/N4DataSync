/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;
import javax.baja.nre.util.*;
import javax.baja.util.*;

import com.tridium.basicdriver.message.*;

/**
 * SerialMessage is the super class for all Modbus Ascii messages.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  07 Nov 99
 * @author    Scott Hoye
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 09/04/02 12:47:14 PM$
 * @since     Niagara 3.0 flexSerial 1.0
 */
public class SerialMessage
  extends Message
  implements SerialMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public SerialMessage ()
  {
  }

 /**
  * Constructor with Modbus device address and function code
  * initialization parameters.
  */
  public SerialMessage (String message)
  {
    this.message = message;
    this.bytes = message.getBytes();
  }

 /**
  * Constructor with byte array
  * initialization parameters.
  */
  public SerialMessage (byte[] bytes)
  {
    this.bytes = bytes;
  }

////////////////////////////////////////////////////////////
// SerialMessage
////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////
// Message
////////////////////////////////////////////////////////////
  /**
  * Write this message to the given output stream.
  */
  public void write(OutputStream out)
  {
    try
    {

      out.write(bytes);
    }
    catch (Exception e) {}
  }

  /**
  * Convert a received message to
  * a response Message.
  */
  public Message toResponse(ReceivedMessage resp)
  {
    SerialReceivedMessage modResp = (SerialReceivedMessage)resp;
    SerialResponse respMessage = new SerialResponse();
    respMessage.readResponse(modResp.getBytes(), modResp.getLength());
    return respMessage;
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
  }


///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  protected String message;
  protected byte[] bytes;

}
