/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BScaleMode defines how scale to fit works.
 *
 * Starting in Niagara 4.11, zoomRatio, zoomWidth, and zoomHeight are available.
 * ZoomRatio is similar to fitRatio, but instead of ensuring the entire image is visible, it ensures the aspect ratio is maintained
 * and the image fills the entire space provided.
 * ZoomWidth and zoomHeight are similar to fitWidth and  fitHeight, but instead of stretching the desired object, it enlarges
 * or shrinks that object to fill the desired dimension and maintains the aspect ratio.
 *
 * @author    Brian Frank on 2 Apr 04
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "none", ordinal = 0),
    @Range(value = "fit", ordinal = 1),
    @Range(value = "fitRatio", ordinal = 2),
    @Range(value = "fitWidth", ordinal = 3),
    @Range(value = "fitHeight", ordinal = 4),
    @Range(value = "zoomRatio", ordinal = 5),
    @Range(value = "zoomWidth", ordinal = 6),
    @Range(value = "zoomHeight", ordinal = 7)
  },
  defaultValue = "none"
)
public final class BScaleMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BScaleMode(708777512)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for fit. */
  public static final int FIT = 1;
  /** Ordinal value for fitRatio. */
  public static final int FIT_RATIO = 2;
  /** Ordinal value for fitWidth. */
  public static final int FIT_WIDTH = 3;
  /** Ordinal value for fitHeight. */
  public static final int FIT_HEIGHT = 4;
  /** Ordinal value for zoomRatio. */
  public static final int ZOOM_RATIO = 5;
  /** Ordinal value for zoomWidth. */
  public static final int ZOOM_WIDTH = 6;
  /** Ordinal value for zoomHeight. */
  public static final int ZOOM_HEIGHT = 7;

  /** BScaleMode constant for none. */
  public static final BScaleMode none = new BScaleMode(NONE);
  /** BScaleMode constant for fit. */
  public static final BScaleMode fit = new BScaleMode(FIT);
  /** BScaleMode constant for fitRatio. */
  public static final BScaleMode fitRatio = new BScaleMode(FIT_RATIO);
  /** BScaleMode constant for fitWidth. */
  public static final BScaleMode fitWidth = new BScaleMode(FIT_WIDTH);
  /** BScaleMode constant for fitHeight. */
  public static final BScaleMode fitHeight = new BScaleMode(FIT_HEIGHT);
  /** BScaleMode constant for zoomRatio. */
  public static final BScaleMode zoomRatio = new BScaleMode(ZOOM_RATIO);
  /** BScaleMode constant for zoomWidth. */
  public static final BScaleMode zoomWidth = new BScaleMode(ZOOM_WIDTH);
  /** BScaleMode constant for zoomHeight. */
  public static final BScaleMode zoomHeight = new BScaleMode(ZOOM_HEIGHT);

  /** Factory method with ordinal. */
  public static BScaleMode make(int ordinal)
  {
    return (BScaleMode)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BScaleMode make(String tag)
  {
    return (BScaleMode)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BScaleMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BScaleMode DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScaleMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
