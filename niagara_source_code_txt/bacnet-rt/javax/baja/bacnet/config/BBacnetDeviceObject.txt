/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import java.util.logging.Level;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.*;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.DefaultFileCopy;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;

import com.tridium.bacnet.stack.DeviceRegistry;
import com.tridium.bacnet.stack.transport.ConfirmedRequestPdu;

/**
 * @author Craig Gemmill
 * @version $Revision: 11$ $Date: 12/13/01 3:37:28 PM$
 * @creation 29 Jan 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.DEVICE, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
/*
 systemStatus reflects the status of the Bacnet device as
 reported by the device.
 */
@NiagaraProperty(
  name = "systemStatus",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetDeviceStatus.OPERATIONAL, BEnumRange.make(BBacnetDeviceStatus.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.SYSTEM_STATUS, ASN_ENUMERATED)")
)
@NiagaraProperty(
  name = "vendorName",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.VENDOR_NAME, ASN_CHARACTER_STRING)")
)
@NiagaraProperty(
  name = "vendorIdentifier",
  type = "BBacnetUnsigned",
  defaultValue = "new BBacnetUnsigned(-1)",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.VENDOR_IDENTIFIER, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "modelName",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.MODEL_NAME, ASN_CHARACTER_STRING)")
)
@NiagaraProperty(
  name = "firmwareRevision",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.FIRMWARE_REVISION, ASN_CHARACTER_STRING)")
)
@NiagaraProperty(
  name = "applicationSoftwareVersion",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.APPLICATION_SOFTWARE_VERSION, ASN_CHARACTER_STRING)")
)
@NiagaraProperty(
  name = "protocolVersion",
  type = "BBacnetUnsigned",
  defaultValue = "new BBacnetUnsigned(1)",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PROTOCOL_VERSION, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "protocolRevision",
  type = "BBacnetUnsigned",
  defaultValue = "new BBacnetUnsigned(0)",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PROTOCOL_REVISION, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "protocolServicesSupported",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetServicesSupported\"))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PROTOCOL_SERVICES_SUPPORTED, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_SERVICES_SUPPORTED_MAP)")
)
@NiagaraProperty(
  name = "protocolObjectTypesSupported",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetObjectTypesSupported\"))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PROTOCOL_OBJECT_TYPES_SUPPORTED, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_OBJECT_TYPES_SUPPORTED_MAP)")
)
@NiagaraProperty(
  name = "objectList",
  type = "BBacnetArray",
  defaultValue = "new BBacnetArray(BBacnetObjectIdentifier.TYPE)",
  flags = Flags.HIDDEN | Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_LIST, ASN_BACNET_ARRAY)")
)
@NiagaraProperty(
  name = "maxAPDULengthAccepted",
  type = "BBacnetUnsigned",
  defaultValue = "new BBacnetUnsigned(ConfirmedRequestPdu.MAX_APDU_LENGTH_UP_TO_MIN_MSG_SIZE)",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.MAX_APDU_LENGTH_ACCEPTED, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "segmentationSupported",
  type = "BBacnetSegmentation",
  defaultValue = "BBacnetSegmentation.noSegmentation",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.SEGMENTATION_SUPPORTED, ASN_ENUMERATED)")
)
/*
 apduTimeout is the time in milliseconds between retransmissions of an APDU.
 <p>If the device does not support modification of this parameter,
 it shall be set to 60000 milliseconds.
 */
@NiagaraProperty(
  name = "apduTimeout",
  type = "BBacnetUnsigned",
  defaultValue = "new BBacnetUnsigned(3000)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.APDU_TIMEOUT, ASN_UNSIGNED)")
)
/*
 numberOfAPDURetries indicates the number of retransmissions of an APDU.
 */
@NiagaraProperty(
  name = "numberOfAPDURetries",
  type = "BBacnetUnsigned",
  defaultValue = "new BBacnetUnsigned(3)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.NUMBER_OF_APDU_RETRIES, ASN_UNSIGNED)")
)
/*
 list of device ids with the BacnetAddress used to communicate to
 each device.
 */
