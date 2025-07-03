/*
 * Copyright 2019 Tridium, Inc.  All rights reserved.
 *
 */

package javax.baja.driver.util;

import javax.baja.control.trigger.BDailyTriggerMode;
import javax.baja.control.trigger.BTimeTrigger;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BLink;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Localizable;
import javax.baja.sys.LocalizableException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;

import com.tridium.sys.schema.Fw;
import com.tridium.util.ThrowableUtil;

/**
 * This abstract base class configures a trigger and provides execute action whose implementation
 * is to be handled by the subclasses. It also provides configuration for scheduling a regular task.
 *
 * The subclass needs to implement doExecute() and postExecute() methods. Subclasses are responsible
 * for calling the following lifecycle methods:
 * <ul>
 *   <li>executeInProgress() - must call when doExecute() is invoked
 *   <li>executeFail()
 *   <li>executeOk()
 *  <br>
 * </ul>
 * @author    Ashutosh Chaturvedi on 5/8/2019
 * @since     Niagara 4.9
 *
 */
@NiagaraType
/*
 The status of this descriptor.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 The current state of the work that is performed by this descriptor.
 */
@NiagaraProperty(
  name = "state",
  type = "BDescriptorState",
  defaultValue = "BDescriptorState.idle",
  flags = Flags.READONLY
)
/*
 The enabled state of this descriptor.  If false, the
 descriptor status will include "disabled".  If true,
 the disabled status is controlled by the parent.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 The execution time controls when the execute action will be invoked.
 */
@NiagaraProperty(
  name = "executionTime",
  type = "BTimeTrigger",
  defaultValue = "new BTimeTrigger(BDailyTriggerMode.make())"
)
/*
 The last time that the descriptor's work was started.
 */
