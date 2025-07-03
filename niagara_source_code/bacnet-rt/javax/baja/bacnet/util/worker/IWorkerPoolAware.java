/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util.worker;

import javax.baja.util.Queue;


/**
 * IWorkerAware is an interface
 * to allow services to have child
 * IBacnetWorkers dynamically added / removed.
 * <p>
 * This will prevent users from adding a worker pool
 * that will not be used by the parent class.
 *
 * @author Joseph Chandler
 * @version $Revision$ $Date$
 * @creation 26 Aug 13
 * @since Niagara 3.8 Bacnet 1.0
 */
public interface IWorkerPoolAware
{
  /**
   * Provides a base worker name for the WorkerPool
   * implementation to use when spawning worker threads.
   *
   * @return worker thread name
   */
  public String getWorkerThreadName();

  /**
   * Allows the worker pool to take over processing of messages
   */
  public void stopWorker();

  /**
   * Allow the worker pool to default to using the parent's queue.
   * This should simplify the configuration of some worker pools
   * that only need one incoming queue.
   *
   * @return the queue
   */
  public Queue getQueue();


  /**
   * Provides a mechanism to determine if the IWorkerPoolAware
   * already has a workerPool.
   *
   * @return true, if there is already a worker pool in use
   * false, if a worker pool has not been assigned.
   */
  public boolean hasWorkerPool();

}
