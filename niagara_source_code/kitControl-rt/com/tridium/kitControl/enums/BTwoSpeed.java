/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BTwoSpeed is an BEnum that represents valid Baja twospeed
 * values
 *
 * @author    Danny Wahlquist
 * @creation  27 Oct 04
 * @version   $Revision: 11$ $Date: 03-Jun-04 1:11:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("off"),
    @Range("slow"),
    @Range("fast")
  }
)
public final class BTwoSpeed
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BTwoSpeed(3033844087)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for off. */
  public static final int OFF = 0;
  /** Ordinal value for slow. */
  public static final int SLOW = 1;
  /** Ordinal value for fast. */
  public static final int FAST = 2;

  /** BTwoSpeed constant for off. */
  public static final BTwoSpeed off = new BTwoSpeed(OFF);
  /** BTwoSpeed constant for slow. */
  public static final BTwoSpeed slow = new BTwoSpeed(SLOW);
  /** BTwoSpeed constant for fast. */
  public static final BTwoSpeed fast = new BTwoSpeed(FAST);

  /** Factory method with ordinal. */
  public static BTwoSpeed make(int ordinal)
  {
    return (BTwoSpeed)off.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BTwoSpeed make(String tag)
  {
    return (BTwoSpeed)off.getRange().get(tag);
  }

  /** Private constructor. */
  private BTwoSpeed(int ordinal)
  {
    super(ordinal);
  }

  public static final BTwoSpeed DEFAULT = off;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTwoSpeed.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /*********************************************
  *  Convenience method.  Returns true if and only
  *  if the current value of the enumeration
  *  is NOT equal to OFF.
  **********************************************/
  public final boolean isFault()
  {
    return (this != off);
  }
      
}
