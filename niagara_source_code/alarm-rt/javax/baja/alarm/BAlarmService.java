/*
 * Copyright 2000-2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.collection.BITable;
import javax.baja.control.trigger.BIntervalTriggerMode;
import javax.baja.control.trigger.BTimeTrigger;
import javax.baja.dataRecovery.BIDataRecoveryService;
import javax.baja.dataRecovery.BIDataRecoverySourceService;
import javax.baja.license.Feature;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.rpc.NiagaraRpc;
import javax.baja.rpc.Transport;
import javax.baja.rpc.TransportType;
import javax.baja.security.AuditEvent;
import javax.baja.security.Auditor;
import javax.baja.space.BComponentSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BVector;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Flags;
import javax.baja.sys.IllegalNameException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.BUuid;
import javax.baja.util.CoalesceQueue;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;
import javax.baja.util.Queue;
import javax.baja.util.Version;
import javax.baja.util.Worker;

import com.tridium.alarm.BAlarmExtStatusJob;
import com.tridium.alarm.BIUpdatableAlarmSource;
import com.tridium.alarm.db.file.BFileAlarmDatabase;
import com.tridium.alarm.db.file.BFileAlarmDbConfig;
import com.tridium.alarm.fox.BAlarmDbChannel;
import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.fox.util.FoxRpcUtil;
import com.tridium.platform.BPlatformService;
import com.tridium.platform.BPlatformServiceContainer;
import com.tridium.platform.alarm.BIAlarmablePlatformService;
import com.tridium.platform.alarm.BPlatformServiceAlarmRecord;
import com.tridium.platform.alarm.BPlatformServiceSourceState;
import com.tridium.platform.alarm.PlatformServiceAlarmListener;
import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.station.Station;
import com.tridium.util.PxUtil;

/**
 * The BAlarmService uses BAlarmClasses to route all alarm
 * messages between AlarmSources and BAlarmRecipients.
 * <p>
 * Each station contains a single BAlarmService.
 *
 * @author Dan Giorgis on 19 Feb 01
 * @since Baja 1.0
 */
@NiagaraType
/*
 Alarm Database Configuration.
 */
@NiagaraProperty(
  name = "alarmDbConfig",
  type = "BAlarmDbConfig",
  defaultValue = "new BFileAlarmDbConfig()"
)
/*
 The default alarm class.
 */
@NiagaraProperty(
  name = "defaultAlarmClass",
  type = "BAlarmClass",
  defaultValue = "new BAlarmClass()"
)
/*
 List of generic instructions that are available to all alarm exts.
 */
@NiagaraProperty(
  name = "masterAlarmInstructions",
  type = "BAlarmInstructions",
  defaultValue = "BAlarmInstructions.make(\"\")"
)
/*
 The frequency that alarms should be escalated
 */
@NiagaraProperty(
  name = "escalationTimeTrigger",
  type = "BTimeTrigger",
  defaultValue = "new BTimeTrigger(BIntervalTriggerMode.make(BRelTime.makeMinutes(1)))",
  flags = Flags.HIDDEN
)
/*
 Determines whether to coalesce alarms or not.
 */
@NiagaraProperty(
  name = "coalesceAlarms",
  type = "boolean",
  defaultValue = "true"
)
/*
 Route an alarm record to recipients.
 */
@NiagaraAction(
  name = "routeAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  flags = Flags.HIDDEN
)
/*
 Route an ack Request to it's source.
 */
@NiagaraAction(
  name = "ackAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  flags = Flags.HIDDEN
)
/*
 Set into service all of the extensions referenced in the specified list of ords.
 */
