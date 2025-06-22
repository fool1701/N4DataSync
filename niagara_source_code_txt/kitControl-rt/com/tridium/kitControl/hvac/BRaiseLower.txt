/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.hvac;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.kitControl.BKitNumeric;
import com.tridium.kitControl.enums.BRaiseLowerFunction;

/**
 * Raise Lower
 * 
 * @author    Gareth Johnson
 * @creation  30 Nov 2006
 * @version   $Revision: 1$ $Date: 01/29/2007 12:20 AM$
 * @since     Niagara 3.4
 */

@NiagaraType
/*
 The output (in volts)
 */
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY | Flags.OPERATOR
)
/*
 Control input position (percentage)
 */
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY | Flags.TRANSIENT,
  facets = @Facet("BFacets.make(BFacets.MIN, BDouble.make(MIN_IN_VALUE), BFacets.MAX, BDouble.make(MAX_IN_VALUE))")
)
@NiagaraProperty(
  name = "cancelSynchronizationOfflimits",
  type = "boolean",
  defaultValue = "false"
)
/*
 The last moved input
 */
@NiagaraProperty(
  name = "lastValidIn",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN,
  facets = @Facet("BFacets.make(BFacets.MIN, BDouble.make(MIN_IN_VALUE), BFacets.MAX, BDouble.make(MAX_IN_VALUE))")
)
/*
 Current virtual position (percentage)
 */
@NiagaraProperty(
  name = "virtualPosition",
  type = "double",
  defaultValue = "0.0",
  flags = Flags.READONLY | Flags.TRANSIENT,
  facets = @Facet("BFacets.make(BFacets.MIN, BDouble.make(MIN_IN_VALUE), BFacets.MAX, BDouble.make(MAX_IN_VALUE))")
)
/*
 output to raise
 */
@NiagaraProperty(
  name = "raise",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.OPERATOR
)
/*
 output to lower
 */
@NiagaraProperty(
  name = "lower",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.OPERATOR
)
/*
 Current function
 */
@NiagaraProperty(
  name = "function",
  type = "BRaiseLowerFunction",
  defaultValue = "BRaiseLowerFunction.offState",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.OPERATOR
)
/*
 The input change deadband
 */
@NiagaraProperty(
  name = "deadBand",
  type = "double",
  defaultValue = "0.5",
  facets = @Facet("BFacets.make(BFacets.MIN, BDouble.make(0.0), BFacets.MAX, BDouble.make(5.0))")
)
/*
 The drive time
 */
@NiagaraProperty(
  name = "driveTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(60000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1000))")
)
/*
 Provide a midnight reset (just before midnight)
 */
