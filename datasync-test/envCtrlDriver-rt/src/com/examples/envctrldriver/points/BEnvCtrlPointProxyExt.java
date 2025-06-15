/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver.points;

import javax.baja.driver.point.BProxyExt;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.*;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.examples.envctrldriver.*;

/**
 * The Proxy for point data on our remote EnvController network.
 *
 * @author J. Spangler on Mar 7, 2013
 */
@NiagaraType
/*
 Poll frequency bucket
 */
@NiagaraProperty(
  name = "pollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal"
)
/*
 Unique name of the point on the device.
 */
@NiagaraProperty(
  name = "pointId",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "writeHandler",
  type = "BWriteWorker",
  defaultValue = "new BWriteWorker()"
)
@NiagaraAction(
  name = "postWrite",
  flags = Flags.ASYNC
)
public class BEnvCtrlPointProxyExt
  extends BProxyExt
  implements BIEnvCtrlPollable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.points.BEnvCtrlPointProxyExt(3207578956)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pollFrequency"

  /**
   * Slot for the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #getPollFrequency
   * @see #setPollFrequency
   */
  public static final Property pollFrequency = newProperty(0, BPollFrequency.normal, null);

  /**
   * Get the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #pollFrequency
   */
  public BPollFrequency getPollFrequency() { return (BPollFrequency)get(pollFrequency); }

  /**
   * Set the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #pollFrequency
   */
  public void setPollFrequency(BPollFrequency v) { set(pollFrequency, v, null); }

  //endregion Property "pollFrequency"

  //region Property "pointId"

  /**
   * Slot for the {@code pointId} property.
   * Unique name of the point on the device.
   * @see #getPointId
   * @see #setPointId
   */
  public static final Property pointId = newProperty(0, "", null);

  /**
   * Get the {@code pointId} property.
   * Unique name of the point on the device.
   * @see #pointId
   */
  public String getPointId() { return getString(pointId); }

  /**
   * Set the {@code pointId} property.
   * Unique name of the point on the device.
   * @see #pointId
   */
  public void setPointId(String v) { setString(pointId, v, null); }

  //endregion Property "pointId"

  //region Property "writeHandler"

  /**
   * Slot for the {@code writeHandler} property.
   * @see #getWriteHandler
   * @see #setWriteHandler
   */
  public static final Property writeHandler = newProperty(0, new BWriteWorker(), null);

  /**
   * Get the {@code writeHandler} property.
   * @see #writeHandler
   */
  public BWriteWorker getWriteHandler() { return (BWriteWorker)get(writeHandler); }

  /**
   * Set the {@code writeHandler} property.
   * @see #writeHandler
   */
  public void setWriteHandler(BWriteWorker v) { set(writeHandler, v, null); }

  //endregion Property "writeHandler"

  //region Action "postWrite"

  /**
   * Slot for the {@code postWrite} action.
   * @see #postWrite()
   */
  public static final Action postWrite = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code postWrite} action.
   * @see #postWrite
   */
  public void postWrite() { invoke(postWrite, null, null); }

  //endregion Action "postWrite"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnvCtrlPointProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the parent PointDeviceExt type this proxy
   * extension belongs under (and by deduction which
   * device and network).
   */
  public Type getDeviceExtType()
  {
    return BEnvCtrlPointDeviceExt.TYPE;
  }

  /**
   * Return if this proxy point is readonly, readWrite or writeonly.
   */
  public BReadWriteMode getMode()
  {
    return BReadWriteMode.readWrite;
  }

  /**
   * This callback is made when the point enters a subscribed
   * state based on the current status and tuning.  The driver
   * should register for changes or begin polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.  The result of reads should be to call the
   * readOk() or readFail() method.
   */
  public void readSubscribed(Context cx)
    throws Exception
  {
    //subscribe to our network's poll scheduler
    BEnvCtrlDeviceNetwork network = (BEnvCtrlDeviceNetwork)getNetwork();
    network.getPollScheduler().subscribe((BIEnvCtrlPollable)this);

    //force data poll and update
    poll();
  }

  /**
   * This callback is made when the point exits the subscribed
   * state based on the current status and tuning.  The driver
   * should unregister for changes or cease polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.
   */
  public void readUnsubscribed(Context cx)
    throws Exception
  {
    //unsubscribe from the poll scheduler
    BEnvCtrlDeviceNetwork network = (BEnvCtrlDeviceNetwork)getNetwork();
    network.getPollScheduler().unsubscribe((BIEnvCtrlPollable)this);

  }

  /**
   * The poll() callback method called from BBasicPollScheduler
   * when it is time to poll this object.
   */
  public void poll()
  {
    // if our previous read attempt failed, call reset
    if (getStatus() == BStatus.fault)
    {
      readReset();
    }

    //the name of the point we're searching for
    String pointName = getPointId();

    //create our read request 
    int deviceId = ((BEnvCtrlDevice)getDevice()).getDeviceId();
    String request = "get " + deviceId;

    //send request to remote network
    BEnvCtrlDeviceNetwork network = (BEnvCtrlDeviceNetwork)getNetwork();
    String response = network.sendRequest(request);

    //check if we get a "failed" response
    if (response.indexOf("get fail") >= 0)
    {
      readFail(response);
      return;
    }

    //parse the response value for our point
    String[] pointValues = TextUtil.split(response, '(');
    for (int i = 1; i < pointValues.length; i++)
    {
      String value = pointValues[i];

      //remove trailing ')' character
      char[] data = new char[value.length() - 1];
      value.getChars(0, value.length() - 1, data, 0);

      String dataStr = new String(data);
      String[] pointData = TextUtil.split(dataStr, ',');
      if (pointData[0].toLowerCase().equals(pointName.toLowerCase()))
      {
        //if we've made it this far, our status should be OK
        BStatus status = BStatus.ok;

        //dealing with numeric data type
        if (getParentPoint().getOutStatusValue() instanceof BStatusNumeric)
        {
          readOk(new BStatusNumeric(Double.parseDouble(pointData[2]), status));
        }
        //dealing with boolean data type
        else if (getParentPoint().getOutStatusValue() instanceof BStatusBoolean)
        {
          readOk(new BStatusBoolean(Boolean.valueOf(pointData[2]).booleanValue(), status));
        }
        //dealing with string data type
        else if (getParentPoint().getOutStatusValue() instanceof BStatusString)
        {
          readOk(new BStatusString(pointData[2], status));
        }
        //dealing with enumerated date type
        else if (getParentPoint().getOutStatusValue() instanceof BStatusEnum)
        {
          readOk(new BStatusEnum(BDynamicEnum.make(0), status));
        }
      }
    }
  }

  /**
   * This callback is made when a write is desired based on the
   * current status and tuning.  The value to write is the current
   * value of the writeValue property.  Any IO should be done
   * asynchronously on another thread - never block the calling
   * thread.  If the write is enqueued then return true and call
   * writeOk() or writeFail() once it has been processed.  If the
   * write is canceled immediately for other reasons then return false.
   *
   * @return true if a write is now pending
   */
  public boolean write(Context cx)
    throws Exception
  {
    postWrite();
    return false;
  }

