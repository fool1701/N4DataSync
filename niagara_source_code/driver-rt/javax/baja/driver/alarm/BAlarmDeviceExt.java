/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.alarm;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BIRemoteAlarmRecipient;
import javax.baja.alarm.BIRemoteAlarmSource;
import javax.baja.driver.BDeviceExt;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BAlarmDeviceExt is the base class for mapping native alarms
 * in a device into the Baja alarm framework.
 *
 * @author    Brian Frank       
 * @creation  17 Oct 01
 * @version   $Revision: 23$ $Date: 2/22/06 2:53:39 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Default alarmClass to route alarm to.
 If blank, use alarm's alarmClass.
 */
@NiagaraProperty(
  name = "alarmClass",
  type = "String",
  defaultValue = "defaultAlarmClass",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"alarm:AlarmClassFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"alarm:AlarmClassEditor\"))")
)
@NiagaraProperty(
  name = "lastReceivedTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.SUMMARY | Flags.READONLY,
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE)")
)
@NiagaraAction(
  name = "routeAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "ackAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  returnType = "BBoolean",
  flags = Flags.HIDDEN
)
public abstract class BAlarmDeviceExt
  extends BDeviceExt
  implements BIRemoteAlarmSource, BIRemoteAlarmRecipient
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.alarm.BAlarmDeviceExt(3309173282)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * Default alarmClass to route alarm to.
   * If blank, use alarm's alarmClass.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(0, "defaultAlarmClass", BFacets.make(BFacets.FIELD_EDITOR, BString.make("alarm:AlarmClassFE"), BFacets.UX_FIELD_EDITOR, BString.make("alarm:AlarmClassEditor")));

  /**
   * Get the {@code alarmClass} property.
   * Default alarmClass to route alarm to.
   * If blank, use alarm's alarmClass.
   * @see #alarmClass
   */
  public String getAlarmClass() { return getString(alarmClass); }

  /**
   * Set the {@code alarmClass} property.
   * Default alarmClass to route alarm to.
   * If blank, use alarm's alarmClass.
   * @see #alarmClass
   */
  public void setAlarmClass(String v) { setString(alarmClass, v, null); }

  //endregion Property "alarmClass"

  //region Property "lastReceivedTime"

  /**
   * Slot for the {@code lastReceivedTime} property.
   * @see #getLastReceivedTime
   * @see #setLastReceivedTime
   */
  public static final Property lastReceivedTime = newProperty(Flags.SUMMARY | Flags.READONLY, BAbsTime.NULL, BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE));

  /**
   * Get the {@code lastReceivedTime} property.
   * @see #lastReceivedTime
   */
  public BAbsTime getLastReceivedTime() { return (BAbsTime)get(lastReceivedTime); }

  /**
   * Set the {@code lastReceivedTime} property.
   * @see #lastReceivedTime
   */
  public void setLastReceivedTime(BAbsTime v) { set(lastReceivedTime, v, null); }

  //endregion Property "lastReceivedTime"

  //region Action "routeAlarm"

  /**
   * Slot for the {@code routeAlarm} action.
   * @see #routeAlarm(BAlarmRecord parameter)
   */
  public static final Action routeAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code routeAlarm} action.
   * @see #routeAlarm
   */
  public void routeAlarm(BAlarmRecord parameter) { invoke(routeAlarm, parameter, null); }

  //endregion Action "routeAlarm"

  //region Action "ackAlarm"

  /**
   * Slot for the {@code ackAlarm} action.
   * @see #ackAlarm(BAlarmRecord parameter)
   */
  public static final Action ackAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code ackAlarm} action.
   * @see #ackAlarm
   */
  public BBoolean ackAlarm(BAlarmRecord parameter) { return (BBoolean)invoke(ackAlarm, parameter, null); }

  //endregion Action "ackAlarm"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public abstract void doRouteAlarm(BAlarmRecord record)
    throws Exception;
    
  public abstract BBoolean doAckAlarm(BAlarmRecord record)
    throws Exception;

  /**
   * BAlarmDeviceExt should not have any children.
   */
  public boolean isChildLegal(BComponent parent)
  {
    return false;
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/alarmService.png");

}
