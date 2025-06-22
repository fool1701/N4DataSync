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
 * BBacnetAbortReason represents the Bacnet Abort Reason
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
 * @version $Revision: 7$ $Date: 12/19/01 4:35:55 PM$
 * @creation 10 Aug 00
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("other"),
    @Range("bufferOverflow"),
    @Range("invalidApduInThisState"),
    @Range("preemptedByHigherPriorityTask"),
    @Range("segmentationNotSupported"),
    @Range("securityError"),
    @Range("insufficientSecurity"),
    @Range("windowSizeOutOfRange"),
    @Range("applicationExceededReplyTime"),
    @Range("outOfResources"),
    @Range("tsmTimeout"),
    @Range("apduTooLong")
  }
)
public final class BBacnetAbortReason
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetAbortReason(3052683178)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for other. */
  public static final int OTHER = 0;
  /** Ordinal value for bufferOverflow. */
  public static final int BUFFER_OVERFLOW = 1;
  /** Ordinal value for invalidApduInThisState. */
  public static final int INVALID_APDU_IN_THIS_STATE = 2;
  /** Ordinal value for preemptedByHigherPriorityTask. */
  public static final int PREEMPTED_BY_HIGHER_PRIORITY_TASK = 3;
  /** Ordinal value for segmentationNotSupported. */
  public static final int SEGMENTATION_NOT_SUPPORTED = 4;
  /** Ordinal value for securityError. */
  public static final int SECURITY_ERROR = 5;
  /** Ordinal value for insufficientSecurity. */
  public static final int INSUFFICIENT_SECURITY = 6;
  /** Ordinal value for windowSizeOutOfRange. */
  public static final int WINDOW_SIZE_OUT_OF_RANGE = 7;
  /** Ordinal value for applicationExceededReplyTime. */
  public static final int APPLICATION_EXCEEDED_REPLY_TIME = 8;
  /** Ordinal value for outOfResources. */
  public static final int OUT_OF_RESOURCES = 9;
  /** Ordinal value for tsmTimeout. */
  public static final int TSM_TIMEOUT = 10;
  /** Ordinal value for apduTooLong. */
  public static final int APDU_TOO_LONG = 11;

  /** BBacnetAbortReason constant for other. */
  public static final BBacnetAbortReason other = new BBacnetAbortReason(OTHER);
  /** BBacnetAbortReason constant for bufferOverflow. */
  public static final BBacnetAbortReason bufferOverflow = new BBacnetAbortReason(BUFFER_OVERFLOW);
  /** BBacnetAbortReason constant for invalidApduInThisState. */
  public static final BBacnetAbortReason invalidApduInThisState = new BBacnetAbortReason(INVALID_APDU_IN_THIS_STATE);
  /** BBacnetAbortReason constant for preemptedByHigherPriorityTask. */
  public static final BBacnetAbortReason preemptedByHigherPriorityTask = new BBacnetAbortReason(PREEMPTED_BY_HIGHER_PRIORITY_TASK);
  /** BBacnetAbortReason constant for segmentationNotSupported. */
  public static final BBacnetAbortReason segmentationNotSupported = new BBacnetAbortReason(SEGMENTATION_NOT_SUPPORTED);
  /** BBacnetAbortReason constant for securityError. */
  public static final BBacnetAbortReason securityError = new BBacnetAbortReason(SECURITY_ERROR);
  /** BBacnetAbortReason constant for insufficientSecurity. */
  public static final BBacnetAbortReason insufficientSecurity = new BBacnetAbortReason(INSUFFICIENT_SECURITY);
  /** BBacnetAbortReason constant for windowSizeOutOfRange. */
  public static final BBacnetAbortReason windowSizeOutOfRange = new BBacnetAbortReason(WINDOW_SIZE_OUT_OF_RANGE);
  /** BBacnetAbortReason constant for applicationExceededReplyTime. */
  public static final BBacnetAbortReason applicationExceededReplyTime = new BBacnetAbortReason(APPLICATION_EXCEEDED_REPLY_TIME);
  /** BBacnetAbortReason constant for outOfResources. */
  public static final BBacnetAbortReason outOfResources = new BBacnetAbortReason(OUT_OF_RESOURCES);
  /** BBacnetAbortReason constant for tsmTimeout. */
  public static final BBacnetAbortReason tsmTimeout = new BBacnetAbortReason(TSM_TIMEOUT);
  /** BBacnetAbortReason constant for apduTooLong. */
  public static final BBacnetAbortReason apduTooLong = new BBacnetAbortReason(APDU_TOO_LONG);

  /** Factory method with ordinal. */
  public static BBacnetAbortReason make(int ordinal)
  {
    return (BBacnetAbortReason)other.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetAbortReason make(String tag)
  {
    return (BBacnetAbortReason)other.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetAbortReason(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetAbortReason DEFAULT = other;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAbortReason.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = APDU_TOO_LONG;
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
