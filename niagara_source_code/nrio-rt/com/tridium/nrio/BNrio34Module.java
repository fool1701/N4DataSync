/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import static com.tridium.nrio.messages.NrioMessageConst.REMOTE_IO_34_PRI;
import static com.tridium.nrio.messages.NrioMessageConst.REMOTE_IO_34_SEC;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BLink;
import javax.baja.sys.BString;
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
import com.tridium.nrio.components.BIo34OutputDefaultValues;
import com.tridium.nrio.components.BNrioLearnDeviceEntry;
import com.tridium.nrio.components.BOutputDefaultValues;
import com.tridium.nrio.components.BOutputFailsafeConfig;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.messages.ReadDefaultOutputStateMessage;
import com.tridium.nrio.messages.SetLogicalAddressMessage;
import com.tridium.nrio.messages.WriteIOStateMapStart;
import com.tridium.nrio.messages.WriteOutputConfigMessage;
import com.tridium.nrio.util.DualModuleUtils;


/**
 * BNrio34Module represents a Nrio IO34 Module
 *
 * @author    Andy Saunders
 * @creation  7 Oct 16
 */

@NiagaraType
@NiagaraProperty(
  name = "outputDefaultValues",
  type = "BComponent",
  defaultValue = "new BIo34OutputDefaultValues()",
  override = true
)
@NiagaraProperty(
  name = "io34Sec",
  type = "BNrio34SecModule",
  defaultValue = "new BNrio34SecModule()",
  flags = Flags.HIDDEN
)
public class BNrio34Module
extends BNrio34PriModule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrio34Module(3714890940)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "outputDefaultValues"

  /**
   * Slot for the {@code outputDefaultValues} property.
   * @see #getOutputDefaultValues
   * @see #setOutputDefaultValues
   */
  public static final Property outputDefaultValues = newProperty(0, new BIo34OutputDefaultValues(), null);

  //endregion Property "outputDefaultValues"

  //region Property "io34Sec"

  /**
   * Slot for the {@code io34Sec} property.
   * @see #getIo34Sec
   * @see #setIo34Sec
   */
  public static final Property io34Sec = newProperty(Flags.HIDDEN, new BNrio34SecModule(), null);

  /**
   * Get the {@code io34Sec} property.
   * @see #io34Sec
   */
  public BNrio34SecModule getIo34Sec() { return (BNrio34SecModule)get(io34Sec); }

  /**
   * Set the {@code io34Sec} property.
   * @see #io34Sec
   */
  public void setIo34Sec(BNrio34SecModule v) { set(io34Sec, v, null); }

  //endregion Property "io34Sec"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio34Module.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Route async actions
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if ( action.equals(readOutputDefaultInfo) ||
         action.equals(writeOutputDefaultInfo) ||
         action.equals(setAddressAndPing)
       )
      return this.postAsync(new Invocation(this, action, arg, cx));
    return super.post(action, arg, cx);
  }

  public boolean isDualModule()
  {
    return true;
  }

  public boolean isFirmwareUptodate()
  {
    return super.isFirmwareUptodate() && getIo34Sec().isFirmwareUptodate();
  }

  public void doEnablePolling()
  {
    super.doEnablePolling();
    getIo34Sec().siblingEnablePolling();
  }

  public void doDisablePolling()
  {
    super.doDisablePolling();
    getIo34Sec().siblingDisablePolling();
  }


  public void doPing()
  {
    if(isDisabled())
      return;
    boolean needsInitialized = isDown() || firstPing;
    if(DualModuleUtils.sendPing(this) != NrioMessageConst.MESSAGE_STATUS_OK)
      return;
    if(DualModuleUtils.sendPing(getIo34Sec()) != NrioMessageConst.MESSAGE_STATUS_OK)
    {
      ((BNrioNetwork)getNetwork()).sendReset(getAddress());
      return;
    }
    try{Thread.sleep(100);}
    catch(Exception ignore) {}

    if(needsInitialized)
    {
      try{Thread.sleep(1000);}
      catch(Exception ignore) {}
    }
    if(DualModuleUtils.processPostPing(this, needsInitialized))
    {
      DualModuleUtils.processPostPing(getIo34Sec(), needsInitialized);
    }

  }

  public void doSetAddressAndPing()
  {
    final BNrioNetwork network = (BNrioNetwork)getNetwork();
    int priLogicalAddress = getAddress();
    int secLogicalAddress = getIo34Sec().getAddress();
    byte[] uid = getUid().copyBytes();
    addressAndPing(network, secLogicalAddress, uid, REMOTE_IO_34_SEC);
    addressAndPing(network, priLogicalAddress, uid, REMOTE_IO_34_PRI);
    doPing();
  }

  private void addressAndPing(BNrioNetwork network, int address, byte[] uid, int type)
  {
    SetLogicalAddressMessage setAddrMsg = new SetLogicalAddressMessage( address, uid, type );
    NrioMessage rsp = network.sendNrioMessage(setAddrMsg);
//    if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
//      return;
    try{Thread.sleep(20l);}catch(Exception e){}
    PingMessage pReq = new PingMessage(address, uid, type);
    PingResponse pRsp = (PingResponse)(network.sendNrioMessage(pReq));

    try{Thread.sleep(20l);}catch(Exception e){}

  }

  public void readBuildInfo()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    network.getLog().trace(">>> BNrioDevice(" + getName() + ").readBuildInfo");
    String version = network.readBuildInfo(getAddress());
    network.getLog().trace(">>> BNrioDevice(" + getName() + ").device version =" + version);
    setInstalledVersion(version);
    final BNrio34SecModule io34Sec = getIo34Sec();
    network.getLog().trace(">>> BNrioDevice(" + io34Sec.getName() + ").readBuildInfo");
    String secVersion = network.readBuildInfo(io34Sec.getAddress());
    network.getLog().trace(">>> BNrioDevice(" + getName() + ").device version =" + secVersion);
    io34Sec.setInstalledVersion(secVersion);
  }



  public void setUiConfig(int instance, int uiType)
  {
    if(instance <= 8)
      super.setUiConfig(instance, uiType);
    else
    {
      ((BNrio16Module)getIo34Sec()).setUiConfig(instance-8, uiType);
    }
  }


  public void doWriteIo()
  {
    super.doWriteIo();
    ((BNrio16Module)getIo34Sec()).doWriteIo();
  }


  public int setAoValue(int value, int instance)
  {
    if(instance <= 0 )
      return 1;
    if(instance >  NUM_AOS)
      return ((BNrio16Module)getIo34Sec()).setAoValue(value, instance - NUM_AOS);
    return super.setAoValue(value, instance);
  }

  public int setDoValue(boolean value, int instance)
  {
    if(instance <= 0)
      return 1;
    if(instance > NUM_DOS)
      return getIo34Sec().setDoValue(value, instance-NUM_DOS);
    return super.setDoValue(value, instance);
  }

  public int doWriteDoValues()
  {
    int rtnValue = doWriteIoValues();
    return getIo34Sec().doWriteDoValues();
  }

