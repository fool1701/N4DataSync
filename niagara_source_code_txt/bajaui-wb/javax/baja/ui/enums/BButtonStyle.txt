/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BButtonStyle defines an enumeration for how buttons
 * should look and behave.
 *
 * @author    Andy Frank
 * @creation  21 Nov 02
 * @version   $Revision: 3$ $Date: 3/23/05 11:29:07 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("normal"),
    @Range("toolBar"),
    @Range("hyperlink")
  }
)
public final class BButtonStyle
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BButtonStyle(2343272814)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for normal. */
  public static final int NORMAL = 1;
  /** Ordinal value for toolBar. */
  public static final int TOOL_BAR = 2;
  /** Ordinal value for hyperlink. */
  public static final int HYPERLINK = 3;

  /** BButtonStyle constant for none. */
  public static final BButtonStyle none = new BButtonStyle(NONE);
  /** BButtonStyle constant for normal. */
  public static final BButtonStyle normal = new BButtonStyle(NORMAL);
  /** BButtonStyle constant for toolBar. */
  public static final BButtonStyle toolBar = new BButtonStyle(TOOL_BAR);
  /** BButtonStyle constant for hyperlink. */
  public static final BButtonStyle hyperlink = new BButtonStyle(HYPERLINK);

  /** Factory method with ordinal. */
  public static BButtonStyle make(int ordinal)
  {
    return (BButtonStyle)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BButtonStyle make(String tag)
  {
    return (BButtonStyle)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BButtonStyle(int ordinal)
  {
    super(ordinal);
  }

  public static final BButtonStyle DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BButtonStyle.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
