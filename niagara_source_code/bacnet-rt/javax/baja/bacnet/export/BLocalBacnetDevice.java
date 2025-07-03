/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BIBacnetObjectContainer;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetAddressBinding;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.datatypes.BBacnetDateTime;
import javax.baja.bacnet.datatypes.BBacnetListOf;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetRecipient;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.datatypes.BIBacnetDataType;
import javax.baja.bacnet.enums.BBacnetBackupState;
import javax.baja.bacnet.enums.BBacnetDeviceStatus;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetRestartReason;
import javax.baja.bacnet.enums.BBacnetSegmentation;
import javax.baja.bacnet.enums.BCharacterSetEncoding;
import javax.baja.bacnet.enums.BExtensibleEnumList;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.bacnet.virtual.BLocalBacnetVirtualGateway;
import javax.baja.file.BFileSystem;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.LongHashMap;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.units.BUnit;
import javax.baja.user.BUser;
import javax.baja.user.BUserService;
import javax.baja.util.BUuid;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.BacnetVendorUtil;
import com.tridium.bacnet.ObjectTypeList;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.services.BacnetConfirmedRequest;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.NChangeListError;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.DeviceRegistry;
import com.tridium.bacnet.stack.client.BBacnetClientLayer;
import com.tridium.bacnet.stack.link.mstp.BBacnetMstpLinkLayer;
import com.tridium.bacnet.stack.link.sc.BScLinkLayer;
import com.tridium.bacnet.stack.network.BBacnetNetworkLayer;
import com.tridium.bacnet.stack.network.BNetworkPort;
import com.tridium.bacnet.stack.server.BBacnetExportTable;
import com.tridium.bacnet.stack.server.LocalBacnetCovPropPoll;
import com.tridium.bacnet.stack.transport.BBacnetTransportLayer;
import com.tridium.bacnet.stack.transport.ConfirmedRequestPdu;
import com.tridium.sys.Nre;
import com.tridium.sys.station.Station;
import com.tridium.util.ComponentTreeCursor;

/**
 * BLocalBacnetDevice is the representation of Niagara as a Bacnet
 * device on the Bacnet internetwork.
 * <p>
 * Objects in Niagara are exposed to Bacnet through the
 * methods defined in the BIBacnetExportObject API.
 *
 * @author Craig Gemmill on 06 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 the status for Niagara server-side behavior.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Provides a description of a fault with server-side behavior.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 objectId is the Bacnet Object Identifier used by Niagara in
 Bacnet communications.  Niagara acts as a Bacnet Device Object.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE)",
  facets = @Facet("BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.DEVICE)")
)
@NiagaraProperty(
  name = "systemStatus",
  type = "BBacnetDeviceStatus",
  defaultValue = "BBacnetDeviceStatus.operational",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "vendorName",
  type = "String",
  defaultValue = "Tridium",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "vendorId",
  type = "int",
  defaultValue = "36",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "modelName",
  type = "String",
  defaultValue = "Niagara4 Station",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "firmwareRevision",
  type = "String",
  defaultValue = "unknown",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "applicationSoftwareVersion",
  type = "String",
  defaultValue = "unknown",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "location",
  type = "String",
  defaultValue = "unknown"
)
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = "Local BACnet Device object"
)
/*
 Identifies the device regardless of its current VMAC address or device instance number. It shall
 be generated before first deployment of the device in an installation, shall be persistently
 stored across device restarts, and shall not change over the entire lifetime of a device. If a
 device is replaced in an installation, the new device is not required to re-use the UUID of the
 replaced device.
 @since Niagara 4.11
 */
@NiagaraProperty(
  name = "deviceUuid",
  type = "BUuid",
  defaultValue = "BUuid.DEFAULT",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  facets = @Facet(name = "BFacets.SECURITY", value = "true")
)
@NiagaraProperty(
  name = "protocolVersion",
  type = "int",
  defaultValue = "1",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "protocolRevision",
  type = "int",
  defaultValue = "14",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "protocolConformanceClass",
  type = "int",
  defaultValue = "3",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "protocolServicesSupported",
  type = "BBacnetBitString",
  defaultValue = "SERVER_SERVICES_SUPPORTED",
  flags = Flags.READONLY,
  facets = @Facet("BacnetBitStringUtil.BACNET_SERVICES_SUPPORTED_FACETS")
)
@NiagaraProperty(
  name = "protocolObjectTypesSupported",
  type = "BBacnetBitString",
  defaultValue = "SERVER_OBJECT_TYPES_SUPPORTED",
  flags = Flags.READONLY,
  facets = @Facet("BacnetBitStringUtil.BACNET_OBJECT_TYPES_SUPPORTED_FACETS")
)
/*
 maxAPDULengthAccepted is the maximum number of bytes that may be
 contained in a single incoming Bacnet application message.
 */
@NiagaraProperty(
  name = "maxAPDULengthAccepted",
  type = "int",
  defaultValue = "ConfirmedRequestPdu.MAX_APDU_LENGTH_UP_TO_1476_OCTETS",
  flags = Flags.READONLY
)
/*
 segmentationSupported states the level of message segmentation
 supported by Niagara.
 */
@NiagaraProperty(
  name = "segmentationSupported",
  type = "BBacnetSegmentation",
  defaultValue = "BBacnetSegmentation.segmentedBoth",
  flags = Flags.READONLY
)
/*
 maximum number of segments that can be accepted in an APDU.
 */
@NiagaraProperty(
  name = "maxSegmentsAccepted",
  type = "int",
  defaultValue = "ConfirmedRequestPdu.MAX_SEGS_GREATER_THAN_64",
  flags = Flags.READONLY
)
/*
 apduSegmentTimeout is the time in milliseconds between
 retransmissions of one segment of an APDU.
 */
@NiagaraProperty(
  name = "apduSegmentTimeout",
  type = "int",
  defaultValue = "2000",
  facets = {
    @Facet(name = "BFacets.UNITS", value = "BUnit.getUnit(\"millisecond\")"),
    @Facet(name = "BFacets.MIN", value = "0")
  }
)
/*
 apduTimeout is the time in milliseconds between retransmissions
 of an APDU.
 */
@NiagaraProperty(
  name = "apduTimeout",
  type = "int",
  defaultValue = "3000",
  facets = {
    @Facet(name = "BFacets.UNITS", value = "BUnit.getUnit(\"millisecond\")"),
    @Facet(name = "BFacets.MIN", value = "0")
  }
)
/*
 numberOfApduRetries indicates the number of retransmissions
 of an APDU that will be attempted before the transaction is abandoned.
 */
