/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

/**
 * Standard keys for the AlarmData portion of a
 * BAlarmRecord referencing a Bacnet alarm.
 * <p>
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 12/19/01 4:35:44 PM$
 * @creation 07 Nov 01
 * @since Niagara 3 Bacnet 1.0
 */
public interface BacnetAlarmConst
{
  // Bacnet specific alarm keys
  String BAC_PROCESS_ID = "processId";
  String BAC_DEVICE_ID = "deviceId";
  String BAC_OBJECT_ID = "objectId";
  String BAC_TIMESTAMP = "BacnetTimestamp";
  String BAC_NOTIFICATION_CLASS = "NC";
  String BAC_EVENT_TYPE = "eventType";
  String BAC_EVENT_VALUES = "eventValues";
  String BAC_ACK_TIME = "ackTime";
  String BAC_PRIORITY = "priority";
  String BAC_ACK_REQUIRED = "bacnetAcksRequired";
  String BAC_NOTIFY_TYPE = "notifyType";
  String BAC_STALE_ACK = "staleAck";

  // Notification Parameters keys
  String BAC_REFERENCED_BITSTRING = "referencedBitstring";
  String BAC_STATUS_FLAGS = "statusFlags";
  String BAC_NEW_STATE = "newState";
  String BAC_NEW_VALUE = "newValue";             // duplicate with AlarmConst (ok?)
  String BAC_CHANGED_VALUE = "changedValue";
  String BAC_ALARM_VALUE = "alarmValue";
  String BAC_CHANGED_BITS = "changedBits";
  String BAC_COMMAND_VALUE = "commandValue";
  String BAC_FEEDBACK_VALUE = "feedbackValue";        // duplicate with AlarmConst (ok?)
  String BAC_REFERENCE_VALUE = "referenceValue";
  String BAC_SETPOINT_VALUE = "setpointValue";
  String BAC_ERROR_LIMIT = "errorLimit";           // duplicate with AlarmConst (ok?)
  String BAC_EXCEEDING_VALUE = "exceedingValue";
  String BAC_DEADBAND = "deadband";             // duplicate with AlarmConst (ok?)
  String BAC_EXCEEDED_LIMIT = "exceededLimit";
  String BAC_COMPLEX_EVENT_VALUE = "complexEventValue";
  String BAC_BUFFER_DEVICE = "bufferDevice";         // deprecated as of 135b-2001
  String BAC_BUFFER_OBJECT = "bufferObject";         // deprecated as of 135b-2001
  String BAC_PREVIOUS_NOTIFICATION = "previousNotification";
  String BAC_CURRENT_NOTIFICATION = "currentNotification";
  String BAC_NEW_MODE = "newMode";
  String BAC_OPERATION_EXPECTED = "operationExpected";
  String BAC_BUFFER_PROPERTY = "bufferProperty";
  String BAC_TIME_DELAY = "timeDelay";
  String BAC_LOW_LIMIT = "lowLimit";
  String BAC_HIGH_LIMIT = "highLimit";
  String BAC_LIFE_SAFETY_ALARM_VALUES = "listOfLifeSafetyAlarmValues";
  String BAC_ALARM_VALUES = "listOfLifeSafetyAlarmValues";
  String BAC_VENDOR_ID = "vendorId";
  String BAC_EXTENDED_EVENT_TYPE = "extendedEventType";
  String BAC_PARAMETERS = "parameters";
  String BAC_ACCESS_EVENT = "accessEvent";
  String BAC_ACCESS_EVENT_TAG = "accessEventTag";
  String BAC_ACCESS_EVENT_TIME = "accessEventTime";
  String BAC_ACCESS_CREDENTIAL = "accessCredential";
  String BAC_AUTH_FACTOR = "authFactor";
  String BAC_PRESENT_VALUE = "presentValue";
  String BAC_RELIABILITY = "reliability";
  String BAC_PROPERTY_VALUES = "propertyValues";

  /** User defined message text key */
  public static final String BAC_TO_OFFNORMAL_MSG_TEXT         = "toOffNormalMsgText";
  public static final String BAC_TO_FAULT_MSG_TEXT             = "toFaultMsgText";
  public static final String BAC_TO_NORMAL_MSG_TEXT            = "toNormalMsgText";

  // Info saved from alarm for ack processing
  String BAC_NORMAL_PRIORITY = "normalPriority";
  String BAC_FAULT_PRIORITY = "faultPriority";
  String BAC_OFFNORMAL_PRIORITY = "offnormalPriority";
  String BAC_OFFNORMAL_TO_STATE = "offnormalToState";
  String BAC_OFFNORMAL_ACKED = "offnormalAcked";
  String BAC_STATE_ACKED = "stateAcked";
  String BAC_DEST_PROC_ID_PREFIX = "procIdFor";
  String BAC_CONFIRMED_FLAG = "confirmed";
  String BAC_NOTIFY_LIST = "bacNotify";

  // Event Summary keys
  String BAC_ESUM_EVENT_STATE = "eventState";
  String BAC_ESUM_ACKED_TRANSITIONS = "ackedTransitions";
  String BAC_ESUM_EVENT_TIMESTAMPS = "eventTimeStamps";
  String BAC_ESUM_EVENT_ENABLE = "eventEnable";
  String BAC_ESUM_EVENT_PRIORITIES = "eventPriorities";

  // Event Transition Bits
  int TO_OFFNORMAL_BIT = 0x04;
  int TO_FAULT_BIT = 0x02;
  int TO_NORMAL_BIT = 0x01;
  int TO_OFFNORMAL_INDEX = 0;
  int TO_FAULT_INDEX = 1;
  int TO_NORMAL_INDEX = 2;
}