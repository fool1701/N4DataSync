/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Clock;

/**
 * This class will handle transactions for communication stacks.
 *
 * @author Robert Adams
 * @creation 22 Oct 2011
 * @since Niagara 3.7
 */
public class NCommTimer
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  public NCommTimer(int maxOutstandingTransactions, int maxTransactionWait, String name)
  {
    this.maxTrans = maxOutstandingTransactions;
    this.maxTrnsWait = maxTransactionWait;
    this.trnsPool = new Pool(32)
    {
      @Override
      Timed makeInstance()
      {
        return new Transaction();
      }
    };
    this.fragPool = new Pool(32)
    {
      @Override
      Timed makeInstance()
      {
        return new Frags();
      }
    };
    this.trnsHash = new Hashtable<>();
    this.fragHash = new Hashtable<>();
    this.name = name;
  }

  public void start()
  {
    done = false;
    transactionTimer = new TransactionTimer(32);
    timerThread = new Thread(transactionTimer, name + ".TransactionTimer");
    timerThread.start();
    timerThread.setPriority(Thread.NORM_PRIORITY);
  }


  public synchronized void stop()
  {
    done = true;
    if (timerThread != null)
    {
      timerThread.interrupt();
      timerThread = null;
    }

    Enumeration<Transaction> ent = trnsHash.elements();
    while (ent.hasMoreElements())
    {
      Transaction t = ent.nextElement();
      synchronized (t)
      {
        freeTransaction(t);
        t.notifyAll();
      }
    }
    trnsHash.clear();
    synchronized (trnsHash)
    {
      trnsHash.notifyAll();
    }

    Enumeration<Frags> enf = fragHash.elements();
    while (enf.hasMoreElements())
    {
      Frags f = enf.nextElement();
      synchronized (f)
      {
        closeFragTimer(f.fragment);
        f.notifyAll();
      }
    }
    fragHash.clear();

  }


////////////////////////////////////////////////////////////
//  Transaction apis
////////////////////////////////////////////////////////////

  /**
   * Get a reference to a Transaction object and lock its usage until
   * freeTransaction is called.
   * <p>
   * If no free Transaction object is available, then this method will cause the
   * calling thread to loop until one becomes available or transaction wait
   * period passes in which case an Exception will be generated
   *
   * @return first available Transaction
   */
  public Transaction getTransaction(NMessage msg)
    throws Exception
  {
    if (done)
    {
      return null;
    }

    synchronized (trnsHash)
    {
      // If no available transaction wait for one to free
      if (curTrans >= maxTrans)
      {
        try
        {
          trnsHash.wait(maxTrnsWait * 1000);
        }
        catch (InterruptedException e)
        {
          return null;
        } //Indicates driver is shutting down
      }

      // If still no available transaction throw exception   
      if (curTrans >= maxTrans)
      {
        throw new NCommException("Would exceed maximum outstanding request messages.");
      }

      Transaction t = (Transaction)trnsPool.make();
      t.req = msg;
      trnsHash.put(msg.getTag(), t);
      transactionTimer.start(t);
      curTrans++;
      return t;
    }
  }

  /**
   * Free a Transaction object for another thread to use.
   */
  public void freeTransaction(Transaction transaction)
  {
    // Make sure we only free a transaction once
    if (transaction == null || transaction.freeTrns)
    {
      return;
    }

    synchronized (trnsHash)
    {
      if (transactionTimer != null)
      {
        transactionTimer.cancel(transaction);
      }
      trnsHash.remove(transaction.getTag());
      trnsPool.release(transaction);
      curTrans--;
      // NCCB-48009: Undo (inadvertent?) change to "notifyAll" from NCCB-27783
      trnsHash.notify();
    }
  }

  /**
   * Get a reference to the Transaction object by tag
   */
  Transaction getTransactionMatch(Object tag)
  {
    synchronized (trnsHash)
    {
      return trnsHash.get(tag);
    }
  }

  void resetTimer(Transaction transaction)
  {
    transactionTimer.restart(transaction);
  }

