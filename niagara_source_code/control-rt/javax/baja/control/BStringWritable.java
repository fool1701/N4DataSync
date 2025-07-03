/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.control.enums.BPriorityLevel;
import javax.baja.control.util.BStringOverride;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.util.ObjectUtil;

/**
 * BStringWritable defines a writable control point with 16 input levels.
 *
 * @author    Craig Gemmill
 * @creation  11 Feb 02
 * @version   $Revision: 28$ $Date: 7/27/10 12:19:15 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The last command at this priority level.
 Commands at emergency level 1 are persisted.
 */
@NiagaraProperty(
  name = "in1",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.READONLY
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in2",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in3",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in4",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in5",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in6",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in7",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 Commands at manual level 8 are persisted.
 */
@NiagaraProperty(
  name = "in8",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.READONLY
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in9",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in10",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in11",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in12",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in13",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in14",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in15",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.TRANSIENT
)
/*
 The last command at this priority level.
 */
@NiagaraProperty(
  name = "in16",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
/*
 This is the value to use if none of the levels are valid.
 */
@NiagaraProperty(
  name = "fallback",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)"
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
 To set an output value at the Emergency level (1).
 */
@NiagaraAction(
  name = "emergencyOverride",
  parameterType = "BString",
  defaultValue = "BString.make(\"\")"
)
/*
 To clear an output value set at the Emergency level (1).
 */
@NiagaraAction(
  name = "emergencyAuto"
)
/*
 To set an output value at the Manual level (8).
 */
@NiagaraAction(
  name = "override",
  parameterType = "BStringOverride",
  defaultValue = "new BStringOverride()",
  flags = Flags.OPERATOR
)
/*
 To clear an output value set at the Manual level (8).
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
  parameterType = "BString",
  defaultValue = "BString.DEFAULT",
  flags = Flags.OPERATOR
)
public class BStringWritable
  extends BStringPoint
  implements BIWritablePoint
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BStringWritable(1099567032)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "in1"

  /**
   * Slot for the {@code in1} property.
   * The last command at this priority level.
   * Commands at emergency level 1 are persisted.
   * @see #getIn1
   * @see #setIn1
   */
  public static final Property in1 = newProperty(Flags.READONLY, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in1} property.
   * The last command at this priority level.
   * Commands at emergency level 1 are persisted.
   * @see #in1
   */
  public BStatusString getIn1() { return (BStatusString)get(in1); }

  /**
   * Set the {@code in1} property.
   * The last command at this priority level.
   * Commands at emergency level 1 are persisted.
   * @see #in1
   */
  public void setIn1(BStatusString v) { set(in1, v, null); }

  //endregion Property "in1"

  //region Property "in2"

  /**
   * Slot for the {@code in2} property.
   * The last command at this priority level.
   * @see #getIn2
   * @see #setIn2
   */
  public static final Property in2 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in2} property.
   * The last command at this priority level.
   * @see #in2
   */
  public BStatusString getIn2() { return (BStatusString)get(in2); }

  /**
   * Set the {@code in2} property.
   * The last command at this priority level.
   * @see #in2
   */
  public void setIn2(BStatusString v) { set(in2, v, null); }

  //endregion Property "in2"

  //region Property "in3"

  /**
   * Slot for the {@code in3} property.
   * The last command at this priority level.
   * @see #getIn3
   * @see #setIn3
   */
  public static final Property in3 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in3} property.
   * The last command at this priority level.
   * @see #in3
   */
  public BStatusString getIn3() { return (BStatusString)get(in3); }

  /**
   * Set the {@code in3} property.
   * The last command at this priority level.
   * @see #in3
   */
  public void setIn3(BStatusString v) { set(in3, v, null); }

  //endregion Property "in3"

  //region Property "in4"

  /**
   * Slot for the {@code in4} property.
   * The last command at this priority level.
   * @see #getIn4
   * @see #setIn4
   */
  public static final Property in4 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in4} property.
   * The last command at this priority level.
   * @see #in4
   */
  public BStatusString getIn4() { return (BStatusString)get(in4); }

  /**
   * Set the {@code in4} property.
   * The last command at this priority level.
   * @see #in4
   */
  public void setIn4(BStatusString v) { set(in4, v, null); }

  //endregion Property "in4"

  //region Property "in5"

  /**
   * Slot for the {@code in5} property.
   * The last command at this priority level.
   * @see #getIn5
   * @see #setIn5
   */
  public static final Property in5 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in5} property.
   * The last command at this priority level.
   * @see #in5
   */
  public BStatusString getIn5() { return (BStatusString)get(in5); }

  /**
   * Set the {@code in5} property.
   * The last command at this priority level.
   * @see #in5
   */
  public void setIn5(BStatusString v) { set(in5, v, null); }

  //endregion Property "in5"

  //region Property "in6"

  /**
   * Slot for the {@code in6} property.
   * The last command at this priority level.
   * @see #getIn6
   * @see #setIn6
   */
  public static final Property in6 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in6} property.
   * The last command at this priority level.
   * @see #in6
   */
  public BStatusString getIn6() { return (BStatusString)get(in6); }

  /**
   * Set the {@code in6} property.
   * The last command at this priority level.
   * @see #in6
   */
  public void setIn6(BStatusString v) { set(in6, v, null); }

  //endregion Property "in6"

  //region Property "in7"

  /**
   * Slot for the {@code in7} property.
   * The last command at this priority level.
   * @see #getIn7
   * @see #setIn7
   */
  public static final Property in7 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in7} property.
   * The last command at this priority level.
   * @see #in7
   */
  public BStatusString getIn7() { return (BStatusString)get(in7); }

  /**
   * Set the {@code in7} property.
   * The last command at this priority level.
   * @see #in7
   */
  public void setIn7(BStatusString v) { set(in7, v, null); }

  //endregion Property "in7"

  //region Property "in8"

  /**
   * Slot for the {@code in8} property.
   * The last command at this priority level.
   * Commands at manual level 8 are persisted.
   * @see #getIn8
   * @see #setIn8
   */
  public static final Property in8 = newProperty(Flags.READONLY, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in8} property.
   * The last command at this priority level.
   * Commands at manual level 8 are persisted.
   * @see #in8
   */
  public BStatusString getIn8() { return (BStatusString)get(in8); }

  /**
   * Set the {@code in8} property.
   * The last command at this priority level.
   * Commands at manual level 8 are persisted.
   * @see #in8
   */
  public void setIn8(BStatusString v) { set(in8, v, null); }

  //endregion Property "in8"

  //region Property "in9"

  /**
   * Slot for the {@code in9} property.
   * The last command at this priority level.
   * @see #getIn9
   * @see #setIn9
   */
  public static final Property in9 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in9} property.
   * The last command at this priority level.
   * @see #in9
   */
  public BStatusString getIn9() { return (BStatusString)get(in9); }

  /**
   * Set the {@code in9} property.
   * The last command at this priority level.
   * @see #in9
   */
  public void setIn9(BStatusString v) { set(in9, v, null); }

  //endregion Property "in9"

  //region Property "in10"

  /**
   * Slot for the {@code in10} property.
   * The last command at this priority level.
   * @see #getIn10
   * @see #setIn10
   */
  public static final Property in10 = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in10} property.
   * The last command at this priority level.
   * @see #in10
   */
  public BStatusString getIn10() { return (BStatusString)get(in10); }

  /**
   * Set the {@code in10} property.
   * The last command at this priority level.
   * @see #in10
   */
  public void setIn10(BStatusString v) { set(in10, v, null); }

  //endregion Property "in10"

  //region Property "in11"

  /**
   * Slot for the {@code in11} property.
   * The last command at this priority level.
   * @see #getIn11
   * @see #setIn11
   */
  public static final Property in11 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in11} property.
   * The last command at this priority level.
   * @see #in11
   */
  public BStatusString getIn11() { return (BStatusString)get(in11); }

  /**
   * Set the {@code in11} property.
   * The last command at this priority level.
   * @see #in11
   */
  public void setIn11(BStatusString v) { set(in11, v, null); }

  //endregion Property "in11"

  //region Property "in12"

  /**
   * Slot for the {@code in12} property.
   * The last command at this priority level.
   * @see #getIn12
   * @see #setIn12
   */
  public static final Property in12 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in12} property.
   * The last command at this priority level.
   * @see #in12
   */
  public BStatusString getIn12() { return (BStatusString)get(in12); }

  /**
   * Set the {@code in12} property.
   * The last command at this priority level.
   * @see #in12
   */
  public void setIn12(BStatusString v) { set(in12, v, null); }

  //endregion Property "in12"

  //region Property "in13"

  /**
   * Slot for the {@code in13} property.
   * The last command at this priority level.
   * @see #getIn13
   * @see #setIn13
   */
  public static final Property in13 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in13} property.
   * The last command at this priority level.
   * @see #in13
   */
  public BStatusString getIn13() { return (BStatusString)get(in13); }

  /**
   * Set the {@code in13} property.
   * The last command at this priority level.
   * @see #in13
   */
  public void setIn13(BStatusString v) { set(in13, v, null); }

  //endregion Property "in13"

  //region Property "in14"

  /**
   * Slot for the {@code in14} property.
   * The last command at this priority level.
   * @see #getIn14
   * @see #setIn14
   */
  public static final Property in14 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in14} property.
   * The last command at this priority level.
   * @see #in14
   */
  public BStatusString getIn14() { return (BStatusString)get(in14); }

  /**
   * Set the {@code in14} property.
   * The last command at this priority level.
   * @see #in14
   */
  public void setIn14(BStatusString v) { set(in14, v, null); }

  //endregion Property "in14"

  //region Property "in15"

  /**
   * Slot for the {@code in15} property.
   * The last command at this priority level.
   * @see #getIn15
   * @see #setIn15
   */
  public static final Property in15 = newProperty(Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in15} property.
   * The last command at this priority level.
   * @see #in15
   */
  public BStatusString getIn15() { return (BStatusString)get(in15); }

  /**
   * Set the {@code in15} property.
   * The last command at this priority level.
   * @see #in15
   */
  public void setIn15(BStatusString v) { set(in15, v, null); }

  //endregion Property "in15"

  //region Property "in16"

  /**
   * Slot for the {@code in16} property.
   * The last command at this priority level.
   * @see #getIn16
   * @see #setIn16
   */
  public static final Property in16 = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in16} property.
   * The last command at this priority level.
   * @see #in16
   */
  public BStatusString getIn16() { return (BStatusString)get(in16); }

  /**
   * Set the {@code in16} property.
   * The last command at this priority level.
   * @see #in16
   */
  public void setIn16(BStatusString v) { set(in16, v, null); }

  //endregion Property "in16"

  //region Property "fallback"

  /**
   * Slot for the {@code fallback} property.
   * This is the value to use if none of the levels are valid.
   * @see #getFallback
   * @see #setFallback
   */
  public static final Property fallback = newProperty(0, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code fallback} property.
   * This is the value to use if none of the levels are valid.
   * @see #fallback
   */
  public BStatusString getFallback() { return (BStatusString)get(fallback); }

  /**
   * Set the {@code fallback} property.
   * This is the value to use if none of the levels are valid.
   * @see #fallback
   */
  public void setFallback(BStatusString v) { set(fallback, v, null); }

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

  //region Action "emergencyOverride"

  /**
   * Slot for the {@code emergencyOverride} action.
   * To set an output value at the Emergency level (1).
   * @see #emergencyOverride(BString parameter)
   */
  public static final Action emergencyOverride = newAction(0, BString.make(""), null);

  /**
   * Invoke the {@code emergencyOverride} action.
   * To set an output value at the Emergency level (1).
   * @see #emergencyOverride
   */
  public void emergencyOverride(BString parameter) { invoke(emergencyOverride, parameter, null); }

  //endregion Action "emergencyOverride"

  //region Action "emergencyAuto"

  /**
   * Slot for the {@code emergencyAuto} action.
   * To clear an output value set at the Emergency level (1).
   * @see #emergencyAuto()
   */
  public static final Action emergencyAuto = newAction(0, null);

  /**
   * Invoke the {@code emergencyAuto} action.
   * To clear an output value set at the Emergency level (1).
   * @see #emergencyAuto
   */
  public void emergencyAuto() { invoke(emergencyAuto, null, null); }

  //endregion Action "emergencyAuto"

  //region Action "override"

  /**
   * Slot for the {@code override} action.
   * To set an output value at the Manual level (8).
   * @see #override(BStringOverride parameter)
   */
  public static final Action override = newAction(Flags.OPERATOR, new BStringOverride(), null);

  /**
   * Invoke the {@code override} action.
   * To set an output value at the Manual level (8).
   * @see #override
   */
  public void override(BStringOverride parameter) { invoke(override, parameter, null); }

  //endregion Action "override"

  //region Action "auto"

  /**
   * Slot for the {@code auto} action.
   * To clear an output value set at the Manual level (8).
   * @see #auto()
   */
  public static final Action auto = newAction(Flags.OPERATOR, null);

  /**
   * Invoke the {@code auto} action.
   * To clear an output value set at the Manual level (8).
   * @see #auto
   */
  public void auto() { invoke(auto, null, null); }

  //endregion Action "auto"

  //region Action "set"

  /**
   * Slot for the {@code set} action.
   * Set the fallback property value.
   * @see #set(BString parameter)
   */
  public static final Action set = newAction(Flags.OPERATOR, BString.DEFAULT, null);

  /**
   * Invoke the {@code set} action.
   * Set the fallback property value.
   * @see #set
   */
  public void set(BString parameter) { invoke(set, parameter, null); }

  //endregion Action "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringWritable.class);

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
  }

  public final boolean isWritablePoint()
  {
    return true;
  }

  public BValue getActionParameterDefault(Action action)
  {
    if (action == set) return getFallback().getValueValue();
    if (action == override)
    {
      BStringOverride o = new BStringOverride(support.getMaxOverrideDuration(),getOut().getValue());
      o.setMaxOverrideDuration(support.getMaxOverrideDuration());
      return o;
    }
    if (action == emergencyOverride) return getOut().getValueValue();
    return super.getActionParameterDefault(action);
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if (support.isInput(slot) ||
        slot == fallback ||
        slot == override ||
        slot == emergencyOverride ||
        slot == set)
      return getFacets();

    return super.getSlotFacets(slot);
  }

  public void onExecute(BStatusValue o, Context cx)
  {
    support.onExecute(o, cx);
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

  public final BStatusString getLevel(BPriorityLevel level)
  {
    return (BStatusString)support.getLevel(level.getOrdinal());
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doEmergencyOverride(BString v)
  {
    support.emergencyOverride(v);
  }

  public void doEmergencyAuto()
  {
    support.emergencyAuto();
  }

  public void doOverride(BStringOverride v)
  {
    support.override(v);
  }

  public void doAuto()
  {
    support.auto();
  }

  public void doSet(BString v)
  {
    support.set(v);
  }

  /**
   * Callback when the given Action is invoked with the given argument and Context. Overridden
   * here to check if the given Context has a non-null user (indicating server-side handling of
   * a user-invoked Action) and the action is a {@link #set}, {@link #override}, or
   * {@link #emergencyOverride}. If so, then a String length check is optionally performed against
   * the action argument if any MIN/MAX facets are specified for the value of this point.
   *
   * @since Niagara 4.10u8
   */
  @Override
  void fwCheckActionInvocation(Action action, BValue arg, Context context)
  {
    if (!ObjectUtil.SERVER_MIN_MAX_CHECK_DISABLED && context != null && context.getUser() != null &&
        (action.equals(set) || action.equals(override) || action.equals(emergencyOverride)))
    {
      ObjectUtil.checkMinMaxFacetsOnValue(
        arg instanceof BStringOverride ? arg.asComplex().get(BStringOverride.value) : arg,
        getSlotFacets(action), context
      );
    }
  }

////////////////////////////////////////////////////////////////
// WritableSupport
////////////////////////////////////////////////////////////////

  final class StringWritableSupport extends WritableSupport
  {
    StringWritableSupport(BStringWritable pt) { super(pt); }

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
      return BStringWritable.this.getFallback();
    }

    void setOverrideExpiration(BAbsTime time)
    {
      BStringWritable.this.setOverrideExpiration(time);
    }

    BAbsTime getOverrideExpiration()
    {
      return BStringWritable.this.getOverrideExpiration();
    }

    void setValue(BStatusValue from, BStatusValue to)
    {
      ((BStatusString)to).setValue( ((BStatusString)from).getValue() );
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  WritableSupport support = new StringWritableSupport(this);
}
