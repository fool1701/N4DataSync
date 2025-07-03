/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BSimpleJob provides a base class to use for simple 
 * implementations which just launch a new background
 * thread for processing. 
 *
 * @author    Brian Frank       
 * @creation  22 Jul04
 * @version   $Revision: 4$ $Date: 4/12/05 3:40:30 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSimpleJob
  extends BJob
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.job.BSimpleJob(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSimpleJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Job
////////////////////////////////////////////////////////////////
  
  /**
   * Launch a background thread which calls <code>run()</code>.
   */
  public void doRun(Context cx)
  {               
    thread = new JobThread(toPathString(), cx);
    thread.start();
  }
  
  /**
   * Set the state to canceling, and call interrupt on the
   * background thread.  Note that Thread.interrupt usually only
   * works on threads blocked on IO or in a sleep.  Subclasses 
   * should also periodically check isAlive() in case they don't 
   * receive the InteruptedException.
   */
  public void doCancel(Context cx)
  {               
    if (getJobState().isRunning()) 
    {
      setJobState(BJobState.canceling);        
      if (thread != null) thread.interrupt();
    }
  }                    
    
  /**
   * This is the method used to perform the job's work.  It
   * is guaranteed to be called on a background thread.  If
   * an exception is raised then <code>failed()</code> is
   * called automatically.  If the method returns successfully
   * then <code>success</code> is called automatically.
   * Subclasses should also periodically check isAlive() in 
   * case they don't receive the InteruptedException.  
   */
  public abstract void run(Context cx)
    throws Exception;
  
////////////////////////////////////////////////////////////////
// Thread
////////////////////////////////////////////////////////////////


  class JobThread extends Thread
  {              
    JobThread(String name, Context cx) { super(name); this.cx = cx; }

    public void run()
    {
      try
      {
        BSimpleJob.this.run(cx);
        success();
      }
      catch(Throwable e)
      {
        failed(e);
      }
    }
    
    Context cx;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  JobThread thread;

}
