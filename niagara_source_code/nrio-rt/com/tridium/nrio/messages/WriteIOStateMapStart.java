/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * WriteIOStateMapStart is the write IO state map start message.
 *
 * @author    Andy Saunders
 * @creation  18 Nov 16
 */
public class WriteIOStateMapStart
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public WriteIOStateMapStart()
  {
  }

  public WriteIOStateMapStart(int address)
  {
    this.address = address;
    this.type    = MSG_WR_IO_DEFAULT_START;
    this.status  = 0;
  }

  public byte[] getByteArray()
  {
    NrioOutputStream packet = new NrioOutputStream();
    for(int i = 0; i < 7; i++)
    {
      packet.write(0);
    }
    data = packet.toByteArray();
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

}
