/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.control.enums.BPriorityLevel;
import javax.baja.control.util.BBooleanOverride;
import javax.baja.control.util.BOverride;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBooleanWritable defines a writable control point with 16 input levels.
 *
 * @author    Dan Giorgis
 * @creation  02 May 01
 * @version   $Revision: 34$ $Date: 7/27/10 12:19:12 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The last command at this priority level.
 Commands at emergency level 1 are persisted.
 */
@NiagaraProperty(
  name = "in1",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.READONLY
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in2",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in3",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in4",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in5",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 Level 6 is used for min active/inactive timer.
 */
@NiagaraProperty(
  name = "in6",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in7",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 Commands at manual level 8 are persisted.
 */
@NiagaraProperty(
  name = "in8",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.READONLY
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in9",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in10",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in11",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in12",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in13",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in14",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in15",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in16",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
/*
 This is the value to use if none of the levels are valid.
 */
@NiagaraProperty(
  name = "fallback",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)"
)
/*
 Time which override will expire
 */
@NiagaraProperty(
  name = "overrideExpiration",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.OPERATOR | Flags.READONLY
)
/*
 If non-zero then this indicates the min amount of time
 to hold the point in the active state using level 6.
 */
@NiagaraProperty(
  name = "minActiveTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
/*
 If non-zero then this indicates the min amount of time
 to hold the point in the inactive state using level 6.
 */
@NiagaraProperty(
  name = "minInactiveTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
/*
 If true, the minInactive timer will be set
 on station start.
 */
@NiagaraProperty(
  name = "setMinInactiveTimeOnStart",
  type = "boolean",
  defaultValue = "false"
)
/*
 To set an active output at the Emergency level (1).
 */
@NiagaraAction(
  name = "emergencyActive"
)
/*
 To set an inactive output at the Emergency level (1).
 */
@NiagaraAction(
  name = "emergencyInactive"
)
/*
 To clear any active or inactive output at the Emergency level (1).
 */
@NiagaraAction(
  name = "emergencyAuto"
)
/*
 To set an active output at the Manual level (8).
 */
@NiagaraAction(
  name = "active",
  parameterType = "BOverride",
  defaultValue = "new BOverride()",
  flags = Flags.OPERATOR
)
/*
 To set an inactive output at the Manual level (8).
 */
@NiagaraAction(
  name = "inactive",
  parameterType = "BOverride",
  defaultValue = "new BOverride()",
  flags = Flags.OPERATOR
)
/*
 To clear any active or inactive output at the Manual level (8).
 */
@NiagaraAction(
  name = "auto",
  flags = Flags.OPERATOR
)
/*
 Set the fallback property value.
 */
@NiagaraAction(
  name = "set",
  parameterType = "BBoolean",
  defaultValue = "BBoolean.FALSE",
  flags = Flags.OPERATOR
)
/*
 Clears level 6 by setting the null bit.
 */
@NiagaraAction(
  name = "cancelMinTimer",
  flags = Flags.HIDDEN
)
public class BBooleanWritable
  extends BBooleanPoint
  implements BIWritablePoint
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BBooleanWritable(2471690599)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "in1"

  /**
   * Slot for the {@code in1} property.
   * The last command at this priority level.
   * Commands at emergency level 1 are persisted.
   * @see #getIn1
   * @see #setIn1
   */
  public static final Property in1 = newProperty(Flags.READONLY, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in1} property.
   * The last command at this priority level.
   * Commands at emergency level 1 are persisted.
   * @see #in1
   */
  public BStatusBoolean getIn1() { return (BStatusBoolean)get(in1); }

  /**
   * Set the {@code in1} property.
   * The last command at this priority level.
   * Commands at emergency level 1 are persisted.
   * @see #in1
   */
  public void setIn1(BStatusBoolean v) { set(in1, v, null); }

  //endregion Property "in1"

  //region Property "in2"

  /**
   * Slot for the {@code in2} property.
   * The last command at this priority level.
   * @see #getIn2
   * @see #setIn2
   */
  public static final Property in2 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in2} property.
   * The last command at this priority level.
   * @see #in2
   */
  public BStatusBoolean getIn2() { return (BStatusBoolean)get(in2); }

  /**
   * Set the {@code in2} property.
   * The last command at this priority level.
   * @see #in2
   */
  public void setIn2(BStatusBoolean v) { set(in2, v, null); }

  //endregion Property "in2"

  //region Property "in3"

  /**
   * Slot for the {@code in3} property.
   * The last command at this priority level.
   * @see #getIn3
   * @see #setIn3
   */
  public static final Property in3 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in3} property.
   * The last command at this priority level.
   * @see #in3
   */
  public BStatusBoolean getIn3() { return (BStatusBoolean)get(in3); }

  /**
   * Set the {@code in3} property.
   * The last command at this priority level.
   * @see #in3
   */
  public void setIn3(BStatusBoolean v) { set(in3, v, null); }

  //endregion Property "in3"

  //region Property "in4"

  /**
   * Slot for the {@code in4} property.
   * The last command at this priority level.
   * @see #getIn4
   * @see #setIn4
   */
  public static final Property in4 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in4} property.
   * The last command at this priority level.
   * @see #in4
   */
  public BStatusBoolean getIn4() { return (BStatusBoolean)get(in4); }

  /**
   * Set the {@code in4} property.
   * The last command at this priority level.
   * @see #in4
   */
  public void setIn4(BStatusBoolean v) { set(in4, v, null); }

  //endregion Property "in4"

  //region Property "in5"

  /**
   * Slot for the {@code in5} property.
   * The last command at this priority level.
   * @see #getIn5
   * @see #setIn5
   */
  public static final Property in5 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in5} property.
   * The last command at this priority level.
   * @see #in5
   */
  public BStatusBoolean getIn5() { return (BStatusBoolean)get(in5); }

  /**
   * Set the {@code in5} property.
   * The last command at this priority level.
   * @see #in5
   */
  public void setIn5(BStatusBoolean v) { set(in5, v, null); }

  //endregion Property "in5"

  //region Property "in6"

  /**
   * Slot for the {@code in6} property.
   * Level 6 is used for min active/inactive timer.
   * @see #getIn6
   * @see #setIn6
   */
  public static final Property in6 = newProperty(Flags.TRANSIENT | Flags.READONLY, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in6} property.
   * Level 6 is used for min active/inactive timer.
   * @see #in6
   */
  public BStatusBoolean getIn6() { return (BStatusBoolean)get(in6); }

  /**
   * Set the {@code in6} property.
   * Level 6 is used for min active/inactive timer.
   * @see #in6
   */
  public void setIn6(BStatusBoolean v) { set(in6, v, null); }

  //endregion Property "in6"

  //region Property "in7"

  /**
   * Slot for the {@code in7} property.
   * The last command at this priority level.
   * @see #getIn7
   * @see #setIn7
   */
  public static final Property in7 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in7} property.
   * The last command at this priority level.
   * @see #in7
   */
  public BStatusBoolean getIn7() { return (BStatusBoolean)get(in7); }

  /**
   * Set the {@code in7} property.
   * The last command at this priority level.
   * @see #in7
   */
  public void setIn7(BStatusBoolean v) { set(in7, v, null); }

  //endregion Property "in7"

  //region Property "in8"

  /**
   * Slot for the {@code in8} property.
   * The last command at this priority level.
   * Commands at manual level 8 are persisted.
   * @see #getIn8
   * @see #setIn8
   */
  public static final Property in8 = newProperty(Flags.READONLY, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in8} property.
   * The last command at this priority level.
   * Commands at manual level 8 are persisted.
   * @see #in8
   */
  public BStatusBoolean getIn8() { return (BStatusBoolean)get(in8); }

  /**
   * Set the {@code in8} property.
   * The last command at this priority level.
   * Commands at manual level 8 are persisted.
   * @see #in8
   */
  public void setIn8(BStatusBoolean v) { set(in8, v, null); }

  //endregion Property "in8"

  //region Property "in9"

  /**
   * Slot for the {@code in9} property.
   * The last command at this priority level.
   * @see #getIn9
   * @see #setIn9
   */
  public static final Property in9 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in9} property.
   * The last command at this priority level.
   * @see #in9
   */
  public BStatusBoolean getIn9() { return (BStatusBoolean)get(in9); }

  /**
   * Set the {@code in9} property.
   * The last command at this priority level.
   * @see #in9
   */
  public void setIn9(BStatusBoolean v) { set(in9, v, null); }

  //endregion Property "in9"

  //region Property "in10"

  /**
   * Slot for the {@code in10} property.
   * The last command at this priority level.
   * @see #getIn10
   * @see #setIn10
   */
  public static final Property in10 = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in10} property.
   * The last command at this priority level.
   * @see #in10
   */
  public BStatusBoolean getIn10() { return (BStatusBoolean)get(in10); }

  /**
   * Set the {@code in10} property.
   * The last command at this priority level.
   * @see #in10
   */
  public void setIn10(BStatusBoolean v) { set(in10, v, null); }

  //endregion Property "in10"

  //region Property "in11"

  /**
   * Slot for the {@code in11} property.
   * The last command at this priority level.
   * @see #getIn11
   * @see #setIn11
   */
  public static final Property in11 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in11} property.
   * The last command at this priority level.
   * @see #in11
   */
  public BStatusBoolean getIn11() { return (BStatusBoolean)get(in11); }

  /**
   * Set the {@code in11} property.
   * The last command at this priority level.
   * @see #in11
   */
  public void setIn11(BStatusBoolean v) { set(in11, v, null); }

  //endregion Property "in11"

  //region Property "in12"

  /**
   * Slot for the {@code in12} property.
   * The last command at this priority level.
   * @see #getIn12
   * @see #setIn12
   */
  public static final Property in12 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in12} property.
   * The last command at this priority level.
   * @see #in12
   */
  public BStatusBoolean getIn12() { return (BStatusBoolean)get(in12); }

  /**
   * Set the {@code in12} property.
   * The last command at this priority level.
   * @see #in12
   */
  public void setIn12(BStatusBoolean v) { set(in12, v, null); }

  //endregion Property "in12"

  //region Property "in13"

  /**
   * Slot for the {@code in13} property.
   * The last command at this priority level.
   * @see #getIn13
   * @see #setIn13
   */
  public static final Property in13 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in13} property.
   * The last command at this priority level.
   * @see #in13
   */
  public BStatusBoolean getIn13() { return (BStatusBoolean)get(in13); }

  /**
   * Set the {@code in13} property.
   * The last command at this priority level.
   * @see #in13
   */
  public void setIn13(BStatusBoolean v) { set(in13, v, null); }

  //endregion Property "in13"

  //region Property "in14"

  /**
   * Slot for the {@code in14} property.
   * The last command at this priority level.
   * @see #getIn14
   * @see #setIn14
   */
  public static final Property in14 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in14} property.
   * The last command at this priority level.
   * @see #in14
   */
  public BStatusBoolean getIn14() { return (BStatusBoolean)get(in14); }

  /**
   * Set the {@code in14} property.
   * The last command at this priority level.
   * @see #in14
   */
  public void setIn14(BStatusBoolean v) { set(in14, v, null); }

  //endregion Property "in14"

  //region Property "in15"

  /**
   * Slot for the {@code in15} property.
   * The last command at this priority level.
   * @see #getIn15
   * @see #setIn15
   */
  public static final Property in15 = newProperty(Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in15} property.
   * The last command at this priority level.
   * @see #in15
   */
  public BStatusBoolean getIn15() { return (BStatusBoolean)get(in15); }

  /**
   * Set the {@code in15} property.
   * The last command at this priority level.
   * @see #in15
   */
  public void setIn15(BStatusBoolean v) { set(in15, v, null); }

  //endregion Property "in15"

  //region Property "in16"

  /**
   * Slot for the {@code in16} property.
   * The last command at this priority level.
   * @see #getIn16
   * @see #setIn16
   */
  public static final Property in16 = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in16} property.
   * The last command at this priority level.
   * @see #in16
   */
  public BStatusBoolean getIn16() { return (BStatusBoolean)get(in16); }

  /**
   * Set the {@code in16} property.
   * The last command at this priority level.
   * @see #in16
   */
  public void setIn16(BStatusBoolean v) { set(in16, v, null); }

  //endregion Property "in16"

  //region Property "fallback"

  /**
   * Slot for the {@code fallback} property.
   * This is the value to use if none of the levels are valid.
   * @see #getFallback
   * @see #setFallback
   */
  public static final Property fallback = newProperty(0, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code fallback} property.
   * This is the value to use if none of the levels are valid.
   * @see #fallback
   */
  public BStatusBoolean getFallback() { return (BStatusBoolean)get(fallback); }

  /**
   * Set the {@code fallback} property.
   * This is the value to use if none of the levels are valid.
   * @see #fallback
   */
  public void setFallback(BStatusBoolean v) { set(fallback, v, null); }

  //endregion Property "fallback"

  //region Property "overrideExpiration"

  /**
   * Slot for the {@code overrideExpiration} property.
   * Time which override will expire
   * @see #getOverrideExpiration
   * @see #setOverrideExpiration
   */
  public static final Property overrideExpiration = newProperty(Flags.OPERATOR | Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code overrideExpiration} property.
   * Time which override will expire
   * @see #overrideExpiration
   */
  public BAbsTime getOverrideExpiration() { return (BAbsTime)get(overrideExpiration); }

  /**
   * Set the {@code overrideExpiration} property.
   * Time which override will expire
   * @see #overrideExpiration
   */
  public void setOverrideExpiration(BAbsTime v) { set(overrideExpiration, v, null); }

  //endregion Property "overrideExpiration"

  //region Property "minActiveTime"

  /**
   * Slot for the {@code minActiveTime} property.
   * If non-zero then this indicates the min amount of time
   * to hold the point in the active state using level 6.
   * @see #getMinActiveTime
   * @see #setMinActiveTime
   */
  public static final Property minActiveTime = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code minActiveTime} property.
   * If non-zero then this indicates the min amount of time
   * to hold the point in the active state using level 6.
   * @see #minActiveTime
   */
  public BRelTime getMinActiveTime() { return (BRelTime)get(minActiveTime); }

  /**
   * Set the {@code minActiveTime} property.
   * If non-zero then this indicates the min amount of time
   * to hold the point in the active state using level 6.
   * @see #minActiveTime
   */
  public void setMinActiveTime(BRelTime v) { set(minActiveTime, v, null); }

  //endregion Property "minActiveTime"

  //region Property "minInactiveTime"

  /**
   * Slot for the {@code minInactiveTime} property.
   * If non-zero then this indicates the min amount of time
   * to hold the point in the inactive state using level 6.
   * @see #getMinInactiveTime
   * @see #setMinInactiveTime
   */
  public static final Property minInactiveTime = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code minInactiveTime} property.
   * If non-zero then this indicates the min amount of time
   * to hold the point in the inactive state using level 6.
   * @see #minInactiveTime
   */
  public BRelTime getMinInactiveTime() { return (BRelTime)get(minInactiveTime); }

  /**
   * Set the {@code minInactiveTime} property.
   * If non-zero then this indicates the min amount of time
   * to hold the point in the inactive state using level 6.
   * @see #minInactiveTime
   */
  public void setMinInactiveTime(BRelTime v) { set(minInactiveTime, v, null); }

  //endregion Property "minInactiveTime"

  //region Property "setMinInactiveTimeOnStart"

  /**
   * Slot for the {@code setMinInactiveTimeOnStart} property.
   * If true, the minInactive timer will be set
   * on station start.
   * @see #getSetMinInactiveTimeOnStart
   * @see #setSetMinInactiveTimeOnStart
   */
  public static final Property setMinInactiveTimeOnStart = newProperty(0, false, null);

  /**
   * Get the {@code setMinInactiveTimeOnStart} property.
   * If true, the minInactive timer will be set
   * on station start.
   * @see #setMinInactiveTimeOnStart
   */
  public boolean getSetMinInactiveTimeOnStart() { return getBoolean(setMinInactiveTimeOnStart); }

  /**
   * Set the {@code setMinInactiveTimeOnStart} property.
   * If true, the minInactive timer will be set
   * on station start.
   * @see #setMinInactiveTimeOnStart
   */
  public void setSetMinInactiveTimeOnStart(boolean v) { setBoolean(setMinInactiveTimeOnStart, v, null); }

  //endregion Property "setMinInactiveTimeOnStart"

  //region Action "emergencyActive"

  /**
   * Slot for the {@code emergencyActive} action.
   * To set an active output at the Emergency level (1).
   * @see #emergencyActive()
   */
  public static final Action emergencyActive = newAction(0, null);

  /**
   * Invoke the {@code emergencyActive} action.
   * To set an active output at the Emergency level (1).
   * @see #emergencyActive
   */
  public void emergencyActive() { invoke(emergencyActive, null, null); }

  //endregion Action "emergencyActive"

  //region Action "emergencyInactive"

  /**
   * Slot for the {@code emergencyInactive} action.
   * To set an inactive output at the Emergency level (1).
   * @see #emergencyInactive()
   */
  public static final Action emergencyInactive = newAction(0, null);

  /**
   * Invoke the {@code emergencyInactive} action.
   * To set an inactive output at the Emergency level (1).
   * @see #emergencyInactive
   */
  public void emergencyInactive() { invoke(emergencyInactive, null, null); }

  //endregion Action "emergencyInactive"

  //region Action "emergencyAuto"

  /**
   * Slot for the {@code emergencyAuto} action.
   * To clear any active or inactive output at the Emergency level (1).
   * @see #emergencyAuto()
   */
  public static final Action emergencyAuto = newAction(0, null);

  /**
   * Invoke the {@code emergencyAuto} action.
   * To clear any active or inactive output at the Emergency level (1).
   * @see #emergencyAuto
   */
  public void emergencyAuto() { invoke(emergencyAuto, null, null); }

  //endregion Action "emergencyAuto"

  //region Action "active"

  /**
   * Slot for the {@code active} action.
   * To set an active output at the Manual level (8).
   * @see #active(BOverride parameter)
   */
  public static final Action active = newAction(Flags.OPERATOR, new BOverride(), null);

  /**
   * Invoke the {@code active} action.
   * To set an active output at the Manual level (8).
   * @see #active
   */
  public void active(BOverride parameter) { invoke(active, parameter, null); }

  //endregion Action "active"

  //region Action "inactive"

  /**
   * Slot for the {@code inactive} action.
   * To set an inactive output at the Manual level (8).
   * @see #inactive(BOverride parameter)
   */
  public static final Action inactive = newAction(Flags.OPERATOR, new BOverride(), null);

  /**
   * Invoke the {@code inactive} action.
   * To set an inactive output at the Manual level (8).
   * @see #inactive
   */
  public void inactive(BOverride parameter) { invoke(inactive, parameter, null); }

  //endregion Action "inactive"

  //region Action "auto"

  /**
   * Slot for the {@code auto} action.
   * To clear any active or inactive output at the Manual level (8).
   * @see #auto()
   */
  public static final Action auto = newAction(Flags.OPERATOR, null);

  /**
   * Invoke the {@code auto} action.
   * To clear any active or inactive output at the Manual level (8).
   * @see #auto
   */
  public void auto() { invoke(auto, null, null); }

  //endregion Action "auto"

  //region Action "set"

  /**
   * Slot for the {@code set} action.
   * Set the fallback property value.
   * @see #set(BBoolean parameter)
   */
  public static final Action set = newAction(Flags.OPERATOR, BBoolean.FALSE, null);

  /**
   * Invoke the {@code set} action.
   * Set the fallback property value.
   * @see #set
   */
  public void set(BBoolean parameter) { invoke(set, parameter, null); }

  //endregion Action "set"

  //region Action "cancelMinTimer"

  /**
   * Slot for the {@code cancelMinTimer} action.
   * Clears level 6 by setting the null bit.
   * @see #cancelMinTimer()
   */
  public static final Action cancelMinTimer = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code cancelMinTimer} action.
   * Clears level 6 by setting the null bit.
   * @see #cancelMinTimer
   */
  public void cancelMinTimer() { invoke(cancelMinTimer, null, null); }

  //endregion Action "cancelMinTimer"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanWritable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////

  public void started()
  throws Exception
  {
    super.started();
    support.started();

    if(getSetMinInactiveTimeOnStart())
    {
      BRelTime minTime = getMinInactiveTime();

      // if > 0 then we have a timer set
      if (minTime.getMillis() > 0)
      {
        // set level 6
        getIn6().setValue(false);
        getIn6().setStatus(BStatus.ok);
        // setup timer to clear
        minTimer = Clock.schedule(this, minTime, cancelMinTimer, null);
      }
    }
  }

  public final boolean isWritablePoint()
  {
    return true;
  }

  public BValue getActionParameterDefault(Action action)
  {
    if (action == set) return getFallback().getValueValue();
    if ((action == active) || (action == inactive))
    {
      BOverride o = new BOverride();
      o.setDuration(support.getMaxOverrideDuration()); 
      o.setMaxOverrideDuration(support.getMaxOverrideDuration());
      return o;
    }
    return super.getActionParameterDefault(action);
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if (support.isInput(slot) ||
        slot == fallback ||
        slot == set)
      return getFacets();

    return super.getSlotFacets(slot);
  }

  public void onExecute(BStatusValue o, Context cx)
  {
    support.onExecute(o, cx);

    // check min active/inactive
    boolean nowActive = ((BStatusBoolean)o).getValue();
    if (wasActive != nowActive)
    {
      // cancel existing timer
      if (minTimer != null) minTimer.cancel();

      // get min to hold in current state
      BRelTime minTime;
      if (nowActive)
        minTime = getMinActiveTime();
      else
        minTime = getMinInactiveTime();

      // if > 0 then we have a timer set
      if (minTime.getMillis() > 0)
      {
        // set level 6
        getIn6().setValue(nowActive);
        getIn6().setStatus(BStatus.ok);

        // run thru execute logic again in case
        // level 6 changes the active level
        support.onExecute(o, cx);

        // setup timer to clear
        minTimer = Clock.schedule(this, minTime, cancelMinTimer, null);
      }

      // save "now" as "was"
      wasActive = nowActive;
    }
  }

  WritableSupport writableSupport()
  {
    return support;
  }

