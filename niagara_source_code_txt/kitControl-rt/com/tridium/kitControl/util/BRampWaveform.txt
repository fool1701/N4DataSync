/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("triangle"),
    @Range("sawTooth"),
    @Range("invertedSawTooth")
  },
  defaultValue = "triangle"
)
public final class BRampWaveform
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BRampWaveform(1876256415)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for triangle. */
  public static final int TRIANGLE = 0;
  /** Ordinal value for sawTooth. */
  public static final int SAW_TOOTH = 1;
  /** Ordinal value for invertedSawTooth. */
  public static final int INVERTED_SAW_TOOTH = 2;

  /** BRampWaveform constant for triangle. */
  public static final BRampWaveform triangle = new BRampWaveform(TRIANGLE);
  /** BRampWaveform constant for sawTooth. */
  public static final BRampWaveform sawTooth = new BRampWaveform(SAW_TOOTH);
  /** BRampWaveform constant for invertedSawTooth. */
  public static final BRampWaveform invertedSawTooth = new BRampWaveform(INVERTED_SAW_TOOTH);

  /** Factory method with ordinal. */
  public static BRampWaveform make(int ordinal)
  {
    return (BRampWaveform)triangle.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BRampWaveform make(String tag)
  {
    return (BRampWaveform)triangle.getRange().get(tag);
  }

  /** Private constructor. */
  private BRampWaveform(int ordinal)
  {
    super(ordinal);
  }

  public static final BRampWaveform DEFAULT = triangle;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRampWaveform.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