@NiagaraProperty(
  name = "midnightResetEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 The reset time
 */
@NiagaraProperty(
  name = "midnightResetTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT.timeOfDay(23, 55, 00, 00)",
  flags = Flags.HIDDEN,
  facets = @Facet("BFacets.make(BFacets.SHOW_DATE, BBoolean.FALSE, BFacets.SHOW_TIME, BBoolean.TRUE)")
)
/*
 Action callback to revert the object to a static state
 Use normal engine thread to execute
 */
@NiagaraAction(
  name = "finishMoving",
  flags = Flags.HIDDEN | Flags.ASYNC
)
/*
 Called to update the virtual position
 */
@NiagaraAction(
  name = "update",
  flags = Flags.HIDDEN | Flags.ASYNC
)
/*
 Called to reset and lower (double the the drive time)
 */
@NiagaraAction(
  name = "reset",
  flags = Flags.ASYNC
)
public final class BRaiseLower extends BKitNumeric implements BIStatus, BINumeric
{
  // These are used for min and max facet values and must be placed before the slot code
  private static final double MAX_IN_VALUE = 100.0;
  private static final double MIN_IN_VALUE = 0.0;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.hvac.BRaiseLower(1258526680)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * The output (in volts)
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY | Flags.OPERATOR, new BStatusNumeric(), null);

  /**
   * Get the {@code out} property.
   * The output (in volts)
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * The output (in volts)
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * Control input position (percentage)
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusNumeric(), BFacets.make(BFacets.MIN, BDouble.make(MIN_IN_VALUE), BFacets.MAX, BDouble.make(MAX_IN_VALUE)));

  /**
   * Get the {@code in} property.
   * Control input position (percentage)
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * Control input position (percentage)
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "cancelSynchronizationOfflimits"

  /**
   * Slot for the {@code cancelSynchronizationOfflimits} property.
   * @see #getCancelSynchronizationOfflimits
   * @see #setCancelSynchronizationOfflimits
   */
  public static final Property cancelSynchronizationOfflimits = newProperty(0, false, null);

  /**
   * Get the {@code cancelSynchronizationOfflimits} property.
   * @see #cancelSynchronizationOfflimits
   */
  public boolean getCancelSynchronizationOfflimits() { return getBoolean(cancelSynchronizationOfflimits); }

  /**
   * Set the {@code cancelSynchronizationOfflimits} property.
   * @see #cancelSynchronizationOfflimits
   */
  public void setCancelSynchronizationOfflimits(boolean v) { setBoolean(cancelSynchronizationOfflimits, v, null); }

  //endregion Property "cancelSynchronizationOfflimits"

  //region Property "lastValidIn"

  /**
   * Slot for the {@code lastValidIn} property.
   * The last moved input
   * @see #getLastValidIn
   * @see #setLastValidIn
   */
  public static final Property lastValidIn = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN, new BStatusNumeric(), BFacets.make(BFacets.MIN, BDouble.make(MIN_IN_VALUE), BFacets.MAX, BDouble.make(MAX_IN_VALUE)));

  /**
   * Get the {@code lastValidIn} property.
   * The last moved input
   * @see #lastValidIn
   */
  public BStatusNumeric getLastValidIn() { return (BStatusNumeric)get(lastValidIn); }

  /**
   * Set the {@code lastValidIn} property.
   * The last moved input
   * @see #lastValidIn
   */
  public void setLastValidIn(BStatusNumeric v) { set(lastValidIn, v, null); }

  //endregion Property "lastValidIn"

  //region Property "virtualPosition"

  /**
   * Slot for the {@code virtualPosition} property.
   * Current virtual position (percentage)
   * @see #getVirtualPosition
   * @see #setVirtualPosition
   */
  public static final Property virtualPosition = newProperty(Flags.READONLY | Flags.TRANSIENT, 0.0, BFacets.make(BFacets.MIN, BDouble.make(MIN_IN_VALUE), BFacets.MAX, BDouble.make(MAX_IN_VALUE)));

  /**
   * Get the {@code virtualPosition} property.
   * Current virtual position (percentage)
   * @see #virtualPosition
   */
  public double getVirtualPosition() { return getDouble(virtualPosition); }

  /**
   * Set the {@code virtualPosition} property.
   * Current virtual position (percentage)
   * @see #virtualPosition
   */
  public void setVirtualPosition(double v) { setDouble(virtualPosition, v, null); }

  //endregion Property "virtualPosition"

  //region Property "raise"

  /**
   * Slot for the {@code raise} property.
   * output to raise
   * @see #getRaise
   * @see #setRaise
   */
  public static final Property raise = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.OPERATOR, new BStatusBoolean(), null);

  /**
   * Get the {@code raise} property.
   * output to raise
   * @see #raise
   */
  public BStatusBoolean getRaise() { return (BStatusBoolean)get(raise); }

  /**
   * Set the {@code raise} property.
   * output to raise
   * @see #raise
   */
  public void setRaise(BStatusBoolean v) { set(raise, v, null); }

  //endregion Property "raise"

  //region Property "lower"

  /**
   * Slot for the {@code lower} property.
   * output to lower
   * @see #getLower
   * @see #setLower
   */
  public static final Property lower = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.OPERATOR, new BStatusBoolean(), null);

  /**
   * Get the {@code lower} property.
   * output to lower
   * @see #lower
   */
  public BStatusBoolean getLower() { return (BStatusBoolean)get(lower); }

  /**
   * Set the {@code lower} property.
   * output to lower
   * @see #lower
   */
  public void setLower(BStatusBoolean v) { set(lower, v, null); }

  //endregion Property "lower"

  //region Property "function"

  /**
   * Slot for the {@code function} property.
   * Current function
   * @see #getFunction
   * @see #setFunction
   */
  public static final Property function = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.OPERATOR, BRaiseLowerFunction.offState, null);

  /**
   * Get the {@code function} property.
   * Current function
   * @see #function
   */
  public BRaiseLowerFunction getFunction() { return (BRaiseLowerFunction)get(function); }

  /**
   * Set the {@code function} property.
   * Current function
   * @see #function
   */
  public void setFunction(BRaiseLowerFunction v) { set(function, v, null); }

  //endregion Property "function"

  //region Property "deadBand"

  /**
   * Slot for the {@code deadBand} property.
   * The input change deadband
   * @see #getDeadBand
   * @see #setDeadBand
   */
  public static final Property deadBand = newProperty(0, 0.5, BFacets.make(BFacets.MIN, BDouble.make(0.0), BFacets.MAX, BDouble.make(5.0)));

  /**
   * Get the {@code deadBand} property.
   * The input change deadband
   * @see #deadBand
   */
  public double getDeadBand() { return getDouble(deadBand); }

  /**
   * Set the {@code deadBand} property.
   * The input change deadband
   * @see #deadBand
   */
  public void setDeadBand(double v) { setDouble(deadBand, v, null); }

  //endregion Property "deadBand"

  //region Property "driveTime"

  /**
   * Slot for the {@code driveTime} property.
   * The drive time
   * @see #getDriveTime
   * @see #setDriveTime
   */
  public static final Property driveTime = newProperty(0, BRelTime.make(60000), BFacets.make(BFacets.MIN, BRelTime.make(1000)));

  /**
   * Get the {@code driveTime} property.
   * The drive time
   * @see #driveTime
   */
  public BRelTime getDriveTime() { return (BRelTime)get(driveTime); }

  /**
   * Set the {@code driveTime} property.
   * The drive time
   * @see #driveTime
   */
  public void setDriveTime(BRelTime v) { set(driveTime, v, null); }

  //endregion Property "driveTime"

  //region Property "midnightResetEnabled"

  /**
   * Slot for the {@code midnightResetEnabled} property.
   * Provide a midnight reset (just before midnight)
   * @see #getMidnightResetEnabled
   * @see #setMidnightResetEnabled
   */
  public static final Property midnightResetEnabled = newProperty(0, true, null);

  /**
   * Get the {@code midnightResetEnabled} property.
   * Provide a midnight reset (just before midnight)
   * @see #midnightResetEnabled
   */
  public boolean getMidnightResetEnabled() { return getBoolean(midnightResetEnabled); }

  /**
   * Set the {@code midnightResetEnabled} property.
   * Provide a midnight reset (just before midnight)
   * @see #midnightResetEnabled
   */
  public void setMidnightResetEnabled(boolean v) { setBoolean(midnightResetEnabled, v, null); }

  //endregion Property "midnightResetEnabled"

  //region Property "midnightResetTime"

  /**
   * Slot for the {@code midnightResetTime} property.
   * The reset time
   * @see #getMidnightResetTime
   * @see #setMidnightResetTime
   */
  public static final Property midnightResetTime = newProperty(Flags.HIDDEN, BAbsTime.DEFAULT.timeOfDay(23, 55, 00, 00), BFacets.make(BFacets.SHOW_DATE, BBoolean.FALSE, BFacets.SHOW_TIME, BBoolean.TRUE));

  /**
   * Get the {@code midnightResetTime} property.
   * The reset time
   * @see #midnightResetTime
   */
  public BAbsTime getMidnightResetTime() { return (BAbsTime)get(midnightResetTime); }

  /**
   * Set the {@code midnightResetTime} property.
   * The reset time
   * @see #midnightResetTime
   */
  public void setMidnightResetTime(BAbsTime v) { set(midnightResetTime, v, null); }

  //endregion Property "midnightResetTime"

  //region Action "finishMoving"

  /**
   * Slot for the {@code finishMoving} action.
   * Action callback to revert the object to a static state
   * Use normal engine thread to execute
   * @see #finishMoving()
   */
  public static final Action finishMoving = newAction(Flags.HIDDEN | Flags.ASYNC, null);

  /**
   * Invoke the {@code finishMoving} action.
   * Action callback to revert the object to a static state
   * Use normal engine thread to execute
   * @see #finishMoving
   */
  public void finishMoving() { invoke(finishMoving, null, null); }

  //endregion Action "finishMoving"

  //region Action "update"

  /**
   * Slot for the {@code update} action.
   * Called to update the virtual position
   * @see #update()
   */
  public static final Action update = newAction(Flags.HIDDEN | Flags.ASYNC, null);

  /**
   * Invoke the {@code update} action.
   * Called to update the virtual position
   * @see #update
   */
  public void update() { invoke(update, null, null); }

  //endregion Action "update"

  //region Action "reset"

  /**
   * Slot for the {@code reset} action.
   * Called to reset and lower (double the the drive time)
   * @see #reset()
   */
  public static final Action reset = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code reset} action.
   * Called to reset and lower (double the the drive time)
   * @see #reset
   */
  public void reset() { invoke(reset, null, null); }

  //endregion Action "reset"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRaiseLower.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Called when the component is started
   */
  @Override
  public void started() throws Exception
  {
    if (getMidnightResetEnabled())
    {
      startResetTimer(BAbsTime.now());
    }
    
    // Update once the component has started
    update();
  }

  /**
   * Called when the component is stopped
   */
  @Override
  public void stopped() throws Exception
  {
    stopResetTimer();
    stopAndUpdateVirtualPosition();
  }
  
  /**
   * Called when one of the properties is changed
   */
  @Override
  public void changed(Property prop, Context cx)
  {
    if (isRunning())
    {
      if (prop.equals(BKitNumeric.propagateFlags))
      {
        // If the propagation flags have changed, then update the outputs
        BStatus s = propagate(getLastValidIn().getStatus());
        
        getOut().setStatus(s);
        getRaise().setStatus(s);
        getLower().setStatus(s);
      }
      else if (prop.equals(midnightResetEnabled) || prop.equals(midnightResetTime))
      {
        // Turn the midnight reset on or off
        if (getMidnightResetEnabled())
        {
          startResetTimer(BAbsTime.now());
        }
        else
        {
          stopResetTimer();
        }
      }
      else if (prop.equals(in))
      {
        // If the input changes then validate via the deadband
        double deadBand = getDeadBand();
        double inValue = getIn().getValue();

        // Cap between 100 and 0
        if (inValue > MAX_IN_VALUE)
        {
          inValue = MAX_IN_VALUE;
        }
        else if (inValue < MIN_IN_VALUE)
        {
          inValue = MIN_IN_VALUE;
        }

        // If we're not outside the deadband then set the last input (or if we're at 100 or 0)
        if (Math.abs(getLastValidIn().getValue() - inValue) > HALF * deadBand)
        {
          // Set the new values
          getLastValidIn().setValue(getIn().getValue());
          getLastValidIn().setStatus(getIn().getStatus());
        }
      }
      else if (prop.equals(lastValidIn) &&
               !getFunction().equals(BRaiseLowerFunction.resetLowerState) &&
               !getFunction().equals(BRaiseLowerFunction.resetRaiseState))
      {
        // If the last input changes (and we are not in a reset state) then update the module
        BStatus s = propagate(getLastValidIn().getStatus());
        
        getOut().setStatus(s);
        getRaise().setStatus(s);
        getLower().setStatus(s);
        
        // Ensure the output is updated
        update();
      }
    }
  }   
      
  /**
   * Return the Slot facets
   */
  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.equals(out))
    {
      return getFacets();
    }
    else
    {
      return super.getSlotFacets(slot);
    }
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

