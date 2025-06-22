/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetLoggingType represents the BACnetLoggingType
 * enumeration.
 * <p>
 * BBacnetLoggingType is an "extensible" enumeration.
 * Values 0-63 are reserved for use by ASHRAE.
 * Values from 64-255 (0xFF)
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
 * @creation 28 Sep 2009
 * @since Niagara 3.5
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("polled"),
    @Range("cov"),
    @Range("triggered")
  }
)
public final class BBacnetLoggingType
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetLoggingType(936370336)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for polled. */
  public static final int POLLED = 0;
  /** Ordinal value for cov. */
  public static final int COV = 1;
  /** Ordinal value for triggered. */
  public static final int TRIGGERED = 2;

  /** BBacnetLoggingType constant for polled. */
  public static final BBacnetLoggingType polled = new BBacnetLoggingType(POLLED);
  /** BBacnetLoggingType constant for cov. */
  public static final BBacnetLoggingType cov = new BBacnetLoggingType(COV);
  /** BBacnetLoggingType constant for triggered. */
  public static final BBacnetLoggingType triggered = new BBacnetLoggingType(TRIGGERED);

  /** Factory method with ordinal. */
  public static BBacnetLoggingType make(int ordinal)
  {
    return (BBacnetLoggingType)polled.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetLoggingType make(String tag)
  {
    return (BBacnetLoggingType)polled.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetLoggingType(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetLoggingType DEFAULT = polled;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLoggingType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 3;
  public static final int MAX_RESERVED_ID = 63;
  public static final int MAX_ID = 255;

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
