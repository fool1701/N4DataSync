/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.AlarmSupport;
import javax.baja.alarm.BAlarmInstructions;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmService;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.BSourceState;
import javax.baja.control.BControlPoint;
import javax.baja.control.BDiscretePoint;
import javax.baja.control.BPointExtension;
import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;
import javax.baja.util.Lexicon;


/**
 * BAlarmSourceExt is the abstract superclass of all
 * Baja control alarming algorithms.
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 141$ $Date: 5/18/11 10:46:40 AM EDT$
 * @since     Baja 1.0
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
 Time between alarmInhibit:true-&gt;false and alarmInhibit being lifted.
 The reverse is 3x this delay for discrete points and 0 for numeric points.
 */
@NiagaraProperty(
  name = "inhibitTime",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(0))")
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
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(0))")
)
/*
 Minimum time period that a normal condition must exist before the object returns to normal.
 */
@NiagaraProperty(
  name = "timeDelayToNormal",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(0))")
)
/*
 Flags that define the types of alarm transitions for this object that will generate alarm.
 */
@NiagaraProperty(
  name = "alarmEnable",
  type = "BAlarmTransitionBits",
  defaultValue = "BAlarmTransitionBits.DEFAULT",
  facets = @Facet("BFacets.make(new String[]{\"showNormal\", \"showAlert\"}, new BBoolean[]{BBoolean.FALSE, BBoolean.FALSE})")
)
/*
 Flags, that when cleared, indicate that an unacknowledged alarm transition has occurred.
 */
