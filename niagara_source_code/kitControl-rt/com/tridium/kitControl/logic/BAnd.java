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
 * BAnd performs a logical AND on all inputs and writes
 * the result to the out property.
 *
 * @author    Dan Giorgis
 * @creation  6 Nov 00
 * @version   $Revision: 17$ $Date: 3/12/2003 11:44:45 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BAnd
  extends BQuadLogic
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BAnd(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAnd.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




  protected boolean calculate(BStatusBoolean a, BStatusBoolean b, BStatusBoolean c, BStatusBoolean d)
  {
    boolean result = true;        
    boolean allInvalid = true;
  
    //  Use only valid values
    if (a.getStatus().isValid()) { result &= a.getValue(); allInvalid = false; }
    if (b.getStatus().isValid()) { result &= b.getValue(); allInvalid = false; }
    if (c.getStatus().isValid()) { result &= c.getValue(); allInvalid = false; }
    if (d.getStatus().isValid()) { result &= d.getValue(); allInvalid = false; }

    if (allInvalid)
      return false;
    else
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
  private static final BIcon icon = BIcon.std("control/logic/and.png");

}
