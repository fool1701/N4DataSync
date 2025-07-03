/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.AlarmSupport;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmState;
import javax.baja.alarm.ext.BAlarmTimestamps;
import javax.baja.alarm.ext.BIAlarmMessages;
import javax.baja.bacnet.BacnetAlarmConst;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.control.BPointExtension;
import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

import com.tridium.bacnet.BacUtil;

/**
 * BReliabilityAlarmSourceExt defines the intrinsic alarming/notification
 * for the change of reliability property.
 *
 * @author     Vidya Shivamurthy on 17 Jan 2019
 * @since      BACNet 14, Niagara R47.u1
 */
@NiagaraType
/*
 Inhibits alarm generation.
 */
@NiagaraProperty(
  name = "alarmInhibit",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)"
)
/*
 Shows the object's current alarm state.
 */
@NiagaraProperty(
  name = "alarmState",
  type = "BAlarmState",
  defaultValue = "BAlarmState.normal",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Minimum time period that an alarm condition must exist before the object alarms.
 */
@NiagaraProperty(
  name = "timeDelay",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  facets = @Facet(name = "BFacets.MIN", value = "BRelTime.make(0)")
)
/*
 Flags that define the types of alarm transitions for this object that will generate alarm.
 */
@NiagaraProperty(
  name = "alarmEnable",
  type = "BAlarmTransitionBits",
  defaultValue = "BAlarmTransitionBits.DEFAULT",
  facets = {
    @Facet("BFacets.make(\"showOffNormal\", false)"),
    @Facet("BFacets.make(\"showAlert\", false)")
  }
)
/*
 Flags, that when cleared, indicate that an unacknowledged alarm transition has occurred.
 */
@NiagaraProperty(
  name = "ackedTransitions",
  type = "BAlarmTransitionBits",
  defaultValue = "BAlarmTransitionBits.ALL",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN
)
/*
 If set to event (not alarm), an active unacknowledged alarm is not reported by the station's Bacnet service.
 */
@NiagaraProperty(
  name = "notifyType",
  type = "BBacnetNotifyType",
  defaultValue = "BBacnetNotifyType.alarm",
  facets = @Facet("BacUtil.makeBacnetNotifyTypeFacets()")
)
/*
 eventTime, ackTime and count for last to fault event.
 */
@NiagaraProperty(
  name = "toFaultTimes",
  type = "BAlarmTimestamps",
  defaultValue = "new BAlarmTimestamps()",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 Text descriptor included in a to-fault alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toFaultText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")",
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "true")
)
/*
 Text descriptor included in a to-normal alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toNormalText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")",
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "true")
)
/*
 Ord to link to for more information about this alarm.
 */
@NiagaraProperty(
  name = "hyperlinkOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = {
    @Facet(name = "BFacets.ORD_RELATIVIZE", value = "false"),
    @Facet("BFacets.make(\"chooseView\", true)")
  }
)
/*
 This is the alarm class used for this object.
 */
@NiagaraProperty(
  name = "alarmClass",
  type = "String",
  defaultValue = "defaultAlarmClass",
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "\"alarm:AlarmClassFE\""),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "\"alarm:AlarmClassEditor\"")
  }
)
/*
 The recent reliability value of the alarm.
 */
@NiagaraProperty(
  name = "reliability",
  type = "BBacnetReliability",
  defaultValue = "BBacnetReliability.noFaultDetected"
)
/*
 Acknowledge the alarm matching this ack request
 */
@NiagaraAction(
  name = "ackAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  returnType = "BBoolean",
  flags = Flags.HIDDEN
)
/*
 Acknowledge the alarm matching this ack request
 */
