/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BMultiply performs the calculation out = inA * inB * inC * inD.
 *   
 * @author    Dan Giorgis
 * @creation  30 Aug 2001
 * @version   $Revision: 8$ $Date: 3/30/2004 3:42:16 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BMultiply
  extends BQuadMath
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BMultiply(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMultiply.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected double calculate(BStatusNumeric a, BStatusNumeric b, BStatusNumeric c, BStatusNumeric d)
  {
    double result = 1;        
      
    //  Use only valid values
    if (a.getStatus().isValid()) result *= a.getValue(); 
    if (b.getStatus().isValid()) result *= b.getValue();
    if (c.getStatus().isValid()) result *= c.getValue();
    if (d.getStatus().isValid()) result *= d.getValue();

    return result;
  }

  /**
   * Return the minimum number of valid inputs needed to
   * generate a valid output, which is 1 for an BMultiply object.
   */
  public int minInputs() { return 1; }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/multiply.png");

}
