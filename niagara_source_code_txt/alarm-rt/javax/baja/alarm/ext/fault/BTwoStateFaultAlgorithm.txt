/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.fault;

import javax.baja.alarm.*;
import javax.baja.alarm.ext.*;
import javax.baja.control.*;
import javax.baja.control.enums.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BTwoStateFaultAlgorithm implements a generic algorithm for
 * objects with only an normal / fault states (vs. high alarm,
 * low alarm, etc.)  The generic algorithm conforms to the
 * CHANGE_OF_STATE algorithm described in BACnet 13.3.2 and
 * the COMMAND_FAILURE algorithm described in 13.3.4.
 *
 * @author    Blake M Puhak
 * @creation  08 Nov 04
 * @version   $Revision: 3$ $Date: 3/23/05 11:53:23 AM EST$
 * @since     Baja 1.0
 */

@NiagaraType
public abstract class BTwoStateFaultAlgorithm
  extends BFaultAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.fault.BTwoStateFaultAlgorithm(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:02 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTwoStateFaultAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void started()
  {
    BAlarmState currentState = ((BAlarmSourceExt)getParent()).getAlarmState();
    if (currentState == BAlarmState.fault)
      current = new FaultState();
  }
  
////////////////////////////////////////////////////////////////
//  Update Methods
////////////////////////////////////////////////////////////////

  /**
   *  Return the new alarm state or null if no change
   */
  @Override
  public BAlarmState checkFault(BStatusValue out)
  {
    BAlarmSourceExt parent = (BAlarmSourceExt) getParent();
    if (parent == null)
    {
      throw new NotRunningException("TwoStateFaultAlgorithm has no parent AlarmSourceExt");
    }

    BAlarmState currentState = parent.getAlarmState();
    if (currentState == BAlarmState.fault)
      current = new FaultState();
    else
      current = new NormalState();
    
    boolean isNorm = isNormal(out);
    return current.evaluate(isNorm);
  }


  /**
   *  Returns true is the present value is offnormal
   */
  abstract protected boolean isNormal(BStatusValue out);


  /**
   *  Return true if the current states is offnormal
   */
  protected boolean isCurrentFault()
    { return current instanceof FaultState; }

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
    public abstract BAlarmState evaluate(boolean normalPV);

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
    public BAlarmState evaluate(boolean normalPV)
    {
      //  If the object is currently in a offnormal state, enter
      //  the validation state
      if (!normalPV)
      {
        transition(new FaultState());
        return BAlarmState.fault;
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
  private class ValidateFaultState
    extends TwoState
  {
    public ValidateFaultState()
    {
    }

    @Override
    public String tag() {return "ValidateFaultState";}

    @Override
    public BAlarmState evaluate(boolean normalPV)
    {
      //  move to the normal state.
      if (normalPV)
      {
        transition(new NormalState());

        return null;
      }
      //  this is an official alarm.
      else
      {
        transition(new FaultState());

        //  Notify the source object that a to-offnormal alarm has
        //  occured
        return BAlarmState.fault;
      }
    }
  }


  //////////////////////////////////////////////////////////////
  // State when object in offnormal condition.
  //////////////////////////////////////////////////////////////
  private class FaultState
    extends TwoState
  {
    @Override
    public String tag() {return "FaultState";}

    @Override
    public BAlarmState evaluate(boolean normalPV)
    {
      //  If present value is normal, then move to the
      //  return to normal validation state
      if (normalPV)
      {
        transition(new NormalState());
        return BAlarmState.normal;
      }

      return null;
    }
  }

  //////////////////////////////////////////////////////////////
  // State when validating a possible return to normal from
  // offnormal.  
  //////////////////////////////////////////////////////////////
  private class ValidateReturnFromFaultState
    extends TwoState
  {
    public ValidateReturnFromFaultState()
    {
    }

    @Override
    public String tag() {return "ValidateReturnFromFaultState";}

    @Override
    public BAlarmState evaluate(boolean normalPV)
    {
      //  generate a return-to-normal.
      if (normalPV)
      {
        transition(new NormalState());

        //  Notify the source object that a to-normal alarm has
        //  occured
        return BAlarmState.normal;
      }
      //   The present value has returned to an offnormal condition.
      //   Re-enter the offnormal state.
      else
      {
        transition(new FaultState());

        return null;
      }

    }
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  TwoState current =  new NormalState();

}
