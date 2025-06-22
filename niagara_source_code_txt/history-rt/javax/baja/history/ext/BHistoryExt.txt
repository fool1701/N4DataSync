/*
 * Copyright 2000-2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history.ext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.control.BControlPoint;
import javax.baja.control.BPointExtension;
import javax.baja.history.BCollectionInterval;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryRecord;
import javax.baja.history.BHistoryService;
import javax.baja.history.BIHistory;
import javax.baja.history.BIHistorySource;
import javax.baja.history.BStringTrendRecord;
import javax.baja.history.BTrendFlags;
import javax.baja.history.BTrendRecord;
import javax.baja.history.HistoryException;
import javax.baja.history.HistoryNotFoundException;
import javax.baja.history.HistorySpaceConnection;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.history.db.HistoryDatabaseConnection;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Localizable;
import javax.baja.sys.LocalizableException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.util.BFormat;

import com.tridium.util.PxUtil;
/**
 * BHistoryExt is a standard point extension used
 * for logging a point's historical data.
 *
 * @author    John Sublett
 * @creation  02 Apr 02
 * @version   $Revision: 84$ $Date: 2/25/10 1:42:14 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The status of the history extension.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 If the extension is in a fault state, this provides
 a description of the problem.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Used to manually enable and disable this extension.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 Defines the time period when this extension should be collecting
 history records.
 */
@NiagaraProperty(
  name = "activePeriod",
  type = "BActivePeriod",
  defaultValue = "new BBasicActivePeriod()",
  facets = @Facet("BFacets.make(\"alwaysExpand\", BBoolean.make(true))")
)
/*
 Indicates whether this extension is currently in it's active period.
 */
