/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import javax.baja.bacnet.util.worker.IWorkerPool;
import javax.baja.bacnet.util.worker.IWorkerPoolAware;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BBacnetWorker is the implementation of BWorker for Bacnet.
 * It uses a CoalesceQueue, so that writes can be coalesced as
 * needed.  Non-coalescing behavior can be accomplished by enqueueing
 * objects that do not implement BICoalescable.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 11 Feb 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 the size of the queue for this worker.
 */
@NiagaraProperty(
  name = "maxQueueSize",
  type = "int",
  defaultValue = "1000"
)
/*
 name of the worker thread.
 */
@NiagaraProperty(
  name = "workerThreadName",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY
)
public class BBacnetWorker
  extends BWorker
  implements IWorkerPoolAware
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.util.BBacnetWorker(600605266)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "maxQueueSize"

  /**
   * Slot for the {@code maxQueueSize} property.
   * the size of the queue for this worker.
   * @see #getMaxQueueSize
   * @see #setMaxQueueSize
   */
  public static final Property maxQueueSize = newProperty(0, 1000, null);

  /**
   * Get the {@code maxQueueSize} property.
   * the size of the queue for this worker.
   * @see #maxQueueSize
   */
  public int getMaxQueueSize() { return getInt(maxQueueSize); }

  /**
   * Set the {@code maxQueueSize} property.
   * the size of the queue for this worker.
   * @see #maxQueueSize
   */
  public void setMaxQueueSize(int v) { setInt(maxQueueSize, v, null); }

  //endregion Property "maxQueueSize"

  //region Property "workerThreadName"

  /**
   * Slot for the {@code workerThreadName} property.
   * name of the worker thread.
   * @see #getWorkerThreadName
   * @see #setWorkerThreadName
   */
  public static final Property workerThreadName = newProperty(Flags.TRANSIENT | Flags.READONLY, "", null);

  /**
   * Get the {@code workerThreadName} property.
   * name of the worker thread.
   * @see #workerThreadName
   */
  public String getWorkerThreadName() { return getString(workerThreadName); }

  /**
   * Set the {@code workerThreadName} property.
   * name of the worker thread.
   * @see #workerThreadName
   */
  public void setWorkerThreadName(String v) { setString(workerThreadName, v, null); }

  //endregion Property "workerThreadName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetWorker.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetWorker()
  {
  }

  /**
   * Constructor with queue size.
   */
  public BBacnetWorker(int queueSize)
  {
    setMaxQueueSize(queueSize);
  }

  /**
   * @param workerName
   */
  public BBacnetWorker(String workerName)
  {
    setWorkerThreadName(workerName);
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {
    if (getWorkerThreadName().length() == 0)
      setWorkerThreadName(getParent().getName() + ":" + getName());
    super.started();
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (p == maxQueueSize)
    {
      if (!isRunning()) return;
      synchronized (lock)
      {
        if (workerPool == null)
        {
          stopWorker();
          queue = null;
          worker = null;
          getWorker();
          startWorker();
        }
      }
    }
  }

  public void added(Property property, Context context)
  {
    super.added(property, context);
    BObject o = null;
    if ((o = get(property)) instanceof IWorkerPool)
    {
      IWorkerPool pool = (IWorkerPool)o;
      synchronized (lock)
      {
        workerPool = pool;
        if (worker != null && worker.isRunning())
          worker.stop();
      }
    }
  }

  public void removed(Property property, BValue oldValue, Context context)
  {
    super.removed(property, oldValue, context);
    if (oldValue instanceof IWorkerPool)
    {
      synchronized (lock)
      {
        workerPool = null;
        startWorker();
      }
    }
  }

  public void stopWorker()
  {
    synchronized (lock)
    {
      if (worker != null && worker.isRunning())
        worker.stop();
    }
  }

////////////////////////////////////////////////////////////////
// BWorker
////////////////////////////////////////////////////////////////

  /**
   * Post an action to be run asynchronously.
   */
  public IFuture post(Runnable r)
  {
    if (!isRunning())
      throw new NotRunningException();

    if (queue == null)
      throw new NotRunningException();

    synchronized (lock)
    {
      if (workerPool == null || !workerPool.isRunning())
      {
        queue.enqueue(r);
      }
      else
      {
        workerPool.post(r);
      }
    }
    return null;
  }

  /**
   * Start running this task.
   */
  public Worker getWorker()
  {
    if (worker == null)
    {
      queue = new CoalesceQueue(getMaxQueueSize());
      worker = new Worker(queue);
    }
    return worker;
  }

  public void setWorker(Worker worker)
  {
    this.worker = worker;
  }

  public void dump()
  {
    synchronized (queue)
    {
      Object[] a = queue.toArray();
      System.out.println("BBacnetWorker dump (" + a.length + " entries):");
      for (int i = 0; i < a.length; i++)
        System.out.println("" + i + ": " + a[i]);
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public int getQueueSize()
  {
    return queue.size();
  }

  public Queue getQueue()
  {
    return queue;
  }

  public boolean hasWorkerPool()
  {
    synchronized (lock)
    {
      return workerPool != null;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Object lock = new Object();
  private IWorkerPool workerPool = null;

  protected CoalesceQueue queue;
  protected Worker worker;

}
