/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.driver.BDevice;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.serial.BSerialBaudRate;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.components.BSdiValueConfig;
import com.tridium.nrio.job.BM2mLearnDeviceJob;
import com.tridium.nrio.messages.NrioMessageConst;

/**
 * BNrioNetwork is the base container for BNrioDevices.   
 *
 * @author    Andy Saunders       
 * @creation  19 Dec 08
 * @version   $Revision$ $Date: 9/12/2005 2:48:27 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "portName",
  type = "String",
  defaultValue = "COM3",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "trunk",
  type = "int",
  defaultValue = "1",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "baudRate",
  type = "BBaudRate",
  defaultValue = "BSerialBaudRate.baud115200",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "sdiValueConfig",
  type = "BSdiValueConfig",
  defaultValue = "new BSdiValueConfig()",
  flags = Flags.HIDDEN,
  override = true
)
public class BM2mIoNetwork
  extends BNrioNetwork
  implements Runnable, NrioMessageConst
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BM2mIoNetwork(4091049610)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "portName"

  /**
   * Slot for the {@code portName} property.
   * @see #getPortName
   * @see #setPortName
   */
  public static final Property portName = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, "COM3", null);

  //endregion Property "portName"

  //region Property "trunk"

  /**
   * Slot for the {@code trunk} property.
   * @see #getTrunk
   * @see #setTrunk
   */
  public static final Property trunk = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, 1, null);

  //endregion Property "trunk"

  //region Property "baudRate"

  /**
   * Slot for the {@code baudRate} property.
   * @see #getBaudRate
   * @see #setBaudRate
   */
  public static final Property baudRate = newProperty(Flags.READONLY, BSerialBaudRate.baud115200, null);

  //endregion Property "baudRate"

  //region Property "sdiValueConfig"

  /**
   * Slot for the {@code sdiValueConfig} property.
   * @see #getSdiValueConfig
   * @see #setSdiValueConfig
   */
  public static final Property sdiValueConfig = newProperty(Flags.HIDDEN, new BSdiValueConfig(), null);

  //endregion Property "sdiValueConfig"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BM2mIoNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void started()
  throws Exception
  {
    super.started();
    //setMaxDevices(1);
  }

  public void atSteadyState()
  {
    BOrd jobOrd = doSubmitDeviceDiscoveryJob();
    BM2mLearnDeviceJob job = (BM2mLearnDeviceJob)jobOrd.get(this);

  }

  public BNrio16Module getLocalIoModule()
  {
    BDevice[] devices = getDevices();
    if(devices == null || devices.length == 0)
      return null;
    if(devices[0] instanceof BNrio16Module)
      return (BNrio16Module)devices[0];
    else 
      return null;
  }

  public BOrd doSubmitDeviceDiscoveryJob()
  {
    if(getStatus().isDisabled())
      return null;
    BM2mLearnDeviceJob job = new BM2mLearnDeviceJob(this);
    return job.submit(null);
  }

  
}
