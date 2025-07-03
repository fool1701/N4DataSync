/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver;

import javax.baja.job.*;
import javax.baja.nre.util.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * Job for discovering devices currently on the remote EnvController network.
 *
 * @author J. Spangler on Mar 4, 2013
 */
@NiagaraType
/*
 Contains dynamic BComponent slots, each slot corresponds to
 the discovery information about a learned device.
 */
@NiagaraProperty(
  name = "learnedDevices",
  type = "BFolder",
  defaultValue = "new BFolder()",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT
)
public class BDeviceDiscoveryJob
  extends BSimpleJob
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.BDeviceDiscoveryJob(4076180185)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "learnedDevices"

  /**
   * Slot for the {@code learnedDevices} property.
   * Contains dynamic BComponent slots, each slot corresponds to
   * the discovery information about a learned device.
   * @see #getLearnedDevices
   * @see #setLearnedDevices
   */
  public static final Property learnedDevices = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT, new BFolder(), null);

  /**
   * Get the {@code learnedDevices} property.
   * Contains dynamic BComponent slots, each slot corresponds to
   * the discovery information about a learned device.
   * @see #learnedDevices
   */
  public BFolder getLearnedDevices() { return (BFolder)get(learnedDevices); }

  /**
   * Set the {@code learnedDevices} property.
   * Contains dynamic BComponent slots, each slot corresponds to
   * the discovery information about a learned device.
   * @see #learnedDevices
   */
  public void setLearnedDevices(BFolder v) { set(learnedDevices, v, null); }

  //endregion Property "learnedDevices"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDeviceDiscoveryJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Framework No Arg constructor
   */
  public BDeviceDiscoveryJob()
  {
    this.network = null;
  }

  /**
   * Constructor
   *
   * @param network - {@link BEnvCtrlDeviceNetwork} network to use to send
   *                discovery request
   */
  public BDeviceDiscoveryJob(BEnvCtrlDeviceNetwork network)
  {
    this.network = network;
  }

  /**
   * Add an entry into our BFolder that represents a discovered device.
   * This information should be enough to reconstruct our device
   * representation when we add the entry into the running station.
   *
   * @param deviceName - String name of the device that we are adding to our
   *                   list of entries as it is named on the remote network.
   * @param id         - int ID of the remote device.
   */
  private void addEntry(String deviceName, int id)
  {
    BDeviceLearnEntry entry = new BDeviceLearnEntry();
    entry.setDeviceName(deviceName);
    entry.setDeviceId(id);
    getLearnedDevices().add(null, entry);
  }

  public void run(Context cx)
    throws Exception
  {
    log().message("Starting");

    //send a message to the network requesting discovery of all devices.
    if (null == network)
    {
      log().failed("Not connected to network");
      failed(new BajaRuntimeException("No Network found"));
    }

    String response = network.sendRequest("learn");

    //parse response into entries
    String[] deviceEntries = TextUtil.split(response, ';');
    for (int i = 0; i < deviceEntries.length; i++)
    {
      //split the device entry into name and id
      String entry = deviceEntries[i];
      String[] values = TextUtil.split(entry, ' ');

      if (values.length < 2)
      {
        continue;
      }

      int id = Integer.parseInt(values[1]);
      addEntry(values[0], id);
      log().message("discovered device: " + values[0]);
    }

    success();
  }

/////////////////////////////////////////////////////////////////
//  Attributes
/////////////////////////////////////////////////////////////////

  BEnvCtrlDeviceNetwork network;
}