////////////////////////////////////////////////////////////
// Fragment support
////////////////////////////////////////////////////////////
  void startFragTimer(IFragmentable f)
  {
    if (done)
    {
      return;
    }

    synchronized (fragPool)
    {
      Frags frg = (Frags)fragPool.make();
      frg.fragment = f;
      fragHash.put(f.getHash(), frg);
      transactionTimer.start(frg);
    }
  }

  void closeFragTimer(IFragmentable f)
  {
    synchronized (fragPool)
    {
      Frags frg = fragHash.get(f.getHash());
      if (frg == null)
      {
        return;
      }
      if (transactionTimer != null)
      {
        transactionTimer.cancel(frg);
      }
      fragHash.remove(f.getHash());
      fragPool.release(frg);
    }
  }

  void setFragmentManager(FragmentManager fm)
  {
    this.fragMgr = fm;
  }
////////////////////////////////////////////////////////////
// Timer
////////////////////////////////////////////////////////////

  /**
   * Timer class for Transactions and Frags.
   */
  private class TransactionTimer
    implements Runnable
  {
    TransactionTimer(int max)
    {
      v = new Vector<>(max);
    }

    @Override
    public void run()
    {
      while (!done)
      {
        synchronized (this)
        {
          long currTime = Clock.ticks();
          timeToWait = 10000;

          if (!v.isEmpty())
          {
            Timed curTrans = v.firstElement();
            long toTime = curTrans.getEndTime();
            if (toTime <= currTime)
            {
              // Cancel transaction.
              v.removeElement(curTrans);
              curTrans.timeOut();
              // fall to the next transaction
              timeToWait = 0;
            }
            else
            {
              timeToWait = (int)(toTime - currTime);
            }
          }

          try
          {
            this.wait(timeToWait);
          }
          catch (Exception ignored)
          {
          }
        }
      }
    }

    synchronized void start(Timed tmd)
    {
      long transTime = tmd.getTimeOut();
      // Calculate end time
      long endTime = Clock.ticks() + transTime;
      tmd.setEndTime(endTime);
      // Sort in ascending order by end time
      int i = 0;
      for (; i < v.size(); i++)
      {
        if (v.elementAt(i).getEndTime() > endTime)
        {
          break;
        }
      }
      v.insertElementAt(tmd, i);
      if (i == 0)
      {
        notifyAll();
      }
    }

    synchronized void restart(Timed trans)
    {
      v.remove(trans);
      start(trans);
    }

    synchronized void cancel(Timed trans)
    {
      // trans.setStartTime(0);
      trans.setEndTime(0);
      v.removeElement(trans);
      notifyAll();
    }

    private void spy(SpyWriter out)
      throws Exception
    {
      out.startProps("timer");
      out.prop("currTime", Long.toString(Clock.ticks()));
      out.prop("timeToWait", timeToWait);
      out.endProps();

      out.startTable(true);
      out.trTitle("transactions", 2);
      out.w("<tr>").th("id").th("endTime").w("</tr>\n");
      for (int i = 0; i < v.size(); i++)
      {
        Timed c = v.elementAt(i);
        out.w("<tr>");
        out.td(Integer.toString(i));
        out.td(Long.toString(c.getEndTime()));
        out.w("</tr>\n");
      }
      out.endTable();
    }

    private final Vector<Timed> v;
    private int timeToWait;
  }

