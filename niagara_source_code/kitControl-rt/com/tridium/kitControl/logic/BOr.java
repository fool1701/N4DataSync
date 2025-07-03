/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.logic;

import java.io.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BOr performs a logical OR on all valid inputs and writes
 * the result to the out property.
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 7$ $Date: 3/12/2003 11:45:03 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BOr
  extends BQuadLogic
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BOr(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOr.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/





  protected boolean calculate(BStatusBoolean a, BStatusBoolean b, BStatusBoolean c, BStatusBoolean d)
  {
    boolean result = false;        
  
    //  Use only valid values.  If any input is valid and true, 
    //  return true.  If all inputs are invalid, return false
    if (a.getStatus().isValid()) { if (a.getValue()) return true; }
    if (b.getStatus().isValid()) { if (b.getValue()) return true; }
    if (c.getStatus().isValid()) { if (c.getValue()) return true; }
    if (d.getStatus().isValid()) { if (d.getValue()) return true; }

    return false;
  }

  /**
   * Return the minimum number of valid inputs needed to
   * generate a valid output, which is 1 for an BOr object.
   */
  public int minInputs() { return 1; }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/logic/or.png");

}