@NiagaraAction(
  name = "enableToOffnormal",
  parameterType = "BVector",
  defaultValue = "new BVector()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
/*
 Set disabled all of the extensions referenced in the specified list of ords.
 */
@NiagaraAction(
  name = "disableToOffnormal",
  parameterType = "BVector",
  defaultValue = "new BVector()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
/*
 Set into service all of the extensions referenced in the specified list of ords.
 */
@NiagaraAction(
  name = "enableToFault",
  parameterType = "BVector",
  defaultValue = "new BVector()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
/*
 Set disabled all of the extensions referenced in the specified list of ords.
 */
@NiagaraAction(
  name = "disableToFault",
  parameterType = "BVector",
  defaultValue = "new BVector()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
/*
 Creates an audit record in the AuditHistory Database if available.
 Flagged as no audit since this method create an audit record.
 */
@NiagaraAction(
  name = "auditForceClear",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  flags = Flags.HIDDEN | Flags.NO_AUDIT
)
/*
 Creates an audit record in the AuditHistory Database if available.
 Flagged as no audit since this method create an audit record.
 */
@NiagaraAction(
  name = "escalateAlarms",
  flags = Flags.HIDDEN | Flags.NO_AUDIT | Flags.ASYNC
)
/*
 Fired whenever an alarm is received (eg. when the alarm is routed to an alarm class).
 */
@NiagaraTopic(
  name = "alarm",
  eventType = "BAlarmRecord",
  flags = Flags.SUMMARY
)
public class BAlarmService
  extends BAbstractService
  implements BIAlarmClassFolder, BIDataRecoverySourceService, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmService(1717681336)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmDbConfig"

  /**
   * Slot for the {@code alarmDbConfig} property.
   * Alarm Database Configuration.
   * @see #getAlarmDbConfig
   * @see #setAlarmDbConfig
   */
  public static final Property alarmDbConfig = newProperty(0, new BFileAlarmDbConfig(), null);

  /**
   * Get the {@code alarmDbConfig} property.
   * Alarm Database Configuration.
   * @see #alarmDbConfig
   */
  public BAlarmDbConfig getAlarmDbConfig() { return (BAlarmDbConfig)get(alarmDbConfig); }

  /**
   * Set the {@code alarmDbConfig} property.
   * Alarm Database Configuration.
   * @see #alarmDbConfig
   */
  public void setAlarmDbConfig(BAlarmDbConfig v) { set(alarmDbConfig, v, null); }

  //endregion Property "alarmDbConfig"

  //region Property "defaultAlarmClass"

  /**
   * Slot for the {@code defaultAlarmClass} property.
   * The default alarm class.
   * @see #getDefaultAlarmClass
   * @see #setDefaultAlarmClass
   */
  public static final Property defaultAlarmClass = newProperty(0, new BAlarmClass(), null);

  /**
   * Get the {@code defaultAlarmClass} property.
   * The default alarm class.
   * @see #defaultAlarmClass
   */
  public BAlarmClass getDefaultAlarmClass() { return (BAlarmClass)get(defaultAlarmClass); }

  /**
   * Set the {@code defaultAlarmClass} property.
   * The default alarm class.
   * @see #defaultAlarmClass
   */
  public void setDefaultAlarmClass(BAlarmClass v) { set(defaultAlarmClass, v, null); }

  //endregion Property "defaultAlarmClass"

  //region Property "masterAlarmInstructions"

  /**
   * Slot for the {@code masterAlarmInstructions} property.
   * List of generic instructions that are available to all alarm exts.
   * @see #getMasterAlarmInstructions
   * @see #setMasterAlarmInstructions
   */
  public static final Property masterAlarmInstructions = newProperty(0, BAlarmInstructions.make(""), null);

  /**
   * Get the {@code masterAlarmInstructions} property.
   * List of generic instructions that are available to all alarm exts.
   * @see #masterAlarmInstructions
   */
  public BAlarmInstructions getMasterAlarmInstructions() { return (BAlarmInstructions)get(masterAlarmInstructions); }

  /**
   * Set the {@code masterAlarmInstructions} property.
   * List of generic instructions that are available to all alarm exts.
   * @see #masterAlarmInstructions
   */
  public void setMasterAlarmInstructions(BAlarmInstructions v) { set(masterAlarmInstructions, v, null); }

  //endregion Property "masterAlarmInstructions"

  //region Property "escalationTimeTrigger"

  /**
   * Slot for the {@code escalationTimeTrigger} property.
   * The frequency that alarms should be escalated
   * @see #getEscalationTimeTrigger
   * @see #setEscalationTimeTrigger
   */
  public static final Property escalationTimeTrigger = newProperty(Flags.HIDDEN, new BTimeTrigger(BIntervalTriggerMode.make(BRelTime.makeMinutes(1))), null);

  /**
   * Get the {@code escalationTimeTrigger} property.
   * The frequency that alarms should be escalated
   * @see #escalationTimeTrigger
   */
  public BTimeTrigger getEscalationTimeTrigger() { return (BTimeTrigger)get(escalationTimeTrigger); }

  /**
   * Set the {@code escalationTimeTrigger} property.
   * The frequency that alarms should be escalated
   * @see #escalationTimeTrigger
   */
  public void setEscalationTimeTrigger(BTimeTrigger v) { set(escalationTimeTrigger, v, null); }

  //endregion Property "escalationTimeTrigger"

  //region Property "coalesceAlarms"

  /**
   * Slot for the {@code coalesceAlarms} property.
   * Determines whether to coalesce alarms or not.
   * @see #getCoalesceAlarms
   * @see #setCoalesceAlarms
   */
  public static final Property coalesceAlarms = newProperty(0, true, null);

  /**
   * Get the {@code coalesceAlarms} property.
   * Determines whether to coalesce alarms or not.
   * @see #coalesceAlarms
   */
  public boolean getCoalesceAlarms() { return getBoolean(coalesceAlarms); }

  /**
   * Set the {@code coalesceAlarms} property.
   * Determines whether to coalesce alarms or not.
   * @see #coalesceAlarms
   */
  public void setCoalesceAlarms(boolean v) { setBoolean(coalesceAlarms, v, null); }

  //endregion Property "coalesceAlarms"

  //region Action "routeAlarm"

  /**
   * Slot for the {@code routeAlarm} action.
   * Route an alarm record to recipients.
   * @see #routeAlarm(BAlarmRecord parameter)
   */
  public static final Action routeAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code routeAlarm} action.
   * Route an alarm record to recipients.
   * @see #routeAlarm
   */
  public void routeAlarm(BAlarmRecord parameter) { invoke(routeAlarm, parameter, null); }

  //endregion Action "routeAlarm"

  //region Action "ackAlarm"

  /**
   * Slot for the {@code ackAlarm} action.
   * Route an ack Request to it's source.
   * @see #ackAlarm(BAlarmRecord parameter)
   */
  public static final Action ackAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code ackAlarm} action.
   * Route an ack Request to it's source.
   * @see #ackAlarm
   */
  public void ackAlarm(BAlarmRecord parameter) { invoke(ackAlarm, parameter, null); }

  //endregion Action "ackAlarm"

  //region Action "enableToOffnormal"

  /**
   * Slot for the {@code enableToOffnormal} action.
   * Set into service all of the extensions referenced in the specified list of ords.
   * @see #enableToOffnormal(BVector parameter)
   */
  public static final Action enableToOffnormal = newAction(Flags.HIDDEN, new BVector(), null);

  /**
   * Invoke the {@code enableToOffnormal} action.
   * Set into service all of the extensions referenced in the specified list of ords.
   * @see #enableToOffnormal
   */
  public BOrd enableToOffnormal(BVector parameter) { return (BOrd)invoke(enableToOffnormal, parameter, null); }

  //endregion Action "enableToOffnormal"

  //region Action "disableToOffnormal"

  /**
   * Slot for the {@code disableToOffnormal} action.
   * Set disabled all of the extensions referenced in the specified list of ords.
   * @see #disableToOffnormal(BVector parameter)
   */
  public static final Action disableToOffnormal = newAction(Flags.HIDDEN, new BVector(), null);

  /**
   * Invoke the {@code disableToOffnormal} action.
   * Set disabled all of the extensions referenced in the specified list of ords.
   * @see #disableToOffnormal
   */
  public BOrd disableToOffnormal(BVector parameter) { return (BOrd)invoke(disableToOffnormal, parameter, null); }

  //endregion Action "disableToOffnormal"

  //region Action "enableToFault"

  /**
   * Slot for the {@code enableToFault} action.
   * Set into service all of the extensions referenced in the specified list of ords.
   * @see #enableToFault(BVector parameter)
   */
  public static final Action enableToFault = newAction(Flags.HIDDEN, new BVector(), null);

  /**
   * Invoke the {@code enableToFault} action.
   * Set into service all of the extensions referenced in the specified list of ords.
   * @see #enableToFault
   */
  public BOrd enableToFault(BVector parameter) { return (BOrd)invoke(enableToFault, parameter, null); }

  //endregion Action "enableToFault"

  //region Action "disableToFault"

  /**
   * Slot for the {@code disableToFault} action.
   * Set disabled all of the extensions referenced in the specified list of ords.
   * @see #disableToFault(BVector parameter)
   */
  public static final Action disableToFault = newAction(Flags.HIDDEN, new BVector(), null);

  /**
   * Invoke the {@code disableToFault} action.
   * Set disabled all of the extensions referenced in the specified list of ords.
   * @see #disableToFault
   */
  public BOrd disableToFault(BVector parameter) { return (BOrd)invoke(disableToFault, parameter, null); }

  //endregion Action "disableToFault"

  //region Action "auditForceClear"

  /**
   * Slot for the {@code auditForceClear} action.
   * Creates an audit record in the AuditHistory Database if available.
   * Flagged as no audit since this method create an audit record.
   * @see #auditForceClear(BAlarmRecord parameter)
   */
  public static final Action auditForceClear = newAction(Flags.HIDDEN | Flags.NO_AUDIT, new BAlarmRecord(), null);

  /**
   * Invoke the {@code auditForceClear} action.
   * Creates an audit record in the AuditHistory Database if available.
   * Flagged as no audit since this method create an audit record.
   * @see #auditForceClear
   */
  public void auditForceClear(BAlarmRecord parameter) { invoke(auditForceClear, parameter, null); }

  //endregion Action "auditForceClear"

  //region Action "escalateAlarms"

  /**
   * Slot for the {@code escalateAlarms} action.
   * Creates an audit record in the AuditHistory Database if available.
   * Flagged as no audit since this method create an audit record.
   * @see #escalateAlarms()
   */
  public static final Action escalateAlarms = newAction(Flags.HIDDEN | Flags.NO_AUDIT | Flags.ASYNC, null);

  /**
   * Invoke the {@code escalateAlarms} action.
   * Creates an audit record in the AuditHistory Database if available.
   * Flagged as no audit since this method create an audit record.
   * @see #escalateAlarms
   */
  public void escalateAlarms() { invoke(escalateAlarms, null, null); }

  //endregion Action "escalateAlarms"

  //region Topic "alarm"

  /**
   * Slot for the {@code alarm} topic.
   * Fired whenever an alarm is received (eg. when the alarm is routed to an alarm class).
   * @see #fireAlarm
   */
  public static final Topic alarm = newTopic(Flags.SUMMARY, null);

  /**
   * Fire an event for the {@code alarm} topic.
   * Fired whenever an alarm is received (eg. when the alarm is routed to an alarm class).
   * @see #alarm
   */
  public void fireAlarm(BAlarmRecord event) { fire(alarm, event, null); }

  //endregion Topic "alarm"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Register this component under "alarm:AlarmService".
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static final Type[] serviceTypes = { TYPE };

  /**
   * Service start.
   */
  @Override
  public void serviceStarted()
  {
    if(isOperational())
    {
      //loop through all platform services
      // check to see if platform service implements BIAlarmable
      // if it does, addAlarmListener(platformsvc)
      try
      {
        BPlatformServiceContainer container = (BPlatformServiceContainer)Sys.getService(BPlatformServiceContainer.TYPE);
        BPlatformService[] platSvcs = container.getChildren(BPlatformService.class);

        for (BPlatformService platSvc : platSvcs)
        {
          if (platSvc instanceof BIAlarmablePlatformService)
          {
            ((BIAlarmablePlatformService)platSvc).addPlatformServiceAlarmListener(listener);
          }
        }
      }
      catch (Exception e)
      {
        logger.severe("Cannot setup alarming for platform service\n  " + e);
      }

      if (alarmWorker == null)
      {
        alarmWorker = new Worker(alarmQueue);
      }
      alarmWorker.start("Alarm:ServiceWorker");

      // create and open the alarm database
      initAlarmDb();

      // create the alarmdb channel
      BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
      if(registry.get("alarmdb") == null)
      {
        registry.add("alarmdb", new BAlarmDbChannel());
      }

      // add myself to receive station.save callbacks
      Station.addSaveListener(saveListener);

      try
      {
        BLocalHost.INSTANCE.addNavChild(getAlarmDb());

      }
      catch (IllegalArgumentException iae)
      {
        //Already added the alarm db
      }
    }

    //TODO if already at steady state, then call atSteadyState() to start esc
    // timer
  }

  @Override
  public void atSteadyState()
  {
  }

  /**
   * Service stop.
   */
  @Override
  public void serviceStopped()
  {
    Station.removeSaveListener(saveListener);

    if(alarmWorker != null)
    {
      alarmWorker.stop();
    }

    BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
    if (registry.get("alarmdb") != null)
    {
      registry.remove("alarmdb");
    }

    if(alarmDb != null)
    {
      try
      {
        alarmDb.flush();
      }
      catch (IOException e)
      {
        String msg = "Alarm database save failed";
        if (logger.isLoggable(Level.FINE))
        {
          logger.log(Level.SEVERE, msg, e);
        }
        else
        {
          logger.severe(msg);
        }
      }
      finally
      {
        alarmDb.close();
        alarmDb = null;
      }
    }

    //removeListeners
    BPlatformServiceContainer container = (BPlatformServiceContainer)Sys.getService(BPlatformServiceContainer.TYPE);
    BPlatformService[] platSvcs = container.getChildren(BPlatformService.class);

    for (BPlatformService platSvc : platSvcs)
    {
      if (platSvc instanceof BIAlarmablePlatformService)
      {
        ((BIAlarmablePlatformService)platSvc).removePlatformServiceAlarmListener(listener);
      }
    }
  }

  @Override
  public Feature getLicenseFeature()
  {
    Feature feature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "alarm");
    String alarmType = feature.get("type", "");
    if (alarmType.isEmpty())
    {
      configFatal("Unlicensed for all alarm types. Alarms are disabled.");
    }
    return feature;
  }

  @Override
  protected final void enabled()
  {
    serviceStarted();
    getArchiveAlarmProvider().ifPresent(BArchiveAlarmProvider::initAlarmArchive);
  }

  @Override
  protected final void disabled()
  {
    serviceStopped();
    getArchiveAlarmProvider().ifPresent(BArchiveAlarmProvider::closeAlarmArchive);
  }

  /**
   * Get the AlarmService or throw ServiceNotFoundException.
   * @since Niagara 4.11
   */
  public static BAlarmService getService()
  {
    return (BAlarmService) Sys.getService(TYPE);
  }

////////////////////////////////////////////////////////////////
// Data Recovery
////////////////////////////////////////////////////////////////

  @Override
  public void initDataRecoverySource(BIDataRecoveryService service)
  {
    if(alarmWorker==null)
    {
      alarmWorker = new Worker(alarmQueue);
      alarmWorker.start("Alarm:ServiceWorker");
    }

    // create and open the alarm database
    initAlarmDb();
  }

////////////////////////////////////////////////////////////////
// AlarmDatabase
////////////////////////////////////////////////////////////////

  /**
   * Initialize the alarm database.
   */
  private void initAlarmDb()
  {
    if (!isOperational())
    {
      throw new NotRunningException("The AlarmService is not operational");
    }

    if (alarmDb == null)
    {
      alarmDb = createAlarmDb();

      try
      {
        alarmDb.open();
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, "Cannot open alarm database.", e);
      }
    }

  }

  /**
   * Get the alarm database.
   */
  public final BAlarmDatabase getAlarmDb()
    throws AlarmException
  {
    if (alarmDb == null)
    {
      throw new AlarmException("Alarm database is not open.");
    }
    return alarmDb;
  }

  /**
   * Get the Archive Alarm Provider configured on the Alarm Service,
   * if any and is operational
   * @return the instance of the archive alarm provider
   * @since Niagara 4.11
   */
  private Optional<BArchiveAlarmProvider> getArchiveAlarmProvider()
  {
    BArchiveAlarmProvider[] archiveProviders = getChildren(BArchiveAlarmProvider.class);
    if (archiveProviders.length > 0 && archiveProviders[0].isOperational())
    {
      return Optional.of(archiveProviders[0]);
    }
    return Optional.empty();
  }

  /**
   * Get the AlarmArchive present on the AlarmArchiveProvider, if present.
   * @return The AlarmArchive instance present on the alarm archive provider.
   * @since Niagara 4.11
   */
  public final Optional<BAlarmArchive> getAlarmArchive()
  {
    Optional<BArchiveAlarmProvider> provider = getArchiveAlarmProvider();
    if (provider.isPresent())
    {
      return Optional.of(provider.get().getAlarmArchive());
    }
    return Optional.empty();
  }

  protected BAlarmDatabase createAlarmDb()
  {
    return new BFileAlarmDatabase();
  }

  /**
   * Notify the AlarmService that the AlarmDbConfig has changed. 
   * @since Niagara 4.0
   */
  protected void alarmDbConfigChanged(Property p)
    throws AlarmException
  {
    getAlarmDb().updateConfig(getAlarmDbConfig(), p);
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  @Override
  public IFuture post(Action action, BValue argument, Context cx)
  {
    if (action.equals(escalateAlarms))
    {
      alarmQueue.enqueue(new Invocation(this, action, null, cx));
      return null;
    }
    else
    {
      return super.post(action, argument, cx);
    }
  }

  public void doEscalateAlarms()
  {
    if (!isRunning() || !isOperational())
    {
      return;
    }

    try
    {
      // Build a query to get all open alarms for classes that are using escalation
      StringBuilder bql = new StringBuilder("alarm:|bql:select from openAlarms ");

      int classCount = 0;
      boolean escalationInUse = false;
      for (BAlarmClass alarmClass : getAlarmClasses())
      {
        if (alarmClass.getEscalationLevel1Enabled() ||
          alarmClass.getEscalationLevel2Enabled() ||
          alarmClass.getEscalationLevel3Enabled())
        {
          escalationInUse = true;

          if (classCount != 0)
          {
            bql.append("or ");
          }
          else
          {
            bql.append("where (");
          }
          ++classCount;

          bql.append("alarmClass='");
          bql.append(SlotPath.escape(alarmClass.getName()));
          bql.append("' ");
        }
      }

      if (classCount > 0)
      {
        bql.append(')');
      }

      // If no classes are using escalation, then we don't need to
      // continue
      if (!escalationInUse)
      {
        return;
      }

      // Execute the query
      @SuppressWarnings("unchecked") BITable<BAlarmRecord> table = (BITable<BAlarmRecord>) BOrd.make(bql.toString()).get(this);
      // Storage for alarms needing to be updated
      Map<BUuid, BAlarmRecord> updates = new HashMap<>();
      try (Cursor<BAlarmRecord> cur = table.cursor())
      {
        while (cur.next())
        {
          BAlarmRecord record = cur.get();
          if (record.getAckState() == BAckState.unacked)
          {
            BAlarmClass alarmClass = lookupAlarmClass(record.getAlarmClass());

            BObject escalatedValue = record.getAlarmData().get(BAlarmClass.ESCALATED);
            if (alarmClass.getEscalationLevel1Enabled() && escalatedValue.equals(BString.DEFAULT))
            {
              // if it's never been escalated, we can check level 1
              if (record.getTimestamp().add(alarmClass.getEscalationLevel1Delay()).isBefore(BAbsTime.now()))
              {
                record.addAlarmFacet(BAlarmClass.ESCALATED, BString.make(BAlarmClass.LEVEL_1));
                updates.put(record.getUuid(), (BAlarmRecord)record.newCopy());
                if (logger.isLoggable(Level.FINE))
                {
                  logger.fine("Escalating " + record.getUuid() + " to level 1");
                }
              }
            }
            else if (alarmClass.getEscalationLevel2Enabled() &&
              (escalatedValue.equals(LEVEL_1_STRING) || escalatedValue.equals(BString.DEFAULT)))
            {
              // if it's at level 1 (or 0), we can check level 2
              if (record.getTimestamp().add(alarmClass.getEscalationLevel2Delay()).isBefore(BAbsTime.now()))
              {
                // escalate the alarm
                record.addAlarmFacet(BAlarmClass.ESCALATED, BString.make(BAlarmClass.LEVEL_2));
                updates.put(record.getUuid(), (BAlarmRecord)record.newCopy());
                if (logger.isLoggable(Level.FINE))
                {
                  logger.fine("Escalating " + record.getUuid() + " to level 2");
                }
              }
            }
            else if (alarmClass.getEscalationLevel3Enabled() &&
              (escalatedValue.equals(LEVEL_2_STRING) || escalatedValue.equals(LEVEL_1_STRING) || escalatedValue.equals(BString.DEFAULT)))
            {
              // if it's at level 2 (or 1 or 0), we can check level 3
              if (record.getTimestamp().add(alarmClass.getEscalationLevel3Delay()).isBefore(BAbsTime.now()))
              {
                // escalate the alarm
                record.addAlarmFacet(BAlarmClass.ESCALATED, BString.make(BAlarmClass.LEVEL_3));
                updates.put(record.getUuid(), (BAlarmRecord)record.newCopy());
                if (logger.isLoggable(Level.FINE))
                {
                  logger.fine("Escalating " + record.getUuid() + " to level 3");
                }
              }
            }
          }
        }
      }

      try (AlarmDbConnection connection = getAlarmDb().getDbConnection(null))
      {
        for (BAlarmRecord record : updates.values())
        {
          connection.update(record);

          BAlarmClass alarmClass = lookupAlarmClass(record.getAlarmClass());

          BObject escalatedValue = record.getAlarmData().get(BAlarmClass.ESCALATED);
          if (escalatedValue.equals(LEVEL_1_STRING))
          {
            alarmClass.fireAlarm(record);
            alarmClass.fireEscalatedAlarm1(record);
          }
          else if (escalatedValue.equals(LEVEL_2_STRING))
          {
            alarmClass.fireAlarm(record);
            alarmClass.fireEscalatedAlarm1(record);
            alarmClass.fireEscalatedAlarm2(record);
          }
          else if (escalatedValue.equals(LEVEL_3_STRING))
          {
            alarmClass.fireAlarm(record);
            alarmClass.fireEscalatedAlarm1(record);
            alarmClass.fireEscalatedAlarm2(record);
            alarmClass.fireEscalatedAlarm3(record);
          }
        }
      }
    }
    catch(Exception e)
    {
      logThrowable("Exception while checking for alarms to escalate", e);
    }
  }

  /**
   * If an ack is required, route the alarm to the source.
   * Otherwise, set it to be acked and route it back to the recipients.
   */
  public void doAckAlarm(BAlarmRecord alarm)
    throws Exception
  {
    BObject source = null;
    try
    {
      // Resolve the source of the alarm so we can check the source's type.
      source = resolveAlarmSource(alarm);
    }
    catch (UnresolvedException e)
    {
      //ignore this Exception since doRouteToSource will handle it
    }

    if (source instanceof BIRemoteAlarmSource)
    {
      //Issue 13921
      doRouteToSource(alarm, source);
    }
    else
    {
      if (alarm.getAckRequired())
      {
        doRouteToSource(alarm, source);
      }
      else
      {
        alarm.setAckState(BAckState.acked);
        doRouteToRecipient(alarm);
      }
    }
  }

  /**
   * Route an alarm record to Recipients.
   *
   * @param alarm The alarm to route.
   */
  public void doRouteAlarm(BAlarmRecord alarm)
    throws Exception
  {
    if(!isRunning() || !isOperational())
    {
      return;
    }

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine("BAlarmService.doRouteAlarm: " + alarm.getSourceState() + ' ' + alarm.getAckState() + ' ' + alarm.getTimestamp() + ' ' + alarm.getAlarmClass());
    }

    doRouteToRecipient(alarm);
  }

  /**
   * Route an alarm record to the required recipient.
   *
   * @param alarm The alarm to route.
   */
  public void doRouteToRecipient(BAlarmRecord alarm)
  {
    // Get the alarm class from the alarm record.
    BAlarmClass ac = lookupAlarmClass(alarm.getAlarmClass());

    // Route the record to that alarm class.
    ac.routeAlarm(alarm);

    fireAlarm(alarm);
  }

  public void doRouteToSource(BAlarmRecord alarm)
    throws Exception
  {
    BObject source = null;
    try
    {
      // Resolve the source of the alarm so we can check the source's type.
      source = resolveAlarmSource(alarm);
    }
    catch (UnresolvedException e)
    {
      //ignore this Exception since doRouteToSource will handle it
    }
    doRouteToSource(alarm, source);
  }
  /**
   * Route an alarm record to the source of the alarm.
   *
   * @param alarm The alarm to route.
   */
  public void doRouteToSource(BAlarmRecord alarm, BObject source)
    throws Exception
  {
    // is this an alarm ack?
    boolean isAck = alarm.getAckState() == BAckState.ackPending ||
      (alarm.getAckState() == BAckState.acked && alarm.getAckRequired());

    if (source == null)
    {
      logger.severe("BAlarmService.doRouteToSource cannot resolve alarm source " + alarm.getSource());

      if (isAck)
      {
        alarm.setAckState(BAckState.acked);
        alarm.setAckTime(BAbsTime.now());
        alarm.setAckRequired(false);
        alarm.addAlarmFacet("autoAcked", BString.make("Cannot resolve: " + alarm.getSource()));
        doRouteToRecipient(alarm);
      }

      return;
    }

    // If it's an ack request, have the source ack the alarm.
    if (logger.isLoggable(Level.FINE))
    {
      logger.fine("BAlarmService.doRouteToSource: " + source);
    }

    if (source instanceof BIAlarmSource)
    {
      try
      {
        if (isAck)
        {
          ((BIAlarmSource) source).ackAlarm(alarm);
        }
        else if (source instanceof BIUpdatableAlarmSource)
        {
          BIUpdatableAlarmSource ext = (BIUpdatableAlarmSource)source;
          ext.updateAlarm(alarm);
        }
      }
      catch(Exception e)
      {
        if (e instanceof BajaRuntimeException)
        {
          throw (Exception)e.getCause();
        }
        else
        {
          throw e;
        }
      }
    }
    else if (isAck && source instanceof BIAlarmablePlatformService)
    {
      //  Translate BAlarmTransition to BPlatformServiceAlarmState
      BPlatformServiceSourceState transState;
      BSourceState state = alarm.getAlarmTransition();
      if (state == BSourceState.offnormal)
      {
        transState = BPlatformServiceSourceState.offnormal;
      }
      else if (state == BSourceState.fault)
      {
        transState = BPlatformServiceSourceState.fault;
      }
      else if (state == BSourceState.alert)
      {
        transState = BPlatformServiceSourceState.alert;
      }
      else
      {
        throw new IllegalStateException();
      }

      BOrdList stackedSource = alarm.getSource();
      BOrd localSource = stackedSource.get(stackedSource.size()-1);

      BPlatformServiceAlarmRecord record = new BPlatformServiceAlarmRecord(localSource,
        alarm.getAlarmClass(),
        transState,
        alarm.getAlarmData());
      record.setTimestamp(alarm.getTimestamp());

      ((BIAlarmablePlatformService) source).ackAlarm(record);

      alarm.setAckState(BAckState.acked);
      alarm.setAckTime(BAbsTime.now());
      alarm.setAckRequired(false);
      alarm.setAlarmClass(record.getAlarmClass());

      doRouteToRecipient(alarm);
    }
  }

  /**
   * Enable the specified history extensions.
   */
  public BOrd doEnableToOffnormal(BVector extOrds, Context cx)
  {
    return new BAlarmExtStatusJob(this, extOrds, BAlarmTransitionBits.toOffnormal, true).submit(cx);
  }

  /**
   * Disable the specified history extensions.
   */
  public BOrd doDisableToOffnormal(BVector extOrds, Context cx)
  {
    return new BAlarmExtStatusJob(this, extOrds, BAlarmTransitionBits.toOffnormal, false).submit(cx);
  }

  /**
   * Enable the specified history extensions.
   */
  public BOrd doEnableToFault(BVector extOrds, Context cx)
  {
    return new BAlarmExtStatusJob(this, extOrds, BAlarmTransitionBits.toFault, true).submit(cx);
  }

  /**
   * Disable the specified history extensions.
   */
  public BOrd doDisableToFault(BVector extOrds, Context cx)
  {
    return new BAlarmExtStatusJob(this, extOrds, BAlarmTransitionBits.toFault, false).submit(cx);
  }

  public void doAuditForceClear(BAlarmRecord rec, Context cx)
  {
    Auditor auditor = Sys.getAuditor();
    if (auditor != null)
    {
      BUser user = cx.getUser();
      String username = "";
      if (user != null)
      {
        username = user.getUsername();
      }

      user.getUsername();
      auditor.audit(new AuditEvent("Force Clear Alarm",
                                   rec.getSource().toString(),
                                   rec.getUuid().toString(),
                                   "",
                                   "",
                                   username));
    }
  }

