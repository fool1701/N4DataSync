/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver.points;

import javax.baja.job.BSimpleJob;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFolder;

import com.examples.envctrldriver.BEnvCtrlDeviceNetwork;

/**
 * The Point Discovery Job sends a discovery request to the remote
 * network and then parses the response into {@link BPointLearnEntry}
 * objects that are stored on this Job instance. Those can be parsed
 * later into control point proxy extension instances.
 *
 * @author J. Spangler on Mar 7, 2013
 */
@NiagaraType
/*
 Contains dynamic BComponent slots, each slot corresponds to
 the discovery information about a learned device.
 */
@NiagaraProperty(
  name = "learnedPoints",
  type = "BFolder",
  defaultValue = "new BFolder()",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT
)
public class BPointDiscoveryJob
  extends BSimpleJob
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.points.BPointDiscoveryJob(2533780779)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "learnedPoints"

  /**
   * Slot for the {@code learnedPoints} property.
   * Contains dynamic BComponent slots, each slot corresponds to
   * the discovery information about a learned device.
   * @see #getLearnedPoints
   * @see #setLearnedPoints
   */
  public static final Property learnedPoints = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT, new BFolder(), null);

  /**
   * Get the {@code learnedPoints} property.
   * Contains dynamic BComponent slots, each slot corresponds to
   * the discovery information about a learned device.
   * @see #learnedPoints
   */
  public BFolder getLearnedPoints() { return (BFolder)get(learnedPoints); }

  /**
   * Set the {@code learnedPoints} property.
   * Contains dynamic BComponent slots, each slot corresponds to
   * the discovery information about a learned device.
   * @see #learnedPoints
   */
  public void setLearnedPoints(BFolder v) { set(learnedPoints, v, null); }

  //endregion Property "learnedPoints"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointDiscoveryJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * No Arg constructor for Framework use only.
   */
  public BPointDiscoveryJob()
  {
  }

  /**
   * Constructor
   *
   * @param network  - {@link BEnvCtrlDeviceNetwork} to send messages to.
   * @param deviceId - int ID of the device that we are contacting on the
   *                 remote station to find points for.
   */
  public BPointDiscoveryJob(BEnvCtrlDeviceNetwork network, int deviceId)
  {
    this.network = network;
    this.deviceId = deviceId;
  }

  /**
   * Add an entry to our list of discovered points.
   *
   * @param pointName
   * @param dataType
   */
  public void addEntry(String pointName, String dataType)
  {
    BPointLearnEntry entry = new BPointLearnEntry();
    entry.setPointId(pointName);
    entry.setPointName(pointName);
    entry.setPointType(dataType);
    getLearnedPoints().add(null, entry);
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

    String response = network.sendRequest("learn " + deviceId);

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

      addEntry(values[0], values[1]);
      log().message("discovered device: " + values[0]);
    }

    success();
  }

/////////////////////////////////////////////////////////////////
//  Attributes
/////////////////////////////////////////////////////////////////

  private BEnvCtrlDeviceNetwork network;
  private int deviceId;
}
