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
    @Range("unoccupied"),
    @Range("occupied")
  }
)
public final class BOccupied
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BOccupied(851746081)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unoccupied. */
  public static final int UNOCCUPIED = 0;
  /** Ordinal value for occupied. */
  public static final int OCCUPIED = 1;

  /** BOccupied constant for unoccupied. */
  public static final BOccupied unoccupied = new BOccupied(UNOCCUPIED);
  /** BOccupied constant for occupied. */
  public static final BOccupied occupied = new BOccupied(OCCUPIED);

  /** Factory method with ordinal. */
  public static BOccupied make(int ordinal)
  {
    return (BOccupied)unoccupied.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BOccupied make(String tag)
  {
    return (BOccupied)unoccupied.getRange().get(tag);
  }

  /** Private constructor. */
  private BOccupied(int ordinal)
  {
    super(ordinal);
  }

  public static final BOccupied DEFAULT = unoccupied;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOccupied.class);

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
    return (this != unoccupied);
  }
      
}
