/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import java.util.Vector;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.*;
import javax.baja.bacnet.export.BLocalBacnetDevice;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.BacnetNotificationParameters;

/**
 * BBacnetComm is the object that exposes the communications
 * stack.  No methods exist, because different stack implementations
 * may have varying levels of support for the specification.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 19 Apr 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "commControl",
  type = "BBacnetCommControl",
  defaultValue = "BBacnetCommControl.enable",
  flags = Flags.TRANSIENT
)
@NiagaraAction(
  name = "enableComm"
)
@NiagaraAction(
  name = "disableInitiation"
)
@NiagaraAction(
  name = "disableComm"
)
abstract public class BBacnetComm
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.io.BBacnetComm(1837327661)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "commControl"

  /**
   * Slot for the {@code commControl} property.
   * @see #getCommControl
   * @see #setCommControl
   */
  public static final Property commControl = newProperty(Flags.TRANSIENT, BBacnetCommControl.enable, null);

  /**
   * Get the {@code commControl} property.
   * @see #commControl
   */
  public BBacnetCommControl getCommControl() { return (BBacnetCommControl)get(commControl); }

  /**
   * Set the {@code commControl} property.
   * @see #commControl
   */
  public void setCommControl(BBacnetCommControl v) { set(commControl, v, null); }

  //endregion Property "commControl"

  //region Action "enableComm"

  /**
   * Slot for the {@code enableComm} action.
   * @see #enableComm()
   */
  public static final Action enableComm = newAction(0, null);

  /**
   * Invoke the {@code enableComm} action.
   * @see #enableComm
   */
  public void enableComm() { invoke(enableComm, null, null); }

  //endregion Action "enableComm"

  //region Action "disableInitiation"

  /**
   * Slot for the {@code disableInitiation} action.
   * @see #disableInitiation()
   */
  public static final Action disableInitiation = newAction(0, null);

  /**
   * Invoke the {@code disableInitiation} action.
   * @see #disableInitiation
   */
  public void disableInitiation() { invoke(disableInitiation, null, null); }

  //endregion Action "disableInitiation"

  //region Action "disableComm"

  /**
   * Slot for the {@code disableComm} action.
   * @see #disableComm()
   */
  public static final Action disableComm = newAction(0, null);

  /**
   * Invoke the {@code disableComm} action.
   * @see #disableComm
   */
  public void disableComm() { invoke(disableComm, null, null); }

  //endregion Action "disableComm"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetComm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetNetwork;
  }

  public boolean isSiblingLegal(BComponent sibling)
  {
    return !(sibling instanceof BBacnetComm);
  }

  private BBacnetNetwork net()
  {
    return (BBacnetNetwork)getParent();
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Enable Bacnet communications.
   */
  public void doEnableComm()
  {
    setCommControl(BBacnetCommControl.enable);
  }

  /**
   * Disable Bacnet communications.
   */
  public void doDisableInitiation()
  {
    setCommControl(BBacnetCommControl.disableInitiation);
  }

  /**
   * Disable Bacnet communications.
   */
  public void doDisableComm()
  {
    setCommControl(BBacnetCommControl.disable);
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Is Bacnet communication allowed for execution of requests?
   * This checks that the commControl property is NOT set to disable,
   * and that the objectId of the local Bacnet device
   * is configured properly.
   * <p>
   * If both of these are configured properly, request execution
   * (responding to Bacnet requests) is allowed.
   *
   * @return true if Bacnet request execution is enabled.
   */
  public boolean isCommExecutionEnabled()
  {
    BLocalBacnetDevice local = net().getLocalDevice();
    local.loadSlots();
    return (getCommControl() != BBacnetCommControl.disable)
      && net().getStatus().isValid()
      && local.getObjectId().isValid();
  }

  /**
   * Is communication allowed for initiation of requests?
   * This checks that the commControl property is set to enabled,
   * and that the objectId of the local Bacnet device is configured
   * to a valid id.
   * <p>
   * If both of these are configured properly, comm is allowed.
   *
   * @return true if Bacnet request initiation is enabled.
   */
  public boolean isCommInitiationEnabled()
  {
    BLocalBacnetDevice local = net().getLocalDevice();
    local.loadSlots();
    return (getCommControl() == BBacnetCommControl.enable)
      && net().getStatus().isValid()
      && local.getObjectId().isValid();
  }


////////////////////////////////////////////////////////////////
// BACnet Stack API
////////////////////////////////////////////////////////////////

  /////////////////////////////////////
  // Alarm and Event Services
  /////////////////////////////////////

  /**
   * Issue an AcknowledgeAlarm request.
   */
  abstract public void acknowledgeAlarm(BBacnetAddress deviceAddress,
                                        long acknowledgingProcessId,
                                        BBacnetObjectIdentifier eventObjectId,
                                        BBacnetEventState eventStateAcknowledged,
                                        BBacnetTimeStamp timestamp,
                                        String acknowledgementSource,
                                        BBacnetTimeStamp timeOfAcknowledgement,
                                        BCharacterSetEncoding encoding)
    throws BacnetException;

  /**
   * Issue a ConfirmedCovNotification request.
   */
  abstract public void confirmedCovNotification(BBacnetAddress address,
                                                long subscriberProcessId,
                                                BBacnetObjectIdentifier initiatingDeviceId,
                                                BBacnetObjectIdentifier monitoredObjectId,
                                                long timeRemaining,
                                                PropertyValue[] listOfValues)
    throws BacnetException;

  /**
   * Issue a ConfirmedEventNotification request.
   */
  abstract public void confirmedEventNotification(BBacnetAddress address,
                                                  long processId,
                                                  BBacnetObjectIdentifier initiatingDeviceId,
                                                  BBacnetObjectIdentifier eventObjectId,
                                                  BBacnetTimeStamp timeStamp,
                                                  long notificationClass,
                                                  int priority,
                                                  BEnum eventType,
                                                  String messageText,
                                                  BBacnetNotifyType notifyType,
                                                  boolean ackRequired,
                                                  BEnum fromState,
                                                  BEnum toState,
                                                  BacnetNotificationParameters eventValues,
                                                  BCharacterSetEncoding encoding)
    throws BacnetException;

  /**
   * Issue a GetAlarmSummary request.
   */
  @SuppressWarnings("rawtypes")
  abstract public Vector getAlarmSummary(BBacnetAddress address)
    throws BacnetException;

  /**
   * Issue a GetEnrollmentSummary request.
   */
  @SuppressWarnings("rawtypes")
  abstract public Vector getEnrollmentSummary(BBacnetAddress address,
                                              int acknowledgmentFilter,
                                              BBacnetRecipientProcess enrollmentFilter,
                                              int eventStateFilter,
                                              BEnum eventTypeFilter,
                                              int[] priorityFilter,
                                              long notificationClassFilter)
    throws BacnetException;

  /**
   * Issue a GetEventInformation request.
   */
  @SuppressWarnings("rawtypes")
  abstract public Vector getEventInformation(BBacnetAddress address,
                                             BBacnetObjectIdentifier lastReceivedObjectId)
    throws BacnetException;

  /**
   * Issue a SubscribeCov request.
   */
  abstract public void subscribeCov(BBacnetAddress address,
                                    long processId,
                                    BBacnetObjectIdentifier objectId,
                                    boolean issueConfirmedNotifications,
                                    long lifetime)
    throws BacnetException;

  /**
   * Issue a SubscribeCov request indicating a subsription cancellation.
   */
  abstract public void unsubscribeCov(BBacnetAddress address,
                                      long processId,
                                      BBacnetObjectIdentifier objectId)
    throws BacnetException;

  /**
   * Issue a SubscribeCov request.
   */
  abstract public void subscribeCovProperty(BBacnetAddress address,
                                            long processId,
                                            BBacnetObjectIdentifier objectId,
                                            boolean issueConfirmedNotifications,
                                            long lifetime,
                                            PropertyReference monitoredPropertyIdentifier,
                                            BDouble covIncrement)
    throws BacnetException;

  /**
   * Issue a SubscribeCov request indicating a subsription cancellation.
   */
  abstract public void unsubscribeCovProperty(BBacnetAddress address,
                                              long processId,
                                              BBacnetObjectIdentifier objectId,
                                              PropertyReference monitoredPropertyIdentifier)
    throws BacnetException;

  /////////////////////////////////////
  // File Access Services
  /////////////////////////////////////

  /**
   * Issue an AtomicReadFile-Request, using record access.
   *
   * @param deviceAddress
   * @param objectId
   * @param start         file start position
   * @param count         number of octets to read
   * @return a FileData containing the information read from the device.
   */
  abstract public FileData atomicReadFileRecord(BBacnetAddress deviceAddress,
                                                BBacnetObjectIdentifier objectId,
                                                int start,
                                                long count)
    throws BacnetException;

  /**
   * Issue an AtomicReadFile-Request, using stream access.
   *
   * @param deviceAddress
   * @param objectId
   * @param start         file start position
   * @param count         number of octets to read
   * @return a FileData containing the information read from the device.
   */
  abstract public FileData atomicReadFileStream(BBacnetAddress deviceAddress,
                                                BBacnetObjectIdentifier objectId,
                                                int start,
                                                long count)
    throws BacnetException;

  /**
   * Issue an AtomicWriteFile-Request, using record access.
   *
   * @param deviceAddress
   * @param objectId
   * @param start          file start record
   * @param count          number of records to write
   * @param fileRecordData the source record data
   * @return the actual starting record of the written data.
   */
  abstract public int atomicWriteFileRecord(BBacnetAddress deviceAddress,
                                            BBacnetObjectIdentifier objectId,
                                            int start,
                                            long count,
                                            BBacnetOctetString[] fileRecordData)
    throws BacnetException;

  /**
   * Issue an AtomicWriteFile-Request, using stream access.
   *
   * @param deviceAddress
   * @param objectId
   * @param start         file start position
   * @param fileData      the source file data
   * @return the actual starting position of the written data.
   */
  abstract public int atomicWriteFileStream(BBacnetAddress deviceAddress,
                                            BBacnetObjectIdentifier objectId,
                                            int start,
                                            byte[] fileData)
    throws BacnetException;

  /////////////////////////////////////
  // Object Access Services
  /////////////////////////////////////

  /**
   * Issue an AddListElement request.
   *
   * @param deviceAddress  the address of the remote device.
   * @param objectId       the object-identifier of the remote object.
   * @param propertyId     the property-identifier of the desired property.
   * @param listOfElements the encoded list of elements to be added.
   */
  abstract public void addListElement(BBacnetAddress deviceAddress,
                                      BBacnetObjectIdentifier objectId,
                                      int propertyId,
                                      byte[] listOfElements)
    throws BacnetException;

  /**
   * Issue an AddListElement request.
   *
   * @param deviceAddress      the address of the remote device.
   * @param objectId           the object-identifier of the remote object.
   * @param propertyId         the property-identifier of the desired property.
   * @param propertyArrayIndex the array index, if specified.
   * @param listOfElements     the encoded list of elements to be added.
   */
  abstract public void addListElement(BBacnetAddress deviceAddress,
                                      BBacnetObjectIdentifier objectId,
                                      int propertyId,
                                      int propertyArrayIndex,
                                      byte[] listOfElements)
    throws BacnetException;

  /**
   * Issue a RemoveListElement request.
   *
   * @param deviceAddress  the address of the remote device.
   * @param objectId       the object-identifier of the remote object.
   * @param propertyId     the property-identifier of the desired property.
   * @param listOfElements the encoded list of elements to be removed.
   */
  abstract public void removeListElement(BBacnetAddress deviceAddress,
                                         BBacnetObjectIdentifier objectId,
                                         int propertyId,
                                         byte[] listOfElements)
    throws BacnetException;

  /**
   * Issue a RemoveListElement request.
   *
   * @param deviceAddress      the address of the remote device.
   * @param objectId           the object-identifier of the remote object.
   * @param propertyId         the property-identifier of the desired property.
   * @param propertyArrayIndex the array index, if specified.
   * @param listOfElements     the encoded list of elements to be removed.
   */
  abstract public void removeListElement(BBacnetAddress deviceAddress,
                                         BBacnetObjectIdentifier objectId,
                                         int propertyId,
                                         int propertyArrayIndex,
                                         byte[] listOfElements)
    throws BacnetException;

  /**
   * Issue a CreateObject service request.
   *
   * @param deviceAddress       the address of the remote device.
   * @param objectType          the object type of the new object.
   * @param listOfInitialValues the list of initial values.
   * @return the objectId of the new object.
   */
  @SuppressWarnings("rawtypes")
  abstract public BBacnetObjectIdentifier createObject(BBacnetAddress deviceAddress,
                                                       int objectType,
                                                       Array listOfInitialValues)
    throws BacnetException;

  /**
   * Issue a CreateObject service request.
   *
   * @param deviceAddress       the address of the remote device.
   * @param objectId          the object type of the new object.
   * @param listOfInitialValues the list of initial values.
   * @return the objectId of the new object.
   */
  @SuppressWarnings("rawtypes")
  abstract public BBacnetObjectIdentifier createObject(BBacnetAddress deviceAddress,
                                                       BBacnetObjectIdentifier objectId,
                                                       Array listOfInitialValues)
    throws BacnetException;

  /**
   * Issue a DeleteObject service request.
   *
   * @param deviceAddress the address of the remote device.
   * @param objectId    the object type of the new object.
   */
  abstract public void deleteObject(BBacnetAddress deviceAddress,
                                    BBacnetObjectIdentifier objectId)
    throws BacnetException;

  /**
   * Issue a ReadProperty service request.
   *
   * @param deviceAddress the address of the remote device.
   * @param objectId      the object-identifier of the remote object.
   * @param propertyId    the property-identifier of the desired property.
   * @return a byte array containing the encoded value.
   */
  abstract public byte[] readProperty(BBacnetAddress deviceAddress,
                                      BBacnetObjectIdentifier objectId,
                                      int propertyId)
    throws BacnetException;

  /**
   * Issue a ReadProperty service request.
   *
   * @param deviceAddress      the address of the remote device.
   * @param objectId           the object-identifier of the remote object.
   * @param propertyId         the property-identifier of the desired property.
   * @param propertyArrayIndex the array index, if specified.
   * @return a byte array containing the encoded value.
   */
  abstract public byte[] readProperty(BBacnetAddress deviceAddress,
                                      BBacnetObjectIdentifier objectId,
                                      int propertyId,
                                      int propertyArrayIndex)
    throws BacnetException;

  /**
   * Convenience method for requesting multiple properties from only
   * one object.
   *
   * @param deviceAddress      the address to which the request is sent.
   * @param objectId           the object identifier of the object to poll.
   * @param propRefs a Vector containing NBacnetPropertyReferences
   *                           for each property requested.
   * @return a Vector containing the list of Read Property Results, as PropertyValues,
   * if the request succeeds, or the input Vector if the request
   * is aborted or rejected.
   */
  @SuppressWarnings("rawtypes")
  abstract public Vector readPropertyMultiple(BBacnetAddress deviceAddress,
                                              BBacnetObjectIdentifier objectId,
                                              Vector propRefs)
    throws BacnetException;

  /**
   * ReadPropertyMultiple used in device-level polling.
   * If this request is aborted or rejected (probably due to
   * maxAPDULengthAccepted and/or segmentation issues), the
   * Iterator returned will be the iterator containing the
   * property requests.  This allows the requesting object
   * to make a decision to split up the request into
   * smaller chunks.
   *
   * @param deviceAddress the address to which the request is sent.
   * @param readAccessSpecs            a Vector containing the NReadAccessSpecs .
   * @return a Vector containing the NReadAccessResults in the same
   * order as the specifications if the request succeeds, or
   * the input Vector if the request is aborted or rejected.
   */
  @SuppressWarnings("rawtypes")
  abstract public Vector readPropertyMultiple(BBacnetAddress deviceAddress,
                                              Vector readAccessSpecs)
    throws BacnetException;

  /**
   * Issue a ReadRange service request.
   *
   * @param deviceAddress
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   */
  abstract public RangeData readRange(BBacnetAddress deviceAddress,
                                      BBacnetObjectIdentifier objectId,
                                      int propertyId,
                                      int propertyArrayIndex,
                                      int rangeType,
                                      long referenceIndex,
                                      BBacnetDateTime referenceTime,
                                      int count)
    throws BacnetException;


  /**
   * Issue a WriteProperty service request with no priority level.
   *
   * @param deviceAddress      the address of the remote device to write to.
   * @param objectId           the object-identifier of the remote object.
   * @param propertyId         the property-identifier of the desired property.
   * @param propertyArrayIndex the array index, if specified.
   * @param encodedValue       byte array containing Asn-encoded value to write.
   */
  abstract public void writeProperty(BBacnetAddress deviceAddress,
                                     BBacnetObjectIdentifier objectId,
                                     int propertyId,
                                     int propertyArrayIndex,
                                     byte[] encodedValue)
    throws BacnetException;

  /**
   * Issue a WriteProperty service request with no priority or index.
   *
   * @param deviceAddress the address of the remote device to write to.
   * @param objectId      the object-identifier of the remote object.
   * @param propertyId    the property-identifier of the desired property.
   * @param encodedValue  byte array containing Asn-encoded value to write.
   */
  abstract public void writeProperty(BBacnetAddress deviceAddress,
                                     BBacnetObjectIdentifier objectId,
                                     int propertyId,
                                     byte[] encodedValue)
    throws BacnetException;

  /**
   * Issue a WriteProperty service request.
   *
   * @param deviceAddress      the address of the remote device to write to.
   * @param objectId           the object-identifier of the remote object.
   * @param propertyId         the property-identifier of the desired property.
   * @param propertyArrayIndex the array index, if specified.
   * @param encodedValue       byte array containing Asn-encoded value to write.
   * @param priorityLevel      the priority level, if specified.
   */
  abstract public void writeProperty(BBacnetAddress deviceAddress,
                                     BBacnetObjectIdentifier objectId,
                                     int propertyId,
                                     int propertyArrayIndex,
                                     byte[] encodedValue,
                                     int priorityLevel)
    throws BacnetException;

  /**
   * WritePropertyMultiple is not used by Niagara, but it is required for
   * BACnet B-OWS compliance, so we make the best effort we can to send this
   * type of request.  By setting the useWritePropertyMultiple flag to true,
   * writes will use this format instead of the WriteProperty format.
   */
  @SuppressWarnings("rawtypes")
  abstract public void writePropertyMultiple(BBacnetAddress deviceAddress,
                                             Vector writeAccessSpecs)
    throws BacnetException;

  /////////////////////////////////////
  // Remote Device Management Services
  /////////////////////////////////////

  /**
   * Issue a DeviceCommunicationControl Request.
   *
   * @param deviceAddress
   * @param enableDisable
   * @param duration
   * @param password
   * @param encoding
   */
  abstract public void deviceCommunicationControl(BBacnetAddress deviceAddress,
                                                  BBacnetCommControl enableDisable,
                                                  BRelTime duration,
                                                  String password,
                                                  BCharacterSetEncoding encoding)
    throws BacnetException;

  /**
   * Issue a ConfirmedPrivateTransfer Request.
   *
   * @param deviceAddress
   * @param vendorId
   * @param serviceNumber
   * @param serviceParameters
   */
  abstract public byte[] confirmedPrivateTransfer(BBacnetAddress deviceAddress,
                                                  int vendorId,
                                                  int serviceNumber,
                                                  byte[] serviceParameters)
    throws BacnetException;

  /**
   * Issue a ReinitializeDevice Request.
   */
  abstract public void reinitializeDevice(BBacnetAddress deviceAddress,
                                          BBacnetReinitializedDeviceState reinitializedStateOfDevice,
                                          String password,
                                          BCharacterSetEncoding encoding)
    throws BacnetException;

  /////////////////////////////////////
  // Virtual Terminal Services
  /////////////////////////////////////

  /////////////////////////////////////
  // Security Services
  /////////////////////////////////////

  /////////////////////////////////////
  // Unconfirmed Services
  /////////////////////////////////////

  /**
   * Issue an I-Am request.
   */
  abstract public void iAm();

  /**
   * Issue an I-Have request.
   *
   * @param objectId
   * @param objectName
   * @param encoding
   */
  abstract public void iHave(BBacnetObjectIdentifier objectId,
                             String objectName,
                             BCharacterSetEncoding encoding);

  /**
   * Issue an UnconfirmedCovNotification request.
   */
  abstract public void unconfirmedCovNotification(BBacnetAddress address,
                                                  long subscriberProcessId,
                                                  BBacnetObjectIdentifier initiatingDeviceId,
                                                  BBacnetObjectIdentifier monitoredObjectId,
                                                  long timeRemaining,
                                                  PropertyValue[] listOfValues)
    throws BacnetException;

  /**
   * Issue an UnconfirmedEventNotification request.
   */
  abstract public void unconfirmedEventNotification(BBacnetAddress address,
                                                    long processId,
                                                    BBacnetObjectIdentifier initiatingDeviceId,
                                                    BBacnetObjectIdentifier eventObjectId,
                                                    BBacnetTimeStamp timeStamp,
                                                    long notificationClass,
                                                    int priority,
                                                    BEnum eventType,
                                                    String messageText,
                                                    BBacnetNotifyType notifyType,
                                                    boolean ackRequired,
                                                    BEnum fromState,
                                                    BEnum toState,
                                                    BacnetNotificationParameters eventValues,
                                                    BCharacterSetEncoding encoding)

    throws BacnetException;

  /**
   * Issue an UnconfirmedPrivateTransfer Request.
   *
   * @param deviceAddress
   * @param vendorId
   * @param serviceNumber
   * @param serviceParameters
   */
  abstract public void unconfirmedPrivateTransfer(BBacnetAddress deviceAddress,
                                                  int vendorId,
                                                  int serviceNumber,
                                                  byte[] serviceParameters)
    throws BacnetException;

  /**
   * Send a TimeSynchronization message to the given address.
   *
   * @param recipient
   */
  abstract public void timeSynch(BBacnetRecipient recipient)
    throws BacnetException;

  /**
   * Broadcast a Who-Has message with object Id, and no instance limits.
   *
   * @param deviceAddress the target device address.
   * @param objectId      the desired object id.
   */
  abstract public void whoHas(BBacnetAddress deviceAddress,
                              BBacnetObjectIdentifier objectId)
    throws BacnetException;

  /**
   * Broadcast a Who-Has message with instance limits.
   *
   * @param deviceAddress the target device address.
   * @param objectId      the desired object id.
   * @param lowLimit      device instance range low limit.
   * @param highLimit     device instance range high limit.
   */
  abstract public void whoHas(BBacnetAddress deviceAddress,
                              BBacnetObjectIdentifier objectId,
                              int lowLimit,
                              int highLimit)
    throws BacnetException;

  /**
   * Broadcast a Who-Has message with object Id, and no instance limits.
   *
   * @param deviceAddress the target device address.
   * @param objectName    the desired object name.
   */
  abstract public void whoHas(BBacnetAddress deviceAddress,
                              String objectName,
                              BCharacterSetEncoding charset)
    throws BacnetException;

  /**
   * Broadcast a Who-Has message with instance limits.
   *
   * @param deviceAddress the target device address.
   * @param objectName    the desired object name.
   * @param lowLimit      device instance range low limit.
   * @param highLimit     device instance range high limit.
   */
  abstract public void whoHas(BBacnetAddress deviceAddress,
                              String objectName,
                              BCharacterSetEncoding charset,
                              int lowLimit,
                              int highLimit)
    throws BacnetException;

  /**
   * Broadcast a Who-Is message with no instance limits.
   *
   * @param deviceAddress the target device address.
   */
  abstract public void whoIs(BBacnetAddress deviceAddress)
    throws BacnetException;

  /**
   * Broadcast a Who-Is message with instance limits.
   *
   * @param lowLimit  device instance range low limit.
   * @param highLimit device instance range high limit.
   */
  abstract public void whoIs(BBacnetAddress deviceAddress,
                             int lowLimit,
                             int highLimit)
    throws BacnetException;

  /**
   * Send a UTCTimeSynchronization message to the given address.
   *
   * @param recipient
   */
  abstract public void utcTimeSynch(BBacnetRecipient recipient)
    throws BacnetException;


////////////////////////////////////////////////////////////////
// Listeners
////////////////////////////////////////////////////////////////

  /**
   * Register the given listener to receive services of the specified
   * service type.
   * The serviceType is specified in Clause 21 of the BACnet Specification,
   * under the BACnetServicesSupported bit string definition.
   * The listener must implement the appropriate listener
   * interface to receive the specified service type.  For example,
   * if you wish to receive ConfirmedPrivateTransfer requests, the
   * listener supplied must be a PrivateTransferListener.
   *
   * @param listener     a BacnetServiceListener of the appropriate type
   * @param serviceIndex the service type, as identified in BacnetServicesSupported
   */
  abstract public void registerBacnetListener(BacnetServiceListener listener,
                                              int serviceIndex);

  /**
   * Unregister the given listener from receiving services of the specified
   * service type.
   * The serviceType is specified in Clause 21 of the BACnet Specification,
   * under the BACnetServicesSupported bit string definition.
   * The listener must implement the appropriate listener
   * interface to receive the specified service type.  For example,
   * if you wish to receive ConfirmedPrivateTransfer requests, the
   * listener supplied must be a PrivateTransferListener.
   *
   * @param listener     a BacnetServiceListener of the appropriate type
   * @param serviceIndex the service type, as identified in BacnetServicesSupported
   */
  abstract public void unregisterBacnetListener(BacnetServiceListener listener,
                                                int serviceIndex);


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("commConfig.png");

}
