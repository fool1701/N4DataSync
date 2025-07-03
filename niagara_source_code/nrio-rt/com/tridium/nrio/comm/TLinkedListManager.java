/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.comm;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 *  The TLinkedListManager manages a linked list of 
 * TLinkedListElements. Typically used to handle "queueing" 
 * incoming or  outgoing messages in a linked list.
 * <p>
 *   A TLinkedListElement can only be part of a single list at
 * any given time.  Duplicate entries of the same element within
 * the same list are also prohibited.
 *
 * @author    Dan Giorgis (Original R2 code - modified TLinkedList)       
 * @creation  17 Feb 99
 * @author    Andy Saunders (Added to R3 ModbusSlave driver)       
 * @creation  13 Nov 02
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:11 AM$  
 * @since     Niagara 3.0 geM6 1.0   
 */ 
public class TLinkedListManager
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
  * Create a linked list manager which manages
  * TLinkedListElements. Duplicates are NOT allowed.
  */
  public TLinkedListManager(String name)
  {
    this.name = name;
  }
  
////////////////////////////////////////////////////////////////
// Nrio
////////////////////////////////////////////////////////////////

  /**
  * Get the number of elements.
  */
  public synchronized int size()
  {
    return size;
  }
  
  /**
  * Is the linked list empty?
  */
  public synchronized boolean isEmpty()
  {
    return head == null;
  }

  /**
  *  Calls notifyAll.  Pseudo-back door to wake
  * up threads parked in removeFromHead() w/o
  * having to write a dummy entry to the list.
  */
  public synchronized void signal()
  {
    notifyAll();
  }

  /**
  * Add an object to the head of the 
  * linked list.
  */
  public synchronized void addToHead(TLinkedListElement linkage)
  {
    TLinkedListManager mgr = linkage.getLinkedListManager();

    //  Validate the linked list manager
    if (mgr != null)
      throw new IllegalStateException("Already linked " + mgr.getName() + ", my name " + getName());

    linkage.setLinkedListManager(this);

    if (head == null)
    {
      head = tail = linkage;
    }
    else
    {
      linkage.setNext(head);
      head = linkage;
    }
    size++;
    
    notifyAll();
  }
  
  /**
  * Add an object to the head of the 
  * linked list.
  */
  public synchronized void addToTail(TLinkedListElement linkage)
  {
    TLinkedListManager mgr = linkage.getLinkedListManager();

    //  Validate the linked list manager
    if (mgr != null)
      throw new IllegalStateException("Already linked " + mgr.getName() + ", my name " + getName());

    linkage.setLinkedListManager(this);
        
    if (head == null)
    {
      head = tail = linkage;
    }
    else
    {
      tail.setNext(linkage);
      tail = linkage;
    }
    size++;
    
    notifyAll();
  }
  
  /**
  * Remove the head of the linked list.
  * @return null if empty.
  */
  public synchronized TLinkedListElement removeFromHead()
  {    
    if (head == null)
      return null;
      
    TLinkedListElement linkage = head;
    head = linkage.getNext();

    if (head == null)
      tail = null;
    
    size--;
    
    linkage.setLinkedListManager(null);   //  no longer owned!
    linkage.setNext(null);
    
    return linkage;
  }
  
  /**
  * This method blocks the caller's thread
  * until there is something to remove from
  * the linked list or the timeout is expired.
  * Waits forever if timeout is -1.
  * @return null if timer expired or signal() is
  *  called
  */
  public synchronized TLinkedListElement removeFromHead(long timeout)
    throws InterruptedException
  {
    if (head == null) 
    {
      if (timeout == -1)
        wait();
      else
        wait(timeout);
    }
    return removeFromHead();
  }
  

  

////////////////////////////////////////////////////////////////
// Debugging.
////////////////////////////////////////////////////////////////  

  public String getName()
  {
    return name;
  }

  /**
  * To string.   
  */
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    Enumeration<Object> e = elements();
    boolean first = true;
    buf.append(size()).append(" {");
    while(e.hasMoreElements())
    {
      if (first) first = false;
      else buf.append(", ");
      buf.append(e.nextElement());
    }
    buf.append("}");
    return buf.toString();
  }
  
//////////////////////////////////////////////////////////////
// Enumerator
//////////////////////////////////////////////////////////////

  /**
  * Get an enumeration of all the elements. 
  */
  public Enumeration<Object> elements()
  {
    return new Enumerator();
  }

  /**
  * Specialized enumeration for LinkedList.
  */
  public class Enumerator
    implements java.util.Enumeration<Object>
  {
  
    /**
    * Constructor.
    */
    public Enumerator() 
    {
      cur = head;
    }

    /**
    * Return true if this Enumeration has more
    * elements.
    */
    public boolean hasMoreElements()
    {
      return (cur != null);
    }

    /**
    * Return the next node.
    */
    public Object nextElement() 
    {
      if (cur == null)
        throw new NoSuchElementException();
      TLinkedListElement p = cur;
      cur = cur.getNext();
      return p;
    }

    private TLinkedListElement cur;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private TLinkedListElement head;
  private TLinkedListElement tail;
  private int size;
  private String name;
  
}