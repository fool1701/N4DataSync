/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

/**
 * Queue is a linked list of objects designed for FIFO access.
 *
 * @author    Brian Frank
 * @creation  19 Jan 01
 * @version   $Revision: 11$ $Date: 11/13/08 4:31:53 PM EST$
 * @since     Baja 1.0
 */
public class Queue  
  implements Worker.ITodo
{     

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create a new Queue with the specified max number
   * of entries.  Attempts to enqueue more than maxSize 
   * will result in QueueFullException.
   */
  public Queue(int maxSize)
  {
    this.maxSize = maxSize;
  }
  
  /**
   * Construct a new Queue with a max size of Integer.MAX_VALUE.
   */
  public Queue()
  {
    this(Integer.MAX_VALUE);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the number of entries currently in the queue.
   */
  public int size()
  {
    return size;
  }

  /**
   * Get the max number of entries this queue will
   * access before throwing QueueFullException.
   */
  public int maxSize()
  {
    return maxSize;
  }
  
  /**
   * Return true if this queue has a size of 0.
   */
  public boolean isEmpty()
  {
    return size == 0;
  }

  /**
   * Return true if this queue has a size of maxSize.
   */
  public boolean isFull()
  {
    return size == maxSize;
  }

  /**
   * Peek at the newest object on the queue,
   * but don't actually dequeue it.
   *
   * @return newest queue element, or null
   *    if the queue is empty.
   */
  public synchronized Object tail()
  {
    if (tail == null) return null;
    return tail.value;
  }
  
  /**
   * Peek at the oldest object on the queue,
   * but don't actually dequeue it.
   *
   * @return oldest queue element, or null
   *    if the queue is empty.
   */
  public synchronized Object peek()
  {
    if (head == null) return null;
    return head.value;
  }
  
  /**
   * Peek at the oldest object on the queue.  If
   * no objects exist on the queue, then wait for
   * up to timeout milliseconds before returning.
   *
   * @param timeout number of milliseconds to wait
   *    before timing out or -1 to wait forever.
   * @return oldest Queue element, or null
   *    if the queue is empty and the timeout
   *    expired.
   */
  public synchronized Object peek(int timeout)   
    throws InterruptedException
  {
    while (size == 0)
    {
      if (timeout == -1) wait();
      else { wait(timeout); break; }
    }
    return peek();
  }

  /**
   * Search the enqueued entries for an entry that
   * returns true for equals(value).  If such an
   * entry is found then return it.  Otherwise return
   * null.  This is an expensive operation because
   * it requires a linear search.
   */
  public synchronized Object find(Object value)
  {
    for(Entry e = head; e != null; e = e.next)
      if (e.value.equals(value)) 
        return e.value;
    return null;
  }

  /**
   * Read off the oldest object from the queue.
   *
   * @return oldest queue element, or null
   *    if the queue is empty.
   */
  public synchronized Object dequeue()
  {
    Entry entry = head;
    if (entry == null) return null;
    head = entry.next;
    if (head == null) tail = null;
    entry.next = null;
    size--;
    return entry.value;
  }               
  
  /**
   * Read off the oldest object from the queue.  If
   * no objects exist on the queue, then wait for
   * up to timeout milliseconds before returning
   *
   * @param timeout number of milliseconds to wait
   *    before timing out or -1 to wait forever.
   * @return oldest Queue element, or null
   *    if the queue is empty and the timeout
   *    expired.
   */
  public synchronized Object dequeue(int timeout)   
    throws InterruptedException
  {
    while (size == 0)
    {
      if (timeout == -1) wait();
      else { wait(timeout); break; }
    }
    return dequeue();
  }

  /**
   * Adds an entry to the end of the Queue.
   *                         
   * @return true if object was enqueued.
   * @param value Object to append to the
   *    end of the queue.
   * @throws QueueFullException is the queue
   *    is already at max size.
   */
  public synchronized boolean enqueue(Object value)
    throws QueueFullException
  {
    if (size >= maxSize)
      throw new QueueFullException();
    if (value == null)
      throw new NullPointerException();
      
    Entry entry = newEntry(value);
    entry.next = null;
    if (tail == null) { head = tail = entry; }
    else { tail.next = entry; tail = entry; }
    size++;                
    notifyAll();       
    return true;
  }

  /**
   * Adds an entry to the front of the Queue.
   *                         
   * @return true if object was enqueued.
   * @param value Object to add to the front
   *    of the queue.
   * @throws QueueFullException is the queue
   *    if already at max size.
   */
  public synchronized boolean push(Object value)
    throws QueueFullException
  {
    if (size >= maxSize)
      throw new QueueFullException();
    if (value == null)
      throw new NullPointerException();
      
    Entry entry = newEntry(value);
    entry.next = null;
    if (head == null) { head = tail = entry; }
    else { entry.next = head; head = entry; }
    size++;                
    notifyAll();       
    return true;
  }     
  
  Entry newEntry(Object v) { return new Entry(v); }
  
  /**
   * Get a snapshot of the queue's list.
   */
  public synchronized Object[] toArray()
  {  
    Object[] a = new Object[size];
    Entry p = head;
    for(int i=0; p != null; ++i) { a[i] = p.value; p = p.next; }
    return a;
  }
  
  /**
   * Remove all the enqueued entries.
   */
  public synchronized void clear()
  {
    size = 0;
    head = null;
    tail = null;
    notifyAll();
  }


////////////////////////////////////////////////////////////////
// ITodo
////////////////////////////////////////////////////////////////
  
  /**
   * If this queue is being with with a Worker, then dequeue
   * the top entry as a Runnable with the specified timeout.
   */
  @Override
  public Runnable todo(int timeout)
    throws InterruptedException
  {
    return (Runnable)dequeue(timeout);
  }

////////////////////////////////////////////////////////////////
// Entry
////////////////////////////////////////////////////////////////

  static class Entry
  {                 
    Entry(Object v) { value = v; }
    
    Entry next;
    Object value;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Entry head;
  Entry tail;
  int size;
  int maxSize;
  
}
