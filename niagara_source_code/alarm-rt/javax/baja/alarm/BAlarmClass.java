/*
 * Copyright 2000-2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.util.IFuture;
import javax.baja.util.Queue;

import com.tridium.alarm.AlarmClassRouteAlarmInvocation;
import com.tridium.sys.schema.Fw;

/**
 * A BAlarmClass object is used to group alarms that have
 * the same routing/handling characteristics.
 * <p>
 * All AlarmSource objects within the framework implicitly
 * reference a BAlarmClass instance.  If the BAlarmClass
 * instance is invalid or missing, the default BAlarmClass
 * will be used.
 *
 * @author    Dan Giorgis
 * @creation  19 Feb 01
 * @version   $Revision: 84$ $Date: 8/31/10 10:14:25 AM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
/*
 Alarm transition types that require a user
 acknowledgement
 */
@NiagaraProperty(
  name = "ackRequired",
  type = "BAlarmTransitionBits",
  defaultValue = "BAlarmTransitionBits.make(BAlarmTransitionBits.TO_OFFNORMAL | BAlarmTransitionBits.TO_FAULT | BAlarmTransitionBits.TO_NORMAL)"
)
/*
 Priority assigned to each transition type
 */
@NiagaraProperty(
  name = "priority",
  type = "BAlarmPriorities",
  defaultValue = "BAlarmPriorities.DEFAULT"
)
@NiagaraProperty(
  name = "totalAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "openAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "inAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "unackedAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "timeOfLastAlarm",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "escalationLevel1Enabled",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "escalationLevel1Delay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(5*60*1000)",
  facets = @Facet("BFacets.make(BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(60*1000))")
)
@NiagaraProperty(
  name = "escalationLevel2Enabled",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "escalationLevel2Delay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(15*60*1000)",
  facets = @Facet("BFacets.make(BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(2*60*1000))")
)
@NiagaraProperty(
  name = "escalationLevel3Enabled",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "escalationLevel3Delay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(30*60*1000)",
  facets = @Facet("BFacets.make(BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(3*60*1000))")
)
/*
 Route an alarm record
 */
