/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver.points;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * The Write Worker is a simple way to post actions asynchronously.
 * This is used by the Proxy Extension write method to perform the
 * write request without tying up the calling thread.
 *
 * @author J. Spangler
 */
@NiagaraType
public class BWriteWorker
  extends BWorker
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.points.BWriteWorker(2979906276)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWriteWorker.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Worker getWorker()
  {
    // if we do not already have a worker instance, create one
    if (null == worker)
    {
      // init our queue with max size of 1000 Runnable tasks. This
      // queue will be used by the Worker to hold all tasks to be
      // processed.
      queue = new CoalesceQueue(1000);
      worker = new Worker(queue);
    }

    return worker;
  }

  /**
   * This method is used to post work to our Worker thread.
   * The task must be a {@link Runnable}.
   *
   * @param r - {@link Runnable} to execute on background thread.
   */
  public void postWork(Runnable r)
  {
    // check to see that our BComponent is mounted in a station and
    // that the queue is non null. If either of these conditions are
    // not meant, throw an exception
    if (null == queue || !isRunning())
    {
      throw new NotRunningException();
    }

    // Enqueue our Runnable task on our Worker queue
    queue.enqueue(r);
  }

//////////////////////////////////////////////////////////////// 
//  Attributes 
//////////////////////////////////////////////////////////////// 

  private CoalesceQueue queue;
  private Worker worker;
}
