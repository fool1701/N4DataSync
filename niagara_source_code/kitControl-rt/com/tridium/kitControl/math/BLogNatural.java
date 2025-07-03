/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BLogNatural performs the operation out = ln(inA)
 * (log base e of inA)
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 7$ $Date: 3/30/2004 3:41:58 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BLogNatural
  extends BUnaryMath
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BLogNatural(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLogNatural.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected double calculate(double a)
  {
    return Math.log(a);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/logNatural.png");

}
