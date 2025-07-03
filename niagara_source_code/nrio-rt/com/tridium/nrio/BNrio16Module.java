/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.control.BControlPoint;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.log.Log;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BStruct;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.nrio.components.BIOutputDefaultValues;
import com.tridium.nrio.components.BNrio16Status;
import com.tridium.nrio.components.BOutputDefaultValues;
import com.tridium.nrio.components.BOutputFailsafeConfig;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.messages.NrIo16WriteConfigMessage;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.messages.ReadDefaultOutputStateMessage;
import com.tridium.nrio.messages.ReadInfoMemoryMessage;
import com.tridium.nrio.messages.ReadScaleOffsetMessage;
import com.tridium.nrio.messages.WriteIOStateMapStart;
import com.tridium.nrio.messages.WriteIoMessage;
import com.tridium.nrio.messages.WriteOutputConfigMessage;
import com.tridium.nrio.points.BNrio16Points;
import com.tridium.nrio.points.BNrio16ProxyExt;
import com.tridium.nrio.points.BNrioPointDeviceExt;
import com.tridium.nrio.points.BNrioRelayOutputProxyExt;
import com.tridium.nrio.points.BNrioVoltageOutputProxyExt;
import com.tridium.nrio.points.BUiProxyExt;


/**
 * BNrio16Module represents a Nrio IO Module
 *
 * @author    Andy Saunders
 * @creation  12 Jan 06
 * @version   $Revision$ $Date: 8/29/2005 10:21:10 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "deviceType",
  type = "BNrioDeviceTypeEnum",
  defaultValue = "BNrioDeviceTypeEnum.io16",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "outputDefaultValues",
  type = "BComponent",
  defaultValue = "new BOutputDefaultValues()"
)
@NiagaraProperty(
  name = "points",
  type = "BNrioPointDeviceExt",
  defaultValue = "new BNrio16Points()",
  override = true
)
/*
 last io status message received
 */
