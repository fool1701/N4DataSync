/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.alarm.BBacnetAlarmDeviceExt;
import javax.baja.bacnet.config.BBacnetConfigDeviceExt;
import javax.baja.bacnet.config.BBacnetDeviceObject;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetOctetString;
import javax.baja.bacnet.datatypes.BBacnetPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.device.LatencyRecorder;
import javax.baja.bacnet.device.LatencyRecorderAware;
import javax.baja.bacnet.device.overrides.ApduSizeOverride;
import javax.baja.bacnet.device.overrides.DeviceOverride;
import javax.baja.bacnet.device.overrides.DeviceOverrideAware;
import javax.baja.bacnet.device.overrides.SegmentationOverride;
import javax.baja.bacnet.device.overrides.ServiceOverride;
import javax.baja.bacnet.enums.BBacnetAbortReason;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetRejectReason;
import javax.baja.bacnet.enums.BBacnetSegmentation;
import javax.baja.bacnet.enums.BCharacterSetEncoding;
import javax.baja.bacnet.enums.BExtensibleEnumList;
import javax.baja.bacnet.io.AbortException;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorException;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.point.BBacnetPointDeviceExt;
import javax.baja.bacnet.point.BBacnetProxyExt;
import javax.baja.bacnet.point.BBacnetTuningPolicy;
import javax.baja.bacnet.util.BIBacnetPollable;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.util.PollList;
import javax.baja.bacnet.util.PollListEntry;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.bacnet.virtual.BBacnetVirtualGateway;
import javax.baja.control.BControlPoint;
import javax.baja.driver.history.BHistoryDeviceExt;
import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BLoadableDevice;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.driver.schedule.BScheduleDeviceExt;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.TextUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Clock.Ticket;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.ObjectTypeList;
import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadAccessSpec;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.history.BBacnetHistoryDeviceExt;
import com.tridium.bacnet.schedule.BBacnetScheduleDeviceExt;
import com.tridium.bacnet.stack.BBacnetPoll;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.DeviceRegistry;
import com.tridium.bacnet.stack.client.BBacnetClientLayer;
import com.tridium.bacnet.stack.link.BBacnetLinkLayer;
import com.tridium.bacnet.stack.link.ethernet.BBacnetEthernetLinkLayer;
import com.tridium.bacnet.stack.link.ip.BBacnetIpLinkLayer;
import com.tridium.bacnet.stack.link.mstp.BBacnetMstpLinkLayer;
import com.tridium.bacnet.stack.link.sc.BScLinkLayer;
import com.tridium.bacnet.stack.network.BNetworkPort;
import com.tridium.bacnet.stack.transport.TransactionException;
import com.tridium.bacnet.stack.transport.TransactionTimeoutException;

/**
 * BBacnetDevice represents the Baja shadow object for a Bacnet device.
 * <p>
 * Properties such as the device address, which are not Bacnet Device
 * properties, but which are associated with this device, are contained
 * in a BBacnetDeviceData object child of this device.
 * <p>
 * DeviceExts for status monitoring and point monitoring are included,
 * as well as a configuration object used for examining the device in
 * its native object model as a container of Bacnet Objects.
 *
 * @author Craig Gemmill
 * @version $Revision: 1$ $Date: 12/19/01 4:32:51 PM$
 * @creation 27 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 the device's address.
 */
