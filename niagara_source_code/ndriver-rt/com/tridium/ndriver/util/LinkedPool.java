/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.util;

import com.tridium.ndriver.util.LinkedQueue.ILinkable;

/**
 * LinkedPool is a pool of {@code PoolLinkable}. {@code PoolLinkable}
 * extends
 * {@code LinkQueue.ILinkables}.  It is designed to work in conjunction
 * with
 * LinkQueue.  LinkedPool was created to minimize object creation by providing
 * to a means to maintain a pool of reusable objects.
 *
 * @author Robert Adams
 * @version $Revision:$ $Date:$
 * @creation 20 Nov 2012
 * @since Niagara 3.6.303
 */
public class LinkedPool
{
  public LinkedPool(int maxPool, Class<?> cls)
  {
    pool = new PoolLinkable[maxPool];
    this.cls = cls;
  }

  public final PoolLinkable get()
    throws Exception
  {
    PoolLinkable pl;
    synchronized (pool)
    {
      if (poolCnt > 0)
      {
        pl = pool[--poolCnt];
      }
      else
      {
        pl = create();
      }
      pl.claim();
    }
    return pl;
  }

  public final void release(PoolLinkable pl)
  {
    synchronized (pool)
    {
      while (pl != null)
      {
        // Sanity check - prevent duplicate release
        if (pl.isFree())
        {
          System.out.println("already free");
          Thread.dumpStack();
          return;
        }

        if (poolCnt < pool.length)
        {
          pool[poolCnt++] = pl;
        }
        pl = (PoolLinkable)pl.getNext();
        pl.free();
      }
    }
  }

  /**
   * Create a new instance of class for this LinkPool.  Override point to
   * customized creation of class.
   */
  public PoolLinkable create()
    throws Exception
  {
    return (PoolLinkable)cls.getDeclaredConstructor().newInstance();
  }

  private final PoolLinkable[] pool;
  private int poolCnt = 0;
  private Class<?> cls;
////////////////////////////////////////////////////////////////
// PoolLinkable
////////////////////////////////////////////////////////////////

  /**
   * PoolLinkable is interface implemented by linkable objects which will be
   * handled by a {@code LinkedQueue}.
   */
  public static abstract class PoolLinkable
    implements ILinkable
  {
    public abstract void init();

    public abstract void release();

    void claim()
    {
      init();
      free = false;
    }

    void free()
    {
      release();
      free = true;
      next = null;
    }

    boolean isFree()
    {
      return free;
    }

    private boolean free = true;

    // ILinkable implementation
    @Override
    public ILinkable getNext()
    {
      return next;
    }

    @Override
    public void setNext(ILinkable nxt)
    {
      next = (PoolLinkable)nxt;
    }

    private PoolLinkable next = null;
  }
}
