/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.*;
import javax.baja.sys.*;

/**
 * BJob is used to manage tasks which run asynchronously in 
 * the background but require user visibility.  Jobs can be
 * monitored and canceled by users via standard tools in
 * the workbench.  A job lifecycle is composed of:
 *
 * <ol>
 *
 * <li>To kick off a new job, a concrete instance of BJob is passed
 * to JobService.submit() (or you may use BJob.submit()).  This mounts 
 * the job as a dynamic slot under the JobService.</li>
 *
 * <li>The doSubmit() callback is used to initialize the job to begin 
 * its execution.  The job is reset to enter the running state.</li>
 *
 * <li>The doRun() callback is invoked for the job to begin running 
 * in the background. Subclasses should do all their work on another
 * thread - never block the caller's thread.</li>
 *
 * <li>While running it is up to the subclass to provide progress
 * on the job to the user.  If the job can determine a percentage
 * complete, then it should call progress() periodically to update 
 * its percent complete.  If the job cannot determine it's progress 
 * as a percentage, then it should perdiocally call heartbeat() to 
 * let the framework know it is still alive.  The subclass can also 
 * maintain details in its JobLog.</li>
 *
 * <li>The doCancel() callback is invoked if the user manually cancels 
 * the job while it is running.  It is up to the subclass to use
 * that callback to terminate work in the background thread.</li>   
 *
 * <li>When the job completes it is up to the subclass to inform
 * the framework of its completion status via the success(), canceled(),
 * failure(), or completed() method.
 *
 * <li>doDispose() is called when the job is complete, and
 * the user no longer wishes to visualize it.  During this 
 * callback the job is removed from the database.</li>
 *
 * </ol>
 *
 * @author    Brian Frank       
 * @creation  30 Apr 03
 * @version   $Revision: 10$ $Date: 9/23/10 10:32:53 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Stores the current state of this job's lifecycle
 */
