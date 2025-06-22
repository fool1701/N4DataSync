/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Clock;

/**
 * ThreadPoolWorker is a Worker which processes its work
 * concurrently using a pool of threads.
 * 
 * Starting in Niagara 3.3, the behavior was enhanced to
 * remove idle worker threads from the pool (those that 
 * have surpassed 10 seconds since last used).  Of course, 
 * they will get recreated on demand as needed.  The idea
 * is that idle worker threads should linger in memory for 
 * a short time (10 seconds) and then be automatically 
 * cleaned up if still inactive.
 *
 * @author    John Sublett
 * @creation  27 Jan 2004
 * @version   $Revision: 13$ $Date: 1/9/09 4:22:35 PM EST$
 * @since     Baja 1.0
 */
public class ThreadPoolWorker
  extends Worker
{
                                      
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct a ThreadPoolWorker to process work from todo.
   */
  public ThreadPoolWorker(ITodo todo)
  {                                 
    super(todo);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the max number of concurrent threads to use for this worker.
   */
  public int getMaxThreads()
  {
    return maxThreads;
  }

  /**
   * Set the max number of concurrent threads to use for this worker.
   */
  public void setMaxThreads(int maxThreads)
  {                          
    if (maxThreads < 1) throw new IllegalArgumentException("" + maxThreads + " < 1");
    this.maxThreads = maxThreads;
  }
  
////////////////////////////////////////////////////////////////
// Worker
////////////////////////////////////////////////////////////////
  
  /**
   * Start the worker on a thread and loop forever processing
   * its todo entries until the <code>stop()</code> method is called.
   */
  @Override
  public void start(ThreadGroup threadGroup, String threadName)
  {
    if (isAlive) return;
    pool.start();
    super.start(threadGroup, threadName);
  }
  
  /**
   * Stop the worker threads from running.
   */
  @Override
  public void stop()
  {                
    isAlive = false;
    pool.stop();
    super.stop();
  }  
  
  /**
   * Dispatch the work to one of the threads in the thread
   * pool, or if we are at maxThreads then block until a thread
   * becomes available.
   */
  @Override
  protected void process(Runnable work)
    throws Exception
  {          
    pool.run(work);
  }

  /** Override point, called by a pool thread when it is created. */
  protected void threadStarted() {}

  /** Override point, called by a pool thread when it is destroyed. */
  protected void threadStopped() {}
  
  /** 
   * Remove the given WorkerThread from the thread pool
   */
  void removeThread(WorkerThread t)
  {
    // make sure this thread is removed from the pool
    try
    {
      synchronized(pool.threads) { pool.threads.remove(t); }
    }
    catch(Exception npe) {} // suppress nullPointerExceptions (stopping case)
    expiredWorkers++;
  }

////////////////////////////////////////////////////////////////
// ThreadPool
////////////////////////////////////////////////////////////////
  
  /**
   * ThreadPool manages the list of WorkerThreads.
   */  
  class ThreadPool 
  { 
    void start()
    {                  
      // alloc new list
      if (threads != null) throw new IllegalStateException();   
      threads = new Array<>(WorkerThread.class);
    }   
    
    void stop()
    {                                         
      if (threads == null) return;
      
      // stop all worker threads and free list
      WorkerThread[] myThreads = threads.trim();
      if (myThreads != null)
      {
        for(int i=0; i<myThreads.length; ++i)
          try { myThreads[i].interrupt(); } catch(Exception e) {}
      }
      threads = null;
    }                        
           
    void run(Runnable work)
      throws InterruptedException
    {    
      while(isAlive)
      {
        synchronized(threads)
        {
          // check if maxThreads has been reduced
          while(threads.size() > maxThreads)
          {
            WorkerThread t = threads.remove(threads.size()-1);
            t.reduce();
          }
                     
          // find free one
          for(int i=0; i<threads.size(); ++i)
          {      
            WorkerThread t = threads.get(i);
            if (!t.isBusy()) 
            {           
              t.run(work); 
              return; 
            }
          }           
        
          // if we are below maxThreads spawn a new one
          if (threads.size() < maxThreads)
          {              
            ThreadGroup group = thread.getThreadGroup();
            String name = thread.getName() + "-" + threads.size();
            WorkerThread t = new WorkerThread(group, name);
            threads.add(t); 
            t.start();
            t.run(work);      
            return;
          }
        }
        
        // sleep until a thread becomes available
        Thread.sleep(250);
      }
    }
            
    Array<WorkerThread> threads;
  }

////////////////////////////////////////////////////////////////
// WorkerThread
////////////////////////////////////////////////////////////////

  /**
   * A WorkerThread processes one Runnable at a time.  
   * Between* work it waits for new work to be assigned.
   */
  class WorkerThread
    extends Thread
  {
    WorkerThread(ThreadGroup group, String name)
    {
      super(group, name);       
      startTime = Clock.millis();
      startTicks = Clock.ticks();
      lastJobTicks = startTicks;
    }
    
    void run(Runnable work)
    {                  
      synchronized(lock)
      {
        if (this.work != null) throw new IllegalStateException();
        this.work = work;
        lock.notifyAll();
      }
    }

    boolean isBusy()
    {
      return (work != null) || reduced;
    }

    void reduce()
    {
      synchronized(lock)
      {
        System.out.println("ThreadPoolWorker reduce: " + this);
        this.reduced = true;
        lock.notifyAll();
      }
    }

    boolean isActive()
    {
      return isAlive && !reduced;
    }

    @Override
    @SuppressWarnings("squid:S2142")
    public void run()
    {
      try
      {
        threadStarted();
        while (isActive())
        {                 
          // wait for work to arrive
          while (isActive() && (work == null))
          {
            synchronized(lock)
            {
              try
              {
                lock.wait(IDLE_THREAD_EXPIRATION);
              }
              catch(InterruptedException ignore) {}
              
              if ((IDLE_THREAD_EXPIRATION > 0) &&
                  (Clock.ticks() - lastJobTicks > IDLE_THREAD_EXPIRATION && 
                  work == null))
              {
                // indicates we went the full 10 seconds without work,
                // so cleanup this thread
                reduced = true;                
              }
            }
          }

          // check if still alive
          if (!isActive()) break;

          // run the work
          try
          {         
            long t1 = Clock.ticks();

            work.run();

            long t2 = Clock.ticks();
            
            lastJobTicks = t2;
            numProcessed++;
            processingTicks += (t2-t1);
          }
          catch(Throwable e)
          {    
            e.printStackTrace();
          }

          // no longer busy
          synchronized(lock)
          {
            work = null;
          }
        }    
      }
      finally
      {
        removeThread(WorkerThread.this);
        threadStopped();
      }
    }   
        
    volatile Runnable work;
    volatile boolean reduced;
    long startTime;                                                     
    long startTicks;
    int numProcessed;  
    long processingTicks;
    long lastJobTicks;
    final Object lock = new Object();
    
  }//class WorkerThread

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////
  
  @Override
  void spyImpl(SpyWriter out)
  {                
    super.spyImpl(out);         
    out.prop("maxThreads", ""+maxThreads);

    Array<WorkerThread> threads = pool.threads;
    out.prop("currentThreadCount", (threads != null)?""+threads.size():"null");
    out.prop("busyThreadCount", ""+getBusyThreadCount());
    out.prop("expiredThreadCount", ""+expiredWorkers);
    out.prop("spyMaxThreadCount", String.valueOf(SPY_MAX_THREAD_COUNT));
    
    if (threads != null) 
    {
      for(int i=0; i<threads.size(); ++i)
      {
        if (i > SPY_MAX_THREAD_COUNT)
          break;
        
        WorkerThread t = threads.get(i);
        out.trTitle("Worker[" + i + "] " + t.getName(), 2);
        out.prop("work", String.valueOf(t.work));
        spy(out, t.startTime, t.startTicks, t.processingTicks, t.numProcessed);
      }
    }
  }       
  
  /**
   * Returns the current number of worker threads in the pool
   * that are busy doing work (not including active threads 
   * in an "idle" state).
   * 
   * @since Niagara 3.4
   */
  private int getBusyThreadCount()
  {
    Array<WorkerThread> pThreads = pool.threads;
    if (pThreads == null) return 0;
    WorkerThread[] t = pThreads.array();
    
    int size = t.length;
    int count = 0;    
    for(int i=0; i<size; ++i)
    {      
      try
      {
        if (t[i].work != null) count++;
      }
      catch (Exception e) {}
    }
    return count;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  // The default time (in milliseconds) that an idle worker thread 
  // (in a thread pool) will linger waiting for more work before it
  // expires.  A value of zero means that worker threads will never expire.
  static final long IDLE_THREAD_EXPIRATION = AccessController.doPrivileged((PrivilegedAction<Long>)
    () -> Long.getLong("niagara.threadPoolWorker.idleExpiration", 10000L).longValue());
  
  // A limit to the number of threads the Spy will print out.
  private static final long SPY_MAX_THREAD_COUNT =
    AccessController.doPrivileged((PrivilegedAction<Long>)
      () -> Long.getLong("niagara.threadPoolWorker.spyMaxThreadCount", 1000L).longValue());
  
  int maxThreads = 4;
  final ThreadPool pool = new ThreadPool();
  volatile int expiredWorkers = 0; 
}
