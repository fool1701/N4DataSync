/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.fault;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.ext.*;
import javax.baja.control.BNumericPoint;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;
import javax.baja.units.BUnit;
import javax.baja.util.BFormat;

/**
 * BOutOfRangeFaultAlgorithm implements a standard out-of-range
 * alarming algorithm
 *
 * @author    Blake M Puhal
 * @creation  08 Nov 04
 * @version   $Revision: 10$ $Date: 6/15/10 2:58:33 PM EDT$
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
@NiagaraProperty(
  name = "inHighState",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "inLowState",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
public class BOutOfRangeFaultAlgorithm
  extends BFaultAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.fault.BOutOfRangeFaultAlgorithm(3103752195)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "inHighState"

  /**
   * Slot for the {@code inHighState} property.
   * @see #getInHighState
   * @see #setInHighState
   */
  public static final Property inHighState = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code inHighState} property.
   * @see #inHighState
   */
  public boolean getInHighState() { return getBoolean(inHighState); }

  /**
   * Set the {@code inHighState} property.
   * @see #inHighState
   */
  public void setInHighState(boolean v) { setBoolean(inHighState, v, null); }

  //endregion Property "inHighState"

  //region Property "inLowState"

  /**
   * Slot for the {@code inLowState} property.
   * @see #getInLowState
   * @see #setInLowState
   */
  public static final Property inLowState = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code inLowState} property.
   * @see #inLowState
   */
  public boolean getInLowState() { return getBoolean(inLowState); }

  /**
   * Set the {@code inLowState} property.
   * @see #inLowState
   */
  public void setInLowState(boolean v) { setBoolean(inLowState, v, null); }

  //endregion Property "inLowState"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutOfRangeFaultAlgorithm.class);

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
    if (currentState == BAlarmState.fault)
    {
      //Bug 7357: (also in evaluate) check Fault High or Low
      //          states are set in the High Low and Normal State classes
      if (getInHighState())
        current = new HighAlarmState();
      else if (getInLowState())
        current = new LowAlarmState();
    }
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
  public BAlarmState checkFault(BStatusValue o)
  {
    BStatusNumeric out = (BStatusNumeric)o;
    return evaluate(out.getValue());
  }

  /**********************************************
  * Evaluates object state based on given
  * parameters
  **********************************************/
  private BAlarmState evaluate(double presentValue)
  {
    BAlarmSourceExt parent = (BAlarmSourceExt) getParent();
    if (parent == null)
    {
      throw new NotRunningException("OutOfRangeFaultAlgorithm has no parent AlarmSourceExt");
    }

    BAlarmState currentState = parent.getAlarmState();
    if (currentState == BAlarmState.fault)
    {
      //Bug 7357: (also in started) check Fault High or Low
      //          states are set in the High Low and Normal State classes
      if (getInHighState())
        current = new HighAlarmState();
      else if (getInLowState())
        current = new LowAlarmState();
    }
    else
      current = new NormalState();
    
    return current.evaluate(presentValue);
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
    public abstract BAlarmState evaluate(double presentValue);

  }

  //////////////////////////////////////////////////////////////
  // Normal state of the object
  //////////////////////////////////////////////////////////////
  private class NormalState
    extends OutOfRangeState
  {
    public NormalState()
    {
      setInHighState(false);
      setInLowState(false);
    }

    @Override
    public String tag() {return "Normal";}

    @Override
    public BAlarmState evaluate(double presentValue)
    {
      //  If 'presentValue' falls below the low limit, enter
      //  the low alarm validation state
      if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        transition(new LowAlarmState());
        return BAlarmState.fault;
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
        transition(new HighAlarmState());
        return BAlarmState.fault;
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible high alarm.  'Present
  // value' must exceed the 'high limit' 
  //////////////////////////////////////////////////////////////
  private class ValidateHighAlarmState
    extends OutOfRangeState
  {
    public ValidateHighAlarmState()
    {
    }

    @Override
    public String tag() {return "ValidateHighAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue)
    {
      //  If 'presentValue' falls below the low limit while
      //  we are validating a high alarm, enter the low
      //  alarm validation state.
      if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        transition(new LowAlarmState());
        return BAlarmState.fault;
      }
      //  this is an official alarm
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        transition(new HighAlarmState());
        return BAlarmState.fault;
      }
      //  move to the normal state.
      else if (presentValue <= getHighLimit())
      {
        transition(new NormalState());
        return BAlarmState.normal;
      }

      return null;
    }
  }


  //////////////////////////////////////////////////////////////
  // State when value is in high alarm.  'Present Value' must
  // fall below  the 'high limit' minus 'deadband'  before a 
  // return-to-normal is generated
  //////////////////////////////////////////////////////////////
  private class HighAlarmState
    extends OutOfRangeState
  {
    public HighAlarmState()
    {
      setInHighState(true);
      setInLowState(false);
    }

    @Override
    public String tag() {return "HighAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue)
    {
      //  If present value is still greater than alarm limit
      //  minus deadband, we are still in alarm.
      if (presentValue > (getHighLimit() - getDeadband()))
      {
        //  Do nothing
      }
      //  If we are in High Alarm and the 'presentValue' falls below
      //  the low limit, enter the validate low alarm state
      else if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        transition(new LowAlarmState());
        return BAlarmState.fault;
      }
      //  If 'presentValue' falls below the high limit
      //  minus the deadband, enter the return-from-high
      //  validation state
      else if (presentValue <= (getHighLimit() - getDeadband()))
      {
        transition(new NormalState());
        return BAlarmState.normal;
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible return to normal from
  // a high alarm.  'presentValue' must be stay below 'high
  // limit' minus 'deadband' before a
  // return-to-normal is generated.
  //////////////////////////////////////////////////////////////
  private class ValidateReturnFromHighState
    extends OutOfRangeState
  {
    public ValidateReturnFromHighState()
    {
    }

    @Override
    public String tag() {return "ValidateReturnFromHighState";}

    @Override
    public BAlarmState evaluate(double presentValue)
    {
      //  If 'presentValue' is greater than or equal to alarm
      //  limit plus deadband, return to the alarm state
      if ((presentValue > (getHighLimit() - getDeadband())) && isHighLimitEnabled())
      {
        transition(new HighAlarmState());
        return BAlarmState.fault;
      }
      //  If we are validating a return from high alarm and
      //  the 'presentValue'  fall below the low limit, enter
      //  the validate low alarm state
      if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        transition(new LowAlarmState());
        return BAlarmState.fault;
      }
      //  return to the normal state.
      else if ( presentValue < (getHighLimit() - getDeadband()))
      {
        transition(new NormalState());
        return BAlarmState.normal;
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible low alarm.  'Present
  // value' must fall below  the 'low limit' before an alarm 
  // is generated
  //////////////////////////////////////////////////////////////
  private class ValidateLowAlarmState
    extends OutOfRangeState
  {
    public ValidateLowAlarmState()
    {
    }

    @Override
    public String tag() {return "ValidateLowAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue)
    {
      // this is an offical alarm.
      if ((presentValue < getLowLimit()) && isLowLimitEnabled())
      {
        transition(new LowAlarmState());
        return BAlarmState.fault;
      }
      //  If 'presentValue' falls exceeds the high limit
      //  while we are validating a low alarm, enter the
      //  high alarm validation state.
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        transition(new HighAlarmState());
        return BAlarmState.fault;
      }
      // 'move to the normal state
      else if (presentValue >= getLowLimit())
      {
        transition(new NormalState());
        return BAlarmState.normal;
      }

      return null;

    }
  }

  //////////////////////////////////////////////////////////////
  // State when value is in low alarm.  'Present Value' must
  // fall below  the 'high limit - deadband' before a 
  // return-to-normal is generated
  //////////////////////////////////////////////////////////////
  private class LowAlarmState
    extends OutOfRangeState
  {
    public LowAlarmState()
    {
      setInHighState(false);
      setInLowState(true);
    }

    @Override
    public String tag() {return "LowAlarmState";}

    @Override
    public BAlarmState evaluate(double presentValue)
    {
        
      //  If present value is still less than or equal to
      //  alarm limit plus deadband, we are still in alarm
      if (presentValue < (getLowLimit() + getDeadband()))
      {
        //  Do nothing
      }
      //  If we are in Low Alarm and the 'presentValue'
      //  exceeds the high limit, enter the validate high alarm state
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        transition(new HighAlarmState());
        return BAlarmState.fault;
      }
      //  If 'presentValue' falls exceeds the low limit
      //  plus the deadband, enter the return-from-low
      //  validation state
      else if (presentValue >= (getLowLimit()+getDeadband()))
      {
        transition(new NormalState());
        return BAlarmState.normal;
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible return to normal from
  // a low alarm.  'Present value' must exceed the 'low limit'
  // plus 'deadband' before a return-to-normal is generated.
  //////////////////////////////////////////////////////////////
  private class ValidateReturnFromLowState
    extends OutOfRangeState
  {
    public ValidateReturnFromLowState()
    {
    }

    @Override
    public String tag() {return "ValidateReturnFromLowState";}

    @Override
    public BAlarmState evaluate(double presentValue)
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
      else if ((presentValue > getHighLimit()) && isHighLimitEnabled())
      {
        transition(new HighAlarmState());
        return BAlarmState.fault;
      }
      //  return to the normal state.
      else if (presentValue > (getLowLimit() + getDeadband()))
      {
        transition(new NormalState());
        return BAlarmState.normal;
      }

      return null;
    }
  }


//////////////////////////////////////////////////////////////
// Attributes
//////////////////////////////////////////////////////////////

  OutOfRangeState current = new NormalState();



}
