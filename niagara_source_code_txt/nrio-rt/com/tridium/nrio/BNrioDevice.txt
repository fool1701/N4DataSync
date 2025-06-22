/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.driver.point.BProxyExt;
import javax.baja.log.Log;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BBlob;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
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

import com.tridium.basicdriver.BBasicDevice;
import com.tridium.nrio.components.BIoStatus;
import com.tridium.nrio.components.BNrioLearnDeviceEntry;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.messages.ClearInfoMemoryMessage;
import com.tridium.nrio.messages.NrioDeviceIoStatus;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.NrioReceivedMessage;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.messages.ReadInfoMemoryMessage;
import com.tridium.nrio.messages.SetLogicalAddressMessage;
import com.tridium.nrio.points.BNrioPointDeviceExt;
import com.tridium.nrio.points.BNrioProxyExt;
import com.tridium.nrio.util.DualModuleUtils;

/**
 * BNrioDevice represents a device
 *
 * @author Andy Saunders on 21 Jan 02
 * @since Baja 1.0
 */
@NiagaraType
/*
 Device logical address
 */
@NiagaraProperty(
  name = "address",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  facets = @Facet("BFacets.makeInt(null, 0, 16)")
)
@NiagaraProperty(
  name = "deviceType",
  type = "BNrioDeviceTypeEnum",
  defaultValue = "BNrioDeviceTypeEnum.none",
  flags = Flags.READONLY
)
/*
 Device uid address
 */
@NiagaraProperty(
  name = "uid",
  type = "BBlob",
  defaultValue = "BBlob.make( defaultUid )",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"nrio:FlexBlobFE\"))")
)
/*
 Software version installed in this device.
 */
