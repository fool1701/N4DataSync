/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

import java.io.*;

import javax.baja.control.*;
import javax.baja.control.enums.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BInterstartDelayControl - No other object using the same delay master 
 * can start for delay time after this object starts
 * If delay is not defined, the default delay on the master will be used.     
 *
 * @author    Andy Saunders
 * @creation   17 Nov 04
 * @version   $Revision: 23$ $Date: 1/20/2004 9:32:24 AM$
 * @since     Baja 1.0
 */

@NiagaraType
/*
 Delay time for use with this object.
 No other object using the same
 delay master can start for delay
 time after this object starts
 If delay is not defined, the
 default delay on the master will
 be used.
 */
@NiagaraProperty(
  name = "delay",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT"
)
/*
 Reference to the master interstart
 delay object to use
 */
@NiagaraProperty(
  name = "master",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 true if this object wants to start, but
 cannot
 */
@NiagaraProperty(
  name = "startPending",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
public class BInterstartDelayControl
  extends BBooleanWritable
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BInterstartDelayControl(2712421193)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "delay"

  /**
   * Slot for the {@code delay} property.
   * Delay time for use with this object.
   * No other object using the same
   * delay master can start for delay
   * time after this object starts
   * If delay is not defined, the
   * default delay on the master will
   * be used.
   * @see #getDelay
   * @see #setDelay
   */
  public static final Property delay = newProperty(0, BRelTime.DEFAULT, null);

  /**
   * Get the {@code delay} property.
   * Delay time for use with this object.
   * No other object using the same
   * delay master can start for delay
   * time after this object starts
   * If delay is not defined, the
   * default delay on the master will
   * be used.
   * @see #delay
   */
  public BRelTime getDelay() { return (BRelTime)get(delay); }

  /**
   * Set the {@code delay} property.
   * Delay time for use with this object.
   * No other object using the same
   * delay master can start for delay
   * time after this object starts
   * If delay is not defined, the
   * default delay on the master will
   * be used.
   * @see #delay
   */
  public void setDelay(BRelTime v) { set(delay, v, null); }

  //endregion Property "delay"

  //region Property "master"

  /**
   * Slot for the {@code master} property.
   * Reference to the master interstart
   * delay object to use
   * @see #getMaster
   * @see #setMaster
   */
  public static final Property master = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code master} property.
   * Reference to the master interstart
   * delay object to use
   * @see #master
   */
  public BOrd getMaster() { return (BOrd)get(master); }

  /**
   * Set the {@code master} property.
   * Reference to the master interstart
   * delay object to use
   * @see #master
   */
  public void setMaster(BOrd v) { set(master, v, null); }

  //endregion Property "master"

  //region Property "startPending"

  /**
   * Slot for the {@code startPending} property.
   * true if this object wants to start, but
   * cannot
   * @see #getStartPending
   * @see #setStartPending
   */
  public static final Property startPending = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code startPending} property.
   * true if this object wants to start, but
   * cannot
   * @see #startPending
   */
  public boolean getStartPending() { return getBoolean(startPending); }

  /**
   * Set the {@code startPending} property.
   * true if this object wants to start, but
   * cannot
   * @see #startPending
   */
  public void setStartPending(boolean v) { setBoolean(startPending, v, null); }

  //endregion Property "startPending"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInterstartDelayControl.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////

  /** 
   * Called when either me or my parent control 
   * point is updated.
   */ 
  public void onExecute(BStatusValue o, Context cx)
  {
    super.onExecute(o, cx);

    BBooleanPoint boolPt = this;
    BStatusBoolean out = (BStatusBoolean)o;

    //  FIXX - handle polarity???

    //  Don't care about transitions to inactive state
    if (out.getValue() == false)    
    {
      wasActive = false;
      return;
    }    

    //  If command level is manual life safety or
    //  manual, it's not subject to interstart delay  
    //  FIXX - desired behavior?
    if (boolPt instanceof BBooleanWritable)
    {
      BPriorityLevel active = getActiveLevel(out);
      if ((active == BPriorityLevel.level_1) ||
          (active == BPriorityLevel.level_8))
        return;
    }

    //  If we were already active, no need to check for
    //  delay
    if (!wasActive)
    {        
      //  Is it OK to start?
      if (checkInterstartDelay(boolPt))
        wasActive = true;
      else
      {
        out.setValue(false);
        out.setStatus(BStatus.make(out.getStatus(), "startPending", BBoolean.make(true)));
        wasActive = false;
      }
    }
    //System.out.println(" BInterstartDelayControl returning working varilabe: " + o);
  }

  BPriorityLevel getActiveLevel(BStatusValue value)
  {
    return BPriorityLevel.make( value.getStatus().geti(BStatus.ACTIVE_LEVEL, BPriorityLevel.FALLBACK) );
  }


  /**
   * Called when a change to active state is detected  
   * Returns true if COS should proceed, false
   * another object is already starting.
   */
  private boolean checkInterstartDelay(BBooleanPoint bootPt)
  {
    BOrd ord = getMaster();
    BInterstartDelayMaster delayMaster = null;

    try
    {
      delayMaster = (BInterstartDelayMaster)ord.resolve(this).get();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return false;
    }

    boolean okToStart = delayMaster.checkInterstartDelay((BComponent)bootPt, getDelay());

    if (okToStart)
      setStartPending(false);
    else
      setStartPending(true);
    
    return okToStart;      

  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private long interstartDelayStartTime;
  private boolean wasActive = false;

}