@NiagaraProperty(
  name = "address",
  type = "BBacnetAddress",
  defaultValue = "BBacnetAddress.LOCAL_BROADCAST_ADDRESS",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 Point mapping device extension.
 */
@NiagaraProperty(
  name = "points",
  type = "BBacnetPointDeviceExt",
  defaultValue = "new BBacnetPointDeviceExt()"
)
/*
 Virtual Point container
 */
@NiagaraProperty(
  name = "virtual",
  type = "BBacnetVirtualGateway",
  defaultValue = "new BBacnetVirtualGateway()"
)
/*
 Alarm device extension.
 */
@NiagaraProperty(
  name = "alarms",
  type = "BBacnetAlarmDeviceExt",
  defaultValue = "new BBacnetAlarmDeviceExt()"
)
/*
 Schedule device extension.
 */
@NiagaraProperty(
  name = "schedules",
  type = "BScheduleDeviceExt",
  defaultValue = "new BBacnetScheduleDeviceExt()"
)
/*
 History device extension.
 */
@NiagaraProperty(
  name = "trendLogs",
  type = "BHistoryDeviceExt",
  defaultValue = "new BBacnetHistoryDeviceExt()"
)
/*
 BACnet Object commissioning/configuration.
 */
@NiagaraProperty(
  name = "config",
  type = "BBacnetConfigDeviceExt",
  defaultValue = "new BBacnetConfigDeviceExt()"
)
/*
 Management of proprietary extensions to extensible enumerations.
 */
@NiagaraProperty(
  name = "enumerationList",
  type = "BExtensibleEnumList",
  defaultValue = "new BExtensibleEnumList()"
)
/*
 flag indicating if Niagara will use COV notification services to
 receive data about points in this device for which COV is supported.
 */
@NiagaraProperty(
  name = "useCov",
  type = "boolean",
  defaultValue = "false"
)
/*
 flag indicating if Niagara will use COV notification services to
 receive data about points in this device for which COVP is supported.
 */
@NiagaraProperty(
  name = "useCovProperty",
  type = "boolean",
  defaultValue = "false"
)
/*
 the maximum number of COV subscriptions that Niagara will attempt to
 initiate to this device.
 */
@NiagaraProperty(
  name = "maxCovSubscriptions",
  type = "int",
  defaultValue = "Integer.MAX_VALUE"
)
/*
 the number of COV subscriptions currently active to this device.
 */
@NiagaraProperty(
  name = "covSubscriptions",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 which Poll frequency bucket does this device belong in?
 deprecated as of 3.2.
 */
@NiagaraProperty(
  name = "pollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal",
  flags = Flags.HIDDEN | Flags.READONLY
)
/*
 which default character set is currently being used by this device.
 */
@NiagaraProperty(
  name = "characterSet",
  type = "BCharacterSetEncoding",
  defaultValue = "BCharacterSetEncoding.iso10646_UTF8",
  flags = Flags.READONLY
)
/*
 the maximum number of consecutive poll timeouts before marking polled
 points into fault.  The device is pinged on each timeout, and only
 marked down if the ping fails.
 */
@NiagaraProperty(
  name = "maxPollTimeouts",
  type = "int",
  defaultValue = "0",
  flags = Flags.HIDDEN,
  facets = @Facet(name = "BFacets.MIN", value = "0")
)
/*
 flag to configure if the device should be marked as down if
 Subscribe/Unsubscribe Cov/CovProperty (and retries) have failed.
 */
@NiagaraProperty(
  name = "disableDeviceOnCovSubscriptionFailure",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "macAddressFailed",
  flags = Flags.HIDDEN
)
public class BBacnetDevice
  extends BLoadableDevice
  implements BacnetConst,
  BIBacnetPollable,
  BIBacnetObjectContainer,
  DeviceOverrideAware,
  LatencyRecorderAware,
  LatencyRecorder
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.BBacnetDevice(2313156081)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * the device's address.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetAddress.LOCAL_BROADCAST_ADDRESS, null);

  /**
   * Get the {@code address} property.
   * the device's address.
   * @see #address
   */
  public BBacnetAddress getAddress() { return (BBacnetAddress)get(address); }

  /**
   * Set the {@code address} property.
   * the device's address.
   * @see #address
   */
  public void setAddress(BBacnetAddress v) { set(address, v, null); }

  //endregion Property "address"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * Point mapping device extension.
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BBacnetPointDeviceExt(), null);

  /**
   * Get the {@code points} property.
   * Point mapping device extension.
   * @see #points
   */
  public BBacnetPointDeviceExt getPoints() { return (BBacnetPointDeviceExt)get(points); }

  /**
   * Set the {@code points} property.
   * Point mapping device extension.
   * @see #points
   */
  public void setPoints(BBacnetPointDeviceExt v) { set(points, v, null); }

  //endregion Property "points"

  //region Property "virtual"

  /**
   * Slot for the {@code virtual} property.
   * Virtual Point container
   * @see #getVirtual
   * @see #setVirtual
   */
  public static final Property virtual = newProperty(0, new BBacnetVirtualGateway(), null);

  /**
   * Get the {@code virtual} property.
   * Virtual Point container
   * @see #virtual
   */
  public BBacnetVirtualGateway getVirtual() { return (BBacnetVirtualGateway)get(virtual); }

  /**
   * Set the {@code virtual} property.
   * Virtual Point container
   * @see #virtual
   */
  public void setVirtual(BBacnetVirtualGateway v) { set(virtual, v, null); }

  //endregion Property "virtual"

  //region Property "alarms"

  /**
   * Slot for the {@code alarms} property.
   * Alarm device extension.
   * @see #getAlarms
   * @see #setAlarms
   */
  public static final Property alarms = newProperty(0, new BBacnetAlarmDeviceExt(), null);

  /**
   * Get the {@code alarms} property.
   * Alarm device extension.
   * @see #alarms
   */
  public BBacnetAlarmDeviceExt getAlarms() { return (BBacnetAlarmDeviceExt)get(alarms); }

  /**
   * Set the {@code alarms} property.
   * Alarm device extension.
   * @see #alarms
   */
  public void setAlarms(BBacnetAlarmDeviceExt v) { set(alarms, v, null); }

  //endregion Property "alarms"

  //region Property "schedules"

  /**
   * Slot for the {@code schedules} property.
   * Schedule device extension.
   * @see #getSchedules
   * @see #setSchedules
   */
  public static final Property schedules = newProperty(0, new BBacnetScheduleDeviceExt(), null);

  /**
   * Get the {@code schedules} property.
   * Schedule device extension.
   * @see #schedules
   */
  public BScheduleDeviceExt getSchedules() { return (BScheduleDeviceExt)get(schedules); }

  /**
   * Set the {@code schedules} property.
   * Schedule device extension.
   * @see #schedules
   */
  public void setSchedules(BScheduleDeviceExt v) { set(schedules, v, null); }

  //endregion Property "schedules"

  //region Property "trendLogs"

  /**
   * Slot for the {@code trendLogs} property.
   * History device extension.
   * @see #getTrendLogs
   * @see #setTrendLogs
   */
  public static final Property trendLogs = newProperty(0, new BBacnetHistoryDeviceExt(), null);

  /**
   * Get the {@code trendLogs} property.
   * History device extension.
   * @see #trendLogs
   */
  public BHistoryDeviceExt getTrendLogs() { return (BHistoryDeviceExt)get(trendLogs); }

  /**
   * Set the {@code trendLogs} property.
   * History device extension.
   * @see #trendLogs
   */
  public void setTrendLogs(BHistoryDeviceExt v) { set(trendLogs, v, null); }

  //endregion Property "trendLogs"

  //region Property "config"

  /**
   * Slot for the {@code config} property.
   * BACnet Object commissioning/configuration.
   * @see #getConfig
   * @see #setConfig
   */
  public static final Property config = newProperty(0, new BBacnetConfigDeviceExt(), null);

  /**
   * Get the {@code config} property.
   * BACnet Object commissioning/configuration.
   * @see #config
   */
  public BBacnetConfigDeviceExt getConfig() { return (BBacnetConfigDeviceExt)get(config); }

  /**
   * Set the {@code config} property.
   * BACnet Object commissioning/configuration.
   * @see #config
   */
  public void setConfig(BBacnetConfigDeviceExt v) { set(config, v, null); }

  //endregion Property "config"

  //region Property "enumerationList"

  /**
   * Slot for the {@code enumerationList} property.
   * Management of proprietary extensions to extensible enumerations.
   * @see #getEnumerationList
   * @see #setEnumerationList
   */
  public static final Property enumerationList = newProperty(0, new BExtensibleEnumList(), null);

  /**
   * Get the {@code enumerationList} property.
   * Management of proprietary extensions to extensible enumerations.
   * @see #enumerationList
   */
  public BExtensibleEnumList getEnumerationList() { return (BExtensibleEnumList)get(enumerationList); }

  /**
   * Set the {@code enumerationList} property.
   * Management of proprietary extensions to extensible enumerations.
   * @see #enumerationList
   */
  public void setEnumerationList(BExtensibleEnumList v) { set(enumerationList, v, null); }

  //endregion Property "enumerationList"

  //region Property "useCov"

  /**
   * Slot for the {@code useCov} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COV is supported.
   * @see #getUseCov
   * @see #setUseCov
   */
  public static final Property useCov = newProperty(0, false, null);

  /**
   * Get the {@code useCov} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COV is supported.
   * @see #useCov
   */
  public boolean getUseCov() { return getBoolean(useCov); }

  /**
   * Set the {@code useCov} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COV is supported.
   * @see #useCov
   */
  public void setUseCov(boolean v) { setBoolean(useCov, v, null); }

  //endregion Property "useCov"

  //region Property "useCovProperty"

  /**
   * Slot for the {@code useCovProperty} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COVP is supported.
   * @see #getUseCovProperty
   * @see #setUseCovProperty
   */
  public static final Property useCovProperty = newProperty(0, false, null);

  /**
   * Get the {@code useCovProperty} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COVP is supported.
   * @see #useCovProperty
   */
  public boolean getUseCovProperty() { return getBoolean(useCovProperty); }

  /**
   * Set the {@code useCovProperty} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COVP is supported.
   * @see #useCovProperty
   */
  public void setUseCovProperty(boolean v) { setBoolean(useCovProperty, v, null); }

  //endregion Property "useCovProperty"

  //region Property "maxCovSubscriptions"

  /**
   * Slot for the {@code maxCovSubscriptions} property.
   * the maximum number of COV subscriptions that Niagara will attempt to
   * initiate to this device.
   * @see #getMaxCovSubscriptions
   * @see #setMaxCovSubscriptions
   */
  public static final Property maxCovSubscriptions = newProperty(0, Integer.MAX_VALUE, null);

  /**
   * Get the {@code maxCovSubscriptions} property.
   * the maximum number of COV subscriptions that Niagara will attempt to
   * initiate to this device.
   * @see #maxCovSubscriptions
   */
  public int getMaxCovSubscriptions() { return getInt(maxCovSubscriptions); }

  /**
   * Set the {@code maxCovSubscriptions} property.
   * the maximum number of COV subscriptions that Niagara will attempt to
   * initiate to this device.
   * @see #maxCovSubscriptions
   */
  public void setMaxCovSubscriptions(int v) { setInt(maxCovSubscriptions, v, null); }

  //endregion Property "maxCovSubscriptions"

  //region Property "covSubscriptions"

  /**
   * Slot for the {@code covSubscriptions} property.
   * the number of COV subscriptions currently active to this device.
   * @see #getCovSubscriptions
   * @see #setCovSubscriptions
   */
  public static final Property covSubscriptions = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code covSubscriptions} property.
   * the number of COV subscriptions currently active to this device.
   * @see #covSubscriptions
   */
  public int getCovSubscriptions() { return getInt(covSubscriptions); }

  /**
   * Set the {@code covSubscriptions} property.
   * the number of COV subscriptions currently active to this device.
   * @see #covSubscriptions
   */
  public void setCovSubscriptions(int v) { setInt(covSubscriptions, v, null); }

  //endregion Property "covSubscriptions"

  //region Property "pollFrequency"

  /**
   * Slot for the {@code pollFrequency} property.
   * which Poll frequency bucket does this device belong in?
   * deprecated as of 3.2.
   * @see #getPollFrequency
   * @see #setPollFrequency
   */
  public static final Property pollFrequency = newProperty(Flags.HIDDEN | Flags.READONLY, BPollFrequency.normal, null);

  /**
   * Get the {@code pollFrequency} property.
   * which Poll frequency bucket does this device belong in?
   * deprecated as of 3.2.
   * @see #pollFrequency
   */
  public BPollFrequency getPollFrequency() { return (BPollFrequency)get(pollFrequency); }

  /**
   * Set the {@code pollFrequency} property.
   * which Poll frequency bucket does this device belong in?
   * deprecated as of 3.2.
   * @see #pollFrequency
   */
  public void setPollFrequency(BPollFrequency v) { set(pollFrequency, v, null); }

  //endregion Property "pollFrequency"

  //region Property "characterSet"

  /**
   * Slot for the {@code characterSet} property.
   * which default character set is currently being used by this device.
   * @see #getCharacterSet
   * @see #setCharacterSet
   */
  public static final Property characterSet = newProperty(Flags.READONLY, BCharacterSetEncoding.iso10646_UTF8, null);

  /**
   * Get the {@code characterSet} property.
   * which default character set is currently being used by this device.
   * @see #characterSet
   */
  public BCharacterSetEncoding getCharacterSet() { return (BCharacterSetEncoding)get(characterSet); }

  /**
   * Set the {@code characterSet} property.
   * which default character set is currently being used by this device.
   * @see #characterSet
   */
  public void setCharacterSet(BCharacterSetEncoding v) { set(characterSet, v, null); }

  //endregion Property "characterSet"

  //region Property "maxPollTimeouts"

  /**
   * Slot for the {@code maxPollTimeouts} property.
   * the maximum number of consecutive poll timeouts before marking polled
   * points into fault.  The device is pinged on each timeout, and only
   * marked down if the ping fails.
   * @see #getMaxPollTimeouts
   * @see #setMaxPollTimeouts
   */
  public static final Property maxPollTimeouts = newProperty(Flags.HIDDEN, 0, BFacets.make(BFacets.MIN, 0));

  /**
   * Get the {@code maxPollTimeouts} property.
   * the maximum number of consecutive poll timeouts before marking polled
   * points into fault.  The device is pinged on each timeout, and only
   * marked down if the ping fails.
   * @see #maxPollTimeouts
   */
  public int getMaxPollTimeouts() { return getInt(maxPollTimeouts); }

  /**
   * Set the {@code maxPollTimeouts} property.
   * the maximum number of consecutive poll timeouts before marking polled
   * points into fault.  The device is pinged on each timeout, and only
   * marked down if the ping fails.
   * @see #maxPollTimeouts
   */
  public void setMaxPollTimeouts(int v) { setInt(maxPollTimeouts, v, null); }

  //endregion Property "maxPollTimeouts"

  //region Property "disableDeviceOnCovSubscriptionFailure"

  /**
   * Slot for the {@code disableDeviceOnCovSubscriptionFailure} property.
   * flag to configure if the device should be marked as down if
   * Subscribe/Unsubscribe Cov/CovProperty (and retries) have failed.
   * @see #getDisableDeviceOnCovSubscriptionFailure
   * @see #setDisableDeviceOnCovSubscriptionFailure
   */
  public static final Property disableDeviceOnCovSubscriptionFailure = newProperty(Flags.HIDDEN, true, null);

  /**
   * Get the {@code disableDeviceOnCovSubscriptionFailure} property.
   * flag to configure if the device should be marked as down if
   * Subscribe/Unsubscribe Cov/CovProperty (and retries) have failed.
   * @see #disableDeviceOnCovSubscriptionFailure
   */
  public boolean getDisableDeviceOnCovSubscriptionFailure() { return getBoolean(disableDeviceOnCovSubscriptionFailure); }

  /**
   * Set the {@code disableDeviceOnCovSubscriptionFailure} property.
   * flag to configure if the device should be marked as down if
   * Subscribe/Unsubscribe Cov/CovProperty (and retries) have failed.
   * @see #disableDeviceOnCovSubscriptionFailure
   */
  public void setDisableDeviceOnCovSubscriptionFailure(boolean v) { setBoolean(disableDeviceOnCovSubscriptionFailure, v, null); }

  //endregion Property "disableDeviceOnCovSubscriptionFailure"

  //region Action "macAddressFailed"

  /**
   * Slot for the {@code macAddressFailed} action.
   * @see #macAddressFailed()
   */
  public static final Action macAddressFailed = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code macAddressFailed} action.
   * @see #macAddressFailed
   */
  public void macAddressFailed() { invoke(macAddressFailed, null, null); }

  //endregion Action "macAddressFailed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  public BBacnetDevice()
  {
  }


