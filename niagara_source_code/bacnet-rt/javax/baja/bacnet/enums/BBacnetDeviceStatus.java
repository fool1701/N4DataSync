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
 * BBacnetDeviceStatus represents the BACnetDeviceStatus
 * enumeration.
 * <p>
 * BBacnetDeviceStatus is an "extensible" enumeration.
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
 * @version $Revision: 7$ $Date: 12/19/01 4:35:56 PM$
 * @creation 30 Jan 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("operational"),
    @Range("operationalReadOnly"),
    @Range("downloadRequired"),
    @Range("downloadInProgress"),
    @Range("nonOperational"),
    @Range("backupInProgress")
  }
)
public final class BBacnetDeviceStatus
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetDeviceStatus(112474345)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for operational. */
  public static final int OPERATIONAL = 0;
  /** Ordinal value for operationalReadOnly. */
  public static final int OPERATIONAL_READ_ONLY = 1;
  /** Ordinal value for downloadRequired. */
  public static final int DOWNLOAD_REQUIRED = 2;
  /** Ordinal value for downloadInProgress. */
  public static final int DOWNLOAD_IN_PROGRESS = 3;
  /** Ordinal value for nonOperational. */
  public static final int NON_OPERATIONAL = 4;
  /** Ordinal value for backupInProgress. */
  public static final int BACKUP_IN_PROGRESS = 5;

  /** BBacnetDeviceStatus constant for operational. */
  public static final BBacnetDeviceStatus operational = new BBacnetDeviceStatus(OPERATIONAL);
  /** BBacnetDeviceStatus constant for operationalReadOnly. */
  public static final BBacnetDeviceStatus operationalReadOnly = new BBacnetDeviceStatus(OPERATIONAL_READ_ONLY);
  /** BBacnetDeviceStatus constant for downloadRequired. */
  public static final BBacnetDeviceStatus downloadRequired = new BBacnetDeviceStatus(DOWNLOAD_REQUIRED);
  /** BBacnetDeviceStatus constant for downloadInProgress. */
  public static final BBacnetDeviceStatus downloadInProgress = new BBacnetDeviceStatus(DOWNLOAD_IN_PROGRESS);
  /** BBacnetDeviceStatus constant for nonOperational. */
  public static final BBacnetDeviceStatus nonOperational = new BBacnetDeviceStatus(NON_OPERATIONAL);
  /** BBacnetDeviceStatus constant for backupInProgress. */
  public static final BBacnetDeviceStatus backupInProgress = new BBacnetDeviceStatus(BACKUP_IN_PROGRESS);

  /** Factory method with ordinal. */
  public static BBacnetDeviceStatus make(int ordinal)
  {
    return (BBacnetDeviceStatus)operational.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetDeviceStatus make(String tag)
  {
    return (BBacnetDeviceStatus)operational.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetDeviceStatus(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetDeviceStatus DEFAULT = operational;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDeviceStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 5;
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