////////////////////////////////////////////////////////////////
// Raise and lower
////////////////////////////////////////////////////////////////

  /**
   * Action handler: updates the outputs
   */
  @SuppressWarnings("squid:S109")
  public void doUpdate()
  { 
    LOGGER.fine("Update in progress");
    
    // If we are currently moving then stop it and calculate the virtual position
    stopAndUpdateVirtualPosition();
    
    double delta = 0.0;
    
    BRaiseLowerFunction newFunctionState = BRaiseLowerFunction.staticState;

    if (!getFunction().equals(BRaiseLowerFunction.resetLowerState) &&
        !getFunction().equals(BRaiseLowerFunction.resetRaiseState))
    {
      // In states off, static, lower, or raise; change to resetLower, resetRaise, lower, or raise
      if (LOGGER.isLoggable(Level.FINE))
      {
        LOGGER.fine("Input: " + getLastValidIn().getValue() + " Virtual: " + getVirtualPosition());
      }
      
      // If the user wants 100 or 0 as the input then always operate double the drive time
      if (getLastValidIn().getValue() >= MAX_IN_VALUE)
      {
        newFunctionState = BRaiseLowerFunction.resetRaiseState;
      }
      else if (getLastValidIn().getValue() <= MIN_IN_VALUE)
      {
        newFunctionState = BRaiseLowerFunction.resetLowerState;
      }
      else
      {
        // If not 100 or 0 then work out the percentage
        delta = getLastValidIn().getValue() - getVirtualPosition();
        
        if (delta > 0.0)
        {
          newFunctionState = BRaiseLowerFunction.raiseState;
        }
        else if (delta < 0.0)
        {
          newFunctionState = BRaiseLowerFunction.lowerState;
        }
      }
      
      changeFunctionState(newFunctionState);
    } 
    else
    {
      // In states resetLower or resetRaise; keep it there
      newFunctionState = getFunction();
    }

    // If the new state isn't static then move it
    if (!getFunction().equals(BRaiseLowerFunction.staticState))
    {
      BRelTime timePeriod;
      
      // If reset or then double the drive time
      if (newFunctionState.equals(BRaiseLowerFunction.resetLowerState) ||
          newFunctionState.equals(BRaiseLowerFunction.resetRaiseState))
      {
        // squid:S109- Magic numbers should not be used: 2 cannot be turned into a meaningful constant
        long resetDriveTime = getCancelSynchronizationOfflimits() ?
          getDriveTime().getMillis() :
          getDriveTime().getMillis() * 2;
        timePeriod = BRelTime.make(resetDriveTime);
      }
      else
      {
        // Calculate amount of time to raise/lower for
        timePeriod = BRelTime.make((long)(getDriveTime().getMillis() / MAX_IN_VALUE * Math.abs(delta)));
      }
      
      // Make a note of these before starting the timer
      lastVirtualPositionTime = BRelTime.make(Clock.ticks());
      newVirtualPosition = getLastValidIn().getValue();
      
      if (LOGGER.isLoggable(Level.FINE))
      {
        LOGGER.fine("Activating timer for drive time: " + timePeriod);
      }
      
      // Start the timer for the desired amount of time
      driveTimePeriod = timePeriod;
      driveTimeTicket = Clock.schedule(
        /* target */ this,
        /* time */ timePeriod,
        /* action */ finishMoving,
        /* arg */ null);
    }
  } 
    
  /**
   * Update virtual position
   */
  private void stopAndUpdateVirtualPosition()
  {
    if (driveTimeTicket != null && !lastVirtualPositionTime.equals(BRelTime.DEFAULT))
    {
      BRelTime timePeriod = driveTimePeriod;
      driveTimeTicket.cancel();
      driveTimeTicket = null;

      // Get the time difference
      long timeDiff = Clock.ticks() - lastVirtualPositionTime.getMillis();
      
      double oldVirtualPosition = getVirtualPosition();
            
      // Was in a state of raising
      double delta = newVirtualPosition - oldVirtualPosition; 
      
      double calcVirtualPosition = oldVirtualPosition + ((double)timeDiff / timePeriod.getMillis() * delta);
      
      // If we've doubled the drive time then ensure values are in a valid range
      if (calcVirtualPosition < MIN_IN_VALUE)
      {
        calcVirtualPosition = MIN_IN_VALUE;
      }
      else if (calcVirtualPosition > MAX_IN_VALUE)
      {
        calcVirtualPosition = MAX_IN_VALUE;
      }
      
      // Calc the virtual position
      setVirtualPosition(calcVirtualPosition);
      
      if (LOGGER.isLoggable(Level.FINE))
      {
        LOGGER.fine("After stop and update on raise vp = " + getVirtualPosition());
      }

      // Reset variables
      lastVirtualPositionTime = BRelTime.DEFAULT;
      newVirtualPosition = 0.0;
    }
  }
  
  /**
   * Change function state
   */
  private void changeFunctionState(BRaiseLowerFunction functionState)
  {
    setFunction(functionState);
    
    // Set the current status on all outputs
    BStatus newStatus = propagate(getLastValidIn().getStatus());
    
    getRaise().setStatus(newStatus);
    getLower().setStatus(newStatus);
    getOut().setStatus(newStatus);
    
    if (functionState.equals(BRaiseLowerFunction.lowerState) ||
        functionState.equals(BRaiseLowerFunction.resetLowerState))
    {
      getRaise().setValue(false);
      getLower().setValue(true); 
      getOut().setValue(LOWER_STATE_OUT_VALUE);
    }
    else if (functionState.equals(BRaiseLowerFunction.raiseState) ||
             functionState.equals(BRaiseLowerFunction.resetRaiseState))
    {
      getRaise().setValue(true);
      getLower().setValue(false);
      getOut().setValue(RAISE_STATE_OUT_VALUE);
    }
    else
    {
      getRaise().setValue(false);
      getLower().setValue(false);
      
      if (functionState.equals(BRaiseLowerFunction.offState))
      {
        getOut().setValue(OFF_STATE_OUT_VALUE);
      }
      else
      {
        // static state
        getOut().setValue(STATIC_STATE_OUT_VALUE);
      }
    }
  }
  
  /**
   * Action handler: called after the moving time has expired
   */
  public void doFinishMoving()
  {    
    LOGGER.fine("Finish moving called");

    boolean processUpdate = false;

    if (getFunction().equals(BRaiseLowerFunction.resetLowerState))
    {
      // Once the reset has happened then set the virtual position to 0
      setVirtualPosition(MIN_IN_VALUE);

      processUpdate = getLastValidIn().getValue() > MIN_IN_VALUE;
    }
    else if (getFunction().equals(BRaiseLowerFunction.resetRaiseState))
    {
     // Once the reset has happened then set the virtual position to 100
      setVirtualPosition(MAX_IN_VALUE);
      
      processUpdate = getLastValidIn().getValue() < MAX_IN_VALUE;
    }
    else
    {
      // Set the virtual position to its desired setting now it has moved
      setVirtualPosition(newVirtualPosition);
    }
    
    // Finished process so don't move anything
    changeFunctionState(BRaiseLowerFunction.staticState);
    
    // Nullify these values
    lastVirtualPositionTime = BRelTime.DEFAULT;
    newVirtualPosition = 0.0;
    driveTimeTicket = null;
    
    // If we need to process the input again then just update
    if (processUpdate)
    {
      doUpdate();
    }
  }

