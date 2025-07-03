/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * WriteDOMessage is the ping request message.
 *
 * @author    Andy Saunders
 * @creation  06 Dec 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class WriteIoMessage
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public WriteIoMessage ()
  {
  }
  
  public WriteIoMessage ( int address, byte[] ioData)
  {
    this.address = address;
    this.type    = MSG_WR_DO_DATA;
    this.status  = 0;
    this.data   = ioData;
  }

  public byte[] getByteArray()
  {
//    NrioOutputStream packet = new NrioOutputStream();
//    packet.writeInt(value);
//    // pad out to a total of 7;
//    packet.write(0);
//    packet.write(0);
//    packet.write(0);
//    packet.write(0);
//    packet.write(0);
//    data = packet.toByteArray();
    return super.getByteArray();
  }

  /**
  * Convert a received message to 
  * a response Message.
  */
  public Message toResponse(ReceivedMessage resp)
  {
    NrioReceivedMessage accessResp = (NrioReceivedMessage)resp;
    NrioMessage respMessage = new NrioMessage();
    respMessage.readResponse(accessResp);
    return respMessage;
  }



///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////


  protected byte[] ioData  ;
  
}
