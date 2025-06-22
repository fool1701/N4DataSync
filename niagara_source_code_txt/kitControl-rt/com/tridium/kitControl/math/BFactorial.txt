/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import java.math.BigInteger;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;

/**
 * BFactorial performs the factorial operation, out = inA!
 *
 * @author    Lee Adcock
 * @creation  18 June 2009
 * @version   $Revision: 6$ $Date: 3/30/2004 3:41:44 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFactorial
  extends BUnaryMath
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BFactorial(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFactorial.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected double calculate(double a)
  {
    double n = 1.0;

    for (int i=1; i<= a; i++)
    {
      n = n*i;
      if(Double.isInfinite(n))
      {
        return Double.POSITIVE_INFINITY;
      }
    }
    return n;
  }


  public void onExecute(BStatusValue o, Context cx)
  {
    super.onExecute(o, cx);

    BStatusNumeric out = (BStatusNumeric)o;
    if(out.getValue() == Double.POSITIVE_INFINITY)
    {
      out.setStatus(BStatus.makeFault(out.getStatus(), true));
    }
  }


  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/factorial.png");
  
}
