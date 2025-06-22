/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.components.BNrio34PriStatus;
import com.tridium.nrio.components.BNrio34SecStatus;
import com.tridium.nrio.components.BOutputDefaultValues;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.points.BNrio34PriSecPoints;


/**
 * BNrio34Module represents a Nrio IO34 Module
 *
 * @author    Andy Saunders
 * @creation  7 Oct 16
 */

@NiagaraType
@NiagaraProperty(
  name = "deviceType",
  type = "BNrioDeviceTypeEnum",
  defaultValue = "BNrioDeviceTypeEnum.io34",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "points",
  type = "BNrioPointDeviceExt",
  defaultValue = "new BNrio34PriSecPoints()",
  override = true
)
@NiagaraProperty(
  name = "ioStatus",
  type = "BStruct",
  defaultValue = "new BNrio34PriStatus()",
  override = true
)
@NiagaraProperty(
  name = "secIoStatus",
  type = "BStruct",
  defaultValue = "new BNrio34SecStatus()"
)
@NiagaraProperty(
  name = "secVersion",
  type = "String",
  defaultValue = ""
)


public class BNrio34PriModule
extends BNrio16Module
{





//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrio34PriModule(2233343689)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(Flags.READONLY, BNrioDeviceTypeEnum.io34, null);

  //endregion Property "deviceType"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BNrio34PriSecPoints(), null);

  //endregion Property "points"

  //region Property "ioStatus"

  /**
   * Slot for the {@code ioStatus} property.
   * @see #getIoStatus
   * @see #setIoStatus
   */
  public static final Property ioStatus = newProperty(0, new BNrio34PriStatus(), null);

  //endregion Property "ioStatus"

  //region Property "secIoStatus"

  /**
   * Slot for the {@code secIoStatus} property.
   * @see #getSecIoStatus
   * @see #setSecIoStatus
   */
  public static final Property secIoStatus = newProperty(0, new BNrio34SecStatus(), null);

  /**
   * Get the {@code secIoStatus} property.
   * @see #secIoStatus
   */
  public BStruct getSecIoStatus() { return (BStruct)get(secIoStatus); }

  /**
   * Set the {@code secIoStatus} property.
   * @see #secIoStatus
   */
  public void setSecIoStatus(BStruct v) { set(secIoStatus, v, null); }

  //endregion Property "secIoStatus"

  //region Property "secVersion"

  /**
   * Slot for the {@code secVersion} property.
   * @see #getSecVersion
   * @see #setSecVersion
   */
  public static final Property secVersion = newProperty(0, "", null);

  /**
   * Get the {@code secVersion} property.
   * @see #secVersion
   */
  public String getSecVersion() { return getString(secVersion); }

  /**
   * Set the {@code secVersion} property.
   * @see #secVersion
   */
  public void setSecVersion(String v) { setString(secVersion, v, null); }

  //endregion Property "secVersion"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio34PriModule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
