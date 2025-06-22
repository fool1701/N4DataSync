/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;    

import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFloat;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;

import javax.baja.nre.util.TextUtil;

/**
 * Worker is used to asynchronously perform "work" on a 
 * background thread.  The "work" is Runnables returned
 * by the ITodo interface.  The common case is to use a
 * Queue as the ITodo.
 *
 * @author    Brian Frank
 * @creation  7 Feb 04
 * @version   $Revision: 4$ $Date: 8/30/07 8:52:49 AM EDT$
 * @since     Baja 1.0
 */
public class Worker
{     

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a Worker.
   */
  public Worker(ITodo todo)
  {                      
    this.todo = todo;
  }                   
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the ITodo object which provides the work.
   */
  public ITodo getTodo()
  {
    return todo;
  }                  
  
  /**
   * Get the timeout used for fectching work from the ITodo.
   */
  public int getTimeout()
  {
    return timeout;
  }

  /**
   * Set the timeout used for fectching work from the ITodo.
   */
  public void setTimeout(int timeout)
  {            
    this.timeout = timeout;
  }
  
  /**
   * Get string representation.
   */
  public String toString()
  {
    return TextUtil.getClassName(getClass()) + " [" + (isAlive ? "Running" : "Stopped") + "]";
  }

////////////////////////////////////////////////////////////////
// Thread Management
////////////////////////////////////////////////////////////////  

  /**
   * Is the worker currently running.
   */
  public boolean isRunning()
  {
    return thread != null && isAlive;
  }
  
  /**
   * Convenience for <code>start(Thread.currentThread().getThreadGroup(), threadName)</code>.
   */
  public final void start(String threadName)
  {                         
    start(Thread.currentThread().getThreadGroup(), threadName);
  }

  /**
   * Start the worker on a thread and loop forever processing
   * its todo entries until the <code>stop()</code> method is called.
   */
  public void start(ThreadGroup threadGroup, String threadName)
  {
    if (isAlive) return;
    
    this.isAlive = true;  
    this.startTime  = Clock.millis();           
    this.startTicks = Clock.ticks();           
    this.numProcessed = 0;
    this.thread = new Thread(threadGroup, new Processor(), threadName);
    this.thread.setDaemon(true);
    this.thread.start();
  }
  
  /**
   * Stop the worker thread from running.
   */
  public void stop()
  {
    isAlive = false;
    if (thread != null) thread.interrupt();
  }              
  
////////////////////////////////////////////////////////////////
// Processor
////////////////////////////////////////////////////////////////

  class Processor implements Runnable
  {
    @Override
    public void run()
    {
      isAlive = true;
      while(isAlive)
      {
        try
        { 
          // wait for work      
          Runnable work = todo.todo(timeout);
          
          // process
          long t1 = Clock.ticks();
          process(work);
          long t2 = Clock.ticks();
          
          // update statistics
          processingTicks += (t2 - t1);
          numProcessed++;
        }
        catch(InterruptedException e)
        {
          // silent
        }
        catch(Throwable e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  /**
   * This method is called on the worker's thread when a 
   * new item of work is available.  If the todo timed out
   * then null is passed.
   */
  protected void process(Runnable work)
    throws Exception
  {
    if (work != null) work.run();
  }                
  
////////////////////////////////////////////////////////////////
// ITodo
////////////////////////////////////////////////////////////////
  
  /**
   * ITodo is used to provide work as Runnables to a Worker.
   */
  public interface ITodo
  {           
    /**
     * Get the next item of work as a Runnable.  This method should 
     * block until new work is ready or until the timeout has expired.   
     * If the timeout expires then return null.  If the calling thread 
     * is interrupted, the method should propogate the 
     * InterruptedException.
     */                               
    public Runnable todo(int timeout)
      throws InterruptedException;
  }              
  
////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////
  
  /**
   * Dump debug information.
   */
  public void spy(SpyWriter out)
    throws Exception
  {                         
    out.startProps("Worker");
    spyImpl(out);
    out.endProps();
  }              
  
  void spyImpl(SpyWriter out)
  {
    out.prop("isAlive",      ""+isAlive); 
    
    if (isAlive)
     spy(out, startTime, startTicks, processingTicks, numProcessed);
        
    out.prop("todo", todo.getClass().getName()); 
    if (todo instanceof Queue)
    {                             
      Queue q = (Queue)todo;
      out.prop("queue.size",    ""+q.size()); 
      out.prop("queue.maxSize", ""+q.maxSize()); 
    }                                            
    if (todo instanceof CoalesceQueue)
    {                             
      CoalesceQueue q = (CoalesceQueue)todo;
      out.prop("queue.hashTable", ""+q.table.length); 
      out.prop("queue.hashSize",  ""+q.hashSize); 
      out.prop("queue.threshold", ""+q.threshold); 
    }
  }
  
  static void spy(SpyWriter out, long startTime, long startTicks, long processingTicks, int numProcessed)
  {                      
    long uptime = Clock.ticks() - startTicks;
    float percent = (float)processingTicks/(float)uptime * 100f;
    float avg = (float)processingTicks/(float)numProcessed;
    
    out.prop("startTime",    BAbsTime.make(startTime)); 
    out.prop("upTime",       BRelTime.toString(uptime)); 
    out.prop("processing",   BRelTime.toString(processingTicks)); 
    out.prop("numProcessed", ""+numProcessed); 
    out.prop("average",      BFloat.toString(avg, null)+"ms/work"); 
    out.prop("utilization",  ""+(int)percent+"%"); 
  }
     
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  boolean isAlive;
  Thread thread;   
  ITodo todo;        
  int timeout;         
  int numProcessed;                                                       
  long startTime;
  long startTicks;
  long processingTicks;
} 

