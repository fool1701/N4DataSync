/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * ReadBuildInfoMessage is the ping request message.
 *
 * @author    Andy Saunders
 * @creation  13 Jan 06
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class ReadBuildInfoMessage
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public ReadBuildInfoMessage ()
  {
  }
  
  public ReadBuildInfoMessage ( int address)
  {
    this.address = address;
    this.type    = MSG_RD_BUILD_INFO;
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
  static byte[] pad = { (byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0, };

  
}
