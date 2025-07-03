/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BOffOn is an BEnum that represents valid Baja OffOn
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
    @Range("off"),
    @Range("on")
  }
)
public final class BOffOn
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BOffOn(816221984)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for off. */
  public static final int OFF = 0;
  /** Ordinal value for on. */
  public static final int ON = 1;

  /** BOffOn constant for off. */
  public static final BOffOn off = new BOffOn(OFF);
  /** BOffOn constant for on. */
  public static final BOffOn on = new BOffOn(ON);

  /** Factory method with ordinal. */
  public static BOffOn make(int ordinal)
  {
    return (BOffOn)off.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BOffOn make(String tag)
  {
    return (BOffOn)off.getRange().get(tag);
  }

  /** Private constructor. */
  private BOffOn(int ordinal)
  {
    super(ordinal);
  }

  public static final BOffOn DEFAULT = off;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOffOn.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /*********************************************
  *  Convenience method.  Returns true if and only
  *  if the current value of the enumeration
  *  is NOT equal to NO_FAULT_DETECTED.
  **********************************************/
  public final boolean isFault()
  {
    return (this != off);
  }
      
}
