/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BTransformMode defines a type of transform that should be applied. These transformations include
 * rotations of 0, 90, 180, and 270 degrees as well as flip and mirror.
 *
 * @since Niagara 4.12
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "none", ordinal = 0),
    @Range(value = "rotate90", ordinal = 1),
    @Range(value = "rotate180", ordinal = 2),
    @Range(value = "rotate270", ordinal = 3),
    @Range(value = "mirror", ordinal = 4),
    @Range(value = "flip", ordinal = 5)
  },
  defaultValue = "none"
)
public final class BTransformMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BTransformMode(2887130730)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for rotate90. */
  public static final int ROTATE_90 = 1;
  /** Ordinal value for rotate180. */
  public static final int ROTATE_180 = 2;
  /** Ordinal value for rotate270. */
  public static final int ROTATE_270 = 3;
  /** Ordinal value for mirror. */
  public static final int MIRROR = 4;
  /** Ordinal value for flip. */
  public static final int FLIP = 5;

  /** BTransformMode constant for none. */
  public static final BTransformMode none = new BTransformMode(NONE);
  /** BTransformMode constant for rotate90. */
  public static final BTransformMode rotate90 = new BTransformMode(ROTATE_90);
  /** BTransformMode constant for rotate180. */
  public static final BTransformMode rotate180 = new BTransformMode(ROTATE_180);
  /** BTransformMode constant for rotate270. */
  public static final BTransformMode rotate270 = new BTransformMode(ROTATE_270);
  /** BTransformMode constant for mirror. */
  public static final BTransformMode mirror = new BTransformMode(MIRROR);
  /** BTransformMode constant for flip. */
  public static final BTransformMode flip = new BTransformMode(FLIP);

  /** Factory method with ordinal. */
  public static BTransformMode make(int ordinal)
  {
    return (BTransformMode)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BTransformMode make(String tag)
  {
    return (BTransformMode)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BTransformMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BTransformMode DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTransformMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
