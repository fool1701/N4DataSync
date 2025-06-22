/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import java.io.BufferedReader;
import java.util.Vector;
import java.util.logging.Level;

import javax.baja.driver.BDevice;
import javax.baja.driver.ping.BPingMonitor;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.license.Feature;
import javax.baja.license.FeatureNotLicensedException;
import javax.baja.log.Log;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.serial.BBaudRate;
import javax.baja.serial.BSerialBaudRate;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Action;
import javax.baja.sys.BBlob;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BModule;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.units.UnitDatabase;
import javax.baja.util.Lexicon;

import com.tridium.basicdriver.BBasicNetwork;
import com.tridium.basicdriver.comm.Comm;
import com.tridium.nrio.comm.NrioComm;
import com.tridium.nrio.comm.NrioUnsolicitedReceive;
import com.tridium.nrio.components.BIOutputDefaultValues;
import com.tridium.nrio.components.BNrioLearnDeviceEntry;
import com.tridium.nrio.components.BOutputFailsafeConfig;
import com.tridium.nrio.components.BSdiValueConfig;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.job.BNrioLearnDevicesJob;
import com.tridium.nrio.job.BUpgradeFirmwareJob;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.NrioReceivedMessage;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.ReadBuildInfoMessage;
import com.tridium.nrio.messages.ReadInfoMemoryMessage;
import com.tridium.nrio.messages.ResetMessage;
import com.tridium.nrio.messages.WriteConfigMessage;
import com.tridium.nrio.messages.WriteDOMessage;
import com.tridium.nrio.points.BNrioPointDeviceExt;
import com.tridium.sys.license.LicenseUtil;


