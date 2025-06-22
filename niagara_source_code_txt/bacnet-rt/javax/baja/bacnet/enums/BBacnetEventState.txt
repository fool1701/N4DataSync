/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.alarm.BSourceState;
import javax.baja.alarm.ext.BAlarmState;
import javax.baja.bacnet.BacnetAlarmConst;
import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetEventState represents the Bacnet Event State
 * enumeration.
 * <p>
 * BBacnetEventState is an "extensible" enumeration.
 * Values 0-63 are reserved for use by ASHRAE.
 * Values from 64-65535 (0x3FFFFF)
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
 * @version $Revision: 5$ $Date: 12/19/01 4:35:58 PM$
 * @creation 07 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("normal"),
    @Range("fault"),
    @Range("offnormal"),
    @Range("highLimit"),
    @Range("lowLimit"),
    @Range("lifeSafetyAlarm")
  }
)
public final class BBacnetEventState
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetEventState(346656574)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for normal. */
  public static final int NORMAL = 0;
  /** Ordinal value for fault. */
  public static final int FAULT = 1;
  /** Ordinal value for offnormal. */
  public static final int OFFNORMAL = 2;
  /** Ordinal value for highLimit. */
  public static final int HIGH_LIMIT = 3;
  /** Ordinal value for lowLimit. */
  public static final int LOW_LIMIT = 4;
  /** Ordinal value for lifeSafetyAlarm. */
  public static final int LIFE_SAFETY_ALARM = 5;

  /** BBacnetEventState constant for normal. */
  public static final BBacnetEventState normal = new BBacnetEventState(NORMAL);
  /** BBacnetEventState constant for fault. */
  public static final BBacnetEventState fault = new BBacnetEventState(FAULT);
  /** BBacnetEventState constant for offnormal. */
  public static final BBacnetEventState offnormal = new BBacnetEventState(OFFNORMAL);
  /** BBacnetEventState constant for highLimit. */
  public static final BBacnetEventState highLimit = new BBacnetEventState(HIGH_LIMIT);
  /** BBacnetEventState constant for lowLimit. */
  public static final BBacnetEventState lowLimit = new BBacnetEventState(LOW_LIMIT);
  /** BBacnetEventState constant for lifeSafetyAlarm. */
  public static final BBacnetEventState lifeSafetyAlarm = new BBacnetEventState(LIFE_SAFETY_ALARM);

  /** Factory method with ordinal. */
  public static BBacnetEventState make(int ordinal)
  {
    return (BBacnetEventState)normal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetEventState make(String tag)
  {
    return (BBacnetEventState)normal.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetEventState(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetEventState DEFAULT = normal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEventState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = LIFE_SAFETY_ALARM;
  public static final int MAX_RESERVED_ID = 63;
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

  /**
   * Generate the correct ordinal mapping from BAlarmState
   * to BBacnetEventState.
   * <p>
   * The mapping is exact at this point.
   *
   * @param alarmState the BAlarmState.
   * @return the ordinal representing the corresponding BBacnetEventState.
   */
  public static BBacnetEventState make(BAlarmState alarmState)
  {
    return BBacnetEventState.make(alarmState.getOrdinal());
  }

  /**
   * Generate the correct ordinal mapping from BSourceState
   * to BBacnetEventState.
   * <p>
   * The mapping is exact at this point.
   *
   * @param sourceState the alarm source state.
   * @return the ordinal representing the corresponding BBacnetEventState.
   */
  public static BBacnetEventState make(BSourceState sourceState)
  {
    switch (sourceState.getOrdinal())
    {
      case BSourceState.NORMAL:
        return BBacnetEventState.normal;
      case BSourceState.OFFNORMAL:
        return BBacnetEventState.offnormal;
      case BSourceState.FAULT:
        return BBacnetEventState.fault;
      case BSourceState.ALERT:
      default:
        throw new IllegalArgumentException("Invalid sourceState:" + sourceState);
    }
  }

  /**
   * Generate the correct ordinal mapping from BAlarmState
   * to BBacnetEventState.
   * <p>
   * The mapping is exact at this point.
   *
   * @param alarmState the BAlarmState.
   * @return the ordinal representing the corresponding BBacnetEventState.
   */
  public static int fromBAlarmState(BAlarmState alarmState)
  {
    return alarmState.getOrdinal();
  }

  /**
   * Return this event state as a BSourceState.
   *
   * @return the BSourceState corresponding to this BACnet event state.
   */
  public BSourceState toSourceState()
  {
    switch (getOrdinal())
    {
      case NORMAL:
        return BSourceState.normal;
      case FAULT:
        return BSourceState.fault;

      // all of these are offnormal states
      case OFFNORMAL:
      case HIGH_LIMIT:
      case LOW_LIMIT:
      case LIFE_SAFETY_ALARM:
      default:
        return BSourceState.offnormal;
    }
  }

  /**
   * Is the given event state enum a "normal" event state?
   */
  public static boolean isNormal(BEnum eventState)
  {
    return eventState.getOrdinal() == NORMAL;
  }

  /**
   * Is the given event state enum a "fault" event state?
   */
  public static boolean isFault(BEnum eventState)
  {
    return eventState.getOrdinal() == FAULT;
  }

  /**
   * Is the given event state enum an "offnormal" event state?
   * Note that all non-normal and non-fault states are "offnormal".
   */
  public static boolean isOffnormal(BEnum eventState)
  {
    return eventState.getOrdinal() != NORMAL
      && eventState.getOrdinal() != FAULT;
  }

  /**
   * @param eventState a BEnum using the BBacnetEventState range.
   * @return the appropriate BACneEventTransitionBits field
   * for this event state.
   */
  public static int getEventTransitionBits(BEnum eventState)
  {
    switch (eventState.getOrdinal())
    {
      case NORMAL:
        return BacnetAlarmConst.TO_NORMAL_BIT;
      case FAULT:
        return BacnetAlarmConst.TO_FAULT_BIT;

      // all of these are offnormal states
      case OFFNORMAL:
      case HIGH_LIMIT:
      case LOW_LIMIT:
      case LIFE_SAFETY_ALARM:
      default:
        return BacnetAlarmConst.TO_OFFNORMAL_BIT;
    }
  }

  /**
   * @param eventState a BEnum using the BBacnetEventState range.
   * @return the appropriate BACnetEventTransitionBits index for
   * referencing transition arrays, e.g. Event_Time_Stamps.
   */
  public static int getEventTransition(BEnum eventState)
  {
    switch (eventState.getOrdinal())
    {
      case NORMAL:
        return BacnetAlarmConst.TO_NORMAL_INDEX;
      case FAULT:
        return BacnetAlarmConst.TO_FAULT_INDEX;

      // all of these are offnormal states
      case OFFNORMAL:
      case HIGH_LIMIT:
      case LOW_LIMIT:
      case LIFE_SAFETY_ALARM:
      default:
        return BacnetAlarmConst.TO_OFFNORMAL_INDEX;
    }
  }

  /**
   * @return String representation of this BEnum.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      return getTag();
    return getDisplayTag(context);
  }

}
