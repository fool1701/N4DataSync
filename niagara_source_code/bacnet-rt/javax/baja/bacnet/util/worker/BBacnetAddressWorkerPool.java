/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util.worker;

import java.util.ArrayList;
import java.util.List;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.CoalesceQueue;
import javax.baja.util.IFuture;
import javax.baja.util.Queue;
import javax.baja.util.ThreadPoolWorker;

/**
 * BBacnetAddressWorkerPool balances incoming work across a
 * pool of thread pools using the BBacnetAddress hashcode() method.
 * <p>
 * Spreading the load by address will decrease the impact
 * to the system as a whole from one device.
 * <p>
 * This will not be an even split, and can be less than optimal depending
 * on the device addresses in a particular installation.
 * It is possible that all messages could end up getting routed
 * to one worker pool.
 *
 * @author Joseph Chandler
 * @version $Revision$ $Date$
 * @creation 26 Aug 2013
 * @since Niagara 3.8 Bacnet 1.0
 */
@NiagaraType
/*
 The number of address pools should be based on the
 number of bacnet devices and the desired loading factor.
 */
@NiagaraProperty(
  name = "addressPools",
  type = "int",
  defaultValue = "DEFAULT_POOLS"
)
/*
 The number of workers per address pool
 */
@NiagaraProperty(
  name = "workersPerAddressPool",
  type = "int",
  defaultValue = "DEFAULT_WORKERS_PER_POOL"
)
public class BBacnetAddressWorkerPool
  extends BComponent
  implements IWorkerPool
{
  private static final int DEFAULT_POOLS = 4;
  private static final int DEFAULT_WORKERS_PER_POOL = 2;
  
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.util.worker.BBacnetAddressWorkerPool(1190652642)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "addressPools"

  /**
   * Slot for the {@code addressPools} property.
   * The number of address pools should be based on the
   * number of bacnet devices and the desired loading factor.
   * @see #getAddressPools
   * @see #setAddressPools
   */
  public static final Property addressPools = newProperty(0, DEFAULT_POOLS, null);

  /**
   * Get the {@code addressPools} property.
   * The number of address pools should be based on the
   * number of bacnet devices and the desired loading factor.
   * @see #addressPools
   */
  public int getAddressPools() { return getInt(addressPools); }

  /**
   * Set the {@code addressPools} property.
   * The number of address pools should be based on the
   * number of bacnet devices and the desired loading factor.
   * @see #addressPools
   */
  public void setAddressPools(int v) { setInt(addressPools, v, null); }

  //endregion Property "addressPools"

  //region Property "workersPerAddressPool"

  /**
   * Slot for the {@code workersPerAddressPool} property.
   * The number of workers per address pool
   * @see #getWorkersPerAddressPool
   * @see #setWorkersPerAddressPool
   */
  public static final Property workersPerAddressPool = newProperty(0, DEFAULT_WORKERS_PER_POOL, null);

  /**
   * Get the {@code workersPerAddressPool} property.
   * The number of workers per address pool
   * @see #workersPerAddressPool
   */
  public int getWorkersPerAddressPool() { return getInt(workersPerAddressPool); }

  /**
   * Set the {@code workersPerAddressPool} property.
   * The number of workers per address pool
   * @see #workersPerAddressPool
   */
  public void setWorkersPerAddressPool(int v) { setInt(workersPerAddressPool, v, null); }

  //endregion Property "workersPerAddressPool"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAddressWorkerPool.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetAddressWorkerPool()
  {

  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public boolean isParentLegal(BComponent parent)
  {
    return BBacnetWorkerPool.isLegalParent(parent);
  }

  public void started()
  {
    startPools();
  }

  public void stopped()
  {
    stopPools();
  }

  public void changed(Property p, Context cx)
  {
    if (!isRunning())
      return;

    if (p.equals(addressPools) || p.equals(workersPerAddressPool))
    {
      restartPools();
    }
  }

  private void restartPools()
  {
    synchronized (lock)
    {
      stopPools();
      startPools();
    }
  }

  private void startPools()
  {
    synchronized (lock)
    {
      int numberOfPools = getAddressPools();
      addressWorkers = new ArrayList<>(numberOfPools);
      IWorkerPoolAware parent = (IWorkerPoolAware)getParent();
      int maxQueueSize = parent.getQueue().maxSize();
      String threadName = parent.getWorkerThreadName();

      for (int i = 0; i < numberOfPools; i++)
      {
        Queue queue = new CoalesceQueue(maxQueueSize);
        ThreadPoolWorker worker = new ThreadPoolWorker(queue);
        worker.setMaxThreads(getWorkersPerAddressPool());
        worker.start(threadName + i);
        addressWorkers.add(new AddressWorker(worker, queue));
      }
      parent.stopWorker();
    }
  }

  private void stopPools()
  {
    synchronized (lock)
    {
      if (addressWorkers != null)
      {
        int numberOfPools = addressWorkers.size();
        for (int i = 0; i < numberOfPools; i++)
        {
          AddressWorker aw = addressWorkers.get(i);
          aw.stop();
        }
        addressWorkers.clear();
      }
    }
  }

  public IFuture post(Runnable r)
  {
    if (!isRunning())
      throw new NotRunningException();

    synchronized (lock)
    {
      if (r instanceof IBacnetAddress)
      {
        //Try to keep work from the same device on the same workers.
        IBacnetAddress request = (IBacnetAddress)r;
        int workerIdx = request.getAddress().hash() % addressWorkers.size();
        AddressWorker aw = addressWorkers.get(workerIdx);
        aw.enqueue(r);

      }
      else
      {
        if (addressWorkers.size() > 0)
        {
          AddressWorker defaultWorker = addressWorkers.get(0);
          defaultWorker.enqueue(r);
        }
      }
    }
    return null;
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("gears.png");

////////////////////////////////////////////////////////////////
//Attributes
////////////////////////////////////////////////////////////////

  private List<AddressWorker> addressWorkers;
  private Object lock = new Object();

  private static class AddressWorker
  {
    public AddressWorker(ThreadPoolWorker worker, Queue queue)
    {
      this.worker = worker;
      this.queue = queue;
    }

    public void stop()
    {
      if (worker != null)
      {
        worker.stop();
      }
    }

    public void enqueue(Runnable r)
    {
      queue.enqueue(r);
    }

    private ThreadPoolWorker worker;
    private Queue queue;
  }

}
