/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.comm;

import java.util.*;

import com.tridium.flexSerial.comm.*;
import com.tridium.flexSerial.messages.*;

/**
 * MessageElement is a linked list element
 * to contain an unsolicited received Modbus message.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  29 Feb 00
 * @author    Scott Hoye (Upgraded to R3)      
 * @creation  13 Nov 02
 * @version   $Revision: 1$ $Date: 11/13/02 12:47:14 PM$  
 * @since     Niagara 3.0 flexSerial 1.0     
 */ 
public class MessageElement
  implements com.tridium.flexSerial.comm.TLinkedListElement
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**********************************************
  *  Create a Linked list element for an message
  *  where the LinkedListManager will be set 
  *  later.
  **********************************************/
  public MessageElement(SerialReceivedMessage message)
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
// Access methods
////////////////////////////////////////////////////////////////
  public SerialReceivedMessage getMessage()
  {
    return message;
  }

  public void setMessage( SerialReceivedMessage message)
  {
    this.message = message;
  }

  public String toString() { return message.toString(); }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private SerialReceivedMessage    message;
  private TLinkedListManager manager;
  private TLinkedListElement next;

} // end of MessageElement