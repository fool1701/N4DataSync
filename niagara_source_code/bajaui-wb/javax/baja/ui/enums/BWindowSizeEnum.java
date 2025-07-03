/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BWindowSizeEnum defines how the BWindow resizeWindow action works:
 *
 * - `Preferred Size` will set both the width and height to the main content's preferred Size.
 * - `Preferred Width` will set the width the main content's preferred with.
 * - `Preferred Height` will set both the height to the main content's preferred height.
 * - `Fit Preferred Size` will set both the width and height to the main content's preferred Size unless that size
 *    would be too big for the current monitor.
 * - `Fit Preferred Width` will set the width to the main content's preferred width unless that width
 *    would be too big for the current monitor.
 * - `Fit Preferred height` will set the height to the main content's preferred height unless that height
 *    would be too big for the current monitor.
 *
 * @author JJ Frankovich
 * @since Niagara 4.13
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "preferredSize", ordinal = 0),
    @Range(value = "preferredWidth", ordinal = 1),
    @Range(value = "preferredHeight", ordinal = 2),
    @Range(value = "fitPreferredSize", ordinal = 3),
    @Range(value = "fitPreferredWidth", ordinal = 4),
    @Range(value = "fitPreferredHeight", ordinal = 5)
  },
  defaultValue = "preferredSize"
)
public final class BWindowSizeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BWindowSizeEnum(985955295)1.0$ @*/
/* Generated Wed Jul 20 08:46:57 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for preferredSize. */
  public static final int PREFERRED_SIZE = 0;
  /** Ordinal value for preferredWidth. */
  public static final int PREFERRED_WIDTH = 1;
  /** Ordinal value for preferredHeight. */
  public static final int PREFERRED_HEIGHT = 2;
  /** Ordinal value for fitPreferredSize. */
  public static final int FIT_PREFERRED_SIZE = 3;
  /** Ordinal value for fitPreferredWidth. */
  public static final int FIT_PREFERRED_WIDTH = 4;
  /** Ordinal value for fitPreferredHeight. */
  public static final int FIT_PREFERRED_HEIGHT = 5;

  /** BWindowSizeEnum constant for preferredSize. */
  public static final BWindowSizeEnum preferredSize = new BWindowSizeEnum(PREFERRED_SIZE);
  /** BWindowSizeEnum constant for preferredWidth. */
  public static final BWindowSizeEnum preferredWidth = new BWindowSizeEnum(PREFERRED_WIDTH);
  /** BWindowSizeEnum constant for preferredHeight. */
  public static final BWindowSizeEnum preferredHeight = new BWindowSizeEnum(PREFERRED_HEIGHT);
  /** BWindowSizeEnum constant for fitPreferredSize. */
  public static final BWindowSizeEnum fitPreferredSize = new BWindowSizeEnum(FIT_PREFERRED_SIZE);
  /** BWindowSizeEnum constant for fitPreferredWidth. */
  public static final BWindowSizeEnum fitPreferredWidth = new BWindowSizeEnum(FIT_PREFERRED_WIDTH);
  /** BWindowSizeEnum constant for fitPreferredHeight. */
  public static final BWindowSizeEnum fitPreferredHeight = new BWindowSizeEnum(FIT_PREFERRED_HEIGHT);

  /** Factory method with ordinal. */
  public static BWindowSizeEnum make(int ordinal)
  {
    return (BWindowSizeEnum)preferredSize.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BWindowSizeEnum make(String tag)
  {
    return (BWindowSizeEnum)preferredSize.getRange().get(tag);
  }

  /** Private constructor. */
  private BWindowSizeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BWindowSizeEnum DEFAULT = preferredSize;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWindowSizeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
