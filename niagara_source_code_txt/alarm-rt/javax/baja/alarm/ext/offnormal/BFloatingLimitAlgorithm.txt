/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

import java.util.Map;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.BAlarmState;
import javax.baja.alarm.ext.BLimitEnable;
import javax.baja.alarm.ext.BOffnormalAlgorithm;
import javax.baja.control.BNumericPoint;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.util.BFormat;

/**
 * BFloatingLimitAlgorithm implements the floating-limit event algorithm as described in BACnet.
 * The algorithm behaves like a standard out-of-range alarming algorithm except that the range is
 * based on a dynamic setpoint value rather than being fixed.
 *
 * @author Uday Rapuru on 28 Jan 22
 * @since Niagara 4.10.3
 * @since Niagara 4.11.1
 * @since Niagara 4.12.0
 */

@NiagaraType
/*
 Value that, when its status and value is valid, is the basis for defining the range considered
 normal.
 */
@NiagaraProperty(
  name = "setpoint",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()"
)
/*
 The setpoint value when its status and value were last valid.
 */
@NiagaraProperty(
  name = "lastValidSetpoint",
  type = "double",
  defaultValue = "0.0",
  flags = Flags.READONLY
)
/*
 Value is subtracted from the last valid setpoint value to determine the low limit of the range
 considered normal.
 */
@NiagaraProperty(
  name = "lowDiffLimit",
  type = "double",
  defaultValue = "0.0",
  facets = @Facet(name = "BFacets.MIN", value = "BDouble.make(0.0)")
)
/*
 Value is added to the last valid setpoint value to determine the high limit of the range considered
 normal.
 */
@NiagaraProperty(
  name = "highDiffLimit",
  type = "double",
  defaultValue = "0.0",
  facets = @Facet(name = "BFacets.MIN", value = "BDouble.make(0.0)")
)
/*
 Differential value applied to high and low limits before return-to-normal. This value is subtracted
 from the high limit and added to low limit.
 */
@NiagaraProperty(
  name = "deadband",
  type = "double",
  defaultValue = "0.0",
  facets = @Facet(name = "BFacets.MIN", value = "BDouble.make(0.0)")
)
/*
 Text descriptor included in a to-low-limit alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "lowLimitText",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT",
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "true")
)
/*
 Text descriptor included in a to-high-limit alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "highLimitText",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT",
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "true")
)
/*
 Flags that enable the low-limit and high-limit alarms, as needed.
 */
