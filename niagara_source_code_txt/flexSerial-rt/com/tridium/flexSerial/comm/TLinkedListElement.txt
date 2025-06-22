/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.comm;

/**
 *  The TLinkedListElement interface is implemented by object that
 * must be managed be a TLinkedListManager.  Typically implemented
 * by protocol messages, etc. to handle "queueing" incoming or 
 * outgoing messages in a linked list.
 * <p>
 *   A TLinkedListElement can only be part of a single list at
 * any given time.  Duplicate entries of the same element within
 * the same list are also prohibited.
 *
 * @author    Dan Giorgis (Original R2 code)       
 * @creation  17 Feb 99
 * @author    Scott Hoye (Added to R3 ModbusSlave driver)       
 * @creation  13 Nov 02
 * @version   $Revision: 1$ $Date: 11/13/02 12:47:14 PM$  
 * @since     Niagara 3.0 flexSerial 1.0     
 */ 
public interface TLinkedListElement
{
  /**
  *  Get the next element in the list
  */
	public TLinkedListElement getNext();

  /**
  *  Set the next element in the list
  */
  public void setNext(TLinkedListElement e);

  /**
  *  Get the current manager of this element
  */
	public TLinkedListManager	getLinkedListManager();
  
  /**
  *  Set the current link manager for this element
  */
  public void setLinkedListManager(TLinkedListManager manager);

}
