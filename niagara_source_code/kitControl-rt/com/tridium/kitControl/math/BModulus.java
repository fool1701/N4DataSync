/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BModulus performs the JAVA  modulus operation, out = (inA % inB)
 *
 * @author    Lee Adcock
 * @creation  18 June 2009
 * @version   $Revision: 6$ $Date: 3/30/2004 3:41:44 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BModulus
  extends BBinaryMath
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BModulus(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BModulus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected double calculate(double a, double b)
  {
    try
    {
      return (a % b);
    } catch (ArithmeticException ae) {
      return Double.NaN;
    }
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/modulus.png");
  
}