@NiagaraProperty(
  name = "limitEnable",
  type = "BLimitEnable",
  defaultValue = "new BLimitEnable()"
)
public final class BFloatingLimitAlgorithm
  extends BOffnormalAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BFloatingLimitAlgorithm(380725965)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "setpoint"

  /**
   * Slot for the {@code setpoint} property.
   * Value that, when its status and value is valid, is the basis for defining the range considered
   * normal.
   * @see #getSetpoint
   * @see #setSetpoint
   */
  public static final Property setpoint = newProperty(0, new BStatusNumeric(), null);

  /**
   * Get the {@code setpoint} property.
   * Value that, when its status and value is valid, is the basis for defining the range considered
   * normal.
   * @see #setpoint
   */
  public BStatusNumeric getSetpoint() { return (BStatusNumeric)get(setpoint); }

  /**
   * Set the {@code setpoint} property.
   * Value that, when its status and value is valid, is the basis for defining the range considered
   * normal.
   * @see #setpoint
   */
  public void setSetpoint(BStatusNumeric v) { set(setpoint, v, null); }

  //endregion Property "setpoint"

  //region Property "lastValidSetpoint"

  /**
   * Slot for the {@code lastValidSetpoint} property.
   * The setpoint value when its status and value were last valid.
   * @see #getLastValidSetpoint
   * @see #setLastValidSetpoint
   */
  public static final Property lastValidSetpoint = newProperty(Flags.READONLY, 0.0, null);

  /**
   * Get the {@code lastValidSetpoint} property.
   * The setpoint value when its status and value were last valid.
   * @see #lastValidSetpoint
   */
  public double getLastValidSetpoint() { return getDouble(lastValidSetpoint); }

  /**
   * Set the {@code lastValidSetpoint} property.
   * The setpoint value when its status and value were last valid.
   * @see #lastValidSetpoint
   */
  public void setLastValidSetpoint(double v) { setDouble(lastValidSetpoint, v, null); }

  //endregion Property "lastValidSetpoint"

  //region Property "lowDiffLimit"

  /**
   * Slot for the {@code lowDiffLimit} property.
   * Value is subtracted from the last valid setpoint value to determine the low limit of the range
   * considered normal.
   * @see #getLowDiffLimit
   * @see #setLowDiffLimit
   */
  public static final Property lowDiffLimit = newProperty(0, 0.0, BFacets.make(BFacets.MIN, BDouble.make(0.0)));

  /**
   * Get the {@code lowDiffLimit} property.
   * Value is subtracted from the last valid setpoint value to determine the low limit of the range
   * considered normal.
   * @see #lowDiffLimit
   */
  public double getLowDiffLimit() { return getDouble(lowDiffLimit); }

  /**
   * Set the {@code lowDiffLimit} property.
   * Value is subtracted from the last valid setpoint value to determine the low limit of the range
   * considered normal.
   * @see #lowDiffLimit
   */
  public void setLowDiffLimit(double v) { setDouble(lowDiffLimit, v, null); }

  //endregion Property "lowDiffLimit"

  //region Property "highDiffLimit"

  /**
   * Slot for the {@code highDiffLimit} property.
   * Value is added to the last valid setpoint value to determine the high limit of the range considered
   * normal.
   * @see #getHighDiffLimit
   * @see #setHighDiffLimit
   */
  public static final Property highDiffLimit = newProperty(0, 0.0, BFacets.make(BFacets.MIN, BDouble.make(0.0)));

  /**
   * Get the {@code highDiffLimit} property.
   * Value is added to the last valid setpoint value to determine the high limit of the range considered
   * normal.
   * @see #highDiffLimit
   */
  public double getHighDiffLimit() { return getDouble(highDiffLimit); }

  /**
   * Set the {@code highDiffLimit} property.
   * Value is added to the last valid setpoint value to determine the high limit of the range considered
   * normal.
   * @see #highDiffLimit
   */
  public void setHighDiffLimit(double v) { setDouble(highDiffLimit, v, null); }

  //endregion Property "highDiffLimit"

  //region Property "deadband"

  /**
   * Slot for the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal. This value is subtracted
   * from the high limit and added to low limit.
   * @see #getDeadband
   * @see #setDeadband
   */
  public static final Property deadband = newProperty(0, 0.0, BFacets.make(BFacets.MIN, BDouble.make(0.0)));

  /**
   * Get the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal. This value is subtracted
   * from the high limit and added to low limit.
   * @see #deadband
   */
  public double getDeadband() { return getDouble(deadband); }

  /**
   * Set the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal. This value is subtracted
   * from the high limit and added to low limit.
   * @see #deadband
   */
  public void setDeadband(double v) { setDouble(deadband, v, null); }

  //endregion Property "deadband"

  //region Property "lowLimitText"

  /**
   * Slot for the {@code lowLimitText} property.
   * Text descriptor included in a to-low-limit alarm for this object. Uses BFormat.
   * @see #getLowLimitText
   * @see #setLowLimitText
   */
  public static final Property lowLimitText = newProperty(0, BFormat.DEFAULT, BFacets.make(BFacets.MULTI_LINE, true));

  /**
   * Get the {@code lowLimitText} property.
   * Text descriptor included in a to-low-limit alarm for this object. Uses BFormat.
   * @see #lowLimitText
   */
  public BFormat getLowLimitText() { return (BFormat)get(lowLimitText); }

  /**
   * Set the {@code lowLimitText} property.
   * Text descriptor included in a to-low-limit alarm for this object. Uses BFormat.
   * @see #lowLimitText
   */
  public void setLowLimitText(BFormat v) { set(lowLimitText, v, null); }

  //endregion Property "lowLimitText"

  //region Property "highLimitText"

  /**
   * Slot for the {@code highLimitText} property.
   * Text descriptor included in a to-high-limit alarm for this object. Uses BFormat.
   * @see #getHighLimitText
   * @see #setHighLimitText
   */
  public static final Property highLimitText = newProperty(0, BFormat.DEFAULT, BFacets.make(BFacets.MULTI_LINE, true));

  /**
   * Get the {@code highLimitText} property.
   * Text descriptor included in a to-high-limit alarm for this object. Uses BFormat.
   * @see #highLimitText
   */
  public BFormat getHighLimitText() { return (BFormat)get(highLimitText); }

  /**
   * Set the {@code highLimitText} property.
   * Text descriptor included in a to-high-limit alarm for this object. Uses BFormat.
   * @see #highLimitText
   */
  public void setHighLimitText(BFormat v) { set(highLimitText, v, null); }

  //endregion Property "highLimitText"

  //region Property "limitEnable"

  /**
   * Slot for the {@code limitEnable} property.
   * Flags that enable the low-limit and high-limit alarms, as needed.
   * @see #getLimitEnable
   * @see #setLimitEnable
   */
  public static final Property limitEnable = newProperty(0, new BLimitEnable(), null);

  /**
   * Get the {@code limitEnable} property.
   * Flags that enable the low-limit and high-limit alarms, as needed.
   * @see #limitEnable
   */
  public BLimitEnable getLimitEnable() { return (BLimitEnable)get(limitEnable); }

  /**
   * Set the {@code limitEnable} property.
   * Flags that enable the low-limit and high-limit alarms, as needed.
   * @see #limitEnable
   */
  public void setLimitEnable(BLimitEnable v) { set(limitEnable, v, null); }

  //endregion Property "limitEnable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFloatingLimitAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Grandparent must implement the NumericPoint interface
   */
  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return grandparent instanceof BNumericPoint;
  }

  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.equals(setpoint) || slot.equals(lastValidSetpoint))
    {
      return getPointFacets();
    }

    if (slot.equals(highDiffLimit) || slot.equals(lowDiffLimit) || slot.equals(deadband))
    {
      BFacets facets = getPointFacets();

      // Replace the units facet with the equivalent differential unit
      BUnit unit = (BUnit) facets.getFacet(BFacets.UNITS);
      if (unit != null)
      {
        facets = BFacets.make(facets, BFacets.UNITS, unit.getDifferentialUnit());
      }

      return BFacets.make(facets, super.getSlotFacets(slot));
    }

    return super.getSlotFacets(slot);
  }

  @Override
  public void started()
  {
    updateLastValidSetpoint();

    BAlarmState alarmState = getParentAlarmState();
    if (alarmState.equals(BAlarmState.highLimit))
    {
      transition(HIGH_ALARM_STATE, 0, 0);
    }
    else if (alarmState.equals(BAlarmState.lowLimit))
    {
      transition(LOW_ALARM_STATE, 0, 0);
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

    if (p.equals(setpoint))
    {
      updateLastValidSetpoint();
    }
    else
    {
      executePoint();
    }
  }

  @Override
  public BAlarmState checkAlarms(BStatusValue out, long toAlarmTimeDelay, long toNormalTimeDelay)
  {
    algorithmState.synchronizeWithAlarmState(this, getParentAlarmState());
    return algorithmState.evaluate(this, (BStatusNumeric) out, toAlarmTimeDelay, toNormalTimeDelay);
  }

  /**
   * Write the key-value pairs defining alarm data for the alarm algorithm and state to the given
   * Facets.
   *
   * @param out The relevant control point status value
   * @param map The map.
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void writeAlarmData(BStatusValue out, Map map)
  {
    BFacets pointFacets = getPointFacets();
    BFacets diffFacets = getSlotFacets(deadband);

    map.put(BAlarmRecord.STATUS, BString.make(out.getStatus().toString(null)));
    map.put(BAlarmRecord.DEADBAND, BString.make(BDouble.toString(getDeadband(), diffFacets)));
    map.put(BAlarmRecord.SETPT_VALUE, BString.make(BDouble.toString(getLastValidSetpoint(), pointFacets)));
    map.put(BAlarmRecord.SETPT_NUMERIC, BDouble.make(getLastValidSetpoint()));

    if (algorithmState == HIGH_ALARM_STATE)
    {
      if (!getHighLimitText().equals(BFormat.DEFAULT))
      {
        map.put(BAlarmRecord.MSG_TEXT, BString.make(getHighLimitText().getFormat()));
      }
      map.put(BAlarmRecord.HIGH_LIMIT, BString.make(BDouble.toString(getHighLimit(), pointFacets)));
      map.put(BAlarmRecord.HIGH_DIFF_LIMIT, BString.make(BDouble.toString(getHighDiffLimit(), diffFacets)));
      if (isLowLimitEnabled())
      {
        map.put(BAlarmRecord.LOW_LIMIT, BString.make(BDouble.toString(getLowLimit(), pointFacets)));
        map.put(BAlarmRecord.LOW_DIFF_LIMIT, BString.make(BDouble.toString(getLowDiffLimit(), diffFacets)));
      }
    }
    else if (algorithmState == LOW_ALARM_STATE)
    {
      if (!getLowLimitText().equals(BFormat.DEFAULT))
      {
        map.put(BAlarmRecord.MSG_TEXT, BString.make(getLowLimitText().getFormat()));
      }
      if (isHighLimitEnabled())
      {
        map.put(BAlarmRecord.HIGH_LIMIT, BString.make(BDouble.toString(getHighLimit(), pointFacets)));
        map.put(BAlarmRecord.HIGH_DIFF_LIMIT, BString.make(BDouble.toString(getHighDiffLimit(), diffFacets)));
      }
      map.put(BAlarmRecord.LOW_LIMIT, BString.make(BDouble.toString(getLowLimit(), pointFacets)));
      map.put(BAlarmRecord.LOW_DIFF_LIMIT, BString.make(BDouble.toString(getLowDiffLimit(), diffFacets)));
    }
  }

  //region Utility

  private void transition(FloatingLimitState newState, long toAlarmTimeDelay, long toNormalTimeDelay)
  {
    // Always cancel timer upon entering a new state. The new state will handle starting another
    // timer if needed.
    cancelTimer();
    algorithmState = newState;
    newState.enterState(this, toAlarmTimeDelay, toNormalTimeDelay);
  }

  private BAlarmState getParentAlarmState()
  {
    return ((BAlarmSourceExt) getParent()).getAlarmState();
  }

  private double getLowLimit()
  {
    return getLastValidSetpoint() - getLowDiffLimit();
  }

  private double getHighLimit()
  {
    return getLastValidSetpoint() + getHighDiffLimit();
  }

  private boolean isLowLimitEnabled()
  {
    return getLimitEnable().getLowLimitEnable();
  }

  private boolean isHighLimitEnabled()
  {
    return getLimitEnable().getHighLimitEnable();
  }

  private void updateLastValidSetpoint()
  {
    BStatusNumeric setpoint = getSetpoint();
    if (!setpoint.getStatus().isValid())
    {
      return;
    }

    double value = setpoint.getNumeric();
    if (!Double.isNaN(value) && !Double.isInfinite(value))
    {
      setLastValidSetpoint(value);
    }
  }

  //endregion

  //region Fields

  private FloatingLimitState algorithmState = NORMAL_STATE;

  //endregion

  //region States

  private static abstract class FloatingLimitState
  {
    public abstract void synchronizeWithAlarmState(BFloatingLimitAlgorithm algorithm, BAlarmState alarmState);

    public void enterState(BFloatingLimitAlgorithm algorithm, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
    }

    public abstract BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay);
  }

  private static abstract class NormalState extends FloatingLimitState
  {
    @Override
    public void synchronizeWithAlarmState(BFloatingLimitAlgorithm algorithm, BAlarmState alarmState)
    {
      if (alarmState.equals(BAlarmState.highLimit))
      {
        algorithm.transition(HIGH_ALARM_STATE, 0, 0);
      }
      else if (alarmState.equals(BAlarmState.lowLimit))
      {
        algorithm.transition(LOW_ALARM_STATE, 0, 0);
      }
    }

    protected static BAlarmState handleLowLimit(BFloatingLimitAlgorithm algorithm, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (toAlarmTimeDelay == 0)
      {
        algorithm.transition(LOW_ALARM_STATE, 0, toNormalTimeDelay);
        return BAlarmState.lowLimit;
      }
      else
      {
        algorithm.transition(DELAY_TO_LOW_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return null;
      }
    }

    protected static BAlarmState handleHighLimit(BFloatingLimitAlgorithm algorithm, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (toAlarmTimeDelay == 0)
      {
        algorithm.transition(HIGH_ALARM_STATE, 0, toNormalTimeDelay);
        return BAlarmState.highLimit;
      }
      else
      {
        algorithm.transition(DELAY_TO_HIGH_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return null;
      }
    }
  }

  private static final FloatingLimitState NORMAL_STATE = new NormalState()
  {
    @Override
    public BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (out.getStatus().isNull())
      {
        return null;
      }

      double outValue = out.getValue();
      if (outValue < algorithm.getLowLimit())
      {
        if (algorithm.isLowLimitEnabled())
        {
          return handleLowLimit(algorithm, toAlarmTimeDelay, toNormalTimeDelay);
        }
      }
      else if (outValue > algorithm.getHighLimit())
      {
        if (algorithm.isHighLimitEnabled())
        {
          return handleHighLimit(algorithm, toAlarmTimeDelay, toNormalTimeDelay);
        }
      }

      return null;
    }
  };

  private static final FloatingLimitState DELAY_TO_LOW_ALARM_STATE = new NormalState()
  {
    @Override
    public void enterState(BFloatingLimitAlgorithm algorithm, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      algorithm.startTimer(toAlarmTimeDelay);
    }

    @Override
    public BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (out.getStatus().isNull())
      {
        algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return null;
      }

      double outValue = out.getValue();
      if (outValue < algorithm.getLowLimit())
      {
        if (!algorithm.isLowLimitEnabled())
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        }
        else if (toAlarmTimeDelay == 0 || algorithm.isTimerExpired())
        {
          algorithm.transition(LOW_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
          return BAlarmState.lowLimit;
        }
      }
      else
      {
        if (algorithm.isHighLimitEnabled() && outValue > algorithm.getHighLimit())
        {
          return handleHighLimit(algorithm, toAlarmTimeDelay, toNormalTimeDelay);
        }
        else
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        }
      }

      return null;
    }
  };

  private static final FloatingLimitState DELAY_TO_HIGH_ALARM_STATE = new NormalState()
  {
    @Override
    public void enterState(BFloatingLimitAlgorithm algorithm, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      algorithm.startTimer(toAlarmTimeDelay);
    }

    @Override
    public BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (out.getStatus().isNull())
      {
        algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return null;
      }

      double outValue = out.getValue();
      if (outValue > algorithm.getHighLimit())
      {
        if (!algorithm.isHighLimitEnabled())
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        }
        else if (toAlarmTimeDelay == 0 || algorithm.isTimerExpired())
        {
          algorithm.transition(HIGH_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
          return BAlarmState.highLimit;
        }
      }
      else
      {
        if (algorithm.isLowLimitEnabled() && outValue < algorithm.getLowLimit())
        {
          return handleLowLimit(algorithm, toAlarmTimeDelay, toNormalTimeDelay);
        }
        else
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        }
      }

      return null;
    }
  };

  private static abstract class LowAlarmState extends FloatingLimitState
  {
    @Override
    public void synchronizeWithAlarmState(BFloatingLimitAlgorithm algorithm, BAlarmState alarmState)
    {
      if (alarmState.equals(BAlarmState.highLimit))
      {
        algorithm.transition(HIGH_ALARM_STATE, 0, 0);
      }
      else if (!alarmState.equals(BAlarmState.lowLimit))
      {
        algorithm.transition(NORMAL_STATE, 0, 0);
      }
    }
  }

  private static final FloatingLimitState LOW_ALARM_STATE = new LowAlarmState()
  {
    @Override
    public BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (!algorithm.isLowLimitEnabled())
      {
        algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return BAlarmState.normal;
      }

      if (out.getStatus().isNull() || out.getValue() >= algorithm.getLowLimit() + algorithm.getDeadband())
      {
        if (toNormalTimeDelay == 0)
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, 0);
          return BAlarmState.normal;
        }
        else
        {
          algorithm.transition(DELAY_FROM_LOW_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        }
      }

      return null;
    }
  };

  private static final FloatingLimitState DELAY_FROM_LOW_ALARM_STATE = new LowAlarmState()
  {
    @Override
    public void enterState(BFloatingLimitAlgorithm algorithm, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      algorithm.startTimer(toNormalTimeDelay);
    }

    @Override
    public BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (!algorithm.isLowLimitEnabled())
      {
        algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return BAlarmState.normal;
      }

      if (out.getStatus().isNull() || out.getValue() >= algorithm.getLowLimit() + algorithm.getDeadband())
      {
        if (toNormalTimeDelay == 0 || algorithm.isTimerExpired())
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
          return BAlarmState.normal;
        }
      }
      else
      {
        algorithm.transition(LOW_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
      }

      return null;
    }
  };

  private static abstract class HighAlarmState extends FloatingLimitState
  {
    @Override
    public void synchronizeWithAlarmState(BFloatingLimitAlgorithm algorithm, BAlarmState alarmState)
    {
      if (alarmState.equals(BAlarmState.lowLimit))
      {
        algorithm.transition(LOW_ALARM_STATE, 0, 0);
      }
      else if (!alarmState.equals(BAlarmState.highLimit))
      {
        algorithm.transition(NORMAL_STATE, 0, 0);
      }
    }
  }

  private static final FloatingLimitState HIGH_ALARM_STATE = new HighAlarmState()
  {
    @Override
    public BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (!algorithm.isHighLimitEnabled())
      {
        algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return BAlarmState.normal;
      }

      if (out.getStatus().isNull() || out.getValue() <= algorithm.getHighLimit() - algorithm.getDeadband())
      {
        if (toNormalTimeDelay == 0)
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, 0);
          return BAlarmState.normal;
        }
        else
        {
          algorithm.transition(DELAY_FROM_HIGH_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        }
      }

      return null;
    }
  };

  private static final FloatingLimitState DELAY_FROM_HIGH_ALARM_STATE = new HighAlarmState()
  {
    @Override
    public void enterState(BFloatingLimitAlgorithm algorithm, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      algorithm.startTimer(toNormalTimeDelay);
    }

    @Override
    public BAlarmState evaluate(BFloatingLimitAlgorithm algorithm, BStatusNumeric out, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (!algorithm.isHighLimitEnabled())
      {
        algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
        return BAlarmState.normal;
      }

      if (out.getStatus().isNull() || out.getValue() <= algorithm.getHighLimit() - algorithm.getDeadband())
      {
        if (toNormalTimeDelay == 0 || algorithm.isTimerExpired())
        {
          algorithm.transition(NORMAL_STATE, toAlarmTimeDelay, toNormalTimeDelay);
          return BAlarmState.normal;
        }
      }
      else
      {
        algorithm.transition(HIGH_ALARM_STATE, toAlarmTimeDelay, toNormalTimeDelay);
      }

      return null;
    }
  };

  //endregion
}
