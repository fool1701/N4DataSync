/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BLogBase10 performs the operation out = log10(inA)
 * (log base 10 of inA)
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 7$ $Date: 3/30/2004 3:41:53 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BLogBase10
  extends BUnaryMath
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BLogBase10(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLogBase10.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected double calculate(double a)
  {
    return Math.log(a) * LOG10E;
  }

  // the (log base 10 of e)
  private static final double LOG10E = 0.4342944819018;

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/logBase10.png");

}
