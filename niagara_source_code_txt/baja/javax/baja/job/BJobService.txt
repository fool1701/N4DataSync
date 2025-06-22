/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.BNotification;

import com.tridium.nre.util.NreForkJoinWorkerThreadFactory;
import com.tridium.sys.service.ServiceManager;
import com.tridium.sys.station.BStationSaveJob;

/**
 * BJobService is used to manage all the BJobs in a station VM.
 * Refer to BJob class header for details.
 *
 * @author    Brian Frank       
 * @creation  30 Apr 03
 * @version   $Revision: 7$ $Date: 12/15/06 3:49:33 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "submitAction",
  parameterType = "BJob",
  defaultValue = "new BStationSaveJob()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@NiagaraTopic(
  name = "notification",
  eventType = "BNotification",
  flags = Flags.HIDDEN
)
public class BJobService
  extends BComponent
  implements BIService, BIJobService, BIRestrictedComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.job.BJobService(2527441570)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "submitAction"

  /**
   * Slot for the {@code submitAction} action.
   * @see #submitAction(BJob parameter)
   */
  public static final Action submitAction = newAction(Flags.HIDDEN, new BStationSaveJob(), null);

  /**
   * Invoke the {@code submitAction} action.
   * @see #submitAction
   */
  public BOrd submitAction(BJob parameter) { return (BOrd)invoke(submitAction, parameter, null); }

  //endregion Action "submitAction"

  //region Topic "notification"

  /**
   * Slot for the {@code notification} topic.
   * @see #fireNotification
   */
  public static final Topic notification = newTopic(Flags.HIDDEN, null);

  /**
   * Fire an event for the {@code notification} topic.
   * @see #notification
   */
  public void fireNotification(BNotification event) { fire(notification, event, null); }

  //endregion Topic "notification"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJobService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected ForkJoinPool executor;
  protected MonitorWorker monitorWorker;
  protected UncaughtJobExceptionHandler exceptionHandler = new UncaughtJobExceptionHandler();
  protected final static long DEFAULT_THREAD_MONITOR_INTERVAL_MS = 2000;
  private static Logger logger = Logger.getLogger("job.thread.monitor");

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the JobService or throw ServiceNotFoundException.
   */
  public static BIJobService getService()
  {                                                             
    try
    {
      return (BIJobService)Sys.getService(TYPE);
    }
    catch (ServiceNotFoundException e)
    {
      try
      {
        BOrd ord = BOrd.make("tool:workbench:WbJobService|slot:/");
        return (BIJobService)ord.resolve().get();
      }
      catch (Exception err)
      {
        throw new ServiceNotFoundException("IJobService", err);
      }
    }
  }

////////////////////////////////////////////////////////////////
// IJobService
////////////////////////////////////////////////////////////////
  
  /**
   * Get all the child jobs under this service.
   */
  @Override
  public final BJob[] getJobs()
  {                                                    
    return getChildren(BJob.class);
  }

  /**
   * Submit a job and run it!
   * 
   * @return {@link BOrd} is the ORD to the BJob that is created
   */
  @Override
  public BOrd submit(BJob job, Context cx)
  {                  
    return (BOrd)invoke(submitAction, job, cx);                    
  }               
  
  /**
   * Submit action implementation, do not use directly.
   */                                                  
  public BOrd doSubmitAction(BJob job, Context cx)
  {
    return AccessController.doPrivileged(new PrivilegedAction<BOrd>() {
      @Override
      public BOrd run()
      {
        add(job.getType().getTypeName()+'?', job, Flags.TRANSIENT);
        job.doSubmit(cx);
        ServiceManager.houseKeeping(BJobService.this);
        return job.getSlotPathOrd();
      }
    });
  }
                                         