@NiagaraProperty(
  name = "numberOfApduRetries",
  type = "int",
  defaultValue = "3"
)
@NiagaraProperty(
  name = "deviceAddressBinding",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetAddressBinding.TYPE)",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "databaseRevision",
  type = "int",
  defaultValue = "1",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "configurationFiles",
  type = "BBacnetArray",
  defaultValue = "new BBacnetArray(BBacnetObjectIdentifier.TYPE)",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "lastRestoreTime",
  type = "BBacnetTimeStamp",
  defaultValue = "new BBacnetTimeStamp(new BBacnetDateTime())",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "backupFailureTimeout",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(180)",
  facets = {
    @Facet(name = "BFacets.SHOW_MILLISECONDS", value = "false"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.DEFAULT")
  }
)
@NiagaraProperty(
  name = "backupPreparationTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(60000)",
  flags = Flags.READONLY,
  facets = {
    @Facet(name = "BFacets.SHOW_MILLISECONDS", value = "true"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.DEFAULT")
  }
)
@NiagaraProperty(
  name = "restorePreparationTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(60000)",
  flags = Flags.READONLY,
  facets = {
    @Facet(name = "BFacets.SHOW_MILLISECONDS", value = "true"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.DEFAULT")
  }
)
@NiagaraProperty(
  name = "restoreCompletionTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(180000)",
  flags = Flags.READONLY,
  facets = {
    @Facet(name = "BFacets.SHOW_MILLISECONDS", value = "true"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.DEFAULT")
  }
)
@NiagaraProperty(
  name = "backupAndRestoreState",
  type = "BBacnetBackupState",
  defaultValue = "BBacnetBackupState.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 list of subscribed Cov recipients.
 */
@NiagaraProperty(
  name = "activeCovSubscriptions",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetCovSubscription.TYPE)",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "characterSet",
  type = "BCharacterSetEncoding",
  defaultValue = "BCharacterSetEncoding.iso10646_UTF8"
)
@NiagaraProperty(
  name = "enumerationList",
  type = "BExtensibleEnumList",
  defaultValue = "new BExtensibleEnumList()"
)
@NiagaraProperty(
  name = "exportTable",
  type = "BComponent",
  defaultValue = "new BBacnetExportTable()"
)
@NiagaraProperty(
  name = "virtual",
  type = "BLocalBacnetVirtualGateway",
  defaultValue = "new BLocalBacnetVirtualGateway()",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "covPropertyPollRate",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(5)",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "timeSynchronizationRecipients",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetRecipient.TYPE)"
)
@NiagaraProperty(
  name = "timeSynchronizationInterval",
  type = "BRelTime",
  defaultValue = "BRelTime.make(86400000)",
  facets = {
    @Facet(name = "BFacets.SHOW_SECONDS", value = "false"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.DEFAULT")
  }
)
@NiagaraProperty(
  name = "alignIntervals",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "intervalOffset",
  type = "int",
  defaultValue = "0",
  facets = {
    @Facet(name = "BFacets.UNITS", value = "BUnit.getUnit(\"minute\")"),
    @Facet(name = "BFacets.MIN", value = "0"),
    @Facet(name = "BFacets.MAX", value = "1440")
  }
)
@NiagaraProperty(
  name = "utcTimeSynchronizationRecipients",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetRecipient.TYPE)"
)
@NiagaraProperty(
  name = "lastRestartReason",
  type = "BBacnetRestartReason",
  defaultValue = "BBacnetRestartReason.unknown"
)
@NiagaraProperty(
  name = "timeOfDeviceRestart",
  type = "BBacnetTimeStamp",
  defaultValue = "new BBacnetTimeStamp(BAbsTime.make())",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "restartNotificationRecipients",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetRecipient.TYPE)"
)
@NiagaraAction(
  name = "sendIAm"
)
@NiagaraAction(
  name = "setBackupMode",
  parameterType = "BBoolean",
  defaultValue = "BBoolean.FALSE",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "setRestoreMode",
  parameterType = "BBoolean",
  defaultValue = "BBoolean.FALSE",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "println",
  parameterType = "BString",
  defaultValue = "BString.make(\"\")"
)
/*
 action for automatic time synch sending.
 */
@NiagaraAction(
  name = "sendTimeSynch",
  flags = Flags.HIDDEN
)
/*
 action for checking for duplicate export descriptors
 */
@NiagaraAction(
  name = "checkDuplicates",
  flags = Flags.HIDDEN
)
/*
 action for sending a restart notification
 */
@NiagaraAction(
  name = "sendRestartNotifications",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "changeDeviceUuid",
  parameterType = "BUuid",
  defaultValue = "BUuid.NULL",
  flags = Flags.CONFIRM_REQUIRED
)
public class BLocalBacnetDevice
  extends BComponent
  implements BIBacnetExportObject,
             BIBacnetObjectContainer,
             BacnetPropertyListProvider
{
  private static final BBacnetBitString SERVER_OBJECT_TYPES_SUPPORTED =
    BBacnetBitString.make(new boolean[]
      {
    /* 0 */ true,   true,   true,   true,   true,   true,   true,   false,  true,   true,
    /* 1 */ true, false, true, true, true, true, false, true, false, true,
    /* 2 */ true, false, false, false, false, false, false, false, false, true,
    /* 3 */ false, false, false, false, false, false, false, false, false, false,
    /* 4 */ true, false, false, false, false, true, true, false, true, false,
    /* 5 */ false, false, false, false, false
      });//0      1       2       3       4       5       6       7       8       9

  private static final BBacnetBitString SERVER_SERVICES_SUPPORTED =
    BBacnetBitString.make(new boolean[]
      {
    /* 0 */ true, true, true, true, true, true, true, true, true, true,
    /* 1 */ true,  true,  true,   false,  true,   true,   true,   true,   true,   false,
    /* 2 */ true, false, false, false, false, false, true, true, true, true,
    /* 3 */ true, false, true, true, true, true, true, false, true, true,
    /* 4 */ false,
      });//0      1       2       3       4       5       6       7       8       9

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BLocalBacnetDevice(3539114905)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the Bacnet Object Identifier used by Niagara in
   * Bacnet communications.  Niagara acts as a Bacnet Device Object.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(0, BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE), BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.DEVICE));

  /**
   * Get the {@code objectId} property.
   * objectId is the Bacnet Object Identifier used by Niagara in
   * Bacnet communications.  Niagara acts as a Bacnet Device Object.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * objectId is the Bacnet Object Identifier used by Niagara in
   * Bacnet communications.  Niagara acts as a Bacnet Device Object.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "systemStatus"

  /**
   * Slot for the {@code systemStatus} property.
   * @see #getSystemStatus
   * @see #setSystemStatus
   */
  public static final Property systemStatus = newProperty(Flags.READONLY | Flags.TRANSIENT, BBacnetDeviceStatus.operational, null);

  /**
   * Get the {@code systemStatus} property.
   * @see #systemStatus
   */
  public BBacnetDeviceStatus getSystemStatus() { return (BBacnetDeviceStatus)get(systemStatus); }

  /**
   * Set the {@code systemStatus} property.
   * @see #systemStatus
   */
  public void setSystemStatus(BBacnetDeviceStatus v) { set(systemStatus, v, null); }

  //endregion Property "systemStatus"

  //region Property "vendorName"

  /**
   * Slot for the {@code vendorName} property.
   * @see #getVendorName
   * @see #setVendorName
   */
  public static final Property vendorName = newProperty(Flags.READONLY, "Tridium", null);

  /**
   * Get the {@code vendorName} property.
   * @see #vendorName
   */
  public String getVendorName() { return getString(vendorName); }

  /**
   * Set the {@code vendorName} property.
   * @see #vendorName
   */
  public void setVendorName(String v) { setString(vendorName, v, null); }

  //endregion Property "vendorName"

  //region Property "vendorId"

  /**
   * Slot for the {@code vendorId} property.
   * @see #getVendorId
   * @see #setVendorId
   */
  public static final Property vendorId = newProperty(Flags.READONLY, 36, null);

  /**
   * Get the {@code vendorId} property.
   * @see #vendorId
   */
  public int getVendorId() { return getInt(vendorId); }

  /**
   * Set the {@code vendorId} property.
   * @see #vendorId
   */
  public void setVendorId(int v) { setInt(vendorId, v, null); }

  //endregion Property "vendorId"

  //region Property "modelName"

  /**
   * Slot for the {@code modelName} property.
   * @see #getModelName
   * @see #setModelName
   */
  public static final Property modelName = newProperty(Flags.READONLY, "Niagara4 Station", null);

  /**
   * Get the {@code modelName} property.
   * @see #modelName
   */
  public String getModelName() { return getString(modelName); }

  /**
   * Set the {@code modelName} property.
   * @see #modelName
   */
  public void setModelName(String v) { setString(modelName, v, null); }

  //endregion Property "modelName"

  //region Property "firmwareRevision"

  /**
   * Slot for the {@code firmwareRevision} property.
   * @see #getFirmwareRevision
   * @see #setFirmwareRevision
   */
  public static final Property firmwareRevision = newProperty(Flags.READONLY, "unknown", null);

  /**
   * Get the {@code firmwareRevision} property.
   * @see #firmwareRevision
   */
  public String getFirmwareRevision() { return getString(firmwareRevision); }

  /**
   * Set the {@code firmwareRevision} property.
   * @see #firmwareRevision
   */
  public void setFirmwareRevision(String v) { setString(firmwareRevision, v, null); }

  //endregion Property "firmwareRevision"

  //region Property "applicationSoftwareVersion"

  /**
   * Slot for the {@code applicationSoftwareVersion} property.
   * @see #getApplicationSoftwareVersion
   * @see #setApplicationSoftwareVersion
   */
  public static final Property applicationSoftwareVersion = newProperty(Flags.READONLY, "unknown", null);

  /**
   * Get the {@code applicationSoftwareVersion} property.
   * @see #applicationSoftwareVersion
   */
  public String getApplicationSoftwareVersion() { return getString(applicationSoftwareVersion); }

  /**
   * Set the {@code applicationSoftwareVersion} property.
   * @see #applicationSoftwareVersion
   */
  public void setApplicationSoftwareVersion(String v) { setString(applicationSoftwareVersion, v, null); }

  //endregion Property "applicationSoftwareVersion"

  //region Property "location"

  /**
   * Slot for the {@code location} property.
   * @see #getLocation
   * @see #setLocation
   */
  public static final Property location = newProperty(0, "unknown", null);

  /**
   * Get the {@code location} property.
   * @see #location
   */
  public String getLocation() { return getString(location); }

  /**
   * Set the {@code location} property.
   * @see #location
   */
  public void setLocation(String v) { setString(location, v, null); }

  //endregion Property "location"

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(0, "Local BACnet Device object", null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Property "deviceUuid"

  /**
   * Slot for the {@code deviceUuid} property.
   * Identifies the device regardless of its current VMAC address or device instance number. It shall
   * be generated before first deployment of the device in an installation, shall be persistently
   * stored across device restarts, and shall not change over the entire lifetime of a device. If a
   * device is replaced in an installation, the new device is not required to re-use the UUID of the
   * replaced device.
   * @since Niagara 4.11
   * @see #getDeviceUuid
   * @see #setDeviceUuid
   */
  public static final Property deviceUuid = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BUuid.DEFAULT, BFacets.make(BFacets.SECURITY, true));

  /**
   * Get the {@code deviceUuid} property.
   * Identifies the device regardless of its current VMAC address or device instance number. It shall
   * be generated before first deployment of the device in an installation, shall be persistently
   * stored across device restarts, and shall not change over the entire lifetime of a device. If a
   * device is replaced in an installation, the new device is not required to re-use the UUID of the
   * replaced device.
   * @since Niagara 4.11
   * @see #deviceUuid
   */
  public BUuid getDeviceUuid() { return (BUuid)get(deviceUuid); }

  /**
   * Set the {@code deviceUuid} property.
   * Identifies the device regardless of its current VMAC address or device instance number. It shall
   * be generated before first deployment of the device in an installation, shall be persistently
   * stored across device restarts, and shall not change over the entire lifetime of a device. If a
   * device is replaced in an installation, the new device is not required to re-use the UUID of the
   * replaced device.
   * @since Niagara 4.11
   * @see #deviceUuid
   */
  public void setDeviceUuid(BUuid v) { set(deviceUuid, v, null); }

  //endregion Property "deviceUuid"

  //region Property "protocolVersion"

  /**
   * Slot for the {@code protocolVersion} property.
   * @see #getProtocolVersion
   * @see #setProtocolVersion
   */
  public static final Property protocolVersion = newProperty(Flags.READONLY, 1, null);

  /**
   * Get the {@code protocolVersion} property.
   * @see #protocolVersion
   */
  public int getProtocolVersion() { return getInt(protocolVersion); }

  /**
   * Set the {@code protocolVersion} property.
   * @see #protocolVersion
   */
  public void setProtocolVersion(int v) { setInt(protocolVersion, v, null); }

  //endregion Property "protocolVersion"

  //region Property "protocolRevision"

  /**
   * Slot for the {@code protocolRevision} property.
   * @see #getProtocolRevision
   * @see #setProtocolRevision
   */
  public static final Property protocolRevision = newProperty(Flags.READONLY, 14, null);

  /**
   * Get the {@code protocolRevision} property.
   * @see #protocolRevision
   */
  public int getProtocolRevision() { return getInt(protocolRevision); }

  /**
   * Set the {@code protocolRevision} property.
   * @see #protocolRevision
   */
  public void setProtocolRevision(int v) { setInt(protocolRevision, v, null); }

  //endregion Property "protocolRevision"

  //region Property "protocolConformanceClass"

  /**
   * Slot for the {@code protocolConformanceClass} property.
   * @see #getProtocolConformanceClass
   * @see #setProtocolConformanceClass
   */
  public static final Property protocolConformanceClass = newProperty(Flags.READONLY | Flags.HIDDEN, 3, null);

  /**
   * Get the {@code protocolConformanceClass} property.
   * @see #protocolConformanceClass
   */
  public int getProtocolConformanceClass() { return getInt(protocolConformanceClass); }

  /**
   * Set the {@code protocolConformanceClass} property.
   * @see #protocolConformanceClass
   */
  public void setProtocolConformanceClass(int v) { setInt(protocolConformanceClass, v, null); }

  //endregion Property "protocolConformanceClass"

  //region Property "protocolServicesSupported"

  /**
   * Slot for the {@code protocolServicesSupported} property.
   * @see #getProtocolServicesSupported
   * @see #setProtocolServicesSupported
   */
  public static final Property protocolServicesSupported = newProperty(Flags.READONLY, SERVER_SERVICES_SUPPORTED, BacnetBitStringUtil.BACNET_SERVICES_SUPPORTED_FACETS);

  /**
   * Get the {@code protocolServicesSupported} property.
   * @see #protocolServicesSupported
   */
  public BBacnetBitString getProtocolServicesSupported() { return (BBacnetBitString)get(protocolServicesSupported); }

  /**
   * Set the {@code protocolServicesSupported} property.
   * @see #protocolServicesSupported
   */
  public void setProtocolServicesSupported(BBacnetBitString v) { set(protocolServicesSupported, v, null); }

  //endregion Property "protocolServicesSupported"

  //region Property "protocolObjectTypesSupported"

  /**
   * Slot for the {@code protocolObjectTypesSupported} property.
   * @see #getProtocolObjectTypesSupported
   * @see #setProtocolObjectTypesSupported
   */
  public static final Property protocolObjectTypesSupported = newProperty(Flags.READONLY, SERVER_OBJECT_TYPES_SUPPORTED, BacnetBitStringUtil.BACNET_OBJECT_TYPES_SUPPORTED_FACETS);

  /**
   * Get the {@code protocolObjectTypesSupported} property.
   * @see #protocolObjectTypesSupported
   */
  public BBacnetBitString getProtocolObjectTypesSupported() { return (BBacnetBitString)get(protocolObjectTypesSupported); }

  /**
   * Set the {@code protocolObjectTypesSupported} property.
   * @see #protocolObjectTypesSupported
   */
  public void setProtocolObjectTypesSupported(BBacnetBitString v) { set(protocolObjectTypesSupported, v, null); }

  //endregion Property "protocolObjectTypesSupported"

  //region Property "maxAPDULengthAccepted"

  /**
   * Slot for the {@code maxAPDULengthAccepted} property.
   * maxAPDULengthAccepted is the maximum number of bytes that may be
   * contained in a single incoming Bacnet application message.
   * @see #getMaxAPDULengthAccepted
   * @see #setMaxAPDULengthAccepted
   */
  public static final Property maxAPDULengthAccepted = newProperty(Flags.READONLY, ConfirmedRequestPdu.MAX_APDU_LENGTH_UP_TO_1476_OCTETS, null);

  /**
   * Get the {@code maxAPDULengthAccepted} property.
   * maxAPDULengthAccepted is the maximum number of bytes that may be
   * contained in a single incoming Bacnet application message.
   * @see #maxAPDULengthAccepted
   */
  public int getMaxAPDULengthAccepted() { return getInt(maxAPDULengthAccepted); }

  /**
   * Set the {@code maxAPDULengthAccepted} property.
   * maxAPDULengthAccepted is the maximum number of bytes that may be
   * contained in a single incoming Bacnet application message.
   * @see #maxAPDULengthAccepted
   */
  public void setMaxAPDULengthAccepted(int v) { setInt(maxAPDULengthAccepted, v, null); }

  //endregion Property "maxAPDULengthAccepted"

  //region Property "segmentationSupported"

  /**
   * Slot for the {@code segmentationSupported} property.
   * segmentationSupported states the level of message segmentation
   * supported by Niagara.
   * @see #getSegmentationSupported
   * @see #setSegmentationSupported
   */
  public static final Property segmentationSupported = newProperty(Flags.READONLY, BBacnetSegmentation.segmentedBoth, null);

  /**
   * Get the {@code segmentationSupported} property.
   * segmentationSupported states the level of message segmentation
   * supported by Niagara.
   * @see #segmentationSupported
   */
  public BBacnetSegmentation getSegmentationSupported() { return (BBacnetSegmentation)get(segmentationSupported); }

  /**
   * Set the {@code segmentationSupported} property.
   * segmentationSupported states the level of message segmentation
   * supported by Niagara.
   * @see #segmentationSupported
   */
  public void setSegmentationSupported(BBacnetSegmentation v) { set(segmentationSupported, v, null); }

  //endregion Property "segmentationSupported"

  //region Property "maxSegmentsAccepted"

  /**
   * Slot for the {@code maxSegmentsAccepted} property.
   * maximum number of segments that can be accepted in an APDU.
   * @see #getMaxSegmentsAccepted
   * @see #setMaxSegmentsAccepted
   */
  public static final Property maxSegmentsAccepted = newProperty(Flags.READONLY, ConfirmedRequestPdu.MAX_SEGS_GREATER_THAN_64, null);

  /**
   * Get the {@code maxSegmentsAccepted} property.
   * maximum number of segments that can be accepted in an APDU.
   * @see #maxSegmentsAccepted
   */
  public int getMaxSegmentsAccepted() { return getInt(maxSegmentsAccepted); }

  /**
   * Set the {@code maxSegmentsAccepted} property.
   * maximum number of segments that can be accepted in an APDU.
   * @see #maxSegmentsAccepted
   */
  public void setMaxSegmentsAccepted(int v) { setInt(maxSegmentsAccepted, v, null); }

  //endregion Property "maxSegmentsAccepted"

  //region Property "apduSegmentTimeout"

  /**
   * Slot for the {@code apduSegmentTimeout} property.
   * apduSegmentTimeout is the time in milliseconds between
   * retransmissions of one segment of an APDU.
   * @see #getApduSegmentTimeout
   * @see #setApduSegmentTimeout
   */
  public static final Property apduSegmentTimeout = newProperty(0, 2000, BFacets.make(BFacets.make(BFacets.UNITS, BUnit.getUnit("millisecond")), BFacets.make(BFacets.MIN, 0)));

  /**
   * Get the {@code apduSegmentTimeout} property.
   * apduSegmentTimeout is the time in milliseconds between
   * retransmissions of one segment of an APDU.
   * @see #apduSegmentTimeout
   */
  public int getApduSegmentTimeout() { return getInt(apduSegmentTimeout); }

  /**
   * Set the {@code apduSegmentTimeout} property.
   * apduSegmentTimeout is the time in milliseconds between
   * retransmissions of one segment of an APDU.
   * @see #apduSegmentTimeout
   */
  public void setApduSegmentTimeout(int v) { setInt(apduSegmentTimeout, v, null); }

  //endregion Property "apduSegmentTimeout"

  //region Property "apduTimeout"

  /**
   * Slot for the {@code apduTimeout} property.
   * apduTimeout is the time in milliseconds between retransmissions
   * of an APDU.
   * @see #getApduTimeout
   * @see #setApduTimeout
   */
  public static final Property apduTimeout = newProperty(0, 3000, BFacets.make(BFacets.make(BFacets.UNITS, BUnit.getUnit("millisecond")), BFacets.make(BFacets.MIN, 0)));

  /**
   * Get the {@code apduTimeout} property.
   * apduTimeout is the time in milliseconds between retransmissions
   * of an APDU.
   * @see #apduTimeout
   */
  public int getApduTimeout() { return getInt(apduTimeout); }

  /**
   * Set the {@code apduTimeout} property.
   * apduTimeout is the time in milliseconds between retransmissions
   * of an APDU.
   * @see #apduTimeout
   */
  public void setApduTimeout(int v) { setInt(apduTimeout, v, null); }

  //endregion Property "apduTimeout"

  //region Property "numberOfApduRetries"

  /**
   * Slot for the {@code numberOfApduRetries} property.
   * numberOfApduRetries indicates the number of retransmissions
   * of an APDU that will be attempted before the transaction is abandoned.
   * @see #getNumberOfApduRetries
   * @see #setNumberOfApduRetries
   */
  public static final Property numberOfApduRetries = newProperty(0, 3, null);

  /**
   * Get the {@code numberOfApduRetries} property.
   * numberOfApduRetries indicates the number of retransmissions
   * of an APDU that will be attempted before the transaction is abandoned.
   * @see #numberOfApduRetries
   */
  public int getNumberOfApduRetries() { return getInt(numberOfApduRetries); }

  /**
   * Set the {@code numberOfApduRetries} property.
   * numberOfApduRetries indicates the number of retransmissions
   * of an APDU that will be attempted before the transaction is abandoned.
   * @see #numberOfApduRetries
   */
  public void setNumberOfApduRetries(int v) { setInt(numberOfApduRetries, v, null); }

  //endregion Property "numberOfApduRetries"

  //region Property "deviceAddressBinding"

  /**
   * Slot for the {@code deviceAddressBinding} property.
   * @see #getDeviceAddressBinding
   * @see #setDeviceAddressBinding
   */
  public static final Property deviceAddressBinding = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT, new BBacnetListOf(BBacnetAddressBinding.TYPE), null);

  /**
   * Get the {@code deviceAddressBinding} property.
   * @see #deviceAddressBinding
   */
  public BBacnetListOf getDeviceAddressBinding() { return (BBacnetListOf)get(deviceAddressBinding); }

  /**
   * Set the {@code deviceAddressBinding} property.
   * @see #deviceAddressBinding
   */
  public void setDeviceAddressBinding(BBacnetListOf v) { set(deviceAddressBinding, v, null); }

  //endregion Property "deviceAddressBinding"

  //region Property "databaseRevision"

  /**
   * Slot for the {@code databaseRevision} property.
   * @see #getDatabaseRevision
   * @see #setDatabaseRevision
   */
  public static final Property databaseRevision = newProperty(Flags.READONLY, 1, null);

  /**
   * Get the {@code databaseRevision} property.
   * @see #databaseRevision
   */
  public int getDatabaseRevision() { return getInt(databaseRevision); }

  /**
   * Set the {@code databaseRevision} property.
   * @see #databaseRevision
   */
  public void setDatabaseRevision(int v) { setInt(databaseRevision, v, null); }

  //endregion Property "databaseRevision"

  //region Property "configurationFiles"

  /**
   * Slot for the {@code configurationFiles} property.
   * @see #getConfigurationFiles
   * @see #setConfigurationFiles
   */
  public static final Property configurationFiles = newProperty(Flags.READONLY | Flags.HIDDEN, new BBacnetArray(BBacnetObjectIdentifier.TYPE), null);

  /**
   * Get the {@code configurationFiles} property.
   * @see #configurationFiles
   */
  public BBacnetArray getConfigurationFiles() { return (BBacnetArray)get(configurationFiles); }

  /**
   * Set the {@code configurationFiles} property.
   * @see #configurationFiles
   */
  public void setConfigurationFiles(BBacnetArray v) { set(configurationFiles, v, null); }

  //endregion Property "configurationFiles"

  //region Property "lastRestoreTime"

  /**
   * Slot for the {@code lastRestoreTime} property.
   * @see #getLastRestoreTime
   * @see #setLastRestoreTime
   */
  public static final Property lastRestoreTime = newProperty(Flags.READONLY, new BBacnetTimeStamp(new BBacnetDateTime()), null);

  /**
   * Get the {@code lastRestoreTime} property.
   * @see #lastRestoreTime
   */
  public BBacnetTimeStamp getLastRestoreTime() { return (BBacnetTimeStamp)get(lastRestoreTime); }

  /**
   * Set the {@code lastRestoreTime} property.
   * @see #lastRestoreTime
   */
  public void setLastRestoreTime(BBacnetTimeStamp v) { set(lastRestoreTime, v, null); }

  //endregion Property "lastRestoreTime"

  //region Property "backupFailureTimeout"

  /**
   * Slot for the {@code backupFailureTimeout} property.
   * @see #getBackupFailureTimeout
   * @see #setBackupFailureTimeout
   */
  public static final Property backupFailureTimeout = newProperty(0, BRelTime.makeSeconds(180), BFacets.make(BFacets.make(BFacets.SHOW_MILLISECONDS, false), BFacets.make(BFacets.MIN, BRelTime.DEFAULT)));

  /**
   * Get the {@code backupFailureTimeout} property.
   * @see #backupFailureTimeout
   */
  public BRelTime getBackupFailureTimeout() { return (BRelTime)get(backupFailureTimeout); }

  /**
   * Set the {@code backupFailureTimeout} property.
   * @see #backupFailureTimeout
   */
  public void setBackupFailureTimeout(BRelTime v) { set(backupFailureTimeout, v, null); }

  //endregion Property "backupFailureTimeout"

  //region Property "backupPreparationTime"

  /**
   * Slot for the {@code backupPreparationTime} property.
   * @see #getBackupPreparationTime
   * @see #setBackupPreparationTime
   */
  public static final Property backupPreparationTime = newProperty(Flags.READONLY, BRelTime.make(60000), BFacets.make(BFacets.make(BFacets.SHOW_MILLISECONDS, true), BFacets.make(BFacets.MIN, BRelTime.DEFAULT)));

  /**
   * Get the {@code backupPreparationTime} property.
   * @see #backupPreparationTime
   */
  public BRelTime getBackupPreparationTime() { return (BRelTime)get(backupPreparationTime); }

  /**
   * Set the {@code backupPreparationTime} property.
   * @see #backupPreparationTime
   */
  public void setBackupPreparationTime(BRelTime v) { set(backupPreparationTime, v, null); }

  //endregion Property "backupPreparationTime"

  //region Property "restorePreparationTime"

  /**
   * Slot for the {@code restorePreparationTime} property.
   * @see #getRestorePreparationTime
   * @see #setRestorePreparationTime
   */
  public static final Property restorePreparationTime = newProperty(Flags.READONLY, BRelTime.make(60000), BFacets.make(BFacets.make(BFacets.SHOW_MILLISECONDS, true), BFacets.make(BFacets.MIN, BRelTime.DEFAULT)));

  /**
   * Get the {@code restorePreparationTime} property.
   * @see #restorePreparationTime
   */
  public BRelTime getRestorePreparationTime() { return (BRelTime)get(restorePreparationTime); }

  /**
   * Set the {@code restorePreparationTime} property.
   * @see #restorePreparationTime
   */
  public void setRestorePreparationTime(BRelTime v) { set(restorePreparationTime, v, null); }

  //endregion Property "restorePreparationTime"

  //region Property "restoreCompletionTime"

  /**
   * Slot for the {@code restoreCompletionTime} property.
   * @see #getRestoreCompletionTime
   * @see #setRestoreCompletionTime
   */
  public static final Property restoreCompletionTime = newProperty(Flags.READONLY, BRelTime.make(180000), BFacets.make(BFacets.make(BFacets.SHOW_MILLISECONDS, true), BFacets.make(BFacets.MIN, BRelTime.DEFAULT)));

  /**
   * Get the {@code restoreCompletionTime} property.
   * @see #restoreCompletionTime
   */
  public BRelTime getRestoreCompletionTime() { return (BRelTime)get(restoreCompletionTime); }

  /**
   * Set the {@code restoreCompletionTime} property.
   * @see #restoreCompletionTime
   */
  public void setRestoreCompletionTime(BRelTime v) { set(restoreCompletionTime, v, null); }

  //endregion Property "restoreCompletionTime"

  //region Property "backupAndRestoreState"

  /**
   * Slot for the {@code backupAndRestoreState} property.
   * @see #getBackupAndRestoreState
   * @see #setBackupAndRestoreState
   */
  public static final Property backupAndRestoreState = newProperty(Flags.TRANSIENT | Flags.READONLY, BBacnetBackupState.DEFAULT, null);

  /**
   * Get the {@code backupAndRestoreState} property.
   * @see #backupAndRestoreState
   */
  public BBacnetBackupState getBackupAndRestoreState() { return (BBacnetBackupState)get(backupAndRestoreState); }

  /**
   * Set the {@code backupAndRestoreState} property.
   * @see #backupAndRestoreState
   */
  public void setBackupAndRestoreState(BBacnetBackupState v) { set(backupAndRestoreState, v, null); }

  //endregion Property "backupAndRestoreState"

  //region Property "activeCovSubscriptions"

  /**
   * Slot for the {@code activeCovSubscriptions} property.
   * list of subscribed Cov recipients.
   * @see #getActiveCovSubscriptions
   * @see #setActiveCovSubscriptions
   */
  public static final Property activeCovSubscriptions = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT, new BBacnetListOf(BBacnetCovSubscription.TYPE), null);

  /**
   * Get the {@code activeCovSubscriptions} property.
   * list of subscribed Cov recipients.
   * @see #activeCovSubscriptions
   */
  public BBacnetListOf getActiveCovSubscriptions() { return (BBacnetListOf)get(activeCovSubscriptions); }

  /**
   * Set the {@code activeCovSubscriptions} property.
   * list of subscribed Cov recipients.
   * @see #activeCovSubscriptions
   */
  public void setActiveCovSubscriptions(BBacnetListOf v) { set(activeCovSubscriptions, v, null); }

  //endregion Property "activeCovSubscriptions"

  //region Property "characterSet"

  /**
   * Slot for the {@code characterSet} property.
   * @see #getCharacterSet
   * @see #setCharacterSet
   */
  public static final Property characterSet = newProperty(0, BCharacterSetEncoding.iso10646_UTF8, null);

  /**
   * Get the {@code characterSet} property.
   * @see #characterSet
   */
  public BCharacterSetEncoding getCharacterSet() { return (BCharacterSetEncoding)get(characterSet); }

  /**
   * Set the {@code characterSet} property.
   * @see #characterSet
   */
  public void setCharacterSet(BCharacterSetEncoding v) { set(characterSet, v, null); }

  //endregion Property "characterSet"

  //region Property "enumerationList"

  /**
   * Slot for the {@code enumerationList} property.
   * @see #getEnumerationList
   * @see #setEnumerationList
   */
  public static final Property enumerationList = newProperty(0, new BExtensibleEnumList(), null);

  /**
   * Get the {@code enumerationList} property.
   * @see #enumerationList
   */
  public BExtensibleEnumList getEnumerationList() { return (BExtensibleEnumList)get(enumerationList); }

  /**
   * Set the {@code enumerationList} property.
   * @see #enumerationList
   */
  public void setEnumerationList(BExtensibleEnumList v) { set(enumerationList, v, null); }

  //endregion Property "enumerationList"

  //region Property "exportTable"

  /**
   * Slot for the {@code exportTable} property.
   * @see #getExportTable
   * @see #setExportTable
   */
  public static final Property exportTable = newProperty(0, new BBacnetExportTable(), null);

  /**
   * Get the {@code exportTable} property.
   * @see #exportTable
   */
  public BComponent getExportTable() { return (BComponent)get(exportTable); }

  /**
   * Set the {@code exportTable} property.
   * @see #exportTable
   */
  public void setExportTable(BComponent v) { set(exportTable, v, null); }

  //endregion Property "exportTable"

  //region Property "virtual"

  /**
   * Slot for the {@code virtual} property.
   * @see #getVirtual
   * @see #setVirtual
   */
  public static final Property virtual = newProperty(Flags.HIDDEN, new BLocalBacnetVirtualGateway(), null);

  /**
   * Get the {@code virtual} property.
   * @see #virtual
   */
  public BLocalBacnetVirtualGateway getVirtual() { return (BLocalBacnetVirtualGateway)get(virtual); }

  /**
   * Set the {@code virtual} property.
   * @see #virtual
   */
  public void setVirtual(BLocalBacnetVirtualGateway v) { set(virtual, v, null); }

  //endregion Property "virtual"

  //region Property "covPropertyPollRate"

  /**
   * Slot for the {@code covPropertyPollRate} property.
   * @see #getCovPropertyPollRate
   * @see #setCovPropertyPollRate
   */
  public static final Property covPropertyPollRate = newProperty(Flags.HIDDEN, BRelTime.makeSeconds(5), null);

  /**
   * Get the {@code covPropertyPollRate} property.
   * @see #covPropertyPollRate
   */
  public BRelTime getCovPropertyPollRate() { return (BRelTime)get(covPropertyPollRate); }

  /**
   * Set the {@code covPropertyPollRate} property.
   * @see #covPropertyPollRate
   */
  public void setCovPropertyPollRate(BRelTime v) { set(covPropertyPollRate, v, null); }

  //endregion Property "covPropertyPollRate"

  //region Property "timeSynchronizationRecipients"

  /**
   * Slot for the {@code timeSynchronizationRecipients} property.
   * @see #getTimeSynchronizationRecipients
   * @see #setTimeSynchronizationRecipients
   */
  public static final Property timeSynchronizationRecipients = newProperty(0, new BBacnetListOf(BBacnetRecipient.TYPE), null);

  /**
   * Get the {@code timeSynchronizationRecipients} property.
   * @see #timeSynchronizationRecipients
   */
  public BBacnetListOf getTimeSynchronizationRecipients() { return (BBacnetListOf)get(timeSynchronizationRecipients); }

  /**
   * Set the {@code timeSynchronizationRecipients} property.
   * @see #timeSynchronizationRecipients
   */
  public void setTimeSynchronizationRecipients(BBacnetListOf v) { set(timeSynchronizationRecipients, v, null); }

  //endregion Property "timeSynchronizationRecipients"

  //region Property "timeSynchronizationInterval"

  /**
   * Slot for the {@code timeSynchronizationInterval} property.
   * @see #getTimeSynchronizationInterval
   * @see #setTimeSynchronizationInterval
   */
  public static final Property timeSynchronizationInterval = newProperty(0, BRelTime.make(86400000), BFacets.make(BFacets.make(BFacets.SHOW_SECONDS, false), BFacets.make(BFacets.MIN, BRelTime.DEFAULT)));

  /**
   * Get the {@code timeSynchronizationInterval} property.
   * @see #timeSynchronizationInterval
   */
  public BRelTime getTimeSynchronizationInterval() { return (BRelTime)get(timeSynchronizationInterval); }

  /**
   * Set the {@code timeSynchronizationInterval} property.
   * @see #timeSynchronizationInterval
   */
  public void setTimeSynchronizationInterval(BRelTime v) { set(timeSynchronizationInterval, v, null); }

  //endregion Property "timeSynchronizationInterval"

  //region Property "alignIntervals"

  /**
   * Slot for the {@code alignIntervals} property.
   * @see #getAlignIntervals
   * @see #setAlignIntervals
   */
  public static final Property alignIntervals = newProperty(0, true, null);

  /**
   * Get the {@code alignIntervals} property.
   * @see #alignIntervals
   */
  public boolean getAlignIntervals() { return getBoolean(alignIntervals); }

  /**
   * Set the {@code alignIntervals} property.
   * @see #alignIntervals
   */
  public void setAlignIntervals(boolean v) { setBoolean(alignIntervals, v, null); }

  //endregion Property "alignIntervals"

  //region Property "intervalOffset"

  /**
   * Slot for the {@code intervalOffset} property.
   * @see #getIntervalOffset
   * @see #setIntervalOffset
   */
  public static final Property intervalOffset = newProperty(0, 0, BFacets.make(BFacets.make(BFacets.make(BFacets.UNITS, BUnit.getUnit("minute")), BFacets.make(BFacets.MIN, 0)), BFacets.make(BFacets.MAX, 1440)));

  /**
   * Get the {@code intervalOffset} property.
   * @see #intervalOffset
   */
  public int getIntervalOffset() { return getInt(intervalOffset); }

  /**
   * Set the {@code intervalOffset} property.
   * @see #intervalOffset
   */
  public void setIntervalOffset(int v) { setInt(intervalOffset, v, null); }

  //endregion Property "intervalOffset"

  //region Property "utcTimeSynchronizationRecipients"

  /**
   * Slot for the {@code utcTimeSynchronizationRecipients} property.
   * @see #getUtcTimeSynchronizationRecipients
   * @see #setUtcTimeSynchronizationRecipients
   */
  public static final Property utcTimeSynchronizationRecipients = newProperty(0, new BBacnetListOf(BBacnetRecipient.TYPE), null);

  /**
   * Get the {@code utcTimeSynchronizationRecipients} property.
   * @see #utcTimeSynchronizationRecipients
   */
  public BBacnetListOf getUtcTimeSynchronizationRecipients() { return (BBacnetListOf)get(utcTimeSynchronizationRecipients); }

  /**
   * Set the {@code utcTimeSynchronizationRecipients} property.
   * @see #utcTimeSynchronizationRecipients
   */
  public void setUtcTimeSynchronizationRecipients(BBacnetListOf v) { set(utcTimeSynchronizationRecipients, v, null); }

  //endregion Property "utcTimeSynchronizationRecipients"

  //region Property "lastRestartReason"

  /**
   * Slot for the {@code lastRestartReason} property.
   * @see #getLastRestartReason
   * @see #setLastRestartReason
   */
  public static final Property lastRestartReason = newProperty(0, BBacnetRestartReason.unknown, null);

  /**
   * Get the {@code lastRestartReason} property.
   * @see #lastRestartReason
   */
  public BBacnetRestartReason getLastRestartReason() { return (BBacnetRestartReason)get(lastRestartReason); }

  /**
   * Set the {@code lastRestartReason} property.
   * @see #lastRestartReason
   */
  public void setLastRestartReason(BBacnetRestartReason v) { set(lastRestartReason, v, null); }

  //endregion Property "lastRestartReason"

  //region Property "timeOfDeviceRestart"

  /**
   * Slot for the {@code timeOfDeviceRestart} property.
   * @see #getTimeOfDeviceRestart
   * @see #setTimeOfDeviceRestart
   */
  public static final Property timeOfDeviceRestart = newProperty(Flags.TRANSIENT, new BBacnetTimeStamp(BAbsTime.make()), null);

  /**
   * Get the {@code timeOfDeviceRestart} property.
   * @see #timeOfDeviceRestart
   */
  public BBacnetTimeStamp getTimeOfDeviceRestart() { return (BBacnetTimeStamp)get(timeOfDeviceRestart); }

  /**
   * Set the {@code timeOfDeviceRestart} property.
   * @see #timeOfDeviceRestart
   */
  public void setTimeOfDeviceRestart(BBacnetTimeStamp v) { set(timeOfDeviceRestart, v, null); }

  //endregion Property "timeOfDeviceRestart"

  //region Property "restartNotificationRecipients"

  /**
   * Slot for the {@code restartNotificationRecipients} property.
   * @see #getRestartNotificationRecipients
   * @see #setRestartNotificationRecipients
   */
  public static final Property restartNotificationRecipients = newProperty(0, new BBacnetListOf(BBacnetRecipient.TYPE), null);

  /**
   * Get the {@code restartNotificationRecipients} property.
   * @see #restartNotificationRecipients
   */
  public BBacnetListOf getRestartNotificationRecipients() { return (BBacnetListOf)get(restartNotificationRecipients); }

  /**
   * Set the {@code restartNotificationRecipients} property.
   * @see #restartNotificationRecipients
   */
  public void setRestartNotificationRecipients(BBacnetListOf v) { set(restartNotificationRecipients, v, null); }

  //endregion Property "restartNotificationRecipients"

  //region Action "sendIAm"

  /**
   * Slot for the {@code sendIAm} action.
   * @see #sendIAm()
   */
  public static final Action sendIAm = newAction(0, null);

  /**
   * Invoke the {@code sendIAm} action.
   * @see #sendIAm
   */
  public void sendIAm() { invoke(sendIAm, null, null); }

  //endregion Action "sendIAm"

  //region Action "setBackupMode"

  /**
   * Slot for the {@code setBackupMode} action.
   * @see #setBackupMode(BBoolean parameter)
   */
  public static final Action setBackupMode = newAction(Flags.HIDDEN, BBoolean.FALSE, null);

  /**
   * Invoke the {@code setBackupMode} action.
   * @see #setBackupMode
   */
  public void setBackupMode(BBoolean parameter) { invoke(setBackupMode, parameter, null); }

  //endregion Action "setBackupMode"

  //region Action "setRestoreMode"

  /**
   * Slot for the {@code setRestoreMode} action.
   * @see #setRestoreMode(BBoolean parameter)
   */
  public static final Action setRestoreMode = newAction(Flags.HIDDEN, BBoolean.FALSE, null);

  /**
   * Invoke the {@code setRestoreMode} action.
   * @see #setRestoreMode
   */
  public void setRestoreMode(BBoolean parameter) { invoke(setRestoreMode, parameter, null); }

  //endregion Action "setRestoreMode"

  //region Action "println"

  /**
   * Slot for the {@code println} action.
   * @see #println(BString parameter)
   */
  public static final Action println = newAction(0, BString.make(""), null);

  /**
   * Invoke the {@code println} action.
   * @see #println
   */
  public void println(BString parameter) { invoke(println, parameter, null); }

  //endregion Action "println"

  //region Action "sendTimeSynch"

  /**
   * Slot for the {@code sendTimeSynch} action.
   * action for automatic time synch sending.
   * @see #sendTimeSynch()
   */
  public static final Action sendTimeSynch = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code sendTimeSynch} action.
   * action for automatic time synch sending.
   * @see #sendTimeSynch
   */
  public void sendTimeSynch() { invoke(sendTimeSynch, null, null); }

  //endregion Action "sendTimeSynch"

  //region Action "checkDuplicates"

  /**
   * Slot for the {@code checkDuplicates} action.
   * action for checking for duplicate export descriptors
   * @see #checkDuplicates()
   */
  public static final Action checkDuplicates = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code checkDuplicates} action.
   * action for checking for duplicate export descriptors
   * @see #checkDuplicates
   */
  public void checkDuplicates() { invoke(checkDuplicates, null, null); }

  //endregion Action "checkDuplicates"

  //region Action "sendRestartNotifications"

  /**
   * Slot for the {@code sendRestartNotifications} action.
   * action for sending a restart notification
   * @see #sendRestartNotifications()
   */
  public static final Action sendRestartNotifications = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code sendRestartNotifications} action.
   * action for sending a restart notification
   * @see #sendRestartNotifications
   */
  public void sendRestartNotifications() { invoke(sendRestartNotifications, null, null); }

  //endregion Action "sendRestartNotifications"

  //region Action "changeDeviceUuid"

  /**
   * Slot for the {@code changeDeviceUuid} action.
   * @see #changeDeviceUuid(BUuid parameter)
   */
  public static final Action changeDeviceUuid = newAction(Flags.CONFIRM_REQUIRED, BUuid.NULL, null);

  /**
   * Invoke the {@code changeDeviceUuid} action.
   * @see #changeDeviceUuid
   */
  public void changeDeviceUuid(BUuid parameter) { invoke(changeDeviceUuid, parameter, null); }

  //endregion Action "changeDeviceUuid"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalBacnetDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BLocalBacnetDevice()
  {
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  @Override
  public String toString(Context c)
  {
    return "Local Bacnet Device [" + getObjectId() + "]";
  }

  /**
   * Started.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();

    // First check for fatal faults.
    checkFatalFault();

    // Set device property defaults.
    //bacnet-rt, bacnetAws-rt, bacnetOws-rt, or 3rd party
    Type type = BBacnetNetwork.bacnet().getType();
    setFirmwareRevision(type.getVendorVersion().toString());
    setApplicationSoftwareVersion(type.getVendor() + " " + type.getVendorVersion());

    objectName = Sys.getStation().getStationName();

    // Default on clone will not work if BUuid.make() is used to set the default value of
    // deviceUuid. Therefore, it is updated here.
    if (BUuid.DEFAULT.equals(getDeviceUuid()))
    {
      setDeviceUuid(BUuid.make());
    }

    setInt(protocolRevision, 15);
    checkConfiguration();

    // Read vendor specific property values.
    network().postAsync(new Runnable()
    {
      @Override
      public void run()
      {
        readBrandProperties();
      }
    });

    // Propagate object ID changes via I-Am.
    linkTo("sendIAmLink", this, objectId, sendIAm);
    setFlags(getSlot("sendIAmLink"), getFlags(getSlot("sendIAmLink")) | Flags.HIDDEN);

    // add myself to receive station.save callbacks
    Station.addSaveListener(saveListener);

    // ensure BACnet user is there.
    try
    {
      getBacnetContext();
    }
    catch (Exception ignored)
    {}

    maxWaitTime = getApduTimeout() * (getNumberOfApduRetries() + 1);
  }

  /**
   * Descendants Started.
   */
  @Override
  public void descendantsStarted()
  {
    BFileSystem fs = BFileSystem.INSTANCE;
    FilePath path = new FilePath(LAST_RESTORE_TIME_FILENAME);

    AccessController.doPrivileged((PrivilegedAction<Void>)() ->
    {
      BIFile lastRestoreFile = fs.findFile(path);
      if (lastRestoreFile != null)
      {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(lastRestoreFile.getInputStream(), StandardCharsets.UTF_8)))
        {
          String s = br.readLine();
          BAbsTime t = BAbsTime.make(s);
          BBacnetTimeStamp ts = new BBacnetTimeStamp(t);
          setLastRestoreTime(ts);
          s = br.readLine();
          int dbRev = Integer.parseInt(s);
          setDatabaseRevision(dbRev + 1);
        }
        catch (IOException e)
        {
          log.log(Level.SEVERE, "IOException occurred reading last restore time file in descendantsStarted", e);
        }

        try
        {
          lastRestoreFile.delete();
        }
        catch (IOException e)
        {
          log.log(Level.SEVERE, "IOException occurred deleting last restore time file in descendantsStarted", e);
        }
      }
      return null;
    });
  }

  /**
   * Stopped.
   */
  @Override
  public void stopped()
  {
    // remove myself from receiving station.save callbacks
    Station.removeSaveListener(saveListener);
    covSubscriber.unsubscribeAll();
    covSubscriber = null;
    if (tsTicket != null)
    {
      tsTicket.cancel();
    }
    tsTicket = null;
    lastTSTime = null;
  }

  /**
   * Property Changed.
   */
  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
    {
      return;
    }
    if (p.equals(objectId))
    {
      checkConfiguration();
      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(apduTimeout) || p.equals(numberOfApduRetries))
    {
      maxWaitTime = getApduTimeout() * (getNumberOfApduRetries() + 1);
      BBacnetTransportLayer transport = ((BBacnetStack)((BBacnetNetwork)getParent()).getBacnetComm()).getTransport();
      long lockup = transport.getLockupThreshold().getMillis();
      if (maxWaitTime > lockup)
      {
        loggerBacnetTransport.info("Reconfiguring Transport layer lockup threshold...");
        transport.set(BBacnetTransportLayer.lockupThreshold, BRelTime.make(maxWaitTime), BacnetConst.fallback);
      }

    }
    if (p.equals(timeSynchronizationInterval) ||
        p.equals(alignIntervals) ||
        p.equals(intervalOffset))
    {
      scheduleTimeSynch();
    }
    if (p.equals(timeSynchronizationRecipients))
    {
      checkRecipients(p);
    }
    else if (p.equals(utcTimeSynchronizationRecipients))
    {
      checkRecipients(p);
    }
  }

  /**
   * This callback is invoked during station bootstrap after
   * the steady state timeout has expired.
   */
  @Override
  public void atSteadyState()
    throws Exception
  {
    super.atSteadyState();

    // Resolve the device address for each of our recipients that uses the DEVICE choice.
    checkRecipients(timeSynchronizationRecipients);
    checkRecipients(utcTimeSynchronizationRecipients);

    // Start time synch cycle.
    sendTimeSynch();
    scheduleTimeSynch();

    setTimeOfDeviceRestart(new BBacnetTimeStamp(BAbsTime.make()));
    sendRestartNotifications();
  }

  /**
   * Clock changed.  Need to resynchronize time synchronization.
   */
  @Override
  public void clockChanged(BRelTime shift)
    throws Exception
  {
    sendTimeSynch();
    scheduleTimeSynch();
  }

  /**
   * BLocalBacnetDevice can only be placed directly inside a BBacnetNetwork.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetNetwork;
  }

  /**
   * Get the nav children - filter out activeCovSubscriptions
   * and deviceAddressBinding.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    BINavNode[] kids = super.getNavChildren();
    Array<BINavNode> acc = new Array<>(BINavNode.class);
    for (int i = 0; i < kids.length; ++i)
    {
      BComponent kid = (BComponent)kids[i];
      if (kid.getType().is(BBacnetListOf.TYPE))
      {
        continue;
      }
      if (kid.getType().is(BBacnetArray.TYPE))
      {
        continue;
      }
      acc.add(kid);
    }
    return acc.trim();
  }

////////////////////////////////////////////////////////////////
// BIBacnetExportObject
////////////////////////////////////////////////////////////////

  public UUID getUuid()
  {
    BUuid bUuid = getDeviceUuid();
    long msb = bUuid.getMostSignificant();
    long lsb = bUuid.getLeastSignificant();
    return new UUID(msb, lsb);
  }

  /**
   * Get the exported object.
   */
  @Override
  public final BObject getObject()
  {
    return this;
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getOrdInSession();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    throw new UnsupportedOperationException(lex.getText("UnsupportedOperationException.localDevice.setObjectOrd"));
  }

  /**
   * Get the Object_Name property.
   */
  @Override
  public String getObjectName()
  {
    BValue objName = get(BLocalBacnetDevice.OBJECT_NAME_OVERRIDE_SLOTNAME);
    if (objName instanceof BString)
    {
      String name = ((BString)objName).getString();
      if (name.length() > 0)
      {
        return name;
      }
    }

    return objectName + "_" + getObjectId().getInstanceNumber();
  }

  /**
   * Set the Object_Name property.
   */
  @Override
  public void setObjectName(String objectName)
  {
    throw new UnsupportedOperationException(lex.getText("UnsupportedOperationException.localDevice.setObjectName"));
  }

  /**
   * Check the configuration of this object.
   */
  @Override
  public void checkConfiguration()
  {
    // quit if fatal fault
    if (isFatalFault())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      return;
    }

    boolean configOk = true;
    if (!getObjectId().isValid())
    {
      setFaultCause("Invalid Object ID");
      configOk = false;
    }

    // Check for another object with this object name, if we are running
    // in a station environment.
    if (Sys.getStation() != null)
    {
      BIBacnetExportObject o = BBacnetNetwork.localDevice().lookupBacnetObject(getObjectName());
      if ((o != this))
      {
        setFaultCause("Duplicate Object_Name");
        configOk = false;
      }
    }

    if (configOk)
    {
      setFaultCause("");
    }
    setStatus(BStatus.makeFault(getStatus(), !configOk));
  }