@NiagaraAction(
  name = "routeAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  flags = Flags.HIDDEN | Flags.ASYNC
)
@NiagaraTopic(
  name = "alarm",
  eventType = "BAlarmRecord",
  flags = Flags.SUMMARY
)
@NiagaraTopic(
  name = "escalatedAlarm1",
  eventType = "BAlarmRecord"
)
@NiagaraTopic(
  name = "escalatedAlarm2",
  eventType = "BAlarmRecord"
)
@NiagaraTopic(
  name = "escalatedAlarm3",
  eventType = "BAlarmRecord"
)
public class BAlarmClass
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmClass(2753820458)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ackRequired"

  /**
   * Slot for the {@code ackRequired} property.
   * Alarm transition types that require a user
   * acknowledgement
   * @see #getAckRequired
   * @see #setAckRequired
   */
  public static final Property ackRequired = newProperty(0, BAlarmTransitionBits.make(BAlarmTransitionBits.TO_OFFNORMAL | BAlarmTransitionBits.TO_FAULT | BAlarmTransitionBits.TO_NORMAL), null);

  /**
   * Get the {@code ackRequired} property.
   * Alarm transition types that require a user
   * acknowledgement
   * @see #ackRequired
   */
  public BAlarmTransitionBits getAckRequired() { return (BAlarmTransitionBits)get(ackRequired); }

  /**
   * Set the {@code ackRequired} property.
   * Alarm transition types that require a user
   * acknowledgement
   * @see #ackRequired
   */
  public void setAckRequired(BAlarmTransitionBits v) { set(ackRequired, v, null); }

  //endregion Property "ackRequired"

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * Priority assigned to each transition type
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(0, BAlarmPriorities.DEFAULT, null);

  /**
   * Get the {@code priority} property.
   * Priority assigned to each transition type
   * @see #priority
   */
  public BAlarmPriorities getPriority() { return (BAlarmPriorities)get(priority); }

  /**
   * Set the {@code priority} property.
   * Priority assigned to each transition type
   * @see #priority
   */
  public void setPriority(BAlarmPriorities v) { set(priority, v, null); }

  //endregion Property "priority"

  //region Property "totalAlarmCount"

  /**
   * Slot for the {@code totalAlarmCount} property.
   * @see #getTotalAlarmCount
   * @see #setTotalAlarmCount
   */
  public static final Property totalAlarmCount = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code totalAlarmCount} property.
   * @see #totalAlarmCount
   */
  public int getTotalAlarmCount() { return getInt(totalAlarmCount); }

  /**
   * Set the {@code totalAlarmCount} property.
   * @see #totalAlarmCount
   */
  public void setTotalAlarmCount(int v) { setInt(totalAlarmCount, v, null); }

  //endregion Property "totalAlarmCount"

  //region Property "openAlarmCount"

  /**
   * Slot for the {@code openAlarmCount} property.
   * @see #getOpenAlarmCount
   * @see #setOpenAlarmCount
   */
  public static final Property openAlarmCount = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code openAlarmCount} property.
   * @see #openAlarmCount
   */
  public int getOpenAlarmCount() { return getInt(openAlarmCount); }

  /**
   * Set the {@code openAlarmCount} property.
   * @see #openAlarmCount
   */
  public void setOpenAlarmCount(int v) { setInt(openAlarmCount, v, null); }

  //endregion Property "openAlarmCount"

  //region Property "inAlarmCount"

  /**
   * Slot for the {@code inAlarmCount} property.
   * @see #getInAlarmCount
   * @see #setInAlarmCount
   */
  public static final Property inAlarmCount = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code inAlarmCount} property.
   * @see #inAlarmCount
   */
  public int getInAlarmCount() { return getInt(inAlarmCount); }

  /**
   * Set the {@code inAlarmCount} property.
   * @see #inAlarmCount
   */
  public void setInAlarmCount(int v) { setInt(inAlarmCount, v, null); }

  //endregion Property "inAlarmCount"

  //region Property "unackedAlarmCount"

  /**
   * Slot for the {@code unackedAlarmCount} property.
   * @see #getUnackedAlarmCount
   * @see #setUnackedAlarmCount
   */
  public static final Property unackedAlarmCount = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code unackedAlarmCount} property.
   * @see #unackedAlarmCount
   */
  public int getUnackedAlarmCount() { return getInt(unackedAlarmCount); }

  /**
   * Set the {@code unackedAlarmCount} property.
   * @see #unackedAlarmCount
   */
  public void setUnackedAlarmCount(int v) { setInt(unackedAlarmCount, v, null); }

  //endregion Property "unackedAlarmCount"

  //region Property "timeOfLastAlarm"

  /**
   * Slot for the {@code timeOfLastAlarm} property.
   * @see #getTimeOfLastAlarm
   * @see #setTimeOfLastAlarm
   */
  public static final Property timeOfLastAlarm = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code timeOfLastAlarm} property.
   * @see #timeOfLastAlarm
   */
  public BAbsTime getTimeOfLastAlarm() { return (BAbsTime)get(timeOfLastAlarm); }

  /**
   * Set the {@code timeOfLastAlarm} property.
   * @see #timeOfLastAlarm
   */
  public void setTimeOfLastAlarm(BAbsTime v) { set(timeOfLastAlarm, v, null); }

  //endregion Property "timeOfLastAlarm"

  //region Property "escalationLevel1Enabled"

  /**
   * Slot for the {@code escalationLevel1Enabled} property.
   * @see #getEscalationLevel1Enabled
   * @see #setEscalationLevel1Enabled
   */
  public static final Property escalationLevel1Enabled = newProperty(0, false, null);

  /**
   * Get the {@code escalationLevel1Enabled} property.
   * @see #escalationLevel1Enabled
   */
  public boolean getEscalationLevel1Enabled() { return getBoolean(escalationLevel1Enabled); }

  /**
   * Set the {@code escalationLevel1Enabled} property.
   * @see #escalationLevel1Enabled
   */
  public void setEscalationLevel1Enabled(boolean v) { setBoolean(escalationLevel1Enabled, v, null); }

  //endregion Property "escalationLevel1Enabled"

  //region Property "escalationLevel1Delay"

  /**
   * Slot for the {@code escalationLevel1Delay} property.
   * @see #getEscalationLevel1Delay
   * @see #setEscalationLevel1Delay
   */
  public static final Property escalationLevel1Delay = newProperty(0, BRelTime.make(5*60*1000), BFacets.make(BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(60*1000)));

  /**
   * Get the {@code escalationLevel1Delay} property.
   * @see #escalationLevel1Delay
   */
  public BRelTime getEscalationLevel1Delay() { return (BRelTime)get(escalationLevel1Delay); }

  /**
   * Set the {@code escalationLevel1Delay} property.
   * @see #escalationLevel1Delay
   */
  public void setEscalationLevel1Delay(BRelTime v) { set(escalationLevel1Delay, v, null); }

  //endregion Property "escalationLevel1Delay"

  //region Property "escalationLevel2Enabled"

  /**
   * Slot for the {@code escalationLevel2Enabled} property.
   * @see #getEscalationLevel2Enabled
   * @see #setEscalationLevel2Enabled
   */
  public static final Property escalationLevel2Enabled = newProperty(0, false, null);

  /**
   * Get the {@code escalationLevel2Enabled} property.
   * @see #escalationLevel2Enabled
   */
  public boolean getEscalationLevel2Enabled() { return getBoolean(escalationLevel2Enabled); }

  /**
   * Set the {@code escalationLevel2Enabled} property.
   * @see #escalationLevel2Enabled
   */
  public void setEscalationLevel2Enabled(boolean v) { setBoolean(escalationLevel2Enabled, v, null); }

  //endregion Property "escalationLevel2Enabled"

  //region Property "escalationLevel2Delay"

  /**
   * Slot for the {@code escalationLevel2Delay} property.
   * @see #getEscalationLevel2Delay
   * @see #setEscalationLevel2Delay
   */
  public static final Property escalationLevel2Delay = newProperty(0, BRelTime.make(15*60*1000), BFacets.make(BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(2*60*1000)));

  /**
   * Get the {@code escalationLevel2Delay} property.
   * @see #escalationLevel2Delay
   */
  public BRelTime getEscalationLevel2Delay() { return (BRelTime)get(escalationLevel2Delay); }

  /**
   * Set the {@code escalationLevel2Delay} property.
   * @see #escalationLevel2Delay
   */
  public void setEscalationLevel2Delay(BRelTime v) { set(escalationLevel2Delay, v, null); }

  //endregion Property "escalationLevel2Delay"

  //region Property "escalationLevel3Enabled"

  /**
   * Slot for the {@code escalationLevel3Enabled} property.
   * @see #getEscalationLevel3Enabled
   * @see #setEscalationLevel3Enabled
   */
  public static final Property escalationLevel3Enabled = newProperty(0, false, null);

  /**
   * Get the {@code escalationLevel3Enabled} property.
   * @see #escalationLevel3Enabled
   */
  public boolean getEscalationLevel3Enabled() { return getBoolean(escalationLevel3Enabled); }

  /**
   * Set the {@code escalationLevel3Enabled} property.
   * @see #escalationLevel3Enabled
   */
  public void setEscalationLevel3Enabled(boolean v) { setBoolean(escalationLevel3Enabled, v, null); }

  //endregion Property "escalationLevel3Enabled"

  //region Property "escalationLevel3Delay"

  /**
   * Slot for the {@code escalationLevel3Delay} property.
   * @see #getEscalationLevel3Delay
   * @see #setEscalationLevel3Delay
   */
  public static final Property escalationLevel3Delay = newProperty(0, BRelTime.make(30*60*1000), BFacets.make(BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(3*60*1000)));

  /**
   * Get the {@code escalationLevel3Delay} property.
   * @see #escalationLevel3Delay
   */
  public BRelTime getEscalationLevel3Delay() { return (BRelTime)get(escalationLevel3Delay); }

  /**
   * Set the {@code escalationLevel3Delay} property.
   * @see #escalationLevel3Delay
   */
  public void setEscalationLevel3Delay(BRelTime v) { set(escalationLevel3Delay, v, null); }

  //endregion Property "escalationLevel3Delay"

  //region Action "routeAlarm"

  /**
   * Slot for the {@code routeAlarm} action.
   * Route an alarm record
   * @see #routeAlarm(BAlarmRecord parameter)
   */
  public static final Action routeAlarm = newAction(Flags.HIDDEN | Flags.ASYNC, new BAlarmRecord(), null);

  /**
   * Invoke the {@code routeAlarm} action.
   * Route an alarm record
   * @see #routeAlarm
   */
  public void routeAlarm(BAlarmRecord parameter) { invoke(routeAlarm, parameter, null); }

  //endregion Action "routeAlarm"

  //region Topic "alarm"

  /**
   * Slot for the {@code alarm} topic.
   * @see #fireAlarm
   */
  public static final Topic alarm = newTopic(Flags.SUMMARY, null);

  /**
   * Fire an event for the {@code alarm} topic.
   * @see #alarm
   */
  public void fireAlarm(BAlarmRecord event) { fire(alarm, event, null); }

  //endregion Topic "alarm"

  //region Topic "escalatedAlarm1"

  /**
   * Slot for the {@code escalatedAlarm1} topic.
   * @see #fireEscalatedAlarm1
   */
  public static final Topic escalatedAlarm1 = newTopic(0, null);

  /**
   * Fire an event for the {@code escalatedAlarm1} topic.
   * @see #escalatedAlarm1
   */
  public void fireEscalatedAlarm1(BAlarmRecord event) { fire(escalatedAlarm1, event, null); }

  //endregion Topic "escalatedAlarm1"

  //region Topic "escalatedAlarm2"

  /**
   * Slot for the {@code escalatedAlarm2} topic.
   * @see #fireEscalatedAlarm2
   */
  public static final Topic escalatedAlarm2 = newTopic(0, null);

  /**
   * Fire an event for the {@code escalatedAlarm2} topic.
   * @see #escalatedAlarm2
   */
  public void fireEscalatedAlarm2(BAlarmRecord event) { fire(escalatedAlarm2, event, null); }

  //endregion Topic "escalatedAlarm2"

  //region Topic "escalatedAlarm3"

  /**
   * Slot for the {@code escalatedAlarm3} topic.
   * @see #fireEscalatedAlarm3
   */
  public static final Topic escalatedAlarm3 = newTopic(0, null);

  /**
   * Fire an event for the {@code escalatedAlarm3} topic.
   * @see #escalatedAlarm3
   */
  public void fireEscalatedAlarm3(BAlarmRecord event) { fire(escalatedAlarm3, event, null); }

  //endregion Topic "escalatedAlarm3"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmClass.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Methods
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);


    if (p == totalAlarmCount || p == openAlarmCount ||
        p == unackedAlarmCount || p == inAlarmCount)
    {
      if (getInt(p) < 0)
        setInt(p, 0, null);
    }
    else if(p == escalationLevel1Enabled)
    {
      if (getEscalationLevel1Enabled())
      {
        int flags = getFlags(escalatedAlarm1);
        flags = flags | Flags.SUMMARY;
        setFlags(escalatedAlarm1, flags);
      }
      else
      {
        int flags = getFlags(escalatedAlarm1);
        flags = flags & ~Flags.SUMMARY;
        setFlags(escalatedAlarm1, flags);
      }
    }
    else if(p == escalationLevel2Enabled)
    {
      if (getEscalationLevel2Enabled())
      {
        int flags = getFlags(escalatedAlarm1);
        flags = flags | Flags.SUMMARY;
        setFlags(escalatedAlarm2, flags);
      }
      else
      {
        int flags = getFlags(escalatedAlarm2);
        flags = flags & ~Flags.SUMMARY;
        setFlags(escalatedAlarm2, flags);
      }
    }
    else if(p == escalationLevel3Enabled)
    {
      if (getEscalationLevel3Enabled())
      {
        int flags = getFlags(escalatedAlarm3);
        flags = flags | Flags.SUMMARY;
        setFlags(escalatedAlarm3, flags);
      }
      else
      {
        int flags = getFlags(escalatedAlarm3);
        flags = flags & ~Flags.SUMMARY;
        setFlags(escalatedAlarm3, flags);
      }
    }

    //check the escalation enableds in order to not have the Clock running when nothing is enabled
    //
    //when enabled, set the topic flag to summary, and remove it when disabled
    //if enable level 2, also enable level 1, etc.
    //if disable level1, also do level2 and level3, etc.
  }

  @Override
  public void started()
  {
  }

  @Override
  public IFuture post(Action action, BValue argument, Context cx)
  {
    if ((action == routeAlarm))
    {
      Queue q = (Queue)Sys.getService(BAlarmService.TYPE).fw(Fw.GET_ALARM_QUEUE);
      AlarmClassRouteAlarmInvocation invc = AlarmClassRouteAlarmInvocation.make(this, action, (BAlarmRecord)argument, cx);
      q.enqueue(invc);
      if (action == routeAlarm) return invc;
      else return null;
    }
    else
      return super.post(action, argument, cx);
  }

  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BIAlarmClassFolder);
  }

  /**
   * Route this alarm to all interested recipients
   *
   * @param alarm The alarm to route.
   */
  public void doRouteAlarm(BAlarmRecord alarm)
  {
    try
    {
      if (!isRunning())
      {
        return;
      }

      //set alarm priorities
      BSourceState newState = alarm.getSourceState();
      alarm.setPriority(getPriority().getPriority(newState));
      alarm.setAlarmClass(this.getName());
      if (alarm.getAlarmData().get(BFacets.TIME_ZONE) == null)
      { // we don't want to override the timezone of a remote alarm NCCB-41378
        alarm.setAlarmData(BFacets.make(alarm.getAlarmData(), BFacets.TIME_ZONE, BTimeZone.getLocal()));
      }
      boolean ackReq = getAckRequired().includes(newState);

      //FIX: Bug 8624
      // If it's an acked alert, set to to normal to update the alarm counts properly.
      if (alarm.getSourceState() == BSourceState.alert && alarm.getAckState() == BAckState.acked)
      {
        alarm.setSourceState(BSourceState.normal);
        alarm.setAlarmTransition(BSourceState.alert);//just in case it wasn't already set
      }
      //add the escalated facet until bql quries are fixed to short curcuit && and ||
      if (alarm.getAlarmData().get(ESCALATED) == null)
        alarm.addAlarmFacet(ESCALATED, BString.make(""));
      //Lookup the alarm service, store the alarm
      alarm.setLastUpdate(BAbsTime.now());
      BAlarmService as = getAlarmService();
      try (AlarmDbConnection conn = as.getAlarmDb().getDbConnection(null))
      {
        if (conn.getRecord(alarm.getUuid()) == null)
          conn.append(alarm);
        else
          conn.update(alarm);
      }
      catch(Exception e)
      {
        log.log(Level.SEVERE, "Cannot write alarm.", e);
        throw new AlarmException("Cannot write alarm", e);
        //return;
      }
      if (log.isLoggable(Level.FINE)) log.fine("BAlarmClass: " + getName() + " stored alarm.timestamp: " + alarm.getTimestamp());

      if (alarm.isAlarm())
        setTimeOfLastAlarm(alarm.getTimestamp());

      //  Fire the alarm topic
      fireAlarm(alarm);

      BString escalatedLevel = (BString)alarm.getAlarmData().get(ESCALATED);
      if (escalatedLevel != null)
      {
        if (getEscalationLevel1Enabled() && (escalatedLevel.equals(BString.make(LEVEL_1)) || escalatedLevel.equals(BString.make(LEVEL_2)) || escalatedLevel.equals(BString.make(LEVEL_3))))
          fireEscalatedAlarm1(alarm);
        if (getEscalationLevel2Enabled() && (escalatedLevel.equals(BString.make(LEVEL_2)) || escalatedLevel.equals(BString.make(LEVEL_3))))
          fireEscalatedAlarm2(alarm);
        if (getEscalationLevel3Enabled() && escalatedLevel.equals(BString.make(LEVEL_3)))
          fireEscalatedAlarm3(alarm);
      }

    }
    catch (ServiceNotFoundException e)
    {
      log.log(Level.SEVERE, "Alarm service not found",e);
    }

  }

  @Override
  public String toString(Context cx)
  {
    return getDisplayName(cx);
  }

  protected BAlarmService getAlarmService()
  {
    if (alarmService == null)
      alarmService = (BAlarmService)Sys.getService(BAlarmService.TYPE);
    return alarmService;
  }

  public static final Logger log = Logger.getLogger("alarm");

  BAlarmService alarmService;

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("alarm/alarmClass.png");

  public static final String ESCALATED = "escalated";
  public static final String LEVEL_1 = "level1";
  public static final String LEVEL_2 = "level2";
  public static final String LEVEL_3 = "level3";
}