@NiagaraProperty(
  name = "ackedTransitions",
  type = "BAlarmTransitionBits",
  defaultValue = "BAlarmTransitionBits.ALL",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 eventTime, normalTime, ackTime and count for last to offnormal event.
 */
@NiagaraProperty(
  name = "toOffnormalTimes",
  type = "BAlarmTimestamps",
  defaultValue = "new BAlarmTimestamps()",
  flags = Flags.READONLY
)
/*
 eventTime, normalTime,  ackTime and count for last to fault event.
 */
@NiagaraProperty(
  name = "toFaultTimes",
  type = "BAlarmTimestamps",
  defaultValue = "new BAlarmTimestamps()",
  flags = Flags.READONLY
)
/*
 the time that this point has been in it's current state
 update every 10sec.
 */
@NiagaraProperty(
  name = "timeInCurrentState",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 Text descriptor for the source name of the alarm. Uses BFormat, but currently only supports lexicons.
 */
@NiagaraProperty(
  name = "sourceName",
  type = "BFormat",
  defaultValue = "BFormat.make(\"%parent.displayName%\")"
)
/*
 Text descriptor included in a to-fault alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toFaultText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE)")
)
/*
 Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toOffnormalText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE)")
)
/*
 Text descriptor included in a to-normal alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toNormalText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE)")
)
/*
 Ord to link to for more information about this alarm.
 */
@NiagaraProperty(
  name = "hyperlinkOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet("BFacets.make(BFacets.ORD_RELATIVIZE, BBoolean.FALSE, \"chooseView\", BBoolean.TRUE)")
)
/*
 Sound to play when the alarm comes into the alarm console.
 Sound must be available on the client.
 */
@NiagaraProperty(
  name = "soundFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet("BFacets.make(BFacets.TARGET_TYPE, BString.make(\"file:AudioFile\"))")
)
/*
 Icon to display for this alarm in the alarm console
 Icon must be available on the client.
 */
@NiagaraProperty(
  name = "alarmIcon",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet("BFacets.make(BFacets.TARGET_TYPE, BString.make(\"file:ImageFile\"))")
)
@NiagaraProperty(
  name = "alarmInstructions",
  type = "BAlarmInstructions",
  defaultValue = "BAlarmInstructions.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"alarm:InstructionsFE\"))")
)
/*
 This is the fault algorithm used for this object.
 */
@NiagaraProperty(
  name = "faultAlgorithm",
  type = "BFaultAlgorithm",
  defaultValue = "new BFaultAlgorithm()"
)
/*
 This is the offnormal algorithm used for this object.
 */
@NiagaraProperty(
  name = "offnormalAlgorithm",
  type = "BOffnormalAlgorithm",
  defaultValue = "new BOffnormalAlgorithm()"
)
/*
 This is the alarm class used for routing this alarm.
 */
@NiagaraProperty(
  name = "alarmClass",
  type = "String",
  defaultValue = "defaultAlarmClass",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"alarm:AlarmClassFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"alarm:AlarmClassEditor\"))")
)
/*
 Additional user defined data for this alarm.
 */
@NiagaraProperty(
  name = "metaData",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
/*
 the status's that propagate to the control point's status
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN
)
@NiagaraAction(
  name = "timerExpired",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "inhibitTimerExpired",
  flags = Flags.HIDDEN
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
@NiagaraTopic(
  name = "toOffnormal",
  eventType = "BAlarmRecord"
)
@NiagaraTopic(
  name = "toFault",
  eventType = "BAlarmRecord"
)
public class BAlarmSourceExt
  extends BPointExtension
  implements BIAlarmSource, 
             BIAlarmMessages
{

////////////////////////////////////////////////////////////////
// Topic "toNormal"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code toNormal} topic.
   * @see #fireToNormal
   */
  // This topic is defined outside of the slot-o-matic template because its fire
  // method is custom
  public static final Topic toNormal = newTopic(0,null);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BAlarmSourceExt(2772878968)1.0$ @*/
/* Generated Mon Nov 21 09:50:59 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "inhibitTime"

  /**
   * Slot for the {@code inhibitTime} property.
   * Time between alarmInhibit:true-&gt;false and alarmInhibit being lifted.
   * The reverse is 3x this delay for discrete points and 0 for numeric points.
   * @see #getInhibitTime
   * @see #setInhibitTime
   */
  public static final Property inhibitTime = newProperty(0, BRelTime.DEFAULT, BFacets.make(BFacets.MIN, BRelTime.make(0)));

  /**
   * Get the {@code inhibitTime} property.
   * Time between alarmInhibit:true-&gt;false and alarmInhibit being lifted.
   * The reverse is 3x this delay for discrete points and 0 for numeric points.
   * @see #inhibitTime
   */
  public BRelTime getInhibitTime() { return (BRelTime)get(inhibitTime); }

  /**
   * Set the {@code inhibitTime} property.
   * Time between alarmInhibit:true-&gt;false and alarmInhibit being lifted.
   * The reverse is 3x this delay for discrete points and 0 for numeric points.
   * @see #inhibitTime
   */
  public void setInhibitTime(BRelTime v) { set(inhibitTime, v, null); }

  //endregion Property "inhibitTime"

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

  //region Property "timeDelayToNormal"

  /**
   * Slot for the {@code timeDelayToNormal} property.
   * Minimum time period that a normal condition must exist before the object returns to normal.
   * @see #getTimeDelayToNormal
   * @see #setTimeDelayToNormal
   */
  public static final Property timeDelayToNormal = newProperty(0, BRelTime.DEFAULT, BFacets.make(BFacets.MIN, BRelTime.make(0)));

  /**
   * Get the {@code timeDelayToNormal} property.
   * Minimum time period that a normal condition must exist before the object returns to normal.
   * @see #timeDelayToNormal
   */
  public BRelTime getTimeDelayToNormal() { return (BRelTime)get(timeDelayToNormal); }

  /**
   * Set the {@code timeDelayToNormal} property.
   * Minimum time period that a normal condition must exist before the object returns to normal.
   * @see #timeDelayToNormal
   */
  public void setTimeDelayToNormal(BRelTime v) { set(timeDelayToNormal, v, null); }

  //endregion Property "timeDelayToNormal"

  //region Property "alarmEnable"

  /**
   * Slot for the {@code alarmEnable} property.
   * Flags that define the types of alarm transitions for this object that will generate alarm.
   * @see #getAlarmEnable
   * @see #setAlarmEnable
   */
  public static final Property alarmEnable = newProperty(0, BAlarmTransitionBits.DEFAULT, BFacets.make(new String[]{"showNormal", "showAlert"}, new BBoolean[]{BBoolean.FALSE, BBoolean.FALSE}));

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
  public static final Property ackedTransitions = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAlarmTransitionBits.ALL, null);

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

  //region Property "toOffnormalTimes"

  /**
   * Slot for the {@code toOffnormalTimes} property.
   * eventTime, normalTime, ackTime and count for last to offnormal event.
   * @see #getToOffnormalTimes
   * @see #setToOffnormalTimes
   */
  public static final Property toOffnormalTimes = newProperty(Flags.READONLY, new BAlarmTimestamps(), null);

  /**
   * Get the {@code toOffnormalTimes} property.
   * eventTime, normalTime, ackTime and count for last to offnormal event.
   * @see #toOffnormalTimes
   */
  public BAlarmTimestamps getToOffnormalTimes() { return (BAlarmTimestamps)get(toOffnormalTimes); }

  /**
   * Set the {@code toOffnormalTimes} property.
   * eventTime, normalTime, ackTime and count for last to offnormal event.
   * @see #toOffnormalTimes
   */
  public void setToOffnormalTimes(BAlarmTimestamps v) { set(toOffnormalTimes, v, null); }

  //endregion Property "toOffnormalTimes"

  //region Property "toFaultTimes"

  /**
   * Slot for the {@code toFaultTimes} property.
   * eventTime, normalTime,  ackTime and count for last to fault event.
   * @see #getToFaultTimes
   * @see #setToFaultTimes
   */
  public static final Property toFaultTimes = newProperty(Flags.READONLY, new BAlarmTimestamps(), null);

  /**
   * Get the {@code toFaultTimes} property.
   * eventTime, normalTime,  ackTime and count for last to fault event.
   * @see #toFaultTimes
   */
  public BAlarmTimestamps getToFaultTimes() { return (BAlarmTimestamps)get(toFaultTimes); }

  /**
   * Set the {@code toFaultTimes} property.
   * eventTime, normalTime,  ackTime and count for last to fault event.
   * @see #toFaultTimes
   */
  public void setToFaultTimes(BAlarmTimestamps v) { set(toFaultTimes, v, null); }

  //endregion Property "toFaultTimes"

  //region Property "timeInCurrentState"

  /**
   * Slot for the {@code timeInCurrentState} property.
   * the time that this point has been in it's current state
   * update every 10sec.
   * @see #getTimeInCurrentState
   * @see #setTimeInCurrentState
   */
  public static final Property timeInCurrentState = newProperty(Flags.TRANSIENT | Flags.READONLY, BRelTime.DEFAULT, null);

  /**
   * Get the {@code timeInCurrentState} property.
   * the time that this point has been in it's current state
   * update every 10sec.
   * @see #timeInCurrentState
   */
  public BRelTime getTimeInCurrentState() { return (BRelTime)get(timeInCurrentState); }

  /**
   * Set the {@code timeInCurrentState} property.
   * the time that this point has been in it's current state
   * update every 10sec.
   * @see #timeInCurrentState
   */
  public void setTimeInCurrentState(BRelTime v) { set(timeInCurrentState, v, null); }

  //endregion Property "timeInCurrentState"

  //region Property "sourceName"

  /**
   * Slot for the {@code sourceName} property.
   * Text descriptor for the source name of the alarm. Uses BFormat, but currently only supports lexicons.
   * @see #getSourceName
   * @see #setSourceName
   */
  public static final Property sourceName = newProperty(0, BFormat.make("%parent.displayName%"), null);

  /**
   * Get the {@code sourceName} property.
   * Text descriptor for the source name of the alarm. Uses BFormat, but currently only supports lexicons.
   * @see #sourceName
   */
  public BFormat getSourceName() { return (BFormat)get(sourceName); }

  /**
   * Set the {@code sourceName} property.
   * Text descriptor for the source name of the alarm. Uses BFormat, but currently only supports lexicons.
   * @see #sourceName
   */
  public void setSourceName(BFormat v) { set(sourceName, v, null); }

  //endregion Property "sourceName"

  //region Property "toFaultText"

  /**
   * Slot for the {@code toFaultText} property.
   * Text descriptor included in a to-fault alarm for this object. Uses BFormat.
   * @see #getToFaultText
   * @see #setToFaultText
   */
  public static final Property toFaultText = newProperty(0, BFormat.make(""), BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

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

  //region Property "toOffnormalText"

  /**
   * Slot for the {@code toOffnormalText} property.
   * Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
   * @see #getToOffnormalText
   * @see #setToOffnormalText
   */
  public static final Property toOffnormalText = newProperty(0, BFormat.make(""), BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code toOffnormalText} property.
   * Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
   * @see #toOffnormalText
   */
  public BFormat getToOffnormalText() { return (BFormat)get(toOffnormalText); }

  /**
   * Set the {@code toOffnormalText} property.
   * Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
   * @see #toOffnormalText
   */
  public void setToOffnormalText(BFormat v) { set(toOffnormalText, v, null); }

  //endregion Property "toOffnormalText"

  //region Property "toNormalText"

  /**
   * Slot for the {@code toNormalText} property.
   * Text descriptor included in a to-normal alarm for this object. Uses BFormat.
   * @see #getToNormalText
   * @see #setToNormalText
   */
  public static final Property toNormalText = newProperty(0, BFormat.make(""), BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

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
  public static final Property hyperlinkOrd = newProperty(0, BOrd.NULL, BFacets.make(BFacets.ORD_RELATIVIZE, BBoolean.FALSE, "chooseView", BBoolean.TRUE));

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

  //region Property "soundFile"

  /**
   * Slot for the {@code soundFile} property.
   * Sound to play when the alarm comes into the alarm console.
   * Sound must be available on the client.
   * @see #getSoundFile
   * @see #setSoundFile
   */
  public static final Property soundFile = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, BString.make("file:AudioFile")));

  /**
   * Get the {@code soundFile} property.
   * Sound to play when the alarm comes into the alarm console.
   * Sound must be available on the client.
   * @see #soundFile
   */
  public BOrd getSoundFile() { return (BOrd)get(soundFile); }

  /**
   * Set the {@code soundFile} property.
   * Sound to play when the alarm comes into the alarm console.
   * Sound must be available on the client.
   * @see #soundFile
   */
  public void setSoundFile(BOrd v) { set(soundFile, v, null); }

  //endregion Property "soundFile"

  //region Property "alarmIcon"

  /**
   * Slot for the {@code alarmIcon} property.
   * Icon to display for this alarm in the alarm console
   * Icon must be available on the client.
   * @see #getAlarmIcon
   * @see #setAlarmIcon
   */
  public static final Property alarmIcon = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, BString.make("file:ImageFile")));

  /**
   * Get the {@code alarmIcon} property.
   * Icon to display for this alarm in the alarm console
   * Icon must be available on the client.
   * @see #alarmIcon
   */
  public BOrd getAlarmIcon() { return (BOrd)get(alarmIcon); }

  /**
   * Set the {@code alarmIcon} property.
   * Icon to display for this alarm in the alarm console
   * Icon must be available on the client.
   * @see #alarmIcon
   */
  public void setAlarmIcon(BOrd v) { set(alarmIcon, v, null); }

  //endregion Property "alarmIcon"

  //region Property "alarmInstructions"

  /**
   * Slot for the {@code alarmInstructions} property.
   * @see #getAlarmInstructions
   * @see #setAlarmInstructions
   */
  public static final Property alarmInstructions = newProperty(0, BAlarmInstructions.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("alarm:InstructionsFE")));

  /**
   * Get the {@code alarmInstructions} property.
   * @see #alarmInstructions
   */
  public BAlarmInstructions getAlarmInstructions() { return (BAlarmInstructions)get(alarmInstructions); }

  /**
   * Set the {@code alarmInstructions} property.
   * @see #alarmInstructions
   */
  public void setAlarmInstructions(BAlarmInstructions v) { set(alarmInstructions, v, null); }

  //endregion Property "alarmInstructions"

  //region Property "faultAlgorithm"

  /**
   * Slot for the {@code faultAlgorithm} property.
   * This is the fault algorithm used for this object.
   * @see #getFaultAlgorithm
   * @see #setFaultAlgorithm
   */
  public static final Property faultAlgorithm = newProperty(0, new BFaultAlgorithm(), null);

  /**
   * Get the {@code faultAlgorithm} property.
   * This is the fault algorithm used for this object.
   * @see #faultAlgorithm
   */
  public BFaultAlgorithm getFaultAlgorithm() { return (BFaultAlgorithm)get(faultAlgorithm); }

  /**
   * Set the {@code faultAlgorithm} property.
   * This is the fault algorithm used for this object.
   * @see #faultAlgorithm
   */
  public void setFaultAlgorithm(BFaultAlgorithm v) { set(faultAlgorithm, v, null); }

  //endregion Property "faultAlgorithm"

  //region Property "offnormalAlgorithm"

  /**
   * Slot for the {@code offnormalAlgorithm} property.
   * This is the offnormal algorithm used for this object.
   * @see #getOffnormalAlgorithm
   * @see #setOffnormalAlgorithm
   */
  public static final Property offnormalAlgorithm = newProperty(0, new BOffnormalAlgorithm(), null);

  /**
   * Get the {@code offnormalAlgorithm} property.
   * This is the offnormal algorithm used for this object.
   * @see #offnormalAlgorithm
   */
  public BOffnormalAlgorithm getOffnormalAlgorithm() { return (BOffnormalAlgorithm)get(offnormalAlgorithm); }

  /**
   * Set the {@code offnormalAlgorithm} property.
   * This is the offnormal algorithm used for this object.
   * @see #offnormalAlgorithm
   */
  public void setOffnormalAlgorithm(BOffnormalAlgorithm v) { set(offnormalAlgorithm, v, null); }

  //endregion Property "offnormalAlgorithm"

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * This is the alarm class used for routing this alarm.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(0, "defaultAlarmClass", BFacets.make(BFacets.FIELD_EDITOR, BString.make("alarm:AlarmClassFE"), BFacets.UX_FIELD_EDITOR, BString.make("alarm:AlarmClassEditor")));

  /**
   * Get the {@code alarmClass} property.
   * This is the alarm class used for routing this alarm.
   * @see #alarmClass
   */
  public String getAlarmClass() { return getString(alarmClass); }

  /**
   * Set the {@code alarmClass} property.
   * This is the alarm class used for routing this alarm.
   * @see #alarmClass
   */
  public void setAlarmClass(String v) { setString(alarmClass, v, null); }

  //endregion Property "alarmClass"

  //region Property "metaData"

  /**
   * Slot for the {@code metaData} property.
   * Additional user defined data for this alarm.
   * @see #getMetaData
   * @see #setMetaData
   */
  public static final Property metaData = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code metaData} property.
   * Additional user defined data for this alarm.
   * @see #metaData
   */
  public BFacets getMetaData() { return (BFacets)get(metaData); }

  /**
   * Set the {@code metaData} property.
   * Additional user defined data for this alarm.
   * @see #metaData
   */
  public void setMetaData(BFacets v) { set(metaData, v, null); }

  //endregion Property "metaData"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * the status's that propagate to the control point's status
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN, BStatus.DEFAULT, null);

  /**
   * Get the {@code status} property.
   * the status's that propagate to the control point's status
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * the status's that propagate to the control point's status
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Action "timerExpired"

  /**
   * Slot for the {@code timerExpired} action.
   * @see #timerExpired()
   */
  public static final Action timerExpired = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code timerExpired} action.
   * @see #timerExpired
   */
  public void timerExpired() { invoke(timerExpired, null, null); }

  //endregion Action "timerExpired"

  //region Action "inhibitTimerExpired"

  /**
   * Slot for the {@code inhibitTimerExpired} action.
   * @see #inhibitTimerExpired()
   */
  public static final Action inhibitTimerExpired = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code inhibitTimerExpired} action.
   * @see #inhibitTimerExpired
   */
  public void inhibitTimerExpired() { invoke(inhibitTimerExpired, null, null); }

  //endregion Action "inhibitTimerExpired"

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

  //region Topic "toOffnormal"

  /**
   * Slot for the {@code toOffnormal} topic.
   * @see #fireToOffnormal
   */
  public static final Topic toOffnormal = newTopic(0, null);

  /**
   * Fire an event for the {@code toOffnormal} topic.
   * @see #toOffnormal
   */
  public void fireToOffnormal(BAlarmRecord event) { fire(toOffnormal, event, null); }

  //endregion Topic "toOffnormal"

  //region Topic "toFault"

  /**
   * Slot for the {@code toFault} topic.
   * @see #fireToFault
   */
  public static final Topic toFault = newTopic(0, null);

  /**
   * Fire an event for the {@code toFault} topic.
   * @see #toFault
   */
  public void fireToFault(BAlarmRecord event) { fire(toFault, event, null); }

  //endregion Topic "toFault"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmSourceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * Callback for when the extension is started.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();
    support = new AlarmSupport(this, getAlarmClass());
    // Initialize state variables
    timeOfLastStateChange = Clock.ticks();
    initScheduler();

    if (Sys.atSteadyState())
    {
      atSteadyState = true;
    }
  }

  @Override
  public void atSteadyState()
    throws Exception
  {
    atSteadyState = true;
    executePoint();
  }

  /**
   * An alarm extension requires its point to be subscribed
   * whenever it is enabled.
   */
  @Override
  public boolean requiresPointSubscription()
  {
    return getAlarmEnable().isToOffnormal() || getAlarmEnable().isToFault();
  }

  public void checkPointSubscription()
  {
    BControlPoint point = getParentPoint();

    if (point != null)
    {
      point.checkExtensionsRequireSubscription();
    }
  }

  /**
   * A BAlarmSourceExt's parent must be a BControlPoint.  In
   * addition, fault / offnormal have special parentage requirements
   * that are checked at this point.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BControlPoint &&
      getOffnormalAlgorithm().isGrandparentLegal(parent) &&
      getFaultAlgorithm().isGrandparentLegal(parent);
  }

  /**
   * Any sibling is legal for an alarm extension.
   */
  @Override
  protected boolean isSiblingLegal(BComponent sibling)
  {
    return true;
  }

  /**
   * Schedule to counter to be invoked periodically
   * regardless of changes to the parent object so
   * the total will get updated every 10 seconds
   */
  private void initScheduler()
  {
    if (ticket != null)
    {
      ticket.cancel();
    }
    ticket = Clock.schedulePeriodically(this, BRelTime.make(10000), timerExpired, null);
  }

  public BOrdList getSourceOrd()
  {
    return support.getSourceOrd();
  }