////////////////////////////////////////////////////////////
//  Timed / Transaction / Frags
////////////////////////////////////////////////////////////
  // Super class for Transaction and Frags that supports
  // timer and pool support
  abstract class Timed
  {
    void setEndTime(long s)
    {
      endTime = s;
    }

    long getEndTime()
    {
      return endTime;
    }

    abstract void timeOut();

    abstract long getTimeOut();

    abstract void init();

    abstract void free();

    long endTime;
  }

  // Class to support comm transaction 
  public class Transaction
    extends Timed
  {
    Transaction()
    {
    }

    @Override
    void init()
    {
      complete = false;
      exception = null;
      resp = null;
      freeTrns = false;
      endTime = 0;
      timeout = false;
      respRcvd = false;
    }

    @Override
    void free()
    {
      exception = null;
      resp = null;
      freeTrns = true;
      endTime = 0;
      timeout = false;
      respRcvd = false;
    }

    void setComplete(boolean c)
    {
      complete = c;
    }

    boolean getComplete()
    {
      return complete;
    }

    void setResponseMessage(NMessage msg)
    {
      resp = msg;
    }

    NMessage getResponseMessage()
    {
      return resp;
    }

    void setRequestMessage(NMessage msg)
    {
      req = msg;
    }

    NMessage getRequestMessage()
    {
      return req;
    }

    void setException(Exception e)
    {
      exception = e;
    }

    Exception getException()
    {
      return exception;
    }

    boolean isDone()
    {
      return respRcvd || timeout;
    }

    Object getTag()
    {
      return req.getTag();
    }

    boolean getTimeout()
    {
      return timeout;
    }

    @Override
    synchronized void timeOut()
    {
      if (!freeTrns && !getComplete())
      {
        timeout = true;
        setException(new NCommTimeoutException(req.toTraceString()));
        notifyAll();
      }
    }

    // note : this is called from within synchronized block
    void receivedResponse()
    {
      respRcvd = true;
      notifyAll();
    }

    @Override
    long getTimeOut()
    {
      return getRequestMessage().getResponseTimeOut() + getRequestMessage().getRespTimePadding();
    }

    boolean complete;
    boolean freeTrns;
    boolean timeout;
    boolean respRcvd;

    private Exception exception;
    private NMessage req;
    private NMessage resp;
  }

  // Class to support timeout of fragmented messages
  class Frags
    extends Timed
  {
    Frags()
    {
    }

    @Override
    void init()
    {
    }

    @Override
    void free()
    {
      fragment = null;
    }

    @Override
    void timeOut()
    {
      fragMgr.fragTimeout(fragment);
    }

    @Override
    long getTimeOut()
    {
      return fragment.getTimeout();
    }

    IFragmentable fragment;
  }

////////////////////////////////////////////////////////////////
// Pool support
////////////////////////////////////////////////////////////////

  // Pool will managed a cached pool of Timed (Transaction/Frags) objects
  abstract class Pool
  {
    Pool(int size)
    {
      this.maxSize = size;
      this.v = new Vector<>(size);
    }

    synchronized Timed make()
    {
      Timed t = null;
      if (v.size() > 0)
      {
        t = v.remove(0);
      }
      else
      {
        t = makeInstance();
        cnt++;
      }
      t.init();
      return t;
    }

    abstract Timed makeInstance();

    synchronized void release(Timed t)
    {
      t.free();
      // If vector is full then don't pool this one
      if (v.size() < maxSize)
      {
        v.add(t);
      }
    }

    Vector<Timed> v;
    int maxSize;

    // Total number of instances created
    int cnt = 0;
  }
////////////////////////////////////////////////////////////////
//Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps("TransactionManager");
    out.prop("done", done);
    out.prop("maximum transactions ", maxTrans);
    out.prop("transaction wait(seconds) ", maxTrnsWait);
    if (trnsPool != null)
    {
      out.prop("transPool max", trnsPool.cnt);
      out.prop("transPool available", trnsPool.v.size());
    }
    if (fragPool != null)
    {
      out.prop("fragPool max", fragPool.cnt);
      out.prop("fragPool available", fragPool.v.size());
    }
    out.endProps();
    transactionTimer.spy(out);
  }

////////////////////////////////////////////////////////////
//  TransactionManager local variables
////////////////////////////////////////////////////////////
  private final Hashtable<Object, Transaction> trnsHash;
  private TransactionTimer transactionTimer;
  private Thread timerThread = null;
  private boolean done = false;
  private final int maxTrans;
  private final int maxTrnsWait;
  private int curTrans;
  private final Pool trnsPool;

  private final Pool fragPool;
  private final Hashtable<Object, Frags> fragHash;
  private FragmentManager fragMgr;

  String name;
}
