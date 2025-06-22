/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

import java.util.Vector;

import javax.baja.control.BControlPoint;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BInterstartDelayMaster
 *
 * This class is just a rough first hack to test the
 * extension mechanism.  It needs some polishing..
 * FIXX:  add a property display list of BComponents in
 * the wait list
 * FIXX:  this object relies on checkInterstartDelay()
 * from the delay ext to start timers and manage the
 * list.  It needs to be modified to execute periodically
 * If an object in the list were to have its ext
 * removed or no longer need to start, the
 * checkInterstartDelay() will never be made and the
 * list will not be re-evaluated.
 * FIXX:  Is a vector the best way to manage list of
 * waiting objects?  How to handle object deletion?
 * FIXX: dispose of schedules when done?
 *
 * @author Dan Giorgis on 17 Nov 00
 * @since Baja 1.0
 */
@NiagaraType
/*
 This Ord.  Used to link to slave BInterstartDelayExt
 It will automatically be initialized this Ord.
 */
@NiagaraProperty(
  name = "slaveLink",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 Default delay time to use per
 start request.  Only used
 if the object request start
 permission does not specify
 at delay time.
 */
@NiagaraProperty(
  name = "defaultDelay",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT"
)
/*
 true if the interstart delay is
 active
 */
@NiagaraProperty(
  name = "activeDelay",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 length of current delay
 */
@NiagaraProperty(
  name = "delayTime",
  type = "BRelTime",
  defaultValue = "BRelTime.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 time at which current delay
 started
 */
@NiagaraProperty(
  name = "delayStartTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 number of objects currently waiting
 to start
 */
@NiagaraProperty(
  name = "numObjectsWaiting",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 delayTimerExpired
 */
@NiagaraAction(
  name = "delayTimerExpired"
)
public class BInterstartDelayMaster
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BInterstartDelayMaster(754902277)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "slaveLink"

  /**
   * Slot for the {@code slaveLink} property.
   * This Ord.  Used to link to slave BInterstartDelayExt
   * It will automatically be initialized this Ord.
   * @see #getSlaveLink
   * @see #setSlaveLink
   */
  public static final Property slaveLink = newProperty(Flags.TRANSIENT | Flags.READONLY, BOrd.NULL, null);

  /**
   * Get the {@code slaveLink} property.
   * This Ord.  Used to link to slave BInterstartDelayExt
   * It will automatically be initialized this Ord.
   * @see #slaveLink
   */
  public BOrd getSlaveLink() { return (BOrd)get(slaveLink); }

  /**
   * Set the {@code slaveLink} property.
   * This Ord.  Used to link to slave BInterstartDelayExt
   * It will automatically be initialized this Ord.
   * @see #slaveLink
   */
  public void setSlaveLink(BOrd v) { set(slaveLink, v, null); }

  //endregion Property "slaveLink"

  //region Property "defaultDelay"

  /**
   * Slot for the {@code defaultDelay} property.
   * Default delay time to use per
   * start request.  Only used
   * if the object request start
   * permission does not specify
   * at delay time.
   * @see #getDefaultDelay
   * @see #setDefaultDelay
   */
  public static final Property defaultDelay = newProperty(0, BRelTime.DEFAULT, null);

  /**
   * Get the {@code defaultDelay} property.
   * Default delay time to use per
   * start request.  Only used
   * if the object request start
   * permission does not specify
   * at delay time.
   * @see #defaultDelay
   */
  public BRelTime getDefaultDelay() { return (BRelTime)get(defaultDelay); }

  /**
   * Set the {@code defaultDelay} property.
   * Default delay time to use per
   * start request.  Only used
   * if the object request start
   * permission does not specify
   * at delay time.
   * @see #defaultDelay
   */
  public void setDefaultDelay(BRelTime v) { set(defaultDelay, v, null); }

  //endregion Property "defaultDelay"

  //region Property "activeDelay"

  /**
   * Slot for the {@code activeDelay} property.
   * true if the interstart delay is
   * active
   * @see #getActiveDelay
   * @see #setActiveDelay
   */
  public static final Property activeDelay = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code activeDelay} property.
   * true if the interstart delay is
   * active
   * @see #activeDelay
   */
  public boolean getActiveDelay() { return getBoolean(activeDelay); }

  /**
   * Set the {@code activeDelay} property.
   * true if the interstart delay is
   * active
   * @see #activeDelay
   */
  public void setActiveDelay(boolean v) { setBoolean(activeDelay, v, null); }

  //endregion Property "activeDelay"

  //region Property "delayTime"

  /**
   * Slot for the {@code delayTime} property.
   * length of current delay
   * @see #getDelayTime
   * @see #setDelayTime
   */
  public static final Property delayTime = newProperty(Flags.TRANSIENT | Flags.READONLY, BRelTime.DEFAULT, null);

  /**
   * Get the {@code delayTime} property.
   * length of current delay
   * @see #delayTime
   */
  public BRelTime getDelayTime() { return (BRelTime)get(delayTime); }

  /**
   * Set the {@code delayTime} property.
   * length of current delay
   * @see #delayTime
   */
  public void setDelayTime(BRelTime v) { set(delayTime, v, null); }

  //endregion Property "delayTime"

  //region Property "delayStartTime"

  /**
   * Slot for the {@code delayStartTime} property.
   * time at which current delay
   * started
   * @see #getDelayStartTime
   * @see #setDelayStartTime
   */
  public static final Property delayStartTime = newProperty(Flags.TRANSIENT | Flags.READONLY, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code delayStartTime} property.
   * time at which current delay
   * started
   * @see #delayStartTime
   */
  public BAbsTime getDelayStartTime() { return (BAbsTime)get(delayStartTime); }

  /**
   * Set the {@code delayStartTime} property.
   * time at which current delay
   * started
   * @see #delayStartTime
   */
  public void setDelayStartTime(BAbsTime v) { set(delayStartTime, v, null); }

  //endregion Property "delayStartTime"

  //region Property "numObjectsWaiting"

  /**
   * Slot for the {@code numObjectsWaiting} property.
   * number of objects currently waiting
   * to start
   * @see #getNumObjectsWaiting
   * @see #setNumObjectsWaiting
   */
  public static final Property numObjectsWaiting = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code numObjectsWaiting} property.
   * number of objects currently waiting
   * to start
   * @see #numObjectsWaiting
   */
  public int getNumObjectsWaiting() { return getInt(numObjectsWaiting); }

  /**
   * Set the {@code numObjectsWaiting} property.
   * number of objects currently waiting
   * to start
   * @see #numObjectsWaiting
   */
  public void setNumObjectsWaiting(int v) { setInt(numObjectsWaiting, v, null); }

  //endregion Property "numObjectsWaiting"

  //region Action "delayTimerExpired"

  /**
   * Slot for the {@code delayTimerExpired} action.
   * delayTimerExpired
   * @see #delayTimerExpired()
   */
  public static final Action delayTimerExpired = newAction(0, null);

  /**
   * Invoke the {@code delayTimerExpired} action.
   * delayTimerExpired
   * @see #delayTimerExpired
   */
  public void delayTimerExpired() { invoke(delayTimerExpired, null, null); }

  //endregion Action "delayTimerExpired"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInterstartDelayMaster.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public void started()
  {
    setSlaveLink(getAbsoluteOrd());
  }

  long activeDelayEndTime = 0;
  int activeDelayTime = 0;

  Vector<BComponent> waitList = new Vector<>();

  /**
   *  Returns true if it's OK to start.
 */
  //  FIXX - synchronization???
  public boolean checkInterstartDelay(BComponent output, BRelTime delayTime)
  {

//System.out.println("BInterstartDelayMaster::checkInterstartDelay "  + getActiveDelay());
    if (!getActiveDelay() )
    {
      //  Nothing starting right now, so go ahead


      //  Check for unspecified delay time
      long dtime = delayTime.getMillis();
      if (dtime == 0)
        dtime = getDefaultDelay().getMillis();

      //  If delay time is still 0, just return
      if (dtime == 0)
      {
        return true;
      }

      activeDelayEndTime = Clock.ticks() + dtime;

      setDelayStartTime(Clock.time());
      setActiveDelay(true);
      setDelayTime(BRelTime.make(dtime));
//FIXX      setDelayObject(output);

//System.out.println("interstart delay begin: " + getDelayStartTime() + " requestor " + output.getName());

      // Schedule ourselves to run when the delay expires
      Clock.schedule(this, BRelTime.make(dtime), delayTimerExpired, null);

      return true;
    }
    else
    {
      //  Add object to pending queue
      if (!waitList.contains(output))
      {
        waitList.addElement(output);
        setNumObjectsWaiting(waitList.size());
        //System.out.println("active delay, cannot start object, adding to queue...");
      }
      //else
        //System.out.println("already in queue...");

      return false;
    }
  }


  public void doDelayTimerExpired()
  {
    //System.out.println("*************** doDelayTimeExpired");

    setDelayStartTime(BAbsTime.DEFAULT);
    setActiveDelay(false);

    // Are other objects waiting to start?
    if (!waitList.isEmpty())
    {
      //  Get next object in wait list
      BControlPoint next = (BControlPoint)waitList.elementAt(0);
      waitList.removeElementAt(0);

      //System.out.println("~~~~~~ Updating: " + next.getName());

      next.execute();
    }

    setNumObjectsWaiting(waitList.size());
  }

}
