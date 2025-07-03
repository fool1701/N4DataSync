/**
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;
import com.tridium.nrio.components.BIOutputDefaultValues;
import com.tridium.nrio.components.BOutputDefaultValues;

/**
 * WriteOutputConfigMessage is the message to config output default values.
 *
 * @author    Andy Saunders
 * @creation  12 Nov 16
 */
public class WriteOutputConfigMessage
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public WriteOutputConfigMessage()
  {
  }

  public WriteOutputConfigMessage(int address, int powerupTime, int commLossTime, BIOutputDefaultValues defValueComp, boolean isPrimary)
  {
    this.address = address;
    this.type    = MSG_WR_IO_DEFAULT_MAP;
    this.status  = 0;
    this.commLossTime   = defValueComp.getEnableCommLossDefaults() ? commLossTime : 0;
    this.powerupTime   = defValueComp.getEnableStartupDefaults() ? powerupTime : 0;
    this.defValueComp = defValueComp;
    this.isPrimary = isPrimary;
  }

  public byte[] getByteArray()
  {
    NrioOutputStream packet = new NrioOutputStream();
    packet.writeIntRev(powerupTime);
    packet.writeIntRev(commLossTime);
    try{packet.write(defValueComp.toMessageBytes(isPrimary));}
    catch(Exception e)
    {
      e.printStackTrace();
    }
    // pad out to a total of 7;
//    packet.write(0);
//    packet.write(0);
//    packet.write(0);
//    packet.write(0);
//    packet.write(0);
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


  protected int commLossTime;
  protected int powerupTime;
  protected BIOutputDefaultValues defValueComp;
  protected boolean isPrimary = false;
  
}
