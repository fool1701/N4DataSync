/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * ReadDefaultOutputStateMessage is whaat its name implies.
 *
 * @author    Andy Saunders
 * @creation  18 Nov 16
 */
public class ReadDefaultOutputStateMessage
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public ReadDefaultOutputStateMessage()
  {
  }

  public ReadDefaultOutputStateMessage(int address)
  {
    this.address = address;
    this.type    = MSG_RD_IO_DEFAULT_MAP;
    this.status  = 0;
    this.data = pad;
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
  static byte[] pad = { (byte)1,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0, };

  
}