////////////////////////////////////////////////////////////////
// BIBacnetPollable
////////////////////////////////////////////////////////////////

  /**
   * Get the containing device object which will poll this object.
   *
   * @return the containing BBacnetDevice
   */
  public final BBacnetDevice device()
  {
    return this;
  }

  /**
   * Get the pollable type of this object.
   *
   * @return one of the pollable types defined in BIBacnetPollable.
   */
  public final int getPollableType()
  {
    return BACNET_POLLABLE_DEVICE;
  }

  // FIXX:Temporary place holders....

  /**
   * Poll all subscribed points in the device.
   *
   * @deprecated
   */
  @Deprecated
  public boolean poll()
  {
    log.warning("BBacnetDevice.poll() is no longer used.");
    return true;
  }

  /**
   * Indicate a failure polling this object.
   *
   * @param failureMsg
   */
  public final void readFail(String failureMsg)
  {
  }

  /**
   * Normalize the encoded data into the pollable's data structure.
   *
   * @param encodedValue
   * @param status
   * @param cx
   */
  public final void fromEncodedValue(byte[] encodedValue, BStatus status, Context cx)
  {
  }

  /**
   * Get the list of poll list entries for this pollable.
   * The first entry for points must be the configured property.
   *
   * @return the list of poll list entries.
   */
  public final PollListEntry[] getPollListEntries()
  {
    return new PollListEntry[0];
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("BacnetDevice {").append(getName()).append("}");
//      .append(getObjectId().toString(context));
    return sb.toString();
  }

  /**
   * Get the object ID of this device.
   *
   * @return the device's object identifier.
   */
  public final BBacnetObjectIdentifier getObjectId()
  {
    return getDeviceObject().getObjectId();
  }

  /**
   * Set the object ID of this device.
   *
   * @param objectId
   */
  public final void setObjectId(BBacnetObjectIdentifier objectId, Context cx)
  {
    getDeviceObject().set(BBacnetObject.objectId, objectId, cx);
  }

  public final void objectIdChanged()
  {
    if (getObjectId().isValid())
    {
      configOk();
      network().postAsync(new Runnable()
      {
        public void run()
        {
          checkAddress();
          upload(new BUploadParameters(false));
        }
      });
    }
    else
      configFail("Invalid Device Object ID");
  }

  /**
   * Get the maximum APDU length accepted by this device.
   *
   * @return the integer maximum APDU length.
   */
  public final int getMaxAPDULengthAccepted()
  {
    int overridenMaxApdu = getOverridenAdpuSize(maxAPDU);
    return Math.max(overridenMaxApdu, MIN_APDU);
  }

  /**
   * Set the maximum APDU length accepted by this device.
   *
   * @param maxAPDULengthAccepted
   */
  public final void setMaxAPDULengthAccepted(int maxAPDULengthAccepted, Context cx)
//    { getDeviceObject().set(BBacnetDeviceObject.maxAPDULengthAccepted, BBacnetUnsigned.make(maxAPDULengthAccepted), cx); }
  {
    maxAPDU = maxAPDULengthAccepted;
  }

  /**
   * Get the device's segmentation support.
   *
   * @return the segmentation support enumeration for this device.
   */
  public final BBacnetSegmentation getSegmentationSupported()
  {
    SegmentationOverride segmentationOverride = getSegmentationOverride();
    if (segmentationOverride == null)
      return getDeviceObject().getSegmentationSupported();

    return segmentationOverride.getSegmentationSupported(getDeviceObject());
  }

  /**
   * Set the device's segmentation support.
   *
   * @param segmentationSupported
   */
  public final void setSegmentationSupported(BBacnetSegmentation segmentationSupported, Context cx)
  {
    getDeviceObject().set(BBacnetDeviceObject.segmentationSupported, segmentationSupported, cx);
  }

  /**
   * Get the device's number of segments accepted.
   *
   * @return the max number of segments accepted.
   */
  public final int getMaxSegmentsAccepted()
  {
    return getDeviceObject().getMaxSegmentsAccepted();
  }

  /**
   * Get the device's vendor identifier.
   *
   * @return the integer vendor ID.
   */
  public final int getVendorId()
  {
    return getDeviceObject().getVendorIdentifier().getInt();
  }

  /**
   * Set the device's vendor identifier.
   *
   * @param vendorId
   */
  public final void setVendorId(int vendorId, Context cx)
  {
    getDeviceObject().set(BBacnetDeviceObject.vendorIdentifier, BBacnetUnsigned.make(vendorId), cx);
  }

  /**
   * Is the service with the given service ID supported by this device?
   *
   * @param serviceId the service ID of the service type, as
   *                  specified in the BacnetServicesSupported bit string,
   *                  defined in Section 21 of the Bacnet specification.
   * @return true if the device indicates support for this service.
   */
  public boolean isServiceSupported(int serviceId)
  {
    return getServicesSupported(getDeviceObject().getProtocolServicesSupported()).getBit(serviceId);
  }

  /**
   * Is the service with the given serviceName supported by this device?
   *
   * @param serviceName the name of the service, as
   *                    specified in the BacnetServicesSupported bit string,
   *                    defined in Section 21 of the Bacnet specification.
   * @return true if the device indicates support for this service.
   */
  public boolean isServiceSupported(String serviceName)
  {
    return getServicesSupported(getDeviceObject().getProtocolServicesSupported()).getBit(
      BacnetBitStringUtil.getBitIndex(BacnetBitStringUtil.BACNET_SERVICES_SUPPORTED, serviceName));
  }

  /**
   * Is the object type with the given ID supported by this device?
   *
   * @param objectType the object type, as specified in the
   *                   BacnetObjectTypesSupported bit string,
   *                   defined in Section 21 of the Bacnet specification.
   * @return true if the device indicates support for this object type.
   */
  public boolean isObjectTypeSupported(int objectType)
  {
    return getDeviceObject().getProtocolObjectTypesSupported().getBit(objectType);
  }

  /**
   * Get the Protocol_Revision property.
   */
  public final int getProtocolRevision()
  {
    return getDeviceObject().getProtocolRevision().getInt();
  }

  /**
   * @deprecated
   */
  @Deprecated
  public BValue getObjectListStaleTime()
  {
    return get("objectListStaleTime");
  }

////////////////////////////////////////////////////////////////
//  Convenience methods
////////////////////////////////////////////////////////////////

  protected BBacnetNetwork network()
  {
    return (BBacnetNetwork)getNetwork();
  }

  private BBacnetClientLayer client()
  {
    return ((BBacnetStack)network().getBacnetComm()).getClient();
  }

  /**
   * Get the device object for this device.
   */
  private BBacnetDeviceObject getDeviceObject()
  {
    return getConfig().getDeviceObject();
  }

  public boolean isAddressValid()
  {
    BBacnetAddress addr = getAddress();
    return addr != null && !addr.getMacAddress().isNull();
  }

  public void checkAddress()
  {
    try
    {
      int instanceNumber = getObjectId().getInstanceNumber();
      if (instanceNumber >= 0)
      {
        client().whoIs(BBacnetAddress.GLOBAL_BROADCAST_ADDRESS,
          instanceNumber,
          instanceNumber);
      }

    }
    catch (BacnetException e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "BacnetException checking address for " + this + ": " + e, e);
      }
    }
  }

  private boolean isAws()
  {
    if (isAws == null)
    {
      isAws = BBoolean.FALSE;
      try
      {
        BOrd serviceOrd = BOrd.make("service:bacnet:BacnetNetwork");
        BBacnetNetwork bacnet = (BBacnetNetwork)serviceOrd.get(this);
        if ((bacnet != null) && (bacnet.getType().getTypeName().indexOf("Aws") >= 0))
          isAws = BBoolean.TRUE;
      }
      catch (Exception e)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("Could not determine AWS status for " + this + ":" + e);
        }
      }
    }
    return isAws.getBoolean();
  }


