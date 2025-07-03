/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

/**
 * PingResponse is the ping response message.
 *
 * @author    Andy Saunders
 * @creation  06 Dec 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */
public class PingResponse
  extends NrioMessage
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public PingResponse ()
  {
  }
  
  public int  getLogicalAddress( ) { return logicalAddress ; }

  public void setLogicalAddress ( int  logicalAddress ) { this.logicalAddress = logicalAddress ; }

  /**********************************************  
  *  This method will read a response to a request message 
  *  into the base message structure
  **********************************************/
  public NrioInputStream readResponse(NrioReceivedMessage message)
  {
    NrioInputStream inStream = super.readResponse(message);
    if(status == MESSAGE_STATUS_OK)
      logicalAddress = inStream.read() & 0x0ff;
    return inStream;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////


  protected int    logicalAddress  ;
  
}
