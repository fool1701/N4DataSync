/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BAlign defines an enumeration for justification: 
 * top, left, bottom, right, or center.
 *
 * @author    Brian Frank
 * @creation  1 Dec 00
 * @version   $Revision: 4$ $Date: 3/23/05 11:29:07 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("top"),
    @Range("left"),
    @Range("bottom"),
    @Range("right"),
    @Range("center")
  }
)
public final class BAlign
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BAlign(2654366229)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for top. */
  public static final int TOP = 0;
  /** Ordinal value for left. */
  public static final int LEFT = 1;
  /** Ordinal value for bottom. */
  public static final int BOTTOM = 2;
  /** Ordinal value for right. */
  public static final int RIGHT = 3;
  /** Ordinal value for center. */
  public static final int CENTER = 4;

  /** BAlign constant for top. */
  public static final BAlign top = new BAlign(TOP);
  /** BAlign constant for left. */
  public static final BAlign left = new BAlign(LEFT);
  /** BAlign constant for bottom. */
  public static final BAlign bottom = new BAlign(BOTTOM);
  /** BAlign constant for right. */
  public static final BAlign right = new BAlign(RIGHT);
  /** BAlign constant for center. */
  public static final BAlign center = new BAlign(CENTER);

  /** Factory method with ordinal. */
  public static BAlign make(int ordinal)
  {
    return (BAlign)top.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAlign make(String tag)
  {
    return (BAlign)top.getRange().get(tag);
  }

  /** Private constructor. */
  private BAlign(int ordinal)
  {
    super(ordinal);
  }

  public static final BAlign DEFAULT = top;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlign.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
}
