/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import static com.tridium.nrio.messages.NrioMessageConst.REMOTE_IO_34_PRI;
import static com.tridium.nrio.messages.NrioMessageConst.REMOTE_IO_34_SEC;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBlob;
import javax.baja.sys.BComplex;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.components.BNrio34SecStatus;
import com.tridium.nrio.components.BOutputDefaultValues;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.messages.SetLogicalAddressMessage;
import com.tridium.nrio.points.BNrio34PriSecPoints;
import com.tridium.nrio.util.DualModuleUtils;


/**
 * BNrio34SecModule represents a Nrio IO34 Module
 *
 * @author    Andy Saunders
 * @creation  7 Oct 16
 */

@NiagaraType
@NiagaraProperty(
  name = "deviceType",
  type = "BNrioDeviceTypeEnum",
  defaultValue = "BNrioDeviceTypeEnum.io34sec",
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
  defaultValue = "new BNrio34SecStatus()",
  override = true
)


public class BNrio34SecModule
extends BNrio16Module
{



//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrio34SecModule(3551436016)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(Flags.READONLY, BNrioDeviceTypeEnum.io34sec, null);

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
  public static final Property ioStatus = newProperty(0, new BNrio34SecStatus(), null);

  //endregion Property "ioStatus"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio34SecModule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNrio34Module getParentModule()
  {
    final BComplex parent = getParent();
    if(parent instanceof BNrio34Module)
      return (BNrio34Module)parent;
    return null;
  }

  public void started()
    throws Exception
  {
    super.started();
    byte[] uid = getParentModule().getUid().copyBytes();
    setUid(BBlob.make(uid));
    setAvailableVersion(getParentModule().getAvailableVersion());
  }

  // handled by BNrio34Module
  public void doPing()
  {
  }

  public void pingFail(String cause)
  {
    super.pingFail(cause);
    getParentModule().siblingPingFail(cause);
    doDisablePolling();
  }

  public void siblingPingFail(String cause)
  {
    super.pingFail(cause);
    doDisablePolling();
  }

  public void doEnablePolling()
  {
    // do nothing as the IO34 primary module handles by
    // calling siblingEnablePolling
  }

  public void doDisablePolling()
  {
    // do nothing as the IO34 primary module handles by
    // calling doDisablePolling
  }

  public void doSetAddressAndPing()
  {
    //nothing to do as BNrio34Module parent handles.
  }

  public void readBuildInfo()
  {
    //nothing to do as BNrio34Module parent handles.
  }



  public void siblingEnablePolling()
  {
    super.doEnablePolling();
  }

  public void siblingDisablePolling()
  {
    super.doDisablePolling();
  }

  /**
   * Get the parent Io34 module and let it return the value.
   */
  public boolean isWriteOutputDefaultsInProgress()
  {
    return getParentModule().isWriteOutputDefaultsInProgress();
  }

  public void doWriteOutputDefaultInfo()
  {
    // BNrio34 module handles this, so do nothing.
  }


  // just return OK as BNrio34Module sendOutputConfig() will handle this.
  public int sendOutputConfig()
  {
    return NrioMessageConst.MESSAGE_STATUS_OK;
  }


}