////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

  /**
   * Device started.
   */
  public void started()
    throws Exception
  {
    super.started();
    // Check object id.
    if (!getObjectId().isValid())
    {
      configFail("Invalid Device Object ID");
      return;
    }
    checkForDuplicateDeviceId();

    if (!isAddressValid())
    {
      setStatus(BStatus.stale);
      checkAddress();

      synchronized (DEVICE_LOCK)
      {
        if (staleTicket == null)
        {
          staleTicket = Clock.schedule(this,
            network().getMonitor().getStartupAlarmDelay(),
            macAddressFailed, null);
        }
      }
    }

    initializeDevice();
    if (log.isLoggable(Level.FINEST))
    {
      log.finest(this + " device started execution finish.");
    }
  }

  /**
   * Callback to indicate the supported Protocol Services (e.g. RPM)
   * support has changed.
   * <p>
   * This method will replace the local cache using
   * values from the device's BBacnetDeviceObject.
   */
  public void updateServicesSupported()
  {
    rpmOk = isServiceSupported("readPropertyMultiple");
  }

  private void updateMaxAPDU()
  {
    //If the device is newly discovered it may not have been uploaded yet.
    //In the discovery case the device registry already contains a valid APDU value.
    maxAPDU = getDeviceObject().getMaxAPDULengthAccepted().getInt();
    if (maxAPDU <= MIN_APDU && isAddressValid())
    {
      int registrySize = DeviceRegistry.getMaxApduLengthSupported(getAddress());
      maxAPDU = Math.max(maxAPDU, registrySize);
    }

    if (maxAPDU > MIN_APDU)
    {
      getConfig().getDeviceObject().set(
        BBacnetDeviceObject.maxAPDULengthAccepted,
        BBacnetUnsigned.make(maxAPDU), noWrite);
    }
  }

  private void updateSegmentationSupported()
  {
    //If the device is newly discovered it may not have been uploaded yet.
    //In the discovery case the device registry already contains a valid segmentation value.
    BBacnetSegmentation segmentationSupported = getDeviceObject().getSegmentationSupported();
    if (segmentationSupported == BBacnetSegmentation.noSegmentation && isAddressValid())
    {
      BBacnetSegmentation registrySegmentation =
        DeviceRegistry.getSegmentationSupported(getAddress());

      if (registrySegmentation != BBacnetSegmentation.noSegmentation)
      {
        setSegmentationSupported(registrySegmentation, noWrite);
      }
    }
  }

  protected void initializeDevice()
  {
    oldAddress = getAddress();
    updateServicesSupported();
    updateSegmentationSupported();
    updateMaxAPDU();

    isAws = null;

    // Register our address with the BBacnetNetwork container.
    BBacnetNetwork.localDevice().addAddressBinding(this);
    DeviceRegistry.update(this);
    BBacnetNetwork.bacnet().registerDevice(this);

    // Set the schedule support appropriate for the device's protocol revision.
    ((BBacnetScheduleDeviceExt)getSchedules()).setSupport(getProtocolRevision());

    // Generate vendor-specific object types list if needed.
    network().postAsync(new Runnable()
    {
      public void run()
      {
        getVendorObjectTypesList();
      }
    });
  }

  //Check for duplicate devices. 
  private void checkForDuplicateDeviceId()
  {
    BBacnetDevice dup = network().doLookupDeviceById(getObjectId());
    if ((dup != null) && (dup != this))
    {
      configFail("Duplicate Device Object ID");
      setObjectId(BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE), null);
      return;
    }
  }

  /**
   * Descendants started.
   */
  public void descendantsStarted()
  {
    // If we have been added to a running station, the network
    // will already have gone to networkReady, so make that call.
    if (network().isNetworkReady()) networkReady();
  }

  /**
   * Should the device be uploaded on start?
   *
   * @return true, if device should be uploaded
   * false, if the device should not be uploaded
   */
  protected boolean uploadOnStart()
  {
    BValue skipUpload = get(SKIP_UPLOAD);
    return skipUpload == null || skipUpload.equals(BBoolean.FALSE);
  }

  /**
   * Network ready.
   */
  void networkReady()
  {
    if (getObjectId().isValid() &&
      getStatus().isValid() &&
      uploadOnStart())
    {
      // We used to attempt an upload here that also did a ping. For link layers that are not ready
      // right away (co-processor MSTP and SC), the ping could fail, the device would be marked
      // down, and nuisance alarms would be raised. The upload was moved to first pingOk. The soft
      // ping below will call pingOk if successful but not pingFailed if not. For link layers that
      // are ready right away (Ethernet, IP, legacy MSTP), this early pingOk will cause an earlier
      // upload, which could optimize polling. This is also important for devices added to a running
      // station.
      if (log.isLoggable(Level.FINE))
      {
        log.fine(this + ": sending soft ping to trigger an upload at start");
      }
      postSoftPing();
    }
  }

  private void postUpload()
  {
    network().postAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          if (!isAddressValid())
          {
            checkAddress();
          }
          else
          {
            if (BBacnetDevice.this.getStatus().isOk())
            {
              // This method is used instead of doUpload to avoid an extra ping because this upload
              // is posted by pingOk or when receiving an IAm message.
              uploadDevice(new BUploadParameters(), null);

              //If the network is set to not "uploadOnStart" then
              //once a device has been initially uploaded, 
              //set the device's uploadOnStart to false.
              //This will prevent an automatic upload on subsequent restarts 
              //(e.g. viewing the device config object or initiating the upload action.
              if (!network().uploadOnStart().getBoolean())
                BacUtil.setOrAdd(BBacnetDevice.this,
                  SKIP_UPLOAD,
                  BBoolean.make(true),
                  Flags.HIDDEN,
                  null, /* Facets */
                  null /*Context*/);
            }
          }
        }
        catch (Exception e)
        {
          plog.log(Level.SEVERE, "Exception occurred in postUpload", e);
        }
      }
    });
  }

  private void postSoftPing()
  {
    network().postAsync(new Runnable()
    {
      @Override
      public void run()
      {
        // This is a copy of readSystemStatus but without any calls to pingFail.
        try
        {
          byte[] encoded = ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
            .readProperty(getAddress(), getObjectId(), BBacnetPropertyIdentifier.SYSTEM_STATUS);
          if (encoded == null)
          {
            if (log.isLoggable(Level.FINE))
            {
              log.fine(this + ": soft ping returned null");
            }
            return;
          }

          int systemStatus = AsnUtil.fromAsnEnumerated(encoded);
          pingOk();

          updateSystemStatus(systemStatus);
        }
        catch (Exception e)
        {
          if (log.isLoggable(Level.FINE))
          {
            log.log(Level.FINE, this + ": soft ping failed", e);
          }
        }
      }
    });
  }

  /**
   * Device stopped.
   */
  public void stopped()
    throws Exception
  {
    super.stopped();

    BBacnetNetwork.bacnet().unregisterDevice(this);

    try
    {
      BBacnetNetwork.localDevice().removeAddressBinding(this);
    }
    catch (NotRunningException e)
    {
    }

    DeviceRegistry.remove(getObjectId());
  }

  /**
   * Property Changed.
   * If necessary, update our entry in the Device Registry.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning() || (cx == fallback)) return;
    if (p.equals(status))
    {
      getVirtual().updateStatus();
    }
    else if (p.equals(useCov) || p.equals(useCovProperty))
    {
      this.tuningChanged(null, cx);
    }
    else if (p.equals(address))
    {
      BBacnetAddress newAddress = getAddress();
      BBacnetNetwork network = BBacnetNetwork.bacnet();

      // Changes with noWrite require no processing.
      if (cx != noWrite)
      {
        // If there's no change, skip update.
        if (oldAddress != null && newAddress != null)
        {
          BBacnetOctetString oldMacAddr = oldAddress.getMacAddress();
          if (oldMacAddr != null)
          {
            byte[] oldMacAddrBytes = oldMacAddr.getBytes();
            if (newAddress.equals(oldAddress.getNetworkNumber(), oldMacAddrBytes))
              return;
          }
        }

        BBacnetDevice d = network.doLookupDeviceByAddress(newAddress);
        if ((d != null) && (d != this))
        {
          // This is a duplicate address: reset to the old value and return.
          log.severe("Duplicate Address:" + newAddress + ", used by " + d.getName()
            + "!\n  Resetting to old address:" + oldAddress);
          set(address, oldAddress, fallback);
          return;
        }
        else
        {
          if (log.isLoggable(Level.FINE))
            log.fine("BacnetDevice " + getName() + " Address changed from " + oldAddress + " to " + newAddress);
          upload(new BUploadParameters(false));
        }
      }
      if (oldAddress == null) oldAddress = BBacnetAddress.DEFAULT;
      network.getLocalDevice().updateAddressBinding(oldAddress, newAddress);
      network.updateDevice(this);
      DeviceRegistry.update(this);
      oldAddress = (BBacnetAddress)newAddress.newCopy(true);
    }
    else if (p.getName().equals("vendorObjectTypesFile"))
    {
      network().postAsync(new Runnable()
      {
        public void run()
        {
          getVendorObjectTypesList();
        }
      });
    }
//    else if (p.getName().equals("debug"))
//      setDebug();
  }

  public void subscribed()
  {
    if (!isRunning()) return;
//    upload(new BUploadParameters(false));
    if (!isDown())
      getConfig().getDeviceObject().loadSlots();
  }

  /**
   * BBacnetDevice may only be placed under a BBacnetNetwork or BBacnetDeviceFolder.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetNetwork || parent instanceof BBacnetDeviceFolder;
  }

  /**
   * Get the Type of the parent network.
   */
  public Type getNetworkType()
  {
    return BBacnetNetwork.TYPE;
  }


  public void updateDeviceInfo(BBacnetObjectIdentifier objectId,
                               BBacnetAddress address,
                               int maxAPDULengthAccepted,
                               BBacnetSegmentation segmentationSupported,
                               int vendorId,
                               BNetworkPort port)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("Update Device info: " + this);
    }
    // Update Max APDU length accepted in device (used for polling)
    // and in device object (reported value).
    // FIXX: could use an I-Am context to indicate I'm setting maxAPDU from I-Am,
    // and not from a poll failure??
    maxAPDU = Math.max(MIN_APDU, maxAPDULengthAccepted);

    getConfig().getDeviceObject().set(BBacnetDeviceObject.maxAPDULengthAccepted,
      BBacnetUnsigned.make(maxAPDU), noWrite);
    setSegmentationSupported(segmentationSupported, noWrite);
    setVendorId(vendorId, noWrite);

    // 2007-08-02 CPG The pieces of the address were being set individually, but
    // this can cause problems when the same address instance is used to hash transactions,
    // but its values have changed.
    BBacnetAddress newAddress = (BBacnetAddress)address.newCopy();
    newAddress.setAddressType(getAddress().getAddressType());

    if ((port != null)
      && (port.getNetworkNumber() == address.getNetworkNumber())
      && (getAddress().getAddressType() == BBacnetAddress.MAC_TYPE_UNKNOWN))
    {
      BBacnetLinkLayer link = port.getLink();
      if (link instanceof BBacnetIpLinkLayer)
        newAddress.setAddressType(BBacnetAddress.MAC_TYPE_IP);
      else if (link instanceof BBacnetEthernetLinkLayer)
        newAddress.setAddressType(BBacnetAddress.MAC_TYPE_ETHERNET);
      else if (link instanceof BBacnetMstpLinkLayer)
        newAddress.setAddressType(BBacnetAddress.MAC_TYPE_MSTP);
      else if (link instanceof BScLinkLayer)
        newAddress.setAddressType(BBacnetAddress.MAC_TYPE_SC);
    }

    if (!getAddress().equals(newAddress.getNetworkNumber(),
      newAddress.getMacAddress().getBytes()))
    {
      set(BBacnetDevice.address, newAddress, noWrite);
    }

    BStatus status = getStatus();
    if (status.isDown())
    {
      if (!status.isDisabled())
      {
        readSystemStatus();
      }
    }
    else if (!status.isDisabled())
    {
      //Device is not disabled or down.
      pingOk();
    }
    //If the device was added by device id, with a null net/mac
    //and this is the first i-am message from the device, 
    //it will need to be uploaded to get a valid value for
    //can RPM, etc. 
    if (status.isStale())
    {
      synchronized (DEVICE_LOCK)
      {
        if (staleTicket != null)
          staleTicket.cancel();
      }
      clearStaleFlag();
      updateStatus();
      // pingOk above will not postUpload if status is stale even if it is the first pingOk so this
      // upload is not redundant.
      postUpload();
    }
    DeviceRegistry.update(this);
  }