@NiagaraProperty(
  name = "ioStatus",
  type = "BStruct",
  defaultValue = "new BNrio16Status()",
  override = true
)
@NiagaraAction(
  name = "writeConfig",
  flags = Flags.ASYNC | Flags.HIDDEN
)
@NiagaraAction(
  name = "writeIo",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "clearTotals"
)
@NiagaraAction(
  name = "readScaleOffset",
  returnType = "BComponent",
  flags = Flags.ASYNC | Flags.HIDDEN
)
@NiagaraAction(
  name = "writeOutputDefaultInfo",
  flags = Flags.ASYNC | Flags.HIDDEN
)
@NiagaraAction(
  name = "readOutputDefaultInfo",
  returnType = "BComponent",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public class BNrio16Module
extends BNrioDevice
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrio16Module(1154934004)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(Flags.READONLY, BNrioDeviceTypeEnum.io16, null);

  //endregion Property "deviceType"

  //region Property "outputDefaultValues"

  /**
   * Slot for the {@code outputDefaultValues} property.
   * @see #getOutputDefaultValues
   * @see #setOutputDefaultValues
   */
  public static final Property outputDefaultValues = newProperty(0, new BOutputDefaultValues(), null);

  /**
   * Get the {@code outputDefaultValues} property.
   * @see #outputDefaultValues
   */
  public BComponent getOutputDefaultValues() { return (BComponent)get(outputDefaultValues); }

  /**
   * Set the {@code outputDefaultValues} property.
   * @see #outputDefaultValues
   */
  public void setOutputDefaultValues(BComponent v) { set(outputDefaultValues, v, null); }

  //endregion Property "outputDefaultValues"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BNrio16Points(), null);

  //endregion Property "points"

  //region Property "ioStatus"

  /**
   * Slot for the {@code ioStatus} property.
   * last io status message received
   * @see #getIoStatus
   * @see #setIoStatus
   */
  public static final Property ioStatus = newProperty(0, new BNrio16Status(), null);

  //endregion Property "ioStatus"

  //region Action "writeConfig"

  /**
   * Slot for the {@code writeConfig} action.
   * @see #writeConfig()
   */
  public static final Action writeConfig = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code writeConfig} action.
   * @see #writeConfig
   */
  public void writeConfig() { invoke(writeConfig, null, null); }

  //endregion Action "writeConfig"

  //region Action "writeIo"

  /**
   * Slot for the {@code writeIo} action.
   * @see #writeIo()
   */
  public static final Action writeIo = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code writeIo} action.
   * @see #writeIo
   */
  public void writeIo() { invoke(writeIo, null, null); }

  //endregion Action "writeIo"

  //region Action "clearTotals"

  /**
   * Slot for the {@code clearTotals} action.
   * @see #clearTotals()
   */
  public static final Action clearTotals = newAction(0, null);

  /**
   * Invoke the {@code clearTotals} action.
   * @see #clearTotals
   */
  public void clearTotals() { invoke(clearTotals, null, null); }

  //endregion Action "clearTotals"

  //region Action "readScaleOffset"

  /**
   * Slot for the {@code readScaleOffset} action.
   * @see #readScaleOffset()
   */
  public static final Action readScaleOffset = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code readScaleOffset} action.
   * @see #readScaleOffset
   */
  public BComponent readScaleOffset() { return (BComponent)invoke(readScaleOffset, null, null); }

  //endregion Action "readScaleOffset"

  //region Action "writeOutputDefaultInfo"

  /**
   * Slot for the {@code writeOutputDefaultInfo} action.
   * @see #writeOutputDefaultInfo()
   */
  public static final Action writeOutputDefaultInfo = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code writeOutputDefaultInfo} action.
   * @see #writeOutputDefaultInfo
   */
  public void writeOutputDefaultInfo() { invoke(writeOutputDefaultInfo, null, null); }

  //endregion Action "writeOutputDefaultInfo"

  //region Action "readOutputDefaultInfo"

  /**
   * Slot for the {@code readOutputDefaultInfo} action.
   * @see #readOutputDefaultInfo()
   */
  public static final Action readOutputDefaultInfo = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code readOutputDefaultInfo} action.
   * @see #readOutputDefaultInfo
   */
  public BComponent readOutputDefaultInfo() { return (BComponent)invoke(readOutputDefaultInfo, null, null); }

  //endregion Action "readOutputDefaultInfo"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio16Module.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Route async actions
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(writeConfig) ||
        action.equals(readScaleOffset) ||
        action.equals(writeOutputDefaultInfo) ||
        action.equals(readOutputDefaultInfo))
    {
      return postAsync(new Invocation(this, action,arg, cx));
    }
    return super.post(action, arg, cx);
  }

  protected IFuture postWriteConfig(BValue arg, Context cx)
  {
    return this.postAsync(new Invocation(this, writeConfig, arg, cx));
  }

  public void started()
  throws Exception
  {
    super.started();
    if(Sys.atSteadyState())
      initDefaultValueSupport();
    if(getAddress() != 0 )
      return;
    BDeviceNetwork network = getNetwork();
    if(network != null &&
        network instanceof BM2mIoNetwork )
    {
      ((BM2mIoNetwork)network).doSubmitDeviceDiscoveryJob();
    }
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if( ! isRunning() || (cx != null && cx.equals(Context.decoding)) )
      return;
    if(p.equals(outputDefaultValues))
    {
      postWriteOutputDefaults();
    }
    else if(p.equals(status))
    {
      if(isDown() || isDisabled())
      {
        forceWrite = true;
        // clear lastWriteData
        for(int i = 0; i<lastWriteData.length; i++)
          lastWriteData[i] = (byte)~lastWriteData[i];
      }
      else
      {
        this.postWriteOutputDefaults();
      }
    }
    else if(p.equals(installedVersion))
    {
      initDefaultValueSupport();
    }
  }

  public void initDefaultValueSupport()
  {
    float version = 0.0f;
    try{ version = Float.parseFloat(getInstalledVersion()); }
    catch (Exception ignore) {}
    if(version != 0.0 )
    {
      setDefaultOutputSupport(version >= 1.35f);
      if(isDefaultOutputSupport())
      {
        setFlags(outputDefaultValues, getFlags(outputDefaultValues) & ~Flags.HIDDEN);
//        postWriteOutputDefaults();
      }
      else
      {
        setFlags(outputDefaultValues, getFlags(outputDefaultValues) | Flags.HIDDEN);
      }
    }
  }

  public BComponent doReadScaleOffset()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    ReadScaleOffsetMessage cReq = new ReadScaleOffsetMessage(getAddress());
    NrioMessage cRsp = network.sendNrioMessage(cReq);
    if(getConfigLog().isTraceOn())
    {
      byte[] bytes = cReq.getByteArray();
      getConfigLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      bytes = cRsp.getByteArray();
      getConfigLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
    }
    if(cRsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      return null;
    BComponent info = new BComponent();
    byte[] infoData = cRsp.getData();
    NrioInputStream in = new NrioInputStream(infoData);
    for(int i = 1; i < 9; ++i)
    {
      info.add(("uiResitive" + i + "scale"), BInteger.make(in.readInt()));
      info.add(("uiResitive" + i + "offset"), BInteger.make(in.readInt()));
    }
    for(int i = 1; i < 9; ++i)
    {
      info.add(("uiPlat" + i + "scale"), BInteger.make(in.readInt()));
      info.add(("uiPlat" + i + "offset"), BInteger.make(in.readInt()));
    }
    for(int i = 1; i < 9; ++i)
    {
      info.add(("uiVolts" + i + "scale"), BInteger.make(in.readInt()));
      info.add(("uiVolts" + i + "offset"), BInteger.make(in.readInt()));
    }
    for(int i = 1; i < 5; ++i)
    {
      info.add(("aoVolts" + i + "scale"), BInteger.make(in.readInt()));
      info.add(("aoVolts" + i + "offset"), BInteger.make(in.readInt()));
    }
    return info;

  }

  public void doClearTotals()
  {
    ((BNrio16Status)getIoStatus()).doClearTotals();
  }

  public void doWriteConfig()
  {
    if(isDisabled() || isDown() || firstPing)
      return;
    sendWriteConfig();
//  if(sendWriteConfig() == NrioMessageConst.MESSAGE_STATUS_OK)
//  System.out.println("write config was OK");
  }

  public int sendWriteConfig()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    NrIo16WriteConfigMessage cReq = new NrIo16WriteConfigMessage(getAddress(), getUiConfigBytes());
    NrioMessage cRsp = network.sendNrioMessage(cReq);
    if(getConfigLog().isTraceOn())
    {
      byte[] bytes = cReq.getByteArray();
      getConfigLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      bytes = cRsp.getByteArray();
      getConfigLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
    }

    return cRsp.getStatus();
  }

  /**
   * Return true if this device is in the process of writing the output default values to this device.
   * This is a two message sequence.  A true indicates that the first message has been sent but the
   * second message has not been sent.
   */
  public boolean isWriteOutputDefaultsInProgress()
  {
    return isWriteDefaultOutputInProcess;
  }

  public void doWriteOutputDefaultInfo()
  {
    if(isDisabled() || isDown())
      return;
    try
    {
      if(!isDefaultOutputSupport())
        return;
      getConfigLog().trace(getName() + ": writeOutputDefaults");
      BNrioNetwork network = (BNrioNetwork)getNetwork();
//      System.out.println(getAddress() + ": disablePolling");
      doDisablePolling();
      setFirstPing(false);
      isWriteDefaultOutputInProcess = true;
      WriteIOStateMapStart req = new WriteIOStateMapStart(getAddress());
      NrioMessage rsp = network.sendNrioMessage(req);
      if(getConfigLog().isTraceOn())
      {
        byte[] bytes = req.getByteArray();
        getConfigLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
        bytes = rsp.getByteArray();
        getConfigLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      }
      if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      {
        getConfigLog().trace("WriteIOStateMapStart pri response error: " + rsp.getStatus());
      }
      BOutputFailsafeConfig failsafeConfig = network.getOutputFailsafeConfig();
//      System.out.println(getAddress() + ": WriteOutputConfigMessage");
      WriteOutputConfigMessage wrReq = new WriteOutputConfigMessage(getAddress(), failsafeConfig.getStartupTimeout(), failsafeConfig.getCommLossTimeout(), (BIOutputDefaultValues)getOutputDefaultValues(), true);
      NrioMessage wrRsp = network.sendNrioMessage(wrReq);
      if(getConfigLog().isTraceOn())
      {
        byte[] bytes = wrReq.getByteArray();
        getConfigLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
        bytes = wrRsp.getByteArray();
        getConfigLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      }
      if(wrRsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      {
        getConfigLog().trace("WriteOutputConfigMessage primary response error: " + wrRsp.getStatus());
      }
      doEnablePolling();
    }
    catch(Exception e)
    {
      getConfigLog().trace("doWriteOutputDefaultInfo caught exception: " + e);
    }
    isWriteDefaultOutputInProcess = false;
  }

  public BComponent doReadOutputDefaultInfo()
  {
    try
    {
      BNrioNetwork network = (BNrioNetwork)getNetwork();
      ReadDefaultOutputStateMessage req = new ReadDefaultOutputStateMessage(getAddress());
      NrioMessage rsp = network.sendNrioMessage(req);
      if(getConfigLog().isTraceOn())
      {
        byte[] bytes = req.getByteArray();
        getConfigLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
        bytes = rsp.getByteArray();
        getConfigLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      }
      if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      {
        return null;
      }
      byte[] infoData = rsp.getData();
      BOutputDefaultValues defaultValues = BOutputDefaultValues.makeFromBytes(infoData);;
      return defaultValues;
    }
    catch(Exception e)
    {
      getConfigLog().trace("doReadOutputDefaultInfo caught exception: " + e);
    }
    return null;
  }

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    Property[] props = null;
    if(!isDualModule())
    {
      BComponent priInfo = doReadInfoMemory();
      out.startProps("CPU Info Memory");
      props = priInfo.getPropertiesArray();
      for (int i = 0; i < props.length; ++i)
      {
        Property p = props[i];
        out.prop(p.getName(), priInfo.get(p));
      }
      out.endProps();
    }
    out.startProps("Output Write Values");
    out.prop("wrDoValue", "0b"+Integer.toBinaryString(wrDoValue));

    for (int i = 0; i < wrAoValue.length; ++i)
    {
      out.prop("wrAoValue", "0x"+Integer.toHexString(wrAoValue[i]));
    }
    out.endProps();

    BComponent info = doReadScaleOffset();
    out.startProps("Calibration Info");
    if(info != null)
    {
      for (Property property : info.getProperties())
      {
        out.prop(property.getName(), info.get(property));
      }
    }
    out.endProps();



    BComponent defaultValues = doReadOutputDefaultInfo();
    if(defaultValues == null)
    {
      return;
    }
    out.startProps("Output Default Value Info");
    props = defaultValues.getPropertiesArray();
    for (int i = 0; i < props.length; ++i)
    {
      out.prop(props[i].getName(), defaultValues.get(props[i]));
    }
    out.endProps();

  }

  public BComponent doReadInfoMemory()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    BComponent info = new BComponent();
    try
    {
      ReadInfoMemoryMessage req = new ReadInfoMemoryMessage(getAddress());
      NrioMessage rsp = network.sendNrioMessage(req);

      if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      {
        return info;
      }
      byte[] infoData = rsp.getData();
      info.add("nodeAddr"             , BInteger.make(infoData[0] & 0x0ff));
      info.add("numMsgsOurAddr"       , BInteger.make(infoData[1] & 0x0ff));
      info.add("numMsgsRcvd"          , BInteger.make(infoData[2] & 0x0ff));
      info.add("numBadMsgsRcvd"       , BInteger.make(infoData[3] & 0x0ff));
      info.add("numMsgsTransmitted"   , BInteger.make(infoData[4] & 0x0ff));
      info.add("numIOStatusCrcErrors" , BInteger.make(infoData[5] & 0x0ff));
//      info.add("numCardReadsProcessed", BInteger.make(infoData[6] & 0x0ff));
//      info.add("numCardReadsXmitted"  , BInteger.make(infoData[7] & 0x0ff));
//      info.add("numCardReadErrors"    , BInteger.make(infoData[8] & 0x0ff));
      info.add("num485ResetsOnRcv"    , BInteger.make(infoData[9] & 0x0ff));
      info.add("lastResetState"       , BInteger.make(infoData[10] & 0x0ff));
    }
    catch(Exception e)
    {
      network.getLog().message(getName() + ": doReadInfoMemory caught exception: " + e);
    }
    return info;
  }


  public void postWriteOutputDefaults()
  {
    if(isDisabled() || isDown() || !isDefaultOutputSupport())
      return;
    getConfigLog().trace(getName() + ": writeOutputDefaults posted: will write in 5 seconds");
    if(wrOutputConfigTicket != null)
      wrOutputConfigTicket.cancel();
    wrOutputConfigTicket = Clock.schedule(this, BRelTime.makeSeconds(5), writeOutputDefaultInfo, null);
  }

  public BControlPoint checkForProxyExtConflicts(BControlPoint sourcePoint)
  {
    BNrio16ProxyExt sourceProxy = (BNrio16ProxyExt)sourcePoint.getProxyExt();
    BControlPoint[] cps = getPoints().getPoints();
    for(int i = 0; i < cps.length; i++)
    {
      if(cps[i].equals(sourcePoint))
        continue;
      BNrio16ProxyExt testProxy = (BNrio16ProxyExt)cps[i].getProxyExt();
      if(!testProxy.getEnabled())
        continue;
      boolean sameInstance = testProxy.getInstance() == sourceProxy.getInstance();
      if( testProxy instanceof BUiProxyExt   &&
          sourceProxy instanceof BUiProxyExt &&
          sameInstance )
        return cps[i];
      if( testProxy instanceof BNrioVoltageOutputProxyExt &&
          sourceProxy instanceof BNrioVoltageOutputProxyExt &&
          sameInstance )
        return cps[i];
      if( testProxy instanceof BNrioRelayOutputProxyExt &&
          sourceProxy instanceof BNrioRelayOutputProxyExt &&
          sameInstance )
        return cps[i];
    }
    return null;
  }

  public void initLastWrite()
  {
    for(int i = 0; i<lastWriteData.length; i++)
      lastWriteData[i] = (byte)0xff;
  }

  public int setAoValue(int value, int instance)
  {
    if(instance <= 0 || instance > 4)
      return 1;
    wrAoValue[instance-1] = value;
    //((BNrioNetwork)getNetwork()).interruptWriteThread();
    return 0;
  }

  public int setDoValue(boolean value, int instance)
  {
    if(instance <= 0)
      return 1;
    int mask = 0x0001 << (instance-1);
    setWrDoValue(value,mask);
    //return doWriteDoValues();
    return 0;
  }

  public synchronized void setWrDoValue(boolean value, int mask)
  {
    if(value)
      wrDoValue = wrDoValue | mask;
    else
      wrDoValue = wrDoValue & ~mask;
    //((BNrioNetwork)getNetwork()).interruptWriteThread();
  }

  public int doWriteDoValues()
  {
    return doWriteIoValues();
  }

  public void atSteadyState()
  {
    initDefaultValueSupport();
    forceWrite = true;
//    doWriteDoValues();
  }

  public void doWriteIo()
  {
//    lastWriteData[0] = (byte)~wrDoValue;
    forceWrite = true;
//    doWriteIoValues();
  }
  public int doWriteIoValues()
  {
    if(isDown() || this.isFault() || isDisabled())
    {
      return 0;
    }
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    byte[] thisWrite = getIoData();
    if(forceWrite || hasWrDataChanged(thisWrite))
    {
      if(isWriteOutputDefaultsInProgress())
      {
        getConfigLog().trace(getAddress() + ": WriteOutputDefaultsInProgress blocking nrio msgType: 0x9" );
        return 0;
      }
      forceWrite = false;
//      System.out.println("Thread: " + Thread.currentThread().getName() + " device: " + getAddress() + " write: " + Integer.toHexString(wrDoValue));
      WriteIoMessage req = new WriteIoMessage(getAddress(), getIoData() );
      NrioMessage rsp = network.sendNrioMessage(req);
      if(getWrIoLog().isTraceOn())
      {
        byte[] bytes = req.getByteArray();
        getWrIoLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
        bytes = rsp.getByteArray();
        getWrIoLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      }

      if( rsp != null && rsp.getStatus() == 0)
      {
        if(!getFirstPing())
          pingOk();
        for(int i = 0; i < lastWriteData.length; i++)
          lastWriteData[i] = thisWrite[i];
      }
      return rsp.getStatus();
    }

    return 0;
  }

  /**
   * Return the boolean array that indiates how many relays are supported and which are
   *   currently assigned.  A true value will indicate that a relay is assigned.
   */
  public BOrd[] getUsedRelayArray()
  {
    return getUsedRelayArray(8);
  }

  /**
   * Return the boolean array that indiates how many digital inputs are supported and which are
   *   currently assigned.  A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedDiArray()
  {
    return getUsedDiArray(2);
  }

  /**
   * Return the boolean array that indiates how many supervised digital inputs are supported
   *    and which are currently assigned.
   *    A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedSdiArray()
  {
    return getUsedSdiArray(8);
  }


  // must be overridden by subclasses
  public void updateProxyValues()
  {
    byte[] statusBytes = ((BNrio16Status)getIoStatus()).copyBytes();
    if(statusBytes.length < 12)
      return;
    NrioMessage statusMsg = new NrioMessage();
    statusMsg.decodeFromBytes(statusBytes, statusBytes.length);

    getPoints().setDynamicPoints();
//  ((BNrio16Points)getPoints()).setIoStatus(new NrIo16IOStatus(statusMsg.getData()));
  }

  public String toString(Context cx)
  {
    StringBuilder b = new StringBuilder();
//     b.append(getStatus().toString()).append(" ");
//     BNrioIOPoints points = (BNrioIOPoints) getPoints();
//     if(points.getDi1().getBoolean())   b.append("Di1 ");
//     if(points.getDi2().getBoolean())   b.append("Di2 ");
//     if(points.getSdi1().getBoolean())  b.append("Sdi1 ");
//     if(points.getSdi2().getBoolean())  b.append("Sdi2 ");
//     if(points.getSdi3().getBoolean())  b.append("Sdi3 ");
//     if(points.getSdi4().getBoolean())  b.append("Sdi4 ");
//     if(points.getSdi5().getBoolean())  b.append("Sdi5 ");
//     if(points.getSdi6().getBoolean())  b.append("Sdi6 ");
//     if(points.getSdi7().getBoolean())  b.append("Sdi7 ");
//     if(points.getSdi8().getBoolean())  b.append("Sdi8 ");

    return b.toString();
  }

  public void setUiConfig(int instance, int uiType)
  {
    switch(instance-1)
    {
      case 0: uiConfig[0] = (byte) ((uiConfig[0] & 0x0f) | ((uiType & 0x0f) << 4)); break;
      case 1: uiConfig[0] = (byte) ((uiConfig[0] & 0xf0) | ((uiType & 0x0f)     )); break;
      case 2: uiConfig[1] = (byte) ((uiConfig[1] & 0x0f) | ((uiType & 0x0f) << 4)); break;
      case 3: uiConfig[1] = (byte) ((uiConfig[1] & 0xf0) | ((uiType & 0x0f)     )); break;
      case 4: uiConfig[2] = (byte) ((uiConfig[2] & 0x0f) | ((uiType & 0x0f) << 4)); break;
      case 5: uiConfig[2] = (byte) ((uiConfig[2] & 0xf0) | ((uiType & 0x0f)     )); break;
      case 6: uiConfig[3] = (byte) ((uiConfig[3] & 0x0f) | ((uiType & 0x0f) << 4)); break;
      case 7: uiConfig[3] = (byte) ((uiConfig[3] & 0xf0) | ((uiType & 0x0f)     )); break;
    }
    if(isRunning())
      writeConfig();
  }

  public byte[] getUiConfigBytes()
  {
    return uiConfig;
  }

  public byte[] getIoData()
  {
    byte[] ioData = new byte[7];
    ioData[0] = (byte)wrDoValue;
    for(int i = 0; i < NUM_AOS; i++)
    {
      int rawValue = getAoRawValue(i);
      switch(i)
      {
        case 0:
          ioData[1] = (byte)((rawValue >> 4) & 0x0ff);
          ioData[2] = (byte)((rawValue & 0x0f) << 4);
          break;
        case 1:
          ioData[2] = (byte)((ioData[2] | ((rawValue >> 8) & 0x0f)));
          ioData[3] = (byte)((rawValue     ) & 0x0ff);
          break;
        case 2:
          ioData[4] = (byte)((rawValue >> 4) & 0x0ff);
          ioData[5] = (byte)((rawValue & 0x0f) << 4);
          break;
        case 3:
          ioData[5] = (byte)((ioData[5] | ((rawValue >> 8) & 0x0f)));
          ioData[6] = (byte)((rawValue     ) & 0x0ff);
          break;

      }
    }
    return ioData;
  }

  private int getAoRawValue(int instance)
  {
    int retValue = wrAoValue[instance];
    if( retValue > 4095)
      retValue = 4095;
    if( retValue < 0)
      retValue = 0;
    return retValue;
  }

  private boolean hasWrDataChanged(byte[] thisWrite)
  {
    if(thisWrite.length != lastWriteData.length)
      return true;
    for(int i = 0; i < thisWrite.length; i++)
    {
      if(thisWrite[i] != lastWriteData[i])
        return true;
    }
    return false;
  }

  public Log getConfigLog()
  {
    return Log.getLog(getNetwork().getName() + ".config");
  }
  public Log getWrIoLog()
  {
    return Log.getLog(getNetwork().getName() + ".wrIo");
  }

  public boolean isDefaultOutputSupport() {return defaultOutputSupport;}
  public void setDefaultOutputSupport(boolean value) { defaultOutputSupport = value;}

  private int wrDoValue = 0;
  private int[] wrAoValue = new int[NUM_AOS];
  private byte[] lastWriteData = new byte[7];
  private byte[] uiConfig = { (byte)0x11, 
      (byte)0x11,
      (byte)0x11,
      (byte)0x11 };

  protected boolean defaultOutputSupport = false;
  protected boolean forceWrite = true;
  protected boolean isWriteDefaultOutputInProcess = false;
  protected static byte[] NO_DATA = new byte[0];
  protected static final int NUM_AOS = 4;
  protected static final int NUM_UIS = 8;
  protected static final int NUM_DOS = 5;

  private Clock.Ticket wrOutputConfigTicket;


}
