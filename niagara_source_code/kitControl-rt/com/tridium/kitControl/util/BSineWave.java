/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BSineWave generates a sine wave.
 *
 * @author    Brian Frank
 * @creation  18 Oct 00
 * @version   $Revision: 46$ $Date: 5/11/2004 11:30:56 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Enabled will set the output on or off.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Period is the length of time it takes to
 output one complete cycle of the sine wave.
 */
@NiagaraProperty(
  name = "period",
  type = "BRelTime",
  defaultValue = "BRelTime.make(30000)"
)
/*
 Amplitude is height of the sine wave from
 its lowest to highest point.
 */
@NiagaraProperty(
  name = "amplitude",
  type = "double",
  defaultValue = "50"
)
/*
 Offset is the positive or negative distance
 from zero that the wave's amplitude is
 centered on.
 */
@NiagaraProperty(
  name = "offset",
  type = "double",
  defaultValue = "50"
)
/*
 Update interval specifies how long between
 output changes.  A smaller value results in
 a more accurate sine wave with more changes
 per second, while a larger value results in
 less precision but with less overhead.
 */
@NiagaraProperty(
  name = "updateInterval",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)"
)
public class BSineWave
  extends BNumericPoint
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BSineWave(746144663)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * Enabled will set the output on or off.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * Enabled will set the output on or off.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * Enabled will set the output on or off.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "period"

  /**
   * Slot for the {@code period} property.
   * Period is the length of time it takes to
   * output one complete cycle of the sine wave.
   * @see #getPeriod
   * @see #setPeriod
   */
  public static final Property period = newProperty(0, BRelTime.make(30000), null);

  /**
   * Get the {@code period} property.
   * Period is the length of time it takes to
   * output one complete cycle of the sine wave.
   * @see #period
   */
  public BRelTime getPeriod() { return (BRelTime)get(period); }

  /**
   * Set the {@code period} property.
   * Period is the length of time it takes to
   * output one complete cycle of the sine wave.
   * @see #period
   */
  public void setPeriod(BRelTime v) { set(period, v, null); }

  //endregion Property "period"

  //region Property "amplitude"

  /**
   * Slot for the {@code amplitude} property.
   * Amplitude is height of the sine wave from
   * its lowest to highest point.
   * @see #getAmplitude
   * @see #setAmplitude
   */
  public static final Property amplitude = newProperty(0, 50, null);

  /**
   * Get the {@code amplitude} property.
   * Amplitude is height of the sine wave from
   * its lowest to highest point.
   * @see #amplitude
   */
  public double getAmplitude() { return getDouble(amplitude); }

  /**
   * Set the {@code amplitude} property.
   * Amplitude is height of the sine wave from
   * its lowest to highest point.
   * @see #amplitude
   */
  public void setAmplitude(double v) { setDouble(amplitude, v, null); }

  //endregion Property "amplitude"

  //region Property "offset"

  /**
   * Slot for the {@code offset} property.
   * Offset is the positive or negative distance
   * from zero that the wave's amplitude is
   * centered on.
   * @see #getOffset
   * @see #setOffset
   */
  public static final Property offset = newProperty(0, 50, null);

  /**
   * Get the {@code offset} property.
   * Offset is the positive or negative distance
   * from zero that the wave's amplitude is
   * centered on.
   * @see #offset
   */
  public double getOffset() { return getDouble(offset); }

  /**
   * Set the {@code offset} property.
   * Offset is the positive or negative distance
   * from zero that the wave's amplitude is
   * centered on.
   * @see #offset
   */
  public void setOffset(double v) { setDouble(offset, v, null); }

  //endregion Property "offset"

  //region Property "updateInterval"

  /**
   * Slot for the {@code updateInterval} property.
   * Update interval specifies how long between
   * output changes.  A smaller value results in
   * a more accurate sine wave with more changes
   * per second, while a larger value results in
   * less precision but with less overhead.
   * @see #getUpdateInterval
   * @see #setUpdateInterval
   */
  public static final Property updateInterval = newProperty(0, BRelTime.make(1000), null);

  /**
   * Get the {@code updateInterval} property.
   * Update interval specifies how long between
   * output changes.  A smaller value results in
   * a more accurate sine wave with more changes
   * per second, while a larger value results in
   * less precision but with less overhead.
   * @see #updateInterval
   */
  public BRelTime getUpdateInterval() { return (BRelTime)get(updateInterval); }

  /**
   * Set the {@code updateInterval} property.
   * Update interval specifies how long between
   * output changes.  A smaller value results in
   * a more accurate sine wave with more changes
   * per second, while a larger value results in
   * less precision but with less overhead.
   * @see #updateInterval
   */
  public void setUpdateInterval(BRelTime v) { set(updateInterval, v, null); }

  //endregion Property "updateInterval"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSineWave.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////
  
  public void started()
    throws Exception
  {            
    super.started();
    execute();
    initTimer();
  }
  
  public void stopped()
    throws Exception
  {
    if (ticket != null) ticket.cancel();
    super.stopped();
  }
  
  private void initTimer()
  {
    if (ticket != null) ticket.cancel();
    //BAbsTime now = Clock.time();
    start = Clock.ticks();
    if (getEnabled()) ticket = Clock.schedulePeriodically(this, getUpdateInterval(), execute, null);
  }
  
  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    if (prop == updateInterval || prop == enabled) 
    {
      if(getUpdateInterval().getMillis() < 1000)
        setUpdateInterval(BRelTime.make(1000));
      if(isRunning()) initTimer();
    }
  }
  
  public void onExecute(BStatusValue o, Context cx)
  {
    if (getEnabled())
    {
      // Set the Status bit on the output
      o.setStatus(BStatus.makeDisabled(o.getStatus(), false));

      // get the millis into the current period
      long period = getPeriod().getMillis();
      if (period == 0) period = 1000;
      long runtime = Clock.ticks() - start;
      long millisIntoPeriod = runtime % period;
      double periodPercent = (double)millisIntoPeriod / (double)period;
  
      // get the angle per millisecond into one period
      double amplitude = getAmplitude();
      double offset    = getOffset();
      double ang       = 2.0 * Math.PI * periodPercent;
      double value     = Math.sin(ang)*amplitude + offset;
      
/*
long ticks = Clock.ticks();
long delta = ticks - last;
last = ticks;
System.out.println("  SineWave [" + this + "] " + delta + " (" + BAbsTime.make() + ") " + value);
*/
  
  
      // update output
      BStatusNumeric out = (BStatusNumeric)o;
      out.setValue(value);
    }
    else
    {
      o.setStatus(BStatus.makeDisabled(o.getStatus(), true));
    }
  }
/*
long last;
*/

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/sine.png");
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private long start;
  private Clock.Ticket ticket;
  
}