////////////////////////////////////////////////////////////////
//  Actions
////////////////////////////////////////////////////////////////

  /**
   * Pinging a BBacnetDevice is implemented by reading the
   * System_Status property of its Device object.
   */
  public void doPing()
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("doPing on " + this);
    }

    // Short circuits
    BStatus status = getStatus();
    if ((status.getBits() & (BStatus.DISABLED | BStatus.FAULT)) != 0) return;

    if (isAddressValid())
    {
      //Avoid flooding the network worker with synchronous messages
      //for devices that cannot have timed out yet.
      //This should only come into effect if the ping interval is below the deviceTimeout
      long now = 0;
      if (!isDown() || ((now = Clock.ticks()) - lastPingTime) > network().getLocalDevice().getDeviceTimeout())
      {
        Runnable ping = new Runnable()
        {
          public void run()
          {
            long t0 = 0;
            if (BBacnetDevice.this.isRecordingLatency())
              t0 = Clock.ticks();

            readSystemStatus();
            updateStatus();

            if (isDown() && ++BBacnetDevice.this.failedPings % CHECK_ADDRESS_AFTER_PING_FAILS == 0)
            {
              checkAddress();

              if (t0 > 0)
                BBacnetDevice.this.recordLatency(Clock.ticks() - t0);
            }
          }
        };

        //If the network worker is using a worker pool
        //dispatch pings to the network worker
        BBacnetNetwork net = network();
        if (net.getAsyncPing() && net.getWorker().hasWorkerPool())
          net.postAsync(ping);
        else
          ping.run();

        lastPingTime = now;
      }
    }
    else
    {
      // If/When the 'i-am' comes in from the device it 
      // will have its system status read by the updateDeviceInfo method
      checkAddress();
    }
  }

  void readSystemStatus()
  {
    // postSoftPing copies most of this method- if readSystemStatus is changed, consider updating
    // postSoftPing, too.
    try
    {
      // Ping the device.
      byte[] encodedStatus = ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
        .readProperty(getAddress(), getObjectId(), BBacnetPropertyIdentifier.SYSTEM_STATUS);

      if (encodedStatus == null)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine(this + ": read System Status returned null");
        }
        pingFail(lex.getText("BacnetDevice.ping.null"));
        return;
      }

      // Decode and process the response.
      int systemStatus = AsnUtil.fromAsnEnumerated(encodedStatus);
      pingOk();

      updateSystemStatus(systemStatus);
    }
    catch (TransactionException e)
    {
      log.info("TransactionException pinging " + this + ": " + e);
      pingFail(e.toString());
    }
    catch (AsnException e)
    {
      log.log(Level.WARNING, "Unable to convert encoded System_Status!", e);
      pingFail(e.toString());
    }
    catch (BacnetException e)
    {
      log.log(Level.WARNING, "BacnetException pinging " + this + ": " + e, e);
      pingFail(e.toString());
    }
  }

  private void updateSystemStatus(int systemStatus)
  {
    // Add the enumerated response value to the list if unknown.
    if (!getEnumerationList().getDeviceStatusRange().isOrdinal(systemStatus))
      getEnumerationList().addNewDeviceStatus(systemStatus);

    BBacnetDeviceObject deviceObj = getConfig().getDeviceObject();
    if (systemStatus != deviceObj.getSystemStatus().getOrdinal())
    {
      deviceObj.set(BBacnetDeviceObject.systemStatus,
        BDynamicEnum.make(
          systemStatus,
          getEnumerationList().getDeviceStatusRange()),
        noWrite);
    }
  }

  public void pingOk()
  {
    super.pingOk();
    failedPings = 0;

    if (isFirstPingOk && uploadOnStart() && getStatus().isValid())
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(this + ": uploading on first pingOk");
      }
      postUpload();
    }
    isFirstPingOk = false;
  }


////////////////////////////////////////////////////////////////
//  Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetDevice", 2);
    out.prop("rpmOk", rpmOk);
    out.prop("oldAddress", oldAddress);
    out.prop("isPolling", isPolling);
    out.prop("pollTimeouts", pollTimeouts);
    out.prop("maxAPDU", maxAPDU);
    out.prop("isAws", isAws);
    out.endProps();
  }


////////////////////////////////////////////////////////////////
//  BILoadable support
////////////////////////////////////////////////////////////////

  /**
   * Implementation of upload.
   */
  public void doUpload(BUploadParameters p, Context cx)
    throws Exception
  {
    // Sanity check - bail if disabled/fault.
    BStatus status = getStatus();
    if ((status.getBits() & (BStatus.DISABLED | BStatus.FAULT)) != 0)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(this + " is either disabled or fault, device upload unsuccessful.");
      }
      return;
    }

    // Verify
    doPing();
    if (!getStatus().isValid()) return; // use newly acquired status

    uploadDevice(p, cx);
  }

  private void uploadDevice(BUploadParameters p, Context cx)
    throws Exception
  {
    // First check servicesSupported before starting the upload.
    getDeviceObject().readProperty(BBacnetDeviceObject.protocolServicesSupported);

    // Only the config is a BILoadable.
    getConfig().doUpload(p, cx);

    // Check for RPM support.
    if (isServiceSupported("readPropertyMultiple"))
    {
      rpmOk = true;
    }
    else
    {
      rpmOk = false;
    }

    // Set the maxAPDULengthAccepted to be used for this device.
    maxAPDU = getDeviceObject().getMaxAPDULengthAccepted().getInt();

    // Set the COV flag
    boolean subCov = isServiceSupported("subscribeCov");
    int flags = getFlags(useCov);
    if (!subCov) setUseCov(false);
    flags = (subCov ? (flags & (~(Flags.READONLY))) : (flags | Flags.READONLY));
    setFlags(useCov, flags);

    // Set the schedule support.
    ((BBacnetScheduleDeviceExt)getSchedules()).setSupport(getProtocolRevision());
    if (log.isLoggable(Level.FINEST))
    {
      log.finest(this + " device upload execution finish.");
    }
  }

  /**
   * Implementation of download.
   */
  public void doDownload(BDownloadParameters p, Context cx)
    throws Exception
  {
    // Only the config is a BILoadable.
    getConfig().doDownload(p, cx);
  }


