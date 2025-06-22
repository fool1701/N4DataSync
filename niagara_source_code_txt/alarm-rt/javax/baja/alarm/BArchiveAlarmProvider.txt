/*
 * Copyright 2020 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.collection.BITable;
import javax.baja.control.trigger.BDailyTriggerMode;
import javax.baja.control.trigger.BIntervalTriggerMode;
import javax.baja.control.trigger.BTimeTrigger;
import javax.baja.license.Feature;
import javax.baja.license.FeatureNotLicensedException;
import javax.baja.naming.BOrd;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
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
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Flags;
import javax.baja.sys.IllegalChildException;
import javax.baja.sys.IllegalParentException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BExecutionState;
import javax.baja.util.BFormat;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.BUuid;
import javax.baja.util.IFuture;
import javax.baja.util.Lexicon;

import com.tridium.alarm.fox.BAlarmArchiveChannel;
import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.registry.NAgentList;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.station.Station;

/**
 * BArchiveAlarmProvider is added to the BAlarmService and provides mechanism to
 * archive cleared alarms. It allows the main alarm database to store only open
 * alarms (that are displayed in the Alarm Consoles). Once cleared (transitioned
 * to normal or acknowledged), alarms are archived to a secondary database for
 * historical storage.
 *
 * Cleared alarms are periodically archived via the execute action which is
 * invoked by the executionTime trigger.
 *
 * Subclasses will need to supply a BAlarmArchive and ensure that postExecute
 * places the archive work on a non-control engine thread.
 *
 * The subclass needs to implement {@code exportClearedRecords(Cursor<BAlarmRecord> recordsToExport)}
 * and {@code postExecute()} methods.
 *
 * @author Ashutosh Chaturvedi on 7/13/2020
 * @since Niagara 4.11
 */
@NiagaraType
/*
 The status of this alarm archival provider.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 The enabled state of this alarm archive provider.  If false, this alarm
 archive provider won't be used to store cleared alarms.  If true,
 this alarm archive provider will be used to archive alarms once they
 are cleared.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "false"
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
 The current state of the work that is performed by this descriptor.
 */
@NiagaraProperty(
  name = "state",
  type = "BExecutionState",
  defaultValue = "BExecutionState.idle",
  flags = Flags.READONLY | Flags.TRANSIENT
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
 The minimum time that an alarm should remain in the cleared state before
 it can be archived.
 */
@NiagaraProperty(
  name = "clearedAlarmLingerTime",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0))")
)
/*
 The last time that the alarm archival's work was started.
 */
