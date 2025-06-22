/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/**
 *
 */
package javax.baja.bacnet.io;

import javax.baja.sys.BEnum;

import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BCharacterSetEncoding;

/**
 * EventNotificationListener is the interface that classes must implement
 * to be notified when BACnet event notifications are received by the Niagara
 * BACnet comm stack.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 22 Aug 2007
 * @since Niagara 3.2
 */
public interface EventNotificationListener
  extends BacnetServiceListener
{
  /**
   * Receive a ConfirmedEventNotification request from the given address.
   * Note that the BACnet Transport layer acknowledgment of this request is
   * already being handled by the Niagara comm stack.  This is purely used
   * for notification to a subordinate application.
   */
  void receiveConfirmedEventNotification(BBacnetAddress sourceAddress,
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
                                         byte[] eventValues,
                                         BCharacterSetEncoding encoding);

  /**
   * Receive an UnconfirmedEventNotification request from the given address.
   */
  void receiveUnconfirmedEventNotification(BBacnetAddress sourceAddress,
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
                                           byte[] eventValues,
                                           BCharacterSetEncoding encoding);
}
