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
 * BBacnetLifeSafetyOperation represents the Bacnet Life Safety Operation
 * enumeration.
 * <p>
 * BBacnetLifeSafetyOperation is an "extensible" enumeration.
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
 * @version $Revision$ $Date$
 * @creation 16 May 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("silence"),
    @Range("silenceAudible"),
    @Range("silenceVisual"),
    @Range("reset"),
    @Range("resetAlarm"),
    @Range("resetFault"),
    @Range("unsilence"),
    @Range("unsilenceAudible"),
    @Range("unsilenceVisual")
  }
)
public final class BBacnetLifeSafetyOperation
  extends BFrozenEnum
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetLifeSafetyOperation(2757319819)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for silence. */
  public static final int SILENCE = 1;
  /** Ordinal value for silenceAudible. */
  public static final int SILENCE_AUDIBLE = 2;
  /** Ordinal value for silenceVisual. */
  public static final int SILENCE_VISUAL = 3;
  /** Ordinal value for reset. */
  public static final int RESET = 4;
  /** Ordinal value for resetAlarm. */
  public static final int RESET_ALARM = 5;
  /** Ordinal value for resetFault. */
  public static final int RESET_FAULT = 6;
  /** Ordinal value for unsilence. */
  public static final int UNSILENCE = 7;
  /** Ordinal value for unsilenceAudible. */
  public static final int UNSILENCE_AUDIBLE = 8;
  /** Ordinal value for unsilenceVisual. */
  public static final int UNSILENCE_VISUAL = 9;

  /** BBacnetLifeSafetyOperation constant for none. */
  public static final BBacnetLifeSafetyOperation none = new BBacnetLifeSafetyOperation(NONE);
  /** BBacnetLifeSafetyOperation constant for silence. */
  public static final BBacnetLifeSafetyOperation silence = new BBacnetLifeSafetyOperation(SILENCE);
  /** BBacnetLifeSafetyOperation constant for silenceAudible. */
  public static final BBacnetLifeSafetyOperation silenceAudible = new BBacnetLifeSafetyOperation(SILENCE_AUDIBLE);
  /** BBacnetLifeSafetyOperation constant for silenceVisual. */
  public static final BBacnetLifeSafetyOperation silenceVisual = new BBacnetLifeSafetyOperation(SILENCE_VISUAL);
  /** BBacnetLifeSafetyOperation constant for reset. */
  public static final BBacnetLifeSafetyOperation reset = new BBacnetLifeSafetyOperation(RESET);
  /** BBacnetLifeSafetyOperation constant for resetAlarm. */
  public static final BBacnetLifeSafetyOperation resetAlarm = new BBacnetLifeSafetyOperation(RESET_ALARM);
  /** BBacnetLifeSafetyOperation constant for resetFault. */
  public static final BBacnetLifeSafetyOperation resetFault = new BBacnetLifeSafetyOperation(RESET_FAULT);
  /** BBacnetLifeSafetyOperation constant for unsilence. */
  public static final BBacnetLifeSafetyOperation unsilence = new BBacnetLifeSafetyOperation(UNSILENCE);
  /** BBacnetLifeSafetyOperation constant for unsilenceAudible. */
  public static final BBacnetLifeSafetyOperation unsilenceAudible = new BBacnetLifeSafetyOperation(UNSILENCE_AUDIBLE);
  /** BBacnetLifeSafetyOperation constant for unsilenceVisual. */
  public static final BBacnetLifeSafetyOperation unsilenceVisual = new BBacnetLifeSafetyOperation(UNSILENCE_VISUAL);

  /** Factory method with ordinal. */
  public static BBacnetLifeSafetyOperation make(int ordinal)
  {
    return (BBacnetLifeSafetyOperation)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetLifeSafetyOperation make(String tag)
  {
    return (BBacnetLifeSafetyOperation)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetLifeSafetyOperation(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetLifeSafetyOperation DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLifeSafetyOperation.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 9;
  public static final int MAX_RESERVED_ID = 64;
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
