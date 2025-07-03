/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * WriteDownLoadData is the write down load data message.
 *
 * @author    Andy Saunders
 * @creation  09 Feb 06
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class WriteDownLoadData
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public WriteDownLoadData ()
  {
  }
  
  public WriteDownLoadData ( int address, byte[] memData, int memAddress, int offset)
  {
    this.address = address;
    this.type    = MSG_WR_CODE_DNLD_DATA;
    this.status  = 0;
    this.memData   = memData;
    this.memAddress = memAddress;
    this.offset = offset;
  }

  public byte[] getByteArray()
  {
    int length = MAX_MEMORY_DOWN_LOAD_SIZE;
    int padLength = DOWN_LOAD_MESSAGE_SIZE-length;
    if( (memData.length - offset) < MAX_MEMORY_DOWN_LOAD_SIZE )
    {
      length = memData.length - offset;
      padLength = DOWN_LOAD_MESSAGE_SIZE-length;
    }
    NrioOutputStream packet = new NrioOutputStream();
    packet.writeIntRev((length +1)/2);
    packet.writeIntRev(memAddress+offset);
    for(int i = offset; i < (offset + length); i++)
    {
      packet.write(memData[i]);
    }
    for(int i = 0; i < padLength; i++)
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

  public void setOffset(int offset)
  {
    this.offset = offset;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////


  protected int    memAddress  ;
  protected int    offset  ;
  protected byte[] memData;
  
}
