/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * WriteDownLoadStart is the write down load stop message.
 *
 * @author    Andy Saunders
 * @creation  09 Feb 06
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class WriteDownLoadStart
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public WriteDownLoadStart ()
  {
  }
  
  public WriteDownLoadStart ( int address)
  {
    this.address = address;
    this.type    = MSG_WR_CODE_DNLD_START;
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
