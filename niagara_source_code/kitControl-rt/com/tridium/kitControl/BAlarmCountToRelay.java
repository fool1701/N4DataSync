/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.kitControl.enums.*;


/**
 * BAlarmCountToRelay
 *
 * @author    JJ Frankovich
 * @creation  12 May 2006
 * @version   $Revision$ $Date$
 * @since     Niagara 3.2
*/
@NiagaraType
@NiagaraProperty(
  name = "alarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.FAN_IN
)
@NiagaraProperty(
  name = "relayOut",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "timer",
  type = "BRelTime",
  defaultValue = "BRelTime.makeMinutes(2)"
)
/*
 switching this value automates switching the links if not set to anyCount
 */
@NiagaraProperty(
  name = "alarmCountType",
  type = "BAlarmCountEnum",
  defaultValue = "BAlarmCountEnum.anyCount"
)
@NiagaraAction(
  name = "relayOutStop",
  flags = Flags.HIDDEN
)
public class BAlarmCountToRelay
  extends BComponent
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BAlarmCountToRelay(2523700363)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmCount"

  /**
   * Slot for the {@code alarmCount} property.
   * @see #getAlarmCount
   * @see #setAlarmCount
   */
  public static final Property alarmCount = newProperty(Flags.TRANSIENT | Flags.FAN_IN, 0, null);

  /**
   * Get the {@code alarmCount} property.
   * @see #alarmCount
   */
  public int getAlarmCount() { return getInt(alarmCount); }

  /**
   * Set the {@code alarmCount} property.
   * @see #alarmCount
   */
  public void setAlarmCount(int v) { setInt(alarmCount, v, null); }

  //endregion Property "alarmCount"

  //region Property "relayOut"

  /**
   * Slot for the {@code relayOut} property.
   * @see #getRelayOut
   * @see #setRelayOut
   */
  public static final Property relayOut = newProperty(Flags.TRANSIENT | Flags.READONLY, new BStatusBoolean(), null);

  /**
   * Get the {@code relayOut} property.
   * @see #relayOut
   */
  public BStatusBoolean getRelayOut() { return (BStatusBoolean)get(relayOut); }

  /**
   * Set the {@code relayOut} property.
   * @see #relayOut
   */
  public void setRelayOut(BStatusBoolean v) { set(relayOut, v, null); }

  //endregion Property "relayOut"

  //region Property "timer"

  /**
   * Slot for the {@code timer} property.
   * @see #getTimer
   * @see #setTimer
   */
  public static final Property timer = newProperty(0, BRelTime.makeMinutes(2), null);

  /**
   * Get the {@code timer} property.
   * @see #timer
   */
  public BRelTime getTimer() { return (BRelTime)get(timer); }

  /**
   * Set the {@code timer} property.
   * @see #timer
   */
  public void setTimer(BRelTime v) { set(timer, v, null); }

  //endregion Property "timer"

  //region Property "alarmCountType"

  /**
   * Slot for the {@code alarmCountType} property.
   * switching this value automates switching the links if not set to anyCount
   * @see #getAlarmCountType
   * @see #setAlarmCountType
   */
  public static final Property alarmCountType = newProperty(0, BAlarmCountEnum.anyCount, null);

  /**
   * Get the {@code alarmCountType} property.
   * switching this value automates switching the links if not set to anyCount
   * @see #alarmCountType
   */
  public BAlarmCountEnum getAlarmCountType() { return (BAlarmCountEnum)get(alarmCountType); }

  /**
   * Set the {@code alarmCountType} property.
   * switching this value automates switching the links if not set to anyCount
   * @see #alarmCountType
   */
  public void setAlarmCountType(BAlarmCountEnum v) { set(alarmCountType, v, null); }

  //endregion Property "alarmCountType"

  //region Action "relayOutStop"

  /**
   * Slot for the {@code relayOutStop} action.
   * @see #relayOutStop()
   */
  public static final Action relayOutStop = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code relayOutStop} action.
   * @see #relayOutStop
   */
  public void relayOutStop() { invoke(relayOutStop, null, null); }

  //endregion Action "relayOutStop"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmCountToRelay.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BAlarmCountToRelay()
  {
  }

  public void started()
  {
    inputChange();
  }

  private int checkActive()
  {
    int total = 0;
    BLink[] links = getLinks(alarmCount);
    for(int i=0; i<links.length; i++)
    {
      if(links[i].isActive())
      {
        BComponent alarmClass = links[i].getSourceComponent();
        BInteger alarmCount = (BInteger) alarmClass.get(links[i].getSourceSlot().asProperty());
        if(alarmCount.getInt() > 0)
          total+=alarmCount.getInt();
      }
    }
    return total;
  }

  protected LinkCheck doCheckLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    LinkCheck superCheck = super.doCheckLink(source, sourceSlot, targetSlot, cx);
    if(!superCheck.isValid() || getAlarmCountType().equals(BAlarmCountEnum.anyCount))
      return superCheck;

    if(!sourceSlot.getName().equals(getAlarmCountType().getTag()))
      return LinkCheck.makeInvalid("sourceSlot does not match AlarmCountType");
    else
      return LinkCheck.makeValid();
  }


  public void changed(Property p, Context cx)
  {
    if(!isRunning())
      return;

    if(p.equals(alarmCount))
    {
      inputChange();
    }
    else if(p.equals(timer))
    {
      totalAlarmCount = 0;
      inputChange();
    }
    else if(p.equals(alarmCountType))
    {
      if(!getAlarmCountType().equals(BAlarmCountEnum.anyCount))
      {
        BLink[] links = getLinks(alarmCount);
        for(int i=0; i<links.length; i++)
        {
          links[i].deactivate();
          links[i].setSourceSlotName(getAlarmCountType().getTag());
          links[i].activate();
        }
      }
      //reset
      totalAlarmCount = 0;
      inputChange();
    }
  }

  private void inputChange()
  {
    int active = checkActive();
    if(getTimer().equals(BRelTime.DEFAULT))
    {
      //untimed on
      getRelayOut().setValue( (active > 0) );
    }
    else
    {
      if(active > totalAlarmCount)
        turnOnRelay();
      else
        turnOffRelay();
      totalAlarmCount=active;
    }
  }

  private void turnOffRelay()
  {
    getRelayOut().setValue(false);
    if(relayStopTicket != null)
    {
      relayStopTicket.cancel();
      relayStopTicket=null;
    }
  }

  private void turnOnRelay()
  {
    getRelayOut().setValue(true);
    relayStopTicket = Clock.schedule(this, getTimer(), relayOutStop, null);
  }

  public void doRelayOutStop()
  {
    getRelayOut().setValue(false);
    relayStopTicket = null;
  }

  private Clock.Ticket relayStopTicket;
  private int totalAlarmCount = 0;
}
