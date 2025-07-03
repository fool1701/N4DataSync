/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.util;

/**
 * LinkedQueue is a linked list of ILinkables designed for FIFO access for use
 * by comm components.  It is designed to minimize object creation by processing
 * only linkable objects which must implement the <code>LinkedQueue.ILinkable</code>
 * interface.
 *
 * @author Robert Adams
 * @creation 27 May 2011 - copied from Lonworks
 * @since Niagara 3.6
 */
public class LinkedQueue
{
  /**
   * Read the oldest object from the queue.  If none available wait for the next
   * object.
   */
  public synchronized ILinkable dequeue()
  {
    try
    {
      if (size == 0)
      {
        wait();
      }
    }
    catch (Throwable e)
    {
    }

    return getNext();
  }

  /**
   * Read the oldest object from the queue.  If none available wait the
   * specified time for the next object. If still none available return null.
   */
  public synchronized ILinkable dequeue(int timeout)
  {
    try
    {
      if (size == 0 && timeout > 0)
      {
        wait(timeout);
      }
    }
    catch (Throwable e)
    {
    }

    return getNext();
  }

  private ILinkable getNext()
  {
    ILinkable lnkable = head;
    if (lnkable == null)
    {
      return null;
    }
    head = lnkable.getNext();
    if (head == null)
    {
      tail = null;
    }
    lnkable.setNext(null);
    size--;
    return lnkable;
  }

  /**
   * Adds an linkable to the end of the LinkedQueue.
   *
   * @param value Linkable to append to the end of the queue.
   */
  public synchronized void enqueue(ILinkable value)
  {
    value.setNext(null);
    if (tail == null)
    {
      head = tail = value;
    }
    else
    {
      tail.setNext(value);
      tail = value;
    }
    size++;
    notifyAll();
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
// Attributes
////////////////////////////////////////////////////////////////

  ILinkable head;
  ILinkable tail;
  int size;

////////////////////////////////////////////////////////////////
// Linkable
////////////////////////////////////////////////////////////////

  /**
   * ILinkable is interface implemented by linkable objects which will be
   * handled by a {@code LinkedQueue}.
   */
  public interface ILinkable
  {
    ILinkable getNext();

    void setNext(ILinkable nxt);
  }
}
