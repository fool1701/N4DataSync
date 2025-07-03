/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.InvalidEnumException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBacnetErrorCode represents the error-code portion of the
 * BACnet Error sequence.
 * <p>
 * BBacnetErrorCode is an "extensible" enumeration.
 * Values 0-255 are reserved for use by ASHRAE.
 * Values from 256-65535 (0xFFFF)
 * can be used for proprietary extensions.
 * <p>
 * Note that for proprietary extensions, a given ordinal is not
 * globally mapped to the same enumeration.  Type X from vendor
 * A will be different than type X from vendor B.  Extensions are
 * also not guaranteed unique within a vendor's own products, so
 * type Y in device A from vendor A will in general be different
 * than type Y in device B from vendor A.
 *
 * @author Craig Gemmill
 * @version $Revision: 7$ $Date: 12/19/01 4:35:58 PM$
 * @creation 10 Aug 00
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("other"),
    @Range("authenticationFailed"),
    @Range("configurationInProgress"),
    @Range("deviceBusy"),
    @Range("dynamicCreationNotSupported"),
    @Range("fileAccessDenied"),
    @Range("incompatibleSecurityLevels"),
    @Range("inconsistentParameters"),
    @Range("inconsistentSelectionCriterion"),
    @Range("invalidDataType"),
    @Range("invalidFileAccessMethod"),
    @Range("invalidFileStartPosition"),
    @Range("invalidOperatorName"),
    @Range("invalidParameterDataType"),
    @Range("invalidTimeStamp"),
    @Range("keyGenerationError"),
    @Range("missingRequiredParameter"),
    @Range("noObjectsOfSpecifiedType"),
    @Range("noSpaceForObject"),
    @Range("noSpaceToAddListElement"),
    @Range("noSpaceToWriteProperty"),
    @Range("noVtSessionsAvailable"),
    @Range("propertyIsNotA_List"),
    @Range("objectDeletionNotPermitted"),
    @Range("objectIdentifierAlreadyExists"),
    @Range("operationalProblem"),
    @Range("passwordFailure"),
    @Range("readAccessDenied"),
    @Range("securityNotSupported"),
    @Range("serviceRequestDenied"),
    @Range("timeout"),
    @Range("unknownObject"),
    @Range("unknownProperty"),
    @Range("removed"),
    @Range("unknownVtClass"),
    @Range("unknownVtSession"),
    @Range("unsupportedObjectType"),
    @Range("valueOutOfRange"),
    @Range("vtSessionAlreadyClosed"),
    @Range("vtSessionTerminationFailure"),
    @Range("writeAccessDenied"),
    @Range("characterSetNotSupported"),
    @Range("invalidArrayIndex"),
    @Range("covSubscriptionFailed"),
    @Range("notCovProperty"),
    @Range("optionalFunctionalityNotSupported"),
    @Range("invalidConfigurationData"),
    @Range("datatypeNotSupported"),
    @Range("duplicateName"),
    @Range("duplicateObjectId"),
    @Range("propertyIsNotAnArray"),
    @Range("abortBufferOverflow"),
    @Range("abortInvalidApduInThisState"),
    @Range("abortPreemptedByHigherPriorityTask"),
    @Range("abortSegmentationNotSupported"),
    @Range("abortProprietary"),
    @Range("abortOther"),
    @Range("invalidTag"),
    @Range("networkDown"),
    @Range("rejectBufferOverflow"),
    @Range("rejectInconsistentParameters"),
    @Range("rejectInvalidParameterDataType"),
    @Range("rejectInvalidTag"),
    @Range("rejectMissingRequiredParameter"),
    @Range("rejectParameterOutOfRange"),
    @Range("rejectTooManyArguments"),
    @Range("rejectUndefinedEnumeration"),
    @Range("rejectUnrecognizedService"),
    @Range("rejectProprietary"),
    @Range("rejectOther"),
    @Range("unknownDevice"),
    @Range("unknownRoute"),
    @Range("valueNotInitialized"),
    @Range("invalidEventState"),
    @Range("noAlarmConfigured"),
    @Range("logBufferFull"),
    @Range("loggedValuePurged"),
    @Range("noPropertySpecified"),
    @Range("notConfiguredForTriggeredLogging"),
    @Range("unknownSubscription"),
    @Range("parameterOutOfRange"),
    @Range("listElementNotFound"),
    @Range("busy"),
    @Range("communicationDisabled"),
    @Range("success"),
    @Range("accessDenied"),
    @Range("badDestinationAddress"),
    @Range("badDestinationDeviceId"),
    @Range("badSignature"),
    @Range("badSourceAddress"),
    @Range("badTimestamp"),
    @Range("cannotUseKey"),
    @Range("cannotVerifyMessageId"),
    @Range("correctKeyRevision"),
    @Range("destinationDeviceIdRequired"),
    @Range("duplicateMessage"),
    @Range("encryptionNotConfigured"),
    @Range("encryptionRequired"),
    @Range("incorrectKey"),
    @Range("invalidKeyData"),
    @Range("keyUpdateInProgress"),
    @Range("malformedMessage"),
    @Range("notKeyServer"),
    @Range("securityNotConfigured"),
    @Range("sourceSecurityRequired"),
    @Range("tooManyKeys"),
    @Range("unknownAuthenticationType"),
    @Range("unknownKey"),
    @Range("unknownKeyRevision"),
    @Range("unknownSourceMessage"),
    @Range("notRouterToDnet"),
    @Range("routerBusy"),
    @Range("unknownNetworkMessage"),
    @Range("messageTooLong"),
    @Range("securityError"),
    @Range("addressingError"),
    @Range("writeBdtFailed"),
    @Range("readBdtFailed"),
    @Range("registerForeignDeviceFailed"),
    @Range("readFdtFailed"),
    @Range("deleteFdtEntryFailed"),
    @Range("distributeBroadcastFailed"),
    @Range("unknownFileSize"),
    @Range("abortApduTooLong"),
    @Range("abortApplicationExceededReplyTime"),
    @Range("abortOutOfResources"),
    @Range("abortTsmTimeout"),
    @Range("abortWindowSizeOutOfRange"),
    @Range("fileFull"),
    @Range("inconsistentConfiguration"),
    @Range("inconsistentObjectType"),
    @Range("internalError"),
    @Range("notConfigured"),
    @Range("outOfMemory"),
    @Range("valueTooLong"),
    @Range("abortInsufficientSecurity"),
    @Range("abortSecurityError"),
    @Range("duplicateEntry"),
    @Range("invalidValueInThisState"),
    @Range("invalidOperationInThisState"),
    @Range("listItemNotNumbered"),
    @Range("listItemNotTimestamped"),
    @Range("invalidDataEncoding"),
    @Range("bvlcFunctionUnknown"),
    @Range("bvlcProprietaryFunctionUnknown"),
    @Range("headerEncodingError"),
    @Range("headerNotUnderstood"),
    @Range("messageIncomplete"),
    @Range("notA_BacnetScHub"),
    @Range("payloadExpected"),
    @Range("unexpectedData"),
    @Range("nodeDuplicateVmac"),
    @Range("httpUnexpectedResponseCode"),
    @Range("httpNoUpgrade"),
    @Range("httpResourceNotLocal"),
    @Range("httpProxyAuthenticationFailed"),
    @Range("httpResponseTimeout"),
    @Range("httpResponseSyntaxError"),
    @Range("httpResponseValueError"),
    @Range("httpResponseMissingHeader"),
    @Range("httpWebsocketHeaderError"),
    @Range("httpUpgradeRequired"),
    @Range("httpUpgradeError"),
    @Range("httpTemporaryUnavailable"),
    @Range("httpNotA_Server"),
    @Range("httpError"),
    @Range("websocketSchemeNotSupported"),
    @Range("websocketUnknownControlMessage"),
    @Range("websocketCloseError"),
    @Range("websocketClosedByPeer"),
    @Range("websocketEndpointLeaves"),
    @Range("websocketProtocolError"),
    @Range("websocketDataNotAccepted"),
    @Range("websocketClosedAbnormally"),
    @Range("websocketDataInconsistent"),
    @Range("websocketDataAgainstPolicy"),
    @Range("websocketFrameTooLong"),
    @Range("websocketExtensionMissing"),
    @Range("websocketRequestUnavailable"),
    @Range("websocketError"),
    @Range("tlsClientCertificateError"),
    @Range("tlsServerCertificateError"),
    @Range("tlsClientAuthenticationFailed"),
    @Range("tlsServerAuthenticationFailed"),
    @Range("tlsClientCertificateExpired"),
    @Range("tlsServerCertificateExpired"),
    @Range("tlsClientCertificateRevoked"),
    @Range("tlsServerCertificateRevoked"),
    @Range("tlsError"),
    @Range("dnsUnavailable"),
    @Range("dnsNameResolutionFailed"),
    @Range("dnsResolverFailure"),
    @Range("dnsError"),
    @Range("tcpConnectTimeout"),
    @Range("tcpConnectionRefused"),
    @Range("tcpClosedByLocal"),
    @Range("tcpClosedOther"),
    @Range("tcpError"),
    @Range("ipAddressNotReachable"),
    @Range("ipError"),
    @Range(value = "nullValueEvent", ordinal = 355)
  }
)
public final class BBacnetErrorCode
  extends BFrozenEnum
  implements BacnetConst
{
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetErrorCode(1200458025)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for other. */
  public static final int OTHER = 0;
  /** Ordinal value for authenticationFailed. */
  public static final int AUTHENTICATION_FAILED = 1;
  /** Ordinal value for configurationInProgress. */
  public static final int CONFIGURATION_IN_PROGRESS = 2;
  /** Ordinal value for deviceBusy. */
  public static final int DEVICE_BUSY = 3;
  /** Ordinal value for dynamicCreationNotSupported. */
  public static final int DYNAMIC_CREATION_NOT_SUPPORTED = 4;
  /** Ordinal value for fileAccessDenied. */
  public static final int FILE_ACCESS_DENIED = 5;
  /** Ordinal value for incompatibleSecurityLevels. */
  public static final int INCOMPATIBLE_SECURITY_LEVELS = 6;
  /** Ordinal value for inconsistentParameters. */
  public static final int INCONSISTENT_PARAMETERS = 7;
  /** Ordinal value for inconsistentSelectionCriterion. */
  public static final int INCONSISTENT_SELECTION_CRITERION = 8;
  /** Ordinal value for invalidDataType. */
  public static final int INVALID_DATA_TYPE = 9;
  /** Ordinal value for invalidFileAccessMethod. */
  public static final int INVALID_FILE_ACCESS_METHOD = 10;
  /** Ordinal value for invalidFileStartPosition. */
  public static final int INVALID_FILE_START_POSITION = 11;
  /** Ordinal value for invalidOperatorName. */
  public static final int INVALID_OPERATOR_NAME = 12;
  /** Ordinal value for invalidParameterDataType. */
  public static final int INVALID_PARAMETER_DATA_TYPE = 13;
  /** Ordinal value for invalidTimeStamp. */
  public static final int INVALID_TIME_STAMP = 14;
  /** Ordinal value for keyGenerationError. */
  public static final int KEY_GENERATION_ERROR = 15;
  /** Ordinal value for missingRequiredParameter. */
  public static final int MISSING_REQUIRED_PARAMETER = 16;
  /** Ordinal value for noObjectsOfSpecifiedType. */
  public static final int NO_OBJECTS_OF_SPECIFIED_TYPE = 17;
  /** Ordinal value for noSpaceForObject. */
  public static final int NO_SPACE_FOR_OBJECT = 18;
  /** Ordinal value for noSpaceToAddListElement. */
  public static final int NO_SPACE_TO_ADD_LIST_ELEMENT = 19;
  /** Ordinal value for noSpaceToWriteProperty. */
  public static final int NO_SPACE_TO_WRITE_PROPERTY = 20;
  /** Ordinal value for noVtSessionsAvailable. */
  public static final int NO_VT_SESSIONS_AVAILABLE = 21;
  /** Ordinal value for propertyIsNotA_List. */
  public static final int PROPERTY_IS_NOT_A_LIST = 22;
  /** Ordinal value for objectDeletionNotPermitted. */
  public static final int OBJECT_DELETION_NOT_PERMITTED = 23;
  /** Ordinal value for objectIdentifierAlreadyExists. */
  public static final int OBJECT_IDENTIFIER_ALREADY_EXISTS = 24;
  /** Ordinal value for operationalProblem. */
  public static final int OPERATIONAL_PROBLEM = 25;
  /** Ordinal value for passwordFailure. */
  public static final int PASSWORD_FAILURE = 26;
  /** Ordinal value for readAccessDenied. */
  public static final int READ_ACCESS_DENIED = 27;
  /** Ordinal value for securityNotSupported. */
  public static final int SECURITY_NOT_SUPPORTED = 28;
  /** Ordinal value for serviceRequestDenied. */
  public static final int SERVICE_REQUEST_DENIED = 29;
  /** Ordinal value for timeout. */
  public static final int TIMEOUT = 30;
  /** Ordinal value for unknownObject. */
  public static final int UNKNOWN_OBJECT = 31;
  /** Ordinal value for unknownProperty. */
  public static final int UNKNOWN_PROPERTY = 32;
  /** Ordinal value for removed. */
  public static final int REMOVED = 33;
  /** Ordinal value for unknownVtClass. */
  public static final int UNKNOWN_VT_CLASS = 34;
  /** Ordinal value for unknownVtSession. */
  public static final int UNKNOWN_VT_SESSION = 35;
  /** Ordinal value for unsupportedObjectType. */
  public static final int UNSUPPORTED_OBJECT_TYPE = 36;
  /** Ordinal value for valueOutOfRange. */
  public static final int VALUE_OUT_OF_RANGE = 37;
  /** Ordinal value for vtSessionAlreadyClosed. */
  public static final int VT_SESSION_ALREADY_CLOSED = 38;
  /** Ordinal value for vtSessionTerminationFailure. */
  public static final int VT_SESSION_TERMINATION_FAILURE = 39;
  /** Ordinal value for writeAccessDenied. */
  public static final int WRITE_ACCESS_DENIED = 40;
  /** Ordinal value for characterSetNotSupported. */
  public static final int CHARACTER_SET_NOT_SUPPORTED = 41;
  /** Ordinal value for invalidArrayIndex. */
  public static final int INVALID_ARRAY_INDEX = 42;
  /** Ordinal value for covSubscriptionFailed. */
  public static final int COV_SUBSCRIPTION_FAILED = 43;
  /** Ordinal value for notCovProperty. */
  public static final int NOT_COV_PROPERTY = 44;
  /** Ordinal value for optionalFunctionalityNotSupported. */
  public static final int OPTIONAL_FUNCTIONALITY_NOT_SUPPORTED = 45;
  /** Ordinal value for invalidConfigurationData. */
  public static final int INVALID_CONFIGURATION_DATA = 46;
  /** Ordinal value for datatypeNotSupported. */
  public static final int DATATYPE_NOT_SUPPORTED = 47;
  /** Ordinal value for duplicateName. */
  public static final int DUPLICATE_NAME = 48;
  /** Ordinal value for duplicateObjectId. */
  public static final int DUPLICATE_OBJECT_ID = 49;
  /** Ordinal value for propertyIsNotAnArray. */
  public static final int PROPERTY_IS_NOT_AN_ARRAY = 50;
  /** Ordinal value for abortBufferOverflow. */
  public static final int ABORT_BUFFER_OVERFLOW = 51;
  /** Ordinal value for abortInvalidApduInThisState. */
  public static final int ABORT_INVALID_APDU_IN_THIS_STATE = 52;
  /** Ordinal value for abortPreemptedByHigherPriorityTask. */
  public static final int ABORT_PREEMPTED_BY_HIGHER_PRIORITY_TASK = 53;
  /** Ordinal value for abortSegmentationNotSupported. */
  public static final int ABORT_SEGMENTATION_NOT_SUPPORTED = 54;
  /** Ordinal value for abortProprietary. */
  public static final int ABORT_PROPRIETARY = 55;
  /** Ordinal value for abortOther. */
  public static final int ABORT_OTHER = 56;
  /** Ordinal value for invalidTag. */
  public static final int INVALID_TAG = 57;
  /** Ordinal value for networkDown. */
  public static final int NETWORK_DOWN = 58;
  /** Ordinal value for rejectBufferOverflow. */
  public static final int REJECT_BUFFER_OVERFLOW = 59;
  /** Ordinal value for rejectInconsistentParameters. */
  public static final int REJECT_INCONSISTENT_PARAMETERS = 60;
  /** Ordinal value for rejectInvalidParameterDataType. */
  public static final int REJECT_INVALID_PARAMETER_DATA_TYPE = 61;
  /** Ordinal value for rejectInvalidTag. */
  public static final int REJECT_INVALID_TAG = 62;
  /** Ordinal value for rejectMissingRequiredParameter. */
  public static final int REJECT_MISSING_REQUIRED_PARAMETER = 63;
  /** Ordinal value for rejectParameterOutOfRange. */
  public static final int REJECT_PARAMETER_OUT_OF_RANGE = 64;
  /** Ordinal value for rejectTooManyArguments. */
  public static final int REJECT_TOO_MANY_ARGUMENTS = 65;
  /** Ordinal value for rejectUndefinedEnumeration. */
  public static final int REJECT_UNDEFINED_ENUMERATION = 66;
  /** Ordinal value for rejectUnrecognizedService. */
  public static final int REJECT_UNRECOGNIZED_SERVICE = 67;
  /** Ordinal value for rejectProprietary. */
  public static final int REJECT_PROPRIETARY = 68;
  /** Ordinal value for rejectOther. */
  public static final int REJECT_OTHER = 69;
  /** Ordinal value for unknownDevice. */
  public static final int UNKNOWN_DEVICE = 70;
  /** Ordinal value for unknownRoute. */
  public static final int UNKNOWN_ROUTE = 71;
  /** Ordinal value for valueNotInitialized. */
  public static final int VALUE_NOT_INITIALIZED = 72;
  /** Ordinal value for invalidEventState. */
  public static final int INVALID_EVENT_STATE = 73;
  /** Ordinal value for noAlarmConfigured. */
  public static final int NO_ALARM_CONFIGURED = 74;
  /** Ordinal value for logBufferFull. */
  public static final int LOG_BUFFER_FULL = 75;
  /** Ordinal value for loggedValuePurged. */
  public static final int LOGGED_VALUE_PURGED = 76;
  /** Ordinal value for noPropertySpecified. */
  public static final int NO_PROPERTY_SPECIFIED = 77;
  /** Ordinal value for notConfiguredForTriggeredLogging. */
  public static final int NOT_CONFIGURED_FOR_TRIGGERED_LOGGING = 78;
  /** Ordinal value for unknownSubscription. */
  public static final int UNKNOWN_SUBSCRIPTION = 79;
  /** Ordinal value for parameterOutOfRange. */
  public static final int PARAMETER_OUT_OF_RANGE = 80;
  /** Ordinal value for listElementNotFound. */
  public static final int LIST_ELEMENT_NOT_FOUND = 81;
  /** Ordinal value for busy. */
  public static final int BUSY = 82;
  /** Ordinal value for communicationDisabled. */
  public static final int COMMUNICATION_DISABLED = 83;
  /** Ordinal value for success. */
  public static final int SUCCESS = 84;
  /** Ordinal value for accessDenied. */
  public static final int ACCESS_DENIED = 85;
  /** Ordinal value for badDestinationAddress. */
  public static final int BAD_DESTINATION_ADDRESS = 86;
  /** Ordinal value for badDestinationDeviceId. */
  public static final int BAD_DESTINATION_DEVICE_ID = 87;
  /** Ordinal value for badSignature. */
  public static final int BAD_SIGNATURE = 88;
  /** Ordinal value for badSourceAddress. */
  public static final int BAD_SOURCE_ADDRESS = 89;
  /** Ordinal value for badTimestamp. */
  public static final int BAD_TIMESTAMP = 90;
  /** Ordinal value for cannotUseKey. */
  public static final int CANNOT_USE_KEY = 91;
  /** Ordinal value for cannotVerifyMessageId. */
  public static final int CANNOT_VERIFY_MESSAGE_ID = 92;
  /** Ordinal value for correctKeyRevision. */
  public static final int CORRECT_KEY_REVISION = 93;
  /** Ordinal value for destinationDeviceIdRequired. */
  public static final int DESTINATION_DEVICE_ID_REQUIRED = 94;
  /** Ordinal value for duplicateMessage. */
  public static final int DUPLICATE_MESSAGE = 95;
  /** Ordinal value for encryptionNotConfigured. */
  public static final int ENCRYPTION_NOT_CONFIGURED = 96;
  /** Ordinal value for encryptionRequired. */
  public static final int ENCRYPTION_REQUIRED = 97;
  /** Ordinal value for incorrectKey. */
  public static final int INCORRECT_KEY = 98;
  /** Ordinal value for invalidKeyData. */
  public static final int INVALID_KEY_DATA = 99;
  /** Ordinal value for keyUpdateInProgress. */
  public static final int KEY_UPDATE_IN_PROGRESS = 100;
  /** Ordinal value for malformedMessage. */
  public static final int MALFORMED_MESSAGE = 101;
  /** Ordinal value for notKeyServer. */
  public static final int NOT_KEY_SERVER = 102;
  /** Ordinal value for securityNotConfigured. */
  public static final int SECURITY_NOT_CONFIGURED = 103;
  /** Ordinal value for sourceSecurityRequired. */
  public static final int SOURCE_SECURITY_REQUIRED = 104;
  /** Ordinal value for tooManyKeys. */
  public static final int TOO_MANY_KEYS = 105;
  /** Ordinal value for unknownAuthenticationType. */
  public static final int UNKNOWN_AUTHENTICATION_TYPE = 106;
  /** Ordinal value for unknownKey. */
  public static final int UNKNOWN_KEY = 107;
  /** Ordinal value for unknownKeyRevision. */
  public static final int UNKNOWN_KEY_REVISION = 108;
  /** Ordinal value for unknownSourceMessage. */
  public static final int UNKNOWN_SOURCE_MESSAGE = 109;
  /** Ordinal value for notRouterToDnet. */
  public static final int NOT_ROUTER_TO_DNET = 110;
  /** Ordinal value for routerBusy. */
  public static final int ROUTER_BUSY = 111;
  /** Ordinal value for unknownNetworkMessage. */
  public static final int UNKNOWN_NETWORK_MESSAGE = 112;
  /** Ordinal value for messageTooLong. */
  public static final int MESSAGE_TOO_LONG = 113;
  /** Ordinal value for securityError. */
  public static final int SECURITY_ERROR = 114;
  /** Ordinal value for addressingError. */
  public static final int ADDRESSING_ERROR = 115;
  /** Ordinal value for writeBdtFailed. */
  public static final int WRITE_BDT_FAILED = 116;
  /** Ordinal value for readBdtFailed. */
  public static final int READ_BDT_FAILED = 117;
  /** Ordinal value for registerForeignDeviceFailed. */
  public static final int REGISTER_FOREIGN_DEVICE_FAILED = 118;
  /** Ordinal value for readFdtFailed. */
  public static final int READ_FDT_FAILED = 119;
  /** Ordinal value for deleteFdtEntryFailed. */
  public static final int DELETE_FDT_ENTRY_FAILED = 120;
  /** Ordinal value for distributeBroadcastFailed. */
  public static final int DISTRIBUTE_BROADCAST_FAILED = 121;
  /** Ordinal value for unknownFileSize. */
  public static final int UNKNOWN_FILE_SIZE = 122;
  /** Ordinal value for abortApduTooLong. */
  public static final int ABORT_APDU_TOO_LONG = 123;
  /** Ordinal value for abortApplicationExceededReplyTime. */
  public static final int ABORT_APPLICATION_EXCEEDED_REPLY_TIME = 124;
  /** Ordinal value for abortOutOfResources. */
  public static final int ABORT_OUT_OF_RESOURCES = 125;
  /** Ordinal value for abortTsmTimeout. */
  public static final int ABORT_TSM_TIMEOUT = 126;
  /** Ordinal value for abortWindowSizeOutOfRange. */
  public static final int ABORT_WINDOW_SIZE_OUT_OF_RANGE = 127;
  /** Ordinal value for fileFull. */
  public static final int FILE_FULL = 128;
  /** Ordinal value for inconsistentConfiguration. */
  public static final int INCONSISTENT_CONFIGURATION = 129;
  /** Ordinal value for inconsistentObjectType. */
  public static final int INCONSISTENT_OBJECT_TYPE = 130;
  /** Ordinal value for internalError. */
  public static final int INTERNAL_ERROR = 131;
  /** Ordinal value for notConfigured. */
  public static final int NOT_CONFIGURED = 132;
  /** Ordinal value for outOfMemory. */
  public static final int OUT_OF_MEMORY = 133;
  /** Ordinal value for valueTooLong. */
  public static final int VALUE_TOO_LONG = 134;
  /** Ordinal value for abortInsufficientSecurity. */
  public static final int ABORT_INSUFFICIENT_SECURITY = 135;
  /** Ordinal value for abortSecurityError. */
  public static final int ABORT_SECURITY_ERROR = 136;
  /** Ordinal value for duplicateEntry. */
  public static final int DUPLICATE_ENTRY = 137;
  /** Ordinal value for invalidValueInThisState. */
  public static final int INVALID_VALUE_IN_THIS_STATE = 138;
  /** Ordinal value for invalidOperationInThisState. */
  public static final int INVALID_OPERATION_IN_THIS_STATE = 139;
  /** Ordinal value for listItemNotNumbered. */
  public static final int LIST_ITEM_NOT_NUMBERED = 140;
  /** Ordinal value for listItemNotTimestamped. */
  public static final int LIST_ITEM_NOT_TIMESTAMPED = 141;
  /** Ordinal value for invalidDataEncoding. */
  public static final int INVALID_DATA_ENCODING = 142;
  /** Ordinal value for bvlcFunctionUnknown. */
  public static final int BVLC_FUNCTION_UNKNOWN = 143;
  /** Ordinal value for bvlcProprietaryFunctionUnknown. */
  public static final int BVLC_PROPRIETARY_FUNCTION_UNKNOWN = 144;
  /** Ordinal value for headerEncodingError. */
  public static final int HEADER_ENCODING_ERROR = 145;
  /** Ordinal value for headerNotUnderstood. */
  public static final int HEADER_NOT_UNDERSTOOD = 146;
  /** Ordinal value for messageIncomplete. */
  public static final int MESSAGE_INCOMPLETE = 147;
  /** Ordinal value for notA_BacnetScHub. */
  public static final int NOT_A_BACNET_SC_HUB = 148;
  /** Ordinal value for payloadExpected. */
  public static final int PAYLOAD_EXPECTED = 149;
  /** Ordinal value for unexpectedData. */
  public static final int UNEXPECTED_DATA = 150;
  /** Ordinal value for nodeDuplicateVmac. */
  public static final int NODE_DUPLICATE_VMAC = 151;
  /** Ordinal value for httpUnexpectedResponseCode. */
  public static final int HTTP_UNEXPECTED_RESPONSE_CODE = 152;
  /** Ordinal value for httpNoUpgrade. */
  public static final int HTTP_NO_UPGRADE = 153;
  /** Ordinal value for httpResourceNotLocal. */
  public static final int HTTP_RESOURCE_NOT_LOCAL = 154;
  /** Ordinal value for httpProxyAuthenticationFailed. */
  public static final int HTTP_PROXY_AUTHENTICATION_FAILED = 155;
  /** Ordinal value for httpResponseTimeout. */
  public static final int HTTP_RESPONSE_TIMEOUT = 156;
  /** Ordinal value for httpResponseSyntaxError. */
  public static final int HTTP_RESPONSE_SYNTAX_ERROR = 157;
  /** Ordinal value for httpResponseValueError. */
  public static final int HTTP_RESPONSE_VALUE_ERROR = 158;
  /** Ordinal value for httpResponseMissingHeader. */
  public static final int HTTP_RESPONSE_MISSING_HEADER = 159;
  /** Ordinal value for httpWebsocketHeaderError. */
  public static final int HTTP_WEBSOCKET_HEADER_ERROR = 160;
  /** Ordinal value for httpUpgradeRequired. */
  public static final int HTTP_UPGRADE_REQUIRED = 161;
  /** Ordinal value for httpUpgradeError. */
  public static final int HTTP_UPGRADE_ERROR = 162;
  /** Ordinal value for httpTemporaryUnavailable. */
  public static final int HTTP_TEMPORARY_UNAVAILABLE = 163;
  /** Ordinal value for httpNotA_Server. */
  public static final int HTTP_NOT_A_SERVER = 164;
  /** Ordinal value for httpError. */
  public static final int HTTP_ERROR = 165;
  /** Ordinal value for websocketSchemeNotSupported. */
  public static final int WEBSOCKET_SCHEME_NOT_SUPPORTED = 166;
  /** Ordinal value for websocketUnknownControlMessage. */
  public static final int WEBSOCKET_UNKNOWN_CONTROL_MESSAGE = 167;
  /** Ordinal value for websocketCloseError. */
  public static final int WEBSOCKET_CLOSE_ERROR = 168;
  /** Ordinal value for websocketClosedByPeer. */
  public static final int WEBSOCKET_CLOSED_BY_PEER = 169;
  /** Ordinal value for websocketEndpointLeaves. */
  public static final int WEBSOCKET_ENDPOINT_LEAVES = 170;
  /** Ordinal value for websocketProtocolError. */
  public static final int WEBSOCKET_PROTOCOL_ERROR = 171;
  /** Ordinal value for websocketDataNotAccepted. */
  public static final int WEBSOCKET_DATA_NOT_ACCEPTED = 172;
  /** Ordinal value for websocketClosedAbnormally. */
  public static final int WEBSOCKET_CLOSED_ABNORMALLY = 173;
  /** Ordinal value for websocketDataInconsistent. */
  public static final int WEBSOCKET_DATA_INCONSISTENT = 174;
  /** Ordinal value for websocketDataAgainstPolicy. */
  public static final int WEBSOCKET_DATA_AGAINST_POLICY = 175;
  /** Ordinal value for websocketFrameTooLong. */
  public static final int WEBSOCKET_FRAME_TOO_LONG = 176;
  /** Ordinal value for websocketExtensionMissing. */
  public static final int WEBSOCKET_EXTENSION_MISSING = 177;
  /** Ordinal value for websocketRequestUnavailable. */
  public static final int WEBSOCKET_REQUEST_UNAVAILABLE = 178;
  /** Ordinal value for websocketError. */
  public static final int WEBSOCKET_ERROR = 179;
  /** Ordinal value for tlsClientCertificateError. */
  public static final int TLS_CLIENT_CERTIFICATE_ERROR = 180;
  /** Ordinal value for tlsServerCertificateError. */
  public static final int TLS_SERVER_CERTIFICATE_ERROR = 181;
  /** Ordinal value for tlsClientAuthenticationFailed. */
  public static final int TLS_CLIENT_AUTHENTICATION_FAILED = 182;
  /** Ordinal value for tlsServerAuthenticationFailed. */
  public static final int TLS_SERVER_AUTHENTICATION_FAILED = 183;
  /** Ordinal value for tlsClientCertificateExpired. */
  public static final int TLS_CLIENT_CERTIFICATE_EXPIRED = 184;
  /** Ordinal value for tlsServerCertificateExpired. */
  public static final int TLS_SERVER_CERTIFICATE_EXPIRED = 185;
  /** Ordinal value for tlsClientCertificateRevoked. */
  public static final int TLS_CLIENT_CERTIFICATE_REVOKED = 186;
  /** Ordinal value for tlsServerCertificateRevoked. */
  public static final int TLS_SERVER_CERTIFICATE_REVOKED = 187;
  /** Ordinal value for tlsError. */
  public static final int TLS_ERROR = 188;
  /** Ordinal value for dnsUnavailable. */
  public static final int DNS_UNAVAILABLE = 189;
  /** Ordinal value for dnsNameResolutionFailed. */
  public static final int DNS_NAME_RESOLUTION_FAILED = 190;
  /** Ordinal value for dnsResolverFailure. */
  public static final int DNS_RESOLVER_FAILURE = 191;
  /** Ordinal value for dnsError. */
  public static final int DNS_ERROR = 192;
  /** Ordinal value for tcpConnectTimeout. */
  public static final int TCP_CONNECT_TIMEOUT = 193;
  /** Ordinal value for tcpConnectionRefused. */
  public static final int TCP_CONNECTION_REFUSED = 194;
  /** Ordinal value for tcpClosedByLocal. */
  public static final int TCP_CLOSED_BY_LOCAL = 195;
  /** Ordinal value for tcpClosedOther. */
  public static final int TCP_CLOSED_OTHER = 196;
  /** Ordinal value for tcpError. */
  public static final int TCP_ERROR = 197;
  /** Ordinal value for ipAddressNotReachable. */
  public static final int IP_ADDRESS_NOT_REACHABLE = 198;
  /** Ordinal value for ipError. */
  public static final int IP_ERROR = 199;
  /** Ordinal value for nullValueEvent. */
  public static final int NULL_VALUE_EVENT = 355;

  /** BBacnetErrorCode constant for other. */
  public static final BBacnetErrorCode other = new BBacnetErrorCode(OTHER);
  /** BBacnetErrorCode constant for authenticationFailed. */
  public static final BBacnetErrorCode authenticationFailed = new BBacnetErrorCode(AUTHENTICATION_FAILED);
  /** BBacnetErrorCode constant for configurationInProgress. */
  public static final BBacnetErrorCode configurationInProgress = new BBacnetErrorCode(CONFIGURATION_IN_PROGRESS);
  /** BBacnetErrorCode constant for deviceBusy. */
  public static final BBacnetErrorCode deviceBusy = new BBacnetErrorCode(DEVICE_BUSY);
  /** BBacnetErrorCode constant for dynamicCreationNotSupported. */
  public static final BBacnetErrorCode dynamicCreationNotSupported = new BBacnetErrorCode(DYNAMIC_CREATION_NOT_SUPPORTED);
  /** BBacnetErrorCode constant for fileAccessDenied. */
  public static final BBacnetErrorCode fileAccessDenied = new BBacnetErrorCode(FILE_ACCESS_DENIED);
  /** BBacnetErrorCode constant for incompatibleSecurityLevels. */
  public static final BBacnetErrorCode incompatibleSecurityLevels = new BBacnetErrorCode(INCOMPATIBLE_SECURITY_LEVELS);
  /** BBacnetErrorCode constant for inconsistentParameters. */
  public static final BBacnetErrorCode inconsistentParameters = new BBacnetErrorCode(INCONSISTENT_PARAMETERS);
  /** BBacnetErrorCode constant for inconsistentSelectionCriterion. */
  public static final BBacnetErrorCode inconsistentSelectionCriterion = new BBacnetErrorCode(INCONSISTENT_SELECTION_CRITERION);
  /** BBacnetErrorCode constant for invalidDataType. */
  public static final BBacnetErrorCode invalidDataType = new BBacnetErrorCode(INVALID_DATA_TYPE);
  /** BBacnetErrorCode constant for invalidFileAccessMethod. */
  public static final BBacnetErrorCode invalidFileAccessMethod = new BBacnetErrorCode(INVALID_FILE_ACCESS_METHOD);
  /** BBacnetErrorCode constant for invalidFileStartPosition. */
  public static final BBacnetErrorCode invalidFileStartPosition = new BBacnetErrorCode(INVALID_FILE_START_POSITION);
  /** BBacnetErrorCode constant for invalidOperatorName. */
  public static final BBacnetErrorCode invalidOperatorName = new BBacnetErrorCode(INVALID_OPERATOR_NAME);
  /** BBacnetErrorCode constant for invalidParameterDataType. */
  public static final BBacnetErrorCode invalidParameterDataType = new BBacnetErrorCode(INVALID_PARAMETER_DATA_TYPE);
  /** BBacnetErrorCode constant for invalidTimeStamp. */
  public static final BBacnetErrorCode invalidTimeStamp = new BBacnetErrorCode(INVALID_TIME_STAMP);
  /** BBacnetErrorCode constant for keyGenerationError. */
  public static final BBacnetErrorCode keyGenerationError = new BBacnetErrorCode(KEY_GENERATION_ERROR);
  /** BBacnetErrorCode constant for missingRequiredParameter. */
  public static final BBacnetErrorCode missingRequiredParameter = new BBacnetErrorCode(MISSING_REQUIRED_PARAMETER);
  /** BBacnetErrorCode constant for noObjectsOfSpecifiedType. */
  public static final BBacnetErrorCode noObjectsOfSpecifiedType = new BBacnetErrorCode(NO_OBJECTS_OF_SPECIFIED_TYPE);
  /** BBacnetErrorCode constant for noSpaceForObject. */
  public static final BBacnetErrorCode noSpaceForObject = new BBacnetErrorCode(NO_SPACE_FOR_OBJECT);
  /** BBacnetErrorCode constant for noSpaceToAddListElement. */
  public static final BBacnetErrorCode noSpaceToAddListElement = new BBacnetErrorCode(NO_SPACE_TO_ADD_LIST_ELEMENT);
  /** BBacnetErrorCode constant for noSpaceToWriteProperty. */
  public static final BBacnetErrorCode noSpaceToWriteProperty = new BBacnetErrorCode(NO_SPACE_TO_WRITE_PROPERTY);
  /** BBacnetErrorCode constant for noVtSessionsAvailable. */
  public static final BBacnetErrorCode noVtSessionsAvailable = new BBacnetErrorCode(NO_VT_SESSIONS_AVAILABLE);
  /** BBacnetErrorCode constant for propertyIsNotA_List. */
  public static final BBacnetErrorCode propertyIsNotA_List = new BBacnetErrorCode(PROPERTY_IS_NOT_A_LIST);
  /** BBacnetErrorCode constant for objectDeletionNotPermitted. */
  public static final BBacnetErrorCode objectDeletionNotPermitted = new BBacnetErrorCode(OBJECT_DELETION_NOT_PERMITTED);
  /** BBacnetErrorCode constant for objectIdentifierAlreadyExists. */
  public static final BBacnetErrorCode objectIdentifierAlreadyExists = new BBacnetErrorCode(OBJECT_IDENTIFIER_ALREADY_EXISTS);
  /** BBacnetErrorCode constant for operationalProblem. */
  public static final BBacnetErrorCode operationalProblem = new BBacnetErrorCode(OPERATIONAL_PROBLEM);
  /** BBacnetErrorCode constant for passwordFailure. */
  public static final BBacnetErrorCode passwordFailure = new BBacnetErrorCode(PASSWORD_FAILURE);
  /** BBacnetErrorCode constant for readAccessDenied. */
  public static final BBacnetErrorCode readAccessDenied = new BBacnetErrorCode(READ_ACCESS_DENIED);
  /** BBacnetErrorCode constant for securityNotSupported. */
  public static final BBacnetErrorCode securityNotSupported = new BBacnetErrorCode(SECURITY_NOT_SUPPORTED);
  /** BBacnetErrorCode constant for serviceRequestDenied. */
  public static final BBacnetErrorCode serviceRequestDenied = new BBacnetErrorCode(SERVICE_REQUEST_DENIED);
  /** BBacnetErrorCode constant for timeout. */
  public static final BBacnetErrorCode timeout = new BBacnetErrorCode(TIMEOUT);
  /** BBacnetErrorCode constant for unknownObject. */
  public static final BBacnetErrorCode unknownObject = new BBacnetErrorCode(UNKNOWN_OBJECT);
  /** BBacnetErrorCode constant for unknownProperty. */
  public static final BBacnetErrorCode unknownProperty = new BBacnetErrorCode(UNKNOWN_PROPERTY);
  /** BBacnetErrorCode constant for removed. */
  public static final BBacnetErrorCode removed = new BBacnetErrorCode(REMOVED);
  /** BBacnetErrorCode constant for unknownVtClass. */
  public static final BBacnetErrorCode unknownVtClass = new BBacnetErrorCode(UNKNOWN_VT_CLASS);
  /** BBacnetErrorCode constant for unknownVtSession. */
  public static final BBacnetErrorCode unknownVtSession = new BBacnetErrorCode(UNKNOWN_VT_SESSION);
  /** BBacnetErrorCode constant for unsupportedObjectType. */
  public static final BBacnetErrorCode unsupportedObjectType = new BBacnetErrorCode(UNSUPPORTED_OBJECT_TYPE);
  /** BBacnetErrorCode constant for valueOutOfRange. */
  public static final BBacnetErrorCode valueOutOfRange = new BBacnetErrorCode(VALUE_OUT_OF_RANGE);
  /** BBacnetErrorCode constant for vtSessionAlreadyClosed. */
  public static final BBacnetErrorCode vtSessionAlreadyClosed = new BBacnetErrorCode(VT_SESSION_ALREADY_CLOSED);
  /** BBacnetErrorCode constant for vtSessionTerminationFailure. */
  public static final BBacnetErrorCode vtSessionTerminationFailure = new BBacnetErrorCode(VT_SESSION_TERMINATION_FAILURE);
  /** BBacnetErrorCode constant for writeAccessDenied. */
  public static final BBacnetErrorCode writeAccessDenied = new BBacnetErrorCode(WRITE_ACCESS_DENIED);
  /** BBacnetErrorCode constant for characterSetNotSupported. */
  public static final BBacnetErrorCode characterSetNotSupported = new BBacnetErrorCode(CHARACTER_SET_NOT_SUPPORTED);
  /** BBacnetErrorCode constant for invalidArrayIndex. */
  public static final BBacnetErrorCode invalidArrayIndex = new BBacnetErrorCode(INVALID_ARRAY_INDEX);
  /** BBacnetErrorCode constant for covSubscriptionFailed. */
  public static final BBacnetErrorCode covSubscriptionFailed = new BBacnetErrorCode(COV_SUBSCRIPTION_FAILED);
  /** BBacnetErrorCode constant for notCovProperty. */
  public static final BBacnetErrorCode notCovProperty = new BBacnetErrorCode(NOT_COV_PROPERTY);
  /** BBacnetErrorCode constant for optionalFunctionalityNotSupported. */
  public static final BBacnetErrorCode optionalFunctionalityNotSupported = new BBacnetErrorCode(OPTIONAL_FUNCTIONALITY_NOT_SUPPORTED);
  /** BBacnetErrorCode constant for invalidConfigurationData. */
  public static final BBacnetErrorCode invalidConfigurationData = new BBacnetErrorCode(INVALID_CONFIGURATION_DATA);
  /** BBacnetErrorCode constant for datatypeNotSupported. */
  public static final BBacnetErrorCode datatypeNotSupported = new BBacnetErrorCode(DATATYPE_NOT_SUPPORTED);
  /** BBacnetErrorCode constant for duplicateName. */
  public static final BBacnetErrorCode duplicateName = new BBacnetErrorCode(DUPLICATE_NAME);
  /** BBacnetErrorCode constant for duplicateObjectId. */
  public static final BBacnetErrorCode duplicateObjectId = new BBacnetErrorCode(DUPLICATE_OBJECT_ID);
  /** BBacnetErrorCode constant for propertyIsNotAnArray. */
  public static final BBacnetErrorCode propertyIsNotAnArray = new BBacnetErrorCode(PROPERTY_IS_NOT_AN_ARRAY);
  /** BBacnetErrorCode constant for abortBufferOverflow. */
  public static final BBacnetErrorCode abortBufferOverflow = new BBacnetErrorCode(ABORT_BUFFER_OVERFLOW);
  /** BBacnetErrorCode constant for abortInvalidApduInThisState. */
  public static final BBacnetErrorCode abortInvalidApduInThisState = new BBacnetErrorCode(ABORT_INVALID_APDU_IN_THIS_STATE);
  /** BBacnetErrorCode constant for abortPreemptedByHigherPriorityTask. */
  public static final BBacnetErrorCode abortPreemptedByHigherPriorityTask = new BBacnetErrorCode(ABORT_PREEMPTED_BY_HIGHER_PRIORITY_TASK);
  /** BBacnetErrorCode constant for abortSegmentationNotSupported. */
  public static final BBacnetErrorCode abortSegmentationNotSupported = new BBacnetErrorCode(ABORT_SEGMENTATION_NOT_SUPPORTED);
  /** BBacnetErrorCode constant for abortProprietary. */
  public static final BBacnetErrorCode abortProprietary = new BBacnetErrorCode(ABORT_PROPRIETARY);
  /** BBacnetErrorCode constant for abortOther. */
  public static final BBacnetErrorCode abortOther = new BBacnetErrorCode(ABORT_OTHER);
  /** BBacnetErrorCode constant for invalidTag. */
  public static final BBacnetErrorCode invalidTag = new BBacnetErrorCode(INVALID_TAG);
  /** BBacnetErrorCode constant for networkDown. */
  public static final BBacnetErrorCode networkDown = new BBacnetErrorCode(NETWORK_DOWN);
  /** BBacnetErrorCode constant for rejectBufferOverflow. */
  public static final BBacnetErrorCode rejectBufferOverflow = new BBacnetErrorCode(REJECT_BUFFER_OVERFLOW);
  /** BBacnetErrorCode constant for rejectInconsistentParameters. */
  public static final BBacnetErrorCode rejectInconsistentParameters = new BBacnetErrorCode(REJECT_INCONSISTENT_PARAMETERS);
  /** BBacnetErrorCode constant for rejectInvalidParameterDataType. */
  public static final BBacnetErrorCode rejectInvalidParameterDataType = new BBacnetErrorCode(REJECT_INVALID_PARAMETER_DATA_TYPE);
  /** BBacnetErrorCode constant for rejectInvalidTag. */
  public static final BBacnetErrorCode rejectInvalidTag = new BBacnetErrorCode(REJECT_INVALID_TAG);
  /** BBacnetErrorCode constant for rejectMissingRequiredParameter. */
  public static final BBacnetErrorCode rejectMissingRequiredParameter = new BBacnetErrorCode(REJECT_MISSING_REQUIRED_PARAMETER);
  /** BBacnetErrorCode constant for rejectParameterOutOfRange. */
  public static final BBacnetErrorCode rejectParameterOutOfRange = new BBacnetErrorCode(REJECT_PARAMETER_OUT_OF_RANGE);
  /** BBacnetErrorCode constant for rejectTooManyArguments. */
  public static final BBacnetErrorCode rejectTooManyArguments = new BBacnetErrorCode(REJECT_TOO_MANY_ARGUMENTS);
  /** BBacnetErrorCode constant for rejectUndefinedEnumeration. */
  public static final BBacnetErrorCode rejectUndefinedEnumeration = new BBacnetErrorCode(REJECT_UNDEFINED_ENUMERATION);
  /** BBacnetErrorCode constant for rejectUnrecognizedService. */
  public static final BBacnetErrorCode rejectUnrecognizedService = new BBacnetErrorCode(REJECT_UNRECOGNIZED_SERVICE);
  /** BBacnetErrorCode constant for rejectProprietary. */
  public static final BBacnetErrorCode rejectProprietary = new BBacnetErrorCode(REJECT_PROPRIETARY);
  /** BBacnetErrorCode constant for rejectOther. */
  public static final BBacnetErrorCode rejectOther = new BBacnetErrorCode(REJECT_OTHER);
  /** BBacnetErrorCode constant for unknownDevice. */
  public static final BBacnetErrorCode unknownDevice = new BBacnetErrorCode(UNKNOWN_DEVICE);
  /** BBacnetErrorCode constant for unknownRoute. */
  public static final BBacnetErrorCode unknownRoute = new BBacnetErrorCode(UNKNOWN_ROUTE);
  /** BBacnetErrorCode constant for valueNotInitialized. */
  public static final BBacnetErrorCode valueNotInitialized = new BBacnetErrorCode(VALUE_NOT_INITIALIZED);
  /** BBacnetErrorCode constant for invalidEventState. */
  public static final BBacnetErrorCode invalidEventState = new BBacnetErrorCode(INVALID_EVENT_STATE);
  /** BBacnetErrorCode constant for noAlarmConfigured. */
  public static final BBacnetErrorCode noAlarmConfigured = new BBacnetErrorCode(NO_ALARM_CONFIGURED);
  /** BBacnetErrorCode constant for logBufferFull. */
  public static final BBacnetErrorCode logBufferFull = new BBacnetErrorCode(LOG_BUFFER_FULL);
  /** BBacnetErrorCode constant for loggedValuePurged. */
  public static final BBacnetErrorCode loggedValuePurged = new BBacnetErrorCode(LOGGED_VALUE_PURGED);
  /** BBacnetErrorCode constant for noPropertySpecified. */
  public static final BBacnetErrorCode noPropertySpecified = new BBacnetErrorCode(NO_PROPERTY_SPECIFIED);
  /** BBacnetErrorCode constant for notConfiguredForTriggeredLogging. */
  public static final BBacnetErrorCode notConfiguredForTriggeredLogging = new BBacnetErrorCode(NOT_CONFIGURED_FOR_TRIGGERED_LOGGING);
  /** BBacnetErrorCode constant for unknownSubscription. */
  public static final BBacnetErrorCode unknownSubscription = new BBacnetErrorCode(UNKNOWN_SUBSCRIPTION);
  /** BBacnetErrorCode constant for parameterOutOfRange. */
  public static final BBacnetErrorCode parameterOutOfRange = new BBacnetErrorCode(PARAMETER_OUT_OF_RANGE);
  /** BBacnetErrorCode constant for listElementNotFound. */
  public static final BBacnetErrorCode listElementNotFound = new BBacnetErrorCode(LIST_ELEMENT_NOT_FOUND);
  /** BBacnetErrorCode constant for busy. */
  public static final BBacnetErrorCode busy = new BBacnetErrorCode(BUSY);
  /** BBacnetErrorCode constant for communicationDisabled. */
  public static final BBacnetErrorCode communicationDisabled = new BBacnetErrorCode(COMMUNICATION_DISABLED);
  /** BBacnetErrorCode constant for success. */
  public static final BBacnetErrorCode success = new BBacnetErrorCode(SUCCESS);
  /** BBacnetErrorCode constant for accessDenied. */
  public static final BBacnetErrorCode accessDenied = new BBacnetErrorCode(ACCESS_DENIED);
  /** BBacnetErrorCode constant for badDestinationAddress. */
  public static final BBacnetErrorCode badDestinationAddress = new BBacnetErrorCode(BAD_DESTINATION_ADDRESS);
  /** BBacnetErrorCode constant for badDestinationDeviceId. */
  public static final BBacnetErrorCode badDestinationDeviceId = new BBacnetErrorCode(BAD_DESTINATION_DEVICE_ID);
  /** BBacnetErrorCode constant for badSignature. */
  public static final BBacnetErrorCode badSignature = new BBacnetErrorCode(BAD_SIGNATURE);
  /** BBacnetErrorCode constant for badSourceAddress. */
  public static final BBacnetErrorCode badSourceAddress = new BBacnetErrorCode(BAD_SOURCE_ADDRESS);
  /** BBacnetErrorCode constant for badTimestamp. */
  public static final BBacnetErrorCode badTimestamp = new BBacnetErrorCode(BAD_TIMESTAMP);
  /** BBacnetErrorCode constant for cannotUseKey. */
  public static final BBacnetErrorCode cannotUseKey = new BBacnetErrorCode(CANNOT_USE_KEY);
  /** BBacnetErrorCode constant for cannotVerifyMessageId. */
  public static final BBacnetErrorCode cannotVerifyMessageId = new BBacnetErrorCode(CANNOT_VERIFY_MESSAGE_ID);
  /** BBacnetErrorCode constant for correctKeyRevision. */
  public static final BBacnetErrorCode correctKeyRevision = new BBacnetErrorCode(CORRECT_KEY_REVISION);
  /** BBacnetErrorCode constant for destinationDeviceIdRequired. */
  public static final BBacnetErrorCode destinationDeviceIdRequired = new BBacnetErrorCode(DESTINATION_DEVICE_ID_REQUIRED);
  /** BBacnetErrorCode constant for duplicateMessage. */
  public static final BBacnetErrorCode duplicateMessage = new BBacnetErrorCode(DUPLICATE_MESSAGE);
  /** BBacnetErrorCode constant for encryptionNotConfigured. */
  public static final BBacnetErrorCode encryptionNotConfigured = new BBacnetErrorCode(ENCRYPTION_NOT_CONFIGURED);
  /** BBacnetErrorCode constant for encryptionRequired. */
  public static final BBacnetErrorCode encryptionRequired = new BBacnetErrorCode(ENCRYPTION_REQUIRED);
  /** BBacnetErrorCode constant for incorrectKey. */
  public static final BBacnetErrorCode incorrectKey = new BBacnetErrorCode(INCORRECT_KEY);
  /** BBacnetErrorCode constant for invalidKeyData. */
  public static final BBacnetErrorCode invalidKeyData = new BBacnetErrorCode(INVALID_KEY_DATA);
  /** BBacnetErrorCode constant for keyUpdateInProgress. */
  public static final BBacnetErrorCode keyUpdateInProgress = new BBacnetErrorCode(KEY_UPDATE_IN_PROGRESS);
  /** BBacnetErrorCode constant for malformedMessage. */
  public static final BBacnetErrorCode malformedMessage = new BBacnetErrorCode(MALFORMED_MESSAGE);
  /** BBacnetErrorCode constant for notKeyServer. */
  public static final BBacnetErrorCode notKeyServer = new BBacnetErrorCode(NOT_KEY_SERVER);
  /** BBacnetErrorCode constant for securityNotConfigured. */
  public static final BBacnetErrorCode securityNotConfigured = new BBacnetErrorCode(SECURITY_NOT_CONFIGURED);
  /** BBacnetErrorCode constant for sourceSecurityRequired. */
  public static final BBacnetErrorCode sourceSecurityRequired = new BBacnetErrorCode(SOURCE_SECURITY_REQUIRED);
  /** BBacnetErrorCode constant for tooManyKeys. */
  public static final BBacnetErrorCode tooManyKeys = new BBacnetErrorCode(TOO_MANY_KEYS);
  /** BBacnetErrorCode constant for unknownAuthenticationType. */
  public static final BBacnetErrorCode unknownAuthenticationType = new BBacnetErrorCode(UNKNOWN_AUTHENTICATION_TYPE);
  /** BBacnetErrorCode constant for unknownKey. */
  public static final BBacnetErrorCode unknownKey = new BBacnetErrorCode(UNKNOWN_KEY);
  /** BBacnetErrorCode constant for unknownKeyRevision. */
  public static final BBacnetErrorCode unknownKeyRevision = new BBacnetErrorCode(UNKNOWN_KEY_REVISION);
  /** BBacnetErrorCode constant for unknownSourceMessage. */
  public static final BBacnetErrorCode unknownSourceMessage = new BBacnetErrorCode(UNKNOWN_SOURCE_MESSAGE);
  /** BBacnetErrorCode constant for notRouterToDnet. */
  public static final BBacnetErrorCode notRouterToDnet = new BBacnetErrorCode(NOT_ROUTER_TO_DNET);
  /** BBacnetErrorCode constant for routerBusy. */
  public static final BBacnetErrorCode routerBusy = new BBacnetErrorCode(ROUTER_BUSY);
  /** BBacnetErrorCode constant for unknownNetworkMessage. */
  public static final BBacnetErrorCode unknownNetworkMessage = new BBacnetErrorCode(UNKNOWN_NETWORK_MESSAGE);
  /** BBacnetErrorCode constant for messageTooLong. */
  public static final BBacnetErrorCode messageTooLong = new BBacnetErrorCode(MESSAGE_TOO_LONG);
  /** BBacnetErrorCode constant for securityError. */
  public static final BBacnetErrorCode securityError = new BBacnetErrorCode(SECURITY_ERROR);
  /** BBacnetErrorCode constant for addressingError. */
  public static final BBacnetErrorCode addressingError = new BBacnetErrorCode(ADDRESSING_ERROR);
  /** BBacnetErrorCode constant for writeBdtFailed. */
  public static final BBacnetErrorCode writeBdtFailed = new BBacnetErrorCode(WRITE_BDT_FAILED);
  /** BBacnetErrorCode constant for readBdtFailed. */
  public static final BBacnetErrorCode readBdtFailed = new BBacnetErrorCode(READ_BDT_FAILED);
  /** BBacnetErrorCode constant for registerForeignDeviceFailed. */
  public static final BBacnetErrorCode registerForeignDeviceFailed = new BBacnetErrorCode(REGISTER_FOREIGN_DEVICE_FAILED);
  /** BBacnetErrorCode constant for readFdtFailed. */
  public static final BBacnetErrorCode readFdtFailed = new BBacnetErrorCode(READ_FDT_FAILED);
  /** BBacnetErrorCode constant for deleteFdtEntryFailed. */
  public static final BBacnetErrorCode deleteFdtEntryFailed = new BBacnetErrorCode(DELETE_FDT_ENTRY_FAILED);
  /** BBacnetErrorCode constant for distributeBroadcastFailed. */
  public static final BBacnetErrorCode distributeBroadcastFailed = new BBacnetErrorCode(DISTRIBUTE_BROADCAST_FAILED);
  /** BBacnetErrorCode constant for unknownFileSize. */
  public static final BBacnetErrorCode unknownFileSize = new BBacnetErrorCode(UNKNOWN_FILE_SIZE);
  /** BBacnetErrorCode constant for abortApduTooLong. */
  public static final BBacnetErrorCode abortApduTooLong = new BBacnetErrorCode(ABORT_APDU_TOO_LONG);
  /** BBacnetErrorCode constant for abortApplicationExceededReplyTime. */
  public static final BBacnetErrorCode abortApplicationExceededReplyTime = new BBacnetErrorCode(ABORT_APPLICATION_EXCEEDED_REPLY_TIME);
  /** BBacnetErrorCode constant for abortOutOfResources. */
  public static final BBacnetErrorCode abortOutOfResources = new BBacnetErrorCode(ABORT_OUT_OF_RESOURCES);
  /** BBacnetErrorCode constant for abortTsmTimeout. */
  public static final BBacnetErrorCode abortTsmTimeout = new BBacnetErrorCode(ABORT_TSM_TIMEOUT);
  /** BBacnetErrorCode constant for abortWindowSizeOutOfRange. */
  public static final BBacnetErrorCode abortWindowSizeOutOfRange = new BBacnetErrorCode(ABORT_WINDOW_SIZE_OUT_OF_RANGE);
  /** BBacnetErrorCode constant for fileFull. */
  public static final BBacnetErrorCode fileFull = new BBacnetErrorCode(FILE_FULL);
  /** BBacnetErrorCode constant for inconsistentConfiguration. */
  public static final BBacnetErrorCode inconsistentConfiguration = new BBacnetErrorCode(INCONSISTENT_CONFIGURATION);
  /** BBacnetErrorCode constant for inconsistentObjectType. */
  public static final BBacnetErrorCode inconsistentObjectType = new BBacnetErrorCode(INCONSISTENT_OBJECT_TYPE);
  /** BBacnetErrorCode constant for internalError. */
  public static final BBacnetErrorCode internalError = new BBacnetErrorCode(INTERNAL_ERROR);
  /** BBacnetErrorCode constant for notConfigured. */
  public static final BBacnetErrorCode notConfigured = new BBacnetErrorCode(NOT_CONFIGURED);
  /** BBacnetErrorCode constant for outOfMemory. */
  public static final BBacnetErrorCode outOfMemory = new BBacnetErrorCode(OUT_OF_MEMORY);
  /** BBacnetErrorCode constant for valueTooLong. */
  public static final BBacnetErrorCode valueTooLong = new BBacnetErrorCode(VALUE_TOO_LONG);
  /** BBacnetErrorCode constant for abortInsufficientSecurity. */
  public static final BBacnetErrorCode abortInsufficientSecurity = new BBacnetErrorCode(ABORT_INSUFFICIENT_SECURITY);
  /** BBacnetErrorCode constant for abortSecurityError. */
  public static final BBacnetErrorCode abortSecurityError = new BBacnetErrorCode(ABORT_SECURITY_ERROR);
  /** BBacnetErrorCode constant for duplicateEntry. */
  public static final BBacnetErrorCode duplicateEntry = new BBacnetErrorCode(DUPLICATE_ENTRY);
  /** BBacnetErrorCode constant for invalidValueInThisState. */
  public static final BBacnetErrorCode invalidValueInThisState = new BBacnetErrorCode(INVALID_VALUE_IN_THIS_STATE);
  /** BBacnetErrorCode constant for invalidOperationInThisState. */
  public static final BBacnetErrorCode invalidOperationInThisState = new BBacnetErrorCode(INVALID_OPERATION_IN_THIS_STATE);
  /** BBacnetErrorCode constant for listItemNotNumbered. */
  public static final BBacnetErrorCode listItemNotNumbered = new BBacnetErrorCode(LIST_ITEM_NOT_NUMBERED);
  /** BBacnetErrorCode constant for listItemNotTimestamped. */
  public static final BBacnetErrorCode listItemNotTimestamped = new BBacnetErrorCode(LIST_ITEM_NOT_TIMESTAMPED);
  /** BBacnetErrorCode constant for invalidDataEncoding. */
  public static final BBacnetErrorCode invalidDataEncoding = new BBacnetErrorCode(INVALID_DATA_ENCODING);
  /** BBacnetErrorCode constant for bvlcFunctionUnknown. */
  public static final BBacnetErrorCode bvlcFunctionUnknown = new BBacnetErrorCode(BVLC_FUNCTION_UNKNOWN);
  /** BBacnetErrorCode constant for bvlcProprietaryFunctionUnknown. */
  public static final BBacnetErrorCode bvlcProprietaryFunctionUnknown = new BBacnetErrorCode(BVLC_PROPRIETARY_FUNCTION_UNKNOWN);
  /** BBacnetErrorCode constant for headerEncodingError. */
  public static final BBacnetErrorCode headerEncodingError = new BBacnetErrorCode(HEADER_ENCODING_ERROR);
  /** BBacnetErrorCode constant for headerNotUnderstood. */
  public static final BBacnetErrorCode headerNotUnderstood = new BBacnetErrorCode(HEADER_NOT_UNDERSTOOD);
  /** BBacnetErrorCode constant for messageIncomplete. */
  public static final BBacnetErrorCode messageIncomplete = new BBacnetErrorCode(MESSAGE_INCOMPLETE);
  /** BBacnetErrorCode constant for notA_BacnetScHub. */
  public static final BBacnetErrorCode notA_BacnetScHub = new BBacnetErrorCode(NOT_A_BACNET_SC_HUB);
  /** BBacnetErrorCode constant for payloadExpected. */
  public static final BBacnetErrorCode payloadExpected = new BBacnetErrorCode(PAYLOAD_EXPECTED);
  /** BBacnetErrorCode constant for unexpectedData. */
  public static final BBacnetErrorCode unexpectedData = new BBacnetErrorCode(UNEXPECTED_DATA);
  /** BBacnetErrorCode constant for nodeDuplicateVmac. */
  public static final BBacnetErrorCode nodeDuplicateVmac = new BBacnetErrorCode(NODE_DUPLICATE_VMAC);
  /** BBacnetErrorCode constant for httpUnexpectedResponseCode. */
  public static final BBacnetErrorCode httpUnexpectedResponseCode = new BBacnetErrorCode(HTTP_UNEXPECTED_RESPONSE_CODE);
  /** BBacnetErrorCode constant for httpNoUpgrade. */
  public static final BBacnetErrorCode httpNoUpgrade = new BBacnetErrorCode(HTTP_NO_UPGRADE);
  /** BBacnetErrorCode constant for httpResourceNotLocal. */
  public static final BBacnetErrorCode httpResourceNotLocal = new BBacnetErrorCode(HTTP_RESOURCE_NOT_LOCAL);
  /** BBacnetErrorCode constant for httpProxyAuthenticationFailed. */
  public static final BBacnetErrorCode httpProxyAuthenticationFailed = new BBacnetErrorCode(HTTP_PROXY_AUTHENTICATION_FAILED);
  /** BBacnetErrorCode constant for httpResponseTimeout. */
  public static final BBacnetErrorCode httpResponseTimeout = new BBacnetErrorCode(HTTP_RESPONSE_TIMEOUT);
  /** BBacnetErrorCode constant for httpResponseSyntaxError. */
  public static final BBacnetErrorCode httpResponseSyntaxError = new BBacnetErrorCode(HTTP_RESPONSE_SYNTAX_ERROR);
  /** BBacnetErrorCode constant for httpResponseValueError. */
  public static final BBacnetErrorCode httpResponseValueError = new BBacnetErrorCode(HTTP_RESPONSE_VALUE_ERROR);
  /** BBacnetErrorCode constant for httpResponseMissingHeader. */
  public static final BBacnetErrorCode httpResponseMissingHeader = new BBacnetErrorCode(HTTP_RESPONSE_MISSING_HEADER);
  /** BBacnetErrorCode constant for httpWebsocketHeaderError. */
  public static final BBacnetErrorCode httpWebsocketHeaderError = new BBacnetErrorCode(HTTP_WEBSOCKET_HEADER_ERROR);
  /** BBacnetErrorCode constant for httpUpgradeRequired. */
  public static final BBacnetErrorCode httpUpgradeRequired = new BBacnetErrorCode(HTTP_UPGRADE_REQUIRED);
  /** BBacnetErrorCode constant for httpUpgradeError. */
  public static final BBacnetErrorCode httpUpgradeError = new BBacnetErrorCode(HTTP_UPGRADE_ERROR);
  /** BBacnetErrorCode constant for httpTemporaryUnavailable. */
  public static final BBacnetErrorCode httpTemporaryUnavailable = new BBacnetErrorCode(HTTP_TEMPORARY_UNAVAILABLE);
  /** BBacnetErrorCode constant for httpNotA_Server. */
  public static final BBacnetErrorCode httpNotA_Server = new BBacnetErrorCode(HTTP_NOT_A_SERVER);
  /** BBacnetErrorCode constant for httpError. */
  public static final BBacnetErrorCode httpError = new BBacnetErrorCode(HTTP_ERROR);
  /** BBacnetErrorCode constant for websocketSchemeNotSupported. */
  public static final BBacnetErrorCode websocketSchemeNotSupported = new BBacnetErrorCode(WEBSOCKET_SCHEME_NOT_SUPPORTED);
  /** BBacnetErrorCode constant for websocketUnknownControlMessage. */
  public static final BBacnetErrorCode websocketUnknownControlMessage = new BBacnetErrorCode(WEBSOCKET_UNKNOWN_CONTROL_MESSAGE);
  /** BBacnetErrorCode constant for websocketCloseError. */
  public static final BBacnetErrorCode websocketCloseError = new BBacnetErrorCode(WEBSOCKET_CLOSE_ERROR);
  /** BBacnetErrorCode constant for websocketClosedByPeer. */
  public static final BBacnetErrorCode websocketClosedByPeer = new BBacnetErrorCode(WEBSOCKET_CLOSED_BY_PEER);
  /** BBacnetErrorCode constant for websocketEndpointLeaves. */
  public static final BBacnetErrorCode websocketEndpointLeaves = new BBacnetErrorCode(WEBSOCKET_ENDPOINT_LEAVES);
  /** BBacnetErrorCode constant for websocketProtocolError. */
  public static final BBacnetErrorCode websocketProtocolError = new BBacnetErrorCode(WEBSOCKET_PROTOCOL_ERROR);
  /** BBacnetErrorCode constant for websocketDataNotAccepted. */
  public static final BBacnetErrorCode websocketDataNotAccepted = new BBacnetErrorCode(WEBSOCKET_DATA_NOT_ACCEPTED);
  /** BBacnetErrorCode constant for websocketClosedAbnormally. */
  public static final BBacnetErrorCode websocketClosedAbnormally = new BBacnetErrorCode(WEBSOCKET_CLOSED_ABNORMALLY);
  /** BBacnetErrorCode constant for websocketDataInconsistent. */
  public static final BBacnetErrorCode websocketDataInconsistent = new BBacnetErrorCode(WEBSOCKET_DATA_INCONSISTENT);
  /** BBacnetErrorCode constant for websocketDataAgainstPolicy. */
  public static final BBacnetErrorCode websocketDataAgainstPolicy = new BBacnetErrorCode(WEBSOCKET_DATA_AGAINST_POLICY);
  /** BBacnetErrorCode constant for websocketFrameTooLong. */
  public static final BBacnetErrorCode websocketFrameTooLong = new BBacnetErrorCode(WEBSOCKET_FRAME_TOO_LONG);
  /** BBacnetErrorCode constant for websocketExtensionMissing. */
  public static final BBacnetErrorCode websocketExtensionMissing = new BBacnetErrorCode(WEBSOCKET_EXTENSION_MISSING);
  /** BBacnetErrorCode constant for websocketRequestUnavailable. */
  public static final BBacnetErrorCode websocketRequestUnavailable = new BBacnetErrorCode(WEBSOCKET_REQUEST_UNAVAILABLE);
  /** BBacnetErrorCode constant for websocketError. */
  public static final BBacnetErrorCode websocketError = new BBacnetErrorCode(WEBSOCKET_ERROR);
  /** BBacnetErrorCode constant for tlsClientCertificateError. */
  public static final BBacnetErrorCode tlsClientCertificateError = new BBacnetErrorCode(TLS_CLIENT_CERTIFICATE_ERROR);
  /** BBacnetErrorCode constant for tlsServerCertificateError. */
  public static final BBacnetErrorCode tlsServerCertificateError = new BBacnetErrorCode(TLS_SERVER_CERTIFICATE_ERROR);
  /** BBacnetErrorCode constant for tlsClientAuthenticationFailed. */
  public static final BBacnetErrorCode tlsClientAuthenticationFailed = new BBacnetErrorCode(TLS_CLIENT_AUTHENTICATION_FAILED);
  /** BBacnetErrorCode constant for tlsServerAuthenticationFailed. */
  public static final BBacnetErrorCode tlsServerAuthenticationFailed = new BBacnetErrorCode(TLS_SERVER_AUTHENTICATION_FAILED);
  /** BBacnetErrorCode constant for tlsClientCertificateExpired. */
  public static final BBacnetErrorCode tlsClientCertificateExpired = new BBacnetErrorCode(TLS_CLIENT_CERTIFICATE_EXPIRED);
  /** BBacnetErrorCode constant for tlsServerCertificateExpired. */
  public static final BBacnetErrorCode tlsServerCertificateExpired = new BBacnetErrorCode(TLS_SERVER_CERTIFICATE_EXPIRED);
  /** BBacnetErrorCode constant for tlsClientCertificateRevoked. */
  public static final BBacnetErrorCode tlsClientCertificateRevoked = new BBacnetErrorCode(TLS_CLIENT_CERTIFICATE_REVOKED);
  /** BBacnetErrorCode constant for tlsServerCertificateRevoked. */
  public static final BBacnetErrorCode tlsServerCertificateRevoked = new BBacnetErrorCode(TLS_SERVER_CERTIFICATE_REVOKED);
  /** BBacnetErrorCode constant for tlsError. */
  public static final BBacnetErrorCode tlsError = new BBacnetErrorCode(TLS_ERROR);
  /** BBacnetErrorCode constant for dnsUnavailable. */
  public static final BBacnetErrorCode dnsUnavailable = new BBacnetErrorCode(DNS_UNAVAILABLE);
  /** BBacnetErrorCode constant for dnsNameResolutionFailed. */
  public static final BBacnetErrorCode dnsNameResolutionFailed = new BBacnetErrorCode(DNS_NAME_RESOLUTION_FAILED);
  /** BBacnetErrorCode constant for dnsResolverFailure. */
  public static final BBacnetErrorCode dnsResolverFailure = new BBacnetErrorCode(DNS_RESOLVER_FAILURE);
  /** BBacnetErrorCode constant for dnsError. */
  public static final BBacnetErrorCode dnsError = new BBacnetErrorCode(DNS_ERROR);
  /** BBacnetErrorCode constant for tcpConnectTimeout. */
  public static final BBacnetErrorCode tcpConnectTimeout = new BBacnetErrorCode(TCP_CONNECT_TIMEOUT);
  /** BBacnetErrorCode constant for tcpConnectionRefused. */
  public static final BBacnetErrorCode tcpConnectionRefused = new BBacnetErrorCode(TCP_CONNECTION_REFUSED);
  /** BBacnetErrorCode constant for tcpClosedByLocal. */
  public static final BBacnetErrorCode tcpClosedByLocal = new BBacnetErrorCode(TCP_CLOSED_BY_LOCAL);
  /** BBacnetErrorCode constant for tcpClosedOther. */
  public static final BBacnetErrorCode tcpClosedOther = new BBacnetErrorCode(TCP_CLOSED_OTHER);
  /** BBacnetErrorCode constant for tcpError. */
  public static final BBacnetErrorCode tcpError = new BBacnetErrorCode(TCP_ERROR);
  /** BBacnetErrorCode constant for ipAddressNotReachable. */
  public static final BBacnetErrorCode ipAddressNotReachable = new BBacnetErrorCode(IP_ADDRESS_NOT_REACHABLE);
  /** BBacnetErrorCode constant for ipError. */
  public static final BBacnetErrorCode ipError = new BBacnetErrorCode(IP_ERROR);
  /** BBacnetErrorCode constant for nullValueEvent. */
  public static final BBacnetErrorCode nullValueEvent = new BBacnetErrorCode(NULL_VALUE_EVENT);

  /** Factory method with ordinal. */
  public static BBacnetErrorCode make(int ordinal)
  {
    return (BBacnetErrorCode)other.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetErrorCode make(String tag)
  {
    return (BBacnetErrorCode)other.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetErrorCode(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetErrorCode DEFAULT = other;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetErrorCode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////


  public static final int MAX_ASHRAE_ID = ABORT_SECURITY_ERROR;
  public static final int MAX_RESERVED_ID = 255;
  public static final int MAX_ID = 65535;


////////////////////////////////////////////////////////////////
// Niagara extensions
////////////////////////////////////////////////////////////////

  // Error Class - DEVICE

  // Error Class - OBJECT
  /**
   * Ordinal value for targetNotConfigured.
   */
  public static final int TARGET_NOT_CONFIGURED = 1000;
  /**
   * Ordinal value for invalidTargetType.
   */
  public static final int INVALID_TARGET_TYPE = 1001;

  // Error Class - PROPERTY
  // Error Class - RESOURCES
  // Error Class - SECURITY
  // Error Class - SERVICES

  /**
   * BBacnetErrorCode constant for targetNotConfigured.
   */
  public static final String TARGET_NOT_CONFIGURED_TAG = "targetNotConfigured";
  /**
   * BBacnetErrorCode constant for invalidTargetType.
   */
  public static final String INVALID_TARGET_TYPE_TAG = "invalidTargetType";
  public static final int[] NIAGARA_CODES = new int[]
    {
      TARGET_NOT_CONFIGURED,
      INVALID_TARGET_TYPE
    };
  public static final String[] NIAGARA_TAGS = new String[]
    {
      TARGET_NOT_CONFIGURED_TAG,
      INVALID_TARGET_TYPE_TAG
    };

  public static final BEnumRange NIAGARA_ERROR_CODES_RANGE
    = BEnumRange.make(TYPE, NIAGARA_CODES, NIAGARA_TAGS);


////////////////////////////////////////////////////////////////
// Static methods
////////////////////////////////////////////////////////////////

  /**
   * Create a string tag for the given ordinal.
   *
   * @return the tag for the ordinal, if it is known,
   * or construct one using standard prefixes.
   */
  public static String tag(int id)
  {
    return tag(id, null);
  }

  /**
   * Create a string tag for the given ordinal.
   *
   * @return the tag for the ordinal, if it is known,
   * or construct one using standard prefixes.
   */
  public static String tag(int id, BExtensibleEnumList list)
  {
    if (DEFAULT.getRange().isOrdinal(id))
    { return DEFAULT.getRange().getTag(id); }
    if (isAshrae(id))
    { return ASHRAE_PREFIX + id; }
    if (isProprietary(id))
    {
      if (list != null)
      {
        if (list.getErrorCodeRange().isOrdinal(id))
        { return list.getErrorCodeRange().getTag(id); }
      }
      return PROPRIETARY_PREFIX + id;
    }
    throw new InvalidEnumException(id);
  }

  /**
   * Get the ordinal for the given tag.
   *
   * @return the ordinal for the tag, if it is known,
   * or generate one if the tag uses standard prefixes.
   */
  public static int ordinal(String tag)
  {
    return ordinal(tag, null);
  }

  /**
   * Get the ordinal for the given tag.
   *
   * @return the ordinal for the tag, if it is known,
   * or generate one if the tag uses standard prefixes.
   */
  public static int ordinal(String tag, BExtensibleEnumList list)
  {
    if (DEFAULT.getRange().isTag(tag))
    { return DEFAULT.getRange().tagToOrdinal(tag); }
    if (tag.startsWith(ASHRAE_PREFIX))
    { return Integer.parseInt(tag.substring(ASHRAE_PREFIX_LENGTH)); }
    if (list != null)
    {
      if (list.getErrorCodeRange().isTag(tag))
      { return list.getErrorCodeRange().tagToOrdinal(tag); }
    }
    if (tag.startsWith(PROPRIETARY_PREFIX))
    { return Integer.parseInt(tag.substring(PROPRIETARY_PREFIX_LENGTH)); }
    throw new InvalidEnumException(tag);
  }

  /**
   * Is this a proprietary extension?
   *
   * @return true if this is a proprietary extension.
   */
  public static boolean isProprietary(int id)
  {
    return (id > MAX_RESERVED_ID) && (id <= MAX_ID);
  }

  /**
   * Is this an ASHRAE extension?
   *
   * @return true if this is an ASHRAE extension.
   */
  public static boolean isAshrae(int id)
  {
    return (id > MAX_ASHRAE_ID) && (id <= MAX_RESERVED_ID);
  }

  /**
   * Is this id valid for this enumeration?
   *
   * @return true if this id is within the allowed range.
   */
  public static boolean isValid(int id)
  {
    return id <= MAX_ID;
  }

  /**
   * Is this id part of the predefined (fixed) range?
   *
   * @return true if this id is in the fixed range.
   */
  public static boolean isFixed(int id)
  {
    return id <= MAX_ASHRAE_ID;
  }
}
