/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.nre.util.IntHashMap;
import javax.baja.sys.*;

/**
 * BBacnetObjectType represents the BACnetObjectType
 * enumeration.
 * <p>
 * BBacnetObjectType is an "extensible" enumeration.
 * Values 0-127 are reserved for used by ASHRAE.
 * Values from 128-1023 (0x3FF)
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
 * @version $Revision: 7$ $Date: 12/19/01 4:35:59 PM$
 * @creation 21 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("analogInput"),
    @Range("analogOutput"),
    @Range("analogValue"),
    @Range("binaryInput"),
    @Range("binaryOutput"),
    @Range("binaryValue"),
    @Range("calendar"),
    @Range("command"),
    @Range("device"),
    @Range("eventEnrollment"),
    @Range("file"),
    @Range("group"),
    @Range("loop"),
    @Range("multiStateInput"),
    @Range("multiStateOutput"),
    @Range("notificationClass"),
    @Range("program"),
    @Range("schedule"),
    @Range("averaging"),
    @Range("multiStateValue"),
    @Range("trendLog"),
    @Range("lifeSafetyPoint"),
    @Range("lifeSafetyZone"),
    @Range("accumulator"),
    @Range("pulseConverter"),
    @Range("eventLog"),
    @Range("globalGroup"),
    @Range("trendLogMultiple"),
    @Range("loadControl"),
    @Range("structuredView"),
    @Range("accessDoor"),
    @Range("unassigned31"),
    @Range("accessCredential"),
    @Range("accessPoint"),
    @Range("accessRights"),
    @Range("accessUser"),
    @Range("accessZone"),
    @Range("credentialDataInput"),
    @Range("networkSecurity"),
    @Range("bitstringValue"),
    @Range("characterStringValue"),
    @Range("datePatternValue"),
    @Range("dateValue"),
    @Range("dateTimePatternValue"),
    @Range("dateTimeValue"),
    @Range("integerValue"),
    @Range("largeAnalogValue"),
    @Range("octetStringValue"),
    @Range("positiveIntegerValue"),
    @Range("timePatternValue"),
    @Range("timeValue"),
    @Range("notificationForwarder"),
    @Range("alertEnrollment"),
    @Range("channel"),
    @Range("lightingOutput")
  }
)
public final class BBacnetObjectType
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetObjectType(171880877)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for analogInput. */
  public static final int ANALOG_INPUT = 0;
  /** Ordinal value for analogOutput. */
  public static final int ANALOG_OUTPUT = 1;
  /** Ordinal value for analogValue. */
  public static final int ANALOG_VALUE = 2;
  /** Ordinal value for binaryInput. */
  public static final int BINARY_INPUT = 3;
  /** Ordinal value for binaryOutput. */
  public static final int BINARY_OUTPUT = 4;
  /** Ordinal value for binaryValue. */
  public static final int BINARY_VALUE = 5;
  /** Ordinal value for calendar. */
  public static final int CALENDAR = 6;
  /** Ordinal value for command. */
  public static final int COMMAND = 7;
  /** Ordinal value for device. */
  public static final int DEVICE = 8;
  /** Ordinal value for eventEnrollment. */
  public static final int EVENT_ENROLLMENT = 9;
  /** Ordinal value for file. */
  public static final int FILE = 10;
  /** Ordinal value for group. */
  public static final int GROUP = 11;
  /** Ordinal value for loop. */
  public static final int LOOP = 12;
  /** Ordinal value for multiStateInput. */
  public static final int MULTI_STATE_INPUT = 13;
  /** Ordinal value for multiStateOutput. */
  public static final int MULTI_STATE_OUTPUT = 14;
  /** Ordinal value for notificationClass. */
  public static final int NOTIFICATION_CLASS = 15;
  /** Ordinal value for program. */
  public static final int PROGRAM = 16;
  /** Ordinal value for schedule. */
  public static final int SCHEDULE = 17;
  /** Ordinal value for averaging. */
  public static final int AVERAGING = 18;
  /** Ordinal value for multiStateValue. */
  public static final int MULTI_STATE_VALUE = 19;
  /** Ordinal value for trendLog. */
  public static final int TREND_LOG = 20;
  /** Ordinal value for lifeSafetyPoint. */
  public static final int LIFE_SAFETY_POINT = 21;
  /** Ordinal value for lifeSafetyZone. */
  public static final int LIFE_SAFETY_ZONE = 22;
  /** Ordinal value for accumulator. */
  public static final int ACCUMULATOR = 23;
  /** Ordinal value for pulseConverter. */
  public static final int PULSE_CONVERTER = 24;
  /** Ordinal value for eventLog. */
  public static final int EVENT_LOG = 25;
  /** Ordinal value for globalGroup. */
  public static final int GLOBAL_GROUP = 26;
  /** Ordinal value for trendLogMultiple. */
  public static final int TREND_LOG_MULTIPLE = 27;
  /** Ordinal value for loadControl. */
  public static final int LOAD_CONTROL = 28;
  /** Ordinal value for structuredView. */
  public static final int STRUCTURED_VIEW = 29;
  /** Ordinal value for accessDoor. */
  public static final int ACCESS_DOOR = 30;
  /** Ordinal value for unassigned31. */
  public static final int UNASSIGNED_31 = 31;
  /** Ordinal value for accessCredential. */
  public static final int ACCESS_CREDENTIAL = 32;
  /** Ordinal value for accessPoint. */
  public static final int ACCESS_POINT = 33;
  /** Ordinal value for accessRights. */
  public static final int ACCESS_RIGHTS = 34;
  /** Ordinal value for accessUser. */
  public static final int ACCESS_USER = 35;
  /** Ordinal value for accessZone. */
  public static final int ACCESS_ZONE = 36;
  /** Ordinal value for credentialDataInput. */
  public static final int CREDENTIAL_DATA_INPUT = 37;
  /** Ordinal value for networkSecurity. */
  public static final int NETWORK_SECURITY = 38;
  /** Ordinal value for bitstringValue. */
  public static final int BITSTRING_VALUE = 39;
  /** Ordinal value for characterStringValue. */
  public static final int CHARACTER_STRING_VALUE = 40;
  /** Ordinal value for datePatternValue. */
  public static final int DATE_PATTERN_VALUE = 41;
  /** Ordinal value for dateValue. */
  public static final int DATE_VALUE = 42;
  /** Ordinal value for dateTimePatternValue. */
  public static final int DATE_TIME_PATTERN_VALUE = 43;
  /** Ordinal value for dateTimeValue. */
  public static final int DATE_TIME_VALUE = 44;
  /** Ordinal value for integerValue. */
  public static final int INTEGER_VALUE = 45;
  /** Ordinal value for largeAnalogValue. */
  public static final int LARGE_ANALOG_VALUE = 46;
  /** Ordinal value for octetStringValue. */
  public static final int OCTET_STRING_VALUE = 47;
  /** Ordinal value for positiveIntegerValue. */
  public static final int POSITIVE_INTEGER_VALUE = 48;
  /** Ordinal value for timePatternValue. */
  public static final int TIME_PATTERN_VALUE = 49;
  /** Ordinal value for timeValue. */
  public static final int TIME_VALUE = 50;
  /** Ordinal value for notificationForwarder. */
  public static final int NOTIFICATION_FORWARDER = 51;
  /** Ordinal value for alertEnrollment. */
  public static final int ALERT_ENROLLMENT = 52;
  /** Ordinal value for channel. */
  public static final int CHANNEL = 53;
  /** Ordinal value for lightingOutput. */
  public static final int LIGHTING_OUTPUT = 54;

  /** BBacnetObjectType constant for analogInput. */
  public static final BBacnetObjectType analogInput = new BBacnetObjectType(ANALOG_INPUT);
  /** BBacnetObjectType constant for analogOutput. */
  public static final BBacnetObjectType analogOutput = new BBacnetObjectType(ANALOG_OUTPUT);
  /** BBacnetObjectType constant for analogValue. */
  public static final BBacnetObjectType analogValue = new BBacnetObjectType(ANALOG_VALUE);
  /** BBacnetObjectType constant for binaryInput. */
  public static final BBacnetObjectType binaryInput = new BBacnetObjectType(BINARY_INPUT);
  /** BBacnetObjectType constant for binaryOutput. */
  public static final BBacnetObjectType binaryOutput = new BBacnetObjectType(BINARY_OUTPUT);
  /** BBacnetObjectType constant for binaryValue. */
  public static final BBacnetObjectType binaryValue = new BBacnetObjectType(BINARY_VALUE);
  /** BBacnetObjectType constant for calendar. */
  public static final BBacnetObjectType calendar = new BBacnetObjectType(CALENDAR);
  /** BBacnetObjectType constant for command. */
  public static final BBacnetObjectType command = new BBacnetObjectType(COMMAND);
  /** BBacnetObjectType constant for device. */
  public static final BBacnetObjectType device = new BBacnetObjectType(DEVICE);
  /** BBacnetObjectType constant for eventEnrollment. */
  public static final BBacnetObjectType eventEnrollment = new BBacnetObjectType(EVENT_ENROLLMENT);
  /** BBacnetObjectType constant for file. */
  public static final BBacnetObjectType file = new BBacnetObjectType(FILE);
  /** BBacnetObjectType constant for group. */
  public static final BBacnetObjectType group = new BBacnetObjectType(GROUP);
  /** BBacnetObjectType constant for loop. */
  public static final BBacnetObjectType loop = new BBacnetObjectType(LOOP);
  /** BBacnetObjectType constant for multiStateInput. */
  public static final BBacnetObjectType multiStateInput = new BBacnetObjectType(MULTI_STATE_INPUT);
  /** BBacnetObjectType constant for multiStateOutput. */
  public static final BBacnetObjectType multiStateOutput = new BBacnetObjectType(MULTI_STATE_OUTPUT);
  /** BBacnetObjectType constant for notificationClass. */
  public static final BBacnetObjectType notificationClass = new BBacnetObjectType(NOTIFICATION_CLASS);
  /** BBacnetObjectType constant for program. */
  public static final BBacnetObjectType program = new BBacnetObjectType(PROGRAM);
  /** BBacnetObjectType constant for schedule. */
  public static final BBacnetObjectType schedule = new BBacnetObjectType(SCHEDULE);
  /** BBacnetObjectType constant for averaging. */
  public static final BBacnetObjectType averaging = new BBacnetObjectType(AVERAGING);
  /** BBacnetObjectType constant for multiStateValue. */
  public static final BBacnetObjectType multiStateValue = new BBacnetObjectType(MULTI_STATE_VALUE);
  /** BBacnetObjectType constant for trendLog. */
  public static final BBacnetObjectType trendLog = new BBacnetObjectType(TREND_LOG);
  /** BBacnetObjectType constant for lifeSafetyPoint. */
  public static final BBacnetObjectType lifeSafetyPoint = new BBacnetObjectType(LIFE_SAFETY_POINT);
  /** BBacnetObjectType constant for lifeSafetyZone. */
  public static final BBacnetObjectType lifeSafetyZone = new BBacnetObjectType(LIFE_SAFETY_ZONE);
  /** BBacnetObjectType constant for accumulator. */
  public static final BBacnetObjectType accumulator = new BBacnetObjectType(ACCUMULATOR);
  /** BBacnetObjectType constant for pulseConverter. */
  public static final BBacnetObjectType pulseConverter = new BBacnetObjectType(PULSE_CONVERTER);
  /** BBacnetObjectType constant for eventLog. */
  public static final BBacnetObjectType eventLog = new BBacnetObjectType(EVENT_LOG);
  /** BBacnetObjectType constant for globalGroup. */
  public static final BBacnetObjectType globalGroup = new BBacnetObjectType(GLOBAL_GROUP);
  /** BBacnetObjectType constant for trendLogMultiple. */
  public static final BBacnetObjectType trendLogMultiple = new BBacnetObjectType(TREND_LOG_MULTIPLE);
  /** BBacnetObjectType constant for loadControl. */
  public static final BBacnetObjectType loadControl = new BBacnetObjectType(LOAD_CONTROL);
  /** BBacnetObjectType constant for structuredView. */
  public static final BBacnetObjectType structuredView = new BBacnetObjectType(STRUCTURED_VIEW);
  /** BBacnetObjectType constant for accessDoor. */
  public static final BBacnetObjectType accessDoor = new BBacnetObjectType(ACCESS_DOOR);
  /** BBacnetObjectType constant for unassigned31. */
  public static final BBacnetObjectType unassigned31 = new BBacnetObjectType(UNASSIGNED_31);
  /** BBacnetObjectType constant for accessCredential. */
  public static final BBacnetObjectType accessCredential = new BBacnetObjectType(ACCESS_CREDENTIAL);
  /** BBacnetObjectType constant for accessPoint. */
  public static final BBacnetObjectType accessPoint = new BBacnetObjectType(ACCESS_POINT);
  /** BBacnetObjectType constant for accessRights. */
  public static final BBacnetObjectType accessRights = new BBacnetObjectType(ACCESS_RIGHTS);
  /** BBacnetObjectType constant for accessUser. */
  public static final BBacnetObjectType accessUser = new BBacnetObjectType(ACCESS_USER);
  /** BBacnetObjectType constant for accessZone. */
  public static final BBacnetObjectType accessZone = new BBacnetObjectType(ACCESS_ZONE);
  /** BBacnetObjectType constant for credentialDataInput. */
  public static final BBacnetObjectType credentialDataInput = new BBacnetObjectType(CREDENTIAL_DATA_INPUT);
  /** BBacnetObjectType constant for networkSecurity. */
  public static final BBacnetObjectType networkSecurity = new BBacnetObjectType(NETWORK_SECURITY);
  /** BBacnetObjectType constant for bitstringValue. */
  public static final BBacnetObjectType bitstringValue = new BBacnetObjectType(BITSTRING_VALUE);
  /** BBacnetObjectType constant for characterStringValue. */
  public static final BBacnetObjectType characterStringValue = new BBacnetObjectType(CHARACTER_STRING_VALUE);
  /** BBacnetObjectType constant for datePatternValue. */
  public static final BBacnetObjectType datePatternValue = new BBacnetObjectType(DATE_PATTERN_VALUE);
  /** BBacnetObjectType constant for dateValue. */
  public static final BBacnetObjectType dateValue = new BBacnetObjectType(DATE_VALUE);
  /** BBacnetObjectType constant for dateTimePatternValue. */
  public static final BBacnetObjectType dateTimePatternValue = new BBacnetObjectType(DATE_TIME_PATTERN_VALUE);
  /** BBacnetObjectType constant for dateTimeValue. */
  public static final BBacnetObjectType dateTimeValue = new BBacnetObjectType(DATE_TIME_VALUE);
  /** BBacnetObjectType constant for integerValue. */
  public static final BBacnetObjectType integerValue = new BBacnetObjectType(INTEGER_VALUE);
  /** BBacnetObjectType constant for largeAnalogValue. */
  public static final BBacnetObjectType largeAnalogValue = new BBacnetObjectType(LARGE_ANALOG_VALUE);
  /** BBacnetObjectType constant for octetStringValue. */
  public static final BBacnetObjectType octetStringValue = new BBacnetObjectType(OCTET_STRING_VALUE);
  /** BBacnetObjectType constant for positiveIntegerValue. */
  public static final BBacnetObjectType positiveIntegerValue = new BBacnetObjectType(POSITIVE_INTEGER_VALUE);
  /** BBacnetObjectType constant for timePatternValue. */
  public static final BBacnetObjectType timePatternValue = new BBacnetObjectType(TIME_PATTERN_VALUE);
  /** BBacnetObjectType constant for timeValue. */
  public static final BBacnetObjectType timeValue = new BBacnetObjectType(TIME_VALUE);
  /** BBacnetObjectType constant for notificationForwarder. */
  public static final BBacnetObjectType notificationForwarder = new BBacnetObjectType(NOTIFICATION_FORWARDER);
  /** BBacnetObjectType constant for alertEnrollment. */
  public static final BBacnetObjectType alertEnrollment = new BBacnetObjectType(ALERT_ENROLLMENT);
  /** BBacnetObjectType constant for channel. */
  public static final BBacnetObjectType channel = new BBacnetObjectType(CHANNEL);
  /** BBacnetObjectType constant for lightingOutput. */
  public static final BBacnetObjectType lightingOutput = new BBacnetObjectType(LIGHTING_OUTPUT);

  /** Factory method with ordinal. */
  public static BBacnetObjectType make(int ordinal)
  {
    return (BBacnetObjectType)analogInput.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetObjectType make(String tag)
  {
    return (BBacnetObjectType)analogInput.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetObjectType(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetObjectType DEFAULT = analogInput;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetObjectType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = ALERT_ENROLLMENT;
  public static final int MAX_RESERVED_ID = 127;
  public static final int MAX_ID = 1023;

  private static final String INVALID_OBJECT_TYPE = "INVALID";

////////////////////////////////////////////////////////////////
// Status Flags Constants
////////////////////////////////////////////////////////////////

  private static int[] sfPr0 = new int[] {
    BBacnetObjectType.LIFE_SAFETY_ZONE,
    BBacnetObjectType.LIFE_SAFETY_POINT,
    BBacnetObjectType.MULTI_STATE_VALUE,
    BBacnetObjectType.PROGRAM,
    BBacnetObjectType.MULTI_STATE_OUTPUT,
    BBacnetObjectType.MULTI_STATE_INPUT,
    BBacnetObjectType.LOOP,
    BBacnetObjectType.BINARY_VALUE,
    BBacnetObjectType.BINARY_OUTPUT,
    BBacnetObjectType.BINARY_INPUT,
    BBacnetObjectType.ANALOG_VALUE,
    BBacnetObjectType.ANALOG_OUTPUT,
    BBacnetObjectType.ANALOG_INPUT,
  };

  private static int[] sfPr4 = new int[] {
    BBacnetObjectType.PULSE_CONVERTER,
    BBacnetObjectType.ACCUMULATOR,
    BBacnetObjectType.SCHEDULE,
  };

  private static int[] sfPr6 = new int[] {
    BBacnetObjectType.ACCESS_DOOR,
    BBacnetObjectType.LOAD_CONTROL,
  };

  private static int[] sfPr7 = new int[] {
    BBacnetObjectType.LOAD_CONTROL,
    BBacnetObjectType.TREND_LOG_MULTIPLE,
    BBacnetObjectType.EVENT_LOG,
    BBacnetObjectType.TREND_LOG,
  };

  private static int[] pr10 = new int[] {
    BBacnetObjectType.LARGE_ANALOG_VALUE,
    BBacnetObjectType.INTEGER_VALUE,
    BBacnetObjectType.POSITIVE_INTEGER_VALUE,
    BBacnetObjectType.CHARACTER_STRING_VALUE
  };

  private static long statusFlagsObjectTypes = bitmask(sfPr0);
  private static long statusFlagsObjectTypesPR4 = bitmask(sfPr4) | statusFlagsObjectTypes;
  private static long statusFlagsObjectTypesPR6 = bitmask(sfPr6) | statusFlagsObjectTypesPR4;
  private static long statusFlagsObjectTypesPR7 = bitmask(sfPr7) | statusFlagsObjectTypesPR6;
  private static long statusFlagsObjectTypesPR10 = bitmask(pr10) | statusFlagsObjectTypesPR7;

////////////////////////////////////////////////////////////////
// Cov Constants
////////////////////////////////////////////////////////////////


  private static int[] covPr0 = new int[] {
    BBacnetObjectType.LIFE_SAFETY_ZONE,
    BBacnetObjectType.LIFE_SAFETY_POINT,
    BBacnetObjectType.MULTI_STATE_VALUE,
    BBacnetObjectType.MULTI_STATE_OUTPUT,
    BBacnetObjectType.MULTI_STATE_INPUT,
    BBacnetObjectType.LOOP,
    BBacnetObjectType.BINARY_VALUE,
    BBacnetObjectType.BINARY_OUTPUT,
    BBacnetObjectType.BINARY_INPUT,
    BBacnetObjectType.ANALOG_VALUE,
    BBacnetObjectType.ANALOG_OUTPUT,
    BBacnetObjectType.ANALOG_INPUT,
  };

  private static int[] covPr4 = new int[] {
    BBacnetObjectType.PULSE_CONVERTER
  };

  private static int[] covPr6 = new int[] {
    BBacnetObjectType.ACCESS_DOOR,
    BBacnetObjectType.LOAD_CONTROL,
  };

  private static long covObjectTypes = bitmask(covPr0);
  private static long covObjectTypesPR4 = bitmask(covPr4) | covObjectTypes;
  private static long covObjectTypesPR6 = bitmask(covPr6) | covObjectTypesPR4;
  private static long covObjectTypesPR10 = bitmask(pr10) | covObjectTypesPR6;

////////////////////////////////////////////////////////////////
// Static methods
////////////////////////////////////////////////////////////////

  /**
   * Create a string tag for the given ordinal.
   *
   * @param id the integer enumeration value.
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
    return INVALID_OBJECT_TYPE + ":" + id;
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
      if (tag.startsWith(INVALID_OBJECT_TYPE))
        return -1;
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

  /**
   * Utility to determine if this is one of the object types
   * with status flags.
   *
   * @param ordinal object type
   * @return true if this object type has status flags.
   * @deprecated Since 3.5 - use <code>hasStatusFlags(int, BBacnetDevice)</code>
   */
  @Deprecated
  public static final boolean hasStatusFlags(int ordinal)
  {
    return hasStatusFlags(ordinal, null);
  }

  /**
   * Utility to determine if this is one of the object types
   * with status flags.
   *
   * @param ordinal object type
   * @param device  the device in which the object resides
   * @return true if this object type has status flags.
   */
  public static final boolean hasStatusFlags(int ordinal, BBacnetDevice device)
  {
    int pr = 0;
    if (device != null)
      pr = device.getProtocolRevision();

    return hasStatusFlags(ordinal, pr);
  }

  /**
   * Utility to determine if this is one of the object types
   * with status flags.
   *
   * @param ordinal object type
   * @param pr      of the device in which the object resides
   * @return true if this object type has status flags.
   */
  public static final boolean hasStatusFlags(int ordinal, int pr)
  {
    if (ordinal > MAX_ASHRAE_ID) return false;
    long chk = 1L << ordinal;
    if (pr > 9)
      return (chk & statusFlagsObjectTypesPR10) != 0;
    else if (pr > 6)
      return (chk & statusFlagsObjectTypesPR7) != 0;
    else if (pr > 5)
      return (chk & statusFlagsObjectTypesPR6) != 0;
    else if (pr > 3)
      return (chk & statusFlagsObjectTypesPR4) != 0;
    else
      return (chk & statusFlagsObjectTypes) != 0;
  }

  /**
   * Utility to determine if this is one of the object types
   * that may support Cov reporting (not Cov-Property).
   *
   * @param ordinal object type
   * @return true if this object type may support Cov reporting.
   * @deprecated Since 3.5 - use <code>canSupportCov(int, BBacnetDevice)</code>
   */
  @Deprecated
  public static final boolean canSupportCov(int ordinal)
  {
    return canSupportCov(ordinal, null);
  }

  /**
   * Utility to determine if this is one of the object types
   * that may support Cov reporting (not Cov-Property).
   *
   * @param ordinal object type
   * @return true if this object type may support Cov reporting.
   */
  public static final boolean canSupportCov(int ordinal, BBacnetDevice device)
  {
    int pr = 0;
    if (device != null)
      pr = device.getProtocolRevision();

    return canSupportCov(ordinal, pr);
  }

  public static final boolean canSupportCov(int ordinal, int pr)
  {
    if (ordinal > MAX_ASHRAE_ID) return false;
    long chk = 1L << ordinal;
    if (pr > 9)
      return (chk & covObjectTypesPR10) != 0;
    else if (pr > 5)
      return (chk & covObjectTypesPR6) != 0;
    else if (pr > 3)
      return (chk & covObjectTypesPR4) != 0;
    else
      return (chk & covObjectTypes) != 0;
  }

  /**
   * Build a long bit mask for the object ids whose ordinal
   * values are specified in the bits arguments.
   *
   * @param bits an array of integers whose bits should be set
   * @return the long mask representing the input ordinal values
   */
  public static long bitmask(int... bits)
  {
    long mask = 0;
    for (int i = 0; i < bits.length; i++)
    {
      mask |= 1L << bits[i];
    }
    return mask;
  }

////////////////////////////////////////////////////////////////
// Short tag
////////////////////////////////////////////////////////////////

  /**
   * Return a short form of the tag
   */
  public static String getShortTag(int type)
  {
    String s = (String)stagByOType.get(type);
    if (s != null) return s;
    return type + ":";
  }

  private static IntHashMap stagByOType = new IntHashMap();

  static
  {
    stagByOType.put(ANALOG_INPUT, "AI");
    stagByOType.put(ANALOG_OUTPUT, "AO");
    stagByOType.put(ANALOG_VALUE, "AV");
    stagByOType.put(BINARY_INPUT, "BI");
    stagByOType.put(BINARY_OUTPUT, "BO");
    stagByOType.put(BINARY_VALUE, "BV");
    stagByOType.put(CALENDAR, "CAL");
    stagByOType.put(COMMAND, "CMD");
    stagByOType.put(DEVICE, "DEV");
    stagByOType.put(EVENT_ENROLLMENT, "EE");
    stagByOType.put(FILE, "FILE");
    stagByOType.put(GROUP, "GRP");
    stagByOType.put(LOOP, "LP");
    stagByOType.put(MULTI_STATE_INPUT, "MSI");
    stagByOType.put(MULTI_STATE_OUTPUT, "MSO");
    stagByOType.put(NOTIFICATION_CLASS, "NC");
    stagByOType.put(PROGRAM, "PGM");
    stagByOType.put(SCHEDULE, "SCH");
    stagByOType.put(AVERAGING, "AVG");
    stagByOType.put(MULTI_STATE_VALUE, "MSV");
    stagByOType.put(TREND_LOG, "LOG");
    stagByOType.put(LIFE_SAFETY_POINT, "LSP");
    stagByOType.put(LIFE_SAFETY_ZONE, "LSZ");
    stagByOType.put(ACCUMULATOR, "ACC");
    stagByOType.put(PULSE_CONVERTER, "PC");
    stagByOType.put(EVENT_LOG, "ELOG");
    stagByOType.put(TREND_LOG_MULTIPLE, "TLM");
    stagByOType.put(LOAD_CONTROL, "LCO");
    stagByOType.put(STRUCTURED_VIEW, "SVO");
    stagByOType.put(ACCESS_DOOR, "DOOR");
    stagByOType.put(CHARACTER_STRING_VALUE, "CSV");
    stagByOType.put(LARGE_ANALOG_VALUE, "LAV");
    stagByOType.put(INTEGER_VALUE, "INT");
    stagByOType.put(POSITIVE_INTEGER_VALUE, "PINT");

  }

  // not needed yet
//  public static int fromShortTag(String tag)
//  {
//    if (tag.equals("AI"))   return ANALOG_INPUT;
//    if (tag.equals("AO"))   return ANALOG_OUTPUT;
//    if (tag.equals("AV"))   return ANALOG_VALUE;
//    if (tag.equals("BI"))   return BINARY_INPUT;
//    if (tag.equals("BO"))   return BINARY_OUTPUT;
//    if (tag.equals("BV"))   return BINARY_VALUE;
//    if (tag.equals("CAL"))  return CALENDAR;
//    if (tag.equals("CMD"))  return COMMAND;
//    if (tag.equals("DEV"))  return DEVICE;
//    if (tag.equals("EE"))   return EVENT_ENROLLMENT;
//    if (tag.equals("FILE")) return FILE;
//    if (tag.equals("GRP"))  return GROUP;
//    if (tag.equals("LP"))   return LOOP;
//    if (tag.equals("MSI"))  return MULTI_STATE_INPUT;
//    if (tag.equals("MSO"))  return MULTI_STATE_OUTPUT;
//    if (tag.equals("NC"))   return NOTIFICATION_CLASS;
//    if (tag.equals("PGM"))  return PROGRAM;
//    if (tag.equals("SCH"))  return SCHEDULE;
//    if (tag.equals("AVG"))  return AVERAGING;
//    if (tag.equals("MSV"))  return MULTI_STATE_VALUE;
//    if (tag.equals("LOG"))  return TREND_LOG;
//    if (tag.equals("LSP"))  return LIFE_SAFETY_POINT;
//    if (tag.equals("LSZ"))  return LIFE_SAFETY_ZONE;
//    if (tag.equals("ACC"))  return ACCUMULATOR;
//    if (tag.equals("PC"))   return PULSE_CONVERTER;
//    int colon = tag.indexOf(":");
//    if (colon >= 0) return Integer.parseInt(tag.substring(0, colon));
//    throw new IllegalArgumentException("Invalid Short Tag:"+tag);
//  }


/////////////////////////////////////////////////////////////////
//  Object ID Facets instances
/////////////////////////////////////////////////////////////////

  private static IntHashMap objectIdFacets = new IntHashMap();

  public static BFacets getObjectIdFacets(int objectType)
  {
    return (BFacets)objectIdFacets.get(objectType);
  }

  static
  {
    objectIdFacets.put(ANALOG_INPUT, BFacets.makeEnum(BEnumRange.make(new int[] { ANALOG_INPUT }, new String[] { analogInput.getTag() })));
    objectIdFacets.put(ANALOG_OUTPUT, BFacets.makeEnum(BEnumRange.make(new int[] { ANALOG_OUTPUT }, new String[] { analogOutput.getTag() })));
    objectIdFacets.put(ANALOG_VALUE, BFacets.makeEnum(BEnumRange.make(new int[] { ANALOG_VALUE }, new String[] { analogValue.getTag() })));
    objectIdFacets.put(BINARY_INPUT, BFacets.makeEnum(BEnumRange.make(new int[] { BINARY_INPUT }, new String[] { binaryInput.getTag() })));
    objectIdFacets.put(BINARY_OUTPUT, BFacets.makeEnum(BEnumRange.make(new int[] { BINARY_OUTPUT }, new String[] { binaryOutput.getTag() })));
    objectIdFacets.put(BINARY_VALUE, BFacets.makeEnum(BEnumRange.make(new int[] { BINARY_VALUE }, new String[] { binaryValue.getTag() })));
    objectIdFacets.put(CALENDAR, BFacets.makeEnum(BEnumRange.make(new int[] { CALENDAR }, new String[] { calendar.getTag() })));
    objectIdFacets.put(COMMAND, BFacets.makeEnum(BEnumRange.make(new int[] { COMMAND }, new String[] { command.getTag() })));
    objectIdFacets.put(DEVICE, BFacets.makeEnum(BEnumRange.make(new int[] { DEVICE }, new String[] { device.getTag() })));
    objectIdFacets.put(EVENT_ENROLLMENT, BFacets.makeEnum(BEnumRange.make(new int[] { EVENT_ENROLLMENT }, new String[] { eventEnrollment.getTag() })));
    objectIdFacets.put(FILE, BFacets.makeEnum(BEnumRange.make(new int[] { FILE }, new String[] { file.getTag() })));
    objectIdFacets.put(GROUP, BFacets.makeEnum(BEnumRange.make(new int[] { GROUP }, new String[] { group.getTag() })));
    objectIdFacets.put(LOOP, BFacets.makeEnum(BEnumRange.make(new int[] { LOOP }, new String[] { loop.getTag() })));
    objectIdFacets.put(MULTI_STATE_INPUT, BFacets.makeEnum(BEnumRange.make(new int[] { MULTI_STATE_INPUT }, new String[] { multiStateInput.getTag() })));
    objectIdFacets.put(MULTI_STATE_OUTPUT, BFacets.makeEnum(BEnumRange.make(new int[] { MULTI_STATE_OUTPUT }, new String[] { multiStateOutput.getTag() })));
    objectIdFacets.put(NOTIFICATION_CLASS, BFacets.makeEnum(BEnumRange.make(new int[] { NOTIFICATION_CLASS }, new String[] { notificationClass.getTag() })));
    objectIdFacets.put(PROGRAM, BFacets.makeEnum(BEnumRange.make(new int[] { PROGRAM }, new String[] { program.getTag() })));
    objectIdFacets.put(SCHEDULE, BFacets.makeEnum(BEnumRange.make(new int[] { SCHEDULE }, new String[] { schedule.getTag() })));
    objectIdFacets.put(AVERAGING, BFacets.makeEnum(BEnumRange.make(new int[] { AVERAGING }, new String[] { averaging.getTag() })));
    objectIdFacets.put(MULTI_STATE_VALUE, BFacets.makeEnum(BEnumRange.make(new int[] { MULTI_STATE_VALUE }, new String[] { multiStateValue.getTag() })));
    objectIdFacets.put(TREND_LOG, BFacets.makeEnum(BEnumRange.make(new int[] { TREND_LOG }, new String[] { trendLog.getTag() })));
    objectIdFacets.put(LIFE_SAFETY_POINT, BFacets.makeEnum(BEnumRange.make(new int[] { LIFE_SAFETY_POINT }, new String[] { lifeSafetyPoint.getTag() })));
    objectIdFacets.put(LIFE_SAFETY_ZONE, BFacets.makeEnum(BEnumRange.make(new int[] { LIFE_SAFETY_ZONE }, new String[] { lifeSafetyZone.getTag() })));
    objectIdFacets.put(ACCUMULATOR, BFacets.makeEnum(BEnumRange.make(new int[] { ACCUMULATOR }, new String[] { accumulator.getTag() })));
    objectIdFacets.put(PULSE_CONVERTER, BFacets.makeEnum(BEnumRange.make(new int[] { PULSE_CONVERTER }, new String[] { pulseConverter.getTag() })));
    objectIdFacets.put(LARGE_ANALOG_VALUE, BFacets.makeEnum(BEnumRange.make(new int[] { LARGE_ANALOG_VALUE }, new String[] { largeAnalogValue.getTag() })));
    objectIdFacets.put(INTEGER_VALUE, BFacets.makeEnum(BEnumRange.make(new int[] { INTEGER_VALUE }, new String[] { integerValue.getTag() })));
    objectIdFacets.put(POSITIVE_INTEGER_VALUE, BFacets.makeEnum(BEnumRange.make(new int[] { POSITIVE_INTEGER_VALUE }, new String[] { positiveIntegerValue.getTag() })));
    objectIdFacets.put(CHARACTER_STRING_VALUE, BFacets.makeEnum(BEnumRange.make(new int[] { CHARACTER_STRING_VALUE }, new String[] { characterStringValue.getTag() })));
  }
}
