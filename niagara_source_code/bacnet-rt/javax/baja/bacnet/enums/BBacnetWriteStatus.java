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
 * BBacnetWriteStatus represents the BACnetWriteStatus
 * enumeration.
 * <p>
 * BBacnetWriteStatus is an "extensible" enumeration.
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
 * @author Joseph Chandler
 * @creation 15 Apr 15
 * @since Niagara 4
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("idle"),
    @Range("inProgress"),
    @Range("successful"),
    @Range("failed")
  }
)
public final class BBacnetWriteStatus
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetWriteStatus(4215953409)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for idle. */
  public static final int IDLE = 0;
  /** Ordinal value for inProgress. */
  public static final int IN_PROGRESS = 1;
  /** Ordinal value for successful. */
  public static final int SUCCESSFUL = 2;
  /** Ordinal value for failed. */
  public static final int FAILED = 3;

  /** BBacnetWriteStatus constant for idle. */
  public static final BBacnetWriteStatus idle = new BBacnetWriteStatus(IDLE);
  /** BBacnetWriteStatus constant for inProgress. */
  public static final BBacnetWriteStatus inProgress = new BBacnetWriteStatus(IN_PROGRESS);
  /** BBacnetWriteStatus constant for successful. */
  public static final BBacnetWriteStatus successful = new BBacnetWriteStatus(SUCCESSFUL);
  /** BBacnetWriteStatus constant for failed. */
  public static final BBacnetWriteStatus failed = new BBacnetWriteStatus(FAILED);

  /** Factory method with ordinal. */
  public static BBacnetWriteStatus make(int ordinal)
  {
    return (BBacnetWriteStatus)idle.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetWriteStatus make(String tag)
  {
    return (BBacnetWriteStatus)idle.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetWriteStatus(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetWriteStatus DEFAULT = idle;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetWriteStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = FAILED;
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
