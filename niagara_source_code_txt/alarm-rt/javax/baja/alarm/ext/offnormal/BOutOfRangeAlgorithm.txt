/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

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
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.util.BFormat;

/**
 * BOutOfRangeAlgorithm implements a standard out-of-range
 * alarming algorithm
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 54$ $Date: 6/15/10 2:58:33 PM EDT$
 * @since     Baja 1.0
 */

// FIXX - test w/ varying alarm limits or is a new object needed
//  to handle setpoint +/- delta alarming - could floating limit
//  object be used - originally tailored for loop.  Need different
//  high / low alarm limit deltas???

@NiagaraType
/*
 Value above which the object is evaluated in high-limit alarm.
 */
@NiagaraProperty(
  name = "highLimit",
  type = "double",
  defaultValue = "0"
)
/*
 Value below which the object is considered in low-limit alarm.
 */
@NiagaraProperty(
  name = "lowLimit",
  type = "double",
  defaultValue = "0"
)
/*
 Differential value applied to high and low limits before return-to-normal.
 Deadband is subtracted from highLimit and added to lowLimit.
 */
@NiagaraProperty(
  name = "deadband",
  type = "double",
  defaultValue = "0",
  facets = @Facet("BFacets.make(BFacets.MIN, BFloat.make(0))")
)
@NiagaraProperty(
  name = "highLimitText",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE)")
)
@NiagaraProperty(
  name = "lowLimitText",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE)")
)
/*
 Flags that enable low-limit and high-limit alarms, as needed.
 */
@NiagaraProperty(
  name = "limitEnable",
  type = "BLimitEnable",
  defaultValue = "new BLimitEnable()"
)
public class BOutOfRangeAlgorithm
  extends BOffnormalAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BOutOfRangeAlgorithm(957851947)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "highLimit"

  /**
   * Slot for the {@code highLimit} property.
   * Value above which the object is evaluated in high-limit alarm.
   * @see #getHighLimit
   * @see #setHighLimit
   */
  public static final Property highLimit = newProperty(0, 0, null);

  /**
   * Get the {@code highLimit} property.
   * Value above which the object is evaluated in high-limit alarm.
   * @see #highLimit
   */
  public double getHighLimit() { return getDouble(highLimit); }

  /**
   * Set the {@code highLimit} property.
   * Value above which the object is evaluated in high-limit alarm.
   * @see #highLimit
   */
  public void setHighLimit(double v) { setDouble(highLimit, v, null); }

  //endregion Property "highLimit"

  //region Property "lowLimit"

  /**
   * Slot for the {@code lowLimit} property.
   * Value below which the object is considered in low-limit alarm.
   * @see #getLowLimit
   * @see #setLowLimit
   */
  public static final Property lowLimit = newProperty(0, 0, null);

  /**
   * Get the {@code lowLimit} property.
   * Value below which the object is considered in low-limit alarm.
   * @see #lowLimit
   */
  public double getLowLimit() { return getDouble(lowLimit); }

  /**
   * Set the {@code lowLimit} property.
   * Value below which the object is considered in low-limit alarm.
   * @see #lowLimit
   */
  public void setLowLimit(double v) { setDouble(lowLimit, v, null); }

  //endregion Property "lowLimit"

  //region Property "deadband"

  /**
   * Slot for the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal.
   * Deadband is subtracted from highLimit and added to lowLimit.
   * @see #getDeadband
   * @see #setDeadband
   */
  public static final Property deadband = newProperty(0, 0, BFacets.make(BFacets.MIN, BFloat.make(0)));

  /**
   * Get the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal.
   * Deadband is subtracted from highLimit and added to lowLimit.
   * @see #deadband
   */
  public double getDeadband() { return getDouble(deadband); }

  /**
   * Set the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal.
   * Deadband is subtracted from highLimit and added to lowLimit.
   * @see #deadband
   */
  public void setDeadband(double v) { setDouble(deadband, v, null); }

  //endregion Property "deadband"

  //region Property "highLimitText"

  /**
   * Slot for the {@code highLimitText} property.
   * @see #getHighLimitText
   * @see #setHighLimitText
   */
  public static final Property highLimitText = newProperty(0, BFormat.DEFAULT, BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code highLimitText} property.
   * @see #highLimitText
   */
  public BFormat getHighLimitText() { return (BFormat)get(highLimitText); }

  /**
   * Set the {@code highLimitText} property.
   * @see #highLimitText
   */
  public void setHighLimitText(BFormat v) { set(highLimitText, v, null); }

  //endregion Property "highLimitText"

  //region Property "lowLimitText"

  /**
   * Slot for the {@code lowLimitText} property.
   * @see #getLowLimitText
   * @see #setLowLimitText
   */
  public static final Property lowLimitText = newProperty(0, BFormat.DEFAULT, BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code lowLimitText} property.
   * @see #lowLimitText
   */
  public BFormat getLowLimitText() { return (BFormat)get(lowLimitText); }

  /**
   * Set the {@code lowLimitText} property.
   * @see #lowLimitText
   */
  public void setLowLimitText(BFormat v) { set(lowLimitText, v, null); }

  //endregion Property "lowLimitText"

  //region Property "limitEnable"

  /**
   * Slot for the {@code limitEnable} property.
   * Flags that enable low-limit and high-limit alarms, as needed.
   * @see #getLimitEnable
   * @see #setLimitEnable
   */
  public static final Property limitEnable = newProperty(0, new BLimitEnable(), null);

  /**
   * Get the {@code limitEnable} property.
   * Flags that enable low-limit and high-limit alarms, as needed.
   * @see #limitEnable
   */
  public BLimitEnable getLimitEnable() { return (BLimitEnable)get(limitEnable); }

  /**
   * Set the {@code limitEnable} property.
   * Flags that enable low-limit and high-limit alarms, as needed.
   * @see #limitEnable
   */
  public void setLimitEnable(BLimitEnable v) { set(limitEnable, v, null); }

  //endregion Property "limitEnable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutOfRangeAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Convienence
