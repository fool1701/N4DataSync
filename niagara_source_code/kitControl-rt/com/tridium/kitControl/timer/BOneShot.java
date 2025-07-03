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
 * BOneShot is a component that provides a edge triggered one-shot.
 *    A one-shot converts a boolean input signal to a timed boolean pulse.
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
  name = "time",
  type = "BRelTime",
  defaultValue = "BRelTime.make(500)",
  facets = {
    @Facet(name = "BFacets.MIN", value = "BRelTime.make(1)"),
    @Facet(name = "BFacets.SHOW_MILLISECONDS", value = "true")
  }
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
  name = "fire"
)
@NiagaraAction(
  name = "timerExpired",
  flags = Flags.HIDDEN
)
public class BOneShot
  extends BComponent
  implements BIStatus, BIBoolean
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.timer.BOneShot(4181276109)1.0$ @*/
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

  //region Property "time"

  /**
   * Slot for the {@code time} property.
   * @see #getTime
   * @see #setTime
   */
  public static final Property time = newProperty(0, BRelTime.make(500), BFacets.make(BFacets.make(BFacets.MIN, BRelTime.make(1)), BFacets.make(BFacets.SHOW_MILLISECONDS, true)));

  /**
   * Get the {@code time} property.
   * @see #time
   */
  public BRelTime getTime() { return (BRelTime)get(time); }

  /**
   * Set the {@code time} property.
   * @see #time
   */
  public void setTime(BRelTime v) { set(time, v, null); }

  //endregion Property "time"

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

  //region Action "fire"

  /**
   * Slot for the {@code fire} action.
   * @see #fire()
   */
  public static final Action fire = newAction(0, null);

  /**
   * Invoke the {@code fire} action.
   * @see #fire
   */
  public void fire() { invoke(fire, null, null); }

  //endregion Action "fire"

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
  public static final Type TYPE = Sys.loadType(BOneShot.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BOneShot()
  {
  }

  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    //getOut().setValue(getIn().getBoolean());
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == in)
    {
      if(!getIn().getStatus().isValid())
        return;
      boolean input = getIn().getValue();
      if(input && !lastInput)
      {
        lastInput = input;
        getOut().setValue(true);
        getOutNot().setValue(false);
        updateTimer();
      }
      else
        lastInput = input;
    }
  }

  public void doFire()
  {
    getOut().setValue(true);
    getOutNot().setValue(false);
    updateTimer();
  }

  public void doTimerExpired()
  {
    getOut().setValue(false);
    getOutNot().setValue(true);
  }

  void updateTimer()
  {
    if (ticket != null) ticket.cancel();
    ticket = Clock.schedule(this, getTime(), timerExpired, null);
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

  boolean lastInput;
  Clock.Ticket ticket;      // Used to manage the current timer

}
