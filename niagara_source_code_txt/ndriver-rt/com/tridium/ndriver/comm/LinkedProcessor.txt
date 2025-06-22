/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import com.tridium.ndriver.util.LinkedQueue;
import com.tridium.ndriver.util.LinkedQueue.ILinkable;

/**
 * LinkedProcessor is a utility class that provides a queue and thread to
 * process ILinkables.  APIs are provided to start and stop the thread, enqueue
 * ILinkables and a process() override point to process ILinkables.
 *
 * @author Robert Adams
 * @version $Revision:$ $Date:$
 * @creation 22 Feb 2012
 * @since Niagara 3.7
 */
public abstract class LinkedProcessor
  implements Runnable
{
  public LinkedProcessor() {}

  /**
   * Start the processing thread. Use the specified name.
   */
  public void start(String threadName)
  {
    if (processThread != null)
    {
      return;
    }
    done = false;
    processThread = new Thread(this, threadName);
    processThread.start();
  }

  /**
   * Start the processing thread and clear the queue.
   */
  public void stop()
  {
    done = true;
    if (processThread != null)
    {
      processThread.interrupt();
    }
    processThread = null;
    rcvQueue.clear();
  }

  @Override
  public void run()
  {
    while (!done)
    {
      try
      {
        ILinkable newMsg = rcvQueue.dequeue();
        if (newMsg != null)
        {
          process(newMsg);
        }
      }
      catch (Throwable e)
      {
      }
    }
  }

  /**
   * Add and ILinkable to the queue for processing.
   */
  public void enqueue(ILinkable lmsg)
  {
    rcvQueue.enqueue(lmsg);
  }

  /**
   * Subclasses must implement to process enqueued objects.
   */
  public abstract void process(Object obj);

  LinkedQueue rcvQueue = new LinkedQueue();
  Thread processThread = null;
  boolean done = false;
}