@NiagaraProperty(
  name = "deviceAddressBinding",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetAddressBinding.TYPE)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.DEVICE_ADDRESS_BINDING, ASN_BACNET_LIST)")
)
@NiagaraProperty(
  name = "databaseRevision",
  type = "BBacnetUnsigned",
  defaultValue = "new BBacnetUnsigned(-1)",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.DATABASE_REVISION, ASN_UNSIGNED)")
)
public class BBacnetDeviceObject
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetDeviceObject(2710010336)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.DEVICE, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "systemStatus"

  /**
   * Slot for the {@code systemStatus} property.
   * systemStatus reflects the status of the Bacnet device as
   * reported by the device.
   * @see #getSystemStatus
   * @see #setSystemStatus
   */
  public static final Property systemStatus = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetDeviceStatus.OPERATIONAL, BEnumRange.make(BBacnetDeviceStatus.TYPE)), makeFacets(BBacnetPropertyIdentifier.SYSTEM_STATUS, ASN_ENUMERATED));

  /**
   * Get the {@code systemStatus} property.
   * systemStatus reflects the status of the Bacnet device as
   * reported by the device.
   * @see #systemStatus
   */
  public BEnum getSystemStatus() { return (BEnum)get(systemStatus); }

  /**
   * Set the {@code systemStatus} property.
   * systemStatus reflects the status of the Bacnet device as
   * reported by the device.
   * @see #systemStatus
   */
  public void setSystemStatus(BEnum v) { set(systemStatus, v, null); }

  //endregion Property "systemStatus"

  //region Property "vendorName"

  /**
   * Slot for the {@code vendorName} property.
   * @see #getVendorName
   * @see #setVendorName
   */
  public static final Property vendorName = newProperty(Flags.READONLY, "", makeFacets(BBacnetPropertyIdentifier.VENDOR_NAME, ASN_CHARACTER_STRING));

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

  //region Property "vendorIdentifier"

  /**
   * Slot for the {@code vendorIdentifier} property.
   * @see #getVendorIdentifier
   * @see #setVendorIdentifier
   */
  public static final Property vendorIdentifier = newProperty(Flags.READONLY, new BBacnetUnsigned(-1), makeFacets(BBacnetPropertyIdentifier.VENDOR_IDENTIFIER, ASN_UNSIGNED));

  /**
   * Get the {@code vendorIdentifier} property.
   * @see #vendorIdentifier
   */
  public BBacnetUnsigned getVendorIdentifier() { return (BBacnetUnsigned)get(vendorIdentifier); }

  /**
   * Set the {@code vendorIdentifier} property.
   * @see #vendorIdentifier
   */
  public void setVendorIdentifier(BBacnetUnsigned v) { set(vendorIdentifier, v, null); }

  //endregion Property "vendorIdentifier"

  //region Property "modelName"

  /**
   * Slot for the {@code modelName} property.
   * @see #getModelName
   * @see #setModelName
   */
  public static final Property modelName = newProperty(Flags.READONLY, "", makeFacets(BBacnetPropertyIdentifier.MODEL_NAME, ASN_CHARACTER_STRING));

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
  public static final Property firmwareRevision = newProperty(Flags.READONLY, "", makeFacets(BBacnetPropertyIdentifier.FIRMWARE_REVISION, ASN_CHARACTER_STRING));

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
  public static final Property applicationSoftwareVersion = newProperty(Flags.READONLY, "", makeFacets(BBacnetPropertyIdentifier.APPLICATION_SOFTWARE_VERSION, ASN_CHARACTER_STRING));

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

  //region Property "protocolVersion"

  /**
   * Slot for the {@code protocolVersion} property.
   * @see #getProtocolVersion
   * @see #setProtocolVersion
   */
  public static final Property protocolVersion = newProperty(Flags.READONLY, new BBacnetUnsigned(1), makeFacets(BBacnetPropertyIdentifier.PROTOCOL_VERSION, ASN_UNSIGNED));

  /**
   * Get the {@code protocolVersion} property.
   * @see #protocolVersion
   */
  public BBacnetUnsigned getProtocolVersion() { return (BBacnetUnsigned)get(protocolVersion); }

  /**
   * Set the {@code protocolVersion} property.
   * @see #protocolVersion
   */
  public void setProtocolVersion(BBacnetUnsigned v) { set(protocolVersion, v, null); }

  //endregion Property "protocolVersion"

  //region Property "protocolRevision"

  /**
   * Slot for the {@code protocolRevision} property.
   * @see #getProtocolRevision
   * @see #setProtocolRevision
   */
  public static final Property protocolRevision = newProperty(Flags.READONLY, new BBacnetUnsigned(0), makeFacets(BBacnetPropertyIdentifier.PROTOCOL_REVISION, ASN_UNSIGNED));

  /**
   * Get the {@code protocolRevision} property.
   * @see #protocolRevision
   */
  public BBacnetUnsigned getProtocolRevision() { return (BBacnetUnsigned)get(protocolRevision); }

  /**
   * Set the {@code protocolRevision} property.
   * @see #protocolRevision
   */
  public void setProtocolRevision(BBacnetUnsigned v) { set(protocolRevision, v, null); }

  //endregion Property "protocolRevision"

  //region Property "protocolServicesSupported"

  /**
   * Slot for the {@code protocolServicesSupported} property.
   * @see #getProtocolServicesSupported
   * @see #setProtocolServicesSupported
   */
  public static final Property protocolServicesSupported = newProperty(Flags.READONLY, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetServicesSupported")), makeFacets(BBacnetPropertyIdentifier.PROTOCOL_SERVICES_SUPPORTED, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_SERVICES_SUPPORTED_MAP));

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
  public static final Property protocolObjectTypesSupported = newProperty(Flags.READONLY, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetObjectTypesSupported")), makeFacets(BBacnetPropertyIdentifier.PROTOCOL_OBJECT_TYPES_SUPPORTED, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_OBJECT_TYPES_SUPPORTED_MAP));

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

  //region Property "objectList"

  /**
   * Slot for the {@code objectList} property.
   * @see #getObjectList
   * @see #setObjectList
   */
  public static final Property objectList = newProperty(Flags.HIDDEN | Flags.READONLY, new BBacnetArray(BBacnetObjectIdentifier.TYPE), makeFacets(BBacnetPropertyIdentifier.OBJECT_LIST, ASN_BACNET_ARRAY));

  /**
   * Get the {@code objectList} property.
   * @see #objectList
   */
  public BBacnetArray getObjectList() { return (BBacnetArray)get(objectList); }

  /**
   * Set the {@code objectList} property.
   * @see #objectList
   */
  public void setObjectList(BBacnetArray v) { set(objectList, v, null); }

  //endregion Property "objectList"

  //region Property "maxAPDULengthAccepted"

  /**
   * Slot for the {@code maxAPDULengthAccepted} property.
   * @see #getMaxAPDULengthAccepted
   * @see #setMaxAPDULengthAccepted
   */
  public static final Property maxAPDULengthAccepted = newProperty(Flags.READONLY, new BBacnetUnsigned(ConfirmedRequestPdu.MAX_APDU_LENGTH_UP_TO_MIN_MSG_SIZE), makeFacets(BBacnetPropertyIdentifier.MAX_APDU_LENGTH_ACCEPTED, ASN_UNSIGNED));

  /**
   * Get the {@code maxAPDULengthAccepted} property.
   * @see #maxAPDULengthAccepted
   */
  public BBacnetUnsigned getMaxAPDULengthAccepted() { return (BBacnetUnsigned)get(maxAPDULengthAccepted); }

  /**
   * Set the {@code maxAPDULengthAccepted} property.
   * @see #maxAPDULengthAccepted
   */
  public void setMaxAPDULengthAccepted(BBacnetUnsigned v) { set(maxAPDULengthAccepted, v, null); }

  //endregion Property "maxAPDULengthAccepted"

  //region Property "segmentationSupported"

  /**
   * Slot for the {@code segmentationSupported} property.
   * @see #getSegmentationSupported
   * @see #setSegmentationSupported
   */
  public static final Property segmentationSupported = newProperty(Flags.READONLY, BBacnetSegmentation.noSegmentation, makeFacets(BBacnetPropertyIdentifier.SEGMENTATION_SUPPORTED, ASN_ENUMERATED));

  /**
   * Get the {@code segmentationSupported} property.
   * @see #segmentationSupported
   */
  public BBacnetSegmentation getSegmentationSupported() { return (BBacnetSegmentation)get(segmentationSupported); }

  /**
   * Set the {@code segmentationSupported} property.
   * @see #segmentationSupported
   */
  public void setSegmentationSupported(BBacnetSegmentation v) { set(segmentationSupported, v, null); }

  //endregion Property "segmentationSupported"

  //region Property "apduTimeout"

  /**
   * Slot for the {@code apduTimeout} property.
   * apduTimeout is the time in milliseconds between retransmissions of an APDU.
   * <p>If the device does not support modification of this parameter,
   * it shall be set to 60000 milliseconds.
   * @see #getApduTimeout
   * @see #setApduTimeout
   */
  public static final Property apduTimeout = newProperty(0, new BBacnetUnsigned(3000), makeFacets(BBacnetPropertyIdentifier.APDU_TIMEOUT, ASN_UNSIGNED));

  /**
   * Get the {@code apduTimeout} property.
   * apduTimeout is the time in milliseconds between retransmissions of an APDU.
   * <p>If the device does not support modification of this parameter,
   * it shall be set to 60000 milliseconds.
   * @see #apduTimeout
   */
  public BBacnetUnsigned getApduTimeout() { return (BBacnetUnsigned)get(apduTimeout); }

  /**
   * Set the {@code apduTimeout} property.
   * apduTimeout is the time in milliseconds between retransmissions of an APDU.
   * <p>If the device does not support modification of this parameter,
   * it shall be set to 60000 milliseconds.
   * @see #apduTimeout
   */
  public void setApduTimeout(BBacnetUnsigned v) { set(apduTimeout, v, null); }

  //endregion Property "apduTimeout"

  //region Property "numberOfAPDURetries"

  /**
   * Slot for the {@code numberOfAPDURetries} property.
   * numberOfAPDURetries indicates the number of retransmissions of an APDU.
   * @see #getNumberOfAPDURetries
   * @see #setNumberOfAPDURetries
   */
  public static final Property numberOfAPDURetries = newProperty(0, new BBacnetUnsigned(3), makeFacets(BBacnetPropertyIdentifier.NUMBER_OF_APDU_RETRIES, ASN_UNSIGNED));

  /**
   * Get the {@code numberOfAPDURetries} property.
   * numberOfAPDURetries indicates the number of retransmissions of an APDU.
   * @see #numberOfAPDURetries
   */
  public BBacnetUnsigned getNumberOfAPDURetries() { return (BBacnetUnsigned)get(numberOfAPDURetries); }

  /**
   * Set the {@code numberOfAPDURetries} property.
   * numberOfAPDURetries indicates the number of retransmissions of an APDU.
   * @see #numberOfAPDURetries
   */
  public void setNumberOfAPDURetries(BBacnetUnsigned v) { set(numberOfAPDURetries, v, null); }

  //endregion Property "numberOfAPDURetries"

  //region Property "deviceAddressBinding"

  /**
   * Slot for the {@code deviceAddressBinding} property.
   * list of device ids with the BacnetAddress used to communicate to
   * each device.
   * @see #getDeviceAddressBinding
   * @see #setDeviceAddressBinding
   */
  public static final Property deviceAddressBinding = newProperty(0, new BBacnetListOf(BBacnetAddressBinding.TYPE), makeFacets(BBacnetPropertyIdentifier.DEVICE_ADDRESS_BINDING, ASN_BACNET_LIST));

  /**
   * Get the {@code deviceAddressBinding} property.
   * list of device ids with the BacnetAddress used to communicate to
   * each device.
   * @see #deviceAddressBinding
   */
  public BBacnetListOf getDeviceAddressBinding() { return (BBacnetListOf)get(deviceAddressBinding); }

  /**
   * Set the {@code deviceAddressBinding} property.
   * list of device ids with the BacnetAddress used to communicate to
   * each device.
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
  public static final Property databaseRevision = newProperty(Flags.READONLY, new BBacnetUnsigned(-1), makeFacets(BBacnetPropertyIdentifier.DATABASE_REVISION, ASN_UNSIGNED));

  /**
   * Get the {@code databaseRevision} property.
   * @see #databaseRevision
   */
  public BBacnetUnsigned getDatabaseRevision() { return (BBacnetUnsigned)get(databaseRevision); }

  /**
   * Set the {@code databaseRevision} property.
   * @see #databaseRevision
   */
  public void setDatabaseRevision(BBacnetUnsigned v) { set(databaseRevision, v, null); }

  //endregion Property "databaseRevision"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDeviceObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  public BBacnetDeviceObject()
  {
  }


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * Started.
   */
  public void started()
    throws Exception
  {
    super.started();
    oldId = getObjectId();
  }

  /**
   * Property Added.
   */
  @Override
  public void added(Property p, Context cx)
  {
    super.added(p, cx);

    if (!isRunning())
    {
      return;
    }

    if (BBacnetPropertyIdentifier.maxSegmentsAccepted.getTag().equals(p.getName()))
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(device().getName() + " added slot maxSegmentsAccepted " + device().getMaxSegmentsAccepted());
      }

      DeviceRegistry.update(device());
      if (log.isLoggable(Level.FINEST))
      {
        log.finest(device().getName() + " deviceObject added callback execution finish for property " + p);
      }
    }
  }

  /**
   * Property Changed.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning() || (cx == fallback)) return;
    if (p.equals(objectId))
    {
      BBacnetObjectIdentifier newId = getObjectId();
      BBacnetNetwork network = BBacnetNetwork.bacnet();
      if (newId.equals(oldId)) return;
      BBacnetDevice d = network.doLookupDeviceById(newId);
      if ((d != null) && (d != device()))
      {
        log.severe("Duplicate Object ID:" + newId + ", used by " + d.getName()
          + "!\n  Resetting to old id:" + oldId);
        set(objectId, oldId, fallback);
      }
      else
      {
        if (log.isLoggable(Level.FINE))
          log.fine("Object ID changed from " + oldId + " to " + newId);
        device().objectIdChanged();
        network.getLocalDevice().updateAddressBinding(oldId, newId);
        network.updateDevice(device());

        // Must remove the old entry because the new ID should not be present and then the address
        // map to the old ID will not be removed when calling update.
        if (oldId != null)
        {
          DeviceRegistry.remove(oldId);
        }
        DeviceRegistry.update(device());

        oldId = newId;
      }
    }
    else if (p.equals(protocolRevision))
    {
      DeviceRegistry.update(device());
    }
    else if (p.equals(segmentationSupported))
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(device().getName() + " segmentationSupported changed to " + device().getSegmentationSupported());
      }

      DeviceRegistry.update(device());
    }
    else if (p.equals(maxAPDULengthAccepted))
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(device().getName() + " maxAPDULengthAccepted changed to " + device().getMaxAPDULengthAccepted());
      }

      DeviceRegistry.update(device());
    }
    // maxSegmentsAccepted is an optional, dynamic property
    else if (BBacnetPropertyIdentifier.maxSegmentsAccepted.getTag().equals(p.getName()))
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(device().getName() + " maxSegmentsAccepted changed to " + device().getMaxSegmentsAccepted());
      }

      DeviceRegistry.update(device());
    }
    if (p.equals(protocolServicesSupported))
    {
      device().updateServicesSupported();
    }

    if (log.isLoggable(Level.FINEST))
    {
      log.finest(device().getName() + " deviceObject changed callback execution finish for property " + p);
    }
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    return getObjectName() + " [" + getObjectId().toString(context) + "]";
  }

  /**
   * Should this property ID be polled?
   * Override point for objects to filter properties for polling, e.g.,
   * Object_List in Device object, or Log_Buffer in Trend Log.
   */
  protected boolean shouldPoll(int propertyId)
  {
    switch (propertyId)
    {
      case BBacnetPropertyIdentifier.SYSTEM_STATUS:
      case BBacnetPropertyIdentifier.LOCAL_TIME:
      case BBacnetPropertyIdentifier.LOCAL_DATE:
      case BBacnetPropertyIdentifier.DAYLIGHT_SAVINGS_STATUS:
        return true;
      default:
        return false;
    }
  }

  /**
   * Callback for processing upLoad on async thread.
   * Default implementation is to call asyncUpload on all
   * children implementing the  Loadable interface.
   */
  public void doUpload(BUploadParameters p, Context cx)
  {
    // Bail if device is down or disabled, or objectId is bad.
    if (!device().getEnabled() || getStatus().isDown())
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(device().getName() + " is either disabled or status is down, deviceObject upload is unsuccessful.");
      }
      return;
    }
    if (!getObjectId().isValid()) return;

    try
    {
      readProperty(maxAPDULengthAccepted);
      readProperty(segmentationSupported);
      readProperty(vendorIdentifier);
      readProperty(modelName);
      readProperty(firmwareRevision);
      readProperty(applicationSoftwareVersion);

      // Determine the vendor-specific objectTypes file if null.
      if (getVendorIdentifier().getInt() == VENDOR_ID_TRIDIUM)
      {
        if (getFirmwareRevision().startsWith("3"))
        {
          try
          {
            BOrd vendorObjectTypesFile = (BOrd)device().get("vendorObjectTypesFile");
            if ((vendorObjectTypesFile != null) && !vendorObjectTypesFile.equals(BOrd.NULL))
            {
              DefaultFileCopy.copyFile("niagaraAxBacnetObjectTypes.xml");
              device().set("vendorObjectTypesFile", BOrd.make("file!defaults/niagaraAxBacnetObjectTypes.xml"));
            }
//          if (device().getVendorObjectTypesFile().equals(BOrd.NULL))
//          {
//            device().setVendorObjectTypesFile(BOrd.make("file:!lib/niagaraAxBacnetObjectTypes.xml"));
//          }
          }
          catch (Exception e)
          {
            if (log.isLoggable(Level.FINE))
            {
              log.log(Level.FINE, "Exception setting vendorObjectTypesFile for BACnet deviceObject " + this + ":" + e, e);
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception uploading BACnet deviceObject " + this + ":" + e, e);
      }
    }
    super.doUpload(p, cx);
    if (log.isLoggable(Level.FINEST))
    {
      log.finest(device().getName() + "deviceObject upload execution finish.");
    }
  }


////////////////////////////////////////////////////////////////
//  Access methods
////////////////////////////////////////////////////////////////

  public int getMaxSegmentsAccepted()
  {
    BBacnetUnsigned msa = (BBacnetUnsigned)get(BBacnetPropertyIdentifier.maxSegmentsAccepted.getTag());
    if (msa != null) return msa.getInt();
    return ConfirmedRequestPdu.MAX_SEGS_UNSPECIFIED;
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetDeviceObject", 2);
    out.prop("oldId", oldId);
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BBacnetObjectIdentifier oldId;

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////


}