@NiagaraProperty(
  name = "active",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 This properties defines a pattern for deriving the
 name of the history created by this extension.
 It can be static text or a simple pattern that allows
 the actual history name to be derived from the context.
 The default value of this property is %parent.name% which
 sets the history name in the id to the name of the parent
 of this extension.<p>
 Changes to this property cause the format to be reapplied
 and the history id in the history config is updated.
 */
@NiagaraProperty(
  name = "historyName",
  type = "BFormat",
  defaultValue = "BFormat.make(\"%parent.name%\")"
)
/*
 This is only here temporarily to help with the transition
 to the new historyName mechanism.  It will be removed
 before the final release.
 */
@NiagaraProperty(
  name = "historyNameFormat",
  type = "BFormat",
  defaultValue = "BFormat.make(\"%parent.name%\")",
  flags = Flags.HIDDEN | Flags.TRANSIENT
)
/*
 The configuration for the history created by this extension.
 */
@NiagaraProperty(
  name = "historyConfig",
  type = "BHistoryConfig",
  defaultValue = "new BHistoryConfig()"
)
/*
 This property stores a copy of the most recent record
 successfully appended to the history for this extension.
 @since Niagara 3.4
 */
@NiagaraProperty(
  name = "lastRecord",
  type = "BHistoryRecord",
  defaultValue = "new BStringTrendRecord()",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Update the history id of the history config with
 the current value of the history name property.
 This applies the historyName as a format relative
 to the history extension.  If the result is different
 from the name in the history id, the history id is
 changed and if necessary, the attached history is renamed.
 */
@NiagaraAction(
  name = "updateHistoryId",
  flags = Flags.ASYNC
)
/*
 Synchronize the history configuration of this extension
 with the configuration that is stored with the history
 in the database.
 */
@NiagaraAction(
  name = "syncConfig",
  flags = Flags.HIDDEN | Flags.ASYNC
)
/*
 Enter the active period.  This action should never be linked.
 It is invoked internally by the extension.
 */
@NiagaraAction(
  name = "activate",
  flags = Flags.ASYNC | Flags.HIDDEN
)
/*
 Exit the active period.  This action should never be linked.
 It is invoked internally by the extension.
 */
@NiagaraAction(
  name = "deactivate",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public abstract class BHistoryExt
  extends BPointExtension
  implements BIHistorySource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BHistoryExt(233536367)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The status of the history extension.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * The status of the history extension.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The status of the history extension.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * If the extension is in a fault state, this provides
   * a description of the problem.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * If the extension is in a fault state, this provides
   * a description of the problem.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * If the extension is in a fault state, this provides
   * a description of the problem.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * Used to manually enable and disable this extension.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(Flags.DEFAULT_ON_CLONE, false, null);

  /**
   * Get the {@code enabled} property.
   * Used to manually enable and disable this extension.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * Used to manually enable and disable this extension.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "activePeriod"

  /**
   * Slot for the {@code activePeriod} property.
   * Defines the time period when this extension should be collecting
   * history records.
   * @see #getActivePeriod
   * @see #setActivePeriod
   */
  public static final Property activePeriod = newProperty(0, new BBasicActivePeriod(), BFacets.make("alwaysExpand", BBoolean.make(true)));

  /**
   * Get the {@code activePeriod} property.
   * Defines the time period when this extension should be collecting
   * history records.
   * @see #activePeriod
   */
  public BActivePeriod getActivePeriod() { return (BActivePeriod)get(activePeriod); }

  /**
   * Set the {@code activePeriod} property.
   * Defines the time period when this extension should be collecting
   * history records.
   * @see #activePeriod
   */
  public void setActivePeriod(BActivePeriod v) { set(activePeriod, v, null); }

  //endregion Property "activePeriod"

  //region Property "active"

  /**
   * Slot for the {@code active} property.
   * Indicates whether this extension is currently in it's active period.
   * @see #getActive
   * @see #setActive
   */
  public static final Property active = newProperty(Flags.READONLY | Flags.TRANSIENT, false, null);

  /**
   * Get the {@code active} property.
   * Indicates whether this extension is currently in it's active period.
   * @see #active
   */
  public boolean getActive() { return getBoolean(active); }

  /**
   * Set the {@code active} property.
   * Indicates whether this extension is currently in it's active period.
   * @see #active
   */
  public void setActive(boolean v) { setBoolean(active, v, null); }

  //endregion Property "active"

  //region Property "historyName"

  /**
   * Slot for the {@code historyName} property.
   * This properties defines a pattern for deriving the
   * name of the history created by this extension.
   * It can be static text or a simple pattern that allows
   * the actual history name to be derived from the context.
   * The default value of this property is %parent.name% which
   * sets the history name in the id to the name of the parent
   * of this extension.<p>
   * Changes to this property cause the format to be reapplied
   * and the history id in the history config is updated.
   * @see #getHistoryName
   * @see #setHistoryName
   */
  public static final Property historyName = newProperty(0, BFormat.make("%parent.name%"), null);

  /**
   * Get the {@code historyName} property.
   * This properties defines a pattern for deriving the
   * name of the history created by this extension.
   * It can be static text or a simple pattern that allows
   * the actual history name to be derived from the context.
   * The default value of this property is %parent.name% which
   * sets the history name in the id to the name of the parent
   * of this extension.<p>
   * Changes to this property cause the format to be reapplied
   * and the history id in the history config is updated.
   * @see #historyName
   */
  public BFormat getHistoryName() { return (BFormat)get(historyName); }

  /**
   * Set the {@code historyName} property.
   * This properties defines a pattern for deriving the
   * name of the history created by this extension.
   * It can be static text or a simple pattern that allows
   * the actual history name to be derived from the context.
   * The default value of this property is %parent.name% which
   * sets the history name in the id to the name of the parent
   * of this extension.<p>
   * Changes to this property cause the format to be reapplied
   * and the history id in the history config is updated.
   * @see #historyName
   */
  public void setHistoryName(BFormat v) { set(historyName, v, null); }

  //endregion Property "historyName"

  //region Property "historyNameFormat"

  /**
   * Slot for the {@code historyNameFormat} property.
   * This is only here temporarily to help with the transition
   * to the new historyName mechanism.  It will be removed
   * before the final release.
   * @see #getHistoryNameFormat
   * @see #setHistoryNameFormat
   */
  public static final Property historyNameFormat = newProperty(Flags.HIDDEN | Flags.TRANSIENT, BFormat.make("%parent.name%"), null);

  /**
   * Get the {@code historyNameFormat} property.
   * This is only here temporarily to help with the transition
   * to the new historyName mechanism.  It will be removed
   * before the final release.
   * @see #historyNameFormat
   */
  public BFormat getHistoryNameFormat() { return (BFormat)get(historyNameFormat); }

  /**
   * Set the {@code historyNameFormat} property.
   * This is only here temporarily to help with the transition
   * to the new historyName mechanism.  It will be removed
   * before the final release.
   * @see #historyNameFormat
   */
  public void setHistoryNameFormat(BFormat v) { set(historyNameFormat, v, null); }

  //endregion Property "historyNameFormat"

  //region Property "historyConfig"

  /**
   * Slot for the {@code historyConfig} property.
   * The configuration for the history created by this extension.
   * @see #getHistoryConfig
   * @see #setHistoryConfig
   */
  public static final Property historyConfig = newProperty(0, new BHistoryConfig(), null);

  /**
   * Get the {@code historyConfig} property.
   * The configuration for the history created by this extension.
   * @see #historyConfig
   */
  public BHistoryConfig getHistoryConfig() { return (BHistoryConfig)get(historyConfig); }

  /**
   * Set the {@code historyConfig} property.
   * The configuration for the history created by this extension.
   * @see #historyConfig
   */
  public void setHistoryConfig(BHistoryConfig v) { set(historyConfig, v, null); }

  //endregion Property "historyConfig"

  //region Property "lastRecord"

  /**
   * Slot for the {@code lastRecord} property.
   * This property stores a copy of the most recent record
   * successfully appended to the history for this extension.
   * @since Niagara 3.4
   * @see #getLastRecord
   * @see #setLastRecord
   */
  public static final Property lastRecord = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStringTrendRecord(), null);

  /**
   * Get the {@code lastRecord} property.
   * This property stores a copy of the most recent record
   * successfully appended to the history for this extension.
   * @since Niagara 3.4
   * @see #lastRecord
   */
  public BHistoryRecord getLastRecord() { return (BHistoryRecord)get(lastRecord); }

  /**
   * Set the {@code lastRecord} property.
   * This property stores a copy of the most recent record
   * successfully appended to the history for this extension.
   * @since Niagara 3.4
   * @see #lastRecord
   */
  public void setLastRecord(BHistoryRecord v) { set(lastRecord, v, null); }

  //endregion Property "lastRecord"

  //region Action "updateHistoryId"

  /**
   * Slot for the {@code updateHistoryId} action.
   * Update the history id of the history config with
   * the current value of the history name property.
   * This applies the historyName as a format relative
   * to the history extension.  If the result is different
   * from the name in the history id, the history id is
   * changed and if necessary, the attached history is renamed.
   * @see #updateHistoryId()
   */
  public static final Action updateHistoryId = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code updateHistoryId} action.
   * Update the history id of the history config with
   * the current value of the history name property.
   * This applies the historyName as a format relative
   * to the history extension.  If the result is different
   * from the name in the history id, the history id is
   * changed and if necessary, the attached history is renamed.
   * @see #updateHistoryId
   */
  public void updateHistoryId() { invoke(updateHistoryId, null, null); }

  //endregion Action "updateHistoryId"

  //region Action "syncConfig"

  /**
   * Slot for the {@code syncConfig} action.
   * Synchronize the history configuration of this extension
   * with the configuration that is stored with the history
   * in the database.
   * @see #syncConfig()
   */
  public static final Action syncConfig = newAction(Flags.HIDDEN | Flags.ASYNC, null);

  /**
   * Invoke the {@code syncConfig} action.
   * Synchronize the history configuration of this extension
   * with the configuration that is stored with the history
   * in the database.
   * @see #syncConfig
   */
  public void syncConfig() { invoke(syncConfig, null, null); }

  //endregion Action "syncConfig"

  //region Action "activate"

  /**
   * Slot for the {@code activate} action.
   * Enter the active period.  This action should never be linked.
   * It is invoked internally by the extension.
   * @see #activate()
   */
  public static final Action activate = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code activate} action.
   * Enter the active period.  This action should never be linked.
   * It is invoked internally by the extension.
   * @see #activate
   */
  public void activate() { invoke(activate, null, null); }

  //endregion Action "activate"

  //region Action "deactivate"

  /**
   * Slot for the {@code deactivate} action.
   * Exit the active period.  This action should never be linked.
   * It is invoked internally by the extension.
   * @see #deactivate()
   */
  public static final Action deactivate = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code deactivate} action.
   * Exit the active period.  This action should never be linked.
   * It is invoked internally by the extension.
   * @see #deactivate
   */
  public void deactivate() { invoke(deactivate, null, null); }

  //endregion Action "deactivate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BHistoryExt()
  {
  }

  /**
   * Get the ord to use as the source for the history.
   */
  public BOrd getSourceOrd()
  {
    BComponentSpace cs = getComponentSpace();
    if (cs == null) return null;
    BOrd base = cs.getOrdInSession();
    if (base == null) return null;
    return BOrd.make(base, getSlotPathOrd());
  }

  /**
   * Any sibling is legal for a history extension.
   */
  @Override
  protected boolean isSiblingLegal(BComponent sibling)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Initialize the status on startup.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();

    // Make sure the transient flag is always set on the lastRecord property!
    setFlags(lastRecord, getFlags(lastRecord) | Flags.TRANSIENT);

    setActive(getActivePeriod().isActive(BAbsTime.make()));

    // Make sure the config has the correct record type.
    Type recType = getRecordType();
    if (!recType.is(BTrendRecord.TYPE))
      throw new HistoryException("Record type must be a trend record. (actual type = " + recType.getTypeSpec() + ")");
    BHistoryConfig config = getHistoryConfig();
    if (!config.getRecordType().equals(recType.getTypeSpec()))
      config.setRecordType(recType.getTypeSpec());

    syncFacets();
  }

  /**
   * Component start callback.
   */
  @Override
  public void descendantsStarted()
    throws Exception
  {
    super.descendantsStarted();

    // Make sure the transient flag is always set on the lastRecord property!
    setFlags(lastRecord, getFlags(lastRecord) | Flags.TRANSIENT);

    init();
  }

  private void init()
  {
    syncFacets();
    if (getEnabled())
    {
      try
      {
        initHistory();
        checkPointSubscription();
      }
      catch(Exception ex)
      {
        setFault(ex);
        return;
      }
    }

    updateStatus();

    try
    {
      scheduleActivation();
    }
    catch(HistoryException ex)
    {
      throw ex;
    }
    catch(Exception ex)
    {
      throw new HistoryException(ex);
    }
  }

  /**
   * Component stop callback.
   */
  @Override
  public void stopped()
    throws Exception
  {
    super.stopped();
    history = null;
  }

  /**
   * Handle a property change.
   */
  @Override
  public void changed(Property p, Context c)
  {
    if (c == Context.decoding) return;
    if (!isRunning()) return;

    try
    {
      Sys.getService(BHistoryService.TYPE);
    }
    catch(ServiceNotFoundException e)
    {
      return;
    }

    // handle a change to the enabled state
    if (p.equals(enabled))
    {
      if (getEnabled())
      {
        init();
      }
      else
      {
        if (ticket != null) { ticket.cancel(); ticket = null; }
        history = null;
        checkPointSubscription();
        updateStatus();
      }
    }
    else if (p.equals(historyName))
    {
      // TODO - don't reninit if I'm handling the name transition stuff.
      if (inInit) return;
      initHistory();
    }
    else if (p.equals(activePeriod))
    {
      boolean oldActive = getActive();
      boolean newActive = getActivePeriod().isActive(BAbsTime.make());

      try
      {
        if (oldActive != newActive)
        {
          if (ticket != null) { ticket.cancel(); ticket = null; }

          if (newActive)
          {
            processActivate();
          }
          else
          {
            deactivate();
          }
        }
        else
        {
          scheduleActivation();
        }
      }
      catch(HistoryException ex)
      {
        throw ex;
      }
      catch(Exception ex)
      {
        throw new HistoryException(ex);
      }
    }
    // it is important to check for status or fault cause
    // here because those properties are updated in
    // updateStatus().  If I update the status on a change
    // to those properties -> StackOverflow.
    else if (!p.equals(status) && !p.equals(faultCause))
      updateStatus();
  }

  /**
   * Point change callback.
   *
   * <p>
   * Any point change callbacks must be captured
   * in case the algorithm is a change of value one.
   */
  @Override
  public void onExecute(BStatusValue out, Context cx)
  {
    syncFacets();
    if (!getEnabled() || !Sys.atSteadyState()) return;
    if (isRunning())
    {
      if (getActive())
      {
        BAbsTime now = BAbsTime.make();
        try { pointChanged(now, out); }
        catch(HistoryException ex)
        {
          throw ex;
        }
        catch(Exception ex)
        {
          throw new HistoryException(ex);
        }
      }
    }
  }

  /**
   * Handle a change to the parent control point.
   */
  public abstract void pointChanged(BAbsTime timestamp, BStatusValue out)
    throws Exception;

  /**
   * A history extension requires its point to be subscribed
   * whenever it is enabled.
   */
  @Override
  public boolean requiresPointSubscription()
  {
    if (!getEnabled())
      return false;
    else
      return true;
  }

  /**
   * Make sure the subscribed state of the parent control point
   * is correct.
   */
  public void checkPointSubscription()
  {
    BControlPoint point = getParentPoint();

    if (point != null)
      point.checkExtensionsRequireSubscription();
  }

  /**
   * Update the extension status based on the current configuration
   * and state.
   */
  public void updateStatus()
  {
    if (!getEnabled())
    {
      setStatus(BStatus.disabled);
      setFaultCause("");
    }
    else
    {
      boolean validName = getHistoryConfig().getId().isValid();
      if (!validName)
      {
        setStatus(BStatus.fault);
        setFaultCause("Invalid history name.");
      }
      else if (initHistorySuccessful) // 4/15/05 S. Hoye added to fix pacman issue 6077
      {  // Make sure initHistory was successful before marking OK

        // 1/26/07 - Issue 9179
        if (nextValidThreshold != null)
        { // If non-null, indicates that an out-of-order (or duplicate) record append was attempted
          // last (the abs time indicates the next valid time after which an append can safely occur).
          // So we want to set the status to fault, and give a fault cause message.
          setStatus(BStatus.fault);
          setFaultCause("Cannot append out-of-order or duplicate record. This could occur on a backwards system clock change. Next append can occur after "+nextValidThreshold);
        }
        else
        { // Otherwise its business as usual, but be sure to clear
          // the fault cause when setting the status to OK
          setStatus(BStatus.ok);
          setFaultCause("");
        }
      }
      else
        setStatus(BStatus.makeDisabled(getStatus(), false)); // It is safe to remove the disabled flag
    }
  }

