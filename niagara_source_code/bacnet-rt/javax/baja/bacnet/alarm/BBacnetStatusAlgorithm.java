/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.alarm;

import static javax.baja.bacnet.BacnetConst.*;
import static javax.baja.sys.Flags.*;

import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.BAlarmState;
import javax.baja.alarm.ext.BOffnormalAlgorithm;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBacnetStatusAlgorithm defines the offnormal algorithm
 * for the <code> BacnetBitStringUtil.BACNET_STATUS_FLAGS </code> bits of a point.
 * Refer BACnet ANSI/ASHRAE Standard 135-2016 , 13.3.11
 *
 * @author    Vidya Shivamurthy
 * @creation   17 Feb 2019
 * @since     BACNet 14, Niagara R47.u1
 */
@NiagaraType
@NiagaraProperty(
  name = "alarmValues",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.make(new boolean[] {false, false, true, true})",
  flags = Flags.DEFAULT_ON_CLONE,
  facets = @Facet("BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS")
)
@NiagaraProperty(
  name = "lastMonitoredValue",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetStatusFlags\"))",
  flags = Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY,
  facets = @Facet("BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS")
)
public class BBacnetStatusAlgorithm
  extends BOffnormalAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.alarm.BBacnetStatusAlgorithm(2426626072)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmValues"

  /**
   * Slot for the {@code alarmValues} property.
   * @see #getAlarmValues
   * @see #setAlarmValues
   */
  public static final Property alarmValues = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetBitString.make(new boolean[] {false, false, true, true}), BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS);

  /**
   * Get the {@code alarmValues} property.
   * @see #alarmValues
   */
  public BBacnetBitString getAlarmValues() { return (BBacnetBitString)get(alarmValues); }

  /**
   * Set the {@code alarmValues} property.
   * @see #alarmValues
   */
  public void setAlarmValues(BBacnetBitString v) { set(alarmValues, v, null); }

  //endregion Property "alarmValues"

  //region Property "lastMonitoredValue"

  /**
   * Slot for the {@code lastMonitoredValue} property.
   * @see #getLastMonitoredValue
   * @see #setLastMonitoredValue
   */
  public static final Property lastMonitoredValue = newProperty(Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetStatusFlags")), BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS);

  /**
   * Get the {@code lastMonitoredValue} property.
   * @see #lastMonitoredValue
   */
  public BBacnetBitString getLastMonitoredValue() { return (BBacnetBitString)get(lastMonitoredValue); }

  /**
   * Set the {@code lastMonitoredValue} property.
   * @see #lastMonitoredValue
   */
  public void setLastMonitoredValue(BBacnetBitString v) { set(lastMonitoredValue, v, null); }

  //endregion Property "lastMonitoredValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetStatusAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  @Override
  public void started()
  {
    BAlarmState currentState = ((BAlarmSourceExt)getParent()).getAlarmState();
    if (currentState == BAlarmState.offnormal)
    {
      current = new OffnormalState();
    }
  }


////////////////////////////////////////////////////////////////
//  property changed processing
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning())
    {
      return;
    }

    executePoint();
  }

