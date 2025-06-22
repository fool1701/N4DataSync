/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import java.io.*;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BNumericBitXor performs a bit XOR of the in value and mask value..
 *
 * @author    Andy Saunders
 * @creation  20 April 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNumericBitXor
  extends BNumericMask
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BNumericBitXor(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericBitXor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public void doCalculate()
  {
    if(getIn().getStatus().isValid() && getMask().getStatus().isValid())
    {
      long results = (long)(getIn().getValue()) ^ (long)(getMask().getValue());
      getOut().setValue((double)results);
    }
  }


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
  */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/logic/xor.png");


}
