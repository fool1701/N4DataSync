/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BHalign defines an enumeration for horizontal 
 * justification: left, center, right, fill.
 *
 * @author    Brian Frank
 * @creation  1 Dec 00
 * @version   $Revision: 4$ $Date: 3/23/05 11:29:07 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("left"),
    @Range("center"),
    @Range("right"),
    @Range("fill")
  }
)
public final class BHalign
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BHalign(3289874309)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for left. */
  public static final int LEFT = 0;
  /** Ordinal value for center. */
  public static final int CENTER = 1;
  /** Ordinal value for right. */
  public static final int RIGHT = 2;
  /** Ordinal value for fill. */
  public static final int FILL = 3;

  /** BHalign constant for left. */
  public static final BHalign left = new BHalign(LEFT);
  /** BHalign constant for center. */
  public static final BHalign center = new BHalign(CENTER);
  /** BHalign constant for right. */
  public static final BHalign right = new BHalign(RIGHT);
  /** BHalign constant for fill. */
  public static final BHalign fill = new BHalign(FILL);

  /** Factory method with ordinal. */
  public static BHalign make(int ordinal)
  {
    return (BHalign)left.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BHalign make(String tag)
  {
    return (BHalign)left.getRange().get(tag);
  }

  /** Private constructor. */
  private BHalign(int ordinal)
  {
    super(ordinal);
  }

  public static final BHalign DEFAULT = left;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHalign.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
}