@NiagaraProperty(
  name = "lastAttempt",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 The last time that the alarm archival's work completed successfully.
 */
@NiagaraProperty(
  name = "lastSuccess",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 The last time that the alarm archival's work failed to complete successfully.
 */
@NiagaraProperty(
  name = "lastFailure",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 The retryTrigger controls when retries of failed alarm archival
 executions will be reattempted.
 */
@NiagaraProperty(
  name = "retryTrigger",
  type = "BTimeTrigger",
  defaultValue = "new BTimeTrigger(BIntervalTriggerMode.make(BRelTime.makeMinutes(15)))"
)
/*
 Determines whether an alarm should be generated when a failure occurs for
 an execution of this export.
 */
@NiagaraProperty(
  name = "alarmOnFailure",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "alarmSourceInfo",
  type = "BAlarmSourceInfo",
  defaultValue = "initAlarmSourceInfo()"
)
/*
 Execute the work that the alarm archival is responsible for.
 */
@NiagaraAction(
  name = "execute",
  flags = Flags.ASYNC
)
/*
 The action invoked by the retryTrigger to retry any failed alarm archival
 executions.
 */
@NiagaraAction(
  name = "retry",
  flags = Flags.SUMMARY
)
/*
 Hidden action to support alarm acknowledgements
 */
@NiagaraAction(
  name = "ackAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  returnType = "BBoolean",
  flags = Flags.HIDDEN
)
public abstract class BArchiveAlarmProvider
extends BComponent
  implements BIRestrictedComponent, BIStatus, BIAlarmSource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BArchiveAlarmProvider(3263970196)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The status of this alarm archival provider.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * The status of this alarm archival provider.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The status of this alarm archival provider.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * The enabled state of this alarm archive provider.  If false, this alarm
   * archive provider won't be used to store cleared alarms.  If true,
   * this alarm archive provider will be used to archive alarms once they
   * are cleared.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, false, null);

  /**
   * Get the {@code enabled} property.
   * The enabled state of this alarm archive provider.  If false, this alarm
   * archive provider won't be used to store cleared alarms.  If true,
   * this alarm archive provider will be used to archive alarms once they
   * are cleared.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * The enabled state of this alarm archive provider.  If false, this alarm
   * archive provider won't be used to store cleared alarms.  If true,
   * this alarm archive provider will be used to archive alarms once they
   * are cleared.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

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

  //region Property "state"

  /**
   * Slot for the {@code state} property.
   * The current state of the work that is performed by this descriptor.
   * @see #getState
   * @see #setState
   */
  public static final Property state = newProperty(Flags.READONLY | Flags.TRANSIENT, BExecutionState.idle, null);

  /**
   * Get the {@code state} property.
   * The current state of the work that is performed by this descriptor.
   * @see #state
   */
  public BExecutionState getState() { return (BExecutionState)get(state); }

  /**
   * Set the {@code state} property.
   * The current state of the work that is performed by this descriptor.
   * @see #state
   */
  public void setState(BExecutionState v) { set(state, v, null); }

  //endregion Property "state"

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

  //region Property "clearedAlarmLingerTime"

  /**
   * Slot for the {@code clearedAlarmLingerTime} property.
   * The minimum time that an alarm should remain in the cleared state before
   * it can be archived.
   * @see #getClearedAlarmLingerTime
   * @see #setClearedAlarmLingerTime
   */
  public static final Property clearedAlarmLingerTime = newProperty(0, BRelTime.DEFAULT, BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0)));

  /**
   * Get the {@code clearedAlarmLingerTime} property.
   * The minimum time that an alarm should remain in the cleared state before
   * it can be archived.
   * @see #clearedAlarmLingerTime
   */
  public BRelTime getClearedAlarmLingerTime() { return (BRelTime)get(clearedAlarmLingerTime); }

  /**
   * Set the {@code clearedAlarmLingerTime} property.
   * The minimum time that an alarm should remain in the cleared state before
   * it can be archived.
   * @see #clearedAlarmLingerTime
   */
  public void setClearedAlarmLingerTime(BRelTime v) { set(clearedAlarmLingerTime, v, null); }

  //endregion Property "clearedAlarmLingerTime"

  //region Property "lastAttempt"

  /**
   * Slot for the {@code lastAttempt} property.
   * The last time that the alarm archival's work was started.
   * @see #getLastAttempt
   * @see #setLastAttempt
   */
  public static final Property lastAttempt = newProperty(Flags.READONLY | Flags.TRANSIENT, BAbsTime.NULL, null);

  /**
   * Get the {@code lastAttempt} property.
   * The last time that the alarm archival's work was started.
   * @see #lastAttempt
   */
  public BAbsTime getLastAttempt() { return (BAbsTime)get(lastAttempt); }

  /**
   * Set the {@code lastAttempt} property.
   * The last time that the alarm archival's work was started.
   * @see #lastAttempt
   */
  public void setLastAttempt(BAbsTime v) { set(lastAttempt, v, null); }

  //endregion Property "lastAttempt"

  //region Property "lastSuccess"

  /**
   * Slot for the {@code lastSuccess} property.
   * The last time that the alarm archival's work completed successfully.
   * @see #getLastSuccess
   * @see #setLastSuccess
   */
  public static final Property lastSuccess = newProperty(Flags.READONLY | Flags.TRANSIENT, BAbsTime.NULL, null);

  /**
   * Get the {@code lastSuccess} property.
   * The last time that the alarm archival's work completed successfully.
   * @see #lastSuccess
   */
  public BAbsTime getLastSuccess() { return (BAbsTime)get(lastSuccess); }

  /**
   * Set the {@code lastSuccess} property.
   * The last time that the alarm archival's work completed successfully.
   * @see #lastSuccess
   */
  public void setLastSuccess(BAbsTime v) { set(lastSuccess, v, null); }

  //endregion Property "lastSuccess"

  //region Property "lastFailure"

  /**
   * Slot for the {@code lastFailure} property.
   * The last time that the alarm archival's work failed to complete successfully.
   * @see #getLastFailure
   * @see #setLastFailure
   */
  public static final Property lastFailure = newProperty(Flags.READONLY | Flags.TRANSIENT, BAbsTime.NULL, null);

  /**
   * Get the {@code lastFailure} property.
   * The last time that the alarm archival's work failed to complete successfully.
   * @see #lastFailure
   */
  public BAbsTime getLastFailure() { return (BAbsTime)get(lastFailure); }

  /**
   * Set the {@code lastFailure} property.
   * The last time that the alarm archival's work failed to complete successfully.
   * @see #lastFailure
   */
  public void setLastFailure(BAbsTime v) { set(lastFailure, v, null); }

  //endregion Property "lastFailure"

  //region Property "retryTrigger"

  /**
   * Slot for the {@code retryTrigger} property.
   * The retryTrigger controls when retries of failed alarm archival
   * executions will be reattempted.
   * @see #getRetryTrigger
   * @see #setRetryTrigger
   */
  public static final Property retryTrigger = newProperty(0, new BTimeTrigger(BIntervalTriggerMode.make(BRelTime.makeMinutes(15))), null);

  /**
   * Get the {@code retryTrigger} property.
   * The retryTrigger controls when retries of failed alarm archival
   * executions will be reattempted.
   * @see #retryTrigger
   */
  public BTimeTrigger getRetryTrigger() { return (BTimeTrigger)get(retryTrigger); }

  /**
   * Set the {@code retryTrigger} property.
   * The retryTrigger controls when retries of failed alarm archival
   * executions will be reattempted.
   * @see #retryTrigger
   */
  public void setRetryTrigger(BTimeTrigger v) { set(retryTrigger, v, null); }

  //endregion Property "retryTrigger"

  //region Property "alarmOnFailure"

  /**
   * Slot for the {@code alarmOnFailure} property.
   * Determines whether an alarm should be generated when a failure occurs for
   * an execution of this export.
   * @see #getAlarmOnFailure
   * @see #setAlarmOnFailure
   */
  public static final Property alarmOnFailure = newProperty(0, true, null);

  /**
   * Get the {@code alarmOnFailure} property.
   * Determines whether an alarm should be generated when a failure occurs for
   * an execution of this export.
   * @see #alarmOnFailure
   */
  public boolean getAlarmOnFailure() { return getBoolean(alarmOnFailure); }

  /**
   * Set the {@code alarmOnFailure} property.
   * Determines whether an alarm should be generated when a failure occurs for
   * an execution of this export.
   * @see #alarmOnFailure
   */
  public void setAlarmOnFailure(boolean v) { setBoolean(alarmOnFailure, v, null); }

  //endregion Property "alarmOnFailure"

  //region Property "alarmSourceInfo"

  /**
   * Slot for the {@code alarmSourceInfo} property.
   * @see #getAlarmSourceInfo
   * @see #setAlarmSourceInfo
   */
  public static final Property alarmSourceInfo = newProperty(0, initAlarmSourceInfo(), null);

  /**
   * Get the {@code alarmSourceInfo} property.
   * @see #alarmSourceInfo
   */
  public BAlarmSourceInfo getAlarmSourceInfo() { return (BAlarmSourceInfo)get(alarmSourceInfo); }

  /**
   * Set the {@code alarmSourceInfo} property.
   * @see #alarmSourceInfo
   */
  public void setAlarmSourceInfo(BAlarmSourceInfo v) { set(alarmSourceInfo, v, null); }

  //endregion Property "alarmSourceInfo"

  //region Action "execute"

  /**
   * Slot for the {@code execute} action.
   * Execute the work that the alarm archival is responsible for.
   * @see #execute()
   */
  public static final Action execute = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code execute} action.
   * Execute the work that the alarm archival is responsible for.
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

  //endregion Action "execute"

  //region Action "retry"

  /**
   * Slot for the {@code retry} action.
   * The action invoked by the retryTrigger to retry any failed alarm archival
   * executions.
   * @see #retry()
   */
  public static final Action retry = newAction(Flags.SUMMARY, null);

  /**
   * Invoke the {@code retry} action.
   * The action invoked by the retryTrigger to retry any failed alarm archival
   * executions.
   * @see #retry
   */
  public void retry() { invoke(retry, null, null); }

  //endregion Action "retry"

  //region Action "ackAlarm"

  /**
   * Slot for the {@code ackAlarm} action.
   * Hidden action to support alarm acknowledgements
   * @see #ackAlarm(BAlarmRecord parameter)
   */
  public static final Action ackAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code ackAlarm} action.
   * Hidden action to support alarm acknowledgements
   * @see #ackAlarm
   */
  public BBoolean ackAlarm(BAlarmRecord parameter) { return (BBoolean)invoke(ackAlarm, parameter, null); }

  //endregion Action "ackAlarm"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BArchiveAlarmProvider.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Abstract Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the alarm archive configured.
   */
  public abstract BAlarmArchive getAlarmArchive();

  /**
   * Get the status of the alarm archive configured.
   */
  public abstract BStatus getArchiveStatus();

  /**
   * Handle an invocation of the execute action with a context.
   * It checks for the provider's status and resolves the query ord to get the
   * alarm records to export.
   * This will only export the alarm records eligible for archival since
   * the lastUpdate time minus the clearedAlarmLingerTime.
   * {@code exportClearedRecords(Cursor<BAlarmRecord> recordsToExport)} is called with the
   * query result which returns the list of the BUuid of the alarm records exported.
   * The list is used to remove the exported records from the alarm db space post archival.
   */
  public final void doExecute(Context cx)
  {
    if (!canExecuteRequest())
    {
      if (!getState().equals(BExecutionState.idle))
      {
        setState(BExecutionState.idle);
      }
      return;
    }

    try
    {
      if (isProviderReady())
      {
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.fine("begin export on thread " + Thread.currentThread().getName());
        }
        long t0 = Clock.ticks();

        setState(BExecutionState.inProgress);

        BAlarmService alarmService = (BAlarmService) Sys.getService(BAlarmService.TYPE);
        // export the records
        List<BUuid> clearedUuids;
        BAbsTime exportQueryStartTime = BAbsTime.now().subtract(getClearedAlarmLingerTime());
        BOrd query = BOrd.make("alarm:|bql:select * where " +
          "(sourceState = 'normal' or sourceState = 'alert') " +
          "and ackState = 'acked'" +
          "and lastUpdate <= AbsTime '"+ exportQueryStartTime.encodeToString() + '\'');

        try(Cursor<BAlarmRecord> result = ((BITable<BAlarmRecord>)query.resolve(alarmService).get()).cursor())
        {
          clearedUuids = exportClearedRecords(result);
        }
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.fine("exported " + clearedUuids.size() + " alarm records");
        }

        int clearCount = 0;
        try (AlarmDbConnection alarmDbConn = alarmService.getAlarmDb().getDbConnection(null))
        {
          for (BUuid uuid : clearedUuids)
          {
            alarmDbConn.clearRecord(uuid, null);
            clearCount++;
          }
        }
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.fine("cleared " + clearCount + " local alarm records");
        }
        long ms = Clock.ticks() - t0;
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.fine("end export (" + ms + "ms)");
        }
        executeOk();
      }
    }
    catch(Exception e)
    {
      executeFail(e.getLocalizedMessage());
    }
    finally
    {
      setState(BExecutionState.idle);
    }
  }

  /**
   * Check the status of the provider's configuration before running execute method.
   * @return True if the provider is ready to run execute method. False otherwise.
   */
  public abstract boolean isProviderReady();

  /**
   * Called as part of the execute method to export the cleared alarm/s from
   * the openAlarms space to the alarm archive.
   * @see #execute
   * @param recordsToExport The Cursor reference containing alarm records
   *                        eligible for archival.
   * @return List of alarm record BUuid successfully exported. The records will
   * be removed from the local alarm database.
   */
  public abstract List<BUuid> exportClearedRecords(Cursor<BAlarmRecord> recordsToExport);