////////////////////////////////////////////////////////////////
// Export Table Management
////////////////////////////////////////////////////////////////

  /**
   * Export a server object.
   *
   * @param object the object to be exported.
   * @return null if the object exported ok, or a String describing the error.
   */
  public String export(BIBacnetExportObject object)
  {
    return exports().export(object);
  }

  /**
   * Export a server object only for it's Ord.
   *
   * @param object the object to be exported.
   * @return null if the object exported ok, or a String describing the error.
   */
  public String exportByOrd(BIBacnetExportObject object)
  {
    return exports().exportByOrd(object);
  }

  /**
   * Unexport a server object.
   *
   * @param objectId   the objectId that was used as the key to this object.
   * @param objectName the objectName that was used as the key to this object.
   * @param object     the object to be unexported.
   */
  public void unexport(BBacnetObjectIdentifier objectId, String objectName, BIBacnetExportObject object)
  {
    exports().unexport(objectId, objectName, object);

    if (isRunning())
    {
      checkDuplicates(object);
    }
  }

  /**
   * Check all objects marked as duplicate,
   * to see if their duplicate condition has been cleared.
   *
   * @param exclude skip this object in the check.
   */
  private void checkDuplicates(BIBacnetExportObject exclude)
  {
    synchronized (CHECK_DUP_LOCK)
    {
      if (checkDupTicket != null)
      {
        checkDupTicket.cancel();
      }

      checkDupTicket = Clock.schedule(this, CHECK_DUP_DELAY, checkDuplicates, null);
    }
  }

  public void doCheckDuplicates()
  {
    ComponentTreeCursor c = new ComponentTreeCursor(exports(), null);
    BIBacnetExportObject e = null;
    while (c.next(BIBacnetExportObject.class))
    {
      e = (BIBacnetExportObject)c.get();
      if (e.getStatus().isFault())
      {
        // check all faulted objects
        e.checkConfiguration();
      }
    }
  }

  /**
   * Get the export table.
   */
  private BBacnetExportTable exports()
  {
    return (BBacnetExportTable)getExportTable();
  }

  /**
   * Look up a BACnet server object by its Object Identifier.
   *
   * @param objectId
   * @return the server object with that id, or null if not found.
   */
  public final BIBacnetExportObject lookupBacnetObject(BBacnetObjectIdentifier objectId)
  {
    if (getObjectId().equals(objectId))
    {
      return this;
    }

    // This won't work because we could find the desired object as the first of
    // multiple objects with the same id.  Until the trap set API is implemented,
    // we must find another way to accomplish this check.
    return exports().byObjectId(objectId);
  }

  /**
   * Look up a BACnet server object by its Object Name.
   *
   * @param objectName
   * @return the server object with that name, or null if not found.
   */
  public final BIBacnetExportObject lookupBacnetObject(String objectName)
  {
    if (getObjectName().equals(objectName))
    {
      return this;
    }

    // This won't work because we could find the desired object as the first of
    // multiple objects with the same id.  Until the trap set API is implemented,
    // we must find another way to accomplish this check.
    return exports().byObjectName(objectName);
  }

  /**
   * Look up a BACnet Object Identifier by the object's ord.
   *
   * @param objectOrd
   * @return the server objectId, or null if not found.
   */
  public final BBacnetObjectIdentifier lookupBacnetObjectId(BOrd objectOrd)
  {
    if (getObjectOrd().equals(objectOrd))
    {
      return getObjectId();
    }

    // I don't think a duplicate check is necessary here??
    return exports().byObjectOrd(objectOrd);
  }

  /**
   * Get the next available instance number of the given Object Type.
   *
   * @param objectType
   * @return the next unused instance number.
   */
  public final int getNextInstance(int objectType)
  {
    return exports().getNextInstance(objectType);
  }

  /**
   * Convenience for incrementing the Database_Revision property.
   * Use whenever creating/deleting an export, or changing the id or objectName.
   */
  public final void incrementDatabaseRevision()
  {
    setDatabaseRevision(getDatabaseRevision() + 1);
  }

