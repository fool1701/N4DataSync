/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BExponential performs the operation out = e ^ inA 
 * (e raised in the inA power)
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 9$ $Date: 3/30/2004 3:41:49 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BExponential
  extends BUnaryMath
{ 
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BExponential(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExponential.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  

  protected double calculate(double a)
  {
    return Math.exp(a);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/exponential.png");

}
