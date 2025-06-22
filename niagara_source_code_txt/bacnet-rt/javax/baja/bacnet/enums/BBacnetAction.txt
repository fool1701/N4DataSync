/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetAction represents the BACnetAction enumeration.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 26 Jul 2005
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("direct"),
    @Range("reverse")
  }
)
public final class BBacnetAction
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetAction(457396846)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for direct. */
  public static final int DIRECT = 0;
  /** Ordinal value for reverse. */
  public static final int REVERSE = 1;

  /** BBacnetAction constant for direct. */
  public static final BBacnetAction direct = new BBacnetAction(DIRECT);
  /** BBacnetAction constant for reverse. */
  public static final BBacnetAction reverse = new BBacnetAction(REVERSE);

  /** Factory method with ordinal. */
  public static BBacnetAction make(int ordinal)
  {
    return (BBacnetAction)direct.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetAction make(String tag)
  {
    return (BBacnetAction)direct.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetAction(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetAction DEFAULT = direct;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAction.class);

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
   * @return a BBacnetAction object with the given code.
   */
  public static final BBacnetAction make(boolean value)
  {
    return (value ? reverse : direct);
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
