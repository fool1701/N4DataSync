/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.control.BBooleanPoint;
import javax.baja.log.Log;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BMultiVibrator generates a repeating pulse train
 *
 * @author    Bill Smith
 * @creation  01 Sept 2004
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Period is the length of time it takes to
 output one complete cycle of the pulse.
 */
@NiagaraProperty(
  name = "period",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)",
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE, BFacets.MIN, BRelTime.make(200))")
)
/*
 Percent of period that it is high.
 */
@NiagaraProperty(
  name = "dutyCycle",
  type = "int",
  defaultValue = "50",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(0), BFacets.MAX, BInteger.make(100))")
)
public class BMultiVibrator
  extends BBooleanPoint
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BMultiVibrator(1349613503)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "period"

  /**
   * Slot for the {@code period} property.
   * Period is the length of time it takes to
   * output one complete cycle of the pulse.
   * @see #getPeriod
   * @see #setPeriod
   */
  public static final Property period = newProperty(0, BRelTime.make(1000), BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE, BFacets.MIN, BRelTime.make(200)));

  /**
   * Get the {@code period} property.
   * Period is the length of time it takes to
   * output one complete cycle of the pulse.
   * @see #period
   */
  public BRelTime getPeriod() { return (BRelTime)get(period); }

  /**
   * Set the {@code period} property.
   * Period is the length of time it takes to
   * output one complete cycle of the pulse.
   * @see #period
   */
  public void setPeriod(BRelTime v) { set(period, v, null); }

  //endregion Property "period"

  //region Property "dutyCycle"

  /**
   * Slot for the {@code dutyCycle} property.
   * Percent of period that it is high.
   * @see #getDutyCycle
   * @see #setDutyCycle
   */
  public static final Property dutyCycle = newProperty(0, 50, BFacets.make(BFacets.MIN, BInteger.make(0), BFacets.MAX, BInteger.make(100)));

  /**
   * Get the {@code dutyCycle} property.
   * Percent of period that it is high.
   * @see #dutyCycle
   */
  public int getDutyCycle() { return getInt(dutyCycle); }

  /**
   * Set the {@code dutyCycle} property.
   * Percent of period that it is high.
   * @see #dutyCycle
   */
  public void setDutyCycle(int v) { setInt(dutyCycle, v, null); }

  //endregion Property "dutyCycle"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMultiVibrator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////

  @Override
  public void started()
    throws Exception
  {
    super.started();
    initTimer();
  }

  private synchronized void initTimer()
  {
    int cycle = getDutyCycle();
    if (cycle <= 0)
    {
      cycle = 0;
    }
    else if (cycle >= 100)
    {
      cycle = 100;
    }

    highTime = BRelTime.make(getPeriod().getMillis() * cycle / 100);
    lowTime = BRelTime.make(getPeriod().getMillis() * (100 - cycle) / 100);

    if (ticket != null)
    {
      ticket.cancel();
    }

    if (getEnabled())
    {
      ticket = Clock.schedule(this, lowTime, execute, null);
    }
  }

  @Override
  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    if (prop == period || prop == dutyCycle || prop == enabled)
    {
      if (isRunning())
      {
        initTimer();
      }
    }
  }

  @Override
  public synchronized void onExecute(BStatusValue o, Context cx)
  {
    if (getEnabled())
    {
      if (highTime.getMillis() <= 0)
      {
        high = false;
      }
      else if (lowTime.getMillis() <= 0)
      {
        high = true;
      }
      else
      {
        if (high)
        {
          high = false;
          if (ticket != null)
          {
            ticket.cancel();
          }
          if (isRunning())
          {
            ticket = Clock.schedule(this, lowTime, execute, null);
          }
        }
        else
        {
          high = true;
          if (ticket != null)
          {
            ticket.cancel();
          }
          if (isRunning())
          {
            ticket = Clock.schedule(this, highTime, execute, null);
          }
        }
      }

      // update output
      BStatusBoolean out = (BStatusBoolean)o;
      out.setValue(high);
    }
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/sine.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Clock.Ticket ticket = null;
  static final Log log = Log.getLog("kitControl");
  private long start;
  private BRelTime highTime;
  private BRelTime lowTime;
  private boolean high = false;
}