////////////////////////////////////////////////////////////////
// Active Period
////////////////////////////////////////////////////////////////

  /**
   * Schedule activation according to the active period.
   */
  private void scheduleActivation()
    throws IOException
  {
    if (ticket != null)
    {
      ticket.cancel();
      ticket = null;
    }

    BActivePeriod activePeriod = getActivePeriod();

    if (activePeriod.isNeverActive()) return;

    BAbsTime now = BAbsTime.make();
    if (activePeriod.isActive(now))
    {
      processActivate();
    }
    else
    {
      BAbsTime next = activePeriod.getNextActive(now);
      if (next != null)
      {
        if (log.isLoggable(Level.FINE))
          log.fine(getSlotPath() + ": next active " + next.toString(BHistoryRecord.TIMESTAMP_FACETS));
        ticket = Clock.schedule(this, next, activate, null);
      }
    }
  }

  /**
   * Process a request to invoke the {@link #activate} action. If this history
   * ext instance is disabled ({@link #getEnabled()} is false), then the
   * activate action will be invoked synchronously on the calling thread since
   * history record writes should not occur. Otherwise the activate action will
   * be invoked asynchronously on the control engine thread.
   *
   * @since Niagara 4.9
   */
  private void processActivate()
  {
    if (!getEnabled())
    {
      DISABLED_ON_ACTIVATION.set(Boolean.TRUE);
      try
      {
        // Per NCCB-44923, when the history ext is disabled, run the activate
        // action synchronously on the calling thread in order for the thread
        // local to be detected. This should be quick enough to process
        // synchronously since history record appends (file writes) are
        // short-circuited (see corresponding check in the append() method).
        doActivate();
      }
      finally
      {
        DISABLED_ON_ACTIVATION.remove();
      }
    }
    else
    {
      // If the history ext is enabled, process the activate action
      // asynchronously on the control engine thread
      activate();
    }
  }

  /**
   * Activate the extension.  This is typically invoked
   * when the extension enters its active period.
   */
  public void doActivate()
  {
    start = true;
    try
    {
      BAbsTime now = BAbsTime.make();
      setActive(true);
      if (getActivePeriod().isAlwaysActive())
        activated(now.timeOfDay(0, 0, 0, 0), now, getParentPoint().getStatusValue());
      else
      {
        BAbsTime activeStart = getActivePeriod().getActiveStart(now);
        activated(activeStart, now, getParentPoint().getStatusValue());
      }

      scheduleDeactivation();
    }
    catch (HistoryException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new HistoryException(ex);
    }
  }

  /**
   * Receive notification that the extension has entered the active period.
   *
   * @param activeStartTime The configured start time of the active period that has just started.
   * @param currentTime The current time.
   * @param out The output value of the control point at the time of activation.
   */
  protected abstract void activated(BAbsTime activeStartTime, BAbsTime currentTime, BStatusValue out)
    throws IOException;

  /**
   * Schedule deactivation according to the active period.
   */
  private void scheduleDeactivation()
    throws IOException
  {
    if (ticket != null)
    {
      ticket.cancel();
      ticket = null;
    }

    BActivePeriod activePeriod = getActivePeriod();

    if (activePeriod.isNeverActive()) return;

    BAbsTime now = BAbsTime.make();
    if (activePeriod.isActive(now))
    {
      BAbsTime next = activePeriod.getNextInactive(now);
      if (next != null)
      {
        if (log.isLoggable(Level.FINE))
          log.fine(getSlotPath() + ": next inactive " + next.toString(BHistoryRecord.TIMESTAMP_FACETS));
        ticket = Clock.schedule(this, next, deactivate, null);
      }
    }
    else
    {
      deactivate();
    }
  }

  /**
   * End the active period.  This is typically invoked
   * when the extension exits its active period.
   */
  public void doDeactivate()
  {
    try
    {
      setActive(false);
      deactivated(BAbsTime.make(), getParentPoint().getStatusValue());
      scheduleActivation();
    }
    catch(HistoryException ex)
    {
      throw ex;
    }
    catch(Exception ex)
    {
      throw new HistoryException(ex);
    }
  }

  /**
   * Receive notification that the extension has exited the active period.
   *
   * @param currentTime The current time.
   * @param out The output value of the control point at the time of deactivation.
   */
  protected abstract void deactivated(BAbsTime currentTime, BStatusValue out)
    throws IOException;

