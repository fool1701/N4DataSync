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
 * BRandom generates a random numer.
 *
 *  out = random() * multiplier + offset
 *    where random() is a random number >= 0.0 and < 1.0
 *
 * @author    Andy Saunders
 * @creation  18 Oct 00
 * @version   $Revision: 46$ $Date: 5/11/2004 11:30:56 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Multiplier is multiplied by the random number generated
 which is >= 0.0 but < 1.0
 */
@NiagaraProperty(
  name = "multiplier",
  type = "double",
  defaultValue = "1.0"
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
 output changes.
 */
@NiagaraProperty(
  name = "updateInterval",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)"
)
public class BRandom
  extends BNumericPoint
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BRandom(632096318)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "multiplier"

  /**
   * Slot for the {@code multiplier} property.
   * Multiplier is multiplied by the random number generated
   * which is >= 0.0 but < 1.0
   * @see #getMultiplier
   * @see #setMultiplier
   */
  public static final Property multiplier = newProperty(0, 1.0, null);

  /**
   * Get the {@code multiplier} property.
   * Multiplier is multiplied by the random number generated
   * which is >= 0.0 but < 1.0
   * @see #multiplier
   */
  public double getMultiplier() { return getDouble(multiplier); }

  /**
   * Set the {@code multiplier} property.
   * Multiplier is multiplied by the random number generated
   * which is >= 0.0 but < 1.0
   * @see #multiplier
   */
  public void setMultiplier(double v) { setDouble(multiplier, v, null); }

  //endregion Property "multiplier"

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
   * output changes.
   * @see #getUpdateInterval
   * @see #setUpdateInterval
   */
  public static final Property updateInterval = newProperty(0, BRelTime.make(1000), null);

  /**
   * Get the {@code updateInterval} property.
   * Update interval specifies how long between
   * output changes.
   * @see #updateInterval
   */
  public BRelTime getUpdateInterval() { return (BRelTime)get(updateInterval); }

  /**
   * Set the {@code updateInterval} property.
   * Update interval specifies how long between
   * output changes.
   * @see #updateInterval
   */
  public void setUpdateInterval(BRelTime v) { set(updateInterval, v, null); }

  //endregion Property "updateInterval"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRandom.class);

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
    ticket = Clock.schedulePeriodically(this, getUpdateInterval(), execute, null);
  }
  
  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    if (prop == updateInterval) 
    {
      if(getUpdateInterval().getMillis() < 1000)
        setUpdateInterval(BRelTime.make(1000));
      if(isRunning()) initTimer();
    }
  }
  
  public void onExecute(BStatusValue o, Context cx)
  {
    // get the angle per millisecond into one period
    double multiplier = getMultiplier();
    double offset    = getOffset();
    double value     = Math.random()*multiplier + offset;
    
    // update output
    BStatusNumeric out = (BStatusNumeric)o;
    out.setValue(value);
  }

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
