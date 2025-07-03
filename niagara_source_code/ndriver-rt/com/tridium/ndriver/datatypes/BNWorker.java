/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.ndriver.datatypes;

import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BWorker;
import javax.baja.util.Queue;
import javax.baja.util.Worker;

/**
 * This is a default implementation of BWorker that uses an infinite length
 * queue to process items.
 */
@NiagaraType
public class BNWorker
  extends BWorker
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BNWorker(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNWorker.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BWorker
////////////////////////////////////////////////////////////////

  /**
   * Starts the worker thread if parent network or device is startable (not
   * disabled or in fault).
   */
  @Override
  protected void startWorker()
  {
    if (isStartable())
    {
      super.startWorker();
    }
  }

  /**
   * Provides the Worker (which is essentially a Thread wrapper) for this
   * object.
   *
   * @return a direct reference to the 'communicationWorker'
   */
  @Override
  public Worker getWorker()
  {
    return worker;
  }

////////////////////////////////////////////////////////////////
// BNWorker
////////////////////////////////////////////////////////////////

  /**
   * Places the given Runnable on the worker's queue.
   */
  public void post(Runnable r)
  {
    if (!isRunning() || infiniteQueue == null)
    {
      throw new NotRunningException();
    }
    //NCCB-3397. Check for the Worker running state.
    //If the worker is not in running state, check for if BNWorker is startable and then start worker
    if (!getWorker().isRunning())
    {
      if (!isStartable())
      {
        throw new BajaRuntimeException("Worker is not startable.");
      }
      super.startWorker();
    }
    infiniteQueue.enqueue(r);

  }

  /**
   * Is parent device or network startable (not disabled of in fault)
   */
  boolean isStartable()
  {
    // Looks up the ancestry for the first device or network and
    // Keys off of its status
    BComponent parent = (BComponent)getParent();
    while (parent != null)
    {
      if (parent instanceof BDevice)
      {
        return !(((BDevice)parent).isDisabled() || ((BDevice)parent).isFault());
      }
      else if (parent instanceof BDeviceNetwork)
      {
        return !(((BDeviceNetwork)parent).isDisabled() || ((BDeviceNetwork)parent).isFault());
      }
      else
      {
        parent = (BComponent)parent.getParent();
      }
    }

    return true;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // This is the communicationWorker's queue
  Queue infiniteQueue = new Queue();

  // This essentially wraps a Thread and the infiniteQueue
  Worker worker = new Worker(infiniteQueue);
}
