/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitLon;

import javax.baja.driver.point.BTuningPolicy;
import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.lonworks.enums.BLonOccupancyEnum;
import javax.baja.lonworks.enums.BLonSnvtType;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonEnum;
import javax.baja.lonworks.londata.BLonFloat;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;
import javax.baja.units.UnitDatabase;

import com.tridium.lonworks.device.NvDev;
import com.tridium.lonworks.local.BPseudoNV;
import com.tridium.lonworks.local.BPseudoNvContainer;
import com.tridium.lonworks.util.NmUtil;

/**
 * BLonTodEvent takes input from a Niagara BooleanSchedule to update a SnvtTodEvent.
 *
 * @author    Robert Adams
 * @creation  27 April 2006
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "updateTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(60000)"
)
@NiagaraProperty(
  name = "currentState",
  type = "BStatusValue",
  defaultValue = "new BStatusBoolean(false)"
)
@NiagaraProperty(
  name = "nextState",
  type = "BStatusValue",
  defaultValue = "new BStatusBoolean(false)"
)
@NiagaraProperty(
  name = "nextTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT"
)
@NiagaraProperty(
  name = "maxTimeToNextState",
  type = "int",
  defaultValue = "65535",
  facets = @Facet("BFacets.make(BFacets.MIN,BInteger.make(0),BFacets.MAX,BInteger.make(65535),BFacets.UNITS,UnitDatabase.getUnit(\"minute\"))")
)
@NiagaraProperty(
  name = "todEvent",
  type = "BPseudoNV",
  defaultValue = "new BPseudoNV(BLonSnvtType.SNVT_TOD_EVENT, BLonNvDirection.output)"
)
@NiagaraAction(
  name = "timerExpired",
  flags = Flags.HIDDEN
)
public class BLonTodEvent
  extends BPseudoNvContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitLon.BLonTodEvent(1077837076)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "updateTime"

  /**
   * Slot for the {@code updateTime} property.
   * @see #getUpdateTime
   * @see #setUpdateTime
   */
  public static final Property updateTime = newProperty(0, BRelTime.make(60000), null);

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

  //region Property "currentState"

  /**
   * Slot for the {@code currentState} property.
   * @see #getCurrentState
   * @see #setCurrentState
   */
  public static final Property currentState = newProperty(0, new BStatusBoolean(false), null);

  /**
   * Get the {@code currentState} property.
   * @see #currentState
   */
  public BStatusValue getCurrentState() { return (BStatusValue)get(currentState); }

  /**
   * Set the {@code currentState} property.
   * @see #currentState
   */
  public void setCurrentState(BStatusValue v) { set(currentState, v, null); }

  //endregion Property "currentState"

  //region Property "nextState"

  /**
   * Slot for the {@code nextState} property.
   * @see #getNextState
   * @see #setNextState
   */
  public static final Property nextState = newProperty(0, new BStatusBoolean(false), null);

  /**
   * Get the {@code nextState} property.
   * @see #nextState
   */
  public BStatusValue getNextState() { return (BStatusValue)get(nextState); }

  /**
   * Set the {@code nextState} property.
   * @see #nextState
   */
  public void setNextState(BStatusValue v) { set(nextState, v, null); }

  //endregion Property "nextState"

  //region Property "nextTime"

  /**
   * Slot for the {@code nextTime} property.
   * @see #getNextTime
   * @see #setNextTime
   */
  public static final Property nextTime = newProperty(0, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code nextTime} property.
   * @see #nextTime
   */
  public BAbsTime getNextTime() { return (BAbsTime)get(nextTime); }

  /**
   * Set the {@code nextTime} property.
   * @see #nextTime
   */
  public void setNextTime(BAbsTime v) { set(nextTime, v, null); }

  //endregion Property "nextTime"

  //region Property "maxTimeToNextState"

  /**
   * Slot for the {@code maxTimeToNextState} property.
   * @see #getMaxTimeToNextState
   * @see #setMaxTimeToNextState
   */
  public static final Property maxTimeToNextState = newProperty(0, 65535, BFacets.make(BFacets.MIN,BInteger.make(0),BFacets.MAX,BInteger.make(65535),BFacets.UNITS,UnitDatabase.getUnit("minute")));

  /**
   * Get the {@code maxTimeToNextState} property.
   * @see #maxTimeToNextState
   */
  public int getMaxTimeToNextState() { return getInt(maxTimeToNextState); }

  /**
   * Set the {@code maxTimeToNextState} property.
   * @see #maxTimeToNextState
   */
  public void setMaxTimeToNextState(int v) { setInt(maxTimeToNextState, v, null); }

  //endregion Property "maxTimeToNextState"

  //region Property "todEvent"

  /**
   * Slot for the {@code todEvent} property.
   * @see #getTodEvent
   * @see #setTodEvent
   */
  public static final Property todEvent = newProperty(0, new BPseudoNV(BLonSnvtType.SNVT_TOD_EVENT, BLonNvDirection.output), null);

  /**
   * Get the {@code todEvent} property.
   * @see #todEvent
   */
  public BPseudoNV getTodEvent() { return (BPseudoNV)get(todEvent); }

  /**
   * Set the {@code todEvent} property.
   * @see #todEvent
   */
  public void setTodEvent(BPseudoNV v) { set(todEvent, v, null); }

  //endregion Property "todEvent"

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
  public static final Type TYPE = Sys.loadType(BLonTodEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLonTodEvent()
  {
  }

  public void started()
    throws Exception
  {
    super.started();
    if(!isOk()) return;
    initTimer();
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
    if (ticket != null) ticket.cancel();
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning() || !isOk()) return;

    if ((p == currentState) || (p == nextState) || (p == nextTime) || (p == maxTimeToNextState))
    {
      update();
    }
    else if ((p == updateTime))
      initTimer();
  }

  public void atSteadyState()
    throws Exception
  {
    super.atSteadyState();
    if(!isOk()) return;
    update();
  }

  private void initTimer()
  {
    if (ticket != null) ticket.cancel();
    if (getUpdateTime().getMillis() <= 0L) return;
    ticket = Clock.schedulePeriodically(this, getUpdateTime(), timerExpired, null);
  }

  public void doTimerExpired()
  {
    if(okayToUpdate()) update();
  }

  public boolean okayToUpdate()
  {
    // If expected update from schedule is within 20 seconds don't do an update.
    // This is attempt to prevent nv update while change is in progress
    long delta = Math.abs(getNextTime().getMillis() - Clock.millis());
    return delta>20000;
  }

  private synchronized void update()
  {
    if(posted) return;
    posted = true;

    getLonNetwork().postAsync(
      new Runnable()
      {
        @Override
        public void run() { doUpdate();  }
      });
  }
  private boolean posted = false;

  private void doUpdate()
  {
    NmUtil.wait(200); // give some time for schedule to set all changes
  // System.out.println("update " + getCurrentState() + " " + getNextState() + " " + nextTimeMin());
    // Update todEvent
    BLonData ld = getTodEvent().copyData();

    ld.set("currentState"    , BLonEnum.make(makeLonOccEnum(getCurrentState()) ));
    ld.set("nextState"       , BLonEnum.make(makeLonOccEnum(getNextState())    ));
    ld.set("timeToNextState" , BLonFloat.make(nextTimeMin())  );
    
    getTodEvent().updateData(ld,false);
    synchronized(this)
    {
      posted = false;
    }
  }

  BLonOccupancyEnum makeLonOccEnum(BStatusValue v)
  {
    BStatusBoolean bool = (BStatusBoolean)v;
    if (bool.getValue())
      return BLonOccupancyEnum.occupied;  
    else
      return BLonOccupancyEnum.unoccupied;
  } 
  
  // Get time till next event in minutes
  private float nextTimeMin()
  {
    long eventTime = getNextTime().getMillis();
    float nxtTime = (((eventTime - Clock.millis()) / 1000) / 60);  
    
    // limit to the configured maxTime
    if(nxtTime > getMaxTimeToNextState()) nxtTime = getMaxTimeToNextState();
    
    // Don't let time be negative PacMan 19181
    if(nxtTime < 0) nxtTime = 0;
    
    return nxtTime;
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/nvClock.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Clock.Ticket ticket;      
}