////////////////////////////////////////////////////////////////
//  Actions
////////////////////////////////////////////////////////////////

  /**
   * Callback for timer expired.
   */
  public void doTimerExpired()
  {
    setTimeInCurrentState(BRelTime.make(Clock.ticks()-timeOfLastStateChange));
  }

  /**
   * Callback for inhibit timer expired.
   */
  public void doInhibitTimerExpired()
  {
    inhibitTicket = null;
    checkPointSubscription();
    executePoint();
  }

  public BBoolean doAckAlarm(BAlarmRecord ackRequest)
  {
    if (!isRunning())
    {
      return BBoolean.make(false);
    }

    try
    {
      // support.ackAlarm returns true if the request is for the most recent alarm
      // support.ackAlarm sends the ackRequest back to BAlarmService.routeAlarm()
      boolean validAck = support.ackAlarm(ackRequest);
      lastAckTime = ackRequest.getAckTime();

      if (validAck)
      {
        updateAckedTransitions(ackRequest.getAlarmTransition());
        updateAckTime(ackRequest);
      }

      // check toNormal
      boolean validNormalAck = support.isValidNormalAck(ackRequest);
      if (validNormalAck)
      {
        updateAckedTransitions(BSourceState.normal);
      }

      // Clear the unacked alarm status
      if ((validAck || validNormalAck) && getStatus().isUnackedAlarm())
      {
        setStatus(BStatus.makeUnackedAlarm(getStatus(), false));
      }

      // Force the parent control point to execute to ensure the status flags get updated
      executePoint();

      return BBoolean.make(validAck);
    }
    catch(Exception e)
    {
      log.log(Level.WARNING, "Exception thrown while acking alarm", e);
      return BBoolean.make(false);
    }
  }

  private void updateAckedTransitions(BSourceState state)
  {
    BAlarmTransitionBits ackedTrans = getAckedTransitions();
    ackedTrans = BAlarmTransitionBits.make(ackedTrans, state.getAlarmTransitionBits(), true);
    setAckedTransitions(ackedTrans);
  }

  private void updateAckTime(BAlarmRecord ackRequest)
  {
    BAlarmTimestamps alarmTimes = null;
    BSourceState initialState = ackRequest.getAlarmTransition();
    if (initialState == BSourceState.offnormal)
    {
      alarmTimes = getToOffnormalTimes();
    }
    else if (initialState == BSourceState.fault)
    {
      alarmTimes = getToFaultTimes();
    }

    if (alarmTimes != null && !alarmTimes.getAlarmTime().isNull())
    {
      alarmTimes.setAckTime(ackRequest.getAckTime());
    }
  }

  private void updateNormalTimes(BAlarmRecord record)
  {
    BAlarmTimestamps alarmTimes = getToOffnormalTimes();
    if (!alarmTimes.getAlarmTime().isNull())
    {
      alarmTimes.setNormalTime(record.getNormalTime());
    }

    alarmTimes = getToFaultTimes();
    if (!alarmTimes.getAlarmTime().isNull())
    {
      alarmTimes.setNormalTime(record.getNormalTime());
    }
  }