////////////////////////////////////////////////////////////////
// State changes
////////////////////////////////////////////////////////////////

  /**
   * Called to indicate that the execute action completed
   * successfully. This sets lastSuccess and clears the fault
   * cause. This method also updates the status by calling {@code updateStatus()}
   */
  public void executeOk()
  {
    setFaultCause("");
    setLastSuccess(Clock.time());
    setState(BExecutionState.idle);

    if (getStatus().isAlarm())
    {
      processAlarmNormal(alarmSupport);

      // clear the alarm flag
      setStatus(BStatus.make(getStatus(), BStatus.ALARM, false));
    }

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
   * to complete.  This sets the last failure time and message
   * and sets the fault cause. This method also
   * updates the status by calling {@code updateStatus()}
   * @param reason A localized string to set as the fault cause.
   */
  public void executeFail(String reason)
  {
    if (reason == null)
    {
      reason = "";
    }
    setLastFailure(Clock.time());
    setFaultCause(reason);
    setState(BExecutionState.idle);

    // if already in alarm then skip
    if (getAlarmOnFailure() && !getStatus().isAlarm())
    {
      // send offNormal transition to alarm service
      boolean ackRequired = processAlarmOffnormal(alarmSupport, getAlarmSourceInfo(), reason);

      // set the alarm bit and maybe the unacked bit
      int newStatus = getStatus().getBits();
      newStatus |= BStatus.ALARM;
      if (ackRequired)
      {
        newStatus |= BStatus.UNACKED_ALARM;
      }
      setStatus(BStatus.make(newStatus));
    }

    updateStatus();
  }

  /**
   * Called to indicate that the execute action is in progress.
   */
  public void executeInProgress()
  {
    setState(BExecutionState.inProgress);
  }

  /**
   * Convenience method to process an alarm toNormal transition using the provided AlarmSupport
   * instance.
   */
  void processAlarmNormal(AlarmSupport support)
  {
    try
    {
      support.toNormal(null);
    }
    catch (Throwable e)
    {
      if (LOG.isLoggable(Level.FINE))
      {
        LOG.log(Level.WARNING, "Cannot process normal alarm:" + e.getLocalizedMessage(), e);
      }
      else
      {
        LOG.warning("Cannot process normal alarm:" + e.getLocalizedMessage());
      }
    }
  }

  /**
   * Convenience method to process an alarm offNormal transition using the provided AlarmSupport
   * instance.
   */
  boolean processAlarmOffnormal(AlarmSupport support, BAlarmSourceInfo info, String reason)
  {
    // send offNormal transition to alarm service
    boolean ackRequired = false;
    try
    {
      ackRequired = support.isAckRequired(BSourceState.offnormal);
      BFacets alarmData = info.makeAlarmData(BSourceState.offnormal);
      String msgText = alarmData.gets(BAlarmRecord.MSG_TEXT, "");
      alarmData = BFacets.make(alarmData, BAlarmRecord.MSG_TEXT, BString.make(msgText+'\n'+reason));
      support.newOffnormalAlarm(alarmData);
    }
    catch (Throwable e)
    {
      if (LOG.isLoggable(Level.FINE))
      {
        LOG.log(Level.WARNING, "Cannot process off normal alarm: " + e.getLocalizedMessage(), e);
      }
      else
      {
        LOG.warning("Cannot process off normal alarm: " + e.getLocalizedMessage());
      }
    }

    return ackRequired;
  }

/////////////////////////////////////////////////////////////////
// Status
/////////////////////////////////////////////////////////////////

  private void checkLicense()
  {
    try
    {
      Feature alarmArchiveFeature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "alarmArchive");
      if (alarmArchiveFeature == null)
      {
        throw new FeatureNotLicensedException(UNLICENSED);
      }
      alarmArchiveFeature.check();
    }
    catch (Exception e)
    {
      configFatal(UNLICENSED);
    }
  }

  /**
   * If false, execute should be short circuited.
   *
   * @return false if disabled, or fault
   */
  public final boolean isOperational()
  {
    return !isDisabled() && !isFault();
  }

  /**
   * Return true if the status is down.
   */
  public final boolean isDown()
  {
    return getStatus().isDown();
  }

  /**
   * Return true if in fault.
   */
  public final boolean isFault()
  {
    return getStatus().isFault();
  }

  /**
   * Return true if disabled.
   * Is disabled if the user has manually
   * set the enabled property to false.
   */
  public final boolean isDisabled()
  {
    return getStatus().isDisabled();
  }

  /**
   * Returns true if we can execute a request to the remote archive alarm system.
   */
  protected final boolean canExecuteRequest()
  {
    return !(isFatalFault() || isDisabled() || isDown());
  }

  /**
   * Convenience method to update the status of the instance.
   */
  public final void updateStatus()
  {
    //if we're already in fatalFault, fail fast
    if (fatalFault)
    {
      return;
    }

    BStatus archiveStatus = getArchiveStatus();
    //if getArchiveStatus sets fatalFault, fail fast
    if (fatalFault)
    {
      return;
    }

    int newStatus = getStatus().getBits();

    // disabled bit
    if (!getEnabled() || archiveStatus.isDisabled())
    {
      //if the provider is disabled, ignore stale, fault and down on the archive or rdb
      newStatus |= BStatus.DISABLED;
      newStatus &= ~BStatus.FAULT;
      newStatus &= ~BStatus.DOWN;
      newStatus &= ~BStatus.STALE;
      setStatus(BStatus.make(newStatus));
      if (archiveStatus.isDisabled())
      {
        setFaultCause(LEX.getText("alarmArchive.archiveStatus.disabled"));
      }
      else
      {
        setFaultCause("");
      }
      return;
    }

    newStatus &= ~BStatus.DISABLED;

    // down bit
    if (archiveStatus.isDown())
    {
      newStatus |= BStatus.DOWN;
    }
    else
    {
      newStatus &= ~BStatus.DOWN;
    }

    // TODO: isFatalFault(): add fatal fault check in NCCB-48166
    // fault bit
    if (!getLastFailure().isNull() && getLastFailure().isAfter(getLastSuccess()))
    {
      newStatus |= BStatus.FAULT;
    }
    else if (archiveStatus.isFault())
    {
      newStatus |= BStatus.FAULT;
      setFaultCause(LEX.getText("alarmArchive.archiveStatus.fault"));
    }
    else
    {
      newStatus &= ~BStatus.FAULT;
      setFaultCause("");
    }

    // short circuit if no changes
    if (newStatus == getStatus().getBits())
    {
      return;
    }

    setStatus(BStatus.make(newStatus));
  }

  /**
   * Return true if the service detected a fatal fault.
   * Fatal faults cannot be recovered until the service
   * is restarted.
   */
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  /**
   * Set the service into the fatal fault condition.  The fatal
   * fault condition cannot be cleared until station restart.
   *
   * @param cause fault cause
   */
  public final void configFatal(String cause)
  {
    fatalFault = true;
    setStatus(BStatus.make(getStatus().getBits() | BStatus.FAULT));
    setFaultCause(LEX.getText("alarmArchive.restartRequired", cause));
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  private static BAlarmSourceInfo initAlarmSourceInfo()
  {
    BAlarmSourceInfo asi = new BAlarmSourceInfo();
    asi.setSourceName(BFormat.make("%parent.displayName%"));
    asi.setToOffnormalText(BFormat.make("%lexicon(alarm:archiveFail)%"));
    asi.setToNormalText(BFormat.make("%lexicon(alarm:archiveSuccess)%"));
    return asi;
  }

  /**
   * If not disabled, calls execute on alarm archive who have failed state.
   */
  public void doRetry()
  {
    if (!isDisabled())
    {
      execute();
    }
  }

  static BBoolean processAlarmAck(AlarmSupport support, BAlarmRecord ackRequest)
  {
    // notify alarm service
    try
    {
      return BBoolean.make(support.ackAlarm(ackRequest));
    }
    catch(Throwable ignored)
    {

    }

    return BBoolean.FALSE;
  }

  public final BBoolean doAckAlarm(BAlarmRecord ackRequest)
  {
    BBoolean alarmAck = processAlarmAck(alarmSupport, ackRequest);

    // clear unacked alarm flag
    if (alarmAck.getBoolean())
    {
      setStatus(BStatus.make(getStatus(), BStatus.UNACKED_ALARM, false));
    }
    return alarmAck;
  }

  @Override
  public BIcon getIcon()
  {
    return BIcon.make(
      BIcon.make(LEX.get("archiveAlarmProvider.icon")),
      BIcon.make(LEX.get("archiveAlarmProvider.icon.badge")));
  }

  void initAlarmArchive()
  {
    BAlarmArchive alarmArchive = getAlarmArchive();
    if (alarmArchive != null && !alarmArchive.isOpen())
    {
      try
      {
        alarmArchive.open();
        try
        {
          BAlarmService alarmService = (BAlarmService) getParent();
          alarmService.getAlarmDb().addNavChild(alarmArchive);
        }
        catch (Exception e)
        {
          if (LOG.isLoggable(Level.FINE))
          {
            LOG.log(Level.FINE, "Failed to add Alarm Archive as nav child of Alarm Database", e);
          }
        }
      }
      catch (Exception e)
      {
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.log(Level.SEVERE, "Cannot open alarm database.", e);
        }
        else
        {
          LOG.log(Level.SEVERE, "Cannot open alarm database: " + e.getMessage());
        }
      }
      addAlarmArchiveToFoxChannelRegistry();
    }
    else if (alarmArchive != null && alarmArchive.isOpen())
    {
      // If the alarm archive is already open, fire off a REPLACED event. This is
      // necessary to allow NavListeners to be notified of the provider being enabled
      // if a session is connected to the station during a time in which the provider
      // was disabled (since the alarm archive is opened during #stationStarted, and
      // the NavEvent for the initial start of the provider would
      // have been fired before that particular NavListener could be notified).
      try
      {
        BAlarmDatabase alarmDb = ((BAlarmService)getParent()).getAlarmDb();
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.makeReplaced(alarmDb.getNavOrd(), alarmArchive.getNavName(), null));
      }
      catch (Exception e)
      {
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.log(Level.FINE, "Unable to fire NavEvent.REPLACED for Alarm Database:" + e.getMessage());
        }
      }
    }
  }

  private static void addAlarmArchiveToFoxChannelRegistry()
  {
    BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
    if(registry.get(BAlarmArchiveChannel.NAME) == null)
    {
      registry.add(BAlarmArchiveChannel.NAME, new BAlarmArchiveChannel());
    }
  }

  void closeAlarmArchive()
  {
    BAlarmArchive alarmArchive = getAlarmArchive();
    if (alarmArchive != null && alarmArchive.isOpen())
    {
      removeAlarmArchiveToFoxChannelRegistry();
      try
      {
        alarmArchive.flush();
      }
      catch (IOException e)
      {
        LOG.log(Level.SEVERE, "Failed to flush alarm archive", e);
      }
      alarmArchive.close();
      try
      {
        BAlarmService alarmService = (BAlarmService) getParent();
        alarmService.getAlarmDb().removeNavChild(alarmArchive);
      }
      catch (Exception e)
      {
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.log(Level.FINE, "Failed to remove Alarm Archive nav child from Alarm Database", e);
        }
      }
    }
  }

  private static void removeAlarmArchiveToFoxChannelRegistry()
  {
    BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
    if (registry.get(BAlarmArchiveChannel.NAME) != null)
    {
      registry.remove(BAlarmArchiveChannel.NAME);
    }
  }