////////////////////////////////////////////////////////////////
// History
////////////////////////////////////////////////////////////////

  /**
   * Returns the history or null if not initialized.
   */
  public BIHistory getHistory()
  {
    return history;
  }

  /**
   * Resolve the history name pattern to a string.
   */
  public String resolveHistoryName()
  {
    BFormat pattern = getHistoryName();

    // This is a tricky issue.  It looks funny but it's correct.
    // The resolved pattern must be unescaped because it may include
    // slot names of ancestors (ex. "%parent.name%") which are already
    // escaped.  Sometimes the result needs to be escaped because
    // it may just be static test (ex. "My History").  By unescaping
    // the result first, the escape will always return the right result.
    return SlotPath.escape(SlotPath.unescape(pattern.format(this)));
  }

  /**
   * Get the history ready for writing.  If it does not exist, create it.
   */
  private synchronized void initHistory()
    throws ServiceNotFoundException
  {
    BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
    BHistoryDatabase db = service.getDatabase();

    try (HistoryDatabaseConnection conn = db.getDbConnection(null))
    {
      initHistory(conn);
    }
  }

  /**
   * Get the history ready for writing.  If it does not exist, create it.
   */
  private synchronized void initHistory(HistoryDatabaseConnection conn)
    throws ServiceNotFoundException
  {
    initHistorySuccessful = false; // 4/15/05 S. Hoye added to fix pacman issue 6077
                                   // initialize initHistorySuccessful.

    if (!getEnabled()) return;

    try
    {
      // pull the old historyNameFormat switcheroo if necessary
      // TODO - remove this and the historyNameFormat property before
      // final release
      inInit = true;
      BFormat oldFormat = getHistoryNameFormat();
      String oldName = getHistoryConfig().getHistoryName();
      if (oldName.length() != 0)
      {
        getHistoryConfig().setHistoryName("");
        setHistoryNameFormat(BFormat.DEFAULT);

        String oldResolved = oldFormat.format(this);
        if (!oldName.equals(oldResolved))
          setHistoryName(BFormat.make(oldName));
        else
          setHistoryName(oldFormat);
      }
      inInit = false;
      // remove all the way to here

      // fix up the required config properties
      BHistoryConfig config = getHistoryConfig();
      BOrdList source = BOrdList.make(getSourceOrd());
      BOrd handleOrd = getHandleOrd();
      if (!config.getSource().equals(source)) config.setSource(source);
      if (!config.getSourceHandle().equals(handleOrd)) config.setSourceHandle(handleOrd);
      if (!config.getTimeZone().equals(BTimeZone.getLocal())) config.setTimeZone(BTimeZone.getLocal());

      String historyName = resolveHistoryName();
      BHistoryId.validateName(historyName);

      BHistoryId newId = BHistoryId.make(Sys.getStation().getStationName(), historyName);
      BHistoryId id = config.getId();

      if (!id.getHistoryName().equals(newId.getHistoryName()))
      {
        if (!id.isNull())
        {
          // If the old id and the new id are different, I may need to
          // rename the existing history.  Let's see...
          renameAttachedHistory(conn, id, newId);
        }
      }

      if (!id.equals(newId))
      {
        config.setId(newId);
        id = newId;
      }

      // Set the value facets from the control point.
      BControlPoint point = getParentPoint();
      BFacets facets = point.getFacets();
      BTrendRecord rec = (BTrendRecord)config.makeRecord();
      Property valueProp = rec.getValueProperty();
      String facetsName = valueProp.getName() + "Facets";
      Property prop = config.loadSlots().getProperty(facetsName);
      if (prop == null)
        config.add(facetsName, facets);
      else
      {
        BFacets oldFacets = (BFacets)config.get(prop);
        if (!oldFacets.equals(facets)) config.set(prop, facets);
      }

      initConfig(config);

      // this may trigger an exception if the history Metrics count is exceeded
      if (!conn.exists(id) && getEnabled())
      {
        conn.createHistory(config);
      }
      else if (conn.exists(id))
      {
        // check the source
        BIHistory existing = conn.getHistory(id);
        BHistoryConfig existingCfg = existing.getConfig();
        BOrdList historySrc = existingCfg.getSource();
        if (historySrc.size() != 1)
          throw new HistoryException("Duplicate history id: " + config.getId() + ". Source mismatch.");
        else
        {
          BOrd extOrd = historySrc.get(0);
          BObject src = null;

          try
          {
            src = extOrd.resolve(this).get();
          }
          catch(Exception e)
          {
          }

          if ((src != null) && (src != this))
          {
            throw new HistoryException("Duplicate history id: " + config.getId());
          }
        }

        conn.reconfigureHistory(config);

        // Initialize the lastRecord property with a copy of the last record in the history
        try
        {
          existing = conn.getHistory(id);
          BHistoryRecord lastRec = conn.getLastRecord(existing);
          if (lastRec != null)
          {
            setLastRecord(lastRec);
          }
        }
        catch(Exception e)
        {
          log.log(Level.SEVERE, "Could not initialize 'Last Record' property for history ext "+toPathString(), e);
        }

        if (getEnabled())
        {
          history = conn.getHistory(id);
          if (history == null)
            throw new HistoryException("Cannot open history: " + id);
        }
      }
    }
    catch(Exception ex)
    {
      setFault(ex);
      return;
    }

    setFault(false, "");
    if (getEnabled())
      initHistorySuccessful = true; // 4/15/05 S. Hoye added to fix pacman issue 6077
                                    // initHistory was successful.
  }

  /**
   * Subclass override for setting properties
   */
  protected void initConfig(BHistoryConfig config)
  {
  }

  /**
   * Handle a history configuration change.
   */
  @Override
  public void historyConfigChanged(BHistoryConfig config, Property p)
  {
    if (!isRunning()) return;
    if (config != getHistoryConfig()) return;

    updateStatus();
    syncConfig();
  }

  /**
   * Rename the history that this extension is attached to.
   */
  private void renameAttachedHistory(HistorySpaceConnection conn, BHistoryId oldId, BHistoryId newId)
  {
    // If the new history already exists then I can't rename the
    // old one to it.  In that case I have to check the source
    // of the new history to see if it's me.  If it is, then
    // I'm in an unexpected situation, but I can still proceed
    // and just start using the new history.  The old history
    // will get orphaned but the data will be preserved.
    BIHistory newHistory = conn.getHistory(newId);
    if (newHistory != null)
    {
      BHistoryConfig newCfg = newHistory.getConfig();
      BOrdList newSrc = newCfg.getSource();
      BOrd newHandle = newCfg.getSourceHandle();
      if (newSrc.size() != 1 || (!newHandle.isNull() && !newHandle.equals(getHandleOrd())))
      {
        throw new HistoryException
          ("Cannot rename attached history.  The target id is already in use.");
      }
      else
      {
        log.warning("Rename failed for " + oldId + ".  New data will be written to " + newId);
        return;
      }
    }

    BIHistory oldHistory = conn.getHistory(oldId);

    // if the history hasn't been created with the old id
    // then I'm done
    if (oldHistory == null) return;

    BHistoryConfig config = oldHistory.getConfig();

    // Now I need to check the source.  If the source isn't
    // me, then there was a name conflict that is probably
    // being resolved by the id change.  Don't rename the
    // history in this case.
    BOrdList src = config.getSource();
    BOrd handle = config.getSourceHandle();
    if (src.size() != 1) return;
    if (!handle.isNull() && !handle.equals(getHandleOrd())) return;
    conn.renameHistory(oldId, newId.getHistoryName());

  }

  /**
   * Update the history id based on the current history name
   * if necessary.
   */
  public void doUpdateHistoryId()
  {
    String newName = resolveHistoryName();
    BHistoryId id = getHistoryConfig().getId();
    BHistoryId newId = BHistoryId.make(Sys.getStation().getStationName(), newName);

    if (!id.equals(newId)) initHistory();
  }

  /**
   * Synchronize the history configuration of this extension
   * with the configuration that is stored with the history
   * in the database.
   */
  public synchronized void doSyncConfig()
  {
    if(initHistorySuccessful) // 4/15/05 S. Hoye added to fix pacman issue 6077
    {                         // If unsuccessful initHistory, short circuit.
      BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
      BHistoryDatabase db = service.getDatabase();
      try (HistoryDatabaseConnection conn = db.getDbConnection(null))
      {
        conn.reconfigureHistory(getHistoryConfig());
      }
    }
  }

  /**
   * Get the type of the records written by this extension.  The type
   * must be a subtype of history:TrendRecord.
   */
  public abstract Type getRecordType();

  /**
   * Append a new record to the history.
   */
  public final void append(BTrendRecord record)
    throws IOException, HistoryException
  {
    // Per NCCB-44923, we need to prevent an invalid history record during
    // activation. If the original activation call (from descendantsStarted())
    // occurred when getEnabled() was false, that means we don't want to append
    // an improper {start} record, even if another thread subsequently enables
    // the history ext before the activation call gets this far on its thread.
    // Note that enabling the history ext will trigger another activation that
    // will properly append a {start} record on the control engine thread,
    // therefore we avoid a duplicate {start} record. The DISABLED_ON_ACTIVATION
    // thread local field used here lets us know whether the history ext was
    // disabled when the activation request was originally made.
    if (!getEnabled() || DISABLED_ON_ACTIVATION.get() == Boolean.TRUE)
    {
      return;
    }

    if (isRunning() && Sys.atSteadyState())
    {
      if (start) record.setTrendFlags(record.getTrendFlags().set(BTrendFlags.START, true));

      // try at most two times
      for (int i = 0; i < 2; i++)
      {
        BHistoryDatabase db = ((BHistoryService) Sys.getService(BHistoryService.TYPE)).getDatabase();

        try (HistoryDatabaseConnection conn = db.getDbConnection(null))
        {
          synchronized (this)
          {
            if (history == null) initHistory(conn);
            if (history != null)
            {

              // 1/26/07 - Issue 9179
              // Check to make sure out-of-order/duplicate records are not allowed.
              // For interval type history extensions, we must also allow
              // for a tolerance.  For example, for an interval extension, the timestamp
              // on an append can vary by a few milliseconds.  So if a collection
              // occurs at 12:00.01, and then a backwards time change occurs, and another
              // collection occurs at 12:00.03, this should be considered a duplicate
              // record and disallowed, even though the timestamps are in ascending order.
              BCollectionInterval cInterval = getHistoryConfig().getInterval();
              BRelTime tolerance = null;
              if (cInterval.isIrregular()) // The tolerance is not applicable to COV extensions (irregular intervals)
                tolerance = BRelTime.make(0L);
              else // If not irregular, must be an interval extension, so the tolerance is half the interval
                tolerance = BRelTime.make(cInterval.getInterval().getMillis() / 2L);

              BAbsTime recTimestamp = record.getTimestamp(); // The timestamp for the record to be appended
              BAbsTime lastTimestamp = conn.getLastTimestamp(history); // The timestamp of the last record appended
              if ((lastTimestamp != null) &&
                  (!lastTimestamp.isNull()) &&
                  (!(recTimestamp.getMillis() > (lastTimestamp.getMillis() + tolerance.getMillis()))))
              {
                nextValidThreshold = lastTimestamp; // A non-null nextValidThreshold indicates the out-of-order/duplicate state has occurred
              }
              else
              {
                nextValidThreshold = null; // A null nextValidThreshold indicates the append is in proper order
              }

              if (nextValidThreshold == null)
              { // Only append if the record's timestamp is in the proper order
                conn.append(history, record);
                start = false;
                setLastRecord((BHistoryRecord) (record.newCopy()));
              }
            }
          }

          // 1/26/07 - Issue 9179
          // The updateStatus() call will ensure that the fault status gets marked
          // if the out-of-order or duplicate attempt was detected and prevented.
          // It will also clear the fault status when appropriate.
          if (history != null) updateStatus();
          break;
        }
        catch (HistoryNotFoundException ex)
        {
          if (i == 0)
          {
            initHistory();
          }
          else
          {
            boolean wasFault = getStatus().isFault();
            setFault(ex);
            if (!wasFault)
              throw ex;
            else
              break;
          }
        }
        catch (Exception ex)
        {
          boolean wasFault = getStatus().isFault();
          setFault(ex);
          if (!wasFault)
          {
            if (ex instanceof HistoryException)
              throw (HistoryException) ex;
            else
              throw new HistoryException(ex);
          }
          else
            break;
        }
      }
    }
  }

  /**
   * Synchronize the control point facets with the facets for the
   * trend value in the config.
   */
  private void syncFacets()
  {
    // Set the value facets from the control point.
    BControlPoint point = getParentPoint();
    BFacets facets = point.getFacets();
    if ((localFacets != null) && (facets == localFacets))
      return;

    localFacets = facets;

    BHistoryConfig config = getHistoryConfig();
    if (config.getRecordType().isNull()) return;
    BTrendRecord rec = (BTrendRecord)config.makeRecord();
    Property valueProp = rec.getValueProperty();
    String facetsName = valueProp.getName() + "Facets";
    Property prop = config.loadSlots().getProperty(facetsName);
    if (prop == null)
      config.add(facetsName, facets);
    else
    {
      BFacets configFacets = (BFacets)config.get(prop);
      if (!configFacets.equals(facets))
        config.set(prop, facets);
    }
  }