////////////////////////////////////////////////////////////////
//  Poll support
////////////////////////////////////////////////////////////////

  /**
   * Is the device currently polling?
   */
  public boolean isPolling()
  {
    return isPolling;
  }

  /**
   * Set the polling flag.
   */
  public void setIsPolling(boolean isPolling)
  {
    this.isPolling = isPolling;
  }

  /**
   * Add a proxy point to the poll list for this device.
   *
   * @param pt the BBacnetProxyExt to be added.
   * @deprecated As of 3.2
   */
  @Deprecated
  public void addPolledPoint(BBacnetProxyExt pt)
  {
    log.warning("BBacnetDevice.addPolledPoint(Ljavax/baja/bacnet/point/BBacnetProxyExt;) is DEPRECATED!");
  }

  /**
   * Remove a proxy point from the poll list for this device.
   *
   * @param pt the BBacnetProxyExt to be removed.
   * @deprecated As of 3.2
   */
  @Deprecated
  public void removePolledPoint(BBacnetProxyExt pt)
  {
    log.warning("BBacnetDevice.removePolledPoint(Ljavax/baja/bacnet/point/BBacnetProxyExt;) is DEPRECATED!");
  }

  /**
   * Handle a tuning policy change.
   */
  public void tuningChanged(BBacnetTuningPolicy policy, Context cx)
  {
    BControlPoint[] points = getPoints().getPoints();
    for (int i = 0; i < points.length; i++)
      ((BBacnetProxyExt)points[i].getProxyExt()).tuningChanged(policy, cx);
  }

  /**
   * Poll a point in this device.
   *
   * @param pt the BBacnetProxyExt containing the point access data.
   * @deprecated As of 3.2
   */
  @Deprecated
  public boolean poll(BBacnetProxyExt pt)
  {
    log.warning("BBacnetDevice.poll(Ljavax/baja/bacnet/point/BBacnetProxyExt;) is DEPRECATED!");
    return true;
  }

  public boolean poll(PollList pl)
  {
    // Do not poll if this device is down or disabled.
    BStatus status = getStatus();
    if (!getEnabled() || status.isDown() || status.isFault() || status.isStale())
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("BBacnetDevice#poll: Poll list skipped because device is disabled or has an invalid status" +
          "; device enabled: " + getEnabled() + "; device status: " + status + "; poll list: " + pl.debug());
      }
      return true;
    }

    if (!rpmOk)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("BBacnetDevice#poll: Using readProperty instead of readPropertyMultiple because rpmOk is false; poll list: " + pl.debug());
      }
      pollRP(pl);
      return true;
    }

    // Build the list of ReadAccessSpecifications.
    PollListEntry[] entries = pl.getPollEntries();
    int len = entries.length;
    if (len == 0)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("BBacnetDevice#poll: Poll list has no entries; poll list: " + pl.debug());
      }
      return false;
    }

    Array<NReadAccessSpec> specs = new Array<>(NReadAccessSpec.class);
    int ndx = 0;

    // Creat spec for first entry.
    PollListEntry e = entries[ndx++];
    NReadAccessSpec spec = new NReadAccessSpec(e.getObjectId(), e.getPropertyId(), e.getPropertyArrayIndex());
    int curHC = e.getObjectId().hashCode();

    while (ndx < len)
    {
      e = entries[ndx++];

      // sanity check
      if (e == null)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("BBacnetDevice#poll: Building ReadAccessSpec: Poll list entry is null at index " + (ndx - 1) + "; poll list: " + pl.debug());
        }
        continue;
      }
      // Do not poll an object if the object has an invalid object ID.
      if (e.getObjectId().getInstanceNumber() >= 0)
      {
        if (curHC == e.getObjectId().hashCode())
        {
          // ObjectID matches; add to current spec
          spec.addPropertyReference(e.getPropertyId(), e.getPropertyArrayIndex());
        }
        else
        {
          // ObjectID does not match; add current spec to list & make a new spec
          specs.add(spec);

          spec = new NReadAccessSpec(e.getObjectId(), e.getPropertyId(), e.getPropertyArrayIndex());
          curHC = e.getObjectId().hashCode();
        }
      }
      else
      {
        log.warning("BBacnetDevice#poll: Building ReadAccessSpec: Poll list entry at index " + (ndx - 1) +
          " has an invalid object instance number: " + e.getObjectId().getInstanceNumber() + "; poll list: " + pl.debug());
        BIBacnetPollable p = e.getPollable();
        if (p instanceof BBacnetObject)
        {
          BBacnetObject obj = (BBacnetObject) p;
          obj.setStatus(BStatus.fault);
          obj.setFaultCause(TextUtil.toFriendly(BBacnetErrorCode.tag(BBacnetErrorCode.UNKNOWN_OBJECT)));
        }
      }
    }

    // Add current spec since it hasn't been added yet.
    if (spec.getObjectId().getInstanceNumber() >= 0)
    {
      specs.add(spec);
    }
    else
    {
      log.warning("BBacnetDevice#poll: Building ReadAccessSpec: Poll list entry at index " + (ndx - 1) +
        " has an invalid object instance number: " + e.getObjectId().getInstanceNumber() + "; poll list: " + pl.debug());
    }

    if (specs.isEmpty())
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("BBacnetDevice#poll: Poll list entries did not result in any read access specs; poll list: " + pl.debug());
      }
      return true;
    }

    // Now send the request.
    @SuppressWarnings("rawtypes") Iterator resultList = null;
    try
    {
      long t0 = 0;
      if (isRecordingLatency())
      {
        t0 = Clock.ticks();
      }

      resultList = client().readPropertyMultiple(getAddress(), specs);

      if (t0 > 0)
      {
        recordLatency(Clock.ticks() - t0);
      }
    }
    catch (TransactionException x)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.INFO, "TransactionException in poll() for " + this + "; poll list = " + pl, x);
      }
      else
      {
        log.log(Level.INFO, "TransactionException in poll() for " + this + "; exception = " + x + "; poll list = " + pl);
      }

      ping();
      if (++pollTimeouts > getMaxPollTimeouts())
      {
        for (int i = 0; i < len; i++)
        {
          e = entries[i];
          if (e != null) // sanity check for device address change scenario
            e.getPollable().readFail(x.toString());
        }
      }
      return false;
    }
    catch (ErrorException x)
    {
      pollTimeouts = 0;
      for (int i = 0; i < len; i++)
      {
        e = entries[i];
        if (e != null) // sanity check for device address change scenario
        {
          // FIXX: setLastReadError() should probably become part of the
          // BIBacnetPollable API.
          BIBacnetPollable p = e.getPollable();
          if (p instanceof BBacnetProxyExt)
            ((BBacnetProxyExt)p).setLastReadError(x.getErrorType());
          plog.log(Level.SEVERE, "Bacnet Error polling " + pl + "; entry " + e
            + " in " + getName() + " {" + getObjectId() + "}: " + x, x);
          e.getPollable().readFail(/*e.toString()+"::"+*/x.toString());
        }
      }
      return true;
    }
    catch (AbortException x)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.INFO, "AbortException in poll() for " + this + "; poll list = " + pl, x);
      }
      else
      {
        log.log(Level.INFO, "AbortException in poll() for " + this + "; exception = " + x + "; poll list = " + pl);
      }

      pollTimeouts = 0;
      if ((x.getAbortReason() == BBacnetAbortReason.SEGMENTATION_NOT_SUPPORTED)
        || (x.getAbortReason() == BBacnetAbortReason.OTHER))
      {
        //maxAPDU -= 5;
        //if (maxAPDU <= 50) rpmOk = false;
        //plog.message("ABORT:Segmentation not supported - decreasing max APDU size to "+maxAPDU);
        return false;
      }
    }
    catch (RejectException x)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.INFO, "RejectException in poll() for " + this + "; poll list = " + pl, x);
      }
      else
      {
        log.log(Level.INFO, "RejectException in poll() for " + this + "; exception = " + x + "; poll list = " + pl);
      }

      pollTimeouts = 0;
      if (x.getRejectReason() == BBacnetRejectReason.UNRECOGNIZED_SERVICE)
      {
        rpmOk = false;
        plog.info("REJECT:Unrecognized service - switch polling to ReadProperty");
        pollRP(pl);
        return true;
      }
      if (x.getRejectReason() == BBacnetRejectReason.BUFFER_OVERFLOW)
      {
        maxAPDU -= 5;
        if (maxAPDU <= MIN_APDU) rpmOk = false;
        plog.info("REJECT:Buffer overflow - decreasing max APDU size to " + maxAPDU);
        return false;
      }
    }
    catch (BacnetException x)
    {
      pollTimeouts = 0;
      plog.log(Level.SEVERE, "BacnetException polling " + pl + " in " + getName() + " {" + getObjectId() + "}: " + x, x);
      return false;
    }

    // Sanity check - make sure we have results.
    if (resultList == null)
    {
      if (plog.isLoggable(Level.INFO))
      {
        plog.info("BBacnetDevice#poll: Poll resultList is empty!; poll list = " + pl);
      }
      return false;
    }

    // Set the ping success indicator.
    pingOk();
    pollTimeouts = 0;

    // Iterate through each result in the ack.
    ndx = 0;
    while (resultList.hasNext() && (ndx < entries.length))
    {
      NReadPropertyResult rpr = (NReadPropertyResult)resultList.next();
      e = entries[ndx++];

      // sanity check 1
      if (e == null)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("Iterating ReadAccessResults: PLE is null in  slot " + (ndx - 1));
        }
        continue;
      }

      // sanity check 2
      if (!e.getObjectId().equals(rpr.getObjectId()) ||
        e.getPropertyId() != rpr.getPropertyId() ||
        e.getPropertyArrayIndex() != rpr.getPropertyArrayIndex())
      {
        plog.info("Mismatch between PollListEntry and ReadPropertyResult:\n  ple=" + e.debugString() + "\n  rpr=" + rpr.debug());
        return false;   // force a redistribution
      }

      if (rpr.isError())
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("BBacnetDevice#poll: Read property error; poll list entry = " + e.debugString() +
            "; read property result = " + rpr.debug() + "; poll list = " + pl.debug());
        }

        // FIXX: setLastReadError() should probably become part of the
        // BIBacnetPollable API.
        BIBacnetPollable p = e.getPollable();
        if (p instanceof BBacnetProxyExt)
          ((BBacnetProxyExt)p).setLastReadError(rpr.getPropertyAccessError());
        e.getPollable().readFail(NErrorType.toString(rpr.getErrorClass(), rpr.getErrorCode()));
      }
      else
      {
        e.getPollable().fromEncodedValue(rpr.getPropertyValue(), null, (Context)e/*.getContext()*/);
      }
    }

    // No complaints.
    return true;
  }

  private void pollRP(PollList pl)
  {
    PollListEntry[] entries = pl.getPollEntries();
    int len = entries.length;
    for (int i = 0; i < len; i++)
    {
      PollListEntry e = entries[i];
      if (e == null)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("pollRP: PLE is null in  slot " + i);
        }
        continue;
      }
      byte[] encodedValue = null;
      try
      {
        long t0 = 0;
        if (isRecordingLatency())
          t0 = Clock.ticks();

        encodedValue = client().readProperty(getAddress(),
          e.getObjectId(),
          e.getPropertyId(),
          e.getPropertyArrayIndex());
        if (t0 > 0)
          recordLatency(Clock.ticks() - t0);

        pingOk();
        e.getPollable().fromEncodedValue(encodedValue, null, (Context)e/*.getContext()*/);
      }
      catch (TransactionException x)
      {
        e.getPollable().readFail(x.toString());
        ping();
        if (getStatus().isDown()) break;
      }
      catch (ErrorException x)
      {
        // FIXX: setLastReadError() should probably become part of the
        // BIBacnetPollable API.
        BIBacnetPollable p = e.getPollable();
        if (p instanceof BBacnetProxyExt)
          ((BBacnetProxyExt)p).setLastReadError(x.getErrorType());
        plog.log(Level.SEVERE, "Bacnet Error polling PollList " + pl + " entry " + e
          + " in " + getName() + " {" + getObjectId() + "}: " + x, x);
        e.getPollable().readFail(/*e.toString()+"::"+*/x.toString());
      }
      catch (BacnetException x)
      {
        plog.log(Level.SEVERE, "BacnetException polling PollList " + pl + " entry " + e
          + " in " + getName() + " {" + getObjectId() + "}: " + x, x);
        e.getPollable().readFail(/*e.toString()+"::"+*/x.toString());
      }
    }
  }

  /**
   * @deprecated As of 3.2
   */
  @Deprecated
  public int countPolledPoints()
  {
    log.warning("BBacnetDevice.countPolledPoints()I is DEPRECATED!");
    return 0;
  }


////////////////////////////////////////////////////////////////
// COV Support
////////////////////////////////////////////////////////////////

  /**
   * Can a Cov subscription be initiated for this device?
   * Checks the useCov flag, along with current subscription count.
   *
   * @return true if a new subscription can be attempted.
   */
  public boolean canAddCov()
  {
    return getUseCov() && (getCovSubscriptions() < getMaxCovSubscriptions());
  }


  ////////////////////////////////////////////////////////////////
