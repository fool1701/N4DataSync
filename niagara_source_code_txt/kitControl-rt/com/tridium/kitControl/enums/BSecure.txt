/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BSecure is an BEnum that represents valid Baja Secure
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
    @Range("access"),
    @Range("secure")
  }
)
public final class BSecure
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.enums.BSecure(2946278910)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for access. */
  public static final int ACCESS = 0;
  /** Ordinal value for secure. */
  public static final int SECURE = 1;

  /** BSecure constant for access. */
  public static final BSecure access = new BSecure(ACCESS);
  /** BSecure constant for secure. */
  public static final BSecure secure = new BSecure(SECURE);

  /** Factory method with ordinal. */
  public static BSecure make(int ordinal)
  {
    return (BSecure)access.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BSecure make(String tag)
  {
    return (BSecure)access.getRange().get(tag);
  }

  /** Private constructor. */
  private BSecure(int ordinal)
  {
    super(ordinal);
  }

  public static final BSecure DEFAULT = access;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSecure.class);

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
    return (this != access);
  }
      
}