////////////////////////////////////////////////////////////////
// AlarmClass management
////////////////////////////////////////////////////////////////

  /**
   * Return an array of all alarm classes in the AlarmService and all AlarmClassFolders
   */
  public BAlarmClass[] getAlarmClasses()
  {
    return loadFromFolder(this);
  }

  private static BAlarmClass[] loadFromFolder(BComponent folder)
  {
    Array<BAlarmClass> alarmClasses = new Array<>(BAlarmClass.class);
    folder.lease();
    BAlarmClass[] direct = folder.getChildren(BAlarmClass.class);
    for (BAlarmClass aDirect : direct)
    {
      alarmClasses.add(aDirect);
    }
    BAlarmClassFolder[] folders = folder.getChildren(BAlarmClassFolder.class);
    for (BAlarmClassFolder aFolder : folders)
    {
      loadFromFolder(aFolder);
      BAlarmClass[] indirect = loadFromFolder(aFolder);
      for (BAlarmClass anIndirect : indirect)
      {
        alarmClasses.add(anIndirect);
      }
    }
    return alarmClasses.trim();
  }

  /**
   * Resolve an alarm class name to an alarm class.
   *
   * @param alarmClassName The path to the alarm class.
   * @return Returns BAlarmService.defaultAlarmClass if no alarm class with the specified name can be found.
   */
  public BAlarmClass lookupAlarmClass(String alarmClassName)
  {
    BAlarmClass ac = lookupAlarmClass(this, alarmClassName);
    if (ac == null)
    {
      ac = getDefaultAlarmClass();
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine("BAlarmService.lookupAlarmClass can't find alarm class <" +
                    alarmClassName + "> using default <" + ac.getName() + '>');
      }

    }
    else
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine("BAlarmService.lookupAlarmClass: will route alarm with " + ac);
      }
    }
    return ac;
  }

  protected BAlarmClass lookupAlarmClass(BComponent folder, String alarmClassName)
  {
    folder.lease();
    Property acp = folder.getProperty(alarmClassName);
    if (acp == null || !folder.get(acp).getType().is(BAlarmClass.TYPE))
    {
      //check all the AlarmClassFolders
      BAlarmClassFolder[] folders = folder.getChildren(BAlarmClassFolder.class);
      for (BAlarmClassFolder alarmClassFolder : folders)
      {
        BAlarmClass temp = lookupAlarmClass(alarmClassFolder, alarmClassName);
        if (temp != null)
        {
          return temp;
        }
      }
    }
    else
    {
      return (BAlarmClass)folder.get(acp);
    }
    return null;
  }

  /**
   * Utility method for finding display names of alarm classes.
   * If invoked on the client side and the server is running alarm-3.7.35 or greater, will make an RPC call.
   *
   * @since Niagara 3.7
   * @param alarmClass BAlarmClass name
   * @param cx the context
   * @return Returns getDisplayName(cx) of BAlarmClass with name alarmClass of BAlarmService.getDefaultAlarmClass().getDisplayName(c) if alarmClass does not exist or remote station does not support RPC call.
   */
  @NiagaraRpc(permissions = "unrestricted", transports = @Transport(type = TransportType.fox))
  public BString getAlarmClassDisplayName(Object alarmClass, Context cx)
  {
    BComponentSpace space = getComponentSpace();
    if (space != null && space.isProxyComponentSpace())
    {
      if (remoteVersionGtOrEq("3.7.35"))
      {
        try
        {
          return FoxRpcUtil.<BString>doRpc(this, "getAlarmClassDisplayName", BString.make(alarmClass.toString())).get();
        }
        catch(Exception ignore) {}
      }
      return BString.make(alarmClass.toString());
    }
    else
    {
      String displayName = alarmClassDisplayNames.get(alarmClass.toString());
      if (displayName == null)
      {
        displayName = lookupAlarmClass(alarmClass.toString()).getDisplayName(cx);
        alarmClassDisplayNames.put(alarmClass, displayName);
      }
      return BString.make(displayName);
    }
  }
  HashMap<Object, String> alarmClassDisplayNames = new HashMap<>();
  private Version remoteVersion = null;


  /**
   * Utility method for retrieving a name/displayName map with an entry for each BAlarmClass in BAlarmService, and
   * recursively in its BAlarmFolder(s). If invoked on the client side and the server is running alarm-4.9.0 or
   * greater, will make an RPC call.
   *
   * @since Niagara 4.9
   * @param cx the context
   * @return Returns {@code Map<alarmClass.getName(), alarmClass.getDisplayName(cx)>} for each BAlarmClass found in the
   * BAlarmService. If the remote station does not support RPC call an empty map is returned.
   */
  @NiagaraRpc(permissions = "unrestricted", transports = @Transport(type = TransportType.fox))
  public Map<String, String> getAlarmClassDisplayNameMap(Context cx)
  {
    Map<String, String> alarmClassDisplayNameMap = new HashMap<String, String>();

    BComponentSpace space = getComponentSpace();
    if (space != null && space.isProxyComponentSpace())
    {
      if (remoteVersionGtOrEq("4.9.0"))
      {
        try
        {
          alarmClassDisplayNameMap = FoxRpcUtil.<Map<String, String>>doRpc(this, "getAlarmClassDisplayNameMap").get();
        }
        catch(Exception ignore) {}
      }
    }
    else
    {
      addAlarmClassesToDisplayNameMap(alarmClassDisplayNameMap, this, cx);
    }

    return alarmClassDisplayNameMap;
  }

  /**
   * Resolve source object for silence / ack requests
   */
  private BObject resolveAlarmSource(BAlarmRecord req)
    throws Exception
  {
    BObject source = null;

    BOrdList stackedSource = req.getSource();
    BOrd localSource = stackedSource.get(stackedSource.size()-1);

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine("BAlarmService::resolveAlarmSource = " + localSource);
    }
    source = localSource.get(this);

    if (logger.isLoggable(Level.FINE))
    {
      logger.fine("  BAlarmService::resolveAlarmSource resolved = " + source);
    }
    return source;
  }

  /**
   * Recursively search for BAlarmClass(es) in a folder and add <name, displayName> to to a map.
   *
   * @since Niagara 4.9
   * @param map the Map<String, String> to be populated.
   * @param folder the folder to search.
   * @param cx the context
   */
  private void addAlarmClassesToDisplayNameMap(Map<String, String> map, BComponent folder, Context cx)
  {
    BAlarmClass[] alarmClasses = folder.getChildren(BAlarmClass.class);
    for (BAlarmClass alarmClass : alarmClasses)
    {
      if (cx != null && cx.getUser() != null && cx.getUser().getPermissionsFor(alarmClass).hasOperatorRead())
      {
        map.put(alarmClass.getName(), alarmClass.getDisplayName(cx));
      }
    }

    BAlarmClassFolder[] alarmClassFolders = folder.getChildren(BAlarmClassFolder.class);
    for (BAlarmClassFolder alarmClassFolder : alarmClassFolders)
    {
      addAlarmClassesToDisplayNameMap(map, alarmClassFolder, cx);
    }
  }

  /**
   * Utility method to compare the remote version of the alarm module to the version string passed in as a parameter.
   *
   * @since Niagara 4.9
   * @param versionString the version string to compare against the remote version of the alarm module.
   * @return Returns boolean: true if the remote version of the alarm module is greater than or equal to the
   * versionString parameter passed in as a parameter, otherwise false.
   */
  private boolean remoteVersionGtOrEq(String versionString)
  {
    boolean result = false;

    if (remoteVersion == null)
    {
      try
      {
        remoteVersion  = (Version)((BFoxSession)getSession()).fw(Fw.GET_REMOTE_VERSION, "alarm", null, null, null);
      }
      catch(Exception ignore) {}
    }
    if (remoteVersion != null && !remoteVersion.isNull())
    {
      result = remoteVersion.compareTo(new Version(versionString)) >= 0;
    }

    return result;
  }

