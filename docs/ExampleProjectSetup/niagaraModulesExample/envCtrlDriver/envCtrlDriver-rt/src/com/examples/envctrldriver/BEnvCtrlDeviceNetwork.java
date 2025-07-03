/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver;

import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.driver.point.BTuningPolicyMap;
import javax.baja.driver.util.BPollScheduler;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The Device Network represents our remote EnvController network,
 * which in our case is a remote server that we connect with for
 * information.
 *
 * @author J. Spangler
 */
@NiagaraType
@NiagaraProperty(
  name = "ipAddress",
  type = "String",
  defaultValue = "###.###.###.###"
)
@NiagaraProperty(
  name = "ipPort",
  type = "int",
  defaultValue = "-1"
)
/*
 A container for tuning policies which determines how
 and when proxy points are read and written.
 */
@NiagaraProperty(
  name = "tuningPolicies",
  type = "BTuningPolicyMap",
  defaultValue = "new BTuningPolicyMap()"
)
/*
 The basic poll scheduler
 */
@NiagaraProperty(
  name = "pollScheduler",
  type = "BPollScheduler",
  defaultValue = "new BEnvCtrlPollScheduler()"
)
/*
 Submit the discovery job to the Job service from the
 station side. Returns the BOrd of the submitted Job.
 */
@NiagaraAction(
  name = "submitDeviceDiscoveryJob",
  returnType = "BOrd"
)
public class BEnvCtrlDeviceNetwork
  extends BDeviceNetwork
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.BEnvCtrlDeviceNetwork(743190414)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ipAddress"

  /**
   * Slot for the {@code ipAddress} property.
   * @see #getIpAddress
   * @see #setIpAddress
   */
  public static final Property ipAddress = newProperty(0, "###.###.###.###", null);

  /**
   * Get the {@code ipAddress} property.
   * @see #ipAddress
   */
  public String getIpAddress() { return getString(ipAddress); }

  /**
   * Set the {@code ipAddress} property.
   * @see #ipAddress
   */
  public void setIpAddress(String v) { setString(ipAddress, v, null); }

  //endregion Property "ipAddress"

  //region Property "ipPort"

  /**
   * Slot for the {@code ipPort} property.
   * @see #getIpPort
   * @see #setIpPort
   */
  public static final Property ipPort = newProperty(0, -1, null);

  /**
   * Get the {@code ipPort} property.
   * @see #ipPort
   */
  public int getIpPort() { return getInt(ipPort); }

  /**
   * Set the {@code ipPort} property.
   * @see #ipPort
   */
  public void setIpPort(int v) { setInt(ipPort, v, null); }

  //endregion Property "ipPort"

  //region Property "tuningPolicies"

  /**
   * Slot for the {@code tuningPolicies} property.
   * A container for tuning policies which determines how
   * and when proxy points are read and written.
   * @see #getTuningPolicies
   * @see #setTuningPolicies
   */
  public static final Property tuningPolicies = newProperty(0, new BTuningPolicyMap(), null);

  /**
   * Get the {@code tuningPolicies} property.
   * A container for tuning policies which determines how
   * and when proxy points are read and written.
   * @see #tuningPolicies
   */
  public BTuningPolicyMap getTuningPolicies() { return (BTuningPolicyMap)get(tuningPolicies); }

  /**
   * Set the {@code tuningPolicies} property.
   * A container for tuning policies which determines how
   * and when proxy points are read and written.
   * @see #tuningPolicies
   */
  public void setTuningPolicies(BTuningPolicyMap v) { set(tuningPolicies, v, null); }

  //endregion Property "tuningPolicies"

  //region Property "pollScheduler"

  /**
   * Slot for the {@code pollScheduler} property.
   * The basic poll scheduler
   * @see #getPollScheduler
   * @see #setPollScheduler
   */
  public static final Property pollScheduler = newProperty(0, new BEnvCtrlPollScheduler(), null);

  /**
   * Get the {@code pollScheduler} property.
   * The basic poll scheduler
   * @see #pollScheduler
   */
  public BPollScheduler getPollScheduler() { return (BPollScheduler)get(pollScheduler); }

  /**
   * Set the {@code pollScheduler} property.
   * The basic poll scheduler
   * @see #pollScheduler
   */
  public void setPollScheduler(BPollScheduler v) { set(pollScheduler, v, null); }

  //endregion Property "pollScheduler"

  //region Action "submitDeviceDiscoveryJob"

  /**
   * Slot for the {@code submitDeviceDiscoveryJob} action.
   * Submit the discovery job to the Job service from the
   * station side. Returns the BOrd of the submitted Job.
   * @see #submitDeviceDiscoveryJob()
   */
  public static final Action submitDeviceDiscoveryJob = newAction(0, null);

  /**
   * Invoke the {@code submitDeviceDiscoveryJob} action.
   * Submit the discovery job to the Job service from the
   * station side. Returns the BOrd of the submitted Job.
   * @see #submitDeviceDiscoveryJob
   */
  public BOrd submitDeviceDiscoveryJob() { return (BOrd)invoke(submitDeviceDiscoveryJob, null, null); }

  //endregion Action "submitDeviceDiscoveryJob"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnvCtrlDeviceNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * constructor
   * <p>
   * On creation, create a new TCP comm stack instance.
   */
  public BEnvCtrlDeviceNetwork()
  {
    this.comm = new TcpComm(this);
  }

  /**
   * Return the {@link Type} for the Device Folder that can be parented
   * under our network.
   */
  public Type getDeviceFolderType()
  {
    return BEnvCtrlDeviceFolder.TYPE;
  }

  /**
   * Return the {@link Type} for the {@link BDevice} that can be parented
   * under the network.
   */
  public Type getDeviceType()
  {
    return BEnvCtrlDevice.TYPE;
  }


/////////////////////////////////////////////////////////////////
// Actions
/////////////////////////////////////////////////////////////////

  /**
   * Submit a Device Discovery job to the job service. The job will
   * send a simple request to the network which will respond with the
   * remote device network response message which can be parsed into
   * discovery objects.
   *
   * @param cx
   * @return
   */
  public BOrd doSubmitDeviceDiscoveryJob(Context cx)
  {
    BDeviceDiscoveryJob job = new BDeviceDiscoveryJob(this);
    BOrd jobOrd = job.submit(cx);
    return jobOrd;
  }

  /**
   * This method pings the remote device network to ensure that the
   * network is available to receive commands.
   */
  public void doPing()
    throws Exception
  {
    String sendRequest = sendRequest("ping");

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
   * This simple method sends requests over the network to the remote
   * device network.
   *
   * @param request
   * @return
   */
  public String sendRequest(String request)
  {
    return comm.sendRequest(request);
  }

/////////////////////////////////////////////////////////////////
//  Attributes
/////////////////////////////////////////////////////////////////

  private TcpComm comm;
}