@NiagaraProperty(
  name = "lastAttempt",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 The last time that the descriptor's work completed successfully.
 */
@NiagaraProperty(
  name = "lastSuccess",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 The last time that the descriptor's work failed to complete successfully.
 */
@NiagaraProperty(
  name = "lastFailure",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY
)
/*
 This is a text message describing the reason for the fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT,
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE")
)
/*
 Execute the work that the descriptor is responsible for.
 */
@NiagaraAction(
  name = "execute",
  flags = Flags.ASYNC
)
public abstract class BAbstractDescriptor
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BAbstractDescriptor(958613007)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The status of this descriptor.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * The status of this descriptor.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The status of this descriptor.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "state"

  /**
   * Slot for the {@code state} property.
   * The current state of the work that is performed by this descriptor.
   * @see #getState
   * @see #setState
   */
  public static final Property state = newProperty(Flags.READONLY, BDescriptorState.idle, null);

  /**
   * Get the {@code state} property.
   * The current state of the work that is performed by this descriptor.
   * @see #state
   */
  public BDescriptorState getState() { return (BDescriptorState)get(state); }

  /**
   * Set the {@code state} property.
   * The current state of the work that is performed by this descriptor.
   * @see #state
   */
  public void setState(BDescriptorState v) { set(state, v, null); }

  //endregion Property "state"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * The enabled state of this descriptor.  If false, the
   * descriptor status will include "disabled".  If true,
   * the disabled status is controlled by the parent.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * The enabled state of this descriptor.  If false, the
   * descriptor status will include "disabled".  If true,
   * the disabled status is controlled by the parent.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * The enabled state of this descriptor.  If false, the
   * descriptor status will include "disabled".  If true,
   * the disabled status is controlled by the parent.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "executionTime"

  /**
   * Slot for the {@code executionTime} property.
   * The execution time controls when the execute action will be invoked.
   * @see #getExecutionTime
   * @see #setExecutionTime
   */
  public static final Property executionTime = newProperty(0, new BTimeTrigger(BDailyTriggerMode.make()), null);

  /**
   * Get the {@code executionTime} property.
   * The execution time controls when the execute action will be invoked.
   * @see #executionTime
   */
  public BTimeTrigger getExecutionTime() { return (BTimeTrigger)get(executionTime); }

  /**
   * Set the {@code executionTime} property.
   * The execution time controls when the execute action will be invoked.
   * @see #executionTime
   */
  public void setExecutionTime(BTimeTrigger v) { set(executionTime, v, null); }

  //endregion Property "executionTime"

  //region Property "lastAttempt"

  /**
   * Slot for the {@code lastAttempt} property.
   * The last time that the descriptor's work was started.
   * @see #getLastAttempt
   * @see #setLastAttempt
   */
  public static final Property lastAttempt = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code lastAttempt} property.
   * The last time that the descriptor's work was started.
   * @see #lastAttempt
   */
  public BAbsTime getLastAttempt() { return (BAbsTime)get(lastAttempt); }

  /**
   * Set the {@code lastAttempt} property.
   * The last time that the descriptor's work was started.
   * @see #lastAttempt
   */
  public void setLastAttempt(BAbsTime v) { set(lastAttempt, v, null); }

  //endregion Property "lastAttempt"

  //region Property "lastSuccess"

  /**
   * Slot for the {@code lastSuccess} property.
   * The last time that the descriptor's work completed successfully.
   * @see #getLastSuccess
   * @see #setLastSuccess
   */
  public static final Property lastSuccess = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code lastSuccess} property.
   * The last time that the descriptor's work completed successfully.
   * @see #lastSuccess
   */
  public BAbsTime getLastSuccess() { return (BAbsTime)get(lastSuccess); }

  /**
   * Set the {@code lastSuccess} property.
   * The last time that the descriptor's work completed successfully.
   * @see #lastSuccess
   */
  public void setLastSuccess(BAbsTime v) { set(lastSuccess, v, null); }

  //endregion Property "lastSuccess"

  //region Property "lastFailure"

  /**
   * Slot for the {@code lastFailure} property.
   * The last time that the descriptor's work failed to complete successfully.
   * @see #getLastFailure
   * @see #setLastFailure
   */
  public static final Property lastFailure = newProperty(Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code lastFailure} property.
   * The last time that the descriptor's work failed to complete successfully.
   * @see #lastFailure
   */
  public BAbsTime getLastFailure() { return (BAbsTime)get(lastFailure); }

  /**
   * Set the {@code lastFailure} property.
   * The last time that the descriptor's work failed to complete successfully.
   * @see #lastFailure
   */
  public void setLastFailure(BAbsTime v) { set(lastFailure, v, null); }

  //endregion Property "lastFailure"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * This is a text message describing the reason for the fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code faultCause} property.
   * This is a text message describing the reason for the fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * This is a text message describing the reason for the fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Action "execute"

  /**
   * Slot for the {@code execute} action.
   * Execute the work that the descriptor is responsible for.
   * @see #execute()
   */
  public static final Action execute = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code execute} action.
   * Execute the work that the descriptor is responsible for.
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

  //endregion Action "execute"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// State changes
////////////////////////////////////////////////////////////////

  /**
   * Called to indicate that the execute action completed
   * successfully.  This sets lastSuccess, clears the fault
   * cause, and sets the state to idle. This method also
   * updates the status by calling {@code updateStatus()}
   */
  public void executeOk()
  {
    setFaultCause("");
    setLastSuccess(Clock.time());
    setState(BDescriptorState.idle);
    updateStatus();
  }

  /**
   * See setExecuteFail(String)
   */
  public void executeFail()
  {
    executeFail("");
  }

  /**
   * Called to indicate that the execute action failed
   * to complete.  This sets the last failure time and message,
   * sets the fault cause, and sets the state
   * back to idle. This method also
   * updates the status by calling {@code updateStatus()}
   */
  public void executeFail(String reason)
  {
    if (reason == null)
      reason = "";
    setLastFailure(Clock.time());
    setFaultCause(reason);
    setState(BDescriptorState.idle);
    updateStatus();
  }

  /**
   * See setExecuteFail(String)
   */
  public void executeFail(Throwable ex)
  {
    executeFail(getFailureReason(ex));
  }

  /**
   * Convenience method to return a String representation of the given failure, optionally allowing
   * for newline characters in the result to be removed.
   * @since Niagara 4.12
   */
  private static String getFailureReason(Throwable ex)
  {
    String reason;
    if (ex == null)
    {
      reason = "";
    }
    else if (ex instanceof LocalizableException || ex instanceof LocalizableRuntimeException)
    {
      reason = ((Localizable) ex).toString(null);
    }
    else
    {
      reason = ThrowableUtil.dumpToString(ex, 1);
    }
    if (reason == null)
    {
      reason = ex.toString();
    }
    reason = reason.replace("\n", "");
    return reason;
  }

  /**
   * Called to indicate that the execute action is in progress.
   */
  public void executeInProgress()
  {
    setState(BDescriptorState.inProgress);
  }