////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////

  @Override
  public void onExecute(BStatusValue out, Context cx)
  {
    if (!isRunning() || !atSteadyState)
    {
      return;
    }

    boolean isWorkingVariableAlarm = out.getStatus().isAlarm();
    boolean isWorkingVariableFault = out.getStatus().isFault();
    checkAlarms(out);

    if (!(inhibitTicket != null && !inhibitTicket.isExpired()))
    {
      setStatus(getAlarmInhibit().getBoolean() || isTimerActive() ?
                BStatus.make(getStatus(), INHIBIT_FACET_TEXT, true) :
                BStatus.make(getStatus().getBits(), BFacets.makeRemove(getStatus().getFacets(), INHIBIT_FACET_TEXT)));
    }
    else
    {
      setStatus(getAlarmInhibit().getBoolean() || isTimerActive() ?
                BStatus.make(getStatus().getBits(), BFacets.makeRemove(getStatus().getFacets(), INHIBIT_FACET_TEXT)) :
                BStatus.make(getStatus(), INHIBIT_FACET_TEXT, true));
    }

    BControlPoint point = getParentPoint();
    BAlarmSourceExt[] exts = point.getChildren(BAlarmSourceExt.class);

    // get the status without any of the alarm type bits set exclusively by these ext's.
    int bits = out.getStatus().getBits() & NON_ALARM_BITS;
    boolean hasInhibit = false;
    for (BAlarmSourceExt ext : exts)
    {
      // Bitwise OR all of the ALARM_BITS from all the peers if not inhibited (after an execution)
      bits |= ext.getStatus().getBits();
      if (ext.getStatus().getb(INHIBIT_FACET_TEXT, false))
      {
        hasInhibit = true;
      }

      // only get previously onExecuted bits
      if (ext == this)
      {
        break;
      }
    }

    out.setStatus(hasInhibit ?
                  // sets the bits, add alarmInhibit to the status's facets if it's present in any ext
                  BStatus.make(bits, BFacets.make(out.getStatus().getFacets(), INHIBIT_FACET_TEXT, BBoolean.TRUE)) :
                  // sets the bits, remove alarmInhibit to the status's facets if it's not present in any ext
                  BStatus.make(bits, BFacets.makeRemove(out.getStatus().getFacets(), INHIBIT_FACET_TEXT)));

    if (isWorkingVariableAlarm)
    {
      out.setStatusInAlarm(true);
    }

    if (isWorkingVariableFault)
    {
      out.setStatusFault(true);
    }
  }

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning())
    {
      return;
    }

    if (p.equals(alarmClass) && support != null)
    {
      support.setAlarmClass(getAlarmClass());
    }
    else if (p.equals(timeDelay))
    {
      executePoint();
    }
    else if (p.equals(alarmInhibit))
    {
      checkPointSubscription();

      if (getInhibitTime().getMillis() > 0)
      {
        if (inhibitTicket != null)
        {
          inhibitTicket.cancel();
        }

        if (!getAlarmInhibit().getBoolean())
        {
          inhibitTicket = Clock.schedule(this, getInhibitTime(), inhibitTimerExpired, null);
        }
        else if (getParent() instanceof BDiscretePoint)
        {
          inhibitTicket = Clock.schedule(this, BRelTime.make(getInhibitTime().getMillis()*3), inhibitTimerExpired, null);
        }
      }
      else
      {
        executePoint();
      }
    }
    else if (p.equals(alarmEnable))
    {
      checkPointSubscription();
      executePoint();
    }
  }

  /**********************************************
  *  Check for to see if alarm inhibit timer is active
  * alarms
  **********************************************/
  protected boolean isTimerActive()
  {
    try
    {
      return getOffnormalAlgorithm().endTime != -1 &&
             !getOffnormalAlgorithm().isTimerExpired();
    }
    catch(IllegalStateException e)
    {
      return false;
    }
  }

  protected boolean isInhibitTimerActive()
  {
    try
    {
      return inhibitTicket != null && !inhibitTicket.isExpired();
    }
    catch(IllegalStateException e)
    {
      return false;
    }
  }

  /**********************************************
  *  Check for to_normal, to_offnormal and to_fault
  * alarms
  **********************************************/
  protected void checkAlarms(BStatusValue out)
  {
    //  Don't bother checking for alarms if control point is disabled
    if (out.getStatus().isDisabled() || out.getStatus().isStale())
    {
      return;
    }

    if (Sys.getService(BAlarmService.TYPE) == null)
    {
      return;
    }

    BAlarmState alarmState = getAlarmState();
    BAlarmState newAlarmState;
    try
    {
      newAlarmState = evaluateNewAlarmState(out, alarmState);
    }
    catch (NotRunningException e)
    {
      // The faultAlgorithm or offnormal algorithm was replaced. Re-check alarms with the
      // replacement algorithm.
      checkAlarms(out);
      return;
    }

    // Update the inAlarm bit of status flags to reflect the new alarm state
    boolean ackRequired = true;
    if (newAlarmState != null)
    {
      if (getAlarmEnable().isToOffnormal() && newAlarmState.isInAlarm())
      {
        setStatus(BStatus.makeAlarm(getStatus(), true));
      }
      else if (!newAlarmState.isInAlarm())
      {
        setStatus(BStatus.makeAlarm(getStatus(), false));
      }

      if (getAlarmEnable().isToFault() && newAlarmState == BAlarmState.fault)
      {
        setStatus(BStatus.makeFault(getStatus(), true));
      }
      else if (newAlarmState != BAlarmState.fault)
      {
        setStatus(BStatus.makeFault(getStatus(), false));
      }

      if (newAlarmState == BAlarmState.normal)
      {
        ackRequired = alarmStateTransition(BSourceState.normal, BAlarmState.normal, out);
        if (alarmState.isOffnormal())
        {
          ackRequired |= support.isAckRequired(BSourceState.offnormal);
        }
        else if (alarmState.isInAlarm())
        {
          ackRequired |= support.isAckRequired(BSourceState.fault);
        }
      }
      else if (newAlarmState == BAlarmState.fault)
      {
        ackRequired = alarmStateTransition(BSourceState.fault, BAlarmState.fault, out);
      }
      else
      {
        ackRequired = alarmStateTransition(BSourceState.offnormal, newAlarmState, out);
      }

      //  Update the alarm state
      setAlarmState(newAlarmState);

      timeOfLastStateChange = Clock.ticks();
      setTimeInCurrentState(BRelTime.DEFAULT);
    }
    else
    {
      // No change in alarm state.
      // If we are in alarm, re-assert our alarm state in case it got cleared by the Control Point's
      // executePoint method.
      if (getAlarmEnable().isToOffnormal())
      {
        setStatus(BStatus.makeAlarm(getStatus(), alarmState.isInAlarm()));
      }

      if (getAlarmEnable().isToFault())
      {
        setStatus(BStatus.makeFault(getStatus(), alarmState == BAlarmState.fault));
      }

      // update timeInCurrentState
      setTimeInCurrentState(BRelTime.make(Clock.ticks() - timeOfLastStateChange));
    }

    //  Now that we have had a chance to generate alarms, update the unacked alarm bit. The acked
    //  transition bits are cleared if there is an unacknowledged transition to that state. If
    //  either the to-offnormal or to-fault acked bits are clear, set the unackedAlarm bit.
    BAlarmTransitionBits ackedTrans = getAckedTransitions();
    if (getAlarmEnable().isToOffnormal() || getAlarmEnable().isToFault())
    {
      if ((!ackedTrans.isToOffnormal() || !ackedTrans.isToFault() || !ackedTrans.isToNormal()) && ackRequired)
      {
        setStatus(BStatus.makeUnackedAlarm(getStatus(), true));
      }
      else
      {
        setStatus(BStatus.makeUnackedAlarm(getStatus(), false));
      }
    }

    // check again...
    if ((alarmState == BAlarmState.lowLimit || alarmState == BAlarmState.highLimit) && newAlarmState == BAlarmState.normal)
    {
      checkAlarms(out);
    }
  }

  private BAlarmState evaluateNewAlarmState(BStatusValue out, BAlarmState alarmState)
  {
    // either (there is no inhibit time and there is an inhibit),
    // or (there is no timer and the inhibit on), or (there is a timer and it is not inhibited)
    boolean useInhibit = (getInhibitTime().getMillis() == 0 && getAlarmInhibit().getBoolean()) ||
                         ((inhibitTicket == null || inhibitTicket.isExpired())  && getAlarmInhibit().getBoolean()) ||
                         ((inhibitTicket != null && !inhibitTicket.isExpired()) && !getAlarmInhibit().getBoolean());
    if (useInhibit)
    {
      return alarmState != BAlarmState.normal ? BAlarmState.normal : null;
    }

    //  Alarm Detection Rules
    //  - Two types of alarm:  faults and offnormal.  Each has its own algorithm
    //  - Fault takes precedence over offnormal.  Don't check for offnormal alarm if a fault
    //  condition exists.
    //  - Each algorithm returns null if there is no change in the associated condition or, if a
    //  change in state, the new BAlarmState.
    //  - Transitions within the same non-normal state can occur. For example, an object could go
    //  directly from offnormal high alarm to low alarm, or from one type of fault to another.
    //  - An object can transition from the fault state directly to the offnormal state w/out
    //  transitioning to normal or vice versa.
    BAlarmState newAlarmState = null;
    BAlarmTransitionBits alarmEnable = getAlarmEnable();

    if (alarmEnable.isToFault())
    {
      // Check change if fault condition first
      newAlarmState = getFaultAlgorithm().checkFault(out);
    }

    if (alarmState == BAlarmState.fault && !alarmEnable.isToFault())
    {
      newAlarmState = BAlarmState.normal;
    }

    if (newAlarmState != BAlarmState.fault && alarmEnable.isToOffnormal())
    {
      // No change in fault state
      if (newAlarmState == null)
      {
        // Only check for offnormal if we are not in fault or returning from fault
        // Bug: 7356 added || newAlarmState == normal
        if (alarmState != BAlarmState.fault)
        {
          newAlarmState = getOffnormalAlgorithm().checkAlarmState(out, getTimeDelay().getMillis(), getTimeDelayToNormal().getMillis());
        }
      }
      else if (newAlarmState == BAlarmState.normal)
      {
        // return from fault - check for offnormal or normal
        // Possible transition from fault directly to offnormal
        newAlarmState = getOffnormalAlgorithm().checkAlarmState(out, getTimeDelay().getMillis(), getTimeDelayToNormal().getMillis());

        // If no offnormal condition detected, then it's a return to normal from fault
        if (newAlarmState == null)
        {
          newAlarmState = BAlarmState.normal;
        }
      }
    }

    if (alarmState.isOffnormal() && !alarmEnable.isToOffnormal())
    {
      // TODO Does this preempt a transition toFault from offnormal when toOffnormal == false?
      newAlarmState = BAlarmState.normal;
    }

    return newAlarmState;
  }

  /**********************************************
  *  Invoked when an alarm state transition occurs
  **********************************************/
  private boolean alarmStateTransition(BSourceState sourceState, BAlarmState newAlarmState, BStatusValue out)
  {
    HashMap<String, BIDataValue> map = new HashMap<>();

    BOrd ord = getHyperlinkOrd();
    Property alarmTimesProp = null;

    // Add facets data
    if (sourceState == BSourceState.offnormal)
    {
      map.put(BAlarmRecord.OFFNORMAL_VALUE, BString.make(out.valueToString(getPointFacets())));
      map.put(BAlarmRecord.ALARM_VALUE, (BIDataValue)out.getValueValue());
    }
    else if (sourceState == BSourceState.fault)
    {
      map.put(BAlarmRecord.FAULT_VALUE, BString.make(out.valueToString(getPointFacets())));
      map.put(BAlarmRecord.ALARM_VALUE, (BIDataValue)out.getValueValue());
    }

    map.put(BAlarmRecord.PRESENT_VALUE, BString.make(out.valueToString(getPointFacets())));
    map.put(BAlarmRecord.FROM_STATE, BString.make(getAlarmState().getTag()));
    map.put(BAlarmRecord.TO_STATE, BString.make(newAlarmState.getTag()));
    map.put(BAlarmRecord.SOURCE_NAME, BString.make(getSourceName().format(this)));
    map.put(BAlarmRecord.TIME_DELAY, getTimeDelay());
    map.put(BAlarmRecord.TIME_DELAY_TO_NORMAL, getTimeDelayToNormal());

    if (!ord.isNull())
    {
      map.put(BAlarmRecord.HYPERLINK_ORD, BString.make(ord.toString()));
    }

    if (!getSoundFile().isNull())
    {
      map.put(BAlarmRecord.SOUND_FILE, BString.make(getSoundFile().toString()));
    }

    if (!getAlarmIcon().isNull())
    {
      map.put(BAlarmRecord.ICON, BString.make(getAlarmIcon().toString()));
    }

    try
    {
      if (!getAlarmInstructions().equals(BAlarmInstructions.NULL))
      {
        map.put(BAlarmRecord.INSTRUCTIONS, BString.make(getAlarmInstructions().encodeToString()));
      }
    }
    catch(Exception e)
    {
      log.log(Level.SEVERE, "Could not encode Alarm Instructions", e);
    }

    BFacets alarmMetaData = getMetaData();
    String [] keys = alarmMetaData.list();
    for (String key : keys)
    {
      map.put(key, (BIDataValue) alarmMetaData.get(key));
    }

    if (sourceState == BSourceState.normal)
    {
      map.put(BAlarmRecord.MSG_TEXT, BString.make(getToNormalText().getFormat()));
      if (getAlarmState().isInAlarm())
      {
        getOffnormalAlgorithm().writeAlarmData(out, map);
      }
      else
      {
        getFaultAlgorithm().writeAlarmData(out, map);
      }
    }
    else if (sourceState == BSourceState.offnormal)
    {
      map.put(BAlarmRecord.MSG_TEXT, BString.make(getToOffnormalText().getFormat()));
      map.put(BAlarmTimestamps.count.getDefaultDisplayName(null), BInteger.make(getToOffnormalTimes().getCount()+1));
      getOffnormalAlgorithm().writeAlarmData(out, map);
      alarmTimesProp = toOffnormalTimes;
    }
    else if (sourceState == BSourceState.fault)
    {
      map.put(BAlarmRecord.MSG_TEXT, BString.make(getToFaultText().getFormat()));
      map.put(BAlarmTimestamps.count.getDefaultDisplayName(null), BInteger.make(getToFaultTimes().getCount()+1));
      getFaultAlgorithm().writeAlarmData(out, map);
      alarmTimesProp = toFaultTimes;
    }
    else
    {
      throw new IllegalStateException();
    }

    boolean ackRequired = false;
    try
    {
      ackRequired = support.isAckRequired(sourceState);

      //  Send the alarm to the alarm service
      BAlarmRecord rec = null;
      if (sourceState == BSourceState.normal)
      {
        // Will put all alarms for this alarm source back to normal, and then call fireToNormal with
        // the most recent alarm.
        support.toNormal(BFacets.make(map), null);
      }
      else if (sourceState == BSourceState.offnormal)
      {
        rec = support.newOffnormalAlarm(BFacets.make(map));
        lastOffnormalTime = rec.getTimestamp();
        fireToOffnormal(rec);
      }
      else if (sourceState == BSourceState.fault)
      {
        rec = support.newFaultAlarm(BFacets.make(map));
        lastFaultTime = rec.getTimestamp();
        fireToFault(rec);
      }

      if (rec != null)
      {
        // Set Alarm Transition Timestamps
        if (sourceState == BSourceState.normal)
        {
          updateNormalTimes(rec);
        }

        //  Store alarm timestamp, clear the ack timestamp
        if (alarmTimesProp != null)
        {
          BAlarmTimestamps alarmTimes = (BAlarmTimestamps)get(alarmTimesProp);

          alarmTimes.setAlarmTime(rec.getTimestamp());
          alarmTimes.setAckTime(BAbsTime.NULL);
          alarmTimes.setNormalTime(BAbsTime.NULL);
          alarmTimes.setCount(alarmTimes.getCount() + 1);
        }
      }

      if (ackRequired)
      {
        BAlarmTransitionBits ackedTrans = getAckedTransitions();
        setAckedTransitions(BAlarmTransitionBits.make(ackedTrans, sourceState.getAlarmTransitionBits(), false));
      }
    }
    catch (ServiceNotFoundException e)
    {
      log.severe("AlarmState Transition Failed - AlarmService not found.");
    }
    catch(Exception e)
    {
      log.log(Level.SEVERE, "AlarmState Transition Failed: " + e, e);
    }

    return ackRequired;
  }

  /**
   * Fires the 'normal' action, as well as updating alarm
   * metrics on the source extension
   */
  public void fireToNormal(BAlarmRecord record)
  {
    lastToNormalTime = record.getNormalTime();
    if (record.getSourceState().getOrdinal() == BSourceState.NORMAL)
    {
      updateNormalTimes(record);
    }

    fire(toNormal, record, null);
  }

  /**
   * Returns true if the given record corresponds to the most recent alert, offnormal, or fault
   * alarm when the source has transitioned to normal. If there have not been any alert, offnormal,
   * or fault alarms for this source, this method returns true if the given record is for this
   * source and has a normal source state.
   *
   * @since Niagara 4.10u5
   * @since Niagara 4.12u2
   * @since Niagara 4.13
   */
  public boolean isLastNormalRecord(BAlarmRecord alarmRecord)
  {
    Objects.requireNonNull(alarmRecord, "alarmRecord parameter must not be null");

    BAlarmRecord lastNormal = support.getLastNormal();
    if (lastNormal != null)
    {
      return alarmRecord.getUuid().equals(lastNormal.getUuid());
    }

    BOrdList source = alarmRecord.getSource();
    if (alarmRecord.getSourceState().equals(BSourceState.normal) && source.size() > 0)
    {
      try
      {
        return source.get(0).get().equals(this);
      }
      catch (Exception e)
      {
        log.log(
          Level.FINE,
          "Could not resolve alarm record's source while checking whether it is the last normal record" +
            "; source: [" + source + ']',
          e);
      }
    }

    return false;
  }

