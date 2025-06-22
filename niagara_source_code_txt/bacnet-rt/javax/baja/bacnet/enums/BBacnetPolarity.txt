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
 * BBacnetPolarity represents the Bacnet Polarity
 * enumeration.
 *
 * @author Craig Gemmill
 * @version $Revision: 7$ $Date: 12/19/01 4:35:59 PM$
 * @creation 19 Jun 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("normal"),
    @Range("reverse")
  }
)
public final class BBacnetPolarity
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetPolarity(3095385145)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for normal. */
  public static final int NORMAL = 0;
  /** Ordinal value for reverse. */
  public static final int REVERSE = 1;

  /** BBacnetPolarity constant for normal. */
  public static final BBacnetPolarity normal = new BBacnetPolarity(NORMAL);
  /** BBacnetPolarity constant for reverse. */
  public static final BBacnetPolarity reverse = new BBacnetPolarity(REVERSE);

  /** Factory method with ordinal. */
  public static BBacnetPolarity make(int ordinal)
  {
    return (BBacnetPolarity)normal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetPolarity make(String tag)
  {
    return (BBacnetPolarity)normal.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetPolarity(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetPolarity DEFAULT = normal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPolarity.class);

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
   * @param value the boolean value to be represented.
   * @return a BBacnetPolarity object with the given code.
   */
  public static final BBacnetPolarity make(boolean value)
  {
    return (value ? reverse : normal);
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
