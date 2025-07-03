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
 * BXor performs a logical XOR on all valid inputs and writes
 * the result to the out property.
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 7$ $Date: 3/12/2003 11:45:06 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BXor
  extends BQuadLogic
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BXor(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BXor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/





  protected boolean calculate(BStatusBoolean a, BStatusBoolean b, BStatusBoolean c, BStatusBoolean d)
  {
    boolean result = false;        
    boolean aa,bb,cc,dd;  

    //  Use only valid values.  If an input is invalid, use
    //  false (identity operation for XOR)
    aa = bb = cc = dd = false;    
    if (a.getStatus().isValid()) { aa = a.getValue(); }
    if (b.getStatus().isValid()) { bb = b.getValue(); }
    if (c.getStatus().isValid()) { cc = c.getValue(); }
    if (d.getStatus().isValid()) { dd = d.getValue(); }

    return (aa ^ bb ^ cc ^ dd);
  }

  /**
   * Return the minimum number of valid inputs needed to
   * generate a valid output, which is 2 for an BXor object.
   */
  public int minInputs() { return 2; }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/logic/xor.png");

}