@NiagaraAction(
  name = "timerElapsed",
  parameterType = "BBacnetReliability",
  defaultValue = "BBacnetReliability.noFaultDetected",
  flags = Flags.HIDDEN
)
public class BReliabilityAlarmSourceExt
  extends BPointExtension
  implements BIAlarmSource,
             BIAlarmMessages,
             BacnetAlarmConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BReliabilityAlarmSourceExt(2043919475)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmInhibit"

  /**
   * Slot for the {@code alarmInhibit} property.
   * Inhibits alarm generation.
   * @see #getAlarmInhibit
   * @see #setAlarmInhibit
   */
  public static final Property alarmInhibit = newProperty(0, new BStatusBoolean(false), null);

  /**
   * Get the {@code alarmInhibit} property.
   * Inhibits alarm generation.
   * @see #alarmInhibit
   */
  public BStatusBoolean getAlarmInhibit() { return (BStatusBoolean)get(alarmInhibit); }

  /**
   * Set the {@code alarmInhibit} property.
   * Inhibits alarm generation.
   * @see #alarmInhibit
   */
  public void setAlarmInhibit(BStatusBoolean v) { set(alarmInhibit, v, null); }

  //endregion Property "alarmInhibit"

  //region Property "alarmState"

  /**
   * Slot for the {@code alarmState} property.
   * Shows the object's current alarm state.
   * @see #getAlarmState
   * @see #setAlarmState
   */
  public static final Property alarmState = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAlarmState.normal, null);

  /**
   * Get the {@code alarmState} property.
   * Shows the object's current alarm state.
   * @see #alarmState
   */
  public BAlarmState getAlarmState() { return (BAlarmState)get(alarmState); }

  /**
   * Set the {@code alarmState} property.
   * Shows the object's current alarm state.
   * @see #alarmState
   */
  public void setAlarmState(BAlarmState v) { set(alarmState, v, null); }

  //endregion Property "alarmState"

  //region Property "timeDelay"

  /**
   * Slot for the {@code timeDelay} property.
   * Minimum time period that an alarm condition must exist before the object alarms.
   * @see #getTimeDelay
   * @see #setTimeDelay
   */
  public static final Property timeDelay = newProperty(0, BRelTime.DEFAULT, BFacets.make(BFacets.MIN, BRelTime.make(0)));

  /**
   * Get the {@code timeDelay} property.
   * Minimum time period that an alarm condition must exist before the object alarms.
   * @see #timeDelay
   */
  public BRelTime getTimeDelay() { return (BRelTime)get(timeDelay); }

  /**
   * Set the {@code timeDelay} property.
   * Minimum time period that an alarm condition must exist before the object alarms.
   * @see #timeDelay
   */
  public void setTimeDelay(BRelTime v) { set(timeDelay, v, null); }

  //endregion Property "timeDelay"

  //region Property "alarmEnable"

  /**
   * Slot for the {@code alarmEnable} property.
   * Flags that define the types of alarm transitions for this object that will generate alarm.
   * @see #getAlarmEnable
   * @see #setAlarmEnable
   */
  public static final Property alarmEnable = newProperty(0, BAlarmTransitionBits.DEFAULT, BFacets.make(BFacets.make("showOffNormal", false), BFacets.make("showAlert", false)));

  /**
   * Get the {@code alarmEnable} property.
   * Flags that define the types of alarm transitions for this object that will generate alarm.
   * @see #alarmEnable
   */
  public BAlarmTransitionBits getAlarmEnable() { return (BAlarmTransitionBits)get(alarmEnable); }

  /**
   * Set the {@code alarmEnable} property.
   * Flags that define the types of alarm transitions for this object that will generate alarm.
   * @see #alarmEnable
   */
  public void setAlarmEnable(BAlarmTransitionBits v) { set(alarmEnable, v, null); }

  //endregion Property "alarmEnable"

  //region Property "ackedTransitions"

  /**
   * Slot for the {@code ackedTransitions} property.
   * Flags, that when cleared, indicate that an unacknowledged alarm transition has occurred.
   * @see #getAckedTransitions
   * @see #setAckedTransitions
   */
  public static final Property ackedTransitions = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN, BAlarmTransitionBits.ALL, null);

  /**
   * Get the {@code ackedTransitions} property.
   * Flags, that when cleared, indicate that an unacknowledged alarm transition has occurred.
   * @see #ackedTransitions
   */
  public BAlarmTransitionBits getAckedTransitions() { return (BAlarmTransitionBits)get(ackedTransitions); }

  /**
   * Set the {@code ackedTransitions} property.
   * Flags, that when cleared, indicate that an unacknowledged alarm transition has occurred.
   * @see #ackedTransitions
   */
  public void setAckedTransitions(BAlarmTransitionBits v) { set(ackedTransitions, v, null); }

  //endregion Property "ackedTransitions"

  //region Property "notifyType"

  /**
   * Slot for the {@code notifyType} property.
   * If set to event (not alarm), an active unacknowledged alarm is not reported by the station's Bacnet service.
   * @see #getNotifyType
   * @see #setNotifyType
   */
  public static final Property notifyType = newProperty(0, BBacnetNotifyType.alarm, BacUtil.makeBacnetNotifyTypeFacets());

  /**
   * Get the {@code notifyType} property.
   * If set to event (not alarm), an active unacknowledged alarm is not reported by the station's Bacnet service.
   * @see #notifyType
   */
  public BBacnetNotifyType getNotifyType() { return (BBacnetNotifyType)get(notifyType); }

  /**
   * Set the {@code notifyType} property.
   * If set to event (not alarm), an active unacknowledged alarm is not reported by the station's Bacnet service.
   * @see #notifyType
   */
  public void setNotifyType(BBacnetNotifyType v) { set(notifyType, v, null); }

  //endregion Property "notifyType"

  //region Property "toFaultTimes"

  /**
   * Slot for the {@code toFaultTimes} property.
   * eventTime, ackTime and count for last to fault event.
   * @see #getToFaultTimes
   * @see #setToFaultTimes
   */
  public static final Property toFaultTimes = newProperty(Flags.TRANSIENT | Flags.READONLY, new BAlarmTimestamps(), null);

  /**
   * Get the {@code toFaultTimes} property.
   * eventTime, ackTime and count for last to fault event.
   * @see #toFaultTimes
   */
  public BAlarmTimestamps getToFaultTimes() { return (BAlarmTimestamps)get(toFaultTimes); }

  /**
   * Set the {@code toFaultTimes} property.
   * eventTime, ackTime and count for last to fault event.
   * @see #toFaultTimes
   */
  public void setToFaultTimes(BAlarmTimestamps v) { set(toFaultTimes, v, null); }

  //endregion Property "toFaultTimes"

  //region Property "toFaultText"

  /**
   * Slot for the {@code toFaultText} property.
   * Text descriptor included in a to-fault alarm for this object. Uses BFormat.
   * @see #getToFaultText
   * @see #setToFaultText
   */
  public static final Property toFaultText = newProperty(0, BFormat.make(""), BFacets.make(BFacets.MULTI_LINE, true));

  /**
   * Get the {@code toFaultText} property.
   * Text descriptor included in a to-fault alarm for this object. Uses BFormat.
   * @see #toFaultText
   */
  public BFormat getToFaultText() { return (BFormat)get(toFaultText); }

  /**
   * Set the {@code toFaultText} property.
   * Text descriptor included in a to-fault alarm for this object. Uses BFormat.
   * @see #toFaultText
   */
  public void setToFaultText(BFormat v) { set(toFaultText, v, null); }

  //endregion Property "toFaultText"

  //region Property "toNormalText"

  /**
   * Slot for the {@code toNormalText} property.
   * Text descriptor included in a to-normal alarm for this object. Uses BFormat.
   * @see #getToNormalText
   * @see #setToNormalText
   */
  public static final Property toNormalText = newProperty(0, BFormat.make(""), BFacets.make(BFacets.MULTI_LINE, true));

  /**
   * Get the {@code toNormalText} property.
   * Text descriptor included in a to-normal alarm for this object. Uses BFormat.
   * @see #toNormalText
   */
  public BFormat getToNormalText() { return (BFormat)get(toNormalText); }

  /**
   * Set the {@code toNormalText} property.
   * Text descriptor included in a to-normal alarm for this object. Uses BFormat.
   * @see #toNormalText
   */
  public void setToNormalText(BFormat v) { set(toNormalText, v, null); }

  //endregion Property "toNormalText"

  //region Property "hyperlinkOrd"

  /**
   * Slot for the {@code hyperlinkOrd} property.
   * Ord to link to for more information about this alarm.
   * @see #getHyperlinkOrd
   * @see #setHyperlinkOrd
   */
  public static final Property hyperlinkOrd = newProperty(0, BOrd.NULL, BFacets.make(BFacets.make(BFacets.ORD_RELATIVIZE, false), BFacets.make("chooseView", true)));

  /**
   * Get the {@code hyperlinkOrd} property.
   * Ord to link to for more information about this alarm.
   * @see #hyperlinkOrd
   */
  public BOrd getHyperlinkOrd() { return (BOrd)get(hyperlinkOrd); }

  /**
   * Set the {@code hyperlinkOrd} property.
   * Ord to link to for more information about this alarm.
   * @see #hyperlinkOrd
   */
  public void setHyperlinkOrd(BOrd v) { set(hyperlinkOrd, v, null); }

  //endregion Property "hyperlinkOrd"

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * This is the alarm class used for this object.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(0, "defaultAlarmClass", BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "alarm:AlarmClassFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "alarm:AlarmClassEditor")));

  /**
   * Get the {@code alarmClass} property.
   * This is the alarm class used for this object.
   * @see #alarmClass
   */
  public String getAlarmClass() { return getString(alarmClass); }

  /**
   * Set the {@code alarmClass} property.
   * This is the alarm class used for this object.
   * @see #alarmClass
   */
  public void setAlarmClass(String v) { setString(alarmClass, v, null); }

  //endregion Property "alarmClass"

  //region Property "reliability"

  /**
   * Slot for the {@code reliability} property.
   * The recent reliability value of the alarm.
   * @see #getReliability
   * @see #setReliability
   */
  public static final Property reliability = newProperty(0, BBacnetReliability.noFaultDetected, null);

  /**
   * Get the {@code reliability} property.
   * The recent reliability value of the alarm.
   * @see #reliability
   */
  public BBacnetReliability getReliability() { return (BBacnetReliability)get(reliability); }

  /**
   * Set the {@code reliability} property.
   * The recent reliability value of the alarm.
   * @see #reliability
   */
  public void setReliability(BBacnetReliability v) { set(reliability, v, null); }

  //endregion Property "reliability"

  //region Action "ackAlarm"

  /**
   * Slot for the {@code ackAlarm} action.
   * Acknowledge the alarm matching this ack request
   * @see #ackAlarm(BAlarmRecord parameter)
   */
  public static final Action ackAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code ackAlarm} action.
   * Acknowledge the alarm matching this ack request
   * @see #ackAlarm
   */
  public BBoolean ackAlarm(BAlarmRecord parameter) { return (BBoolean)invoke(ackAlarm, parameter, null); }

  //endregion Action "ackAlarm"

  //region Action "timerElapsed"

  /**
   * Slot for the {@code timerElapsed} action.
   * Acknowledge the alarm matching this ack request
   * @see #timerElapsed(BBacnetReliability parameter)
   */
  public static final Action timerElapsed = newAction(Flags.HIDDEN, BBacnetReliability.noFaultDetected, null);

  /**
   * Invoke the {@code timerElapsed} action.
   * Acknowledge the alarm matching this ack request
   * @see #timerElapsed
   */
  public void timerElapsed(BBacnetReliability parameter) { invoke(timerElapsed, parameter, null); }

  //endregion Action "timerElapsed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReliabilityAlarmSourceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BReliabilityAlarmSourceExt()
  {
    //  Always cancel timer upon entering a new state.
    //  The new state will handle starting another timer
    //  if needed.
    cancelTimer();
  }

  @Override
  public final void started()
    throws Exception
  {
    super.started();
    cancelTimer();
    support = new AlarmSupport(this, "");
    if (reliabilityStored != null)
    {
      if (!reliabilityStored.equals(getReliability()))
      {
        reliabilityChanged(reliabilityStored);
      }
      reliabilityStored = null;
    }
  }

  @Override
  public final void stopped()
    throws Exception
  {
    super.stopped();
    cancelTimer();
  }

  @Override
  public void changed(Property property, Context context)
  {
    super.changed(property, context);
    cancelTimer();
  }

  public BBoolean doAckAlarm(BAlarmRecord ackRequest)
  {
    if (logger.isLoggable(Level.FINE))
    {
      logger.fine("Acknowledging the alarm " + ackRequest.getSourceState());
    }
    try
    {
      updateAlarmTimeProps(ackRequest, false, true);
      return BBoolean.make(support.ackAlarm(ackRequest));
    }
    catch (Exception e)
    {
      logger.severe("Unable to acknowledge the alarm: " + e);
    }

    return BBoolean.make(false);
  }

  public void doTimerElapsed(BBacnetReliability reliability)
  {
   if (isTimerExpired())
   {
     alarmProcessing(reliability);
   }
  }

  public void alarmProcessing(BBacnetReliability reliability)
  {
    setReliability(reliability);
    try
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine("Reliability has changed to " + reliability);
      }
      if (reliability == BBacnetReliability.noFaultDetected)
      {
        HashMap<String, BIDataValue> map = getAlarmFacet(reliability, true);
        support.toNormal(BFacets.make(map), null);
        setAlarmState(BAlarmState.normal);
        updateAlarmTimeProps(null, true);
      }
      else
      {
        HashMap<String, BIDataValue> map = getAlarmFacet(reliability, false);
        BAlarmRecord alarmRecord = support.newFaultAlarm(BFacets.make(map));
        setAlarmState(BAlarmState.fault);
        updateAlarmTimeProps(alarmRecord, false);
      }
    }
    catch (Exception e)
    {
      logger.severe("Unable to process tha alarm for Reliability changed: " + e);
    }
  }

  /**
   * When there is change in the property reliability, then this method is called
   * @param reliability
   */
  public void reliabilityChanged(BBacnetReliability reliability)
  {
    if (!isRunning())
    {
      reliabilityStored = reliability;
      return;
    }
    if (getAlarmInhibit().getBoolean())
    {
      logger.warning("Alarm inhibit is enabled, alarm cannot be processed.");
      return;
    }
    if (getTimeDelay().getMillis() > 0 )
    {
      startTimer(getTimeDelay().getMillis(), reliability);
    }
    else
    {
      alarmProcessing(reliability);
    }
  }

  private void updateAlarmTimeProps(BAlarmRecord alarm, boolean isNormal)
  {
    updateAlarmTimeProps(alarm, isNormal, false);
  }

  private void updateAlarmTimeProps(BAlarmRecord alarm, boolean isNormal, boolean isAck)
  {
    Property alarmTimesProp = toFaultTimes;
    BAlarmTimestamps alarmTimes = (BAlarmTimestamps)get(alarmTimesProp);

    if (isNormal)
    {
      alarmTimes.setNormalTime(BAbsTime.now());
    }
    else
    {
      alarmTimes.setAlarmTime(alarm.getTimestamp());
      alarmTimes.setCount((alarmTimes.getCount()) + 1);
    }
    if (isAck)
    {
      alarmTimes.setAckTime(alarm.getAckTime());
    }
  }

  /*
   * Returns an hashmap containing the alarm record values
   * @param reliability
   * @param isNormalAlarm
   * @return
   */
  private HashMap<String, BIDataValue> getAlarmFacet(BBacnetReliability reliability, boolean isNormalAlarm)
  {
    HashMap<String, BIDataValue> map = new HashMap<>();
    BOrd ord = getHyperlinkOrd();
    map.put(BAlarmRecord.ALARM_VALUE, BString.make(reliability.getTag()));
    map.put(BAlarmRecord.FROM_STATE, BString.make(getAlarmState().getTag()));
    map.put(BAlarmRecord.TO_STATE, BString.make(isNormalAlarm ? BAlarmState.normal.getTag(): BAlarmState.fault.getTag()));
    map.put(BAlarmRecord.MSG_TEXT, BString.make(isNormalAlarm ? getToNormalText().getFormat() : getToFaultText().getFormat()));
    map.put(BAlarmRecord.HYPERLINK_ORD, BString.make(ord.toString()));
    map.put(BAC_NOTIFY_TYPE, BString.make(getNotifyType().getTag()));
    return map;
  }

  @Override
  public void onExecute(BStatusValue out, Context cx)
  {
    //donothing
  }

  @Override
  public BFormat getToOffnormalText()
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Time Delay Utility Methods
////////////////////////////////////////////////////////////////

  /**********************************************
   *  Start a timer to handle alarm validation.
   **********************************************/
  protected void startTimer(long timeDelay, BBacnetReliability reliability)
  {
    endTime = Clock.ticks() + timeDelay;
    if (isRunning())
    {
      ticket = Clock.schedule(this, BRelTime.make(timeDelay), timerElapsed,  reliability);
    }
  }

  /**********************************************
   *  Cancels all timers associated with this
   *  alarm support object
   **********************************************/
  protected void cancelTimer()
  {
    endTime = -1;
    if (ticket != null)
    {
      ticket.cancel();
    }
  }

  /**********************************************
   *  Timer status function
   **********************************************/
  protected boolean isTimerExpired()
  {
    long now = Clock.ticks();

    if (endTime == -1)
    {
      throw new IllegalStateException();
    }

    if (now >= endTime)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  ////////////////////////////////////////////////////////////////
  // Attributes
  ////////////////////////////////////////////////////////////////

  long endTime;
  Clock.Ticket ticket;
  private static final Logger logger = Logger.getLogger("bacnet.server");
  private BBacnetReliability reliabilityStored;
  private AlarmSupport support;
}
