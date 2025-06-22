/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BSubtract performs the operation out = (inA - inB)
 *
 * If either input is Double.NaN, the output will be
 * Double.NaN.
 *
 * @author    Dan Giorgis
 * @creation  8 Nov 00
 * @version   $Revision: 13$ $Date: 3/30/2004 3:42:43 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BSubtract
  extends BBinaryMath
{ 
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BSubtract(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSubtract.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected double calculate(double a, double b)
  {
    return (a - b);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/subtract.png");
  
}