@NiagaraProperty(
  name = "jobState",
  type = "BJobState",
  defaultValue = "BJobState.unknown",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 The percentage (0-100) of the task that has been completed.
 If the job is unable to calculate its progress then this
 value should be set to -1.
 */
@NiagaraProperty(
  name = "progress",
  type = "int",
  defaultValue = "-1",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 Time when job was submitted to begin execution.
 */
@NiagaraProperty(
  name = "startTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 This is the last time when heartbeat() was called.  It
 is used to detect that a job is still running even when
 progress is not being computed.
 */
@NiagaraProperty(
  name = "heartbeatTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 Time when job completed.
 */
@NiagaraProperty(
  name = "endTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 Cancel the job.
 */
@NiagaraAction(
  name = "cancel"
)
/*
 Dispose removes the job from the service and cleans up
 any log data.  This action may not be used while the
 job is running, you must explicitly cancel first.
 */
@NiagaraAction(
  name = "dispose"
)
/*
 Read the current contents of the log via JobLog.encode().
 */
@NiagaraAction(
  name = "readLog",
  returnType = "BString"
)
/*
 Read the most recent contents of the log starting at the given
 sequence number (inclusive). If the log has a fixed size and
 has overwritten items, the returned sequence will begin at
 the first item at or greater than the requested number.
 The returned log items will be encoded as a newline delimited
 string using JobLogItem.encode(). The highest and lowest sequence
 numbers of the items can be found as properties on the returned
 BJobLogSequence object. Sequence numbers of -1 and an empty item array
 will be returned if the log is empty, or if an attempt is made to read
 a sequence starting from beyond the end of the log.
 */
@NiagaraAction(
  name = "readLogFrom",
  parameterType = "BLong",
  defaultValue = "BLong.make(0)",
  returnType = "BJobLogSequence",
  flags = Flags.HIDDEN | Flags.NO_AUDIT
)
public abstract class BJob
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.job.BJob(2418362890)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "jobState"

  /**
   * Slot for the {@code jobState} property.
   * Stores the current state of this job's lifecycle
   * @see #getJobState
   * @see #setJobState
   */
  public static final Property jobState = newProperty(Flags.TRANSIENT | Flags.READONLY, BJobState.unknown, null);

  /**
   * Get the {@code jobState} property.
   * Stores the current state of this job's lifecycle
   * @see #jobState
   */
  public BJobState getJobState() { return (BJobState)get(jobState); }

  /**
   * Set the {@code jobState} property.
   * Stores the current state of this job's lifecycle
   * @see #jobState
   */
  public void setJobState(BJobState v) { set(jobState, v, null); }

  //endregion Property "jobState"

  //region Property "progress"

  /**
   * Slot for the {@code progress} property.
   * The percentage (0-100) of the task that has been completed.
   * If the job is unable to calculate its progress then this
   * value should be set to -1.
   * @see #getProgress
   * @see #setProgress
   */
  public static final Property progress = newProperty(Flags.TRANSIENT | Flags.READONLY, -1, null);

  /**
   * Get the {@code progress} property.
   * The percentage (0-100) of the task that has been completed.
   * If the job is unable to calculate its progress then this
   * value should be set to -1.
   * @see #progress
   */
  public int getProgress() { return getInt(progress); }

  /**
   * Set the {@code progress} property.
   * The percentage (0-100) of the task that has been completed.
   * If the job is unable to calculate its progress then this
   * value should be set to -1.
   * @see #progress
   */
  public void setProgress(int v) { setInt(progress, v, null); }

  //endregion Property "progress"

  //region Property "startTime"

  /**
   * Slot for the {@code startTime} property.
   * Time when job was submitted to begin execution.
   * @see #getStartTime
   * @see #setStartTime
   */
  public static final Property startTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code startTime} property.
   * Time when job was submitted to begin execution.
   * @see #startTime
   */
  public BAbsTime getStartTime() { return (BAbsTime)get(startTime); }

  /**
   * Set the {@code startTime} property.
   * Time when job was submitted to begin execution.
   * @see #startTime
   */
  public void setStartTime(BAbsTime v) { set(startTime, v, null); }

  //endregion Property "startTime"

  //region Property "heartbeatTime"

  /**
   * Slot for the {@code heartbeatTime} property.
   * This is the last time when heartbeat() was called.  It
   * is used to detect that a job is still running even when
   * progress is not being computed.
   * @see #getHeartbeatTime
   * @see #setHeartbeatTime
   */
  public static final Property heartbeatTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code heartbeatTime} property.
   * This is the last time when heartbeat() was called.  It
   * is used to detect that a job is still running even when
   * progress is not being computed.
   * @see #heartbeatTime
   */
  public BAbsTime getHeartbeatTime() { return (BAbsTime)get(heartbeatTime); }

  /**
   * Set the {@code heartbeatTime} property.
   * This is the last time when heartbeat() was called.  It
   * is used to detect that a job is still running even when
   * progress is not being computed.
   * @see #heartbeatTime
   */
  public void setHeartbeatTime(BAbsTime v) { set(heartbeatTime, v, null); }

  //endregion Property "heartbeatTime"

  //region Property "endTime"

  /**
   * Slot for the {@code endTime} property.
   * Time when job completed.
   * @see #getEndTime
   * @see #setEndTime
   */
  public static final Property endTime = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code endTime} property.
   * Time when job completed.
   * @see #endTime
   */
  public BAbsTime getEndTime() { return (BAbsTime)get(endTime); }

  /**
   * Set the {@code endTime} property.
   * Time when job completed.
   * @see #endTime
   */
  public void setEndTime(BAbsTime v) { set(endTime, v, null); }

  //endregion Property "endTime"

  //region Action "cancel"

  /**
   * Slot for the {@code cancel} action.
   * Cancel the job.
   * @see #cancel()
   */
  public static final Action cancel = newAction(0, null);

  /**
   * Invoke the {@code cancel} action.
   * Cancel the job.
   * @see #cancel
   */
  public void cancel() { invoke(cancel, null, null); }

  //endregion Action "cancel"

  //region Action "dispose"

  /**
   * Slot for the {@code dispose} action.
   * Dispose removes the job from the service and cleans up
   * any log data.  This action may not be used while the
   * job is running, you must explicitly cancel first.
   * @see #dispose()
   */
  public static final Action dispose = newAction(0, null);

  /**
   * Invoke the {@code dispose} action.
   * Dispose removes the job from the service and cleans up
   * any log data.  This action may not be used while the
   * job is running, you must explicitly cancel first.
   * @see #dispose
   */
  public void dispose() { invoke(dispose, null, null); }

  //endregion Action "dispose"

  //region Action "readLog"

  /**
   * Slot for the {@code readLog} action.
   * Read the current contents of the log via JobLog.encode().
   * @see #readLog()
   */
  public static final Action readLog = newAction(0, null);

  /**
   * Invoke the {@code readLog} action.
   * Read the current contents of the log via JobLog.encode().
   * @see #readLog
   */
  public BString readLog() { return (BString)invoke(readLog, null, null); }

  //endregion Action "readLog"

  //region Action "readLogFrom"

  /**
   * Slot for the {@code readLogFrom} action.
   * Read the most recent contents of the log starting at the given
   * sequence number (inclusive). If the log has a fixed size and
   * has overwritten items, the returned sequence will begin at
   * the first item at or greater than the requested number.
   * The returned log items will be encoded as a newline delimited
   * string using JobLogItem.encode(). The highest and lowest sequence
   * numbers of the items can be found as properties on the returned
   * BJobLogSequence object. Sequence numbers of -1 and an empty item array
   * will be returned if the log is empty, or if an attempt is made to read
   * a sequence starting from beyond the end of the log.
   * @see #readLogFrom(BLong parameter)
   */
  public static final Action readLogFrom = newAction(Flags.HIDDEN | Flags.NO_AUDIT, BLong.make(0), null);

  /**
   * Invoke the {@code readLogFrom} action.
   * Read the most recent contents of the log starting at the given
   * sequence number (inclusive). If the log has a fixed size and
   * has overwritten items, the returned sequence will begin at
   * the first item at or greater than the requested number.
   * The returned log items will be encoded as a newline delimited
   * string using JobLogItem.encode(). The highest and lowest sequence
   * numbers of the items can be found as properties on the returned
   * BJobLogSequence object. Sequence numbers of -1 and an empty item array
   * will be returned if the log is empty, or if an attempt is made to read
   * a sequence starting from beyond the end of the log.
   * @see #readLogFrom
   */
  public BJobLogSequence readLogFrom(BLong parameter) { return (BJobLogSequence)invoke(readLogFrom, parameter, null); }

  //endregion Action "readLogFrom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  protected BJob()
  {
    log.setJob(this);
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>BJobService.getService().submit(this, cx)</code>.
   */
  public BOrd submit(Context cx)
  {                        
    return BJobService.getService().submit(this, cx);    
  }               
  
  /**
   * This callback is invoked to kick off the job run.  The job state 
   * is set to running; startTime is set to the current time; progress 
   * is set to -1; and the log is reset.  Then the <code>doRun()</code> 
   * method is called.
   */
  public void doSubmit(Context cx)
  {
    setJobState(BJobState.running);
    setStartTime(Clock.time());
    setProgress(-1);
    resetLog();
    try
    {
      doRun(cx);
    }
    catch(Throwable e)
    {
      failed(e);
    }
  }

  /**
   * Return if the job's current state is running.
   */
  public boolean isAlive()
  {
    return getJobState() == BJobState.running;
  }
  
  /**
   * This is the callback to begin the job.  All work should be done
   * on a background thread - never block the callers thread.  During
   * the job execution, subclasses should periodically update progress
   * via the <code>progress()</code> or <code>heartbeat()</code> method.  
   * Diagnostics information may be dumped via the log() method.  Once
   * the run finishes, the subclass must invoke one of the completion
   * methods (success, canceled, failed, or complete).
   */
  public abstract void doRun(Context cx)
    throws Exception;
  
  /**
   * This callback is invoked when the user manually cancels a 
   * running job.  It is up to the subclass to terminate the job on 
   * the background thread.  Typically the state should be set to
   * canceling while waiting for the background thread to terminate.
   */  
  public abstract void doCancel(Context cx)
    throws Exception;
  
  /**
   * Dispose is called when a completed job expires or the user
   * manually calls dispose.  The job is removed from the database.
   * It is illegal to call dispose() on a running job.
   */
  public void doDispose(Context cx)
  {      
    if (getJobState().isRunning())
      throw new IllegalStateException("Cannot dispose while running");
    setJobState(BJobState.unknown);
    if(getParent()!=null)
      ((BComponent)getParent()).remove(this);
  }             
  
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    String jobName = getType().getDisplayName(null);
    if (jobName.endsWith(" Job")) 
      jobName = jobName.substring(0, jobName.length()-4);
    return jobName;
  }
  
////////////////////////////////////////////////////////////////
// Progress
////////////////////////////////////////////////////////////////
  
  /**
   * Update the progress property with the percent complete.
   * This method automatically updates the heartbeatTime.
   */
  public void progress(int percent)
  {                       
    setProgress(percent);
    heartbeat();
  }
    
  /**
   * Update the heartbeat time so that we know this job is still 
   * alive and kicking.  Note this method is automatically called
   * by progress() calls.
   */
  public void heartbeat()
  {           
    setHeartbeatTime(Clock.time());
  }       
  
////////////////////////////////////////////////////////////////
// Completion
////////////////////////////////////////////////////////////////
  
  /**
   * This method is called to indicate successful completion
   * of the job.  It sets the job state to success.
   */
  public void success()
  { 
    log().success("Job Success");
    complete(BJobState.success);
  }
  
  /**
   * This method is called to indicate that the job was
   * canceled.  It sets the job state to canceled.
   */
  public void canceled()
  {        
    log().add(new JobLogItem(JobLogItem.CANCELED, "Job Canceled"));
    complete(BJobState.canceled);
  }

  /**
   * This method is called to indicate that the job failed.  If
   * an non-null exception is passed it is automatically dumped
   * to the log.  If the current status is canceling or the exception 
   * is InterruptedException or JobCancelException, then we assume 
   * the failure is actually a cancel.
   */
  public void failed(Throwable cause)
  {                         
    if (getJobState() == BJobState.canceling ||
        cause instanceof InterruptedException || 
        cause instanceof JobCancelException)
    {
      canceled();         
      return;
    }                                        
    
    log().failed("Job Failed", cause);
    complete(BJobState.failed);
  }
  
  /**
   * Call this method when the job completes with a state of 
   * success, canceled, or failed.  It updates the job state, 
   * progress and endTime.
   */
  public void complete(BJobState state)
  {       
    if (!state.isComplete())
      throw new IllegalArgumentException("Cannot complete as " + state);
      
    setJobState(state);
    setProgress(100);
    setEndTime(Clock.time());   
  }

////////////////////////////////////////////////////////////////
// Logging 
////////////////////////////////////////////////////////////////

  /**
   * Get the JobLog to use for generating a list of job items.
   */
  public JobLog log() 
  { 
    return log; 
  }

  /**
   * Return the <code>log().encode()</code> as BString.
   */
  public BString doReadLog() 
  {                       
    return BString.make(log.encode()); 
  }

  /**
   * Return a BJobLogSequence containing the items in the log
   * with sequence numbers greater or equal to the requested
   * number.
   *
   * @since Niagara 4.3
   * @param num the minimum value for the sequence numbers of the returned items.
   * @return a BJobLogSequence with the items encoded as a String.
   */
  public BJobLogSequence doReadLogFrom(BLong num)
  {
    return log.getSequenceFrom(num.getLong());
  }
  
  /**
   * Clear the log of all items.  Note this method is 
   * called automatically by <code>doSubmit()</code>.
   *
   * If the log contained one or more items, the
   * <code>logCleared</code> topic will be fired.
   */
  public void resetLog()
  {
    JobLog old = log;
    old.setJob(null);

    log = new JobLog(old.sequenceNumber);
    log.setJob(this);
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////  

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    
    out.w("<hr><pre>").safe(readLog()).w("</pre>");
  }
    
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("build.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  JobLog log = new JobLog();   
}