/////////////////////////////////////////////////////////////////
//  Actions
/////////////////////////////////////////////////////////////////

  /**
   * This method is invoked asynchronously to handle a write request
   * to the remote network. This method updates the framework with
   * the success of our request by calling writeOk() or writeFail()
   */
  public void doPostWrite()
  {
    if (getStatus() == BStatus.fault)
    {
      writeReset();
    }

    //get the value that we want to write 
    int deviceId = ((BEnvCtrlDevice)getDevice()).getDeviceId();
    String pointId = getPointId();

    //get the output value
    BStatusValue outStatusValue = getWriteValue();
    BValue value = outStatusValue.getValueValue();

    //create the message request
    String request = "set " + deviceId + " " + pointId + " " + value.toString();

    //send request over network
    BEnvCtrlDeviceNetwork network = (BEnvCtrlDeviceNetwork)getNetwork();
    String response = network.sendRequest(request);

    //check if the response starts with "set fail"
    boolean success = response.indexOf("set fail") < 0;
    if (!success)
    {
      writeFail(response);
    }
    else
    {
      writeOk((BStatusValue)outStatusValue.newCopy());
    }
  }

  /*
   * (non-Javadoc)
   * @see javax.baja.sys.BComponent#post(javax.baja.sys.Action, javax.baja.sys.BValue, javax.baja.sys.Context)
   */
  public IFuture post(Action action, BValue argument, Context cx)
  {
    Invocation work = new Invocation(this, action, argument, cx);
    getWriteHandler().postWork(work);
    return null;
  }
}