////////////////////////////////////////////////////////////////
// Reset
////////////////////////////////////////////////////////////////  
 
  /**
   *  Start the reset timer
   */
  private void startResetTimer(BAbsTime date)
  {
    if (resetTicket != null)
    {
      resetTicket.cancel();
    }
    
    BAbsTime resetTime = getMidnightResetTime();
    
    if (LOGGER.isLoggable(Level.FINE))
    {
      LOGGER.fine("Next reset time: " + resetTime);
    }
    
    BAbsTime newDateTime = date.timeOfDay(resetTime.getHour(), resetTime.getMinute(), resetTime.getSecond(), 0);
    
    if (LOGGER.isLoggable(Level.FINE))
    {
      LOGGER.fine("Next reset event: " + newDateTime);
    }
    
    // Reset the dampers
    resetTicket = Clock.schedule(
      /* target */ this,
      /* time */ newDateTime,
      /* action */ reset,
      /* arg */ null);
  }
  
  /**
   * Stop the reset timer
   */
  private void stopResetTimer()
  {
    if (resetTicket != null)
    {
      resetTicket.cancel();
      resetTicket = null;
    }
  }
    
  /**
   * Action handler: called to reset the system so the virtual position is ok
   */
  public void doReset()
  {
    // Call the reset for the following day
    startResetTimer(BAbsTime.now().nextDay());
    
    // Change to reset
    changeFunctionState(BRaiseLowerFunction.resetLowerState);
    
    // Commence the update with the reset flag
    update();
  }