////////////////////////////////////////////////////////////////
// BIBacnetObjectContainer
////////////////////////////////////////////////////////////////

  /**
   * Implementation of <code>BIBacnetObjectContainer</code>.
   * Look up a BACnet object by the identifiers objectId, propertyId,
   * and propertyArrayIndex.  Only the objectId is used by <code>BLocalBacnetDevice</code>.
   *
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   * @param domain             (ignored here)
   * @return the <code>BIBacnetExportObject</code> with the given objectId, as a <code>BObject</code>.
   */
  @Override
  public final BObject lookupBacnetObject(BBacnetObjectIdentifier objectId,
                                          int propertyId,
                                          int propertyArrayIndex,
                                          String domain)
  {
    // propertyId and propertyArrayIndex are not used in local lookup.
    return (BObject)lookupBacnetObject(objectId);
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Send an I-Am message to announce ourselves on the Bacnet network.
   */
  public void doSendIAm()
  {
    ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().iAm();
  }

  public void doSetBackupMode(BBoolean backupMode)
  {
    if (getSystemStatus().getOrdinal() == BBacnetDeviceStatus.DOWNLOAD_IN_PROGRESS)
    {
      throw new IllegalStateException("Cannot modify backup mode while restore is in progress");
    }

    if (backupMode.getBoolean())
    {
      log.info("Entering Backup Mode...");
      preBackupRestoreStatus = getSystemStatus();
      setBackupAndRestoreState(BBacnetBackupState.preparingForBackup);
      setSystemStatus(BBacnetDeviceStatus.backupInProgress);
    }
    else
    {
      log.info("Exiting Backup Mode...");
      setBackupAndRestoreState(BBacnetBackupState.idle);
      setSystemStatus(preBackupRestoreStatus);
      ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().cleanupBackupMode();
    }
  }

  public void doSetRestoreMode(BBoolean restoreMode)
  {
    if (getSystemStatus().getOrdinal() == BBacnetDeviceStatus.BACKUP_IN_PROGRESS)
    {
      throw new IllegalStateException("Cannot modify restore mode while backup is in progress");
    }

    if (restoreMode.getBoolean())
    {
      log.info("Entering Restore Mode...");
      preBackupRestoreStatus = getSystemStatus();
      setBackupAndRestoreState(BBacnetBackupState.preparingForRestore);
      setSystemStatus(BBacnetDeviceStatus.downloadInProgress);
    }
    else
    {
      log.info("Exiting Restore Mode...");
      setBackupAndRestoreState(BBacnetBackupState.idle);
      setSystemStatus(preBackupRestoreStatus);
      ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().cleanupBackupMode();
    }
  }

  /**
   * Display a line on the output.
   */
  public void doPrintln(BString arg)
  {
    System.out.println("\n\n********\n" + arg + "\n********\n");
  }

  /**
   * Send a Time Synch message to all time synch recipients.
   */
  public final void doSendTimeSynch()
  {
    lastTSTime = BAbsTime.make();
    if (log.isLoggable(Level.FINE))
    {
      log.fine("Sending Automatic Time Synch...");
    }
    try
    {
      BBacnetClientLayer client = ((BBacnetStack)network().getBacnetComm()).getClient();
      SlotCursor<Property> c = getTimeSynchronizationRecipients().getProperties();
      BBacnetRecipient r = null;
      while (c.next(BBacnetRecipient.class))
      {
        r = (BBacnetRecipient)c.get();
        if (r.isDevice() && (!r.getDevice().isValid() || (DeviceRegistry.getDeviceAddress(r.getDevice()) == null)))
        {
          continue;
        }
        client.timeSynch(r);
      }
      c = getUtcTimeSynchronizationRecipients().getProperties();
      while (c.next(BBacnetRecipient.class))
      {
        r = (BBacnetRecipient)c.get();
        if (r.isDevice() && (!r.getDevice().isValid() || (DeviceRegistry.getDeviceAddress(r.getDevice()) == null)))
        {
          continue;
        }
        client.utcTimeSynch(r);
      }
    }
    catch (BacnetException e)
    {
      log.log(Level.WARNING, "BacnetException sending time synch {" + e + "}", e);
    }
  }

  public void doSendRestartNotifications()
  {
    SlotCursor<Property> c = getRestartNotificationRecipients().getProperties();
    BBacnetClientLayer client = ((BBacnetStack)network().getBacnetComm()).getClient();

    while (c.next(BBacnetRecipient.class))
    {
      BBacnetRecipient r = (BBacnetRecipient)c.get();
      if (r.isDevice() && (!r.getDevice().isValid() || (DeviceRegistry.getDeviceAddress(r.getDevice()) == null)))
      {
        continue;
      }
      try
      {
        client.deviceRestartNotification(r);
      }
      catch (BacnetException e)
      {
        log.log(Level.WARNING, "BacnetException sending restart notification {" + e + "}", e);
      }
    }
  }

  public void doChangeDeviceUuid(BUuid uuid)
  {
    if (BUuid.NULL.equals(uuid))
    {
      throw new LocalizableRuntimeException("bacnet", "localBacnetDevice.changeDeviceUuid.newValueNull");
    }

    if (hasEnabledScPort())
    {
      throw new LocalizableRuntimeException("bacnet", "localBacnetDevice.changeDeviceUuid.scPortEnabled");
    }

    setDeviceUuid(uuid);
  }

  private static boolean hasEnabledScPort()
  {
    BBacnetStack comm = (BBacnetStack) BBacnetNetwork.bacnet().getBacnetComm();
    BNetworkPort[] ports = comm.getNetwork().getChildren(BNetworkPort.class);
    for (BNetworkPort port : ports)
    {
      if (port.getLink() instanceof BScLinkLayer && port.getEnabled())
      {
        return true;
      }
    }

    return false;
  }

////////////////////////////////////////////////////////////////
// Device Address Binding
////////////////////////////////////////////////////////////////

  /**
   * Add an address binding.
   * The network number of the address must be zero if this is a locally
   * connected device, according to the Bacnet specification.
   *
   * @param device
   * SSPC135-2001, Section 12.10.33.
   */
  public final void addAddressBinding(BBacnetDevice device)
  {
    BBacnetAddress address = (BBacnetAddress)device.getAddress().newCopy();
    BBacnetNetworkLayer net = ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getNetwork();

    // per 135-2004-d-9, this can probably be removed.
    if (net.isDirectlyConnectedNetwork(address.getNetworkNumber()))
    {
      address.setNetworkNumber(0);
    }

    BBacnetAddressBinding b = bindingById(device.getObjectId());
    if (b == null)
    {
      b = new BBacnetAddressBinding(device.getObjectId(), address);
      getDeviceAddressBinding().addListElement(b, null);
    }
    else
    {
      b.getDeviceAddress().copyFrom(address);
    }
    Flags.setAllReadonly(getDeviceAddressBinding(), true, null);
  }

  public final void removeAddressBinding(BBacnetDevice device)
  {
    BBacnetAddressBinding b = bindingById(device.getObjectId());
    if (b != null)
    {
      getDeviceAddressBinding().removeListElement(b, null);
    }
  }

  public final void updateAddressBinding(BBacnetObjectIdentifier oldId, BBacnetObjectIdentifier newId)
  {
    BBacnetAddressBinding b = bindingById(oldId);
    if (b != null)
    {
      b.setDeviceObjectId(newId);
    }
  }

  public final void updateAddressBinding(BBacnetAddress oldAddress, BBacnetAddress newAddress)
  {
    BBacnetAddress address = (BBacnetAddress)oldAddress.newCopy();
    BBacnetNetworkLayer net = ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getNetwork();

    // per 135-2004-d-9, this can probably be removed.
    if (net.isDirectlyConnectedNetwork(address.getNetworkNumber()))
    {
      address.setNetworkNumber(0);
    }

    BBacnetAddressBinding b = bindingByAddress(address);
    if (b != null)
    {
      b.getDeviceAddress().copyFrom(newAddress);
    }
  }

  private BBacnetAddressBinding bindingById(BBacnetObjectIdentifier id)
  {
    SlotCursor<Property> sc = getDeviceAddressBinding().getProperties();
    while (sc.next(BBacnetAddressBinding.class))
    {
      BBacnetAddressBinding b = (BBacnetAddressBinding)sc.get();
      if (b.getDeviceObjectId().equals(id))
      {
        return b;
      }
    }

    return null;
  }

  private BBacnetAddressBinding bindingByAddress(BBacnetAddress address)
  {
    if (address == null)
    {
      return null;
    }
    byte[] mac = address.getMacAddress().getBytes();
    SlotCursor<Property> sc = getDeviceAddressBinding().getProperties();
    while (sc.next(BBacnetAddressBinding.class))
    {
      BBacnetAddressBinding b = (BBacnetAddressBinding)sc.get();
      if (b.getDeviceAddress().macEquals(mac))
      {
        return b;
      }
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// Bacnet Request Execution
////////////////////////////////////////////////////////////////

  /**
   * Get the value of a property.
   *
   * @param ref the PropertyReference containing id and index.
   * @return a PropertyValue containing the encoded value or the error.
   */
  @Override
  public final PropertyValue readProperty(PropertyReference ref)
    throws RejectException
  {
    return readProperty(ref.getPropertyId(), ref.getPropertyArrayIndex());
  }

  /**
   * Read the value of multiple Bacnet properties.
   *
   * @param refs the list of property references.
   * @return an array of PropertyValues.
   */
  @Override
  public final PropertyValue[] readPropertyMultiple(PropertyReference[] refs)
    throws RejectException
  {
    PropertyValue[] readResults = new PropertyValue[0];
    ArrayList<PropertyValue> results = new ArrayList<>(refs.length);
    for (int i = 0; i < refs.length; i++)
    {
      int[] props;
      switch (refs[i].getPropertyId())
      {
        case BBacnetPropertyIdentifier.ALL:
          props = REQUIRED_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            if (checkPropertyForReadMultiple(props[j]))
            {
              results.add(readProperty(props[j]));
            }
          }
          props = getOptionalProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j]));
          }
          break;

        case BBacnetPropertyIdentifier.OPTIONAL:
          props = getOptionalProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j]));
          }
          break;

        case BBacnetPropertyIdentifier.REQUIRED:
          props = REQUIRED_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            if (checkPropertyForReadMultiple(props[j]))
            {
              results.add(readProperty(props[j]));
            }
          }
          break;

        default:
          results.add(readProperty(refs[i].getPropertyId(),
                                   refs[i].getPropertyArrayIndex()));
          break;
      }
    }

    return results.toArray(readResults);
  }

  /**
   * This method will check if a property should be added as part of response to a
   * readPropertyMultiple request.
   * @param property_id
   * @return boolean
   */
  private static boolean checkPropertyForReadMultiple(int property_id)
  {
    if (property_id == BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return false;
    }
    return true;
  }

  /**
   * Read the specified range of values of a compound property.
   *
   * @param rangeReference the range reference describing the requested range.
   * @return a byte array containing the encoded range.
   */
  @Override
  public final RangeData readRange(RangeReference rangeReference)
    throws RejectException
  {
    int pId = rangeReference.getPropertyId();
    if (rangeReference.getPropertyArrayIndex() >= 0)
    {
      if (!isArray(pId))
      {
        return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }
    switch (pId)
    {
      case BBacnetPropertyIdentifier.DEVICE_ADDRESS_BINDING:
        BBacnetAddressBinding[] addrList = getDeviceAddressBinding().getChildren(BBacnetAddressBinding.class);
        return readRange(rangeReference, addrList, BBacnetAddressBinding.MAX_ENCODED_SIZE);

      case BBacnetPropertyIdentifier.ACTIVE_COV_SUBSCRIPTIONS:
        BOrd[] covOrdList = getActiveCovSubscriptions().getChildren(BOrd.class);
        BBacnetCovSubscription[] covList = new BBacnetCovSubscription[covOrdList.length];
        int j = 0;
        try
        {
          for (j = 0; j < covOrdList.length; j++)
          {
            covList[j] = (BBacnetCovSubscription)covOrdList[j].get(this);
          }
          return readRange(rangeReference, covList, BBacnetCovSubscription.MAX_ENCODED_SIZE);
        }
        catch (Exception e)
        {
          log.warning("Exception building Active_COV_Subscriptions[" + j + "] for ReadRange: " + e);
          return new ReadRangeAck(BBacnetErrorClass.DEVICE,
                                  BBacnetErrorCode.OPERATIONAL_PROBLEM);
        }

      case BBacnetPropertyIdentifier.RESTART_NOTIFICATION_RECIPIENTS:
        SlotCursor<Property> rnC = getRestartNotificationRecipients().getProperties();
        ArrayList<BBacnetRecipient> rstPropsList = new ArrayList<>();
        int m = 0;
        try
        {
          while (rnC.next(BBacnetRecipient.class))
          {
            rstPropsList.add((BBacnetRecipient)rnC.get());
          }
          BBacnetRecipient[] rstNotiList = new BBacnetRecipient[rstPropsList.size()];
          for (m = 0; m < rstPropsList.size(); m++)
          {
            rstNotiList[m] = rstPropsList.get(m);
          }
          return readRange(rangeReference, rstNotiList, BBacnetCovSubscription.MAX_ENCODED_SIZE);
        }
        catch (Exception e)
        {
          log.warning("Exception building RESTART_NOTIFICATION_RECIPIENTS[" + m + "] for ReadRange: " + e);
          return new ReadRangeAck(BBacnetErrorClass.DEVICE,
                                  BBacnetErrorCode.OPERATIONAL_PROBLEM);
        }

      case BBacnetPropertyIdentifier.TIME_SYNCHRONIZATION_RECIPIENTS:
        SlotCursor<Property> tsC = getTimeSynchronizationRecipients().getProperties();
        ArrayList<BBacnetRecipient> timeSyncOrdList = new ArrayList<>();
        int k = 0;
        try
        {
          while (tsC.next(BBacnetRecipient.class))
          {
            timeSyncOrdList.add((BBacnetRecipient)tsC.get());
          }
          BBacnetRecipient[] timeSyncList = new  BBacnetRecipient[timeSyncOrdList.size()];
          for (k = 0; k < timeSyncOrdList.size(); k++)
          {
            timeSyncList[k] = timeSyncOrdList.get(k);
          }
          return readRange(rangeReference, timeSyncList, BBacnetCovSubscription.MAX_ENCODED_SIZE);
        }
        catch (Exception e)
        {
          log.warning("Exception building TIME_SYNCHRONIZATION_RECIPIENTS[" + k + "] for ReadRange: " + e);
          return new ReadRangeAck(BBacnetErrorClass.DEVICE,
                                  BBacnetErrorCode.OPERATIONAL_PROBLEM);
        }

      case BBacnetPropertyIdentifier.UTC_TIME_SYNCHRONIZATION_RECIPIENTS:
        SlotCursor<Property> utsC = getUtcTimeSynchronizationRecipients().getProperties();
        ArrayList<BBacnetRecipient> utcTimeSyncOrdList = new ArrayList<>();
        int l = 0;
        try
        {
          while (utsC.next(BBacnetRecipient.class))
          {
            utcTimeSyncOrdList.add((BBacnetRecipient)utsC.get());
          }
          BBacnetRecipient[] utcTimeSyncList = new  BBacnetRecipient[utcTimeSyncOrdList.size()];
          for (l = 0; l < utcTimeSyncOrdList.size(); l++)
          {
            utcTimeSyncList[l] = utcTimeSyncOrdList.get(l);
          }
          return readRange(rangeReference, utcTimeSyncList, BBacnetCovSubscription.MAX_ENCODED_SIZE);
        }
        catch (Exception e)
        {
          log.warning("Exception building UTC_TIME_SYNCHRONIZATION_RECIPIENTS[" + l + "] for ReadRange: " + e);
          return new ReadRangeAck(BBacnetErrorClass.DEVICE,
                                  BBacnetErrorCode.OPERATIONAL_PROBLEM);
        }

      default:
        for (int i = 0; i < REQUIRED_PROPS.length; i++)
        {
          if (pId == REQUIRED_PROPS[i])
          {
            return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                    BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
          }
        }
        int[] props = getOptionalProps();
        for (int i = 0; i < props.length; i++)
        {
          if (pId == props[i])
          {
            return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                    BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
          }
        }

        return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.UNKNOWN_PROPERTY);
    }
  }

  /**
   * Set the value of a property.
   *
   * @param val the PropertyValue containing the write information.
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  @Override
  public final ErrorType writeProperty(PropertyValue val)
    throws BacnetException
  {
    return writeProperty(val.getPropertyId(),
                         val.getPropertyArrayIndex(),
                         val.getPropertyValue(),
                         val.getPriority());
  }

  /**
   * Add list elements.
   *
   * @param val the PropertyValue containing the propertyId,
   *            propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to add any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError addListElements(PropertyValue val)
    throws BacnetException
  {
    return addListElements(val.getPropertyId(), val.getPropertyArrayIndex(), val.getPropertyValue());
  }

  /**
   * Remove list elements.
   *
   * @param val the PropertyValue containing the propertyId,
   *            propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to remove any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError removeListElements(PropertyValue val)
    throws BacnetException
  {
    return removeListElements(val.getPropertyId(), val.getPropertyArrayIndex(), val.getPropertyValue());
  }

////////////////////////////////////////////////////////////////
// Bacnet Support
////////////////////////////////////////////////////////////////

  /**
   * Convenience method to compute the total time in milliseconds
   * that a device has to respond to request before a tranactions
   * cycle will be considered failed.
   *
   * @return milliseconds for: adpuTimeout + (apduTimeout * retries)
   */
  public int getDeviceTimeout()
  {
    return maxWaitTime;
  }

  /**
   * Is the property referenced by this propertyId an array property?
   *
   * @param propertyId
   * @return true if it is an array property, false if not or if the
   * propertyId does not refer to a property in this object.
   */
  boolean isArray(int propertyId)
  {
    if (propertyId == BBacnetPropertyIdentifier.OBJECT_LIST)
    {
      return true;
    }
    if (propertyId == BBacnetPropertyIdentifier.CONFIGURATION_FILES)
    {
      return true;
    }
    if (propertyId == BBacnetPropertyIdentifier.SLAVE_PROXY_ENABLE)
    {
      return true;
    }
    if (propertyId == BBacnetPropertyIdentifier.AUTO_SLAVE_DISCOVERY)
    {
      return true;
    }
    if (propertyId == BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return true;
    }

    return false;
  }

  /**
   * Read the value of a property.
   * Allows easier readPropertyMultiple access.
   *
   * @param pId the requested property-identifier.
   * @return a PropertyValue containing the encoded value or the error.
   */
  private PropertyValue readProperty(int pId)
  {
    return readProperty(pId, NOT_USED);
  }

  /**
   * Read the value of a property.
   * Allows easier readPropertyMultiple access.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing the encoded value or the error.
   */
  protected PropertyValue readProperty(int pId, int ndx)
  {
    // Check for array index on non-array property.
    if (ndx >= 0)
    {
      if (!isArray(pId))
      {
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY));
      }
    }

    synchronized (asnOut)
    {
      asnOut.reset();
      try
      {
        switch (pId)
        {
          case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
            asnOut.writeObjectIdentifier(getObjectId());
            break;

          case BBacnetPropertyIdentifier.OBJECT_NAME:
            asnOut.writeCharacterString(getObjectName(), getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.OBJECT_TYPE:
            asnOut.writeEnumerated(getObjectId().getObjectType());
            break;

          case BBacnetPropertyIdentifier.PROPERTY_LIST:
            return readPropertyList(ndx);

          case BBacnetPropertyIdentifier.SYSTEM_STATUS:
            asnOut.writeEnumerated(getSystemStatus().getOrdinal());
            break;

          case BBacnetPropertyIdentifier.VENDOR_NAME:
            asnOut.writeCharacterString(getVendorName(), getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.VENDOR_IDENTIFIER:
            asnOut.writeUnsignedInteger(getVendorId());
            break;

          case BBacnetPropertyIdentifier.SERIAL_NUMBER:
            asnOut.writeCharacterString(SERIAL_NUMBER, getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.MODEL_NAME:
            asnOut.writeCharacterString(getModelName(), getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.FIRMWARE_REVISION:
            asnOut.writeCharacterString(getFirmwareRevision(), getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.APPLICATION_SOFTWARE_VERSION:
            asnOut.writeCharacterString(getApplicationSoftwareVersion(), getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.LOCATION:
            asnOut.writeCharacterString(getLocation(), getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.DESCRIPTION:
            asnOut.writeCharacterString(getDescription(), getCharacterSet());
            break;

          case BBacnetPropertyIdentifier.PROTOCOL_VERSION:
            asnOut.writeUnsignedInteger(getProtocolVersion());
            break;

          case BBacnetPropertyIdentifier.PROTOCOL_REVISION:
            asnOut.writeUnsignedInteger(getProtocolRevision());
            break;

          case BBacnetPropertyIdentifier.PROTOCOL_SERVICES_SUPPORTED:
            asnOut.writeBitString(getProtocolServicesSupported());
            break;

          case BBacnetPropertyIdentifier.PROTOCOL_OBJECT_TYPES_SUPPORTED:
            asnOut.writeBitString(getProtocolObjectTypesSupported());
            break;

          case BBacnetPropertyIdentifier.OBJECT_LIST:
            //  Object list is a array of object id's.
            //  Per Bacnet, if the property array index
            //  is zero, return the size of the array.
            //  If the property array index is not present,
            //  return all elements in the array.  Otherwise,
            //  return the specified element.
            //  Note that the local device's objectId is always
            //  included as the first element in the list.
            if (ndx == 0)
            {
              int size = exports().getSize();
              asnOut.writeUnsignedInteger(size + 1);  // include ourself
            }
            else if (ndx == NOT_USED)
            {
              asnOut.writeObjectIdentifier(getObjectId());
              exports().writeObjectIds(asnOut);
            }
            else if (ndx == 1)
            {
              asnOut.writeObjectIdentifier(getObjectId());
            }
            else
            {
              asnOut.writeObjectIdentifier(exports().getEntry(ndx - 2));
            }
            break;

          case BBacnetPropertyIdentifier.MAX_APDU_LENGTH_ACCEPTED:
            asnOut.writeUnsignedInteger(getMaxAPDULengthAccepted());
            break;

          case BBacnetPropertyIdentifier.SEGMENTATION_SUPPORTED:
            asnOut.writeEnumerated(getSegmentationSupported().getOrdinal());
            break;

          case BBacnetPropertyIdentifier.MAX_SEGMENTS_ACCEPTED:
            int num = getMaxSegmentsAccepted();
            if (num < 0)
            {
              num = 100;
            }
            asnOut.writeUnsignedInteger(num);
            break;

          case BBacnetPropertyIdentifier.LOCAL_TIME:
            BAbsTime t = BAbsTime.make();
            asnOut.writeTime(t.getHour(), t.getMinute(), t.getSecond(), t.getMillisecond() / 10);
            break;

          case BBacnetPropertyIdentifier.LOCAL_DATE:
            BAbsTime t0 = BAbsTime.make();
            int wd = t0.getWeekday().getOrdinal();
            if (wd == NIAGARA_SUNDAY)
            {
              wd = BAC_SUNDAY;
            }
            asnOut.writeDate(t0.getYear() - 1900, t0.getMonth().getOrdinal() + 1, t0.getDay(), wd);
            break;

          case BBacnetPropertyIdentifier.UTC_OFFSET:
            int niagaraMillis = BTimeZone.getLocal().getUtcOffset();
            int off = (int)(-niagaraMillis / BRelTime.MILLIS_IN_MINUTE);
            asnOut.writeSignedInteger(off);
            break;

          case BBacnetPropertyIdentifier.DAYLIGHT_SAVINGS_STATUS:
            asnOut.writeBoolean(BAbsTime.make().inDaylightTime());
            break;

          case BBacnetPropertyIdentifier.APDU_SEGMENT_TIMEOUT:
            asnOut.writeUnsignedInteger(getApduSegmentTimeout());
            break;

          case BBacnetPropertyIdentifier.APDU_TIMEOUT:
            asnOut.writeUnsignedInteger(getApduTimeout());
            break;

          case BBacnetPropertyIdentifier.NUMBER_OF_APDU_RETRIES:
            asnOut.writeUnsignedInteger(getNumberOfApduRetries());
            break;

          case BBacnetPropertyIdentifier.TIME_SYNCHRONIZATION_RECIPIENTS:
            getTimeSynchronizationRecipients().writeAsn(asnOut);
            break;

          case BBacnetPropertyIdentifier.MAX_MASTER:
            BBacnetNetworkLayer net = ((BBacnetStack)((BBacnetNetwork)getParent()).getBacnetComm()).getNetwork();
            SlotCursor<Property> sc = net.loadSlots().getProperties();
            while (sc.next(BNetworkPort.class))
            {
              if (((BNetworkPort)sc.get()).getLink() instanceof BBacnetMstpLinkLayer)
              {
                asnOut.writeUnsignedInteger(((BBacnetMstpLinkLayer)((BNetworkPort)sc.get()).getLink()).getMaxMaster());
                break;
              }
            }
            if (asnOut.size() == 0)
            {
              // didn't find any MSTP ports
              asnOut.writeUnsignedInteger(127);
            }
            break;

          case BBacnetPropertyIdentifier.MAX_INFO_FRAMES:
            net = ((BBacnetStack)((BBacnetNetwork)getParent()).getBacnetComm()).getNetwork();
            sc = net.loadSlots().getProperties();
            while (sc.next(BNetworkPort.class))
            {
              if (((BNetworkPort)sc.get()).getLink() instanceof BBacnetMstpLinkLayer)
              {
                asnOut.writeUnsignedInteger(((BBacnetMstpLinkLayer)((BNetworkPort)sc.get()).getLink()).getMaxInfoFrames());
                break;
              }
            }
            if (asnOut.size() == 0)
            {
              // didn't find any MSTP ports
              asnOut.writeUnsignedInteger(1);
            }
            break;

          case BBacnetPropertyIdentifier.DEVICE_ADDRESS_BINDING:
            getDeviceAddressBinding().writeAsn(asnOut);
            break;

          case BBacnetPropertyIdentifier.DATABASE_REVISION:
            asnOut.writeUnsignedInteger(getDatabaseRevision());
            break;

          case BBacnetPropertyIdentifier.CONFIGURATION_FILES:
            if (ndx == 0)
            {
              int size = getConfigurationFiles().getSize();
              asnOut.writeUnsignedInteger(size);
            }
            else if (ndx == NOT_USED)
            {
              getConfigurationFiles().writeAsn(asnOut);
            }
            else
            {
              BBacnetObjectIdentifier id = (BBacnetObjectIdentifier)getConfigurationFiles().getElement(ndx);
              if (id != null)
              {
                asnOut.writeObjectIdentifier(id);
              }
              else
              {
                return new NReadPropertyResult(pId, ndx,
                                               new NErrorType(BBacnetErrorClass.PROPERTY,
                                                              BBacnetErrorCode.INVALID_ARRAY_INDEX));
              }
            }
            break;

          case BBacnetPropertyIdentifier.LAST_RESTORE_TIME:
            getLastRestoreTime().writeAsn(asnOut);
            break;

          case BBacnetPropertyIdentifier.BACKUP_FAILURE_TIMEOUT:
            asnOut.writeUnsignedInteger(getBackupFailureTimeout().getMillis() / BRelTime.MILLIS_IN_SECOND);
            break;

          case BBacnetPropertyIdentifier.ACTIVE_COV_SUBSCRIPTIONS:
            getActiveCovSubscriptions().writeAsn(asnOut);
            break;

          case BBacnetPropertyIdentifier.UTC_TIME_SYNCHRONIZATION_RECIPIENTS:
            getUtcTimeSynchronizationRecipients().writeAsn(asnOut);
            break;

          case BBacnetPropertyIdentifier.TIME_SYNCHRONIZATION_INTERVAL:
            asnOut.writeUnsignedInteger(getTimeSynchronizationInterval().getMillis() / BRelTime.MILLIS_IN_MINUTE);
            break;

          case BBacnetPropertyIdentifier.ALIGN_INTERVALS:
            asnOut.writeBoolean(getAlignIntervals());
            break;

          case BBacnetPropertyIdentifier.INTERVAL_OFFSET:
            asnOut.writeUnsignedInteger(getIntervalOffset());
            break;

          case BBacnetPropertyIdentifier.BACKUP_AND_RESTORE_STATE:
            asnOut.writeEnumerated(getBackupAndRestoreState().getOrdinal());
            break;

          case BBacnetPropertyIdentifier.BACKUP_PREPARATION_TIME:
            asnOut.writeUnsignedInteger(getBackupPreparationTime().getSeconds());
            break;

          case BBacnetPropertyIdentifier.RESTORE_COMPLETION_TIME:
            asnOut.writeUnsignedInteger(getRestoreCompletionTime().getSeconds());
            break;

          case BBacnetPropertyIdentifier.RESTORE_PREPARATION_TIME:
            asnOut.writeUnsignedInteger(getRestorePreparationTime().getSeconds());
            break;

          case BBacnetPropertyIdentifier.LAST_RESTART_REASON:
            asnOut.writeEnumerated(getLastRestartReason().getOrdinal());
            break;

          case BBacnetPropertyIdentifier.TIME_OF_DEVICE_RESTART:
            getTimeOfDeviceRestart().writeAsn(asnOut);
            break;

          case BBacnetPropertyIdentifier.RESTART_NOTIFICATION_RECIPIENTS:
            getRestartNotificationRecipients().writeAsn(asnOut);
            break;

          case BBacnetPropertyIdentifier.DEVICE_UUID:
            // BACnet/SC is defined to be protocol revision agnostic but requires a device UUID. See
            // Clause YY.1.5.3. However, the new Device_UUID property can only appear in the Device
            // object of devices with the Protocol Revision of this addendum or higher. Before this
            // protocol revision, the device UUID for BACnet/SC is not represented in the Device
            // object and is configured by some other means.

          default:
            return new NReadPropertyResult(pId, ndx,
                                           new NErrorType(BBacnetErrorClass.PROPERTY,
                                                          BBacnetErrorCode.UNKNOWN_PROPERTY));
        }
      }
      catch (IndexOutOfBoundsException e)
      {
        return new NReadPropertyResult(pId, ndx,
                                       new NErrorType(BBacnetErrorClass.PROPERTY,
                                                      BBacnetErrorCode.INVALID_ARRAY_INDEX));
      }

      return new NReadPropertyResult(pId, ndx, asnOut.toByteArray());
    }
  }

  /**
   * Execute the ReadRange for the given class and maximum encoded size
   * of the data type.
   */
  protected RangeData readRange(RangeReference ref, Object[] list, int maxEncodedSize)
  {
    int rangeType = ref.getRangeType();
    int len = list.length;
    boolean[] rflags = new boolean[] { false, false, false };

    // Calculate the maximum allowed data length.
    int maxDataLength = -1;
    if (ref instanceof BacnetConfirmedRequest)
    {
      maxDataLength = ((BacnetConfirmedRequest) ref).getMaxDataLength()
        // We need to subtract the size of the ReadRangeAck application headers.
        - ReadRangeAck.READ_RANGE_ACK_MAX_APP_HEADER_SIZE
        // We also add back in the length of the unused fields.
        + 3 // we don't use propertyArrayIndex here
        + 5; // we don't use sequenceNumber here
    }

    if (rangeType == RangeReference.BY_POSITION)
    {
      int refNdx = (int)ref.getReferenceIndex();
      int count = ref.getCount();

      // sanity check on refNdx - should we throw an error/reject here?
      if ((refNdx > len) || (refNdx < 1))
      {
        return new ReadRangeAck(getObjectId(),
                                ref.getPropertyId(),
                                NOT_USED,
                                BBacnetBitString.emptyBitString(3),
                                0,
                                new byte[0]);
      }

      Array<Object> a = new Array<>(Object.class);
      int itemsFound = 0;

      if (count > 0)
      {
        // Count is positive: Search from refNdx to end,
        // until we find (count) items.
        for (int i = refNdx - 1; i < len && itemsFound < count; i++)
        {
          a.add(list[i]);
          itemsFound++;
        }

        // Set firstItem result flag.
        if (refNdx == 1)
        {
          rflags[0] = true;
        }
        // Set lastItem flag temporarily - adjust later if needed.
        if ((refNdx + count - 1) >= len)
        {
          rflags[1] = true;
        }
      }

      else if (count < 0)
      {
        // Count is negative: Search from refNdx to beginning,
        // until we find (-count) items.
        count = -count;
        for (int i = refNdx - 1; i >= 0 && itemsFound < count; i--)
        {
          a.add(list[i]);
          itemsFound++;
        }

        // Reverse the array because we need to return the items
        // in their natural order.
        a = a.reverse();

        // Set firstItem result flag.
        if ((refNdx - count) <= 0)
        {
          rflags[0] = true;
        }
        // Set lastItem flag temporarily - adjust later if needed.
        if (refNdx == len)
        {
          rflags[1] = true;
        }
      }
      else
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.INCONSISTENT_PARAMETERS);
      }

      // Iterate through the found items until we have written
      // them all, or until we don't have any more room in the
      // outgoing packet.
      Iterator<Object> it = a.iterator();
      int itemCount = 0;

      synchronized (asnOut)
      {
        asnOut.reset();
        if (maxDataLength > 0)
        {
          while (it.hasNext())
          {
            if ((maxDataLength - asnOut.size()) < maxEncodedSize)
            {
              rflags[1] = false;
              break;
            }
            ((BIBacnetDataType)it.next()).writeAsn(asnOut);
            itemCount++;
          }
        }
        else
        {
          itemCount = itemsFound;
          while (it.hasNext())
          {
            ((BIBacnetDataType) it.next()).writeAsn(asnOut);
          }
        }

        // Set the moreItems result flag.
        if (itemCount < itemsFound)
        {
          rflags[2] = true;
        }

        // Return the ack.
        return new ReadRangeAck(getObjectId(),
                                ref.getPropertyId(),
                                NOT_USED,
                                BBacnetBitString.make(rflags),
                                itemCount,
                                asnOut.toByteArray());
      }
    }
    else if (rangeType == NOT_USED)
    {
      rflags[0] = false;
      int itemCount = 0;
      synchronized (asnOut)
      {
        asnOut.reset();

        if (maxDataLength > 0)
        {
          for (int i = 0; i < len; i++)
          {
            ((BIBacnetDataType)list[i]).writeAsn(asnOut);
            itemCount++;
            if ((maxDataLength - asnOut.size()) < maxEncodedSize)
            {
              break;
            }
          }
          if (itemCount > 0)
          {
            rflags[0] = true;
          }
          if (itemCount > 0 && itemCount == len)
          {
            rflags[1] = true;
          }
        }
        else
        {
          itemCount = len;
          for (int i = 0; i < len; i++)
          {
            ((BIBacnetDataType) list[i]).writeAsn(asnOut);
          }
          if (itemCount > 0)
          {
            rflags[0] = true;
          }
          if (itemCount > 0 && itemCount == len)
          {
            rflags[1] = true;
          }
        }

        // Set the moreItems result flag.
        if (itemCount < len)
        {
          rflags[2] = true;
        }

        // Return the ack.
        return new ReadRangeAck(getObjectId(),
                                ref.getPropertyId(),
                                NOT_USED,
                                BBacnetBitString.make(rflags),
                                itemCount,
                                asnOut.toByteArray());
      }
    }
    else
    {
      return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                              BBacnetErrorCode.INCONSISTENT_PARAMETERS);
    }
  }

  /**
   * Set the value of a property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @param pri the priority level (only used for commandable properties).
   * @throws BacnetException if something is wrong from a BACnet perspective.
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    // Check for array index on non-array property.
    if (ndx >= 0)
    {
      if (!isArray(pId))
      {
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }

    try
    {
      synchronized (asnIn)
      {
        long lval = 0L;
        switch (pId)
        {
          case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
            if (allowObjectIdWrite)
            {
              set(objectId, AsnUtil.fromAsnObjectId(val), getBacnetContext());
              return null;
            }
            else
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                BBacnetErrorCode.WRITE_ACCESS_DENIED);
            }

          case BBacnetPropertyIdentifier.LOCATION:
            setString(location,
                      AsnUtil.fromAsnCharacterString(val),
                      getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.DESCRIPTION:
            setString(description,
                      AsnUtil.fromAsnCharacterString(val),
                      getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.TIME_SYNCHRONIZATION_RECIPIENTS:
            asnIn.setBuffer(val);
            BBacnetListOf tsRecips = (BBacnetListOf)getTimeSynchronizationRecipients().newCopy();
            tsRecips.readAsn(asnIn);
            set(timeSynchronizationRecipients, tsRecips, getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.RESTART_NOTIFICATION_RECIPIENTS:
            asnIn.setBuffer(val);
            BBacnetListOf rsRecips = (BBacnetListOf)getRestartNotificationRecipients().newCopy();
            rsRecips.readAsn(asnIn);
            set(restartNotificationRecipients, rsRecips, getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.BACKUP_FAILURE_TIMEOUT:
            lval = AsnUtil.fromAsnUnsignedInteger(val);
            if (lval > BBacnetUnsigned.MAX_UNSIGNED16_VALUE)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            set(backupFailureTimeout,
                BRelTime.make(BRelTime.MILLIS_IN_SECOND * lval),
                getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.UTC_TIME_SYNCHRONIZATION_RECIPIENTS:
            asnIn.setBuffer(val);
            BBacnetListOf utcTsRecips = (BBacnetListOf)getUtcTimeSynchronizationRecipients().newCopy();
            utcTsRecips.readAsn(asnIn);
            set(utcTimeSynchronizationRecipients, utcTsRecips, getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.TIME_SYNCHRONIZATION_INTERVAL:
            BBacnetUnsigned unsigned = AsnUtil.fromAsnUnsigned(val);
            long timeSynchIntervalMinutes = unsigned.getUnsigned();
            set(timeSynchronizationInterval,
                BRelTime.make(timeSynchIntervalMinutes * BRelTime.MILLIS_IN_MINUTE),
                getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.ALIGN_INTERVALS:
            boolean align = AsnUtil.fromAsnBoolean(val);
            setBoolean(alignIntervals,
                       align,
                       getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.INTERVAL_OFFSET:
            long offset = AsnUtil.fromAsnUnsignedInteger(val);
            BFacets f = getSlotFacets(intervalOffset);
            if ((offset > f.geti(BFacets.MAX, 1440)) ||
                (offset < f.geti(BFacets.MIN, 0)))
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            setInt(intervalOffset, (int)offset, getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.PROPERTY_LIST:
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.WRITE_ACCESS_DENIED);

          default:
            for (int i = 0; i < REQUIRED_PROPS.length; i++)
            {
              if (pId == REQUIRED_PROPS[i])
              {
                return new NErrorType(BBacnetErrorClass.PROPERTY,
                                      BBacnetErrorCode.WRITE_ACCESS_DENIED);
              }
            }
            int[] props = getOptionalProps();
            for (int i = 0; i < props.length; i++)
            {
              if (pId == props[i])
              {
                return new NErrorType(BBacnetErrorClass.PROPERTY,
                                      BBacnetErrorCode.WRITE_ACCESS_DENIED);
              }
            }
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.UNKNOWN_PROPERTY);
        }
      }
    }
    catch (AsnException e)
    {
      log.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

  /**
   * Add list elements.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @throws BacnetException if something is wrong from a BACnet perspective.
   * @return null if everything goes OK, or
   * a ChangeListError describing the error if not.
   */
  protected ChangeListError addListElements(int pId,
                                            int ndx,
                                            byte[] val)
    throws BacnetException
  {
    synchronized (asnIn)
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.DEVICE_ADDRESS_BINDING:
          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                      0);

        case BBacnetPropertyIdentifier.TIME_SYNCHRONIZATION_RECIPIENTS:
          // Check for array index on non-array property.
          if (ndx >= 0)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                        0);
          }
          return getTimeSynchronizationRecipients().addElements(val, getBacnetContext());

        case BBacnetPropertyIdentifier.UTC_TIME_SYNCHRONIZATION_RECIPIENTS:
          // Check for array index on non-array property.
          if (ndx >= 0)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                        0);
          }
          return this.getUtcTimeSynchronizationRecipients().addElements(val, getBacnetContext());

        case BBacnetPropertyIdentifier.RESTART_NOTIFICATION_RECIPIENTS:
          // Check for array index on non-array property.
          if (ndx >= 0)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                        0);
          }
          return this.getRestartNotificationRecipients().addElements(val, getBacnetContext());

        case BBacnetPropertyIdentifier.ACTIVE_COV_SUBSCRIPTIONS:
          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                      0);

        default:
          for (int i = 0; i < REQUIRED_PROPS.length; i++)
          {
            if (pId == REQUIRED_PROPS[i])
            {
              return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                          new NErrorType(BBacnetErrorClass.SERVICES,
                                                         BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                          0);
            }
          }
          int[] props = getOptionalProps();
          for (int i = 0; i < props.length; i++)
          {
            if (pId == props[i])
            {
              return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                          new NErrorType(BBacnetErrorClass.SERVICES,
                                                         BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                          0);
            }
          }

          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.UNKNOWN_PROPERTY),
                                      0);
      }
    }
  }

  /**
   * Remove list elements.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @throws BacnetException if something is wrong from a BACnet perspective.
   * @return null if everything goes OK, or
   * a ChangeListError describing the error if not.
   */
  protected ChangeListError removeListElements(int pId,
                                               int ndx,
                                               byte[] val)
    throws BacnetException
  {
    synchronized (asnIn)
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.DEVICE_ADDRESS_BINDING:
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                      0);

        case BBacnetPropertyIdentifier.TIME_SYNCHRONIZATION_RECIPIENTS:
          // Check for array index on non-array property.
          if (ndx >= 0)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                        0);
          }
          return getTimeSynchronizationRecipients().removeElements(val, getBacnetContext());

        case BBacnetPropertyIdentifier.UTC_TIME_SYNCHRONIZATION_RECIPIENTS:
          // Check for array index on non-array property.
          if (ndx >= 0)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                        0);
          }
          return getUtcTimeSynchronizationRecipients().removeElements(val, getBacnetContext());

        case BBacnetPropertyIdentifier.RESTART_NOTIFICATION_RECIPIENTS:
          // Check for array index on non-array property.
          if (ndx >= 0)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                        0);
          }
          return getRestartNotificationRecipients().removeElements(val, getBacnetContext());

        case BBacnetPropertyIdentifier.ACTIVE_COV_SUBSCRIPTIONS:
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                      0);

        default:
          for (int i = 0; i < REQUIRED_PROPS.length; i++)
          {
            if (pId == REQUIRED_PROPS[i])
            {
              return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                          new NErrorType(BBacnetErrorClass.SERVICES,
                                                         BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                          0);
            }
          }
          int[] props = getOptionalProps();
          for (int i = 0; i < props.length; i++)
          {
            if (pId == props[i])
            {
              return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                          new NErrorType(BBacnetErrorClass.SERVICES,
                                                         BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                          0);
            }
          }

          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.UNKNOWN_PROPERTY),
                                      0);
      }
    }
  }

  /**
   * Get all the optional properties for this object.
   *
   * @return the list as an array of BDiscretes.
   */
  private int[] getOptionalProps()
  {
    Vector<BBacnetPropertyIdentifier> v = new Vector<>();
    v.add(BBacnetPropertyIdentifier.location);
    v.add(BBacnetPropertyIdentifier.description);
    v.add(BBacnetPropertyIdentifier.maxSegmentsAccepted);
    v.add(BBacnetPropertyIdentifier.localTime);
    v.add(BBacnetPropertyIdentifier.localDate);
    v.add(BBacnetPropertyIdentifier.utcOffset);
    v.add(BBacnetPropertyIdentifier.serialNumber);
    v.add(BBacnetPropertyIdentifier.daylightSavingsStatus);
    v.add(BBacnetPropertyIdentifier.apduSegmentTimeout);
    v.add(BBacnetPropertyIdentifier.timeSynchronizationRecipients);
    v.add(BBacnetPropertyIdentifier.maxMaster);
    v.add(BBacnetPropertyIdentifier.maxInfoFrames);
    v.add(BBacnetPropertyIdentifier.utcTimeSynchronizationRecipients);
    v.add(BBacnetPropertyIdentifier.timeSynchronizationInterval);
    v.add(BBacnetPropertyIdentifier.alignIntervals);
    v.add(BBacnetPropertyIdentifier.intervalOffset);
    v.add(BBacnetPropertyIdentifier.lastRestartReason);
    v.add(BBacnetPropertyIdentifier.timeOfDeviceRestart);

    addOptionalProps(v);
    int[] optionalProps = new int[v.size()];
    for (int i = 0; i < optionalProps.length; i++)
    {
      optionalProps[i] = ((BEnum) v.elementAt(i)).getOrdinal();
    }
    return optionalProps;
  }

  /**
   * Override method to add optional properties.
   * NOTE: You MUST call super.addOptionalProps(v) first!
   *
   * @param v Vector containing optional propertyIds, as BEnums.
   */
  protected void addOptionalProps(Vector<BBacnetPropertyIdentifier> v)
  {
    v.add(BBacnetPropertyIdentifier.activeCovSubscriptions);
    v.add(BBacnetPropertyIdentifier.restartNotificationRecipients);
    v.add(BBacnetPropertyIdentifier.configurationFiles);
    v.add(BBacnetPropertyIdentifier.lastRestoreTime);
    v.add(BBacnetPropertyIdentifier.backupFailureTimeout);
    v.add(BBacnetPropertyIdentifier.backupPreparationTime);
    v.add(BBacnetPropertyIdentifier.restorePreparationTime);
    v.add(BBacnetPropertyIdentifier.restoreCompletionTime);
    v.add(BBacnetPropertyIdentifier.backupAndRestoreState);
  }

  /**
   * Get the parent <code>BBacnetNetwork</code>.
   */
  private BBacnetNetwork network()
  {
    return (BBacnetNetwork)getParent();
  }

  /**
   * Add a COV subscription for the given export object to the target component,
   * and add the list element to the local device's Active_COV_Subscriptions
   * BACnet property.  This API is used by BIBacnetCovSource objects when they
   * are adding a COV subscription.
   *
   * @param export the export which has been subscribed for COV.
   * @param src    the target component which must be subscribed in Niagara to receive notification of COVs.
   * @param p      the BacnetCovSubscription property that was added to the export object.
   */
  public void subscribeCov(BIBacnetCovSource export, BComponent src, Property p)
  {
    BBacnetCovSubscription cov = (BBacnetCovSubscription)((BComplex)export).get(p);
    if (cov.isCovProperty())
    {
      covPropPoller.subscribe(cov);
    }
    else
    {
      covSubscriber.subscribe(export, src);
    }
    BOrd covOrd = BOrd.make(((BComponent)export).getSlotPathOrd().toString() + "/" + p.getName());
    Property sub = getActiveCovSubscriptions().addListElement(covOrd, null);
    getActiveCovSubscriptions().setFlags(sub, Flags.READONLY);
  }

  /**
   * Remove a COV subscription for the given export object from the target component,
   * and remove the list element from the local device's Active_COV_Subscriptions
   * BACnet property.  This API is used by BIBacnetCovSource objects when they
   * are removing a COV subscription.
   *
   * @param export the export which has been unsubscribed for COV.
   * @param src    the target component which must be unsubscribed in Niagara.
   * @param p      the BacnetCovSubscription property that was removed to the export object.
   */
  public void unsubscribeCov(BIBacnetCovSource export, BComponent src, Property p)
  {
    BBacnetCovSubscription cov = (BBacnetCovSubscription)((BComplex)export).get(p);
    if (cov.isCovProperty())
    {
      covPropPoller.unsubscribe(cov);
    }
    else
    {
      Object[] children = ((BComponent)export).getChildren(BBacnetCovSubscription.class);
      // if no more cov subscription (expired or removed) on a object then,
      // remove from cov subscription list
      if (children.length <= 0)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("Removing cov subscription on " + export);
        }
        covSubscriber.unsubscribe(export, src);
      }
    }
    BOrd covOrd = BOrd.make(((BComponent)export).getSlotPathOrd().toString() + "/" + p.getName());
    getActiveCovSubscriptions().removeListElement(covOrd, null);
  }

  public void subscribe(BIBacnetExportObject export, Object src)
  {
    if (src instanceof BComponent)
    {
      objectSubscriber.subscribe(export, (BComponent)src);
    }
  }

  public void unsubscribe(BIBacnetExportObject export, Object src)
  {
    if (src instanceof BComponent)
    {
      objectSubscriber.unsubscribe(export, (BComponent)src);
    }
  }

  /**
   * Get the BACnet User context.
   * This context is used for determining access restrictions for
   * BACnet writes, and add/remove list elements, as well as any
   * BACnet actions that map to action invocations.
   *
   * @throws PermissionException if there is an error retrieiving
   *                             the user - inability to find the user service, or the
   *                             BACnet user does not exist.
   */
  public static Context getBacnetContext()
  {
    if (bacnetContext == null)
    {
      try
      {
        BUserService us = (BUserService)Sys.getService(BUserService.TYPE);
        BUser bacnetUser = us.getUser("BACnet");
        if (bacnetUser == null)
        {
          bacnetUser = (BUser) us.get(us.add("BACnet", new BUser()));
        }
        if (!bacnetUser.getEnabled())
        {
          throw new PermissionException("BACnet User not enabled");
        }
        bacnetContext = new BasicContext(bacnetUser);
      }
      catch (Exception e1)
      {
        log.log(Level.SEVERE, "Unable to retrieve BACnet user context", e1);
        throw new PermissionException("Error retrieving BACnet user context");
      }
    }
    return bacnetContext;
  }

  /**
   * Schedule the next and future time synchronization requests,
   * based on timeSynchronizationInterval, alignIntervals, and
   * intervalOffset.
   */
  private void scheduleTimeSynch()
  {
    synchronized (TIME_SYNC_LOCK)
    {
      if (tsTicket != null)
      {
        tsTicket.cancel();
      }

      BRelTime interval = getTimeSynchronizationInterval();
      long imillis = interval.getMillis();

      // No time synchs if the interval is 0.
      if (imillis == 0)
      {
        tsTicket = null;
        return;
      }

      BAbsTime now = BAbsTime.now();
      BAbsTime start = null;
      long nowMillis = now.getMillis();
      if (imillis > 0)
      {
        if (getAlignIntervals())
        {
          if ((BRelTime.MILLIS_IN_HOUR % imillis) == 0)
          {
            // Interval is an even factor of an hour
            //(int year, BMonth month, int day, int hour, int min)
            BAbsTime startOfHour = BAbsTime.make(now.getYear(),
                                                 now.getMonth(),
                                                 now.getDay(),
                                                 now.getHour(),
                                                0);

            long startOfHourMillis = startOfHour.getMillis();
            start = getNextInterval(startOfHourMillis, imillis, getIntervalOffset(), nowMillis);
          }
          else if ((BRelTime.MILLIS_IN_DAY % imillis) == 0)
          {
            // Interval is an even factor of a day
            BAbsTime startOfDay = BAbsTime.make(now.getYear(),
                                                now.getMonth(),
                                                now.getDay(),
                                                0,
                                                0);

            long startOfDayMillis = startOfDay.getMillis();
            start = getNextInterval(startOfDayMillis, imillis, getIntervalOffset(), nowMillis);
          }
          else
          {
            // interval is uneven, no alignment
            start = lastTSTime.add(interval);
            if (start.isBefore(now))
            {
              now.add(interval);
            }
          }
        }
        else
        {
          // no interval alignment
          start = lastTSTime.add(interval);
          if (start.isBefore(now))
          {
            now.add(interval);
          }
        }

        if (log.isLoggable(Level.FINE))
        {
          StringBuilder sb = new StringBuilder("BACnet Time Synchronization: every ");
          sb.append(interval.toString(BFacets.make(BFacets.SHOW_SECONDS, false)))
            .append(", beginning at ").append(start.toString(BFacets.make(BFacets.SHOW_SECONDS, false)))
            .append((getAlignIntervals() ? ": aligned" : ": unaligned"));
          if (getAlignIntervals())
          {
            sb.append(", offset:").append(getIntervalOffset()).append(" min");
          }
          log.fine(sb.toString());
        }

        // Begin the sequence
        tsTicket = Clock.schedulePeriodically(this, start, interval, sendTimeSynch, null);
      }
      else
      {
        log.fine("BACnet Time Synchronization disabled");
      }
    }
  }

  /*
   * Find the next time synchronization instance by starting at either the day or hour,
   * add interval amounts of time, until we pass the current time.
   */
  private static BAbsTime getNextInterval(long start, long interval, int offset, long now)
  {
    long offsetInterval = (offset * BRelTime.MILLIS_IN_MINUTE) % interval;
    long next = start + offsetInterval;
    do
    {
      // Add intervals until we get to the next one after now
      next += interval;
    }
    while (next < now);

    return BAbsTime.make(next);
  }

  private void checkRecipients(Property p)
  {
    SlotCursor<Property> c = ((BComplex)get(p)).getProperties();
    BBacnetRecipient r = null;
    while (c.next(BBacnetRecipient.class))
    {
      r = (BBacnetRecipient)c.get();
      if (r.isDevice())
      {
        BBacnetObjectIdentifier deviceId = r.getDevice();
        if (deviceId.isValid())
        {
          if (DeviceRegistry.getDeviceAddress(deviceId) == null)
          {
            try
            {
              ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient().whoIs(BBacnetAddress.GLOBAL_BROADCAST_ADDRESS,
                                                                                        deviceId.getInstanceNumber(),
                                                                                        deviceId.getInstanceNumber());
            }
            catch (BacnetException e)
            {
              log.log(Level.SEVERE, "Unable to determine address for Bacnet Time Synch Recipient " + deviceId, e);
            }
          }
        }
      }
    }
  }

  public void updateSystemStatus(BBacnetDeviceStatus newStatus)
  {
    preBackupRestoreStatus = getSystemStatus();
    setSystemStatus(newStatus);
  }

  public void restoreSystemStatus()
  {
    setSystemStatus(preBackupRestoreStatus);
  }

