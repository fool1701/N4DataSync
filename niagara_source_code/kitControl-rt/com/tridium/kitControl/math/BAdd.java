/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BAdd performs the operation out = (inA + inB + inC + inD)
 *   
 * @author    Brian Frank
 * @creation  11 Oct 00
 * @version   $Revision: 23$ $Date: 3/30/2004 3:41:12 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BAdd
  extends BQuadMath
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BAdd(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAdd.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    


  protected double calculate(BStatusNumeric a, BStatusNumeric b, BStatusNumeric c, BStatusNumeric d)
  {
    double result = 0;        
  
    //  Use only valid values
    if (a.getStatus().isValid()) result += a.getValue();
    if (b.getStatus().isValid()) result += b.getValue();
    if (c.getStatus().isValid()) result += c.getValue();
    if (d.getStatus().isValid()) result += d.getValue();

    return result;
  }

  /**
   * Return the minimum number of valid inputs needed to
   * generate a valid output, which is 1 for an BAdd object.
   */
  public int minInputs() { return 1; }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/add.png");

}
