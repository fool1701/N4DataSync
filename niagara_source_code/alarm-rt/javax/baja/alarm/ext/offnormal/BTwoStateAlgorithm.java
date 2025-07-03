/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

import javax.baja.alarm.*;
import javax.baja.alarm.ext.*;
import javax.baja.control.*;
import javax.baja.control.enums.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BTwoStateAlgorithm implements a generic algorithm for
 * objects with only an normal / offnormal states (vs. high alarm,
 * low alarm, etc.)  The generic algorithm conforms to the
 * CHANGE_OF_STATE algorithm described in BACnet 13.3.2 and
 * the COMMAND_FAILURE algorithm described in 13.3.4.
 *
 * @author    Dan Giorgis
 * @creation  04 May 01
 * @version   $Revision: 20$ $Date: 6/9/09 9:56:02 AM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
public abstract class BTwoStateAlgorithm
  extends BOffnormalAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BTwoStateAlgorithm(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:02 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTwoStateAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Update Methods
////////////////////////////////////////////////////////////////

  @Override
  public void started()
  {
    BAlarmState currentState = ((BAlarmSourceExt)getParent()).getAlarmState();
    if (currentState == BAlarmState.offnormal)
      current = new OffnormalState();
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
      throw new NotRunningException("TwoStateAlgorithm has no parent AlarmSourceExt");
    }

    BAlarmState currentState = parent.getAlarmState();
    
    if (current instanceof ValidateOffnormalState)
    {
      return current.evaluate(isNormal(out), toAlarmTimeDelay);
    } else if (current instanceof ValidateReturnFromOffnormalState)
    {
      return current.evaluate(isNormal(out), toNormalTimeDelay);      
    } else if (currentState == BAlarmState.offnormal)
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

  /**
   *  Returns true is the present value is offnormal
   */
  abstract protected boolean isNormal(BStatusValue out);


  /**
   *  Return true if the current states is offnormal
   */
  protected boolean isCurrentOffnormal()
    { return current instanceof OffnormalState; }

  /**
   * Transitions state of object from one state
   * to another.
   */
  private void transition(TwoState state)
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
    public abstract BAlarmState evaluate(boolean normalPV,
                                         long timeDelay);

  }

  private abstract class ValidateState
    extends TwoState
  {
  }
  
  //////////////////////////////////////////////////////////////
  // Normal state of the object
  //////////////////////////////////////////////////////////////
  private class NormalState
    extends TwoState
  {
    @Override
    public String tag() {return "Normal";}

    @Override
    public BAlarmState evaluate(boolean normalPV,
                                long timeDelay)
    {
      //  If the object is currently in a offnormal state, enter
      //  the validation state
      if (!normalPV)
      {
        if (timeDelay == 0)
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
      else
        return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible transition to offnormal.
  // 'Present value' must remain in an offnormal condition for
  // 'time delay' seconds before an alarm state transition occurs.
  //////////////////////////////////////////////////////////////
  private class ValidateOffnormalState
    extends ValidateState
  {
    public ValidateOffnormalState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateOffnormalState";}

    @Override
    public BAlarmState evaluate(boolean normalPV,
                                long timeDelay)
    {
      //  If present value returns to the normal before
      //  'timeDelay' seconds have elapsed, move to the normal
      //  state.  This will cancel the active timer.
      if (normalPV)
      {
        transition(new NormalState());

        return null;
      }
      //  If 'timeDelay' seconds have elapsed since we entered the
      //  validation state, this is an official alarm.  Otherwise, remain
      //  calm.
      else
      {
        if (isTimerExpired())
        {
          transition(new OffnormalState());

          //  Notify the source object that a to-offnormal alarm has
          //  occured
          return BAlarmState.offnormal;

        }
        else
          return null;
      }
    }
  }


  //////////////////////////////////////////////////////////////
  // State when object in offnormal condition.  'Present Value'
  // must be normal for 'time delay' seconds before a
  // return-to-normal is generated
  //////////////////////////////////////////////////////////////
  private class OffnormalState
    extends TwoState
  {
    @Override
    public String tag() {return "OffnormalState";}

    @Override
    public BAlarmState evaluate(boolean normalPV,
                                long timeDelay)
    {
      //  If present value is normal, then move to the
      //  return to normal validation state
      if (normalPV)
      {
        if (timeDelay == 0)
        {
          transition(new NormalState());
          return BAlarmState.normal;
        }
        else
        {
          transition(new ValidateReturnFromOffnormalState(timeDelay));
          return null;
        }
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
    extends ValidateState
  {
    public ValidateReturnFromOffnormalState(long timeDelay)
    {
      startTimer(timeDelay);
    }

    @Override
    public String tag() {return "ValidateReturnFromOffnormalState";}

    @Override
    public BAlarmState evaluate(boolean normalPV,
                                long timeDelay)
    {
      //  If 'timeDelay' seconds have elapsed since we entered the
      //  validation state, generate a return-to-normal.
      if (normalPV)
      {
        if (isTimerExpired())
        {
          transition(new NormalState());

          //  Notify the source object that a to-normal alarm has
          //  occured
          return BAlarmState.normal;
        }
      }
      //   The present value has returned to an offnormal condition.
      //   Re-enter the offnormal state.
      else
      {
        transition(new OffnormalState());

        return null;
      }

      return null;
    }
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  TwoState current =  new NormalState();
//  BAlarmState  state = null;

}
