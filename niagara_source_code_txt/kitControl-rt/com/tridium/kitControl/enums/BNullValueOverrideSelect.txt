/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BOccupied is an BEnum that represents valid Baja Occupied
 * values
 *
 * @author    Danny Wahlquist
 * @creation  25 Oct 04
 * @version   $Revision: 11$ $Date: 03-Jun-04 1:11:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("useInValue"),
    @Range("specifyOutValue")
  }
)
public final class BNullValueOverrideSelect
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BNullValueOverrideSelect(4031925649)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for useInValue. */
  public static final int USE_IN_VALUE = 0;
  /** Ordinal value for specifyOutValue. */
  public static final int SPECIFY_OUT_VALUE = 1;

  /** BNullValueOverrideSelect constant for useInValue. */
  public static final BNullValueOverrideSelect useInValue = new BNullValueOverrideSelect(USE_IN_VALUE);
  /** BNullValueOverrideSelect constant for specifyOutValue. */
  public static final BNullValueOverrideSelect specifyOutValue = new BNullValueOverrideSelect(SPECIFY_OUT_VALUE);

  /** Factory method with ordinal. */
  public static BNullValueOverrideSelect make(int ordinal)
  {
    return (BNullValueOverrideSelect)useInValue.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BNullValueOverrideSelect make(String tag)
  {
    return (BNullValueOverrideSelect)useInValue.getRange().get(tag);
  }

  /** Private constructor. */
  private BNullValueOverrideSelect(int ordinal)
  {
    super(ordinal);
  }

  public static final BNullValueOverrideSelect DEFAULT = useInValue;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNullValueOverrideSelect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

      
}