////////////////////////////////////////////////////////////////
// Component callbacks
////////////////////////////////////////////////////////////////

  /**
   * Disallow duplicate alarm classes
   */
  @Override
  public void checkAdd(String newName, BValue value, int flags, BFacets facets,
      Context context)
  {
    if(isRunning() && isOperational() && value.getType().is(BAlarmClass.TYPE))
    {
      BAlarmClass existingClass = lookupAlarmClass(newName);
      if(newName.equals("defaultAlarmClass") || !existingClass.getName().equals("defaultAlarmClass"))
      {
        throw new LocalizableRuntimeException("alarm","DuplicateAlarmClass",new String[] {newName});
      }
    }
    super.checkAdd(newName, value, flags, facets, context);
  }

  /**
   * Disallow duplicate alarm classes
   */
  @Override
  public void checkRename(Property property, String newName, Context context)
  {
    if(property.getType().is(BAlarmClass.TYPE))
    {
      BAlarmClass existingClass = lookupAlarmClass(newName);
      if(newName.equals("defaultAlarmClass") || !existingClass.getName().equals("defaultAlarmClass"))
      {
        throw new IllegalNameException("alarm","DuplicateAlarmClass",new String[] {newName});
      }
      alarmClassDisplayNames.clear();
    }
    super.checkRename(property, newName, context);
  }

  @Override
  public void changed(Property prop, Context cx)
  {
    if (prop.getName().equals("displayNames"))
    {
      alarmClassDisplayNames.clear();
    }
  }

  @Override
  public void added(Property prop, Context cx)
  {
    if (prop.getName().equals("displayNames"))
    {
      alarmClassDisplayNames.clear();
    }
  }

  @Override
  public void removed(Property prop, BValue oldValue, Context cx)
  {
    if (prop.getName().equals("displayNames"))
    {
      alarmClassDisplayNames.clear();
    }
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only allowed to live under the station's BServiceContainer and
   * no duplicates of the exact same type.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentIsServiceContainer(parent, this);
    BIRestrictedComponent.checkForDuplicates(parent, this, /*allowNonExactTypes*/true);
  }

