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
 * BCurrentTime is a component exposes the current time as a BAbsTime.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.SHOW_DATE, BBoolean.make(true), BFacets.SHOW_TIME, BBoolean.make(true), BFacets.SHOW_SECONDS, BBoolean.make(true))"
)
@NiagaraProperty(
  name = "updateTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(500)",
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, true)")
)
@NiagaraProperty(
  name = "out",
  type = "BAbsTime",
  defaultValue = "BAbsTime.make()",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
@NiagaraAction(
  name = "timerExpired",
  flags = Flags.HIDDEN
)
public class BCurrentTime
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.timer.BCurrentTime(3798548786)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.make(BFacets.SHOW_DATE, BBoolean.make(true), BFacets.SHOW_TIME, BBoolean.make(true), BFacets.SHOW_SECONDS, BBoolean.make(true)), null);

  /**
   * Get the {@code facets} property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "updateTime"

  /**
   * Slot for the {@code updateTime} property.
   * @see #getUpdateTime
   * @see #setUpdateTime
   */
  public static final Property updateTime = newProperty(0, BRelTime.make(500), BFacets.make(BFacets.SHOW_MILLISECONDS, true));

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

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, BAbsTime.make(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BAbsTime getOut() { return (BAbsTime)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BAbsTime v) { set(out, v, null); }

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
  public static final Type TYPE = Sys.loadType(BCurrentTime.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BCurrentTime()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    initTimer();
  }

  protected void initTimer()
  {
    if (ticket != null) ticket.cancel();
    ticket = Clock.schedulePeriodically(this, getUpdateTime(), timerExpired, null);
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p.equals(updateTime))
    {
      initTimer();
    }
    else
    {
      super.changed(p, cx);
    }
  }

  public void doTimerExpired()
  {
    setOut(BAbsTime.now());
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


  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/trigger.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  boolean lastInput;
  Clock.Ticket ticket;      // Used to manage the current timer
  
}