////////////////////////////////////////////////////////////////
// XML support
////////////////////////////////////////////////////////////////

  /**
   * Get a PropertyInfo object containing metadata about this property.
   *
   * @param objectType the Bacnet object type of the containing object.
   * @param propId     the property ID.
   * @return a PropertyInfo read from the manufacturer-specific XML file.
   */
  public PropertyInfo getPropertyInfo(int objectType, int propId)
  {
    PropertyInfo propInfo = ObjectTypeList.getInstance().getPropertyInfo(objectType, propId);

    // If nothing, just create an "unknown proprietary" PropertyInfo.
    if (propInfo == null)
    {
      propInfo = new PropertyInfo(BBacnetPropertyIdentifier.tag(propId), propId, BacnetConst.ASN_UNKNOWN_PROPRIETARY);
    }

    // Return what we have.
    return propInfo;
  }

////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("LocalBacnetDevice", 2);
    out.prop("fatalFault", fatalFault);
    out.prop("objectName", getObjectName());
    out.prop("preBackupRestoreStatus", preBackupRestoreStatus);
    out.prop("tsTicket", tsTicket);
    out.prop("lastTSTime", lastTSTime);
    out.trTitle("DeviceRegistry", 2);
    LongHashMap.Iterator it = DeviceRegistry.addressIterator();
    int i = 0;
    while (it.hasNext())
    {
      out.prop("  " + (i++), it.next());
    }
    out.prop("bacnetContext", bacnetContext);
    out.prop("COV subscription count", covSubscriber.getSubscriptionCount());
    covPropPoller.spy(out);
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Fatal Fault
////////////////////////////////////////////////////////////////

  private boolean fatalFault = false;

  /**
   * Is this component in a fatal fault condition?
   */
  @Override
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  private void checkFatalFault()
  {
    // short circuit if already in fatal fault
    if (fatalFault)
    {
      return;
    }

    // check network fatal fault
    if (network().isFatalFault())
    {
      fatalFault = true;
      setFaultCause("Network fault: " + network().getFaultCause());
      return;
    }

    // The local device is always exported, regardless of the license,
    // because Niagara is a BACnet device on the network and must
    // expose its Device object.
    // Individual exports check the license feature themselves.

    // no fatal faults
    setFaultCause("");
  }

