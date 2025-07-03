/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver;

import javax.baja.driver.BDevice;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;

import com.examples.envctrldriver.points.BEnvCtrlPointDeviceExt;
import com.examples.envctrldriver.points.BPointDiscoveryJob;

/**
 * This class represents the remote device on the remote network.
 *
 * @author J. Spangler
 */
@NiagaraType
@NiagaraProperty(
  name = "deviceId",
  type = "int",
  defaultValue = "-1"
)
/*
 Point container
 */
@NiagaraProperty(
  name = "points",
  type = "BEnvCtrlPointDeviceExt",
  defaultValue = "new BEnvCtrlPointDeviceExt()"
)
/*
 submits a point discovery job and returns ORD to job
 in the job service
 */
@NiagaraAction(
  name = "submitPointDiscoveryJob",
  returnType = "BOrd"
)
public class BEnvCtrlDevice
  extends BDevice
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.BEnvCtrlDevice(4094081582)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * Point container
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BEnvCtrlPointDeviceExt(), null);

  /**
   * Get the {@code points} property.
   * Point container
   * @see #points
   */
  public BEnvCtrlPointDeviceExt getPoints() { return (BEnvCtrlPointDeviceExt)get(points); }

  /**
   * Set the {@code points} property.
   * Point container
   * @see #points
   */
  public void setPoints(BEnvCtrlPointDeviceExt v) { set(points, v, null); }

  //endregion Property "points"

  //region Action "submitPointDiscoveryJob"

  /**
   * Slot for the {@code submitPointDiscoveryJob} action.
   * submits a point discovery job and returns ORD to job
   * in the job service
   * @see #submitPointDiscoveryJob()
   */
  public static final Action submitPointDiscoveryJob = newAction(0, null);

  /**
   * Invoke the {@code submitPointDiscoveryJob} action.
   * submits a point discovery job and returns ORD to job
   * in the job service
   * @see #submitPointDiscoveryJob
   */
  public BOrd submitPointDiscoveryJob() { return (BOrd)invoke(submitPointDiscoveryJob, null, null); }

  //endregion Action "submitPointDiscoveryJob"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnvCtrlDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * The doPing callback is called to determine the availability
   * of the device on the network.
   */
  public void doPing()
    throws Exception
  {
    BEnvCtrlDeviceNetwork network = (BEnvCtrlDeviceNetwork)getNetwork();
    String sendRequest = network.sendRequest("ping " + getDeviceId());

    if (sendRequest.equals("ping ok!"))
    {
      pingOk();
    }
    else
    {
      pingFail(sendRequest);
    }
  }

  /**
   * This post ping is called asynchronously.
   */
  protected IFuture postPing()
  {
    try
    {
      doPing();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get the Type of the parent network.
   */
  public Type getNetworkType()
  {
    return BEnvCtrlDeviceNetwork.TYPE;
  }

/////////////////////////////////////////////////////////////////
//Actions
/////////////////////////////////////////////////////////////////

  /**
   * This action is used to launch the Discovery Job for points
   * under this device on the remote device.
   *
   * @param cx
   * @return {@link BOrd} to the submitted job.
   */
  public BOrd doSubmitPointDiscoveryJob(Context cx)
  {
    BEnvCtrlDeviceNetwork network = (BEnvCtrlDeviceNetwork)getNetwork();
    BPointDiscoveryJob job = new BPointDiscoveryJob(network, getDeviceId());
    BOrd submit = job.submit(cx);
    return submit;
  }
}
