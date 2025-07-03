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
 * BBacnetLifeSafetyMode represents the Bacnet Life Safety Mode
 * enumeration.
 * <p>
 * BBacnetLifeSafetyMode is an "extensible" enumeration.
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
    @Range("off"),
    @Range("on"),
    @Range("test"),
    @Range("manned"),
    @Range("unmanned"),
    @Range("armed"),
    @Range("disarmed"),
    @Range("prearmed"),
    @Range("slow"),
    @Range("fast"),
    @Range("disconnected"),
    @Range("enabled"),
    @Range("disabled"),
    @Range("automaticReleaseDisabled"),
    @Range("defaultMode")
  }
)
public final class BBacnetLifeSafetyMode
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetLifeSafetyMode(2286176327)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for off. */
  public static final int OFF = 0;
  /** Ordinal value for on. */
  public static final int ON = 1;
  /** Ordinal value for test. */
  public static final int TEST = 2;
  /** Ordinal value for manned. */
  public static final int MANNED = 3;
  /** Ordinal value for unmanned. */
  public static final int UNMANNED = 4;
  /** Ordinal value for armed. */
  public static final int ARMED = 5;
  /** Ordinal value for disarmed. */
  public static final int DISARMED = 6;
  /** Ordinal value for prearmed. */
  public static final int PREARMED = 7;
  /** Ordinal value for slow. */
  public static final int SLOW = 8;
  /** Ordinal value for fast. */
  public static final int FAST = 9;
  /** Ordinal value for disconnected. */
  public static final int DISCONNECTED = 10;
  /** Ordinal value for enabled. */
  public static final int ENABLED = 11;
  /** Ordinal value for disabled. */
  public static final int DISABLED = 12;
  /** Ordinal value for automaticReleaseDisabled. */
  public static final int AUTOMATIC_RELEASE_DISABLED = 13;
  /** Ordinal value for defaultMode. */
  public static final int DEFAULT_MODE = 14;

  /** BBacnetLifeSafetyMode constant for off. */
  public static final BBacnetLifeSafetyMode off = new BBacnetLifeSafetyMode(OFF);
  /** BBacnetLifeSafetyMode constant for on. */
  public static final BBacnetLifeSafetyMode on = new BBacnetLifeSafetyMode(ON);
  /** BBacnetLifeSafetyMode constant for test. */
  public static final BBacnetLifeSafetyMode test = new BBacnetLifeSafetyMode(TEST);
  /** BBacnetLifeSafetyMode constant for manned. */
  public static final BBacnetLifeSafetyMode manned = new BBacnetLifeSafetyMode(MANNED);
  /** BBacnetLifeSafetyMode constant for unmanned. */
  public static final BBacnetLifeSafetyMode unmanned = new BBacnetLifeSafetyMode(UNMANNED);
  /** BBacnetLifeSafetyMode constant for armed. */
  public static final BBacnetLifeSafetyMode armed = new BBacnetLifeSafetyMode(ARMED);
  /** BBacnetLifeSafetyMode constant for disarmed. */
  public static final BBacnetLifeSafetyMode disarmed = new BBacnetLifeSafetyMode(DISARMED);
  /** BBacnetLifeSafetyMode constant for prearmed. */
  public static final BBacnetLifeSafetyMode prearmed = new BBacnetLifeSafetyMode(PREARMED);
  /** BBacnetLifeSafetyMode constant for slow. */
  public static final BBacnetLifeSafetyMode slow = new BBacnetLifeSafetyMode(SLOW);
  /** BBacnetLifeSafetyMode constant for fast. */
  public static final BBacnetLifeSafetyMode fast = new BBacnetLifeSafetyMode(FAST);
  /** BBacnetLifeSafetyMode constant for disconnected. */
  public static final BBacnetLifeSafetyMode disconnected = new BBacnetLifeSafetyMode(DISCONNECTED);
  /** BBacnetLifeSafetyMode constant for enabled. */
  public static final BBacnetLifeSafetyMode enabled = new BBacnetLifeSafetyMode(ENABLED);
  /** BBacnetLifeSafetyMode constant for disabled. */
  public static final BBacnetLifeSafetyMode disabled = new BBacnetLifeSafetyMode(DISABLED);
  /** BBacnetLifeSafetyMode constant for automaticReleaseDisabled. */
  public static final BBacnetLifeSafetyMode automaticReleaseDisabled = new BBacnetLifeSafetyMode(AUTOMATIC_RELEASE_DISABLED);
  /** BBacnetLifeSafetyMode constant for defaultMode. */
  public static final BBacnetLifeSafetyMode defaultMode = new BBacnetLifeSafetyMode(DEFAULT_MODE);

  /** Factory method with ordinal. */
  public static BBacnetLifeSafetyMode make(int ordinal)
  {
    return (BBacnetLifeSafetyMode)off.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetLifeSafetyMode make(String tag)
  {
    return (BBacnetLifeSafetyMode)off.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetLifeSafetyMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetLifeSafetyMode DEFAULT = off;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLifeSafetyMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 14;
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