//  public BComponent doReadScaleOffset()
//  {
//    BComponent priInfo super.doReadScaleOffset();
//    getIo34Sec().readScaleOffset();
//  }

  /**
   * Return the BNrioLearnDeviceEntry based on this device. Used by BNrioLearnDevicesJob to populate the
   * existing devices.
   */
  public BNrioLearnDeviceEntry makeLearnDeviceEntry()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getInstalledVersion());
    sb.append(";");
    sb.append(getIo34Sec().getInstalledVersion());
    int secAddr = getIo34Sec().getAddress();
    return new BNrioLearnDeviceEntry(getAddress(), getDeviceType(), getUid().copyBytes(), sb.toString(), getDisplayName(null), secAddr);
  }

  public void started()
    throws Exception
  {
    super.started();
    BValue t2102 = getNetwork().get("T2102");
    if(t2102 != null && t2102 instanceof BString)
    {
      setAvailableVersion(t2102.toString());
      getIo34Sec().setAvailableVersion(t2102.toString());
      if(isRunning())
      {
        BLink link = makeLink(getIo34Sec(), ioStatus, secIoStatus, null);
        add(null, link, Flags.READONLY | Flags.TRANSIENT);
        BLink link1 = makeLink(getIo34Sec(), installedVersion, secVersion, null);
        add(null, link1, Flags.READONLY | Flags.TRANSIENT);
      }
    }
  }

  public void stopped()
    throws Exception
  {
    getIo34Sec().stopped();
    super.stopped();
  }

  public void pingFail(String cause)
  {
    super.pingFail(cause);
    getIo34Sec().siblingPingFail(cause);
    doDisablePolling();
  }


  public void siblingPingFail(String cause)
  {
    super.pingFail(cause);
    doDisablePolling();
  }


  public void doClearInfoMemory()
  {
    super.doClearInfoMemory();
    getIo34Sec().doClearInfoMemory();
  }
  public int sendWriteConfig()
  {
    int priRtnValue = super.sendWriteConfig();
    int secRtnValue = getIo34Sec().sendWriteConfig();
    if(priRtnValue != 0)
      return priRtnValue;
    if(secRtnValue == 0)
      postWriteOutputDefaults();
    return secRtnValue;

  }

  public int sendOutputConfig()
  {
    postWriteOutputDefaults();
    return 0;
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
      getConfigLog().trace(getName() + ": writeOutputDefaults");
      BNrioNetwork network = (BNrioNetwork)getNetwork();
      BNrio34SecModule io34Sec = getIo34Sec();
//      System.out.println(getAddress() + ": disablePolling");
      doDisablePolling();
      firstPing = false;
//      System.out.println(io34Sec.getAddress() + ": disablePolling");
//      io34Sec.doDisablePolling();
      io34Sec.firstPing = false;
      isWriteDefaultOutputInProcess = true;
      try{Thread.sleep(500);}
      catch (Exception ignore){}
//      System.out.println(getAddress() + ": WriteIOStateMapStart");
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
      req = new WriteIOStateMapStart(io34Sec.getAddress());
      rsp = network.sendNrioMessage(req);
      if(getConfigLog().isTraceOn())
      {
        byte[] bytes = req.getByteArray();
        getConfigLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
        bytes = rsp.getByteArray();
        getConfigLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      }
      if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      {
        getConfigLog().trace("WriteIOStateMapStart sec response error: " + rsp.getStatus());
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
      wrReq = new WriteOutputConfigMessage(io34Sec.getAddress(), failsafeConfig.getStartupTimeout(), failsafeConfig.getCommLossTimeout(), (BIOutputDefaultValues)getOutputDefaultValues(), false);
      wrRsp = network.sendNrioMessage(wrReq);
      if(getConfigLog().isTraceOn())
      {
        byte[] bytes = wrReq.getByteArray();
        getConfigLog().trace(getName() + " request: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
        bytes = wrRsp.getByteArray();
        getConfigLog().trace(getName() + " response: " + ByteArrayUtil.toHexString(bytes, 0, bytes.length));
      }
      if(wrRsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      {
        getConfigLog().trace("WriteOutputConfigMessage secondary response error: " + wrRsp.getStatus());
      }
      doEnablePolling();
      io34Sec.doEnablePolling();
    }
    catch(Exception e)
    {
      getConfigLog().trace("doReadOutputDefaultInfo caught exception: " + e);
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
      byte[] priInfoData = rsp.getData();
      req = new ReadDefaultOutputStateMessage(getIo34Sec().getAddress());
      rsp = network.sendNrioMessage(req);
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
      byte[] secInfoData = rsp.getData();
      BIo34OutputDefaultValues defaultValues = new BIo34OutputDefaultValues(priInfoData,secInfoData);
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
    BComponent info = getIo34Sec().doReadScaleOffset();
    out.startProps("Sec Calibration Info");
    if(info != null)
    {
      for (Property property : info.getProperties())
      {
        out.prop(property.getName(), info.get(property));
      }
    }
    out.endProps();

    BComponent priInfo = doReadInfoMemory();
    BComponent secInfo = getIo34Sec().doReadInfoMemory();
    out.startProps("IO-34-Pri CPU Info Memory");
    for (Property property : priInfo.getProperties())
    {
      out.prop(property.getName(), priInfo.get(property));
    }
    out.endProps();
    out.startProps("IO-34-Sec CPU Info Memory");
    for (Property property : secInfo.getProperties())
    {
      out.prop(property.getName(), secInfo.get(property));
    }
    out.endProps();

  }


  private Clock.Ticket wrOutputConfigTicket;

}