////////////////////////////////////////////////////////////////
// Async
////////////////////////////////////////////////////////////////

  /**
   * This post method includes special handling for the execute action.
   * A post of the execute action results in a call to
   * postExecute(Action, BValue, Context).  If this method is overridden
   * super.post(Action, BValue, Context) must be called.
   */
  @Override
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(execute))
    {
      // short circuit in case of fatal fault
      if (!canExecuteRequest())
      {
        return null;
      }
      // short circuit if already pending or in progress
      if (getState() != BExecutionState.idle)
      {
        return null;
      }

      setLastAttempt(Clock.time());
      setState(BExecutionState.pending);
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
  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
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
    checkLicense();
    alarmSupport = new AlarmSupport(this, getAlarmSourceInfo());
    updateStatus();
    add("triggerLink?",
      new BLink(getExecutionTime().getOrdInSession(),"fireTrigger","execute",true),
      Flags.HIDDEN | Flags.TRANSIENT);

    Station.addSaveListener(saveListener);
    initAlarmArchive();
  }

  private void fwStopped()
  {
    Property triggerLink = getProperty("triggerLink");
    if (triggerLink != null)
    {
      remove(triggerLink);
    }
    if (Sys.getStation().isRunning())
    {
      Station.removeSaveListener(saveListener);
    }
    closeAlarmArchive();
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
      if (getEnabled())
      {
        initAlarmArchive();
      }
      else
      {
        closeAlarmArchive();
      }
    }
    else if (prop.equals(executionTime))
    { // Must re-add the fireTrigger link
      add("triggerLink?",
        new BLink(getExecutionTime().getOrdInSession(),"fireTrigger","execute",true),
        Flags.HIDDEN | Flags.TRANSIENT);
    }
    else if (prop.equals(status) && isFatalFault())
    {
      closeAlarmArchive();
    }
  }

  private void fwStationStarted()
  {
    // If the state isn't idle, then this descriptor was interrupted
    // in the middle of working.  In that case, immediately start
    // again.
    if (getState() != BExecutionState.idle)
    {
      setState(BExecutionState.idle);
      execute();
    }
  }

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = BAlarmDatabase.filterAgents(super.getAgents(cx));
    if (!isOperational())
    {
      //Filter out Alarm Db Views
      agents.remove(ALARM_ARCHIVE_VIEWS);
    }
    return agents;
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Ensures only one alarm archive provider instance is allowed to live under a
   * {@link BAlarmService} in a station.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    if (!parent.getType().is(BAlarmService.TYPE))
    {
      throw new IllegalParentException("baja", "IllegalParentException.parentAndChild",
        new Object[] { parent.getType(), getType() });
    }

    SlotCursor<Property> slots = parent.getProperties();
    while (slots.next(BArchiveAlarmProvider.class))
    {
      if (slots.get() != this)
      {
        throw new IllegalChildException("baja", "DuplicateRestrictedComponent",
          new Object[] { parent.getType(), getType() });
      }
    }
  }

  private final Station.SaveListener saveListener = new Station.SaveListener()
  {
    @Override
    public void stationSave()
    {
      try
      {
        getAlarmArchive().save();
      }
      catch(Exception e)
      {
        BAlarmDatabase.log.log(Level.SEVERE, "Archive Alarm database save failed.", e);
      }
    }
    @Override
    public void stationSaveOk() {}
    @Override
    public void stationSaveFail(String cause) {}
    @Override
    public String toString() { return "ArchiveAlarmProvider " + getNavOrd(); }
  };

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private boolean fatalFault;
  private AlarmSupport alarmSupport;
  public static final Lexicon LEX = Lexicon.make("alarm");
  private static final Logger LOG = BAlarmService.logger;

  private static final AgentList ALARM_ARCHIVE_VIEWS;
  static
  {
    ALARM_ARCHIVE_VIEWS = new NAgentList();
    ALARM_ARCHIVE_VIEWS.add("alarm:AlarmDbView");
    ALARM_ARCHIVE_VIEWS.add("alarm:AlarmDbMaintenance");
    ALARM_ARCHIVE_VIEWS.add("alarm:DatabaseView");
    ALARM_ARCHIVE_VIEWS.add("alarm:DatabaseMaintenance");
  }

  private static final String UNLICENSED = LEX.getText("alarmArchive.error.unlicensed");
}
