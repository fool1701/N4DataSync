/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetPropertyIdentifier represents the BACnetPropertyIdentifier
 * enumeration.
 * <p>
 * BBacnetPropertyIdentifier is an "extensible" enumeration.
 * Values 0-511 are reserved for use by ASHRAE.
 * Values from 512-4194303 (0x3FFFFF)
 * can be used for proprietary extensions.
 * <p>
 * Note that for proprietary extensions, a given ordinal is not
 * globally mapped to the same enumeration.  Type X from vendor
 * A will be different than type X from vendor B.  Extensions are
 * also not guaranteed unique within a vendor's own products, so
 * type Y in device A from vendor A will in general be different
 * than type Y in device B from vendor A.
 * <p>
 * Maintained for backward compatibility:
 * <ul>
 *   <li>expiryTime should be expirationTime</li>
 *   <li>memberStatusDlags should be memberStatusFlags</li>
 *   <li>defaultFadetime should be defaultFadeTime</li>
 *   <li>defaultRamprate should be defaultRampRate</li>
 *   <li>egressTime should be egressTimer</li>
 * </ul>
 * <p>
 * Deprecated:
 * <ul>
 *   <li>removed1 (18)</li>
 *   <li>issueConfirmedNotifications</li>
 *   <li>listOfSessionKeys</li>
 *   <li>protocolConformanceClass</li>
 *   <li>recipient</li>
 *   <li>currentNotifyTime</li>
 *   <li>previousNotifyTime</li>
 *   <li>masterExemption</li>
 *   <li>occupancyExemption</li>
 *   <li>passbackExemption</li>
 * </ul>
 *
 * @author Craig Gemmill
 * @version $Revision: 7$ $Date: 12/19/01 4:36:00 PM$
 * @creation 21 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "ackedTransitions", ordinal = 0),
    @Range(value = "ackRequired", ordinal = 1),
    @Range(value = "action", ordinal = 2),
    @Range(value = "actionText", ordinal = 3),
    @Range(value = "activeText", ordinal = 4),
    @Range(value = "activeVtSessions", ordinal = 5),
    @Range(value = "alarmValue", ordinal = 6),
    @Range(value = "alarmValues", ordinal = 7),
    @Range(value = "all", ordinal = 8),
    @Range(value = "allWritesSuccessful", ordinal = 9),
    @Range(value = "apduSegmentTimeout", ordinal = 10),
    @Range(value = "apduTimeout", ordinal = 11),
    @Range(value = "applicationSoftwareVersion", ordinal = 12),
    @Range(value = "archive", ordinal = 13),
    @Range(value = "bias", ordinal = 14),
    @Range(value = "changeOfStateCount", ordinal = 15),
    @Range(value = "changeOfStateTime", ordinal = 16),
    @Range(value = "notificationClass", ordinal = 17),
    @Range(value = "controlledVariableReference", ordinal = 19),
    @Range(value = "controlledVariableUnits", ordinal = 20),
    @Range(value = "controlledVariableValue", ordinal = 21),
    @Range(value = "covIncrement", ordinal = 22),
    @Range(value = "dateList", ordinal = 23),
    @Range(value = "daylightSavingsStatus", ordinal = 24),
    @Range(value = "deadband", ordinal = 25),
    @Range(value = "derivativeConstant", ordinal = 26),
    @Range(value = "derivativeConstantUnits", ordinal = 27),
    @Range(value = "description", ordinal = 28),
    @Range(value = "descriptionOfHalt", ordinal = 29),
    @Range(value = "deviceAddressBinding", ordinal = 30),
    @Range(value = "deviceType", ordinal = 31),
    @Range(value = "effectivePeriod", ordinal = 32),
    @Range(value = "elapsedActiveTime", ordinal = 33),
    @Range(value = "errorLimit", ordinal = 34),
    @Range(value = "eventEnable", ordinal = 35),
    @Range(value = "eventState", ordinal = 36),
    @Range(value = "eventType", ordinal = 37),
    @Range(value = "exceptionSchedule", ordinal = 38),
    @Range(value = "faultValues", ordinal = 39),
    @Range(value = "feedbackValue", ordinal = 40),
    @Range(value = "fileAccessMethod", ordinal = 41),
    @Range(value = "fileSize", ordinal = 42),
    @Range(value = "fileType", ordinal = 43),
    @Range(value = "firmwareRevision", ordinal = 44),
    @Range(value = "highLimit", ordinal = 45),
    @Range(value = "inactiveText", ordinal = 46),
    @Range(value = "inProcess", ordinal = 47),
    @Range(value = "instanceOf", ordinal = 48),
    @Range(value = "integralConstant", ordinal = 49),
    @Range(value = "integralConstantUnits", ordinal = 50),
    @Range(value = "limitEnable", ordinal = 52),
    @Range(value = "listOfGroupMembers", ordinal = 53),
    @Range(value = "listOfObjectPropertyReferences", ordinal = 54),
    @Range(value = "localDate", ordinal = 56),
    @Range(value = "localTime", ordinal = 57),
    @Range(value = "location", ordinal = 58),
    @Range(value = "lowLimit", ordinal = 59),
    @Range(value = "manipulatedVariableReference", ordinal = 60),
    @Range(value = "maximumOutput", ordinal = 61),
    @Range(value = "maxApduLengthAccepted", ordinal = 62),
    @Range(value = "maxInfoFrames", ordinal = 63),
    @Range(value = "maxMaster", ordinal = 64),
    @Range(value = "maxPresValue", ordinal = 65),
    @Range(value = "minimumOffTime", ordinal = 66),
    @Range(value = "minimumOnTime", ordinal = 67),
    @Range(value = "minimumOutput", ordinal = 68),
    @Range(value = "minPresValue", ordinal = 69),
    @Range(value = "modelName", ordinal = 70),
    @Range(value = "modificationDate", ordinal = 71),
    @Range(value = "notifyType", ordinal = 72),
    @Range(value = "numberOfApduRetries", ordinal = 73),
    @Range(value = "numberOfStates", ordinal = 74),
    @Range(value = "objectIdentifier", ordinal = 75),
    @Range(value = "objectList", ordinal = 76),
    @Range(value = "objectName", ordinal = 77),
    @Range(value = "objectPropertyReference", ordinal = 78),
    @Range(value = "objectType", ordinal = 79),
    @Range(value = "optional", ordinal = 80),
    @Range(value = "outOfService", ordinal = 81),
    @Range(value = "outputUnits", ordinal = 82),
    @Range(value = "eventParameters", ordinal = 83),
    @Range(value = "polarity", ordinal = 84),
    @Range(value = "presentValue", ordinal = 85),
    @Range(value = "priority", ordinal = 86),
    @Range(value = "priorityArray", ordinal = 87),
    @Range(value = "priorityForWriting", ordinal = 88),
    @Range(value = "processIdentifier", ordinal = 89),
    @Range(value = "programChange", ordinal = 90),
    @Range(value = "programLocation", ordinal = 91),
    @Range(value = "programState", ordinal = 92),
    @Range(value = "proportionalConstant", ordinal = 93),
    @Range(value = "proportionalConstantUnits", ordinal = 94),
    @Range(value = "protocolObjectTypesSupported", ordinal = 96),
    @Range(value = "protocolServicesSupported", ordinal = 97),
    @Range(value = "protocolVersion", ordinal = 98),
    @Range(value = "readOnly", ordinal = 99),
    @Range(value = "reasonForHalt", ordinal = 100),
    @Range(value = "recipientList", ordinal = 102),
    @Range(value = "reliability", ordinal = 103),
    @Range(value = "relinquishDefault", ordinal = 104),
    @Range(value = "required", ordinal = 105),
    @Range(value = "resolution", ordinal = 106),
    @Range(value = "segmentationSupported", ordinal = 107),
    @Range(value = "setpoint", ordinal = 108),
    @Range(value = "setpointReference", ordinal = 109),
    @Range(value = "stateText", ordinal = 110),
    @Range(value = "statusFlags", ordinal = 111),
    @Range(value = "systemStatus", ordinal = 112),
    @Range(value = "timeDelay", ordinal = 113),
    @Range(value = "timeOfActiveTimeReset", ordinal = 114),
    @Range(value = "timeOfStateCountReset", ordinal = 115),
    @Range(value = "timeSynchronizationRecipients", ordinal = 116),
    @Range(value = "units", ordinal = 117),
    @Range(value = "updateInterval", ordinal = 118),
    @Range(value = "utcOffset", ordinal = 119),
    @Range(value = "vendorIdentifier", ordinal = 120),
    @Range(value = "vendorName", ordinal = 121),
    @Range(value = "vtClassesSupported", ordinal = 122),
    @Range(value = "weeklySchedule", ordinal = 123),
    @Range(value = "attemptedSamples", ordinal = 124),
    @Range(value = "averageValue", ordinal = 125),
    @Range(value = "bufferSize", ordinal = 126),
    @Range(value = "clientCovIncrement", ordinal = 127),
    @Range(value = "covResubscriptionInterval", ordinal = 128),
    @Range(value = "eventTimeStamps", ordinal = 130),
    @Range(value = "logBuffer", ordinal = 131),
    @Range(value = "logDeviceObjectProperty", ordinal = 132),
    @Range(value = "enable", ordinal = 133),
    @Range(value = "logInterval", ordinal = 134),
    @Range(value = "maximumValue", ordinal = 135),
    @Range(value = "minimumValue", ordinal = 136),
    @Range(value = "notificationThreshold", ordinal = 137),
    @Range(value = "protocolRevision", ordinal = 139),
    @Range(value = "recordsSinceNotification", ordinal = 140),
    @Range(value = "recordCount", ordinal = 141),
    @Range(value = "startTime", ordinal = 142),
    @Range(value = "stopTime", ordinal = 143),
    @Range(value = "stopWhenFull", ordinal = 144),
    @Range(value = "totalRecordCount", ordinal = 145),
    @Range(value = "validSamples", ordinal = 146),
    @Range(value = "windowInterval", ordinal = 147),
    @Range(value = "windowSamples", ordinal = 148),
    @Range(value = "maximumValueTimestamp", ordinal = 149),
    @Range(value = "minimumValueTimestamp", ordinal = 150),
    @Range(value = "varianceValue", ordinal = 151),
    @Range(value = "activeCovSubscriptions", ordinal = 152),
    @Range(value = "backupFailureTimeout", ordinal = 153),
    @Range(value = "configurationFiles", ordinal = 154),
    @Range(value = "databaseRevision", ordinal = 155),
    @Range(value = "directReading", ordinal = 156),
    @Range(value = "lastRestoreTime", ordinal = 157),
    @Range(value = "maintenanceRequired", ordinal = 158),
    @Range(value = "memberOf", ordinal = 159),
    @Range(value = "mode", ordinal = 160),
    @Range(value = "operationExpected", ordinal = 161),
    @Range(value = "setting", ordinal = 162),
    @Range(value = "silenced", ordinal = 163),
    @Range(value = "trackingValue", ordinal = 164),
    @Range(value = "zoneMembers", ordinal = 165),
    @Range(value = "lifeSafetyAlarmValues", ordinal = 166),
    @Range(value = "maxSegmentsAccepted", ordinal = 167),
    @Range(value = "profileName", ordinal = 168),
    @Range(value = "autoSlaveDiscovery", ordinal = 169),
    @Range(value = "manualSlaveAddressBinding", ordinal = 170),
    @Range(value = "slaveAddressBinding", ordinal = 171),
    @Range(value = "slaveProxyEnable", ordinal = 172),
    @Range(value = "lastNotifyRecord", ordinal = 173),
    @Range(value = "scheduleDefault", ordinal = 174),
    @Range(value = "acceptedModes", ordinal = 175),
    @Range(value = "adjustValue", ordinal = 176),
    @Range(value = "count", ordinal = 177),
    @Range(value = "countBeforeChange", ordinal = 178),
    @Range(value = "countChangeTime", ordinal = 179),
    @Range(value = "covPeriod", ordinal = 180),
    @Range(value = "inputReference", ordinal = 181),
    @Range(value = "limitMonitoringInterval", ordinal = 182),
    @Range(value = "loggingObject", ordinal = 183),
    @Range(value = "loggingRecord", ordinal = 184),
    @Range(value = "prescale", ordinal = 185),
    @Range(value = "pulseRate", ordinal = 186),
    @Range(value = "scale", ordinal = 187),
    @Range(value = "scaleFactor", ordinal = 188),
    @Range(value = "updateTime", ordinal = 189),
    @Range(value = "valueBeforeChange", ordinal = 190),
    @Range(value = "valueSet", ordinal = 191),
    @Range(value = "valueChangeTime", ordinal = 192),
    @Range(value = "alignIntervals", ordinal = 193),
    @Range(value = "intervalOffset", ordinal = 195),
    @Range(value = "lastRestartReason", ordinal = 196),
    @Range(value = "loggingType", ordinal = 197),
    @Range(value = "restartNotificationRecipients", ordinal = 202),
    @Range(value = "timeOfDeviceRestart", ordinal = 203),
    @Range(value = "timeSynchronizationInterval", ordinal = 204),
    @Range(value = "trigger", ordinal = 205),
    @Range(value = "utcTimeSynchronizationRecipients", ordinal = 206),
    @Range(value = "nodeSubtype", ordinal = 207),
    @Range(value = "nodeType", ordinal = 208),
    @Range(value = "structuredObjectList", ordinal = 209),
    @Range(value = "subordinateAnnotations", ordinal = 210),
    @Range(value = "subordinateList", ordinal = 211),
    @Range(value = "actualShedLevel", ordinal = 212),
    @Range(value = "dutyWindow", ordinal = 213),
    @Range(value = "expectedShedLevel", ordinal = 214),
    @Range(value = "fullDutyBaseline", ordinal = 215),
    @Range(value = "requestedShedLevel", ordinal = 218),
    @Range(value = "shedDuration", ordinal = 219),
    @Range(value = "shedLevelDescriptions", ordinal = 220),
    @Range(value = "shedLevels", ordinal = 221),
    @Range(value = "stateDescription", ordinal = 222),
    @Range(value = "doorAlarmState", ordinal = 226),
    @Range(value = "doorExtendedPulseTime", ordinal = 227),
    @Range(value = "doorMembers", ordinal = 228),
    @Range(value = "doorOpenTooLongTime", ordinal = 229),
    @Range(value = "doorPulseTime", ordinal = 230),
    @Range(value = "doorStatus", ordinal = 231),
    @Range(value = "doorUnlockDelayTime", ordinal = 232),
    @Range(value = "lockStatus", ordinal = 233),
    @Range(value = "maskedAlarmValues", ordinal = 234),
    @Range(value = "securedStatus", ordinal = 235),
    @Range(value = "absenteeLimit", ordinal = 244),
    @Range(value = "accessAlarmEvents", ordinal = 245),
    @Range(value = "accessDoors", ordinal = 246),
    @Range(value = "accessEvent", ordinal = 247),
    @Range(value = "accessEventAuthenticationFactor", ordinal = 248),
    @Range(value = "accessEventCredential", ordinal = 249),
    @Range(value = "accessEventTime", ordinal = 250),
    @Range(value = "accessTransactionEvents", ordinal = 251),
    @Range(value = "accompaniment", ordinal = 252),
    @Range(value = "accompanimentTime", ordinal = 253),
    @Range(value = "activationTime", ordinal = 254),
    @Range(value = "activeAuthenticationPolicy", ordinal = 255),
    @Range(value = "assignedAccessRights", ordinal = 256),
    @Range(value = "authenticationFactors", ordinal = 257),
    @Range(value = "authenticationPolicyList", ordinal = 258),
    @Range(value = "authenticationPolicyNames", ordinal = 259),
    @Range(value = "authenticationStatus", ordinal = 260),
    @Range(value = "authorizationMode", ordinal = 261),
    @Range(value = "belongsTo", ordinal = 262),
    @Range(value = "credentialDisable", ordinal = 263),
    @Range(value = "credentialStatus", ordinal = 264),
    @Range(value = "credentials", ordinal = 265),
    @Range(value = "credentialsInZone", ordinal = 266),
    @Range(value = "daysRemaining", ordinal = 267),
    @Range(value = "entryPoints", ordinal = 268),
    @Range(value = "exitPoints", ordinal = 269),
    @Range(value = "expiryTime", ordinal = 270),
    @Range(value = "extendedTimeEnable", ordinal = 271),
    @Range(value = "failedAttemptEvents", ordinal = 272),
    @Range(value = "failedAttempts", ordinal = 273),
    @Range(value = "failedAttemptsTime", ordinal = 274),
    @Range(value = "lastAccessEvent", ordinal = 275),
    @Range(value = "lastAccessPoint", ordinal = 276),
    @Range(value = "lastCredentialAdded", ordinal = 277),
    @Range(value = "lastCredentialAddedTime", ordinal = 278),
    @Range(value = "lastCredentialRemoved", ordinal = 279),
    @Range(value = "lastCredentialRemovedTime", ordinal = 280),
    @Range(value = "lastUseTime", ordinal = 281),
    @Range(value = "lockout", ordinal = 282),
    @Range(value = "lockoutRelinquishTime", ordinal = 283),
    @Range(value = "maxFailedAttempts", ordinal = 285),
    @Range(value = "members", ordinal = 286),
    @Range(value = "musterPoint", ordinal = 287),
    @Range(value = "negativeAccessRules", ordinal = 288),
    @Range(value = "numberOfAuthenticationPolicies", ordinal = 289),
    @Range(value = "occupancyCount", ordinal = 290),
    @Range(value = "occupancyCountAdjust", ordinal = 291),
    @Range(value = "occupancyCountEnable", ordinal = 292),
    @Range(value = "occupancyLowerLimit", ordinal = 294),
    @Range(value = "occupancyLowerLimitEnforced", ordinal = 295),
    @Range(value = "occupancyState", ordinal = 296),
    @Range(value = "occupancyUpperLimit", ordinal = 297),
    @Range(value = "occupancyUpperLimitEnforced", ordinal = 298),
    @Range(value = "passbackMode", ordinal = 300),
    @Range(value = "passbackTimeout", ordinal = 301),
    @Range(value = "positiveAccessRules", ordinal = 302),
    @Range(value = "reasonForDisable", ordinal = 303),
    @Range(value = "supportedFormats", ordinal = 304),
    @Range(value = "supportedFormatClasses", ordinal = 305),
    @Range(value = "threatAuthority", ordinal = 306),
    @Range(value = "threatLevel", ordinal = 307),
    @Range(value = "traceFlag", ordinal = 308),
    @Range(value = "transactionNotificationClass", ordinal = 309),
    @Range(value = "userExternalIdentifier", ordinal = 310),
    @Range(value = "userInformationReference", ordinal = 311),
    @Range(value = "userName", ordinal = 317),
    @Range(value = "userType", ordinal = 318),
    @Range(value = "usesRemaining", ordinal = 319),
    @Range(value = "zoneFrom", ordinal = 320),
    @Range(value = "zoneTo", ordinal = 321),
    @Range(value = "accessEventTag", ordinal = 322),
    @Range(value = "globalIdentifier", ordinal = 323),
    @Range(value = "verificationTime", ordinal = 326),
    @Range(value = "backupAndRestoreState", ordinal = 338),
    @Range(value = "backupPreparationTime", ordinal = 339),
    @Range(value = "restoreCompletionTime", ordinal = 340),
    @Range(value = "restorePreparationTime", ordinal = 341),
    @Range(value = "bitMask", ordinal = 342),
    @Range(value = "bitText", ordinal = 343),
    @Range(value = "isUtc", ordinal = 344),
    @Range(value = "groupMembers", ordinal = 345),
    @Range(value = "groupMemberNames", ordinal = 346),
    @Range(value = "memberStatusDlags", ordinal = 347),
    @Range(value = "requestedUpdateInterval", ordinal = 348),
    @Range(value = "covuPeriod", ordinal = 349),
    @Range(value = "covuRecipients", ordinal = 350),
    @Range(value = "eventMessageTexts", ordinal = 351),
    @Range(value = "eventMessageTextsConfig", ordinal = 352),
    @Range(value = "eventDetectionEnable", ordinal = 353),
    @Range(value = "eventAlgorithmInhibit", ordinal = 354),
    @Range(value = "eventAlgorithmInhibitRef", ordinal = 355),
    @Range(value = "timeDelayNormal", ordinal = 356),
    @Range(value = "reliabilityEvaluationInhibit", ordinal = 357),
    @Range(value = "faultParameters", ordinal = 358),
    @Range(value = "faultType", ordinal = 359),
    @Range(value = "localForwardingOnly", ordinal = 360),
    @Range(value = "processIdentifierFilter", ordinal = 361),
    @Range(value = "subscribedRecipients", ordinal = 362),
    @Range(value = "portFilter", ordinal = 363),
    @Range(value = "authorizationExemptions", ordinal = 364),
    @Range(value = "allowGroupDelayInhibit", ordinal = 365),
    @Range(value = "channelNumber", ordinal = 366),
    @Range(value = "controlGroups", ordinal = 367),
    @Range(value = "executionDelay", ordinal = 368),
    @Range(value = "lastPriority", ordinal = 369),
    @Range(value = "writeStatus", ordinal = 370),
    @Range(value = "propertyList", ordinal = 371),
    @Range(value = "serialNumber", ordinal = 372),
    @Range(value = "blinkWarnEnable", ordinal = 373),
    @Range(value = "defaultFadetime", ordinal = 374),
    @Range(value = "defaultRamprate", ordinal = 375),
    @Range(value = "defaultStepIncrement", ordinal = 376),
    @Range(value = "egressTime", ordinal = 377),
    @Range(value = "inProgress", ordinal = 378),
    @Range(value = "instantaneousPower", ordinal = 379),
    @Range(value = "lightingCommand", ordinal = 380),
    @Range(value = "lightingCommandDefaultPriority", ordinal = 381),
    @Range(value = "maxActualValue", ordinal = 382),
    @Range(value = "minActualValue", ordinal = 383),
    @Range(value = "power", ordinal = 384),
    @Range(value = "transition", ordinal = 385),
    @Range(value = "egressActive", ordinal = 386),
    @Range(value = "interfaceValue", ordinal = 387),
    @Range(value = "faultHighLimit", ordinal = 388),
    @Range(value = "faultLowLimit", ordinal = 389),
    @Range(value = "lowDiffLimit", ordinal = 390),
    @Range(value = "strikeCount", ordinal = 391),
    @Range(value = "timeOfStrikeCountReset", ordinal = 392),
    @Range(value = "defaultTimeout", ordinal = 393),
    @Range(value = "initialTimeout", ordinal = 394),
    @Range(value = "lastStateChange", ordinal = 395),
    @Range(value = "stateChangeValues", ordinal = 396),
    @Range(value = "timerRunning", ordinal = 397),
    @Range(value = "timerState", ordinal = 398),
    @Range(value = "apduLength", ordinal = 399),
    @Range(value = "ipAddress", ordinal = 400),
    @Range(value = "ipDefaultGateway", ordinal = 401),
    @Range(value = "ipDhcpEnable", ordinal = 402),
    @Range(value = "ipDhcpLeaseTime", ordinal = 403),
    @Range(value = "ipDhcpLeaseTimeRemaining", ordinal = 404),
    @Range(value = "ipDhcpServer", ordinal = 405),
    @Range(value = "ipDnsServer", ordinal = 406),
    @Range(value = "bacnetIpGlobalAddress", ordinal = 407),
    @Range(value = "bacnetIpMode", ordinal = 408),
    @Range(value = "bacnetIpMulticastAddress", ordinal = 409),
    @Range(value = "bacnetIpNatTraversal", ordinal = 410),
    @Range(value = "ipSubnetMask", ordinal = 411),
    @Range(value = "bacnetIpUdpPort", ordinal = 412),
    @Range(value = "bbmdAcceptFdRegistrations", ordinal = 413),
    @Range(value = "bbmdBroadcastDistributionTable", ordinal = 414),
    @Range(value = "bbmdForeignDeviceTable", ordinal = 415),
    @Range(value = "changesPending", ordinal = 416),
    @Range(value = "command", ordinal = 417),
    @Range(value = "fdBbmdAddress", ordinal = 418),
    @Range(value = "fdSubscriptionLifetime", ordinal = 419),
    @Range(value = "linkSpeed", ordinal = 420),
    @Range(value = "linkSpeeds", ordinal = 421),
    @Range(value = "linkSpeedAutonegotiate", ordinal = 422),
    @Range(value = "macAddress", ordinal = 423),
    @Range(value = "networkInterfaceName", ordinal = 424),
    @Range(value = "networkNumber", ordinal = 425),
    @Range(value = "networkNumberQuality", ordinal = 426),
    @Range(value = "networkType", ordinal = 427),
    @Range(value = "routingTable", ordinal = 428),
    @Range(value = "virtualMacAddressTable", ordinal = 429),
    @Range(value = "commandTimeArray", ordinal = 430),
    @Range(value = "currentCommandPriority", ordinal = 431),
    @Range(value = "lastCommandTime", ordinal = 432),
    @Range(value = "valueSource", ordinal = 433),
    @Range(value = "valueSourceArray", ordinal = 434),
    @Range(value = "bacnetIpv6Mode", ordinal = 435),
    @Range(value = "ipv6Address", ordinal = 436),
    @Range(value = "ipv6PrefixLength", ordinal = 437),
    @Range(value = "bacnetIpv6UdpPort", ordinal = 438),
    @Range(value = "ipv6DefaultGateway", ordinal = 439),
    @Range(value = "bacnetIpv6MulticastAddress", ordinal = 440),
    @Range(value = "ipv6DnsServer", ordinal = 441),
    @Range(value = "ipv6AutoAddressingEnable", ordinal = 442),
    @Range(value = "ipv6DhcpLeaseTime", ordinal = 443),
    @Range(value = "ipv6DhcpLeaseTimeRemaining", ordinal = 444),
    @Range(value = "ipv6DhcpServer", ordinal = 445),
    @Range(value = "ipv6ZoneIndex", ordinal = 446),
    @Range(value = "assignedLandingCalls", ordinal = 447),
    @Range(value = "carAssignedDirection", ordinal = 448),
    @Range(value = "carDoorCommand", ordinal = 449),
    @Range(value = "carDoorStatus", ordinal = 450),
    @Range(value = "carDoorText", ordinal = 451),
    @Range(value = "carDoorZone", ordinal = 452),
    @Range(value = "carDriveStatus", ordinal = 453),
    @Range(value = "carLoad", ordinal = 454),
    @Range(value = "carLoadUnits", ordinal = 455),
    @Range(value = "carMode", ordinal = 456),
    @Range(value = "carMovingDirection", ordinal = 457),
    @Range(value = "carPosition", ordinal = 458),
    @Range(value = "elevatorGroup", ordinal = 459),
    @Range(value = "energyMeter", ordinal = 460),
    @Range(value = "energyMeterRef", ordinal = 461),
    @Range(value = "escalatorMode", ordinal = 462),
    @Range(value = "faultSignals", ordinal = 463),
    @Range(value = "floorText", ordinal = 464),
    @Range(value = "groupId", ordinal = 465),
    @Range(value = "groupMode", ordinal = 467),
    @Range(value = "higherDeck", ordinal = 468),
    @Range(value = "installationId", ordinal = 469),
    @Range(value = "landingCalls", ordinal = 470),
    @Range(value = "landingCallControl", ordinal = 471),
    @Range(value = "landingDoorStatus", ordinal = 472),
    @Range(value = "lowerDeck", ordinal = 473),
    @Range(value = "machineRoomId", ordinal = 474),
    @Range(value = "makingCarCall", ordinal = 475),
    @Range(value = "nextStoppingFloor", ordinal = 476),
    @Range(value = "operationDirection", ordinal = 477),
    @Range(value = "passengerAlarm", ordinal = 478),
    @Range(value = "powerMode", ordinal = 479),
    @Range(value = "registeredCarCall", ordinal = 480),
    @Range(value = "activeCovMultipleSubscriptions", ordinal = 481),
    @Range(value = "protocolLevel", ordinal = 482),
    @Range(value = "referencePort", ordinal = 483),
    @Range(value = "deployedProfileLocation", ordinal = 484),
    @Range(value = "profileLocation", ordinal = 485),
    @Range(value = "tags", ordinal = 486),
    @Range(value = "subordinateNodeTypes", ordinal = 487),
    @Range(value = "subordinateTags", ordinal = 488),
    @Range(value = "subordinateRelationships", ordinal = 489),
    @Range(value = "defaultSubordinateRelationship", ordinal = 490),
    @Range(value = "represents", ordinal = 491),
    @Range(value = "defaultPresentValue", ordinal = 492),
    @Range(value = "presentStage", ordinal = 493),
    @Range(value = "stages", ordinal = 494),
    @Range(value = "stageNames", ordinal = 495),
    @Range(value = "targetReferences", ordinal = 496),
    @Range(value = "auditSourceReporter", ordinal = 497),
    @Range(value = "auditLevel", ordinal = 498),
    @Range(value = "auditNotificationRecipient", ordinal = 499),
    @Range(value = "auditPriorityFilter", ordinal = 500),
    @Range(value = "auditableOperations", ordinal = 501),
    @Range(value = "deleteOnForward", ordinal = 502),
    @Range(value = "maximumSendDelay", ordinal = 503),
    @Range(value = "monitoredObjects", ordinal = 504),
    @Range(value = "sendNow", ordinal = 505),
    @Range(value = "floorNumber", ordinal = 506),
    @Range(value = "deviceUuid", ordinal = 507),
    @Range(value = "removed1", ordinal = 18),
    @Range(value = "issueConfirmedNotifications", ordinal = 51),
    @Range(value = "listOfSessionKeys", ordinal = 55),
    @Range(value = "protocolConformanceClass", ordinal = 95),
    @Range(value = "recipient", ordinal = 101),
    @Range(value = "currentNotifyTime", ordinal = 129),
    @Range(value = "previousNotifyTime", ordinal = 138),
    @Range(value = "masterExemption", ordinal = 284),
    @Range(value = "occupancyExemption", ordinal = 293),
    @Range(value = "passbackExemption", ordinal = 299)
  }
)
public final class BBacnetPropertyIdentifier
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetPropertyIdentifier(2679271090)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ackedTransitions. */
  public static final int ACKED_TRANSITIONS = 0;
  /** Ordinal value for ackRequired. */
  public static final int ACK_REQUIRED = 1;
  /** Ordinal value for action. */
  public static final int ACTION = 2;
  /** Ordinal value for actionText. */
  public static final int ACTION_TEXT = 3;
  /** Ordinal value for activeText. */
  public static final int ACTIVE_TEXT = 4;
  /** Ordinal value for activeVtSessions. */
  public static final int ACTIVE_VT_SESSIONS = 5;
  /** Ordinal value for alarmValue. */
  public static final int ALARM_VALUE = 6;
  /** Ordinal value for alarmValues. */
  public static final int ALARM_VALUES = 7;
  /** Ordinal value for all. */
  public static final int ALL = 8;
  /** Ordinal value for allWritesSuccessful. */
  public static final int ALL_WRITES_SUCCESSFUL = 9;
  /** Ordinal value for apduSegmentTimeout. */
  public static final int APDU_SEGMENT_TIMEOUT = 10;
  /** Ordinal value for apduTimeout. */
  public static final int APDU_TIMEOUT = 11;
  /** Ordinal value for applicationSoftwareVersion. */
  public static final int APPLICATION_SOFTWARE_VERSION = 12;
  /** Ordinal value for archive. */
  public static final int ARCHIVE = 13;
  /** Ordinal value for bias. */
  public static final int BIAS = 14;
  /** Ordinal value for changeOfStateCount. */
  public static final int CHANGE_OF_STATE_COUNT = 15;
  /** Ordinal value for changeOfStateTime. */
  public static final int CHANGE_OF_STATE_TIME = 16;
  /** Ordinal value for notificationClass. */
  public static final int NOTIFICATION_CLASS = 17;
  /** Ordinal value for controlledVariableReference. */
  public static final int CONTROLLED_VARIABLE_REFERENCE = 19;
  /** Ordinal value for controlledVariableUnits. */
  public static final int CONTROLLED_VARIABLE_UNITS = 20;
  /** Ordinal value for controlledVariableValue. */
  public static final int CONTROLLED_VARIABLE_VALUE = 21;
  /** Ordinal value for covIncrement. */
  public static final int COV_INCREMENT = 22;
  /** Ordinal value for dateList. */
  public static final int DATE_LIST = 23;
  /** Ordinal value for daylightSavingsStatus. */
  public static final int DAYLIGHT_SAVINGS_STATUS = 24;
  /** Ordinal value for deadband. */
  public static final int DEADBAND = 25;
  /** Ordinal value for derivativeConstant. */
  public static final int DERIVATIVE_CONSTANT = 26;
  /** Ordinal value for derivativeConstantUnits. */
  public static final int DERIVATIVE_CONSTANT_UNITS = 27;
  /** Ordinal value for description. */
  public static final int DESCRIPTION = 28;
  /** Ordinal value for descriptionOfHalt. */
  public static final int DESCRIPTION_OF_HALT = 29;
  /** Ordinal value for deviceAddressBinding. */
  public static final int DEVICE_ADDRESS_BINDING = 30;
  /** Ordinal value for deviceType. */
  public static final int DEVICE_TYPE = 31;
  /** Ordinal value for effectivePeriod. */
  public static final int EFFECTIVE_PERIOD = 32;
  /** Ordinal value for elapsedActiveTime. */
  public static final int ELAPSED_ACTIVE_TIME = 33;
  /** Ordinal value for errorLimit. */
  public static final int ERROR_LIMIT = 34;
  /** Ordinal value for eventEnable. */
  public static final int EVENT_ENABLE = 35;
  /** Ordinal value for eventState. */
  public static final int EVENT_STATE = 36;
  /** Ordinal value for eventType. */
  public static final int EVENT_TYPE = 37;
  /** Ordinal value for exceptionSchedule. */
  public static final int EXCEPTION_SCHEDULE = 38;
  /** Ordinal value for faultValues. */
  public static final int FAULT_VALUES = 39;
  /** Ordinal value for feedbackValue. */
  public static final int FEEDBACK_VALUE = 40;
  /** Ordinal value for fileAccessMethod. */
  public static final int FILE_ACCESS_METHOD = 41;
  /** Ordinal value for fileSize. */
  public static final int FILE_SIZE = 42;
  /** Ordinal value for fileType. */
  public static final int FILE_TYPE = 43;
  /** Ordinal value for firmwareRevision. */
  public static final int FIRMWARE_REVISION = 44;
  /** Ordinal value for highLimit. */
  public static final int HIGH_LIMIT = 45;
  /** Ordinal value for inactiveText. */
  public static final int INACTIVE_TEXT = 46;
  /** Ordinal value for inProcess. */
  public static final int IN_PROCESS = 47;
  /** Ordinal value for instanceOf. */
  public static final int INSTANCE_OF = 48;
  /** Ordinal value for integralConstant. */
  public static final int INTEGRAL_CONSTANT = 49;
  /** Ordinal value for integralConstantUnits. */
  public static final int INTEGRAL_CONSTANT_UNITS = 50;
  /** Ordinal value for limitEnable. */
  public static final int LIMIT_ENABLE = 52;
  /** Ordinal value for listOfGroupMembers. */
  public static final int LIST_OF_GROUP_MEMBERS = 53;
  /** Ordinal value for listOfObjectPropertyReferences. */
  public static final int LIST_OF_OBJECT_PROPERTY_REFERENCES = 54;
  /** Ordinal value for localDate. */
  public static final int LOCAL_DATE = 56;
  /** Ordinal value for localTime. */
  public static final int LOCAL_TIME = 57;
  /** Ordinal value for location. */
  public static final int LOCATION = 58;
  /** Ordinal value for lowLimit. */
  public static final int LOW_LIMIT = 59;
  /** Ordinal value for manipulatedVariableReference. */
  public static final int MANIPULATED_VARIABLE_REFERENCE = 60;
  /** Ordinal value for maximumOutput. */
  public static final int MAXIMUM_OUTPUT = 61;
  /** Ordinal value for maxApduLengthAccepted. */
  public static final int MAX_APDU_LENGTH_ACCEPTED = 62;
  /** Ordinal value for maxInfoFrames. */
  public static final int MAX_INFO_FRAMES = 63;
  /** Ordinal value for maxMaster. */
  public static final int MAX_MASTER = 64;
  /** Ordinal value for maxPresValue. */
  public static final int MAX_PRES_VALUE = 65;
  /** Ordinal value for minimumOffTime. */
  public static final int MINIMUM_OFF_TIME = 66;
  /** Ordinal value for minimumOnTime. */
  public static final int MINIMUM_ON_TIME = 67;
  /** Ordinal value for minimumOutput. */
  public static final int MINIMUM_OUTPUT = 68;
  /** Ordinal value for minPresValue. */
  public static final int MIN_PRES_VALUE = 69;
  /** Ordinal value for modelName. */
  public static final int MODEL_NAME = 70;
  /** Ordinal value for modificationDate. */
  public static final int MODIFICATION_DATE = 71;
  /** Ordinal value for notifyType. */
  public static final int NOTIFY_TYPE = 72;
  /** Ordinal value for numberOfApduRetries. */
  public static final int NUMBER_OF_APDU_RETRIES = 73;
  /** Ordinal value for numberOfStates. */
  public static final int NUMBER_OF_STATES = 74;
  /** Ordinal value for objectIdentifier. */
  public static final int OBJECT_IDENTIFIER = 75;
  /** Ordinal value for objectList. */
  public static final int OBJECT_LIST = 76;
  /** Ordinal value for objectName. */
  public static final int OBJECT_NAME = 77;
  /** Ordinal value for objectPropertyReference. */
  public static final int OBJECT_PROPERTY_REFERENCE = 78;
  /** Ordinal value for objectType. */
  public static final int OBJECT_TYPE = 79;
  /** Ordinal value for optional. */
  public static final int OPTIONAL = 80;
  /** Ordinal value for outOfService. */
  public static final int OUT_OF_SERVICE = 81;
  /** Ordinal value for outputUnits. */
  public static final int OUTPUT_UNITS = 82;
  /** Ordinal value for eventParameters. */
  public static final int EVENT_PARAMETERS = 83;
  /** Ordinal value for polarity. */
  public static final int POLARITY = 84;
  /** Ordinal value for presentValue. */
  public static final int PRESENT_VALUE = 85;
  /** Ordinal value for priority. */
  public static final int PRIORITY = 86;
  /** Ordinal value for priorityArray. */
  public static final int PRIORITY_ARRAY = 87;
  /** Ordinal value for priorityForWriting. */
  public static final int PRIORITY_FOR_WRITING = 88;
  /** Ordinal value for processIdentifier. */
  public static final int PROCESS_IDENTIFIER = 89;
  /** Ordinal value for programChange. */
  public static final int PROGRAM_CHANGE = 90;
  /** Ordinal value for programLocation. */
  public static final int PROGRAM_LOCATION = 91;
  /** Ordinal value for programState. */
  public static final int PROGRAM_STATE = 92;
  /** Ordinal value for proportionalConstant. */
  public static final int PROPORTIONAL_CONSTANT = 93;
  /** Ordinal value for proportionalConstantUnits. */
  public static final int PROPORTIONAL_CONSTANT_UNITS = 94;
  /** Ordinal value for protocolObjectTypesSupported. */
  public static final int PROTOCOL_OBJECT_TYPES_SUPPORTED = 96;
  /** Ordinal value for protocolServicesSupported. */
  public static final int PROTOCOL_SERVICES_SUPPORTED = 97;
  /** Ordinal value for protocolVersion. */
  public static final int PROTOCOL_VERSION = 98;
  /** Ordinal value for readOnly. */
  public static final int READ_ONLY = 99;
  /** Ordinal value for reasonForHalt. */
  public static final int REASON_FOR_HALT = 100;
  /** Ordinal value for recipientList. */
  public static final int RECIPIENT_LIST = 102;
  /** Ordinal value for reliability. */
  public static final int RELIABILITY = 103;
  /** Ordinal value for relinquishDefault. */
  public static final int RELINQUISH_DEFAULT = 104;
  /** Ordinal value for required. */
  public static final int REQUIRED = 105;
  /** Ordinal value for resolution. */
  public static final int RESOLUTION = 106;
  /** Ordinal value for segmentationSupported. */
  public static final int SEGMENTATION_SUPPORTED = 107;
  /** Ordinal value for setpoint. */
  public static final int SETPOINT = 108;
  /** Ordinal value for setpointReference. */
  public static final int SETPOINT_REFERENCE = 109;
  /** Ordinal value for stateText. */
  public static final int STATE_TEXT = 110;
  /** Ordinal value for statusFlags. */
  public static final int STATUS_FLAGS = 111;
  /** Ordinal value for systemStatus. */
  public static final int SYSTEM_STATUS = 112;
  /** Ordinal value for timeDelay. */
  public static final int TIME_DELAY = 113;
  /** Ordinal value for timeOfActiveTimeReset. */
  public static final int TIME_OF_ACTIVE_TIME_RESET = 114;
  /** Ordinal value for timeOfStateCountReset. */
  public static final int TIME_OF_STATE_COUNT_RESET = 115;
  /** Ordinal value for timeSynchronizationRecipients. */
  public static final int TIME_SYNCHRONIZATION_RECIPIENTS = 116;
  /** Ordinal value for units. */
  public static final int UNITS = 117;
  /** Ordinal value for updateInterval. */
  public static final int UPDATE_INTERVAL = 118;
  /** Ordinal value for utcOffset. */
  public static final int UTC_OFFSET = 119;
  /** Ordinal value for vendorIdentifier. */
  public static final int VENDOR_IDENTIFIER = 120;
  /** Ordinal value for vendorName. */
  public static final int VENDOR_NAME = 121;
  /** Ordinal value for vtClassesSupported. */
  public static final int VT_CLASSES_SUPPORTED = 122;
  /** Ordinal value for weeklySchedule. */
  public static final int WEEKLY_SCHEDULE = 123;
  /** Ordinal value for attemptedSamples. */
  public static final int ATTEMPTED_SAMPLES = 124;
  /** Ordinal value for averageValue. */
  public static final int AVERAGE_VALUE = 125;
  /** Ordinal value for bufferSize. */
  public static final int BUFFER_SIZE = 126;
  /** Ordinal value for clientCovIncrement. */
  public static final int CLIENT_COV_INCREMENT = 127;
  /** Ordinal value for covResubscriptionInterval. */
  public static final int COV_RESUBSCRIPTION_INTERVAL = 128;
  /** Ordinal value for eventTimeStamps. */
  public static final int EVENT_TIME_STAMPS = 130;
  /** Ordinal value for logBuffer. */
  public static final int LOG_BUFFER = 131;
  /** Ordinal value for logDeviceObjectProperty. */
  public static final int LOG_DEVICE_OBJECT_PROPERTY = 132;
  /** Ordinal value for enable. */
  public static final int ENABLE = 133;
  /** Ordinal value for logInterval. */
  public static final int LOG_INTERVAL = 134;
  /** Ordinal value for maximumValue. */
  public static final int MAXIMUM_VALUE = 135;
  /** Ordinal value for minimumValue. */
  public static final int MINIMUM_VALUE = 136;
  /** Ordinal value for notificationThreshold. */
  public static final int NOTIFICATION_THRESHOLD = 137;
  /** Ordinal value for protocolRevision. */
  public static final int PROTOCOL_REVISION = 139;
  /** Ordinal value for recordsSinceNotification. */
  public static final int RECORDS_SINCE_NOTIFICATION = 140;
  /** Ordinal value for recordCount. */
  public static final int RECORD_COUNT = 141;
  /** Ordinal value for startTime. */
  public static final int START_TIME = 142;
  /** Ordinal value for stopTime. */
  public static final int STOP_TIME = 143;
  /** Ordinal value for stopWhenFull. */
  public static final int STOP_WHEN_FULL = 144;
  /** Ordinal value for totalRecordCount. */
  public static final int TOTAL_RECORD_COUNT = 145;
  /** Ordinal value for validSamples. */
  public static final int VALID_SAMPLES = 146;
  /** Ordinal value for windowInterval. */
  public static final int WINDOW_INTERVAL = 147;
  /** Ordinal value for windowSamples. */
  public static final int WINDOW_SAMPLES = 148;
  /** Ordinal value for maximumValueTimestamp. */
  public static final int MAXIMUM_VALUE_TIMESTAMP = 149;
  /** Ordinal value for minimumValueTimestamp. */
  public static final int MINIMUM_VALUE_TIMESTAMP = 150;
  /** Ordinal value for varianceValue. */
  public static final int VARIANCE_VALUE = 151;
  /** Ordinal value for activeCovSubscriptions. */
  public static final int ACTIVE_COV_SUBSCRIPTIONS = 152;
  /** Ordinal value for backupFailureTimeout. */
  public static final int BACKUP_FAILURE_TIMEOUT = 153;
  /** Ordinal value for configurationFiles. */
  public static final int CONFIGURATION_FILES = 154;
  /** Ordinal value for databaseRevision. */
  public static final int DATABASE_REVISION = 155;
  /** Ordinal value for directReading. */
  public static final int DIRECT_READING = 156;
  /** Ordinal value for lastRestoreTime. */
  public static final int LAST_RESTORE_TIME = 157;
  /** Ordinal value for maintenanceRequired. */
  public static final int MAINTENANCE_REQUIRED = 158;
  /** Ordinal value for memberOf. */
  public static final int MEMBER_OF = 159;
  /** Ordinal value for mode. */
  public static final int MODE = 160;
  /** Ordinal value for operationExpected. */
  public static final int OPERATION_EXPECTED = 161;
  /** Ordinal value for setting. */
  public static final int SETTING = 162;
  /** Ordinal value for silenced. */
  public static final int SILENCED = 163;
  /** Ordinal value for trackingValue. */
  public static final int TRACKING_VALUE = 164;
  /** Ordinal value for zoneMembers. */
  public static final int ZONE_MEMBERS = 165;
  /** Ordinal value for lifeSafetyAlarmValues. */
  public static final int LIFE_SAFETY_ALARM_VALUES = 166;
  /** Ordinal value for maxSegmentsAccepted. */
  public static final int MAX_SEGMENTS_ACCEPTED = 167;
  /** Ordinal value for profileName. */
  public static final int PROFILE_NAME = 168;
  /** Ordinal value for autoSlaveDiscovery. */
  public static final int AUTO_SLAVE_DISCOVERY = 169;
  /** Ordinal value for manualSlaveAddressBinding. */
  public static final int MANUAL_SLAVE_ADDRESS_BINDING = 170;
  /** Ordinal value for slaveAddressBinding. */
  public static final int SLAVE_ADDRESS_BINDING = 171;
  /** Ordinal value for slaveProxyEnable. */
  public static final int SLAVE_PROXY_ENABLE = 172;
  /** Ordinal value for lastNotifyRecord. */
  public static final int LAST_NOTIFY_RECORD = 173;
  /** Ordinal value for scheduleDefault. */
  public static final int SCHEDULE_DEFAULT = 174;
  /** Ordinal value for acceptedModes. */
  public static final int ACCEPTED_MODES = 175;
  /** Ordinal value for adjustValue. */
  public static final int ADJUST_VALUE = 176;
  /** Ordinal value for count. */
  public static final int COUNT = 177;
  /** Ordinal value for countBeforeChange. */
  public static final int COUNT_BEFORE_CHANGE = 178;
  /** Ordinal value for countChangeTime. */
  public static final int COUNT_CHANGE_TIME = 179;
  /** Ordinal value for covPeriod. */
  public static final int COV_PERIOD = 180;
  /** Ordinal value for inputReference. */
  public static final int INPUT_REFERENCE = 181;
  /** Ordinal value for limitMonitoringInterval. */
  public static final int LIMIT_MONITORING_INTERVAL = 182;
  /** Ordinal value for loggingObject. */
  public static final int LOGGING_OBJECT = 183;
  /** Ordinal value for loggingRecord. */
  public static final int LOGGING_RECORD = 184;
  /** Ordinal value for prescale. */
  public static final int PRESCALE = 185;
  /** Ordinal value for pulseRate. */
  public static final int PULSE_RATE = 186;
  /** Ordinal value for scale. */
  public static final int SCALE = 187;
  /** Ordinal value for scaleFactor. */
  public static final int SCALE_FACTOR = 188;
  /** Ordinal value for updateTime. */
  public static final int UPDATE_TIME = 189;
  /** Ordinal value for valueBeforeChange. */
  public static final int VALUE_BEFORE_CHANGE = 190;
  /** Ordinal value for valueSet. */
  public static final int VALUE_SET = 191;
  /** Ordinal value for valueChangeTime. */
  public static final int VALUE_CHANGE_TIME = 192;
  /** Ordinal value for alignIntervals. */
  public static final int ALIGN_INTERVALS = 193;
  /** Ordinal value for intervalOffset. */
  public static final int INTERVAL_OFFSET = 195;
  /** Ordinal value for lastRestartReason. */
  public static final int LAST_RESTART_REASON = 196;
  /** Ordinal value for loggingType. */
  public static final int LOGGING_TYPE = 197;
  /** Ordinal value for restartNotificationRecipients. */
  public static final int RESTART_NOTIFICATION_RECIPIENTS = 202;
  /** Ordinal value for timeOfDeviceRestart. */
  public static final int TIME_OF_DEVICE_RESTART = 203;
  /** Ordinal value for timeSynchronizationInterval. */
  public static final int TIME_SYNCHRONIZATION_INTERVAL = 204;
  /** Ordinal value for trigger. */
  public static final int TRIGGER = 205;
  /** Ordinal value for utcTimeSynchronizationRecipients. */
  public static final int UTC_TIME_SYNCHRONIZATION_RECIPIENTS = 206;
  /** Ordinal value for nodeSubtype. */
  public static final int NODE_SUBTYPE = 207;
  /** Ordinal value for nodeType. */
  public static final int NODE_TYPE = 208;
  /** Ordinal value for structuredObjectList. */
  public static final int STRUCTURED_OBJECT_LIST = 209;
  /** Ordinal value for subordinateAnnotations. */
  public static final int SUBORDINATE_ANNOTATIONS = 210;
  /** Ordinal value for subordinateList. */
  public static final int SUBORDINATE_LIST = 211;
  /** Ordinal value for actualShedLevel. */
  public static final int ACTUAL_SHED_LEVEL = 212;
  /** Ordinal value for dutyWindow. */
  public static final int DUTY_WINDOW = 213;
  /** Ordinal value for expectedShedLevel. */
  public static final int EXPECTED_SHED_LEVEL = 214;
  /** Ordinal value for fullDutyBaseline. */
  public static final int FULL_DUTY_BASELINE = 215;
  /** Ordinal value for requestedShedLevel. */
  public static final int REQUESTED_SHED_LEVEL = 218;
  /** Ordinal value for shedDuration. */
  public static final int SHED_DURATION = 219;
  /** Ordinal value for shedLevelDescriptions. */
  public static final int SHED_LEVEL_DESCRIPTIONS = 220;
  /** Ordinal value for shedLevels. */
  public static final int SHED_LEVELS = 221;
  /** Ordinal value for stateDescription. */
  public static final int STATE_DESCRIPTION = 222;
  /** Ordinal value for doorAlarmState. */
  public static final int DOOR_ALARM_STATE = 226;
  /** Ordinal value for doorExtendedPulseTime. */
  public static final int DOOR_EXTENDED_PULSE_TIME = 227;
  /** Ordinal value for doorMembers. */
  public static final int DOOR_MEMBERS = 228;
  /** Ordinal value for doorOpenTooLongTime. */
  public static final int DOOR_OPEN_TOO_LONG_TIME = 229;
  /** Ordinal value for doorPulseTime. */
  public static final int DOOR_PULSE_TIME = 230;
  /** Ordinal value for doorStatus. */
  public static final int DOOR_STATUS = 231;
  /** Ordinal value for doorUnlockDelayTime. */
  public static final int DOOR_UNLOCK_DELAY_TIME = 232;
  /** Ordinal value for lockStatus. */
  public static final int LOCK_STATUS = 233;
  /** Ordinal value for maskedAlarmValues. */
  public static final int MASKED_ALARM_VALUES = 234;
  /** Ordinal value for securedStatus. */
  public static final int SECURED_STATUS = 235;
  /** Ordinal value for absenteeLimit. */
  public static final int ABSENTEE_LIMIT = 244;
  /** Ordinal value for accessAlarmEvents. */
  public static final int ACCESS_ALARM_EVENTS = 245;
  /** Ordinal value for accessDoors. */
  public static final int ACCESS_DOORS = 246;
  /** Ordinal value for accessEvent. */
  public static final int ACCESS_EVENT = 247;
  /** Ordinal value for accessEventAuthenticationFactor. */
  public static final int ACCESS_EVENT_AUTHENTICATION_FACTOR = 248;
  /** Ordinal value for accessEventCredential. */
  public static final int ACCESS_EVENT_CREDENTIAL = 249;
  /** Ordinal value for accessEventTime. */
  public static final int ACCESS_EVENT_TIME = 250;
  /** Ordinal value for accessTransactionEvents. */
  public static final int ACCESS_TRANSACTION_EVENTS = 251;
  /** Ordinal value for accompaniment. */
  public static final int ACCOMPANIMENT = 252;
  /** Ordinal value for accompanimentTime. */
  public static final int ACCOMPANIMENT_TIME = 253;
  /** Ordinal value for activationTime. */
  public static final int ACTIVATION_TIME = 254;
  /** Ordinal value for activeAuthenticationPolicy. */
  public static final int ACTIVE_AUTHENTICATION_POLICY = 255;
  /** Ordinal value for assignedAccessRights. */
  public static final int ASSIGNED_ACCESS_RIGHTS = 256;
  /** Ordinal value for authenticationFactors. */
  public static final int AUTHENTICATION_FACTORS = 257;
  /** Ordinal value for authenticationPolicyList. */
  public static final int AUTHENTICATION_POLICY_LIST = 258;
  /** Ordinal value for authenticationPolicyNames. */
  public static final int AUTHENTICATION_POLICY_NAMES = 259;
  /** Ordinal value for authenticationStatus. */
  public static final int AUTHENTICATION_STATUS = 260;
  /** Ordinal value for authorizationMode. */
  public static final int AUTHORIZATION_MODE = 261;
  /** Ordinal value for belongsTo. */
  public static final int BELONGS_TO = 262;
  /** Ordinal value for credentialDisable. */
  public static final int CREDENTIAL_DISABLE = 263;
  /** Ordinal value for credentialStatus. */
  public static final int CREDENTIAL_STATUS = 264;
  /** Ordinal value for credentials. */
  public static final int CREDENTIALS = 265;
  /** Ordinal value for credentialsInZone. */
  public static final int CREDENTIALS_IN_ZONE = 266;
  /** Ordinal value for daysRemaining. */
  public static final int DAYS_REMAINING = 267;
  /** Ordinal value for entryPoints. */
  public static final int ENTRY_POINTS = 268;
  /** Ordinal value for exitPoints. */
  public static final int EXIT_POINTS = 269;
  /** Ordinal value for expiryTime. */
  public static final int EXPIRY_TIME = 270;
  /** Ordinal value for extendedTimeEnable. */
  public static final int EXTENDED_TIME_ENABLE = 271;
  /** Ordinal value for failedAttemptEvents. */
  public static final int FAILED_ATTEMPT_EVENTS = 272;
  /** Ordinal value for failedAttempts. */
  public static final int FAILED_ATTEMPTS = 273;
  /** Ordinal value for failedAttemptsTime. */
  public static final int FAILED_ATTEMPTS_TIME = 274;
  /** Ordinal value for lastAccessEvent. */
  public static final int LAST_ACCESS_EVENT = 275;
  /** Ordinal value for lastAccessPoint. */
  public static final int LAST_ACCESS_POINT = 276;
  /** Ordinal value for lastCredentialAdded. */
  public static final int LAST_CREDENTIAL_ADDED = 277;
  /** Ordinal value for lastCredentialAddedTime. */
  public static final int LAST_CREDENTIAL_ADDED_TIME = 278;
  /** Ordinal value for lastCredentialRemoved. */
  public static final int LAST_CREDENTIAL_REMOVED = 279;
  /** Ordinal value for lastCredentialRemovedTime. */
  public static final int LAST_CREDENTIAL_REMOVED_TIME = 280;
  /** Ordinal value for lastUseTime. */
  public static final int LAST_USE_TIME = 281;
  /** Ordinal value for lockout. */
  public static final int LOCKOUT = 282;
  /** Ordinal value for lockoutRelinquishTime. */
  public static final int LOCKOUT_RELINQUISH_TIME = 283;
  /** Ordinal value for maxFailedAttempts. */
  public static final int MAX_FAILED_ATTEMPTS = 285;
  /** Ordinal value for members. */
  public static final int MEMBERS = 286;
  /** Ordinal value for musterPoint. */
  public static final int MUSTER_POINT = 287;
  /** Ordinal value for negativeAccessRules. */
  public static final int NEGATIVE_ACCESS_RULES = 288;
  /** Ordinal value for numberOfAuthenticationPolicies. */
  public static final int NUMBER_OF_AUTHENTICATION_POLICIES = 289;
  /** Ordinal value for occupancyCount. */
  public static final int OCCUPANCY_COUNT = 290;
  /** Ordinal value for occupancyCountAdjust. */
  public static final int OCCUPANCY_COUNT_ADJUST = 291;
  /** Ordinal value for occupancyCountEnable. */
  public static final int OCCUPANCY_COUNT_ENABLE = 292;
  /** Ordinal value for occupancyLowerLimit. */
  public static final int OCCUPANCY_LOWER_LIMIT = 294;
  /** Ordinal value for occupancyLowerLimitEnforced. */
  public static final int OCCUPANCY_LOWER_LIMIT_ENFORCED = 295;
  /** Ordinal value for occupancyState. */
  public static final int OCCUPANCY_STATE = 296;
  /** Ordinal value for occupancyUpperLimit. */
  public static final int OCCUPANCY_UPPER_LIMIT = 297;
  /** Ordinal value for occupancyUpperLimitEnforced. */
  public static final int OCCUPANCY_UPPER_LIMIT_ENFORCED = 298;
  /** Ordinal value for passbackMode. */
  public static final int PASSBACK_MODE = 300;
  /** Ordinal value for passbackTimeout. */
  public static final int PASSBACK_TIMEOUT = 301;
  /** Ordinal value for positiveAccessRules. */
  public static final int POSITIVE_ACCESS_RULES = 302;
  /** Ordinal value for reasonForDisable. */
  public static final int REASON_FOR_DISABLE = 303;
  /** Ordinal value for supportedFormats. */
  public static final int SUPPORTED_FORMATS = 304;
  /** Ordinal value for supportedFormatClasses. */
  public static final int SUPPORTED_FORMAT_CLASSES = 305;
  /** Ordinal value for threatAuthority. */
  public static final int THREAT_AUTHORITY = 306;
  /** Ordinal value for threatLevel. */
  public static final int THREAT_LEVEL = 307;
  /** Ordinal value for traceFlag. */
  public static final int TRACE_FLAG = 308;
  /** Ordinal value for transactionNotificationClass. */
  public static final int TRANSACTION_NOTIFICATION_CLASS = 309;
  /** Ordinal value for userExternalIdentifier. */
  public static final int USER_EXTERNAL_IDENTIFIER = 310;
  /** Ordinal value for userInformationReference. */
  public static final int USER_INFORMATION_REFERENCE = 311;
  /** Ordinal value for userName. */
  public static final int USER_NAME = 317;
  /** Ordinal value for userType. */
  public static final int USER_TYPE = 318;
  /** Ordinal value for usesRemaining. */
  public static final int USES_REMAINING = 319;
  /** Ordinal value for zoneFrom. */
  public static final int ZONE_FROM = 320;
  /** Ordinal value for zoneTo. */
  public static final int ZONE_TO = 321;
  /** Ordinal value for accessEventTag. */
  public static final int ACCESS_EVENT_TAG = 322;
  /** Ordinal value for globalIdentifier. */
  public static final int GLOBAL_IDENTIFIER = 323;
  /** Ordinal value for verificationTime. */
  public static final int VERIFICATION_TIME = 326;
  /** Ordinal value for backupAndRestoreState. */
  public static final int BACKUP_AND_RESTORE_STATE = 338;
  /** Ordinal value for backupPreparationTime. */
  public static final int BACKUP_PREPARATION_TIME = 339;
  /** Ordinal value for restoreCompletionTime. */
  public static final int RESTORE_COMPLETION_TIME = 340;
  /** Ordinal value for restorePreparationTime. */
  public static final int RESTORE_PREPARATION_TIME = 341;
  /** Ordinal value for bitMask. */
  public static final int BIT_MASK = 342;
  /** Ordinal value for bitText. */
  public static final int BIT_TEXT = 343;
  /** Ordinal value for isUtc. */
  public static final int IS_UTC = 344;
  /** Ordinal value for groupMembers. */
  public static final int GROUP_MEMBERS = 345;
  /** Ordinal value for groupMemberNames. */
  public static final int GROUP_MEMBER_NAMES = 346;
  /** Ordinal value for memberStatusDlags. */
  public static final int MEMBER_STATUS_DLAGS = 347;
  /** Ordinal value for requestedUpdateInterval. */
  public static final int REQUESTED_UPDATE_INTERVAL = 348;
  /** Ordinal value for covuPeriod. */
  public static final int COVU_PERIOD = 349;
  /** Ordinal value for covuRecipients. */
  public static final int COVU_RECIPIENTS = 350;
  /** Ordinal value for eventMessageTexts. */
  public static final int EVENT_MESSAGE_TEXTS = 351;
  /** Ordinal value for eventMessageTextsConfig. */
  public static final int EVENT_MESSAGE_TEXTS_CONFIG = 352;
  /** Ordinal value for eventDetectionEnable. */
  public static final int EVENT_DETECTION_ENABLE = 353;
  /** Ordinal value for eventAlgorithmInhibit. */
  public static final int EVENT_ALGORITHM_INHIBIT = 354;
  /** Ordinal value for eventAlgorithmInhibitRef. */
  public static final int EVENT_ALGORITHM_INHIBIT_REF = 355;
  /** Ordinal value for timeDelayNormal. */
  public static final int TIME_DELAY_NORMAL = 356;
  /** Ordinal value for reliabilityEvaluationInhibit. */
  public static final int RELIABILITY_EVALUATION_INHIBIT = 357;
  /** Ordinal value for faultParameters. */
  public static final int FAULT_PARAMETERS = 358;
  /** Ordinal value for faultType. */
  public static final int FAULT_TYPE = 359;
  /** Ordinal value for localForwardingOnly. */
  public static final int LOCAL_FORWARDING_ONLY = 360;
  /** Ordinal value for processIdentifierFilter. */
  public static final int PROCESS_IDENTIFIER_FILTER = 361;
  /** Ordinal value for subscribedRecipients. */
  public static final int SUBSCRIBED_RECIPIENTS = 362;
  /** Ordinal value for portFilter. */
  public static final int PORT_FILTER = 363;
  /** Ordinal value for authorizationExemptions. */
  public static final int AUTHORIZATION_EXEMPTIONS = 364;
  /** Ordinal value for allowGroupDelayInhibit. */
  public static final int ALLOW_GROUP_DELAY_INHIBIT = 365;
  /** Ordinal value for channelNumber. */
  public static final int CHANNEL_NUMBER = 366;
  /** Ordinal value for controlGroups. */
  public static final int CONTROL_GROUPS = 367;
  /** Ordinal value for executionDelay. */
  public static final int EXECUTION_DELAY = 368;
  /** Ordinal value for lastPriority. */
  public static final int LAST_PRIORITY = 369;
  /** Ordinal value for writeStatus. */
  public static final int WRITE_STATUS = 370;
  /** Ordinal value for propertyList. */
  public static final int PROPERTY_LIST = 371;
  /** Ordinal value for serialNumber. */
  public static final int SERIAL_NUMBER = 372;
  /** Ordinal value for blinkWarnEnable. */
  public static final int BLINK_WARN_ENABLE = 373;
  /** Ordinal value for defaultFadetime. */
  public static final int DEFAULT_FADETIME = 374;
  /** Ordinal value for defaultRamprate. */
  public static final int DEFAULT_RAMPRATE = 375;
  /** Ordinal value for defaultStepIncrement. */
  public static final int DEFAULT_STEP_INCREMENT = 376;
  /** Ordinal value for egressTime. */
  public static final int EGRESS_TIME = 377;
  /** Ordinal value for inProgress. */
  public static final int IN_PROGRESS = 378;
  /** Ordinal value for instantaneousPower. */
  public static final int INSTANTANEOUS_POWER = 379;
  /** Ordinal value for lightingCommand. */
  public static final int LIGHTING_COMMAND = 380;
  /** Ordinal value for lightingCommandDefaultPriority. */
  public static final int LIGHTING_COMMAND_DEFAULT_PRIORITY = 381;
  /** Ordinal value for maxActualValue. */
  public static final int MAX_ACTUAL_VALUE = 382;
  /** Ordinal value for minActualValue. */
  public static final int MIN_ACTUAL_VALUE = 383;
  /** Ordinal value for power. */
  public static final int POWER = 384;
  /** Ordinal value for transition. */
  public static final int TRANSITION = 385;
  /** Ordinal value for egressActive. */
  public static final int EGRESS_ACTIVE = 386;
  /** Ordinal value for interfaceValue. */
  public static final int INTERFACE_VALUE = 387;
  /** Ordinal value for faultHighLimit. */
  public static final int FAULT_HIGH_LIMIT = 388;
  /** Ordinal value for faultLowLimit. */
  public static final int FAULT_LOW_LIMIT = 389;
  /** Ordinal value for lowDiffLimit. */
  public static final int LOW_DIFF_LIMIT = 390;
  /** Ordinal value for strikeCount. */
  public static final int STRIKE_COUNT = 391;
  /** Ordinal value for timeOfStrikeCountReset. */
  public static final int TIME_OF_STRIKE_COUNT_RESET = 392;
  /** Ordinal value for defaultTimeout. */
  public static final int DEFAULT_TIMEOUT = 393;
  /** Ordinal value for initialTimeout. */
  public static final int INITIAL_TIMEOUT = 394;
  /** Ordinal value for lastStateChange. */
  public static final int LAST_STATE_CHANGE = 395;
  /** Ordinal value for stateChangeValues. */
  public static final int STATE_CHANGE_VALUES = 396;
  /** Ordinal value for timerRunning. */
  public static final int TIMER_RUNNING = 397;
  /** Ordinal value for timerState. */
  public static final int TIMER_STATE = 398;
  /** Ordinal value for apduLength. */
  public static final int APDU_LENGTH = 399;
  /** Ordinal value for ipAddress. */
  public static final int IP_ADDRESS = 400;
  /** Ordinal value for ipDefaultGateway. */
  public static final int IP_DEFAULT_GATEWAY = 401;
  /** Ordinal value for ipDhcpEnable. */
  public static final int IP_DHCP_ENABLE = 402;
  /** Ordinal value for ipDhcpLeaseTime. */
  public static final int IP_DHCP_LEASE_TIME = 403;
  /** Ordinal value for ipDhcpLeaseTimeRemaining. */
  public static final int IP_DHCP_LEASE_TIME_REMAINING = 404;
  /** Ordinal value for ipDhcpServer. */
  public static final int IP_DHCP_SERVER = 405;
  /** Ordinal value for ipDnsServer. */
  public static final int IP_DNS_SERVER = 406;
  /** Ordinal value for bacnetIpGlobalAddress. */
  public static final int BACNET_IP_GLOBAL_ADDRESS = 407;
  /** Ordinal value for bacnetIpMode. */
  public static final int BACNET_IP_MODE = 408;
  /** Ordinal value for bacnetIpMulticastAddress. */
  public static final int BACNET_IP_MULTICAST_ADDRESS = 409;
  /** Ordinal value for bacnetIpNatTraversal. */
  public static final int BACNET_IP_NAT_TRAVERSAL = 410;
  /** Ordinal value for ipSubnetMask. */
  public static final int IP_SUBNET_MASK = 411;
  /** Ordinal value for bacnetIpUdpPort. */
  public static final int BACNET_IP_UDP_PORT = 412;
  /** Ordinal value for bbmdAcceptFdRegistrations. */
  public static final int BBMD_ACCEPT_FD_REGISTRATIONS = 413;
  /** Ordinal value for bbmdBroadcastDistributionTable. */
  public static final int BBMD_BROADCAST_DISTRIBUTION_TABLE = 414;
  /** Ordinal value for bbmdForeignDeviceTable. */
  public static final int BBMD_FOREIGN_DEVICE_TABLE = 415;
  /** Ordinal value for changesPending. */
  public static final int CHANGES_PENDING = 416;
  /** Ordinal value for command. */
  public static final int COMMAND = 417;
  /** Ordinal value for fdBbmdAddress. */
  public static final int FD_BBMD_ADDRESS = 418;
  /** Ordinal value for fdSubscriptionLifetime. */
  public static final int FD_SUBSCRIPTION_LIFETIME = 419;
  /** Ordinal value for linkSpeed. */
  public static final int LINK_SPEED = 420;
  /** Ordinal value for linkSpeeds. */
  public static final int LINK_SPEEDS = 421;
  /** Ordinal value for linkSpeedAutonegotiate. */
  public static final int LINK_SPEED_AUTONEGOTIATE = 422;
  /** Ordinal value for macAddress. */
  public static final int MAC_ADDRESS = 423;
  /** Ordinal value for networkInterfaceName. */
  public static final int NETWORK_INTERFACE_NAME = 424;
  /** Ordinal value for networkNumber. */
  public static final int NETWORK_NUMBER = 425;
  /** Ordinal value for networkNumberQuality. */
  public static final int NETWORK_NUMBER_QUALITY = 426;
  /** Ordinal value for networkType. */
  public static final int NETWORK_TYPE = 427;
  /** Ordinal value for routingTable. */
  public static final int ROUTING_TABLE = 428;
  /** Ordinal value for virtualMacAddressTable. */
  public static final int VIRTUAL_MAC_ADDRESS_TABLE = 429;
  /** Ordinal value for commandTimeArray. */
  public static final int COMMAND_TIME_ARRAY = 430;
  /** Ordinal value for currentCommandPriority. */
  public static final int CURRENT_COMMAND_PRIORITY = 431;
  /** Ordinal value for lastCommandTime. */
  public static final int LAST_COMMAND_TIME = 432;
  /** Ordinal value for valueSource. */
  public static final int VALUE_SOURCE = 433;
  /** Ordinal value for valueSourceArray. */
  public static final int VALUE_SOURCE_ARRAY = 434;
  /** Ordinal value for bacnetIpv6Mode. */
  public static final int BACNET_IPV_6MODE = 435;
  /** Ordinal value for ipv6Address. */
  public static final int IPV_6ADDRESS = 436;
  /** Ordinal value for ipv6PrefixLength. */
  public static final int IPV_6PREFIX_LENGTH = 437;
  /** Ordinal value for bacnetIpv6UdpPort. */
  public static final int BACNET_IPV_6UDP_PORT = 438;
  /** Ordinal value for ipv6DefaultGateway. */
  public static final int IPV_6DEFAULT_GATEWAY = 439;
  /** Ordinal value for bacnetIpv6MulticastAddress. */
  public static final int BACNET_IPV_6MULTICAST_ADDRESS = 440;
  /** Ordinal value for ipv6DnsServer. */
  public static final int IPV_6DNS_SERVER = 441;
  /** Ordinal value for ipv6AutoAddressingEnable. */
  public static final int IPV_6AUTO_ADDRESSING_ENABLE = 442;
  /** Ordinal value for ipv6DhcpLeaseTime. */
  public static final int IPV_6DHCP_LEASE_TIME = 443;
  /** Ordinal value for ipv6DhcpLeaseTimeRemaining. */
  public static final int IPV_6DHCP_LEASE_TIME_REMAINING = 444;
  /** Ordinal value for ipv6DhcpServer. */
  public static final int IPV_6DHCP_SERVER = 445;
  /** Ordinal value for ipv6ZoneIndex. */
  public static final int IPV_6ZONE_INDEX = 446;
  /** Ordinal value for assignedLandingCalls. */
  public static final int ASSIGNED_LANDING_CALLS = 447;
  /** Ordinal value for carAssignedDirection. */
  public static final int CAR_ASSIGNED_DIRECTION = 448;
  /** Ordinal value for carDoorCommand. */
  public static final int CAR_DOOR_COMMAND = 449;
  /** Ordinal value for carDoorStatus. */
  public static final int CAR_DOOR_STATUS = 450;
  /** Ordinal value for carDoorText. */
  public static final int CAR_DOOR_TEXT = 451;
  /** Ordinal value for carDoorZone. */
  public static final int CAR_DOOR_ZONE = 452;
  /** Ordinal value for carDriveStatus. */
  public static final int CAR_DRIVE_STATUS = 453;
  /** Ordinal value for carLoad. */
  public static final int CAR_LOAD = 454;
  /** Ordinal value for carLoadUnits. */
  public static final int CAR_LOAD_UNITS = 455;
  /** Ordinal value for carMode. */
  public static final int CAR_MODE = 456;
  /** Ordinal value for carMovingDirection. */
  public static final int CAR_MOVING_DIRECTION = 457;
  /** Ordinal value for carPosition. */
  public static final int CAR_POSITION = 458;
  /** Ordinal value for elevatorGroup. */
  public static final int ELEVATOR_GROUP = 459;
  /** Ordinal value for energyMeter. */
  public static final int ENERGY_METER = 460;
  /** Ordinal value for energyMeterRef. */
  public static final int ENERGY_METER_REF = 461;
  /** Ordinal value for escalatorMode. */
  public static final int ESCALATOR_MODE = 462;
  /** Ordinal value for faultSignals. */
  public static final int FAULT_SIGNALS = 463;
  /** Ordinal value for floorText. */
  public static final int FLOOR_TEXT = 464;
  /** Ordinal value for groupId. */
  public static final int GROUP_ID = 465;
  /** Ordinal value for groupMode. */
  public static final int GROUP_MODE = 467;
  /** Ordinal value for higherDeck. */
  public static final int HIGHER_DECK = 468;
  /** Ordinal value for installationId. */
  public static final int INSTALLATION_ID = 469;
  /** Ordinal value for landingCalls. */
  public static final int LANDING_CALLS = 470;
  /** Ordinal value for landingCallControl. */
  public static final int LANDING_CALL_CONTROL = 471;
  /** Ordinal value for landingDoorStatus. */
  public static final int LANDING_DOOR_STATUS = 472;
  /** Ordinal value for lowerDeck. */
  public static final int LOWER_DECK = 473;
  /** Ordinal value for machineRoomId. */
  public static final int MACHINE_ROOM_ID = 474;
  /** Ordinal value for makingCarCall. */
  public static final int MAKING_CAR_CALL = 475;
  /** Ordinal value for nextStoppingFloor. */
  public static final int NEXT_STOPPING_FLOOR = 476;
  /** Ordinal value for operationDirection. */
  public static final int OPERATION_DIRECTION = 477;
  /** Ordinal value for passengerAlarm. */
  public static final int PASSENGER_ALARM = 478;
  /** Ordinal value for powerMode. */
  public static final int POWER_MODE = 479;
  /** Ordinal value for registeredCarCall. */
  public static final int REGISTERED_CAR_CALL = 480;
  /** Ordinal value for activeCovMultipleSubscriptions. */
  public static final int ACTIVE_COV_MULTIPLE_SUBSCRIPTIONS = 481;
  /** Ordinal value for protocolLevel. */
  public static final int PROTOCOL_LEVEL = 482;
  /** Ordinal value for referencePort. */
  public static final int REFERENCE_PORT = 483;
  /** Ordinal value for deployedProfileLocation. */
  public static final int DEPLOYED_PROFILE_LOCATION = 484;
  /** Ordinal value for profileLocation. */
  public static final int PROFILE_LOCATION = 485;
  /** Ordinal value for tags. */
  public static final int TAGS = 486;
  /** Ordinal value for subordinateNodeTypes. */
  public static final int SUBORDINATE_NODE_TYPES = 487;
  /** Ordinal value for subordinateTags. */
  public static final int SUBORDINATE_TAGS = 488;
  /** Ordinal value for subordinateRelationships. */
  public static final int SUBORDINATE_RELATIONSHIPS = 489;
  /** Ordinal value for defaultSubordinateRelationship. */
  public static final int DEFAULT_SUBORDINATE_RELATIONSHIP = 490;
  /** Ordinal value for represents. */
  public static final int REPRESENTS = 491;
  /** Ordinal value for defaultPresentValue. */
  public static final int DEFAULT_PRESENT_VALUE = 492;
  /** Ordinal value for presentStage. */
  public static final int PRESENT_STAGE = 493;
  /** Ordinal value for stages. */
  public static final int STAGES = 494;
  /** Ordinal value for stageNames. */
  public static final int STAGE_NAMES = 495;
  /** Ordinal value for targetReferences. */
  public static final int TARGET_REFERENCES = 496;
  /** Ordinal value for auditSourceReporter. */
  public static final int AUDIT_SOURCE_REPORTER = 497;
  /** Ordinal value for auditLevel. */
  public static final int AUDIT_LEVEL = 498;
  /** Ordinal value for auditNotificationRecipient. */
  public static final int AUDIT_NOTIFICATION_RECIPIENT = 499;
  /** Ordinal value for auditPriorityFilter. */
  public static final int AUDIT_PRIORITY_FILTER = 500;
  /** Ordinal value for auditableOperations. */
  public static final int AUDITABLE_OPERATIONS = 501;
  /** Ordinal value for deleteOnForward. */
  public static final int DELETE_ON_FORWARD = 502;
  /** Ordinal value for maximumSendDelay. */
  public static final int MAXIMUM_SEND_DELAY = 503;
  /** Ordinal value for monitoredObjects. */
  public static final int MONITORED_OBJECTS = 504;
  /** Ordinal value for sendNow. */
  public static final int SEND_NOW = 505;
  /** Ordinal value for floorNumber. */
  public static final int FLOOR_NUMBER = 506;
  /** Ordinal value for deviceUuid. */
  public static final int DEVICE_UUID = 507;
  /** Ordinal value for removed1. */
  public static final int REMOVED_1 = 18;
  /** Ordinal value for issueConfirmedNotifications. */
  public static final int ISSUE_CONFIRMED_NOTIFICATIONS = 51;
  /** Ordinal value for listOfSessionKeys. */
  public static final int LIST_OF_SESSION_KEYS = 55;
  /** Ordinal value for protocolConformanceClass. */
  public static final int PROTOCOL_CONFORMANCE_CLASS = 95;
  /** Ordinal value for recipient. */
  public static final int RECIPIENT = 101;
  /** Ordinal value for currentNotifyTime. */
  public static final int CURRENT_NOTIFY_TIME = 129;
  /** Ordinal value for previousNotifyTime. */
  public static final int PREVIOUS_NOTIFY_TIME = 138;
  /** Ordinal value for masterExemption. */
  public static final int MASTER_EXEMPTION = 284;
  /** Ordinal value for occupancyExemption. */
  public static final int OCCUPANCY_EXEMPTION = 293;
  /** Ordinal value for passbackExemption. */
  public static final int PASSBACK_EXEMPTION = 299;

  /** BBacnetPropertyIdentifier constant for ackedTransitions. */
  public static final BBacnetPropertyIdentifier ackedTransitions = new BBacnetPropertyIdentifier(ACKED_TRANSITIONS);
  /** BBacnetPropertyIdentifier constant for ackRequired. */
  public static final BBacnetPropertyIdentifier ackRequired = new BBacnetPropertyIdentifier(ACK_REQUIRED);
  /** BBacnetPropertyIdentifier constant for action. */
  public static final BBacnetPropertyIdentifier action = new BBacnetPropertyIdentifier(ACTION);
  /** BBacnetPropertyIdentifier constant for actionText. */
  public static final BBacnetPropertyIdentifier actionText = new BBacnetPropertyIdentifier(ACTION_TEXT);
  /** BBacnetPropertyIdentifier constant for activeText. */
  public static final BBacnetPropertyIdentifier activeText = new BBacnetPropertyIdentifier(ACTIVE_TEXT);
  /** BBacnetPropertyIdentifier constant for activeVtSessions. */
  public static final BBacnetPropertyIdentifier activeVtSessions = new BBacnetPropertyIdentifier(ACTIVE_VT_SESSIONS);
  /** BBacnetPropertyIdentifier constant for alarmValue. */
  public static final BBacnetPropertyIdentifier alarmValue = new BBacnetPropertyIdentifier(ALARM_VALUE);
  /** BBacnetPropertyIdentifier constant for alarmValues. */
  public static final BBacnetPropertyIdentifier alarmValues = new BBacnetPropertyIdentifier(ALARM_VALUES);
  /** BBacnetPropertyIdentifier constant for all. */
  public static final BBacnetPropertyIdentifier all = new BBacnetPropertyIdentifier(ALL);
  /** BBacnetPropertyIdentifier constant for allWritesSuccessful. */
  public static final BBacnetPropertyIdentifier allWritesSuccessful = new BBacnetPropertyIdentifier(ALL_WRITES_SUCCESSFUL);
  /** BBacnetPropertyIdentifier constant for apduSegmentTimeout. */
  public static final BBacnetPropertyIdentifier apduSegmentTimeout = new BBacnetPropertyIdentifier(APDU_SEGMENT_TIMEOUT);
  /** BBacnetPropertyIdentifier constant for apduTimeout. */
  public static final BBacnetPropertyIdentifier apduTimeout = new BBacnetPropertyIdentifier(APDU_TIMEOUT);
  /** BBacnetPropertyIdentifier constant for applicationSoftwareVersion. */
  public static final BBacnetPropertyIdentifier applicationSoftwareVersion = new BBacnetPropertyIdentifier(APPLICATION_SOFTWARE_VERSION);
  /** BBacnetPropertyIdentifier constant for archive. */
  public static final BBacnetPropertyIdentifier archive = new BBacnetPropertyIdentifier(ARCHIVE);
  /** BBacnetPropertyIdentifier constant for bias. */
  public static final BBacnetPropertyIdentifier bias = new BBacnetPropertyIdentifier(BIAS);
  /** BBacnetPropertyIdentifier constant for changeOfStateCount. */
  public static final BBacnetPropertyIdentifier changeOfStateCount = new BBacnetPropertyIdentifier(CHANGE_OF_STATE_COUNT);
  /** BBacnetPropertyIdentifier constant for changeOfStateTime. */
  public static final BBacnetPropertyIdentifier changeOfStateTime = new BBacnetPropertyIdentifier(CHANGE_OF_STATE_TIME);
  /** BBacnetPropertyIdentifier constant for notificationClass. */
  public static final BBacnetPropertyIdentifier notificationClass = new BBacnetPropertyIdentifier(NOTIFICATION_CLASS);
  /** BBacnetPropertyIdentifier constant for controlledVariableReference. */
  public static final BBacnetPropertyIdentifier controlledVariableReference = new BBacnetPropertyIdentifier(CONTROLLED_VARIABLE_REFERENCE);
  /** BBacnetPropertyIdentifier constant for controlledVariableUnits. */
  public static final BBacnetPropertyIdentifier controlledVariableUnits = new BBacnetPropertyIdentifier(CONTROLLED_VARIABLE_UNITS);
  /** BBacnetPropertyIdentifier constant for controlledVariableValue. */
  public static final BBacnetPropertyIdentifier controlledVariableValue = new BBacnetPropertyIdentifier(CONTROLLED_VARIABLE_VALUE);
  /** BBacnetPropertyIdentifier constant for covIncrement. */
  public static final BBacnetPropertyIdentifier covIncrement = new BBacnetPropertyIdentifier(COV_INCREMENT);
  /** BBacnetPropertyIdentifier constant for dateList. */
  public static final BBacnetPropertyIdentifier dateList = new BBacnetPropertyIdentifier(DATE_LIST);
  /** BBacnetPropertyIdentifier constant for daylightSavingsStatus. */
  public static final BBacnetPropertyIdentifier daylightSavingsStatus = new BBacnetPropertyIdentifier(DAYLIGHT_SAVINGS_STATUS);
  /** BBacnetPropertyIdentifier constant for deadband. */
  public static final BBacnetPropertyIdentifier deadband = new BBacnetPropertyIdentifier(DEADBAND);
  /** BBacnetPropertyIdentifier constant for derivativeConstant. */
  public static final BBacnetPropertyIdentifier derivativeConstant = new BBacnetPropertyIdentifier(DERIVATIVE_CONSTANT);
  /** BBacnetPropertyIdentifier constant for derivativeConstantUnits. */
  public static final BBacnetPropertyIdentifier derivativeConstantUnits = new BBacnetPropertyIdentifier(DERIVATIVE_CONSTANT_UNITS);
  /** BBacnetPropertyIdentifier constant for description. */
  public static final BBacnetPropertyIdentifier description = new BBacnetPropertyIdentifier(DESCRIPTION);
  /** BBacnetPropertyIdentifier constant for descriptionOfHalt. */
  public static final BBacnetPropertyIdentifier descriptionOfHalt = new BBacnetPropertyIdentifier(DESCRIPTION_OF_HALT);
  /** BBacnetPropertyIdentifier constant for deviceAddressBinding. */
  public static final BBacnetPropertyIdentifier deviceAddressBinding = new BBacnetPropertyIdentifier(DEVICE_ADDRESS_BINDING);
  /** BBacnetPropertyIdentifier constant for deviceType. */
  public static final BBacnetPropertyIdentifier deviceType = new BBacnetPropertyIdentifier(DEVICE_TYPE);
  /** BBacnetPropertyIdentifier constant for effectivePeriod. */
  public static final BBacnetPropertyIdentifier effectivePeriod = new BBacnetPropertyIdentifier(EFFECTIVE_PERIOD);
  /** BBacnetPropertyIdentifier constant for elapsedActiveTime. */
  public static final BBacnetPropertyIdentifier elapsedActiveTime = new BBacnetPropertyIdentifier(ELAPSED_ACTIVE_TIME);
  /** BBacnetPropertyIdentifier constant for errorLimit. */
  public static final BBacnetPropertyIdentifier errorLimit = new BBacnetPropertyIdentifier(ERROR_LIMIT);
  /** BBacnetPropertyIdentifier constant for eventEnable. */
  public static final BBacnetPropertyIdentifier eventEnable = new BBacnetPropertyIdentifier(EVENT_ENABLE);
  /** BBacnetPropertyIdentifier constant for eventState. */
  public static final BBacnetPropertyIdentifier eventState = new BBacnetPropertyIdentifier(EVENT_STATE);
  /** BBacnetPropertyIdentifier constant for eventType. */
  public static final BBacnetPropertyIdentifier eventType = new BBacnetPropertyIdentifier(EVENT_TYPE);
  /** BBacnetPropertyIdentifier constant for exceptionSchedule. */
  public static final BBacnetPropertyIdentifier exceptionSchedule = new BBacnetPropertyIdentifier(EXCEPTION_SCHEDULE);
  /** BBacnetPropertyIdentifier constant for faultValues. */
  public static final BBacnetPropertyIdentifier faultValues = new BBacnetPropertyIdentifier(FAULT_VALUES);
  /** BBacnetPropertyIdentifier constant for feedbackValue. */
  public static final BBacnetPropertyIdentifier feedbackValue = new BBacnetPropertyIdentifier(FEEDBACK_VALUE);
  /** BBacnetPropertyIdentifier constant for fileAccessMethod. */
  public static final BBacnetPropertyIdentifier fileAccessMethod = new BBacnetPropertyIdentifier(FILE_ACCESS_METHOD);
  /** BBacnetPropertyIdentifier constant for fileSize. */
  public static final BBacnetPropertyIdentifier fileSize = new BBacnetPropertyIdentifier(FILE_SIZE);
  /** BBacnetPropertyIdentifier constant for fileType. */
  public static final BBacnetPropertyIdentifier fileType = new BBacnetPropertyIdentifier(FILE_TYPE);
  /** BBacnetPropertyIdentifier constant for firmwareRevision. */
  public static final BBacnetPropertyIdentifier firmwareRevision = new BBacnetPropertyIdentifier(FIRMWARE_REVISION);
  /** BBacnetPropertyIdentifier constant for highLimit. */
  public static final BBacnetPropertyIdentifier highLimit = new BBacnetPropertyIdentifier(HIGH_LIMIT);
  /** BBacnetPropertyIdentifier constant for inactiveText. */
  public static final BBacnetPropertyIdentifier inactiveText = new BBacnetPropertyIdentifier(INACTIVE_TEXT);
  /** BBacnetPropertyIdentifier constant for inProcess. */
  public static final BBacnetPropertyIdentifier inProcess = new BBacnetPropertyIdentifier(IN_PROCESS);
  /** BBacnetPropertyIdentifier constant for instanceOf. */
  public static final BBacnetPropertyIdentifier instanceOf = new BBacnetPropertyIdentifier(INSTANCE_OF);
  /** BBacnetPropertyIdentifier constant for integralConstant. */
  public static final BBacnetPropertyIdentifier integralConstant = new BBacnetPropertyIdentifier(INTEGRAL_CONSTANT);
  /** BBacnetPropertyIdentifier constant for integralConstantUnits. */
  public static final BBacnetPropertyIdentifier integralConstantUnits = new BBacnetPropertyIdentifier(INTEGRAL_CONSTANT_UNITS);
  /** BBacnetPropertyIdentifier constant for limitEnable. */
  public static final BBacnetPropertyIdentifier limitEnable = new BBacnetPropertyIdentifier(LIMIT_ENABLE);
  /** BBacnetPropertyIdentifier constant for listOfGroupMembers. */
  public static final BBacnetPropertyIdentifier listOfGroupMembers = new BBacnetPropertyIdentifier(LIST_OF_GROUP_MEMBERS);
  /** BBacnetPropertyIdentifier constant for listOfObjectPropertyReferences. */
  public static final BBacnetPropertyIdentifier listOfObjectPropertyReferences = new BBacnetPropertyIdentifier(LIST_OF_OBJECT_PROPERTY_REFERENCES);
  /** BBacnetPropertyIdentifier constant for localDate. */
  public static final BBacnetPropertyIdentifier localDate = new BBacnetPropertyIdentifier(LOCAL_DATE);
  /** BBacnetPropertyIdentifier constant for localTime. */
  public static final BBacnetPropertyIdentifier localTime = new BBacnetPropertyIdentifier(LOCAL_TIME);
  /** BBacnetPropertyIdentifier constant for location. */
  public static final BBacnetPropertyIdentifier location = new BBacnetPropertyIdentifier(LOCATION);
  /** BBacnetPropertyIdentifier constant for lowLimit. */
  public static final BBacnetPropertyIdentifier lowLimit = new BBacnetPropertyIdentifier(LOW_LIMIT);
  /** BBacnetPropertyIdentifier constant for manipulatedVariableReference. */
  public static final BBacnetPropertyIdentifier manipulatedVariableReference = new BBacnetPropertyIdentifier(MANIPULATED_VARIABLE_REFERENCE);
  /** BBacnetPropertyIdentifier constant for maximumOutput. */
  public static final BBacnetPropertyIdentifier maximumOutput = new BBacnetPropertyIdentifier(MAXIMUM_OUTPUT);
  /** BBacnetPropertyIdentifier constant for maxApduLengthAccepted. */
  public static final BBacnetPropertyIdentifier maxApduLengthAccepted = new BBacnetPropertyIdentifier(MAX_APDU_LENGTH_ACCEPTED);
  /** BBacnetPropertyIdentifier constant for maxInfoFrames. */
  public static final BBacnetPropertyIdentifier maxInfoFrames = new BBacnetPropertyIdentifier(MAX_INFO_FRAMES);
  /** BBacnetPropertyIdentifier constant for maxMaster. */
  public static final BBacnetPropertyIdentifier maxMaster = new BBacnetPropertyIdentifier(MAX_MASTER);
  /** BBacnetPropertyIdentifier constant for maxPresValue. */
  public static final BBacnetPropertyIdentifier maxPresValue = new BBacnetPropertyIdentifier(MAX_PRES_VALUE);
  /** BBacnetPropertyIdentifier constant for minimumOffTime. */
  public static final BBacnetPropertyIdentifier minimumOffTime = new BBacnetPropertyIdentifier(MINIMUM_OFF_TIME);
  /** BBacnetPropertyIdentifier constant for minimumOnTime. */
  public static final BBacnetPropertyIdentifier minimumOnTime = new BBacnetPropertyIdentifier(MINIMUM_ON_TIME);
  /** BBacnetPropertyIdentifier constant for minimumOutput. */
  public static final BBacnetPropertyIdentifier minimumOutput = new BBacnetPropertyIdentifier(MINIMUM_OUTPUT);
  /** BBacnetPropertyIdentifier constant for minPresValue. */
  public static final BBacnetPropertyIdentifier minPresValue = new BBacnetPropertyIdentifier(MIN_PRES_VALUE);
  /** BBacnetPropertyIdentifier constant for modelName. */
  public static final BBacnetPropertyIdentifier modelName = new BBacnetPropertyIdentifier(MODEL_NAME);
  /** BBacnetPropertyIdentifier constant for modificationDate. */
  public static final BBacnetPropertyIdentifier modificationDate = new BBacnetPropertyIdentifier(MODIFICATION_DATE);
  /** BBacnetPropertyIdentifier constant for notifyType. */
  public static final BBacnetPropertyIdentifier notifyType = new BBacnetPropertyIdentifier(NOTIFY_TYPE);
  /** BBacnetPropertyIdentifier constant for numberOfApduRetries. */
  public static final BBacnetPropertyIdentifier numberOfApduRetries = new BBacnetPropertyIdentifier(NUMBER_OF_APDU_RETRIES);
  /** BBacnetPropertyIdentifier constant for numberOfStates. */
  public static final BBacnetPropertyIdentifier numberOfStates = new BBacnetPropertyIdentifier(NUMBER_OF_STATES);
  /** BBacnetPropertyIdentifier constant for objectIdentifier. */
  public static final BBacnetPropertyIdentifier objectIdentifier = new BBacnetPropertyIdentifier(OBJECT_IDENTIFIER);
  /** BBacnetPropertyIdentifier constant for objectList. */
  public static final BBacnetPropertyIdentifier objectList = new BBacnetPropertyIdentifier(OBJECT_LIST);
  /** BBacnetPropertyIdentifier constant for objectName. */
  public static final BBacnetPropertyIdentifier objectName = new BBacnetPropertyIdentifier(OBJECT_NAME);
  /** BBacnetPropertyIdentifier constant for objectPropertyReference. */
  public static final BBacnetPropertyIdentifier objectPropertyReference = new BBacnetPropertyIdentifier(OBJECT_PROPERTY_REFERENCE);
  /** BBacnetPropertyIdentifier constant for objectType. */
  public static final BBacnetPropertyIdentifier objectType = new BBacnetPropertyIdentifier(OBJECT_TYPE);
  /** BBacnetPropertyIdentifier constant for optional. */
  public static final BBacnetPropertyIdentifier optional = new BBacnetPropertyIdentifier(OPTIONAL);
  /** BBacnetPropertyIdentifier constant for outOfService. */
  public static final BBacnetPropertyIdentifier outOfService = new BBacnetPropertyIdentifier(OUT_OF_SERVICE);
  /** BBacnetPropertyIdentifier constant for outputUnits. */
  public static final BBacnetPropertyIdentifier outputUnits = new BBacnetPropertyIdentifier(OUTPUT_UNITS);
  /** BBacnetPropertyIdentifier constant for eventParameters. */
  public static final BBacnetPropertyIdentifier eventParameters = new BBacnetPropertyIdentifier(EVENT_PARAMETERS);
  /** BBacnetPropertyIdentifier constant for polarity. */
  public static final BBacnetPropertyIdentifier polarity = new BBacnetPropertyIdentifier(POLARITY);
  /** BBacnetPropertyIdentifier constant for presentValue. */
  public static final BBacnetPropertyIdentifier presentValue = new BBacnetPropertyIdentifier(PRESENT_VALUE);
  /** BBacnetPropertyIdentifier constant for priority. */
  public static final BBacnetPropertyIdentifier priority = new BBacnetPropertyIdentifier(PRIORITY);
  /** BBacnetPropertyIdentifier constant for priorityArray. */
  public static final BBacnetPropertyIdentifier priorityArray = new BBacnetPropertyIdentifier(PRIORITY_ARRAY);
  /** BBacnetPropertyIdentifier constant for priorityForWriting. */
  public static final BBacnetPropertyIdentifier priorityForWriting = new BBacnetPropertyIdentifier(PRIORITY_FOR_WRITING);
  /** BBacnetPropertyIdentifier constant for processIdentifier. */
  public static final BBacnetPropertyIdentifier processIdentifier = new BBacnetPropertyIdentifier(PROCESS_IDENTIFIER);
  /** BBacnetPropertyIdentifier constant for programChange. */
  public static final BBacnetPropertyIdentifier programChange = new BBacnetPropertyIdentifier(PROGRAM_CHANGE);
  /** BBacnetPropertyIdentifier constant for programLocation. */
  public static final BBacnetPropertyIdentifier programLocation = new BBacnetPropertyIdentifier(PROGRAM_LOCATION);
  /** BBacnetPropertyIdentifier constant for programState. */
  public static final BBacnetPropertyIdentifier programState = new BBacnetPropertyIdentifier(PROGRAM_STATE);
  /** BBacnetPropertyIdentifier constant for proportionalConstant. */
  public static final BBacnetPropertyIdentifier proportionalConstant = new BBacnetPropertyIdentifier(PROPORTIONAL_CONSTANT);
  /** BBacnetPropertyIdentifier constant for proportionalConstantUnits. */
  public static final BBacnetPropertyIdentifier proportionalConstantUnits = new BBacnetPropertyIdentifier(PROPORTIONAL_CONSTANT_UNITS);
  /** BBacnetPropertyIdentifier constant for protocolObjectTypesSupported. */
  public static final BBacnetPropertyIdentifier protocolObjectTypesSupported = new BBacnetPropertyIdentifier(PROTOCOL_OBJECT_TYPES_SUPPORTED);
  /** BBacnetPropertyIdentifier constant for protocolServicesSupported. */
  public static final BBacnetPropertyIdentifier protocolServicesSupported = new BBacnetPropertyIdentifier(PROTOCOL_SERVICES_SUPPORTED);
  /** BBacnetPropertyIdentifier constant for protocolVersion. */
  public static final BBacnetPropertyIdentifier protocolVersion = new BBacnetPropertyIdentifier(PROTOCOL_VERSION);
  /** BBacnetPropertyIdentifier constant for readOnly. */
  public static final BBacnetPropertyIdentifier readOnly = new BBacnetPropertyIdentifier(READ_ONLY);
  /** BBacnetPropertyIdentifier constant for reasonForHalt. */
  public static final BBacnetPropertyIdentifier reasonForHalt = new BBacnetPropertyIdentifier(REASON_FOR_HALT);
  /** BBacnetPropertyIdentifier constant for recipientList. */
  public static final BBacnetPropertyIdentifier recipientList = new BBacnetPropertyIdentifier(RECIPIENT_LIST);
  /** BBacnetPropertyIdentifier constant for reliability. */
  public static final BBacnetPropertyIdentifier reliability = new BBacnetPropertyIdentifier(RELIABILITY);
  /** BBacnetPropertyIdentifier constant for relinquishDefault. */
  public static final BBacnetPropertyIdentifier relinquishDefault = new BBacnetPropertyIdentifier(RELINQUISH_DEFAULT);
  /** BBacnetPropertyIdentifier constant for required. */
  public static final BBacnetPropertyIdentifier required = new BBacnetPropertyIdentifier(REQUIRED);
  /** BBacnetPropertyIdentifier constant for resolution. */
  public static final BBacnetPropertyIdentifier resolution = new BBacnetPropertyIdentifier(RESOLUTION);
  /** BBacnetPropertyIdentifier constant for segmentationSupported. */
  public static final BBacnetPropertyIdentifier segmentationSupported = new BBacnetPropertyIdentifier(SEGMENTATION_SUPPORTED);
  /** BBacnetPropertyIdentifier constant for setpoint. */
  public static final BBacnetPropertyIdentifier setpoint = new BBacnetPropertyIdentifier(SETPOINT);
  /** BBacnetPropertyIdentifier constant for setpointReference. */
  public static final BBacnetPropertyIdentifier setpointReference = new BBacnetPropertyIdentifier(SETPOINT_REFERENCE);
  /** BBacnetPropertyIdentifier constant for stateText. */
  public static final BBacnetPropertyIdentifier stateText = new BBacnetPropertyIdentifier(STATE_TEXT);
  /** BBacnetPropertyIdentifier constant for statusFlags. */
  public static final BBacnetPropertyIdentifier statusFlags = new BBacnetPropertyIdentifier(STATUS_FLAGS);
  /** BBacnetPropertyIdentifier constant for systemStatus. */
  public static final BBacnetPropertyIdentifier systemStatus = new BBacnetPropertyIdentifier(SYSTEM_STATUS);
  /** BBacnetPropertyIdentifier constant for timeDelay. */
  public static final BBacnetPropertyIdentifier timeDelay = new BBacnetPropertyIdentifier(TIME_DELAY);
  /** BBacnetPropertyIdentifier constant for timeOfActiveTimeReset. */
  public static final BBacnetPropertyIdentifier timeOfActiveTimeReset = new BBacnetPropertyIdentifier(TIME_OF_ACTIVE_TIME_RESET);
  /** BBacnetPropertyIdentifier constant for timeOfStateCountReset. */
  public static final BBacnetPropertyIdentifier timeOfStateCountReset = new BBacnetPropertyIdentifier(TIME_OF_STATE_COUNT_RESET);
  /** BBacnetPropertyIdentifier constant for timeSynchronizationRecipients. */
  public static final BBacnetPropertyIdentifier timeSynchronizationRecipients = new BBacnetPropertyIdentifier(TIME_SYNCHRONIZATION_RECIPIENTS);
  /** BBacnetPropertyIdentifier constant for units. */
  public static final BBacnetPropertyIdentifier units = new BBacnetPropertyIdentifier(UNITS);
  /** BBacnetPropertyIdentifier constant for updateInterval. */
  public static final BBacnetPropertyIdentifier updateInterval = new BBacnetPropertyIdentifier(UPDATE_INTERVAL);
  /** BBacnetPropertyIdentifier constant for utcOffset. */
  public static final BBacnetPropertyIdentifier utcOffset = new BBacnetPropertyIdentifier(UTC_OFFSET);
  /** BBacnetPropertyIdentifier constant for vendorIdentifier. */
  public static final BBacnetPropertyIdentifier vendorIdentifier = new BBacnetPropertyIdentifier(VENDOR_IDENTIFIER);
  /** BBacnetPropertyIdentifier constant for vendorName. */
  public static final BBacnetPropertyIdentifier vendorName = new BBacnetPropertyIdentifier(VENDOR_NAME);
  /** BBacnetPropertyIdentifier constant for vtClassesSupported. */
  public static final BBacnetPropertyIdentifier vtClassesSupported = new BBacnetPropertyIdentifier(VT_CLASSES_SUPPORTED);
  /** BBacnetPropertyIdentifier constant for weeklySchedule. */
  public static final BBacnetPropertyIdentifier weeklySchedule = new BBacnetPropertyIdentifier(WEEKLY_SCHEDULE);
  /** BBacnetPropertyIdentifier constant for attemptedSamples. */
  public static final BBacnetPropertyIdentifier attemptedSamples = new BBacnetPropertyIdentifier(ATTEMPTED_SAMPLES);
  /** BBacnetPropertyIdentifier constant for averageValue. */
  public static final BBacnetPropertyIdentifier averageValue = new BBacnetPropertyIdentifier(AVERAGE_VALUE);
  /** BBacnetPropertyIdentifier constant for bufferSize. */
  public static final BBacnetPropertyIdentifier bufferSize = new BBacnetPropertyIdentifier(BUFFER_SIZE);
  /** BBacnetPropertyIdentifier constant for clientCovIncrement. */
  public static final BBacnetPropertyIdentifier clientCovIncrement = new BBacnetPropertyIdentifier(CLIENT_COV_INCREMENT);
  /** BBacnetPropertyIdentifier constant for covResubscriptionInterval. */
  public static final BBacnetPropertyIdentifier covResubscriptionInterval = new BBacnetPropertyIdentifier(COV_RESUBSCRIPTION_INTERVAL);
  /** BBacnetPropertyIdentifier constant for eventTimeStamps. */
  public static final BBacnetPropertyIdentifier eventTimeStamps = new BBacnetPropertyIdentifier(EVENT_TIME_STAMPS);
  /** BBacnetPropertyIdentifier constant for logBuffer. */
  public static final BBacnetPropertyIdentifier logBuffer = new BBacnetPropertyIdentifier(LOG_BUFFER);
  /** BBacnetPropertyIdentifier constant for logDeviceObjectProperty. */
  public static final BBacnetPropertyIdentifier logDeviceObjectProperty = new BBacnetPropertyIdentifier(LOG_DEVICE_OBJECT_PROPERTY);
  /** BBacnetPropertyIdentifier constant for enable. */
  public static final BBacnetPropertyIdentifier enable = new BBacnetPropertyIdentifier(ENABLE);
  /** BBacnetPropertyIdentifier constant for logInterval. */
  public static final BBacnetPropertyIdentifier logInterval = new BBacnetPropertyIdentifier(LOG_INTERVAL);
  /** BBacnetPropertyIdentifier constant for maximumValue. */
  public static final BBacnetPropertyIdentifier maximumValue = new BBacnetPropertyIdentifier(MAXIMUM_VALUE);
  /** BBacnetPropertyIdentifier constant for minimumValue. */
  public static final BBacnetPropertyIdentifier minimumValue = new BBacnetPropertyIdentifier(MINIMUM_VALUE);
  /** BBacnetPropertyIdentifier constant for notificationThreshold. */
  public static final BBacnetPropertyIdentifier notificationThreshold = new BBacnetPropertyIdentifier(NOTIFICATION_THRESHOLD);
  /** BBacnetPropertyIdentifier constant for protocolRevision. */
  public static final BBacnetPropertyIdentifier protocolRevision = new BBacnetPropertyIdentifier(PROTOCOL_REVISION);
  /** BBacnetPropertyIdentifier constant for recordsSinceNotification. */
  public static final BBacnetPropertyIdentifier recordsSinceNotification = new BBacnetPropertyIdentifier(RECORDS_SINCE_NOTIFICATION);
  /** BBacnetPropertyIdentifier constant for recordCount. */
  public static final BBacnetPropertyIdentifier recordCount = new BBacnetPropertyIdentifier(RECORD_COUNT);
  /** BBacnetPropertyIdentifier constant for startTime. */
  public static final BBacnetPropertyIdentifier startTime = new BBacnetPropertyIdentifier(START_TIME);
  /** BBacnetPropertyIdentifier constant for stopTime. */
  public static final BBacnetPropertyIdentifier stopTime = new BBacnetPropertyIdentifier(STOP_TIME);
  /** BBacnetPropertyIdentifier constant for stopWhenFull. */
  public static final BBacnetPropertyIdentifier stopWhenFull = new BBacnetPropertyIdentifier(STOP_WHEN_FULL);
  /** BBacnetPropertyIdentifier constant for totalRecordCount. */
  public static final BBacnetPropertyIdentifier totalRecordCount = new BBacnetPropertyIdentifier(TOTAL_RECORD_COUNT);
  /** BBacnetPropertyIdentifier constant for validSamples. */
  public static final BBacnetPropertyIdentifier validSamples = new BBacnetPropertyIdentifier(VALID_SAMPLES);
  /** BBacnetPropertyIdentifier constant for windowInterval. */
  public static final BBacnetPropertyIdentifier windowInterval = new BBacnetPropertyIdentifier(WINDOW_INTERVAL);
  /** BBacnetPropertyIdentifier constant for windowSamples. */
  public static final BBacnetPropertyIdentifier windowSamples = new BBacnetPropertyIdentifier(WINDOW_SAMPLES);
  /** BBacnetPropertyIdentifier constant for maximumValueTimestamp. */
  public static final BBacnetPropertyIdentifier maximumValueTimestamp = new BBacnetPropertyIdentifier(MAXIMUM_VALUE_TIMESTAMP);
  /** BBacnetPropertyIdentifier constant for minimumValueTimestamp. */
  public static final BBacnetPropertyIdentifier minimumValueTimestamp = new BBacnetPropertyIdentifier(MINIMUM_VALUE_TIMESTAMP);
  /** BBacnetPropertyIdentifier constant for varianceValue. */
  public static final BBacnetPropertyIdentifier varianceValue = new BBacnetPropertyIdentifier(VARIANCE_VALUE);
  /** BBacnetPropertyIdentifier constant for activeCovSubscriptions. */
  public static final BBacnetPropertyIdentifier activeCovSubscriptions = new BBacnetPropertyIdentifier(ACTIVE_COV_SUBSCRIPTIONS);
  /** BBacnetPropertyIdentifier constant for backupFailureTimeout. */
  public static final BBacnetPropertyIdentifier backupFailureTimeout = new BBacnetPropertyIdentifier(BACKUP_FAILURE_TIMEOUT);
  /** BBacnetPropertyIdentifier constant for configurationFiles. */
  public static final BBacnetPropertyIdentifier configurationFiles = new BBacnetPropertyIdentifier(CONFIGURATION_FILES);
  /** BBacnetPropertyIdentifier constant for databaseRevision. */
  public static final BBacnetPropertyIdentifier databaseRevision = new BBacnetPropertyIdentifier(DATABASE_REVISION);
  /** BBacnetPropertyIdentifier constant for directReading. */
  public static final BBacnetPropertyIdentifier directReading = new BBacnetPropertyIdentifier(DIRECT_READING);
  /** BBacnetPropertyIdentifier constant for lastRestoreTime. */
  public static final BBacnetPropertyIdentifier lastRestoreTime = new BBacnetPropertyIdentifier(LAST_RESTORE_TIME);
  /** BBacnetPropertyIdentifier constant for maintenanceRequired. */
  public static final BBacnetPropertyIdentifier maintenanceRequired = new BBacnetPropertyIdentifier(MAINTENANCE_REQUIRED);
  /** BBacnetPropertyIdentifier constant for memberOf. */
  public static final BBacnetPropertyIdentifier memberOf = new BBacnetPropertyIdentifier(MEMBER_OF);
  /** BBacnetPropertyIdentifier constant for mode. */
  public static final BBacnetPropertyIdentifier mode = new BBacnetPropertyIdentifier(MODE);
  /** BBacnetPropertyIdentifier constant for operationExpected. */
  public static final BBacnetPropertyIdentifier operationExpected = new BBacnetPropertyIdentifier(OPERATION_EXPECTED);
  /** BBacnetPropertyIdentifier constant for setting. */
  public static final BBacnetPropertyIdentifier setting = new BBacnetPropertyIdentifier(SETTING);
  /** BBacnetPropertyIdentifier constant for silenced. */
  public static final BBacnetPropertyIdentifier silenced = new BBacnetPropertyIdentifier(SILENCED);
  /** BBacnetPropertyIdentifier constant for trackingValue. */
  public static final BBacnetPropertyIdentifier trackingValue = new BBacnetPropertyIdentifier(TRACKING_VALUE);
  /** BBacnetPropertyIdentifier constant for zoneMembers. */
  public static final BBacnetPropertyIdentifier zoneMembers = new BBacnetPropertyIdentifier(ZONE_MEMBERS);
  /** BBacnetPropertyIdentifier constant for lifeSafetyAlarmValues. */
  public static final BBacnetPropertyIdentifier lifeSafetyAlarmValues = new BBacnetPropertyIdentifier(LIFE_SAFETY_ALARM_VALUES);
  /** BBacnetPropertyIdentifier constant for maxSegmentsAccepted. */
  public static final BBacnetPropertyIdentifier maxSegmentsAccepted = new BBacnetPropertyIdentifier(MAX_SEGMENTS_ACCEPTED);
  /** BBacnetPropertyIdentifier constant for profileName. */
  public static final BBacnetPropertyIdentifier profileName = new BBacnetPropertyIdentifier(PROFILE_NAME);
  /** BBacnetPropertyIdentifier constant for autoSlaveDiscovery. */
  public static final BBacnetPropertyIdentifier autoSlaveDiscovery = new BBacnetPropertyIdentifier(AUTO_SLAVE_DISCOVERY);
  /** BBacnetPropertyIdentifier constant for manualSlaveAddressBinding. */
  public static final BBacnetPropertyIdentifier manualSlaveAddressBinding = new BBacnetPropertyIdentifier(MANUAL_SLAVE_ADDRESS_BINDING);
  /** BBacnetPropertyIdentifier constant for slaveAddressBinding. */
  public static final BBacnetPropertyIdentifier slaveAddressBinding = new BBacnetPropertyIdentifier(SLAVE_ADDRESS_BINDING);
  /** BBacnetPropertyIdentifier constant for slaveProxyEnable. */
  public static final BBacnetPropertyIdentifier slaveProxyEnable = new BBacnetPropertyIdentifier(SLAVE_PROXY_ENABLE);
  /** BBacnetPropertyIdentifier constant for lastNotifyRecord. */
  public static final BBacnetPropertyIdentifier lastNotifyRecord = new BBacnetPropertyIdentifier(LAST_NOTIFY_RECORD);
  /** BBacnetPropertyIdentifier constant for scheduleDefault. */
  public static final BBacnetPropertyIdentifier scheduleDefault = new BBacnetPropertyIdentifier(SCHEDULE_DEFAULT);
  /** BBacnetPropertyIdentifier constant for acceptedModes. */
  public static final BBacnetPropertyIdentifier acceptedModes = new BBacnetPropertyIdentifier(ACCEPTED_MODES);
  /** BBacnetPropertyIdentifier constant for adjustValue. */
  public static final BBacnetPropertyIdentifier adjustValue = new BBacnetPropertyIdentifier(ADJUST_VALUE);
  /** BBacnetPropertyIdentifier constant for count. */
  public static final BBacnetPropertyIdentifier count = new BBacnetPropertyIdentifier(COUNT);
  /** BBacnetPropertyIdentifier constant for countBeforeChange. */
  public static final BBacnetPropertyIdentifier countBeforeChange = new BBacnetPropertyIdentifier(COUNT_BEFORE_CHANGE);
  /** BBacnetPropertyIdentifier constant for countChangeTime. */
  public static final BBacnetPropertyIdentifier countChangeTime = new BBacnetPropertyIdentifier(COUNT_CHANGE_TIME);
  /** BBacnetPropertyIdentifier constant for covPeriod. */
  public static final BBacnetPropertyIdentifier covPeriod = new BBacnetPropertyIdentifier(COV_PERIOD);
  /** BBacnetPropertyIdentifier constant for inputReference. */
  public static final BBacnetPropertyIdentifier inputReference = new BBacnetPropertyIdentifier(INPUT_REFERENCE);
  /** BBacnetPropertyIdentifier constant for limitMonitoringInterval. */
  public static final BBacnetPropertyIdentifier limitMonitoringInterval = new BBacnetPropertyIdentifier(LIMIT_MONITORING_INTERVAL);
  /** BBacnetPropertyIdentifier constant for loggingObject. */
  public static final BBacnetPropertyIdentifier loggingObject = new BBacnetPropertyIdentifier(LOGGING_OBJECT);
  /** BBacnetPropertyIdentifier constant for loggingRecord. */
  public static final BBacnetPropertyIdentifier loggingRecord = new BBacnetPropertyIdentifier(LOGGING_RECORD);
  /** BBacnetPropertyIdentifier constant for prescale. */
  public static final BBacnetPropertyIdentifier prescale = new BBacnetPropertyIdentifier(PRESCALE);
  /** BBacnetPropertyIdentifier constant for pulseRate. */
  public static final BBacnetPropertyIdentifier pulseRate = new BBacnetPropertyIdentifier(PULSE_RATE);
  /** BBacnetPropertyIdentifier constant for scale. */
  public static final BBacnetPropertyIdentifier scale = new BBacnetPropertyIdentifier(SCALE);
  /** BBacnetPropertyIdentifier constant for scaleFactor. */
  public static final BBacnetPropertyIdentifier scaleFactor = new BBacnetPropertyIdentifier(SCALE_FACTOR);
  /** BBacnetPropertyIdentifier constant for updateTime. */
  public static final BBacnetPropertyIdentifier updateTime = new BBacnetPropertyIdentifier(UPDATE_TIME);
  /** BBacnetPropertyIdentifier constant for valueBeforeChange. */
  public static final BBacnetPropertyIdentifier valueBeforeChange = new BBacnetPropertyIdentifier(VALUE_BEFORE_CHANGE);
  /** BBacnetPropertyIdentifier constant for valueSet. */
  public static final BBacnetPropertyIdentifier valueSet = new BBacnetPropertyIdentifier(VALUE_SET);
  /** BBacnetPropertyIdentifier constant for valueChangeTime. */
  public static final BBacnetPropertyIdentifier valueChangeTime = new BBacnetPropertyIdentifier(VALUE_CHANGE_TIME);
  /** BBacnetPropertyIdentifier constant for alignIntervals. */
  public static final BBacnetPropertyIdentifier alignIntervals = new BBacnetPropertyIdentifier(ALIGN_INTERVALS);
  /** BBacnetPropertyIdentifier constant for intervalOffset. */
  public static final BBacnetPropertyIdentifier intervalOffset = new BBacnetPropertyIdentifier(INTERVAL_OFFSET);
  /** BBacnetPropertyIdentifier constant for lastRestartReason. */
  public static final BBacnetPropertyIdentifier lastRestartReason = new BBacnetPropertyIdentifier(LAST_RESTART_REASON);
  /** BBacnetPropertyIdentifier constant for loggingType. */
  public static final BBacnetPropertyIdentifier loggingType = new BBacnetPropertyIdentifier(LOGGING_TYPE);
  /** BBacnetPropertyIdentifier constant for restartNotificationRecipients. */
  public static final BBacnetPropertyIdentifier restartNotificationRecipients = new BBacnetPropertyIdentifier(RESTART_NOTIFICATION_RECIPIENTS);
  /** BBacnetPropertyIdentifier constant for timeOfDeviceRestart. */
  public static final BBacnetPropertyIdentifier timeOfDeviceRestart = new BBacnetPropertyIdentifier(TIME_OF_DEVICE_RESTART);
  /** BBacnetPropertyIdentifier constant for timeSynchronizationInterval. */
  public static final BBacnetPropertyIdentifier timeSynchronizationInterval = new BBacnetPropertyIdentifier(TIME_SYNCHRONIZATION_INTERVAL);
  /** BBacnetPropertyIdentifier constant for trigger. */
  public static final BBacnetPropertyIdentifier trigger = new BBacnetPropertyIdentifier(TRIGGER);
  /** BBacnetPropertyIdentifier constant for utcTimeSynchronizationRecipients. */
  public static final BBacnetPropertyIdentifier utcTimeSynchronizationRecipients = new BBacnetPropertyIdentifier(UTC_TIME_SYNCHRONIZATION_RECIPIENTS);
  /** BBacnetPropertyIdentifier constant for nodeSubtype. */
  public static final BBacnetPropertyIdentifier nodeSubtype = new BBacnetPropertyIdentifier(NODE_SUBTYPE);
  /** BBacnetPropertyIdentifier constant for nodeType. */
  public static final BBacnetPropertyIdentifier nodeType = new BBacnetPropertyIdentifier(NODE_TYPE);
  /** BBacnetPropertyIdentifier constant for structuredObjectList. */
  public static final BBacnetPropertyIdentifier structuredObjectList = new BBacnetPropertyIdentifier(STRUCTURED_OBJECT_LIST);
  /** BBacnetPropertyIdentifier constant for subordinateAnnotations. */
  public static final BBacnetPropertyIdentifier subordinateAnnotations = new BBacnetPropertyIdentifier(SUBORDINATE_ANNOTATIONS);
  /** BBacnetPropertyIdentifier constant for subordinateList. */
  public static final BBacnetPropertyIdentifier subordinateList = new BBacnetPropertyIdentifier(SUBORDINATE_LIST);
  /** BBacnetPropertyIdentifier constant for actualShedLevel. */
  public static final BBacnetPropertyIdentifier actualShedLevel = new BBacnetPropertyIdentifier(ACTUAL_SHED_LEVEL);
  /** BBacnetPropertyIdentifier constant for dutyWindow. */
  public static final BBacnetPropertyIdentifier dutyWindow = new BBacnetPropertyIdentifier(DUTY_WINDOW);
  /** BBacnetPropertyIdentifier constant for expectedShedLevel. */
  public static final BBacnetPropertyIdentifier expectedShedLevel = new BBacnetPropertyIdentifier(EXPECTED_SHED_LEVEL);
  /** BBacnetPropertyIdentifier constant for fullDutyBaseline. */
  public static final BBacnetPropertyIdentifier fullDutyBaseline = new BBacnetPropertyIdentifier(FULL_DUTY_BASELINE);
  /** BBacnetPropertyIdentifier constant for requestedShedLevel. */
  public static final BBacnetPropertyIdentifier requestedShedLevel = new BBacnetPropertyIdentifier(REQUESTED_SHED_LEVEL);
  /** BBacnetPropertyIdentifier constant for shedDuration. */
  public static final BBacnetPropertyIdentifier shedDuration = new BBacnetPropertyIdentifier(SHED_DURATION);
  /** BBacnetPropertyIdentifier constant for shedLevelDescriptions. */
  public static final BBacnetPropertyIdentifier shedLevelDescriptions = new BBacnetPropertyIdentifier(SHED_LEVEL_DESCRIPTIONS);
  /** BBacnetPropertyIdentifier constant for shedLevels. */
  public static final BBacnetPropertyIdentifier shedLevels = new BBacnetPropertyIdentifier(SHED_LEVELS);
  /** BBacnetPropertyIdentifier constant for stateDescription. */
  public static final BBacnetPropertyIdentifier stateDescription = new BBacnetPropertyIdentifier(STATE_DESCRIPTION);
  /** BBacnetPropertyIdentifier constant for doorAlarmState. */
  public static final BBacnetPropertyIdentifier doorAlarmState = new BBacnetPropertyIdentifier(DOOR_ALARM_STATE);
  /** BBacnetPropertyIdentifier constant for doorExtendedPulseTime. */
  public static final BBacnetPropertyIdentifier doorExtendedPulseTime = new BBacnetPropertyIdentifier(DOOR_EXTENDED_PULSE_TIME);
  /** BBacnetPropertyIdentifier constant for doorMembers. */
  public static final BBacnetPropertyIdentifier doorMembers = new BBacnetPropertyIdentifier(DOOR_MEMBERS);
  /** BBacnetPropertyIdentifier constant for doorOpenTooLongTime. */
  public static final BBacnetPropertyIdentifier doorOpenTooLongTime = new BBacnetPropertyIdentifier(DOOR_OPEN_TOO_LONG_TIME);
  /** BBacnetPropertyIdentifier constant for doorPulseTime. */
  public static final BBacnetPropertyIdentifier doorPulseTime = new BBacnetPropertyIdentifier(DOOR_PULSE_TIME);
  /** BBacnetPropertyIdentifier constant for doorStatus. */
  public static final BBacnetPropertyIdentifier doorStatus = new BBacnetPropertyIdentifier(DOOR_STATUS);
  /** BBacnetPropertyIdentifier constant for doorUnlockDelayTime. */
  public static final BBacnetPropertyIdentifier doorUnlockDelayTime = new BBacnetPropertyIdentifier(DOOR_UNLOCK_DELAY_TIME);
  /** BBacnetPropertyIdentifier constant for lockStatus. */
  public static final BBacnetPropertyIdentifier lockStatus = new BBacnetPropertyIdentifier(LOCK_STATUS);
  /** BBacnetPropertyIdentifier constant for maskedAlarmValues. */
  public static final BBacnetPropertyIdentifier maskedAlarmValues = new BBacnetPropertyIdentifier(MASKED_ALARM_VALUES);
  /** BBacnetPropertyIdentifier constant for securedStatus. */
  public static final BBacnetPropertyIdentifier securedStatus = new BBacnetPropertyIdentifier(SECURED_STATUS);
  /** BBacnetPropertyIdentifier constant for absenteeLimit. */
  public static final BBacnetPropertyIdentifier absenteeLimit = new BBacnetPropertyIdentifier(ABSENTEE_LIMIT);
  /** BBacnetPropertyIdentifier constant for accessAlarmEvents. */
  public static final BBacnetPropertyIdentifier accessAlarmEvents = new BBacnetPropertyIdentifier(ACCESS_ALARM_EVENTS);
  /** BBacnetPropertyIdentifier constant for accessDoors. */
  public static final BBacnetPropertyIdentifier accessDoors = new BBacnetPropertyIdentifier(ACCESS_DOORS);
  /** BBacnetPropertyIdentifier constant for accessEvent. */
  public static final BBacnetPropertyIdentifier accessEvent = new BBacnetPropertyIdentifier(ACCESS_EVENT);
  /** BBacnetPropertyIdentifier constant for accessEventAuthenticationFactor. */
  public static final BBacnetPropertyIdentifier accessEventAuthenticationFactor = new BBacnetPropertyIdentifier(ACCESS_EVENT_AUTHENTICATION_FACTOR);
  /** BBacnetPropertyIdentifier constant for accessEventCredential. */
  public static final BBacnetPropertyIdentifier accessEventCredential = new BBacnetPropertyIdentifier(ACCESS_EVENT_CREDENTIAL);
  /** BBacnetPropertyIdentifier constant for accessEventTime. */
  public static final BBacnetPropertyIdentifier accessEventTime = new BBacnetPropertyIdentifier(ACCESS_EVENT_TIME);
  /** BBacnetPropertyIdentifier constant for accessTransactionEvents. */
  public static final BBacnetPropertyIdentifier accessTransactionEvents = new BBacnetPropertyIdentifier(ACCESS_TRANSACTION_EVENTS);
  /** BBacnetPropertyIdentifier constant for accompaniment. */
  public static final BBacnetPropertyIdentifier accompaniment = new BBacnetPropertyIdentifier(ACCOMPANIMENT);
  /** BBacnetPropertyIdentifier constant for accompanimentTime. */
  public static final BBacnetPropertyIdentifier accompanimentTime = new BBacnetPropertyIdentifier(ACCOMPANIMENT_TIME);
  /** BBacnetPropertyIdentifier constant for activationTime. */
  public static final BBacnetPropertyIdentifier activationTime = new BBacnetPropertyIdentifier(ACTIVATION_TIME);
  /** BBacnetPropertyIdentifier constant for activeAuthenticationPolicy. */
  public static final BBacnetPropertyIdentifier activeAuthenticationPolicy = new BBacnetPropertyIdentifier(ACTIVE_AUTHENTICATION_POLICY);
  /** BBacnetPropertyIdentifier constant for assignedAccessRights. */
  public static final BBacnetPropertyIdentifier assignedAccessRights = new BBacnetPropertyIdentifier(ASSIGNED_ACCESS_RIGHTS);
  /** BBacnetPropertyIdentifier constant for authenticationFactors. */
  public static final BBacnetPropertyIdentifier authenticationFactors = new BBacnetPropertyIdentifier(AUTHENTICATION_FACTORS);
  /** BBacnetPropertyIdentifier constant for authenticationPolicyList. */
  public static final BBacnetPropertyIdentifier authenticationPolicyList = new BBacnetPropertyIdentifier(AUTHENTICATION_POLICY_LIST);
  /** BBacnetPropertyIdentifier constant for authenticationPolicyNames. */
  public static final BBacnetPropertyIdentifier authenticationPolicyNames = new BBacnetPropertyIdentifier(AUTHENTICATION_POLICY_NAMES);
  /** BBacnetPropertyIdentifier constant for authenticationStatus. */
  public static final BBacnetPropertyIdentifier authenticationStatus = new BBacnetPropertyIdentifier(AUTHENTICATION_STATUS);
  /** BBacnetPropertyIdentifier constant for authorizationMode. */
  public static final BBacnetPropertyIdentifier authorizationMode = new BBacnetPropertyIdentifier(AUTHORIZATION_MODE);
  /** BBacnetPropertyIdentifier constant for belongsTo. */
  public static final BBacnetPropertyIdentifier belongsTo = new BBacnetPropertyIdentifier(BELONGS_TO);
  /** BBacnetPropertyIdentifier constant for credentialDisable. */
  public static final BBacnetPropertyIdentifier credentialDisable = new BBacnetPropertyIdentifier(CREDENTIAL_DISABLE);
  /** BBacnetPropertyIdentifier constant for credentialStatus. */
  public static final BBacnetPropertyIdentifier credentialStatus = new BBacnetPropertyIdentifier(CREDENTIAL_STATUS);
  /** BBacnetPropertyIdentifier constant for credentials. */
  public static final BBacnetPropertyIdentifier credentials = new BBacnetPropertyIdentifier(CREDENTIALS);
  /** BBacnetPropertyIdentifier constant for credentialsInZone. */
  public static final BBacnetPropertyIdentifier credentialsInZone = new BBacnetPropertyIdentifier(CREDENTIALS_IN_ZONE);
  /** BBacnetPropertyIdentifier constant for daysRemaining. */
  public static final BBacnetPropertyIdentifier daysRemaining = new BBacnetPropertyIdentifier(DAYS_REMAINING);
  /** BBacnetPropertyIdentifier constant for entryPoints. */
  public static final BBacnetPropertyIdentifier entryPoints = new BBacnetPropertyIdentifier(ENTRY_POINTS);
  /** BBacnetPropertyIdentifier constant for exitPoints. */
  public static final BBacnetPropertyIdentifier exitPoints = new BBacnetPropertyIdentifier(EXIT_POINTS);
  /** BBacnetPropertyIdentifier constant for expiryTime. */
  public static final BBacnetPropertyIdentifier expiryTime = new BBacnetPropertyIdentifier(EXPIRY_TIME);
  /** BBacnetPropertyIdentifier constant for extendedTimeEnable. */
  public static final BBacnetPropertyIdentifier extendedTimeEnable = new BBacnetPropertyIdentifier(EXTENDED_TIME_ENABLE);
  /** BBacnetPropertyIdentifier constant for failedAttemptEvents. */
  public static final BBacnetPropertyIdentifier failedAttemptEvents = new BBacnetPropertyIdentifier(FAILED_ATTEMPT_EVENTS);
  /** BBacnetPropertyIdentifier constant for failedAttempts. */
  public static final BBacnetPropertyIdentifier failedAttempts = new BBacnetPropertyIdentifier(FAILED_ATTEMPTS);
  /** BBacnetPropertyIdentifier constant for failedAttemptsTime. */
  public static final BBacnetPropertyIdentifier failedAttemptsTime = new BBacnetPropertyIdentifier(FAILED_ATTEMPTS_TIME);
  /** BBacnetPropertyIdentifier constant for lastAccessEvent. */
  public static final BBacnetPropertyIdentifier lastAccessEvent = new BBacnetPropertyIdentifier(LAST_ACCESS_EVENT);
  /** BBacnetPropertyIdentifier constant for lastAccessPoint. */
  public static final BBacnetPropertyIdentifier lastAccessPoint = new BBacnetPropertyIdentifier(LAST_ACCESS_POINT);
  /** BBacnetPropertyIdentifier constant for lastCredentialAdded. */
  public static final BBacnetPropertyIdentifier lastCredentialAdded = new BBacnetPropertyIdentifier(LAST_CREDENTIAL_ADDED);
  /** BBacnetPropertyIdentifier constant for lastCredentialAddedTime. */
  public static final BBacnetPropertyIdentifier lastCredentialAddedTime = new BBacnetPropertyIdentifier(LAST_CREDENTIAL_ADDED_TIME);
  /** BBacnetPropertyIdentifier constant for lastCredentialRemoved. */
  public static final BBacnetPropertyIdentifier lastCredentialRemoved = new BBacnetPropertyIdentifier(LAST_CREDENTIAL_REMOVED);
  /** BBacnetPropertyIdentifier constant for lastCredentialRemovedTime. */
  public static final BBacnetPropertyIdentifier lastCredentialRemovedTime = new BBacnetPropertyIdentifier(LAST_CREDENTIAL_REMOVED_TIME);
  /** BBacnetPropertyIdentifier constant for lastUseTime. */
  public static final BBacnetPropertyIdentifier lastUseTime = new BBacnetPropertyIdentifier(LAST_USE_TIME);
  /** BBacnetPropertyIdentifier constant for lockout. */
  public static final BBacnetPropertyIdentifier lockout = new BBacnetPropertyIdentifier(LOCKOUT);
  /** BBacnetPropertyIdentifier constant for lockoutRelinquishTime. */
  public static final BBacnetPropertyIdentifier lockoutRelinquishTime = new BBacnetPropertyIdentifier(LOCKOUT_RELINQUISH_TIME);
  /** BBacnetPropertyIdentifier constant for maxFailedAttempts. */
  public static final BBacnetPropertyIdentifier maxFailedAttempts = new BBacnetPropertyIdentifier(MAX_FAILED_ATTEMPTS);
  /** BBacnetPropertyIdentifier constant for members. */
  public static final BBacnetPropertyIdentifier members = new BBacnetPropertyIdentifier(MEMBERS);
  /** BBacnetPropertyIdentifier constant for musterPoint. */
  public static final BBacnetPropertyIdentifier musterPoint = new BBacnetPropertyIdentifier(MUSTER_POINT);
  /** BBacnetPropertyIdentifier constant for negativeAccessRules. */
  public static final BBacnetPropertyIdentifier negativeAccessRules = new BBacnetPropertyIdentifier(NEGATIVE_ACCESS_RULES);
  /** BBacnetPropertyIdentifier constant for numberOfAuthenticationPolicies. */
  public static final BBacnetPropertyIdentifier numberOfAuthenticationPolicies = new BBacnetPropertyIdentifier(NUMBER_OF_AUTHENTICATION_POLICIES);
  /** BBacnetPropertyIdentifier constant for occupancyCount. */
  public static final BBacnetPropertyIdentifier occupancyCount = new BBacnetPropertyIdentifier(OCCUPANCY_COUNT);
  /** BBacnetPropertyIdentifier constant for occupancyCountAdjust. */
  public static final BBacnetPropertyIdentifier occupancyCountAdjust = new BBacnetPropertyIdentifier(OCCUPANCY_COUNT_ADJUST);
  /** BBacnetPropertyIdentifier constant for occupancyCountEnable. */
  public static final BBacnetPropertyIdentifier occupancyCountEnable = new BBacnetPropertyIdentifier(OCCUPANCY_COUNT_ENABLE);
  /** BBacnetPropertyIdentifier constant for occupancyLowerLimit. */
  public static final BBacnetPropertyIdentifier occupancyLowerLimit = new BBacnetPropertyIdentifier(OCCUPANCY_LOWER_LIMIT);
  /** BBacnetPropertyIdentifier constant for occupancyLowerLimitEnforced. */
  public static final BBacnetPropertyIdentifier occupancyLowerLimitEnforced = new BBacnetPropertyIdentifier(OCCUPANCY_LOWER_LIMIT_ENFORCED);
  /** BBacnetPropertyIdentifier constant for occupancyState. */
  public static final BBacnetPropertyIdentifier occupancyState = new BBacnetPropertyIdentifier(OCCUPANCY_STATE);
  /** BBacnetPropertyIdentifier constant for occupancyUpperLimit. */
  public static final BBacnetPropertyIdentifier occupancyUpperLimit = new BBacnetPropertyIdentifier(OCCUPANCY_UPPER_LIMIT);
  /** BBacnetPropertyIdentifier constant for occupancyUpperLimitEnforced. */
  public static final BBacnetPropertyIdentifier occupancyUpperLimitEnforced = new BBacnetPropertyIdentifier(OCCUPANCY_UPPER_LIMIT_ENFORCED);
  /** BBacnetPropertyIdentifier constant for passbackMode. */
  public static final BBacnetPropertyIdentifier passbackMode = new BBacnetPropertyIdentifier(PASSBACK_MODE);
  /** BBacnetPropertyIdentifier constant for passbackTimeout. */
  public static final BBacnetPropertyIdentifier passbackTimeout = new BBacnetPropertyIdentifier(PASSBACK_TIMEOUT);
  /** BBacnetPropertyIdentifier constant for positiveAccessRules. */
  public static final BBacnetPropertyIdentifier positiveAccessRules = new BBacnetPropertyIdentifier(POSITIVE_ACCESS_RULES);
  /** BBacnetPropertyIdentifier constant for reasonForDisable. */
  public static final BBacnetPropertyIdentifier reasonForDisable = new BBacnetPropertyIdentifier(REASON_FOR_DISABLE);
  /** BBacnetPropertyIdentifier constant for supportedFormats. */
  public static final BBacnetPropertyIdentifier supportedFormats = new BBacnetPropertyIdentifier(SUPPORTED_FORMATS);
  /** BBacnetPropertyIdentifier constant for supportedFormatClasses. */
  public static final BBacnetPropertyIdentifier supportedFormatClasses = new BBacnetPropertyIdentifier(SUPPORTED_FORMAT_CLASSES);
  /** BBacnetPropertyIdentifier constant for threatAuthority. */
  public static final BBacnetPropertyIdentifier threatAuthority = new BBacnetPropertyIdentifier(THREAT_AUTHORITY);
  /** BBacnetPropertyIdentifier constant for threatLevel. */
  public static final BBacnetPropertyIdentifier threatLevel = new BBacnetPropertyIdentifier(THREAT_LEVEL);
  /** BBacnetPropertyIdentifier constant for traceFlag. */
  public static final BBacnetPropertyIdentifier traceFlag = new BBacnetPropertyIdentifier(TRACE_FLAG);
  /** BBacnetPropertyIdentifier constant for transactionNotificationClass. */
  public static final BBacnetPropertyIdentifier transactionNotificationClass = new BBacnetPropertyIdentifier(TRANSACTION_NOTIFICATION_CLASS);
  /** BBacnetPropertyIdentifier constant for userExternalIdentifier. */
  public static final BBacnetPropertyIdentifier userExternalIdentifier = new BBacnetPropertyIdentifier(USER_EXTERNAL_IDENTIFIER);
  /** BBacnetPropertyIdentifier constant for userInformationReference. */
  public static final BBacnetPropertyIdentifier userInformationReference = new BBacnetPropertyIdentifier(USER_INFORMATION_REFERENCE);
  /** BBacnetPropertyIdentifier constant for userName. */
  public static final BBacnetPropertyIdentifier userName = new BBacnetPropertyIdentifier(USER_NAME);
  /** BBacnetPropertyIdentifier constant for userType. */
  public static final BBacnetPropertyIdentifier userType = new BBacnetPropertyIdentifier(USER_TYPE);
  /** BBacnetPropertyIdentifier constant for usesRemaining. */
  public static final BBacnetPropertyIdentifier usesRemaining = new BBacnetPropertyIdentifier(USES_REMAINING);
  /** BBacnetPropertyIdentifier constant for zoneFrom. */
  public static final BBacnetPropertyIdentifier zoneFrom = new BBacnetPropertyIdentifier(ZONE_FROM);
  /** BBacnetPropertyIdentifier constant for zoneTo. */
  public static final BBacnetPropertyIdentifier zoneTo = new BBacnetPropertyIdentifier(ZONE_TO);
  /** BBacnetPropertyIdentifier constant for accessEventTag. */
  public static final BBacnetPropertyIdentifier accessEventTag = new BBacnetPropertyIdentifier(ACCESS_EVENT_TAG);
  /** BBacnetPropertyIdentifier constant for globalIdentifier. */
  public static final BBacnetPropertyIdentifier globalIdentifier = new BBacnetPropertyIdentifier(GLOBAL_IDENTIFIER);
  /** BBacnetPropertyIdentifier constant for verificationTime. */
  public static final BBacnetPropertyIdentifier verificationTime = new BBacnetPropertyIdentifier(VERIFICATION_TIME);
  /** BBacnetPropertyIdentifier constant for backupAndRestoreState. */
  public static final BBacnetPropertyIdentifier backupAndRestoreState = new BBacnetPropertyIdentifier(BACKUP_AND_RESTORE_STATE);
  /** BBacnetPropertyIdentifier constant for backupPreparationTime. */
  public static final BBacnetPropertyIdentifier backupPreparationTime = new BBacnetPropertyIdentifier(BACKUP_PREPARATION_TIME);
  /** BBacnetPropertyIdentifier constant for restoreCompletionTime. */
  public static final BBacnetPropertyIdentifier restoreCompletionTime = new BBacnetPropertyIdentifier(RESTORE_COMPLETION_TIME);
  /** BBacnetPropertyIdentifier constant for restorePreparationTime. */
  public static final BBacnetPropertyIdentifier restorePreparationTime = new BBacnetPropertyIdentifier(RESTORE_PREPARATION_TIME);
  /** BBacnetPropertyIdentifier constant for bitMask. */
  public static final BBacnetPropertyIdentifier bitMask = new BBacnetPropertyIdentifier(BIT_MASK);
  /** BBacnetPropertyIdentifier constant for bitText. */
  public static final BBacnetPropertyIdentifier bitText = new BBacnetPropertyIdentifier(BIT_TEXT);
  /** BBacnetPropertyIdentifier constant for isUtc. */
  public static final BBacnetPropertyIdentifier isUtc = new BBacnetPropertyIdentifier(IS_UTC);
  /** BBacnetPropertyIdentifier constant for groupMembers. */
  public static final BBacnetPropertyIdentifier groupMembers = new BBacnetPropertyIdentifier(GROUP_MEMBERS);
  /** BBacnetPropertyIdentifier constant for groupMemberNames. */
  public static final BBacnetPropertyIdentifier groupMemberNames = new BBacnetPropertyIdentifier(GROUP_MEMBER_NAMES);
  /** BBacnetPropertyIdentifier constant for memberStatusDlags. */
  public static final BBacnetPropertyIdentifier memberStatusDlags = new BBacnetPropertyIdentifier(MEMBER_STATUS_DLAGS);
  /** BBacnetPropertyIdentifier constant for requestedUpdateInterval. */
  public static final BBacnetPropertyIdentifier requestedUpdateInterval = new BBacnetPropertyIdentifier(REQUESTED_UPDATE_INTERVAL);
  /** BBacnetPropertyIdentifier constant for covuPeriod. */
  public static final BBacnetPropertyIdentifier covuPeriod = new BBacnetPropertyIdentifier(COVU_PERIOD);
  /** BBacnetPropertyIdentifier constant for covuRecipients. */
  public static final BBacnetPropertyIdentifier covuRecipients = new BBacnetPropertyIdentifier(COVU_RECIPIENTS);
  /** BBacnetPropertyIdentifier constant for eventMessageTexts. */
  public static final BBacnetPropertyIdentifier eventMessageTexts = new BBacnetPropertyIdentifier(EVENT_MESSAGE_TEXTS);
  /** BBacnetPropertyIdentifier constant for eventMessageTextsConfig. */
  public static final BBacnetPropertyIdentifier eventMessageTextsConfig = new BBacnetPropertyIdentifier(EVENT_MESSAGE_TEXTS_CONFIG);
  /** BBacnetPropertyIdentifier constant for eventDetectionEnable. */
  public static final BBacnetPropertyIdentifier eventDetectionEnable = new BBacnetPropertyIdentifier(EVENT_DETECTION_ENABLE);
  /** BBacnetPropertyIdentifier constant for eventAlgorithmInhibit. */
  public static final BBacnetPropertyIdentifier eventAlgorithmInhibit = new BBacnetPropertyIdentifier(EVENT_ALGORITHM_INHIBIT);
  /** BBacnetPropertyIdentifier constant for eventAlgorithmInhibitRef. */
  public static final BBacnetPropertyIdentifier eventAlgorithmInhibitRef = new BBacnetPropertyIdentifier(EVENT_ALGORITHM_INHIBIT_REF);
  /** BBacnetPropertyIdentifier constant for timeDelayNormal. */
  public static final BBacnetPropertyIdentifier timeDelayNormal = new BBacnetPropertyIdentifier(TIME_DELAY_NORMAL);
  /** BBacnetPropertyIdentifier constant for reliabilityEvaluationInhibit. */
  public static final BBacnetPropertyIdentifier reliabilityEvaluationInhibit = new BBacnetPropertyIdentifier(RELIABILITY_EVALUATION_INHIBIT);
  /** BBacnetPropertyIdentifier constant for faultParameters. */
  public static final BBacnetPropertyIdentifier faultParameters = new BBacnetPropertyIdentifier(FAULT_PARAMETERS);
  /** BBacnetPropertyIdentifier constant for faultType. */
  public static final BBacnetPropertyIdentifier faultType = new BBacnetPropertyIdentifier(FAULT_TYPE);
  /** BBacnetPropertyIdentifier constant for localForwardingOnly. */
  public static final BBacnetPropertyIdentifier localForwardingOnly = new BBacnetPropertyIdentifier(LOCAL_FORWARDING_ONLY);
  /** BBacnetPropertyIdentifier constant for processIdentifierFilter. */
  public static final BBacnetPropertyIdentifier processIdentifierFilter = new BBacnetPropertyIdentifier(PROCESS_IDENTIFIER_FILTER);
  /** BBacnetPropertyIdentifier constant for subscribedRecipients. */
  public static final BBacnetPropertyIdentifier subscribedRecipients = new BBacnetPropertyIdentifier(SUBSCRIBED_RECIPIENTS);
  /** BBacnetPropertyIdentifier constant for portFilter. */
  public static final BBacnetPropertyIdentifier portFilter = new BBacnetPropertyIdentifier(PORT_FILTER);
  /** BBacnetPropertyIdentifier constant for authorizationExemptions. */
  public static final BBacnetPropertyIdentifier authorizationExemptions = new BBacnetPropertyIdentifier(AUTHORIZATION_EXEMPTIONS);
  /** BBacnetPropertyIdentifier constant for allowGroupDelayInhibit. */
  public static final BBacnetPropertyIdentifier allowGroupDelayInhibit = new BBacnetPropertyIdentifier(ALLOW_GROUP_DELAY_INHIBIT);
  /** BBacnetPropertyIdentifier constant for channelNumber. */
  public static final BBacnetPropertyIdentifier channelNumber = new BBacnetPropertyIdentifier(CHANNEL_NUMBER);
  /** BBacnetPropertyIdentifier constant for controlGroups. */
  public static final BBacnetPropertyIdentifier controlGroups = new BBacnetPropertyIdentifier(CONTROL_GROUPS);
  /** BBacnetPropertyIdentifier constant for executionDelay. */
  public static final BBacnetPropertyIdentifier executionDelay = new BBacnetPropertyIdentifier(EXECUTION_DELAY);
  /** BBacnetPropertyIdentifier constant for lastPriority. */
  public static final BBacnetPropertyIdentifier lastPriority = new BBacnetPropertyIdentifier(LAST_PRIORITY);
  /** BBacnetPropertyIdentifier constant for writeStatus. */
  public static final BBacnetPropertyIdentifier writeStatus = new BBacnetPropertyIdentifier(WRITE_STATUS);
  /** BBacnetPropertyIdentifier constant for propertyList. */
  public static final BBacnetPropertyIdentifier propertyList = new BBacnetPropertyIdentifier(PROPERTY_LIST);
  /** BBacnetPropertyIdentifier constant for serialNumber. */
  public static final BBacnetPropertyIdentifier serialNumber = new BBacnetPropertyIdentifier(SERIAL_NUMBER);
  /** BBacnetPropertyIdentifier constant for blinkWarnEnable. */
  public static final BBacnetPropertyIdentifier blinkWarnEnable = new BBacnetPropertyIdentifier(BLINK_WARN_ENABLE);
  /** BBacnetPropertyIdentifier constant for defaultFadetime. */
  public static final BBacnetPropertyIdentifier defaultFadetime = new BBacnetPropertyIdentifier(DEFAULT_FADETIME);
  /** BBacnetPropertyIdentifier constant for defaultRamprate. */
  public static final BBacnetPropertyIdentifier defaultRamprate = new BBacnetPropertyIdentifier(DEFAULT_RAMPRATE);
  /** BBacnetPropertyIdentifier constant for defaultStepIncrement. */
  public static final BBacnetPropertyIdentifier defaultStepIncrement = new BBacnetPropertyIdentifier(DEFAULT_STEP_INCREMENT);
  /** BBacnetPropertyIdentifier constant for egressTime. */
  public static final BBacnetPropertyIdentifier egressTime = new BBacnetPropertyIdentifier(EGRESS_TIME);
  /** BBacnetPropertyIdentifier constant for inProgress. */
  public static final BBacnetPropertyIdentifier inProgress = new BBacnetPropertyIdentifier(IN_PROGRESS);
  /** BBacnetPropertyIdentifier constant for instantaneousPower. */
  public static final BBacnetPropertyIdentifier instantaneousPower = new BBacnetPropertyIdentifier(INSTANTANEOUS_POWER);
  /** BBacnetPropertyIdentifier constant for lightingCommand. */
  public static final BBacnetPropertyIdentifier lightingCommand = new BBacnetPropertyIdentifier(LIGHTING_COMMAND);
  /** BBacnetPropertyIdentifier constant for lightingCommandDefaultPriority. */
  public static final BBacnetPropertyIdentifier lightingCommandDefaultPriority = new BBacnetPropertyIdentifier(LIGHTING_COMMAND_DEFAULT_PRIORITY);
  /** BBacnetPropertyIdentifier constant for maxActualValue. */
  public static final BBacnetPropertyIdentifier maxActualValue = new BBacnetPropertyIdentifier(MAX_ACTUAL_VALUE);
  /** BBacnetPropertyIdentifier constant for minActualValue. */
  public static final BBacnetPropertyIdentifier minActualValue = new BBacnetPropertyIdentifier(MIN_ACTUAL_VALUE);
  /** BBacnetPropertyIdentifier constant for power. */
  public static final BBacnetPropertyIdentifier power = new BBacnetPropertyIdentifier(POWER);
  /** BBacnetPropertyIdentifier constant for transition. */
  public static final BBacnetPropertyIdentifier transition = new BBacnetPropertyIdentifier(TRANSITION);
  /** BBacnetPropertyIdentifier constant for egressActive. */
  public static final BBacnetPropertyIdentifier egressActive = new BBacnetPropertyIdentifier(EGRESS_ACTIVE);
  /** BBacnetPropertyIdentifier constant for interfaceValue. */
  public static final BBacnetPropertyIdentifier interfaceValue = new BBacnetPropertyIdentifier(INTERFACE_VALUE);
  /** BBacnetPropertyIdentifier constant for faultHighLimit. */
  public static final BBacnetPropertyIdentifier faultHighLimit = new BBacnetPropertyIdentifier(FAULT_HIGH_LIMIT);
  /** BBacnetPropertyIdentifier constant for faultLowLimit. */
  public static final BBacnetPropertyIdentifier faultLowLimit = new BBacnetPropertyIdentifier(FAULT_LOW_LIMIT);
  /** BBacnetPropertyIdentifier constant for lowDiffLimit. */
  public static final BBacnetPropertyIdentifier lowDiffLimit = new BBacnetPropertyIdentifier(LOW_DIFF_LIMIT);
  /** BBacnetPropertyIdentifier constant for strikeCount. */
  public static final BBacnetPropertyIdentifier strikeCount = new BBacnetPropertyIdentifier(STRIKE_COUNT);
  /** BBacnetPropertyIdentifier constant for timeOfStrikeCountReset. */
  public static final BBacnetPropertyIdentifier timeOfStrikeCountReset = new BBacnetPropertyIdentifier(TIME_OF_STRIKE_COUNT_RESET);
  /** BBacnetPropertyIdentifier constant for defaultTimeout. */
  public static final BBacnetPropertyIdentifier defaultTimeout = new BBacnetPropertyIdentifier(DEFAULT_TIMEOUT);
  /** BBacnetPropertyIdentifier constant for initialTimeout. */
  public static final BBacnetPropertyIdentifier initialTimeout = new BBacnetPropertyIdentifier(INITIAL_TIMEOUT);
  /** BBacnetPropertyIdentifier constant for lastStateChange. */
  public static final BBacnetPropertyIdentifier lastStateChange = new BBacnetPropertyIdentifier(LAST_STATE_CHANGE);
  /** BBacnetPropertyIdentifier constant for stateChangeValues. */
  public static final BBacnetPropertyIdentifier stateChangeValues = new BBacnetPropertyIdentifier(STATE_CHANGE_VALUES);
  /** BBacnetPropertyIdentifier constant for timerRunning. */
  public static final BBacnetPropertyIdentifier timerRunning = new BBacnetPropertyIdentifier(TIMER_RUNNING);
  /** BBacnetPropertyIdentifier constant for timerState. */
  public static final BBacnetPropertyIdentifier timerState = new BBacnetPropertyIdentifier(TIMER_STATE);
  /** BBacnetPropertyIdentifier constant for apduLength. */
  public static final BBacnetPropertyIdentifier apduLength = new BBacnetPropertyIdentifier(APDU_LENGTH);
  /** BBacnetPropertyIdentifier constant for ipAddress. */
  public static final BBacnetPropertyIdentifier ipAddress = new BBacnetPropertyIdentifier(IP_ADDRESS);
  /** BBacnetPropertyIdentifier constant for ipDefaultGateway. */
  public static final BBacnetPropertyIdentifier ipDefaultGateway = new BBacnetPropertyIdentifier(IP_DEFAULT_GATEWAY);
  /** BBacnetPropertyIdentifier constant for ipDhcpEnable. */
  public static final BBacnetPropertyIdentifier ipDhcpEnable = new BBacnetPropertyIdentifier(IP_DHCP_ENABLE);
  /** BBacnetPropertyIdentifier constant for ipDhcpLeaseTime. */
  public static final BBacnetPropertyIdentifier ipDhcpLeaseTime = new BBacnetPropertyIdentifier(IP_DHCP_LEASE_TIME);
  /** BBacnetPropertyIdentifier constant for ipDhcpLeaseTimeRemaining. */
  public static final BBacnetPropertyIdentifier ipDhcpLeaseTimeRemaining = new BBacnetPropertyIdentifier(IP_DHCP_LEASE_TIME_REMAINING);
  /** BBacnetPropertyIdentifier constant for ipDhcpServer. */
  public static final BBacnetPropertyIdentifier ipDhcpServer = new BBacnetPropertyIdentifier(IP_DHCP_SERVER);
  /** BBacnetPropertyIdentifier constant for ipDnsServer. */
  public static final BBacnetPropertyIdentifier ipDnsServer = new BBacnetPropertyIdentifier(IP_DNS_SERVER);
  /** BBacnetPropertyIdentifier constant for bacnetIpGlobalAddress. */
  public static final BBacnetPropertyIdentifier bacnetIpGlobalAddress = new BBacnetPropertyIdentifier(BACNET_IP_GLOBAL_ADDRESS);
  /** BBacnetPropertyIdentifier constant for bacnetIpMode. */
  public static final BBacnetPropertyIdentifier bacnetIpMode = new BBacnetPropertyIdentifier(BACNET_IP_MODE);
  /** BBacnetPropertyIdentifier constant for bacnetIpMulticastAddress. */
  public static final BBacnetPropertyIdentifier bacnetIpMulticastAddress = new BBacnetPropertyIdentifier(BACNET_IP_MULTICAST_ADDRESS);
  /** BBacnetPropertyIdentifier constant for bacnetIpNatTraversal. */
  public static final BBacnetPropertyIdentifier bacnetIpNatTraversal = new BBacnetPropertyIdentifier(BACNET_IP_NAT_TRAVERSAL);
  /** BBacnetPropertyIdentifier constant for ipSubnetMask. */
  public static final BBacnetPropertyIdentifier ipSubnetMask = new BBacnetPropertyIdentifier(IP_SUBNET_MASK);
  /** BBacnetPropertyIdentifier constant for bacnetIpUdpPort. */
  public static final BBacnetPropertyIdentifier bacnetIpUdpPort = new BBacnetPropertyIdentifier(BACNET_IP_UDP_PORT);
  /** BBacnetPropertyIdentifier constant for bbmdAcceptFdRegistrations. */
  public static final BBacnetPropertyIdentifier bbmdAcceptFdRegistrations = new BBacnetPropertyIdentifier(BBMD_ACCEPT_FD_REGISTRATIONS);
  /** BBacnetPropertyIdentifier constant for bbmdBroadcastDistributionTable. */
  public static final BBacnetPropertyIdentifier bbmdBroadcastDistributionTable = new BBacnetPropertyIdentifier(BBMD_BROADCAST_DISTRIBUTION_TABLE);
  /** BBacnetPropertyIdentifier constant for bbmdForeignDeviceTable. */
  public static final BBacnetPropertyIdentifier bbmdForeignDeviceTable = new BBacnetPropertyIdentifier(BBMD_FOREIGN_DEVICE_TABLE);
  /** BBacnetPropertyIdentifier constant for changesPending. */
  public static final BBacnetPropertyIdentifier changesPending = new BBacnetPropertyIdentifier(CHANGES_PENDING);
  /** BBacnetPropertyIdentifier constant for command. */
  public static final BBacnetPropertyIdentifier command = new BBacnetPropertyIdentifier(COMMAND);
  /** BBacnetPropertyIdentifier constant for fdBbmdAddress. */
  public static final BBacnetPropertyIdentifier fdBbmdAddress = new BBacnetPropertyIdentifier(FD_BBMD_ADDRESS);
  /** BBacnetPropertyIdentifier constant for fdSubscriptionLifetime. */
  public static final BBacnetPropertyIdentifier fdSubscriptionLifetime = new BBacnetPropertyIdentifier(FD_SUBSCRIPTION_LIFETIME);
  /** BBacnetPropertyIdentifier constant for linkSpeed. */
  public static final BBacnetPropertyIdentifier linkSpeed = new BBacnetPropertyIdentifier(LINK_SPEED);
  /** BBacnetPropertyIdentifier constant for linkSpeeds. */
  public static final BBacnetPropertyIdentifier linkSpeeds = new BBacnetPropertyIdentifier(LINK_SPEEDS);
  /** BBacnetPropertyIdentifier constant for linkSpeedAutonegotiate. */
  public static final BBacnetPropertyIdentifier linkSpeedAutonegotiate = new BBacnetPropertyIdentifier(LINK_SPEED_AUTONEGOTIATE);
  /** BBacnetPropertyIdentifier constant for macAddress. */
  public static final BBacnetPropertyIdentifier macAddress = new BBacnetPropertyIdentifier(MAC_ADDRESS);
  /** BBacnetPropertyIdentifier constant for networkInterfaceName. */
  public static final BBacnetPropertyIdentifier networkInterfaceName = new BBacnetPropertyIdentifier(NETWORK_INTERFACE_NAME);
  /** BBacnetPropertyIdentifier constant for networkNumber. */
  public static final BBacnetPropertyIdentifier networkNumber = new BBacnetPropertyIdentifier(NETWORK_NUMBER);
  /** BBacnetPropertyIdentifier constant for networkNumberQuality. */
  public static final BBacnetPropertyIdentifier networkNumberQuality = new BBacnetPropertyIdentifier(NETWORK_NUMBER_QUALITY);
  /** BBacnetPropertyIdentifier constant for networkType. */
  public static final BBacnetPropertyIdentifier networkType = new BBacnetPropertyIdentifier(NETWORK_TYPE);
  /** BBacnetPropertyIdentifier constant for routingTable. */
  public static final BBacnetPropertyIdentifier routingTable = new BBacnetPropertyIdentifier(ROUTING_TABLE);
  /** BBacnetPropertyIdentifier constant for virtualMacAddressTable. */
  public static final BBacnetPropertyIdentifier virtualMacAddressTable = new BBacnetPropertyIdentifier(VIRTUAL_MAC_ADDRESS_TABLE);
  /** BBacnetPropertyIdentifier constant for commandTimeArray. */
  public static final BBacnetPropertyIdentifier commandTimeArray = new BBacnetPropertyIdentifier(COMMAND_TIME_ARRAY);
  /** BBacnetPropertyIdentifier constant for currentCommandPriority. */
  public static final BBacnetPropertyIdentifier currentCommandPriority = new BBacnetPropertyIdentifier(CURRENT_COMMAND_PRIORITY);
  /** BBacnetPropertyIdentifier constant for lastCommandTime. */
  public static final BBacnetPropertyIdentifier lastCommandTime = new BBacnetPropertyIdentifier(LAST_COMMAND_TIME);
  /** BBacnetPropertyIdentifier constant for valueSource. */
  public static final BBacnetPropertyIdentifier valueSource = new BBacnetPropertyIdentifier(VALUE_SOURCE);
  /** BBacnetPropertyIdentifier constant for valueSourceArray. */
  public static final BBacnetPropertyIdentifier valueSourceArray = new BBacnetPropertyIdentifier(VALUE_SOURCE_ARRAY);
  /** BBacnetPropertyIdentifier constant for bacnetIpv6Mode. */
  public static final BBacnetPropertyIdentifier bacnetIpv6Mode = new BBacnetPropertyIdentifier(BACNET_IPV_6MODE);
  /** BBacnetPropertyIdentifier constant for ipv6Address. */
  public static final BBacnetPropertyIdentifier ipv6Address = new BBacnetPropertyIdentifier(IPV_6ADDRESS);
  /** BBacnetPropertyIdentifier constant for ipv6PrefixLength. */
  public static final BBacnetPropertyIdentifier ipv6PrefixLength = new BBacnetPropertyIdentifier(IPV_6PREFIX_LENGTH);
  /** BBacnetPropertyIdentifier constant for bacnetIpv6UdpPort. */
  public static final BBacnetPropertyIdentifier bacnetIpv6UdpPort = new BBacnetPropertyIdentifier(BACNET_IPV_6UDP_PORT);
  /** BBacnetPropertyIdentifier constant for ipv6DefaultGateway. */
  public static final BBacnetPropertyIdentifier ipv6DefaultGateway = new BBacnetPropertyIdentifier(IPV_6DEFAULT_GATEWAY);
  /** BBacnetPropertyIdentifier constant for bacnetIpv6MulticastAddress. */
  public static final BBacnetPropertyIdentifier bacnetIpv6MulticastAddress = new BBacnetPropertyIdentifier(BACNET_IPV_6MULTICAST_ADDRESS);
  /** BBacnetPropertyIdentifier constant for ipv6DnsServer. */
  public static final BBacnetPropertyIdentifier ipv6DnsServer = new BBacnetPropertyIdentifier(IPV_6DNS_SERVER);
  /** BBacnetPropertyIdentifier constant for ipv6AutoAddressingEnable. */
  public static final BBacnetPropertyIdentifier ipv6AutoAddressingEnable = new BBacnetPropertyIdentifier(IPV_6AUTO_ADDRESSING_ENABLE);
  /** BBacnetPropertyIdentifier constant for ipv6DhcpLeaseTime. */
  public static final BBacnetPropertyIdentifier ipv6DhcpLeaseTime = new BBacnetPropertyIdentifier(IPV_6DHCP_LEASE_TIME);
  /** BBacnetPropertyIdentifier constant for ipv6DhcpLeaseTimeRemaining. */
  public static final BBacnetPropertyIdentifier ipv6DhcpLeaseTimeRemaining = new BBacnetPropertyIdentifier(IPV_6DHCP_LEASE_TIME_REMAINING);
  /** BBacnetPropertyIdentifier constant for ipv6DhcpServer. */
  public static final BBacnetPropertyIdentifier ipv6DhcpServer = new BBacnetPropertyIdentifier(IPV_6DHCP_SERVER);
  /** BBacnetPropertyIdentifier constant for ipv6ZoneIndex. */
  public static final BBacnetPropertyIdentifier ipv6ZoneIndex = new BBacnetPropertyIdentifier(IPV_6ZONE_INDEX);
  /** BBacnetPropertyIdentifier constant for assignedLandingCalls. */
  public static final BBacnetPropertyIdentifier assignedLandingCalls = new BBacnetPropertyIdentifier(ASSIGNED_LANDING_CALLS);
  /** BBacnetPropertyIdentifier constant for carAssignedDirection. */
  public static final BBacnetPropertyIdentifier carAssignedDirection = new BBacnetPropertyIdentifier(CAR_ASSIGNED_DIRECTION);
  /** BBacnetPropertyIdentifier constant for carDoorCommand. */
  public static final BBacnetPropertyIdentifier carDoorCommand = new BBacnetPropertyIdentifier(CAR_DOOR_COMMAND);
  /** BBacnetPropertyIdentifier constant for carDoorStatus. */
  public static final BBacnetPropertyIdentifier carDoorStatus = new BBacnetPropertyIdentifier(CAR_DOOR_STATUS);
  /** BBacnetPropertyIdentifier constant for carDoorText. */
  public static final BBacnetPropertyIdentifier carDoorText = new BBacnetPropertyIdentifier(CAR_DOOR_TEXT);
  /** BBacnetPropertyIdentifier constant for carDoorZone. */
  public static final BBacnetPropertyIdentifier carDoorZone = new BBacnetPropertyIdentifier(CAR_DOOR_ZONE);
  /** BBacnetPropertyIdentifier constant for carDriveStatus. */
  public static final BBacnetPropertyIdentifier carDriveStatus = new BBacnetPropertyIdentifier(CAR_DRIVE_STATUS);
  /** BBacnetPropertyIdentifier constant for carLoad. */
  public static final BBacnetPropertyIdentifier carLoad = new BBacnetPropertyIdentifier(CAR_LOAD);
  /** BBacnetPropertyIdentifier constant for carLoadUnits. */
  public static final BBacnetPropertyIdentifier carLoadUnits = new BBacnetPropertyIdentifier(CAR_LOAD_UNITS);
  /** BBacnetPropertyIdentifier constant for carMode. */
  public static final BBacnetPropertyIdentifier carMode = new BBacnetPropertyIdentifier(CAR_MODE);
  /** BBacnetPropertyIdentifier constant for carMovingDirection. */
  public static final BBacnetPropertyIdentifier carMovingDirection = new BBacnetPropertyIdentifier(CAR_MOVING_DIRECTION);
  /** BBacnetPropertyIdentifier constant for carPosition. */
  public static final BBacnetPropertyIdentifier carPosition = new BBacnetPropertyIdentifier(CAR_POSITION);
  /** BBacnetPropertyIdentifier constant for elevatorGroup. */
  public static final BBacnetPropertyIdentifier elevatorGroup = new BBacnetPropertyIdentifier(ELEVATOR_GROUP);
  /** BBacnetPropertyIdentifier constant for energyMeter. */
  public static final BBacnetPropertyIdentifier energyMeter = new BBacnetPropertyIdentifier(ENERGY_METER);
  /** BBacnetPropertyIdentifier constant for energyMeterRef. */
  public static final BBacnetPropertyIdentifier energyMeterRef = new BBacnetPropertyIdentifier(ENERGY_METER_REF);
  /** BBacnetPropertyIdentifier constant for escalatorMode. */
  public static final BBacnetPropertyIdentifier escalatorMode = new BBacnetPropertyIdentifier(ESCALATOR_MODE);
  /** BBacnetPropertyIdentifier constant for faultSignals. */
  public static final BBacnetPropertyIdentifier faultSignals = new BBacnetPropertyIdentifier(FAULT_SIGNALS);
  /** BBacnetPropertyIdentifier constant for floorText. */
  public static final BBacnetPropertyIdentifier floorText = new BBacnetPropertyIdentifier(FLOOR_TEXT);
  /** BBacnetPropertyIdentifier constant for groupId. */
  public static final BBacnetPropertyIdentifier groupId = new BBacnetPropertyIdentifier(GROUP_ID);
  /** BBacnetPropertyIdentifier constant for groupMode. */
  public static final BBacnetPropertyIdentifier groupMode = new BBacnetPropertyIdentifier(GROUP_MODE);
  /** BBacnetPropertyIdentifier constant for higherDeck. */
  public static final BBacnetPropertyIdentifier higherDeck = new BBacnetPropertyIdentifier(HIGHER_DECK);
  /** BBacnetPropertyIdentifier constant for installationId. */
  public static final BBacnetPropertyIdentifier installationId = new BBacnetPropertyIdentifier(INSTALLATION_ID);
  /** BBacnetPropertyIdentifier constant for landingCalls. */
  public static final BBacnetPropertyIdentifier landingCalls = new BBacnetPropertyIdentifier(LANDING_CALLS);
  /** BBacnetPropertyIdentifier constant for landingCallControl. */
  public static final BBacnetPropertyIdentifier landingCallControl = new BBacnetPropertyIdentifier(LANDING_CALL_CONTROL);
  /** BBacnetPropertyIdentifier constant for landingDoorStatus. */
  public static final BBacnetPropertyIdentifier landingDoorStatus = new BBacnetPropertyIdentifier(LANDING_DOOR_STATUS);
  /** BBacnetPropertyIdentifier constant for lowerDeck. */
  public static final BBacnetPropertyIdentifier lowerDeck = new BBacnetPropertyIdentifier(LOWER_DECK);
  /** BBacnetPropertyIdentifier constant for machineRoomId. */
  public static final BBacnetPropertyIdentifier machineRoomId = new BBacnetPropertyIdentifier(MACHINE_ROOM_ID);
  /** BBacnetPropertyIdentifier constant for makingCarCall. */
  public static final BBacnetPropertyIdentifier makingCarCall = new BBacnetPropertyIdentifier(MAKING_CAR_CALL);
  /** BBacnetPropertyIdentifier constant for nextStoppingFloor. */
  public static final BBacnetPropertyIdentifier nextStoppingFloor = new BBacnetPropertyIdentifier(NEXT_STOPPING_FLOOR);
  /** BBacnetPropertyIdentifier constant for operationDirection. */
  public static final BBacnetPropertyIdentifier operationDirection = new BBacnetPropertyIdentifier(OPERATION_DIRECTION);
  /** BBacnetPropertyIdentifier constant for passengerAlarm. */
  public static final BBacnetPropertyIdentifier passengerAlarm = new BBacnetPropertyIdentifier(PASSENGER_ALARM);
  /** BBacnetPropertyIdentifier constant for powerMode. */
  public static final BBacnetPropertyIdentifier powerMode = new BBacnetPropertyIdentifier(POWER_MODE);
  /** BBacnetPropertyIdentifier constant for registeredCarCall. */
  public static final BBacnetPropertyIdentifier registeredCarCall = new BBacnetPropertyIdentifier(REGISTERED_CAR_CALL);
  /** BBacnetPropertyIdentifier constant for activeCovMultipleSubscriptions. */
  public static final BBacnetPropertyIdentifier activeCovMultipleSubscriptions = new BBacnetPropertyIdentifier(ACTIVE_COV_MULTIPLE_SUBSCRIPTIONS);
  /** BBacnetPropertyIdentifier constant for protocolLevel. */
  public static final BBacnetPropertyIdentifier protocolLevel = new BBacnetPropertyIdentifier(PROTOCOL_LEVEL);
  /** BBacnetPropertyIdentifier constant for referencePort. */
  public static final BBacnetPropertyIdentifier referencePort = new BBacnetPropertyIdentifier(REFERENCE_PORT);
  /** BBacnetPropertyIdentifier constant for deployedProfileLocation. */
  public static final BBacnetPropertyIdentifier deployedProfileLocation = new BBacnetPropertyIdentifier(DEPLOYED_PROFILE_LOCATION);
  /** BBacnetPropertyIdentifier constant for profileLocation. */
  public static final BBacnetPropertyIdentifier profileLocation = new BBacnetPropertyIdentifier(PROFILE_LOCATION);
  /** BBacnetPropertyIdentifier constant for tags. */
  public static final BBacnetPropertyIdentifier tags = new BBacnetPropertyIdentifier(TAGS);
  /** BBacnetPropertyIdentifier constant for subordinateNodeTypes. */
  public static final BBacnetPropertyIdentifier subordinateNodeTypes = new BBacnetPropertyIdentifier(SUBORDINATE_NODE_TYPES);
  /** BBacnetPropertyIdentifier constant for subordinateTags. */
  public static final BBacnetPropertyIdentifier subordinateTags = new BBacnetPropertyIdentifier(SUBORDINATE_TAGS);
  /** BBacnetPropertyIdentifier constant for subordinateRelationships. */
  public static final BBacnetPropertyIdentifier subordinateRelationships = new BBacnetPropertyIdentifier(SUBORDINATE_RELATIONSHIPS);
  /** BBacnetPropertyIdentifier constant for defaultSubordinateRelationship. */
  public static final BBacnetPropertyIdentifier defaultSubordinateRelationship = new BBacnetPropertyIdentifier(DEFAULT_SUBORDINATE_RELATIONSHIP);
  /** BBacnetPropertyIdentifier constant for represents. */
  public static final BBacnetPropertyIdentifier represents = new BBacnetPropertyIdentifier(REPRESENTS);
  /** BBacnetPropertyIdentifier constant for defaultPresentValue. */
  public static final BBacnetPropertyIdentifier defaultPresentValue = new BBacnetPropertyIdentifier(DEFAULT_PRESENT_VALUE);
  /** BBacnetPropertyIdentifier constant for presentStage. */
  public static final BBacnetPropertyIdentifier presentStage = new BBacnetPropertyIdentifier(PRESENT_STAGE);
  /** BBacnetPropertyIdentifier constant for stages. */
  public static final BBacnetPropertyIdentifier stages = new BBacnetPropertyIdentifier(STAGES);
  /** BBacnetPropertyIdentifier constant for stageNames. */
  public static final BBacnetPropertyIdentifier stageNames = new BBacnetPropertyIdentifier(STAGE_NAMES);
  /** BBacnetPropertyIdentifier constant for targetReferences. */
  public static final BBacnetPropertyIdentifier targetReferences = new BBacnetPropertyIdentifier(TARGET_REFERENCES);
  /** BBacnetPropertyIdentifier constant for auditSourceReporter. */
  public static final BBacnetPropertyIdentifier auditSourceReporter = new BBacnetPropertyIdentifier(AUDIT_SOURCE_REPORTER);
  /** BBacnetPropertyIdentifier constant for auditLevel. */
  public static final BBacnetPropertyIdentifier auditLevel = new BBacnetPropertyIdentifier(AUDIT_LEVEL);
  /** BBacnetPropertyIdentifier constant for auditNotificationRecipient. */
  public static final BBacnetPropertyIdentifier auditNotificationRecipient = new BBacnetPropertyIdentifier(AUDIT_NOTIFICATION_RECIPIENT);
  /** BBacnetPropertyIdentifier constant for auditPriorityFilter. */
  public static final BBacnetPropertyIdentifier auditPriorityFilter = new BBacnetPropertyIdentifier(AUDIT_PRIORITY_FILTER);
  /** BBacnetPropertyIdentifier constant for auditableOperations. */
  public static final BBacnetPropertyIdentifier auditableOperations = new BBacnetPropertyIdentifier(AUDITABLE_OPERATIONS);
  /** BBacnetPropertyIdentifier constant for deleteOnForward. */
  public static final BBacnetPropertyIdentifier deleteOnForward = new BBacnetPropertyIdentifier(DELETE_ON_FORWARD);
  /** BBacnetPropertyIdentifier constant for maximumSendDelay. */
  public static final BBacnetPropertyIdentifier maximumSendDelay = new BBacnetPropertyIdentifier(MAXIMUM_SEND_DELAY);
  /** BBacnetPropertyIdentifier constant for monitoredObjects. */
  public static final BBacnetPropertyIdentifier monitoredObjects = new BBacnetPropertyIdentifier(MONITORED_OBJECTS);
  /** BBacnetPropertyIdentifier constant for sendNow. */
  public static final BBacnetPropertyIdentifier sendNow = new BBacnetPropertyIdentifier(SEND_NOW);
  /** BBacnetPropertyIdentifier constant for floorNumber. */
  public static final BBacnetPropertyIdentifier floorNumber = new BBacnetPropertyIdentifier(FLOOR_NUMBER);
  /** BBacnetPropertyIdentifier constant for deviceUuid. */
  public static final BBacnetPropertyIdentifier deviceUuid = new BBacnetPropertyIdentifier(DEVICE_UUID);
  /** BBacnetPropertyIdentifier constant for removed1. */
  public static final BBacnetPropertyIdentifier removed1 = new BBacnetPropertyIdentifier(REMOVED_1);
  /** BBacnetPropertyIdentifier constant for issueConfirmedNotifications. */
  public static final BBacnetPropertyIdentifier issueConfirmedNotifications = new BBacnetPropertyIdentifier(ISSUE_CONFIRMED_NOTIFICATIONS);
  /** BBacnetPropertyIdentifier constant for listOfSessionKeys. */
  public static final BBacnetPropertyIdentifier listOfSessionKeys = new BBacnetPropertyIdentifier(LIST_OF_SESSION_KEYS);
  /** BBacnetPropertyIdentifier constant for protocolConformanceClass. */
  public static final BBacnetPropertyIdentifier protocolConformanceClass = new BBacnetPropertyIdentifier(PROTOCOL_CONFORMANCE_CLASS);
  /** BBacnetPropertyIdentifier constant for recipient. */
  public static final BBacnetPropertyIdentifier recipient = new BBacnetPropertyIdentifier(RECIPIENT);
  /** BBacnetPropertyIdentifier constant for currentNotifyTime. */
  public static final BBacnetPropertyIdentifier currentNotifyTime = new BBacnetPropertyIdentifier(CURRENT_NOTIFY_TIME);
  /** BBacnetPropertyIdentifier constant for previousNotifyTime. */
  public static final BBacnetPropertyIdentifier previousNotifyTime = new BBacnetPropertyIdentifier(PREVIOUS_NOTIFY_TIME);
  /** BBacnetPropertyIdentifier constant for masterExemption. */
  public static final BBacnetPropertyIdentifier masterExemption = new BBacnetPropertyIdentifier(MASTER_EXEMPTION);
  /** BBacnetPropertyIdentifier constant for occupancyExemption. */
  public static final BBacnetPropertyIdentifier occupancyExemption = new BBacnetPropertyIdentifier(OCCUPANCY_EXEMPTION);
  /** BBacnetPropertyIdentifier constant for passbackExemption. */
  public static final BBacnetPropertyIdentifier passbackExemption = new BBacnetPropertyIdentifier(PASSBACK_EXEMPTION);

  /** Factory method with ordinal. */
  public static BBacnetPropertyIdentifier make(int ordinal)
  {
    return (BBacnetPropertyIdentifier)ackedTransitions.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetPropertyIdentifier make(String tag)
  {
    return (BBacnetPropertyIdentifier)ackedTransitions.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetPropertyIdentifier(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetPropertyIdentifier DEFAULT = ackedTransitions;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPropertyIdentifier.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = DEVICE_UUID;
  public static final int MAX_RESERVED_ID = 511;
  public static final int MAX_ID = 4194303;

  //  public static final BBacnetPropertyIdentifier DEFAULT = presentValue;
  public static final String INVALID_OR_UNSPECIFIED_ID = "Invalid ID";

  /**
   * Ordinal value for logEnable, renamed to enable in 135-2004b-4.
   * This is retained here only for the benefit of dependent projects.
   *
   * @deprecated as of 3.5.
   */
  @Deprecated
  public static final int LOG_ENABLE = 133;


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
    if (DEFAULT.getRange().isOrdinal(id))
      return DEFAULT.getRange().getTag(id);
    if (isAshrae(id))
      return ASHRAE_PREFIX + id;
    if (isProprietary(id))
      return PROPRIETARY_PREFIX + id;
    return INVALID_OR_UNSPECIFIED_ID;
  }

  /**
   * Get the ordinal for the given tag.
   *
   * @return the ordinal for the tag, if it is known,
   * or generate one if the tag uses standard prefixes.
   */
  public static int ordinal(String tag)
  {
    try
    {
      return DEFAULT.getRange().tagToOrdinal(tag);
    }
    catch (InvalidEnumException e)
    {
      if (tag.startsWith(ASHRAE_PREFIX))
        return Integer.parseInt(tag.substring(ASHRAE_PREFIX_LENGTH));
      if (tag.startsWith(PROPRIETARY_PREFIX))
        return Integer.parseInt(tag.substring(PROPRIETARY_PREFIX_LENGTH));
      throw e;
    }
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