////////////////////////////////////////////////////////////////
// BIStatus
////////////////////////////////////////////////////////////////  

  @Override
  public BStatus getStatus()
  {
    return getOut().getStatus();
  }
  
////////////////////////////////////////////////////////////////
// BINumeric
////////////////////////////////////////////////////////////////  

  @Override
  public double getNumeric()
  {
    return getOut().getValue();
  }

  @Override
  public BFacets getNumericFacets()
  {
    return getFacets();
  }
  
////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private static final double HALF = 0.5;

  private static final double RAISE_STATE_OUT_VALUE = 10.0;
  private static final double STATIC_STATE_OUT_VALUE = 7.0;
  private static final double LOWER_STATE_OUT_VALUE = 4.0;
  private static final double OFF_STATE_OUT_VALUE = 0.0;

  // The Clock for the drive time
  private Clock.Ticket driveTimeTicket = null;
  private BRelTime driveTimePeriod = BRelTime.DEFAULT;
  
  // The new virtual position and time when it last changed
  private double newVirtualPosition = 0.0;
  private BRelTime lastVirtualPositionTime = BRelTime.DEFAULT;   
    
  // Log for the raise lower component
  private static final Logger LOGGER = Logger.getLogger("kitControl.raiseLower");
  
  // Reset at end of day
  private Clock.Ticket resetTicket = null;
  
  private static final BIcon icon = BIcon.std("control/control.png");
}