////////////////////////////////////////////////////////////////
//  Presentation
////////////////////////////////////////////////////////////////

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.toTop("wiresheet:WebWiresheet");
    list.toTop("wiresheet:WireSheet");
    return PxUtil.movePxViewsToTop(BAlarmDatabase.filterAgents(list));
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/alarmService.png");

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED:
        add(null,
            new BLink(getEscalationTimeTrigger().getOrdInSession(),"fireTrigger","escalateAlarms",true),
            Flags.HIDDEN | Flags.TRANSIENT);
        break;
      case Fw.RR:
        if (alarmDb != null)
        {
          alarmDb.fw(x, a, b, c, d);
        }
        break;
      case Fw.GET_ALARM_QUEUE:
        return alarmQueue;
    }
    return super.fw(x, a, b, c, d);
  }

////////////////////////////////////////////////////////////////
// PlatformServiceListener
////////////////////////////////////////////////////////////////

  class PlatformServiceListener
    implements PlatformServiceAlarmListener
  {
    @Override
    public void platformServiceAlarm(BPlatformServiceAlarmRecord platformServiceAlarm)
    {
      if (!isRunning())
      {
        throw new NotRunningException("Alarm Service is not running, unable to process platform alarm record");
      }

      //  Translate platform service alarm transition to BAlarmTransition
      BPlatformServiceSourceState pt = platformServiceAlarm.getSourceState();
      BSourceState sourceState;

      if (pt == BPlatformServiceSourceState.normal)
      {
        platformServiceToNormal(platformServiceAlarm);
        return;
      }

      if (pt == BPlatformServiceSourceState.offnormal)
      {
        sourceState = BSourceState.offnormal;
      }
      else if (pt == BPlatformServiceSourceState.fault)
      {
        sourceState = BSourceState.fault;
      }
      else if (pt == BPlatformServiceSourceState.alert)
      {
        sourceState = BSourceState.alert;
      }
      else
      {
        throw new IllegalStateException();
      }

      BAlarmRecord alarm = null;
      try (AlarmDbConnection conn = getAlarmDb().getDbConnection(null))
      {
        alarm = conn.getRecord(platformServiceAlarm.getUuid());
      }
      catch (Exception e) { }
      if (alarm == null)
      {
        //  Create alarm record
        alarm = new BAlarmRecord(platformServiceAlarm.getSource(),
                                 platformServiceAlarm.getAlarmClass(),
                                 platformServiceAlarm.getAlarmData(),
                                 BUuid.DEFAULT);
        alarm.setUuid(platformServiceAlarm.getUuid());
        alarm.setTimestamp(platformServiceAlarm.getTimestamp());
      }

      alarm.setSourceState(sourceState);
      alarm.setAlarmTransition(sourceState);
      //ackState is set in doAckAlarm

      routeAlarm(alarm);
    }

    public void platformServiceToNormal(BPlatformServiceAlarmRecord platformServiceAlarm)
    {
      if (!isRunning())
      {
        throw new NotRunningException("Alarm Service is not running, unable to process platform toNormal record");
      }

      try (AlarmDbConnection conn = getAlarmDb().getDbConnection(null);
           Cursor<BAlarmRecord> cur = conn.getAlarmsForSource(BOrdList.make(platformServiceAlarm.getSource())))
      {
        while (cur.next())
        {
          BAlarmRecord alarm = cur.get();
          if (!alarm.isNormal())
          {
            // Make the record normal and route it to recipients
            alarm.setAlarmClass(platformServiceAlarm.getAlarmClass());
            alarm.setAlarmData(BFacets.make(alarm.getAlarmData(), platformServiceAlarm.getAlarmData()));
            alarm.setNormalTime(platformServiceAlarm.getTimestamp());
            alarm.setSourceState(BSourceState.normal);
            routeAlarm((BAlarmRecord) alarm.newCopy());
          }
        }
      }
      catch(Exception e) { logger.log(Level.SEVERE, "Unable to generate toNormal PlatformServiceAlarm for: " + platformServiceAlarm.getSource(), e); }
    }
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Include worker's spy in diagnostics.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BAlarmService", 2);
    out.prop("workInAlarmQueue", String.valueOf(alarmQueue != null ? alarmQueue.size() : 0));
    out.endProps();
    if (alarmWorker != null)
    {
      alarmWorker.spy(out);
    }
  }