////////////////////////////////////////////////////////////////
//  Offnormal transition checking
////////////////////////////////////////////////////////////////
  @SuppressWarnings({"rawtypes","unchecked"})
  @Override
  public void writeAlarmData(BStatusValue out, java.util.Map map)
  {
    super.writeAlarmData(out,map);
  }

  /**
   *  Return the new alarm state or null if no change
   */
  @Override
  public BAlarmState checkAlarms(BStatusValue out, long toAlarmTimeDelay, long toNormalTimeDelay)
  {
    BAlarmSourceExt parent = (BAlarmSourceExt) getParent();
    if (parent == null)
    {
      throw new NotRunningException("BacnetStatusAlgorithm has no parent AlarmSourceExt");
    }

    BAlarmState currentState = parent.getAlarmState();
    if (current instanceof ValidateOffnormalState)
    {
      return current.evaluate(isNormal(out), toAlarmTimeDelay);
    } else if (current instanceof ValidateReturnFromOffnormalState)
    {
      return current.evaluate(isNormal(out), toNormalTimeDelay);
    }
    else if (currentState == BAlarmState.offnormal)
    {
      current = new OffnormalState();
      return current.evaluate(isNormal(out), toNormalTimeDelay);
  }
    else
  {
    current = new NormalState();
    return current.evaluate(isNormal(out), toAlarmTimeDelay);
  }
}

  protected BacnetStatus isNormal(BStatusValue out)
  {
    if(monitoredValue != null)
    {
      if (monitoredValue.equals(BBacnetBitString.emptyBitString(FOUR)))
      {
        setLastMonitoredValue(monitoredValue);
        return BacnetStatus.NORMAL;
      }
      else if (checkForBitIncrease())
      {
        setLastMonitoredValue(monitoredValue);
        return BacnetStatus.OFFNORMAL;
      }
      setLastMonitoredValue(monitoredValue);
    }

    return BacnetStatus.IGNORE;
  }

  private boolean checkForBitIncrease()
  {
    for (int i = ZERO; i < monitoredValue.getBits().length; i++)
    {
      if (monitoredValue.getBits()[i] != getLastMonitoredValue().getBits()[i] && (!getLastMonitoredValue().getBits()[i] && getAlarmValues().getBit(i)))
      {
        return true;
      }
    }
    return false;
  }

  public void setStausFlags(BBacnetBitString bacnetStatusFlags)
  {
    int size = bacnetStatusFlags.getBits().length;
    boolean[] copyBits = new boolean[size];
    for(int i = ZERO; i < size; i++)
    {
      copyBits[i] = getAlarmValues().getBit(i) ? bacnetStatusFlags.getBit(i) : false;
    }
    this.monitoredValue =  BBacnetBitString.make(copyBits);
  }

  public void setStatusFlagsOnExtWhenOutOfServiceIsChanged(boolean outOfService)
  {
    if (outOfService)
    {
      if (monitoredValue == null)
      {
        setLastMonitoredValue(BBacnetBitString.make(new boolean[] { false, false, false, false }));
        monitoredValue = BBacnetBitString.make(new boolean[] { false, false, false, true });
      }
      else
      {
        monitoredValue = BBacnetBitString.make(new boolean[]{getLastMonitoredValue().getBit(ZERO),
                getLastMonitoredValue().getBit(ONE), getLastMonitoredValue().getBit(TWO), true});
      }
    }
    else
    {
      monitoredValue = BBacnetBitString.make(new boolean[]{getLastMonitoredValue().getBit(ZERO),
              getLastMonitoredValue().getBit(ONE), getLastMonitoredValue().getBit(TWO), false});
    }
  }
  ////////////////////////////////////////////////////////////////
