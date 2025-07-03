/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetLifeSafetyState represents the Bacnet Life Safety State
 * enumeration.
 * <p>
 * BBacnetLifeSafetyState is an "extensible" enumeration.
 * Values 0-255 are reserved for use by ASHRAE.
 * Values from 256-65535 (0x3FFFFF)
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
 * @version $Revision$ $Date$
 * @creation 16 May 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("quiet"),
    @Range("preAlarm"),
    @Range("alarm"),
    @Range("fault"),
    @Range("faultPreAlarm"),
    @Range("faultAlarm"),
    @Range("notReady"),
    @Range("active"),
    @Range("tamper"),
    @Range("testAlarm"),
    @Range("testActive"),
    @Range("testFault"),
    @Range("testFaultAlarm"),
    @Range("holdup"),
    @Range("duress"),
    @Range("tamperAlarm"),
    @Range("abnormal"),
    @Range("emergencyPower"),
    @Range("delayed"),
    @Range("blocked"),
    @Range("localAlarm"),
    @Range("generalAlarm"),
    @Range("supervisory"),
    @Range("testSupervisory")
  }
)
public final class BBacnetLifeSafetyState
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetLifeSafetyState(3473176751)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for quiet. */
  public static final int QUIET = 0;
  /** Ordinal value for preAlarm. */
  public static final int PRE_ALARM = 1;
  /** Ordinal value for alarm. */
  public static final int ALARM = 2;
  /** Ordinal value for fault. */
  public static final int FAULT = 3;
  /** Ordinal value for faultPreAlarm. */
  public static final int FAULT_PRE_ALARM = 4;
  /** Ordinal value for faultAlarm. */
  public static final int FAULT_ALARM = 5;
  /** Ordinal value for notReady. */
  public static final int NOT_READY = 6;
  /** Ordinal value for active. */
  public static final int ACTIVE = 7;
  /** Ordinal value for tamper. */
  public static final int TAMPER = 8;
  /** Ordinal value for testAlarm. */
  public static final int TEST_ALARM = 9;
  /** Ordinal value for testActive. */
  public static final int TEST_ACTIVE = 10;
  /** Ordinal value for testFault. */
  public static final int TEST_FAULT = 11;
  /** Ordinal value for testFaultAlarm. */
  public static final int TEST_FAULT_ALARM = 12;
  /** Ordinal value for holdup. */
  public static final int HOLDUP = 13;
  /** Ordinal value for duress. */
  public static final int DURESS = 14;
  /** Ordinal value for tamperAlarm. */
  public static final int TAMPER_ALARM = 15;
  /** Ordinal value for abnormal. */
  public static final int ABNORMAL = 16;
  /** Ordinal value for emergencyPower. */
  public static final int EMERGENCY_POWER = 17;
  /** Ordinal value for delayed. */
  public static final int DELAYED = 18;
  /** Ordinal value for blocked. */
  public static final int BLOCKED = 19;
  /** Ordinal value for localAlarm. */
  public static final int LOCAL_ALARM = 20;
  /** Ordinal value for generalAlarm. */
  public static final int GENERAL_ALARM = 21;
  /** Ordinal value for supervisory. */
  public static final int SUPERVISORY = 22;
  /** Ordinal value for testSupervisory. */
  public static final int TEST_SUPERVISORY = 23;

  /** BBacnetLifeSafetyState constant for quiet. */
  public static final BBacnetLifeSafetyState quiet = new BBacnetLifeSafetyState(QUIET);
  /** BBacnetLifeSafetyState constant for preAlarm. */
  public static final BBacnetLifeSafetyState preAlarm = new BBacnetLifeSafetyState(PRE_ALARM);
  /** BBacnetLifeSafetyState constant for alarm. */
  public static final BBacnetLifeSafetyState alarm = new BBacnetLifeSafetyState(ALARM);
  /** BBacnetLifeSafetyState constant for fault. */
  public static final BBacnetLifeSafetyState fault = new BBacnetLifeSafetyState(FAULT);
  /** BBacnetLifeSafetyState constant for faultPreAlarm. */
  public static final BBacnetLifeSafetyState faultPreAlarm = new BBacnetLifeSafetyState(FAULT_PRE_ALARM);
  /** BBacnetLifeSafetyState constant for faultAlarm. */
  public static final BBacnetLifeSafetyState faultAlarm = new BBacnetLifeSafetyState(FAULT_ALARM);
  /** BBacnetLifeSafetyState constant for notReady. */
  public static final BBacnetLifeSafetyState notReady = new BBacnetLifeSafetyState(NOT_READY);
  /** BBacnetLifeSafetyState constant for active. */
  public static final BBacnetLifeSafetyState active = new BBacnetLifeSafetyState(ACTIVE);
  /** BBacnetLifeSafetyState constant for tamper. */
  public static final BBacnetLifeSafetyState tamper = new BBacnetLifeSafetyState(TAMPER);
  /** BBacnetLifeSafetyState constant for testAlarm. */
  public static final BBacnetLifeSafetyState testAlarm = new BBacnetLifeSafetyState(TEST_ALARM);
  /** BBacnetLifeSafetyState constant for testActive. */
  public static final BBacnetLifeSafetyState testActive = new BBacnetLifeSafetyState(TEST_ACTIVE);
  /** BBacnetLifeSafetyState constant for testFault. */
  public static final BBacnetLifeSafetyState testFault = new BBacnetLifeSafetyState(TEST_FAULT);
  /** BBacnetLifeSafetyState constant for testFaultAlarm. */
  public static final BBacnetLifeSafetyState testFaultAlarm = new BBacnetLifeSafetyState(TEST_FAULT_ALARM);
  /** BBacnetLifeSafetyState constant for holdup. */
  public static final BBacnetLifeSafetyState holdup = new BBacnetLifeSafetyState(HOLDUP);
  /** BBacnetLifeSafetyState constant for duress. */
  public static final BBacnetLifeSafetyState duress = new BBacnetLifeSafetyState(DURESS);
  /** BBacnetLifeSafetyState constant for tamperAlarm. */
  public static final BBacnetLifeSafetyState tamperAlarm = new BBacnetLifeSafetyState(TAMPER_ALARM);
  /** BBacnetLifeSafetyState constant for abnormal. */
  public static final BBacnetLifeSafetyState abnormal = new BBacnetLifeSafetyState(ABNORMAL);
  /** BBacnetLifeSafetyState constant for emergencyPower. */
  public static final BBacnetLifeSafetyState emergencyPower = new BBacnetLifeSafetyState(EMERGENCY_POWER);
  /** BBacnetLifeSafetyState constant for delayed. */
  public static final BBacnetLifeSafetyState delayed = new BBacnetLifeSafetyState(DELAYED);
  /** BBacnetLifeSafetyState constant for blocked. */
  public static final BBacnetLifeSafetyState blocked = new BBacnetLifeSafetyState(BLOCKED);
  /** BBacnetLifeSafetyState constant for localAlarm. */
  public static final BBacnetLifeSafetyState localAlarm = new BBacnetLifeSafetyState(LOCAL_ALARM);
  /** BBacnetLifeSafetyState constant for generalAlarm. */
  public static final BBacnetLifeSafetyState generalAlarm = new BBacnetLifeSafetyState(GENERAL_ALARM);
  /** BBacnetLifeSafetyState constant for supervisory. */
  public static final BBacnetLifeSafetyState supervisory = new BBacnetLifeSafetyState(SUPERVISORY);
  /** BBacnetLifeSafetyState constant for testSupervisory. */
  public static final BBacnetLifeSafetyState testSupervisory = new BBacnetLifeSafetyState(TEST_SUPERVISORY);

  /** Factory method with ordinal. */
  public static BBacnetLifeSafetyState make(int ordinal)
  {
    return (BBacnetLifeSafetyState)quiet.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetLifeSafetyState make(String tag)
  {
    return (BBacnetLifeSafetyState)quiet.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetLifeSafetyState(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetLifeSafetyState DEFAULT = quiet;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLifeSafetyState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 23;
  public static final int MAX_RESERVED_ID = 255;
  public static final int MAX_ID = 65535;

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
