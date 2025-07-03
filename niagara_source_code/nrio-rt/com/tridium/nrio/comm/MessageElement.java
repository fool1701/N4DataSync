/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.comm;

import com.tridium.nrio.messages.NrioReceivedMessage;

/**
 * MessageElement is a linked list element
 * to contain an unsolicited received Modbus message.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  29 Feb 00
 * @author    Andy Saunders (Upgraded to R3)      
 * @creation  13 Nov 02
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:11 AM$  
 * @since     Niagara 3.0 geM6 1.0     
 */ 
public class MessageElement
  implements com.tridium.nrio.comm.TLinkedListElement
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**********************************************
  *  Create a Linked list element for an message
  *  where the LinkedListManager will be set 
  *  later.
  **********************************************/
  public MessageElement(NrioReceivedMessage message)
  {
    this.message = message;
    manager = null;
  }


////////////////////////////////////////////////////////////////
// Element methods
////////////////////////////////////////////////////////////////
	public TLinkedListElement getNext()
  {
    return next;
  }

  public void setNext(TLinkedListElement next)
  {
    this.next = next;
  }

	public TLinkedListManager	getLinkedListManager()
  {
    return manager;
  }
  
  public void setLinkedListManager(TLinkedListManager manager)
  {
    this.manager = manager;
  }

////////////////////////////////////////////////////////////////
// Nrio methods
////////////////////////////////////////////////////////////////
  public NrioReceivedMessage getMessage()
  {
    return message;
  }

  public void setMessage( NrioReceivedMessage message)
  {
    this.message = message;
  }

  public String toString() { return message.toString(); }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private NrioReceivedMessage    message;
  private TLinkedListManager manager;
  private TLinkedListElement next;

} // end of MessageElement