////////////////////////////////////////////////////////////////
// Branding
////////////////////////////////////////////////////////////////

  /**
   * Read the OEM-branded properties file and set the values of the
   * vendor-specifiable fields.
   */
  private void readBrandProperties()
  {
    if (brandPropertiesRead)
    {
      return;
    }
    AccessController.doPrivileged((PrivilegedAction<Void>)() ->
    {
      InputStream is = null;
      try
      {
        BOrd ord = BOrd.make("file:!etc/brand.properties");
        BIFile brandFile = (BIFile)ord.resolve().get();
        is = brandFile.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line = br.readLine();
        while (line != null)
        {
          try
          {
            line = line.trim();

            if (line.startsWith("bacnetVendorId="))
            {
              int vid = 36;
              try
              {
                vid = Integer.parseInt(line.substring(15));
              }
              catch (Exception ignored)
              {}
              setVendorId(vid);
              setVendorName(BacnetVendorUtil.getVendorName(vid));
            }
            else if (line.startsWith("modelName="))
            {
              String mn = line.substring(10);
              objectName = mn;
              setModelName(mn);
            }
            else if (line.startsWith("applicationSoftwareVersion="))
            {
              String nAppSwVer = getType().getVendor() + " " + getType().getVendorVersion();
              setApplicationSoftwareVersion(line.substring(27) + " - BACnet: " + nAppSwVer);
            }
          }
          catch (Exception e)
          {
            log.warning("Error parsing BACnet device branding information line: " + line + " (" + e + ")");
          }
          line = br.readLine();
        }
      }
      catch (UnresolvedException ignored)
      {}
      catch (Exception e)
      {
        log.log(Level.SEVERE, "Error reading BACnet device branding information", e);
      }
      finally
      {
        try
        {
          if (is != null)
          {
            is.close();
          }
        }
        catch (Exception ignored)
        {}

        brandPropertiesRead = true;
      }

      return null;
    });
  }

  private volatile boolean brandPropertiesRead = false;

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("deviceLocal.png");
  private static final Lexicon lex = Lexicon.make("bacnet");

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final int[] REQUIRED_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
      BBacnetPropertyIdentifier.OBJECT_NAME,
      BBacnetPropertyIdentifier.OBJECT_TYPE,
      BBacnetPropertyIdentifier.SYSTEM_STATUS,
      BBacnetPropertyIdentifier.VENDOR_NAME,
      BBacnetPropertyIdentifier.VENDOR_IDENTIFIER,
      BBacnetPropertyIdentifier.MODEL_NAME,
      BBacnetPropertyIdentifier.FIRMWARE_REVISION,
      BBacnetPropertyIdentifier.APPLICATION_SOFTWARE_VERSION,
      BBacnetPropertyIdentifier.PROTOCOL_VERSION,
      BBacnetPropertyIdentifier.PROTOCOL_REVISION,
      BBacnetPropertyIdentifier.PROTOCOL_SERVICES_SUPPORTED,
      BBacnetPropertyIdentifier.PROTOCOL_OBJECT_TYPES_SUPPORTED,
      BBacnetPropertyIdentifier.OBJECT_LIST,
      BBacnetPropertyIdentifier.MAX_APDU_LENGTH_ACCEPTED,
      BBacnetPropertyIdentifier.SEGMENTATION_SUPPORTED,
      BBacnetPropertyIdentifier.APDU_TIMEOUT,
      BBacnetPropertyIdentifier.NUMBER_OF_APDU_RETRIES,
      BBacnetPropertyIdentifier.DEVICE_ADDRESS_BINDING,
      BBacnetPropertyIdentifier.DATABASE_REVISION,
    };

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, getOptionalProps());
  }

  public static final String LAST_RESTORE_TIME_FILENAME = "~backups/lastRestoreTime";
  public static final String OBJECT_NAME_OVERRIDE_SLOTNAME = "objectName";

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Log
   */
  private static final Logger log = Logger.getLogger("bacnet.server");
  private static final Logger loggerBacnetTransport = Logger.getLogger("bacnet.transport");

  private static final AsnInputStream asnIn = new AsnInputStream();
  private static final AsnOutputStream asnOut = new AsnOutputStream();
  private BacnetCovSubscriber covSubscriber = new BacnetCovSubscriber();
  private final ObjectSubscriber objectSubscriber = new ObjectSubscriber();
  static BasicContext bacnetContext;

  private int maxWaitTime = 0;
  private String objectName = "";
  private BBacnetDeviceStatus preBackupRestoreStatus = BBacnetDeviceStatus.operational;

  private final LocalBacnetCovPropPoll covPropPoller = new LocalBacnetCovPropPoll(this);

  private Clock.Ticket tsTicket = null;
  private final Object TIME_SYNC_LOCK = new Object();
  private BAbsTime lastTSTime = null;

  private static final boolean allowObjectIdWrite = false;

  private static final BRelTime CHECK_DUP_DELAY = BRelTime.makeSeconds(5);
  private Clock.Ticket checkDupTicket = null;
  private final Object CHECK_DUP_LOCK = new Object();

  private static final String SERIAL_NUMBER = Nre.getHostId();

////////////////////////////////////////////////////////////////
// SaveListener
////////////////////////////////////////////////////////////////

  private final Station.SaveListener saveListener = new Station.SaveListener()
  {
    @Override
    public void stationSave()
    {
      setDatabaseRevision(getDatabaseRevision() + 1);
    }

    @Override
    public void stationSaveOk()
    {
    }

    @Override
    public void stationSaveFail(String cause)
    {
    }

    public String toString()
    {
      return "Local BACnet Device " + getNavOrd();
    }
  };
}