////////////////////////////////////////////////////////////////

  private boolean isHighLimitEnabled() { return getLimitEnable().isHighLimitEnabled(); }
  private boolean isLowLimitEnabled()  { return getLimitEnable().isLowLimitEnabled(); }

  @Override
  public void started()
  {
    BAlarmState currentState = ((BAlarmSourceExt)getParent()).getAlarmState();
    if (currentState == BAlarmState.highLimit)
      current = new HighAlarmState();
    if (currentState == BAlarmState.lowLimit)
      current = new LowAlarmState();
  }

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BOutOfRangeAlgorithm's grandparent must implement
   * the NumericPoint interface
   */
  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return (grandparent instanceof BNumericPoint);
  }

////////////////////////////////////////////////////////////////
//  property facet checking
////////////////////////////////////////////////////////////////

  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == highLimit ||
      slot == lowLimit)
      return getPointFacets();

    if (slot == deadband)
    {
      BFacets facets = getPointFacets();
      BUnit unit = (BUnit)facets.getFacet(BFacets.UNITS);
      if (unit != null)
        facets = BFacets.make(facets, BFacets.UNITS, unit.getDifferentialUnit());

      return BFacets.make(facets, super.getSlotFacets(deadband));
    }

    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
//  Algorithm implementation
////////////////////////////////////////////////////////////////


  @Override
  public BAlarmState checkAlarms(BStatusValue o, long toAlarmTimeDelay, long toNormalTimeDelay)
  {
    BStatusNumeric out = (BStatusNumeric)o;
    if (out.getStatus().isNull())
    {
      if (current instanceof NormalState)
        return null;//no change in state
      //transition correctly
      if (toNormalTimeDelay > 0 && current instanceof HighAlarmState)
        current = new ValidateReturnFromHighState(toNormalTimeDelay);
      else if (toNormalTimeDelay > 0 && current instanceof LowAlarmState)
        current = new ValidateReturnFromLowState(toNormalTimeDelay);
      else
      {
        current = new NormalState();
        return BAlarmState.normal;
      }
      return null;
    }

    return evaluate(out.getValue(), toAlarmTimeDelay, toNormalTimeDelay);
  }

  /**********************************************
   * Evaluates object state based on given
   * parameters
   **********************************************/
  private BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
  {
    BAlarmSourceExt parent = (BAlarmSourceExt) getParent();
    if (parent == null)
    {
      throw new NotRunningException("OutOfRangeAlgorithm has no parent AlarmSourceExt");
    }

    BAlarmState currentState = parent.getAlarmState();

    if (!(current instanceof ValidationState))
    {
      if (currentState == BAlarmState.highLimit)
        current = new HighAlarmState();
      else if (currentState == BAlarmState.lowLimit)
        current = new LowAlarmState();
      else
        current = new NormalState();
    }

    return current.evaluate(presentValue, toAlarmTimeDelay, toNormalTimeDelay);
  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Facets.
   * <p>
   *  The alarm data for an Out of Range alarm is given by
   *  BACnet table 13-3, Standard Object Property Values
   *  returned in notifications.
   *
   * @param out The relevant control point status value
   * @param map The map.
   */
  @Override
  @SuppressWarnings({"rawtypes","unchecked"})
  public void writeAlarmData(BStatusValue out, java.util.Map map)
  {
    BFacets facets = getPointFacets();
    map.put(BAlarmRecord.STATUS, BString.make(out.getStatus().toString(null)));
    map.put(BAlarmRecord.DEADBAND, BString.make(BDouble.toString(getDeadband(), facets)));
    if (current instanceof HighAlarmState)
    {
      if (!getHighLimitText().equals(BFormat.DEFAULT))
        map.put(BAlarmRecord.MSG_TEXT, BString.make(getHighLimitText().getFormat()));
      map.put(BAlarmRecord.HIGH_LIMIT, BString.make(BDouble.toString(getHighLimit(), facets)));
      if(isLowLimitEnabled())
        map.put(BAlarmRecord.LOW_LIMIT, BString.make(BDouble.toString(getLowLimit(), facets)));
    }
    else if (current instanceof LowAlarmState)
    {
      if (!getLowLimitText().equals(BFormat.DEFAULT))
        map.put(BAlarmRecord.MSG_TEXT, BString.make(getLowLimitText().getFormat()));
      if(isHighLimitEnabled())
        map.put(BAlarmRecord.HIGH_LIMIT, BString.make(BDouble.toString(getHighLimit(), facets)));
      map.put(BAlarmRecord.LOW_LIMIT, BString.make(BDouble.toString(getLowLimit(), facets)));
    }

  }

////////////////////////////////////////////////////////////////
//  property changed processing
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning()) return;

    //cause the parent controlPoint to execute.
    executePoint();
  }

