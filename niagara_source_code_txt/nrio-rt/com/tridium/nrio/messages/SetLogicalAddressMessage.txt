/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * SetLogicalAddressMessage 
 *
 * @author    Andy Saunders
 * @creation  06 Dec 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class SetLogicalAddressMessage
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public SetLogicalAddressMessage ()
  {
  }

  public SetLogicalAddressMessage ( int setAddress, byte[] uid)
  {
    this.address = BROADCAST_ADDR;
    this.type    = MSG_SET_LOGICAL_ADDRESS;
    this.status  = 0;
    this.uid     = uid;
    this.setAddress    = setAddress;
    this.moduleType = 0;
  }


  public SetLogicalAddressMessage ( int setAddress, byte[] uid, int moduleType)
  {
    this.address = BROADCAST_ADDR;
    this.type    = MSG_SET_LOGICAL_ADDRESS;
    this.status  = 0;
    this.uid     = uid;
    this.setAddress    = setAddress;
    this.moduleType = moduleType;
  }


  public byte[] getByteArray()
  {
    NrioOutputStream packet = new NrioOutputStream();
    try {packet.write(uid, 0, 6);} catch(Exception e) {}
    packet.write(setAddress);
    if(moduleType != 0)
      packet.write(moduleType);
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


  protected byte[] uid = new byte[6];
  protected int    setAddress  ;
  protected int    moduleType  ;

}
