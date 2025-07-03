/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.timer;

import javax.baja.control.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BNumericDelay is a component that provides numeric delay / filtering for a numeric value.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric()"
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "updateTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0))")
)
@NiagaraProperty(
  name = "maxStepSize",
  type = "double",
  defaultValue = "0.5d"
)
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraAction(
  name = "timerExpired",
  flags = Flags.HIDDEN
)
public class BNumericDelay
  extends BComponent
  implements BIStatus, BINumeric
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.timer.BNumericDelay(3411103976)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeNumeric(), null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "updateTime"

  /**
   * Slot for the {@code updateTime} property.
   * @see #getUpdateTime
   * @see #setUpdateTime
   */
  public static final Property updateTime = newProperty(0, BRelTime.make(1000), BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0)));

  /**
   * Get the {@code updateTime} property.
   * @see #updateTime
   */
  public BRelTime getUpdateTime() { return (BRelTime)get(updateTime); }

  /**
   * Set the {@code updateTime} property.
   * @see #updateTime
   */
  public void setUpdateTime(BRelTime v) { set(updateTime, v, null); }

  //endregion Property "updateTime"

  //region Property "maxStepSize"

  /**
   * Slot for the {@code maxStepSize} property.
   * @see #getMaxStepSize
   * @see #setMaxStepSize
   */
  public static final Property maxStepSize = newProperty(0, 0.5d, null);

  /**
   * Get the {@code maxStepSize} property.
   * @see #maxStepSize
   */
  public double getMaxStepSize() { return getDouble(maxStepSize); }

  /**
   * Set the {@code maxStepSize} property.
   * @see #maxStepSize
   */
  public void setMaxStepSize(double v) { setDouble(maxStepSize, v, null); }

  //endregion Property "maxStepSize"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericDelay.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNumericDelay()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    startTimer();
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;
    if( p == updateTime )
      startTimer();
    else if (p == in)
    {
      if(ticket == null)
        calculate();
    }
  }

  public void calculate()
  {
    if(!getIn().getStatus().isValid())
      return;
    double input = getIn().getValue();
    double output = getOut().getValue();
    double delta = input - output;
    if(input != output)
    {
      if(getUpdateTime().getMillis() == 0l)
      {
        if( ticket != null) ticket.cancel();
        setOutput(input);
      }
      else
      {
        if( (float)Math.abs(delta) > getMaxStepSize() )
        {
          if(input > output)
            setOutput(output + getMaxStepSize() );
          else
            setOutput(output - getMaxStepSize() );
        }
        else
          setOutput(input);
      }
    }
  }

  private void setOutput(double value)
  {
    getOut().setValue(value);
  }

  public void doTimerExpired()
  {
    calculate();
  }


  void startTimer()
  {
    if( ticket != null) ticket.cancel();
    BRelTime time = getUpdateTime();
    if(time.getMillis() != 0l)
      ticket = Clock.schedulePeriodically(this, time, timerExpired, null);
  }    
  

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }

  /**
   * Apply the "facets" property to the "out" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == out) return getFacets();
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }

////////////////////////////////////////////////////////////////
// BINumeric interface
////////////////////////////////////////////////////////////////

  public double getNumeric() { return getOut().getValue(); }

  public final BFacets getNumericFacets() { return getFacets(); }


  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Clock.Ticket ticket;      // Used to manage the current timer
  
}