////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////


  /**
   * Set the fault state and cause.
   */
  private void setFault(boolean fault, String cause)
  {
    setStatus(BStatus.makeFault(getStatus(), fault));
    setFaultCause(cause);
  }

  private void setFault(Throwable ex)
  {
    if (!(ex instanceof HistoryException))
    {
      log.log(Level.SEVERE, getHistoryConfig().getId().toString(), ex);
    }

    String faultCause;
    // in case the exception is a LocalizableException or LocalizableRuntimeException;
    // set the fault cause as the localized message of that wrapped exception.
    if (ex instanceof LocalizableException || ex instanceof LocalizableRuntimeException)
    {
      faultCause = ((Localizable)ex).toString(null);
    }
    else
    {
      /*
       * fix by NCCB-51638: In case of an underlying exception thrown from the database or the file system, the same is
       * wrapped in a HistoryException and is set as the fault cause which can be unhelpful to debug.
       * To provide a better fault cause we keep walking the exception path till we reach the root exception and get the
       * fault cause
       */
      Throwable cause;
      Throwable currentException = ex;

      while (null != (cause = currentException.getCause()) && currentException != cause)
      {
        currentException = cause;
      }

      faultCause = currentException.getMessage();
      // If the exception is a FileNotFoundException, trim out the path and just show the file name with the exception message
      if (currentException instanceof FileNotFoundException)
      {
        // get the last index of the '/' and populate msg with the file name and the exception cause; in case the File
        // separatorChar is missing, the whole msg is displayed
        faultCause = faultCause.substring(faultCause.lastIndexOf(File.separatorChar) + 1);
      }
    }

    // if no fault cause found in the exception, set the fault cause as the exception class name
    if (faultCause == null || faultCause.isEmpty())
    {
      faultCause = ex.getClass().getName();
    }
    setStatus(BStatus.makeFault(getStatus(), true));
    setFaultCause(faultCause);
  }

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  /**
   * Get the agents for a history.
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.toTop("workbench:PropertySheet");

    return PxUtil.movePxViewsToTop(list);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("history.ext");

  // Per NCCB-44923 (introduced in Niagara 4.9), we need to prevent an invalid
  // history record during activation. If the original activation call (from
  // descendantsStarted()) occurred when getEnabled() was false, that means we
  // don't want to append an improper {start} record, even if another thread
  // subsequently enables the history ext before the activation call finishes
  // processing on its thread. Note that enabling the history ext will trigger
  // another activation that will properly append a {start} record on the
  // control engine thread, therefore we avoid a duplicate {start} record. This
  // DISABLED_ON_ACTIVATION thread local field lets us know whether the history
  // ext was disabled when an activation request was originally made.
  private static final ThreadLocal<Boolean> DISABLED_ON_ACTIVATION = new ThreadLocal<>();

  private boolean start = true;
  private BIHistory history;
  private Clock.Ticket ticket;
  private boolean initHistorySuccessful = false; // 4/15/05 S. Hoye added to fix pacman issue 6077
                                                 // Set this instance to true when the history has been successfully inited (no duplicate names)

  // Keep a copy of the last facets read from the parent point.  This
  // is used to sync with the config on a change.
  private BFacets localFacets;

  // TODO - remove for final release
  private boolean inInit = false;

  // If non-null, indicates that an out-of-order (or duplicate) record append was attempted
  // last (the abs time indicates the next valid time after which an append can safely occur).
  // If null, then the last append occurred in proper order.
  private BAbsTime nextValidThreshold = null; // 1/26/07 - Issue 9179
}
