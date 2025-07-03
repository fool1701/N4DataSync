/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.category;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BCategoryMode defines how a category is mapped into permissions
 * for an object.  Union indicates that permissions for the category
 * are added to the to the user's permissions for the object.
 * Intersection indicates that missing permissions for the category
 * are removed from the user's permissions for the object.
 * 
 * @author    John Sublett
 * @creation  11 Mar 2008
 * @version   $Revision: 1$ $Date: 3/12/08 5:40:56 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("union"),
    @Range("intersection")
  }
)
public final class BCategoryMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.category.BCategoryMode(1553246482)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for union. */
  public static final int UNION = 0;
  /** Ordinal value for intersection. */
  public static final int INTERSECTION = 1;

  /** BCategoryMode constant for union. */
  public static final BCategoryMode union = new BCategoryMode(UNION);
  /** BCategoryMode constant for intersection. */
  public static final BCategoryMode intersection = new BCategoryMode(INTERSECTION);

  /** Factory method with ordinal. */
  public static BCategoryMode make(int ordinal)
  {
    return (BCategoryMode)union.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BCategoryMode make(String tag)
  {
    return (BCategoryMode)union.getRange().get(tag);
  }

  /** Private constructor. */
  private BCategoryMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BCategoryMode DEFAULT = union;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCategoryMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