// COV Property Support
////////////////////////////////////////////////////////////////

  /**
   * Can a Cov Property subscription be initiated for this device?
   * Checks the useCovProperty flag, along with current subscription count.
   *
   * @return true if a new Cov Property subscription can be attempted.
   */
  public boolean canAddCovProperty()
  {
    return getUseCovProperty() && (getCovSubscriptions() < getMaxCovSubscriptions());
  }

  /**
   * Subscribe a point for Cov notifications.
   *
   * @param pt the proxy point to be subscribed - must be a Present_Value
   *           of an AI/AO/AV, BI/BO/BV, MSI/MSO/MSV, Loop, or LifeSafety
   *           object.
   * @return boolean indicating if the subscription was successful.
   */
  public boolean subscribeCov(BBacnetProxyExt pt)
  {
    if (!device().isOperational())
    {
      // Short here as when device ping fails, callback flows to BacnetProxyExt
      // readUnsubscribed(Context cx), should take care of reducing covSubscriptions number
      // & finally set to unsubscribe state.
      return false;
    }

    boolean covOK = true;
    if (!canAddCov())
    {
      covOK = false;
    }
    else
    {
      int subLife = calculateSubcriptionLifetime(pt.getCovSubscriptionLifetime());

      try
      {
        client().subscribeCov(getAddress(),
          1,
          pt.getObjectId(),
          pt.useConfirmedCov(),
          subLife);
        pingOk();
      }
      catch (BacnetException e)
      {
        if (getDisableDeviceOnCovSubscriptionFailure() && e instanceof TransactionTimeoutException)
        {
          device().pingFail(lex.get("bacnetDevice.subscribeCov.failure"));
          log.severe(this + ": Timeout detected, marking the device down due to no response.");
        }

        plog.log(Level.SEVERE, this + ": BacnetException sending SubscribeCov for " + pt.getObjectId()
          + " in " + getObjectId() + ": " + e, e);
        covOK = false;
      }
    }

    // Set the counter for our COV subscriptions.
    BBacnetPoll pollService = (BBacnetPoll)network().getPollService(pt);
    if (covOK)
    {
      pollService.unsubscribe(pt);
      if (!pt.isCOV())
        setCovSubscriptions(getCovSubscriptions() + 1);
    }
    else
    {
      if (!pt.isPolled())
        pollService.subscribe(pt);
      if (pt.isCOV())
        setCovSubscriptions(getCovSubscriptions() - 1);
    }

    // Set the COV flag on the point.
    pt.setSubState(covOK ? BBacnetProxyExt.SUB_STATE_COV : BBacnetProxyExt.SUB_STATE_POLLED_PENDING);

    return covOK;
  }

  /**
   * Subscribe COV Property for Cov notifications.
   *
   * @param pt the proxy point to be subscribed - Any Property - must be a
   *           of an AI/AO/AV, BI/BO/BV, MSI/MSO/MSV, Loop, or LifeSafety
   *           object.
   * @return boolean indicating if the subscription was successful.
   */
  public boolean subscribeCovProperty(BBacnetProxyExt pt)
  {
    if (!device().isOperational())
    {
      // Short here as when device ping fails, callback flows to BacnetProxyExt
      // readUnsubscribed(Context cx), should take care of reducing covSubscriptions number
      // & finally set to unsubscribe state.
      return false;
    }

    boolean covPropertyOK = true;
    if (!canAddCovProperty())
      covPropertyOK = false;
    else
    {
      int subLife = calculateSubcriptionLifetime( pt.getCovPropertySubscriptionLifetime());
      try
      {
        client().subscribeCovProperty(getAddress(),
          1,
          pt.getObjectId(),
          pt.useConfirmedCovProperty(),
          subLife, new BBacnetPropertyReference(pt.getPropertyId().getOrdinal(), pt.getPropertyArrayIndex()), pt.getCovPropertyIncrement());
        pingOk();
      }
      catch (BacnetException e)
      {
        if (getDisableDeviceOnCovSubscriptionFailure() && e instanceof TransactionTimeoutException)
        {
          device().pingFail(lex.get("bacnetDevice.subscribeCovProperty.failure"));
          log.severe(this + ": Timeout detected, marking the device down due to no response.");
        }

        plog.log(Level.SEVERE, this + ": BacnetException sending SubscribeCovProperty for " + pt.getObjectId()
          + " in " + getObjectId() + ": " + e, e);
        covPropertyOK = false;
      }
    }

    // Set the counter for our COV subscriptions.
    BBacnetPoll pollService = (BBacnetPoll)network().getPollService(pt);
    if (covPropertyOK)
    {
      pollService.unsubscribe(pt);
      if (!pt.isCOVProperty())
      {
        setCovSubscriptions(getCovSubscriptions() + 1);
      }
      // Set the COVP flag on the point.
      pt.setSubState(BBacnetProxyExt.SUB_STATE_COVP);
    }
    else
    {
      if (!pt.isPolled())
      {
        pollService.subscribe(pt);
      }
      if (pt.isCOVProperty())
      {
        setCovSubscriptions(getCovSubscriptions() - 1);
      }

      pt.setSubState(BBacnetProxyExt.SUB_STATE_POLLED_PENDING);
    }
    return covPropertyOK;
  }


  /**
   * Unsubscribe a point for Cov notifications.
   *
   * @param pt the proxy point to be unsubscribed.
   */
  public void unsubscribeCov(BBacnetProxyExt pt)
  {
    if (!device().isOperational())
    {
      setCovSubscriptions(getCovSubscriptions() - 1);
      return;
    }

    BBacnetObjectIdentifier objectId = pt.getObjectId();
    try
    {
      client().unsubscribeCov(getAddress(),
          1,
          objectId);

      setCovSubscriptions(getCovSubscriptions() - 1);
    }
    catch (BacnetException e)
    {
      if (getDisableDeviceOnCovSubscriptionFailure() && e instanceof TransactionTimeoutException)
      {
        device().pingFail(lex.get("bacnetDevice.unsubscribeCov.failure"));
        log.severe(this + ": Timeout detected, marking the device down due to no response.");
      }

      plog.log(Level.SEVERE, this + ": BacnetException cancelling Cov subscription for " + objectId
        + " in " + getObjectId() + ": " + e, e);
    }
  }

  /**
   * Unsubscribe a Cov Property point for Cov notifications.
   *
   * @param pt the proxy Cov Property point to be unsubscribed.
   */
  public void unsubscribeCovProperty(BBacnetProxyExt pt)
  {
    if (!device().isOperational())
    {
      setCovSubscriptions(getCovSubscriptions() - 1);
      return;
    }

    BBacnetObjectIdentifier objectId = pt.getObjectId();
    BBacnetPropertyReference propertyReference = new BBacnetPropertyReference(pt.getPropertyId().getOrdinal(), pt.getPropertyArrayIndex());
    try
    {
      client().unsubscribeCovProperty(getAddress(),
          1,
          objectId, propertyReference);

      setCovSubscriptions(getCovSubscriptions() - 1);
    }
    catch (BacnetException e)
    {
      if (getDisableDeviceOnCovSubscriptionFailure() && e instanceof TransactionTimeoutException)
      {
        device().pingFail(lex.get("bacnetDevice.unsubscribeCovProperty.failure"));
        log.severe(this + ": Timeout detected, marking the device down due to no response.");
      }

      plog.log(Level.SEVERE, this + ": BacnetException cancelling Cov Property subscription for " + objectId
        + " in " + getObjectId() + " Property Reference  " +propertyReference +  ": " + e, e);
    }
  }


////////////////////////////////////////////////////////////////
// XML-based Object/Property type support
////////////////////////////////////////////////////////////////

  /**
   * Get a PropertyInfo object containing metadata about this property.
   * Vendor-defined information is checked first, allowing vendors to
   * override the standard property info if needed.  Then the standard
   * object type list is checked, and if still nothing is found, a generic
   * "unknown proprietary" info is created and returned.
   *
   * @param objectType the Bacnet object type of the containing object.
   * @param propId     the property ID.
   * @return a PropertyInfo read from the manufacturer-specific XML file.
   */
  public final PropertyInfo getPropertyInfo(int objectType, int propId)
  {
    // First try to load any vendor-specific property info.  This allows
    // vendor-defined information to override the standard defaults.
    PropertyInfo propInfo = null;
    try
    {
      propInfo = getVendorPropertyInfo(objectType, propId);
    }
    catch (Exception e)
    {
    }

//    // If no vendor-supplied property info, then try to load the info from
//    // the network's object type list.
//    try
//    {
//      if (propInfo == null && isMounted())
//      {
//        BComplex c = this;
//        while (c != null)
//        {
//          if (c instanceof BBacnetNetwork)
//          {
//            propInfo = ((BBacnetNetwork)c).getPropertyInfo(objectType, propId);
//            break;
//          }
//          c = c.getParent();
//        }
//      }
//    }
//    catch (Exception e) {}

    // If unable to get the network's list, just use the standard object type list.
    try
    {
      if (propInfo == null)
        propInfo = ObjectTypeList.getInstance().getPropertyInfo(objectType, propId);
    }
    catch (Exception e)
    {
    }

    // Filter out aws types based on network
    if (propInfo != null && propInfo.isAws() && !isAws()) propInfo = null;

    // If there is still no info,
    // just create an "unknown proprietary" PropertyInfo.
    if (propInfo == null)
      propInfo = new PropertyInfo(BBacnetPropertyIdentifier.tag(propId), propId, AsnConst.ASN_UNKNOWN_PROPRIETARY);

    // Return what we have.
    return propInfo;
  }
  
  public int[] getPossibleProperties(BBacnetObjectIdentifier objectId)
  {
    int revisionId = -1;

    try
    {
      getDeviceObject().readProperty(BBacnetDeviceObject.protocolRevision);
      revisionId = getDeviceObject().getProtocolRevision().getInt();
    }
    catch (Exception e)
    {
      log.log(Level.INFO,"Could not read the protocol revision from the device object.",e);
    }
    int[] possibleProperties = ObjectTypeList.getInstance().getPossibleProperties(objectId, revisionId);
    return removePropertyFromArray(possibleProperties, BBacnetPropertyIdentifier.PROTOCOL_REVISION);
  }
  private int[] removePropertyFromArray(int[] possibleProperties, int propToRemove)
  {
    for(int index =0 ; index < possibleProperties.length; index++)
    {
     if(possibleProperties[index] == propToRemove)
     {
       int[] newPossibleProperties = new int[possibleProperties.length - 1];
       System.arraycopy(possibleProperties, 0, newPossibleProperties, 0, index);
       System.arraycopy(possibleProperties, index+1, newPossibleProperties, index, possibleProperties.length-index- 1);
       return newPossibleProperties;
     }
    }
    return possibleProperties;
  }

  public int[] getRequiredProperties(BBacnetObjectIdentifier objectId)
  {
    return ObjectTypeList.getInstance().getRequiredProperties(objectId);
//    return network().getRequiredProperties(objectId);
  }

  protected PropertyInfo getVendorPropertyInfo(int objectType, int propertyId)
  {
    if (vendorObjectTypesList != null)
      return vendorObjectTypesList.getPropertyInfo(objectType, propertyId);
    return null;
  }

  private ObjectTypeList getVendorObjectTypesList()
  {
    BOrd o = BOrd.NULL;
    if (vendorObjectTypesList == null)
    {
      try
      {
        o = (BOrd)get("vendorObjectTypesFile");
        if ((o != null) && !o.equals(BOrd.NULL))
        {
          vendorObjectTypesList = ObjectTypeList.make(o);
        }
      }
      catch (ClassCastException e)
      {
        log.log(Level.INFO, "vendorObjectTypesFile must be a BOrd." + e, e);
      }
      catch (Exception e)
      {
        log.log(Level.INFO, "Unable to build vendor object types list from file:" + o, e);
      }
    }
    return vendorObjectTypesList;
  }

  public BEnumRange getEnumRange(int objectType, int propertyId)
  {
    PropertyInfo pi = getPropertyInfo(objectType, propertyId);
    if (pi.isEnum())
    {
      if (pi.isExtensible())
      {
        return getEnumerationList().getEnumRange(pi.getType());
      }
      else
      {
        BTypeSpec tspec = BTypeSpec.make(pi.getType());
        return ((BEnum)tspec.getInstance()).getRange();
      }
    }
    return null;
  }


  /**
   * The isOperational method is used to control optional communication
   * to devices, that should be skipped if the device has determined
   * to be non-operational
   * <p>
   * The default implementation is a simple status check that
   * the device's status is not either down, disabled or in fault.
   *
   * @return true, if normal communication with a device should be enabled
   * false, if there is a problem communicating with the device
   * and optional communication should stop until the
   * device is operational
   */
  public boolean isOperational()
  {
    BStatus status = getStatus();
    if (status == null)
      status = BStatus.down;

    return !(status.isDown() || status.isDisabled() || status.isFault());
  }

