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
 * BBacnetReliability represents the BACnetReliability
 * enumeration.
 * <p>
 * BBacnetReliability is an "extensible" enumeration.
 * Values 0-63 are reserved for use by ASHRAE.
 * Values from 64-65535 (0xFFFF)
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
 * @version $Revision: 4$ $Date: 11/28/01 6:14:22 AM$
 * @creation 07 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("noFaultDetected"),
    @Range("noSensor"),
    @Range("overRange"),
    @Range("underRange"),
    @Range("openLoop"),
    @Range("shortedLoop"),
    @Range("noOutput"),
    @Range("unreliableOther"),
    @Range("processError"),
    @Range("multiStateFault"),
    @Range("configurationError"),
    @Range(value = "communicationFailure", ordinal = 12),
    @Range(value = "memberFault", ordinal = 13),
    @Range(value = "monitoredObjectFault", ordinal = 14),
    @Range(value = "tripped", ordinal = 15)
  }
)
public final class BBacnetReliability
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetReliability(1579967829)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for noFaultDetected. */
  public static final int NO_FAULT_DETECTED = 0;
  /** Ordinal value for noSensor. */
  public static final int NO_SENSOR = 1;
  /** Ordinal value for overRange. */
  public static final int OVER_RANGE = 2;
  /** Ordinal value for underRange. */
  public static final int UNDER_RANGE = 3;
  /** Ordinal value for openLoop. */
  public static final int OPEN_LOOP = 4;
  /** Ordinal value for shortedLoop. */
  public static final int SHORTED_LOOP = 5;
  /** Ordinal value for noOutput. */
  public static final int NO_OUTPUT = 6;
  /** Ordinal value for unreliableOther. */
  public static final int UNRELIABLE_OTHER = 7;
  /** Ordinal value for processError. */
  public static final int PROCESS_ERROR = 8;
  /** Ordinal value for multiStateFault. */
  public static final int MULTI_STATE_FAULT = 9;
  /** Ordinal value for configurationError. */
  public static final int CONFIGURATION_ERROR = 10;
  /** Ordinal value for communicationFailure. */
  public static final int COMMUNICATION_FAILURE = 12;
  /** Ordinal value for memberFault. */
  public static final int MEMBER_FAULT = 13;
  /** Ordinal value for monitoredObjectFault. */
  public static final int MONITORED_OBJECT_FAULT = 14;
  /** Ordinal value for tripped. */
  public static final int TRIPPED = 15;

  /** BBacnetReliability constant for noFaultDetected. */
  public static final BBacnetReliability noFaultDetected = new BBacnetReliability(NO_FAULT_DETECTED);
  /** BBacnetReliability constant for noSensor. */
  public static final BBacnetReliability noSensor = new BBacnetReliability(NO_SENSOR);
  /** BBacnetReliability constant for overRange. */
  public static final BBacnetReliability overRange = new BBacnetReliability(OVER_RANGE);
  /** BBacnetReliability constant for underRange. */
  public static final BBacnetReliability underRange = new BBacnetReliability(UNDER_RANGE);
  /** BBacnetReliability constant for openLoop. */
  public static final BBacnetReliability openLoop = new BBacnetReliability(OPEN_LOOP);
  /** BBacnetReliability constant for shortedLoop. */
  public static final BBacnetReliability shortedLoop = new BBacnetReliability(SHORTED_LOOP);
  /** BBacnetReliability constant for noOutput. */
  public static final BBacnetReliability noOutput = new BBacnetReliability(NO_OUTPUT);
  /** BBacnetReliability constant for unreliableOther. */
  public static final BBacnetReliability unreliableOther = new BBacnetReliability(UNRELIABLE_OTHER);
  /** BBacnetReliability constant for processError. */
  public static final BBacnetReliability processError = new BBacnetReliability(PROCESS_ERROR);
  /** BBacnetReliability constant for multiStateFault. */
  public static final BBacnetReliability multiStateFault = new BBacnetReliability(MULTI_STATE_FAULT);
  /** BBacnetReliability constant for configurationError. */
  public static final BBacnetReliability configurationError = new BBacnetReliability(CONFIGURATION_ERROR);
  /** BBacnetReliability constant for communicationFailure. */
  public static final BBacnetReliability communicationFailure = new BBacnetReliability(COMMUNICATION_FAILURE);
  /** BBacnetReliability constant for memberFault. */
  public static final BBacnetReliability memberFault = new BBacnetReliability(MEMBER_FAULT);
  /** BBacnetReliability constant for monitoredObjectFault. */
  public static final BBacnetReliability monitoredObjectFault = new BBacnetReliability(MONITORED_OBJECT_FAULT);
  /** BBacnetReliability constant for tripped. */
  public static final BBacnetReliability tripped = new BBacnetReliability(TRIPPED);

  /** Factory method with ordinal. */
  public static BBacnetReliability make(int ordinal)
  {
    return (BBacnetReliability)noFaultDetected.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetReliability make(String tag)
  {
    return (BBacnetReliability)noFaultDetected.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetReliability(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetReliability DEFAULT = noFaultDetected;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetReliability.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = MONITORED_OBJECT_FAULT;
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
   * @return String representation of this BEnum.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      return getTag();
    return getDisplayTag(context);
  }
}
