/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import java.io.*;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BStatusDemux will decode the status flags of
 *   its inputs and expose as individual StatusBoolean
 *   outputs. 
 *
 * @author    Andy Saunders
 * @creation  6 Aug 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "inStatus",
  type = "BStatus",
  defaultValue = "BStatus.make(0)",
  flags = Flags.OPERATOR | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "inBoolean",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "inEnum",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.OPERATOR | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "inNumeric",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "inString",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.OPERATOR | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "alarm",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "down",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "fault",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "nullStatus",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "disabled",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "overridden",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "unackedAlarm",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "stale",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
public class BStatusDemux
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BStatusDemux(2679911201)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inStatus"

  /**
   * Slot for the {@code inStatus} property.
   * @see #getInStatus
   * @see #setInStatus
   */
  public static final Property inStatus = newProperty(Flags.OPERATOR | Flags.TRANSIENT, BStatus.make(0), null);

  /**
   * Get the {@code inStatus} property.
   * @see #inStatus
   */
  public BStatus getInStatus() { return (BStatus)get(inStatus); }

  /**
   * Set the {@code inStatus} property.
   * @see #inStatus
   */
  public void setInStatus(BStatus v) { set(inStatus, v, null); }

  //endregion Property "inStatus"

  //region Property "inBoolean"

  /**
   * Slot for the {@code inBoolean} property.
   * @see #getInBoolean
   * @see #setInBoolean
   */
  public static final Property inBoolean = newProperty(Flags.OPERATOR | Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code inBoolean} property.
   * @see #inBoolean
   */
  public BStatusBoolean getInBoolean() { return (BStatusBoolean)get(inBoolean); }

  /**
   * Set the {@code inBoolean} property.
   * @see #inBoolean
   */
  public void setInBoolean(BStatusBoolean v) { set(inBoolean, v, null); }

  //endregion Property "inBoolean"

  //region Property "inEnum"

  /**
   * Slot for the {@code inEnum} property.
   * @see #getInEnum
   * @see #setInEnum
   */
  public static final Property inEnum = newProperty(Flags.OPERATOR | Flags.TRANSIENT, new BStatusEnum(), null);

  /**
   * Get the {@code inEnum} property.
   * @see #inEnum
   */
  public BStatusEnum getInEnum() { return (BStatusEnum)get(inEnum); }

  /**
   * Set the {@code inEnum} property.
   * @see #inEnum
   */
  public void setInEnum(BStatusEnum v) { set(inEnum, v, null); }

  //endregion Property "inEnum"

  //region Property "inNumeric"

  /**
   * Slot for the {@code inNumeric} property.
   * @see #getInNumeric
   * @see #setInNumeric
   */
  public static final Property inNumeric = newProperty(Flags.OPERATOR | Flags.TRANSIENT, new BStatusNumeric(), null);

  /**
   * Get the {@code inNumeric} property.
   * @see #inNumeric
   */
  public BStatusNumeric getInNumeric() { return (BStatusNumeric)get(inNumeric); }

  /**
   * Set the {@code inNumeric} property.
   * @see #inNumeric
   */
  public void setInNumeric(BStatusNumeric v) { set(inNumeric, v, null); }

  //endregion Property "inNumeric"

  //region Property "inString"

  /**
   * Slot for the {@code inString} property.
   * @see #getInString
   * @see #setInString
   */
  public static final Property inString = newProperty(Flags.OPERATOR | Flags.TRANSIENT, new BStatusString(), null);

  /**
   * Get the {@code inString} property.
   * @see #inString
   */
  public BStatusString getInString() { return (BStatusString)get(inString); }

  /**
   * Set the {@code inString} property.
   * @see #inString
   */
  public void setInString(BStatusString v) { set(inString, v, null); }

  //endregion Property "inString"

  //region Property "alarm"

  /**
   * Slot for the {@code alarm} property.
   * @see #getAlarm
   * @see #setAlarm
   */
  public static final Property alarm = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code alarm} property.
   * @see #alarm
   */
  public BStatusBoolean getAlarm() { return (BStatusBoolean)get(alarm); }

  /**
   * Set the {@code alarm} property.
   * @see #alarm
   */
  public void setAlarm(BStatusBoolean v) { set(alarm, v, null); }

  //endregion Property "alarm"

  //region Property "down"

  /**
   * Slot for the {@code down} property.
   * @see #getDown
   * @see #setDown
   */
  public static final Property down = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code down} property.
   * @see #down
   */
  public BStatusBoolean getDown() { return (BStatusBoolean)get(down); }

  /**
   * Set the {@code down} property.
   * @see #down
   */
  public void setDown(BStatusBoolean v) { set(down, v, null); }

  //endregion Property "down"

  //region Property "fault"

  /**
   * Slot for the {@code fault} property.
   * @see #getFault
   * @see #setFault
   */
  public static final Property fault = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code fault} property.
   * @see #fault
   */
  public BStatusBoolean getFault() { return (BStatusBoolean)get(fault); }

  /**
   * Set the {@code fault} property.
   * @see #fault
   */
  public void setFault(BStatusBoolean v) { set(fault, v, null); }

  //endregion Property "fault"

  //region Property "nullStatus"

  /**
   * Slot for the {@code nullStatus} property.
   * @see #getNullStatus
   * @see #setNullStatus
   */
  public static final Property nullStatus = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code nullStatus} property.
   * @see #nullStatus
   */
  public BStatusBoolean getNullStatus() { return (BStatusBoolean)get(nullStatus); }

  /**
   * Set the {@code nullStatus} property.
   * @see #nullStatus
   */
  public void setNullStatus(BStatusBoolean v) { set(nullStatus, v, null); }

  //endregion Property "nullStatus"

  //region Property "disabled"

  /**
   * Slot for the {@code disabled} property.
   * @see #getDisabled
   * @see #setDisabled
   */
  public static final Property disabled = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code disabled} property.
   * @see #disabled
   */
  public BStatusBoolean getDisabled() { return (BStatusBoolean)get(disabled); }

  /**
   * Set the {@code disabled} property.
   * @see #disabled
   */
  public void setDisabled(BStatusBoolean v) { set(disabled, v, null); }

  //endregion Property "disabled"

  //region Property "overridden"

  /**
   * Slot for the {@code overridden} property.
   * @see #getOverridden
   * @see #setOverridden
   */
  public static final Property overridden = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code overridden} property.
   * @see #overridden
   */
  public BStatusBoolean getOverridden() { return (BStatusBoolean)get(overridden); }

  /**
   * Set the {@code overridden} property.
   * @see #overridden
   */
  public void setOverridden(BStatusBoolean v) { set(overridden, v, null); }

  //endregion Property "overridden"

  //region Property "unackedAlarm"

  /**
   * Slot for the {@code unackedAlarm} property.
   * @see #getUnackedAlarm
   * @see #setUnackedAlarm
   */
  public static final Property unackedAlarm = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code unackedAlarm} property.
   * @see #unackedAlarm
   */
  public BStatusBoolean getUnackedAlarm() { return (BStatusBoolean)get(unackedAlarm); }

  /**
   * Set the {@code unackedAlarm} property.
   * @see #unackedAlarm
   */
  public void setUnackedAlarm(BStatusBoolean v) { set(unackedAlarm, v, null); }

  //endregion Property "unackedAlarm"

  //region Property "stale"

  /**
   * Slot for the {@code stale} property.
   * @see #getStale
   * @see #setStale
   */
  public static final Property stale = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code stale} property.
   * @see #stale
   */
  public BStatusBoolean getStale() { return (BStatusBoolean)get(stale); }

  /**
   * Set the {@code stale} property.
   * @see #stale
   */
  public void setStale(BStatusBoolean v) { set(stale, v, null); }

  //endregion Property "stale"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusDemux.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      if( property.getName().startsWith("in") )
      {
        calculate();
      }
    }
  }

  /**
   * Default implementation is to do nothing.
   */
  public void calculate()
  {
    checkLinks();
    int statusBits = getInStatus().getBits();
    statusBits     = statusBits | getInBoolean().getStatus().getBits();
    statusBits     = statusBits | getInEnum().getStatus().getBits();
    statusBits     = statusBits | getInNumeric().getStatus().getBits();
    statusBits     = statusBits | getInString().getStatus().getBits();

    BStatus status = BStatus.make(statusBits);

    getAlarm()       .setValue(status.isAlarm()       );
    getFault()       .setValue(status.isFault()       );
    getOverridden()  .setValue(status.isOverridden());
    getDisabled()    .setValue(status.isDisabled());
    getNullStatus()  .setValue(status.isNull()        );
    getUnackedAlarm().setValue(status.isUnackedAlarm());
    getDown()        .setValue(status.isDown()        );
    getStale()       .setValue(status.isStale()       );
  }

  void checkLinks()
  {
    BLink[] links = getLinks(getSlot("inNumeric"));
    if(links.length == 0)
      setInNumeric(newNumeric);
    links = getLinks(getSlot("inBoolean"));
    if(links.length == 0)
      setInBoolean(newBoolean);
    links = getLinks(getSlot("inEnum"));
    if(links.length == 0)
      setInEnum(newEnum);
    links = getLinks(getSlot("inString"));
    if(links.length == 0)
      setInString(newString);
    links = getLinks(getSlot("inStatus"));
    if(links.length == 0)
      setInStatus(BStatus.make(0));
  }



////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

  private BStatusBoolean newBoolean = new BStatusBoolean(false);
  private BStatusEnum    newEnum = new BStatusEnum();
  private BStatusNumeric newNumeric = new BStatusNumeric(0f);
  private BStatusString  newString = new BStatusString("");

}
