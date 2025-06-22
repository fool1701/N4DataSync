/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BOrientation defines a widget's orientation as
 * either horizontal or vertical.
 *
 * @author    Brian Frank
 * @creation  21 Nov 00
 * @version   $Revision: 2$ $Date: 3/23/05 11:29:07 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("horizontal"),
    @Range("vertical")
  }
)
public final class BOrientation
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BOrientation(2351750692)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for horizontal. */
  public static final int HORIZONTAL = 0;
  /** Ordinal value for vertical. */
  public static final int VERTICAL = 1;

  /** BOrientation constant for horizontal. */
  public static final BOrientation horizontal = new BOrientation(HORIZONTAL);
  /** BOrientation constant for vertical. */
  public static final BOrientation vertical = new BOrientation(VERTICAL);

  /** Factory method with ordinal. */
  public static BOrientation make(int ordinal)
  {
    return (BOrientation)horizontal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BOrientation make(String tag)
  {
    return (BOrientation)horizontal.getRange().get(tag);
  }

  /** Private constructor. */
  private BOrientation(int ordinal)
  {
    super(ordinal);
  }

  public static final BOrientation DEFAULT = horizontal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrientation.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the orientation value that is opposite of this one.
   */
  public BOrientation getOpposite()
  {
    if (this == BOrientation.horizontal)
      return BOrientation.vertical;
    else
      return BOrientation.horizontal;
  }
}
