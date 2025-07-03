/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.testng;

import java.util.concurrent.ForkJoinPool;
import javax.baja.log.Log;
import com.tridium.nre.util.NreForkJoinWorkerThreadFactory;

/**
 * Class definition.
 *
 * @author Dan Heine
 * @creation 2013-09-13
 * @since Niagara 4.0
 */
public class TestThreadPool implements Thread.UncaughtExceptionHandler
{
////////////////////////////////////////////////////////////////
// Thread pool
////////////////////////////////////////////////////////////////

  ForkJoinPool executor;
  MonitorWorker monitorWorker;

  public TestThreadPool(String name, int threads)
  {
    // Create the ForkJoinPool
    startThreadPool(threads);
  }

  /**
   * Uncaught exception handler for thread pool
   *
   * @param t
   * @param e
   */
  @Override
  public void uncaughtException(Thread t, Throwable e)
  {
    Log.getLog("search.thread.monitor").error("Uncaught exception from thread " + t, e);
  }


  protected void startThreadPool(int threads)
  {
    // Create the ForkJoinPool
    executor = new ForkJoinPool(threads,
                                NreForkJoinWorkerThreadFactory.DEFAULT_INSTANCE,
                                /* uncaughtExceptionHandler = */ this,
                                /* asyncMode = */ true);

    // Start the monitoring thread
    int monitorIntervalSec = 2;
    monitorWorker = new MonitorWorker(executor, monitorIntervalSec);
    new Thread(monitorWorker).start();

  }

  public ForkJoinPool executor()
  {
    return this.executor;
  }

  public void execute(Runnable runnable)
  {
    executor.execute(runnable);
  }

  public void shutdown()
  {

    // Shut down the pool
    executor.shutdownNow();

    try
    {
      // Let pool stop
      Thread.sleep(2000);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    // Shut down the monitor thread
    monitorWorker.shutdown();

  }

////////////////////////////////////////////////////////////////
// Thread pool support classes
////////////////////////////////////////////////////////////////

  /**
   * Monitor the thread pool
   */
  protected static class MonitorWorker
    implements Runnable
  {
    private ForkJoinPool executor;
    private int monitorIntervalSec;
    private boolean run = true;

    public MonitorWorker(ForkJoinPool executor, int monitorIntervalSec)
    {
      this.executor = executor;
      this.monitorIntervalSec = monitorIntervalSec;
    }

    public void shutdown()
    {
      run = false;
    }

    @Override
    public void run()
    {
      while (run)
      {
        Log.getLog("test.thread.monitor").trace(
          String.format("[%d/%d] Active: %d, Running: %d, Submitted: %d, Queued: %d, Steals: %d",
                        executor.getPoolSize(),
                        executor.getParallelism(),
                        executor.getActiveThreadCount(),
                        executor.getRunningThreadCount(),
                        executor.getQueuedSubmissionCount(),
                        executor.getQueuedTaskCount(),
                        executor.getStealCount()));
        try
        {
          Thread.sleep(monitorIntervalSec * 1000);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }
  }

}