////////////////////////////////////////////////////////////////
// IWritablePoint
////////////////////////////////////////////////////////////////

  public final BPriorityLevel getActiveLevel()
  {
    return support.getActiveLevel();
  }

  public final BStatusValue getInStatusValue(BPriorityLevel level)
  {
    return support.getLevel(level.getOrdinal());
  }

  public final Property getInProperty(BPriorityLevel level)
  {
    return support.getLevel(level.getOrdinal()).getPropertyInParent();
  }

  public final BStatusBoolean getLevel(BPriorityLevel level)
  {
    return (BStatusBoolean)support.getLevel(level.getOrdinal());
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doEmergencyActive()
  {
    support.emergencyOverride(BBoolean.TRUE);
  }

  public void doEmergencyInactive()
  {
    support.emergencyOverride(BBoolean.FALSE);
  }

  public void doEmergencyAuto()
  {
    support.emergencyAuto();
  }

  public void doActive(BOverride override)
  {
    support.override(new BBooleanOverride(override.getDuration(), true));
  }

  public void doInactive(BOverride override)
  {
    support.override(new BBooleanOverride(override.getDuration(), false));
  }

  public void doAuto()
  {
    support.auto();
  }

  public void doSet(BBoolean v)
  {
    support.set(v);
  }

  public void doCancelMinTimer()
  {
    getIn6().setStatus(BStatus.nullStatus);
  }

////////////////////////////////////////////////////////////////
// WritableSupport
////////////////////////////////////////////////////////////////

  final class BooleanWritableSupport extends WritableSupport
  {
    BooleanWritableSupport(BBooleanWritable pt) { super(pt); }

    Property in1()  { return in1; }
    Property in2()  { return in2; }
    Property in3()  { return in3; }
    Property in4()  { return in4; }
    Property in5()  { return in5; }
    Property in6()  { return in6; }
    Property in7()  { return in7; }
    Property in8()  { return in8; }
    Property in9()  { return in9; }
    Property in10() { return in10; }
    Property in11() { return in11; }
    Property in12() { return in12; }
    Property in13() { return in13; }
    Property in14() { return in14; }
    Property in15() { return in15; }
    Property in16() { return in16; }

    BStatusValue getFallback()
    {
      return BBooleanWritable.this.getFallback();
    }

    void setOverrideExpiration(BAbsTime time)
    {
      BBooleanWritable.this.setOverrideExpiration(time);
    }

    BAbsTime getOverrideExpiration()
    {
      return BBooleanWritable.this.getOverrideExpiration();
    }

    void setValue(BStatusValue from, BStatusValue to)
    {
      ((BStatusBoolean)to).setValue( ((BStatusBoolean)from).getValue() );
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  WritableSupport support = new BooleanWritableSupport(this);

  boolean wasActive;
  Clock.Ticket minTimer;
}
