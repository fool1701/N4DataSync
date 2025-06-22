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
 * BBacnetEventType represents the Bacnet Event Type
 * enumeration.  BBacnetEventType is an "extensible" enumeration.
 * <p>
 * BBacnetEventType is an "extensible" enumeration.
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
 * @version $Revision: 4$ $Date: 11/28/01 6:14:21 AM$
 * @creation 07 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("changeOfBitstring"),
    @Range("changeOfState"),
    @Range("changeOfValue"),
    @Range("commandFailure"),
    @Range("floatingLimit"),
    @Range("outOfRange"),
    @Range("complexEventType"),
    @Range("bufferReadyDeprecated"),
    @Range("changeOfLifeSafety"),
    @Range("extended"),
    @Range("bufferReady"),
    @Range("unsignedRange"),
    @Range("reserved"),
    @Range("accessEvent"),
    @Range("doubleOutOfRange"),
    @Range("signedOutOfRange"),
    @Range("unsignedOutOfRange"),
    @Range("changeOfCharacterstring"),
    @Range("changeOfStatusFlags"),
    @Range("changeOfReliability"),
    @Range("none")
  }
)
public final class BBacnetEventType
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetEventType(4181555318)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for changeOfBitstring. */
  public static final int CHANGE_OF_BITSTRING = 0;
  /** Ordinal value for changeOfState. */
  public static final int CHANGE_OF_STATE = 1;
  /** Ordinal value for changeOfValue. */
  public static final int CHANGE_OF_VALUE = 2;
  /** Ordinal value for commandFailure. */
  public static final int COMMAND_FAILURE = 3;
  /** Ordinal value for floatingLimit. */
  public static final int FLOATING_LIMIT = 4;
  /** Ordinal value for outOfRange. */
  public static final int OUT_OF_RANGE = 5;
  /** Ordinal value for complexEventType. */
  public static final int COMPLEX_EVENT_TYPE = 6;
  /** Ordinal value for bufferReadyDeprecated. */
  public static final int BUFFER_READY_DEPRECATED = 7;
  /** Ordinal value for changeOfLifeSafety. */
  public static final int CHANGE_OF_LIFE_SAFETY = 8;
  /** Ordinal value for extended. */
  public static final int EXTENDED = 9;
  /** Ordinal value for bufferReady. */
  public static final int BUFFER_READY = 10;
  /** Ordinal value for unsignedRange. */
  public static final int UNSIGNED_RANGE = 11;
  /** Ordinal value for reserved. */
  public static final int RESERVED = 12;
  /** Ordinal value for accessEvent. */
  public static final int ACCESS_EVENT = 13;
  /** Ordinal value for doubleOutOfRange. */
  public static final int DOUBLE_OUT_OF_RANGE = 14;
  /** Ordinal value for signedOutOfRange. */
  public static final int SIGNED_OUT_OF_RANGE = 15;
  /** Ordinal value for unsignedOutOfRange. */
  public static final int UNSIGNED_OUT_OF_RANGE = 16;
  /** Ordinal value for changeOfCharacterstring. */
  public static final int CHANGE_OF_CHARACTERSTRING = 17;
  /** Ordinal value for changeOfStatusFlags. */
  public static final int CHANGE_OF_STATUS_FLAGS = 18;
  /** Ordinal value for changeOfReliability. */
  public static final int CHANGE_OF_RELIABILITY = 19;
  /** Ordinal value for none. */
  public static final int NONE = 20;

  /** BBacnetEventType constant for changeOfBitstring. */
  public static final BBacnetEventType changeOfBitstring = new BBacnetEventType(CHANGE_OF_BITSTRING);
  /** BBacnetEventType constant for changeOfState. */
  public static final BBacnetEventType changeOfState = new BBacnetEventType(CHANGE_OF_STATE);
  /** BBacnetEventType constant for changeOfValue. */
  public static final BBacnetEventType changeOfValue = new BBacnetEventType(CHANGE_OF_VALUE);
  /** BBacnetEventType constant for commandFailure. */
  public static final BBacnetEventType commandFailure = new BBacnetEventType(COMMAND_FAILURE);
  /** BBacnetEventType constant for floatingLimit. */
  public static final BBacnetEventType floatingLimit = new BBacnetEventType(FLOATING_LIMIT);
  /** BBacnetEventType constant for outOfRange. */
  public static final BBacnetEventType outOfRange = new BBacnetEventType(OUT_OF_RANGE);
  /** BBacnetEventType constant for complexEventType. */
  public static final BBacnetEventType complexEventType = new BBacnetEventType(COMPLEX_EVENT_TYPE);
  /** BBacnetEventType constant for bufferReadyDeprecated. */
  public static final BBacnetEventType bufferReadyDeprecated = new BBacnetEventType(BUFFER_READY_DEPRECATED);
  /** BBacnetEventType constant for changeOfLifeSafety. */
  public static final BBacnetEventType changeOfLifeSafety = new BBacnetEventType(CHANGE_OF_LIFE_SAFETY);
  /** BBacnetEventType constant for extended. */
  public static final BBacnetEventType extended = new BBacnetEventType(EXTENDED);
  /** BBacnetEventType constant for bufferReady. */
  public static final BBacnetEventType bufferReady = new BBacnetEventType(BUFFER_READY);
  /** BBacnetEventType constant for unsignedRange. */
  public static final BBacnetEventType unsignedRange = new BBacnetEventType(UNSIGNED_RANGE);
  /** BBacnetEventType constant for reserved. */
  public static final BBacnetEventType reserved = new BBacnetEventType(RESERVED);
  /** BBacnetEventType constant for accessEvent. */
  public static final BBacnetEventType accessEvent = new BBacnetEventType(ACCESS_EVENT);
  /** BBacnetEventType constant for doubleOutOfRange. */
  public static final BBacnetEventType doubleOutOfRange = new BBacnetEventType(DOUBLE_OUT_OF_RANGE);
  /** BBacnetEventType constant for signedOutOfRange. */
  public static final BBacnetEventType signedOutOfRange = new BBacnetEventType(SIGNED_OUT_OF_RANGE);
  /** BBacnetEventType constant for unsignedOutOfRange. */
  public static final BBacnetEventType unsignedOutOfRange = new BBacnetEventType(UNSIGNED_OUT_OF_RANGE);
  /** BBacnetEventType constant for changeOfCharacterstring. */
  public static final BBacnetEventType changeOfCharacterstring = new BBacnetEventType(CHANGE_OF_CHARACTERSTRING);
  /** BBacnetEventType constant for changeOfStatusFlags. */
  public static final BBacnetEventType changeOfStatusFlags = new BBacnetEventType(CHANGE_OF_STATUS_FLAGS);
  /** BBacnetEventType constant for changeOfReliability. */
  public static final BBacnetEventType changeOfReliability = new BBacnetEventType(CHANGE_OF_RELIABILITY);
  /** BBacnetEventType constant for none. */
  public static final BBacnetEventType none = new BBacnetEventType(NONE);

  /** Factory method with ordinal. */
  public static BBacnetEventType make(int ordinal)
  {
    return (BBacnetEventType)changeOfBitstring.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetEventType make(String tag)
  {
    return (BBacnetEventType)changeOfBitstring.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetEventType(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetEventType DEFAULT = changeOfBitstring;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEventType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = NONE;
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
