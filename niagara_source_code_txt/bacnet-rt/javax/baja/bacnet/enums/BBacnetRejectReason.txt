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
 * BBacnetRejectReason represents the Bacnet Abort Reason
 * enumeration.
 * <p>
 * BBacnetAbortReason is an "extensible" enumeration.
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
 * @version $Revision: 7$ $Date: 12/19/01 4:36:01 PM$
 * @creation 10 Aug 00
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("other"),
    @Range("bufferOverflow"),
    @Range("inconsistentParameters"),
    @Range("invalidParameterDataType"),
    @Range("invalidTag"),
    @Range("missingRequiredParameter"),
    @Range("parameterOutOfRange"),
    @Range("tooManyArguments"),
    @Range("undefinedEnumeration"),
    @Range("unrecognizedService")
  }
)
public final class BBacnetRejectReason
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetRejectReason(4139349293)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for other. */
  public static final int OTHER = 0;
  /** Ordinal value for bufferOverflow. */
  public static final int BUFFER_OVERFLOW = 1;
  /** Ordinal value for inconsistentParameters. */
  public static final int INCONSISTENT_PARAMETERS = 2;
  /** Ordinal value for invalidParameterDataType. */
  public static final int INVALID_PARAMETER_DATA_TYPE = 3;
  /** Ordinal value for invalidTag. */
  public static final int INVALID_TAG = 4;
  /** Ordinal value for missingRequiredParameter. */
  public static final int MISSING_REQUIRED_PARAMETER = 5;
  /** Ordinal value for parameterOutOfRange. */
  public static final int PARAMETER_OUT_OF_RANGE = 6;
  /** Ordinal value for tooManyArguments. */
  public static final int TOO_MANY_ARGUMENTS = 7;
  /** Ordinal value for undefinedEnumeration. */
  public static final int UNDEFINED_ENUMERATION = 8;
  /** Ordinal value for unrecognizedService. */
  public static final int UNRECOGNIZED_SERVICE = 9;

  /** BBacnetRejectReason constant for other. */
  public static final BBacnetRejectReason other = new BBacnetRejectReason(OTHER);
  /** BBacnetRejectReason constant for bufferOverflow. */
  public static final BBacnetRejectReason bufferOverflow = new BBacnetRejectReason(BUFFER_OVERFLOW);
  /** BBacnetRejectReason constant for inconsistentParameters. */
  public static final BBacnetRejectReason inconsistentParameters = new BBacnetRejectReason(INCONSISTENT_PARAMETERS);
  /** BBacnetRejectReason constant for invalidParameterDataType. */
  public static final BBacnetRejectReason invalidParameterDataType = new BBacnetRejectReason(INVALID_PARAMETER_DATA_TYPE);
  /** BBacnetRejectReason constant for invalidTag. */
  public static final BBacnetRejectReason invalidTag = new BBacnetRejectReason(INVALID_TAG);
  /** BBacnetRejectReason constant for missingRequiredParameter. */
  public static final BBacnetRejectReason missingRequiredParameter = new BBacnetRejectReason(MISSING_REQUIRED_PARAMETER);
  /** BBacnetRejectReason constant for parameterOutOfRange. */
  public static final BBacnetRejectReason parameterOutOfRange = new BBacnetRejectReason(PARAMETER_OUT_OF_RANGE);
  /** BBacnetRejectReason constant for tooManyArguments. */
  public static final BBacnetRejectReason tooManyArguments = new BBacnetRejectReason(TOO_MANY_ARGUMENTS);
  /** BBacnetRejectReason constant for undefinedEnumeration. */
  public static final BBacnetRejectReason undefinedEnumeration = new BBacnetRejectReason(UNDEFINED_ENUMERATION);
  /** BBacnetRejectReason constant for unrecognizedService. */
  public static final BBacnetRejectReason unrecognizedService = new BBacnetRejectReason(UNRECOGNIZED_SERVICE);

  /** Factory method with ordinal. */
  public static BBacnetRejectReason make(int ordinal)
  {
    return (BBacnetRejectReason)other.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetRejectReason make(String tag)
  {
    return (BBacnetRejectReason)other.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetRejectReason(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetRejectReason DEFAULT = other;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetRejectReason.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 9;
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
}
