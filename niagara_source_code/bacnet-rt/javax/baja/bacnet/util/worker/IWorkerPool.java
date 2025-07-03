/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util.worker;

import javax.baja.util.IFuture;

/**
 * IBacnetWorker provides a mechanism inject a customized
 * worker implementation to the BBacnetServerLayer.
 * <p>
 * The first child IBacnetWorker of the BBacnetServerLayer
 * will be used to process Confirmed requests.
 *
 * @author Joseph Chandler
 * @version $Revision$ $Date$
 * @creation 26 Aug 13
 * @since Niagara 3.8 Bacnet 1.0
 */
public interface IWorkerPool
{
  /**
   * The post method is used to dispatch work to the WorkerPool.
   *
   * @param r Runable work to perform
   * @return null, the current implementation does not utilize IFuture.
   */
  public IFuture post(Runnable r);

  /**
   * Check to see if the WorkerPool is still capable of processing messages.
   * Typically this interface will be satisfied by the BComponent.
   * <p>
   * If this message ever returns false, the BBacnetWorker will no longer
   * route messages to the worker pool, until the BBacnetWorker is restarted.
   *
   * @return true if capable of processing a message,
   * false if incapable of further message processing.
   */
  public boolean isRunning();
}
