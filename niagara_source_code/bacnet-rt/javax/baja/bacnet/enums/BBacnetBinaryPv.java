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
 * BBacnetBinaryPv represents the Bacnet Binary present value
 * enumeration.
 *
 * @author Craig Gemmill
 * @version $Revision: 8$ $Date: 11/20/01 9:19:58 AM$
 * @creation 31 Jan 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("inactive"),
    @Range("active")
  }
)
public final class BBacnetBinaryPv
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetBinaryPv(68504199)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for inactive. */
  public static final int INACTIVE = 0;
  /** Ordinal value for active. */
  public static final int ACTIVE = 1;

  /** BBacnetBinaryPv constant for inactive. */
  public static final BBacnetBinaryPv inactive = new BBacnetBinaryPv(INACTIVE);
  /** BBacnetBinaryPv constant for active. */
  public static final BBacnetBinaryPv active = new BBacnetBinaryPv(ACTIVE);

  /** Factory method with ordinal. */
  public static BBacnetBinaryPv make(int ordinal)
  {
    return (BBacnetBinaryPv)inactive.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetBinaryPv make(String tag)
  {
    return (BBacnetBinaryPv)inactive.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetBinaryPv(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetBinaryPv DEFAULT = inactive;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBinaryPv.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


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
    return DEFAULT.getRange().getTag(id);
  }

  /**
   * Get a string tag, using the true/false text in the
   * given context, if possible.
   */
  public String getTag(Context cx)
  {
    if (cx != null)
    {
      BString tag = (BString)cx.getFacet(isActive() ? BFacets.TRUE_TEXT : BFacets.FALSE_TEXT);
      return (tag != null) ? tag.getString() : getTag();
    }
    return getTag();
  }

  /**
   * @param value the boolean value to be represented.
   * @return a BBacnetBinaryPv object with the given code.
   */
  public static final BBacnetBinaryPv make(boolean value)
  {
    return (value ? active : inactive);
  }

  /**
   * @return String representation of this BEnum.
   */
  public String toString(Context context)
  {
    if (context != null)
    {
      if (context.equals(BacnetConst.facetsContext))
        return getTag();
      if (isActive())
      {
        BString s = (BString)context.getFacet(BFacets.TRUE_TEXT);
        if (s != null) return s.getString();
      }
      else
      {
        BString s = (BString)context.getFacet(BFacets.FALSE_TEXT);
        if (s != null) return s.getString();
      }
    }

    return getDisplayTag(context);
  }

  /**
   * @return true for active, false for inactive.
   */
  public boolean isActive()
  {
    return getOrdinal() == ACTIVE;
  }
}
