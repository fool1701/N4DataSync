/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import java.io.OutputStream;
import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;

/**
 * PingMessage is the ping request message.
 *
 * @author    Andy Saunders
 * @creation  06 Dec 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class PingMessage
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public PingMessage ()
  {
  }
  
  public PingMessage ( int address, byte[] uid, int deviceType)
  {
    this.address = address;
    this.type    = MSG_PING;
    this.status  = 0;
    this.uid     = uid;
    this.moduleType = deviceType;
  }

  public PingMessage ( int address, byte[] uid, BNrioDeviceTypeEnum deviceType)
  {
    this.address = address;
    this.type    = MSG_PING;
    this.status  = 0;
    this.uid     = uid;
    this.moduleType = deviceType.getRawInt();
  }


  public byte[] getUid     ( ) { return uid        ; }
  public int  getModuleType( ) { return moduleType ; }

  public void setUid        ( byte[] uid      ) { this.uid        = uid        ; }
  public void setModuleType ( int  moduleType ) { this.moduleType = moduleType ; }

  /**
  * Write this message to the given output stream. 
  */
  public void write(OutputStream out)
  {
    try
    {
      byte[] ba = getByteArray();
//            System.out.println(" sending packet: ");
//            ByteArrayUtil.hexDump(ba);
      out.write(ba);
    }
    catch (Exception e) {}
  }

  public byte[] getByteArray()
  {
    NrioOutputStream packet = new NrioOutputStream();
    try {packet.write(uid, 0, 6);} catch(Exception e) {}
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
    PingResponse respMessage = new PingResponse();
    respMessage.readResponse(accessResp);
    return respMessage;
  }


///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////


  protected byte[] uid = new byte[6];
  protected int    moduleType  ;
  
}