//  Update Methods
////////////////////////////////////////////////////////////////

  /**
   * Transitions state of object from one state
   * to another.
   */
  private void transition(BBacnetStatusAlgorithm.TwoState state)
  {
    current = state;
  }



  //////////////////////////////////////////////////////////////
  // This is the base class for all states
  //////////////////////////////////////////////////////////////
  private abstract class TwoState
  {
    ////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////
    public TwoState()
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
     * state based parsing. Returns null if no
     * change in state, or new BAlarmState
     **********************************************/
    public abstract BAlarmState evaluate(BacnetStatus normalPV,
                                         long timeDelay);

  }

  private abstract class ValidateState
    extends BBacnetStatusAlgorithm.TwoState
  {
  }

  //////////////////////////////////////////////////////////////
  // Normal state of the object
  //////////////////////////////////////////////////////////////
  private class NormalState
    extends BBacnetStatusAlgorithm.TwoState
  {
    @Override
    public String tag() {return "Normal";}

    @Override
    public BAlarmState evaluate(BacnetStatus bacnetStatus,
                                long timeDelay)
    {
      //  If the object is currently in a offnormal state, enter
      //  the validation state
      if (bacnetStatus == BacnetStatus.OFFNORMAL)
      {
        return getAlarmStateForOffnormalStatus(timeDelay);
      }
      else
      {
        return null;
      }
    }
  }

  private BAlarmState getAlarmStateForOffnormalStatus(long timeDelay)
  {
    if (timeDelay == ZERO)
    {
      transition(new OffnormalState());

      return BAlarmState.offnormal;
    }
    else
    {
      transition(new ValidateOffnormalState(timeDelay));

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible transition to offnormal.
  // 'Present value' must remain in an offnormal condition for
  // 'time delay' seconds before an alarm state transition occurs.
  //////////////////////////////////////////////////////////////
  private class ValidateOffnormalState
    extends BBacnetStatusAlgorithm.ValidateState
  {
    public ValidateOffnormalState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateOffnormalState";}

    @Override
    public BAlarmState evaluate(BacnetStatus bacnetStatus,
                                long timeDelay)
    {
      //  If present value returns to the normal before
      //  'timeDelay' seconds have elapsed, move to the normal
      //  state.  This will cancel the active timer.
      if (bacnetStatus == BacnetStatus.NORMAL)
      {
        transition(new BBacnetStatusAlgorithm.NormalState());

        return null;
      }
      //  If 'timeDelay' seconds have elapsed since we entered the
      //  validation state, this is an official alarm.  Otherwise, remain
      //  calm.
      else
      {
        if (isTimerExpired())
        {
          transition(new BBacnetStatusAlgorithm.OffnormalState());

          //  Notify the source object that a to-offnormal alarm has
          //  occured
          return BAlarmState.offnormal;

        }
        else
        {
          return null;
        }
      }
    }
  }


  //////////////////////////////////////////////////////////////
  // State when object in offnormal condition.  'Present Value'
  // must be normal for 'time delay' seconds before a
  // return-to-normal is generated
  //////////////////////////////////////////////////////////////
  private class OffnormalState
    extends BBacnetStatusAlgorithm.TwoState
  {
    @Override
    public String tag() {return "OffnormalState";}

    @Override
    public BAlarmState evaluate(BacnetStatus bacnetStatus,
                                long timeDelay)
    {
      //  If present value is normal, then move to the
      //  return to normal validation state
      if (bacnetStatus == BacnetStatus.NORMAL)
      {
        if (timeDelay == ZERO)
        {
          transition(new BBacnetStatusAlgorithm.NormalState());
          return BAlarmState.normal;
        }
        else
        {
          transition(new BBacnetStatusAlgorithm.ValidateReturnFromOffnormalState(timeDelay));
          return null;
        }
      }else  if (bacnetStatus == BacnetStatus.OFFNORMAL)
      {
        return getAlarmStateForOffnormalStatus(timeDelay);
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible return to normal from
  // offnormal.  'Present Value' must be normal for 'time delay'
  // seconds before a return-to-normal is generated
  //////////////////////////////////////////////////////////////
  private class ValidateReturnFromOffnormalState
    extends BBacnetStatusAlgorithm.ValidateState
  {
    public ValidateReturnFromOffnormalState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateReturnFromOffnormalState";}

    @Override
    public BAlarmState evaluate(BacnetStatus bacnetStatus,
                                long timeDelay)
    {
      //  If 'timeDelay' seconds have elapsed since we entered the
      //  validation state, generate a return-to-normal.
      if (bacnetStatus == BacnetStatus.NORMAL)
      {
        if (isTimerExpired())
        {
          transition(new BBacnetStatusAlgorithm.NormalState());

          //  Notify the source object that a to-normal alarm has
          //  occured
          return BAlarmState.normal;
        }
      }
      //   The present value has returned to an offnormal condition.
      //   Re-enter the offnormal state.
      else
      {
        transition(new BBacnetStatusAlgorithm.OffnormalState());

        return null;
      }

      return null;
    }
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BBacnetStatusAlgorithm.TwoState current =  new BBacnetStatusAlgorithm.NormalState();

  BBacnetBitString monitoredValue;

  enum BacnetStatus {
    NORMAL,
    OFFNORMAL,
    IGNORE
  }


}