////////////////////////////////////////////////////////////////
// IService
////////////////////////////////////////////////////////////////  

  @Override
  public Type[] getServiceTypes()
  { 
    return new Type[] { TYPE }; 
  }

  @Override
  public void serviceStarted()
  {
    // Default number of threads is twice the number of CPUs in order to handle the blocking I/O
    // paradigm in Niagara.  If jobs used only asynchronous, non-blocking I/O,
    // the number of threads could be reduced back to the number of CPUs to reduce stack
    // requirements.
    int threadsPerCPU = Integer.parseInt(AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.job.threadsPerCPU", String.valueOf(2))));
    int defaultThreads = Runtime.getRuntime().availableProcessors() * threadsPerCPU;

    // Allow total override in system.properties for some installations if needed
    int threads = Integer.parseInt(AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.job.threads", String.valueOf(defaultThreads))));

    startThreadPool(threads);
  }

  @Override
  public void serviceStopped()
  {
    stopThreadPool();
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("jobService.png");

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////  

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    BJob[] jobs = getJobs();
    out.startTable(true);
    out.trTitle("Jobs", 6);
    out.w("<tr>").th("Name").th("State").th("Progress")
       .th("Start").th("Heartbeat").th("End").w("</tr>").nl();
    for (BJob j : jobs)
    {
      out.tr(j.getName(), j.getJobState(), String.valueOf(j.getProgress()),
        j.getStartTime(), j.getHeartbeatTime(), j.getEndTime());
    }
    out.endTable();

    if (getExecutor() != null)
    {
      out.w("<p>");
      out.startTable(true);
      out.trTitle("Thread Pool", 7);
      out.w("<tr>").th("Current Pool Size").th("Max Pool Size").th("Active").th("Running")
         .th("Submitted").th("Queued").th("Steals")
         .w("</tr>").nl();
      out.tr(getExecutor().getPoolSize(),
             getExecutor().getParallelism(),
             getExecutor().getActiveThreadCount(),
             getExecutor().getRunningThreadCount(),
             getExecutor().getQueuedSubmissionCount(),
             getExecutor().getQueuedTaskCount(),
             getExecutor().getStealCount());
      out.endTable();
    }

    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Thread pool
////////////////////////////////////////////////////////////////

  /**
   * Get the search executor.
   */
  public ForkJoinPool getExecutor()
  {
    return executor;
  }

  /**
   * Handle ForkJoinPool uncaught exceptions
   */
  protected static class UncaughtJobExceptionHandler implements Thread.UncaughtExceptionHandler
  {

    /**
     * Uncaught exception handler for thread pool
     *
     * @param t thread
     * @param e Throwable
     */
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
      if (logger.isLoggable(Level.SEVERE))
      {
        logger.log(Level.SEVERE, "Uncaught exception from thread " + t +"\n" + e);
      }
    }
  }

  protected void startThreadPool(int threads)
  {
    // Create the ForkJoinPool
    executor = new ForkJoinPool(threads,
                                NreForkJoinWorkerThreadFactory.DEFAULT_INSTANCE,
                                /* uncaughtExceptionHandler = */ exceptionHandler,
                                /* asyncMode = */ true);

    // Start the monitoring thread
    long monitorIntervalMs = Long.parseLong(AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.job.thread.monitor.intervalMs",
      String.valueOf(DEFAULT_THREAD_MONITOR_INTERVAL_MS))));
    monitorWorker = new MonitorWorker(executor, monitorIntervalMs);
    new Thread(monitorWorker, "JobService:MonitorWorker").start();
  }

  /**
   * Shut down the thread pool and monitor.
   */
  protected void stopThreadPool()
  {
    if (executor == null)
    {
      return;
    }

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
// Thread pool support class
////////////////////////////////////////////////////////////////

  /**
   * Monitor the thread pool
   */
  protected static class MonitorWorker
    implements Runnable
  {
    private ForkJoinPool executor;
    private long monitorIntervalMs;
    private boolean run = true;

    public MonitorWorker(ForkJoinPool executor, long monitorIntervalMs)
    {
      this.executor = executor;
      this.monitorIntervalMs = monitorIntervalMs;
    }

    public void shutdown()
    {
      run = false;
    }

    @Override
    public void run()
    {
      if (monitorIntervalMs > 0)
      {
        while (run)
        {
          if (logger.isLoggable(Level.FINE))
          {
            String traceMessage =
              String.format("[%d/%d] Active: %d, Running: %d, Submitted: %d, Queued: %d, Steals: %d",
                executor.getPoolSize(),
                executor.getParallelism(),
                executor.getActiveThreadCount(),
                executor.getRunningThreadCount(),
                executor.getQueuedSubmissionCount(),
                executor.getQueuedTaskCount(),
                executor.getStealCount());
            logger.log(Level.FINE, traceMessage);
          }

          try
          {
            Thread.sleep(monitorIntervalMs);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
        }
      }
    }
  }

}
