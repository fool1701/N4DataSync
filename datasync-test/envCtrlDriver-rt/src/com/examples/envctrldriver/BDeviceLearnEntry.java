/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This simple struct contains the information that we discover at
 * the time of device discovery. We can use this information to later
 * construct an {@link BEnvCtrlDevice} instance.
 *
 * @author J. Spangler
 */
@NiagaraType
@NiagaraProperty(
  name = "deviceName",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "deviceId",
  type = "int",
  defaultValue = "-1"
)
public class BDeviceLearnEntry
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.BDeviceLearnEntry(3085379804)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceName"

  /**
   * Slot for the {@code deviceName} property.
   * @see #getDeviceName
   * @see #setDeviceName
   */
  public static final Property deviceName = newProperty(0, "", null);

  /**
   * Get the {@code deviceName} property.
   * @see #deviceName
   */
  public String getDeviceName() { return getString(deviceName); }

  /**
   * Set the {@code deviceName} property.
   * @see #deviceName
   */
  public void setDeviceName(String v) { setString(deviceName, v, null); }

  //endregion Property "deviceName"

  //region Property "deviceId"

  /**
   * Slot for the {@code deviceId} property.
   * @see #getDeviceId
   * @see #setDeviceId
   */
  public static final Property deviceId = newProperty(0, -1, null);

  /**
   * Get the {@code deviceId} property.
   * @see #deviceId
   */
  public int getDeviceId() { return getInt(deviceId); }

  /**
   * Set the {@code deviceId} property.
   * @see #deviceId
   */
  public void setDeviceId(int v) { setInt(deviceId, v, null); }

  //endregion Property "deviceId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDeviceLearnEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
