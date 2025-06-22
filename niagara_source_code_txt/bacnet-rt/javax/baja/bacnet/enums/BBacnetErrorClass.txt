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
 * BBacnetErrorClass represents the error-class portion of the
 * BACnet Error sequence.
 * <p>
 * BBacnetErrorClass is an "extensible" enumeration.
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
 * @version $Revision: 7$ $Date: 12/19/01 4:35:57 PM$
 * @creation 10 Aug 00
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("device"),
    @Range("object"),
    @Range("property"),
    @Range("resources"),
    @Range("security"),
    @Range("services"),
    @Range("vt"),
    @Range("communication")
  }
)
public final class BBacnetErrorClass
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetErrorClass(3794331102)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for device. */
  public static final int DEVICE = 0;
  /** Ordinal value for object. */
  public static final int OBJECT = 1;
  /** Ordinal value for property. */
  public static final int PROPERTY = 2;
  /** Ordinal value for resources. */
  public static final int RESOURCES = 3;
  /** Ordinal value for security. */
  public static final int SECURITY = 4;
  /** Ordinal value for services. */
  public static final int SERVICES = 5;
  /** Ordinal value for vt. */
  public static final int VT = 6;
  /** Ordinal value for communication. */
  public static final int COMMUNICATION = 7;

  /** BBacnetErrorClass constant for device. */
  public static final BBacnetErrorClass device = new BBacnetErrorClass(DEVICE);
  /** BBacnetErrorClass constant for object. */
  public static final BBacnetErrorClass object = new BBacnetErrorClass(OBJECT);
  /** BBacnetErrorClass constant for property. */
  public static final BBacnetErrorClass property = new BBacnetErrorClass(PROPERTY);
  /** BBacnetErrorClass constant for resources. */
  public static final BBacnetErrorClass resources = new BBacnetErrorClass(RESOURCES);
  /** BBacnetErrorClass constant for security. */
  public static final BBacnetErrorClass security = new BBacnetErrorClass(SECURITY);
  /** BBacnetErrorClass constant for services. */
  public static final BBacnetErrorClass services = new BBacnetErrorClass(SERVICES);
  /** BBacnetErrorClass constant for vt. */
  public static final BBacnetErrorClass vt = new BBacnetErrorClass(VT);
  /** BBacnetErrorClass constant for communication. */
  public static final BBacnetErrorClass communication = new BBacnetErrorClass(COMMUNICATION);

  /** Factory method with ordinal. */
  public static BBacnetErrorClass make(int ordinal)
  {
    return (BBacnetErrorClass)device.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetErrorClass make(String tag)
  {
    return (BBacnetErrorClass)device.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetErrorClass(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetErrorClass DEFAULT = device;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetErrorClass.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 7;
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
