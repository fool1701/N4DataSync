package javax.baja.util;

import com.tridium.nre.util.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Java concurrency {@link java.util.concurrent.Executor} utilities.
 * <p>
 * Java already comes with a useful set of utility methods in {@link java.util.concurrent.Executors}.
 * This class provides some additional utility methods that are missing from the standard Java library.
 * </p>
 *
 * @see java.util.concurrent.Executor
 * @see java.util.concurrent.ExecutorService
 * @see java.util.concurrent.ScheduledExecutorService
 *
 * @author Gareth Johnson on 08/09/2015.
 * @since Niagara 4.1
 */
public final class ExecutorUtil
{
  private ExecutorUtil() {}

  /**
   * Shutdown an Executor Service.
   * <p>
   * This method attempts to shutdown, wait and then forcibly shutdown an
   * Executor Service. For more information, see the
   * <a href="http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html">
   * Executor Service class comments</a>.
   *
   * @param service The Executor Service to shutdown.
   */
  public static void shutdown(ExecutorService service)
  {
    // Disable new tasks from being submitted
    service.shutdown();
    try
    {
      // Wait a while for existing tasks to terminate
      if (!service.awaitTermination(10, TimeUnit.SECONDS))
      {
        // Cancel currently executing tasks
        service.shutdownNow();

        // Wait a while for tasks to respond to being cancelled
        service.awaitTermination(10, TimeUnit.SECONDS);
      }
    }
    catch (InterruptedException e)
    {
      // (Re-)Cancel if current thread also interrupted
      service.shutdownNow();

      // Preserve interrupt status
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Return a fixed single threaded Executor Service. The underlying thread will shut itself
   * down after a period inactivity. If a task is submitted to the Executor Service
   * after the thread and been shutdown, the thread will be created again.
   * <p>
   * The created Executor Service is useful for services that require a background thread
   * that not used too often.
   * </p>
   * <p>
   * It's important to note that since the underlying thread may be shutdown and then created
   * again, the thread's id may differ over time.
   * </p>
   *
   * @see #newSingleThreadBackgroundScheduledExecutor(String, long, TimeUnit)
   *
   * @param threadName The name of the thread.
   * @param keepAliveTime The amount of time to keep the thread alive before it's shutdown.
   * @param keepAliveUnits The units for the keep alive time.
   * @return A new Executor Service.
   */
  public static ExecutorService newSingleThreadBackgroundExecutor(String threadName,
                                                                  long keepAliveTime,
                                                                  TimeUnit keepAliveUnits)
  {
    return new ThreadPoolExecutor(
      0,   // corePoolSize
      1,   // maximumPoolSize
      keepAliveTime,
      keepAliveUnits,
      new LinkedBlockingQueue<>(),
      new NamedThreadFactory(threadName));
  }

  /**
   * Return a fixed single threaded Scheduled Executor Service. The underlying thread
   * will shut itself down after a period inactivity. If a task is submitted to the
   * Executor Service after the thread and been shutdown, the thread will be created again.
   * <p>
   * The created Scheduled Executor Service is useful for services that require a background thread
   * that's not used too often.
   * </p>
   * <p>
   * It's important to note that since the underlying thread may be shutdown and then created
   * again, the thread's id may differ over time.
   * </p>
   *
   * @see #newSingleThreadBackgroundExecutor(String, long, TimeUnit)
   *
   * @param threadName The name of the thread.
   * @param keepAliveTime The amount of time to keep the thread alive before it's shutdown.
   * @param keepAliveUnits The units for the keep alive time.
   * @return A new Scheduled Executor Service.
   */
  public static ScheduledExecutorService newSingleThreadBackgroundScheduledExecutor(String threadName,
                                                                                    long keepAliveTime,
                                                                                    TimeUnit keepAliveUnits)
  {
    return newSingleThreadBackgroundScheduledExecutor(threadName, keepAliveTime, keepAliveUnits, false);
  }

  /**
   * Return a fixed single threaded Scheduled Executor Service. The underlying thread
   * will shut itself down after a period inactivity. If a task is submitted to the
   * Executor Service after the thread has been shutdown, the thread will be created again.
   * <p>
   * The created Scheduled Executor Service is useful for services that require a background thread
   * that's not used too often.
   * </p>
   * <p>
   * It's important to note that since the underlying thread may be shutdown and then created
   * again, the thread's id may differ over time.
   * </p>
   *
   * @see #newSingleThreadBackgroundExecutor(String, long, TimeUnit)
   *
   * @param threadName The name of the thread.
   * @param keepAliveTime The amount of time to keep the thread alive before it's shutdown.
   * @param keepAliveUnits The units for the keep alive time.
   * @param isDaemon specifies the value for the call to {@link Thread#setDaemon(boolean)} on each
   *                 thread created by the returned Executor.
   * @return A new Scheduled Executor Service.
   *
   * @since Niagara 4.13
   */
  public static ScheduledExecutorService newSingleThreadBackgroundScheduledExecutor(String threadName,
                                                                                    long keepAliveTime,
                                                                                    TimeUnit keepAliveUnits,
                                                                                    boolean isDaemon)
  {
    ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(
      0,   // corePoolSize
      new NamedThreadFactory(threadName, isDaemon));
    
    service.setKeepAliveTime(keepAliveTime, keepAliveUnits);

    return service;
  }
}