/**
 * BNrioNetwork is the base container for BNrioDevices.   
 *
 * @author Andy Saunders on 21 Jan 02
 * @since Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "monitor",
  type = "BPingMonitor",
  defaultValue = "makePingMonitor(BRelTime.makeSeconds(30))",
  override = true
)
@NiagaraProperty(
  name = "portName",
  type = "String",
  defaultValue = "COM2",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "trunk",
  type = "int",
  defaultValue = "1",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "baudRate",
  type = "BBaudRate",
  defaultValue = "BSerialBaudRate.baud115200",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "sdiValueConfig",
  type = "BSdiValueConfig",
  defaultValue = "new BSdiValueConfig()"
)
@NiagaraProperty(
  name = "maxDevices",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.HIDDEN | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "writeThreadSleepTime",
  type = "long",
  defaultValue = "100",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "pushToPoints",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "minPushTime",
  type = "long",
  defaultValue = "300",
  flags = Flags.HIDDEN,
  facets = @Facet(name = "BFacets.UNITS", value = "BUnit.getUnit(\"millisecond\")")
)
@NiagaraProperty(
  name = "maxFailsUntilDown",
  type = "int",
  defaultValue = "3",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "wrPriority",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "outputFailsafeConfig",
  type = "BOutputFailsafeConfig",
  defaultValue = "new BOutputFailsafeConfig(8, 180)"
)
@NiagaraProperty(
  name = "unsolicitedMsgCount",
  type = "long",
  defaultValue = "0L",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "unsolicitedProcessTime",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "unsolicitedMessageRate",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "processedUnsolicitedMsgCount",
  type = "long",
  defaultValue = "0L",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "pushedUnsolicitedMessageRate",
  type = "float",
  defaultValue = "0.0f",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraAction(
  name = "submitDeviceDiscoveryJob",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "upgradeFirmware",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "winkDevice",
  parameterType = "BInteger",
  defaultValue = "BInteger.make(0)",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "enableWinking",
  parameterType = "BNrioLearnDeviceEntry",
  defaultValue = "new BNrioLearnDeviceEntry()",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "disableWinking",
  parameterType = "BNrioLearnDeviceEntry",
  defaultValue = "new BNrioLearnDeviceEntry()",
  flags = Flags.HIDDEN
)


public class BNrioNetwork
  extends BBasicNetwork
  implements Runnable, NrioMessageConst
{

  public static BPingMonitor makePingMonitor(BRelTime pingTime)
  {
    BPingMonitor pm = new BPingMonitor();
    pm.setPingFrequency(pingTime);
    return pm;
  }

  private static final BFacets holdTimeFacets = BFacets.makeInt(UnitDatabase.getUnit("second"), 5, 120);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrioNetwork(3645117221)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "monitor"

  /**
   * Slot for the {@code monitor} property.
   * @see #getMonitor
   * @see #setMonitor
   */
  public static final Property monitor = newProperty(0, makePingMonitor(BRelTime.makeSeconds(30)), null);

  //endregion Property "monitor"

  //region Property "portName"

  /**
   * Slot for the {@code portName} property.
   * @see #getPortName
   * @see #setPortName
   */
  public static final Property portName = newProperty(Flags.DEFAULT_ON_CLONE, "COM2", null);

  /**
   * Get the {@code portName} property.
   * @see #portName
   */
  public String getPortName() { return getString(portName); }

  /**
   * Set the {@code portName} property.
   * @see #portName
   */
  public void setPortName(String v) { setString(portName, v, null); }

  //endregion Property "portName"

  //region Property "trunk"

  /**
   * Slot for the {@code trunk} property.
   * @see #getTrunk
   * @see #setTrunk
   */
  public static final Property trunk = newProperty(Flags.DEFAULT_ON_CLONE, 1, null);

  /**
   * Get the {@code trunk} property.
   * @see #trunk
   */
  public int getTrunk() { return getInt(trunk); }

  /**
   * Set the {@code trunk} property.
   * @see #trunk
   */
  public void setTrunk(int v) { setInt(trunk, v, null); }

  //endregion Property "trunk"

  //region Property "baudRate"

  /**
   * Slot for the {@code baudRate} property.
   * @see #getBaudRate
   * @see #setBaudRate
   */
  public static final Property baudRate = newProperty(Flags.READONLY, BSerialBaudRate.baud115200, null);

  /**
   * Get the {@code baudRate} property.
   * @see #baudRate
   */
  public BBaudRate getBaudRate() { return (BBaudRate)get(baudRate); }

  /**
   * Set the {@code baudRate} property.
   * @see #baudRate
   */
  public void setBaudRate(BBaudRate v) { set(baudRate, v, null); }

  //endregion Property "baudRate"

  //region Property "sdiValueConfig"

  /**
   * Slot for the {@code sdiValueConfig} property.
   * @see #getSdiValueConfig
   * @see #setSdiValueConfig
   */
  public static final Property sdiValueConfig = newProperty(0, new BSdiValueConfig(), null);

  /**
   * Get the {@code sdiValueConfig} property.
   * @see #sdiValueConfig
   */
  public BSdiValueConfig getSdiValueConfig() { return (BSdiValueConfig)get(sdiValueConfig); }

  /**
   * Set the {@code sdiValueConfig} property.
   * @see #sdiValueConfig
   */
  public void setSdiValueConfig(BSdiValueConfig v) { set(sdiValueConfig, v, null); }

  //endregion Property "sdiValueConfig"

  //region Property "maxDevices"

  /**
   * Slot for the {@code maxDevices} property.
   * @see #getMaxDevices
   * @see #setMaxDevices
   */
  public static final Property maxDevices = newProperty(Flags.READONLY | Flags.HIDDEN | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code maxDevices} property.
   * @see #maxDevices
   */
  public int getMaxDevices() { return getInt(maxDevices); }

  /**
   * Set the {@code maxDevices} property.
   * @see #maxDevices
   */
  public void setMaxDevices(int v) { setInt(maxDevices, v, null); }

  //endregion Property "maxDevices"

  //region Property "writeThreadSleepTime"

  /**
   * Slot for the {@code writeThreadSleepTime} property.
   * @see #getWriteThreadSleepTime
   * @see #setWriteThreadSleepTime
   */
  public static final Property writeThreadSleepTime = newProperty(Flags.HIDDEN, 100, null);

  /**
   * Get the {@code writeThreadSleepTime} property.
   * @see #writeThreadSleepTime
   */
  public long getWriteThreadSleepTime() { return getLong(writeThreadSleepTime); }

  /**
   * Set the {@code writeThreadSleepTime} property.
   * @see #writeThreadSleepTime
   */
  public void setWriteThreadSleepTime(long v) { setLong(writeThreadSleepTime, v, null); }

  //endregion Property "writeThreadSleepTime"

  //region Property "pushToPoints"

  /**
   * Slot for the {@code pushToPoints} property.
   * @see #getPushToPoints
   * @see #setPushToPoints
   */
  public static final Property pushToPoints = newProperty(0, true, null);

  /**
   * Get the {@code pushToPoints} property.
   * @see #pushToPoints
   */
  public boolean getPushToPoints() { return getBoolean(pushToPoints); }

  /**
   * Set the {@code pushToPoints} property.
   * @see #pushToPoints
   */
  public void setPushToPoints(boolean v) { setBoolean(pushToPoints, v, null); }

  //endregion Property "pushToPoints"

  //region Property "minPushTime"

  /**
   * Slot for the {@code minPushTime} property.
   * @see #getMinPushTime
   * @see #setMinPushTime
   */
  public static final Property minPushTime = newProperty(Flags.HIDDEN, 300, BFacets.make(BFacets.UNITS, BUnit.getUnit("millisecond")));

  /**
   * Get the {@code minPushTime} property.
   * @see #minPushTime
   */
  public long getMinPushTime() { return getLong(minPushTime); }

  /**
   * Set the {@code minPushTime} property.
   * @see #minPushTime
   */
  public void setMinPushTime(long v) { setLong(minPushTime, v, null); }

  //endregion Property "minPushTime"

  //region Property "maxFailsUntilDown"

  /**
   * Slot for the {@code maxFailsUntilDown} property.
   * @see #getMaxFailsUntilDown
   * @see #setMaxFailsUntilDown
   */
  public static final Property maxFailsUntilDown = newProperty(Flags.HIDDEN, 3, null);

  /**
   * Get the {@code maxFailsUntilDown} property.
   * @see #maxFailsUntilDown
   */
  public int getMaxFailsUntilDown() { return getInt(maxFailsUntilDown); }

  /**
   * Set the {@code maxFailsUntilDown} property.
   * @see #maxFailsUntilDown
   */
  public void setMaxFailsUntilDown(int v) { setInt(maxFailsUntilDown, v, null); }

  //endregion Property "maxFailsUntilDown"

  //region Property "wrPriority"

  /**
   * Slot for the {@code wrPriority} property.
   * @see #getWrPriority
   * @see #setWrPriority
   */
  public static final Property wrPriority = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code wrPriority} property.
   * @see #wrPriority
   */
  public boolean getWrPriority() { return getBoolean(wrPriority); }

  /**
   * Set the {@code wrPriority} property.
   * @see #wrPriority
   */
  public void setWrPriority(boolean v) { setBoolean(wrPriority, v, null); }

  //endregion Property "wrPriority"

  //region Property "outputFailsafeConfig"

  /**
   * Slot for the {@code outputFailsafeConfig} property.
   * @see #getOutputFailsafeConfig
   * @see #setOutputFailsafeConfig
   */
  public static final Property outputFailsafeConfig = newProperty(0, new BOutputFailsafeConfig(8, 180), null);

  /**
   * Get the {@code outputFailsafeConfig} property.
   * @see #outputFailsafeConfig
   */
  public BOutputFailsafeConfig getOutputFailsafeConfig() { return (BOutputFailsafeConfig)get(outputFailsafeConfig); }

  /**
   * Set the {@code outputFailsafeConfig} property.
   * @see #outputFailsafeConfig
   */
  public void setOutputFailsafeConfig(BOutputFailsafeConfig v) { set(outputFailsafeConfig, v, null); }

  //endregion Property "outputFailsafeConfig"

  //region Property "unsolicitedMsgCount"

  /**
   * Slot for the {@code unsolicitedMsgCount} property.
   * @see #getUnsolicitedMsgCount
   * @see #setUnsolicitedMsgCount
   */
  public static final Property unsolicitedMsgCount = newProperty(Flags.TRANSIENT | Flags.READONLY, 0L, null);

  /**
   * Get the {@code unsolicitedMsgCount} property.
   * @see #unsolicitedMsgCount
   */
  public long getUnsolicitedMsgCount() { return getLong(unsolicitedMsgCount); }

  /**
   * Set the {@code unsolicitedMsgCount} property.
   * @see #unsolicitedMsgCount
   */
  public void setUnsolicitedMsgCount(long v) { setLong(unsolicitedMsgCount, v, null); }

  //endregion Property "unsolicitedMsgCount"

  //region Property "unsolicitedProcessTime"

  /**
   * Slot for the {@code unsolicitedProcessTime} property.
   * @see #getUnsolicitedProcessTime
   * @see #setUnsolicitedProcessTime
   */
  public static final Property unsolicitedProcessTime = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code unsolicitedProcessTime} property.
   * @see #unsolicitedProcessTime
   */
  public int getUnsolicitedProcessTime() { return getInt(unsolicitedProcessTime); }

  /**
   * Set the {@code unsolicitedProcessTime} property.
   * @see #unsolicitedProcessTime
   */
  public void setUnsolicitedProcessTime(int v) { setInt(unsolicitedProcessTime, v, null); }

  //endregion Property "unsolicitedProcessTime"

  //region Property "unsolicitedMessageRate"

  /**
   * Slot for the {@code unsolicitedMessageRate} property.
   * @see #getUnsolicitedMessageRate
   * @see #setUnsolicitedMessageRate
   */
  public static final Property unsolicitedMessageRate = newProperty(Flags.TRANSIENT | Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code unsolicitedMessageRate} property.
   * @see #unsolicitedMessageRate
   */
  public float getUnsolicitedMessageRate() { return getFloat(unsolicitedMessageRate); }

  /**
   * Set the {@code unsolicitedMessageRate} property.
   * @see #unsolicitedMessageRate
   */
  public void setUnsolicitedMessageRate(float v) { setFloat(unsolicitedMessageRate, v, null); }

  //endregion Property "unsolicitedMessageRate"

  //region Property "processedUnsolicitedMsgCount"

  /**
   * Slot for the {@code processedUnsolicitedMsgCount} property.
   * @see #getProcessedUnsolicitedMsgCount
   * @see #setProcessedUnsolicitedMsgCount
   */
  public static final Property processedUnsolicitedMsgCount = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN, 0L, null);

  /**
   * Get the {@code processedUnsolicitedMsgCount} property.
   * @see #processedUnsolicitedMsgCount
   */
  public long getProcessedUnsolicitedMsgCount() { return getLong(processedUnsolicitedMsgCount); }

  /**
   * Set the {@code processedUnsolicitedMsgCount} property.
   * @see #processedUnsolicitedMsgCount
   */
  public void setProcessedUnsolicitedMsgCount(long v) { setLong(processedUnsolicitedMsgCount, v, null); }

  //endregion Property "processedUnsolicitedMsgCount"

  //region Property "pushedUnsolicitedMessageRate"

  /**
   * Slot for the {@code pushedUnsolicitedMessageRate} property.
   * @see #getPushedUnsolicitedMessageRate
   * @see #setPushedUnsolicitedMessageRate
   */
  public static final Property pushedUnsolicitedMessageRate = newProperty(Flags.TRANSIENT | Flags.READONLY, 0.0f, null);

  /**
   * Get the {@code pushedUnsolicitedMessageRate} property.
   * @see #pushedUnsolicitedMessageRate
   */
  public float getPushedUnsolicitedMessageRate() { return getFloat(pushedUnsolicitedMessageRate); }

  /**
   * Set the {@code pushedUnsolicitedMessageRate} property.
   * @see #pushedUnsolicitedMessageRate
   */
  public void setPushedUnsolicitedMessageRate(float v) { setFloat(pushedUnsolicitedMessageRate, v, null); }

  //endregion Property "pushedUnsolicitedMessageRate"

  //region Action "submitDeviceDiscoveryJob"

  /**
   * Slot for the {@code submitDeviceDiscoveryJob} action.
   * @see #submitDeviceDiscoveryJob()
   */
  public static final Action submitDeviceDiscoveryJob = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code submitDeviceDiscoveryJob} action.
   * @see #submitDeviceDiscoveryJob
   */
  public BOrd submitDeviceDiscoveryJob() { return (BOrd)invoke(submitDeviceDiscoveryJob, null, null); }

  //endregion Action "submitDeviceDiscoveryJob"

  //region Action "upgradeFirmware"

  /**
   * Slot for the {@code upgradeFirmware} action.
   * @see #upgradeFirmware()
   */
  public static final Action upgradeFirmware = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code upgradeFirmware} action.
   * @see #upgradeFirmware
   */
  public BOrd upgradeFirmware() { return (BOrd)invoke(upgradeFirmware, null, null); }

  //endregion Action "upgradeFirmware"

  //region Action "winkDevice"

  /**
   * Slot for the {@code winkDevice} action.
   * @see #winkDevice(BInteger parameter)
   */
  public static final Action winkDevice = newAction(Flags.HIDDEN, BInteger.make(0), null);

  /**
   * Invoke the {@code winkDevice} action.
   * @see #winkDevice
   */
  public void winkDevice(BInteger parameter) { invoke(winkDevice, parameter, null); }

  //endregion Action "winkDevice"

  //region Action "enableWinking"

  /**
   * Slot for the {@code enableWinking} action.
   * @see #enableWinking(BNrioLearnDeviceEntry parameter)
   */
  public static final Action enableWinking = newAction(Flags.HIDDEN, new BNrioLearnDeviceEntry(), null);

  /**
   * Invoke the {@code enableWinking} action.
   * @see #enableWinking
   */
  public void enableWinking(BNrioLearnDeviceEntry parameter) { invoke(enableWinking, parameter, null); }

  //endregion Action "enableWinking"

  //region Action "disableWinking"

  /**
   * Slot for the {@code disableWinking} action.
   * @see #disableWinking(BNrioLearnDeviceEntry parameter)
   */
  public static final Action disableWinking = newAction(Flags.HIDDEN, new BNrioLearnDeviceEntry(), null);

  /**
   * Invoke the {@code disableWinking} action.
   * @see #disableWinking
   */
  public void disableWinking(BNrioLearnDeviceEntry parameter) { invoke(disableWinking, parameter, null); }

  //endregion Action "disableWinking"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

 public BNrioNetwork()
 {

 }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
      return;
    if (p.equals(portName) || p.equals(trunk))
    {
      if (!(getComm() == null))
      {
        ((NrioComm)getComm()).resetCommPort();
      }
    }
    else if(p.equals(outputFailsafeConfig))
    {
      BDevice[] devices = getDevices();
      for (int i = 0; i < devices.length; ++i)
      {
        if(devices[i] instanceof BNrio16Module)
        {
          BNrio16Module io16 = (BNrio16Module)devices[i];
          ((BIOutputDefaultValues)io16.getOutputDefaultValues()).setCommLossTimeout(getOutputFailsafeConfig().getCommLossTimeout());
          ((BIOutputDefaultValues)io16.getOutputDefaultValues()).setStartupTimeout(getOutputFailsafeConfig().getStartupTimeout());
          io16.postWriteOutputDefaults();
        }
      }
    }
  }

  public int getWriteThreadPriority()
  {
    return getWrPriority() ? Thread.NORM_PRIORITY+2 : Thread.NORM_PRIORITY;
  }

  public int getReadThreadPriority()
  {
    return Thread.NORM_PRIORITY;
  }

  public void setWriteThreadPriority(int priority)
  {
    if (wrThread.getPriority() != priority)
    {
      getLog().message(wrThread.getName() + " thread priority changed from " + wrThread.getPriority() + " to " + priority);
      wrThread.setPriority(priority);
    }
  }

  public void setReadThreadPriority(int priority)
  {
    getUnsolicitedReceive().setThreadPriority(priority);
  }

  ////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public BINavNode[] getNavChildren()
  {
    loadSlots();
    BComponent[] temp = new BComponent[getSlotCount()];
    SlotCursor<Property> c = getProperties();
    int count = 0;
    while (c.nextComponent())
    {
      BComponent kid = (BComponent)c.get();
      if (Flags.isHidden(this, c.property())) continue;
      if (!kid.isNavChild()) continue;
      if (kid.getPropertyInParent().isFrozen() &&
        !(kid instanceof BNrioDevice)) continue;

      temp[count++] = kid;
      kid = null;
    }

    BComponent[] result = new BComponent[count];
    System.arraycopy(temp, 0, result, 0, count);

    temp = null;
    c = null;

    return result;
  }

  /**
   * Returns the BNrioDevice type.
   */
  public Type getDeviceType()
  {
    return BNrioDevice.TYPE;
  }

  /**
   * Returns the BNrioDeviceFolder type.
   */
  public Type getDeviceFolderType()
  {
    return BNrioDeviceFolder.TYPE;
  }

  public final Feature getLicenseFeature()
  {
    Feature feature = null;
    if (getType().getModule().getModuleName().equals("accessDriver"))
    {

      try
      {
        feature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "accessControl");
      }
      catch (Exception e)
      {
      }

      if (feature != null)
      {
        isSecurityLicensed = true;
        return feature;
      }
      isSecurityLicensed = false;
      throw new FeatureNotLicensedException("accessControl");
    }
    else
    {
      try
      {
        feature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "nrio");
      }
      catch (Exception e)
      {
      }

      if (feature != null)
      {
        isNrioLicensed = true;
        return feature;
      }
      isNrioLicensed = false;
      throw new FeatureNotLicensedException("nrio");
    }
  }


  /**
   * Return a new instance of the custom Nrio communication
   * handler
   */
  protected Comm makeComm()
  {
    return new NrioComm(this);
  }

  public void started()
    throws Exception
  {
    if (!isSecurityLicensed && !isNrioLicensed)
    {
      getLog().error("network not starting because it is not licensed!");
      return;
    }
    super.started();

    // get version #s of BNrio's firmware files
    BModule module = TYPE.getModule();
    BIFile downLoadDir = module.findFile(new FilePath("/download"));
    if (downLoadDir == null)
      getLog().message("could not find down load directory");
    BIFile[] downLoadFiles = module.getChildren(downLoadDir);
    if (downLoadFiles != null)

      for (int i = 0; i < downLoadFiles.length; i++)
      {
        String fileName = downLoadFiles[i].getFileName();
        // 012345678
        // T2039_X_Y.
        try
        {
          String type = fileName.substring(1, 5);
          String tail = fileName.substring(6, fileName.indexOf('.'));
          int delimit = tail.indexOf('_');
          String interfaceNo = tail.substring(0, delimit);
          String buildNo = tail.substring(delimit + 1);
          if (get("T" + type) == null)
          {
            add("T" + type, BString.make(interfaceNo + "." + buildNo), Flags.READONLY | Flags.HIDDEN);
          }
          else
          {
            set("T" + type, BString.make(interfaceNo + "." + buildNo));
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }

    // start write thread
    timeToDie = false;
    wrThread = new Thread(this, "NrioWrThread" + getTrunk());
    wrThread.setPriority(getWriteThreadPriority());
    wrThread.start();

    //doSubmitDeviceDiscoveryJob();
    setMaxDevices(getLicenseMaxDevices());

    isStarted = true;
  }

  public void stopped()
    throws Exception
  {
    isStarted = false;

    timeToDie = true;
    wrThread.interrupt();
    super.stopped();
  }


  public final int getLicenseMaxDevices()
  {
    Feature feature = getLicenseFeature();
    String deviceLimit = feature.get("device.limit");
    if (deviceLimit == null)
      return 0;
    if (deviceLimit.equals("none"))
      return MAX_MODULE_ADDRESS;
    return feature.geti("device.limit", 0);
  }

  /**
   * This method starts the Communication handler
   * (Comm) if the network is not out-of-service
   * and the current Comm is not null.
   */
  public void startComm()
    throws Exception
  {
    try
    {
      super.startComm();
      nrioComm = (NrioComm)getComm();
      startUnsolicitedReceive();
      initLogicalAddressMapV2();
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {
      String error = ((NrioComm)getComm()).getCommInitError();
      if (error == null) configOk();
      else configFail(error);
    }
  }

  /**
   * This method starts the Communication handler
   * (Comm) if the network is not out-of-service
   * and the current Comm is not null.
   */
  public void stopComm()
    throws Exception
  {
    stopUnsolicitedReceive();
    super.stopComm();
  }

  public NrioUnsolicitedReceive getUnsolicitedReceive()
  {
    return unsolicitedReceive;
  }

  public void startUnsolicitedReceive()
  {
    if (isDisabled())
      return;
    unsolicitedReceive = new NrioUnsolicitedReceive(this);
    unsolicitedReceive.init();
    getComm().registerListener(unsolicitedReceive);
    unsolicitedReceive.start();
  }

  public void stopUnsolicitedReceive()
  {
    try
    {
      if (unsolicitedReceive != null)
        unsolicitedReceive.stop();
      //disablePolling();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  public void initNetwork()
  {
  }

  public Vector<byte[]> discover()
  {
    return ((NrioComm)getComm()).discover();
  }

  public void enablePolling(int address)
  {
    if (address == 0)
    {
      getLog().message("cannot enable polling on address 0 !!!");
      return;
    }
    ((NrioComm)getComm()).enablePolling(address);
  }

  public void disablePolling(int address)
  {
    if (address == 0)
    {
      getLog().message("cannot disable polling on address 0 !!!");
      return;
    }
    ((NrioComm)getComm()).disablePolling(address);
  }

  public NrioMessage sendReset(int address)
  {
    return sendNrioMessage(new ResetMessage(address));
  }


  public String readBuildInfo(int deviceAddress)
  {
    StringBuilder version = new StringBuilder();
    ReadBuildInfoMessage req = new ReadBuildInfoMessage(deviceAddress);
    NrioMessage rsp = sendNrioMessage(req);
    if (rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
    {
      rsp = sendNrioMessage(req);
    }
    if (rsp.getStatus() == NrioMessageConst.MESSAGE_STATUS_OK)
    {
      version.append(rsp.getData()[0] & 0x0ff);
      version.append('.');
      version.append(rsp.getData()[1] & 0x0ff);
    }
    //System.out.println("readBuildInfo( " + deviceAddress + " ) = " + version.toString());
    return version.toString();
  }

  public void readInfoMemory(int deviceAddress)
  {
    ReadInfoMemoryMessage req = new ReadInfoMemoryMessage(deviceAddress);
    NrioMessage rsp = sendNrioMessage(req);
    if (rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
      return;
    byte[] infoData = rsp.getData();
    System.out.println("             nodeAddr = " + (infoData[0] & 0x0ff));
    System.out.println("       numMsgsOurAddr = " + (infoData[1] & 0x0ff));
    System.out.println("          numMsgsRcvd = " + (infoData[2] & 0x0ff));
    System.out.println("       numBadMsgsRcvd = " + (infoData[3] & 0x0ff));
    System.out.println("   numMsgsTransmitted = " + (infoData[4] & 0x0ff));
    System.out.println(" numIOStatusCrcErrors = " + (infoData[5] & 0x0ff));
    System.out.println("numCardReadsProcessed = " + (infoData[6] & 0x0ff));
    System.out.println("  numCardReadsXmitted = " + (infoData[7] & 0x0ff));
    System.out.println("    numCardReadErrors = " + (infoData[8] & 0x0ff));
    System.out.println("    num485ResetsOnRcv = " + (infoData[9] & 0x0ff));
    System.out.println("       lastResetState = " + (infoData[10] & 0x0ff));
  }


  public byte[] sendRequest(byte[] request)
  {
    return ((NrioComm)getComm()).sendRequest(request);
  }


  public NrioMessage sendNrioMessage(NrioMessage request)
  {
    while (downLoadInProcess)
    {
      request.setStatus(255);
      return request;
    }
    byte[] msgData = request.getByteArray();
    BNrioDevice device = getDevice(msgData[0]);
    int msgType = request.getType();
    // NCCB-30508: if this device is in the process of writing output default value
    // block messages except for the two IO Default messages.
    if(device != null && device.isWriteOutputDefaultsInProgress())
    {
      if(msgType != MSG_WR_IO_DEFAULT_START && msgType != MSG_WR_IO_DEFAULT_MAP)
      {
        getConfigLog().trace(msgData[0] + ": WriteOutputDefaultsInProgress blocking nrio msgType: 0x" + Integer.toHexString(msgType));
        request.setStatus(255);
        return request;
      }
    }
    if (msgType == MSG_WR_CR_CONFIG)
    {
      Log configLog = getConfigLog();
      if (configLog.isTraceOn())
        configLog.trace("Writing config data to device: " + msgData[0]);
    }
    byte[] response = sendRequest(msgData);
    return (NrioMessage)request.toResponse(new NrioReceivedMessage(response, response.length, null));
  }

  public NrioMessage sendDownLoadMessage(NrioMessage request, int retry)
  {
    byte[] response;
    NrioMessage responseMsg;
    for (int i = 0; i <= retry; i++)
    {
      BUpgradeFirmwareJob.logger.finer("request: " + ByteArrayUtil.toHexString(request.getByteArray()));
      response = sendRequest(request.getByteArray());
      BUpgradeFirmwareJob.logger.finer("response: " + ByteArrayUtil.toHexString(response));
      responseMsg = (NrioMessage)request.toResponse(new NrioReceivedMessage(response, response.length, null));
      if (responseMsg != null && responseMsg.isOk())
        return responseMsg;
      if (retry - i > 0)
        BUpgradeFirmwareJob.logger.finer("sendDownLoadMessage error - retrying " + i + " times");
      else if (retry > 0)
        BUpgradeFirmwareJob.logger.finer("sendDownLoadMessage error - returning null");
    }
    return null;
  }

  public static BNrioPointDeviceExt getDeviceExt(BComponent c)
  {
    BComplex parent = c.getParent();
    while (!(parent instanceof BNrioPointDeviceExt))
    {
      parent = parent.getParent();
      if (parent == null)
        return null;
    }
    return (BNrioPointDeviceExt)parent;
  }

  public BNrioDevice[] getNrioDevices()
  {
    return (BNrioDevice[])getDevices();
  }

  public BNrioDevice getDevice(byte[] uid)
  {
    BBlob inUid = BBlob.make(uid);
    BDevice[] devices = getDevices();
    for (int i = 0; i < devices.length; i++)
    {
      if (devices[i] instanceof BNrioDevice)
      {
        if (((BNrioDevice)devices[i]).getUid().equals(inUid))
          return (BNrioDevice)devices[i];
      }
    }
    return null;
  }

  // device cache routines.

  public Vector<BNrioDevice> devices = new Vector<>();

  public void addDevice(BNrioDevice newDevice)
  {
    if(newDevice.getAddress() <=0)
    {
      getLog().message(" adding a device with an invalid address: " + newDevice.getSlotPath());
    }
    else if (!devices.contains(newDevice))
    {
      devices.add(newDevice);
      logicalAddressMap = logicalAddressMap | (1 << newDevice.getAddress());
      logicalTypeUidMap[newDevice.getAddress()-1] = new TypeUid(newDevice.getDeviceType().getRawInt(), newDevice.getUid().copyBytes());

      //System.out.println("nrioDevice added: " + newDevice.getName() + ", size = " + devices.size());
    }
  }

  public void removeDevice(int address)
  {
    for (int i = 0; i < devices.size(); i++)
    {
      if (devices.elementAt(i) instanceof BNrioDevice)
      {
        if (devices.elementAt(i).getAddress() == address)
        {
          devices.remove(i);
          logicalAddressMap = logicalAddressMap & ~(1 << address);
          logicalTypeUidMap[address-1] = null;
          return;
        }
      }
    }
    getLog().message("could not find device to remove: address = " + address + ", size = " + devices.size());

  }

  public BNrioDevice getDevice(int address)
  {
    for (int i = 0; i < devices.size(); i++)
    {
      if (devices.elementAt(i) instanceof BNrioDevice)
      {
        if (devices.elementAt(i).getAddress() == address)
          return devices.elementAt(i);
      }
    }
    return null;
  }

  public void doEnableWinking(BNrioLearnDeviceEntry learnDevice)
  {

    if (isDownLoadInProcess())
      return;
    getLog().trace(">>> BNrioDevice(" + getName() + ").doEnableWinking");
    // if the learned device is an IO-34 the secAddr will be a number 1-16
    final int secAddrInt = learnDevice.getSecAddrInt();
    PingMessage req = new PingMessage(learnDevice.getAddress(), learnDevice.getUid().copyBytes(), learnDevice.getDeviceType());
    sendNrioMessage(req);
    // if IO-34 learned device, we have to ping the secondary CPU and wait 1 second
    // then write config data to each CPU so that the wink relay can be controlled.
    if(secAddrInt > 0)
    {
      req = new PingMessage(secAddrInt, learnDevice.getUid().copyBytes(), BNrioDeviceTypeEnum.io34sec);
      sendNrioMessage(req);
      // give the IO-34 pri & sec CPU enough time to sync up.
      try { Thread.sleep(1000); } catch(Exception ignore) {}
    }
    WriteConfigMessage cReq = new WriteConfigMessage(learnDevice.getAddress(), 0x0202);
    sendNrioMessage(cReq);
    if(secAddrInt > 0)
    {
      cReq.setAddress(secAddrInt);
      sendNrioMessage(cReq);
    }
  }

  public void doDisableWinking(BNrioLearnDeviceEntry learnDevice)
  {
    // make sure wink output is turned off.
    WriteDOMessage req = new WriteDOMessage(learnDevice.getAddress(), 0);
    this.sendNrioMessage(req);

    disablePolling(learnDevice.getAddress());
  }

  public void doWinkDevice(BInteger value)
  {
    int address = value.getInt() >> 8;
    int wrValue = (value.getInt() & 0x0ff) << 8;

    WriteDOMessage req = new WriteDOMessage(address, wrValue);
    this.sendNrioMessage(req);
  }

  public BOrd doSubmitDeviceDiscoveryJob()
  {
    if (getStatus().isDisabled())
      return null;
    BNrioLearnDevicesJob job = new BNrioLearnDevicesJob(this);
    return job.submit(null);
  }

  public BOrd doUpgradeFirmware()
  {
    if (getStatus().isDisabled())
      return null;
    BUpgradeFirmwareJob job = new BUpgradeFirmwareJob(this);
    return job.submit(null);
  }

  public void setDownLoadInProcess(boolean value)
  {
    downLoadInProcess = value;
    BDevice[] devices = getDevices();
    for (int i = 0; i < devices.length; i++)
    {
      if (devices[i] instanceof BNrioDevice)
      {
        if (value)
          ((BNrioDevice)devices[i]).doDisablePolling();
        else if( !((BNrioDevice)devices[i]).isDisabled() )
          ((BNrioDevice)devices[i]).doEnablePolling();
      }
    }
  }

  public boolean isDownLoadInProcess()
  {
    return downLoadInProcess;
  }

  public void initLogicalAddressMapV2()
  {
    for (int i = 1; i <= 16; i++)
    {
      BNrioDevice device = getDevice(i);
      if (device != null)
      {
        int index = i-1;
        logicalTypeUidMap[index] = new TypeUid(device.getDeviceType().getRawInt(), device.getUid().copyBytes());
      }
    }
  }

  public void initLogicalAddressMap()
  {
    for (int i = 1; i <= 16; i++)
    {
      BNrioDevice device = getDevice(i);
      if (device != null)
      {
        logicalAddressMap = logicalAddressMap | (1 << i);
        logicalTypeUidMap[i-1] = new TypeUid(device.getDeviceType().getRawInt(), device.getUid().copyBytes());
      }
      else
      {
        logicalAddressMap = logicalAddressMap & ~(1 << i);
        logicalTypeUidMap[i-1] = null;

      }
    }
  }

  public int getLogicalAddress(byte[] uid)
  {
    BNrioDevice temp = getDevice(uid);
    if (temp == null)
      return -1;
    return temp.getAddress();
  }


  public int getFreeAddressV2(int moduleType, byte[] uid)
  {
    boolean isReader = moduleType == REMOTE_READER || moduleType == BASE_READER;
    int freeAddress = getFreeAddress(isReader);
    if(freeAddress > 0)
    {
      TypeUid thisTypeUid = new TypeUid(moduleType, uid);
      logicalTypeUidMap[freeAddress-1] = thisTypeUid;
    }
    return freeAddress;
  }

  private int getFreeAddress(boolean isReader)
  {
    int start = 1;
    int maxAddress = MAX_MODULE_ADDRESS;
    for (int i = start; i <= maxAddress; i++)
    {
      if ((logicalAddressMap & (1 << i)) == 0)
      {
        logicalAddressMap = logicalAddressMap | (1 << i);
        return i;
      }
    }
    // try to get an address of a device that is down
    maxAddress = MAX_MODULE_ADDRESS;
    for (int i = start; i <= maxAddress; i++)
    {
      if (getDevice(i) == null)
      {
        return -1;
      }
      if (getDevice(i).isDown())
        return i;
    }
    // else return -1;
    return -1;
  }

  public int getIo34OtherAddr(int type, byte[] uid)
  {
    int otherType = (type == REMOTE_IO_34_PRI) ? REMOTE_IO_34_SEC : REMOTE_IO_34_PRI;
    for (int i = 0; i < logicalTypeUidMap.length; ++i)
    {
      TypeUid typeUid = logicalTypeUidMap[i];
      if(typeUid == null)
        continue;
      if(ByteArrayUtil.equals(uid, typeUid.uid))
      {
        if(typeUid.type == otherType)
          return i+1;
      }
    }
    return -1;
  }

  public void clearAddressUsedV2(int address)
  {
    int index = address - 1;
    if (index >= logicalTypeUidMap.length || index < 0)
    {
      getLog().message(getName() + ": clearAddressUsedV2 invalid address: " + address);
      return;
    }
    if (logicalTypeUidMap[index] != null)
    {
      int existingType = logicalTypeUidMap[index].type;
      byte[] existingUid = logicalTypeUidMap[index].uid;
      logicalTypeUidMap[index] = null;
      logicalAddressMap = logicalAddressMap & ~(1 << address);

      if (existingType == REMOTE_IO_34_PRI || existingType == REMOTE_IO_34_SEC)
      {
        int otherAddr = getIo34OtherAddr(existingType, existingUid);
        if(otherAddr > 0)
        {
          // also clear otherAddr
          logicalTypeUidMap[otherAddr - 1] = null;
          logicalAddressMap = logicalAddressMap & ~(1 << otherAddr);

        }
      }
    }
  }

  public void clearAddressUsed(int address)
  {
    clearAddressUsedV2(address);
//    logicalAddressMap = logicalAddressMap & ~(1 << address);
  }

  public BIFile getFirmwareFile(BNrioDeviceTypeEnum type)
  {
    BIFile[] files = getFirmwareFiles(type);
    if(files == null || files.length == 0)
      return null;
    return files[0];
  }

  public BIFile[] getFirmwareFiles(BNrioDeviceTypeEnum type)
  {
    BModule module = BNrioNetwork.TYPE.getModule();
    BIFile downLoadDir = module.findFile(new FilePath("/download"));
    if (downLoadDir == null)
    {
      getLog().message("could not find download directory");
      return null;
    }

    BIFile[] downLoadFiles = module.getChildren(downLoadDir);
    String fileName = null;
    if (type.equals(BNrioDeviceTypeEnum.remoteReader))
      fileName = "2030";
    else if (type.equals(BNrioDeviceTypeEnum.remoteInputOutput))
      fileName = "2034";
    else if (type.equals(BNrioDeviceTypeEnum.baseBoardReader))
      fileName = "2029";
    else if (type.equals(BNrioDeviceTypeEnum.io16))
      fileName = "2041";
    else if (type.equals(BNrioDeviceTypeEnum.io16V1))
      fileName = "2101";
    else if (type.equals(BNrioDeviceTypeEnum.io34))
      fileName = "2102";
    else if (type.equals(BNrioDeviceTypeEnum.io34sec))
      fileName = "2102";
    if (fileName == null)
      return null;
    Vector<BIFile> v = new Vector<>();
    for (int i = 0; i < downLoadFiles.length; i++)
    {
      if (downLoadFiles[i].getFileName().indexOf(fileName) > 0)
        v.add(downLoadFiles[i]);
    }
    if(v.size() > 0)
    {
      BIFile[] rtnFiles = v.toArray(new BIFile[v.size()]);
      return rtnFiles;
    }
    return null;
  }


  ////////////////////////////////////////////////////////////////
//  Required run method
////////////////////////////////////////////////////////////////
  public void run()
  {
    while(!Sys.atSteadyState())
    {
      try{Thread.sleep(500L);}
      catch(Exception ignore){}
    }
    // NCCB-36223  ATS 9/12/18
    try // don't write any outputs until @steadyState + 30 seconds
    {
      Thread.sleep(30000L);
    }
    catch (Exception e)
    {
    }
    getWrIoLog().message("wrThread running");
    BDevice[] devices = getDevices();
    long updateDevicesTicks = Clock.ticks();
    while (!timeToDie)
    {

      try
      {
        if ((Clock.ticks() - updateDevicesTicks) > 30000l)
        {
          devices = getDevices();
          updateDevicesTicks = Clock.ticks();
          if (wrThread.getPriority() != getWriteThreadPriority())
          {
            setWriteThreadPriority(getWriteThreadPriority());
          }
        }
        for (int i = 0; i < devices.length; i++)
        {
          if (devices[i] != null && devices[i] instanceof BNrioDevice &&
            devices[i].isRunning() &&
            !devices[i].isDown() &&
            !devices[i].isFault() &&
            !devices[i].isDisabled())
          {
            ((BNrioDevice)devices[i]).doWriteDoValues();
          }
        }
      }
      catch (Exception e)
      {
        getLog().message("WriteDoValues caught Exception: " + e);

      }
      try
      {
        Thread.sleep(getWriteThreadSleepTime());
      }
      catch (Exception e)
      {
      }

    }
  }

  public void interruptWriteThread()
  {
    wrThread.interrupt();
  }

////////////////////////////////////////////////////////////////
// Basic Statistics
////////////////////////////////////////////////////////////////


  /**
   * Adds basic communication statistics to the spy page,
   * such as total messages sent/received.
   */
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    try
    {
      long totalProcessTime = getUnsolicitedReceive().getTotalProcessTime();
      long runTime = getUnsolicitedReceive().getRunTime();
      out.startProps();
      out.trTitle("NrioUnsolicited", 1);
      out.prop("Message Count", Long.valueOf(this.getUnsolicitedMsgCount()));
      out.prop("Avg Process Time(ms)", Double.valueOf((double)totalProcessTime / (double)getUnsolicitedMsgCount()));
      out.prop("Total Process Time(ms)", Long.valueOf(totalProcessTime));
      out.prop("Total Run Time(ms)", BRelTime.make(runTime));
      out.prop("Process Usage(%)", Double.valueOf((double)totalProcessTime) / (double)runTime * 100.0d);
      out.prop("Message Rate", Float.valueOf(this.getUnsolicitedMessageRate()));
      out.prop("DroppedByteCount", Long.valueOf(this.getUnsolicitedReceive().getDroppedByteCount()));
      out.prop("InvalidMsgCount", Long.valueOf(this.getUnsolicitedReceive().getInvalidMessageCount()));

      out.endProps();

      out.startProps();
      out.trTitle("NrioNetwork Device Cache", 1);
      for (int i = 0; i < devices.size(); i++)
      {
        BNrioDevice device = devices.elementAt(i);
        String name = "logicalAddr " + device.getAddress() + ": ";
        out.prop(name, device.getSlotPath());
      }
      out.endProps();

      out.startProps();
      out.trTitle("LogicalAddressUsedMap", 1);
      out.prop("map", Integer.toBinaryString(logicalAddressMap));
      out.endProps();

//      dumpLogicalTypeUidMap();
      out.startTable(true);
      {
        out.trTitle("NrioDevice Info", 3);
        out.w("<tr>").th("Addr").th("Type").th("UID").w("</tr>\n");
        for(int i = 0; i < logicalTypeUidMap.length; ++i)
        {
          TypeUid typeUid = logicalTypeUidMap[i];
          if(typeUid == null)
          {
            out.tr((i+1), "", "");
          }
          else
          {
            int type = typeUid.type;
            byte[] uid = typeUid.uid;
            out.tr((i + 1),
              BNrioDeviceTypeEnum.makeFromRaw(type),
              ByteArrayUtil.toHexString(uid));
          }
        }
      }
      out.endTable();

      out.startProps();
      out.trTitle("NrioNetwork Device Polling Data", 1);
      out.endProps();
      try
      {
        BufferedReader br = ((NrioComm)getComm()).readStats(getTrunk());

        out.w("<pre>\n");
        String line = br.readLine();
        while (line != null)
        {
          line = convertBogusChars(line);
          out.safe(line).w("\n");
          line = br.readLine();
        }
        out.w("</pre>\n");
      }
      catch(Exception e)
      {
        getLog().error("readStats caught Exception: " + e, e);
      }
    }
    catch (Exception e)
    {
      getLogger().log(Level.SEVERE, "Error occurred during NrioNetwork spy operation", e);
      out.safe(e.getLocalizedMessage());
    }
    out.endProps();
  }

  private String convertBogusChars(String src)
  {
    StringBuilder buf = new StringBuilder();
    char[] chars = src.toCharArray();

    for (int i = 0; i < chars.length; i++)
    {
      if (chars[i] == 9)
      {
        buf.append("        ");
      }
      else if (chars[i] == 0)
      {
      }
      else
      {
        buf.append(chars[i]);
      }
    }

    return buf.toString();
  }

  private class TypeUid
  {
    TypeUid(int type, byte[] uid)
    {
      this.type = type;
      this.uid = uid;
    }

    private boolean isPrimary()
    {
      return type == REMOTE_IO_34_PRI;
    }

    private boolean isSecondary()
    {
      return type == REMOTE_IO_34_SEC;
    }

    int type = 0;
    byte[] uid = new byte[0];
  }

  public void dumpLogicalTypeUidMap()
  {
    for (int i = 0; i < 16; ++i)
    {
      StringBuilder sb = new StringBuilder();
      sb.append(i+1).append(": ");
      if (logicalTypeUidMap[i] != null)
      {
        sb.append(BNrioDeviceTypeEnum.makeFromRaw(logicalTypeUidMap[i].type)).append(", ");
        sb.append(ByteArrayUtil.toHexString(logicalTypeUidMap[i].uid));
      }
      System.out.println(sb.toString());
    }
    System.out.println();
  }



  public Log getConfigLog()
  {
    return Log.getLog(getName() + ".config");  
  }

  ////////////////////////////////////////////////////////////////
  // test stuff
  ////////////////////////////////////////////////////////////////
//    public void doTestToggleUnsolicitedMessageProcessing()
//    {
//      if(unsolicitedReceive != null)
//        unsolicitedReceive.toggleMessageProcessing();
//    }
    
  ////////////////////////////////////////////////////////////////
  // Attributes
  ////////////////////////////////////////////////////////////////
  
  private NrioUnsolicitedReceive  unsolicitedReceive = null;
  private NrioComm                nrioComm         = null;
  private Thread                    wrThread;
  public Log getWrIoLog()
  {
    return Log.getLog(getName() + ".wrIo");
  }
  public Log getAddressLog()
  {
    return Log.getLog(getName() + ".addrMap");
  }
  public long getUnsolicitedProcessedCount() { return unsolicitedProcessedCount; }
  public long incUnsolicitedProcessedCount() { return ++unsolicitedProcessedCount; }

  
  public boolean isSecurityLicensed = false;
  public boolean isNrioLicensed     = false;
  private boolean downLoadInProcess = false;
  private boolean timeToDie = true;
  private boolean isStarted = false;
  public int     logicalAddressMap = 0;
  private TypeUid[] logicalTypeUidMap = new TypeUid[16];
  private long    replyTicks = 0;
  private long unsolicitedProcessedCount = 0;

  protected static byte[]   NULL_BA = new byte[0];
  public  static Lexicon  lex = Lexicon.make(BNrioNetwork.class);

}
