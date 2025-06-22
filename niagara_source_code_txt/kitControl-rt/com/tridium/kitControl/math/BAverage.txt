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
 * BAverage determines the average value of valid inputs and
 * writes that value to out.  out = (inA + inB + inC + inD) / 4
 *   
 * @author    Dan Giorgis
 * @creation  30 Aug 2001
 * @version   $Revision: 10$ $Date: 3/30/2004 3:41:30 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BAverage
  extends BQuadMath
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BAverage(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAverage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    

  protected double calculate(BStatusNumeric a, BStatusNumeric b, BStatusNumeric c, BStatusNumeric d)
  {
    double total = 0;        
    int numValid = 0;
  
    //  Use only valid values
    if (a.getStatus().isValid()) { total += a.getValue(); numValid++; }
    if (b.getStatus().isValid()) { total += b.getValue(); numValid++; }
    if (c.getStatus().isValid()) { total += c.getValue(); numValid++; }
    if (d.getStatus().isValid()) { total += d.getValue(); numValid++; }

    return total / numValid;
  }

  /**
   * Return the minimum number of valid inputs needed to
   * generate a valid output, which is 1 for an BMinimum object.
   */
  public int minInputs() { return 1; }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/average.png");

}
