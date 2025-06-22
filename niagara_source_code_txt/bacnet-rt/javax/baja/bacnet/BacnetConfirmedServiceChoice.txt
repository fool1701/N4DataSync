/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

/**
 * This interface contains constants which represent the values
 * of the BacnetConfirmedServiceChoice iteration.  For more
 * information, see Bacnet spec, Clause 21, Confirmed Service
 * Productions.
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 12/19/01 4:35:45 PM$
 * @creation 01 Jul 97
 * @since Niagara 3 Bacnet 1.0
 */

public interface BacnetConfirmedServiceChoice
{

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  //  Alarm and Event Services
  int ACKNOWLEDGE_ALARM = 0;
  int CONFIRMED_COV_NOTIFICATION = 1;
  int CONFIRMED_EVENT_NOTIFICATION = 2;
  int GET_ALARM_SUMMARY = 3;
  int GET_ENROLLMENT_SUMMARY = 4;
  int GET_EVENT_INFORMATION = 29;
  int SUBSCRIBE_COV = 5;
  int SUBSCRIBE_COV_PROPERTY = 28;
  int LIFE_SAFTEY_OPERATION = 27;

  //  File Access Services
  int ATOMIC_READ_FILE = 6;
  int ATOMIC_WRITE_FILE = 7;

  //  Object Access Services
  int ADD_LIST_ELEMENT = 8;
  int REMOVE_LIST_ELEMENT = 9;
  int CREATE_OBJECT = 10;
  int DELETE_OBJECT = 11;
  int READ_PROPERTY = 12;
  int READ_PROPERTY_CONDITIONAL = 13;
  int READ_PROPERTY_MULTIPLE = 14;
  int READ_RANGE = 26;
  int WRITE_PROPERTY = 15;
  int WRITE_PROPERTY_MULTIPLE = 16;

  //  Remote Device Management Services
  int DEVICE_COMMUNICATION_CONTROL = 17;
  int CONFIRMED_PRIVATE_TRANSFER = 18;
  int CONFIRMED_TEXT_MESSAGE = 19;
  int REINITIALIZE_DEVICE = 20;

  //  Virtual Terminal Services
  int VT_OPEN = 21;
  int VT_CLOSE = 22;
  int VT_DATA = 23;

  //  Security Services
  int AUTHENTICATE = 24;
  int REQUEST_KEY = 25;

  String[] TAGS =
    {
      "acknowledgeAlarm",
      "confirmedCovNotification",
      "confirmedEventNotification",
      "getAlarmSummary",
      "getEnrollmentSummary",
      "subscribeCov",
      "atomicReadFile",
      "atomicWriteFile",
      "addListElement",
      "removeListElement",
      "createObject",
      "deleteObject",
      "readProperty",
      "readPropertyConditional",
      "readPropertyMultiple",
      "writeProperty",
      "writePropertyMultiple",
      "deviceCommunicationControl",
      "confirmedPrivateTransfer",
      "confirmedTextMessage",
      "reinitializeDevice",
      "vtOpen",
      "vtClose",
      "vtData",
      "authenticate",
      "requestKey",
      "readRange",
      "lifeSafetyOperation",
      "subscribeCovProperty",
      "getEventInformation"
    };

}