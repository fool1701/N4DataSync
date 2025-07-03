/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import java.util.logging.Logger;

/**
 * NLinkMessageFactory is a utility class to create a pool of LinkMessage for
 * use by ILinkLayer implementations.  To use custom subclasses of NLinkMessage
 * create subclass of NLinkMessageFactory and override createLinkMessage().
 *
 * @author Robert A Adams
 * @creation Oct 23, 2011
 */
public class NLinkMessageFactory
{
  /**
   * Constructor specifying maximum linkMessage length.
   */
  public NLinkMessageFactory(int linkMaxLength)
  {
    this(32, linkMaxLength);
  }

  /**
   * Constructor specifying the max size message pool and maximum linkMessage
   * length.
   */
  public NLinkMessageFactory(int maxPool, int linkMaxLength)
  {
    this.pool = new Pool(maxPool);
    this.linkMaxLength = linkMaxLength;
  }

  /**
   * Get a LinkMessage from the pool.  Create new on if needed.
   */
  public final LinkMessage getLinkMessage()
  {
    LinkMessage lm = pool.getPoolMessage();
    return lm;
  }

  /**
   * Release the LinkMessage to the pool.
   */
  public final void releaseLinkMessage(LinkMessage lm)
  {
    pool.releasePoolMessage(lm);
  }

  /**
   * Override point for subclasses to create subclass of LinkMessage.
   */
  protected LinkMessage createLinkMessage()
  {
    return new LinkMessage(linkMaxLength);
  }

  public int getLinkMaxLength()
  {
    return linkMaxLength;
  }

  private Pool pool;
  protected int linkMaxLength;

////////////////////////////////////////////////////////////
// Pool class to maintain pool of LinkMessages
////////////////////////////////////////////////////////////
  class Pool
  {
    public Pool(int maxPool)
    {
      appPool = new LinkMessage[maxPool];
    }

    LinkMessage getPoolMessage()
    {
      LinkMessage lm;
      synchronized (appPool)
      {
        if (appCnt > 0)
        {
          lm = appPool[--appCnt];
        }
        else
        {
          lm = createLinkMessage();
        }
        lm.init();
        lm.freeBuf = false;
      }
      return lm;
    }

    void releasePoolMessage(LinkMessage lm)
    {
      synchronized (appPool)
      {
        while (lm != null)
        {
          // Sanity check - prevent duplicate release
          if (lm.freeBuf)
          {
            System.out.println("already free ");
            Thread.dumpStack();
            return;
          }

          if (appCnt < appPool.length)
          {
            appPool[appCnt++] = lm;
          }
          lm.freeBuf = true;
          lm = (LinkMessage)lm.getNext();
        }
      }
    }

    private LinkMessage[] appPool;
    private int appCnt = 0;
  }
}