////////////////////////////////////////////////////////////////
// Async
////////////////////////////////////////////////////////////////
  /**
   * Handle an invocation of the execute action with null context.
   */
  public abstract void doExecute();

/////////////////////////////////////////////////////////////////
// Status
/////////////////////////////////////////////////////////////////

  /**
   * If true, execute should be short circuited.
   * @return True if down, disabled
   */
  public boolean isUnoperational()
  {
    return isDisabled() || isDown();
  }

  /**
   * Return true if the status is down.
   */
  public boolean isDown()
  {
    return getStatus().isDown();
  }

  /**
   * Return true if in fault.
   */
  public boolean isFault()
  {
    return getStatus().isFault();
  }

  /**
   * Return true if disabled.
   * Is disabled if the user has manually
   * set the enabled property to false.
   */
  public boolean isDisabled()
  {
    return getStatus().isDisabled();
  }

  /**
   * Convenience method to update the status of the instance.
   */
  public abstract void updateStatus();

////////////////////////////////////////////////////////////////
// Async
////////////////////////////////////////////////////////////////

  /**
   * This post method includes special handling for the execute action.
   * A post of the execute action results in a call to
   * postExecute(Action, BValue, Context).  If this method is overridden
   * super.post(Action, BValue, Context) must be called.
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(execute))
    {
      // short circuit disabled
      if (isUnoperational())
      {
        return null;
      }
      // short circuit if already pending or in progress
      if (getState() != BDescriptorState.idle)
      {
        return null;
      }

      setLastAttempt(Clock.time());
      setState(BDescriptorState.pending);
      return postExecute(action, arg, cx);
    }
    else
    {
      return super.post(action, arg, cx);
    }
  }

  /**
   * This post method includes special handling for the execute action.
   *
   * @return always return null
   */
  protected abstract IFuture postExecute(Action action, BValue arg, Context cx);

////////////////////////////////////////////////////////////////
// Framework Implementation
////////////////////////////////////////////////////////////////

  /**
   * For internal framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.STARTED: fwStarted(); break;
      case Fw.STOPPED: fwStopped(); break;
      case Fw.CHANGED: fwChanged((Property)a); break;
      case Fw.STATION_STARTED: fwStationStarted(); break;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    updateStatus();
    add("triggerLink?",
      new BLink(getExecutionTime().getOrdInSession(),"fireTrigger","execute",true),
      Flags.HIDDEN | Flags.TRANSIENT);
  }

  private void fwStopped()
  {
    Property triggerLink = getProperty("triggerLink");
    if (triggerLink != null)
    {
      remove(triggerLink);
    }
  }

  private void fwChanged(Property prop)
  {
    if (!isRunning())
    {
      return;
    }
    if (prop.equals(enabled))
    {
      updateStatus();
    }
    else if (prop.equals(executionTime))
    { // Must re-add the fireTrigger link
      add("triggerLink?",
        new BLink(getExecutionTime().getOrdInSession(),"fireTrigger","execute",true),
        Flags.HIDDEN | Flags.TRANSIENT);
    }
  }

  private void fwStationStarted()
  {
    // If the state isn't idle, then this descriptor was interrupted
    // in the middle of working.  In that case, immediately start
    // again.
    if (getState() != BDescriptorState.idle)
    {
      setState(BDescriptorState.idle);
      execute();
    }
  }
}
