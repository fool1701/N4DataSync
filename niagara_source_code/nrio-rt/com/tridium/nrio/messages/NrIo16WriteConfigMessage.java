/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * WriteConfigMessage is the ping request message.
 *
 * @author    Andy Saunders
 * @creation  06 Dec 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class NrIo16WriteConfigMessage
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public NrIo16WriteConfigMessage ()
  {
  }
  
  public NrIo16WriteConfigMessage ( int address, byte[] uiConfig)
  {
    this.address = address;
    this.type    = MSG_WR_CR_CONFIG;
    this.status  = 0;
    this.uiConfig   = uiConfig;
  }

  public byte[] getByteArray()
  {
    NrioOutputStream packet = new NrioOutputStream();
    packet.writeInt(IO_CONFIGURED);
    packet.write(uiConfig[0]);
    packet.write(uiConfig[1]);
    packet.write(uiConfig[2]);
    packet.write(uiConfig[3]);
    packet.write(0);
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


  protected byte[] uiConfig  ;
  private static int IO_CONFIGURED = 2;
 
}