@NiagaraProperty(
  name = "installedVersion",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Software version available for this device.
 */
@NiagaraProperty(
  name = "availableVersion",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
/*
 Defines which relay output will be used to wink.
 */
@NiagaraProperty(
  name = "winkOutput",
  type = "int",
  defaultValue = "1",
  facets = @Facet("BFacets.makeInt(null, 1, 8)")
)
/*
 Defines how long the winking will last.
 */
@NiagaraProperty(
  name = "winkDuration",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(10)",
  facets = @Facet("BFacets.make(BFacets.MAX, BRelTime.makeSeconds(60), BFacets.MIN, BRelTime.makeSeconds(5) )")
)
/*
 Contains the created proxy points representing
 data values on this device
 */
@NiagaraProperty(
  name = "points",
  type = "BNrioPointDeviceExt",
  defaultValue = "new BNrioPointDeviceExt()"
)
/*
 last io status message received
 */
@NiagaraProperty(
  name = "ioStatus",
  type = "BStruct",
  defaultValue = "new BIoStatus()",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
@NiagaraAction(
  name = "upload",
  parameterType = "BUploadParameters",
  defaultValue = "new BUploadParameters()",
  flags = Flags.ASYNC | Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "download",
  parameterType = "BDownloadParameters",
  defaultValue = "new BDownloadParameters()",
  flags = Flags.ASYNC | Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "winkDevice"
)
@NiagaraAction(
  name = "winkTimeout",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "winkCancel",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "enablePolling",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "disablePolling",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "setPingOk",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "setAddressAndPing",
  flags = Flags.ASYNC | Flags.HIDDEN
)
@NiagaraAction(
  name = "dumpIoMap",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "readInfoMemory",
  returnType = "BComponent",
  flags = Flags.ASYNC | Flags.HIDDEN
)
@NiagaraAction(
  name = "clearInfoMemory",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public class BNrioDevice
extends BBasicDevice
{
  static byte[] defaultUid = { (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, };

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrioDevice(3099876049)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * Device logical address
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, 0, BFacets.makeInt(null, 0, 16));

  /**
   * Get the {@code address} property.
   * Device logical address
   * @see #address
   */
  public int getAddress() { return getInt(address); }

  /**
   * Set the {@code address} property.
   * Device logical address
   * @see #address
   */
  public void setAddress(int v) { setInt(address, v, null); }

  //endregion Property "address"

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(Flags.READONLY, BNrioDeviceTypeEnum.none, null);

  /**
   * Get the {@code deviceType} property.
   * @see #deviceType
   */
  public BNrioDeviceTypeEnum getDeviceType() { return (BNrioDeviceTypeEnum)get(deviceType); }

  /**
   * Set the {@code deviceType} property.
   * @see #deviceType
   */
  public void setDeviceType(BNrioDeviceTypeEnum v) { set(deviceType, v, null); }

  //endregion Property "deviceType"

  //region Property "uid"

  /**
   * Slot for the {@code uid} property.
   * Device uid address
   * @see #getUid
   * @see #setUid
   */
  public static final Property uid = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BBlob.make( defaultUid ), BFacets.make(BFacets.FIELD_EDITOR, BString.make("nrio:FlexBlobFE")));

  /**
   * Get the {@code uid} property.
   * Device uid address
   * @see #uid
   */
  public BBlob getUid() { return (BBlob)get(uid); }

  /**
   * Set the {@code uid} property.
   * Device uid address
   * @see #uid
   */
  public void setUid(BBlob v) { set(uid, v, null); }

  //endregion Property "uid"

  //region Property "installedVersion"

  /**
   * Slot for the {@code installedVersion} property.
   * Software version installed in this device.
   * @see #getInstalledVersion
   * @see #setInstalledVersion
   */
  public static final Property installedVersion = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code installedVersion} property.
   * Software version installed in this device.
   * @see #installedVersion
   */
  public String getInstalledVersion() { return getString(installedVersion); }

  /**
   * Set the {@code installedVersion} property.
   * Software version installed in this device.
   * @see #installedVersion
   */
  public void setInstalledVersion(String v) { setString(installedVersion, v, null); }

  //endregion Property "installedVersion"

  //region Property "availableVersion"

  /**
   * Slot for the {@code availableVersion} property.
   * Software version available for this device.
   * @see #getAvailableVersion
   * @see #setAvailableVersion
   */
  public static final Property availableVersion = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code availableVersion} property.
   * Software version available for this device.
   * @see #availableVersion
   */
  public String getAvailableVersion() { return getString(availableVersion); }

  /**
   * Set the {@code availableVersion} property.
   * Software version available for this device.
   * @see #availableVersion
   */
  public void setAvailableVersion(String v) { setString(availableVersion, v, null); }

  //endregion Property "availableVersion"

  //region Property "winkOutput"

  /**
   * Slot for the {@code winkOutput} property.
   * Defines which relay output will be used to wink.
   * @see #getWinkOutput
   * @see #setWinkOutput
   */
  public static final Property winkOutput = newProperty(0, 1, BFacets.makeInt(null, 1, 8));

  /**
   * Get the {@code winkOutput} property.
   * Defines which relay output will be used to wink.
   * @see #winkOutput
   */
  public int getWinkOutput() { return getInt(winkOutput); }

  /**
   * Set the {@code winkOutput} property.
   * Defines which relay output will be used to wink.
   * @see #winkOutput
   */
  public void setWinkOutput(int v) { setInt(winkOutput, v, null); }

  //endregion Property "winkOutput"

  //region Property "winkDuration"

  /**
   * Slot for the {@code winkDuration} property.
   * Defines how long the winking will last.
   * @see #getWinkDuration
   * @see #setWinkDuration
   */
  public static final Property winkDuration = newProperty(0, BRelTime.makeSeconds(10), BFacets.make(BFacets.MAX, BRelTime.makeSeconds(60), BFacets.MIN, BRelTime.makeSeconds(5) ));

  /**
   * Get the {@code winkDuration} property.
   * Defines how long the winking will last.
   * @see #winkDuration
   */
  public BRelTime getWinkDuration() { return (BRelTime)get(winkDuration); }

  /**
   * Set the {@code winkDuration} property.
   * Defines how long the winking will last.
   * @see #winkDuration
   */
  public void setWinkDuration(BRelTime v) { set(winkDuration, v, null); }

  //endregion Property "winkDuration"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * Contains the created proxy points representing
   * data values on this device
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BNrioPointDeviceExt(), null);

  /**
   * Get the {@code points} property.
   * Contains the created proxy points representing
   * data values on this device
   * @see #points
   */
  public BNrioPointDeviceExt getPoints() { return (BNrioPointDeviceExt)get(points); }

  /**
   * Set the {@code points} property.
   * Contains the created proxy points representing
   * data values on this device
   * @see #points
   */
  public void setPoints(BNrioPointDeviceExt v) { set(points, v, null); }

  //endregion Property "points"

  //region Property "ioStatus"

  /**
   * Slot for the {@code ioStatus} property.
   * last io status message received
   * @see #getIoStatus
   * @see #setIoStatus
   */
  public static final Property ioStatus = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, new BIoStatus(), null);

  /**
   * Get the {@code ioStatus} property.
   * last io status message received
   * @see #ioStatus
   */
  public BStruct getIoStatus() { return (BStruct)get(ioStatus); }

  /**
   * Set the {@code ioStatus} property.
   * last io status message received
   * @see #ioStatus
   */
  public void setIoStatus(BStruct v) { set(ioStatus, v, null); }

  //endregion Property "ioStatus"

  //region Action "upload"

  /**
   * Slot for the {@code upload} action.
   * @see #upload(BUploadParameters parameter)
   */
  public static final Action upload = newAction(Flags.ASYNC | Flags.HIDDEN, new BUploadParameters(), null);

  //endregion Action "upload"

  //region Action "download"

  /**
   * Slot for the {@code download} action.
   * @see #download(BDownloadParameters parameter)
   */
  public static final Action download = newAction(Flags.ASYNC | Flags.HIDDEN, new BDownloadParameters(), null);

  //endregion Action "download"

  //region Action "winkDevice"

  /**
   * Slot for the {@code winkDevice} action.
   * @see #winkDevice()
   */
  public static final Action winkDevice = newAction(0, null);

  /**
   * Invoke the {@code winkDevice} action.
   * @see #winkDevice
   */
  public void winkDevice() { invoke(winkDevice, null, null); }

  //endregion Action "winkDevice"

  //region Action "winkTimeout"

  /**
   * Slot for the {@code winkTimeout} action.
   * @see #winkTimeout()
   */
  public static final Action winkTimeout = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code winkTimeout} action.
   * @see #winkTimeout
   */
  public void winkTimeout() { invoke(winkTimeout, null, null); }

  //endregion Action "winkTimeout"

  //region Action "winkCancel"

  /**
   * Slot for the {@code winkCancel} action.
   * @see #winkCancel()
   */
  public static final Action winkCancel = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code winkCancel} action.
   * @see #winkCancel
   */
  public void winkCancel() { invoke(winkCancel, null, null); }

  //endregion Action "winkCancel"

  //region Action "enablePolling"

  /**
   * Slot for the {@code enablePolling} action.
   * @see #enablePolling()
   */
  public static final Action enablePolling = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code enablePolling} action.
   * @see #enablePolling
   */
  public void enablePolling() { invoke(enablePolling, null, null); }

  //endregion Action "enablePolling"

  //region Action "disablePolling"

  /**
   * Slot for the {@code disablePolling} action.
   * @see #disablePolling()
   */
  public static final Action disablePolling = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code disablePolling} action.
   * @see #disablePolling
   */
  public void disablePolling() { invoke(disablePolling, null, null); }

  //endregion Action "disablePolling"

  //region Action "setPingOk"

  /**
   * Slot for the {@code setPingOk} action.
   * @see #setPingOk()
   */
  public static final Action setPingOk = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code setPingOk} action.
   * @see #setPingOk
   */
  public void setPingOk() { invoke(setPingOk, null, null); }

  //endregion Action "setPingOk"

  //region Action "setAddressAndPing"

  /**
   * Slot for the {@code setAddressAndPing} action.
   * @see #setAddressAndPing()
   */
  public static final Action setAddressAndPing = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code setAddressAndPing} action.
   * @see #setAddressAndPing
   */
  public void setAddressAndPing() { invoke(setAddressAndPing, null, null); }

  //endregion Action "setAddressAndPing"

  //region Action "dumpIoMap"

  /**
   * Slot for the {@code dumpIoMap} action.
   * @see #dumpIoMap()
   */
  public static final Action dumpIoMap = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code dumpIoMap} action.
   * @see #dumpIoMap
   */
  public void dumpIoMap() { invoke(dumpIoMap, null, null); }

  //endregion Action "dumpIoMap"

  //region Action "readInfoMemory"

  /**
   * Slot for the {@code readInfoMemory} action.
   * @see #readInfoMemory()
   */
  public static final Action readInfoMemory = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code readInfoMemory} action.
   * @see #readInfoMemory
   */
  public BComponent readInfoMemory() { return (BComponent)invoke(readInfoMemory, null, null); }

  //endregion Action "readInfoMemory"

  //region Action "clearInfoMemory"

  /**
   * Slot for the {@code clearInfoMemory} action.
   * @see #clearInfoMemory()
   */
  public static final Action clearInfoMemory = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code clearInfoMemory} action.
   * @see #clearInfoMemory
   */
  public void clearInfoMemory() { invoke(clearInfoMemory, null, null); }

  //endregion Action "clearInfoMemory"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public void started()
  throws Exception
  {
    super.started();
    BNrioDeviceTypeEnum type = getDeviceType();
    accessNet = (BNrioNetwork)getNetwork();
    try
    {
      if(type.equals(BNrioDeviceTypeEnum.remoteReader))
        setAvailableVersion(((BString)accessNet.get("T2030")).getString());
      else if(type.equals(BNrioDeviceTypeEnum.remoteInputOutput))
        setAvailableVersion(((BString)accessNet.get("T2034")).getString());
      else if(type.equals(BNrioDeviceTypeEnum.baseBoardReader))
        setAvailableVersion(((BString)accessNet.get("T2029")).getString());
      else if(type.equals(BNrioDeviceTypeEnum.io16))
        setAvailableVersion(((BString)accessNet.get("T2041")).getString());
      else if(type.equals(BNrioDeviceTypeEnum.io16V1))
        setAvailableVersion(((BString)accessNet.get("T2101")).getString());
      else
        setAvailableVersion("");
    }
    catch(Exception e)
    {
      setAvailableVersion("");
    }
    setIsRemote( !type.equals(BNrioDeviceTypeEnum.baseBoardReader) );
    setFlags(upload, getFlags(upload) | Flags.HIDDEN );
    setFlags(download, getFlags(download) | Flags.HIDDEN );    
    if(isUidDefault())
    {
      configFail("Invalid UID: Do Discover and Match.");
    }
    else
      configOk();
    accessNet.addDevice(this);
  }

  public void stopped()
  throws Exception
  {
    try
    {
      accessNet.removeDevice(getAddress());
      doDisablePolling();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public boolean isUidDefault()
  {
    byte[] currentUid = getUid().copyBytes();
    if(currentUid.length != defaultUid.length)
      return false;
    for( int i = 0; i < defaultUid.length; i++)
    {
      if(currentUid[i] != defaultUid[i]) return false;
    }
    return true;
  }

  public Type getNetworkType()
  {
    return BNrioNetwork.TYPE;
  }

  /**
   * Overrides isParentLegal method.  BNrioDevices
   * must reside under a BNrioNetwork.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BNrioDeviceFolder ||
        parent instanceof BNrioNetwork         );
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
    {
      return;
    }
    if (p.equals(status))
    {
      if (getStatus().isDisabled() || getStatus().isDown())
      {
        firstPing = true;
      }
      else if (firstPing)
      {
        ping();
      }
    }
    else if (p.equals(enabled))
    {
      if (!getEnabled())
      {
        disablePolling();
      }
      else
      {
        enablePolling();
        ping();
      }
    }
    else if (p.equals(uid) || p.equals(address))
    {
      final int address = getAddress();
      if (isUidDefault())
      {
        configFail("Invalid UID: Do Discover and Match.");
      }
      else if (address <= 0 || address > 16)
      {
        configFail("Invalid Address: " + address);
      }
      else
      {
        configOk();
        accessNet.removeDevice(address);
        accessNet.addDevice(this);
      }
    }
  }

  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(clearInfoMemory) ||
      action.equals(setAddressAndPing) ||
      action.equals(readInfoMemory)        )
    {
      return this.postAsync(new Invocation(this, action, arg, cx));
    }
    return super.post(action, arg, cx);
  }

  public boolean getFirstPing()
  {
    return firstPing;
  }
  public void setFirstPing(boolean firstPing)
  {
    this.firstPing = firstPing;
  }


  /**
   * Ping implementation.
   */
  public void doPing()
  {
    boolean needsInitialized = isDown() || firstPing;
    if(DualModuleUtils.sendPing(this) != NrioMessageConst.MESSAGE_STATUS_OK)
      return;
    DualModuleUtils.processPostPing(this, needsInitialized);
  }

  public void readBuildInfo()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    network.getLog().trace(">>> BNrioDevice(" + getName() + ").readBuildInfo");
    String version = network.readBuildInfo(getAddress());
    network.getLog().trace(">>> BNrioDevice(" + getName() + ").device version =" + version);
    setInstalledVersion(version);
  }

  public boolean isFirmwareUptodate()
  {
    String availVersion = getAvailableVersion().trim();
    String installedVersion = getInstalledVersion();
    return( availVersion.length() > 0 && (installedVersion.equals(getAvailableVersion()) ) );
  }

  public void doEnablePolling()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    if (this.isFault() || isDisabled())
    {
      isPollingActive = false;
      network.getLog().message(">>> BNrioDevice(" + getName() + ") polling is not enabled because of fault or disabled status");
    }
    else
    {
      //TODO: Behavior of isPollingActive statement always calling enablePolling() codified as intentional
      //      with { } to maintain status quo in NCCB-50221 coverity cleanup. If this is incorrect, and intention
      //      was to only call enablePolling() when isPollingActive is false, create new issue to investigate further.
      //      "Incorrect" if behavior introduced in NCCB-22971 "Update Nrio java driver to support the IO34 module prototype hardware".
      //      Also consider only calling disablePolling below under similar conditions, i.e. only when isPollingActive == true.
      if (!isPollingActive)
      {
        network.getLog().trace(">>> BNrioDevice(" + getName() + ") pollingEnabled");
      }
      network.enablePolling(getAddress());
      isPollingActive = true;
    }
  }

  public void doDisablePolling()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    network.getLog().trace(">>> BNrioDevice(" + getName() + ") pollingDisabled");
    network.disablePolling(getAddress());
    isPollingActive = false;
    firstPing = true;
  }

  public boolean incrementPingFailCount()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    final int maxFails = network.getMaxFailsUntilDown();
    if(++pingFailCount > maxFails)
      pingFailCount = maxFails;
    pingFailLog.trace(getName() + ": pingFailCount = " + pingFailCount + ", maxFailCount = " + maxFails);
    return pingFailCount == maxFails;
  }

  public void clearPingFailCount()
  {
    pingFailCount = 0;
  }

  public void pingFail(String cause)
  {
    if(!getStatus().isDown())
    {
      ((BNrioNetwork)getNetwork()).getLog().message(getAddress() + ": device down. Cause: " + cause);
    }
    pingOkFlag = false;
    firstPing = true;
    super.pingFail(cause);
  }

  public void pingOk()
  {
    if (!pingOkFlag)
    {
      pingOkFlag = true;
      ((BNrioNetwork)getNetwork()).getLog().message(getAddress() + ": device up.");
    }
    clearPingFailCount();
    super.pingOk();
  }

  /*
   *  doDumpIoMap - subclass should override this method.
   */
  public void doDumpIoMap()
  {
  }

  public void doWinkDevice()
  {
    winkDurationTicket = setTimer(winkDurationTicket, getWinkDuration(), winkCancel);
    int winkOut = getWinkOutput();
    winkTicket = setTimer(winkTicket, BRelTime.makeSeconds(1), winkTimeout);
    setDoValue(true, winkOut);
    winkOutState= true;
    winkActive = true;
    this.setFlags(winkDevice, (getFlags(winkDevice) |  Flags.HIDDEN ));
    this.setFlags(winkCancel, (getFlags(winkCancel) & ~Flags.HIDDEN ));
  }

  public void doWinkTimeout()
  {
    if(winkActive)
    {
      setDoValue(!winkOutState, getWinkOutput());
      winkOutState = !winkOutState;
    }
  }

  public void doWinkCancel()
  {
    winkActive = false;
    cancelTimer(winkTicket);
    cancelTimer(winkDurationTicket);
    setDoValue(false, getWinkOutput());
    this.setFlags(winkDevice, (getFlags(winkDevice) & ~Flags.HIDDEN ));
    this.setFlags(winkCancel, (getFlags(winkCancel) |  Flags.HIDDEN ));
  }

  public void doSetPingOk()
  {
    pingOk();
  }

  public void initLastWrite()
  {

  }

  // must be overridden by subclass if it is a dual module
  // currently only IO-34 is a dual module.
  public boolean isDualModule()
  {
    return false;
  }

  // must be overridden by subclasses
  public int doWriteDoValues()
  {
    return -1;
  }

  // must be overridden by subclasses
  public int setDoValue(boolean value, int instance)
  {
    return -1;
  }

  // must be overridden by subclasses
  public void processStatusMessage(NrioReceivedMessage statusMsg)
  {
  }

  // must be overridden by subclasses
  public void updateProxyValues()
  {
  }

  // must be overridden by subclasses
  public void setIsRemote(boolean value)
  {
  }

  // must be overridden by subclasses
  public boolean getIsRemote()
  {
    return true;
  }

  // must be overridden by subclasses
  public int sendWriteConfig()
  {
    return 0;
  }

  // must be overridden by subclasses
  public int sendOutputConfig()
  {
    return 0;
  }



  public BControlPoint checkForProxyExtConflicts(BControlPoint sourcePoint)
  {
    if(!(sourcePoint.getProxyExt() instanceof BNrioProxyExt))
      return null;
      
    BNrioProxyExt sourceProxy = (BNrioProxyExt)sourcePoint.getProxyExt();            
    
    BControlPoint[] cps = getPoints().getPoints();
    for(int i = 0; i < cps.length; i++)
    {
      if(cps[i].getHandle() != null && sourcePoint.getHandle() != null && 
         cps[i].getHandle().equals(sourcePoint.getHandle()))
           continue;
      BNrioProxyExt testProxy = (BNrioProxyExt)cps[i].getProxyExt();
      if(!testProxy.getEnabled() || !sourceProxy.getEnabled())
        continue;
      if(testProxy.getInstance() != sourceProxy.getInstance())
        continue;
      if(testProxy.getIsSdi() != sourceProxy.getIsSdi())
        continue;
      if(sourcePoint.isWritablePoint() != cps[i].isWritablePoint())
        continue;
            
      return cps[i];      
    }
    return null;
  }

  // call back only used by Security product for badge validation
  public void processIoStatusMessage(NrioMessage statusMessage)
  {

  }

  public NrioDeviceIoStatus getLastIoStatus()
  {
    return lastIoStatus;
  }

  public BComponent doReadInfoMemory()
  {
    BComponent info = new BComponent();
    try
    {
      ReadInfoMemoryMessage req = new ReadInfoMemoryMessage(getAddress());
      NrioMessage rsp = accessNet.sendNrioMessage(req);

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
      info.add("numCardReadsProcessed", BInteger.make(infoData[6] & 0x0ff));
      info.add("numCardReadsXmitted"  , BInteger.make(infoData[7] & 0x0ff));
      info.add("numCardReadErrors"    , BInteger.make(infoData[8] & 0x0ff));
      info.add("num485ResetsOnRcv"    , BInteger.make(infoData[9] & 0x0ff));
      info.add("lastResetState"       , BInteger.make(infoData[10] & 0x0ff));
    }
    catch(Exception e)
    {
      accessNet.getLog().message(getName() + ": doReadInfoMemory caught exception: " + e);
    }
    return info;
  }

  public void doClearInfoMemory()
  {
    try
    {
      ClearInfoMemoryMessage req = new ClearInfoMemoryMessage(getAddress());
      NrioMessage rsp = accessNet.sendNrioMessage(req);
    }
    catch(Exception e)
    {
      accessNet.getLog().message(getName() + ": doReadInfoMemory caught exception: " + e);
    }
  }

  public void doSetAddressAndPing()
  {
    int logicalAddress = getAddress();
    byte[] uid = getUid().copyBytes();
    int type = getDeviceType().getRawInt();
    SetLogicalAddressMessage setAddrMsg = new SetLogicalAddressMessage( logicalAddress, uid );
    NrioMessage rsp = accessNet.sendNrioMessage(setAddrMsg);
    try{Thread.sleep(20);}catch(Exception ignored){}
    PingMessage pReq = new PingMessage(logicalAddress, uid, type);
    PingResponse pRsp = (PingResponse)(accessNet.sendNrioMessage(pReq));
    doPing();
  }


  private Clock.Ticket setTimer(Clock.Ticket ticket, BRelTime time, Action action )
  {
    if(ticket!= null)
      ticket.cancel();
    return Clock.schedulePeriodically(this, time, action, null);
  }

  private void cancelTimer(Clock.Ticket ticket)
  {
    if(ticket!= null) ticket.cancel();
  }