////////////////////////////////////////////////////////////////
//  TransitionTimes
////////////////////////////////////////////////////////////////

  /**
   * Return the time of the last transition to offnormal.
   */
  public BAbsTime getLastOffnormalTime()
  {
    return lastOffnormalTime;
  }

  /**
   * Return the time of the last transition to fault.
   */
  public BAbsTime getLastFaultTime()
  {
    return lastFaultTime;
  }

  /**
   * Return the time of the last transition to normal.
   */
  public BAbsTime getLastToNormalTime()
  {
    return lastToNormalTime;
  }

  /**
   * Return the time of the last alarm acknowledgement.
   */
  public BAbsTime getLastAckTime()
  {
    return lastAckTime;
  }

////////////////////////////////////////////////////////////////
//  Icon
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make(BIcon.std("control/controlExtension.png"), BIcon.std("badges/alarm.png"));

////////////////////////////////////////////////////////////////
//  Debug
////////////////////////////////////////////////////////////////

  private static void dump(String s) { System.out.println(s); }

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private static final int NON_ALARM_BITS = ~(BStatus.ALARM | BStatus.FAULT | BStatus.UNACKED_ALARM);
  private Clock.Ticket ticket;
  private Clock.Ticket inhibitTicket;
  private long timeOfLastStateChange;
  private AlarmSupport support;

  private BAbsTime lastOffnormalTime = BAbsTime.NULL;
  private BAbsTime lastFaultTime = BAbsTime.NULL;
  private BAbsTime lastToNormalTime = BAbsTime.NULL;
  private BAbsTime lastAckTime = BAbsTime.NULL;

  private static final Logger log = Logger.getLogger("alarm");

  private boolean atSteadyState;

  private static final String INHIBIT_FACET_TEXT = Lexicon.make("alarm").getText("alarm.alarmInhibit.facet");
}
