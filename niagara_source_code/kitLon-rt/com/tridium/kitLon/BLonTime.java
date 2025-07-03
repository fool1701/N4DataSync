/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitLon;

import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.lonworks.enums.BLonSnvtType;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonFloat;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.device.NvDev;
import com.tridium.lonworks.local.BPseudoNV;
import com.tridium.lonworks.local.BPseudoNvContainer;

/**
 * BLonTime provides a linkable SnvtTimeStamp source with a programmable
 * update time.
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
  defaultValue = "BRelTime.make(10000)"
)
@NiagaraProperty(
  name = "timeStamp",
  type = "BPseudoNV",
  defaultValue = "new BPseudoNV(BLonSnvtType.SNVT_TIME_STAMP, BLonNvDirection.output)"
)
@NiagaraAction(
  name = "timerExpired",
  flags = Flags.HIDDEN
)
public class BLonTime
  extends BPseudoNvContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitLon.BLonTime(2066351709)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "updateTime"

  /**
   * Slot for the {@code updateTime} property.
   * @see #getUpdateTime
   * @see #setUpdateTime
   */
  public static final Property updateTime = newProperty(0, BRelTime.make(10000), null);

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

  //region Property "timeStamp"

  /**
   * Slot for the {@code timeStamp} property.
   * @see #getTimeStamp
   * @see #setTimeStamp
   */
  public static final Property timeStamp = newProperty(0, new BPseudoNV(BLonSnvtType.SNVT_TIME_STAMP, BLonNvDirection.output), null);

  /**
   * Get the {@code timeStamp} property.
   * @see #timeStamp
   */
  public BPseudoNV getTimeStamp() { return (BPseudoNV)get(timeStamp); }

  /**
   * Set the {@code timeStamp} property.
   * @see #timeStamp
   */
  public void setTimeStamp(BPseudoNV v) { set(timeStamp, v, null); }

  //endregion Property "timeStamp"

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
  public static final Type TYPE = Sys.loadType(BLonTime.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLonTime()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
    throws Exception
  {
    super.started();
    initTimer();
  }
  
  public void stopped()
    throws Exception
  {
    super.stopped();
    if (ticket != null) ticket.cancel();
  }

  private void initTimer()
  {
    if (ticket != null) ticket.cancel();
    if(getUpdateTime().getMillis()<=0L) return;
    ticket = Clock.schedulePeriodically(this, getUpdateTime(), timerExpired, null);
  }

  /**
   * Reinitialize timer if updateTime changes
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
   
    if (!isRunning()) return;

    if (p.equals(updateTime)) initTimer();
  }

  public void doTimerExpired()
  {
    BAbsTime curr = BAbsTime.now();
    
    // Update timeStamp
    BLonData ld = getTimeStamp().copyData();
    
    ld.set("year"   , BLonFloat.make(curr.getYear  ()) );
    ld.set("month"  , BLonFloat.make(curr.getMonth ().getOrdinal() + 1) );
    ld.set("day"    , BLonFloat.make(curr.getDay   ()) );
    ld.set("hour"   , BLonFloat.make(curr.getHour  ()) );
    ld.set("minute" , BLonFloat.make(curr.getMinute()) );
    ld.set("second" , BLonFloat.make(curr.getSecond()) );
    
    getTimeStamp().updateData(ld,false); 
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/nvClock.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Clock.Ticket ticket;      // Used to manage the current timer
}