////////////////////////////////////////////////////////////////
// BIBacnetObjectContainer
////////////////////////////////////////////////////////////////

  public BObject lookupBacnetObject(BBacnetObjectIdentifier objectId,
                                    int propertyId,
                                    int propertyArrayIndex,
                                    String domain)
  {
    // If client side may need to load stuff
    if (!isRunning()) loadSlots();

    if ((domain == null) || domain.equals(BIBacnetObjectContainer.POINT))
    {
      return getPoints().lookupBacnetObject(objectId, propertyId, propertyArrayIndex, domain);
    }

    // Schedules and Calendars are resolved from the ScheduleDeviceExt.
    if (domain.equals(BIBacnetObjectContainer.SCHEDULE))
    {
      return ((BBacnetScheduleDeviceExt)getSchedules()).lookupBacnetObject(objectId, propertyId, propertyArrayIndex, domain);
    }

    // Trend Logs are resolved from the HistoryDeviceExt.
    if (domain.equals(BIBacnetObjectContainer.HISTORY))
    {
      return null; // FIXX
    }

    // Config objects are resolved from the ConfigDeviceExt.
    if (domain.equals(BIBacnetObjectContainer.CONFIG))
    {
      return getConfig().lookupBacnetObject(objectId, propertyId, propertyArrayIndex, domain);
    }
    // FIXX: Files?

    // Domain type not understood.
    return null;
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doMacAddressFailed()
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("Cannot resolve mac address for: " + this);
    }
    synchronized (DEVICE_LOCK)
    {
      staleTicket = null;
    }
    //remove stale status flag:
    clearStaleFlag();
    pingFail("Cannot resolve MAC address");
    updateStatus();
  }

////////////////////////////////////////////////////////////////
// DeviceOverrideAware
////////////////////////////////////////////////////////////////

  @Override
  public boolean addDeviceOverride(DeviceOverride override)
  {
    // Adding overrides should be a limited edge case, 
    // on start. The performance hit on startup from
    // acquiring the DEVICE_LOCK should be minimal. 

    synchronized (DEVICE_LOCK)
    {
      if (overrides == null)
        overrides = Collections.synchronizedList(new ArrayList<>());
    }

    return overrides.add(override);
  }

  @Override
  public boolean removeDeviceOverride(DeviceOverride override)
  {
    if (overrides == null) return false;
    return overrides.remove(override);
  }

  /**
   * Carries out the necessary checks and calculations required to calculate the subscription
   * lifetime. Expects the sublife parameter to be in minutes.
   *
   * @param subLife desired subscription lifetime in minutes
   * @return minimum of desired subscription lifetime or MINIMUM_COV_SUBSCRIPTION_LIFETIME in seconds
   */
  private int calculateSubcriptionLifetime(int subLife)
  {
    subLife *= 60; //Converting from minutes to seconds.

    // Setting subscription lifetime to 0 stands for an un-subcription request.
    if ((subLife < 0) || (subLife > 0 && subLife < MINIMUM_COV_SUBSCRIPTION_LIFETIME))
    {
      subLife = MINIMUM_COV_SUBSCRIPTION_LIFETIME;
    }
    return subLife;
  }

  /**
   * Look through the collection of DeviceOverrides
   * for APDU size overrides, return the override
   * with the smallest APDU size.
   *
   * @return the ApduOverride with the smallest value
   * or null if no ApduOverride is present
   */

  private int getOverridenAdpuSize(int claimed)
  {
    int smallest = claimed;
    if (overrides != null)
    {
      for (DeviceOverride override : overrides)
      {
        if (override instanceof ApduSizeOverride)
        {
          ApduSizeOverride current = (ApduSizeOverride)override;
          smallest = Math.min(smallest,
            current.getMaxAPDULengthAccepted(getDeviceObject()));
        }
      }
    }
    return smallest;
  }

  /**
   * Look through the collection of DeviceOverrides
   * for Segmentation Overrides, return the first Segmentation
   * override found
   *
   * @return the first SegmentationOverride encountered.
   * or null if no SegmentationOverride is present
   */

  private SegmentationOverride getSegmentationOverride()
  {
    if (overrides != null)
    {
      for (DeviceOverride override : overrides)
      {
        if (override instanceof SegmentationOverride)
        {
          return (SegmentationOverride)override;
        }
      }
    }
    return null;
  }

  /**
   * Look through the collection of DeviceOverrides
   * for ServiceSupportOverrides.
   * <p>
   * The precedence of ServiceSupportOverrides is
   * override dependent. The override can either
   * merge with the status that is passed in (e.g. RPMOverride)
   * or ignore the current merged field and completely
   * drive the field. The most recently registered
   * override will win in the later case.
   * <p>
   * Overrides should be used with great care and only
   * as a last resort to work around a field issue.
   *
   * @return the first SegmentationOverride encountered.
   * or null if no SegmentationOverride is present
   */
  private BBacnetBitString getServicesSupported(BBacnetBitString claimed)
  {
    BBacnetBitString merged = claimed;
    if (overrides != null)
    {
      for (DeviceOverride override : overrides)
      {
        if (override instanceof ServiceOverride)
        {
          ServiceOverride sso = (ServiceOverride)override;
          merged = sso.getProtocolServicesSupported(getDeviceObject(), merged);
        }
      }
    }
    return merged;
  }


  private void clearStaleFlag()
  {
    BStatus status = getStatus();
    setStatus(BStatus.make(status.getBits() & ~BStatus.STALE,
      status.getFacets()));
  }

  ////////////////////////////////////////////////////////////////
// LatencyRecorderAware
////////////////////////////////////////////////////////////////
  //TODO update to a set.
  @Override
  public boolean addLatencyRecorder(LatencyRecorder recorder)
  {
    if (recorder != null)
    {
      synchronized (DEVICE_LOCK)
      {
        if (recorders == null)
          recorders = Collections.synchronizedList(new ArrayList<>());
      }
      return recorders.add(recorder);
    }

    return false;
  }

  @Override
  public boolean removeLatencyRecorder(LatencyRecorder recorder)
  {
    if (recorder != null && this.recorders != null)
    {
      return this.recorders.remove(recorder);
    }
    return false;
  }

  @Override
  public void recordLatency(long ms)
  {
    if (recorders != null)
    {
      for (LatencyRecorder r : recorders)
        r.recordLatency(ms);
    }
  }

  @Override
  public boolean isRecordingLatency()
  {
    if (recorders != null)
    {
      for (LatencyRecorder r : recorders)
        if (r.isRecordingLatency())
          return true;
    }

    return false;
  }
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  protected static final int MINIMUM_COV_SUBSCRIPTION_LIFETIME = 300;

  private static final Lexicon lex = Lexicon.make("bacnet");


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Flag indicating RPM support.
   */
  private boolean rpmOk = false;

  /**
   * Max APDU length to be used in polling - initialized from device object value.
   */
  private int maxAPDU = MIN_APDU; //50 is the smallest allowable APDU size

  /**
   * Holder for old address; use as temporary trap behavior until Invariant API is developed.
   */
  private BBacnetAddress oldAddress;

  /**
   * Is the device currently polling its points?
   */
  private boolean isPolling;

  /**
   * Number of consecutive poll timeouts
   */
  private int pollTimeouts = 0;

  /**
   * List of vendor-specific object types and properties.
   */
  private ObjectTypeList vendorObjectTypesList = null;

  /**
   * Is this device in a BacnetAwsNetwork?
   */
  private BBoolean isAws = null;

  private Object DEVICE_LOCK = new Object();
  private Ticket staleTicket = null;

  private long lastPingTime = 0;
  private volatile int failedPings = 0;
  private volatile boolean isFirstPingOk = true;
  private static final int CHECK_ADDRESS_AFTER_PING_FAILS =
    Integer.getInteger("niagara.bacnet.checkAddressAfterFailedPings", 100).intValue();

  // Logs
  public static final Logger log = Logger.getLogger("bacnet.client");
  public static final Logger plog = Logger.getLogger("bacnet.point");

  private static final int MIN_APDU = 50;
  private static final String SKIP_UPLOAD = "skipUpload";
  public static final String IGNORE_SYSTEM_STATUS = "ignoreSystemStatus";

  //The order of registration drives precendence and must be preserved.
  private List<DeviceOverride> overrides = null;

  private List<LatencyRecorder> recorders = null;
}
