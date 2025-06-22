/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BEnglishMetric is an BEnum that represents valid Baja Secure
 * values
 *
 * @author    Andy Saunders
 * @creation  18 Sept 06
 * @version   $Revision: 11$ $Date: 03-Jun-04 1:11:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("english"),
    @Range("metric")
  }
)
public final class BEnglishMetric
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BEnglishMetric(1265638975)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for english. */
  public static final int ENGLISH = 0;
  /** Ordinal value for metric. */
  public static final int METRIC = 1;

  /** BEnglishMetric constant for english. */
  public static final BEnglishMetric english = new BEnglishMetric(ENGLISH);
  /** BEnglishMetric constant for metric. */
  public static final BEnglishMetric metric = new BEnglishMetric(METRIC);

  /** Factory method with ordinal. */
  public static BEnglishMetric make(int ordinal)
  {
    return (BEnglishMetric)english.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BEnglishMetric make(String tag)
  {
    return (BEnglishMetric)english.getRange().get(tag);
  }

  /** Private constructor. */
  private BEnglishMetric(int ordinal)
  {
    super(ordinal);
  }

  public static final BEnglishMetric DEFAULT = english;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnglishMetric.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

      
}