////////////////////////////////////////////////////////////////
//  Internal Utility Methods
////////////////////////////////////////////////////////////////

  /**********************************************
   * Transitions state of object from one state
   * to another.
   **********************************************/
  void transition(OutOfRangeState state)
  {
    current = state;
  }

  //////////////////////////////////////////////////////////////
  // This is the base class for all states
  //////////////////////////////////////////////////////////////
  private abstract class OutOfRangeState
  {
    ////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////
    public OutOfRangeState()
    {
      //  Always cancel timer upon entering a new state.
      //  The new state will handle starting another timer
      //  if needed.
      cancelTimer();
    }

    ////////////////////////////////////////////////////////////
    // Utility methods
    ////////////////////////////////////////////////////////////

    /**********************************************
     * Used to get a trace tag.
     **********************************************/
    public abstract String tag();

    /**********************************************
     * Default parse methods call parseDefault.
     * Subclasses override these methods for
     * state based parsing.
     **********************************************/
    public abstract BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay);

  }

  private abstract class ValidationState
    extends OutOfRangeState
  {
  }

  //////////////////////////////////////////////////////////////
  // Normal state of the object
  //////////////////////////////////////////////////////////////
  private class NormalState
    extends OutOfRangeState
  {
    public NormalState()
    {
      super();
    }

    @Override
    public String tag() {return "Normal";}

    @Override
    public BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      //  If 'presentValue' falls below the low limit, enter
      //  the low alarm validation state
      if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        if (toAlarmTimeDelay == 0)
        {
          transition(new LowAlarmState());
          return BAlarmState.lowLimit;
        }
        else
        {
          transition(new ValidateLowAlarmState(toAlarmTimeDelay));
        }
      }

      //  If 'presentValue' is in the normal range, do nothing
      else if ((presentValue >= getLowLimit()) && (presentValue <= getHighLimit()))
      {
        // Do nothing
      }

      //  If 'presentValue' exceeds the high limit, enter
      //  the high alarm validation state
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        if (toAlarmTimeDelay == 0)
        {
          transition(new HighAlarmState());
          return BAlarmState.highLimit;

        }
        else
        {
          transition(new ValidateHighAlarmState(toAlarmTimeDelay));
        }
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible high alarm.  'Present
  // value' must exceed the 'high limit' for 'time delay'
  // seconds before an alarm is generated
  //////////////////////////////////////////////////////////////
  private class ValidateHighAlarmState
    extends ValidationState
  {
    public ValidateHighAlarmState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateHighAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      //  If 'presentValue' falls below the low limit while
      //  we are validating a high alarm, enter the low
      //  alarm validation state.
      if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        if (toAlarmTimeDelay == 0)
        {
          transition(new LowAlarmState());
          return BAlarmState.lowLimit;
        }
        else
          transition(new ValidateLowAlarmState(toAlarmTimeDelay));
      }
      //  If 'timeDelay' seconds have elapsed since we entered the
      //  alarm state, this is an official alarm.  Otherwise, remain
      //  calm.
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        if (isTimerExpired())
        {
          transition(new HighAlarmState());
          return BAlarmState.highLimit;
        }
      }
      //  If 'presentValue' returns to the normal range before
      //  'timeDelay' seconds have elapsed, move to the normal
      //  state.  This will cancel the active timer.
      else if (presentValue <= getHighLimit())
      {
        transition(new NormalState());
        //return BAlarmState.normal;
      }

      return null;
    }
  }


  //////////////////////////////////////////////////////////////
  // State when value is in high alarm.  'Present Value' must
  // fall below  the 'high limit' minus 'deadband' for 'time delay'
  // seconds before a return-to-normal is generated
  //////////////////////////////////////////////////////////////
  private class HighAlarmState
    extends OutOfRangeState
  {
    public HighAlarmState()
    {
    }

    @Override
    public String tag() {return "HighAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      //  If present value is still greater than alarm limit
      //  minus deadband, we are still in alarm.
      if (presentValue > (getHighLimit() - getDeadband()))
      {
        //  Do nothing
        if (!isHighLimitEnabled())
        {
          transition(new NormalState());
          return BAlarmState.normal;
        }
      }
      //  If we are in High Alarm and the 'presentValue' falls below
      //  the low limit, enter the validate low alarm state
      else if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        if (toAlarmTimeDelay == 0)
        {
          //transition(new LowAlarmState());
          //return BAlarmState.lowLimit;
          transition(new NormalState());
          return BAlarmState.normal;
        }
        else
          //transition(new ValidateLowAlarmState(timeDelay));
          transition(new ValidateReturnFromHighState(toAlarmTimeDelay));
      }
      //  If 'presentValue' falls below the high limit
      //  minus the deadband, enter the return-from-high
      //  validation state
      else if (presentValue <= (getHighLimit() - getDeadband()))
      {
        if (toNormalTimeDelay == 0)
        {
          transition(new NormalState());
          return BAlarmState.normal;
        }
        else
          transition(new ValidateReturnFromHighState(toNormalTimeDelay));
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible return to normal from
  // a high alarm.  'presentValue' must be stay below 'high
  // limit' minus 'deadband' for 'timeDelay' seconds before a
  // return-to-normal is generated.
  //////////////////////////////////////////////////////////////
  private class ValidateReturnFromHighState
    extends ValidationState
  {
    public ValidateReturnFromHighState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateReturnFromHighState";}

    @Override
    public BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      if (toNormalTimeDelay > 0 && !isTimerExpired()) return null;

      //  If 'presentValue' is greater than or equal to alarm
      //  limit plus deadband, return to the alarm state
      if ((presentValue > (getHighLimit() - getDeadband())) && isHighLimitEnabled())
      {
        transition(new HighAlarmState());
      }
      //  If we are validating a return from high alarm and
      //  the 'presentValue'  fall below the low limit, enter
      //  the validate low alarm state
      /*if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        if (timeDelay == 0)
        {
          transition(new LowAlarmState());
          return BAlarmState.lowLimit;
        }
        else
          transition(new ValidateLowAlarmState(timeDelay));
      }*/
      //  If present value has remained below the 'high limit'
      //  minus 'deadband' for 'timeDelay' seconds, return
      //  to the normal state.  Otherwise, do nothing
      else if ( presentValue < (getHighLimit() - getDeadband()))
      {
        if (isTimerExpired())
        {
          transition(new NormalState());
          return BAlarmState.normal;
        }
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible low alarm.  'Present
  // value' must fall below  the 'low limit' for 'time delay'
  // seconds before an alarm is generated
  //////////////////////////////////////////////////////////////
  private class ValidateLowAlarmState
    extends ValidationState
  {
    public ValidateLowAlarmState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateLowAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      //  If 'timeDelay' seconds have elapsed since we
      //  entered the alarm state, this is an offical
      //  alarm.  Otherwise, remain in the validation state.
      if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        if (isTimerExpired())
        {
          transition(new LowAlarmState());
          return BAlarmState.lowLimit;
        }
      }
      //  If 'presentValue' falls exceeds the high limit
      //  while we are validating a low alarm, enter the
      //  high alarm validation state.
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        if (toAlarmTimeDelay == 0)
        {
          transition(new HighAlarmState());
          return BAlarmState.highLimit;
        }
        else
          transition(new ValidateHighAlarmState(toAlarmTimeDelay));
      }
      //  If 'presentValue' returns to the normal range before
      //  'timeDelay' seconds have elapsed, move to the normal
      //  state
      else if (presentValue >= getLowLimit())
      {
        transition(new NormalState());
        //return BAlarmState.normal;
      }

      return null;

    }
  }

  //////////////////////////////////////////////////////////////
  // State when value is in low alarm.  'Present Value' must
  // fall below  the 'high limit - deadband' for 'time delay'
  // seconds before a return-to-normal is generated
  //////////////////////////////////////////////////////////////
  private class LowAlarmState
    extends OutOfRangeState
  {
    public LowAlarmState()
    {
    }

    @Override
    public String tag() {return "LowAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      //  If present value is still less than or equal to
      //  alarm limit plus deadband, we are still in alarm
      if (presentValue < (getLowLimit() + getDeadband()))
      {
        //  Do nothing
        if (!isLowLimitEnabled())
        {
          transition(new NormalState());
          return BAlarmState.normal;
        }
      }
      //  If we are in Low Alarm and the 'presentValue'
      //  exceeds the high limit, enter the validate high alarm state
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        if (toNormalTimeDelay == 0)
        {
          //transition(new HighAlarmState());
          //return BAlarmState.highLimit;
          transition(new NormalState());
          return BAlarmState.normal;
        }
        else
          //transition(new ValidateHighAlarmState(timeDelay));
          transition(new ValidateReturnFromLowState(toNormalTimeDelay));
      }
      //  If 'presentValue' falls exceeds the low limit
      //  plus the deadband, enter the return-from-low
      //  validation state
      else if (presentValue >= (getLowLimit()+getDeadband()))
      {
        if (toNormalTimeDelay == 0)
        {
          transition(new NormalState());
          return BAlarmState.normal;
        }
        else
          transition(new ValidateReturnFromLowState(toNormalTimeDelay));
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible return to normal from
  // a low alarm.  'Present value' must exceed the 'low limit'
  // plus 'deadband' for 'time delay' seconds before a
  // return-to-normal is generated.
  //////////////////////////////////////////////////////////////
  private class ValidateReturnFromLowState
    extends ValidationState
  {
    public ValidateReturnFromLowState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateReturnFromLowState";}

    @Override
    public BAlarmState evaluate(double presentValue, long toAlarmTimeDelay, long toNormalTimeDelay)
    {
      //  If 'presentValue' is less than or equal to alarm
      //  limit plus deadband, return to the alarm state
      if ((presentValue <= (getLowLimit() + getDeadband())) && isLowLimitEnabled())
      {
        transition(new LowAlarmState());
      }
      //
      //  If we are validating a return from low alarm and
      //  the 'presentValue'  exceeds the high limit, enter the
      //  validate high alarm state
      //
      /*else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        if (timeDelay == 0)
        {
          transition(new HighAlarmState());
          return BAlarmState.highLimit;
        }
        else
          transition(new ValidateHighAlarmState(timeDelay));

      }*/
      //  If present value has remained above the low limit
      //  plus deadband for 'timeDelay' seconds, return
      //  to the normal state.  Otherwise, do nothing
      else if (presentValue > (getLowLimit() + getDeadband()))
      {
        if (isTimerExpired())
        {
          transition(new NormalState());
          return BAlarmState.normal;
        }
      }

      return null;
    }
  }


//////////////////////////////////////////////////////////////
// Attributes
//////////////////////////////////////////////////////////////

  OutOfRangeState current = new NormalState();



}