////////////////////////////////////////////////////////////////
//  Utilities
////////////////////////////////////////////////////////////////

  private static void logThrowable(String message, Throwable thrown)
  {
    if (logger.isLoggable(Level.FINE))
    {
      logger.log(Level.FINE, message, thrown);
    }
    else
    {
      logger.warning(message);
    }
  }

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  public static final Logger logger = Logger.getLogger("alarm");

  private static final BString LEVEL_1_STRING = BString.make(BAlarmClass.LEVEL_1);
  private static final BString LEVEL_2_STRING = BString.make(BAlarmClass.LEVEL_2);
  private static final BString LEVEL_3_STRING = BString.make(BAlarmClass.LEVEL_3);

  private final PlatformServiceListener listener = new PlatformServiceListener();
  private BAlarmDatabase alarmDb;
  private final Queue alarmQueue = new CoalesceQueue();
  private Worker alarmWorker;

  private final Station.SaveListener saveListener = new Station.SaveListener()
  {
    @Override
    public void stationSave()
    {
      try
      {
        getAlarmDb().save();
      }
      catch(Exception e)
      {
        BAlarmDatabase.log.log(Level.SEVERE, "Alarm database save failed.", e);
      }
    }
    @Override
    public void stationSaveOk() {}
    @Override
    public void stationSaveFail(String cause) {}
    @Override
    public String toString() { return "AlarmService " + getNavOrd(); }
  };
}
