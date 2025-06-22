/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.timer;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBooleanDelay is a component that provices delayOn and delayOff timing.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
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
  defaultValue = "BFacets.makeBoolean()"
)
@NiagaraProperty(
  name = "in",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "onDelay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0))")
)
@NiagaraProperty(
  name = "offDelay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0))")
)
@NiagaraProperty(
  name = "onDelayActive",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "offDelayActive",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "out",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "outNot",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(true)",
  flags = Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
@NiagaraAction(
  name = "onTimerExpired",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "offTimerExpired",
  flags = Flags.HIDDEN
)
public class BBooleanDelay
  extends BComponent
  implements BIStatus, BIBoolean
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.timer.BBooleanDelay(4154090548)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeBoolean(), null);

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
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusBoolean getIn() { return (BStatusBoolean)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusBoolean v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "onDelay"

  /**
   * Slot for the {@code onDelay} property.
   * @see #getOnDelay
   * @see #setOnDelay
   */
  public static final Property onDelay = newProperty(0, BRelTime.make(1000), BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0)));

  /**
   * Get the {@code onDelay} property.
   * @see #onDelay
   */
  public BRelTime getOnDelay() { return (BRelTime)get(onDelay); }

  /**
   * Set the {@code onDelay} property.
   * @see #onDelay
   */
  public void setOnDelay(BRelTime v) { set(onDelay, v, null); }

  //endregion Property "onDelay"

  //region Property "offDelay"

  /**
   * Slot for the {@code offDelay} property.
   * @see #getOffDelay
   * @see #setOffDelay
   */
  public static final Property offDelay = newProperty(0, BRelTime.make(1000), BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0)));

  /**
   * Get the {@code offDelay} property.
   * @see #offDelay
   */
  public BRelTime getOffDelay() { return (BRelTime)get(offDelay); }

  /**
   * Set the {@code offDelay} property.
   * @see #offDelay
   */
  public void setOffDelay(BRelTime v) { set(offDelay, v, null); }

  //endregion Property "offDelay"

  //region Property "onDelayActive"

  /**
   * Slot for the {@code onDelayActive} property.
   * @see #getOnDelayActive
   * @see #setOnDelayActive
   */
  public static final Property onDelayActive = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code onDelayActive} property.
   * @see #onDelayActive
   */
  public boolean getOnDelayActive() { return getBoolean(onDelayActive); }

  /**
   * Set the {@code onDelayActive} property.
   * @see #onDelayActive
   */
  public void setOnDelayActive(boolean v) { setBoolean(onDelayActive, v, null); }

  //endregion Property "onDelayActive"

  //region Property "offDelayActive"

  /**
   * Slot for the {@code offDelayActive} property.
   * @see #getOffDelayActive
   * @see #setOffDelayActive
   */
  public static final Property offDelayActive = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code offDelayActive} property.
   * @see #offDelayActive
   */
  public boolean getOffDelayActive() { return getBoolean(offDelayActive); }

  /**
   * Set the {@code offDelayActive} property.
   * @see #offDelayActive
   */
  public void setOffDelayActive(boolean v) { setBoolean(offDelayActive, v, null); }

  //endregion Property "offDelayActive"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, new BStatusBoolean(false), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusBoolean getOut() { return (BStatusBoolean)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusBoolean v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "outNot"

  /**
   * Slot for the {@code outNot} property.
   * @see #getOutNot
   * @see #setOutNot
   */
  public static final Property outNot = newProperty(Flags.TRANSIENT | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, new BStatusBoolean(true), null);

  /**
   * Get the {@code outNot} property.
   * @see #outNot
   */
  public BStatusBoolean getOutNot() { return (BStatusBoolean)get(outNot); }

  /**
   * Set the {@code outNot} property.
   * @see #outNot
   */
  public void setOutNot(BStatusBoolean v) { set(outNot, v, null); }

  //endregion Property "outNot"

  //region Action "onTimerExpired"

  /**
   * Slot for the {@code onTimerExpired} action.
   * @see #onTimerExpired()
   */
  public static final Action onTimerExpired = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code onTimerExpired} action.
   * @see #onTimerExpired
   */
  public void onTimerExpired() { invoke(onTimerExpired, null, null); }

  //endregion Action "onTimerExpired"

  //region Action "offTimerExpired"

  /**
   * Slot for the {@code offTimerExpired} action.
   * @see #offTimerExpired()
   */
  public static final Action offTimerExpired = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code offTimerExpired} action.
   * @see #offTimerExpired
   */
  public void offTimerExpired() { invoke(offTimerExpired, null, null); }

  //endregion Action "offTimerExpired"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanDelay.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BBooleanDelay()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    //getOut().setValue(getIn().getBoolean());
  }

  public void atSteadyState()
  {
      calculate();
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;
    if (p == in)
      calculate();
  }

  public void calculate()
  {
    if(!getIn().getStatus().isValid())
      return;
    boolean input = getIn().getValue();
    if(input && !lastInput)
    {
      lastInput = input;
      if(getOnDelay().getMillis() == 0L)
      {
        setOutputImmediately(true);
      }
      else
      {
        startOnTimer();
      }
    }
    else if(!input && lastInput)
    {
      lastInput = input;
      if(getOffDelay().getMillis() == 0L)
      {
        setOutputImmediately(false);
      }
      else
      {
        startOffTimer();
      }
    }
  }

  private void setOutput(boolean value)
  {
    getOut().setValue(value);
    getOutNot().setValue(!value);
  }

  public void doOnTimerExpired()
  {
    setOutput(true);
    setOnDelayActive(false);
  }

  public void doOffTimerExpired()
  {
    setOutput(false);
    setOffDelayActive(false);
  }

  private void setOutputImmediately(boolean value)
  {
    if (offTicket != null)
    {
      offTicket.cancel();
    }
    if (onTicket != null)
    {
      onTicket.cancel();
    }
    setOutput(value);
    setOnDelayActive(false);
    setOffDelayActive(false);
  }

  void startOnTimer()
  {
    if (offTicket != null)
    {
      offTicket.cancel();
    }
    if (onTicket != null)
    {
      onTicket.cancel();
    }
    onTicket = Clock.schedule(this, getOnDelay(), onTimerExpired, null);
    setOnDelayActive(true);
    setOffDelayActive(false);
  }

  void startOffTimer()
  {
    if (onTicket != null)
    {
      onTicket.cancel();
    }
    if (offTicket != null)
    {
      offTicket.cancel();
    }
    offTicket = Clock.schedule(this, getOffDelay(), offTimerExpired, null);
    setOffDelayActive(true);
    setOnDelayActive(false);
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
// BIBoolean interface
////////////////////////////////////////////////////////////////

  public boolean getBoolean() { return getOut().getValue(); }

  public final BFacets getBooleanFacets() { return getFacets(); }

  /**
   * Return the vaule as a enum.
   */
  public final BEnum getEnum() { return getOut().getEnum(); }

  /**
   * Return getFacets().
   */
  public final BFacets getEnumFacets() { return getFacets(); }



  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  boolean lastInput = false;
  Clock.Ticket onTicket;      // Used to manage the current timer
  Clock.Ticket offTicket;      // Used to manage the current timer
  
}
