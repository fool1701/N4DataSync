/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.nre.util.TextUtil;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * NrioResponse is the super class for all Modbus Ascii response
 * messages.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  07 Nov 99
 * @author    Andy Saunders
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$
 * @since     Niagara 3.0 modbusAscii 1.0
 */
public class NrioResponse
  extends ReceivedMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public NrioResponse()
  {
  }

////////////////////////////////////////////////////////////
// NrioResponse
////////////////////////////////////////////////////////////

  /**
   * Form the response data based on the given received bytes.
   */
  public void readResponse(byte[] response, int len)
  {
    NrioInputStream in = new NrioInputStream(response, 0, len);
    in.read(); // throw away SOH
    int length = in.read() & 0x0ff;
    transactionId = in.read() & 0x0ff;
    classId = in.read() & 0x0ff;
    classInstance = in.read() & 0x0ff;
    opCode = in.readInt();
    data = new byte[in.available()];
    int i = 0;
    while( in.available() > 0 )
    {
      data[i++] = (byte)(in.read() & 0x0ff);
    }

  }

  public byte[] getData()
  {
    return data;
  }

  public String toString()
  {
    return toDebugString();
  }

////////////////////////////////////////////////////////////
// Message
////////////////////////////////////////////////////////////

  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("NrioMessage       = " + TextUtil.getClassName(getClass()));
    sb.append("\n  Tag           = " + getTag());
    sb.append("\n  transactionId = " + transactionId);
    sb.append("\n  classId       = " + classId);
    sb.append("\n  classInstance = " + classInstance);
    sb.append("\n  opCode        = " + opCode);
    sb.append("\n  data          = " + ByteArrayUtil.toHexString(data) );
    return sb.toString();
  }


///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  protected int transactionId;
  protected int classId;
  protected int classInstance;
  protected int opCode;
  protected byte[] data = new byte[0];
}