////////////////////////////////////////////////////////////////
// some appliance support stuff
////////////////////////////////////////////////////////////////

  public BOrd[] getUsedRelayArray()
  {
    return null;
  }

  /**
   * Return the boolean array that indicates how many digital inputs are supported and which are
   *   currently assigned.  A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedDiArray()
  {
    return null;
  }

  /**
   * Return the boolean array that indicates how many supervised digital inputs are supported
   *    and which are currently assigned.  
   *    A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedSdiArray()
  {
    return null;
  }

  /**
   * Return the BOrd array that indicates how many relays are supported and which are
   *   currently assigned.  A true value will indicate that a relay is assigned.
   */

  public BOrd[] getUsedRelayArray(int maxSize)
  {
    BOrd[] usedRelayMap = new BOrd[maxSize];
    BControlPoint[] points = getPoints().getPoints();
    for(int i = 0; i < points.length; i++)
    {
      BProxyExt proxyExt = (BProxyExt)points[i].getProxyExt();
      if(proxyExt instanceof BNrioProxyExt)
      {
        if(points[i] instanceof BBooleanWritable && proxyExt.getEnabled())
        {
          int instance = ((BNrioProxyExt)proxyExt).getInstance();
          if(instance > 0 && instance <= usedRelayMap.length)
            usedRelayMap[instance-1]=points[i].getSlotPathOrd();
        }
      }
    }
    return usedRelayMap;
  }

  /**
   * Return the BOrd array that indicates how many digital inputs are supported and which are
   *   currently assigned.  A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedDiArray(int maxSize)
  {
    BOrd[]usedDiMap = new BOrd[maxSize]; 
    BControlPoint[] points = getPoints().getPoints();
    for(int i = 0; i < points.length; i++)
    {
      BProxyExt proxyExt = (BProxyExt)points[i].getProxyExt();
      if(proxyExt instanceof BNrioProxyExt)
      {
        if(points[i] instanceof BBooleanPoint && !points[i].isWritablePoint())
        {
          if( !((BNrioProxyExt)proxyExt).getIsSdi() && proxyExt.getEnabled() )
          {
            try
            {
              int instance = ((BNrioProxyExt)proxyExt).getInstance();
              if(instance > 0 && instance <= usedDiMap.length)
                usedDiMap[instance-1]=points[i].getSlotPathOrd();
            }
            catch(Exception ignored) {}
          }
        }
      }
    }
    return usedDiMap;
  }

  /**
   * Return the BOrd array that indicates how many supervised digital inputs are supported
   *    and which are currently assigned.  
   *    A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedSdiArray(int maxSize)
  {
    BOrd[] usedSdiMap = new BOrd[maxSize];
    BControlPoint[] points = getPoints().getPoints();
    for(int i = 0; i < points.length; i++)
    {
      BProxyExt proxyExt = (BProxyExt)points[i].getProxyExt();
      if(proxyExt instanceof BNrioProxyExt)
      {
        if(points[i] instanceof BBooleanPoint && !points[i].isWritablePoint())
        {
          if( ((BNrioProxyExt)proxyExt).getIsSdi() &&
              proxyExt.getEnabled()                      )
          {
            int instance = ((BNrioProxyExt)proxyExt).getInstance();
            if(instance > 0 && instance <= usedSdiMap.length)
              usedSdiMap[instance-1]=points[i].getSlotPathOrd();
          }
        }
      }
    }
    return usedSdiMap;
  }

  /**
   * Return the BNrioLearnDeviceEntry based on this device. Used by BNrioLearnDevicesJob to populate the
   * existing devices.
   */
  public BNrioLearnDeviceEntry makeLearnDeviceEntry()
  {
    return new BNrioLearnDeviceEntry(getAddress(), getDeviceType(), getUid().copyBytes(), getInstalledVersion(), getDisplayName(null), -1);
  }

  /**
   * Return true if this device is in the process of writing the output default values to this device.
   * This is a two message sequence.  A true indicates that the first message has been sent but the
   * second message has not been sent.
   */
  public boolean isWriteOutputDefaultsInProgress()
  {
    return false;
  }

  public static final Log pingFailLog = Log.getLog("nrio.pingFail");

  private boolean pingOkFlag = true;
  private boolean winkActive = false;
  private Clock.Ticket winkDurationTicket;
  private boolean winkOutState = false;
  private Clock.Ticket winkTicket;
  private final NrioMessage statusMessage = new NrioMessage();
  private final NrioDeviceIoStatus lastIoStatus = new NrioDeviceIoStatus();
  private BNrioNetwork accessNet;
  private boolean isPollingActive = false;
  public boolean firstPing = true;
  public int pingFailCount = 0;
}
